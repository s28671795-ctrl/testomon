/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.BindFailedException$;
import akka.stream.StreamTcpException;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00194A\u0001D\u0007\u0001%!)q\u0003\u0001C\u00011\u001d)!$\u0004EA7\u0019)A\"\u0004EA9!)qc\u0001C\u0001_!9\u0001gAA\u0001\n\u0003\n\u0004b\u0002\u001e\u0004\u0003\u0003%\ta\u000f\u0005\b\u007f\r\t\t\u0011\"\u0001A\u0011\u001d15!!A\u0005B\u001dCqAT\u0002\u0002\u0002\u0013\u0005q\nC\u0004U\u0007\u0005\u0005I\u0011I+\t\u000fY\u001b\u0011\u0011!C\u0005/\n\u0019\")\u001b8e\r\u0006LG.\u001a3Fq\u000e,\u0007\u000f^5p]*\u0011abD\u0001\u0007gR\u0014X-Y7\u000b\u0003A\tA!Y6lC\u000e\u00011C\u0001\u0001\u0014!\t!R#D\u0001\u000e\u0013\t1RB\u0001\nTiJ,\u0017-\u001c+da\u0016C8-\u001a9uS>t\u0017A\u0002\u001fj]&$h\bF\u0001\u001a!\t!\u0002!A\nCS:$g)Y5mK\u0012,\u0005pY3qi&|g\u000e\u0005\u0002\u0015\u0007M!1!G\u000f$!\tq\u0012%D\u0001 \u0015\u0005\u0001\u0013!B:dC2\f\u0017B\u0001\u0012 \u0005\u001d\u0001&o\u001c3vGR\u0004\"\u0001\n\u0017\u000f\u0005\u0015RcB\u0001\u0014*\u001b\u00059#B\u0001\u0015\u0012\u0003\u0019a$o\\8u}%\t\u0001%\u0003\u0002,?\u00059\u0001/Y2lC\u001e,\u0017BA\u0017/\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\tYs\u0004F\u0001\u001c\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\t!\u0007\u0005\u00024q5\tAG\u0003\u00026m\u0005!A.\u00198h\u0015\u00059\u0014\u0001\u00026bm\u0006L!!\u000f\u001b\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005a\u0004C\u0001\u0010>\u0013\tqtDA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002B\tB\u0011aDQ\u0005\u0003\u0007~\u00111!\u00118z\u0011\u001d)u!!AA\u0002q\n1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#\u0001%\u0011\u0007%c\u0015)D\u0001K\u0015\tYu$\u0001\u0006d_2dWm\u0019;j_:L!!\u0014&\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0003!N\u0003\"AH)\n\u0005I{\"a\u0002\"p_2,\u0017M\u001c\u0005\b\u000b&\t\t\u00111\u0001B\u0003!A\u0017m\u001d5D_\u0012,G#\u0001\u001f\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0003a\u0003\"aM-\n\u0005i#$AB(cU\u0016\u001cG\u000f\u000b\u0004\u00049~\u0003'm\u0019\t\u0003=uK!AX\u0010\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0004nKN\u001c\u0018mZ3\"\u0003\u0005\fAJQ5oI\u001a\u000b\u0017\u000e\\3e\u000bb\u001cW\r\u001d;j_:\u0004sN\u00196fGR\u0004s/\u001b7mA9,g/\u001a:!E\u0016\u0004C\u000f\u001b:po:t\u0003%T1uG\"\u0004sN\u001c\u0011uQ\u0016\u00043\r\\1tg\u0002Jgn\u001d;fC\u0012t\u0013!B:j]\u000e,\u0017%\u00013\u0002\rIrCGL\u0019:Q\u0019\u0011Al\u00181cG\u0002")
public class BindFailedException
extends StreamTcpException {
    public static boolean canEqual(Object x$1) {
        return BindFailedException$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return BindFailedException$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return BindFailedException$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return BindFailedException$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return BindFailedException$.MODULE$.productPrefix();
    }

    public static Iterator<String> productElementNames() {
        return BindFailedException$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return BindFailedException$.MODULE$.productElementName(n);
    }

    public BindFailedException() {
        super("bind failed");
    }
}

