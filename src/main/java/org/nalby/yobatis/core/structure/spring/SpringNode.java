package org.nalby.yobatis.core.structure.spring;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.nalby.yobatis.core.exception.ProjectException;
import org.nalby.yobatis.core.structure.File;
import org.nalby.yobatis.core.util.TextUtil;
import org.nalby.yobatis.core.xml.AbstractXmlParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SpringNode extends AbstractXmlParser {

    private final static String SPRING_XML_TAG = "beans";

    public SpringNode(InputStream inputStream, String rootElmentTag) throws DocumentException, IOException {
        super(inputStream, rootElmentTag);
        propertyFileLocations = new HashSet<>();
        importLocations = new HashSet<>();
    }

    private static final String[] DATASOURCE_CLASSES = {
            "com.alibaba.druid.pool.DruidDataSource",
            "org.apache.commons.dbcp.BasicDataSource" };

    private static final String P_NAMESPACE = "http://www.springframework.org/schema/p";
    private static final String CONTEXT_NAMESPACE = "http://www.springframework.org/schema/context";

    private Set<String> propertyFileLocations = null;

    private Set<String> importLocations = null;

    private void parseLocationsInContextPropertyTag() {
        Element root = document.getRootElement();
        QName qName = new QName("property-placeholder", new Namespace("context", CONTEXT_NAMESPACE));
        List<Element> elements = root.elements(qName);
        for (Element element : elements) {
            String text = element.attributeValue("location");
            if (TextUtil.isEmpty(text)) {
                continue;
            }
            String vals[] = text.split(",");
            for (String tmp: vals) {
                if (!TextUtil.isEmpty(tmp)) {
                    propertyFileLocations.add(tmp.trim());
                }
            }
        }
    }

    private void parseImportLocations() {
        List<Element> importElements = document.getRootElement().elements("import");
        for (Element importElement: importElements) {
            String val = importElement.attributeValue("resource");
            if (!TextUtil.isEmpty(val)) {
                importLocations.add(val.trim());
            }
        }
    }


    private Element findElementByAttr(List<Element>elements, String attrName, String attrValue) {
        for (Element element : elements) {
            if (attrValue.equals(element.attributeValue(attrName))) {
                return element;
            }
        }
        return null;
    }

    /**
     * Find the property value of a bean.
     * @param bean the bean element.
     * @param name the property name.
     * @return
     */
    private String findPropertyValue(Element bean, String name) {
        QName qname = new QName(name, new Namespace("p", P_NAMESPACE));
        String val = bean.attributeValue(qname);
        if (val != null) {
            return val.trim();
        }
        Element property = findElementByAttr(bean.elements("property"), "name", name);
        if (property == null) {
            return null;
        }
        val = property.attributeValue("value");
        if (val != null) {
            return val.trim();
        }
        List<Element> valueList = property.elements("value");
        if (valueList.size() == 1) {
            return valueList.get(0).getTextTrim();
        }
        return null;
    }


    private void parseLocationInPlaceholderBean(Element bean, boolean checkSuffix) {
        String location = findPropertyValue(bean, "location");
        if (location != null && (!checkSuffix || location.endsWith(".properties"))) {
            propertyFileLocations.add(location);
        }
    }

    /**
     * PropertyPlaceholderConfigurer supports config only one location.
     * @param root
     */
    private void parsePropertyLocationInPlaceholderConfiguer(Element root) {
        Element configuer = findElementByAttr(root.elements("bean"),
                "class", "org.springframework.beans.factory.config.PropertyPlaceholderConfigurer");
        if (configuer != null) {
            parseLocationInPlaceholderBean(configuer, false);
        }
    }


    /**
     * Find locations in a placeholder configuer bean. This method checks
     * whether the locations ends with '.properties' if checkSuffix is true.
     * @param configuer
     * @param checkSuffix
     * @return locations found, or an empty list else.
     */
    private List<String> findLocationsInPlaceholderBean(Element configuer, boolean checkSuffix) {
        List<String> list = new LinkedList<>();
        Element locations = findElementByAttr(configuer.elements("property"), "name", "locations");
        if (locations == null) {
            return list;
        }
        Element value = locations.element("value");
        if (value != null) {
            String text = value.getTextTrim();
            if (!TextUtil.isEmpty(text) &&
                    (!checkSuffix || text.endsWith(".properties"))) {
                propertyFileLocations.add(value.getTextTrim());
            }
            return list;
        }
        Element collection = locations.element("list");
        if (collection == null) {
            collection = locations.element("set");
        }
        if (collection != null) {
            for (Element valueElement: collection.elements("value")) {
                String text = valueElement.getTextTrim();
                if (!TextUtil.isEmpty(text) &&
                        (!checkSuffix || text.endsWith(".properties"))) {
                    list.add(text);
                }
            }
        }
        return list;
    }

    private void parsePropertyLocationsInPlaceholderConfiguer(Element root) {
        Element configuer = findElementByAttr(root.elements("bean"),
                "class", "org.springframework.beans.factory.config.PropertyPlaceholderConfigurer");
        if (configuer != null) {
            propertyFileLocations.addAll(findLocationsInPlaceholderBean(configuer, false));
        }
    }

    private void parsePropertyLocations() {
        Element root = document.getRootElement();
        parsePropertyLocationInPlaceholderConfiguer(root);
        parsePropertyLocationsInPlaceholderConfiguer(root);
        if (!propertyFileLocations.isEmpty()) {
            return;
        }
        //Reaching here means no standard Spring placeholder bean was found, then we guess one.
        for (Element bean : root.elements("bean")) {
            if (hasProperty(bean, "location")) {
                parseLocationInPlaceholderBean(bean, true);
            }
            Element locations = findElementByAttr(bean.elements("property"), "name", "locations");
            if (locations == null) {
                continue;
            }
            propertyFileLocations.addAll(findLocationsInPlaceholderBean(bean, true));
            if (!propertyFileLocations.isEmpty()) {
                break;
            }
        }
    }

    private boolean hasProperty(Element parent, String attrName) {
        QName qname = new QName(attrName, new Namespace("p", P_NAMESPACE));
        if (parent.attributeValue(qname) != null) {
            return true;
        }
        return findElementByAttr(parent.elements("property"), "name", attrName) != null;
    }

    private boolean isDatasourceBean(Element element) {
        String clazz = element.attributeValue("class");
        if (null == clazz) {
            return false;
        }
        if (DATASOURCE_CLASSES[0].equals(clazz)
                || DATASOURCE_CLASSES[1].equals(clazz)) {
            return true;
        }
        String id = element.attributeValue("id");
        if ((id == null || !id.toLowerCase().contains("datasource")) &&
                !clazz.toLowerCase().contains("datasource")) {
            return false;
        }
        if (hasProperty(element, "url") &&
                hasProperty(element, "username") &&
                hasProperty(element, "password")) {
            return true;
        }
        return false;
    }

    /**
     * Parse the property value of the property name in a bean. Calling this method with the following bean
     * and the propertyName of "key", for instance, will return "value".
     * <pre>
     *     &lt;bean class="XXX"&gt;
     *         &lt;property name="key"&gt;value&lt;/property&gt;
     *     &lt;/bean&gt;
     * </pre>
     * @param bean
     * @param propertyName
     * @return the value of the property, or null if not found.
     */
    private String parsePropertyValue(Element bean, String propertyName) {
        QName qname = new QName(propertyName, new Namespace("p", P_NAMESPACE));
        if (null != bean.attributeValue(qname)) {
            return bean.attributeValue(qname).trim();
        }
        for (Element property : bean.elements("property")) {
            String nameAttr = property.attributeValue("name");
            if (nameAttr == null || !nameAttr.equals(propertyName)) {
                continue;
            }
            String value = property.attributeValue("value");
            return value == null ? value : value.trim();
        }
        return null;
    }

    private String propertyValueFromDatasources(String propertyName) {
        Element root = document.getRootElement();
        List<Element> beanElements = root.elements("bean");
        for (Element beanElement : beanElements) {
            if (!isDatasourceBean(beanElement)) {
                continue;
            }
            String tmp = parsePropertyValue(beanElement, propertyName);
            if (tmp != null) {
                return tmp;
            }
        }
        return null;
    }

    /**
     * Get the username from the datasource bean. if there are multiple
     * datasource beans, the first one will be used.
     *
     * @return the username if there is one, null otherise.
     */
    public String getDbUsername() {
        return propertyValueFromDatasources("username");
    }

    /**
     * Get the driver class' name from the datasource bean. if there are
     * multiple datasource beans, the first one will be used.
     *
     * @return the driver class' name if there is one, null otherwise.
     */
    public String getDbDriverClass() {
        return propertyValueFromDatasources("driverClassName");
    }

    /**
     * Get the password from the datasource bean. if there are multiple
     * datasource beans, the first one will be used.
     *
     * @return the password if there is one, null otherwise.
     */
    public String getDbPassword() {
        return propertyValueFromDatasources("password");
    }

    /**
     * Get the url from the datasource bean. if there are multiple
     * datasource beans, the first one will be used.
     *
     * @return the url if there is one, null otherwise.
     */
    public String getDbUrl() {
        return propertyValueFromDatasources("url");
    }

    /**
     * Get the imported spring file locations if any.
     * @return locations if any, an empty set else.
     */
    public Set<String> getImportLocations() {
        return this.importLocations;
    }

    /**
     * Get properties file locations configured in PropertyPlaceholderConfigurer bean.
     * @return locations if any, an empty set else.
     */
    public Set<String> getPropertyFileLocations() {
        return propertyFileLocations;
    }


    public static SpringNode parse(File file) {
        try (InputStream inputStream = file.open()) {
            SpringNode springNode = new SpringNode(inputStream, SPRING_XML_TAG);
            springNode.parseImportLocations();
            springNode.parsePropertyLocations();
            springNode.parseLocationsInContextPropertyTag();
            return springNode;
        } catch (Exception e) {
            throw new ProjectException(file.name() + " is not a valid spring config file.");
        }
    }
}
