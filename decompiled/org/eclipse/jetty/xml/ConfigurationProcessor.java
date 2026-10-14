/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.xml;

import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.eclipse.jetty.xml.XmlParser;

public interface ConfigurationProcessor {
    public void init(Resource var1, XmlParser.Node var2, XmlConfiguration var3);

    public Object configure(Object var1) throws Exception;

    public Object configure() throws Exception;
}

