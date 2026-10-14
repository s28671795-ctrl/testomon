/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.Pair;
import akka.japi.function.Function;
import akka.japi.tuple.Tuple18;
import akka.japi.tuple.Tuple19;
import akka.japi.tuple.Tuple20;
import akka.japi.tuple.Tuple21;
import akka.japi.tuple.Tuple22;
import akka.japi.tuple.Tuple3;
import akka.japi.tuple.Tuple4;
import akka.japi.tuple.Tuple5;
import akka.japi.tuple.Tuple6;
import akka.japi.tuple.Tuple7;
import akka.japi.tuple.Tuple8;
import akka.japi.tuple.Tuple9;
import akka.stream.FanOutShape10;
import akka.stream.FanOutShape11;
import akka.stream.FanOutShape12;
import akka.stream.FanOutShape13;
import akka.stream.FanOutShape14;
import akka.stream.FanOutShape15;
import akka.stream.FanOutShape16;
import akka.stream.FanOutShape17;
import akka.stream.FanOutShape18;
import akka.stream.FanOutShape19;
import akka.stream.FanOutShape2;
import akka.stream.FanOutShape20;
import akka.stream.FanOutShape21;
import akka.stream.FanOutShape22;
import akka.stream.FanOutShape3;
import akka.stream.FanOutShape4;
import akka.stream.FanOutShape5;
import akka.stream.FanOutShape6;
import akka.stream.FanOutShape7;
import akka.stream.FanOutShape8;
import akka.stream.FanOutShape9;
import akka.stream.Graph;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject10$;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject11$;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject12$;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject13$;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject14$;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject15$;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject16$;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject17$;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject18$;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject19$;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject2$;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject20$;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject21$;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject22$;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject3$;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject4$;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject5$;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject6$;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject7$;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject8$;
import akka.stream.scaladsl.UnzipWithApply$UnzipWithCreatorObject9$;
import java.io.Serializable;
import scala.Function1;
import scala.MatchError;
import scala.Tuple10;
import scala.Tuple11;
import scala.Tuple12;
import scala.Tuple13;
import scala.Tuple14;
import scala.Tuple15;
import scala.Tuple16;
import scala.Tuple17;
import scala.Tuple2;

public final class UnzipWith$ {
    public static final UnzipWith$ MODULE$ = new UnzipWith$();

