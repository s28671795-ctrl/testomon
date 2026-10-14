/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.Done;
import akka.Done$;
import akka.actor.ActorRef;
import akka.annotation.InternalApi;
import akka.annotation.InternalStableApi;
import akka.event.Logging$;
import akka.event.LoggingAdapter;
import akka.stream.Attributes;
import akka.stream.Attributes$LogLevels$;
import akka.stream.Inlet;
import akka.stream.Materializer;
import akka.stream.Outlet;
import akka.stream.impl.fusing.GraphInterpreter$;
import akka.stream.impl.fusing.GraphInterpreter$Empty$;
import akka.stream.snapshot.ConnectionSnapshot;
import akka.stream.snapshot.ConnectionSnapshot$Closed$;
import akka.stream.snapshot.ConnectionSnapshot$ShouldPull$;
import akka.stream.snapshot.ConnectionSnapshot$ShouldPush$;
import akka.stream.snapshot.ConnectionSnapshotImpl;
import akka.stream.snapshot.LogicSnapshot;
import akka.stream.snapshot.LogicSnapshotImpl;
import akka.stream.snapshot.RunningInterpreter;
import akka.stream.snapshot.RunningInterpreterImpl;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageLogic$;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;
import scala.$less$colon$less$;
import scala.Array$;
import scala.Function1;
import scala.Function4;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.collection.ArrayOps$;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.concurrent.Promise;
import scala.math.package$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Null$;
import scala.runtime.RichInt$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0019UrACA@\u0003\u0003C\t!!$\u0002\u0012\u001aQ\u0011QSAA\u0011\u0003\ti)a&\t\u000f\u0005\u0015\u0016\u0001\"\u0001\u0002*\"I\u00111V\u0001C\u0002\u0013\u0015\u0011Q\u0016\u0005\t\u0003g\u000b\u0001\u0015!\u0004\u00020\"I\u0011QW\u0001C\u0002\u0013\u0015\u0011q\u0017\u0005\t\u0003{\u000b\u0001\u0015!\u0004\u0002:\"I\u0011qX\u0001C\u0002\u0013\u0015\u0011\u0011\u0019\u0005\t\u0003\u000f\f\u0001\u0015!\u0004\u0002D\"I\u0011\u0011Z\u0001C\u0002\u0013\u0015\u00111\u001a\u0005\t\u0003#\f\u0001\u0015!\u0004\u0002N\"I\u00111[\u0001C\u0002\u0013\u0015\u0011Q\u001b\u0005\t\u00037\f\u0001\u0015!\u0004\u0002X\"I\u0011Q\\\u0001C\u0002\u0013\u0015\u0011q\u001c\u0005\t\u0003K\f\u0001\u0015!\u0004\u0002b\"I\u0011q]\u0001C\u0002\u0013\u0015\u0011\u0011\u001e\u0005\t\u0003_\f\u0001\u0015!\u0004\u0002l\"I\u0011\u0011_\u0001C\u0002\u0013\u0015\u00111\u001f\u0005\t\u0003s\f\u0001\u0015!\u0004\u0002v\"I\u00111`\u0001C\u0002\u0013\u0015\u0011Q \u0005\t\u0005\u0007\t\u0001\u0015!\u0004\u0002\u0000\"I!QA\u0001C\u0002\u0013\u0015!q\u0001\u0005\t\u0005\u001b\t\u0001\u0015!\u0004\u0003\n!I!qB\u0001C\u0002\u0013\u0015!\u0011\u0003\u0005\t\u0005/\t\u0001\u0015!\u0004\u0003\u0014!I!\u0011D\u0001C\u0002\u0013\u0015!1\u0004\u0005\t\u0005C\t\u0001\u0015!\u0004\u0003\u001e!I!1E\u0001C\u0002\u0013\u0015!Q\u0005\u0005\t\u0005W\t\u0001\u0015!\u0004\u0003(!I!QF\u0001C\u0002\u0013\u0015!q\u0006\u0005\t\u0005k\t\u0001\u0015!\u0004\u00032!I!qG\u0001C\u0002\u0013\u0015!\u0011\b\u0005\t\u0005\u007f\t\u0001\u0015!\u0004\u0003<\u001d9!\u0011I\u0001\t\u0002\n\rca\u0002B$\u0003!\u0005%\u0011\n\u0005\b\u0003K\u0013C\u0011\u0001B5\u0011%\u0011YGIA\u0001\n\u0003\u0012i\u0007C\u0005\u0003\u0000\t\n\t\u0011\"\u0001\u0003\u0002\"I!\u0011\u0012\u0012\u0002\u0002\u0013\u0005!1\u0012\u0005\n\u0005/\u0013\u0013\u0011!C!\u00053C\u0011Ba*#\u0003\u0003%\tA!+\t\u0013\tM&%!A\u0005B\tU\u0006\"\u0003B\\E\u0005\u0005I\u0011\tB]\u0011%\u0011YLIA\u0001\n\u0013\u0011iL\u0002\u0004\u0003F\u0006\u0011%q\u0019\u0005\u000b\u0005\u0013d#Q3A\u0005\u0002\t-\u0007B\u0003BjY\tE\t\u0015!\u0003\u0003N\"Q!Q\u001b\u0017\u0003\u0016\u0004%\tAa6\t\u0015\teGF!E!\u0002\u0013\u0011i\tC\u0004\u0002&2\"\tAa7\t\u0013\t\rH&!A\u0005\u0002\t\u0015\b\"\u0003BvYE\u0005I\u0011\u0001Bw\u0011%\u0019\u0019\u0001LI\u0001\n\u0003\u0019)\u0001C\u0005\u0003l1\n\t\u0011\"\u0011\u0003n!I!q\u0010\u0017\u0002\u0002\u0013\u0005!\u0011\u0011\u0005\n\u0005\u0013c\u0013\u0011!C\u0001\u0007\u0013A\u0011Ba&-\u0003\u0003%\tE!'\t\u0013\t\u001dF&!A\u0005\u0002\r5\u0001\"CB\tY\u0005\u0005I\u0011IB\n\u0011%\u0011\u0019\fLA\u0001\n\u0003\u0012)\fC\u0005\u000382\n\t\u0011\"\u0011\u0003:\"I1q\u0003\u0017\u0002\u0002\u0013\u00053\u0011D\u0004\n\u0007;\t\u0011\u0011!E\u0001\u0007?1\u0011B!2\u0002\u0003\u0003E\ta!\t\t\u000f\u0005\u0015v\b\"\u0001\u0004:!I!qW \u0002\u0002\u0013\u0015#\u0011\u0018\u0005\n\u0007wy\u0014\u0011!CA\u0007{A\u0011ba\u0011@\u0003\u0003%\ti!\u0012\t\u0013\tmv(!A\u0005\n\tufABB,\u0003\t\u001bI\u0006\u0003\u0006\u0004\\\u0015\u0013)\u001a!C\u0001\u0005\u0017D!b!\u0018F\u0005#\u0005\u000b\u0011\u0002Bg\u0011\u001d\t)+\u0012C\u0001\u0007?B\u0011Ba9F\u0003\u0003%\ta!\u001a\t\u0013\t-X)%A\u0005\u0002\t5\b\"\u0003B6\u000b\u0006\u0005I\u0011\tB7\u0011%\u0011y(RA\u0001\n\u0003\u0011\t\tC\u0005\u0003\n\u0016\u000b\t\u0011\"\u0001\u0004j!I!qS#\u0002\u0002\u0013\u0005#\u0011\u0014\u0005\n\u0005O+\u0015\u0011!C\u0001\u0007[B\u0011b!\u0005F\u0003\u0003%\te!\u001d\t\u0013\tMV)!A\u0005B\tU\u0006\"\u0003B\\\u000b\u0006\u0005I\u0011\tB]\u0011%\u00199\"RA\u0001\n\u0003\u001a)hB\u0005\u0004z\u0005\t\t\u0011#\u0001\u0004|\u0019I1qK\u0001\u0002\u0002#\u00051Q\u0010\u0005\b\u0003K+F\u0011ABC\u0011%\u00119,VA\u0001\n\u000b\u0012I\fC\u0005\u0004<U\u000b\t\u0011\"!\u0004\b\"I11I+\u0002\u0002\u0013\u000551\u0012\u0005\n\u0005w+\u0016\u0011!C\u0005\u0005{3qa!%\u0002\u0003\u0003\u0019\u0019\nC\u0004\u0002&n#\taa)\t\u000f\r]6L\"\u0001\u0004:\u001a911Y\u0001\u0002\u0002\r\u0015\u0007bBAS=\u0012\u00051\u0011\u001a\u0005\b\u0007#tf\u0011ABj\u0011%\u0019Y.\u0001b\u0001\n\u0003\u0019i\u000e\u0003\u0005\u0004l\u0006\u0001\u000b\u0011BBp\r\u0019\u0019i/\u0001\u0002\u0004p\"Q1\u0011_2\u0003\u0002\u0004%\tA!!\t\u0015\rM8M!a\u0001\n\u0003\u0019)\u0010\u0003\u0006\u0004\u0000\u000e\u0014\t\u0011)Q\u0005\u0005\u0007C!\u0002\"\u0001d\u0005\u0003\u0007I\u0011\u0001C\u0002\u0011)!)a\u0019BA\u0002\u0013\u0005Aq\u0001\u0005\u000b\t\u0017\u0019'\u0011!Q!\n\r]\u0005B\u0003C\u0007G\n\u0005\r\u0011\"\u0001\u0005\u0004!QAqB2\u0003\u0002\u0004%\t\u0001\"\u0005\t\u0015\u0011U1M!A!B\u0013\u00199\n\u0003\u0006\u0005\u0018\r\u0014\t\u0019!C\u0001\t3A!\u0002\"\td\u0005\u0003\u0007I\u0011\u0001C\u0012\u0011)!9c\u0019B\u0001B\u0003&A1\u0004\u0005\u000b\tS\u0019'\u00111A\u0005\u0002\u0011-\u0002B\u0003C\u001aG\n\u0005\r\u0011\"\u0001\u00056!QA\u0011H2\u0003\u0002\u0003\u0006K\u0001\"\f\t\u000f\u0005\u00156\r\"\u0001\u0005<!IA\u0011J2A\u0002\u0013\u0005!\u0011\u0011\u0005\n\t\u0017\u001a\u0007\u0019!C\u0001\t\u001bB\u0001\u0002\"\u0015dA\u0003&!1\u0011\u0005\n\t'\u001a\u0007\u0019!C\u0001\u0005/D\u0011\u0002\"\u0016d\u0001\u0004%\t\u0001b\u0016\t\u0011\u0011m3\r)Q\u0005\u0005\u001bC\u0011\u0002\"\u001b\u0002\u0005\u0004%I\u0001b\u001b\t\u0011\u0011U\u0014\u0001)A\u0005\t[B\u0011\u0002b\u001e\u0002\t\u0003\ti\t\"\u001f\t\u0013\u0019=\u0012\u0001\"\u0001\u0002\u000e\u0012ed!CAK\u0003\u0003\u0013\u0011Q\u0012C?\u0011)!yH BC\u0002\u0013\u0005A\u0011\u0011\u0005\u000b\t\u0013s(\u0011!Q\u0001\n\u0011\r\u0005B\u0003CF}\n\u0015\r\u0011\"\u0001\u0005\u000e\"QA1\u0014@\u0003\u0002\u0003\u0006I\u0001b$\t\u0015\u0011ueP!b\u0001\n\u0003!y\n\u0003\u0006\u0005$z\u0014\t\u0011)A\u0005\tCC!\u0002\"*\u007f\u0005\u000b\u0007I\u0011\u0001CT\u0011)!yK B\u0001B\u0003%A\u0011\u0016\u0005\u000b\tcs(Q1A\u0005\u0002\u0011M\u0006B\u0003Ck}\n\u0005\t\u0015!\u0003\u00056\"QAq\u001b@\u0003\u0006\u0004%\t\u0001\"7\t\u0015\u0011mgP!A!\u0002\u0013\u0011Y\u000b\u0003\u0006\u0005^z\u0014)\u0019!C\u0001\t?D!\u0002\"<\u007f\u0005\u0003\u0005\u000b\u0011\u0002Cq\u0011\u001d\t)K C\u0001\t_D\u0001\u0002b@\u007fA\u0003%!1\u0011\u0005\u000e\u000b\u0003q\b\u0019!a\u0001\n\u0003\tI\tb\u0001\t\u001b\u0015\ra\u00101AA\u0002\u0013\u0005\u0011\u0011RC\u0003\u0011-)IA a\u0001\u0002\u0003\u0006Kaa&\t\u0011\u0015Ma\u0010)Q\u0005\u0005\u0007C\u0001\"\"\u0006\u007fA\u0003%Qq\u0003\u0005\f\u000b3q\b\u0019!A!B\u0013!\u0019\t\u0003\u0006\u0006\u001cyD)\u0019)C\u0005\u000b;Aq!\"\f\u007f\t\u0003!\t\t\u0003\u0005\u00060y\u0004\u000b\u0011\u0002CU\u0011!)\tD Q\u0001\n\t\r\u0005\u0002CC\u001a}\u0002\u0006KAa!\t\u0011\u0015Ub\u0010)Q\u0005\u0005\u0007C\u0001\"b\u000e\u007fA\u0003&!1\u0011\u0005\t\u000bsq\b\u0015)\u0003\u0005,\"AQ1\b@!B\u0013!Y\u000bC\u0004\u0006>y$I!b\u0010\t\u0017\u0015=c\u00101A\u0001B\u0003&Q\u0011\t\u0005\b\u000b#rH\u0011AC \u0011%)\u0019F C\u0001\u0003\u0013#I\bC\u0004\u0006Xy$\t!\"\u0017\t\u000f\u0015]c\u0010\"\u0001\u0006d!9Q\u0011\u000e@\u0005\u0002\u0011e\u0007bBC6}\u0012\u0005A\u0011\u001c\u0005\b\u000b[rH\u0011AC8\u0011\u001d))H C\u0001\u000boBq!\"\u001f\u007f\t\u0013)Y\bC\u0004\u0006\u0000y$I!\"!\t\u000f\u0015\u0015e\u0010\"\u0003\u0006\b\"9Q1\u0012@\u0005\n\u00155\u0005bBCI}\u0012%Qq\b\u0005\b\u000b'sH\u0011ACK\u0011\u001d)YJ C\u0001\u000b;Cq!b,\u007f\t\u0013)\t\fC\u0004\u00068z$I!\"/\t\u000f\u0015}f\u0010\"\u0003\u0006B\"9Qq\u0019@\u0005\n\u0015%\u0007bBCf}\u0012\u0005QQ\u001a\u0005\b\u000b#tH\u0011ACj\u0011\u001d)9N C\u0001\u000b3Dq!\"8\u007f\t\u0013)y\u000eC\u0005\u0006fz$\t!!#\u0006h\"IQq\u001e@\u0005\u0002\u0005%U\u0011\u001f\u0005\n\u000botH\u0011AAE\u000bsD\u0011\"\"@\u007f\t\u0003\tI)b@\t\u0013\u0019\ra\u0010\"\u0001\u0002\n\u001a\u0015\u0001\"\u0003D\u0005}\u0012\u0005\u0011\u0011\u0012D\u0006\u0011%1\u0019B C\u0001\u0003\u00133)\u0002C\u0004\u0007\u001ey$\tAb\b\u0002!\u001d\u0013\u0018\r\u001d5J]R,'\u000f\u001d:fi\u0016\u0014(\u0002BAB\u0003\u000b\u000baAZ;tS:<'\u0002BAD\u0003\u0013\u000bA![7qY*!\u00111RAG\u0003\u0019\u0019HO]3b[*\u0011\u0011qR\u0001\u0005C.\\\u0017\rE\u0002\u0002\u0014\u0006i!!!!\u0003!\u001d\u0013\u0018\r\u001d5J]R,'\u000f\u001d:fi\u0016\u00148cA\u0001\u0002\u001aB!\u00111TAQ\u001b\t\tiJ\u0003\u0002\u0002 \u0006)1oY1mC&!\u00111UAO\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\u001a\u0001\u0001\u0006\u0002\u0002\u0012\u0006)A)\u001a2vOV\u0011\u0011qV\b\u0003\u0003cK\u0012\u0001A\u0001\u0007\t\u0016\u0014Wo\u001a\u0011\u0002\u000f9{WI^3oiV\u0011\u0011\u0011X\b\u0003\u0003w\u0013\u0003!\u0001\u0005O_\u00163XM\u001c;!\u0003\u001dIeNU3bIf,\"!a1\u0010\u0005\u0005\u0015W$A\u0001\u0002\u0011%s'+Z1es\u0002\nq\u0001U;mY&tw-\u0006\u0002\u0002N>\u0011\u0011qZ\u000f\u0002\u0005\u0005A\u0001+\u001e7mS:<\u0007%A\u0004QkND\u0017N\\4\u0016\u0005\u0005]wBAAm;\u0005!\u0011\u0001\u0003)vg\"Lgn\u001a\u0011\u0002\u0011=+HOU3bIf,\"!!9\u0010\u0005\u0005\rX$\u0001\u0005\u0002\u0013=+HOU3bIf\u0004\u0013\u0001C%o\u00072|7/\u001a3\u0016\u0005\u0005-xBAAw;\u0005\u0001\u0012!C%o\u00072|7/\u001a3!\u0003%yU\u000f^\"m_N,G-\u0006\u0002\u0002v>\u0011\u0011q_\u000f\u0002A\u0005Qq*\u001e;DY>\u001cX\r\u001a\u0011\u0002\u0011%sg)Y5mK\u0012,\"!a@\u0010\u0005\t\u0005Q$\u0001!\u0002\u0013%sg)Y5mK\u0012\u0004\u0013!\u0004)vY2\u001cF/\u0019:u\r2L\u0007/\u0006\u0002\u0003\n=\u0011!1B\u000f\u0002\u0007\u0005q\u0001+\u001e7m'R\f'\u000f\u001e$mSB\u0004\u0013a\u0003)vY2,e\u000e\u001a$mSB,\"Aa\u0005\u0010\u0005\tUQ$\u0001\u0006\u0002\u0019A+H\u000e\\#oI\u001ac\u0017\u000e\u001d\u0011\u0002\u001bA+8\u000f[*uCJ$h\t\\5q+\t\u0011ib\u0004\u0002\u0003 u\tA\"\u0001\bQkND7\u000b^1si\u001ac\u0017\u000e\u001d\u0011\u0002\u0017A+8\u000f[#oI\u001ac\u0017\u000e]\u000b\u0003\u0005Oy!A!\u000b\u001e\u0003\u0015\tA\u0002U;tQ\u0016sGM\u00127ja\u0002\nQbS3fa\u001e{\u0017N\\4GY\u0006<WC\u0001B\u0019\u001f\t\u0011\u0019$\b\u0003\u0005\u0001\u0001\u0001\u0011AD&fKB<u.\u001b8h\r2\fw\rI\u0001\u000e\u0017\u0016,\u0007oR8j]\u001el\u0015m]6\u0016\u0005\tmrB\u0001B\u001f;\u0011\u0019q\u0000\u0000\u0000\u0002\u001d-+W\r]$pS:<W*Y:lA\u0005)Q)\u001c9usB\u0019!Q\t\u0012\u000e\u0003\u0005\u0011Q!R7qif\u001crAIAM\u0005\u0017\u0012\t\u0006\u0005\u0003\u0002\u001c\n5\u0013\u0002\u0002B(\u0003;\u0013q\u0001\u0015:pIV\u001cG\u000f\u0005\u0003\u0003T\t\rd\u0002\u0002B+\u0005?rAAa\u0016\u0003^5\u0011!\u0011\f\u0006\u0005\u00057\n9+\u0001\u0004=e>|GOP\u0005\u0003\u0003?KAA!\u0019\u0002\u001e\u00069\u0001/Y2lC\u001e,\u0017\u0002\u0002B3\u0005O\u0012AbU3sS\u0006d\u0017N_1cY\u0016TAA!\u0019\u0002\u001eR\u0011!1I\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\t=\u0004\u0003\u0002B9\u0005wj!Aa\u001d\u000b\t\tU$qO\u0001\u0005Y\u0006twM\u0003\u0002\u0003z\u0005!!.\u0019<b\u0013\u0011\u0011iHa\u001d\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\t\u0011\u0019\t\u0005\u0003\u0002\u001c\n\u0015\u0015\u0002\u0002BD\u0003;\u00131!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$BA!$\u0003\u0014B!\u00111\u0014BH\u0013\u0011\u0011\t*!(\u0003\u0007\u0005s\u0017\u0010C\u0005\u0003\u0016\u001a\n\t\u00111\u0001\u0003\u0004\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"Aa'\u0011\r\tu%1\u0015BG\u001b\t\u0011yJ\u0003\u0003\u0003\"\u0006u\u0015AC2pY2,7\r^5p]&!!Q\u0015BP\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\t-&\u0011\u0017\t\u0005\u00037\u0013i+\u0003\u0003\u00030\u0006u%a\u0002\"p_2,\u0017M\u001c\u0005\n\u0005+C\u0013\u0011!a\u0001\u0005\u001b\u000b\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0005\u0007\u000b\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0005_\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"Aa0\u0011\t\tE$\u0011Y\u0005\u0005\u0005\u0007\u0014\u0019H\u0001\u0004PE*,7\r\u001e\u0002\u0007\r\u0006LG.\u001a3\u0014\u000f1\nIJa\u0013\u0003R\u0005\u0011Q\r_\u000b\u0003\u0005\u001b\u0004BAa\u0015\u0003P&!!\u0011\u001bB4\u0005%!\u0006N]8xC\ndW-A\u0002fq\u0002\nA\u0002\u001d:fm&|Wo]#mK6,\"A!$\u0002\u001bA\u0014XM^5pkN,E.Z7!)\u0019\u0011iNa8\u0003bB\u0019!Q\t\u0017\t\u000f\t%\u0017\u00071\u0001\u0003N\"9!Q[\u0019A\u0002\t5\u0015\u0001B2paf$bA!8\u0003h\n%\b\"\u0003BeeA\u0005\t\u0019\u0001Bg\u0011%\u0011)N\rI\u0001\u0002\u0004\u0011i)\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\t=(\u0006\u0002Bg\u0005c\\#Aa=\u0011\t\tU(q`\u0007\u0003\u0005oTAA!?\u0003|\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0005\u0005{\fi*\u0001\u0006b]:|G/\u0019;j_:LAa!\u0001\u0003x\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\u00111q\u0001\u0016\u0005\u0005\u001b\u0013\t\u0010\u0006\u0003\u0003\u000e\u000e-\u0001\"\u0003BKo\u0005\u0005\t\u0019\u0001BB)\u0011\u0011Yka\u0004\t\u0013\tU\u0015(!AA\u0002\t5\u0015A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$BAa\u001c\u0004\u0016!I!Q\u0013\u001e\u0002\u0002\u0003\u0007!1Q\u0001\u0007KF,\u0018\r\\:\u0015\t\t-61\u0004\u0005\n\u0005+k\u0014\u0011!a\u0001\u0005\u001b\u000baAR1jY\u0016$\u0007c\u0001B#\u007fM)qha\t\u00040AQ1QEB\u0016\u0005\u001b\u0014iI!8\u000e\u0005\r\u001d\"\u0002BB\u0015\u0003;\u000bqA];oi&lW-\u0003\u0003\u0004.\r\u001d\"!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8oeA!1\u0011GB\u001c\u001b\t\u0019\u0019D\u0003\u0003\u00046\t]\u0014AA5p\u0013\u0011\u0011)ga\r\u0015\u0005\r}\u0011!B1qa2LHC\u0002Bo\u0007\u007f\u0019\t\u0005C\u0004\u0003J\n\u0003\rA!4\t\u000f\tU'\t1\u0001\u0003\u000e\u00069QO\\1qa2LH\u0003BB$\u0007'\u0002b!a'\u0004J\r5\u0013\u0002BB&\u0003;\u0013aa\u00149uS>t\u0007\u0003CAN\u0007\u001f\u0012iM!$\n\t\rE\u0013Q\u0014\u0002\u0007)V\u0004H.\u001a\u001a\t\u0013\rU3)!AA\u0002\tu\u0017a\u0001=%a\tI1)\u00198dK2dW\rZ\n\b\u000b\u0006e%1\nB)\u0003\u0015\u0019\u0017-^:f\u0003\u0019\u0019\u0017-^:fAQ!1\u0011MB2!\r\u0011)%\u0012\u0005\b\u00077B\u0005\u0019\u0001Bg)\u0011\u0019\tga\u001a\t\u0013\rm\u0013\n%AA\u0002\t5G\u0003\u0002BG\u0007WB\u0011B!&N\u0003\u0003\u0005\rAa!\u0015\t\t-6q\u000e\u0005\n\u0005+{\u0015\u0011!a\u0001\u0005\u001b#BAa\u001c\u0004t!I!Q\u0013)\u0002\u0002\u0003\u0007!1\u0011\u000b\u0005\u0005W\u001b9\bC\u0005\u0003\u0016N\u000b\t\u00111\u0001\u0003\u000e\u0006I1)\u00198dK2dW\r\u001a\t\u0004\u0005\u000b*6#B+\u0004\u0000\r=\u0002\u0003CB\u0013\u0007\u0003\u0013im!\u0019\n\t\r\r5q\u0005\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\fDCAB>)\u0011\u0019\tg!#\t\u000f\rm\u0003\f1\u0001\u0003NR!1QRBH!\u0019\tYj!\u0013\u0003N\"I1QK-\u0002\u0002\u0003\u00071\u0011\r\u0002\u001b+B\u001cHO]3b[\n{WO\u001c3bef\u001cF/Y4f\u0019><\u0017nY\u000b\u0005\u0007+\u001bYkE\u0002\\\u0007/\u0003Ba!'\u0004 6\u001111\u0014\u0006\u0005\u0007;\u000bI)A\u0003ti\u0006<W-\u0003\u0003\u0004\"\u000em%aD$sCBD7\u000b^1hK2{w-[2\u0015\u0005\r\u0015\u0006#\u0002B#7\u000e\u001d\u0006\u0003BBU\u0007Wc\u0001\u0001B\u0004\u0004.n\u0013\raa,\u0003\u0003Q\u000bBa!-\u0003\u000eB!\u00111TBZ\u0013\u0011\u0019),!(\u0003\u000f9{G\u000f[5oO\u0006\u0019q.\u001e;\u0016\u0005\rm\u0006CBB_\u0007\u007f\u001b9+\u0004\u0002\u0002\n&!1\u0011YAE\u0005\u0019yU\u000f\u001e7fi\naBi\\<ogR\u0014X-Y7C_VtG-\u0019:z'R\fw-\u001a'pO&\u001cW\u0003BBd\u0007\u001f\u001c2AXBL)\t\u0019Y\rE\u0003\u0003Fy\u001bi\r\u0005\u0003\u0004*\u000e=GaBBW=\n\u00071qV\u0001\u0003S:,\"a!6\u0011\r\ru6q[Bg\u0013\u0011\u0019I.!#\u0003\u000b%sG.\u001a;\u0002#MLgn\u001a7f\u001d>\fE\u000f\u001e:jEV$X-\u0006\u0002\u0004`B1\u00111TBq\u0007KLAaa9\u0002\u001e\n)\u0011I\u001d:bsB!1QXBt\u0013\u0011\u0019I/!#\u0003\u0015\u0005#HO]5ckR,7/\u0001\ntS:<G.\u001a(p\u0003R$(/\u001b2vi\u0016\u0004#AC\"p]:,7\r^5p]N\u00191-!'\u0002\u0005%$\u0017AB5e?\u0012*\u0017\u000f\u0006\u0003\u0004x\u000eu\b\u0003BAN\u0007sLAaa?\u0002\u001e\n!QK\\5u\u0011%\u0011)*ZA\u0001\u0002\u0004\u0011\u0019)A\u0002jI\u0002\nq!\u001b8Po:,'/\u0006\u0002\u0004\u0018\u0006Y\u0011N\\(x]\u0016\u0014x\fJ3r)\u0011\u00199\u0010\"\u0003\t\u0013\tU\u0005.!AA\u0002\r]\u0015\u0001C5o\u001f^tWM\u001d\u0011\u0002\u0011=,HoT<oKJ\fAb\\;u\u001f^tWM]0%KF$Baa>\u0005\u0014!I!QS6\u0002\u0002\u0003\u00071qS\u0001\n_V$xj\u001e8fe\u0002\n\u0011\"\u001b8IC:$G.\u001a:\u0016\u0005\u0011m\u0001\u0003BBM\t;IA\u0001b\b\u0004\u001c\nI\u0011J\u001c%b]\u0012dWM]\u0001\u000eS:D\u0015M\u001c3mKJ|F%Z9\u0015\t\r]HQ\u0005\u0005\n\u0005+s\u0017\u0011!a\u0001\t7\t!\"\u001b8IC:$G.\u001a:!\u0003)yW\u000f\u001e%b]\u0012dWM]\u000b\u0003\t[\u0001Ba!'\u00050%!A\u0011GBN\u0005)yU\u000f\u001e%b]\u0012dWM]\u0001\u000f_V$\b*\u00198eY\u0016\u0014x\fJ3r)\u0011\u00199\u0010b\u000e\t\u0013\tU\u0015/!AA\u0002\u00115\u0012aC8vi\"\u000bg\u000e\u001a7fe\u0002\"B\u0002\"\u0010\u0005@\u0011\u0005C1\tC#\t\u000f\u00022A!\u0012d\u0011\u001d\u0019\tp\u001da\u0001\u0005\u0007Cq\u0001\"\u0001t\u0001\u0004\u00199\nC\u0004\u0005\u000eM\u0004\raa&\t\u000f\u0011]1\u000f1\u0001\u0005\u001c!9A\u0011F:A\u0002\u00115\u0012!\u00039peR\u001cF/\u0019;f\u00035\u0001xN\u001d;Ti\u0006$Xm\u0018\u0013fcR!1q\u001fC(\u0011%\u0011)*^A\u0001\u0002\u0004\u0011\u0019)\u0001\u0006q_J$8\u000b^1uK\u0002\nAa\u001d7pi\u0006A1\u000f\\8u?\u0012*\u0017\u000f\u0006\u0003\u0004x\u0012e\u0003\"\u0003BKq\u0006\u0005\t\u0019\u0001BG\u0003\u0015\u0019Hn\u001c;!Q\r\u0019Gq\f\t\u0005\tC\")'\u0004\u0002\u0005d)!!Q`AG\u0013\u0011!9\u0007b\u0019\u0003#%sG/\u001a:oC2\u001cF/\u00192mK\u0006\u0003\u0018.A\n`GV\u0014(/\u001a8u\u0013:$XM\u001d9sKR,'/\u0006\u0002\u0005nA1!\u0011\u000fC8\tgJA\u0001\"\u001d\u0003t\tYA\u000b\u001b:fC\u0012dunY1m!\u0019\tYj!9\u0002\u001a\u0006!rlY;se\u0016tG/\u00138uKJ\u0004(/\u001a;fe\u0002\n!cY;se\u0016tG/\u00138uKJ\u0004(/\u001a;feV\u0011A1\u0010\t\u0004\u0003's8c\u0001@\u0002\u001a\u0006aQ.\u0019;fe&\fG.\u001b>feV\u0011A1\u0011\t\u0005\u0007{#))\u0003\u0003\u0005\b\u0006%%\u0001D'bi\u0016\u0014\u0018.\u00197ju\u0016\u0014\u0018!D7bi\u0016\u0014\u0018.\u00197ju\u0016\u0014\b%A\u0002m_\u001e,\"\u0001b$\u0011\t\u0011EEqS\u0007\u0003\t'SA\u0001\"&\u0002\u000e\u0006)QM^3oi&!A\u0011\u0014CJ\u00059aunZ4j]\u001e\fE-\u00199uKJ\fA\u0001\\8hA\u00051An\\4jGN,\"\u0001\")\u0011\r\u0005m5\u0011]BL\u0003\u001dawnZ5dg\u0002\n1bY8o]\u0016\u001cG/[8ogV\u0011A\u0011\u0016\t\u0007\u00037\u001b\t\u000fb+\u0011\u0007\u001156MD\u0002\u0002\u0014\u0002\tAbY8o]\u0016\u001cG/[8og\u0002\nAb\u001c8Bgft7-\u00138qkR,\"\u0001\".\u0011\u001d\u0005mEqWBL\u0005\u001b#Y\fb4\u0004x&!A\u0011XAO\u0005%1UO\\2uS>tG\u0007\u0005\u0004\u0005>\u0012\rGqY\u0007\u0003\t\u007fSA\u0001\"1\u0002\u001e\u0006Q1m\u001c8dkJ\u0014XM\u001c;\n\t\u0011\u0015Gq\u0018\u0002\b!J|W.[:f!\u0011!I\rb3\u000e\u0005\u00055\u0015\u0002\u0002Cg\u0003\u001b\u0013A\u0001R8oKBA\u00111\u0014Ci\u0005\u001b\u001b90\u0003\u0003\u0005T\u0006u%!\u0003$v]\u000e$\u0018n\u001c82\u00035yg.Q:z]\u000eLe\u000e];uA\u0005Ya-\u001e>{S:<Wj\u001c3f+\t\u0011Y+\u0001\u0007gkjT\u0018N\\4N_\u0012,\u0007%A\u0004d_:$X\r\u001f;\u0016\u0005\u0011\u0005\b\u0003\u0002Cr\tSl!\u0001\":\u000b\t\u0011\u001d\u0018QR\u0001\u0006C\u000e$xN]\u0005\u0005\tW$)O\u0001\u0005BGR|'OU3g\u0003!\u0019wN\u001c;fqR\u0004C\u0003\u0005C>\tc$\u0019\u0010\">\u0005x\u0012eH1 C\u007f\u0011!!y(a\u0007A\u0002\u0011\r\u0005\u0002\u0003CF\u00037\u0001\r\u0001b$\t\u0011\u0011u\u00151\u0004a\u0001\tCC\u0001\u0002\"*\u0002\u001c\u0001\u0007A\u0011\u0016\u0005\t\tc\u000bY\u00021\u0001\u00056\"AAq[A\u000e\u0001\u0004\u0011Y\u000b\u0003\u0005\u0005^\u0006m\u0001\u0019\u0001Cq\u0003)\u0019\u0005.Y:f\u0019&l\u0017\u000e^\u0001\fC\u000e$\u0018N^3Ti\u0006<W-A\bbGRLg/Z*uC\u001e,w\fJ3r)\u0011\u001990b\u0002\t\u0015\tU\u0015\u0011EA\u0001\u0002\u0004\u00199*\u0001\u0007bGRLg/Z*uC\u001e,\u0007\u0005\u000b\u0003\u0002$\u00155\u0001\u0003\u0002C1\u000b\u001fIA!\"\u0005\u0005d\tY\u0011J\u001c;fe:\fG.\u00119j\u00035\u0011XO\u001c8j]\u001e\u001cF/Y4fg\u0006y1\u000f[;uI><hnQ8v]R,'\u000f\u0005\u0004\u0002\u001c\u000e\u0005(1Q\u0001\u0017?N,(MR;tS:<W*\u0019;fe&\fG.\u001b>fe\u0006iB-\u001a4bk2$XI\u001d:peJ+\u0007o\u001c:uS:<Gj\\4MKZ,G.\u0006\u0002\u0006 A!Q\u0011EC\u0014\u001d\u0011!\t*b\t\n\t\u0015\u0015B1S\u0001\b\u0019><w-\u001b8h\u0013\u0011)I#b\u000b\u0003\u00111{w\rT3wK2TA!\"\n\u0005\u0014\u0006)2/\u001e2GkNLgnZ'bi\u0016\u0014\u0018.\u00197ju\u0016\u0014\u0018AC3wK:$\u0018+^3vK\u0006!Q.Y:l\u0003%\tX/Z;f\u0011\u0016\fG-A\u0005rk\u0016,X\rV1jY\u0006a1\r[1tK\u000e{WO\u001c;fe\u0006Q1\r[1tK\u0012\u0004Vo\u001d5\u0002\u0015\rD\u0017m]3e!VdG.A\u0006rk\u0016,Xm\u0015;biV\u001cXCAC!!\u0011)\u0019%b\u0013\u000f\t\u0015\u0015Sq\t\t\u0005\u0005/\ni*\u0003\u0003\u0006J\u0005u\u0015A\u0002)sK\u0012,g-\u0003\u0003\u0003~\u00155#\u0002BC%\u0003;\u000bQa\u0018(b[\u0016\fAAT1nK\u00069an\u001c8Ok2d\u0007\u0006BA\"\u000b\u001b\t!b]3u\u0011\u0006tG\r\\3s)\u0019\u001990b\u0017\u0006`!AQQLA#\u0001\u0004!Y+\u0001\u0006d_:tWm\u0019;j_:D\u0001\"\"\u0019\u0002F\u0001\u0007A1D\u0001\bQ\u0006tG\r\\3s)\u0019\u001990\"\u001a\u0006h!AQQLA$\u0001\u0004!Y\u000b\u0003\u0005\u0006b\u0005\u001d\u0003\u0019\u0001C\u0017\u0003-I7oU;ta\u0016tG-\u001a3\u0002\u0017%\u001c8i\\7qY\u0016$X\rZ\u0001\u0005S:LG\u000f\u0006\u0003\u0004x\u0016E\u0004\u0002CC:\u0003\u001b\u0002\r\u0001b!\u0002\rM,(-T1u\u0003\u00191\u0017N\\5tQR\u00111q_\u0001\fS:|uO\\3s\u001d\u0006lW\r\u0006\u0003\u0006B\u0015u\u0004\u0002CC/\u0003#\u0002\r\u0001b+\u0002\u0019=,HoT<oKJt\u0015-\\3\u0015\t\u0015\u0005S1\u0011\u0005\t\u000b;\n\u0019\u00061\u0001\u0005,\u0006Y\u0011N\u001c'pO&\u001cg*Y7f)\u0011)\t%\"#\t\u0011\u0015u\u0013Q\u000ba\u0001\tW\u000bAb\\;u\u0019><\u0017n\u0019(b[\u0016$B!\"\u0011\u0006\u0010\"AQQLA,\u0001\u0004!Y+\u0001\ttQV$Hm\\<o\u0007>,h\u000e^3sg\u00069Q\r_3dkR,G\u0003\u0002BB\u000b/C\u0001\"\"'\u0002\\\u0001\u0007!1Q\u0001\u000bKZ,g\u000e\u001e'j[&$\u0018!\u0004:v]\u0006\u001b\u0018P\\2J]B,H\u000f\u0006\u0006\u0004x\u0016}U1UCT\u000bWC\u0001\"\")\u0002^\u0001\u00071qS\u0001\u0006Y><\u0017n\u0019\u0005\t\u000bK\u000bi\u00061\u0001\u0003\u000e\u0006\u0019QM\u001e;\t\u0011\u0015%\u0016Q\fa\u0001\tw\u000bq\u0001\u001d:p[&\u001cX\r\u0003\u0005\u0006b\u0005u\u0003\u0019\u0001ChQ\u0011\ti\u0006b\u0018\u0002\u0019A\u0014xnY3tg\u00163XM\u001c;\u0015\t\r]X1\u0017\u0005\t\u000b;\ny\u00061\u0001\u0005,\"\"\u0011q\fC0\u0003-\u0001(o\\2fgN\u0004Vo\u001d5\u0015\t\r]X1\u0018\u0005\t\u000b;\n\t\u00071\u0001\u0005,\"\"\u0011\u0011\rC0\u0003-\u0001(o\\2fgN\u0004V\u000f\u001c7\u0015\t\r]X1\u0019\u0005\t\u000b;\n\u0019\u00071\u0001\u0005,\"\"\u00111\rC0\u0003\u001d!W-];fk\u0016$\"\u0001b+\u0002\u000f\u0015t\u0017/^3vKR!1q_Ch\u0011!)i&a\u001aA\u0002\u0011-\u0016\u0001E1gi\u0016\u00148\u000b^1hK\"\u000b7OU;o)\u0011\u0011Y+\"6\t\u0011\u0015\u0005\u0016\u0011\u000ea\u0001\u0007/\u000b\u0001#[:Ti\u0006<WmQ8na2,G/\u001a3\u0015\t\t-V1\u001c\u0005\t\u0007;\u000bY\u00071\u0001\u0004\u0018\u0006\u00112m\\7qY\u0016$XmQ8o]\u0016\u001cG/[8o)\u0011\u001990\"9\t\u0011\u0015\r\u0018Q\u000ea\u0001\u0005\u0007\u000bqa\u001d;bO\u0016LE-\u0001\u0007tKR\\U-\u001a9H_&tw\r\u0006\u0004\u0004x\u0016%X1\u001e\u0005\t\u000bC\u000by\u00071\u0001\u0004\u0018\"AQQ^A8\u0001\u0004\u0011Y+A\u0004f]\u0006\u0014G.\u001a3\u0002\u001b\u0019Lg.\u00197ju\u0016\u001cF/Y4f)\u0011\u001990b=\t\u0011\u0015\u0005\u0016\u0011\u000fa\u0001\u0007/CC!!\u001d\u0005`\u0005I1\r[1tKB+8\u000f\u001b\u000b\u0005\u0007o,Y\u0010\u0003\u0005\u0006^\u0005M\u0004\u0019\u0001CV\u0003%\u0019\u0007.Y:f!VdG\u000e\u0006\u0003\u0004x\u001a\u0005\u0001\u0002CC/\u0003k\u0002\r\u0001b+\u0002\u0011\r|W\u000e\u001d7fi\u0016$Baa>\u0007\b!AQQLA<\u0001\u0004!Y+\u0001\u0003gC&dGCBB|\r\u001b1y\u0001\u0003\u0005\u0006^\u0005e\u0004\u0019\u0001CV\u0011!\u0011I-!\u001fA\u0002\t5\u0007\u0006BA=\t?\naaY1oG\u0016dGCBB|\r/1I\u0002\u0003\u0005\u0006^\u0005m\u0004\u0019\u0001CV\u0011!\u0019Y&a\u001fA\u0002\t5\u0007\u0006BA>\t?\n!\u0002^8T]\u0006\u00048\u000f[8u+\t1\t\u0003\u0005\u0003\u0007$\u0019%RB\u0001D\u0013\u0015\u001119#!#\u0002\u0011Mt\u0017\r]:i_RLAAb\u000b\u0007&\t\u0011\"+\u001e8oS:<\u0017J\u001c;feB\u0014X\r^3sQ\rqXQB\u0001\u0019GV\u0014(/\u001a8u\u0013:$XM\u001d9sKR,'o\u0014:Ok2d\u0007fA\u0001\u0006\u000e!\u001a\u0001!\"\u0004")
@InternalApi
public final class GraphInterpreter {
    private int defaultErrorReportingLogLevel;
    private final Materializer materializer;
    private final LoggingAdapter log;
    private final GraphStageLogic[] logics;
    private final Connection[] connections;
    private final Function4<GraphStageLogic, Object, Promise<Done>, Function1<Object, BoxedUnit>, BoxedUnit> onAsyncInput;
    private final boolean fuzzingMode;
    private final ActorRef context;
    private final int ChaseLimit;
    @InternalApi
    private GraphStageLogic activeStage;
    private int runningStages;
    private final int[] shutdownCounter;
    private Materializer _subFusingMaterializer;
    private final Connection[] eventQueue;
    private final int mask;
    private int queueHead;
    private int queueTail;
    private int chaseCounter;
    private Connection chasedPush;
    private Connection chasedPull;
    private String _Name;
    private volatile boolean bitmap$0;

    public static Attributes[] singleNoAttribute() {
        return GraphInterpreter$.MODULE$.singleNoAttribute();
    }

    public static int KeepGoingMask() {
        return GraphInterpreter$.MODULE$.KeepGoingMask();
    }

    public static int KeepGoingFlag() {
        return GraphInterpreter$.MODULE$.KeepGoingFlag();
    }

    public static int PushEndFlip() {
        return GraphInterpreter$.MODULE$.PushEndFlip();
    }

    public static int PushStartFlip() {
        return GraphInterpreter$.MODULE$.PushStartFlip();
    }

    public static int PullEndFlip() {
        return GraphInterpreter$.MODULE$.PullEndFlip();
    }

    public static int PullStartFlip() {
        return GraphInterpreter$.MODULE$.PullStartFlip();
    }

    public static int InFailed() {
        return GraphInterpreter$.MODULE$.InFailed();
    }

    public static int OutClosed() {
        return GraphInterpreter$.MODULE$.OutClosed();
    }

    public static int InClosed() {
        return GraphInterpreter$.MODULE$.InClosed();
    }

    public static int OutReady() {
        return GraphInterpreter$.MODULE$.OutReady();
    }

    public static int Pushing() {
        return GraphInterpreter$.MODULE$.Pushing();
    }

    public static int Pulling() {
        return GraphInterpreter$.MODULE$.Pulling();
    }

    public static int InReady() {
        return GraphInterpreter$.MODULE$.InReady();
    }

    public static Null$ NoEvent() {
        return GraphInterpreter$.MODULE$.NoEvent();
    }

    public static boolean Debug() {
        return GraphInterpreter$.MODULE$.Debug();
    }

    public Materializer materializer() {
        return this.materializer;
    }

    public LoggingAdapter log() {
        return this.log;
    }

    public GraphStageLogic[] logics() {
        return this.logics;
    }

    public Connection[] connections() {
        return this.connections;
    }

    public Function4<GraphStageLogic, Object, Promise<Done>, Function1<Object, BoxedUnit>, BoxedUnit> onAsyncInput() {
        return this.onAsyncInput;
    }

    public boolean fuzzingMode() {
        return this.fuzzingMode;
    }

    public ActorRef context() {
        return this.context;
    }

    public GraphStageLogic activeStage() {
        return this.activeStage;
    }

    public void activeStage_$eq(GraphStageLogic x$1) {
        this.activeStage = x$1;
    }

    private int defaultErrorReportingLogLevel$lzycompute() {
        GraphInterpreter graphInterpreter = this;
        synchronized (graphInterpreter) {
            if (!this.bitmap$0) {
                this.defaultErrorReportingLogLevel = Attributes$LogLevels$.MODULE$.defaultErrorLevel(this.materializer().system());
                this.bitmap$0 = true;
            }
        }
        return this.defaultErrorReportingLogLevel;
    }

    private int defaultErrorReportingLogLevel() {
        if (!this.bitmap$0) {
            return this.defaultErrorReportingLogLevel$lzycompute();
        }
        return this.defaultErrorReportingLogLevel;
    }

    public Materializer subFusingMaterializer() {
        return this._subFusingMaterializer;
    }

    private String queueStatus() {
        Object contents = RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(this.queueHead), this.queueTail).map((Function1<Object, Connection> & Serializable)idx -> conn = this.eventQueue[BoxesRunTime.unboxToInt(idx) & this.mask]);
        return new StringBuilder(8).append("(").append(this.eventQueue.length).append(", ").append(this.queueHead).append(", ").append(this.queueTail).append(")(").append(contents.mkString(", ")).append(")").toString();
    }

    public String Name() {
        if (this._Name == null) {
            this._Name = StringOps$.MODULE$.format$extension("%08X", ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{BoxesRunTime.boxToInteger(System.identityHashCode(this))}));
            return this._Name;
        }
        return this._Name;
    }

    @InternalApi
    public GraphInterpreter nonNull() {
        return this;
    }

    public void setHandler(Connection connection, InHandler handler) {
        connection.inHandler_$eq(handler);
    }

    public void setHandler(Connection connection, OutHandler handler) {
        connection.outHandler_$eq(handler);
    }

    public boolean isSuspended() {
        return this.queueHead != this.queueTail;
    }

    public boolean isCompleted() {
        return this.runningStages == 0 && !this.isSuspended();
    }

    public void init(Materializer subMat) {
        this._subFusingMaterializer = subMat == null ? this.materializer() : subMat;
        for (int i = 0; i < this.logics().length; ++i) {
            GraphStageLogic logic = this.logics()[i];
            logic.interpreter_$eq(this);
            try {
                logic.beforePreStart();
                logic.preStart();
            }
            catch (Throwable throwable) {
                Option<Throwable> option;
                Throwable throwable2 = throwable;
                if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                    Throwable e = option.get();
                    this.log().error(e, "Error during preStart in [{}]: {}", (Object)logic.toString(), (Object)e.getMessage());
                    logic.failStage(e);
                }
                throw throwable;
            }
            this.afterStageHasRun(logic);
        }
    }

    public void finish() {
        for (int i = 0; i < this.logics().length; ++i) {
            GraphStageLogic logic = this.logics()[i];
            if (this.isStageCompleted(logic)) continue;
            this.finalizeStage(logic);
        }
    }

    private String inOwnerName(Connection connection) {
        return connection.inOwner().toString();
    }

    private String outOwnerName(Connection connection) {
        return connection.outOwner().toString();
    }

    private String inLogicName(Connection connection) {
        return this.logics()[connection.inOwner().stageId()].toString();
    }

    private String outLogicName(Connection connection) {
        return this.logics()[connection.outOwner().stageId()].toString();
    }

    private String shutdownCounters() {
        return Predef$.MODULE$.wrapRefArray((Object[])ArrayOps$.MODULE$.map$extension(Predef$.MODULE$.intArrayOps(this.shutdownCounter), (Function1<Object, String> & Serializable)x -> GraphInterpreter.$anonfun$shutdownCounters$1(BoxesRunTime.unboxToInt(x)), ClassTag$.MODULE$.apply(String.class))).mkString(",");
    }

    public int execute(int eventLimit) {
        Object[] currentInterpreterHolder = GraphInterpreter$.MODULE$.akka$stream$impl$fusing$GraphInterpreter$$_currentInterpreter().get();
        Object previousInterpreter = currentInterpreterHolder[0];
        currentInterpreterHolder[0] = this;
        int eventsRemaining = eventLimit;
        try {
            while (eventsRemaining > 0 && this.queueTail != this.queueHead) {
                Connection connection = this.dequeue();
                this.chaseCounter = package$.MODULE$.min(this.ChaseLimit, --eventsRemaining);
                try {
                    this.processEvent(connection);
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable e = option.get();
                        this.reportStageError$1(e);
                    }
                    throw throwable;
                }
                boolean wasFinalized = this.afterStageHasRun(this.activeStage());
                while (this.chasedPush != null) {
                    Connection connection2 = this.chasedPush;
                    this.chasedPush = null;
                    try {
                        this.processPush(connection2);
                    }
                    catch (Throwable throwable) {
                        Option<Throwable> option;
                        Throwable throwable3 = throwable;
                        if (throwable3 != null && !(option = NonFatal$.MODULE$.unapply(throwable3)).isEmpty()) {
                            Throwable e = option.get();
                            this.reportStageError$1(e);
                        }
                        throw throwable;
                    }
                    if (wasFinalized) continue;
                    wasFinalized = this.afterStageHasRun(this.activeStage());
                }
                while (this.chasedPull != null) {
                    Connection connection3 = this.chasedPull;
                    this.chasedPull = null;
                    try {
                        this.processPull(connection3);
                    }
                    catch (Throwable throwable) {
                        Option<Throwable> option;
                        Throwable throwable4 = throwable;
                        if (throwable4 != null && !(option = NonFatal$.MODULE$.unapply(throwable4)).isEmpty()) {
                            Throwable e = option.get();
                            this.reportStageError$1(e);
                        }
                        throw throwable;
                    }
                    if (wasFinalized) continue;
                    wasFinalized = this.afterStageHasRun(this.activeStage());
                }
                if (this.chasedPush == null) continue;
                this.enqueue(this.chasedPush);
                this.chasedPush = null;
            }
            this.chaseCounter = 0;
        }
        finally {
            currentInterpreterHolder[0] = previousInterpreter;
        }
        return eventsRemaining;
    }

    @InternalStableApi
    public void runAsyncInput(GraphStageLogic logic, Object evt, Promise<Done> promise, Function1<Object, BoxedUnit> handler) {
        if (!this.isStageCompleted(logic)) {
            Object[] currentInterpreterHolder = GraphInterpreter$.MODULE$.akka$stream$impl$fusing$GraphInterpreter$$_currentInterpreter().get();
            Object previousInterpreter = currentInterpreterHolder[0];
            currentInterpreterHolder[0] = this;
            try {
                this.activeStage_$eq(logic);
                try {
                    handler.apply(evt);
                    if (promise != GraphStageLogic$.MODULE$.NoPromise()) {
                        promise.success(Done$.MODULE$);
                        logic.onFeedbackDispatched(promise);
                    }
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable ex = option.get();
                        if (promise != GraphStageLogic$.MODULE$.NoPromise()) {
                            promise.failure(ex);
                            logic.onFeedbackDispatched(promise);
                        }
                        logic.failStage(ex);
                    }
                    throw throwable;
                }
                this.afterStageHasRun(logic);
            }
            finally {
                currentInterpreterHolder[0] = previousInterpreter;
            }
            return;
        }
    }

    @InternalStableApi
    private void processEvent(Connection connection) {
        this.activeStage_$eq(null);
        int code = connection.portState();
        if ((code & 0x34) == 4) {
            this.processPush(connection);
            return;
        }
        if ((code & 0x32) == 2) {
            this.processPull(connection);
            return;
        }
        if ((code & 0x30) == 16) {
            this.activeStage_$eq(connection.outOwner());
            connection.portState_$eq(connection.portState() | 0x20);
            this.completeConnection(connection.outOwner().stageId());
            Throwable cause = ((Cancelled)connection.slot()).cause();
            connection.slot_$eq(GraphInterpreter$Empty$.MODULE$);
            connection.outHandler().onDownstreamFinish(cause);
            return;
        }
        if ((code & 0x30) == 32) {
            if ((code & 4) == 0) {
                connection.portState_$eq(connection.portState() | 0x10);
                this.activeStage_$eq(connection.inOwner());
                this.completeConnection(connection.inOwner().stageId());
                if ((connection.portState() & 0x40) == 0) {
                    connection.inHandler().onUpstreamFinish();
                    return;
                }
                connection.inHandler().onUpstreamFailure(((Failed)connection.slot()).ex());
                return;
            }
            this.processPush(connection);
            this.enqueue(connection);
            return;
        }
    }

    @InternalStableApi
    private void processPush(Connection connection) {
        this.activeStage_$eq(connection.inOwner());
        connection.portState_$eq(connection.portState() ^ 5);
        connection.inHandler().onPush();
    }

    @InternalStableApi
    private void processPull(Connection connection) {
        this.activeStage_$eq(connection.outOwner());
        connection.portState_$eq(connection.portState() ^ 0xA);
        connection.outHandler().onPull();
    }

    private Connection dequeue() {
        int idx = this.queueHead & this.mask;
        if (this.fuzzingMode()) {
            int swapWith = ThreadLocalRandom.current().nextInt(this.queueTail - this.queueHead) + this.queueHead & this.mask;
            Connection ev = this.eventQueue[swapWith];
            this.eventQueue[swapWith] = this.eventQueue[idx];
            this.eventQueue[idx] = ev;
        }
        Connection elem = this.eventQueue[idx];
        this.eventQueue[idx] = null;
        ++this.queueHead;
        return elem;
    }

    public void enqueue(Connection connection) {
        this.eventQueue[this.queueTail & this.mask] = connection;
        ++this.queueTail;
    }

    public boolean afterStageHasRun(GraphStageLogic logic) {
        if (this.isStageCompleted(logic)) {
            --this.runningStages;
            this.finalizeStage(logic);
            return true;
        }
        return false;
    }

    public boolean isStageCompleted(GraphStageLogic stage) {
        return stage != null && this.shutdownCounter[stage.stageId()] == 0;
    }

    private void completeConnection(int stageId) {
        int activeConnections = this.shutdownCounter[stageId];
        if (activeConnections > 0) {
            this.shutdownCounter[stageId] = activeConnections - 1;
            return;
        }
    }

    public void setKeepGoing(GraphStageLogic logic, boolean enabled) {
        if (enabled) {
            int n = logic.stageId();
            this.shutdownCounter[n] = this.shutdownCounter[n] | 0x4000000;
            return;
        }
        int n = logic.stageId();
        this.shutdownCounter[n] = this.shutdownCounter[n] & 0x3FFFFFF;
    }

    @InternalStableApi
    public void finalizeStage(GraphStageLogic logic) {
        try {
            logic.postStop();
            logic.afterPostStop();
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable e = option.get();
                this.log().error(e, "Error during postStop in [{}]: {}", (Object)logic.toString(), (Object)e.getMessage());
            }
            throw throwable;
        }
    }

    public void chasePush(Connection connection) {
        if (this.chaseCounter > 0 && this.chasedPush == null) {
            --this.chaseCounter;
            this.chasedPush = connection;
            return;
        }
        this.enqueue(connection);
    }

    public void chasePull(Connection connection) {
        if (this.chaseCounter > 0 && this.chasedPull == null) {
            --this.chaseCounter;
            this.chasedPull = connection;
            return;
        }
        this.enqueue(connection);
    }

    public void complete(Connection connection) {
        int currentState = connection.portState();
        connection.portState_$eq(currentState | 0x20);
        Connection connection2 = this.chasedPush;
        Connection connection3 = connection;
        if (!(connection2 != null ? !connection2.equals(connection3) : connection3 != null)) {
            this.chasedPush = null;
            this.enqueue(connection);
        } else if ((currentState & 0x36) == 0) {
            this.enqueue(connection);
        }
        if ((currentState & 0x20) == 0) {
            this.completeConnection(connection.outOwner().stageId());
            return;
        }
    }

    @InternalStableApi
    public void fail(Connection connection, Throwable ex) {
        int currentState = connection.portState();
        connection.portState_$eq(currentState | 0x20);
        if ((currentState & 0x30) == 0) {
            connection.portState_$eq(currentState | 0x60);
            connection.slot_$eq(new Failed(ex, connection.slot()));
            if ((currentState & 6) == 0) {
                this.enqueue(connection);
            } else if (this.chasedPush == connection) {
                this.chasedPush = null;
                this.enqueue(connection);
            }
        }
        if ((currentState & 0x20) == 0) {
            this.completeConnection(connection.outOwner().stageId());
            return;
        }
    }

    @InternalStableApi
    public void cancel(Connection connection, Throwable cause) {
        int currentState = connection.portState();
        connection.portState_$eq(currentState | 0x10);
        if ((currentState & 0x20) == 0) {
            connection.slot_$eq(new Cancelled(cause));
            if ((currentState & 0x16) == 0) {
                this.enqueue(connection);
            } else if (this.chasedPull == connection) {
                this.chasedPull = null;
                this.enqueue(connection);
            }
        }
        if ((currentState & 0x10) == 0) {
            this.completeConnection(connection.inOwner().stageId());
            return;
        }
    }

    public RunningInterpreter toSnapshot() {
        LogicSnapshotImpl[] logicSnapshots = (LogicSnapshotImpl[])ArrayOps$.MODULE$.map$extension(Predef$.MODULE$.refArrayOps(ArrayOps$.MODULE$.zipWithIndex$extension(Predef$.MODULE$.refArrayOps(this.logics()))), (Function1<Tuple2, LogicSnapshotImpl> & Serializable)x0$1 -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null) {
                GraphStageLogic logic = (GraphStageLogic)tuple2._1();
                int idx = tuple2._2$mcI$sp();
                return new LogicSnapshotImpl(idx, logic.toString(), logic.attributes());
            }
            throw new MatchError(tuple2);
        }, ClassTag$.MODULE$.apply(LogicSnapshotImpl.class));
        Map logicIndexes = Predef$.MODULE$.wrapRefArray((Object[])ArrayOps$.MODULE$.map$extension(Predef$.MODULE$.refArrayOps(ArrayOps$.MODULE$.zipWithIndex$extension(Predef$.MODULE$.refArrayOps(this.logics()))), (Function1<Tuple2, Tuple2> & Serializable)x0$2 -> {
            Tuple2 tuple2 = x0$2;
            if (tuple2 != null) {
                GraphStageLogic stage = (GraphStageLogic)tuple2._1();
                int idx = tuple2._2$mcI$sp();
                return Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(stage), BoxesRunTime.boxToInteger(idx));
            }
            throw new MatchError(tuple2);
        }, ClassTag$.MODULE$.apply(Tuple2.class))).toMap($less$colon$less$.MODULE$.refl());
        ConnectionSnapshotImpl[] connectionSnapshots = (ConnectionSnapshotImpl[])ArrayOps$.MODULE$.map$extension(Predef$.MODULE$.refArrayOps((Object[])ArrayOps$.MODULE$.filter$extension(Predef$.MODULE$.refArrayOps(this.connections()), (Function1<Connection, Object> & Serializable)x$4 -> BoxesRunTime.boxToBoolean(GraphInterpreter.$anonfun$toSnapshot$3(x$4)))), (Function1<Connection, ConnectionSnapshotImpl> & Serializable)connection -> {
            Product product;
            int n = connection.id();
            LogicSnapshotImpl logicSnapshotImpl = logicSnapshots[BoxesRunTime.unboxToInt(logicIndexes.apply(connection.inOwner()))];
            LogicSnapshotImpl logicSnapshotImpl2 = logicSnapshots[BoxesRunTime.unboxToInt(logicIndexes.apply(connection.outOwner()))];
            int n2 = connection.portState();
            switch (n2) {
                case 1: 
                case 4: {
                    product = ConnectionSnapshot$ShouldPull$.MODULE$;
                    break;
                }
                case 2: 
                case 8: {
                    product = ConnectionSnapshot$ShouldPush$.MODULE$;
                    break;
                }
                default: {
                    if ((n2 & 0x30) == 48) {
                        product = ConnectionSnapshot$Closed$.MODULE$;
                        break;
                    }
                    throw new IllegalStateException(new StringBuilder(34).append("Unexpected connection state for ").append(connection).append(": ").append(connection.portState()).toString());
                }
            }
            return new ConnectionSnapshotImpl(n, logicSnapshotImpl, logicSnapshotImpl2, (ConnectionSnapshot.ConnectionState)((Object)product));
        }, ClassTag$.MODULE$.apply(ConnectionSnapshotImpl.class));
        List<LogicSnapshot> stoppedStages = Predef$.MODULE$.wrapRefArray((Object[])ArrayOps$.MODULE$.collect$extension(Predef$.MODULE$.refArrayOps(ArrayOps$.MODULE$.zipWithIndex$extension(Predef$.MODULE$.intArrayOps(this.shutdownCounter))), new Serializable(null, logicSnapshots){
            private static final long serialVersionUID = 0L;
            private final LogicSnapshotImpl[] logicSnapshots$1;

            public final <A1 extends Tuple2<Object, Object>, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 != null) {
                    int activeConnections = A1._1$mcI$sp();
                    int idx = A1._2$mcI$sp();
                    if (activeConnections < 1) {
                        return (B1)this.logicSnapshots$1[idx];
                    }
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Tuple2<Object, Object> x1) {
                int activeConnections;
                Tuple2<Object, Object> tuple2 = x1;
                return tuple2 != null && (activeConnections = tuple2._1$mcI$sp()) < 1;
            }
            {
                this.logicSnapshots$1 = logicSnapshots$1;
            }
        }, ClassTag$.MODULE$.apply(LogicSnapshotImpl.class))).toList();
        return new RunningInterpreterImpl(Predef$.MODULE$.wrapRefArray((Object[])logicSnapshots).toVector(), Predef$.MODULE$.wrapRefArray((Object[])connectionSnapshots).toVector(), this.queueStatus(), this.runningStages, stoppedStages);
    }

    public static final /* synthetic */ String $anonfun$shutdownCounters$1(int x) {
        if (x >= 0x4000000) {
            return new StringBuilder(11).append(x & 0x3FFFFFF).append("(KeepGoing)").toString();
        }
        return Integer.toString(x);
    }

    private final void reportStageError$1(Throwable e) {
        int logAt;
        int n;
        if (this.activeStage() == null) {
            throw e;
        }
        Option option = this.activeStage().attributes().get(ClassTag$.MODULE$.apply(Attributes.LogLevels.class));
        if (option instanceof Some) {
            Some some = (Some)option;
            Attributes.LogLevels levels = (Attributes.LogLevels)some.value();
            n = levels.onFailure();
        } else if (None$.MODULE$.equals(option)) {
            n = this.defaultErrorReportingLogLevel();
        } else {
            throw new MatchError(option);
        }
        int n2 = logAt = n;
        if (Logging$.MODULE$.ErrorLevel() == n2) {
            this.log().error(e, "Error in stage [{}]: {}", (Object)this.activeStage().toString(), (Object)e.getMessage());
        } else if (Logging$.MODULE$.WarningLevel() == n2) {
            this.log().warning(e, "Error in stage [{}]: {}", (Object)this.activeStage().toString(), (Object)e.getMessage());
        } else if (Logging$.MODULE$.InfoLevel() == n2) {
            this.log().info("Error in stage [{}]: {}", this.activeStage().toString(), e.getMessage());
        } else if (Logging$.MODULE$.DebugLevel() == n2) {
            this.log().debug("Error in stage [{}]: {}", this.activeStage().toString(), e.getMessage());
        }
        this.activeStage().failStage(e);
        this.chaseCounter = 0;
        if (this.chasedPush != null) {
            this.enqueue(this.chasedPush);
            this.chasedPush = null;
        }
        if (this.chasedPull != null) {
            this.enqueue(this.chasedPull);
            this.chasedPull = null;
            return;
        }
    }

    public static final /* synthetic */ boolean $anonfun$toSnapshot$3(Connection x$4) {
        return x$4 != null;
    }

    public GraphInterpreter(Materializer materializer, LoggingAdapter log, GraphStageLogic[] logics, Connection[] connections, Function4<GraphStageLogic, Object, Promise<Done>, Function1<Object, BoxedUnit>, BoxedUnit> onAsyncInput, boolean fuzzingMode, ActorRef context) {
        this.materializer = materializer;
        this.log = log;
        this.logics = logics;
        this.connections = connections;
        this.onAsyncInput = onAsyncInput;
        this.fuzzingMode = fuzzingMode;
        this.context = context;
        this.ChaseLimit = fuzzingMode ? 0 : 16;
        this.runningStages = logics.length;
        this.shutdownCounter = (int[])Array$.MODULE$.tabulate(logics.length, i -> this.logics()[i].handlers().length, ClassTag$.MODULE$.Int());
        this.eventQueue = new Connection[1 << 32 - Integer.numberOfLeadingZeros(connections.length - 1)];
        this.mask = this.eventQueue.length - 1;
        this.queueHead = 0;
        this.queueTail = 0;
        this.chaseCounter = 0;
        this.chasedPush = null;
        this.chasedPull = null;
    }

    public static final class Cancelled
    implements Product,
    Serializable {
        private final Throwable cause;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Throwable cause() {
            return this.cause;
        }

        public Cancelled copy(Throwable cause) {
            return new Cancelled(cause);
        }

        public Throwable copy$default$1() {
            return this.cause();
        }

        @Override
        public String productPrefix() {
            return "Cancelled";
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
                    return this.cause();
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
            return x$1 instanceof Cancelled;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "cause";
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
            if (!(object instanceof Cancelled)) return false;
            boolean bl = true;
            if (!bl) return false;
            Cancelled cancelled = (Cancelled)x$1;
            Throwable throwable = this.cause();
            Throwable throwable2 = cancelled.cause();
            if (throwable != null) {
                if (!throwable.equals(throwable2)) return false;
                return true;
            }
            if (throwable2 == null) return true;
            return false;
        }

        public Cancelled(Throwable cause) {
            this.cause = cause;
            Product.$init$(this);
        }
    }

    @InternalStableApi
    public static final class Connection {
        private int id;
        private GraphStageLogic inOwner;
        private GraphStageLogic outOwner;
        private InHandler inHandler;
        private OutHandler outHandler;
        private int portState;
        private Object slot;

        public int id() {
            return this.id;
        }

        public void id_$eq(int x$1) {
            this.id = x$1;
        }

        public GraphStageLogic inOwner() {
            return this.inOwner;
        }

        public void inOwner_$eq(GraphStageLogic x$1) {
            this.inOwner = x$1;
        }

        public GraphStageLogic outOwner() {
            return this.outOwner;
        }

        public void outOwner_$eq(GraphStageLogic x$1) {
            this.outOwner = x$1;
        }

        public InHandler inHandler() {
            return this.inHandler;
        }

        public void inHandler_$eq(InHandler x$1) {
            this.inHandler = x$1;
        }

        public OutHandler outHandler() {
            return this.outHandler;
        }

        public void outHandler_$eq(OutHandler x$1) {
            this.outHandler = x$1;
        }

        public int portState() {
            return this.portState;
        }

        public void portState_$eq(int x$1) {
            this.portState = x$1;
        }

        public Object slot() {
            return this.slot;
        }

        public void slot_$eq(Object x$1) {
            this.slot = x$1;
        }

        public Connection(int id, GraphStageLogic inOwner, GraphStageLogic outOwner, InHandler inHandler, OutHandler outHandler) {
            this.id = id;
            this.inOwner = inOwner;
            this.outOwner = outOwner;
            this.inHandler = inHandler;
            this.outHandler = outHandler;
            this.portState = 1;
            this.slot = GraphInterpreter$Empty$.MODULE$;
        }
    }

    public static abstract class DownstreamBoundaryStageLogic<T>
    extends GraphStageLogic {
        public abstract Inlet<T> in();

        public DownstreamBoundaryStageLogic() {
            super(1, 0);
        }
    }

    public static final class Failed
    implements Product,
    Serializable {
        private final Throwable ex;
        private final Object previousElem;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Throwable ex() {
            return this.ex;
        }

        public Object previousElem() {
            return this.previousElem;
        }

        public Failed copy(Throwable ex, Object previousElem) {
            return new Failed(ex, previousElem);
        }

        public Throwable copy$default$1() {
            return this.ex();
        }

        public Object copy$default$2() {
            return this.previousElem();
        }

        @Override
        public String productPrefix() {
            return "Failed";
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
                    return this.ex();
                }
                case 1: {
                    return this.previousElem();
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
            return x$1 instanceof Failed;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "ex";
                }
                case 1: {
                    return "previousElem";
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
            if (!(object instanceof Failed)) return false;
            boolean bl = true;
            if (!bl) return false;
            Failed failed = (Failed)x$1;
            Throwable throwable = this.ex();
            Throwable throwable2 = failed.ex();
            if (throwable == null) {
                if (throwable2 != null) {
                    return false;
                }
            } else if (!throwable.equals(throwable2)) return false;
            if (!BoxesRunTime.equals(this.previousElem(), failed.previousElem())) return false;
            return true;
        }

        public Failed(Throwable ex, Object previousElem) {
            this.ex = ex;
            this.previousElem = previousElem;
            Product.$init$(this);
        }
    }

    public static abstract class UpstreamBoundaryStageLogic<T>
    extends GraphStageLogic {
        public abstract Outlet<T> out();

        public UpstreamBoundaryStageLogic() {
            super(0, 1);
        }
    }
}

