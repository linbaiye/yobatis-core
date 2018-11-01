package org.nalby.yobatis.core.mybatis.mapper;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.XmlFormatter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.nalby.yobatis.core.database.YobatisIntrospectedTable;
import org.nalby.yobatis.core.exception.InvalidUnitException;
import org.nalby.yobatis.core.mybatis.YobatisUnit;
import org.nalby.yobatis.core.mybatis.mapper.legacy.LegacyXmlMapper;

import java.util.Arrays;
import java.util.List;

public class XmlMapperProxy extends GeneratedXmlFile implements YobatisUnit {

    private boolean legacyExist;

    private LegacyXmlMapper legacyXmlMapper;

    private XmlMapper xmlMapper;

    private final static List<String> LEGACY_KEYWORDS = Arrays.asList("selectByCriteria", "selectByPk", "deleteByPk",
                "deleteByCriteria", "insert", "insertAll", "insertAllIgnore",
                "updateByCriteria", "updateAllByCriteria", "update", "updateAll");

    private XmlMapperProxy(LegacyXmlMapper legacyXmlMapper, XmlMapper xmlMapper) {
        super(null, null, null,
                null, false,
                null);
        this.legacyExist = false;
        this.legacyXmlMapper = legacyXmlMapper;
        this.xmlMapper = xmlMapper;
    }

    @Override
    public String getPathToPut() {
        if (legacyExist) {
            return legacyXmlMapper.getPathToPut();
        }
        return xmlMapper.getPathToPut();
    }

    private boolean isLegacy(String fileContent) {
        for (String legacyKeyword : LEGACY_KEYWORDS) {
            if (!fileContent.contains(legacyKeyword)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void merge(String fileContent) throws InvalidUnitException {
        if (isLegacy(fileContent)) {
            legacyExist = true;
            legacyXmlMapper.merge(fileContent);
        } else {
            xmlMapper.merge(fileContent);
        }
    }

    @Override
    public String getFormattedContent() {
        if (legacyExist) {
            return legacyXmlMapper.getFormattedContent();
        } else {
            return xmlMapper.getFormattedContent();
        }
    }

    public static XmlMapperProxy wrap(Document document, YobatisIntrospectedTable table) {
        return new XmlMapperProxy(LegacyXmlMapper.wrap(document, table.getWrappedTable()),
                XmlMapper.wrap(table));
    }
}
