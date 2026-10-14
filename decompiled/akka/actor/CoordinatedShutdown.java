/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.Done;
import akka.Done$;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.actor.Cancellable$;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.CoordinatedShutdown$;
import akka.actor.CoordinatedShutdown$tasks$;
import akka.actor.CoordinatedShutdownTerminationWatcher;
import akka.actor.CoordinatedShutdownTerminationWatcher$;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.JVMShutdownHooks;
import akka.dispatch.MessageDispatcher;
import akka.event.LogSource$;
import akka.event.Logging$;
import akka.event.LoggingAdapter;
import akka.pattern.package$;
import akka.util.OptionVal$;
import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Set;
import scala.compat.java8.FutureConverters$;
import scala.compat.java8.FutureConverters$CompletionStageOps$;
import scala.compat.java8.FutureConverters$FutureOps$;
import scala.compat.java8.OptionConverters$;
import scala.compat.java8.OptionConverters$RichOptionForJava8$;
import scala.compat.java8.OptionConverters$RichOptionalGeneric$;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.Future$;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.concurrent.duration.Deadline;
import scala.concurrent.duration.Deadline$;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u001d\rr\u0001CAr\u0003KD\t!a<\u0007\u0011\u0005M\u0018Q\u001dE\u0001\u0003kDqAa#\u0002\t\u0003)\t\u000bC\u0005\u0006$\u0006\u0011\r\u0011\"\u0001\u0003P\"AQQU\u0001!\u0002\u0013\u0011\t\u000eC\u0005\u0006(\u0006\u0011\r\u0011\"\u0001\u0003P\"AQ\u0011V\u0001!\u0002\u0013\u0011\t\u000eC\u0005\u0006,\u0006\u0011\r\u0011\"\u0001\u0003P\"AQQV\u0001!\u0002\u0013\u0011\t\u000eC\u0005\u00060\u0006\u0011\r\u0011\"\u0001\u0003P\"AQ\u0011W\u0001!\u0002\u0013\u0011\t\u000eC\u0005\u00064\u0006\u0011\r\u0011\"\u0001\u0003P\"AQQW\u0001!\u0002\u0013\u0011\t\u000eC\u0005\u00068\u0006\u0011\r\u0011\"\u0001\u0003P\"AQ\u0011X\u0001!\u0002\u0013\u0011\t\u000eC\u0005\u0006<\u0006\u0011\r\u0011\"\u0001\u0003P\"AQQX\u0001!\u0002\u0013\u0011\t\u000eC\u0005\u0006@\u0006\u0011\r\u0011\"\u0001\u0003P\"AQ\u0011Y\u0001!\u0002\u0013\u0011\t\u000eC\u0005\u0006D\u0006\u0011\r\u0011\"\u0001\u0003P\"AQQY\u0001!\u0002\u0013\u0011\t\u000eC\u0005\u0006H\u0006\u0011\r\u0011\"\u0001\u0003P\"AQ\u0011Z\u0001!\u0002\u0013\u0011\t\u000eC\u0005\u0006L\u0006\u0011\r\u0011\"\u0001\u0003P\"AQQZ\u0001!\u0002\u0013\u0011\t\u000eC\u0005\u0006P\u0006\u0011\r\u0011\"\u0001\u0003P\"AQ\u0011[\u0001!\u0002\u0013\u0011\tNB\u0005\u0005v\u0005\u0001\n1%\u0001\u0005x\u001d9Q1[\u0001\t\u0002\u0016UgaBCl\u0003!\u0005U\u0011\u001c\u0005\b\u0005\u0017kB\u0011ACo\u0011%\u0011i-HA\u0001\n\u0003\u0012y\rC\u0005\u0003`v\t\t\u0011\"\u0001\u0003b\"I!\u0011^\u000f\u0002\u0002\u0013\u0005Qq\u001c\u0005\n\u0005ol\u0012\u0011!C!\u0005sD\u0011ba\u0002\u001e\u0003\u0003%\t!b9\t\u0013\rMQ$!A\u0005B\rU\u0001\"CB\f;\u0005\u0005I\u0011IB\r\u0011%)9/HA\u0001\n\u0013)I\u000fC\u0004\u0006r\u0006!\t!b=\b\u000f\u0015U\u0018\u0001#!\u0006x\u001a9Q\u0011`\u0001\t\u0002\u0016m\bb\u0002BFS\u0011\u0005QQ \u0005\n\u0005\u001bL\u0013\u0011!C!\u0005\u001fD\u0011Ba8*\u0003\u0003%\tA!9\t\u0013\t%\u0018&!A\u0005\u0002\u0015}\b\"\u0003B|S\u0005\u0005I\u0011\tB}\u0011%\u00199!KA\u0001\n\u00031\u0019\u0001C\u0005\u0004\u0014%\n\t\u0011\"\u0011\u0004\u0016!I1qC\u0015\u0002\u0002\u0013\u00053\u0011\u0004\u0005\n\u000bOL\u0013\u0011!C\u0005\u000bSDqAb\u0002\u0002\t\u0003)\u0019pB\u0004\u0007\n\u0005A\tIb\u0003\u0007\u000f\u00195\u0011\u0001#!\u0007\u0010!9!1R\u001b\u0005\u0002\u0019E\u0001\"\u0003Bgk\u0005\u0005I\u0011\tBh\u0011%\u0011y.NA\u0001\n\u0003\u0011\t\u000fC\u0005\u0003jV\n\t\u0011\"\u0001\u0007\u0014!I!q_\u001b\u0002\u0002\u0013\u0005#\u0011 \u0005\n\u0007\u000f)\u0014\u0011!C\u0001\r/A\u0011ba\u00056\u0003\u0003%\te!\u0006\t\u0013\r]Q'!A\u0005B\re\u0001\"CCtk\u0005\u0005I\u0011BCu\u0011\u001d1Y\"\u0001C\u0001\u000bg<qA\"\b\u0002\u0011\u00033yBB\u0004\u0007\"\u0005A\tIb\t\t\u000f\t-\u0015\t\"\u0001\u0007&!I!QZ!\u0002\u0002\u0013\u0005#q\u001a\u0005\n\u0005?\f\u0015\u0011!C\u0001\u0005CD\u0011B!;B\u0003\u0003%\tAb\n\t\u0013\t]\u0018)!A\u0005B\te\b\"CB\u0004\u0003\u0006\u0005I\u0011\u0001D\u0016\u0011%\u0019\u0019\"QA\u0001\n\u0003\u001a)\u0002C\u0005\u0004\u0018\u0005\u000b\t\u0011\"\u0011\u0004\u001a!IQq]!\u0002\u0002\u0013%Q\u0011\u001e\u0005\b\r_\tA\u0011ACz\u000f\u001d1\t$\u0001EA\rg1qA\"\u000e\u0002\u0011\u000339\u0004C\u0004\u0003\f6#\tA\"\u000f\t\u0013\t5W*!A\u0005B\t=\u0007\"\u0003Bp\u001b\u0006\u0005I\u0011\u0001Bq\u0011%\u0011I/TA\u0001\n\u00031Y\u0004C\u0005\u0003x6\u000b\t\u0011\"\u0011\u0003z\"I1qA'\u0002\u0002\u0013\u0005aq\b\u0005\n\u0007'i\u0015\u0011!C!\u0007+A\u0011ba\u0006N\u0003\u0003%\te!\u0007\t\u0013\u0015\u001dX*!A\u0005\n\u0015%\bb\u0002D\"\u0003\u0011\u0005Q1_\u0004\b\r\u000b\n\u0001\u0012\u0011D$\r\u001d1I%\u0001EA\r\u0017BqAa#Z\t\u00031i\u0005C\u0005\u0003Nf\u000b\t\u0011\"\u0011\u0003P\"I!q\\-\u0002\u0002\u0013\u0005!\u0011\u001d\u0005\n\u0005SL\u0016\u0011!C\u0001\r\u001fB\u0011Ba>Z\u0003\u0003%\tE!?\t\u0013\r\u001d\u0011,!A\u0005\u0002\u0019M\u0003\"CB\n3\u0006\u0005I\u0011IB\u000b\u0011%\u00199\"WA\u0001\n\u0003\u001aI\u0002C\u0005\u0006hf\u000b\t\u0011\"\u0003\u0006j\"9aqK\u0001\u0005\u0002\u0015Mxa\u0002D-\u0003!\u0005e1\f\u0004\b\r;\n\u0001\u0012\u0011D0\u0011\u001d\u0011Y)\u001aC\u0001\rCB\u0011B!4f\u0003\u0003%\tEa4\t\u0013\t}W-!A\u0005\u0002\t\u0005\b\"\u0003BuK\u0006\u0005I\u0011\u0001D2\u0011%\u001190ZA\u0001\n\u0003\u0012I\u0010C\u0005\u0004\b\u0015\f\t\u0011\"\u0001\u0007h!I11C3\u0002\u0002\u0013\u00053Q\u0003\u0005\n\u0007/)\u0017\u0011!C!\u00073A\u0011\"b:f\u0003\u0003%I!\";\t\u000f\u0019-\u0014\u0001\"\u0001\u0006t\"IaQN\u0001A\u0002\u0013%!Q\u0010\u0005\n\r_\n\u0001\u0019!C\u0005\rcB\u0001B\"\u001e\u0002A\u0003&!q\u0010\u0005\b\t\u0017\nA\u0011\tD=\u0011\u001d!Y%\u0001C!\r\u0007CqA\"$\u0002\t\u00032y\tC\u0004\u0007\u0012\u0006!\tEb%\t\u0013\u0019]\u0015\u0001\"\u0001\u0002j\u001ae\u0005\"\u0003Db\u0003\u0011\u0005\u0011\u0011\u001eDc\u0011\u001d1\t.\u0001C\u0005\r'DqAb7\u0002\t\u00131iN\u0002\u0005\u0003>\u0005\u0011\u0015\u0011\u001eB \u0011)\u0011If\u001fBK\u0002\u0013\u0005!1\f\u0005\u000b\u0005GZ(\u0011#Q\u0001\n\tu\u0003B\u0003B3w\nU\r\u0011\"\u0001\u0003h!Q!\u0011P>\u0003\u0012\u0003\u0006IA!\u001b\t\u0015\tm4P!f\u0001\n\u0003\u0011i\b\u0003\u0006\u0003\u0006n\u0014\t\u0012)A\u0005\u0005\u007fB!Ba\"|\u0005+\u0007I\u0011\u0001B?\u0011)\u0011Ii\u001fB\tB\u0003%!q\u0010\u0005\b\u0005\u0017[H\u0011\u0001BG\u0011%\u0011Yj_A\u0001\n\u0003\u0011i\nC\u0005\u0003(n\f\n\u0011\"\u0001\u0003*\"I!qX>\u0012\u0002\u0013\u0005!\u0011\u0019\u0005\n\u0005\u000b\\\u0018\u0013!C\u0001\u0005\u000fD\u0011Ba3|#\u0003%\tAa2\t\u0013\t570!A\u0005B\t=\u0007\"\u0003Bpw\u0006\u0005I\u0011\u0001Bq\u0011%\u0011Io_A\u0001\n\u0003\u0011Y\u000fC\u0005\u0003xn\f\t\u0011\"\u0011\u0003z\"I1qA>\u0002\u0002\u0013\u00051\u0011\u0002\u0005\n\u0007\u001bY\u0018\u0011!C!\u0007\u001fA\u0011ba\u0005|\u0003\u0003%\te!\u0006\t\u0013\r]10!A\u0005B\re\u0001\"CB\u000ew\u0006\u0005I\u0011IB\u000f\u000f-1)/AA\u0001\u0012\u0003\tIOb:\u0007\u0017\tu\u0012!!A\t\u0002\u0005%h\u0011\u001e\u0005\t\u0005\u0017\u000bI\u0003\"\u0001\u0007x\"Q1qCA\u0015\u0003\u0003%)e!\u0007\t\u0015\u0011\r\u0011\u0011FA\u0001\n\u00033I\u0010\u0003\u0006\u0005>\u0005%\u0012\u0011!CA\u000f\u0007A!\"b:\u0002*\u0005\u0005I\u0011BCu\u0011%9y!\u0001C\u0001\u0003S<\t\u0002C\u0005\b\u0018\u0005!\t!!;\b\u001a!YqQD\u0001\u0012\u0002\u0013\u0005\u0011\u0011^D\u0010\r\u001d\t\u00190!:\u0003\u0005\u0017A1Ba\u0005\u0002<\t\u0005\t\u0015!\u0003\u0003\u0016!Y!1DA\u001e\u0005\u0003\u0005\u000b\u0011\u0002B\u000f\u0011-\u0019\t#a\u000f\u0003\u0002\u0003\u0006Iaa\t\t\u0015\t-\u00151\bC\u0001\u0003S\u001cI\u0003\u0003\u0007\u00042\u0005m\"\u0019!C\u0001\u0003S\u001c\u0019\u0004C\u0005\u0004B\u0005m\u0002\u0015!\u0003\u00046!Q11IA\u001e\u0005\u0004%Ia!\u0012\t\u0013\rE\u00131\bQ\u0001\n\r\u001d\u0003\u0002DB*\u0003w\u0011\r\u0011\"\u0001\u0002j\u000eU\u0003\"CB/\u0003w\u0001\u000b\u0011BB,\r)\u0019y&a\u000f\u0011\u0002G%1\u0011\r\u0005\t\u0007G\n\tF\"\u0001\u0003b\"A1QMA)\r\u0003\u00199g\u0002\u0005\u0004\n\u0006m\u0002\u0012BBF\r!\u0019y)a\u000f\t\n\rE\u0005\u0002\u0003BF\u00033\"\taa%\t\u0015\rU\u0015\u0011\fb\u0001\n\u0013\u00199\nC\u0005\u0004z\u0006e\u0003\u0015!\u0003\u0004\u001a\u001aQ1qWA-!\u0003\rJa!/\t\u0015\r\u0015\u0014\u0011\rD\u0001\u00033\u001a\tm\u0002\u0005\u0004|\u0006e\u0003\u0012BB\u007f\r!\u00199,!\u0017\t\n\r}\b\u0002\u0003BF\u0003O\"\t\u0001\"\u0001\t\u0011\u0011\r\u0011q\rC\u0001\t\u000b1qaa+\u0002Z\u0011\u001bi\u000bC\u0006\u0004\n\u00065$Q3A\u0005\u0002\rE\u0006bCBe\u0003[\u0012\t\u0012)A\u0005\u0007gC\u0001Ba#\u0002n\u0011\u000511\u001a\u0005\u000b\u0007G\niG1A\u0005B\t\u0005\b\"CBh\u0003[\u0002\u000b\u0011\u0002Br\u0011!\u0019)'!\u001c\u0005B\rE\u0007\u0002CBm\u0003[\"\taa7\t\u0015\tm\u0015QNA\u0001\n\u0003\u0019\t\u000f\u0003\u0006\u0003(\u00065\u0014\u0013!C\u0001\u0007KD!B!4\u0002n\u0005\u0005I\u0011\tBh\u0011)\u0011y.!\u001c\u0002\u0002\u0013\u0005!\u0011\u001d\u0005\u000b\u0005S\fi'!A\u0005\u0002\r%\bB\u0003B|\u0003[\n\t\u0011\"\u0011\u0003z\"Q1qAA7\u0003\u0003%\ta!<\t\u0015\r5\u0011QNA\u0001\n\u0003\u001a\t\u0010\u0003\u0006\u0004\u0014\u00055\u0014\u0011!C!\u0007+A!ba\u0006\u0002n\u0005\u0005I\u0011IB\r\u0011)\u0019Y\"!\u001c\u0002\u0002\u0013\u00053Q_\u0004\t\t3\tI\u0006#\u0003\u0005\u001c\u0019A11VA-\u0011\u0013!i\u0002\u0003\u0005\u0003\f\u0006UE\u0011\u0001C\u0015\u0011!!Y#!&\u0005\u0002\u00115\u0002B\u0003C\u001a\u0003+\u0013\r\u0011\"\u0001\u00056!IAqGAKA\u0003%1q\u0015\u0005\u000b\t\u0007\t)*!A\u0005\u0002\u0012e\u0002B\u0003C\u001f\u0003+\u000b\t\u0011\"!\u0005@!AA1JA-\t\u0003!i\u0005\u0003\u0005\u0005T\u0005eC\u0011\u0001C+\u0011!!9&!\u0017\u0005\u0002\u0011e\u0003B\u0003C1\u0003w\u0011\r\u0011\"\u0003\u0005d!IA\u0011PA\u001eA\u0003%AQ\r\u0005\u000b\tw\nYD1A\u0005\n\u0011u\u0004\"\u0003CC\u0003w\u0001\u000b\u0011\u0002C@\u0011)!9)a\u000fC\u0002\u0013%A\u0011\u0012\u0005\n\t'\u000bY\u0004)A\u0005\t\u0017C!\u0002\"&\u0002<\u0001\u0007I\u0011\u0002CL\u0011)!\u0019+a\u000fA\u0002\u0013%AQ\u0015\u0005\n\t_\u000bY\u0004)Q\u0005\t3CQ\u0002\"/\u0002<!\u0015\r\u0011\"\u0001\u0002j\u0012m\u0006B\u0003Cb\u0003w!\t!!;\u0005F\"AAqYA\u001e\t\u0003!I\r\u0003\u0005\u0005H\u0006mB\u0011\u0001Cl\u0011!!\t0a\u000f\u0005\u0002\u0011M\b\u0002\u0003Cy\u0003w!\t\u0001\"@\t\u0011\u0015\u0015\u00111\bC\u0001\u000b\u000fA\u0001\"\"\u0002\u0002<\u0011\u0005QQ\u0003\u0005\t\u000bO\tY\u0004\"\u0001\u0006*!AQ1FA\u001e\t\u0003)i\u0003\u0003\u0005\u0004f\u0005mB\u0011AC\u0019\u0011!)9$a\u000f\u0005\u0002\u0015e\u0002\u0002CB3\u0003w!\t!\"\u0010\t\u0011\r\u0015\u00141\bC\u0001\u000b\u000fB\u0001B!\u001a\u0002<\u0011\u0005Qq\n\u0005\t\u000b'\nY\u0004\"\u0001\u0005V!AQQKA\u001e\t\u0003)9\u0006\u0003\u0005\u0006v\u0005mB\u0011AC<\u0011!))&a\u000f\u0005\u0002\u00155\u0005\u0002CC;\u0003w!\t!b&\u0002'\r{wN\u001d3j]\u0006$X\rZ*ikR$wn\u001e8\u000b\t\u0005\u001d\u0018\u0011^\u0001\u0006C\u000e$xN\u001d\u0006\u0003\u0003W\fA!Y6lC\u000e\u0001\u0001cAAy\u00035\u0011\u0011Q\u001d\u0002\u0014\u0007>|'\u000fZ5oCR,Gm\u00155vi\u0012|wO\\\n\b\u0003\u0005](1ACN!\u0011\tI0a@\u000e\u0005\u0005m(BAA\u007f\u0003\u0015\u00198-\u00197b\u0013\u0011\u0011\t!a?\u0003\r\u0005s\u0017PU3g!\u0019\t\tP!\u0002\u0003\n%!!qAAs\u0005-)\u0005\u0010^3og&|g.\u00133\u0011\t\u0005E\u00181H\n\u0007\u0003w\t9P!\u0004\u0011\t\u0005E(qB\u0005\u0005\u0005#\t)OA\u0005FqR,gn]5p]\u000611/_:uK6\u0004B!!=\u0003\u0018%!!\u0011DAs\u0005M)\u0005\u0010^3oI\u0016$\u0017i\u0019;peNK8\u000f^3n\u0003\u0019\u0001\b.Y:fgBA!q\u0004B\u0017\u0005g\u0011ID\u0004\u0003\u0003\"\t%\u0002\u0003\u0002B\u0012\u0003wl!A!\n\u000b\t\t\u001d\u0012Q^\u0001\u0007yI|w\u000e\u001e \n\t\t-\u00121`\u0001\u0007!J,G-\u001a4\n\t\t=\"\u0011\u0007\u0002\u0004\u001b\u0006\u0004(\u0002\u0002B\u0016\u0003w\u0004BAa\b\u00036%!!q\u0007B\u0019\u0005\u0019\u0019FO]5oOB\u0019!1H>\u000f\u0007\u0005E\bAA\u0003QQ\u0006\u001cXmE\u0004|\u0003o\u0014\tEa\u0012\u0011\t\u0005e(1I\u0005\u0005\u0005\u000b\nYPA\u0004Qe>$Wo\u0019;\u0011\t\t%#1\u000b\b\u0005\u0005\u0017\u0012yE\u0004\u0003\u0003$\t5\u0013BAA\u007f\u0013\u0011\u0011\t&a?\u0002\u000fA\f7m[1hK&!!Q\u000bB,\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\u0011\u0011\t&a?\u0002\u0013\u0011,\u0007/\u001a8eg>sWC\u0001B/!\u0019\u0011yBa\u0018\u00034%!!\u0011\rB\u0019\u0005\r\u0019V\r^\u0001\u000bI\u0016\u0004XM\u001c3t\u001f:\u0004\u0013a\u0002;j[\u0016|W\u000f^\u000b\u0003\u0005S\u0002BAa\u001b\u0003v5\u0011!Q\u000e\u0006\u0005\u0005_\u0012\t(\u0001\u0005ekJ\fG/[8o\u0015\u0011\u0011\u0019(a?\u0002\u0015\r|gnY;se\u0016tG/\u0003\u0003\u0003x\t5$A\u0004$j]&$X\rR;sCRLwN\\\u0001\ti&lWm\\;uA\u00059!/Z2pm\u0016\u0014XC\u0001B@!\u0011\tIP!!\n\t\t\r\u00151 \u0002\b\u0005>|G.Z1o\u0003!\u0011XmY8wKJ\u0004\u0013aB3oC\ndW\rZ\u0001\tK:\f'\r\\3eA\u00051A(\u001b8jiz\"\"Ba$\u0003\u0014\nU%q\u0013BM!\r\u0011\tj_\u0007\u0002\u0003!A!\u0011LA\u0005\u0001\u0004\u0011i\u0006\u0003\u0005\u0003f\u0005%\u0001\u0019\u0001B5\u0011!\u0011Y(!\u0003A\u0002\t}\u0004\u0002\u0003BD\u0003\u0013\u0001\rAa \u0002\t\r|\u0007/\u001f\u000b\u000b\u0005\u001f\u0013yJ!)\u0003$\n\u0015\u0006B\u0003B-\u0003\u0017\u0001\n\u00111\u0001\u0003^!Q!QMA\u0006!\u0003\u0005\rA!\u001b\t\u0015\tm\u00141\u0002I\u0001\u0002\u0004\u0011y\b\u0003\u0006\u0003\b\u0006-\u0001\u0013!a\u0001\u0005\u007f\nabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0002\u0003,*\"!Q\fBWW\t\u0011y\u000b\u0005\u0003\u00032\nmVB\u0001BZ\u0015\u0011\u0011)La.\u0002\u0013Ut7\r[3dW\u0016$'\u0002\u0002B]\u0003w\f!\"\u00198o_R\fG/[8o\u0013\u0011\u0011iLa-\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0005\t\r'\u0006\u0002B5\u0005[\u000babY8qs\u0012\"WMZ1vYR$3'\u0006\u0002\u0003J*\"!q\u0010BW\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIQ\nQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXC\u0001Bi!\u0011\u0011\u0019N!8\u000e\u0005\tU'\u0002\u0002Bl\u00053\fA\u0001\\1oO*\u0011!1\\\u0001\u0005U\u00064\u0018-\u0003\u0003\u00038\tU\u0017\u0001\u00049s_\u0012,8\r^!sSRLXC\u0001Br!\u0011\tIP!:\n\t\t\u001d\u00181 \u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0005[\u0014\u0019\u0010\u0005\u0003\u0002z\n=\u0018\u0002\u0002By\u0003w\u00141!\u00118z\u0011)\u0011)0!\u0007\u0002\u0002\u0003\u0007!1]\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\tm\bC\u0002B\u007f\u0007\u0007\u0011i/\u0004\u0002\u0003\u0000*!1\u0011AA~\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0007\u000b\u0011yP\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003\u0002B@\u0007\u0017A!B!>\u0002\u001e\u0005\u0005\t\u0019\u0001Bw\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\tE7\u0011\u0003\u0005\u000b\u0005k\fy\"!AA\u0002\t\r\u0018\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\t\r\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\tE\u0017AB3rk\u0006d7\u000f\u0006\u0003\u0003\u0000\r}\u0001B\u0003B{\u0003K\t\t\u00111\u0001\u0003n\u0006\u0001\"N^7TQV$Hm\\<o\u0011>|7n\u001d\t\u0005\u0003c\u001c)#\u0003\u0003\u0004(\u0005\u0015(\u0001\u0005&W\u001bNCW\u000f\u001e3po:Dun\\6t)!\u0011Iaa\u000b\u0004.\r=\u0002\u0002\u0003B\n\u0003\u0007\u0002\rA!\u0006\t\u0011\tm\u00111\ta\u0001\u0005;A!b!\t\u0002DA\u0005\t\u0019AB\u0012\u0003\rawnZ\u000b\u0003\u0007k\u0001Baa\u000e\u0004>5\u00111\u0011\b\u0006\u0005\u0007w\tI/A\u0003fm\u0016tG/\u0003\u0003\u0004@\re\"A\u0004'pO\u001eLgnZ!eCB$XM]\u0001\u0005Y><\u0007%A\u0006l]><h\u000e\u00155bg\u0016\u001cXCAB$!\u0019\u0019Iea\u0014\u000345\u001111\n\u0006\u0005\u0007\u001b\u0012y0A\u0005j[6,H/\u00192mK&!!\u0011MB&\u00031Ygn\\<o!\"\f7/Z:!\u00035y'\u000fZ3sK\u0012\u0004\u0006.Y:fgV\u00111q\u000b\t\u0007\u0005\u0013\u001aIFa\r\n\t\rm#q\u000b\u0002\u0005\u0019&\u001cH/\u0001\bpe\u0012,'/\u001a3QQ\u0006\u001cXm\u001d\u0011\u0003\u001fAC\u0017m]3EK\u001aLg.\u001b;j_:\u001cB!!\u0015\u0002x\u0006!1/\u001b>f\u0003\r\u0011XO\u001c\u000b\u0005\u0007S\u001a)\t\u0006\u0003\u0004l\rm\u0004CBB7\u0007_\u001a\u0019(\u0004\u0002\u0003r%!1\u0011\u000fB9\u0005\u00191U\u000f^;sKB!1QOB<\u001b\t\tI/\u0003\u0003\u0004z\u0005%(\u0001\u0002#p]\u0016D\u0001b! \u0002V\u0001\u000f1qP\u0001\u0003K\u000e\u0004Ba!\u001c\u0004\u0002&!11\u0011B9\u0005A)\u00050Z2vi&|gnQ8oi\u0016DH\u000f\u0003\u0005\u0004\b\u0006U\u0003\u0019\u0001B@\u00039\u0011XmY8wKJ,e.\u00192mK\u0012\fQ\u0001^1tWN\u0004Ba!$\u0002Z5\u0011\u00111\b\u0002\u0006i\u0006\u001c8n]\n\u0005\u00033\n9\u0010\u0006\u0002\u0004\f\u0006\u0001\"/Z4jgR,'/\u001a3QQ\u0006\u001cXm]\u000b\u0003\u00073\u0003\u0002ba'\u0004$\nM2qU\u0007\u0003\u0007;SAAa\u001d\u0004 *!1\u0011\u0015Bm\u0003\u0011)H/\u001b7\n\t\r\u00156Q\u0014\u0002\u0012\u0007>t7-\u001e:sK:$\b*Y:i\u001b\u0006\u0004\b\u0003BBU\u0003[j!!!\u0017\u0003+M#(/[2u!\"\f7/\u001a#fM&t\u0017\u000e^5p]NQ\u0011QNA|\u0007_\u0013\tEa\u0012\u0011\t\r5\u0015\u0011K\u000b\u0003\u0007g\u0003bAa\b\u0003`\rU\u0006\u0003BBU\u0003C\u0012a\u0002V1tW\u0012+g-\u001b8ji&|gn\u0005\u0004\u0002b\u0005]81\u0018\t\u0005\u0003c\u001ci,\u0003\u0003\u0004@\u0006\u0015(aC\"b]\u000e,G\u000e\\1cY\u0016$Baa1\u0004HR!11NBc\u0011!\u0019i(a\u0019A\u0004\r}\u0004\u0002CBD\u0003G\u0002\rAa \u0002\rQ\f7o[:!)\u0011\u00199k!4\t\u0011\r%\u00151\u000fa\u0001\u0007g\u000bQa]5{K\u0002\"Baa5\u0004XR!11NBk\u0011!\u0019i(!\u001fA\u0004\r}\u0004\u0002CBD\u0003s\u0002\rAa \u0002\u000b5,'oZ3\u0015\t\r\u001d6Q\u001c\u0005\t\u0007?\fY\b1\u0001\u0004(\u0006)q\u000e\u001e5feR!1qUBr\u0011)\u0019I)! \u0011\u0002\u0003\u000711W\u000b\u0003\u0007OTCaa-\u0003.R!!Q^Bv\u0011)\u0011)0!\"\u0002\u0002\u0003\u0007!1\u001d\u000b\u0005\u0005\u007f\u001ay\u000f\u0003\u0006\u0003v\u0006%\u0015\u0011!a\u0001\u0005[$BA!5\u0004t\"Q!Q_AF\u0003\u0003\u0005\rAa9\u0015\t\t}4q\u001f\u0005\u000b\u0005k\f\t*!AA\u0002\t5\u0018!\u0005:fO&\u001cH/\u001a:fIBC\u0017m]3tA\u0005qA+Y:l\t\u00164\u0017N\\5uS>t\u0007\u0003BBU\u0003O\u001aB!a\u001a\u0002xR\u00111Q`\u0001\u0006CB\u0004H.\u001f\u000b\t\u0007k#9\u0001b\u0003\u0005\u0016!AA\u0011BA6\u0001\u0004\u0011\u0019$A\u0005qQ\u0006\u001cXMT1nK\"AAQBA6\u0001\u0004!y!\u0001\u0003uCN\\\u0007CBA}\t#\u0019Y'\u0003\u0003\u0005\u0014\u0005m(!\u0003$v]\u000e$\u0018n\u001c81\u0011!!9\"a\u001bA\u0002\tM\u0012\u0001\u00028b[\u0016\fQc\u0015;sS\u000e$\b\u000b[1tK\u0012+g-\u001b8ji&|g\u000e\u0005\u0003\u0004*\u0006U5CBAK\u0003o$y\u0002\u0005\u0003\u0005\"\u0011\u001dRB\u0001C\u0012\u0015\u0011!)C!7\u0002\u0005%|\u0017\u0002\u0002B+\tG!\"\u0001b\u0007\u0002\rMLgn\u001a7f)\u0011\u00199\u000bb\f\t\u0011\u0011E\u0012\u0011\u0014a\u0001\u0007k\u000ba\u0002^1tW\u0012+g-\u001b8ji&|g.A\u0003f[B$\u00180\u0006\u0002\u0004(\u00061Q-\u001c9us\u0002\"Baa*\u0005<!A1\u0011RAP\u0001\u0004\u0019\u0019,A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0011\u0005Cq\t\t\u0007\u0003s$\u0019ea-\n\t\u0011\u0015\u00131 \u0002\u0007\u001fB$\u0018n\u001c8\t\u0015\u0011%\u0013\u0011UA\u0001\u0002\u0004\u00199+A\u0002yIA\n1aZ3u)\u0011!y\u0005\"\u0015\u0011\r\u0005eH1IBX\u0011!!I!a)A\u0002\tM\u0012!\u0004;pi\u0006dG)\u001e:bi&|g\u000e\u0006\u0002\u0003j\u0005A!/Z4jgR,'\u000f\u0006\u0005\u0004<\u0012mCQ\fC0\u0011!!I!a*A\u0002\tM\u0002\u0002\u0003C\u0007\u0003O\u0003\r\u0001b\u0004\t\u0011\u0011]\u0011q\u0015a\u0001\u0005g\t!B];o'R\f'\u000f^3e+\t!)\u0007\u0005\u0004\u0005h\u00115D\u0011O\u0007\u0003\tSRA\u0001b\u001b\u0004\u001e\u00061\u0011\r^8nS\u000eLA\u0001b\u001c\u0005j\ty\u0011\t^8nS\u000e\u0014VMZ3sK:\u001cW\r\u0005\u0004\u0002z\u0012\rC1\u000f\t\u0004\u0005wY\"A\u0002*fCN|gnE\u0002\u001c\u0003o\f1B];o'R\f'\u000f^3eA\u0005Q!/\u001e8Qe>l\u0017n]3\u0016\u0005\u0011}\u0004CBB7\t\u0003\u001b\u0019(\u0003\u0003\u0005\u0004\nE$a\u0002)s_6L7/Z\u0001\feVt\u0007K]8nSN,\u0007%\u0001\b`UZl\u0007j\\8lg2\u000bGo\u00195\u0016\u0005\u0011-\u0005C\u0002C4\t[\"i\t\u0005\u0003\u0004\u001c\u0012=\u0015\u0002\u0002CI\u0007;\u0013abQ8v]R$un\u001e8MCR\u001c\u0007.A\b`UZl\u0007j\\8lg2\u000bGo\u00195!\u0003I\t7\r^8s'f\u001cH/Z7Km6Dun\\6\u0016\u0005\u0011e\u0005C\u0002CN\t?\u001bY,\u0004\u0002\u0005\u001e*!1\u0011UAu\u0013\u0011!\t\u000b\"(\u0003\u0013=\u0003H/[8o-\u0006d\u0017AF1di>\u00148+_:uK6Te/\u001c%p_.|F%Z9\u0015\t\u0011\u001dFQ\u0016\t\u0005\u0003s$I+\u0003\u0003\u0005,\u0006m(\u0001B+oSRD!B!>\u00028\u0006\u0005\t\u0019\u0001CM\u0003M\t7\r^8s'f\u001cH/Z7Km6Dun\\6!Q\u0011\tI\fb-\u0011\t\u0005eHQW\u0005\u0005\to\u000bYP\u0001\u0005w_2\fG/\u001b7f\u0003I!XM]7j]\u0006$\u0018n\u001c8XCR\u001c\u0007.\u001a:\u0016\u0005\u0011u\u0006\u0003BAy\t\u007fKA\u0001\"1\u0002f\nA\u0011i\u0019;peJ+g-A\u0007km6Dun\\6t\u0019\u0006$8\r[\u000b\u0003\t\u001b\u000b!#\u00193e\u0007\u0006t7-\u001a7mC\ndW\rV1tWR1A1\u001aCh\t'$Baa/\u0005N\"AAQBA`\u0001\u0004!y\u0001\u0003\u0005\u0005R\u0006}\u0006\u0019\u0001B\u001a\u0003\u0015\u0001\b.Y:f\u0011!!).a0A\u0002\tM\u0012\u0001\u0003;bg.t\u0015-\\3\u0015\u0011\rmF\u0011\u001cCn\t;D\u0001\u0002\"5\u0002B\u0002\u0007!1\u0007\u0005\t\t+\f\t\r1\u0001\u00034!AAQBAa\u0001\u0004!y\u000e\u0005\u0004\u0005b\u0012\u001dH1^\u0007\u0003\tGTA\u0001\":\u0004 \u0006Aa-\u001e8di&|g.\u0003\u0003\u0005j\u0012\r(\u0001C*vaBd\u0017.\u001a:\u0011\r\rmEQ^B:\u0013\u0011!yo!(\u0003\u001f\r{W\u000e\u001d7fi&|gn\u0015;bO\u0016\fq!\u00193e)\u0006\u001c8\u000e\u0006\u0004\u0005v\u0012eH1 \u000b\u0005\tO#9\u0010\u0003\u0005\u0005\u000e\u0005\r\u0007\u0019\u0001C\b\u0011!!\t.a1A\u0002\tM\u0002\u0002\u0003Ck\u0003\u0007\u0004\rAa\r\u0015\u0011\u0011\u001dFq`C\u0001\u000b\u0007A\u0001\u0002\"5\u0002F\u0002\u0007!1\u0007\u0005\t\t+\f)\r1\u0001\u00034!AAQBAc\u0001\u0004!y.A\fbI\u0012\f5\r^8s)\u0016\u0014X.\u001b8bi&|g\u000eV1tWRQAqUC\u0005\u000b\u0017)i!b\u0004\t\u0011\u0011E\u0017q\u0019a\u0001\u0005gA\u0001\u0002\"6\u0002H\u0002\u0007!1\u0007\u0005\t\u0003O\f9\r1\u0001\u0005>\"AQ\u0011CAd\u0001\u0004)\u0019\"A\u0004ti>\u0004Xj]4\u0011\r\u0005eH1\tBw))!9+b\u0006\u0006\u001a\u0015mQQ\u0004\u0005\t\t#\fI\r1\u0001\u00034!AAQ[Ae\u0001\u0004\u0011\u0019\u0004\u0003\u0005\u0002h\u0006%\u0007\u0019\u0001C_\u0011!)\t\"!3A\u0002\u0015}\u0001CBC\u0011\u000bG\u0011i/\u0004\u0002\u0004 &!QQEBP\u0005!y\u0005\u000f^5p]\u0006d\u0017AD:ikR$wn\u001e8SK\u0006\u001cxN\u001c\u000b\u0003\tc\n\u0011cZ3u'\",H\u000fZ8x]J+\u0017m]8o)\t)y\u0003\u0005\u0004\u0006\"\u0015\rB1\u000f\u000b\u0005\u0007W*\u0019\u0004\u0003\u0005\u00066\u0005=\u0007\u0019\u0001C:\u0003\u0019\u0011X-Y:p]\u00061!/\u001e8BY2$B\u0001b;\u0006<!AQQGAi\u0001\u0004!\u0019\b\u0006\u0004\u0004l\u0015}R\u0011\t\u0005\t\u000bk\t\u0019\u000e1\u0001\u0005t!AQ1IAj\u0001\u0004))%A\u0005ge>l\u0007\u000b[1tKB1\u0011\u0011 C\"\u0005g!b\u0001b;\u0006J\u0015-\u0003\u0002CC\u001b\u0003+\u0004\r\u0001b\u001d\t\u0011\u0015\r\u0013Q\u001ba\u0001\u000b\u001b\u0002b!\"\t\u0006$\tMB\u0003\u0002B5\u000b#B\u0001\u0002\"5\u0002X\u0002\u0007!1G\u0001\ri>$\u0018\r\u001c+j[\u0016|W\u000f^\u0001\u0013C\u0012$'J^7TQV$Hm\\<o\u0011>|7.\u0006\u0003\u0006Z\u0015%D\u0003\u0002CT\u000b7B\u0011\"\"\u0018\u0002\\\u0012\u0005\r!b\u0018\u0002\t!|wn\u001b\t\u0007\u0003s,\t'\"\u001a\n\t\u0015\r\u00141 \u0002\ty\tLh.Y7f}A!QqMC5\u0019\u0001!\u0001\"b\u001b\u0002\\\n\u0007QQ\u000e\u0002\u0002)F!Qq\u000eBw!\u0011\tI0\"\u001d\n\t\u0015M\u00141 \u0002\b\u001d>$\b.\u001b8h\u0003u\tG\rZ\"b]\u000e,G\u000e\\1cY\u0016Te/\\*ikR$wn\u001e8I_>\\W\u0003BC=\u000b\u0003#Baa/\u0006|!IQQLAo\t\u0003\u0007QQ\u0010\t\u0007\u0003s,\t'b \u0011\t\u0015\u001dT\u0011\u0011\u0003\t\u000bW\niN1\u0001\u0006n!\"\u0011Q\\CC!\u0011)9)\"#\u000e\u0005\t]\u0016\u0002BCF\u0005o\u0013q\u0001^1jYJ,7\r\u0006\u0003\u0005(\u0016=\u0005\u0002CC/\u0003?\u0004\r!\"%\u0011\t\tMW1S\u0005\u0005\u000b+\u0013)N\u0001\u0005Sk:t\u0017M\u00197f)\u0011\u0019Y,\"'\t\u0011\u0015u\u0013\u0011\u001da\u0001\u000b#\u0003B!!=\u0006\u001e&!QqTAs\u0005M)\u0005\u0010^3og&|g.\u00133Qe>4\u0018\u000eZ3s)\t\ty/\u0001\rQQ\u0006\u001cXMQ3g_J,7+\u001a:wS\u000e,WK\u001c2j]\u0012\f\u0011\u0004\u00155bg\u0016\u0014UMZ8sKN+'O^5dKVs'-\u001b8eA\u0005\u0011\u0002\u000b[1tKN+'O^5dKVs'-\u001b8e\u0003M\u0001\u0006.Y:f'\u0016\u0014h/[2f+:\u0014\u0017N\u001c3!\u0003a\u0001\u0006.Y:f'\u0016\u0014h/[2f%\u0016\fX/Z:ug\u0012{g.Z\u0001\u001a!\"\f7/Z*feZL7-\u001a*fcV,7\u000f^:E_:,\u0007%\u0001\tQQ\u0006\u001cXmU3sm&\u001cWm\u0015;pa\u0006\t\u0002\u000b[1tKN+'O^5dKN#x\u000e\u001d\u0011\u00025AC\u0017m]3CK\u001a|'/Z\"mkN$XM]*ikR$wn\u001e8\u00027AC\u0017m]3CK\u001a|'/Z\"mkN$XM]*ikR$wn\u001e8!\u0003\t\u0002\u0006.Y:f\u00072,8\u000f^3s'\"\f'\u000fZ5oONCW\u000f\u001e3po:\u0014VmZ5p]\u0006\u0019\u0003\u000b[1tK\u000ecWo\u001d;feNC\u0017M\u001d3j]\u001e\u001c\u0006.\u001e;e_^t'+Z4j_:\u0004\u0013!\u0005)iCN,7\t\\;ti\u0016\u0014H*Z1wK\u0006\u0011\u0002\u000b[1tK\u000ecWo\u001d;fe2+\u0017M^3!\u0003M\u0001\u0006.Y:f\u00072,8\u000f^3s\u000bbLG/\u001b8h\u0003Q\u0001\u0006.Y:f\u00072,8\u000f^3s\u000bbLG/\u001b8hA\u00059\u0002\u000b[1tK\u000ecWo\u001d;fe\u0016C\u0018\u000e^5oO\u0012{g.Z\u0001\u0019!\"\f7/Z\"mkN$XM]#ySRLgn\u001a#p]\u0016\u0004\u0013\u0001\u0006)iCN,7\t\\;ti\u0016\u00148\u000b[;uI><h.A\u000bQQ\u0006\u001cXm\u00117vgR,'o\u00155vi\u0012|wO\u001c\u0011\u0002?AC\u0017m]3CK\u001a|'/Z!di>\u00148+_:uK6$VM]7j]\u0006$X-\u0001\u0011QQ\u0006\u001cXMQ3g_J,\u0017i\u0019;peNK8\u000f^3n)\u0016\u0014X.\u001b8bi\u0016\u0004\u0013!\u0007)iCN,\u0017i\u0019;peNK8\u000f^3n)\u0016\u0014X.\u001b8bi\u0016\f!\u0004\u00155bg\u0016\f5\r^8s'f\u001cH/Z7UKJl\u0017N\\1uK\u0002\nQ\"\u00168l]><hNU3bg>t\u0007c\u0001BI;\tiQK\\6o_^t'+Z1t_:\u001c\u0012\"HA|\u000b7\u0014\tEa\u0012\u0011\u0007\tE5\u0004\u0006\u0002\u0006VR!!Q^Cq\u0011%\u0011)0IA\u0001\u0002\u0004\u0011\u0019\u000f\u0006\u0003\u0003\u0000\u0015\u0015\b\"\u0003B{G\u0005\u0005\t\u0019\u0001Bw\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t)Y\u000f\u0005\u0003\u0003T\u00165\u0018\u0002BCx\u0005+\u0014aa\u00142kK\u000e$\u0018!D;oW:|wO\u001c*fCN|g.\u0006\u0002\u0006\\\u0006Q\u0012i\u0019;peNK8\u000f^3n)\u0016\u0014X.\u001b8bi\u0016\u0014V-Y:p]B\u0019!\u0011S\u0015\u00035\u0005\u001bGo\u001c:TsN$X-\u001c+fe6Lg.\u0019;f%\u0016\f7o\u001c8\u0014\u0013%\n90b7\u0003B\t\u001dCCAC|)\u0011\u0011iO\"\u0001\t\u0013\tUX&!AA\u0002\t\rH\u0003\u0002B@\r\u000bA\u0011B!>0\u0003\u0003\u0005\rA!<\u00025\u0005\u001cGo\u001c:TsN$X-\u001c+fe6Lg.\u0019;f%\u0016\f7o\u001c8\u0002\u001b)3X.\u0012=jiJ+\u0017m]8o!\r\u0011\t*\u000e\u0002\u000e\u0015ZlW\t_5u%\u0016\f7o\u001c8\u0014\u0013U\n90b7\u0003B\t\u001dCC\u0001D\u0006)\u0011\u0011iO\"\u0006\t\u0013\tU\u0018(!AA\u0002\t\rH\u0003\u0002B@\r3A\u0011B!><\u0003\u0003\u0005\rA!<\u0002\u001b)4X.\u0012=jiJ+\u0017m]8o\u0003Q\u0019E.^:uKJ$un\u001e8j]\u001e\u0014V-Y:p]B\u0019!\u0011S!\u0003)\rcWo\u001d;fe\u0012{wO\\5oOJ+\u0017m]8o'%\t\u0015q_Cn\u0005\u0003\u00129\u0005\u0006\u0002\u0007 Q!!Q\u001eD\u0015\u0011%\u0011)0RA\u0001\u0002\u0004\u0011\u0019\u000f\u0006\u0003\u0003\u0000\u00195\u0002\"\u0003B{\u000f\u0006\u0005\t\u0019\u0001Bw\u0003Q\u0019G.^:uKJ$un\u001e8j]\u001e\u0014V-Y:p]\u0006i2\t\\;ti\u0016\u0014(j\\5o+:\u001cXoY2fgN4W\u000f\u001c*fCN|g\u000eE\u0002\u0003\u00126\u0013Qd\u00117vgR,'OS8j]Vs7/^2dKN\u001ch-\u001e7SK\u0006\u001cxN\\\n\n\u001b\u0006]X1\u001cB!\u0005\u000f\"\"Ab\r\u0015\t\t5hQ\b\u0005\n\u0005k\f\u0016\u0011!a\u0001\u0005G$BAa \u0007B!I!Q_*\u0002\u0002\u0003\u0007!Q^\u0001\u001eG2,8\u000f^3s\u0015>Lg.\u00168tk\u000e\u001cWm]:gk2\u0014V-Y:p]\u00069\u0013J\\2p[B\fG/\u001b2mK\u000e{gNZ5hkJ\fG/[8o\t\u0016$Xm\u0019;fIJ+\u0017m]8o!\r\u0011\t*\u0017\u0002(\u0013:\u001cw.\u001c9bi&\u0014G.Z\"p]\u001aLw-\u001e:bi&|g\u000eR3uK\u000e$X\r\u001a*fCN|gnE\u0005Z\u0003o,YN!\u0011\u0003HQ\u0011aq\t\u000b\u0005\u0005[4\t\u0006C\u0005\u0003vv\u000b\t\u00111\u0001\u0003dR!!q\u0010D+\u0011%\u0011)pXA\u0001\u0002\u0004\u0011i/A\u0014j]\u000e|W\u000e]1uS\ndWmQ8oM&<WO]1uS>tG)\u001a;fGR,GMU3bg>t\u0017\u0001F\"mkN$XM\u001d'fCZLgn\u001a*fCN|g\u000eE\u0002\u0003\u0012\u0016\u0014Ac\u00117vgR,'\u000fT3bm&twMU3bg>t7#C3\u0002x\u0016m'\u0011\tB$)\t1Y\u0006\u0006\u0003\u0003n\u001a\u0015\u0004\"\u0003B{S\u0006\u0005\t\u0019\u0001Br)\u0011\u0011yH\"\u001b\t\u0013\tU8.!AA\u0002\t5\u0018\u0001F2mkN$XM\u001d'fCZLgn\u001a*fCN|g.\u0001\bsk:t\u0017N\\4Km6Dun\\6\u0002%I,hN\\5oO*3X\u000eS8pW~#S-\u001d\u000b\u0005\tO3\u0019\bC\u0005\u0003vF\f\t\u00111\u0001\u0003\u0000\u0005y!/\u001e8oS:<'J^7I_>\\\u0007\u0005K\u0002s\tg#BA!\u0003\u0007|!9!1C:A\u0002\u0019u\u0004\u0003BAy\r\u007fJAA\"!\u0002f\nY\u0011i\u0019;peNK8\u000f^3n)\u0011\u0011IA\"\"\t\u000f\tMA\u000f1\u0001\u0007\bB!\u0011\u0011\u001fDE\u0013\u00111Y)!:\u00035\rc\u0017m]:jG\u0006\u001bGo\u001c:TsN$X-\u001c)s_ZLG-\u001a:\u0002\r1|wn[;q+\t\ty/A\bde\u0016\fG/Z#yi\u0016t7/[8o)\u0011\u0011IA\"&\t\u000f\tMa\u000f1\u0001\u0003\u0016\u0005\t2m\u001c8g/&$\bn\u0014<feJLG-Z:\u0015\r\u0019meq\u0016DZ!\u00111iJb+\u000e\u0005\u0019}%\u0002\u0002DQ\rG\u000baaY8oM&<'\u0002\u0002DS\rO\u000b\u0001\u0002^=qKN\fg-\u001a\u0006\u0003\rS\u000b1aY8n\u0013\u00111iKb(\u0003\r\r{gNZ5h\u0011\u001d1\tl\u001ea\u0001\r7\u000bAaY8oM\"9QQG<A\u0002\u0019U\u0006CBA}\t\u0007*Y\u000eK\u0002x\rs\u0003BAb/\u0007@6\u0011aQ\u0018\u0006\u0005\u0005s\u000bI/\u0003\u0003\u0007B\u001au&aC%oi\u0016\u0014h.\u00197Ba&\fA!\u001b8jiRAAq\u0015Dd\r\u00134Y\rC\u0004\u0003\u0014a\u0004\rA!\u0006\t\u000f\u0019E\u0006\u00101\u0001\u0007\u001c\"9aQ\u001a=A\u0002\t%\u0011!B2p_J$\u0007f\u0001=\u0007:\u0006i\u0012N\\5u!\"\f7/Z!di>\u00148+_:uK6$VM]7j]\u0006$X\r\u0006\u0005\u0005(\u001aUgq\u001bDm\u0011\u001d\u0011\u0019\"\u001fa\u0001\u0005+AqA\"-z\u0001\u00041Y\nC\u0004\u0007Nf\u0004\rA!\u0003\u0002\u0017%t\u0017\u000e\u001e&w[\"{wn\u001b\u000b\t\tO3yN\"9\u0007d\"9!1\u0003>A\u0002\u0019u\u0004b\u0002DYu\u0002\u0007a1\u0014\u0005\b\r\u001bT\b\u0019\u0001B\u0005\u0003\u0015\u0001\u0006.Y:f!\u0011\u0011\t*!\u000b\u0014\r\u0005%b1\u001eC\u0010!91iOb=\u0003^\t%$q\u0010B@\u0005\u001fk!Ab<\u000b\t\u0019E\u00181`\u0001\beVtG/[7f\u0013\u00111)Pb<\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>tG\u0007\u0006\u0002\u0007hRQ!q\u0012D~\r{4yp\"\u0001\t\u0011\te\u0013q\u0006a\u0001\u0005;B\u0001B!\u001a\u00020\u0001\u0007!\u0011\u000e\u0005\t\u0005w\ny\u00031\u0001\u0003\u0000!A!qQA\u0018\u0001\u0004\u0011y\b\u0006\u0003\b\u0006\u001d5\u0001CBA}\t\u0007:9\u0001\u0005\u0007\u0002z\u001e%!Q\fB5\u0005\u007f\u0012y(\u0003\u0003\b\f\u0005m(A\u0002+va2,G\u0007\u0003\u0006\u0005J\u0005E\u0012\u0011!a\u0001\u0005\u001f\u000b\u0001\u0003\u001d5bg\u0016\u001chI]8n\u0007>tg-[4\u0015\t\u001dMqQ\u0003\t\t\u0005?\u0011iCa\r\u0003\u0010\"Aa\u0011WA\u001b\u0001\u00041Y*A\bu_B|Gn\\4jG\u0006d7k\u001c:u)\u0011\u00199fb\u0007\t\u0011\tm\u0011q\u0007a\u0001\u000f'\t1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\u001aTCAD\u0011U\u0011\u0019\u0019C!,")
public final class CoordinatedShutdown
implements Extension {
    private volatile CoordinatedShutdown$tasks$ tasks$module;
    private ActorRef terminationWatcher;
    private final ExtendedActorSystem system;
    private final Map<String, Phase> phases;
    public final JVMShutdownHooks akka$actor$CoordinatedShutdown$$jvmShutdownHooks;
    private final LoggingAdapter log;
    private final Set<String> knownPhases;
    private final List<String> orderedPhases;
    private final AtomicReference<Option<Reason>> runStarted;
    private final Promise<Done> runPromise;
    private final AtomicReference<CountDownLatch> akka$actor$CoordinatedShutdown$$_jvmHooksLatch;
    private volatile Cancellable akka$actor$CoordinatedShutdown$$actorSystemJvmHook;
    private volatile boolean bitmap$0;

    public static CoordinatedShutdown createExtension(ExtendedActorSystem system) {
        return CoordinatedShutdown$.MODULE$.createExtension(system);
    }

    public static CoordinatedShutdown$ lookup() {
        return CoordinatedShutdown$.MODULE$.lookup();
    }

    public static CoordinatedShutdown get(ClassicActorSystemProvider system) {
        return CoordinatedShutdown$.MODULE$.get(system);
    }

    public static CoordinatedShutdown get(ActorSystem system) {
        return CoordinatedShutdown$.MODULE$.get(system);
    }

    public static Reason clusterLeavingReason() {
        return CoordinatedShutdown$.MODULE$.clusterLeavingReason();
    }

    public static Reason incompatibleConfigurationDetectedReason() {
        return CoordinatedShutdown$.MODULE$.incompatibleConfigurationDetectedReason();
    }

    public static Reason clusterJoinUnsuccessfulReason() {
        return CoordinatedShutdown$.MODULE$.clusterJoinUnsuccessfulReason();
    }

    public static Reason clusterDowningReason() {
        return CoordinatedShutdown$.MODULE$.clusterDowningReason();
    }

    public static Reason jvmExitReason() {
        return CoordinatedShutdown$.MODULE$.jvmExitReason();
    }

    public static Reason actorSystemTerminateReason() {
        return CoordinatedShutdown$.MODULE$.actorSystemTerminateReason();
    }

    public static Reason unknownReason() {
        return CoordinatedShutdown$.MODULE$.unknownReason();
    }

    public static String PhaseActorSystemTerminate() {
        return CoordinatedShutdown$.MODULE$.PhaseActorSystemTerminate();
    }

    public static String PhaseBeforeActorSystemTerminate() {
        return CoordinatedShutdown$.MODULE$.PhaseBeforeActorSystemTerminate();
    }

    public static String PhaseClusterShutdown() {
        return CoordinatedShutdown$.MODULE$.PhaseClusterShutdown();
    }

    public static String PhaseClusterExitingDone() {
        return CoordinatedShutdown$.MODULE$.PhaseClusterExitingDone();
    }

    public static String PhaseClusterExiting() {
        return CoordinatedShutdown$.MODULE$.PhaseClusterExiting();
    }

    public static String PhaseClusterLeave() {
        return CoordinatedShutdown$.MODULE$.PhaseClusterLeave();
    }

    public static String PhaseClusterShardingShutdownRegion() {
        return CoordinatedShutdown$.MODULE$.PhaseClusterShardingShutdownRegion();
    }

    public static String PhaseBeforeClusterShutdown() {
        return CoordinatedShutdown$.MODULE$.PhaseBeforeClusterShutdown();
    }

    public static String PhaseServiceStop() {
        return CoordinatedShutdown$.MODULE$.PhaseServiceStop();
    }

    public static String PhaseServiceRequestsDone() {
        return CoordinatedShutdown$.MODULE$.PhaseServiceRequestsDone();
    }

    public static String PhaseServiceUnbind() {
        return CoordinatedShutdown$.MODULE$.PhaseServiceUnbind();
    }

    public static String PhaseBeforeServiceUnbind() {
        return CoordinatedShutdown$.MODULE$.PhaseBeforeServiceUnbind();
    }

    public static Extension apply(ClassicActorSystemProvider system) {
        return CoordinatedShutdown$.MODULE$.apply(system);
    }

    public static Extension apply(ActorSystem system) {
        return CoordinatedShutdown$.MODULE$.apply(system);
    }

    private CoordinatedShutdown$tasks$ tasks() {
        if (this.tasks$module == null) {
            this.tasks$lzycompute$1();
        }
        return this.tasks$module;
    }

    public LoggingAdapter log() {
        return this.log;
    }

    private Set<String> knownPhases() {
        return this.knownPhases;
    }

    public List<String> orderedPhases() {
        return this.orderedPhases;
    }

    private AtomicReference<Option<Reason>> runStarted() {
        return this.runStarted;
    }

    private Promise<Done> runPromise() {
        return this.runPromise;
    }

    public AtomicReference<CountDownLatch> akka$actor$CoordinatedShutdown$$_jvmHooksLatch() {
        return this.akka$actor$CoordinatedShutdown$$_jvmHooksLatch;
    }

    public Cancellable akka$actor$CoordinatedShutdown$$actorSystemJvmHook() {
        return this.akka$actor$CoordinatedShutdown$$actorSystemJvmHook;
    }

    public void akka$actor$CoordinatedShutdown$$actorSystemJvmHook_$eq(Cancellable x$1) {
        this.akka$actor$CoordinatedShutdown$$actorSystemJvmHook = x$1;
    }

    private ActorRef terminationWatcher$lzycompute() {
        CoordinatedShutdown coordinatedShutdown = this;
        synchronized (coordinatedShutdown) {
            if (!this.bitmap$0) {
                this.terminationWatcher = this.system.systemActorOf(CoordinatedShutdownTerminationWatcher$.MODULE$.props(), "coordinatedShutdownTerminationWatcher");
                this.bitmap$0 = true;
            }
        }
        return this.terminationWatcher;
    }

    public ActorRef terminationWatcher() {
        if (!this.bitmap$0) {
            return this.terminationWatcher$lzycompute();
        }
        return this.terminationWatcher;
    }

    public CountDownLatch jvmHooksLatch() {
        return this.akka$actor$CoordinatedShutdown$$_jvmHooksLatch().get();
    }

    public Cancellable addCancellableTask(String phase, String taskName, Function0<Future<Done>> task) {
        Predef$.MODULE$.require(this.knownPhases().apply(phase), (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(119).append("Unknown phase [").append(phase).append("], known phases [").append(this.knownPhases()).append("]. All phases (along with their optional dependencies) must be defined in configuration").toString());
        Predef$.MODULE$.require(StringOps$.MODULE$.nonEmpty$extension(Predef$.MODULE$.augmentString(taskName)), (Function0<Object>)(Function0<String> & Serializable)() -> "Set a task name when adding tasks to the Coordinated Shutdown. Try to use unique, self-explanatory names.");
        return this.tasks().register(phase, task, taskName);
    }

    public Cancellable addCancellableTask(String phase, String taskName, Supplier<CompletionStage<Done>> task) {
        return this.addCancellableTask(phase, taskName, (Function0<Future<Done>>)(Function0<Future> & Serializable)() -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)task.get())));
    }

    public void addTask(String phase, String taskName, Function0<Future<Done>> task) {
        Predef$.MODULE$.require(this.knownPhases().apply(phase), (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(119).append("Unknown phase [").append(phase).append("], known phases [").append(this.knownPhases()).append("]. ").append("All phases (along with their optional dependencies) must be defined in configuration").toString());
        Predef$.MODULE$.require(StringOps$.MODULE$.nonEmpty$extension(Predef$.MODULE$.augmentString(taskName)), (Function0<Object>)(Function0<String> & Serializable)() -> "Set a task name when adding tasks to the Coordinated Shutdown. Try to use unique, self-explanatory names.");
        this.tasks().register(phase, task, taskName);
    }

    public void addTask(String phase, String taskName, Supplier<CompletionStage<Done>> task) {
        this.addTask(phase, taskName, (Function0<Future<Done>>)(Function0<Future> & Serializable)() -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)task.get())));
    }

    public void addActorTerminationTask(String phase, String taskName, ActorRef actor, Option<Object> stopMsg) {
        this.addTask(phase, taskName, (Function0<Future<Done>>)(Function0<Future> & Serializable)() -> {
            stopMsg.foreach((Function1<Object, Object> & Serializable)msg -> {
                CoordinatedShutdown.$anonfun$addActorTerminationTask$2(actor, msg);
                return BoxedUnit.UNIT;
            });
            Deadline deadline = ((Phase)$this.phases.apply(phase)).timeout().fromNow();
            Promise<Done> completionPromise = Promise$.MODULE$.apply();
            CoordinatedShutdownTerminationWatcher.Watch x$3 = new CoordinatedShutdownTerminationWatcher.Watch(actor, deadline, completionPromise);
            ActorRef x$4 = this.terminationWatcher().$bang$default$2(x$3);
            this.terminationWatcher().$bang(x$3, x$4);
            return completionPromise.future();
        });
    }

    public void addActorTerminationTask(String phase, String taskName, ActorRef actor, Optional<Object> stopMsg) {
        this.addActorTerminationTask(phase, taskName, actor, OptionConverters$RichOptionalGeneric$.MODULE$.asScala$extension(OptionConverters$.MODULE$.RichOptionalGeneric(stopMsg)));
    }

    public Option<Reason> shutdownReason() {
        return this.runStarted().get();
    }

    public Optional<Reason> getShutdownReason() {
        return OptionConverters$RichOptionForJava8$.MODULE$.asJava$extension(OptionConverters$.MODULE$.RichOptionForJava8(this.shutdownReason()));
    }

    public Future<Done> run(Reason reason) {
        return this.run(reason, None$.MODULE$);
    }

    public CompletionStage<Done> runAll(Reason reason) {
        return FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(this.run(reason)));
    }

    public Future<Done> run(Reason reason, Option<String> fromPhase) {
        Object object;
        if (this.runStarted().compareAndSet(None$.MODULE$, new Some<Reason>(reason))) {
            List list;
            MessageDispatcher ec = this.system.dispatchers().internalDispatcher();
            boolean debugEnabled = this.log().isDebugEnabled();
            this.log().info("Running CoordinatedShutdown with reason [{}]", reason);
            Option<String> option = fromPhase;
            if (None$.MODULE$.equals(option)) {
                list = this.orderedPhases();
            } else if (option instanceof Some) {
                Some some = (Some)option;
                String p = (String)some.value();
                list = (List)this.orderedPhases().dropWhile((Function1<String, Object> & Serializable)x$8 -> BoxesRunTime.boxToBoolean(CoordinatedShutdown.$anonfun$run$8(p, x$8)));
            } else {
                throw new MatchError(option);
            }
            List remainingPhases = list;
            Future done = this.loop$1(remainingPhases, debugEnabled, ec);
            object = this.runPromise().completeWith(done);
        } else {
            object = BoxedUnit.UNIT;
        }
        return this.runPromise().future();
    }

    public CompletionStage<Done> run(Reason reason, Optional<String> fromPhase) {
        return FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(this.run(reason, OptionConverters$RichOptionalGeneric$.MODULE$.asScala$extension(OptionConverters$.MODULE$.RichOptionalGeneric(fromPhase)))));
    }

    public FiniteDuration timeout(String phase) {
        Option option = this.phases.get(phase);
        if (option instanceof Some) {
            Some some = (Some)option;
            Phase p = (Phase)some.value();
            return p.timeout();
        }
        if (None$.MODULE$.equals(option)) {
            throw new IllegalArgumentException(new StringBuilder(61).append("Unknown phase [").append(phase).append("]. All phases must be defined in configuration").toString());
        }
        throw new MatchError(option);
    }

    public FiniteDuration totalTimeout() {
        return this.tasks().totalDuration();
    }

    public <T> void addJvmShutdownHook(Function0<T> hook) {
        this.addCancellableJvmShutdownHook(hook);
    }

    public <T> Cancellable addCancellableJvmShutdownHook(Function0<T> hook) {
        while (this.runStarted().get().isEmpty()) {
            Cancellable cancellable;
            CountDownLatch currentLatch = this.akka$actor$CoordinatedShutdown$$_jvmHooksLatch().get();
            CountDownLatch newLatch = new CountDownLatch((int)currentLatch.getCount() + 1);
            if (!this.akka$actor$CoordinatedShutdown$$_jvmHooksLatch().compareAndSet(currentLatch, newLatch)) continue;
            Thread thread2 = new Thread(this, hook){
                private final /* synthetic */ CoordinatedShutdown $outer;
                private final Function0 hook$1;

                public void run() {
                    try {
                        this.hook$1.apply();
                    }
                    finally {
                        this.$outer.akka$actor$CoordinatedShutdown$$_jvmHooksLatch().get().countDown();
                    }
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    this.hook$1 = hook$1;
                }
            };
            thread2.setName(new StringBuilder(15).append(this.system.name()).append("-shutdown-hook-").append(newLatch.getCount()).toString());
            try {
                this.akka$actor$CoordinatedShutdown$$jvmShutdownHooks.addHook(thread2);
                cancellable = new Cancellable(this, thread2){
                    private volatile boolean cancelled;
                    private final /* synthetic */ CoordinatedShutdown $outer;
                    private final Thread thread$1;

                    private boolean cancelled() {
                        return this.cancelled;
                    }

                    private void cancelled_$eq(boolean x$1) {
                        this.cancelled = x$1;
                    }

                    public boolean cancel() {
                        boolean bl;
                        block3: {
                            try {
                                if (this.$outer.akka$actor$CoordinatedShutdown$$jvmShutdownHooks.removeHook(this.thread$1)) {
                                    this.cancelled_$eq(true);
                                    this.$outer.akka$actor$CoordinatedShutdown$$_jvmHooksLatch().get().countDown();
                                    bl = true;
                                    break block3;
                                }
                                bl = false;
                            }
                            catch (IllegalStateException illegalStateException) {
                                bl = false;
                            }
                        }
                        return bl;
                    }

                    public boolean isCancelled() {
                        return this.cancelled();
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        this.thread$1 = thread$1;
                        this.cancelled = false;
                    }
                };
            }
            catch (IllegalStateException e) {
                this.log().warning("Could not addJvmShutdownHook, due to: {}", (Object)e.getMessage());
                this.akka$actor$CoordinatedShutdown$$_jvmHooksLatch().get().countDown();
                cancellable = Cancellable$.MODULE$.alreadyCancelled();
            }
            return cancellable;
        }
        return Cancellable$.MODULE$.alreadyCancelled();
    }

    public void addJvmShutdownHook(Runnable hook) {
        this.addJvmShutdownHook(() -> hook.run());
    }

    public Cancellable addCancellableJvmShutdownHook(Runnable hook) {
        return this.addCancellableJvmShutdownHook(() -> hook.run());
    }

    private final void tasks$lzycompute$1() {
        CoordinatedShutdown coordinatedShutdown = this;
        synchronized (coordinatedShutdown) {
            if (this.tasks$module == null) {
                this.tasks$module = new CoordinatedShutdown$tasks$(this);
            }
        }
    }

    public static final /* synthetic */ void $anonfun$addActorTerminationTask$2(ActorRef actor$1, Object msg) {
        Object x$1 = msg;
        ActorRef x$2 = actor$1.$bang$default$2(x$1);
        actor$1.$bang(x$1, x$2);
    }

    public static final /* synthetic */ void $anonfun$run$5(CoordinatedShutdown $this, String phaseName$2, PhaseDefinition phaseDef) {
        $this.log().info("Phase [{}] disabled through configuration, skipping [{}] tasks.", phaseName$2, BoxesRunTime.boxToInteger(phaseDef.size()));
    }

    private final /* synthetic */ Future liftedTree2$1(FiniteDuration timeout$2, String phaseName$3, Deadline deadline$1, Future result$2, boolean recoverEnabled$3, ExecutionContext ec$3) {
        Future future;
        try {
            future = package$.MODULE$.after(timeout$2, this.system.scheduler(), (Function0<Future> & Serializable)() -> {
                String string = phaseName$3;
                String string2 = CoordinatedShutdown$.MODULE$.PhaseActorSystemTerminate();
                if (!(string != null ? !string.equals(string2) : string2 != null) && deadline$1.hasTimeLeft()) {
                    return result$2;
                }
                if (result$2.isCompleted()) {
                    return Future$.MODULE$.successful(Done$.MODULE$);
                }
                if (recoverEnabled$3) {
                    this.log().warning("Coordinated shutdown phase [{}] timed out after {}", (Object)phaseName$3, (Object)timeout$2);
                    return Future$.MODULE$.successful(Done$.MODULE$);
                }
                return Future$.MODULE$.failed(new TimeoutException(new StringBuilder(46).append("Coordinated shutdown phase [").append(phaseName$3).append("] timed out after ").append(timeout$2).toString()));
            }, ec$3);
        }
        catch (IllegalStateException illegalStateException) {
            future = result$2;
        }
        return future;
    }

    private final Future loop$1(List remainingPhases, boolean debugEnabled$1, ExecutionContext ec$3) {
        List list;
        $colon$colon $colon$colon;
        boolean bl;
        while (true) {
            bl = false;
            $colon$colon = null;
            list = remainingPhases;
            if (((Object)Nil$.MODULE$).equals(list)) {
                return Future$.MODULE$.successful(Done$.MODULE$);
            }
            if (!(list instanceof $colon$colon)) break;
            bl = true;
            $colon$colon = ($colon$colon)list;
            String phaseName = (String)$colon$colon.head();
            List remaining = $colon$colon.next$access$1();
            if (((Phase)this.phases.apply(phaseName)).enabled()) break;
            this.tasks().get(phaseName).foreach((Function1<PhaseDefinition, Object> & Serializable)phaseDef -> {
                CoordinatedShutdown.$anonfun$run$5(this, phaseName, phaseDef);
                return BoxedUnit.UNIT;
            });
            remainingPhases = remaining;
        }
        if (bl) {
            Future<Done$> future;
            String phaseName = (String)$colon$colon.head();
            List remaining = $colon$colon.next$access$1();
            Option<PhaseDefinition> option = this.tasks().get(phaseName);
            if (None$.MODULE$.equals(option)) {
                if (debugEnabled$1) {
                    this.log().debug("Performing phase [{}] with [0] tasks", phaseName);
                }
                future = Future$.MODULE$.successful(Done$.MODULE$);
            } else if (option instanceof Some) {
                Some some = (Some)option;
                PhaseDefinition phaseDef2 = (PhaseDefinition)some.value();
                if (debugEnabled$1) {
                    this.log().debug("Performing phase [{}] with [{}] tasks.", phaseName, BoxesRunTime.boxToInteger(phaseDef2.size()));
                }
                boolean recoverEnabled = ((Phase)this.phases.apply(phaseName)).recover();
                Future<Done> result = phaseDef2.run(recoverEnabled, ec$3);
                FiniteDuration timeout2 = ((Phase)this.phases.apply(phaseName)).timeout();
                Deadline deadline = Deadline$.MODULE$.now().$plus(timeout2);
                Future timeoutFut = this.liftedTree2$1(timeout2, phaseName, deadline, result, recoverEnabled, ec$3);
                future = Future$.MODULE$.firstCompletedOf(new $colon$colon<Nothing$>((Nothing$)((Object)result), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)timeoutFut), Nil$.MODULE$)), ec$3);
            } else {
                throw new MatchError(option);
            }
            Future<Done$> phaseResult = future;
            if (remaining.isEmpty()) {
                return phaseResult;
            }
            return phaseResult.flatMap((Function1<Done, Future> & Serializable)x$7 -> this.loop$1(remaining, debugEnabled$1, ec$3), ec$3);
        }
        throw new MatchError(list);
    }

    public static final /* synthetic */ boolean $anonfun$run$8(String p$1, String x$8) {
        String string = x$8;
        String string2 = p$1;
        return string == null ? string2 != null : !string.equals(string2);
    }

    public CoordinatedShutdown(ExtendedActorSystem system, Map<String, Phase> phases, JVMShutdownHooks jvmShutdownHooks) {
        this.system = system;
        this.phases = phases;
        this.akka$actor$CoordinatedShutdown$$jvmShutdownHooks = jvmShutdownHooks;
        this.log = Logging$.MODULE$.apply(system, CoordinatedShutdown.class, LogSource$.MODULE$.fromAnyClass());
        this.knownPhases = (Set)phases.keySet().$plus$plus((IterableOnce)phases.values().flatMap((Function1<Phase, Set> & Serializable)x$3 -> x$3.dependsOn()));
        this.orderedPhases = CoordinatedShutdown$.MODULE$.topologicalSort(phases);
        this.runStarted = new AtomicReference<None$>(None$.MODULE$);
        this.runPromise = Promise$.MODULE$.apply();
        this.akka$actor$CoordinatedShutdown$$_jvmHooksLatch = new AtomicReference<CountDownLatch>(new CountDownLatch(0));
        OptionVal$.MODULE$.None();
        this.akka$actor$CoordinatedShutdown$$actorSystemJvmHook = null;
    }

    public static final class Phase
    implements Product,
    Serializable {
        private final Set<String> dependsOn;
        private final FiniteDuration timeout;
        private final boolean recover;
        private final boolean enabled;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Set<String> dependsOn() {
            return this.dependsOn;
        }

        public FiniteDuration timeout() {
            return this.timeout;
        }

        public boolean recover() {
            return this.recover;
        }

        public boolean enabled() {
            return this.enabled;
        }

        public Phase copy(Set<String> dependsOn, FiniteDuration timeout2, boolean recover2, boolean enabled) {
            return new Phase(dependsOn, timeout2, recover2, enabled);
        }

        public Set<String> copy$default$1() {
            return this.dependsOn();
        }

        public FiniteDuration copy$default$2() {
            return this.timeout();
        }

        public boolean copy$default$3() {
            return this.recover();
        }

        public boolean copy$default$4() {
            return this.enabled();
        }

        @Override
        public String productPrefix() {
            return "Phase";
        }

        @Override
        public int productArity() {
            return 4;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.dependsOn();
                }
                case 1: {
                    return this.timeout();
                }
                case 2: {
                    return BoxesRunTime.boxToBoolean(this.recover());
                }
                case 3: {
                    return BoxesRunTime.boxToBoolean(this.enabled());
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
            return x$1 instanceof Phase;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "dependsOn";
                }
                case 1: {
                    return "timeout";
                }
                case 2: {
                    return "recover";
                }
                case 3: {
                    return "enabled";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.anyHash(this.dependsOn()));
            n = Statics.mix(n, Statics.anyHash(this.timeout()));
            n = Statics.mix(n, this.recover() ? 1231 : 1237);
            n = Statics.mix(n, this.enabled() ? 1231 : 1237);
            return Statics.finalizeHash(n, 4);
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
            if (!(object instanceof Phase)) return false;
            boolean bl = true;
            if (!bl) return false;
            Phase phase = (Phase)x$1;
            if (this.recover() != phase.recover()) return false;
            if (this.enabled() != phase.enabled()) return false;
            Set<String> set = this.dependsOn();
            Set<String> set2 = phase.dependsOn();
            if (set == null) {
                if (set2 != null) {
                    return false;
                }
            } else if (!set.equals(set2)) return false;
            FiniteDuration finiteDuration = this.timeout();
            FiniteDuration finiteDuration2 = phase.timeout();
            if (finiteDuration == null) {
                if (finiteDuration2 == null) return true;
                return false;
            } else {
                if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
                return true;
            }
        }

        public Phase(Set<String> dependsOn, FiniteDuration timeout2, boolean recover2, boolean enabled) {
            this.dependsOn = dependsOn;
            this.timeout = timeout2;
            this.recover = recover2;
            this.enabled = enabled;
            Product.$init$(this);
        }
    }

    private interface PhaseDefinition {
        public int size();

        public Future<Done> run(boolean var1, ExecutionContext var2);
    }

    public static interface Reason {
    }
}

