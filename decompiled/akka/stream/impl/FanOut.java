/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorLogging;
import akka.actor.ActorRef;
import akka.actor.DeadLetterSuppression;
import akka.actor.NoSerializationVerificationNeeded;
import akka.actor.SupervisorStrategy;
import akka.annotation.DoNotInherit;
import akka.annotation.InternalApi;
import akka.event.LoggingAdapter;
import akka.stream.AbruptTerminationException;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.impl.ActorPublisher;
import akka.stream.impl.BatchingInputBuffer;
import akka.stream.impl.Cancel;
import akka.stream.impl.ExposedPublisher;
import akka.stream.impl.FanOut$OutputBunch$;
import akka.stream.impl.Inputs;
import akka.stream.impl.Pump;
import akka.stream.impl.ReactiveStreamsCompliance$;
import akka.stream.impl.RequestMore;
import akka.stream.impl.SimpleOutputs;
import akka.stream.impl.SubReceive;
import akka.stream.impl.SubscribePending$;
import akka.stream.impl.TransferPhase;
import akka.stream.impl.TransferState;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import org.reactivestreams.Subscription;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Product;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.collection.immutable.Seq;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0015\ruACA?\u0003\u007fB\t!a\"\u0002\f\u001aQ\u0011qRA@\u0011\u0003\t9)!%\t\u000f\u0005}\u0015\u0001\"\u0001\u0002$\u001a1\u0011QU\u0001C\u0003OC!\"!7\u0004\u0005+\u0007I\u0011AAn\u0011)\t\u0019o\u0001B\tB\u0003%\u0011Q\u001c\u0005\u000b\u0003K\u001c!Q3A\u0005\u0002\u0005\u001d\bBCAx\u0007\tE\t\u0015!\u0003\u0002j\"9\u0011qT\u0002\u0005\u0002\u0005E\b\"CA~\u0007\u0005\u0005I\u0011AA\u007f\u0011%\u0011\u0019aAI\u0001\n\u0003\u0011)\u0001C\u0005\u0003\u001c\r\t\n\u0011\"\u0001\u0003\u001e!I!\u0011E\u0002\u0002\u0002\u0013\u0005#1\u0005\u0005\n\u0005k\u0019\u0011\u0011!C\u0001\u00037D\u0011Ba\u000e\u0004\u0003\u0003%\tA!\u000f\t\u0013\t\u00153!!A\u0005B\t\u001d\u0003\"\u0003B+\u0007\u0005\u0005I\u0011\u0001B,\u0011%\u0011\tgAA\u0001\n\u0003\u0012\u0019\u0007C\u0005\u0003h\r\t\t\u0011\"\u0011\u0003j!I!1N\u0002\u0002\u0002\u0013\u0005#Q\u000e\u0005\n\u0005_\u001a\u0011\u0011!C!\u0005c:\u0011B!\u001e\u0002\u0003\u0003E\tAa\u001e\u0007\u0013\u0005\u0015\u0016!!A\t\u0002\te\u0004bBAP-\u0011\u0005!\u0011\u0013\u0005\n\u0005W2\u0012\u0011!C#\u0005[B\u0011Ba%\u0017\u0003\u0003%\tI!&\t\u0013\tme#!A\u0005\u0002\nu\u0005\"\u0003BX-\u0005\u0005I\u0011\u0002BY\r\u0019\u0011I,\u0001\"\u0003<\"Q\u0011\u0011\u001c\u000f\u0003\u0016\u0004%\t!a7\t\u0015\u0005\rHD!E!\u0002\u0013\ti\u000eC\u0004\u0002 r!\tA!0\t\u0013\u0005mH$!A\u0005\u0002\t\r\u0007\"\u0003B\u00029E\u0005I\u0011\u0001B\u0003\u0011%\u0011\t\u0003HA\u0001\n\u0003\u0012\u0019\u0003C\u0005\u00036q\t\t\u0011\"\u0001\u0002\\\"I!q\u0007\u000f\u0002\u0002\u0013\u0005!q\u0019\u0005\n\u0005\u000bb\u0012\u0011!C!\u0005\u000fB\u0011B!\u0016\u001d\u0003\u0003%\tAa3\t\u0013\t\u0005D$!A\u0005B\t=\u0007\"\u0003B49\u0005\u0005I\u0011\tB5\u0011%\u0011Y\u0007HA\u0001\n\u0003\u0012i\u0007C\u0005\u0003pq\t\t\u0011\"\u0011\u0003T\u001eI!q[\u0001\u0002\u0002#\u0005!\u0011\u001c\u0004\n\u0005s\u000b\u0011\u0011!E\u0001\u00057Dq!a(-\t\u0003\u0011\u0019\u000fC\u0005\u0003l1\n\t\u0011\"\u0012\u0003n!I!1\u0013\u0017\u0002\u0002\u0013\u0005%Q\u001d\u0005\n\u00057c\u0013\u0011!CA\u0005SD\u0011Ba,-\u0003\u0003%IA!-\u0007\r\t=\u0018A\u0011By\u0011)\tIN\rBK\u0002\u0013\u0005\u00111\u001c\u0005\u000b\u0003G\u0014$\u0011#Q\u0001\n\u0005u\u0007bBAPe\u0011\u0005!1\u001f\u0005\n\u0003w\u0014\u0014\u0011!C\u0001\u0005sD\u0011Ba\u00013#\u0003%\tA!\u0002\t\u0013\t\u0005\"'!A\u0005B\t\r\u0002\"\u0003B\u001be\u0005\u0005I\u0011AAn\u0011%\u00119DMA\u0001\n\u0003\u0011i\u0010C\u0005\u0003FI\n\t\u0011\"\u0011\u0003H!I!Q\u000b\u001a\u0002\u0002\u0013\u00051\u0011\u0001\u0005\n\u0005C\u0012\u0014\u0011!C!\u0007\u000bA\u0011Ba\u001a3\u0003\u0003%\tE!\u001b\t\u0013\t-$'!A\u0005B\t5\u0004\"\u0003B8e\u0005\u0005I\u0011IB\u0005\u000f%\u0019i!AA\u0001\u0012\u0003\u0019yAB\u0005\u0003p\u0006\t\t\u0011#\u0001\u0004\u0012!9\u0011q\u0014\"\u0005\u0002\rU\u0001\"\u0003B6\u0005\u0006\u0005IQ\tB7\u0011%\u0011\u0019JQA\u0001\n\u0003\u001b9\u0002C\u0005\u0003\u001c\n\u000b\t\u0011\"!\u0004\u001c!I!q\u0016\"\u0002\u0002\u0013%!\u0011\u0017\u0004\u0007\u0007?\t\u0001a!\t\t\u0015\rM\u0002J!b\u0001\n\u0003\u0019)\u0004\u0003\u0006\u0004>!\u0013\t\u0011)A\u0005\u0007oA!\"!7I\u0005\u000b\u0007I\u0011AAn\u0011)\t\u0019\u000f\u0013B\u0001B\u0003%\u0011Q\u001c\u0005\b\u0003?CE\u0011AB \u0011\u001d\u00199\u0005\u0013C!\u0007\u0013Bqa!\u0016I\t\u0003\u001a9\u0006C\u0004\u0003l!#\tE!\u001c\u0007\r\re\u0013\u0001AB.\u0011)\tI.\u0015BC\u0002\u0013\u0005\u00111\u001c\u0005\u000b\u0003G\f&\u0011!Q\u0001\n\u0005u\u0007\u0002DB2#\n\u0005\t\u0015!\u0003\u00048\r\u0015\u0004\u0002DB4#\n\u0005\t\u0015!\u0003\u0004j\r=\u0004bBAP#\u0012\u000511\u000f\u0005\b\u0007{\nF\u0011IB@\r\u0019\u0019\t)\u0001\"\u0004\u0004\"Q1Q\u0011-\u0003\u0016\u0004%\taa\"\t\u0015\rm\u0005L!E!\u0002\u0013\u0019I\tC\u0004\u0002 b#\ta!(\t\u0013\u0005m\b,!A\u0005\u0002\r\r\u0006\"\u0003B\u00021F\u0005I\u0011ABT\u0011%\u0011\t\u0003WA\u0001\n\u0003\u0012\u0019\u0003C\u0005\u00036a\u000b\t\u0011\"\u0001\u0002\\\"I!q\u0007-\u0002\u0002\u0013\u000511\u0016\u0005\n\u0005\u000bB\u0016\u0011!C!\u0005\u000fB\u0011B!\u0016Y\u0003\u0003%\taa,\t\u0013\t\u0005\u0004,!A\u0005B\rM\u0006\"\u0003B41\u0006\u0005I\u0011\tB5\u0011%\u0011Y\u0007WA\u0001\n\u0003\u0012i\u0007C\u0005\u0003pa\u000b\t\u0011\"\u0011\u00048\u001eI11X\u0001\u0002\u0002#\u00051Q\u0018\u0004\n\u0007\u0003\u000b\u0011\u0011!E\u0001\u0007\u007fCq!a(i\t\u0003\u0019\u0019\rC\u0005\u0003l!\f\t\u0011\"\u0012\u0003n!I!1\u00135\u0002\u0002\u0013\u00055Q\u0019\u0005\n\u00057C\u0017\u0011!CA\u0007\u0013D\u0011Ba,i\u0003\u0003%IA!-\u0007\r\r=\u0017\u0001ABi\u0011)\u0019\u0019N\u001cB\u0001B\u0003%\u0011Q\u001c\u0005\u000b\u0003\u0003s'\u0011!Q\u0001\n\r]\u0002BCB9]\n\u0005\t\u0015!\u0003\u0004j!9\u0011q\u00148\u0005\u0002\rU\u0007\"CBp]\u0002\u0007I\u0011BBq\u0011%\u0019\u0019O\u001ca\u0001\n\u0013\u0019)\u000f\u0003\u0005\u0004j:\u0004\u000b\u0015\u0002B-\u0011%\u0019YO\u001cb\u0001\n\u0013\u0019i\u000f\u0003\u0005\u0004v:\u0004\u000b\u0011BBx\u0011%\u00199P\u001cb\u0001\n\u0013\u0019I\u0010\u0003\u0005\u0004~:\u0004\u000b\u0011BB~\u0011%\u0019yP\u001ca\u0001\n\u0013\tY\u000eC\u0005\u0005\u00029\u0004\r\u0011\"\u0003\u0005\u0004!AAq\u00018!B\u0013\ti\u000eC\u0005\u0005\n9\u0014\r\u0011\"\u0003\u0004z\"AA1\u00028!\u0002\u0013\u0019Y\u0010C\u0005\u0005\u000e9\u0004\r\u0011\"\u0003\u0002\\\"IAq\u00028A\u0002\u0013%A\u0011\u0003\u0005\t\t+q\u0007\u0015)\u0003\u0002^\"IAq\u00038C\u0002\u0013%1\u0011 \u0005\t\t3q\u0007\u0015!\u0003\u0004|\"IA1\u00048A\u0002\u0013%\u00111\u001c\u0005\n\t;q\u0007\u0019!C\u0005\t?A\u0001\u0002b\toA\u0003&\u0011Q\u001c\u0005\n\tKq'\u0019!C\u0005\u0007sD\u0001\u0002b\noA\u0003%11 \u0005\n\tSq'\u0019!C\u0005\u0007sD\u0001\u0002b\u000boA\u0003%11 \u0005\b\u0005WrG\u0011\tC\u0017\u0011%!iD\u001ca\u0001\n\u0013\u0019\t\u000fC\u0005\u0005@9\u0004\r\u0011\"\u0003\u0005B!AAQ\t8!B\u0013\u0011I\u0006C\u0005\u0005H9\u0004\r\u0011\"\u0003\u0002\\\"IA\u0011\n8A\u0002\u0013%A1\n\u0005\t\t\u001fr\u0007\u0015)\u0003\u0002^\"9A\u0011\u000b8\u0005\u0002\u0011M\u0003b\u0002C-]\u0012\u0005A1\f\u0005\b\t?rG\u0011\u0001C1\u0011\u001d!)G\u001cC\u0001\tOBq\u0001b\u001bo\t\u0003\u00199\u0006C\u0004\u0005l9$\t\u0001\"\u001c\t\u000f\rUc\u000e\"\u0001\u0005r!9AQ\u00108\u0005\u0002\u0011}\u0004b\u0002CC]\u0012\u0005Aq\u0011\u0005\b\t\u0017sG\u0011\u0001CG\u0011\u001d!\tJ\u001cC\u0001\u0007/Bq\u0001b%o\t\u0003\u00199\u0006C\u0004\u0005\u0016:$\t\u0001b&\t\u000f\u0011ue\u000e\"\u0001\u0003j!9Aq\u00148\u0005\u0002\u0011\u0005\u0006b\u0002CU]\u0012\u0005A1\u0016\u0005\b\t_sG\u0011\u0001B5\u0011\u001d!\tL\u001cC\u0001\tgCq\u0001b.o\t\u0003!I\fC\u0004\u0005B:$\t\u0001b1\t\u000f\u0011Ug\u000e\"\u0001\u0005X\"9Aq\u001d8\u0005\u0002\u0011%\b\"\u0003Cy]\n\u0007I\u0011\u0001Cz\u0011!!IP\u001cQ\u0001\n\u0011U\b\"\u0003C~]\n\u0007I\u0011\u0001C\u007f\u0011!)\u0019A\u001cQ\u0001\n\u0011}\bbBC\u0003]\u0012\u0005Qq\u0001\u0004\u000b\u0003\u001f\u000by(!\u0001\u0002\b\u0016u\u0001bCC\u0016\u00037\u0012\t\u0011)A\u0005\u000b[A1ba5\u0002\\\t\u0015\r\u0011\"\u0001\u0002\\\"YQQGA.\u0005\u0003\u0005\u000b\u0011BAo\u0011!\ty*a\u0017\u0005\u0002\u0015]\u0002BCC \u00037\u0012\r\u0011\"\u0005\u0006B!IQqIA.A\u0003%Q1\t\u0005\u000b\u000b\u0013\nYF1A\u0005\u0012\u0015-\u0003\"CC*\u00037\u0002\u000b\u0011BC'\u0011)))&a\u0017C\u0002\u0013%1\u0011\u001d\u0005\n\u000b/\nY\u0006)A\u0005\u00053B\u0001\"\"\u0017\u0002\\\u0011\u00053q\u000b\u0005\t\u000b7\nY\u0006\"\u0011\u0006^!AQ\u0011MA.\t#)\u0019\u0007\u0003\u0005\u0006h\u0005mC\u0011IB,\u0011!)I'a\u0017\u0005B\u0015-\u0004\u0002CC9\u00037\"\t!b\u001d\u0002\r\u0019\u000bgnT;u\u0015\u0011\t\t)a!\u0002\t%l\u0007\u000f\u001c\u0006\u0005\u0003\u000b\u000b9)\u0001\u0004tiJ,\u0017-\u001c\u0006\u0003\u0003\u0013\u000bA!Y6lCB\u0019\u0011QR\u0001\u000e\u0005\u0005}$A\u0002$b]>+HoE\u0002\u0002\u0003'\u0003B!!&\u0002\u001c6\u0011\u0011q\u0013\u0006\u0003\u00033\u000bQa]2bY\u0006LA!!(\u0002\u0018\n1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u0003\u0003\u0017\u0013AcU;cgR\u0014X-Y7SKF,Xm\u001d;N_J,7cC\u0002\u0002\u0014\u0006%\u0016QWA^\u0003\u0003\u0004B!a+\u000226\u0011\u0011Q\u0016\u0006\u0005\u0003_\u000b9)A\u0003bGR|'/\u0003\u0003\u00024\u00065&!\u0006#fC\u0012dU\r\u001e;feN+\b\u000f\u001d:fgNLwN\u001c\t\u0005\u0003W\u000b9,\u0003\u0003\u0002:\u00065&!\t(p'\u0016\u0014\u0018.\u00197ju\u0006$\u0018n\u001c8WKJLg-[2bi&|gNT3fI\u0016$\u0007\u0003BAK\u0003{KA!a0\u0002\u0018\n9\u0001K]8ek\u000e$\b\u0003BAb\u0003'tA!!2\u0002P:!\u0011qYAg\u001b\t\tIM\u0003\u0003\u0002L\u0006\u0005\u0016A\u0002\u001fs_>$h(\u0003\u0002\u0002\u001a&!\u0011\u0011[AL\u0003\u001d\u0001\u0018mY6bO\u0016LA!!6\u0002X\na1+\u001a:jC2L'0\u00192mK*!\u0011\u0011[AL\u0003\tIG-\u0006\u0002\u0002^B!\u0011QSAp\u0013\u0011\t\t/a&\u0003\u0007%sG/A\u0002jI\u0002\na\u0001Z3nC:$WCAAu!\u0011\t)*a;\n\t\u00055\u0018q\u0013\u0002\u0005\u0019>tw-A\u0004eK6\fg\u000e\u001a\u0011\u0015\r\u0005M\u0018q_A}!\r\t)pA\u0007\u0002\u0003!9\u0011\u0011\u001c\u0005A\u0002\u0005u\u0007bBAs\u0011\u0001\u0007\u0011\u0011^\u0001\u0005G>\u0004\u0018\u0010\u0006\u0004\u0002t\u0006}(\u0011\u0001\u0005\n\u00033L\u0001\u0013!a\u0001\u0003;D\u0011\"!:\n!\u0003\u0005\r!!;\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011!q\u0001\u0016\u0005\u0003;\u0014Ia\u000b\u0002\u0003\fA!!Q\u0002B\f\u001b\t\u0011yA\u0003\u0003\u0003\u0012\tM\u0011!C;oG\",7m[3e\u0015\u0011\u0011)\"a&\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0003\u001a\t=!!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012TC\u0001B\u0010U\u0011\tIO!\u0003\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\u0011)\u0003\u0005\u0003\u0003(\tERB\u0001B\u0015\u0015\u0011\u0011YC!\f\u0002\t1\fgn\u001a\u0006\u0003\u0005_\tAA[1wC&!!1\u0007B\u0015\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5us\u0006q\u0001O]8ek\u000e$X\t\\3nK:$H\u0003\u0002B\u001e\u0005\u0003\u0002B!!&\u0003>%!!qHAL\u0005\r\te.\u001f\u0005\n\u0005\u0007r\u0011\u0011!a\u0001\u0003;\f1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XC\u0001B%!\u0019\u0011YE!\u0015\u0003<5\u0011!Q\n\u0006\u0005\u0005\u001f\n9*\u0001\u0006d_2dWm\u0019;j_:LAAa\u0015\u0003N\tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\u0011IFa\u0018\u0011\t\u0005U%1L\u0005\u0005\u0005;\n9JA\u0004C_>dW-\u00198\t\u0013\t\r\u0003#!AA\u0002\tm\u0012A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$BA!\n\u0003f!I!1I\t\u0002\u0002\u0003\u0007\u0011Q\\\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u0011Q\\\u0001\ti>\u001cFO]5oOR\u0011!QE\u0001\u0007KF,\u0018\r\\:\u0015\t\te#1\u000f\u0005\n\u0005\u0007\"\u0012\u0011!a\u0001\u0005w\tAcU;cgR\u0014X-Y7SKF,Xm\u001d;N_J,\u0007cAA{-M)aCa\u001f\u0003\bBQ!Q\u0010BB\u0003;\fI/a=\u000e\u0005\t}$\u0002\u0002BA\u0003/\u000bqA];oi&lW-\u0003\u0003\u0003\u0006\n}$!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8oeA!!\u0011\u0012BH\u001b\t\u0011YI\u0003\u0003\u0003\u000e\n5\u0012AA5p\u0013\u0011\t)Na#\u0015\u0005\t]\u0014!B1qa2LHCBAz\u0005/\u0013I\nC\u0004\u0002Zf\u0001\r!!8\t\u000f\u0005\u0015\u0018\u00041\u0001\u0002j\u00069QO\\1qa2LH\u0003\u0002BP\u0005W\u0003b!!&\u0003\"\n\u0015\u0016\u0002\u0002BR\u0003/\u0013aa\u00149uS>t\u0007\u0003CAK\u0005O\u000bi.!;\n\t\t%\u0016q\u0013\u0002\u0007)V\u0004H.\u001a\u001a\t\u0013\t5&$!AA\u0002\u0005M\u0018a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011!1\u0017\t\u0005\u0005O\u0011),\u0003\u0003\u00038\n%\"AB(cU\u0016\u001cGOA\bTk\n\u001cHO]3b[\u000e\u000bgnY3m'-a\u00121SAU\u0003k\u000bY,!1\u0015\t\t}&\u0011\u0019\t\u0004\u0003kd\u0002bBAm?\u0001\u0007\u0011Q\u001c\u000b\u0005\u0005\u007f\u0013)\rC\u0005\u0002Z\u0002\u0002\n\u00111\u0001\u0002^R!!1\bBe\u0011%\u0011\u0019\u0005JA\u0001\u0002\u0004\ti\u000e\u0006\u0003\u0003Z\t5\u0007\"\u0003B\"M\u0005\u0005\t\u0019\u0001B\u001e)\u0011\u0011)C!5\t\u0013\t\rs%!AA\u0002\u0005uG\u0003\u0002B-\u0005+D\u0011Ba\u0011+\u0003\u0003\u0005\rAa\u000f\u0002\u001fM+(m\u001d;sK\u0006l7)\u00198dK2\u00042!!>-'\u0015a#Q\u001cBD!!\u0011iHa8\u0002^\n}\u0016\u0002\u0002Bq\u0005\u007f\u0012\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82)\t\u0011I\u000e\u0006\u0003\u0003@\n\u001d\bbBAm_\u0001\u0007\u0011Q\u001c\u000b\u0005\u0005W\u0014i\u000f\u0005\u0004\u0002\u0016\n\u0005\u0016Q\u001c\u0005\n\u0005[\u0003\u0014\u0011!a\u0001\u0005\u007f\u0013\u0011dU;cgR\u0014X-Y7Tk\n\u001c8M]5cKB+g\u000eZ5oONY!'a%\u0002*\u0006U\u00161XAa)\u0011\u0011)Pa>\u0011\u0007\u0005U(\u0007C\u0004\u0002ZV\u0002\r!!8\u0015\t\tU(1 \u0005\n\u000334\u0004\u0013!a\u0001\u0003;$BAa\u000f\u0003\u0000\"I!1\t\u001e\u0002\u0002\u0003\u0007\u0011Q\u001c\u000b\u0005\u00053\u001a\u0019\u0001C\u0005\u0003Dq\n\t\u00111\u0001\u0003<Q!!QEB\u0004\u0011%\u0011\u0019%PA\u0001\u0002\u0004\ti\u000e\u0006\u0003\u0003Z\r-\u0001\"\u0003B\"\u0001\u0006\u0005\t\u0019\u0001B\u001e\u0003e\u0019VOY:ue\u0016\fWnU;cg\u000e\u0014\u0018NY3QK:$\u0017N\\4\u0011\u0007\u0005U(iE\u0003C\u0007'\u00119\t\u0005\u0005\u0003~\t}\u0017Q\u001cB{)\t\u0019y\u0001\u0006\u0003\u0003v\u000ee\u0001bBAm\u000b\u0002\u0007\u0011Q\u001c\u000b\u0005\u0005W\u001ci\u0002C\u0005\u0003.\u001a\u000b\t\u00111\u0001\u0003v\n)2+\u001e2tiJ,\u0017-\\*vEN\u001c'/\u001b9uS>t7#\u0002%\u00034\u000e\r\u0002\u0003BB\u0013\u0007_i!aa\n\u000b\t\r%21F\u0001\u0010e\u0016\f7\r^5wKN$(/Z1ng*\u00111QF\u0001\u0004_J<\u0017\u0002BB\u0019\u0007O\u0011AbU;cg\u000e\u0014\u0018\u000e\u001d;j_:\fa\u0001]1sK:$XCAB\u001c!\u0011\tYk!\u000f\n\t\rm\u0012Q\u0016\u0002\t\u0003\u000e$xN\u001d*fM\u00069\u0001/\u0019:f]R\u0004CCBB!\u0007\u0007\u001a)\u0005E\u0002\u0002v\"Cqaa\rN\u0001\u0004\u00199\u0004C\u0004\u0002Z6\u0003\r!!8\u0002\u000fI,\u0017/^3tiR!11JB)!\u0011\t)j!\u0014\n\t\r=\u0013q\u0013\u0002\u0005+:LG\u000fC\u0004\u0004T9\u0003\r!!;\u0002\u0011\u0015dW-\\3oiN\faaY1oG\u0016dGCAB&\u000551\u0015M\\8vi>+H\u000f];ugN\u0019\u0011k!\u0018\u0011\t\u000555qL\u0005\u0005\u0007C\nyHA\u0007TS6\u0004H.Z(viB,Ho]\u0001\u0006?&l\u0007\u000f\\\u0005\u0005\u0003_\u001by&A\u0003`aVl\u0007\u000f\u0005\u0003\u0002\u000e\u000e-\u0014\u0002BB7\u0003\u007f\u0012A\u0001U;na&!1\u0011OB0\u0003\u0011\u0001X/\u001c9\u0015\u0011\rU4qOB=\u0007w\u00022!!>R\u0011\u001d\tIN\u0016a\u0001\u0003;Dqaa\u0019W\u0001\u0004\u00199\u0004C\u0004\u0004hY\u0003\ra!\u001b\u0002%\r\u0014X-\u0019;f'V\u00147o\u0019:jaRLwN\u001c\u000b\u0003\u0007G\u0011\u0011#\u0012=q_N,G\rU;cY&\u001c\b.\u001a:t'-A\u00161SAU\u0003k\u000bY,!1\u0002\u0015A,(\r\\5tQ\u0016\u00148/\u0006\u0002\u0004\nB111RBI\u0007+k!a!$\u000b\t\r=%QJ\u0001\nS6lW\u000f^1cY\u0016LAaa%\u0004\u000e\n\u00191+Z9\u0011\r\u000555q\u0013B\u001e\u0013\u0011\u0019I*a \u0003\u001d\u0005\u001bGo\u001c:Qk\nd\u0017n\u001d5fe\u0006Y\u0001/\u001e2mSNDWM]:!)\u0011\u0019yj!)\u0011\u0007\u0005U\b\fC\u0004\u0004\u0006n\u0003\ra!#\u0015\t\r}5Q\u0015\u0005\n\u0007\u000bc\u0006\u0013!a\u0001\u0007\u0013+\"a!++\t\r%%\u0011\u0002\u000b\u0005\u0005w\u0019i\u000bC\u0005\u0003D\u0001\f\t\u00111\u0001\u0002^R!!\u0011LBY\u0011%\u0011\u0019EYA\u0001\u0002\u0004\u0011Y\u0004\u0006\u0003\u0003&\rU\u0006\"\u0003B\"G\u0006\u0005\t\u0019AAo)\u0011\u0011If!/\t\u0013\t\rc-!AA\u0002\tm\u0012!E#ya>\u001cX\r\u001a)vE2L7\u000f[3sgB\u0019\u0011Q\u001f5\u0014\u000b!\u001c\tMa\"\u0011\u0011\tu$q\\BE\u0007?#\"a!0\u0015\t\r}5q\u0019\u0005\b\u0007\u000b[\u0007\u0019ABE)\u0011\u0019Ym!4\u0011\r\u0005U%\u0011UBE\u0011%\u0011i\u000b\\A\u0001\u0002\u0004\u0019yJA\u0006PkR\u0004X\u000f\u001e\"v]\u000eD7c\u00018\u0002\u0014\u0006Yq.\u001e;qkR\u001cu.\u001e8u)!\u00199n!7\u0004\\\u000eu\u0007cAA{]\"911\u001b:A\u0002\u0005u\u0007bBAAe\u0002\u00071q\u0007\u0005\b\u0007c\u0012\b\u0019AB5\u00039\u0011WO\\2i\u0007\u0006t7-\u001a7mK\u0012,\"A!\u0017\u0002%\t,hn\u00195DC:\u001cW\r\u001c7fI~#S-\u001d\u000b\u0005\u0007\u0017\u001a9\u000fC\u0005\u0003DQ\f\t\u00111\u0001\u0003Z\u0005y!-\u001e8dQ\u000e\u000bgnY3mY\u0016$\u0007%A\u0004pkR\u0004X\u000f^:\u0016\u0005\r=\bCBAK\u0007c\u001c)(\u0003\u0003\u0004t\u0006]%!B!se\u0006L\u0018\u0001C8viB,Ho\u001d\u0011\u0002\r5\f'o[3e+\t\u0019Y\u0010\u0005\u0004\u0002\u0016\u000eE(\u0011L\u0001\b[\u0006\u00148.\u001a3!\u0003-i\u0017M]6fI\u000e{WO\u001c;\u0002\u001f5\f'o[3e\u0007>,h\u000e^0%KF$Baa\u0013\u0005\u0006!I!1I>\u0002\u0002\u0003\u0007\u0011Q\\\u0001\r[\u0006\u00148.\u001a3D_VtG\u000fI\u0001\ba\u0016tG-\u001b8h\u0003!\u0001XM\u001c3j]\u001e\u0004\u0013!D7be.,G\rU3oI&tw-A\tnCJ\\W\r\u001a)f]\u0012LgnZ0%KF$Baa\u0013\u0005\u0014!Q!1IA\u0001\u0003\u0003\u0005\r!!8\u0002\u001d5\f'o[3e!\u0016tG-\u001b8hA\u0005I1-\u00198dK2dW\rZ\u0001\u000bG\u0006t7-\u001a7mK\u0012\u0004\u0013aD7be.,GmQ1oG\u0016dG.\u001a3\u0002'5\f'o[3e\u0007\u0006t7-\u001a7mK\u0012|F%Z9\u0015\t\r-C\u0011\u0005\u0005\u000b\u0005\u0007\nY!!AA\u0002\u0005u\u0017\u0001E7be.,GmQ1oG\u0016dG.\u001a3!\u0003%\u0019w.\u001c9mKR,G-\u0001\u0006d_6\u0004H.\u001a;fI\u0002\nq!\u001a:s_J,G-\u0001\u0005feJ|'/\u001a3!)\t!y\u0003\u0005\u0003\u00052\u0011eb\u0002\u0002C\u001a\tk\u0001B!a2\u0002\u0018&!AqGAL\u0003\u0019\u0001&/\u001a3fM&!!1\u0007C\u001e\u0015\u0011!9$a&\u0002\u001fUtW.\u0019:l\u0007\u0006t7-\u001a7mK\u0012\f1#\u001e8nCJ\\7)\u00198dK2dW\rZ0%KF$Baa\u0013\u0005D!Q!1IA\u000e\u0003\u0003\u0005\rA!\u0017\u0002!UtW.\u0019:l\u0007\u0006t7-\u001a7mK\u0012\u0004\u0013a\u00039sK\u001a,'O]3e\u0013\u0012\fq\u0002\u001d:fM\u0016\u0014(/\u001a3JI~#S-\u001d\u000b\u0005\u0007\u0017\"i\u0005\u0003\u0006\u0003D\u0005\u0005\u0012\u0011!a\u0001\u0003;\fA\u0002\u001d:fM\u0016\u0014(/\u001a3JI\u0002\n\u0011\"[:QK:$\u0017N\\4\u0015\t\teCQ\u000b\u0005\t\t/\n)\u00031\u0001\u0002^\u00061q.\u001e;qkR\f1\"[:D_6\u0004H.\u001a;fIR!!\u0011\fC/\u0011!!9&a\nA\u0002\u0005u\u0017aC5t\u0007\u0006t7-\u001a7mK\u0012$BA!\u0017\u0005d!AAqKA\u0015\u0001\u0004\ti.A\u0005jg\u0016\u0013(o\u001c:fIR!!\u0011\fC5\u0011!!9&a\u000bA\u0002\u0005u\u0017\u0001C2p[BdW\r^3\u0015\t\r-Cq\u000e\u0005\t\t/\ny\u00031\u0001\u0002^R!11\nC:\u0011!!)(!\rA\u0002\u0011]\u0014!A3\u0011\t\u0005\rG\u0011P\u0005\u0005\tw\n9NA\u0005UQJ|w/\u00192mK\u0006)QM\u001d:peR111\nCA\t\u0007C\u0001\u0002b\u0016\u00024\u0001\u0007\u0011Q\u001c\u0005\t\tk\n\u0019\u00041\u0001\u0005x\u0005QQ.\u0019:l\u001fV$\b/\u001e;\u0015\t\r-C\u0011\u0012\u0005\t\t/\n)\u00041\u0001\u0002^\u0006aQO\\7be.|U\u000f\u001e9viR!11\nCH\u0011!!9&a\u000eA\u0002\u0005u\u0017AD7be.\fE\u000e\\(viB,Ho]\u0001\u0011k:l\u0017M]6BY2|U\u000f\u001e9viN\fa#\u001e8nCJ\\7)\u00198dK2dW\rZ(viB,Ho\u001d\u000b\u0005\u0007\u0017\"I\n\u0003\u0005\u0005\u001c\u0006u\u0002\u0019\u0001B-\u0003\u001d)g.\u00192mK\u0012\f1\"\u001b3U_\u0016s\u0017/^3vK\u00069QM\\9vKV,GCBB&\tG#)\u000b\u0003\u0005\u0002Z\u0006\u0005\u0003\u0019AAo\u0011!!9+!\u0011A\u0002\tm\u0012\u0001B3mK6\fQ\"\u001a8rk\u0016,X-T1sW\u0016$G\u0003BB&\t[C\u0001\u0002b*\u0002D\u0001\u0007!1H\u0001\u0014S\u0012$v.\u00128rk\u0016,X-\u00118e3&,G\u000eZ\u0001\u0010K:\fX/Z;f\u0003:$\u0017,[3mIR!11\nC[\u0011!!9+a\u0012A\u0002\tm\u0012\u0001E3ocV,W/Z!oIB\u0013XMZ3s)\u0019\u0019Y\u0005b/\u0005>\"AAqUA%\u0001\u0004\u0011Y\u0004\u0003\u0005\u0005@\u0006%\u0003\u0019AAo\u0003%\u0001(/\u001a4feJ,G-\u0001\u0005p]\u000e\u000bgnY3m)\u0011\u0019Y\u0005\"2\t\u0011\u0011]\u00131\na\u0001\u0003;DC\u0001\"2\u0005JB!A1\u001aCi\u001b\t!iM\u0003\u0003\u0005P\u0006\u001d\u0015\u0001B;uS2LA\u0001b5\u0005N\n1QO\\;tK\u0012\f!\u0003Z3nC:$\u0017I^1jY\u0006\u0014G.\u001a$peR!A\u0011\u001cCs%\u0019!Y.a%\u0005`\u001a9AQ\\A'\u0001\u0011e'\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004\u0003BAG\tCLA\u0001b9\u0002\u0000\tiAK]1og\u001a,'o\u0015;bi\u0016D\u0001\"!7\u0002N\u0001\u0007\u0011Q\\\u0001\u001bI\u0016l\u0017M\u001c3Pe\u000e\u000bgnY3m\u0003Z\f\u0017\u000e\\1cY\u00164uN\u001d\u000b\u0005\tW$yO\u0005\u0004\u0005n\u0006MEq\u001c\u0004\b\t;\fy\u0005\u0001Cv\u0011!\tI.a\u0014A\u0002\u0005u\u0017AE!mY>3W*\u0019:lK\u0012|U\u000f\u001e9viN,\"\u0001\">\u0013\r\u0011]\u00181\u0013Cp\r\u001d!i.a\u0015\u0001\tk\f1#\u00117m\u001f\u001al\u0015M]6fI>+H\u000f];ug\u0002\n!#\u00118z\u001f\u001al\u0015M]6fI>+H\u000f];ugV\u0011Aq \n\u0007\u000b\u0003\t\u0019\nb8\u0007\u000f\u0011u\u0017q\u000b\u0001\u0005\u0000\u0006\u0019\u0012I\\=PM6\u000b'o[3e\u001fV$\b/\u001e;tA\u0005Q1/\u001e2sK\u000e,\u0017N^3\u0016\u0005\u0015%\u0001\u0003BAG\u000b\u0017IA!\"\u0004\u0002\u0000\tQ1+\u001e2SK\u000e,\u0017N^3)\u0007\u0005)\t\u0002\u0005\u0003\u0006\u0014\u0015]QBAC\u000b\u0015\u0011\u0011)\"a\"\n\t\u0015eQQ\u0003\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000eK\u0002\u0001\u000b#\u0019\"\"a\u0017\u0002\u0014\u0016}QQEB5!\u0011\tY+\"\t\n\t\u0015\r\u0012Q\u0016\u0002\u0006\u0003\u000e$xN\u001d\t\u0005\u0003W+9#\u0003\u0003\u0006*\u00055&\u0001D!di>\u0014Hj\\4hS:<\u0017AC1uiJL'-\u001e;fgB!QqFC\u0019\u001b\t\t\u0019)\u0003\u0003\u00064\u0005\r%AC!uiJL'-\u001e;fg\u0006aq.\u001e;qkR\u001cu.\u001e8uAQ1Q\u0011HC\u001e\u000b{\u0001B!!$\u0002\\!AQ1FA2\u0001\u0004)i\u0003\u0003\u0005\u0004T\u0006\r\u0004\u0019AAo\u0003-yW\u000f\u001e9vi\n+hn\u00195\u0016\u0005\u0015\r\u0003cAC#]:\u0019\u0011Q\u0012\u0001\u0002\u0019=,H\u000f];u\u0005Vt7\r\u001b\u0011\u0002\u001bA\u0014\u0018.\\1ss&s\u0007/\u001e;t+\t)i\u0005\u0005\u0003\u0002\u000e\u0016=\u0013\u0002BC)\u0003\u007f\u0012a!\u00138qkR\u001c\u0018A\u00049sS6\f'/_%oaV$8\u000fI\u0001\u0014I\u0016\u0014Wo\u001a'pO\u001eLgnZ#oC\ndW\rZ\u0001\u0015I\u0016\u0014Wo\u001a'pO\u001eLgnZ#oC\ndW\r\u001a\u0011\u0002\u0019A,X\u000e\u001d$j]&\u001c\b.\u001a3\u0002\u0015A,X\u000e\u001d$bS2,G\r\u0006\u0003\u0004L\u0015}\u0003\u0002\u0003C;\u0003g\u0002\r\u0001b\u001e\u0002\t\u0019\f\u0017\u000e\u001c\u000b\u0005\u0007\u0017*)\u0007\u0003\u0005\u0005v\u0005U\u0004\u0019\u0001C<\u0003!\u0001xn\u001d;Ti>\u0004\u0018a\u00039pgR\u0014Vm\u001d;beR$Baa\u0013\u0006n!AQqNA=\u0001\u0004!9(\u0001\u0004sK\u0006\u001cxN\\\u0001\be\u0016\u001cW-\u001b<f+\t))\b\u0005\u0005\u0002\u0016\u0016]$1HB&\u0013\u0011)I(a&\u0003\u001fA\u000b'\u000f^5bY\u001a+hn\u0019;j_:DC!a\u0017\u0006~A!Q1CC@\u0013\u0011)\t)\"\u0006\u0003\u0019\u0011{gj\u001c;J]\",'/\u001b;")
@DoNotInherit
public abstract class FanOut
implements Actor,
ActorLogging,
Pump {
    private final int outputCount;
    private final OutputBunch outputBunch;
    private final Inputs primaryInputs;
    private final boolean debugLoggingEnabled;
    private TransferState akka$stream$impl$Pump$$transferState;
    private Function0<BoxedUnit> akka$stream$impl$Pump$$currentAction;
    private TransferPhase completedPhase;
    private LoggingAdapter akka$actor$ActorLogging$$_log;
    private ActorContext context;
    private ActorRef self;

    @Override
    public final void initialPhase(int waitForUpstream, TransferPhase andThen) {
        Pump.initialPhase$(this, waitForUpstream, andThen);
    }

    @Override
    public final void waitForUpstreams(int waitForUpstream) {
        Pump.waitForUpstreams$(this, waitForUpstream);
    }

    @Override
    public void gotUpstreamSubscription() {
        Pump.gotUpstreamSubscription$(this);
    }

    @Override
    public final void nextPhase(TransferPhase phase) {
        Pump.nextPhase$(this, phase);
    }

    @Override
    public final boolean isPumpFinished() {
        return Pump.isPumpFinished$(this);
    }

    @Override
    public final void pump() {
        Pump.pump$(this);
    }

    @Override
    public LoggingAdapter log() {
        return ActorLogging.log$(this);
    }

    @Override
    public final ActorRef sender() {
        return Actor.sender$(this);
    }

    @Override
    @InternalApi
    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
        Actor.aroundReceive$(this, receive2, msg);
    }

    @Override
    @InternalApi
    public void aroundPreStart() {
        Actor.aroundPreStart$(this);
    }

    @Override
    @InternalApi
    public void aroundPostStop() {
        Actor.aroundPostStop$(this);
    }

    @Override
    @InternalApi
    public void aroundPreRestart(Throwable reason, Option<Object> message) {
        Actor.aroundPreRestart$(this, reason, message);
    }

    @Override
    @InternalApi
    public void aroundPostRestart(Throwable reason) {
        Actor.aroundPostRestart$(this, reason);
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return Actor.supervisorStrategy$(this);
    }

    @Override
    public void preStart() throws Exception {
        Actor.preStart$(this);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        Actor.preRestart$(this, reason, message);
    }

    @Override
    public void unhandled(Object message) {
        Actor.unhandled$(this, message);
    }

    @Override
    public TransferState akka$stream$impl$Pump$$transferState() {
        return this.akka$stream$impl$Pump$$transferState;
    }

    @Override
    public void akka$stream$impl$Pump$$transferState_$eq(TransferState x$1) {
        this.akka$stream$impl$Pump$$transferState = x$1;
    }

    @Override
    public Function0<BoxedUnit> akka$stream$impl$Pump$$currentAction() {
        return this.akka$stream$impl$Pump$$currentAction;
    }

    @Override
    public void akka$stream$impl$Pump$$currentAction_$eq(Function0<BoxedUnit> x$1) {
        this.akka$stream$impl$Pump$$currentAction = x$1;
    }

    @Override
    public final TransferPhase completedPhase() {
        return this.completedPhase;
    }

    @Override
    public final void akka$stream$impl$Pump$_setter_$completedPhase_$eq(TransferPhase x$1) {
        this.completedPhase = x$1;
    }

    @Override
    public LoggingAdapter akka$actor$ActorLogging$$_log() {
        return this.akka$actor$ActorLogging$$_log;
    }

    @Override
    public void akka$actor$ActorLogging$$_log_$eq(LoggingAdapter x$1) {
        this.akka$actor$ActorLogging$$_log = x$1;
    }

    @Override
    public ActorContext context() {
        return this.context;
    }

    @Override
    public final ActorRef self() {
        return this.self;
    }

    @Override
    public void akka$actor$Actor$_setter_$context_$eq(ActorContext x$1) {
        this.context = x$1;
    }

    @Override
    public final void akka$actor$Actor$_setter_$self_$eq(ActorRef x$1) {
        this.self = x$1;
    }

    public int outputCount() {
        return this.outputCount;
    }

    public OutputBunch outputBunch() {
        return this.outputBunch;
    }

    public Inputs primaryInputs() {
        return this.primaryInputs;
    }

    private boolean debugLoggingEnabled() {
        return this.debugLoggingEnabled;
    }

    @Override
    public void pumpFinished() {
        this.primaryInputs().cancel();
        this.outputBunch().complete();
        this.context().stop(this.self());
    }

    @Override
    public void pumpFailed(Throwable e) {
        this.fail(e);
    }

    public void fail(Throwable e) {
        if (this.debugLoggingEnabled()) {
            this.log().debug("fail due to: {}", e.getMessage());
        }
        this.primaryInputs().cancel();
        this.outputBunch().cancel(e);
        this.pump();
    }

    @Override
    public void postStop() {
        this.primaryInputs().cancel();
        this.outputBunch().cancel(new AbruptTerminationException(this.self()));
    }

    @Override
    public void postRestart(Throwable reason) {
        Actor.postRestart$(this, reason);
        throw new IllegalStateException("This actor cannot be restarted");
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return this.primaryInputs().subreceive().orElse(this.outputBunch().subreceive());
    }

    public FanOut(Attributes attributes, int outputCount) {
        this.outputCount = outputCount;
        Actor.$init$(this);
        ActorLogging.$init$(this);
        Pump.$init$(this);
        this.outputBunch = new OutputBunch(outputCount, this.self(), this);
        int maxInputBufferSize = ((Attributes.InputBuffer)attributes.mandatoryAttribute(ClassTag$.MODULE$.apply(Attributes.InputBuffer.class))).max();
        this.primaryInputs = new BatchingInputBuffer(this, maxInputBufferSize){
            private final /* synthetic */ FanOut $outer;

            public void onError(Throwable e) {
                this.$outer.fail(e);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super(maxInputBufferSize$1, $outer);
            }
        };
        this.debugLoggingEnabled = ((ActorAttributes.DebugLogging)attributes.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.DebugLogging.class))).enabled();
        Statics.releaseFence();
    }

    public static final class ExposedPublishers
    implements DeadLetterSuppression,
    NoSerializationVerificationNeeded,
    Product,
    Serializable {
        private final Seq<ActorPublisher<Object>> publishers;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Seq<ActorPublisher<Object>> publishers() {
            return this.publishers;
        }

        public ExposedPublishers copy(Seq<ActorPublisher<Object>> publishers) {
            return new ExposedPublishers(publishers);
        }

        public Seq<ActorPublisher<Object>> copy$default$1() {
            return this.publishers();
        }

        @Override
        public String productPrefix() {
            return "ExposedPublishers";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.publishers();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof ExposedPublishers;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "publishers";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof ExposedPublishers)) return false;
            boolean bl = true;
            if (!bl) return false;
            ExposedPublishers exposedPublishers = (ExposedPublishers)x$1;
            Seq<ActorPublisher<Object>> seq = this.publishers();
            Seq<ActorPublisher<Object>> seq2 = exposedPublishers.publishers();
            if (seq != null) {
                if (!seq.equals(seq2)) return false;
                return true;
            }
            if (seq2 == null) return true;
            return false;
        }

        public ExposedPublishers(Seq<ActorPublisher<Object>> publishers) {
            this.publishers = publishers;
            Product.$init$(this);
        }
    }

    public static class FanoutOutputs
    extends SimpleOutputs {
        private final int id;

        public int id() {
            return this.id;
        }

        @Override
        public Subscription createSubscription() {
            return new SubstreamSubscription(this.actor(), this.id());
        }

        public FanoutOutputs(int id, ActorRef _impl, Pump _pump) {
            this.id = id;
            super(_impl, _pump);
        }
    }

    public static class OutputBunch {
        private final int outputCount;
        private final ActorRef impl;
        private final Pump pump;
        private boolean bunchCancelled;
        private final FanoutOutputs[] akka$stream$impl$FanOut$OutputBunch$$outputs;
        private final boolean[] akka$stream$impl$FanOut$OutputBunch$$marked;
        private int akka$stream$impl$FanOut$OutputBunch$$markedCount;
        private final boolean[] akka$stream$impl$FanOut$OutputBunch$$pending;
        private int akka$stream$impl$FanOut$OutputBunch$$markedPending;
        private final boolean[] akka$stream$impl$FanOut$OutputBunch$$cancelled;
        private int akka$stream$impl$FanOut$OutputBunch$$markedCancelled;
        private final boolean[] akka$stream$impl$FanOut$OutputBunch$$completed;
        private final boolean[] akka$stream$impl$FanOut$OutputBunch$$errored;
        private boolean akka$stream$impl$FanOut$OutputBunch$$unmarkCancelled;
        private int preferredId;
        private final TransferState AllOfMarkedOutputs;
        private final TransferState AnyOfMarkedOutputs;

        private boolean bunchCancelled() {
            return this.bunchCancelled;
        }

        private void bunchCancelled_$eq(boolean x$1) {
            this.bunchCancelled = x$1;
        }

        public FanoutOutputs[] akka$stream$impl$FanOut$OutputBunch$$outputs() {
            return this.akka$stream$impl$FanOut$OutputBunch$$outputs;
        }

        public boolean[] akka$stream$impl$FanOut$OutputBunch$$marked() {
            return this.akka$stream$impl$FanOut$OutputBunch$$marked;
        }

        public int akka$stream$impl$FanOut$OutputBunch$$markedCount() {
            return this.akka$stream$impl$FanOut$OutputBunch$$markedCount;
        }

        private void markedCount_$eq(int x$1) {
            this.akka$stream$impl$FanOut$OutputBunch$$markedCount = x$1;
        }

        public boolean[] akka$stream$impl$FanOut$OutputBunch$$pending() {
            return this.akka$stream$impl$FanOut$OutputBunch$$pending;
        }

        public int akka$stream$impl$FanOut$OutputBunch$$markedPending() {
            return this.akka$stream$impl$FanOut$OutputBunch$$markedPending;
        }

        public void akka$stream$impl$FanOut$OutputBunch$$markedPending_$eq(int x$1) {
            this.akka$stream$impl$FanOut$OutputBunch$$markedPending = x$1;
        }

        public boolean[] akka$stream$impl$FanOut$OutputBunch$$cancelled() {
            return this.akka$stream$impl$FanOut$OutputBunch$$cancelled;
        }

        public int akka$stream$impl$FanOut$OutputBunch$$markedCancelled() {
            return this.akka$stream$impl$FanOut$OutputBunch$$markedCancelled;
        }

        public void akka$stream$impl$FanOut$OutputBunch$$markedCancelled_$eq(int x$1) {
            this.akka$stream$impl$FanOut$OutputBunch$$markedCancelled = x$1;
        }

        public boolean[] akka$stream$impl$FanOut$OutputBunch$$completed() {
            return this.akka$stream$impl$FanOut$OutputBunch$$completed;
        }

        public boolean[] akka$stream$impl$FanOut$OutputBunch$$errored() {
            return this.akka$stream$impl$FanOut$OutputBunch$$errored;
        }

        public String toString() {
            return StringOps$.MODULE$.stripMargin$extension(Predef$.MODULE$.augmentString(new StringBuilder(184).append("|OutputBunch\n          |  marked:    ").append(Predef$.MODULE$.wrapBooleanArray(this.akka$stream$impl$FanOut$OutputBunch$$marked()).mkString(", ")).append("\n          |  pending:   ").append(Predef$.MODULE$.wrapBooleanArray(this.akka$stream$impl$FanOut$OutputBunch$$pending()).mkString(", ")).append("\n          |  errored:   ").append(Predef$.MODULE$.wrapBooleanArray(this.akka$stream$impl$FanOut$OutputBunch$$errored()).mkString(", ")).append("\n          |  completed: ").append(Predef$.MODULE$.wrapBooleanArray(this.akka$stream$impl$FanOut$OutputBunch$$completed()).mkString(", ")).append("\n          |  cancelled: ").append(Predef$.MODULE$.wrapBooleanArray(this.akka$stream$impl$FanOut$OutputBunch$$cancelled()).mkString(", ")).append("\n          |    mark=").append(this.akka$stream$impl$FanOut$OutputBunch$$markedCount()).append(" pend=").append(this.akka$stream$impl$FanOut$OutputBunch$$markedPending()).append(" depl=").append(this.akka$stream$impl$FanOut$OutputBunch$$markedCancelled()).append(" pref=").append(this.preferredId()).append(" unmark=").append(this.akka$stream$impl$FanOut$OutputBunch$$unmarkCancelled()).toString()));
        }

        public boolean akka$stream$impl$FanOut$OutputBunch$$unmarkCancelled() {
            return this.akka$stream$impl$FanOut$OutputBunch$$unmarkCancelled;
        }

        private void unmarkCancelled_$eq(boolean x$1) {
            this.akka$stream$impl$FanOut$OutputBunch$$unmarkCancelled = x$1;
        }

        private int preferredId() {
            return this.preferredId;
        }

        private void preferredId_$eq(int x$1) {
            this.preferredId = x$1;
        }

        public boolean isPending(int output) {
            return this.akka$stream$impl$FanOut$OutputBunch$$pending()[output];
        }

        public boolean isCompleted(int output) {
            return this.akka$stream$impl$FanOut$OutputBunch$$completed()[output];
        }

        public boolean isCancelled(int output) {
            return this.akka$stream$impl$FanOut$OutputBunch$$cancelled()[output];
        }

        public boolean isErrored(int output) {
            return this.akka$stream$impl$FanOut$OutputBunch$$errored()[output];
        }

        public void complete() {
            if (!this.bunchCancelled()) {
                this.bunchCancelled_$eq(true);
                for (int i = 0; i < this.akka$stream$impl$FanOut$OutputBunch$$outputs().length; ++i) {
                    this.complete(i);
                }
                return;
            }
        }

        public void complete(int output) {
            if (!(this.akka$stream$impl$FanOut$OutputBunch$$completed()[output] || this.akka$stream$impl$FanOut$OutputBunch$$errored()[output] || this.akka$stream$impl$FanOut$OutputBunch$$cancelled()[output])) {
                this.akka$stream$impl$FanOut$OutputBunch$$outputs()[output].complete();
                this.akka$stream$impl$FanOut$OutputBunch$$completed()[output] = true;
                this.unmarkOutput(output);
                return;
            }
        }

        public void cancel(Throwable e) {
            if (!this.bunchCancelled()) {
                this.bunchCancelled_$eq(true);
                for (int i = 0; i < this.akka$stream$impl$FanOut$OutputBunch$$outputs().length; ++i) {
                    this.error(i, e);
                }
                return;
            }
        }

        public void error(int output, Throwable e) {
            if (!(this.akka$stream$impl$FanOut$OutputBunch$$errored()[output] || this.akka$stream$impl$FanOut$OutputBunch$$cancelled()[output] || this.akka$stream$impl$FanOut$OutputBunch$$completed()[output])) {
                this.akka$stream$impl$FanOut$OutputBunch$$outputs()[output].error(e);
                this.akka$stream$impl$FanOut$OutputBunch$$errored()[output] = true;
                this.unmarkOutput(output);
                return;
            }
        }

        public void markOutput(int output) {
            if (!this.akka$stream$impl$FanOut$OutputBunch$$marked()[output]) {
                if (this.akka$stream$impl$FanOut$OutputBunch$$cancelled()[output]) {
                    this.akka$stream$impl$FanOut$OutputBunch$$markedCancelled_$eq(this.akka$stream$impl$FanOut$OutputBunch$$markedCancelled() + 1);
                }
                if (this.akka$stream$impl$FanOut$OutputBunch$$pending()[output]) {
                    this.akka$stream$impl$FanOut$OutputBunch$$markedPending_$eq(this.akka$stream$impl$FanOut$OutputBunch$$markedPending() + 1);
                }
                this.akka$stream$impl$FanOut$OutputBunch$$marked()[output] = true;
                this.markedCount_$eq(this.akka$stream$impl$FanOut$OutputBunch$$markedCount() + 1);
                return;
            }
        }

        public void unmarkOutput(int output) {
            if (this.akka$stream$impl$FanOut$OutputBunch$$marked()[output]) {
                if (this.akka$stream$impl$FanOut$OutputBunch$$cancelled()[output]) {
                    this.akka$stream$impl$FanOut$OutputBunch$$markedCancelled_$eq(this.akka$stream$impl$FanOut$OutputBunch$$markedCancelled() - 1);
                }
                if (this.akka$stream$impl$FanOut$OutputBunch$$pending()[output]) {
                    this.akka$stream$impl$FanOut$OutputBunch$$markedPending_$eq(this.akka$stream$impl$FanOut$OutputBunch$$markedPending() - 1);
                }
                this.akka$stream$impl$FanOut$OutputBunch$$marked()[output] = false;
                this.markedCount_$eq(this.akka$stream$impl$FanOut$OutputBunch$$markedCount() - 1);
                return;
            }
        }

        public void markAllOutputs() {
            for (int i = 0; i < this.outputCount; ++i) {
                this.markOutput(i);
            }
        }

        public void unmarkAllOutputs() {
            for (int i = 0; i < this.outputCount; ++i) {
                this.unmarkOutput(i);
            }
        }

        public void unmarkCancelledOutputs(boolean enabled) {
            this.unmarkCancelled_$eq(enabled);
        }

        public int idToEnqueue() {
            int id = this.preferredId();
            while (!this.akka$stream$impl$FanOut$OutputBunch$$marked()[id] || !this.akka$stream$impl$FanOut$OutputBunch$$pending()[id]) {
                if (++id == this.outputCount) {
                    id = 0;
                }
                Predef$.MODULE$.require(id != this.preferredId(), (Function0<Object>)(Function0<String> & Serializable)() -> "Tried to enqueue without waiting for any demand");
            }
            return id;
        }

        public void enqueue(int id, Object elem) {
            FanoutOutputs output = this.akka$stream$impl$FanOut$OutputBunch$$outputs()[id];
            output.enqueueOutputElement(elem);
            if (!output.demandAvailable()) {
                if (this.akka$stream$impl$FanOut$OutputBunch$$marked()[id]) {
                    this.akka$stream$impl$FanOut$OutputBunch$$markedPending_$eq(this.akka$stream$impl$FanOut$OutputBunch$$markedPending() - 1);
                }
                this.akka$stream$impl$FanOut$OutputBunch$$pending()[id] = false;
                return;
            }
        }

        public void enqueueMarked(Object elem) {
            for (int id = 0; id < this.outputCount; ++id) {
                if (!this.akka$stream$impl$FanOut$OutputBunch$$marked()[id]) continue;
                this.enqueue(id, elem);
            }
        }

        public int idToEnqueueAndYield() {
            int id;
            block0: {
                id = this.idToEnqueue();
                this.preferredId_$eq(id + 1);
                if (this.preferredId() != this.outputCount) break block0;
                this.preferredId_$eq(0);
            }
            return id;
        }

        public void enqueueAndYield(Object elem) {
            int id = this.idToEnqueueAndYield();
            this.enqueue(id, elem);
        }

        public void enqueueAndPrefer(Object elem, int preferred) {
            int id = this.idToEnqueue();
            this.preferredId_$eq(preferred);
            this.enqueue(id, elem);
        }

        public void onCancel(int output) {
        }

        public TransferState demandAvailableFor(int id) {
            return new TransferState(this, id){
                private final /* synthetic */ OutputBunch $outer;
                private final int id$1;

                public boolean isExecutable() {
                    return TransferState.isExecutable$(this);
                }

                public TransferState $bar$bar(TransferState other) {
                    return TransferState.$bar$bar$(this, other);
                }

                public TransferState $amp$amp(TransferState other) {
                    return TransferState.$amp$amp$(this, other);
                }

                public boolean isCompleted() {
                    return this.$outer.akka$stream$impl$FanOut$OutputBunch$$cancelled()[this.id$1] || this.$outer.akka$stream$impl$FanOut$OutputBunch$$completed()[this.id$1] || this.$outer.akka$stream$impl$FanOut$OutputBunch$$errored()[this.id$1];
                }

                public boolean isReady() {
                    return this.$outer.akka$stream$impl$FanOut$OutputBunch$$pending()[this.id$1];
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    this.id$1 = id$1;
                    TransferState.$init$(this);
                }
            };
        }

        public TransferState demandOrCancelAvailableFor(int id) {
            return new TransferState(this, id){
                private final /* synthetic */ OutputBunch $outer;
                private final int id$2;

                public boolean isExecutable() {
                    return TransferState.isExecutable$(this);
                }

                public TransferState $bar$bar(TransferState other) {
                    return TransferState.$bar$bar$(this, other);
                }

                public TransferState $amp$amp(TransferState other) {
                    return TransferState.$amp$amp$(this, other);
                }

                public boolean isCompleted() {
                    return false;
                }

                public boolean isReady() {
                    return this.$outer.akka$stream$impl$FanOut$OutputBunch$$pending()[this.id$2] || this.$outer.akka$stream$impl$FanOut$OutputBunch$$cancelled()[this.id$2];
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    this.id$2 = id$2;
                    TransferState.$init$(this);
                }
            };
        }

        public TransferState AllOfMarkedOutputs() {
            return this.AllOfMarkedOutputs;
        }

        public TransferState AnyOfMarkedOutputs() {
            return this.AnyOfMarkedOutputs;
        }

        public SubReceive subreceive() {
            return new SubReceive((PartialFunction<Object, BoxedUnit>)((Object)new Serializable(this){
                private static final long serialVersionUID = 0L;
                private final /* synthetic */ OutputBunch $outer;

                public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                    A1 A1 = x1;
                    if (A1 instanceof ExposedPublishers) {
                        ExposedPublishers exposedPublishers = (ExposedPublishers)A1;
                        Seq<ActorPublisher<Object>> publishers = exposedPublishers.publishers();
                        ((IterableOnceOps)publishers.zip(Predef$.MODULE$.wrapRefArray((Object[])this.$outer.akka$stream$impl$FanOut$OutputBunch$$outputs()))).foreach((Function1<Tuple2, Object> & Serializable)x0$1 -> {
                            OutputBunch$$anonfun$subreceive$1.$anonfun$applyOrElse$1(x0$1);
                            return BoxedUnit.UNIT;
                        });
                        return (B1)BoxedUnit.UNIT;
                    }
                    if (A1 instanceof SubstreamRequestMore) {
                        SubstreamRequestMore substreamRequestMore = (SubstreamRequestMore)A1;
                        int id = substreamRequestMore.id();
                        long demand = substreamRequestMore.demand();
                        if (demand < 1L) {
                            this.$outer.error(id, ReactiveStreamsCompliance$.MODULE$.numberOfElementsInRequestMustBePositiveException());
                            return (B1)BoxedUnit.UNIT;
                        }
                        if (this.$outer.akka$stream$impl$FanOut$OutputBunch$$marked()[id] && !this.$outer.akka$stream$impl$FanOut$OutputBunch$$pending()[id]) {
                            this.$outer.akka$stream$impl$FanOut$OutputBunch$$markedPending_$eq(this.$outer.akka$stream$impl$FanOut$OutputBunch$$markedPending() + 1);
                        }
                        this.$outer.akka$stream$impl$FanOut$OutputBunch$$pending()[id] = true;
                        this.$outer.akka$stream$impl$FanOut$OutputBunch$$outputs()[id].subreceive().apply(new RequestMore<T>(null, demand));
                        return (B1)BoxedUnit.UNIT;
                    }
                    if (A1 instanceof SubstreamCancel) {
                        SubstreamCancel substreamCancel = (SubstreamCancel)A1;
                        int id = substreamCancel.id();
                        if (this.$outer.akka$stream$impl$FanOut$OutputBunch$$unmarkCancelled()) {
                            this.$outer.unmarkOutput(id);
                        }
                        if (this.$outer.akka$stream$impl$FanOut$OutputBunch$$marked()[id] && !this.$outer.akka$stream$impl$FanOut$OutputBunch$$cancelled()[id]) {
                            this.$outer.akka$stream$impl$FanOut$OutputBunch$$markedCancelled_$eq(this.$outer.akka$stream$impl$FanOut$OutputBunch$$markedCancelled() + 1);
                        }
                        this.$outer.akka$stream$impl$FanOut$OutputBunch$$cancelled()[id] = true;
                        this.$outer.onCancel(id);
                        this.$outer.akka$stream$impl$FanOut$OutputBunch$$outputs()[id].subreceive().apply(new Cancel<T>(null));
                        return (B1)BoxedUnit.UNIT;
                    }
                    if (A1 instanceof SubstreamSubscribePending) {
                        SubstreamSubscribePending substreamSubscribePending = (SubstreamSubscribePending)A1;
                        int id = substreamSubscribePending.id();
                        this.$outer.akka$stream$impl$FanOut$OutputBunch$$outputs()[id].subreceive().apply(SubscribePending$.MODULE$);
                        return (B1)BoxedUnit.UNIT;
                    }
                    return function1.apply(x1);
                }

                public final boolean isDefinedAt(Object x1) {
                    Object object = x1;
                    if (object instanceof ExposedPublishers) {
                        return true;
                    }
                    if (object instanceof SubstreamRequestMore) {
                        return true;
                    }
                    if (object instanceof SubstreamCancel) {
                        return true;
                    }
                    return object instanceof SubstreamSubscribePending;
                }

                public static final /* synthetic */ void $anonfun$applyOrElse$1(Tuple2 x0$1) {
                    Tuple2 tuple2 = x0$1;
                    if (tuple2 != null) {
                        ActorPublisher pub = (ActorPublisher)tuple2._1();
                        FanoutOutputs output = (FanoutOutputs)tuple2._2();
                        output.subreceive().apply(new ExposedPublisher(pub));
                        return;
                    }
                    throw new MatchError(tuple2);
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                }

                private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                    return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1$adapted(scala.Tuple2 )}, serializedLambda);
                }
            }));
        }

        public static final /* synthetic */ FanoutOutputs $anonfun$outputs$1(OutputBunch $this, int x$1) {
            return new FanoutOutputs(x$1, $this.impl, $this.pump);
        }

        public OutputBunch(int outputCount, ActorRef impl, Pump pump) {
            this.outputCount = outputCount;
            this.impl = impl;
            this.pump = pump;
            this.bunchCancelled = false;
            this.akka$stream$impl$FanOut$OutputBunch$$outputs = (FanoutOutputs[])Array$.MODULE$.tabulate(outputCount, (Function1<Object, FanoutOutputs> & Serializable)x$1 -> OutputBunch.$anonfun$outputs$1(this, BoxesRunTime.unboxToInt(x$1)), ClassTag$.MODULE$.apply(FanoutOutputs.class));
            this.akka$stream$impl$FanOut$OutputBunch$$marked = new boolean[outputCount];
            this.akka$stream$impl$FanOut$OutputBunch$$markedCount = 0;
            this.akka$stream$impl$FanOut$OutputBunch$$pending = new boolean[outputCount];
            this.akka$stream$impl$FanOut$OutputBunch$$markedPending = 0;
            this.akka$stream$impl$FanOut$OutputBunch$$cancelled = new boolean[outputCount];
            this.akka$stream$impl$FanOut$OutputBunch$$markedCancelled = 0;
            this.akka$stream$impl$FanOut$OutputBunch$$completed = new boolean[outputCount];
            this.akka$stream$impl$FanOut$OutputBunch$$errored = new boolean[outputCount];
            this.akka$stream$impl$FanOut$OutputBunch$$unmarkCancelled = true;
            this.preferredId = 0;
            this.AllOfMarkedOutputs = new TransferState(this){
                private final /* synthetic */ OutputBunch $outer;

                public boolean isExecutable() {
                    return TransferState.isExecutable$(this);
                }

                public TransferState $bar$bar(TransferState other) {
                    return TransferState.$bar$bar$(this, other);
                }

                public TransferState $amp$amp(TransferState other) {
                    return TransferState.$amp$amp$(this, other);
                }

                public boolean isCompleted() {
                    return this.$outer.akka$stream$impl$FanOut$OutputBunch$$markedCancelled() > 0 || this.$outer.akka$stream$impl$FanOut$OutputBunch$$markedCount() == 0;
                }

                public boolean isReady() {
                    return this.$outer.akka$stream$impl$FanOut$OutputBunch$$markedPending() == this.$outer.akka$stream$impl$FanOut$OutputBunch$$markedCount();
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    TransferState.$init$(this);
                }
            };
            this.AnyOfMarkedOutputs = new TransferState(this){
                private final /* synthetic */ OutputBunch $outer;

                public boolean isExecutable() {
                    return TransferState.isExecutable$(this);
                }

                public TransferState $bar$bar(TransferState other) {
                    return TransferState.$bar$bar$(this, other);
                }

                public TransferState $amp$amp(TransferState other) {
                    return TransferState.$amp$amp$(this, other);
                }

                public boolean isCompleted() {
                    return this.$outer.akka$stream$impl$FanOut$OutputBunch$$markedCancelled() == this.$outer.akka$stream$impl$FanOut$OutputBunch$$markedCount();
                }

                public boolean isReady() {
                    return this.$outer.akka$stream$impl$FanOut$OutputBunch$$markedPending() > 0;
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    TransferState.$init$(this);
                }
            };
        }
    }

    public static final class SubstreamCancel
    implements DeadLetterSuppression,
    NoSerializationVerificationNeeded,
    Product,
    Serializable {
        private final int id;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public int id() {
            return this.id;
        }

        public SubstreamCancel copy(int id) {
            return new SubstreamCancel(id);
        }

        public int copy$default$1() {
            return this.id();
        }

        @Override
        public String productPrefix() {
            return "SubstreamCancel";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return BoxesRunTime.boxToInteger(this.id());
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof SubstreamCancel;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "id";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.id());
            return Statics.finalizeHash(n, 1);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof SubstreamCancel)) return false;
            boolean bl = true;
            if (!bl) return false;
            SubstreamCancel substreamCancel = (SubstreamCancel)x$1;
            if (this.id() != substreamCancel.id()) return false;
            return true;
        }

        public SubstreamCancel(int id) {
            this.id = id;
            Product.$init$(this);
        }
    }

    public static final class SubstreamRequestMore
    implements DeadLetterSuppression,
    NoSerializationVerificationNeeded,
    Product,
    Serializable {
        private final int id;
        private final long demand;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public int id() {
            return this.id;
        }

        public long demand() {
            return this.demand;
        }

        public SubstreamRequestMore copy(int id, long demand) {
            return new SubstreamRequestMore(id, demand);
        }

        public int copy$default$1() {
            return this.id();
        }

        public long copy$default$2() {
            return this.demand();
        }

        @Override
        public String productPrefix() {
            return "SubstreamRequestMore";
        }

        @Override
        public int productArity() {
            return 2;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return BoxesRunTime.boxToInteger(this.id());
                }
                case 1: {
                    return BoxesRunTime.boxToLong(this.demand());
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof SubstreamRequestMore;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "id";
                }
                case 1: {
                    return "demand";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.id());
            n = Statics.mix(n, Statics.longHash(this.demand()));
            return Statics.finalizeHash(n, 2);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof SubstreamRequestMore)) return false;
            boolean bl = true;
            if (!bl) return false;
            SubstreamRequestMore substreamRequestMore = (SubstreamRequestMore)x$1;
            if (this.id() != substreamRequestMore.id()) return false;
            if (this.demand() != substreamRequestMore.demand()) return false;
            return true;
        }

        public SubstreamRequestMore(int id, long demand) {
            this.id = id;
            this.demand = demand;
            Product.$init$(this);
        }
    }

    public static final class SubstreamSubscribePending
    implements DeadLetterSuppression,
    NoSerializationVerificationNeeded,
    Product,
    Serializable {
        private final int id;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public int id() {
            return this.id;
        }

        public SubstreamSubscribePending copy(int id) {
            return new SubstreamSubscribePending(id);
        }

        public int copy$default$1() {
            return this.id();
        }

        @Override
        public String productPrefix() {
            return "SubstreamSubscribePending";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return BoxesRunTime.boxToInteger(this.id());
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof SubstreamSubscribePending;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "id";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.id());
            return Statics.finalizeHash(n, 1);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof SubstreamSubscribePending)) return false;
            boolean bl = true;
            if (!bl) return false;
            SubstreamSubscribePending substreamSubscribePending = (SubstreamSubscribePending)x$1;
            if (this.id() != substreamSubscribePending.id()) return false;
            return true;
        }

        public SubstreamSubscribePending(int id) {
            this.id = id;
            Product.$init$(this);
        }
    }

    public static class SubstreamSubscription
    implements Subscription {
        private final ActorRef parent;
        private final int id;

        public ActorRef parent() {
            return this.parent;
        }

        public int id() {
            return this.id;
        }

        @Override
        public void request(long elements) {
            SubstreamRequestMore x$1 = new SubstreamRequestMore(this.id(), elements);
            ActorRef x$2 = this.parent().$bang$default$2(x$1);
            this.parent().$bang(x$1, x$2);
        }

        @Override
        public void cancel() {
            SubstreamCancel x$1 = new SubstreamCancel(this.id());
            ActorRef x$2 = this.parent().$bang$default$2(x$1);
            this.parent().$bang(x$1, x$2);
        }

        public String toString() {
            return new StringBuilder(21).append("SubstreamSubscription").append(System.identityHashCode(this)).toString();
        }

        public SubstreamSubscription(ActorRef parent, int id) {
            this.parent = parent;
            this.id = id;
        }
    }
}

