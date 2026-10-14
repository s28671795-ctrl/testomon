/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.databinding;

import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.databinding.WrapperHelper;

public interface WrapperCapableDatabinding {
    public WrapperHelper createWrapperHelper(Class<?> var1, QName var2, List<String> var3, List<String> var4, List<Class<?>> var5);
}

