/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.generic;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import scala.Function1;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.generic.SerializeEnd$;
import scala.collection.mutable.Builder;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.IntRef;

@ScalaSignature(bytes="\u0006\u000594AAC\u0006\u0003%!AA\u0005\u0001B\u0001B\u0003%Q\u0005\u0003\u00055\u0001\t\u0005\t\u0015!\u00036\u0011\u0015a\u0004\u0001\"\u0001>\u0011%\u0011\u0005\u00011AA\u0002\u0013E1\tC\u0005K\u0001\u0001\u0007\t\u0019!C\t\u0017\"I\u0011\u000b\u0001a\u0001\u0002\u0003\u0006K\u0001\u0012\u0005\u0007'\u0002\u0001K\u0011\u0002+\t\r}\u0003\u0001\u0015\"\u0003a\u0011\u00191\u0007\u0001)C\tO\nIB)\u001a4bk2$8+\u001a:jC2L'0\u0019;j_:\u0004&o\u001c=z\u0015\taQ\"A\u0004hK:,'/[2\u000b\u00059y\u0011AC2pY2,7\r^5p]*\t\u0001#A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005MY3c\u0001\u0001\u00151A\u0011QCF\u0007\u0002\u001f%\u0011qc\u0004\u0002\u0007\u0003:L(+\u001a4\u0011\u0005e\tcB\u0001\u000e \u001d\tYb$D\u0001\u001d\u0015\ti\u0012#\u0001\u0004=e>|GOP\u0005\u0002!%\u0011\u0001eD\u0001\ba\u0006\u001c7.Y4f\u0013\t\u00113E\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002!\u001f\u00059a-Y2u_JL\b\u0003\u0002\u0014(SEj\u0011!D\u0005\u0003Q5\u0011qAR1di>\u0014\u0018\u0010\u0005\u0002+W1\u0001A!\u0002\u0017\u0001\u0005\u0004i#!A!\u0012\u00059\n\u0004CA\u000b0\u0013\t\u0001tBA\u0004O_RD\u0017N\\4\u0011\u0005U\u0011\u0014BA\u001a\u0010\u0005\r\te._\u0001\u0005G>dG\u000eE\u0002'm%J!aN\u0007\u0003\u0011%#XM]1cY\u0016D#AA\u001d\u0011\u0005UQ\u0014BA\u001e\u0010\u0005%!(/\u00198tS\u0016tG/\u0001\u0004=S:LGO\u0010\u000b\u0004}\u0001\u000b\u0005cA \u0001S5\t1\u0002C\u0003%\u0007\u0001\u0007Q\u0005C\u00035\u0007\u0001\u0007Q'A\u0004ck&dG-\u001a:\u0016\u0003\u0011\u0003B!\u0012%*c5\taI\u0003\u0002H\u001b\u00059Q.\u001e;bE2,\u0017BA%G\u0005\u001d\u0011U/\u001b7eKJ\f1BY;jY\u0012,'o\u0018\u0013fcR\u0011Aj\u0014\t\u0003+5K!AT\b\u0003\tUs\u0017\u000e\u001e\u0005\b!\u0016\t\t\u00111\u0001E\u0003\rAH%M\u0001\tEVLG\u000eZ3sA!\u0012a!O\u0001\foJLG/Z(cU\u0016\u001cG\u000f\u0006\u0002M+\")ak\u0002a\u0001/\u0006\u0019q.\u001e;\u0011\u0005akV\"A-\u000b\u0005i[\u0016AA5p\u0015\u0005a\u0016\u0001\u00026bm\u0006L!AX-\u0003%=\u0013'.Z2u\u001fV$\b/\u001e;TiJ,\u0017-\\\u0001\u000be\u0016\fGm\u00142kK\u000e$HC\u0001'b\u0011\u0015\u0011\u0007\u00021\u0001d\u0003\tIg\u000e\u0005\u0002YI&\u0011Q-\u0017\u0002\u0012\u001f\nTWm\u0019;J]B,Ho\u0015;sK\u0006l\u0017a\u0003:fC\u0012\u0014Vm]8mm\u0016$\u0012!\r\u0015\u0005\u0001%dW\u000e\u0005\u0002\u0016U&\u00111n\u0004\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012a\u0001")
public final class DefaultSerializationProxy<A>
implements Serializable {
    private static final long serialVersionUID = 3L;
    private final Factory<A, Object> factory;
    private final transient Iterable<A> coll;
    private transient Builder<A, Object> builder;

    public Builder<A, Object> builder() {
        return this.builder;
    }

    public void builder_$eq(Builder<A, Object> x$1) {
        this.builder = x$1;
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        int k = this.coll.knownSize();
        out.writeInt(k);
        int create_e = 0;
        IntRef count = new IntRef(create_e);
        this.coll.foreach((Function1<Object, Object> & Serializable)x -> {
            DefaultSerializationProxy.$anonfun$writeObject$1(out, count, x);
            return BoxedUnit.UNIT;
        });
        if (k >= 0) {
            if (count.elem != k) {
                throw new IllegalStateException(new StringBuilder(38).append("Illegal size ").append(count.elem).append(" of collection, expected ").append(k).toString());
            }
            return;
        }
        out.writeObject(SerializeEnd$.MODULE$);
    }

    /*
     * WARNING - void declaration
     */
    private void readObject(ObjectInputStream in) {
        in.defaultReadObject();
        this.builder_$eq(this.factory.newBuilder());
        int k = in.readInt();
        if (k >= 0) {
            this.builder().sizeHint(k);
            for (int count = 0; count < k; ++count) {
                void $plus$eq_elem;
                Builder<void, Object> builder = this.builder();
                Object object = in.readObject();
                if (builder == null) {
                    throw null;
                }
                builder.addOne($plus$eq_elem);
                object = null;
            }
            return;
        }
        Object object;
        while (!SerializeEnd$.MODULE$.equals(object = in.readObject())) {
            Builder<Object, Object> builder = this.builder();
            if (builder == null) {
                throw null;
            }
            Builder cfr_ignored_0 = (Builder)builder.addOne(object);
        }
        return;
    }

    public Object readResolve() {
        return this.builder().result();
    }

    public static final /* synthetic */ void $anonfun$writeObject$1(ObjectOutputStream out$1, IntRef count$1, Object x) {
        out$1.writeObject(x);
        ++count$1.elem;
    }

    public DefaultSerializationProxy(Factory<A, Object> factory2, Iterable<A> coll) {
        this.factory = factory2;
        this.coll = coll;
    }
}

