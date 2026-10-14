/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxb.io;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.databinding.DataReader;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxb.JAXBDataBase;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.jaxb.JAXBEncoderDecoder;
import org.apache.cxf.jaxb.UnmarshallerEventHandler;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.service.model.MessagePartInfo;

public class DataReaderImpl<T>
extends JAXBDataBase
implements DataReader<T> {
    private static final Logger LOG = LogUtils.getLogger(JAXBDataBinding.class);
    JAXBDataBinding databinding;
    boolean unwrapJAXBElement = true;
    ValidationEventHandler veventHandler;
    boolean setEventHandler = true;

    public DataReaderImpl(JAXBDataBinding binding, boolean unwrap2) {
        super(binding.getContext());
        this.unwrapJAXBElement = unwrap2;
        this.databinding = binding;
    }

    @Override
    public Object read(T input) {
        return this.read(null, input);
    }

    @Override
    public void setProperty(String prop, Object value) {
        if (prop.equals("unwrap.jaxb.element")) {
            this.unwrapJAXBElement = Boolean.TRUE.equals(value);
        } else if (prop.equals(Message.class.getName())) {
            Message m4 = (Message)value;
            this.veventHandler = this.getValidationEventHandler(m4, "jaxb-reader-validation-event-handler");
            if (this.veventHandler == null) {
                this.veventHandler = this.databinding.getValidationEventHandler();
            }
            this.setEventHandler = MessageUtils.getContextualBoolean(m4, "set-jaxb-validation-event-handler", true);
            Object unwrapProperty = m4.get("unwrap.jaxb.element");
            if (unwrapProperty == null) {
                unwrapProperty = m4.getExchange().get("unwrap.jaxb.element");
            }
            if (unwrapProperty != null) {
                this.unwrapJAXBElement = Boolean.TRUE.equals(unwrapProperty);
            }
        }
    }

    private Unmarshaller createUnmarshaller() {
        try {
            Unmarshaller um = null;
            um = this.context.createUnmarshaller();
            if (this.databinding.getUnmarshallerListener() != null) {
                um.setListener(this.databinding.getUnmarshallerListener());
            }
            if (this.setEventHandler) {
                um.setEventHandler(new WSUIDValidationHandler(this.veventHandler));
            }
            if (this.databinding.getUnmarshallerProperties() != null) {
                for (Map.Entry<String, Object> entry : this.databinding.getUnmarshallerProperties().entrySet()) {
                    try {
                        um.setProperty(entry.getKey(), entry.getValue());
                    }
                    catch (PropertyException pe) {
                        LOG.log(Level.INFO, "PropertyException setting Marshaller properties", pe);
                    }
                }
            }
            um.setSchema(this.schema);
            um.setAttachmentUnmarshaller(this.getAttachmentUnmarshaller());
            for (XmlAdapter xmlAdapter : this.databinding.getConfiguredXmlAdapters()) {
                um.setAdapter(xmlAdapter);
            }
            return um;
        }
        catch (UnmarshalException ex) {
            throw new Fault(new org.apache.cxf.common.i18n.Message("UNMARSHAL_ERROR", LOG, ex.getLinkedException().getMessage()), (Throwable)ex);
        }
        catch (JAXBException ex) {
            throw new Fault(new org.apache.cxf.common.i18n.Message("UNMARSHAL_ERROR", LOG, ex.getMessage()), (Throwable)ex);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object read(MessagePartInfo part, T reader) {
        boolean honorJaxbAnnotation = this.honorJAXBAnnotations(part);
        Annotation[] anns = null;
        if (honorJaxbAnnotation && (anns = this.getJAXBAnnotation(part)).length > 0) {
            QName qname = new QName(null, part.getConcreteName().getLocalPart());
            Object obj = JAXBEncoderDecoder.unmarshalWithBridge(qname, part.getTypeClass(), anns, this.databinding.getContextClasses(), reader, this.getAttachmentUnmarshaller());
            this.onCompleteUnmarshalling();
            return obj;
        }
        Unmarshaller um = this.createUnmarshaller();
        try {
            Object obj = JAXBEncoderDecoder.unmarshall(um, reader, part, this.unwrapJAXBElement);
            this.onCompleteUnmarshalling();
            Object object = obj;
            return object;
        }
        finally {
            JAXBUtils.closeUnmarshaller(um);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object read(QName name, T input, Class<?> type) {
        Unmarshaller um = this.createUnmarshaller();
        try {
            Object obj = JAXBEncoderDecoder.unmarshall(um, input, name, type, this.unwrapJAXBElement);
            this.onCompleteUnmarshalling();
            Object object = obj;
            return object;
        }
        finally {
            JAXBUtils.closeUnmarshaller(um);
        }
    }

    private void onCompleteUnmarshalling() {
        if (this.setEventHandler && this.veventHandler instanceof UnmarshallerEventHandler) {
            try {
                ((UnmarshallerEventHandler)((Object)this.veventHandler)).onUnmarshalComplete();
            }
            catch (UnmarshalException e) {
                if (e.getLinkedException() != null) {
                    throw new Fault(new org.apache.cxf.common.i18n.Message("UNMARSHAL_ERROR", LOG, e.getLinkedException().getMessage()), (Throwable)e);
                }
                throw new Fault(new org.apache.cxf.common.i18n.Message("UNMARSHAL_ERROR", LOG, e.getMessage()), (Throwable)e);
            }
        }
    }

    private static class WSUIDValidationHandler
    implements ValidationEventHandler {
        ValidationEventHandler origHandler;

        WSUIDValidationHandler(ValidationEventHandler o) {
            this.origHandler = o;
        }

        @Override
        public boolean handleEvent(ValidationEvent event) {
            if (this.origHandler != null && this.origHandler.handleEvent(event)) {
                return true;
            }
            String msg = event.getMessage();
            return msg != null && msg.contains(":Id") && (msg.startsWith("cvc-type.3.1.1") || msg.startsWith("cvc-type.3.2.2") || msg.startsWith("cvc-complex-type.3.1.1") || msg.startsWith("cvc-complex-type.3.2.2"));
        }
    }
}

