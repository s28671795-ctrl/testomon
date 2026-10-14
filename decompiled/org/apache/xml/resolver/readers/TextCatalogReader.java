/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.resolver.readers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Stack;
import java.util.Vector;
import org.apache.xml.resolver.Catalog;
import org.apache.xml.resolver.CatalogEntry;
import org.apache.xml.resolver.CatalogException;
import org.apache.xml.resolver.readers.CatalogReader;

public class TextCatalogReader
implements CatalogReader {
    protected InputStream catfile = null;
    protected int[] stack = new int[3];
    protected Stack tokenStack = new Stack();
    protected int top = -1;
    protected boolean caseSensitive = false;

    public void setCaseSensitive(boolean isCaseSensitive) {
        this.caseSensitive = isCaseSensitive;
    }

    public boolean getCaseSensitive() {
        return this.caseSensitive;
    }

    public void readCatalog(Catalog catalog, String fileUrl) throws MalformedURLException, IOException {
        URL catURL = null;
        try {
            catURL = new URL(fileUrl);
        }
        catch (MalformedURLException e) {
            catURL = new URL("file:///" + fileUrl);
        }
        URLConnection urlCon = catURL.openConnection();
        try {
            this.readCatalog(catalog, urlCon.getInputStream());
        }
        catch (FileNotFoundException e) {
            catalog.getCatalogManager().debug.message(1, "Failed to load catalog, file not found", catURL.toString());
        }
    }

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

    protected void finalize() {
        if (this.catfile != null) {
            try {
                this.catfile.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        this.catfile = null;
    }

    /*
     * Unable to fully structure code
     */
    protected String nextToken() throws IOException, CatalogException {
        block8: {
            token = "";
            if (!this.tokenStack.empty()) {
                return (String)this.tokenStack.pop();
            }
            do {
                ch = this.catfile.read();
                while (ch <= 32) {
                    ch = this.catfile.read();
                    if (ch >= 0) continue;
                    return null;
                }
                nextch = this.catfile.read();
                if (nextch < 0) {
                    return null;
                }
                if (ch != 45 || nextch != 45) break block8;
                ch = 32;
                nextch = this.nextChar();
                while ((ch != 45 || nextch != 45) && nextch > 0) {
                    ch = nextch;
                    nextch = this.nextChar();
                }
            } while (nextch >= 0);
            throw new CatalogException(8, "Unterminated comment in catalog file; EOF treated as end-of-comment.");
        }
        this.stack[++this.top] = nextch;
        this.stack[++this.top] = ch;
        ch = this.nextChar();
        if (ch != 34 && ch != 39) ** GOTO lbl43
        quote = ch;
        while ((ch = this.nextChar()) != quote) {
            chararr = new char[]{(char)ch};
            s = new String(chararr);
            token = token.concat(s);
        }
        return token;
lbl-1000:
        // 1 sources

        {
            nextch = this.nextChar();
            if (ch == 45 && nextch == 45) {
                this.stack[++this.top] = ch;
                this.stack[++this.top] = nextch;
                return token;
            }
            chararr = new char[]{(char)ch};
            s = new String(chararr);
            token = token.concat(s);
            ch = nextch;
lbl43:
            // 2 sources

            ** while (ch > 32)
        }
lbl44:
        // 1 sources

        return token;
    }

    protected int nextChar() throws IOException {
        if (this.top < 0) {
            return this.catfile.read();
        }
        return this.stack[this.top--];
    }
}

