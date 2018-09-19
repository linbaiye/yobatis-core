package org.nalby.yobatis.core.mybatis.method;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

public final class DaoMethodSignatureFactory implements DaoMethodFactory {

    private static final DaoMethodSignatureFactory factory = new DaoMethodSignatureFactory();

    private DaoMethodSignatureFactory() {}

    public static DaoMethodSignatureFactory getInstance() {
        return factory;
    }

    private Method createMethod(String name, String returnType) {
        Method method = new Method(name);
        method.setReturnType(new FullyQualifiedJavaType(returnType));
        return method;
    }

    @Override
    public Method insertAll() {
        Method method = createMethod("insertAll", "int");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("B"), "record"));
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Insert all fields of the record into table.");
        method.addJavaDocLine(" * @param record the record to insert.");
        method.addJavaDocLine(" * @return 1 if inserted successfully.");
        method.addJavaDocLine(" * @throws IllegalArgumentException if record is null.");
        method.addJavaDocLine(" */");
        return method;
    }

    @Override
    public Method insertAllIgnore() {
        Method method = createMethod("insertAllIgnore", "int");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("B"), "record"));
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Insert all fields of the record into table.");
        method.addJavaDocLine(" * @param record the record to insert.");
        method.addJavaDocLine(" * @return 1 if inserted successfully, 0 if the insertion can not be done.");
        method.addJavaDocLine(" * @throws IllegalArgumentException if record is null.");
        method.addJavaDocLine(" */");
        return method;
    }

    @Override
    public Method insert() {
        Method method = createMethod("insert", "int");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("B"), "record"));
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Insert non-null fields into the table. If the table has an auto_increment pk,");
        method.addJavaDocLine(" * the primary key field will hold the generated key after insertion.");
        method.addJavaDocLine(" * @param record the record to insert.");
        method.addJavaDocLine(" * @return 1 if inserted successfully.");
        method.addJavaDocLine(" * @throws IllegalArgumentException if record is null.");
        method.addJavaDocLine(" */");
        return method;
    }

    @Override
    public Method selectOne() {
        Method method = createMethod("selectOne", "T");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("PK"), "pk"));
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Select a record by primary key.");
        method.addJavaDocLine(" * @param pk the primary key.");
        method.addJavaDocLine(" * @return the record if found, null else.");
        method.addJavaDocLine(" * @throws IllegalArgumentException if pk is null.");
        method.addJavaDocLine(" */");
        return method;
    }

    @Override
    public Method selectOneByCriteria() {
        Method method = createMethod("selectOne", "T");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("BaseCriteria"), "criteria"));
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Select a record by criteria, a MybatisSystemException will be thrown if");
        method.addJavaDocLine(" * more than one records meet the criteria.");
        method.addJavaDocLine(" * @param criteria the criteria.");
        method.addJavaDocLine(" * @return the record if one single record is selected, null if none selected.");
        method.addJavaDocLine(" * @throws IllegalArgumentException if criteria is null or empty.");
        method.addJavaDocLine(" * @throws MybatisSystemException if more than one records are yielded by the criteria.");
        method.addJavaDocLine(" */");
        return method;
    }

    @Override
    public Method selectList() {
        Method method = createMethod("selectList", "List<T>");
        Parameter parameter = new Parameter(new FullyQualifiedJavaType("BaseCriteria"), "criteria");
        method.addParameter(parameter);

        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Select records by criteria.");
        method.addJavaDocLine(" * @param criteria the criteria.");
        method.addJavaDocLine(" * @return a list of selected records, or an empty list if none meets the criteria.");
        method.addJavaDocLine(" * @throws IllegalArgumentException if criteria is null or empty.");
        method.addJavaDocLine(" */");
        return method;
    }

    @Override
    public Method countAll() {
        Method method = createMethod("countAll", "long");
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Count row number of the whole table.");
        method.addJavaDocLine(" * @return the row number.");
        method.addJavaDocLine(" */");
        return method;
    }

    @Override
    public Method countByCriteria() {
        Method method = createMethod("count", "long");
        Parameter parameter = new Parameter(new FullyQualifiedJavaType("BaseCriteria"),
                "criteria");
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Count row number by criteria.");
        method.addJavaDocLine(" * @param criteria the criteria to query rows.");
        method.addJavaDocLine(" * @return the number of rows that meet the criteria.");
        method.addJavaDocLine(" */");
        method.addParameter(parameter);
        return method;
    }

    @Override
    public Method update() {
        Method method = createMethod("update", "int");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("B"), "record"));
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Update the record by primary key, null fields are ignored.");
        method.addJavaDocLine(" * @param record the record that holds new values and the primary key.");
        method.addJavaDocLine(" * @return 1 if updated successfully, 0 if no such a record.");
        method.addJavaDocLine(" * @throws IllegalArgumentException if record is null.");
        method.addJavaDocLine(" */");
        return method;
    }

    @Override
    public Method updateAll() {
        Method method = createMethod("updateAll", "int");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("B"), "record"));
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Update the record by primary key, all fields including null ones will be updated.");
        method.addJavaDocLine(" * @param record the record that holds new values and the primary key.");
        method.addJavaDocLine(" * @return 1 if updated successfully, 0 if no such a record.");
        method.addJavaDocLine(" * @throws IllegalArgumentException if record is null.");
        method.addJavaDocLine(" */");
        return method;
    }

    @Override
    public Method updateByCriteria() {
        Method method = createMethod("update", "int");
        Parameter parameter = new Parameter(new FullyQualifiedJavaType("B"), "record");
        method.addParameter(parameter);
        parameter = new Parameter(new FullyQualifiedJavaType("BaseCriteria"),
                "criteria");
        method.addParameter(parameter);
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Update non-null fields of the {@code record} to corresponding columns of the table.");
        method.addJavaDocLine(" * @param record the new values.");
        method.addJavaDocLine(" * @param criteria to query rows to update.");
        method.addJavaDocLine(" * @return the number of affected rows.");
        method.addJavaDocLine(" */");
        return method;
    }

    @Override
    public Method updateAllByCriteria() {
        Method method = createMethod("updateAll", "int");
        Parameter parameter = new Parameter(new FullyQualifiedJavaType("B"), "record");
        method.addParameter(parameter);
        parameter = new Parameter(new FullyQualifiedJavaType("BaseCriteria"),
                "criteria");
        method.addParameter(parameter);
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Update all fields of the {@code record} to corresponding columns of the table.");
        method.addJavaDocLine(" * @param record the new values.");
        method.addJavaDocLine(" * @param criteria to query rows to update.");
        method.addJavaDocLine(" * @return the number of affected rows.");
        method.addJavaDocLine(" */");
        return method;
    }

    @Override
    public Method delete() {
        Method method = createMethod("delete", "int");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("PK"), "pk"));
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Delete the record by primary key.");
        method.addJavaDocLine(" * @param pk the primary key.");
        method.addJavaDocLine(" * @return 1 if deleted successfully, 0 if no such a record.");
        method.addJavaDocLine(" * @throws IllegalArgumentException if pk is null.");
        method.addJavaDocLine(" */");
        return method;
    }

    @Override
    public Method deleteByCriteria() {
        Method method = createMethod("delete", "int");
        Parameter parameter = new Parameter(new FullyQualifiedJavaType("BaseCriteria"),
                "criteria");
        method.addParameter(parameter);

        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Delete records by criteria.");
        method.addJavaDocLine(" * @param criteria the criteria.");
        method.addJavaDocLine(" * @return the number of deleted records.");
        method.addJavaDocLine(" * @throws IllegalArgumentException if criteria is null or empty.");
        method.addJavaDocLine(" */");
        return method;
    }
}
