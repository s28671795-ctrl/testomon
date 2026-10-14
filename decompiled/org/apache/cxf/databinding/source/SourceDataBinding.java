/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.databinding.source;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.common.xmlschema.SchemaCollection;
import org.apache.cxf.databinding.AbstractDataBinding;
import org.apache.cxf.databinding.DataReader;
import org.apache.cxf.databinding.DataWriter;
import org.apache.cxf.databinding.source.NodeDataReader;
import org.apache.cxf.databinding.source.NodeDataWriter;
import org.apache.cxf.databinding.source.XMLStreamDataReader;
import org.apache.cxf.databinding.source.XMLStreamDataWriter;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.ServiceModelVisitor;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.ws.commons.schema.constants.Constants;
import org.w3c.dom.Node;

public class SourceDataBinding
extends AbstractDataBinding {
    public static final String PREFERRED_FORMAT = "source-preferred-format";
    final Class<?> preferred;

    public SourceDataBinding() {
        this.preferred = null;
    }

    public SourceDataBinding(Class<?> pref) {
        this.preferred = pref;
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
            return new XMLStreamDataReader(this.preferred);
        }
        if (cls == Node.class) {
            return new NodeDataReader();
        }
        throw new UnsupportedOperationException("The type " + cls.getName() + " is not supported.");
    }

    @Override
    public Class<?>[] getSupportedReaderFormats() {
        return new Class[]{XMLStreamReader.class, Node.class};
    }

    @Override
    public <T> DataWriter<T> createWriter(Class<T> cls) {
        if (cls == XMLStreamWriter.class) {
            return new XMLStreamDataWriter();
        }
        if (cls == Node.class) {
            return new NodeDataWriter();
        }
        throw new UnsupportedOperationException("The type " + cls.getName() + " is not supported.");
    }

    @Override
    public Class<?>[] getSupportedWriterFormats() {
        return new Class[]{XMLStreamWriter.class, Node.class};
    }
}

