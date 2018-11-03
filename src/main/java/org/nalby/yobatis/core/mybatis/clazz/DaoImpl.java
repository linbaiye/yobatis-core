package org.nalby.yobatis.core.mybatis.clazz;

import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.nalby.yobatis.core.mybatis.YobatisIntrospectedTable;
import org.nalby.yobatis.core.exception.InvalidUnitException;
import org.nalby.yobatis.core.mybatis.YobatisUnit;
import org.nalby.yobatis.core.mybatis.field.FieldFactoryImpl;
import org.nalby.yobatis.core.mybatis.method.DaoImplMethodFactory;
import org.nalby.yobatis.core.mybatis.method.DaoMethodName;

public class DaoImpl extends TopLevelClass implements YobatisUnit {

    private String pathToPut;

    private String currentFileContent;

    private DaoImpl(FullyQualifiedJavaType type, String pathToPut) {
        super(type);
        this.pathToPut = pathToPut;
        setVisibility(JavaVisibility.PUBLIC);
    }

    @Override
    public String getPathToPut() {
        return pathToPut;
    }

    @Override
    public void merge(String fileContent) throws InvalidUnitException {
        currentFileContent = fileContent;
    }

    @Override
    public String getFormattedContent() {
        if (currentFileContent != null) {
            return currentFileContent;
        }
        return super.getFormattedContent();
    }

    public static DaoImpl newInstance(YobatisIntrospectedTable tableItem) {
        DaoImpl daoImpl = new DaoImpl(tableItem.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.DAO_IMPL),
                tableItem.getPathForGeneratedFile(YobatisIntrospectedTable.ClassType.DAO_IMPL));

        Field field = FieldFactoryImpl.getInstance().privateField("NAMESPACE", "String");
        field.setInitializationString("\"" + tableItem.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.DAO_IMPL).getFullyQualifiedName() + "." + "\"");
        field.setFinal(true);
        field.setStatic(true);
        daoImpl.addField(field);

        field = FieldFactoryImpl.getInstance().privateField("sqlSessionTemplate", "SqlSessionTemplate");
        field.addAnnotation("@Resource");
        daoImpl.addField(field);

        String shortName = tableItem.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.DAO).getShortName();
        String beanName = shortName.substring(0, 1).toLowerCase() + shortName.substring(1, shortName.length());
        daoImpl.addAnnotation("@Component(\"" + beanName + "\")");

        DaoImplMethodFactory methodFactory = DaoImplMethodFactory.getInstance(tableItem);
        for (String name: DaoMethodName.listMethodNamesByGroup(DaoMethodName.DAO_IMPL_GROUP)) {
            daoImpl.addMethod(methodFactory.create(name));
        }


        daoImpl.addSuperInterface(tableItem.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.DAO));
        daoImpl.addImportedType(tableItem.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.DAO));
        daoImpl.addImportedType(new FullyQualifiedJavaType("java.util.Map"));
        daoImpl.addImportedType(new FullyQualifiedJavaType("java.util.HashMap"));
        daoImpl.addImportedType(new FullyQualifiedJavaType("java.util.List"));
        daoImpl.addImportedType(tableItem.getPrimaryKey());
        daoImpl.addImportedType(tableItem.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.ENTITY));
        daoImpl.addImportedType(tableItem.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.BASE_ENTITY));
        daoImpl.addImportedType(tableItem.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.CRITERIA));
        daoImpl.addImportedType(new FullyQualifiedJavaType("javax.annotation.Resource"));
        daoImpl.addImportedType(new FullyQualifiedJavaType("org.mybatis.spring.SqlSessionTemplate"));
        daoImpl.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Component"));
        return daoImpl;
    }
}
