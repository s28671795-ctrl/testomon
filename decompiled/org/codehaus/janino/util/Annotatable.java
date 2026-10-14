/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino.util;

import java.util.Map;
import org.codehaus.janino.util.ClassFile;

public interface Annotatable {
    public ClassFile.Annotation[] getAnnotations(boolean var1);

    public void addAnnotationsAttributeEntry(boolean var1, String var2, Map<Short, ClassFile.ElementValue> var3);
}

