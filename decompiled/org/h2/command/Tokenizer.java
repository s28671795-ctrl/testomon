/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.ListIterator;
import org.h2.command.Token;
import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.util.StringUtils;
import org.h2.value.ValueBigint;
import org.h2.value.ValueDecfloat;
import org.h2.value.ValueNumeric;

public final class Tokenizer {
    private final CastDataProvider provider;
    private final boolean identifiersToUpper;
    private final boolean identifiersToLower;
    private final BitSet nonKeywords;

    Tokenizer(CastDataProvider castDataProvider, boolean bl, boolean bl2, BitSet bitSet) {
        this.provider = castDataProvider;
        this.identifiersToUpper = bl;
        this.identifiersToLower = bl2;
        this.nonKeywords = bitSet;
    }

    ArrayList<Token> tokenize(String string, boolean bl, BitSet bitSet) {
        ArrayList<Token> arrayList = new ArrayList<Token>();
        int n = string.length() - 1;
        boolean bl2 = false;
        int n2 = 0;
        int n3 = 0;
        block64: while (n3 <= n) {
            Token token;
            int n4 = string.charAt(n3);
            switch (n4) {
                case 33: {
                    int n5;
                    if (n3 < n) {
                        n5 = string.charAt(n3 + 1);
                        if (n5 == 61) {
                            token = new Token.KeywordToken(n3++, 100);
                            break;
                        }
                        if (n5 == 126) {
                            token = new Token.KeywordToken(n3++, 122);
                            break;
                        }
                    }
                    throw DbException.getSyntaxError(string, n3);
                }
                case 34: 
                case 96: {
                    n3 = this.readQuotedIdentifier(string, n, n3, n3, (char)n4, false, arrayList);
                    continue block64;
                }
                case 35: {
                    if (this.provider.getMode().supportPoundSymbolForColumnNames) {
                        n3 = this.readIdentifier(string, n, n3, n3, arrayList);
                        continue block64;
                    }
                    throw DbException.getSyntaxError(string, n3);
                }
                case 36: {
                    int n6;
                    int n5;
                    if (n3 < n) {
                        n5 = string.charAt(n3 + 1);
                        if (n5 == 36) {
                            n6 = n3 + 2;
                            int n7 = string.indexOf("$$", n6);
                            if (n7 < 0) {
                                throw DbException.getSyntaxError(string, n3);
                            }
                            token = new Token.CharacterStringToken(n3, string.substring(n6, n7), false);
                            n3 = n7 + 1;
                            break;
                        }
                        n3 = Tokenizer.parseParameterIndex(string, n, n3, arrayList);
                        n2 = Tokenizer.assignParameterIndex(arrayList, n2, bitSet);
                        continue block64;
                    }
                    token = new Token.ParameterToken(n3, 0);
                    break;
                }
                case 37: {
                    token = new Token.KeywordToken(n3, 114);
                    break;
                }
                case 38: {
                    if (n3 < n && string.charAt(n3 + 1) == '&') {
                        token = new Token.KeywordToken(n3++, 107);
                        break;
                    }
                    throw DbException.getSyntaxError(string, n3);
                }
                case 39: {
                    n3 = Tokenizer.readCharacterString(string, n3, n, n3, false, arrayList);
                    continue block64;
                }
                case 40: {
                    token = new Token.KeywordToken(n3, 105);
                    break;
                }
                case 41: {
                    token = new Token.KeywordToken(n3, 106);
                    if (!bl) break;
                    arrayList.add(token);
                    n = Tokenizer.skipWhitespace(string, n, n3 + 1) - 1;
                    break block64;
                }
                case 42: {
                    token = new Token.KeywordToken(n3, 108);
                    break;
                }
                case 43: {
                    token = new Token.KeywordToken(n3, 103);
                    break;
                }
                case 44: {
                    token = new Token.KeywordToken(n3, 109);
                    break;
                }
                case 45: {
                    if (n3 < n && string.charAt(n3 + 1) == '-') {
                        n3 = Tokenizer.skipSimpleComment(string, n, n3);
                        continue block64;
                    }
                    token = new Token.KeywordToken(n3, 102);
                    break;
                }
                case 46: {
                    int n5;
                    if (n3 < n && (n5 = (int)string.charAt(n3 + 1)) >= 48 && n5 <= 57) {
                        n3 = Tokenizer.readFloat(string, n3, n, n3 + 1, false, arrayList);
                        continue block64;
                    }
                    token = new Token.KeywordToken(n3, 110);
                    break;
                }
                case 47: {
                    int n5;
                    if (n3 < n) {
                        n5 = string.charAt(n3 + 1);
                        if (n5 == 42) {
                            n3 = Tokenizer.skipBracketedComment(string, n, n3);
                            continue block64;
                        }
                        if (n5 == 47) {
                            n3 = Tokenizer.skipSimpleComment(string, n, n3);
                            continue block64;
                        }
                    }
                    token = new Token.KeywordToken(n3, 113);
                    break;
                }
                case 48: {
                    if (n3 < n) {
                        switch (string.charAt(n3 + 1) & 0xFFDF) {
                            case 66: {
                                n3 = Tokenizer.readIntegerNumber(string, n3, n, n3 + 2, arrayList, "Binary number", 2);
                                continue block64;
                            }
                            case 79: {
                                n3 = Tokenizer.readIntegerNumber(string, n3, n, n3 + 2, arrayList, "Octal number", 8);
                                continue block64;
                            }
                            case 88: {
                                if (this.provider.getMode().zeroExLiteralsAreBinaryStrings) {
                                    n3 = Tokenizer.read0xBinaryString(string, n, n3 + 2, arrayList);
                                    continue block64;
                                }
                                n3 = Tokenizer.readIntegerNumber(string, n3, n, n3 + 2, arrayList, "Hex number", 16);
                                continue block64;
                            }
                        }
                    }
                }
                case 49: 
                case 50: 
                case 51: 
                case 52: 
                case 53: 
                case 54: 
                case 55: 
                case 56: 
                case 57: {
                    n3 = Tokenizer.readNumeric(string, n3, n, n3 + 1, (char)n4, arrayList);
                    continue block64;
                }
                case 58: {
                    int n5;
                    if (n3 < n) {
                        n5 = string.charAt(n3 + 1);
                        if (n5 == 58) {
                            token = new Token.KeywordToken(n3++, 120);
                            break;
                        }
                        if (n5 == 61) {
                            token = new Token.KeywordToken(n3++, 121);
                            break;
                        }
                    }
                    token = new Token.KeywordToken(n3, 116);
                    break;
                }
                case 59: {
                    token = new Token.KeywordToken(n3, 115);
                    break;
                }
                case 60: {
                    int n5;
                    if (n3 < n) {
                        n5 = string.charAt(n3 + 1);
                        if (n5 == 61) {
                            token = new Token.KeywordToken(n3++, 99);
                            break;
                        }
                        if (n5 == 62) {
                            token = new Token.KeywordToken(n3++, 100);
                            break;
                        }
                    }
                    token = new Token.KeywordToken(n3, 98);
                    break;
                }
                case 61: {
                    token = new Token.KeywordToken(n3, 95);
                    break;
                }
                case 62: {
                    if (n3 < n && string.charAt(n3 + 1) == '=') {
                        token = new Token.KeywordToken(n3++, 96);
                        break;
                    }
                    token = new Token.KeywordToken(n3, 97);
                    break;
                }
                case 63: {
                    int n5;
                    if (n3 + 1 < n && string.charAt(n3 + 1) == '?') {
                        n5 = string.charAt(n3 + 2);
                        if (n5 == 40) {
                            token = new Token.KeywordToken(n3, 117);
                            n3 += 2;
                            break;
                        }
                        if (n5 == 41) {
                            token = new Token.KeywordToken(n3, 118);
                            n3 += 2;
                            break;
                        }
                    }
                    n3 = Tokenizer.parseParameterIndex(string, n, n3, arrayList);
                    n2 = Tokenizer.assignParameterIndex(arrayList, n2, bitSet);
                    continue block64;
                }
                case 64: {
                    token = new Token.KeywordToken(n3, 101);
                    break;
                }
                case 65: 
                case 97: {
                    n3 = this.readA(string, n, n3, arrayList);
                    continue block64;
                }
                case 66: 
                case 98: {
                    n3 = this.readB(string, n, n3, arrayList);
                    continue block64;
                }
                case 67: 
                case 99: {
                    n3 = this.readC(string, n, n3, arrayList);
                    continue block64;
                }
                case 68: 
                case 100: {
                    n3 = this.readD(string, n, n3, arrayList);
                    continue block64;
                }
                case 69: 
                case 101: {
                    n3 = this.readE(string, n, n3, arrayList);
                    continue block64;
                }
                case 70: 
                case 102: {
                    n3 = this.readF(string, n, n3, arrayList);
                    continue block64;
                }
                case 71: 
                case 103: {
                    n3 = this.readG(string, n, n3, arrayList);
                    continue block64;
                }
                case 72: 
                case 104: {
                    n3 = this.readH(string, n, n3, arrayList);
                    continue block64;
                }
                case 73: 
                case 105: {
                    n3 = this.readI(string, n, n3, arrayList);
                    continue block64;
                }
                case 74: 
                case 106: {
                    n3 = this.readJ(string, n, n3, arrayList);
                    continue block64;
                }
                case 75: 
                case 107: {
                    n3 = this.readK(string, n, n3, arrayList);
                    continue block64;
                }
                case 76: 
                case 108: {
                    n3 = this.readL(string, n, n3, arrayList);
                    continue block64;
                }
                case 77: 
                case 109: {
                    n3 = this.readM(string, n, n3, arrayList);
                    continue block64;
                }
                case 78: 
                case 110: {
                    if (n3 < n && string.charAt(n3 + 1) == '\'') {
                        n3 = Tokenizer.readCharacterString(string, n3, n, n3 + 1, false, arrayList);
                        continue block64;
                    }
                    n3 = this.readN(string, n, n3, arrayList);
                    continue block64;
                }
                case 79: 
                case 111: {
                    n3 = this.readO(string, n, n3, arrayList);
                    continue block64;
                }
                case 80: 
                case 112: {
                    n3 = this.readP(string, n, n3, arrayList);
                    continue block64;
                }
                case 81: 
                case 113: {
                    n3 = this.readQ(string, n, n3, arrayList);
                    continue block64;
                }
                case 82: 
                case 114: {
                    n3 = this.readR(string, n, n3, arrayList);
                    continue block64;
                }
                case 83: 
                case 115: {
                    n3 = this.readS(string, n, n3, arrayList);
                    continue block64;
                }
                case 84: 
                case 116: {
                    n3 = this.readT(string, n, n3, arrayList);
                    continue block64;
                }
                case 85: 
                case 117: {
                    int n5;
                    if (n3 + 1 < n && string.charAt(n3 + 1) == '&') {
                        n5 = string.charAt(n3 + 2);
                        if (n5 == 34) {
                            n3 = this.readQuotedIdentifier(string, n, n3, n3 + 2, '\"', true, arrayList);
                            bl2 = true;
                            continue block64;
                        }
                        if (n5 == 39) {
                            n3 = Tokenizer.readCharacterString(string, n3, n, n3 + 2, true, arrayList);
                            bl2 = true;
                            continue block64;
                        }
                    }
                    n3 = this.readU(string, n, n3, arrayList);
                    continue block64;
                }
                case 86: 
                case 118: {
                    n3 = this.readV(string, n, n3, arrayList);
                    continue block64;
                }
                case 87: 
                case 119: {
                    n3 = this.readW(string, n, n3, arrayList);
                    continue block64;
                }
                case 88: 
                case 120: {
                    if (n3 < n && string.charAt(n3 + 1) == '\'') {
                        n3 = Tokenizer.readBinaryString(string, n3, n, n3 + 1, arrayList);
                        continue block64;
                    }
                    n3 = this.readIdentifier(string, n, n3, n3, arrayList);
                    continue block64;
                }
                case 89: 
                case 121: {
                    n3 = this.readY(string, n, n3, arrayList);
                    continue block64;
                }
                case 90: 
                case 122: {
                    n3 = this.readIdentifier(string, n, n3, n3, arrayList);
                    continue block64;
                }
                case 91: {
                    int n6;
                    int n5;
                    if (this.provider.getMode().squareBracketQuotedNames) {
                        n5 = n3 + 1;
                        n6 = string.indexOf(93, n5);
                        if (n6 < 0) {
                            throw DbException.getSyntaxError(string, n3);
                        }
                        token = new Token.IdentifierToken(n3, string.substring(n5, n6), true, false);
                        n3 = n6;
                        break;
                    }
                    token = new Token.KeywordToken(n3, 117);
                    break;
                }
                case 93: {
                    token = new Token.KeywordToken(n3, 118);
                    break;
                }
                case 95: {
                    n3 = this.read_(string, n, n3, arrayList);
                    continue block64;
                }
                case 123: {
                    token = new Token.KeywordToken(n3, 111);
                    break;
                }
                case 124: {
                    if (n3 < n && string.charAt(n3 + 1) == '|') {
                        token = new Token.KeywordToken(n3++, 104);
                        break;
                    }
                    throw DbException.getSyntaxError(string, n3);
                }
                case 125: {
                    token = new Token.KeywordToken(n3, 112);
                    break;
                }
                case 126: {
                    token = new Token.KeywordToken(n3, 119);
                    break;
                }
                default: {
                    int n6;
                    if (n4 <= 32) {
                        ++n3;
                        continue block64;
                    }
                    int n5 = n3;
                    int n8 = n6 = Character.isHighSurrogate((char)n4) ? string.codePointAt(n3++) : n4;
                    if (Character.isSpaceChar(n6)) {
                        ++n3;
                        continue block64;
                    }
                    if (Character.isJavaIdentifierStart(n6)) {
                        n3 = this.readIdentifier(string, n, n5, n3, arrayList);
                        continue block64;
                    }
                    throw DbException.getSyntaxError(string, n5);
                }
            }
            arrayList.add(token);
            ++n3;
        }
        if (bl2) {
            Tokenizer.processUescape(string, arrayList);
        }
        arrayList.add(new Token.EndOfInputToken(n + 1));
        return arrayList;
    }

