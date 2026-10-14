/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util.grammar;

import java.io.IOException;
import java.io.PrintStream;
import liquibase.util.grammar.SimpleCharStream;
import liquibase.util.grammar.SimpleSqlGrammarConstants;
import liquibase.util.grammar.Token;
import liquibase.util.grammar.TokenMgrError;

public class SimpleSqlGrammarTokenManager
implements SimpleSqlGrammarConstants {
    public PrintStream debugStream = System.out;
    static final long[] jjbitVec0 = new long[]{0L, 0L, 0x100000020L, 0L};
    static final long[] jjbitVec1 = new long[]{0L, 0L, 1L, 0L};
    static final long[] jjbitVec2 = new long[]{16384L, 0L, 0L, 0L};
    static final long[] jjbitVec3 = new long[]{144036023255039L, 0x180000000L, 0L, 0L};
    static final long[] jjbitVec4 = new long[]{1L, 0L, 0L, 0L};
    static final long[] jjbitVec5 = new long[]{0L, 0L, 0L, Long.MIN_VALUE};
    static final long[] jjbitVec6 = new long[]{-2L, -1L, -1L, -1L};
    static final long[] jjbitVec8 = new long[]{0L, 0L, -1L, -1L};
    static final long[] jjbitVec9 = new long[]{-2L, -1L, -1L, Long.MAX_VALUE};
    static final long[] jjbitVec10 = new long[]{0L, 0L, -8589934592L, -1L};
    static final long[] jjbitVec11 = new long[]{-1L, 0x3FFFFFFFFFL, 0L, 0L};
    static final long[] jjbitVec12 = new long[]{-4503598551400446L, -8193L, -17388175097857L, 1297036692691091455L};
    static final long[] jjbitVec13 = new long[]{0L, 0L, 0L, -8388609L};
    static final long[] jjbitVec14 = new long[]{-1L, -1L, -1L, 88094074470339L};
    static final long[] jjbitVec15 = new long[]{0L, -4837147474772623360L, -17179879616L, -18014398509481985L};
    static final long[] jjbitVec16 = new long[]{-1L, -1L, -1021L, -1L};
    static final long[] jjbitVec17 = new long[]{-281474976710657L, -8547991553L, 255L, 0x707FFFFFF0000L};
    static final long[] jjbitVec18 = new long[]{-4294967296L, -351843720886273L, -1L, -7205547885240254465L};
    static final long[] jjbitVec19 = new long[]{0xFFFFFFFD0000L, -8192L, 563224831328255L, 301749971126844416L};
    static final long[] jjbitVec20 = new long[]{1168302407679L, 0x1FFFFFFL, 0x1FFFFF00000000L, 0L};
    static final long[] jjbitVec21 = new long[]{2594073385365405680L, -562932790263808L, 2577745637692514273L, 844440767840256L};
    static final long[] jjbitVec22 = new long[]{247132830528276448L, 7881300924956672L, 2589004636761079776L, 144115200960823296L};
    static final long[] jjbitVec23 = new long[]{2589004636760940512L, 562965791113216L, 288167810662516712L, 65536L};
    static final long[] jjbitVec24 = new long[]{2594071186342010848L, 0x307000000L, 2589567586714640352L, 1688863818907648L};
    static final long[] jjbitVec25 = new long[]{2882303761516978144L, -288230361119309824L, 3457638613854978016L, 127L};
    static final long[] jjbitVec26 = new long[]{0xDFFFFFFFFFFFEL, 127L, 2309762420256548246L, 0xF000005FL};
    static final long[] jjbitVec27 = new long[]{1L, 0x1FFFFFFFFEFFL, 7936L, 0L};
    static final long[] jjbitVec28 = new long[]{-9223363240761753601L, -8514196127940608L, -4294950909L, -576460752303480641L};
    static final long[] jjbitVec29 = new long[]{-1L, -3263218177L, 9168765891372858879L, -8388803L};
    static final long[] jjbitVec30 = new long[]{-12713985L, 0x7FFFFFFL, -4294901761L, 0x3F3FFFFFFFFFFFFFL};
    static final long[] jjbitVec31 = new long[]{-1L, -105553116266497L, -4160749570L, 143561034215456767L};
    static final long[] jjbitVec32 = new long[]{1125895612129279L, 527761286627327L, 0xFFFFFFFFFFFFFL, 0x10800000L};
    static final long[] jjbitVec33 = new long[]{-4294967296L, 0xFFFFFFFFFFFFFFL, -274877906944001L, 0x3FFFFFFFFFFFFFL};
    static final long[] jjbitVec34 = new long[]{Integer.MAX_VALUE, 8796093022142464L, -263882790666241L, 1023L};
    static final long[] jjbitVec35 = new long[]{-4286578689L, 0x1FFFFFL, 0x8000000000L, 0L};
    static final long[] jjbitVec36 = new long[]{0xFFFFFFFFFFFE0L, 4064L, -288019261329244168L, 0x3FFFFFFFFFL};
    static final long[] jjbitVec37 = new long[]{0xFFFFFFFFFL, 4611686018360336384L, 0L, 28110114275721216L};
    static final long[] jjbitVec38 = new long[]{-1L, -1L, -1L, 0L};
    static final long[] jjbitVec39 = new long[]{-3233808385L, 0x3FFFFFFFAAFF3F3FL, 0x5FDFFFFFFFFFFFFFL, 2295745090394464220L};
    static final long[] jjbitVec40 = new long[]{0L, -9222809086901354496L, 0x1FFF0000L, 0L};
    static final long[] jjbitVec41 = new long[]{-864764451089286012L, -4294949920L, 24L, 0L};
    static final long[] jjbitVec42 = new long[]{-140737488355329L, -2147483649L, -1L, 3509778554814463L};
    static final long[] jjbitVec43 = new long[]{-245465970900993L, 0x80FFFFFFFFFFL, 0x7F7F7F7F007FFFFFL, 0x7F7F7F7FL};
    static final long[] jjbitVec44 = new long[]{0x800000000000L, 0L, 0L, 0L};
    static final long[] jjbitVec45 = new long[]{-1L, -2L, -4823449601L, -576460752303423489L};
    static final long[] jjbitVec46 = new long[]{-492581209243680L, -1L, 0x7FFFFFF00007FFFL, -281474976710656L};
    static final long[] jjbitVec47 = new long[]{-1L, -1L, 0x3FFFFFFFFFFFFFL, 0L};
    static final long[] jjbitVec48 = new long[]{-1L, -1L, -1L, 0x3FFFFFL};
    static final long[] jjbitVec49 = new long[]{-1L, -1L, 8191L, 0x3FFFFFFFFFFF0000L};
    static final long[] jjbitVec50 = new long[]{13198434443263L, -9223231299366420481L, -3221225473L, 0x3FFFFFFFFFL};
    static final long[] jjbitVec51 = new long[]{-12893290496L, -1L, 0xFF3FFFFFFFF9FFL, -36028797018963968L};
    static final long[] jjbitVec52 = new long[]{0x7FFFFF7BBL, 0xFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFCL, 2953235455648202752L};
    static final long[] jjbitVec53 = new long[]{-281200098804736L, 2305843004918726783L, 0x7FFFFFFFFFFF0L, 8935422993945886720L};
    static final long[] jjbitVec54 = new long[]{0x1FFFFFFFFFFL, -4287426849551675401L, 4495436853045886975L, 7890092085477381L};
    static final long[] jjbitVec55 = new long[]{-141291530846594L, -281200233021441L, -1L, 0x7FFFFFFFFL};
    static final long[] jjbitVec56 = new long[]{-1L, -1L, -281406257233921L, 0xFFFFFFFFFFFF87FL};
    static final long[] jjbitVec57 = new long[]{-1L, -211106232532993L, -1L, 0x3FFFFFFL};
    static final long[] jjbitVec58 = new long[]{6881498030004502655L, -37L, 0x3FFFFFFFFFFFFL, -524288L};
    static final long[] jjbitVec59 = new long[]{0x3FFFFFFFFFFFFFFFL, -65536L, -196609L, 0xFFF0000000000FFL};
    static final long[] jjbitVec60 = new long[]{0L, -9288674231451648L, -1L, 0x1FFFFFFFFFFFFFFFL};
    static final long[] jjbitVec61 = new long[]{-1L, -1L, -1L, 0xFFFFFFFFFFFFL};
    public static final String[] jjstrLiteralImages = new String[]{"", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
    static final int[] jjnextStates = new int[]{44, 11, 45, 46, 50, 51, 54, 86, 28, 29, 31, 32, 34, 88, 90, 56, 57, 58, 72, 19, 20, 22, 23, 25, 6, 7, 9, 28, 29, 31, 32, 34, 17, 26, 56, 57, 58, 59, 60, 62, 66, 69, 57, 58, 61, 67, 68, 81, 82, 83, 84, 74, 77, 14, 15, 48, 49, 52, 53, 70, 71, 86, 87, 88, 89};
    int curLexState = 0;
    int defaultLexState = 0;
    int jjnewStateCnt;
    int jjround;
    int jjmatchedPos;
    int jjmatchedKind;
    public static final String[] lexStateNames = new String[]{"DEFAULT"};
    public static final int[] jjnewLexState = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    static final long[] jjtoToken = new long[]{3015103L};
    static final long[] jjtoSkip = new long[]{0L};
    static final long[] jjtoSpecial = new long[]{0L};
    static final long[] jjtoMore = new long[]{0L};
    protected SimpleCharStream input_stream;
    private final int[] jjrounds = new int[91];
    private final int[] jjstateSet = new int[182];
    private final StringBuilder jjimage;
    private StringBuilder image = this.jjimage = new StringBuilder();
    private int jjimageLen;
    private int lengthOfMatch;
    protected int curChar;

    public void setDebugStream(PrintStream ds) {
        this.debugStream = ds;
    }

    private int jjMoveStringLiteralDfa0_0() {
        return this.jjMoveNfa_0(3, 0);
    }

    private int jjMoveNfa_0(int startState, int curPos) {
        int startsAt = 0;
        this.jjnewStateCnt = 91;
        int i = 1;
        this.jjstateSet[0] = startState;
        int kind = Integer.MAX_VALUE;
        while (true) {
            if (++this.jjround == Integer.MAX_VALUE) {
                this.ReInitRounds();
            }
            if (this.curChar < 64) {
                long l = 1L << this.curChar;
                block140: do {
                    switch (this.jjstateSet[--i]) {
                        case 3: {
                            if ((0xFC00FFFE00000000L & l) != 0L) {
                                if (kind > 21) {
                                    kind = 21;
                                }
                            } else if ((0x3FF000000000000L & l) != 0L) {
                                if (kind > 5) {
                                    kind = 5;
                                }
                                this.jjCheckNAddStates(0, 6);
                            } else if ((0x100002600L & l) != 0L) {
                                if (kind > 1) {
                                    kind = 1;
                                }
                                this.jjCheckNAdd(0);
                            }
                            if (this.curChar == 34) {
                                this.jjCheckNAddStates(7, 14);
                                break;
                            }
                            if (this.curChar == 36) {
                                if (kind > 8) {
                                    kind = 8;
                                }
                                this.jjCheckNAddStates(15, 18);
                                break;
                            }
                            if (this.curChar == 39) {
                                this.jjCheckNAddStates(19, 23);
                                break;
                            }
                            if (this.curChar == 46) {
                                this.jjCheckNAdd(12);
                                break;
                            }
                            if (this.curChar == 47) {
                                this.jjstateSet[this.jjnewStateCnt++] = 4;
                                break;
                            }
                            if (this.curChar != 45) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 1;
                            break;
                        }
                        case 0: {
                            if ((0x100002600L & l) == 0L) continue block140;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAdd(0);
                            break;
                        }
                        case 1: {
                            if (this.curChar != 45) continue block140;
                            if (kind > 2) {
                                kind = 2;
                            }
                            this.jjCheckNAdd(2);
                            break;
                        }
                        case 2: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0L) continue block140;
                            if (kind > 2) {
                                kind = 2;
                            }
                            this.jjCheckNAdd(2);
                            break;
                        }
                        case 4: {
                            if (this.curChar != 42) break;
                            this.jjCheckNAddTwoStates(5, 6);
                            break;
                        }
                        case 5: {
                            if ((0xFFFFFBFFFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(5, 6);
                            break;
                        }
                        case 6: {
                            if (this.curChar != 42) break;
                            this.jjCheckNAddStates(24, 26);
                            break;
                        }
                        case 7: {
                            if ((0xFFFF7BFFFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(8, 6);
                            break;
                        }
                        case 8: {
                            if ((0xFFFFFBFFFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(8, 6);
                            break;
                        }
                        case 9: {
                            if (this.curChar != 47 || kind <= 3) continue block140;
                            kind = 3;
                            break;
                        }
                        case 10: {
                            if (this.curChar != 47) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 4;
                            break;
                        }
                        case 11: {
                            if (this.curChar != 46) break;
                            this.jjCheckNAdd(12);
                            break;
                        }
                        case 12: {
                            if ((0x3FF000000000000L & l) == 0L) continue block140;
                            if (kind > 4) {
                                kind = 4;
                            }
                            this.jjCheckNAddTwoStates(12, 13);
                            break;
                        }
                        case 14: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(15);
                            break;
                        }
                        case 15: {
                            if ((0x3FF000000000000L & l) == 0L) continue block140;
                            if (kind > 4) {
                                kind = 4;
                            }
                            this.jjCheckNAdd(15);
                            break;
                        }
                        case 17: 
                        case 18: {
                            if (this.curChar != 39) break;
                            this.jjCheckNAddStates(19, 23);
                            break;
                        }
                        case 19: {
                            if (this.curChar != 39) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 18;
                            break;
                        }
                        case 21: {
                            this.jjCheckNAddStates(19, 23);
                            break;
                        }
                        case 22: {
                            if ((0xFFFFFF7FFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(19, 23);
                            break;
                        }
                        case 23: 
                        case 24: {
                            if (this.curChar != 39 || kind <= 17) continue block140;
                            kind = 17;
                            break;
                        }
                        case 26: 
                        case 27: {
                            if (this.curChar != 34) break;
                            this.jjCheckNAddStates(27, 31);
                            break;
                        }
                        case 28: {
                            if (this.curChar != 34) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 27;
                            break;
                        }
                        case 30: {
                            this.jjCheckNAddStates(27, 31);
                            break;
                        }
                        case 31: {
                            if ((0xFFFFFFFBFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(27, 31);
                            break;
                        }
                        case 32: 
                        case 33: {
                            if (this.curChar != 34 || kind <= 17) continue block140;
                            kind = 17;
                            break;
                        }
                        case 37: {
                            if (this.curChar != 56) break;
                            this.jjAddStates(32, 33);
                            break;
                        }
                        case 42: {
                            if ((0xFC00FFFE00000000L & l) == 0L || kind <= 21) continue block140;
                            kind = 21;
                            break;
                        }
                        case 43: {
                            if ((0x3FF000000000000L & l) == 0L) continue block140;
                            if (kind > 5) {
                                kind = 5;
                            }
                            this.jjCheckNAddStates(0, 6);
                            break;
                        }
                        case 44: {
                            if ((0x3FF000000000000L & l) == 0L) break;
                            this.jjCheckNAddTwoStates(44, 11);
                            break;
                        }
                        case 45: {
                            if ((0x3FF000000000000L & l) == 0L) break;
                            this.jjCheckNAddTwoStates(45, 46);
                            break;
                        }
                        case 46: {
                            if (this.curChar != 46) continue block140;
                            if (kind > 4) {
                                kind = 4;
                            }
                            this.jjstateSet[this.jjnewStateCnt++] = 47;
                            break;
                        }
                        case 48: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(49);
                            break;
                        }
                        case 49: {
                            if ((0x3FF000000000000L & l) == 0L) continue block140;
                            if (kind > 4) {
                                kind = 4;
                            }
                            this.jjCheckNAdd(49);
                            break;
                        }
                        case 50: {
                            if ((0x3FF000000000000L & l) == 0L) break;
                            this.jjCheckNAddTwoStates(50, 51);
                            break;
                        }
                        case 52: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(53);
                            break;
                        }
                        case 53: {
                            if ((0x3FF000000000000L & l) == 0L) continue block140;
                            if (kind > 4) {
                                kind = 4;
                            }
                            this.jjCheckNAdd(53);
                            break;
                        }
                        case 54: {
                            if ((0x3FF000000000000L & l) == 0L) continue block140;
                            if (kind > 5) {
                                kind = 5;
                            }
                            this.jjCheckNAdd(54);
                            break;
                        }
                        case 55: {
                            if (this.curChar != 36) continue block140;
                            if (kind > 8) {
                                kind = 8;
                            }
                            this.jjCheckNAddStates(15, 18);
                            break;
                        }
                        case 56: {
                            if ((0x3FF001800000000L & l) == 0L) break;
                            this.jjCheckNAddStates(34, 36);
                            break;
                        }
                        case 57: {
                            if ((0x100002400L & l) == 0L) break;
                            this.jjCheckNAddTwoStates(57, 58);
                            break;
                        }
                        case 58: {
                            if (this.curChar != 46) break;
                            this.jjCheckNAddStates(37, 41);
                            break;
                        }
                        case 59: {
                            if ((0x100002400L & l) == 0L) break;
                            this.jjCheckNAddStates(37, 41);
                            break;
                        }
                        case 60: {
                            if (this.curChar != 36) continue block140;
                            if (kind > 7) {
                                kind = 7;
                            }
                            this.jjCheckNAddStates(42, 44);
                            break;
                        }
                        case 61: {
                            if ((0x3FF001800000000L & l) == 0L) continue block140;
                            if (kind > 7) {
                                kind = 7;
                            }
                            this.jjCheckNAddStates(42, 44);
                            break;
                        }
                        case 63: {
                            if ((0xFC00FFFFFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(64, 65);
                            break;
                        }
                        case 64: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(64, 65);
                            break;
                        }
                        case 67: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0L) break;
                            this.jjAddStates(45, 46);
                            break;
                        }
                        case 69: {
                            if (this.curChar != 34) break;
                            this.jjCheckNAdd(70);
                            break;
                        }
                        case 70: {
                            if ((0xFFFFFFFBFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(70, 71);
                            break;
                        }
                        case 71: {
                            if (this.curChar != 34) continue block140;
                            if (kind > 7) {
                                kind = 7;
                            }
                            this.jjCheckNAddTwoStates(57, 58);
                            break;
                        }
                        case 72: {
                            if ((0x3FF001800000000L & l) == 0L) continue block140;
                            if (kind > 8) {
                                kind = 8;
                            }
                            this.jjCheckNAdd(72);
                            break;
                        }
                        case 74: {
                            if ((0xFC00FFFFFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(75, 76);
                            break;
                        }
                        case 75: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(75, 76);
                            break;
                        }
                        case 77: {
                            if ((0xFC00FFFFFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(78, 79);
                            break;
                        }
                        case 78: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(78, 79);
                            break;
                        }
                        case 81: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0L) break;
                            this.jjAddStates(47, 48);
                            break;
                        }
                        case 83: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0L) break;
                            this.jjAddStates(49, 50);
                            break;
                        }
                        case 85: {
                            if (this.curChar != 34) break;
                            this.jjCheckNAddStates(7, 14);
                            break;
                        }
                        case 86: {
                            if ((0xFFFFFFFBFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(86, 87);
                            break;
                        }
                        case 87: {
                            if (this.curChar != 34) break;
                            this.jjCheckNAddTwoStates(57, 58);
                            break;
                        }
                        case 88: {
                            if ((0xFFFFFFFBFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(88, 89);
                            break;
                        }
                        case 89: {
                            if (this.curChar != 34 || kind <= 18) continue block140;
                            kind = 18;
                            break;
                        }
                        case 90: {
                            if (this.curChar != 34 || kind <= 19) continue block140;
                            kind = 19;
                            break;
                        }
                    }
                } while (i != startsAt);
            } else if (this.curChar < 128) {
                long l = 1L << (this.curChar & 0x3F);
                block141: do {
                    switch (this.jjstateSet[--i]) {
                        case 3: {
                            if ((0x7FFFFFE87FFFFFEL & l) != 0L) {
                                if (kind > 8) {
                                    kind = 8;
                                }
                                this.jjCheckNAddStates(15, 18);
                            } else if (this.curChar == 96) {
                                this.jjCheckNAddTwoStates(81, 83);
                            } else if (this.curChar == 91) {
                                this.jjAddStates(51, 52);
                            }
                            if ((0x78000201F8080201L & l) != 0L) {
                                if (kind > 21) {
                                    kind = 21;
                                }
                            } else if ((0x24402400244024L & l) != 0L) {
                                this.jjCheckNAddTwoStates(17, 26);
                            }
                            if ((0x4000000040000L & l) != 0L) {
                                this.jjstateSet[this.jjnewStateCnt++] = 35;
                                break;
                            }
                            if (this.curChar != 95) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 40;
                            break;
                        }
                        case 2: {
                            if (kind > 2) {
                                kind = 2;
                            }
                            this.jjstateSet[this.jjnewStateCnt++] = 2;
                            break;
                        }
                        case 5: {
                            this.jjCheckNAddTwoStates(5, 6);
                            break;
                        }
                        case 7: 
                        case 8: {
                            this.jjCheckNAddTwoStates(8, 6);
                            break;
                        }
                        case 13: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(53, 54);
                            break;
                        }
                        case 16: {
                            if ((0x24402400244024L & l) == 0L) break;
                            this.jjCheckNAddTwoStates(17, 26);
                            break;
                        }
                        case 20: {
                            if (this.curChar != 92) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 21;
                            break;
                        }
                        case 21: {
                            this.jjCheckNAddStates(19, 23);
                            break;
                        }
                        case 22: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(19, 23);
                            break;
                        }
                        case 25: {
                            if (this.curChar != 92) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 24;
                            break;
                        }
                        case 29: {
                            if (this.curChar != 92) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 30;
                            break;
                        }
                        case 30: {
                            this.jjCheckNAddStates(27, 31);
                            break;
                        }
                        case 31: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(27, 31);
                            break;
                        }
                        case 34: {
                            if (this.curChar != 92) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 33;
                            break;
                        }
                        case 35: {
                            if ((0x400000004L & l) == 0L) break;
                            this.jjCheckNAddTwoStates(17, 26);
                            break;
                        }
                        case 36: {
                            if ((0x4000000040000L & l) == 0L) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 35;
                            break;
                        }
                        case 38: {
                            if ((0x4000000040L & l) == 0L) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 37;
                            break;
                        }
                        case 39: {
                            if ((0x10000000100000L & l) == 0L) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 38;
                            break;
                        }
                        case 40: {
                            if ((0x20000000200000L & l) == 0L) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 39;
                            break;
                        }
                        case 41: {
                            if (this.curChar != 95) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 40;
                            break;
                        }
                        case 42: {
                            if ((0x78000201F8080201L & l) == 0L || kind <= 21) continue block141;
                            kind = 21;
                            break;
                        }
                        case 47: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(55, 56);
                            break;
                        }
                        case 51: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(57, 58);
                            break;
                        }
                        case 55: {
                            if ((0x7FFFFFE87FFFFFEL & l) == 0L) continue block141;
                            if (kind > 8) {
                                kind = 8;
                            }
                            this.jjCheckNAddStates(15, 18);
                            break;
                        }
                        case 56: {
                            if ((0x7FFFFFE87FFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(34, 36);
                            break;
                        }
                        case 60: {
                            if ((0x7FFFFFE87FFFFFEL & l) == 0L) continue block141;
                            if (kind > 7) {
                                kind = 7;
                            }
                            this.jjCheckNAddStates(42, 44);
                            break;
                        }
                        case 61: {
                            if ((0x7FFFFFE87FFFFFFL & l) == 0L) continue block141;
                            if (kind > 7) {
                                kind = 7;
                            }
                            this.jjCheckNAddStates(42, 44);
                            break;
                        }
                        case 62: {
                            if (this.curChar != 91) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 63;
                            break;
                        }
                        case 63: 
                        case 64: {
                            if ((0xFFFFFFFFDFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(64, 65);
                            break;
                        }
                        case 65: {
                            if (this.curChar != 93) continue block141;
                            if (kind > 7) {
                                kind = 7;
                            }
                            this.jjCheckNAddTwoStates(57, 58);
                            break;
                        }
                        case 66: {
                            if (this.curChar != 96) break;
                            this.jjCheckNAdd(67);
                            break;
                        }
                        case 67: {
                            if ((0xFFFFFFFEFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(67, 68);
                            break;
                        }
                        case 68: {
                            if (this.curChar != 96) continue block141;
                            if (kind > 7) {
                                kind = 7;
                            }
                            this.jjCheckNAddTwoStates(57, 58);
                            break;
                        }
                        case 70: {
                            this.jjAddStates(59, 60);
                            break;
                        }
                        case 72: {
                            if ((0x7FFFFFE87FFFFFFL & l) == 0L) continue block141;
                            if (kind > 8) {
                                kind = 8;
                            }
                            this.jjCheckNAdd(72);
                            break;
                        }
                        case 73: {
                            if (this.curChar != 91) break;
                            this.jjAddStates(51, 52);
                            break;
                        }
                        case 74: 
                        case 75: {
                            if ((0xFFFFFFFFDFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(75, 76);
                            break;
                        }
                        case 76: {
                            if (this.curChar != 93) break;
                            this.jjCheckNAddTwoStates(57, 58);
                            break;
                        }
                        case 77: 
                        case 78: {
                            if ((0xFFFFFFFFDFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(78, 79);
                            break;
                        }
                        case 79: {
                            if (this.curChar != 93 || kind <= 18) continue block141;
                            kind = 18;
                            break;
                        }
                        case 80: {
                            if (this.curChar != 96) break;
                            this.jjCheckNAddTwoStates(81, 83);
                            break;
                        }
                        case 81: {
                            if ((0xFFFFFFFEFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(81, 82);
                            break;
                        }
                        case 82: {
                            if (this.curChar != 96) break;
                            this.jjCheckNAddTwoStates(57, 58);
                            break;
                        }
                        case 83: {
                            if ((0xFFFFFFFEFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(83, 84);
                            break;
                        }
                        case 84: {
                            if (this.curChar != 96 || kind <= 18) continue block141;
                            kind = 18;
                            break;
                        }
                        case 86: {
                            this.jjAddStates(61, 62);
                            break;
                        }
                        case 88: {
                            this.jjAddStates(63, 64);
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
                block142: do {
                    switch (this.jjstateSet[--i]) {
                        case 3: {
                            if (SimpleSqlGrammarTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                                if (kind > 1) {
                                    kind = 1;
                                }
                                this.jjCheckNAdd(0);
                            }
                            if (SimpleSqlGrammarTokenManager.jjCanMove_2(hiByte, i1, i2, l1, l2) && kind > 21) {
                                kind = 21;
                            }
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_3(hiByte, i1, i2, l1, l2)) continue block142;
                            if (kind > 8) {
                                kind = 8;
                            }
                            this.jjCheckNAddStates(15, 18);
                            break;
                        }
                        case 0: {
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) continue block142;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAdd(0);
                            break;
                        }
                        case 2: {
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block142;
                            if (kind > 2) {
                                kind = 2;
                            }
                            this.jjstateSet[this.jjnewStateCnt++] = 2;
                            break;
                        }
                        case 5: {
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block142;
                            this.jjCheckNAddTwoStates(5, 6);
                            break;
                        }
                        case 7: 
                        case 8: {
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block142;
                            this.jjCheckNAddTwoStates(8, 6);
                            break;
                        }
                        case 21: 
                        case 22: {
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block142;
                            this.jjCheckNAddStates(19, 23);
                            break;
                        }
                        case 30: 
                        case 31: {
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block142;
                            this.jjCheckNAddStates(27, 31);
                            break;
                        }
                        case 42: {
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_2(hiByte, i1, i2, l1, l2) || kind <= 21) continue block142;
                            kind = 21;
                            break;
                        }
                        case 55: {
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_3(hiByte, i1, i2, l1, l2)) continue block142;
                            if (kind > 8) {
                                kind = 8;
                            }
                            this.jjCheckNAddStates(15, 18);
                            break;
                        }
                        case 56: {
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_3(hiByte, i1, i2, l1, l2)) continue block142;
                            this.jjCheckNAddStates(34, 36);
                            break;
                        }
                        case 60: 
                        case 61: {
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_3(hiByte, i1, i2, l1, l2)) continue block142;
                            if (kind > 7) {
                                kind = 7;
                            }
                            this.jjCheckNAddStates(42, 44);
                            break;
                        }
                        case 63: 
                        case 64: {
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block142;
                            this.jjCheckNAddTwoStates(64, 65);
                            break;
                        }
                        case 67: {
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block142;
                            this.jjAddStates(45, 46);
                            break;
                        }
                        case 70: {
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block142;
                            this.jjAddStates(59, 60);
                            break;
                        }
                        case 72: {
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_3(hiByte, i1, i2, l1, l2)) continue block142;
                            if (kind > 8) {
                                kind = 8;
                            }
                            this.jjCheckNAdd(72);
                            break;
                        }
                        case 74: 
                        case 75: {
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block142;
                            this.jjCheckNAddTwoStates(75, 76);
                            break;
                        }
                        case 77: 
                        case 78: {
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block142;
                            this.jjCheckNAddTwoStates(78, 79);
                            break;
                        }
                        case 81: {
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block142;
                            this.jjAddStates(47, 48);
                            break;
                        }
                        case 83: {
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block142;
                            this.jjAddStates(49, 50);
                            break;
                        }
                        case 86: {
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block142;
                            this.jjAddStates(61, 62);
                            break;
                        }
                        case 88: {
                            if (!SimpleSqlGrammarTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block142;
                            this.jjAddStates(63, 64);
                            break;
                        }
                        default: {
                            if (i1 != 0 && l1 != 0L && i2 != 0 && l2 != 0L) continue block142;
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
            if (i == (startsAt = 91 - this.jjnewStateCnt)) {
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

    private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2) {
        switch (hiByte) {
            case 0: {
                return (jjbitVec0[i2] & l2) != 0L;
            }
            case 22: {
                return (jjbitVec1[i2] & l2) != 0L;
            }
            case 24: {
                return (jjbitVec2[i2] & l2) != 0L;
            }
            case 32: {
                return (jjbitVec3[i2] & l2) != 0L;
            }
            case 48: {
                return (jjbitVec4[i2] & l2) != 0L;
            }
            case 254: {
                return (jjbitVec5[i2] & l2) != 0L;
            }
        }
        return false;
    }

    private static final boolean jjCanMove_1(int hiByte, int i1, int i2, long l1, long l2) {
        switch (hiByte) {
            case 0: {
                return (jjbitVec8[i2] & l2) != 0L;
            }
        }
        return (jjbitVec6[i1] & l1) != 0L;
    }

    private static final boolean jjCanMove_2(int hiByte, int i1, int i2, long l1, long l2) {
        switch (hiByte) {
            case 0: {
                return (jjbitVec10[i2] & l2) != 0L;
            }
            case 255: {
                return (jjbitVec11[i2] & l2) != 0L;
            }
        }
        return (jjbitVec9[i1] & l1) != 0L;
    }

    private static final boolean jjCanMove_3(int hiByte, int i1, int i2, long l1, long l2) {
        switch (hiByte) {
            case 0: {
                return (jjbitVec13[i2] & l2) != 0L;
            }
            case 2: {
                return (jjbitVec14[i2] & l2) != 0L;
            }
            case 3: {
                return (jjbitVec15[i2] & l2) != 0L;
            }
            case 4: {
                return (jjbitVec16[i2] & l2) != 0L;
            }
            case 5: {
                return (jjbitVec17[i2] & l2) != 0L;
            }
            case 6: {
                return (jjbitVec18[i2] & l2) != 0L;
            }
            case 7: {
                return (jjbitVec19[i2] & l2) != 0L;
            }
            case 8: {
                return (jjbitVec20[i2] & l2) != 0L;
            }
            case 9: {
                return (jjbitVec21[i2] & l2) != 0L;
            }
            case 10: {
                return (jjbitVec22[i2] & l2) != 0L;
            }
            case 11: {
                return (jjbitVec23[i2] & l2) != 0L;
            }
            case 12: {
                return (jjbitVec24[i2] & l2) != 0L;
            }
            case 13: {
                return (jjbitVec25[i2] & l2) != 0L;
            }
            case 14: {
                return (jjbitVec26[i2] & l2) != 0L;
            }
            case 15: {
                return (jjbitVec27[i2] & l2) != 0L;
            }
            case 16: {
                return (jjbitVec28[i2] & l2) != 0L;
            }
            case 18: {
                return (jjbitVec29[i2] & l2) != 0L;
            }
            case 19: {
                return (jjbitVec30[i2] & l2) != 0L;
            }
            case 20: {
                return (jjbitVec6[i2] & l2) != 0L;
            }
            case 22: {
                return (jjbitVec31[i2] & l2) != 0L;
            }
            case 23: {
                return (jjbitVec32[i2] & l2) != 0L;
            }
            case 24: {
                return (jjbitVec33[i2] & l2) != 0L;
            }
            case 25: {
                return (jjbitVec34[i2] & l2) != 0L;
            }
            case 26: {
                return (jjbitVec35[i2] & l2) != 0L;
            }
            case 27: {
                return (jjbitVec36[i2] & l2) != 0L;
            }
            case 28: {
                return (jjbitVec37[i2] & l2) != 0L;
            }
            case 29: {
                return (jjbitVec38[i2] & l2) != 0L;
            }
            case 31: {
                return (jjbitVec39[i2] & l2) != 0L;
            }
            case 32: {
                return (jjbitVec40[i2] & l2) != 0L;
            }
            case 33: {
                return (jjbitVec41[i2] & l2) != 0L;
            }
            case 44: {
                return (jjbitVec42[i2] & l2) != 0L;
            }
            case 45: {
                return (jjbitVec43[i2] & l2) != 0L;
            }
            case 46: {
                return (jjbitVec44[i2] & l2) != 0L;
            }
            case 48: {
                return (jjbitVec45[i2] & l2) != 0L;
            }
            case 49: {
                return (jjbitVec46[i2] & l2) != 0L;
            }
            case 77: {
                return (jjbitVec47[i2] & l2) != 0L;
            }
            case 159: {
                return (jjbitVec48[i2] & l2) != 0L;
            }
            case 164: {
                return (jjbitVec49[i2] & l2) != 0L;
            }
            case 166: {
                return (jjbitVec50[i2] & l2) != 0L;
            }
            case 167: {
                return (jjbitVec51[i2] & l2) != 0L;
            }
            case 168: {
                return (jjbitVec52[i2] & l2) != 0L;
            }
            case 169: {
                return (jjbitVec53[i2] & l2) != 0L;
            }
            case 170: {
                return (jjbitVec54[i2] & l2) != 0L;
            }
            case 171: {
                return (jjbitVec55[i2] & l2) != 0L;
            }
            case 215: {
                return (jjbitVec56[i2] & l2) != 0L;
            }
            case 250: {
                return (jjbitVec57[i2] & l2) != 0L;
            }
            case 251: {
                return (jjbitVec58[i2] & l2) != 0L;
            }
            case 253: {
                return (jjbitVec59[i2] & l2) != 0L;
            }
            case 254: {
                return (jjbitVec60[i2] & l2) != 0L;
            }
            case 255: {
                return (jjbitVec61[i2] & l2) != 0L;
            }
        }
        return (jjbitVec12[i1] & l1) != 0L;
    }

    public Token getNextToken() {
        int curPos = 0;
        try {
            this.curChar = this.input_stream.BeginToken();
        }
        catch (Exception e) {
            this.jjmatchedKind = 0;
            this.jjmatchedPos = -1;
            Token matchedToken = this.jjFillToken();
            return matchedToken;
        }
        this.jjmatchedKind = Integer.MAX_VALUE;
        this.jjmatchedPos = 0;
        curPos = this.jjMoveStringLiteralDfa0_0();
        if (this.jjmatchedKind != Integer.MAX_VALUE) {
            if (this.jjmatchedPos + 1 < curPos) {
                this.input_stream.backup(curPos - this.jjmatchedPos - 1);
            }
            Token matchedToken = this.jjFillToken();
            return matchedToken;
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
            if (this.curChar == 10 || this.curChar == 13) {
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

    void SkipLexicalActions(Token matchedToken) {
        switch (this.jjmatchedKind) {
            default: 
        }
    }

    void MoreLexicalActions() {
        this.lengthOfMatch = this.jjmatchedPos + 1;
        this.jjimageLen += this.lengthOfMatch;
        switch (this.jjmatchedKind) {
            default: 
        }
    }

    void TokenLexicalActions(Token matchedToken) {
        switch (this.jjmatchedKind) {
            default: 
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

    public SimpleSqlGrammarTokenManager(SimpleCharStream stream) {
        this.input_stream = stream;
    }

    public SimpleSqlGrammarTokenManager(SimpleCharStream stream, int lexState) {
        this.ReInit(stream);
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
        int i = 91;
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
}

