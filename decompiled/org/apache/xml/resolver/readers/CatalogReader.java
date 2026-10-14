/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.resolver.readers;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import org.apache.xml.resolver.Catalog;
import org.apache.xml.resolver.CatalogException;

public interface CatalogReader {
    public void readCatalog(Catalog var1, String var2) throws MalformedURLException, IOException, CatalogException;

    public void readCatalog(Catalog var1, InputStream var2) throws IOException, CatalogException;
}

