/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl.service.factory;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import javax.xml.namespace.QName;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.wsdl.service.factory.ReflectionServiceFactoryBean;

public abstract class AbstractServiceConfiguration {
    protected String serviceNamespace;
    private ReflectionServiceFactoryBean serviceFactory;

    public ReflectionServiceFactoryBean getServiceFactory() {
        return this.serviceFactory;
    }

    public void setServiceFactory(ReflectionServiceFactoryBean serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    public String getWsdlURL() {
        return null;
    }

    public String getServiceName() {
        return null;
    }

    public String getServiceNamespace() {
        return this.serviceNamespace;
    }

    public void setServiceNamespace(String s2) {
        this.serviceNamespace = s2;
    }

    public Boolean isOperation(Method method2) {
        return null;
    }

    public String getStyle() {
        return null;
    }

    public Boolean isWrapped() {
        return null;
    }

    public Boolean isWrapped(Method m4) {
        return null;
    }

    public Boolean isOutParam(Method method2, int j) {
        return null;
    }

    public Boolean isInParam(Method method2, int j) {
        return null;
    }

    public Boolean isInOutParam(Method method2, int j) {
        return null;
    }

    public QName getInputMessageName(OperationInfo op, Method method2) {
        return null;
    }

    public QName getOutputMessageName(OperationInfo op, Method method2) {
        return null;
    }

    public Boolean hasOutMessage(Method m4) {
        return null;
    }

    public QName getFaultName(InterfaceInfo service2, OperationInfo o, Class<?> exClass, Class<?> beanClass) {
        return null;
    }

    public String getAction(OperationInfo op, Method method2) {
        return null;
    }

    public Boolean isHeader(Method method2, int j) {
        return null;
    }

    public QName getOperationName(InterfaceInfo service2, Method method2) {
        return null;
    }

    public String getMEP(Method method2) {
        return null;
    }

    public Boolean isAsync(Method method2) {
        return null;
    }

    public QName getInParameterName(OperationInfo op, Method method2, int paramNumber) {
        return null;
    }

    public QName getOutParameterName(OperationInfo op, Method method2, int paramNumber) {
        return null;
    }

    public QName getInPartName(OperationInfo op, Method method2, int paramNumber) {
        return null;
    }

    public QName getOutPartName(OperationInfo op, Method method2, int paramNumber) {
        return null;
    }

    public QName getInterfaceName() {
        return null;
    }

    public QName getEndpointName() {
        return null;
    }

    public QName getRequestWrapperName(OperationInfo op, Method method2) {
        return null;
    }

    public QName getResponseWrapperName(OperationInfo op, Method method2) {
        return null;
    }

    public String getResponseWrapperPartName(OperationInfo op, Method method2) {
        return null;
    }

    public String getRequestWrapperPartName(OperationInfo op, Method method2) {
        return null;
    }

    public Class<?> getResponseWrapper(Method selected) {
        return null;
    }

    public Class<?> getRequestWrapper(Method selected) {
        return null;
    }

    public String getResponseWrapperClassName(Method selected) {
        return null;
    }

    public String getRequestWrapperClassName(Method selected) {
        return null;
    }

    public Boolean isRPC(Method selected) {
        return null;
    }

    public Boolean isHolder(Class<?> cls, Type type) {
        return null;
    }

    public Type getHolderType(Class<?> cls, Type type) {
        return null;
    }

    public Boolean isWrapperPartNillable(MessagePartInfo mpi) {
        return null;
    }

    public Boolean isWrapperPartQualified(MessagePartInfo mpi) {
        return null;
    }

    public Long getWrapperPartMaxOccurs(MessagePartInfo mpi) {
        return null;
    }

    public Long getWrapperPartMinOccurs(MessagePartInfo mpi) {
        return null;
    }

    public String getFaultMessageName(OperationInfo op, Class<?> exClass, Class<?> beanClass) {
        return null;
    }
}

