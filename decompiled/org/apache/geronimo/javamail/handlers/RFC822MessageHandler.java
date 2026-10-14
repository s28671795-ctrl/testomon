/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.handlers;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessageAware;
import javax.mail.MessageContext;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public class RFC822MessageHandler
implements DataContentHandler {
    protected final DataFlavor flavour = new ActivationDataFlavor(Message.class, "message/rfc822", "Message");

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{this.flavour};
    }

    public Object getTransferData(DataFlavor df, DataSource ds) throws UnsupportedFlavorException, IOException {
        return this.flavour.equals(df) ? this.getContent(ds) : null;
    }

    public Object getContent(DataSource ds) throws IOException {
        try {
            if (ds instanceof MessageAware) {
                MessageContext context = ((MessageAware)((Object)ds)).getMessageContext();
                return new MimeMessage(context.getSession(), ds.getInputStream());
            }
            return new MimeMessage(Session.getDefaultInstance(new Properties(), null), ds.getInputStream());
        }
        catch (MessagingException e) {
            throw (IOException)new IOException(e.getMessage()).initCause(e);
        }
    }

    public void writeTo(Object obj, String mimeType, OutputStream os) throws IOException {
        if (obj instanceof Message) {
            Message message = (Message)obj;
            try {
                message.writeTo(os);
            }
            catch (MessagingException e) {
                throw (IOException)new IOException(e.getMessage()).initCause(e);
            }
        }
    }
}

