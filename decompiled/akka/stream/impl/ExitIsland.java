/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.ExitIsland$;
import akka.stream.impl.Traversal;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0019<aa\u0003\u0007\t\u0002B\u0011bA\u0002\u000b\r\u0011\u0003\u0003R\u0003C\u00030\u0003\u0011\u0005\u0001\u0007C\u00042\u0003\u0005\u0005I\u0011\t\u001a\t\u000fm\n\u0011\u0011!C\u0001y!9\u0001)AA\u0001\n\u0003\t\u0005bB$\u0002\u0003\u0003%\t\u0005\u0013\u0005\b\u001f\u0006\t\t\u0011\"\u0001Q\u0011\u001d)\u0016!!A\u0005BYCqaV\u0001\u0002\u0002\u0013\u0005\u0003\fC\u0004Z\u0003\u0005\u0005I\u0011\u0002.\u0002\u0015\u0015C\u0018\u000e^%tY\u0006tGM\u0003\u0002\u000e\u001d\u0005!\u0011.\u001c9m\u0015\ty\u0001#\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002#\u0005!\u0011m[6b!\t\u0019\u0012!D\u0001\r\u0005))\u00050\u001b;Jg2\fg\u000eZ\n\u0006\u0003YarD\t\t\u0003/ii\u0011\u0001\u0007\u0006\u00023\u0005)1oY1mC&\u00111\u0004\u0007\u0002\u0007\u0003:L(+\u001a4\u0011\u0005Mi\u0012B\u0001\u0010\r\u0005%!&/\u0019<feN\fG\u000e\u0005\u0002\u0018A%\u0011\u0011\u0005\u0007\u0002\b!J|G-^2u!\t\u0019CF\u0004\u0002%U9\u0011Q%K\u0007\u0002M)\u0011q\u0005K\u0001\u0007yI|w\u000e\u001e \u0004\u0001%\t\u0011$\u0003\u0002,1\u00059\u0001/Y2lC\u001e,\u0017BA\u0017/\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\tY\u0003$\u0001\u0004=S:LGO\u0010\u000b\u0002%\u0005i\u0001O]8ek\u000e$\bK]3gSb,\u0012a\r\t\u0003iej\u0011!\u000e\u0006\u0003m]\nA\u0001\\1oO*\t\u0001(\u0001\u0003kCZ\f\u0017B\u0001\u001e6\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\tQ\b\u0005\u0002\u0018}%\u0011q\b\u0007\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003\u0005\u0016\u0003\"aF\"\n\u0005\u0011C\"aA!os\"9a)BA\u0001\u0002\u0004i\u0014a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/F\u0001J!\rQUJQ\u0007\u0002\u0017*\u0011A\nG\u0001\u000bG>dG.Z2uS>t\u0017B\u0001(L\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0005E#\u0006CA\fS\u0013\t\u0019\u0006DA\u0004C_>dW-\u00198\t\u000f\u0019;\u0011\u0011!a\u0001\u0005\u0006A\u0001.Y:i\u0007>$W\rF\u0001>\u0003!!xn\u0015;sS:<G#A\u001a\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0003m\u0003\"\u0001\u000e/\n\u0005u+$AB(cU\u0016\u001cG\u000f\u000b\u0002\u0002?B\u0011\u0001mY\u0007\u0002C*\u0011!\rE\u0001\u000bC:tw\u000e^1uS>t\u0017B\u00013b\u0005-Ie\u000e^3s]\u0006d\u0017\t]5)\u0005\u0001y\u0006")
@InternalApi
public final class ExitIsland {
    public static String toString() {
        return ExitIsland$.MODULE$.toString();
    }

    public static int hashCode() {
        return ExitIsland$.MODULE$.hashCode();
    }

    public static boolean canEqual(Object x$1) {
        return ExitIsland$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return ExitIsland$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return ExitIsland$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return ExitIsland$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return ExitIsland$.MODULE$.productPrefix();
    }

    public static Iterator<String> productElementNames() {
        return ExitIsland$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return ExitIsland$.MODULE$.productElementName(n);
    }

    public static Traversal rewireFirstTo(int relativeOffset) {
        return ExitIsland$.MODULE$.rewireFirstTo(relativeOffset);
    }

    public static Traversal concat(Traversal that) {
        return ExitIsland$.MODULE$.concat(that);
    }
}

