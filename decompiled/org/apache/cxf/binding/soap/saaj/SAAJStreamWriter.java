/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.saaj;

import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPPart;
import javax.xml.stream.XMLStreamException;
import org.apache.cxf.binding.soap.saaj.SAAJUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.staxutils.OverlayW3CDOMStreamWriter;
import org.apache.cxf.staxutils.W3CNamespaceContext;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class SAAJStreamWriter
extends OverlayW3CDOMStreamWriter {
    private final SOAPPart part;
    private final SOAPEnvelope envelope;
    private String uri;

    public SAAJStreamWriter(SOAPPart part) {
        super(part);
        this.part = part;
        Node nd = part.getFirstChild();
        if (nd == null) {
            this.isOverlaid = false;
        }
        this.envelope = null;
    }

    public SAAJStreamWriter(SOAPPart part, Element current) {
        super((Document)part, current);
        this.part = part;
        this.envelope = null;
    }

    public SAAJStreamWriter(SOAPEnvelope env, DocumentFragment frag) {
        super(env.getOwnerDocument(), frag);
        this.part = null;
        this.envelope = env;
        this.isOverlaid = false;
    }

    public SAAJStreamWriter(SOAPEnvelope env, Element cur) {
        super(env.getOwnerDocument(), cur);
        this.part = null;
        this.envelope = env;
        this.isOverlaid = false;
    }

    @Override
    public String getPrefix(String nsuri) throws XMLStreamException {
        if (this.isOverlaid && this.part != null && this.getCurrentNode() == null) {
            Node nd = this.part.getFirstChild();
            while (nd != null) {
                if (nd instanceof Element) {
                    Iterator<String> it = new W3CNamespaceContext((Element)nd).getPrefixes(nsuri);
                    if (it.hasNext()) {
                        return it.next();
                    }
                    nd = null;
                    continue;
                }
                nd = nd.getNextSibling();
            }
        }
        return super.getPrefix(nsuri);
    }

    private String getEnvelopeURI() throws SOAPException {
        if (this.uri == null) {
            this.uri = this.getEnvelope().getElementName().getURI();
        }
        return this.uri;
    }

    private SOAPEnvelope getEnvelope() throws SOAPException {
        if (this.envelope == null) {
            return this.part.getEnvelope();
        }
        return this.envelope;
    }

    @Override
    protected void adjustOverlaidNode(Node nd2, String pfx) {
        String namespace = nd2.getNamespaceURI();
        try {
            if (namespace != null && namespace.equals(this.getEnvelopeURI())) {
                SAAJUtils.adjustPrefix((Element)nd2, pfx);
                if ("Envelope".equals(nd2.getLocalName())) {
                    SAAJUtils.adjustPrefix(this.getEnvelope().getHeader(), pfx);
                }
            }
        }
        catch (SOAPException sOAPException) {
            // empty catch block
        }
        super.adjustOverlaidNode(nd2, pfx);
    }

    @Override
    protected void createAndAddElement(String prefix, String local, String namespace) {
        if (this.part == null) {
            super.createAndAddElement(prefix, local, namespace);
            return;
        }
        try {
            if (namespace != null && namespace.equals(this.getEnvelopeURI())) {
                if ("Envelope".equals(local)) {
                    this.setChild(SAAJUtils.adjustPrefix(this.getEnvelope(), prefix), false);
                    SAAJUtils.adjustPrefix(this.getEnvelope().getHeader(), prefix);
                    SAAJUtils.adjustPrefix(this.getEnvelope().getBody(), prefix);
                    if (this.getEnvelope().getHeader() != null) {
                        this.getEnvelope().removeChild(this.getEnvelope().getHeader());
                    }
                    if (this.getEnvelope().getBody() != null) {
                        this.getEnvelope().removeChild(this.getEnvelope().getBody());
                    }
                    return;
                }
                if ("Body".equals(local)) {
                    if (this.getEnvelope().getBody() == null) {
                        this.getEnvelope().addBody();
                    }
                    this.setChild(SAAJUtils.adjustPrefix(this.getEnvelope().getBody(), prefix), false);
                    return;
                }
                if ("Header".equals(local)) {
                    if (this.getEnvelope().getHeader() == null) {
                        this.getEnvelope().addHeader();
                    }
                    this.setChild(SAAJUtils.adjustPrefix(this.getEnvelope().getHeader(), prefix), false);
                    return;
                }
                if ("Fault".equals(local)) {
                    SOAPFault f = this.getEnvelope().getBody().getFault();
                    if (f == null) {
                        Element el = this.getDocument().createElementNS(namespace, StringUtils.isEmpty(prefix) ? local : prefix + ":" + local);
                        this.getEnvelope().getBody().appendChild(el);
                        f = this.getEnvelope().getBody().getFault();
                        if (f == null) {
                            f = this.getEnvelope().getBody().addFault();
                        }
                    }
                    this.setChild(SAAJUtils.adjustPrefix(f, prefix), false);
                    return;
                }
            } else if (this.getCurrentNode() instanceof SOAPFault) {
                SOAPFault f = (SOAPFault)this.getCurrentNode();
                for (Node nd = f.getFirstChild(); nd != null; nd = nd.getNextSibling()) {
                    if (!(nd instanceof Element)) continue;
                    Element el = (Element)nd;
                    if (!local.equals(nd.getLocalName())) continue;
                    this.setChild(el, false);
                    nd = el.getFirstChild();
                    while (nd != null) {
                        el.removeChild(nd);
                        nd = el.getFirstChild();
                    }
                    return;
                }
            }
        }
        catch (SOAPException sOAPException) {
            // empty catch block
        }
        super.createAndAddElement(prefix, local, namespace);
    }

    @Override
    protected Element createElementNS(String ns, String pfx, String local) {
        Element cur = this.getCurrentNode();
        if (cur instanceof SOAPBody) {
            try {
                if (StringUtils.isEmpty(pfx) && StringUtils.isEmpty(ns)) {
                    SOAPBodyElement el = ((SOAPBody)cur).addBodyElement(new QName(local));
                    cur.removeChild(el);
                    return el;
                }
                SOAPBodyElement el = ((SOAPBody)cur).addBodyElement(new QName(ns, local, pfx == null ? "" : pfx));
                cur.removeChild(el);
                return el;
            }
            catch (SOAPException el) {}
        } else if (cur instanceof SOAPHeader) {
            try {
                SOAPHeaderElement el = ((SOAPHeader)cur).addHeaderElement(new QName(ns, local, pfx == null ? "" : pfx));
                cur.removeChild(el);
                return el;
            }
            catch (SOAPException el) {}
        } else if (cur instanceof SOAPElement) {
            try {
                SOAPElement el = null;
                if (StringUtils.isEmpty(pfx) && StringUtils.isEmpty(ns)) {
                    el = ((SOAPElement)cur).addChildElement(local, "", "");
                } else {
                    el = ((SOAPElement)cur).addChildElement(local, pfx == null ? "" : pfx, ns);
                    SAAJUtils.adjustPrefix(el, pfx);
                }
                cur.removeChild(el);
                return el;
            }
            catch (SOAPException sOAPException) {
                // empty catch block
            }
        }
        return super.createElementNS(ns, pfx, local);
    }
}

