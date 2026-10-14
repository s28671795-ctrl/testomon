/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.spring;

import org.apache.cxf.configuration.spring.AbstractBeanDefinitionParser;

public class SimpleBeanDefinitionParser
extends AbstractBeanDefinitionParser {
    public SimpleBeanDefinitionParser(Class<?> beanClass) {
        this.setBeanClass(beanClass);
    }
}

