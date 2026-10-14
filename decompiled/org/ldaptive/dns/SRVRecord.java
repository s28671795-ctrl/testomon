/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.dns;

import org.ldaptive.LdapURL;
import org.ldaptive.LdapUtils;

public class SRVRecord {
    private static final int HASH_CODE_SEED = 1201;
    private final long priority;
    private final long weight;
    private final int port;
    private final String target;
    private final boolean useSSL;

    public SRVRecord(String record, boolean ssl) {
        String[] parts = record.split(" ");
        int i = 0;
        this.priority = Long.parseLong(parts[i++]);
        this.weight = Long.parseLong(parts[i++]);
        this.port = Integer.parseInt(parts[i++]);
        this.target = parts[i].endsWith(".") ? parts[i].substring(0, parts[i].length() - 1) : parts[i];
        this.useSSL = ssl;
    }

    public long getPriority() {
        return this.priority;
    }

    public long getWeight() {
        return this.weight;
    }

    public int getPort() {
        return this.port;
    }

    public String getTarget() {
        return this.target;
    }

    public LdapURL getLdapURL() {
        if (this.useSSL) {
            return new LdapURL("ldaps://" + this.target + ":" + this.port);
        }
        return new LdapURL("ldap://" + this.target + ":" + this.port);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof SRVRecord) {
            SRVRecord v = (SRVRecord)o;
            return LdapUtils.areEqual(this.priority, v.priority) && LdapUtils.areEqual(this.weight, v.weight) && LdapUtils.areEqual(this.port, v.port) && LdapUtils.areEqual(this.target, v.target) && LdapUtils.areEqual(this.useSSL, v.useSSL);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(1201, this.priority, this.weight, this.port, this.target, this.useSSL);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "priority=" + this.priority + ", " + "weight=" + this.weight + ", " + "port=" + this.port + ", " + "target=" + this.target + ", " + "useSSL=" + this.useSSL + "]";
    }
}

