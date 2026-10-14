/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.neethi.Constants;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyBuilder;
import org.apache.neethi.PolicyComponent;
import org.apache.neethi.PolicyRegistry;

public class PolicyReference
implements PolicyComponent {
    private String uri;
    private PolicyBuilder engine;

    public PolicyReference() {
    }

    public PolicyReference(PolicyBuilder p) {
        this.engine = p;
    }

    public void setURI(String uri) {
        this.uri = uri;
    }

    public String getURI() {
        return this.uri;
    }

    @Override
    public boolean equal(PolicyComponent policyComponent) {
        if (4 != policyComponent.getType()) {
            return false;
        }
        String u = ((PolicyReference)policyComponent).getURI();
        return u != null && u.length() != 0 && u.equals(this.uri);
    }

    @Override
    public short getType() {
        return 4;
    }

    public PolicyComponent normalize() {
        throw new UnsupportedOperationException("PolicyReference.normalize() is meaningless");
    }

    public PolicyComponent normalize(PolicyRegistry reg, boolean deep) {
        String key = this.getURI();
        int pos = key.indexOf("#");
        if (pos == 0) {
            key = key.substring(1);
        } else if (pos > 0) {
            key = key.substring(0, pos);
        }
        Policy policy = reg.lookup(key);
        if (policy == null) {
            policy = this.getRemoteReferencedPolicy(key);
            if (policy == null) {
                throw new RuntimeException(key + " can't be resolved");
            }
            reg.register(key, policy);
        }
        return policy.normalize(reg, deep);
    }

    @Override
    public void serialize(XMLStreamWriter writer) throws XMLStreamException {
        String namespace = Constants.findPolicyNamespace(writer);
        String wspPrefix = writer.getPrefix(namespace);
        if (wspPrefix == null) {
            wspPrefix = "wsp";
            writer.setPrefix(wspPrefix, namespace);
        }
        writer.writeStartElement(wspPrefix, "PolicyReference", namespace);
        writer.writeNamespace("wsp", namespace);
        writer.writeAttribute("URI", this.getURI());
        writer.writeEndElement();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Policy getRemoteReferencedPolicy(String u) {
        Policy policy;
        URL url2 = new URL(u);
        URLConnection connection = url2.openConnection();
        connection.setDoInput(true);
        InputStream in = connection.getInputStream();
        try {
            PolicyBuilder pe = this.engine;
            if (pe == null) {
                pe = new PolicyBuilder();
            }
            policy = pe.getPolicy(connection.getInputStream());
        }
        catch (Throwable throwable) {
            try {
                in.close();
                throw throwable;
            }
            catch (MalformedURLException mue) {
                throw new RuntimeException("Malformed uri: " + u);
            }
            catch (IOException ioe) {
                throw new RuntimeException("Cannot reach remote resource: " + u);
            }
        }
        in.close();
        return policy;
    }
}

