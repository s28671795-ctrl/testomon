/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyReference;
import org.apache.neethi.PolicyRegistry;
import org.xml.sax.SAXException;

public interface PolicyBuilder {
    public PolicyReference getPolicyReference(Object var1);

    public Policy getPolicy(Object var1);

    public Policy getPolicy(InputStream var1) throws IOException, ParserConfigurationException, SAXException;

    public PolicyRegistry getPolicyRegistry();
}

