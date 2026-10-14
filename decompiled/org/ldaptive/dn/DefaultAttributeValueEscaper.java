/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.dn;

import org.ldaptive.LdapUtils;
import org.ldaptive.dn.AttributeValueEscaper;

public class DefaultAttributeValueEscaper
implements AttributeValueEscaper {
    @Override
    public String escape(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        int len = value.length();
        StringBuilder sb = new StringBuilder(len);
        block5: for (int i = 0; i < len; ++i) {
            char ch = value.charAt(i);
            switch (ch) {
                case '\"': 
                case '#': 
                case '+': 
                case ',': 
                case ';': 
                case '<': 
                case '=': 
                case '>': 
                case '\\': {
                    sb.append('\\').append(ch);
                    continue block5;
                }
                case ' ': {
                    if (i == 0 || i + 1 == len) {
                        sb.append('\\').append(ch);
                        continue block5;
                    }
                    sb.append(ch);
                    continue block5;
                }
                case '\u0000': {
                    sb.append("\\00");
                    continue block5;
                }
                default: {
                    if (ch > '\u001f' && ch < '\u007f') {
                        sb.append(ch);
                        continue block5;
                    }
                    if (i + 1 < len && Character.isHighSurrogate(ch)) {
                        char[] hex;
                        char nextChar;
                        if (Character.isLowSurrogate(nextChar = value.charAt(++i))) {
                            int codePoint = Character.toCodePoint(ch, nextChar);
                            hex = LdapUtils.hexEncode(LdapUtils.utf8Encode(new String(new int[]{codePoint}, 0, 1)));
                        } else {
                            hex = LdapUtils.hexEncode(ch);
                        }
                        this.escapeHex(sb, hex);
                        continue block5;
                    }
                    this.escapeHex(sb, LdapUtils.hexEncode(ch));
                }
            }
        }
        return sb.toString();
    }

    private void escapeHex(StringBuilder sb, char ... hex) {
        for (int i = 0; i < hex.length; i += 2) {
            sb.append('\\').append(hex[i]).append(hex[i + 1]);
        }
    }
}

