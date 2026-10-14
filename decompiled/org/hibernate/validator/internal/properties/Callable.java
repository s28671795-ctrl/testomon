/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.properties;

import java.lang.reflect.Type;
import org.hibernate.validator.internal.properties.Constrainable;
import org.hibernate.validator.internal.properties.Signature;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;

public interface Callable
extends Constrainable {
    public boolean hasReturnValue();

    public boolean hasParameters();

    public int getParameterCount();

    public Type getParameterGenericType(int var1);

    public Class<?>[] getParameterTypes();

    public String getParameterName(ExecutableParameterNameProvider var1, int var2);

    public boolean isPrivate();

    public Signature getSignature();

    public boolean overrides(ExecutableHelper var1, Callable var2);

    public boolean isResolvedToSameMethodInHierarchy(ExecutableHelper var1, Class<?> var2, Callable var3);
}

