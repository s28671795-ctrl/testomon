/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.databinding.source;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;
import javax.activation.DataSource;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.databinding.DataWriter;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLStreamDataWriter
implements DataWriter<XMLStreamWriter> {
    private static final Logger LOG = LogUtils.getL7dLogger(XMLStreamDataWriter.class);
    private Schema schema;

    @Override
    public void write(Object obj, MessagePartInfo part, XMLStreamWriter output) {
        this.write(obj, output);
    }

    @Override
    public void write(Object obj, XMLStreamWriter writer) {
        try {
            if (obj instanceof DataSource) {
                DataSource ds = (DataSource)obj;
                if (this.schema != null) {
                    DOMSource domSource = new DOMSource(StaxUtils.read(ds.getInputStream()));
                    Validator schemaValidator = this.schema.newValidator();
                    schemaValidator.setErrorHandler(new MtomValidationErrorHandler(schemaValidator.getErrorHandler(), domSource.getNode()));
                    schemaValidator.validate(domSource);
                    StaxUtils.copy((Source)domSource, writer);
                } else {
                    XMLStreamReader reader = StaxUtils.createXMLStreamReader(ds.getInputStream());
                    StaxUtils.copy(reader, writer);
                    reader.close();
                }
            } else if (obj instanceof Node) {
                if (obj instanceof DocumentFragment) {
                    obj = DOMUtils.getDomDocumentFragment((DocumentFragment)obj);
                }
                if (this.schema != null) {
                    Validator schemaValidator = this.schema.newValidator();
                    schemaValidator.setErrorHandler(new MtomValidationErrorHandler(schemaValidator.getErrorHandler(), (Node)obj));
                    schemaValidator.validate(new DOMSource((Node)obj));
                }
                Node nd = (Node)obj;
                this.writeNode(nd, writer);
            } else {
                Source s2 = (Source)obj;
                if (this.schema != null) {
                    if (!(s2 instanceof DOMSource)) {
                        s2 = new DOMSource(StaxUtils.read(s2));
                    }
                    Validator schemaValidator = this.schema.newValidator();
                    schemaValidator.setErrorHandler(new MtomValidationErrorHandler(schemaValidator.getErrorHandler(), ((DOMSource)s2).getNode()));
                    schemaValidator.validate(s2);
                }
                if (s2 instanceof DOMSource && ((DOMSource)s2).getNode() == null) {
                    return;
                }
                StaxUtils.copy(s2, writer);
            }
        }
        catch (XMLStreamException e) {
            throw new Fault("COULD_NOT_WRITE_XML_STREAM_CAUSED_BY", LOG, (Throwable)e, e.getClass().getCanonicalName(), e.getMessage());
        }
        catch (IOException e) {
            throw new Fault(new Message("COULD_NOT_WRITE_XML_STREAM", LOG, new Object[0]), (Throwable)e);
        }
        catch (SAXException e) {
            throw new Fault("COULD_NOT_WRITE_XML_STREAM_CAUSED_BY", LOG, (Throwable)e, e.getClass().getCanonicalName(), e.getMessage());
        }
    }

    private void writeNode(Node nd, XMLStreamWriter writer) throws XMLStreamException {
        if (writer instanceof W3CDOMStreamWriter) {
            W3CDOMStreamWriter dw = (W3CDOMStreamWriter)writer;
            if (dw.getCurrentNode() != null) {
                if (nd instanceof DocumentFragment && nd.getOwnerDocument() == dw.getCurrentNode().getOwnerDocument()) {
                    Node ch = nd.getFirstChild();
                    while (ch != null) {
                        nd.removeChild(ch);
                        dw.getCurrentNode().appendChild(DOMUtils.getDomElement(ch));
                        ch = nd.getFirstChild();
                    }
                } else {
                    if (nd.getOwnerDocument() == dw.getCurrentNode().getOwnerDocument()) {
                        dw.getCurrentNode().appendChild(nd);
                        return;
                    }
                    if (nd instanceof DocumentFragment) {
                        nd = dw.getDocument().importNode(nd, true);
                        dw.getCurrentNode().appendChild(nd);
                        return;
                    }
                }
            } else if (dw.getCurrentFragment() != null) {
                if (nd.getOwnerDocument() == dw.getCurrentFragment().getOwnerDocument()) {
                    dw.getCurrentFragment().appendChild(nd);
                    return;
                }
                if (nd instanceof DocumentFragment) {
                    nd = dw.getDocument().importNode(nd, true);
                    dw.getCurrentFragment().appendChild(nd);
                    return;
                }
            }
        }
        if (nd instanceof Document) {
            StaxUtils.writeDocument((Document)nd, writer, false, true);
        } else {
            StaxUtils.writeNode(nd, writer, true);
        }
    }

    @Override
    public void setSchema(Schema s2) {
        this.schema = s2;
    }

    @Override
    public void setAttachments(Collection<Attachment> attachments) {
    }

    @Override
    public void setProperty(String key, Object value) {
    }

    private static class MtomValidationErrorHandler
    implements ErrorHandler {
        private ErrorHandler origErrorHandler;
        private Node node;

        MtomValidationErrorHandler(ErrorHandler origErrorHandler, Node node) {
            this.origErrorHandler = origErrorHandler;
            this.node = node;
        }

        @Override
        public void warning(SAXParseException exception) throws SAXException {
            if (this.origErrorHandler != null) {
                this.origErrorHandler.warning(exception);
            }
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            if (this.isCVC312Exception(exception)) {
                String elementName = this.getAttachmentElementName(exception);
                if (this.node != null && this.findIncludeNode(this.node, elementName)) {
                    return;
                }
            }
            if (this.origErrorHandler == null) {
                throw exception;
            }
            this.origErrorHandler.error(exception);
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            if (this.origErrorHandler == null) {
                throw exception;
            }
            this.origErrorHandler.fatalError(exception);
        }

        private boolean isCVC312Exception(SAXParseException exception) {
            String msg = exception.getMessage();
            return (msg.startsWith("cvc-type.3.1.2") || msg.startsWith("cvc-complex-type.2.2")) && msg.endsWith("is a simple type, so it must have no element information item [children].");
        }

        private String getAttachmentElementName(SAXParseException exception) {
            String msg = exception.getMessage();
            String[] str = msg.split("'");
            return str[1];
        }

        private boolean findIncludeNode(Node checkNode, String mtomElement) {
            boolean ret = false;
            NodeList nList = checkNode.getChildNodes();
            for (int i = 0; i < nList.getLength(); ++i) {
                Node nNode = nList.item(i);
                if (nNode.getLocalName() != null && nNode.getLocalName().equals(mtomElement)) {
                    NodeList subNodeList = nNode.getChildNodes();
                    for (int j = 0; j < subNodeList.getLength(); ++j) {
                        Node subNode = subNodeList.item(j);
                        if (!"http://www.w3.org/2004/08/xop/include".equals(subNode.getNamespaceURI()) || !"Include".equals(subNode.getLocalName())) continue;
                        return true;
                    }
                    continue;
                }
                ret = this.findIncludeNode(nNode, mtomElement);
            }
            return ret;
        }
    }
}

