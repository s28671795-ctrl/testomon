/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingType;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceProvider;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PackageUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.jaxb.JAXBEncoderDecoder;

public class JaxWsImplementorInfo {
    private static final Logger LOG = LogUtils.getL7dLogger(JaxWsImplementorInfo.class);
    private static final ResourceBundle BUNDLE = LOG.getResourceBundle();
    private Class<?> implementorClass;
    private Class<?> seiClass;
    private ParameterizedType seiType;
    private List<WebService> wsAnnotations = new ArrayList<WebService>(2);
    private WebServiceProvider wsProviderAnnotation;

    public JaxWsImplementorInfo(Class<?> ic) {
        this.implementorClass = ic;
        this.initialize();
    }

    public Class<?> getSEIClass() {
        return this.seiClass;
    }

    public ParameterizedType getSEIType() {
        return this.seiType;
    }

    public Class<?> getImplementorClass() {
        return this.implementorClass;
    }

    public Class<?> getEndpointClass() {
        Class<?> endpointInterface = this.getSEIClass();
        if (null == endpointInterface) {
            endpointInterface = this.getImplementorClass();
        }
        return endpointInterface;
    }

    public String getWsdlLocation() {
        for (WebService service2 : this.wsAnnotations) {
            if (StringUtils.isEmpty(service2.wsdlLocation())) continue;
            return service2.wsdlLocation();
        }
        if (null != this.wsProviderAnnotation && !StringUtils.isEmpty(this.wsProviderAnnotation.wsdlLocation())) {
            return this.wsProviderAnnotation.wsdlLocation();
        }
        return null;
    }

    public QName getServiceName() {
        String serviceName = null;
        String namespace = null;
        if (!this.wsAnnotations.isEmpty()) {
            int offset = 1;
            if (this.seiClass == null) {
                offset = 0;
            }
            for (int x = 0; x < this.wsAnnotations.size() - offset; ++x) {
                if (StringUtils.isEmpty(serviceName)) {
                    serviceName = this.wsAnnotations.get(x).serviceName();
                }
                if (!StringUtils.isEmpty(namespace)) continue;
                namespace = this.wsAnnotations.get(x).targetNamespace();
            }
        }
        if ((serviceName == null || namespace == null) && this.wsProviderAnnotation != null) {
            serviceName = this.wsProviderAnnotation.serviceName();
            namespace = this.wsProviderAnnotation.targetNamespace();
        }
        if (StringUtils.isEmpty(serviceName)) {
            serviceName = this.implementorClass.getSimpleName() + "Service";
        }
        if (StringUtils.isEmpty(namespace)) {
            namespace = this.getDefaultNamespace(this.implementorClass);
        }
        return new QName(namespace, serviceName);
    }

    public QName getEndpointName() {
        String portName = null;
        String namespace = null;
        String name = null;
        if (!this.wsAnnotations.isEmpty()) {
            int offset = 1;
            if (this.seiClass == null) {
                offset = 0;
            }
            for (int x = 0; x < this.wsAnnotations.size() - offset; ++x) {
                if (StringUtils.isEmpty(portName)) {
                    portName = this.wsAnnotations.get(x).portName();
                }
                if (StringUtils.isEmpty(namespace)) {
                    namespace = this.wsAnnotations.get(x).targetNamespace();
                }
                if (!StringUtils.isEmpty(name)) continue;
                name = this.wsAnnotations.get(x).name();
            }
        }
        if ((portName == null || namespace == null) && this.wsProviderAnnotation != null) {
            portName = this.wsProviderAnnotation.portName();
            namespace = this.wsProviderAnnotation.targetNamespace();
        }
        if (StringUtils.isEmpty(portName) && !StringUtils.isEmpty(name)) {
            portName = name + "Port";
        }
        if (StringUtils.isEmpty(portName)) {
            portName = this.implementorClass.getSimpleName() + "Port";
        }
        if (StringUtils.isEmpty(namespace)) {
            namespace = this.getDefaultNamespace(this.implementorClass);
        }
        return new QName(namespace, portName);
    }

