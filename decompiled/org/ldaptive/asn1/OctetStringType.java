/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.AbstractDERType;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.DERTag;
import org.ldaptive.asn1.UniversalDERTag;

public class OctetStringType
extends AbstractDERType
implements DEREncoder {
    private final byte[] derItem;

    public OctetStringType(String item) {
        this(LdapUtils.utf8Encode(item, false));
    }

    public OctetStringType(byte[] item) {
        super(UniversalDERTag.OCTSTR);
        this.derItem = item;
    }

    public OctetStringType(DERTag tag, String item) {
        this(tag, LdapUtils.utf8Encode(item, false));
    }

    public OctetStringType(DERTag tag, byte[] item) {
        super(tag);
        if (tag.isConstructed()) {
            throw new IllegalArgumentException("DER tag must not be constructed");
        }
        this.derItem = item;
    }

    @Override
    public byte[] encode() {
        return this.encode(new byte[][]{this.derItem});
    }

    public static String decode(DERBuffer encoded) {
        return LdapUtils.utf8Encode(encoded.getRemainingBytes(), false);
    }

    public static byte[] toBytes(String s2) {
        return LdapUtils.utf8Encode(s2, false);
    }
}

