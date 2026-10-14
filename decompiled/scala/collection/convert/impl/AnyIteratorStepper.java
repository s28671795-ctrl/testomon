/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.Spliterator;
import scala.collection.AnyStepper;
import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StepperShape$;
import scala.collection.convert.impl.IteratorStepperBase;
import scala.jdk.Accumulator;
import scala.jdk.AnyAccumulator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005}2QAB\u0004\u0001\u0017=A\u0011b\n\u0001\u0003\u0002\u0003\u0006I\u0001\u000b\u001b\t\u000bY\u0002A\u0011A\u001c\t\u000be\u0002A\u0011\u0003\u001e\t\u000bm\u0002A\u0011\u0001\u001f\t\u000bu\u0002A\u0011\u0001 \u0003%\u0005s\u00170\u0013;fe\u0006$xN]*uKB\u0004XM\u001d\u0006\u0003\u0011%\tA![7qY*\u0011!bC\u0001\bG>tg/\u001a:u\u0015\taQ\"\u0001\u0006d_2dWm\u0019;j_:T\u0011AD\u0001\u0006g\u000e\fG.Y\u000b\u0003!]\u00192\u0001A\t#!\u0015\u00112#\u0006\u0012'\u001b\u00059\u0011B\u0001\u000b\b\u0005MIE/\u001a:bi>\u00148\u000b^3qa\u0016\u0014()Y:f!\t1r\u0003\u0004\u0001\u0005\u000ba\u0001!\u0019\u0001\u000e\u0003\u0003\u0005\u001b\u0001!\u0005\u0002\u001c?A\u0011A$H\u0007\u0002\u001b%\u0011a$\u0004\u0002\b\u001d>$\b.\u001b8h!\ta\u0002%\u0003\u0002\"\u001b\t\u0019\u0011I\\=\u0011\u0007\r\"S#D\u0001\f\u0013\t)3B\u0001\u0006B]f\u001cF/\u001a9qKJ\u00042A\u0005\u0001\u0016\u0003-yVO\u001c3fe2L\u0018N\\4\u0011\u0007%\nTC\u0004\u0002+_9\u00111FL\u0007\u0002Y)\u0011Q&G\u0001\u0007yI|w\u000e\u001e \n\u00039I!\u0001M\u0007\u0002\u000fA\f7m[1hK&\u0011!g\r\u0002\t\u0013R,'/\u0019;pe*\u0011\u0001'D\u0005\u0003kM\t!\"\u001e8eKJd\u00170\u001b8h\u0003\u0019a\u0014N\\5u}Q\u0011a\u0005\u000f\u0005\u0006O\t\u0001\r\u0001K\u0001\ng\u0016l\u0017n\u00197p]\u0016$\u0012AJ\u0001\t]\u0016DHo\u0015;faR\tQ#\u0001\u0005uef\u001c\u0006\u000f\\5u)\u0005\u0011\u0003")
public class AnyIteratorStepper<A>
extends IteratorStepperBase<A, AnyStepper<A>, AnyIteratorStepper<A>>
implements AnyStepper<A> {
    @Override
    public <B> Spliterator<B> spliterator() {
        return AnyStepper.spliterator$(this);
    }

    @Override
    public <B> java.util.Iterator<B> javaIterator() {
        return AnyStepper.javaIterator$(this);
    }

    @Override
    public double nextStep$mcD$sp() {
        return Stepper.nextStep$mcD$sp$(this);
    }

    @Override
    public int nextStep$mcI$sp() {
        return Stepper.nextStep$mcI$sp$(this);
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
    public <B> Spliterator<?> spliterator$mcI$sp() {
        return Stepper.spliterator$mcI$sp$(this);
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
    public <B> java.util.Iterator<?> javaIterator$mcI$sp() {
        return Stepper.javaIterator$mcI$sp$(this);
    }

    @Override
    public <B> java.util.Iterator<?> javaIterator$mcJ$sp() {
        return Stepper.javaIterator$mcJ$sp$(this);
    }

    @Override
    public Iterator<A> iterator() {
        return Stepper.iterator$(this);
    }

    @Override
    public AnyIteratorStepper<A> semiclone() {
        return new AnyIteratorStepper<A>(null);
    }

    @Override
    public A nextStep() {
        if (this.proxied() != null) {
            return this.proxied().nextStep();
        }
        return this.underlying().next();
    }

    @Override
    public AnyStepper<A> trySplit() {
        int i;
        if (this.proxied() != null) {
            return ((AnyStepper)this.proxied()).trySplit();
        }
        AnyAccumulator<Object> acc = new AnyAccumulator<Object>();
        int n = this.nextChunkSize() & 0xFFFFFFFC;
        for (i = 0; i < n && this.underlying().hasNext(); ++i) {
            Object $plus$eq_elem = this.underlying().next();
            acc.addOne($plus$eq_elem);
            Object var5_4 = null;
        }
        if (i < n || !this.underlying().hasNext()) {
            StepperShape$ anyStepperShape_this = StepperShape$.MODULE$;
            Object var6_5 = null;
            StepperShape<Object, Stepper<Object>> stepper_shape = anyStepperShape_this.anyStepperShapePrototype();
            Object var8_6 = null;
            this.proxied_$eq(((Accumulator)acc).efficientStepper(stepper_shape));
            return ((AnyStepper)this.proxied()).trySplit();
        }
        Stepper ans = this.semiclone();
        StepperShape$ anyStepperShape_this = StepperShape$.MODULE$;
        Object var7_8 = null;
        StepperShape<Object, Stepper<Object>> stepper_shape = anyStepperShape_this.anyStepperShapePrototype();
        Object var9_9 = null;
        ((IteratorStepperBase)((Object)ans)).proxied_$eq(((Accumulator)acc).efficientStepper(stepper_shape));
        this.nextChunkSize_$eq((this.nextChunkSize() & 3) == 3 ? (n < 0x40000000 ? n * 2 : n) : this.nextChunkSize() + 1);
        return ans;
    }

    public AnyIteratorStepper(Iterator<A> _underlying) {
        super(_underlying);
    }
}

