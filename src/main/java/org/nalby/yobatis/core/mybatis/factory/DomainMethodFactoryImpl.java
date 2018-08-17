package org.nalby.yobatis.core.mybatis.factory;

import org.mybatis.generator.api.dom.java.*;

public class DomainMethodFactoryImpl implements  AbstractDomainMethodFactory {

    private static DomainMethodFactoryImpl publicMethodImplFactory = new DomainMethodFactoryImpl();

    private DomainMethodFactoryImpl() { }

    public static DomainMethodFactoryImpl getInstance() {
        return publicMethodImplFactory;
    }

    @Override
    public Method createToString(TopLevelClass baseDomain) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.setName("toString");
        method.addAnnotation("@Override");
        method.addBodyLine("StringBuilder sb = new StringBuilder();");
        method.addBodyLine("sb.append(getClass().getSimpleName());");
        method.addBodyLine("sb.append(\"[\");");
        StringBuilder sb = new StringBuilder();
        boolean needComma = false;
        for (Field field : baseDomain.getFields()) {
            String property = field.getName();
            sb.setLength(0);
            sb.append("sb.append(\"");
            if (needComma) {
                sb.append(", ");
            }
            sb.append(property)
                    .append("=\")").append(".append(").append(property)
                    .append(");");
            needComma = true;
            method.addBodyLine(sb.toString());
        }
        method.addBodyLine("sb.append(\"]\");");
        method.addBodyLine("return sb.toString();");
        return method;
    }

    @Override
    public Method createCopy(TopLevelClass baseDomain) {
        Method method = new Method();
        method.setName("copy");
        method.setReturnType(baseDomain.getType());
        method.setVisibility(JavaVisibility.PUBLIC);

        method.addParameter(new Parameter(baseDomain.getType(), "dest"));
        method.addBodyLine("if (dest == null) {");
        method.addBodyLine("throw new NullPointerException(\"dest must not be null.\");");
        method.addBodyLine("}");
        for (Field field : baseDomain.getFields()) {
            method.addBodyLine("dest." + field.getName() + " = this." + field.getName() + ";");
        }
        method.addBodyLine("return dest;");
        method.addAnnotation("/**");
        method.addAnnotation(" * Copy properties of this object to {@code dest} object.");
        method.addAnnotation(" * @param dest the object to copy properties to.");
        method.addAnnotation(" * @return the dest object.");
        method.addAnnotation(" */");
        return method;
    }
}
