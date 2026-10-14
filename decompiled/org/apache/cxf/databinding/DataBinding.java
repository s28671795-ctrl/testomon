/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.databinding;

import java.util.Map;
import org.apache.cxf.databinding.DataReader;
import org.apache.cxf.databinding.DataWriter;
import org.apache.cxf.service.Service;

public interface DataBinding {
    public <T> DataReader<T> createReader(Class<T> var1);

    public <T> DataWriter<T> createWriter(Class<T> var1);

    public Class<?>[] getSupportedReaderFormats();

    public Class<?>[] getSupportedWriterFormats();

    public void initialize(Service var1);

    public Map<String, String> getDeclaredNamespaceMappings();

    public void setMtomEnabled(boolean var1);

    public boolean isMtomEnabled();

    public void setMtomThreshold(int var1);

    public int getMtomThreshold();
}

