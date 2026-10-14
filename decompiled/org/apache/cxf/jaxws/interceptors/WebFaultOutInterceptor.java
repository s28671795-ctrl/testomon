/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.interceptors;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.validation.Schema;
import javax.xml.ws.WebFault;
import javax.xml.ws.soap.SOAPFaultException;
import org.apache.cxf.annotations.SchemaValidation;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.databinding.DataWriter;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.ServiceUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.FaultOutInterceptor;
import org.apache.cxf.message.FaultMode;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.FaultInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.apache.cxf.ws.addressing.EndpointReferenceUtils;

public class WebFaultOutInterceptor
extends FaultOutInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(WebFaultOutInterceptor.class);
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(WebFaultOutInterceptor.class);

    private QName getFaultName(WebFault wf, Class<?> cls, OperationInfo op) {
        String name;
        String ns = wf.targetNamespace();
        if (StringUtils.isEmpty(ns) && op != null) {
            ns = op.getName().getNamespaceURI();
        }
        if (StringUtils.isEmpty(name = wf.name())) {
            name = cls.getSimpleName();
        }
        return new QName(ns, name);
    }

    private WebFault getWebFaultAnnotation(Class<?> t2) {
        WebFault fault = t2.getAnnotation(WebFault.class);
        if (fault == null && t2.getSuperclass() != null && Throwable.class.isAssignableFrom(t2.getSuperclass())) {
            fault = this.getWebFaultAnnotation(t2.getSuperclass());
        }
        return fault;
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        Fault f = (Fault)message.getContent(Exception.class);
        if (f == null) {
            return;
        }
        try {
            Throwable thr = f.getCause();
            SOAPFaultException sf = null;
            if (thr instanceof SOAPFaultException) {
                sf = (SOAPFaultException)thr;
            } else if (thr.getCause() instanceof SOAPFaultException) {
                sf = (SOAPFaultException)thr.getCause();
            }
            if (sf != null) {
                SoapVersion soapVersion = (SoapVersion)message.get(SoapVersion.class.getName());
                if (soapVersion != null && soapVersion.getVersion() != 1.1) {
                    if (f instanceof SoapFault) {
                        Iterator it = CastUtils.cast(sf.getFault().getFaultSubcodes());
                        while (it.hasNext()) {
                            ((SoapFault)f).addSubCode((QName)it.next());
                        }
                    }
                    if (sf.getFault().getFaultReasonLocales().hasNext()) {
                        Locale lang = sf.getFault().getFaultReasonLocales().next();
                        String convertedLang = lang.getLanguage();
                        String country = lang.getCountry();
                        if (country.length() > 0) {
                            convertedLang = convertedLang + '-' + country;
                        }
                        f.setLang(convertedLang);
                    }
                }
                message.setContent(Exception.class, f);
            }
        }
        catch (Exception thr) {
            // empty catch block
        }
        Throwable cause = f.getCause();
        WebFault fault = null;
        if (cause != null && (fault = this.getWebFaultAnnotation(cause.getClass())) == null && cause.getCause() != null && ((fault = this.getWebFaultAnnotation(cause.getCause().getClass())) != null || cause instanceof RuntimeException)) {
            cause = cause.getCause();
        }
        if (cause instanceof Exception && fault != null) {
            Exception ex = (Exception)cause;
            Object faultInfo = null;
            try {
                Method method2 = cause.getClass().getMethod("getFaultInfo", new Class[0]);
                faultInfo = method2.invoke((Object)cause, new Object[0]);
            }
            catch (NoSuchMethodException e) {
                faultInfo = this.createFaultInfoBean(fault, cause);
            }
            catch (InvocationTargetException e) {
                throw new Fault(new org.apache.cxf.common.i18n.Message("INVOCATION_TARGET_EXC", BUNDLE, new Object[0]), (Throwable)e);
            }
            catch (IllegalAccessException | IllegalArgumentException e) {
                throw new Fault(new org.apache.cxf.common.i18n.Message("COULD_NOT_INVOKE", BUNDLE, new Object[0]), (Throwable)e);
            }
            Service service2 = message.getExchange().getService();
            try {
                MessagePartInfo part;
                DataWriter<XMLStreamWriter> writer = service2.getDataBinding().createWriter(XMLStreamWriter.class);
                if (ServiceUtils.isSchemaValidationEnabled(SchemaValidation.SchemaValidationType.OUT, message)) {
                    Schema schema = EndpointReferenceUtils.getSchema(service2.getServiceInfos().get(0), message.getExchange().getBus());
                    writer.setSchema(schema);
                }
                OperationInfo op = null;
                if (message.getExchange().getBindingOperationInfo() != null) {
                    op = message.getExchange().getBindingOperationInfo().getOperationInfo();
                }
                QName faultName = this.getFaultName(fault, cause.getClass(), op);
                MessagePartInfo messagePartInfo = part = op != null ? this.getFaultMessagePart(faultName, op) : null;
                if (f.hasDetails()) {
                    writer.write(faultInfo, part, new W3CDOMStreamWriter(f.getDetail()));
                } else {
                    writer.write(faultInfo, part, new W3CDOMStreamWriter(f.getOrCreateDetail()));
                    if (!f.getDetail().hasChildNodes()) {
                        f.setDetail(null);
                    }
                }
                f.setMessage(ex.getMessage());
            }
            catch (Fault f2) {
                message.setContent(Exception.class, f2);
                super.handleMessage(message);
            }
            catch (Exception nex) {
                LOG.log(Level.WARNING, "EXCEPTION_WHILE_WRITING_FAULT", nex);
            }
        } else {
            if (cause instanceof SOAPFaultException && ((SOAPFaultException)cause).getFault().hasDetail()) {
                return;
            }
            FaultMode mode = message.get(FaultMode.class);
            if (mode == FaultMode.CHECKED_APPLICATION_FAULT) {
                super.handleMessage(message);
            }
        }
    }

    private Object createFaultInfoBean(WebFault fault, Throwable cause) {
        if (!StringUtils.isEmpty(fault.faultBean())) {
            try {
                Class<?> cls = ClassLoaderUtils.loadClass(fault.faultBean(), cause.getClass());
                if (cls != null) {
                    Method[] meth;
                    Object ret = cls.newInstance();
                    for (Method m4 : meth = cause.getClass().getMethods()) {
                        if (m4.getParameterTypes().length != 0 || !m4.getName().startsWith("get") && !m4.getName().startsWith("is")) continue;
                        try {
                            String name = m4.getName().startsWith("get") ? "set" + m4.getName().substring(3) : "set" + m4.getName().substring(2);
                            Method m22 = cls.getMethod(name, m4.getReturnType());
                            m22.invoke(ret, m4.invoke((Object)cause, new Object[0]));
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                    }
                    return ret;
                }
            }
            catch (ClassNotFoundException | IllegalAccessException | InstantiationException reflectiveOperationException) {
                // empty catch block
            }
        }
        LOG.fine("Using @WebFault annotated class " + cause.getClass().getName() + " as faultInfo since getFaultInfo() was not found");
        return cause;
    }

    private MessagePartInfo getFaultMessagePart(QName qname, OperationInfo op) {
        for (FaultInfo faultInfo : op.getFaults()) {
            for (MessagePartInfo mpi : faultInfo.getMessageParts()) {
                String ns = null;
                ns = mpi.isElement() ? mpi.getElementQName().getNamespaceURI() : mpi.getTypeQName().getNamespaceURI();
                if (!qname.getLocalPart().equals(mpi.getConcreteName().getLocalPart()) || !qname.getNamespaceURI().equals(ns)) continue;
                return mpi;
            }
        }
        return null;
    }
}

