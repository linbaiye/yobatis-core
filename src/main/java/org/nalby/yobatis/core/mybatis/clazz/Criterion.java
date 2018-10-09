package org.nalby.yobatis.core.mybatis.clazz;

import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.nalby.yobatis.core.mybatis.method.ConstantMethod;

import java.util.LinkedList;
import java.util.List;

public class Criterion extends InnerClass {

    private final static List<ConstantMethod> METHOD_LIST = new LinkedList<>();

    static {
        METHOD_LIST.add(ConstantMethod.GET_CONDITION);
        METHOD_LIST.add(ConstantMethod.GET_VALUE);
        METHOD_LIST.add(ConstantMethod.GET_SECOND_VALUE);
        METHOD_LIST.add(ConstantMethod.IS_NO_VALUE);
        METHOD_LIST.add(ConstantMethod.IS_SINGLE_VALUE);
        METHOD_LIST.add(ConstantMethod.iS_BETWEEN_VALUE);
        METHOD_LIST.add(ConstantMethod.iS_LIST_VALUE);
        METHOD_LIST.add(ConstantMethod.CRITERION_CONDITION);
        METHOD_LIST.add(ConstantMethod.CRITERION_CONDITION_VALUE);
        METHOD_LIST.add(ConstantMethod.CRITERION_CONDITION_TWO_VALUES);
    }

    private Criterion() {
        super("Criterion");
        this.setAbstract(false);
        this.setStatic(true);
        this.setVisibility(JavaVisibility.PUBLIC);
    }

    public static Criterion newInstance() {
        org.nalby.yobatis.core.mybatis.field.FieldFactory fieldFactory = org.nalby.yobatis.core.mybatis.field.FieldFactoryImpl.getInstance();
        Criterion clz = new Criterion();
        clz.addField(fieldFactory.privateField("condition", "String"));
        clz.addField(fieldFactory.privateField("value", "Object"));
        clz.addField(fieldFactory.privateField("secondValue", "Object"));
        clz.addField(fieldFactory.privateField("noValue", "boolean"));
        clz.addField(fieldFactory.privateField("singleValue", "boolean"));
        clz.addField(fieldFactory.privateField("betweenValue", "boolean"));
        clz.addField(fieldFactory.privateField("listValue", "boolean"));
        for (ConstantMethod method : METHOD_LIST) {
            clz.addMethod(method.get());
        }
        return clz;
    }

}