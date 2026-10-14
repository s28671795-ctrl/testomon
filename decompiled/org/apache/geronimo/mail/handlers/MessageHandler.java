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
import javax.mail.Message;
import javax.mail.MessageAware;
import javax.mail.MessageContext;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class MessageHandler
implements DataContentHandler {
    ActivationDataFlavor dataFlavor = new ActivationDataFlavor(String.class, "message/rfc822", "Text");

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
            if (datasource instanceof MessageAware) {
                MessageContext context = ((MessageAware)((Object)datasource)).getMessageContext();
                return new MimeMessage(context.getSession(), datasource.getInputStream());
            }
        }
        catch (MessagingException e) {
            throw new IOException("Exception writing MimeMultipart: " + e.toString());
        }
        return null;
    }

    public void writeTo(Object object, String s2, OutputStream outputstream) throws IOException {
        if (object instanceof Message) {
            try {
                ((Message)object).writeTo(outputstream);
            }
            catch (MessagingException e) {
                throw new IOException("Error parsing message: " + e.toString());
            }
        }
    }
}

