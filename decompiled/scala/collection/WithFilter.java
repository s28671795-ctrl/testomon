/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.Function1;
import scala.collection.IterableOnce;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\r4QAB\u0004\u0002\u00021AQA\b\u0001\u0005\u0002}AQa\r\u0001\u0007\u0002QBQa\u0010\u0001\u0007\u0002\u0001CQA\u0013\u0001\u0007\u0002-CQ!\u0016\u0001\u0007\u0002Y\u0013!bV5uQ\u001aKG\u000e^3s\u0015\tA\u0011\"\u0001\u0006d_2dWm\u0019;j_:T\u0011AC\u0001\u0006g\u000e\fG.Y\u0002\u0001+\riAEL\n\u0004\u00019\u0011\u0002CA\b\u0011\u001b\u0005I\u0011BA\t\n\u0005\u0019\te.\u001f*fMB\u00111c\u0007\b\u0003)eq!!\u0006\r\u000e\u0003YQ!aF\u0006\u0002\rq\u0012xn\u001c;?\u0013\u0005Q\u0011B\u0001\u000e\n\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001H\u000f\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005iI\u0011A\u0002\u001fj]&$h\bF\u0001!!\u0011\t\u0003AI\u0017\u000e\u0003\u001d\u0001\"a\t\u0013\r\u0001\u00111Q\u0005\u0001CC\u0002\u0019\u0012\u0011!Q\t\u0003O)\u0002\"a\u0004\u0015\n\u0005%J!a\u0002(pi\"Lgn\u001a\t\u0003\u001f-J!\u0001L\u0005\u0003\u0007\u0005s\u0017\u0010\u0005\u0002$]\u00111q\u0006\u0001CC\u0002A\u0012!aQ\"\u0016\u0005\u0019\nD!\u0002\u001a/\u0005\u00041#\u0001B0%IE\n1!\\1q+\t)\u0004\b\u0006\u00027uA\u00191EL\u001c\u0011\u0005\rBD!B\u001d\u0003\u0005\u00041#!\u0001\"\t\u000bm\u0012\u0001\u0019\u0001\u001f\u0002\u0003\u0019\u0004BaD\u001f#o%\u0011a(\u0003\u0002\n\rVt7\r^5p]F\nqA\u001a7bi6\u000b\u0007/\u0006\u0002B\tR\u0011!)\u0012\t\u0004G9\u001a\u0005CA\u0012E\t\u0015I4A1\u0001'\u0011\u0015Y4\u00011\u0001G!\u0011yQHI$\u0011\u0007\u0005B5)\u0003\u0002J\u000f\ta\u0011\n^3sC\ndWm\u00148dK\u00069am\u001c:fC\u000eDWC\u0001'T)\ti\u0005\u000b\u0005\u0002\u0010\u001d&\u0011q*\u0003\u0002\u0005+:LG\u000fC\u0003<\t\u0001\u0007\u0011\u000b\u0005\u0003\u0010{\t\u0012\u0006CA\u0012T\t\u0015!FA1\u0001'\u0005\u0005)\u0016AC<ji\"4\u0015\u000e\u001c;feR\u0011\u0001e\u0016\u0005\u00061\u0016\u0001\r!W\u0001\u0002cB!q\"\u0010\u0012[!\ty1,\u0003\u0002]\u0013\t9!i\\8mK\u0006t\u0007\u0006\u0002\u0001_C\n\u0004\"aD0\n\u0005\u0001L!\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\u0019\u0001")
public abstract class WithFilter<A, CC>
implements Serializable {
    private static final long serialVersionUID = 3L;

    public abstract <B> CC map(Function1<A, B> var1);

    public abstract <B> CC flatMap(Function1<A, IterableOnce<B>> var1);

    public abstract <U> void foreach(Function1<A, U> var1);

    public abstract WithFilter<A, CC> withFilter(Function1<A, Object> var1);
}

