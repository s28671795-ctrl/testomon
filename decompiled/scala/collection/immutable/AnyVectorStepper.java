/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.util.Iterator;
import java.util.Spliterator;
import scala.collection.AnyStepper;
import scala.collection.immutable.NewVectorIterator;
import scala.collection.immutable.VectorStepperBase;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A2A!\u0002\u0004\u0005\u001b!AA\u0005\u0001B\u0001B\u0003%Q\u0005C\u0003)\u0001\u0011\u0005\u0011\u0006\u0003\u0004,\u0001\u0001&\t\u0002\f\u0005\u0006]\u0001!\ta\f\u0002\u0011\u0003:Lh+Z2u_J\u001cF/\u001a9qKJT!a\u0002\u0005\u0002\u0013%lW.\u001e;bE2,'BA\u0005\u000b\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u0017\u0005)1oY1mC\u000e\u0001QC\u0001\b\u0016'\r\u0001qb\b\t\u0006!E\u0019rdI\u0007\u0002\r%\u0011!C\u0002\u0002\u0012-\u0016\u001cGo\u001c:Ti\u0016\u0004\b/\u001a:CCN,\u0007C\u0001\u000b\u0016\u0019\u0001!QA\u0006\u0001C\u0002]\u0011\u0011!Q\t\u00031q\u0001\"!\u0007\u000e\u000e\u0003)I!a\u0007\u0006\u0003\u000f9{G\u000f[5oOB\u0011\u0011$H\u0005\u0003=)\u00111!\u00118z!\r\u0001\u0013eE\u0007\u0002\u0011%\u0011!\u0005\u0003\u0002\u000b\u0003:L8\u000b^3qa\u0016\u0014\bc\u0001\t\u0001'\u0005\u0011\u0011\u000e\u001e\t\u0004!\u0019\u001a\u0012BA\u0014\u0007\u0005EqUm\u001e,fGR|'/\u0013;fe\u0006$xN]\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005\rR\u0003\"\u0002\u0013\u0003\u0001\u0004)\u0013!\u00022vS2$GCA\u0012.\u0011\u0015!3\u00011\u0001&\u0003!qW\r\u001f;Ti\u0016\u0004H#A\n")
public class AnyVectorStepper<A>
extends VectorStepperBase<A, AnyStepper<A>, AnyVectorStepper<A>>
implements AnyStepper<A> {
    private final NewVectorIterator<A> it;

    @Override
    public <B> Spliterator<B> spliterator() {
        return AnyStepper.spliterator$(this);
    }

    @Override
    public <B> Iterator<B> javaIterator() {
        return AnyStepper.javaIterator$(this);
    }

    @Override
    public AnyVectorStepper<A> build(NewVectorIterator<A> it) {
        return new AnyVectorStepper<A>(it);
    }

    @Override
    public A nextStep() {
        return this.it.next();
    }

    public AnyVectorStepper(NewVectorIterator<A> it) {
        this.it = it;
        super(it);
    }
}

