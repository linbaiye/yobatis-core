package org.nalby.yobatis.core.mybatis.method;

import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public interface DomainMethodFactory {

    Method createToString(TopLevelClass baseDomain);

    Method createCopy(TopLevelClass baseDomain);
}
