/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.interceptors;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import javax.activation.DataSource;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import org.apache.cxf.attachment.AttachmentUtil;
import org.apache.cxf.helpers.LoadingByteArrayOutputStream;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.staxutils.StaxSource;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamReader;

public class MessageModeInInterceptor
extends AbstractPhaseInterceptor<Message> {
    Class<?> type;
    QName bindingName;
    Class<?> soapMsgClass;

    public MessageModeInInterceptor(Class<?> c, QName bName) {
        super("post-logical");
        this.bindingName = bName;
        this.type = c;
        try {
            this.soapMsgClass = Class.forName("javax.xml.soap.SOAPMessage");
        }
        catch (Throwable t2) {
            this.soapMsgClass = null;
        }
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        BindingOperationInfo bop = message.getExchange().getBindingOperationInfo();
        if (bop == null || !this.bindingName.equals(bop.getBinding().getName())) {
            return;
        }
        Object o = message.getContent(this.soapMsgClass);
        if (o != null) {
            this.doFromSoapMessage(message, o);
        } else if (DataSource.class.isAssignableFrom(this.type)) {
            this.doDataSource(message);
        }
    }

    private void doDataSource(Message message) {
        MessageContentsList list = (MessageContentsList)message.getContent(List.class);
        if (message.getAttachments() != null && !message.getAttachments().isEmpty() && list != null && !list.isEmpty() && list.get(0) instanceof DataSource) {
            list.set(0, new MultiPartDataSource(message, (DataSource)list.get(0)));
        }
    }

    private void doFromSoapMessage(Message message, Object sm) {
        Object o;
        MessageContentsList list;
        block19: {
            SOAPMessage m4 = (SOAPMessage)sm;
            list = (MessageContentsList)message.getContent(List.class);
            if (list == null) {
                list = new MessageContentsList();
                message.setContent(List.class, list);
            }
            o = m4;
            if (StreamSource.class.isAssignableFrom(this.type)) {
                try (CachedOutputStream out = new CachedOutputStream();){
                    XMLStreamWriter xsw = StaxUtils.createXMLStreamWriter(out);
                    StaxUtils.copy((Source)new DOMSource(m4.getSOAPPart()), xsw);
                    xsw.close();
                    o = new StreamSource(out.getInputStream());
                    break block19;
                }
                catch (Exception e) {
                    throw new Fault(e);
                }
            }
            if (SAXSource.class.isAssignableFrom(this.type)) {
                o = new StaxSource(new W3CDOMStreamReader(m4.getSOAPPart()));
            } else if (Source.class.isAssignableFrom(this.type)) {
                o = new DOMSource(m4.getSOAPPart());
            }
        }
        list.set(0, o);
    }

    private static class MultiPartDataSource
    implements DataSource {
        final Iterator<Attachment> atts;
        final String contentType;
        final String boundary;
        final String start;
        final LoadingByteArrayOutputStream bout = new LoadingByteArrayOutputStream();
        Writer writer;
        DataSource rootPart;
        InputStream current;
        boolean writingHeaders;
        Attachment att;

        MultiPartDataSource(Message message, DataSource root) {
            this.atts = message.getAttachments().iterator();
            String s2 = (String)message.get("Content-Type");
            this.boundary = this.findBoundary(s2);
            this.start = this.findStart(s2);
            if (!s2.contains(this.boundary)) {
                s2 = s2 + "; boundary=\"" + this.boundary + "\"";
            }
            this.contentType = s2;
            this.rootPart = root;
            try {
                this.writer = new OutputStreamWriter((OutputStream)this.bout, "ASCII");
                this.writer.append("Content-Type: ").append(this.contentType).append("\r\n\r\n");
                this.writer.flush();
                this.current = this.bout.createInputStream();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }

        @Override
        public String getContentType() {
            return this.contentType;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new InputStream(){

                @Override
                public int read() throws IOException {
                    int i = current.read();
                    if (i == -1) {
                        this.nextCurrent();
                        i = current.read();
                    }
                    return i;
                }
            };
        }

        private void nextCurrent() throws IOException {
            if (this.rootPart != null) {
                if (this.writingHeaders) {
                    this.writingHeaders = false;
                    this.current = this.rootPart.getInputStream();
                    this.rootPart = null;
                } else {
                    this.writingHeaders = true;
                    this.bout.reset();
                    this.writer.append("--").append(this.boundary).append("\r\n");
                    this.writer.append("Content-Type: ").append(this.rootPart.getContentType()).append("\r\n");
                    this.writer.append("Content-ID: <").append(this.start).append(">\r\n\r\n");
                    this.writer.flush();
                    this.current = this.bout.createInputStream();
                }
            } else if (this.writingHeaders) {
                this.writingHeaders = false;
                this.current = this.att.getDataHandler().getInputStream();
            } else if (this.atts.hasNext()) {
                this.att = this.atts.next();
                this.writingHeaders = true;
                this.bout.reset();
                this.writer.append("\r\n");
                this.writer.append("--").append(this.boundary).append("\r\n");
                Iterator<String> heads = this.att.getHeaderNames();
                while (heads.hasNext()) {
                    String s2 = heads.next();
                    this.writer.append(s2).append(": ").append(this.att.getHeader(s2)).append("\r\n");
                }
                this.writer.append("\r\n");
                this.writer.flush();
                this.current = this.bout.createInputStream();
            }
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            return null;
        }

        private String findStart(String ct) {
            int idx = ct.indexOf("start=");
            if (idx == -1) {
                return "root";
            }
            if ((ct = ct.substring(idx + 6)).charAt(0) == '\"') {
                ct = ct.substring(1);
                idx = ct.indexOf(34);
                return ct.substring(0, idx);
            }
            idx = ct.indexOf(59);
            if (idx == -1) {
                return ct;
            }
            return ct.substring(0, idx);
        }

        private String findBoundary(String ct) {
            int idx = ct.indexOf("boundary=");
            if (idx == -1) {
                return AttachmentUtil.getUniqueBoundaryValue();
            }
            if ((ct = ct.substring(idx + 9)).charAt(0) == '\"') {
                ct = ct.substring(1);
                idx = ct.indexOf(34);
                return ct.substring(0, idx);
            }
            idx = ct.indexOf(59);
            if (idx == -1) {
                return ct;
            }
            return ct.substring(0, idx);
        }
    }
}

