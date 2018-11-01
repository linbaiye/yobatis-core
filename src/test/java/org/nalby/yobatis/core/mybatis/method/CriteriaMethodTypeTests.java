package org.nalby.yobatis.core.mybatis.method;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CriteriaMethodTypeTests {

    @Test
    public void makeName() {
        String methodName = CriteriaMethodType.IN.makeMethodName("id");
        assertEquals("andIdIn", methodName);

        methodName = CriteriaMethodType.STATIC_IN.makeMethodName("id");
        assertEquals("idIn", methodName);
    }

    @Test
    public void methodSupport() {
        CriteriaMethodType.GREATER_THAN_OR_EQUAL_TO.isJavaTypeSupported("");
    }
}
