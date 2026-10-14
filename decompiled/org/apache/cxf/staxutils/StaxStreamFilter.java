/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils;

import javax.xml.namespace.QName;
import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLStreamReader;

public class StaxStreamFilter
implements StreamFilter {
    private QName[] tags;

    public StaxStreamFilter(QName ... eventsToReject) {
        this.tags = eventsToReject;
    }

    @Override
    public boolean accept(XMLStreamReader reader) {
        QName elName;
        if (reader.isStartElement()) {
            elName = reader.getName();
            for (QName tag : this.tags) {
                if (!elName.equals(tag)) continue;
                return false;
            }
        }
        if (reader.isEndElement()) {
            elName = reader.getName();
            for (QName tag : this.tags) {
                if (!elName.equals(tag)) continue;
                return false;
            }
        }
        return true;
    }
}

