/*
 * Decompiled with CFR 0.152.
 */
package org.h2.bnf;

import java.util.HashMap;
import org.h2.bnf.Bnf;
import org.h2.bnf.BnfVisitor;
import org.h2.bnf.Rule;
import org.h2.bnf.RuleHead;
import org.h2.bnf.Sentence;

public class RuleFixed
implements Rule {
    public static final int YMD = 0;
    public static final int HMS = 1;
    public static final int NANOS = 2;
    public static final int ANY_EXCEPT_SINGLE_QUOTE = 3;
    public static final int ANY_EXCEPT_DOUBLE_QUOTE = 4;
    public static final int ANY_UNTIL_EOL = 5;
    public static final int ANY_UNTIL_END = 6;
    public static final int ANY_WORD = 7;
    public static final int ANY_EXCEPT_2_DOLLAR = 8;
    public static final int HEX_START = 9;
    public static final int OCTAL_START = 10;
    public static final int BINARY_START = 11;
    public static final int CONCAT = 12;
    public static final int AZ_UNDERSCORE = 13;
    public static final int AF = 14;
    public static final int DIGIT = 15;
    public static final int OPEN_BRACKET = 16;
    public static final int CLOSE_BRACKET = 17;
    public static final int JSON_TEXT = 18;
    private final int type;

    RuleFixed(int n) {
        this.type = n;
    }

    @Override
    public void accept(BnfVisitor bnfVisitor) {
        bnfVisitor.visitRuleFixed(this.type);
    }

    @Override
    public void setLinks(HashMap<String, RuleHead> hashMap) {
    }

    @Override
    public boolean autoComplete(Sentence sentence) {
        String string;
        sentence.stopIfRequired();
        String string2 = string = sentence.getQuery();
        boolean bl = false;
        switch (this.type) {
            case 0: {
                while (string2.length() > 0 && "0123456789-".indexOf(string2.charAt(0)) >= 0) {
                    string2 = string2.substring(1);
                }
                if (string2.length() == 0) {
                    sentence.add("2006-01-01", "1", 1);
                }
                bl = true;
                break;
            }
            case 1: {
                while (string2.length() > 0 && "0123456789:".indexOf(string2.charAt(0)) >= 0) {
                    string2 = string2.substring(1);
                }
                if (string2.length() != 0) break;
                sentence.add("12:00:00", "1", 1);
                break;
            }
            case 2: {
                while (string2.length() > 0 && Character.isDigit(string2.charAt(0))) {
                    string2 = string2.substring(1);
                }
                if (string2.length() == 0) {
                    sentence.add("nanoseconds", "0", 1);
                }
                bl = true;
                break;
            }
            case 3: {
                while (true) {
                    if (string2.length() > 0 && string2.charAt(0) != '\'') {
                        string2 = string2.substring(1);
                        continue;
                    }
                    if (!string2.startsWith("''")) break;
                    string2 = string2.substring(2);
                }
                if (string2.length() != 0) break;
                sentence.add("anything", "Hello World", 1);
                sentence.add("'", "'", 1);
                break;
            }
            case 8: {
                while (string2.length() > 0 && !string2.startsWith("$$")) {
                    string2 = string2.substring(1);
                }
                if (string2.length() != 0) break;
                sentence.add("anything", "Hello World", 1);
                sentence.add("$$", "$$", 1);
                break;
            }
            case 4: {
                while (true) {
                    if (string2.length() > 0 && string2.charAt(0) != '\"') {
                        string2 = string2.substring(1);
                        continue;
                    }
                    if (!string2.startsWith("\"\"")) break;
                    string2 = string2.substring(2);
                }
                if (string2.length() != 0) break;
                sentence.add("anything", "identifier", 1);
                sentence.add("\"", "\"", 1);
                break;
            }
            case 7: 
            case 18: {
                while (string2.length() > 0 && !Bnf.startWithSpace(string2)) {
                    string2 = string2.substring(1);
                }
                if (string2.length() != 0) break;
                sentence.add("anything", "anything", 1);
                break;
            }
            case 9: {
                if (string2.startsWith("0X") || string2.startsWith("0x")) {
                    string2 = string2.substring(2);
                    break;
                }
                if ("0".equals(string2)) {
                    sentence.add("0x", "x", 1);
                    break;
                }
                if (string2.length() != 0) break;
                sentence.add("0x", "0x", 1);
                break;
            }
            case 10: {
                if (string2.startsWith("0O") || string2.startsWith("0o")) {
                    string2 = string2.substring(2);
                    break;
                }
                if ("0".equals(string2)) {
                    sentence.add("0o", "o", 1);
                    break;
                }
                if (string2.length() != 0) break;
                sentence.add("0o", "0o", 1);
                break;
            }
            case 11: {
                if (string2.startsWith("0B") || string2.startsWith("0b")) {
                    string2 = string2.substring(2);
                    break;
                }
                if ("0".equals(string2)) {
                    sentence.add("0b", "b", 1);
                    break;
                }
                if (string2.length() != 0) break;
                sentence.add("0b", "0b", 1);
                break;
            }
            case 12: {
                if (string2.equals("|")) {
                    sentence.add("||", "|", 1);
                } else if (string2.startsWith("||")) {
                    string2 = string2.substring(2);
                } else if (string2.length() == 0) {
                    sentence.add("||", "||", 1);
                }
                bl = true;
                break;
            }
            case 13: {
                if (string2.length() > 0 && (Character.isLetter(string2.charAt(0)) || string2.charAt(0) == '_')) {
                    string2 = string2.substring(1);
                }
                if (string2.length() != 0) break;
                sentence.add("character", "A", 1);
                break;
            }
            case 14: {
                char c;
                if (string2.length() > 0 && (c = Character.toUpperCase(string2.charAt(0))) >= 'A' && c <= 'F') {
                    string2 = string2.substring(1);
                }
                if (string2.length() != 0) break;
                sentence.add("hex character", "0A", 1);
                break;
            }
            case 15: {
                if (string2.length() > 0 && Character.isDigit(string2.charAt(0))) {
                    string2 = string2.substring(1);
                }
                if (string2.length() != 0) break;
                sentence.add("digit", "1", 1);
                break;
            }
            case 16: {
                if (string2.length() == 0) {
                    sentence.add("[", "[", 1);
                } else if (string2.charAt(0) == '[') {
                    string2 = string2.substring(1);
                }
                bl = true;
                break;
            }
            case 17: {
                if (string2.length() == 0) {
                    sentence.add("]", "]", 1);
                } else if (string2.charAt(0) == ']') {
                    string2 = string2.substring(1);
                }
                bl = true;
                break;
            }
            default: {
                throw new AssertionError((Object)("type=" + this.type));
            }
        }
        if (!string2.equals(string)) {
            if (bl) {
                while (Bnf.startWithSpace(string2)) {
                    string2 = string2.substring(1);
                }
            }
            sentence.setQuery(string2);
            return true;
        }
        return false;
    }

    public String toString() {
        return "#" + this.type;
    }
}

