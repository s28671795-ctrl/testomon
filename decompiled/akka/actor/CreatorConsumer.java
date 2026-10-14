/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import akka.japi.Creator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000553QAB\u0004\u0001\u0013-A\u0001B\u0006\u0001\u0003\u0002\u0003\u0006I\u0001\u0007\u0005\t_\u0001\u0011\t\u0011)A\u0005a!)a\u0007\u0001C\u0001o!)q\b\u0001C!\u0001\")1\n\u0001C!\u0019\ny1I]3bi>\u00148i\u001c8tk6,'O\u0003\u0002\t\u0013\u0005)\u0011m\u0019;pe*\t!\"\u0001\u0003bW.\f7c\u0001\u0001\r%A\u0011Q\u0002E\u0007\u0002\u001d)\tq\"A\u0003tG\u0006d\u0017-\u0003\u0002\u0012\u001d\t1\u0011I\\=SK\u001a\u0004\"a\u0005\u000b\u000e\u0003\u001dI!!F\u0004\u0003+%sG-\u001b:fGR\f5\r^8s!J|G-^2fe\u0006)1\r\\1{u\u000e\u0001\u0001GA\r'!\rQ\u0012\u0005\n\b\u00037}\u0001\"\u0001\b\b\u000e\u0003uQ!AH\f\u0002\rq\u0012xn\u001c;?\u0013\t\u0001c\"\u0001\u0004Qe\u0016$WMZ\u0005\u0003E\r\u0012Qa\u00117bgNT!\u0001\t\b\u0011\u0005\u00152C\u0002\u0001\u0003\nO\u0005\t\t\u0011!A\u0003\u0002!\u00121a\u0018\u00136#\tIC\u0006\u0005\u0002\u000eU%\u00111F\u0004\u0002\b\u001d>$\b.\u001b8h!\t\u0019R&\u0003\u0002/\u000f\t)\u0011i\u0019;pe\u000691M]3bi>\u0014\bcA\u00195Y5\t!G\u0003\u00024\u0013\u0005!!.\u00199j\u0013\t)$GA\u0004De\u0016\fGo\u001c:\u0002\rqJg.\u001b;?)\rA\u0014H\u0010\t\u0003'\u0001AQAF\u0002A\u0002i\u0002$aO\u001f\u0011\u0007i\tC\b\u0005\u0002&{\u0011Iq%OA\u0001\u0002\u0003\u0015\t\u0001\u000b\u0005\u0006_\r\u0001\r\u0001M\u0001\u000bC\u000e$xN]\"mCN\u001cX#A!1\u0005\tS\u0005cA\"I\u00136\tAI\u0003\u0002F\r\u0006!A.\u00198h\u0015\u00059\u0015\u0001\u00026bm\u0006L!A\t#\u0011\u0005\u0015RE!C\u0014\u0005\u0003\u0003\u0005\tQ!\u0001)\u0003\u001d\u0001(o\u001c3vG\u0016$\u0012\u0001\f")
public class CreatorConsumer
implements IndirectActorProducer {
    private final Class<? extends Actor> clazz;
    private final Creator<Actor> creator;

    @Override
    public Class<? extends Actor> actorClass() {
        return this.clazz;
    }

    @Override
    public Actor produce() {
        return this.creator.create();
    }

    public CreatorConsumer(Class<? extends Actor> clazz, Creator<Actor> creator) {
        this.clazz = clazz;
        this.creator = creator;
    }
}

