/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxb;

import javax.xml.bind.MarshalException;

public interface MarshallerEventHandler {
    public void onMarshalComplete() throws MarshalException;
}

