/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.PriorityGenerator;
import scala.Function1;
import scala.runtime.BoxesRunTime;

public final class PriorityGenerator$ {
    public static final PriorityGenerator$ MODULE$ = new PriorityGenerator$();

    public PriorityGenerator apply(Function1<Object, Object> priorityFunction) {
        return new PriorityGenerator(priorityFunction){
            private final Function1 priorityFunction$1;

            public int gen(Object message) {
                return BoxesRunTime.unboxToInt(this.priorityFunction$1.apply(message));
            }
            {
                this.priorityFunction$1 = priorityFunction$1;
            }
        };
    }

    private PriorityGenerator$() {
    }
}

