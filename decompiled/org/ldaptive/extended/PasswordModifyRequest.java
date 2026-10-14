/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.extended;

import java.util.ArrayList;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.ContextType;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.UniversalDERTag;
import org.ldaptive.extended.ExtendedRequest;

public class PasswordModifyRequest
extends ExtendedRequest {
    public static final String OID = "1.3.6.1.4.1.4203.1.11.1";

    public PasswordModifyRequest() {
        super(OID);
    }

    public PasswordModifyRequest(String identity) {
        this(identity, null, null);
    }

    public PasswordModifyRequest(String identity, String oldPass) {
        this(identity, oldPass, null);
    }

    public PasswordModifyRequest(String identity, String oldPass, String newPass) {
        super(OID);
        ArrayList<ContextType> l = new ArrayList<ContextType>();
        if (identity != null) {
            l.add(new ContextType(0, identity));
        }
        if (oldPass != null) {
            l.add(new ContextType(1, oldPass));
        }
        if (newPass != null) {
            l.add(new ContextType(2, newPass));
        }
        ConstructedDEREncoder se = new ConstructedDEREncoder(UniversalDERTag.SEQ, (DEREncoder[])l.toArray(DEREncoder[]::new));
        this.setRequestValue(se.encode());
    }
}

