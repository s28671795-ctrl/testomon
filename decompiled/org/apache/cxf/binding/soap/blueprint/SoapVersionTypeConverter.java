/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.service.blueprint.container.Converter
 *  org.osgi.service.blueprint.container.ReifiedType
 */
package org.apache.cxf.binding.soap.blueprint;

import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.SoapVersion;
import org.osgi.service.blueprint.container.Converter;
import org.osgi.service.blueprint.container.ReifiedType;

public class SoapVersionTypeConverter
implements Converter {
    public boolean canConvert(Object sourceObject, ReifiedType targetType) {
        return SoapVersion.class.isAssignableFrom(targetType.getRawClass());
    }

    public Object convert(Object sourceObject, ReifiedType targetType) throws Exception {
        if ("1.2".equals(sourceObject)) {
            return Soap12.getInstance();
        }
        if ("1.1".equals(sourceObject)) {
            return Soap11.getInstance();
        }
        throw new IllegalArgumentException("Unimplemented SOAP version requested.");
    }
}

