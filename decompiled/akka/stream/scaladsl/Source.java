/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.annotation.ApiMayChange;
import akka.event.LogMarker;
import akka.event.LoggingAdapter;
import akka.event.MarkerLoggingAdapter;
import akka.stream.ActorMaterializer;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.BoundedSourceQueue;
import akka.stream.CompletionStrategy;
import akka.stream.DelayOverflowStrategy;
import akka.stream.FlowMonitor;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.Materializer;
import akka.stream.OverflowStrategy;
import akka.stream.SinkShape;
import akka.stream.SourceShape;
import akka.stream.SubstreamCancelStrategy;
import akka.stream.ThrottleMode;
import akka.stream.UniformFanInShape;
import akka.stream.impl.LinearTraversalBuilder;
import akka.stream.impl.LinearTraversalBuilder$;
import akka.stream.scaladsl.DelayStrategy;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Flow$;
import akka.stream.scaladsl.FlowOps;
import akka.stream.scaladsl.FlowOpsMat;
import akka.stream.scaladsl.Keep$;
import akka.stream.scaladsl.RunnableGraph;
import akka.stream.scaladsl.Sink$;
import akka.stream.scaladsl.Source$;
import akka.stream.scaladsl.SourceQueueWithComplete;
import akka.stream.scaladsl.SourceWithContext;
import akka.stream.scaladsl.SubFlow;
import akka.util.Timeout;
import java.io.Serializable;
import java.util.concurrent.CompletionStage;
import java.util.stream.BaseStream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.Seq;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.duration.FiniteDuration;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Nothing$;

