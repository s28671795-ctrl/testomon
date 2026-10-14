/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.SoapVersion;

public class SoapVersionFactory {
    private static SoapVersionFactory factory = new SoapVersionFactory();
    private Map<String, SoapVersion> versions = new HashMap<String, SoapVersion>();

    public static SoapVersionFactory getInstance() {
        return factory;
    }

    public SoapVersion getSoapVersion(String namespace) {
        return this.versions.get(namespace);
    }

    public void register(SoapVersion version) {
        this.versions.put(version.getNamespace(), version);
    }

    public Iterator<SoapVersion> getVersions() {
        return this.versions.values().iterator();
    }

    static {
        SoapVersionFactory.getInstance().register(Soap11.getInstance());
        SoapVersionFactory.getInstance().register(Soap12.getInstance());
    }
}

