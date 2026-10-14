/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.RDN;
import org.ldaptive.asn1.UniversalDERTag;

@Deprecated
public class DN
implements DEREncoder {
    private static final int HASH_CODE_SEED = 631;
    private final RDN[] rdns;

    public DN(Collection<RDN> names) {
        this.rdns = names.toArray(new RDN[0]);
    }

    public DN(RDN ... names) {
        this.rdns = names;
    }

    public RDN[] getRDNs() {
        return this.rdns;
    }

    @Override
    public byte[] encode() {
        ArrayList<DEREncoder> typeEncoders = new ArrayList<DEREncoder>();
        for (RDN rdn : this.rdns) {
            typeEncoders.add(rdn::encode);
        }
        ConstructedDEREncoder se = new ConstructedDEREncoder(UniversalDERTag.SEQ, typeEncoders.toArray(new DEREncoder[0]));
        return se.encode();
    }

    public static DN decode(DERBuffer encoded) {
        return new DN(RDN.decode(encoded));
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof DN) {
            DN v = (DN)o;
            return LdapUtils.areEqual(this.rdns, v.rdns);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(631, new Object[]{this.rdns});
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "rdns=" + Arrays.toString(this.rdns) + "]";
    }
}

