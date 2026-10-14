/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntSupplier;
import scala.Function0;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000592A\u0001B\u0003\u0001\u001d!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003(\u0001\u0011\u0005\u0001\u0006C\u0003-\u0001\u0011\u0005QFA\tBg*\u000bg/Y%oiN+\b\u000f\u001d7jKJT!AB\u0004\u0002-\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYNT!\u0001C\u0005\u0002\u000b)\fg/\u0019\u001d\u000b\u0005)Y\u0011AB2p[B\fGOC\u0001\r\u0003\u0015\u00198-\u00197b\u0007\u0001\u00192\u0001A\b\u0018!\t\u0001R#D\u0001\u0012\u0015\t\u00112#\u0001\u0003mC:<'\"\u0001\u000b\u0002\t)\fg/Y\u0005\u0003-E\u0011aa\u00142kK\u000e$\bC\u0001\r\u001e\u001b\u0005I\"B\u0001\u000e\u001c\u0003!1WO\\2uS>t'B\u0001\u000f\u0014\u0003\u0011)H/\u001b7\n\u0005yI\"aC%oiN+\b\u000f\u001d7jKJ\f!a\u001d4\u0011\u0007\u0005\u0012C%D\u0001\f\u0013\t\u00193BA\u0005Gk:\u001cG/[8oaA\u0011\u0011%J\u0005\u0003M-\u00111!\u00138u\u0003\u0019a\u0014N\\5u}Q\u0011\u0011f\u000b\t\u0003U\u0001i\u0011!\u0002\u0005\u0006?\t\u0001\r\u0001I\u0001\tO\u0016$\u0018i]%oiR\tA\u0005")
public class AsJavaIntSupplier
implements IntSupplier {
    private final Function0<Object> sf;

    @Override
    public int getAsInt() {
        return this.sf.apply$mcI$sp();
    }

    public AsJavaIntSupplier(Function0<Object> sf) {
        this.sf = sf;
    }
}

