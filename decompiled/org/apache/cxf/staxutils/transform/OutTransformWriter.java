/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.staxutils.DelegatingXMLStreamWriter;
import org.apache.cxf.staxutils.transform.DelegatingNamespaceContext;
import org.apache.cxf.staxutils.transform.ElementProperty;
import org.apache.cxf.staxutils.transform.ParsingEvent;
import org.apache.cxf.staxutils.transform.QNamesMap;
import org.apache.cxf.staxutils.transform.TransformUtils;

public class OutTransformWriter
extends DelegatingXMLStreamWriter {
    private String defaultNamespace;
    private QNamesMap elementsMap;
    private QNamesMap attributesMap;
    private Map<QName, ElementProperty> appendMap = new HashMap<QName, ElementProperty>(5);
    private Map<String, String> nsMap = new HashMap<String, String>(5);
    private List<Set<String>> writtenUris = new LinkedList<Set<String>>();
    private Set<QName> dropElements;
    private List<List<ParsingEvent>> pushedAheadEvents = new LinkedList<List<ParsingEvent>>();
    private List<QName> elementsStack = new LinkedList<QName>();
    private String replaceNamespace;
    private String replaceText;
    private int currentDepth;
    private int dropDepth;
    private boolean attributesToElements;
    private DelegatingNamespaceContext namespaceContext;

    public OutTransformWriter(XMLStreamWriter writer, Map<String, String> outMap) {
        this(writer, outMap, null, null, null, false, null);
    }

    public OutTransformWriter(XMLStreamWriter writer, Map<String, String> outMap, Map<String, String> append, List<String> dropEls, boolean attributesToElements, String defaultNamespace) {
        this(writer, outMap, append, dropEls, null, attributesToElements, defaultNamespace);
    }

    public OutTransformWriter(XMLStreamWriter writer, Map<String, String> outEMap, Map<String, String> append, List<String> dropEls, Map<String, String> outAMap, boolean attributesToElements, String defaultNamespace) {
        super(writer);
        this.elementsMap = new QNamesMap(outEMap == null ? 0 : outEMap.size());
        this.attributesMap = new QNamesMap(outAMap == null ? 0 : outAMap.size());
        TransformUtils.convertToQNamesMap(outEMap, this.elementsMap, this.nsMap);
        TransformUtils.convertToQNamesMap(outAMap, this.attributesMap, null);
        TransformUtils.convertToMapOfElementProperties(append, this.appendMap);
        this.dropElements = DOMUtils.convertStringsToQNames(dropEls);
        this.attributesToElements = attributesToElements;
        this.namespaceContext = new DelegatingNamespaceContext(writer.getNamespaceContext(), this.nsMap);
        this.defaultNamespace = defaultNamespace;
    }

    @Override
    public void writeNamespace(String prefix, String uri) throws XMLStreamException {
        if (StringUtils.isEmpty(prefix) || "xmlns".equals(prefix)) {
            this.writeDefaultNamespace(uri);
            return;
        }
        if (this.matchesDropped(true)) {
            return;
        }
        String value = this.nsMap.get(uri);
        if (value != null && value.isEmpty() || uri.equals(this.replaceNamespace)) {
            return;
        }
        String string = uri = value != null ? value : uri;
        if (this.writtenUris.get(0).contains(uri) && (prefix.isEmpty() || prefix.equals(this.namespaceContext.getPrefix(uri)))) {
            return;
        }
        if (this.defaultNamespace != null && this.defaultNamespace.equals(uri)) {
            super.writeDefaultNamespace(uri);
            this.namespaceContext.addPrefix("", uri);
        } else {
            if (prefix.isEmpty()) {
                prefix = this.namespaceContext.findUniquePrefix(uri);
            }
            super.writeNamespace(prefix, uri);
            this.namespaceContext.addPrefix(prefix, uri);
        }
        this.writtenUris.get(0).add(uri);
    }

    @Override
    public void writeDefaultNamespace(String uri) throws XMLStreamException {
        if (this.matchesDropped(true)) {
            return;
        }
        String value = this.nsMap.get(uri);
        if (value != null && value.isEmpty() || this.isDefaultNamespaceRedefined() && !this.isDefaultNamespaceRedefined(uri) || uri.equals(this.replaceNamespace)) {
            return;
        }
        String string = uri = value != null ? value : uri;
        if (this.writtenUris.get(0).contains(uri) && this.namespaceContext.getPrefix(uri).isEmpty()) {
            return;
        }
        super.writeDefaultNamespace(uri);
        this.namespaceContext.addPrefix("", uri);
        this.writtenUris.get(0).add(uri);
    }

    @Override
    public void setDefaultNamespace(String uri) throws XMLStreamException {
        if (this.matchesDropped(true)) {
            return;
        }
        String value = this.nsMap.get(uri);
        if (value != null && value.isEmpty() || this.isDefaultNamespaceRedefined() && !this.isDefaultNamespaceRedefined(uri)) {
            return;
        }
        super.setDefaultNamespace(value != null ? value : uri);
    }

    @Override
    public void writeStartElement(String prefix, String local, String uri) throws XMLStreamException {
        String theprefix;
        QName appendQName;
        ++this.currentDepth;
        this.namespaceContext.down();
        if (this.matchesDropped(false)) {
            return;
        }
        HashSet s2 = this.writtenUris.isEmpty() ? new HashSet() : new HashSet(this.writtenUris.get(0));
        this.writtenUris.add(0, s2);
        QName theName = new QName(uri, local, prefix);
        ElementProperty appendProp = this.appendMap.remove(theName);
        boolean replaceContent = appendProp != null && theName.equals(appendProp.getName());
        boolean dropped = this.dropElements.contains(theName);
        QName expected = this.elementsMap.get(theName);
        if (expected == null) {
            expected = theName;
        } else {
            if (prefix.isEmpty() && !expected.getNamespaceURI().isEmpty() && theName.getNamespaceURI().isEmpty()) {
                prefix = this.namespaceContext.getPrefix(expected.getNamespaceURI());
                if (prefix == null) {
                    prefix = this.namespaceContext.findUniquePrefix(expected.getNamespaceURI());
                }
            } else if (!prefix.isEmpty() && expected.getNamespaceURI().isEmpty()) {
                prefix = "";
            }
            expected = new QName(expected.getNamespaceURI(), expected.getLocalPart(), prefix);
        }
        ArrayList<ParsingEvent> pe = null;
        if (appendProp != null && !replaceContent) {
            if (!appendProp.isChild()) {
                appendQName = appendProp.getName();
                theprefix = this.namespaceContext.getPrefix(appendQName.getNamespaceURI());
                boolean nsadded = false;
                if (theprefix == null) {
                    nsadded = true;
                    theprefix = this.namespaceContext.getPrefix(appendQName.getNamespaceURI());
                    if (theprefix == null && appendQName.getNamespaceURI().equals(expected.getNamespaceURI()) && expected.getPrefix().length() > 0) {
                        theprefix = expected.getPrefix();
                    } else if (theprefix == null) {
                        theprefix = this.namespaceContext.findUniquePrefix(appendQName.getNamespaceURI());
                    }
                    if (theprefix == null) {
                        theprefix = "";
                    }
                }
                this.write(new QName(appendQName.getNamespaceURI(), appendQName.getLocalPart(), theprefix), false);
                if (nsadded && theprefix.length() > 0) {
                    this.writeNamespace(theprefix, appendQName.getNamespaceURI());
                }
                if (appendProp.getText() == null) {
                    ++this.currentDepth;
                    pe = new ArrayList<ParsingEvent>();
                    pe.add(TransformUtils.createEndElementEvent(expected));
                    pe.add(TransformUtils.createEndElementEvent(appendProp.getName()));
                    this.pushedAheadEvents.add(0, null);
                    this.elementsStack.add(0, appendQName);
                } else {
                    super.writeCharacters(appendProp.getText());
                    super.writeEndElement();
                }
            }
        } else if (null != appendProp && replaceContent) {
            this.replaceText = appendProp.getText();
        } else {
            if (dropped) {
                this.elementsStack.add(0, theName);
                return;
            }
            if (TransformUtils.isEmptyQName(expected)) {
                this.dropDepth = this.currentDepth;
                return;
            }
        }
        this.write(expected, false);
        if (!expected.getNamespaceURI().isEmpty() && theName.getNamespaceURI().isEmpty()) {
            this.writeNamespace(expected.getPrefix(), expected.getNamespaceURI());
        }
        this.pushedAheadEvents.add(0, pe);
        this.elementsStack.add(0, expected);
        String string = this.replaceNamespace = expected.getNamespaceURI().equals(theName.getNamespaceURI()) ? null : theName.getNamespaceURI();
        if (appendProp != null && !replaceContent && appendProp.isChild()) {
            appendQName = appendProp.getName();
            theprefix = this.namespaceContext.getPrefix(appendQName.getNamespaceURI());
            if (appendProp.getText() == null) {
                this.write(new QName(appendQName.getNamespaceURI(), appendQName.getLocalPart(), theprefix == null ? "" : theprefix), false);
                if (this.namespaceContext.getPrefix(appendQName.getNamespaceURI()) == null) {
                    this.writeNamespace(theprefix, uri);
                }
                ++this.currentDepth;
                pe = new ArrayList();
                pe.add(TransformUtils.createEndElementEvent(appendProp.getName()));
                pe.add(TransformUtils.createEndElementEvent(expected));
                this.pushedAheadEvents.add(0, pe);
                this.elementsStack.add(0, appendQName);
            } else {
                this.pushedAheadEvents.remove(0);
                pe = new ArrayList();
                pe.add(TransformUtils.createStartElementEvent(appendProp.getName()));
                pe.add(TransformUtils.createCharactersEvent(appendProp.getText()));
                pe.add(TransformUtils.createEndElementEvent(appendProp.getName()));
                pe.add(TransformUtils.createEndElementEvent(expected));
                this.pushedAheadEvents.add(0, pe);
            }
        }
    }

    @Override
    public void writeStartElement(String uri, String local) throws XMLStreamException {
        this.writeStartElement("", local, uri);
    }

    @Override
    public void writeStartElement(String local) throws XMLStreamException {
        this.writeStartElement("", local, "");
    }

    @Override
    public void writeEndElement() throws XMLStreamException {
        QName theName;
        boolean dropped;
        boolean indrop = this.matchesDropped(false);
        this.namespaceContext.up();
        --this.currentDepth;
        if (indrop) {
            if (this.dropDepth > this.currentDepth) {
                this.dropDepth = 0;
            }
            return;
        }
        if (!this.writtenUris.isEmpty()) {
            this.writtenUris.remove(0);
        }
        if (!(dropped = this.dropElements.contains(theName = this.elementsStack.remove(0)))) {
            List<ParsingEvent> pes = this.pushedAheadEvents.remove(0);
            if (null != pes) {
                for (ParsingEvent pe : pes) {
                    switch (pe.getEvent()) {
                        case 1: {
                            this.write(pe.getName(), true);
                            break;
                        }
                        case 2: {
                            super.writeEndElement();
                            break;
                        }
                        case 4: {
                            super.writeCharacters(pe.getValue());
                            break;
                        }
                    }
                }
            } else {
                super.writeEndElement();
            }
        }
    }

    @Override
    public void writeCharacters(String text) throws XMLStreamException {
        if (this.matchesDropped(false)) {
            return;
        }
        if (this.replaceText != null) {
            text = this.replaceText;
            this.replaceText = null;
        }
        super.writeCharacters(text);
    }

    @Override
    public void writeCharacters(char[] text, int arg1, int arg2) throws XMLStreamException {
        if (this.matchesDropped(false)) {
            return;
        }
        if (this.replaceText != null) {
            text = this.replaceText.toCharArray();
            this.replaceText = null;
        }
        super.writeCharacters(text, arg1, arg2);
    }

    private void write(QName qname, boolean replacePrefix) throws XMLStreamException {
        boolean writeNs = false;
        String prefix = "";
        if (!qname.getNamespaceURI().isEmpty()) {
            if ((replacePrefix || this.isDefaultNamespaceRedefined()) && qname.getPrefix().isEmpty()) {
                prefix = this.namespaceContext.getPrefix(qname.getNamespaceURI());
                if (prefix == null) {
                    prefix = this.namespaceContext.findUniquePrefix(qname.getNamespaceURI());
                    writeNs = true;
                }
            } else {
                prefix = qname.getPrefix();
            }
        }
        if (this.isDefaultNamespaceRedefined(qname.getNamespaceURI())) {
            prefix = "";
        }
        super.writeStartElement(prefix, qname.getLocalPart(), qname.getNamespaceURI());
        if (writeNs || !qname.getNamespaceURI().equals(this.namespaceContext.getNamespaceURI(prefix))) {
            this.writeNamespace(prefix, qname.getNamespaceURI());
        }
    }

    private boolean isDefaultNamespaceRedefined() {
        return this.defaultNamespace != null;
    }

    private boolean isDefaultNamespaceRedefined(String uri) {
        return this.isDefaultNamespaceRedefined() && this.defaultNamespace.equals(uri);
    }

    private boolean matchesDropped(boolean shallow) {
        return this.dropDepth > 0 && this.dropDepth <= this.currentDepth || shallow && !this.elementsStack.isEmpty() && this.dropElements.contains(this.elementsStack.get(0));
    }

    @Override
    public NamespaceContext getNamespaceContext() {
        return this.namespaceContext;
    }

    @Override
    public void writeAttribute(String prefix, String uri, String local, String value) throws XMLStreamException {
        if (this.matchesDropped(false)) {
            return;
        }
        QName expected = this.attributesMap.get(new QName(uri, local, prefix));
        if (expected != null) {
            if (TransformUtils.isEmptyQName(expected)) {
                return;
            }
            uri = expected.getNamespaceURI();
            local = expected.getLocalPart();
        }
        if (!this.attributesToElements) {
            super.writeAttribute(prefix, uri, local, value);
        } else {
            this.writeAttributeAsElement(uri, local, value);
        }
    }

    @Override
    public void writeAttribute(String local, String value) throws XMLStreamException {
        if (this.matchesDropped(false)) {
            return;
        }
        String uri = "";
        QName expected = this.attributesMap.get(new QName("", local));
        if (expected != null) {
            if (TransformUtils.isEmptyQName(expected)) {
                return;
            }
            uri = expected.getNamespaceURI();
            local = expected.getLocalPart();
        }
        if (!this.attributesToElements) {
            if (!uri.isEmpty()) {
                super.writeAttribute(uri, local, value);
            } else {
                super.writeAttribute(local, value);
            }
        } else {
            this.writeAttributeAsElement(uri, local, value);
        }
    }

    private void writeAttributeAsElement(String uri, String local, String value) throws XMLStreamException {
        this.writeStartElement(uri, local);
        this.writeCharacters(value);
        this.writeEndElement();
    }
}

