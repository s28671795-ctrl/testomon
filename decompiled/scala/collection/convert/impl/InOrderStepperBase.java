/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import scala.collection.Stepper;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-4aa\u0004\t\u0002\u0002IA\u0002\u0002C\u0017\u0001\u0005\u0003\u0007I\u0011\u0003\u0018\t\u0011I\u0002!\u00111A\u0005\u0012MB\u0001\"\u000f\u0001\u0003\u0002\u0003\u0006Ka\f\u0005\tu\u0001\u0011\t\u0019!C\t]!A1\b\u0001BA\u0002\u0013EA\b\u0003\u0005?\u0001\t\u0005\t\u0015)\u00030\u0011\u0015y\u0004\u0001\"\u0001A\u0011\u00159\u0006A\"\u0005Y\u0011\u0015a\u0006A\"\u0005^\u0011\u0015q\u0006A\"\u0005`\u0011\u0015\u0011\u0007\u0001\"\u0002Y\u0011\u0015\u0019\u0007\u0001\"\u0001/\u0011\u0015!\u0007\u0001\"\u0001f\u0011\u0015I\u0007\u0001\"\u0001k\u0005IIen\u0014:eKJ\u001cF/\u001a9qKJ\u0014\u0015m]3\u000b\u0005E\u0011\u0012\u0001B5na2T!a\u0005\u000b\u0002\u000f\r|gN^3si*\u0011QCF\u0001\u000bG>dG.Z2uS>t'\"A\f\u0002\u000bM\u001c\u0017\r\\1\u0016\u0007e)ujE\u0002\u00015y\u0001\"a\u0007\u000f\u000e\u0003YI!!\b\f\u0003\r\u0005s\u0017PU3g!\ty\"F\u0004\u0002!Q9\u0011\u0011e\n\b\u0003E\u0019j\u0011a\t\u0006\u0003I\u0015\na\u0001\u0010:p_Rt4\u0001A\u0005\u0002/%\u0011QCF\u0005\u0003SQ\tqa\u0015;faB,'/\u0003\u0002,Y\tqQI\u001a4jG&,g\u000e^*qY&$(BA\u0015\u0015\u0003\tI\u0007'F\u00010!\tY\u0002'\u0003\u00022-\t\u0019\u0011J\u001c;\u0002\r%\u0004t\fJ3r)\t!t\u0007\u0005\u0002\u001ck%\u0011aG\u0006\u0002\u0005+:LG\u000fC\u00049\u0005\u0005\u0005\t\u0019A\u0018\u0002\u0007a$\u0013'A\u0002ja\u0001\n!!\u001b(\u0002\r%tu\fJ3r)\t!T\bC\u00049\u000b\u0005\u0005\t\u0019A\u0018\u0002\u0007%t\u0005%\u0001\u0004=S:LGO\u0010\u000b\u0004\u0003V3\u0006\u0003\u0002\"\u0001\u0007:k\u0011\u0001\u0005\t\u0003\t\u0016c\u0001\u0001B\u0003G\u0001\t\u0007qIA\u0002Tk\n\f\"\u0001S&\u0011\u0005mI\u0015B\u0001&\u0017\u0005\u0011qU\u000f\u001c7\u0011\u0005ma\u0015BA'\u0017\u0005\r\te.\u001f\t\u0003\t>#Q\u0001\u0015\u0001C\u0002E\u0013AaU3nSF\u0011!k\u0011\t\u00037MK!\u0001\u0016\f\u0003\u000f9{G\u000f[5oO\")Qf\u0002a\u0001_!)!h\u0002a\u0001_\u0005)am\\;oIV\t\u0011\f\u0005\u0002\u001c5&\u00111L\u0006\u0002\b\u0005>|G.Z1o\u0003!1\u0017N\u001c3OKb$H#A-\u0002\u0013M,W.[2m_:,GC\u0001(a\u0011\u0015\t'\u00021\u00010\u0003\u0011A\u0017\r\u001c4\u0002\u000f!\f7o\u0015;fa\u0006y1\r[1sC\u000e$XM]5ti&\u001c7/\u0001\u0007fgRLW.\u0019;f'&TX-F\u0001g!\tYr-\u0003\u0002i-\t!Aj\u001c8h\u0003!!(/_*qY&$H#A\"")
public abstract class InOrderStepperBase<Sub, Semi extends Sub>
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

    public abstract boolean found();

    public abstract boolean findNext();

    public abstract Semi semiclone(int var1);

    public final boolean hasStep() {
        return this.found() || this.findNext();
    }

    public int characteristics() {
        return 16;
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

    public InOrderStepperBase(int i0, int iN) {
        this.i0 = i0;
        this.iN = iN;
    }
}

