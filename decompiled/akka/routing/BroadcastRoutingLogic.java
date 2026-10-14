/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.BroadcastRoutingLogic$;
import akka.routing.NoRoutee$;
import akka.routing.Routee;
import akka.routing.RoutingLogic;
import akka.routing.SeveralRoutees;
import scala.collection.immutable.IndexedSeq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005i:Qa\u0002\u0005\t\u000251Qa\u0004\u0005\t\u0002AAQaF\u0001\u0005\u0002aAQ!G\u0001\u0005\u0002i1Aa\u0004\u0005\u00039!)q\u0003\u0002C\u00015!)\u0001\u0005\u0002C!C\u0005)\"I]8bI\u000e\f7\u000f\u001e*pkRLgn\u001a'pO&\u001c'BA\u0005\u000b\u0003\u001d\u0011x.\u001e;j]\u001eT\u0011aC\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u00059\tQ\"\u0001\u0005\u0003+\t\u0013x.\u00193dCN$(k\\;uS:<Gj\\4jGN\u0011\u0011!\u0005\t\u0003%Ui\u0011a\u0005\u0006\u0002)\u0005)1oY1mC&\u0011ac\u0005\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005i\u0011!B1qa2LH#A\u000e\u0011\u00059!1c\u0001\u0003\u0012;A\u0011aBH\u0005\u0003?!\u0011ABU8vi&tw\rT8hS\u000e\faa]3mK\u000e$Hc\u0001\u0012&UA\u0011abI\u0005\u0003I!\u0011aAU8vi\u0016,\u0007\"\u0002\u0014\u0007\u0001\u00049\u0013aB7fgN\fw-\u001a\t\u0003%!J!!K\n\u0003\u0007\u0005s\u0017\u0010C\u0003,\r\u0001\u0007A&A\u0004s_V$X-Z:\u0011\u00075\u0012$%D\u0001/\u0015\ty\u0003'A\u0005j[6,H/\u00192mK*\u0011\u0011gE\u0001\u000bG>dG.Z2uS>t\u0017BA\u001a/\u0005)Ie\u000eZ3yK\u0012\u001cV-\u001d\u0015\u0005\tUB\u0014\b\u0005\u0002\u0013m%\u0011qg\u0005\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!\u0001")
public final class BroadcastRoutingLogic
implements RoutingLogic {
    private static final long serialVersionUID = 1L;

    public static BroadcastRoutingLogic apply() {
        return BroadcastRoutingLogic$.MODULE$.apply();
    }

    @Override
    public Routee select(Object message, IndexedSeq<Routee> routees) {
        if (routees.isEmpty()) {
            return NoRoutee$.MODULE$;
        }
        return new SeveralRoutees(routees);
    }
}

