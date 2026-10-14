/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.dn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.asn1.DefaultDERBuffer;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.asn1.ParseHandler;
import org.ldaptive.dn.DnParser;
import org.ldaptive.dn.NameValue;
import org.ldaptive.dn.RDn;

public final class DefaultDnParser
implements DnParser {
    private static final int HEX_RADIX = 16;
    private static final DERPath HEX_PATH = new DERPath("/OCTSTR[0]");

    @Override
    public List<RDn> parse(String dn) {
        if (dn.trim().isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<RDn> rdns = new ArrayList<RDn>();
        ArrayList<NameValue> nameValues = new ArrayList<NameValue>();
        int pos = 0;
        while (pos < dn.length()) {
            int[] endAttrNamePos = DefaultDnParser.readToChar(dn, new char[]{'='}, pos);
            String attrName = dn.substring(pos, endAttrNamePos[0]).trim();
            if (attrName.isEmpty()) {
                throw new IllegalArgumentException("Invalid RDN: no attribute name found for " + dn);
            }
            if (attrName.contains("+") || attrName.contains(",")) {
                throw new IllegalArgumentException("Invalid RDN: unexpected '" + attrName.charAt(0) + "' for " + dn);
            }
            pos = endAttrNamePos[0];
            if (pos >= dn.length() || dn.charAt(pos++) != '=') {
                throw new IllegalArgumentException("Invalid RDN: no equals found for " + dn);
            }
            int[] endAttrValuePos = DefaultDnParser.readToChar(dn, new char[]{'+', ','}, pos);
            String attrValue = dn.substring(pos, endAttrValuePos[0]).trim();
            if (attrValue.isEmpty()) {
                nameValues.add(new NameValue(attrName, ""));
            } else if (attrValue.startsWith("#")) {
                DERParser parser = new DERParser();
                OctetStringHandler handler = new OctetStringHandler();
                parser.registerHandler(HEX_PATH, handler);
                String hexData = attrValue.substring(1);
                parser.parse(new DefaultDERBuffer(DefaultDnParser.decodeHexValue(hexData.toCharArray())));
                nameValues.add(new NameValue(attrName, handler.getDecodedValue()));
            } else {
                nameValues.add(new NameValue(attrName, DefaultDnParser.decodeStringValue(attrValue)));
            }
            if (endAttrValuePos[1] == -1 || endAttrValuePos[1] == 44) {
                rdns.add(new RDn(nameValues));
                nameValues.clear();
            }
            if ((pos = endAttrValuePos[0] + 1) != dn.length() || endAttrValuePos[1] == -1) continue;
            throw new IllegalArgumentException("Invalid RDN: attribute value ends with '" + endAttrValuePos[1] + "' for " + dn);
        }
        return Collections.unmodifiableList(rdns);
    }

    private static byte[] decodeHexValue(char[] value) {
        if (value == null || value.length == 0) {
            throw new IllegalArgumentException("Invalid HEX value: value cannot be null or empty");
        }
        return LdapUtils.hexDecode(value);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String decodeStringValue(String value) {
        StringBuilder sb = new StringBuilder();
        StringBuilder hexValue = new StringBuilder();
        for (int pos = 0; pos < value.length(); ++pos) {
            char c = value.charAt(pos);
            boolean appendHex = false;
            boolean appendValue = false;
            if (c == '\\') {
                if (pos + 1 < value.length()) {
                    if (Character.digit(c = value.charAt(++pos), 16) != -1) {
                        if (pos + 1 >= value.length()) throw new IllegalArgumentException("Invalid HEX value: " + c);
                        hexValue.append(c).append(value.charAt(++pos));
                        if (pos + 1 == value.length()) {
                            appendHex = true;
                        }
                    } else {
                        appendHex = hexValue.length() > 0;
                        appendValue = true;
                    }
                }
            } else {
                appendHex = hexValue.length() > 0;
                appendValue = true;
            }
            if (appendHex) {
                sb.append(LdapUtils.utf8Encode(DefaultDnParser.decodeHexValue(hexValue.toString().toCharArray())));
                hexValue.setLength(0);
            }
            if (!appendValue) continue;
            sb.append(c);
        }
        return sb.toString();
    }

    private static int[] readToChar(String s2, char[] chars, int pos) {
        int i;
        int matchChar = -1;
        int quotes = 0;
        for (i = pos; i < s2.length(); ++i) {
            boolean match = false;
            char sChar = s2.charAt(i);
            if (sChar == '\\') {
                if (++i != s2.length()) continue;
                break;
            }
            if (sChar == '\"') {
                ++quotes;
                continue;
            }
            if (quotes == 1) continue;
            for (int n : chars) {
                if (n != s2.charAt(i)) continue;
                matchChar = n;
                match = true;
                break;
            }
            if (match) break;
        }
        return new int[]{i, matchChar};
    }

    private static class OctetStringHandler
    implements ParseHandler {
        private String decoded;

        private OctetStringHandler() {
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            this.decoded = OctetStringType.decode(encoded);
        }

        public String getDecodedValue() {
            return this.decoded;
        }
    }
}

