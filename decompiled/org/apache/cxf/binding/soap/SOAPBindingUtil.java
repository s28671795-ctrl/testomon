/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap;

import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jws.soap.SOAPBinding;
import javax.wsdl.Binding;
import javax.wsdl.BindingFault;
import javax.wsdl.BindingInput;
import javax.wsdl.BindingOperation;
import javax.wsdl.BindingOutput;
import javax.wsdl.Definition;
import javax.wsdl.Port;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.wsdl.extensions.soap.SOAPBinding;
import javax.wsdl.extensions.soap.SOAPBody;
import javax.wsdl.extensions.soap.SOAPFault;
import javax.wsdl.extensions.soap.SOAPHeader;
import javax.wsdl.extensions.soap.SOAPOperation;
import javax.wsdl.extensions.soap12.SOAP12Address;
import javax.wsdl.extensions.soap12.SOAP12Binding;
import javax.wsdl.extensions.soap12.SOAP12Body;
import javax.wsdl.extensions.soap12.SOAP12Fault;
import javax.wsdl.extensions.soap12.SOAP12Header;
import javax.wsdl.extensions.soap12.SOAP12Operation;
import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.wsdl.extensions.SoapAddress;
import org.apache.cxf.binding.soap.wsdl.extensions.SoapBinding;
import org.apache.cxf.binding.soap.wsdl.extensions.SoapBody;
import org.apache.cxf.binding.soap.wsdl.extensions.SoapFault;
import org.apache.cxf.binding.soap.wsdl.extensions.SoapHeader;
import org.apache.cxf.binding.soap.wsdl.extensions.SoapOperation;
import org.apache.cxf.common.util.ExtensionInvocationHandler;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.wsdl.WSDLConstants;

public final class SOAPBindingUtil {
    private static Map<String, String> bindingMap = new HashMap<String, String>();

    private SOAPBindingUtil() {
    }

    public static String getBindingAnnotation(String key) {
        return bindingMap.get(key.toUpperCase());
    }

    public static <T> T getProxy(Class<T> cls, Object obj) {
        ExtensionInvocationHandler ih = new ExtensionInvocationHandler(obj);
        Object proxy = null;
        try {
            proxy = Proxy.newProxyInstance(SOAPBindingUtil.getContextClassLoader(), new Class[]{cls}, ih);
        }
        catch (Throwable ex) {
            ClassLoader contextLoader = SOAPBindingUtil.getContextClassLoader();
            ClassLoader clsClassLoader = SOAPBindingUtil.getClassLoader(cls);
            if (contextLoader != clsClassLoader) {
                proxy = Proxy.newProxyInstance(clsClassLoader, new Class[]{cls}, ih);
            }
            if (ex instanceof RuntimeException) {
                throw (RuntimeException)ex;
            }
            throw new RuntimeException(ex);
        }
        return cls.cast(proxy);
    }

    private static ClassLoader getContextClassLoader() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

