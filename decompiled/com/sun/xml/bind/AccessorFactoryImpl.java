/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.bind;

import com.sun.xml.bind.InternalAccessorFactory;
import com.sun.xml.bind.v2.runtime.reflect.Accessor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AccessorFactoryImpl
implements InternalAccessorFactory {
    private static AccessorFactoryImpl instance = new AccessorFactoryImpl();

    private AccessorFactoryImpl() {
    }

    public static AccessorFactoryImpl getInstance() {
        return instance;
    }

    @Override
    public Accessor createFieldAccessor(Class bean, Field field2, boolean readOnly) {
        return readOnly ? new Accessor.ReadOnlyFieldReflection(field2) : new Accessor.FieldReflection(field2);
    }

    @Override
    public Accessor createFieldAccessor(Class bean, Field field2, boolean readOnly, boolean supressWarning) {
        return readOnly ? new Accessor.ReadOnlyFieldReflection(field2, supressWarning) : new Accessor.FieldReflection(field2, supressWarning);
    }

    @Override
    public Accessor createPropertyAccessor(Class bean, Method getter2, Method setter2) {
        if (getter2 == null) {
            return new Accessor.SetterOnlyReflection(setter2);
        }
        if (setter2 == null) {
            return new Accessor.GetterOnlyReflection(getter2);
        }
        return new Accessor.GetterSetterReflection(getter2, setter2);
    }
}

