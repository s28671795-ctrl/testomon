/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils;

import com.ctc.wstx.stax.WstxEventFactory;
import com.ctc.wstx.stax.WstxInputFactory;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import org.codehaus.stax2.XMLStreamReader2;

final class WoodstoxHelper {
    private WoodstoxHelper() {
    }

    public static XMLInputFactory createInputFactory() {
        return new WstxInputFactory();
    }

    public static XMLEventFactory createEventFactory() {
        return new WstxEventFactory();
    }

    public static void setProperty(XMLStreamReader reader, String p, Object v) {
        ((XMLStreamReader2)reader).setProperty(p, v);
    }
}

