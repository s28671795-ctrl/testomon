/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.annotation.InternalApi;
import akka.io.dns.ResourceRecord;
import akka.io.dns.ResourceRecord$;
import akka.io.dns.internal.Message;
import akka.io.dns.internal.MessageFlags;
import akka.io.dns.internal.MessageFlags$;
import akka.io.dns.internal.Question;
import akka.io.dns.internal.Question$;
import akka.io.dns.internal.package$;
import akka.util.ByteIterator;
import akka.util.ByteString;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.Tuple6;
import scala.collection.IterableOnce;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Seq$;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.RichInt$;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.Try$;

@InternalApi
public final class Message$
implements Serializable {
    public static final Message$ MODULE$ = new Message$();

    public Seq<Question> $lessinit$greater$default$3() {
        return (Seq)Seq$.MODULE$.empty();
    }

    public Seq<ResourceRecord> $lessinit$greater$default$4() {
        return (Seq)Seq$.MODULE$.empty();
    }

    public Seq<ResourceRecord> $lessinit$greater$default$5() {
        return (Seq)Seq$.MODULE$.empty();
    }

    public Seq<ResourceRecord> $lessinit$greater$default$6() {
        return (Seq)Seq$.MODULE$.empty();
    }

    public Message parse(ByteString msg) {
        ByteIterator it = msg.iterator();
        short id = it.getShort(package$.MODULE$.networkByteOrder());
        short flags = it.getShort(package$.MODULE$.networkByteOrder());
        short qdCount = it.getShort(package$.MODULE$.networkByteOrder());
        short anCount = it.getShort(package$.MODULE$.networkByteOrder());
        short nsCount = it.getShort(package$.MODULE$.networkByteOrder());
        short arCount = it.getShort(package$.MODULE$.networkByteOrder());
        Object qs = RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), qdCount).map((Function1<Object, Try> & Serializable)x$2 -> Try$.MODULE$.apply((Function0<Question> & Serializable)() -> Question$.MODULE$.parse(it$1, msg$1)));
        Object ans = RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), anCount).map((Function1<Object, Try> & Serializable)x$3 -> Try$.MODULE$.apply((Function0<ResourceRecord> & Serializable)() -> ResourceRecord$.MODULE$.parse(it$1, msg$1)));
        Object nss = RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), nsCount).map((Function1<Object, Try> & Serializable)x$4 -> Try$.MODULE$.apply((Function0<ResourceRecord> & Serializable)() -> ResourceRecord$.MODULE$.parse(it$1, msg$1)));
        Object ars = RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), arCount).map((Function1<Object, Try> & Serializable)x$5 -> Try$.MODULE$.apply((Function0<ResourceRecord> & Serializable)() -> ResourceRecord$.MODULE$.parse(it$1, msg$1)));
        return new Message(id, flags, (Seq)qs.flatten((Function1<Try, IterableOnce> & Serializable)tried -> Message$.flattener$1(tried, flags)), (Seq)ans.flatten((Function1<Try, IterableOnce> & Serializable)tried -> Message$.flattener$1(tried, flags)), (Seq)nss.flatten((Function1<Try, IterableOnce> & Serializable)tried -> Message$.flattener$1(tried, flags)), (Seq)ars.flatten((Function1<Try, IterableOnce> & Serializable)tried -> Message$.flattener$1(tried, flags)));
    }

    public Message apply(short id, short flags, Seq<Question> questions, Seq<ResourceRecord> answerRecs, Seq<ResourceRecord> authorityRecs, Seq<ResourceRecord> additionalRecs) {
        return new Message(id, flags, questions, answerRecs, authorityRecs, additionalRecs);
    }

    public Seq<Question> apply$default$3() {
        return (Seq)Seq$.MODULE$.empty();
    }

    public Seq<ResourceRecord> apply$default$4() {
        return (Seq)Seq$.MODULE$.empty();
    }

    public Seq<ResourceRecord> apply$default$5() {
        return (Seq)Seq$.MODULE$.empty();
    }

    public Seq<ResourceRecord> apply$default$6() {
        return (Seq)Seq$.MODULE$.empty();
    }

    public Option<Tuple6<Object, MessageFlags, Seq<Question>, Seq<ResourceRecord>, Seq<ResourceRecord>, Seq<ResourceRecord>>> unapply(Message x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple6<Object, MessageFlags, Seq<Question>, Seq<ResourceRecord>, Seq<ResourceRecord>, Seq<ResourceRecord>>>(new Tuple6<Short, MessageFlags, Seq<Question>, Seq<ResourceRecord>, Seq<ResourceRecord>, Seq<ResourceRecord>>(BoxesRunTime.boxToShort(x$0.id()), new MessageFlags(x$0.flags()), x$0.questions(), x$0.answerRecs(), x$0.authorityRecs(), x$0.additionalRecs()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Message$.class);
    }

    private static final IterableOnce flattener$1(Try tried, short flags$1) {
        if (MessageFlags$.MODULE$.isTruncated$extension(flags$1)) {
            return tried.toOption();
        }
        Try try_ = tried;
        if (try_ instanceof Success) {
            Success success = (Success)try_;
            Object value = success.value();
            return new Some(value);
        }
        if (try_ instanceof Failure) {
            Failure failure = (Failure)try_;
            Throwable reason = failure.exception();
            throw reason;
        }
        throw new MatchError(try_);
    }

    private Message$() {
    }
}

