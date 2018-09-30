package org.nalby.yobatis.core.mybatis.javadoc;

import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.nalby.yobatis.core.mybatis.method.ConstantMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDaoMethodJavaDocDecorator implements JavaDocDecorator<Method> {

    private static BaseDaoMethodJavaDocDecorator ourInstance =
            new BaseDaoMethodJavaDocDecorator();

    public static BaseDaoMethodJavaDocDecorator getInstance() {
        return ourInstance;
    }

    private BaseDaoMethodJavaDocDecorator() {}

    private final static Map<String, String[]> METHOD_TO_JAVADOC;

    static {
        METHOD_TO_JAVADOC = new HashMap<>();
        //insertAll(B record)
        METHOD_TO_JAVADOC.put(ConstantMethod.INSERT_ALL_SIGNATURE.getName() + ":record", new String[] {
                "/**",
                " * Insert all fields of the record into table, it throws an exception if the insertion fails.",
                " * @param record the record to insert.",
                " * @return 1 if inserted successfully.",
                " * @throws IllegalArgumentException if record is null.",
                " */"
        });


        //insertAllIgnore
        METHOD_TO_JAVADOC.put(ConstantMethod.INSERT_ALL_IGNORE_SIGNATURE.getName() + ":record", new String[] {
                "/**",
                " * Insert all fields of the record into table.",
                " * @param record the record to insert.",
                " * @return 1 if inserted successfully, 0 if the insertion fails.",
                " * @throws IllegalArgumentException if record is null.",
                " */"
        });

        //insert(B record);
        METHOD_TO_JAVADOC.put(ConstantMethod.INSERT_SIGNATURE.getName() + ":record", new String[] {
                "/**",
                " * Insert non-null fields into the table, it throws an exception if insertion fails. If the table has an auto_increment pk,",
                " * the primary key field will hold the generated key after insertion.",
                " * @param record the record to insert.",
                " * @return 1 if inserted successfully.",
                " * @throws IllegalArgumentException if record is null.",
                " */"
        });

        //selectOne(PK pk);
        METHOD_TO_JAVADOC.put(ConstantMethod.SELECT_ONE_SIGNATURE.getName() + ":pk", new String[] {
                "/**",
                " * Select a record by primary key.",
                " * @param pk the primary key.",
                " * @return the record if found, null else.",
                " * @throws IllegalArgumentException if pk is null.",
                "*/",
        });

        //T selectOne(BaseCriteria criteria);
        METHOD_TO_JAVADOC.put(ConstantMethod.SELECT_ONE_BY_CRITERIA_SIGNATURE.getName() + ":criteria", new String[] {
                "/**",
                " * Select a record by criteria, a MybatisSystemException will be thrown if",
                " * more than one records meet the criteria.",
                " * @param criteria the criteria.",
                " * @return the record if one single record is selected, null if canNotDecide selected.",
                " * @throws IllegalArgumentException if criteria is null or empty.",
                " * @throws MybatisSystemException if more than one records are yielded by the criteria.",
                "*/",
        });

        //    List<T> selectList(BaseCriteria criteria);
        METHOD_TO_JAVADOC.put(ConstantMethod.SELECT_LIST_SIGNATURE.getName() + ":criteria", new String[] {
                "/**",
                " * Select records by criteria.",
                " * @param criteria the criteria.",
                " * @return a list of selected records, or an empty list if none meets the criteria.",
                " * @throws IllegalArgumentException if criteria is null or empty.",
                " */",
        });

        METHOD_TO_JAVADOC.put(ConstantMethod.COUNT_ALL_SIGNATURE.getName(), new String[] {
                "/**",
                " * Count row number of the whole table.",
                " * @return the row number.",
                " */",
        });

        // count(BaseCriteria criteria);
        METHOD_TO_JAVADOC.put(ConstantMethod.COUNT_BY_CRITERIA_SIGNATURE.getName() + ":criteria", new String[] {
                "/**",
                " * Count row number by criteria.",
                " * @param criteria the criteria to query rows.",
                " * @return the number of rows that meet the criteria.",
                " */",
        });

        //int update(B record);
        METHOD_TO_JAVADOC.put(ConstantMethod.UPDATE_SIGNATURE.getName() + ":record", new String[] {
                "/**",
                " * Update the record by primary key, null fields are ignored.",
                " * @param record the record that holds new values and the primary key.",
                " * @return 1 if updated successfully, 0 if no such a record.",
                " * @throws IllegalArgumentException if record is null.",
                " */",
        });

        //int updateAll(B record);
        METHOD_TO_JAVADOC.put(ConstantMethod.UPDATE_ALL_SIGNATURE.getName() + ":record", new String[] {
                "/**",
                " * Update the record by primary key, all fields including null ones will be updated.",
                " * @param record the record that holds new values and the primary key.",
                " * @return 1 if updated successfully, 0 if no such a record.",
                " * @throws IllegalArgumentException if record is null.",
                " */",
        });

        // int update(B record, BaseCriteria criteria);
        METHOD_TO_JAVADOC.put(ConstantMethod.UPDATE_BY_CRITERIA_SIGNATURE.getName() + ":record:criteria", new String[] {
                "/**",
                " * Update non-null fields of the {@code record} to corresponding columns of the table.",
                " * @param record the new values.",
                " * @param criteria to query rows to update.",
                " * @return the number of affected rows.",
                " */",
        });


        // int updateAll(B record, BaseCriteria criteria);
        METHOD_TO_JAVADOC.put(ConstantMethod.UPDATE_ALL_BY_CRITERIA_SIGNATURE.getName() + ":record:criteria", new String[] {
                "/**",
                " * Update all fields of the {@code record} to corresponding columns of the table.",
                " * @param record the new values.",
                " * @param criteria to query rows to update.",
                " * @return the number of affected rows.",
                " */",
        });

        METHOD_TO_JAVADOC.put(ConstantMethod.DELETE_SIGNATURE.getName() + ":pk", new String[] {
                "/**",
                " * Delete a record by primary key.",
                " * @param pk the primary key.",
                " * @return 1 if deleted successfully, 0 if no such a record.",
                " * @throws IllegalArgumentException if pk is null.",
                " */",
        });


        METHOD_TO_JAVADOC.put(ConstantMethod.DELETE_BY_CRITERIA_SIGNATURE.getName() + ":criteria", new String[] {
                "/**",
                " * Delete records by criteria.",
                " * @param criteria the criteria.",
                " * @return the number of deleted records.",
                " * @throws IllegalArgumentException if criteria is null or empty.",
                " */",
        });
    }

    @Override
    public void decorate(Method method) {
        String key = method.getName();
        List<Parameter> list = method.getParameters();
        if (list != null) {
            for (Parameter parameter : list) {
                key = key + ":" + parameter.getName();
            }
        }
        if (!METHOD_TO_JAVADOC.containsKey(key)) {
            return;
        }
        String[] docs = METHOD_TO_JAVADOC.get(key);
        for (String doc : docs) {
            method.addJavaDocLine(doc);
        }
    }
}
