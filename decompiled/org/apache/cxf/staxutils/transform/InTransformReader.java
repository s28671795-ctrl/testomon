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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.staxutils.DepthXMLStreamReader;
import org.apache.cxf.staxutils.transform.DelegatingNamespaceContext;
import org.apache.cxf.staxutils.transform.ElementProperty;
import org.apache.cxf.staxutils.transform.ParsingEvent;
import org.apache.cxf.staxutils.transform.QNamesMap;
import org.apache.cxf.staxutils.transform.TransformUtils;

public class InTransformReader
extends DepthXMLStreamReader {
    private static final Logger LOG = LogUtils.getLogger(InTransformReader.class);
    private static final String INTERN_NAMES = "org.codehaus.stax2.internNames";
    private static final String INTERN_NS = "org.codehaus.stax2.internNsUris";
    private QNamesMap inElementsMap;
    private QNamesMap inAttributesMap;
    private Map<QName, ElementProperty> inAppendMap = new HashMap<QName, ElementProperty>(5);
    private Set<QName> inDropSet = new HashSet<QName>(5);
    private Map<String, String> nsMap = new HashMap<String, String>(5);
    private List<ParsingEvent> pushedBackEvents = new LinkedList<ParsingEvent>();
    private List<List<ParsingEvent>> pushedAheadEvents = new LinkedList<List<ParsingEvent>>();
    private String replaceText;
    private ParsingEvent currentEvent;
    private List<Integer> attributesIndexes = new ArrayList<Integer>();
    private boolean blockOriginalReader = true;
    private boolean attributesIndexed;
    private DelegatingNamespaceContext namespaceContext;

    public InTransformReader(XMLStreamReader reader, Map<String, String> inMap, Map<String, String> appendMap, boolean blockOriginalReader) {
        this(reader, inMap, appendMap, null, null, blockOriginalReader);
    }

    public InTransformReader(XMLStreamReader reader, Map<String, String> inEMap, Map<String, String> appendMap, List<String> dropESet, Map<String, String> inAMap, boolean blockOriginalReader) {
        super(reader);
        this.inElementsMap = new QNamesMap(inEMap == null ? 0 : inEMap.size());
        this.inAttributesMap = new QNamesMap(inAMap == null ? 0 : inAMap.size());
        this.blockOriginalReader = blockOriginalReader;
        TransformUtils.convertToQNamesMap(inEMap, this.inElementsMap, this.nsMap);
        TransformUtils.convertToQNamesMap(inAMap, this.inAttributesMap, null);
        TransformUtils.convertToMapOfElementProperties(appendMap, this.inAppendMap);
        TransformUtils.convertToSetOfQNames(dropESet, this.inDropSet);
        this.namespaceContext = new DelegatingNamespaceContext(reader.getNamespaceContext(), this.nsMap);
    }

    @Override
    public XMLStreamReader getReader() {
        return this.blockOriginalReader ? this : super.getReader();
    }

    @Override
    public int next() throws XMLStreamException {
        int event;
        boolean doDebug = LOG.isLoggable(Level.FINE);
        if (!this.pushedBackEvents.isEmpty()) {
            this.currentEvent = this.pushedBackEvents.remove(0);
            if (doDebug) {
                LOG.fine("pushed event available: " + this.currentEvent);
            }
            return this.currentEvent.getEvent();
        }
        if (doDebug) {
            LOG.fine("no pushed event");
        }
        if ((event = super.next()) == 1) {
            boolean replaceContent;
            this.attributesIndexed = false;
            this.namespaceContext.down();
            QName theName = super.getName();
            ElementProperty appendProp = this.inAppendMap.remove(theName);
            boolean bl = replaceContent = appendProp != null && theName.equals(appendProp.getName());
            if (doDebug) {
                LOG.fine("read StartElement " + theName + " at " + this.getDepth());
            }
            boolean dropped = this.inDropSet.contains(theName);
            QName expected = this.inElementsMap.get(theName);
            if (expected == null) {
                expected = theName;
            } else {
                String prefix = theName.getPrefix();
                if (prefix.isEmpty() && theName.getNamespaceURI().isEmpty() && !expected.getNamespaceURI().isEmpty()) {
                    prefix = this.namespaceContext.getPrefix(expected.getNamespaceURI());
                    if (prefix == null) {
                        prefix = this.namespaceContext.findUniquePrefix(expected.getNamespaceURI());
                    }
                } else if (!prefix.isEmpty() && expected.getNamespaceURI().isEmpty()) {
                    prefix = "";
                }
                expected = new QName(expected.getNamespaceURI(), expected.getLocalPart(), prefix);
            }
            if (null != appendProp && !replaceContent) {
                this.handleAppendMode(expected, appendProp);
            } else if (null != appendProp && replaceContent) {
                this.replaceText = appendProp.getText();
                if (doDebug) {
                    LOG.fine("replacing content with " + this.replaceText);
                }
                this.currentEvent = TransformUtils.createStartElementEvent(expected);
                this.pushedAheadEvents.add(0, null);
            } else if (dropped) {
                if (doDebug) {
                    LOG.fine("shallow-dropping start " + expected);
                }
                event = this.next();
            } else if (TransformUtils.isEmptyQName(expected)) {
                if (doDebug) {
                    LOG.fine("deep-dropping " + theName);
                }
                this.handleDeepDrop();
                event = this.next();
            } else {
                this.handleDefaultMode(theName, expected);
            }
        } else if (event == 2) {
            QName theName = super.getName();
            if (doDebug) {
                LOG.fine("read EndElement " + theName + " at " + this.getDepth());
            }
            this.namespaceContext.up();
            boolean dropped = this.inDropSet.contains(theName);
            if (!dropped) {
                List<ParsingEvent> pe = this.pushedAheadEvents.remove(0);
                if (null != pe) {
                    if (doDebug) {
                        LOG.fine("pushed event found");
                    }
                    this.pushedBackEvents.addAll(0, pe);
                    this.currentEvent = this.pushedBackEvents.remove(0);
                    event = this.currentEvent.getEvent();
                } else if (doDebug) {
                    LOG.fine("no pushed event found");
                }
            } else {
                if (doDebug) {
                    LOG.fine("shallow-dropping end " + theName);
                }
                event = this.next();
            }
        } else {
            if (doDebug) {
                LOG.fine("read other event " + event);
            }
            this.currentEvent = null;
        }
        return event;
    }

    private void handleAppendMode(QName expected, ElementProperty appendProp) {
        boolean doDebug = LOG.isLoggable(Level.FINE);
        if (appendProp.isChild()) {
            if (null == appendProp.getText()) {
                this.pushedBackEvents.add(0, TransformUtils.createStartElementEvent(appendProp.getName()));
                this.currentEvent = TransformUtils.createStartElementEvent(expected);
                ArrayList<ParsingEvent> pe = new ArrayList<ParsingEvent>(2);
                pe.add(TransformUtils.createEndElementEvent(appendProp.getName()));
                pe.add(TransformUtils.createEndElementEvent(expected));
                this.pushedAheadEvents.add(0, pe);
            } else {
                this.currentEvent = TransformUtils.createStartElementEvent(expected);
                ArrayList<ParsingEvent> pe = new ArrayList<ParsingEvent>(4);
                pe.add(TransformUtils.createStartElementEvent(appendProp.getName()));
                pe.add(TransformUtils.createCharactersEvent(appendProp.getText()));
                pe.add(TransformUtils.createEndElementEvent(appendProp.getName()));
                pe.add(TransformUtils.createEndElementEvent(expected));
                this.pushedAheadEvents.add(0, pe);
            }
        } else if (null == appendProp.getText()) {
            this.pushedBackEvents.add(0, TransformUtils.createStartElementEvent(expected));
            this.currentEvent = TransformUtils.createStartElementEvent(appendProp.getName());
            ArrayList<ParsingEvent> pe = new ArrayList<ParsingEvent>(2);
            pe.add(TransformUtils.createEndElementEvent(expected));
            pe.add(TransformUtils.createEndElementEvent(appendProp.getName()));
            this.pushedAheadEvents.add(0, pe);
        } else {
            this.pushedBackEvents.add(0, TransformUtils.createStartElementEvent(expected));
            this.pushedBackEvents.add(0, TransformUtils.createEndElementEvent(appendProp.getName()));
            this.pushedBackEvents.add(0, TransformUtils.createCharactersEvent(appendProp.getText()));
            this.currentEvent = TransformUtils.createStartElementEvent(appendProp.getName());
            if (doDebug) {
                LOG.fine("ap-pre-incl " + appendProp.getName() + "=" + appendProp.getText());
            }
            this.pushedAheadEvents.add(0, null);
        }
    }

    private void handleDefaultMode(QName name, QName expected) {
        this.currentEvent = TransformUtils.createStartElementEvent(expected);
        if (!name.equals(expected)) {
            ArrayList<ParsingEvent> pe = new ArrayList<ParsingEvent>(1);
            pe.add(TransformUtils.createEndElementEvent(expected));
            this.pushedAheadEvents.add(0, pe);
        } else {
            this.pushedAheadEvents.add(0, null);
        }
    }

    private void handleDeepDrop() throws XMLStreamException {
        int depth = this.getDepth();
        while (depth != this.getDepth() || super.next() != 2) {
        }
    }

    @Override
    public Object getProperty(String name) {
        if (INTERN_NAMES.equals(name) || INTERN_NS.equals(name)) {
            return Boolean.FALSE;
        }
        return super.getProperty(name);
    }

    @Override
    public String getLocalName() {
        if (this.currentEvent != null) {
            return this.currentEvent.getName().getLocalPart();
        }
        return super.getLocalName();
    }

    @Override
    public NamespaceContext getNamespaceContext() {
        return this.namespaceContext;
    }

    @Override
    public String getPrefix() {
        QName name = this.readCurrentElement();
        String prefix = name.getPrefix();
        if (prefix.isEmpty() && !this.getNamespaceURI().isEmpty() && (prefix = this.namespaceContext.getPrefix(this.getNamespaceURI())) == null) {
            prefix = "";
        }
        return prefix;
    }

    @Override
    public String getNamespaceURI(int index) {
        String ns = super.getNamespaceURI(index);
        String actualNs = this.nsMap.get(ns);
        if (actualNs != null) {
            return actualNs;
        }
        if (ns.equals(this.reader.getNamespaceURI())) {
            return this.getNamespaceURI();
        }
        return ns;
    }

    @Override
    public String getNamespacePrefix(int index) {
        String ns = super.getNamespaceURI(index);
        String actualNs = this.nsMap.get(ns);
        if (actualNs != null) {
            if (actualNs.length() > 0) {
                return super.getNamespacePrefix(index);
            }
            return "";
        }
        if (ns.equals(this.reader.getNamespaceURI())) {
            return this.getPrefix();
        }
        return this.namespaceContext.getPrefix(ns);
    }

    @Override
    public String getNamespaceURI(String prefix) {
        String ns = super.getNamespaceURI(prefix);
        String actualNs = this.nsMap.get(ns);
        if (actualNs != null) {
            return actualNs;
        }
        return ns != null ? ns : this.namespaceContext.getNamespaceURI(prefix);
    }

    @Override
    public String getNamespaceURI() {
        if (this.currentEvent != null) {
            return this.currentEvent.getName().getNamespaceURI();
        }
        return super.getNamespaceURI();
    }

    private QName readCurrentElement() {
        if (this.currentEvent != null) {
            return this.currentEvent.getName();
        }
        String ns = super.getNamespaceURI();
        String name = super.getLocalName();
        String prefix = super.getPrefix();
        return new QName(ns, name, prefix == null ? "" : prefix);
    }

    @Override
    public QName getName() {
        return new QName(this.getNamespaceURI(), this.getLocalName());
    }

    @Override
    public int getAttributeCount() {
        if (!this.pushedBackEvents.isEmpty()) {
            return 0;
        }
        this.checkAttributeIndexRange(-1);
        return this.attributesIndexes.size();
    }

    @Override
    public String getAttributeLocalName(int arg0) {
        if (!this.pushedBackEvents.isEmpty()) {
            this.throwIndexException(arg0, 0);
        }
        this.checkAttributeIndexRange(arg0);
        return this.getAttributeName(arg0).getLocalPart();
    }

    @Override
    public QName getAttributeName(int arg0) {
        if (!this.pushedBackEvents.isEmpty()) {
            this.throwIndexException(arg0, 0);
        }
        this.checkAttributeIndexRange(arg0);
        QName aname = super.getAttributeName(this.attributesIndexes.get(arg0));
        QName expected = this.inAttributesMap.get(aname);
        return expected == null ? aname : expected;
    }

    @Override
    public String getAttributeNamespace(int arg0) {
        if (!this.pushedBackEvents.isEmpty()) {
            this.throwIndexException(arg0, 0);
        }
        this.checkAttributeIndexRange(arg0);
        return this.getAttributeName(arg0).getNamespaceURI();
    }

    @Override
    public String getAttributePrefix(int arg0) {
        if (!this.pushedBackEvents.isEmpty()) {
            this.throwIndexException(arg0, 0);
        }
        this.checkAttributeIndexRange(arg0);
        QName aname = this.getAttributeName(arg0);
        if ("".equals(aname.getNamespaceURI())) {
            return "";
        }
        String actualNs = this.nsMap.get(aname.getNamespaceURI());
        if (actualNs != null) {
            return this.namespaceContext.findUniquePrefix(actualNs);
        }
        return this.namespaceContext.getPrefix(aname.getNamespaceURI());
    }

    @Override
    public String getAttributeType(int arg0) {
        if (!this.pushedBackEvents.isEmpty()) {
            this.throwIndexException(arg0, 0);
        }
        this.checkAttributeIndexRange(arg0);
        return super.getAttributeType(this.attributesIndexes.get(arg0));
    }

    @Override
    public String getAttributeValue(int arg0) {
        if (!this.pushedBackEvents.isEmpty()) {
            this.throwIndexException(arg0, 0);
        }
        this.checkAttributeIndexRange(arg0);
        return super.getAttributeValue(this.attributesIndexes.get(arg0));
    }

    @Override
    public String getAttributeValue(String namespace, String localName) {
        if (!this.pushedBackEvents.isEmpty()) {
            return null;
        }
        this.checkAttributeIndexRange(-1);
        return super.getAttributeValue(namespace, localName);
    }

    @Override
    public String getText() {
        if (this.currentEvent != null) {
            return this.currentEvent.getValue();
        }
        String superText = super.getText();
        if (this.replaceText != null) {
            superText = this.replaceText;
            this.replaceText = null;
        }
        return superText;
    }

    @Override
    public char[] getTextCharacters() {
        if (this.currentEvent != null && this.currentEvent.getValue() != null) {
            return this.currentEvent.getValue().toCharArray();
        }
        char[] superChars = super.getTextCharacters();
        if (this.replaceText != null) {
            superChars = this.replaceText.toCharArray();
            this.replaceText = null;
        }
        return superChars;
    }

    @Override
    public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
        if (this.currentEvent != null && this.currentEvent.getValue() != null) {
            int len = this.currentEvent.getValue().length() - sourceStart;
            if (len > length) {
                len = length;
            }
            this.currentEvent.getValue().getChars(sourceStart, sourceStart + len, target, targetStart);
            return len;
        }
        return super.getTextCharacters(sourceStart, target, targetStart, length);
    }

    @Override
    public int getTextLength() {
        if (this.currentEvent != null && this.currentEvent.getValue() != null) {
            return this.currentEvent.getValue().length();
        }
        return super.getTextLength();
    }

    private void checkAttributeIndexRange(int index) {
        if (!this.attributesIndexed) {
            this.attributesIndexes.clear();
            int c = super.getAttributeCount();
            for (int i = 0; i < c; ++i) {
                QName aname = super.getAttributeName(i);
                QName expected = this.inAttributesMap.get(aname);
                if (expected != null && TransformUtils.isEmptyQName(expected)) continue;
                this.attributesIndexes.add(i);
            }
            this.attributesIndexed = true;
        }
        if (index >= this.attributesIndexes.size()) {
            this.throwIndexException(index, this.attributesIndexes.size());
        }
    }

    private void throwIndexException(int index, int size) {
        throw new IllegalArgumentException("Invalid index " + index + "; current element has only " + size + " attributes");
    }
}

