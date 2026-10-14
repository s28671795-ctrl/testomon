/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.annotation.DoNotInherit;
import akka.stream.CompletionStrategy$;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Mba\u0002\u0013&!\u0003\r\nCK\u0004\u0007\u0003c)\u0003\u0012Q#\u0007\u000b\u0011*\u0003\u0012Q\u001a\t\u000b\r\u0013A\u0011\u0001#\b\r\u001d\u0013\u0001\u0012Q\u0014I\r\u0019Q%\u0001#!(\u0017\")1)\u0002C\u0001\u001b\"9a*BA\u0001\n\u0003z\u0005b\u0002-\u0006\u0003\u0003%\t!\u0017\u0005\b;\u0016\t\t\u0011\"\u0001_\u0011\u001d!W!!A\u0005B\u0015Dq\u0001\\\u0003\u0002\u0002\u0013\u0005Q\u000eC\u0004s\u000b\u0005\u0005I\u0011I:\t\u000fQ,\u0011\u0011!C!k\"9a/BA\u0001\n\u00139x\u0001CA\u0004\u0005!\u0005u%!\u0003\u0007\u000fI\u0012\u0001\u0012Q\u0014\u0002\u001c!11\t\u0005C\u0001\u0003;AqA\u0014\t\u0002\u0002\u0013\u0005s\nC\u0004Y!\u0005\u0005I\u0011A-\t\u0011u\u0003\u0012\u0011!C\u0001\u0003?Aq\u0001\u001a\t\u0002\u0002\u0013\u0005S\r\u0003\u0005m!\u0005\u0005I\u0011AA\u0012\u0011\u001d\u0011\b#!A\u0005BMDq\u0001\u001e\t\u0002\u0002\u0013\u0005S\u000fC\u0004w!\u0005\u0005I\u0011B<\t\u000f\u00055!\u0001\"\u0001\u0002\u0010!9\u0011\u0011\u0003\u0002\u0005\u0002\u0005=\u0001b\u0002(\u0003\u0003\u0003%\te\u0014\u0005\b1\n\t\t\u0011\"\u0001Z\u0011!i&!!A\u0005\u0002\u0005M\u0001b\u00023\u0003\u0003\u0003%\t%\u001a\u0005\tY\n\t\t\u0011\"\u0001\u0002\u0018!9!OAA\u0001\n\u0003\u001a\bb\u0002;\u0003\u0003\u0003%\t%\u001e\u0005\bm\n\t\t\u0011\"\u0003x\u0005I\u0019u.\u001c9mKRLwN\\*ue\u0006$XmZ=\u000b\u0005\u0019:\u0013AB:ue\u0016\fWNC\u0001)\u0003\u0011\t7n[1\u0004\u0001M\u0011\u0001a\u000b\t\u0003Y=j\u0011!\f\u0006\u0002]\u0005)1oY1mC&\u0011\u0001'\f\u0002\u0007\u0003:L(+\u001a4*\u0007\u0001\u0001RA\u0001\u0005Ee\u0006Lg.\u001b8h'\u0011\u00111\u0006N\u001c\u0011\u00051*\u0014B\u0001\u001c.\u0005\u001d\u0001&o\u001c3vGR\u0004\"\u0001\u000f!\u000f\u0005erdB\u0001\u001e>\u001b\u0005Y$B\u0001\u001f*\u0003\u0019a$o\\8u}%\ta&\u0003\u0002@[\u00059\u0001/Y2lC\u001e,\u0017BA!C\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\tyT&\u0001\u0004=S:LGO\u0010\u000b\u0002\u000bB\u0011aIA\u0007\u0002K\u0005Y\u0011*\\7fI&\fG/\u001a7z!\tIU!D\u0001\u0003\u0005-IU.\\3eS\u0006$X\r\\=\u0014\u000b\u0015YC\nN\u001c\u0011\u0005\u0019\u0003A#\u0001%\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005\u0001\u0006CA)W\u001b\u0005\u0011&BA*U\u0003\u0011a\u0017M\\4\u000b\u0003U\u000bAA[1wC&\u0011qK\u0015\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003i\u0003\"\u0001L.\n\u0005qk#aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HCA0c!\ta\u0003-\u0003\u0002b[\t\u0019\u0011I\\=\t\u000f\rL\u0011\u0011!a\u00015\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\u0012A\u001a\t\u0004O*|V\"\u00015\u000b\u0005%l\u0013AC2pY2,7\r^5p]&\u00111\u000e\u001b\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0002ocB\u0011Af\\\u0005\u0003a6\u0012qAQ8pY\u0016\fg\u000eC\u0004d\u0017\u0005\u0005\t\u0019A0\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012AW\u0001\ti>\u001cFO]5oOR\t\u0001+\u0001\u0007xe&$XMU3qY\u0006\u001cW\rF\u0001y!\t\t\u00160\u0003\u0002{%\n1qJ\u00196fGRD#!\u0002?\u0011\u0007u\f\t!D\u0001\u007f\u0015\tyx%\u0001\u0006b]:|G/\u0019;j_:L1!a\u0001\u007f\u0005-Ie\u000e^3s]\u0006d\u0017\t]5)\u0005\u0011a\u0018\u0001\u0003#sC&t\u0017N\\4\u0011\u0005%\u0003\u0002FA\b}\u0003-IW.\\3eS\u0006$X\r\\=\u0016\u00031\u000b\u0001\u0002\u001a:bS:Lgn\u001a\u000b\u0004?\u0006U\u0001bB2\u001f\u0003\u0003\u0005\rA\u0017\u000b\u0004]\u0006e\u0001bB2!\u0003\u0003\u0005\raX\n\u0006!-bEg\u000e\u000b\u0003\u0003\u0013!2aXA\u0011\u0011\u001d\u0019G#!AA\u0002i#2A\\A\u0013\u0011\u001d\u0019g#!AA\u0002}C#\u0001\u0005?)\u0007\u0001\tY\u0003E\u0002~\u0003[I1!a\f\u007f\u00051!uNT8u\u0013:DWM]5u\u0003I\u0019u.\u001c9mKRLwN\\*ue\u0006$XmZ=")
@DoNotInherit
public interface CompletionStrategy {
    public static boolean canEqual(Object x$1) {
        return CompletionStrategy$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return CompletionStrategy$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return CompletionStrategy$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return CompletionStrategy$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return CompletionStrategy$.MODULE$.productPrefix();
    }

    public static CompletionStrategy draining() {
        return CompletionStrategy$.MODULE$.draining();
    }

    public static CompletionStrategy immediately() {
        return CompletionStrategy$.MODULE$.immediately();
    }

    public static Iterator<String> productElementNames() {
        return CompletionStrategy$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return CompletionStrategy$.MODULE$.productElementName(n);
    }
}

