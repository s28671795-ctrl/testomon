/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.utils;

import org.apache.xml.security.utils.XPathAPI;
import org.apache.xml.security.utils.XPathFactory;
import org.apache.xml.security.utils.XalanXPathAPI;

public class XalanXPathFactory
extends XPathFactory {
    @Override
    public XPathAPI newXPathAPI() {
        return new XalanXPathAPI();
    }
}

