/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils;

import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import org.apache.cxf.common.util.StringUtils;

public class PropertiesExpandingStreamReader
extends StreamReaderDelegate {
    public static final String DELIMITER = "@";
    private final Map<String, String> props;

    public PropertiesExpandingStreamReader(XMLStreamReader reader, Map<String, String> props) {
        super(reader);
        this.props = props;
    }

    protected String expandProperty(String value) {
        String envValue;
        String propName;
        int endIndx;
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        int startIndx = value.indexOf(DELIMITER);
        if (startIndx > -1 && (endIndx = value.lastIndexOf(DELIMITER)) > -1 && startIndx + 1 < endIndx && !StringUtils.isEmpty(propName = value.substring(startIndx + 1, endIndx)) && !StringUtils.isEmpty(envValue = this.props.get(propName))) {
            return value.substring(0, startIndx) + envValue + value.substring(endIndx + 1);
        }
        return value;
    }

    @Override
    public String getElementText() throws XMLStreamException {
        return this.expandProperty(super.getElementText());
    }

    @Override
    public String getAttributeValue(String namespaceURI, String localName) {
        return this.expandProperty(super.getAttributeValue(namespaceURI, localName));
    }

    @Override
    public String getAttributeValue(int index) {
        return this.expandProperty(super.getAttributeValue(index));
    }

    @Override
    public String getText() {
        return this.expandProperty(super.getText());
    }
}

