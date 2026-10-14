/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.mail.handlers;

import java.awt.datatransfer.DataFlavor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParseException;

public class TextHandler
implements DataContentHandler {
    ActivationDataFlavor dataFlavor;

    public TextHandler() {
        this.dataFlavor = new ActivationDataFlavor(String.class, "text/plain", "Text String");
    }

    public TextHandler(ActivationDataFlavor dataFlavor) {
        this.dataFlavor = dataFlavor;
    }

    protected ActivationDataFlavor getDF() {
        return this.dataFlavor;
    }

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{this.dataFlavor};
    }

    public Object getTransferData(DataFlavor dataflavor, DataSource datasource) throws IOException {
        if (this.getDF().equals(dataflavor)) {
            return this.getContent(datasource);
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object getContent(DataSource datasource) throws IOException {
        InputStream is = datasource.getInputStream();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1000];
        try {
            int count;
            while ((count = is.read(buffer, 0, buffer.length)) > 0) {
                os.write(buffer, 0, count);
            }
        }
        finally {
            is.close();
        }
        try {
            return os.toString(this.getCharSet(datasource.getContentType()));
        }
        catch (ParseException e) {
            throw new UnsupportedEncodingException(e.getMessage());
        }
    }

    public void writeTo(Object object, String contentType, OutputStream outputstream) throws IOException {
        OutputStreamWriter os;
        try {
            String charset = this.getCharSet(contentType);
            os = new OutputStreamWriter(outputstream, charset);
        }
        catch (Exception ex) {
            throw new UnsupportedEncodingException(ex.toString());
        }
        String content = (String)object;
        os.write(content, 0, content.length());
        os.flush();
    }

    protected String getCharSet(String contentType) throws ParseException {
        ContentType type = new ContentType(contentType);
        String charset = type.getParameter("charset");
        if (charset == null) {
            charset = "us-ascii";
        }
        return MimeUtility.javaCharset(charset);
    }
}

