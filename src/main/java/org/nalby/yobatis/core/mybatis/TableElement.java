package org.nalby.yobatis.core.mybatis;

import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Node;
import org.nalby.yobatis.core.exception.InvalidMybatisGeneratorConfigException;
import org.nalby.yobatis.core.util.TextUtil;

public class TableElement implements Comparable<TableElement> {
    private String name;

    private boolean enabled;

    public TableElement(String name, boolean enabled ) {
        this.name = name;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Element asElement() {
        DocumentFactory factory = DocumentFactory.getInstance();
        Element element = factory.createElement("table");
        element.addAttribute("tableName", name);
        element.addAttribute("modelType", "flat");
        Element property = factory.createElement("property");
        property.addAttribute("name", "enable");
        property.addAttribute("value", String.valueOf(enabled));
        element.add(property);
        return element;
    }

    public static TableElement fromElement(Element element) {
        if (!"table".equals(element.getName())) {
            throw new InvalidMybatisGeneratorConfigException("not a table element.");
        }
        String name = element.attributeValue("tableName");
        if (TextUtil.isEmpty(name)) {
            throw new InvalidMybatisGeneratorConfigException("tableName is empty.");
        }
        Node node = element.selectSingleNode("property[@name='enable']");
        if (node == null) {
            return new TableElement(name, false);
        }
        String v = ((Element)node).attributeValue("value");
        if (!"true".equals(v) && !"false".equals(v)) {
            throw new InvalidMybatisGeneratorConfigException("table is misconfigured.");
        }
        return new TableElement(name, Boolean.valueOf(v));
    }

    @Override
    public int compareTo(TableElement o) {
        return name.compareTo(o.getName());
    }
}
