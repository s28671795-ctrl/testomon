/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongPredicate;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichFunction1AsLongPredicate$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!Iq\u0005\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006Q\u0001!\t!\u000b\u0005\u0006]\u0001!\ta\f\u0005\b}\u0001\t\t\u0011\"\u0011@\u0011\u001d\u0019\u0005!!A\u0005B\u0011;qA\u0013\b\u0002\u0002#\u00051JB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001'\t\u000b!BA\u0011\u0001)\t\u000bECAQ\u0001*\t\u000fYC\u0011\u0011!C\u0003/\"9\u0011\fCA\u0001\n\u000bQ&\u0001\b*jG\"4UO\\2uS>t\u0017'Q:M_:<\u0007K]3eS\u000e\fG/\u001a\u0006\u0003\u001fA\taCZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0006\u0003#I\tQA[1wCbR!a\u0005\u000b\u0002\r\r|W\u000e]1u\u0015\u0005)\u0012!B:dC2\f7\u0001A\n\u0003\u0001a\u0001\"!\u0007\u000e\u000e\u0003QI!a\u0007\u000b\u0003\r\u0005s\u0017PV1m\u0003I\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195Gk:\u001cG/[8oc\u0005\u001bHj\u001c8h!J,G-[2bi\u0016$C%\u001e8eKJd\u00170\u001b8h+\u0005q\u0002\u0003B\r C\u0011J!\u0001\t\u000b\u0003\u0013\u0019+hn\u0019;j_:\f\u0004CA\r#\u0013\t\u0019CC\u0001\u0003M_:<\u0007CA\r&\u0013\t1CCA\u0004C_>dW-\u00198\u0002'N\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r\u001b$v]\u000e$\u0018n\u001c82\u0003NduN\\4Qe\u0016$\u0017nY1uK\u0012\"SO\u001c3fe2L\u0018N\\4!\u0003\u0019a\u0014N\\5u}Q\u0011!\u0006\f\t\u0003W\u0001i\u0011A\u0004\u0005\u0006[\r\u0001\rAH\u0001\u000bk:$WM\u001d7zS:<\u0017AB1t\u0015\u00064\u0018-F\u00011!\t\t\u0004(D\u00013\u0015\t\u0019D'\u0001\u0005gk:\u001cG/[8o\u0015\t)d'\u0001\u0003vi&d'\"A\u001c\u0002\t)\fg/Y\u0005\u0003sI\u0012Q\u0002T8oOB\u0013X\rZ5dCR,\u0007F\u0001\u0003<!\tIB(\u0003\u0002>)\t1\u0011N\u001c7j]\u0016\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002\u0001B\u0011\u0011$Q\u0005\u0003\u0005R\u00111!\u00138u\u0003\u0019)\u0017/^1mgR\u0011A%\u0012\u0005\b\r\u001a\t\t\u00111\u0001H\u0003\rAH%\r\t\u00033!K!!\u0013\u000b\u0003\u0007\u0005s\u00170\u0001\u000fSS\u000eDg)\u001e8di&|g.M!t\u0019>tw\r\u0015:fI&\u001c\u0017\r^3\u0011\u0005-B1C\u0001\u0005N!\tIb*\u0003\u0002P)\t1\u0011I\\=SK\u001a$\u0012aS\u0001\u0011CNT\u0015M^1%Kb$XM\\:j_:$\"\u0001M*\t\u000bQS\u0001\u0019\u0001\u0016\u0002\u000b\u0011\"\b.[:)\u0005)Y\u0014A\u00055bg\"\u001cu\u000eZ3%Kb$XM\\:j_:$\"a\u0010-\t\u000bQ[\u0001\u0019\u0001\u0016\u0002!\u0015\fX/\u00197tI\u0015DH/\u001a8tS>tGCA.^)\t!C\fC\u0004G\u0019\u0005\u0005\t\u0019A$\t\u000bQc\u0001\u0019\u0001\u0016")
public final class RichFunction1AsLongPredicate {
    private final Function1<Object, Object> scala$compat$java8$functionConverterImpls$RichFunction1AsLongPredicate$$underlying;

    public static boolean equals$extension(Function1 function1, Object object) {
        return RichFunction1AsLongPredicate$.MODULE$.equals$extension(function1, object);
    }

    public static int hashCode$extension(Function1 function1) {
        return RichFunction1AsLongPredicate$.MODULE$.hashCode$extension(function1);
    }

    public static LongPredicate asJava$extension(Function1 function1) {
        return RichFunction1AsLongPredicate$.MODULE$.asJava$extension(function1);
    }

    public Function1<Object, Object> scala$compat$java8$functionConverterImpls$RichFunction1AsLongPredicate$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction1AsLongPredicate$$underlying;
    }

    public LongPredicate asJava() {
        return RichFunction1AsLongPredicate$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsLongPredicate$$underlying());
    }

    public int hashCode() {
        return RichFunction1AsLongPredicate$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsLongPredicate$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction1AsLongPredicate$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsLongPredicate$$underlying(), x$1);
    }

    public RichFunction1AsLongPredicate(Function1<Object, Object> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction1AsLongPredicate$$underlying = underlying;
    }
}

