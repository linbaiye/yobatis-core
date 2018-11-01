package org.nalby.yobatis.core.mybatis.mapper.legacy;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.Element;
import org.nalby.yobatis.core.exception.InvalidUnitException;
import org.nalby.yobatis.core.mybatis.NamingHelper;
import org.nalby.yobatis.core.mybatis.YobatisUnit;
import org.nalby.yobatis.core.mybatis.mapper.MapperEntityResolver;
import org.nalby.yobatis.core.mybatis.method.NamespaceSuffix;
import org.xml.sax.EntityResolver;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class LegacyXmlMapper extends GeneratedXmlFile implements YobatisUnit {

    private String pathToPut;

    private final static Map<String, String> XML_ID_MAP;

    private Document document;

    private final static String PAGING_ID = "_PAGING_";

    private static final EntityResolver entityResolver = new MapperEntityResolver();

    private LegacyXmlMapper(Document document, String pathToPut, IntrospectedTable table) {
        super(document, null, null,
                null, false,
                table.getContext().getXmlFormatter());
        this.pathToPut = pathToPut;
        this.document = document;
    }

    static {
        XML_ID_MAP = new HashMap<>();
        XML_ID_MAP.put("BaseResultMap", "BASE_RESULT_MAP");
        XML_ID_MAP.put("Example_Where_Clause", "WHERE_CLAUSE");
        XML_ID_MAP.put("Update_By_Example_Where_Clause", "WHERE_CLAUSE_FOR_UPDATE");
        XML_ID_MAP.put("Base_Column_List", "BASE_COLUMN_LIST");
        XML_ID_MAP.put("selectByExample", NamespaceSuffix.SELECT_BY_CRITERIA);
        XML_ID_MAP.put("selectByPrimaryKey", NamespaceSuffix.SELECT_BY_PK);
        XML_ID_MAP.put("deleteByPrimaryKey", NamespaceSuffix.DELETE);
        XML_ID_MAP.put("deleteByExample", NamespaceSuffix.DELETE_BY_CRITERIA);
        XML_ID_MAP.put("insert", NamespaceSuffix.INSERT_ALL);
        XML_ID_MAP.put("insertSelective", NamespaceSuffix.INSERT);
        XML_ID_MAP.put("countByExample", NamespaceSuffix.COUNT);
        XML_ID_MAP.put("updateByExampleSelective", NamespaceSuffix.UPDATE_BY_CRITERIA);
        XML_ID_MAP.put("updateByExample", NamespaceSuffix.UPDATE_ALL_BY_CRITERIA);
        XML_ID_MAP.put("updateByPrimaryKeySelective", NamespaceSuffix.UPDATE);
        XML_ID_MAP.put("updateByPrimaryKey", NamespaceSuffix.UPDATE_ALL);
    }

    @Override
    public String getPathToPut() {
        return pathToPut;
    }


    private boolean hasElement(org.dom4j.Element element) {
        for (Element e : document.getRootElement().getElements()) {
            if (!(e instanceof XmlElement)) {
                continue;
            }
            for (Attribute attribute : ((XmlElement) e).getAttributes()) {
                if ("id".equals(attribute.getName()) &&
                        attribute.getValue() != null &&
                        element.attribute("id") != null &&
                        attribute.getValue().equals(element.attribute("id").getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void merge(String fileContent) throws InvalidUnitException {
        try (InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes())) {
            SAXReader saxReader = new SAXReader();
            saxReader.setValidation(false);
            saxReader.setEntityResolver(entityResolver);
            org.dom4j.Document document = saxReader.read(inputStream);
            Iterator<Node> nodeIterator = document.getRootElement().nodeIterator();
            while (nodeIterator.hasNext()) {
                Node node = nodeIterator.next();
                if (node instanceof Comment) {
                    this.document.getRootElement().addElement(new TextElement(node.asXML()));
                } else if (node instanceof org.dom4j.Element && !hasElement((org.dom4j.Element)node)) {
                    this.document.getRootElement().addElement(new TextElement(node.asXML()));
                }
            }
        } catch (IOException | DocumentException e) {
            throw new InvalidUnitException(e);
        }
    }

    private static void replaceIds(XmlElement xmlElement) {
        Iterator<Attribute> iterator = xmlElement.getAttributes().iterator();
        List<Attribute> attributeToInsert = new ArrayList<>();
        while (iterator.hasNext()) {
            Attribute attr = iterator.next();
            if (XML_ID_MAP.containsKey(attr.getValue())) {
                attributeToInsert.add(new Attribute(attr.getName(), XML_ID_MAP.get(attr.getValue())));
                iterator.remove();
            }
        }
        if (!attributeToInsert.isEmpty()) {
            xmlElement.getAttributes().addAll(attributeToInsert);
        }
        for (Element e : xmlElement.getElements()) {
            if (e instanceof XmlElement) {
                replaceIds((XmlElement)e);
            }
        }
    }

    private static void eraseTimestampedComments(XmlElement root) {
        for (Element e : root.getElements()) {
            if (!(e instanceof XmlElement)) {
                continue;
            }
            XmlElement parentElement = (XmlElement) e;
            Iterator<Element> iterator = parentElement.getElements().iterator();
            boolean commentStart = false;
            while (iterator.hasNext()) {
                Element element = iterator.next();
                if (element.getFormattedContent(0).contains("<!--")) {
                    commentStart = true;
                } else if (element.getFormattedContent(0).contains("-->")) {
                    break;
                } else if (commentStart && (element.getFormattedContent(0).contains("This element was generated on"))) {
                    // Remove this comment since it contains a timestamp which
                    // differs from time to time.
                    iterator.remove();
                }
            }
        }
    }

    private static void replaceTypes(XmlElement root, IntrospectedTable table) {
        FullyQualifiedJavaType baseEntity = NamingHelper.getBaseEntityType(table);
        FullyQualifiedJavaType criteria = NamingHelper.getBaseCriteriaType(table);
        for (Element e : root.getElements()) {
            if (!(e instanceof XmlElement) ||
                "resultMap".equals(((XmlElement) e).getName())) {
                continue;
            }
            XmlElement xmlElement = (XmlElement) e;
            Iterator<Attribute> iterator = xmlElement.getAttributes().iterator();
            Attribute attributeToAdd = null;
            while (iterator.hasNext()) {
                Attribute attribute = iterator.next();
                if (table.getExampleType().equals(attribute.getValue())) {
                    attributeToAdd = new Attribute(attribute.getName(), criteria.getFullyQualifiedName());
                }
                if (table.getBaseRecordType().equals(attribute.getValue())) {
                    attributeToAdd = new Attribute(attribute.getName(), baseEntity.getFullyQualifiedName());
                }
                if (attributeToAdd != null) {
                    iterator.remove();
                    break;
                }
            }
            if (attributeToAdd != null) {
                xmlElement.getAttributes().add(attributeToAdd);
            }
        }
    }

    private static void replaceNamespace(XmlElement root, IntrospectedTable table) {
        FullyQualifiedJavaType type = NamingHelper.getDaoImplType(table);
        root.getAttributes().clear();
        root.addAttribute(new Attribute("namespace", type.getFullyQualifiedName()));
    }

    /**
     * Delete an xml element whose id attribute equals to {@code id} and return
     * the position of the element.
     * @param document
     * @param id
     * @return the position if deleted, -1 otherwise..
     */
    private static int deleteXmlElement(Document document, String id) {
        Iterator<Element> iterator = document.getRootElement().getElements().iterator();
        int pos = 0;
        while (iterator.hasNext()) {
            pos++;
            Element element = iterator.next();
            if (!(element instanceof XmlElement)) {
                continue;
            }
            XmlElement xmlElement = (XmlElement) element;
            for (Attribute attribute : xmlElement.getAttributes()) {
                if ("id".equals(attribute.getName()) && id.equals(attribute.getValue())) {
                    iterator.remove();
                    return pos;
                }
            }
        }
        return -1;
    }

    private static void addPaging(XmlElement element) {
        LegacyMapperXmlFactory mapperXmlElementFactory = LegacyMapperXmlElementFactoryImpl.getInstance();
        element.addElement(3, mapperXmlElementFactory.pagingElement(PAGING_ID));
        for (Element e : element.getElements()) {
            if (!(e instanceof XmlElement)) {
                continue;
            }
            XmlElement xmlElement = (XmlElement)e;
            for (Attribute attribute : xmlElement.getAttributes()) {
                if ("id".equals(attribute.getName()) && NamespaceSuffix.SELECT_BY_CRITERIA.equals(attribute.getValue())) {
                    xmlElement.addElement(mapperXmlElementFactory.include(PAGING_ID));
                    xmlElement.addElement(mapperXmlElementFactory.ifElement("forUpdate", "for update"));
                    return;
                }
            }
        }
    }

    public static LegacyXmlMapper wrap(Document document, IntrospectedTable table) {
        document.setRootElement(new XmlElement(document.getRootElement()));
        // Invocation order matters.
        replaceIds(document.getRootElement());
        replaceTypes(document.getRootElement(), table);
        eraseTimestampedComments(document.getRootElement());
        replaceNamespace(document.getRootElement(), table);
        LegacyMapperXmlFactory mapperXmlElementFactory = LegacyMapperXmlElementFactoryImpl.getInstance();
        List<Element> elements = document.getRootElement().getElements();
        int pos = deleteXmlElement(document, "insertAll");
        document.getRootElement().getElements().add(pos + 1, mapperXmlElementFactory.insertAll(table));
        document.getRootElement().getElements().add(pos + 2, mapperXmlElementFactory.insertAllIgnore(table));
        if (NamingHelper.isAllKeyTable(table)) {
            elements.add(elements.size(), mapperXmlElementFactory.selectByPkOfAllKeyTable(table));
            elements.add(elements.size(), mapperXmlElementFactory.updateOfAllKeyTable(table));
            elements.add(elements.size(), mapperXmlElementFactory.updateAllOfAllKeyTable(table));
        }
        addPaging(document.getRootElement());
        FullyQualifiedJavaType entity = NamingHelper.getEntityType(table);
        String path = table.getContext().getSqlMapGeneratorConfiguration().getTargetProject() + "/" +
                table.getContext().getSqlMapGeneratorConfiguration().getTargetPackage() + "/" +
                entity.getShortName() + "Mapper.xml";
        return new LegacyXmlMapper(document, path, table);
    }
}
