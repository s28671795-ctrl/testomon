/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.serialization.Serializer;
import java.io.NotSerializableException;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00154Q\u0001C\u0005\u0002\u00029AQ!\u0007\u0001\u0005\u0002iAQ\u0001\b\u0001\u0007\u0002uAQ!\t\u0001\u0005F\tBQA\n\u0001\u0007\u0002\u001dBQ!\u000e\u0001\u0007\u0002YBQA\u0010\u0001\u0007\u0002}BQA\u0010\u0001\u0005\u0006A\u0013AdU3sS\u0006d\u0017N_3s/&$\bn\u0015;sS:<W*\u00198jM\u0016\u001cHO\u0003\u0002\u000b\u0017\u0005i1/\u001a:jC2L'0\u0019;j_:T\u0011\u0001D\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u0007\u0001yQ\u0003\u0005\u0002\u0011'5\t\u0011CC\u0001\u0013\u0003\u0015\u00198-\u00197b\u0013\t!\u0012C\u0001\u0004B]f\u0014VM\u001a\t\u0003-]i\u0011!C\u0005\u00031%\u0011!bU3sS\u0006d\u0017N_3s\u0003\u0019a\u0014N\\5u}Q\t1\u0004\u0005\u0002\u0017\u0001\u0005Q\u0011\u000eZ3oi&4\u0017.\u001a:\u0016\u0003y\u0001\"\u0001E\u0010\n\u0005\u0001\n\"aA%oi\u0006y\u0011N\\2mk\u0012,W*\u00198jM\u0016\u001cH/F\u0001$!\t\u0001B%\u0003\u0002&#\t9!i\\8mK\u0006t\u0017\u0001C7b]&4Wm\u001d;\u0015\u0005!\u001a\u0004CA\u00151\u001d\tQc\u0006\u0005\u0002,#5\tAF\u0003\u0002.\u001b\u00051AH]8pizJ!aL\t\u0002\rA\u0013X\rZ3g\u0013\t\t$G\u0001\u0004TiJLgn\u001a\u0006\u0003_EAQ\u0001\u000e\u0003A\u0002=\t\u0011a\\\u0001\ti>\u0014\u0015N\\1ssR\u0011q'\u0010\t\u0004!aR\u0014BA\u001d\u0012\u0005\u0015\t%O]1z!\t\u00012(\u0003\u0002=#\t!!)\u001f;f\u0011\u0015!T\u00011\u0001\u0010\u0003)1'o\\7CS:\f'/\u001f\u000b\u0004\u001f\u0001\u0013\u0005\"B!\u0007\u0001\u00049\u0014!\u00022zi\u0016\u001c\b\"\u0002\u0014\u0007\u0001\u0004A\u0003f\u0001\u0004E\u001fB\u0019\u0001#R$\n\u0005\u0019\u000b\"A\u0002;ie><8\u000f\u0005\u0002I\u001b6\t\u0011J\u0003\u0002K\u0017\u0006\u0011\u0011n\u001c\u0006\u0002\u0019\u0006!!.\u0019<b\u0013\tq\u0015J\u0001\rO_R\u001cVM]5bY&T\u0018M\u00197f\u000bb\u001cW\r\u001d;j_:\u001c\u0013a\u0012\u000b\u0004\u001fE\u0013\u0006\"B!\b\u0001\u00049\u0004\"\u0002\u0014\b\u0001\u0004\u0019\u0006c\u0001\tU-&\u0011Q+\u0005\u0002\u0007\u001fB$\u0018n\u001c81\u0005]c\u0006cA\u0015Y5&\u0011\u0011L\r\u0002\u0006\u00072\f7o\u001d\t\u00037rc\u0001\u0001B\u0005^%\u0006\u0005\t\u0011!B\u0001=\n\u0019q\fJ\u001a\u0012\u0005}\u0013\u0007C\u0001\ta\u0013\t\t\u0017CA\u0004O_RD\u0017N\\4\u0011\u0005A\u0019\u0017B\u00013\u0012\u0005\r\te.\u001f")
public abstract class SerializerWithStringManifest
implements Serializer {
    @Override
    public final Object fromBinary(byte[] bytes) {
        return Serializer.fromBinary$(this, bytes);
    }

    @Override
    public final Object fromBinary(byte[] bytes, Class<?> clazz) throws NotSerializableException {
        return Serializer.fromBinary$(this, bytes, clazz);
    }

    @Override
    public abstract int identifier();

    @Override
    public final boolean includeManifest() {
        return true;
    }

    public abstract String manifest(Object var1);

    @Override
    public abstract byte[] toBinary(Object var1);

    public abstract Object fromBinary(byte[] var1, String var2) throws NotSerializableException;

    @Override
    public final Object fromBinary(byte[] bytes, Option<Class<?>> manifest) {
        String string;
        Option<Class<?>> option = manifest;
        if (option instanceof Some) {
            Some some = (Some)option;
            Class c = (Class)some.value();
            string = c.getName();
        } else if (None$.MODULE$.equals(option)) {
            string = "";
        } else {
            throw new MatchError(option);
        }
        String manifestString = string;
        return this.fromBinary(bytes, manifestString);
    }

    public SerializerWithStringManifest() {
        Serializer.$init$(this);
    }
}

