/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.Creator;
import akka.japi.Pair;
import akka.stream.impl.JavaFlowAndRsConverters;
import akka.stream.javadsl.AsPublisher;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.RunnableGraph;
import java.util.concurrent.Flow;

public final class JavaFlowSupport {
    private static final NotUsed NotUsed = akka.NotUsed.getInstance();

    private JavaFlowSupport() {
        throw new RuntimeException("No instances allowed!");
    }

    public static final class Sink {
        private Sink() {
            throw new RuntimeException("No instances allowed!");
        }

        public static <T> akka.stream.javadsl.Sink<T, Flow.Publisher<T>> asPublisher(AsPublisher asPublisher) {
            return akka.stream.javadsl.Sink.asPublisher(asPublisher).mapMaterializedValue(JavaFlowAndRsConverters::asJava);
        }

        public static <T> akka.stream.javadsl.Sink<T, NotUsed> fromSubscriber(Flow.Subscriber<T> subscriber) {
            return akka.stream.javadsl.Sink.fromSubscriber(JavaFlowAndRsConverters.asRs(subscriber));
        }
    }

    public static final class Flow {
        private Flow() {
            throw new RuntimeException("No instances allowed!");
        }

        public static <I, O> akka.stream.javadsl.Flow<I, O, NotUsed> fromProcessor(Creator<Flow.Processor<I, O>> creator) throws Exception {
            return Flow.fromProcessorMat(() -> Pair.apply((Flow.Processor)creator.create(), NotUsed));
        }

        public static <I, O, M> akka.stream.javadsl.Flow<I, O, M> fromProcessorMat(Creator<Pair<Flow.Processor<I, O>, M>> creator) throws Exception {
            Pair<Flow.Processor<I, O>, M> pair = creator.create();
            Flow.Processor processor = pair.first();
            Object m4 = pair.second();
            return akka.stream.javadsl.Flow.fromProcessorMat(() -> Pair.apply(JavaFlowAndRsConverters.asRs(processor), m4));
        }

        public static <In, Out, Mat> RunnableGraph<Flow.Processor<In, Out>> toProcessor(akka.stream.javadsl.Flow<In, Out, Mat> flow) {
            akka.stream.javadsl.Source source2 = Source.asSubscriber();
            akka.stream.javadsl.Sink sink2 = Sink.asPublisher(AsPublisher.WITHOUT_FANOUT);
            return source2.via(flow).toMat(sink2, Keep.both()).mapMaterializedValue(pair -> {
                final Flow.Subscriber subscriber = (Flow.Subscriber)pair.first();
                final Flow.Publisher publisher = (Flow.Publisher)pair.second();
                return new Flow.Processor<In, Out>(){

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                    }

                    @Override
                    public void onSubscribe(Flow.Subscription subscription) {
                        subscriber.onSubscribe(subscription);
                    }

                    @Override
                    public void onComplete() {
                        subscriber.onComplete();
                    }

                    @Override
                    public void onNext(In In2) {
                        subscriber.onNext(In2);
                    }

                    @Override
                    public void subscribe(Flow.Subscriber<? super Out> subscriber2) {
                        publisher.subscribe(subscriber2);
                    }
                };
            });
        }
    }

    public static final class Source {
        private Source() {
            throw new RuntimeException("No instances allowed!");
        }

        public static <T> akka.stream.javadsl.Source<T, NotUsed> fromPublisher(Flow.Publisher<T> publisher) {
            return akka.stream.javadsl.Source.fromPublisher(JavaFlowAndRsConverters.asRs(publisher));
        }

        public static <T> akka.stream.javadsl.Source<T, Flow.Subscriber<T>> asSubscriber() {
            return akka.stream.javadsl.Source.asSubscriber().mapMaterializedValue(JavaFlowAndRsConverters::asJava);
        }
    }
}

