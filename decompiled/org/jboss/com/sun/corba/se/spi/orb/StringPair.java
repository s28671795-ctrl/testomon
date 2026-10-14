/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orb;

public class StringPair {
    private String first;
    private String second;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StringPair)) {
            return false;
        }
        StringPair other = (StringPair)obj;
        return this.first.equals(other.first) && this.second.equals(other.second);
    }

    public int hashCode() {
        return this.first.hashCode() ^ this.second.hashCode();
    }

    public StringPair(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return this.first;
    }

    public String getSecond() {
        return this.second;
    }
}

