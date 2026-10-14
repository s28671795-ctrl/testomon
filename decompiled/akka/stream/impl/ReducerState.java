/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A2\u0001b\u0001\u0003\u0011\u0002G\u0005\u0001B\u0003\u0005\u0006%\u00011\t\u0001\u0006\u0005\u0006O\u00011\t\u0001\u000b\u0002\r%\u0016$WoY3s'R\fG/\u001a\u0006\u0003\u000b\u0019\tA![7qY*\u0011q\u0001C\u0001\u0007gR\u0014X-Y7\u000b\u0003%\tA!Y6lCV\u00191\"G\u0012\u0014\u0005\u0001a\u0001CA\u0007\u0011\u001b\u0005q!\"A\b\u0002\u000bM\u001c\u0017\r\\1\n\u0005Eq!AB!osJ+g-\u0001\u0004va\u0012\fG/Z\u0002\u0001)\t)R\u0005\u0005\u0003\u0017\u0001]\u0011S\"\u0001\u0003\u0011\u0005aIB\u0002\u0001\u0003\u00065\u0001\u0011\ra\u0007\u0002\u0002)F\u0011Ad\b\t\u0003\u001buI!A\b\b\u0003\u000f9{G\u000f[5oOB\u0011Q\u0002I\u0005\u0003C9\u00111!\u00118z!\tA2\u0005B\u0003%\u0001\t\u00071DA\u0001S\u0011\u00151\u0013\u00011\u0001 \u0003\u0015\u0011\u0017\r^2i\u0003\u00191\u0017N\\5tQR\t!\u0005\u000b\u0002\u0001UA\u00111FL\u0007\u0002Y)\u0011Q\u0006C\u0001\u000bC:tw\u000e^1uS>t\u0017BA\u0018-\u0005-Ie\u000e^3s]\u0006d\u0017\t]5")
@InternalApi
public interface ReducerState<T, R> {
    public ReducerState<T, R> update(Object var1);

    public R finish();
}

