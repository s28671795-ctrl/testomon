/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.bind.v2.runtime;

import com.sun.istack.NotNull;
import com.sun.xml.bind.api.TypeReference;
import com.sun.xml.bind.marshaller.SAX2DOMEx;
import com.sun.xml.bind.v2.runtime.DomPostInitAction;
import com.sun.xml.bind.v2.runtime.InternalBridge;
import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
import com.sun.xml.bind.v2.runtime.JaxBeanInfo;
import com.sun.xml.bind.v2.runtime.MarshallerImpl;
import com.sun.xml.bind.v2.runtime.Name;
import com.sun.xml.bind.v2.runtime.StAXPostInitAction;
import com.sun.xml.bind.v2.runtime.XMLSerializer;
import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import com.sun.xml.bind.v2.runtime.output.XMLStreamWriterOutput;
import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallerImpl;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

final class BridgeImpl<T>
extends InternalBridge<T> {
    private final Name tagName;
    private final JaxBeanInfo<T> bi;
    private final TypeReference typeRef;

    public BridgeImpl(JAXBContextImpl context, Name tagName, JaxBeanInfo<T> bi, TypeReference typeRef) {
        super(context);
        this.tagName = tagName;
        this.bi = bi;
        this.typeRef = typeRef;
    }

    @Override
    public void marshal(Marshaller _m, T t2, XMLStreamWriter output) throws JAXBException {
        MarshallerImpl m4 = (MarshallerImpl)_m;
        m4.write(this.tagName, this.bi, t2, XMLStreamWriterOutput.create(output, this.context, m4.getEscapeHandler()), new StAXPostInitAction(output, m4.serializer));
    }

    @Override
    public void marshal(Marshaller _m, T t2, OutputStream output, NamespaceContext nsContext) throws JAXBException {
        MarshallerImpl m4 = (MarshallerImpl)_m;
        StAXPostInitAction pia = null;
        if (nsContext != null) {
            pia = new StAXPostInitAction(nsContext, m4.serializer);
        }
        m4.write(this.tagName, this.bi, t2, m4.createWriter(output), pia);
    }

    @Override
    public void marshal(Marshaller _m, T t2, Node output) throws JAXBException {
        MarshallerImpl m4 = (MarshallerImpl)_m;
        m4.write(this.tagName, this.bi, t2, new SAXOutput(new SAX2DOMEx(output)), new DomPostInitAction(output, m4.serializer));
    }

    @Override
    public void marshal(Marshaller _m, T t2, ContentHandler contentHandler) throws JAXBException {
        MarshallerImpl m4 = (MarshallerImpl)_m;
        m4.write(this.tagName, this.bi, t2, new SAXOutput(contentHandler), null);
    }

    @Override
    public void marshal(Marshaller _m, T t2, Result result) throws JAXBException {
        MarshallerImpl m4 = (MarshallerImpl)_m;
        m4.write(this.tagName, this.bi, t2, m4.createXmlOutput(result), m4.createPostInitAction(result));
    }

    @Override
    @NotNull
    public T unmarshal(Unmarshaller _u, XMLStreamReader in) throws JAXBException {
        UnmarshallerImpl u = (UnmarshallerImpl)_u;
        return ((JAXBElement)u.unmarshal0(in, this.bi)).getValue();
    }

    @Override
    @NotNull
    public T unmarshal(Unmarshaller _u, Source in) throws JAXBException {
        UnmarshallerImpl u = (UnmarshallerImpl)_u;
        return ((JAXBElement)u.unmarshal0(in, this.bi)).getValue();
    }

    @Override
    @NotNull
    public T unmarshal(Unmarshaller _u, InputStream in) throws JAXBException {
        UnmarshallerImpl u = (UnmarshallerImpl)_u;
        return ((JAXBElement)u.unmarshal0(in, this.bi)).getValue();
    }

    @Override
    @NotNull
    public T unmarshal(Unmarshaller _u, Node n) throws JAXBException {
        UnmarshallerImpl u = (UnmarshallerImpl)_u;
        return ((JAXBElement)u.unmarshal0(n, this.bi)).getValue();
    }

    @Override
    public TypeReference getTypeReference() {
        return this.typeRef;
    }

    @Override
    public void marshal(T value, XMLSerializer out) throws IOException, SAXException, XMLStreamException {
        out.startElement(this.tagName, null);
        if (value == null) {
            out.writeXsiNilTrue();
        } else {
            out.childAsXsiType(value, null, this.bi, false);
        }
        out.endElement();
    }
}

