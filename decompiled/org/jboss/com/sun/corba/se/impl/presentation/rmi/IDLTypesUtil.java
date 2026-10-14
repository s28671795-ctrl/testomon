/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import java.io.Externalizable;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.IDLType;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.IDLTypeException;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.IDLEntity;

public final class IDLTypesUtil {
    private static final String GET_PROPERTY_PREFIX = "get";
    private static final String SET_PROPERTY_PREFIX = "set";
    private static final String IS_PROPERTY_PREFIX = "is";
    public static final int VALID_TYPE = 0;
    public static final int INVALID_TYPE = 1;
    public static final boolean FOLLOW_RMIC = true;

    public void validateRemoteInterface(Class<?> c) throws IDLTypeException {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        if (!c.isInterface()) {
            String msg = "Class " + c + " must be a java interface.";
            throw new IDLTypeException(msg);
        }
        if (!Remote.class.isAssignableFrom(c)) {
            String msg = "Class " + c + " must extend java.rmi.Remote, either directly or indirectly.";
            throw new IDLTypeException(msg);
        }
        Method[] methods = c.getMethods();
        for (int i = 0; i < methods.length; ++i) {
            Method next = methods[i];
            this.validateExceptions(next);
        }
        this.validateConstants(c);
    }

    public boolean isRemoteInterface(Class<?> c) {
        boolean remoteInterface = true;
        try {
            this.validateRemoteInterface(c);
        }
        catch (IDLTypeException ite) {
            remoteInterface = false;
        }
        return remoteInterface;
    }

    public boolean isPrimitive(Class<?> c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        return c.isPrimitive();
    }

    public boolean isValue(Class<?> c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        return !c.isInterface() && Serializable.class.isAssignableFrom(c) && !Remote.class.isAssignableFrom(c);
    }

    public boolean isArray(Class<?> c) {
        boolean arrayType = false;
        if (c == null) {
            throw new IllegalArgumentException();
        }
        if (c.isArray()) {
            Class<?> componentType = c.getComponentType();
            arrayType = this.isPrimitive(componentType) || this.isRemoteInterface(componentType) || this.isEntity(componentType) || this.isException(componentType) || this.isValue(componentType) || this.isObjectReference(componentType);
        }
        return arrayType;
    }

    public boolean isException(Class<?> c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        return this.isCheckedException(c) && !this.isRemoteException(c) && this.isValue(c);
    }

    public boolean isRemoteException(Class<?> c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        return RemoteException.class.isAssignableFrom(c);
    }

    public boolean isCheckedException(Class<?> c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        return Throwable.class.isAssignableFrom(c) && !RuntimeException.class.isAssignableFrom(c) && !Error.class.isAssignableFrom(c);
    }

    public boolean isObjectReference(Class<?> c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        return c.isInterface() && Object.class.isAssignableFrom(c);
    }

    public boolean isEntity(Class<?> c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        Class<?> superClass = c.getSuperclass();
        return !c.isInterface() && superClass != null && IDLEntity.class.isAssignableFrom(c);
    }

    public boolean isPropertyAccessorMethod(Method m4, Class<?> c) {
        String methodName = m4.getName();
        Class<?> returnType = m4.getReturnType();
        Class<?>[] parameters = m4.getParameterTypes();
        String propertyType = null;
        if (methodName.startsWith(GET_PROPERTY_PREFIX)) {
            if (parameters.length == 0 && returnType != Void.TYPE && !this.readHasCorrespondingIsProperty(m4, c)) {
                propertyType = GET_PROPERTY_PREFIX;
            }
        } else if (methodName.startsWith(SET_PROPERTY_PREFIX)) {
            if (returnType == Void.TYPE && parameters.length == 1 && (this.hasCorrespondingReadProperty(m4, c, GET_PROPERTY_PREFIX) || this.hasCorrespondingReadProperty(m4, c, IS_PROPERTY_PREFIX))) {
                propertyType = SET_PROPERTY_PREFIX;
            }
        } else if (methodName.startsWith(IS_PROPERTY_PREFIX) && parameters.length == 0 && returnType == Boolean.TYPE && !this.isHasCorrespondingReadProperty(m4, c)) {
            propertyType = IS_PROPERTY_PREFIX;
        }
        if (!(propertyType == null || this.validPropertyExceptions(m4) && methodName.length() > propertyType.length())) {
            propertyType = null;
        }
        return propertyType != null;
    }

    private boolean hasCorrespondingReadProperty(Method writeProperty, Class<?> c, String readPropertyPrefix) {
        String writePropertyMethodName = writeProperty.getName();
        Class<?>[] writePropertyParameters = writeProperty.getParameterTypes();
        boolean foundReadProperty = false;
        try {
            String readPropertyMethodName = writePropertyMethodName.replaceFirst(SET_PROPERTY_PREFIX, readPropertyPrefix);
            Method readPropertyMethod = c.getMethod(readPropertyMethodName, new Class[0]);
            foundReadProperty = this.isPropertyAccessorMethod(readPropertyMethod, c) && readPropertyMethod.getReturnType() == writePropertyParameters[0];
        }
        catch (Exception exception) {
            // empty catch block
        }
        return foundReadProperty;
    }

    private boolean readHasCorrespondingIsProperty(Method readProperty, Class<?> c) {
        return false;
    }

