package func;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.nalby.yobatis.core.exception.InvalidMybatisGeneratorConfigException;
import org.nalby.yobatis.core.mybatis.YobatisUnit;
import org.nalby.yobatis.core.util.TextUtil;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    // create table compound_key_table(pk1 int, pk2 char(10), f3 text, primary key(pk1, pk2));
    private final static String COMPOUND_PK_TABLE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE generatorConfiguration PUBLIC \"-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN\" \"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd\">\n" +
            "\n" +
            "<generatorConfiguration>\n" +
            "  <classPathEntry location=\"/Users/lintao/.m2/repository/mysql/mysql-connector-java/5.1.25/mysql-connector-java-5.1.25.jar\"/>\n" +
            "  <context id=\"func\" targetRuntime=\"MyBatis3\">\n" +
            "    <property name=\"type\" value=\"spring-boot\"/>" +
            "    <plugin type=\"org.nalby.yobatis.core.mybatis.YobatisDaoPlugin\"/>\n" +
            "    <jdbcConnection driverClass=\"com.mysql.jdbc.Driver\" connectionURL=\"jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8\" userId=\"mybatis\" password=\"mybatis\"/>\n" +
            "    <javaTypeResolver>\n" +
            "      <property name=\"forceBigDecimals\" value=\"false\"/>\n" +
            "    </javaTypeResolver>\n" +
            "    <javaModelGenerator targetPackage=\"func.compoundkey.model\" targetProject=\"/Users/lintao/Work/idea-projects/yobatis-core/src/test/java\"/>\n" +
            "    <sqlMapGenerator targetPackage=\"mybatis-mappers\" targetProject=\"/Users/lintao/Work/idea-projects/yobatis-core/src/test/resources\"/>\n" +
            "    <javaClientGenerator type=\"XMLMAPPER\" targetPackage=\"func.compoundkey.dao\" targetProject=\"/Users/lintao/Work/idea-projects/yobatis-core/src/test/java\"/>\n" +
            "    <table tableName=\"compound_key_table\" schema=\"mybatis\" modelType=\"flat\"/>\n" +
            "  </context>\n" +
            "</generatorConfiguration>\n";


    private final static String ALL_TYPES_TABLE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE generatorConfiguration PUBLIC \"-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN\" \"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd\">\n" +
            "\n" +
            "<generatorConfiguration>\n" +
            "  <classPathEntry location=\"/Users/lintao/.m2/repository/mysql/mysql-connector-java/5.1.25/mysql-connector-java-5.1.25.jar\"/>\n" +
            "  <context id=\"func\" targetRuntime=\"MyBatis3\">\n" +
            "    <plugin type=\"org.nalby.yobatis.core.mybatis.YobatisDaoPlugin\"/>\n" +
            "    <jdbcConnection driverClass=\"com.mysql.jdbc.Driver\" connectionURL=\"jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8\" userId=\"mybatis\" password=\"mybatis\"/>\n" +
            "    <javaTypeResolver>\n" +
            "      <property name=\"forceBigDecimals\" value=\"false\"/>\n" +
            "    </javaTypeResolver>\n" +
            "    <javaModelGenerator targetPackage=\"func.alltype.model\" targetProject=\"/Users/lintao/Work/idea-projects/yobatis-core/src/test/java\"/>\n" +
            "    <sqlMapGenerator targetPackage=\"mybatis-mappers\" targetProject=\"/Users/lintao/Work/idea-projects/yobatis-core/src/test/resources\"/>\n" +
            "    <javaClientGenerator type=\"XMLMAPPER\" targetPackage=\"func.alltype.dao\" targetProject=\"/Users/lintao/Work/idea-projects/yobatis-core/src/test/java\"/>\n" +
            "    <table tableName=\"all_data_types\" schema=\"mybatis\" modelType=\"flat\"/>\n" +
            "  </context>\n" +
            "</generatorConfiguration>\n";

    private final static String ALL_PK_TABLE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE generatorConfiguration PUBLIC \"-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN\" \"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd\">\n" +
            "\n" +
            "<generatorConfiguration>\n" +
            "  <classPathEntry location=\"/Users/lintao/.m2/repository/mysql/mysql-connector-java/5.1.25/mysql-connector-java-5.1.25.jar\"/>\n" +
            "  <context id=\"func\" targetRuntime=\"MyBatis3\">\n" +
            "    <plugin type=\"org.nalby.yobatis.core.mybatis.YobatisDaoPlugin\"/>\n" +
            "    <jdbcConnection driverClass=\"com.mysql.jdbc.Driver\" connectionURL=\"jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8\" userId=\"mybatis\" password=\"mybatis\"/>\n" +
            "    <javaTypeResolver>\n" +
            "      <property name=\"forceBigDecimals\" value=\"false\"/>\n" +
            "    </javaTypeResolver>\n" +
            "    <javaModelGenerator targetPackage=\"func.allkey.model\" targetProject=\"/Users/lintao/Work/idea-projects/yobatis-core/src/test/java\"/>\n" +
            "    <sqlMapGenerator targetPackage=\"mybatis-mappers\" targetProject=\"/Users/lintao/Work/idea-projects/yobatis-core/src/test/resources\"/>\n" +
            "    <javaClientGenerator type=\"XMLMAPPER\" targetPackage=\"func.allkey.dao\" targetProject=\"/Users/lintao/Work/idea-projects/yobatis-core/src/test/java\"/>\n" +
            "    <table tableName=\"all_key_table\" schema=\"mybatis\" modelType=\"flat\"/>\n" +
            "  </context>\n" +
            "</generatorConfiguration>\n";
    /*
     CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f1` char(20) DEFAULT NULL,
  `f2` text,
  `f3` decimal(10,4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8
     */
    private final static String AUTO_INC_KEY_TABLE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE generatorConfiguration PUBLIC \"-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN\" \"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd\">\n" +
            "\n" +
            "<generatorConfiguration>\n" +
            "  <classPathEntry location=\"/Users/lintao/.m2/repository/mysql/mysql-connector-java/5.1.25/mysql-connector-java-5.1.25.jar\"/>\n" +
            "  <context id=\"func\" targetRuntime=\"MyBatis3\">\n" +
            "    <plugin type=\"org.nalby.yobatis.core.mybatis.YobatisDaoPlugin\"/>\n" +
            "    <jdbcConnection driverClass=\"com.mysql.jdbc.Driver\" connectionURL=\"jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8\" userId=\"mybatis\" password=\"mybatis\"/>\n" +
            "    <javaTypeResolver>\n" +
            "      <property name=\"forceBigDecimals\" value=\"false\"/>\n" +
            "    </javaTypeResolver>\n" +
            "    <javaModelGenerator targetPackage=\"func.autoinckey.model\" targetProject=\"/Users/lintao/Work/idea-projects/yobatis-core/src/test/java\"/>\n" +
            "    <sqlMapGenerator targetPackage=\"mybatis-mappers\" targetProject=\"/Users/lintao/Work/idea-projects/yobatis-core/src/test/resources\"/>\n" +
            "    <javaClientGenerator type=\"XMLMAPPER\" targetPackage=\"func.autoinckey.dao\" targetProject=\"/Users/lintao/Work/idea-projects/yobatis-core/src/test/java\"/>\n" +
            "    <table tableName=\"customer\" schema=\"mybatis\" modelType=\"flat\">\n" +
            " 		<generatedKey column=\"id\" sqlStatement=\"mysql\" identity=\"true\"/>" +
            "    </table>\n" +
            "  </context>\n" +
            "</generatorConfiguration>\n";

    private final static String ALL_TYPES_TABLE_ORIGINAL = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE generatorConfiguration PUBLIC \"-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN\" \"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd\">\n" +
            "\n" +
            "<generatorConfiguration>\n" +
            "  <classPathEntry location=\"/Users/lintao/.m2/repository/mysql/mysql-connector-java/5.1.25/mysql-connector-java-5.1.25.jar\"/>\n" +
            "  <context id=\"func\" targetRuntime=\"MyBatis3\">\n" +
            "    <jdbcConnection driverClass=\"com.mysql.jdbc.Driver\" connectionURL=\"jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8\" userId=\"mybatis\" password=\"mybatis\"/>\n" +
            "    <javaTypeResolver>\n" +
            "      <property name=\"forceBigDecimals\" value=\"false\"/>\n" +
            "    </javaTypeResolver>\n" +
            "    <javaModelGenerator targetPackage=\"original.model\" targetProject=\"/Users/lintao/Work/idea-projects/yobatis-core/src/test/java\"/>\n" +
            "    <sqlMapGenerator targetPackage=\"mybatis-origin\" targetProject=\"/Users/lintao/Work/idea-projects/yobatis-core/src/test/resources\"/>\n" +
            "    <javaClientGenerator type=\"XMLMAPPER\" targetPackage=\"original.dao\" targetProject=\"/Users/lintao/Work/idea-projects/yobatis-core/src/test/java\"/>\n" +
            "    <table tableName=\"all_data_types\" schema=\"mybatis\" modelType=\"flat\">\n" +
            " 		<generatedKey column=\"id\" sqlStatement=\"mysql\" identity=\"true\"/>" +
            "    </table>\n" +
            "  </context>\n" +
            "</generatorConfiguration>\n";



    private static MyBatisGenerator generate(InputStream inputStream) {
        List<String> warnings = new ArrayList<>();
        try {
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(inputStream);

            DefaultShellCallback shellCallback = new DefaultShellCallback(false);

            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);

            myBatisGenerator.generate(null, null, null, false);
            return myBatisGenerator;
        } catch (XMLParserException | InvalidConfigurationException | SQLException | IOException | InterruptedException e) {
            throw new InvalidMybatisGeneratorConfigException(e);
        }
    }


    private static void generate(String name) throws Exception {
        InputStream inputStream = new ByteArrayInputStream(name.getBytes());
        MyBatisGenerator generator = generate(inputStream);
        List<GeneratedJavaFile> javaFiles = generator.getGeneratedJavaFiles();
        for (GeneratedJavaFile javaFile : javaFiles) {
            CompilationUnit unit = javaFile.getCompilationUnit();
            if (!(unit instanceof YobatisUnit)) {
                continue;
            }
            YobatisUnit yobatisUnit = (YobatisUnit) unit;
            Path path = Paths.get(yobatisUnit.getPathToPut());
            System.out.println(path);
            File file = path.toFile();
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                String content = TextUtil.asString(fileInputStream);
                yobatisUnit.merge(content);
                file.delete();
            }
            String content = javaFile.getFormattedContent();
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes());
            outputStream.close();
        }

        for (GeneratedXmlFile xmlFile: generator.getGeneratedXmlFiles()) {
            if (!(xmlFile instanceof YobatisUnit)) {
                continue;
            }
            YobatisUnit unit = (YobatisUnit) xmlFile;
            Path path = Paths.get(unit.getPathToPut());
            File file = path.toFile();
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                String content = TextUtil.asString(fileInputStream);
                unit.merge(content);
                file.delete();
            }
            String content = xmlFile.getFormattedContent();
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes());
            outputStream.close();
        }
    }

    private static void print(String name) throws Exception {
        InputStream inputStream = new ByteArrayInputStream(name.getBytes());
        MyBatisGenerator generator = generate(inputStream);
        List<GeneratedJavaFile> javaFiles = generator.getGeneratedJavaFiles();
        for (GeneratedJavaFile javaFile : javaFiles) {
        }
        for (GeneratedXmlFile xmlFile: generator.getGeneratedXmlFiles()) {
            if (!(xmlFile instanceof YobatisUnit)) {
                continue;
            }
            YobatisUnit unit = (YobatisUnit) xmlFile;
            System.out.println(unit.getFormattedContent());
        }
    }


    public static void main(String[] args) throws Exception {
//         generate(ALL_TYPES_TABLE);
        //generate(AUTO_INC_KEY_TABLE_ORIGINAL);
        generate(AUTO_INC_KEY_TABLE);
//        generate(COMPOUND_PK_TABLE);
//        generate(ALL_PK_TABLE);
//        generate(ALL_TYPES_TABLE);
//          print(ALL_PK_TABLE);
    }
}
