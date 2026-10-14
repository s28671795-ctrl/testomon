/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import scala.Function1;
import scala.collection.IntStepper;
import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.collection.convert.impl.TableStepperBase;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u000553QAC\u0006\u0003\u001fMA\u0001B\f\u0001\u0003\u0002\u0003\u0006I!\u0007\u0005\n_\u0001\u0011\t\u0011)A\u0005aMB\u0001\"\u000e\u0001\u0003\u0002\u0003\u0006IA\u000e\u0005\ts\u0001\u0011\t\u0011)A\u0005u!A1\b\u0001B\u0001B\u0003%\u0011\u0004C\u0005=\u0001\t\u0005\t\u0015!\u0003\u001a{!)q\b\u0001C\u0001\u0001\")q\t\u0001C\u0001\u0011\")\u0011\n\u0001C\u0001\u0015\ny\u0011J\u001c;UC\ndWm\u0015;faB,'O\u0003\u0002\r\u001b\u0005!\u0011.\u001c9m\u0015\tqq\"A\u0004d_:4XM\u001d;\u000b\u0005A\t\u0012AC2pY2,7\r^5p]*\t!#A\u0003tG\u0006d\u0017-\u0006\u0002\u0015?M\u0019\u0001!F\u0015\u0011\rY9\u0012$H\u0015.\u001b\u0005Y\u0011B\u0001\r\f\u0005A!\u0016M\u00197f'R,\u0007\u000f]3s\u0005\u0006\u001cX\r\u0005\u0002\u001b75\t\u0011#\u0003\u0002\u001d#\t\u0019\u0011J\u001c;\u0011\u0005yyB\u0002\u0001\u0003\u0006A\u0001\u0011\rA\t\u0002\u0002\u0013\u000e\u0001\u0011CA\u0012'!\tQB%\u0003\u0002&#\t!a*\u001e7m!\tQr%\u0003\u0002)#\t1\u0011I\\=SK\u001a\u0004\"AK\u0016\u000e\u0003=I!\u0001L\b\u0003\u0015%sGo\u0015;faB,'\u000fE\u0002\u0017\u0001u\t!bX7bq2+gn\u001a;i\u0003\u0019yF/\u00192mKB\u0019!$M\u000f\n\u0005I\n\"!B!se\u0006L\u0018B\u0001\u001b\u0018\u0003\u0015!\u0018M\u00197f\u0003\u001dIG/\u001a:bi\u0016\u0004BAG\u001c\u001e;%\u0011\u0001(\u0005\u0002\n\rVt7\r^5p]F\nq!\u001a=ue\u0006\u001cG\u000f\u0005\u0003\u001bouI\u0012aA0ja\u0005\u0019q,\u001b(\n\u0005y:\u0012AA5O\u0003\u0019a\u0014N\\5u}Q9Q&\u0011\"D\t\u00163\u0005\"\u0002\u0018\b\u0001\u0004I\u0002\"B\u0018\b\u0001\u0004\u0001\u0004\"B\u001b\b\u0001\u00041\u0004\"B\u001d\b\u0001\u0004Q\u0004\"B\u001e\b\u0001\u0004I\u0002\"\u0002\u001f\b\u0001\u0004I\u0012\u0001\u00038fqR\u001cF/\u001a9\u0015\u0003e\t\u0011b]3nS\u000edwN\\3\u0015\u00055Z\u0005\"\u0002'\n\u0001\u0004I\u0012\u0001\u00025bY\u001a\u0004")
public final class IntTableStepper<I>
extends TableStepperBase<Object, I, IntStepper, IntTableStepper<I>>
implements IntStepper {
    public final Function1<I, I> scala$collection$convert$impl$IntTableStepper$$iterate;
    public final Function1<I, Object> scala$collection$convert$impl$IntTableStepper$$extract;

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

    @Override
    public int nextStep() {
        return this.nextStep$mcI$sp();
    }

    @Override
    public IntTableStepper<I> semiclone(int half) {
        return new IntTableStepper(this.maxLength(), this.table(), this.scala$collection$convert$impl$IntTableStepper$$iterate, this.scala$collection$convert$impl$IntTableStepper$$extract, this.i0(), half);
    }

    @Override
    public int nextStep$mcI$sp() {
        if (this.hasStep()) {
            int ans = BoxesRunTime.unboxToInt(this.scala$collection$convert$impl$IntTableStepper$$extract.apply(this.myCurrent()));
            this.myCurrent_$eq(this.scala$collection$convert$impl$IntTableStepper$$iterate.apply(this.myCurrent()));
            return ans;
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    public IntTableStepper(int _maxLength, I[] _table, Function1<I, I> iterate, Function1<I, Object> extract, int _i0, int _iN) {
        this.scala$collection$convert$impl$IntTableStepper$$iterate = iterate;
        this.scala$collection$convert$impl$IntTableStepper$$extract = extract;
        super(_maxLength, _table, _i0, _iN);
    }
}

