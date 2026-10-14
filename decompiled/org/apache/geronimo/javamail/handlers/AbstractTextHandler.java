/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.handlers;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeUtility;

public class AbstractTextHandler
implements DataContentHandler {
    private final DataFlavor flavour;

    public AbstractTextHandler(DataFlavor flavour) {
        this.flavour = flavour;
    }

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{this.flavour};
    }

    public Object getTransferData(DataFlavor dataFlavor, DataSource dataSource) throws UnsupportedFlavorException, IOException {
        return this.flavour.equals(dataFlavor) ? this.getContent(dataSource) : null;
    }

    public Object getContent(DataSource ds) throws IOException {
        int count;
        InputStreamReader reader;
        InputStream is = ds.getInputStream();
        try {
            String charset = this.getCharSet(ds.getContentType());
            reader = new InputStreamReader(is, charset);
        }
        catch (Exception ex) {
            throw new UnsupportedEncodingException(ex.toString());
        }
        StringBuffer result = new StringBuffer(1024);
        char[] buffer = new char[32768];
        while ((count = reader.read(buffer)) > 0) {
            result.append(buffer, 0, count);
        }
        return result.toString();
    }

    public void writeTo(Object o, String contentType, OutputStream outputstream) throws IOException {
        OutputStreamWriter writer;
        String s2;
        if (o instanceof String) {
            s2 = (String)o;
        } else if (o != null) {
            s2 = o.toString();
        } else {
            return;
        }
        try {
            String charset = this.getCharSet(contentType);
            writer = new OutputStreamWriter(outputstream, charset);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new UnsupportedEncodingException(ex.toString());
        }
        writer.write(s2);
        writer.flush();
    }

    protected String getCharSet(String contentType) throws Exception {
        ContentType type = new ContentType(contentType);
        String charset = type.getParameter("charset");
        if (charset == null) {
            charset = "us-ascii";
        }
        return MimeUtility.javaCharset(charset);
    }
}

