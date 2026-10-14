/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntConsumer;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005M2A\u0001B\u0003\u0001\u001d!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003+\u0001\u0011\u00051\u0006C\u00030\u0001\u0011\u0005\u0001GA\tBg*\u000bg/Y%oi\u000e{gn];nKJT!AB\u0004\u0002-\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYNT!\u0001C\u0005\u0002\u000b)\fg/\u0019\u001d\u000b\u0005)Y\u0011AB2p[B\fGOC\u0001\r\u0003\u0015\u00198-\u00197b\u0007\u0001\u00192\u0001A\b\u0018!\t\u0001R#D\u0001\u0012\u0015\t\u00112#\u0001\u0003mC:<'\"\u0001\u000b\u0002\t)\fg/Y\u0005\u0003-E\u0011aa\u00142kK\u000e$\bC\u0001\r\u001e\u001b\u0005I\"B\u0001\u000e\u001c\u0003!1WO\\2uS>t'B\u0001\u000f\u0014\u0003\u0011)H/\u001b7\n\u0005yI\"aC%oi\u000e{gn];nKJ\f!a\u001d4\u0011\t\u0005\u0012CeJ\u0007\u0002\u0017%\u00111e\u0003\u0002\n\rVt7\r^5p]F\u0002\"!I\u0013\n\u0005\u0019Z!aA%oiB\u0011\u0011\u0005K\u0005\u0003S-\u0011A!\u00168ji\u00061A(\u001b8jiz\"\"\u0001\f\u0018\u0011\u00055\u0002Q\"A\u0003\t\u000b}\u0011\u0001\u0019\u0001\u0011\u0002\r\u0005\u001c7-\u001a9u)\t9\u0013\u0007C\u00033\u0007\u0001\u0007A%\u0001\u0002yc\u0001")
public class AsJavaIntConsumer
implements IntConsumer {
    private final Function1<Object, BoxedUnit> sf;

    @Override
    public IntConsumer andThen(IntConsumer x$1) {
        return IntConsumer.super.andThen(x$1);
    }

    @Override
    public void accept(int x1) {
        this.sf.apply$mcVI$sp(x1);
    }

    public AsJavaIntConsumer(Function1<Object, BoxedUnit> sf) {
        this.sf = sf;
    }
}

