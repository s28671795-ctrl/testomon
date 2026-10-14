/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.converterImpl;

import scala.collection.IterableOnce;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005]2qa\u0001\u0003\u0011\u0002\u0007\u0005Q\u0002C\u0003\u0013\u0001\u0011\u00051\u0003C\u0003\u0018\u0001\u0011\r\u0001D\u0001\u0010Qe&|'/\u001b;zg\u0005\u001b7-^7vY\u0006$xN]\"p]Z,'\u000f^3sg*\u0011QAB\u0001\u000eG>tg/\u001a:uKJLU\u000e\u001d7\u000b\u0005\u001dA\u0011!\u00026bm\u0006D$BA\u0005\u000b\u0003\u0019\u0019w.\u001c9bi*\t1\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0005\u0001q\u0001CA\b\u0011\u001b\u0005Q\u0011BA\t\u000b\u0005\u0019\te.\u001f*fM\u00061A%\u001b8ji\u0012\"\u0012\u0001\u0006\t\u0003\u001fUI!A\u0006\u0006\u0003\tUs\u0017\u000e^\u0001\u0018G>dG.Z2uS>t7)\u00198BG\u000e,X.\u001e7bi\u0016,\"!\u0007\u0011\u0015\u0005iI\u0003cA\u000e\u001d=5\tA!\u0003\u0002\u001e\t\t92i\u001c7mK\u000e$\u0018n\u001c8DC:\f5mY;nk2\fG/\u001a\t\u0003?\u0001b\u0001\u0001B\u0003\"\u0005\t\u0007!EA\u0001B#\t\u0019c\u0005\u0005\u0002\u0010I%\u0011QE\u0003\u0002\b\u001d>$\b.\u001b8h!\tyq%\u0003\u0002)\u0015\t\u0019\u0011I\\=\t\u000b)\u0012\u0001\u0019A\u0016\u0002\u0015UtG-\u001a:ms&tw\rE\u0002-iyq!!\f\u001a\u000f\u00059\nT\"A\u0018\u000b\u0005Ab\u0011A\u0002\u001fs_>$h(C\u0001\f\u0013\t\u0019$\"A\u0004qC\u000e\\\u0017mZ3\n\u0005U2$\u0001D%uKJ\f'\r\\3P]\u000e,'BA\u001a\u000b\u0001")
public interface Priority3AccumulatorConverters {
    public static /* synthetic */ IterableOnce collectionCanAccumulate$(Priority3AccumulatorConverters $this, IterableOnce underlying) {
        return $this.collectionCanAccumulate(underlying);
    }

    default public <A> IterableOnce<A> collectionCanAccumulate(IterableOnce<A> underlying) {
        return underlying;
    }

    public static void $init$(Priority3AccumulatorConverters $this) {
    }
}

