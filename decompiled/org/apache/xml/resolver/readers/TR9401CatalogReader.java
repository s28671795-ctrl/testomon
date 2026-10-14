/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.resolver.readers;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Vector;
import org.apache.xml.resolver.Catalog;
import org.apache.xml.resolver.CatalogEntry;
import org.apache.xml.resolver.CatalogException;
import org.apache.xml.resolver.readers.TextCatalogReader;

public class TR9401CatalogReader
extends TextCatalogReader {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void readCatalog(Catalog catalog, InputStream is) throws MalformedURLException, IOException {
        this.catfile = is;
        if (this.catfile == null) {
            return;
        }
        Vector<String> unknownEntry = null;
        try {
            while (true) {
                String token;
                if ((token = this.nextToken()) == null) {
                    if (unknownEntry != null) {
                        catalog.unknownEntry(unknownEntry);
                        unknownEntry = null;
                    }
                    this.catfile.close();
                    this.catfile = null;
                    return;
                }
                String entryToken = null;
                entryToken = this.caseSensitive ? token : token.toUpperCase();
                if (entryToken.equals("DELEGATE")) {
                    entryToken = "DELEGATE_PUBLIC";
                }
                try {
                    int type = CatalogEntry.getEntryType(entryToken);
                    int numArgs = CatalogEntry.getEntryArgCount(type);
                    Vector<String> args2 = new Vector<String>();
                    if (unknownEntry != null) {
                        catalog.unknownEntry(unknownEntry);
                        unknownEntry = null;
                    }
                    int count = 0;
                    while (true) {
                        if (count >= numArgs) {
                            catalog.addEntry(new CatalogEntry(entryToken, args2));
                        }
                        args2.addElement(this.nextToken());
                        ++count;
                    }
                }
                catch (CatalogException cex) {
                    if (cex.getExceptionType() == 3) {
                        if (unknownEntry == null) {
                            unknownEntry = new Vector<String>();
                        }
                        unknownEntry.addElement(token);
                        continue;
                    }
                    if (cex.getExceptionType() == 2) {
                        catalog.getCatalogManager().debug.message(1, "Invalid catalog entry", token);
                        unknownEntry = null;
                        continue;
                    }
                    if (cex.getExceptionType() != 8) continue;
                    catalog.getCatalogManager().debug.message(1, cex.getMessage());
                }
            }
        }
        catch (CatalogException cex2) {
            if (cex2.getExceptionType() == 8) {
                catalog.getCatalogManager().debug.message(1, cex2.getMessage());
            }
            return;
        }
    }
}

