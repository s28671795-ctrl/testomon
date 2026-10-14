/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.dns;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import org.ldaptive.dns.AbstractDNSResolver;
import org.ldaptive.dns.DNSContextFactory;
import org.ldaptive.dns.DefaultDNSContextFactory;
import org.ldaptive.dns.SRVRecord;

public class SRVDNSResolver
extends AbstractDNSResolver<SRVRecord> {
    private static final String[] ATTRIBUTES = new String[]{"SRV"};
    private static final String DEFAULT_RECORD_NAME = "_ldap._tcp";
    private boolean useSSL;

    public SRVDNSResolver() {
        this(new DefaultDNSContextFactory(new String[0]));
    }

    public SRVDNSResolver(DNSContextFactory factory2) {
        this(factory2, false);
    }

    public SRVDNSResolver(DNSContextFactory factory2, boolean ssl) {
        super(factory2);
        this.useSSL = ssl;
    }

    @Override
    public Set<SRVRecord> resolve(String name) {
        if (name == null) {
            return super.resolve(DEFAULT_RECORD_NAME);
        }
        return super.resolve(name);
    }

    @Override
    protected String[] getAttributes() {
        return ATTRIBUTES;
    }

    @Override
    protected Set<SRVRecord> processRecords(Set<String> records) {
        HashSet<SRVRecord> srvRecords = new HashSet<SRVRecord>(records.size());
        for (String record : records) {
            srvRecords.add(new SRVRecord(record, this.useSSL));
        }
        return this.sortSrvRecords(srvRecords);
    }

    protected Set<SRVRecord> sortSrvRecords(Set<SRVRecord> records) {
        TreeMap priorityRecords = new TreeMap();
        for (SRVRecord record : records) {
            Set<SRVRecord> priority;
            if (!priorityRecords.containsKey(record.getPriority())) {
                priority = new LinkedHashSet();
                priorityRecords.put(record.getPriority(), priority);
            } else {
                priority = (Set)priorityRecords.get(record.getPriority());
            }
            priority.add(record);
        }
        LinkedHashSet<SRVRecord> sortedRecords = new LinkedHashSet<SRVRecord>();
        for (Map.Entry entry : priorityRecords.entrySet()) {
            HashMap<Long, SRVRecord> weighted = new HashMap<Long, SRVRecord>();
            LinkedHashSet<SRVRecord> unweighted = new LinkedHashSet<SRVRecord>();
            long totalWeight = 0L;
            for (SRVRecord record : (Set)entry.getValue()) {
                if (record.getWeight() == 0L) {
                    unweighted.add(record);
                    continue;
                }
                weighted.put(totalWeight += record.getWeight(), record);
            }
            while (!weighted.isEmpty()) {
                SRVRecord record = null;
                Iterator i = weighted.keySet().iterator();
                long random = ThreadLocalRandom.current().nextLong(totalWeight + 1L);
                while (i.hasNext()) {
                    Long weight = (Long)i.next();
                    if (weight < random) continue;
                    record = (SRVRecord)weighted.get(weight);
                    totalWeight -= record.getWeight();
                    i.remove();
                    break;
                }
                sortedRecords.add(record);
            }
            if (unweighted.isEmpty()) continue;
            sortedRecords.addAll(unweighted);
        }
        return sortedRecords;
    }

    @Override
    public String toString() {
        return "[" + super.toString() + ", " + "useSSL=" + this.useSSL + "]";
    }
}

