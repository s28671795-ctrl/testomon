/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.resolver.readers;

import org.apache.xml.resolver.Catalog;
import org.xml.sax.ContentHandler;
import org.xml.sax.DocumentHandler;

public interface SAXCatalogParser
extends ContentHandler,
DocumentHandler {
    public void setCatalog(Catalog var1);
}

