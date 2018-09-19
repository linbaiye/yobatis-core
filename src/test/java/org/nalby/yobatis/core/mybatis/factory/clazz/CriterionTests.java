package org.nalby.yobatis.core.mybatis.factory.clazz;

import org.junit.Test;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.nalby.yobatis.core.mybatis.clazz.Criterion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CriterionTests {


    @Test
    public void test() {
        Criterion criterion = Criterion.newInstance();
        assertTrue(criterion.isStatic());
        assertEquals(JavaVisibility.PUBLIC, criterion.getVisibility());
    }

}
