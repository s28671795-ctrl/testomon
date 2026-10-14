/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.databinding.DataWriter;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.FaultInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.w3c.dom.Node;

public class FaultOutInterceptor
extends AbstractPhaseInterceptor<Message> {
    private static final Logger LOG = LogUtils.getL7dLogger(FaultOutInterceptor.class);

    public FaultOutInterceptor() {
        super("pre-protocol");
    }

    @Override
    public void handleMessage(Message message) {
        Fault f = (Fault)message.getContent(Exception.class);
        if (f == null) {
            return;
        }
        Throwable cause = f.getCause();
        if (cause == null) {
            return;
        }
        BindingOperationInfo bop = message.getExchange().getBindingOperationInfo();
        if (bop == null) {
            return;
        }
        FaultInfo fi = this.getFaultForClass(bop, cause.getClass());
        if (cause instanceof Exception && fi != null) {
            Exception ex = (Exception)cause;
            Object bean = this.getFaultBean(cause, fi, message);
            Service service2 = message.getExchange().getService();
            MessagePartInfo part = fi.getFirstMessagePart();
            DataBinding db = service2.getDataBinding();
            try {
                if (this.isDOMSupported(db)) {
                    DataWriter<Node> writer = db.createWriter(Node.class);
                    if (f.hasDetails()) {
                        writer.write(bean, part, f.getDetail());
                    } else {
                        writer.write(bean, part, f.getOrCreateDetail());
                        if (!f.getDetail().hasChildNodes()) {
                            f.setDetail(null);
                        }
                    }
                } else if (f.hasDetails()) {
                    W3CDOMStreamWriter xsw = new W3CDOMStreamWriter(f.getDetail());
                    DataWriter<XMLStreamWriter> writer = db.createWriter(XMLStreamWriter.class);
                    writer.write(bean, part, xsw);
                } else {
                    W3CDOMStreamWriter xsw = new W3CDOMStreamWriter(f.getOrCreateDetail());
                    DataWriter<XMLStreamWriter> writer = db.createWriter(XMLStreamWriter.class);
                    writer.write(bean, part, xsw);
                    if (!f.getDetail().hasChildNodes()) {
                        f.setDetail(null);
                    }
                }
                f.setMessage(ex.getMessage());
            }
            catch (Exception fex) {
                LOG.log(Level.WARNING, "EXCEPTION_WHILE_WRITING_FAULT", fex);
            }
        }
    }

    private boolean isDOMSupported(DataBinding db) {
        boolean supportsDOM = false;
        for (Class<?> c : db.getSupportedWriterFormats()) {
            if (!c.equals(Node.class)) continue;
            supportsDOM = true;
        }
        return supportsDOM;
    }

    protected Object getFaultBean(Throwable cause, FaultInfo faultPart, Message message) {
        if (cause instanceof FaultInfoException) {
            try {
                Method method2 = cause.getClass().getMethod("getFaultInfo", new Class[0]);
                return method2.invoke((Object)cause, new Object[0]);
            }
            catch (InvocationTargetException e) {
                throw new Fault(new org.apache.cxf.common.i18n.Message("INVOKE_FAULT_INFO", LOG, new Object[0]), (Throwable)e);
            }
            catch (NoSuchMethodException e) {
                throw new Fault(new org.apache.cxf.common.i18n.Message("NO_FAULT_INFO_METHOD", LOG, new Object[0]), (Throwable)e);
            }
            catch (Exception e) {
                throw new Fault(new org.apache.cxf.common.i18n.Message("NO_ACCCESS_FAULT_INFO", LOG, new Object[0]), (Throwable)e);
            }
        }
        return cause;
    }

    public FaultInfo getFaultForClass(BindingOperationInfo op, Class<?> class1) {
        FaultInfo selectedFaultInfo = null;
        Class selectedFaultInfoClass = null;
        for (BindingFaultInfo bfi : op.getFaults()) {
            FaultInfo faultInfo = bfi.getFaultInfo();
            Class c = (Class)faultInfo.getProperty(Class.class.getName());
            if (c == null || !c.isAssignableFrom(class1) || selectedFaultInfo != null && (selectedFaultInfoClass == null || !selectedFaultInfoClass.isAssignableFrom(c))) continue;
            selectedFaultInfo = faultInfo;
            selectedFaultInfoClass = c;
        }
        return selectedFaultInfo;
    }

    public static interface FaultInfoException {
    }
}

