/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import java.util.Map;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.service.model.BindingOperationInfo;

public interface Retryable {
    public Object[] invoke(BindingOperationInfo var1, Object[] var2, Map<String, Object> var3, Exchange var4) throws Exception;
}

