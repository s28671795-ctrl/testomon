/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.Attributes;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SinkShape;
import akka.stream.SourceShape;
import akka.stream.StreamDetachedException;
import akka.stream.scaladsl.BroadcastHub$;
import akka.stream.scaladsl.BroadcastHub$$anon$4$;
import akka.stream.scaladsl.BroadcastHub$Advance$;
import akka.stream.scaladsl.BroadcastHub$Closed$;
import akka.stream.scaladsl.BroadcastHub$Completed$;
import akka.stream.scaladsl.BroadcastHub$Consumer$;
import akka.stream.scaladsl.BroadcastHub$HubCompleted$;
import akka.stream.scaladsl.BroadcastHub$Initialize$;
import akka.stream.scaladsl.BroadcastHub$NeedWakeup$;
import akka.stream.scaladsl.BroadcastHub$Open$;
import akka.stream.scaladsl.BroadcastHub$RegistrationPending$;
import akka.stream.scaladsl.BroadcastHub$UnRegister$;
import akka.stream.scaladsl.BroadcastHub$Wakeup$;
import akka.stream.scaladsl.Sink;
import akka.stream.scaladsl.Source;
import akka.stream.scaladsl.Source$;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.collection.ArrayOps$;
import scala.collection.Iterator;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\u0019\ru\u0001CAy\u0003gD\tA!\u0001\u0007\u0011\t\u0015\u00111\u001fE\u0001\u0005\u000fAqA!\u0006\u0002\t\u0003\u00119\u0002C\u0006\u0003\u001a\u0005\u0011\r\u0011\"\u0001\u0002|\nm\u0001\u0002\u0003B\u0012\u0003\u0001\u0006IA!\b\t\u000f\tM\u0012\u0001\"\u0001\u00036!9!1G\u0001\u0005\u0002\t\u001dd!\u0003B\u0003\u0003g\u0004\u00111 B:\u0011)\u0011)g\u0002B\u0001B\u0003%!Q\u0004\u0005\b\u0005+9A\u0011\u0001BI\u0011%\u00119j\u0002b\u0001\n\u0013\u0011Y\u0002\u0003\u0005\u0003\u001a\u001e\u0001\u000b\u0011\u0002B\u000f\u0011%\u0011Yj\u0002b\u0001\n\u0013\u0011Y\u0002\u0003\u0005\u0003\u001e\u001e\u0001\u000b\u0011\u0002B\u000f\u0011%\u0011yj\u0002b\u0001\n\u0003\u0011\t\u000b\u0003\u0005\u0003*\u001e\u0001\u000b\u0011\u0002BR\u0011%\u0011Yk\u0002b\u0001\n\u0003\u0012i\u000b\u0003\u0005\u00030\u001e\u0001\u000b\u0011\u0002BB\u0011!\u0011\tl\u0002Q\u0001\n\tua!\u0003BZ\u000fA\u0005\u0019\u0013\u0006B[\u000f\u001d\u00199m\u0002E\u0005\u000773qa!&\b\u0011\u0013\u00199\nC\u0004\u0003\u0016U!\ta!'\u0007\r\ruu\u0001RBP\u0011)\u0011yn\u0006BK\u0002\u0013\u0005!\u0011\u001d\u0005\u000b\u0005S<\"\u0011#Q\u0001\n\t\r\bB\u0003Bv/\tU\r\u0011\"\u0001\u0003\u001c!Q!Q^\f\u0003\u0012\u0003\u0006IA!\b\t\u0015\r\u0005vC!f\u0001\n\u0003\u0011Y\u0002\u0003\u0006\u0004$^\u0011\t\u0012)A\u0005\u0005;AqA!\u0006\u0018\t\u0003\u0019)\u000bC\u0005\u0003x^\t\t\u0011\"\u0001\u00040\"I!q`\f\u0012\u0002\u0013\u00051\u0011\u0001\u0005\n\u0007+9\u0012\u0013!C\u0001\u0007/A\u0011ba!\u0018#\u0003%\taa\u0006\t\u0013\rmq#!A\u0005B\ru\u0001\"CB\u0018/\u0005\u0005I\u0011\u0001B\u000e\u0011%\u0019\tdFA\u0001\n\u0003\u00199\fC\u0005\u0004:]\t\t\u0011\"\u0011\u0004<!I1\u0011J\f\u0002\u0002\u0013\u000511\u0018\u0005\n\u0007+:\u0012\u0011!C!\u0007\u007fC\u0011ba\u0017\u0018\u0003\u0003%\te!\u0018\t\u0013\r}s#!A\u0005B\r\u0005\u0004\"CB2/\u0005\u0005I\u0011IBb\u000f%\u0019ImBA\u0001\u0012\u0013\u0019YMB\u0005\u0004\u001e\u001e\t\t\u0011#\u0003\u0004N\"9!QC\u0017\u0005\u0002\r\u0015\b\"CB0[\u0005\u0005IQIB1\u0011%\u00199/LA\u0001\n\u0003\u001bI\u000fC\u0005\u0004r6\n\t\u0011\"!\u0004t\u001a1!\u0011X\u0004E\u0005wC!Ba83\u0005+\u0007I\u0011\u0001Bq\u0011)\u0011IO\rB\tB\u0003%!1\u001d\u0005\u000b\u0005W\u0014$Q3A\u0005\u0002\tm\u0001B\u0003Bwe\tE\t\u0015!\u0003\u0003\u001e!9!Q\u0003\u001a\u0005\u0002\t=\b\"\u0003B|e\u0005\u0005I\u0011\u0001B}\u0011%\u0011yPMI\u0001\n\u0003\u0019\t\u0001C\u0005\u0004\u0016I\n\n\u0011\"\u0001\u0004\u0018!I11\u0004\u001a\u0002\u0002\u0013\u00053Q\u0004\u0005\n\u0007_\u0011\u0014\u0011!C\u0001\u00057A\u0011b!\r3\u0003\u0003%\taa\r\t\u0013\re\"'!A\u0005B\rm\u0002\"CB%e\u0005\u0005I\u0011AB&\u0011%\u0019)FMA\u0001\n\u0003\u001a9\u0006C\u0005\u0004\\I\n\t\u0011\"\u0011\u0004^!I1q\f\u001a\u0002\u0002\u0013\u00053\u0011\r\u0005\n\u0007G\u0012\u0014\u0011!C!\u0007K:\u0011\u0002\"\u0002\b\u0003\u0003EI\u0001b\u0002\u0007\u0013\tev!!A\t\n\u0011%\u0001b\u0002B\u000b\u000b\u0012\u0005A\u0011\u0003\u0005\n\u0007?*\u0015\u0011!C#\u0007CB\u0011ba:F\u0003\u0003%\t\tb\u0005\t\u0013\rEX)!A\u0005\u0002\u0012eaABB5\u000f\u0011\u001bY\u0007\u0003\u0006\u0003`*\u0013)\u001a!C\u0001\u0005CD!B!;K\u0005#\u0005\u000b\u0011\u0002Br\u0011)\u0011YO\u0013BK\u0002\u0013\u0005!1\u0004\u0005\u000b\u0005[T%\u0011#Q\u0001\n\tu\u0001BCB7\u0015\nU\r\u0011\"\u0001\u0003\u001c!Q1q\u000e&\u0003\u0012\u0003\u0006IA!\b\t\u000f\tU!\n\"\u0001\u0004r!I!q\u001f&\u0002\u0002\u0013\u000511\u0010\u0005\n\u0005\u007fT\u0015\u0013!C\u0001\u0007\u0003A\u0011b!\u0006K#\u0003%\taa\u0006\t\u0013\r\r%*%A\u0005\u0002\r]\u0001\"CB\u000e\u0015\u0006\u0005I\u0011IB\u000f\u0011%\u0019yCSA\u0001\n\u0003\u0011Y\u0002C\u0005\u00042)\u000b\t\u0011\"\u0001\u0004\u0006\"I1\u0011\b&\u0002\u0002\u0013\u000531\b\u0005\n\u0007\u0013R\u0015\u0011!C\u0001\u0007\u0013C\u0011b!\u0016K\u0003\u0003%\te!$\t\u0013\rm#*!A\u0005B\ru\u0003\"CB0\u0015\u0006\u0005I\u0011IB1\u0011%\u0019\u0019GSA\u0001\n\u0003\u001a\tjB\u0005\u0005&\u001d\t\t\u0011#\u0003\u0005(\u0019I1\u0011N\u0004\u0002\u0002#%A\u0011\u0006\u0005\b\u0005+\u0001G\u0011\u0001C\u0017\u0011%\u0019y\u0006YA\u0001\n\u000b\u001a\t\u0007C\u0005\u0004h\u0002\f\t\u0011\"!\u00050!I1\u0011\u001f1\u0002\u0002\u0013\u0005Eq\u0007\u0004\u0007\tw9A\t\"\u0010\t\u0015\t}WM!f\u0001\n\u0003\u0011\t\u000f\u0003\u0006\u0003j\u0016\u0014\t\u0012)A\u0005\u0005GD!\u0002b\u0010f\u0005+\u0007I\u0011\u0001C!\u0011)!Y+\u001aB\tB\u0003%A1\t\u0005\b\u0005+)G\u0011\u0001CW\u0011%\u001190ZA\u0001\n\u0003!)\fC\u0005\u0003\u0000\u0016\f\n\u0011\"\u0001\u0004\u0002!I1QC3\u0012\u0002\u0013\u0005A1\u0018\u0005\n\u00077)\u0017\u0011!C!\u0007;A\u0011ba\ff\u0003\u0003%\tAa\u0007\t\u0013\rER-!A\u0005\u0002\u0011}\u0006\"CB\u001dK\u0006\u0005I\u0011IB\u001e\u0011%\u0019I%ZA\u0001\n\u0003!\u0019\rC\u0005\u0004V\u0015\f\t\u0011\"\u0011\u0005H\"I11L3\u0002\u0002\u0013\u00053Q\f\u0005\n\u0007?*\u0017\u0011!C!\u0007CB\u0011ba\u0019f\u0003\u0003%\t\u0005b3\b\u0013\u0011=w!!A\t\n\u0011Eg!\u0003C\u001e\u000f\u0005\u0005\t\u0012\u0002Cj\u0011\u001d\u0011)\u0002\u001fC\u0001\t/D\u0011ba\u0018y\u0003\u0003%)e!\u0019\t\u0013\r\u001d\b0!A\u0005\u0002\u0012e\u0007\"CByq\u0006\u0005I\u0011\u0011Cp\u000f\u001d!9o\u0002E\u0005\tS4q\u0001b;\b\u0011\u0013!i\u000fC\u0004\u0003\u0016y$\t\u0001b<\u0007\u0013\u0011Ex\u0001%A\u0012*\u0011MhABC\f\u000f\u0011+I\u0002C\u0006\u0006\u001c\u0005\r!Q3A\u0005\u0002\u0015u\u0001bCC\u0017\u0003\u0007\u0011\t\u0012)A\u0005\u000b?A1\"b\f\u0002\u0004\tU\r\u0011\"\u0001\u00062!YQ\u0011HA\u0002\u0005#\u0005\u000b\u0011BC\u001a\u0011!\u0011)\"a\u0001\u0005\u0002\u0015m\u0002B\u0003B|\u0003\u0007\t\t\u0011\"\u0001\u0006D!Q!q`A\u0002#\u0003%\t!\"\u0013\t\u0015\rU\u00111AI\u0001\n\u0003)i\u0005\u0003\u0006\u0004\u001c\u0005\r\u0011\u0011!C!\u0007;A!ba\f\u0002\u0004\u0005\u0005I\u0011\u0001B\u000e\u0011)\u0019\t$a\u0001\u0002\u0002\u0013\u0005Q\u0011\u000b\u0005\u000b\u0007s\t\u0019!!A\u0005B\rm\u0002BCB%\u0003\u0007\t\t\u0011\"\u0001\u0006V!Q1QKA\u0002\u0003\u0003%\t%\"\u0017\t\u0015\rm\u00131AA\u0001\n\u0003\u001ai\u0006\u0003\u0006\u0004`\u0005\r\u0011\u0011!C!\u0007CB!ba\u0019\u0002\u0004\u0005\u0005I\u0011IC/\u000f%)\tgBA\u0001\u0012\u0013)\u0019GB\u0005\u0006\u0018\u001d\t\t\u0011#\u0003\u0006f!A!QCA\u0015\t\u0003)I\u0007\u0003\u0006\u0004`\u0005%\u0012\u0011!C#\u0007CB!ba:\u0002*\u0005\u0005I\u0011QC6\u0011)\u0019\t0!\u000b\u0002\u0002\u0013\u0005U\u0011\u000f\u0004\u0007\to<A\t\"?\t\u0017\u0011U\u00131\u0007BK\u0002\u0013\u0005Aq\u000b\u0005\f\tC\n\u0019D!E!\u0002\u0013!I\u0006\u0003\u0005\u0003\u0016\u0005MB\u0011\u0001C\u007f\u0011)\u001190a\r\u0002\u0002\u0013\u0005Q1\u0001\u0005\u000b\u0005\u007f\f\u0019$%A\u0005\u0002\u00115\u0004BCB\u000e\u0003g\t\t\u0011\"\u0011\u0004\u001e!Q1qFA\u001a\u0003\u0003%\tAa\u0007\t\u0015\rE\u00121GA\u0001\n\u0003)9\u0001\u0003\u0006\u0004:\u0005M\u0012\u0011!C!\u0007wA!b!\u0013\u00024\u0005\u0005I\u0011AC\u0006\u0011)\u0019)&a\r\u0002\u0002\u0013\u0005Sq\u0002\u0005\u000b\u00077\n\u0019$!A\u0005B\ru\u0003BCB0\u0003g\t\t\u0011\"\u0011\u0004b!Q11MA\u001a\u0003\u0003%\t%b\u0005\b\u0013\u0015et!!A\t\n\u0015md!\u0003C|\u000f\u0005\u0005\t\u0012BC?\u0011!\u0011)\"a\u0015\u0005\u0002\u0015\u0015\u0005BCB0\u0003'\n\t\u0011\"\u0012\u0004b!Q1q]A*\u0003\u0003%\t)b\"\t\u0015\rE\u00181KA\u0001\n\u0003+YI\u0002\u0004\u0006\u0012\u001e!Q1\u0013\u0005\f\u000bC\u000biF!A!\u0002\u0013)\u0019\u000b\u0003\u0005\u0003\u0016\u0005uC\u0011ACW\u0011%)\u0019,!\u0018!\u0002\u0013))\fC\u0005\u0006<\u0006u\u0003\u0015!\u0003\u0006>!QQQXA/\u0005\u0004%\t!b0\t\u0013\u0015M\u0017Q\fQ\u0001\n\u0015\u0005\u0007\"CCk\u0003;\u0002\u000b\u0015\u0002B\u000f\u0011%)y.!\u0018!B\u0013\u0011i\u0002C\u0005\u0006b\u0006u\u0003\u0015!\u0003\u0006d\"IQ\u0011^A/A\u0003%Q1\u001e\u0005\n\u000b[\fi\u0006)Q\u0005\u0005;A\u0001\"b<\u0002^\u0011\u0005S\u0011\u001f\u0005\t\u000bs\fi\u0006\"\u0011\u0006r\"AQ1`A/\t\u0003*\t\u0010\u0003\u0005\u0006~\u0006uC\u0011BC\u0000\u0011!1)!!\u0018\u0005\n\u0019\u001d\u0001\u0002\u0003D\u0005\u0003;\"\tEb\u0003\t\u0011\u0019E\u0011Q\fC\u0005\r'A\u0001B\"\u0007\u0002^\u0011%a1\u0004\u0005\t\rC\ti\u0006\"\u0003\u0007$!AaqEA/\t\u00131I\u0003\u0003\u0005\u00072\u0005uC\u0011\u0002D\u001a\u0011!1I$!\u0018\u0005\n\u0015E\b\u0002\u0003D\u001e\u0003;\"\t%\"=\t\u0011\u0019u\u0012Q\fC\u0005\r\u007fA\u0001B\"\u0012\u0002^\u0011\u0005aq\t\u0004\n\t\u0017:\u0001\u0013aI\u0015\t\u001b:qAb\u0013\b\u0011\u0013!IKB\u0004\u0005$\u001eAI\u0001\"*\t\u0011\tU\u0011q\u0013C\u0001\tO3a\u0001\"\u0015\b\t\u0012M\u0003b\u0003C+\u00037\u0013)\u001a!C\u0001\t/B1\u0002\"\u0019\u0002\u001c\nE\t\u0015!\u0003\u0005Z!A!QCAN\t\u0003!\u0019\u0007\u0003\u0006\u0003x\u0006m\u0015\u0011!C\u0001\tSB!Ba@\u0002\u001cF\u0005I\u0011\u0001C7\u0011)\u0019Y\"a'\u0002\u0002\u0013\u00053Q\u0004\u0005\u000b\u0007_\tY*!A\u0005\u0002\tm\u0001BCB\u0019\u00037\u000b\t\u0011\"\u0001\u0005r!Q1\u0011HAN\u0003\u0003%\tea\u000f\t\u0015\r%\u00131TA\u0001\n\u0003!)\b\u0003\u0006\u0004V\u0005m\u0015\u0011!C!\tsB!ba\u0017\u0002\u001c\u0006\u0005I\u0011IB/\u0011)\u0019y&a'\u0002\u0002\u0013\u00053\u0011\r\u0005\u000b\u0007G\nY*!A\u0005B\u0011ut!\u0003D'\u000f\u0005\u0005\t\u0012\u0002D(\r%!\tfBA\u0001\u0012\u00131\t\u0006\u0003\u0005\u0003\u0016\u0005mF\u0011\u0001D+\u0011)\u0019y&a/\u0002\u0002\u0013\u00153\u0011\r\u0005\u000b\u0007O\fY,!A\u0005\u0002\u001a]\u0003BCBy\u0003w\u000b\t\u0011\"!\u0007\\\u00191A\u0011Q\u0004E\t\u0007C1\u0002\"\"\u0002F\nU\r\u0011\"\u0001\u0003\u001c!YAqQAc\u0005#\u0005\u000b\u0011\u0002B\u000f\u0011!\u0011)\"!2\u0005\u0002\u0011%\u0005B\u0003B|\u0003\u000b\f\t\u0011\"\u0001\u0005\u0010\"Q!q`Ac#\u0003%\taa\u0006\t\u0015\rm\u0011QYA\u0001\n\u0003\u001ai\u0002\u0003\u0006\u00040\u0005\u0015\u0017\u0011!C\u0001\u00057A!b!\r\u0002F\u0006\u0005I\u0011\u0001CJ\u0011)\u0019I$!2\u0002\u0002\u0013\u000531\b\u0005\u000b\u0007\u0013\n)-!A\u0005\u0002\u0011]\u0005BCB+\u0003\u000b\f\t\u0011\"\u0011\u0005\u001c\"Q11LAc\u0003\u0003%\te!\u0018\t\u0015\r}\u0013QYA\u0001\n\u0003\u001a\t\u0007\u0003\u0006\u0004d\u0005\u0015\u0017\u0011!C!\t?;\u0011Bb\u0018\b\u0003\u0003EIA\"\u0019\u0007\u0013\u0011\u0005u!!A\t\n\u0019\r\u0004\u0002\u0003B\u000b\u0003K$\tAb\u001a\t\u0015\r}\u0013Q]A\u0001\n\u000b\u001a\t\u0007\u0003\u0006\u0004h\u0006\u0015\u0018\u0011!CA\rSB!b!=\u0002f\u0006\u0005I\u0011\u0011D7\u0011\u001d1\u0019h\u0002C!\rk\nAB\u0011:pC\u0012\u001c\u0017m\u001d;Ik\nTA!!>\u0002x\u0006A1oY1mC\u0012\u001cHN\u0003\u0003\u0002z\u0006m\u0018AB:ue\u0016\fWN\u0003\u0002\u0002~\u0006!\u0011m[6b\u0007\u0001\u00012Aa\u0001\u0002\u001b\t\t\u0019P\u0001\u0007Ce>\fGmY1ti\"+(mE\u0002\u0002\u0005\u0013\u0001BAa\u0003\u0003\u00125\u0011!Q\u0002\u0006\u0003\u0005\u001f\tQa]2bY\u0006LAAa\u0005\u0003\u000e\t1\u0011I\\=SK\u001a\fa\u0001P5oSRtDC\u0001B\u0001\u0003E!WMZ1vYR\u0014UO\u001a4feNK'0Z\u000b\u0003\u0005;\u0001BAa\u0003\u0003 %!!\u0011\u0005B\u0007\u0005\rIe\u000e^\u0001\u0013I\u00164\u0017-\u001e7u\u0005V4g-\u001a:TSj,\u0007\u0005K\u0002\u0005\u0005O\u0001BA!\u000b\u000305\u0011!1\u0006\u0006\u0005\u0005[\tY0\u0001\u0006b]:|G/\u0019;j_:LAA!\r\u0003,\tY\u0011J\u001c;fe:\fG.\u00119j\u0003\u0011\u0019\u0018N\\6\u0016\t\t]\"1\t\u000b\u0005\u0005s\u0011\u0019\u0007\u0005\u0005\u0003\u0004\tm\"q\bB+\u0013\u0011\u0011i$a=\u0003\tMKgn\u001b\t\u0005\u0005\u0003\u0012\u0019\u0005\u0004\u0001\u0005\u000f\t\u0015SA1\u0001\u0003H\t\tA+\u0005\u0003\u0003J\t=\u0003\u0003\u0002B\u0006\u0005\u0017JAA!\u0014\u0003\u000e\t9aj\u001c;iS:<\u0007\u0003\u0002B\u0006\u0005#JAAa\u0015\u0003\u000e\t\u0019\u0011I\\=\u0011\u0011\t\r!q\u000bB \u00057JAA!\u0017\u0002t\n11k\\;sG\u0016\u0004BA!\u0018\u0003`5\u0011\u00111`\u0005\u0005\u0005C\nYPA\u0004O_R,6/\u001a3\t\u000f\t\u0015T\u00011\u0001\u0003\u001e\u0005Q!-\u001e4gKJ\u001c\u0016N_3\u0016\t\t%$qN\u000b\u0003\u0005W\u0002\u0002Ba\u0001\u0003<\t5$\u0011\u000f\t\u0005\u0005\u0003\u0012y\u0007B\u0004\u0003F\u0019\u0011\rAa\u0012\u0011\u0011\t\r!q\u000bB7\u00057*BA!\u001e\u0003\u000eN\u0019qAa\u001e\u0011\u0011\te$q\u0010BB\u0005\u001fk!Aa\u001f\u000b\t\tu\u0014q_\u0001\u0006gR\fw-Z\u0005\u0005\u0005\u0003\u0013YHA\u0010He\u0006\u0004\bn\u0015;bO\u0016<\u0016\u000e\u001e5NCR,'/[1mSj,GMV1mk\u0016\u0004bA!\"\u0003\b\n-UBAA|\u0013\u0011\u0011I)a>\u0003\u0013MKgn[*iCB,\u0007\u0003\u0002B!\u0005\u001b#qA!\u0012\b\u0005\u0004\u00119\u0005\u0005\u0005\u0003\u0004\t]#1\u0012B.)\u0011\u0011\u0019J!&\u0011\u000b\t\rqAa#\t\u000f\t\u0015\u0014\u00021\u0001\u0003\u001e\u0005!Q*Y:l\u0003\u0015i\u0015m]6!\u0003%9\u0006.Z3m\u001b\u0006\u001c8.\u0001\u0006XQ\u0016,G.T1tW\u0002\n!!\u001b8\u0016\u0005\t\r\u0006C\u0002BC\u0005K\u0013Y)\u0003\u0003\u0003(\u0006](!B%oY\u0016$\u0018aA5oA\u0005)1\u000f[1qKV\u0011!1Q\u0001\u0007g\"\f\u0007/\u001a\u0011\u0002\u001f\u0011+W.\u00198e)\"\u0014Xm\u001d5pY\u0012\u0014\u0001\u0002S;c\u000bZ,g\u000e^\n\u0004'\t%\u0011&B\n3\u0015V9\"aB!em\u0006t7-Z\n\ne\t%!Q\u0018Ba\u0005\u000f\u00042Aa0\u0014\u001b\u00059\u0001\u0003\u0002B\u0006\u0005\u0007LAA!2\u0003\u000e\t9\u0001K]8ek\u000e$\b\u0003\u0002Be\u00053tAAa3\u0003V:!!Q\u001aBj\u001b\t\u0011yM\u0003\u0003\u0003R\u0006}\u0018A\u0002\u001fs_>$h(\u0003\u0002\u0003\u0010%!!q\u001bB\u0007\u0003\u001d\u0001\u0018mY6bO\u0016LAAa7\u0003^\na1+\u001a:jC2L'0\u00192mK*!!q\u001bB\u0007\u0003\tIG-\u0006\u0002\u0003dB!!1\u0002Bs\u0013\u0011\u00119O!\u0004\u0003\t1{gnZ\u0001\u0004S\u0012\u0004\u0013A\u00049sKZLw.^:PM\u001a\u001cX\r^\u0001\u0010aJ,g/[8vg>3gm]3uAQ1!\u0011\u001fBz\u0005k\u00042Aa03\u0011\u001d\u0011yn\u000ea\u0001\u0005GDqAa;8\u0001\u0004\u0011i\"\u0001\u0003d_BLHC\u0002By\u0005w\u0014i\u0010C\u0005\u0003`b\u0002\n\u00111\u0001\u0003d\"I!1\u001e\u001d\u0011\u0002\u0003\u0007!QD\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\t\u0019\u0019A\u000b\u0003\u0003d\u000e\u00151FAB\u0004!\u0011\u0019Ia!\u0005\u000e\u0005\r-!\u0002BB\u0007\u0007\u001f\t\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\t\t5\"QB\u0005\u0005\u0007'\u0019YAA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'\u0006\u0002\u0004\u001a)\"!QDB\u0003\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u00111q\u0004\t\u0005\u0007C\u0019Y#\u0004\u0002\u0004$)!1QEB\u0014\u0003\u0011a\u0017M\\4\u000b\u0005\r%\u0012\u0001\u00026bm\u0006LAa!\f\u0004$\t11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0003\u0003P\rU\u0002\"CB\u001c{\u0005\u0005\t\u0019\u0001B\u000f\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u00111Q\b\t\u0007\u0007\u007f\u0019)Ea\u0014\u000e\u0005\r\u0005#\u0002BB\"\u0005\u001b\t!bY8mY\u0016\u001cG/[8o\u0013\u0011\u00199e!\u0011\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0007\u001b\u001a\u0019\u0006\u0005\u0003\u0003\f\r=\u0013\u0002BB)\u0005\u001b\u0011qAQ8pY\u0016\fg\u000eC\u0005\u00048}\n\t\u00111\u0001\u0003P\u0005\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\u0019yb!\u0017\t\u0013\r]\u0002)!AA\u0002\tu\u0011\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\tu\u0011\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\r}\u0011AB3rk\u0006d7\u000f\u0006\u0003\u0004N\r\u001d\u0004\"CB\u001c\u0007\u0006\u0005\t\u0019\u0001B(\u0005)qU-\u001a3XC.,W\u000f]\n\n\u0015\n%!Q\u0018Ba\u0005\u000f\fQbY;se\u0016tGo\u00144gg\u0016$\u0018AD2veJ,g\u000e^(gMN,G\u000f\t\u000b\t\u0007g\u001a)ha\u001e\u0004zA\u0019!q\u0018&\t\u000f\t}\u0017\u000b1\u0001\u0003d\"9!1^)A\u0002\tu\u0001bBB7#\u0002\u0007!Q\u0004\u000b\t\u0007g\u001aiha \u0004\u0002\"I!q\u001c*\u0011\u0002\u0003\u0007!1\u001d\u0005\n\u0005W\u0014\u0006\u0013!a\u0001\u0005;A\u0011b!\u001cS!\u0003\u0005\rA!\b\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gQ!!qJBD\u0011%\u00199\u0004WA\u0001\u0002\u0004\u0011i\u0002\u0006\u0003\u0004N\r-\u0005\"CB\u001c5\u0006\u0005\t\u0019\u0001B()\u0011\u0019yba$\t\u0013\r]2,!AA\u0002\tuA\u0003BB'\u0007'C\u0011ba\u000e_\u0003\u0003\u0005\rAa\u0014\u0003'I+w-[:ue\u0006$\u0018n\u001c8QK:$\u0017N\\4\u0014\u000bU\u0011IA!0\u0015\u0005\rm\u0005c\u0001B`+\tQQK\u001c*fO&\u001cH/\u001a:\u0014\u0013]\u0011IA!0\u0003B\n\u001d\u0017a\u00034j]\u0006dwJ\u001a4tKR\fABZ5oC2|eMZ:fi\u0002\"\u0002ba*\u0004*\u000e-6Q\u0016\t\u0004\u0005\u007f;\u0002b\u0002Bp=\u0001\u0007!1\u001d\u0005\b\u0005Wt\u0002\u0019\u0001B\u000f\u0011\u001d\u0019\tK\ba\u0001\u0005;!\u0002ba*\u00042\u000eM6Q\u0017\u0005\n\u0005?|\u0002\u0013!a\u0001\u0005GD\u0011Ba; !\u0003\u0005\rA!\b\t\u0013\r\u0005v\u0004%AA\u0002\tuA\u0003\u0002B(\u0007sC\u0011ba\u000e&\u0003\u0003\u0005\rA!\b\u0015\t\r53Q\u0018\u0005\n\u0007o9\u0013\u0011!a\u0001\u0005\u001f\"Baa\b\u0004B\"I1q\u0007\u0015\u0002\u0002\u0003\u0007!Q\u0004\u000b\u0005\u0007\u001b\u001a)\rC\u0005\u00048-\n\t\u00111\u0001\u0003P\u0005\u0019\"+Z4jgR\u0014\u0018\r^5p]B+g\u000eZ5oO\u0006QQK\u001c*fO&\u001cH/\u001a:\u0011\u0007\t}VfE\u0003.\u0007\u001f\u001cY\u000e\u0005\u0007\u0004R\u000e]'1\u001dB\u000f\u0005;\u00199+\u0004\u0002\u0004T*!1Q\u001bB\u0007\u0003\u001d\u0011XO\u001c;j[\u0016LAa!7\u0004T\n\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u001a\u0011\t\ru71]\u0007\u0003\u0007?TAa!9\u0004(\u0005\u0011\u0011n\\\u0005\u0005\u00057\u001cy\u000e\u0006\u0002\u0004L\u0006)\u0011\r\u001d9msRA1qUBv\u0007[\u001cy\u000fC\u0004\u0003`B\u0002\rAa9\t\u000f\t-\b\u00071\u0001\u0003\u001e!91\u0011\u0015\u0019A\u0002\tu\u0011aB;oCB\u0004H.\u001f\u000b\u0005\u0007k$\t\u0001\u0005\u0004\u0003\f\r]81`\u0005\u0005\u0007s\u0014iA\u0001\u0004PaRLwN\u001c\t\u000b\u0005\u0017\u0019iPa9\u0003\u001e\tu\u0011\u0002BB\u0000\u0005\u001b\u0011a\u0001V;qY\u0016\u001c\u0004\"\u0003C\u0002c\u0005\u0005\t\u0019ABT\u0003\rAH\u0005M\u0001\b\u0003\u00124\u0018M\\2f!\r\u0011y,R\n\u0006\u000b\u0012-11\u001c\t\u000b\u0007#$iAa9\u0003\u001e\tE\u0018\u0002\u0002C\b\u0007'\u0014\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c83)\t!9\u0001\u0006\u0004\u0003r\u0012UAq\u0003\u0005\b\u0005?D\u0005\u0019\u0001Br\u0011\u001d\u0011Y\u000f\u0013a\u0001\u0005;!B\u0001b\u0007\u0005$A1!1BB|\t;\u0001\u0002Ba\u0003\u0005 \t\r(QD\u0005\u0005\tC\u0011iA\u0001\u0004UkBdWM\r\u0005\n\t\u0007I\u0015\u0011!a\u0001\u0005c\f!BT3fI^\u000b7.Z;q!\r\u0011y\fY\n\u0006A\u0012-21\u001c\t\r\u0007#\u001c9Na9\u0003\u001e\tu11\u000f\u000b\u0003\tO!\u0002ba\u001d\u00052\u0011MBQ\u0007\u0005\b\u0005?\u001c\u0007\u0019\u0001Br\u0011\u001d\u0011Yo\u0019a\u0001\u0005;Aqa!\u001cd\u0001\u0004\u0011i\u0002\u0006\u0003\u0004v\u0012e\u0002\"\u0003C\u0002I\u0006\u0005\t\u0019AB:\u0005!\u0019uN\\:v[\u0016\u00148cB3\u0003\n\t\u0005'qY\u0001\tG\u0006dGNY1dWV\u0011A1\t\t\u0007\u0005s\")\u0005\"\u0013\n\t\u0011\u001d#1\u0010\u0002\u000e\u0003NLhnY\"bY2\u0014\u0017mY6\u0011\t\t}\u00161\u0013\u0002\u000e\u0007>t7/^7fe\u00163XM\u001c;\u0014\t\u0005M%\u0011B\u0015\t\u0003'\u000bY*!2\u0002\u0018\na\u0001*\u001e2D_6\u0004H.\u001a;fINQ\u00111\u0014B\u0005\t\u0013\u0012\tMa2\u0002\u000f\u0019\f\u0017\u000e\\;sKV\u0011A\u0011\f\t\u0007\u0005\u0017\u00199\u0010b\u0017\u0011\t\t%GQL\u0005\u0005\t?\u0012iNA\u0005UQJ|w/\u00192mK\u0006Aa-Y5mkJ,\u0007\u0005\u0006\u0003\u0005f\u0011\u001d\u0004\u0003\u0002B`\u00037C\u0001\u0002\"\u0016\u0002\"\u0002\u0007A\u0011\f\u000b\u0005\tK\"Y\u0007\u0003\u0006\u0005V\u0005\r\u0006\u0013!a\u0001\t3*\"\u0001b\u001c+\t\u0011e3Q\u0001\u000b\u0005\u0005\u001f\"\u0019\b\u0003\u0006\u00048\u0005-\u0016\u0011!a\u0001\u0005;!Ba!\u0014\u0005x!Q1qGAX\u0003\u0003\u0005\rAa\u0014\u0015\t\r}A1\u0010\u0005\u000b\u0007o\t\t,!AA\u0002\tuA\u0003BB'\t\u007fB!ba\u000e\u00028\u0006\u0005\t\u0019\u0001B(\u0005)Ie.\u001b;jC2L'0Z\n\u000b\u0003\u000b\u0014I\u0001\"\u0013\u0003B\n\u001d\u0017AB8gMN,G/A\u0004pM\u001a\u001cX\r\u001e\u0011\u0015\t\u0011-EQ\u0012\t\u0005\u0005\u007f\u000b)\r\u0003\u0005\u0005\u0006\u0006-\u0007\u0019\u0001B\u000f)\u0011!Y\t\"%\t\u0015\u0011\u0015\u0015Q\u001aI\u0001\u0002\u0004\u0011i\u0002\u0006\u0003\u0003P\u0011U\u0005BCB\u001c\u0003+\f\t\u00111\u0001\u0003\u001eQ!1Q\nCM\u0011)\u00199$!7\u0002\u0002\u0003\u0007!q\n\u000b\u0005\u0007?!i\n\u0003\u0006\u00048\u0005m\u0017\u0011!a\u0001\u0005;!Ba!\u0014\u0005\"\"Q1qGAq\u0003\u0003\u0005\rAa\u0014\u0003\r]\u000b7.Z;q'\u0019\t9J!\u0003\u0005JQ\u0011A\u0011\u0016\t\u0005\u0005\u007f\u000b9*A\u0005dC2d'-Y2lAQ1Aq\u0016CY\tg\u00032Aa0f\u0011\u001d\u0011yN\u001ba\u0001\u0005GDq\u0001b\u0010k\u0001\u0004!\u0019\u0005\u0006\u0004\u00050\u0012]F\u0011\u0018\u0005\n\u0005?\\\u0007\u0013!a\u0001\u0005GD\u0011\u0002b\u0010l!\u0003\u0005\r\u0001b\u0011\u0016\u0005\u0011u&\u0006\u0002C\"\u0007\u000b!BAa\u0014\u0005B\"I1q\u00079\u0002\u0002\u0003\u0007!Q\u0004\u000b\u0005\u0007\u001b\")\rC\u0005\u00048I\f\t\u00111\u0001\u0003PQ!1q\u0004Ce\u0011%\u00199d]A\u0001\u0002\u0004\u0011i\u0002\u0006\u0003\u0004N\u00115\u0007\"CB\u001cm\u0006\u0005\t\u0019\u0001B(\u0003!\u0019uN\\:v[\u0016\u0014\bc\u0001B`qN)\u0001\u0010\"6\u0004\\BQ1\u0011\u001bC\u0007\u0005G$\u0019\u0005b,\u0015\u0005\u0011EGC\u0002CX\t7$i\u000eC\u0004\u0003`n\u0004\rAa9\t\u000f\u0011}2\u00101\u0001\u0005DQ!A\u0011\u001dCs!\u0019\u0011Yaa>\u0005dBA!1\u0002C\u0010\u0005G$\u0019\u0005C\u0005\u0005\u0004q\f\t\u00111\u0001\u00050\u0006I1i\\7qY\u0016$X\r\u001a\t\u0004\u0005\u007fs(!C\"p[BdW\r^3e'\rq(\u0011\u0002\u000b\u0003\tS\u0014\u0001\u0002S;c'R\fG/Z\n\u0005\u0003\u0003\u0011I!\u000b\u0004\u0002\u0002\u0005M\u00121\u0001\u0002\u0007\u00072|7/\u001a3\u0014\u0015\u0005M\"\u0011\u0002C~\u0005\u0003\u00149\r\u0005\u0003\u0003@\u0006\u0005A\u0003\u0002C\u0000\u000b\u0003\u0001BAa0\u00024!AAQKA\u001d\u0001\u0004!I\u0006\u0006\u0003\u0005\u0000\u0016\u0015\u0001B\u0003C+\u0003w\u0001\n\u00111\u0001\u0005ZQ!!qJC\u0005\u0011)\u00199$a\u0011\u0002\u0002\u0003\u0007!Q\u0004\u000b\u0005\u0007\u001b*i\u0001\u0003\u0006\u00048\u0005\u001d\u0013\u0011!a\u0001\u0005\u001f\"Baa\b\u0006\u0012!Q1qGA%\u0003\u0003\u0005\rA!\b\u0015\t\r5SQ\u0003\u0005\u000b\u0007o\ty%!AA\u0002\t=#\u0001B(qK:\u001c\"\"a\u0001\u0003\n\u0011m(\u0011\u0019Bd\u00039\u0019\u0017\r\u001c7cC\u000e\\g)\u001e;ve\u0016,\"!b\b\u0011\r\u0015\u0005RqEC\u0016\u001b\t)\u0019C\u0003\u0003\u0006&\t5\u0011AC2p]\u000e,(O]3oi&!Q\u0011FC\u0012\u0005\u00191U\u000f^;sKB1!\u0011\u0010C#\u0005{\u000bqbY1mY\n\f7m\u001b$viV\u0014X\rI\u0001\u000ee\u0016<\u0017n\u001d;sCRLwN\\:\u0016\u0005\u0015M\u0002C\u0002Be\u000bk!y+\u0003\u0003\u00068\tu'\u0001\u0002'jgR\faB]3hSN$(/\u0019;j_:\u001c\b\u0005\u0006\u0004\u0006>\u0015}R\u0011\t\t\u0005\u0005\u007f\u000b\u0019\u0001\u0003\u0005\u0006\u001c\u00055\u0001\u0019AC\u0010\u0011!)y#!\u0004A\u0002\u0015MBCBC\u001f\u000b\u000b*9\u0005\u0003\u0006\u0006\u001c\u0005=\u0001\u0013!a\u0001\u000b?A!\"b\f\u0002\u0010A\u0005\t\u0019AC\u001a+\t)YE\u000b\u0003\u0006 \r\u0015QCAC(U\u0011)\u0019d!\u0002\u0015\t\t=S1\u000b\u0005\u000b\u0007o\tI\"!AA\u0002\tuA\u0003BB'\u000b/B!ba\u000e\u0002\u001e\u0005\u0005\t\u0019\u0001B()\u0011\u0019y\"b\u0017\t\u0015\r]\u0012qDA\u0001\u0002\u0004\u0011i\u0002\u0006\u0003\u0004N\u0015}\u0003BCB\u001c\u0003K\t\t\u00111\u0001\u0003P\u0005!q\n]3o!\u0011\u0011y,!\u000b\u0014\r\u0005%RqMBn!)\u0019\t\u000e\"\u0004\u0006 \u0015MRQ\b\u000b\u0003\u000bG\"b!\"\u0010\u0006n\u0015=\u0004\u0002CC\u000e\u0003_\u0001\r!b\b\t\u0011\u0015=\u0012q\u0006a\u0001\u000bg!B!b\u001d\u0006xA1!1BB|\u000bk\u0002\u0002Ba\u0003\u0005 \u0015}Q1\u0007\u0005\u000b\t\u0007\t\t$!AA\u0002\u0015u\u0012AB\"m_N,G\r\u0005\u0003\u0003@\u0006M3CBA*\u000b\u007f\u001aY\u000e\u0005\u0005\u0004R\u0016\u0005E\u0011\fC\u0000\u0013\u0011)\u0019ia5\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t\u0017\u0007\u0006\u0002\u0006|Q!Aq`CE\u0011!!)&!\u0017A\u0002\u0011eC\u0003BCG\u000b\u001f\u0003bAa\u0003\u0004x\u0012e\u0003B\u0003C\u0002\u00037\n\t\u00111\u0001\u0005\u0000\n\u0011\"I]8bI\u000e\f7\u000f^*j].dunZ5d'\u0019\ti&\"&\u0006\u001cB!!\u0011PCL\u0013\u0011)IJa\u001f\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000e\u0004BA!\u001f\u0006\u001e&!Qq\u0014B>\u0005%Ie\u000eS1oI2,'/\u0001\u0004`g\"\f\u0007/\u001a\t\u0005\u0005\u007f+)+\u0003\u0003\u0006(\u0016%&!B*iCB,\u0017\u0002BCV\u0003o\u0014Qa\u0012:ba\"$B!b,\u00062B!!qXA/\u0011!)\t+!\u0019A\u0002\u0015\r\u0016aD2bY2\u0014\u0017mY6Qe>l\u0017n]3\u0011\r\u0015\u0005RqWC\u0016\u0013\u0011)I,b\t\u0003\u000fA\u0013x.\\5tK\u0006!bn\u001c*fO&\u001cHO]1uS>t7o\u0015;bi\u0016\fQa\u001d;bi\u0016,\"!\"1\u0011\r\u0015\rWq\u001aC~\u001b\t))M\u0003\u0003\u0006H\u0016%\u0017AB1u_6L7M\u0003\u0003\u0006&\u0015-'\u0002BCg\u0007O\tA!\u001e;jY&!Q\u0011[Cc\u0005=\tEo\\7jGJ+g-\u001a:f]\u000e,\u0017AB:uCR,\u0007%\u0001\u0003uC&d\u0007\u0006BA6\u000b3\u0004BAa\u0003\u0006\\&!QQ\u001cB\u0007\u0005!1x\u000e\\1uS2,\u0017\u0001\u00025fC\u0012\fQ!];fk\u0016\u0004bAa\u0003\u0006f\n%\u0011\u0002BCt\u0005\u001b\u0011Q!\u0011:sCf\fQbY8ogVlWM],iK\u0016d\u0007C\u0002B\u0006\u000bK,\u0019$A\bbGRLg/Z\"p]N,X.\u001a:t\u0003!\u0001(/Z*uCJ$HCACz!\u0011\u0011Y!\">\n\t\u0015](Q\u0002\u0002\u0005+:LG/\u0001\tp]V\u00038\u000f\u001e:fC64\u0015N\\5tQ\u00061qN\u001c)vg\"\fqa\u001c8Fm\u0016tG\u000f\u0006\u0003\u0006t\u001a\u0005\u0001\u0002\u0003D\u0002\u0003w\u0002\rA!0\u0002\u0005\u00154\u0018AB5t\rVdG.\u0006\u0002\u0004N\u0005\trN\\+qgR\u0014X-Y7GC&dWO]3\u0015\t\u0015MhQ\u0002\u0005\t\r\u001f\ty\b1\u0001\u0005\\\u0005\u0011Q\r_\u0001\u0016M&tG-\u00118e%\u0016lwN^3D_:\u001cX/\\3s)\u0019!yK\"\u0006\u0007\u0018!A!q\\AA\u0001\u0004\u0011\u0019\u000f\u0003\u0005\u0005\u0006\u0006\u0005\u0005\u0019\u0001B\u000f\u00031\u0019\u0007.Z2l+:\u0014Gn\\2l)\u0011)\u0019P\"\b\t\u0011\u0019}\u00111\u0011a\u0001\u0005;\tqc\u001c4gg\u0016$xJZ\"p]N,X.\u001a:SK6|g/\u001a3\u0002#Ut'\r\\8dW&3\u0007k\\:tS\ndW\r\u0006\u0003\u0004N\u0019\u0015\u0002\u0002\u0003D\u0010\u0003\u000b\u0003\rA!\b\u0002\u0017\u0005$GmQ8ogVlWM\u001d\u000b\u0007\u000bg4YCb\f\t\u0011\u00195\u0012q\u0011a\u0001\t_\u000b\u0001bY8ogVlWM\u001d\u0005\t\t\u000b\u000b9\t1\u0001\u0003\u001e\u0005Iq/Y6fkBLE\r\u001f\u000b\u0005\u000bg4)\u0004\u0003\u0005\u00078\u0005%\u0005\u0019\u0001B\u000f\u0003\rIG\r_\u0001\tG>l\u0007\u000f\\3uK\u0006A\u0001o\\:u'R|\u0007/A\u0004qk\nd\u0017n\u001d5\u0015\t\u0015Mh\u0011\t\u0005\t\r\u0007\ny\t1\u0001\u0003\f\u0006!Q\r\\3n\u0003\u0011\u0001x\u000e\u001c7\u0015\t\t%a\u0011\n\u0005\t\t\u000b\u000b\t\n1\u0001\u0003\u001e\u00051q+Y6fkB\fA\u0002S;c\u0007>l\u0007\u000f\\3uK\u0012\u0004BAa0\u0002<N1\u00111\u0018D*\u00077\u0004\u0002b!5\u0006\u0002\u0012eCQ\r\u000b\u0003\r\u001f\"B\u0001\"\u001a\u0007Z!AAQKAa\u0001\u0004!I\u0006\u0006\u0003\u0006\u000e\u001au\u0003B\u0003C\u0002\u0003\u0007\f\t\u00111\u0001\u0005f\u0005Q\u0011J\\5uS\u0006d\u0017N_3\u0011\t\t}\u0016Q]\n\u0007\u0003K4)ga7\u0011\u0011\rEW\u0011\u0011B\u000f\t\u0017#\"A\"\u0019\u0015\t\u0011-e1\u000e\u0005\t\t\u000b\u000bY\u000f1\u0001\u0003\u001eQ!aq\u000eD9!\u0019\u0011Yaa>\u0003\u001e!QA1AAw\u0003\u0003\u0005\r\u0001b#\u0002?\r\u0014X-\u0019;f\u0019><\u0017nY!oI6\u000bG/\u001a:jC2L'0\u001a3WC2,X\r\u0006\u0003\u0007x\u0019e\u0004\u0003\u0003B\u0006\t?))Ja$\t\u0011\u0019m\u0014q\u001ea\u0001\r{\n1#\u001b8iKJLG/\u001a3BiR\u0014\u0018NY;uKN\u0004BA!\"\u0007\u0000%!a\u0011QA|\u0005)\tE\u000f\u001e:jEV$Xm\u001d")
public class BroadcastHub<T>
extends GraphStageWithMaterializedValue<SinkShape<T>, Source<T, NotUsed>> {
    private volatile BroadcastHub$RegistrationPending$ RegistrationPending$module;
    private volatile BroadcastHub$UnRegister$ UnRegister$module;
    private volatile BroadcastHub$Advance$ Advance$module;
    private volatile BroadcastHub$NeedWakeup$ NeedWakeup$module;
    private volatile BroadcastHub$Consumer$ Consumer$module;
    private volatile BroadcastHub$Completed$ Completed$module;
    private volatile BroadcastHub$Open$ Open$module;
    private volatile BroadcastHub$Closed$ Closed$module;
    private volatile BroadcastHub$Wakeup$ Wakeup$module;
    private volatile BroadcastHub$HubCompleted$ HubCompleted$module;
    private volatile BroadcastHub$Initialize$ Initialize$module;
    public final int akka$stream$scaladsl$BroadcastHub$$bufferSize;
    private final int akka$stream$scaladsl$BroadcastHub$$Mask;
    private final int akka$stream$scaladsl$BroadcastHub$$WheelMask;
    private final Inlet<T> in;
    private final SinkShape<T> shape;
    public final int akka$stream$scaladsl$BroadcastHub$$DemandThreshold;

    public static <T> Sink<T, Source<T, NotUsed>> sink() {
        return BroadcastHub$.MODULE$.sink();
    }

    public static <T> Sink<T, Source<T, NotUsed>> sink(int bufferSize) {
        return BroadcastHub$.MODULE$.sink(bufferSize);
    }

    public BroadcastHub$RegistrationPending$ akka$stream$scaladsl$BroadcastHub$$RegistrationPending() {
        if (this.RegistrationPending$module == null) {
            this.RegistrationPending$lzycompute$1();
        }
        return this.RegistrationPending$module;
    }

    private BroadcastHub$UnRegister$ UnRegister() {
        if (this.UnRegister$module == null) {
            this.UnRegister$lzycompute$1();
        }
        return this.UnRegister$module;
    }

    private BroadcastHub$Advance$ Advance() {
        if (this.Advance$module == null) {
            this.Advance$lzycompute$1();
        }
        return this.Advance$module;
    }

    private BroadcastHub$NeedWakeup$ NeedWakeup() {
        if (this.NeedWakeup$module == null) {
            this.NeedWakeup$lzycompute$1();
        }
        return this.NeedWakeup$module;
    }

    private BroadcastHub$Consumer$ Consumer() {
        if (this.Consumer$module == null) {
            this.Consumer$lzycompute$1();
        }
        return this.Consumer$module;
    }

    public BroadcastHub$Completed$ akka$stream$scaladsl$BroadcastHub$$Completed() {
        if (this.Completed$module == null) {
            this.Completed$lzycompute$1();
        }
        return this.Completed$module;
    }

    private BroadcastHub$Open$ Open() {
        if (this.Open$module == null) {
            this.Open$lzycompute$1();
        }
        return this.Open$module;
    }

    private BroadcastHub$Closed$ Closed() {
        if (this.Closed$module == null) {
            this.Closed$lzycompute$1();
        }
        return this.Closed$module;
    }

    public BroadcastHub$Wakeup$ akka$stream$scaladsl$BroadcastHub$$Wakeup() {
        if (this.Wakeup$module == null) {
            this.Wakeup$lzycompute$1();
        }
        return this.Wakeup$module;
    }

    private BroadcastHub$HubCompleted$ HubCompleted() {
        if (this.HubCompleted$module == null) {
            this.HubCompleted$lzycompute$1();
        }
        return this.HubCompleted$module;
    }

    private BroadcastHub$Initialize$ Initialize() {
        if (this.Initialize$module == null) {
            this.Initialize$lzycompute$1();
        }
        return this.Initialize$module;
    }

    public int akka$stream$scaladsl$BroadcastHub$$Mask() {
        return this.akka$stream$scaladsl$BroadcastHub$$Mask;
    }

    public int akka$stream$scaladsl$BroadcastHub$$WheelMask() {
        return this.akka$stream$scaladsl$BroadcastHub$$WheelMask;
    }

    public Inlet<T> in() {
        return this.in;
    }

    @Override
    public SinkShape<T> shape() {
        return this.shape;
    }

    @Override
    public Tuple2<GraphStageLogic, Source<T, NotUsed>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        AtomicLong idCounter = new AtomicLong();
        BroadcastSinkLogic logic = new BroadcastSinkLogic(this.shape());
        GraphStage source2 = new GraphStage<SourceShape<T>>(this, idCounter, logic){
            private final Outlet<T> out;
            private final SourceShape<T> shape;
            private final /* synthetic */ BroadcastHub $outer;
            public final AtomicLong idCounter$2;
            public final BroadcastSinkLogic logic$2;

            public Outlet<T> out() {
                return this.out;
            }

            public SourceShape<T> shape() {
                return this.shape;
            }

            public GraphStageLogic createLogic(Attributes inheritedAttributes) {
                return new OutHandler(this){
                    private int untilNextAdvanceSignal;
                    private final long id;
                    private boolean offsetInitialized;
                    private AsyncCallback<HubEvent> hubCallback;
                    private int previousPublishedOffset;
                    private int offset;
                    private final /* synthetic */ $anon$4 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void preStart() {
                        AsyncCallback<T> callback = this.getAsyncCallback((Function1<ConsumerEvent, Object> & Serializable)cmd -> {
                            $anon$4$$anon$5.$anonfun$preStart$3(this, cmd);
                            return BoxedUnit.UNIT;
                        });
                        Function1<Try, Object> & Serializable onHubReady = (Function1<Try, Object> & Serializable)x0$1 -> {
                            $anon$4$$anon$5.$anonfun$preStart$4(this, x0$1);
                            return BoxedUnit.UNIT;
                        };
                        this.register$1(callback, onHubReady);
                    }

                    public void onPull() {
                        if (this.offsetInitialized && this.hubCallback != null) {
                            Object elem = this.$outer.logic$2.poll(this.offset);
                            Object object = elem;
                            if (object == null) {
                                this.hubCallback.invoke(new NeedWakeup(this.$outer.akka$stream$scaladsl$BroadcastHub$$anon$$$outer(), this.id, this.previousPublishedOffset, this.offset));
                                this.previousPublishedOffset = this.offset;
                                this.untilNextAdvanceSignal = this.$outer.akka$stream$scaladsl$BroadcastHub$$anon$$$outer().akka$stream$scaladsl$BroadcastHub$$DemandThreshold;
                                return;
                            }
                            if (this.$outer.akka$stream$scaladsl$BroadcastHub$$anon$$$outer().akka$stream$scaladsl$BroadcastHub$$Completed().equals(object)) {
                                this.completeStage();
                                return;
                            }
                            this.push(this.$outer.out(), elem);
                            ++this.offset;
                            --this.untilNextAdvanceSignal;
                            if (this.untilNextAdvanceSignal == 0) {
                                this.untilNextAdvanceSignal = this.$outer.akka$stream$scaladsl$BroadcastHub$$anon$$$outer().akka$stream$scaladsl$BroadcastHub$$DemandThreshold;
                                int previousOffset = this.previousPublishedOffset;
                                this.previousPublishedOffset += this.$outer.akka$stream$scaladsl$BroadcastHub$$anon$$$outer().akka$stream$scaladsl$BroadcastHub$$DemandThreshold;
                                this.hubCallback.invoke(new Advance(this.$outer.akka$stream$scaladsl$BroadcastHub$$anon$$$outer(), this.id, previousOffset));
                                return;
                            }
                            return;
                        }
                    }

                    public void postStop() {
                        if (this.hubCallback != null) {
                            this.hubCallback.invoke(new UnRegister(this.$outer.akka$stream$scaladsl$BroadcastHub$$anon$$$outer(), this.id, this.previousPublishedOffset, this.offset));
                            return;
                        }
                    }

                    private void onCommand(ConsumerEvent cmd) {
                        Option<Throwable> option;
                        boolean bl = false;
                        HubCompleted hubCompleted = null;
                        ConsumerEvent consumerEvent = cmd;
                        if (consumerEvent instanceof HubCompleted) {
                            bl = true;
                            hubCompleted = (HubCompleted)consumerEvent;
                            Option<Throwable> option2 = hubCompleted.failure();
                            if (option2 instanceof Some) {
                                Some some = (Some)option2;
                                Throwable ex = (Throwable)some.value();
                                this.failStage(ex);
                                return;
                            }
                        }
                        if (bl && None$.MODULE$.equals(option = hubCompleted.failure())) {
                            this.completeStage();
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$BroadcastHub$$anon$$$outer().akka$stream$scaladsl$BroadcastHub$$Wakeup().equals(consumerEvent)) {
                            if (this.isAvailable(this.$outer.out())) {
                                this.onPull();
                                return;
                            }
                            return;
                        }
                        if (consumerEvent instanceof Initialize) {
                            Initialize initialize = (Initialize)consumerEvent;
                            int initialOffset = initialize.offset();
                            this.offsetInitialized = true;
                            this.previousPublishedOffset = initialOffset;
                            this.offset = initialOffset;
                            if (this.isAvailable(this.$outer.out()) && this.hubCallback != null) {
                                this.onPull();
                                return;
                            }
                            return;
                        }
                        throw new MatchError(consumerEvent);
                    }

                    public static final /* synthetic */ void $anonfun$preStart$3($anon$4$$anon$5 $this, ConsumerEvent cmd) {
                        $this.onCommand(cmd);
                    }

                    public static final /* synthetic */ void $anonfun$preStart$4($anon$4$$anon$5 $this, Try x0$1) {
                        Try try_ = x0$1;
                        if (try_ instanceof Success) {
                            AsyncCallback callback;
                            Success success = (Success)try_;
                            $this.hubCallback = callback = (AsyncCallback)success.value();
                            if ($this.isAvailable($this.$outer.out()) && $this.offsetInitialized) {
                                $this.onPull();
                            }
                            callback.invoke($this.$outer.akka$stream$scaladsl$BroadcastHub$$anon$$$outer().akka$stream$scaladsl$BroadcastHub$$RegistrationPending());
                            return;
                        }
                        if (try_ instanceof Failure) {
                            Failure failure = (Failure)try_;
                            Throwable ex = failure.exception();
                            $this.failStage(ex);
                            return;
                        }
                        throw new MatchError(try_);
                    }

                    public static final /* synthetic */ void $anonfun$preStart$5(AsyncCallback eta$0$1$1, Try t2) {
                        eta$0$1$1.invoke(t2);
                    }

                    private final void register$1(AsyncCallback callback$1, Function1 onHubReady$1) {
                        block4: {
                            HubState hubState;
                            block5: {
                                List<Consumer> newRegistrations;
                                Future<AsyncCallback<HubEvent>> callbackFuture;
                                Open open;
                                do {
                                    Option<Throwable> option;
                                    boolean bl = false;
                                    Closed closed = null;
                                    hubState = this.$outer.logic$2.state().get();
                                    if (hubState instanceof Closed) {
                                        bl = true;
                                        closed = (Closed)hubState;
                                        Option<Throwable> option2 = closed.failure();
                                        if (option2 instanceof Some) {
                                            Some some = (Some)option2;
                                            Throwable ex = (Throwable)some.value();
                                            this.failStage(ex);
                                            break block4;
                                        }
                                    }
                                    if (bl && None$.MODULE$.equals(option = closed.failure())) {
                                        this.completeStage();
                                        break block4;
                                    }
                                    if (!(hubState instanceof Open)) break block5;
                                    open = (Open)hubState;
                                    callbackFuture = open.callbackFuture();
                                    List<Consumer> registrations = open.registrations();
                                    Consumer consumer = new Consumer(this.$outer.akka$stream$scaladsl$BroadcastHub$$anon$$$outer(), this.id, callback$1);
                                    newRegistrations = registrations.$colon$colon(consumer);
                                } while (!this.$outer.logic$2.state().compareAndSet(open, new Open(this.$outer.akka$stream$scaladsl$BroadcastHub$$anon$$$outer(), callbackFuture, newRegistrations)));
                                AsyncCallback<T> asyncCallback = this.getAsyncCallback(onHubReady$1);
                                callbackFuture.onComplete((Function1<Try, Object> & Serializable)t2 -> {
                                    $anon$4$$anon$5.$anonfun$preStart$5(asyncCallback, t2);
                                    return BoxedUnit.UNIT;
                                }, this.materializer().executionContext());
                                break block4;
                            }
                            throw new MatchError(hubState);
                        }
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        super($outer.shape());
                        OutHandler.$init$(this);
                        this.untilNextAdvanceSignal = $outer.akka$stream$scaladsl$BroadcastHub$$anon$$$outer().akka$stream$scaladsl$BroadcastHub$$DemandThreshold;
                        this.id = $outer.idCounter$2.getAndIncrement();
                        this.offsetInitialized = false;
                        this.previousPublishedOffset = 0;
                        this.offset = 0;
                        this.setHandler($outer.out(), this);
                    }

                    private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                        return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$preStart$3$adapted(akka.stream.scaladsl.BroadcastHub$$anon$4$$anon$5 akka.stream.scaladsl.BroadcastHub$ConsumerEvent ), $anonfun$preStart$4$adapted(akka.stream.scaladsl.BroadcastHub$$anon$4$$anon$5 scala.util.Try ), $anonfun$preStart$5$adapted(akka.stream.stage.AsyncCallback scala.util.Try )}, serializedLambda);
                    }
                };
            }

            public /* synthetic */ BroadcastHub akka$stream$scaladsl$BroadcastHub$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.idCounter$2 = idCounter$2;
                this.logic$2 = logic$2;
                this.out = Outlet$.MODULE$.apply("BroadcastHub.out");
                this.shape = new SourceShape<T>(this.out());
            }
        };
        return new Tuple2<GraphStageLogic, Source<T, NotUsed>>(logic, Source$.MODULE$.fromGraph(source2));
    }

    private final void RegistrationPending$lzycompute$1() {
        BroadcastHub broadcastHub = this;
        synchronized (broadcastHub) {
            if (this.RegistrationPending$module == null) {
                this.RegistrationPending$module = new BroadcastHub$RegistrationPending$(this);
            }
        }
    }

    private final void UnRegister$lzycompute$1() {
        BroadcastHub broadcastHub = this;
        synchronized (broadcastHub) {
            if (this.UnRegister$module == null) {
                this.UnRegister$module = new BroadcastHub$UnRegister$(this);
            }
        }
    }

    private final void Advance$lzycompute$1() {
        BroadcastHub broadcastHub = this;
        synchronized (broadcastHub) {
            if (this.Advance$module == null) {
                this.Advance$module = new BroadcastHub$Advance$(this);
            }
        }
    }

    private final void NeedWakeup$lzycompute$1() {
        BroadcastHub broadcastHub = this;
        synchronized (broadcastHub) {
            if (this.NeedWakeup$module == null) {
                this.NeedWakeup$module = new BroadcastHub$NeedWakeup$(this);
            }
        }
    }

    private final void Consumer$lzycompute$1() {
        BroadcastHub broadcastHub = this;
        synchronized (broadcastHub) {
            if (this.Consumer$module == null) {
                this.Consumer$module = new BroadcastHub$Consumer$(this);
            }
        }
    }

    private final void Completed$lzycompute$1() {
        BroadcastHub broadcastHub = this;
        synchronized (broadcastHub) {
            if (this.Completed$module == null) {
                this.Completed$module = new BroadcastHub$Completed$(this);
            }
        }
    }

    private final void Open$lzycompute$1() {
        BroadcastHub broadcastHub = this;
        synchronized (broadcastHub) {
            if (this.Open$module == null) {
                this.Open$module = new BroadcastHub$Open$(this);
            }
        }
    }

    private final void Closed$lzycompute$1() {
        BroadcastHub broadcastHub = this;
        synchronized (broadcastHub) {
            if (this.Closed$module == null) {
                this.Closed$module = new BroadcastHub$Closed$(this);
            }
        }
    }

    private final void Wakeup$lzycompute$1() {
        BroadcastHub broadcastHub = this;
        synchronized (broadcastHub) {
            if (this.Wakeup$module == null) {
                this.Wakeup$module = new BroadcastHub$Wakeup$(this);
            }
        }
    }

    private final void HubCompleted$lzycompute$1() {
        BroadcastHub broadcastHub = this;
        synchronized (broadcastHub) {
            if (this.HubCompleted$module == null) {
                this.HubCompleted$module = new BroadcastHub$HubCompleted$(this);
            }
        }
    }

    private final void Initialize$lzycompute$1() {
        BroadcastHub broadcastHub = this;
        synchronized (broadcastHub) {
            if (this.Initialize$module == null) {
                this.Initialize$module = new BroadcastHub$Initialize$(this);
            }
        }
    }

    public BroadcastHub(int bufferSize) {
        this.akka$stream$scaladsl$BroadcastHub$$bufferSize = bufferSize;
        Predef$.MODULE$.require(bufferSize > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "Buffer size must be positive");
        Predef$.MODULE$.require(bufferSize < 4096, (Function0<Object>)(Function0<String> & Serializable)() -> "Buffer size larger then 4095 is not allowed");
        Predef$.MODULE$.require((bufferSize & bufferSize - 1) == 0, (Function0<Object>)(Function0<String> & Serializable)() -> "Buffer size must be a power of two");
        this.akka$stream$scaladsl$BroadcastHub$$Mask = bufferSize - 1;
        this.akka$stream$scaladsl$BroadcastHub$$WheelMask = bufferSize * 2 - 1;
        this.in = Inlet$.MODULE$.apply("BroadcastHub.in");
        this.shape = new SinkShape<T>(this.in());
        this.akka$stream$scaladsl$BroadcastHub$$DemandThreshold = bufferSize / 2 + bufferSize % 2;
    }

    private class Advance
    implements HubEvent,
    Product,
    Serializable {
        private final long id;
        private final int previousOffset;
        public final /* synthetic */ BroadcastHub $outer;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public long id() {
            return this.id;
        }

        public int previousOffset() {
            return this.previousOffset;
        }

        public Advance copy(long id, int previousOffset) {
            return new Advance(this.akka$stream$scaladsl$BroadcastHub$Advance$$$outer(), id, previousOffset);
        }

        public long copy$default$1() {
            return this.id();
        }

        public int copy$default$2() {
            return this.previousOffset();
        }

        @Override
        public String productPrefix() {
            return "Advance";
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
                    return BoxesRunTime.boxToLong(this.id());
                }
                case 1: {
                    return BoxesRunTime.boxToInteger(this.previousOffset());
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
            return x$1 instanceof Advance;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "id";
                }
                case 1: {
                    return "previousOffset";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.longHash(this.id()));
            n = Statics.mix(n, this.previousOffset());
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
            if (!(object instanceof Advance)) return false;
            if (((Advance)object).akka$stream$scaladsl$BroadcastHub$Advance$$$outer() != this.akka$stream$scaladsl$BroadcastHub$Advance$$$outer()) return false;
            boolean bl = true;
            if (!bl) return false;
            Advance advance = (Advance)x$1;
            if (this.id() != advance.id()) return false;
            if (this.previousOffset() != advance.previousOffset()) return false;
            if (!advance.canEqual(this)) return false;
            return true;
        }

        public /* synthetic */ BroadcastHub akka$stream$scaladsl$BroadcastHub$Advance$$$outer() {
            return this.$outer;
        }

        public Advance(BroadcastHub $outer, long id, int previousOffset) {
            this.id = id;
            this.previousOffset = previousOffset;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            Product.$init$(this);
        }
    }

    private class BroadcastSinkLogic
    extends GraphStageLogic
    implements InHandler {
        private final Promise<AsyncCallback<HubEvent>> callbackPromise;
        private final Open noRegistrationsState;
        private final AtomicReference<HubState> state;
        private volatile int tail;
        private int head;
        private final Object[] queue;
        private final List<Consumer>[] consumerWheel;
        private int activeConsumers;

        public AtomicReference<HubState> state() {
            return this.state;
        }

        @Override
        public void preStart() {
            this.setKeepGoing(true);
            this.callbackPromise.success(this.getAsyncCallback((Function1<HubEvent, Object> & Serializable)ev -> {
                this.onEvent(ev);
                return BoxedUnit.UNIT;
            }));
            this.pull(this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().in());
        }

        @Override
        public void onUpstreamFinish() {
            if (!this.isFull()) {
                this.complete();
                return;
            }
        }

        @Override
        public void onPush() {
            this.publish(this.grab(this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().in()));
            if (!this.isFull()) {
                this.pull(this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().in());
                return;
            }
        }

        private void onEvent(HubEvent ev) {
            HubEvent hubEvent = ev;
            if (this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().akka$stream$scaladsl$BroadcastHub$$RegistrationPending().equals(hubEvent)) {
                ((Open)this.state().getAndSet(this.noRegistrationsState)).registrations().foreach((Function1<Consumer, Object> & Serializable)consumer -> {
                    BroadcastSinkLogic.$anonfun$onEvent$1(this, consumer);
                    return BoxedUnit.UNIT;
                });
                return;
            }
            if (hubEvent instanceof UnRegister) {
                UnRegister unRegister = (UnRegister)hubEvent;
                long id = unRegister.id();
                int previousOffset = unRegister.previousOffset();
                int finalOffset = unRegister.finalOffset();
                if (this.findAndRemoveConsumer(id, previousOffset) != null) {
                    --this.activeConsumers;
                }
                if (this.activeConsumers == 0) {
                    if (this.isClosed(this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().in())) {
                        this.completeStage();
                        return;
                    }
                    if (this.head != finalOffset) {
                        for (int n = 0; this.head != finalOffset && n < this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().akka$stream$scaladsl$BroadcastHub$$bufferSize; ++n) {
                            this.queue[this.head & this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().akka$stream$scaladsl$BroadcastHub$$Mask()] = null;
                            ++this.head;
                        }
                        this.head = finalOffset;
                        if (!this.hasBeenPulled(this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().in())) {
                            this.pull(this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().in());
                            return;
                        }
                        return;
                    }
                    return;
                }
                this.checkUnblock(previousOffset);
                return;
            }
            if (hubEvent instanceof Advance) {
                Advance advance = (Advance)hubEvent;
                long id = advance.id();
                int previousOffset = advance.previousOffset();
                int newOffset = previousOffset + this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().akka$stream$scaladsl$BroadcastHub$$DemandThreshold;
                Consumer consumer2 = this.findAndRemoveConsumer(id, previousOffset);
                this.addConsumer(consumer2, newOffset);
                this.checkUnblock(previousOffset);
                return;
            }
            if (hubEvent instanceof NeedWakeup) {
                NeedWakeup needWakeup = (NeedWakeup)hubEvent;
                long id = needWakeup.id();
                int previousOffset = needWakeup.previousOffset();
                int currentOffset = needWakeup.currentOffset();
                Consumer consumer3 = this.findAndRemoveConsumer(id, previousOffset);
                this.addConsumer(consumer3, currentOffset);
                if (currentOffset != this.tail) {
                    consumer3.callback().invoke(this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().akka$stream$scaladsl$BroadcastHub$$Wakeup());
                }
                this.checkUnblock(previousOffset);
                return;
            }
            throw new MatchError(hubEvent);
        }

        private boolean isFull() {
            return this.tail - this.head == this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().akka$stream$scaladsl$BroadcastHub$$bufferSize;
        }

        @Override
        public void onUpstreamFailure(Throwable ex) {
            HubCompleted failMessage = new HubCompleted(this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer(), new Some<Throwable>(ex));
            ((Open)this.state().getAndSet(new Closed(this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer(), new Some<Throwable>(ex)))).registrations().foreach((Function1<Consumer, Object> & Serializable)consumer -> {
                BroadcastSinkLogic.$anonfun$onUpstreamFailure$1(failMessage, consumer);
                return BoxedUnit.UNIT;
            });
            ArrayOps$.MODULE$.iterator$extension(Predef$.MODULE$.refArrayOps(this.consumerWheel)).flatMap((Function1<List, Iterator> & Serializable)x$4 -> x$4.iterator()).foreach((Function1<Consumer, Object> & Serializable)consumer -> {
                BroadcastSinkLogic.$anonfun$onUpstreamFailure$3(failMessage, consumer);
                return BoxedUnit.UNIT;
            });
            this.failStage(ex);
        }

        private Consumer findAndRemoveConsumer(long id, int offset) {
            int wheelSlot = offset & this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().akka$stream$scaladsl$BroadcastHub$$WheelMask();
            List consumersInSlot = this.consumerWheel[wheelSlot];
            List remainingConsumersInSlot = Nil$.MODULE$;
            Consumer removedConsumer = null;
            while (consumersInSlot.nonEmpty()) {
                Consumer consumer = (Consumer)consumersInSlot.head();
                if (consumer.id() != id) {
                    Consumer consumer2 = consumer;
                    remainingConsumersInSlot = remainingConsumersInSlot.$colon$colon(consumer2);
                } else {
                    removedConsumer = consumer;
                }
                consumersInSlot = (List)consumersInSlot.tail();
            }
            this.consumerWheel[wheelSlot] = remainingConsumersInSlot;
            return removedConsumer;
        }

        private void checkUnblock(int offsetOfConsumerRemoved) {
            if (this.unblockIfPossible(offsetOfConsumerRemoved)) {
                if (this.isClosed(this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().in())) {
                    this.complete();
                    return;
                }
                if (!this.hasBeenPulled(this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().in())) {
                    this.pull(this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().in());
                    return;
                }
                return;
            }
        }

        private boolean unblockIfPossible(int offsetOfConsumerRemoved) {
            boolean unblocked = false;
            if (offsetOfConsumerRemoved == this.head) {
                while (this.consumerWheel[this.head & this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().akka$stream$scaladsl$BroadcastHub$$WheelMask()].isEmpty() && this.head != this.tail) {
                    this.queue[this.head & this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().akka$stream$scaladsl$BroadcastHub$$Mask()] = null;
                    ++this.head;
                    unblocked = true;
                }
            }
            return unblocked;
        }

        private void addConsumer(Consumer consumer, int offset) {
            int slot = offset & this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().akka$stream$scaladsl$BroadcastHub$$WheelMask();
            Consumer consumer2 = consumer;
            this.consumerWheel[slot] = this.consumerWheel[slot].$colon$colon(consumer2);
        }

        private void wakeupIdx(int idx) {
            Iterator<Consumer> itr = this.consumerWheel[idx].iterator();
            while (itr.hasNext()) {
                itr.next().callback().invoke(this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().akka$stream$scaladsl$BroadcastHub$$Wakeup());
            }
        }

        private void complete() {
            int idx = this.tail & this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().akka$stream$scaladsl$BroadcastHub$$Mask();
            int wheelSlot = this.tail & this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().akka$stream$scaladsl$BroadcastHub$$WheelMask();
            this.queue[idx] = this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().akka$stream$scaladsl$BroadcastHub$$Completed();
            this.wakeupIdx(wheelSlot);
            ++this.tail;
            if (this.activeConsumers == 0) {
                this.completeStage();
                return;
            }
        }

        @Override
        public void postStop() {
            this.tryClose$1();
        }

        private void publish(T elem) {
            int idx = this.tail & this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().akka$stream$scaladsl$BroadcastHub$$Mask();
            int wheelSlot = this.tail & this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().akka$stream$scaladsl$BroadcastHub$$WheelMask();
            this.queue[idx] = elem;
            ++this.tail;
            this.wakeupIdx(wheelSlot);
        }

        public Object poll(int offset) {
            if (offset == this.tail) {
                return null;
            }
            return this.queue[offset & this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer().akka$stream$scaladsl$BroadcastHub$$Mask()];
        }

        public /* synthetic */ BroadcastHub akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer() {
            return BroadcastHub.this;
        }

        public static final /* synthetic */ void $anonfun$onEvent$2(BroadcastSinkLogic $this, Consumer consumer$1, int startFrom$1, ExecutionContextExecutor ec$1, Throwable x0$1) {
            Throwable throwable = x0$1;
            if (throwable instanceof StreamDetachedException) {
                $this.callbackPromise.future().foreach((Function1<AsyncCallback, Object> & Serializable)callback -> {
                    callback.invoke(new UnRegister($this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer(), consumer$1.id(), startFrom$1, startFrom$1));
                    return BoxedUnit.UNIT;
                }, ec$1);
                return;
            }
        }

        public static final /* synthetic */ void $anonfun$onEvent$1(BroadcastSinkLogic $this, Consumer consumer) {
            int startFrom = $this.head;
            ++$this.activeConsumers;
            $this.addConsumer(consumer, startFrom);
            ExecutionContextExecutor ec = $this.materializer().executionContext();
            consumer.callback().invokeWithFeedback(new Initialize($this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer(), startFrom)).failed().foreach((Function1<Throwable, Object> & Serializable)x0$1 -> {
                BroadcastSinkLogic.$anonfun$onEvent$2($this, consumer, startFrom, ec, x0$1);
                return BoxedUnit.UNIT;
            }, ec);
        }

        public static final /* synthetic */ void $anonfun$onUpstreamFailure$1(HubCompleted failMessage$1, Consumer consumer) {
            consumer.callback().invoke(failMessage$1);
        }

        public static final /* synthetic */ void $anonfun$onUpstreamFailure$3(HubCompleted failMessage$1, Consumer consumer) {
            consumer.callback().invoke(failMessage$1);
        }

        public static final /* synthetic */ void $anonfun$postStop$1(HubCompleted completedMessage$1, Consumer consumer) {
            consumer.callback().invoke(completedMessage$1);
        }

        private final void tryClose$1() {
            block2: {
                HubState hubState;
                block3: {
                    Open open;
                    do {
                        if ((hubState = this.state().get()) instanceof Closed) {
                            break block2;
                        }
                        if (!(hubState instanceof Open)) break block3;
                        open = (Open)hubState;
                    } while (!this.state().compareAndSet(open, new Closed(this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer(), None$.MODULE$)));
                    HubCompleted completedMessage = new HubCompleted(this.akka$stream$scaladsl$BroadcastHub$BroadcastSinkLogic$$$outer(), None$.MODULE$);
                    open.registrations().foreach((Function1<Consumer, Object> & Serializable)consumer -> {
                        BroadcastSinkLogic.$anonfun$postStop$1(completedMessage, consumer);
                        return BoxedUnit.UNIT;
                    });
                    break block2;
                }
                throw new MatchError(hubState);
            }
        }

        public BroadcastSinkLogic(SinkShape<T> _shape) {
            if (BroadcastHub.this == null) {
                throw null;
            }
            super(_shape);
            InHandler.$init$(this);
            this.callbackPromise = Promise$.MODULE$.apply();
            this.noRegistrationsState = new Open(BroadcastHub.this, this.callbackPromise.future(), Nil$.MODULE$);
            this.state = new AtomicReference<Open>(this.noRegistrationsState);
            this.tail = Integer.MAX_VALUE;
            this.head = Integer.MAX_VALUE;
            this.queue = new Object[BroadcastHub.this.akka$stream$scaladsl$BroadcastHub$$bufferSize];
            this.consumerWheel = (List[])Array$.MODULE$.fill(BroadcastHub.this.akka$stream$scaladsl$BroadcastHub$$bufferSize * 2, (Function0<Nil$> & Serializable)() -> Nil$.MODULE$, ClassTag$.MODULE$.apply(List.class));
            this.activeConsumers = 0;
            this.setHandler(BroadcastHub.this.in(), this);
        }
    }

    private class Closed
    implements HubState,
    Product,
    Serializable {
        private final Option<Throwable> failure;
        public final /* synthetic */ BroadcastHub $outer;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Option<Throwable> failure() {
            return this.failure;
        }

        public Closed copy(Option<Throwable> failure) {
            return new Closed(this.akka$stream$scaladsl$BroadcastHub$Closed$$$outer(), failure);
        }

        public Option<Throwable> copy$default$1() {
            return this.failure();
        }

        @Override
        public String productPrefix() {
            return "Closed";
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
                    return this.failure();
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
            return x$1 instanceof Closed;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "failure";
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
            if (!(object instanceof Closed)) return false;
            if (((Closed)object).akka$stream$scaladsl$BroadcastHub$Closed$$$outer() != this.akka$stream$scaladsl$BroadcastHub$Closed$$$outer()) return false;
            boolean bl = true;
            if (!bl) return false;
            Closed closed = (Closed)x$1;
            Option<Throwable> option = this.failure();
            Option<Throwable> option2 = closed.failure();
            if (option == null) {
                if (option2 != null) {
                    return false;
                }
            } else if (!option.equals(option2)) return false;
            if (!closed.canEqual(this)) return false;
            return true;
        }

        public /* synthetic */ BroadcastHub akka$stream$scaladsl$BroadcastHub$Closed$$$outer() {
            return this.$outer;
        }

        public Closed(BroadcastHub $outer, Option<Throwable> failure) {
            this.failure = failure;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            Product.$init$(this);
        }
    }

    private class Consumer
    implements Product,
    Serializable {
        private final long id;
        private final AsyncCallback<ConsumerEvent> callback;
        public final /* synthetic */ BroadcastHub $outer;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public long id() {
            return this.id;
        }

        public AsyncCallback<ConsumerEvent> callback() {
            return this.callback;
        }

        public Consumer copy(long id, AsyncCallback<ConsumerEvent> callback) {
            return new Consumer(this.akka$stream$scaladsl$BroadcastHub$Consumer$$$outer(), id, callback);
        }

        public long copy$default$1() {
            return this.id();
        }

        public AsyncCallback<ConsumerEvent> copy$default$2() {
            return this.callback();
        }

        @Override
        public String productPrefix() {
            return "Consumer";
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
                    return BoxesRunTime.boxToLong(this.id());
                }
                case 1: {
                    return this.callback();
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
            return x$1 instanceof Consumer;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "id";
                }
                case 1: {
                    return "callback";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.longHash(this.id()));
            n = Statics.mix(n, Statics.anyHash(this.callback()));
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
            if (!(object instanceof Consumer)) return false;
            if (((Consumer)object).akka$stream$scaladsl$BroadcastHub$Consumer$$$outer() != this.akka$stream$scaladsl$BroadcastHub$Consumer$$$outer()) return false;
            boolean bl = true;
            if (!bl) return false;
            Consumer consumer = (Consumer)x$1;
            if (this.id() != consumer.id()) return false;
            AsyncCallback<ConsumerEvent> asyncCallback = this.callback();
            AsyncCallback<ConsumerEvent> asyncCallback2 = consumer.callback();
            if (asyncCallback == null) {
                if (asyncCallback2 != null) {
                    return false;
                }
            } else if (!asyncCallback.equals(asyncCallback2)) return false;
            if (!consumer.canEqual(this)) return false;
            return true;
        }

        public /* synthetic */ BroadcastHub akka$stream$scaladsl$BroadcastHub$Consumer$$$outer() {
            return this.$outer;
        }

        public Consumer(BroadcastHub $outer, long id, AsyncCallback<ConsumerEvent> callback) {
            this.id = id;
            this.callback = callback;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            Product.$init$(this);
        }
    }

    private interface ConsumerEvent {
    }

    private class HubCompleted
    implements ConsumerEvent,
    Product,
    Serializable {
        private final Option<Throwable> failure;
        public final /* synthetic */ BroadcastHub $outer;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Option<Throwable> failure() {
            return this.failure;
        }

        public HubCompleted copy(Option<Throwable> failure) {
            return new HubCompleted(this.akka$stream$scaladsl$BroadcastHub$HubCompleted$$$outer(), failure);
        }

        public Option<Throwable> copy$default$1() {
            return this.failure();
        }

        @Override
        public String productPrefix() {
            return "HubCompleted";
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
                    return this.failure();
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
            return x$1 instanceof HubCompleted;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "failure";
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
            if (!(object instanceof HubCompleted)) return false;
            if (((HubCompleted)object).akka$stream$scaladsl$BroadcastHub$HubCompleted$$$outer() != this.akka$stream$scaladsl$BroadcastHub$HubCompleted$$$outer()) return false;
            boolean bl = true;
            if (!bl) return false;
            HubCompleted hubCompleted = (HubCompleted)x$1;
            Option<Throwable> option = this.failure();
            Option<Throwable> option2 = hubCompleted.failure();
            if (option == null) {
                if (option2 != null) {
                    return false;
                }
            } else if (!option.equals(option2)) return false;
            if (!hubCompleted.canEqual(this)) return false;
            return true;
        }

        public /* synthetic */ BroadcastHub akka$stream$scaladsl$BroadcastHub$HubCompleted$$$outer() {
            return this.$outer;
        }

        public HubCompleted(BroadcastHub $outer, Option<Throwable> failure) {
            this.failure = failure;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            Product.$init$(this);
        }
    }

    private interface HubEvent {
    }

    private interface HubState {
    }

    private class Initialize
    implements ConsumerEvent,
    Product,
    Serializable {
        private final int offset;
        public final /* synthetic */ BroadcastHub $outer;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public int offset() {
            return this.offset;
        }

        public Initialize copy(int offset) {
            return new Initialize(this.akka$stream$scaladsl$BroadcastHub$Initialize$$$outer(), offset);
        }

        public int copy$default$1() {
            return this.offset();
        }

        @Override
        public String productPrefix() {
            return "Initialize";
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
                    return BoxesRunTime.boxToInteger(this.offset());
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
            return x$1 instanceof Initialize;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "offset";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.offset());
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
            if (!(object instanceof Initialize)) return false;
            if (((Initialize)object).akka$stream$scaladsl$BroadcastHub$Initialize$$$outer() != this.akka$stream$scaladsl$BroadcastHub$Initialize$$$outer()) return false;
            boolean bl = true;
            if (!bl) return false;
            Initialize initialize = (Initialize)x$1;
            if (this.offset() != initialize.offset()) return false;
            if (!initialize.canEqual(this)) return false;
            return true;
        }

        public /* synthetic */ BroadcastHub akka$stream$scaladsl$BroadcastHub$Initialize$$$outer() {
            return this.$outer;
        }

        public Initialize(BroadcastHub $outer, int offset) {
            this.offset = offset;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            Product.$init$(this);
        }
    }

    private class NeedWakeup
    implements HubEvent,
    Product,
    Serializable {
        private final long id;
        private final int previousOffset;
        private final int currentOffset;
        public final /* synthetic */ BroadcastHub $outer;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public long id() {
            return this.id;
        }

        public int previousOffset() {
            return this.previousOffset;
        }

        public int currentOffset() {
            return this.currentOffset;
        }

        public NeedWakeup copy(long id, int previousOffset, int currentOffset) {
            return new NeedWakeup(this.akka$stream$scaladsl$BroadcastHub$NeedWakeup$$$outer(), id, previousOffset, currentOffset);
        }

        public long copy$default$1() {
            return this.id();
        }

        public int copy$default$2() {
            return this.previousOffset();
        }

        public int copy$default$3() {
            return this.currentOffset();
        }

        @Override
        public String productPrefix() {
            return "NeedWakeup";
        }

        @Override
        public int productArity() {
            return 3;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return BoxesRunTime.boxToLong(this.id());
                }
                case 1: {
                    return BoxesRunTime.boxToInteger(this.previousOffset());
                }
                case 2: {
                    return BoxesRunTime.boxToInteger(this.currentOffset());
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
            return x$1 instanceof NeedWakeup;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "id";
                }
                case 1: {
                    return "previousOffset";
                }
                case 2: {
                    return "currentOffset";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.longHash(this.id()));
            n = Statics.mix(n, this.previousOffset());
            n = Statics.mix(n, this.currentOffset());
            return Statics.finalizeHash(n, 3);
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
            if (!(object instanceof NeedWakeup)) return false;
            if (((NeedWakeup)object).akka$stream$scaladsl$BroadcastHub$NeedWakeup$$$outer() != this.akka$stream$scaladsl$BroadcastHub$NeedWakeup$$$outer()) return false;
            boolean bl = true;
            if (!bl) return false;
            NeedWakeup needWakeup = (NeedWakeup)x$1;
            if (this.id() != needWakeup.id()) return false;
            if (this.previousOffset() != needWakeup.previousOffset()) return false;
            if (this.currentOffset() != needWakeup.currentOffset()) return false;
            if (!needWakeup.canEqual(this)) return false;
            return true;
        }

        public /* synthetic */ BroadcastHub akka$stream$scaladsl$BroadcastHub$NeedWakeup$$$outer() {
            return this.$outer;
        }

        public NeedWakeup(BroadcastHub $outer, long id, int previousOffset, int currentOffset) {
            this.id = id;
            this.previousOffset = previousOffset;
            this.currentOffset = currentOffset;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            Product.$init$(this);
        }
    }

    private class Open
    implements HubState,
    Product,
    Serializable {
        private final Future<AsyncCallback<HubEvent>> callbackFuture;
        private final List<Consumer> registrations;
        public final /* synthetic */ BroadcastHub $outer;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Future<AsyncCallback<HubEvent>> callbackFuture() {
            return this.callbackFuture;
        }

        public List<Consumer> registrations() {
            return this.registrations;
        }

        public Open copy(Future<AsyncCallback<HubEvent>> callbackFuture, List<Consumer> registrations) {
            return new Open(this.akka$stream$scaladsl$BroadcastHub$Open$$$outer(), callbackFuture, registrations);
        }

        public Future<AsyncCallback<HubEvent>> copy$default$1() {
            return this.callbackFuture();
        }

        public List<Consumer> copy$default$2() {
            return this.registrations();
        }

        @Override
        public String productPrefix() {
            return "Open";
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
                    return this.callbackFuture();
                }
                case 1: {
                    return this.registrations();
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
            return x$1 instanceof Open;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "callbackFuture";
                }
                case 1: {
                    return "registrations";
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
            if (!(object instanceof Open)) return false;
            if (((Open)object).akka$stream$scaladsl$BroadcastHub$Open$$$outer() != this.akka$stream$scaladsl$BroadcastHub$Open$$$outer()) return false;
            boolean bl = true;
            if (!bl) return false;
            Open open = (Open)x$1;
            Future<AsyncCallback<HubEvent>> future = this.callbackFuture();
            Future<AsyncCallback<HubEvent>> future2 = open.callbackFuture();
            if (future == null) {
                if (future2 != null) {
                    return false;
                }
            } else if (!future.equals(future2)) return false;
            List<Consumer> list = this.registrations();
            List<Consumer> list2 = open.registrations();
            if (list == null) {
                if (list2 != null) {
                    return false;
                }
            } else if (!((Object)list).equals(list2)) return false;
            if (!open.canEqual(this)) return false;
            return true;
        }

        public /* synthetic */ BroadcastHub akka$stream$scaladsl$BroadcastHub$Open$$$outer() {
            return this.$outer;
        }

        public Open(BroadcastHub $outer, Future<AsyncCallback<HubEvent>> callbackFuture, List<Consumer> registrations) {
            this.callbackFuture = callbackFuture;
            this.registrations = registrations;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            Product.$init$(this);
        }
    }

    private class UnRegister
    implements HubEvent,
    Product,
    Serializable {
        private final long id;
        private final int previousOffset;
        private final int finalOffset;
        public final /* synthetic */ BroadcastHub $outer;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public long id() {
            return this.id;
        }

        public int previousOffset() {
            return this.previousOffset;
        }

        public int finalOffset() {
            return this.finalOffset;
        }

        public UnRegister copy(long id, int previousOffset, int finalOffset) {
            return new UnRegister(this.akka$stream$scaladsl$BroadcastHub$UnRegister$$$outer(), id, previousOffset, finalOffset);
        }

        public long copy$default$1() {
            return this.id();
        }

        public int copy$default$2() {
            return this.previousOffset();
        }

        public int copy$default$3() {
            return this.finalOffset();
        }

        @Override
        public String productPrefix() {
            return "UnRegister";
        }

        @Override
        public int productArity() {
            return 3;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return BoxesRunTime.boxToLong(this.id());
                }
                case 1: {
                    return BoxesRunTime.boxToInteger(this.previousOffset());
                }
                case 2: {
                    return BoxesRunTime.boxToInteger(this.finalOffset());
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
            return x$1 instanceof UnRegister;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "id";
                }
                case 1: {
                    return "previousOffset";
                }
                case 2: {
                    return "finalOffset";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.longHash(this.id()));
            n = Statics.mix(n, this.previousOffset());
            n = Statics.mix(n, this.finalOffset());
            return Statics.finalizeHash(n, 3);
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
            if (!(object instanceof UnRegister)) return false;
            if (((UnRegister)object).akka$stream$scaladsl$BroadcastHub$UnRegister$$$outer() != this.akka$stream$scaladsl$BroadcastHub$UnRegister$$$outer()) return false;
            boolean bl = true;
            if (!bl) return false;
            UnRegister unRegister = (UnRegister)x$1;
            if (this.id() != unRegister.id()) return false;
            if (this.previousOffset() != unRegister.previousOffset()) return false;
            if (this.finalOffset() != unRegister.finalOffset()) return false;
            if (!unRegister.canEqual(this)) return false;
            return true;
        }

        public /* synthetic */ BroadcastHub akka$stream$scaladsl$BroadcastHub$UnRegister$$$outer() {
            return this.$outer;
        }

        public UnRegister(BroadcastHub $outer, long id, int previousOffset, int finalOffset) {
            this.id = id;
            this.previousOffset = previousOffset;
            this.finalOffset = finalOffset;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            Product.$init$(this);
        }
    }
}

