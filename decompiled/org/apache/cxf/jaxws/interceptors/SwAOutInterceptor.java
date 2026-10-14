/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.interceptors;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import org.apache.cxf.attachment.AttachmentImpl;
import org.apache.cxf.attachment.ByteDataSource;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.model.SoapBodyInfo;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.helpers.LoadingByteArrayOutputStream;
import org.apache.cxf.interceptor.AttachmentOutInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.jaxws.interceptors.HolderOutInterceptor;
import org.apache.cxf.jaxws.interceptors.WrapperClassOutInterceptor;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.staxutils.StaxUtils;

public class SwAOutInterceptor
extends AbstractSoapInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(SwAOutInterceptor.class);
    private static final Map<String, Method> SWA_REF_METHOD = new ConcurrentHashMap<String, Method>(4, 0.75f, 2);
    private static final Set<String> SWA_REF_NO_METHOD = Collections.newSetFromMap(new ConcurrentHashMap(4, 0.75f, 2));
    AttachmentOutInterceptor attachOut = new AttachmentOutInterceptor();

    public SwAOutInterceptor() {
        super("pre-logical");
        this.addAfter(HolderOutInterceptor.class.getName());
        this.addBefore(WrapperClassOutInterceptor.class.getName());
    }

    private boolean callSWARefMethod(final JAXBContext ctx) {
        String cname = ctx.getClass().getName();
        Method m4 = SWA_REF_METHOD.get(cname);
        if (m4 == null && !SWA_REF_NO_METHOD.contains(cname)) {
            try {
                m4 = AccessController.doPrivileged(new PrivilegedExceptionAction<Method>(){

                    @Override
                    public Method run() throws Exception {
                        Method hasSwaRefMethod = ctx.getClass().getMethod("hasSwaRef", new Class[0]);
                        if (!hasSwaRefMethod.isAccessible()) {
                            hasSwaRefMethod.setAccessible(true);
                        }
                        return hasSwaRefMethod;
                    }
                });
                if (m4 == null) {
                    SWA_REF_NO_METHOD.add(cname);
                } else {
                    SWA_REF_METHOD.put(cname, m4);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        try {
            if (m4 != null) {
                return (Boolean)m4.invoke((Object)ctx, new Object[0]);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return false;
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        boolean client;
        BindingMessageInfo bmi;
        Exchange ex = message.getExchange();
        BindingOperationInfo bop = ex.getBindingOperationInfo();
        if (bop == null) {
            return;
        }
        if (bop.isUnwrapped()) {
            bop = bop.getWrappedOperation();
        }
        BindingMessageInfo bindingMessageInfo = bmi = (client = this.isRequestor(message)) ? bop.getInput() : bop.getOutput();
        if (bmi == null) {
            return;
        }
        SoapBodyInfo sbi = bmi.getExtensor(SoapBodyInfo.class);
        if (sbi == null || sbi.getAttachments() == null || sbi.getAttachments().isEmpty()) {
            Service s2 = ex.getService();
            DataBinding db = s2.getDataBinding();
            if (db instanceof JAXBDataBinding && this.hasSwaRef((JAXBDataBinding)db)) {
                this.setupAttachmentOutput(message);
            }
            return;
        }
        this.processAttachments(message, sbi);
    }

    protected void processAttachments(SoapMessage message, SoapBodyInfo sbi) {
        Collection<Attachment> atts = this.setupAttachmentOutput(message);
        List outObjects = CastUtils.cast(message.getContent(List.class));
        for (MessagePartInfo mpi : sbi.getAttachments()) {
            String partName = mpi.getConcreteName().getLocalPart();
            String ct = (String)mpi.getProperty("Content-Type");
            String id = partName + '=' + UUID.randomUUID() + "@apache.org";
            int idx = mpi.getIndex();
            Object o = outObjects.get(idx);
            if (o == null) continue;
            outObjects.set(idx, null);
            DataHandler dh = null;
            if (o instanceof Source) {
                dh = new DataHandler(this.createDataSource((Source)o, ct));
            } else if (o instanceof Image) {
                final Image img = (Image)o;
                final String contentType = ct;
                dh = new DataHandler(o, ct){

                    @Override
                    public InputStream getInputStream() throws IOException {
                        LoadingByteArrayOutputStream bout = new LoadingByteArrayOutputStream();
                        this.writeTo(bout);
                        return bout.createInputStream();
                    }

                    @Override
                    public void writeTo(OutputStream out) throws IOException {
                        ImageWriter writer = null;
                        Iterator<ImageWriter> writers = ImageIO.getImageWritersByMIMEType(contentType);
                        if (writers.hasNext()) {
                            writer = writers.next();
                        }
                        if (writer != null) {
                            BufferedImage bimg = SwAOutInterceptor.this.convertToBufferedImage(img);
                            ImageOutputStream iout = ImageIO.createImageOutputStream(out);
                            writer.setOutput(iout);
                            writer.write(bimg);
                            writer.dispose();
                            iout.flush();
                            out.flush();
                        }
                    }
                };
            } else if (o instanceof DataHandler) {
                dh = (DataHandler)o;
                ct = dh.getContentType();
                try {
                    if ("text/xml".equals(ct) && dh.getContent() instanceof Source) {
                        dh = new DataHandler(this.createDataSource((Source)dh.getContent(), ct));
                    }
                }
                catch (IOException img) {}
            } else if (o instanceof byte[]) {
                if (ct == null) {
                    ct = "application/octet-stream";
                }
                dh = new DataHandler(new ByteDataSource((byte[])o, ct));
            } else if (o instanceof String) {
                if (ct == null) {
                    ct = "text/plain; charset='UTF-8'";
                }
                dh = new DataHandler(new ByteDataSource(((String)o).getBytes(StandardCharsets.UTF_8), ct));
            } else {
                throw new Fault(new Message("ATTACHMENT_NOT_SUPPORTED", LOG, o.getClass()));
            }
            AttachmentImpl att = new AttachmentImpl(id);
            att.setDataHandler(dh);
            att.setHeader("Content-Type", ct);
            att.setHeader("Content-ID", "<" + id + ">");
            atts.add(att);
        }
    }

    protected boolean hasSwaRef(JAXBDataBinding db) {
        JAXBContext context = db.getContext();
        return this.callSWARefMethod(context);
    }

    private DataSource createDataSource(Source o, String ct) {
        ByteDataSource ds = null;
        if (o instanceof StreamSource) {
            StreamSource src = (StreamSource)o;
            try {
                if (src.getInputStream() != null) {
                    try (ByteArrayOutputStream bos = new ByteArrayOutputStream(2048);){
                        IOUtils.copy(src.getInputStream(), bos, 1024);
                        ds = new ByteDataSource(bos.toByteArray(), ct);
                    }
                }
                ds = new ByteDataSource(IOUtils.toString(src.getReader()).getBytes(StandardCharsets.UTF_8), ct);
            }
            catch (IOException e) {
                throw new Fault(e);
            }
        } else {
            ByteArrayOutputStream bwriter = new ByteArrayOutputStream();
            XMLStreamWriter writer = null;
            try {
                writer = StaxUtils.createXMLStreamWriter(bwriter);
                StaxUtils.copy(o, writer);
                writer.flush();
                ds = new ByteDataSource(bwriter.toByteArray(), ct);
            }
            catch (XMLStreamException e1) {
                throw new Fault(e1);
            }
            finally {
                StaxUtils.close(writer);
            }
        }
        return ds;
    }

    private BufferedImage convertToBufferedImage(Image image) throws IOException {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }
        MediaTracker tracker = new MediaTracker(new Component(){
            private static final long serialVersionUID = 6412221228374321325L;
        });
        tracker.addImage(image, 0);
        try {
            tracker.waitForAll();
        }
        catch (InterruptedException e) {
            throw new Fault(e);
        }
        BufferedImage bufImage = new BufferedImage(image.getWidth(null), image.getHeight(null), 2);
        Graphics2D g2 = bufImage.createGraphics();
        g2.drawImage(image, 0, 0, null);
        return bufImage;
    }

    private Collection<Attachment> setupAttachmentOutput(SoapMessage message) {
        message.getInterceptorChain().add(this.attachOut);
        message.put("write.attachments", (Object)Boolean.TRUE);
        Collection<Attachment> atts = message.getAttachments();
        if (atts == null) {
            atts = new ArrayList<Attachment>();
            message.setAttachments(atts);
        }
        return atts;
    }
}

