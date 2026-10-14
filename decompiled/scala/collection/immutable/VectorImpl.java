/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.immutable.Vector;
import scala.collection.immutable.Vector0$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\r3Q\u0001B\u0003\u0002*1A\u0011B\b\u0001\u0003\u0002\u0003\u0006IaH\u0018\t\u000bE\u0002A\u0011\u0001\u001a\t\u000bU\u0002AQ\t\u001c\u0003\u0015Y+7\r^8s\u00136\u0004HN\u0003\u0002\u0007\u000f\u0005I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003\u0011%\t!bY8mY\u0016\u001cG/[8o\u0015\u0005Q\u0011!B:dC2\f7\u0001A\u000b\u0003\u001bQ\u0019\"\u0001\u0001\b\u0011\u0007=\u0001\"#D\u0001\u0006\u0013\t\tRA\u0001\u0004WK\u000e$xN\u001d\t\u0003'Qa\u0001\u0001\u0002\u0004\u0016\u0001\u0011\u0015\rA\u0006\u0002\u0002\u0003F\u0011qc\u0007\t\u00031ei\u0011!C\u0005\u00035%\u0011qAT8uQ&tw\r\u0005\u0002\u00199%\u0011Q$\u0003\u0002\u0004\u0003:L\u0018\u0001C0qe\u00164\u0017\u000e_\u0019\u0011\u0005\u0001bcBA\u0011+\u001d\t\u0011\u0013F\u0004\u0002$Q9\u0011AeJ\u0007\u0002K)\u0011aeC\u0001\u0007yI|w\u000e\u001e \n\u0003)I!\u0001C\u0005\n\u0005\u00199\u0011BA\u0016\u0006\u000311Vm\u0019;pe&sG.\u001b8f\u0013\ticF\u0001\u0003BeJ\f$BA\u0016\u0006\u0013\t\u0001\u0004#A\u0004qe\u00164\u0017\u000e_\u0019\u0002\rqJg.\u001b;?)\t\u0019D\u0007E\u0002\u0010\u0001IAQA\b\u0002A\u0002}\tQa\u001d7jG\u0016$2AD\u001c=\u0011\u0015A4\u00011\u0001:\u0003\u00111'o\\7\u0011\u0005aQ\u0014BA\u001e\n\u0005\rIe\u000e\u001e\u0005\u0006{\r\u0001\r!O\u0001\u0006k:$\u0018\u000e\\\u0015\u0004\u0001}\n\u0015B\u0001!\u0006\u0005%\u0011\u0015n\u001a,fGR|'/\u0003\u0002C\u000b\t9a+Z2u_J\f\u0004")
public abstract class VectorImpl<A>
extends Vector<A> {
    @Override
    public final Vector<A> slice(int from, int until) {
        int lo = Math.max(from, 0);
        int hi = Math.min(until, this.length());
        if (hi - lo == this.length()) {
            return this;
        }
        if (hi <= lo) {
            return Vector0$.MODULE$;
        }
        return this.slice0(lo, hi);
    }

    public VectorImpl(Object[] _prefix1) {
        super(_prefix1);
    }
}

