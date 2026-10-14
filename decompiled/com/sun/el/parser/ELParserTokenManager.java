/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.parser;

import com.sun.el.parser.ELParserConstants;
import com.sun.el.parser.SimpleCharStream;
import com.sun.el.parser.Token;
import com.sun.el.parser.TokenMgrError;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Stack;

public class ELParserTokenManager
implements ELParserConstants {
    Stack<Integer> stack = new Stack();
    public PrintStream debugStream = System.out;
    static final long[] jjbitVec0 = new long[]{-2L, -1L, -1L, -1L};
    static final long[] jjbitVec2 = new long[]{0L, 0L, -1L, -1L};
    static final long[] jjbitVec3 = new long[]{2301339413881290750L, -16384L, 0xFFFFFFFFL, 0x600000000000000L};
    static final long[] jjbitVec4 = new long[]{0L, 0L, 0L, -36028797027352577L};
    static final long[] jjbitVec5 = new long[]{0L, -1L, -1L, -1L};
    static final long[] jjbitVec6 = new long[]{-1L, -1L, 65535L, 0L};
    static final long[] jjbitVec7 = new long[]{-1L, -1L, 0L, 0L};
    static final long[] jjbitVec8 = new long[]{0x3FFFFFFFFFFFL, 0L, 0L, 0L};
    static final int[] jjnextStates = new int[]{0, 1, 3, 5, 8, 9, 10, 15, 16, 28, 29, 31, 32, 33, 20, 21, 23, 24, 25, 20, 21, 23, 28, 29, 31, 3, 4, 13, 14, 17, 18, 24, 25, 32, 33};
    public static final String[] jjstrLiteralImages = new String[]{"", null, "${", "#{", null, null, null, null, null, "{", "}", null, null, null, null, null, "true", "false", "null", ".", "(", ")", "[", "]", ":", ",", ";", ">", "gt", "<", "lt", ">=", "ge", "<=", "le", "==", "eq", "!=", "ne", "!", "not", "&&", "and", "||", "or", "empty", "instanceof", "*", "+", "-", "?", "/", "div", "%", "mod", "+=", "=", "->", null, null, null, null, null};
    public static final String[] lexStateNames = new String[]{"DEFAULT", "IN_EXPRESSION", "IN_MAP"};
    public static final int[] jjnewLexState = new int[]{-1, -1, 1, 1, -1, -1, -1, -1, -1, 2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    static final long[] jjtoToken = new long[]{5188146770730802703L};
    static final long[] jjtoSkip = new long[]{496L};
    protected SimpleCharStream input_stream;
    private final int[] jjrounds = new int[35];
    private final int[] jjstateSet = new int[70];
    private final StringBuilder jjimage;
    private StringBuilder image = this.jjimage = new StringBuilder();
    private int jjimageLen;
    private int lengthOfMatch;
    protected char curChar;
    int curLexState = 0;
    int defaultLexState = 0;
    int jjnewStateCnt;
    int jjround;
    int jjmatchedPos;
    int jjmatchedKind;

    public void setDebugStream(PrintStream ds) {
        this.debugStream = ds;
    }

    private final int jjStopStringLiteralDfa_0(int pos, long active0) {
        switch (pos) {
            case 0: {
                if ((active0 & 0x10L) != 0L) {
                    return 2;
                }
                if ((active0 & 0xCL) != 0L) {
                    this.jjmatchedKind = 1;
                    return 4;
                }
                return -1;
            }
        }
        return -1;
    }

    private final int jjStartNfa_0(int pos, long active0) {
        return this.jjMoveNfa_0(this.jjStopStringLiteralDfa_0(pos, active0), pos + 1);
    }

    private int jjStopAtPos(int pos, int kind) {
        this.jjmatchedKind = kind;
        this.jjmatchedPos = pos;
        return pos + 1;
    }

    private int jjMoveStringLiteralDfa0_0() {
        switch (this.curChar) {
            case '#': {
                return this.jjMoveStringLiteralDfa1_0(8L);
            }
            case '$': {
                return this.jjMoveStringLiteralDfa1_0(4L);
            }
            case '\\': {
                return this.jjStartNfaWithStates_0(0, 4, 2);
            }
        }
        return this.jjMoveNfa_0(6, 0);
    }

    private int jjMoveStringLiteralDfa1_0(long active0) {
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_0(0, active0);
            return 1;
        }
        switch (this.curChar) {
            case '{': {
                if ((active0 & 4L) != 0L) {
                    return this.jjStopAtPos(1, 2);
                }
                if ((active0 & 8L) == 0L) break;
                return this.jjStopAtPos(1, 3);
            }
        }
        return this.jjStartNfa_0(0, active0);
    }

    private int jjStartNfaWithStates_0(int pos, int kind, int state) {
        this.jjmatchedKind = kind;
        this.jjmatchedPos = pos;
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            return pos + 1;
        }
        return this.jjMoveNfa_0(state, pos + 1);
    }

    private int jjMoveNfa_0(int startState, int curPos) {
        int startsAt = 0;
        this.jjnewStateCnt = 7;
        int i = 1;
        this.jjstateSet[0] = startState;
        int kind = Integer.MAX_VALUE;
        while (true) {
            if (++this.jjround == Integer.MAX_VALUE) {
                this.ReInitRounds();
            }
            if (this.curChar < '@') {
                long l = 1L << this.curChar;
                block20: do {
                    switch (this.jjstateSet[--i]) {
                        case 6: {
                            if ((0xFFFFFFE7FFFFFFFFL & l) != 0L) {
                                if (kind > 1) {
                                    kind = 1;
                                }
                                this.jjCheckNAddStates(0, 3);
                            } else if ((0x1800000000L & l) != 0L && kind > 1) {
                                kind = 1;
                            }
                            if (this.curChar == '#') {
                                this.jjCheckNAdd(4);
                                break;
                            }
                            if (this.curChar != '$') break;
                            this.jjCheckNAdd(4);
                            break;
                        }
                        case 0: 
                        case 4: {
                            if ((0xFFFFFFE7FFFFFFFFL & l) == 0L) continue block20;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAddStates(0, 3);
                            break;
                        }
                        case 2: {
                            if ((0x1800000000L & l) == 0L) continue block20;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAddStates(0, 3);
                            break;
                        }
                        case 3: {
                            if (this.curChar != '$') break;
                            this.jjCheckNAdd(4);
                            break;
                        }
                        case 5: {
                            if (this.curChar != '#') break;
                            this.jjCheckNAdd(4);
                            break;
                        }
                    }
                } while (i != startsAt);
            } else if (this.curChar < '\u0080') {
                long l = 1L << (this.curChar & 0x3F);
                block21: do {
                    switch (this.jjstateSet[--i]) {
                        case 6: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) != 0L) {
                                if (kind > 1) {
                                    kind = 1;
                                }
                                this.jjCheckNAddStates(0, 3);
                                break;
                            }
                            if (this.curChar != '\\') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 2;
                            break;
                        }
                        case 0: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) continue block21;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAddStates(0, 3);
                            break;
                        }
                        case 1: {
                            if (this.curChar != '\\') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 2;
                            break;
                        }
                        case 2: {
                            if (this.curChar != '\\') continue block21;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAddStates(0, 3);
                            break;
                        }
                        case 4: {
                            if ((0xF7FFFFFFFFFFFFFFL & l) == 0L) continue block21;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAddStates(0, 3);
                            break;
                        }
                    }
                } while (i != startsAt);
            } else {
                int hiByte = this.curChar >> 8;
                int i1 = hiByte >> 6;
                long l1 = 1L << (hiByte & 0x3F);
                int i2 = (this.curChar & 0xFF) >> 6;
                long l2 = 1L << (this.curChar & 0x3F);
                block22: do {
                    switch (this.jjstateSet[--i]) {
                        case 0: 
                        case 4: 
                        case 6: {
                            if (!ELParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) continue block22;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAddStates(0, 3);
                            break;
                        }
                    }
                } while (i != startsAt);
            }
            if (kind != Integer.MAX_VALUE) {
                this.jjmatchedKind = kind;
                this.jjmatchedPos = curPos;
                kind = Integer.MAX_VALUE;
            }
            ++curPos;
            i = this.jjnewStateCnt;
            this.jjnewStateCnt = startsAt;
            if (i == (startsAt = 7 - this.jjnewStateCnt)) {
                return curPos;
            }
            try {
                this.curChar = this.input_stream.readChar();
            }
            catch (IOException e) {
                return curPos;
            }
        }
    }

    private final int jjStopStringLiteralDfa_2(int pos, long active0) {
        switch (pos) {
            case 0: {
                if ((active0 & 0x80000L) != 0L) {
                    return 1;
                }
                if ((active0 & 0x50755550070000L) != 0L) {
                    this.jjmatchedKind = 58;
                    return 6;
                }
                return -1;
            }
            case 1: {
                if ((active0 & 0x105550000000L) != 0L) {
                    return 6;
                }
                if ((active0 & 0x50650000070000L) != 0L) {
                    this.jjmatchedKind = 58;
                    this.jjmatchedPos = 1;
                    return 6;
                }
                return -1;
            }
            case 2: {
                if ((active0 & 0x50050000000000L) != 0L) {
                    return 6;
                }
                if ((active0 & 0x600000070000L) != 0L) {
                    this.jjmatchedKind = 58;
                    this.jjmatchedPos = 2;
                    return 6;
                }
                return -1;
            }
            case 3: {
                if ((active0 & 0x50000L) != 0L) {
                    return 6;
                }
                if ((active0 & 0x600000020000L) != 0L) {
                    this.jjmatchedKind = 58;
                    this.jjmatchedPos = 3;
                    return 6;
                }
                return -1;
            }
            case 4: {
                if ((active0 & 0x400000000000L) != 0L) {
                    this.jjmatchedKind = 58;
                    this.jjmatchedPos = 4;
                    return 6;
                }
                if ((active0 & 0x200000020000L) != 0L) {
                    return 6;
                }
                return -1;
            }
            case 5: {
                if ((active0 & 0x400000000000L) != 0L) {
                    this.jjmatchedKind = 58;
                    this.jjmatchedPos = 5;
                    return 6;
                }
                return -1;
            }
            case 6: {
                if ((active0 & 0x400000000000L) != 0L) {
                    this.jjmatchedKind = 58;
                    this.jjmatchedPos = 6;
                    return 6;
                }
                return -1;
            }
            case 7: {
                if ((active0 & 0x400000000000L) != 0L) {
                    this.jjmatchedKind = 58;
                    this.jjmatchedPos = 7;
                    return 6;
                }
                return -1;
            }
            case 8: {
                if ((active0 & 0x400000000000L) != 0L) {
                    this.jjmatchedKind = 58;
                    this.jjmatchedPos = 8;
                    return 6;
                }
                return -1;
            }
        }
        return -1;
    }

    private final int jjStartNfa_2(int pos, long active0) {
        return this.jjMoveNfa_2(this.jjStopStringLiteralDfa_2(pos, active0), pos + 1);
    }

    private int jjMoveStringLiteralDfa0_2() {
        switch (this.curChar) {
            case '!': {
                this.jjmatchedKind = 39;
                return this.jjMoveStringLiteralDfa1_2(0x2000000000L);
            }
            case '%': {
                return this.jjStopAtPos(0, 53);
            }
            case '&': {
                return this.jjMoveStringLiteralDfa1_2(0x20000000000L);
            }
            case '(': {
                return this.jjStopAtPos(0, 20);
            }
            case ')': {
                return this.jjStopAtPos(0, 21);
            }
            case '*': {
                return this.jjStopAtPos(0, 47);
            }
            case '+': {
                this.jjmatchedKind = 48;
                return this.jjMoveStringLiteralDfa1_2(0x80000000000000L);
            }
            case ',': {
                return this.jjStopAtPos(0, 25);
            }
            case '-': {
                this.jjmatchedKind = 49;
                return this.jjMoveStringLiteralDfa1_2(0x200000000000000L);
            }
            case '.': {
                return this.jjStartNfaWithStates_2(0, 19, 1);
            }
            case '/': {
                return this.jjStopAtPos(0, 51);
            }
            case ':': {
                return this.jjStopAtPos(0, 24);
            }
            case ';': {
                return this.jjStopAtPos(0, 26);
            }
            case '<': {
                this.jjmatchedKind = 29;
                return this.jjMoveStringLiteralDfa1_2(0x200000000L);
            }
            case '=': {
                this.jjmatchedKind = 56;
                return this.jjMoveStringLiteralDfa1_2(0x800000000L);
            }
            case '>': {
                this.jjmatchedKind = 27;
                return this.jjMoveStringLiteralDfa1_2(0x80000000L);
            }
            case '?': {
                return this.jjStopAtPos(0, 50);
            }
            case '[': {
                return this.jjStopAtPos(0, 22);
            }
            case ']': {
                return this.jjStopAtPos(0, 23);
            }
            case 'a': {
                return this.jjMoveStringLiteralDfa1_2(0x40000000000L);
            }
            case 'd': {
                return this.jjMoveStringLiteralDfa1_2(0x10000000000000L);
            }
            case 'e': {
                return this.jjMoveStringLiteralDfa1_2(0x201000000000L);
            }
            case 'f': {
                return this.jjMoveStringLiteralDfa1_2(131072L);
            }
            case 'g': {
                return this.jjMoveStringLiteralDfa1_2(0x110000000L);
            }
            case 'i': {
                return this.jjMoveStringLiteralDfa1_2(0x400000000000L);
            }
            case 'l': {
                return this.jjMoveStringLiteralDfa1_2(0x440000000L);
            }
            case 'm': {
                return this.jjMoveStringLiteralDfa1_2(0x40000000000000L);
            }
            case 'n': {
                return this.jjMoveStringLiteralDfa1_2(0x14000040000L);
            }
            case 'o': {
                return this.jjMoveStringLiteralDfa1_2(0x100000000000L);
            }
            case 't': {
                return this.jjMoveStringLiteralDfa1_2(65536L);
            }
            case '{': {
                return this.jjStopAtPos(0, 9);
            }
            case '|': {
                return this.jjMoveStringLiteralDfa1_2(0x80000000000L);
            }
            case '}': {
                return this.jjStopAtPos(0, 10);
            }
        }
        return this.jjMoveNfa_2(0, 0);
    }

    private int jjMoveStringLiteralDfa1_2(long active0) {
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_2(0, active0);
            return 1;
        }
        switch (this.curChar) {
            case '&': {
                if ((active0 & 0x20000000000L) == 0L) break;
                return this.jjStopAtPos(1, 41);
            }
            case '=': {
                if ((active0 & 0x80000000L) != 0L) {
                    return this.jjStopAtPos(1, 31);
                }
                if ((active0 & 0x200000000L) != 0L) {
                    return this.jjStopAtPos(1, 33);
                }
                if ((active0 & 0x800000000L) != 0L) {
                    return this.jjStopAtPos(1, 35);
                }
                if ((active0 & 0x2000000000L) != 0L) {
                    return this.jjStopAtPos(1, 37);
                }
                if ((active0 & 0x80000000000000L) == 0L) break;
                return this.jjStopAtPos(1, 55);
            }
            case '>': {
                if ((active0 & 0x200000000000000L) == 0L) break;
                return this.jjStopAtPos(1, 57);
            }
            case 'a': {
                return this.jjMoveStringLiteralDfa2_2(active0, 131072L);
            }
            case 'e': {
                if ((active0 & 0x100000000L) != 0L) {
                    return this.jjStartNfaWithStates_2(1, 32, 6);
                }
                if ((active0 & 0x400000000L) != 0L) {
                    return this.jjStartNfaWithStates_2(1, 34, 6);
                }
                if ((active0 & 0x4000000000L) == 0L) break;
                return this.jjStartNfaWithStates_2(1, 38, 6);
            }
            case 'i': {
                return this.jjMoveStringLiteralDfa2_2(active0, 0x10000000000000L);
            }
            case 'm': {
                return this.jjMoveStringLiteralDfa2_2(active0, 0x200000000000L);
            }
            case 'n': {
                return this.jjMoveStringLiteralDfa2_2(active0, 0x440000000000L);
            }
            case 'o': {
                return this.jjMoveStringLiteralDfa2_2(active0, 0x40010000000000L);
            }
            case 'q': {
                if ((active0 & 0x1000000000L) == 0L) break;
                return this.jjStartNfaWithStates_2(1, 36, 6);
            }
            case 'r': {
                if ((active0 & 0x100000000000L) != 0L) {
                    return this.jjStartNfaWithStates_2(1, 44, 6);
                }
                return this.jjMoveStringLiteralDfa2_2(active0, 65536L);
            }
            case 't': {
                if ((active0 & 0x10000000L) != 0L) {
                    return this.jjStartNfaWithStates_2(1, 28, 6);
                }
                if ((active0 & 0x40000000L) == 0L) break;
                return this.jjStartNfaWithStates_2(1, 30, 6);
            }
            case 'u': {
                return this.jjMoveStringLiteralDfa2_2(active0, 262144L);
            }
            case '|': {
                if ((active0 & 0x80000000000L) == 0L) break;
                return this.jjStopAtPos(1, 43);
            }
        }
        return this.jjStartNfa_2(0, active0);
    }

    private int jjMoveStringLiteralDfa2_2(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_2(0, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_2(1, active0);
            return 2;
        }
        switch (this.curChar) {
            case 'd': {
                if ((active0 & 0x40000000000L) != 0L) {
                    return this.jjStartNfaWithStates_2(2, 42, 6);
                }
                if ((active0 & 0x40000000000000L) == 0L) break;
                return this.jjStartNfaWithStates_2(2, 54, 6);
            }
            case 'l': {
                return this.jjMoveStringLiteralDfa3_2(active0, 393216L);
            }
            case 'p': {
                return this.jjMoveStringLiteralDfa3_2(active0, 0x200000000000L);
            }
            case 's': {
                return this.jjMoveStringLiteralDfa3_2(active0, 0x400000000000L);
            }
            case 't': {
                if ((active0 & 0x10000000000L) == 0L) break;
                return this.jjStartNfaWithStates_2(2, 40, 6);
            }
            case 'u': {
                return this.jjMoveStringLiteralDfa3_2(active0, 65536L);
            }
            case 'v': {
                if ((active0 & 0x10000000000000L) == 0L) break;
                return this.jjStartNfaWithStates_2(2, 52, 6);
            }
        }
        return this.jjStartNfa_2(1, active0);
    }

    private int jjMoveStringLiteralDfa3_2(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_2(1, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_2(2, active0);
            return 3;
        }
        switch (this.curChar) {
            case 'e': {
                if ((active0 & 0x10000L) == 0L) break;
                return this.jjStartNfaWithStates_2(3, 16, 6);
            }
            case 'l': {
                if ((active0 & 0x40000L) == 0L) break;
                return this.jjStartNfaWithStates_2(3, 18, 6);
            }
            case 's': {
                return this.jjMoveStringLiteralDfa4_2(active0, 131072L);
            }
            case 't': {
                return this.jjMoveStringLiteralDfa4_2(active0, 0x600000000000L);
            }
        }
        return this.jjStartNfa_2(2, active0);
    }

    private int jjMoveStringLiteralDfa4_2(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_2(2, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_2(3, active0);
            return 4;
        }
        switch (this.curChar) {
            case 'a': {
                return this.jjMoveStringLiteralDfa5_2(active0, 0x400000000000L);
            }
            case 'e': {
                if ((active0 & 0x20000L) == 0L) break;
                return this.jjStartNfaWithStates_2(4, 17, 6);
            }
            case 'y': {
                if ((active0 & 0x200000000000L) == 0L) break;
                return this.jjStartNfaWithStates_2(4, 45, 6);
            }
        }
        return this.jjStartNfa_2(3, active0);
    }

    private int jjMoveStringLiteralDfa5_2(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_2(3, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_2(4, active0);
            return 5;
        }
        switch (this.curChar) {
            case 'n': {
                return this.jjMoveStringLiteralDfa6_2(active0, 0x400000000000L);
            }
        }
        return this.jjStartNfa_2(4, active0);
    }

    private int jjMoveStringLiteralDfa6_2(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_2(4, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_2(5, active0);
            return 6;
        }
        switch (this.curChar) {
            case 'c': {
                return this.jjMoveStringLiteralDfa7_2(active0, 0x400000000000L);
            }
        }
        return this.jjStartNfa_2(5, active0);
    }

    private int jjMoveStringLiteralDfa7_2(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_2(5, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_2(6, active0);
            return 7;
        }
        switch (this.curChar) {
            case 'e': {
                return this.jjMoveStringLiteralDfa8_2(active0, 0x400000000000L);
            }
        }
        return this.jjStartNfa_2(6, active0);
    }

    private int jjMoveStringLiteralDfa8_2(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_2(6, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_2(7, active0);
            return 8;
        }
        switch (this.curChar) {
            case 'o': {
                return this.jjMoveStringLiteralDfa9_2(active0, 0x400000000000L);
            }
        }
        return this.jjStartNfa_2(7, active0);
    }

    private int jjMoveStringLiteralDfa9_2(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_2(7, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_2(8, active0);
            return 9;
        }
        switch (this.curChar) {
            case 'f': {
                if ((active0 & 0x400000000000L) == 0L) break;
                return this.jjStartNfaWithStates_2(9, 46, 6);
            }
        }
        return this.jjStartNfa_2(8, active0);
    }

    private int jjStartNfaWithStates_2(int pos, int kind, int state) {
        this.jjmatchedKind = kind;
        this.jjmatchedPos = pos;
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            return pos + 1;
        }
        return this.jjMoveNfa_2(state, pos + 1);
    }

    private int jjMoveNfa_2(int startState, int curPos) {
        int startsAt = 0;
        this.jjnewStateCnt = 35;
        int i = 1;
        this.jjstateSet[0] = startState;
        int kind = Integer.MAX_VALUE;
        while (true) {
            if (++this.jjround == Integer.MAX_VALUE) {
                this.ReInitRounds();
            }
            if (this.curChar < '@') {
                long l = 1L << this.curChar;
                block58: do {
                    switch (this.jjstateSet[--i]) {
                        case 0: {
                            if ((0x3FF000000000000L & l) != 0L) {
                                if (kind > 11) {
                                    kind = 11;
                                }
                                this.jjCheckNAddStates(4, 8);
                                break;
                            }
                            if ((0x1800000000L & l) != 0L) {
                                if (kind > 58) {
                                    kind = 58;
                                }
                                this.jjCheckNAdd(6);
                                break;
                            }
                            if (this.curChar == '\'') {
                                this.jjCheckNAddStates(9, 13);
                                break;
                            }
                            if (this.curChar == '\"') {
                                this.jjCheckNAddStates(14, 18);
                                break;
                            }
                            if (this.curChar != '.') break;
                            this.jjCheckNAdd(1);
                            break;
                        }
                        case 1: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 12) {
                                kind = 12;
                            }
                            this.jjCheckNAddTwoStates(1, 2);
                            break;
                        }
                        case 3: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(4);
                            break;
                        }
                        case 4: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 12) {
                                kind = 12;
                            }
                            this.jjCheckNAdd(4);
                            break;
                        }
                        case 5: {
                            if ((0x1800000000L & l) == 0L) continue block58;
                            if (kind > 58) {
                                kind = 58;
                            }
                            this.jjCheckNAdd(6);
                            break;
                        }
                        case 6: {
                            if ((0x3FF001000000000L & l) == 0L) continue block58;
                            if (kind > 58) {
                                kind = 58;
                            }
                            this.jjCheckNAdd(6);
                            break;
                        }
                        case 7: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 11) {
                                kind = 11;
                            }
                            this.jjCheckNAddStates(4, 8);
                            break;
                        }
                        case 8: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 11) {
                                kind = 11;
                            }
                            this.jjCheckNAdd(8);
                            break;
                        }
                        case 9: {
                            if ((0x3FF000000000000L & l) == 0L) break;
                            this.jjCheckNAddTwoStates(9, 10);
                            break;
                        }
                        case 10: {
                            if (this.curChar != '.') continue block58;
                            if (kind > 12) {
                                kind = 12;
                            }
                            this.jjCheckNAddTwoStates(11, 12);
                            break;
                        }
                        case 11: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 12) {
                                kind = 12;
                            }
                            this.jjCheckNAddTwoStates(11, 12);
                            break;
                        }
                        case 13: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(14);
                            break;
                        }
                        case 14: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 12) {
                                kind = 12;
                            }
                            this.jjCheckNAdd(14);
                            break;
                        }
                        case 15: {
                            if ((0x3FF000000000000L & l) == 0L) break;
                            this.jjCheckNAddTwoStates(15, 16);
                            break;
                        }
                        case 17: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(18);
                            break;
                        }
                        case 18: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 12) {
                                kind = 12;
                            }
                            this.jjCheckNAdd(18);
                            break;
                        }
                        case 19: {
                            if (this.curChar != '\"') break;
                            this.jjCheckNAddStates(14, 18);
                            break;
                        }
                        case 20: {
                            if ((0xFFFFFFFBFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(19, 21);
                            break;
                        }
                        case 22: {
                            if (this.curChar != '\"') break;
                            this.jjCheckNAddStates(19, 21);
                            break;
                        }
                        case 23: {
                            if (this.curChar != '\"' || kind <= 14) continue block58;
                            kind = 14;
                            break;
                        }
                        case 24: {
                            if ((0xFFFFFFFBFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(24, 25);
                            break;
                        }
                        case 26: {
                            if ((0xFFFFFFFBFFFFFFFFL & l) == 0L || kind <= 15) continue block58;
                            kind = 15;
                            break;
                        }
                        case 27: {
                            if (this.curChar != '\'') break;
                            this.jjCheckNAddStates(9, 13);
                            break;
                        }
                        case 28: {
                            if ((0xFFFFFF7FFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(22, 24);
                            break;
                        }
                        case 30: {
                            if (this.curChar != '\'') break;
                            this.jjCheckNAddStates(22, 24);
                            break;
                        }
                        case 31: {
                            if (this.curChar != '\'' || kind <= 14) continue block58;
                            kind = 14;
                            break;
                        }
                        case 32: {
                            if ((0xFFFFFF7FFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(32, 33);
                            break;
                        }
                        case 34: {
                            if ((0xFFFFFF7FFFFFFFFFL & l) == 0L || kind <= 15) continue block58;
                            kind = 15;
                            break;
                        }
                    }
                } while (i != startsAt);
            } else if (this.curChar < '\u0080') {
                long l = 1L << (this.curChar & 0x3F);
                block59: do {
                    switch (this.jjstateSet[--i]) {
                        case 0: 
                        case 6: {
                            if ((0x7FFFFFE87FFFFFEL & l) == 0L) continue block59;
                            if (kind > 58) {
                                kind = 58;
                            }
                            this.jjCheckNAdd(6);
                            break;
                        }
                        case 2: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(25, 26);
                            break;
                        }
                        case 12: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(27, 28);
                            break;
                        }
                        case 16: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(29, 30);
                            break;
                        }
                        case 20: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(19, 21);
                            break;
                        }
                        case 21: {
                            if (this.curChar != '\\') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 22;
                            break;
                        }
                        case 22: {
                            if (this.curChar != '\\') break;
                            this.jjCheckNAddStates(19, 21);
                            break;
                        }
                        case 24: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) break;
                            this.jjAddStates(31, 32);
                            break;
                        }
                        case 25: {
                            if (this.curChar != '\\') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 26;
                            break;
                        }
                        case 26: 
                        case 34: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L || kind <= 15) continue block59;
                            kind = 15;
                            break;
                        }
                        case 28: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(22, 24);
                            break;
                        }
                        case 29: {
                            if (this.curChar != '\\') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 30;
                            break;
                        }
                        case 30: {
                            if (this.curChar != '\\') break;
                            this.jjCheckNAddStates(22, 24);
                            break;
                        }
                        case 32: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) break;
                            this.jjAddStates(33, 34);
                            break;
                        }
                        case 33: {
                            if (this.curChar != '\\') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 34;
                            break;
                        }
                    }
                } while (i != startsAt);
            } else {
                int hiByte = this.curChar >> 8;
                int i1 = hiByte >> 6;
                long l1 = 1L << (hiByte & 0x3F);
                int i2 = (this.curChar & 0xFF) >> 6;
                long l2 = 1L << (this.curChar & 0x3F);
                block60: do {
                    switch (this.jjstateSet[--i]) {
                        case 0: 
                        case 6: {
                            if (!ELParserTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block60;
                            if (kind > 58) {
                                kind = 58;
                            }
                            this.jjCheckNAdd(6);
                            break;
                        }
                        case 20: {
                            if (!ELParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) break;
                            this.jjAddStates(19, 21);
                            break;
                        }
                        case 24: {
                            if (!ELParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) break;
                            this.jjAddStates(31, 32);
                            break;
                        }
                        case 26: 
                        case 34: {
                            if (!ELParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2) || kind <= 15) continue block60;
                            kind = 15;
                            break;
                        }
                        case 28: {
                            if (!ELParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) break;
                            this.jjAddStates(22, 24);
                            break;
                        }
                        case 32: {
                            if (!ELParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) break;
                            this.jjAddStates(33, 34);
                            break;
                        }
                    }
                } while (i != startsAt);
            }
            if (kind != Integer.MAX_VALUE) {
                this.jjmatchedKind = kind;
                this.jjmatchedPos = curPos;
                kind = Integer.MAX_VALUE;
            }
            ++curPos;
            i = this.jjnewStateCnt;
            this.jjnewStateCnt = startsAt;
            if (i == (startsAt = 35 - this.jjnewStateCnt)) {
                return curPos;
            }
            try {
                this.curChar = this.input_stream.readChar();
            }
            catch (IOException e) {
                return curPos;
            }
        }
    }

    private final int jjStopStringLiteralDfa_1(int pos, long active0) {
        switch (pos) {
            case 0: {
                if ((active0 & 0x80000L) != 0L) {
                    return 1;
                }
                if ((active0 & 0x50755550070000L) != 0L) {
                    this.jjmatchedKind = 58;
                    return 6;
                }
                return -1;
            }
            case 1: {
                if ((active0 & 0x105550000000L) != 0L) {
                    return 6;
                }
                if ((active0 & 0x50650000070000L) != 0L) {
                    this.jjmatchedKind = 58;
                    this.jjmatchedPos = 1;
                    return 6;
                }
                return -1;
            }
            case 2: {
                if ((active0 & 0x50050000000000L) != 0L) {
                    return 6;
                }
                if ((active0 & 0x600000070000L) != 0L) {
                    this.jjmatchedKind = 58;
                    this.jjmatchedPos = 2;
                    return 6;
                }
                return -1;
            }
            case 3: {
                if ((active0 & 0x50000L) != 0L) {
                    return 6;
                }
                if ((active0 & 0x600000020000L) != 0L) {
                    this.jjmatchedKind = 58;
                    this.jjmatchedPos = 3;
                    return 6;
                }
                return -1;
            }
            case 4: {
                if ((active0 & 0x400000000000L) != 0L) {
                    this.jjmatchedKind = 58;
                    this.jjmatchedPos = 4;
                    return 6;
                }
                if ((active0 & 0x200000020000L) != 0L) {
                    return 6;
                }
                return -1;
            }
            case 5: {
                if ((active0 & 0x400000000000L) != 0L) {
                    this.jjmatchedKind = 58;
                    this.jjmatchedPos = 5;
                    return 6;
                }
                return -1;
            }
            case 6: {
                if ((active0 & 0x400000000000L) != 0L) {
                    this.jjmatchedKind = 58;
                    this.jjmatchedPos = 6;
                    return 6;
                }
                return -1;
            }
            case 7: {
                if ((active0 & 0x400000000000L) != 0L) {
                    this.jjmatchedKind = 58;
                    this.jjmatchedPos = 7;
                    return 6;
                }
                return -1;
            }
            case 8: {
                if ((active0 & 0x400000000000L) != 0L) {
                    this.jjmatchedKind = 58;
                    this.jjmatchedPos = 8;
                    return 6;
                }
                return -1;
            }
        }
        return -1;
    }

    private final int jjStartNfa_1(int pos, long active0) {
        return this.jjMoveNfa_1(this.jjStopStringLiteralDfa_1(pos, active0), pos + 1);
    }

    private int jjMoveStringLiteralDfa0_1() {
        switch (this.curChar) {
            case '!': {
                this.jjmatchedKind = 39;
                return this.jjMoveStringLiteralDfa1_1(0x2000000000L);
            }
            case '%': {
                return this.jjStopAtPos(0, 53);
            }
            case '&': {
                return this.jjMoveStringLiteralDfa1_1(0x20000000000L);
            }
            case '(': {
                return this.jjStopAtPos(0, 20);
            }
            case ')': {
                return this.jjStopAtPos(0, 21);
            }
            case '*': {
                return this.jjStopAtPos(0, 47);
            }
            case '+': {
                this.jjmatchedKind = 48;
                return this.jjMoveStringLiteralDfa1_1(0x80000000000000L);
            }
            case ',': {
                return this.jjStopAtPos(0, 25);
            }
            case '-': {
                this.jjmatchedKind = 49;
                return this.jjMoveStringLiteralDfa1_1(0x200000000000000L);
            }
            case '.': {
                return this.jjStartNfaWithStates_1(0, 19, 1);
            }
            case '/': {
                return this.jjStopAtPos(0, 51);
            }
            case ':': {
                return this.jjStopAtPos(0, 24);
            }
            case ';': {
                return this.jjStopAtPos(0, 26);
            }
            case '<': {
                this.jjmatchedKind = 29;
                return this.jjMoveStringLiteralDfa1_1(0x200000000L);
            }
            case '=': {
                this.jjmatchedKind = 56;
                return this.jjMoveStringLiteralDfa1_1(0x800000000L);
            }
            case '>': {
                this.jjmatchedKind = 27;
                return this.jjMoveStringLiteralDfa1_1(0x80000000L);
            }
            case '?': {
                return this.jjStopAtPos(0, 50);
            }
            case '[': {
                return this.jjStopAtPos(0, 22);
            }
            case ']': {
                return this.jjStopAtPos(0, 23);
            }
            case 'a': {
                return this.jjMoveStringLiteralDfa1_1(0x40000000000L);
            }
            case 'd': {
                return this.jjMoveStringLiteralDfa1_1(0x10000000000000L);
            }
            case 'e': {
                return this.jjMoveStringLiteralDfa1_1(0x201000000000L);
            }
            case 'f': {
                return this.jjMoveStringLiteralDfa1_1(131072L);
            }
            case 'g': {
                return this.jjMoveStringLiteralDfa1_1(0x110000000L);
            }
            case 'i': {
                return this.jjMoveStringLiteralDfa1_1(0x400000000000L);
            }
            case 'l': {
                return this.jjMoveStringLiteralDfa1_1(0x440000000L);
            }
            case 'm': {
                return this.jjMoveStringLiteralDfa1_1(0x40000000000000L);
            }
            case 'n': {
                return this.jjMoveStringLiteralDfa1_1(0x14000040000L);
            }
            case 'o': {
                return this.jjMoveStringLiteralDfa1_1(0x100000000000L);
            }
            case 't': {
                return this.jjMoveStringLiteralDfa1_1(65536L);
            }
            case '{': {
                return this.jjStopAtPos(0, 9);
            }
            case '|': {
                return this.jjMoveStringLiteralDfa1_1(0x80000000000L);
            }
            case '}': {
                return this.jjStopAtPos(0, 10);
            }
        }
        return this.jjMoveNfa_1(0, 0);
    }

    private int jjMoveStringLiteralDfa1_1(long active0) {
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(0, active0);
            return 1;
        }
        switch (this.curChar) {
            case '&': {
                if ((active0 & 0x20000000000L) == 0L) break;
                return this.jjStopAtPos(1, 41);
            }
            case '=': {
                if ((active0 & 0x80000000L) != 0L) {
                    return this.jjStopAtPos(1, 31);
                }
                if ((active0 & 0x200000000L) != 0L) {
                    return this.jjStopAtPos(1, 33);
                }
                if ((active0 & 0x800000000L) != 0L) {
                    return this.jjStopAtPos(1, 35);
                }
                if ((active0 & 0x2000000000L) != 0L) {
                    return this.jjStopAtPos(1, 37);
                }
                if ((active0 & 0x80000000000000L) == 0L) break;
                return this.jjStopAtPos(1, 55);
            }
            case '>': {
                if ((active0 & 0x200000000000000L) == 0L) break;
                return this.jjStopAtPos(1, 57);
            }
            case 'a': {
                return this.jjMoveStringLiteralDfa2_1(active0, 131072L);
            }
            case 'e': {
                if ((active0 & 0x100000000L) != 0L) {
                    return this.jjStartNfaWithStates_1(1, 32, 6);
                }
                if ((active0 & 0x400000000L) != 0L) {
                    return this.jjStartNfaWithStates_1(1, 34, 6);
                }
                if ((active0 & 0x4000000000L) == 0L) break;
                return this.jjStartNfaWithStates_1(1, 38, 6);
            }
            case 'i': {
                return this.jjMoveStringLiteralDfa2_1(active0, 0x10000000000000L);
            }
            case 'm': {
                return this.jjMoveStringLiteralDfa2_1(active0, 0x200000000000L);
            }
            case 'n': {
                return this.jjMoveStringLiteralDfa2_1(active0, 0x440000000000L);
            }
            case 'o': {
                return this.jjMoveStringLiteralDfa2_1(active0, 0x40010000000000L);
            }
            case 'q': {
                if ((active0 & 0x1000000000L) == 0L) break;
                return this.jjStartNfaWithStates_1(1, 36, 6);
            }
            case 'r': {
                if ((active0 & 0x100000000000L) != 0L) {
                    return this.jjStartNfaWithStates_1(1, 44, 6);
                }
                return this.jjMoveStringLiteralDfa2_1(active0, 65536L);
            }
            case 't': {
                if ((active0 & 0x10000000L) != 0L) {
                    return this.jjStartNfaWithStates_1(1, 28, 6);
                }
                if ((active0 & 0x40000000L) == 0L) break;
                return this.jjStartNfaWithStates_1(1, 30, 6);
            }
            case 'u': {
                return this.jjMoveStringLiteralDfa2_1(active0, 262144L);
            }
            case '|': {
                if ((active0 & 0x80000000000L) == 0L) break;
                return this.jjStopAtPos(1, 43);
            }
        }
        return this.jjStartNfa_1(0, active0);
    }

    private int jjMoveStringLiteralDfa2_1(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_1(0, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(1, active0);
            return 2;
        }
        switch (this.curChar) {
            case 'd': {
                if ((active0 & 0x40000000000L) != 0L) {
                    return this.jjStartNfaWithStates_1(2, 42, 6);
                }
                if ((active0 & 0x40000000000000L) == 0L) break;
                return this.jjStartNfaWithStates_1(2, 54, 6);
            }
            case 'l': {
                return this.jjMoveStringLiteralDfa3_1(active0, 393216L);
            }
            case 'p': {
                return this.jjMoveStringLiteralDfa3_1(active0, 0x200000000000L);
            }
            case 's': {
                return this.jjMoveStringLiteralDfa3_1(active0, 0x400000000000L);
            }
            case 't': {
                if ((active0 & 0x10000000000L) == 0L) break;
                return this.jjStartNfaWithStates_1(2, 40, 6);
            }
            case 'u': {
                return this.jjMoveStringLiteralDfa3_1(active0, 65536L);
            }
            case 'v': {
                if ((active0 & 0x10000000000000L) == 0L) break;
                return this.jjStartNfaWithStates_1(2, 52, 6);
            }
        }
        return this.jjStartNfa_1(1, active0);
    }

    private int jjMoveStringLiteralDfa3_1(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_1(1, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(2, active0);
            return 3;
        }
        switch (this.curChar) {
            case 'e': {
                if ((active0 & 0x10000L) == 0L) break;
                return this.jjStartNfaWithStates_1(3, 16, 6);
            }
            case 'l': {
                if ((active0 & 0x40000L) == 0L) break;
                return this.jjStartNfaWithStates_1(3, 18, 6);
            }
            case 's': {
                return this.jjMoveStringLiteralDfa4_1(active0, 131072L);
            }
            case 't': {
                return this.jjMoveStringLiteralDfa4_1(active0, 0x600000000000L);
            }
        }
        return this.jjStartNfa_1(2, active0);
    }

    private int jjMoveStringLiteralDfa4_1(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_1(2, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(3, active0);
            return 4;
        }
        switch (this.curChar) {
            case 'a': {
                return this.jjMoveStringLiteralDfa5_1(active0, 0x400000000000L);
            }
            case 'e': {
                if ((active0 & 0x20000L) == 0L) break;
                return this.jjStartNfaWithStates_1(4, 17, 6);
            }
            case 'y': {
                if ((active0 & 0x200000000000L) == 0L) break;
                return this.jjStartNfaWithStates_1(4, 45, 6);
            }
        }
        return this.jjStartNfa_1(3, active0);
    }

    private int jjMoveStringLiteralDfa5_1(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_1(3, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(4, active0);
            return 5;
        }
        switch (this.curChar) {
            case 'n': {
                return this.jjMoveStringLiteralDfa6_1(active0, 0x400000000000L);
            }
        }
        return this.jjStartNfa_1(4, active0);
    }

    private int jjMoveStringLiteralDfa6_1(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_1(4, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(5, active0);
            return 6;
        }
        switch (this.curChar) {
            case 'c': {
                return this.jjMoveStringLiteralDfa7_1(active0, 0x400000000000L);
            }
        }
        return this.jjStartNfa_1(5, active0);
    }

    private int jjMoveStringLiteralDfa7_1(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_1(5, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(6, active0);
            return 7;
        }
        switch (this.curChar) {
            case 'e': {
                return this.jjMoveStringLiteralDfa8_1(active0, 0x400000000000L);
            }
        }
        return this.jjStartNfa_1(6, active0);
    }

    private int jjMoveStringLiteralDfa8_1(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_1(6, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(7, active0);
            return 8;
        }
        switch (this.curChar) {
            case 'o': {
                return this.jjMoveStringLiteralDfa9_1(active0, 0x400000000000L);
            }
        }
        return this.jjStartNfa_1(7, active0);
    }

    private int jjMoveStringLiteralDfa9_1(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_1(7, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(8, active0);
            return 9;
        }
        switch (this.curChar) {
            case 'f': {
                if ((active0 & 0x400000000000L) == 0L) break;
                return this.jjStartNfaWithStates_1(9, 46, 6);
            }
        }
        return this.jjStartNfa_1(8, active0);
    }

    private int jjStartNfaWithStates_1(int pos, int kind, int state) {
        this.jjmatchedKind = kind;
        this.jjmatchedPos = pos;
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            return pos + 1;
        }
        return this.jjMoveNfa_1(state, pos + 1);
    }

    private int jjMoveNfa_1(int startState, int curPos) {
        int startsAt = 0;
        this.jjnewStateCnt = 35;
        int i = 1;
        this.jjstateSet[0] = startState;
        int kind = Integer.MAX_VALUE;
        while (true) {
            if (++this.jjround == Integer.MAX_VALUE) {
                this.ReInitRounds();
            }
            if (this.curChar < '@') {
                long l = 1L << this.curChar;
                block58: do {
                    switch (this.jjstateSet[--i]) {
                        case 0: {
                            if ((0x3FF000000000000L & l) != 0L) {
                                if (kind > 11) {
                                    kind = 11;
                                }
                                this.jjCheckNAddStates(4, 8);
                                break;
                            }
                            if ((0x1800000000L & l) != 0L) {
                                if (kind > 58) {
                                    kind = 58;
                                }
                                this.jjCheckNAdd(6);
                                break;
                            }
                            if (this.curChar == '\'') {
                                this.jjCheckNAddStates(9, 13);
                                break;
                            }
                            if (this.curChar == '\"') {
                                this.jjCheckNAddStates(14, 18);
                                break;
                            }
                            if (this.curChar != '.') break;
                            this.jjCheckNAdd(1);
                            break;
                        }
                        case 1: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 12) {
                                kind = 12;
                            }
                            this.jjCheckNAddTwoStates(1, 2);
                            break;
                        }
                        case 3: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(4);
                            break;
                        }
                        case 4: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 12) {
                                kind = 12;
                            }
                            this.jjCheckNAdd(4);
                            break;
                        }
                        case 5: {
                            if ((0x1800000000L & l) == 0L) continue block58;
                            if (kind > 58) {
                                kind = 58;
                            }
                            this.jjCheckNAdd(6);
                            break;
                        }
                        case 6: {
                            if ((0x3FF001000000000L & l) == 0L) continue block58;
                            if (kind > 58) {
                                kind = 58;
                            }
                            this.jjCheckNAdd(6);
                            break;
                        }
                        case 7: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 11) {
                                kind = 11;
                            }
                            this.jjCheckNAddStates(4, 8);
                            break;
                        }
                        case 8: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 11) {
                                kind = 11;
                            }
                            this.jjCheckNAdd(8);
                            break;
                        }
                        case 9: {
                            if ((0x3FF000000000000L & l) == 0L) break;
                            this.jjCheckNAddTwoStates(9, 10);
                            break;
                        }
                        case 10: {
                            if (this.curChar != '.') continue block58;
                            if (kind > 12) {
                                kind = 12;
                            }
                            this.jjCheckNAddTwoStates(11, 12);
                            break;
                        }
                        case 11: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 12) {
                                kind = 12;
                            }
                            this.jjCheckNAddTwoStates(11, 12);
                            break;
                        }
                        case 13: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(14);
                            break;
                        }
                        case 14: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 12) {
                                kind = 12;
                            }
                            this.jjCheckNAdd(14);
                            break;
                        }
                        case 15: {
                            if ((0x3FF000000000000L & l) == 0L) break;
                            this.jjCheckNAddTwoStates(15, 16);
                            break;
                        }
                        case 17: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(18);
                            break;
                        }
                        case 18: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 12) {
                                kind = 12;
                            }
                            this.jjCheckNAdd(18);
                            break;
                        }
                        case 19: {
                            if (this.curChar != '\"') break;
                            this.jjCheckNAddStates(14, 18);
                            break;
                        }
                        case 20: {
                            if ((0xFFFFFFFBFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(19, 21);
                            break;
                        }
                        case 22: {
                            if (this.curChar != '\"') break;
                            this.jjCheckNAddStates(19, 21);
                            break;
                        }
                        case 23: {
                            if (this.curChar != '\"' || kind <= 14) continue block58;
                            kind = 14;
                            break;
                        }
                        case 24: {
                            if ((0xFFFFFFFBFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(24, 25);
                            break;
                        }
                        case 26: {
                            if ((0xFFFFFFFBFFFFFFFFL & l) == 0L || kind <= 15) continue block58;
                            kind = 15;
                            break;
                        }
                        case 27: {
                            if (this.curChar != '\'') break;
                            this.jjCheckNAddStates(9, 13);
                            break;
                        }
                        case 28: {
                            if ((0xFFFFFF7FFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(22, 24);
                            break;
                        }
                        case 30: {
                            if (this.curChar != '\'') break;
                            this.jjCheckNAddStates(22, 24);
                            break;
                        }
                        case 31: {
                            if (this.curChar != '\'' || kind <= 14) continue block58;
                            kind = 14;
                            break;
                        }
                        case 32: {
                            if ((0xFFFFFF7FFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(32, 33);
                            break;
                        }
                        case 34: {
                            if ((0xFFFFFF7FFFFFFFFFL & l) == 0L || kind <= 15) continue block58;
                            kind = 15;
                            break;
                        }
                    }
                } while (i != startsAt);
            } else if (this.curChar < '\u0080') {
                long l = 1L << (this.curChar & 0x3F);
                block59: do {
                    switch (this.jjstateSet[--i]) {
                        case 0: 
                        case 6: {
                            if ((0x7FFFFFE87FFFFFEL & l) == 0L) continue block59;
                            if (kind > 58) {
                                kind = 58;
                            }
                            this.jjCheckNAdd(6);
                            break;
                        }
                        case 2: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(25, 26);
                            break;
                        }
                        case 12: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(27, 28);
                            break;
                        }
                        case 16: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(29, 30);
                            break;
                        }
                        case 20: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(19, 21);
                            break;
                        }
                        case 21: {
                            if (this.curChar != '\\') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 22;
                            break;
                        }
                        case 22: {
                            if (this.curChar != '\\') break;
                            this.jjCheckNAddStates(19, 21);
                            break;
                        }
                        case 24: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) break;
                            this.jjAddStates(31, 32);
                            break;
                        }
                        case 25: {
                            if (this.curChar != '\\') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 26;
                            break;
                        }
                        case 26: 
                        case 34: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L || kind <= 15) continue block59;
                            kind = 15;
                            break;
                        }
                        case 28: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(22, 24);
                            break;
                        }
                        case 29: {
                            if (this.curChar != '\\') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 30;
                            break;
                        }
                        case 30: {
                            if (this.curChar != '\\') break;
                            this.jjCheckNAddStates(22, 24);
                            break;
                        }
                        case 32: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) break;
                            this.jjAddStates(33, 34);
                            break;
                        }
                        case 33: {
                            if (this.curChar != '\\') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 34;
                            break;
                        }
                    }
                } while (i != startsAt);
            } else {
                int hiByte = this.curChar >> 8;
                int i1 = hiByte >> 6;
                long l1 = 1L << (hiByte & 0x3F);
                int i2 = (this.curChar & 0xFF) >> 6;
                long l2 = 1L << (this.curChar & 0x3F);
                block60: do {
                    switch (this.jjstateSet[--i]) {
                        case 0: 
                        case 6: {
                            if (!ELParserTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block60;
                            if (kind > 58) {
                                kind = 58;
                            }
                            this.jjCheckNAdd(6);
                            break;
                        }
                        case 20: {
                            if (!ELParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) break;
                            this.jjAddStates(19, 21);
                            break;
                        }
                        case 24: {
                            if (!ELParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) break;
                            this.jjAddStates(31, 32);
                            break;
                        }
                        case 26: 
                        case 34: {
                            if (!ELParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2) || kind <= 15) continue block60;
                            kind = 15;
                            break;
                        }
                        case 28: {
                            if (!ELParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) break;
                            this.jjAddStates(22, 24);
                            break;
                        }
                        case 32: {
                            if (!ELParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) break;
                            this.jjAddStates(33, 34);
                            break;
                        }
                    }
                } while (i != startsAt);
            }
            if (kind != Integer.MAX_VALUE) {
                this.jjmatchedKind = kind;
                this.jjmatchedPos = curPos;
                kind = Integer.MAX_VALUE;
            }
            ++curPos;
            i = this.jjnewStateCnt;
            this.jjnewStateCnt = startsAt;
            if (i == (startsAt = 35 - this.jjnewStateCnt)) {
                return curPos;
            }
            try {
                this.curChar = this.input_stream.readChar();
            }
            catch (IOException e) {
                return curPos;
            }
        }
    }

    private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2) {
        switch (hiByte) {
            case 0: {
                return (jjbitVec2[i2] & l2) != 0L;
            }
        }
        return (jjbitVec0[i1] & l1) != 0L;
    }

    private static final boolean jjCanMove_1(int hiByte, int i1, int i2, long l1, long l2) {
        switch (hiByte) {
            case 0: {
                return (jjbitVec4[i2] & l2) != 0L;
            }
            case 48: {
                return (jjbitVec5[i2] & l2) != 0L;
            }
            case 49: {
                return (jjbitVec6[i2] & l2) != 0L;
            }
            case 51: {
                return (jjbitVec7[i2] & l2) != 0L;
            }
            case 61: {
                return (jjbitVec8[i2] & l2) != 0L;
            }
        }
        return (jjbitVec3[i1] & l1) != 0L;
    }

    public ELParserTokenManager(SimpleCharStream stream) {
        this.input_stream = stream;
    }

    public ELParserTokenManager(SimpleCharStream stream, int lexState) {
        this(stream);
        this.SwitchTo(lexState);
    }

    public void ReInit(SimpleCharStream stream) {
        this.jjnewStateCnt = 0;
        this.jjmatchedPos = 0;
        this.curLexState = this.defaultLexState;
        this.input_stream = stream;
        this.ReInitRounds();
    }

    private void ReInitRounds() {
        this.jjround = -2147483647;
        int i = 35;
        while (i-- > 0) {
            this.jjrounds[i] = Integer.MIN_VALUE;
        }
    }

    public void ReInit(SimpleCharStream stream, int lexState) {
        this.ReInit(stream);
        this.SwitchTo(lexState);
    }

    public void SwitchTo(int lexState) {
        if (lexState >= 3 || lexState < 0) {
            throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
        }
        this.curLexState = lexState;
    }

    protected Token jjFillToken() {
        String im = jjstrLiteralImages[this.jjmatchedKind];
        String curTokenImage = im == null ? this.input_stream.GetImage() : im;
        int beginLine = this.input_stream.getBeginLine();
        int beginColumn = this.input_stream.getBeginColumn();
        int endLine = this.input_stream.getEndLine();
        int endColumn = this.input_stream.getEndColumn();
        Token t2 = Token.newToken(this.jjmatchedKind);
        t2.kind = this.jjmatchedKind;
        t2.image = curTokenImage;
        t2.beginLine = beginLine;
        t2.endLine = endLine;
        t2.beginColumn = beginColumn;
        t2.endColumn = endColumn;
        return t2;
    }

    public Token getNextToken() {
        int curPos = 0;
        block13: while (true) {
            try {
                this.curChar = this.input_stream.BeginToken();
            }
            catch (IOException e) {
                this.jjmatchedKind = 0;
                Token matchedToken = this.jjFillToken();
                return matchedToken;
            }
            this.image = this.jjimage;
            this.image.setLength(0);
            this.jjimageLen = 0;
            switch (this.curLexState) {
                case 0: {
                    this.jjmatchedKind = Integer.MAX_VALUE;
                    this.jjmatchedPos = 0;
                    curPos = this.jjMoveStringLiteralDfa0_0();
                    break;
                }
                case 1: {
                    try {
                        this.input_stream.backup(0);
                        while (this.curChar <= ' ' && (0x100002600L & 1L << this.curChar) != 0L) {
                            this.curChar = this.input_stream.BeginToken();
                        }
                    }
                    catch (IOException e1) {
                        continue block13;
                    }
                    this.jjmatchedKind = Integer.MAX_VALUE;
                    this.jjmatchedPos = 0;
                    curPos = this.jjMoveStringLiteralDfa0_1();
                    if (this.jjmatchedPos != 0 || this.jjmatchedKind <= 62) break;
                    this.jjmatchedKind = 62;
                    break;
                }
                case 2: {
                    try {
                        this.input_stream.backup(0);
                        while (this.curChar <= ' ' && (0x100002600L & 1L << this.curChar) != 0L) {
                            this.curChar = this.input_stream.BeginToken();
                        }
                    }
                    catch (IOException e1) {
                        continue block13;
                    }
                    this.jjmatchedKind = Integer.MAX_VALUE;
                    this.jjmatchedPos = 0;
                    curPos = this.jjMoveStringLiteralDfa0_2();
                    if (this.jjmatchedPos != 0 || this.jjmatchedKind <= 62) break;
                    this.jjmatchedKind = 62;
                }
            }
            if (this.jjmatchedKind == Integer.MAX_VALUE) break;
            if (this.jjmatchedPos + 1 < curPos) {
                this.input_stream.backup(curPos - this.jjmatchedPos - 1);
            }
            if ((jjtoToken[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 0x3F)) != 0L) {
                Token matchedToken = this.jjFillToken();
                this.TokenLexicalActions(matchedToken);
                if (jjnewLexState[this.jjmatchedKind] != -1) {
                    this.curLexState = jjnewLexState[this.jjmatchedKind];
                }
                return matchedToken;
            }
            if (jjnewLexState[this.jjmatchedKind] == -1) continue;
            this.curLexState = jjnewLexState[this.jjmatchedKind];
        }
        int error_line = this.input_stream.getEndLine();
        int error_column = this.input_stream.getEndColumn();
        String error_after = null;
        boolean EOFSeen = false;
        try {
            this.input_stream.readChar();
            this.input_stream.backup(1);
        }
        catch (IOException e1) {
            EOFSeen = true;
            String string = error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
            if (this.curChar == '\n' || this.curChar == '\r') {
                ++error_line;
                error_column = 0;
            }
            ++error_column;
        }
        if (!EOFSeen) {
            this.input_stream.backup(1);
            error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
        }
        throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
    }

    void TokenLexicalActions(Token matchedToken) {
        switch (this.jjmatchedKind) {
            case 2: {
                this.image.append(jjstrLiteralImages[2]);
                this.lengthOfMatch = jjstrLiteralImages[2].length();
                this.stack.push(0);
                break;
            }
            case 3: {
                this.image.append(jjstrLiteralImages[3]);
                this.lengthOfMatch = jjstrLiteralImages[3].length();
                this.stack.push(0);
                break;
            }
            case 9: {
                this.image.append(jjstrLiteralImages[9]);
                this.lengthOfMatch = jjstrLiteralImages[9].length();
                this.stack.push(this.curLexState);
                break;
            }
            case 10: {
                this.image.append(jjstrLiteralImages[10]);
                this.lengthOfMatch = jjstrLiteralImages[10].length();
                this.SwitchTo(this.stack.pop());
                break;
            }
        }
    }

    private void jjCheckNAdd(int state) {
        if (this.jjrounds[state] != this.jjround) {
            this.jjstateSet[this.jjnewStateCnt++] = state;
            this.jjrounds[state] = this.jjround;
        }
    }

    private void jjAddStates(int start2, int end) {
        do {
            this.jjstateSet[this.jjnewStateCnt++] = jjnextStates[start2];
        } while (start2++ != end);
    }

    private void jjCheckNAddTwoStates(int state1, int state2) {
        this.jjCheckNAdd(state1);
        this.jjCheckNAdd(state2);
    }

    private void jjCheckNAddStates(int start2, int end) {
        do {
            this.jjCheckNAdd(jjnextStates[start2]);
        } while (start2++ != end);
    }
}

