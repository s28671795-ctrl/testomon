/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.selector;

import java.io.IOException;
import java.io.PrintStream;
import org.apache.activemq.selector.SelectorParserConstants;
import org.apache.activemq.selector.SimpleCharStream;
import org.apache.activemq.selector.Token;
import org.apache.activemq.selector.TokenMgrError;

public class SelectorParserTokenManager
implements SelectorParserConstants {
    public PrintStream debugStream = System.out;
    static final long[] jjbitVec0 = new long[]{-2L, -1L, -1L, -1L};
    static final long[] jjbitVec2 = new long[]{0L, 0L, -1L, -1L};
    static final int[] jjnextStates = new int[]{29, 30, 35, 36, 23, 24, 25, 1, 2, 4, 8, 9, 11, 19, 20, 33, 34, 37, 38};
    public static final String[] jjstrLiteralImages = new String[]{"", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "=", "<>", ">", ">=", "<", "<=", "(", ",", ")", "+", "-", "*", "/", "%"};
    public static final String[] lexStateNames = new String[]{"DEFAULT"};
    static final long[] jjtoToken = new long[]{4398012956417L};
    static final long[] jjtoSkip = new long[]{254L};
    static final long[] jjtoSpecial = new long[]{62L};
    protected SimpleCharStream input_stream;
    private final int[] jjrounds = new int[43];
    private final int[] jjstateSet = new int[86];
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

    private int jjStopAtPos(int pos, int kind) {
        this.jjmatchedKind = kind;
        this.jjmatchedPos = pos;
        return pos + 1;
    }

    private int jjMoveStringLiteralDfa0_0() {
        switch (this.curChar) {
            case '\t': {
                this.jjmatchedKind = 2;
                return this.jjMoveNfa_0(5, 0);
            }
            case '\n': {
                this.jjmatchedKind = 3;
                return this.jjMoveNfa_0(5, 0);
            }
            case '\f': {
                this.jjmatchedKind = 5;
                return this.jjMoveNfa_0(5, 0);
            }
            case '\r': {
                this.jjmatchedKind = 4;
                return this.jjMoveNfa_0(5, 0);
            }
            case ' ': {
                this.jjmatchedKind = 1;
                return this.jjMoveNfa_0(5, 0);
            }
            case '%': {
                this.jjmatchedKind = 41;
                return this.jjMoveNfa_0(5, 0);
            }
            case '(': {
                this.jjmatchedKind = 34;
                return this.jjMoveNfa_0(5, 0);
            }
            case ')': {
                this.jjmatchedKind = 36;
                return this.jjMoveNfa_0(5, 0);
            }
            case '*': {
                this.jjmatchedKind = 39;
                return this.jjMoveNfa_0(5, 0);
            }
            case '+': {
                this.jjmatchedKind = 37;
                return this.jjMoveNfa_0(5, 0);
            }
            case ',': {
                this.jjmatchedKind = 35;
                return this.jjMoveNfa_0(5, 0);
            }
            case '-': {
                this.jjmatchedKind = 38;
                return this.jjMoveNfa_0(5, 0);
            }
            case '/': {
                this.jjmatchedKind = 40;
                return this.jjMoveNfa_0(5, 0);
            }
            case '<': {
                this.jjmatchedKind = 32;
                return this.jjMoveStringLiteralDfa1_0(0x220000000L);
            }
            case '=': {
                this.jjmatchedKind = 28;
                return this.jjMoveNfa_0(5, 0);
            }
            case '>': {
                this.jjmatchedKind = 30;
                return this.jjMoveStringLiteralDfa1_0(0x80000000L);
            }
            case 'A': {
                return this.jjMoveStringLiteralDfa1_0(512L);
            }
            case 'B': {
                return this.jjMoveStringLiteralDfa1_0(2048L);
            }
            case 'E': {
                return this.jjMoveStringLiteralDfa1_0(8192L);
            }
            case 'F': {
                return this.jjMoveStringLiteralDfa1_0(131072L);
            }
            case 'I': {
                return this.jjMoveStringLiteralDfa1_0(49152L);
            }
            case 'L': {
                return this.jjMoveStringLiteralDfa1_0(4096L);
            }
            case 'N': {
                return this.jjMoveStringLiteralDfa1_0(262400L);
            }
            case 'O': {
                return this.jjMoveStringLiteralDfa1_0(1024L);
            }
            case 'T': {
                return this.jjMoveStringLiteralDfa1_0(65536L);
            }
            case 'X': {
                return this.jjMoveStringLiteralDfa1_0(0x180000L);
            }
            case 'a': {
                return this.jjMoveStringLiteralDfa1_0(512L);
            }
            case 'b': {
                return this.jjMoveStringLiteralDfa1_0(2048L);
            }
            case 'e': {
                return this.jjMoveStringLiteralDfa1_0(8192L);
            }
            case 'f': {
                return this.jjMoveStringLiteralDfa1_0(131072L);
            }
            case 'i': {
                return this.jjMoveStringLiteralDfa1_0(49152L);
            }
            case 'l': {
                return this.jjMoveStringLiteralDfa1_0(4096L);
            }
            case 'n': {
                return this.jjMoveStringLiteralDfa1_0(262400L);
            }
            case 'o': {
                return this.jjMoveStringLiteralDfa1_0(1024L);
            }
            case 't': {
                return this.jjMoveStringLiteralDfa1_0(65536L);
            }
            case 'x': {
                return this.jjMoveStringLiteralDfa1_0(0x180000L);
            }
        }
        return this.jjMoveNfa_0(5, 0);
    }

    private int jjMoveStringLiteralDfa1_0(long active0) {
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            return this.jjMoveNfa_0(5, 0);
        }
        switch (this.curChar) {
            case '=': {
                if ((active0 & 0x80000000L) != 0L) {
                    this.jjmatchedKind = 31;
                    this.jjmatchedPos = 1;
                    break;
                }
                if ((active0 & 0x200000000L) == 0L) break;
                this.jjmatchedKind = 33;
                this.jjmatchedPos = 1;
                break;
            }
            case '>': {
                if ((active0 & 0x20000000L) == 0L) break;
                this.jjmatchedKind = 29;
                this.jjmatchedPos = 1;
                break;
            }
            case 'A': {
                return this.jjMoveStringLiteralDfa2_0(active0, 131072L);
            }
            case 'E': {
                return this.jjMoveStringLiteralDfa2_0(active0, 2048L);
            }
            case 'I': {
                return this.jjMoveStringLiteralDfa2_0(active0, 4096L);
            }
            case 'N': {
                if ((active0 & 0x4000L) != 0L) {
                    this.jjmatchedKind = 14;
                    this.jjmatchedPos = 1;
                }
                return this.jjMoveStringLiteralDfa2_0(active0, 512L);
            }
            case 'O': {
                return this.jjMoveStringLiteralDfa2_0(active0, 256L);
            }
            case 'P': {
                return this.jjMoveStringLiteralDfa2_0(active0, 524288L);
            }
            case 'Q': {
                return this.jjMoveStringLiteralDfa2_0(active0, 0x100000L);
            }
            case 'R': {
                if ((active0 & 0x400L) != 0L) {
                    this.jjmatchedKind = 10;
                    this.jjmatchedPos = 1;
                }
                return this.jjMoveStringLiteralDfa2_0(active0, 65536L);
            }
            case 'S': {
                if ((active0 & 0x8000L) != 0L) {
                    this.jjmatchedKind = 15;
                    this.jjmatchedPos = 1;
                }
                return this.jjMoveStringLiteralDfa2_0(active0, 8192L);
            }
            case 'U': {
                return this.jjMoveStringLiteralDfa2_0(active0, 262144L);
            }
            case 'a': {
                return this.jjMoveStringLiteralDfa2_0(active0, 131072L);
            }
            case 'e': {
                return this.jjMoveStringLiteralDfa2_0(active0, 2048L);
            }
            case 'i': {
                return this.jjMoveStringLiteralDfa2_0(active0, 4096L);
            }
            case 'n': {
                if ((active0 & 0x4000L) != 0L) {
                    this.jjmatchedKind = 14;
                    this.jjmatchedPos = 1;
                }
                return this.jjMoveStringLiteralDfa2_0(active0, 512L);
            }
            case 'o': {
                return this.jjMoveStringLiteralDfa2_0(active0, 256L);
            }
            case 'p': {
                return this.jjMoveStringLiteralDfa2_0(active0, 524288L);
            }
            case 'q': {
                return this.jjMoveStringLiteralDfa2_0(active0, 0x100000L);
            }
            case 'r': {
                if ((active0 & 0x400L) != 0L) {
                    this.jjmatchedKind = 10;
                    this.jjmatchedPos = 1;
                }
                return this.jjMoveStringLiteralDfa2_0(active0, 65536L);
            }
            case 's': {
                if ((active0 & 0x8000L) != 0L) {
                    this.jjmatchedKind = 15;
                    this.jjmatchedPos = 1;
                }
                return this.jjMoveStringLiteralDfa2_0(active0, 8192L);
            }
            case 'u': {
                return this.jjMoveStringLiteralDfa2_0(active0, 262144L);
            }
        }
        return this.jjMoveNfa_0(5, 1);
    }

    private int jjMoveStringLiteralDfa2_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjMoveNfa_0(5, 1);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            return this.jjMoveNfa_0(5, 1);
        }
        switch (this.curChar) {
            case 'A': {
                return this.jjMoveStringLiteralDfa3_0(active0, 524288L);
            }
            case 'C': {
                return this.jjMoveStringLiteralDfa3_0(active0, 8192L);
            }
            case 'D': {
                if ((active0 & 0x200L) == 0L) break;
                this.jjmatchedKind = 9;
                this.jjmatchedPos = 2;
                break;
            }
            case 'K': {
                return this.jjMoveStringLiteralDfa3_0(active0, 4096L);
            }
            case 'L': {
                return this.jjMoveStringLiteralDfa3_0(active0, 393216L);
            }
            case 'T': {
                if ((active0 & 0x100L) != 0L) {
                    this.jjmatchedKind = 8;
                    this.jjmatchedPos = 2;
                }
                return this.jjMoveStringLiteralDfa3_0(active0, 2048L);
            }
            case 'U': {
                return this.jjMoveStringLiteralDfa3_0(active0, 0x110000L);
            }
            case 'a': {
                return this.jjMoveStringLiteralDfa3_0(active0, 524288L);
            }
            case 'c': {
                return this.jjMoveStringLiteralDfa3_0(active0, 8192L);
            }
            case 'd': {
                if ((active0 & 0x200L) == 0L) break;
                this.jjmatchedKind = 9;
                this.jjmatchedPos = 2;
                break;
            }
            case 'k': {
                return this.jjMoveStringLiteralDfa3_0(active0, 4096L);
            }
            case 'l': {
                return this.jjMoveStringLiteralDfa3_0(active0, 393216L);
            }
            case 't': {
                if ((active0 & 0x100L) != 0L) {
                    this.jjmatchedKind = 8;
                    this.jjmatchedPos = 2;
                }
                return this.jjMoveStringLiteralDfa3_0(active0, 2048L);
            }
            case 'u': {
                return this.jjMoveStringLiteralDfa3_0(active0, 0x110000L);
            }
        }
        return this.jjMoveNfa_0(5, 2);
    }

    private int jjMoveStringLiteralDfa3_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjMoveNfa_0(5, 2);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            return this.jjMoveNfa_0(5, 2);
        }
        switch (this.curChar) {
            case 'A': {
                return this.jjMoveStringLiteralDfa4_0(active0, 8192L);
            }
            case 'E': {
                if ((active0 & 0x1000L) != 0L) {
                    this.jjmatchedKind = 12;
                    this.jjmatchedPos = 3;
                } else if ((active0 & 0x10000L) != 0L) {
                    this.jjmatchedKind = 16;
                    this.jjmatchedPos = 3;
                }
                return this.jjMoveStringLiteralDfa4_0(active0, 0x100000L);
            }
            case 'L': {
                if ((active0 & 0x40000L) == 0L) break;
                this.jjmatchedKind = 18;
                this.jjmatchedPos = 3;
                break;
            }
            case 'S': {
                return this.jjMoveStringLiteralDfa4_0(active0, 131072L);
            }
            case 'T': {
                return this.jjMoveStringLiteralDfa4_0(active0, 524288L);
            }
            case 'W': {
                return this.jjMoveStringLiteralDfa4_0(active0, 2048L);
            }
            case 'a': {
                return this.jjMoveStringLiteralDfa4_0(active0, 8192L);
            }
            case 'e': {
                if ((active0 & 0x1000L) != 0L) {
                    this.jjmatchedKind = 12;
                    this.jjmatchedPos = 3;
                } else if ((active0 & 0x10000L) != 0L) {
                    this.jjmatchedKind = 16;
                    this.jjmatchedPos = 3;
                }
                return this.jjMoveStringLiteralDfa4_0(active0, 0x100000L);
            }
            case 'l': {
                if ((active0 & 0x40000L) == 0L) break;
                this.jjmatchedKind = 18;
                this.jjmatchedPos = 3;
                break;
            }
            case 's': {
                return this.jjMoveStringLiteralDfa4_0(active0, 131072L);
            }
            case 't': {
                return this.jjMoveStringLiteralDfa4_0(active0, 524288L);
            }
            case 'w': {
                return this.jjMoveStringLiteralDfa4_0(active0, 2048L);
            }
        }
        return this.jjMoveNfa_0(5, 3);
    }

    private int jjMoveStringLiteralDfa4_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjMoveNfa_0(5, 3);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            return this.jjMoveNfa_0(5, 3);
        }
        switch (this.curChar) {
            case 'E': {
                if ((active0 & 0x20000L) != 0L) {
                    this.jjmatchedKind = 17;
                    this.jjmatchedPos = 4;
                }
                return this.jjMoveStringLiteralDfa5_0(active0, 2048L);
            }
            case 'H': {
                if ((active0 & 0x80000L) == 0L) break;
                this.jjmatchedKind = 19;
                this.jjmatchedPos = 4;
                break;
            }
            case 'P': {
                return this.jjMoveStringLiteralDfa5_0(active0, 8192L);
            }
            case 'R': {
                return this.jjMoveStringLiteralDfa5_0(active0, 0x100000L);
            }
            case 'e': {
                if ((active0 & 0x20000L) != 0L) {
                    this.jjmatchedKind = 17;
                    this.jjmatchedPos = 4;
                }
                return this.jjMoveStringLiteralDfa5_0(active0, 2048L);
            }
            case 'h': {
                if ((active0 & 0x80000L) == 0L) break;
                this.jjmatchedKind = 19;
                this.jjmatchedPos = 4;
                break;
            }
            case 'p': {
                return this.jjMoveStringLiteralDfa5_0(active0, 8192L);
            }
            case 'r': {
                return this.jjMoveStringLiteralDfa5_0(active0, 0x100000L);
            }
        }
        return this.jjMoveNfa_0(5, 4);
    }

    private int jjMoveStringLiteralDfa5_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjMoveNfa_0(5, 4);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            return this.jjMoveNfa_0(5, 4);
        }
        switch (this.curChar) {
            case 'E': {
                if ((active0 & 0x2000L) != 0L) {
                    this.jjmatchedKind = 13;
                    this.jjmatchedPos = 5;
                }
                return this.jjMoveStringLiteralDfa6_0(active0, 2048L);
            }
            case 'Y': {
                if ((active0 & 0x100000L) == 0L) break;
                this.jjmatchedKind = 20;
                this.jjmatchedPos = 5;
                break;
            }
            case 'e': {
                if ((active0 & 0x2000L) != 0L) {
                    this.jjmatchedKind = 13;
                    this.jjmatchedPos = 5;
                }
                return this.jjMoveStringLiteralDfa6_0(active0, 2048L);
            }
            case 'y': {
                if ((active0 & 0x100000L) == 0L) break;
                this.jjmatchedKind = 20;
                this.jjmatchedPos = 5;
                break;
            }
        }
        return this.jjMoveNfa_0(5, 5);
    }

    private int jjMoveStringLiteralDfa6_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjMoveNfa_0(5, 5);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            return this.jjMoveNfa_0(5, 5);
        }
        switch (this.curChar) {
            case 'N': {
                if ((active0 & 0x800L) == 0L) break;
                this.jjmatchedKind = 11;
                this.jjmatchedPos = 6;
                break;
            }
            case 'n': {
                if ((active0 & 0x800L) == 0L) break;
                this.jjmatchedKind = 11;
                this.jjmatchedPos = 6;
                break;
            }
        }
        return this.jjMoveNfa_0(5, 6);
    }

    private int jjMoveNfa_0(int startState, int curPos) {
        int strKind = this.jjmatchedKind;
        int strPos = this.jjmatchedPos;
        int seenUpto = curPos + 1;
        this.input_stream.backup(seenUpto);
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            throw new Error("Internal Error");
        }
        curPos = 0;
        int startsAt = 0;
        this.jjnewStateCnt = 43;
        int i = 1;
        this.jjstateSet[0] = startState;
        int kind = Integer.MAX_VALUE;
        while (true) {
            if (++this.jjround == Integer.MAX_VALUE) {
                this.ReInitRounds();
            }
            if (this.curChar < '@') {
                long l = 1L << this.curChar;
                block65: do {
                    switch (this.jjstateSet[--i]) {
                        case 5: {
                            if ((0x3FF000000000000L & l) != 0L) {
                                this.jjCheckNAddStates(0, 3);
                            } else if (this.curChar == '$') {
                                if (kind > 27) {
                                    kind = 27;
                                }
                                this.jjCheckNAdd(27);
                            } else if (this.curChar == '\'') {
                                this.jjCheckNAddStates(4, 6);
                            } else if (this.curChar == '.') {
                                this.jjCheckNAdd(17);
                            } else if (this.curChar == '/') {
                                this.jjstateSet[this.jjnewStateCnt++] = 6;
                            } else if (this.curChar == '-') {
                                this.jjstateSet[this.jjnewStateCnt++] = 0;
                            }
                            if ((0x3FE000000000000L & l) != 0L) {
                                if (kind > 21) {
                                    kind = 21;
                                }
                                this.jjCheckNAddTwoStates(14, 15);
                                break;
                            }
                            if (this.curChar != '0') break;
                            if (kind > 23) {
                                kind = 23;
                            }
                            this.jjCheckNAddTwoStates(40, 42);
                            break;
                        }
                        case 0: {
                            if (this.curChar != '-') break;
                            this.jjCheckNAddStates(7, 9);
                            break;
                        }
                        case 1: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddStates(7, 9);
                            break;
                        }
                        case 2: {
                            if ((0x2400L & l) == 0L || kind <= 6) continue block65;
                            kind = 6;
                            break;
                        }
                        case 3: {
                            if (this.curChar != '\n' || kind <= 6) continue block65;
                            kind = 6;
                            break;
                        }
                        case 4: {
                            if (this.curChar != '\r') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 3;
                            break;
                        }
                        case 6: {
                            if (this.curChar != '*') break;
                            this.jjCheckNAddTwoStates(7, 8);
                            break;
                        }
                        case 7: {
                            if ((0xFFFFFBFFFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(7, 8);
                            break;
                        }
                        case 8: {
                            if (this.curChar != '*') break;
                            this.jjCheckNAddStates(10, 12);
                            break;
                        }
                        case 9: {
                            if ((0xFFFF7BFFFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(10, 8);
                            break;
                        }
                        case 10: {
                            if ((0xFFFFFBFFFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(10, 8);
                            break;
                        }
                        case 11: {
                            if (this.curChar != '/' || kind <= 7) continue block65;
                            kind = 7;
                            break;
                        }
                        case 12: {
                            if (this.curChar != '/') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 6;
                            break;
                        }
                        case 13: {
                            if ((0x3FE000000000000L & l) == 0L) continue block65;
                            if (kind > 21) {
                                kind = 21;
                            }
                            this.jjCheckNAddTwoStates(14, 15);
                            break;
                        }
                        case 14: {
                            if ((0x3FF000000000000L & l) == 0L) continue block65;
                            if (kind > 21) {
                                kind = 21;
                            }
                            this.jjCheckNAddTwoStates(14, 15);
                            break;
                        }
                        case 16: {
                            if (this.curChar != '.') break;
                            this.jjCheckNAdd(17);
                            break;
                        }
                        case 17: {
                            if ((0x3FF000000000000L & l) == 0L) continue block65;
                            if (kind > 24) {
                                kind = 24;
                            }
                            this.jjCheckNAddTwoStates(17, 18);
                            break;
                        }
                        case 19: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(20);
                            break;
                        }
                        case 20: {
                            if ((0x3FF000000000000L & l) == 0L) continue block65;
                            if (kind > 24) {
                                kind = 24;
                            }
                            this.jjCheckNAdd(20);
                            break;
                        }
                        case 21: 
                        case 22: {
                            if (this.curChar != '\'') break;
                            this.jjCheckNAddStates(4, 6);
                            break;
                        }
                        case 23: {
                            if (this.curChar != '\'') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 22;
                            break;
                        }
                        case 24: {
                            if ((0xFFFFFF7FFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(4, 6);
                            break;
                        }
                        case 25: {
                            if (this.curChar != '\'' || kind <= 26) continue block65;
                            kind = 26;
                            break;
                        }
                        case 26: {
                            if (this.curChar != '$') continue block65;
                            if (kind > 27) {
                                kind = 27;
                            }
                            this.jjCheckNAdd(27);
                            break;
                        }
                        case 27: {
                            if ((0x3FF001000000000L & l) == 0L) continue block65;
                            if (kind > 27) {
                                kind = 27;
                            }
                            this.jjCheckNAdd(27);
                            break;
                        }
                        case 28: {
                            if ((0x3FF000000000000L & l) == 0L) break;
                            this.jjCheckNAddStates(0, 3);
                            break;
                        }
                        case 29: {
                            if ((0x3FF000000000000L & l) == 0L) break;
                            this.jjCheckNAddTwoStates(29, 30);
                            break;
                        }
                        case 30: {
                            if (this.curChar != '.') continue block65;
                            if (kind > 24) {
                                kind = 24;
                            }
                            this.jjCheckNAddTwoStates(31, 32);
                            break;
                        }
                        case 31: {
                            if ((0x3FF000000000000L & l) == 0L) continue block65;
                            if (kind > 24) {
                                kind = 24;
                            }
                            this.jjCheckNAddTwoStates(31, 32);
                            break;
                        }
                        case 33: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(34);
                            break;
                        }
                        case 34: {
                            if ((0x3FF000000000000L & l) == 0L) continue block65;
                            if (kind > 24) {
                                kind = 24;
                            }
                            this.jjCheckNAdd(34);
                            break;
                        }
                        case 35: {
                            if ((0x3FF000000000000L & l) == 0L) break;
                            this.jjCheckNAddTwoStates(35, 36);
                            break;
                        }
                        case 37: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(38);
                            break;
                        }
                        case 38: {
                            if ((0x3FF000000000000L & l) == 0L) continue block65;
                            if (kind > 24) {
                                kind = 24;
                            }
                            this.jjCheckNAdd(38);
                            break;
                        }
                        case 39: {
                            if (this.curChar != '0') continue block65;
                            if (kind > 23) {
                                kind = 23;
                            }
                            this.jjCheckNAddTwoStates(40, 42);
                            break;
                        }
                        case 41: {
                            if ((0x3FF000000000000L & l) == 0L) continue block65;
                            if (kind > 22) {
                                kind = 22;
                            }
                            this.jjstateSet[this.jjnewStateCnt++] = 41;
                            break;
                        }
                        case 42: {
                            if ((0xFF000000000000L & l) == 0L) continue block65;
                            if (kind > 23) {
                                kind = 23;
                            }
                            this.jjCheckNAdd(42);
                            break;
                        }
                    }
                } while (i != startsAt);
            } else if (this.curChar < '\u0080') {
                long l = 1L << (this.curChar & 0x3F);
                block66: do {
                    switch (this.jjstateSet[--i]) {
                        case 5: 
                        case 27: {
                            if ((0x7FFFFFE87FFFFFEL & l) == 0L) continue block66;
                            if (kind > 27) {
                                kind = 27;
                            }
                            this.jjCheckNAdd(27);
                            break;
                        }
                        case 1: {
                            this.jjAddStates(7, 9);
                            break;
                        }
                        case 7: {
                            this.jjCheckNAddTwoStates(7, 8);
                            break;
                        }
                        case 9: 
                        case 10: {
                            this.jjCheckNAddTwoStates(10, 8);
                            break;
                        }
                        case 15: {
                            if ((0x100000001000L & l) == 0L || kind <= 21) continue block66;
                            kind = 21;
                            break;
                        }
                        case 18: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(13, 14);
                            break;
                        }
                        case 24: {
                            this.jjAddStates(4, 6);
                            break;
                        }
                        case 32: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(15, 16);
                            break;
                        }
                        case 36: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(17, 18);
                            break;
                        }
                        case 40: {
                            if ((0x100000001000000L & l) == 0L) break;
                            this.jjCheckNAdd(41);
                            break;
                        }
                        case 41: {
                            if ((0x7E0000007EL & l) == 0L) continue block66;
                            if (kind > 22) {
                                kind = 22;
                            }
                            this.jjCheckNAdd(41);
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
                do {
                    switch (this.jjstateSet[--i]) {
                        case 1: {
                            if (!SelectorParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) break;
                            this.jjAddStates(7, 9);
                            break;
                        }
                        case 7: {
                            if (!SelectorParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) break;
                            this.jjCheckNAddTwoStates(7, 8);
                            break;
                        }
                        case 9: 
                        case 10: {
                            if (!SelectorParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) break;
                            this.jjCheckNAddTwoStates(10, 8);
                            break;
                        }
                        case 24: {
                            if (!SelectorParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) break;
                            this.jjAddStates(4, 6);
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
            if (i == (startsAt = 43 - this.jjnewStateCnt)) break;
            try {
                this.curChar = this.input_stream.readChar();
            }
            catch (IOException e) {
                // empty catch block
                break;
            }
        }
        if (this.jjmatchedPos > strPos) {
            return curPos;
        }
        int toRet = Math.max(curPos, seenUpto);
        if (curPos < toRet) {
            i = toRet - Math.min(curPos, seenUpto);
            while (i-- > 0) {
                try {
                    this.curChar = this.input_stream.readChar();
                }
                catch (IOException e) {
                    throw new Error("Internal Error : Please send a bug report.");
                }
            }
        }
        if (this.jjmatchedPos < strPos) {
            this.jjmatchedKind = strKind;
            this.jjmatchedPos = strPos;
        } else if (this.jjmatchedPos == strPos && this.jjmatchedKind > strKind) {
            this.jjmatchedKind = strKind;
        }
        return toRet;
    }

    private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2) {
        switch (hiByte) {
            case 0: {
                return (jjbitVec2[i2] & l2) != 0L;
            }
        }
        return (jjbitVec0[i1] & l1) != 0L;
    }

    public SelectorParserTokenManager(SimpleCharStream stream) {
        this.input_stream = stream;
    }

    public SelectorParserTokenManager(SimpleCharStream stream, int lexState) {
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
        int i = 43;
        while (i-- > 0) {
            this.jjrounds[i] = Integer.MIN_VALUE;
        }
    }

    public void ReInit(SimpleCharStream stream, int lexState) {
        this.ReInit(stream);
        this.SwitchTo(lexState);
    }

    public void SwitchTo(int lexState) {
        if (lexState >= 1 || lexState < 0) {
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
        Token t2 = Token.newToken(this.jjmatchedKind, curTokenImage);
        t2.beginLine = beginLine;
        t2.endLine = endLine;
        t2.beginColumn = beginColumn;
        t2.endColumn = endColumn;
        return t2;
    }

    public Token getNextToken() {
        Token specialToken = null;
        int curPos = 0;
        while (true) {
            Token matchedToken;
            try {
                this.curChar = this.input_stream.BeginToken();
            }
            catch (IOException e) {
                this.jjmatchedKind = 0;
                matchedToken = this.jjFillToken();
                matchedToken.specialToken = specialToken;
                return matchedToken;
            }
            this.jjmatchedKind = Integer.MAX_VALUE;
            this.jjmatchedPos = 0;
            curPos = this.jjMoveStringLiteralDfa0_0();
            if (this.jjmatchedKind == Integer.MAX_VALUE) break;
            if (this.jjmatchedPos + 1 < curPos) {
                this.input_stream.backup(curPos - this.jjmatchedPos - 1);
            }
            if ((jjtoToken[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 0x3F)) != 0L) {
                matchedToken = this.jjFillToken();
                matchedToken.specialToken = specialToken;
                return matchedToken;
            }
            if ((jjtoSpecial[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 0x3F)) == 0L) continue;
            matchedToken = this.jjFillToken();
            if (specialToken == null) {
                specialToken = matchedToken;
                continue;
            }
            matchedToken.specialToken = specialToken;
            specialToken = specialToken.next = matchedToken;
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

