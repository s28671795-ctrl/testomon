/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.Pair;
import akka.stream.FanOutShape2;
import akka.stream.Graph;
import akka.stream.javadsl.Unzip$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q;Q!\u0002\u0004\t\u000251Qa\u0004\u0004\t\u0002AAQaF\u0001\u0005\u0002aAQ!G\u0001\u0005\u0002iAQ!G\u0001\u0005\u0002m\nQ!\u00168{SBT!a\u0002\u0005\u0002\u000f)\fg/\u00193tY*\u0011\u0011BC\u0001\u0007gR\u0014X-Y7\u000b\u0003-\tA!Y6lC\u000e\u0001\u0001C\u0001\b\u0002\u001b\u00051!!B+ou&\u00048CA\u0001\u0012!\t\u0011R#D\u0001\u0014\u0015\u0005!\u0012!B:dC2\f\u0017B\u0001\f\u0014\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012!D\u0001\u0007GJ,\u0017\r^3\u0016\u0007mYS\u0007F\u0001\u001d!\u0011ib\u0004I\u001c\u000e\u0003!I!a\b\u0005\u0003\u000b\u001d\u0013\u0018\r\u001d5\u0011\u000bu\t3%\u000b\u001b\n\u0005\tB!\u0001\u0004$b]>+Ho\u00155ba\u0016\u0014\u0004\u0003\u0002\u0013(SQj\u0011!\n\u0006\u0003M)\tAA[1qS&\u0011\u0001&\n\u0002\u0005!\u0006L'\u000f\u0005\u0002+W1\u0001A!\u0002\u0017\u0004\u0005\u0004i#!A!\u0012\u00059\n\u0004C\u0001\n0\u0013\t\u00014CA\u0004O_RD\u0017N\\4\u0011\u0005I\u0011\u0014BA\u001a\u0014\u0005\r\te.\u001f\t\u0003UU\"QAN\u0002C\u00025\u0012\u0011A\u0011\t\u0003qej\u0011AC\u0005\u0003u)\u0011qAT8u+N,G-F\u0002=\u0003\u000e#2!\u0010#Y!\u0011ibDP\u001c\u0011\u000bu\ts\b\u0011\"\u0011\t\u0011:\u0003I\u0011\t\u0003U\u0005#Q\u0001\f\u0003C\u00025\u0002\"AK\"\u0005\u000bY\"!\u0019A\u0017\t\u000b\u0015#\u0001\u0019\u0001$\u0002\t1,g\r\u001e\t\u0004\u000f:\u0003eB\u0001%M!\tI5#D\u0001K\u0015\tYE\"\u0001\u0004=e>|GOP\u0005\u0003\u001bN\ta\u0001\u0015:fI\u00164\u0017BA(Q\u0005\u0015\u0019E.Y:t\u0015\ti5\u0003\u000b\u0002E%B\u00111KV\u0007\u0002)*\u0011QKC\u0001\u0005kRLG.\u0003\u0002X)\n1QO\\;tK\u0012DQ!\u0017\u0003A\u0002i\u000bQA]5hQR\u00042a\u0012(CQ\tA&\u000b")
public final class Unzip {
    public static <A, B> Graph<FanOutShape2<Pair<A, B>, A, B>, NotUsed> create(Class<A> left, Class<B> right) {
        return Unzip$.MODULE$.create(left, right);
    }

    public static <A, B> Graph<FanOutShape2<Pair<A, B>, A, B>, NotUsed> create() {
        return Unzip$.MODULE$.create();
    }
}

