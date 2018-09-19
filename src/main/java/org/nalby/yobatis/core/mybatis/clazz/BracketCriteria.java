package org.nalby.yobatis.core.mybatis.clazz;

import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.nalby.yobatis.core.mybatis.factory.*;
import org.nalby.yobatis.core.mybatis.method.CommonMethodFactory;
import org.nalby.yobatis.core.mybatis.method.CommonMethodFactoryImpl;
import org.nalby.yobatis.core.mybatis.method.ConstantMethod;

import java.util.LinkedList;
import java.util.List;

public class BracketCriteria extends InnerClass {

    private final static List<ConstantMethod> METHOD_LIST = new LinkedList<>();

    static {
        METHOD_LIST.add(ConstantMethod.IS_VALID);
        METHOD_LIST.add(ConstantMethod.GET_CRITERIA);
        METHOD_LIST.add(ConstantMethod.ADD_CRITERION_1_PARAM);
        METHOD_LIST.add(ConstantMethod.ADD_CRITERION_3_PARAM);
        METHOD_LIST.add(ConstantMethod.ADD_CRITERION_4_PARAM);
        METHOD_LIST.add(ConstantMethod.ADD_DATE_3_PARAM);
        METHOD_LIST.add(ConstantMethod.ADD_DATE_LIST_PARAM);
        METHOD_LIST.add(ConstantMethod.ADD_DATE_BETWEEN_PARAM);
        METHOD_LIST.add(ConstantMethod.ADD_TIME_3_PARAM);
        METHOD_LIST.add(ConstantMethod.ADD_TIME_LIST_PARAM);
        METHOD_LIST.add(ConstantMethod.ADD_TIME_BETWEEN_PARAM);
    }

    private BracketCriteria() {
        super("BracketCriteria");
        this.setAbstract(false);
        this.setStatic(true);
        this.setVisibility(JavaVisibility.PUBLIC);
    }

    public static BracketCriteria newInstance() {
        CommonMethodFactory commonMethodFactory = CommonMethodFactoryImpl.getInstance();
        FieldFactory fieldFactory = FieldFactoryImpl.getInstance();

        BracketCriteria clz = new BracketCriteria();

        clz.addField(fieldFactory.privateField("criteria", "List<Criterion>"));

        clz.addMethod(commonMethodFactory.constructor("BracketCriteria", "criteria = new ArrayList<Criterion>();"));

        for (ConstantMethod method : METHOD_LIST) {
            clz.addMethod(method.get());
        }
        return clz;
    }
}
