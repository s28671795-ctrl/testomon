/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent;

import scala.Function0;
import scala.Function1;
import scala.concurrent.BlockContext$;
import scala.concurrent.BlockContext$DefaultBlockContext$;
import scala.concurrent.CanAwait;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E4qa\u0004\t\u0011\u0002G\u0005Q\u0003C\u0003\u001b\u0001\u0019\u00051dB\u00035!!\u0005QGB\u0003\u0010!!\u0005a\u0007C\u00038\u0007\u0011\u0005\u0001h\u0002\u0004:\u0007\u0001FIA\u000f\u0004\u0007y\r\u0001\u000b\u0012B\u001f\t\u000b]2A\u0011A \t\u000bi1AQ\t!\t\u000b!\u001bAQA%\t\r)\u001b\u0001\u0015!\u0004L\u0011\u0019\u00196\u0001)C\u0007)\")qk\u0001C\u0003\u0013\")\u0001l\u0001C\u00033\")1m\u0001C\u0003I\na!\t\\8dW\u000e{g\u000e^3yi*\u0011\u0011CE\u0001\u000bG>t7-\u001e:sK:$(\"A\n\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001M\u0011\u0001A\u0006\t\u0003/ai\u0011AE\u0005\u00033I\u0011a!\u00118z%\u00164\u0017a\u00022m_\u000e\\wJ\\\u000b\u00039\u0001\"\"!H\u0018\u0015\u0005yI\u0003CA\u0010!\u0019\u0001!Q!I\u0001C\u0002\t\u0012\u0011\u0001V\t\u0003G\u0019\u0002\"a\u0006\u0013\n\u0005\u0015\u0012\"a\u0002(pi\"Lgn\u001a\t\u0003/\u001dJ!\u0001\u000b\n\u0003\u0007\u0005s\u0017\u0010C\u0003+\u0003\u0001\u000f1&\u0001\u0006qKJl\u0017n]:j_:\u0004\"\u0001L\u0017\u000e\u0003AI!A\f\t\u0003\u0011\r\u000bg.Q<bSRDa\u0001M\u0001\u0005\u0002\u0004\t\u0014!\u0002;ik:\\\u0007cA\f3=%\u00111G\u0005\u0002\ty\tLh.Y7f}\u0005a!\t\\8dW\u000e{g\u000e^3yiB\u0011AfA\n\u0003\u0007Y\ta\u0001P5oSRtD#A\u001b\u0002'\u0011+g-Y;mi\ncwnY6D_:$X\r\u001f;\u0011\u0005m2Q\"A\u0002\u0003'\u0011+g-Y;mi\ncwnY6D_:$X\r\u001f;\u0014\u0007\u00191b\b\u0005\u0002-\u0001Q\t!(\u0006\u0002B\tR\u0011!I\u0012\u000b\u0003\u0007\u0016\u0003\"a\b#\u0005\u000b\u0005B!\u0019\u0001\u0012\t\u000b)B\u00019A\u0016\t\rABA\u00111\u0001H!\r9\"gQ\u0001\u0014I\u00164\u0017-\u001e7u\u00052|7m[\"p]R,\u0007\u0010^\u000b\u0002}\u0005a1m\u001c8uKb$Hj\\2bYB\u0019A*\u0015 \u000e\u00035S!AT(\u0002\t1\fgn\u001a\u0006\u0002!\u0006!!.\u0019<b\u0013\t\u0011VJA\u0006UQJ,\u0017\r\u001a'pG\u0006d\u0017A\u00029sK\u001a,'\u000f\u0006\u0002?+\")ak\u0003a\u0001}\u0005I1-\u00198eS\u0012\fG/Z\u0001\bGV\u0014(/\u001a8u\u0003A9\u0018\u000e\u001e5CY>\u001c7nQ8oi\u0016DH/\u0006\u0002[;R\u00111,\u0019\u000b\u00039z\u0003\"aH/\u0005\u000b\u0005j!\u0019\u0001\u0012\t\r}kA\u00111\u0001a\u0003\u0011\u0011w\u000eZ=\u0011\u0007]\u0011D\fC\u0003c\u001b\u0001\u0007a(\u0001\u0007cY>\u001c7nQ8oi\u0016DH/A\tvg&twM\u00117pG.\u001cuN\u001c;fqR,2!Z8i)\t1g\u000e\u0006\u0002hSB\u0011q\u0004\u001b\u0003\u0006C9\u0011\rA\t\u0005\u0006U:\u0001\ra[\u0001\u0002MB!q\u0003\u001c h\u0013\ti'CA\u0005Gk:\u001cG/[8oc!)!M\u0004a\u0001}\u0011)\u0001O\u0004b\u0001E\t\t\u0011\n")
public interface BlockContext {
    public static <I, T> T usingBlockContext(BlockContext blockContext, Function1<BlockContext, T> f) {
        return BlockContext$.MODULE$.usingBlockContext(blockContext, f);
    }

    public static <T> T withBlockContext(BlockContext blockContext, Function0<T> body2) {
        return BlockContext$.MODULE$.withBlockContext(blockContext, body2);
    }

    public static BlockContext current() {
        return BlockContext$.MODULE$.current();
    }

    public static BlockContext defaultBlockContext() {
        return BlockContext$DefaultBlockContext$.MODULE$;
    }

    public <T> T blockOn(Function0<T> var1, CanAwait var2);
}

