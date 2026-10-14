/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Function1;
import scala.collection.SeqFactory;
import scala.collection.mutable.Seq;
import scala.collection.mutable.SeqOps;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t2QAA\u0002\u0002\u0002)AQa\b\u0001\u0005\u0002\u0001\u00121\"\u00112tiJ\f7\r^*fc*\u0011A!B\u0001\b[V$\u0018M\u00197f\u0015\t1q!\u0001\u0006d_2dWm\u0019;j_:T\u0011\u0001C\u0001\u0006g\u000e\fG.Y\u0002\u0001+\tY\u0011cE\u0002\u0001\u0019m\u00012!\u0004\b\u0010\u001b\u0005)\u0011B\u0001\u0002\u0006!\t\u0001\u0012\u0003\u0004\u0001\u0005\u000bI\u0001!\u0019A\n\u0003\u0003\u0005\u000b\"\u0001\u0006\r\u0011\u0005U1R\"A\u0004\n\u0005]9!a\u0002(pi\"Lgn\u001a\t\u0003+eI!AG\u0004\u0003\u0007\u0005s\u0017\u0010E\u0002\u001d;=i\u0011aA\u0005\u0003=\r\u00111aU3r\u0003\u0019a\u0014N\\5u}Q\t\u0011\u0005E\u0002\u001d\u0001=\u0001")
public abstract class AbstractSeq<A>
extends scala.collection.AbstractSeq<A>
implements Seq<A> {
    @Override
    public SeqFactory<Seq> iterableFactory() {
        return Seq.iterableFactory$(this);
    }

    @Override
    public Object clone() {
        return SeqOps.clone$(this);
    }

    @Override
    public final SeqOps<A, Seq, Seq<A>> transform(Function1<A, A> f) {
        return SeqOps.transform$(this, f);
    }

    @Override
    public /* synthetic */ Object scala$collection$mutable$Cloneable$$super$clone() {
        return super.clone();
    }
}

