/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.mail.handlers;

import java.awt.datatransfer.DataFlavor;
import java.io.IOException;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;

public class MultipartHandler
implements DataContentHandler {
    ActivationDataFlavor dataFlavor;

    public MultipartHandler() {
        this.dataFlavor = new ActivationDataFlavor(MimeMultipart.class, "multipart/mixed", "Multipart");
    }

    public MultipartHandler(ActivationDataFlavor dataFlavor) {
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

    public Object getContent(DataSource datasource) throws IOException {
        try {
            return new MimeMultipart(datasource);
        }
        catch (MessagingException e) {
            return null;
        }
    }

    public void writeTo(Object object, String s2, OutputStream outputstream) throws IOException {
        if (object instanceof MimeMultipart) {
            try {
                ((MimeMultipart)object).writeTo(outputstream);
            }
            catch (MessagingException e) {
                throw new IOException("Exception writing MimeMultipart: " + e.toString());
            }
        }
    }
}

