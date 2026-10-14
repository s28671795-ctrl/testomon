/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.Done;
import akka.Done$;
import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.pattern.StatusReply;
import akka.pattern.StatusReply$Error$;
import akka.pattern.StatusReply$Success$;
import java.io.Serializable;
import scala.Function1;
import scala.MatchError;
import scala.Option;
import scala.concurrent.Future;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

public final class StatusReply$ {
    public static final StatusReply$ MODULE$ = new StatusReply$();
    private static final StatusReply<Done> Ack = MODULE$.success(Done$.MODULE$);

    public StatusReply<Done> Ack() {
        return Ack;
    }

    public StatusReply<Done> ack() {
        return this.Ack();
    }

    public <T> StatusReply<T> success(T value) {
        return new StatusReply<T>(new Success<T>(value));
    }

    public <T> StatusReply<T> error(String errorMessage) {
        return StatusReply$Error$.MODULE$.apply(errorMessage);
    }

    public <T> StatusReply<T> error(Throwable exception) {
        return StatusReply$Error$.MODULE$.apply(exception);
    }

    public <T> StatusReply<T> fromTryKeepException(Try<T> status) {
        return new StatusReply<T>(status);
    }

    public <T> StatusReply<T> fromTry(Try<T> status) {
        Try<T> try_ = status;
        if (try_ instanceof Success) {
            Success success = (Success)try_;
            Object value = success.value();
            return this.success(value);
        }
        if (try_ instanceof Failure) {
            Failure failure = (Failure)try_;
            Throwable t2 = failure.exception();
            return this.error(t2.getMessage());
        }
        throw new MatchError(try_);
    }

    @InternalApi
    public <T> Future<T> flattenStatusFuture(Future<StatusReply<T>> f) {
        return f.transform((Function1<Try, Try> & Serializable)x0$1 -> {
            Try try_ = x0$1;
            if (try_ instanceof Success) {
                Option<Throwable> option;
                Option<Object> option2;
                Success success = (Success)try_;
                StatusReply s2 = (StatusReply)success.value();
                StatusReply statusReply = s2;
                if (statusReply != null && !(option2 = StatusReply$Success$.MODULE$.unapply(statusReply)).isEmpty()) {
                    Object v = option2.get();
                    return new Success<Object>(v);
                }
                if (statusReply != null && !(option = StatusReply$Error$.MODULE$.unapply(statusReply)).isEmpty()) {
                    Throwable ex = option.get();
                    return new Failure(ex);
                }
                return new Failure(new IllegalArgumentException(new StringBuilder(39).append("Unexpected status reply success value: ").append(statusReply).toString()));
            }
            if (try_ instanceof Failure) {
                Failure failure = (Failure)try_;
                return failure;
            }
            throw new MatchError(try_);
        }, ExecutionContexts$.MODULE$.parasitic());
    }

    private StatusReply$() {
    }
}

