/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.annotation.DoNotInherit;
import akka.annotation.InternalApi;
import akka.stream.DelayOverflowStrategy$;
import java.io.Serializable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005u3QAD\b\u0002\"QAQa\n\u0001\u0005\u0002!Baa\u000b\u0001\u0007\u0002Eas!\u0002\"\u0010\u0011\u0003\u0019e!\u0002\b\u0010\u0011\u0003!\u0005\"B\u0014\u0005\t\u0003a\u0005\"B'\u0005\t\u0003q\u0005\"B(\u0005\t\u0003q\u0005\"\u0002)\u0005\t\u0003q\u0005\"B)\u0005\t\u0003q\u0005\"\u0002*\u0005\t\u0003q\u0005\"B*\u0005\t\u0003q\u0005\"\u0002+\u0005\t\u0003q\u0005bB+\u0005\u0003\u0003%IA\u0016\u0002\u0016\t\u0016d\u0017-_(wKJ4Gn\\<TiJ\fG/Z4z\u0015\t\u0001\u0012#\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002%\u0005!\u0011m[6b\u0007\u0001\u00192\u0001A\u000b\u001c!\t1\u0012$D\u0001\u0018\u0015\u0005A\u0012!B:dC2\f\u0017B\u0001\u000e\u0018\u0005\u0019\te.\u001f*fMB\u0011A\u0004\n\b\u0003;\tr!AH\u0011\u000e\u0003}Q!\u0001I\n\u0002\rq\u0012xn\u001c;?\u0013\u0005A\u0012BA\u0012\u0018\u0003\u001d\u0001\u0018mY6bO\u0016L!!\n\u0014\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005\r:\u0012A\u0002\u001fj]&$h\bF\u0001*!\tQ\u0003!D\u0001\u0010\u00039I7OQ1dWB\u0014Xm]:ve\u0016,\u0012!\f\t\u0003-9J!aL\f\u0003\u000f\t{w\u000e\\3b]\"\u0012!!\r\t\u0003eUj\u0011a\r\u0006\u0003iE\t!\"\u00198o_R\fG/[8o\u0013\t14GA\u0006J]R,'O\\1m\u0003BL\u0017f\u0001\u00019y)\u0011\u0011HO\u0001\n\u000b6LG/R1sYfT!aO\b\u0002%=3XM\u001d4m_^\u001cFO]1uK\u001eLWm]\u0005\u0003{=\u0011\u0001c\u0014<fe\u001adwn^*ue\u0006$XmZ=)\u0005\u0001y\u0004C\u0001\u001aA\u0013\t\t5G\u0001\u0007E_:{G/\u00138iKJLG/A\u000bEK2\f\u0017p\u0014<fe\u001adwn^*ue\u0006$XmZ=\u0011\u0005)\"1c\u0001\u0003\u0016\u000bB\u0011aiS\u0007\u0002\u000f*\u0011\u0001*S\u0001\u0003S>T\u0011AS\u0001\u0005U\u00064\u0018-\u0003\u0002&\u000fR\t1)A\u0005f[&$X)\u0019:msV\t\u0011&\u0001\u0005ee>\u0004\b*Z1e\u0003!!'o\u001c9UC&d\u0017A\u00033s_B\u0014UO\u001a4fe\u00069AM]8q\u001d\u0016<\u0018\u0001\u00042bG.\u0004(/Z:tkJ,\u0017\u0001\u00024bS2\fAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\u0012a\u0016\t\u00031nk\u0011!\u0017\u0006\u00035&\u000bA\u0001\\1oO&\u0011A,\u0017\u0002\u0007\u001f\nTWm\u0019;")
@DoNotInherit
public abstract class DelayOverflowStrategy
implements Serializable {
    public static DelayOverflowStrategy fail() {
        return DelayOverflowStrategy$.MODULE$.fail();
    }

    public static DelayOverflowStrategy backpressure() {
        return DelayOverflowStrategy$.MODULE$.backpressure();
    }

    public static DelayOverflowStrategy dropNew() {
        return DelayOverflowStrategy$.MODULE$.dropNew();
    }

    public static DelayOverflowStrategy dropBuffer() {
        return DelayOverflowStrategy$.MODULE$.dropBuffer();
    }

    public static DelayOverflowStrategy dropTail() {
        return DelayOverflowStrategy$.MODULE$.dropTail();
    }

    public static DelayOverflowStrategy dropHead() {
        return DelayOverflowStrategy$.MODULE$.dropHead();
    }

    public static DelayOverflowStrategy emitEarly() {
        return DelayOverflowStrategy$.MODULE$.emitEarly();
    }

    @InternalApi
    public abstract boolean isBackpressure();
}