    public <In, A, B> Graph<FanOutShape2<In, A, B>, NotUsed> create(Function<In, Pair<A, B>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, Tuple2> & Serializable)in -> {
            Pair pair = (Pair)f.apply(in);
            if (pair != null) {
                Object a = pair.first();
                Object b = pair.second();
                return new Tuple2(a, b);
            }
            throw new MatchError(pair);
        }, UnzipWithApply$UnzipWithCreatorObject2$.MODULE$);
    }

    public <In, T1, T2, T3> Graph<FanOutShape3<In, T1, T2, T3>, NotUsed> create3(Function<In, Tuple3<T1, T2, T3>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, scala.Tuple3> & Serializable)in -> ((Tuple3)f.apply(in)).toScala(), UnzipWithApply$UnzipWithCreatorObject3$.MODULE$);
    }

    public <In, T1, T2, T3, T4> Graph<FanOutShape4<In, T1, T2, T3, T4>, NotUsed> create4(Function<In, Tuple4<T1, T2, T3, T4>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, scala.Tuple4> & Serializable)in -> ((Tuple4)f.apply(in)).toScala(), UnzipWithApply$UnzipWithCreatorObject4$.MODULE$);
    }

    public <In, T1, T2, T3, T4, T5> Graph<FanOutShape5<In, T1, T2, T3, T4, T5>, NotUsed> create5(Function<In, Tuple5<T1, T2, T3, T4, T5>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, scala.Tuple5> & Serializable)in -> ((Tuple5)f.apply(in)).toScala(), UnzipWithApply$UnzipWithCreatorObject5$.MODULE$);
    }

    public <In, T1, T2, T3, T4, T5, T6> Graph<FanOutShape6<In, T1, T2, T3, T4, T5, T6>, NotUsed> create6(Function<In, Tuple6<T1, T2, T3, T4, T5, T6>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, scala.Tuple6> & Serializable)in -> ((Tuple6)f.apply(in)).toScala(), UnzipWithApply$UnzipWithCreatorObject6$.MODULE$);
    }

    public <In, T1, T2, T3, T4, T5, T6, T7> Graph<FanOutShape7<In, T1, T2, T3, T4, T5, T6, T7>, NotUsed> create7(Function<In, Tuple7<T1, T2, T3, T4, T5, T6, T7>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, scala.Tuple7> & Serializable)in -> ((Tuple7)f.apply(in)).toScala(), UnzipWithApply$UnzipWithCreatorObject7$.MODULE$);
    }

    public <In, T1, T2, T3, T4, T5, T6, T7, T8> Graph<FanOutShape8<In, T1, T2, T3, T4, T5, T6, T7, T8>, NotUsed> create8(Function<In, Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, scala.Tuple8> & Serializable)in -> ((Tuple8)f.apply(in)).toScala(), UnzipWithApply$UnzipWithCreatorObject8$.MODULE$);
    }

    public <In, T1, T2, T3, T4, T5, T6, T7, T8, T9> Graph<FanOutShape9<In, T1, T2, T3, T4, T5, T6, T7, T8, T9>, NotUsed> create9(Function<In, Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, scala.Tuple9> & Serializable)in -> ((Tuple9)f.apply(in)).toScala(), UnzipWithApply$UnzipWithCreatorObject9$.MODULE$);
    }

    public <In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Graph<FanOutShape10<In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, NotUsed> create10(Function<In, akka.japi.tuple.Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, Tuple10> & Serializable)in -> ((akka.japi.tuple.Tuple10)f.apply(in)).toScala(), UnzipWithApply$UnzipWithCreatorObject10$.MODULE$);
    }

    public <In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Graph<FanOutShape11<In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>, NotUsed> create11(Function<In, akka.japi.tuple.Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, Tuple11> & Serializable)in -> ((akka.japi.tuple.Tuple11)f.apply(in)).toScala(), UnzipWithApply$UnzipWithCreatorObject11$.MODULE$);
    }

    public <In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Graph<FanOutShape12<In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>, NotUsed> create12(Function<In, akka.japi.tuple.Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, Tuple12> & Serializable)in -> ((akka.japi.tuple.Tuple12)f.apply(in)).toScala(), UnzipWithApply$UnzipWithCreatorObject12$.MODULE$);
    }

    public <In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Graph<FanOutShape13<In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>, NotUsed> create13(Function<In, akka.japi.tuple.Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, Tuple13> & Serializable)in -> ((akka.japi.tuple.Tuple13)f.apply(in)).toScala(), UnzipWithApply$UnzipWithCreatorObject13$.MODULE$);
    }

    public <In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Graph<FanOutShape14<In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>, NotUsed> create14(Function<In, akka.japi.tuple.Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, Tuple14> & Serializable)in -> ((akka.japi.tuple.Tuple14)f.apply(in)).toScala(), UnzipWithApply$UnzipWithCreatorObject14$.MODULE$);
    }

    public <In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Graph<FanOutShape15<In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>, NotUsed> create15(Function<In, akka.japi.tuple.Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, Tuple15> & Serializable)in -> ((akka.japi.tuple.Tuple15)f.apply(in)).toScala(), UnzipWithApply$UnzipWithCreatorObject15$.MODULE$);
    }

    public <In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Graph<FanOutShape16<In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>, NotUsed> create16(Function<In, akka.japi.tuple.Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, Tuple16> & Serializable)in -> ((akka.japi.tuple.Tuple16)f.apply(in)).toScala(), UnzipWithApply$UnzipWithCreatorObject16$.MODULE$);
    }

    public <In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Graph<FanOutShape17<In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>, NotUsed> create17(Function<In, akka.japi.tuple.Tuple17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, Tuple17> & Serializable)in -> ((akka.japi.tuple.Tuple17)f.apply(in)).toScala(), UnzipWithApply$UnzipWithCreatorObject17$.MODULE$);
    }

    public <In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Graph<FanOutShape18<In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>, NotUsed> create18(Function<In, Tuple18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, scala.Tuple18> & Serializable)in -> ((Tuple18)f.apply(in)).toScala(), UnzipWithApply$UnzipWithCreatorObject18$.MODULE$);
    }

    public <In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Graph<FanOutShape19<In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>, NotUsed> create19(Function<In, Tuple19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, scala.Tuple19> & Serializable)in -> ((Tuple19)f.apply(in)).toScala(), UnzipWithApply$UnzipWithCreatorObject19$.MODULE$);
    }

    public <In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Graph<FanOutShape20<In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>, NotUsed> create20(Function<In, Tuple20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, scala.Tuple20> & Serializable)in -> ((Tuple20)f.apply(in)).toScala(), UnzipWithApply$UnzipWithCreatorObject20$.MODULE$);
    }

    public <In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Graph<FanOutShape21<In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>, NotUsed> create21(Function<In, Tuple21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, scala.Tuple21> & Serializable)in -> ((Tuple21)f.apply(in)).toScala(), UnzipWithApply$UnzipWithCreatorObject21$.MODULE$);
    }

    public <In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Graph<FanOutShape22<In, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>, NotUsed> create22(Function<In, Tuple22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> f) {
        return akka.stream.scaladsl.UnzipWith$.MODULE$.apply((Function1<Object, scala.Tuple22> & Serializable)in -> ((Tuple22)f.apply(in)).toScala(), UnzipWithApply$UnzipWithCreatorObject22$.MODULE$);
    }

    private UnzipWith$() {
    }
}

