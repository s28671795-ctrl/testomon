/*
 * Decompiled with CFR 0.152.
 */
package scala.sys;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.StringOps$;
import scala.collection.mutable.Map;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.sys.Prop;
import scala.sys.SystemProperties;

@ScalaSignature(bytes="\u0006\u0005\u0005\ra!B\t\u0013\u0001I1\u0002\u0002\u0003\u0017\u0001\u0005\u000b\u0007I\u0011A\u0017\t\u0011e\u0002!\u0011!Q\u0001\n9B\u0001B\u000f\u0001\u0003\u0002\u0003\u0006Ia\u000f\u0005\u0006}\u0001!\ta\u0010\u0005\u0006\u0007\u0002!\t\u0001\u0012\u0005\u0006\u000b\u0002!\tA\u0012\u0005\u0006\u0015\u0002!\ta\u0013\u0005\u0006\u001d\u0002!\ta\u0014\u0005\u0006-\u0002!\t!\f\u0005\u0006/\u0002!\t\u0001\u0017\u0005\u00069\u0002!\t!\u0018\u0005\u0006C\u0002!\tA\u0019\u0005\u0006W\u0002!\t\u0002\u001c\u0005\u0006k\u0002!\t\u0002\u0012\u0005\u0006m\u0002!Ia\u001e\u0005\u0007\u007f\u0002!\t%!\u0001\u0003\u0011A\u0013x\u000e]%na2T!a\u0005\u000b\u0002\u0007ML8OC\u0001\u0016\u0003\u0015\u00198-\u00197b+\t9\"eE\u0002\u00011q\u0001\"!\u0007\u000e\u000e\u0003QI!a\u0007\u000b\u0003\r\u0005s\u0017PU3g!\rib\u0004I\u0007\u0002%%\u0011qD\u0005\u0002\u0005!J|\u0007\u000f\u0005\u0002\"E1\u0001AAB\u0012\u0001\t\u000b\u0007QEA\u0001U\u0007\u0001\t\"AJ\u0015\u0011\u0005e9\u0013B\u0001\u0015\u0015\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!\u0007\u0016\n\u0005-\"\"aA!os\u0006\u00191.Z=\u0016\u00039\u0002\"a\f\u001c\u000f\u0005A\"\u0004CA\u0019\u0015\u001b\u0005\u0011$BA\u001a%\u0003\u0019a$o\\8u}%\u0011Q\u0007F\u0001\u0007!J,G-\u001a4\n\u0005]B$AB*ue&twM\u0003\u00026)\u0005!1.Z=!\u0003\u001d1\u0018\r\\;f\r:\u0004B!\u0007\u001f/A%\u0011Q\b\u0006\u0002\n\rVt7\r^5p]F\na\u0001P5oSRtDc\u0001!B\u0005B\u0019Q\u0004\u0001\u0011\t\u000b1\"\u0001\u0019\u0001\u0018\t\u000bi\"\u0001\u0019A\u001e\u0002\u000bY\fG.^3\u0016\u0003\u0001\nQ![:TKR,\u0012a\u0012\t\u00033!K!!\u0013\u000b\u0003\u000f\t{w\u000e\\3b]\u0006\u00191/\u001a;\u0015\u00059b\u0005\"B'\b\u0001\u0004q\u0013\u0001\u00038foZ\u000bG.^3\u0002\u0011M,GOV1mk\u0016,\"\u0001U*\u0015\u0005\u0001\n\u0006\"B'\t\u0001\u0004\u0011\u0006CA\u0011T\t\u0015!\u0006B1\u0001V\u0005\t!\u0016'\u0005\u0002!S\u0005\u0019q-\u001a;\u0002\u000b\rdW-\u0019:\u0015\u0003e\u0003\"!\u0007.\n\u0005m#\"\u0001B+oSR\faa\u001c9uS>tW#\u00010\u0011\u0007ey\u0006%\u0003\u0002a)\t1q\n\u001d;j_:\f!a\u001c:\u0016\u0005\r,GC\u00013g!\t\tS\rB\u0003U\u0019\t\u0007Q\u000b\u0003\u0004h\u0019\u0011\u0005\r\u0001[\u0001\u0004C2$\bcA\rjI&\u0011!\u000e\u0006\u0002\ty\tLh.Y7f}\u0005QQO\u001c3fe2L\u0018N\\4\u0016\u00035\u0004BA\\:/]5\tqN\u0003\u0002qc\u00069Q.\u001e;bE2,'B\u0001:\u0015\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003i>\u00141!T1q\u0003\u0011QXM]8\u0002\u0013\u001d,Go\u0015;sS:<W#\u0001=\u0011\u0005etX\"\u0001>\u000b\u0005md\u0018\u0001\u00027b]\u001eT\u0011!`\u0001\u0005U\u00064\u0018-\u0003\u00028u\u0006AAo\\*ue&tw\rF\u0001/\u0001")
public class PropImpl<T>
implements Prop<T> {
    private final String key;
    private final Function1<String, T> valueFn;

    @Override
    public String key() {
        return this.key;
    }

    @Override
    public T value() {
        if (this.isSet()) {
            return this.valueFn.apply(this.get());
        }
        return this.zero();
    }

    @Override
    public boolean isSet() {
        return this.underlying().contains(this.key());
    }

    @Override
    public String set(String newValue) {
        String old = this.isSet() ? this.get() : null;
        this.underlying().update(this.key(), newValue);
        return old;
    }

    @Override
    public <T1> T setValue(T1 newValue) {
        T old = this.value();
        if (newValue == null) {
            this.set(null);
        } else {
            this.set(String.valueOf(newValue));
        }
        return old;
    }

    @Override
    public String get() {
        if (this.isSet()) {
            return (String)this.underlying().getOrElse(this.key(), (Function0<String> & Serializable)() -> "");
        }
        return "";
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void clear() {
        void $minus$eq_elem;
        Map<String, String> map = this.underlying();
        String string = this.key();
        if (map == null) {
            throw null;
        }
        map.subtractOne($minus$eq_elem);
    }

    @Override
    public Option<T> option() {
        if (this.isSet()) {
            return new Some<T>(this.value());
        }
        return None$.MODULE$;
    }

    public <T1> T1 or(Function0<T1> alt) {
        if (this.isSet()) {
            return (T1)this.value();
        }
        return alt.apply();
    }

    public Map<String, String> underlying() {
        return new SystemProperties();
    }

    @Override
    public T zero() {
        return null;
    }

    private String getString() {
        if (this.isSet()) {
            return new StringBuilder(11).append("currently: ").append(this.get()).toString();
        }
        return "unset";
    }

    public String toString() {
        return StringOps$.MODULE$.format$extension("%s (%s)", ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.key(), this.getString()}));
    }

    public PropImpl(String key, Function1<String, T> valueFn) {
        this.key = key;
        this.valueFn = valueFn;
    }
}

