/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.extended;

import org.ldaptive.asn1.AbstractParseHandler;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.asn1.DefaultDERBuffer;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.extended.ExtendedResponse;

public final class PasswordModifyResponseParser {
    private PasswordModifyResponseParser() {
    }

    public static String parse(ExtendedResponse response) {
        StringBuilder sb = new StringBuilder();
        DERParser p = new DERParser();
        p.registerHandler(GenPasswdHandler.PATH, new GenPasswdHandler(sb));
        p.parse(new DefaultDERBuffer(response.getResponseValue()));
        return sb.toString();
    }

    private static class GenPasswdHandler
    extends AbstractParseHandler<StringBuilder> {
        public static final DERPath PATH = new DERPath("/SEQ/CTX(0)");

        GenPasswdHandler(StringBuilder sb) {
            super(sb);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((StringBuilder)this.getObject()).append(OctetStringType.decode(encoded));
        }
    }
}

