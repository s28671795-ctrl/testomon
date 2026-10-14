/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy$;
import scala.PartialFunction;
import scala.Tuple2;
import scala.collection.Iterable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E2\u0001b\u0001\u0003\u0011\u0002\u0007\u0005\u0011B\f\u0005\u0006!\u0001!\t!\u0005\u0005\u0006+\u0001!\u0019A\u0006\u0002''V\u0004XM\u001d<jg>\u00148\u000b\u001e:bi\u0016<\u0017\u0010T8x!JLwN]5us&k\u0007\u000f\\5dSR\u001c(BA\u0003\u0007\u0003\u0015\t7\r^8s\u0015\u00059\u0011\u0001B1lW\u0006\u001c\u0001a\u0005\u0002\u0001\u0015A\u00111BD\u0007\u0002\u0019)\tQ\"A\u0003tG\u0006d\u0017-\u0003\u0002\u0010\u0019\t1\u0011I\\=SK\u001a\fa\u0001J5oSR$C#\u0001\n\u0011\u0005-\u0019\u0012B\u0001\u000b\r\u0005\u0011)f.\u001b;\u00023M,\u0017oQ1vg\u0016$\u0015N]3di&4XM\r#fG&$WM\u001d\u000b\u0003/u\u0001\"\u0001G\r\u000e\u0003\u0001I!AG\u000e\u0003\u000f\u0011+7-\u001b3fe*\u0011A\u0004B\u0001\u0013'V\u0004XM\u001d<jg>\u00148\u000b\u001e:bi\u0016<\u0017\u0010C\u0003\u001f\u0005\u0001\u0007q$\u0001\u0005ue\u0006\u0004X\t_5u!\r\u0001\u0003f\u000b\b\u0003C\u0019r!AI\u0013\u000e\u0003\rR!\u0001\n\u0005\u0002\rq\u0012xn\u001c;?\u0013\u0005i\u0011BA\u0014\r\u0003\u001d\u0001\u0018mY6bO\u0016L!!\u000b\u0016\u0003\u0011%#XM]1cY\u0016T!a\n\u0007\u0011\u0005aa\u0013BA\u0017\u001c\u00059\u0019\u0015-^:f\t&\u0014Xm\u0019;jm\u0016t!a\f\u0019\u000e\u0003\u0011I!\u0001\b\u0003")
public interface SupervisorStrategyLowPriorityImplicits {
    public static /* synthetic */ PartialFunction seqCauseDirective2Decider$(SupervisorStrategyLowPriorityImplicits $this, Iterable trapExit) {
        return $this.seqCauseDirective2Decider(trapExit);
    }

    default public PartialFunction<Throwable, SupervisorStrategy.Directive> seqCauseDirective2Decider(Iterable<Tuple2<Class<? extends Throwable>, SupervisorStrategy.Directive>> trapExit) {
        return ((SupervisorStrategy$)this).makeDecider(trapExit);
    }

    public static void $init$(SupervisorStrategyLowPriorityImplicits $this) {
    }
}

