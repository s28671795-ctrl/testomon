/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.messaging.saaj.util;

import java.util.Iterator;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;

public class MimeHeadersUtil {
    public static MimeHeaders copy(MimeHeaders headers) {
        MimeHeaders newHeaders = new MimeHeaders();
        Iterator<MimeHeader> eachHeader = headers.getAllHeaders();
        while (eachHeader.hasNext()) {
            MimeHeader currentHeader = eachHeader.next();
            newHeaders.addHeader(currentHeader.getName(), currentHeader.getValue());
        }
        return newHeaders;
    }
}

