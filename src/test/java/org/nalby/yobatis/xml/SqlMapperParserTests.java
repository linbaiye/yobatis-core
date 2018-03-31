package org.nalby.yobatis.xml;

import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;
import org.nalby.yobatis.exception.InvalidMapperException;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SqlMapperParserTests {
	
	public static class SqlMapper extends AbstractXmlParser {
		public SqlMapper(InputStream inputStream)
				throws DocumentException, IOException {
			super(inputStream, "mapper");
		}
		
		@Override
		protected void customSAXReader(SAXReader saxReader ) {
			saxReader.setEntityResolver(new EntityResolver() {
				@Override
				public InputSource resolveEntity(String publicId, String systemId)
						throws SAXException, IOException {
					return null;
				}
			});
		}

		
		
		@Override
		public String toXmlString() throws IOException {
			//OutputFormat format = new OutputFormat(" ");
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setTrimText(false);
			//format.setNewLineAfterDeclaration(false);
			//format.setPadText(false);
			//format.setPadText(true);
			//OutputFormat format = OutputFormat.createCompactFormat();
			//format.setTrimText(true);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//PrintStream ps = new PrintStream(baos, true, "utf-8");
		    XMLWriter writer = new XMLWriter(baos, format);
		    writer.setEscapeText(false);
		    writer.write(document);
			String content = new String(baos.toByteArray(), StandardCharsets.UTF_8);
			//ps.close();
			//return document.asXML();o
			//return removeBlankLines(content);
			return content;
		}
		
		public void assertHasElement(String id) {
			List<Element> elements = document.getRootElement().elements();
			for (Element element : elements) {
				if (id.equals(element.attributeValue("id"))) {
					return;
				}
			}
			fail("failed to find id : " + id);
		}
	}
	
	@Test
	public void mergable() throws IOException, DocumentException {
		String file1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" + 
				"<mapper namespace=\"dao.BlogMapper\">\n" + 
				"  <resultMap id=\"BaseResultMap\" type=\"hello.world.domain.Blog\">\n" + 
				"    <!--\n" + 
				"      WARNING - @mbg.generated\n" + 
				"      This element is automatically generated by MyBatis Generator, do not modify.\n" + 
				"      This element was generated on Mon Dec 04 21:25:54 PHT 2017.\n" + 
				"    -->\n" + 
				"    <id column=\"id\" jdbcType=\"BIGINT\" property=\"id\" />\n" + 
				"    <result column=\"name\" jdbcType=\"VARCHAR\" property=\"name\" />\n" + 
				"  </resultMap>\n" + 
				"</mapper>";
		String file2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" + 
				"<mapper namespace=\"dao.BlogMapper\">\n" + 
				"  <!--sql id=\"test\"></sql-->\n" + 
				"  <sql id=\"test\"><!-- test comment --></sql>\n" + 
				"</mapper>";
		SqlMapperParser parser1 = SqlMapperParser.fromString(file1);
		SqlMapperParser parser2 = SqlMapperParser.fromString(file2);
		parser1.merge(parser2);
		String newFile = parser1.toXmlString();
		SqlMapper mapper = new SqlMapper(new ByteArrayInputStream(newFile.getBytes()));
		mapper.assertHasElement("test");
		mapper.assertHasElement("BaseResultMap");
	}
	
	@Test(expected = InvalidMapperException.class)
	public void selfConflictId() {
		String file1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" + 
				"<mapper namespace=\"dao.BlogMapper\">\n" + 
				"  <resultMap id=\"BaseResultMap\" type=\"hello.world.domain.Blog\">\n" + 
				"    <!--\n" + 
				"      WARNING - @mbg.generated\n" + 
				"      This element is automatically generated by MyBatis Generator, do not modify.\n" + 
				"      This element was generated on Mon Dec 04 21:25:54 PHT 2017.\n" + 
				"    -->\n" + 
				"    <id column=\"id\" jdbcType=\"BIGINT\" property=\"id\" />\n" + 
				"    <result column=\"name\" jdbcType=\"VARCHAR\" property=\"name\" />\n" + 
				"  </resultMap>\n" + 
				"  <sql id=\"BaseResultMap\"><!-- test comment --></sql>\n" + 
				"</mapper>";
		SqlMapperParser.fromString(file1);
	}
	
	public static void main(String[] args) throws Exception {
		String file1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" + 
				"<mapper namespace=\"dao.impl.DateTableDaoImpl\">\n" + 
				"  <resultMap id=\"BASE_RESULT_MAP\" type=\"model.DateTable\">\n" + 
				"    <!--\n" + 
				"      WARNING - @mbg.generated\n" + 
				"      This element is automatically generated by MyBatis Generator, do not modify.\n" + 
				"    -->\n" + 
				"    <id column=\"id\" jdbcType=\"INTEGER\" property=\"id\" />\n" + 
				"    <result column=\"created_date\" jdbcType=\"DATE\" property=\"createdDate\" />\n" + 
				"    <result column=\"created_time\" jdbcType=\"TIME\" property=\"createdTime\" />\n" + 
				"    <result column=\"created_timestamp\" jdbcType=\"TIMESTAMP\" property=\"createdTimestamp\" />\n" + 
				"  </resultMap>\n" + 
				"  <sql id=\"WHERE_CLAUSE\">\n" + 
				"    <!--\n" + 
				"      WARNING - @mbg.generated\n" + 
				"      This element is automatically generated by MyBatis Generator, do not modify.\n" + 
				"    -->\n" + 
				"    <where>\n" + 
				"      <foreach collection=\"oredCriteria\" item=\"criteria\" separator=\"or\">\n" + 
				"        <if test=\"criteria.valid\">\n" + 
				"          <trim prefix=\"(\" prefixOverrides=\"and\" suffix=\")\">\n" + 
				"            <foreach collection=\"criteria.criteria\" item=\"criterion\">\n" + 
				"              <choose>\n" + 
				"                <when test=\"criterion.noValue\">\n" + 
				"                  and ${criterion.condition}\n" + 
				"                </when>\n" + 
				"                <when test=\"criterion.singleValue\">\n" + 
				"                  and ${criterion.condition} #{criterion.value}\n" + 
				"                </when>\n" + 
				"                <when test=\"criterion.betweenValue\">\n" + 
				"                  and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}\n" + 
				"                </when>\n" + 
				"                <when test=\"criterion.listValue\">\n" + 
				"                  and ${criterion.condition}\n" + 
				"                  <foreach close=\")\" collection=\"criterion.value\" item=\"listItem\" open=\"(\" separator=\",\">\n" + 
				"                    #{listItem}\n" + 
				"                  </foreach>\n" + 
				"                </when>\n" + 
				"              </choose>\n" + 
				"            </foreach>\n" + 
				"          </trim>\n" + 
				"        </if>\n" + 
				"      </foreach>\n" + 
				"    </where>\n" + 
				"  </sql>\n" + 
				"  <sql id=\"WHERE_CLAUSE_FOR_UPDATE\">\n" + 
				"    <!--\n" + 
				"      WARNING - @mbg.generated\n" + 
				"      This element is automatically generated by MyBatis Generator, do not modify.\n" + 
				"    -->\n" + 
				"    <where>\n" + 
				"      <foreach collection=\"example.oredCriteria\" item=\"criteria\" separator=\"or\">\n" + 
				"        <if test=\"criteria.valid\">\n" + 
				"          <trim prefix=\"(\" prefixOverrides=\"and\" suffix=\")\">\n" + 
				"            <foreach collection=\"criteria.criteria\" item=\"criterion\">\n" + 
				"              <choose>\n" + 
				"                <when test=\"criterion.noValue\">\n" + 
				"                  and ${criterion.condition}\n" + 
				"                </when>\n" + 
				"                <when test=\"criterion.singleValue\">\n" + 
				"                  and ${criterion.condition} #{criterion.value}\n" + 
				"                </when>\n" + 
				"                <when test=\"criterion.betweenValue\">\n" + 
				"                  and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}\n" + 
				"                </when>\n" + 
				"                <when test=\"criterion.listValue\">\n" + 
				"                  and ${criterion.condition}\n" + 
				"                  <foreach close=\")\" collection=\"criterion.value\" item=\"listItem\" open=\"(\" separator=\",\">\n" + 
				"                    #{listItem}\n" + 
				"                  </foreach>\n" + 
				"                </when>\n" + 
				"              </choose>\n" + 
				"            </foreach>\n" + 
				"          </trim>\n" + 
				"        </if>\n" + 
				"      </foreach>\n" + 
				"    </where>\n" + 
				"  </sql>\n" + 
				"  <sql id=\"BASE_COLUMN_LIST\">\n" + 
				"    <!--\n" + 
				"      WARNING - @mbg.generated\n" + 
				"      This element is automatically generated by MyBatis Generator, do not modify.\n" + 
				"    -->\n" + 
				"    id, created_date, created_time, created_timestamp\n" + 
				"  </sql>\n" + 
				"  <select id=\"selectByCriteria\" parameterType=\"model.criteria.DateTableCriteria\" resultMap=\"BASE_RESULT_MAP\">\n" + 
				"    <!--\n" + 
				"      WARNING - @mbg.generated\n" + 
				"      This element is automatically generated by MyBatis Generator, do not modify.\n" + 
				"    -->\n" + 
				"    select\n" + 
				"    <if test=\"distinct\">\n" + 
				"      distinct\n" + 
				"    </if>\n" + 
				"    <include refid=\"BASE_COLUMN_LIST\" />\n" + 
				"    from date_table\n" + 
				"    <if test=\"_parameter != null\">\n" + 
				"      <include refid=\"WHERE_CLAUSE\" />\n" + 
				"    </if>\n" + 
				"    <if test=\"orderByClause != null\">\n" + 
				"      order by ${orderByClause}\n" + 
				"    </if>\n" + 
				"    <if test=\"limit != null\">\n" + 
				"      limit #{limit}\n" + 
				"    </if>\n" + 
				"    <if test=\"offset != null\">\n" + 
				"      offset #{offset}\n" + 
				"    </if>\n" + 
				"    <if test=\"lockSelectedRows != null and lockSelectedRows == true\">\n" + 
				"      for update\n" + 
				"    </if>\n" + 
				"  </select>\n" + 
				"  <select id=\"selectByPk\" parameterType=\"java.lang.Integer\" resultMap=\"BASE_RESULT_MAP\">\n" + 
				"    <!--\n" + 
				"      WARNING - @mbg.generated\n" + 
				"      This element is automatically generated by MyBatis Generator, do not modify.\n" + 
				"    -->\n" + 
				"    select \n" + 
				"    <include refid=\"BASE_COLUMN_LIST\" />\n" + 
				"    from date_table\n" + 
				"    where id = #{id,jdbcType=INTEGER}\n" + 
				"  </select>\n" + 
				"  <delete id=\"deleteByPk\" parameterType=\"java.lang.Integer\">\n" + 
				"    <!--\n" + 
				"      WARNING - @mbg.generated\n" + 
				"      This element is automatically generated by MyBatis Generator, do not modify.\n" + 
				"    -->\n" + 
				"    delete from date_table\n" + 
				"    where id = #{id,jdbcType=INTEGER}\n" + 
				"  </delete>\n" + 
				"  <delete id=\"deleteByCriteria\" parameterType=\"model.criteria.DateTableCriteria\">\n" + 
				"    <!--\n" + 
				"      WARNING - @mbg.generated\n" + 
				"      This element is automatically generated by MyBatis Generator, do not modify.\n" + 
				"    -->\n" + 
				"    delete from date_table\n" + 
				"    <if test=\"_parameter != null\">\n" + 
				"      <include refid=\"WHERE_CLAUSE\" />\n" + 
				"    </if>\n" + 
				"  </delete>\n" + 
				"  <insert id=\"insertAll\" parameterType=\"model.base.BaseDateTable\">\n" + 
				"    <!--\n" + 
				"      WARNING - @mbg.generated\n" + 
				"      This element is automatically generated by MyBatis Generator, do not modify.\n" + 
				"    -->\n" + 
				"    insert into date_table (id, created_date, created_time, created_timestamp)\n" + 
				"    values (#{id,jdbcType=INTEGER}, #{createdDate,jdbcType=DATE}, #{createdTime,jdbcType=TIME}, #{createdTimestamp,jdbcType=TIMESTAMP})\n" + 
				"  </insert>\n" + 
				"  <insert id=\"insertAllIgnore\" parameterType=\"model.base.BaseDateTable\">\n" + 
				"    <!--\n" + 
				"      WARNING - @mbg.generated\n" + 
				"      This element is automatically generated by MyBatis Generator, do not modify.\n" + 
				"    -->\n" + 
				"    insert ignore into date_table (id, created_date, created_time, created_timestamp)\n" + 
				"    values (#{id,jdbcType=INTEGER}, #{createdDate,jdbcType=DATE}, #{createdTime,jdbcType=TIME}, #{createdTimestamp,jdbcType=TIMESTAMP})\n" + 
				"  </insert>\n" + 
				"  <insert id=\"insert\" parameterType=\"model.base.BaseDateTable\">\n" + 
				"    <!--\n" + 
				"      WARNING - @mbg.generated\n" + 
				"      This element is automatically generated by MyBatis Generator, do not modify.\n" + 
				"    -->\n" + 
				"    insert into date_table\n" + 
				"    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n" + 
				"      <if test=\"id != null\">\n" + 
				"        id,\n" + 
				"      </if>\n" + 
				"      <if test=\"createdDate != null\">\n" + 
				"        created_date,\n" + 
				"      </if>\n" + 
				"      <if test=\"createdTime != null\">\n" + 
				"        created_time,\n" + 
				"      </if>\n" + 
				"      <if test=\"createdTimestamp != null\">\n" + 
				"        created_timestamp,\n" + 
				"      </if>\n" + 
				"    </trim>\n" + 
				"    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">\n" + 
				"      <if test=\"id != null\">\n" + 
				"        #{id,jdbcType=INTEGER},\n" + 
				"      </if>\n" + 
				"      <if test=\"createdDate != null\">\n" + 
				"        #{createdDate,jdbcType=DATE},\n" + 
				"      </if>\n" + 
				"      <if test=\"createdTime != null\">\n" + 
				"        #{createdTime,jdbcType=TIME},\n" + 
				"      </if>\n" + 
				"      <if test=\"createdTimestamp != null\">\n" + 
				"        #{createdTimestamp,jdbcType=TIMESTAMP},\n" + 
				"      </if>\n" + 
				"    </trim>\n" + 
				"  </insert>\n" + 
				"  <select id=\"count\" parameterType=\"model.criteria.DateTableCriteria\" resultType=\"java.lang.Long\">\n" + 
				"    <!--\n" + 
				"      WARNING - @mbg.generated\n" + 
				"      This element is automatically generated by MyBatis Generator, do not modify.\n" + 
				"    -->\n" + 
				"    select count(*) from date_table\n" + 
				"    <if test=\"_parameter != null\">\n" + 
				"      <include refid=\"WHERE_CLAUSE\" />\n" + 
				"    </if>\n" + 
				"  </select>\n" + 
				"  <update id=\"updateByCriteria\" parameterType=\"map\">\n" + 
				"    <!--\n" + 
				"      WARNING - @mbg.generated\n" + 
				"      This element is automatically generated by MyBatis Generator, do not modify.\n" + 
				"    -->\n" + 
				"    update date_table\n" + 
				"    <set>\n" + 
				"      <if test=\"record.id != null\">\n" + 
				"        id = #{record.id,jdbcType=INTEGER},\n" + 
				"      </if>\n" + 
				"      <if test=\"record.createdDate != null\">\n" + 
				"        created_date = #{record.createdDate,jdbcType=DATE},\n" + 
				"      </if>\n" + 
				"      <if test=\"record.createdTime != null\">\n" + 
				"        created_time = #{record.createdTime,jdbcType=TIME},\n" + 
				"      </if>\n" + 
				"      <if test=\"record.createdTimestamp != null\">\n" + 
				"        created_timestamp = #{record.createdTimestamp,jdbcType=TIMESTAMP},\n" + 
				"      </if>\n" + 
				"    </set>\n" + 
				"    <if test=\"_parameter != null\">\n" + 
				"      <include refid=\"WHERE_CLAUSE_FOR_UPDATE\" />\n" + 
				"    </if>\n" + 
				"  </update>\n" + 
				"  <update id=\"updateAllByCriteria\" parameterType=\"map\">\n" + 
				"    <!--\n" + 
				"      WARNING - @mbg.generated\n" + 
				"      This element is automatically generated by MyBatis Generator, do not modify.\n" + 
				"    -->\n" + 
				"    update date_table\n" + 
				"    set id = #{record.id,jdbcType=INTEGER},\n" + 
				"      created_date = #{record.createdDate,jdbcType=DATE},\n" + 
				"      created_time = #{record.createdTime,jdbcType=TIME},\n" + 
				"      created_timestamp = #{record.createdTimestamp,jdbcType=TIMESTAMP}\n" + 
				"    <if test=\"_parameter != null\">\n" + 
				"      <include refid=\"WHERE_CLAUSE_FOR_UPDATE\" />\n" + 
				"    </if>\n" + 
				"  </update>\n" + 
				"  <update id=\"update\" parameterType=\"model.base.BaseDateTable\">\n" + 
				"    <!--\n" + 
				"      WARNING - @mbg.generated\n" + 
				"      This element is automatically generated by MyBatis Generator, do not modify.\n" + 
				"    -->\n" + 
				"    update date_table\n" + 
				"    <set>\n" + 
				"      <if test=\"createdDate != null\">\n" + 
				"        created_date = #{createdDate,jdbcType=DATE},\n" + 
				"      </if>\n" + 
				"      <if test=\"createdTime != null\">\n" + 
				"        created_time = #{createdTime,jdbcType=TIME},\n" + 
				"      </if>\n" + 
				"      <if test=\"createdTimestamp != null\">\n" + 
				"        created_timestamp = #{createdTimestamp,jdbcType=TIMESTAMP},\n" + 
				"      </if>\n" + 
				"    </set>\n" + 
				"    where id = #{id,jdbcType=INTEGER}\n" + 
				"  </update>\n" + 
				"  <update id=\"updateAll\" parameterType=\"model.base.BaseDateTable\">\n" + 
				"    <!--\n" + 
				"      WARNING - @mbg.generated\n" + 
				"      This element is automatically generated by MyBatis Generator, do not modify.\n" + 
				"    -->\n" + 
				"    update date_table\n" + 
				"    set created_date = #{createdDate,jdbcType=DATE},\n" + 
				"      created_time = #{createdTime,jdbcType=TIME},\n" + 
				"      created_timestamp = #{createdTimestamp,jdbcType=TIMESTAMP}\n" + 
				"    where id = #{id,jdbcType=INTEGER}\n" + 
				"  </update>\n" + 
				"</mapper>"; 
		System.out.println(new SqlMapper(new ByteArrayInputStream(file1.getBytes())).toXmlString());
	}
	

}
