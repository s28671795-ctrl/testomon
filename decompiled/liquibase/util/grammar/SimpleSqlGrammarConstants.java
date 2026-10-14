/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util.grammar;

public interface SimpleSqlGrammarConstants {
    public static final int EOF = 0;
    public static final int WHITESPACE = 1;
    public static final int LINE_COMMENT = 2;
    public static final int MULTI_LINE_COMMENT = 3;
    public static final int S_DOUBLE = 4;
    public static final int S_LONG = 5;
    public static final int DIGIT = 6;
    public static final int COMPLEX_IDENTIFIER = 7;
    public static final int S_IDENTIFIER = 8;
    public static final int LETTER = 9;
    public static final int FIRST_CHAR_SPECIAL_CHARS = 10;
    public static final int SPECIAL_CHARS = 11;
    public static final int ESC_S_QUOTE = 12;
    public static final int ESC_D_QUOTE = 13;
    public static final int ESC_ANY_CHAR = 14;
    public static final int S_QUOTED_STRING_HYBRID = 15;
    public static final int D_QUOTED_STRING_HYBRID = 16;
    public static final int S_CHAR_LITERAL = 17;
    public static final int S_QUOTED_IDENTIFIER = 18;
    public static final int EMPTY_QUOTE = 19;
    public static final int UNICODE_LETTERS = 20;
    public static final int SYMBOL = 21;
    public static final int DEFAULT = 0;
    public static final String[] tokenImage = new String[]{"<EOF>", "<WHITESPACE>", "<LINE_COMMENT>", "<MULTI_LINE_COMMENT>", "<S_DOUBLE>", "<S_LONG>", "<DIGIT>", "<COMPLEX_IDENTIFIER>", "<S_IDENTIFIER>", "<LETTER>", "<FIRST_CHAR_SPECIAL_CHARS>", "<SPECIAL_CHARS>", "\"\\'\\'\"", "\"\\\"\\\"\"", "<ESC_ANY_CHAR>", "<S_QUOTED_STRING_HYBRID>", "<D_QUOTED_STRING_HYBRID>", "<S_CHAR_LITERAL>", "<S_QUOTED_IDENTIFIER>", "<EMPTY_QUOTE>", "<UNICODE_LETTERS>", "<SYMBOL>"};
}

