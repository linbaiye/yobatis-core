package org.nalby.yobatis.core.mybatis.factory.clazz;

import org.junit.Test;
import org.nalby.yobatis.core.mybatis.clazz.BracketCriteria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BracketCriteriaTests {

    @Test
    public void test() {
        BracketCriteria criteria = BracketCriteria.newInstance();
        assertTrue(criteria.isStatic());
        assertEquals("BracketCriteria", criteria.getType().getShortName());
    }
}
