/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.dns;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import org.ldaptive.dns.DNSContextFactory;

public class DefaultDNSContextFactory
implements DNSContextFactory {
    public static final String DNS_CONTEXT_FACTORY = "com.sun.jndi.dns.DnsContextFactory";
    public static final String DEFAULT_DNS_PROVIDER_URL = "dns:";
    private final List<String> nameservers;

    public DefaultDNSContextFactory(String ... servers) {
        this.nameservers = servers != null && servers.length > 0 ? Arrays.asList(servers) : Collections.emptyList();
    }

    @Override
    public InitialDirContext create() throws NamingException {
        HashMap<String, String> env = new HashMap<String, String>();
        env.put("java.naming.factory.initial", DNS_CONTEXT_FACTORY);
        if (this.nameservers.isEmpty()) {
            env.put("java.naming.provider.url", DEFAULT_DNS_PROVIDER_URL);
        } else {
            env.put("java.naming.provider.url", String.join((CharSequence)" ", this.nameservers));
        }
        return new InitialDirContext(new Hashtable(env));
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "nameservers=" + this.nameservers;
    }
}

