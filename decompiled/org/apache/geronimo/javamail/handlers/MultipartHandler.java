/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.handlers;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;

public class MultipartHandler
implements DataContentHandler {
    private final DataFlavor flavour = new ActivationDataFlavor(MimeMultipart.class, "multipart/mixed", "Multipart MIME");

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{this.flavour};
    }

    public Object getTransferData(DataFlavor df, DataSource ds) throws UnsupportedFlavorException, IOException {
        return this.flavour.equals(df) ? this.getContent(ds) : null;
    }

    public Object getContent(DataSource ds) throws IOException {
        try {
            return new MimeMultipart(ds);
        }
        catch (MessagingException e) {
            throw (IOException)new IOException(e.getMessage()).initCause(e);
        }
    }

    public void writeTo(Object obj, String mimeType, OutputStream os) throws IOException {
        if (obj instanceof MimeMultipart) {
            MimeMultipart mp = (MimeMultipart)obj;
            try {
                mp.writeTo(os);
            }
            catch (MessagingException e) {
                throw (IOException)new IOException(e.getMessage()).initCause(e);
            }
        }
    }
}

