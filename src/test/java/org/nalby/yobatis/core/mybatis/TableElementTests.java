package org.nalby.yobatis.core.mybatis;

import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;
import org.nalby.yobatis.core.exception.InvalidMybatisGeneratorConfigException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TableElementTests {

    private DocumentFactory documentFactory = DocumentFactory.getInstance();

    private Element table;

    private Element property;

    @Before
    public void setup() {
        table = documentFactory.createElement("table");
        property = documentFactory.createElement("property");
        property.addAttribute("name", "enable");
        property.addAttribute("value", "true");
        table.add(property);
    }

    @Test
    public void fromElementOk() {
        table.addAttribute("tableName", "test");
        TableElement tableElement = TableElement.fromElement(table);
        assertEquals("test", tableElement.getName());
        assertTrue(tableElement.isEnabled());
        property.detach();
        tableElement = TableElement.fromElement(table);
        assertFalse(tableElement.isEnabled());
    }

    @Test(expected = InvalidMybatisGeneratorConfigException.class)
    public void fromElementWithoutName() {
        TableElement.fromElement(table);
    }

    @Test
    public void asElement() {
        TableElement tableElement = new TableElement("hello", true);
        assertEquals("<table tableName=\"hello\" modelType=\"flat\"><property name=\"enable\" value=\"true\"/></table>", tableElement.asElement().asXML());
    }

}
