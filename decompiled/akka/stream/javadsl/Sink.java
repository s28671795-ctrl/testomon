/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ClassicActorSystemProvider;
import akka.japi.Pair;
import akka.japi.function.Creator;
import akka.japi.function.Function;
import akka.japi.function.Function2;
import akka.japi.function.Procedure;
import akka.stream.ActorMaterializer;
import akka.stream.Attributes;
import akka.stream.Graph;
import akka.stream.Materializer;
import akka.stream.SinkShape;
import akka.stream.SourceShape;
import akka.stream.SystemMaterializer;
import akka.stream.SystemMaterializer$;
import akka.stream.UniformFanOutShape;
import akka.stream.impl.LinearTraversalBuilder;
import akka.stream.javadsl.AsPublisher;
import akka.stream.javadsl.Flow$;
import akka.stream.javadsl.Keep$;
import akka.stream.javadsl.Sink$;
import akka.stream.javadsl.SinkQueueWithCancel;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import scala.Function1;
import scala.MatchError;
import scala.Tuple2;
import scala.reflect.ScalaSignature;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\u0019Mr!\u0002\u001e<\u0011\u0003\u0011e!\u0002#<\u0011\u0003)\u0005\"\u0002'\u0002\t\u0003i\u0005\"\u0002(\u0002\t\u0003y\u0005b\u0002B\u000f\u0003\u0011\u0005!q\u0004\u0005\b\u0005k\tA\u0011\u0001B\u001c\u0011\u001d\u0011i&\u0001C\u0001\u0005?BqAa\u001c\u0002\t\u0003\u0011\t\bC\u0004\u0003\u0010\u0006!\tA!%\t\u000f\tu\u0015\u0001\"\u0001\u0003 \"9!\u0011W\u0001\u0005\u0002\tM\u0006b\u0002B_\u0003\u0011\u0005!q\u0018\u0005\b\u00053\fA\u0011\u0001Bn\u0011\u001d\u0011i/\u0001C\u0001\u0005_Dqa!\u0005\u0002\t\u0003\u0019\u0019\u0002C\u0004\u0004.\u0005!\taa\f\t\u000f\rm\u0012\u0001\"\u0001\u0004>!91\u0011K\u0001\u0005\u0002\rM\u0003bBB0\u0003\u0011\u00051\u0011\r\u0005\b\u0007_\nA\u0011AB9\u0011\u001d\u00199)\u0001C\u0001\u0007\u0013Cqaa&\u0002\t\u0003\u0019I\nC\u0004\u00042\u0006!\taa-\t\u000f\rE\u0016\u0001\"\u0001\u0004b\"911_\u0001\u0005\u0002\rU\bb\u0002C\u0013\u0003\u0011\u0005Aq\u0005\u0005\b\t{\tA\u0011\u0001C \u0011\u001d!y&\u0001C\u0001\tCBq\u0001b!\u0002\t\u0003!)\tC\u0004\u0006\f\u0005!\t!\"\u0004\t\u000f\u0011\r\u0015\u0001\"\u0001\u0006F!9QQO\u0001\u0005\u0002\u0015]\u0004bBC;\u0003\u0011\u0005Q1\u0012\u0005\b\u000b/\u000bA\u0011ACM\u0011\u001d)\u0019-\u0001C\u0001\u000b\u000bDq!\":\u0002\t\u0003)9\u000fC\u0004\u0006\u0000\u0006!\tA\"\u0001\t\u000f\u0019e\u0011\u0001\"\u0001\u0007\u001c\u0019!Ai\u000f\u0002S\u0011!IgE!A!\u0002\u0013Q\u0007\"\u0002''\t\u0003y\u0007\"\u0002:'\t\u0003\u001a\b\"\u0002;'\t\u0003*\b\"\u0002?'\t\u0003j\bbBA\nM\u0011\u0005\u0011Q\u0003\u0005\b\u0003/1C\u0011AA\r\u0011\u001d\t9B\nC\u0001\u0003\u007fAq!!\u0016'\t\u0003\t9\u0006C\u0004\u0002x\u0019\"\t!!\u001f\t\u000f\u0005%e\u0005\"\u0001\u0002\f\"9\u0011\u0011\u0012\u0014\u0005\u0002\u0005]\u0006bBA^M\u0011\u0005\u0013Q\u0018\u0005\b\u0003\u00134C\u0011IAf\u0011\u001d\tyM\nC!\u0003#Dq!a6'\t\u0003\nI\u000eC\u0004\u0002X\u001a\"\t%a7\t\u000f\u0005]g\u0005\"\u0011\u0002b\"9\u0011q\u001e\u0014\u0005B\u0005E\u0018\u0001B*j].T!\u0001P\u001f\u0002\u000f)\fg/\u00193tY*\u0011ahP\u0001\u0007gR\u0014X-Y7\u000b\u0003\u0001\u000bA!Y6lC\u000e\u0001\u0001CA\"\u0002\u001b\u0005Y$\u0001B*j].\u001c\"!\u0001$\u0011\u0005\u001dSU\"\u0001%\u000b\u0003%\u000bQa]2bY\u0006L!a\u0013%\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t!)\u0001\u0003g_2$W#\u0002)\u0003\u000e\u0005UH#B)\u0003\u0012\tU\u0001CB\"'\u0003g\f90F\u0002T;\u001e\u001c2A\n$U!\u0011)f\u000b\u00174\u000e\u0003uJ!aV\u001f\u0003\u000b\u001d\u0013\u0018\r\u001d5\u0011\u0007UK6,\u0003\u0002[{\tI1+\u001b8l'\"\f\u0007/\u001a\t\u00039vc\u0001\u0001B\u0003_M\t\u0007qL\u0001\u0002J]F\u0011\u0001m\u0019\t\u0003\u000f\u0006L!A\u0019%\u0003\u000f9{G\u000f[5oOB\u0011q\tZ\u0005\u0003K\"\u00131!\u00118z!\tav\rB\u0003iM\t\u0007qLA\u0002NCR\f\u0001\u0002Z3mK\u001e\fG/\u001a\t\u0005W:\\f-D\u0001m\u0015\tiW(\u0001\u0005tG\u0006d\u0017\rZ:m\u0013\t!E\u000e\u0006\u0002qcB!1IJ.g\u0011\u0015I\u0007\u00061\u0001k\u0003\u0015\u0019\b.\u00199f+\u0005A\u0016\u0001\u0005;sCZ,'o]1m\u0005VLG\u000eZ3s+\u00051\bCA<{\u001b\u0005A(BA=>\u0003\u0011IW\u000e\u001d7\n\u0005mD(A\u0006'j]\u0016\f'\u000f\u0016:bm\u0016\u00148/\u00197Ck&dG-\u001a:\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012A \t\u0004\u007f\u00065a\u0002BA\u0001\u0003\u0013\u00012!a\u0001I\u001b\t\t)AC\u0002\u0002\b\u0005\u000ba\u0001\u0010:p_Rt\u0014bAA\u0006\u0011\u00061\u0001K]3eK\u001aLA!a\u0004\u0002\u0012\t11\u000b\u001e:j]\u001eT1!a\u0003I\u0003\u001d\t7oU2bY\u0006,\u0012A[\u0001\beVtw+\u001b;i+\u0011\tY\"a\b\u0015\r\u0005u\u00111EA\u0018!\ra\u0016q\u0004\u0003\u0007\u0003Ci#\u0019A0\u0003\u00035Cq!!\n.\u0001\u0004\t9#\u0001\u0004t_V\u00148-\u001a\t\u0007+Z\u000bI#!\b\u0011\tU\u000bYcW\u0005\u0004\u0003[i$aC*pkJ\u001cWm\u00155ba\u0016Dq!!\r.\u0001\u0004\t\u0019$\u0001\btsN$X-\u001c)s_ZLG-\u001a:\u0011\t\u0005U\u00121H\u0007\u0003\u0003oQ1!!\u000f@\u0003\u0015\t7\r^8s\u0013\u0011\ti$a\u000e\u00035\rc\u0017m]:jG\u0006\u001bGo\u001c:TsN$X-\u001c)s_ZLG-\u001a:\u0016\t\u0005\u0005\u0013Q\t\u000b\u0007\u0003\u0007\n9%a\u0013\u0011\u0007q\u000b)\u0005\u0002\u0004\u0002\"9\u0012\ra\u0018\u0005\b\u0003Kq\u0003\u0019AA%!\u0019)f+!\u000b\u0002D!9\u0011Q\n\u0018A\u0002\u0005=\u0013\u0001D7bi\u0016\u0014\u0018.\u00197ju\u0016\u0014\bcA+\u0002R%\u0019\u00111K\u001f\u0003\u00195\u000bG/\u001a:jC2L'0\u001a:\u0002\u0013\r|g\u000e\u001e:b[\u0006\u0004X\u0003BA-\u0003?\"B!a\u0017\u0002dA)1IJA/MB\u0019A,a\u0018\u0005\r\u0005\u0005tF1\u0001`\u0005\rIeN\r\u0005\b\u0003Kz\u0003\u0019AA4\u0003\u00051\u0007cBA5\u0003g\nifW\u0007\u0003\u0003WRA!!\u001c\u0002p\u0005Aa-\u001e8di&|gNC\u0002\u0002r}\nAA[1qS&!\u0011QOA6\u0005!1UO\\2uS>t\u0017\u0001F7ba6\u000bG/\u001a:jC2L'0\u001a3WC2,X-\u0006\u0003\u0002|\u0005\u0005E\u0003BA?\u0003\u000b\u0003Ra\u0011\u0014\\\u0003\u007f\u00022\u0001XAA\t\u0019\t\u0019\t\rb\u0001?\n!Q*\u0019;3\u0011\u001d\t)\u0007\ra\u0001\u0003\u000f\u0003r!!\u001b\u0002t\u0019\fy(\u0001\bqe\u0016l\u0015\r^3sS\u0006d\u0017N_3\u0015\t\u00055\u0015Q\u0017\t\t\u0003\u001f\u000b\t*!&\u0002*6\u0011\u0011qN\u0005\u0005\u0003'\u000byG\u0001\u0003QC&\u0014(f\u00014\u0002\u0018.\u0012\u0011\u0011\u0014\t\u0005\u00037\u000b)+\u0004\u0002\u0002\u001e*!\u0011qTAQ\u0003%)hn\u00195fG.,GMC\u0002\u0002$\"\u000b!\"\u00198o_R\fG/[8o\u0013\u0011\t9+!(\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW\r\u0005\u0004DM\u0005-\u0016Q\u0016\u0016\u00047\u0006]\u0005\u0003BAX\u0003ck\u0011aP\u0005\u0004\u0003g{$a\u0002(piV\u001bX\r\u001a\u0005\b\u0003c\t\u0004\u0019AA\u001a)\u0011\ti)!/\t\u000f\u00055#\u00071\u0001\u0002P\u0005qq/\u001b;i\u0003R$(/\u001b2vi\u0016\u001cHc\u00019\u0002@\"9\u0011\u0011Y\u001aA\u0002\u0005\r\u0017\u0001B1uiJ\u00042!VAc\u0013\r\t9-\u0010\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018!D1eI\u0006#HO]5ckR,7\u000fF\u0002q\u0003\u001bDq!!15\u0001\u0004\t\u0019-A\u0003oC6,G\rF\u0002q\u0003'Da!!66\u0001\u0004q\u0018\u0001\u00028b[\u0016\fQ!Y:z]\u000e,\u0012\u0001\u001d\u000b\u0004a\u0006u\u0007BBApo\u0001\u0007a0\u0001\u0006eSN\u0004\u0018\r^2iKJ$R\u0001]Ar\u0003KDa!a89\u0001\u0004q\bbBAtq\u0001\u0007\u0011\u0011^\u0001\u0010S:\u0004X\u000f\u001e\"vM\u001a,'oU5{KB\u0019q)a;\n\u0007\u00055\bJA\u0002J]R\fQbZ3u\u0003R$(/\u001b2vi\u0016\u001cXCAAb!\ra\u0016Q\u001f\u0003\u0006=\u000e\u0011\ra\u0018\t\u0007\u0003s\u00149Aa\u0003\u000e\u0005\u0005m(\u0002BA\u007f\u0003\u007f\f!bY8oGV\u0014(/\u001a8u\u0015\u0011\u0011\tAa\u0001\u0002\tU$\u0018\u000e\u001c\u0006\u0003\u0005\u000b\tAA[1wC&!!\u0011BA~\u0005=\u0019u.\u001c9mKRLwN\\*uC\u001e,\u0007c\u0001/\u0003\u000e\u00111!qB\u0002C\u0002}\u0013\u0011!\u0016\u0005\b\u0005'\u0019\u0001\u0019\u0001B\u0006\u0003\u0011QXM]8\t\u000f\u0005\u00154\u00011\u0001\u0003\u0018AQ\u0011\u0011\u000eB\r\u0005\u0017\t\u0019Pa\u0003\n\t\tm\u00111\u000e\u0002\n\rVt7\r^5p]J\n\u0011BZ8mI\u0006\u001b\u0018P\\2\u0016\r\t\u0005\"Q\u0006B\u0014)\u0019\u0011\u0019Ca\f\u00032A11I\nB\u0013\u0005S\u00012\u0001\u0018B\u0014\t\u0015qFA1\u0001`!\u0019\tIPa\u0002\u0003,A\u0019AL!\f\u0005\r\t=AA1\u0001`\u0011\u001d\u0011\u0019\u0002\u0002a\u0001\u0005WAq!!\u001a\u0005\u0001\u0004\u0011\u0019\u0004\u0005\u0006\u0002j\te!1\u0006B\u0013\u0005S\tqaY8mY\u0016\u001cG/\u0006\u0004\u0003:\t\u0015#q\b\u000b\u0005\u0005w\u00119\u0005\u0005\u0004DM\tu\"\u0011\t\t\u00049\n}B!\u00020\u0006\u0005\u0004y\u0006CBA}\u0005\u000f\u0011\u0019\u0005E\u0002]\u0005\u000b\"aAa\u0004\u0006\u0005\u0004y\u0006b\u0002B%\u000b\u0001\u0007!1J\u0001\nG>dG.Z2u_J\u0004DA!\u0014\u0003ZAQ!q\nB*\u0005{\u00119Fa\u0011\u000e\u0005\tE#b\u0001 \u0002\u0000&!!Q\u000bB)\u0005%\u0019u\u000e\u001c7fGR|'\u000fE\u0002]\u00053\"1Ba\u0017\u0003H\u0005\u0005\t\u0011!B\u0001?\n\u0019q\fJ\u0019\u0002\rI,G-^2f+\u0011\u0011\tGa\u001a\u0015\t\t\r$1\u000e\t\u0007\u0007\u001a\u0012)G!\u001b\u0011\u0007q\u00139\u0007B\u0003_\r\t\u0007q\f\u0005\u0004\u0002z\n\u001d!Q\r\u0005\b\u0003K2\u0001\u0019\u0001B7!)\tIG!\u0007\u0003f\t\u0015$QM\u0001\u000fMJ|WnU;cg\u000e\u0014\u0018NY3s+\u0011\u0011\u0019H!\u001f\u0015\t\tU$1\u0010\t\u0007\u0007\u001a\u00129(!,\u0011\u0007q\u0013I\bB\u0003_\u000f\t\u0007q\fC\u0004\u0003~\u001d\u0001\rAa \u0002\tM,(m\u001d\t\u0007\u0005\u0003\u0013YIa\u001e\u000e\u0005\t\r%\u0002\u0002BC\u0005\u000f\u000bqB]3bGRLg/Z:ue\u0016\fWn\u001d\u0006\u0003\u0005\u0013\u000b1a\u001c:h\u0013\u0011\u0011iIa!\u0003\u0015M+(m]2sS\n,'/A\u0005dC:\u001cW\r\u001c7fIV!!1\u0013BM)\t\u0011)\n\u0005\u0004DM\t]\u0015Q\u0016\t\u00049\neEA\u0002BN\u0011\t\u0007qLA\u0001U\u0003\u0019IwM\\8sKV!!\u0011\u0015BT)\t\u0011\u0019\u000b\u0005\u0004DM\t\u0015&\u0011\u0016\t\u00049\n\u001dFA\u0002BN\u0013\t\u0007q\f\u0005\u0004\u0002z\n\u001d!1\u0016\t\u0005\u0003_\u0013i+C\u0002\u00030~\u0012A\u0001R8oK\u0006)a.\u001a<feV!!Q\u0017B^+\t\u00119\f\u0005\u0004DM\te&\u0011\u0016\t\u00049\nmFA\u0002BN\u0015\t\u0007q,A\u0006bgB+(\r\\5tQ\u0016\u0014X\u0003\u0002Ba\u0005\u000f$BAa1\u0003PB11I\nBc\u0005\u0013\u00042\u0001\u0018Bd\t\u0019\u0011Yj\u0003b\u0001?B1!\u0011\u0011Bf\u0005\u000bLAA!4\u0003\u0004\nI\u0001+\u001e2mSNDWM\u001d\u0005\b\u0005#\\\u0001\u0019\u0001Bj\u0003\u00191\u0017M\\8viB\u00191I!6\n\u0007\t]7HA\u0006BgB+(\r\\5tQ\u0016\u0014\u0018a\u00024pe\u0016\f7\r[\u000b\u0005\u0005;\u0014\u0019\u000f\u0006\u0003\u0003`\n\u0015\bCB\"'\u0005C\u0014I\u000bE\u0002]\u0005G$aAa'\r\u0005\u0004y\u0006bBA3\u0019\u0001\u0007!q\u001d\t\u0007\u0003S\u0012IO!9\n\t\t-\u00181\u000e\u0002\n!J|7-\u001a3ve\u0016\fABZ8sK\u0006\u001c\u0007.Q:z]\u000e,BA!=\u0003zR!!1_B\u0007)\u0011\u0011)Pa?\u0011\r\r3#q\u001fBU!\ra&\u0011 \u0003\u0007\u00057k!\u0019A0\t\u000f\u0005\u0015T\u00021\u0001\u0003~BA\u0011\u0011NA:\u0005o\u0014y\u0010\u0005\u0004\u0002z\n\u001d1\u0011\u0001\t\u0005\u0007\u0007\u0019I!\u0004\u0002\u0004\u0006)!1q\u0001B\u0002\u0003\u0011a\u0017M\\4\n\t\r-1Q\u0001\u0002\u0005->LG\rC\u0004\u0004\u00105\u0001\r!!;\u0002\u0017A\f'/\u00197mK2L7/\\\u0001\u000b_:\u001cu.\u001c9mKR,W\u0003BB\u000b\u00077!Baa\u0006\u0004\u001eA11IJB\r\u0003[\u00032\u0001XB\u000e\t\u0015qfB1\u0001`\u0011\u001d\u0019yB\u0004a\u0001\u0007C\t\u0001bY1mY\n\f7m\u001b\t\u0007\u0003S\u0012Ioa\t\u0011\r\r\u00152\u0011\u0006BV\u001b\t\u00199CC\u0002\u0003\u0002!KAaa\u000b\u0004(\t\u0019AK]=\u0002\t!,\u0017\rZ\u000b\u0005\u0007c\u00199\u0004\u0006\u0002\u00044A11IJB\u001b\u0007s\u00012\u0001XB\u001c\t\u0015qvB1\u0001`!\u0019\tIPa\u0002\u00046\u0005Q\u0001.Z1e\u001fB$\u0018n\u001c8\u0016\t\r}2Q\t\u000b\u0003\u0007\u0003\u0002ba\u0011\u0014\u0004D\r\u001d\u0003c\u0001/\u0004F\u0011)a\f\u0005b\u0001?B1\u0011\u0011 B\u0004\u0007\u0013\u0002baa\u0013\u0004N\r\rSBAA\u0000\u0013\u0011\u0019y%a@\u0003\u0011=\u0003H/[8oC2\fA\u0001\\1tiV!1QKB.)\t\u00199\u0006\u0005\u0004DM\re3Q\f\t\u00049\u000emC!\u00020\u0012\u0005\u0004y\u0006CBA}\u0005\u000f\u0019I&\u0001\u0006mCN$x\n\u001d;j_:,Baa\u0019\u0004jQ\u00111Q\r\t\u0007\u0007\u001a\u001a9ga\u001b\u0011\u0007q\u001bI\u0007B\u0003_%\t\u0007q\f\u0005\u0004\u0002z\n\u001d1Q\u000e\t\u0007\u0007\u0017\u001aiea\u001a\u0002\u0011Q\f7.\u001a'bgR,Baa\u001d\u0004zQ!1QOBB!\u0019\u0019eea\u001e\u0004|A\u0019Al!\u001f\u0005\u000by\u001b\"\u0019A0\u0011\r\u0005e(qAB?!\u0019\u0019Yea \u0004x%!1\u0011QA\u0000\u0005\u0011a\u0015n\u001d;\t\u000f\r\u00155\u00031\u0001\u0002j\u0006\ta.A\u0002tKF,Baa#\u0004\u0012V\u00111Q\u0012\t\u0007\u0007\u001a\u001ayia%\u0011\u0007q\u001b\t\nB\u0003_)\t\u0007q\f\u0005\u0004\u0002z\n\u001d1Q\u0013\t\u0007\u0007\u0017\u001ayha$\u0002\u0011\u0005\u001cGo\u001c:SK\u001a,Baa'\u0004\"R11QTBR\u0007[\u0003ba\u0011\u0014\u0004 \u00065\u0006c\u0001/\u0004\"\u0012)a,\u0006b\u0001?\"91QU\u000bA\u0002\r\u001d\u0016a\u0001:fMB!\u0011QGBU\u0013\u0011\u0019Y+a\u000e\u0003\u0011\u0005\u001bGo\u001c:SK\u001aDaaa,\u0016\u0001\u0004\u0019\u0017!E8o\u0007>l\u0007\u000f\\3uK6+7o]1hK\u0006A\u0012m\u0019;peJ+gmV5uQ\n\u000b7m\u001b9sKN\u001cXO]3\u0016\t\rU61\u0018\u000b\r\u0007o\u001bila0\u0004D\u000e\u001d7\u0011\u001a\t\u0007\u0007\u001a\u001aI,!,\u0011\u0007q\u001bY\fB\u0003_-\t\u0007q\fC\u0004\u0004&Z\u0001\raa*\t\r\r\u0005g\u00031\u0001d\u00035yg.\u00138ji6+7o]1hK\"11Q\u0019\fA\u0002\r\f!\"Y2l\u001b\u0016\u001c8/Y4f\u0011\u0019\u0019yK\u0006a\u0001G\"911\u001a\fA\u0002\r5\u0017\u0001E8o\r\u0006LG.\u001e:f\u001b\u0016\u001c8/Y4f!\u001d\tI'a\u001d\u0004P\u000e\u0004Ba!5\u0004\\:!11[Bl\u001d\u0011\t\u0019a!6\n\u0003%K1a!7I\u0003\u001d\u0001\u0018mY6bO\u0016LAa!8\u0004`\nIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0004\u00073DU\u0003BBr\u0007S$\"b!:\u0004l\u000e58q^By!\u0019\u0019eea:\u0002.B\u0019Al!;\u0005\u000by;\"\u0019A0\t\u000f\r\u0015v\u00031\u0001\u0004(\"11\u0011Y\fA\u0002\rDaaa,\u0018\u0001\u0004\u0019\u0007bBBf/\u0001\u00071QZ\u0001\u0010C\u000e$xN\u001d*fM^KG\u000f[!dWV!1q_B\u007f)1\u0019Ipa@\u0005\u0002\u0011\rAQ\u0001C\u0004!\u0019\u0019eea?\u0002.B\u0019Al!@\u0005\u000byC\"\u0019A0\t\u000f\r\u0015\u0006\u00041\u0001\u0004(\"11\u0011\u0019\rA\u0002\rDaa!2\u0019\u0001\u0004\u0019\u0007BBBX1\u0001\u00071\rC\u0004\u0004Lb\u0001\ra!4)\u0017a!Y\u0001\"\u0005\u0005\u0014\u0011]A\u0011\u0004\t\u0004\u000f\u00125\u0011b\u0001C\b\u0011\nQA-\u001a9sK\u000e\fG/\u001a3\u0002\u000f5,7o]1hK\u0006\u0012AQC\u0001%+N,\u0007%Y2u_J\u0014VMZ,ji\"\u0014\u0015mY6qe\u0016\u001c8/\u001e:fA%t7\u000f^3bI\u0006)1/\u001b8dK\u0006\u0012A1D\u0001\u0006e92d\u0006\r\u0015\u00041\u0011}\u0001\u0003BB\u0002\tCIA\u0001b\t\u0004\u0006\tQA)\u001a9sK\u000e\fG/\u001a3\u0002\u0013\u0019\u0014x.\\$sCBDWC\u0002C\u0015\t_!\u0019\u0004\u0006\u0003\u0005,\u0011U\u0002CB\"'\t[!\t\u0004E\u0002]\t_!aAa'\u001a\u0005\u0004y\u0006c\u0001/\u00054\u00111\u0011\u0011E\rC\u0002}Cq\u0001b\u000e\u001a\u0001\u0004!I$A\u0001h!\u0019)f\u000bb\u000f\u00052A!Q+\u0017C\u0017\u0003A1'o\\7NCR,'/[1mSj,'/\u0006\u0004\u0005B\u0011\u001dCQ\n\u000b\u0005\t\u0007\"y\u0005\u0005\u0004DM\u0011\u0015C\u0011\n\t\u00049\u0012\u001dCA\u0002BN5\t\u0007q\f\u0005\u0004\u0002z\n\u001dA1\n\t\u00049\u00125CABA\u00115\t\u0007q\fC\u0004\u0005Ri\u0001\r\u0001b\u0015\u0002\u000f\u0019\f7\r^8ssBQAQ\u000bC-\u0003\u001f\n\u0019\r\"\u0018\u000e\u0005\u0011]#\u0002BA7\u0003\u007fLA\u0001b\u0017\u0005X\tQ!)\u001b$v]\u000e$\u0018n\u001c8\u0011\r\r3CQ\tC&\u0003\u0015\u0019X\r^;q+\u0019!\u0019\u0007\"\u001b\u0005pQ!AQ\rC9!\u0019\u0019e\u0005b\u001a\u0005lA\u0019A\f\"\u001b\u0005\r\tm5D1\u0001`!\u0019\tIPa\u0002\u0005nA\u0019A\fb\u001c\u0005\r\u0005\u00052D1\u0001`\u0011\u001d!\tf\u0007a\u0001\tg\u0002\"\u0002\"\u0016\u0005Z\u0011U\u00141\u0019C>!\r)FqO\u0005\u0004\tsj$!E!di>\u0014X*\u0019;fe&\fG.\u001b>feB11I\nC4\t[B3b\u0007C\u0006\t#!y\bb\u0006\u0005\u001a\u0005\u0012A\u0011Q\u0001\u001f+N,\u0007e\n4s_6l\u0015\r^3sS\u0006d\u0017N_3sO\u0001Jgn\u001d;fC\u0012\fqaY8nE&tW-\u0006\u0004\u0005\b\u00125E\u0011\u0014\u000b\u000b\t\u0013#y\t\")\u00050\u0012%\u0007CB\"'\t\u0017\u000bi\u000bE\u0002]\t\u001b#aAa'\u001d\u0005\u0004y\u0006b\u0002CI9\u0001\u0007A1S\u0001\b_V$\b/\u001e;2a\u0011!)\n\"(\u0011\r\r3Cq\u0013CN!\raF\u0011\u0014\u0003\u0007\u0005\u001fa\"\u0019A0\u0011\u0007q#i\nB\u0006\u0005 \u0012=\u0015\u0011!A\u0001\u0006\u0003y&aA0%e!9A1\u0015\u000fA\u0002\u0011\u0015\u0016aB8viB,HO\r\u0019\u0005\tO#Y\u000b\u0005\u0004DM\u0011]E\u0011\u0016\t\u00049\u0012-Fa\u0003CW\tC\u000b\t\u0011!A\u0003\u0002}\u00131a\u0018\u00134\u0011\u001d!\t\f\ba\u0001\tg\u000bAA]3tiB111JB@\tk\u0003D\u0001b.\u0005<B11I\nCL\ts\u00032\u0001\u0018C^\t-!i\fb0\u0002\u0002\u0003\u0005)\u0011A0\u0003\u0007}#C\u0007C\u0004\u00052r\u0001\r\u0001\"1\u0011\r\r-3q\u0010Cba\u0011!)\rb/\u0011\r\r3Cq\u0019C]!\raF\u0011\u0014\u0005\b\t\u0017d\u0002\u0019\u0001Cg\u000391\u0017M\\(viN#(/\u0019;fOf\u0004\u0002\"!\u001b\u0002t\u0011=GQ\u001b\t\u0005\u0007\u0007!\t.\u0003\u0003\u0005T\u000e\u0015!aB%oi\u0016<WM\u001d\t\u0007+Z#9.!,\u0011\u000fU#I\u000eb#\u0005\u0018&\u0019A1\\\u001f\u0003%Us\u0017NZ8s[\u001a\u000bgnT;u'\"\f\u0007/\u001a\u0015\u0007\t\u0013$y\u000e\":\u0011\u0007\u001d#\t/C\u0002\u0005d\"\u0013a\u0002Z3qe\u0016\u001c\u0017\r^3e\u001d\u0006lW-M\u0004 \tO$i/b\u0001\u0011\u0007\u001d#I/C\u0002\u0005l\"\u0013aaU=nE>d\u0017'C\u0012\u0005p\u0012MH1 C{)\u0011!9\u000f\"=\t\r\u0005U\u0007\u00011\u0001\u007f\u0013\u0011!)\u0010b>\u0002\u000b\u0005\u0004\b\u000f\\=\u000b\u0007\u0011e\b*\u0001\u0004Ts6\u0014w\u000e\\\u0019\nG\u0011uHq`C\u0001\tstAaa5\u0005\u0000&\u0019A\u0011 %2\r\u0011\u001a\u0019n!6Jc\u0015)SQAC\u0004\u001f\t)9!\t\u0002\u0006\n\u0005A1\u000f\u001e:bi\u0016<\u00170\u0001\u0006d_6\u0014\u0017N\\3NCR,B\"b\u0004\u0006\u0016\u0015\rRqEC\u001a\u000b3!\"\"\"\u0005\u0006\u001c\u0015-RqGC !\u0019\u0019e%b\u0005\u0006\u0018A\u0019A,\"\u0006\u0005\r\tmUD1\u0001`!\raV\u0011\u0004\u0003\u0007\u0003Ci\"\u0019A0\t\u000f\u0015uQ\u00041\u0001\u0006 \u0005)a-\u001b:tiB11IJC\u0011\u000bK\u00012\u0001XC\u0012\t\u0019\u0011y!\bb\u0001?B\u0019A,b\n\u0005\r\u0015%RD1\u0001`\u0005\ti\u0015\u0007C\u0004\u0006.u\u0001\r!b\f\u0002\rM,7m\u001c8e!\u0019\u0019e%\"\t\u00062A\u0019A,b\r\u0005\r\u0015URD1\u0001`\u0005\ti%\u0007C\u0004\u0005Lv\u0001\r!\"\u000f\u0011\u0011\u0005%\u00141\u000fCh\u000bw\u0001b!\u0016,\u0006>\u00055\u0006cB+\u0005Z\u0016MQ\u0011\u0005\u0005\b\u000b\u0003j\u0002\u0019AC\"\u0003\u0011i\u0017\r\u001e$\u0011\u0015\u0005%$\u0011DC\u0013\u000bc)9\"\u0006\u0005\u0006H\u00155S1NC*)\u0019)I%\"\u0016\u0006nA11IJC&\u000b\u001f\u00022\u0001XC'\t\u0019\u0011YJ\bb\u0001?B111JB@\u000b#\u00022\u0001XC*\t\u0019\t\tC\bb\u0001?\"9Qq\u000b\u0010A\u0002\u0015e\u0013!B:j].\u001c\b\u0007BC.\u000b?\u0002baa\u0013\u0004\u0000\u0015u\u0003c\u0001/\u0006`\u0011aQ\u0011MC+\u0003\u0003\u0005\tQ!\u0001\u0006d\t\u0019q\fJ\u001b\u0012\u0007\u0001,)\u0007\u0005\u0004V-\u0016\u001dT\u0011\u000b\t\u0005+f+I\u0007E\u0002]\u000bW\"aAa\u0004\u001f\u0005\u0004y\u0006b\u0002Cf=\u0001\u0007Qq\u000e\t\t\u0003S\n\u0019\bb4\u0006rA1QKVC:\u0003[\u0003r!\u0016Cm\u000b\u0017*I'A\u0003rk\u0016,X-\u0006\u0003\u0006z\u0015}D\u0003BC>\u000b\u000f\u0003ba\u0011\u0014\u0006~\u0015\u0005\u0005c\u0001/\u0006\u0000\u00111!1T\u0010C\u0002}\u0003RaQCB\u000b{J1!\"\"<\u0005M\u0019\u0016N\\6Rk\u0016,XmV5uQ\u000e\u000bgnY3m\u0011\u001d)Ii\ba\u0001\u0003S\f!#\\1y\u0007>t7-\u001e:sK:$\b+\u001e7mgV!QQRCJ)\t)y\t\u0005\u0004DM\u0015EUQ\u0013\t\u00049\u0016MEA\u0002BNA\t\u0007q\fE\u0003D\u000b\u0007+\t*\u0001\u0005mCjL\u0018J\\5u+\u0019)Y*\")\u0006(R1QQTCU\u000bg\u0003ba\u0011\u0014\u0006 \u0016\r\u0006c\u0001/\u0006\"\u00121!1T\u0011C\u0002}\u0003b!!?\u0003\b\u0015\u0015\u0006c\u0001/\u0006(\u00121\u0011\u0011E\u0011C\u0002}Cq!b+\"\u0001\u0004)i+A\u0006tS:\\g)Y2u_JL\b\u0003CA5\u0003g*y*b,\u0011\r\u0005e(qACY!\u0019\u0019e%b(\u0006&\"9QQW\u0011A\u0002\u0015]\u0016\u0001\u00034bY2\u0014\u0017mY6\u0011\r\u0005%T\u0011XCS\u0013\u0011)Y,a\u001b\u0003\u000f\r\u0013X-\u0019;pe\"Z\u0011\u0005b\u0003\u0005\u0012\u0015}Fq\u0003C\rC\t)\t-\u0001,Vg\u0016\u0004seU5oW:b\u0017M_=D_6\u0004H.\u001a;j_:\u001cF/Y4f'&t7n\n\u0011j]\u0002\u001aw.\u001c2j]\u0006$\u0018n\u001c8!o&$\b\u000eI\u0014GY><h\u0006\u001d:fM&D\u0018I\u001c3UC&d\u0007&M\u0015(A%t7\u000f^3bI\u0006iA.\u0019>z\u0013:LG/Q:z]\u000e,b!b2\u0006N\u0016UG\u0003BCe\u000b/\u0004ba\u0011\u0014\u0006L\u0016=\u0007c\u0001/\u0006N\u00121!1\u0014\u0012C\u0002}\u0003b!!?\u0003\b\u0015E\u0007CBB&\u0007\u001b*\u0019\u000eE\u0002]\u000b+$a!!\t#\u0005\u0004y\u0006bBCVE\u0001\u0007Q\u0011\u001c\t\u0007\u0003S*I,b7\u0011\r\u0005e(qACo!\u0019\u0019e%b3\u0006T\"Z!\u0005b\u0003\u0005\u0012\u0015\u0005Hq\u0003C\rC\t)\u0019/\u0001\u0016Vg\u0016\u0004seU5oW:b\u0017M_=D_6\u0004H.\u001a;j_:\u001cF/Y4f'&t7n\n\u0011j]N$X-\u00193\u0002'\r|W\u000e\u001d7fi&|gn\u0015;bO\u0016\u001c\u0016N\\6\u0016\r\u0015%Xq^C{)\u0011)Y/b>\u0011\r\r3SQ^Cy!\raVq\u001e\u0003\u0007\u00057\u001b#\u0019A0\u0011\r\u0005e(qACz!\raVQ\u001f\u0003\u0007\u0003C\u0019#\u0019A0\t\u000f\u0015e8\u00051\u0001\u0006|\u00061a-\u001e;ve\u0016\u0004b!!?\u0003\b\u0015u\bCB\"'\u000b[,\u00190\u0001\u0005mCjL8+\u001b8l+\u00191\u0019A\"\u0003\u0007\u0010Q!aQ\u0001D\t!\u0019\u0019eEb\u0002\u0007\fA\u0019AL\"\u0003\u0005\r\tmEE1\u0001`!\u0019\tIPa\u0002\u0007\u000eA\u0019ALb\u0004\u0005\r\u0005\u0005BE1\u0001`\u0011\u001d1\u0019\u0002\na\u0001\r+\taa\u0019:fCR,\u0007CBA5\u000bs39\u0002\u0005\u0004DM\u0019\u001daQB\u0001\u0018Y\u0006T\u0018pQ8na2,G/[8o'R\fw-Z*j].,bA\"\b\u0007$\u0019%B\u0003\u0002D\u0010\rW\u0001ba\u0011\u0014\u0007\"\u0019\u0015\u0002c\u0001/\u0007$\u00111!1T\u0013C\u0002}\u0003b!!?\u0003\b\u0019\u001d\u0002c\u0001/\u0007*\u00111\u0011\u0011E\u0013C\u0002}CqAb\u0005&\u0001\u00041i\u0003\u0005\u0004\u0002j\u0015efq\u0006\t\u0007\u0003s\u00149A\"\r\u0011\r\r3c\u0011\u0005D\u0014\u0001")
public final class Sink<In, Mat>
implements Graph<SinkShape<In>, Mat> {
    private final akka.stream.scaladsl.Sink<In, Mat> delegate;

    public static <T, M> Sink<T, CompletionStage<M>> lazyCompletionStageSink(Creator<CompletionStage<Sink<T, M>>> create) {
        return Sink$.MODULE$.lazyCompletionStageSink(create);
    }

    public static <T, M> Sink<T, CompletionStage<M>> lazySink(Creator<Sink<T, M>> create) {
        return Sink$.MODULE$.lazySink(create);
    }

    public static <T, M> Sink<T, CompletionStage<M>> completionStageSink(CompletionStage<Sink<T, M>> future) {
        return Sink$.MODULE$.completionStageSink(future);
    }

    public static <T, M> Sink<T, CompletionStage<Optional<M>>> lazyInitAsync(Creator<CompletionStage<Sink<T, M>>> sinkFactory) {
        return Sink$.MODULE$.lazyInitAsync(sinkFactory);
    }

    public static <T, M> Sink<T, CompletionStage<M>> lazyInit(Function<T, CompletionStage<Sink<T, M>>> sinkFactory, Creator<M> fallback) {
        return Sink$.MODULE$.lazyInit(sinkFactory, fallback);
    }

    public static <T> Sink<T, SinkQueueWithCancel<T>> queue() {
        return Sink$.MODULE$.queue();
    }

    public static <T> Sink<T, SinkQueueWithCancel<T>> queue(int maxConcurrentPulls) {
        return Sink$.MODULE$.queue(maxConcurrentPulls);
    }

    public static <T, U, M> Sink<T, List<M>> combine(List<? extends Graph<SinkShape<U>, M>> sinks, Function<Integer, Graph<UniformFanOutShape<T, U>, NotUsed>> fanOutStrategy) {
        return Sink$.MODULE$.combine(sinks, fanOutStrategy);
    }

    public static <T, U, M1, M2, M> Sink<T, M> combineMat(Sink<U, M1> first, Sink<U, M2> second, Function<Integer, Graph<UniformFanOutShape<T, U>, NotUsed>> fanOutStrategy, Function2<M1, M2, M> matF) {
        return Sink$.MODULE$.combineMat(first, second, fanOutStrategy, matF);
    }

    public static <T, U> Sink<T, NotUsed> combine(Sink<U, ?> output1, Sink<U, ?> output2, List<Sink<U, ?>> rest, Function<Integer, Graph<UniformFanOutShape<T, U>, NotUsed>> fanOutStrategy) {
        return Sink$.MODULE$.combine(output1, output2, rest, fanOutStrategy);
    }

    public static <T, M> Sink<T, CompletionStage<M>> setup(BiFunction<ActorMaterializer, Attributes, Sink<T, M>> factory2) {
        return Sink$.MODULE$.setup(factory2);
    }

    public static <T, M> Sink<T, CompletionStage<M>> fromMaterializer(BiFunction<Materializer, Attributes, Sink<T, M>> factory2) {
        return Sink$.MODULE$.fromMaterializer(factory2);
    }

    public static <T, M> Sink<T, M> fromGraph(Graph<SinkShape<T>, M> g2) {
        return Sink$.MODULE$.fromGraph(g2);
    }

    @Deprecated
    public static <In> Sink<In, NotUsed> actorRefWithAck(ActorRef ref, Object onInitMessage, Object ackMessage, Object onCompleteMessage, Function<Throwable, Object> onFailureMessage) {
        return Sink$.MODULE$.actorRefWithAck(ref, onInitMessage, ackMessage, onCompleteMessage, onFailureMessage);
    }

    public static <In> Sink<In, NotUsed> actorRefWithBackpressure(ActorRef ref, Object onInitMessage, Object onCompleteMessage, Function<Throwable, Object> onFailureMessage) {
        return Sink$.MODULE$.actorRefWithBackpressure(ref, onInitMessage, onCompleteMessage, onFailureMessage);
    }

    public static <In> Sink<In, NotUsed> actorRefWithBackpressure(ActorRef ref, Object onInitMessage, Object ackMessage, Object onCompleteMessage, Function<Throwable, Object> onFailureMessage) {
        return Sink$.MODULE$.actorRefWithBackpressure(ref, onInitMessage, ackMessage, onCompleteMessage, onFailureMessage);
    }

    public static <In> Sink<In, NotUsed> actorRef(ActorRef ref, Object onCompleteMessage) {
        return Sink$.MODULE$.actorRef(ref, onCompleteMessage);
    }

    public static <In> Sink<In, CompletionStage<List<In>>> seq() {
        return Sink$.MODULE$.seq();
    }

    public static <In> Sink<In, CompletionStage<List<In>>> takeLast(int n) {
        return Sink$.MODULE$.takeLast(n);
    }

    public static <In> Sink<In, CompletionStage<Optional<In>>> lastOption() {
        return Sink$.MODULE$.lastOption();
    }

    public static <In> Sink<In, CompletionStage<In>> last() {
        return Sink$.MODULE$.last();
    }

    public static <In> Sink<In, CompletionStage<Optional<In>>> headOption() {
        return Sink$.MODULE$.headOption();
    }

    public static <In> Sink<In, CompletionStage<In>> head() {
        return Sink$.MODULE$.head();
    }

    public static <In> Sink<In, NotUsed> onComplete(Procedure<Try<Done>> callback) {
        return Sink$.MODULE$.onComplete(callback);
    }

    public static <T> Sink<T, CompletionStage<Done>> foreachAsync(int parallelism, Function<T, CompletionStage<Void>> f) {
        return Sink$.MODULE$.foreachAsync(parallelism, f);
    }

    public static <T> Sink<T, CompletionStage<Done>> foreach(Procedure<T> f) {
        return Sink$.MODULE$.foreach(f);
    }

    public static <T> Sink<T, Publisher<T>> asPublisher(AsPublisher fanout) {
        return Sink$.MODULE$.asPublisher(fanout);
    }

    public static <T> Sink<T, CompletionStage<Done>> never() {
        return Sink$.MODULE$.never();
    }

    public static <T> Sink<T, CompletionStage<Done>> ignore() {
        return Sink$.MODULE$.ignore();
    }

    public static <T> Sink<T, NotUsed> cancelled() {
        return Sink$.MODULE$.cancelled();
    }

    public static <In> Sink<In, NotUsed> fromSubscriber(Subscriber<In> subs) {
        return Sink$.MODULE$.fromSubscriber(subs);
    }

    public static <In> Sink<In, CompletionStage<In>> reduce(Function2<In, In, In> f) {
        return Sink$.MODULE$.reduce(f);
    }

    public static <U, In> Sink<In, CompletionStage<U>> collect(Collector<In, ?, U> collector) {
        return Sink$.MODULE$.collect(collector);
    }

    public static <U, In> Sink<In, CompletionStage<U>> foldAsync(U zero, Function2<U, In, CompletionStage<U>> f) {
        return Sink$.MODULE$.foldAsync(zero, f);
    }

    public static <U, In> Sink<In, CompletionStage<U>> fold(U zero, Function2<U, In, U> f) {
        return Sink$.MODULE$.fold(zero, f);
    }

    @Override
    public SinkShape<In> shape() {
        return this.delegate.shape();
    }

    @Override
    public LinearTraversalBuilder traversalBuilder() {
        return this.delegate.traversalBuilder();
    }

    public String toString() {
        return this.delegate.toString();
    }

    public akka.stream.scaladsl.Sink<In, Mat> asScala() {
        return this.delegate;
    }

    public <M> M runWith(Graph<SourceShape<In>, M> source2, ClassicActorSystemProvider systemProvider) {
        return this.asScala().runWith(source2, ((SystemMaterializer)SystemMaterializer$.MODULE$.apply(systemProvider.classicSystem())).materializer());
    }

    public <M> M runWith(Graph<SourceShape<In>, M> source2, Materializer materializer) {
        return this.asScala().runWith(source2, materializer);
    }

    public <In2> Sink<In2, Mat> contramap(Function<In2, In> f) {
        return Flow$.MODULE$.fromFunction(f).toMat(this, Keep$.MODULE$.right());
    }

    public <Mat2> Sink<In, Mat2> mapMaterializedValue(Function<Mat, Mat2> f) {
        return new Sink(this.delegate.mapMaterializedValue((Function1<Object, Object> & Serializable)(Mat param2) -> f.apply(param2)));
    }

    public Pair<Mat, Sink<In, NotUsed>> preMaterialize(ClassicActorSystemProvider systemProvider) {
        Tuple2<Mat, akka.stream.scaladsl.Sink<In, NotUsed>> tuple2 = this.delegate.preMaterialize(((SystemMaterializer)SystemMaterializer$.MODULE$.apply(systemProvider.classicSystem())).materializer());
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        Mat mat = tuple2._1();
        akka.stream.scaladsl.Sink<In, NotUsed> sink2 = tuple2._2();
        Tuple2<Mat, akka.stream.scaladsl.Sink<In, NotUsed>> tuple22 = new Tuple2<Mat, akka.stream.scaladsl.Sink<In, NotUsed>>(mat, sink2);
        Mat mat2 = tuple22._1();
        akka.stream.scaladsl.Sink<In, NotUsed> sink3 = tuple22._2();
        return new Pair(mat2, sink3.asJava());
    }

    public Pair<Mat, Sink<In, NotUsed>> preMaterialize(Materializer materializer) {
        Tuple2<Mat, akka.stream.scaladsl.Sink<In, NotUsed>> tuple2 = this.delegate.preMaterialize(materializer);
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        Mat mat = tuple2._1();
        akka.stream.scaladsl.Sink<In, NotUsed> sink2 = tuple2._2();
        Tuple2<Mat, akka.stream.scaladsl.Sink<In, NotUsed>> tuple22 = new Tuple2<Mat, akka.stream.scaladsl.Sink<In, NotUsed>>(mat, sink2);
        Mat mat2 = tuple22._1();
        akka.stream.scaladsl.Sink<In, NotUsed> sink3 = tuple22._2();
        return new Pair(mat2, sink3.asJava());
    }

    @Override
    public Sink<In, Mat> withAttributes(Attributes attr) {
        return new Sink<In, Mat>(this.delegate.withAttributes(attr));
    }

    @Override
    public Sink<In, Mat> addAttributes(Attributes attr) {
        return new Sink<In, Mat>(this.delegate.addAttributes(attr));
    }

    @Override
    public Sink<In, Mat> named(String name) {
        return new Sink<In, Mat>(this.delegate.named(name));
    }

    @Override
    public Sink<In, Mat> async() {
        return new Sink<In, Mat>(this.delegate.async());
    }

    @Override
    public Sink<In, Mat> async(String dispatcher) {
        return new Sink<In, Mat>(this.delegate.async(dispatcher));
    }

    @Override
    public Sink<In, Mat> async(String dispatcher, int inputBufferSize) {
        return new Sink<In, Mat>(this.delegate.async(dispatcher, inputBufferSize));
    }

    @Override
    public Attributes getAttributes() {
        return this.delegate.getAttributes();
    }

    public Sink(akka.stream.scaladsl.Sink<In, Mat> delegate) {
        this.delegate = delegate;
        Graph.$init$(this);
    }
}