    public QName getInterfaceName() {
        String name = null;
        String namespace = null;
        if (this.seiClass != null) {
            WebService service2 = this.seiClass.getAnnotation(WebService.class);
            if (!StringUtils.isEmpty(service2.name())) {
                name = service2.name();
            }
            if (!StringUtils.isEmpty(service2.targetNamespace())) {
                namespace = service2.targetNamespace();
            }
        } else {
            for (WebService service3 : this.wsAnnotations) {
                if (!StringUtils.isEmpty(service3.name()) && name == null) {
                    name = service3.name();
                }
                if (StringUtils.isEmpty(service3.targetNamespace()) || namespace != null) continue;
                namespace = service3.targetNamespace();
            }
        }
        if (name == null) {
            if (this.seiClass != null) {
                name = this.seiClass.getSimpleName();
            } else if (this.implementorClass != null) {
                name = this.implementorClass.getSimpleName();
            }
        }
        if (namespace == null) {
            if (this.seiClass != null) {
                namespace = this.getDefaultNamespace(this.seiClass);
            } else if (this.implementorClass != null) {
                namespace = this.getDefaultNamespace(this.implementorClass);
            }
        }
        return new QName(namespace, name);
    }

    private String getDefaultNamespace(Class<?> clazz) {
        String pkg = PackageUtils.getNamespace(PackageUtils.getPackageName(clazz));
        return StringUtils.isEmpty(pkg) ? "http://unknown.namespace/" : pkg;
    }

    private String getWSInterfaceName(Class<?> implClz) {
        Class<?>[] clzs;
        if (implClz.isInterface() && implClz.getAnnotation(WebService.class) != null) {
            return implClz.getName();
        }
        for (Class<?> clz : clzs = implClz.getInterfaces()) {
            if (null == clz.getAnnotation(WebService.class)) continue;
            return clz.getName();
        }
        return null;
    }

    private String getImplementorClassName() {
        for (WebService service2 : this.wsAnnotations) {
            if (StringUtils.isEmpty(service2.endpointInterface())) continue;
            return service2.endpointInterface();
        }
        return null;
    }

    protected static boolean ifAnnotationLoadedByOtherClassLoader(Class<?> cls, Class<? extends Annotation> annotationClass) {
        for (Annotation an : cls.getAnnotations()) {
            if (an.annotationType() == null || !annotationClass.getName().equals(an.annotationType().getName())) continue;
            return true;
        }
        return false;
    }

    private void initialize() {
        Class<?> cls = this.implementorClass;
        while (cls != null) {
            WebService annotation = cls.getAnnotation(WebService.class);
            if (annotation != null) {
                this.wsAnnotations.add(annotation);
                if (cls.isInterface()) {
                    cls = null;
                }
            } else if (JaxWsImplementorInfo.ifAnnotationLoadedByOtherClassLoader(cls, WebService.class)) {
                LOG.log(Level.WARNING, "WEBSERVICE_ANNOTATIONS_IS_LOADED_BY_OTHER_CLASSLOADER", WebService.class.getName());
            }
            if (cls == null) continue;
            cls = cls.getSuperclass();
        }
        String sei = this.getImplementorClassName();
        boolean seiFromWsAnnotation = true;
        if (StringUtils.isEmpty(sei)) {
            seiFromWsAnnotation = false;
            sei = this.getWSInterfaceName(this.implementorClass);
        }
        if (!StringUtils.isEmpty(sei)) {
            try {
                this.seiClass = ClassLoaderUtils.loadClass(sei, this.implementorClass);
            }
            catch (ClassNotFoundException ex) {
                throw new WebServiceException(BUNDLE.getString("SEI_LOAD_FAILURE_MSG"), ex);
            }
            WebService seiAnnotation = this.seiClass.getAnnotation(WebService.class);
            if (null == seiAnnotation) {
                throw new WebServiceException(BUNDLE.getString("SEI_WITHOUT_WEBSERVICE_ANNOTATION_EXC"));
            }
            if (!(!seiFromWsAnnotation || StringUtils.isEmpty(seiAnnotation.portName()) && StringUtils.isEmpty(seiAnnotation.serviceName()) && StringUtils.isEmpty(seiAnnotation.endpointInterface()))) {
                String expString = BUNDLE.getString("ILLEGAL_ATTRIBUTE_IN_SEI_ANNOTATION_EXC");
                throw new WebServiceException(expString);
            }
            this.wsAnnotations.add(seiAnnotation);
            for (int x = this.implementorClass.getInterfaces().length - 1; x >= 0; --x) {
                Type type;
                if (!this.seiClass.equals(this.implementorClass.getInterfaces()[x]) || !((type = this.implementorClass.getGenericInterfaces()[x]) instanceof ParameterizedType)) continue;
                this.seiType = (ParameterizedType)type;
            }
        }
        this.wsProviderAnnotation = JaxWsImplementorInfo.getWebServiceProviderAnnotation(this.implementorClass);
    }

