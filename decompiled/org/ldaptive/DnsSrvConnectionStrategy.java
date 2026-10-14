/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.ldaptive.AbstractConnectionStrategy;
import org.ldaptive.LdapURL;
import org.ldaptive.LdapURLRetryMetadata;
import org.ldaptive.LdapURLSet;
import org.ldaptive.dn.Dn;
import org.ldaptive.dns.DNSContextFactory;
import org.ldaptive.dns.DNSDomainFunction;
import org.ldaptive.dns.DefaultDNSContextFactory;
import org.ldaptive.dns.SRVDNSResolver;
import org.ldaptive.dns.SRVRecord;

public class DnsSrvConnectionStrategy
extends AbstractConnectionStrategy {
    protected static final Duration DEFAULT_TTL = Duration.ofHours(6L);
    private final DNSContextFactory dnsContextFactory;
    private final Duration srvTtl;
    private final boolean useSSL;
    private String ldapUrls;
    private Map<SRVDNSResolver, String> dnsResolvers;
    private Instant expirationTime;

    public DnsSrvConnectionStrategy() {
        this(DEFAULT_TTL);
    }

    public DnsSrvConnectionStrategy(Duration ttl) {
        this(null, ttl);
    }

    public DnsSrvConnectionStrategy(DNSContextFactory factory2) {
        this(factory2, DEFAULT_TTL);
    }

    public DnsSrvConnectionStrategy(DNSContextFactory factory2, Duration ttl) {
        this(factory2, ttl, false);
    }

    public DnsSrvConnectionStrategy(DNSContextFactory factory2, Duration ttl, boolean ssl) {
        this.dnsContextFactory = factory2;
        this.srvTtl = ttl;
        this.useSSL = ssl;
    }

    @Override
    public void populate(String urls2, LdapURLSet urlSet) {
        this.ldapUrls = urls2;
        List<LdapURL> list = this.readSrvRecords(this.ldapUrls).stream().map(srv -> {
            LdapURL url2 = srv.getLdapURL();
            url2.setRetryMetadata(new LdapURLRetryMetadata(this));
            return url2;
        }).collect(Collectors.toList());
        urlSet.populate(list);
    }

    protected Set<SRVRecord> readSrvRecords(String urls2) {
        if (urls2 == null) {
            this.dnsResolvers = this.dnsContextFactory == null ? Collections.singletonMap(new SRVDNSResolver(new DefaultDNSContextFactory(new String[0]), this.useSSL), null) : Collections.singletonMap(new SRVDNSResolver(this.dnsContextFactory, this.useSSL), null);
        } else if (urls2.contains(" ")) {
            this.dnsResolvers = new HashMap<SRVDNSResolver, String>();
            for (String url2 : urls2.split(" ")) {
                String[] dnsUrl = this.parseUrl(url2);
                this.dnsResolvers.put(new SRVDNSResolver(Objects.requireNonNullElseGet(this.dnsContextFactory, () -> new DefaultDNSContextFactory(dnsUrl[0])), this.useSSL), dnsUrl[1]);
            }
        } else {
            String[] dnsUrl = this.parseUrl(urls2);
            this.dnsResolvers = this.dnsContextFactory == null ? Collections.singletonMap(new SRVDNSResolver(new DefaultDNSContextFactory(dnsUrl[0]), this.useSSL), dnsUrl[1]) : Collections.singletonMap(new SRVDNSResolver(this.dnsContextFactory, this.useSSL), dnsUrl[1]);
        }
        Set<SRVRecord> srvRecords = this.retrieveDNSRecords();
        if (srvRecords.isEmpty()) {
            this.logger.error("No SRV records found using {}", (Object)this.dnsResolvers);
            this.expirationTime = Instant.now();
        } else {
            this.expirationTime = Instant.now().plus(this.srvTtl);
        }
        return srvRecords;
    }

    protected String[] parseUrl(String url2) {
        LdapURL ldapURL;
        try {
            ldapURL = new LdapURL(url2);
        }
        catch (Exception e) {
            return this.parseDnsUrl(url2);
        }
        if (ldapURL.getBaseDn() == null || ldapURL.getBaseDn().isEmpty()) {
            throw new IllegalArgumentException("LDAP URL " + url2 + " must contain a base DN");
        }
        String domain = new DNSDomainFunction().apply(new Dn(ldapURL.getBaseDn()));
        if (domain.isEmpty()) {
            throw new IllegalArgumentException("Base DN " + ldapURL.getBaseDn() + " could not be converted to a domain");
        }
        return new String[]{null, "_ldap._tcp.".concat(domain)};
    }

    protected String[] parseDnsUrl(String url2) {
        if (!url2.contains("?")) {
            return new String[]{url2, null};
        }
        return url2.split("\\?");
    }

    @Override
    public synchronized Iterator<LdapURL> iterator() {
        if (!this.isInitialized()) {
            throw new IllegalStateException("Strategy is not initialized");
        }
        if (Instant.now().isAfter(this.expirationTime)) {
            this.populate(this.ldapUrls, this.ldapURLSet);
        }
        return new AbstractConnectionStrategy.DefaultLdapURLIterator(this.ldapURLSet.getUrls());
    }

    protected Set<SRVRecord> retrieveDNSRecords() {
        for (Map.Entry<SRVDNSResolver, String> entry : this.dnsResolvers.entrySet()) {
            try {
                Set<SRVRecord> records = entry.getKey().resolve(entry.getValue());
                if (records == null || records.isEmpty()) continue;
                return records;
            }
            catch (Exception e) {
                this.logger.error("Could not resolve SRV record {} using {}", entry.getValue(), entry.getKey(), e);
            }
        }
        return Collections.emptySet();
    }

    @Override
    public DnsSrvConnectionStrategy newInstance() {
        DnsSrvConnectionStrategy strategy = new DnsSrvConnectionStrategy(this.dnsContextFactory, this.srvTtl, this.useSSL);
        strategy.setRetryCondition(this.getRetryCondition());
        return strategy;
    }
}

