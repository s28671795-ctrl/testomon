/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxb;

import javax.xml.bind.UnmarshalException;

public interface UnmarshallerEventHandler {
    public void onUnmarshalComplete() throws UnmarshalException;
}

