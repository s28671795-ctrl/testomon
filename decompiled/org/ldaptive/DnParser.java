/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.asn1.DefaultDERBuffer;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.asn1.ParseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public final class DnParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(DnParser.class);
    private static final int HEX_RADIX = 16;
    private static final DERPath HEX_PATH = new DERPath("/OCTSTR[0]");

    private DnParser() {
    }

    public static Collection<String> getValues(String dn, String name) {
        ArrayList<String> values2 = new ArrayList<String>();
        DnParser.convertDnToAttributes(dn).stream().filter(la -> la.getName().equalsIgnoreCase(name)).forEach(la -> values2.addAll(la.getStringValues()));
        return values2;
    }

    public static String getValue(String dn, String name) {
        Collection<String> values2 = DnParser.getValues(dn, name);
        if (values2.isEmpty()) {
            return "";
        }
        return values2.iterator().next();
    }

    public static String substring(String dn, int beginIndex) {
        if (beginIndex < 0) {
            throw new IndexOutOfBoundsException("beginIndex cannot be negative");
        }
        List<LdapAttribute> attrs = DnParser.convertDnToAttributes(dn);
        if (beginIndex >= attrs.size()) {
            throw new IndexOutOfBoundsException("beginIndex cannot be larger than the number of RDNs");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < attrs.size(); ++i) {
            if (i < beginIndex) continue;
            LdapAttribute la = attrs.get(i);
            sb.append(la.getName()).append("=").append(la.getStringValue()).append(",");
        }
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static String substring(String dn, int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            throw new IndexOutOfBoundsException("beginIndex cannot be negative");
        }
        if (beginIndex > endIndex) {
            throw new IndexOutOfBoundsException("beginIndex cannot be larger than endIndex");
        }
        List<LdapAttribute> attrs = DnParser.convertDnToAttributes(dn);
        if (endIndex > attrs.size()) {
            throw new IndexOutOfBoundsException("endIndex cannot be larger than the number of RDNs");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < attrs.size(); ++i) {
            if (i < beginIndex || i >= endIndex) continue;
            LdapAttribute la = attrs.get(i);
            sb.append(la.getName()).append("=").append(la.getStringValue()).append(",");
        }
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static List<LdapAttribute> convertDnToAttributes(String dn) {
        LOGGER.trace("parsing DN: {}", (Object)dn);
        ArrayList<LdapAttribute> attributes = new ArrayList<LdapAttribute>();
        if (dn.isEmpty()) {
            return attributes;
        }
        int pos = 0;
        while (pos < dn.length()) {
            int endAttrNamePos = DnParser.readToChar(dn, new char[]{'='}, pos);
            String attrName = dn.substring(pos, endAttrNamePos);
            LOGGER.trace("read attribute name: [{}]", (Object)attrName);
            pos = endAttrNamePos;
            if (pos >= dn.length() || dn.charAt(pos++) != '=') {
                throw new IllegalArgumentException("Invalid DN: " + dn);
            }
            int endAttrValuePos = DnParser.readToChar(dn, new char[]{'+', ','}, pos);
            String attrValue = dn.substring(pos, endAttrValuePos);
            LOGGER.trace("read attribute value: [{}]", (Object)attrValue);
            attrValue = attrValue.trim();
            if (attrValue.isEmpty()) {
                throw new IllegalArgumentException("Invalid DN: " + dn);
            }
            if (attrValue.startsWith("#")) {
                DERParser parser = new DERParser();
                OctetStringHandler handler = new OctetStringHandler();
                parser.registerHandler(HEX_PATH, handler);
                String hexData = attrValue.substring(1);
                parser.parse(new DefaultDERBuffer(DnParser.decodeHexValue(hexData.toCharArray())));
                attributes.add(new LdapAttribute(attrName.trim(), handler.getDecodedValue()));
            } else {
                attributes.add(new LdapAttribute(attrName.trim(), DnParser.decodeStringValue(attrValue)));
            }
            pos = endAttrValuePos + 1;
        }
        LOGGER.debug("Parsed DN into: {}", (Object)attributes);
        return attributes;
    }

    protected static byte[] decodeHexValue(char[] value) {
        if (value == null || value.length == 0) {
            throw new IllegalArgumentException("Invalid HEX value: value cannot be null or empty");
        }
        return LdapUtils.hexDecode(value);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected static String decodeStringValue(String value) {
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
                sb.append(LdapUtils.utf8Encode(DnParser.decodeHexValue(hexValue.toString().toCharArray())));
                hexValue.setLength(0);
            }
            if (!appendValue) continue;
            sb.append(c);
        }
        return sb.toString();
    }

    private static int readToChar(String s2, char[] chars, int pos) {
        int i;
        for (i = pos; i < s2.length(); ++i) {
            boolean match = false;
            char sChar = s2.charAt(i);
            if (sChar == '\\' && i + 1 < s2.length()) {
                ++i;
                continue;
            }
            for (char c : chars) {
                if (c != s2.charAt(i)) continue;
                match = true;
                break;
            }
            if (match) break;
        }
        return i;
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

