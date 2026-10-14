/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.security;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import org.apache.activemq.filter.DestinationMapEntry;
import org.apache.activemq.security.DefaultAuthorizationMap;

public class AuthorizationEntry
extends DestinationMapEntry {
    private Set<Object> readACLs = this.emptySet();
    private Set<Object> writeACLs = this.emptySet();
    private Set<Object> adminACLs = this.emptySet();
    protected String adminRoles;
    protected String readRoles;
    protected String writeRoles;
    private String groupClass;

    public String getGroupClass() {
        return this.groupClass;
    }

    private Set<Object> emptySet() {
        return Collections.emptySet();
    }

    public void setGroupClass(String groupClass) {
        this.groupClass = groupClass;
    }

    public Set<Object> getAdminACLs() {
        return this.adminACLs;
    }

    public void setAdminACLs(Set<Object> adminACLs) {
        this.adminACLs = adminACLs;
    }

    public Set<Object> getReadACLs() {
        return this.readACLs;
    }

    public void setReadACLs(Set<Object> readACLs) {
        this.readACLs = readACLs;
    }

    public Set<Object> getWriteACLs() {
        return this.writeACLs;
    }

    public void setWriteACLs(Set<Object> writeACLs) {
        this.writeACLs = writeACLs;
    }

    public void setAdmin(String roles) throws Exception {
        this.adminRoles = roles;
        this.setAdminACLs(this.parseACLs(this.adminRoles));
    }

    public void setRead(String roles) throws Exception {
        this.readRoles = roles;
        this.setReadACLs(this.parseACLs(this.readRoles));
    }

    public void setWrite(String roles) throws Exception {
        this.writeRoles = roles;
        this.setWriteACLs(this.parseACLs(this.writeRoles));
    }

    protected Set<Object> parseACLs(String roles) throws Exception {
        HashSet<Object> answer = new HashSet<Object>();
        StringTokenizer iter = new StringTokenizer(roles, ",");
        while (iter.hasMoreTokens()) {
            String name = iter.nextToken().trim();
            String groupClass = this.groupClass != null ? this.groupClass : "org.apache.activemq.jaas.GroupPrincipal";
            answer.add(DefaultAuthorizationMap.createGroupPrincipal(name, groupClass));
        }
        return answer;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthorizationEntry)) {
            return false;
        }
        AuthorizationEntry that = (AuthorizationEntry)o;
        if (this.adminACLs != null ? !this.adminACLs.equals(that.adminACLs) : that.adminACLs != null) {
            return false;
        }
        if (this.adminRoles != null ? !this.adminRoles.equals(that.adminRoles) : that.adminRoles != null) {
            return false;
        }
        if (this.groupClass != null ? !this.groupClass.equals(that.groupClass) : that.groupClass != null) {
            return false;
        }
        if (this.readACLs != null ? !this.readACLs.equals(that.readACLs) : that.readACLs != null) {
            return false;
        }
        if (this.readRoles != null ? !this.readRoles.equals(that.readRoles) : that.readRoles != null) {
            return false;
        }
        if (this.writeACLs != null ? !this.writeACLs.equals(that.writeACLs) : that.writeACLs != null) {
            return false;
        }
        return !(this.writeRoles != null ? !this.writeRoles.equals(that.writeRoles) : that.writeRoles != null);
    }

    public int hashCode() {
        int result = this.readACLs != null ? this.readACLs.hashCode() : 0;
        result = 31 * result + (this.writeACLs != null ? this.writeACLs.hashCode() : 0);
        result = 31 * result + (this.adminACLs != null ? this.adminACLs.hashCode() : 0);
        result = 31 * result + (this.adminRoles != null ? this.adminRoles.hashCode() : 0);
        result = 31 * result + (this.readRoles != null ? this.readRoles.hashCode() : 0);
        result = 31 * result + (this.writeRoles != null ? this.writeRoles.hashCode() : 0);
        result = 31 * result + (this.groupClass != null ? this.groupClass.hashCode() : 0);
        return result;
    }
}

