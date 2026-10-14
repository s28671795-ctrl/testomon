/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.jms.JMSException;
import org.apache.activemq.Message;

public interface BlobMessage
extends Message {
    public InputStream getInputStream() throws IOException, JMSException;

    public URL getURL() throws MalformedURLException, JMSException;

    public String getMimeType();

    public void setMimeType(String var1);

    public String getName();

    public void setName(String var1);
}

