/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.namespace.QName;
import javax.xml.ws.Action;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.Response;
import javax.xml.ws.ResponseWrapper;
import javax.xml.ws.WebFault;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PackageUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxws.JaxWsConfigurationException;
import org.apache.cxf.jaxws.support.JaxWsImplementorInfo;
import org.apache.cxf.jaxws.support.JaxWsServiceFactoryBean;
import org.apache.cxf.service.factory.ServiceConstructionException;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.wsdl.service.factory.AbstractServiceConfiguration;
import org.apache.cxf.wsdl.service.factory.ReflectionServiceFactoryBean;

public class JaxWsServiceConfiguration
extends AbstractServiceConfiguration {
    private static final Logger LOG = LogUtils.getL7dLogger(JaxWsServiceConfiguration.class);
    private JaxWsImplementorInfo implInfo;
    private Map<Object, Class<?>> responseMethodClassCache = new HashMap();
    private Map<Object, Class<?>> requestMethodClassCache = new HashMap();
    private List<Method> responseMethodClassNotFoundCache = new ArrayList<Method>();
    private List<Method> requestMethodClassNotFoundCache = new ArrayList<Method>();
    private Map<Method, Annotation[][]> methodAnnotationCache = new HashMap<Method, Annotation[][]>();

    @Override
    public void setServiceFactory(ReflectionServiceFactoryBean serviceFactory) {
        super.setServiceFactory(serviceFactory);
        this.implInfo = ((JaxWsServiceFactoryBean)serviceFactory).getJaxWsImplementorInfo();
    }

    @Override
    public String getServiceName() {
        QName service2 = this.implInfo.getServiceName();
        return service2.getLocalPart();
    }

    @Override
    public String getServiceNamespace() {
        String ret = super.getServiceNamespace();
        if (ret != null) {
            return ret;
        }
        QName service2 = this.implInfo.getServiceName();
        return service2.getNamespaceURI();
    }

    @Override
    public QName getEndpointName() {
        return this.implInfo.getEndpointName();
    }

    @Override
    public QName getInterfaceName() {
        return this.implInfo.getInterfaceName();
    }

    @Override
    public String getWsdlURL() {
        String wsdlLocation = this.implInfo.getWsdlLocation();
        if (wsdlLocation != null && wsdlLocation.length() > 0) {
            return wsdlLocation;
        }
        return null;
    }

    @Override
    public QName getOperationName(InterfaceInfo intf, Method method2) {
        WebMethod wm = (method2 = this.getDeclaredMethod(method2)).getAnnotation(WebMethod.class);
        if (wm != null) {
            String name = wm.operationName();
            if (name.length() == 0) {
                name = method2.getName();
            }
            return new QName(intf.getName().getNamespaceURI(), name);
        }
        return new QName(intf.getName().getNamespaceURI(), method2.getName());
    }

    public Boolean isWebMethod(Method method2) {
        if (method2 == null || method2.getReturnType().equals(Future.class) || method2.getReturnType().equals(Response.class) || method2.isSynthetic()) {
            return Boolean.FALSE;
        }
        WebMethod wm = method2.getAnnotation(WebMethod.class);
        Class<?> cls = method2.getDeclaringClass();
        if (wm != null && wm.exclude()) {
            return Boolean.FALSE;
        }
        if (wm != null && !wm.exclude() || this.implInfo.getSEIClass() != null && cls.isInterface() && cls.isAssignableFrom(this.implInfo.getSEIClass())) {
            return Boolean.TRUE;
        }
        if (method2.getDeclaringClass().isInterface()) {
            return this.hasWebServiceAnnotation(method2);
        }
        if (this.implInfo.getSEIClass() == null) {
            return this.hasWebServiceAnnotation(method2) && !Modifier.isFinal(method2.getModifiers()) && !Modifier.isStatic(method2.getModifiers());
        }
        return this.implInfo.getSEIClass().isAssignableFrom(method2.getDeclaringClass());
    }

    @Override
    public Boolean isOperation(Method method2) {
        WebMethod wm;
        WebMethod wm2;
        if (Object.class.equals(method2.getDeclaringClass())) {
            return false;
        }
        if (method2.getDeclaringClass() == this.implInfo.getSEIClass() && (wm2 = method2.getAnnotation(WebMethod.class)) != null && wm2.exclude()) {
            Message message = new Message("WEBMETHOD_EXCLUDE_NOT_ALLOWED", LOG, method2.getName());
            throw new JaxWsConfigurationException(message);
        }
        Class<?> implClz = this.implInfo.getImplementorClass();
        Method m4 = this.getDeclaredMethod(implClz, method2);
        if (m4 != null && (wm = m4.getAnnotation(WebMethod.class)) != null && wm.exclude()) {
            return Boolean.FALSE;
        }
        if (this.isWebMethod(m4).booleanValue()) {
            return true;
        }
        return this.isWebMethod(this.getDeclaredMethod(method2));
    }

    private boolean hasWebServiceAnnotation(Method method2) {
        return method2.getDeclaringClass().getAnnotation(WebService.class) != null;
    }

    Method getDeclaredMethod(Method method2) {
        return this.getDeclaredMethod(this.implInfo.getEndpointClass(), method2);
    }

    private Method getDeclaredMethod(Class<?> endpointClass, Method method2) {
        if (!method2.getDeclaringClass().equals(endpointClass)) {
            try {
                method2 = endpointClass.getMethod(method2.getName(), method2.getParameterTypes());
            }
            catch (SecurityException e) {
                throw new ServiceConstructionException(e);
            }
            catch (NoSuchMethodException e) {
                return this.isWebMethod(method2) != false ? method2 : null;
            }
        }
        return method2;
    }

    @Override
    public QName getInPartName(OperationInfo op, Method method2, int paramNumber) {
        if (paramNumber < 0) {
            return null;
        }
        return this.getPartName(op, method2, paramNumber, op.getInput(), "arg", true);
    }

    @Override
    public QName getInParameterName(OperationInfo op, Method method2, int paramNumber) {
        if (paramNumber < 0) {
            return null;
        }
        return this.getParameterName(op, method2, paramNumber, op.getInput().size(), "arg", true);
    }

    private QName getPartName(OperationInfo op, Method method2, int paramNumber, MessageInfo mi, String prefix, boolean isIn) {
        int partIndex = this.getPartIndex(method2, paramNumber, isIn);
        method2 = this.getDeclaredMethod(method2);
        WebParam param2 = this.getWebParam(method2, paramNumber);
        String tns = mi.getName().getNamespaceURI();
        String local = null;
        if (param2 != null) {
            if (Boolean.TRUE.equals(this.isRPC(method2)) || this.isDocumentBare(method2) || param2.header()) {
                local = param2.partName();
            }
            if (local == null || local.length() == 0) {
                local = param2.name();
            }
        }
        if (local == null || local.length() == 0) {
            local = Boolean.TRUE.equals(this.isRPC(method2)) || !Boolean.FALSE.equals(this.isWrapped(method2)) ? this.getDefaultLocalName(op, method2, paramNumber, partIndex, prefix) : this.getOperationName(op.getInterface(), method2).getLocalPart();
        }
        return new QName(tns, local);
    }

    private int getPartIndex(Method method2, int paraNumber, boolean isIn) {
        int i;
        int ret = 0;
        if (isIn && this.isInParam(method2, paraNumber).booleanValue()) {
            for (i = 0; i < paraNumber; ++i) {
                if (!this.isInParam(method2, i).booleanValue()) continue;
                ++ret;
            }
        }
        if (!isIn && this.isOutParam(method2, paraNumber).booleanValue()) {
            if (method2.getReturnType() != Void.class && method2.getReturnType() != Void.TYPE) {
                ++ret;
            }
            for (i = 0; i < paraNumber; ++i) {
                if (!this.isOutParam(method2, i).booleanValue()) continue;
                ++ret;
            }
        }
        return ret;
    }

    private QName getParameterName(OperationInfo op, Method method2, int paramNumber, int curSize, String prefix, boolean input) {
        int partIndex = this.getPartIndex(method2, paramNumber, input);
        method2 = this.getDeclaredMethod(method2);
        WebParam param2 = this.getWebParam(method2, paramNumber);
        String tns = null;
        String local = null;
        if (param2 != null) {
            tns = param2.targetNamespace();
            local = param2.name();
        }
        if (tns == null || tns.length() == 0) {
            QName wrappername = null;
            wrappername = input ? this.getRequestWrapperName(op, method2) : this.getResponseWrapperName(op, method2);
            if (wrappername != null) {
                tns = wrappername.getNamespaceURI();
            }
        }
        if (tns == null || tns.length() == 0) {
            tns = op.getName().getNamespaceURI();
        }
        if (local == null || local.length() == 0) {
            if (Boolean.TRUE.equals(this.isRPC(method2)) || !Boolean.FALSE.equals(this.isWrapped(method2))) {
                local = this.getDefaultLocalName(op, method2, paramNumber, partIndex, prefix);
            } else {
                local = this.getOperationName(op.getInterface(), method2).getLocalPart();
                if (!input) {
                    local = local + "Response";
                }
            }
        }
        return new QName(tns, local);
    }

    private String getDefaultLocalName(OperationInfo op, Method method2, int paramNumber, int partIndex, String prefix) {
        String paramName = null;
        paramName = paramNumber != -1 ? prefix + partIndex : prefix;
        return paramName;
    }

    private WebParam getWebParam(Method method2, int parameter) {
        Annotation[][] annotations = this.methodAnnotationCache.get(method2);
        if (annotations == null) {
            annotations = method2.getParameterAnnotations();
            this.methodAnnotationCache.put(method2, annotations);
        }
        if (parameter >= annotations.length) {
            return null;
        }
        for (int i = 0; i < annotations[parameter].length; ++i) {
            Annotation annotation = annotations[parameter][i];
            if (!(annotation instanceof WebParam)) continue;
            return (WebParam)annotation;
        }
        return null;
    }

    @Override
    public String getRequestWrapperPartName(OperationInfo op, Method method2) {
        RequestWrapper rw = (method2 = this.getDeclaredMethod(method2)).getAnnotation(RequestWrapper.class);
        if (rw != null) {
            return this.getWithReflection(RequestWrapper.class, rw, "partName");
        }
        return null;
    }

    @Override
    public String getResponseWrapperPartName(OperationInfo op, Method method2) {
        String pn;
        method2 = this.getDeclaredMethod(method2);
        WebResult webResult = this.getWebResult(method2);
        ResponseWrapper rw = method2.getAnnotation(ResponseWrapper.class);
        if (rw != null && (pn = this.getWithReflection(ResponseWrapper.class, rw, "partName")) != null) {
            return pn;
        }
        int countOut = 0;
        int countHeaders = 0;
        if (webResult != null && webResult.header()) {
            ++countHeaders;
        } else if (method2.getReturnType() != Void.TYPE) {
            ++countOut;
        }
        for (int x = 0; x < method2.getParameterTypes().length; ++x) {
            WebParam parm = this.getWebParam(method2, x);
            if (parm == null) continue;
            if (parm.header()) {
                ++countHeaders;
            }
            if (parm.mode() == WebParam.Mode.IN) continue;
            ++countOut;
        }
        if (countHeaders > 0 && countOut == 0) {
            return "result";
        }
        return null;
    }

    @Override
    public String getFaultMessageName(OperationInfo op, Class<?> exClass, Class<?> beanClass) {
        WebFault f = exClass.getAnnotation(WebFault.class);
        if (f != null) {
            return this.getWithReflection(WebFault.class, f, "messageName");
        }
        return null;
    }

    private <T> String getWithReflection(Class<T> cls, T obj, String name) {
        try {
            String s2 = cls.getMethod(name, new Class[0]).invoke(obj, new Object[0]).toString();
            if (!StringUtils.isEmpty(s2)) {
                return s2;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }

    @Override
    public QName getOutParameterName(OperationInfo op, Method method2, int paramNumber) {
        QName wrappername;
        method2 = this.getDeclaredMethod(method2);
        if (paramNumber >= 0) {
            return this.getParameterName(op, method2, paramNumber, op.getOutput().size(), "return", false);
        }
        WebResult webResult = this.getWebResult(method2);
        String tns = null;
        String local = null;
        if (webResult != null) {
            tns = webResult.targetNamespace();
            local = webResult.name();
        }
        if ((tns == null || tns.length() == 0) && (wrappername = this.getResponseWrapperName(op, method2)) != null) {
            tns = wrappername.getNamespaceURI();
        }
        if (tns == null || tns.length() == 0) {
            tns = op.getName().getNamespaceURI();
        }
        if (local == null || local.length() == 0) {
            local = Boolean.TRUE.equals(this.isRPC(method2)) || !Boolean.FALSE.equals(this.isWrapped(method2)) ? this.getDefaultLocalName(op, method2, paramNumber, op.getOutput().size(), "return") : this.getOperationName(op.getInterface(), method2).getLocalPart() + "Response";
        }
        return new QName(tns, local);
    }

    @Override
    public QName getOutPartName(OperationInfo op, Method method2, int paramNumber) {
        method2 = this.getDeclaredMethod(method2);
        if (paramNumber >= 0) {
            return this.getPartName(op, method2, paramNumber, op.getOutput(), "return", false);
        }
        WebResult webResult = this.getWebResult(method2);
        String tns = op.getOutput().getName().getNamespaceURI();
        String local = null;
        if (webResult != null) {
            if (Boolean.TRUE.equals(this.isRPC(method2)) || this.isDocumentBare(method2)) {
                local = webResult.partName();
            }
            if (local == null || local.length() == 0) {
                local = webResult.name();
            }
        }
        if (local == null || local.length() == 0) {
            local = Boolean.TRUE.equals(this.isRPC(method2)) || !Boolean.FALSE.equals(this.isWrapped(method2)) ? "return" : this.getOperationName(op.getInterface(), method2).getLocalPart() + "Response";
        }
        return new QName(tns, local);
    }

    @Override
    public Boolean isInParam(Method method2, int j) {
        if (j < 0) {
            return Boolean.FALSE;
        }
        WebParam webParam = this.getWebParam(method2 = this.getDeclaredMethod(method2), j);
        return webParam == null || webParam.mode().equals((Object)WebParam.Mode.IN) || webParam.mode().equals((Object)WebParam.Mode.INOUT);
    }

    private WebResult getWebResult(Method method2) {
        return method2.getAnnotation(WebResult.class);
    }

    @Override
    public Boolean isOutParam(Method method2, int j) {
        method2 = this.getDeclaredMethod(method2);
        if (j == -1) {
            return !method2.getReturnType().equals(Void.TYPE);
        }
        WebParam webParam = this.getWebParam(method2, j);
        if (webParam != null && (webParam.mode().equals((Object)WebParam.Mode.OUT) || webParam.mode().equals((Object)WebParam.Mode.INOUT))) {
            return Boolean.TRUE;
        }
        return method2.getParameterTypes()[j] == Holder.class;
    }

    @Override
    public Boolean isInOutParam(Method method2, int j) {
        method2 = this.getDeclaredMethod(method2);
        if (j == -1) {
            return !method2.getReturnType().equals(Void.TYPE);
        }
        WebParam webParam = this.getWebParam(method2, j);
        if (webParam != null && webParam.mode().equals((Object)WebParam.Mode.INOUT)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public QName getRequestWrapperName(OperationInfo op, Method method2) {
        WebMethod meth;
        Method m4 = this.getDeclaredMethod(method2);
        RequestWrapper rw = m4.getAnnotation(RequestWrapper.class);
        String nm = null;
        String lp = null;
        if (rw != null) {
            nm = rw.targetNamespace();
            lp = rw.localName();
        }
        if ((meth = m4.getAnnotation(WebMethod.class)) != null && StringUtils.isEmpty(lp)) {
            lp = meth.operationName();
        }
        if (StringUtils.isEmpty(nm)) {
            nm = op.getName().getNamespaceURI();
        }
        if (!StringUtils.isEmpty(nm) && !StringUtils.isEmpty(lp)) {
            return new QName(nm, lp);
        }
        return null;
    }

    @Override
    public QName getResponseWrapperName(OperationInfo op, Method method2) {
        WebMethod meth;
        Method m4 = this.getDeclaredMethod(method2);
        ResponseWrapper rw = m4.getAnnotation(ResponseWrapper.class);
        String nm = null;
        String lp = null;
        if (rw != null) {
            nm = rw.targetNamespace();
            lp = rw.localName();
        }
        if ((meth = m4.getAnnotation(WebMethod.class)) != null && StringUtils.isEmpty(lp) && !StringUtils.isEmpty(lp = meth.operationName())) {
            lp = lp + "Response";
        }
        if (StringUtils.isEmpty(nm)) {
            nm = op.getName().getNamespaceURI();
        }
        if (!StringUtils.isEmpty(nm) && !StringUtils.isEmpty(lp)) {
            return new QName(nm, lp);
        }
        return null;
    }

    @Override
    public Class<?> getResponseWrapper(Method selected) {
        if (this.responseMethodClassNotFoundCache.contains(selected)) {
            return null;
        }
        Class<?> cachedClass = this.responseMethodClassCache.get(selected);
        if (cachedClass != null) {
            return cachedClass;
        }
        Method m4 = this.getDeclaredMethod(selected);
        ResponseWrapper rw = m4.getAnnotation(ResponseWrapper.class);
        String clsName = "";
        clsName = rw == null ? JaxWsServiceConfiguration.getPackageName(selected) + ".jaxws." + StringUtils.capitalize(selected.getName()) + "Response" : rw.className();
        if (clsName.length() > 0) {
            cachedClass = this.responseMethodClassCache.get(clsName);
            if (cachedClass != null) {
                this.responseMethodClassCache.put(selected, cachedClass);
                return cachedClass;
            }
            try {
                Class<?> r = ClassLoaderUtils.loadClass(clsName, this.implInfo.getEndpointClass());
                this.responseMethodClassCache.put(clsName, r);
                this.responseMethodClassCache.put(selected, r);
                if (r.equals(m4.getReturnType())) {
                    LOG.log(Level.WARNING, "INVALID_RESPONSE_WRAPPER", new Object[]{clsName, m4.getReturnType().getName()});
                }
                return r;
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
        }
        this.responseMethodClassNotFoundCache.add(selected);
        return null;
    }

    @Override
    public String getResponseWrapperClassName(Method selected) {
        Method m4 = this.getDeclaredMethod(selected);
        ResponseWrapper rw = m4.getAnnotation(ResponseWrapper.class);
        String clsName = "";
        if (rw != null) {
            clsName = rw.className();
        }
        if (clsName.length() > 0) {
            return clsName;
        }
        return null;
    }

    @Override
    public String getRequestWrapperClassName(Method selected) {
        Method m4 = this.getDeclaredMethod(selected);
        RequestWrapper rw = m4.getAnnotation(RequestWrapper.class);
        String clsName = "";
        if (rw != null) {
            clsName = rw.className();
        }
        if (clsName.length() > 0) {
            return clsName;
        }
        return null;
    }

    @Override
    public Class<?> getRequestWrapper(Method selected) {
        if (this.requestMethodClassNotFoundCache.contains(selected)) {
            return null;
        }
        Class<?> cachedClass = this.requestMethodClassCache.get(selected);
        if (cachedClass != null) {
            return cachedClass;
        }
        Method m4 = this.getDeclaredMethod(selected);
        RequestWrapper rw = m4.getAnnotation(RequestWrapper.class);
        String clsName = "";
        clsName = rw == null ? JaxWsServiceConfiguration.getPackageName(selected) + ".jaxws." + StringUtils.capitalize(selected.getName()) : rw.className();
        if (clsName.length() > 0) {
            cachedClass = this.requestMethodClassCache.get(clsName);
            if (cachedClass != null) {
                this.requestMethodClassCache.put(selected, cachedClass);
                return cachedClass;
            }
            try {
                Class<?> r = ClassLoaderUtils.loadClass(clsName, this.implInfo.getEndpointClass());
                this.requestMethodClassCache.put(clsName, r);
                this.requestMethodClassCache.put(selected, r);
                if (m4.getParameterTypes().length == 1 && r.equals(m4.getParameterTypes()[0])) {
                    LOG.log(Level.WARNING, "INVALID_REQUEST_WRAPPER", new Object[]{clsName, m4.getParameterTypes()[0].getName()});
                }
                return r;
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
        }
        this.requestMethodClassNotFoundCache.add(selected);
        return null;
    }

    private static String getPackageName(Method method2) {
        return PackageUtils.getPackageName(method2.getDeclaringClass());
    }

    @Override
    public QName getFaultName(InterfaceInfo service2, OperationInfo o, Class<?> exClass, Class<?> beanClass) {
        WebFault fault = exClass.getAnnotation(WebFault.class);
        if (fault != null) {
            String ns;
            String name = fault.name();
            if (name.length() == 0) {
                name = exClass.getSimpleName();
            }
            if ((ns = fault.targetNamespace()).length() == 0) {
                ns = service2.getName().getNamespaceURI();
            }
            return new QName(ns, name);
        }
        return null;
    }

    @Override
    public Boolean isWrapped(Method m4) {
        if (this.getServiceFactory().getWrapped() != null) {
            return this.getServiceFactory().getWrapped();
        }
        SOAPBinding ann = (m4 = this.getDeclaredMethod(m4)).getAnnotation(SOAPBinding.class);
        if (ann != null) {
            if (ann.style().equals((Object)SOAPBinding.Style.RPC)) {
                Message message = new Message("SOAPBinding_MESSAGE_RPC", LOG, m4.getName());
                throw new Fault(new JaxWsConfigurationException(message));
            }
            return !ann.parameterStyle().equals((Object)SOAPBinding.ParameterStyle.BARE);
        }
        return this.isWrapped();
    }

    @Override
    public Boolean isWrapped() {
        SOAPBinding ann = this.implInfo.getEndpointClass().getAnnotation(SOAPBinding.class);
        if (ann != null) {
            return !ann.parameterStyle().equals((Object)SOAPBinding.ParameterStyle.BARE) && !ann.style().equals((Object)SOAPBinding.Style.RPC);
        }
        return null;
    }

    @Override
    public Boolean isHeader(Method method2, int j) {
        method2 = this.getDeclaredMethod(method2);
        if (j >= 0) {
            WebParam webParam = this.getWebParam(method2, j);
            return webParam != null && webParam.header();
        }
        WebResult webResult = this.getWebResult(method2);
        return webResult != null && webResult.header();
    }

    @Override
    public String getStyle() {
        SOAPBinding ann = this.implInfo.getEndpointClass().getAnnotation(SOAPBinding.class);
        if (ann != null) {
            return ann.style().toString().toLowerCase();
        }
        return super.getStyle();
    }

    private boolean isDocumentBare(Method method2) {
        SOAPBinding ann = method2.getAnnotation(SOAPBinding.class);
        if (ann != null) {
            return ann.style().equals((Object)SOAPBinding.Style.DOCUMENT) && ann.parameterStyle().equals((Object)SOAPBinding.ParameterStyle.BARE);
        }
        ann = this.implInfo.getEndpointClass().getAnnotation(SOAPBinding.class);
        if (ann != null) {
            return ann.style().equals((Object)SOAPBinding.Style.DOCUMENT) && ann.parameterStyle().equals((Object)SOAPBinding.ParameterStyle.BARE);
        }
        return false;
    }

    @Override
    public Boolean isRPC(Method method2) {
        SOAPBinding ann = method2.getAnnotation(SOAPBinding.class);
        if (ann != null) {
            return ann.style().equals((Object)SOAPBinding.Style.RPC);
        }
        ann = this.implInfo.getEndpointClass().getAnnotation(SOAPBinding.class);
        if (ann != null) {
            return ann.style().equals((Object)SOAPBinding.Style.RPC);
        }
        return super.isRPC(method2);
    }

    @Override
    public Boolean hasOutMessage(Method method2) {
        return !(method2 = this.getDeclaredMethod(method2)).isAnnotationPresent(Oneway.class);
    }

    @Override
    public String getAction(OperationInfo op, Method method2) {
        Action act;
        method2 = this.getDeclaredMethod(method2);
        WebMethod wm = method2.getAnnotation(WebMethod.class);
        String action = "";
        if (wm != null) {
            action = wm.action();
        }
        if (StringUtils.isEmpty(action) && (act = method2.getAnnotation(Action.class)) != null) {
            action = act.input();
        }
        return action;
    }

    @Override
    public Boolean isHolder(Class<?> cls, Type type) {
        return Holder.class.equals(cls);
    }

    @Override
    public Type getHolderType(Class<?> cls, Type type) {
        if (cls.equals(Holder.class) && type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType)type;
            return paramType.getActualTypeArguments()[0];
        }
        return cls;
    }

    @Override
    public Boolean isWrapperPartQualified(MessagePartInfo mpi) {
        Annotation[] annotations = (Annotation[])mpi.getProperty("parameter.annotations");
        if (annotations != null) {
            for (Annotation an : annotations) {
                String tns = null;
                if (an instanceof WebParam) {
                    tns = ((WebParam)an).targetNamespace();
                } else if (an instanceof WebResult) {
                    tns = ((WebResult)an).targetNamespace();
                }
                if (tns == null || StringUtils.isEmpty(tns)) continue;
                return Boolean.TRUE;
            }
        }
        return null;
    }

    @Override
    public Long getWrapperPartMinOccurs(MessagePartInfo mpi) {
        Annotation[] a = (Annotation[])mpi.getProperty("parameter.annotations");
        if (a != null) {
            for (Annotation a2 : a) {
                XmlElement e;
                if (!(a2 instanceof XmlElement) || !(e = (XmlElement)a2).required()) continue;
                return 1L;
            }
        }
        return null;
    }
}