    private boolean isHasCorrespondingReadProperty(Method readProperty, Class<?> c) {
        String readPropertyMethodName = readProperty.getName();
        boolean foundIsProperty = false;
        try {
            String isPropertyMethodName = readPropertyMethodName.replaceFirst(IS_PROPERTY_PREFIX, GET_PROPERTY_PREFIX);
            Method isPropertyMethod = c.getMethod(isPropertyMethodName, new Class[0]);
            foundIsProperty = this.isPropertyAccessorMethod(isPropertyMethod, c);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return foundIsProperty;
    }

    public String getAttributeNameForProperty(String propertyName) {
        String attributeName = null;
        String prefix = null;
        if (propertyName.startsWith(GET_PROPERTY_PREFIX)) {
            prefix = GET_PROPERTY_PREFIX;
        } else if (propertyName.startsWith(SET_PROPERTY_PREFIX)) {
            prefix = SET_PROPERTY_PREFIX;
        } else if (propertyName.startsWith(IS_PROPERTY_PREFIX)) {
            prefix = IS_PROPERTY_PREFIX;
        }
        if (prefix != null && prefix.length() < propertyName.length()) {
            String remainder = propertyName.substring(prefix.length());
            attributeName = remainder.length() >= 2 && Character.isUpperCase(remainder.charAt(0)) && Character.isUpperCase(remainder.charAt(1)) ? remainder : Character.toLowerCase(remainder.charAt(0)) + remainder.substring(1);
        }
        return attributeName;
    }

    public IDLType getPrimitiveIDLTypeMapping(Class<?> c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        if (c.isPrimitive()) {
            if (c == Void.TYPE) {
                return new IDLType(c, "void");
            }
            if (c == Boolean.TYPE) {
                return new IDLType(c, "boolean");
            }
            if (c == Character.TYPE) {
                return new IDLType(c, "wchar");
            }
            if (c == Byte.TYPE) {
                return new IDLType(c, "octet");
            }
            if (c == Short.TYPE) {
                return new IDLType(c, "short");
            }
            if (c == Integer.TYPE) {
                return new IDLType(c, "long");
            }
            if (c == Long.TYPE) {
                return new IDLType(c, "long_long");
            }
            if (c == Float.TYPE) {
                return new IDLType(c, "float");
            }
            if (c == Double.TYPE) {
                return new IDLType(c, "double");
            }
        }
        return null;
    }

    public IDLType getSpecialCaseIDLTypeMapping(Class<?> c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        if (c == java.lang.Object.class) {
            return new IDLType(c, new String[]{"java", "lang"}, "Object");
        }
        if (c == String.class) {
            return new IDLType(c, new String[]{"CORBA"}, "WStringValue");
        }
        if (c == Class.class) {
            return new IDLType(c, new String[]{"javax", "rmi", "CORBA"}, "ClassDesc");
        }
        if (c == Serializable.class) {
            return new IDLType(c, new String[]{"java", "io"}, "Serializable");
        }
        if (c == Externalizable.class) {
            return new IDLType(c, new String[]{"java", "io"}, "Externalizable");
        }
        if (c == Remote.class) {
            return new IDLType(c, new String[]{"java", "rmi"}, "Remote");
        }
        if (c == Object.class) {
            return new IDLType(c, "Object");
        }
        return null;
    }

    private void validateExceptions(Method method2) throws IDLTypeException {
        Class<?> exception;
        int eIndex;
        Class<?>[] exceptions = method2.getExceptionTypes();
        boolean declaresRemoteExceptionOrSuperClass = false;
        for (eIndex = 0; eIndex < exceptions.length; ++eIndex) {
            exception = exceptions[eIndex];
            if (!this.isRemoteExceptionOrSuperClass(exception)) continue;
            declaresRemoteExceptionOrSuperClass = true;
            break;
        }
        if (!declaresRemoteExceptionOrSuperClass) {
            String msg = "Method '" + method2 + "' must throw at least one exception of type java.rmi.RemoteException or one of its super-classes";
            throw new IDLTypeException(msg);
        }
        for (eIndex = 0; eIndex < exceptions.length; ++eIndex) {
            exception = exceptions[eIndex];
            if (!this.isCheckedException(exception) || this.isValue(exception) || this.isRemoteException(exception)) continue;
            String msg = "Exception '" + exception + "' on method '" + method2 + "' is not a allowed RMI/IIOP exception type";
            throw new IDLTypeException(msg);
        }
    }

    private boolean validPropertyExceptions(Method method2) {
        Class<?>[] exceptions = method2.getExceptionTypes();
        for (int eIndex = 0; eIndex < exceptions.length; ++eIndex) {
            Class<?> exception = exceptions[eIndex];
            if (!this.isCheckedException(exception) || this.isRemoteException(exception)) continue;
            return false;
        }
        return true;
    }

    private boolean isRemoteExceptionOrSuperClass(Class<?> c) {
        return c == RemoteException.class || c == IOException.class || c == Exception.class || c == Throwable.class;
    }

    private void validateConstants(final Class<?> c) throws IDLTypeException {
        Field[] fields = null;
        try {
            fields = AccessController.doPrivileged(new PrivilegedExceptionAction<Field[]>(){

                @Override
                public Field[] run() throws Exception {
                    return c.getFields();
                }
            });
        }
        catch (PrivilegedActionException pae) {
            IDLTypeException ite = new IDLTypeException();
            ite.initCause(pae);
            throw ite;
        }
        for (int i = 0; i < fields.length; ++i) {
            Field next = fields[i];
            Class<?> fieldType = next.getType();
            if (fieldType == String.class || this.isPrimitive(fieldType)) continue;
            String msg = "Constant field '" + next.getName() + "' in class '" + next.getDeclaringClass().getName() + "' has invalid type' " + next.getType() + "'. Constants in RMI/IIOP interfaces can only have primitive types and java.lang.String types.";
            throw new IDLTypeException(msg);
        }
    }
}

