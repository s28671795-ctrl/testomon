/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.ConfigurationException;
import akka.actor.ActorPath;
import akka.actor.ActorSystemImpl;
import akka.actor.Cell;
import akka.actor.InternalActorRef;
import akka.actor.Props;
import akka.actor.RepointableActorRef;
import akka.actor.UnstartedCell;
import akka.dispatch.BalancingDispatcher;
import akka.dispatch.MailboxType;
import akka.dispatch.MessageDispatcher;
import akka.routing.NoRouter$;
import akka.routing.Pool;
import akka.routing.ResizablePoolCell;
import akka.routing.RoutedActorCell;
import akka.routing.RouterConfig;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q3QAC\u0006\u0001\u001b=A\u0011B\u0006\u0001\u0003\u0002\u0003\u0006I\u0001G\u000e\t\u0013u\u0001!\u0011!Q\u0001\ny\t\u0003\"C\u0012\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0013+\u0011%a\u0003A!A!\u0002\u0013i\u0003\u0007\u0003\u00053\u0001\t\u0005\t\u0015!\u0003\u001f\u0011%\u0019\u0004A!A!\u0002\u0013!t\u0007C\u0005:\u0001\t\u0005\t\u0015!\u0003;{!)q\b\u0001C\u0001\u0001\")!\n\u0001C!\u0017\nq!k\\;uK\u0012\f5\r^8s%\u00164'B\u0001\u0007\u000e\u0003\u001d\u0011x.\u001e;j]\u001eT\u0011AD\u0001\u0005C.\\\u0017m\u0005\u0002\u0001!A\u0011\u0011\u0003F\u0007\u0002%)\u00111#D\u0001\u0006C\u000e$xN]\u0005\u0003+I\u00111CU3q_&tG/\u00192mK\u0006\u001bGo\u001c:SK\u001a\fqaX:zgR,Wn\u0001\u0001\u0011\u0005EI\u0012B\u0001\u000e\u0013\u0005=\t5\r^8s'f\u001cH/Z7J[Bd\u0017B\u0001\u000f\u0015\u0003\u0019\u0019\u0018p\u001d;f[\u0006aqL]8vi\u0016\u0014\bK]8qgB\u0011\u0011cH\u0005\u0003AI\u0011Q\u0001\u0015:paNL!A\t\u000b\u0002\u000bA\u0014x\u000e]:\u0002#}\u0013x.\u001e;fe\u0012K7\u000f]1uG\",'\u000f\u0005\u0002&Q5\taE\u0003\u0002(\u001b\u0005AA-[:qCR\u001c\u0007.\u0003\u0002*M\t\tR*Z:tC\u001e,G)[:qCR\u001c\u0007.\u001a:\n\u0005-\"\u0012A\u00033jgB\fGo\u00195fe\u0006qqL]8vi\u0016\u0014X*Y5mE>D\bCA\u0013/\u0013\tycEA\u0006NC&d'm\u001c=UsB,\u0017BA\u0019\u0015\u0003-i\u0017-\u001b7c_b$\u0016\u0010]3\u0002\u0019}\u0013x.\u001e;fKB\u0013x\u000e]:\u0002\u0017}\u001bX\u000f]3sm&\u001cxN\u001d\t\u0003#UJ!A\u000e\n\u0003!%sG/\u001a:oC2\f5\r^8s%\u00164\u0017B\u0001\u001d\u0015\u0003)\u0019X\u000f]3sm&\u001cxN]\u0001\u0006?B\fG\u000f\u001b\t\u0003#mJ!\u0001\u0010\n\u0003\u0013\u0005\u001bGo\u001c:QCRD\u0017B\u0001 \u0015\u0003\u0011\u0001\u0018\r\u001e5\u0002\rqJg.\u001b;?)!\t5\tR#G\u000f\"K\u0005C\u0001\"\u0001\u001b\u0005Y\u0001\"\u0002\f\t\u0001\u0004A\u0002\"B\u000f\t\u0001\u0004q\u0002\"B\u0012\t\u0001\u0004!\u0003\"\u0002\u0017\t\u0001\u0004i\u0003\"\u0002\u001a\t\u0001\u0004q\u0002\"B\u001a\t\u0001\u0004!\u0004\"B\u001d\t\u0001\u0004Q\u0014a\u00028fo\u000e+G\u000e\u001c\u000b\u0003\u0019>\u0003\"!E'\n\u00059\u0013\"\u0001B\"fY2DQ\u0001U\u0005A\u0002E\u000b1a\u001c7e!\t\t\"+\u0003\u0002T%\tiQK\\:uCJ$X\rZ\"fY2\u0004")
public class RoutedActorRef
extends RepointableActorRef {
    private final Props _routeeProps;

    @Override
    public Cell newCell(UnstartedCell old) {
        Pool pool;
        RouterConfig routerConfig = this.props().routerConfig();
        RoutedActorCell cell = routerConfig instanceof Pool && (pool = (Pool)routerConfig).resizer().isDefined() ? new ResizablePoolCell(this.system(), this, this.props(), this.dispatcher(), this._routeeProps, this.supervisor(), pool) : new RoutedActorCell(this.system(), this, this.props(), this.dispatcher(), this._routeeProps, this.supervisor());
        return cell.init(false, this.mailboxType());
    }

    public RoutedActorRef(ActorSystemImpl _system, Props _routerProps, MessageDispatcher _routerDispatcher, MailboxType _routerMailbox, Props _routeeProps, InternalActorRef _supervisor, ActorPath _path) {
        this._routeeProps = _routeeProps;
        super(_system, _routerProps, _routerDispatcher, _routerMailbox, _supervisor, _path);
        RouterConfig routerConfig = super.props().routerConfig();
        NoRouter$ noRouter$ = NoRouter$.MODULE$;
        if ((routerConfig == null ? noRouter$ != null : !routerConfig.equals(noRouter$)) && super.dispatcher() instanceof BalancingDispatcher) {
            throw new ConfigurationException(new StringBuilder(137).append("Configuration for ").append(this).append(" is invalid - you can not use a 'BalancingDispatcher' as a Router's dispatcher, you can however use it for the routees.").toString());
        }
        super.props().routerConfig().verifyConfig(super.path());
    }
}

