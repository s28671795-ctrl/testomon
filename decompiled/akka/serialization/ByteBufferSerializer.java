/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import java.io.NotSerializableException;
import java.nio.ByteBuffer;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q2qa\u0001\u0003\u0011\u0002G\u0005\u0011\u0002C\u0003\u0011\u0001\u0019\u0005\u0011\u0003C\u0003\"\u0001\u0019\u0005!E\u0001\u000bCsR,')\u001e4gKJ\u001cVM]5bY&TXM\u001d\u0006\u0003\u000b\u0019\tQb]3sS\u0006d\u0017N_1uS>t'\"A\u0004\u0002\t\u0005\\7.Y\u0002\u0001'\t\u0001!\u0002\u0005\u0002\f\u001d5\tABC\u0001\u000e\u0003\u0015\u00198-\u00197b\u0013\tyAB\u0001\u0004B]f\u0014VMZ\u0001\ti>\u0014\u0015N\\1ssR\u0019!#F\f\u0011\u0005-\u0019\u0012B\u0001\u000b\r\u0005\u0011)f.\u001b;\t\u000bY\t\u0001\u0019\u0001\u0006\u0002\u0003=DQ\u0001G\u0001A\u0002e\t1AY;g!\tQr$D\u0001\u001c\u0015\taR$A\u0002oS>T\u0011AH\u0001\u0005U\u00064\u0018-\u0003\u0002!7\tQ!)\u001f;f\u0005V4g-\u001a:\u0002\u0015\u0019\u0014x.\u001c\"j]\u0006\u0014\u0018\u0010F\u0002\u000bG\u0011BQ\u0001\u0007\u0002A\u0002eAQ!\n\u0002A\u0002\u0019\n\u0001\"\\1oS\u001a,7\u000f\u001e\t\u0003O9r!\u0001\u000b\u0017\u0011\u0005%bQ\"\u0001\u0016\u000b\u0005-B\u0011A\u0002\u001fs_>$h(\u0003\u0002.\u0019\u00051\u0001K]3eK\u001aL!a\f\u0019\u0003\rM#(/\u001b8h\u0015\tiC\u0002K\u0002\u0003em\u00022aC\u001a6\u0013\t!DB\u0001\u0004uQJ|wo\u001d\t\u0003mej\u0011a\u000e\u0006\u0003qu\t!![8\n\u0005i:$\u0001\u0007(piN+'/[1mSj\f'\r\\3Fq\u000e,\u0007\u000f^5p]\u000e\nQ\u0007")
public interface ByteBufferSerializer {
    public void toBinary(Object var1, ByteBuffer var2);

    public Object fromBinary(ByteBuffer var1, String var2) throws NotSerializableException;
}

