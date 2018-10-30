package org.nalby.yobatis.core.mybatis.method;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FactoryMethodNameTests {

    @Test
    public void listByGroup() {
        List<String> names = FactoryMethodName.listMethodNamesByGroup(FactoryMethodName.DAO_GROUP);
        assertFalse(names.contains("NOT_NULL"));
        names = FactoryMethodName.listMethodNamesByGroup(FactoryMethodName.DAO_IMPL_GROUP);
        assertTrue(names.contains("NOT_NULL"));
        names = FactoryMethodName.listMethodNamesByGroup(0);
        assertTrue(names.isEmpty());
    }
}
