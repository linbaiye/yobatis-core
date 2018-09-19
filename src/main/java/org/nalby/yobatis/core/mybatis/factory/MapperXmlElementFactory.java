package org.nalby.yobatis.core.mybatis.factory;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.XmlElement;

public interface MapperXmlElementFactory {

    XmlElement insertIgnore(IntrospectedTable table);

    XmlElement insertAll(IntrospectedTable table);

    XmlElement insertAllIgnore(IntrospectedTable table);

    XmlElement selectByPkOfAllKeyTable(IntrospectedTable introspectedTable);

    XmlElement updateOfAllKeyTable(IntrospectedTable introspectedTable);

    XmlElement updateAllOfAllKeyTable(IntrospectedTable introspectedTable);

    XmlElement ifElement(String testClause, String text);

    XmlElement pagingElement(String id);

    XmlElement include(String refid);

    XmlElement convert(org.dom4j.Element element);

}
