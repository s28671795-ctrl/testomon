/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.databinding.stax;

import java.util.Collection;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.validation.Schema;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.xmlschema.SchemaCollection;
import org.apache.cxf.databinding.AbstractInterceptorProvidingDataBinding;
import org.apache.cxf.databinding.DataReader;
import org.apache.cxf.databinding.DataWriter;
import org.apache.cxf.databinding.stax.XMLStreamWriterCallback;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.StaxInEndingInterceptor;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.ServiceModelVisitor;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.ws.commons.schema.constants.Constants;
import org.w3c.dom.Node;

public class StaxDataBinding
extends AbstractInterceptorProvidingDataBinding {
    private XMLStreamDataReader xsrReader = new XMLStreamDataReader();
    private XMLStreamDataWriter xswWriter = new XMLStreamDataWriter();

    public StaxDataBinding() {
        this.inInterceptors.add(new StaxInEndingInterceptor("post-invoke"));
        this.inFaultInterceptors.add(new StaxInEndingInterceptor("post-invoke"));
        this.inInterceptors.add(RemoveStaxInEndingInterceptor.INSTANCE);
        this.inFaultInterceptors.add(RemoveStaxInEndingInterceptor.INSTANCE);
    }

    @Override
    public void initialize(Service service2) {
        for (ServiceInfo serviceInfo : service2.getServiceInfos()) {
            SchemaCollection schemaCollection = serviceInfo.getXmlSchemaCollection();
            if (schemaCollection.getXmlSchemas().length > 1) continue;
            new ServiceModelVisitor(serviceInfo){

                @Override
                public void begin(MessagePartInfo part) {
                    if (part.getTypeQName() != null || part.getElementQName() != null) {
                        return;
                    }
                    part.setTypeQName(Constants.XSD_ANYTYPE);
                }
            }.walk();
        }
    }

    @Override
    public <T> DataReader<T> createReader(Class<T> cls) {
        if (cls == XMLStreamReader.class) {
            return this.xsrReader;
        }
        throw new UnsupportedOperationException("The type " + cls.getName() + " is not supported.");
    }

    @Override
    public Class<?>[] getSupportedReaderFormats() {
        return new Class[]{XMLStreamReader.class};
    }

    @Override
    public <T> DataWriter<T> createWriter(Class<T> cls) {
        if (cls == XMLStreamWriter.class) {
            return this.xswWriter;
        }
        throw new UnsupportedOperationException("The type " + cls.getName() + " is not supported.");
    }

    @Override
    public Class<?>[] getSupportedWriterFormats() {
        return new Class[]{XMLStreamWriter.class, Node.class};
    }

    public static class XMLStreamDataWriter
    implements DataWriter<XMLStreamWriter> {
        private static final Logger LOG = LogUtils.getL7dLogger(XMLStreamDataWriter.class);

        @Override
        public void write(Object obj, MessagePartInfo part, XMLStreamWriter output) {
            this.write(obj, output);
        }

        @Override
        public void write(Object obj, XMLStreamWriter writer) {
            block4: {
                try {
                    if (obj instanceof XMLStreamReader) {
                        XMLStreamReader xmlStreamReader = (XMLStreamReader)obj;
                        StaxUtils.copy(xmlStreamReader, writer);
                        xmlStreamReader.close();
                        break block4;
                    }
                    if (obj instanceof XMLStreamWriterCallback) {
                        ((XMLStreamWriterCallback)obj).write(writer);
                        break block4;
                    }
                    throw new UnsupportedOperationException("Data types of " + obj.getClass() + " are not supported.");
                }
                catch (XMLStreamException e) {
                    throw new Fault("COULD_NOT_READ_XML_STREAM", LOG, (Throwable)e);
                }
            }
        }

        @Override
        public void setSchema(Schema s2) {
        }

        @Override
        public void setAttachments(Collection<Attachment> attachments) {
        }

        @Override
        public void setProperty(String key, Object value) {
        }
    }

    public static class XMLStreamDataReader
    implements DataReader<XMLStreamReader> {
        @Override
        public Object read(MessagePartInfo part, XMLStreamReader input) {
            return this.read((QName)null, input, part.getTypeClass());
        }

        @Override
        public Object read(QName name, XMLStreamReader input, Class<?> type) {
            return input;
        }

        @Override
        public Object read(XMLStreamReader reader) {
            return reader;
        }

        @Override
        public void setSchema(Schema s2) {
        }

        @Override
        public void setAttachments(Collection<Attachment> attachments) {
        }

        @Override
        public void setProperty(String prop, Object value) {
        }
    }

    static class RemoveStaxInEndingInterceptor
    extends AbstractPhaseInterceptor<Message> {
        static final RemoveStaxInEndingInterceptor INSTANCE = new RemoveStaxInEndingInterceptor();

        RemoveStaxInEndingInterceptor() {
            super("pre-invoke");
            this.addBefore(StaxInEndingInterceptor.class.getName());
        }

        @Override
        public void handleMessage(Message message) {
            message.getInterceptorChain().remove(StaxInEndingInterceptor.INSTANCE);
        }
    }
}

