/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.DataStructure;

public class BrokerId
implements DataStructure {
    public static final byte DATA_STRUCTURE_TYPE = 124;
    protected String value;

    public BrokerId() {
    }

    public BrokerId(String brokerId) {
        this.value = brokerId;
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != BrokerId.class) {
            return false;
        }
        BrokerId id = (BrokerId)o;
        return this.value.equals(id.value);
    }

    @Override
    public byte getDataStructureType() {
        return 124;
    }

    public String toString() {
        return this.value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String brokerId) {
        this.value = brokerId;
    }

    @Override
    public boolean isMarshallAware() {
        return false;
    }
}

