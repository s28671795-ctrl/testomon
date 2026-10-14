/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import java.util.Arrays;
import scala.Function1;
import scala.MatchError;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Vector;
import scala.collection.immutable.Vector$;
import scala.collection.immutable.Vector1;
import scala.collection.immutable.Vector2;
import scala.collection.immutable.Vector3;
import scala.collection.immutable.Vector4;
import scala.collection.immutable.Vector5;
import scala.collection.immutable.Vector6;
import scala.collection.immutable.VectorStatics$;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.ReusableBuilder;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0005Ue\u0001\u0002\u0010 \u0005\u0019BQ!\u0011\u0001\u0005\u0002\tC\u0011\u0002\u0012\u0001A\u0002\u0003\u0005\u000b\u0015B#\t\u0013U\u0003\u0001\u0019!A!B\u00131\u0006\"C-\u0001\u0001\u0004\u0005\t\u0015)\u0003[\u0011%i\u0006\u00011A\u0001B\u0003&a\fC\u0005b\u0001\u0001\u0007\t\u0011)Q\u0005E\"1Q\r\u0001Q!\n\u0019DA\"\u001b\u0001\u0005\u0002\u0003\u0015\t\u0011!Q!\n)DA\"\u001c\u0001\u0005\u0002\u0003\u0015\t\u0011!Q!\n)DaA\u001c\u0001!B\u0013Q\u0007BB8\u0001A\u0003&!\u000e\u0003\u0004q\u0001\u0001&i!\u001d\u0005\u0006w\u0002!\t\u0005 \u0005\u0006{\u0002!\t\u0001 \u0005\u0007\u007f\u0002!\t!!\u0001\t\u000f\u0005-\u0001\u0001\"\u0001\u0002\u0002!9\u0011q\u0002\u0001\u0005\u0002\u0005E\u0001\u0002CA\n\u0001\u0011\u0005q$!\u0006\t\u0011\u0005u\u0001\u0001\"\u0001 \u0003?A\u0001\"!\b\u0001\t\u0003y\u0012Q\u0005\u0005\b\u0003o\u0001A\u0011AA\u001d\u0011!\ti\u0004\u0001Q\u0005\n\u0005}\u0002\u0002CA#\u0001\u0001&I!a\u0012\t\u000f\u00055\u0003\u0001\"\u0011\u0002P!A\u00111\f\u0001!\n\u0013\t\t\u0002\u0003\u0005\u0002^\u0001\u0001K\u0011BA0\u0011\u001d\tI\u0007\u0001C\u0001\u0003WBq!!\u001c\u0001\t\u0003\ny\u0007\u0003\u0005\u0002\u0002\u0002!\taHAB\u000551Vm\u0019;pe\n+\u0018\u000e\u001c3fe*\u0011\u0001%I\u0001\nS6lW\u000f^1cY\u0016T!AI\u0012\u0002\u0015\r|G\u000e\\3di&|gNC\u0001%\u0003\u0015\u00198-\u00197b\u0007\u0001)\"a\n\u001b\u0014\u0007\u0001AC\u0006\u0005\u0002*U5\t1%\u0003\u0002,G\t1\u0011I\\=SK\u001a\u0004B!\f\u00193{5\taF\u0003\u00020C\u00059Q.\u001e;bE2,\u0017BA\u0019/\u0005=\u0011V-^:bE2,')^5mI\u0016\u0014\bCA\u001a5\u0019\u0001!Q!\u000e\u0001C\u0002Y\u0012\u0011!Q\t\u0003oi\u0002\"!\u000b\u001d\n\u0005e\u001a#a\u0002(pi\"Lgn\u001a\t\u0003SmJ!\u0001P\u0012\u0003\u0007\u0005s\u0017\u0010E\u0002?\u007fIj\u0011aH\u0005\u0003\u0001~\u0011aAV3di>\u0014\u0018A\u0002\u001fj]&$h\bF\u0001D!\rq\u0004AM\u0001\u0003CZ\u0002\"A\u0012*\u000f\u0005\u001d\u0003fB\u0001%P\u001d\tIeJ\u0004\u0002K\u001b6\t1J\u0003\u0002MK\u00051AH]8pizJ\u0011\u0001J\u0005\u0003E\rJ!\u0001I\u0011\n\u0005E{\u0012\u0001\u0004,fGR|'/\u00138mS:,\u0017BA*U\u0005\u0011\t%O\u001d\u001c\u000b\u0005E{\u0012AA16!\t1u+\u0003\u0002Y)\n!\u0011I\u001d:6\u0003\t\tG\u0007\u0005\u0002G7&\u0011A\f\u0016\u0002\u0005\u0003J\u0014H'\u0001\u0002bgA\u0011aiX\u0005\u0003AR\u0013A!\u0011:sg\u0005\u0011\u0011M\r\t\u0003\r\u000eL!\u0001\u001a+\u0003\t\u0005\u0013(OM\u0001\u0003CF\u0002\"AR4\n\u0005!$&\u0001B!seF\naf]2bY\u0006$3m\u001c7mK\u000e$\u0018n\u001c8%S6lW\u000f^1cY\u0016$c+Z2u_J\u0014U/\u001b7eKJ$C\u0005\\3ocA\u0011\u0011f[\u0005\u0003Y\u000e\u00121!\u00138u\u0003E\u001a8-\u00197bI\r|G\u000e\\3di&|g\u000eJ5n[V$\u0018M\u00197fIY+7\r^8s\u0005VLG\u000eZ3sI\u0011bWM\u001c*fgR\faa\u001c4gg\u0016$\u0018!\u00023faRD\u0017AB:fi2+g\u000e\u0006\u0002skB\u0011\u0011f]\u0005\u0003i\u000e\u0012A!\u00168ji\")a\u000f\u0004a\u0001U\u0006\t\u0011\u000e\u000b\u0002\rqB\u0011\u0011&_\u0005\u0003u\u000e\u0012a!\u001b8mS:,\u0017!C6o_^t7+\u001b>f+\u0005Q\u0017\u0001B:ju\u0016D#A\u0004=\u0002\u000f%\u001cX)\u001c9usV\u0011\u00111\u0001\t\u0004S\u0005\u0015\u0011bAA\u0004G\t9!i\\8mK\u0006t\u0007FA\by\u0003!qwN\\#naRL\bF\u0001\ty\u0003\u0015\u0019G.Z1s)\u0005\u0011\u0018AC5oSR\u001c\u0006/\u0019:tKR)!/a\u0006\u0002\u001a!)QP\u0005a\u0001U\"1\u00111\u0004\nA\u0002I\nA!\u001a7f[\u0006A\u0011N\\5u\rJ|W\u000eF\u0002s\u0003CAa!a\t\u0014\u0001\u00041\u0017a\u00029sK\u001aL\u00070\r\u000b\u0005\u0003O\tI#D\u0001\u0001\u0011\u001d\tY\u0003\u0006a\u0001\u0003[\t\u0011A\u001e\u0019\u0005\u0003_\t\u0019\u0004\u0005\u0003?\u007f\u0005E\u0002cA\u001a\u00024\u0011Y\u0011QGA\u0015\u0003\u0003\u0005\tQ!\u00017\u0005\u0011yF%\r\u001d\u0002\r\u0005$Gm\u00148f)\u0011\t9#a\u000f\t\r\u0005mQ\u00031\u00013\u0003\u001d\tG\rZ!seF\"2A]A!\u0011\u0019\t\u0019E\u0006a\u0001M\u0006!A-\u0019;b\u0003%\tG\r\u001a,fGR|'\u000f\u0006\u0003\u0002(\u0005%\u0003BBA&/\u0001\u0007Q(\u0001\u0002yg\u00061\u0011\r\u001a3BY2$B!a\n\u0002R!9\u00111\n\rA\u0002\u0005M\u0003#BA+\u0003/\u0012T\"A\u0011\n\u0007\u0005e\u0013E\u0001\u0007Ji\u0016\u0014\u0018M\u00197f\u001f:\u001cW-A\u0004bIZ\fgnY3\u0002\u0011\u0005$g/\u00198dKF\"RA]A1\u0003KBa!a\u0019\u001b\u0001\u0004Q\u0017aA5eq\"1\u0011q\r\u000eA\u0002)\f1\u0001_8s\u0003\u0019\u0011Xm];miR\tQ(\u0001\u0005u_N#(/\u001b8h)\t\t\t\b\u0005\u0003\u0002t\u0005md\u0002BA;\u0003o\u0002\"AS\u0012\n\u0007\u0005e4%\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003{\nyH\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003s\u001a\u0013aB4fi\u0012\u000bG/Y\u000b\u0003\u0003\u000b\u0003R!KAD\u0003\u0017K1!!#$\u0005\u0015\t%O]1za\u0011\ti)!%\u0011\u000b%\n9)a$\u0011\u0007M\n\t\n\u0002\u0006\u0002\u0014v\t\t\u0011!A\u0003\u0002Y\u0012Aa\u0018\u00133k\u0001")
public final class VectorBuilder<A>
implements ReusableBuilder<A, Vector<A>> {
    private Object[][][][][][] a6;
    private Object[][][][][] a5;
    private Object[][][][] a4;
    private Object[][][] a3;
    private Object[][] a2;
    private Object[] a1 = new Object[32];
    public int scala$collection$immutable$VectorBuilder$$len1 = 0;
    public int scala$collection$immutable$VectorBuilder$$lenRest = 0;
    private int offset = 0;
    private int depth = 1;

    @Override
    public void sizeHint(int size) {
        Builder.sizeHint$(this, size);
    }

    @Override
    public final void sizeHint(IterableOnce<?> coll, int delta) {
        Builder.sizeHint$(this, coll, delta);
    }

    @Override
    public final int sizeHint$default$2() {
        return Builder.sizeHint$default$2$(this);
    }

    @Override
    public final void sizeHintBounded(int size, Iterable<?> boundingColl) {
        Builder.sizeHintBounded$(this, size, boundingColl);
    }

    @Override
    public <NewTo> Builder<A, NewTo> mapResult(Function1<Vector<A>, NewTo> f) {
        return Builder.mapResult$(this, f);
    }

    @Override
    public final Growable<A> $plus$eq(A elem) {
        return Growable.$plus$eq$(this, elem);
    }

    @Override
    public final Growable<A> $plus$eq(A elem1, A elem2, Seq<A> elems) {
        return Growable.$plus$eq$(this, elem1, elem2, elems);
    }

    @Override
    public final Growable<A> $plus$plus$eq(IterableOnce<A> xs) {
        return Growable.$plus$plus$eq$(this, xs);
    }

    private final void setLen(int i) {
        this.scala$collection$immutable$VectorBuilder$$len1 = i & 0x1F;
        this.scala$collection$immutable$VectorBuilder$$lenRest = i - this.scala$collection$immutable$VectorBuilder$$len1;
    }

    @Override
    public int knownSize() {
        return this.scala$collection$immutable$VectorBuilder$$len1 + this.scala$collection$immutable$VectorBuilder$$lenRest - this.offset;
    }

    public int size() {
        return this.knownSize();
    }

    public boolean isEmpty() {
        return this.knownSize() == 0;
    }

    public boolean nonEmpty() {
        return this.knownSize() != 0;
    }

    @Override
    public void clear() {
        this.a6 = null;
        this.a5 = null;
        this.a4 = null;
        this.a3 = null;
        this.a2 = null;
        this.a1 = new Object[32];
        this.scala$collection$immutable$VectorBuilder$$len1 = 0;
        this.scala$collection$immutable$VectorBuilder$$lenRest = 0;
        this.offset = 0;
        this.depth = 1;
    }

    public void initSparse(int size, A elem) {
        this.scala$collection$immutable$VectorBuilder$$len1 = size & 0x1F;
        this.scala$collection$immutable$VectorBuilder$$lenRest = size - this.scala$collection$immutable$VectorBuilder$$len1;
        Arrays.fill(this.a1, elem);
        if (size > 32) {
            this.a2 = new Object[32][];
            Arrays.fill((Object[])this.a2, this.a1);
            if (size > 1024) {
                this.a3 = new Object[32][][];
                Arrays.fill((Object[])this.a3, this.a2);
                if (size > 32768) {
                    this.a4 = new Object[32][][][];
                    Arrays.fill((Object[])this.a4, this.a3);
                    if (size > 0x100000) {
                        this.a5 = new Object[32][][][][];
                        Arrays.fill((Object[])this.a5, this.a4);
                        if (size > 0x2000000) {
                            this.a6 = new Object[64][][][][][];
                            Arrays.fill((Object[])this.a6, this.a5);
                            this.depth = 6;
                            return;
                        }
                        this.depth = 5;
                        return;
                    }
                    this.depth = 4;
                    return;
                }
                this.depth = 3;
                return;
            }
            this.depth = 2;
            return;
        }
        this.depth = 1;
    }

    /*
     * WARNING - void declaration
     */
    public void initFrom(Object[] prefix1) {
        void copyOrUse_end;
        this.depth = 1;
        int setLen_i = prefix1.length;
        this.scala$collection$immutable$VectorBuilder$$len1 = setLen_i & 0x1F;
        this.scala$collection$immutable$VectorBuilder$$lenRest = setLen_i - this.scala$collection$immutable$VectorBuilder$$len1;
        int n = 32;
        int copyOrUse_start = 0;
        Object[] objectArray = this.a1 = copyOrUse_start == 0 && copyOrUse_end == prefix1.length ? prefix1 : Arrays.copyOfRange(prefix1, copyOrUse_start, (int)copyOrUse_end);
        if (this.scala$collection$immutable$VectorBuilder$$len1 == 0 && this.scala$collection$immutable$VectorBuilder$$lenRest > 0) {
            this.scala$collection$immutable$VectorBuilder$$len1 = 32;
            this.scala$collection$immutable$VectorBuilder$$lenRest -= 32;
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    public VectorBuilder<A> initFrom(Vector<?> v) {
        int n = v.vectorSliceCount();
        switch (n) {
            case 0: {
                break;
            }
            case 1: {
                void copyOrUse_end;
                void copyOrUse_start;
                Vector1 v1 = (Vector1)v;
                this.depth = 1;
                int setLen_i = v1.prefix1().length;
                this.scala$collection$immutable$VectorBuilder$$len1 = setLen_i & 0x1F;
                this.scala$collection$immutable$VectorBuilder$$lenRest = setLen_i - this.scala$collection$immutable$VectorBuilder$$len1;
                int n2 = 32;
                boolean bl = false;
                Object[] copyOrUse_a = v1.prefix1();
                Object var25_7 = null;
                this.a1 = copyOrUse_start == false && copyOrUse_end == copyOrUse_a.length ? copyOrUse_a : Arrays.copyOfRange(copyOrUse_a, (int)copyOrUse_start, (int)copyOrUse_end);
                break;
            }
            case 3: {
                void copyOrUse_end;
                void copyOrUse_start;
                Vector2 v2 = (Vector2)v;
                Object[][] d2 = v2.data2();
                int n3 = 32;
                boolean bl = false;
                Object[] copyOrUse_a = v2.suffix1();
                Object var28_12 = null;
                this.a1 = copyOrUse_start == false && copyOrUse_end == copyOrUse_a.length ? copyOrUse_a : Arrays.copyOfRange(copyOrUse_a, (int)copyOrUse_start, (int)copyOrUse_end);
                this.depth = 2;
                this.offset = 32 - v2.len1();
                int setLen_i = v2.length0() + this.offset;
                this.scala$collection$immutable$VectorBuilder$$len1 = setLen_i & 0x1F;
                this.scala$collection$immutable$VectorBuilder$$lenRest = setLen_i - this.scala$collection$immutable$VectorBuilder$$len1;
                this.a2 = new Object[32][];
                this.a2[0] = v2.prefix1();
                System.arraycopy(d2, 0, this.a2, 1, d2.length);
                this.a2[d2.length + 1] = this.a1;
                break;
            }
            case 5: {
                void copyOrUse_end;
                void copyOrUse_start;
                Vector3 v3 = (Vector3)v;
                Object[][][] d3 = v3.data3();
                Object[][] s2 = v3.suffix2();
                int n4 = 32;
                boolean bl = false;
                Object[] copyOrUse_a = v3.suffix1();
                Object var32_19 = null;
                this.a1 = copyOrUse_start == false && copyOrUse_end == copyOrUse_a.length ? copyOrUse_a : Arrays.copyOfRange(copyOrUse_a, (int)copyOrUse_start, (int)copyOrUse_end);
                this.depth = 3;
                this.offset = 1024 - v3.len12();
                int setLen_i = v3.length0() + this.offset;
                this.scala$collection$immutable$VectorBuilder$$len1 = setLen_i & 0x1F;
                this.scala$collection$immutable$VectorBuilder$$lenRest = setLen_i - this.scala$collection$immutable$VectorBuilder$$len1;
                this.a3 = new Object[32][][];
                this.a3[0] = VectorStatics$.MODULE$.copyPrepend(v3.prefix1(), (T[])v3.prefix2());
                System.arraycopy(d3, 0, this.a3, 1, d3.length);
                this.a2 = (Object[][])Arrays.copyOf(s2, 32);
                this.a3[d3.length + 1] = this.a2;
                this.a2[s2.length] = this.a1;
                break;
            }
            case 7: {
                void copyOrUse_end;
                void copyOrUse_start;
                Vector4 v4 = (Vector4)v;
                Object[][][][] d4 = v4.data4();
                Object[][][] s3 = v4.suffix3();
                Object[][] s2 = v4.suffix2();
                int n5 = 32;
                boolean bl = false;
                Object[] copyOrUse_a = v4.suffix1();
                Object var36_27 = null;
                this.a1 = copyOrUse_start == false && copyOrUse_end == copyOrUse_a.length ? copyOrUse_a : Arrays.copyOfRange(copyOrUse_a, (int)copyOrUse_start, (int)copyOrUse_end);
                this.depth = 4;
                this.offset = 32768 - v4.len123();
                int setLen_i = v4.length0() + this.offset;
                this.scala$collection$immutable$VectorBuilder$$len1 = setLen_i & 0x1F;
                this.scala$collection$immutable$VectorBuilder$$lenRest = setLen_i - this.scala$collection$immutable$VectorBuilder$$len1;
                this.a4 = new Object[32][][][];
                this.a4[0] = VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(v4.prefix1(), (T[])v4.prefix2()), (T[])v4.prefix3());
                System.arraycopy(d4, 0, this.a4, 1, d4.length);
                this.a3 = (Object[][][])Arrays.copyOf(s3, 32);
                this.a2 = (Object[][])Arrays.copyOf(s2, 32);
                this.a4[d4.length + 1] = this.a3;
                this.a3[s3.length] = this.a2;
                this.a2[s2.length] = this.a1;
                break;
            }
            case 9: {
                void copyOrUse_end;
                void copyOrUse_start;
                Vector5 v5 = (Vector5)v;
                Object[][][][][] d5 = v5.data5();
                Object[][][][] s4 = v5.suffix4();
                Object[][][] s3 = v5.suffix3();
                Object[][] s2 = v5.suffix2();
                int n6 = 32;
                boolean bl = false;
                Object[] copyOrUse_a = v5.suffix1();
                Object var40_36 = null;
                this.a1 = copyOrUse_start == false && copyOrUse_end == copyOrUse_a.length ? copyOrUse_a : Arrays.copyOfRange(copyOrUse_a, (int)copyOrUse_start, (int)copyOrUse_end);
                this.depth = 5;
                this.offset = 0x100000 - v5.len1234();
                int setLen_i = v5.length0() + this.offset;
                this.scala$collection$immutable$VectorBuilder$$len1 = setLen_i & 0x1F;
                this.scala$collection$immutable$VectorBuilder$$lenRest = setLen_i - this.scala$collection$immutable$VectorBuilder$$len1;
                this.a5 = new Object[32][][][][];
                this.a5[0] = VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(v5.prefix1(), (T[])v5.prefix2()), (T[])v5.prefix3()), (T[])v5.prefix4());
                System.arraycopy(d5, 0, this.a5, 1, d5.length);
                this.a4 = (Object[][][][])Arrays.copyOf(s4, 32);
                this.a3 = (Object[][][])Arrays.copyOf(s3, 32);
                this.a2 = (Object[][])Arrays.copyOf(s2, 32);
                this.a5[d5.length + 1] = this.a4;
                this.a4[s4.length] = this.a3;
                this.a3[s3.length] = this.a2;
                this.a2[s2.length] = this.a1;
                break;
            }
            case 11: {
                void copyOrUse_end;
                void copyOrUse_start;
                Vector6 v6 = (Vector6)v;
                Object[][][][][][] d6 = v6.data6();
                Object[][][][][] s5 = v6.suffix5();
                Object[][][][] s4 = v6.suffix4();
                Object[][][] s3 = v6.suffix3();
                Object[][] s2 = v6.suffix2();
                int n7 = 32;
                boolean bl = false;
                Object[] copyOrUse_a = v6.suffix1();
                Object var44_46 = null;
                this.a1 = copyOrUse_start == false && copyOrUse_end == copyOrUse_a.length ? copyOrUse_a : Arrays.copyOfRange(copyOrUse_a, (int)copyOrUse_start, (int)copyOrUse_end);
                this.depth = 6;
                this.offset = 0x2000000 - v6.len12345();
                int setLen_i = v6.length0() + this.offset;
                this.scala$collection$immutable$VectorBuilder$$len1 = setLen_i & 0x1F;
                this.scala$collection$immutable$VectorBuilder$$lenRest = setLen_i - this.scala$collection$immutable$VectorBuilder$$len1;
                this.a6 = new Object[32][][][][][];
                this.a6[0] = VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(VectorStatics$.MODULE$.copyPrepend(v6.prefix1(), (T[])v6.prefix2()), (T[])v6.prefix3()), (T[])v6.prefix4()), (T[])v6.prefix5());
                System.arraycopy(d6, 0, this.a6, 1, d6.length);
                this.a5 = (Object[][][][][])Arrays.copyOf(s5, 32);
                this.a4 = (Object[][][][])Arrays.copyOf(s4, 32);
                this.a3 = (Object[][][])Arrays.copyOf(s3, 32);
                this.a2 = (Object[][])Arrays.copyOf(s2, 32);
                this.a6[d6.length + 1] = this.a5;
                this.a5[s5.length] = this.a4;
                this.a4[s4.length] = this.a3;
                this.a3[s3.length] = this.a2;
                this.a2[s2.length] = this.a1;
                break;
            }
            default: {
                throw new MatchError(n);
            }
        }
        if (this.scala$collection$immutable$VectorBuilder$$len1 == 0 && this.scala$collection$immutable$VectorBuilder$$lenRest > 0) {
            this.scala$collection$immutable$VectorBuilder$$len1 = 32;
            this.scala$collection$immutable$VectorBuilder$$lenRest -= 32;
        }
        return this;
    }

    @Override
    public VectorBuilder<A> addOne(A elem) {
        if (this.scala$collection$immutable$VectorBuilder$$len1 == 32) {
            this.advance();
        }
        this.a1[this.scala$collection$immutable$VectorBuilder$$len1] = elem;
        ++this.scala$collection$immutable$VectorBuilder$$len1;
        return this;
    }

    private void addArr1(Object[] data) {
        int dl = data.length;
        if (dl > 0) {
            if (this.scala$collection$immutable$VectorBuilder$$len1 == 32) {
                this.advance();
            }
            int copy1 = Math.min(32 - this.scala$collection$immutable$VectorBuilder$$len1, dl);
            int copy2 = dl - copy1;
            System.arraycopy(data, 0, this.a1, this.scala$collection$immutable$VectorBuilder$$len1, copy1);
            this.scala$collection$immutable$VectorBuilder$$len1 += copy1;
            if (copy2 > 0) {
                this.advance();
                System.arraycopy(data, copy1, this.a1, 0, copy2);
                this.scala$collection$immutable$VectorBuilder$$len1 += copy2;
                return;
            }
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    private VectorBuilder<A> addVector(Vector<A> xs) {
        int sliceCount = xs.vectorSliceCount();
        block3: for (int sliceIdx = 0; sliceIdx < sliceCount; ++sliceIdx) {
            Object[] slice = xs.vectorSlice(sliceIdx);
            int vectorSliceDim_c = sliceCount / 2;
            int n = vectorSliceDim_c + 1 - Math.abs(sliceIdx - vectorSliceDim_c);
            switch (n) {
                case 1: {
                    this.addArr1(slice);
                    continue block3;
                }
                default: {
                    int foreachRec_i;
                    void foreachRec_level;
                    Function1<Object[], Object> & Serializable intersect = (Function1<Object[], Object> & Serializable)data -> {
                        this.addArr1(data);
                        return BoxedUnit.UNIT;
                    };
                    int n2 = n - 2;
                    VectorStatics$ foreachRec_this = VectorStatics$.MODULE$;
                    int foreachRec_len = slice.length;
                    if (foreachRec_level == false) {
                        for (foreachRec_i = 0; foreachRec_i < foreachRec_len; ++foreachRec_i) {
                            Object[] objectArray = (Object[])slice[foreachRec_i];
                            this.addArr1(objectArray);
                        }
                    } else {
                        void foreachRec_l = foreachRec_level - true;
                        while (foreachRec_i < foreachRec_len) {
                            void foreachRec_f;
                            foreachRec_this.foreachRec((int)foreachRec_l, (T[])((Object[])slice[foreachRec_i]), foreachRec_f);
                            ++foreachRec_i;
                        }
                    }
                    Object var7_7 = null;
                    intersect = null;
                }
            }
        }
        return this;
    }

    @Override
    public VectorBuilder<A> addAll(IterableOnce<A> xs) {
        if (xs instanceof Vector) {
            Vector vector = (Vector)xs;
            if (this.scala$collection$immutable$VectorBuilder$$len1 == 0 && this.scala$collection$immutable$VectorBuilder$$lenRest == 0) {
                return this.initFrom(vector);
            }
            return this.addVector(vector);
        }
        return (VectorBuilder)Growable.addAll$(this, xs);
    }

    private void advance() {
        int idx = this.scala$collection$immutable$VectorBuilder$$lenRest + 32;
        int xor = idx ^ this.scala$collection$immutable$VectorBuilder$$lenRest;
        this.scala$collection$immutable$VectorBuilder$$lenRest = idx;
        this.scala$collection$immutable$VectorBuilder$$len1 = 0;
        this.advance1(idx, xor);
    }

    private void advance1(int idx, int xor) {
        if (xor < 1024) {
            if (this.depth == 1) {
                this.a2 = new Object[32][];
                this.a2[0] = this.a1;
                ++this.depth;
            }
            this.a1 = new Object[32];
            this.a2[idx >>> 5 & 0x1F] = this.a1;
            return;
        }
        if (xor < 32768) {
            if (this.depth == 2) {
                this.a3 = new Object[32][][];
                this.a3[0] = this.a2;
                ++this.depth;
            }
            this.a1 = new Object[32];
            this.a2 = new Object[32][];
            this.a2[idx >>> 5 & 0x1F] = this.a1;
            this.a3[idx >>> 10 & 0x1F] = this.a2;
            return;
        }
        if (xor < 0x100000) {
            if (this.depth == 3) {
                this.a4 = new Object[32][][][];
                this.a4[0] = this.a3;
                ++this.depth;
            }
            this.a1 = new Object[32];
            this.a2 = new Object[32][];
            this.a3 = new Object[32][][];
            this.a2[idx >>> 5 & 0x1F] = this.a1;
            this.a3[idx >>> 10 & 0x1F] = this.a2;
            this.a4[idx >>> 15 & 0x1F] = this.a3;
            return;
        }
        if (xor < 0x2000000) {
            if (this.depth == 4) {
                this.a5 = new Object[32][][][][];
                this.a5[0] = this.a4;
                ++this.depth;
            }
            this.a1 = new Object[32];
            this.a2 = new Object[32][];
            this.a3 = new Object[32][][];
            this.a4 = new Object[32][][][];
            this.a2[idx >>> 5 & 0x1F] = this.a1;
            this.a3[idx >>> 10 & 0x1F] = this.a2;
            this.a4[idx >>> 15 & 0x1F] = this.a3;
            this.a5[idx >>> 20 & 0x1F] = this.a4;
            return;
        }
        if (xor < 0x40000000) {
            if (this.depth == 5) {
                this.a6 = new Object[64][][][][][];
                this.a6[0] = this.a5;
                ++this.depth;
            }
            this.a1 = new Object[32];
            this.a2 = new Object[32][];
            this.a3 = new Object[32][][];
            this.a4 = new Object[32][][][];
            this.a5 = new Object[32][][][][];
            this.a2[idx >>> 5 & 0x1F] = this.a1;
            this.a3[idx >>> 10 & 0x1F] = this.a2;
            this.a4[idx >>> 15 & 0x1F] = this.a3;
            this.a5[idx >>> 20 & 0x1F] = this.a4;
            this.a6[idx >>> 25 & 0x1F] = this.a5;
            return;
        }
        throw new IllegalArgumentException(new StringBuilder(50).append("advance1(").append(idx).append(", ").append(xor).append("): a1=").append(this.a1).append(", a2=").append(this.a2).append(", a3=").append(this.a3).append(", a4=").append(this.a4).append(", a5=").append(this.a5).append(", a6=").append(this.a6).append(", depth=").append(this.depth).toString());
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Vector<A> result() {
        void copyIfDifferentSize_len;
        int len = this.scala$collection$immutable$VectorBuilder$$len1 + this.scala$collection$immutable$VectorBuilder$$lenRest;
        int realLen = len - this.offset;
        if (realLen == 0) {
            return Vector$.MODULE$.empty();
        }
        if (len <= 32) {
            if (realLen == 32) {
                return new Vector1(this.a1);
            }
            return new Vector1(Arrays.copyOf(this.a1, realLen));
        }
        if (len <= 1024) {
            void copyIfDifferentSize_len2;
            int i1 = len - 1 & 0x1F;
            int i2 = len - 1 >>> 5;
            Object[][] data = (Object[][])Arrays.copyOfRange(this.a2, 1, i2);
            Object[] prefix1 = this.a2[0];
            int n = i1 + 1;
            Object[] copyIfDifferentSize_a = this.a2[i2];
            Object[] objectArray = copyIfDifferentSize_a.length == copyIfDifferentSize_len2 ? copyIfDifferentSize_a : Arrays.copyOf(copyIfDifferentSize_a, (int)copyIfDifferentSize_len2);
            Object var72_8 = null;
            Object[] suffix1 = objectArray;
            return new Vector2(prefix1, 32 - this.offset, data, suffix1, realLen);
        }
        if (len <= 32768) {
            void copyIfDifferentSize_len3;
            int i1 = len - 1 & 0x1F;
            int i2 = len - 1 >>> 5 & 0x1F;
            int i3 = len - 1 >>> 10;
            Object[][][] data = (Object[][][])Arrays.copyOfRange(this.a3, 1, i3);
            Object[][] copyTail_a = this.a3[0];
            Object var74_14 = null;
            Object[][] prefix2 = (Object[][])Arrays.copyOfRange(copyTail_a, 1, copyTail_a.length);
            Object[] prefix1 = this.a3[0][0];
            Object[][] suffix2 = (Object[][])Arrays.copyOf(this.a3[i3], i2);
            int n = i1 + 1;
            Object[] copyIfDifferentSize_a = this.a3[i3][i2];
            Object[] objectArray = copyIfDifferentSize_a.length == copyIfDifferentSize_len3 ? copyIfDifferentSize_a : Arrays.copyOf(copyIfDifferentSize_a, (int)copyIfDifferentSize_len3);
            Object var75_19 = null;
            Object[] suffix1 = objectArray;
            int len1 = prefix1.length;
            int len12 = len1 + prefix2.length * 32;
            return new Vector3(prefix1, len1, prefix2, len12, data, suffix2, suffix1, realLen);
        }
        if (len <= 0x100000) {
            void copyIfDifferentSize_len4;
            int i1 = len - 1 & 0x1F;
            int i2 = len - 1 >>> 5 & 0x1F;
            int i3 = len - 1 >>> 10 & 0x1F;
            int i4 = len - 1 >>> 15;
            Object[][][][] data = (Object[][][][])Arrays.copyOfRange(this.a4, 1, i4);
            Object[][][] copyTail_a = this.a4[0];
            Object var77_28 = null;
            Object[][][] prefix3 = (Object[][][])Arrays.copyOfRange(copyTail_a, 1, copyTail_a.length);
            Object[] copyTail_a2 = (Object[])this.a4[0][0];
            Object var78_30 = null;
            Object[][] prefix2 = (Object[][])Arrays.copyOfRange(copyTail_a2, 1, copyTail_a2.length);
            Object[] prefix1 = this.a4[0][0][0];
            Object[][][] suffix3 = (Object[][][])Arrays.copyOf(this.a4[i4], i3);
            Object[][] suffix2 = (Object[][])Arrays.copyOf((Object[])this.a4[i4][i3], i2);
            int n = i1 + 1;
            Object[] copyIfDifferentSize_a = this.a4[i4][i3][i2];
            Object[] objectArray = copyIfDifferentSize_a.length == copyIfDifferentSize_len4 ? copyIfDifferentSize_a : Arrays.copyOf(copyIfDifferentSize_a, (int)copyIfDifferentSize_len4);
            Object var79_36 = null;
            Object[] suffix1 = objectArray;
            int len1 = prefix1.length;
            int len12 = len1 + prefix2.length * 32;
            int len123 = len12 + prefix3.length * 1024;
            return new Vector4(prefix1, len1, prefix2, len12, prefix3, len123, data, suffix3, suffix2, suffix1, realLen);
        }
        if (len <= 0x2000000) {
            void copyIfDifferentSize_len5;
            int i1 = len - 1 & 0x1F;
            int i2 = len - 1 >>> 5 & 0x1F;
            int i3 = len - 1 >>> 10 & 0x1F;
            int i4 = len - 1 >>> 15 & 0x1F;
            int i5 = len - 1 >>> 20;
            Object[][][][][] data = (Object[][][][][])Arrays.copyOfRange(this.a5, 1, i5);
            Object[][][][] copyTail_a = this.a5[0];
            Object var81_47 = null;
            Object[][][][] prefix4 = (Object[][][][])Arrays.copyOfRange(copyTail_a, 1, copyTail_a.length);
            Object[] copyTail_a3 = (Object[])this.a5[0][0];
            Object var82_49 = null;
            Object[][][] prefix3 = (Object[][][])Arrays.copyOfRange(copyTail_a3, 1, copyTail_a3.length);
            Object[] copyTail_a4 = (Object[])this.a5[0][0][0];
            Object var83_51 = null;
            Object[][] prefix2 = (Object[][])Arrays.copyOfRange(copyTail_a4, 1, copyTail_a4.length);
            Object[] prefix1 = this.a5[0][0][0][0];
            Object[][][][] suffix4 = (Object[][][][])Arrays.copyOf(this.a5[i5], i4);
            Object[][][] suffix3 = (Object[][][])Arrays.copyOf((Object[])this.a5[i5][i4], i3);
            Object[][] suffix2 = (Object[][])Arrays.copyOf((Object[])this.a5[i5][i4][i3], i2);
            int n = i1 + 1;
            Object[] copyIfDifferentSize_a = this.a5[i5][i4][i3][i2];
            Object[] objectArray = copyIfDifferentSize_a.length == copyIfDifferentSize_len5 ? copyIfDifferentSize_a : Arrays.copyOf(copyIfDifferentSize_a, (int)copyIfDifferentSize_len5);
            Object var84_58 = null;
            Object[] suffix1 = objectArray;
            int len1 = prefix1.length;
            int len12 = len1 + prefix2.length * 32;
            int len123 = len12 + prefix3.length * 1024;
            int len1234 = len123 + prefix4.length * 32768;
            return new Vector5(prefix1, len1, prefix2, len12, prefix3, len123, prefix4, len1234, data, suffix4, suffix3, suffix2, suffix1, realLen);
        }
        int i1 = len - 1 & 0x1F;
        int i2 = len - 1 >>> 5 & 0x1F;
        int i3 = len - 1 >>> 10 & 0x1F;
        int i4 = len - 1 >>> 15 & 0x1F;
        int i5 = len - 1 >>> 20 & 0x1F;
        int i6 = len - 1 >>> 25;
        Object[][][][][][] data = (Object[][][][][][])Arrays.copyOfRange(this.a6, 1, i6);
        Object[][][][][] copyTail_a = this.a6[0];
        Object var86_71 = null;
        Object[][][][][] prefix5 = (Object[][][][][])Arrays.copyOfRange(copyTail_a, 1, copyTail_a.length);
        Object[] copyTail_a5 = (Object[])this.a6[0][0];
        Object var87_73 = null;
        Object[][][][] prefix4 = (Object[][][][])Arrays.copyOfRange(copyTail_a5, 1, copyTail_a5.length);
        Object[] copyTail_a6 = (Object[])this.a6[0][0][0];
        Object var88_75 = null;
        Object[][][] prefix3 = (Object[][][])Arrays.copyOfRange(copyTail_a6, 1, copyTail_a6.length);
        Object[] copyTail_a7 = (Object[])this.a6[0][0][0][0];
        Object var89_77 = null;
        Object[][] prefix2 = (Object[][])Arrays.copyOfRange(copyTail_a7, 1, copyTail_a7.length);
        Object[] prefix1 = this.a6[0][0][0][0][0];
        Object[][][][][] suffix5 = (Object[][][][][])Arrays.copyOf(this.a6[i6], i5);
        Object[][][][] suffix4 = (Object[][][][])Arrays.copyOf((Object[])this.a6[i6][i5], i4);
        Object[][][] suffix3 = (Object[][][])Arrays.copyOf((Object[])this.a6[i6][i5][i4], i3);
        Object[][] suffix2 = (Object[][])Arrays.copyOf((Object[])this.a6[i6][i5][i4][i3], i2);
        int n = i1 + 1;
        Object[] copyIfDifferentSize_a = this.a6[i6][i5][i4][i3][i2];
        Object[] objectArray = copyIfDifferentSize_a.length == copyIfDifferentSize_len ? copyIfDifferentSize_a : Arrays.copyOf(copyIfDifferentSize_a, (int)copyIfDifferentSize_len);
        Object var90_85 = null;
        Object[] suffix1 = objectArray;
        int len1 = prefix1.length;
        int len12 = len1 + prefix2.length * 32;
        int len123 = len12 + prefix3.length * 1024;
        int len1234 = len123 + prefix4.length * 32768;
        int len12345 = len1234 + prefix5.length * 0x100000;
        return new Vector6(prefix1, len1, prefix2, len12, prefix3, len123, prefix4, len1234, prefix5, len12345, data, suffix5, suffix4, suffix3, suffix2, suffix1, realLen);
    }

    public String toString() {
        return new StringBuilder(47).append("VectorBuilder(len1=").append(this.scala$collection$immutable$VectorBuilder$$len1).append(", lenRest=").append(this.scala$collection$immutable$VectorBuilder$$lenRest).append(", offset=").append(this.offset).append(", depth=").append(this.depth).append(")").toString();
    }

    public Object[] getData() {
        return new Object[][]{this.a1, this.a2, this.a3, this.a4, this.a5, this.a6};
    }
}

