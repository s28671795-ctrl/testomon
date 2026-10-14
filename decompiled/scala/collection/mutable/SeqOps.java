/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Function1;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Cloneable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015aaB\u0003\u0007!\u0003\r\t!\u0004\u0005\u0006_\u0001!\t\u0001\r\u0005\u0006i\u0001!\t%\u000e\u0005\u0006m\u00011\ta\u000e\u0005\u0006Y\u0002!)!\u001c\u0002\u0007'\u0016\fx\n]:\u000b\u0005\u001dA\u0011aB7vi\u0006\u0014G.\u001a\u0006\u0003\u0013)\t!bY8mY\u0016\u001cG/[8o\u0015\u0005Y\u0011!B:dC2\f7\u0001A\u000b\u0005\u001da\u0011\u0003f\u0005\u0003\u0001\u001fMY\u0003C\u0001\t\u0012\u001b\u0005Q\u0011B\u0001\n\u000b\u0005\u0019\te.\u001f*fMB)A#\u0006\f\"O5\t\u0001\"\u0003\u0002\u0006\u0011A\u0011q\u0003\u0007\u0007\u0001\t\u0015I\u0002A1\u0001\u001b\u0005\u0005\t\u0015CA\u000e\u001f!\t\u0001B$\u0003\u0002\u001e\u0015\t9aj\u001c;iS:<\u0007C\u0001\t \u0013\t\u0001#BA\u0002B]f\u0004\"a\u0006\u0012\u0005\r\r\u0002AQ1\u0001%\u0005\t\u00195)\u0006\u0002\u001bK\u0011)aE\tb\u00015\t!q\f\n\u00132!\t9\u0002\u0006\u0002\u0004*\u0001\u0011\u0015\rA\u000b\u0002\u0002\u0007F\u00111d\u0004\t\u0004Y5:S\"\u0001\u0004\n\u000592!!C\"m_:,\u0017M\u00197f\u0003\u0019!\u0013N\\5uIQ\t\u0011\u0007\u0005\u0002\u0011e%\u00111G\u0003\u0002\u0005+:LG/A\u0003dY>tW\rF\u0001(\u0003\u0019)\b\u000fZ1uKR\u0019\u0011\u0007O\u001f\t\u000be\u001a\u0001\u0019\u0001\u001e\u0002\u0007%$\u0007\u0010\u0005\u0002\u0011w%\u0011AH\u0003\u0002\u0004\u0013:$\b\"\u0002 \u0004\u0001\u00041\u0012\u0001B3mK6D3a\u0001!P!\r\u0001\u0012iQ\u0005\u0003\u0005*\u0011a\u0001\u001e5s_^\u001c\bC\u0001#M\u001d\t)%J\u0004\u0002G\u00136\tqI\u0003\u0002I\u0019\u00051AH]8pizJ\u0011aC\u0005\u0003\u0017*\tq\u0001]1dW\u0006<W-\u0003\u0002N\u001d\nI\u0012J\u001c3fq>+Ho\u00144C_VtGm]#yG\u0016\u0004H/[8o\u0015\tY%\"\r\u0003\u001f!b[\u0007CA)V\u001d\t\u00116\u000b\u0005\u0002G\u0015%\u0011AKC\u0001\u0007!J,G-\u001a4\n\u0005Y;&AB*ue&twM\u0003\u0002U\u0015E*1%W/g=V\u0011!lW\u000b\u0002!\u0012)A\f\u0004b\u0001C\n\tA+\u0003\u0002_?\u0006YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIER!\u0001\u0019\u0006\u0002\rQD'o\\<t#\tY\"\r\u0005\u0002dI:\u0011\u0001CS\u0005\u0003K:\u0013\u0011\u0002\u00165s_^\f'\r\\32\u000b\r:\u0007.\u001b1\u000f\u0005AA\u0017B\u00011\u000bc\u0011\u0011\u0003C\u00036\u0003\u000bM\u001c\u0017\r\\12\u0005\u0019\u001a\u0015!\u0003;sC:\u001chm\u001c:n)\tqw.D\u0001\u0001\u0011\u0015\u0001H\u00011\u0001r\u0003\u00051\u0007\u0003\u0002\ts-YI!a\u001d\u0006\u0003\u0013\u0019+hn\u0019;j_:\f\u0004F\u0001\u0003v!\t\u0001b/\u0003\u0002x\u0015\t1\u0011N\u001c7j]\u0016Ds\u0001B=}{~\f\t\u0001\u0005\u0002\u0011u&\u00111P\u0003\u0002\u000bI\u0016\u0004(/Z2bi\u0016$\u0017aB7fgN\fw-Z\u0011\u0002}\u0006YSk]3!A6\f\u0007/\u00138QY\u0006\u001cW\r\u0019\u0011p]\u0002\ng\u000e\t1J]\u0012,\u00070\u001a3TKF\u0004\u0007%\u001b8ti\u0016\fG-A\u0003tS:\u001cW-\t\u0002\u0002\u0004\u00051!GL\u00194]A\u0002")
public interface SeqOps<A, CC, C>
extends scala.collection.SeqOps<A, CC, C>,
Cloneable<C> {
    public static /* synthetic */ Object clone$(SeqOps $this) {
        return $this.clone();
    }

    @Override
    default public C clone() {
        Builder b = this.newSpecificBuilder();
        if (b == null) {
            throw null;
        }
        b.addAll(this);
        return b.result();
    }

    public void update(int var1, A var2) throws IndexOutOfBoundsException;

    public static /* synthetic */ SeqOps transform$(SeqOps $this, Function1 f) {
        return $this.transform(f);
    }

    default public SeqOps<A, CC, C> transform(Function1<A, A> f) {
        int siz = this.length();
        for (int i = 0; i < siz; ++i) {
            this.update(i, f.apply(this.apply(i)));
        }
        return this;
    }

    public static void $init$(SeqOps $this) {
    }
}