@ScalaSignature(bytes="\u0006\u0005!Uh\u0001B+W\u0005uC\u0001B \u0001\u0003\u0006\u0004%\te \u0005\u000b\u0003\u001b\u0001!\u0011!Q\u0001\n\u0005\u0005\u0001BCA\b\u0001\t\u0015\r\u0011\"\u0011\u0002\u0012!I\u00111\u0003\u0001\u0003\u0002\u0003\u0006Ia\u001f\u0005\b\u0003+\u0001A\u0011AA\f\u000b\u0019\ty\u0002\u0001\u0011\u0002\"\u00151\u0011q\b\u0001!\u0003\u0003*a!a\u0014\u0001A\u0005ESABA,\u0001\u0001\nI\u0006C\u0004\u0002b\u0001!\t%a\u0019\t\u000f\u0005m\u0004\u0001\"\u0011\u0002~!9\u0011Q\u0014\u0001\u0005B\u0005}\u0005bBAc\u0001\u0011\u0005\u0011q\u0019\u0005\b\u0003;\u0004A\u0011AAp\u0011\u001d\t9\u0010\u0001C!\u0003sDqA!\u0004\u0001\t\u0003\u0011y\u0001C\u0004\u0003.\u0001!\tAa\f\t\u000f\t\u001d\u0003\u0001\"\u0001\u0003J!9!\u0011\f\u0001\u0005\u0002\tm\u0003b\u0002B;\u0001\u0011\u0005!q\u000f\u0005\b\u0005\u001b\u0003A\u0011\u0001BH\u0011\u001d\u0011\u0019\u000b\u0001C\u0001\u0005KCqA!.\u0001\t\u0003\u00129\fC\u0004\u0003F\u0002!\tEa2\t\u000f\t-\u0007\u0001\"\u0011\u0003N\"9!1\u001b\u0001\u0005B\tU\u0007b\u0002Bj\u0001\u0011\u0005#q\u001b\u0005\b\u0005'\u0004A\u0011\tBo\u0011\u001d\u0011Y\u000f\u0001C\u0001\u0005[DqAa?\u0001\t\u0003\u0011i\u0010C\u0004\u0004\u0012\u0001!\tea\u0005\b\u000f\rUa\u000b#\u0001\u0004\u0018\u00191QK\u0016E\u0001\u00073Aq!!\u0006\"\t\u0003\u0019Y\u0002C\u0004\u0002\u0010\u0005\"\ta!\b\t\u000f\r%\u0012\u0005\"\u0001\u0004,!91\u0011J\u0011\u0005\u0002\r-\u0003bBB8C\u0011\u00051\u0011\u000f\u0005\b\u00073\u000bC\u0011ABN\u0011\u001d\u0019Y+\tC\u0001\u0007[Cqaa1\"\t\u0003\u0019)\rC\u0004\u0004^\u0006\"\taa8\t\u000f\u0011=\u0011\u0005\"\u0001\u0005\u0012!9AqF\u0011\u0005\u0002\u0011E\u0002b\u0002C$C\u0011\u0005A\u0011\n\u0005\b\tK\nC\u0011\u0001C4\u0011\u001d!))\tC\u0001\t\u000fCq\u0001\"-\"\t\u0003!\u0019\fC\u0004\u0005`\u0006\"\t\u0001\"9\t\u000f\u0011=\u0018\u0005\"\u0001\u0005r\"9AQ`\u0011\u0005\u0002\u0011}\bbBC\u0011C\u0011\u0005Q1\u0005\u0005\b\u000b\u007f\tC\u0011AC!\u0011!)Y%\tQ\u0001\n\u00155\u0003bBC(C\u0011\u0005Q\u0011\u000b\u0005\b\u000bG\nC\u0011AC3\u0011\u001d)I(\tC\u0001\u000bwBq!\"'\"\t\u0003)Y\nC\u0004\u0005>\u0005\"\t!b-\t\u000f\u0015\r\u0017\u0005\"\u0001\u0006F\"AQqZ\u0011!\u0002\u0013)i\u0005C\u0004\u0006R\u0006\"\t!b5\t\u000f\u0015\u0005\u0018\u0005\"\u0001\u0006d\"9Q\u0011`\u0011\u0005\u0002\u0015m\bb\u0002D\u0005C\u0011\u0005a1\u0002\u0005\b\r7\tC\u0011\u0001D\u000f\u0011\u001d1\u0019$\tC\u0001\rkAqA\"\u0014\"\t\u00031y\u0005C\u0004\u0007`\u0005\"\tA\"\u0019\t\u000f\u0019}\u0013\u0005\"\u0001\u0007\u0016\"Aa\u0011V\u0011\u0005\u0002i3Y\u000bC\u0004\u0007P\u0006\"\tA\"5\t\u000f\u0019%\u0016\u0005\"\u0001\u0007b\"9\u00111W\u0011\u0005\u0002\u0019M\bbBAZC\u0011\u0005qq\u000f\u0005\b\u000fG\u000bC\u0011ADS\u0011\u001d9I/\tC\u0001\u000fWDq\u0001c\u0004\"\t\u0003A\t\u0002C\u0004\tB\u0005\"\t\u0001c\u0011\t\u000f!\u0005\u0013\u0005\"\u0001\tV!9\u0001\u0012I\u0011\u0005\u0002!%\u0004b\u0002E?C\u0011\u0005\u0001r\u0010\u0005\b\u0011?\u000bC\u0011\u0001EQ\u0011\u001dA\t-\tC\u0001\u0011\u0007\u0014aaU8ve\u000e,'BA,Y\u0003!\u00198-\u00197bINd'BA-[\u0003\u0019\u0019HO]3b[*\t1,\u0001\u0003bW.\f7\u0001A\u000b\u0004=.,8\u0003\u0002\u0001`K^\u0004\"\u0001Y2\u000e\u0003\u0005T\u0011AY\u0001\u0006g\u000e\fG.Y\u0005\u0003I\u0006\u0014a!\u00118z%\u00164\u0007\u0003\u00024hSRl\u0011AV\u0005\u0003QZ\u0013!B\u00127po>\u00038/T1u!\tQ7\u000e\u0004\u0001\u0005\r1\u0004AQ1\u0001n\u0005\ryU\u000f^\t\u0003]F\u0004\"\u0001Y8\n\u0005A\f'a\u0002(pi\"Lgn\u001a\t\u0003AJL!a]1\u0003\u0007\u0005s\u0017\u0010\u0005\u0002kk\u00121a\u000f\u0001CC\u00025\u00141!T1u!\u0011A\u0018p\u001f;\u000e\u0003aK!A\u001f-\u0003\u000b\u001d\u0013\u0018\r\u001d5\u0011\u0007ad\u0018.\u0003\u0002~1\nY1k\\;sG\u0016\u001c\u0006.\u00199f\u0003A!(/\u0019<feN\fGNQ;jY\u0012,'/\u0006\u0002\u0002\u0002A!\u00111AA\u0005\u001b\t\t)AC\u0002\u0002\ba\u000bA![7qY&!\u00111BA\u0003\u0005Ya\u0015N\\3beR\u0013\u0018M^3sg\u0006d')^5mI\u0016\u0014\u0018!\u0005;sCZ,'o]1m\u0005VLG\u000eZ3sA\u0005)1\u000f[1qKV\t10\u0001\u0004tQ\u0006\u0004X\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\r\u0005e\u00111DA\u000f!\u00111\u0007!\u001b;\t\ry,\u0001\u0019AA\u0001\u0011\u0019\ty!\u0002a\u0001w\n!!+\u001a9s+\u0011\t\u0019#a\n\u0011\r\u0019\u0004\u0011QEA\u0016!\rQ\u0017q\u0005\u0003\b\u0003S1AQ1\u0001n\u0005\u0005y%f\u0001;\u0002.-\u0012\u0011q\u0006\t\u0005\u0003c\tY$\u0004\u0002\u00024)!\u0011QGA\u001c\u0003%)hn\u00195fG.,GMC\u0002\u0002:\u0005\f!\"\u00198o_R\fG/[8o\u0013\u0011\ti$a\r\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cWMA\u0004SKB\u0014X*\u0019;\u0016\r\u0005\r\u0013qIA&!\u00191\u0007!!\u0012\u0002JA\u0019!.a\u0012\u0005\u000f\u0005%r\u0001\"b\u0001[B\u0019!.a\u0013\u0005\u000f\u00055s\u0001\"b\u0001[\n\tQJ\u0001\u0004DY>\u001cX\r\u001a\t\u0006M\u0006M\u00131F\u0005\u0004\u0003+2&!\u0004*v]:\f'\r\\3He\u0006\u0004\bNA\u0005DY>\u001cX\rZ'biV!\u00111LA0!\u00151\u00171KA/!\rQ\u0017q\f\u0003\b\u0003\u001bJAQ1\u0001n\u0003!!xn\u0015;sS:<GCAA3!\u0011\t9'!\u001e\u000f\t\u0005%\u0014\u0011\u000f\t\u0004\u0003W\nWBAA7\u0015\r\ty\u0007X\u0001\u0007yI|w\u000e\u001e \n\u0007\u0005M\u0014-\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003o\nIH\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003g\n\u0017a\u0001<jCV1\u0011qPAD\u00033#B!!!\u0002\fB)\u00111\u0011\u0004\u0002\u00066\t\u0001\u0001E\u0002k\u0003\u000f#a!!#\f\u0005\u0004i'!\u0001+\t\u000f\u000555\u00021\u0001\u0002\u0010\u0006!a\r\\8x!\u0019A\u00180!%\u0002\u0018B1\u00010a%j\u0003\u000bK1!!&Y\u0005%1En\\<TQ\u0006\u0004X\rE\u0002k\u00033#a!a'\f\u0005\u0004i'\u0001B'biJ\naA^5b\u001b\u0006$X\u0003CAQ\u0003S\u000bi,!,\u0015\t\u0005\r\u0016q\u0018\u000b\u0005\u0003K\u000b\t\f\u0005\u0004g\u0001\u0005\u001d\u00161\u0016\t\u0004U\u0006%FABAE\u0019\t\u0007Q\u000eE\u0002k\u0003[#a!a,\r\u0005\u0004i'\u0001B'biNBq!a-\r\u0001\u0004\t),A\u0004d_6\u0014\u0017N\\3\u0011\u0011\u0001\f9\f^A^\u0003WK1!!/b\u0005%1UO\\2uS>t'\u0007E\u0002k\u0003{#a!a'\r\u0005\u0004i\u0007bBAG\u0019\u0001\u0007\u0011\u0011\u0019\t\u0007qf\f\u0019-a/\u0011\ra\f\u0019*[AT\u0003\t!x.\u0006\u0003\u0002J\u0006mG\u0003BAf\u0003\u001b\u0004BAZA*i\"9\u0011qZ\u0007A\u0002\u0005E\u0017\u0001B:j].\u0004b\u0001_=\u0002T\u0006e\u0007\u0003\u0002=\u0002V&L1!a6Y\u0005%\u0019\u0016N\\6TQ\u0006\u0004X\rE\u0002k\u00037$a!a'\u000e\u0005\u0004i\u0017!\u0002;p\u001b\u0006$XCBAq\u0003c\fI\u000f\u0006\u0003\u0002d\u0006MH\u0003BAs\u0003W\u0004RAZA*\u0003O\u00042A[Au\t\u0019\tyK\u0004b\u0001[\"9\u00111\u0017\bA\u0002\u00055\b\u0003\u00031\u00028R\fy/a:\u0011\u0007)\f\t\u0010\u0002\u0004\u0002\u001c:\u0011\r!\u001c\u0005\b\u0003\u001ft\u0001\u0019AA{!\u0019A\u00180a5\u0002p\u0006!R.\u00199NCR,'/[1mSj,GMV1mk\u0016,B!a?\u0003\u0002Q!\u0011Q B\u0002!\u0019\t\u0019iB5\u0002\u0000B\u0019!N!\u0001\u0005\r\u0005muB1\u0001n\u0011\u001d\u0011)a\u0004a\u0001\u0005\u000f\t\u0011A\u001a\t\u0007A\n%A/a@\n\u0007\t-\u0011MA\u0005Gk:\u001cG/[8oc\u0005q\u0001O]3NCR,'/[1mSj,GC\u0001B\t)\u0011\u0011\u0019Ba\t\u0011\r\u0001\u0014)\u0002\u001eB\r\u0013\r\u00119\"\u0019\u0002\u0007)V\u0004H.\u001a\u001a\u0011\r\u0005\ru!\u001bB\u000e!\u0011\u0011iBa\b\u000e\u0003iK1A!\t[\u0005\u001dqu\u000e^+tK\u0012DqA!\n\u0011\u0001\b\u00119#\u0001\u0007nCR,'/[1mSj,'\u000fE\u0002y\u0005SI1Aa\u000bY\u00051i\u0015\r^3sS\u0006d\u0017N_3s\u0003\r\u0011XO\u001c\u000b\u0003\u0005c!BAa\r\u0003FA1!Q\u0007B\u001e\u0005\u007fi!Aa\u000e\u000b\u0007\te\u0012-\u0001\u0006d_:\u001cWO\u001d:f]RLAA!\u0010\u00038\t1a)\u001e;ve\u0016\u0004BA!\b\u0003B%\u0019!1\t.\u0003\t\u0011{g.\u001a\u0005\b\u0005K\t\u00029\u0001B\u0014\u0003\u001d\u0011XO\\,ji\",BAa\u0013\u0003RQ!!Q\nB+)\u0011\u0011yEa\u0015\u0011\u0007)\u0014\t\u0006\u0002\u0004\u0002\u001cJ\u0011\r!\u001c\u0005\b\u0005K\u0011\u00029\u0001B\u0014\u0011\u001d\tyM\u0005a\u0001\u0005/\u0002b\u0001_=\u0002T\n=\u0013a\u0002:v]\u001a{G\u000eZ\u000b\u0005\u0005;\u00129\u0007\u0006\u0003\u0003`\tED\u0003\u0002B1\u0005[\"BAa\u0019\u0003lA1!Q\u0007B\u001e\u0005K\u00022A\u001bB4\t\u0019\u0011Ig\u0005b\u0001[\n\tQ\u000bC\u0004\u0003&M\u0001\u001dAa\n\t\u000f\t\u00151\u00031\u0001\u0003pAA\u0001-a.\u0003f%\u0014)\u0007C\u0004\u0003tM\u0001\rA!\u001a\u0002\ti,'o\\\u0001\reVtgi\u001c7e\u0003NLhnY\u000b\u0005\u0005s\u0012\u0019\t\u0006\u0003\u0003|\t-E\u0003\u0002B?\u0005\u000f#BAa \u0003\u0006B1!Q\u0007B\u001e\u0005\u0003\u00032A\u001bBB\t\u0019\u0011I\u0007\u0006b\u0001[\"9!Q\u0005\u000bA\u0004\t\u001d\u0002b\u0002B\u0003)\u0001\u0007!\u0011\u0012\t\tA\u0006]&\u0011Q5\u0003\u0000!9!1\u000f\u000bA\u0002\t\u0005\u0015!\u0003:v]J+G-^2f+\u0011\u0011\tJ!'\u0015\t\tM%q\u0014\u000b\u0005\u0005+\u0013i\n\u0005\u0004\u00036\tm\"q\u0013\t\u0004U\neEa\u0002B5+\t\u0007!1T\t\u0003SFDqA!\n\u0016\u0001\b\u00119\u0003C\u0004\u0003\u0006U\u0001\rA!)\u0011\u0013\u0001\f9La&\u0003\u0018\n]\u0015A\u0003:v]\u001a{'/Z1dQR!!q\u0015BV)\u0011\u0011\u0019D!+\t\u000f\t\u0015b\u0003q\u0001\u0003(!9!Q\u0001\fA\u0002\t5\u0006C\u00021\u0003\n%\u0014y\u000bE\u0002a\u0005cK1Aa-b\u0005\u0011)f.\u001b;\u0002\u001d]LG\u000f[!uiJL'-\u001e;fgR!!\u0011\u0018B^!\u0011\t\u0019IB5\t\u000f\tuv\u00031\u0001\u0003@\u0006!\u0011\r\u001e;s!\rA(\u0011Y\u0005\u0004\u0005\u0007D&AC!uiJL'-\u001e;fg\u0006i\u0011\r\u001a3BiR\u0014\u0018NY;uKN$BA!/\u0003J\"9!Q\u0018\rA\u0002\t}\u0016!\u00028b[\u0016$G\u0003\u0002B]\u0005\u001fDqA!5\u001a\u0001\u0004\t)'\u0001\u0003oC6,\u0017!B1ts:\u001cWC\u0001B])\u0011\u0011IL!7\t\u000f\tm7\u00041\u0001\u0002f\u0005QA-[:qCR\u001c\u0007.\u001a:\u0015\r\te&q\u001cBq\u0011\u001d\u0011Y\u000e\ba\u0001\u0003KBqAa9\u001d\u0001\u0004\u0011)/A\bj]B,HOQ;gM\u0016\u00148+\u001b>f!\r\u0001'q]\u0005\u0004\u0005S\f'aA%oi\u00061\u0011m\u001d&bm\u0006,\"Aa<\u0011\u0011\tE(q\u001fB}\u0003Wi!Aa=\u000b\u0007\tU\b,A\u0004kCZ\fGm\u001d7\n\u0007U\u0013\u0019PK\u0002j\u0003[\t1#Y:T_V\u00148-Z,ji\"\u001cuN\u001c;fqR,BAa@\u0004\nQ!1\u0011AB\u0007!\u001d171A5\u0004\bQL1a!\u0002W\u0005E\u0019v.\u001e:dK^KG\u000f[\"p]R,\u0007\u0010\u001e\t\u0004U\u000e%AABB\u0006=\t\u0007QNA\u0002DibDqA!\u0002\u001f\u0001\u0004\u0019y\u0001\u0005\u0004a\u0005\u0013I7qA\u0001\u000eO\u0016$\u0018\t\u001e;sS\n,H/Z:\u0016\u0005\t}\u0016AB*pkJ\u001cW\r\u0005\u0002gCM\u0011\u0011e\u0018\u000b\u0003\u0007/)Baa\b\u0004&Q!1\u0011EB\u0014!\u0011AHpa\t\u0011\u0007)\u001c)\u0003\u0002\u0004\u0002\n\u000e\u0012\r!\u001c\u0005\b\u0005#\u001c\u0003\u0019AA3\u000351'o\\7Qk\nd\u0017n\u001d5feV!1QFB\u001a)\u0011\u0019yc!\u000e\u0011\r\u0019\u00041\u0011\u0007B\u000e!\rQ71\u0007\u0003\u0007\u0003\u0013##\u0019A7\t\u000f\r]B\u00051\u0001\u0004:\u0005I\u0001/\u001e2mSNDWM\u001d\t\u0007\u0007w\u0019)e!\r\u000e\u0005\ru\"\u0002BB \u0007\u0003\nqB]3bGRLg/Z:ue\u0016\fWn\u001d\u0006\u0003\u0007\u0007\n1a\u001c:h\u0013\u0011\u00199e!\u0010\u0003\u0013A+(\r\\5tQ\u0016\u0014\u0018\u0001\u00044s_6LE/\u001a:bi>\u0014X\u0003BB'\u0007'\"Baa\u0014\u0004VA1a\rAB)\u00057\u00012A[B*\t\u0019\tI)\nb\u0001[\"9!QA\u0013A\u0002\r]\u0003#\u00021\u0004Z\ru\u0013bAB.C\nIa)\u001e8di&|g\u000e\r\t\u0007\u0007?\u001aIg!\u0015\u000f\t\r\u00054Q\r\b\u0005\u0003W\u001a\u0019'C\u0001c\u0013\r\u00199'Y\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\u0019Yg!\u001c\u0003\u0011%#XM]1u_JT1aa\u001ab\u000391'o\\7KCZ\f7\u000b\u001e:fC6,baa\u001d\u0004z\rME\u0003BB;\u0007w\u0002bA\u001a\u0001\u0004x\tm\u0001c\u00016\u0004z\u00111\u0011\u0011\u0012\u0014C\u00025Da!\u0017\u0014A\u0002\ru\u0004#\u00021\u0004Z\r}\u0004\u0003CBA\u0007\u001b\u001b9h!%\u000e\u0005\r\r%bA-\u0004\u0006*!1qQBE\u0003\u0011)H/\u001b7\u000b\u0005\r-\u0015\u0001\u00026bm\u0006LAaa$\u0004\u0004\nQ!)Y:f'R\u0014X-Y7\u0011\u0007)\u001c\u0019\nB\u0004\u0004\u0016\u001a\u0012\raa&\u0003\u0003M\u000b2A\\B@\u0003\u0015\u0019\u0017p\u00197f+\u0011\u0019ija)\u0015\t\r}5Q\u0015\t\u0007M\u0002\u0019\tKa\u0007\u0011\u0007)\u001c\u0019\u000b\u0002\u0004\u0002\n\u001e\u0012\r!\u001c\u0005\b\u0005\u000b9\u0003\u0019ABT!\u0015\u00017\u0011LBU!\u0019\u0019yf!\u001b\u0004\"\u0006IaM]8n\u000fJ\f\u0007\u000f[\u000b\u0007\u0007_\u001b)l!/\u0015\t\rE61\u0018\t\u0007M\u0002\u0019\u0019la.\u0011\u0007)\u001c)\f\u0002\u0004\u0002\n\"\u0012\r!\u001c\t\u0004U\u000eeFABA'Q\t\u0007Q\u000eC\u0004\u0004>\"\u0002\raa0\u0002\u0003\u001d\u0004b\u0001_=\u0004B\u000e]\u0006\u0003\u0002=}\u0007g\u000b\u0001C\u001a:p[6\u000bG/\u001a:jC2L'0\u001a:\u0016\r\r\u001d7QZBj)\u0011\u0019Im!6\u0011\r\u0019\u000411ZBh!\rQ7Q\u001a\u0003\u0007\u0003\u0013K#\u0019A7\u0011\r\tU\"1HBi!\rQ71\u001b\u0003\u0007\u0003\u001bJ#\u0019A7\t\u000f\r]\u0017\u00061\u0001\u0004Z\u00069a-Y2u_JL\b#\u00031\u00028\n\u001d\"qXBn!\u00191\u0007aa3\u0004R\u0006)1/\u001a;vaV11\u0011]Bt\u0007[$Baa9\u0004pB1a\rABs\u0007S\u00042A[Bt\t\u0019\tII\u000bb\u0001[B1!Q\u0007B\u001e\u0007W\u00042A[Bw\t\u0019\tiE\u000bb\u0001[\"91q\u001b\u0016A\u0002\rE\b#\u00031\u00028\u000eM(qXB}!\rA8Q_\u0005\u0004\u0007oD&!E!di>\u0014X*\u0019;fe&\fG.\u001b>feB1a\rABs\u0007WD3BKB\u007f\t\u0007!)\u0001\"\u0003\u0005\fA\u0019\u0001ma@\n\u0007\u0011\u0005\u0011M\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-\t\u0002\u0005\b\u0005qRk]3!O\u0019\u0014x.\\'bi\u0016\u0014\u0018.\u00197ju\u0016\u0014x\u0005I5ogR,\u0017\rZ\u0001\u0006g&t7-Z\u0011\u0003\t\u001b\tQA\r\u00187]A\nQ!\u00199qYf,B\u0001b\u0005\u0005\u001aQ!AQ\u0003C\u000e!\u00191\u0007\u0001b\u0006\u0003\u001cA\u0019!\u000e\"\u0007\u0005\r\u0005%5F1\u0001n\u0011\u001d!ib\u000ba\u0001\t?\t\u0001\"\u001b;fe\u0006\u0014G.\u001a\t\u0007\tC!Y\u0003b\u0006\u000e\u0005\u0011\r\"\u0002\u0002C\u0013\tO\t\u0011\"[7nkR\f'\r\\3\u000b\u0007\u0011%\u0012-\u0001\u0006d_2dWm\u0019;j_:LA\u0001\"\f\u0005$\tA\u0011\n^3sC\ndW-\u0001\u0006ge>lg)\u001e;ve\u0016,B\u0001b\r\u0005:Q!AQ\u0007C\u001e!\u00191\u0007\u0001b\u000e\u0003\u001cA\u0019!\u000e\"\u000f\u0005\r\u0005%EF1\u0001n\u0011\u001d!i\u0004\fa\u0001\t\u007f\taAZ;ukJ,\u0007C\u0002B\u001b\u0005w!9\u0004K\u0006-\u0007{$\u0019\u0001b\u0011\u0005\n\u0011-\u0011E\u0001C#\u0003m)6/\u001a\u0011('>,(oY3/MV$XO]3(A%t7\u000f^3bI\u0006\u0019bM]8n\u0007>l\u0007\u000f\\3uS>t7\u000b^1hKV!A1\nC))\u0011!i\u0005b\u0015\u0011\r\u0019\u0004Aq\nB\u000e!\rQG\u0011\u000b\u0003\u0007\u0003\u0013k#\u0019A7\t\u000f\u0011uR\u00061\u0001\u0005VA1Aq\u000bC.\t\u001fj!\u0001\"\u0017\u000b\t\te2QQ\u0005\u0005\t;\"IFA\bD_6\u0004H.\u001a;j_:\u001cF/Y4fQ-i3Q C\u0002\tC\"I\u0001b\u0003\"\u0005\u0011\r\u0014\u0001J+tK\u0002:3k\\;sG\u0016t3m\\7qY\u0016$\u0018n\u001c8Ti\u0006<Wm\n\u0011j]N$X-\u00193\u0002!\u0019\u0014x.\u001c$viV\u0014XmU8ve\u000e,WC\u0002C5\t_\")\b\u0006\u0003\u0005l\u0011]\u0004C\u00024\u0001\t[\"\t\bE\u0002k\t_\"a!!#/\u0005\u0004i\u0007C\u0002B\u001b\u0005w!\u0019\bE\u0002k\tk\"a!!\u0014/\u0005\u0004i\u0007b\u0002C\u001f]\u0001\u0007A\u0011\u0010\t\u0007\u0005k\u0011Y\u0004b\u001f\u0011\raLHQ\u0010C:!\u0011AH\u0010\"\u001c)\u00179\u001ai\u0010b\u0001\u0005\u0002\u0012%A1B\u0011\u0003\t\u0007\u000b\u0001+V:fA\u001d\u001av.\u001e:dK:2W\u000f^;sKN{WO]2fO\u0001B\u0003o\u001c;f]RL\u0017\r\u001c7zAQ|w-\u001a;iKJ\u0004s/\u001b;iA\u0001\u001cv.\u001e:dK:2'o\\7He\u0006\u0004\b\u000eY\u0015!S:\u001cH/Z1e\u0003e1'o\\7T_V\u00148-Z\"p[BdW\r^5p]N#\u0018mZ3\u0016\r\u0011%Eq\u0012CK)\u0011!Y\tb&\u0011\r\u0019\u0004AQ\u0012CI!\rQGq\u0012\u0003\u0007\u0003\u0013{#\u0019A7\u0011\r\u0011]C1\fCJ!\rQGQ\u0013\u0003\u0007\u0003\u001bz#\u0019A7\t\u000f\u0011eu\u00061\u0001\u0005\u001c\u0006Q1m\\7qY\u0016$\u0018n\u001c81\t\u0011uE\u0011\u0015\t\u0007\t/\"Y\u0006b(\u0011\u0007)$\t\u000b\u0002\u0007\u0005$\u0012]\u0015\u0011!A\u0001\u0006\u0003!)KA\u0002`IE\n2A\u001cCT!\u0019A\u0018\u0010\"+\u0005\u0014B!\u0001\u0010 CGQ-y3Q C\u0002\t[#I\u0001b\u0003\"\u0005\u0011=\u0016AV+tK\u0002\u001a8-\u00197b[\r|W\u000e]1uA\r{W\u000e\u001d7fi&|gn\u0015;bO\u0016\u0004Co\u001c\u0011gkR,(/\u001a\u0011d_:4XM\u001d;fe\u0002\ng\u000e\u001a\u0011('>,(oY3/MV$XO]3T_V\u00148-Z\u0014!S:\u001cH/Z1e\u0003\u0011!\u0018nY6\u0016\t\u0011UF1\u0018\u000b\t\to#I\r\"7\u0005^B1a\r\u0001C]\t{\u00032A\u001bC^\t\u0019\tI\t\rb\u0001[B!Aq\u0018Cc\u001b\t!\tMC\u0002\u0005Dj\u000bQ!Y2u_JLA\u0001b2\u0005B\nY1)\u00198dK2d\u0017M\u00197f\u0011\u001d!Y\r\ra\u0001\t\u001b\fA\"\u001b8ji&\fG\u000eR3mCf\u0004B\u0001b4\u0005V6\u0011A\u0011\u001b\u0006\u0005\t'\u00149$\u0001\u0005ekJ\fG/[8o\u0013\u0011!9\u000e\"5\u0003\u001d\u0019Kg.\u001b;f\tV\u0014\u0018\r^5p]\"9A1\u001c\u0019A\u0002\u00115\u0017\u0001C5oi\u0016\u0014h/\u00197\t\u000f\u0011E\u0006\u00071\u0001\u0005:\u000611/\u001b8hY\u0016,B\u0001b9\u0005jR!AQ\u001dCv!\u00191\u0007\u0001b:\u0003\u001cA\u0019!\u000e\";\u0005\r\u0005%\u0015G1\u0001n\u0011\u001d!i/\ra\u0001\tO\fq!\u001a7f[\u0016tG/\u0001\u0004sKB,\u0017\r^\u000b\u0005\tg$I\u0010\u0006\u0003\u0005v\u0012m\bC\u00024\u0001\to\u0014Y\u0002E\u0002k\ts$a!!#3\u0005\u0004i\u0007b\u0002Cwe\u0001\u0007Aq_\u0001\u0007k:4w\u000e\u001c3\u0016\r\u0015\u0005Q1CC\u0005)\u0011)\u0019!\"\b\u0015\t\u0015\u0015QQ\u0002\t\u0007M\u0002)9Aa\u0007\u0011\u0007),I\u0001\u0002\u0004\u0006\fM\u0012\r!\u001c\u0002\u0002\u000b\"9!QA\u001aA\u0002\u0015=\u0001c\u00021\u0003\n\u0015EQQ\u0003\t\u0004U\u0016MAABBKg\t\u0007Q\u000eE\u0003a\u000b/)Y\"C\u0002\u0006\u001a\u0005\u0014aa\u00149uS>t\u0007c\u00021\u0003\u0016\u0015EQq\u0001\u0005\b\u000b?\u0019\u0004\u0019AC\t\u0003\u0005\u0019\u0018aC;oM>dG-Q:z]\u000e,b!\"\n\u00066\u00155B\u0003BC\u0014\u000b{!B!\"\u000b\u00060A1a\rAC\u0016\u00057\u00012A[C\u0017\t\u0019)Y\u0001\u000eb\u0001[\"9!Q\u0001\u001bA\u0002\u0015E\u0002c\u00021\u0003\n\u0015MRq\u0007\t\u0004U\u0016UBABBKi\t\u0007Q\u000e\u0005\u0004\u00036\tmR\u0011\b\t\u0006A\u0016]Q1\b\t\bA\nUQ1GC\u0016\u0011\u001d)y\u0002\u000ea\u0001\u000bg\tQ!Z7qif,B!b\u0011\u0006JU\u0011QQ\t\t\u0007M\u0002)9Ea\u0007\u0011\u0007),I\u0005\u0002\u0004\u0002\nV\u0012\r!\\\u0001\u0007?\u0016l\u0007\u000f^=\u0011\u000b\u0019\u0004aNa\u0007\u0002\u000b5\f\u0017PY3\u0016\t\u0015MS\u0011L\u000b\u0003\u000b+\u0002bA\u001a\u0001\u0006X\u0015m\u0003c\u00016\u0006Z\u00111\u0011\u0011R\u001cC\u00025\u0004bA!\u000e\u0006^\u0015\u0005\u0014\u0002BC0\u0005o\u0011q\u0001\u0015:p[&\u001cX\rE\u0003a\u000b/)9&\u0001\u0004gC&dW\rZ\u000b\u0005\u000bO*i\u0007\u0006\u0003\u0006j\u0015=\u0004C\u00024\u0001\u000bW\u0012Y\u0002E\u0002k\u000b[\"a!!#9\u0005\u0004i\u0007bBC9q\u0001\u0007Q1O\u0001\u0006G\u0006,8/\u001a\t\u0005\u0007?*)(\u0003\u0003\u0006x\r5$!\u0003+ie><\u0018M\u00197f\u0003\u0019a\u0017M_5msV1QQPCB\u000b\u0013#B!b \u0006\fB1a\rACA\u000b\u000b\u00032A[CB\t\u0019\tI)\u000fb\u0001[B1!Q\u0007B\u001e\u000b\u000f\u00032A[CE\t\u0019\ti%\u000fb\u0001[\"9QQR\u001dA\u0002\u0015=\u0015AB2sK\u0006$X\rE\u0003a\u00073*\t\n\u0005\u0004g\u0001\u0015\u0005Uq\u0011\u0015\fs\ruH1ACK\t\u0013!Y!\t\u0002\u0006\u0018\u0006yRk]3!OM{WO]2f]1\f'0_*pkJ\u001cWm\n\u0011j]N$X-\u00193\u0002\u00171\f'0\u001b7z\u0003NLhnY\u000b\u0005\u000b;+\u0019\u000b\u0006\u0003\u0006 \u0016\u001d\u0006C\u00024\u0001\u000bC+)\u000bE\u0002k\u000bG#a!!#;\u0005\u0004i\u0007C\u0002B\u001b\u0005w\u0011Y\u0002C\u0004\u0006\u000ej\u0002\r!\"+\u0011\u000b\u0001\u001cI&b+\u0011\r\tU\"1HCQQ-Q4Q C\u0002\u000b_#I\u0001b\u0003\"\u0005\u0015E\u0016aH+tK\u0002:3k\\;sG\u0016tC.\u0019>z\rV$XO]3(A%t7\u000f^3bIV!QQWC^)\u0011)9,\"0\u0011\r\u0019\u0004Q\u0011\u0018B\u000e!\rQW1\u0018\u0003\u0007\u0003\u0013[$\u0019A7\t\u000f\u0015}6\b1\u0001\u0006B\u0006ia-\u001e;ve\u0016,E.Z7f]R\u0004bA!\u000e\u0003<\u0015e\u0016!\u00028fm\u0016\u0014X\u0003BCd\u000b\u001b,\"!\"3\u0011\r\u0019\u0004Q1\u001aB\u000e!\rQWQ\u001a\u0003\u0007\u0003\u0013c$\u0019A7\u0002\r}sWM^3s\u0003=\u0019w.\u001c9mKRLwN\\*uC\u001e,W\u0003BCk\u000b7$B!b6\u0006^B1a\rACm\u00057\u00012A[Cn\t\u0019\tII\u0010b\u0001[\"9Q\u0011\u001b A\u0002\u0015}\u0007C\u0002C,\t7*I.\u0001\u0007gkR,(/Z*pkJ\u001cW-\u0006\u0004\u0006f\u0016-X\u0011\u001f\u000b\u0005\u000bO,\u0019\u0010\u0005\u0004g\u0001\u0015%XQ\u001e\t\u0004U\u0016-HABAE\u007f\t\u0007Q\u000e\u0005\u0004\u00036\tmRq\u001e\t\u0004U\u0016EHABA'\u007f\t\u0007Q\u000eC\u0004\u0006b~\u0002\r!\">\u0011\r\tU\"1HC|!\u00191\u0007!\";\u0006p\u0006QA.\u0019>z'&tw\r\\3\u0016\t\u0015uh1\u0001\u000b\u0005\u000b\u007f4)\u0001\u0005\u0004g\u0001\u0019\u0005!1\u0004\t\u0004U\u001a\rAABAE\u0001\n\u0007Q\u000eC\u0004\u0006\u000e\u0002\u0003\rAb\u0002\u0011\u000b\u0001\u001cIF\"\u0001\u0002\u00151\f'0\u001f$viV\u0014X-\u0006\u0003\u0007\u000e\u0019MA\u0003\u0002D\b\r+\u0001bA\u001a\u0001\u0007\u0012\tm\u0001c\u00016\u0007\u0014\u00111\u0011\u0011R!C\u00025Dq!\"$B\u0001\u000419\u0002E\u0003a\u000732I\u0002\u0005\u0004\u00036\tmb\u0011C\u0001\u000bY\u0006T\u0018pU8ve\u000e,WC\u0002D\u0010\rK1Y\u0003\u0006\u0003\u0007\"\u00195\u0002C\u00024\u0001\rG19\u0003E\u0002k\rK!a!!#C\u0005\u0004i\u0007C\u0002B\u001b\u0005w1I\u0003E\u0002k\rW!a!!\u0014C\u0005\u0004i\u0007bBCG\u0005\u0002\u0007aq\u0006\t\u0006A\u000eec\u0011\u0007\t\u0007M\u00021\u0019C\"\u000b\u0002!1\f'0\u001f$viV\u0014XmU8ve\u000e,WC\u0002D\u001c\r{1\u0019\u0005\u0006\u0003\u0007:\u0019\u0015\u0003C\u00024\u0001\rw1y\u0004E\u0002k\r{!a!!#D\u0005\u0004i\u0007C\u0002B\u001b\u0005w1\t\u0005E\u0002k\r\u0007\"a!!\u0014D\u0005\u0004i\u0007bBCG\u0007\u0002\u0007aq\t\t\u0006A\u000eec\u0011\n\t\u0007\u0005k\u0011YDb\u0013\u0011\r\u0019\u0004a1\bD!\u00031\t7oU;cg\u000e\u0014\u0018NY3s+\u00111\tFb\u0016\u0016\u0005\u0019M\u0003C\u00024\u0001\r+2I\u0006E\u0002k\r/\"a!!#E\u0005\u0004i\u0007CBB\u001e\r72)&\u0003\u0003\u0007^\ru\"AC*vEN\u001c'/\u001b2fe\u0006A\u0011m\u0019;peJ+g-\u0006\u0003\u0007d\u0019%DC\u0003D3\rc2\tIb\"\u0007\fB1a\r\u0001D4\rW\u00022A\u001bD5\t\u0019\tI)\u0012b\u0001[B!Aq\u0018D7\u0013\u00111y\u0007\"1\u0003\u0011\u0005\u001bGo\u001c:SK\u001aDqAb\u001dF\u0001\u00041)(A\td_6\u0004H.\u001a;j_:l\u0015\r^2iKJ\u0004b\u0001\u0019D<c\u001am\u0014b\u0001D=C\ny\u0001+\u0019:uS\u0006dg)\u001e8di&|g\u000eE\u0002y\r{J1Ab Y\u0005I\u0019u.\u001c9mKRLwN\\*ue\u0006$XmZ=\t\u000f\u0019\rU\t1\u0001\u0007\u0006\u0006qa-Y5mkJ,W*\u0019;dQ\u0016\u0014\bC\u00021\u0007xE,\u0019\bC\u0004\u0007\n\u0016\u0003\rA!:\u0002\u0015\t,hMZ3s'&TX\rC\u0004\u0007\u000e\u0016\u0003\rAb$\u0002!=4XM\u001d4m_^\u001cFO]1uK\u001eL\bc\u0001=\u0007\u0012&\u0019a1\u0013-\u0003!=3XM\u001d4m_^\u001cFO]1uK\u001eLX\u0003\u0002DL\r;#bA\"'\u0007 \u001a\u0005\u0006C\u00024\u0001\r73Y\u0007E\u0002k\r;#a!!#G\u0005\u0004i\u0007b\u0002DE\r\u0002\u0007!Q\u001d\u0005\b\r\u001b3\u0005\u0019\u0001DHQ-15Q C\u0002\rK#I\u0001b\u0003\"\u0005\u0019\u001d\u0016!P+tK\u00022\u0018M]5b]R\u0004\u0013mY2faRLgn\u001a\u0011d_6\u0004H.\u001a;j_:\u0004\u0013M\u001c3!M\u0006LG.\u001e:fA5\fGo\u00195feN\u0004\u0013N\\:uK\u0006$\u0017aD1di>\u0014(+\u001a4XSRD\u0017iY6\u0016\t\u00195f1\u0017\u000b\u000b\r_3)Lb/\u0007@\u001a\u0005\u0007C\u00024\u0001\rc3Y\u0007E\u0002k\rg#a!!#H\u0005\u0004i\u0007b\u0002D\\\u000f\u0002\u0007a\u0011X\u0001\u0006C\u000e\\Gk\u001c\t\u0006A\u0016]a1\u000e\u0005\u0007\r{;\u0005\u0019A9\u0002\u0015\u0005\u001c7.T3tg\u0006<W\rC\u0004\u0007t\u001d\u0003\rA\"\u001e\t\u000f\u0019\ru\t1\u0001\u0007\u0006\"\u001aqI\"2\u0011\t\u0019\u001dg1Z\u0007\u0003\r\u0013T1!!\u000f[\u0013\u00111iM\"3\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\u0019C\u000e$xN\u001d*fM^KG\u000f\u001b\"bG.\u0004(/Z:tkJ,W\u0003\u0002Dj\r3$\u0002B\"6\u0007\\\u001augq\u001c\t\u0007M\u000219Nb\u001b\u0011\u0007)4I\u000e\u0002\u0004\u0002\n\"\u0013\r!\u001c\u0005\u0007\r{C\u0005\u0019A9\t\u000f\u0019M\u0004\n1\u0001\u0007v!9a1\u0011%A\u0002\u0019\u0015U\u0003\u0002Dr\rS$BA\":\u0007lB1a\r\u0001Dt\rW\u00022A\u001bDu\t\u0019\tI)\u0013b\u0001[\"1aQX%A\u0002ED3\"SB\u007f\t\u00071y\u000f\"\u0003\u0005\f\u0005\u0012a\u0011_\u0001O+N,\u0007%Y2u_J\u0014VMZ,ji\"\u0014\u0015mY6qe\u0016\u001c8/\u001e:fA\u0005\u001c7-\u001a9uS:<\u0007eY8na2,G/[8oA\u0005tG\r\t4bS2,(/\u001a\u0011nCR\u001c\u0007.\u001a:tA%t7\u000f^3bIV1aQ_D\b\r{$\u0002Bb>\b>\u001d-s\u0011\f\u000b\u0005\rs4y\u0010\u0005\u0004g\u0001\u0019m(1\u0004\t\u0004U\u001auHA\u0002B5\u0015\n\u0007Q\u000eC\u0004\b\u0002)\u0003\rab\u0001\u0002\u001b\u0019\fg.\u00138TiJ\fG/Z4z!\u001d\u0001'\u0011\u0002Bs\u000f\u000b\u0001b\u0001_=\b\b\tm\u0001c\u0002=\b\n\u001d5a1`\u0005\u0004\u000f\u0017A&!E+oS\u001a|'/\u001c$b]&s7\u000b[1qKB\u0019!nb\u0004\u0005\r\u0005%%J1\u0001nQ\u00191ypb\u0005\b\u001aA\u0019\u0001m\"\u0006\n\u0007\u001d]\u0011M\u0001\beKB\u0014XmY1uK\u0012t\u0015-\\32\u000f}9Yb\"\t\b6A\u0019\u0001m\"\b\n\u0007\u001d}\u0011M\u0001\u0004Ts6\u0014w\u000e\\\u0019\nG\u001d\rrqED\u0017\t\u001f!Bab\u0007\b&!9!\u0011\u001b/A\u0002\u0005\u0015\u0014\u0002\u0002C\b\u000fSQ1ab\u000bb\u0003\u0019\u0019\u00160\u001c2pYFJ1eb\f\b2\u001dMr1\u0006\b\u0005\u0007C:\t$C\u0002\b,\u0005\fd\u0001JB1\u0007G\u0012\u0017'B\u0013\b8\u001derBAD\u001dC\t9Y$\u0001\u0005tiJ\fG/Z4z\u0011\u001d9yD\u0013a\u0001\u000f\u0003\nQAZ5sgR\u0004Dab\u0011\bHA1a\rAD\u0007\u000f\u000b\u00022A[D$\t-9Ie\"\u0010\u0002\u0002\u0003\u0005)\u0011A7\u0003\u0007}##\u0007C\u0004\bN)\u0003\rab\u0014\u0002\rM,7m\u001c8ea\u00119\tf\"\u0016\u0011\r\u0019\u0004qQBD*!\rQwQ\u000b\u0003\f\u000f/:Y%!A\u0001\u0002\u000b\u0005QNA\u0002`IMBqab\u0017K\u0001\u00049i&\u0001\u0003sKN$\b#\u00021\b`\u001d\r\u0014bAD1C\nQAH]3qK\u0006$X\r\u001a 1\t\u001d\u0015t\u0011\u000e\t\u0007M\u00029iab\u001a\u0011\u0007)<I\u0007B\u0006\bl\u001d5\u0014\u0011!A\u0001\u0006\u0003i'aA0%i!9q1\f&A\u0002\u001d=\u0004#\u00021\b`\u001dE\u0004\u0007BD:\u000fS\u0002bA\u001a\u0001\bv\u001d\u001d\u0004c\u00016\b\u0010UAq\u0011PDL\u000f\u0003;Y\t\u0006\u0003\b|\u001deE\u0003BD?\u000f\u001b\u0003bA\u001a\u0001\b\u0000\u001d\r\u0005c\u00016\b\u0002\u00121!\u0011N&C\u00025\u0004b\u0001\"\t\b\u0006\u001e%\u0015\u0002BDD\tG\u00111aU3r!\rQw1\u0012\u0003\u0007\u0003\u001bZ%\u0019A7\t\u000f\u001d\u00051\n1\u0001\b\u0010B9\u0001M!\u0003\u0003f\u001eE\u0005C\u0002=z\u000f'\u0013Y\u0002E\u0004y\u000f\u00139)jb \u0011\u0007)<9\n\u0002\u0004\u0002\n.\u0013\r!\u001c\u0005\b\u000f7[\u0005\u0019ADO\u0003\u001d\u0019x.\u001e:dKN\u0004b\u0001\"\t\b\u0006\u001e}\u0005C\u0002=z\u000fC;I\t\u0005\u0003yy\u001eU\u0015AC2p[\nLg.Z'biVaqqUDj\u000fc;yl\"2\b6R1q\u0011VDq\u000fK$Bab+\bJR!qQVD\\!\u00191\u0007ab,\b4B\u0019!n\"-\u0005\r\t%DJ1\u0001n!\rQwQ\u0017\u0003\u0007\u0003\u001bb%\u0019A7\t\u000f\u001deF\n1\u0001\b<\u0006!Q.\u0019;G!%\u0001\u0017qWD_\u000f\u0007<\u0019\fE\u0002k\u000f\u007f#aa\"1M\u0005\u0004i'AA'2!\rQwQ\u0019\u0003\u0007\u000f\u000fd%\u0019A7\u0003\u00055\u0013\u0004bBD\u0001\u0019\u0002\u0007q1\u001a\t\bA\n%!Q]Dg!\u0019A\u0018pb4\u0003\u001cA9\u0001p\"\u0003\bR\u001e=\u0006c\u00016\bT\u00121\u0011\u0011\u0012'C\u00025Dca\"3\b\u0014\u001d]\u0017gB\u0010\b\u001c\u001dewq\\\u0019\nG\u001d\rrqEDn\t\u001f\t\u0014bID\u0018\u000fc9inb\u000b2\r\u0011\u001a\tga\u0019cc\u0015)sqGD\u001d\u0011\u001d9y\u0004\u0014a\u0001\u000fG\u0004bA\u001a\u0001\bR\u001eu\u0006bBD'\u0019\u0002\u0007qq\u001d\t\u0007M\u00029\tnb1\u0002\tiL\u0007OT\u000b\u0005\u000f[<)\u0010\u0006\u0003\bp\u001e]\bC\u00024\u0001\u000fc\u0014Y\u0002\u0005\u0004\u0005\"\u001d\u0015u1\u001f\t\u0004U\u001eUHABAE\u001b\n\u0007Q\u000eC\u0004\b\u001c6\u0003\ra\"?\u0011\r\u0011\u0005rQQD~a\u00119i\u0010#\u0001\u0011\r\u0019\u0004q1_D\u0000!\rQ\u0007\u0012\u0001\u0003\f\u0011\u0007A)!!A\u0001\u0002\u000b\u0005QNA\u0002`IYBqab'N\u0001\u0004A9\u0001\u0005\u0004\u0005\"\u001d\u0015\u0005\u0012\u0002\u0019\u0005\u0011\u0017A\t\u0001\u0005\u0004g\u0001!5qq \t\u0004U\u001eU\u0018\u0001\u0003>ja^KG\u000f\u001b(\u0016\r!M\u0001r\u0005E\u000e)\u0011A)\u0002#\u000f\u0015\t!]\u0001R\u0004\t\u0007M\u0002AIBa\u0007\u0011\u0007)DY\u0002\u0002\u0004\u0002*9\u0013\r!\u001c\u0005\b\u000f7s\u0005\u0019\u0001E\u0010!\u0019!\tc\"\"\t\"A\"\u00012\u0005E\u0016!\u00191\u0007\u0001#\n\t*A\u0019!\u000ec\n\u0005\r\u0005%eJ1\u0001n!\rQ\u00072\u0006\u0003\f\u0011[Ay#!A\u0001\u0002\u000b\u0005QNA\u0002`I]Bqab'O\u0001\u0004A\t\u0004\u0005\u0004\u0005\"\u001d\u0015\u00052\u0007\u0019\u0005\u0011kAY\u0003\u0005\u0004g\u0001!]\u0002\u0012\u0006\t\u0004U\"\u001d\u0002b\u0002E\u001e\u001d\u0002\u0007\u0001RH\u0001\u0007u&\u0004\b/\u001a:\u0011\u000f\u0001\u0014I\u0001c\u0010\t\u001aA1A\u0011EDC\u0011K\tQ!];fk\u0016,B\u0001#\u0012\tLQ!\u0001r\tE*!\u00191\u0007\u0001#\u0013\tNA\u0019!\u000ec\u0013\u0005\r\u0005%uJ1\u0001n!\u0015A\br\nE%\u0013\rA\t\u0006\u0017\u0002\u0013\u0005>,h\u000eZ3e'>,(oY3Rk\u0016,X\rC\u0004\u0007\n>\u0003\rA!:\u0016\t!]\u0003R\f\u000b\u0007\u00113B)\u0007c\u001a\u0011\r\u0019\u0004\u00012\fE0!\rQ\u0007R\f\u0003\u0007\u0003\u0013\u0003&\u0019A7\u0011\u000b\u0019D\t\u0007c\u0017\n\u0007!\rdKA\fT_V\u00148-Z)vKV,w+\u001b;i\u0007>l\u0007\u000f\\3uK\"9a\u0011\u0012)A\u0002\t\u0015\bb\u0002DG!\u0002\u0007aqR\u000b\u0005\u0011WB\t\b\u0006\u0005\tn!U\u0004r\u000fE=!\u00191\u0007\u0001c\u001c\ttA\u0019!\u000e#\u001d\u0005\r\u0005%\u0015K1\u0001n!\u00151\u0007\u0012\rE8\u0011\u001d1I)\u0015a\u0001\u0005KDqA\"$R\u0001\u00041y\tC\u0004\t|E\u0003\rA!:\u0002'5\f\u0007pQ8oGV\u0014(/\u001a8u\u001f\u001a4WM]:\u0002\u001dUtgm\u001c7e%\u0016\u001cx.\u001e:dKV1\u0001\u0012\u0011ED\u0011\u001f#\u0002\u0002c!\t\n\"E\u0005\u0012\u0014\t\u0007M\u0002A)Ia\u0007\u0011\u0007)D9\t\u0002\u0004\u0002\nJ\u0013\r!\u001c\u0005\b\u000b\u001b\u0013\u0006\u0019\u0001EF!\u0015\u00017\u0011\fEG!\rQ\u0007r\u0012\u0003\u0007\u0007+\u0013&\u0019A7\t\u000f!M%\u000b1\u0001\t\u0016\u0006!!/Z1e!\u001d\u0001'\u0011\u0002EG\u0011/\u0003R\u0001YC\f\u0011\u000bCq\u0001c'S\u0001\u0004Ai*A\u0003dY>\u001cX\rE\u0004a\u0005\u0013AiIa,\u0002'Utgm\u001c7e%\u0016\u001cx.\u001e:dK\u0006\u001b\u0018P\\2\u0016\r!\r\u0006\u0012\u0016EZ)!A)\u000bc+\t6\"u\u0006C\u00024\u0001\u0011O\u0013Y\u0002E\u0002k\u0011S#a!!#T\u0005\u0004i\u0007bBCG'\u0002\u0007\u0001R\u0016\t\u0006A\u000ee\u0003r\u0016\t\u0007\u0005k\u0011Y\u0004#-\u0011\u0007)D\u0019\f\u0002\u0004\u0004\u0016N\u0013\r!\u001c\u0005\b\u0011'\u001b\u0006\u0019\u0001E\\!\u001d\u0001'\u0011\u0002EY\u0011s\u0003bA!\u000e\u0003<!m\u0006#\u00021\u0006\u0018!\u001d\u0006b\u0002EN'\u0002\u0007\u0001r\u0018\t\bA\n%\u0001\u0012\u0017B\u001a\u0003EiWM]4f!JLwN]5uSj,GMT\u000b\u0005\u0011\u000bDY\r\u0006\u0004\tH\"5\u00072\u001e\t\u0007M\u0002AIMa\u0007\u0011\u0007)DY\r\u0002\u0004\u0002\nR\u0013\r!\u001c\u0005\b\u0011\u001f$\u0006\u0019\u0001Ei\u0003Q\u0019x.\u001e:dKN\fe\u000e\u001a)sS>\u0014\u0018\u000e^5fgB1A\u0011EDC\u0011'\u0004r\u0001\u0019B\u000b\u0011+\u0014)\u000f\r\u0003\tX\"m\u0007C\u00024\u0001\u0011\u0013DI\u000eE\u0002k\u00117$1\u0002#8\t`\u0006\u0005\t\u0011!B\u0001[\n\u0019q\f\n\u001d\t\u000f!=G\u000b1\u0001\tbB1A\u0011EDC\u0011G\u0004r\u0001\u0019B\u000b\u0011K\u0014)\u000f\r\u0003\th\"m\u0007C\u00024\u0001\u0011SDI\u000eE\u0002k\u0011\u0017Dq\u0001#<U\u0001\u0004Ay/A\u0007fC\u001e,'oQ8na2,G/\u001a\t\u0004A\"E\u0018b\u0001EzC\n9!i\\8mK\u0006t\u0007")
public final class Source<Out, Mat>
implements FlowOpsMat<Out, Mat>,
Graph<SourceShape<Out>, Mat> {
    private final LinearTraversalBuilder traversalBuilder;
    private final SourceShape<Out> shape;

    public static <T> Source<T, NotUsed> mergePrioritizedN(Seq<Tuple2<Source<T, ?>, Object>> sourcesAndPriorities, boolean eagerComplete) {
        return Source$.MODULE$.mergePrioritizedN(sourcesAndPriorities, eagerComplete);
    }

    public static <T, S> Source<T, NotUsed> unfoldResourceAsync(Function0<Future<S>> create, Function1<S, Future<Option<T>>> read, Function1<S, Future<Done>> close) {
        return Source$.MODULE$.unfoldResourceAsync(create, read, close);
    }

    public static <T, S> Source<T, NotUsed> unfoldResource(Function0<S> create, Function1<S, Option<T>> read, Function1<S, BoxedUnit> close) {
        return Source$.MODULE$.unfoldResource(create, read, close);
    }

    public static <T> Source<T, SourceQueueWithComplete<T>> queue(int bufferSize, OverflowStrategy overflowStrategy, int maxConcurrentOffers) {
        return Source$.MODULE$.queue(bufferSize, overflowStrategy, maxConcurrentOffers);
    }

    public static <T> Source<T, SourceQueueWithComplete<T>> queue(int bufferSize, OverflowStrategy overflowStrategy) {
        return Source$.MODULE$.queue(bufferSize, overflowStrategy);
    }

    public static <T> Source<T, BoundedSourceQueue<T>> queue(int bufferSize) {
        return Source$.MODULE$.queue(bufferSize);
    }

    public static <T, O> Source<O, NotUsed> zipWithN(Function1<Seq<T>, O> zipper, Seq<Source<T, ?>> sources) {
        return Source$.MODULE$.zipWithN(zipper, sources);
    }

    public static <T> Source<Seq<T>, NotUsed> zipN(Seq<Source<T, ?>> sources) {
        return Source$.MODULE$.zipN(sources);
    }

    public static <T, U, M1, M2, M> Source<U, M> combineMat(Source<T, M1> first, Source<T, M2> second, Function1<Object, Graph<UniformFanInShape<T, U>, NotUsed>> fanInStrategy, Function2<M1, M2, M> matF) {
        return Source$.MODULE$.combineMat(first, second, fanInStrategy, matF);
    }

    public static <T, U, M> Source<U, Seq<M>> combine(Seq<Graph<SourceShape<T>, M>> sources, Function1<Object, Graph<UniformFanInShape<T, U>, NotUsed>> fanInStrategy) {
        return Source$.MODULE$.combine(sources, fanInStrategy);
    }

    public static <T, U> Source<U, NotUsed> combine(Source<T, ?> first, Source<T, ?> second, Seq<Source<T, ?>> rest, Function1<Object, Graph<UniformFanInShape<T, U>, NotUsed>> fanInStrategy) {
        return Source$.MODULE$.combine(first, second, rest, fanInStrategy);
    }

    public static <T> Source<T, ActorRef> actorRefWithAck(Object ackMessage) {
        return Source$.MODULE$.actorRefWithAck(ackMessage);
    }

    public static <T> Source<T, ActorRef> actorRefWithBackpressure(Object ackMessage, PartialFunction<Object, CompletionStrategy> completionMatcher, PartialFunction<Object, Throwable> failureMatcher) {
        return Source$.MODULE$.actorRefWithBackpressure(ackMessage, completionMatcher, failureMatcher);
    }

    public static <T> Source<T, ActorRef> actorRef(int bufferSize, OverflowStrategy overflowStrategy) {
        return Source$.MODULE$.actorRef(bufferSize, overflowStrategy);
    }

    public static <T> Source<T, ActorRef> actorRef(PartialFunction<Object, CompletionStrategy> completionMatcher, PartialFunction<Object, Throwable> failureMatcher, int bufferSize, OverflowStrategy overflowStrategy) {
        return Source$.MODULE$.actorRef(completionMatcher, failureMatcher, bufferSize, overflowStrategy);
    }

    public static <T> Source<T, Subscriber<T>> asSubscriber() {
        return Source$.MODULE$.asSubscriber();
    }

    public static <T, M> Source<T, Future<M>> lazyFutureSource(Function0<Future<Source<T, M>>> create) {
        return Source$.MODULE$.lazyFutureSource(create);
    }

    public static <T, M> Source<T, Future<M>> lazySource(Function0<Source<T, M>> create) {
        return Source$.MODULE$.lazySource(create);
    }

    public static <T> Source<T, NotUsed> lazyFuture(Function0<Future<T>> create) {
        return Source$.MODULE$.lazyFuture(create);
    }

    public static <T> Source<T, NotUsed> lazySingle(Function0<T> create) {
        return Source$.MODULE$.lazySingle(create);
    }

    public static <T, M> Source<T, Future<M>> futureSource(Future<Source<T, M>> futureSource) {
        return Source$.MODULE$.futureSource(futureSource);
    }

    public static <T> Source<T, NotUsed> completionStage(CompletionStage<T> completionStage) {
        return Source$.MODULE$.completionStage(completionStage);
    }

    public static <T> Source<T, NotUsed> never() {
        return Source$.MODULE$.never();
    }

    public static <T> Source<T, NotUsed> future(Future<T> futureElement) {
        return Source$.MODULE$.future(futureElement);
    }

    public static <T> Source<T, Future<NotUsed>> lazilyAsync(Function0<Future<T>> create) {
        return Source$.MODULE$.lazilyAsync(create);
    }

    public static <T, M> Source<T, Future<M>> lazily(Function0<Source<T, M>> create) {
        return Source$.MODULE$.lazily(create);
    }

    public static <T> Source<T, NotUsed> failed(Throwable cause) {
        return Source$.MODULE$.failed(cause);
    }

    public static <T> Source<T, Promise<Option<T>>> maybe() {
        return Source$.MODULE$.maybe();
    }

    public static <T> Source<T, NotUsed> empty() {
        return Source$.MODULE$.empty();
    }

    public static <S, E> Source<E, NotUsed> unfoldAsync(S s2, Function1<S, Future<Option<Tuple2<S, E>>>> f) {
        return Source$.MODULE$.unfoldAsync(s2, f);
    }

    public static <S, E> Source<E, NotUsed> unfold(S s2, Function1<S, Option<Tuple2<S, E>>> f) {
        return Source$.MODULE$.unfold(s2, f);
    }

    public static <T> Source<T, NotUsed> repeat(T element) {
        return Source$.MODULE$.repeat(element);
    }

    public static <T> Source<T, NotUsed> single(T element) {
        return Source$.MODULE$.single(element);
    }

    public static <T> Source<T, Cancellable> tick(FiniteDuration initialDelay, FiniteDuration interval, T tick) {
        return Source$.MODULE$.tick(initialDelay, interval, tick);
    }

    public static <T, M> Source<T, CompletionStage<M>> fromSourceCompletionStage(CompletionStage<? extends Graph<SourceShape<T>, M>> completion) {
        return Source$.MODULE$.fromSourceCompletionStage(completion);
    }

    public static <T, M> Source<T, Future<M>> fromFutureSource(Future<Graph<SourceShape<T>, M>> future) {
        return Source$.MODULE$.fromFutureSource(future);
    }

    public static <T> Source<T, NotUsed> fromCompletionStage(CompletionStage<T> future) {
        return Source$.MODULE$.fromCompletionStage(future);
    }

    public static <T> Source<T, NotUsed> fromFuture(Future<T> future) {
        return Source$.MODULE$.fromFuture(future);
    }

    public static <T> Source<T, NotUsed> apply(Iterable<T> iterable) {
        return Source$.MODULE$.apply(iterable);
    }

    public static <T, M> Source<T, Future<M>> setup(Function2<ActorMaterializer, Attributes, Source<T, M>> factory2) {
        return Source$.MODULE$.setup(factory2);
    }

    public static <T, M> Source<T, Future<M>> fromMaterializer(Function2<Materializer, Attributes, Source<T, M>> factory2) {
        return Source$.MODULE$.fromMaterializer(factory2);
    }

    public static <T, M> Source<T, M> fromGraph(Graph<SourceShape<T>, M> g2) {
        return Source$.MODULE$.fromGraph(g2);
    }

    public static <T> Source<T, NotUsed> cycle(Function0<Iterator<T>> f) {
        return Source$.MODULE$.cycle(f);
    }

    public static <T, S extends BaseStream<T, S>> Source<T, NotUsed> fromJavaStream(Function0<BaseStream<T, S>> stream) {
        return Source$.MODULE$.fromJavaStream(stream);
    }

    public static <T> Source<T, NotUsed> fromIterator(Function0<Iterator<T>> f) {
        return Source$.MODULE$.fromIterator(f);
    }

    public static <T> Source<T, NotUsed> fromPublisher(Publisher<T> publisher) {
        return Source$.MODULE$.fromPublisher(publisher);
    }

    @Override
    public <Out2, Mat2, Mat3> FlowOpsMat flatMapPrefixMat(int n, Function1<Seq<Out>, Flow<Out, Out2, Mat2>> f, Function2<Mat, Future<Mat2>, Mat3> matF) {
        return FlowOpsMat.flatMapPrefixMat$(this, n, f, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat zipMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.zipMat$(this, that, matF);
    }

    @Override
    public <U, Mat2, Mat3, A> FlowOpsMat zipAllMat(Graph<SourceShape<U>, Mat2> that, A thisElem, U thatElem, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.zipAllMat$(this, that, thisElem, thatElem, matF);
    }

    @Override
    public <Out2, Out3, Mat2, Mat3> FlowOpsMat zipWithMat(Graph<SourceShape<Out2>, Mat2> that, Function2<Out, Out2, Out3> combine, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.zipWithMat$(this, that, combine, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat zipLatestMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.zipLatestMat$(this, that, matF);
    }

    @Override
    public <Out2, Out3, Mat2, Mat3> FlowOpsMat zipLatestWithMat(Graph<SourceShape<Out2>, Mat2> that, Function2<Out, Out2, Out3> combine, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.zipLatestWithMat$(this, that, combine, matF);
    }

    @Override
    public <Out2, Out3, Mat2, Mat3> FlowOpsMat zipLatestWithMat(Graph<SourceShape<Out2>, Mat2> that, boolean eagerComplete, Function2<Out, Out2, Out3> combine, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.zipLatestWithMat$(this, that, eagerComplete, combine, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat mergeMat(Graph<SourceShape<U>, Mat2> that, boolean eagerComplete, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.mergeMat$(this, that, eagerComplete, matF);
    }

    @Override
    public <U, Mat2, Mat3> boolean mergeMat$default$2() {
        return FlowOpsMat.mergeMat$default$2$(this);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat interleaveMat(Graph<SourceShape<U>, Mat2> that, int segmentSize, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.interleaveMat$(this, that, segmentSize, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat interleaveMat(Graph<SourceShape<U>, Mat2> that, int segmentSize, boolean eagerClose, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.interleaveMat$(this, that, segmentSize, eagerClose, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat mergeLatestMat(Graph<SourceShape<U>, Mat2> that, boolean eagerClose, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.mergeLatestMat$(this, that, eagerClose, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat mergePreferredMat(Graph<SourceShape<U>, Mat2> that, boolean preferred, boolean eagerClose, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.mergePreferredMat$(this, that, preferred, eagerClose, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat mergePrioritizedMat(Graph<SourceShape<U>, Mat2> that, int leftPriority, int rightPriority, boolean eagerClose, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.mergePrioritizedMat$(this, that, leftPriority, rightPriority, eagerClose, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat mergeSortedMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF, Ordering<U> ord) {
        return FlowOpsMat.mergeSortedMat$(this, that, matF, ord);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat concatMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.concatMat$(this, that, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat concatLazyMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.concatLazyMat$(this, that, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat prependMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.prependMat$(this, that, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat prependLazyMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.prependLazyMat$(this, that, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat orElseMat(Graph<SourceShape<U>, Mat2> secondary, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.orElseMat$(this, secondary, matF);
    }

    @Override
    public <Mat2, Mat3> FlowOpsMat alsoToMat(Graph<SinkShape<Out>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.alsoToMat$(this, that, matF);
    }

    @Override
    public <Mat2, Mat3> FlowOpsMat divertToMat(Graph<SinkShape<Out>, Mat2> that, Function1<Out, Object> when, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.divertToMat$(this, that, when, matF);
    }

    @Override
    public <Mat2, Mat3> FlowOpsMat wireTapMat(Graph<SinkShape<Out>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.wireTapMat$(this, that, matF);
    }

    @Override
    public <Mat2> FlowOpsMat watchTermination(Function2<Mat, Future<Done>, Mat2> matF) {
        return FlowOpsMat.watchTermination$(this, matF);
    }

    @Override
    public <Mat2> FlowOpsMat monitorMat(Function2<Mat, FlowMonitor<Out>, Mat2> combine) {
        return FlowOpsMat.monitorMat$(this, combine);
    }

    @Override
    public FlowOpsMat monitor() {
        return FlowOpsMat.monitor$(this);
    }

    @Override
    public <T> FlowOps recover(PartialFunction<Throwable, T> pf) {
        return FlowOps.recover$(this, pf);
    }

    @Override
    public <T> FlowOps recoverWith(PartialFunction<Throwable, Graph<SourceShape<T>, NotUsed>> pf) {
        return FlowOps.recoverWith$(this, pf);
    }

    @Override
    public <T> FlowOps recoverWithRetries(int attempts, PartialFunction<Throwable, Graph<SourceShape<T>, NotUsed>> pf) {
        return FlowOps.recoverWithRetries$(this, attempts, pf);
    }

    @Override
    public FlowOps mapError(PartialFunction<Throwable, Throwable> pf) {
        return FlowOps.mapError$(this, pf);
    }

    @Override
    public <T> FlowOps map(Function1<Out, T> f) {
        return FlowOps.map$(this, f);
    }

    @Override
    public FlowOps wireTap(Function1<Out, BoxedUnit> f) {
        return FlowOps.wireTap$((FlowOps)this, f);
    }

    @Override
    public <T> FlowOps mapConcat(Function1<Out, IterableOnce<T>> f) {
        return FlowOps.mapConcat$(this, f);
    }

    @Override
    public <S, T> FlowOps statefulMap(Function0<S> create, Function2<S, Out, Tuple2<S, T>> f, Function1<S, Option<T>> onComplete) {
        return FlowOps.statefulMap$(this, create, f, onComplete);
    }

    @Override
    public <R, T> FlowOps mapWithResource(Function0<R> create, Function2<R, Out, T> f, Function1<R, Option<T>> close) {
        return FlowOps.mapWithResource$(this, create, f, close);
    }

    @Override
    public <T> FlowOps statefulMapConcat(Function0<Function1<Out, IterableOnce<T>>> f) {
        return FlowOps.statefulMapConcat$(this, f);
    }

    @Override
    public <T> FlowOps mapAsync(int parallelism, Function1<Out, Future<T>> f) {
        return FlowOps.mapAsync$(this, parallelism, f);
    }

    @Override
    public <T, P> FlowOps mapAsyncPartitioned(int parallelism, int perPartition, Function1<Out, P> partitioner, Function2<Out, P, Future<T>> f) {
        return FlowOps.mapAsyncPartitioned$(this, parallelism, perPartition, partitioner, f);
    }

    @Override
    public <T> FlowOps mapAsyncUnordered(int parallelism, Function1<Out, Future<T>> f) {
        return FlowOps.mapAsyncUnordered$(this, parallelism, f);
    }

    @Override
    public <S> FlowOps ask(ActorRef ref, Timeout timeout2, ClassTag<S> tag) {
        return FlowOps.ask$(this, ref, timeout2, tag);
    }

    @Override
    public <S> FlowOps ask(int parallelism, ActorRef ref, Timeout timeout2, ClassTag<S> tag) {
        return FlowOps.ask$(this, parallelism, ref, timeout2, tag);
    }

    @Override
    public FlowOps watch(ActorRef ref) {
        return FlowOps.watch$(this, ref);
    }

    @Override
    public FlowOps filter(Function1<Out, Object> p) {
        return FlowOps.filter$(this, p);
    }

    @Override
    public FlowOps filterNot(Function1<Out, Object> p) {
        return FlowOps.filterNot$(this, p);
    }

    @Override
    public FlowOps takeWhile(Function1<Out, Object> p) {
        return FlowOps.takeWhile$(this, p);
    }

    @Override
    public FlowOps takeWhile(Function1<Out, Object> p, boolean inclusive) {
        return FlowOps.takeWhile$(this, p, inclusive);
    }

    @Override
    public FlowOps dropWhile(Function1<Out, Object> p) {
        return FlowOps.dropWhile$(this, p);
    }

    @Override
    public <T> FlowOps collect(PartialFunction<Out, T> pf) {
        return FlowOps.collect$(this, pf);
    }

    @Override
    public <T> FlowOps collectType(ClassTag<T> tag) {
        return FlowOps.collectType$(this, tag);
    }

    @Override
    public FlowOps grouped(int n) {
        return FlowOps.grouped$(this, n);
    }

    @Override
    public FlowOps groupedWeighted(long minWeight, Function1<Out, Object> costFn) {
        return FlowOps.groupedWeighted$(this, minWeight, costFn);
    }

    @Override
    public FlowOps limit(long max) {
        return FlowOps.limit$(this, max);
    }

    @Override
    public <T> FlowOps limitWeighted(long max, Function1<Out, Object> costFn) {
        return FlowOps.limitWeighted$(this, max, costFn);
    }

    @Override
    public FlowOps sliding(int n, int step) {
        return FlowOps.sliding$(this, n, step);
    }

    @Override
    public int sliding$default$2() {
        return FlowOps.sliding$default$2$(this);
    }

    @Override
    public <T> FlowOps scan(T zero, Function2<T, Out, T> f) {
        return FlowOps.scan$(this, zero, f);
    }

    @Override
    public <T> FlowOps scanAsync(T zero, Function2<T, Out, Future<T>> f) {
        return FlowOps.scanAsync$(this, zero, f);
    }

    @Override
    public <T> FlowOps fold(T zero, Function2<T, Out, T> f) {
        return FlowOps.fold$(this, zero, f);
    }

    @Override
    public <T> FlowOps foldAsync(T zero, Function2<T, Out, Future<T>> f) {
        return FlowOps.foldAsync$(this, zero, f);
    }

    @Override
    public <T> FlowOps reduce(Function2<T, T, T> f) {
        return FlowOps.reduce$(this, f);
    }

    @Override
    public <T> FlowOps intersperse(T start2, T inject, T end) {
        return FlowOps.intersperse$(this, start2, inject, end);
    }

    @Override
    public <T> FlowOps intersperse(T inject) {
        return FlowOps.intersperse$(this, inject);
    }

    @Override
    public FlowOps groupedWithin(int n, FiniteDuration d) {
        return FlowOps.groupedWithin$(this, n, d);
    }

    @Override
    public FlowOps groupedWeightedWithin(long maxWeight, FiniteDuration d, Function1<Out, Object> costFn) {
        return FlowOps.groupedWeightedWithin$(this, maxWeight, d, costFn);
    }

    @Override
    public FlowOps groupedWeightedWithin(long maxWeight, int maxNumber, FiniteDuration d, Function1<Out, Object> costFn) {
        return FlowOps.groupedWeightedWithin$(this, maxWeight, maxNumber, d, costFn);
    }

    @Override
    public FlowOps delay(FiniteDuration of, DelayOverflowStrategy strategy) {
        return FlowOps.delay$(this, of, strategy);
    }

    @Override
    public DelayOverflowStrategy delay$default$2() {
        return FlowOps.delay$default$2$(this);
    }

    @Override
    public FlowOps delayWith(Function0<DelayStrategy<Out>> delayStrategySupplier, DelayOverflowStrategy overFlowStrategy) {
        return FlowOps.delayWith$(this, delayStrategySupplier, overFlowStrategy);
    }

    @Override
    public FlowOps drop(long n) {
        return FlowOps.drop$(this, n);
    }

    @Override
    public FlowOps dropWithin(FiniteDuration d) {
        return FlowOps.dropWithin$(this, d);
    }

    @Override
    public FlowOps take(long n) {
        return FlowOps.take$(this, n);
    }

    @Override
    public FlowOps takeWithin(FiniteDuration d) {
        return FlowOps.takeWithin$(this, d);
    }

    @Override
    public <S> FlowOps conflateWithSeed(Function1<Out, S> seed, Function2<S, Out, S> aggregate) {
        return FlowOps.conflateWithSeed$(this, seed, aggregate);
    }

    @Override
    public <O2> FlowOps conflate(Function2<O2, O2, O2> aggregate) {
        return FlowOps.conflate$(this, aggregate);
    }

    @Override
    public <S> FlowOps batch(long max, Function1<Out, S> seed, Function2<S, Out, S> aggregate) {
        return FlowOps.batch$(this, max, seed, aggregate);
    }

    @Override
    public <S> FlowOps batchWeighted(long max, Function1<Out, Object> costFn, Function1<Out, S> seed, Function2<S, Out, S> aggregate) {
        return FlowOps.batchWeighted$(this, max, costFn, seed, aggregate);
    }

    @Override
    public <U> FlowOps expand(Function1<Out, Iterator<U>> expander) {
        return FlowOps.expand$(this, expander);
    }

    @Override
    public <U> FlowOps extrapolate(Function1<U, Iterator<U>> extrapolator, Option<U> initial) {
        return FlowOps.extrapolate$(this, extrapolator, initial);
    }

    @Override
    public <U> None$ extrapolate$default$2() {
        return FlowOps.extrapolate$default$2$(this);
    }

    @Override
    public FlowOps buffer(int size, OverflowStrategy overflowStrategy) {
        return FlowOps.buffer$(this, size, overflowStrategy);
    }

    @Override
    public <U> FlowOps prefixAndTail(int n) {
        return FlowOps.prefixAndTail$(this, n);
    }

    @Override
    public <Out2, Mat2> FlowOps flatMapPrefix(int n, Function1<Seq<Out>, Flow<Out, Out2, Mat2>> f) {
        return FlowOps.flatMapPrefix$(this, n, f);
    }

    @Override
    public <K> SubFlow<Out, Mat, FlowOps, Object> groupBy(int maxSubstreams, Function1<Out, K> f, boolean allowClosedSubstreamRecreation) {
        return FlowOps.groupBy$(this, maxSubstreams, f, allowClosedSubstreamRecreation);
    }

    @Override
    public <K> SubFlow<Out, Mat, FlowOps, Object> groupBy(int maxSubstreams, Function1<Out, K> f) {
        return FlowOps.groupBy$(this, maxSubstreams, f);
    }

    @Override
    public SubFlow<Out, Mat, FlowOps, Object> splitWhen(SubstreamCancelStrategy substreamCancelStrategy, Function1<Out, Object> p) {
        return FlowOps.splitWhen$(this, substreamCancelStrategy, p);
    }

    @Override
    public SubFlow<Out, Mat, FlowOps, Object> splitWhen(Function1<Out, Object> p) {
        return FlowOps.splitWhen$(this, p);
    }

    @Override
    public SubFlow<Out, Mat, FlowOps, Object> splitAfter(SubstreamCancelStrategy substreamCancelStrategy, Function1<Out, Object> p) {
        return FlowOps.splitAfter$(this, substreamCancelStrategy, p);
    }

    @Override
    public SubFlow<Out, Mat, FlowOps, Object> splitAfter(Function1<Out, Object> p) {
        return FlowOps.splitAfter$(this, p);
    }

    @Override
    public <T, M> FlowOps flatMapConcat(Function1<Out, Graph<SourceShape<T>, M>> f) {
        return FlowOps.flatMapConcat$(this, f);
    }

    @Override
    public <T, M> FlowOps flatMapMerge(int breadth, Function1<Out, Graph<SourceShape<T>, M>> f) {
        return FlowOps.flatMapMerge$(this, breadth, f);
    }

    @Override
    public FlowOps initialTimeout(FiniteDuration timeout2) {
        return FlowOps.initialTimeout$(this, timeout2);
    }

    @Override
    public FlowOps completionTimeout(FiniteDuration timeout2) {
        return FlowOps.completionTimeout$(this, timeout2);
    }

    @Override
    public FlowOps idleTimeout(FiniteDuration timeout2) {
        return FlowOps.idleTimeout$(this, timeout2);
    }

    @Override
    public FlowOps backpressureTimeout(FiniteDuration timeout2) {
        return FlowOps.backpressureTimeout$(this, timeout2);
    }

    @Override
    public <U> FlowOps keepAlive(FiniteDuration maxIdle, Function0<U> injectedElem) {
        return FlowOps.keepAlive$(this, maxIdle, injectedElem);
    }

    @Override
    public FlowOps throttle(int elements, FiniteDuration per) {
        return FlowOps.throttle$(this, elements, per);
    }

    @Override
    public FlowOps throttle(int elements, FiniteDuration per, int maximumBurst, ThrottleMode mode) {
        return FlowOps.throttle$(this, elements, per, maximumBurst, mode);
    }

    @Override
    public FlowOps throttle(int cost, FiniteDuration per, Function1<Out, Object> costCalculation) {
        return FlowOps.throttle$(this, cost, per, costCalculation);
    }

    @Override
    public FlowOps throttle(int cost, FiniteDuration per, int maximumBurst, Function1<Out, Object> costCalculation, ThrottleMode mode) {
        return FlowOps.throttle$(this, cost, per, maximumBurst, costCalculation, mode);
    }

    @Override
    public FlowOps detach() {
        return FlowOps.detach$(this);
    }

    @Override
    public FlowOps initialDelay(FiniteDuration delay) {
        return FlowOps.initialDelay$(this, delay);
    }

    @Override
    public FlowOps log(String name, Function1<Out, Object> extract, LoggingAdapter log) {
        return FlowOps.log$(this, name, extract, log);
    }

    @Override
    public Function1<Out, Object> log$default$2() {
        return FlowOps.log$default$2$(this);
    }

    @Override
    public LoggingAdapter log$default$3(String name, Function1<Out, Object> extract) {
        return FlowOps.log$default$3$(this, name, extract);
    }

    @Override
    public FlowOps logWithMarker(String name, Function1<Out, LogMarker> marker, Function1<Out, Object> extract, MarkerLoggingAdapter log) {
        return FlowOps.logWithMarker$(this, name, marker, extract, log);
    }

    @Override
    public Function1<Out, Object> logWithMarker$default$3() {
        return FlowOps.logWithMarker$default$3$(this);
    }

    @Override
    public MarkerLoggingAdapter logWithMarker$default$4(String name, Function1<Out, LogMarker> marker, Function1<Out, Object> extract) {
        return FlowOps.logWithMarker$default$4$(this, name, marker, extract);
    }

    @Override
    public <U> FlowOps zip(Graph<SourceShape<U>, ?> that) {
        return FlowOps.zip$(this, that);
    }

    @Override
    public <U, A> FlowOps zipAll(Graph<SourceShape<U>, ?> that, A thisElem, U thatElem) {
        return FlowOps.zipAll$(this, that, thisElem, thatElem);
    }

    @Override
    public <U, A, Mat2> Flow<Out, Tuple2<A, U>, Mat2> zipAllFlow(Graph<SourceShape<U>, Mat2> that, A thisElem, U thatElem) {
        return FlowOps.zipAllFlow$(this, that, thisElem, thatElem);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, Tuple2<Out, U>>, M> zipGraph(Graph<SourceShape<U>, M> that) {
        return FlowOps.zipGraph$(this, that);
    }

    @Override
    public <U> FlowOps zipLatest(Graph<SourceShape<U>, ?> that) {
        return FlowOps.zipLatest$(this, that);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, Tuple2<Out, U>>, M> zipLatestGraph(Graph<SourceShape<U>, M> that) {
        return FlowOps.zipLatestGraph$(this, that);
    }

    @Override
    public <Out2, Out3> FlowOps zipWith(Graph<SourceShape<Out2>, ?> that, Function2<Out, Out2, Out3> combine) {
        return FlowOps.zipWith$(this, that, combine);
    }

    @Override
    public <Out2, Out3, M> Graph<FlowShape<Out, Out3>, M> zipWithGraph(Graph<SourceShape<Out2>, M> that, Function2<Out, Out2, Out3> combine) {
        return FlowOps.zipWithGraph$(this, that, combine);
    }

    @Override
    public <Out2, Out3> FlowOps zipLatestWith(Graph<SourceShape<Out2>, ?> that, Function2<Out, Out2, Out3> combine) {
        return FlowOps.zipLatestWith$(this, that, combine);
    }

    @Override
    public <Out2, Out3> FlowOps zipLatestWith(Graph<SourceShape<Out2>, ?> that, boolean eagerComplete, Function2<Out, Out2, Out3> combine) {
        return FlowOps.zipLatestWith$(this, that, eagerComplete, combine);
    }

    @Override
    public <Out2, Out3, M> Graph<FlowShape<Out, Out3>, M> zipLatestWithGraph(Graph<SourceShape<Out2>, M> that, Function2<Out, Out2, Out3> combine) {
        return FlowOps.zipLatestWithGraph$(this, that, combine);
    }

    @Override
    public <Out2, Out3, M> Graph<FlowShape<Out, Out3>, M> zipLatestWithGraph(Graph<SourceShape<Out2>, M> that, boolean eagerComplete, Function2<Out, Out2, Out3> combine) {
        return FlowOps.zipLatestWithGraph$(this, that, eagerComplete, combine);
    }

    @Override
    public FlowOps zipWithIndex() {
        return FlowOps.zipWithIndex$(this);
    }

    @Override
    public <U> FlowOps interleave(Graph<SourceShape<U>, ?> that, int segmentSize) {
        return FlowOps.interleave$(this, that, segmentSize);
    }

    @Override
    public <U> FlowOps interleave(Graph<SourceShape<U>, ?> that, int segmentSize, boolean eagerClose) {
        return FlowOps.interleave$(this, that, segmentSize, eagerClose);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, U>, M> interleaveGraph(Graph<SourceShape<U>, M> that, int segmentSize, boolean eagerClose) {
        return FlowOps.interleaveGraph$(this, that, segmentSize, eagerClose);
    }

    @Override
    public <U, M> boolean interleaveGraph$default$3() {
        return FlowOps.interleaveGraph$default$3$(this);
    }

    @Override
    public <U> FlowOps interleaveAll(Seq<Graph<SourceShape<U>, ?>> those, int segmentSize, boolean eagerClose) {
        return FlowOps.interleaveAll$(this, those, segmentSize, eagerClose);
    }

    @Override
    public <U, M> FlowOps merge(Graph<SourceShape<U>, M> that, boolean eagerComplete) {
        return FlowOps.merge$(this, that, eagerComplete);
    }

    @Override
    public <U, M> boolean merge$default$2() {
        return FlowOps.merge$default$2$(this);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, U>, M> mergeGraph(Graph<SourceShape<U>, M> that, boolean eagerComplete) {
        return FlowOps.mergeGraph$(this, that, eagerComplete);
    }

    @Override
    public <U> FlowOps mergeAll(Seq<Graph<SourceShape<U>, ?>> those, boolean eagerComplete) {
        return FlowOps.mergeAll$(this, those, eagerComplete);
    }

    @Override
    public <U, M> FlowOps mergeLatest(Graph<SourceShape<U>, M> that, boolean eagerComplete) {
        return FlowOps.mergeLatest$(this, that, eagerComplete);
    }

    @Override
    public <U, M> boolean mergeLatest$default$2() {
        return FlowOps.mergeLatest$default$2$(this);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, Seq<U>>, M> mergeLatestGraph(Graph<SourceShape<U>, M> that, boolean eagerComplete) {
        return FlowOps.mergeLatestGraph$(this, that, eagerComplete);
    }

    @Override
    public <U, M> FlowOps mergePreferred(Graph<SourceShape<U>, M> that, boolean preferred, boolean eagerComplete) {
        return FlowOps.mergePreferred$(this, that, preferred, eagerComplete);
    }

    @Override
    public <U, M> boolean mergePreferred$default$3() {
        return FlowOps.mergePreferred$default$3$(this);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, U>, M> mergePreferredGraph(Graph<SourceShape<U>, M> that, boolean preferred, boolean eagerComplete) {
        return FlowOps.mergePreferredGraph$(this, that, preferred, eagerComplete);
    }

    @Override
    public <U, M> FlowOps mergePrioritized(Graph<SourceShape<U>, M> that, int leftPriority, int rightPriority, boolean eagerComplete) {
        return FlowOps.mergePrioritized$(this, that, leftPriority, rightPriority, eagerComplete);
    }

    @Override
    public <U, M> boolean mergePrioritized$default$4() {
        return FlowOps.mergePrioritized$default$4$(this);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, U>, M> mergePrioritizedGraph(Graph<SourceShape<U>, M> that, int leftPriority, int rightPriority, boolean eagerComplete) {
        return FlowOps.mergePrioritizedGraph$(this, that, leftPriority, rightPriority, eagerComplete);
    }

    @Override
    public <U, M> FlowOps mergeSorted(Graph<SourceShape<U>, M> that, Ordering<U> ord) {
        return FlowOps.mergeSorted$(this, that, ord);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, U>, M> mergeSortedGraph(Graph<SourceShape<U>, M> that, Ordering<U> ord) {
        return FlowOps.mergeSortedGraph$(this, that, ord);
    }

    @Override
    public <U, Mat2> FlowOps concat(Graph<SourceShape<U>, Mat2> that) {
        return FlowOps.concat$(this, that);
    }

    @Override
    public <U, Mat2> Graph<FlowShape<Out, U>, Mat2> concatGraph(Graph<SourceShape<U>, Mat2> that, boolean detached) {
        return FlowOps.concatGraph$(this, that, detached);
    }

    @Override
    public <U, Mat2> FlowOps concatLazy(Graph<SourceShape<U>, Mat2> that) {
        return FlowOps.concatLazy$(this, that);
    }

    @Override
    public <U> FlowOps concatAllLazy(Seq<Graph<SourceShape<U>, ?>> those) {
        return FlowOps.concatAllLazy$(this, those);
    }

    @Override
    public <U, Mat2> FlowOps prepend(Graph<SourceShape<U>, Mat2> that) {
        return FlowOps.prepend$(this, that);
    }

    @Override
    public <U, Mat2> Graph<FlowShape<Out, U>, Mat2> prependGraph(Graph<SourceShape<U>, Mat2> that, boolean detached) {
        return FlowOps.prependGraph$(this, that, detached);
    }

    @Override
    public <U, Mat2> FlowOps prependLazy(Graph<SourceShape<U>, Mat2> that) {
        return FlowOps.prependLazy$(this, that);
    }

    @Override
    public <U, Mat2> FlowOps orElse(Graph<SourceShape<U>, Mat2> secondary) {
        return FlowOps.orElse$(this, secondary);
    }

    @Override
    public <U, Mat2> Graph<FlowShape<Out, U>, Mat2> orElseGraph(Graph<SourceShape<U>, Mat2> secondary) {
        return FlowOps.orElseGraph$(this, secondary);
    }

    @Override
    public <U, M> FlowOps $plus$plus(Graph<SourceShape<U>, M> that) {
        return FlowOps.$plus$plus$(this, that);
    }

    @Override
    public FlowOps alsoTo(Graph<SinkShape<Out>, ?> that) {
        return FlowOps.alsoTo$(this, that);
    }

    @Override
    public <M> Graph<FlowShape<Out, Out>, M> alsoToGraph(Graph<SinkShape<Out>, M> that) {
        return FlowOps.alsoToGraph$(this, that);
    }

    @Override
    public FlowOps alsoToAll(Seq<Graph<SinkShape<Out>, ?>> those) {
        return FlowOps.alsoToAll$(this, those);
    }

    @Override
    public FlowOps divertTo(Graph<SinkShape<Out>, ?> that, Function1<Out, Object> when) {
        return FlowOps.divertTo$(this, that, when);
    }

    @Override
    public <M> Graph<FlowShape<Out, Out>, M> divertToGraph(Graph<SinkShape<Out>, M> that, Function1<Out, Object> when) {
        return FlowOps.divertToGraph$(this, that, when);
    }

    @Override
    public FlowOps wireTap(Graph<SinkShape<Out>, ?> that) {
        return FlowOps.wireTap$((FlowOps)this, that);
    }

    @Override
    public <M> Graph<FlowShape<Out, Out>, M> wireTapGraph(Graph<SinkShape<Out>, M> that) {
        return FlowOps.wireTapGraph$(this, that);
    }

    @Override
    @ApiMayChange
    public <Agg, Emit> FlowOps aggregateWithBoundary(Function0<Agg> allocate, Function2<Agg, Out, Tuple2<Agg, Object>> aggregate, Function1<Agg, Emit> harvest, Option<Tuple2<Function1<Agg, Object>, FiniteDuration>> emitOnTimer) {
        return FlowOps.aggregateWithBoundary$(this, allocate, aggregate, harvest, emitOnTimer);
    }

    @Override
    public LinearTraversalBuilder traversalBuilder() {
        return this.traversalBuilder;
    }

    @Override
    public SourceShape<Out> shape() {
        return this.shape;
    }

    public String toString() {
        return new StringBuilder(8).append("Source(").append(this.shape()).append(")").toString();
    }

    @Override
    public <T, Mat2> Source<T, Mat> via(Graph<FlowShape<Out, T>, Mat2> flow) {
        return this.viaMat((Graph)flow, Keep$.MODULE$.left());
    }

    @Override
    public <T, Mat2, Mat3> Source<T, Mat3> viaMat(Graph<FlowShape<Out, T>, Mat2> flow, Function2<Mat, Mat2, Mat3> combine) {
        block4: {
            block6: {
                block5: {
                    if (flow.traversalBuilder() != Flow$.MODULE$.identityTraversalBuilder()) break block4;
                    Function2<Nothing$, Nothing$, Object> function2 = combine;
                    Function2 function22 = Keep$.MODULE$.left();
                    if (!(function2 != null ? !function2.equals(function22) : function22 != null)) {
                        return this;
                    }
                    Function2<Nothing$, Nothing$, Object> function23 = combine;
                    Function2 function24 = Keep$.MODULE$.right();
                    if (!(function23 == null ? function24 != null : !function23.equals(function24))) break block5;
                    Function2<Nothing$, Nothing$, Object> function25 = combine;
                    Function2 function26 = Keep$.MODULE$.none();
                    if (function25 != null ? !function25.equals(function26) : function26 != null) break block6;
                }
                return new Source(this.traversalBuilder().append(LinearTraversalBuilder$.MODULE$.empty(LinearTraversalBuilder$.MODULE$.empty$default$1()), flow.shape(), combine), new SourceShape(((SourceShape)this.shape()).out()));
            }
            return new Source<T, Mat>(this.traversalBuilder().append(flow.traversalBuilder(), flow.shape(), combine), new SourceShape<T>(flow.shape().out()));
        }
        return new Source<T, Mat>(this.traversalBuilder().append(flow.traversalBuilder(), flow.shape(), combine), new SourceShape<T>(flow.shape().out()));
    }

    @Override
    public <Mat2> RunnableGraph<Mat> to(Graph<SinkShape<Out>, Mat2> sink2) {
        return this.toMat((Graph)sink2, Keep$.MODULE$.left());
    }

    @Override
    public <Mat2, Mat3> RunnableGraph<Mat3> toMat(Graph<SinkShape<Out>, Mat2> sink2, Function2<Mat, Mat2, Mat3> combine) {
        return new RunnableGraph(this.traversalBuilder().append(sink2.traversalBuilder(), sink2.shape(), combine));
    }

    @Override
    public <Mat2> Source<Out, Mat2> mapMaterializedValue(Function1<Mat, Mat2> f) {
        return new Source<Out, Mat>((LinearTraversalBuilder)this.traversalBuilder().transformMat(f), this.shape());
    }

    public Tuple2<Mat, Source<Out, NotUsed>> preMaterialize(Materializer materializer) {
        Tuple2 tuple2 = (Tuple2)((RunnableGraph)this.toMat((Graph)Sink$.MODULE$.asPublisher(true), Keep$.MODULE$.both())).run(materializer);
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        Object mat = tuple2._1();
        Publisher pub = (Publisher)tuple2._2();
        Tuple2 tuple22 = new Tuple2(mat, pub);
        Object mat2 = tuple22._1();
        Publisher pub2 = tuple22._2();
        return new Tuple2(mat2, Source$.MODULE$.fromPublisher(pub2));
    }

    public Future<Done> run(Materializer materializer) {
        return (Future)((RunnableGraph)this.toMat(Sink$.MODULE$.ignore(), Keep$.MODULE$.right())).run(materializer);
    }

    public <Mat2> Mat2 runWith(Graph<SinkShape<Out>, Mat2> sink2, Materializer materializer) {
        return (Mat2)((RunnableGraph)this.toMat((Graph)sink2, Keep$.MODULE$.right())).run(materializer);
    }

    public <U> Future<U> runFold(U zero, Function2<U, Out, U> f, Materializer materializer) {
        return this.runWith(Sink$.MODULE$.fold(zero, f), materializer);
    }

    public <U> Future<U> runFoldAsync(U zero, Function2<U, Out, Future<U>> f, Materializer materializer) {
        return this.runWith(Sink$.MODULE$.foldAsync(zero, f), materializer);
    }

    public <U> Future<U> runReduce(Function2<U, U, U> f, Materializer materializer) {
        return this.runWith(Sink$.MODULE$.reduce(f), materializer);
    }

    public Future<Done> runForeach(Function1<Out, BoxedUnit> f, Materializer materializer) {
        return this.runWith(Sink$.MODULE$.foreach(f), materializer);
    }

    @Override
    public Source<Out, Mat> withAttributes(Attributes attr) {
        return new Source<Out, Mat>(this.traversalBuilder().setAttributes(attr), this.shape());
    }

    @Override
    public Source<Out, Mat> addAttributes(Attributes attr) {
        return this.withAttributes(this.traversalBuilder().attributes().and(attr));
    }

    @Override
    public Source<Out, Mat> named(String name) {
        return this.addAttributes(Attributes$.MODULE$.name(name));
    }

    @Override
    public Source<Out, Mat> async() {
        return (Source)Graph.async$(this);
    }

    @Override
    public Source<Out, Mat> async(String dispatcher) {
        return (Source)Graph.async$(this, dispatcher);
    }

    @Override
    public Source<Out, Mat> async(String dispatcher, int inputBufferSize) {
        return (Source)Graph.async$(this, dispatcher, inputBufferSize);
    }

    public akka.stream.javadsl.Source<Out, Mat> asJava() {
        return new akka.stream.javadsl.Source(this);
    }

    public <Ctx> SourceWithContext<Out, Ctx, Mat> asSourceWithContext(Function1<Out, Ctx> f) {
        return new SourceWithContext((Source)this.map((Function1<Object, Tuple2> & Serializable)e -> new Tuple2(e, f.apply(e))));
    }

    @Override
    public Attributes getAttributes() {
        return this.traversalBuilder().attributes();
    }

    public Source(LinearTraversalBuilder traversalBuilder, SourceShape<Out> shape) {
        this.traversalBuilder = traversalBuilder;
        this.shape = shape;
        FlowOps.$init$(this);
        FlowOpsMat.$init$(this);
        Graph.$init$(this);
    }
}

