/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.annotation.DoNotInherit;
import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SinkShape;
import akka.stream.SourceShape;
import akka.stream.javadsl.PartitionHub;
import akka.stream.scaladsl.PartitionHub;
import akka.stream.scaladsl.PartitionHub$;
import akka.stream.scaladsl.PartitionHub$$anon$7$;
import akka.stream.scaladsl.PartitionHub$Internal$Completed$;
import akka.stream.scaladsl.PartitionHub$Internal$Initialize$;
import akka.stream.scaladsl.PartitionHub$Internal$RegistrationPending$;
import akka.stream.scaladsl.PartitionHub$Internal$TryPull$;
import akka.stream.scaladsl.PartitionHub$Internal$Wakeup$;
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
import java.util.AbstractList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.SeqOps;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Vector;
import scala.collection.mutable.LongMap;
import scala.collection.mutable.LongMap$;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.math.Ordering$Long$;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005!\u0005v\u0001\u0003BL\u00053C\tAa*\u0007\u0011\t-&\u0011\u0014E\u0001\u0005[CqAa/\u0002\t\u0003\u0011i\fC\u0006\u0003@\u0006\u0011\r\u0011\"\u0001\u0003\"\n\u0005\u0007\u0002\u0003Be\u0003\u0001\u0006IAa1\t\u000f\te\u0017\u0001\"\u0001\u0003\\\"I1QP\u0001\u0012\u0002\u0013\u00051q\u0010\u0005\b\u0007/\u000bA\u0011ABM\u0011%\u0019i+AI\u0001\n\u0003\u0019yKB\u0005\u0004\u001e\u0005\u0001\n1%\u0001\u0004 !91\u0011I\u0005\u0007\u0002\r\r\u0003bBB.\u0013\u0019\u00051Q\f\u0005\b\u0007GJa\u0011AB3\u0011\u001d\u0019Y'\u0003D\u0001\u0005\u0003<\u0011ba-\u0002\u0011\u0003\u0011\tk!.\u0007\u0013\r]\u0016\u0001#\u0001\u0003\"\u000ee\u0006b\u0002B^\u001f\u0011\u000511\u0018\u0004\n\u0007{{\u0001\u0013aI\u0011\u0007\u007f;q\u0001b\u001f\u0010\u0011\u0003#\tHB\u0004\u0005l=A\t\t\"\u001c\t\u000f\tm6\u0003\"\u0001\u0005p!IAqA\n\u0002\u0002\u0013\u0005C\u0011\u0002\u0005\n\t7\u0019\u0012\u0011!C\u0001\u0005\u0003D\u0011\u0002\"\b\u0014\u0003\u0003%\t\u0001b\u001d\t\u0013\u0011\u00152#!A\u0005B\u0011\u001d\u0002\"\u0003C\u0019'\u0005\u0005I\u0011\u0001C<\u0011%!\u0019eEA\u0001\n\u0003\")\u0005C\u0005\u0005HM\t\t\u0011\"\u0011\u0005J!IA\u0011M\n\u0002\u0002\u0013%A1\r\u0004\u0007\u0007\u0007|!i!2\t\u0015\r\rXD!f\u0001\n\u0003\u0019)\u000f\u0003\u0006\u0004tv\u0011\t\u0012)A\u0005\u0007ODqAa/\u001e\t\u0003\u0019)\u0010C\u0005\u0004|v\t\t\u0011\"\u0001\u0004~\"IA\u0011A\u000f\u0012\u0002\u0013\u0005A1\u0001\u0005\n\t\u000fi\u0012\u0011!C!\t\u0013A\u0011\u0002b\u0007\u001e\u0003\u0003%\tA!1\t\u0013\u0011uQ$!A\u0005\u0002\u0011}\u0001\"\u0003C\u0013;\u0005\u0005I\u0011\tC\u0014\u0011%!\t$HA\u0001\n\u0003!\u0019\u0004C\u0005\u0005>u\t\t\u0011\"\u0011\u0005@!IA1I\u000f\u0002\u0002\u0013\u0005CQ\t\u0005\n\t\u000fj\u0012\u0011!C!\t\u0013B\u0011\u0002b\u0013\u001e\u0003\u0003%\t\u0005\"\u0014\b\u0013\u0011ut\"!A\t\u0002\u0011}d!CBb\u001f\u0005\u0005\t\u0012\u0001CA\u0011\u001d\u0011Y,\fC\u0001\t3C\u0011\u0002b\u0012.\u0003\u0003%)\u0005\"\u0013\t\u0013\u0011mU&!A\u0005\u0002\u0012u\u0005\"\u0003CQ[\u0005\u0005I\u0011\u0011CR\u0011%!\t'LA\u0001\n\u0013!\u0019gB\u0004\u0005,>A\t\tb\u0016\u0007\u000f\u0011Es\u0002#!\u0005T!9!1\u0018\u001b\u0005\u0002\u0011U\u0003\"\u0003C\u0004i\u0005\u0005I\u0011\tC\u0005\u0011%!Y\u0002NA\u0001\n\u0003\u0011\t\rC\u0005\u0005\u001eQ\n\t\u0011\"\u0001\u0005Z!IAQ\u0005\u001b\u0002\u0002\u0013\u0005Cq\u0005\u0005\n\tc!\u0014\u0011!C\u0001\t;B\u0011\u0002b\u00115\u0003\u0003%\t\u0005\"\u0012\t\u0013\u0011\u001dC'!A\u0005B\u0011%\u0003\"\u0003C1i\u0005\u0005I\u0011\u0002C2\r%!ik\u0004I\u0001$C!ykB\u0004\u0006`=A\t)b\n\u0007\u000f\u0015\u0005r\u0002#!\u0006$!9!1\u0018!\u0005\u0002\u0015\u0015\u0002\"\u0003C\u0004\u0001\u0006\u0005I\u0011\tC\u0005\u0011%!Y\u0002QA\u0001\n\u0003\u0011\t\rC\u0005\u0005\u001e\u0001\u000b\t\u0011\"\u0001\u0006*!IAQ\u0005!\u0002\u0002\u0013\u0005Cq\u0005\u0005\n\tc\u0001\u0015\u0011!C\u0001\u000b[A\u0011\u0002b\u0011A\u0003\u0003%\t\u0005\"\u0012\t\u0013\u0011\u001d\u0003)!A\u0005B\u0011%\u0003\"\u0003C1\u0001\u0006\u0005I\u0011\u0002C2\r\u0019)\te\u0004\"\u0006D!QA1\u0019&\u0003\u0016\u0004%\t\u0001\"2\t\u0015\u0011\u001d'J!E!\u0002\u0013\u0019)\u0006C\u0004\u0003<*#\t!\"\u0012\t\u0013\rm(*!A\u0005\u0002\u0015-\u0003\"\u0003C\u0001\u0015F\u0005I\u0011\u0001Ct\u0011%!9ASA\u0001\n\u0003\"I\u0001C\u0005\u0005\u001c)\u000b\t\u0011\"\u0001\u0003B\"IAQ\u0004&\u0002\u0002\u0013\u0005Qq\n\u0005\n\tKQ\u0015\u0011!C!\tOA\u0011\u0002\"\rK\u0003\u0003%\t!b\u0015\t\u0013\u0011u\"*!A\u0005B\u0015]\u0003\"\u0003C\"\u0015\u0006\u0005I\u0011\tC#\u0011%!9ESA\u0001\n\u0003\"I\u0005C\u0005\u0005L)\u000b\t\u0011\"\u0011\u0006\\\u001dIQ\u0011M\b\u0002\u0002#\u0005Q1\r\u0004\n\u000b\u0003z\u0011\u0011!E\u0001\u000bKBqAa/[\t\u0003)I\u0007C\u0005\u0005Hi\u000b\t\u0011\"\u0012\u0005J!IA1\u0014.\u0002\u0002\u0013\u0005U1\u000e\u0005\n\tCS\u0016\u0011!CA\u000b_B\u0011\u0002\"\u0019[\u0003\u0003%I\u0001b\u0019\u0007\r\u0011MvB\u0011C[\u0011)!I\f\u0019BK\u0002\u0013\u0005A1\u0018\u0005\u000b\u000b\u0003\u0001'\u0011#Q\u0001\n\u0011u\u0006b\u0002B^A\u0012\u0005Q1\u0001\u0005\n\u0007w\u0004\u0017\u0011!C\u0001\u000b\u0013A\u0011\u0002\"\u0001a#\u0003%\t!\"\u0004\t\u0013\u0011\u001d\u0001-!A\u0005B\u0011%\u0001\"\u0003C\u000eA\u0006\u0005I\u0011\u0001Ba\u0011%!i\u0002YA\u0001\n\u0003)\t\u0002C\u0005\u0005&\u0001\f\t\u0011\"\u0011\u0005(!IA\u0011\u00071\u0002\u0002\u0013\u0005QQ\u0003\u0005\n\t{\u0001\u0017\u0011!C!\u000b3A\u0011\u0002b\u0011a\u0003\u0003%\t\u0005\"\u0012\t\u0013\u0011\u001d\u0003-!A\u0005B\u0011%\u0003\"\u0003C&A\u0006\u0005I\u0011IC\u000f\u000f%))hDA\u0001\u0012\u0003)9HB\u0005\u00054>\t\t\u0011#\u0001\u0006z!9!1\u00189\u0005\u0002\u0015u\u0004\"\u0003C$a\u0006\u0005IQ\tC%\u0011%!Y\n]A\u0001\n\u0003+y\bC\u0005\u0005\"B\f\t\u0011\"!\u0006\u0004\"IA\u0011\r9\u0002\u0002\u0013%A1\r\u0004\u0007\t\u007f{!\t\"1\t\u0015\u0011\rgO!f\u0001\n\u0003!)\r\u0003\u0006\u0005HZ\u0014\t\u0012)A\u0005\u0007+B!\u0002\"3w\u0005+\u0007I\u0011\u0001Cf\u0011)!IN\u001eB\tB\u0003%AQ\u001a\u0005\b\u0005w3H\u0011\u0001Cn\u0011%\u0019YP^A\u0001\n\u0003!\t\u000fC\u0005\u0005\u0002Y\f\n\u0011\"\u0001\u0005h\"IA1\u001e<\u0012\u0002\u0013\u0005AQ\u001e\u0005\n\t\u000f1\u0018\u0011!C!\t\u0013A\u0011\u0002b\u0007w\u0003\u0003%\tA!1\t\u0013\u0011ua/!A\u0005\u0002\u0011E\b\"\u0003C\u0013m\u0006\u0005I\u0011\tC\u0014\u0011%!\tD^A\u0001\n\u0003!)\u0010C\u0005\u0005>Y\f\t\u0011\"\u0011\u0005z\"IA1\t<\u0002\u0002\u0013\u0005CQ\t\u0005\n\t\u000f2\u0018\u0011!C!\t\u0013B\u0011\u0002b\u0013w\u0003\u0003%\t\u0005\"@\b\u0013\u0015%u\"!A\t\u0002\u0015-e!\u0003C`\u001f\u0005\u0005\t\u0012ACG\u0011!\u0011Y,a\u0005\u0005\u0002\u0015U\u0005B\u0003C$\u0003'\t\t\u0011\"\u0012\u0005J!QA1TA\n\u0003\u0003%\t)b&\t\u0015\u0011\u0005\u00161CA\u0001\n\u0003+i\n\u0003\u0006\u0005b\u0005M\u0011\u0011!C\u0005\tG:q!\"+\u0010\u0011\u0003+9DB\u0004\u00062=A\t)b\r\t\u0011\tm\u0016\u0011\u0005C\u0001\u000bkA!\u0002b\u0002\u0002\"\u0005\u0005I\u0011\tC\u0005\u0011)!Y\"!\t\u0002\u0002\u0013\u0005!\u0011\u0019\u0005\u000b\t;\t\t#!A\u0005\u0002\u0015e\u0002B\u0003C\u0013\u0003C\t\t\u0011\"\u0011\u0005(!QA\u0011GA\u0011\u0003\u0003%\t!\"\u0010\t\u0015\u0011\r\u0013\u0011EA\u0001\n\u0003\")\u0005\u0003\u0006\u0005H\u0005\u0005\u0012\u0011!C!\t\u0013B!\u0002\"\u0019\u0002\"\u0005\u0005I\u0011\u0002C2\u000f\u001d)Yk\u0004EA\u000b[3q!b,\u0010\u0011\u0003+\t\f\u0003\u0005\u0003<\u0006]B\u0011ACZ\u0011)!9!a\u000e\u0002\u0002\u0013\u0005C\u0011\u0002\u0005\u000b\t7\t9$!A\u0005\u0002\t\u0005\u0007B\u0003C\u000f\u0003o\t\t\u0011\"\u0001\u00066\"QAQEA\u001c\u0003\u0003%\t\u0005b\n\t\u0015\u0011E\u0012qGA\u0001\n\u0003)I\f\u0003\u0006\u0005D\u0005]\u0012\u0011!C!\t\u000bB!\u0002b\u0012\u00028\u0005\u0005I\u0011\tC%\u0011)!\t'a\u000e\u0002\u0002\u0013%A1\r\u0004\n\u000b{{\u0001\u0013aI\u0011\u000b\u007f3a!b9\u0010\u0005\u0016\u0015\bbCCt\u0003\u001b\u0012)\u001a!C\u0001\u000bSD1\"\"?\u0002N\tE\t\u0015!\u0003\u0006l\"YQ1`A'\u0005+\u0007I\u0011AC\u007f\u0011-1)!!\u0014\u0003\u0012\u0003\u0006I!b@\t\u0011\tm\u0016Q\nC\u0001\r\u000fA!ba?\u0002N\u0005\u0005I\u0011\u0001D\b\u0011)!\t!!\u0014\u0012\u0002\u0013\u0005aQ\u0003\u0005\u000b\tW\fi%%A\u0005\u0002\u0019e\u0001B\u0003C\u0004\u0003\u001b\n\t\u0011\"\u0011\u0005\n!QA1DA'\u0003\u0003%\tA!1\t\u0015\u0011u\u0011QJA\u0001\n\u00031i\u0002\u0003\u0006\u0005&\u00055\u0013\u0011!C!\tOA!\u0002\"\r\u0002N\u0005\u0005I\u0011\u0001D\u0011\u0011)!i$!\u0014\u0002\u0002\u0013\u0005cQ\u0005\u0005\u000b\t\u0007\ni%!A\u0005B\u0011\u0015\u0003B\u0003C$\u0003\u001b\n\t\u0011\"\u0011\u0005J!QA1JA'\u0003\u0003%\tE\"\u000b\b\u0013\u00195r\"!A\t\u0002\u0019=b!CCr\u001f\u0005\u0005\t\u0012\u0001D\u0019\u0011!\u0011Y,a\u001d\u0005\u0002\u0019U\u0002B\u0003C$\u0003g\n\t\u0011\"\u0012\u0005J!QA1TA:\u0003\u0003%\tIb\u000e\t\u0015\u0011\u0005\u00161OA\u0001\n\u00033i\u0004\u0003\u0006\u0005b\u0005M\u0014\u0011!C\u0005\tG2a!b1\u0010\u0005\u0016\u0015\u0007bCBr\u0003\u007f\u0012)\u001a!C\u0001\u0007KD1ba=\u0002\u0000\tE\t\u0015!\u0003\u0004h\"A!1XA@\t\u0003)I\r\u0003\u0006\u0004|\u0006}\u0014\u0011!C\u0001\u000b\u001fD!\u0002\"\u0001\u0002\u0000E\u0005I\u0011\u0001C\u0002\u0011)!9!a \u0002\u0002\u0013\u0005C\u0011\u0002\u0005\u000b\t7\ty(!A\u0005\u0002\t\u0005\u0007B\u0003C\u000f\u0003\u007f\n\t\u0011\"\u0001\u0006T\"QAQEA@\u0003\u0003%\t\u0005b\n\t\u0015\u0011E\u0012qPA\u0001\n\u0003)9\u000e\u0003\u0006\u0005>\u0005}\u0014\u0011!C!\u000b7D!\u0002b\u0011\u0002\u0000\u0005\u0005I\u0011\tC#\u0011)!9%a \u0002\u0002\u0013\u0005C\u0011\n\u0005\u000b\t\u0017\ny(!A\u0005B\u0015}w!\u0003D#\u001f\u0005\u0005\t\u0012\u0001D$\r%)\u0019mDA\u0001\u0012\u00031I\u0005\u0003\u0005\u0003<\u0006}E\u0011\u0001D'\u0011)!9%a(\u0002\u0002\u0013\u0015C\u0011\n\u0005\u000b\t7\u000by*!A\u0005\u0002\u001a=\u0003B\u0003CQ\u0003?\u000b\t\u0011\"!\u0007T!QA\u0011MAP\u0003\u0003%I\u0001b\u0019\t\u0013\u0019]sB1A\u0005\n\t\u0005\u0007\u0002\u0003D-\u001f\u0001\u0006IAa1\u0007\u0013\u0019ms\u0002%A\u0012\u0002\u0019u\u0003\u0002\u0003D0\u0003_3\tA\"\u0019\t\u0011\u0019-\u0014q\u0016D\u0001\u0005\u0003D\u0001ba\u001b\u00020\u001a\u0005aQ\u000e\u0005\t\rc\nyK\"\u0001\u0007t!AaqOAX\r\u00031I\b\u0003\u0005\u0007~\u0005=f\u0011\u0001D@\u0011!19)a,\u0007\u0002\u0019%\u0005\u0002\u0003DG\u0003_3\tAb$\b\u000f\u0019Mu\u0002#\u0001\u0007\u0016\u001a9aqS\b\t\u0002\u0019e\u0005\u0002\u0003B^\u0003\u0007$\tAb'\t\u0015\u0019u\u00151\u0019b\u0001\n\u00031y\nC\u0005\u0007b\u0006\r\u0007\u0015!\u0003\u0007\"\"QA1TAb\u0003\u0003%\tIb9\t\u0015\u0011\u0005\u00161YA\u0001\n\u00033I\u000f\u0003\u0006\u0005b\u0005\r\u0017\u0011!C\u0005\tG2aAb&\u0010\u0005\u001a\r\u0006b\u0003DS\u0003#\u0014)\u001a!C\u0001\rOC1Bb,\u0002R\nE\t\u0015!\u0003\u0007*\"Y11NAi\u0005+\u0007I\u0011\u0001Ba\u0011-1\t,!5\u0003\u0012\u0003\u0006IAa1\t\u0011\tm\u0016\u0011\u001bC\u0001\rgC\u0001B\"/\u0002R\u0012\u0005a1\u0018\u0005\t\rc\n\t\u000e\"\u0001\u0007@\"Aa\u0011YAi\t\u00031\u0019\r\u0003\u0005\u0007F\u0006EG\u0011\u0001DP\u0011)\u0019Y0!5\u0002\u0002\u0013\u0005aq\u0019\u0005\u000b\t\u0003\t\t.%A\u0005\u0002\u00195\u0007B\u0003Cv\u0003#\f\n\u0011\"\u0001\u0004\u0002\"QAqAAi\u0003\u0003%\t\u0005\"\u0003\t\u0015\u0011m\u0011\u0011[A\u0001\n\u0003\u0011\t\r\u0003\u0006\u0005\u001e\u0005E\u0017\u0011!C\u0001\r#D!\u0002\"\n\u0002R\u0006\u0005I\u0011\tC\u0014\u0011)!\t$!5\u0002\u0002\u0013\u0005aQ\u001b\u0005\u000b\t{\t\t.!A\u0005B\u0019e\u0007B\u0003C\"\u0003#\f\t\u0011\"\u0011\u0005F!QAqIAi\u0003\u0003%\t\u0005\"\u0013\t\u0015\u0011-\u0013\u0011[A\u0001\n\u00032iN\u0002\u0004\u0007r>\u0001a1\u001f\u0005\t\u0005w\u000bi\u0010\"\u0001\u0007x\"Qa1`A\u007f\u0005\u0004%IA\"@\t\u0013\u001dE\u0011Q Q\u0001\n\u0019}\bBCD\n\u0003{\u0014\r\u0011\"\u0003\b\u0016!IqqDA\u007fA\u0003%qq\u0003\u0005\u000b\u000fC\tiP1A\u0005\n\u001d\r\u0002\"CD\u0016\u0003{\u0004\u000b\u0011BD\u0013\u0011!1y&!@\u0005B\u001d5\u0002\u0002\u0003D6\u0003{$\tE!1\t\u0011\r-\u0014Q C\u0001\u000fcA\u0001B\"\u001d\u0002~\u0012\u0005sQ\u0007\u0005\t\ro\ni\u0010\"\u0011\b:!AaQPA\u007f\t\u0003:i\u0004\u0003\u0005\u0007\b\u0006uH\u0011ID\"\u0011!1i)!@\u0005B\u001d\u001dc!\u0003BV\u00053\u0003!\u0011UD(\u0011-\u0019YA!\b\u0003\u0002\u0003\u0006Iab\u001a\t\u0017\r]$Q\u0004B\u0001B\u0003%!1\u0019\u0005\f\u0007w\u0012iB!A!\u0002\u0013\u0011\u0019\r\u0003\u0005\u0003<\nuA\u0011AD8\u0011)9IH!\bC\u0002\u0013\u0005q1\u0010\u0005\n\u000f\u0007\u0013i\u0002)A\u0005\u000f{B!b\"\"\u0003\u001e\t\u0007I\u0011IDD\u0011%9II!\b!\u0002\u00139I\u0006\u0003\u0005\b\f\nuA\u0011ADG\r\u001d9\u0019J!\b\u0005\u000f+C1bb)\u00032\t\u0005\t\u0015!\u0003\b&\"A!1\u0018B\u0019\t\u00039\t\f\u0003\u0006\b8\nE\"\u0019!C\u0005\u0005\u0003D\u0011b\"/\u00032\u0001\u0006IAa1\t\u0015\u001dm&\u0011\u0007b\u0001\n\u00139i\fC\u0005\b@\nE\u0002\u0015!\u0003\bj!Qq\u0011\u0019B\u0019\u0005\u0004%Iab1\t\u0013\u001d='\u0011\u0007Q\u0001\n\u001d\u0015\u0007BCDi\u0005c\u0011\r\u0011\"\u0003\bT\"Iqq\u001bB\u0019A\u0003%qQ\u001b\u0005\u000b\u000f3\u0014\tD1A\u0005\u0002\u001dm\u0007\"CDs\u0005c\u0001\u000b\u0011BDo\u0011)99O!\rA\u0002\u0013%aq\u0018\u0005\u000b\u000fS\u0014\t\u00041A\u0005\n\u001d-\b\"CDx\u0005c\u0001\u000b\u0015\u0002C\u001b\u0011)1)K!\rC\u0002\u0013%q\u0011\u001f\u0005\n\r_\u0013\t\u0004)A\u0005\u000f\u001fC!bb=\u00032\u0001\u0007I\u0011BD{\u0011)9iP!\rA\u0002\u0013%qq \u0005\n\u0011\u0007\u0011\t\u0004)Q\u0005\u000foD!\u0002#\u0002\u00032\u0001\u0007I\u0011\u0002E\u0004\u0011)A\u0019D!\rA\u0002\u0013%\u0001R\u0007\u0005\n\u0011s\u0011\t\u0004)Q\u0005\u0011\u0013A!\u0002c\u000f\u00032\t\u0007I\u0011\u0002E\u001f\u0011%AYE!\r!\u0002\u0013Ay\u0004\u0003\u0006\tN\tE\u0002\u0019!C\u0005\t\u000bD!\u0002c\u0014\u00032\u0001\u0007I\u0011\u0002E)\u0011%A)F!\r!B\u0013\u0019)FB\u0004\t\u000e\tEb\u0001c\u0004\t\u0017!E!1\u000eBC\u0002\u0013\u0005\u00012\u0003\u0005\f\u00117\u0011YG!A!\u0002\u0013A)\u0002\u0003\u0005\u0003<\n-D\u0011\u0001E\u000f\u0011!\u0019\u0019Ga\u001b\u0005B!\u0005\u0002\u0002CB6\u0005W\"\tE!1\t\u0011\r\u0005#1\u000eC!\u0007\u0007B\u0001ba\u0017\u0003l\u0011\u0005\u0003R\u0005\u0005\t\u0011S\u0011Y\u0007\"\u0011\t,!A\u0001r\u000bB\u0019\t\u0003BI\u0006\u0003\u0005\t\\\tEB\u0011\tE-\u0011!AiF!\r\u0005\n\u0019}\u0006\u0002\u0003E0\u0005c!I\u0001#\u0019\t\u0011!\u0015$\u0011\u0007C\u0005\u0011OB\u0001\u0002c\u001b\u00032\u0011\u0005\u0003\u0012\f\u0005\t\u0011[\u0012\t\u0004\"\u0003\tp!A\u00012\u000fB\u0019\t\u0013AI\u0006\u0003\u0005\tv\tEB\u0011\u0002E<\u0011!AiH!\r\u0005B!}\u0004\u0002\u0003EC\u0005c!\t\u0005#\u0017\t\u0011\u0019\u001d%\u0011\u0007C\u0001\u0011\u000fC\u0001\u0002c$\u0003\u001e\u0011\u0005\u0003\u0012S\u0001\r!\u0006\u0014H/\u001b;j_:DUO\u0019\u0006\u0005\u00057\u0013i*\u0001\u0005tG\u0006d\u0017\rZ:m\u0015\u0011\u0011yJ!)\u0002\rM$(/Z1n\u0015\t\u0011\u0019+\u0001\u0003bW.\f7\u0001\u0001\t\u0004\u0005S\u000bQB\u0001BM\u00051\u0001\u0016M\u001d;ji&|g\u000eS;c'\r\t!q\u0016\t\u0005\u0005c\u00139,\u0004\u0002\u00034*\u0011!QW\u0001\u0006g\u000e\fG.Y\u0005\u0005\u0005s\u0013\u0019L\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005\t\u001d\u0016!\u00053fM\u0006,H\u000e\u001e\"vM\u001a,'oU5{KV\u0011!1\u0019\t\u0005\u0005c\u0013)-\u0003\u0003\u0003H\nM&aA%oi\u0006\u0011B-\u001a4bk2$()\u001e4gKJ\u001c\u0016N_3!Q\r!!Q\u001a\t\u0005\u0005\u001f\u0014).\u0004\u0002\u0003R*!!1\u001bBQ\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0005/\u0014\tNA\u0006J]R,'O\\1m\u0003BL\u0017\u0001D:uCR,g-\u001e7TS:\\W\u0003\u0002Bo\u0005S$\u0002Ba8\u0004\n\rU4\u0011\u0010\t\t\u0005S\u0013\tO!:\u0003|&!!1\u001dBM\u0005\u0011\u0019\u0016N\\6\u0011\t\t\u001d(\u0011\u001e\u0007\u0001\t\u001d\u0011Y/\u0002b\u0001\u0005[\u0014\u0011\u0001V\t\u0005\u0005_\u0014)\u0010\u0005\u0003\u00032\nE\u0018\u0002\u0002Bz\u0005g\u0013qAT8uQ&tw\r\u0005\u0003\u00032\n]\u0018\u0002\u0002B}\u0005g\u00131!\u00118z!!\u0011IK!@\u0003f\u000e\u0005\u0011\u0002\u0002B\u0000\u00053\u0013aaU8ve\u000e,\u0007\u0003BB\u0002\u0007\u000bi!A!)\n\t\r\u001d!\u0011\u0015\u0002\b\u001d>$Xk]3e\u0011\u001d\u0019Y!\u0002a\u0001\u0007\u001b\t1\u0002]1si&$\u0018n\u001c8feB1!\u0011WB\b\u0007'IAa!\u0005\u00034\nIa)\u001e8di&|g\u000e\r\t\u000b\u0005c\u001b)b!\u0007\u0003f\u000eU\u0013\u0002BB\f\u0005g\u0013\u0011BR;oGRLwN\u001c\u001a\u0011\u0007\rm\u0011\"D\u0001\u0002\u00051\u0019uN\\:v[\u0016\u0014\u0018J\u001c4p'\u0015I!qVB\u0011!\u0011\u0019\u0019c!\u0010\u000f\t\r\u00152\u0011\b\b\u0005\u0007O\u0019)D\u0004\u0003\u0004*\rMb\u0002BB\u0016\u0007ci!a!\f\u000b\t\r=\"QU\u0001\u0007yI|w\u000e\u001e \n\u0005\t\r\u0016\u0002\u0002BP\u0005CKAaa\u000e\u0003\u001e\u00069!.\u0019<bINd\u0017\u0002\u0002BL\u0007wQAaa\u000e\u0003\u001e&!1QDB \u0015\u0011\u00119ja\u000f\u0002\u0017\r|gn];nKJLEm]\u000b\u0003\u0007\u000b\u0002baa\u0012\u0004R\rUSBAB%\u0015\u0011\u0019Ye!\u0014\u0002\u0013%lW.\u001e;bE2,'\u0002BB(\u0005g\u000b!bY8mY\u0016\u001cG/[8o\u0013\u0011\u0019\u0019f!\u0013\u0003\u0015%sG-\u001a=fIN+\u0017\u000f\u0005\u0003\u00032\u000e]\u0013\u0002BB-\u0005g\u0013A\u0001T8oO\u0006y1m\u001c8tk6,'/\u00133Cs&#\u0007\u0010\u0006\u0003\u0004V\r}\u0003bBB1\u0017\u0001\u0007!1Y\u0001\u0004S\u0012D\u0018!C9vKV,7+\u001b>f)\u0011\u0011\u0019ma\u001a\t\u000f\r%D\u00021\u0001\u0004V\u0005Q1m\u001c8tk6,'/\u00133\u0002\tML'0\u001a\u0015\u0004\u0013\r=\u0004\u0003\u0002Bh\u0007cJAaa\u001d\u0003R\naAi\u001c(pi&s\u0007.\u001a:ji\"91qO\u0003A\u0002\t\r\u0017aF:uCJ$\u0018I\u001a;fe:\u0013xJZ\"p]N,X.\u001a:t\u0011%\u0019Y(\u0002I\u0001\u0002\u0004\u0011\u0019-\u0001\u0006ck\u001a4WM]*ju\u0016\fac\u001d;bi\u00164W\u000f\\*j].$C-\u001a4bk2$HeM\u000b\u0005\u0007\u0003\u001b)*\u0006\u0002\u0004\u0004*\"!1YBCW\t\u00199\t\u0005\u0003\u0004\n\u000eEUBABF\u0015\u0011\u0019iia$\u0002\u0013Ut7\r[3dW\u0016$'\u0002\u0002Bj\u0005gKAaa%\u0004\f\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0005\u000f\t-hA1\u0001\u0003n\u0006!1/\u001b8l+\u0011\u0019Yj!)\u0015\u0011\ru5QUBU\u0007W\u0003\u0002B!+\u0003b\u000e}51\u0015\t\u0005\u0005O\u001c\t\u000bB\u0004\u0003l\u001e\u0011\rA!<\u0011\u0011\t%&Q`BP\u0007\u0003Aqaa\u0003\b\u0001\u0004\u00199\u000b\u0005\u0006\u00032\u000eU!1YBP\u0005\u0007Dqaa\u001e\b\u0001\u0004\u0011\u0019\rC\u0005\u0004|\u001d\u0001\n\u00111\u0001\u0003D\u0006q1/\u001b8lI\u0011,g-Y;mi\u0012\u001aT\u0003BBA\u0007c#qAa;\t\u0005\u0004\u0011i/\u0001\u0005J]R,'O\\1m!\r\u0019Yb\u0004\u0002\t\u0013:$XM\u001d8bYN\u0019qBa,\u0015\u0005\rU&!D\"p]N,X.\u001a:Fm\u0016tGoE\u0002\u0012\u0005_KC!E\u000f5'\ta\u0001*\u001e2D_6\u0004H.\u001a;fINIQDa,\u0004H\u000e-7\u0011\u001b\t\u0004\u0007\u0013\fR\"A\b\u0011\t\tE6QZ\u0005\u0005\u0007\u001f\u0014\u0019LA\u0004Qe>$Wo\u0019;\u0011\t\rM7Q\u001c\b\u0005\u0007+\u001cIN\u0004\u0003\u0004,\r]\u0017B\u0001B[\u0013\u0011\u0019YNa-\u0002\u000fA\f7m[1hK&!1q\\Bq\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\u0011\u0019YNa-\u0002\u000f\u0019\f\u0017\u000e\\;sKV\u00111q\u001d\t\u0007\u0005c\u001bIo!<\n\t\r-(1\u0017\u0002\u0007\u001fB$\u0018n\u001c8\u0011\t\rM7q^\u0005\u0005\u0007c\u001c\tOA\u0005UQJ|w/\u00192mK\u0006Aa-Y5mkJ,\u0007\u0005\u0006\u0003\u0004x\u000ee\bcABe;!911\u001d\u0011A\u0002\r\u001d\u0018\u0001B2paf$Baa>\u0004\u0000\"I11]\u0011\u0011\u0002\u0003\u00071q]\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\t!)A\u000b\u0003\u0004h\u000e\u0015\u0015!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0005\fA!AQ\u0002C\f\u001b\t!yA\u0003\u0003\u0005\u0012\u0011M\u0011\u0001\u00027b]\u001eT!\u0001\"\u0006\u0002\t)\fg/Y\u0005\u0005\t3!yA\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\u0011\u0011)\u0010\"\t\t\u0013\u0011\rR%!AA\u0002\t\r\u0017a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0005*A1A1\u0006C\u0017\u0005kl!a!\u0014\n\t\u0011=2Q\n\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u00056\u0011m\u0002\u0003\u0002BY\toIA\u0001\"\u000f\u00034\n9!i\\8mK\u0006t\u0007\"\u0003C\u0012O\u0005\u0005\t\u0019\u0001B{\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\u0011-A\u0011\t\u0005\n\tGA\u0013\u0011!a\u0001\u0005\u0007\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0005\u0007\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\t\u0017\ta!Z9vC2\u001cH\u0003\u0002C\u001b\t\u001fB\u0011\u0002b\t,\u0003\u0003\u0005\rA!>\u0003\u0015%s\u0017\u000e^5bY&TXmE\u00055\u0005_\u001b9ma3\u0004RR\u0011Aq\u000b\t\u0004\u0007\u0013$D\u0003\u0002B{\t7B\u0011\u0002b\t9\u0003\u0003\u0005\rAa1\u0015\t\u0011UBq\f\u0005\n\tGQ\u0014\u0011!a\u0001\u0005k\fAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"\u0001\"\u001a\u0011\t\u00115AqM\u0005\u0005\tS\"yA\u0001\u0004PE*,7\r\u001e\u0002\u0007/\u0006\\W-\u001e9\u0014\u0013M\u0011yka2\u0004L\u000eEGC\u0001C9!\r\u0019Im\u0005\u000b\u0005\u0005k$)\bC\u0005\u0005$]\t\t\u00111\u0001\u0003DR!AQ\u0007C=\u0011%!\u0019#GA\u0001\u0002\u0004\u0011)0\u0001\u0004XC.,W\u000f]\u0001\r\u0011V\u00147i\\7qY\u0016$X\r\u001a\t\u0004\u0007\u0013l3#B\u0017\u0005\u0004\u0012=\u0005\u0003\u0003CC\t\u0017\u001b9oa>\u000e\u0005\u0011\u001d%\u0002\u0002CE\u0005g\u000bqA];oi&lW-\u0003\u0003\u0005\u000e\u0012\u001d%!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8ocA!A\u0011\u0013CL\u001b\t!\u0019J\u0003\u0003\u0005\u0016\u0012M\u0011AA5p\u0013\u0011\u0019y\u000eb%\u0015\u0005\u0011}\u0014!B1qa2LH\u0003BB|\t?Cqaa91\u0001\u0004\u00199/A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0011\u0015Fq\u0015\t\u0007\u0005c\u001bIoa:\t\u0013\u0011%\u0016'!AA\u0002\r]\u0018a\u0001=%a\u0005Q\u0011J\\5uS\u0006d\u0017N_3\u0003\u0011!+(-\u0012<f]R\u001c2A\u0010BXS\u0019q\u0004\rQA\u0011\u0015\nQa*Z3e/\u0006\\W-\u001e9\u0014\u0013\u0001\u0014y\u000bb.\u0004L\u000eE\u0007cABe}\u0005A1m\u001c8tk6,'/\u0006\u0002\u0005>B\u00191\u0011\u001a<\u0003\u0011\r{gn];nKJ\u001crA\u001eBX\u0007\u0017\u001c\t.\u0001\u0002jIV\u00111QK\u0001\u0004S\u0012\u0004\u0013\u0001C2bY2\u0014\u0017mY6\u0016\u0005\u00115\u0007C\u0002Ch\t+\u001c9-\u0004\u0002\u0005R*!A1\u001bBO\u0003\u0015\u0019H/Y4f\u0013\u0011!9\u000e\"5\u0003\u001b\u0005\u001b\u0018P\\2DC2d'-Y2l\u0003%\u0019\u0017\r\u001c7cC\u000e\\\u0007\u0005\u0006\u0004\u0005>\u0012uGq\u001c\u0005\b\t\u0007\\\b\u0019AB+\u0011\u001d!Im\u001fa\u0001\t\u001b$b\u0001\"0\u0005d\u0012\u0015\b\"\u0003CbyB\u0005\t\u0019AB+\u0011%!I\r I\u0001\u0002\u0004!i-\u0006\u0002\u0005j*\"1QKBC\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\"\u0001b<+\t\u001157Q\u0011\u000b\u0005\u0005k$\u0019\u0010\u0003\u0006\u0005$\u0005\r\u0011\u0011!a\u0001\u0005\u0007$B\u0001\"\u000e\u0005x\"QA1EA\u0004\u0003\u0003\u0005\rA!>\u0015\t\u0011-A1 \u0005\u000b\tG\tI!!AA\u0002\t\rG\u0003\u0002C\u001b\t\u007fD!\u0002b\t\u0002\u0010\u0005\u0005\t\u0019\u0001B{\u0003%\u0019wN\\:v[\u0016\u0014\b\u0005\u0006\u0003\u0006\u0006\u0015\u001d\u0001cABeA\"9A\u0011X2A\u0002\u0011uF\u0003BC\u0003\u000b\u0017A\u0011\u0002\"/e!\u0003\u0005\r\u0001\"0\u0016\u0005\u0015=!\u0006\u0002C_\u0007\u000b#BA!>\u0006\u0014!IA1\u00055\u0002\u0002\u0003\u0007!1\u0019\u000b\u0005\tk)9\u0002C\u0005\u0005$)\f\t\u00111\u0001\u0003vR!A1BC\u000e\u0011%!\u0019c[A\u0001\u0002\u0004\u0011\u0019\r\u0006\u0003\u00056\u0015}\u0001\"\u0003C\u0012]\u0006\u0005\t\u0019\u0001B{\u0005M\u0011VmZ5tiJ\fG/[8o!\u0016tG-\u001b8h'%\u0001%q\u0016C\\\u0007\u0017\u001c\t\u000e\u0006\u0002\u0006(A\u00191\u0011\u001a!\u0015\t\tUX1\u0006\u0005\n\tG!\u0015\u0011!a\u0001\u0005\u0007$B\u0001\"\u000e\u00060!IA1\u0005$\u0002\u0002\u0003\u0007!Q\u001f\u0002\b)JL\b+\u001e7m')\t\tCa,\u00058\u000e-7\u0011\u001b\u000b\u0003\u000bo\u0001Ba!3\u0002\"Q!!Q_C\u001e\u0011)!\u0019#!\u000b\u0002\u0002\u0003\u0007!1\u0019\u000b\u0005\tk)y\u0004\u0003\u0006\u0005$\u00055\u0012\u0011!a\u0001\u0005k\u0014!\"\u00168SK\u001eL7\u000f^3s'%Q%q\u0016C\\\u0007\u0017\u001c\t\u000e\u0006\u0003\u0006H\u0015%\u0003cABe\u0015\"9A1Y'A\u0002\rUC\u0003BC$\u000b\u001bB\u0011\u0002b1O!\u0003\u0005\ra!\u0016\u0015\t\tUX\u0011\u000b\u0005\n\tG\u0011\u0016\u0011!a\u0001\u0005\u0007$B\u0001\"\u000e\u0006V!IA1\u0005+\u0002\u0002\u0003\u0007!Q\u001f\u000b\u0005\t\u0017)I\u0006C\u0005\u0005$U\u000b\t\u00111\u0001\u0003DR!AQGC/\u0011%!\u0019\u0003WA\u0001\u0002\u0004\u0011)0A\nSK\u001eL7\u000f\u001e:bi&|g\u000eU3oI&tw-\u0001\u0006V]J+w-[:uKJ\u00042a!3['\u0015QVq\rCH!!!)\tb#\u0004V\u0015\u001dCCAC2)\u0011)9%\"\u001c\t\u000f\u0011\rW\f1\u0001\u0004VQ!Q\u0011OC:!\u0019\u0011\tl!;\u0004V!IA\u0011\u00160\u0002\u0002\u0003\u0007QqI\u0001\u000b\u001d\u0016,GmV1lKV\u0004\bcABeaN)\u0001/b\u001f\u0005\u0010BAAQ\u0011CF\t{+)\u0001\u0006\u0002\u0006xQ!QQACA\u0011\u001d!Il\u001da\u0001\t{#B!\"\"\u0006\bB1!\u0011WBu\t{C\u0011\u0002\"+u\u0003\u0003\u0005\r!\"\u0002\u0002\u0011\r{gn];nKJ\u0004Ba!3\u0002\u0014M1\u00111CCH\t\u001f\u0003\"\u0002\"\"\u0006\u0012\u000eUCQ\u001aC_\u0013\u0011)\u0019\nb\"\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t'\u0007\u0006\u0002\u0006\fR1AQXCM\u000b7C\u0001\u0002b1\u0002\u001a\u0001\u00071Q\u000b\u0005\t\t\u0013\fI\u00021\u0001\u0005NR!QqTCT!\u0019\u0011\tl!;\u0006\"BA!\u0011WCR\u0007+\"i-\u0003\u0003\u0006&\nM&A\u0002+va2,'\u0007\u0003\u0006\u0005*\u0006m\u0011\u0011!a\u0001\t{\u000bq\u0001\u0016:z!VdG.A\u0005D_6\u0004H.\u001a;fIB!1\u0011ZA\u001c\u0005%\u0019u.\u001c9mKR,Gm\u0005\u0005\u00028\t=61ZBi)\t)i\u000b\u0006\u0003\u0003v\u0016]\u0006B\u0003C\u0012\u0003\u007f\t\t\u00111\u0001\u0003DR!AQGC^\u0011)!\u0019#a\u0011\u0002\u0002\u0003\u0007!Q\u001f\u0002\t\u0011V\u00147\u000b^1uKN!\u00111\nBXS\u0019\tY%a \u0002N\t11\t\\8tK\u0012\u001c\"\"a \u00030\u0016\u001d71ZBi!\u0011\u0019I-a\u0013\u0015\t\u0015-WQ\u001a\t\u0005\u0007\u0013\fy\b\u0003\u0005\u0004d\u0006\u0015\u0005\u0019ABt)\u0011)Y-\"5\t\u0015\r\r\u0018q\u0011I\u0001\u0002\u0004\u00199\u000f\u0006\u0003\u0003v\u0016U\u0007B\u0003C\u0012\u0003\u001f\u000b\t\u00111\u0001\u0003DR!AQGCm\u0011)!\u0019#a%\u0002\u0002\u0003\u0007!Q\u001f\u000b\u0005\t\u0017)i\u000e\u0003\u0006\u0005$\u0005U\u0015\u0011!a\u0001\u0005\u0007$B\u0001\"\u000e\u0006b\"QA1EAN\u0003\u0003\u0005\rA!>\u0003\t=\u0003XM\\\n\u000b\u0003\u001b\u0012y+b2\u0004L\u000eE\u0017AD2bY2\u0014\u0017mY6GkR,(/Z\u000b\u0003\u000bW\u0004b!\"<\u0006t\u0016]XBACx\u0015\u0011)\tPa-\u0002\u0015\r|gnY;se\u0016tG/\u0003\u0003\u0006v\u0016=(A\u0002$viV\u0014X\r\u0005\u0004\u0005P\u0012UGqW\u0001\u0010G\u0006dGNY1dW\u001a+H/\u001e:fA\u0005i!/Z4jgR\u0014\u0018\r^5p]N,\"!b@\u0011\r\rMg\u0011\u0001C_\u0013\u00111\u0019a!9\u0003\t1K7\u000f^\u0001\u000fe\u0016<\u0017n\u001d;sCRLwN\\:!)\u00191IAb\u0003\u0007\u000eA!1\u0011ZA'\u0011!)9/a\u0016A\u0002\u0015-\b\u0002CC~\u0003/\u0002\r!b@\u0015\r\u0019%a\u0011\u0003D\n\u0011))9/!\u0017\u0011\u0002\u0003\u0007Q1\u001e\u0005\u000b\u000bw\fI\u0006%AA\u0002\u0015}XC\u0001D\fU\u0011)Yo!\"\u0016\u0005\u0019m!\u0006BC\u0000\u0007\u000b#BA!>\u0007 !QA1EA2\u0003\u0003\u0005\rAa1\u0015\t\u0011Ub1\u0005\u0005\u000b\tG\t9'!AA\u0002\tUH\u0003\u0002C\u0006\rOA!\u0002b\t\u0002j\u0005\u0005\t\u0019\u0001Bb)\u0011!)Db\u000b\t\u0015\u0011\r\u0012qNA\u0001\u0002\u0004\u0011)0\u0001\u0003Pa\u0016t\u0007\u0003BBe\u0003g\u001ab!a\u001d\u00074\u0011=\u0005C\u0003CC\u000b#+Y/b@\u0007\nQ\u0011aq\u0006\u000b\u0007\r\u00131IDb\u000f\t\u0011\u0015\u001d\u0018\u0011\u0010a\u0001\u000bWD\u0001\"b?\u0002z\u0001\u0007Qq \u000b\u0005\r\u007f1\u0019\u0005\u0005\u0004\u00032\u000e%h\u0011\t\t\t\u0005c+\u0019+b;\u0006\u0000\"QA\u0011VA>\u0003\u0003\u0005\rA\"\u0003\u0002\r\rcwn]3e!\u0011\u0019I-a(\u0014\r\u0005}e1\nCH!!!)\tb#\u0004h\u0016-GC\u0001D$)\u0011)YM\"\u0015\t\u0011\r\r\u0018Q\u0015a\u0001\u0007O$B\u0001\"*\u0007V!QA\u0011VAT\u0003\u0003\u0005\r!b3\u0002\u0017\u0019K\u00070\u001a3Rk\u0016,Xm]\u0001\r\r&DX\rZ)vKV,7\u000f\t\u0002\u000f!\u0006\u0014H/\u001b;j_:\fV/Z;f'\u0011\tyKa,\u0002\t%t\u0017\u000e\u001e\u000b\u0005\rG2I\u0007\u0005\u0003\u00032\u001a\u0015\u0014\u0002\u0002D4\u0005g\u0013A!\u00168ji\"AA1YAY\u0001\u0004\u0019)&A\u0005u_R\fGnU5{KR!!1\u0019D8\u0011!!\u0019-!.A\u0002\rU\u0013aB5t\u000b6\u0004H/\u001f\u000b\u0005\tk1)\b\u0003\u0005\u0005D\u0006]\u0006\u0019AB+\u0003!qwN\\#naRLH\u0003\u0002C\u001b\rwB\u0001\u0002b1\u0002:\u0002\u00071QK\u0001\u0006_\u001a4WM\u001d\u000b\u0007\rG2\tIb!\t\u0011\u0011\r\u00171\u0018a\u0001\u0007+B\u0001B\"\"\u0002<\u0002\u0007!Q_\u0001\u0005K2,W.\u0001\u0003q_2dG\u0003\u0002BX\r\u0017C\u0001\u0002b1\u0002>\u0002\u00071QK\u0001\u0007e\u0016lwN^3\u0015\t\u0019\rd\u0011\u0013\u0005\t\t\u0007\fy\f1\u0001\u0004V\u0005i1i\u001c8tk6,'/U;fk\u0016\u0004Ba!3\u0002D\ni1i\u001c8tk6,'/U;fk\u0016\u001cb!a1\u00030\u0012=EC\u0001DK\u0003\u0015)W\u000e\u001d;z+\t1\t\u000b\u0005\u0003\u0004J\u0006E7\u0003CAi\u0005_\u001bYm!5\u0002\u000bE,X-^3\u0016\u0005\u0019%\u0006CBB$\rW\u0013)0\u0003\u0003\u0007.\u000e%#!B)vKV,\u0017AB9vKV,\u0007%A\u0003tSj,\u0007\u0005\u0006\u0004\u0007\"\u001aUfq\u0017\u0005\t\rK\u000bY\u000e1\u0001\u0007*\"A11NAn\u0001\u0004\u0011\u0019-A\u0004f]F,X-^3\u0015\t\u0019\u0005fQ\u0018\u0005\t\r\u000b\u000bi\u000e1\u0001\u0003vV\u0011AQG\u0001\u0005Q\u0016\fG-\u0006\u0002\u0003v\u0006!A/Y5m)\u00191\tK\"3\u0007L\"QaQUAs!\u0003\u0005\rA\"+\t\u0015\r-\u0014Q\u001dI\u0001\u0002\u0004\u0011\u0019-\u0006\u0002\u0007P*\"a\u0011VBC)\u0011\u0011)Pb5\t\u0015\u0011\r\u0012q^A\u0001\u0002\u0004\u0011\u0019\r\u0006\u0003\u00056\u0019]\u0007B\u0003C\u0012\u0003g\f\t\u00111\u0001\u0003vR!A1\u0002Dn\u0011)!\u0019#!>\u0002\u0002\u0003\u0007!1\u0019\u000b\u0005\tk1y\u000e\u0003\u0006\u0005$\u0005m\u0018\u0011!a\u0001\u0005k\fa!Z7qif\u0004CC\u0002DQ\rK49\u000f\u0003\u0005\u0007&\u0006-\u0007\u0019\u0001DU\u0011!\u0019Y'a3A\u0002\t\rG\u0003\u0002Dv\r_\u0004bA!-\u0004j\u001a5\b\u0003\u0003BY\u000bG3IKa1\t\u0015\u0011%\u0016QZA\u0001\u0002\u00041\tK\u0001\nQCJ$\u0018\u000e^5p]F+X-^3J[Bd7CBA\u007f\u0005_3)\u0010\u0005\u0003\u0004J\u0006=FC\u0001D}!\u0011\u0019I-!@\u0002\u000fE,X-^3tcU\u0011aq \t\u0007\u000f\u00039iA\")\u000e\u0005\u001d\r!\u0002BD\u0003\u000f\u000f\ta!\u0019;p[&\u001c'\u0002BCy\u000f\u0013QAab\u0003\u0005\u0014\u0005!Q\u000f^5m\u0013\u00119yab\u0001\u0003)\u0005#x.\\5d%\u00164WM]3oG\u0016\f%O]1z\u0003!\tX/Z;fgF\u0002\u0013aB9vKV,7OM\u000b\u0003\u000f/\u0001\u0002b\"\u0007\b\u001c\rUc\u0011U\u0007\u0003\u000f\u000fIAa\"\b\b\b\t\t2i\u001c8dkJ\u0014XM\u001c;ICNDW*\u00199\u0002\u0011E,X-^3te\u0001\n!b\u0018;pi\u0006d7+\u001b>f+\t9)\u0003\u0005\u0003\b\u0002\u001d\u001d\u0012\u0002BD\u0015\u000f\u0007\u0011Q\"\u0011;p[&\u001c\u0017J\u001c;fO\u0016\u0014\u0018aC0u_R\fGnU5{K\u0002\"BAb\u0019\b0!AA1\u0019B\u0007\u0001\u0004\u0019)\u0006\u0006\u0003\u0003D\u001eM\u0002\u0002\u0003Cb\u0005#\u0001\ra!\u0016\u0015\t\u0011Urq\u0007\u0005\t\t\u0007\u0014\u0019\u00021\u0001\u0004VQ!AQGD\u001e\u0011!!\u0019M!\u0006A\u0002\rUCC\u0002D2\u000f\u007f9\t\u0005\u0003\u0005\u0005D\n]\u0001\u0019AB+\u0011!1)Ia\u0006A\u0002\tUH\u0003\u0002BX\u000f\u000bB\u0001\u0002b1\u0003\u001a\u0001\u00071Q\u000b\u000b\u0005\rG:I\u0005\u0003\u0005\u0005D\nm\u0001\u0019AB+Q\ry!Q\u001a\u0015\u0004\u001d\t5W\u0003BD)\u000fG\u001aBA!\b\bTAAAqZD+\u000f3:)'\u0003\u0003\bX\u0011E'aH$sCBD7\u000b^1hK^KG\u000f['bi\u0016\u0014\u0018.\u00197ju\u0016$g+\u00197vKB1q1LD/\u000fCj!A!(\n\t\u001d}#Q\u0014\u0002\n'&t7n\u00155ba\u0016\u0004BAa:\bd\u0011A!1\u001eB\u000f\u0005\u0004\u0011i\u000f\u0005\u0005\u0003*\nux\u0011MB\u0001!\u0019\u0011\tla\u0004\bjAQ!\u0011WB\u000b\u000fW:\tg!\u0016\u0011\u0007\u001d5\u0014BD\u0002\u0003*\u0002!\u0002b\"\u001d\bt\u001dUtq\u000f\t\u0007\u0005S\u0013ib\"\u0019\t\u0011\r-!Q\u0005a\u0001\u000fOB\u0001ba\u001e\u0003&\u0001\u0007!1\u0019\u0005\t\u0007w\u0012)\u00031\u0001\u0003D\u0006\u0011\u0011N\\\u000b\u0003\u000f{\u0002bab\u0017\b\u0000\u001d\u0005\u0014\u0002BDA\u0005;\u0013Q!\u00138mKR\f1!\u001b8!\u0003\u0015\u0019\b.\u00199f+\t9I&\u0001\u0004tQ\u0006\u0004X\rI\u0001\fGJ,\u0017\r^3Rk\u0016,X\r\u0006\u0002\b\u0010B!q\u0011SAX\u001d\r9iG\u0004\u0002\u0013!\u0006\u0014H/\u001b;j_:\u001c\u0016N\\6M_\u001eL7m\u0005\u0004\u00032\u001d]uQ\u0014\t\u0005\t\u001f<I*\u0003\u0003\b\u001c\u0012E'aD$sCBD7\u000b^1hK2{w-[2\u0011\t\u0011=wqT\u0005\u0005\u000fC#\tNA\u0005J]\"\u000bg\u000e\u001a7fe\u00061ql\u001d5ba\u0016\u0004Bab*\b*6\u0011!QD\u0005\u0005\u000fW;iKA\u0003TQ\u0006\u0004X-\u0003\u0003\b0\nu%!B$sCBDG\u0003BDZ\u000fk\u0003Bab*\u00032!Aq1\u0015B\u001b\u0001\u00049)+A\bEK6\fg\u000e\u001a+ie\u0016\u001c\bn\u001c7e\u0003A!U-\\1oIRC'/Z:i_2$\u0007%A\fnCR,'/[1mSj,G\rU1si&$\u0018n\u001c8feV\u0011q\u0011N\u0001\u0019[\u0006$XM]5bY&TX\r\u001a)beRLG/[8oKJ\u0004\u0013aD2bY2\u0014\u0017mY6Qe>l\u0017n]3\u0016\u0005\u001d\u0015\u0007CBCw\u000f\u000f<Y-\u0003\u0003\bJ\u0016=(a\u0002)s_6L7/\u001a\t\u0007\t\u001f$)n\"4\u0011\u0007\u001dEe(\u0001\tdC2d'-Y2l!J|W.[:fA\u0005!bn\u001c*fO&\u001cHO]1uS>t7o\u0015;bi\u0016,\"a\"6\u0011\t\u001dE\u0015QJ\u0001\u0016]>\u0014VmZ5tiJ\fG/[8ogN#\u0018\r^3!\u0003\u0015\u0019H/\u0019;f+\t9i\u000e\u0005\u0004\b\u0002\u001d}w1]\u0005\u0005\u000fC<\u0019AA\bBi>l\u0017n\u0019*fM\u0016\u0014XM\\2f!\u00119\t*a\u0013\u0002\rM$\u0018\r^3!\u0003-Ig.\u001b;jC2L'0\u001a3\u0002\u001f%t\u0017\u000e^5bY&TX\rZ0%KF$BAb\u0019\bn\"QA1\u0005B'\u0003\u0003\u0005\r\u0001\"\u000e\u0002\u0019%t\u0017\u000e^5bY&TX\r\u001a\u0011\u0016\u0005\u001d=\u0015a\u00029f]\u0012LgnZ\u000b\u0003\u000fo\u0004baa\u0012\bz\u001e\u0005\u0014\u0002BD~\u0007\u0013\u0012aAV3di>\u0014\u0018a\u00039f]\u0012LgnZ0%KF$BAb\u0019\t\u0002!QA1\u0005B,\u0003\u0003\u0005\rab>\u0002\u0011A,g\u000eZ5oO\u0002\nAbY8ogVlWM]%oM>,\"\u0001#\u0003\u0011\t!-!1N\u0007\u0003\u0005c\u0011\u0001cQ8ogVlWM]%oM>LU\u000e\u001d7\u0014\r\t-$qVD6\u0003%\u0019wN\\:v[\u0016\u00148/\u0006\u0002\t\u0016A111\u001bE\f\u00113IAab?\u0004bB\u0019q\u0011\u0013<\u0002\u0015\r|gn];nKJ\u001c\b\u0005\u0006\u0003\t\n!}\u0001\u0002\u0003E\t\u0005c\u0002\r\u0001#\u0006\u0015\t\t\r\u00072\u0005\u0005\t\u0007S\u0012\u0019\b1\u0001\u0004VQ!1Q\u000bE\u0014\u0011!\u0019\tG!\u001fA\u0002\t\r\u0017AD4fi\u000e{gn];nKJLEm]\u000b\u0003\u0011[\u0001b\u0001c\f\t2\rUSBAD\u0005\u0013\u00111\u0019a\"\u0003\u0002!\r|gn];nKJLeNZ8`I\u0015\fH\u0003\u0002D2\u0011oA!\u0002b\t\u0003^\u0005\u0005\t\u0019\u0001E\u0005\u00035\u0019wN\\:v[\u0016\u0014\u0018J\u001c4pA\u0005Qa.Z3e/\u0006\\W-\u001e9\u0016\u0005!}\u0002C\u0002E!\u0011\u000fBI\"\u0004\u0002\tD)!\u0001RIB'\u0003\u001diW\u000f^1cY\u0016LA\u0001#\u0013\tD\t9Aj\u001c8h\u001b\u0006\u0004\u0018a\u00038fK\u0012<\u0016m[3va\u0002\nQbY1mY\n\f7m[\"pk:$\u0018!E2bY2\u0014\u0017mY6D_VtGo\u0018\u0013fcR!a1\rE*\u0011)!\u0019Ca\u001a\u0002\u0002\u0003\u00071QK\u0001\u000fG\u0006dGNY1dW\u000e{WO\u001c;!\u0003!\u0001(/Z*uCJ$HC\u0001D2\u0003\u0019yg\u000eU;tQ\u00061\u0011n\u001d$vY2\fq\u0001];cY&\u001c\b\u000e\u0006\u0003\u0007d!\r\u0004\u0002\u0003DC\u0005\u0007\u0003\ra\"\u0019\u0002\r]\f7.Z;q)\u00111\u0019\u0007#\u001b\t\u0011\u0011\r'Q\u0011a\u0001\u0007+\n\u0001c\u001c8VaN$(/Z1n\r&t\u0017n\u001d5\u0002\u0011\r|W\u000e\u001d7fi\u0016$BAb\u0019\tr!AA1\u0019BE\u0001\u0004\u0019)&A\u0004uef\u0004V\u000f\u001c7\u0002\u000f=tWI^3oiR!a1\rE=\u0011!AYH!$A\u0002\u001d5\u0017AA3w\u0003Eyg.\u00169tiJ,\u0017-\u001c$bS2,(/\u001a\u000b\u0005\rGB\t\t\u0003\u0005\t\u0004\n=\u0005\u0019ABw\u0003\t)\u00070\u0001\u0005q_N$8\u000b^8q)\u0019\u0011y\u000b##\t\f\"AA1\u0019BJ\u0001\u0004\u0019)\u0006\u0003\u0005\t\u000e\nM\u0005\u0019ADf\u0003-AWOY\"bY2\u0014\u0017mY6\u0002?\r\u0014X-\u0019;f\u0019><\u0017nY!oI6\u000bG/\u001a:jC2L'0\u001a3WC2,X\r\u0006\u0003\t\u0014\"U\u0005\u0003\u0003BY\u000bG;9j\"\u001a\t\u0011!]%Q\u0013a\u0001\u00113\u000b1#\u001b8iKJLG/\u001a3BiR\u0014\u0018NY;uKN\u0004Bab\u0017\t\u001c&!\u0001R\u0014BO\u0005)\tE\u000f\u001e:jEV$Xm\u001d\u0015\u0005\u0005;\u0011i\r")
@InternalApi
public class PartitionHub<T>
extends GraphStageWithMaterializedValue<SinkShape<T>, Source<T, NotUsed>> {
    public final Function0<Function2<ConsumerInfo, T, Object>> akka$stream$scaladsl$PartitionHub$$partitioner;
    public final int akka$stream$scaladsl$PartitionHub$$startAfterNrOfConsumers;
    public final int akka$stream$scaladsl$PartitionHub$$bufferSize;
    private final Inlet<T> in;
    private final SinkShape<T> shape;

    public static <T> int sink$default$3() {
        return PartitionHub$.MODULE$.sink$default$3();
    }

    public static <T> Sink<T, Source<T, NotUsed>> sink(Function2<Object, T, Object> partitioner, int startAfterNrOfConsumers, int bufferSize) {
        return PartitionHub$.MODULE$.sink(partitioner, startAfterNrOfConsumers, bufferSize);
    }

    public static <T> int statefulSink$default$3() {
        return PartitionHub$.MODULE$.statefulSink$default$3();
    }

    public static <T> Sink<T, Source<T, NotUsed>> statefulSink(Function0<Function2<ConsumerInfo, T, Object>> partitioner, int startAfterNrOfConsumers, int bufferSize) {
        return PartitionHub$.MODULE$.statefulSink(partitioner, startAfterNrOfConsumers, bufferSize);
    }

    public Inlet<T> in() {
        return this.in;
    }

    @Override
    public SinkShape<T> shape() {
        return this.shape;
    }

    public Internal.PartitionQueue createQueue() {
        return new Internal.PartitionQueueImpl();
    }

    @Override
    public Tuple2<GraphStageLogic, Source<T, NotUsed>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        AtomicLong idCounter = new AtomicLong();
        PartitionSinkLogic logic = new PartitionSinkLogic(this.shape());
        GraphStage source2 = new GraphStage<SourceShape<T>>(null, idCounter, logic){
            private final Outlet<T> out;
            private final SourceShape<T> shape;
            public final AtomicLong idCounter$3;
            public final PartitionSinkLogic logic$3;

            public Outlet<T> out() {
                return this.out;
            }

            public SourceShape<T> shape() {
                return this.shape;
            }

            public GraphStageLogic createLogic(Attributes inheritedAttributes) {
                return new OutHandler(this){
                    private final long id;
                    private AsyncCallback<Internal.HubEvent> hubCallback;
                    private final AsyncCallback<Internal.ConsumerEvent> callback;
                    private final Internal.Consumer consumer;
                    private long callbackCount;
                    private final /* synthetic */ $anon$7 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    private long id() {
                        return this.id;
                    }

                    private AsyncCallback<Internal.HubEvent> hubCallback() {
                        return this.hubCallback;
                    }

                    private void hubCallback_$eq(AsyncCallback<Internal.HubEvent> x$1) {
                        this.hubCallback = x$1;
                    }

                    private AsyncCallback<Internal.ConsumerEvent> callback() {
                        return this.callback;
                    }

                    private Internal.Consumer consumer() {
                        return this.consumer;
                    }

                    private long callbackCount() {
                        return this.callbackCount;
                    }

                    private void callbackCount_$eq(long x$1) {
                        this.callbackCount = x$1;
                    }

                    public void preStart() {
                        Function1<Try, Object> & Serializable onHubReady = (Function1<Try, Object> & Serializable)x0$1 -> {
                            $anon$7$$anon$8.$anonfun$preStart$7(this, x0$1);
                            return BoxedUnit.UNIT;
                        };
                        this.register$2(onHubReady);
                    }

                    public void onPull() {
                        if (this.hubCallback() != null) {
                            Object elem = this.$outer.logic$3.poll(this.id(), this.hubCallback());
                            Object object = elem;
                            if (object == null) {
                                this.hubCallback().invoke(new Internal.NeedWakeup(this.consumer()));
                                return;
                            }
                            if (PartitionHub$Internal$Completed$.MODULE$.equals(object)) {
                                this.completeStage();
                                return;
                            }
                            this.push(this.$outer.out(), elem);
                            return;
                        }
                    }

                    public void postStop() {
                        if (this.hubCallback() != null) {
                            this.hubCallback().invoke(new Internal.UnRegister(this.id()));
                            return;
                        }
                    }

                    private void onCommand(Internal.ConsumerEvent cmd) {
                        Option<Throwable> option;
                        this.callbackCount_$eq(this.callbackCount() + 1L);
                        boolean bl = false;
                        Internal.HubCompleted hubCompleted = null;
                        Internal.ConsumerEvent consumerEvent = cmd;
                        if (consumerEvent instanceof Internal.HubCompleted) {
                            bl = true;
                            hubCompleted = (Internal.HubCompleted)consumerEvent;
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
                        if (PartitionHub$Internal$Wakeup$.MODULE$.equals(consumerEvent)) {
                            if (this.isAvailable(this.$outer.out())) {
                                this.onPull();
                                return;
                            }
                            return;
                        }
                        if (PartitionHub$Internal$Initialize$.MODULE$.equals(consumerEvent)) {
                            if (this.isAvailable(this.$outer.out()) && this.hubCallback() != null) {
                                this.onPull();
                                return;
                            }
                            return;
                        }
                        throw new MatchError(consumerEvent);
                    }

                    public static final /* synthetic */ void $anonfun$callback$1($anon$7$$anon$8 $this, Internal.ConsumerEvent cmd) {
                        $this.onCommand(cmd);
                    }

                    public static final /* synthetic */ void $anonfun$preStart$7($anon$7$$anon$8 $this, Try x0$1) {
                        Try try_ = x0$1;
                        if (try_ instanceof Success) {
                            Success success = (Success)try_;
                            AsyncCallback callback = (AsyncCallback)success.value();
                            $this.hubCallback_$eq(callback);
                            callback.invoke(PartitionHub$Internal$RegistrationPending$.MODULE$);
                            if ($this.isAvailable($this.$outer.out())) {
                                $this.onPull();
                                return;
                            }
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

                    public static final /* synthetic */ void $anonfun$preStart$8(AsyncCallback eta$0$1$2, Try t2) {
                        eta$0$1$2.invoke(t2);
                    }

                    private final void register$2(Function1 onHubReady$2) {
                        block4: {
                            Internal.HubState hubState;
                            block5: {
                                List<Internal.Consumer> newRegistrations;
                                Future<AsyncCallback<Internal.HubEvent>> callbackFuture;
                                Internal.Open open;
                                do {
                                    Option<Throwable> option;
                                    boolean bl = false;
                                    Internal.Closed closed = null;
                                    hubState = this.$outer.logic$3.state().get();
                                    if (hubState instanceof Internal.Closed) {
                                        bl = true;
                                        closed = (Internal.Closed)hubState;
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
                                    if (!(hubState instanceof Internal.Open)) break block5;
                                    open = (Internal.Open)hubState;
                                    callbackFuture = open.callbackFuture();
                                    List<Internal.Consumer> registrations = open.registrations();
                                    Internal.Consumer consumer = this.consumer();
                                    newRegistrations = registrations.$colon$colon(consumer);
                                } while (!this.$outer.logic$3.state().compareAndSet(open, new Internal.Open(callbackFuture, newRegistrations)));
                                AsyncCallback<T> asyncCallback = this.getAsyncCallback(onHubReady$2);
                                callbackFuture.onComplete((Function1<Try, Object> & Serializable)t2 -> {
                                    $anon$7$$anon$8.$anonfun$preStart$8(asyncCallback, t2);
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
                        this.id = $outer.idCounter$3.getAndIncrement();
                        this.callback = this.getAsyncCallback((Function1<Internal.ConsumerEvent, Object> & Serializable)cmd -> {
                            $anon$7$$anon$8.$anonfun$callback$1(this, cmd);
                            return BoxedUnit.UNIT;
                        });
                        this.consumer = new Internal.Consumer(this.id(), this.callback());
                        this.callbackCount = 0L;
                        this.setHandler($outer.out(), this);
                    }

                    private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                        return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$callback$1$adapted(akka.stream.scaladsl.PartitionHub$$anon$7$$anon$8 akka.stream.scaladsl.PartitionHub$Internal$ConsumerEvent ), $anonfun$preStart$7$adapted(akka.stream.scaladsl.PartitionHub$$anon$7$$anon$8 scala.util.Try ), $anonfun$preStart$8$adapted(akka.stream.stage.AsyncCallback scala.util.Try )}, serializedLambda);
                    }
                };
            }
            {
                this.idCounter$3 = idCounter$3;
                this.logic$3 = logic$3;
                this.out = Outlet$.MODULE$.apply("PartitionHub.out");
                this.shape = new SourceShape<T>(this.out());
            }
        };
        return new Tuple2<GraphStageLogic, Source<T, NotUsed>>(logic, Source$.MODULE$.fromGraph(source2));
    }

    public PartitionHub(Function0<Function2<ConsumerInfo, T, Object>> partitioner, int startAfterNrOfConsumers, int bufferSize) {
        this.akka$stream$scaladsl$PartitionHub$$partitioner = partitioner;
        this.akka$stream$scaladsl$PartitionHub$$startAfterNrOfConsumers = startAfterNrOfConsumers;
        this.akka$stream$scaladsl$PartitionHub$$bufferSize = bufferSize;
        this.in = Inlet$.MODULE$.apply("PartitionHub.in");
        this.shape = new SinkShape<T>(this.in());
    }

    @DoNotInherit
    public static interface ConsumerInfo
    extends PartitionHub.ConsumerInfo {
        public IndexedSeq<Object> consumerIds();

        @Override
        public long consumerIdByIdx(int var1);

        @Override
        public int queueSize(long var1);

        @Override
        public int size();
    }

    private class PartitionSinkLogic
    extends GraphStageLogic
    implements InHandler {
        private final int DemandThreshold;
        private final Function2<ConsumerInfo, T, Object> materializedPartitioner;
        private final Promise<AsyncCallback<Internal.HubEvent>> callbackPromise;
        private final Internal.Open noRegistrationsState;
        private final AtomicReference<Internal.HubState> state;
        private boolean initialized;
        private final Internal.PartitionQueue akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$queue;
        private Vector<T> pending;
        private ConsumerInfoImpl consumerInfo;
        private final LongMap<Internal.Consumer> needWakeup;
        private long callbackCount;

        private int DemandThreshold() {
            return this.DemandThreshold;
        }

        private Function2<ConsumerInfo, T, Object> materializedPartitioner() {
            return this.materializedPartitioner;
        }

        private Promise<AsyncCallback<Internal.HubEvent>> callbackPromise() {
            return this.callbackPromise;
        }

        private Internal.Open noRegistrationsState() {
            return this.noRegistrationsState;
        }

        public AtomicReference<Internal.HubState> state() {
            return this.state;
        }

        private boolean initialized() {
            return this.initialized;
        }

        private void initialized_$eq(boolean x$1) {
            this.initialized = x$1;
        }

        public Internal.PartitionQueue akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$queue() {
            return this.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$queue;
        }

        private Vector<T> pending() {
            return this.pending;
        }

        private void pending_$eq(Vector<T> x$1) {
            this.pending = x$1;
        }

        private ConsumerInfoImpl consumerInfo() {
            return this.consumerInfo;
        }

        private void consumerInfo_$eq(ConsumerInfoImpl x$1) {
            this.consumerInfo = x$1;
        }

        private LongMap<Internal.Consumer> needWakeup() {
            return this.needWakeup;
        }

        private long callbackCount() {
            return this.callbackCount;
        }

        private void callbackCount_$eq(long x$1) {
            this.callbackCount = x$1;
        }

        @Override
        public void preStart() {
            this.setKeepGoing(true);
            this.callbackPromise().success(this.getAsyncCallback((Function1<Internal.HubEvent, Object> & Serializable)ev -> {
                this.onEvent(ev);
                return BoxedUnit.UNIT;
            }));
            if (this.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$$outer().akka$stream$scaladsl$PartitionHub$$startAfterNrOfConsumers == 0) {
                this.pull(this.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$$outer().in());
                return;
            }
        }

        @Override
        public void onPush() {
            this.publish(this.grab(this.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$$outer().in()));
            if (!this.isFull()) {
                this.pull(this.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$$outer().in());
                return;
            }
        }

        private boolean isFull() {
            return this.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$queue().totalSize() + this.pending().size() >= this.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$$outer().akka$stream$scaladsl$PartitionHub$$bufferSize;
        }

        private void publish(T elem) {
            if (!this.initialized() || this.consumerInfo().consumers().isEmpty()) {
                this.pending_$eq((Vector)this.pending().$colon$plus(elem));
                return;
            }
            long id = BoxesRunTime.unboxToLong(this.materializedPartitioner().apply(this.consumerInfo(), elem));
            if (id >= 0L) {
                this.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$queue().offer(id, elem);
                this.wakeup(id);
                return;
            }
        }

        private void wakeup(long id) {
            Option<Internal.Consumer> option = this.needWakeup().get(id);
            if (None$.MODULE$.equals(option)) {
                return;
            }
            if (option instanceof Some) {
                Some some = (Some)option;
                Internal.Consumer consumer = (Internal.Consumer)some.value();
                this.needWakeup().$minus$eq(BoxesRunTime.boxToLong(id));
                consumer.callback().invoke(PartitionHub$Internal$Wakeup$.MODULE$);
                return;
            }
            throw new MatchError(option);
        }

        @Override
        public void onUpstreamFinish() {
            if (this.consumerInfo().consumers().isEmpty()) {
                this.completeStage();
                return;
            }
            this.consumerInfo().consumers().foreach((Function1<Internal.Consumer, Object> & Serializable)c -> {
                this.complete(c.id());
                return BoxedUnit.UNIT;
            });
        }

        private void complete(long id) {
            this.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$queue().offer(id, PartitionHub$Internal$Completed$.MODULE$);
            this.wakeup(id);
        }

        private void tryPull() {
            if (this.initialized() && !this.isClosed(this.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$$outer().in()) && !this.hasBeenPulled(this.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$$outer().in()) && !this.isFull()) {
                this.pull(this.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$$outer().in());
                return;
            }
        }

        private void onEvent(Internal.HubEvent ev) {
            this.callbackCount_$eq(this.callbackCount() + 1L);
            Internal.HubEvent hubEvent = ev;
            if (hubEvent instanceof Internal.NeedWakeup) {
                Internal.NeedWakeup needWakeup = (Internal.NeedWakeup)hubEvent;
                Internal.Consumer consumer2 = needWakeup.consumer();
                if (this.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$queue().nonEmpty(consumer2.id())) {
                    consumer2.callback().invoke(PartitionHub$Internal$Wakeup$.MODULE$);
                    return;
                }
                this.needWakeup().update(consumer2.id(), consumer2);
                this.tryPull();
                return;
            }
            if (PartitionHub$Internal$TryPull$.MODULE$.equals(hubEvent)) {
                this.tryPull();
                return;
            }
            if (PartitionHub$Internal$RegistrationPending$.MODULE$.equals(hubEvent)) {
                ((Internal.Open)this.state().getAndSet(this.noRegistrationsState())).registrations().foreach((Function1<Internal.Consumer, Object> & Serializable)consumer -> {
                    PartitionSinkLogic.$anonfun$onEvent$4(this, consumer);
                    return BoxedUnit.UNIT;
                });
                return;
            }
            if (hubEvent instanceof Internal.UnRegister) {
                Internal.UnRegister unRegister = (Internal.UnRegister)hubEvent;
                long id = unRegister.id();
                Vector newConsumers = (Vector)this.consumerInfo().consumers().filterNot((Function1<Internal.Consumer, Object> & Serializable)x$8 -> BoxesRunTime.boxToBoolean(PartitionSinkLogic.$anonfun$onEvent$7(id, x$8)));
                this.consumerInfo_$eq(new ConsumerInfoImpl(this, newConsumers));
                this.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$queue().remove(id);
                if (newConsumers.isEmpty()) {
                    if (this.isClosed(this.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$$outer().in())) {
                        this.completeStage();
                        return;
                    }
                    return;
                }
                this.tryPull();
                return;
            }
            throw new MatchError(hubEvent);
        }

        @Override
        public void onUpstreamFailure(Throwable ex) {
            Internal.HubCompleted failMessage = new Internal.HubCompleted(new Some<Throwable>(ex));
            ((Internal.Open)this.state().getAndSet(new Internal.Closed(new Some<Throwable>(ex)))).registrations().foreach((Function1<Internal.Consumer, Object> & Serializable)consumer -> {
                PartitionSinkLogic.$anonfun$onUpstreamFailure$4(failMessage, consumer);
                return BoxedUnit.UNIT;
            });
            this.consumerInfo().consumers().foreach((Function1<Internal.Consumer, Object> & Serializable)consumer -> {
                PartitionSinkLogic.$anonfun$onUpstreamFailure$5(failMessage, consumer);
                return BoxedUnit.UNIT;
            });
            this.failStage(ex);
        }

        @Override
        public void postStop() {
            this.tryClose$2();
        }

        public Object poll(long id, AsyncCallback<Internal.HubEvent> hubCallback) {
            block0: {
                if (this.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$queue().totalSize() != this.DemandThreshold()) break block0;
                hubCallback.invoke(PartitionHub$Internal$TryPull$.MODULE$);
            }
            return this.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$queue().poll(id);
        }

        public /* synthetic */ PartitionHub akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$$outer() {
            return PartitionHub.this;
        }

        public static final /* synthetic */ void $anonfun$onEvent$4(PartitionSinkLogic $this, Internal.Consumer consumer) {
            Vector newConsumers = (Vector)((SeqOps)$this.consumerInfo().consumers().$colon$plus(consumer)).sortBy((Function1<Internal.Consumer, Object> & Serializable)x$7 -> BoxesRunTime.boxToLong(x$7.id()), Ordering$Long$.MODULE$);
            $this.consumerInfo_$eq(new ConsumerInfoImpl($this, newConsumers));
            $this.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$queue().init(consumer.id());
            if (newConsumers.size() >= $this.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$$outer().akka$stream$scaladsl$PartitionHub$$startAfterNrOfConsumers) {
                $this.initialized_$eq(true);
            }
            consumer.callback().invoke(PartitionHub$Internal$Initialize$.MODULE$);
            if ($this.initialized() && $this.pending().nonEmpty()) {
                $this.pending().foreach((Function1<Object, Object> & Serializable)elem -> {
                    $this.publish(elem);
                    return BoxedUnit.UNIT;
                });
                $this.pending_$eq((Vector)package$.MODULE$.Vector().empty());
            }
            $this.tryPull();
        }

        public static final /* synthetic */ boolean $anonfun$onEvent$7(long id$3, Internal.Consumer x$8) {
            return x$8.id() == id$3;
        }

        public static final /* synthetic */ void $anonfun$onUpstreamFailure$4(Internal.HubCompleted failMessage$2, Internal.Consumer consumer) {
            consumer.callback().invoke(failMessage$2);
        }

        public static final /* synthetic */ void $anonfun$onUpstreamFailure$5(Internal.HubCompleted failMessage$2, Internal.Consumer consumer) {
            consumer.callback().invoke(failMessage$2);
        }

        public static final /* synthetic */ void $anonfun$postStop$2(Internal.HubCompleted completedMessage$2, Internal.Consumer consumer) {
            consumer.callback().invoke(completedMessage$2);
        }

        private final void tryClose$2() {
            block2: {
                Internal.HubState hubState;
                block3: {
                    Internal.Open open;
                    do {
                        if ((hubState = this.state().get()) instanceof Internal.Closed) {
                            break block2;
                        }
                        if (!(hubState instanceof Internal.Open)) break block3;
                        open = (Internal.Open)hubState;
                    } while (!this.state().compareAndSet(open, new Internal.Closed(None$.MODULE$)));
                    Internal.HubCompleted completedMessage = new Internal.HubCompleted(None$.MODULE$);
                    open.registrations().foreach((Function1<Internal.Consumer, Object> & Serializable)consumer -> {
                        PartitionSinkLogic.$anonfun$postStop$2(completedMessage, consumer);
                        return BoxedUnit.UNIT;
                    });
                    break block2;
                }
                throw new MatchError(hubState);
            }
        }

        public PartitionSinkLogic(SinkShape<T> _shape) {
            if (PartitionHub.this == null) {
                throw null;
            }
            super(_shape);
            InHandler.$init$(this);
            this.DemandThreshold = PartitionHub.this.akka$stream$scaladsl$PartitionHub$$bufferSize / 2 + PartitionHub.this.akka$stream$scaladsl$PartitionHub$$bufferSize % 2;
            this.materializedPartitioner = PartitionHub.this.akka$stream$scaladsl$PartitionHub$$partitioner.apply();
            this.callbackPromise = Promise$.MODULE$.apply();
            this.noRegistrationsState = new Internal.Open(this.callbackPromise().future(), Nil$.MODULE$);
            this.state = new AtomicReference<Internal.Open>(this.noRegistrationsState());
            this.initialized = false;
            this.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$queue = PartitionHub.this.createQueue();
            this.pending = package$.MODULE$.Vector().empty();
            this.consumerInfo = new ConsumerInfoImpl(this, (Vector<Internal.Consumer>)package$.MODULE$.Vector().empty());
            this.needWakeup = LongMap$.MODULE$.empty();
            this.callbackCount = 0L;
            this.setHandler(PartitionHub.this.in(), this);
        }

        private final class ConsumerInfoImpl
        implements ConsumerInfo {
            private final Vector<Internal.Consumer> consumers;
            private final /* synthetic */ PartitionSinkLogic $outer;

            public Vector<Internal.Consumer> consumers() {
                return this.consumers;
            }

            @Override
            public int queueSize(long consumerId) {
                return this.$outer.akka$stream$scaladsl$PartitionHub$PartitionSinkLogic$$queue().size(consumerId);
            }

            @Override
            public int size() {
                return this.consumers().size();
            }

            @Override
            public IndexedSeq<Object> consumerIds() {
                return (IndexedSeq)this.consumers().map((Function1<Internal.Consumer, Object> & Serializable)x$6 -> BoxesRunTime.boxToLong(x$6.id()));
            }

            @Override
            public long consumerIdByIdx(int idx) {
                return ((Internal.Consumer)this.consumers().apply(idx)).id();
            }

            @Override
            public java.util.List<Object> getConsumerIds() {
                return new AbstractList<Object>(this){
                    private final /* synthetic */ ConsumerInfoImpl $outer;

                    public long get(int idx) {
                        return this.$outer.consumerIdByIdx(idx);
                    }

                    public int size() {
                        return this.$outer.size();
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                    }
                };
            }

            public ConsumerInfoImpl(PartitionSinkLogic $outer, Vector<Internal.Consumer> consumers) {
                this.consumers = consumers;
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        }
    }
}

