/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import java.nio.channels.CompletionHandler;
import scala.Function1;
import scala.Predef$;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

public final class FileSource$ {
    public static final FileSource$ MODULE$ = new FileSource$();
    private static final CompletionHandler<Integer, Function1<Try<Object>, BoxedUnit>> completionHandler = new CompletionHandler<Integer, Function1<Try<Object>, BoxedUnit>>(){

        public void completed(Integer result, Function1<Try<Object>, BoxedUnit> attachment) {
            attachment.apply(new Success<Integer>(BoxesRunTime.boxToInteger(Predef$.MODULE$.Integer2int(result))));
        }

        public void failed(Throwable ex, Function1<Try<Object>, BoxedUnit> attachment) {
            attachment.apply(new Failure<T>(ex));
        }
    };

    public CompletionHandler<Integer, Function1<Try<Object>, BoxedUnit>> completionHandler() {
        return completionHandler;
    }

    private FileSource$() {
    }
}