    private static WebServiceProvider getWebServiceProviderAnnotation(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        WebServiceProvider ann = cls.getAnnotation(WebServiceProvider.class);
        if (null != ann) {
            return ann;
        }
        if (JaxWsImplementorInfo.ifAnnotationLoadedByOtherClassLoader(cls, WebServiceProvider.class)) {
            LOG.log(Level.WARNING, "WEBSERVICE_ANNOTATIONS_IS_LOADED_BY_OTHER_CLASSLOADER", WebServiceProvider.class.getName());
        }
        for (Class<?> inf : cls.getInterfaces()) {
            if (null != inf.getAnnotation(WebServiceProvider.class)) {
                return inf.getAnnotation(WebServiceProvider.class);
            }
            if (!JaxWsImplementorInfo.ifAnnotationLoadedByOtherClassLoader(cls, WebServiceProvider.class)) continue;
            LOG.log(Level.WARNING, "WEBSERVICE_ANNOTATIONS_IS_LOADED_BY_OTHER_CLASSLOADER", WebServiceProvider.class.getName());
        }
        return JaxWsImplementorInfo.getWebServiceProviderAnnotation(cls.getSuperclass());
    }

    public boolean isWebServiceProvider() {
        return Provider.class.isAssignableFrom(this.implementorClass);
    }

    public WebServiceProvider getWsProvider() {
        return this.wsProviderAnnotation;
    }

    public Service.Mode getServiceMode() {
        ServiceMode m4 = this.implementorClass.getAnnotation(ServiceMode.class);
        if (m4 != null && m4.value() != null) {
            return m4.value();
        }
        return Service.Mode.PAYLOAD;
    }

    public Class<?> getProviderParameterType() {
        return JaxWsImplementorInfo.doGetProviderParameterType(this.implementorClass);
    }

    private static Class<?> doGetProviderParameterType(Class<?> c) {
        while (c != null) {
            Type[] intfTypes;
            for (Type t2 : intfTypes = c.getGenericInterfaces()) {
                Class<?> clazz = JAXBEncoderDecoder.getClassFromType(t2);
                if (!Provider.class.isAssignableFrom(clazz)) continue;
                if (Provider.class == clazz) {
                    Type[] paramTypes = ((ParameterizedType)t2).getActualTypeArguments();
                    return JAXBEncoderDecoder.getClassFromType(paramTypes[0]);
                }
                return JaxWsImplementorInfo.doGetProviderParameterType(clazz);
            }
            c = c.getSuperclass();
        }
        return null;
    }

    public String getBindingType() {
        BindingType bType = this.implementorClass.getAnnotation(BindingType.class);
        if (bType != null) {
            return bType.value();
        }
        return "http://schemas.xmlsoap.org/wsdl/soap/http";
    }
}

