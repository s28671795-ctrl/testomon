/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.ldaptive.LdapException;
import org.ldaptive.auth.AggregateEntryResolver;
import org.ldaptive.auth.DnResolver;
import org.ldaptive.auth.EntryResolver;
import org.ldaptive.auth.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AggregateDnResolver
implements DnResolver {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ExecutorService service;
    private Map<String, DnResolver> dnResolvers = new HashMap<String, DnResolver>();
    private boolean allowMultipleDns;

    public AggregateDnResolver() {
        this.service = Executors.newCachedThreadPool();
    }

    public AggregateDnResolver(Map<String, DnResolver> resolvers) {
        this(resolvers, Executors.newCachedThreadPool());
    }

    public AggregateDnResolver(Map<String, DnResolver> resolvers, ExecutorService es) {
        this.setDnResolvers(resolvers);
        this.service = es;
    }

    public Map<String, DnResolver> getDnResolvers() {
        return Collections.unmodifiableMap(this.dnResolvers);
    }

    public void setDnResolvers(Map<String, DnResolver> resolvers) {
        this.logger.trace("setting dnResolvers: {}", (Object)resolvers);
        this.dnResolvers = resolvers;
    }

    public void addDnResolver(String label, DnResolver resolver2) {
        this.logger.trace("adding dnResolver: {}:{}", (Object)label, (Object)resolver2);
        this.dnResolvers.put(label, resolver2);
    }

    public boolean getAllowMultipleDns() {
        return this.allowMultipleDns;
    }

    public void setAllowMultipleDns(boolean b) {
        this.logger.trace("setting allowMultipleDns: {}", (Object)b);
        this.allowMultipleDns = b;
    }

    public EntryResolver createEntryResolver(EntryResolver resolver2) {
        HashMap<String, EntryResolver> resolvers = new HashMap<String, EntryResolver>(this.dnResolvers.size());
        for (String label : this.dnResolvers.keySet()) {
            resolvers.put(label, resolver2);
        }
        return new AggregateEntryResolver(resolvers);
    }

    @Override
    public String resolve(User user) throws LdapException {
        ExecutorCompletionService<String> cs = new ExecutorCompletionService<String>(this.service);
        ArrayList<String> results = new ArrayList<String>(this.dnResolvers.size());
        for (Map.Entry<String, DnResolver> entry : this.dnResolvers.entrySet()) {
            cs.submit(() -> {
                String dn = ((DnResolver)entry.getValue()).resolve(user);
                this.logger.debug("DN resolver {} resolved dn {} for user {}", entry.getValue(), dn, user);
                if (dn != null && !dn.isEmpty()) {
                    return String.format("%s:%s", entry.getKey(), dn);
                }
                return null;
            });
            this.logger.debug("Submitted DN resolver {}", (Object)entry.getValue());
        }
        for (int i = 1; i <= this.dnResolvers.size(); ++i) {
            try {
                this.logger.trace("waiting on DN resolver {} of {}", (Object)i, (Object)this.dnResolvers.size());
                String dn = (String)cs.take().get();
                if (dn == null) continue;
                results.add(dn);
                continue;
            }
            catch (ExecutionException e) {
                if (e.getCause() instanceof LdapException) {
                    throw (LdapException)e.getCause();
                }
                if (e.getCause() instanceof RuntimeException) {
                    throw (RuntimeException)e.getCause();
                }
                this.logger.warn("ExecutionException thrown, ignoring", e);
                continue;
            }
            catch (InterruptedException e) {
                this.logger.warn("InterruptedException thrown, ignoring", e);
            }
        }
        if (results.size() > 1 && !this.allowMultipleDns) {
            throw new LdapException("Found more than (1) DN for: " + user);
        }
        this.logger.debug("Resolved aggregate DN {}", (Object)results);
        return results.isEmpty() ? null : (String)results.get(0);
    }

    public void shutdown() {
        this.service.shutdown();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final AggregateDnResolver object = new AggregateDnResolver();

        protected Builder() {
        }

        public Builder resolver(String label, DnResolver resolver2) {
            this.object.addDnResolver(label, resolver2);
            return this;
        }

        public AggregateDnResolver build() {
            return this.object;
        }
    }
}

