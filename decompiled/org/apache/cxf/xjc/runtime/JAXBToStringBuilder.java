/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.xjc.runtime;

import java.util.Collection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

public final class JAXBToStringBuilder {
    private JAXBToStringBuilder() {
    }

    public static String valueOf(Object object) {
        return JAXBToStringBuilder.valueOf(object, JAXBToStringStyle.DEFAULT_STYLE);
    }

    public static String valueOf(Object object, ToStringStyle style) {
        if (object instanceof String) {
            return (String)object;
        }
        if (object instanceof Collection) {
            object = ((Collection)object).toArray();
        }
        return ToStringBuilder.reflectionToString(object, style);
    }
}

