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
import scala.collection.immutable.Vector4;
import scala.collection.immutable.VectorSliceBuilder;
import scala.collection.immutable.VectorStatics$;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;

@ScalaSignature(bytes="\u0006\u0005\t\ra\u0001B\u0013'\r5B\u0011b\u0010\u0001\u0003\u0002\u0003\u0006I\u0001\u0011)\t\u0013Q\u0003!Q1A\u0005\u0002\u0019*\u0006\u0002C-\u0001\u0005\u0003\u0005\u000b\u0011\u0002,\t\u0013i\u0003!Q1A\u0005\u0002\u0019Z\u0006\u0002C0\u0001\u0005\u0003\u0005\u000b\u0011\u0002/\t\u0013\u0001\u0004!Q1A\u0005\u0002\u0019*\u0006\u0002C1\u0001\u0005\u0003\u0005\u000b\u0011\u0002,\t\u0013\t\u0004!Q1A\u0005\u0002\u0019\u001a\u0007\u0002C4\u0001\u0005\u0003\u0005\u000b\u0011\u00023\t\u0013!\u0004!Q1A\u0005\u0002\u0019Z\u0006\u0002C5\u0001\u0005\u0003\u0005\u000b\u0011\u0002/\t\u0013)\u0004!\u0011!Q\u0001\n\u0001[\u0007\"C7\u0001\u0005\u0003\u0005\u000b\u0011\u0002,o\u0011\u0015\u0001\b\u0001\"\u0001r\u0011\u0019Y\b\u0001)C\u0005y\"I\u0011Q\u0003\u0001\u0012\u0002\u0013%\u0011q\u0003\u0005\n\u0003[\u0001\u0011\u0013!C\u0005\u0003_A\u0011\"a\r\u0001#\u0003%I!!\u000e\t\u0013\u0005e\u0002!%A\u0005\n\u0005=\u0002\"CA\u001e\u0001E\u0005I\u0011BA\u001f\u0011%\t\t\u0005AI\u0001\n\u0013\t)\u0004C\u0005\u0002D\u0001\t\n\u0011\"\u0003\u0002\u0018!I\u0011Q\t\u0001\u0012\u0002\u0013%\u0011q\u0006\u0005\b\u0003\u000f\u0002A\u0011AA%\u0011\u001d\t\t\u0006\u0001C!\u0003'Bq!a\u001a\u0001\t\u0003\nI\u0007C\u0004\u0002v\u0001!\t%a\u001e\t\u000f\u0005\r\u0005\u0001\"\u0011\u0002\u0006\"A\u0011\u0011\u0014\u0001!\n#\tY\nC\u0004\u0002(\u0002!\t%!+\t\u000f\u0005-\u0006\u0001\"\u0011\u0002*\"9\u0011Q\u0016\u0001\u0005\u0012\u0019*\u0006\u0002CAX\u0001\u0011Ea%!-\t\u0011\u00055\u0007\u0001\"\u0005'\u0003\u001fD\u0001\"a5\u0001A\u0013E\u0013Q\u001b\u0005\t\u0003_\u0004\u0001\u0015\"\u0015\u0002r\n9a+Z2u_J\u001c$BA\u0014)\u0003%IW.\\;uC\ndWM\u0003\u0002*U\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003-\nQa]2bY\u0006\u001c\u0001!\u0006\u0002/kM\u0011\u0001a\f\t\u0004aE\u001aT\"\u0001\u0014\n\u0005I2#!\u0003\"jOZ+7\r^8s!\t!T\u0007\u0004\u0001\u0005\rY\u0002AQ1\u00018\u0005\u0005\t\u0015C\u0001\u001d=!\tI$(D\u0001+\u0013\tY$FA\u0004O_RD\u0017N\\4\u0011\u0005ej\u0014B\u0001 +\u0005\r\te._\u0001\t?B\u0014XMZ5ycA\u0011\u0011)\u0014\b\u0003\u0005.s!a\u0011&\u000f\u0005\u0011KeBA#I\u001b\u00051%BA$-\u0003\u0019a$o\\8u}%\t1&\u0003\u0002*U%\u0011q\u0005K\u0005\u0003\u0019\u001a\nABV3di>\u0014\u0018J\u001c7j]\u0016L!AT(\u0003\t\u0005\u0013(/\r\u0006\u0003\u0019\u001aJ!!\u0015*\u0002\u000fA\u0014XMZ5yc%\u00111K\n\u0002\u0007-\u0016\u001cGo\u001c:\u0002\t1,g.M\u000b\u0002-B\u0011\u0011hV\u0005\u00031*\u00121!\u00138u\u0003\u0015aWM\\\u0019!\u0003\u001d\u0001(/\u001a4jqJ*\u0012\u0001\u0018\t\u0003\u0003vK!AX(\u0003\t\u0005\u0013(OM\u0001\taJ,g-\u001b=3A\u0005)A.\u001a82e\u00051A.\u001a82e\u0001\nQ\u0001Z1uCN*\u0012\u0001\u001a\t\u0003\u0003\u0016L!AZ(\u0003\t\u0005\u0013(oM\u0001\u0007I\u0006$\u0018m\r\u0011\u0002\u000fM,hMZ5ye\u0005A1/\u001e4gSb\u0014\u0004%\u0001\u0005`gV4g-\u001b=2\u0013\ta\u0017'A\u0004tk\u001a4\u0017\u000e_\u0019\u0002\u0011}cWM\\4uQBJ!a\\\u0019\u0002\u000f1,gn\u001a;ia\u00051A(\u001b8jiz\"\u0012B]:ukZ<\b0\u001f>\u0011\u0007A\u00021\u0007C\u0003@\u001d\u0001\u0007\u0001\tC\u0003U\u001d\u0001\u0007a\u000bC\u0003[\u001d\u0001\u0007A\fC\u0003a\u001d\u0001\u0007a\u000bC\u0003c\u001d\u0001\u0007A\rC\u0003i\u001d\u0001\u0007A\fC\u0003k\u001d\u0001\u0007\u0001\tC\u0003n\u001d\u0001\u0007a+\u0001\u0003d_BLHcD?\u007f\u007f\u0006\u0005\u00111AA\u0003\u0003\u000f\tI!a\u0003\u0011\u0007A\u0002\u0001\bC\u0004R\u001fA\u0005\t\u0019\u0001!\t\u000fQ{\u0001\u0013!a\u0001-\"9!l\u0004I\u0001\u0002\u0004a\u0006b\u00021\u0010!\u0003\u0005\rA\u0016\u0005\bE>\u0001\n\u00111\u0001e\u0011\u001dAw\u0002%AA\u0002qCq\u0001\\\b\u0011\u0002\u0003\u0007\u0001\tC\u0004p\u001fA\u0005\t\u0019\u0001,)\u0007=\ty\u0001E\u0002:\u0003#I1!a\u0005+\u0005\u0019Ig\u000e\\5oK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nTCAA\rU\r\u0001\u00151D\u0016\u0003\u0003;\u0001B!a\b\u0002*5\u0011\u0011\u0011\u0005\u0006\u0005\u0003G\t)#A\u0005v]\u000eDWmY6fI*\u0019\u0011q\u0005\u0016\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002,\u0005\u0005\"!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012TCAA\u0019U\r1\u00161D\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00134+\t\t9DK\u0002]\u00037\tabY8qs\u0012\"WMZ1vYR$C'\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001b\u0016\u0005\u0005}\"f\u00013\u0002\u001c\u0005q1m\u001c9zI\u0011,g-Y;mi\u00122\u0014AD2paf$C-\u001a4bk2$HeN\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00139\u0003\u0015\t\u0007\u000f\u001d7z)\r\u0019\u00141\n\u0005\u0007\u0003\u001bB\u0002\u0019\u0001,\u0002\u000b%tG-\u001a=)\u0007a\ty!A\u0004va\u0012\fG/\u001a3\u0016\t\u0005U\u00131\f\u000b\u0007\u0003/\n\t'a\u0019\u0011\tA\u0012\u0016\u0011\f\t\u0004i\u0005mCaBA/3\t\u0007\u0011q\f\u0002\u0002\u0005F\u00111\u0007\u0010\u0005\u0007\u0003\u001bJ\u0002\u0019\u0001,\t\u000f\u0005\u0015\u0014\u00041\u0001\u0002Z\u0005!Q\r\\3n\u0003!\t\u0007\u000f]3oI\u0016$W\u0003BA6\u0003c\"B!!\u001c\u0002tA!\u0001GUA8!\r!\u0014\u0011\u000f\u0003\b\u0003;R\"\u0019AA0\u0011\u001d\t)G\u0007a\u0001\u0003_\n\u0011\u0002\u001d:fa\u0016tG-\u001a3\u0016\t\u0005e\u0014q\u0010\u000b\u0005\u0003w\n\t\t\u0005\u00031%\u0006u\u0004c\u0001\u001b\u0002\u0000\u00119\u0011QL\u000eC\u0002\u0005}\u0003bBA37\u0001\u0007\u0011QP\u0001\u0004[\u0006\u0004X\u0003BAD\u0003\u001b#B!!#\u0002\u0010B!\u0001GUAF!\r!\u0014Q\u0012\u0003\u0007\u0003;b\"\u0019A\u001c\t\u000f\u0005EE\u00041\u0001\u0002\u0014\u0006\ta\r\u0005\u0004:\u0003+\u001b\u00141R\u0005\u0004\u0003/S#!\u0003$v]\u000e$\u0018n\u001c82\u0003\u0019\u0019H.[2faQ1\u0011QTAP\u0003G\u00032\u0001\r*4\u0011\u0019\t\t+\ba\u0001-\u0006\u0011An\u001c\u0005\u0007\u0003Kk\u0002\u0019\u0001,\u0002\u0005!L\u0017\u0001\u0002;bS2,\"!!(\u0002\t%t\u0017\u000e^\u0001\u0011m\u0016\u001cGo\u001c:TY&\u001cWmQ8v]R\f1B^3di>\u00148\u000b\\5dKR!\u00111WAea\u0011\t),!0\u0011\u000be\n9,a/\n\u0007\u0005e&FA\u0003BeJ\f\u0017\u0010E\u00025\u0003{#1\"a0\"\u0003\u0003\u0005\tQ!\u0001\u0002B\n!q\fJ\u00195#\rA\u00141\u0019\t\u0004s\u0005\u0015\u0017bAAdU\t1\u0011I\\=SK\u001aDa!a3\"\u0001\u00041\u0016aA5eq\u00069b/Z2u_J\u001cF.[2f!J,g-\u001b=MK:<G\u000f\u001b\u000b\u0004-\u0006E\u0007BBAfE\u0001\u0007a+A\u0007qe\u0016\u0004XM\u001c3fI\u0006cG\u000eM\u000b\u0005\u0003/\fi\u000e\u0006\u0004\u0002Z\u0006}\u00171\u001e\t\u0005aI\u000bY\u000eE\u00025\u0003;$q!!\u0018$\u0005\u0004\ty\u0006C\u0004\u0002b\u000e\u0002\r!a9\u0002\rA\u0014XMZ5y!\u0019\t)/a:\u0002\\6\t\u0001&C\u0002\u0002j\"\u0012A\"\u0013;fe\u0006\u0014G.Z(oG\u0016Da!!<$\u0001\u00041\u0016!A6\u0002\u0019\u0005\u0004\b/\u001a8eK\u0012\fE\u000e\u001c\u0019\u0016\t\u0005M\u0018\u0011 \u000b\u0007\u0003k\fYP!\u0001\u0011\tA\u0012\u0016q\u001f\t\u0004i\u0005eHaBA/I\t\u0007\u0011q\f\u0005\b\u0003{$\u0003\u0019AA\u0000\u0003\u0019\u0019XO\u001a4jqB1\u0011Q]At\u0003oDa!!<%\u0001\u00041\u0006")
public final class Vector3<A>
extends BigVector<A> {
    private final int len1;
    private final Object[][] prefix2;
    private final int len12;
    private final Object[][][] data3;
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

    public Object[][][] data3() {
        return this.data3;
    }

    public Object[][] suffix2() {
        return this.suffix2;
    }

    private Vector3<Nothing$> copy(Object[] prefix1, int len1, Object[][] prefix2, int len12, Object[][][] data3, Object[][] suffix2, Object[] suffix1, int length0) {
        return new Vector3<Nothing$>(prefix1, len1, prefix2, len12, data3, suffix2, suffix1, length0);
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
        return this.data3();
    }

    private Object[][] copy$default$6() {
        return this.suffix2();
    }

    private Object[] copy$default$7() {
        return this.suffix1();
    }

    private int copy$default$8() {
        return this.length0();
    }

    @Override
    public A apply(int index) {
        if (index >= 0 && index < this.length0()) {
            int io = index - this.len12();
            if (io >= 0) {
                int i3 = io >>> 10;
                int i2 = io >>> 5 & 0x1F;
                int i1 = io & 0x1F;
                if (i3 < this.data3().length) {
                    return (A)this.data3()[i3][i2][i1];
                }
                if (i2 < this.suffix2().length) {
                    return (A)this.suffix2()[i2][i1];
                }
                return (A)this.suffix1()[i1];
            }
            if (index >= this.len1()) {
                int io2 = index - this.len1();
                return (A)this.prefix2()[io2 >>> 5][io2 & 0x1F];
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
            void copy_data3;
            void copy_len12;
            void copy_prefix2;
            void copy_len1;
            if (index >= this.len12()) {
                int io = index - this.len12();
                int i3 = io >>> 10;
                int i2 = io >>> 5 & 0x1F;
                int i1 = io & 0x1F;
                if (i3 < this.data3().length) {
                    Object[][][] copyUpdate_a3 = this.data3();
                    Object[][][] copyUpdate_a3c = (Object[][][])copyUpdate_a3.clone();
                    Object[][] copyUpdate_copyUpdate_a2c = (Object[][])copyUpdate_a3c[i3].clone();
                    Object[] copyUpdate_copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_copyUpdate_a2c[i2].clone();
                    copyUpdate_copyUpdate_copyUpdate_a1c[i1] = elem;
                    Object var43_10 = null;
                    copyUpdate_copyUpdate_a2c[i2] = copyUpdate_copyUpdate_copyUpdate_a1c;
                    Object var42_9 = null;
                    copyUpdate_a3c[i3] = copyUpdate_copyUpdate_a2c;
                    Object var40_7 = null;
                    Object var41_8 = null;
                    Object[][][] x$1 = copyUpdate_a3c;
                    Object[] x$2 = this.prefix1();
                    int x$3 = this.len1();
                    Object[][] x$4 = this.prefix2();
                    int x$5 = this.len12();
                    Object[][] x$6 = this.suffix2();
                    Object[] x$7 = this.suffix1();
                    int x$8 = this.length0();
                    return new Vector3<A>(x$2, x$3, x$4, x$5, x$1, x$6, x$7, x$8);
                }
                if (i2 < this.suffix2().length) {
                    Object[][] copyUpdate_a2 = this.suffix2();
                    Object[][] copyUpdate_a2c = (Object[][])copyUpdate_a2.clone();
                    Object[] copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_a2c[i2].clone();
                    copyUpdate_copyUpdate_a1c[i1] = elem;
                    Object var46_21 = null;
                    copyUpdate_a2c[i2] = copyUpdate_copyUpdate_a1c;
                    Object var44_19 = null;
                    Object var45_20 = null;
                    Object[][] x$9 = copyUpdate_a2c;
                    Object[] x$10 = this.prefix1();
                    int x$11 = this.len1();
                    Object[][] x$12 = this.prefix2();
                    int x$13 = this.len12();
                    Object[][][] x$14 = this.data3();
                    Object[] x$15 = this.suffix1();
                    int x$16 = this.length0();
                    return new Vector3<A>(x$10, x$11, x$12, x$13, x$14, x$9, x$15, x$16);
                }
                Object[] copyUpdate_a1 = this.suffix1();
                Object[] copyUpdate_a1c = (Object[])copyUpdate_a1.clone();
                copyUpdate_a1c[i1] = elem;
                Object var47_30 = null;
                Object var48_31 = null;
                Object[] x$17 = copyUpdate_a1c;
                Object[] x$18 = this.prefix1();
                int x$19 = this.len1();
                Object[][] x$20 = this.prefix2();
                int x$21 = this.len12();
                Object[][][] x$22 = this.data3();
                Object[][] x$23 = this.suffix2();
                int x$24 = this.length0();
                return new Vector3<A>(x$18, x$19, x$20, x$21, x$22, x$23, x$17, x$24);
            }
            if (index >= this.len1()) {
                void copyUpdate_idx2;
                int io = index - this.len1();
                int n = io & 0x1F;
                int n2 = io >>> 5;
                Object[][] copyUpdate_a2 = this.prefix2();
                Object[][] copyUpdate_a2c = (Object[][])copyUpdate_a2.clone();
                Object[] copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_a2c[copyUpdate_idx2].clone();
                copyUpdate_copyUpdate_a1c[copyUpdate_idx1] = elem;
                Object var53_45 = null;
                copyUpdate_a2c[copyUpdate_idx2] = copyUpdate_copyUpdate_a1c;
                Object var49_43 = null;
                Object var52_44 = null;
                Object[][] x$25 = copyUpdate_a2c;
                Object[] x$26 = this.prefix1();
                int x$27 = this.len1();
                int x$28 = this.len12();
                Object[][][] x$29 = this.data3();
                Object[][] x$30 = this.suffix2();
                Object[] x$31 = this.suffix1();
                int x$32 = this.length0();
                return new Vector3<A>(x$26, x$27, x$25, x$28, x$29, x$30, x$31, x$32);
            }
            Object[] copyUpdate_a1c = (Object[])this.prefix1().clone();
            copyUpdate_a1c[index] = elem;
            Object var54_54 = null;
            int n = this.length0();
            Object[] objectArray = this.suffix1();
            Object[][] objectArray2 = this.suffix2();
            Object[][][] objectArray3 = this.data3();
            int n3 = this.len12();
            Object[][] objectArray4 = this.prefix2();
            int n4 = this.len1();
            Object[] copy_prefix1 = copyUpdate_a1c;
            return new Vector3<A>(copy_prefix1, (int)copy_len1, (Object[][])copy_prefix2, (int)copy_len12, (Object[][][])copy_data3, (Object[][])copy_suffix2, (Object[])copy_suffix1, (int)copy_length0);
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
            Object[][][] x$7 = this.data3();
            Object[][] x$8 = this.suffix2();
            return new Vector3<A>(x$3, x$4, x$5, x$6, x$7, x$8, x$1, x$2);
        }
        if (this.suffix2().length < 31) {
            Object[][] x$9 = VectorStatics$.MODULE$.copyAppend((T[])this.suffix2(), this.suffix1());
            Object[] wrap1_a = new Object[]{elem};
            Object var26_11 = null;
            Object[] x$10 = wrap1_a;
            int x$11 = this.length0() + 1;
            Object[] x$12 = this.prefix1();
            int x$13 = this.len1();
            Object[][] x$14 = this.prefix2();
            int x$15 = this.len12();
            Object[][][] x$16 = this.data3();
            return new Vector3<A>(x$12, x$13, x$14, x$15, x$16, x$9, x$10, x$11);
        }
        if (this.data3().length < 30) {
            Object[][][] x$17 = VectorStatics$.MODULE$.copyAppend((T[])this.data3(), VectorStatics$.MODULE$.copyAppend((T[])this.suffix2(), this.suffix1()));
            Object[][] x$18 = VectorStatics$.MODULE$.empty2();
            Object[] wrap1_a = new Object[]{elem};
            Object var27_21 = null;
            Object[] x$19 = wrap1_a;
            int x$20 = this.length0() + 1;
            Object[] x$21 = this.prefix1();
            int x$22 = this.len1();
            Object[][] x$23 = this.prefix2();
            int x$24 = this.len12();
            return new Vector3<A>(x$21, x$22, x$23, x$24, x$17, x$18, x$19, x$20);
        }
        Object[][] wrap3_x = VectorStatics$.MODULE$.copyAppend((T[])this.suffix2(), this.suffix1());
        Object[][][] wrap3_a = new Object[][][]{wrap3_x};
        Object var28_28 = null;
        Object var29_29 = null;
        Object[] wrap1_a = new Object[]{elem};
        Object var30_30 = null;
        return new Vector4(this.prefix1(), this.len1(), this.prefix2(), this.len12(), this.data3(), 30720 + this.len12(), VectorStatics$.MODULE$.empty4(), wrap3_a, VectorStatics$.MODULE$.empty2(), wrap1_a, this.length0() + 1);
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
            int x$4 = this.length0() + 1;
            Object[][] x$5 = this.prefix2();
            Object[][][] x$6 = this.data3();
            Object[][] x$7 = this.suffix2();
            Object[] x$8 = this.suffix1();
            return new Vector3<A>(x$1, x$2, x$5, x$3, x$6, x$7, x$8, x$4);
        }
        if (this.len12() < 1024) {
            Object[] wrap1_a = new Object[]{elem};
            Object var23_10 = null;
            Object[] x$9 = wrap1_a;
            Object[][] x$11 = VectorStatics$.MODULE$.copyPrepend(this.prefix1(), (T[])this.prefix2());
            int x$12 = this.len12() + 1;
            int x$13 = this.length0() + 1;
            Object[][][] x$14 = this.data3();
            Object[][] x$15 = this.suffix2();
            Object[] x$16 = this.suffix1();
            int copy_len1 = 1;
            return new Vector3<A>(x$9, copy_len1, x$11, x$12, x$14, x$15, x$16, x$13);
        }
        if (this.data3().length < 30) {
            void copy_len12;
            Object[] wrap1_a = new Object[]{elem};
            Object var25_19 = null;
            Object[] x$17 = wrap1_a;
            Object[][] x$19 = VectorStatics$.MODULE$.empty2();
            Object[][][] x$21 = VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(this.prefix1(), (T[])this.prefix2()), (T[])this.data3());
            int x$22 = this.length0() + 1;
            Object[][] x$23 = this.suffix2();
            Object[] x$24 = this.suffix1();
            boolean bl = true;
            int copy_len1 = 1;
            return new Vector3<A>(x$17, copy_len1, x$19, (int)copy_len12, x$21, x$23, x$24, x$22);
        }
        Object[] wrap1_a = new Object[]{elem};
        Object var28_28 = null;
        Object[][] wrap3_x = VectorStatics$.MODULE$.copyPrepend(this.prefix1(), (T[])this.prefix2());
        Object[][][] wrap3_a = new Object[][][]{wrap3_x};
        Object var29_29 = null;
        Object var30_30 = null;
        return new Vector4(wrap1_a, 1, VectorStatics$.MODULE$.empty2(), 1, wrap3_a, this.len12() + 1, VectorStatics$.MODULE$.empty4(), this.data3(), this.suffix2(), this.suffix1(), this.length0() + 1);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> Vector<B> map(Function1<A, B> f) {
        Object var53_73;
        Object[] objectArray;
        Object[][] x$4;
        Object[][][] x$3;
        Object[][] x$2;
        Object[] x$1;
        block51: {
            Object var46_62;
            Object var45_61;
            Object var47_63;
            Object[] objectArray2;
            Object[][] objectArray3;
            block50: {
                void mapElems_a;
                void mapElems_n;
                Object var35_38;
                Object var34_37;
                Object var36_39;
                Object[] objectArray4;
                Object[][][] objectArray5;
                block48: {
                    void mapElems_a2;
                    void mapElems_n2;
                    Object var24_14;
                    Object var23_13;
                    Object var25_15;
                    Object[] objectArray6;
                    Object[][] objectArray7;
                    block44: {
                        void mapElems_a3;
                        void mapElems_n3;
                        Object var14_6;
                        Object[] objectArray8;
                        block40: {
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
                                objectArray8 = mapElems1_mapElems1Rest_ac;
                                var14_6 = null;
                                break block40;
                            }
                            objectArray8 = mapElems1_a;
                        }
                        Object var10_2 = null;
                        Object var12_4 = null;
                        Object var13_5 = null;
                        var14_6 = null;
                        x$1 = objectArray8;
                        objectArray7 = this.prefix2();
                        int n = 2;
                        VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                        if (mapElems_n3 == true) {
                            block41: {
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
                                    var25_15 = null;
                                    break block41;
                                }
                                objectArray6 = mapElems_a3;
                            }
                            var23_13 = null;
                            var24_14 = null;
                            var25_15 = null;
                        } else {
                            for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a3).length; ++mapElems_i) {
                                Object var61_29;
                                Object var60_28;
                                Object var64_22;
                                Object var63_31;
                                Object var65_23;
                                Object var58_26;
                                void mapElems_v1 = mapElems_a3[mapElems_i];
                                T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n3 - true), (T[])mapElems_v1, f);
                                if (mapElems_v1 == mapElems_v2) continue;
                                Object[] mapElemsRest_ac = (Object[])Array.newInstance(mapElems_a3.getClass().getComponentType(), ((void)mapElems_a3).length);
                                if (mapElems_i > 0) {
                                    System.arraycopy(mapElems_a3, 0, mapElemsRest_ac, 0, mapElems_i);
                                }
                                mapElemsRest_ac[mapElems_i] = mapElems_v2;
                                for (int mapElemsRest_i = mapElems_i + 1; mapElemsRest_i < ((void)mapElems_a3).length; ++mapElemsRest_i) {
                                    Object[] objectArray9;
                                    block43: {
                                        void mapElemsRest_mapElems_a;
                                        var58_26 = mapElems_a3[mapElemsRest_i];
                                        void mapElemsRest_mapElems_n = mapElems_n3 - true;
                                        if (mapElemsRest_mapElems_n == true) {
                                            block42: {
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
                                                    objectArray9 = mapElemsRest_mapElems_mapElems1_mapElems1Rest_ac;
                                                    var65_23 = null;
                                                    break block42;
                                                }
                                                objectArray9 = mapElemsRest_mapElems_a;
                                            }
                                            var63_31 = null;
                                            var64_22 = null;
                                            var65_23 = null;
                                        } else {
                                            for (int mapElemsRest_mapElems_i = 0; mapElemsRest_mapElems_i < ((void)mapElemsRest_mapElems_a).length; ++mapElemsRest_mapElems_i) {
                                                void mapElemsRest_mapElems_v1 = mapElemsRest_mapElems_a[mapElemsRest_mapElems_i];
                                                Object[] mapElemsRest_mapElems_v2 = mapElems_this.mapElems((int)(mapElemsRest_mapElems_n - true), (Object[])mapElemsRest_mapElems_v1, f);
                                                if (mapElemsRest_mapElems_v1 == mapElemsRest_mapElems_v2) continue;
                                                objectArray9 = mapElems_this.mapElemsRest((int)mapElemsRest_mapElems_n, (T[])mapElemsRest_mapElems_a, f, mapElemsRest_mapElems_i, mapElemsRest_mapElems_v2);
                                                break block43;
                                            }
                                            objectArray9 = mapElemsRest_mapElems_a;
                                        }
                                    }
                                    var58_26 = null;
                                    var60_28 = null;
                                    var61_29 = null;
                                    var63_31 = null;
                                    var64_22 = null;
                                    var65_23 = null;
                                    mapElemsRest_ac[mapElemsRest_i] = objectArray9;
                                }
                                objectArray6 = mapElemsRest_ac;
                                Object var55_20 = null;
                                var58_26 = null;
                                var60_28 = null;
                                var61_29 = null;
                                var63_31 = null;
                                var64_22 = null;
                                var65_23 = null;
                                break block44;
                            }
                            objectArray6 = mapElems_a3;
                        }
                    }
                    Object var16_11 = null;
                    objectArray7 = null;
                    Object var20_18 = null;
                    Object var21_19 = null;
                    var23_13 = null;
                    var24_14 = null;
                    var25_15 = null;
                    x$2 = (Object[][])objectArray6;
                    objectArray5 = this.data3();
                    int n = 3;
                    VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                    if (mapElems_n2 == true) {
                        block45: {
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
                                var36_39 = null;
                                break block45;
                            }
                            objectArray4 = mapElems_a2;
                        }
                        var34_37 = null;
                        var35_38 = null;
                        var36_39 = null;
                    } else {
                        for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a2).length; ++mapElems_i) {
                            Object var73_50;
                            Object var72_49;
                            Object var76_53;
                            Object var75_52;
                            Object var77_54;
                            Object var70_47;
                            void mapElems_v1 = mapElems_a2[mapElems_i];
                            T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n2 - true), (T[])mapElems_v1, f);
                            if (mapElems_v1 == mapElems_v2) continue;
                            Object[] mapElemsRest_ac = (Object[])Array.newInstance(mapElems_a2.getClass().getComponentType(), ((void)mapElems_a2).length);
                            if (mapElems_i > 0) {
                                System.arraycopy(mapElems_a2, 0, mapElemsRest_ac, 0, mapElems_i);
                            }
                            mapElemsRest_ac[mapElems_i] = mapElems_v2;
                            for (int mapElemsRest_i = mapElems_i + 1; mapElemsRest_i < ((void)mapElems_a2).length; ++mapElemsRest_i) {
                                Object[] objectArray10;
                                block47: {
                                    void mapElemsRest_mapElems_a;
                                    var70_47 = mapElems_a2[mapElemsRest_i];
                                    void mapElemsRest_mapElems_n = mapElems_n2 - true;
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
                                                objectArray10 = mapElemsRest_mapElems_mapElems1_mapElems1Rest_ac;
                                                var77_54 = null;
                                                break block46;
                                            }
                                            objectArray10 = mapElemsRest_mapElems_a;
                                        }
                                        var75_52 = null;
                                        var76_53 = null;
                                        var77_54 = null;
                                    } else {
                                        for (int mapElemsRest_mapElems_i = 0; mapElemsRest_mapElems_i < ((void)mapElemsRest_mapElems_a).length; ++mapElemsRest_mapElems_i) {
                                            void mapElemsRest_mapElems_v1 = mapElemsRest_mapElems_a[mapElemsRest_mapElems_i];
                                            Object[] mapElemsRest_mapElems_v2 = mapElems_this.mapElems((int)(mapElemsRest_mapElems_n - true), (Object[])mapElemsRest_mapElems_v1, f);
                                            if (mapElemsRest_mapElems_v1 == mapElemsRest_mapElems_v2) continue;
                                            objectArray10 = mapElems_this.mapElemsRest((int)mapElemsRest_mapElems_n, (T[])mapElemsRest_mapElems_a, f, mapElemsRest_mapElems_i, mapElemsRest_mapElems_v2);
                                            break block47;
                                        }
                                        objectArray10 = mapElemsRest_mapElems_a;
                                    }
                                }
                                var70_47 = null;
                                var72_49 = null;
                                var73_50 = null;
                                var75_52 = null;
                                var76_53 = null;
                                var77_54 = null;
                                mapElemsRest_ac[mapElemsRest_i] = objectArray10;
                            }
                            objectArray4 = mapElemsRest_ac;
                            Object var67_44 = null;
                            var70_47 = null;
                            var72_49 = null;
                            var73_50 = null;
                            var75_52 = null;
                            var76_53 = null;
                            var77_54 = null;
                            break block48;
                        }
                        objectArray4 = mapElems_a2;
                    }
                }
                Object var27_35 = null;
                objectArray5 = null;
                Object var31_42 = null;
                Object var32_43 = null;
                var34_37 = null;
                var35_38 = null;
                var36_39 = null;
                x$3 = (Object[][][])objectArray4;
                objectArray3 = this.suffix2();
                int n = 2;
                VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                if (mapElems_n == true) {
                    block49: {
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
                            var47_63 = null;
                            break block49;
                        }
                        objectArray2 = mapElems_a;
                    }
                    var45_61 = null;
                    var46_62 = null;
                    var47_63 = null;
                } else {
                    for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a).length; ++mapElems_i) {
                        void mapElems_v1 = mapElems_a[mapElems_i];
                        T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n - true), (T[])mapElems_v1, f);
                        if (mapElems_v1 == mapElems_v2) continue;
                        objectArray2 = mapElems_this.mapElemsRest((int)mapElems_n, (T[])mapElems_a, f, mapElems_i, mapElems_v2);
                        break block50;
                    }
                    objectArray2 = mapElems_a;
                }
            }
            Object var38_59 = null;
            objectArray3 = null;
            Object var42_66 = null;
            Object var43_67 = null;
            var45_61 = null;
            var46_62 = null;
            var47_63 = null;
            x$4 = (Object[][])objectArray2;
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
                var53_73 = null;
                break block51;
            }
            objectArray = mapElems1_a;
        }
        Object var49_69 = null;
        Object var51_71 = null;
        Object var52_72 = null;
        var53_73 = null;
        Object[] x$5 = objectArray;
        int x$6 = this.len1();
        int x$7 = this.len12();
        int x$8 = this.length0();
        return new Vector3<A>(x$1, x$6, x$2, x$7, x$3, x$4, x$5, x$8);
    }

    @Override
    public Vector<A> slice0(int lo, int hi) {
        VectorSliceBuilder b = new VectorSliceBuilder(lo, hi);
        b.consider(1, this.prefix1());
        b.consider(2, (T[])this.prefix2());
        b.consider(3, (T[])this.data3());
        b.consider(2, (T[])this.suffix2());
        b.consider(1, this.suffix1());
        return b.result();
    }

    @Override
    public Vector<A> tail() {
        if (this.len1() > 1) {
            Object[] copyTail_a = this.prefix1();
            Object var9_1 = null;
            Object[] x$1 = Arrays.copyOfRange(copyTail_a, 1, copyTail_a.length);
            int x$2 = this.len1() - 1;
            int x$3 = this.len12() - 1;
            int x$4 = this.length0() - 1;
            Object[][] x$5 = this.prefix2();
            Object[][][] x$6 = this.data3();
            Object[][] x$7 = this.suffix2();
            Object[] x$8 = this.suffix1();
            return new Vector3<A>(x$1, x$2, x$5, x$3, x$6, x$7, x$8, x$4);
        }
        return this.slice0(1, this.length0());
    }

    @Override
    public Vector<A> init() {
        if (this.suffix1().length > 1) {
            Object[] copyInit_a = this.suffix1();
            Object var9_1 = null;
            Object[] x$1 = Arrays.copyOfRange(copyInit_a, 0, copyInit_a.length - 1);
            int x$2 = this.length0() - 1;
            Object[] x$3 = this.prefix1();
            int x$4 = this.len1();
            Object[][] x$5 = this.prefix2();
            int x$6 = this.len12();
            Object[][][] x$7 = this.data3();
            Object[][] x$8 = this.suffix2();
            return new Vector3<A>(x$3, x$4, x$5, x$6, x$7, x$8, x$1, x$2);
        }
        return this.slice0(0, this.length0() - 1);
    }

    @Override
    public int vectorSliceCount() {
        return 5;
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
                return this.data3();
            }
            case 3: {
                return this.suffix2();
            }
            case 4: {
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
                return this.len12() + this.data3().length * 1024;
            }
            case 3: {
                return this.length0() - this.suffix1().length;
            }
            case 4: {
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
        int x$4 = this.length0() + diff;
        Object[][] x$5 = this.prefix2();
        Object[][][] x$6 = this.data3();
        Object[][] x$7 = this.suffix2();
        Object[] x$8 = this.suffix1();
        return new Vector3<A>(objectArray, x$2, x$5, x$3, x$6, x$7, x$8, x$4);
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
            Object[][][] x$7 = this.data3();
            Object[][] x$8 = this.suffix2();
            return new Vector3<A>(x$3, x$4, x$5, x$6, x$7, x$8, suffix1b, x$2);
        }
        return super.appendedAll0(suffix, k);
    }

    public Vector3(Object[] _prefix1, int len1, Object[][] prefix2, int len12, Object[][][] data3, Object[][] suffix2, Object[] _suffix1, int _length0) {
        this.len1 = len1;
        this.prefix2 = prefix2;
        this.len12 = len12;
        this.data3 = data3;
        this.suffix2 = suffix2;
        super(_prefix1, _suffix1, _length0);
    }
}

