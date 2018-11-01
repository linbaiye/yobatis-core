package org.nalby.yobatis.core.mybatis.mapper;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.XmlFormatter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.nalby.yobatis.core.database.YobatisIntrospectedTable;
import org.nalby.yobatis.core.exception.InvalidUnitException;
import org.nalby.yobatis.core.mybatis.YobatisUnit;

import java.beans.XMLDecoder;

public class XmlMapper extends GeneratedXmlFile implements YobatisUnit  {

    private String pathToPut;
    private Document document;
    private XmlMapper(Document document,
                      String fileName,
                      String targetPackage,
                      String targetProject,
                      boolean isMergeable,
                      XmlFormatter xmlFormatter,
                      String pathToPut) {
        super(document, fileName, targetPackage, targetProject, isMergeable, xmlFormatter);
        this.document = document;
        this.pathToPut = pathToPut;
    }

    @Override
    public String getPathToPut() {
        return pathToPut;
    }
    private boolean hasElement(org.dom4j.Element element) {
        for (Element el: document.getRootElement().getElements()) {
            if (el instanceof XmlElement) {
                for (Attribute attribute : ((XmlElement) el).getAttributes()) {
                    if ("id".equals(attribute.getName()) && attribute.getValue() != null &&
                            element.attribute("id") != null &&
                            attribute.getValue().equals(element.attribute("id").getValue())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void merge(String fileContent) throws InvalidUnitException {


    }

    public static XmlMapper wrap(YobatisIntrospectedTable table) {
        Document document = new Document("-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
        XmlElement root = new XmlElement("mapper");
        root.addAttribute(new Attribute("namespace",
                table.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.DAO_IMPL).getFullyQualifiedName()));
        document.setRootElement(root);
        MapperXmlElementFactory factory = MapperXmlElementFactoryImpl.getInstance(table);
        for (XmlElementName xmlElementName : XmlElementName.values()) {
            root.addElement(factory.create(xmlElementName.getName()));
        }
        return new XmlMapper(document, table.getFullyQualifiedJavaType(
                YobatisIntrospectedTable.ClassType.ENTITY).getShortName() + "Mapper.xml",
                null, null, false,
                table.getWrappedTable().getContext().getXmlFormatter(),
                table.getPathForGeneratedFile(YobatisIntrospectedTable.ClassType.XML_MAPPER));
    }
}
