/*
 * Decompiled with CFR 0.152.
 */
package scala.util;

import scala.Function0;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00014A\u0001C\u0005\u0001\u001d!AA\u0003\u0001B\u0001B\u0003%Q\u0003C\u0003!\u0001\u0011\u0005\u0011\u0005\u0003\u0004&\u0001\u0001\u0006IA\n\u0005\u0006{\u0001!\tA\u0010\u0005\u0006\u007f\u0001!\t\u0001\u0011\u0005\u0006\u001b\u0002!\tA\u0014\u0005\u0006'\u0002!\t\u0005\u0016\u0002\u0010\tft\u0017-\\5d-\u0006\u0014\u0018.\u00192mK*\u0011!bC\u0001\u0005kRLGNC\u0001\r\u0003\u0015\u00198-\u00197b\u0007\u0001)\"aD\f\u0014\u0005\u0001\u0001\u0002CA\t\u0013\u001b\u0005Y\u0011BA\n\f\u0005\u0019\te.\u001f*fM\u0006!\u0011N\\5u!\t1r\u0003\u0004\u0001\u0005\u000ba\u0001!\u0019A\r\u0003\u0003Q\u000b\"AG\u000f\u0011\u0005EY\u0012B\u0001\u000f\f\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!\u0005\u0010\n\u0005}Y!aA!os\u00061A(\u001b8jiz\"\"A\t\u0013\u0011\u0007\r\u0002Q#D\u0001\n\u0011\u0015!\"\u00011\u0001\u0016\u0003\t!HN\u0005\u0002(S\u0019!\u0001f\u0001\u0001'\u00051a$/\u001a4j]\u0016lWM\u001c;?!\rQs&F\u0007\u0002W)\u0011A&L\u0001\u0005Y\u0006twMC\u0001/\u0003\u0011Q\u0017M^1\n\u0005AZ#AF%oQ\u0016\u0014\u0018\u000e^1cY\u0016$\u0006N]3bI2{7-\u00197\t\u000bI:C\u0011I\u001a\u0002\u0019%t\u0017\u000e^5bYZ\u000bG.^3\u0015\u0003Q\u00122!N\u000b\u0011\r\u0011Ac\u0007\u0001\u001b\u0007\t]\u001a!\u0001\u000f\u0002\u0006I\u0005twN\\\n\u0003m%BQ\u0001\t\u001c\u0005\u0002i\"\u0012a\u000f\t\u0003-YBQA\r\u001c\u0005BM\nQA^1mk\u0016,\u0012!F\u0001\no&$\bNV1mk\u0016,\"!\u0011#\u0015\u0005\t[ECA\"G!\t1B\tB\u0003F\u000b\t\u0007\u0011DA\u0001T\u0011\u00199U\u0001\"a\u0001\u0011\u0006)A\u000f[;oWB\u0019\u0011#S\"\n\u0005)[!\u0001\u0003\u001fcs:\fW.\u001a \t\u000b1+\u0001\u0019A\u000b\u0002\r9,wO^1m\u0003%1\u0018\r\\;f?\u0012*\u0017\u000f\u0006\u0002P%B\u0011\u0011\u0003U\u0005\u0003#.\u0011A!\u00168ji\")AJ\u0002a\u0001+\u0005AAo\\*ue&tw\rF\u0001V!\t1VL\u0004\u0002X7B\u0011\u0001lC\u0007\u00023*\u0011!,D\u0001\u0007yI|w\u000e\u001e \n\u0005q[\u0011A\u0002)sK\u0012,g-\u0003\u0002_?\n11\u000b\u001e:j]\u001eT!\u0001X\u0006")
public class DynamicVariable<T> {
    public final T scala$util$DynamicVariable$$init;
    private final InheritableThreadLocal<T> tl;

    public T value() {
        return this.tl.get();
    }

    public <S> S withValue(T newval, Function0<S> thunk) {
        S s2;
        T oldval = this.value();
        this.tl.set(newval);
        try {
            s2 = thunk.apply();
        }
        finally {
            this.tl.set(oldval);
        }
        return s2;
    }

    public void value_$eq(T newval) {
        this.tl.set(newval);
    }

    public String toString() {
        return new StringBuilder(17).append("DynamicVariable(").append(this.value()).append(")").toString();
    }

    public DynamicVariable(T init2) {
        this.scala$util$DynamicVariable$$init = init2;
        this.tl = new InheritableThreadLocal<T>(this){
            private final /* synthetic */ DynamicVariable $outer;

            public Object initialValue() {
                return this.$outer.scala$util$DynamicVariable$$init;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }
}

