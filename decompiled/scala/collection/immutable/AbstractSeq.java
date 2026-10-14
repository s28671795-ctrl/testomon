/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.SeqFactory;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t2QAA\u0002\u0002\u0002)AQa\b\u0001\u0005\u0002\u0001\u00121\"\u00112tiJ\f7\r^*fc*\u0011A!B\u0001\nS6lW\u000f^1cY\u0016T!AB\u0004\u0002\u0015\r|G\u000e\\3di&|gNC\u0001\t\u0003\u0015\u00198-\u00197b\u0007\u0001)\"aC\t\u0014\u0007\u0001a1\u0004E\u0002\u000e\u001d=i\u0011!B\u0005\u0003\u0005\u0015\u0001\"\u0001E\t\r\u0001\u00111!\u0003\u0001CC\u0002M\u0011\u0011!Q\t\u0003)a\u0001\"!\u0006\f\u000e\u0003\u001dI!aF\u0004\u0003\u000f9{G\u000f[5oOB\u0011Q#G\u0005\u00035\u001d\u00111!\u00118z!\raRdD\u0007\u0002\u0007%\u0011ad\u0001\u0002\u0004'\u0016\f\u0018A\u0002\u001fj]&$h\bF\u0001\"!\ra\u0002a\u0004")
public abstract class AbstractSeq<A>
extends scala.collection.AbstractSeq<A>
implements Seq<A> {
    @Override
    public final Seq<A> toSeq() {
        return Seq.toSeq$(this);
    }

    @Override
    public SeqFactory<Seq> iterableFactory() {
        return Seq.iterableFactory$(this);
    }
}

