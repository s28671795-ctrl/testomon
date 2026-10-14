/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.dispatch.sysmsg.Resume;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class Resume$
extends AbstractFunction1<Throwable, Resume>
implements Serializable {
    public static final Resume$ MODULE$ = new Resume$();

    @Override
    public final String toString() {
        return "Resume";
    }

    @Override
    public Resume apply(Throwable causedByFailure) {
        return new Resume(causedByFailure);
    }

    public Option<Throwable> unapply(Resume x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Throwable>(x$0.causedByFailure());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Resume$.class);
    }

    private Resume$() {
    }
}

