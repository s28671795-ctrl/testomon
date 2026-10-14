/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.util.Arrays;
import scala.Function1;
import scala.MatchError;
import scala.collection.IterableOnce;
import scala.collection.immutable.BigVector;
import scala.collection.immutable.Vector;
import scala.collection.immutable.VectorSliceBuilder;
import scala.collection.immutable.VectorStatics$;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;

@ScalaSignature(bytes="\u0006\u0005\t]f\u0001\u0002!B\r!C\u0011B\u0017\u0001\u0003\u0002\u0003\u0006IaW6\t\u0013=\u0004!Q1A\u0005\u0002\u0005\u0003\b\u0002\u0003;\u0001\u0005\u0003\u0005\u000b\u0011B9\t\u0013U\u0004!Q1A\u0005\u0002\u00053\b\u0002\u0003>\u0001\u0005\u0003\u0005\u000b\u0011B<\t\u0013m\u0004!Q1A\u0005\u0002\u0005\u0003\b\u0002\u0003?\u0001\u0005\u0003\u0005\u000b\u0011B9\t\u0013u\u0004!Q1A\u0005\u0002\u0005s\b\"CA\u0003\u0001\t\u0005\t\u0015!\u0003\u0000\u0011)\t9\u0001\u0001BC\u0002\u0013\u0005\u0011\t\u001d\u0005\n\u0003\u0013\u0001!\u0011!Q\u0001\nED1\"a\u0003\u0001\u0005\u000b\u0007I\u0011A!\u0002\u000e!Q\u0011Q\u0003\u0001\u0003\u0002\u0003\u0006I!a\u0004\t\u0015\u0005]\u0001A!b\u0001\n\u0003\t\u0005\u000fC\u0005\u0002\u001a\u0001\u0011\t\u0011)A\u0005c\"Y\u00111\u0004\u0001\u0003\u0006\u0004%\t!QA\u000f\u0011)\t)\u0003\u0001B\u0001B\u0003%\u0011q\u0004\u0005\u000b\u0003O\u0001!Q1A\u0005\u0002\u0005\u0003\b\"CA\u0015\u0001\t\u0005\t\u0015!\u0003r\u0011-\tY\u0003\u0001BC\u0002\u0013\u0005\u0011)!\f\t\u0015\u0005U\u0002A!A!\u0002\u0013\ty\u0003C\u0006\u00028\u0001\u0011)\u0019!C\u0001\u0003\u0006u\u0001BCA\u001d\u0001\t\u0005\t\u0015!\u0003\u0002 !Y\u00111\b\u0001\u0003\u0006\u0004%\t!QA\u0007\u0011)\ti\u0004\u0001B\u0001B\u0003%\u0011q\u0002\u0005\u000b\u0003\u007f\u0001!Q1A\u0005\u0002\u0005s\b\"CA!\u0001\t\u0005\t\u0015!\u0003\u0000\u0011)\t\u0019\u0005\u0001BC\u0002\u0013\u0005\u0011I\u001e\u0005\n\u0003\u000b\u0002!\u0011!Q\u0001\n]D1\"a\u0012\u0001\u0005\u0003\u0005\u000b\u0011B.\u0002J!Y\u0011Q\n\u0001\u0003\u0002\u0003\u0006I!]A(\u0011\u001d\t\u0019\u0006\u0001C\u0001\u0003+B\u0001\"a\u001f\u0001A\u0013%\u0011Q\u0010\u0005\n\u0003W\u0003\u0011\u0013!C\u0005\u0003[C\u0011\"a1\u0001#\u0003%I!!2\t\u0013\u0005%\u0007!%A\u0005\n\u0005-\u0007\"CAh\u0001E\u0005I\u0011BAc\u0011%\t\t\u000eAI\u0001\n\u0013\t\u0019\u000eC\u0005\u0002X\u0002\t\n\u0011\"\u0003\u0002F\"I\u0011\u0011\u001c\u0001\u0012\u0002\u0013%\u00111\u001c\u0005\n\u0003?\u0004\u0011\u0013!C\u0005\u0003\u000bD\u0011\"!9\u0001#\u0003%I!a9\t\u0013\u0005\u001d\b!%A\u0005\n\u0005\u0015\u0007\"CAu\u0001E\u0005I\u0011BAv\u0011%\ty\u000fAI\u0001\n\u0013\t\u0019\u000fC\u0005\u0002r\u0002\t\n\u0011\"\u0003\u0002\\\"I\u00111\u001f\u0001\u0012\u0002\u0013%\u00111\u001b\u0005\n\u0003k\u0004\u0011\u0013!C\u0005\u0003\u0017D\u0011\"a>\u0001#\u0003%I!!,\t\u0013\u0005e\b!%A\u0005\n\u0005\u0015\u0007bBA~\u0001\u0011\u0005\u0011Q \u0005\b\u0005\u000b\u0001A\u0011\tB\u0004\u0011\u001d\u0011Y\u0002\u0001C!\u0005;AqA!\u000b\u0001\t\u0003\u0012Y\u0003C\u0004\u00038\u0001!\tE!\u000f\t\u0011\t5\u0003\u0001)C\t\u0005\u001fBqAa\u0017\u0001\t\u0003\u0012i\u0006C\u0004\u0003`\u0001!\tE!\u0018\t\u000f\t\u0005\u0004\u0001\"\u0005Ba\"A!1\r\u0001\u0005\u0012\u0005\u0013)\u0007\u0003\u0005\u0003\u0002\u0002!\t\"\u0011BB\u0011!\u00119\t\u0001Q\u0005R\t%\u0005\u0002\u0003BR\u0001\u0001&\tF!*\u0003\u000fY+7\r^8sm)\u0011!iQ\u0001\nS6lW\u000f^1cY\u0016T!\u0001R#\u0002\u0015\r|G\u000e\\3di&|gNC\u0001G\u0003\u0015\u00198-\u00197b\u0007\u0001)\"!\u0013)\u0014\u0005\u0001Q\u0005cA&M\u001d6\t\u0011)\u0003\u0002N\u0003\nI!)[4WK\u000e$xN\u001d\t\u0003\u001fBc\u0001\u0001\u0002\u0004R\u0001\u0011\u0015\rA\u0015\u0002\u0002\u0003F\u00111k\u0016\t\u0003)Vk\u0011!R\u0005\u0003-\u0016\u0013qAT8uQ&tw\r\u0005\u0002U1&\u0011\u0011,\u0012\u0002\u0004\u0003:L\u0018\u0001C0qe\u00164\u0017\u000e_\u0019\u0011\u0005qCgBA/g\u001d\tqVM\u0004\u0002`I:\u0011\u0001mY\u0007\u0002C*\u0011!mR\u0001\u0007yI|w\u000e\u001e \n\u0003\u0019K!\u0001R#\n\u0005\t\u001b\u0015BA4B\u000311Vm\u0019;pe&sG.\u001b8f\u0013\tI'N\u0001\u0003BeJ\f$BA4B\u0013\taW.A\u0004qe\u00164\u0017\u000e_\u0019\n\u00059\f%A\u0002,fGR|'/\u0001\u0003mK:\fT#A9\u0011\u0005Q\u0013\u0018BA:F\u0005\rIe\u000e^\u0001\u0006Y\u0016t\u0017\u0007I\u0001\baJ,g-\u001b=3+\u00059\bC\u0001/y\u0013\tI(N\u0001\u0003BeJ\u0014\u0014\u0001\u00039sK\u001aL\u0007P\r\u0011\u0002\u000b1,g.\r\u001a\u0002\r1,g.\r\u001a!\u0003\u001d\u0001(/\u001a4jqN*\u0012a \t\u00049\u0006\u0005\u0011bAA\u0002U\n!\u0011I\u001d:4\u0003!\u0001(/\u001a4jqN\u0002\u0013A\u00027f]F\u00124'A\u0004mK:\f$g\r\u0011\u0002\u000fA\u0014XMZ5yiU\u0011\u0011q\u0002\t\u00049\u0006E\u0011bAA\nU\n!\u0011I\u001d:5\u0003!\u0001(/\u001a4jqR\u0002\u0013a\u00027f]F\u00124\u0007N\u0001\tY\u0016t\u0017GM\u001a5A\u00059\u0001O]3gSb,TCAA\u0010!\ra\u0016\u0011E\u0005\u0004\u0003GQ'\u0001B!seV\n\u0001\u0002\u001d:fM&DX\u0007I\u0001\tY\u0016t\u0017GM\u001a5k\u0005IA.\u001a82eM\"T\u0007I\u0001\u0006I\u0006$\u0018MN\u000b\u0003\u0003_\u00012\u0001XA\u0019\u0013\r\t\u0019D\u001b\u0002\u0005\u0003J\u0014h'\u0001\u0004eCR\fg\u0007I\u0001\bgV4g-\u001b=6\u0003!\u0019XO\u001a4jqV\u0002\u0013aB:vM\u001aL\u0007\u0010N\u0001\tgV4g-\u001b=5A\u000591/\u001e4gSb\u001c\u0014\u0001C:vM\u001aL\u0007p\r\u0011\u0002\u000fM,hMZ5ye\u0005A1/\u001e4gSb\u0014\u0004%\u0001\u0005`gV4g-\u001b=2\u0013\r\tY\u0005T\u0001\bgV4g-\u001b=2\u0003!yF.\u001a8hi\"\u0004\u0014bAA)\u0019\u00069A.\u001a8hi\"\u0004\u0014A\u0002\u001fj]&$h\b\u0006\u0013\u0002X\u0005e\u00131LA/\u0003?\n\t'a\u0019\u0002f\u0005\u001d\u0014\u0011NA6\u0003[\ny'!\u001d\u0002t\u0005U\u0014qOA=!\rY\u0005A\u0014\u0005\u00065\u0002\u0002\ra\u0017\u0005\u0006_\u0002\u0002\r!\u001d\u0005\u0006k\u0002\u0002\ra\u001e\u0005\u0006w\u0002\u0002\r!\u001d\u0005\u0006{\u0002\u0002\ra \u0005\u0007\u0003\u000f\u0001\u0003\u0019A9\t\u000f\u0005-\u0001\u00051\u0001\u0002\u0010!1\u0011q\u0003\u0011A\u0002EDq!a\u0007!\u0001\u0004\ty\u0002\u0003\u0004\u0002(\u0001\u0002\r!\u001d\u0005\b\u0003W\u0001\u0003\u0019AA\u0018\u0011\u001d\t9\u0004\ta\u0001\u0003?Aq!a\u000f!\u0001\u0004\ty\u0001\u0003\u0004\u0002@\u0001\u0002\ra \u0005\u0007\u0003\u0007\u0002\u0003\u0019A<\t\r\u0005\u001d\u0003\u00051\u0001\\\u0011\u0019\ti\u0005\ta\u0001c\u0006!1m\u001c9z)\u0011\ny(!!\u0002\u0004\u0006\u0015\u0015qQAE\u0003\u0017\u000bi)a$\u0002\u0012\u0006M\u0015QSAL\u00033\u000bY*!(\u0002 \u0006\u0005\u0006cA&\u0001'\"9A.\tI\u0001\u0002\u0004Y\u0006bB8\"!\u0003\u0005\r!\u001d\u0005\bk\u0006\u0002\n\u00111\u0001x\u0011\u001dY\u0018\u0005%AA\u0002EDq!`\u0011\u0011\u0002\u0003\u0007q\u0010\u0003\u0005\u0002\b\u0005\u0002\n\u00111\u0001r\u0011%\tY!\tI\u0001\u0002\u0004\ty\u0001\u0003\u0005\u0002\u0018\u0005\u0002\n\u00111\u0001r\u0011%\tY\"\tI\u0001\u0002\u0004\ty\u0002\u0003\u0005\u0002(\u0005\u0002\n\u00111\u0001r\u0011%\tY#\tI\u0001\u0002\u0004\ty\u0003C\u0005\u00028\u0005\u0002\n\u00111\u0001\u0002 !I\u00111H\u0011\u0011\u0002\u0003\u0007\u0011q\u0002\u0005\t\u0003\u007f\t\u0003\u0013!a\u0001\u007f\"A\u00111I\u0011\u0011\u0002\u0003\u0007q\u000f\u0003\u0005\u0002L\u0005\u0002\n\u00111\u0001\\\u0011!\t\t&\tI\u0001\u0002\u0004\t\bfA\u0011\u0002&B\u0019A+a*\n\u0007\u0005%VI\u0001\u0004j]2Lg.Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\t\tyKK\u0002\\\u0003c[#!a-\u0011\t\u0005U\u0016qX\u0007\u0003\u0003oSA!!/\u0002<\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003{+\u0015AC1o]>$\u0018\r^5p]&!\u0011\u0011YA\\\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\t\t9MK\u0002r\u0003c\u000babY8qs\u0012\"WMZ1vYR$3'\u0006\u0002\u0002N*\u001aq/!-\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%i\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012*TCAAkU\ry\u0018\u0011W\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00137\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uI]*\"!!8+\t\u0005=\u0011\u0011W\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00139\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIe*\"!!:+\t\u0005}\u0011\u0011W\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132a\u0005y1m\u001c9zI\u0011,g-Y;mi\u0012\n\u0014'\u0006\u0002\u0002n*\"\u0011qFAY\u0003=\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE\u0012\u0014aD2paf$C-\u001a4bk2$H%M\u001a\u0002\u001f\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cQ\nqbY8qs\u0012\"WMZ1vYR$\u0013'N\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132m\u0005y1m\u001c9zI\u0011,g-Y;mi\u0012\nt'A\u0003baBd\u0017\u0010F\u0002O\u0003\u007fDaA!\u00014\u0001\u0004\t\u0018!B5oI\u0016D\bfA\u001a\u0002&\u00069Q\u000f\u001d3bi\u0016$W\u0003\u0002B\u0005\u0005\u001f!bAa\u0003\u0003\u0016\t]\u0001\u0003B&n\u0005\u001b\u00012a\u0014B\b\t\u001d\u0011\t\u0002\u000eb\u0001\u0005'\u0011\u0011AQ\t\u0003\u001d^CaA!\u00015\u0001\u0004\t\bb\u0002B\ri\u0001\u0007!QB\u0001\u0005K2,W.\u0001\u0005baB,g\u000eZ3e+\u0011\u0011yB!\n\u0015\t\t\u0005\"q\u0005\t\u0005\u00176\u0014\u0019\u0003E\u0002P\u0005K!qA!\u00056\u0005\u0004\u0011\u0019\u0002C\u0004\u0003\u001aU\u0002\rAa\t\u0002\u0013A\u0014X\r]3oI\u0016$W\u0003\u0002B\u0017\u0005g!BAa\f\u00036A!1*\u001cB\u0019!\ry%1\u0007\u0003\b\u0005#1$\u0019\u0001B\n\u0011\u001d\u0011IB\u000ea\u0001\u0005c\t1!\\1q+\u0011\u0011YD!\u0011\u0015\t\tu\"1\t\t\u0005\u00176\u0014y\u0004E\u0002P\u0005\u0003\"aA!\u00058\u0005\u0004\u0011\u0006b\u0002B#o\u0001\u0007!qI\u0001\u0002MB1AK!\u0013O\u0005\u007fI1Aa\u0013F\u0005%1UO\\2uS>t\u0017'\u0001\u0004tY&\u001cW\r\r\u000b\u0007\u0005#\u0012\u0019Fa\u0016\u0011\u0007-kg\n\u0003\u0004\u0003Va\u0002\r!]\u0001\u0003Y>DaA!\u00179\u0001\u0004\t\u0018A\u00015j\u0003\u0011!\u0018-\u001b7\u0016\u0005\tE\u0013\u0001B5oSR\f\u0001C^3di>\u00148\u000b\\5dK\u000e{WO\u001c;\u0002\u0017Y,7\r^8s'2L7-\u001a\u000b\u0005\u0005O\u0012i\b\r\u0003\u0003j\tE\u0004#\u0002+\u0003l\t=\u0014b\u0001B7\u000b\n)\u0011I\u001d:bsB\u0019qJ!\u001d\u0005\u0017\tMD(!A\u0001\u0002\u000b\u0005!Q\u000f\u0002\u0005?\u0012\nt'E\u0002T\u0005o\u00022\u0001\u0016B=\u0013\r\u0011Y(\u0012\u0002\u0007\u0003:L(+\u001a4\t\r\t}D\b1\u0001r\u0003\rIG\r_\u0001\u0018m\u0016\u001cGo\u001c:TY&\u001cW\r\u0015:fM&DH*\u001a8hi\"$2!\u001dBC\u0011\u0019\u0011y(\u0010a\u0001c\u0006i\u0001O]3qK:$W\rZ!mYB*BAa#\u0003\u0012R1!Q\u0012BJ\u0005?\u0003BaS7\u0003\u0010B\u0019qJ!%\u0005\u000f\tEaH1\u0001\u0003\u0014!9!Q\u0013 A\u0002\t]\u0015A\u00029sK\u001aL\u0007\u0010\u0005\u0004\u0003\u001a\nm%qR\u0007\u0002\u0007&\u0019!QT\"\u0003\u0019%#XM]1cY\u0016|enY3\t\r\t\u0005f\b1\u0001r\u0003\u0005Y\u0017\u0001D1qa\u0016tG-\u001a3BY2\u0004T\u0003\u0002BT\u0005[#bA!+\u00030\nU\u0006\u0003B&n\u0005W\u00032a\u0014BW\t\u001d\u0011\tb\u0010b\u0001\u0005'AqA!-@\u0001\u0004\u0011\u0019,\u0001\u0004tk\u001a4\u0017\u000e\u001f\t\u0007\u00053\u0013YJa+\t\r\t\u0005v\b1\u0001r\u0001")
public final class Vector6<A>
extends BigVector<A> {
    private final int len1;
    private final Object[][] prefix2;
    private final int len12;
    private final Object[][][] prefix3;
    private final int len123;
    private final Object[][][][] prefix4;
    private final int len1234;
    private final Object[][][][][] prefix5;
    private final int len12345;
    private final Object[][][][][][] data6;
    private final Object[][][][][] suffix5;
    private final Object[][][][] suffix4;
    private final Object[][][] suffix3;
    private final Object[][] suffix2;

    public int len1() {
        return this.len1;
    }

    public Object[][] prefix2() {
        return this.prefix2;
    }

    public int len12() {
        return this.len12;
    }

    public Object[][][] prefix3() {
        return this.prefix3;
    }

    public int len123() {
        return this.len123;
    }

    public Object[][][][] prefix4() {
        return this.prefix4;
    }

    public int len1234() {
        return this.len1234;
    }

    public Object[][][][][] prefix5() {
        return this.prefix5;
    }

    public int len12345() {
        return this.len12345;
    }

    public Object[][][][][][] data6() {
        return this.data6;
    }

    public Object[][][][][] suffix5() {
        return this.suffix5;
    }

    public Object[][][][] suffix4() {
        return this.suffix4;
    }

    public Object[][][] suffix3() {
        return this.suffix3;
    }

    public Object[][] suffix2() {
        return this.suffix2;
    }

    private Vector6<Nothing$> copy(Object[] prefix1, int len1, Object[][] prefix2, int len12, Object[][][] prefix3, int len123, Object[][][][] prefix4, int len1234, Object[][][][][] prefix5, int len12345, Object[][][][][][] data6, Object[][][][][] suffix5, Object[][][][] suffix4, Object[][][] suffix3, Object[][] suffix2, Object[] suffix1, int length0) {
        return new Vector6<Nothing$>(prefix1, len1, prefix2, len12, prefix3, len123, prefix4, len1234, prefix5, len12345, data6, suffix5, suffix4, suffix3, suffix2, suffix1, length0);
    }

    private Object[] copy$default$1() {
        return this.prefix1();
    }

    private int copy$default$2() {
        return this.len1();
    }

    private Object[][] copy$default$3() {
        return this.prefix2();
    }

    private int copy$default$4() {
        return this.len12();
    }

    private Object[][][] copy$default$5() {
        return this.prefix3();
    }

    private int copy$default$6() {
        return this.len123();
    }

    private Object[][][][] copy$default$7() {
        return this.prefix4();
    }

    private int copy$default$8() {
        return this.len1234();
    }

    private Object[][][][][] copy$default$9() {
        return this.prefix5();
    }

    private int copy$default$10() {
        return this.len12345();
    }

    private Object[][][][][][] copy$default$11() {
        return this.data6();
    }

    private Object[][][][][] copy$default$12() {
        return this.suffix5();
    }

    private Object[][][][] copy$default$13() {
        return this.suffix4();
    }

    private Object[][][] copy$default$14() {
        return this.suffix3();
    }

    private Object[][] copy$default$15() {
        return this.suffix2();
    }

    private Object[] copy$default$16() {
        return this.suffix1();
    }

    private int copy$default$17() {
        return this.length0();
    }

    @Override
    public A apply(int index) {
        if (index >= 0 && index < this.length0()) {
            int io = index - this.len12345();
            if (io >= 0) {
                int i6 = io >>> 25;
                int i5 = io >>> 20 & 0x1F;
                int i4 = io >>> 15 & 0x1F;
                int i3 = io >>> 10 & 0x1F;
                int i2 = io >>> 5 & 0x1F;
                int i1 = io & 0x1F;
                if (i6 < this.data6().length) {
                    return (A)this.data6()[i6][i5][i4][i3][i2][i1];
                }
                if (i5 < this.suffix5().length) {
                    return (A)this.suffix5()[i5][i4][i3][i2][i1];
                }
                if (i4 < this.suffix4().length) {
                    return (A)this.suffix4()[i4][i3][i2][i1];
                }
                if (i3 < this.suffix3().length) {
                    return (A)this.suffix3()[i3][i2][i1];
                }
                if (i2 < this.suffix2().length) {
                    return (A)this.suffix2()[i2][i1];
                }
                return (A)this.suffix1()[i1];
            }
            if (index >= this.len1234()) {
                int io2 = index - this.len1234();
                return (A)this.prefix5()[io2 >>> 20][io2 >>> 15 & 0x1F][io2 >>> 10 & 0x1F][io2 >>> 5 & 0x1F][io2 & 0x1F];
            }
            if (index >= this.len123()) {
                int io3 = index - this.len123();
                return (A)this.prefix4()[io3 >>> 15][io3 >>> 10 & 0x1F][io3 >>> 5 & 0x1F][io3 & 0x1F];
            }
            if (index >= this.len12()) {
                int io4 = index - this.len12();
                return (A)this.prefix3()[io4 >>> 10][io4 >>> 5 & 0x1F][io4 & 0x1F];
            }
            if (index >= this.len1()) {
                int io5 = index - this.len1();
                return (A)this.prefix2()[io5 >>> 5][io5 & 0x1F];
            }
            return (A)this.prefix1()[index];
        }
        throw this.ioob(index);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> Vector<B> updated(int index, B elem) {
        if (index >= 0 && index < this.length0()) {
            void copy_length0;
            void copy_suffix1;
            void copy_suffix2;
            void copy_suffix3;
            void copy_suffix4;
            void copy_suffix5;
            void copy_data6;
            void copy_len12345;
            void copy_prefix5;
            void copy_len1234;
            void copy_prefix4;
            void copy_len123;
            void copy_prefix3;
            void copy_len12;
            void copy_prefix2;
            void copy_len1;
            if (index >= this.len12345()) {
                int io = index - this.len12345();
                int i6 = io >>> 25;
                int i5 = io >>> 20 & 0x1F;
                int i4 = io >>> 15 & 0x1F;
                int i3 = io >>> 10 & 0x1F;
                int i2 = io >>> 5 & 0x1F;
                int i1 = io & 0x1F;
                if (i6 < this.data6().length) {
                    Object[][][][][][] copyUpdate_a6 = this.data6();
                    Object[][][][][][] copyUpdate_a6c = (Object[][][][][][])copyUpdate_a6.clone();
                    Object[][][][][] copyUpdate_copyUpdate_a5c = (Object[][][][][])copyUpdate_a6c[i6].clone();
                    Object[][][][] copyUpdate_copyUpdate_copyUpdate_a4c = (Object[][][][])copyUpdate_copyUpdate_a5c[i5].clone();
                    Object[][][] copyUpdate_copyUpdate_copyUpdate_copyUpdate_a3c = (Object[][][])copyUpdate_copyUpdate_copyUpdate_a4c[i4].clone();
                    Object[][] copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_a2c = (Object[][])copyUpdate_copyUpdate_copyUpdate_copyUpdate_a3c[i3].clone();
                    Object[] copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_a2c[i2].clone();
                    copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c[i1] = elem;
                    Object var190_16 = null;
                    copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_a2c[i2] = copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c;
                    Object var189_15 = null;
                    copyUpdate_copyUpdate_copyUpdate_copyUpdate_a3c[i3] = copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_a2c;
                    Object var188_14 = null;
                    copyUpdate_copyUpdate_copyUpdate_a4c[i4] = copyUpdate_copyUpdate_copyUpdate_copyUpdate_a3c;
                    Object var187_13 = null;
                    copyUpdate_copyUpdate_a5c[i5] = copyUpdate_copyUpdate_copyUpdate_a4c;
                    Object var186_12 = null;
                    copyUpdate_a6c[i6] = copyUpdate_copyUpdate_a5c;
                    Object var184_10 = null;
                    Object var185_11 = null;
                    Object[][][][][][] x$1 = copyUpdate_a6c;
                    Object[] x$2 = this.copy$default$1();
                    int x$3 = this.copy$default$2();
                    Object[][] x$4 = this.copy$default$3();
                    int x$5 = this.copy$default$4();
                    Object[][][] x$6 = this.copy$default$5();
                    int x$7 = this.copy$default$6();
                    Object[][][][] x$8 = this.copy$default$7();
                    int x$9 = this.copy$default$8();
                    Object[][][][][] x$10 = this.copy$default$9();
                    int x$11 = this.copy$default$10();
                    Object[][][][][] x$12 = this.copy$default$12();
                    Object[][][][] x$13 = this.copy$default$13();
                    Object[][][] x$14 = this.copy$default$14();
                    Object[][] x$15 = this.copy$default$15();
                    Object[] x$16 = this.copy$default$16();
                    int x$17 = this.copy$default$17();
                    return new Vector6<A>(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$1, x$12, x$13, x$14, x$15, x$16, x$17);
                }
                if (i5 < this.suffix5().length) {
                    Object[][][][][] copyUpdate_a5 = this.suffix5();
                    Object[][][][][] copyUpdate_a5c = (Object[][][][][])copyUpdate_a5.clone();
                    Object[][][][] copyUpdate_copyUpdate_a4c = (Object[][][][])copyUpdate_a5c[i5].clone();
                    Object[][][] copyUpdate_copyUpdate_copyUpdate_a3c = (Object[][][])copyUpdate_copyUpdate_a4c[i4].clone();
                    Object[][] copyUpdate_copyUpdate_copyUpdate_copyUpdate_a2c = (Object[][])copyUpdate_copyUpdate_copyUpdate_a3c[i3].clone();
                    Object[] copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_copyUpdate_copyUpdate_copyUpdate_a2c[i2].clone();
                    copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c[i1] = elem;
                    Object var196_39 = null;
                    copyUpdate_copyUpdate_copyUpdate_copyUpdate_a2c[i2] = copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c;
                    Object var195_38 = null;
                    copyUpdate_copyUpdate_copyUpdate_a3c[i3] = copyUpdate_copyUpdate_copyUpdate_copyUpdate_a2c;
                    Object var194_37 = null;
                    copyUpdate_copyUpdate_a4c[i4] = copyUpdate_copyUpdate_copyUpdate_a3c;
                    Object var193_36 = null;
                    copyUpdate_a5c[i5] = copyUpdate_copyUpdate_a4c;
                    Object var191_34 = null;
                    Object var192_35 = null;
                    Object[][][][][] x$18 = copyUpdate_a5c;
                    Object[] x$19 = this.copy$default$1();
                    int x$20 = this.copy$default$2();
                    Object[][] x$21 = this.copy$default$3();
                    int x$22 = this.copy$default$4();
                    Object[][][] x$23 = this.copy$default$5();
                    int x$24 = this.copy$default$6();
                    Object[][][][] x$25 = this.copy$default$7();
                    int x$26 = this.copy$default$8();
                    Object[][][][][] x$27 = this.copy$default$9();
                    int x$28 = this.copy$default$10();
                    Object[][][][][][] x$29 = this.copy$default$11();
                    Object[][][][] x$30 = this.copy$default$13();
                    Object[][][] x$31 = this.copy$default$14();
                    Object[][] x$32 = this.copy$default$15();
                    Object[] x$33 = this.copy$default$16();
                    int x$34 = this.copy$default$17();
                    return new Vector6<A>(x$19, x$20, x$21, x$22, x$23, x$24, x$25, x$26, x$27, x$28, x$29, x$18, x$30, x$31, x$32, x$33, x$34);
                }
                if (i4 < this.suffix4().length) {
                    Object[][][][] copyUpdate_a4 = this.suffix4();
                    Object[][][][] copyUpdate_a4c = (Object[][][][])copyUpdate_a4.clone();
                    Object[][][] copyUpdate_copyUpdate_a3c = (Object[][][])copyUpdate_a4c[i4].clone();
                    Object[][] copyUpdate_copyUpdate_copyUpdate_a2c = (Object[][])copyUpdate_copyUpdate_a3c[i3].clone();
                    Object[] copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_copyUpdate_copyUpdate_a2c[i2].clone();
                    copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c[i1] = elem;
                    Object var201_61 = null;
                    copyUpdate_copyUpdate_copyUpdate_a2c[i2] = copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c;
                    Object var200_60 = null;
                    copyUpdate_copyUpdate_a3c[i3] = copyUpdate_copyUpdate_copyUpdate_a2c;
                    Object var199_59 = null;
                    copyUpdate_a4c[i4] = copyUpdate_copyUpdate_a3c;
                    Object var197_57 = null;
                    Object var198_58 = null;
                    Object[][][][] x$35 = copyUpdate_a4c;
                    Object[] x$36 = this.copy$default$1();
                    int x$37 = this.copy$default$2();
                    Object[][] x$38 = this.copy$default$3();
                    int x$39 = this.copy$default$4();
                    Object[][][] x$40 = this.copy$default$5();
                    int x$41 = this.copy$default$6();
                    Object[][][][] x$42 = this.copy$default$7();
                    int x$43 = this.copy$default$8();
                    Object[][][][][] x$44 = this.copy$default$9();
                    int x$45 = this.copy$default$10();
                    Object[][][][][][] x$46 = this.copy$default$11();
                    Object[][][][][] x$47 = this.copy$default$12();
                    Object[][][] x$48 = this.copy$default$14();
                    Object[][] x$49 = this.copy$default$15();
                    Object[] x$50 = this.copy$default$16();
                    int x$51 = this.copy$default$17();
                    return new Vector6<A>(x$36, x$37, x$38, x$39, x$40, x$41, x$42, x$43, x$44, x$45, x$46, x$47, x$35, x$48, x$49, x$50, x$51);
                }
                if (i3 < this.suffix3().length) {
                    Object[][][] copyUpdate_a3 = this.suffix3();
                    Object[][][] copyUpdate_a3c = (Object[][][])copyUpdate_a3.clone();
                    Object[][] copyUpdate_copyUpdate_a2c = (Object[][])copyUpdate_a3c[i3].clone();
                    Object[] copyUpdate_copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_copyUpdate_a2c[i2].clone();
                    copyUpdate_copyUpdate_copyUpdate_a1c[i1] = elem;
                    Object var205_82 = null;
                    copyUpdate_copyUpdate_a2c[i2] = copyUpdate_copyUpdate_copyUpdate_a1c;
                    Object var204_81 = null;
                    copyUpdate_a3c[i3] = copyUpdate_copyUpdate_a2c;
                    Object var202_79 = null;
                    Object var203_80 = null;
                    Object[][][] x$52 = copyUpdate_a3c;
                    Object[] x$53 = this.copy$default$1();
                    int x$54 = this.copy$default$2();
                    Object[][] x$55 = this.copy$default$3();
                    int x$56 = this.copy$default$4();
                    Object[][][] x$57 = this.copy$default$5();
                    int x$58 = this.copy$default$6();
                    Object[][][][] x$59 = this.copy$default$7();
                    int x$60 = this.copy$default$8();
                    Object[][][][][] x$61 = this.copy$default$9();
                    int x$62 = this.copy$default$10();
                    Object[][][][][][] x$63 = this.copy$default$11();
                    Object[][][][][] x$64 = this.copy$default$12();
                    Object[][][][] x$65 = this.copy$default$13();
                    Object[][] x$66 = this.copy$default$15();
                    Object[] x$67 = this.copy$default$16();
                    int x$68 = this.copy$default$17();
                    return new Vector6<A>(x$53, x$54, x$55, x$56, x$57, x$58, x$59, x$60, x$61, x$62, x$63, x$64, x$65, x$52, x$66, x$67, x$68);
                }
                if (i2 < this.suffix2().length) {
                    Object[][] copyUpdate_a2 = this.suffix2();
                    Object[][] copyUpdate_a2c = (Object[][])copyUpdate_a2.clone();
                    Object[] copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_a2c[i2].clone();
                    copyUpdate_copyUpdate_a1c[i1] = elem;
                    Object var208_102 = null;
                    copyUpdate_a2c[i2] = copyUpdate_copyUpdate_a1c;
                    Object var206_100 = null;
                    Object var207_101 = null;
                    Object[][] x$69 = copyUpdate_a2c;
                    Object[] x$70 = this.copy$default$1();
                    int x$71 = this.copy$default$2();
                    Object[][] x$72 = this.copy$default$3();
                    int x$73 = this.copy$default$4();
                    Object[][][] x$74 = this.copy$default$5();
                    int x$75 = this.copy$default$6();
                    Object[][][][] x$76 = this.copy$default$7();
                    int x$77 = this.copy$default$8();
                    Object[][][][][] x$78 = this.copy$default$9();
                    int x$79 = this.copy$default$10();
                    Object[][][][][][] x$80 = this.copy$default$11();
                    Object[][][][][] x$81 = this.copy$default$12();
                    Object[][][][] x$82 = this.copy$default$13();
                    Object[][][] x$83 = this.copy$default$14();
                    Object[] x$84 = this.copy$default$16();
                    int x$85 = this.copy$default$17();
                    return new Vector6<A>(x$70, x$71, x$72, x$73, x$74, x$75, x$76, x$77, x$78, x$79, x$80, x$81, x$82, x$83, x$69, x$84, x$85);
                }
                Object[] copyUpdate_a1 = this.suffix1();
                Object[] copyUpdate_a1c = (Object[])copyUpdate_a1.clone();
                copyUpdate_a1c[i1] = elem;
                Object var209_120 = null;
                Object var210_121 = null;
                Object[] x$86 = copyUpdate_a1c;
                Object[] x$87 = this.copy$default$1();
                int x$88 = this.copy$default$2();
                Object[][] x$89 = this.copy$default$3();
                int x$90 = this.copy$default$4();
                Object[][][] x$91 = this.copy$default$5();
                int x$92 = this.copy$default$6();
                Object[][][][] x$93 = this.copy$default$7();
                int x$94 = this.copy$default$8();
                Object[][][][][] x$95 = this.copy$default$9();
                int x$96 = this.copy$default$10();
                Object[][][][][][] x$97 = this.copy$default$11();
                Object[][][][][] x$98 = this.copy$default$12();
                Object[][][][] x$99 = this.copy$default$13();
                Object[][][] x$100 = this.copy$default$14();
                Object[][] x$101 = this.copy$default$15();
                int x$102 = this.copy$default$17();
                return new Vector6<A>(x$87, x$88, x$89, x$90, x$91, x$92, x$93, x$94, x$95, x$96, x$97, x$98, x$99, x$100, x$101, x$86, x$102);
            }
            if (index >= this.len1234()) {
                void copyUpdate_idx2;
                void copyUpdate_idx3;
                void copyUpdate_idx4;
                void copyUpdate_idx5;
                int io = index - this.len1234();
                int n = io & 0x1F;
                int n2 = io >>> 5 & 0x1F;
                int n3 = io >>> 10 & 0x1F;
                int n4 = io >>> 15 & 0x1F;
                int n5 = io >>> 20;
                Object[][][][][] copyUpdate_a5 = this.prefix5();
                Object[][][][][] copyUpdate_a5c = (Object[][][][][])copyUpdate_a5.clone();
                Object[][][][] copyUpdate_copyUpdate_a4c = (Object[][][][])copyUpdate_a5c[copyUpdate_idx5].clone();
                Object[][][] copyUpdate_copyUpdate_copyUpdate_a3c = (Object[][][])copyUpdate_copyUpdate_a4c[copyUpdate_idx4].clone();
                Object[][] copyUpdate_copyUpdate_copyUpdate_copyUpdate_a2c = (Object[][])copyUpdate_copyUpdate_copyUpdate_a3c[copyUpdate_idx3].clone();
                Object[] copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_copyUpdate_copyUpdate_copyUpdate_a2c[copyUpdate_idx2].clone();
                copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c[copyUpdate_idx1] = elem;
                Object var221_150 = null;
                copyUpdate_copyUpdate_copyUpdate_copyUpdate_a2c[copyUpdate_idx2] = copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c;
                Object var220_149 = null;
                copyUpdate_copyUpdate_copyUpdate_a3c[copyUpdate_idx3] = copyUpdate_copyUpdate_copyUpdate_copyUpdate_a2c;
                Object var219_148 = null;
                copyUpdate_copyUpdate_a4c[copyUpdate_idx4] = copyUpdate_copyUpdate_copyUpdate_a3c;
                Object var218_147 = null;
                copyUpdate_a5c[copyUpdate_idx5] = copyUpdate_copyUpdate_a4c;
                Object var211_145 = null;
                Object var217_146 = null;
                Object[][][][][] x$103 = copyUpdate_a5c;
                Object[] x$104 = this.copy$default$1();
                int x$105 = this.copy$default$2();
                Object[][] x$106 = this.copy$default$3();
                int x$107 = this.copy$default$4();
                Object[][][] x$108 = this.copy$default$5();
                int x$109 = this.copy$default$6();
                Object[][][][] x$110 = this.copy$default$7();
                int x$111 = this.copy$default$8();
                int x$112 = this.copy$default$10();
                Object[][][][][][] x$113 = this.copy$default$11();
                Object[][][][][] x$114 = this.copy$default$12();
                Object[][][][] x$115 = this.copy$default$13();
                Object[][][] x$116 = this.copy$default$14();
                Object[][] x$117 = this.copy$default$15();
                Object[] x$118 = this.copy$default$16();
                int x$119 = this.copy$default$17();
                return new Vector6<A>(x$104, x$105, x$106, x$107, x$108, x$109, x$110, x$111, x$103, x$112, x$113, x$114, x$115, x$116, x$117, x$118, x$119);
            }
            if (index >= this.len123()) {
                void copyUpdate_idx2;
                void copyUpdate_idx3;
                void copyUpdate_idx4;
                int io = index - this.len123();
                int n = io & 0x1F;
                int n6 = io >>> 5 & 0x1F;
                int n7 = io >>> 10 & 0x1F;
                int n8 = io >>> 15;
                Object[][][][] copyUpdate_a4 = this.prefix4();
                Object[][][][] copyUpdate_a4c = (Object[][][][])copyUpdate_a4.clone();
                Object[][][] copyUpdate_copyUpdate_a3c = (Object[][][])copyUpdate_a4c[copyUpdate_idx4].clone();
                Object[][] copyUpdate_copyUpdate_copyUpdate_a2c = (Object[][])copyUpdate_copyUpdate_a3c[copyUpdate_idx3].clone();
                Object[] copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_copyUpdate_copyUpdate_a2c[copyUpdate_idx2].clone();
                copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c[copyUpdate_idx1] = elem;
                Object var230_177 = null;
                copyUpdate_copyUpdate_copyUpdate_a2c[copyUpdate_idx2] = copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c;
                Object var229_176 = null;
                copyUpdate_copyUpdate_a3c[copyUpdate_idx3] = copyUpdate_copyUpdate_copyUpdate_a2c;
                Object var228_175 = null;
                copyUpdate_a4c[copyUpdate_idx4] = copyUpdate_copyUpdate_a3c;
                Object var222_173 = null;
                Object var227_174 = null;
                Object[][][][] x$120 = copyUpdate_a4c;
                Object[] x$121 = this.copy$default$1();
                int x$122 = this.copy$default$2();
                Object[][] x$123 = this.copy$default$3();
                int x$124 = this.copy$default$4();
                Object[][][] x$125 = this.copy$default$5();
                int x$126 = this.copy$default$6();
                int x$127 = this.copy$default$8();
                Object[][][][][] x$128 = this.copy$default$9();
                int x$129 = this.copy$default$10();
                Object[][][][][][] x$130 = this.copy$default$11();
                Object[][][][][] x$131 = this.copy$default$12();
                Object[][][][] x$132 = this.copy$default$13();
                Object[][][] x$133 = this.copy$default$14();
                Object[][] x$134 = this.copy$default$15();
                Object[] x$135 = this.copy$default$16();
                int x$136 = this.copy$default$17();
                return new Vector6<A>(x$121, x$122, x$123, x$124, x$125, x$126, x$120, x$127, x$128, x$129, x$130, x$131, x$132, x$133, x$134, x$135, x$136);
            }
            if (index >= this.len12()) {
                void copyUpdate_idx2;
                void copyUpdate_idx3;
                int io = index - this.len12();
                int n = io & 0x1F;
                int n9 = io >>> 5 & 0x1F;
                int n10 = io >>> 10;
                Object[][][] copyUpdate_a3 = this.prefix3();
                Object[][][] copyUpdate_a3c = (Object[][][])copyUpdate_a3.clone();
                Object[][] copyUpdate_copyUpdate_a2c = (Object[][])copyUpdate_a3c[copyUpdate_idx3].clone();
                Object[] copyUpdate_copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_copyUpdate_a2c[copyUpdate_idx2].clone();
                copyUpdate_copyUpdate_copyUpdate_a1c[copyUpdate_idx1] = elem;
                Object var237_202 = null;
                copyUpdate_copyUpdate_a2c[copyUpdate_idx2] = copyUpdate_copyUpdate_copyUpdate_a1c;
                Object var236_201 = null;
                copyUpdate_a3c[copyUpdate_idx3] = copyUpdate_copyUpdate_a2c;
                Object var231_199 = null;
                Object var235_200 = null;
                Object[][][] x$137 = copyUpdate_a3c;
                Object[] x$138 = this.copy$default$1();
                int x$139 = this.copy$default$2();
                Object[][] x$140 = this.copy$default$3();
                int x$141 = this.copy$default$4();
                int x$142 = this.copy$default$6();
                Object[][][][] x$143 = this.copy$default$7();
                int x$144 = this.copy$default$8();
                Object[][][][][] x$145 = this.copy$default$9();
                int x$146 = this.copy$default$10();
                Object[][][][][][] x$147 = this.copy$default$11();
                Object[][][][][] x$148 = this.copy$default$12();
                Object[][][][] x$149 = this.copy$default$13();
                Object[][][] x$150 = this.copy$default$14();
                Object[][] x$151 = this.copy$default$15();
                Object[] x$152 = this.copy$default$16();
                int x$153 = this.copy$default$17();
                return new Vector6<A>(x$138, x$139, x$140, x$141, x$137, x$142, x$143, x$144, x$145, x$146, x$147, x$148, x$149, x$150, x$151, x$152, x$153);
            }
            if (index >= this.len1()) {
                void copyUpdate_idx2;
                int io = index - this.len1();
                int n = io & 0x1F;
                int n11 = io >>> 5;
                Object[][] copyUpdate_a2 = this.prefix2();
                Object[][] copyUpdate_a2c = (Object[][])copyUpdate_a2.clone();
                Object[] copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_a2c[copyUpdate_idx2].clone();
                copyUpdate_copyUpdate_a1c[copyUpdate_idx1] = elem;
                Object var242_225 = null;
                copyUpdate_a2c[copyUpdate_idx2] = copyUpdate_copyUpdate_a1c;
                Object var238_223 = null;
                Object var241_224 = null;
                Object[][] x$154 = copyUpdate_a2c;
                Object[] x$155 = this.copy$default$1();
                int x$156 = this.copy$default$2();
                int x$157 = this.copy$default$4();
                Object[][][] x$158 = this.copy$default$5();
                int x$159 = this.copy$default$6();
                Object[][][][] x$160 = this.copy$default$7();
                int x$161 = this.copy$default$8();
                Object[][][][][] x$162 = this.copy$default$9();
                int x$163 = this.copy$default$10();
                Object[][][][][][] x$164 = this.copy$default$11();
                Object[][][][][] x$165 = this.copy$default$12();
                Object[][][][] x$166 = this.copy$default$13();
                Object[][][] x$167 = this.copy$default$14();
                Object[][] x$168 = this.copy$default$15();
                Object[] x$169 = this.copy$default$16();
                int x$170 = this.copy$default$17();
                return new Vector6<A>(x$155, x$156, x$154, x$157, x$158, x$159, x$160, x$161, x$162, x$163, x$164, x$165, x$166, x$167, x$168, x$169, x$170);
            }
            Object[] copyUpdate_a1c = (Object[])this.prefix1().clone();
            copyUpdate_a1c[index] = elem;
            Object var243_243 = null;
            int n = this.copy$default$17();
            Object[] objectArray = this.copy$default$16();
            Object[][] objectArray2 = this.copy$default$15();
            Object[][][] objectArray3 = this.copy$default$14();
            Object[][][][] objectArray4 = this.copy$default$13();
            Object[][][][][] objectArray5 = this.copy$default$12();
            Object[][][][][][] objectArray6 = this.copy$default$11();
            int n12 = this.copy$default$10();
            Object[][][][][] objectArray7 = this.copy$default$9();
            int n13 = this.copy$default$8();
            Object[][][][] objectArray8 = this.copy$default$7();
            int n14 = this.copy$default$6();
            Object[][][] objectArray9 = this.copy$default$5();
            int n15 = this.copy$default$4();
            Object[][] objectArray10 = this.copy$default$3();
            int n16 = this.copy$default$2();
            Object[] copy_prefix1 = copyUpdate_a1c;
            return new Vector6<A>(copy_prefix1, (int)copy_len1, (Object[][])copy_prefix2, (int)copy_len12, (Object[][][])copy_prefix3, (int)copy_len123, (Object[][][][])copy_prefix4, (int)copy_len1234, (Object[][][][][])copy_prefix5, (int)copy_len12345, (Object[][][][][][])copy_data6, (Object[][][][][])copy_suffix5, (Object[][][][])copy_suffix4, (Object[][][])copy_suffix3, (Object[][])copy_suffix2, (Object[])copy_suffix1, (int)copy_length0);
        }
        throw this.ioob(index);
    }

    @Override
    public <B> Vector<B> appended(B elem) {
        if (this.suffix1().length < 32) {
            Object[] x$1 = VectorStatics$.MODULE$.copyAppend1(this.suffix1(), elem);
            int x$2 = this.length0() + 1;
            Object[] x$3 = this.prefix1();
            int x$4 = this.len1();
            Object[][] x$5 = this.prefix2();
            int x$6 = this.len12();
            Object[][][] x$7 = this.prefix3();
            int x$8 = this.len123();
            Object[][][][] x$9 = this.prefix4();
            int x$10 = this.len1234();
            Object[][][][][] x$11 = this.prefix5();
            int x$12 = this.len12345();
            Object[][][][][][] x$13 = this.data6();
            Object[][][][][] x$14 = this.suffix5();
            Object[][][][] x$15 = this.copy$default$13();
            Object[][][] x$16 = this.copy$default$14();
            Object[][] x$17 = this.copy$default$15();
            return new Vector6<A>(x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$14, x$15, x$16, x$17, x$1, x$2);
        }
        if (this.suffix2().length < 31) {
            Object[][] x$18 = VectorStatics$.MODULE$.copyAppend((T[])this.suffix2(), this.suffix1());
            Object[] wrap1_a = new Object[]{elem};
            Object var104_20 = null;
            Object[] x$19 = wrap1_a;
            int x$20 = this.length0() + 1;
            Object[] x$21 = this.copy$default$1();
            int x$22 = this.copy$default$2();
            Object[][] x$23 = this.copy$default$3();
            int x$24 = this.copy$default$4();
            Object[][][] x$25 = this.copy$default$5();
            int x$26 = this.copy$default$6();
            Object[][][][] x$27 = this.copy$default$7();
            int x$28 = this.copy$default$8();
            Object[][][][][] x$29 = this.copy$default$9();
            int x$30 = this.copy$default$10();
            Object[][][][][][] x$31 = this.copy$default$11();
            Object[][][][][] x$32 = this.copy$default$12();
            Object[][][][] x$33 = this.copy$default$13();
            Object[][][] x$34 = this.copy$default$14();
            return new Vector6<A>(x$21, x$22, x$23, x$24, x$25, x$26, x$27, x$28, x$29, x$30, x$31, x$32, x$33, x$34, x$18, x$19, x$20);
        }
        if (this.suffix3().length < 31) {
            Object[][][] x$35 = VectorStatics$.MODULE$.copyAppend((T[])this.suffix3(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix2(), this.suffix1()));
            Object[][] x$36 = VectorStatics$.MODULE$.empty2();
            Object[] wrap1_a = new Object[]{elem};
            Object var105_39 = null;
            Object[] x$37 = wrap1_a;
            int x$38 = this.length0() + 1;
            Object[] x$39 = this.copy$default$1();
            int x$40 = this.copy$default$2();
            Object[][] x$41 = this.copy$default$3();
            int x$42 = this.copy$default$4();
            Object[][][] x$43 = this.copy$default$5();
            int x$44 = this.copy$default$6();
            Object[][][][] x$45 = this.copy$default$7();
            int x$46 = this.copy$default$8();
            Object[][][][][] x$47 = this.copy$default$9();
            int x$48 = this.copy$default$10();
            Object[][][][][][] x$49 = this.copy$default$11();
            Object[][][][][] x$50 = this.copy$default$12();
            Object[][][][] x$51 = this.copy$default$13();
            return new Vector6<A>(x$39, x$40, x$41, x$42, x$43, x$44, x$45, x$46, x$47, x$48, x$49, x$50, x$51, x$35, x$36, x$37, x$38);
        }
        if (this.suffix4().length < 31) {
            Object[][][][] x$52 = VectorStatics$.MODULE$.copyAppend((T[])this.suffix4(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix3(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix2(), this.suffix1())));
            Object[][][] x$53 = VectorStatics$.MODULE$.empty3();
            Object[][] x$54 = VectorStatics$.MODULE$.empty2();
            Object[] wrap1_a = new Object[]{elem};
            Object var106_58 = null;
            Object[] x$55 = wrap1_a;
            int x$56 = this.length0() + 1;
            Object[] x$57 = this.copy$default$1();
            int x$58 = this.copy$default$2();
            Object[][] x$59 = this.copy$default$3();
            int x$60 = this.copy$default$4();
            Object[][][] x$61 = this.copy$default$5();
            int x$62 = this.copy$default$6();
            Object[][][][] x$63 = this.copy$default$7();
            int x$64 = this.copy$default$8();
            Object[][][][][] x$65 = this.copy$default$9();
            int x$66 = this.copy$default$10();
            Object[][][][][][] x$67 = this.copy$default$11();
            Object[][][][][] x$68 = this.copy$default$12();
            return new Vector6<A>(x$57, x$58, x$59, x$60, x$61, x$62, x$63, x$64, x$65, x$66, x$67, x$68, x$52, x$53, x$54, x$55, x$56);
        }
        if (this.suffix5().length < 31) {
            Object[][][][][] x$69 = VectorStatics$.MODULE$.copyAppend((T[])this.suffix5(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix4(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix3(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix2(), this.suffix1()))));
            Object[][][][] x$70 = VectorStatics$.MODULE$.empty4();
            Object[][][] x$71 = VectorStatics$.MODULE$.empty3();
            Object[][] x$72 = VectorStatics$.MODULE$.empty2();
            Object[] wrap1_a = new Object[]{elem};
            Object var107_77 = null;
            Object[] x$73 = wrap1_a;
            int x$74 = this.length0() + 1;
            Object[] x$75 = this.copy$default$1();
            int x$76 = this.copy$default$2();
            Object[][] x$77 = this.copy$default$3();
            int x$78 = this.copy$default$4();
            Object[][][] x$79 = this.copy$default$5();
            int x$80 = this.copy$default$6();
            Object[][][][] x$81 = this.copy$default$7();
            int x$82 = this.copy$default$8();
            Object[][][][][] x$83 = this.copy$default$9();
            int x$84 = this.copy$default$10();
            Object[][][][][][] x$85 = this.copy$default$11();
            return new Vector6<A>(x$75, x$76, x$77, x$78, x$79, x$80, x$81, x$82, x$83, x$84, x$85, x$69, x$70, x$71, x$72, x$73, x$74);
        }
        if (this.data6().length < 62) {
            Object[][][][][][] x$86 = VectorStatics$.MODULE$.copyAppend((T[])this.data6(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix5(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix4(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix3(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix2(), this.suffix1())))));
            Object[][][][][] x$87 = VectorStatics$.MODULE$.empty5();
            Object[][][][] x$88 = VectorStatics$.MODULE$.empty4();
            Object[][][] x$89 = VectorStatics$.MODULE$.empty3();
            Object[][] x$90 = VectorStatics$.MODULE$.empty2();
            Object[] wrap1_a = new Object[]{elem};
            Object var108_96 = null;
            Object[] x$91 = wrap1_a;
            int x$92 = this.length0() + 1;
            Object[] x$93 = this.copy$default$1();
            int x$94 = this.copy$default$2();
            Object[][] x$95 = this.copy$default$3();
            int x$96 = this.copy$default$4();
            Object[][][] x$97 = this.copy$default$5();
            int x$98 = this.copy$default$6();
            Object[][][][] x$99 = this.copy$default$7();
            int x$100 = this.copy$default$8();
            Object[][][][][] x$101 = this.copy$default$9();
            int x$102 = this.copy$default$10();
            return new Vector6<A>(x$93, x$94, x$95, x$96, x$97, x$98, x$99, x$100, x$101, x$102, x$86, x$87, x$88, x$89, x$90, x$91, x$92);
        }
        throw new IllegalArgumentException();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> Vector<B> prepended(B elem) {
        if (this.len1() < 32) {
            Object[] x$1 = VectorStatics$.MODULE$.copyPrepend1(elem, this.prefix1());
            int x$2 = this.len1() + 1;
            int x$3 = this.len12() + 1;
            int x$4 = this.len123() + 1;
            int x$5 = this.len1234() + 1;
            int x$6 = this.len12345() + 1;
            int x$7 = this.length0() + 1;
            Object[][] x$8 = this.prefix2();
            Object[][][] x$9 = this.prefix3();
            Object[][][][] x$10 = this.prefix4();
            Object[][][][][] x$11 = this.prefix5();
            Object[][][][][][] x$12 = this.data6();
            Object[][][][][] x$13 = this.suffix5();
            Object[][][][] x$14 = this.suffix4();
            Object[][][] x$15 = this.suffix3();
            Object[][] x$16 = this.suffix2();
            Object[] x$17 = this.suffix1();
            return new Vector6<A>(x$1, x$2, x$8, x$3, x$9, x$4, x$10, x$5, x$11, x$6, x$12, x$13, x$14, x$15, x$16, x$17, x$7);
        }
        if (this.len12() < 1024) {
            Object[] wrap1_a = new Object[]{elem};
            Object var89_19 = null;
            Object[] x$18 = wrap1_a;
            Object[][] x$20 = VectorStatics$.MODULE$.copyPrepend(this.prefix1(), (T[])this.prefix2());
            int x$21 = this.len12() + 1;
            int x$22 = this.len123() + 1;
            int x$23 = this.len1234() + 1;
            int x$24 = this.len12345() + 1;
            int x$25 = this.length0() + 1;
            Object[][][] x$26 = this.prefix3();
            Object[][][][] x$27 = this.copy$default$7();
            Object[][][][][] x$28 = this.copy$default$9();
            Object[][][][][][] x$29 = this.copy$default$11();
            Object[][][][][] x$30 = this.copy$default$12();
            Object[][][][] x$31 = this.copy$default$13();
            Object[][][] x$32 = this.copy$default$14();
            Object[][] x$33 = this.copy$default$15();
            Object[] x$34 = this.copy$default$16();
            int copy_len1 = 1;
            return new Vector6<A>(x$18, copy_len1, x$20, x$21, x$26, x$22, x$27, x$23, x$28, x$24, x$29, x$30, x$31, x$32, x$33, x$34, x$25);
        }
        if (this.len123() < 32768) {
            void copy_len12;
            Object[] wrap1_a = new Object[]{elem};
            Object var91_37 = null;
            Object[] x$35 = wrap1_a;
            Object[][] x$37 = VectorStatics$.MODULE$.empty2();
            Object[][][] x$39 = VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(this.prefix1(), (T[])this.prefix2()), (T[])this.prefix3());
            int x$40 = this.len123() + 1;
            int x$41 = this.len1234() + 1;
            int x$42 = this.len12345() + 1;
            int x$43 = this.length0() + 1;
            Object[][][][] x$44 = this.copy$default$7();
            Object[][][][][] x$45 = this.copy$default$9();
            Object[][][][][][] x$46 = this.copy$default$11();
            Object[][][][][] x$47 = this.copy$default$12();
            Object[][][][] x$48 = this.copy$default$13();
            Object[][][] x$49 = this.copy$default$14();
            Object[][] x$50 = this.copy$default$15();
            Object[] x$51 = this.copy$default$16();
            boolean bl = true;
            int copy_len1 = 1;
            return new Vector6<A>(x$35, copy_len1, x$37, (int)copy_len12, x$39, x$40, x$44, x$41, x$45, x$42, x$46, x$47, x$48, x$49, x$50, x$51, x$43);
        }
        if (this.len1234() < 0x100000) {
            void copy_len123;
            void copy_len12;
            Object[] wrap1_a = new Object[]{elem};
            Object var94_55 = null;
            Object[] x$52 = wrap1_a;
            Object[][] x$54 = VectorStatics$.MODULE$.empty2();
            Object[][][] x$56 = VectorStatics$.MODULE$.empty3();
            Object[][][][] x$58 = VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(this.prefix1(), (T[])this.prefix2()), (T[])this.prefix3()), (T[])this.prefix4());
            int x$59 = this.len1234() + 1;
            int x$60 = this.len12345() + 1;
            int x$61 = this.length0() + 1;
            Object[][][][][] x$62 = this.copy$default$9();
            Object[][][][][][] x$63 = this.copy$default$11();
            Object[][][][][] x$64 = this.copy$default$12();
            Object[][][][] x$65 = this.copy$default$13();
            Object[][][] x$66 = this.copy$default$14();
            Object[][] x$67 = this.copy$default$15();
            Object[] x$68 = this.copy$default$16();
            boolean bl = true;
            boolean bl2 = true;
            int copy_len1 = 1;
            return new Vector6<A>(x$52, copy_len1, x$54, (int)copy_len12, x$56, (int)copy_len123, x$58, x$59, x$62, x$60, x$63, x$64, x$65, x$66, x$67, x$68, x$61);
        }
        if (this.len12345() < 0x2000000) {
            void copy_len1234;
            void copy_len123;
            void copy_len12;
            Object[] wrap1_a = new Object[]{elem};
            Object var98_73 = null;
            Object[] x$69 = wrap1_a;
            Object[][] x$71 = VectorStatics$.MODULE$.empty2();
            Object[][][] x$73 = VectorStatics$.MODULE$.empty3();
            Object[][][][] x$75 = VectorStatics$.MODULE$.empty4();
            Object[][][][][] x$77 = VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(this.prefix1(), (T[])this.prefix2()), (T[])this.prefix3()), (T[])this.prefix4()), (T[])this.prefix5());
            int x$78 = this.len12345() + 1;
            int x$79 = this.length0() + 1;
            Object[][][][][][] x$80 = this.copy$default$11();
            Object[][][][][] x$81 = this.copy$default$12();
            Object[][][][] x$82 = this.copy$default$13();
            Object[][][] x$83 = this.copy$default$14();
            Object[][] x$84 = this.copy$default$15();
            Object[] x$85 = this.copy$default$16();
            boolean bl = true;
            boolean bl3 = true;
            boolean bl4 = true;
            int copy_len1 = 1;
            return new Vector6<A>(x$69, copy_len1, x$71, (int)copy_len12, x$73, (int)copy_len123, x$75, (int)copy_len1234, x$77, x$78, x$80, x$81, x$82, x$83, x$84, x$85, x$79);
        }
        if (this.data6().length < 62) {
            void copy_len12345;
            void copy_len1234;
            void copy_len123;
            void copy_len12;
            Object[] wrap1_a = new Object[]{elem};
            Object var103_91 = null;
            Object[] x$86 = wrap1_a;
            Object[][] x$88 = VectorStatics$.MODULE$.empty2();
            Object[][][] x$90 = VectorStatics$.MODULE$.empty3();
            Object[][][][] x$92 = VectorStatics$.MODULE$.empty4();
            Object[][][][][] x$94 = VectorStatics$.MODULE$.empty5();
            Object[][][][][][] x$96 = VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(this.prefix1(), (T[])this.prefix2()), (T[])this.prefix3()), (T[])this.prefix4()), (T[])this.prefix5()), (T[])this.data6());
            int x$97 = this.length0() + 1;
            Object[][][][][] x$98 = this.copy$default$12();
            Object[][][][] x$99 = this.copy$default$13();
            Object[][][] x$100 = this.copy$default$14();
            Object[][] x$101 = this.copy$default$15();
            Object[] x$102 = this.copy$default$16();
            boolean bl = true;
            boolean bl5 = true;
            boolean bl6 = true;
            boolean bl7 = true;
            int copy_len1 = 1;
            return new Vector6<A>(x$86, copy_len1, x$88, (int)copy_len12, x$90, (int)copy_len123, x$92, (int)copy_len1234, x$94, (int)copy_len12345, x$96, x$98, x$99, x$100, x$101, x$102, x$97);
        }
        throw new IllegalArgumentException();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> Vector<B> map(Function1<A, B> f) {
        Object var117_110;
        Object[] objectArray;
        Object[][] x$10;
        Object[][][] x$9;
        Object[][][][] x$8;
        Object[][][][][] x$7;
        Object[][][][][][] x$6;
        Object[][][][][] x$5;
        Object[][][][] x$4;
        Object[][][] x$3;
        Object[][] x$2;
        Object[] x$1;
        block71: {
            Object var110_98;
            Object var109_97;
            Object var111_99;
            Object[] objectArray2;
            Object[][][] objectArray3;
            block70: {
                void mapElems_a;
                void mapElems_n;
                Object var99_86;
                Object var98_85;
                Object var100_87;
                Object[] objectArray4;
                Object[][][][] objectArray5;
                block68: {
                    void mapElems_a2;
                    void mapElems_n2;
                    Object var88_74;
                    Object var87_73;
                    Object var89_75;
                    Object[] objectArray6;
                    Object[][][][][] objectArray7;
                    block66: {
                        void mapElems_a3;
                        void mapElems_n3;
                        Object var77_62;
                        Object var76_61;
                        Object var78_63;
                        Object[] objectArray8;
                        Object[][][][][][] objectArray9;
                        block64: {
                            void mapElems_a4;
                            void mapElems_n4;
                            Object var66_50;
                            Object var65_49;
                            Object var67_51;
                            Object[] objectArray10;
                            Object[][][][][] objectArray11;
                            block62: {
                                void mapElems_a5;
                                void mapElems_n5;
                                Object var55_38;
                                Object var54_37;
                                Object var56_39;
                                Object[] objectArray12;
                                Object[][][][] objectArray13;
                                block60: {
                                    void mapElems_a6;
                                    void mapElems_n6;
                                    Object var44_26;
                                    Object var43_25;
                                    Object var45_27;
                                    Object[] objectArray14;
                                    Object[][][] objectArray15;
                                    block58: {
                                        void mapElems_a7;
                                        void mapElems_n7;
                                        Object var33_14;
                                        Object var32_13;
                                        Object var34_15;
                                        Object[] objectArray16;
                                        Object[][] objectArray17;
                                        block56: {
                                            void mapElems_a8;
                                            void mapElems_n8;
                                            Object var23_6;
                                            Object[] objectArray18;
                                            block54: {
                                                Object[] mapElems1_a = this.prefix1();
                                                for (int mapElems1_i = 0; mapElems1_i < mapElems1_a.length; ++mapElems1_i) {
                                                    Object mapElems1_v1 = mapElems1_a[mapElems1_i];
                                                    B mapElems1_v2 = f.apply(mapElems1_v1);
                                                    if (mapElems1_v1 == mapElems1_v2) continue;
                                                    Object[] mapElems1_mapElems1Rest_ac = new Object[mapElems1_a.length];
                                                    if (mapElems1_i > 0) {
                                                        System.arraycopy(mapElems1_a, 0, mapElems1_mapElems1Rest_ac, 0, mapElems1_i);
                                                    }
                                                    mapElems1_mapElems1Rest_ac[mapElems1_i] = mapElems1_v2;
                                                    for (int mapElems1_mapElems1Rest_i = mapElems1_i + 1; mapElems1_mapElems1Rest_i < mapElems1_a.length; ++mapElems1_mapElems1Rest_i) {
                                                        mapElems1_mapElems1Rest_ac[mapElems1_mapElems1Rest_i] = f.apply(mapElems1_a[mapElems1_mapElems1Rest_i]);
                                                    }
                                                    objectArray18 = mapElems1_mapElems1Rest_ac;
                                                    var23_6 = null;
                                                    break block54;
                                                }
                                                objectArray18 = mapElems1_a;
                                            }
                                            Object var19_2 = null;
                                            Object var21_4 = null;
                                            Object var22_5 = null;
                                            var23_6 = null;
                                            x$1 = objectArray18;
                                            objectArray17 = this.prefix2();
                                            int n = 2;
                                            VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                                            if (mapElems_n8 == true) {
                                                block55: {
                                                    for (int mapElems_mapElems1_i = 0; mapElems_mapElems1_i < ((void)mapElems_a8).length; ++mapElems_mapElems1_i) {
                                                        void mapElems_mapElems1_v1 = mapElems_a8[mapElems_mapElems1_i];
                                                        B mapElems_mapElems1_v2 = f.apply(mapElems_mapElems1_v1);
                                                        if (mapElems_mapElems1_v1 == mapElems_mapElems1_v2) continue;
                                                        Object[] mapElems_mapElems1_mapElems1Rest_ac = new Object[((void)mapElems_a8).length];
                                                        if (mapElems_mapElems1_i > 0) {
                                                            System.arraycopy(mapElems_a8, 0, mapElems_mapElems1_mapElems1Rest_ac, 0, mapElems_mapElems1_i);
                                                        }
                                                        mapElems_mapElems1_mapElems1Rest_ac[mapElems_mapElems1_i] = mapElems_mapElems1_v2;
                                                        for (int mapElems_mapElems1_mapElems1Rest_i = mapElems_mapElems1_i + 1; mapElems_mapElems1_mapElems1Rest_i < ((void)mapElems_a8).length; ++mapElems_mapElems1_mapElems1Rest_i) {
                                                            mapElems_mapElems1_mapElems1Rest_ac[mapElems_mapElems1_mapElems1Rest_i] = f.apply(mapElems_a8[mapElems_mapElems1_mapElems1Rest_i]);
                                                        }
                                                        objectArray16 = mapElems_mapElems1_mapElems1Rest_ac;
                                                        var34_15 = null;
                                                        break block55;
                                                    }
                                                    objectArray16 = mapElems_a8;
                                                }
                                                var32_13 = null;
                                                var33_14 = null;
                                                var34_15 = null;
                                            } else {
                                                for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a8).length; ++mapElems_i) {
                                                    void mapElems_v1 = mapElems_a8[mapElems_i];
                                                    T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n8 - true), (T[])mapElems_v1, f);
                                                    if (mapElems_v1 == mapElems_v2) continue;
                                                    objectArray16 = mapElems_this.mapElemsRest((int)mapElems_n8, (T[])mapElems_a8, f, mapElems_i, mapElems_v2);
                                                    break block56;
                                                }
                                                objectArray16 = mapElems_a8;
                                            }
                                        }
                                        Object var25_11 = null;
                                        objectArray17 = null;
                                        Object var29_18 = null;
                                        Object var30_19 = null;
                                        var32_13 = null;
                                        var33_14 = null;
                                        var34_15 = null;
                                        x$2 = (Object[][])objectArray16;
                                        objectArray15 = this.prefix3();
                                        int n = 3;
                                        VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                                        if (mapElems_n7 == true) {
                                            block57: {
                                                for (int mapElems_mapElems1_i = 0; mapElems_mapElems1_i < ((void)mapElems_a7).length; ++mapElems_mapElems1_i) {
                                                    void mapElems_mapElems1_v1 = mapElems_a7[mapElems_mapElems1_i];
                                                    B mapElems_mapElems1_v2 = f.apply(mapElems_mapElems1_v1);
                                                    if (mapElems_mapElems1_v1 == mapElems_mapElems1_v2) continue;
                                                    Object[] mapElems_mapElems1_mapElems1Rest_ac = new Object[((void)mapElems_a7).length];
                                                    if (mapElems_mapElems1_i > 0) {
                                                        System.arraycopy(mapElems_a7, 0, mapElems_mapElems1_mapElems1Rest_ac, 0, mapElems_mapElems1_i);
                                                    }
                                                    mapElems_mapElems1_mapElems1Rest_ac[mapElems_mapElems1_i] = mapElems_mapElems1_v2;
                                                    for (int mapElems_mapElems1_mapElems1Rest_i = mapElems_mapElems1_i + 1; mapElems_mapElems1_mapElems1Rest_i < ((void)mapElems_a7).length; ++mapElems_mapElems1_mapElems1Rest_i) {
                                                        mapElems_mapElems1_mapElems1Rest_ac[mapElems_mapElems1_mapElems1Rest_i] = f.apply(mapElems_a7[mapElems_mapElems1_mapElems1Rest_i]);
                                                    }
                                                    objectArray14 = mapElems_mapElems1_mapElems1Rest_ac;
                                                    var45_27 = null;
                                                    break block57;
                                                }
                                                objectArray14 = mapElems_a7;
                                            }
                                            var43_25 = null;
                                            var44_26 = null;
                                            var45_27 = null;
                                        } else {
                                            for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a7).length; ++mapElems_i) {
                                                void mapElems_v1 = mapElems_a7[mapElems_i];
                                                T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n7 - true), (T[])mapElems_v1, f);
                                                if (mapElems_v1 == mapElems_v2) continue;
                                                objectArray14 = mapElems_this.mapElemsRest((int)mapElems_n7, (T[])mapElems_a7, f, mapElems_i, mapElems_v2);
                                                break block58;
                                            }
                                            objectArray14 = mapElems_a7;
                                        }
                                    }
                                    Object var36_23 = null;
                                    objectArray15 = null;
                                    Object var40_30 = null;
                                    Object var41_31 = null;
                                    var43_25 = null;
                                    var44_26 = null;
                                    var45_27 = null;
                                    x$3 = (Object[][][])objectArray14;
                                    objectArray13 = this.prefix4();
                                    int n = 4;
                                    VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                                    if (mapElems_n6 == true) {
                                        block59: {
                                            for (int mapElems_mapElems1_i = 0; mapElems_mapElems1_i < ((void)mapElems_a6).length; ++mapElems_mapElems1_i) {
                                                void mapElems_mapElems1_v1 = mapElems_a6[mapElems_mapElems1_i];
                                                B mapElems_mapElems1_v2 = f.apply(mapElems_mapElems1_v1);
                                                if (mapElems_mapElems1_v1 == mapElems_mapElems1_v2) continue;
                                                Object[] mapElems_mapElems1_mapElems1Rest_ac = new Object[((void)mapElems_a6).length];
                                                if (mapElems_mapElems1_i > 0) {
                                                    System.arraycopy(mapElems_a6, 0, mapElems_mapElems1_mapElems1Rest_ac, 0, mapElems_mapElems1_i);
                                                }
                                                mapElems_mapElems1_mapElems1Rest_ac[mapElems_mapElems1_i] = mapElems_mapElems1_v2;
                                                for (int mapElems_mapElems1_mapElems1Rest_i = mapElems_mapElems1_i + 1; mapElems_mapElems1_mapElems1Rest_i < ((void)mapElems_a6).length; ++mapElems_mapElems1_mapElems1Rest_i) {
                                                    mapElems_mapElems1_mapElems1Rest_ac[mapElems_mapElems1_mapElems1Rest_i] = f.apply(mapElems_a6[mapElems_mapElems1_mapElems1Rest_i]);
                                                }
                                                objectArray12 = mapElems_mapElems1_mapElems1Rest_ac;
                                                var56_39 = null;
                                                break block59;
                                            }
                                            objectArray12 = mapElems_a6;
                                        }
                                        var54_37 = null;
                                        var55_38 = null;
                                        var56_39 = null;
                                    } else {
                                        for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a6).length; ++mapElems_i) {
                                            void mapElems_v1 = mapElems_a6[mapElems_i];
                                            T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n6 - true), (T[])mapElems_v1, f);
                                            if (mapElems_v1 == mapElems_v2) continue;
                                            objectArray12 = mapElems_this.mapElemsRest((int)mapElems_n6, (T[])mapElems_a6, f, mapElems_i, mapElems_v2);
                                            break block60;
                                        }
                                        objectArray12 = mapElems_a6;
                                    }
                                }
                                Object var47_35 = null;
                                objectArray13 = null;
                                Object var51_42 = null;
                                Object var52_43 = null;
                                var54_37 = null;
                                var55_38 = null;
                                var56_39 = null;
                                x$4 = (Object[][][][])objectArray12;
                                objectArray11 = this.prefix5();
                                int n = 5;
                                VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                                if (mapElems_n5 == true) {
                                    block61: {
                                        for (int mapElems_mapElems1_i = 0; mapElems_mapElems1_i < ((void)mapElems_a5).length; ++mapElems_mapElems1_i) {
                                            void mapElems_mapElems1_v1 = mapElems_a5[mapElems_mapElems1_i];
                                            B mapElems_mapElems1_v2 = f.apply(mapElems_mapElems1_v1);
                                            if (mapElems_mapElems1_v1 == mapElems_mapElems1_v2) continue;
                                            Object[] mapElems_mapElems1_mapElems1Rest_ac = new Object[((void)mapElems_a5).length];
                                            if (mapElems_mapElems1_i > 0) {
                                                System.arraycopy(mapElems_a5, 0, mapElems_mapElems1_mapElems1Rest_ac, 0, mapElems_mapElems1_i);
                                            }
                                            mapElems_mapElems1_mapElems1Rest_ac[mapElems_mapElems1_i] = mapElems_mapElems1_v2;
                                            for (int mapElems_mapElems1_mapElems1Rest_i = mapElems_mapElems1_i + 1; mapElems_mapElems1_mapElems1Rest_i < ((void)mapElems_a5).length; ++mapElems_mapElems1_mapElems1Rest_i) {
                                                mapElems_mapElems1_mapElems1Rest_ac[mapElems_mapElems1_mapElems1Rest_i] = f.apply(mapElems_a5[mapElems_mapElems1_mapElems1Rest_i]);
                                            }
                                            objectArray10 = mapElems_mapElems1_mapElems1Rest_ac;
                                            var67_51 = null;
                                            break block61;
                                        }
                                        objectArray10 = mapElems_a5;
                                    }
                                    var65_49 = null;
                                    var66_50 = null;
                                    var67_51 = null;
                                } else {
                                    for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a5).length; ++mapElems_i) {
                                        void mapElems_v1 = mapElems_a5[mapElems_i];
                                        T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n5 - true), (T[])mapElems_v1, f);
                                        if (mapElems_v1 == mapElems_v2) continue;
                                        objectArray10 = mapElems_this.mapElemsRest((int)mapElems_n5, (T[])mapElems_a5, f, mapElems_i, mapElems_v2);
                                        break block62;
                                    }
                                    objectArray10 = mapElems_a5;
                                }
                            }
                            Object var58_47 = null;
                            objectArray11 = null;
                            Object var62_54 = null;
                            Object var63_55 = null;
                            var65_49 = null;
                            var66_50 = null;
                            var67_51 = null;
                            x$5 = (Object[][][][][])objectArray10;
                            objectArray9 = this.data6();
                            int n = 6;
                            VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                            if (mapElems_n4 == true) {
                                block63: {
                                    for (int mapElems_mapElems1_i = 0; mapElems_mapElems1_i < ((void)mapElems_a4).length; ++mapElems_mapElems1_i) {
                                        void mapElems_mapElems1_v1 = mapElems_a4[mapElems_mapElems1_i];
                                        B mapElems_mapElems1_v2 = f.apply(mapElems_mapElems1_v1);
                                        if (mapElems_mapElems1_v1 == mapElems_mapElems1_v2) continue;
                                        Object[] mapElems_mapElems1_mapElems1Rest_ac = new Object[((void)mapElems_a4).length];
                                        if (mapElems_mapElems1_i > 0) {
                                            System.arraycopy(mapElems_a4, 0, mapElems_mapElems1_mapElems1Rest_ac, 0, mapElems_mapElems1_i);
                                        }
                                        mapElems_mapElems1_mapElems1Rest_ac[mapElems_mapElems1_i] = mapElems_mapElems1_v2;
                                        for (int mapElems_mapElems1_mapElems1Rest_i = mapElems_mapElems1_i + 1; mapElems_mapElems1_mapElems1Rest_i < ((void)mapElems_a4).length; ++mapElems_mapElems1_mapElems1Rest_i) {
                                            mapElems_mapElems1_mapElems1Rest_ac[mapElems_mapElems1_mapElems1Rest_i] = f.apply(mapElems_a4[mapElems_mapElems1_mapElems1Rest_i]);
                                        }
                                        objectArray8 = mapElems_mapElems1_mapElems1Rest_ac;
                                        var78_63 = null;
                                        break block63;
                                    }
                                    objectArray8 = mapElems_a4;
                                }
                                var76_61 = null;
                                var77_62 = null;
                                var78_63 = null;
                            } else {
                                for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a4).length; ++mapElems_i) {
                                    void mapElems_v1 = mapElems_a4[mapElems_i];
                                    T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n4 - true), (T[])mapElems_v1, f);
                                    if (mapElems_v1 == mapElems_v2) continue;
                                    objectArray8 = mapElems_this.mapElemsRest((int)mapElems_n4, (T[])mapElems_a4, f, mapElems_i, mapElems_v2);
                                    break block64;
                                }
                                objectArray8 = mapElems_a4;
                            }
                        }
                        Object var69_59 = null;
                        objectArray9 = null;
                        Object var73_66 = null;
                        Object var74_67 = null;
                        var76_61 = null;
                        var77_62 = null;
                        var78_63 = null;
                        x$6 = (Object[][][][][][])objectArray8;
                        objectArray7 = this.suffix5();
                        int n = 5;
                        VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                        if (mapElems_n3 == true) {
                            block65: {
                                for (int mapElems_mapElems1_i = 0; mapElems_mapElems1_i < ((void)mapElems_a3).length; ++mapElems_mapElems1_i) {
                                    void mapElems_mapElems1_v1 = mapElems_a3[mapElems_mapElems1_i];
                                    B mapElems_mapElems1_v2 = f.apply(mapElems_mapElems1_v1);
                                    if (mapElems_mapElems1_v1 == mapElems_mapElems1_v2) continue;
                                    Object[] mapElems_mapElems1_mapElems1Rest_ac = new Object[((void)mapElems_a3).length];
                                    if (mapElems_mapElems1_i > 0) {
                                        System.arraycopy(mapElems_a3, 0, mapElems_mapElems1_mapElems1Rest_ac, 0, mapElems_mapElems1_i);
                                    }
                                    mapElems_mapElems1_mapElems1Rest_ac[mapElems_mapElems1_i] = mapElems_mapElems1_v2;
                                    for (int mapElems_mapElems1_mapElems1Rest_i = mapElems_mapElems1_i + 1; mapElems_mapElems1_mapElems1Rest_i < ((void)mapElems_a3).length; ++mapElems_mapElems1_mapElems1Rest_i) {
                                        mapElems_mapElems1_mapElems1Rest_ac[mapElems_mapElems1_mapElems1Rest_i] = f.apply(mapElems_a3[mapElems_mapElems1_mapElems1Rest_i]);
                                    }
                                    objectArray6 = mapElems_mapElems1_mapElems1Rest_ac;
                                    var89_75 = null;
                                    break block65;
                                }
                                objectArray6 = mapElems_a3;
                            }
                            var87_73 = null;
                            var88_74 = null;
                            var89_75 = null;
                        } else {
                            for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a3).length; ++mapElems_i) {
                                void mapElems_v1 = mapElems_a3[mapElems_i];
                                T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n3 - true), (T[])mapElems_v1, f);
                                if (mapElems_v1 == mapElems_v2) continue;
                                objectArray6 = mapElems_this.mapElemsRest((int)mapElems_n3, (T[])mapElems_a3, f, mapElems_i, mapElems_v2);
                                break block66;
                            }
                            objectArray6 = mapElems_a3;
                        }
                    }
                    Object var80_71 = null;
                    objectArray7 = null;
                    Object var84_78 = null;
                    Object var85_79 = null;
                    var87_73 = null;
                    var88_74 = null;
                    var89_75 = null;
                    x$7 = (Object[][][][][])objectArray6;
                    objectArray5 = this.suffix4();
                    int n = 4;
                    VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                    if (mapElems_n2 == true) {
                        block67: {
                            for (int mapElems_mapElems1_i = 0; mapElems_mapElems1_i < ((void)mapElems_a2).length; ++mapElems_mapElems1_i) {
                                void mapElems_mapElems1_v1 = mapElems_a2[mapElems_mapElems1_i];
                                B mapElems_mapElems1_v2 = f.apply(mapElems_mapElems1_v1);
                                if (mapElems_mapElems1_v1 == mapElems_mapElems1_v2) continue;
                                Object[] mapElems_mapElems1_mapElems1Rest_ac = new Object[((void)mapElems_a2).length];
                                if (mapElems_mapElems1_i > 0) {
                                    System.arraycopy(mapElems_a2, 0, mapElems_mapElems1_mapElems1Rest_ac, 0, mapElems_mapElems1_i);
                                }
                                mapElems_mapElems1_mapElems1Rest_ac[mapElems_mapElems1_i] = mapElems_mapElems1_v2;
                                for (int mapElems_mapElems1_mapElems1Rest_i = mapElems_mapElems1_i + 1; mapElems_mapElems1_mapElems1Rest_i < ((void)mapElems_a2).length; ++mapElems_mapElems1_mapElems1Rest_i) {
                                    mapElems_mapElems1_mapElems1Rest_ac[mapElems_mapElems1_mapElems1Rest_i] = f.apply(mapElems_a2[mapElems_mapElems1_mapElems1Rest_i]);
                                }
                                objectArray4 = mapElems_mapElems1_mapElems1Rest_ac;
                                var100_87 = null;
                                break block67;
                            }
                            objectArray4 = mapElems_a2;
                        }
                        var98_85 = null;
                        var99_86 = null;
                        var100_87 = null;
                    } else {
                        for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a2).length; ++mapElems_i) {
                            void mapElems_v1 = mapElems_a2[mapElems_i];
                            T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n2 - true), (T[])mapElems_v1, f);
                            if (mapElems_v1 == mapElems_v2) continue;
                            objectArray4 = mapElems_this.mapElemsRest((int)mapElems_n2, (T[])mapElems_a2, f, mapElems_i, mapElems_v2);
                            break block68;
                        }
                        objectArray4 = mapElems_a2;
                    }
                }
                Object var91_83 = null;
                objectArray5 = null;
                Object var95_90 = null;
                Object var96_91 = null;
                var98_85 = null;
                var99_86 = null;
                var100_87 = null;
                x$8 = (Object[][][][])objectArray4;
                objectArray3 = this.suffix3();
                int n = 3;
                VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                if (mapElems_n == true) {
                    block69: {
                        for (int mapElems_mapElems1_i = 0; mapElems_mapElems1_i < ((void)mapElems_a).length; ++mapElems_mapElems1_i) {
                            void mapElems_mapElems1_v1 = mapElems_a[mapElems_mapElems1_i];
                            B mapElems_mapElems1_v2 = f.apply(mapElems_mapElems1_v1);
                            if (mapElems_mapElems1_v1 == mapElems_mapElems1_v2) continue;
                            Object[] mapElems_mapElems1_mapElems1Rest_ac = new Object[((void)mapElems_a).length];
                            if (mapElems_mapElems1_i > 0) {
                                System.arraycopy(mapElems_a, 0, mapElems_mapElems1_mapElems1Rest_ac, 0, mapElems_mapElems1_i);
                            }
                            mapElems_mapElems1_mapElems1Rest_ac[mapElems_mapElems1_i] = mapElems_mapElems1_v2;
                            for (int mapElems_mapElems1_mapElems1Rest_i = mapElems_mapElems1_i + 1; mapElems_mapElems1_mapElems1Rest_i < ((void)mapElems_a).length; ++mapElems_mapElems1_mapElems1Rest_i) {
                                mapElems_mapElems1_mapElems1Rest_ac[mapElems_mapElems1_mapElems1Rest_i] = f.apply(mapElems_a[mapElems_mapElems1_mapElems1Rest_i]);
                            }
                            objectArray2 = mapElems_mapElems1_mapElems1Rest_ac;
                            var111_99 = null;
                            break block69;
                        }
                        objectArray2 = mapElems_a;
                    }
                    var109_97 = null;
                    var110_98 = null;
                    var111_99 = null;
                } else {
                    for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a).length; ++mapElems_i) {
                        void mapElems_v1 = mapElems_a[mapElems_i];
                        T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n - true), (T[])mapElems_v1, f);
                        if (mapElems_v1 == mapElems_v2) continue;
                        objectArray2 = mapElems_this.mapElemsRest((int)mapElems_n, (T[])mapElems_a, f, mapElems_i, mapElems_v2);
                        break block70;
                    }
                    objectArray2 = mapElems_a;
                }
            }
            Object var102_95 = null;
            objectArray3 = null;
            Object var106_102 = null;
            Object var107_103 = null;
            var109_97 = null;
            var110_98 = null;
            var111_99 = null;
            x$9 = (Object[][][])objectArray2;
            x$10 = (Object[][])VectorStatics$.MODULE$.mapElems(2, (T[])this.suffix2(), f);
            Object[] mapElems1_a = this.suffix1();
            for (int mapElems1_i = 0; mapElems1_i < mapElems1_a.length; ++mapElems1_i) {
                Object mapElems1_v1 = mapElems1_a[mapElems1_i];
                B mapElems1_v2 = f.apply(mapElems1_v1);
                if (mapElems1_v1 == mapElems1_v2) continue;
                Object[] mapElems1_mapElems1Rest_ac = new Object[mapElems1_a.length];
                if (mapElems1_i > 0) {
                    System.arraycopy(mapElems1_a, 0, mapElems1_mapElems1Rest_ac, 0, mapElems1_i);
                }
                mapElems1_mapElems1Rest_ac[mapElems1_i] = mapElems1_v2;
                for (int mapElems1_mapElems1Rest_i = mapElems1_i + 1; mapElems1_mapElems1Rest_i < mapElems1_a.length; ++mapElems1_mapElems1Rest_i) {
                    mapElems1_mapElems1Rest_ac[mapElems1_mapElems1Rest_i] = f.apply(mapElems1_a[mapElems1_mapElems1Rest_i]);
                }
                objectArray = mapElems1_mapElems1Rest_ac;
                var117_110 = null;
                break block71;
            }
            objectArray = mapElems1_a;
        }
        Object var113_106 = null;
        Object var115_108 = null;
        Object var116_109 = null;
        var117_110 = null;
        Object[] x$11 = objectArray;
        int x$12 = this.copy$default$2();
        int x$13 = this.copy$default$4();
        int x$14 = this.copy$default$6();
        int x$15 = this.copy$default$8();
        int x$16 = this.copy$default$10();
        int x$17 = this.copy$default$17();
        return new Vector6<A>(x$1, x$12, x$2, x$13, x$3, x$14, x$4, x$15, x$5, x$16, x$6, x$7, x$8, x$9, x$10, x$11, x$17);
    }

    @Override
    public Vector<A> slice0(int lo, int hi) {
        VectorSliceBuilder b = new VectorSliceBuilder(lo, hi);
        b.consider(1, this.prefix1());
        b.consider(2, (T[])this.prefix2());
        b.consider(3, (T[])this.prefix3());
        b.consider(4, (T[])this.prefix4());
        b.consider(5, (T[])this.prefix5());
        b.consider(6, (T[])this.data6());
        b.consider(5, (T[])this.suffix5());
        b.consider(4, (T[])this.suffix4());
        b.consider(3, (T[])this.suffix3());
        b.consider(2, (T[])this.suffix2());
        b.consider(1, this.suffix1());
        return b.result();
    }

    @Override
    public Vector<A> tail() {
        if (this.len1() > 1) {
            Object[] copyTail_a = this.prefix1();
            Object var18_1 = null;
            Object[] x$1 = Arrays.copyOfRange(copyTail_a, 1, copyTail_a.length);
            int x$2 = this.len1() - 1;
            int x$3 = this.len12() - 1;
            int x$4 = this.len123() - 1;
            int x$5 = this.len1234() - 1;
            int x$6 = this.len12345() - 1;
            int x$7 = this.length0() - 1;
            Object[][] x$8 = this.prefix2();
            Object[][][] x$9 = this.prefix3();
            Object[][][][] x$10 = this.prefix4();
            Object[][][][][] x$11 = this.prefix5();
            Object[][][][][][] x$12 = this.data6();
            Object[][][][][] x$13 = this.suffix5();
            Object[][][][] x$14 = this.suffix4();
            Object[][][] x$15 = this.suffix3();
            Object[][] x$16 = this.suffix2();
            Object[] x$17 = this.suffix1();
            return new Vector6<A>(x$1, x$2, x$8, x$3, x$9, x$4, x$10, x$5, x$11, x$6, x$12, x$13, x$14, x$15, x$16, x$17, x$7);
        }
        return this.slice0(1, this.length0());
    }

    @Override
    public Vector<A> init() {
        if (this.suffix1().length > 1) {
            Object[] copyInit_a = this.suffix1();
            Object var18_1 = null;
            Object[] x$1 = Arrays.copyOfRange(copyInit_a, 0, copyInit_a.length - 1);
            int x$2 = this.length0() - 1;
            Object[] x$3 = this.prefix1();
            int x$4 = this.len1();
            Object[][] x$5 = this.prefix2();
            int x$6 = this.len12();
            Object[][][] x$7 = this.prefix3();
            int x$8 = this.len123();
            Object[][][][] x$9 = this.prefix4();
            int x$10 = this.len1234();
            Object[][][][][] x$11 = this.prefix5();
            int x$12 = this.len12345();
            Object[][][][][][] x$13 = this.data6();
            Object[][][][][] x$14 = this.suffix5();
            Object[][][][] x$15 = this.suffix4();
            Object[][][] x$16 = this.suffix3();
            Object[][] x$17 = this.suffix2();
            return new Vector6<A>(x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$14, x$15, x$16, x$17, x$1, x$2);
        }
        return this.slice0(0, this.length0() - 1);
    }

    @Override
    public int vectorSliceCount() {
        return 11;
    }

    @Override
    public Object[] vectorSlice(int idx) {
        switch (idx) {
            case 0: {
                return this.prefix1();
            }
            case 1: {
                return this.prefix2();
            }
            case 2: {
                return this.prefix3();
            }
            case 3: {
                return this.prefix4();
            }
            case 4: {
                return this.prefix5();
            }
            case 5: {
                return this.data6();
            }
            case 6: {
                return this.suffix5();
            }
            case 7: {
                return this.suffix4();
            }
            case 8: {
                return this.suffix3();
            }
            case 9: {
                return this.suffix2();
            }
            case 10: {
                return this.suffix1();
            }
        }
        throw new MatchError(idx);
    }

    @Override
    public int vectorSlicePrefixLength(int idx) {
        switch (idx) {
            case 0: {
                return this.len1();
            }
            case 1: {
                return this.len12();
            }
            case 2: {
                return this.len123();
            }
            case 3: {
                return this.len1234();
            }
            case 4: {
                return this.len12345();
            }
            case 5: {
                return this.len12345() + this.data6().length * 0x2000000;
            }
            case 6: {
                return this.len12345() + this.data6().length * 0x2000000 + this.suffix5().length * 0x100000;
            }
            case 7: {
                return this.len12345() + this.data6().length * 0x2000000 + this.suffix5().length * 0x100000 + this.suffix4().length * 32768;
            }
            case 8: {
                return this.len12345() + this.data6().length * 0x2000000 + this.suffix5().length * 0x100000 + this.suffix4().length * 32768 + this.suffix3().length * 1024;
            }
            case 9: {
                return this.length0() - this.suffix1().length;
            }
            case 10: {
                return this.length0();
            }
        }
        throw new MatchError(idx);
    }

    @Override
    public <B> Vector<B> prependedAll0(IterableOnce<B> prefix, int k) {
        Object[] objectArray = VectorStatics$.MODULE$.prepend1IfSpace(this.prefix1(), prefix);
        if (objectArray == null) {
            return super.prependedAll0(prefix, k);
        }
        int diff = objectArray.length - this.prefix1().length;
        int x$2 = this.len1() + diff;
        int x$3 = this.len12() + diff;
        int x$4 = this.len123() + diff;
        int x$5 = this.len1234() + diff;
        int x$6 = this.len12345() + diff;
        int x$7 = this.length0() + diff;
        Object[][] x$8 = this.prefix2();
        Object[][][] x$9 = this.prefix3();
        Object[][][][] x$10 = this.prefix4();
        Object[][][][][] x$11 = this.prefix5();
        Object[][][][][][] x$12 = this.data6();
        Object[][][][][] x$13 = this.suffix5();
        Object[][][][] x$14 = this.suffix4();
        Object[][][] x$15 = this.suffix3();
        Object[][] x$16 = this.suffix2();
        Object[] x$17 = this.suffix1();
        return new Vector6<A>(objectArray, x$2, x$8, x$3, x$9, x$4, x$10, x$5, x$11, x$6, x$12, x$13, x$14, x$15, x$16, x$17, x$7);
    }

    @Override
    public <B> Vector<B> appendedAll0(IterableOnce<B> suffix, int k) {
        Object[] suffix1b = VectorStatics$.MODULE$.append1IfSpace(this.suffix1(), suffix);
        if (suffix1b != null) {
            int x$2 = this.length0() - this.suffix1().length + suffix1b.length;
            Object[] x$3 = this.prefix1();
            int x$4 = this.len1();
            Object[][] x$5 = this.prefix2();
            int x$6 = this.len12();
            Object[][][] x$7 = this.prefix3();
            int x$8 = this.len123();
            Object[][][][] x$9 = this.prefix4();
            int x$10 = this.len1234();
            Object[][][][][] x$11 = this.prefix5();
            int x$12 = this.len12345();
            Object[][][][][][] x$13 = this.data6();
            Object[][][][][] x$14 = this.suffix5();
            Object[][][][] x$15 = this.suffix4();
            Object[][][] x$16 = this.suffix3();
            Object[][] x$17 = this.suffix2();
            return new Vector6<A>(x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$14, x$15, x$16, x$17, suffix1b, x$2);
        }
        return super.appendedAll0(suffix, k);
    }

    public Vector6(Object[] _prefix1, int len1, Object[][] prefix2, int len12, Object[][][] prefix3, int len123, Object[][][][] prefix4, int len1234, Object[][][][][] prefix5, int len12345, Object[][][][][][] data6, Object[][][][][] suffix5, Object[][][][] suffix4, Object[][][] suffix3, Object[][] suffix2, Object[] _suffix1, int _length0) {
        this.len1 = len1;
        this.prefix2 = prefix2;
        this.len12 = len12;
        this.prefix3 = prefix3;
        this.len123 = len123;
        this.prefix4 = prefix4;
        this.len1234 = len1234;
        this.prefix5 = prefix5;
        this.len12345 = len12345;
        this.data6 = data6;
        this.suffix5 = suffix5;
        this.suffix4 = suffix4;
        this.suffix3 = suffix3;
        this.suffix2 = suffix2;
        super(_prefix1, _suffix1, _length0);
    }
}

