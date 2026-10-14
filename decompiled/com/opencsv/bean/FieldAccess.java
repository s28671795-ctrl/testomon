/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.function.AccessorInvoker;
import com.opencsv.bean.function.AssignmentInvoker;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import org.apache.commons.lang3.reflect.FieldUtils;

public class FieldAccess<T> {
    private final Field field;
    private final AccessorInvoker<Object, T> accessor;
    private final AssignmentInvoker<Object, T> assignment;

    public FieldAccess(Field field2) {
        this.field = field2;
        this.accessor = this.determineAccessorMethod();
        this.assignment = this.determineAssignmentMethod();
    }

    private AccessorInvoker<Object, T> determineAccessorMethod() {
        AccessorInvoker<Object, Object> localAccessor;
        String getterName = "get" + Character.toUpperCase(this.field.getName().charAt(0)) + this.field.getName().substring(1);
        try {
            Method getterMethod = this.field.getDeclaringClass().getMethod(getterName, new Class[0]);
            localAccessor = getterMethod.getReturnType().equals(Optional.class) ? bean -> {
                Optional opt = (Optional)getterMethod.invoke(bean, new Object[0]);
                return opt.orElse(null);
            } : bean -> getterMethod.invoke(bean, new Object[0]);
        }
        catch (NoSuchMethodException e) {
            localAccessor = bean -> FieldUtils.readField(this.field, bean, true);
        }
        return localAccessor;
    }

    private AssignmentInvoker<Object, T> determineAssignmentMethod() {
        AssignmentInvoker<Object, Object> localAssignment;
        String setterName = "set" + Character.toUpperCase(this.field.getName().charAt(0)) + this.field.getName().substring(1);
        try {
            Method setterMethod = this.field.getDeclaringClass().getMethod(setterName, this.field.getType());
            localAssignment = (x$0, xva$1) -> setterMethod.invoke(x$0, xva$1);
        }
        catch (NoSuchMethodException e1) {
            try {
                Method setterMethod = this.field.getDeclaringClass().getMethod(setterName, Optional.class);
                localAssignment = (bean, value) -> setterMethod.invoke(bean, Optional.ofNullable(value));
            }
            catch (NoSuchMethodException e2) {
                localAssignment = (bean, value) -> FieldUtils.writeField(this.field, bean, value, true);
            }
        }
        return localAssignment;
    }

    public T getField(Object bean) throws IllegalAccessException, InvocationTargetException {
        return this.accessor.invoke(bean);
    }

    public void setField(Object bean, T value) throws IllegalAccessException, InvocationTargetException {
        this.assignment.invoke(bean, value);
    }

    public int hashCode() {
        return this.field.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FieldAccess)) {
            return false;
        }
        return this.field.equals(((FieldAccess)obj).field);
    }

    public String toString() {
        return this.field.toString();
    }
}

