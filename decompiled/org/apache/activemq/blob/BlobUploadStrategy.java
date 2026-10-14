/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.blob;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.jms.JMSException;
import org.apache.activemq.command.ActiveMQBlobMessage;

public interface BlobUploadStrategy {
    public URL uploadFile(ActiveMQBlobMessage var1, File var2) throws JMSException, IOException;

    public URL uploadStream(ActiveMQBlobMessage var1, InputStream var2) throws JMSException, IOException;
}

