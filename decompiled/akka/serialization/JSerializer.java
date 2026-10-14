/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.serialization.Serializer;
import java.io.NotSerializableException;
import scala.$less$colon$less$;
import scala.Option;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q3Q\u0001B\u0003\u0002\u0002)AQ!\u0006\u0001\u0005\u0002YAQ\u0001\u0007\u0001\u0005\u0006eAQa\u0013\u0001\u0007\u00121\u00131BS*fe&\fG.\u001b>fe*\u0011aaB\u0001\u000eg\u0016\u0014\u0018.\u00197ju\u0006$\u0018n\u001c8\u000b\u0003!\tA!Y6lC\u000e\u00011c\u0001\u0001\f#A\u0011AbD\u0007\u0002\u001b)\ta\"A\u0003tG\u0006d\u0017-\u0003\u0002\u0011\u001b\t1\u0011I\\=SK\u001a\u0004\"AE\n\u000e\u0003\u0015I!\u0001F\u0003\u0003\u0015M+'/[1mSj,'/\u0001\u0004=S:LGO\u0010\u000b\u0002/A\u0011!\u0003A\u0001\u000bMJ|WNQ5oCJLHcA\u0006\u001bE!)1D\u0001a\u00019\u0005)!-\u001f;fgB\u0019A\"H\u0010\n\u0005yi!!B!se\u0006L\bC\u0001\u0007!\u0013\t\tSB\u0001\u0003CsR,\u0007\"B\u0012\u0003\u0001\u0004!\u0013\u0001C7b]&4Wm\u001d;\u0011\u00071)s%\u0003\u0002'\u001b\t1q\n\u001d;j_:\u0004$\u0001K\u001b\u0011\u0007%\u00024G\u0004\u0002+]A\u00111&D\u0007\u0002Y)\u0011Q&C\u0001\u0007yI|w\u000e\u001e \n\u0005=j\u0011A\u0002)sK\u0012,g-\u0003\u00022e\t)1\t\\1tg*\u0011q&\u0004\t\u0003iUb\u0001\u0001B\u00057E\u0005\u0005\t\u0011!B\u0001o\t\u0019q\fJ\u001b\u0012\u0005aZ\u0004C\u0001\u0007:\u0013\tQTBA\u0004O_RD\u0017N\\4\u0011\u00051a\u0014BA\u001f\u000e\u0005\r\te.\u001f\u0015\u0004\u0005}R\u0005c\u0001\u0007A\u0005&\u0011\u0011)\u0004\u0002\u0007i\"\u0014xn^:\u0011\u0005\rCU\"\u0001#\u000b\u0005\u00153\u0015AA5p\u0015\u00059\u0015\u0001\u00026bm\u0006L!!\u0013#\u000319{GoU3sS\u0006d\u0017N_1cY\u0016,\u0005pY3qi&|gnI\u0001C\u000391'o\\7CS:\f'/\u001f&bm\u0006$2aC'O\u0011\u0015Y2\u00011\u0001\u001d\u0011\u0015\u00193\u00011\u0001Pa\t\u0001&\u000bE\u0002*aE\u0003\"\u0001\u000e*\u0005\u0013Ms\u0015\u0011!A\u0001\u0006\u00039$aA0%m\u0001")
public abstract class JSerializer
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
    public final Object fromBinary(byte[] bytes, Option<Class<?>> manifest) throws NotSerializableException {
        return this.fromBinaryJava(bytes, (Class)manifest.orNull($less$colon$less$.MODULE$.refl()));
    }

    public abstract Object fromBinaryJava(byte[] var1, Class<?> var2);

    public JSerializer() {
        Serializer.$init$(this);
    }
}

