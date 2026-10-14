/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import java.nio.ByteBuffer;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00052qa\u0001\u0003\u0011\u0002G\u0005\u0011\u0002C\u0003\u0011\u0001\u0019\u0005\u0011\u0003C\u0003\u001b\u0001\u0019\u00051D\u0001\u0006Ck\u001a4WM\u001d)p_2T!!\u0002\u0004\u0002\u0005%|'\"A\u0004\u0002\t\u0005\\7.Y\u0002\u0001'\t\u0001!\u0002\u0005\u0002\f\u001d5\tABC\u0001\u000e\u0003\u0015\u00198-\u00197b\u0013\tyAB\u0001\u0004B]f\u0014VMZ\u0001\bC\u000e\fX/\u001b:f)\u0005\u0011\u0002CA\n\u0019\u001b\u0005!\"BA\u000b\u0017\u0003\rq\u0017n\u001c\u0006\u0002/\u0005!!.\u0019<b\u0013\tIBC\u0001\u0006CsR,')\u001e4gKJ\fqA]3mK\u0006\u001cX\r\u0006\u0002\u001d?A\u00111\"H\u0005\u0003=1\u0011A!\u00168ji\")\u0001E\u0001a\u0001%\u0005\u0019!-\u001e4")
public interface BufferPool {
    public ByteBuffer acquire();

    public void release(ByteBuffer var1);
}

