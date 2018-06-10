package org.nalby.yobatis.core.mybatis.factory;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class MapperXmlElementFactoryImpl implements MapperXmlElementFactory {

    private final static String PARAM_TYPE = "parameterType";

    private final static MapperXmlElementFactoryImpl instance = new MapperXmlElementFactoryImpl();

    private MapperXmlElementFactoryImpl() { }

    public static MapperXmlElementFactoryImpl getInstance() {
        return instance;
    }

    private XmlElement updateElement(String name) {
        XmlElement xmlElement = new XmlElement("update");
        xmlElement.addAttribute(new Attribute("id", name));
        return xmlElement;
    }

    private XmlElement insertElement(String name) {
        XmlElement xmlElement = new XmlElement("insert");
        xmlElement.addAttribute(new Attribute("id", name));
        return xmlElement;
    }

    private XmlElement selectElement(String name) {
        XmlElement xmlElement = new XmlElement("select");
        xmlElement.addAttribute(new Attribute("id", name));
        return xmlElement;
    }

    private void addXmlComment(XmlElement xmlElement) {
        xmlElement.addElement(new TextElement("<!--"));
        xmlElement.addElement(new TextElement("  WARNING - @mbg.generated"));
        xmlElement.addElement(new TextElement("  This element is automatically generated by MyBatis Generator, do not modify."));
        xmlElement.addElement(new TextElement("-->"));
    }

    @Override
    public XmlElement ifElement(String testClause, String text) {
        XmlElement xmlElement = new XmlElement("if");
        Attribute attribute = new Attribute("test", testClause);
        xmlElement.addAttribute(attribute);
        TextElement textElement = new TextElement(text);
        xmlElement.addElement(textElement);
        return xmlElement;
    }

    private XmlElement insertAll(IntrospectedTable table, boolean ignore) {
        XmlElement xmlElement = insertElement("insertAll");
        if (ignore) {
            xmlElement = insertElement("insertAllIgnore");
        }
        xmlElement.addAttribute(new Attribute(PARAM_TYPE, table.getBaseRecordType()));
        addXmlComment(xmlElement);
        StringBuilder stringBuilder = new StringBuilder();
        if (!ignore) {
            stringBuilder.append("insert into ");
        } else {
            stringBuilder.append("insert ignore into ");
        }
        stringBuilder.append(table.getFullyQualifiedTable().getIntrospectedTableName());
        stringBuilder.append(" (");
        for (int i = 0; i < table.getAllColumns().size(); i++) {
            IntrospectedColumn column = table.getAllColumns().get(i);
            stringBuilder.append(column.getActualColumnName());
            stringBuilder.append(i == table.getAllColumns().size() - 1? ")" : ", ");
            if (i != table.getAllColumns().size() - 1 && (i + 1) % 4 == 0) {
                if (stringBuilder.charAt(stringBuilder.length() - 1) == ' ') {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                }
                xmlElement.addElement(new TextElement(stringBuilder.toString()));
                stringBuilder = new StringBuilder();
                stringBuilder.append("  ");
            }
        }
        TextElement textElement = new TextElement(stringBuilder.toString());
        xmlElement.addElement(textElement);
        stringBuilder = new StringBuilder();
        stringBuilder.append("values (");
        for (int i = 0; i < table.getAllColumns().size(); i++) {
            IntrospectedColumn column = table.getAllColumns().get(i);
            stringBuilder.append("#{");
            stringBuilder.append(column.getJavaProperty());
            stringBuilder.append(",jdbcType=");
            stringBuilder.append(column.getJdbcTypeName());
            stringBuilder.append("}");
            stringBuilder.append(i == table.getAllColumns().size() - 1? ")" : ", ");
            if (i != table.getAllColumns().size() - 1 &&  (i + 1) % 4 == 0) {
                if (stringBuilder.charAt(stringBuilder.length() - 1) == ' ') {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                }
                xmlElement.addElement(new TextElement(stringBuilder.toString()));
                stringBuilder = new StringBuilder();
                stringBuilder.append("  ");
            }
        }
        textElement = new TextElement(stringBuilder.toString());
        xmlElement.addElement(textElement);
        return xmlElement;

    }

    @Override
    public XmlElement insertAll(IntrospectedTable table) {
        return insertAll(table, false);
    }

    @Override
    public XmlElement insertAllIgnore(IntrospectedTable table) {
        return insertAll(table, true);
    }

    private String whereClauseOfAllFields(IntrospectedTable introspectedTable) {
        StringBuilder builder = new StringBuilder("where ");
        for (int i = 0; i < introspectedTable.getAllColumns().size(); i++) {
            IntrospectedColumn column = introspectedTable.getAllColumns().get(i);
            builder.append(column.getActualColumnName());
            builder.append(" = #{");
            builder.append(column.getJavaProperty());
            builder.append(",jdbcType=");
            builder.append(column.getJdbcTypeName());
            builder.append("}");
            if (i < introspectedTable.getAllColumns().size() - 1) {
                builder.append(" and\n\t");
            }
        }
        return builder.toString();
    }

    @Override
    public XmlElement selectByPkOfAllKeyTable(IntrospectedTable introspectedTable) {
        XmlElement xmlElement = selectElement("selectByPk");
        xmlElement.addAttribute(new Attribute(PARAM_TYPE, Helper.getBaseModelType(introspectedTable.getBaseRecordType()).getFullyQualifiedName()));
        xmlElement.addAttribute(new Attribute("resultMap", "BASE_RESULT_MAP"));
        addXmlComment(xmlElement);
        xmlElement.addElement(new TextElement("select"));
        xmlElement.addElement(new TextElement("<include refid=\"BASE_COLUMN_LIST\" />"));
        xmlElement.addElement(new TextElement("from " + introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()));
        xmlElement.addElement(new TextElement(whereClauseOfAllFields(introspectedTable)));
        return xmlElement;
    }

    @Override
    public XmlElement updateOfAllKeyTable(IntrospectedTable introspectedTable) {
        XmlElement xmlElement = updateElement("update");
        xmlElement.addAttribute(new Attribute("parameterType", Helper.getBaseModelType(introspectedTable.getBaseRecordType()).getFullyQualifiedName()));
        addXmlComment(xmlElement);
        xmlElement.addElement(new TextElement("update " + introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()));
        XmlElement setElement = new XmlElement("set");
        for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
            setElement.addElement(ifElement(column.getJavaProperty() + " != null",
                    column.getActualColumnName() + " = #{" + column.getJavaProperty() + ",jdbcType=" + column.getJdbcTypeName() + "},"));
        }
        xmlElement.addElement(setElement);
        xmlElement.addElement(new TextElement(whereClauseOfAllFields(introspectedTable)));
        return xmlElement;
    }

    @Override
    public XmlElement updateAllOfAllKeyTable(IntrospectedTable introspectedTable) {
        XmlElement xmlElement = updateElement("updateAll");
        xmlElement.addAttribute(new Attribute(PARAM_TYPE, Helper.getBaseModelType(introspectedTable.getBaseRecordType()).getFullyQualifiedName()));
        addXmlComment(xmlElement);
        xmlElement.addElement(new TextElement("update " + introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()));
        StringBuilder builder = new StringBuilder("set ");
        for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
            builder.append(column.getActualColumnName());
            builder.append(" = #{");
            builder.append(column.getJavaProperty());
            builder.append(",jdbcType=");
            builder.append(column.getJdbcTypeName());
            builder.append("},\n\t");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.deleteCharAt(builder.length() - 1);
        builder.deleteCharAt(builder.length() - 1);
        xmlElement.addElement(new TextElement(builder.toString()));
        xmlElement.addElement(new TextElement(whereClauseOfAllFields(introspectedTable)));
        return xmlElement;
    }
}
