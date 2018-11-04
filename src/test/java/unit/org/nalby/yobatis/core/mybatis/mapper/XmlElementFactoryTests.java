package unit.org.nalby.yobatis.core.mybatis.mapper;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.nalby.yobatis.core.mybatis.YobatisIntrospectedTable;
import org.nalby.yobatis.core.mybatis.mapper.MapperXmlElementFactory;
import org.nalby.yobatis.core.mybatis.mapper.MapperXmlElementFactoryImpl;
import org.nalby.yobatis.core.mybatis.mapper.XmlElementName;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class XmlElementFactoryTests {

    private YobatisIntrospectedTable yobatisIntrospectedTable;

    private List<IntrospectedColumn> columnList;

    private MapperXmlElementFactory factory;

    private List<IntrospectedColumn> pkColumnList;

    @Before
    public void setup() {
        yobatisIntrospectedTable = mock(YobatisIntrospectedTable.class);
        when(yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.ENTITY))
                .thenReturn(new FullyQualifiedJavaType("org.yobatis.entity.Yobatis"));
        when(yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.CRITERIA))
                .thenReturn(new FullyQualifiedJavaType("org.yobatis.entity.criteria.YobatisCriteria"));
        columnList = new LinkedList<>();
        when(yobatisIntrospectedTable.getColumns()).thenReturn(columnList);
        pkColumnList = new LinkedList<>();
        when(yobatisIntrospectedTable.getPrimaryKeyColumns()).thenReturn(pkColumnList);

        when(yobatisIntrospectedTable.getTableName()).thenReturn("yobatis");

        factory = MapperXmlElementFactoryImpl.getInstance(yobatisIntrospectedTable);
    }

    private IntrospectedColumn addColumn(boolean id, String name, String jdbcType, String javaProperty) {
        IntrospectedColumn column = mock(IntrospectedColumn.class);
        when(column.isIdentity()).thenReturn(id);
        when(column.getActualColumnName()).thenReturn(name);
        when(column.getJdbcTypeName()).thenReturn(jdbcType);
        when(column.getJavaProperty()).thenReturn(javaProperty);
        columnList.add(column);
        return column;
    }

    private IntrospectedColumn addPk(String name, String jdbcType, String javaProperty) {
        IntrospectedColumn column = mock(IntrospectedColumn.class);
        when(column.getActualColumnName()).thenReturn(name);
        when(column.getJdbcTypeName()).thenReturn(jdbcType);
        when(column.getJavaProperty()).thenReturn(javaProperty);
        pkColumnList.add(column);
        return column;
    }

    @Test
    public void baseResultMap() {
        addColumn(true, "pk", "INT", "pk");
        addColumn(true, "pk_ext", "CHAR", "pkExt");
        XmlElement element = factory.create(XmlElementName.BASE_RESULT_MAP.getName());
        String result = "<resultMap id=\"BASE_RESULT_MAP\" type=\"org.yobatis.entity.Yobatis\">\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                "  <id column=\"pk\" jdbcType=\"INT\" property=\"pk\" />\n" +
                "  <id column=\"pk_ext\" jdbcType=\"CHAR\" property=\"pkExt\" />\n" +
                "</resultMap>";
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));
        result = "<resultMap id=\"BASE_RESULT_MAP\" type=\"org.yobatis.entity.Yobatis\">\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                "  <id column=\"pk\" jdbcType=\"INT\" property=\"pk\" />\n" +
                "  <id column=\"pk_ext\" jdbcType=\"CHAR\" property=\"pkExt\" />\n" +
                "  <result column=\"field\" jdbcType=\"DECIMAL\" property=\"field\" />\n" +
                "</resultMap>";
        addColumn(false, "field", "DECIMAL", "field");
        element = factory.create(XmlElementName.BASE_RESULT_MAP.getName());

        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));
    }

    @Test
    public void paging() {
        String result = "  <sql id=\"PAGING\">\n" +
                "    <!--\n" +
                "      WARNING - @mbg.generated\n" +
                "      This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "    -->\n" +
                "    <if test=\"limit != null\">\n" +
                "      limit #{limit}\n" +
                "    </if>\n" +
                "    <if test=\"offset != null\">\n" +
                "      offset #{offset}\n" +
                "    </if>\n" +
                "  </sql>";
        XmlElement element = factory.create(XmlElementName.PAGING.getName());
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));
    }

    @Test
    public void baseColumnList() {
        addColumn(true, "pk", "INT", "pk");
        for (int i = 1; i <= 4; i++) {
            addColumn(false, "field" + i, "DECIMAL", "javaField" +i);
        }
        XmlElement element = factory.create(XmlElementName.BASE_COLUMN_LIST.getName());
        String content = element.getFormattedContent(0);
        assertTrue(content.contains("pk, field1, field2, field3, field4"));
        assertFalse(content.contains("pk, field1, field2, field3, field4,"));

        for (int i = 5; i <= 12; i++) {
            addColumn(false, "field" + i, "DECIMAL", "javaField" +i);
        }
        element = factory.create(XmlElementName.BASE_COLUMN_LIST.getName());
        content = element.getFormattedContent(0);
        assertTrue(content.contains("pk, field1, field2, field3, field4,"));
        assertTrue(content.contains("field5, field6, field7, field8, field9,"));

        assertFalse(content.contains("pk, field1, field2, field3, field4, field5"));
    }

    @Test
    public void selectOneByPk() {
        addPk("pk1", "CHAR", "pk1");
        String result = "<select id=\"selectByPk\" parameterType=\"java.util.Long\" resultMap=\"BASE_RESULT_MAP\">\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                " <if test=\"_parameter != null\">" +
                "  select\n" +
                "  <include refid=\"BASE_COLUMN_LIST\" />\n" +
                "  from yobatis where\n" +
                "    pk1 = #{pk1,jdbcType=CHAR}\n" +
                "</if>" +
                "</select>";
        when(yobatisIntrospectedTable.getPrimaryKey()).thenReturn(new FullyQualifiedJavaType("java.util.Long"));
        XmlElement element = factory.create(XmlElementName.SELECT_BY_PK.getName());
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));

        when(yobatisIntrospectedTable.getPrimaryKey()).thenReturn(new FullyQualifiedJavaType("org.yobatis.Yobatis"));
        addPk("pk_ext", "INT", "pkExt");
        element = factory.create(XmlElementName.SELECT_BY_PK.getName());
        result = "<select id=\"selectByPk\" parameterType=\"org.yobatis.Yobatis\" resultMap=\"BASE_RESULT_MAP\">\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                " <if test=\"_parameter != null\">" +
                "  select\n" +
                "  <include refid=\"BASE_COLUMN_LIST\" />\n" +
                "  from yobatis where\n" +
                "    pk1 = #{pk1,jdbcType=CHAR}\n" +
                "    and pk_ext = #{pkExt,jdbcType=INT}\n" +
                "</if>" +
                "</select>";
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));
    }


    @Test
    public void selectByCriteria() {
        XmlElement element = factory.create(XmlElementName.SELECT_BY_CRITERIA.getName());
        String result = "<select id=\"selectByCriteria\" parameterType=\"org.yobatis.entity.criteria.YobatisCriteria\" resultMap=\"BASE_RESULT_MAP\">\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                "  <include refid=\"PRIVATE_SELECT_BY_CRITERIA\"/>\n" +
                "</select>";
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));
    }

    @Test
    public void privateSelectByCriteria() {
        XmlElement element = factory.create(XmlElementName.PRIVATE_SELECT_BY_CRITERIA.getName());
        String result = "<sql id=\"PRIVATE_SELECT_BY_CRITERIA\">\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                "  select\n" +
                "  <include refid=\"BASE_COLUMN_LIST\" />\n" +
                "  from yobatis\n" +
                "    <include refid=\"WHERE_CLAUSE\" />\n" +
                "  <if test=\"orderByClause != null\">\n" +
                "    order by ${orderByClause}\n" +
                "  </if>\n" +
                "  <include refid=\"PAGING\" />\n" +
                "  <if test=\"forUpdate\">\n" +
                "    for update\n" +
                "  </if>\n" +
                "</sql>";
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));
    }

    @Test
    public void selectList() {
        XmlElement element = factory.create(XmlElementName.SELECT_LIST.getName());
        String result = "<select id=\"selectList\" parameterType=\"org.yobatis.entity.criteria.YobatisCriteria\" resultMap=\"BASE_RESULT_MAP\">\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                "  <include refid=\"PRIVATE_SELECT_BY_CRITERIA\" />\n" +
                "</select>";
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));
    }

    @Test
    public void insertWithNoAutoIncKey() {
        addColumn(true, "pk", "INT", "pk");
        when(yobatisIntrospectedTable.isAutoIncPrimaryKey()).thenReturn(false);
        when(yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.BASE_ENTITY)).thenReturn(new FullyQualifiedJavaType("org.yobatis.base.BaseEntity"));
        for (int i = 1; i <= 4; i++) {
            addColumn(false, "field" + i, "INT", "javaField" +i);
        }
        XmlElement element = factory.create(XmlElementName.INSERT.getName());
        String result = "<insert id=\"insert\" parameterType=\"org.yobatis.base.BaseEntity\">\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                "  insert into yobatis\n" +
                "  (pk, field1, field2, field3, field4)\n" +
                "  values (#{pk,jdbcType=INT}, #{javaField1,jdbcType=INT}, #{javaField2,jdbcType=INT}, #{javaField3,jdbcType=INT}, #{javaField4,jdbcType=INT})\n" +
                "</insert>";
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));

        for (int i = 5; i <= 12; i++) {
            addColumn(false, "field" + i, "INT", "javaField" +i);
        }

        element = factory.create(XmlElementName.INSERT.getName());
        result = "<insert id=\"insert\" parameterType=\"org.yobatis.base.BaseEntity\">\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                "  insert into yobatis\n" +
                "  (pk, field1, field2, field3, field4, \n" +
                "  field5, field6, field7, field8, field9, \n" +
                "  field10, field11, field12)\n" +
                "  values (#{pk,jdbcType=INT}, #{javaField1,jdbcType=INT}, #{javaField2,jdbcType=INT}, #{javaField3,jdbcType=INT}, #{javaField4,jdbcType=INT}, \n" +
                "  #{javaField5,jdbcType=INT}, #{javaField6,jdbcType=INT}, #{javaField7,jdbcType=INT}, #{javaField8,jdbcType=INT}, #{javaField9,jdbcType=INT}, \n" +
                "  #{javaField10,jdbcType=INT}, #{javaField11,jdbcType=INT}, #{javaField12,jdbcType=INT})\n" +
                "</insert>";
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));
    }

    @Test
    public void insertWithAutoIncKey() {
        IntrospectedColumn column = addColumn(true, "pk", "INT", "pk");
        when(column.getFullyQualifiedJavaType()).thenReturn(new FullyQualifiedJavaType("Integer"));
        column = addPk("pk", "INT", "pk");
        when(column.getFullyQualifiedJavaType()).thenReturn(new FullyQualifiedJavaType("Integer"));
        when(yobatisIntrospectedTable.isAutoIncPrimaryKey()).thenReturn(true);
        when(yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.BASE_ENTITY)).thenReturn(new FullyQualifiedJavaType("org.yobatis.base.BaseEntity"));
        XmlElement element = factory.create(XmlElementName.INSERT.getName());
        String result = "<insert id=\"insert\" parameterType=\"org.yobatis.base.BaseEntity\">\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                "  insert into yobatis\n" +
                "  (pk)\n" +
                "  values (#{pk,jdbcType=INT})\n" +
                "  <selectKey keyProperty=\"pk\" order=\"AFTER\" resultType=\"Integer\">\n" +
                "    <if test=\"pk == null\">\n" +
                "      select last_insert_id()\n" +
                "    </if>\n" +
                "    <if test=\"pk != null\">\n" +
                "      select #{pk}\n" +
                "    </if>\n" +
                "  </selectKey>\n" +
                "</insert>";
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));
    }

    @Test
    public void count() {
        XmlElement element = factory.create(XmlElementName.COUNT.getName());
        String result = "<select id=\"count\" parameterType=\"org.yobatis.entity.criteria.YobatisCriteria\" resultType=\"int\">\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                "  select count(*) from yobatis\n" +
                "    <include refid=\"WHERE_CLAUSE\" />\n" +
                "</select>";
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));
    }

    @Test
    public void updateAllKeyTableByPk() {
        addPk("pk1", "CHAR", "pk1");
        addColumn(true, "pk1", "CHAR", "pk1");
        when(yobatisIntrospectedTable.getPrimaryKey()).thenReturn(new FullyQualifiedJavaType("String"));
        XmlElement element = factory.create(XmlElementName.UPDATE_BY_PK.getName());
        String result = "<update id=\"updateByPk\" parameterType=\"String\">\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                "  update yobatis\n" +
                "  <set>\n" +
                "    <if test=\"pk1 != null\">\n" +
                "      pk1 = #{pk1,jdbcType=CHAR},\n" +
                "    </if>\n" +
                "  </set>\n" +
                "  where pk1 = #{pk1,jdbcType=CHAR}\n" +
                "</update>";
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));

        addPk("pk2", "INT", "pk2");
        addColumn(true, "pk2", "INT", "pk2");
        when(yobatisIntrospectedTable.getPrimaryKey()).thenReturn(new FullyQualifiedJavaType("org.nalby.Yobatis"));
        element = factory.create(XmlElementName.UPDATE_BY_PK.getName());
        result = "<update id=\"updateByPk\" parameterType=\"org.nalby.Yobatis\">\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                "  update yobatis\n" +
                "  <set>\n" +
                "    <if test=\"pk1 != null\">\n" +
                "      pk1 = #{pk1,jdbcType=CHAR},\n" +
                "    </if>\n" +
                "    <if test=\"pk2 != null\">\n" +
                "      pk2 = #{pk2,jdbcType=INT},\n" +
                "    </if>\n" +
                "  </set>\n" +
                "  where pk1 = #{pk1,jdbcType=CHAR}\n" +
                "    and pk2 = #{pk2,jdbcType=INT}\n" +
                "</update>";
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));
    }

    @Test
    public void updateByPk() {
        addPk("pk1", "CHAR", "pk1");
        addColumn(true, "pk1", "CHAR", "pk1");
        addColumn(false, "field2", "CHAR", "field2");
        when(yobatisIntrospectedTable.getPrimaryKey()).thenReturn(new FullyQualifiedJavaType("String"));
        XmlElement element = factory.create(XmlElementName.UPDATE_BY_PK.getName());
        String result = "<update id=\"updateByPk\" parameterType=\"String\">\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                "  update yobatis\n" +
                "  <set>\n" +
                "    <if test=\"field2 != null\">\n" +
                "      field2 = #{field2,jdbcType=CHAR},\n" +
                "    </if>\n" +
                "  </set>\n" +
                "  where pk1 = #{pk1,jdbcType=CHAR}\n" +
                "</update>";
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));
        addPk("pk2", "INT", "pk2");
        addColumn(true, "pk2", "INT", "pk2");
        when(yobatisIntrospectedTable.getPrimaryKey()).thenReturn(new FullyQualifiedJavaType("org.nalby.Yobatis"));
        element = factory.create(XmlElementName.UPDATE_BY_PK.getName());
        result = "<update id=\"updateByPk\" parameterType=\"org.nalby.Yobatis\" >\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                "  update yobatis\n" +
                "  <set>\n" +
                "    <if test=\"field2 != null\">\n" +
                "      field2 = #{field2,jdbcType=CHAR},\n" +
                "    </if>\n" +
                "  </set>\n" +
                "  where pk1 = #{pk1,jdbcType=CHAR}\n" +
                "    and pk2 = #{pk2,jdbcType=INT}\n" +
                "</update>";
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));
    }

    @Test
    public void updateByCriteria() {
        addColumn(true, "pk1", "CHAR", "pk1");
        XmlElement element = factory.create(XmlElementName.UPDATE_BY_CRITERIA.getName());
        String result = "<update id=\"updateByCriteria\" parameterType=\"map\">\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                "  update yobatis\n" +
                "  <set>\n" +
                "    <if test=\"record.pk1 != null\">\n" +
                "      pk1 = #{record.pk1,jdbcType=CHAR},\n" +
                "    </if>\n" +
                "  </set>\n" +
                "    <include refid=\"WHERE_CLAUSE_FOR_UPDATE\" />\n" +
                "</update>";
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));
    }

    @Test
    public void deleteByPk() {
        addPk("pk1", "CHAR", "pk1");
        addColumn(true, "pk1", "CHAR", "pk1");
        when(yobatisIntrospectedTable.getPrimaryKey()).thenReturn(new FullyQualifiedJavaType("String"));
        XmlElement element = factory.create(XmlElementName.DELETE_BY_PK.getName());
        String result = "<delete id=\"deleteByPk\" parameterType=\"String\">\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                " <if test=\"_parameter != null\">" +
                "  delete from yobatis\n" +
                "  where pk1 = #{pk1,jdbcType=CHAR}\n" +
                "</if>" +
                "</delete>";
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));

        addPk("pk2", "INT", "pk2");
        addColumn(true, "pk2", "INT", "pk2");
        when(yobatisIntrospectedTable.getPrimaryKey()).thenReturn(new FullyQualifiedJavaType("org.nalby.Yobatis"));
        element = factory.create(XmlElementName.DELETE_BY_PK.getName());
        result = "<delete id=\"deleteByPk\" parameterType=\"org.nalby.Yobatis\">\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                " <if test=\"_parameter != null\">" +
                "  delete from yobatis\n" +
                "  where pk1 = #{pk1,jdbcType=CHAR}\n" +
                "    and pk2 = #{pk2,jdbcType=INT}\n" +
                "</if>" +
                "</delete>";
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));
    }

    @Test
    public void deleteByCriteria() {
        XmlElement element = factory.create(XmlElementName.DELETE_BY_CRITERIA.getName());
        String result = "<delete id=\"deleteByCriteria\" parameterType=\"org.yobatis.entity.criteria.YobatisCriteria\">\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                "  delete from yobatis\n" +
                "    <include refid=\"WHERE_CLAUSE\" />\n" +
                "</delete>";
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));
    }

    @Test
    public void whereForUpdate() {
        String result = "<sql id=\"WHERE_CLAUSE_FOR_UPDATE\">\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                "  <if test=\"criteria.valid\"/>\n" +
                "  <where>\n" +
                "    <foreach collection=\"criteria.oredCriteria\" item=\"bracketCriteria\" separator=\"or\">\n" +
                "      <if test=\"bracketCriteria.valid\">\n" +
                "        <trim prefix=\"(\" prefixOverrides=\"and\" suffix=\")\">\n" +
                "          <foreach collection=\"bracketCriteria.criteria\" item=\"criterion\">\n" +
                "            <choose>\n" +
                "              <when test=\"criterion.noValue\">\n" +
                "                and ${criterion.condition}\n" +
                "              </when>\n" +
                "              <when test=\"criterion.singleValue\">\n" +
                "                and ${criterion.condition} #{criterion.value}\n" +
                "              </when>\n" +
                "              <when test=\"criterion.betweenValue\">\n" +
                "                and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}\n" +
                "              </when>\n" +
                "              <when test=\"criterion.listValue\">\n" +
                "                and ${criterion.condition}\n" +
                "                <foreach close=\")\" collection=\"criterion.value\" item=\"listItem\" open=\"(\" separator=\",\">\n" +
                "                  #{listItem}\n" +
                "                </foreach>\n" +
                "              </when>\n" +
                "            </choose>\n" +
                "          </foreach>\n" +
                "        </trim>\n" +
                "      </if>\n" +
                "    </foreach>\n" +
                "  </where>\n" +
                "</sql>";
        XmlElement element = factory.create(XmlElementName.WHERE_CLAUSE_FOR_UPDATE.getName());
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));
    }

    @Test
    public void where() {
        XmlElement element = factory.create(XmlElementName.WHERE_CLAUSE.getName());
        String result = "<sql id=\"WHERE_CLAUSE\">\n" +
                "  <!--\n" +
                "    WARNING - @mbg.generated\n" +
                "    This element is automatically generated by MyBatis Generator, do not modify.\n" +
                "  -->\n" +
                "  <if test=\"valid\"/>\n" +
                "  <where>\n" +
                "    <foreach collection=\"oredCriteria\" item=\"bracketCriteria\" separator=\"or\">\n" +
                "      <if test=\"bracketCriteria.valid\">\n" +
                "        <trim prefix=\"(\" prefixOverrides=\"and\" suffix=\")\">\n" +
                "          <foreach collection=\"bracketCriteria.criteria\" item=\"criterion\">\n" +
                "            <choose>\n" +
                "              <when test=\"criterion.noValue\">\n" +
                "                and ${criterion.condition}\n" +
                "              </when>\n" +
                "              <when test=\"criterion.singleValue\">\n" +
                "                and ${criterion.condition} #{criterion.value}\n" +
                "              </when>\n" +
                "              <when test=\"criterion.betweenValue\">\n" +
                "                and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}\n" +
                "              </when>\n" +
                "              <when test=\"criterion.listValue\">\n" +
                "                and ${criterion.condition}\n" +
                "                <foreach close=\")\" collection=\"criterion.value\" item=\"listItem\" open=\"(\" separator=\",\">\n" +
                "                  #{listItem}\n" +
                "                </foreach>\n" +
                "              </when>\n" +
                "            </choose>\n" +
                "          </foreach>\n" +
                "        </trim>\n" +
                "      </if>\n" +
                "    </foreach>\n" +
                "  </where>\n" +
                "</sql>";
        assertEquals(result.replaceAll("\\s+", ""), element.getFormattedContent(0).replaceAll("\\s+", ""));
    }

}
