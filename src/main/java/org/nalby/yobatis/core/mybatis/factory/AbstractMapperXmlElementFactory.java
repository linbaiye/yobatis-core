package org.nalby.yobatis.core.mybatis.factory;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.XmlElement;

public interface AbstractMapperXmlElementFactory {

    XmlElement insertAll(IntrospectedTable table);

    XmlElement insertAllIgnore(IntrospectedTable table);

    XmlElement selectByPkOfAllKeyTable(IntrospectedTable introspectedTable);

    XmlElement updateOfAllKeyTable(IntrospectedTable introspectedTable);

    XmlElement updateAllOfAllKeyTable(IntrospectedTable introspectedTable);

    XmlElement ifElement(String testClause, String text);

    XmlElement pagingElement();

}
