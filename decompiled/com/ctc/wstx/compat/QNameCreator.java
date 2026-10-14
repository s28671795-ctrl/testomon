/*
 * Decompiled with CFR 0.152.
 */
package com.ctc.wstx.compat;

import java.util.logging.Logger;
import javax.xml.namespace.QName;

public final class QNameCreator {
    private static final Helper _helper;

    public static QName create(String uri, String localName, String prefix) {
        if (_helper == null) {
            return new QName(uri, localName);
        }
        return _helper.create(uri, localName, prefix);
    }

    static {
        Helper h2 = null;
        try {
            Helper h0 = new Helper();
            h0.create("elem", "http://dummy", "ns");
            h2 = h0;
        }
        catch (Throwable t2) {
            String msg = "Could not construct QNameCreator.Helper; assume 3-arg QName constructor not available and use 2-arg method instead. Problem: " + t2.getMessage();
            try {
                Logger.getLogger("com.ctc.wstx.compat.QNameCreator").warning(msg);
            }
            catch (Throwable t22) {
                System.err.println("ERROR: failed to log error using Logger (problem " + t2.getMessage() + "), original problem: " + msg);
            }
        }
        _helper = h2;
    }

    private static final class Helper {
        public QName create(String localName, String nsURI, String prefix) {
            return new QName(localName, nsURI, prefix);
        }
    }
}

