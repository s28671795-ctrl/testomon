/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.soap;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.Names;
import org.apache.cxf.ws.addressing.RelatesToType;
import org.apache.cxf.ws.addressing.VersionTransformer;
import org.apache.cxf.ws.addressing.soap.MAPCodec;
import org.apache.cxf.ws.addressing.v200408.AttributedURI;
import org.apache.cxf.ws.addressing.v200408.Relationship;
import org.w3c.dom.Element;

public class VersionTransformer
extends org.apache.cxf.ws.addressing.VersionTransformer {
    public static final Set<QName> HEADERS;
    private static final Logger LOG;
    protected MAPCodec codec;

    public VersionTransformer(MAPCodec mapCodec) {
        this.codec = mapCodec;
    }

    public <T> void encodeAsExposed(SoapMessage message, String exposeAs, T value, String localName, Class<T> clz, JAXBContext marshaller, boolean mustUnderstand) throws JAXBException {
        if (value != null) {
            if ("http://www.w3.org/2005/08/addressing".equals(exposeAs)) {
                this.codec.encodeMAP(message, value, new QName(exposeAs, localName), clz, marshaller, mustUnderstand);
            } else if ("http://schemas.xmlsoap.org/ws/2004/08/addressing".equals(exposeAs)) {
                if (AttributedURIType.class.equals(clz)) {
                    this.codec.encodeMAP(message, VersionTransformer.convert((AttributedURIType)value), new QName(exposeAs, localName), AttributedURI.class, marshaller, mustUnderstand);
                } else if (EndpointReferenceType.class.equals(clz)) {
                    this.codec.encodeMAP(message, VersionTransformer.convert((EndpointReferenceType)value), new QName(exposeAs, localName), VersionTransformer.Names200408.EPR_TYPE, marshaller, mustUnderstand);
                } else if (RelatesToType.class.equals(clz)) {
                    this.codec.encodeMAP(message, VersionTransformer.convert((RelatesToType)value), new QName(exposeAs, localName), Relationship.class, marshaller, mustUnderstand);
                }
            } else if ("http://schemas.xmlsoap.org/ws/2004/03/addressing".equals(exposeAs)) {
                if (AttributedURIType.class.equals(clz)) {
                    this.codec.encodeMAP(message, VersionTransformer.convertTo200403((AttributedURIType)value), new QName(exposeAs, localName), org.apache.cxf.ws.addressing.v200403.AttributedURI.class, marshaller, mustUnderstand);
                } else if (EndpointReferenceType.class.equals(clz)) {
                    this.codec.encodeMAP(message, VersionTransformer.convertTo200403((EndpointReferenceType)value), new QName(exposeAs, localName), VersionTransformer.Names200403.EPR_TYPE, marshaller, mustUnderstand);
                } else if (RelatesToType.class.equals(clz)) {
                    this.codec.encodeMAP(message, VersionTransformer.convertTo200403((RelatesToType)value), new QName(exposeAs, localName), org.apache.cxf.ws.addressing.v200403.Relationship.class, marshaller, mustUnderstand);
                }
            }
        }
    }

    public <T> T decodeAsNative(String encodedAs, Class<T> clz, Element headerElement, Unmarshaller unmarshaller) throws JAXBException {
        T ret = null;
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("decodeAsNative: encodedAs: " + encodedAs);
            LOG.fine("                class: " + clz.getName());
        }
        if ("http://www.w3.org/2005/08/addressing".equals(encodedAs)) {
            ret = this.codec.decodeMAP(clz, headerElement, unmarshaller);
        } else if ("http://schemas.xmlsoap.org/ws/2004/08/addressing".equals(encodedAs)) {
            if (AttributedURIType.class.equals(clz)) {
                ret = clz.cast(VersionTransformer.convert(this.codec.decodeMAP(AttributedURI.class, headerElement, unmarshaller)));
            } else if (EndpointReferenceType.class.equals(clz)) {
                ret = clz.cast(VersionTransformer.convert(this.codec.decodeMAP(VersionTransformer.Names200408.EPR_TYPE, headerElement, unmarshaller)));
            } else if (RelatesToType.class.equals(clz)) {
                ret = clz.cast(VersionTransformer.convert(this.codec.decodeMAP(Relationship.class, headerElement, unmarshaller)));
            }
        } else if ("http://schemas.xmlsoap.org/ws/2004/03/addressing".equals(encodedAs)) {
            if (AttributedURIType.class.equals(clz)) {
                ret = clz.cast(VersionTransformer.convert(this.codec.decodeMAP(org.apache.cxf.ws.addressing.v200403.AttributedURI.class, headerElement, unmarshaller)));
            } else if (EndpointReferenceType.class.equals(clz)) {
                ret = clz.cast(VersionTransformer.convert(this.codec.decodeMAP(VersionTransformer.Names200403.EPR_TYPE, headerElement, unmarshaller)));
            } else if (RelatesToType.class.equals(clz)) {
                ret = clz.cast(VersionTransformer.convert(this.codec.decodeMAP(org.apache.cxf.ws.addressing.v200403.Relationship.class, headerElement, unmarshaller)));
            }
        }
        return ret;
    }

    static {
        LOG = LogUtils.getL7dLogger(VersionTransformer.class);
        HashSet<QName> headers = new HashSet<QName>();
        headers.addAll(Names.HEADERS);
        headers.add(new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "From"));
        headers.add(new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "To"));
        headers.add(new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "ReplyTo"));
        headers.add(new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "FaultTo"));
        headers.add(new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "Action"));
        headers.add(new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "MessageID"));
        headers.add(new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "RelatesTo"));
        headers.add(new QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "From"));
        headers.add(new QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "To"));
        headers.add(new QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "ReplyTo"));
        headers.add(new QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "FaultTo"));
        headers.add(new QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "Action"));
        headers.add(new QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "MessageID"));
        headers.add(new QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "RelatesTo"));
        HEADERS = Collections.unmodifiableSet(headers);
    }
}

