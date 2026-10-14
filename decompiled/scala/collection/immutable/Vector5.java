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
import scala.collection.immutable.Vector6;
import scala.collection.immutable.VectorSliceBuilder;
import scala.collection.immutable.VectorStatics$;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;

@ScalaSignature(bytes="\u0006\u0005\tmd\u0001B\u001c9\r}B\u0011\"\u0015\u0001\u0003\u0002\u0003\u0006IA\u00152\t\u0013\u0019\u0004!Q1A\u0005\u0002a:\u0007\u0002C6\u0001\u0005\u0003\u0005\u000b\u0011\u00025\t\u00131\u0004!Q1A\u0005\u0002aj\u0007\u0002C9\u0001\u0005\u0003\u0005\u000b\u0011\u00028\t\u0013I\u0004!Q1A\u0005\u0002a:\u0007\u0002C:\u0001\u0005\u0003\u0005\u000b\u0011\u00025\t\u0013Q\u0004!Q1A\u0005\u0002a*\b\u0002C=\u0001\u0005\u0003\u0005\u000b\u0011\u0002<\t\u0013i\u0004!Q1A\u0005\u0002a:\u0007\u0002C>\u0001\u0005\u0003\u0005\u000b\u0011\u00025\t\u0013q\u0004!Q1A\u0005\u0002aj\b\"CA\u0002\u0001\t\u0005\t\u0015!\u0003\u007f\u0011)\t)\u0001\u0001BC\u0002\u0013\u0005\u0001h\u001a\u0005\n\u0003\u000f\u0001!\u0011!Q\u0001\n!D1\"!\u0003\u0001\u0005\u000b\u0007I\u0011\u0001\u001d\u0002\f!Q\u00111\u0003\u0001\u0003\u0002\u0003\u0006I!!\u0004\t\u0015\u0005U\u0001A!b\u0001\n\u0003AT\u0010C\u0005\u0002\u0018\u0001\u0011\t\u0011)A\u0005}\"Q\u0011\u0011\u0004\u0001\u0003\u0006\u0004%\t\u0001O;\t\u0013\u0005m\u0001A!A!\u0002\u00131\bBCA\u000f\u0001\t\u0015\r\u0011\"\u00019[\"I\u0011q\u0004\u0001\u0003\u0002\u0003\u0006IA\u001c\u0005\f\u0003C\u0001!\u0011!Q\u0001\nI\u000b\u0019\u0003C\u0006\u0002(\u0001\u0011\t\u0011)A\u0005Q\u0006%\u0002bBA\u0017\u0001\u0011\u0005\u0011q\u0006\u0005\t\u0003\u001f\u0002\u0001\u0015\"\u0003\u0002R!I\u0011\u0011\u0010\u0001\u0012\u0002\u0013%\u00111\u0010\u0005\n\u0003#\u0003\u0011\u0013!C\u0005\u0003'C\u0011\"a&\u0001#\u0003%I!!'\t\u0013\u0005u\u0005!%A\u0005\n\u0005M\u0005\"CAP\u0001E\u0005I\u0011BAQ\u0011%\t)\u000bAI\u0001\n\u0013\t\u0019\nC\u0005\u0002(\u0002\t\n\u0011\"\u0003\u0002*\"I\u0011Q\u0016\u0001\u0012\u0002\u0013%\u00111\u0013\u0005\n\u0003_\u0003\u0011\u0013!C\u0005\u0003cC\u0011\"!.\u0001#\u0003%I!!+\t\u0013\u0005]\u0006!%A\u0005\n\u0005\u0005\u0006\"CA]\u0001E\u0005I\u0011BAM\u0011%\tY\fAI\u0001\n\u0013\tY\bC\u0005\u0002>\u0002\t\n\u0011\"\u0003\u0002\u0014\"9\u0011q\u0018\u0001\u0005\u0002\u0005\u0005\u0007bBAe\u0001\u0011\u0005\u00131\u001a\u0005\b\u0003?\u0004A\u0011IAq\u0011\u001d\ti\u000f\u0001C!\u0003_Dq!a?\u0001\t\u0003\ni\u0010\u0003\u0005\u0003\u0012\u0001\u0001K\u0011\u0003B\n\u0011\u001d\u0011y\u0002\u0001C!\u0005CAqAa\t\u0001\t\u0003\u0012\t\u0003C\u0004\u0003&\u0001!\t\u0002O4\t\u0011\t\u001d\u0002\u0001\"\u00059\u0005SA\u0001B!\u0012\u0001\t#A$q\t\u0005\t\u0005\u0017\u0002\u0001\u0015\"\u0015\u0003N!A!q\r\u0001!\n#\u0012IGA\u0004WK\u000e$xN]\u001b\u000b\u0005eR\u0014!C5n[V$\u0018M\u00197f\u0015\tYD(\u0001\u0006d_2dWm\u0019;j_:T\u0011!P\u0001\u0006g\u000e\fG.Y\u0002\u0001+\t\u0001ui\u0005\u0002\u0001\u0003B\u0019!iQ#\u000e\u0003aJ!\u0001\u0012\u001d\u0003\u0013\tKwMV3di>\u0014\bC\u0001$H\u0019\u0001!a\u0001\u0013\u0001\u0005\u0006\u0004I%!A!\u0012\u0005)s\u0005CA&M\u001b\u0005a\u0014BA'=\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"aS(\n\u0005Ac$aA!os\u0006Aq\f\u001d:fM&D\u0018\u0007\u0005\u0002T?:\u0011A+\u0018\b\u0003+rs!AV.\u000f\u0005]SV\"\u0001-\u000b\u0005es\u0014A\u0002\u001fs_>$h(C\u0001>\u0013\tYD(\u0003\u0002:u%\u0011a\fO\u0001\r-\u0016\u001cGo\u001c:J]2Lg.Z\u0005\u0003A\u0006\u0014A!\u0011:sc)\u0011a\fO\u0005\u0003G\u0012\fq\u0001\u001d:fM&D\u0018'\u0003\u0002fq\t1a+Z2u_J\fA\u0001\\3ocU\t\u0001\u000e\u0005\u0002LS&\u0011!\u000e\u0010\u0002\u0004\u0013:$\u0018!\u00027f]F\u0002\u0013a\u00029sK\u001aL\u0007PM\u000b\u0002]B\u00111k\\\u0005\u0003a\u0006\u0014A!\u0011:se\u0005A\u0001O]3gSb\u0014\u0004%A\u0003mK:\f$'\u0001\u0004mK:\f$\u0007I\u0001\baJ,g-\u001b=4+\u00051\bCA*x\u0013\tA\u0018M\u0001\u0003BeJ\u001c\u0014\u0001\u00039sK\u001aL\u0007p\r\u0011\u0002\r1,g.\r\u001a4\u0003\u001daWM\\\u00193g\u0001\nq\u0001\u001d:fM&DH'F\u0001\u007f!\t\u0019v0C\u0002\u0002\u0002\u0005\u0014A!\u0011:si\u0005A\u0001O]3gSb$\u0004%A\u0004mK:\f$g\r\u001b\u0002\u00111,g.\r\u001a4i\u0001\nQ\u0001Z1uCV*\"!!\u0004\u0011\u0007M\u000by!C\u0002\u0002\u0012\u0005\u0014A!\u0011:sk\u00051A-\u0019;bk\u0001\nqa];gM&DH'\u0001\u0005tk\u001a4\u0017\u000e\u001f\u001b!\u0003\u001d\u0019XO\u001a4jqN\n\u0001b];gM&D8\u0007I\u0001\bgV4g-\u001b=3\u0003!\u0019XO\u001a4jqJ\u0002\u0013\u0001C0tk\u001a4\u0017\u000e_\u0019\n\u0007\u0005\u00152)A\u0004tk\u001a4\u0017\u000e_\u0019\u0002\u0011}cWM\\4uQBJ1!a\u000bD\u0003\u001daWM\\4uQB\na\u0001P5oSRtDCHA\u0019\u0003g\t)$a\u000e\u0002:\u0005m\u0012QHA \u0003\u0003\n\u0019%!\u0012\u0002H\u0005%\u00131JA'!\r\u0011\u0005!\u0012\u0005\u0006#j\u0001\rA\u0015\u0005\u0006Mj\u0001\r\u0001\u001b\u0005\u0006Yj\u0001\rA\u001c\u0005\u0006ej\u0001\r\u0001\u001b\u0005\u0006ij\u0001\rA\u001e\u0005\u0006uj\u0001\r\u0001\u001b\u0005\u0006yj\u0001\rA \u0005\u0007\u0003\u000bQ\u0002\u0019\u00015\t\u000f\u0005%!\u00041\u0001\u0002\u000e!1\u0011Q\u0003\u000eA\u0002yDa!!\u0007\u001b\u0001\u00041\bBBA\u000f5\u0001\u0007a\u000e\u0003\u0004\u0002\"i\u0001\rA\u0015\u0005\u0007\u0003OQ\u0002\u0019\u00015\u0002\t\r|\u0007/\u001f\u000b\u001f\u0003'\n)&a\u0016\u0002Z\u0005m\u0013QLA0\u0003C\n\u0019'!\u001a\u0002h\u0005%\u00141NA7\u0003_\u00022A\u0011\u0001K\u0011\u001d\u00197\u0004%AA\u0002ICqAZ\u000e\u0011\u0002\u0003\u0007\u0001\u000eC\u0004m7A\u0005\t\u0019\u00018\t\u000fI\\\u0002\u0013!a\u0001Q\"9Ao\u0007I\u0001\u0002\u00041\bb\u0002>\u001c!\u0003\u0005\r\u0001\u001b\u0005\byn\u0001\n\u00111\u0001\u007f\u0011!\t)a\u0007I\u0001\u0002\u0004A\u0007\"CA\u00057A\u0005\t\u0019AA\u0007\u0011!\t)b\u0007I\u0001\u0002\u0004q\b\u0002CA\r7A\u0005\t\u0019\u0001<\t\u0011\u0005u1\u0004%AA\u00029D\u0001\"!\n\u001c!\u0003\u0005\rA\u0015\u0005\t\u0003WY\u0002\u0013!a\u0001Q\"\u001a1$a\u001d\u0011\u0007-\u000b)(C\u0002\u0002xq\u0012a!\u001b8mS:,\u0017AD2paf$C-\u001a4bk2$H%M\u000b\u0003\u0003{R3AUA@W\t\t\t\t\u0005\u0003\u0002\u0004\u00065UBAAC\u0015\u0011\t9)!#\u0002\u0013Ut7\r[3dW\u0016$'bAAFy\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005=\u0015Q\u0011\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u000b\u0003\u0003+S3\u0001[A@\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*\"!a'+\u00079\fy(\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001b\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%kU\u0011\u00111\u0015\u0016\u0004m\u0006}\u0014AD2paf$C-\u001a4bk2$HEN\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00138+\t\tYKK\u0002\u007f\u0003\u007f\nabY8qs\u0012\"WMZ1vYR$\u0003(\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001d\u0016\u0005\u0005M&\u0006BA\u0007\u0003\u007f\nqbY8qs\u0012\"WMZ1vYR$\u0013\u0007M\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132c\u0005y1m\u001c9zI\u0011,g-Y;mi\u0012\n$'A\bd_BLH\u0005Z3gCVdG\u000fJ\u00194\u0003=\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE\"\u0014!B1qa2LHcA#\u0002D\"1\u0011Q\u0019\u0016A\u0002!\fQ!\u001b8eKbD3AKA:\u0003\u001d)\b\u000fZ1uK\u0012,B!!4\u0002TR1\u0011qZAm\u00037\u0004BA\u00113\u0002RB\u0019a)a5\u0005\u000f\u0005U7F1\u0001\u0002X\n\t!)\u0005\u0002F\u001d\"1\u0011QY\u0016A\u0002!Dq!!8,\u0001\u0004\t\t.\u0001\u0003fY\u0016l\u0017\u0001C1qa\u0016tG-\u001a3\u0016\t\u0005\r\u0018\u0011\u001e\u000b\u0005\u0003K\fY\u000f\u0005\u0003CI\u0006\u001d\bc\u0001$\u0002j\u00129\u0011Q\u001b\u0017C\u0002\u0005]\u0007bBAoY\u0001\u0007\u0011q]\u0001\naJ,\u0007/\u001a8eK\u0012,B!!=\u0002xR!\u00111_A}!\u0011\u0011E-!>\u0011\u0007\u0019\u000b9\u0010B\u0004\u0002V6\u0012\r!a6\t\u000f\u0005uW\u00061\u0001\u0002v\u0006\u0019Q.\u00199\u0016\t\u0005}(Q\u0001\u000b\u0005\u0005\u0003\u00119\u0001\u0005\u0003CI\n\r\u0001c\u0001$\u0003\u0006\u00111\u0011Q\u001b\u0018C\u0002%CqA!\u0003/\u0001\u0004\u0011Y!A\u0001g!\u0019Y%QB#\u0003\u0004%\u0019!q\u0002\u001f\u0003\u0013\u0019+hn\u0019;j_:\f\u0014AB:mS\u000e,\u0007\u0007\u0006\u0004\u0003\u0016\t]!1\u0004\t\u0004\u0005\u0012,\u0005B\u0002B\r_\u0001\u0007\u0001.\u0001\u0002m_\"1!QD\u0018A\u0002!\f!\u0001[5\u0002\tQ\f\u0017\u000e\\\u000b\u0003\u0005+\tA!\u001b8ji\u0006\u0001b/Z2u_J\u001cF.[2f\u0007>,h\u000e^\u0001\fm\u0016\u001cGo\u001c:TY&\u001cW\r\u0006\u0003\u0003,\t\u0005\u0003\u0007\u0002B\u0017\u0005k\u0001Ra\u0013B\u0018\u0005gI1A!\r=\u0005\u0015\t%O]1z!\r1%Q\u0007\u0003\f\u0005o\u0019\u0014\u0011!A\u0001\u0006\u0003\u0011ID\u0001\u0003`IE2\u0014c\u0001&\u0003<A\u00191J!\u0010\n\u0007\t}BH\u0001\u0004B]f\u0014VM\u001a\u0005\u0007\u0005\u0007\u001a\u0004\u0019\u00015\u0002\u0007%$\u00070A\fwK\u000e$xN]*mS\u000e,\u0007K]3gSbdUM\\4uQR\u0019\u0001N!\u0013\t\r\t\rC\u00071\u0001i\u00035\u0001(/\u001a9f]\u0012,G-\u00117maU!!q\nB+)\u0019\u0011\tFa\u0016\u0003dA!!\t\u001aB*!\r1%Q\u000b\u0003\b\u0003+,$\u0019AAl\u0011\u001d\u0011I&\u000ea\u0001\u00057\na\u0001\u001d:fM&D\bC\u0002B/\u0005?\u0012\u0019&D\u0001;\u0013\r\u0011\tG\u000f\u0002\r\u0013R,'/\u00192mK>s7-\u001a\u0005\u0007\u0005K*\u0004\u0019\u00015\u0002\u0003-\fA\"\u00199qK:$W\rZ!mYB*BAa\u001b\u0003rQ1!Q\u000eB:\u0005s\u0002BA\u00113\u0003pA\u0019aI!\u001d\u0005\u000f\u0005UgG1\u0001\u0002X\"9!Q\u000f\u001cA\u0002\t]\u0014AB:vM\u001aL\u0007\u0010\u0005\u0004\u0003^\t}#q\u000e\u0005\u0007\u0005K2\u0004\u0019\u00015")
public final class Vector5<A>
extends BigVector<A> {
    private final int len1;
    private final Object[][] prefix2;
    private final int len12;
    private final Object[][][] prefix3;
    private final int len123;
    private final Object[][][][] prefix4;
    private final int len1234;
    private final Object[][][][][] data5;
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

    public Object[][][][][] data5() {
        return this.data5;
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

    private Vector5<Nothing$> copy(Object[] prefix1, int len1, Object[][] prefix2, int len12, Object[][][] prefix3, int len123, Object[][][][] prefix4, int len1234, Object[][][][][] data5, Object[][][][] suffix4, Object[][][] suffix3, Object[][] suffix2, Object[] suffix1, int length0) {
        return new Vector5<Nothing$>(prefix1, len1, prefix2, len12, prefix3, len123, prefix4, len1234, data5, suffix4, suffix3, suffix2, suffix1, length0);
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
        return this.data5();
    }

    private Object[][][][] copy$default$10() {
        return this.suffix4();
    }

    private Object[][][] copy$default$11() {
        return this.suffix3();
    }

    private Object[][] copy$default$12() {
        return this.suffix2();
    }

    private Object[] copy$default$13() {
        return this.suffix1();
    }

    private int copy$default$14() {
        return this.length0();
    }

    @Override
    public A apply(int index) {
        if (index >= 0 && index < this.length0()) {
            int io = index - this.len1234();
            if (io >= 0) {
                int i5 = io >>> 20;
                int i4 = io >>> 15 & 0x1F;
                int i3 = io >>> 10 & 0x1F;
                int i2 = io >>> 5 & 0x1F;
                int i1 = io & 0x1F;
                if (i5 < this.data5().length) {
                    return (A)this.data5()[i5][i4][i3][i2][i1];
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
            if (index >= this.len123()) {
                int io2 = index - this.len123();
                return (A)this.prefix4()[io2 >>> 15][io2 >>> 10 & 0x1F][io2 >>> 5 & 0x1F][io2 & 0x1F];
            }
            if (index >= this.len12()) {
                int io3 = index - this.len12();
                return (A)this.prefix3()[io3 >>> 10][io3 >>> 5 & 0x1F][io3 & 0x1F];
            }
            if (index >= this.len1()) {
                int io4 = index - this.len1();
                return (A)this.prefix2()[io4 >>> 5][io4 & 0x1F];
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
            void copy_data5;
            void copy_len1234;
            void copy_prefix4;
            void copy_len123;
            void copy_prefix3;
            void copy_len12;
            void copy_prefix2;
            void copy_len1;
            if (index >= this.len1234()) {
                int io = index - this.len1234();
                int i5 = io >>> 20;
                int i4 = io >>> 15 & 0x1F;
                int i3 = io >>> 10 & 0x1F;
                int i2 = io >>> 5 & 0x1F;
                int i1 = io & 0x1F;
                if (i5 < this.data5().length) {
                    Object[][][][][] copyUpdate_a5 = this.data5();
                    Object[][][][][] copyUpdate_a5c = (Object[][][][][])copyUpdate_a5.clone();
                    Object[][][][] copyUpdate_copyUpdate_a4c = (Object[][][][])copyUpdate_a5c[i5].clone();
                    Object[][][] copyUpdate_copyUpdate_copyUpdate_a3c = (Object[][][])copyUpdate_copyUpdate_a4c[i4].clone();
                    Object[][] copyUpdate_copyUpdate_copyUpdate_copyUpdate_a2c = (Object[][])copyUpdate_copyUpdate_copyUpdate_a3c[i3].clone();
                    Object[] copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_copyUpdate_copyUpdate_copyUpdate_a2c[i2].clone();
                    copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c[i1] = elem;
                    Object var129_14 = null;
                    copyUpdate_copyUpdate_copyUpdate_copyUpdate_a2c[i2] = copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c;
                    Object var128_13 = null;
                    copyUpdate_copyUpdate_copyUpdate_a3c[i3] = copyUpdate_copyUpdate_copyUpdate_copyUpdate_a2c;
                    Object var127_12 = null;
                    copyUpdate_copyUpdate_a4c[i4] = copyUpdate_copyUpdate_copyUpdate_a3c;
                    Object var126_11 = null;
                    copyUpdate_a5c[i5] = copyUpdate_copyUpdate_a4c;
                    Object var124_9 = null;
                    Object var125_10 = null;
                    Object[][][][][] x$1 = copyUpdate_a5c;
                    Object[] x$2 = this.copy$default$1();
                    int x$3 = this.copy$default$2();
                    Object[][] x$4 = this.copy$default$3();
                    int x$5 = this.copy$default$4();
                    Object[][][] x$6 = this.copy$default$5();
                    int x$7 = this.copy$default$6();
                    Object[][][][] x$8 = this.copy$default$7();
                    int x$9 = this.copy$default$8();
                    Object[][][][] x$10 = this.copy$default$10();
                    Object[][][] x$11 = this.copy$default$11();
                    Object[][] x$12 = this.copy$default$12();
                    Object[] x$13 = this.copy$default$13();
                    int x$14 = this.copy$default$14();
                    return new Vector5<A>(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$1, x$10, x$11, x$12, x$13, x$14);
                }
                if (i4 < this.suffix4().length) {
                    Object[][][][] copyUpdate_a4 = this.suffix4();
                    Object[][][][] copyUpdate_a4c = (Object[][][][])copyUpdate_a4.clone();
                    Object[][][] copyUpdate_copyUpdate_a3c = (Object[][][])copyUpdate_a4c[i4].clone();
                    Object[][] copyUpdate_copyUpdate_copyUpdate_a2c = (Object[][])copyUpdate_copyUpdate_a3c[i3].clone();
                    Object[] copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_copyUpdate_copyUpdate_a2c[i2].clone();
                    copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c[i1] = elem;
                    Object var134_33 = null;
                    copyUpdate_copyUpdate_copyUpdate_a2c[i2] = copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c;
                    Object var133_32 = null;
                    copyUpdate_copyUpdate_a3c[i3] = copyUpdate_copyUpdate_copyUpdate_a2c;
                    Object var132_31 = null;
                    copyUpdate_a4c[i4] = copyUpdate_copyUpdate_a3c;
                    Object var130_29 = null;
                    Object var131_30 = null;
                    Object[][][][] x$15 = copyUpdate_a4c;
                    Object[] x$16 = this.copy$default$1();
                    int x$17 = this.copy$default$2();
                    Object[][] x$18 = this.copy$default$3();
                    int x$19 = this.copy$default$4();
                    Object[][][] x$20 = this.copy$default$5();
                    int x$21 = this.copy$default$6();
                    Object[][][][] x$22 = this.copy$default$7();
                    int x$23 = this.copy$default$8();
                    Object[][][][][] x$24 = this.copy$default$9();
                    Object[][][] x$25 = this.copy$default$11();
                    Object[][] x$26 = this.copy$default$12();
                    Object[] x$27 = this.copy$default$13();
                    int x$28 = this.copy$default$14();
                    return new Vector5<A>(x$16, x$17, x$18, x$19, x$20, x$21, x$22, x$23, x$24, x$15, x$25, x$26, x$27, x$28);
                }
                if (i3 < this.suffix3().length) {
                    Object[][][] copyUpdate_a3 = this.suffix3();
                    Object[][][] copyUpdate_a3c = (Object[][][])copyUpdate_a3.clone();
                    Object[][] copyUpdate_copyUpdate_a2c = (Object[][])copyUpdate_a3c[i3].clone();
                    Object[] copyUpdate_copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_copyUpdate_a2c[i2].clone();
                    copyUpdate_copyUpdate_copyUpdate_a1c[i1] = elem;
                    Object var138_51 = null;
                    copyUpdate_copyUpdate_a2c[i2] = copyUpdate_copyUpdate_copyUpdate_a1c;
                    Object var137_50 = null;
                    copyUpdate_a3c[i3] = copyUpdate_copyUpdate_a2c;
                    Object var135_48 = null;
                    Object var136_49 = null;
                    Object[][][] x$29 = copyUpdate_a3c;
                    Object[] x$30 = this.copy$default$1();
                    int x$31 = this.copy$default$2();
                    Object[][] x$32 = this.copy$default$3();
                    int x$33 = this.copy$default$4();
                    Object[][][] x$34 = this.copy$default$5();
                    int x$35 = this.copy$default$6();
                    Object[][][][] x$36 = this.copy$default$7();
                    int x$37 = this.copy$default$8();
                    Object[][][][][] x$38 = this.copy$default$9();
                    Object[][][][] x$39 = this.copy$default$10();
                    Object[][] x$40 = this.copy$default$12();
                    Object[] x$41 = this.copy$default$13();
                    int x$42 = this.copy$default$14();
                    return new Vector5<A>(x$30, x$31, x$32, x$33, x$34, x$35, x$36, x$37, x$38, x$39, x$29, x$40, x$41, x$42);
                }
                if (i2 < this.suffix2().length) {
                    Object[][] copyUpdate_a2 = this.suffix2();
                    Object[][] copyUpdate_a2c = (Object[][])copyUpdate_a2.clone();
                    Object[] copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_a2c[i2].clone();
                    copyUpdate_copyUpdate_a1c[i1] = elem;
                    Object var141_68 = null;
                    copyUpdate_a2c[i2] = copyUpdate_copyUpdate_a1c;
                    Object var139_66 = null;
                    Object var140_67 = null;
                    Object[][] x$43 = copyUpdate_a2c;
                    Object[] x$44 = this.copy$default$1();
                    int x$45 = this.copy$default$2();
                    Object[][] x$46 = this.copy$default$3();
                    int x$47 = this.copy$default$4();
                    Object[][][] x$48 = this.copy$default$5();
                    int x$49 = this.copy$default$6();
                    Object[][][][] x$50 = this.copy$default$7();
                    int x$51 = this.copy$default$8();
                    Object[][][][][] x$52 = this.copy$default$9();
                    Object[][][][] x$53 = this.copy$default$10();
                    Object[][][] x$54 = this.copy$default$11();
                    Object[] x$55 = this.copy$default$13();
                    int x$56 = this.copy$default$14();
                    return new Vector5<A>(x$44, x$45, x$46, x$47, x$48, x$49, x$50, x$51, x$52, x$53, x$54, x$43, x$55, x$56);
                }
                Object[] copyUpdate_a1 = this.suffix1();
                Object[] copyUpdate_a1c = (Object[])copyUpdate_a1.clone();
                copyUpdate_a1c[i1] = elem;
                Object var142_83 = null;
                Object var143_84 = null;
                Object[] x$57 = copyUpdate_a1c;
                Object[] x$58 = this.copy$default$1();
                int x$59 = this.copy$default$2();
                Object[][] x$60 = this.copy$default$3();
                int x$61 = this.copy$default$4();
                Object[][][] x$62 = this.copy$default$5();
                int x$63 = this.copy$default$6();
                Object[][][][] x$64 = this.copy$default$7();
                int x$65 = this.copy$default$8();
                Object[][][][][] x$66 = this.copy$default$9();
                Object[][][][] x$67 = this.copy$default$10();
                Object[][][] x$68 = this.copy$default$11();
                Object[][] x$69 = this.copy$default$12();
                int x$70 = this.copy$default$14();
                return new Vector5<A>(x$58, x$59, x$60, x$61, x$62, x$63, x$64, x$65, x$66, x$67, x$68, x$69, x$57, x$70);
            }
            if (index >= this.len123()) {
                void copyUpdate_idx2;
                void copyUpdate_idx3;
                void copyUpdate_idx4;
                int io = index - this.len123();
                int n = io & 0x1F;
                int n2 = io >>> 5 & 0x1F;
                int n3 = io >>> 10 & 0x1F;
                int n4 = io >>> 15;
                Object[][][][] copyUpdate_a4 = this.prefix4();
                Object[][][][] copyUpdate_a4c = (Object[][][][])copyUpdate_a4.clone();
                Object[][][] copyUpdate_copyUpdate_a3c = (Object[][][])copyUpdate_a4c[copyUpdate_idx4].clone();
                Object[][] copyUpdate_copyUpdate_copyUpdate_a2c = (Object[][])copyUpdate_copyUpdate_a3c[copyUpdate_idx3].clone();
                Object[] copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_copyUpdate_copyUpdate_a2c[copyUpdate_idx2].clone();
                copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c[copyUpdate_idx1] = elem;
                Object var152_108 = null;
                copyUpdate_copyUpdate_copyUpdate_a2c[copyUpdate_idx2] = copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c;
                Object var151_107 = null;
                copyUpdate_copyUpdate_a3c[copyUpdate_idx3] = copyUpdate_copyUpdate_copyUpdate_a2c;
                Object var150_106 = null;
                copyUpdate_a4c[copyUpdate_idx4] = copyUpdate_copyUpdate_a3c;
                Object var144_104 = null;
                Object var149_105 = null;
                Object[][][][] x$71 = copyUpdate_a4c;
                Object[] x$72 = this.copy$default$1();
                int x$73 = this.copy$default$2();
                Object[][] x$74 = this.copy$default$3();
                int x$75 = this.copy$default$4();
                Object[][][] x$76 = this.copy$default$5();
                int x$77 = this.copy$default$6();
                int x$78 = this.copy$default$8();
                Object[][][][][] x$79 = this.copy$default$9();
                Object[][][][] x$80 = this.copy$default$10();
                Object[][][] x$81 = this.copy$default$11();
                Object[][] x$82 = this.copy$default$12();
                Object[] x$83 = this.copy$default$13();
                int x$84 = this.copy$default$14();
                return new Vector5<A>(x$72, x$73, x$74, x$75, x$76, x$77, x$71, x$78, x$79, x$80, x$81, x$82, x$83, x$84);
            }
            if (index >= this.len12()) {
                void copyUpdate_idx2;
                void copyUpdate_idx3;
                int io = index - this.len12();
                int n = io & 0x1F;
                int n5 = io >>> 5 & 0x1F;
                int n6 = io >>> 10;
                Object[][][] copyUpdate_a3 = this.prefix3();
                Object[][][] copyUpdate_a3c = (Object[][][])copyUpdate_a3.clone();
                Object[][] copyUpdate_copyUpdate_a2c = (Object[][])copyUpdate_a3c[copyUpdate_idx3].clone();
                Object[] copyUpdate_copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_copyUpdate_a2c[copyUpdate_idx2].clone();
                copyUpdate_copyUpdate_copyUpdate_a1c[copyUpdate_idx1] = elem;
                Object var159_130 = null;
                copyUpdate_copyUpdate_a2c[copyUpdate_idx2] = copyUpdate_copyUpdate_copyUpdate_a1c;
                Object var158_129 = null;
                copyUpdate_a3c[copyUpdate_idx3] = copyUpdate_copyUpdate_a2c;
                Object var153_127 = null;
                Object var157_128 = null;
                Object[][][] x$85 = copyUpdate_a3c;
                Object[] x$86 = this.copy$default$1();
                int x$87 = this.copy$default$2();
                Object[][] x$88 = this.copy$default$3();
                int x$89 = this.copy$default$4();
                int x$90 = this.copy$default$6();
                Object[][][][] x$91 = this.copy$default$7();
                int x$92 = this.copy$default$8();
                Object[][][][][] x$93 = this.copy$default$9();
                Object[][][][] x$94 = this.copy$default$10();
                Object[][][] x$95 = this.copy$default$11();
                Object[][] x$96 = this.copy$default$12();
                Object[] x$97 = this.copy$default$13();
                int x$98 = this.copy$default$14();
                return new Vector5<A>(x$86, x$87, x$88, x$89, x$85, x$90, x$91, x$92, x$93, x$94, x$95, x$96, x$97, x$98);
            }
            if (index >= this.len1()) {
                void copyUpdate_idx2;
                int io = index - this.len1();
                int n = io & 0x1F;
                int n7 = io >>> 5;
                Object[][] copyUpdate_a2 = this.prefix2();
                Object[][] copyUpdate_a2c = (Object[][])copyUpdate_a2.clone();
                Object[] copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_a2c[copyUpdate_idx2].clone();
                copyUpdate_copyUpdate_a1c[copyUpdate_idx1] = elem;
                Object var164_150 = null;
                copyUpdate_a2c[copyUpdate_idx2] = copyUpdate_copyUpdate_a1c;
                Object var160_148 = null;
                Object var163_149 = null;
                Object[][] x$99 = copyUpdate_a2c;
                Object[] x$100 = this.copy$default$1();
                int x$101 = this.copy$default$2();
                int x$102 = this.copy$default$4();
                Object[][][] x$103 = this.copy$default$5();
                int x$104 = this.copy$default$6();
                Object[][][][] x$105 = this.copy$default$7();
                int x$106 = this.copy$default$8();
                Object[][][][][] x$107 = this.copy$default$9();
                Object[][][][] x$108 = this.copy$default$10();
                Object[][][] x$109 = this.copy$default$11();
                Object[][] x$110 = this.copy$default$12();
                Object[] x$111 = this.copy$default$13();
                int x$112 = this.copy$default$14();
                return new Vector5<A>(x$100, x$101, x$99, x$102, x$103, x$104, x$105, x$106, x$107, x$108, x$109, x$110, x$111, x$112);
            }
            Object[] copyUpdate_a1c = (Object[])this.prefix1().clone();
            copyUpdate_a1c[index] = elem;
            Object var165_165 = null;
            int n = this.copy$default$14();
            Object[] objectArray = this.copy$default$13();
            Object[][] objectArray2 = this.copy$default$12();
            Object[][][] objectArray3 = this.copy$default$11();
            Object[][][][] objectArray4 = this.copy$default$10();
            Object[][][][][] objectArray5 = this.copy$default$9();
            int n8 = this.copy$default$8();
            Object[][][][] objectArray6 = this.copy$default$7();
            int n9 = this.copy$default$6();
            Object[][][] objectArray7 = this.copy$default$5();
            int n10 = this.copy$default$4();
            Object[][] objectArray8 = this.copy$default$3();
            int n11 = this.copy$default$2();
            Object[] copy_prefix1 = copyUpdate_a1c;
            return new Vector5<A>(copy_prefix1, (int)copy_len1, (Object[][])copy_prefix2, (int)copy_len12, (Object[][][])copy_prefix3, (int)copy_len123, (Object[][][][])copy_prefix4, (int)copy_len1234, (Object[][][][][])copy_data5, (Object[][][][])copy_suffix4, (Object[][][])copy_suffix3, (Object[][])copy_suffix2, (Object[])copy_suffix1, (int)copy_length0);
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
            Object[][][][][] x$11 = this.data5();
            Object[][][][] x$12 = this.suffix4();
            Object[][][] x$13 = this.suffix3();
            Object[][] x$14 = this.suffix2();
            return new Vector5<A>(x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$14, x$1, x$2);
        }
        if (this.suffix2().length < 31) {
            Object[][] x$15 = VectorStatics$.MODULE$.copyAppend((T[])this.suffix2(), this.suffix1());
            Object[] wrap1_a = new Object[]{elem};
            Object var72_17 = null;
            Object[] x$16 = wrap1_a;
            int x$17 = this.length0() + 1;
            Object[] x$18 = this.prefix1();
            int x$19 = this.len1();
            Object[][] x$20 = this.prefix2();
            int x$21 = this.len12();
            Object[][][] x$22 = this.prefix3();
            int x$23 = this.len123();
            Object[][][][] x$24 = this.prefix4();
            int x$25 = this.len1234();
            Object[][][][][] x$26 = this.data5();
            Object[][][][] x$27 = this.suffix4();
            Object[][][] x$28 = this.suffix3();
            return new Vector5<A>(x$18, x$19, x$20, x$21, x$22, x$23, x$24, x$25, x$26, x$27, x$28, x$15, x$16, x$17);
        }
        if (this.suffix3().length < 31) {
            Object[][][] x$29 = VectorStatics$.MODULE$.copyAppend((T[])this.suffix3(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix2(), this.suffix1()));
            Object[][] x$30 = VectorStatics$.MODULE$.empty2();
            Object[] wrap1_a = new Object[]{elem};
            Object var73_33 = null;
            Object[] x$31 = wrap1_a;
            int x$32 = this.length0() + 1;
            Object[] x$33 = this.prefix1();
            int x$34 = this.len1();
            Object[][] x$35 = this.prefix2();
            int x$36 = this.len12();
            Object[][][] x$37 = this.prefix3();
            int x$38 = this.len123();
            Object[][][][] x$39 = this.prefix4();
            int x$40 = this.len1234();
            Object[][][][][] x$41 = this.data5();
            Object[][][][] x$42 = this.suffix4();
            return new Vector5<A>(x$33, x$34, x$35, x$36, x$37, x$38, x$39, x$40, x$41, x$42, x$29, x$30, x$31, x$32);
        }
        if (this.suffix4().length < 31) {
            Object[][][][] x$43 = VectorStatics$.MODULE$.copyAppend((T[])this.suffix4(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix3(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix2(), this.suffix1())));
            Object[][][] x$44 = VectorStatics$.MODULE$.empty3();
            Object[][] x$45 = VectorStatics$.MODULE$.empty2();
            Object[] wrap1_a = new Object[]{elem};
            Object var74_49 = null;
            Object[] x$46 = wrap1_a;
            int x$47 = this.length0() + 1;
            Object[] x$48 = this.prefix1();
            int x$49 = this.len1();
            Object[][] x$50 = this.prefix2();
            int x$51 = this.len12();
            Object[][][] x$52 = this.copy$default$5();
            int x$53 = this.copy$default$6();
            Object[][][][] x$54 = this.copy$default$7();
            int x$55 = this.copy$default$8();
            Object[][][][][] x$56 = this.copy$default$9();
            return new Vector5<A>(x$48, x$49, x$50, x$51, x$52, x$53, x$54, x$55, x$56, x$43, x$44, x$45, x$46, x$47);
        }
        if (this.data5().length < 30) {
            Object[][][][][] x$57 = VectorStatics$.MODULE$.copyAppend((T[])this.data5(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix4(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix3(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix2(), this.suffix1()))));
            Object[][][][] x$58 = VectorStatics$.MODULE$.empty4();
            Object[][][] x$59 = VectorStatics$.MODULE$.empty3();
            Object[][] x$60 = VectorStatics$.MODULE$.empty2();
            Object[] wrap1_a = new Object[]{elem};
            Object var75_65 = null;
            Object[] x$61 = wrap1_a;
            int x$62 = this.length0() + 1;
            Object[] x$63 = this.copy$default$1();
            int x$64 = this.copy$default$2();
            Object[][] x$65 = this.copy$default$3();
            int x$66 = this.copy$default$4();
            Object[][][] x$67 = this.copy$default$5();
            int x$68 = this.copy$default$6();
            Object[][][][] x$69 = this.copy$default$7();
            int x$70 = this.copy$default$8();
            return new Vector5<A>(x$63, x$64, x$65, x$66, x$67, x$68, x$69, x$70, x$57, x$58, x$59, x$60, x$61, x$62);
        }
        Object[][][][] wrap5_x = VectorStatics$.MODULE$.copyAppend((T[])this.suffix4(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix3(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix2(), this.suffix1())));
        Object[][][][][] wrap5_a = new Object[][][][][]{wrap5_x};
        Object var76_76 = null;
        Object var77_77 = null;
        Object[] wrap1_a = new Object[]{elem};
        Object var78_78 = null;
        return new Vector6(this.prefix1(), this.len1(), this.prefix2(), this.len12(), this.prefix3(), this.len123(), this.prefix4(), this.len1234(), this.data5(), 0x1E00000 + this.len1234(), VectorStatics$.MODULE$.empty6(), wrap5_a, VectorStatics$.MODULE$.empty4(), VectorStatics$.MODULE$.empty3(), VectorStatics$.MODULE$.empty2(), wrap1_a, this.length0() + 1);
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
            int x$6 = this.length0() + 1;
            Object[][] x$7 = this.prefix2();
            Object[][][] x$8 = this.prefix3();
            Object[][][][] x$9 = this.prefix4();
            Object[][][][][] x$10 = this.data5();
            Object[][][][] x$11 = this.suffix4();
            Object[][][] x$12 = this.suffix3();
            Object[][] x$13 = this.suffix2();
            Object[] x$14 = this.suffix1();
            return new Vector5<A>(x$1, x$2, x$7, x$3, x$8, x$4, x$9, x$5, x$10, x$11, x$12, x$13, x$14, x$6);
        }
        if (this.len12() < 1024) {
            Object[] wrap1_a = new Object[]{elem};
            Object var62_16 = null;
            Object[] x$15 = wrap1_a;
            Object[][] x$17 = VectorStatics$.MODULE$.copyPrepend(this.prefix1(), (T[])this.prefix2());
            int x$18 = this.len12() + 1;
            int x$19 = this.len123() + 1;
            int x$20 = this.len1234() + 1;
            int x$21 = this.length0() + 1;
            Object[][][] x$22 = this.prefix3();
            Object[][][][] x$23 = this.prefix4();
            Object[][][][][] x$24 = this.data5();
            Object[][][][] x$25 = this.suffix4();
            Object[][][] x$26 = this.suffix3();
            Object[][] x$27 = this.suffix2();
            Object[] x$28 = this.suffix1();
            int copy_len1 = 1;
            return new Vector5<A>(x$15, copy_len1, x$17, x$18, x$22, x$19, x$23, x$20, x$24, x$25, x$26, x$27, x$28, x$21);
        }
        if (this.len123() < 32768) {
            void copy_len12;
            Object[] wrap1_a = new Object[]{elem};
            Object var64_31 = null;
            Object[] x$29 = wrap1_a;
            Object[][] x$31 = VectorStatics$.MODULE$.empty2();
            Object[][][] x$33 = VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(this.prefix1(), (T[])this.prefix2()), (T[])this.prefix3());
            int x$34 = this.len123() + 1;
            int x$35 = this.len1234() + 1;
            int x$36 = this.length0() + 1;
            Object[][][][] x$37 = this.prefix4();
            Object[][][][][] x$38 = this.data5();
            Object[][][][] x$39 = this.suffix4();
            Object[][][] x$40 = this.suffix3();
            Object[][] x$41 = this.suffix2();
            Object[] x$42 = this.suffix1();
            boolean bl = true;
            int copy_len1 = 1;
            return new Vector5<A>(x$29, copy_len1, x$31, (int)copy_len12, x$33, x$34, x$37, x$35, x$38, x$39, x$40, x$41, x$42, x$36);
        }
        if (this.len1234() < 0x100000) {
            void copy_len123;
            void copy_len12;
            Object[] wrap1_a = new Object[]{elem};
            Object var67_46 = null;
            Object[] x$43 = wrap1_a;
            Object[][] x$45 = VectorStatics$.MODULE$.empty2();
            Object[][][] x$47 = VectorStatics$.MODULE$.empty3();
            Object[][][][] x$49 = VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(this.prefix1(), (T[])this.prefix2()), (T[])this.prefix3()), (T[])this.prefix4());
            int x$50 = this.len1234() + 1;
            int x$51 = this.length0() + 1;
            Object[][][][][] x$52 = this.data5();
            Object[][][][] x$53 = this.suffix4();
            Object[][][] x$54 = this.suffix3();
            Object[][] x$55 = this.suffix2();
            Object[] x$56 = this.suffix1();
            boolean bl = true;
            boolean bl2 = true;
            int copy_len1 = 1;
            return new Vector5<A>(x$43, copy_len1, x$45, (int)copy_len12, x$47, (int)copy_len123, x$49, x$50, x$52, x$53, x$54, x$55, x$56, x$51);
        }
        if (this.data5().length < 30) {
            void copy_len1234;
            void copy_len123;
            void copy_len12;
            Object[] wrap1_a = new Object[]{elem};
            Object var71_61 = null;
            Object[] x$57 = wrap1_a;
            Object[][] x$59 = VectorStatics$.MODULE$.empty2();
            Object[][][] x$61 = VectorStatics$.MODULE$.empty3();
            Object[][][][] x$63 = VectorStatics$.MODULE$.empty4();
            Object[][][][][] x$65 = VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(this.prefix1(), (T[])this.prefix2()), (T[])this.prefix3()), (T[])this.prefix4()), (T[])this.data5());
            int x$66 = this.length0() + 1;
            Object[][][][] x$67 = this.suffix4();
            Object[][][] x$68 = this.suffix3();
            Object[][] x$69 = this.suffix2();
            Object[] x$70 = this.suffix1();
            boolean bl = true;
            boolean bl3 = true;
            boolean bl4 = true;
            int copy_len1 = 1;
            return new Vector5<A>(x$57, copy_len1, x$59, (int)copy_len12, x$61, (int)copy_len123, x$63, (int)copy_len1234, x$65, x$67, x$68, x$69, x$70, x$66);
        }
        Object[] wrap1_a = new Object[]{elem};
        Object var76_76 = null;
        Object[][][][] wrap5_x = VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(this.prefix1(), (T[])this.prefix2()), (T[])this.prefix3()), (T[])this.prefix4());
        Object[][][][][] wrap5_a = new Object[][][][][]{wrap5_x};
        Object var77_77 = null;
        Object var78_78 = null;
        return new Vector6(wrap1_a, 1, VectorStatics$.MODULE$.empty2(), 1, VectorStatics$.MODULE$.empty3(), 1, VectorStatics$.MODULE$.empty4(), 1, wrap5_a, this.len1234() + 1, VectorStatics$.MODULE$.empty6(), this.data5(), this.suffix4(), this.suffix3(), this.suffix2(), this.suffix1(), this.length0() + 1);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> Vector<B> map(Function1<A, B> f) {
        Object var103_97;
        Object[] objectArray;
        Object[][] x$8;
        Object[][][] x$7;
        Object[][][][] x$6;
        Object[][][][][] x$5;
        Object[][][][] x$4;
        Object[][][] x$3;
        Object[][] x$2;
        Object[] x$1;
        block63: {
            Object var96_86;
            Object var95_85;
            Object var97_87;
            Object[] objectArray2;
            Object[][] objectArray3;
            block62: {
                void mapElems_a;
                void mapElems_n;
                Object var85_74;
                Object var84_73;
                Object var86_75;
                Object[] objectArray4;
                Object[][][] objectArray5;
                block60: {
                    void mapElems_a2;
                    void mapElems_n2;
                    Object var74_62;
                    Object var73_61;
                    Object var75_63;
                    Object[] objectArray6;
                    Object[][][][] objectArray7;
                    block58: {
                        void mapElems_a3;
                        void mapElems_n3;
                        Object var63_50;
                        Object var62_49;
                        Object var64_51;
                        Object[] objectArray8;
                        Object[][][][][] objectArray9;
                        block56: {
                            void mapElems_a4;
                            void mapElems_n4;
                            Object var52_38;
                            Object var51_37;
                            Object var53_39;
                            Object[] objectArray10;
                            Object[][][][] objectArray11;
                            block54: {
                                void mapElems_a5;
                                void mapElems_n5;
                                Object var41_26;
                                Object var40_25;
                                Object var42_27;
                                Object[] objectArray12;
                                Object[][][] objectArray13;
                                block52: {
                                    void mapElems_a6;
                                    void mapElems_n6;
                                    Object var30_14;
                                    Object var29_13;
                                    Object var31_15;
                                    Object[] objectArray14;
                                    Object[][] objectArray15;
                                    block50: {
                                        void mapElems_a7;
                                        void mapElems_n7;
                                        Object var20_6;
                                        Object[] objectArray16;
                                        block48: {
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
                                                objectArray16 = mapElems1_mapElems1Rest_ac;
                                                var20_6 = null;
                                                break block48;
                                            }
                                            objectArray16 = mapElems1_a;
                                        }
                                        Object var16_2 = null;
                                        Object var18_4 = null;
                                        Object var19_5 = null;
                                        var20_6 = null;
                                        x$1 = objectArray16;
                                        objectArray15 = this.prefix2();
                                        int n = 2;
                                        VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                                        if (mapElems_n7 == true) {
                                            block49: {
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
                                                    var31_15 = null;
                                                    break block49;
                                                }
                                                objectArray14 = mapElems_a7;
                                            }
                                            var29_13 = null;
                                            var30_14 = null;
                                            var31_15 = null;
                                        } else {
                                            for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a7).length; ++mapElems_i) {
                                                void mapElems_v1 = mapElems_a7[mapElems_i];
                                                T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n7 - true), (T[])mapElems_v1, f);
                                                if (mapElems_v1 == mapElems_v2) continue;
                                                objectArray14 = mapElems_this.mapElemsRest((int)mapElems_n7, (T[])mapElems_a7, f, mapElems_i, mapElems_v2);
                                                break block50;
                                            }
                                            objectArray14 = mapElems_a7;
                                        }
                                    }
                                    Object var22_11 = null;
                                    objectArray15 = null;
                                    Object var26_18 = null;
                                    Object var27_19 = null;
                                    var29_13 = null;
                                    var30_14 = null;
                                    var31_15 = null;
                                    x$2 = (Object[][])objectArray14;
                                    objectArray13 = this.prefix3();
                                    int n = 3;
                                    VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                                    if (mapElems_n6 == true) {
                                        block51: {
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
                                                var42_27 = null;
                                                break block51;
                                            }
                                            objectArray12 = mapElems_a6;
                                        }
                                        var40_25 = null;
                                        var41_26 = null;
                                        var42_27 = null;
                                    } else {
                                        for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a6).length; ++mapElems_i) {
                                            void mapElems_v1 = mapElems_a6[mapElems_i];
                                            T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n6 - true), (T[])mapElems_v1, f);
                                            if (mapElems_v1 == mapElems_v2) continue;
                                            objectArray12 = mapElems_this.mapElemsRest((int)mapElems_n6, (T[])mapElems_a6, f, mapElems_i, mapElems_v2);
                                            break block52;
                                        }
                                        objectArray12 = mapElems_a6;
                                    }
                                }
                                Object var33_23 = null;
                                objectArray13 = null;
                                Object var37_30 = null;
                                Object var38_31 = null;
                                var40_25 = null;
                                var41_26 = null;
                                var42_27 = null;
                                x$3 = (Object[][][])objectArray12;
                                objectArray11 = this.prefix4();
                                int n = 4;
                                VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                                if (mapElems_n5 == true) {
                                    block53: {
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
                                            var53_39 = null;
                                            break block53;
                                        }
                                        objectArray10 = mapElems_a5;
                                    }
                                    var51_37 = null;
                                    var52_38 = null;
                                    var53_39 = null;
                                } else {
                                    for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a5).length; ++mapElems_i) {
                                        void mapElems_v1 = mapElems_a5[mapElems_i];
                                        T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n5 - true), (T[])mapElems_v1, f);
                                        if (mapElems_v1 == mapElems_v2) continue;
                                        objectArray10 = mapElems_this.mapElemsRest((int)mapElems_n5, (T[])mapElems_a5, f, mapElems_i, mapElems_v2);
                                        break block54;
                                    }
                                    objectArray10 = mapElems_a5;
                                }
                            }
                            Object var44_35 = null;
                            objectArray11 = null;
                            Object var48_42 = null;
                            Object var49_43 = null;
                            var51_37 = null;
                            var52_38 = null;
                            var53_39 = null;
                            x$4 = (Object[][][][])objectArray10;
                            objectArray9 = this.data5();
                            int n = 5;
                            VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                            if (mapElems_n4 == true) {
                                block55: {
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
                                        var64_51 = null;
                                        break block55;
                                    }
                                    objectArray8 = mapElems_a4;
                                }
                                var62_49 = null;
                                var63_50 = null;
                                var64_51 = null;
                            } else {
                                for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a4).length; ++mapElems_i) {
                                    void mapElems_v1 = mapElems_a4[mapElems_i];
                                    T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n4 - true), (T[])mapElems_v1, f);
                                    if (mapElems_v1 == mapElems_v2) continue;
                                    objectArray8 = mapElems_this.mapElemsRest((int)mapElems_n4, (T[])mapElems_a4, f, mapElems_i, mapElems_v2);
                                    break block56;
                                }
                                objectArray8 = mapElems_a4;
                            }
                        }
                        Object var55_47 = null;
                        objectArray9 = null;
                        Object var59_54 = null;
                        Object var60_55 = null;
                        var62_49 = null;
                        var63_50 = null;
                        var64_51 = null;
                        x$5 = (Object[][][][][])objectArray8;
                        objectArray7 = this.suffix4();
                        int n = 4;
                        VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                        if (mapElems_n3 == true) {
                            block57: {
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
                                    var75_63 = null;
                                    break block57;
                                }
                                objectArray6 = mapElems_a3;
                            }
                            var73_61 = null;
                            var74_62 = null;
                            var75_63 = null;
                        } else {
                            for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a3).length; ++mapElems_i) {
                                void mapElems_v1 = mapElems_a3[mapElems_i];
                                T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n3 - true), (T[])mapElems_v1, f);
                                if (mapElems_v1 == mapElems_v2) continue;
                                objectArray6 = mapElems_this.mapElemsRest((int)mapElems_n3, (T[])mapElems_a3, f, mapElems_i, mapElems_v2);
                                break block58;
                            }
                            objectArray6 = mapElems_a3;
                        }
                    }
                    Object var66_59 = null;
                    objectArray7 = null;
                    Object var70_66 = null;
                    Object var71_67 = null;
                    var73_61 = null;
                    var74_62 = null;
                    var75_63 = null;
                    x$6 = (Object[][][][])objectArray6;
                    objectArray5 = this.suffix3();
                    int n = 3;
                    VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                    if (mapElems_n2 == true) {
                        block59: {
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
                                var86_75 = null;
                                break block59;
                            }
                            objectArray4 = mapElems_a2;
                        }
                        var84_73 = null;
                        var85_74 = null;
                        var86_75 = null;
                    } else {
                        for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a2).length; ++mapElems_i) {
                            void mapElems_v1 = mapElems_a2[mapElems_i];
                            T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n2 - true), (T[])mapElems_v1, f);
                            if (mapElems_v1 == mapElems_v2) continue;
                            objectArray4 = mapElems_this.mapElemsRest((int)mapElems_n2, (T[])mapElems_a2, f, mapElems_i, mapElems_v2);
                            break block60;
                        }
                        objectArray4 = mapElems_a2;
                    }
                }
                Object var77_71 = null;
                objectArray5 = null;
                Object var81_78 = null;
                Object var82_79 = null;
                var84_73 = null;
                var85_74 = null;
                var86_75 = null;
                x$7 = (Object[][][])objectArray4;
                objectArray3 = this.suffix2();
                int n = 2;
                VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                if (mapElems_n == true) {
                    block61: {
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
                            var97_87 = null;
                            break block61;
                        }
                        objectArray2 = mapElems_a;
                    }
                    var95_85 = null;
                    var96_86 = null;
                    var97_87 = null;
                } else {
                    for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a).length; ++mapElems_i) {
                        void mapElems_v1 = mapElems_a[mapElems_i];
                        T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n - true), (T[])mapElems_v1, f);
                        if (mapElems_v1 == mapElems_v2) continue;
                        objectArray2 = mapElems_this.mapElemsRest((int)mapElems_n, (T[])mapElems_a, f, mapElems_i, mapElems_v2);
                        break block62;
                    }
                    objectArray2 = mapElems_a;
                }
            }
            Object var88_83 = null;
            objectArray3 = null;
            Object var92_90 = null;
            Object var93_91 = null;
            var95_85 = null;
            var96_86 = null;
            var97_87 = null;
            x$8 = (Object[][])objectArray2;
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
                var103_97 = null;
                break block63;
            }
            objectArray = mapElems1_a;
        }
        Object var99_93 = null;
        Object var101_95 = null;
        Object var102_96 = null;
        var103_97 = null;
        Object[] x$9 = objectArray;
        int x$10 = this.copy$default$2();
        int x$11 = this.copy$default$4();
        int x$12 = this.copy$default$6();
        int x$13 = this.copy$default$8();
        int x$14 = this.copy$default$14();
        return new Vector5<A>(x$1, x$10, x$2, x$11, x$3, x$12, x$4, x$13, x$5, x$6, x$7, x$8, x$9, x$14);
    }

    @Override
    public Vector<A> slice0(int lo, int hi) {
        VectorSliceBuilder b = new VectorSliceBuilder(lo, hi);
        b.consider(1, this.prefix1());
        b.consider(2, (T[])this.prefix2());
        b.consider(3, (T[])this.prefix3());
        b.consider(4, (T[])this.prefix4());
        b.consider(5, (T[])this.data5());
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
            Object var15_1 = null;
            Object[] x$1 = Arrays.copyOfRange(copyTail_a, 1, copyTail_a.length);
            int x$2 = this.len1() - 1;
            int x$3 = this.len12() - 1;
            int x$4 = this.len123() - 1;
            int x$5 = this.len1234() - 1;
            int x$6 = this.length0() - 1;
            Object[][] x$7 = this.prefix2();
            Object[][][] x$8 = this.prefix3();
            Object[][][][] x$9 = this.prefix4();
            Object[][][][][] x$10 = this.data5();
            Object[][][][] x$11 = this.suffix4();
            Object[][][] x$12 = this.suffix3();
            Object[][] x$13 = this.suffix2();
            Object[] x$14 = this.suffix1();
            return new Vector5<A>(x$1, x$2, x$7, x$3, x$8, x$4, x$9, x$5, x$10, x$11, x$12, x$13, x$14, x$6);
        }
        return this.slice0(1, this.length0());
    }

    @Override
    public Vector<A> init() {
        if (this.suffix1().length > 1) {
            Object[] copyInit_a = this.suffix1();
            Object var15_1 = null;
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
            Object[][][][][] x$11 = this.data5();
            Object[][][][] x$12 = this.suffix4();
            Object[][][] x$13 = this.suffix3();
            Object[][] x$14 = this.suffix2();
            return new Vector5<A>(x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$14, x$1, x$2);
        }
        return this.slice0(0, this.length0() - 1);
    }

    @Override
    public int vectorSliceCount() {
        return 9;
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
                return this.data5();
            }
            case 5: {
                return this.suffix4();
            }
            case 6: {
                return this.suffix3();
            }
            case 7: {
                return this.suffix2();
            }
            case 8: {
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
                return this.len1234() + this.data5().length * 0x100000;
            }
            case 5: {
                return this.len1234() + this.data5().length * 0x100000 + this.suffix4().length * 32768;
            }
            case 6: {
                return this.len1234() + this.data5().length * 0x100000 + this.suffix4().length * 32768 + this.suffix3().length * 1024;
            }
            case 7: {
                return this.length0() - this.suffix1().length;
            }
            case 8: {
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
        int x$6 = this.length0() + diff;
        Object[][] x$7 = this.prefix2();
        Object[][][] x$8 = this.prefix3();
        Object[][][][] x$9 = this.prefix4();
        Object[][][][][] x$10 = this.data5();
        Object[][][][] x$11 = this.suffix4();
        Object[][][] x$12 = this.suffix3();
        Object[][] x$13 = this.suffix2();
        Object[] x$14 = this.suffix1();
        return new Vector5<A>(objectArray, x$2, x$7, x$3, x$8, x$4, x$9, x$5, x$10, x$11, x$12, x$13, x$14, x$6);
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
            Object[][][][][] x$11 = this.data5();
            Object[][][][] x$12 = this.suffix4();
            Object[][][] x$13 = this.suffix3();
            Object[][] x$14 = this.suffix2();
            return new Vector5<A>(x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$14, suffix1b, x$2);
        }
        return super.appendedAll0(suffix, k);
    }

    public Vector5(Object[] _prefix1, int len1, Object[][] prefix2, int len12, Object[][][] prefix3, int len123, Object[][][][] prefix4, int len1234, Object[][][][][] data5, Object[][][][] suffix4, Object[][][] suffix3, Object[][] suffix2, Object[] _suffix1, int _length0) {
        this.len1 = len1;
        this.prefix2 = prefix2;
        this.len12 = len12;
        this.prefix3 = prefix3;
        this.len123 = len123;
        this.prefix4 = prefix4;
        this.len1234 = len1234;
        this.data5 = data5;
        this.suffix4 = suffix4;
        this.suffix3 = suffix3;
        this.suffix2 = suffix2;
        super(_prefix1, _suffix1, _length0);
    }
}

