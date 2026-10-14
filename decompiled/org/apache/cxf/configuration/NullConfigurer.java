/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration;

import org.apache.cxf.configuration.Configurer;

public class NullConfigurer
implements Configurer {
    @Override
    public void configureBean(Object beanInstance) {
    }

    @Override
    public void configureBean(String name, Object beanInstance) {
    }
}