                @Override
                public ClassLoader run() {
                    return Thread.currentThread().getContextClassLoader();
                }
            });
        }
        return Thread.currentThread().getContextClassLoader();
    }

    private static ClassLoader getClassLoader(final Class<?> clazz) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

                @Override
                public ClassLoader run() {
                    return clazz.getClassLoader();
                }
            });
        }
        return clazz.getClassLoader();
    }

    public static boolean isSOAPBinding(Binding binding) {
        for (Object obj : binding.getExtensibilityElements()) {
            if (!SOAPBindingUtil.isSOAPBinding(obj)) continue;
            return true;
        }
        return false;
    }

    public static String getBindingStyle(Binding binding) {
        for (Object obj : binding.getExtensibilityElements()) {
            if (!SOAPBindingUtil.isSOAPBinding(obj)) continue;
            return SOAPBindingUtil.getSoapBinding(obj).getStyle();
        }
        return "";
    }

    public static SoapOperation getSoapOperation(List<ExtensibilityElement> exts) {
        if (exts != null) {
            for (ExtensibilityElement ext : exts) {
                if (!SOAPBindingUtil.isSOAPOperation(ext)) continue;
                return SOAPBindingUtil.getSoapOperation(ext);
            }
        }
        return null;
    }

    public static SoapOperation getSoapOperation(Object obj) {
        if (SOAPBindingUtil.isSOAPOperation(obj)) {
            return SOAPBindingUtil.getProxy(SoapOperation.class, obj);
        }
        return null;
    }

    public static String getSOAPOperationStyle(BindingOperation bop) {
        String style = "";
        if (bop != null) {
            for (Object obj : bop.getExtensibilityElements()) {
                if (!SOAPBindingUtil.isSOAPOperation(obj)) continue;
                style = SOAPBindingUtil.getSoapOperation(obj).getStyle();
                break;
            }
        }
        return style;
    }

    public static SoapBody getBindingInputSOAPBody(BindingOperation bop) {
        BindingInput bindingInput = bop.getBindingInput();
        if (bindingInput != null) {
            for (Object obj : bindingInput.getExtensibilityElements()) {
                if (!SOAPBindingUtil.isSOAPBody(obj)) continue;
                return SOAPBindingUtil.getSoapBody(obj);
            }
        }
        return null;
    }

    public static SoapBody getBindingOutputSOAPBody(BindingOperation bop) {
        BindingOutput bindingOutput = bop.getBindingOutput();
        if (bindingOutput != null) {
            for (Object obj : bindingOutput.getExtensibilityElements()) {
                if (!SOAPBindingUtil.isSOAPBody(obj)) continue;
                return SOAPBindingUtil.getSoapBody(obj);
            }
        }
        return null;
    }

    public static SoapBody getSoapBody(List<ExtensibilityElement> exts) {
        if (exts != null) {
            for (ExtensibilityElement ext : exts) {
                if (!SOAPBindingUtil.isSOAPBody(ext)) continue;
                return SOAPBindingUtil.getSoapBody(ext);
            }
        }
        return null;
    }

    public static SoapBody getSoapBody(Object obj) {
        if (SOAPBindingUtil.isSOAPBody(obj)) {
            return SOAPBindingUtil.getProxy(SoapBody.class, obj);
        }
        return null;
    }

    public static boolean isSOAPBody(Object obj) {
        return obj instanceof SOAPBody || obj instanceof SOAP12Body;
    }

    public static boolean isSOAPHeader(Object obj) {
        return obj instanceof SOAPHeader || obj instanceof SOAP12Header;
    }

    public static List<SoapHeader> getSoapHeaders(List<ExtensibilityElement> exts) {
        ArrayList<SoapHeader> headers = new ArrayList<SoapHeader>();
        if (exts != null) {
            for (ExtensibilityElement ext : exts) {
                if (!SOAPBindingUtil.isSOAPHeader(ext)) continue;
                headers.add(SOAPBindingUtil.getSoapHeader(ext));
            }
        }
        return headers;
    }

    public static SoapHeader getSoapHeader(Object obj) {
        if (SOAPBindingUtil.isSOAPHeader(obj)) {
            return SOAPBindingUtil.getProxy(SoapHeader.class, obj);
        }
        return null;
    }

    public static SoapAddress getSoapAddress(Object obj) {
        if (SOAPBindingUtil.isSOAPAddress(obj)) {
            return SOAPBindingUtil.getProxy(SoapAddress.class, obj);
        }
        return null;
    }

    public static boolean isSOAPAddress(Object obj) {
        return obj instanceof SOAPAddress || obj instanceof SOAP12Address;
    }

    public static SoapHeader getBindingInputSOAPHeader(BindingOperation bop) {
        BindingInput bindingInput = bop.getBindingInput();
        if (bindingInput != null) {
            for (Object obj : bindingInput.getExtensibilityElements()) {
                if (!SOAPBindingUtil.isSOAPHeader(obj)) continue;
                return SOAPBindingUtil.getProxy(SoapHeader.class, obj);
            }
        }
        return null;
    }

    public static List<SoapHeader> getBindingInputSOAPHeaders(BindingOperation bop) {
        ArrayList<SoapHeader> headers = new ArrayList<SoapHeader>();
        BindingInput bindingInput = bop.getBindingInput();
        if (bindingInput != null) {
            for (Object obj : bindingInput.getExtensibilityElements()) {
                if (!SOAPBindingUtil.isSOAPHeader(obj)) continue;
                headers.add(SOAPBindingUtil.getProxy(SoapHeader.class, obj));
            }
        }
        return headers;
    }

    public static SoapHeader getBindingOutputSOAPHeader(BindingOperation bop) {
        BindingOutput bindingOutput = bop.getBindingOutput();
        if (bindingOutput != null) {
            for (Object obj : bindingOutput.getExtensibilityElements()) {
                if (!SOAPBindingUtil.isSOAPHeader(obj)) continue;
                return SOAPBindingUtil.getProxy(SoapHeader.class, obj);
            }
        }
        return null;
    }

    public static List<SoapHeader> getBindingOutputSOAPHeaders(BindingOperation bop) {
        ArrayList<SoapHeader> headers = new ArrayList<SoapHeader>();
        BindingOutput bindingOutput = bop.getBindingOutput();
        if (bindingOutput != null) {
            for (Object obj : bindingOutput.getExtensibilityElements()) {
                if (!SOAPBindingUtil.isSOAPHeader(obj)) continue;
                headers.add(SOAPBindingUtil.getProxy(SoapHeader.class, obj));
            }
        }
        return headers;
    }

    public static SoapBinding getSoapBinding(List<ExtensibilityElement> exts) {
        for (ExtensibilityElement ext : exts) {
            if (!SOAPBindingUtil.isSOAPBinding(ext)) continue;
            return SOAPBindingUtil.getSoapBinding(ext);
        }
        return null;
    }

    public static SoapBinding getSoapBinding(Object obj) {
        if (SOAPBindingUtil.isSOAPBinding(obj)) {
            return SOAPBindingUtil.getProxy(SoapBinding.class, obj);
        }
        return null;
    }

    public static boolean isSOAPBinding(Object obj) {
        return obj instanceof SOAPBinding || obj instanceof SOAP12Binding;
    }

    public static List<SoapFault> getBindingOperationSoapFaults(BindingOperation bop) {
        ArrayList<SoapFault> faults = new ArrayList<SoapFault>();
        for (Object obj : bop.getBindingFaults().values()) {
            if (!(obj instanceof BindingFault)) continue;
            BindingFault faultElement = (BindingFault)obj;
            for (Object flt : faultElement.getExtensibilityElements()) {
                SoapFault fault = SOAPBindingUtil.getSoapFault(flt);
                if (fault == null) continue;
                faults.add(fault);
            }
        }
        return faults;
    }

    public static SoapFault getSoapFault(Object obj) {
        if (SOAPBindingUtil.isSOAPFault(obj)) {
            return SOAPBindingUtil.getProxy(SoapFault.class, obj);
        }
        return null;
    }

    public static boolean isMixedStyle(Binding binding) {
        String bindingStyle = "";
        String previousOpStyle = "";
        String style = "";
        for (Object obj : binding.getExtensibilityElements()) {
            SoapBinding soapBinding;
            if (!SOAPBindingUtil.isSOAPBinding(obj) || (bindingStyle = (soapBinding = SOAPBindingUtil.getSoapBinding(obj)).getStyle()) != null) continue;
            bindingStyle = "";
        }
        for (Object bobj : binding.getBindingOperations()) {
            BindingOperation bop = (BindingOperation)bobj;
            for (Object obj : bop.getExtensibilityElements()) {
                if (!SOAPBindingUtil.isSOAPOperation(obj)) continue;
                SoapOperation soapOperation = SOAPBindingUtil.getSoapOperation(obj);
                style = soapOperation.getStyle();
                if (style == null) {
                    style = "";
                }
                if ("".equals(bindingStyle) && "".equals(previousOpStyle) || "".equals(bindingStyle) && previousOpStyle.equalsIgnoreCase(style)) {
                    previousOpStyle = style;
                    continue;
                }
                if (!"".equals(bindingStyle) && "".equals(previousOpStyle) && bindingStyle.equalsIgnoreCase(style) || bindingStyle.equalsIgnoreCase(previousOpStyle) && bindingStyle.equalsIgnoreCase(style)) {
                    previousOpStyle = style;
                    continue;
                }
                if (!"".equals(bindingStyle) && "".equals(style) && "".equals(previousOpStyle)) continue;
                return true;
            }
        }
        return false;
    }

    public static String getCanonicalBindingStyle(Binding binding) {
        String bindingStyle = SOAPBindingUtil.getBindingStyle(binding);
        if (!StringUtils.isEmpty(bindingStyle)) {
            return bindingStyle;
        }
        for (Object bobj : binding.getBindingOperations()) {
            BindingOperation bindingOp = (BindingOperation)bobj;
            String bopStyle = SOAPBindingUtil.getSOAPOperationStyle(bindingOp);
            if (StringUtils.isEmpty(bopStyle)) continue;
            return bopStyle;
        }
        return "";
    }

    public static boolean isSOAPOperation(Object obj) {
        return obj instanceof SOAPOperation || obj instanceof SOAP12Operation;
    }

    public static boolean isSOAPFault(Object obj) {
        return obj instanceof SOAPFault || obj instanceof SOAP12Fault;
    }

    public static SoapAddress createSoapAddress(ExtensionRegistry extReg, boolean isSOAP12) throws WSDLException {
        ExtensibilityElement extElement = null;
        extElement = isSOAP12 ? extReg.createExtension(Port.class, WSDLConstants.QNAME_SOAP12_BINDING_ADDRESS) : extReg.createExtension(Port.class, WSDLConstants.QNAME_SOAP_BINDING_ADDRESS);
        return SOAPBindingUtil.getSoapAddress(extElement);
    }

    public static SoapBody createSoapBody(ExtensionRegistry extReg, Class<?> clz, boolean isSOAP12) throws WSDLException {
        ExtensibilityElement extElement = null;
        extElement = isSOAP12 ? extReg.createExtension(clz, new QName("http://schemas.xmlsoap.org/wsdl/soap12/", "body")) : extReg.createExtension(clz, new QName("http://schemas.xmlsoap.org/wsdl/soap/", "body"));
        return SOAPBindingUtil.getSoapBody(extElement);
    }

    public static SoapBinding createSoapBinding(ExtensionRegistry extReg, boolean isSOAP12) throws WSDLException {
        ExtensibilityElement extElement = null;
        if (isSOAP12) {
            extElement = extReg.createExtension(Binding.class, new QName("http://schemas.xmlsoap.org/wsdl/soap12/", "binding"));
            ((SOAP12Binding)extElement).setTransportURI("http://schemas.xmlsoap.org/soap/http");
        } else {
            extElement = extReg.createExtension(Binding.class, new QName("http://schemas.xmlsoap.org/wsdl/soap/", "binding"));
            ((SOAPBinding)extElement).setTransportURI("http://schemas.xmlsoap.org/soap/http");
        }
        return SOAPBindingUtil.getSoapBinding(extElement);
    }

    public static SoapOperation createSoapOperation(ExtensionRegistry extReg, boolean isSOAP12) throws WSDLException {
        ExtensibilityElement extElement = null;
        extElement = isSOAP12 ? extReg.createExtension(BindingOperation.class, new QName("http://schemas.xmlsoap.org/wsdl/soap12/", "operation")) : extReg.createExtension(BindingOperation.class, new QName("http://schemas.xmlsoap.org/wsdl/soap/", "operation"));
        return SOAPBindingUtil.getSoapOperation(extElement);
    }

    public static SoapFault createSoapFault(ExtensionRegistry extReg, boolean isSOAP12) throws WSDLException {
        ExtensibilityElement extElement = null;
        extElement = isSOAP12 ? extReg.createExtension(BindingFault.class, new QName("http://schemas.xmlsoap.org/wsdl/soap12/", "fault")) : extReg.createExtension(BindingFault.class, new QName("http://schemas.xmlsoap.org/wsdl/soap/", "fault"));
        return SOAPBindingUtil.getSoapFault(extElement);
    }

    public static SoapHeader createSoapHeader(ExtensionRegistry extReg, Class<?> clz, boolean isSOAP12) throws WSDLException {
        ExtensibilityElement extElement = null;
        extElement = isSOAP12 ? extReg.createExtension(clz, new QName("http://schemas.xmlsoap.org/wsdl/soap12/", "header")) : extReg.createExtension(clz, new QName("http://schemas.xmlsoap.org/wsdl/soap/", "header"));
        return SOAPBindingUtil.getSoapHeader(extElement);
    }

    public static void addSOAPNamespace(Definition definition, boolean isSOAP12) {
        Map namespaces = definition.getNamespaces();
        if (isSOAP12 && !namespaces.values().contains("http://schemas.xmlsoap.org/wsdl/soap12/")) {
            definition.addNamespace("soap12", "http://schemas.xmlsoap.org/wsdl/soap12/");
        } else if (!namespaces.values().contains("http://schemas.xmlsoap.org/wsdl/soap/")) {
            definition.addNamespace("soap", "http://schemas.xmlsoap.org/wsdl/soap/");
        }
    }

    public static SOAPBinding.Style getSoapStyle(String soapStyle) {
        if ("".equals(soapStyle)) {
            return null;
        }
        if ("RPC".equalsIgnoreCase(soapStyle)) {
            return SOAPBinding.Style.RPC;
        }
        return SOAPBinding.Style.DOCUMENT;
    }

    public static SOAPBinding.Use getSoapUse(String soapUse) {
        if ("".equals(soapUse)) {
            return null;
        }
        if ("ENCODED".equalsIgnoreCase(soapUse)) {
            return SOAPBinding.Use.ENCODED;
        }
        return SOAPBinding.Use.LITERAL;
    }

    static {
        bindingMap.put("RPC", "SOAPBinding.Style.RPC");
        bindingMap.put("DOCUMENT", "SOAPBinding.Style.DOCUMENT");
        bindingMap.put("LITERAL", "SOAPBinding.Use.LITERAL");
        bindingMap.put("ENCODED", "SOAPBinding.Use.ENCODED");
        bindingMap.put("BARE", "SOAPBinding.ParameterStyle.BARE");
        bindingMap.put("WRAPPED", "SOAPBinding.ParameterStyle.WRAPPED");
    }
}

