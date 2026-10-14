/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command;

import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.util.StringUtils;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueInteger;
import org.h2.value.ValueVarbinary;
import org.h2.value.ValueVarchar;

public abstract class Token
implements Cloneable {
    static final int PARAMETER = 92;
    static final int END_OF_INPUT = 93;
    static final int LITERAL = 94;
    static final int EQUAL = 95;
    static final int BIGGER_EQUAL = 96;
    static final int BIGGER = 97;
    static final int SMALLER = 98;
    static final int SMALLER_EQUAL = 99;
    static final int NOT_EQUAL = 100;
    static final int AT = 101;
    static final int MINUS_SIGN = 102;
    static final int PLUS_SIGN = 103;
    static final int CONCATENATION = 104;
    static final int OPEN_PAREN = 105;
    static final int CLOSE_PAREN = 106;
    static final int SPATIAL_INTERSECTS = 107;
    static final int ASTERISK = 108;
    static final int COMMA = 109;
    static final int DOT = 110;
    static final int OPEN_BRACE = 111;
    static final int CLOSE_BRACE = 112;
    static final int SLASH = 113;
    static final int PERCENT = 114;
    static final int SEMICOLON = 115;
    static final int COLON = 116;
    static final int OPEN_BRACKET = 117;
    static final int CLOSE_BRACKET = 118;
    static final int TILDE = 119;
    static final int COLON_COLON = 120;
    static final int COLON_EQ = 121;
    static final int NOT_TILDE = 122;
    static final String[] TOKENS = new String[]{null, null, null, "ALL", "AND", "ANY", "ARRAY", "AS", "ASYMMETRIC", "AUTHORIZATION", "BETWEEN", "CASE", "CAST", "CHECK", "CONSTRAINT", "CROSS", "CURRENT_CATALOG", "CURRENT_DATE", "CURRENT_PATH", "CURRENT_ROLE", "CURRENT_SCHEMA", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_USER", "DAY", "DEFAULT", "DISTINCT", "ELSE", "END", "EXCEPT", "EXISTS", "FALSE", "FETCH", "FOR", "FOREIGN", "FROM", "FULL", "GROUP", "HAVING", "HOUR", "IF", "IN", "INNER", "INTERSECT", "INTERVAL", "IS", "JOIN", "KEY", "LEFT", "LIKE", "LIMIT", "LOCALTIME", "LOCALTIMESTAMP", "MINUS", "MINUTE", "MONTH", "NATURAL", "NOT", "NULL", "OFFSET", "ON", "OR", "ORDER", "PRIMARY", "QUALIFY", "RIGHT", "ROW", "ROWNUM", "SECOND", "SELECT", "SESSION_USER", "SET", "SOME", "SYMMETRIC", "SYSTEM_USER", "TABLE", "TO", "TRUE", "UESCAPE", "UNION", "UNIQUE", "UNKNOWN", "USER", "USING", "VALUE", "VALUES", "WHEN", "WHERE", "WINDOW", "WITH", "YEAR", "_ROWID_", "?", null, null, "=", ">=", ">", "<", "<=", "<>", "@", "-", "+", "||", "(", ")", "&&", "*", ",", ".", "{", "}", "/", "%", ";", ":", "[", "]", "~", "::", ":=", "!~"};
    private int start;

    Token(int n) {
        this.start = n;
    }

    final int start() {
        return this.start;
    }

    final void setStart(int n) {
        this.start = n;
    }

    final void subtractFromStart(int n) {
        this.start -= n;
    }

    abstract int tokenType();

    String asIdentifier() {
        return null;
    }

    boolean isQuoted() {
        return false;
    }

    Value value(CastDataProvider castDataProvider) {
        return null;
    }

    boolean needsUnicodeConversion() {
        return false;
    }

    void convertUnicode(int n) {
        throw DbException.getInternalError();
    }

    protected Token clone() {
        try {
            return (Token)super.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw DbException.getInternalError();
        }
    }

    static final class EndOfInputToken
    extends Token {
        EndOfInputToken(int n) {
            super(n);
        }

        @Override
        int tokenType() {
            return 93;
        }
    }

    static final class ParameterToken
    extends Token {
        int index;

        ParameterToken(int n, int n2) {
            super(n);
            this.index = n2;
        }

        @Override
        int tokenType() {
            return 92;
        }

        @Override
        String asIdentifier() {
            return "?";
        }

        int index() {
            return this.index;
        }

        public String toString() {
            return this.index == 0 ? "?" : "?" + this.index;
        }
    }

    static final class ValueToken
    extends LiteralToken {
        ValueToken(int n, Value value) {
            super(n);
            this.value = value;
        }

        @Override
        Value value(CastDataProvider castDataProvider) {
            return this.value;
        }
    }

    static final class BigintToken
    extends LiteralToken {
        private final long number;

        BigintToken(int n, long l) {
            super(n);
            this.number = l;
        }

        @Override
        Value value(CastDataProvider castDataProvider) {
            if (this.value == null) {
                this.value = ValueBigint.get(this.number);
            }
            return this.value;
        }
    }

    static final class IntegerToken
    extends LiteralToken {
        private final int number;

        IntegerToken(int n, int n2) {
            super(n);
            this.number = n2;
        }

        @Override
        Value value(CastDataProvider castDataProvider) {
            if (this.value == null) {
                this.value = ValueInteger.get(this.number);
            }
            return this.value;
        }
    }

    static final class CharacterStringToken
    extends LiteralToken {
        String string;
        private boolean unicode;

        CharacterStringToken(int n, String string, boolean bl) {
            super(n);
            this.string = string;
            this.unicode = bl;
        }

        @Override
        Value value(CastDataProvider castDataProvider) {
            if (this.value == null) {
                this.value = ValueVarchar.get(this.string, castDataProvider);
            }
            return this.value;
        }

        @Override
        boolean needsUnicodeConversion() {
            return this.unicode;
        }

        @Override
        void convertUnicode(int n) {
            if (!this.unicode) {
                throw DbException.getInternalError();
            }
            this.string = StringUtils.decodeUnicodeStringSQL(this.string, n);
            this.unicode = false;
        }
    }

    static final class BinaryStringToken
    extends LiteralToken {
        private final byte[] string;

        BinaryStringToken(int n, byte[] byArray) {
            super(n);
            this.string = byArray;
        }

        @Override
        Value value(CastDataProvider castDataProvider) {
            if (this.value == null) {
                this.value = ValueVarbinary.getNoCopy(this.string);
            }
            return this.value;
        }
    }

    static abstract class LiteralToken
    extends Token {
        Value value;

        LiteralToken(int n) {
            super(n);
        }

        @Override
        final int tokenType() {
            return 94;
        }

        public final String toString() {
            return this.value(null).getTraceSQL();
        }
    }

    static final class KeywordOrIdentifierToken
    extends Token {
        private final int type;
        private final String identifier;

        KeywordOrIdentifierToken(int n, int n2, String string) {
            super(n);
            this.type = n2;
            this.identifier = string;
        }

        @Override
        int tokenType() {
            return this.type;
        }

        @Override
        String asIdentifier() {
            return this.identifier;
        }

        public String toString() {
            return this.identifier;
        }
    }

    static final class KeywordToken
    extends Token {
        private final int type;

        KeywordToken(int n, int n2) {
            super(n);
            this.type = n2;
        }

        @Override
        int tokenType() {
            return this.type;
        }

        @Override
        String asIdentifier() {
            return TOKENS[this.type];
        }

        public String toString() {
            return TOKENS[this.type];
        }
    }

    static class IdentifierToken
    extends Token {
        private String identifier;
        private final boolean quoted;
        private boolean unicode;

        IdentifierToken(int n, String string, boolean bl, boolean bl2) {
            super(n);
            this.identifier = string;
            this.quoted = bl;
            this.unicode = bl2;
        }

        @Override
        int tokenType() {
            return 2;
        }

        @Override
        String asIdentifier() {
            return this.identifier;
        }

        @Override
        boolean isQuoted() {
            return this.quoted;
        }

        @Override
        boolean needsUnicodeConversion() {
            return this.unicode;
        }

        @Override
        void convertUnicode(int n) {
            if (!this.unicode) {
                throw DbException.getInternalError();
            }
            this.identifier = StringUtils.decodeUnicodeStringSQL(this.identifier, n);
            this.unicode = false;
        }

        public String toString() {
            return this.quoted ? StringUtils.quoteIdentifier(this.identifier) : this.identifier;
        }
    }
}

