/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml.bean;

public class SubjectLocalityBean {
    private String ipAddress;
    private String dnsAddress;

    public SubjectLocalityBean() {
    }

    public SubjectLocalityBean(String ipAddress, String dnsAddress) {
        this.ipAddress = ipAddress;
        this.dnsAddress = dnsAddress;
    }

    public final String getIpAddress() {
        return this.ipAddress;
    }

    public final void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public final String getDnsAddress() {
        return this.dnsAddress;
    }

    public final void setDnsAddress(String dnsAddress) {
        this.dnsAddress = dnsAddress;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubjectLocalityBean)) {
            return false;
        }
        SubjectLocalityBean that = (SubjectLocalityBean)o;
        if (this.ipAddress == null && that.ipAddress != null) {
            return false;
        }
        if (this.ipAddress != null && !this.ipAddress.equals(that.ipAddress)) {
            return false;
        }
        if (this.dnsAddress == null && that.dnsAddress != null) {
            return false;
        }
        return this.dnsAddress == null || this.dnsAddress.equals(that.dnsAddress);
    }

    public int hashCode() {
        int result = 0;
        if (this.ipAddress != null) {
            result = 31 * result + this.ipAddress.hashCode();
        }
        if (this.dnsAddress != null) {
            result = 31 * result + this.dnsAddress.hashCode();
        }
        return result;
    }
}

