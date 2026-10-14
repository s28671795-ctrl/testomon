/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000514aAD\b\u0002\u0002E9\u0002\u0002C\u000f\u0001\u0005\u000b\u0007IQC\u0010\t\u0011]\u0002!\u0011!Q\u0001\u000e\u0001BQ\u0001\u000f\u0001\u0005\u0002eBq\u0001\u0014\u0001A\u0002\u0013UQ\nC\u0004R\u0001\u0001\u0007IQ\u0003*\t\ra\u0003\u0001\u0015)\u0004O\u0011\u001dI\u0006\u00011A\u0005\u0016iCqa\u0017\u0001A\u0002\u0013UA\f\u0003\u0004_\u0001\u0001\u0006k\u0001\u0010\u0005\u0006?\u00021\t\u0002\u0019\u0005\u0006C\u0002!\t!\u0014\u0005\u0006E\u0002!\ta\u0019\u0005\u0006O\u0002!\t\u0001\u001b\u0002\u0014\u0013R,'/\u0019;peN#X\r\u001d9fe\n\u000b7/\u001a\u0006\u0003!E\tA![7qY*\u0011!cE\u0001\bG>tg/\u001a:u\u0015\t!R#\u0001\u0006d_2dWm\u0019;j_:T\u0011AF\u0001\u0006g\u000e\fG.Y\u000b\u000519j\u0004j\u0005\u0002\u00013A\u0011!dG\u0007\u0002+%\u0011A$\u0006\u0002\u0007\u0003:L(+\u001a4\u0002\u0015UtG-\u001a:ms&twm\u0001\u0001\u0016\u0003\u0001\u00022!I\u0015-\u001d\t\u0011sE\u0004\u0002$M5\tAE\u0003\u0002&=\u00051AH]8pizJ\u0011AF\u0005\u0003QU\tq\u0001]1dW\u0006<W-\u0003\u0002+W\tA\u0011\n^3sCR|'O\u0003\u0002)+A\u0011QF\f\u0007\u0001\t\u0015y\u0003A1\u00011\u0005\u0005\t\u0015CA\u00195!\tQ\"'\u0003\u00024+\t9aj\u001c;iS:<\u0007C\u0001\u000e6\u0013\t1TCA\u0002B]f\f1\"\u001e8eKJd\u00170\u001b8hA\u00051A(\u001b8jiz\"\"AO&\u0011\u000bm\u0002A\u0006P$\u000e\u0003=\u0001\"!L\u001f\u0005\u000by\u0002!\u0019A \u0003\u0005M\u0003\u0016C\u0001!D!\tQ\u0012)\u0003\u0002C+\t!a*\u001e7m!\r!U\tL\u0007\u0002'%\u0011ai\u0005\u0002\b'R,\u0007\u000f]3s!\ti\u0003\nB\u0003J\u0001\t\u0007!J\u0001\u0003TK6L\u0017CA\u0019=\u0011\u0015i2\u00011\u0001!\u00035qW\r\u001f;DQVt7nU5{KV\ta\n\u0005\u0002\u001b\u001f&\u0011\u0001+\u0006\u0002\u0004\u0013:$\u0018!\u00058fqR\u001c\u0005.\u001e8l'&TXm\u0018\u0013fcR\u00111K\u0016\t\u00035QK!!V\u000b\u0003\tUs\u0017\u000e\u001e\u0005\b/\u0016\t\t\u00111\u0001O\u0003\rAH%M\u0001\u000f]\u0016DHo\u00115v].\u001c\u0016N_3!\u0003\u001d\u0001(o\u001c=jK\u0012,\u0012\u0001P\u0001\faJ|\u00070[3e?\u0012*\u0017\u000f\u0006\u0002T;\"9q\u000bCA\u0001\u0002\u0004a\u0014\u0001\u00039s_bLW\r\u001a\u0011\u0002\u0013M,W.[2m_:,G#A$\u0002\u001f\rD\u0017M]1di\u0016\u0014\u0018n\u001d;jGN\fA\"Z:uS6\fG/Z*ju\u0016,\u0012\u0001\u001a\t\u00035\u0015L!AZ\u000b\u0003\t1{gnZ\u0001\bQ\u0006\u001c8\u000b^3q+\u0005I\u0007C\u0001\u000ek\u0013\tYWCA\u0004C_>dW-\u00198")
public abstract class IteratorStepperBase<A, SP extends Stepper<A>, Semi extends SP> {
    private final Iterator<A> underlying;
    private int nextChunkSize;
    private SP proxied;

    public final Iterator<A> underlying() {
        return this.underlying;
    }

    public final int nextChunkSize() {
        return this.nextChunkSize;
    }

    public final void nextChunkSize_$eq(int x$1) {
        this.nextChunkSize = x$1;
    }

    public final SP proxied() {
        return this.proxied;
    }

    public final void proxied_$eq(SP x$1) {
        this.proxied = x$1;
    }

    public abstract Semi semiclone();

    public int characteristics() {
        if (this.proxied() != null) {
            return 16464;
        }
        return 16;
    }

    public long estimateSize() {
        if (this.proxied() != null) {
            return this.proxied().estimateSize();
        }
        return Long.MAX_VALUE;
    }

    public boolean hasStep() {
        if (this.proxied() != null) {
            return this.proxied().hasStep();
        }
        return this.underlying().hasNext();
    }

    public IteratorStepperBase(Iterator<A> underlying) {
        this.underlying = underlying;
        this.nextChunkSize = 16;
        this.proxied = null;
    }
}

