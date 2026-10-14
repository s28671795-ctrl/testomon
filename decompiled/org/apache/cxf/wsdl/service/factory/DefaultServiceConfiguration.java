/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl.service.factory;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import javax.xml.namespace.QName;
import org.apache.cxf.helpers.ServiceUtils;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.service.factory.ServiceConstructionException;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.wsdl.service.factory.AbstractServiceConfiguration;
import org.apache.cxf.wsdl.service.factory.ParamReader;

public class DefaultServiceConfiguration
extends AbstractServiceConfiguration {
    @Override
    public QName getOperationName(InterfaceInfo service2, Method method2) {
        boolean fromWsdl = this.getServiceFactory().isFromWsdl();
        String ns = service2.getName().getNamespaceURI();
        String local = method2.getName();
        QName name = new QName(ns, local);
        if (fromWsdl && service2.getOperation(name) != null) {
            return name;
        }
        if (service2.getOperation(name) == null) {
            return name;
        }
        int i = 1;
        while (service2.getOperation(name = new QName(ns, local + i)) != null) {
            ++i;
        }
        return name;
    }

    @Override
    public QName getFaultName(InterfaceInfo service2, OperationInfo o, Class<?> exClass, Class<?> beanClass) {
        String name = ServiceUtils.makeServiceNameFromClassName(beanClass);
        return new QName(service2.getName().getNamespaceURI(), name);
    }

    @Override
    public QName getInPartName(OperationInfo op, Method method2, int paramNumber) {
        return this.getInParameterName(op, method2, paramNumber);
    }

    @Override
    public QName getOutPartName(OperationInfo op, Method method2, int paramNumber) {
        return this.getOutParameterName(op, method2, paramNumber);
    }

    @Override
    public QName getInParameterName(OperationInfo op, Method method2, int paramNumber) {
        return new QName(op.getName().getNamespaceURI(), this.getDefaultLocalName(op, method2, paramNumber, "arg"));
    }

    @Override
    public QName getInputMessageName(OperationInfo op, Method method2) {
        return new QName(op.getName().getNamespaceURI(), op.getName().getLocalPart());
    }

    @Override
    public QName getOutParameterName(OperationInfo op, Method method2, int paramNumber) {
        return new QName(op.getName().getNamespaceURI(), this.getDefaultLocalName(op, method2, paramNumber, "return"));
    }

    private String getDefaultLocalName(OperationInfo op, Method method2, int paramNumber, String prefix) {
        Class<?> impl = this.getServiceFactory().getServiceClass();
        if (impl != null) {
            try {
                method2 = impl.getMethod(method2.getName(), method2.getParameterTypes());
            }
            catch (Exception e) {
                throw new ServiceConstructionException(e);
            }
        }
        return DefaultServiceConfiguration.createName(method2, paramNumber, op.getInput().getMessagePartsNumber(), false, prefix);
    }

    public static String createName(Method method2, int paramNumber, int currentSize, boolean addMethodName, String flow) {
        String paramName = "";
        if (paramNumber != -1) {
            String[] names = ParamReader.getParameterNamesFromDebugInfo(method2);
            if (names != null && names[paramNumber] != null) {
                paramName = names[paramNumber];
                addMethodName = false;
            } else {
                paramName = flow + currentSize;
            }
        } else {
            paramName = flow;
        }
        paramName = addMethodName ? method2.getName() + paramName : paramName;
        return paramName;
    }

    @Override
    public QName getOutputMessageName(OperationInfo op, Method method2) {
        return new QName(op.getName().getNamespaceURI(), op.getName().getLocalPart() + "Response");
    }

    @Override
    public QName getInterfaceName() {
        return new QName(this.getServiceFactory().getServiceNamespace(), this.getServiceName() + "PortType");
    }

    @Override
    public QName getEndpointName() {
        return new QName(this.getServiceFactory().getServiceNamespace(), this.getServiceName() + "Port");
    }

    @Override
    public String getServiceName() {
        return this.getServiceFactory().getServiceClass().getSimpleName();
    }

    @Override
    public String getServiceNamespace() {
        String ret = super.getServiceNamespace();
        if (ret == null && this.getServiceFactory() != null && this.getServiceFactory().getServiceClass() != null) {
            ret = ServiceUtils.makeNamespaceFromClassName(this.getServiceFactory().getServiceClass().getName(), "http");
        }
        return ret;
    }

    @Override
    public Boolean hasOutMessage(Method m4) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean isAsync(Method method2) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean isHeader(Method method2, int j) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean isInParam(Method method2, int j) {
        if (j >= 0) {
            Class<?> c = method2.getParameterTypes()[j];
            if (Exchange.class.equals(c)) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Boolean isOperation(Method method2) {
        if (this.getServiceFactory().getIgnoredClasses().contains(method2.getDeclaringClass().getName())) {
            return Boolean.FALSE;
        }
        for (Method m4 : this.getServiceFactory().getIgnoredMethods()) {
            if (!m4.getName().equals(method2.getName()) || !Arrays.equals(method2.getParameterTypes(), m4.getParameterTypes()) || m4.getReturnType() != method2.getReturnType()) continue;
            return Boolean.FALSE;
        }
        int modifiers = method2.getModifiers();
        if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers) && !method2.isSynthetic()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean isOutParam(Method method2, int j) {
        if (j < 0) {
            return Boolean.TRUE;
        }
        Class<?> cls = method2.getParameterTypes()[j];
        Type tp = method2.getGenericParameterTypes()[j];
        return this.isHolder(cls, tp);
    }

    @Override
    public Boolean isWrapped(Method m4) {
        return this.getServiceFactory().isWrapped();
    }

    @Override
    public Boolean isHolder(Class<?> cls, Type type) {
        if ("Holder".equals(cls.getSimpleName())) {
            for (Field f : cls.getDeclaredFields()) {
                if (Modifier.isStatic(f.getModifiers()) || !Modifier.isPublic(f.getModifiers()) || !"value".equals(f.getName())) continue;
                return Boolean.TRUE;
            }
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Class<?> getHolderType(Class<?> cls, Type type) {
        if (this.isHolder(cls, type).booleanValue()) {
            if (type instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType)type;
                cls = DefaultServiceConfiguration.getHolderClass(paramType);
            } else {
                return cls.getDeclaredFields()[0].getType();
            }
        }
        return null;
    }

    private static Class<?> getHolderClass(ParameterizedType paramType) {
        Class<?> rawClass;
        Type rawType = paramType.getActualTypeArguments()[0];
        if (rawType instanceof GenericArrayType) {
            rawClass = (Class<?>)((GenericArrayType)rawType).getGenericComponentType();
            rawClass = Array.newInstance(rawClass, 0).getClass();
        } else {
            if (rawType instanceof ParameterizedType) {
                rawType = ((ParameterizedType)rawType).getRawType();
            }
            rawClass = (Class)rawType;
        }
        return rawClass;
    }

    @Override
    public Boolean isWrapperPartNillable(MessagePartInfo mpi) {
        return (Boolean)mpi.getProperty("nillable");
    }

    @Override
    public Long getWrapperPartMaxOccurs(MessagePartInfo mpi) {
        String miString = (String)mpi.getProperty("maxOccurs");
        if (miString != null) {
            if ("unbounded".equals(miString)) {
                return Long.MAX_VALUE;
            }
            return Long.valueOf(miString, 10);
        }
        if (mpi.getTypeClass() != null && mpi.getTypeClass().isArray() && !Byte.TYPE.equals(mpi.getTypeClass().getComponentType())) {
            return Long.MAX_VALUE;
        }
        return null;
    }

    @Override
    public Long getWrapperPartMinOccurs(MessagePartInfo mpi) {
        String miString = (String)mpi.getProperty("minOccurs");
        if (miString != null) {
            return Long.valueOf(miString, 10);
        }
        if (mpi.getTypeClass() != null && !mpi.getTypeClass().isPrimitive()) {
            return 0L;
        }
        return null;
    }
}

