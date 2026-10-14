/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.service.blueprint.container.Converter
 *  org.osgi.service.blueprint.container.ReifiedType
 */
package org.apache.cxf.configuration.blueprint;

import org.apache.cxf.interceptor.Interceptor;
import org.osgi.service.blueprint.container.Converter;
import org.osgi.service.blueprint.container.ReifiedType;

public class InterceptorTypeConverter
implements Converter {
    public boolean canConvert(Object sourceObject, ReifiedType targetType) {
        return sourceObject instanceof Interceptor && targetType.getRawClass().isInstance(sourceObject);
    }

    public Object convert(Object sourceObject, ReifiedType targetType) throws Exception {
        return sourceObject;
    }
}

