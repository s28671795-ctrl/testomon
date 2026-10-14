/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.mimepull;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import org.jvnet.mimepull.FinalArrayList;
import org.jvnet.mimepull.Hdr;
import org.jvnet.mimepull.Header;
import org.jvnet.mimepull.MIMEParser;
import org.jvnet.mimepull.MIMEParsingException;

final class InternetHeaders {
    private final FinalArrayList<Hdr> headers = new FinalArrayList();

    InternetHeaders(MIMEParser.LineInputStream lis) {
        String prevline = null;
        StringBuilder lineBuffer = new StringBuilder();
        try {
            String line;
            do {
                if ((line = lis.readLine()) != null && (line.startsWith(" ") || line.startsWith("\t"))) {
                    if (prevline != null) {
                        lineBuffer.append(prevline);
                        prevline = null;
                    }
                    lineBuffer.append("\r\n");
                    lineBuffer.append(line);
                    continue;
                }
                if (prevline != null) {
                    this.addHeaderLine(prevline);
                } else if (lineBuffer.length() > 0) {
                    this.addHeaderLine(lineBuffer.toString());
                    lineBuffer.setLength(0);
                }
                prevline = line;
            } while (line != null && line.length() > 0);
        }
        catch (IOException ioex) {
            throw new MIMEParsingException("Error in input stream", ioex);
        }
    }

    List<String> getHeader(String name) {
        FinalArrayList<String> v = new FinalArrayList<String>();
        int len = this.headers.size();
        for (int i = 0; i < len; ++i) {
            Hdr h2 = (Hdr)this.headers.get(i);
            if (!name.equalsIgnoreCase(h2.name)) continue;
            v.add(h2.getValue());
        }
        return v.size() == 0 ? null : v;
    }

    FinalArrayList<? extends Header> getAllHeaders() {
        return this.headers;
    }

    void addHeaderLine(String line) {
        try {
            char c = line.charAt(0);
            if (c == ' ' || c == '\t') {
                Hdr h2 = (Hdr)this.headers.get(this.headers.size() - 1);
                h2.line = h2.line + "\r\n" + line;
            } else {
                this.headers.add(new Hdr(line));
            }
        }
        catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
        }
        catch (NoSuchElementException noSuchElementException) {
            // empty catch block
        }
    }
}

