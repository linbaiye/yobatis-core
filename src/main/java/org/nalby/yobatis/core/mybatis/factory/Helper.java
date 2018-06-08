package org.nalby.yobatis.core.mybatis.factory;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

public final class Helper {
    private Helper() {}

    public static FullyQualifiedJavaType getBaseModelType(FullyQualifiedJavaType model) {
        String fullName = model.getFullyQualifiedName();
        String shortName = model.getShortName();
        return new FullyQualifiedJavaType(fullName.replaceFirst("(" + shortName + ")$", "base.Base$1"));
    }

    public static FullyQualifiedJavaType getBaseModelType(String model) {
        return getBaseModelType(new FullyQualifiedJavaType(model));
    }
}
