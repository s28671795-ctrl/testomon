/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.simple;

import org.apache.cxf.frontend.AbstractServiceFactory;
import org.apache.cxf.wsdl.service.factory.ReflectionServiceFactoryBean;

public class SimpleServiceBuilder
extends AbstractServiceFactory {
    public SimpleServiceBuilder() {
        super(new ReflectionServiceFactoryBean());
    }
}

