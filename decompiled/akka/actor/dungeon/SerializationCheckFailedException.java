/*
 * Decompiled with CFR 0.152.
 */
package akka.actor.dungeon;

import akka.AkkaException;
import akka.actor.dungeon.SerializationCheckFailedException$;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005}c\u0001\u0002\r\u001a\u0005\u0002B\u0001b\u000e\u0001\u0003\u0016\u0004%\t\u0001\u000f\u0005\t\u0003\u0002\u0011\t\u0012)A\u0005s!A!\t\u0001BK\u0002\u0013\u00051\t\u0003\u0005H\u0001\tE\t\u0015!\u0003E\u0011\u0019A\u0005\u0001\"\u0001\u001a\u0013\"9a\nAA\u0001\n\u0003y\u0005b\u0002*\u0001#\u0003%\ta\u0015\u0005\b=\u0002\t\n\u0011\"\u0001`\u0011\u001d\t\u0007!!A\u0005B\tDqA\u001a\u0001\u0002\u0002\u0013\u0005q\rC\u0004l\u0001\u0005\u0005I\u0011\u00017\t\u000fI\u0004\u0011\u0011!C!g\"9!\u0010AA\u0001\n\u0003Y\b\"CA\u0001\u0001\u0005\u0005I\u0011IA\u0002\u0011%\t9\u0001AA\u0001\n\u0003\nI\u0001C\u0005\u0002\f\u0001\t\t\u0011\"\u0011\u0002\u000e\u001dI\u0011QD\r\u0002\u0002#\u0005\u0011q\u0004\u0004\t1e\t\t\u0011#\u0001\u0002\"!1\u0001J\u0005C\u0001\u0003sA\u0011\"a\u000f\u0013\u0003\u0003%)%!\u0010\t\u0013\u0005}\"#!A\u0005\u0002\u0006\u0005\u0003\"CA$%\u0005\u0005I\u0011QA%\u0011%\tYFEA\u0001\n\u0013\tiFA\u0011TKJL\u0017\r\\5{CRLwN\\\"iK\u000e\\g)Y5mK\u0012,\u0005pY3qi&|gN\u0003\u0002\u001b7\u00059A-\u001e8hK>t'B\u0001\u000f\u001e\u0003\u0015\t7\r^8s\u0015\u0005q\u0012\u0001B1lW\u0006\u001c\u0001a\u0005\u0003\u0001C\u0015Z\u0003C\u0001\u0012$\u001b\u0005i\u0012B\u0001\u0013\u001e\u00055\t5n[1Fq\u000e,\u0007\u000f^5p]B\u0011a%K\u0007\u0002O)\t\u0001&A\u0003tG\u0006d\u0017-\u0003\u0002+O\t9\u0001K]8ek\u000e$\bC\u0001\u00175\u001d\ti#G\u0004\u0002/c5\tqF\u0003\u00021?\u00051AH]8pizJ\u0011\u0001K\u0005\u0003g\u001d\nq\u0001]1dW\u0006<W-\u0003\u00026m\ta1+\u001a:jC2L'0\u00192mK*\u00111gJ\u0001\u0004[N<W#A\u001d\u0011\u0005izT\"A\u001e\u000b\u0005qj\u0014\u0001\u00027b]\u001eT\u0011AP\u0001\u0005U\u00064\u0018-\u0003\u0002Aw\t1qJ\u00196fGR\fA!\\:hA\u0005)1-Y;tKV\tA\t\u0005\u0002-\u000b&\u0011aI\u000e\u0002\n)\"\u0014xn^1cY\u0016\faaY1vg\u0016\u0004\u0013A\u0002\u001fj]&$h\bF\u0002K\u00196\u0003\"a\u0013\u0001\u000e\u0003eAQaN\u0003A\u0002eBQAQ\u0003A\u0002\u0011\u000bAaY8qsR\u0019!\nU)\t\u000f]2\u0001\u0013!a\u0001s!9!I\u0002I\u0001\u0002\u0004!\u0015AD2paf$C-\u001a4bk2$H%M\u000b\u0002)*\u0012\u0011(V\u0016\u0002-B\u0011q\u000bX\u0007\u00021*\u0011\u0011LW\u0001\nk:\u001c\u0007.Z2lK\u0012T!aW\u0014\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002^1\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\t\u0001M\u000b\u0002E+\u0006i\u0001O]8ek\u000e$\bK]3gSb,\u0012a\u0019\t\u0003u\u0011L!!Z\u001e\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005A\u0007C\u0001\u0014j\u0013\tQwEA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002naB\u0011aE\\\u0005\u0003_\u001e\u00121!\u00118z\u0011\u001d\t8\"!AA\u0002!\f1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#\u0001;\u0011\u0007UDX.D\u0001w\u0015\t9x%\u0001\u0006d_2dWm\u0019;j_:L!!\u001f<\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0003y~\u0004\"AJ?\n\u0005y<#a\u0002\"p_2,\u0017M\u001c\u0005\bc6\t\t\u00111\u0001n\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u0007\r\f)\u0001C\u0004r\u001d\u0005\u0005\t\u0019\u00015\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012\u0001[\u0001\u0007KF,\u0018\r\\:\u0015\u0007q\fy\u0001C\u0004r!\u0005\u0005\t\u0019A7)\u000f\u0001\t\u0019\"!\u0007\u0002\u001cA\u0019a%!\u0006\n\u0007\u0005]qE\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t\u0011!A\u0011TKJL\u0017\r\\5{CRLwN\\\"iK\u000e\\g)Y5mK\u0012,\u0005pY3qi&|g\u000e\u0005\u0002L%M)!#a\t\u00020A9\u0011QEA\u0016s\u0011SUBAA\u0014\u0015\r\tIcJ\u0001\beVtG/[7f\u0013\u0011\ti#a\n\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t'\u0007\u0005\u0003\u00022\u0005]RBAA\u001a\u0015\r\t)$P\u0001\u0003S>L1!NA\u001a)\t\ty\"\u0001\u0005u_N#(/\u001b8h)\u0005\u0019\u0017!B1qa2LH#\u0002&\u0002D\u0005\u0015\u0003\"B\u001c\u0016\u0001\u0004I\u0004\"\u0002\"\u0016\u0001\u0004!\u0015aB;oCB\u0004H.\u001f\u000b\u0005\u0003\u0017\n9\u0006E\u0003'\u0003\u001b\n\t&C\u0002\u0002P\u001d\u0012aa\u00149uS>t\u0007#\u0002\u0014\u0002Te\"\u0015bAA+O\t1A+\u001e9mKJB\u0001\"!\u0017\u0017\u0003\u0003\u0005\rAS\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,G#A\u001d")
public final class SerializationCheckFailedException
extends AkkaException
implements Product {
    private static final long serialVersionUID = 1L;
    private final Object msg;
    private final Throwable cause;

    public static Option<Tuple2<Object, Throwable>> unapply(SerializationCheckFailedException x$0) {
        return SerializationCheckFailedException$.MODULE$.unapply(x$0);
    }

    public static SerializationCheckFailedException apply(Object msg, Throwable cause) {
        return SerializationCheckFailedException$.MODULE$.apply(msg, cause);
    }

    public static Function1<Tuple2<Object, Throwable>, SerializationCheckFailedException> tupled() {
        return SerializationCheckFailedException$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Throwable, SerializationCheckFailedException>> curried() {
        return SerializationCheckFailedException$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Object msg() {
        return this.msg;
    }

    public Throwable cause() {
        return this.cause;
    }

    public SerializationCheckFailedException copy(Object msg, Throwable cause) {
        return new SerializationCheckFailedException(msg, cause);
    }

    public Object copy$default$1() {
        return this.msg();
    }

    public Throwable copy$default$2() {
        return this.cause();
    }

    @Override
    public String productPrefix() {
        return "SerializationCheckFailedException";
    }

    @Override
    public int productArity() {
        return 2;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.msg();
            }
            case 1: {
                return this.cause();
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
        return x$1 instanceof SerializationCheckFailedException;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "msg";
            }
            case 1: {
                return "cause";
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
        if (!(object instanceof SerializationCheckFailedException)) return false;
        boolean bl = true;
        if (!bl) return false;
        SerializationCheckFailedException serializationCheckFailedException = (SerializationCheckFailedException)x$1;
        if (!BoxesRunTime.equals(this.msg(), serializationCheckFailedException.msg())) return false;
        Throwable throwable = this.cause();
        Throwable throwable2 = serializationCheckFailedException.cause();
        if (throwable != null) {
            if (!throwable.equals(throwable2)) return false;
            return true;
        }
        if (throwable2 == null) return true;
        return false;
    }

    public SerializationCheckFailedException(Object msg, Throwable cause) {
        this.msg = msg;
        this.cause = cause;
        super(new StringBuilder(255).append("Failed to serialize and deserialize message of type ").append(msg.getClass().getName()).append(" for testing. ").append("To avoid this error, either disable 'akka.actor.serialize-messages', mark the message with 'akka.actor.NoSerializationVerificationNeeded', or configure serialization to support this message").toString(), cause);
        Product.$init$(this);
    }
}

