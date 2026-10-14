/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxb.io;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.attachment.AttachmentMarshaller;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.databinding.DataWriter;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxb.JAXBDataBase;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.jaxb.JAXBEncoderDecoder;
import org.apache.cxf.jaxb.MarshallerEventHandler;
import org.apache.cxf.jaxb.attachment.JAXBAttachmentMarshaller;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.ws.commons.schema.XmlSchemaElement;

public class DataWriterImpl<T>
extends JAXBDataBase
implements DataWriter<T> {
    private static final Logger LOG = LogUtils.getLogger(JAXBDataBinding.class);
    ValidationEventHandler veventHandler;
    boolean setEventHandler = true;
    boolean noEscape;
    private JAXBDataBinding databinding;

    public DataWriterImpl(JAXBDataBinding binding) {
        this(binding, false);
    }

    public DataWriterImpl(JAXBDataBinding binding, boolean noEsc) {
        super(binding.getContext());
        this.databinding = binding;
        this.noEscape = noEsc;
    }

    @Override
    public void write(Object obj, T output) {
        this.write(obj, null, output);
    }

    @Override
    public void setProperty(String prop, Object value) {
        if (prop.equals(Message.class.getName())) {
            Message m4 = (Message)value;
            this.veventHandler = this.getValidationEventHandler(m4, "jaxb-writer-validation-event-handler");
            if (this.veventHandler == null) {
                this.veventHandler = this.databinding.getValidationEventHandler();
            }
            this.setEventHandler = MessageUtils.getContextualBoolean(m4, "set-jaxb-validation-event-handler", true);
        }
    }

    public Marshaller createMarshaller(Object elValue, MessagePartInfo part) {
        Marshaller marshaller;
        Class<?> cls = null;
        if (part != null) {
            cls = part.getTypeClass();
        }
        if (cls == null) {
            Class<?> clazz = cls = null != elValue ? elValue.getClass() : null;
        }
        if (cls != null && cls.isArray() && elValue instanceof Collection) {
            Collection col = (Collection)elValue;
            elValue = col.toArray((Object[])Array.newInstance(cls.getComponentType(), col.size()));
        }
        try {
            marshaller = this.context.createMarshaller();
            marshaller.setProperty("jaxb.encoding", StandardCharsets.UTF_8.name());
            marshaller.setProperty("jaxb.fragment", true);
            marshaller.setProperty("jaxb.formatted.output", Boolean.FALSE);
            marshaller.setListener(this.databinding.getMarshallerListener());
            this.databinding.applyEscapeHandler(!this.noEscape, eh -> JAXBUtils.setEscapeHandler(marshaller, eh));
            if (this.setEventHandler) {
                ValidationEventHandler h2 = this.veventHandler;
                if (this.veventHandler == null) {
                    h2 = new ValidationEventHandler(){

                        @Override
                        public boolean handleEvent(ValidationEvent event) {
                            return event.getSeverity() == 0;
                        }
                    };
                }
                marshaller.setEventHandler(h2);
            }
            Map<String, String> nspref = this.databinding.getDeclaredNamespaceMappings();
            Map<String, String> nsctxt = this.databinding.getContextualNamespaceMap();
            if (nspref != null || nsctxt != null) {
                Iterator<Map.Entry<String, Object>> mapper2 = JAXBUtils.setNamespaceMapper(nspref != null ? nspref : nsctxt, marshaller);
                if (nsctxt != null) {
                    DataWriterImpl.setContextualNamespaceDecls(mapper2, nsctxt);
                }
            }
            if (this.databinding.getMarshallerProperties() != null) {
                for (Map.Entry<String, Object> propEntry : this.databinding.getMarshallerProperties().entrySet()) {
                    try {
                        marshaller.setProperty(propEntry.getKey(), propEntry.getValue());
                    }
                    catch (PropertyException pe) {
                        LOG.log(Level.INFO, "PropertyException setting Marshaller properties", pe);
                    }
                }
            }
            marshaller.setSchema(this.schema);
            AttachmentMarshaller atmarsh = this.getAttachmentMarshaller();
            marshaller.setAttachmentMarshaller(atmarsh);
            if (this.schema != null && atmarsh instanceof JAXBAttachmentMarshaller) {
                marshaller.setEventHandler(new MtomValidationHandler(marshaller.getEventHandler(), (JAXBAttachmentMarshaller)atmarsh));
            }
        }
        catch (MarshalException ex) {
            org.apache.cxf.common.i18n.Message faultMessage = new org.apache.cxf.common.i18n.Message("MARSHAL_ERROR", LOG, ex.getLinkedException().getMessage());
            throw new Fault(faultMessage, (Throwable)ex);
        }
        catch (JAXBException ex) {
            throw new Fault(new org.apache.cxf.common.i18n.Message("MARSHAL_ERROR", LOG, ex.getMessage()), (Throwable)ex);
        }
        for (XmlAdapter<?, ?> adapter : this.databinding.getConfiguredXmlAdapters()) {
            marshaller.setAdapter(adapter);
        }
        return marshaller;
    }

    private static void setContextualNamespaceDecls(Object mapper2, Map<String, String> nsctxt) {
        try {
            Method m4 = ReflectionUtil.getDeclaredMethod(mapper2.getClass(), "setContextualNamespaceDecls", String[].class);
            String[] args2 = new String[nsctxt.size() * 2];
            int ai = 0;
            for (Map.Entry<String, String> nsp : nsctxt.entrySet()) {
                args2[ai++] = nsp.getValue();
                args2[ai++] = nsp.getKey();
            }
            m4.invoke(mapper2, new Object[]{args2});
        }
        catch (Exception e) {
            LOG.log(Level.WARNING, "Failed to set the contextual namespace map", e);
        }
    }

    @Override
    public void write(Object obj, MessagePartInfo part, T output) {
        boolean honorJaxbAnnotation = this.honorJAXBAnnotations(part);
        if (part != null && !part.isElement() && part.getTypeClass() != null) {
            honorJaxbAnnotation = true;
        }
        this.checkPart(part, obj);
        if (obj != null || !(part.getXmlSchema() instanceof XmlSchemaElement)) {
            if (obj instanceof Exception && part != null && Boolean.TRUE.equals(part.getProperty(JAXBDataBinding.class.getName() + ".CUSTOM_EXCEPTION"))) {
                JAXBEncoderDecoder.marshallException(this.createMarshaller(obj, part), (Exception)obj, part, output);
                this.onCompleteMarshalling();
            } else {
                Annotation[] anns = this.getJAXBAnnotation(part);
                if (!honorJaxbAnnotation || anns.length == 0) {
                    JAXBEncoderDecoder.marshall(this.createMarshaller(obj, part), obj, part, output);
                    this.onCompleteMarshalling();
                } else if (honorJaxbAnnotation && anns.length > 0) {
                    JAXBEncoderDecoder.marshalWithBridge(part.getConcreteName(), part.getTypeClass(), anns, this.databinding.getContextClasses(), obj, output, this.getAttachmentMarshaller());
                }
            }
        } else if (this.needToRender(part)) {
            JAXBEncoderDecoder.marshallNullElement(this.createMarshaller(null, part), output, part);
            this.onCompleteMarshalling();
        }
    }

    private void checkPart(MessagePartInfo part, Object object) {
        if (part == null || part.getTypeClass() == null || object == null) {
            return;
        }
        Class<Object> typeClass = part.getTypeClass();
        if (typeClass == null) {
            return;
        }
        if (typeClass.isPrimitive()) {
            if (typeClass == Long.TYPE) {
                typeClass = Long.class;
            } else if (typeClass == Integer.TYPE) {
                typeClass = Integer.class;
            } else if (typeClass == Short.TYPE) {
                typeClass = Short.class;
            } else if (typeClass == Byte.TYPE) {
                typeClass = Byte.class;
            } else if (typeClass == Character.TYPE) {
                typeClass = Character.class;
            } else if (typeClass == Double.TYPE) {
                typeClass = Double.class;
            } else if (typeClass == Float.TYPE) {
                typeClass = Float.class;
            } else if (typeClass == Boolean.TYPE) {
                typeClass = Boolean.class;
            }
        } else if (typeClass.isArray() && object instanceof Collection) {
            return;
        }
        if (!typeClass.isInstance(object)) {
            throw new IllegalArgumentException("Part " + part.getName() + " should be of type " + typeClass.getName() + ", not " + object.getClass().getName());
        }
    }

    private boolean needToRender(MessagePartInfo part) {
        if (part != null && part.getXmlSchema() instanceof XmlSchemaElement) {
            XmlSchemaElement element = (XmlSchemaElement)part.getXmlSchema();
            return element.isNillable() && element.getMinOccurs() > 0L;
        }
        return false;
    }

    private void onCompleteMarshalling() {
        if (this.setEventHandler && this.veventHandler instanceof MarshallerEventHandler) {
            try {
                ((MarshallerEventHandler)((Object)this.veventHandler)).onMarshalComplete();
            }
            catch (MarshalException e) {
                if (e.getLinkedException() != null) {
                    throw new Fault(new org.apache.cxf.common.i18n.Message("MARSHAL_ERROR", LOG, e.getLinkedException().getMessage()), (Throwable)e);
                }
                throw new Fault(new org.apache.cxf.common.i18n.Message("MARSHAL_ERROR", LOG, e.getMessage()), (Throwable)e);
            }
        }
    }

    private static class MtomValidationHandler
    implements ValidationEventHandler {
        ValidationEventHandler origHandler;
        JAXBAttachmentMarshaller marshaller;

        MtomValidationHandler(ValidationEventHandler v, JAXBAttachmentMarshaller m4) {
            this.origHandler = v;
            this.marshaller = m4;
        }

        @Override
        public boolean handleEvent(ValidationEvent event) {
            String msg = event.getMessage();
            if ((msg.startsWith("cvc-type.3.1.2") || msg.startsWith("cvc-complex-type.2.2")) && msg.contains(this.marshaller.getLastMTOMElementName().getLocalPart())) {
                return true;
            }
            if (this.origHandler != null) {
                return this.origHandler.handleEvent(event);
            }
            return false;
        }
    }
}

