/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import scala.collection.Stepper;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00194a!\u0004\b\u0002\u0002A1\u0002\u0002C\u0016\u0001\u0005\u0003\u0007I\u0011\u0003\u0017\t\u0011A\u0002!\u00111A\u0005\u0012EB\u0001b\u000e\u0001\u0003\u0002\u0003\u0006K!\f\u0005\tq\u0001\u0011\t\u0019!C\tY!A\u0011\b\u0001BA\u0002\u0013E!\b\u0003\u0005=\u0001\t\u0005\t\u0015)\u0003.\u0011\u0015i\u0004\u0001\"\u0001?\u0011\u0015)\u0006A\"\u0005W\u0011\u0015I\u0006\u0001\"\u0001[\u0011\u0015q\u0006\u0001\"\u0001-\u0011\u0015y\u0006\u0001\"\u0001a\u0011\u0015!\u0007\u0001\"\u0001f\u0005IIe\u000eZ3yK\u0012\u001cF/\u001a9qKJ\u0014\u0015m]3\u000b\u0005=\u0001\u0012\u0001B5na2T!!\u0005\n\u0002\u000f\r|gN^3si*\u00111\u0003F\u0001\u000bG>dG.Z2uS>t'\"A\u000b\u0002\u000bM\u001c\u0017\r\\1\u0016\u0007]\u0019UjE\u0002\u00011q\u0001\"!\u0007\u000e\u000e\u0003QI!a\u0007\u000b\u0003\r\u0005s\u0017PU3g!\ti\u0002F\u0004\u0002\u001fM9\u0011q$\n\b\u0003A\u0011j\u0011!\t\u0006\u0003E\r\na\u0001\u0010:p_Rt4\u0001A\u0005\u0002+%\u00111\u0003F\u0005\u0003OI\tqa\u0015;faB,'/\u0003\u0002*U\tqQI\u001a4jG&,g\u000e^*qY&$(BA\u0014\u0013\u0003\tI\u0007'F\u0001.!\tIb&\u0003\u00020)\t\u0019\u0011J\u001c;\u0002\r%\u0004t\fJ3r)\t\u0011T\u0007\u0005\u0002\u001ag%\u0011A\u0007\u0006\u0002\u0005+:LG\u000fC\u00047\u0005\u0005\u0005\t\u0019A\u0017\u0002\u0007a$\u0013'A\u0002ja\u0001\n!!\u001b(\u0002\r%tu\fJ3r)\t\u00114\bC\u00047\u000b\u0005\u0005\t\u0019A\u0017\u0002\u0007%t\u0005%\u0001\u0004=S:LGO\u0010\u000b\u0004\u007fM#\u0006\u0003\u0002!\u0001\u00032k\u0011A\u0004\t\u0003\u0005\u000ec\u0001\u0001B\u0003E\u0001\t\u0007QIA\u0002Tk\n\f\"AR%\u0011\u0005e9\u0015B\u0001%\u0015\u0005\u0011qU\u000f\u001c7\u0011\u0005eQ\u0015BA&\u0015\u0005\r\te.\u001f\t\u0003\u00056#QA\u0014\u0001C\u0002=\u0013AaU3nSF\u0011\u0001+\u0011\t\u00033EK!A\u0015\u000b\u0003\u000f9{G\u000f[5oO\")1f\u0002a\u0001[!)\u0001h\u0002a\u0001[\u0005I1/Z7jG2|g.\u001a\u000b\u0003\u0019^CQ\u0001\u0017\u0005A\u00025\nA\u0001[1mM\u00069\u0001.Y:Ti\u0016\u0004X#A.\u0011\u0005ea\u0016BA/\u0015\u0005\u001d\u0011un\u001c7fC:\fqb\u00195be\u0006\u001cG/\u001a:jgRL7m]\u0001\rKN$\u0018.\\1uKNK'0Z\u000b\u0002CB\u0011\u0011DY\u0005\u0003GR\u0011A\u0001T8oO\u0006AAO]=Ta2LG\u000fF\u0001B\u0001")
public abstract class IndexedStepperBase<Sub, Semi extends Sub>
implements Stepper.EfficientSplit {
    private int i0;
    private int iN;

    public int i0() {
        return this.i0;
    }

    public void i0_$eq(int x$1) {
        this.i0 = x$1;
    }

    public int iN() {
        return this.iN;
    }

    public void iN_$eq(int x$1) {
        this.iN = x$1;
    }

    public abstract Semi semiclone(int var1);

    public boolean hasStep() {
        return this.i0() < this.iN();
    }

    public int characteristics() {
        return 16464;
    }

    public long estimateSize() {
        return this.iN() - this.i0();
    }

    public Sub trySplit() {
        if (this.iN() - 1 > this.i0()) {
            int half = this.i0() + this.iN() >>> 1;
            Semi ans = this.semiclone(half);
            this.i0_$eq(half);
            return (Sub)ans;
        }
        return null;
    }

    public IndexedStepperBase(int i0, int iN) {
        this.i0 = i0;
        this.iN = iN;
    }
}

