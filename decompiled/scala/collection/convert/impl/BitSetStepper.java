/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import scala.collection.BitSetOps;
import scala.collection.IntStepper;
import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.collection.convert.impl.BitSetStepper$;
import scala.collection.convert.impl.InOrderStepperBase;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005mc!B\u000e\u001d\u0005\u0001\"\u0003\u0002\u0003\u0018\u0001\u0005\u0003\u0007I\u0011\u0002\u0019\t\u0011\t\u0003!\u00111A\u0005\n\rC\u0001\"\u000f\u0001\u0003\u0002\u0003\u0006K!\r\u0005\t\u0013\u0002\u0011\t\u0019!C\u0005\u0015\"Aa\n\u0001BA\u0002\u0013%q\n\u0003\u0005R\u0001\t\u0005\t\u0015)\u0003L\u0011!\u0011\u0006A!a\u0001\n\u0013Q\u0005\u0002C*\u0001\u0005\u0003\u0007I\u0011\u0002+\t\u0011Y\u0003!\u0011!Q!\n-C\u0001b\u0016\u0001\u0003\u0002\u0003\u0006I\u0001\u0017\u0005\t7\u0002\u0011\t\u0011)A\u00051\"AA\f\u0001BA\u0002\u0013%Q\f\u0003\u0005_\u0001\t\u0005\r\u0011\"\u0003`\u0011!\t\u0007A!A!B\u0013A\u0006\"\u00022\u0001\t\u0003\u0019\u0007b\u00028\u0001\u0001\u0004%\tb\u001c\u0005\bg\u0002\u0001\r\u0011\"\u0005u\u0011\u00191\b\u0001)Q\u0005a\")q\u000f\u0001C\tq\"9\u0011\u0011\u0001\u0001\u0005\u0002\u0005\r\u0001\u0002CA\u0005\u0001\u0001&I!a\u0003\t\u000f\u0005]\u0001\u0001\"\u0001\u0002\u001a\u001dA\u00111\u0004\u000f\t\u0002\u0001\niBB\u0004\u001c9!\u0005\u0001%a\b\t\r\tDB\u0011AA\u0014\u0011\u001d\t\u0019\u0002\u0007C\u0001\u0003S\u0011QBQ5u'\u0016$8\u000b^3qa\u0016\u0014(BA\u000f\u001f\u0003\u0011IW\u000e\u001d7\u000b\u0005}\u0001\u0013aB2p]Z,'\u000f\u001e\u0006\u0003C\t\n!bY8mY\u0016\u001cG/[8o\u0015\u0005\u0019\u0013!B:dC2\f7c\u0001\u0001&SA!aeJ\u0015.\u001b\u0005a\u0012B\u0001\u0015\u001d\u0005IIen\u0014:eKJ\u001cF/\u001a9qKJ\u0014\u0015m]3\u0011\u0005)ZS\"\u0001\u0011\n\u00051\u0002#AC%oiN#X\r\u001d9feB\u0011a\u0005A\u0001\u000bk:$WM\u001d7zS:<7\u0001A\u000b\u0002cA\u0012!g\u000e\t\u0004UM*\u0014B\u0001\u001b!\u0005%\u0011\u0015\u000e^*fi>\u00038\u000f\u0005\u00027o1\u0001A!\u0003\u001d\u0004\u0003\u0003\u0005\tQ!\u0001;\u0005\ryF%M\u0001\fk:$WM\u001d7zS:<\u0007%\u0005\u0002<\u007fA\u0011A(P\u0007\u0002E%\u0011aH\t\u0002\b\u001d>$\b.\u001b8h!\ta\u0004)\u0003\u0002BE\t\u0019\u0011I\\=\u0002\u001dUtG-\u001a:ms&twm\u0018\u0013fcR\u0011Ai\u0012\t\u0003y\u0015K!A\u0012\u0012\u0003\tUs\u0017\u000e\u001e\u0005\b\u0011\n\t\t\u00111\u00012\u0003\rAH%M\u0001\u0007G\u0006\u001c\u0007.\u001a\u0019\u0016\u0003-\u0003\"\u0001\u0010'\n\u00055\u0013#\u0001\u0002'p]\u001e\f!bY1dQ\u0016\u0004t\fJ3r)\t!\u0005\u000bC\u0004I\u000b\u0005\u0005\t\u0019A&\u0002\u000f\r\f7\r[31A\u000511-Y2iKF\n!bY1dQ\u0016\ft\fJ3r)\t!U\u000bC\u0004I\u0011\u0005\u0005\t\u0019A&\u0002\u000f\r\f7\r[32A\u0005\u0019q,\u001b\u0019\u0011\u0005qJ\u0016B\u0001.#\u0005\rIe\u000e^\u0001\u0004?&t\u0015AC2bG\",\u0017J\u001c3fqV\t\u0001,\u0001\bdC\u000eDW-\u00138eKb|F%Z9\u0015\u0005\u0011\u0003\u0007b\u0002%\u000e\u0003\u0003\u0005\r\u0001W\u0001\fG\u0006\u001c\u0007.Z%oI\u0016D\b%\u0001\u0004=S:LGO\u0010\u000b\b[\u0011L'n\u001b7n\u0011\u0015qs\u00021\u0001fa\t1\u0007\u000eE\u0002+g\u001d\u0004\"A\u000e5\u0005\u0013a\"\u0017\u0011!A\u0001\u0006\u0003Q\u0004\"B%\u0010\u0001\u0004Y\u0005\"\u0002*\u0010\u0001\u0004Y\u0005\"B,\u0010\u0001\u0004A\u0006\"B.\u0010\u0001\u0004A\u0006\"\u0002/\u0010\u0001\u0004A\u0016!\u00024pk:$W#\u00019\u0011\u0005q\n\u0018B\u0001:#\u0005\u001d\u0011un\u001c7fC:\f\u0011BZ8v]\u0012|F%Z9\u0015\u0005\u0011+\bb\u0002%\u0012\u0003\u0003\u0005\r\u0001]\u0001\u0007M>,h\u000e\u001a\u0011\u0002\u0011\u0019Lg\u000e\u001a(fqR$\u0012\u0001\u001d\u0015\u0003'i\u0004\"a\u001f@\u000e\u0003qT!! \u0012\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002\u0000y\n9A/Y5me\u0016\u001c\u0017!C:f[&\u001cGn\u001c8f)\ri\u0013Q\u0001\u0005\u0007\u0003\u000f!\u0002\u0019\u0001-\u0002\t!\fGNZ\u0001\tg\u000e\fg\u000eT8oOR)\u0001,!\u0004\u0002\u0012!1\u0011qB\u000bA\u0002-\u000bAAY5ug\"1\u00111C\u000bA\u0002a\u000bAA\u001a:p[\"\u0012QC_\u0001\t]\u0016DHo\u0015;faR\t\u0001,A\u0007CSR\u001cV\r^*uKB\u0004XM\u001d\t\u0003Ma\u00192\u0001GA\u0011!\ra\u00141E\u0005\u0004\u0003K\u0011#AB!osJ+g\r\u0006\u0002\u0002\u001eQ!\u00111FA'%\u0015\ti#KA\u0019\r\u0019\ty\u0003\u0007\u0001\u0002,\taAH]3gS:,W.\u001a8u}A!\u00111GA$\u001d\u0011\t)$a\u0011\u000f\t\u0005]\u0012\u0011\t\b\u0005\u0003s\ty$\u0004\u0002\u0002<)\u0019\u0011QH\u0018\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0019\u0013BA\u0011#\u0013\r\t)\u0005I\u0001\b'R,\u0007\u000f]3s\u0013\u0011\tI%a\u0013\u0003\u001d\u00153g-[2jK:$8\u000b\u001d7ji*\u0019\u0011Q\t\u0011\t\u000f\u0005=#\u00041\u0001\u0002R\u0005\u0011!m\u001d\u0019\u0005\u0003'\n9\u0006\u0005\u0003+g\u0005U\u0003c\u0001\u001c\u0002X\u0011Y\u0011\u0011LA'\u0003\u0003\u0005\tQ!\u0001;\u0005\ryFE\r")
public final class BitSetStepper
extends InOrderStepperBase<IntStepper, BitSetStepper>
implements IntStepper {
    private BitSetOps<?> underlying;
    private long cache0;
    private long cache1;
    private int cacheIndex;
    private boolean found;

    public static IntStepper from(BitSetOps<?> bs) {
        return BitSetStepper$.MODULE$.from(bs);
    }

    @Override
    public <B> Spliterator.OfInt spliterator() {
        return IntStepper.spliterator$(this);
    }

    @Override
    public <B> PrimitiveIterator.OfInt javaIterator() {
        return IntStepper.javaIterator$(this);
    }

    @Override
    public <B> Spliterator<?> spliterator$mcI$sp() {
        return IntStepper.spliterator$mcI$sp$(this);
    }

    @Override
    public <B> java.util.Iterator<?> javaIterator$mcI$sp() {
        return IntStepper.javaIterator$mcI$sp$(this);
    }

    @Override
    public double nextStep$mcD$sp() {
        return Stepper.nextStep$mcD$sp$(this);
    }

    @Override
    public long nextStep$mcJ$sp() {
        return Stepper.nextStep$mcJ$sp$(this);
    }

    @Override
    public Stepper<Object> trySplit$mcD$sp() {
        return Stepper.trySplit$mcD$sp$(this);
    }

    @Override
    public Stepper<Object> trySplit$mcI$sp() {
        return Stepper.trySplit$mcI$sp$(this);
    }

    @Override
    public Stepper<Object> trySplit$mcJ$sp() {
        return Stepper.trySplit$mcJ$sp$(this);
    }

    @Override
    public <B> Spliterator<?> spliterator$mcD$sp() {
        return Stepper.spliterator$mcD$sp$(this);
    }

    @Override
    public <B> Spliterator<?> spliterator$mcJ$sp() {
        return Stepper.spliterator$mcJ$sp$(this);
    }

    @Override
    public <B> java.util.Iterator<?> javaIterator$mcD$sp() {
        return Stepper.javaIterator$mcD$sp$(this);
    }

    @Override
    public <B> java.util.Iterator<?> javaIterator$mcJ$sp() {
        return Stepper.javaIterator$mcJ$sp$(this);
    }

    @Override
    public Iterator<Object> iterator() {
        return Stepper.iterator$(this);
    }

    private BitSetOps<?> underlying() {
        return this.underlying;
    }

    private void underlying_$eq(BitSetOps<?> x$1) {
        this.underlying = x$1;
    }

    private long cache0() {
        return this.cache0;
    }

    private void cache0_$eq(long x$1) {
        this.cache0 = x$1;
    }

    private long cache1() {
        return this.cache1;
    }

    private void cache1_$eq(long x$1) {
        this.cache1 = x$1;
    }

    private int cacheIndex() {
        return this.cacheIndex;
    }

    private void cacheIndex_$eq(int x$1) {
        this.cacheIndex = x$1;
    }

    @Override
    public boolean found() {
        return this.found;
    }

    public void found_$eq(boolean x$1) {
        this.found = x$1;
    }

    @Override
    public boolean findNext() {
        while (this.i0() < this.iN()) {
            int ix = this.i0() >> 6;
            if (ix == this.cacheIndex() || ix == this.cacheIndex() + 1) {
                int i = this.scanLong(ix == this.cacheIndex() ? this.cache0() : this.cache1(), this.i0() & 0x3F);
                if (i >= 0) {
                    this.i0_$eq(this.i0() & ~63 | i);
                    this.found_$eq(this.i0() < this.iN());
                    return this.found();
                }
                this.i0_$eq((this.i0() & ~63) + 64);
                continue;
            }
            if (this.underlying() == null) {
                this.i0_$eq(this.iN());
                this.found_$eq(false);
                return this.found();
            }
            this.cacheIndex_$eq(ix);
            this.cache0_$eq(this.underlying().word(this.cacheIndex()));
            this.cache1_$eq(this.iN() - 1 >> 6 == ix ? -1L : this.underlying().word(this.cacheIndex() + 1));
        }
        return false;
    }

    @Override
    public BitSetStepper semiclone(int half) {
        int ixOld0;
        if (this.underlying() == null) {
            BitSetStepper ans = new BitSetStepper(null, this.cache0(), this.cache1(), this.i0(), half, this.cacheIndex());
            ans.found_$eq(this.found());
            this.i0_$eq(half);
            this.found_$eq(false);
            return ans;
        }
        int ixNewN = half - 1 >> 6;
        BitSetStepper ans = new BitSetStepper(ixNewN <= this.cacheIndex() + 1 ? null : this.underlying(), this.cache0(), this.cache1(), this.i0(), half, this.cacheIndex());
        if (this.found()) {
            ans.found_$eq(true);
        }
        if ((ixOld0 = half >> 6) > this.cacheIndex() + 1) {
            this.cache0_$eq(this.underlying().word(ixOld0));
            this.cache1_$eq(this.iN() - 1 >> 6 == ixOld0 ? -1L : this.underlying().word(ixOld0 + 1));
            this.cacheIndex_$eq(ixOld0);
            this.i0_$eq(half);
            this.found_$eq(false);
        }
        return ans;
    }

    private int scanLong(long bits, int from) {
        while (from < 64) {
            if ((bits & 1L << from) != 0L) {
                return from;
            }
            ++from;
        }
        return -1;
    }

    @Override
    public int nextStep() {
        return this.nextStep$mcI$sp();
    }

    @Override
    public int nextStep$mcI$sp() {
        if (this.found() || this.findNext()) {
            this.found_$eq(false);
            int ans = this.i0();
            this.i0_$eq(this.i0() + 1);
            return ans;
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    public BitSetStepper(BitSetOps<?> underlying, long cache0, long cache1, int _i0, int _iN, int cacheIndex) {
        this.underlying = underlying;
        this.cache0 = cache0;
        this.cache1 = cache1;
        this.cacheIndex = cacheIndex;
        super(_i0, _iN);
        this.found = false;
    }
}

