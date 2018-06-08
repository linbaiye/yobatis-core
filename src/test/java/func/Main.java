package func;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.nalby.yobatis.core.mybatis.MybatisGeneratorRunner;
import org.nalby.yobatis.core.mybatis.YobatisJavaFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    // create table compound_key_table(pk1 int, pk2 char(10), f3 text, primary key(pk1, pk2));
    private final static String COMPOUND_PK_TABLE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE generatorConfiguration PUBLIC \"-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN\" \"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd\">\n" +
            "\n" +
            "<generatorConfiguration>\n" +
            "  <classPathEntry location=\"/Users/lintao/.m2/repository/mysql/mysql-connector-java/5.1.25/mysql-connector-java-5.1.25.jar\"/>\n" +
            "  <context id=\"func\" targetRuntime=\"MyBatis3\">\n" +
            "    <plugin type=\"org.nalby.yobatis.core.mybatis.YobatisDaoPlugin\"/>\n" +
            "    <jdbcConnection driverClass=\"com.mysql.jdbc.Driver\" connectionURL=\"jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8\" userId=\"mybatis\" password=\"mybatis\"/>\n" +
            "    <!--jdbcConnection driverClass=\"com.mysql.jdbc.Driver\" connectionURL=\"jdbc:mysql://localhost:3306/book_store?characterEncoding=utf-8\" userId=\"root\" password=\"root\"/-->\n" +
            "    <javaTypeResolver>\n" +
            "      <property name=\"forceBigDecimals\" value=\"false\"/>\n" +
            "    </javaTypeResolver>\n" +
            "    <javaModelGenerator targetPackage=\"func.compoundkey.model\" targetProject=\"/Users/lintao/Study/java/workspace/generator/core/mybatis-generator-core\"/>\n" +
            "    <sqlMapGenerator targetPackage=\"mybatis-mappers\" targetProject=\"/Users/lintao/Study/java/workspace/generator/core/mybatis-generator-core\"/>\n" +
            "    <javaClientGenerator type=\"XMLMAPPER\" targetPackage=\"func.compoundkey.dao\" targetProject=\"/Users/lintao/Study/java/workspace/generator/core/mybatis-generator-core\"/>\n" +
            "    <table tableName=\"compound_key_table\" schema=\"mybatis\" modelType=\"flat\"/>\n" +
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
            "    <!--jdbcConnection driverClass=\"com.mysql.jdbc.Driver\" connectionURL=\"jdbc:mysql://localhost:3306/book_store?characterEncoding=utf-8\" userId=\"root\" password=\"root\"/-->\n" +
            "    <javaTypeResolver>\n" +
            "      <property name=\"forceBigDecimals\" value=\"false\"/>\n" +
            "    </javaTypeResolver>\n" +
            "    <javaModelGenerator targetPackage=\"func.allkey.model\" targetProject=\"/Users/lintao/Study/java/workspace/generator/core/mybatis-generator-core\"/>\n" +
            "    <sqlMapGenerator targetPackage=\"mybatis-mappers\" targetProject=\"/Users/lintao/Study/java/workspace/generator/core/mybatis-generator-core\"/>\n" +
            "    <javaClientGenerator type=\"XMLMAPPER\" targetPackage=\"func.allkey.dao\" targetProject=\"/Users/lintao/Study/java/workspace/generator/core/mybatis-generator-core\"/>\n" +
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
            "    <!--jdbcConnection driverClass=\"com.mysql.jdbc.Driver\" connectionURL=\"jdbc:mysql://localhost:3306/book_store?characterEncoding=utf-8\" userId=\"root\" password=\"root\"/-->\n" +
            "    <javaTypeResolver>\n" +
            "      <property name=\"forceBigDecimals\" value=\"false\"/>\n" +
            "    </javaTypeResolver>\n" +
            "    <javaModelGenerator targetPackage=\"func.autoinckey.model\" targetProject=\"/Users/lintao/Study/java/workspace/generator/core/mybatis-generator-core\"/>\n" +
            "    <sqlMapGenerator targetPackage=\"mybatis-mappers\" targetProject=\"/Users/lintao/Study/java/workspace/generator/core/mybatis-generator-core\"/>\n" +
            "    <javaClientGenerator type=\"XMLMAPPER\" targetPackage=\"func.autoinckey.dao\" targetProject=\"/Users/lintao/Study/java/workspace/generator/core/mybatis-generator-core\"/>\n" +
            "    <table tableName=\"customer\" schema=\"mybatis\" modelType=\"flat\">\n" +
            " 		<generatedKey column=\"id\" sqlStatement=\"mysql\" identity=\"true\"/>" +
            "    </table>\n" +
            "    <!--table tableName=\"compound_key_table\" schema=\"mybatis\" modelType=\"flat\"/>\n" +
            "    <table tableName=\"string_key_table\" schema=\"mybatis\" modelType=\"flat\"/>\n" +
            "    <table tableName=\"book\" schema=\"mybatis\" modelType=\"flat\"/-->\n" +
            "  </context>\n" +
            "</generatorConfiguration>\n";

    private static void generate(String name) throws Exception {
        InputStream inputStream = new ByteArrayInputStream(name.getBytes());
        MybatisGeneratorRunner runner = new MybatisGeneratorRunner();
        runner.parse(inputStream);
        List<GeneratedJavaFile> javaFiles = runner.getGeneratedJavaFiles();
        for (GeneratedJavaFile javaFile : javaFiles) {
            if (javaFile instanceof YobatisJavaFile) {
                String packagename = javaFile.getTargetPackage();
                String rpath = packagename.replaceAll("\\.", "/");
                Path path = Paths.get("src/test/java/" + rpath + "/" + javaFile.getFileName());
                System.out.println(path);
                File file = path.toFile();
                if (file.exists()) {
                    file.delete();
                }
                String content = javaFile.getFormattedContent();
                OutputStream outputStream = new FileOutputStream(file);
                outputStream.write(content.getBytes());
                outputStream.close();
            }
        }

        for (GeneratedXmlFile xmlFile: runner.getGeneratedXmlFiles()) {
            Path path = Paths.get("src/test/resources/" + xmlFile.getTargetPackage() + "/" + xmlFile.getFileName());
            System.out.println(path);
            File file = path.toFile();
            if (file.exists()) {
                file.delete();
            }
            String content = xmlFile.getFormattedContent();
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes());
            outputStream.close();
        }
    }

    public static void main(String[] args) throws Exception {
        generate(AUTO_INC_KEY_TABLE);
        generate(COMPOUND_PK_TABLE);
        generate(ALL_PK_TABLE);
    }
}
