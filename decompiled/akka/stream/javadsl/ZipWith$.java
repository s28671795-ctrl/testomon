/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.function.Function10;
import akka.japi.function.Function11;
import akka.japi.function.Function12;
import akka.japi.function.Function13;
import akka.japi.function.Function14;
import akka.japi.function.Function15;
import akka.japi.function.Function16;
import akka.japi.function.Function17;
import akka.japi.function.Function18;
import akka.japi.function.Function19;
import akka.japi.function.Function2;
import akka.japi.function.Function20;
import akka.japi.function.Function21;
import akka.japi.function.Function22;
import akka.japi.function.Function3;
import akka.japi.function.Function4;
import akka.japi.function.Function5;
import akka.japi.function.Function6;
import akka.japi.function.Function7;
import akka.japi.function.Function8;
import akka.japi.function.Function9;
import akka.stream.FanInShape10;
import akka.stream.FanInShape11;
import akka.stream.FanInShape12;
import akka.stream.FanInShape13;
import akka.stream.FanInShape14;
import akka.stream.FanInShape15;
import akka.stream.FanInShape16;
import akka.stream.FanInShape17;
import akka.stream.FanInShape18;
import akka.stream.FanInShape19;
import akka.stream.FanInShape2;
import akka.stream.FanInShape20;
import akka.stream.FanInShape21;
import akka.stream.FanInShape22;
import akka.stream.FanInShape3;
import akka.stream.FanInShape4;
import akka.stream.FanInShape5;
import akka.stream.FanInShape6;
import akka.stream.FanInShape7;
import akka.stream.FanInShape8;
import akka.stream.FanInShape9;
import akka.stream.Graph;
import java.io.Serializable;

public final class ZipWith$ {
    public static final ZipWith$ MODULE$ = new ZipWith$();

    public <A, B, Out> Graph<FanInShape2<A, B, Out>, NotUsed> create(Function2<A, B, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function2<Object, Object, Object> & Serializable)(arg1, arg2) -> f.apply(arg1, arg2));
    }

    public <T1, T2, T3, Out> Graph<FanInShape3<T1, T2, T3, Out>, NotUsed> create3(Function3<T1, T2, T3, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function3<Object, Object, Object, Object> & Serializable)(arg1, arg2, arg3) -> f.apply(arg1, arg2, arg3));
    }

    public <T1, T2, T3, T4, Out> Graph<FanInShape4<T1, T2, T3, T4, Out>, NotUsed> create4(Function4<T1, T2, T3, T4, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function4<Object, Object, Object, Object, Object> & Serializable)(arg1, arg2, arg3, arg4) -> f.apply(arg1, arg2, arg3, arg4));
    }

    public <T1, T2, T3, T4, T5, Out> Graph<FanInShape5<T1, T2, T3, T4, T5, Out>, NotUsed> create5(Function5<T1, T2, T3, T4, T5, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function5<Object, Object, Object, Object, Object, Object> & Serializable)(arg1, arg2, arg3, arg4, arg5) -> f.apply(arg1, arg2, arg3, arg4, arg5));
    }

    public <T1, T2, T3, T4, T5, T6, Out> Graph<FanInShape6<T1, T2, T3, T4, T5, T6, Out>, NotUsed> create6(Function6<T1, T2, T3, T4, T5, T6, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function6<Object, Object, Object, Object, Object, Object, Object> & Serializable)(arg1, arg2, arg3, arg4, arg5, arg6) -> f.apply(arg1, arg2, arg3, arg4, arg5, arg6));
    }

    public <T1, T2, T3, T4, T5, T6, T7, Out> Graph<FanInShape7<T1, T2, T3, T4, T5, T6, T7, Out>, NotUsed> create7(Function7<T1, T2, T3, T4, T5, T6, T7, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function7<Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(arg1, arg2, arg3, arg4, arg5, arg6, arg7) -> f.apply(arg1, arg2, arg3, arg4, arg5, arg6, arg7));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, Out> Graph<FanInShape8<T1, T2, T3, T4, T5, T6, T7, T8, Out>, NotUsed> create8(Function8<T1, T2, T3, T4, T5, T6, T7, T8, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function8<Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8) -> f.apply(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, Out> Graph<FanInShape9<T1, T2, T3, T4, T5, T6, T7, T8, T9, Out>, NotUsed> create9(Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function9<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9) -> f.apply(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Out> Graph<FanInShape10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Out>, NotUsed> create10(Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function10<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10) -> f.apply(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, Out> Graph<FanInShape11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, Out>, NotUsed> create11(Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function11<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11) -> f.apply(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, Out> Graph<FanInShape12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, Out>, NotUsed> create12(Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function12<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12) -> f.apply(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, Out> Graph<FanInShape13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, Out>, NotUsed> create13(Function13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function13<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13) -> f.apply(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Out> Graph<FanInShape14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Out>, NotUsed> create14(Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function14<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14) -> f.apply(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, Out> Graph<FanInShape15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, Out>, NotUsed> create15(Function15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function15<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15) -> f.apply(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, Out> Graph<FanInShape16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, Out>, NotUsed> create16(Function16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function16<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16) -> f.apply(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, Out> Graph<FanInShape17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, Out>, NotUsed> create17(Function17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function17<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17) -> f.apply(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, Out> Graph<FanInShape18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, Out>, NotUsed> create18(Function18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function18<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18) -> f.apply(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, Out> Graph<FanInShape19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, Out>, NotUsed> create19(Function19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function19<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19) -> f.apply(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, Out> Graph<FanInShape20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, Out>, NotUsed> create20(Function20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function20<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19, arg20) -> f.apply(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19, arg20));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, Out> Graph<FanInShape21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, Out>, NotUsed> create21(Function21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function21<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19, arg20, arg21) -> f.apply(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19, arg20, arg21));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, Out> Graph<FanInShape22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, Out>, NotUsed> create22(Function22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, Out> f) {
        return akka.stream.scaladsl.ZipWith$.MODULE$.apply((scala.Function22<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19, arg20, arg21, arg22) -> f.apply(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19, arg20, arg21, arg22));
    }

    private ZipWith$() {
    }
}

