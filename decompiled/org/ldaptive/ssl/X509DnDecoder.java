/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.asn1.OidType;
import org.ldaptive.asn1.UniversalDERTag;
import org.ldaptive.dn.Dn;
import org.ldaptive.dn.NameValue;
import org.ldaptive.dn.RDn;

public class X509DnDecoder
implements Function<DERBuffer, Dn> {
    private static final DERPath RDN_PATH = new DERPath("/SEQ/SET");
    private static final DERPath ASSERTION_PATH = new DERPath("/SEQ");

    @Override
    public Dn apply(DERBuffer encoded) {
        ArrayList<RDn> rdns = new ArrayList<RDn>();
        DERParser parser = new DERParser();
        parser.registerHandler(RDN_PATH, (p, e) -> {
            rdns.add(new RDn(X509DnDecoder.decode(e)));
            e.position(e.limit());
        });
        parser.parse(encoded);
        return new Dn(rdns);
    }

    private static List<NameValue> decode(DERBuffer encoded) {
        ArrayList<NameValue> nameValues = new ArrayList<NameValue>();
        DERParser parser = new DERParser();
        parser.registerHandler(ASSERTION_PATH, (p, e) -> {
            if (UniversalDERTag.OID.getTagNo() != p.readTag(e).getTagNo()) {
                throw new IllegalArgumentException("Expected OID tag");
            }
            int seqLimit = e.limit();
            int oidLength = p.readLength(e);
            e.limit(e.position() + oidLength);
            String oid = OidType.decode(e);
            e.limit(seqLimit);
            p.readTag(e);
            p.readLength(e);
            nameValues.add(new NameValue(oid, e.getRemainingBytes()));
        });
        parser.parse(encoded);
        return nameValues;
    }
}

