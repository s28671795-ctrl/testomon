/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxb;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.XmlAttachmentRef;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.attachment.AttachmentMarshaller;
import javax.xml.bind.attachment.AttachmentUnmarshaller;
import javax.xml.validation.Schema;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.jaxb.attachment.JAXBAttachmentMarshaller;
import org.apache.cxf.jaxb.attachment.JAXBAttachmentUnmarshaller;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.AbstractMessageContainer;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;

public abstract class JAXBDataBase {
    static final Logger LOG = LogUtils.getL7dLogger(JAXBDataBase.class);
    protected JAXBContext context;
    protected Schema schema;
    protected Collection<Attachment> attachments;
    protected Integer mtomThreshold;

    protected JAXBDataBase(JAXBContext ctx) {
        this.context = ctx;
    }

    public void setSchema(Schema s2) {
        this.schema = s2;
    }

    public void setJAXBContext(JAXBContext jc) {
        this.context = jc;
    }

    public Schema getSchema() {
        return this.schema;
    }

    public JAXBContext getJAXBContext() {
        return this.context;
    }

    public Collection<Attachment> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(Collection<Attachment> attachments) {
        this.attachments = attachments;
    }

    protected AttachmentUnmarshaller getAttachmentUnmarshaller() {
        return new JAXBAttachmentUnmarshaller(this.attachments);
    }

    protected AttachmentMarshaller getAttachmentMarshaller() {
        return new JAXBAttachmentMarshaller(this.attachments, this.mtomThreshold);
    }

    public void setProperty(String prop, Object value) {
    }

    protected Annotation[] getJAXBAnnotation(MessagePartInfo mpi) {
        List<Annotation> annoList = null;
        if (mpi != null && (annoList = this.extractJAXBAnnotations((Annotation[])mpi.getProperty("parameter.annotations"))) == null) {
            annoList = this.extractJAXBAnnotations(this.getReturnMethodAnnotations(mpi));
        }
        return annoList == null ? new Annotation[]{} : annoList.toArray(new Annotation[0]);
    }

    private List<Annotation> extractJAXBAnnotations(Annotation[] anns) {
        ArrayList<Annotation> annoList = null;
        if (anns != null) {
            for (Annotation ann : anns) {
                if (!(ann instanceof XmlList) && !(ann instanceof XmlAttachmentRef) && !(ann instanceof XmlJavaTypeAdapter)) continue;
                if (annoList == null) {
                    annoList = new ArrayList<Annotation>();
                }
                annoList.add(ann);
            }
        }
        return annoList;
    }

    private Annotation[] getReturnMethodAnnotations(MessagePartInfo mpi) {
        AbstractMessageContainer mi = mpi.getMessageInfo();
        if (mi == null || !this.isOutputMessage(mi)) {
            return null;
        }
        OperationInfo oi = mi != null ? mi.getOperation() : null;
        return oi != null ? (Annotation[])oi.getProperty("method.return.annotations") : null;
    }

    protected boolean isOutputMessage(AbstractMessageContainer messageContainer) {
        if (messageContainer instanceof MessageInfo) {
            return MessageInfo.Type.OUTPUT.equals((Object)((MessageInfo)messageContainer).getType());
        }
        return false;
    }

    public Integer getMtomThreshold() {
        return this.mtomThreshold;
    }

    public void setMtomThreshold(Integer threshold) {
        this.mtomThreshold = threshold;
    }

    protected final boolean honorJAXBAnnotations(MessagePartInfo part) {
        if (part == null) {
            return false;
        }
        if (!part.isElement()) {
            return true;
        }
        Boolean b = (Boolean)part.getProperty("honor.jaxb.annotations");
        return b != null && b != false;
    }

    protected ValidationEventHandler getValidationEventHandler(String cn) {
        try {
            return (ValidationEventHandler)ClassLoaderUtils.loadClass(cn, this.getClass()).newInstance();
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            LOG.log(Level.INFO, "Could not create validation event handler", e);
            return null;
        }
    }

    protected ValidationEventHandler getValidationEventHandler(Message m4, String property) {
        Object value = m4.getContextualProperty(property);
        ValidationEventHandler veventHandler = null;
        veventHandler = value instanceof String ? this.getValidationEventHandler((String)value) : (ValidationEventHandler)value;
        if (veventHandler == null) {
            value = m4.getContextualProperty("jaxb-validation-event-handler");
            veventHandler = value instanceof String ? this.getValidationEventHandler((String)value) : (ValidationEventHandler)value;
        }
        return veventHandler;
    }
}

