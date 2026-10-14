/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.PossiblyHarmful;
import akka.actor.ReceiveTimeout$;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-4QAD\b\u0002\u0002QAQa\b\u0001\u0005\u0002\u0001:QAI\b\t\u0002\u000e2QAD\b\t\u0002\u0012BQaH\u0002\u0005\u0002QBQ!N\u0002\u0005\u0002YBqaN\u0002\u0002\u0002\u0013\u0005\u0003\bC\u0004B\u0007\u0005\u0005I\u0011\u0001\"\t\u000f\u0019\u001b\u0011\u0011!C\u0001\u000f\"9QjAA\u0001\n\u0003r\u0005bB+\u0004\u0003\u0003%\tA\u0016\u0005\b7\u000e\t\t\u0011\"\u0011]\u0011\u001di6!!A\u0005ByCqaX\u0002\u0002\u0002\u0013%\u0001M\u0001\bSK\u000e,\u0017N^3US6,w.\u001e;\u000b\u0005A\t\u0012!B1di>\u0014(\"\u0001\n\u0002\t\u0005\\7.Y\u0002\u0001'\r\u0001Qc\u0007\t\u0003-ei\u0011a\u0006\u0006\u00021\u0005)1oY1mC&\u0011!d\u0006\u0002\u0007\u0003:L(+\u001a4\u0011\u0005qiR\"A\b\n\u0005yy!a\u0004)pgNL'\r\\=ICJlg-\u001e7\u0002\rqJg.\u001b;?)\u0005\t\u0003C\u0001\u000f\u0001\u00039\u0011VmY3jm\u0016$\u0016.\\3pkR\u0004\"\u0001H\u0002\u0014\t\r\tS\u0005\u000b\t\u0003-\u0019J!aJ\f\u0003\u000fA\u0013x\u000eZ;diB\u0011\u0011&\r\b\u0003U=r!a\u000b\u0018\u000e\u00031R!!L\n\u0002\rq\u0012xn\u001c;?\u0013\u0005A\u0012B\u0001\u0019\u0018\u0003\u001d\u0001\u0018mY6bO\u0016L!AM\u001a\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005A:B#A\u0012\u0002\u0017\u001d,G/\u00138ti\u0006t7-Z\u000b\u0002G\u0005i\u0001O]8ek\u000e$\bK]3gSb,\u0012!\u000f\t\u0003u}j\u0011a\u000f\u0006\u0003yu\nA\u0001\\1oO*\ta(\u0001\u0003kCZ\f\u0017B\u0001!<\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\t1\t\u0005\u0002\u0017\t&\u0011Qi\u0006\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003\u0011.\u0003\"AF%\n\u0005);\"aA!os\"9A\nCA\u0001\u0002\u0004\u0019\u0015a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/F\u0001P!\r\u00016\u000bS\u0007\u0002#*\u0011!kF\u0001\u000bG>dG.Z2uS>t\u0017B\u0001+R\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0005]S\u0006C\u0001\fY\u0013\tIvCA\u0004C_>dW-\u00198\t\u000f1S\u0011\u0011!a\u0001\u0011\u0006A\u0001.Y:i\u0007>$W\rF\u0001D\u0003!!xn\u0015;sS:<G#A\u001d\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0003\u0005\u0004\"A\u000f2\n\u0005\r\\$AB(cU\u0016\u001cG\u000f\u000b\u0003\u0004K\"L\u0007C\u0001\fg\u0013\t9wC\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t\u0011\u0001\u000b\u0003\u0003K\"L\u0007")
public abstract class ReceiveTimeout
implements PossiblyHarmful {
    public static boolean canEqual(Object x$1) {
        return ReceiveTimeout$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return ReceiveTimeout$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return ReceiveTimeout$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return ReceiveTimeout$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return ReceiveTimeout$.MODULE$.productPrefix();
    }

    public static ReceiveTimeout$ getInstance() {
        return ReceiveTimeout$.MODULE$.getInstance();
    }

    public static Iterator<String> productElementNames() {
        return ReceiveTimeout$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return ReceiveTimeout$.MODULE$.productElementName(n);
    }
}

