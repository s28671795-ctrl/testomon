/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.Graph;
import akka.stream.Shape;
import akka.stream.scaladsl.GenericGraph;
import akka.stream.scaladsl.GraphApply;
import akka.stream.scaladsl.GraphDSL;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function10;
import scala.Function11;
import scala.Function12;
import scala.Function13;
import scala.Function14;
import scala.Function15;
import scala.Function16;
import scala.Function17;
import scala.Function18;
import scala.Function19;
import scala.Function2;
import scala.Function20;
import scala.Function21;
import scala.Function22;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.Function6;
import scala.Function7;
import scala.Function8;
import scala.Function9;
import scala.Predef$;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;

public final class GraphDSL$
implements GraphApply {
    public static final GraphDSL$ MODULE$ = new GraphDSL$();

    static {
        GraphApply.$init$(MODULE$);
    }

    @Override
    public <S extends Shape> Graph<S, NotUsed> create(Function1<GraphDSL.Builder<NotUsed>, S> buildBlock) {
        return GraphApply.create$(this, buildBlock);
    }

    @Override
    public <S extends Shape, Mat> Graph<S, Mat> create(Graph<Shape, Mat> g1, Function1<GraphDSL.Builder<Mat>, Function1<Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, buildBlock);
    }

    @Override
    public <S extends Shape, S1 extends Shape, Mat> Graph<S, Mat> createGraph(Graph<S1, Mat> g1, Function1<GraphDSL.Builder<Mat>, Function1<S1, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Function2<M1, M2, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function2<Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, S1 extends Shape, S2 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Function2<M1, M2, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function2<S1, S2, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Graph<Shape, M3> g3, Function3<M1, M2, M3, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function3<Shape, Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, g3, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, S1 extends Shape, S2 extends Shape, S3 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Function3<M1, M2, M3, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function3<S1, S2, S3, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, g3, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Graph<Shape, M3> g3, Graph<Shape, M4> g4, Function4<M1, M2, M3, M4, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function4<Shape, Shape, Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, g3, g4, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Function4<M1, M2, M3, M4, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function4<S1, S2, S3, S4, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, g3, g4, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Graph<Shape, M3> g3, Graph<Shape, M4> g4, Graph<Shape, M5> g5, Function5<M1, M2, M3, M4, M5, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function5<Shape, Shape, Shape, Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, g3, g4, g5, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Function5<M1, M2, M3, M4, M5, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function5<S1, S2, S3, S4, S5, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, g3, g4, g5, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Graph<Shape, M3> g3, Graph<Shape, M4> g4, Graph<Shape, M5> g5, Graph<Shape, M6> g6, Function6<M1, M2, M3, M4, M5, M6, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function6<Shape, Shape, Shape, Shape, Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, g3, g4, g5, g6, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Function6<M1, M2, M3, M4, M5, M6, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function6<S1, S2, S3, S4, S5, S6, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, g3, g4, g5, g6, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Graph<Shape, M3> g3, Graph<Shape, M4> g4, Graph<Shape, M5> g5, Graph<Shape, M6> g6, Graph<Shape, M7> g7, Function7<M1, M2, M3, M4, M5, M6, M7, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function7<Shape, Shape, Shape, Shape, Shape, Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, g3, g4, g5, g6, g7, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Function7<M1, M2, M3, M4, M5, M6, M7, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function7<S1, S2, S3, S4, S5, S6, S7, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, g3, g4, g5, g6, g7, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Graph<Shape, M3> g3, Graph<Shape, M4> g4, Graph<Shape, M5> g5, Graph<Shape, M6> g6, Graph<Shape, M7> g7, Graph<Shape, M8> g8, Function8<M1, M2, M3, M4, M5, M6, M7, M8, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function8<Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, g3, g4, g5, g6, g7, g8, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Function8<M1, M2, M3, M4, M5, M6, M7, M8, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function8<S1, S2, S3, S4, S5, S6, S7, S8, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, g3, g4, g5, g6, g7, g8, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Graph<Shape, M3> g3, Graph<Shape, M4> g4, Graph<Shape, M5> g5, Graph<Shape, M6> g6, Graph<Shape, M7> g7, Graph<Shape, M8> g8, Graph<Shape, M9> g9, Function9<M1, M2, M3, M4, M5, M6, M7, M8, M9, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function9<Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Function9<M1, M2, M3, M4, M5, M6, M7, M8, M9, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function9<S1, S2, S3, S4, S5, S6, S7, S8, S9, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Graph<Shape, M3> g3, Graph<Shape, M4> g4, Graph<Shape, M5> g5, Graph<Shape, M6> g6, Graph<Shape, M7> g7, Graph<Shape, M8> g8, Graph<Shape, M9> g9, Graph<Shape, M10> g10, Function10<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function10<Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Function10<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function10<S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Graph<Shape, M3> g3, Graph<Shape, M4> g4, Graph<Shape, M5> g5, Graph<Shape, M6> g6, Graph<Shape, M7> g7, Graph<Shape, M8> g8, Graph<Shape, M9> g9, Graph<Shape, M10> g10, Graph<Shape, M11> g11, Function11<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function11<Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Function11<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function11<S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Graph<Shape, M3> g3, Graph<Shape, M4> g4, Graph<Shape, M5> g5, Graph<Shape, M6> g6, Graph<Shape, M7> g7, Graph<Shape, M8> g8, Graph<Shape, M9> g9, Graph<Shape, M10> g10, Graph<Shape, M11> g11, Graph<Shape, M12> g12, Function12<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function12<Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Function12<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function12<S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Graph<Shape, M3> g3, Graph<Shape, M4> g4, Graph<Shape, M5> g5, Graph<Shape, M6> g6, Graph<Shape, M7> g7, Graph<Shape, M8> g8, Graph<Shape, M9> g9, Graph<Shape, M10> g10, Graph<Shape, M11> g11, Graph<Shape, M12> g12, Graph<Shape, M13> g13, Function13<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function13<Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape, S13 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Graph<S13, M13> g13, Function13<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function13<S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Graph<Shape, M3> g3, Graph<Shape, M4> g4, Graph<Shape, M5> g5, Graph<Shape, M6> g6, Graph<Shape, M7> g7, Graph<Shape, M8> g8, Graph<Shape, M9> g9, Graph<Shape, M10> g10, Graph<Shape, M11> g11, Graph<Shape, M12> g12, Graph<Shape, M13> g13, Graph<Shape, M14> g14, Function14<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function14<Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape, S13 extends Shape, S14 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Graph<S13, M13> g13, Graph<S14, M14> g14, Function14<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function14<S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Graph<Shape, M3> g3, Graph<Shape, M4> g4, Graph<Shape, M5> g5, Graph<Shape, M6> g6, Graph<Shape, M7> g7, Graph<Shape, M8> g8, Graph<Shape, M9> g9, Graph<Shape, M10> g10, Graph<Shape, M11> g11, Graph<Shape, M12> g12, Graph<Shape, M13> g13, Graph<Shape, M14> g14, Graph<Shape, M15> g15, Function15<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function15<Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape, S13 extends Shape, S14 extends Shape, S15 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Graph<S13, M13> g13, Graph<S14, M14> g14, Graph<S15, M15> g15, Function15<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function15<S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Graph<Shape, M3> g3, Graph<Shape, M4> g4, Graph<Shape, M5> g5, Graph<Shape, M6> g6, Graph<Shape, M7> g7, Graph<Shape, M8> g8, Graph<Shape, M9> g9, Graph<Shape, M10> g10, Graph<Shape, M11> g11, Graph<Shape, M12> g12, Graph<Shape, M13> g13, Graph<Shape, M14> g14, Graph<Shape, M15> g15, Graph<Shape, M16> g16, Function16<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function16<Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape, S13 extends Shape, S14 extends Shape, S15 extends Shape, S16 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Graph<S13, M13> g13, Graph<S14, M14> g14, Graph<S15, M15> g15, Graph<S16, M16> g16, Function16<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function16<S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15, S16, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Graph<Shape, M3> g3, Graph<Shape, M4> g4, Graph<Shape, M5> g5, Graph<Shape, M6> g6, Graph<Shape, M7> g7, Graph<Shape, M8> g8, Graph<Shape, M9> g9, Graph<Shape, M10> g10, Graph<Shape, M11> g11, Graph<Shape, M12> g12, Graph<Shape, M13> g13, Graph<Shape, M14> g14, Graph<Shape, M15> g15, Graph<Shape, M16> g16, Graph<Shape, M17> g17, Function17<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function17<Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, g17, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape, S13 extends Shape, S14 extends Shape, S15 extends Shape, S16 extends Shape, S17 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Graph<S13, M13> g13, Graph<S14, M14> g14, Graph<S15, M15> g15, Graph<S16, M16> g16, Graph<S17, M17> g17, Function17<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function17<S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15, S16, S17, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, g17, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Graph<Shape, M3> g3, Graph<Shape, M4> g4, Graph<Shape, M5> g5, Graph<Shape, M6> g6, Graph<Shape, M7> g7, Graph<Shape, M8> g8, Graph<Shape, M9> g9, Graph<Shape, M10> g10, Graph<Shape, M11> g11, Graph<Shape, M12> g12, Graph<Shape, M13> g13, Graph<Shape, M14> g14, Graph<Shape, M15> g15, Graph<Shape, M16> g16, Graph<Shape, M17> g17, Graph<Shape, M18> g18, Function18<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function18<Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, g17, g18, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape, S13 extends Shape, S14 extends Shape, S15 extends Shape, S16 extends Shape, S17 extends Shape, S18 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Graph<S13, M13> g13, Graph<S14, M14> g14, Graph<S15, M15> g15, Graph<S16, M16> g16, Graph<S17, M17> g17, Graph<S18, M18> g18, Function18<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function18<S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15, S16, S17, S18, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, g17, g18, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Graph<Shape, M3> g3, Graph<Shape, M4> g4, Graph<Shape, M5> g5, Graph<Shape, M6> g6, Graph<Shape, M7> g7, Graph<Shape, M8> g8, Graph<Shape, M9> g9, Graph<Shape, M10> g10, Graph<Shape, M11> g11, Graph<Shape, M12> g12, Graph<Shape, M13> g13, Graph<Shape, M14> g14, Graph<Shape, M15> g15, Graph<Shape, M16> g16, Graph<Shape, M17> g17, Graph<Shape, M18> g18, Graph<Shape, M19> g19, Function19<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function19<Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, g17, g18, g19, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape, S13 extends Shape, S14 extends Shape, S15 extends Shape, S16 extends Shape, S17 extends Shape, S18 extends Shape, S19 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Graph<S13, M13> g13, Graph<S14, M14> g14, Graph<S15, M15> g15, Graph<S16, M16> g16, Graph<S17, M17> g17, Graph<S18, M18> g18, Graph<S19, M19> g19, Function19<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function19<S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15, S16, S17, S18, S19, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, g17, g18, g19, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, M20> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Graph<Shape, M3> g3, Graph<Shape, M4> g4, Graph<Shape, M5> g5, Graph<Shape, M6> g6, Graph<Shape, M7> g7, Graph<Shape, M8> g8, Graph<Shape, M9> g9, Graph<Shape, M10> g10, Graph<Shape, M11> g11, Graph<Shape, M12> g12, Graph<Shape, M13> g13, Graph<Shape, M14> g14, Graph<Shape, M15> g15, Graph<Shape, M16> g16, Graph<Shape, M17> g17, Graph<Shape, M18> g18, Graph<Shape, M19> g19, Graph<Shape, M20> g20, Function20<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, M20, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function20<Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, g17, g18, g19, g20, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, M20, S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape, S13 extends Shape, S14 extends Shape, S15 extends Shape, S16 extends Shape, S17 extends Shape, S18 extends Shape, S19 extends Shape, S20 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Graph<S13, M13> g13, Graph<S14, M14> g14, Graph<S15, M15> g15, Graph<S16, M16> g16, Graph<S17, M17> g17, Graph<S18, M18> g18, Graph<S19, M19> g19, Graph<S20, M20> g20, Function20<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, M20, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function20<S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15, S16, S17, S18, S19, S20, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, g17, g18, g19, g20, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, M20, M21> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Graph<Shape, M3> g3, Graph<Shape, M4> g4, Graph<Shape, M5> g5, Graph<Shape, M6> g6, Graph<Shape, M7> g7, Graph<Shape, M8> g8, Graph<Shape, M9> g9, Graph<Shape, M10> g10, Graph<Shape, M11> g11, Graph<Shape, M12> g12, Graph<Shape, M13> g13, Graph<Shape, M14> g14, Graph<Shape, M15> g15, Graph<Shape, M16> g16, Graph<Shape, M17> g17, Graph<Shape, M18> g18, Graph<Shape, M19> g19, Graph<Shape, M20> g20, Graph<Shape, M21> g21, Function21<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, M20, M21, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function21<Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, g17, g18, g19, g20, g21, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, M20, M21, S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape, S13 extends Shape, S14 extends Shape, S15 extends Shape, S16 extends Shape, S17 extends Shape, S18 extends Shape, S19 extends Shape, S20 extends Shape, S21 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Graph<S13, M13> g13, Graph<S14, M14> g14, Graph<S15, M15> g15, Graph<S16, M16> g16, Graph<S17, M17> g17, Graph<S18, M18> g18, Graph<S19, M19> g19, Graph<S20, M20> g20, Graph<S21, M21> g21, Function21<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, M20, M21, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function21<S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15, S16, S17, S18, S19, S20, S21, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, g17, g18, g19, g20, g21, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, M20, M21, M22> Graph<S, Mat> create(Graph<Shape, M1> g1, Graph<Shape, M2> g2, Graph<Shape, M3> g3, Graph<Shape, M4> g4, Graph<Shape, M5> g5, Graph<Shape, M6> g6, Graph<Shape, M7> g7, Graph<Shape, M8> g8, Graph<Shape, M9> g9, Graph<Shape, M10> g10, Graph<Shape, M11> g11, Graph<Shape, M12> g12, Graph<Shape, M13> g13, Graph<Shape, M14> g14, Graph<Shape, M15> g15, Graph<Shape, M16> g16, Graph<Shape, M17> g17, Graph<Shape, M18> g18, Graph<Shape, M19> g19, Graph<Shape, M20> g20, Graph<Shape, M21> g21, Graph<Shape, M22> g22, Function22<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, M20, M21, M22, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function22<Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, Shape, S>> buildBlock) {
        return GraphApply.create$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, g17, g18, g19, g20, g21, g22, combineMat, buildBlock);
    }

    @Override
    public <S extends Shape, Mat, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, M20, M21, M22, S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape, S13 extends Shape, S14 extends Shape, S15 extends Shape, S16 extends Shape, S17 extends Shape, S18 extends Shape, S19 extends Shape, S20 extends Shape, S21 extends Shape, S22 extends Shape> Graph<S, Mat> createGraph(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Graph<S13, M13> g13, Graph<S14, M14> g14, Graph<S15, M15> g15, Graph<S16, M16> g16, Graph<S17, M17> g17, Graph<S18, M18> g18, Graph<S19, M19> g19, Graph<S20, M20> g20, Graph<S21, M21> g21, Graph<S22, M22> g22, Function22<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, M20, M21, M22, Mat> combineMat, Function1<GraphDSL.Builder<Mat>, Function22<S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15, S16, S17, S18, S19, S20, S21, S22, S>> buildBlock) {
        return GraphApply.createGraph$(this, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, g17, g18, g19, g20, g21, g22, combineMat, buildBlock);
    }

    public <S extends Shape, IS extends Shape, Mat> Graph<S, Seq<Mat>> create(Seq<Graph<IS, Mat>> graphs, Function1<GraphDSL.Builder<Seq<Mat>>, Function1<Seq<IS>, S>> buildBlock) {
        Predef$.MODULE$.require(graphs.nonEmpty(), (Function0<Object>)(Function0<String> & Serializable)() -> "The input list must have one or more Graph elements");
        GraphDSL.Builder builder = new GraphDSL.Builder();
        Function1<Object, Seq> & Serializable toList = (Function1<Object, Seq> & Serializable)m1 -> (Seq)package$.MODULE$.Seq().apply((Seq)ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{m1}));
        Function2<Seq, Object, Seq> & Serializable combine = (Function2<Seq, Object, Seq> & Serializable)(s2, m22) -> (Seq)s2.$colon$plus(m22);
        Object sListH = builder.add((Graph)graphs.head(), toList);
        Seq sListT = (Seq)((IterableOps)graphs.tail()).map((Function1<Graph, Shape> & Serializable)g2 -> builder.add(g2, combine));
        Shape s3 = (Shape)buildBlock.apply(builder).apply((Seq<IS>)new $colon$colon<Nothing$>((Nothing$)sListH, Nil$.MODULE$).$plus$plus((IterableOnce)sListT));
        return new GenericGraph(s3, builder.result(s3));
    }

    private GraphDSL$() {
    }
}

