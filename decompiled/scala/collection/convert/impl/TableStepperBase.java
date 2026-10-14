/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import scala.collection.Stepper;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005UbAB\u000b\u0017\u0003\u0003Qb\u0004\u0003\u00054\u0001\t\u0005\r\u0011\"\u00055\u0011!A\u0004A!a\u0001\n#I\u0004\u0002C \u0001\u0005\u0003\u0005\u000b\u0015B\u001b\t\u0011\u0001\u0003!Q1A\u0005\u0012\u0005C\u0001\"\u0014\u0001\u0003\u0002\u0003\u0006IA\u0011\u0005\t\u001d\u0002\u0011\t\u0019!C\ti!Aq\n\u0001BA\u0002\u0013E\u0001\u000b\u0003\u0005S\u0001\t\u0005\t\u0015)\u00036\u0011!\u0019\u0006A!b\u0001\n#!\u0004\u0002\u0003+\u0001\u0005\u0003\u0005\u000b\u0011B\u001b\t\u000bU\u0003A\u0011\u0001,\t\u000fi\u0004\u0001\u0019!C\tw\"9A\u0010\u0001a\u0001\n#i\bBB@\u0001A\u0003&Q\tC\u0004\u0002\u0002\u0001!)\"a\u0001\t\u000f\u0005e\u0001A\"\u0005\u0002\u001c!1\u0011\u0011\u0005\u0001\u0005\u0002QBq!a\t\u0001\t\u0003\t)\u0003C\u0004\u0002.\u0001!\t!a\f\t\u000f\u0005E\u0002\u0001\"\u0001\u00024\t\u0001B+\u00192mKN#X\r\u001d9fe\n\u000b7/\u001a\u0006\u0003/a\tA![7qY*\u0011\u0011DG\u0001\bG>tg/\u001a:u\u0015\tYB$\u0001\u0006d_2dWm\u0019;j_:T\u0011!H\u0001\u0006g\u000e\fG.Y\u000b\u0006?i;E\r[\n\u0004\u0001\u0001\"\u0003CA\u0011#\u001b\u0005a\u0012BA\u0012\u001d\u0005\u0019\te.\u001f*fMB\u0011Q\u0005\r\b\u0003M9r!aJ\u0017\u000f\u0005!bS\"A\u0015\u000b\u0005)Z\u0013A\u0002\u001fs_>$hh\u0001\u0001\n\u0003uI!a\u0007\u000f\n\u0005=R\u0012aB*uKB\u0004XM]\u0005\u0003cI\u0012a\"\u00124gS\u000eLWM\u001c;Ta2LGO\u0003\u000205\u0005IQ.\u0019=MK:<G\u000f[\u000b\u0002kA\u0011\u0011EN\u0005\u0003oq\u00111!\u00138u\u00035i\u0017\r\u001f'f]\u001e$\bn\u0018\u0013fcR\u0011!(\u0010\t\u0003CmJ!\u0001\u0010\u000f\u0003\tUs\u0017\u000e\u001e\u0005\b}\t\t\t\u00111\u00016\u0003\rAH%M\u0001\u000b[\u0006DH*\u001a8hi\"\u0004\u0013!\u0002;bE2,W#\u0001\"\u0011\u0007\u0005\u001aU)\u0003\u0002E9\t)\u0011I\u001d:bsB\u0011ai\u0012\u0007\u0001\t\u0015A\u0005A1\u0001J\u0005\u0005I\u0015C\u0001&!!\t\t3*\u0003\u0002M9\t!a*\u001e7m\u0003\u0019!\u0018M\u00197fA\u0005\u0011\u0011\u000eM\u0001\u0007SBzF%Z9\u0015\u0005i\n\u0006b\u0002 \b\u0003\u0003\u0005\r!N\u0001\u0004SB\u0002\u0013AA5O\u0003\rIg\nI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u000b]3x\u000f_=\u0011\ra\u0003\u0011,R2h\u001b\u00051\u0002C\u0001$[\t\u0015Y\u0006A1\u0001]\u0005\u0005\t\u0015CA/a!\t\tc,\u0003\u0002`9\t9aj\u001c;iS:<\u0007CA\u0011b\u0013\t\u0011GDA\u0002B]f\u0004\"A\u00123\u0005\u000b\u0015\u0004!\u0019\u00014\u0003\u0007M+(-\u0005\u0002KAB\u0011a\t\u001b\u0003\u0006S\u0002\u0011\rA\u001b\u0002\u0005'\u0016l\u0017.\u0005\u0002^WJ\u0019An\u00198\u0007\t5\u0004\u0001a\u001b\u0002\ryI,g-\u001b8f[\u0016tGO\u0010\u0019\u0004_F$\bC\u0002-\u00013\u0016\u00038\u000f\u0005\u0002Gc\u0012I!\u000f[A\u0001\u0002\u0003\u0015\t\u0001\u0018\u0002\u0004?\u0012\n\u0004C\u0001$u\t%)\b.!A\u0001\u0002\u000b\u0005ALA\u0002`IIBQaM\u0006A\u0002UBQ\u0001Q\u0006A\u0002\tCQAT\u0006A\u0002UBQaU\u0006A\u0002U\n\u0011\"\\=DkJ\u0014XM\u001c;\u0016\u0003\u0015\u000bQ\"\\=DkJ\u0014XM\u001c;`I\u0015\fHC\u0001\u001e\u007f\u0011\u001dqT\"!AA\u0002\u0015\u000b!\"\\=DkJ\u0014XM\u001c;!\u0003=1\u0017N\u001c3OKb$8)\u001e:sK:$HCAA\u0003!\r\t\u0013qA\u0005\u0004\u0003\u0013a\"a\u0002\"p_2,\u0017M\u001c\u0015\u0004\u001f\u00055\u0001\u0003BA\b\u0003+i!!!\u0005\u000b\u0007\u0005MA$\u0001\u0006b]:|G/\u0019;j_:LA!a\u0006\u0002\u0012\t9A/Y5me\u0016\u001c\u0017!C:f[&\u001cGn\u001c8f)\r9\u0017Q\u0004\u0005\u0007\u0003?\u0001\u0002\u0019A\u001b\u0002\t!\fGNZ\u0001\u0010G\"\f'/Y2uKJL7\u000f^5dg\u0006aQm\u001d;j[\u0006$XmU5{KV\u0011\u0011q\u0005\t\u0004C\u0005%\u0012bAA\u00169\t!Aj\u001c8h\u0003\u001dA\u0017m]*uKB,\"!!\u0002\u0002\u0011Q\u0014\u0018p\u00159mSR$\u0012a\u0019")
public abstract class TableStepperBase<A, I, Sub, Semi extends Sub>
implements Stepper.EfficientSplit {
    private int maxLength;
    private final I[] table;
    private int i0;
    private final int iN;
    private I myCurrent;

    public int maxLength() {
        return this.maxLength;
    }

    public void maxLength_$eq(int x$1) {
        this.maxLength = x$1;
    }

    public I[] table() {
        return this.table;
    }

    public int i0() {
        return this.i0;
    }

    public void i0_$eq(int x$1) {
        this.i0 = x$1;
    }

    public int iN() {
        return this.iN;
    }

    public I myCurrent() {
        return this.myCurrent;
    }

    public void myCurrent_$eq(I x$1) {
        this.myCurrent = x$1;
    }

    public final boolean findNextCurrent() {
        while (this.i0() < this.iN()) {
            this.i0_$eq(this.i0() + 1);
            if (this.i0() >= this.iN()) {
                return false;
            }
            this.myCurrent_$eq(this.table()[this.i0()]);
            if (this.myCurrent() == null) continue;
            return true;
        }
        return false;
    }

    public abstract Semi semiclone(int var1);

    public int characteristics() {
        return 0;
    }

    public long estimateSize() {
        if (!this.hasStep()) {
            this.maxLength_$eq(0);
            return 0L;
        }
        return this.maxLength();
    }

    public boolean hasStep() {
        return this.myCurrent() != null || this.findNextCurrent();
    }

    public Sub trySplit() {
        if (this.iN() - 1 > this.i0() && this.maxLength() > 0) {
            int inRight;
            int half = this.i0() + this.iN() >>> 1;
            Semi ans = this.semiclone(half);
            ((TableStepperBase)ans).myCurrent_$eq(this.myCurrent());
            this.myCurrent_$eq(this.table()[half]);
            int inLeft = ((TableStepperBase)ans).myCurrent() != null ? 1 : 0;
            int n = inRight = this.myCurrent() != null ? 1 : 0;
            if (this.iN() - this.i0() < 32) {
                int i = this.i0() + 1;
                while (i < half && this.table()[i] != null) {
                    ++i;
                    ++inLeft;
                }
                i = half + 1;
                while (i < this.iN() && this.table()[i] != null) {
                    ++i;
                    ++inRight;
                }
            }
            this.maxLength_$eq(this.maxLength() - inLeft);
            ((TableStepperBase)ans).maxLength_$eq(((TableStepperBase)ans).maxLength() - inRight);
            this.i0_$eq(half);
            return (Sub)ans;
        }
        return null;
    }

    public TableStepperBase(int maxLength, I[] table, int i0, int iN) {
        this.maxLength = maxLength;
        this.table = table;
        this.i0 = i0;
        this.iN = iN;
        this.myCurrent = this.i0() < iN ? table[this.i0()] : null;
    }
}

