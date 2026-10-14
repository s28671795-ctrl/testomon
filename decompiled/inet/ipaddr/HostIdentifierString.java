/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.Address;
import inet.ipaddr.HostIdentifierException;
import java.io.IOException;
import java.io.Serializable;

public interface HostIdentifierString
extends Serializable {
    public static final char SEGMENT_VALUE_DELIMITER = ',';

    public String toNormalizedString();

    public void validate() throws HostIdentifierException;

    public Address getAddress();

    public Address toAddress() throws IOException, HostIdentifierException;

    public String toString();
}

