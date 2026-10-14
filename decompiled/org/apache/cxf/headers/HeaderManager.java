/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.headers;

import org.apache.cxf.headers.HeaderProcessor;

public interface HeaderManager {
    public void registerHeaderProcessor(HeaderProcessor var1);

    public HeaderProcessor getHeaderProcessor(String var1);
}

