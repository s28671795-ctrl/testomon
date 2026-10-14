/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.lang.reflect.Array;
import java.util.Arrays;
import scala.Function1;
import scala.MatchError;
import scala.collection.IterableOnce;
import scala.collection.immutable.BigVector;
import scala.collection.immutable.Vector;
import scala.collection.immutable.Vector5;
import scala.collection.immutable.VectorSliceBuilder;
import scala.collection.immutable.VectorStatics$;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;

@ScalaSignature(bytes="\u0006\u0005\t}b\u0001\u0002\u00180\rYB\u0011\u0002\u0013\u0001\u0003\u0002\u0003\u0006I!S-\t\u0013u\u0003!Q1A\u0005\u0002=r\u0006\u0002\u00032\u0001\u0005\u0003\u0005\u000b\u0011B0\t\u0013\r\u0004!Q1A\u0005\u0002=\"\u0007\u0002\u00035\u0001\u0005\u0003\u0005\u000b\u0011B3\t\u0013%\u0004!Q1A\u0005\u0002=r\u0006\u0002\u00036\u0001\u0005\u0003\u0005\u000b\u0011B0\t\u0013-\u0004!Q1A\u0005\u0002=b\u0007\u0002\u00039\u0001\u0005\u0003\u0005\u000b\u0011B7\t\u0013E\u0004!Q1A\u0005\u0002=r\u0006\u0002\u0003:\u0001\u0005\u0003\u0005\u000b\u0011B0\t\u0013M\u0004!Q1A\u0005\u0002=\"\b\u0002\u0003=\u0001\u0005\u0003\u0005\u000b\u0011B;\t\u0013e\u0004!Q1A\u0005\u0002=b\u0007\u0002\u0003>\u0001\u0005\u0003\u0005\u000b\u0011B7\t\u0013m\u0004!Q1A\u0005\u0002=\"\u0007\u0002\u0003?\u0001\u0005\u0003\u0005\u000b\u0011B3\t\u0013u\u0004!\u0011!Q\u0001\n%s\bbCA\u0001\u0001\t\u0005\t\u0015!\u0003`\u0003\u0007Aq!a\u0002\u0001\t\u0003\tI\u0001\u0003\u0005\u0002$\u0001\u0001K\u0011BA\u0013\u0011%\t9\u0005AI\u0001\n\u0013\tI\u0005C\u0005\u0002`\u0001\t\n\u0011\"\u0003\u0002b!I\u0011Q\r\u0001\u0012\u0002\u0013%\u0011q\r\u0005\n\u0003W\u0002\u0011\u0013!C\u0005\u0003CB\u0011\"!\u001c\u0001#\u0003%I!a\u001c\t\u0013\u0005M\u0004!%A\u0005\n\u0005\u0005\u0004\"CA;\u0001E\u0005I\u0011BA<\u0011%\tY\bAI\u0001\n\u0013\ty\u0007C\u0005\u0002~\u0001\t\n\u0011\"\u0003\u0002h!I\u0011q\u0010\u0001\u0012\u0002\u0013%\u0011\u0011\n\u0005\n\u0003\u0003\u0003\u0011\u0013!C\u0005\u0003CBq!a!\u0001\t\u0003\t)\tC\u0004\u0002\u000e\u0002!\t%a$\t\u000f\u0005\r\u0006\u0001\"\u0011\u0002&\"9\u0011\u0011\u0017\u0001\u0005B\u0005M\u0006bBA`\u0001\u0011\u0005\u0013\u0011\u0019\u0005\t\u0003+\u0004\u0001\u0015\"\u0005\u0002X\"9\u00111\u001d\u0001\u0005B\u0005\u0015\bbBAt\u0001\u0011\u0005\u0013Q\u001d\u0005\b\u0003S\u0004A\u0011C\u0018_\u0011!\tY\u000f\u0001C\t_\u00055\b\u0002\u0003B\u0005\u0001\u0011EqFa\u0003\t\u0011\t=\u0001\u0001)C)\u0005#A\u0001Ba\u000b\u0001A\u0013E#Q\u0006\u0002\b-\u0016\u001cGo\u001c:5\u0015\t\u0001\u0014'A\u0005j[6,H/\u00192mK*\u0011!gM\u0001\u000bG>dG.Z2uS>t'\"\u0001\u001b\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011qGP\n\u0003\u0001a\u00022!\u000f\u001e=\u001b\u0005y\u0013BA\u001e0\u0005%\u0011\u0015n\u001a,fGR|'\u000f\u0005\u0002>}1\u0001AAB \u0001\t\u000b\u0007\u0001IA\u0001B#\t\tU\t\u0005\u0002C\u00076\t1'\u0003\u0002Eg\t9aj\u001c;iS:<\u0007C\u0001\"G\u0013\t95GA\u0002B]f\f\u0001b\u00189sK\u001aL\u00070\r\t\u0003\u0015Zs!a\u0013+\u000f\u00051\u001bfBA'S\u001d\tq\u0015+D\u0001P\u0015\t\u0001V'\u0001\u0004=e>|GOP\u0005\u0002i%\u0011!gM\u0005\u0003aEJ!!V\u0018\u0002\u0019Y+7\r^8s\u0013:d\u0017N\\3\n\u0005]C&\u0001B!seFR!!V\u0018\n\u0005i[\u0016a\u00029sK\u001aL\u00070M\u0005\u00039>\u0012aAV3di>\u0014\u0018\u0001\u00027f]F*\u0012a\u0018\t\u0003\u0005\u0002L!!Y\u001a\u0003\u0007%sG/A\u0003mK:\f\u0004%A\u0004qe\u00164\u0017\u000e\u001f\u001a\u0016\u0003\u0015\u0004\"A\u00134\n\u0005\u001dD&\u0001B!seJ\n\u0001\u0002\u001d:fM&D(\u0007I\u0001\u0006Y\u0016t\u0017GM\u0001\u0007Y\u0016t\u0017G\r\u0011\u0002\u000fA\u0014XMZ5ygU\tQ\u000e\u0005\u0002K]&\u0011q\u000e\u0017\u0002\u0005\u0003J\u00148'\u0001\u0005qe\u00164\u0017\u000e_\u001a!\u0003\u0019aWM\\\u00193g\u00059A.\u001a82eM\u0002\u0013!\u00023bi\u0006$T#A;\u0011\u0005)3\u0018BA<Y\u0005\u0011\t%O\u001d\u001b\u0002\r\u0011\fG/\u0019\u001b!\u0003\u001d\u0019XO\u001a4jqN\n\u0001b];gM&D8\u0007I\u0001\bgV4g-\u001b=3\u0003!\u0019XO\u001a4jqJ\u0002\u0013\u0001C0tk\u001a4\u0017\u000e_\u0019\n\u0005}T\u0014aB:vM\u001aL\u00070M\u0001\t?2,gn\u001a;ia%\u0019\u0011Q\u0001\u001e\u0002\u000f1,gn\u001a;ia\u00051A(\u001b8jiz\"\u0002$a\u0003\u0002\u000e\u0005=\u0011\u0011CA\n\u0003+\t9\"!\u0007\u0002\u001c\u0005u\u0011qDA\u0011!\rI\u0004\u0001\u0010\u0005\u0006\u0011R\u0001\r!\u0013\u0005\u0006;R\u0001\ra\u0018\u0005\u0006GR\u0001\r!\u001a\u0005\u0006SR\u0001\ra\u0018\u0005\u0006WR\u0001\r!\u001c\u0005\u0006cR\u0001\ra\u0018\u0005\u0006gR\u0001\r!\u001e\u0005\u0006sR\u0001\r!\u001c\u0005\u0006wR\u0001\r!\u001a\u0005\u0006{R\u0001\r!\u0013\u0005\u0007\u0003\u0003!\u0002\u0019A0\u0002\t\r|\u0007/\u001f\u000b\u0019\u0003O\tI#a\u000b\u0002.\u0005=\u0012\u0011GA\u001a\u0003k\t9$!\u000f\u0002<\u0005u\u0002cA\u001d\u0001\u0003\"9!,\u0006I\u0001\u0002\u0004I\u0005bB/\u0016!\u0003\u0005\ra\u0018\u0005\bGV\u0001\n\u00111\u0001f\u0011\u001dIW\u0003%AA\u0002}Cqa[\u000b\u0011\u0002\u0003\u0007Q\u000eC\u0004r+A\u0005\t\u0019A0\t\u000fM,\u0002\u0013!a\u0001k\"9\u00110\u0006I\u0001\u0002\u0004i\u0007bB>\u0016!\u0003\u0005\r!\u001a\u0005\b\u007fV\u0001\n\u00111\u0001J\u0011!\t)!\u0006I\u0001\u0002\u0004y\u0006fA\u000b\u0002BA\u0019!)a\u0011\n\u0007\u0005\u00153G\u0001\u0004j]2Lg.Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\t\tYEK\u0002J\u0003\u001bZ#!a\u0014\u0011\t\u0005E\u00131L\u0007\u0003\u0003'RA!!\u0016\u0002X\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u00033\u001a\u0014AC1o]>$\u0018\r^5p]&!\u0011QLA*\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\t\t\u0019GK\u0002`\u0003\u001b\nabY8qs\u0012\"WMZ1vYR$3'\u0006\u0002\u0002j)\u001aQ-!\u0014\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%i\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012*TCAA9U\ri\u0017QJ\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00137\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uI]*\"!!\u001f+\u0007U\fi%\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001d\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%s\u0005y1m\u001c9zI\u0011,g-Y;mi\u0012\n\u0004'A\bd_BLH\u0005Z3gCVdG\u000fJ\u00192\u0003\u0015\t\u0007\u000f\u001d7z)\ra\u0014q\u0011\u0005\u0007\u0003\u0013\u000b\u0003\u0019A0\u0002\u000b%tG-\u001a=)\u0007\u0005\n\t%A\u0004va\u0012\fG/\u001a3\u0016\t\u0005E\u0015q\u0013\u000b\u0007\u0003'\u000bi*a(\u0011\teZ\u0016Q\u0013\t\u0004{\u0005]EaBAME\t\u0007\u00111\u0014\u0002\u0002\u0005F\u0011A(\u0012\u0005\u0007\u0003\u0013\u0013\u0003\u0019A0\t\u000f\u0005\u0005&\u00051\u0001\u0002\u0016\u0006!Q\r\\3n\u0003!\t\u0007\u000f]3oI\u0016$W\u0003BAT\u0003[#B!!+\u00020B!\u0011hWAV!\ri\u0014Q\u0016\u0003\b\u00033\u001b#\u0019AAN\u0011\u001d\t\tk\ta\u0001\u0003W\u000b\u0011\u0002\u001d:fa\u0016tG-\u001a3\u0016\t\u0005U\u00161\u0018\u000b\u0005\u0003o\u000bi\f\u0005\u0003:7\u0006e\u0006cA\u001f\u0002<\u00129\u0011\u0011\u0014\u0013C\u0002\u0005m\u0005bBAQI\u0001\u0007\u0011\u0011X\u0001\u0004[\u0006\u0004X\u0003BAb\u0003\u0013$B!!2\u0002LB!\u0011hWAd!\ri\u0014\u0011\u001a\u0003\u0007\u00033+#\u0019\u0001!\t\u000f\u00055W\u00051\u0001\u0002P\u0006\ta\r\u0005\u0004C\u0003#d\u0014qY\u0005\u0004\u0003'\u001c$!\u0003$v]\u000e$\u0018n\u001c82\u0003\u0019\u0019H.[2faQ1\u0011\u0011\\An\u0003?\u00042!O.=\u0011\u0019\tiN\na\u0001?\u0006\u0011An\u001c\u0005\u0007\u0003C4\u0003\u0019A0\u0002\u0005!L\u0017\u0001\u0002;bS2,\"!!7\u0002\t%t\u0017\u000e^\u0001\u0011m\u0016\u001cGo\u001c:TY&\u001cWmQ8v]R\f1B^3di>\u00148\u000b\\5dKR!\u0011q\u001eB\u0003a\u0011\t\t0!?\u0011\u000b\t\u000b\u00190a>\n\u0007\u0005U8GA\u0003BeJ\f\u0017\u0010E\u0002>\u0003s$1\"a?+\u0003\u0003\u0005\tQ!\u0001\u0002~\n!q\fJ\u00196#\r\t\u0015q \t\u0004\u0005\n\u0005\u0011b\u0001B\u0002g\t1\u0011I\\=SK\u001aDaAa\u0002+\u0001\u0004y\u0016aA5eq\u00069b/Z2u_J\u001cF.[2f!J,g-\u001b=MK:<G\u000f\u001b\u000b\u0004?\n5\u0001B\u0002B\u0004W\u0001\u0007q,A\u0007qe\u0016\u0004XM\u001c3fI\u0006cG\u000eM\u000b\u0005\u0005'\u0011I\u0002\u0006\u0004\u0003\u0016\tm!q\u0005\t\u0005sm\u00139\u0002E\u0002>\u00053!q!!'-\u0005\u0004\tY\nC\u0004\u0003\u001e1\u0002\rAa\b\u0002\rA\u0014XMZ5y!\u0019\u0011\tCa\t\u0003\u00185\t\u0011'C\u0002\u0003&E\u0012A\"\u0013;fe\u0006\u0014G.Z(oG\u0016DaA!\u000b-\u0001\u0004y\u0016!A6\u0002\u0019\u0005\u0004\b/\u001a8eK\u0012\fE\u000e\u001c\u0019\u0016\t\t=\"Q\u0007\u000b\u0007\u0005c\u00119D!\u0010\u0011\teZ&1\u0007\t\u0004{\tUBaBAM[\t\u0007\u00111\u0014\u0005\b\u0005si\u0003\u0019\u0001B\u001e\u0003\u0019\u0019XO\u001a4jqB1!\u0011\u0005B\u0012\u0005gAaA!\u000b.\u0001\u0004y\u0006")
public final class Vector4<A>
extends BigVector<A> {
    private final int len1;
    private final Object[][] prefix2;
    private final int len12;
    private final Object[][][] prefix3;
    private final int len123;
    private final Object[][][][] data4;
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

    public Object[][][][] data4() {
        return this.data4;
    }

    public Object[][][] suffix3() {
        return this.suffix3;
    }

    public Object[][] suffix2() {
        return this.suffix2;
    }

    private Vector4<Nothing$> copy(Object[] prefix1, int len1, Object[][] prefix2, int len12, Object[][][] prefix3, int len123, Object[][][][] data4, Object[][][] suffix3, Object[][] suffix2, Object[] suffix1, int length0) {
        return new Vector4<Nothing$>(prefix1, len1, prefix2, len12, prefix3, len123, data4, suffix3, suffix2, suffix1, length0);
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
        return this.data4();
    }

    private Object[][][] copy$default$8() {
        return this.suffix3();
    }

    private Object[][] copy$default$9() {
        return this.suffix2();
    }

    private Object[] copy$default$10() {
        return this.suffix1();
    }

    private int copy$default$11() {
        return this.length0();
    }

    @Override
    public A apply(int index) {
        if (index >= 0 && index < this.length0()) {
            int io = index - this.len123();
            if (io >= 0) {
                int i4 = io >>> 15;
                int i3 = io >>> 10 & 0x1F;
                int i2 = io >>> 5 & 0x1F;
                int i1 = io & 0x1F;
                if (i4 < this.data4().length) {
                    return (A)this.data4()[i4][i3][i2][i1];
                }
                if (i3 < this.suffix3().length) {
                    return (A)this.suffix3()[i3][i2][i1];
                }
                if (i2 < this.suffix2().length) {
                    return (A)this.suffix2()[i2][i1];
                }
                return (A)this.suffix1()[i1];
            }
            if (index >= this.len12()) {
                int io2 = index - this.len12();
                return (A)this.prefix3()[io2 >>> 10][io2 >>> 5 & 0x1F][io2 & 0x1F];
            }
            if (index >= this.len1()) {
                int io3 = index - this.len1();
                return (A)this.prefix2()[io3 >>> 5][io3 & 0x1F];
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
            void copy_data4;
            void copy_len123;
            void copy_prefix3;
            void copy_len12;
            void copy_prefix2;
            void copy_len1;
            if (index >= this.len123()) {
                int io = index - this.len123();
                int i4 = io >>> 15;
                int i3 = io >>> 10 & 0x1F;
                int i2 = io >>> 5 & 0x1F;
                int i1 = io & 0x1F;
                if (i4 < this.data4().length) {
                    Object[][][][] copyUpdate_a4 = this.data4();
                    Object[][][][] copyUpdate_a4c = (Object[][][][])copyUpdate_a4.clone();
                    Object[][][] copyUpdate_copyUpdate_a3c = (Object[][][])copyUpdate_a4c[i4].clone();
                    Object[][] copyUpdate_copyUpdate_copyUpdate_a2c = (Object[][])copyUpdate_copyUpdate_a3c[i3].clone();
                    Object[] copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_copyUpdate_copyUpdate_a2c[i2].clone();
                    copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c[i1] = elem;
                    Object var80_12 = null;
                    copyUpdate_copyUpdate_copyUpdate_a2c[i2] = copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c;
                    Object var79_11 = null;
                    copyUpdate_copyUpdate_a3c[i3] = copyUpdate_copyUpdate_copyUpdate_a2c;
                    Object var78_10 = null;
                    copyUpdate_a4c[i4] = copyUpdate_copyUpdate_a3c;
                    Object var76_8 = null;
                    Object var77_9 = null;
                    Object[][][][] x$1 = copyUpdate_a4c;
                    Object[] x$2 = this.copy$default$1();
                    int x$3 = this.copy$default$2();
                    Object[][] x$4 = this.copy$default$3();
                    int x$5 = this.copy$default$4();
                    Object[][][] x$6 = this.copy$default$5();
                    int x$7 = this.copy$default$6();
                    Object[][][] x$8 = this.copy$default$8();
                    Object[][] x$9 = this.copy$default$9();
                    Object[] x$10 = this.copy$default$10();
                    int x$11 = this.copy$default$11();
                    return new Vector4<A>(x$2, x$3, x$4, x$5, x$6, x$7, x$1, x$8, x$9, x$10, x$11);
                }
                if (i3 < this.suffix3().length) {
                    Object[][][] copyUpdate_a3 = this.suffix3();
                    Object[][][] copyUpdate_a3c = (Object[][][])copyUpdate_a3.clone();
                    Object[][] copyUpdate_copyUpdate_a2c = (Object[][])copyUpdate_a3c[i3].clone();
                    Object[] copyUpdate_copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_copyUpdate_a2c[i2].clone();
                    copyUpdate_copyUpdate_copyUpdate_a1c[i1] = elem;
                    Object var84_27 = null;
                    copyUpdate_copyUpdate_a2c[i2] = copyUpdate_copyUpdate_copyUpdate_a1c;
                    Object var83_26 = null;
                    copyUpdate_a3c[i3] = copyUpdate_copyUpdate_a2c;
                    Object var81_24 = null;
                    Object var82_25 = null;
                    Object[][][] x$12 = copyUpdate_a3c;
                    Object[] x$13 = this.copy$default$1();
                    int x$14 = this.copy$default$2();
                    Object[][] x$15 = this.copy$default$3();
                    int x$16 = this.copy$default$4();
                    Object[][][] x$17 = this.copy$default$5();
                    int x$18 = this.copy$default$6();
                    Object[][][][] x$19 = this.copy$default$7();
                    Object[][] x$20 = this.copy$default$9();
                    Object[] x$21 = this.copy$default$10();
                    int x$22 = this.copy$default$11();
                    return new Vector4<A>(x$13, x$14, x$15, x$16, x$17, x$18, x$19, x$12, x$20, x$21, x$22);
                }
                if (i2 < this.suffix2().length) {
                    Object[][] copyUpdate_a2 = this.suffix2();
                    Object[][] copyUpdate_a2c = (Object[][])copyUpdate_a2.clone();
                    Object[] copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_a2c[i2].clone();
                    copyUpdate_copyUpdate_a1c[i1] = elem;
                    Object var87_41 = null;
                    copyUpdate_a2c[i2] = copyUpdate_copyUpdate_a1c;
                    Object var85_39 = null;
                    Object var86_40 = null;
                    Object[][] x$23 = copyUpdate_a2c;
                    Object[] x$24 = this.copy$default$1();
                    int x$25 = this.copy$default$2();
                    Object[][] x$26 = this.copy$default$3();
                    int x$27 = this.copy$default$4();
                    Object[][][] x$28 = this.copy$default$5();
                    int x$29 = this.copy$default$6();
                    Object[][][][] x$30 = this.copy$default$7();
                    Object[][][] x$31 = this.copy$default$8();
                    Object[] x$32 = this.copy$default$10();
                    int x$33 = this.copy$default$11();
                    return new Vector4<A>(x$24, x$25, x$26, x$27, x$28, x$29, x$30, x$31, x$23, x$32, x$33);
                }
                Object[] copyUpdate_a1 = this.suffix1();
                Object[] copyUpdate_a1c = (Object[])copyUpdate_a1.clone();
                copyUpdate_a1c[i1] = elem;
                Object var88_53 = null;
                Object var89_54 = null;
                Object[] x$34 = copyUpdate_a1c;
                Object[] x$35 = this.copy$default$1();
                int x$36 = this.copy$default$2();
                Object[][] x$37 = this.copy$default$3();
                int x$38 = this.copy$default$4();
                Object[][][] x$39 = this.copy$default$5();
                int x$40 = this.copy$default$6();
                Object[][][][] x$41 = this.copy$default$7();
                Object[][][] x$42 = this.copy$default$8();
                Object[][] x$43 = this.copy$default$9();
                int x$44 = this.copy$default$11();
                return new Vector4<A>(x$35, x$36, x$37, x$38, x$39, x$40, x$41, x$42, x$43, x$34, x$44);
            }
            if (index >= this.len12()) {
                void copyUpdate_idx2;
                void copyUpdate_idx3;
                int io = index - this.len12();
                int n = io & 0x1F;
                int n2 = io >>> 5 & 0x1F;
                int n3 = io >>> 10;
                Object[][][] copyUpdate_a3 = this.prefix3();
                Object[][][] copyUpdate_a3c = (Object[][][])copyUpdate_a3.clone();
                Object[][] copyUpdate_copyUpdate_a2c = (Object[][])copyUpdate_a3c[copyUpdate_idx3].clone();
                Object[] copyUpdate_copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_copyUpdate_a2c[copyUpdate_idx2].clone();
                copyUpdate_copyUpdate_copyUpdate_a1c[copyUpdate_idx1] = elem;
                Object var96_73 = null;
                copyUpdate_copyUpdate_a2c[copyUpdate_idx2] = copyUpdate_copyUpdate_copyUpdate_a1c;
                Object var95_72 = null;
                copyUpdate_a3c[copyUpdate_idx3] = copyUpdate_copyUpdate_a2c;
                Object var90_70 = null;
                Object var94_71 = null;
                Object[][][] x$45 = copyUpdate_a3c;
                Object[] x$46 = this.copy$default$1();
                int x$47 = this.copy$default$2();
                Object[][] x$48 = this.copy$default$3();
                int x$49 = this.copy$default$4();
                int x$50 = this.copy$default$6();
                Object[][][][] x$51 = this.copy$default$7();
                Object[][][] x$52 = this.copy$default$8();
                Object[][] x$53 = this.copy$default$9();
                Object[] x$54 = this.copy$default$10();
                int x$55 = this.copy$default$11();
                return new Vector4<A>(x$46, x$47, x$48, x$49, x$45, x$50, x$51, x$52, x$53, x$54, x$55);
            }
            if (index >= this.len1()) {
                void copyUpdate_idx2;
                int io = index - this.len1();
                int n = io & 0x1F;
                int n4 = io >>> 5;
                Object[][] copyUpdate_a2 = this.prefix2();
                Object[][] copyUpdate_a2c = (Object[][])copyUpdate_a2.clone();
                Object[] copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_a2c[copyUpdate_idx2].clone();
                copyUpdate_copyUpdate_a1c[copyUpdate_idx1] = elem;
                Object var101_90 = null;
                copyUpdate_a2c[copyUpdate_idx2] = copyUpdate_copyUpdate_a1c;
                Object var97_88 = null;
                Object var100_89 = null;
                Object[][] x$56 = copyUpdate_a2c;
                Object[] x$57 = this.copy$default$1();
                int x$58 = this.copy$default$2();
                int x$59 = this.copy$default$4();
                Object[][][] x$60 = this.copy$default$5();
                int x$61 = this.copy$default$6();
                Object[][][][] x$62 = this.copy$default$7();
                Object[][][] x$63 = this.copy$default$8();
                Object[][] x$64 = this.copy$default$9();
                Object[] x$65 = this.copy$default$10();
                int x$66 = this.copy$default$11();
                return new Vector4<A>(x$57, x$58, x$56, x$59, x$60, x$61, x$62, x$63, x$64, x$65, x$66);
            }
            Object[] copyUpdate_a1c = (Object[])this.prefix1().clone();
            copyUpdate_a1c[index] = elem;
            Object var102_102 = null;
            int n = this.copy$default$11();
            Object[] objectArray = this.copy$default$10();
            Object[][] objectArray2 = this.copy$default$9();
            Object[][][] objectArray3 = this.copy$default$8();
            Object[][][][] objectArray4 = this.copy$default$7();
            int n5 = this.copy$default$6();
            Object[][][] objectArray5 = this.copy$default$5();
            int n6 = this.copy$default$4();
            Object[][] objectArray6 = this.copy$default$3();
            int n7 = this.copy$default$2();
            Object[] copy_prefix1 = copyUpdate_a1c;
            return new Vector4<A>(copy_prefix1, (int)copy_len1, (Object[][])copy_prefix2, (int)copy_len12, (Object[][][])copy_prefix3, (int)copy_len123, (Object[][][][])copy_data4, (Object[][][])copy_suffix3, (Object[][])copy_suffix2, (Object[])copy_suffix1, (int)copy_length0);
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
            Object[][][][] x$9 = this.data4();
            Object[][][] x$10 = this.suffix3();
            Object[][] x$11 = this.suffix2();
            return new Vector4<A>(x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$1, x$2);
        }
        if (this.suffix2().length < 31) {
            Object[][] x$12 = VectorStatics$.MODULE$.copyAppend((T[])this.suffix2(), this.suffix1());
            Object[] wrap1_a = new Object[]{elem};
            Object var46_14 = null;
            Object[] x$13 = wrap1_a;
            int x$14 = this.length0() + 1;
            Object[] x$15 = this.prefix1();
            int x$16 = this.len1();
            Object[][] x$17 = this.prefix2();
            int x$18 = this.len12();
            Object[][][] x$19 = this.prefix3();
            int x$20 = this.len123();
            Object[][][][] x$21 = this.data4();
            Object[][][] x$22 = this.suffix3();
            return new Vector4<A>(x$15, x$16, x$17, x$18, x$19, x$20, x$21, x$22, x$12, x$13, x$14);
        }
        if (this.suffix3().length < 31) {
            Object[][][] x$23 = VectorStatics$.MODULE$.copyAppend((T[])this.suffix3(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix2(), this.suffix1()));
            Object[][] x$24 = VectorStatics$.MODULE$.empty2();
            Object[] wrap1_a = new Object[]{elem};
            Object var47_27 = null;
            Object[] x$25 = wrap1_a;
            int x$26 = this.length0() + 1;
            Object[] x$27 = this.prefix1();
            int x$28 = this.len1();
            Object[][] x$29 = this.prefix2();
            int x$30 = this.len12();
            Object[][][] x$31 = this.prefix3();
            int x$32 = this.len123();
            Object[][][][] x$33 = this.data4();
            return new Vector4<A>(x$27, x$28, x$29, x$30, x$31, x$32, x$33, x$23, x$24, x$25, x$26);
        }
        if (this.data4().length < 30) {
            Object[][][][] x$34 = VectorStatics$.MODULE$.copyAppend((T[])this.data4(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix3(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix2(), this.suffix1())));
            Object[][][] x$35 = VectorStatics$.MODULE$.empty3();
            Object[][] x$36 = VectorStatics$.MODULE$.empty2();
            Object[] wrap1_a = new Object[]{elem};
            Object var48_40 = null;
            Object[] x$37 = wrap1_a;
            int x$38 = this.length0() + 1;
            Object[] x$39 = this.prefix1();
            int x$40 = this.len1();
            Object[][] x$41 = this.prefix2();
            int x$42 = this.len12();
            Object[][][] x$43 = this.prefix3();
            int x$44 = this.len123();
            return new Vector4<A>(x$39, x$40, x$41, x$42, x$43, x$44, x$34, x$35, x$36, x$37, x$38);
        }
        Object[][][] wrap4_x = VectorStatics$.MODULE$.copyAppend((T[])this.suffix3(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix2(), this.suffix1()));
        Object[][][][] wrap4_a = new Object[][][][]{wrap4_x};
        Object var49_49 = null;
        Object var50_50 = null;
        Object[] wrap1_a = new Object[]{elem};
        Object var51_51 = null;
        return new Vector5(this.prefix1(), this.len1(), this.prefix2(), this.len12(), this.prefix3(), this.len123(), this.data4(), 983040 + this.len123(), VectorStatics$.MODULE$.empty5(), wrap4_a, VectorStatics$.MODULE$.empty3(), VectorStatics$.MODULE$.empty2(), wrap1_a, this.length0() + 1);
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
            int x$5 = this.length0() + 1;
            Object[][] x$6 = this.prefix2();
            Object[][][] x$7 = this.prefix3();
            Object[][][][] x$8 = this.data4();
            Object[][][] x$9 = this.suffix3();
            Object[][] x$10 = this.suffix2();
            Object[] x$11 = this.suffix1();
            return new Vector4<A>(x$1, x$2, x$6, x$3, x$7, x$4, x$8, x$9, x$10, x$11, x$5);
        }
        if (this.len12() < 1024) {
            Object[] wrap1_a = new Object[]{elem};
            Object var40_13 = null;
            Object[] x$12 = wrap1_a;
            Object[][] x$14 = VectorStatics$.MODULE$.copyPrepend(this.prefix1(), (T[])this.prefix2());
            int x$15 = this.len12() + 1;
            int x$16 = this.len123() + 1;
            int x$17 = this.length0() + 1;
            Object[][][] x$18 = this.prefix3();
            Object[][][][] x$19 = this.data4();
            Object[][][] x$20 = this.suffix3();
            Object[][] x$21 = this.suffix2();
            Object[] x$22 = this.suffix1();
            int copy_len1 = 1;
            return new Vector4<A>(x$12, copy_len1, x$14, x$15, x$18, x$16, x$19, x$20, x$21, x$22, x$17);
        }
        if (this.len123() < 32768) {
            void copy_len12;
            Object[] wrap1_a = new Object[]{elem};
            Object var42_25 = null;
            Object[] x$23 = wrap1_a;
            Object[][] x$25 = VectorStatics$.MODULE$.empty2();
            Object[][][] x$27 = VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(this.prefix1(), (T[])this.prefix2()), (T[])this.prefix3());
            int x$28 = this.len123() + 1;
            int x$29 = this.length0() + 1;
            Object[][][][] x$30 = this.data4();
            Object[][][] x$31 = this.suffix3();
            Object[][] x$32 = this.suffix2();
            Object[] x$33 = this.suffix1();
            boolean bl = true;
            int copy_len1 = 1;
            return new Vector4<A>(x$23, copy_len1, x$25, (int)copy_len12, x$27, x$28, x$30, x$31, x$32, x$33, x$29);
        }
        if (this.data4().length < 30) {
            void copy_len123;
            void copy_len12;
            Object[] wrap1_a = new Object[]{elem};
            Object var45_37 = null;
            Object[] x$34 = wrap1_a;
            Object[][] x$36 = VectorStatics$.MODULE$.empty2();
            Object[][][] x$38 = VectorStatics$.MODULE$.empty3();
            Object[][][][] x$40 = VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(this.prefix1(), (T[])this.prefix2()), (T[])this.prefix3()), (T[])this.data4());
            int x$41 = this.length0() + 1;
            Object[][][] x$42 = this.suffix3();
            Object[][] x$43 = this.suffix2();
            Object[] x$44 = this.suffix1();
            boolean bl = true;
            boolean bl2 = true;
            int copy_len1 = 1;
            return new Vector4<A>(x$34, copy_len1, x$36, (int)copy_len12, x$38, (int)copy_len123, x$40, x$42, x$43, x$44, x$41);
        }
        Object[] wrap1_a = new Object[]{elem};
        Object var49_49 = null;
        Object[][][] wrap4_x = VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(this.prefix1(), (T[])this.prefix2()), (T[])this.prefix3());
        Object[][][][] wrap4_a = new Object[][][][]{wrap4_x};
        Object var50_50 = null;
        Object var51_51 = null;
        return new Vector5(wrap1_a, 1, VectorStatics$.MODULE$.empty2(), 1, VectorStatics$.MODULE$.empty3(), 1, wrap4_a, this.len123() + 1, VectorStatics$.MODULE$.empty5(), this.data4(), this.suffix3(), this.suffix2(), this.suffix1(), this.length0() + 1);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> Vector<B> map(Function1<A, B> f) {
        Object var78_85;
        Object[] objectArray;
        Object[][] x$6;
        Object[][][] x$5;
        Object[][][][] x$4;
        Object[][][] x$3;
        Object[][] x$2;
        Object[] x$1;
        block57: {
            Object var71_74;
            Object var70_73;
            Object var72_75;
            Object[] objectArray2;
            Object[][] objectArray3;
            block56: {
                void mapElems_a;
                void mapElems_n;
                Object var60_62;
                Object var59_61;
                Object var61_63;
                Object[] objectArray4;
                Object[][][] objectArray5;
                block54: {
                    void mapElems_a2;
                    void mapElems_n2;
                    Object var49_50;
                    Object var48_49;
                    Object var50_51;
                    Object[] objectArray6;
                    Object[][][][] objectArray7;
                    block52: {
                        void mapElems_a3;
                        void mapElems_n3;
                        Object var38_38;
                        Object var37_37;
                        Object var39_39;
                        Object[] objectArray8;
                        Object[][][] objectArray9;
                        block50: {
                            void mapElems_a4;
                            void mapElems_n4;
                            Object var27_14;
                            Object var26_13;
                            Object var28_15;
                            Object[] objectArray10;
                            Object[][] objectArray11;
                            block48: {
                                void mapElems_a5;
                                void mapElems_n5;
                                Object var17_6;
                                Object[] objectArray12;
                                block44: {
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
                                        objectArray12 = mapElems1_mapElems1Rest_ac;
                                        var17_6 = null;
                                        break block44;
                                    }
                                    objectArray12 = mapElems1_a;
                                }
                                Object var13_2 = null;
                                Object var15_5 = null;
                                Object var16_4 = null;
                                var17_6 = null;
                                x$1 = objectArray12;
                                objectArray11 = this.prefix2();
                                int n = 2;
                                VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                                if (mapElems_n5 == true) {
                                    block45: {
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
                                            var28_15 = null;
                                            break block45;
                                        }
                                        objectArray10 = mapElems_a5;
                                    }
                                    var26_13 = null;
                                    var27_14 = null;
                                    var28_15 = null;
                                } else {
                                    for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a5).length; ++mapElems_i) {
                                        Object var86_26;
                                        Object var85_25;
                                        Object var89_29;
                                        Object var88_28;
                                        Object var90_30;
                                        Object var83_23;
                                        void mapElems_v1 = mapElems_a5[mapElems_i];
                                        T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n5 - true), (T[])mapElems_v1, f);
                                        if (mapElems_v1 == mapElems_v2) continue;
                                        Object[] mapElemsRest_ac = (Object[])Array.newInstance(mapElems_a5.getClass().getComponentType(), ((void)mapElems_a5).length);
                                        if (mapElems_i > 0) {
                                            System.arraycopy(mapElems_a5, 0, mapElemsRest_ac, 0, mapElems_i);
                                        }
                                        mapElemsRest_ac[mapElems_i] = mapElems_v2;
                                        for (int mapElemsRest_i = mapElems_i + 1; mapElemsRest_i < ((void)mapElems_a5).length; ++mapElemsRest_i) {
                                            Object[] objectArray13;
                                            block47: {
                                                void mapElemsRest_mapElems_a;
                                                var83_23 = mapElems_a5[mapElemsRest_i];
                                                void mapElemsRest_mapElems_n = mapElems_n5 - true;
                                                if (mapElemsRest_mapElems_n == true) {
                                                    block46: {
                                                        for (int mapElemsRest_mapElems_mapElems1_i = 0; mapElemsRest_mapElems_mapElems1_i < ((void)mapElemsRest_mapElems_a).length; ++mapElemsRest_mapElems_mapElems1_i) {
                                                            void mapElemsRest_mapElems_mapElems1_v1 = mapElemsRest_mapElems_a[mapElemsRest_mapElems_mapElems1_i];
                                                            B mapElemsRest_mapElems_mapElems1_v2 = f.apply(mapElemsRest_mapElems_mapElems1_v1);
                                                            if (mapElemsRest_mapElems_mapElems1_v1 == mapElemsRest_mapElems_mapElems1_v2) continue;
                                                            Object[] mapElemsRest_mapElems_mapElems1_mapElems1Rest_ac = new Object[((void)mapElemsRest_mapElems_a).length];
                                                            if (mapElemsRest_mapElems_mapElems1_i > 0) {
                                                                System.arraycopy(mapElemsRest_mapElems_a, 0, mapElemsRest_mapElems_mapElems1_mapElems1Rest_ac, 0, mapElemsRest_mapElems_mapElems1_i);
                                                            }
                                                            mapElemsRest_mapElems_mapElems1_mapElems1Rest_ac[mapElemsRest_mapElems_mapElems1_i] = mapElemsRest_mapElems_mapElems1_v2;
                                                            for (int mapElemsRest_mapElems_mapElems1_mapElems1Rest_i = mapElemsRest_mapElems_mapElems1_i + 1; mapElemsRest_mapElems_mapElems1_mapElems1Rest_i < ((void)mapElemsRest_mapElems_a).length; ++mapElemsRest_mapElems_mapElems1_mapElems1Rest_i) {
                                                                mapElemsRest_mapElems_mapElems1_mapElems1Rest_ac[mapElemsRest_mapElems_mapElems1_mapElems1Rest_i] = f.apply(mapElemsRest_mapElems_a[mapElemsRest_mapElems_mapElems1_mapElems1Rest_i]);
                                                            }
                                                            objectArray13 = mapElemsRest_mapElems_mapElems1_mapElems1Rest_ac;
                                                            var90_30 = null;
                                                            break block46;
                                                        }
                                                        objectArray13 = mapElemsRest_mapElems_a;
                                                    }
                                                    var88_28 = null;
                                                    var89_29 = null;
                                                    var90_30 = null;
                                                } else {
                                                    for (int mapElemsRest_mapElems_i = 0; mapElemsRest_mapElems_i < ((void)mapElemsRest_mapElems_a).length; ++mapElemsRest_mapElems_i) {
                                                        void mapElemsRest_mapElems_v1 = mapElemsRest_mapElems_a[mapElemsRest_mapElems_i];
                                                        Object[] mapElemsRest_mapElems_v2 = mapElems_this.mapElems((int)(mapElemsRest_mapElems_n - true), (Object[])mapElemsRest_mapElems_v1, f);
                                                        if (mapElemsRest_mapElems_v1 == mapElemsRest_mapElems_v2) continue;
                                                        objectArray13 = mapElems_this.mapElemsRest((int)mapElemsRest_mapElems_n, (T[])mapElemsRest_mapElems_a, f, mapElemsRest_mapElems_i, mapElemsRest_mapElems_v2);
                                                        break block47;
                                                    }
                                                    objectArray13 = mapElemsRest_mapElems_a;
                                                }
                                            }
                                            var83_23 = null;
                                            var85_25 = null;
                                            var86_26 = null;
                                            var88_28 = null;
                                            var89_29 = null;
                                            var90_30 = null;
                                            mapElemsRest_ac[mapElemsRest_i] = objectArray13;
                                        }
                                        objectArray10 = mapElemsRest_ac;
                                        Object var80_20 = null;
                                        var83_23 = null;
                                        var85_25 = null;
                                        var86_26 = null;
                                        var88_28 = null;
                                        var89_29 = null;
                                        var90_30 = null;
                                        break block48;
                                    }
                                    objectArray10 = mapElems_a5;
                                }
                            }
                            Object var19_11 = null;
                            objectArray11 = null;
                            Object var23_18 = null;
                            Object var24_19 = null;
                            var26_13 = null;
                            var27_14 = null;
                            var28_15 = null;
                            x$2 = (Object[][])objectArray10;
                            objectArray9 = this.prefix3();
                            int n = 3;
                            VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                            if (mapElems_n4 == true) {
                                block49: {
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
                                        var39_39 = null;
                                        break block49;
                                    }
                                    objectArray8 = mapElems_a4;
                                }
                                var37_37 = null;
                                var38_38 = null;
                                var39_39 = null;
                            } else {
                                for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a4).length; ++mapElems_i) {
                                    void mapElems_v1 = mapElems_a4[mapElems_i];
                                    T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n4 - true), (T[])mapElems_v1, f);
                                    if (mapElems_v1 == mapElems_v2) continue;
                                    objectArray8 = mapElems_this.mapElemsRest((int)mapElems_n4, (T[])mapElems_a4, f, mapElems_i, mapElems_v2);
                                    break block50;
                                }
                                objectArray8 = mapElems_a4;
                            }
                        }
                        Object var30_35 = null;
                        objectArray9 = null;
                        Object var34_42 = null;
                        Object var35_43 = null;
                        var37_37 = null;
                        var38_38 = null;
                        var39_39 = null;
                        x$3 = (Object[][][])objectArray8;
                        objectArray7 = this.data4();
                        int n = 4;
                        VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                        if (mapElems_n3 == true) {
                            block51: {
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
                                    var50_51 = null;
                                    break block51;
                                }
                                objectArray6 = mapElems_a3;
                            }
                            var48_49 = null;
                            var49_50 = null;
                            var50_51 = null;
                        } else {
                            for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a3).length; ++mapElems_i) {
                                void mapElems_v1 = mapElems_a3[mapElems_i];
                                T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n3 - true), (T[])mapElems_v1, f);
                                if (mapElems_v1 == mapElems_v2) continue;
                                objectArray6 = mapElems_this.mapElemsRest((int)mapElems_n3, (T[])mapElems_a3, f, mapElems_i, mapElems_v2);
                                break block52;
                            }
                            objectArray6 = mapElems_a3;
                        }
                    }
                    Object var41_47 = null;
                    objectArray7 = null;
                    Object var45_54 = null;
                    Object var46_55 = null;
                    var48_49 = null;
                    var49_50 = null;
                    var50_51 = null;
                    x$4 = (Object[][][][])objectArray6;
                    objectArray5 = this.suffix3();
                    int n = 3;
                    VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                    if (mapElems_n2 == true) {
                        block53: {
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
                                var61_63 = null;
                                break block53;
                            }
                            objectArray4 = mapElems_a2;
                        }
                        var59_61 = null;
                        var60_62 = null;
                        var61_63 = null;
                    } else {
                        for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a2).length; ++mapElems_i) {
                            void mapElems_v1 = mapElems_a2[mapElems_i];
                            T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n2 - true), (T[])mapElems_v1, f);
                            if (mapElems_v1 == mapElems_v2) continue;
                            objectArray4 = mapElems_this.mapElemsRest((int)mapElems_n2, (T[])mapElems_a2, f, mapElems_i, mapElems_v2);
                            break block54;
                        }
                        objectArray4 = mapElems_a2;
                    }
                }
                Object var52_59 = null;
                objectArray5 = null;
                Object var56_66 = null;
                Object var57_67 = null;
                var59_61 = null;
                var60_62 = null;
                var61_63 = null;
                x$5 = (Object[][][])objectArray4;
                objectArray3 = this.suffix2();
                int n = 2;
                VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                if (mapElems_n == true) {
                    block55: {
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
                            var72_75 = null;
                            break block55;
                        }
                        objectArray2 = mapElems_a;
                    }
                    var70_73 = null;
                    var71_74 = null;
                    var72_75 = null;
                } else {
                    for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a).length; ++mapElems_i) {
                        void mapElems_v1 = mapElems_a[mapElems_i];
                        T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n - true), (T[])mapElems_v1, f);
                        if (mapElems_v1 == mapElems_v2) continue;
                        objectArray2 = mapElems_this.mapElemsRest((int)mapElems_n, (T[])mapElems_a, f, mapElems_i, mapElems_v2);
                        break block56;
                    }
                    objectArray2 = mapElems_a;
                }
            }
            Object var63_71 = null;
            objectArray3 = null;
            Object var67_78 = null;
            Object var68_79 = null;
            var70_73 = null;
            var71_74 = null;
            var72_75 = null;
            x$6 = (Object[][])objectArray2;
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
                var78_85 = null;
                break block57;
            }
            objectArray = mapElems1_a;
        }
        Object var74_81 = null;
        Object var76_83 = null;
        Object var77_84 = null;
        var78_85 = null;
        Object[] x$7 = objectArray;
        int x$8 = this.copy$default$2();
        int x$9 = this.copy$default$4();
        int x$10 = this.copy$default$6();
        int x$11 = this.copy$default$11();
        return new Vector4<A>(x$1, x$8, x$2, x$9, x$3, x$10, x$4, x$5, x$6, x$7, x$11);
    }

    @Override
    public Vector<A> slice0(int lo, int hi) {
        VectorSliceBuilder b = new VectorSliceBuilder(lo, hi);
        b.consider(1, this.prefix1());
        b.consider(2, (T[])this.prefix2());
        b.consider(3, (T[])this.prefix3());
        b.consider(4, (T[])this.data4());
        b.consider(3, (T[])this.suffix3());
        b.consider(2, (T[])this.suffix2());
        b.consider(1, this.suffix1());
        return b.result();
    }

    @Override
    public Vector<A> tail() {
        if (this.len1() > 1) {
            Object[] copyTail_a = this.prefix1();
            Object var12_1 = null;
            Object[] x$1 = Arrays.copyOfRange(copyTail_a, 1, copyTail_a.length);
            int x$2 = this.len1() - 1;
            int x$3 = this.len12() - 1;
            int x$4 = this.len123() - 1;
            int x$5 = this.length0() - 1;
            Object[][] x$6 = this.prefix2();
            Object[][][] x$7 = this.prefix3();
            Object[][][][] x$8 = this.data4();
            Object[][][] x$9 = this.suffix3();
            Object[][] x$10 = this.suffix2();
            Object[] x$11 = this.suffix1();
            return new Vector4<A>(x$1, x$2, x$6, x$3, x$7, x$4, x$8, x$9, x$10, x$11, x$5);
        }
        return this.slice0(1, this.length0());
    }

    @Override
    public Vector<A> init() {
        if (this.suffix1().length > 1) {
            Object[] copyInit_a = this.suffix1();
            Object var12_1 = null;
            Object[] x$1 = Arrays.copyOfRange(copyInit_a, 0, copyInit_a.length - 1);
            int x$2 = this.length0() - 1;
            Object[] x$3 = this.prefix1();
            int x$4 = this.len1();
            Object[][] x$5 = this.prefix2();
            int x$6 = this.len12();
            Object[][][] x$7 = this.prefix3();
            int x$8 = this.len123();
            Object[][][][] x$9 = this.data4();
            Object[][][] x$10 = this.suffix3();
            Object[][] x$11 = this.suffix2();
            return new Vector4<A>(x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$1, x$2);
        }
        return this.slice0(0, this.length0() - 1);
    }

    @Override
    public int vectorSliceCount() {
        return 7;
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
                return this.data4();
            }
            case 4: {
                return this.suffix3();
            }
            case 5: {
                return this.suffix2();
            }
            case 6: {
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
                return this.len123() + this.data4().length * 32768;
            }
            case 4: {
                return this.len123() + this.data4().length * 32768 + this.suffix3().length * 1024;
            }
            case 5: {
                return this.length0() - this.suffix1().length;
            }
            case 6: {
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
        int x$5 = this.length0() + diff;
        Object[][] x$6 = this.prefix2();
        Object[][][] x$7 = this.prefix3();
        Object[][][][] x$8 = this.data4();
        Object[][][] x$9 = this.suffix3();
        Object[][] x$10 = this.suffix2();
        Object[] x$11 = this.suffix1();
        return new Vector4<A>(objectArray, x$2, x$6, x$3, x$7, x$4, x$8, x$9, x$10, x$11, x$5);
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
            Object[][][][] x$9 = this.data4();
            Object[][][] x$10 = this.suffix3();
            Object[][] x$11 = this.suffix2();
            return new Vector4<A>(x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, suffix1b, x$2);
        }
        return super.appendedAll0(suffix, k);
    }

    public Vector4(Object[] _prefix1, int len1, Object[][] prefix2, int len12, Object[][][] prefix3, int len123, Object[][][][] data4, Object[][][] suffix3, Object[][] suffix2, Object[] _suffix1, int _length0) {
        this.len1 = len1;
        this.prefix2 = prefix2;
        this.len12 = len12;
        this.prefix3 = prefix3;
        this.len123 = len123;
        this.data4 = data4;
        this.suffix3 = suffix3;
        this.suffix2 = suffix2;
        super(_prefix1, _suffix1, _length0);
    }
}

