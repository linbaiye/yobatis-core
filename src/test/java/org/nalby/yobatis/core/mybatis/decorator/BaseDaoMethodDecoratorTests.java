package org.nalby.yobatis.core.mybatis.decorator;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.nalby.yobatis.core.mybatis.javadoc.BaseDaoMethodJavaDocDecorator;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class BaseDaoMethodDecoratorTests {

    private BaseDaoMethodJavaDocDecorator decorator = BaseDaoMethodJavaDocDecorator.getInstance();

    private Method method;

    @Before
    public void setup() {
        method = new Method();
    }

    protected Parameter buildParam(String type, String name) {
        return new Parameter(new FullyQualifiedJavaType(type), name);
    }

    protected void assertContainsLine(List<String> lines, String line) {
        boolean found = false;
        for (String s : lines) {
            if (s.contains(line)) {
                found = true;
            }
        }
        assertTrue(found);
    }


    @Test
    public void selectOneByPk() {
        method.setName("selectOne");
        method.addParameter(buildParam("PK", "pk"));
        decorator.decorate(method);
        assertContainsLine(method.getJavaDocLines(), "Select a record by primary key.");
    }

    @Test
    public void selectOneByCriteria() {
        method.setName("selectOne");
        method.addParameter(buildParam("BaseCriteria", "criteria"));
        decorator.decorate(method);
        assertContainsLine(method.getJavaDocLines(), "Select a record by criteria,");
    }

    // selectList(BaseCriteria criteria);
    @Test
    public void selectList() {
        method.setName("selectList");
        method.addParameter(buildParam("BaseCriteria", "criteria"));
        decorator.decorate(method);
        assertContainsLine(method.getJavaDocLines(), "Select records by criteria.");
    }

    @Test
    public void countAll() {
        method.setName("countAll");
        decorator.decorate(method);
        assertContainsLine(method.getJavaDocLines(), "Count row number of the whole table.");
    }

    @Test
    public void countByCriteria() {
        method.setName("count");
        method.addParameter(buildParam("BaseCriteria", "criteria"));
        decorator.decorate(method);
        assertContainsLine(method.getJavaDocLines(), "Count row number by criteria.");
    }

    @Test
    public void insert() {
        method.setName("insert");
        method.addParameter(buildParam("BaseRecord", "record"));
        decorator.decorate(method);
        assertContainsLine(method.getJavaDocLines(), "Insert non-null fields into the table,");
    }

}
