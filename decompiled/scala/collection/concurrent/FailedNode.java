/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.concurrent;

import scala.collection.StringOps$;
import scala.collection.concurrent.MainNode;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u00193Qa\u0002\u0005\u0003\u00119A\u0001\u0002\n\u0001\u0003\u0002\u0003\u0006I\u0001\u0005\u0005\u0006K\u0001!\tA\n\u0005\u0006S\u0001!\tA\u000b\u0005\u0006a\u0001!\t!\r\u0005\u0006o\u0001!\t\u0001\u000f\u0005\u0006s\u0001!\tE\u000f\u0002\u000b\r\u0006LG.\u001a3O_\u0012,'BA\u0005\u000b\u0003)\u0019wN\\2veJ,g\u000e\u001e\u0006\u0003\u00171\t!bY8mY\u0016\u001cG/[8o\u0015\u0005i\u0011!B:dC2\fWcA\b\u0017EM\u0011\u0001\u0001\u0005\t\u0005#I!\u0012%D\u0001\t\u0013\t\u0019\u0002B\u0001\u0005NC&tgj\u001c3f!\t)b\u0003\u0004\u0001\u0005\u000b]\u0001!\u0019A\r\u0003\u0003-\u001b\u0001!\u0005\u0002\u001b=A\u00111\u0004H\u0007\u0002\u0019%\u0011Q\u0004\u0004\u0002\b\u001d>$\b.\u001b8h!\tYr$\u0003\u0002!\u0019\t\u0019\u0011I\\=\u0011\u0005U\u0011C!B\u0012\u0001\u0005\u0004I\"!\u0001,\u0002\u0003A\fa\u0001P5oSRtDCA\u0014)!\u0011\t\u0002\u0001F\u0011\t\u000b\u0011\u0012\u0001\u0019\u0001\t\u0002\rM$(/\u001b8h)\tQ2\u0006C\u0003-\u0007\u0001\u0007Q&A\u0002mKZ\u0004\"a\u0007\u0018\n\u0005=b!aA%oi\u0006Q1-Y2iK\u0012\u001c\u0016N_3\u0015\u00055\u0012\u0004\"B\u001a\u0005\u0001\u0004!\u0014AA2u!\tYR'\u0003\u00027\u0019\t1\u0011I\\=SK\u001a\f\u0011b\u001b8po:\u001c\u0016N_3\u0015\u00035\n\u0001\u0002^8TiJLgn\u001a\u000b\u0002wA\u0011Ah\u0011\b\u0003{\u0005\u0003\"A\u0010\u0007\u000e\u0003}R!\u0001\u0011\r\u0002\rq\u0012xn\u001c;?\u0013\t\u0011E\"\u0001\u0004Qe\u0016$WMZ\u0005\u0003\t\u0016\u0013aa\u0015;sS:<'B\u0001\"\r\u0001")
public final class FailedNode<K, V>
extends MainNode<K, V> {
    private final MainNode<K, V> p;

    public Nothing$ string(int lev) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int cachedSize(Object ct) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int knownSize() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return StringOps$.MODULE$.format$extension("FailedNode(%s)", ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.p}));
    }

    public FailedNode(MainNode<K, V> p) {
        this.p = p;
        this.WRITE_PREV(p);
    }
}

