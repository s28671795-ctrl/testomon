/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorPath;
import akka.actor.Props;
import akka.routing.NoRouter$;
import akka.routing.RouterConfig;
import akka.routing.RoutingLogic;
import scala.Option;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005-b!B\n\u0015\u0003\u0003I\u0002\"\u0002\u0013\u0001\t\u0003)s!B\u0017\u0015\u0011\u0003sc!B\n\u0015\u0011\u0003{\u0003\"\u0002\u0013\u0004\t\u0003y\u0004\"\u0002!\u0004\t\u0003\n\u0005BB'\u0004\t\u00032b\nC\u0003S\u0007\u0011\u00053\u000bC\u0003]\u0007\u0011\u0005S\fC\u0003a\u0007\u0011\u0005\u0011\rC\u0003c\u0007\u0011\u00051\rC\u0004j\u0007\u0005\u0005I\u0011\t6\t\u000fI\u001c\u0011\u0011!C\u0001g\"9qoAA\u0001\n\u0003A\bb\u0002@\u0004\u0003\u0003%\te \u0005\n\u0003\u001b\u0019\u0011\u0011!C\u0001\u0003\u001fA\u0011\"!\u0007\u0004\u0003\u0003%\t%a\u0007\t\u0013\u0005u1!!A\u0005B\u0005}\u0001\"CA\u0011\u0007\u0005\u0005I\u0011BA\u0012\u0005!quNU8vi\u0016\u0014(BA\u000b\u0017\u0003\u001d\u0011x.\u001e;j]\u001eT\u0011aF\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u0007\u0001Q\u0002\u0005\u0005\u0002\u001c=5\tADC\u0001\u001e\u0003\u0015\u00198-\u00197b\u0013\tyBD\u0001\u0004B]f\u0014VM\u001a\t\u0003C\tj\u0011\u0001F\u0005\u0003GQ\u0011ABU8vi\u0016\u00148i\u001c8gS\u001e\fa\u0001P5oSRtD#\u0001\u0014\u0011\u0005\u0005\u0002\u0001\u0006\u0002\u0001)W1\u0002\"aG\u0015\n\u0005)b\"\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\t\u0011\u0001\u0003(p%>,H/\u001a:\u0011\u0005\u0005\u001a1\u0003B\u0002'aM\u0002\"aG\u0019\n\u0005Ib\"a\u0002)s_\u0012,8\r\u001e\t\u0003iqr!!\u000e\u001e\u000f\u0005YJT\"A\u001c\u000b\u0005aB\u0012A\u0002\u001fs_>$h(C\u0001\u001e\u0013\tYD$A\u0004qC\u000e\\\u0017mZ3\n\u0005ur$\u0001D*fe&\fG.\u001b>bE2,'BA\u001e\u001d)\u0005q\u0013\u0001D2sK\u0006$XMU8vi\u0016\u0014HC\u0001\"F!\t\t3)\u0003\u0002E)\t1!k\\;uKJDQAR\u0003A\u0002\u001d\u000baa]=ti\u0016l\u0007C\u0001%L\u001b\u0005I%B\u0001&\u0017\u0003\u0015\t7\r^8s\u0013\ta\u0015JA\u0006BGR|'oU=ti\u0016l\u0017!E2sK\u0006$XMU8vi\u0016\u0014\u0018i\u0019;peR\tq\n\u0005\u0002\"!&\u0011\u0011\u000b\u0006\u0002\f%>,H/\u001a:BGR|'/\u0001\ts_V$XM\u001d#jgB\fGo\u00195feV\tA\u000b\u0005\u0002V3:\u0011ak\u0016\t\u0003mqI!\u0001\u0017\u000f\u0002\rA\u0013X\rZ3g\u0013\tQ6L\u0001\u0004TiJLgn\u001a\u0006\u00031r\tAb^5uQ\u001a\u000bG\u000e\u001c2bG.$\"\u0001\t0\t\u000b}C\u0001\u0019\u0001\u0011\u0002\u000b=$\b.\u001a:\u0002\u0017\u001d,G/\u00138ti\u0006t7-Z\u000b\u0002]\u0005)\u0001O]8qgR\u0011Am\u001a\t\u0003\u0011\u0016L!AZ%\u0003\u000bA\u0013x\u000e]:\t\u000b!T\u0001\u0019\u00013\u0002\u0017I|W\u000f^3f!J|\u0007o]\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0003-\u0004\"\u0001\\9\u000e\u00035T!A\\8\u0002\t1\fgn\u001a\u0006\u0002a\u0006!!.\u0019<b\u0013\tQV.\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001u!\tYR/\u0003\u0002w9\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011\u0011\u0010 \t\u00037iL!a\u001f\u000f\u0003\u0007\u0005s\u0017\u0010C\u0004~\u001b\u0005\u0005\t\u0019\u0001;\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\t\t\u0001E\u0003\u0002\u0004\u0005%\u00110\u0004\u0002\u0002\u0006)\u0019\u0011q\u0001\u000f\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002\f\u0005\u0015!\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!!\u0005\u0002\u0018A\u00191$a\u0005\n\u0007\u0005UADA\u0004C_>dW-\u00198\t\u000fu|\u0011\u0011!a\u0001s\u0006A\u0001.Y:i\u0007>$W\rF\u0001u\u0003!!xn\u0015;sS:<G#A6\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005\u0015\u0002c\u00017\u0002(%\u0019\u0011\u0011F7\u0003\r=\u0013'.Z2u\u0001")
public abstract class NoRouter
implements RouterConfig {
    private static final long serialVersionUID = 1L;

    public static boolean canEqual(Object x$1) {
        return NoRouter$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return NoRouter$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return NoRouter$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return NoRouter$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return NoRouter$.MODULE$.productPrefix();
    }

    public static Props props(Props routeeProps) {
        return NoRouter$.MODULE$.props(routeeProps);
    }

    public static NoRouter$ getInstance() {
        return NoRouter$.MODULE$.getInstance();
    }

    public static Iterator<String> productElementNames() {
        return NoRouter$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return NoRouter$.MODULE$.productElementName(n);
    }

    @Override
    public Option<Props> routingLogicController(RoutingLogic routingLogic) {
        return RouterConfig.routingLogicController$(this, routingLogic);
    }

    @Override
    public boolean isManagementMessage(Object msg) {
        return RouterConfig.isManagementMessage$(this, msg);
    }

    @Override
    public boolean stopRouterWhenAllRouteesRemoved() {
        return RouterConfig.stopRouterWhenAllRouteesRemoved$(this);
    }

    @Override
    public RouterConfig withFallback(RouterConfig other) {
        return RouterConfig.withFallback$(this, other);
    }

    @Override
    public void verifyConfig(ActorPath path) {
        RouterConfig.verifyConfig$(this, path);
    }

    public NoRouter() {
        RouterConfig.$init$(this);
    }
}