    private int readIdentifier(String string, int n, int n2, int n3, ArrayList<Token> arrayList) {
        int n4 = this.findIdentifierEnd(string, n, n3);
        arrayList.add(new Token.IdentifierToken(n2, this.extractIdentifier(string, n2, n4), false, false));
        return n4;
    }

    private int readA(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = n4 == 2 ? ((string.charAt(n2 + 1) & 0xFFDF) == 83 ? 7 : 2) : (Tokenizer.eq("ALL", string, n2, n4) ? 3 : (Tokenizer.eq("AND", string, n2, n4) ? 4 : (Tokenizer.eq("ANY", string, n2, n4) ? 5 : (Tokenizer.eq("ARRAY", string, n2, n4) ? 6 : (Tokenizer.eq("ASYMMETRIC", string, n2, n4) ? 8 : (Tokenizer.eq("AUTHORIZATION", string, n2, n4) ? 9 : 2))))));
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int readB(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = Tokenizer.eq("BETWEEN", string, n2, n4) ? 10 : 2;
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int readC(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = Tokenizer.eq("CASE", string, n2, n4) ? 11 : (Tokenizer.eq("CAST", string, n2, n4) ? 12 : (Tokenizer.eq("CHECK", string, n2, n4) ? 13 : (Tokenizer.eq("CONSTRAINT", string, n2, n4) ? 14 : (Tokenizer.eq("CROSS", string, n2, n4) ? 15 : (n4 >= 12 && Tokenizer.eq("CURRENT_", string, n2, 8) ? Tokenizer.getTokenTypeCurrent(string, n2, n4) : 2)))));
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private static int getTokenTypeCurrent(String string, int n, int n2) {
        n += 8;
        switch (n2) {
            case 12: {
                if (Tokenizer.eqCurrent("CURRENT_DATE", string, n, n2)) {
                    return 17;
                }
                if (Tokenizer.eqCurrent("CURRENT_PATH", string, n, n2)) {
                    return 18;
                }
                if (Tokenizer.eqCurrent("CURRENT_ROLE", string, n, n2)) {
                    return 19;
                }
                if (Tokenizer.eqCurrent("CURRENT_TIME", string, n, n2)) {
                    return 21;
                }
                if (!Tokenizer.eqCurrent("CURRENT_USER", string, n, n2)) break;
                return 23;
            }
            case 14: {
                if (!Tokenizer.eqCurrent("CURRENT_SCHEMA", string, n, n2)) break;
                return 20;
            }
            case 15: {
                if (!Tokenizer.eqCurrent("CURRENT_CATALOG", string, n, n2)) break;
                return 16;
            }
            case 17: {
                if (!Tokenizer.eqCurrent("CURRENT_TIMESTAMP", string, n, n2)) break;
                return 22;
            }
        }
        return 2;
    }

    private static boolean eqCurrent(String string, String string2, int n, int n2) {
        for (int i = 8; i < n2; ++i) {
            if (string.charAt(i) == (string2.charAt(n++) & 0xFFDF)) continue;
            return false;
        }
        return true;
    }

    private int readD(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = Tokenizer.eq("DAY", string, n2, n4) ? 24 : (Tokenizer.eq("DEFAULT", string, n2, n4) ? 25 : (Tokenizer.eq("DISTINCT", string, n2, n4) ? 26 : 2));
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int readE(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = Tokenizer.eq("ELSE", string, n2, n4) ? 27 : (Tokenizer.eq("END", string, n2, n4) ? 28 : (Tokenizer.eq("EXCEPT", string, n2, n4) ? 29 : (Tokenizer.eq("EXISTS", string, n2, n4) ? 30 : 2)));
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int readF(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = Tokenizer.eq("FETCH", string, n2, n4) ? 32 : (Tokenizer.eq("FROM", string, n2, n4) ? 35 : (Tokenizer.eq("FOR", string, n2, n4) ? 33 : (Tokenizer.eq("FOREIGN", string, n2, n4) ? 34 : (Tokenizer.eq("FULL", string, n2, n4) ? 36 : (Tokenizer.eq("FALSE", string, n2, n4) ? 31 : 2)))));
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int readG(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = Tokenizer.eq("GROUP", string, n2, n4) ? 37 : 2;
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int readH(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = Tokenizer.eq("HAVING", string, n2, n4) ? 38 : (Tokenizer.eq("HOUR", string, n2, n4) ? 39 : 2);
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int readI(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3;
        int n4 = this.findIdentifierEnd(string, n, n2);
        int n5 = n4 - n2;
        if (n5 == 2) {
            switch (string.charAt(n2 + 1) & 0xFFDF) {
                case 70: {
                    n3 = 40;
                    break;
                }
                case 78: {
                    n3 = 41;
                    break;
                }
                case 83: {
                    n3 = 45;
                    break;
                }
                default: {
                    n3 = 2;
                    break;
                }
            }
        } else {
            n3 = Tokenizer.eq("INNER", string, n2, n5) ? 42 : (Tokenizer.eq("INTERSECT", string, n2, n5) ? 43 : (Tokenizer.eq("INTERVAL", string, n2, n5) ? 44 : 2));
        }
        return this.readIdentifierOrKeyword(string, n2, arrayList, n4, n3);
    }

    private int readJ(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = Tokenizer.eq("JOIN", string, n2, n4) ? 46 : 2;
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int readK(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = Tokenizer.eq("KEY", string, n2, n4) ? 47 : 2;
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int readL(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = Tokenizer.eq("LEFT", string, n2, n4) ? 48 : (Tokenizer.eq("LIMIT", string, n2, n4) ? (this.provider.getMode().limit ? 50 : 2) : (Tokenizer.eq("LIKE", string, n2, n4) ? 49 : (Tokenizer.eq("LOCALTIME", string, n2, n4) ? 51 : (Tokenizer.eq("LOCALTIMESTAMP", string, n2, n4) ? 52 : 2))));
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int readM(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = Tokenizer.eq("MINUS", string, n2, n4) ? (this.provider.getMode().minusIsExcept ? 53 : 2) : (Tokenizer.eq("MINUTE", string, n2, n4) ? 54 : (Tokenizer.eq("MONTH", string, n2, n4) ? 55 : 2));
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int readN(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = Tokenizer.eq("NOT", string, n2, n4) ? 57 : (Tokenizer.eq("NATURAL", string, n2, n4) ? 56 : (Tokenizer.eq("NULL", string, n2, n4) ? 58 : 2));
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int readO(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3;
        int n4 = this.findIdentifierEnd(string, n, n2);
        int n5 = n4 - n2;
        if (n5 == 2) {
            switch (string.charAt(n2 + 1) & 0xFFDF) {
                case 78: {
                    n3 = 60;
                    break;
                }
                case 82: {
                    n3 = 61;
                    break;
                }
                default: {
                    n3 = 2;
                    break;
                }
            }
        } else {
            n3 = Tokenizer.eq("OFFSET", string, n2, n5) ? 59 : (Tokenizer.eq("ORDER", string, n2, n5) ? 62 : 2);
        }
        return this.readIdentifierOrKeyword(string, n2, arrayList, n4, n3);
    }

    private int readP(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = Tokenizer.eq("PRIMARY", string, n2, n4) ? 63 : 2;
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int readQ(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = Tokenizer.eq("QUALIFY", string, n2, n4) ? 64 : 2;
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int readR(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = Tokenizer.eq("RIGHT", string, n2, n4) ? 65 : (Tokenizer.eq("ROW", string, n2, n4) ? 66 : (Tokenizer.eq("ROWNUM", string, n2, n4) ? 67 : 2));
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int readS(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = Tokenizer.eq("SECOND", string, n2, n4) ? 68 : (Tokenizer.eq("SELECT", string, n2, n4) ? 69 : (Tokenizer.eq("SESSION_USER", string, n2, n4) ? 70 : (Tokenizer.eq("SET", string, n2, n4) ? 71 : (Tokenizer.eq("SOME", string, n2, n4) ? 72 : (Tokenizer.eq("SYMMETRIC", string, n2, n4) ? 73 : (Tokenizer.eq("SYSTEM_USER", string, n2, n4) ? 74 : 2))))));
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int readT(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = n4 == 2 ? ((string.charAt(n2 + 1) & 0xFFDF) == 79 ? 76 : 2) : (Tokenizer.eq("TABLE", string, n2, n4) ? 75 : (Tokenizer.eq("TRUE", string, n2, n4) ? 77 : 2));
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int readU(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = Tokenizer.eq("UESCAPE", string, n2, n4) ? 78 : (Tokenizer.eq("UNION", string, n2, n4) ? 79 : (Tokenizer.eq("UNIQUE", string, n2, n4) ? 80 : (Tokenizer.eq("UNKNOWN", string, n2, n4) ? 81 : (Tokenizer.eq("USER", string, n2, n4) ? 82 : (Tokenizer.eq("USING", string, n2, n4) ? 83 : 2)))));
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int readV(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = Tokenizer.eq("VALUE", string, n2, n4) ? 84 : (Tokenizer.eq("VALUES", string, n2, n4) ? 85 : 2);
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int readW(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = Tokenizer.eq("WHEN", string, n2, n4) ? 86 : (Tokenizer.eq("WHERE", string, n2, n4) ? 87 : (Tokenizer.eq("WINDOW", string, n2, n4) ? 88 : (Tokenizer.eq("WITH", string, n2, n4) ? 89 : 2)));
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int readY(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2;
        int n5 = Tokenizer.eq("YEAR", string, n2, n4) ? 90 : 2;
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n5);
    }

    private int read_(String string, int n, int n2, ArrayList<Token> arrayList) {
        int n3 = this.findIdentifierEnd(string, n, n2);
        int n4 = n3 - n2 == 7 && "_ROWID_".regionMatches(true, 1, string, n2 + 1, 6) ? 91 : 2;
        return this.readIdentifierOrKeyword(string, n2, arrayList, n3, n4);
    }

    private int readIdentifierOrKeyword(String string, int n, ArrayList<Token> arrayList, int n2, int n3) {
        Token token = n3 == 2 ? new Token.IdentifierToken(n, this.extractIdentifier(string, n, n2), false, false) : (this.nonKeywords != null && this.nonKeywords.get(n3) ? new Token.KeywordOrIdentifierToken(n, n3, this.extractIdentifier(string, n, n2)) : new Token.KeywordToken(n, n3));
        arrayList.add(token);
        return n2;
    }

    private static boolean eq(String string, String string2, int n, int n2) {
        if (n2 != string.length()) {
            return false;
        }
        for (int i = 1; i < n2; ++i) {
            if (string.charAt(i) == (string2.charAt(++n) & 0xFFDF)) continue;
            return false;
        }
        return true;
    }

    private int findIdentifierEnd(String string, int n, int n2) {
        int n3;
        ++n2;
        while (n2 <= n && (Character.isJavaIdentifierPart(n3 = string.codePointAt(n2)) || n3 == 35 && this.provider.getMode().supportPoundSymbolForColumnNames)) {
            n2 += Character.charCount(n3);
        }
        return n2;
    }

    private String extractIdentifier(String string, int n, int n2) {
        return this.convertCase(string.substring(n, n2));
    }

    private int readQuotedIdentifier(String string, int n, int n2, int n3, char c, boolean bl, ArrayList<Token> arrayList) {
        int n4;
        if ((n4 = string.indexOf(c, ++n3)) < 0) {
            throw DbException.getSyntaxError(string, n2);
        }
        String string2 = string.substring(n3, n4);
        n3 = n4 + 1;
        if (n3 <= n && string.charAt(n3) == c) {
            StringBuilder stringBuilder = new StringBuilder(string2);
            do {
                if ((n4 = string.indexOf(c, n3 + 1)) < 0) {
                    throw DbException.getSyntaxError(string, n2);
                }
                stringBuilder.append(string, n3, n4);
            } while ((n3 = n4 + 1) <= n && string.charAt(n3) == c);
            string2 = stringBuilder.toString();
        }
        if (c == '`') {
            string2 = this.convertCase(string2);
        }
        arrayList.add(new Token.IdentifierToken(n2, string2, true, bl));
        return n3;
    }

    private String convertCase(String string) {
        if (this.identifiersToUpper) {
            string = StringUtils.toUpperEnglish(string);
        } else if (this.identifiersToLower) {
            string = StringUtils.toLowerEnglish(string);
        }
        return string;
    }

    private static int readBinaryString(String string, int n, int n2, int n3, ArrayList<Token> arrayList) {
        int n4;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        do {
            if ((n4 = string.indexOf(39, ++n3)) < 0 || n4 < n2 && string.charAt(n4 + 1) == '\'') {
                throw DbException.getSyntaxError(string, n);
            }
            StringUtils.convertHexWithSpacesToBytes(byteArrayOutputStream, string, n3, n4);
        } while ((n3 = Tokenizer.skipWhitespace(string, n2, n4 + 1)) <= n2 && string.charAt(n3) == '\'');
        arrayList.add(new Token.BinaryStringToken(n, byteArrayOutputStream.toByteArray()));
        return n3;
    }

    private static int readCharacterString(String string, int n, int n2, int n3, boolean bl, ArrayList<Token> arrayList) {
        String string2 = null;
        StringBuilder stringBuilder = null;
        do {
            int n4;
            if ((n4 = string.indexOf(39, ++n3)) < 0) {
                throw DbException.getSyntaxError(string, n);
            }
            if (string2 == null) {
                string2 = string.substring(n3, n4);
            } else {
                if (stringBuilder == null) {
                    stringBuilder = new StringBuilder(string2);
                }
                stringBuilder.append(string, n3, n4);
            }
            n3 = n4 + 1;
            if (n3 > n2 || string.charAt(n3) != '\'') continue;
            if (stringBuilder == null) {
                stringBuilder = new StringBuilder(string2);
            }
            do {
                if ((n4 = string.indexOf(39, n3 + 1)) < 0) {
                    throw DbException.getSyntaxError(string, n);
                }
                stringBuilder.append(string, n3, n4);
            } while ((n3 = n4 + 1) <= n2 && string.charAt(n3) == '\'');
        } while ((n3 = Tokenizer.skipWhitespace(string, n2, n3)) <= n2 && string.charAt(n3) == '\'');
        if (stringBuilder != null) {
            string2 = stringBuilder.toString();
        }
        arrayList.add(new Token.CharacterStringToken(n, string2, bl));
        return n3;
    }

    private static int skipWhitespace(String string, int n, int n2) {
        while (n2 <= n) {
            int n3 = string.codePointAt(n2);
            if (!Character.isWhitespace(n3)) {
                if (n3 != 47 || n2 >= n) break;
                char c = string.charAt(n2 + 1);
                if (c == '*') {
                    n2 = Tokenizer.skipBracketedComment(string, n, n2);
                    continue;
                }
                if (c != '/') break;
                n2 = Tokenizer.skipSimpleComment(string, n, n2);
                continue;
            }
            n2 += Character.charCount(n3);
        }
        return n2;
    }

    private static int read0xBinaryString(String string, int n, int n2, ArrayList<Token> arrayList) {
        char c;
        int n3 = n2;
        while (n2 <= n && ((c = string.charAt(n2)) >= '0' && c <= '9' || (c = (char)(c & 0xFFDF)) >= 'A' && c <= 'F')) {
            ++n2;
        }
        if (n2 <= n && Character.isJavaIdentifierPart(string.codePointAt(n2))) {
            throw DbException.get(90004, string.substring(n3, n2 + 1));
        }
        arrayList.add(new Token.BinaryStringToken(n3, StringUtils.convertHexToBytes(string.substring(n3, n2))));
        return n2;
    }

    private static int readIntegerNumber(String string, int n, int n2, int n3, ArrayList<Token> arrayList, String string2, int n4) {
        boolean bl;
        char c;
        int n5;
        char c2;
        if (n3 > n2) {
            throw DbException.getSyntaxError(string, n, string2);
        }
        if (n4 > 10) {
            c2 = '9';
            n5 = 54 + n4;
        } else {
            c2 = 47 + n4;
            n5 = -1;
        }
        int n6 = n3;
        long l = 0L;
        int n7 = Integer.MIN_VALUE;
        do {
            if ((c = string.charAt(n3)) >= '0' && c <= c2) {
                l = l * (long)n4 + (long)c - 48L;
            } else {
                if (c == '_') {
                    if (n7 == n3 - 1) {
                        throw DbException.getSyntaxError(string, n, string2);
                    }
                    n7 = n3;
                    continue;
                }
                if (n5 >= 0 && (c = (char)(c & 0xFFDF)) >= 'A' && c <= n5) {
                    l = l * (long)n4 + (long)c - 55L;
                } else {
                    if (n3 != n6) break;
                    throw DbException.getSyntaxError(string, n, string2);
                }
            }
            if (l <= Integer.MAX_VALUE) continue;
            while (++n3 <= n2) {
                c = string.charAt(n3);
                if (c >= '0' && c <= c2) continue;
                if (c == '_') {
                    if (n7 == n3 - 1) {
                        throw DbException.getSyntaxError(string, n, string2);
                    }
                    n7 = n3;
                    continue;
                }
                if (n5 >= 0 && (c = (char)(c & 0xFFDF)) >= 'A' && c <= 'F') continue;
            }
            if (n7 == n3 - 1) {
                throw DbException.getSyntaxError(string, n, string2);
            }
            return Tokenizer.finishBigInteger(string, n, n2, n3, n6, n3 <= n2 && c == 'L', n7 >= 0, n4, arrayList);
        } while (++n3 <= n2);
        if (n7 == n3 - 1) {
            throw DbException.getSyntaxError(string, n, string2);
        }
        boolean bl2 = bl = n3 <= n2 && c == 'L';
        if (bl) {
            ++n3;
        }
        if (n3 <= n2 && Character.isJavaIdentifierPart(string.codePointAt(n3))) {
            throw DbException.getSyntaxError(string, n, string2);
        }
        arrayList.add(bl ? new Token.BigintToken(n6, l) : new Token.IntegerToken(n6, (int)l));
        return n3;
    }

    private static int readNumeric(String string, int n, int n2, int n3, char c, ArrayList<Token> arrayList) {
        long l = c - 48;
        int n4 = Integer.MIN_VALUE;
        while (n3 <= n2) {
            block19: {
                block18: {
                    c = string.charAt(n3);
                    if (c >= '0' && c <= '9') break block18;
                    if (n4 == n3 - 1) {
                        throw DbException.getSyntaxError(string, n, "Numeric");
                    }
                    switch (c) {
                        case '.': {
                            return Tokenizer.readFloat(string, n, n2, n3, n4 >= 0, arrayList);
                        }
                        case 'E': 
                        case 'e': {
                            return Tokenizer.readApproximateNumeric(string, n, n2, n3, n4 >= 0, arrayList);
                        }
                        case 'L': 
                        case 'l': {
                            return Tokenizer.finishBigInteger(string, n, n2, n3, n, true, n4 >= 0, 10, arrayList);
                        }
                        case '_': {
                            n4 = n3;
                            break block19;
                        }
                    }
                    break;
                }
                if ((l = l * 10L + (long)(c - 48)) > Integer.MAX_VALUE) {
                    block12: while (++n3 <= n2) {
                        c = string.charAt(n3);
                        if (c >= '0' && c <= '9') continue;
                        if (n4 == n3 - 1) {
                            throw DbException.getSyntaxError(string, n, "Numeric");
                        }
                        switch (c) {
                            case '.': {
                                return Tokenizer.readFloat(string, n, n2, n3, n4 >= 0, arrayList);
                            }
                            case 'E': 
                            case 'e': {
                                return Tokenizer.readApproximateNumeric(string, n, n2, n3, n4 >= 0, arrayList);
                            }
                            case '_': {
                                n4 = n3;
                                continue block12;
                            }
                        }
                    }
                    if (n4 == n3 - 1) {
                        throw DbException.getSyntaxError(string, n, "Numeric");
                    }
                    return Tokenizer.finishBigInteger(string, n, n2, n3, n, c == 'L' || c == 'l', n4 >= 0, 10, arrayList);
                }
            }
            ++n3;
        }
        if (n4 == n3 - 1) {
            throw DbException.getSyntaxError(string, n, "Numeric");
        }
        arrayList.add(new Token.IntegerToken(n, (int)l));
        return n3;
    }

    private static int readFloat(String string, int n, int n2, int n3, boolean bl, ArrayList<Token> arrayList) {
        int n4 = n3 + 1;
        int n5 = Integer.MIN_VALUE;
        block4: while (++n3 <= n2) {
            char c = string.charAt(n3);
            if (c >= '0' && c <= '9') continue;
            if (n5 == n3 - 1) {
                throw DbException.getSyntaxError(string, n, "Numeric");
            }
            switch (c) {
                case 'E': 
                case 'e': {
                    return Tokenizer.readApproximateNumeric(string, n, n2, n3, bl, arrayList);
                }
                case '_': {
                    if (n3 == n4) {
                        throw DbException.getSyntaxError(string, n, "Numeric");
                    }
                    n5 = n3;
                    bl = true;
                    continue block4;
                }
            }
            break;
        }
        if (n5 == n3 - 1) {
            throw DbException.getSyntaxError(string, n, "Numeric");
        }
        arrayList.add(new Token.ValueToken(n, ValueNumeric.get(Tokenizer.readBigDecimal(string, n, n3, bl))));
        return n3;
    }

    private static int readApproximateNumeric(String string, int n, int n2, int n3, boolean bl, ArrayList<Token> arrayList) {
        char c;
        if (n3 == n2) {
            throw DbException.getSyntaxError(string, n, "Approximate numeric");
        }
        if ((c = string.charAt(++n3)) == '+' || c == '-') {
            if (n3 == n2) {
                throw DbException.getSyntaxError(string, n, "Approximate numeric");
            }
            c = string.charAt(++n3);
        }
        if (c < '0' || c > '9') {
            throw DbException.getSyntaxError(string, n, "Approximate numeric");
        }
        int n4 = Integer.MIN_VALUE;
        while (++n3 <= n2) {
            c = string.charAt(n3);
            if (c >= '0' && c <= '9') continue;
            if (n4 == n3 - 1) {
                throw DbException.getSyntaxError(string, n, "Approximate numeric");
            }
            if (c != '_') break;
            n4 = n3;
            bl = true;
        }
        if (n4 == n3 - 1) {
            throw DbException.getSyntaxError(string, n, "Approximate numeric");
        }
        arrayList.add(new Token.ValueToken(n, ValueDecfloat.get(Tokenizer.readBigDecimal(string, n, n3, bl))));
        return n3;
    }

    private static BigDecimal readBigDecimal(String string, int n, int n2, boolean bl) {
        BigDecimal bigDecimal;
        String string2 = Tokenizer.readAndRemoveUnderscores(string, n, n2, bl);
        try {
            bigDecimal = new BigDecimal(string2);
        }
        catch (NumberFormatException numberFormatException) {
            throw DbException.getSyntaxError(string, n, "Numeric");
        }
        return bigDecimal;
    }

    private static int finishBigInteger(String string, int n, int n2, int n3, int n4, boolean bl, boolean bl2, int n5, ArrayList<Token> arrayList) {
        Token.LiteralToken literalToken;
        int n6 = n3++;
        if (bl) {
            // empty if block
        }
        if (n5 == 16 && n3 <= n2 && Character.isJavaIdentifierPart(string.codePointAt(n3))) {
            throw DbException.getSyntaxError(string, n, "Hex number");
        }
        BigInteger bigInteger = new BigInteger(Tokenizer.readAndRemoveUnderscores(string, n4, n6, bl2), n5);
        if (bigInteger.compareTo(ValueBigint.MAX_BI) > 0) {
            if (bl) {
                throw DbException.getSyntaxError(string, n, "BIGINT");
            }
            literalToken = new Token.ValueToken(n, ValueNumeric.get(bigInteger));
        } else {
            literalToken = new Token.BigintToken(n, bigInteger.longValue());
        }
        arrayList.add(literalToken);
        return n3;
    }

    private static String readAndRemoveUnderscores(String string, int n, int n2, boolean bl) {
        if (!bl) {
            return string.substring(n, n2);
        }
        StringBuilder stringBuilder = new StringBuilder(n2 - n - 1);
        while (n < n2) {
            char c = string.charAt(n);
            if (c != '_') {
                stringBuilder.append(c);
            }
            ++n;
        }
        return stringBuilder.toString();
    }

    private static int skipBracketedComment(String string, int n, int n2) {
        int n3 = n2;
        n2 += 2;
        int n4 = 1;
        block0: while (n4 > 0) {
            while (true) {
                char c;
                if (n2 >= n) {
                    throw DbException.getSyntaxError(string, n3);
                }
                if ((c = string.charAt(n2++)) == '*') {
                    if (string.charAt(n2) != '/') continue;
                    --n4;
                    ++n2;
                    continue block0;
                }
                if (c != '/' || string.charAt(n2) != '*') continue;
                ++n4;
                ++n2;
            }
        }
        return n2;
    }

    private static int skipSimpleComment(String string, int n, int n2) {
        char c;
        n2 += 2;
        while (n2 <= n && (c = string.charAt(n2)) != '\n' && c != '\r') {
            ++n2;
        }
        return n2;
    }

    private static int parseParameterIndex(String string, int n, int n2, ArrayList<Token> arrayList) {
        char c;
        int n3 = n2;
        long l = 0L;
        while (++n2 <= n && (c = string.charAt(n2)) >= '0' && c <= '9') {
            if ((l = l * 10L + (long)(c - 48)) <= Integer.MAX_VALUE) continue;
            throw DbException.getInvalidValueException("parameter index", l);
        }
        if (n2 > n3 + 1 && l == 0L) {
            throw DbException.getInvalidValueException("parameter index", l);
        }
        arrayList.add(new Token.ParameterToken(n3, (int)l));
        return n2;
    }

    private static int assignParameterIndex(ArrayList<Token> arrayList, int n, BitSet bitSet) {
        Token.ParameterToken parameterToken = (Token.ParameterToken)arrayList.get(arrayList.size() - 1);
        int n2 = parameterToken.index;
        if (n2 == 0) {
            if (n < 0) {
                throw DbException.get(90123);
            }
            parameterToken.index = n2 = ++n;
        } else {
            if (n > 0) {
                throw DbException.get(90123);
            }
            n = -1;
        }
        bitSet.set(n2 - 1);
        return n;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void processUescape(String string, ArrayList<Token> arrayList) {
        ListIterator<Token> listIterator = arrayList.listIterator();
        while (listIterator.hasNext()) {
            Token token;
            Token token2 = listIterator.next();
            if (!token2.needsUnicodeConversion()) continue;
            int n = 92;
            if (listIterator.hasNext() && (token = listIterator.next()).tokenType() == 78) {
                int n2;
                String string2;
                listIterator.remove();
                if (!listIterator.hasNext()) throw DbException.getSyntaxError(string, token.start() + 7, "'<Unicode escape character>'");
                Token token3 = listIterator.next();
                listIterator.remove();
                if (!(token3 instanceof Token.CharacterStringToken) || (string2 = ((Token.CharacterStringToken)token3).string).codePointCount(0, string2.length()) != 1 || Character.isWhitespace(n2 = string2.codePointAt(0)) || n2 >= 48 && n2 <= 57 || n2 >= 65 && n2 <= 70 || n2 >= 97 && n2 <= 102) throw DbException.getSyntaxError(string, token.start() + 7, "'<Unicode escape character>'");
                switch (n2) {
                    default: {
                        n = n2;
                        break;
                    }
                    case 34: 
                    case 39: 
                    case 43: {
                        throw DbException.getSyntaxError(string, token.start() + 7, "'<Unicode escape character>'");
                    }
                }
            }
            token2.convertUnicode(n);
        }
    }
}

