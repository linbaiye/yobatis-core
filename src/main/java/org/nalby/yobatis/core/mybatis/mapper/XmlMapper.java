package org.nalby.yobatis.core.mybatis.mapper;

import org.dom4j.Comment;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.XmlFormatter;
import org.mybatis.generator.api.dom.DefaultXmlFormatter;
import org.mybatis.generator.api.dom.xml.*;
import org.nalby.yobatis.core.log.Logger;
import org.nalby.yobatis.core.log.LoggerFactory;
import org.nalby.yobatis.core.mybatis.YobatisIntrospectedTable;
import org.nalby.yobatis.core.exception.InvalidUnitException;
import org.nalby.yobatis.core.mybatis.GeneratorEntityResolver;
import org.nalby.yobatis.core.mybatis.YobatisUnit;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlMapper.class);


    @Override
    public String getPathToPut() {
        return pathToPut;
    }

    private boolean hasElement(org.dom4j.Element element) {
        for (Element el: document.getRootElement().getElements()) {
            if (el instanceof XmlElement) {
                for (Attribute attribute : ((XmlElement) el).getAttributes()) {
                    if ("id".equals(attribute.getName()) && attribute.getValue() != null &&
                            attribute.getValue().equals(element.attributeValue("id"))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void merge(String fileContent) throws InvalidUnitException {
        LOGGER.debug("Merging :{}.", this.pathToPut);
        try (InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes())) {
            SAXReader saxReader = new SAXReader();
            saxReader.setEntityResolver(GeneratorEntityResolver.ENTITY_RESOLVER);
            org.dom4j.Document document = saxReader.read(inputStream);
            for (Iterator<Node> nodeIterator = document.getRootElement().nodeIterator(); nodeIterator.hasNext();) {
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
                new DefaultXmlFormatter(),
                table.getPathForGeneratedFile(YobatisIntrospectedTable.ClassType.XML_MAPPER));
    }
}
