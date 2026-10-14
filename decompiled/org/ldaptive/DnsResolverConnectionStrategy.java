/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.ldaptive.AbstractConnectionStrategy;
import org.ldaptive.LdapURL;
import org.ldaptive.LdapURLRetryMetadata;
import org.ldaptive.LdapURLSet;

public class DnsResolverConnectionStrategy
extends AbstractConnectionStrategy {
    protected static final Duration DEFAULT_TTL = Duration.ofHours(6L);
    private final Function<List<LdapURL>, Iterator<LdapURL>> iterFunction;
    private final Duration dnsTtl;
    private Function<String, InetAddress[]> resolverFunction = name -> {
        try {
            return InetAddress.getAllByName(name);
        }
        catch (UnknownHostException e) {
            throw new IllegalStateException("Could not resolve IP address for " + name, e);
        }
    };
    private String ldapUrls;
    private Instant expirationTime;

    public DnsResolverConnectionStrategy() {
        this(DEFAULT_TTL);
    }

    public DnsResolverConnectionStrategy(Duration ttl) {
        this(null, ttl);
    }

    public DnsResolverConnectionStrategy(Function<List<LdapURL>, Iterator<LdapURL>> function) {
        this(function, DEFAULT_TTL);
    }

    public DnsResolverConnectionStrategy(Function<List<LdapURL>, Iterator<LdapURL>> function, Duration ttl) {
        this.iterFunction = function;
        this.dnsTtl = ttl;
    }

    public Function<String, InetAddress[]> getResolverFunction() {
        return this.resolverFunction;
    }

    public void setResolverFunction(Function<String, InetAddress[]> func) {
        this.resolverFunction = func;
    }

    @Override
    public Iterator<LdapURL> iterator() {
        if (!this.isInitialized()) {
            throw new IllegalStateException("Strategy is not initialized");
        }
        if (Instant.now().isAfter(this.expirationTime)) {
            this.populate(this.ldapUrls, this.ldapURLSet);
        }
        if (this.iterFunction != null) {
            return this.iterFunction.apply(this.ldapURLSet.getUrls());
        }
        return new AbstractConnectionStrategy.DefaultLdapURLIterator(this.ldapURLSet.getUrls());
    }

    @Override
    public void populate(String urls2, LdapURLSet urlSet) {
        if (urls2 == null || urls2.isEmpty()) {
            throw new IllegalArgumentException("urls cannot be empty or null");
        }
        this.ldapUrls = urls2;
        if (urls2.contains(" ")) {
            urlSet.populate(Stream.of(urls2.split(" ")).flatMap(s2 -> {
                ArrayList<LdapURL> l = new ArrayList<LdapURL>(2);
                LdapURL parsedUrl = new LdapURL((String)s2);
                for (InetAddress address : this.resolverFunction.apply(parsedUrl.getHostname())) {
                    LdapURL url2 = LdapURL.copy(parsedUrl);
                    url2.setRetryMetadata(new LdapURLRetryMetadata(this));
                    url2.setInetAddress(address);
                    l.add(url2);
                }
                return l.stream();
            }).collect(Collectors.toList()));
        } else {
            LdapURL parsedUrl = new LdapURL(urls2);
            urlSet.populate(Stream.of(this.resolverFunction.apply(parsedUrl.getHostname())).map(ip2 -> {
                LdapURL url2 = LdapURL.copy(parsedUrl);
                url2.setRetryMetadata(new LdapURLRetryMetadata(this));
                url2.setInetAddress((InetAddress)ip2);
                return url2;
            }).collect(Collectors.toList()));
        }
        this.expirationTime = Instant.now().plus(this.dnsTtl);
    }

    @Override
    public DnsResolverConnectionStrategy newInstance() {
        DnsResolverConnectionStrategy strategy = new DnsResolverConnectionStrategy(this.iterFunction, this.dnsTtl);
        strategy.setResolverFunction(this.resolverFunction);
        strategy.setRetryCondition(this.getRetryCondition());
        return strategy;
    }
}

