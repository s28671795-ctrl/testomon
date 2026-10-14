/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl11;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.cxf.ws.addressing.JAXWSAConstants;
import org.apache.cxf.wsdl.WSDLConstants;

final class NSManager {
    private final Map<String, String> cache = new HashMap<String, String>();

    NSManager() {
        this.resolveConstants(JAXWSAConstants.class);
        this.resolveConstants(WSDLConstants.class);
    }

    private void resolveConstants(Class<?> clz) {
        for (Field field2 : clz.getFields()) {
            if (!field2.getName().endsWith("_PREFIX") || !this.isPulicStaticFinal(field2)) continue;
            try {
                String prefix = (String)field2.get(clz);
                Field nsField = clz.getField("NS_" + prefix.toUpperCase());
                if (nsField == null) continue;
                this.cache.put((String)nsField.get(clz), prefix);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public String getPrefixFromNS(String namespace) {
        return this.cache.get(namespace);
    }

    private boolean isPulicStaticFinal(Field field2) {
        return field2.getModifiers() == 25;
    }

    public Set<String> getNamespaces() {
        return this.cache.keySet();
    }
}

