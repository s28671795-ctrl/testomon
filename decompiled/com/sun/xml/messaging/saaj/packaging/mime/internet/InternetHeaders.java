/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.messaging.saaj.packaging.mime.internet;

import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
import com.sun.xml.messaging.saaj.packaging.mime.internet.hdr;
import com.sun.xml.messaging.saaj.packaging.mime.util.LineInputStream;
import com.sun.xml.messaging.saaj.util.FinalArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractList;
import java.util.List;
import java.util.NoSuchElementException;

public final class InternetHeaders {
    private final FinalArrayList<hdr> headers = new FinalArrayList();
    private List<String> headerValueView;

    public InternetHeaders() {
    }

    public InternetHeaders(InputStream is) throws MessagingException {
        this.load(is);
    }

    public void load(InputStream is) throws MessagingException {
        LineInputStream lis = new LineInputStream(is);
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
            throw new MessagingException("Error in input stream", ioex);
        }
    }

    public String[] getHeader(String name) {
        FinalArrayList<String> v = new FinalArrayList<String>();
        int len = this.headers.size();
        for (int i = 0; i < len; ++i) {
            hdr h2 = (hdr)this.headers.get(i);
            if (!name.equalsIgnoreCase(h2.name)) continue;
            v.add(h2.getValue());
        }
        if (v.size() == 0) {
            return null;
        }
        return v.toArray(new String[v.size()]);
    }

    public String getHeader(String name, String delimiter) {
        String[] s2 = this.getHeader(name);
        if (s2 == null) {
            return null;
        }
        if (s2.length == 1 || delimiter == null) {
            return s2[0];
        }
        StringBuilder r = new StringBuilder(s2[0]);
        for (int i = 1; i < s2.length; ++i) {
            r.append(delimiter);
            r.append(s2[i]);
        }
        return r.toString();
    }

    public void setHeader(String name, String value) {
        boolean found = false;
        for (int i = 0; i < this.headers.size(); ++i) {
            hdr h2 = (hdr)this.headers.get(i);
            if (!name.equalsIgnoreCase(h2.name)) continue;
            if (!found) {
                int j;
                h2.line = h2.line != null && (j = h2.line.indexOf(58)) >= 0 ? h2.line.substring(0, j + 1) + " " + value : name + ": " + value;
                found = true;
                continue;
            }
            this.headers.remove(i);
            --i;
        }
        if (!found) {
            this.addHeader(name, value);
        }
    }

    public void addHeader(String name, String value) {
        int pos = this.headers.size();
        for (int i = this.headers.size() - 1; i >= 0; --i) {
            hdr h2 = (hdr)this.headers.get(i);
            if (name.equalsIgnoreCase(h2.name)) {
                this.headers.add(i + 1, new hdr(name, value));
                return;
            }
            if (!h2.name.equals(":")) continue;
            pos = i;
        }
        this.headers.add(pos, new hdr(name, value));
    }

    public void removeHeader(String name) {
        for (int i = 0; i < this.headers.size(); ++i) {
            hdr h2 = (hdr)this.headers.get(i);
            if (!name.equalsIgnoreCase(h2.name)) continue;
            this.headers.remove(i);
            --i;
        }
    }

    public FinalArrayList<hdr> getAllHeaders() {
        return this.headers;
    }

    public void addHeaderLine(String line) {
        try {
            char c = line.charAt(0);
            if (c == ' ' || c == '\t') {
                hdr h2 = (hdr)this.headers.get(this.headers.size() - 1);
                h2.line = h2.line + "\r\n" + line;
            } else {
                this.headers.add(new hdr(line));
            }
        }
        catch (StringIndexOutOfBoundsException e) {
            return;
        }
        catch (NoSuchElementException noSuchElementException) {
            // empty catch block
        }
    }

    public List<String> getAllHeaderLines() {
        if (this.headerValueView == null) {
            this.headerValueView = new AbstractList<String>(){

                @Override
                public String get(int index) {
                    return ((hdr)((InternetHeaders)InternetHeaders.this).headers.get((int)index)).line;
                }

                @Override
                public int size() {
                    return InternetHeaders.this.headers.size();
                }
            };
        }
        return this.headerValueView;
    }
}

