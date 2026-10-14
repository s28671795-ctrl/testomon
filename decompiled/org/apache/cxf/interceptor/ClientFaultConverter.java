/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.xpath.XPathConstants;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.databinding.DataReader;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.helpers.XPathUtils;
import org.apache.cxf.interceptor.AbstractInDatabindingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.FaultMode;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.FaultInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.staxutils.W3CDOMStreamReader;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ClientFaultConverter
extends AbstractInDatabindingInterceptor {
    public static final String DISABLE_FAULT_MAPPING = "disable-fault-mapping";
    public static final Pattern CAUSE_SUFFIX_SPLITTER = Pattern.compile("#*#", 24);
    private static final Logger LOG = LogUtils.getLogger(ClientFaultConverter.class);

    public ClientFaultConverter() {
        super("unmarshal");
    }

    public ClientFaultConverter(String phase) {
        super(phase);
    }

    @Override
    public void handleMessage(Message msg) {
        Fault fault = (Fault)msg.getContent(Exception.class);
        if (fault.getDetail() != null && !MessageUtils.getContextualBoolean(msg, DISABLE_FAULT_MAPPING, false)) {
            this.processFaultDetail(fault, msg);
            this.setStackTrace(fault, msg);
        }
        FaultMode faultMode = FaultMode.UNCHECKED_APPLICATION_FAULT;
        Method m4 = msg.getExchange().get(Method.class);
        if (m4 != null) {
            Exception e = msg.getContent(Exception.class);
            for (Class<?> cl : m4.getExceptionTypes()) {
                if (!cl.isInstance(e)) continue;
                faultMode = FaultMode.CHECKED_APPLICATION_FAULT;
                break;
            }
        }
        msg.getExchange().put(FaultMode.class, faultMode);
    }

    protected void processFaultDetail(Fault fault, Message msg) {
        Object e;
        DataReader<Object> reader;
        Element exDetail = (Element)DOMUtils.getChild((Node)fault.getDetail(), 1);
        if (exDetail == null) {
            return;
        }
        QName qname = new QName(exDetail.getNamespaceURI(), exDetail.getLocalName());
        FaultInfo faultWanted = null;
        MessagePartInfo part = null;
        BindingOperationInfo boi = msg.getExchange().getBindingOperationInfo();
        if (boi == null) {
            return;
        }
        if (boi.isUnwrapped()) {
            boi = boi.getWrappedOperation();
        }
        for (FaultInfo faultInfo : boi.getOperationInfo().getFaults()) {
            for (MessagePartInfo mpi : faultInfo.getMessageParts()) {
                if (!qname.equals(mpi.getConcreteName())) continue;
                faultWanted = faultInfo;
                part = mpi;
                break;
            }
            if (faultWanted == null) continue;
            break;
        }
        if (faultWanted == null) {
            for (FaultInfo faultInfo : boi.getOperationInfo().getFaults()) {
                for (MessagePartInfo mpi : faultInfo.getMessageParts()) {
                    if (!qname.getLocalPart().equals(mpi.getConcreteName().getLocalPart())) continue;
                    faultWanted = faultInfo;
                    part = mpi;
                    break;
                }
                if (faultWanted == null) continue;
                break;
            }
        }
        if (faultWanted == null) {
            return;
        }
        Service s2 = msg.getExchange().getService();
        DataBinding dataBinding = s2.getDataBinding();
        if (this.isDOMSupported(dataBinding)) {
            reader = this.getNodeDataReader(msg);
            reader.setProperty(DataReader.FAULT, fault);
            e = reader.read(part, exDetail);
        } else {
            reader = this.getDataReader(msg);
            W3CDOMStreamReader xsr = new W3CDOMStreamReader(exDetail);
            try {
                xsr.nextTag();
            }
            catch (XMLStreamException e1) {
                throw new Fault(e1);
            }
            reader.setProperty(DataReader.FAULT, fault);
            e = reader.read(part, xsr);
        }
        if (!(e instanceof Exception)) {
            try {
                Constructor<Object> constructor;
                Class exClass = faultWanted.getProperty(Class.class.getName(), Class.class);
                if (exClass == null) {
                    return;
                }
                if (e == null) {
                    constructor = exClass.getConstructor(String.class);
                    e = constructor.newInstance(fault.getMessage());
                } else {
                    try {
                        constructor = this.getConstructor(exClass, e);
                        e = constructor.newInstance(fault.getMessage(), e);
                    }
                    catch (NoSuchMethodException e1) {
                        e = this.convertFaultBean(exClass, e, fault);
                    }
                }
                msg.setContent(Exception.class, e);
            }
            catch (Exception e1) {
                LogUtils.log(LOG, Level.INFO, "EXCEPTION_WHILE_CREATING_EXCEPTION", (Throwable)e1, (Object)e1.getMessage());
            }
        } else {
            if (fault.getMessage() != null) {
                try {
                    Field f = Throwable.class.getDeclaredField("detailMessage");
                    ReflectionUtil.setAccessible(f);
                    f.set(e, fault.getMessage());
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            msg.setContent(Exception.class, e);
        }
    }

    private Constructor<?> getConstructor(Class<?> faultClass, Object e) throws NoSuchMethodException {
        Constructor<?>[] cons;
        Class<?> beanClass = e.getClass();
        for (Constructor<?> c : cons = faultClass.getConstructors()) {
            if (c.getParameterTypes().length != 2 || !String.class.equals(c.getParameterTypes()[0]) || !c.getParameterTypes()[1].isInstance(e)) continue;
            return c;
        }
        try {
            return faultClass.getConstructor(String.class, beanClass);
        }
        catch (NoSuchMethodException ex) {
            Class<?> cls = this.getPrimitiveClass(beanClass);
            if (cls != null) {
                return faultClass.getConstructor(String.class, cls);
            }
            throw ex;
        }
    }

    private boolean isDOMSupported(DataBinding db) {
        boolean supportsDOM = false;
        for (Class<?> c : db.getSupportedReaderFormats()) {
            if (!c.equals(Node.class)) continue;
            supportsDOM = true;
        }
        return supportsDOM;
    }

    private void setStackTrace(Fault fault, Message msg) {
        Throwable cause = null;
        HashMap<String, String> ns = new HashMap<String, String>();
        XPathUtils xu = new XPathUtils(ns);
        ns.put("s", "http://cxf.apache.org/fault");
        String ss = (String)xu.getValue("//s:stackTrace/text()", fault.getDetail(), XPathConstants.STRING);
        ArrayList<StackTraceElement> stackTraceList = new ArrayList<StackTraceElement>();
        if (!StringUtils.isEmpty(ss)) {
            Iterator<String> linesIterator = Arrays.asList(CAUSE_SUFFIX_SPLITTER.split(ss)).iterator();
            while (linesIterator.hasNext()) {
                String oneLine = linesIterator.next();
                if (oneLine.startsWith("Caused by:")) {
                    cause = this.getCause(linesIterator, oneLine);
                    break;
                }
                stackTraceList.add(ClientFaultConverter.parseStackTrackLine(oneLine));
            }
            if (!stackTraceList.isEmpty() || cause != null) {
                Exception e = msg.getContent(Exception.class);
                if (!stackTraceList.isEmpty()) {
                    StackTraceElement[] stackTraceElement = new StackTraceElement[stackTraceList.size()];
                    e.setStackTrace(stackTraceList.toArray(stackTraceElement));
                } else if (cause != null && cause.getMessage() != null && cause.getMessage().startsWith(e.getClass().getName())) {
                    e.setStackTrace(cause.getStackTrace());
                    if (cause.getCause() != null) {
                        e.initCause(cause.getCause());
                    }
                } else if (cause != null) {
                    e.initCause(cause);
                }
            }
        }
    }

    private Throwable getCause(Iterator<String> linesIterator, String firstLine) {
        String cn;
        firstLine = firstLine.substring(firstLine.indexOf(58) + 1).trim();
        Throwable res = null;
        if (firstLine.indexOf(58) != -1 && (cn = firstLine.substring(0, firstLine.indexOf(58)).trim()).startsWith("java.lang")) {
            try {
                res = (Throwable)Class.forName(cn).getConstructor(String.class).newInstance(firstLine.substring(firstLine.indexOf(58) + 2));
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        if (res == null) {
            res = new Throwable(firstLine);
        }
        ArrayList<StackTraceElement> stackTraceList = new ArrayList<StackTraceElement>();
        while (linesIterator.hasNext()) {
            String oneLine = linesIterator.next();
            if (oneLine.startsWith("Caused by:")) {
                Throwable nestedCause = this.getCause(linesIterator, oneLine);
                res.initCause(nestedCause);
                break;
            }
            stackTraceList.add(ClientFaultConverter.parseStackTrackLine(oneLine));
        }
        StackTraceElement[] stackTraceElement = new StackTraceElement[stackTraceList.size()];
        res.setStackTrace(stackTraceList.toArray(stackTraceElement));
        return res;
    }

    private static StackTraceElement parseStackTrackLine(String oneLine) {
        StringTokenizer stInner = new StringTokenizer(oneLine, "!");
        return new StackTraceElement(stInner.nextToken(), stInner.nextToken(), stInner.nextToken(), Integer.parseInt(stInner.nextToken()));
    }

    private Class<?> getPrimitiveClass(Class<?> cls) {
        if (cls.isPrimitive()) {
            return cls;
        }
        try {
            Field field2 = cls.getField("TYPE");
            Class<?> obj = cls;
            Object type = field2.get(obj);
            if (type instanceof Class) {
                return (Class)type;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }

    private Exception convertFaultBean(Class<?> exClass, Object faultBean, Fault fault) throws Exception {
        Method[] meth;
        Constructor<?> constructor = exClass.getConstructor(String.class);
        Exception e = (Exception)constructor.newInstance(fault.getMessage());
        Class<?> obj = exClass;
        while (!obj.equals(Object.class)) {
            Field[] fields;
            Field[] fieldArray = fields = obj.getDeclaredFields();
            int n = fieldArray.length;
            for (int i = 0; i < n; ++i) {
                Field f = fieldArray[i];
                try {
                    Field beanField = faultBean.getClass().getDeclaredField(f.getName());
                    ReflectionUtil.setAccessible(beanField);
                    ReflectionUtil.setAccessible(f);
                    f.set(e, beanField.get(faultBean));
                    continue;
                }
                catch (NoSuchFieldException beanField) {
                    // empty catch block
                }
            }
            obj = obj.getSuperclass();
        }
        for (Method m4 : meth = faultBean.getClass().getMethods()) {
            if (m4.getParameterTypes().length != 0 || !m4.getName().startsWith("get") && !m4.getName().startsWith("is")) continue;
            try {
                String name = m4.getName().startsWith("get") ? "set" + m4.getName().substring(3) : "set" + m4.getName().substring(2);
                Method m22 = exClass.getMethod(name, m4.getReturnType());
                m22.invoke((Object)e, m4.invoke(faultBean, new Object[0]));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return e;
    }
}

