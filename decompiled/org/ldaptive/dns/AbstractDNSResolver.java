/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.dns;

import java.util.HashSet;
import java.util.Set;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import org.ldaptive.dns.DNSContextFactory;
import org.ldaptive.dns.DNSResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDNSResolver<T>
implements DNSResolver<T> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DNSContextFactory contextFactory;

    public AbstractDNSResolver(DNSContextFactory factory2) {
        this.contextFactory = factory2;
    }

    @Override
    public Set<T> resolve(String name) {
        DirContext ctx = null;
        try {
            ctx = this.contextFactory.create();
            HashSet<String> records = new HashSet<String>();
            for (String key : this.getAttributes()) {
                this.resolveOne(ctx, name, key, records);
            }
            Set<T> results = this.processRecords(records);
            this.logger.info("Resolved {} for domain {} using {}", results, name, ctx);
            Set<T> set = results;
            return set;
        }
        catch (NamingException e) {
            throw new RuntimeException("DNS lookup failed for " + name, e);
        }
        finally {
            if (ctx != null) {
                try {
                    ctx.close();
                }
                catch (NamingException e) {
                    this.logger.warn("Error closing DirContext", e);
                }
            }
        }
    }

    protected abstract String[] getAttributes();

    protected abstract Set<T> processRecords(Set<String> var1);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void resolveOne(DirContext ctx, String name, String attrId, Set<String> records) throws NamingException {
        try (NamingEnumeration<?> en = null;){
            Attribute attr;
            Attributes attrs = ctx.getAttributes(name, new String[]{attrId});
            if (attrs != null && (attr = attrs.get(attrId)) != null) {
                en = attr.getAll();
                while (en.hasMore()) {
                    records.add((String)en.next());
                }
            }
        }
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "contextFactory=" + this.contextFactory;
    }
}

