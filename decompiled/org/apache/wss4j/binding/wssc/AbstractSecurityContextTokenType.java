/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.binding.wssc;

import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;

public abstract class AbstractSecurityContextTokenType {
    public abstract List<Object> getAny();

    public abstract String getId();

    public abstract void setId(String var1);

    public abstract Map<QName, String> getOtherAttributes();
}

