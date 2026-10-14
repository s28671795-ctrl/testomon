/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils.transform;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.transform.ElementProperty;
import org.apache.cxf.staxutils.transform.InTransformReader;
import org.apache.cxf.staxutils.transform.OutTransformWriter;
import org.apache.cxf.staxutils.transform.ParsingEvent;
import org.apache.cxf.staxutils.transform.QNamesMap;

public final class TransformUtils {
    private TransformUtils() {
    }

    public static XMLStreamReader createNewReaderIfNeeded(XMLStreamReader reader, InputStream is) {
        return reader == null ? StaxUtils.createXMLStreamReader(is) : reader;
    }

    public static XMLStreamWriter createNewWriterIfNeeded(XMLStreamWriter writer, OutputStream os) {
        return TransformUtils.createNewWriterIfNeeded(writer, os, null);
    }

    public static XMLStreamWriter createNewWriterIfNeeded(XMLStreamWriter writer, OutputStream os, String encoding) {
        if (writer != null) {
            return writer;
        }
        if (encoding != null) {
            return StaxUtils.createXMLStreamWriter(os, encoding);
        }
        return StaxUtils.createXMLStreamWriter(os);
    }

    public static XMLStreamWriter createTransformWriterIfNeeded(XMLStreamWriter writer, OutputStream os, Map<String, String> outElementsMap, List<String> outDropElements, Map<String, String> outAppendMap, boolean attributesToElements, String defaultNamespace) {
        if (outElementsMap != null || outDropElements != null || outAppendMap != null || attributesToElements) {
            writer = new OutTransformWriter(TransformUtils.createNewWriterIfNeeded(writer, os), outElementsMap, outAppendMap, outDropElements, null, attributesToElements, defaultNamespace);
        }
        return writer;
    }

    public static XMLStreamWriter createTransformWriterIfNeeded(XMLStreamWriter writer, OutputStream os, Map<String, String> outElementsMap, List<String> outDropElements, Map<String, String> outAppendMap, Map<String, String> outAttributesMap, boolean attributesToElements, String defaultNamespace) {
        return TransformUtils.createTransformWriterIfNeeded(writer, os, outElementsMap, outDropElements, outAppendMap, outAttributesMap, attributesToElements, defaultNamespace, null);
    }

    public static XMLStreamWriter createTransformWriterIfNeeded(XMLStreamWriter writer, OutputStream os, Map<String, String> outElementsMap, List<String> outDropElements, Map<String, String> outAppendMap, Map<String, String> outAttributesMap, boolean attributesToElements, String defaultNamespace, String encoding) {
        if (outElementsMap != null || outDropElements != null || outAppendMap != null || attributesToElements) {
            writer = new OutTransformWriter(TransformUtils.createNewWriterIfNeeded(writer, os, encoding), outElementsMap, outAppendMap, outDropElements, outAttributesMap, attributesToElements, defaultNamespace);
        }
        return writer;
    }

    public static XMLStreamReader createTransformReaderIfNeeded(XMLStreamReader reader, InputStream is, List<String> inDropElements, Map<String, String> inElementsMap, Map<String, String> inAppendMap, boolean blockOriginalReader) {
        return TransformUtils.createTransformReaderIfNeeded(reader, is, inDropElements, inElementsMap, inAppendMap, null, blockOriginalReader);
    }

    public static XMLStreamReader createTransformReaderIfNeeded(XMLStreamReader reader, InputStream is, List<String> inDropElements, Map<String, String> inElementsMap, Map<String, String> inAppendMap, Map<String, String> inAttributesMap, boolean blockOriginalReader) {
        if (inElementsMap != null || inAppendMap != null || inDropElements != null || inAttributesMap != null) {
            reader = new InTransformReader(TransformUtils.createNewReaderIfNeeded(reader, is), inElementsMap, inAppendMap, inDropElements, inAttributesMap, blockOriginalReader);
        }
        return reader;
    }

    protected static void convertToQNamesMap(Map<String, String> map, QNamesMap elementsMap, Map<String, String> nsMap) {
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                QName lname = DOMUtils.convertStringToQName(entry.getKey());
                QName rname = DOMUtils.convertStringToQName(entry.getValue());
                elementsMap.put(lname, rname);
                if (nsMap == null || TransformUtils.isEmptyQName(rname) || !"*".equals(lname.getLocalPart()) || !"*".equals(rname.getLocalPart())) continue;
                nsMap.put(lname.getNamespaceURI(), rname.getNamespaceURI());
            }
        }
    }

    static void convertToMapOfElementProperties(Map<String, String> map, Map<QName, ElementProperty> elementsMap) {
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                String text = null;
                boolean child = false;
                int d = value.indexOf(125);
                d = value.indexOf(61, d < 0 ? 0 : d);
                if (d > 0) {
                    text = value.substring(d + 1);
                    value = value.substring(0, d);
                }
                if (key.endsWith("/")) {
                    key = key.substring(0, key.length() - 1);
                    child = true;
                }
                QName lname = DOMUtils.convertStringToQName(key);
                QName rname = DOMUtils.convertStringToQName(value);
                ElementProperty desc = new ElementProperty(rname, text, child);
                elementsMap.put(lname, desc);
            }
        }
    }

    protected static void convertToSetOfQNames(List<String> set, Set<QName> elementsSet) {
        if (set != null) {
            for (String entry : set) {
                QName name = DOMUtils.convertStringToQName(entry);
                elementsSet.add(name);
            }
        }
    }

    static boolean isEmptyQName(QName qname) {
        return "".equals(qname.getNamespaceURI()) && qname.getLocalPart().isEmpty();
    }

    static ParsingEvent createStartElementEvent(QName name) {
        return new ParsingEvent(1, name, null);
    }

    static ParsingEvent createEndElementEvent(QName name) {
        return new ParsingEvent(2, name, null);
    }

    static ParsingEvent createCharactersEvent(String value) {
        return new ParsingEvent(4, null, value);
    }
}

