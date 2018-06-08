package org.nalby.yobatis.core.mybatis.factory;

import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public interface AbstractDomainMethodFactory {

    Method createToString(TopLevelClass baseDomain);

    Method createCopy(TopLevelClass baseDomain);
}
