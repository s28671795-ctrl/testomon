/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.databinding;

import java.util.Collection;
import javax.xml.namespace.QName;
import javax.xml.validation.Schema;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.service.model.MessagePartInfo;

public interface DataReader<T> {
    public static final String FAULT = DataReader.class.getName() + "Fault";
    public static final String ENDPOINT = DataReader.class.getName() + "Endpoint";

    public void setSchema(Schema var1);

    public void setAttachments(Collection<Attachment> var1);

    public void setProperty(String var1, Object var2);

    public Object read(T var1);

    public Object read(MessagePartInfo var1, T var2);

    public Object read(QName var1, T var2, Class<?> var3);
}

