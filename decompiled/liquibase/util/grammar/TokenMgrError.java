/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util.grammar;

public class TokenMgrError
extends Error {
    private static final long serialVersionUID = 1L;
    public static final int LEXICAL_ERROR = 0;
    public static final int STATIC_LEXER_ERROR = 1;
    public static final int INVALID_LEXICAL_STATE = 2;
    public static final int LOOP_DETECTED = 3;
    int errorCode;

    protected static final String addEscapes(String str) {
        StringBuilder retval = new StringBuilder();
        block10: for (int i = 0; i < str.length(); ++i) {
            switch (str.charAt(i)) {
                case '\b': {
                    retval.append("\\b");
                    continue block10;
                }
                case '\t': {
                    retval.append("\\t");
                    continue block10;
                }
                case '\n': {
                    retval.append("\\n");
                    continue block10;
                }
                case '\f': {
                    retval.append("\\f");
                    continue block10;
                }
                case '\r': {
                    retval.append("\\r");
                    continue block10;
                }
                case '\"': {
                    retval.append("\\\"");
                    continue block10;
                }
                case '\'': {
                    retval.append("\\'");
                    continue block10;
                }
                case '\\': {
                    retval.append("\\\\");
                    continue block10;
                }
                default: {
                    char ch = str.charAt(i);
                    if (ch < ' ' || ch > '~') {
                        String s2 = "0000" + Integer.toString(ch, 16);
                        retval.append("\\u" + s2.substring(s2.length() - 4, s2.length()));
                        continue block10;
                    }
                    retval.append(ch);
                }
            }
        }
        return retval.toString();
    }

    protected static String LexicalErr(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, int curChar) {
        return "Lexical error at line " + errorLine + ", column " + errorColumn + ".  Encountered: " + (EOFSeen ? "<EOF>" : "'" + TokenMgrError.addEscapes(String.valueOf((char)curChar)) + "' (" + curChar + "),") + (errorAfter == null || errorAfter.length() == 0 ? "" : " after prefix \"" + TokenMgrError.addEscapes(errorAfter) + "\"") + (lexState == 0 ? "" : " (in lexical state " + lexState + ")");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public TokenMgrError() {
    }

    public TokenMgrError(String message, int reason) {
        super(message);
        this.errorCode = reason;
    }

    public TokenMgrError(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, int curChar, int reason) {
        this(TokenMgrError.LexicalErr(EOFSeen, lexState, errorLine, errorColumn, errorAfter, curChar), reason);
    }
}

