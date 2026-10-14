/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.BufferOverflowException$;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005]b\u0001B\u000b\u0017\u0005nA\u0001\"\r\u0001\u0003\u0016\u0004%\tA\r\u0005\tw\u0001\u0011\t\u0012)A\u0005g!)A\b\u0001C\u0001{!9\u0011\tAA\u0001\n\u0003\u0011\u0005b\u0002#\u0001#\u0003%\t!\u0012\u0005\b!\u0002\t\t\u0011\"\u0011R\u0011\u001dI\u0006!!A\u0005\u0002iCqA\u0018\u0001\u0002\u0002\u0013\u0005q\fC\u0004f\u0001\u0005\u0005I\u0011\t4\t\u000f5\u0004\u0011\u0011!C\u0001]\"91\u000fAA\u0001\n\u0003\"\bb\u0002<\u0001\u0003\u0003%\te\u001e\u0005\bq\u0002\t\t\u0011\"\u0011z\u000f\u001dYh#!A\t\u0002q4q!\u0006\f\u0002\u0002#\u0005Q\u0010\u0003\u0004=\u001f\u0011\u0005\u00111\u0003\u0005\n\u0003+y\u0011\u0011!C#\u0003/A\u0011\"!\u0007\u0010\u0003\u0003%\t)a\u0007\t\u0013\u0005}q\"!A\u0005\u0002\u0006\u0005\u0002\"CA\u0017\u001f\u0005\u0005I\u0011BA\u0018\u0005]\u0011UO\u001a4fe>3XM\u001d4m_^,\u0005pY3qi&|gN\u0003\u0002\u00181\u000511\u000f\u001e:fC6T\u0011!G\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\t\u0001a\"F\f\t\u0003;\u001dr!A\b\u0013\u000f\u0005}\u0011S\"\u0001\u0011\u000b\u0005\u0005R\u0012A\u0002\u001fs_>$h(C\u0001$\u0003\u0015\u00198-\u00197b\u0013\t)c%A\u0004qC\u000e\\\u0017mZ3\u000b\u0003\rJ!\u0001K\u0015\u0003!I+h\u000e^5nK\u0016C8-\u001a9uS>t'BA\u0013'!\tYC&D\u0001'\u0013\ticEA\u0004Qe>$Wo\u0019;\u0011\u0005uy\u0013B\u0001\u0019*\u00051\u0019VM]5bY&T\u0018M\u00197f\u0003\ri7oZ\u000b\u0002gA\u0011A\u0007\u000f\b\u0003kY\u0002\"a\b\u0014\n\u0005]2\u0013A\u0002)sK\u0012,g-\u0003\u0002:u\t11\u000b\u001e:j]\u001eT!a\u000e\u0014\u0002\t5\u001cx\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005y\u0002\u0005CA \u0001\u001b\u00051\u0002\"B\u0019\u0004\u0001\u0004\u0019\u0014\u0001B2paf$\"AP\"\t\u000fE\"\u0001\u0013!a\u0001g\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#\u0001$+\u0005M:5&\u0001%\u0011\u0005%sU\"\u0001&\u000b\u0005-c\u0015!C;oG\",7m[3e\u0015\tie%\u0001\u0006b]:|G/\u0019;j_:L!a\u0014&\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002%B\u00111\u000bW\u0007\u0002)*\u0011QKV\u0001\u0005Y\u0006twMC\u0001X\u0003\u0011Q\u0017M^1\n\u0005e\"\u0016\u0001\u00049s_\u0012,8\r^!sSRLX#A.\u0011\u0005-b\u0016BA/'\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\t\u00017\r\u0005\u0002,C&\u0011!M\n\u0002\u0004\u0003:L\bb\u00023\t\u0003\u0003\u0005\raW\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0003\u001d\u00042\u0001[6a\u001b\u0005I'B\u00016'\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003Y&\u0014\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR\u0011qN\u001d\t\u0003WAL!!\u001d\u0014\u0003\u000f\t{w\u000e\\3b]\"9AMCA\u0001\u0002\u0004\u0001\u0017A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$\"AU;\t\u000f\u0011\\\u0011\u0011!a\u00017\u0006A\u0001.Y:i\u0007>$W\rF\u0001\\\u0003\u0019)\u0017/^1mgR\u0011qN\u001f\u0005\bI6\t\t\u00111\u0001a\u0003]\u0011UO\u001a4fe>3XM\u001d4m_^,\u0005pY3qi&|g\u000e\u0005\u0002@\u001fM!qB`A\u0005!\u0015y\u0018QA\u001a?\u001b\t\t\tAC\u0002\u0002\u0004\u0019\nqA];oi&lW-\u0003\u0003\u0002\b\u0005\u0005!!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8ocA!\u00111BA\t\u001b\t\tiAC\u0002\u0002\u0010Y\u000b!![8\n\u0007A\ni\u0001F\u0001}\u0003!!xn\u0015;sS:<G#\u0001*\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0007y\ni\u0002C\u00032%\u0001\u00071'A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0005\r\u0012\u0011\u0006\t\u0005W\u0005\u00152'C\u0002\u0002(\u0019\u0012aa\u00149uS>t\u0007\u0002CA\u0016'\u0005\u0005\t\u0019\u0001 \u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u00022A\u00191+a\r\n\u0007\u0005UBK\u0001\u0004PE*,7\r\u001e")
public final class BufferOverflowException
extends RuntimeException
implements Product {
    private final String msg;

    public static Option<String> unapply(BufferOverflowException x$0) {
        return BufferOverflowException$.MODULE$.unapply(x$0);
    }

    public static BufferOverflowException apply(String msg) {
        return BufferOverflowException$.MODULE$.apply(msg);
    }

    public static <A> Function1<String, A> andThen(Function1<BufferOverflowException, A> g2) {
        return BufferOverflowException$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, BufferOverflowException> compose(Function1<A, String> g2) {
        return BufferOverflowException$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String msg() {
        return this.msg;
    }

    public BufferOverflowException copy(String msg) {
        return new BufferOverflowException(msg);
    }

    public String copy$default$1() {
        return this.msg();
    }

    @Override
    public String productPrefix() {
        return "BufferOverflowException";
    }

    @Override
    public int productArity() {
        return 1;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.msg();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof BufferOverflowException;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "msg";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof BufferOverflowException)) return false;
        boolean bl = true;
        if (!bl) return false;
        BufferOverflowException bufferOverflowException = (BufferOverflowException)x$1;
        String string = this.msg();
        String string2 = bufferOverflowException.msg();
        if (string != null) {
            if (!string.equals(string2)) return false;
            return true;
        }
        if (string2 == null) return true;
        return false;
    }

    public BufferOverflowException(String msg) {
        this.msg = msg;
        super(msg);
        Product.$init$(this);
    }
}

