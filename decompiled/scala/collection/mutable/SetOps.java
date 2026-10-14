/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.collection.Set;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Cloneable;
import scala.collection.mutable.Growable;
import scala.collection.mutable.Shrinkable;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005y4qa\u0003\u0007\u0011\u0002\u0007\u00051\u0003C\u0003C\u0001\u0011\u00051\tC\u0003H\u0001\u0011\u0005\u0001\nC\u0003J\u0001\u0011\u0005!\nC\u0003Q\u0001\u0011\u0005\u0011\u000bC\u0003V\u0001\u0011\u0005a\u000bC\u0003Y\u0001\u0011\u0005\u0011\fC\u0003`\u0001\u0011\u0015\u0001\rC\u0003u\u0001\u0011\u0005Q\u000fC\u0003y\u0001\u0011\u0005\u0003\nC\u0003z\u0001\u0011\u0005#P\u0001\u0004TKR|\u0005o\u001d\u0006\u0003\u001b9\tq!\\;uC\ndWM\u0003\u0002\u0010!\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003E\tQa]2bY\u0006\u001c\u0001!\u0006\u0003\u0015=!r3\u0003\u0003\u0001\u00163M2\u0014\bP \u0011\u0005Y9R\"\u0001\t\n\u0005a\u0001\"AB!osJ+g\rE\u0003\u001b7q9S&D\u0001\u000f\u0013\tYa\u0002\u0005\u0002\u001e=1\u0001A!B\u0010\u0001\u0005\u0004\u0001#!A!\u0012\u0005\u0005\"\u0003C\u0001\f#\u0013\t\u0019\u0003CA\u0004O_RD\u0017N\\4\u0011\u0005Y)\u0013B\u0001\u0014\u0011\u0005\r\te.\u001f\t\u0003;!\"a!\u000b\u0001\u0005\u0006\u0004Q#AA\"D+\t\u00013\u0006B\u0003-Q\t\u0007\u0001EA\u0001Y!\tib\u0006\u0002\u00040\u0001\u0011\u0015\r\u0001\r\u0002\u0002\u0007F\u0011\u0011%\r\t\u0006e\u0001ar%L\u0007\u0002\u0019A)!\u0004\u000e\u000f([%\u0011QG\u0004\u0002\f\u0013R,'/\u00192mK>\u00038\u000fE\u00023o5J!\u0001\u000f\u0007\u0003\u0013\rcwN\\3bE2,\u0007\u0003\u0002\u001a;95J!a\u000f\u0007\u0003\u000f\t+\u0018\u000e\u001c3feB\u0019!'\u0010\u000f\n\u0005yb!\u0001C$s_^\f'\r\\3\u0011\u0007I\u0002E$\u0003\u0002B\u0019\tQ1\u000b\u001b:j].\f'\r\\3\u0002\r\u0011Jg.\u001b;%)\u0005!\u0005C\u0001\fF\u0013\t1\u0005C\u0001\u0003V]&$\u0018A\u0002:fgVdG\u000fF\u0001.\u0003\r\tG\r\u001a\u000b\u0003\u0017:\u0003\"A\u0006'\n\u00055\u0003\"a\u0002\"p_2,\u0017M\u001c\u0005\u0006\u001f\u000e\u0001\r\u0001H\u0001\u0005K2,W.\u0001\u0004va\u0012\fG/\u001a\u000b\u0004\tJ\u001b\u0006\"B(\u0005\u0001\u0004a\u0002\"\u0002+\u0005\u0001\u0004Y\u0015\u0001C5oG2,H-\u001a3\u0002\rI,Wn\u001c<f)\tYu\u000bC\u0003P\u000b\u0001\u0007A$\u0001\u0003eS\u001a4GCA\u0017[\u0011\u0015Yf\u00011\u0001]\u0003\u0011!\b.\u0019;\u0011\u0007iiF$\u0003\u0002_\u001d\t\u00191+\u001a;\u0002\rI,G/Y5o)\t!\u0015\rC\u0003c\u000f\u0001\u00071-A\u0001q!\u00111B\rH&\n\u0005\u0015\u0004\"!\u0003$v]\u000e$\u0018n\u001c82Q\t9q\r\u0005\u0002\u0017Q&\u0011\u0011\u000e\u0005\u0002\u0007S:d\u0017N\\3)\r\u001dYgn\\9s!\t1B.\u0003\u0002n!\tQA-\u001a9sK\u000e\fG/\u001a3\u0002\u000f5,7o]1hK\u0006\n\u0001/A\rVg\u0016\u0004c-\u001b7uKJLe\u000e\u00157bG\u0016\u0004\u0013N\\:uK\u0006$\u0017!B:j]\u000e,\u0017%A:\u0002\rIr\u0013g\r\u00181\u000351\u0017\u000e\u001c;fe&s\u0007\u000b\\1dKR\u0011ao^\u0007\u0002\u0001!)!\r\u0003a\u0001G\u0006)1\r\\8oK\u0006I1N\\8x]NK'0Z\u000b\u0002wB\u0011a\u0003`\u0005\u0003{B\u00111!\u00138u\u0001")
public interface SetOps<A, CC, C extends SetOps<A, CC, C>>
extends scala.collection.SetOps<A, CC, C>,
Cloneable<C>,
Builder<A, C>,
Shrinkable<A> {
    public static /* synthetic */ SetOps result$(SetOps $this) {
        return $this.result();
    }

    @Override
    default public C result() {
        return (C)((SetOps)this.coll());
    }

    public static /* synthetic */ boolean add$(SetOps $this, Object elem) {
        return $this.add(elem);
    }

    default public boolean add(A elem) {
        if (!this.contains(elem)) {
            Growable growable = (Growable)this.coll();
            if (growable == null) {
                throw null;
            }
            growable.addOne(elem);
            return true;
        }
        return false;
    }

    public static /* synthetic */ void update$(SetOps $this, Object elem, boolean included) {
        $this.update(elem, included);
    }

    default public void update(A elem, boolean included) {
        if (included) {
            this.add(elem);
            return;
        }
        this.remove(elem);
    }

    public static /* synthetic */ boolean remove$(SetOps $this, Object elem) {
        return $this.remove(elem);
    }

    default public boolean remove(A elem) {
        boolean res = this.contains(elem);
        Shrinkable shrinkable = (Shrinkable)this.coll();
        if (shrinkable == null) {
            throw null;
        }
        shrinkable.subtractOne(elem);
        return res;
    }

    public static /* synthetic */ SetOps diff$(SetOps $this, Set that) {
        return $this.diff(that);
    }

    @Override
    default public C diff(Set<A> that) {
        return (C)((SetOps)this.foldLeft(this.empty(), (Function2<SetOps, Object, SetOps> & Serializable)(result, elem) -> {
            if (that.contains(elem)) {
                return result;
            }
            if (result == null) {
                throw null;
            }
            return (SetOps)result.addOne(elem);
        }));
    }

    public static /* synthetic */ void retain$(SetOps $this, Function1 p) {
        $this.retain(p);
    }

    default public void retain(Function1<A, Object> p) {
        this.filterInPlace(p);
    }

    public static /* synthetic */ SetOps filterInPlace$(SetOps $this, Function1 p) {
        return $this.filterInPlace(p);
    }

    default public SetOps<A, CC, C> filterInPlace(Function1<A, Object> p) {
        if (this.nonEmpty()) {
            for (Object elem : (Object[])this.toArray(ClassTag$.MODULE$.Any())) {
                if (BoxesRunTime.unboxToBoolean(p.apply(elem))) continue;
                this.subtractOne(elem);
            }
        }
        return this;
    }

    public static /* synthetic */ SetOps clone$(SetOps $this) {
        return $this.clone();
    }

    @Override
    default public C clone() {
        Growable growable = (Growable)this.empty();
        if (growable == null) {
            throw null;
        }
        return (C)((SetOps)growable.addAll(this));
    }

    public static /* synthetic */ int knownSize$(SetOps $this) {
        return $this.knownSize();
    }

    @Override
    default public int knownSize() {
        return -1;
    }

    public static void $init$(SetOps $this) {
    }
}

