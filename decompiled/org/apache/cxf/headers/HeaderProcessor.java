/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.headers;

import org.apache.cxf.databinding.DataBinding;

public interface HeaderProcessor {
    public String getNamespace();

    public DataBinding getDataBinding();
}

