/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.xjc.runtime;

import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.cxf.xjc.runtime.JAXBToStringStyleImpl;

public final class JAXBToStringStyle {
    public static final ToStringStyle MULTI_LINE_STYLE = new JAXBToStringStyleImpl(true);
    public static final ToStringStyle SIMPLE_STYLE = new JAXBToStringStyleImpl(false);
    public static final ToStringStyle DEFAULT_STYLE = new JAXBToStringStyleImpl();

    private JAXBToStringStyle() {
    }
}

