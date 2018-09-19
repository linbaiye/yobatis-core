package func;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.PrimitiveType;

import java.util.List;
import java.util.Optional;

public class Parser {

    public static void main(String[] args) {
        CompilationUnit compilationUnit = JavaParser.parse("package func.alltype.dao.impl;\n" +
                "\n" +
                "import func.alltype.dao.BaseDao;\n" +
                "import func.alltype.model.criteria.BaseCriteria;\n" +
                "import java.util.HashMap;\n" +
                "import java.util.List;\n" +
                "import java.util.Map;\n" +
                "import javax.annotation.Resource;\n" +
                "import org.mybatis.spring.SqlSessionTemplate;\n" +
                "\n" +
                "abstract class BaseDaoImpl<B, T extends B, PK> implements BaseDao<B, T, PK> {\n" +
                "    @Resource\n" +
                "    protected SqlSessionTemplate sqlSessionTemplate;\n" +
                "\n" +
                "    protected abstract String namespace();\n" +
                "\n" +
                "    protected final T doSelectOne(String statement, Object parameter) {\n" +
                "        return sqlSessionTemplate.selectOne(namespace() + statement, parameter);\n" +
                "    }\n" +
                "\n" +
                "    protected final List<T> doSelectList(String statement, Object parameter) {\n" +
                "        return sqlSessionTemplate.selectList(namespace() + statement, parameter);\n" +
                "    }\n" +
                "\n" +
                "    protected final int doUpdate(String statement, Object parameter) {\n" +
                "        return sqlSessionTemplate.update(namespace() + statement, parameter);\n" +
                "    }\n" +
                "\n" +
                "    protected final int doInsert(String statement, Object parameter) {\n" +
                "        return sqlSessionTemplate.insert(namespace() + statement, parameter);\n" +
                "    }\n" +
                "\n" +
                "    protected final int doDelete(String statement, Object parameter) {\n" +
                "        return sqlSessionTemplate.delete(namespace() + statement, parameter);\n" +
                "    }\n" +
                "\n" +
                "    protected final void notNull(Object object, String errMsg) {\n" +
                "        if (object == null) {\n" +
                "            throw new IllegalArgumentException(errMsg);\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    protected final void validateCriteria(BaseCriteria criteria) {\n" +
                "        if (criteria == null || criteria.getOredCriteria().isEmpty()) {\n" +
                "            throw new IllegalArgumentException(\"criteria must not be empty.\");\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    protected final Map<String, Object> makeParam(B record, BaseCriteria criteria) {\n" +
                "        notNull(record, \"record must not be null.\");\n" +
                "        validateCriteria(criteria);\n" +
                "        Map<String, Object> param = new HashMap<>();\n" +
                "        param.put(\"record\", record);\n" +
                "        param.put(\"example\", criteria);\n" +
                "        return param;\n" +
                "    }\n" +
                "\n" +
                "    public final int insertAll(B record) {\n" +
                "        notNull(record, \"record must not be null.\");\n" +
                "        return doInsert(\"insertAll\", record);\n" +
                "    }\n" +
                "\n" +
                "    public final int insertAllIgnore(B record) {\n" +
                "        notNull(record, \"record must not be null.\");\n" +
                "        return doInsert(\"insertAllIgnore\", record);\n" +
                "    }\n" +
                "\n" +
                "    public final int insert(B record) {\n" +
                "        notNull(record, \"record must not be null.\");\n" +
                "        return doInsert(\"insert\", record);\n" +
                "    }\n" +
                "\n" +
                "    public final T selectOne(PK pk) {\n" +
                "        notNull(pk, \"Primary key must not be null.\");\n" +
                "        return doSelectOne(\"selectByPk\", pk);\n" +
                "    }\n" +
                "\n" +
                "    public final T selectOne(BaseCriteria criteria) {\n" +
                "        validateCriteria(criteria);\n" +
                "        return doSelectOne(\"selectByCriteria\", criteria);\n" +
                "    }\n" +
                "\n" +
                "    public final List<T> selectList(BaseCriteria criteria) {\n" +
                "        validateCriteria(criteria);\n" +
                "        return doSelectList(\"selectByCriteria\", criteria);\n" +
                "    }\n" +
                "\n" +
                "    public final long countAll() {\n" +
                "        return sqlSessionTemplate.selectOne(namespace() + \"countAll\", null);\n" +
                "    }\n" +
                "\n" +
                "    public final long count(BaseCriteria criteria) {\n" +
                "        validateCriteria(criteria);\n" +
                "        return sqlSessionTemplate.selectOne(namespace() + \"count\", criteria);\n" +
                "    }\n" +
                "\n" +
                "    public final int update(B record) {\n" +
                "        notNull(record, \"record must no be null.\");\n" +
                "        return doUpdate(\"update\", record);\n" +
                "    }\n" +
                "\n" +
                "    public final int updateAll(B record) {\n" +
                "        notNull(record, \"record must no be null.\");\n" +
                "        return doUpdate(\"updateAll\", record);\n" +
                "    }\n" +
                "\n" +
                "    public final int update(B record, BaseCriteria criteria) {\n" +
                "        return doUpdate(\"updateByCriteria\", makeParam(record, criteria));\n" +
                "    }\n" +
                "\n" +
                "    public final int updateAll(B record, BaseCriteria criteria) {\n" +
                "        return doUpdate(\"updateAllByCriteria\", makeParam(record, criteria));\n" +
                "    }\n" +
                "\n" +
                "    public final int delete(PK pk) {\n" +
                "        notNull(pk, \"record must no be null.\");\n" +
                "        return doDelete(\"deleteByPk\", pk);\n" +
                "    }\n" +
                "\n" +
                "    public final int delete(BaseCriteria criteria) {\n" +
                "        validateCriteria(criteria);\n" +
                "        return doDelete(\"deleteByCriteria\", criteria);\n" +
                "    }\n" +
                "}");
        Optional<ClassOrInterfaceDeclaration> baseDaoImpl = compilationUnit.getClassByName("BaseDaoImpl");
        if (!baseDaoImpl.isPresent()) {
            return;
        }
        ClassOrInterfaceDeclaration classOrInterfaceDeclaration = baseDaoImpl.get();
        MethodDeclaration methodDeclaration = new MethodDeclaration(Modifier.PUBLIC.toEnumSet(), new PrimitiveType(PrimitiveType.Primitive.BOOLEAN), "ssssssssssssssshello");
        BlockStmt blockStmt = new BlockStmt();
        blockStmt.addStatement("return true;");
        methodDeclaration.setBody(blockStmt);
        classOrInterfaceDeclaration.addMember(methodDeclaration);
        List<MethodDeclaration> methodDeclarationList = classOrInterfaceDeclaration.getMethods();
        for (MethodDeclaration methodDeclaration1 : methodDeclarationList) {
            System.out.println(methodDeclaration1.getDeclarationAsString());
        }
    }
}
