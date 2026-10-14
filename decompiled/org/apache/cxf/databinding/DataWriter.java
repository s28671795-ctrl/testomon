/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.databinding;

import java.util.Collection;
import javax.xml.validation.Schema;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.service.model.MessagePartInfo;

public interface DataWriter<T> {
    public static final String ENDPOINT = DataWriter.class.getName() + "Endpoint";

    public void setSchema(Schema var1);

    public void setAttachments(Collection<Attachment> var1);

    public void setProperty(String var1, Object var2);

    public void write(Object var1, T var2);

    public void write(Object var1, MessagePartInfo var2, T var3);
}

