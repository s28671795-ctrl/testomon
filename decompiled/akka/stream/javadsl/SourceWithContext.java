/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.actor.ClassicActorSystemProvider;
import akka.annotation.ApiMayChange;
import akka.event.LogMarker;
import akka.event.LoggingAdapter;
import akka.event.MarkerLoggingAdapter;
import akka.japi.Pair;
import akka.japi.Util$;
import akka.japi.function.Function;
import akka.japi.function.Function2;
import akka.japi.function.Predicate;
import akka.stream.Attributes;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.GraphDelegate;
import akka.stream.Materializer;
import akka.stream.SinkShape;
import akka.stream.SourceShape;
import akka.stream.ThrottleMode;
import akka.stream.javadsl.Keep$;
import akka.stream.javadsl.RunnableGraph;
import akka.stream.javadsl.RunnableGraph$;
import akka.stream.javadsl.Source;
import akka.stream.javadsl.SourceWithContext$;
import akka.stream.javadsl.package$;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Flow$;
import akka.util.ConstantFun$;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import akka.util.ccompat.package$JavaConverters$;
import java.io.Serializable;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;
import scala.Function1;
import scala.MatchError;
import scala.PartialFunction;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.immutable.Seq;
import scala.compat.java8.FutureConverters$;
import scala.compat.java8.FutureConverters$CompletionStageOps$;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\r-x!\u0002\u0016,\u0011\u0003\u0011d!\u0002\u001b,\u0011\u0003)\u0004\"\u0002\u001f\u0002\t\u0003i\u0004\"\u0002 \u0002\t\u0003yd\u0001\u0002\u001b,\u0005\tC\u0001b\u0018\u0003\u0003\u0002\u0003\u0006I\u0001\u0019\u0005\u0006y\u0011!\t!\u001a\u0005\u0006Q\u0012!\t!\u001b\u0005\b\u0003?!A\u0011AA\u0011\u0011\u001d\t\t\u0005\u0002C!\u0003\u0007Bq!a\u0014\u0005\t\u0003\t\t\u0006C\u0004\u0002v\u0011!\t!a\u001e\t\u000f\u0005EE\u0001\"\u0001\u0002\u0014\"9\u0011Q\u0014\u0003\u0005\u0002\u0005}\u0005bBAW\t\u0011\u0005\u0011q\u0016\u0005\b\u0003w#A\u0011AA_\u0011\u001d\t\t\r\u0002C\u0001\u0003\u0007Dq!a9\u0005\t\u0003\t)\u000fC\u0004\u0002t\u0012!\t!!>\t\u000f\tMA\u0001\"\u0001\u0003\u0016!9!q\b\u0003\u0005\u0002\t\u0005\u0003b\u0002B3\t\u0011\u0005!q\r\u0005\b\u0005o\"A\u0011\u0001B=\u0011%\u0011\t\tBI\u0001\n\u0003\u0011\u0019\tC\u0004\u0003\b\u0012!\tA!#\t\u000f\t\u001dE\u0001\"\u0001\u00034\"9!q\u0011\u0003\u0005\u0002\te\u0006b\u0002BD\t\u0011\u0005!q\u0018\u0005\b\u0005\u0007$A\u0011\u0001Bc\u0011\u001d\u0011\u0019\r\u0002C\u0001\u0005GDqAa1\u0005\t\u0003\u0011Y\u000fC\u0004\u0003D\u0012!\tAa=\t\u000f\teH\u0001\"\u0001\u0003|\"9!\u0011 \u0003\u0005\u0002\rE\u0001b\u0002B}\t\u0011\u00051Q\u0005\u0005\b\u0005s$A\u0011AB\u001d\u0011\u001d\u0019)\u0005\u0002C\u0001\u0007\u000fBqa!\u0019\u0005\t\u0003\u0019\u0019\u0007C\u0004\u0004~\u0011!\taa \t\u000f\ruD\u0001\"\u0001\u0004\u001e\"911\u0017\u0003\u0005\u0002\rU\u0006\u0002CB\\\t\u0001&Ia!/\u0002#M{WO]2f/&$\bnQ8oi\u0016DHO\u0003\u0002-[\u00059!.\u0019<bINd'B\u0001\u00180\u0003\u0019\u0019HO]3b[*\t\u0001'\u0001\u0003bW.\f7\u0001\u0001\t\u0003g\u0005i\u0011a\u000b\u0002\u0012'>,(oY3XSRD7i\u001c8uKb$8CA\u00017!\t9$(D\u00019\u0015\u0005I\u0014!B:dC2\f\u0017BA\u001e9\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012AM\u0001\nMJ|W\u000eU1jeN,r\u0001QBl\u00077\u001c\t\u000fF\u0002B\u0007G\u0004\u0002b\r\u0003\u0004V\u000ee7q\\\u000b\u0005\u0007BSVl\u0005\u0002\u0005\tB!QI\u0012%]\u001b\u0005i\u0013BA$.\u000559%/\u00199i\t\u0016dWmZ1uKB\u0019Q)S&\n\u0005)k#aC*pkJ\u001cWm\u00155ba\u0016\u0004Ba\u000e'O3&\u0011Q\n\u000f\u0002\u0007)V\u0004H.\u001a\u001a\u0011\u0005=\u0003F\u0002\u0001\u0003\u0006#\u0012\u0011\rA\u0015\u0002\u0004\u001fV$\u0018CA*W!\t9D+\u0003\u0002Vq\t9aj\u001c;iS:<\u0007CA\u001cX\u0013\tA\u0006HA\u0002B]f\u0004\"a\u0014.\u0005\u000bm#!\u0019\u0001*\u0003\u0007\r#\b\u0010\u0005\u0002P;\u00121a\f\u0002CC\u0002I\u00131!T1u\u0003!!W\r\\3hCR,\u0007#B1e\u001dfcV\"\u00012\u000b\u0005\rl\u0013\u0001C:dC2\fGm\u001d7\n\u0005Q\u0012GC\u00014h!\u0015\u0019DAT-]\u0011\u0015yf\u00011\u0001a\u0003\r1\u0018.Y\u000b\u0006U6\u0004\u00181\u0004\u000b\u0003WJ\u0004Ra\r\u0003m_r\u0003\"aT7\u0005\u000b9<!\u0019\u0001*\u0003\t=+HO\r\t\u0003\u001fB$Q!]\u0004C\u0002I\u0013Aa\u0011;ye!)1o\u0002a\u0001i\u00069a/[1GY><\b#B#vo\u0006e\u0011B\u0001<.\u0005\u00159%/\u00199i!\u0015)\u0005P_A\f\u0013\tIXFA\u0005GY><8\u000b[1qKB11P`A\u0001\u0003+i\u0011\u0001 \u0006\u0003{>\nAA[1qS&\u0011q\u0010 \u0002\u0005!\u0006L'OK\u0002O\u0003\u0007Y#!!\u0002\u0011\t\u0005\u001d\u0011\u0011C\u0007\u0003\u0003\u0013QA!a\u0003\u0002\u000e\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003\u001fA\u0014AC1o]>$\u0018\r^5p]&!\u00111CA\u0005\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0016\u00043\u0006\r\u0001\u0003B>\u007fY>\u00042aTA\u000e\t\u0019\tib\u0002b\u0001%\n!Q*\u0019;3\u00035)hn]1gK\u0012\u000bG/\u0019,jCV1\u00111EA\u0015\u0003g!B!!\n\u0002,A11\u0007BA\u00143r\u00032aTA\u0015\t\u0015q\u0007B1\u0001S\u0011\u0019\u0019\b\u00021\u0001\u0002.A1Q)^A\u0018\u0003c\u0001b!\u0012=\u0002\u0002\u0005\u001d\u0002cA(\u00024\u00111\u0011Q\u0004\u0005C\u0002IC3\u0001CA\u001c!\u0011\tI$!\u0010\u000e\u0005\u0005m\"bAA\b_%!\u0011qHA\u001e\u00051\t\u0005/['bs\u000eC\u0017M\\4f\u000399\u0018\u000e\u001e5BiR\u0014\u0018NY;uKN$2AZA#\u0011\u001d\t9%\u0003a\u0001\u0003\u0013\nA!\u0019;ueB\u0019Q)a\u0013\n\u0007\u00055SF\u0001\u0006BiR\u0014\u0018NY;uKN\f\u0001\"\\1q\u000bJ\u0014xN\u001d\u000b\u0004M\u0006M\u0003bBA+\u0015\u0001\u0007\u0011qK\u0001\u0003a\u001a\u0004raNA-\u0003;\ni&C\u0002\u0002\\a\u0012q\u0002U1si&\fGNR;oGRLwN\u001c\t\u0005\u0003?\nyG\u0004\u0003\u0002b\u0005-d\u0002BA2\u0003Sj!!!\u001a\u000b\u0007\u0005\u001d\u0014'\u0001\u0004=e>|GOP\u0005\u0002s%\u0019\u0011Q\u000e\u001d\u0002\u000fA\f7m[1hK&!\u0011\u0011OA:\u0005%!\u0006N]8xC\ndWMC\u0002\u0002na\nA#\\1q\u001b\u0006$XM]5bY&TX\r\u001a,bYV,W\u0003BA=\u0003\u007f\"B!a\u001f\u0002\u0002B11\u0007\u0002(Z\u0003{\u00022aTA@\t\u0019\tib\u0003b\u0001%\"9\u00111Q\u0006A\u0002\u0005\u0015\u0015!\u00014\u0011\u000f\u0005\u001d\u0015Q\u0012/\u0002~5\u0011\u0011\u0011\u0012\u0006\u0004\u0003\u0017c\u0018\u0001\u00034v]\u000e$\u0018n\u001c8\n\t\u0005=\u0015\u0011\u0012\u0002\t\rVt7\r^5p]\u0006A\u0011m]*pkJ\u001cW\r\u0006\u0002\u0002\u0016B11'a&{\u00037K1!!',\u0005\u0019\u0019v.\u001e:dK*\u001aA,a\u0001\u0002\u000f\r|G\u000e\\3diV!\u0011\u0011UAT)\u0011\t\u0019+!+\u0011\rM\"\u0011QU-]!\ry\u0015q\u0015\u0003\u0006]6\u0011\rA\u0015\u0005\b\u0003+j\u0001\u0019AAV!\u00199\u0014\u0011\f(\u0002&\u00061a-\u001b7uKJ$2AZAY\u0011\u001d\t\u0019L\u0004a\u0001\u0003k\u000b\u0011\u0001\u001d\t\u0006\u0003\u000f\u000b9LT\u0005\u0005\u0003s\u000bIIA\u0005Qe\u0016$\u0017nY1uK\u0006Ia-\u001b7uKJtu\u000e\u001e\u000b\u0004M\u0006}\u0006bBAZ\u001f\u0001\u0007\u0011QW\u0001\bOJ|W\u000f]3e)\u0011\t)-!7\u0011\u000fM\"\u0011qYAl9B1\u0011\u0011ZAj\u0003\u0003i!!a3\u000b\t\u00055\u0017qZ\u0001\u0005kRLGN\u0003\u0002\u0002R\u0006!!.\u0019<b\u0013\u0011\t).a3\u0003\t1K7\u000f\u001e\t\u0007\u0003\u0013\f\u0019.!\u0006\t\u000f\u0005m\u0007\u00031\u0001\u0002^\u0006\ta\u000eE\u00028\u0003?L1!!99\u0005\rIe\u000e^\u0001\u0004[\u0006\u0004X\u0003BAt\u0003[$B!!;\u0002pB11\u0007BAv3r\u00032aTAw\t\u0015q\u0017C1\u0001S\u0011\u001d\t\u0019)\u0005a\u0001\u0003c\u0004r!a\"\u0002\u000e:\u000bY/\u0001\u0005nCB\f5/\u001f8d+\u0011\t90!@\u0015\r\u0005e\u0018q B\u0002!\u0019\u0019D!a?Z9B\u0019q*!@\u0005\u000b9\u0014\"\u0019\u0001*\t\u000f\t\u0005!\u00031\u0001\u0002^\u0006Y\u0001/\u0019:bY2,G.[:n\u0011\u001d\t\u0019I\u0005a\u0001\u0005\u000b\u0001r!a\"\u0002\u000e:\u00139\u0001\u0005\u0004\u0003\n\t=\u00111`\u0007\u0003\u0005\u0017QAA!\u0004\u0002L\u0006Q1m\u001c8dkJ\u0014XM\u001c;\n\t\tE!1\u0002\u0002\u0010\u0007>l\u0007\u000f\\3uS>t7\u000b^1hK\u0006\u0019R.\u00199Bgft7\rU1si&$\u0018n\u001c8fIV1!q\u0003B\u000f\u0005[!\"B!\u0007\u0003 \t\u0005\"Q\u0005B\u0019!\u0019\u0019DAa\u0007Z9B\u0019qJ!\b\u0005\u000b9\u001c\"\u0019\u0001*\t\u000f\t\u00051\u00031\u0001\u0002^\"9!1E\nA\u0002\u0005u\u0017\u0001\u00049feB\u000b'\u000f^5uS>t\u0007b\u0002B\u0014'\u0001\u0007!\u0011F\u0001\fa\u0006\u0014H/\u001b;j_:,'\u000fE\u0004\u0002\b\u00065eJa\u000b\u0011\u0007=\u0013i\u0003\u0002\u0004\u00030M\u0011\rA\u0015\u0002\u0002!\"9\u00111Q\nA\u0002\tM\u0002#\u0003B\u001b\u0005sq%1\u0006B\u001f\u001b\t\u00119D\u0003\u0003\u0002\f\u0006-\u0017\u0002\u0002B\u001e\u0005o\u0011!BQ5Gk:\u001cG/[8o!\u0019\u0011IAa\u0004\u0003\u001c\u0005IQ.\u00199D_:\u001c\u0017\r^\u000b\u0005\u0005\u0007\u0012I\u0005\u0006\u0003\u0003F\t-\u0003CB\u001a\u0005\u0005\u000fJF\fE\u0002P\u0005\u0013\"QA\u001c\u000bC\u0002ICq!a!\u0015\u0001\u0004\u0011i\u0005\r\u0003\u0003P\tM\u0003cBAD\u0003\u001bs%\u0011\u000b\t\u0004\u001f\nMC\u0001\u0004B+\u0005\u0017\n\t\u0011!A\u0003\u0002\t]#aA0%cE\u00191K!\u0017\u0011\r\tm#\u0011\rB$\u001b\t\u0011iF\u0003\u0003\u0003`\u0005=\u0017\u0001\u00027b]\u001eLAAa\u0019\u0003^\tA\u0011\n^3sC\ndW-\u0001\u0006nCB\u001cuN\u001c;fqR,BA!\u001b\u0003pQ!!1\u000eB9!\u0019\u0019DA\u0014B79B\u0019qJa\u001c\u0005\u000bE,\"\u0019\u0001*\t\u000f\tMT\u00031\u0001\u0003v\u0005qQ\r\u001f;sC\u000e$8i\u001c8uKb$\bcBAD\u0003\u001bK&QN\u0001\bg2LG-\u001b8h)\u0019\t)Ma\u001f\u0003~!9\u00111\u001c\fA\u0002\u0005u\u0007\"\u0003B@-A\u0005\t\u0019AAo\u0003\u0011\u0019H/\u001a9\u0002#Md\u0017\u000eZ5oO\u0012\"WMZ1vYR$#'\u0006\u0002\u0003\u0006*\"\u0011Q\\A\u0002\u0003\rawn\u001a\u000b\bM\n-%q\u0014BS\u0011\u001d\u0011i\t\u0007a\u0001\u0005\u001f\u000bAA\\1nKB!!\u0011\u0013BM\u001d\u0011\u0011\u0019J!&\u0011\u0007\u0005\r\u0004(C\u0002\u0003\u0018b\na\u0001\u0015:fI\u00164\u0017\u0002\u0002BN\u0005;\u0013aa\u0015;sS:<'b\u0001BLq!9!\u0011\u0015\rA\u0002\t\r\u0016aB3yiJ\f7\r\u001e\t\u0007\u0003\u000f\u000biI\u0014,\t\u000f\t\u001d\u0005\u00041\u0001\u0003(B!!\u0011\u0016BX\u001b\t\u0011YKC\u0002\u0003.>\nQ!\u001a<f]RLAA!-\u0003,\nqAj\\4hS:<\u0017\tZ1qi\u0016\u0014H#\u00024\u00036\n]\u0006b\u0002BG3\u0001\u0007!q\u0012\u0005\b\u0005CK\u0002\u0019\u0001BR)\u00151'1\u0018B_\u0011\u001d\u0011iI\u0007a\u0001\u0005\u001fCqAa\"\u001b\u0001\u0004\u00119\u000bF\u0002g\u0005\u0003DqA!$\u001c\u0001\u0004\u0011y)A\u0007m_\u001e<\u0016\u000e\u001e5NCJ\\WM\u001d\u000b\nM\n\u001d'\u0011\u001aBm\u00057DqA!$\u001d\u0001\u0004\u0011y\tC\u0004\u0003Lr\u0001\rA!4\u0002\r5\f'o[3s!!\t9Ia4O3\nM\u0017\u0002\u0002Bi\u0003\u0013\u0013\u0011BR;oGRLwN\u001c\u001a\u0011\t\t%&Q[\u0005\u0005\u0005/\u0014YKA\u0005M_\u001el\u0015M]6fe\"9!\u0011\u0015\u000fA\u0002\t\r\u0006b\u0002BD9\u0001\u0007!Q\u001c\t\u0005\u0005S\u0013y.\u0003\u0003\u0003b\n-&\u0001F'be.,'\u000fT8hO&tw-\u00113baR,'\u000fF\u0004g\u0005K\u00149O!;\t\u000f\t5U\u00041\u0001\u0003\u0010\"9!1Z\u000fA\u0002\t5\u0007b\u0002BQ;\u0001\u0007!1\u0015\u000b\bM\n5(q\u001eBy\u0011\u001d\u0011iI\ba\u0001\u0005\u001fCqAa3\u001f\u0001\u0004\u0011i\rC\u0004\u0003\bz\u0001\rA!8\u0015\u000b\u0019\u0014)Pa>\t\u000f\t5u\u00041\u0001\u0003\u0010\"9!1Z\u0010A\u0002\t5\u0017\u0001\u0003;ie>$H\u000f\\3\u0015\u000b\u0019\u0014ip!\u0001\t\u000f\t}\b\u00051\u0001\u0002^\u0006AQ\r\\3nK:$8\u000fC\u0004\u0004\u0004\u0001\u0002\ra!\u0002\u0002\u0007A,'\u000f\u0005\u0003\u0004\b\r5QBAB\u0005\u0015\u0011\u0019Y!a4\u0002\tQLW.Z\u0005\u0005\u0007\u001f\u0019IA\u0001\u0005EkJ\fG/[8o)%171CB\u000b\u0007/\u0019Y\u0002C\u0004\u0003\u0000\u0006\u0002\r!!8\t\u000f\r\r\u0011\u00051\u0001\u0004\u0006!91\u0011D\u0011A\u0002\u0005u\u0017\u0001D7bq&lW/\u001c\"veN$\bbBB\u000fC\u0001\u00071qD\u0001\u0005[>$W\rE\u0002F\u0007CI1aa\t.\u00051!\u0006N]8ui2,Wj\u001c3f)\u001d17qEB\u0016\u0007[Aqa!\u000b#\u0001\u0004\ti.\u0001\u0003d_N$\bbBB\u0002E\u0001\u00071Q\u0001\u0005\b\u0007_\u0011\u0003\u0019AB\u0019\u0003=\u0019wn\u001d;DC2\u001cW\u000f\\1uS>t\u0007cBAD\u0003\u001bs51\u0007\t\u0005\u00057\u001a)$\u0003\u0003\u00048\tu#aB%oi\u0016<WM\u001d\u000b\fM\u000em2QHB \u0007\u0003\u001a\u0019\u0005C\u0004\u0004*\r\u0002\r!!8\t\u000f\r\r1\u00051\u0001\u0004\u0006!91\u0011D\u0012A\u0002\u0005u\u0007bBB\u0018G\u0001\u00071\u0011\u0007\u0005\b\u0007;\u0019\u0003\u0019AB\u0010\u0003\t!x.\u0006\u0003\u0004J\r}C\u0003BB&\u0007#\u0002BaMB'9&\u00191qJ\u0016\u0003\u001bI+hN\\1cY\u0016<%/\u00199i\u0011\u001d\u0019\u0019\u0006\na\u0001\u0007+\nAa]5oWB1Q)^B,\u0007;\u0002B!RB-u&\u001911L\u0017\u0003\u0013MKgn[*iCB,\u0007cA(\u0004`\u00111\u0011Q\u0004\u0013C\u0002I\u000bQ\u0001^8NCR,ba!\u001a\u0004v\r-DCBB4\u0007_\u001a9\bE\u00034\u0007\u001b\u001aI\u0007E\u0002P\u0007W\"aa!\u001c&\u0005\u0004\u0011&\u0001B'biNBqaa\u0015&\u0001\u0004\u0019\t\b\u0005\u0004Fk\u000e]31\u000f\t\u0004\u001f\u000eUDABA\u000fK\t\u0007!\u000bC\u0004\u0004z\u0015\u0002\raa\u001f\u0002\u000f\r|WNY5oKBI\u0011q\u0011Bh9\u000eM4\u0011N\u0001\beVtw+\u001b;i+\u0011\u0019\ti!\"\u0015\r\r\r5\u0011RBG!\ry5Q\u0011\u0003\u0007\u0007\u000f3#\u0019\u0001*\u0003\u00035Cqaa\u0015'\u0001\u0004\u0019Y\t\u0005\u0004Fk\u000e]31\u0011\u0005\b\u0007\u001f3\u0003\u0019ABI\u00039\u0019\u0018p\u001d;f[B\u0013xN^5eKJ\u0004Baa%\u0004\u001a6\u00111Q\u0013\u0006\u0004\u0007/{\u0013!B1di>\u0014\u0018\u0002BBN\u0007+\u0013!d\u00117bgNL7-Q2u_J\u001c\u0016p\u001d;f[B\u0013xN^5eKJ,Baa(\u0004$R11\u0011UBS\u0007S\u00032aTBR\t\u0019\u00199i\nb\u0001%\"911K\u0014A\u0002\r\u001d\u0006CB#v\u0007/\u001a\t\u000bC\u0004\u0004,\u001e\u0002\ra!,\u0002\u00195\fG/\u001a:jC2L'0\u001a:\u0011\u0007\u0015\u001by+C\u0002\u000426\u0012A\"T1uKJL\u0017\r\\5{KJ\fq!Y:TG\u0006d\u0017-F\u0001a\u0003!1\u0018.Y*dC2\fW\u0003CB^\u0007\u0003\u001c)m!3\u0015\t\ru61\u001a\t\tg\u0011\u0019yla1\u0004HB\u0019qj!1\u0005\u000b9L#\u0019\u0001*\u0011\u0007=\u001b)\rB\u0003rS\t\u0007!\u000bE\u0002P\u0007\u0013$a!!\b*\u0005\u0004\u0011\u0006bBABS\u0001\u00071Q\u001a\t\u0007o\r=\u0007ma5\n\u0007\rE\u0007HA\u0005Gk:\u001cG/[8ocAA\u0011\rZB`\u0007\u0007\u001c9\rE\u0002P\u0007/$Q!U\u0002C\u0002I\u00032aTBn\t\u0019\u0019in\u0001b\u0001%\n11\t\u001e=PkR\u00042aTBq\t\u0015q6A1\u0001S\u0011\u001d\u0019)o\u0001a\u0001\u0007O\fQ!\u001e8eKJ\u0004raMAL\u0007S\u001cy\u000e\u0005\u0004|}\u000eU7\u0011\u001c")
public final class SourceWithContext<Out, Ctx, Mat>
extends GraphDelegate<SourceShape<Tuple2<Out, Ctx>>, Mat> {
    private final akka.stream.scaladsl.SourceWithContext<Out, Ctx, Mat> delegate;

    public static <Out, CtxOut, Mat> SourceWithContext<Out, CtxOut, Mat> fromPairs(Source<Pair<Out, CtxOut>, Mat> under) {
        return SourceWithContext$.MODULE$.fromPairs(under);
    }

    public <Out2, Ctx2, Mat2> SourceWithContext<Out2, Ctx2, Mat> via(Graph<FlowShape<Pair<Out, Ctx>, Pair<Out2, Ctx2>>, Mat2> viaFlow) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$2 -> x$2.via((Graph)((Object)((Flow)((Flow)Flow$.MODULE$.apply().map((Function1<Tuple2, Pair> & Serializable)(Out x0$1) -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null) {
                Object o = tuple2._1();
                Object c = tuple2._2();
                return new Pair(o, c);
            }
            throw new MatchError(tuple2);
        })).via(viaFlow)).map((Function1<Pair, Tuple2> & Serializable)(Out x$3) -> x$3.toScala()))));
    }

    @ApiMayChange
    public <Out2, Mat2> SourceWithContext<Out2, Ctx, Mat> unsafeDataVia(Graph<FlowShape<Out, Out2>, Mat2> viaFlow) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$4 -> x$4.unsafeDataVia(viaFlow));
    }

    public SourceWithContext<Out, Ctx, Mat> withAttributes(Attributes attr) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$5 -> x$5.withAttributes(attr));
    }

    public SourceWithContext<Out, Ctx, Mat> mapError(PartialFunction<Throwable, Throwable> pf) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$6 -> (akka.stream.scaladsl.SourceWithContext)x$6.mapError(pf));
    }

    public <Mat2> SourceWithContext<Out, Ctx, Mat2> mapMaterializedValue(Function<Mat, Mat2> f) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$7 -> x$7.mapMaterializedValue((Function1<Object, Object> & Serializable)(Mat param2) -> f.apply(param2)));
    }

    public Source<Pair<Out, Ctx>, Mat> asSource() {
        return ((akka.stream.scaladsl.Source)this.delegate.asSource().map((Function1<Tuple2, Pair> & Serializable)(Out x0$1) -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null) {
                Object o = tuple2._1();
                Object c = tuple2._2();
                return new Pair(o, c);
            }
            throw new MatchError(tuple2);
        })).asJava();
    }

    public <Out2> SourceWithContext<Out2, Ctx, Mat> collect(PartialFunction<Out, Out2> pf) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$8 -> (akka.stream.scaladsl.SourceWithContext)x$8.collect(pf));
    }

    public SourceWithContext<Out, Ctx, Mat> filter(Predicate<Out> p) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$9 -> (akka.stream.scaladsl.SourceWithContext)x$9.filter((Function1<Object, Object> & Serializable)(Out param2) -> BoxesRunTime.boxToBoolean(p.test(param2))));
    }

    public SourceWithContext<Out, Ctx, Mat> filterNot(Predicate<Out> p) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$10 -> (akka.stream.scaladsl.SourceWithContext)x$10.filterNot((Function1<Object, Object> & Serializable)(Out param2) -> BoxesRunTime.boxToBoolean(p.test(param2))));
    }

    public SourceWithContext<List<Out>, List<Ctx>, Mat> grouped(int n) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$11 -> (akka.stream.scaladsl.SourceWithContext)x$11.grouped(n).map((Function1<Seq, List> & Serializable)(Out x$12) -> package$JavaConverters$.MODULE$.SeqHasAsJava(x$12).asJava()).mapContext((Function1<Seq, List> & Serializable)(Ctx x$13) -> package$JavaConverters$.MODULE$.SeqHasAsJava(x$13).asJava()));
    }

    public <Out2> SourceWithContext<Out2, Ctx, Mat> map(Function<Out, Out2> f) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$14 -> (akka.stream.scaladsl.SourceWithContext)x$14.map((Function1<Object, Object> & Serializable)(Out param2) -> f.apply(param2)));
    }

    public <Out2> SourceWithContext<Out2, Ctx, Mat> mapAsync(int parallelism, Function<Out, CompletionStage<Out2>> f) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$15 -> (akka.stream.scaladsl.SourceWithContext)x$15.mapAsync(parallelism, (Function1<Object, Future> & Serializable)(Out o) -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)f.apply(o)))));
    }

    public <Out2, P> SourceWithContext<Out2, Ctx, Mat> mapAsyncPartitioned(int parallelism, int perPartition, Function<Out, P> partitioner, BiFunction<Out, P, CompletionStage<Out2>> f) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$16 -> (akka.stream.scaladsl.SourceWithContext)x$16.mapAsyncPartitioned(parallelism, perPartition, (Function1<Object, Object> & Serializable)(Out x) -> partitioner.apply(x), (scala.Function2<Object, Object, Future> & Serializable)(Out x, P p) -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)f.apply(x, p)))));
    }

    public <Out2> SourceWithContext<Out2, Ctx, Mat> mapConcat(Function<Out, ? extends Iterable<Out2>> f) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$17 -> (akka.stream.scaladsl.SourceWithContext)x$17.mapConcat((Function1<Object, Seq> & Serializable)(Out elem) -> Util$.MODULE$.immutableSeq((Iterable)f.apply(elem))));
    }

    public <Ctx2> SourceWithContext<Out, Ctx2, Mat> mapContext(Function<Ctx, Ctx2> extractContext) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$18 -> (akka.stream.scaladsl.SourceWithContext)x$18.mapContext((Function1<Object, Object> & Serializable)(Ctx param2) -> extractContext.apply(param2)));
    }

    public SourceWithContext<List<Out>, List<Ctx>, Mat> sliding(int n, int step) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$19 -> (akka.stream.scaladsl.SourceWithContext)x$19.sliding(n, step).map((Function1<Seq, List> & Serializable)(Out x$20) -> package$JavaConverters$.MODULE$.SeqHasAsJava(x$20).asJava()).mapContext((Function1<Seq, List> & Serializable)(Ctx x$21) -> package$JavaConverters$.MODULE$.SeqHasAsJava(x$21).asJava()));
    }

    public int sliding$default$2() {
        return 1;
    }

    public SourceWithContext<Out, Ctx, Mat> log(String name, Function<Out, Object> extract, LoggingAdapter log) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$22 -> (akka.stream.scaladsl.SourceWithContext)x$22.log(name, (Function1<Object, Object> & Serializable)(Out e) -> extract.apply(e), log));
    }

    public SourceWithContext<Out, Ctx, Mat> log(String name, Function<Out, Object> extract) {
        return this.log(name, extract, null);
    }

    public SourceWithContext<Out, Ctx, Mat> log(String name, LoggingAdapter log) {
        return this.log(name, ConstantFun$.MODULE$.javaIdentityFunction(), log);
    }

    public SourceWithContext<Out, Ctx, Mat> log(String name) {
        return this.log(name, ConstantFun$.MODULE$.javaIdentityFunction(), null);
    }

    public SourceWithContext<Out, Ctx, Mat> logWithMarker(String name, Function2<Out, Ctx, LogMarker> marker, Function<Out, Object> extract, MarkerLoggingAdapter log) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$23 -> (akka.stream.scaladsl.SourceWithContext)x$23.logWithMarker(name, (scala.Function2<Object, Object, LogMarker> & Serializable)(Out e, Ctx c) -> (LogMarker)marker.apply(e, c), (Function1<Object, Object> & Serializable)(Out e) -> extract.apply(e), log));
    }

    public SourceWithContext<Out, Ctx, Mat> logWithMarker(String name, Function2<Out, Ctx, LogMarker> marker, Function<Out, Object> extract) {
        return this.logWithMarker(name, marker, extract, null);
    }

    public SourceWithContext<Out, Ctx, Mat> logWithMarker(String name, Function2<Out, Ctx, LogMarker> marker, MarkerLoggingAdapter log) {
        return this.logWithMarker(name, marker, ConstantFun$.MODULE$.javaIdentityFunction(), log);
    }

    public SourceWithContext<Out, Ctx, Mat> logWithMarker(String name, Function2<Out, Ctx, LogMarker> marker) {
        return this.logWithMarker(name, marker, ConstantFun$.MODULE$.javaIdentityFunction(), null);
    }

    public SourceWithContext<Out, Ctx, Mat> throttle(int elements, Duration per) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$24 -> (akka.stream.scaladsl.SourceWithContext)x$24.throttle(elements, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(per))));
    }

    public SourceWithContext<Out, Ctx, Mat> throttle(int elements, Duration per, int maximumBurst, ThrottleMode mode) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$25 -> (akka.stream.scaladsl.SourceWithContext)x$25.throttle(elements, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(per)), maximumBurst, mode));
    }

    public SourceWithContext<Out, Ctx, Mat> throttle(int cost, Duration per, Function<Out, Integer> costCalculation) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$26 -> (akka.stream.scaladsl.SourceWithContext)x$26.throttle(cost, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(per)), (Function1<Object, Object> & Serializable)(Out param2) -> BoxesRunTime.boxToInteger(Predef$.MODULE$.Integer2int((Integer)costCalculation.apply(param2)))));
    }

    public SourceWithContext<Out, Ctx, Mat> throttle(int cost, Duration per, int maximumBurst, Function<Out, Integer> costCalculation, ThrottleMode mode) {
        return this.viaScala((Function1<akka.stream.scaladsl.SourceWithContext, akka.stream.scaladsl.SourceWithContext> & Serializable)x$27 -> (akka.stream.scaladsl.SourceWithContext)x$27.throttle(cost, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(per)), maximumBurst, (Function1<Object, Object> & Serializable)(Out param2) -> BoxesRunTime.boxToInteger(Predef$.MODULE$.Integer2int((Integer)costCalculation.apply(param2))), mode));
    }

    public <Mat2> RunnableGraph<Mat> to(Graph<SinkShape<Pair<Out, Ctx>>, Mat2> sink2) {
        return RunnableGraph$.MODULE$.fromGraph(((akka.stream.scaladsl.Source)this.asScala().asSource().map((Function1<Tuple2, Pair> & Serializable)(Out x0$1) -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null) {
                Object o = tuple2._1();
                Object e = tuple2._2();
                return new Pair(o, e);
            }
            throw new MatchError(tuple2);
        })).to(sink2));
    }

    public <Mat2, Mat3> RunnableGraph<Mat3> toMat(Graph<SinkShape<Pair<Out, Ctx>>, Mat2> sink2, Function2<Mat, Mat2, Mat3> combine) {
        return RunnableGraph$.MODULE$.fromGraph(((akka.stream.scaladsl.Source)this.asScala().asSource().map((Function1<Tuple2, Pair> & Serializable)(Out x0$1) -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null) {
                Object o = tuple2._1();
                Object e = tuple2._2();
                return new Pair(o, e);
            }
            throw new MatchError(tuple2);
        })).toMat(sink2, (scala.Function2)package$.MODULE$.combinerToScala(combine)));
    }

    public <M> M runWith(Graph<SinkShape<Pair<Out, Ctx>>, M> sink2, ClassicActorSystemProvider systemProvider) {
        return (M)this.toMat(sink2, Keep$.MODULE$.right()).run(systemProvider.classicSystem());
    }

    public <M> M runWith(Graph<SinkShape<Pair<Out, Ctx>>, M> sink2, Materializer materializer) {
        return (M)this.toMat(sink2, Keep$.MODULE$.right()).run(materializer);
    }

    public akka.stream.scaladsl.SourceWithContext<Out, Ctx, Mat> asScala() {
        return this.delegate;
    }

    private <Out2, Ctx2, Mat2> SourceWithContext<Out2, Ctx2, Mat2> viaScala(Function1<akka.stream.scaladsl.SourceWithContext<Out, Ctx, Mat>, akka.stream.scaladsl.SourceWithContext<Out2, Ctx2, Mat2>> f) {
        return new SourceWithContext<Out2, Ctx2, Mat2>(f.apply(this.delegate));
    }

    public SourceWithContext(akka.stream.scaladsl.SourceWithContext<Out, Ctx, Mat> delegate) {
        this.delegate = delegate;
        super(delegate);
    }
}

