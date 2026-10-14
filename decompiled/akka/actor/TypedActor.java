/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.InternalActorRef;
import akka.actor.Status;
import akka.actor.SupervisorStrategy;
import akka.actor.TypedActor$;
import akka.actor.TypedActor$NullResponse$;
import akka.actor.TypedActor$TypedActor$;
import akka.actor.TypedActorExtension;
import akka.actor.TypedActorFactory;
import akka.actor.TypedProps$;
import akka.annotation.InternalApi;
import akka.japi.Option$;
import akka.pattern.AskTimeoutException;
import akka.pattern.package$;
import akka.serialization.JavaSerializer$;
import akka.serialization.Serialization;
import akka.serialization.SerializationExtension$;
import akka.serialization.Serializer;
import akka.serialization.Serializers$;
import akka.util.Timeout;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.concurrent.Await$;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.RichInt$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0019=q\u0001CA\u001f\u0003\u007fA\t!!\u0013\u0007\u0011\u00055\u0013q\bE\u0001\u0003\u001fBq!a\u001c\u0002\t\u0003\t\t\bC\u0004\u0002t\u0005!\t%!\u001e\t\u000f\u0005M\u0014\u0001\"\u0011\u0002\u0002\"9\u00111R\u0001\u0005\u0002\u00055\u0005bBAH\u0003\u0011\u0005\u0011\u0011\u0013\u0005\b\u00037\u000bA\u0011AAO\u0011\u001d\t\u0019(\u0001C\u0001\u0003\u00074a!a2\u0002\u0005\u0006%\u0007BCAu\u0013\tU\r\u0011\"\u0001\u0002l\"Q!\u0011A\u0005\u0003\u0012\u0003\u0006I!!<\t\u0015\t\r\u0011B!f\u0001\n\u0003\u0011)\u0001\u0003\u0006\u0003\u000e%\u0011\t\u0012)A\u0005\u0005\u000fAq!a\u001c\n\t\u0003\u0011y\u0001C\u0004\u0003\u001a%!\tAa\u0007\t\u000f\t\r\u0012\u0002\"\u0001\u0003\u001c!9!QE\u0005\u0005\u0002\tm\u0001b\u0002B\u0014\u0013\u0011\u0005!1\u0004\u0005\b\u00037KA\u0011\u0001B\u0015\u0011\u001d\u0011y#\u0003C\u0005\u0005cA\u0011B!\u0013\n\u0003\u0003%\tAa\u0013\t\u0013\tE\u0013\"%A\u0005\u0002\tM\u0003\"\u0003B5\u0013E\u0005I\u0011\u0001B6\u0011%\u0011y'CA\u0001\n\u0003\u0012\t\bC\u0005\u0003|%\t\t\u0011\"\u0001\u0003~!I!QQ\u0005\u0002\u0002\u0013\u0005!q\u0011\u0005\n\u0005'K\u0011\u0011!C!\u0005+C\u0011Ba)\n\u0003\u0003%\tA!*\t\u0013\t%\u0016\"!A\u0005B\t-\u0006\"\u0003BX\u0013\u0005\u0005I\u0011\tBY\u0011%\u0011\u0019,CA\u0001\n\u0003\u0012)\fC\u0005\u00038&\t\t\u0011\"\u0011\u0003:\u001eI!QX\u0001\u0002\u0002#\u0005!q\u0018\u0004\n\u0003\u000f\f\u0011\u0011!E\u0001\u0005\u0003Dq!a\u001c#\t\u0003\u0011\u0019\u000eC\u0005\u00034\n\n\t\u0011\"\u0012\u00036\"I\u00111\u0014\u0012\u0002\u0002\u0013\u0005%Q\u001b\u0005\n\u00057\u0014\u0013\u0011!CA\u0005;D\u0011Ba\f#\u0003\u0003%IAa<\u0007\u0011\t]\u0018AQA\"\u0005sD!Ba?)\u0005+\u0007I\u0011\u0001B\u007f\u0011)\u0019I\u0002\u000bB\tB\u0003%!q \u0005\u000b\u0007GA#Q3A\u0005\u0002\r\u0015\u0002BCB\u0016Q\tE\t\u0015!\u0003\u0004(!Q1Q\u0006\u0015\u0003\u0016\u0004%\taa\f\t\u0015\ru\u0002F!E!\u0002\u0013\u0019\t\u0004\u0003\u0006\u0004@!\u0012)\u001a!C\u0001\u0007\u0003B!ba\u0015)\u0005#\u0005\u000b\u0011BB\"\u0011\u001d\ty\u0007\u000bC\u0001\u0007+Bqaa\u001d)\t\u0013\u0011\t\u0004C\u0005\u0003J!\n\t\u0011\"\u0001\u0004x!I!\u0011\u000b\u0015\u0012\u0002\u0013\u00051\u0011\u0011\u0005\n\u0005SB\u0013\u0013!C\u0001\u0007\u001fC\u0011ba%)#\u0003%\ta!&\t\u0013\re\u0005&%A\u0005\u0002\rm\u0005\"\u0003B8Q\u0005\u0005I\u0011\tB9\u0011%\u0011Y\bKA\u0001\n\u0003\u0011i\bC\u0005\u0003\u0006\"\n\t\u0011\"\u0001\u0004 \"I!1\u0013\u0015\u0002\u0002\u0013\u0005#Q\u0013\u0005\n\u0005GC\u0013\u0011!C\u0001\u0007GC\u0011B!+)\u0003\u0003%\tea*\t\u0013\t=\u0006&!A\u0005B\tE\u0006\"\u0003BZQ\u0005\u0005I\u0011\tB[\u0011%\u00119\fKA\u0001\n\u0003\u001aYkB\u0006\u00040\u0006\t\t\u0011#\u0001\u0002D\rEfa\u0003B|\u0003\u0005\u0005\t\u0012AA\"\u0007gCq!a\u001cC\t\u0003\u0019i\rC\u0005\u00034\n\u000b\t\u0011\"\u0012\u00036\"I\u00111\u0014\"\u0002\u0002\u0013\u00055q\u001a\u0005\n\u00057\u0014\u0015\u0011!CA\u0007WD\u0011Ba\fC\u0003\u0003%IAa<\t\u0013\u0011%\u0011A1A\u0005\n\u0011-\u0001\u0002\u0003C\n\u0003\u0001\u0006I\u0001\"\u0004\t\u0013\u0011U\u0011A1A\u0005\n\u0011]\u0001\u0002\u0003C\u000e\u0003\u0001\u0006I\u0001\"\u0007\b\u000f\u0011u\u0011\u0001##\u0005 \u00199A\u0011E\u0001\t\n\u0012\r\u0002bBA8\u001b\u0012\u0005AQ\u0005\u0005\n\u0005_j\u0015\u0011!C!\u0005cB\u0011Ba\u001fN\u0003\u0003%\tA! \t\u0013\t\u0015U*!A\u0005\u0002\u0011\u001d\u0002\"\u0003BJ\u001b\u0006\u0005I\u0011\tBK\u0011%\u0011\u0019+TA\u0001\n\u0003!Y\u0003C\u0005\u000306\u000b\t\u0011\"\u0011\u00032\"I!1W'\u0002\u0002\u0013\u0005#Q\u0017\u0005\n\u0005_i\u0015\u0011!C\u0005\u0005_Dq\u0001\"\u0010\u0002\t\u0003!y\u0004C\u0004\u0002(\u0006!\t\u0001b\u0013\t\u000f\u00115\u0013\u0001b\u0001\u0005P\u0019A\u0011QJ\u0001\u0001\u0003\u0007\"i\u0006\u0003\u0006\u0005hi\u0013)\u0019!C\u0001\tSB!\u0002b![\u0005\u0003\u0005\u000b\u0011\u0002C6\u0011)!)I\u0017B\u0001J\u0003%Aq\u0011\u0005\u000b\t'S&\u0011!Q\u0001\n\u0011U\u0005bBA85\u0012\u0005A1\u0016\u0005\n\t\u007fS&\u0019!C\u0005\t\u0003D\u0001\u0002b1[A\u0003%AQ\u0012\u0005\b\t\u000bTF\u0011\tCd\u0011\u001d!yM\u0017C!\t#Dq\u0001\"7[\t\u0003\"\t\u000eC\u0004\u0005\\j#\t\u0005\"8\t\u000f\u00115(\f\"\u0011\u0005p\"9A1\u001f.\u0005\u0012\u0011U\bbBC\u00035\u0012\u0005Qq\u0001\u0005\u000f\u000b\u001fQ\u0006\u0013aA\u0001\u0002\u0013%A\u0011[C\t\u00119)\u0019B\u0017I\u0001\u0004\u0003\u0005I\u0011\u0002Ci\u000b+Aa\"b\u0006[!\u0003\r\t\u0011!C\u0005\u000b3)ICB\u0005\u0006,\u0005\u0001\n1%\u0001\u0006.!9AQ\u00197\u0007\u0002\u0015=b!CC\u0019\u0003A\u0005\u0019\u0013AC\u001a\u0011\u001d))D\u001cD\u0001\u000bo1\u0011\"\"\u0012\u0002!\u0003\r\n!b\u0012\t\u000f\u0011=\u0007O\"\u0001\u0005R\u001aIQ\u0011J\u0001\u0011\u0002G\u0005Q1\n\u0005\b\t3\u0014h\u0011\u0001Ci\r%)i%\u0001I\u0001$\u0003)y\u0005C\u0004\u0005\\R4\t!\"\u0015\u0007\u0013\u0015]\u0013\u0001%A\u0012\u0002\u0015e\u0003b\u0002Cwm\u001a\u0005Q1\f\u0004\t\u000b?\n\u0001!a\u0011\u0006b!QQ\u0011\u000e=\u0003\u0006\u0004%\t!b\u001b\t\u0015\u00155\u0004P!A!\u0002\u0013\t\u0019\u0007\u0003\u0006\u0006xa\u0014)\u0019!C\u0001\u000bsB!\"\" y\u0005\u0003\u0005\u000b\u0011BC>\u0011))\t\t\u001fBC\u0002\u0013\u0005Q1\u0011\u0005\u000b\u000b\u0017C(\u0011!Q\u0001\n\u0015\u0015\u0005bBA8q\u0012\u0005Qq\u0012\u0005\b\u0003\u0003BH\u0011ACM\u0011\u001d)Y\n\u001fC\u0001\u000b;CqAa\fy\t\u0013\u0011\tD\u0002\u0005\u00062\u0006\u0011\u00151ICZ\u0011-\t\t%a\u0002\u0003\u0016\u0004%\t!\"'\t\u0017\u0015U\u0016q\u0001B\tB\u0003%Qq\b\u0005\f\u000b\u0003\u000b9A!f\u0001\n\u0003)9\fC\u0006\u0006\f\u0006\u001d!\u0011#Q\u0001\n\u0015e\u0006\u0002CA8\u0003\u000f!\t!\"2\t\u0011\rM\u0014q\u0001C\u0005\u0005cA\u0001\"b4\u0002\b\u0011\u0005Q\u0011\u001b\u0005\u000b\u0005\u0013\n9!!A\u0005\u0002\u0015U\u0007B\u0003B)\u0003\u000f\t\n\u0011\"\u0001\u0006\\\"Q!\u0011NA\u0004#\u0003%\t!b8\t\u0015\t=\u0014qAA\u0001\n\u0003\u0012\t\b\u0003\u0006\u0003|\u0005\u001d\u0011\u0011!C\u0001\u0005{B!B!\"\u0002\b\u0005\u0005I\u0011ACr\u0011)\u0011\u0019*a\u0002\u0002\u0002\u0013\u0005#Q\u0013\u0005\u000b\u0005G\u000b9!!A\u0005\u0002\u0015\u001d\bB\u0003BU\u0003\u000f\t\t\u0011\"\u0011\u0006l\"Q!qVA\u0004\u0003\u0003%\tE!-\t\u0015\tM\u0016qAA\u0001\n\u0003\u0012)\f\u0003\u0006\u00038\u0006\u001d\u0011\u0011!C!\u000b_<1\"b=\u0002\u0003\u0003E\t!a\u0011\u0006v\u001aYQ\u0011W\u0001\u0002\u0002#\u0005\u00111IC|\u0011!\ty'!\r\u0005\u0002\u0015m\bB\u0003BZ\u0003c\t\t\u0011\"\u0012\u00036\"Q\u00111TA\u0019\u0003\u0003%\t)\"@\t\u0015\tm\u0017\u0011GA\u0001\n\u00033\u0019\u0001\u0003\u0006\u00030\u0005E\u0012\u0011!C\u0005\u0005_\f!\u0002V=qK\u0012\f5\r^8s\u0015\u0011\t\t%a\u0011\u0002\u000b\u0005\u001cGo\u001c:\u000b\u0005\u0005\u0015\u0013\u0001B1lW\u0006\u001c\u0001\u0001E\u0002\u0002L\u0005i!!a\u0010\u0003\u0015QK\b/\u001a3BGR|'oE\u0004\u0002\u0003#\ni&!\u001b\u0011\t\u0005M\u0013\u0011L\u0007\u0003\u0003+R!!a\u0016\u0002\u000bM\u001c\u0017\r\\1\n\t\u0005m\u0013Q\u000b\u0002\u0007\u0003:L(+\u001a4\u0011\r\u0005-\u0013qLA2\u0013\u0011\t\t'a\u0010\u0003\u0017\u0015CH/\u001a8tS>t\u0017\n\u001a\t\u0005\u0003\u0017\n)'\u0003\u0003\u0002h\u0005}\"a\u0005+za\u0016$\u0017i\u0019;pe\u0016CH/\u001a8tS>t\u0007\u0003BA&\u0003WJA!!\u001c\u0002@\t\u0019R\t\u001f;f]NLwN\\%e!J|g/\u001b3fe\u00061A(\u001b8jiz\"\"!!\u0013\u0002\u0007\u001d,G\u000f\u0006\u0003\u0002d\u0005]\u0004bBA=\u0007\u0001\u0007\u00111P\u0001\u0007gf\u001cH/Z7\u0011\t\u0005-\u0013QP\u0005\u0005\u0003\u007f\nyDA\u0006BGR|'oU=ti\u0016lG\u0003BA2\u0003\u0007Cq!!\u001f\u0005\u0001\u0004\t)\t\u0005\u0003\u0002L\u0005\u001d\u0015\u0002BAE\u0003\u007f\u0011!d\u00117bgNL7-Q2u_J\u001c\u0016p\u001d;f[B\u0013xN^5eKJ\fa\u0001\\8pWV\u0004XCAA%\u0003=\u0019'/Z1uK\u0016CH/\u001a8tS>tG\u0003BA2\u0003'Cq!!\u001f\u0007\u0001\u0004\t)\n\u0005\u0003\u0002L\u0005]\u0015\u0002BAM\u0003\u007f\u00111#\u0012=uK:$W\rZ!di>\u00148+_:uK6\fQ!\u00199qYf$B!a(\u0002&B!\u00111JAQ\u0013\u0011\t\u0019+a\u0010\u0003#QK\b/\u001a3BGR|'OR1di>\u0014\u0018\u0010C\u0004\u0002(\u001e\u0001\r!!+\u0002\u000f\r|g\u000e^3yiB!\u00111JAV\u0013\u0011\ti+a\u0010\u0003\u0019\u0005\u001bGo\u001c:D_:$X\r\u001f;)\u0017\u001d\t\t,a.\u0002:\u0006u\u0016q\u0018\t\u0005\u0003'\n\u0019,\u0003\u0003\u00026\u0006U#A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017EAA^\u0003m)6/\u001a\u0011(C.\\\u0017ML1di>\u0014h\u0006^=qK\u0012<\u0003%\u0011)J]\u0005)1/\u001b8dK\u0006\u0012\u0011\u0011Y\u0001\u0006e92d\u0006\r\u000b\u0005\u0003?\u000b)\rC\u0004\u0002(\"\u0001\r!!+\u0003\u00155+G\u000f[8e\u0007\u0006dGnE\u0004\n\u0003#\nY-!5\u0011\t\u0005M\u0013QZ\u0005\u0005\u0003\u001f\f)FA\u0004Qe>$Wo\u0019;\u0011\t\u0005M\u00171\u001d\b\u0005\u0003+\fyN\u0004\u0003\u0002X\u0006uWBAAm\u0015\u0011\tY.a\u0012\u0002\rq\u0012xn\u001c;?\u0013\t\t9&\u0003\u0003\u0002b\u0006U\u0013a\u00029bG.\fw-Z\u0005\u0005\u0003K\f9O\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0003\u0002b\u0006U\u0013AB7fi\"|G-\u0006\u0002\u0002nB!\u0011q^A\u007f\u001b\t\t\tP\u0003\u0003\u0002t\u0006U\u0018a\u0002:fM2,7\r\u001e\u0006\u0005\u0003o\fI0\u0001\u0003mC:<'BAA~\u0003\u0011Q\u0017M^1\n\t\u0005}\u0018\u0011\u001f\u0002\u0007\u001b\u0016$\bn\u001c3\u0002\u000f5,G\u000f[8eA\u0005Q\u0001/\u0019:b[\u0016$XM]:\u0016\u0005\t\u001d\u0001CBA*\u0005\u0013\t\t&\u0003\u0003\u0003\f\u0005U#!B!se\u0006L\u0018a\u00039be\u0006lW\r^3sg\u0002\"bA!\u0005\u0003\u0016\t]\u0001c\u0001B\n\u00135\t\u0011\u0001C\u0004\u0002j:\u0001\r!!<\t\u000f\t\ra\u00021\u0001\u0003\b\u0005A\u0011n](oK^\u000b\u00170\u0006\u0002\u0003\u001eA!\u00111\u000bB\u0010\u0013\u0011\u0011\t#!\u0016\u0003\u000f\t{w\u000e\\3b]\u0006i!/\u001a;ve:\u001ch)\u001e;ve\u0016\faB]3ukJt7OS(qi&|g.A\u0007sKR,(O\\:PaRLwN\u001c\u000b\u0005\u0003#\u0012Y\u0003C\u0004\u0003.M\u0001\r!!\u0015\u0002\u0011%t7\u000f^1oG\u0016\fAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!\u0015)\u000bQ\u0011)Da\u0012\u0011\r\u0005M#q\u0007B\u001e\u0013\u0011\u0011I$!\u0016\u0003\rQD'o\\<t!\u0011\u0011iDa\u0011\u000e\u0005\t}\"\u0002\u0002B!\u0003s\f!![8\n\t\t\u0015#q\b\u0002\u0016\u001f\nTWm\u0019;TiJ,\u0017-\\#yG\u0016\u0004H/[8oG\t\u0011Y$\u0001\u0003d_BLHC\u0002B\t\u0005\u001b\u0012y\u0005C\u0005\u0002jV\u0001\n\u00111\u0001\u0002n\"I!1A\u000b\u0011\u0002\u0003\u0007!qA\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\t\u0011)F\u000b\u0003\u0002n\n]3F\u0001B-!\u0011\u0011YF!\u001a\u000e\u0005\tu#\u0002\u0002B0\u0005C\n\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\t\t\r\u0014QK\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002\u0002B4\u0005;\u0012\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\"A!\u001c+\t\t\u001d!qK\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\tM\u0004\u0003\u0002B;\u0005oj!!!>\n\t\te\u0014Q\u001f\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0005\t}\u0004\u0003BA*\u0005\u0003KAAa!\u0002V\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!!\u0011\u0012BH!\u0011\t\u0019Fa#\n\t\t5\u0015Q\u000b\u0002\u0004\u0003:L\b\"\u0003BI5\u0005\u0005\t\u0019\u0001B@\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011!q\u0013\t\u0007\u00053\u0013yJ!#\u000e\u0005\tm%\u0002\u0002BO\u0003+\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\u0011\tKa'\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0005;\u00119\u000bC\u0005\u0003\u0012r\t\t\u00111\u0001\u0003\n\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\u0011\u0019H!,\t\u0013\tEU$!AA\u0002\t}\u0014\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\t}\u0014\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\tM\u0014AB3rk\u0006d7\u000f\u0006\u0003\u0003\u001e\tm\u0006\"\u0003BIA\u0005\u0005\t\u0019\u0001BE\u0003)iU\r\u001e5pI\u000e\u000bG\u000e\u001c\t\u0004\u0005'\u00113#\u0002\u0012\u0003D\n=\u0007C\u0003Bc\u0005\u0017\fiOa\u0002\u0003\u00125\u0011!q\u0019\u0006\u0005\u0005\u0013\f)&A\u0004sk:$\u0018.\\3\n\t\t5'q\u0019\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\u0014\u0004\u0003\u0002B\u001f\u0005#LA!!:\u0003@Q\u0011!q\u0018\u000b\u0007\u0005#\u00119N!7\t\u000f\u0005%X\u00051\u0001\u0002n\"9!1A\u0013A\u0002\t\u001d\u0011aB;oCB\u0004H.\u001f\u000b\u0005\u0005?\u0014Y\u000f\u0005\u0004\u0002T\t\u0005(Q]\u0005\u0005\u0005G\f)F\u0001\u0004PaRLwN\u001c\t\t\u0003'\u00129/!<\u0003\b%!!\u0011^A+\u0005\u0019!V\u000f\u001d7fe!I!Q\u001e\u0014\u0002\u0002\u0003\u0007!\u0011C\u0001\u0004q\u0012\u0002DC\u0001By!\u0011\u0011)Ha=\n\t\tU\u0018Q\u001f\u0002\u0007\u001f\nTWm\u0019;\u0003)M+'/[1mSj,G-T3uQ>$7)\u00197m'\u001dA\u0013\u0011KAf\u0003#\f\u0011b\\<oKJ$\u0016\u0010]3\u0016\u0005\t}\b\u0007BB\u0001\u0007+\u0001baa\u0001\u0004\f\rEa\u0002BB\u0003\u0007\u000f\u0001B!a6\u0002V%!1\u0011BA+\u0003\u0019\u0001&/\u001a3fM&!1QBB\b\u0005\u0015\u0019E.Y:t\u0015\u0011\u0019I!!\u0016\u0011\t\rM1Q\u0003\u0007\u0001\t-\u00199BKA\u0001\u0002\u0003\u0015\taa\u0007\u0003\u0007}#C'\u0001\u0006po:,'\u000fV=qK\u0002\nBa!\b\u0003\nB!\u00111KB\u0010\u0013\u0011\u0019\t#!\u0016\u0003\u000f9{G\u000f[5oO\u0006QQ.\u001a;i_\u0012t\u0015-\\3\u0016\u0005\r\u001d\u0002\u0003BB\u0002\u0007SIAA!\u001f\u0004\u0010\u0005YQ.\u001a;i_\u0012t\u0015-\\3!\u00039\u0001\u0018M]1nKR,'\u000fV=qKN,\"a!\r\u0011\r\u0005M#\u0011BB\u001aa\u0011\u0019)d!\u000f\u0011\r\r\r11BB\u001c!\u0011\u0019\u0019b!\u000f\u0005\u0017\rmb&!A\u0001\u0002\u000b\u000511\u0004\u0002\u0004?\u0012*\u0014a\u00049be\u0006lW\r^3s)f\u0004Xm\u001d\u0011\u0002)M,'/[1mSj,G\rU1sC6,G/\u001a:t+\t\u0019\u0019\u0005\u0005\u0004\u0002T\t%1Q\t\t\u000b\u0003'\u001a9Ea \u0004(\r-\u0013\u0002BB%\u0003+\u0012a\u0001V;qY\u0016\u001c\u0004CBA*\u0005\u0013\u0019i\u0005\u0005\u0003\u0002T\r=\u0013\u0002BB)\u0003+\u0012AAQ=uK\u0006)2/\u001a:jC2L'0\u001a3QCJ\fW.\u001a;feN\u0004CCCB,\u00073\u001a\u0019g!\u001a\u0004rA\u0019!1\u0003\u0015\t\u000f\tm\u0018\u00071\u0001\u0004\\A\"1QLB1!\u0019\u0019\u0019aa\u0003\u0004`A!11CB1\t1\u00199b!\u0017\u0002\u0002\u0003\u0005)\u0011AB\u000e\u0011\u001d\u0019\u0019#\ra\u0001\u0007OAqa!\f2\u0001\u0004\u00199\u0007\u0005\u0004\u0002T\t%1\u0011\u000e\u0019\u0005\u0007W\u001ay\u0007\u0005\u0004\u0004\u0004\r-1Q\u000e\t\u0005\u0007'\u0019y\u0007\u0002\u0007\u0004<\r\u0015\u0014\u0011!A\u0001\u0006\u0003\u0019Y\u0002C\u0004\u0004@E\u0002\raa\u0011\u0002\u0017I,\u0017\r\u001a*fg>dg/\u001a\u0015\u0006e\tU\"q\t\u000b\u000b\u0007/\u001aIha\u001f\u0004~\r}\u0004\"\u0003B~gA\u0005\t\u0019AB.\u0011%\u0019\u0019c\rI\u0001\u0002\u0004\u00199\u0003C\u0005\u0004.M\u0002\n\u00111\u0001\u0004h!I1qH\u001a\u0011\u0002\u0003\u000711I\u000b\u0003\u0007\u0007\u0003Da!\"\u0004\u000e*\"1q\u0011B,!\u0019\u0011)h!#\u0004\f&!1QBA{!\u0011\u0019\u0019b!$\u0005\u0017\r]A'!A\u0001\u0002\u000b\u000511D\u000b\u0003\u0007#SCaa\n\u0003X\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\u001aTCABLU\u0011\u0019\tDa\u0016\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%iU\u00111Q\u0014\u0016\u0005\u0007\u0007\u00129\u0006\u0006\u0003\u0003\n\u000e\u0005\u0006\"\u0003BIu\u0005\u0005\t\u0019\u0001B@)\u0011\u0011ib!*\t\u0013\tEE(!AA\u0002\t%E\u0003\u0002B:\u0007SC\u0011B!%>\u0003\u0003\u0005\rAa \u0015\t\tu1Q\u0016\u0005\n\u0005#\u0003\u0015\u0011!a\u0001\u0005\u0013\u000bAcU3sS\u0006d\u0017N_3e\u001b\u0016$\bn\u001c3DC2d\u0007c\u0001B\n\u0005N)!i!.\u0003PBq!QYB\\\u0007w\u001b9ca1\u0004D\r]\u0013\u0002BB]\u0005\u000f\u0014\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c85a\u0011\u0019il!1\u0011\r\r\r11BB`!\u0011\u0019\u0019b!1\u0005\u0017\r]!)!A\u0001\u0002\u000b\u000511\u0004\t\u0007\u0003'\u0012Ia!21\t\r\u001d71\u001a\t\u0007\u0007\u0007\u0019Ya!3\u0011\t\rM11\u001a\u0003\f\u0007w\u0011\u0015\u0011!A\u0001\u0006\u0003\u0019Y\u0002\u0006\u0002\u00042RQ1qKBi\u00077\u001cin!;\t\u000f\tmX\t1\u0001\u0004TB\"1Q[Bm!\u0019\u0019\u0019aa\u0003\u0004XB!11CBm\t1\u00199b!5\u0002\u0002\u0003\u0005)\u0011AB\u000e\u0011\u001d\u0019\u0019#\u0012a\u0001\u0007OAqa!\fF\u0001\u0004\u0019y\u000e\u0005\u0004\u0002T\t%1\u0011\u001d\u0019\u0005\u0007G\u001c9\u000f\u0005\u0004\u0004\u0004\r-1Q\u001d\t\u0005\u0007'\u00199\u000f\u0002\u0007\u0004<\ru\u0017\u0011!A\u0001\u0006\u0003\u0019Y\u0002C\u0004\u0004@\u0015\u0003\raa\u0011\u0015\t\r5Hq\u0001\t\u0007\u0003'\u0012\toa<\u0011\u0019\u0005M3\u0011_B{\u0007O\u0019ipa\u0011\n\t\rM\u0018Q\u000b\u0002\u0007)V\u0004H.\u001a\u001b1\t\r]81 \t\u0007\u0007\u0007\u0019Ya!?\u0011\t\rM11 \u0003\f\u0007/1\u0015\u0011!A\u0001\u0006\u0003\u0019Y\u0002\u0005\u0004\u0002T\t%1q \u0019\u0005\t\u0003!)\u0001\u0005\u0004\u0004\u0004\r-A1\u0001\t\u0005\u0007'!)\u0001B\u0006\u0004<\u0019\u000b\t\u0011!A\u0003\u0002\rm\u0001\"\u0003Bw\r\u0006\u0005\t\u0019AB,\u00035\u0019X\r\u001c4SK\u001a,'/\u001a8dKV\u0011AQ\u0002\t\u0007\u0005k\"y!!\u0015\n\t\u0011E\u0011Q\u001f\u0002\f)\"\u0014X-\u00193M_\u000e\fG.\u0001\btK24'+\u001a4fe\u0016t7-\u001a\u0011\u0002\u001d\r,(O]3oi\u000e{g\u000e^3yiV\u0011A\u0011\u0004\t\u0007\u0005k\"y!!+\u0002\u001f\r,(O]3oi\u000e{g\u000e^3yi\u0002\nABT;mYJ+7\u000f]8og\u0016\u00042Aa\u0005N\u00051qU\u000f\u001c7SKN\u0004xN\\:f'\u001di\u0015\u0011KAf\u0003#$\"\u0001b\b\u0015\t\t%E\u0011\u0006\u0005\n\u0005#\u000b\u0016\u0011!a\u0001\u0005\u007f\"BA!\b\u0005.!I!\u0011S*\u0002\u0002\u0003\u0007!\u0011\u0012\u0015\b\u001b\u0012EBq\u0007C\u001d!\u0011\t\u0019\u0006b\r\n\t\u0011U\u0012Q\u000b\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!\u0001\u0015\b\u0019\u0012EBq\u0007C\u001d\u0003\u0011\u0019X\r\u001c4\u0016\t\u0011\u0005CQI\u000b\u0003\t\u0007\u0002Baa\u0005\u0005F\u00119AqI,C\u0002\u0011%#!\u0001+\u0012\t\ru\u0011\u0011K\u000b\u0003\u0003S\u000b!\u0002Z5ta\u0006$8\r[3s+\t!\t\u0006\u0005\u0003\u0005T\u0011eSB\u0001C+\u0015\u0011!9&!\u0016\u0002\u0015\r|gnY;se\u0016tG/\u0003\u0003\u0005\\\u0011U#\u0001G#yK\u000e,H/[8o\u0007>tG/\u001a=u\u000bb,7-\u001e;peV1Aq\fC@\t\u001f\u001bRAWA)\tC\u0002B!a\u0013\u0005d%!AQMA \u0005\u0015\t5\r^8s\u0003!\u0001(o\u001c=z-\u0006\u0014XC\u0001C6!\u0019!i\u0007\"\u001f\u0005~5\u0011Aq\u000e\u0006\u0005\tc\"\u0019(\u0001\u0004bi>l\u0017n\u0019\u0006\u0005\t/\")H\u0003\u0003\u0005x\u0005e\u0018\u0001B;uS2LA\u0001b\u001f\u0005p\ty\u0011\t^8nS\u000e\u0014VMZ3sK:\u001cW\r\u0005\u0003\u0004\u0014\u0011}Da\u0002CA5\n\u0007A\u0011\n\u0002\u0002%\u0006I\u0001O]8ysZ\u000b'\u000fI\u0001\u000fGJ,\u0017\r^3J]N$\u0018M\\2f!\u0019\t\u0019\u0006\"#\u0005\u000e&!A1RA+\u0005!a$-\u001f8b[\u0016t\u0004\u0003BB\n\t\u001f#q\u0001b\u0012[\u0005\u0004!\t*\u0005\u0003\u0004\u001e\u0011u\u0014AC5oi\u0016\u0014h-Y2fgB1Aq\u0013CO\tCk!\u0001\"'\u000b\t\u0011m%1T\u0001\nS6lW\u000f^1cY\u0016LA\u0001b(\u0005\u001a\n\u00191+Z91\t\u0011\rFq\u0015\t\u0007\u0007\u0007\u0019Y\u0001\"*\u0011\t\rMAq\u0015\u0003\f\tSs\u0016\u0011!A\u0001\u0006\u0003\u0019YBA\u0002`IY\"\u0002\u0002\",\u00050\u0012EF1\u0017\t\b\u0005'QFQ\u0010CG\u0011\u001d!9g\u0018a\u0001\tWB\u0001\u0002\"\"`\t\u0003\u0007Aq\u0011\u0005\b\t'{\u0006\u0019\u0001C[!\u0019!9\n\"(\u00058B\"A\u0011\u0018C_!\u0019\u0019\u0019aa\u0003\u0005<B!11\u0003C_\t1!I\u000bb-\u0002\u0002\u0003\u0005)\u0011AB\u000e\u0003\tiW-\u0006\u0002\u0005\u000e\u0006\u0019Q.\u001a\u0011\u0002%M,\b/\u001a:wSN|'o\u0015;sCR,w-_\u000b\u0003\t\u0013\u0004B!a\u0013\u0005L&!AQZA \u0005I\u0019V\u000f]3sm&\u001cxN]*ue\u0006$XmZ=\u0002\u0011A\u0014Xm\u0015;beR$\"\u0001b5\u0011\t\u0005MCQ[\u0005\u0005\t/\f)F\u0001\u0003V]&$\u0018\u0001\u00039pgR\u001cFo\u001c9\u0002\u0015A\u0014XMU3ti\u0006\u0014H\u000f\u0006\u0004\u0005T\u0012}G\u0011\u001e\u0005\b\tC,\u0007\u0019\u0001Cr\u0003\u0019\u0011X-Y:p]B!\u00111\u001bCs\u0013\u0011!9/a:\u0003\u0013QC'o\\<bE2,\u0007bBA\\K\u0002\u0007A1\u001e\t\u0007\u0003'\u0012\tO!#\u0002\u0017A|7\u000f\u001e*fgR\f'\u000f\u001e\u000b\u0005\t'$\t\u0010C\u0004\u0005b\u001a\u0004\r\u0001b9\u0002\u0017]LG\u000f[\"p]R,\u0007\u0010^\u000b\u0005\to$Y\u0010\u0006\u0003\u0005z\u0012}\b\u0003BB\n\tw$q\u0001\"@h\u0005\u0004\u0019YBA\u0001V\u0011!)\ta\u001aCA\u0002\u0015\r\u0011AC;oSR|emV8sWB1\u00111\u000bCE\ts\fqA]3dK&4X-\u0006\u0002\u0006\nAA\u00111KC\u0006\u0005\u0013#\u0019.\u0003\u0003\u0006\u000e\u0005U#a\u0004)beRL\u0017\r\u001c$v]\u000e$\u0018n\u001c8\u0002\u001dM,\b/\u001a:%aJ,7\u000b^1si&!Aq\u001aC2\u00039\u0019X\u000f]3sIA|7\u000f^*u_BLA\u0001\"7\u0005d\u0005\t2/\u001e9fe\u0012\u0002xn\u001d;SKN$\u0018M\u001d;\u0015\t\u0011MW1\u0004\u0005\b\tC\\\u0007\u0019\u0001CrQ\u0011)Y\"b\b\u0011\t\u0015\u0005RQE\u0007\u0003\u000bGQA\u0001b\u001e\u0002D%!QqEC\u0012\u0005\u0019)h.^:fI&!AQ\u001eC2\u0005)\u0019V\u000f]3sm&\u001cxN]\n\u0004Y\u0006ECC\u0001Ce\u0005!\u0011VmY3jm\u0016\u00148c\u00018\u0002R\u0005IqN\u001c*fG\u0016Lg/\u001a\u000b\u0007\t',I$b\u000f\t\u000f\u0005]v\u000e1\u0001\u0003\n\"9QQH8A\u0002\u0015}\u0012AB:f]\u0012,'\u000f\u0005\u0003\u0002L\u0015\u0005\u0013\u0002BC\"\u0003\u007f\u0011\u0001\"Q2u_J\u0014VM\u001a\u0002\t!J,7\u000b^1siN\u0019\u0001/!\u0015\u0003\u0011A{7\u000f^*u_B\u001c2A]A)\u0005)\u0001&/\u001a*fgR\f'\u000f^\n\u0004i\u0006ECC\u0002Cj\u000b'*)\u0006C\u0004\u0005bV\u0004\r\u0001b9\t\u000f\u0005]V\u000f1\u0001\u0005l\nY\u0001k\\:u%\u0016\u001cH/\u0019:u'\r1\u0018\u0011\u000b\u000b\u0005\t',i\u0006C\u0004\u0005b^\u0004\r\u0001b9\u00037QK\b/\u001a3BGR|'/\u00138w_\u000e\fG/[8o\u0011\u0006tG\r\\3s'\u001dA(\u0011_C2\u0003#\u0004B!a<\u0006f%!QqMAy\u0005EIeN^8dCRLwN\u001c%b]\u0012dWM]\u0001\nKb$XM\\:j_:,\"!a\u0019\u0002\u0015\u0015DH/\u001a8tS>t\u0007\u0005K\u0002{\u000bc\u0002B!a\u0015\u0006t%!QQOA+\u0005%!(/\u00198tS\u0016tG/\u0001\u0005bGR|'OV1s+\t)Y\b\u0005\u0004\u0005n\u0011eTqH\u0001\nC\u000e$xN\u001d,be\u0002B3\u0001`C9\u0003\u001d!\u0018.\\3pkR,\"!\"\"\u0011\t\u0015\u0005RqQ\u0005\u0005\u000b\u0013+\u0019CA\u0004US6,w.\u001e;\u0002\u0011QLW.Z8vi\u0002B3A`C9)!)\t*b%\u0006\u0016\u0016]\u0005c\u0001B\nq\"9Q\u0011N@A\u0002\u0005\r\u0004bBC<\u007f\u0002\u0007Q1\u0010\u0005\b\u000b\u0003{\b\u0019ACC+\t)y$\u0001\u0004j]Z|7.\u001a\u000b\t\u0003#*y*b)\u0006&\"AQ\u0011UA\u0002\u0001\u0004\t\t&A\u0003qe>D\u0018\u0010\u0003\u0005\u0002j\u0006\r\u0001\u0019AAw\u0011!)9+a\u0001A\u0002\t\u001d\u0011\u0001B1sONDc!a\u0001\u0006,\u00165\u0006CBA*\u0005o!\u0019o\t\u0002\u0005d\"2\u0011Q\u0001B\u001b\u0005\u000f\u0012QeU3sS\u0006d\u0017N_3e)f\u0004X\rZ!di>\u0014\u0018J\u001c<pG\u0006$\u0018n\u001c8IC:$G.\u001a:\u0014\u0011\u0005\u001d\u0011\u0011KAf\u0003#\fa!Y2u_J\u0004SCAC]!\u0011)Y,\"1\u000e\u0005\u0015u&\u0002BC`\t+\n\u0001\u0002Z;sCRLwN\\\u0005\u0005\u000b\u0007,iL\u0001\bGS:LG/\u001a#ve\u0006$\u0018n\u001c8\u0015\r\u0015\u001dW\u0011ZCf!\u0011\u0011\u0019\"a\u0002\t\u0011\u0005\u0005\u0013\u0011\u0003a\u0001\u000b\u007fA\u0001\"\"!\u0002\u0012\u0001\u0007Q\u0011\u0018\u0015\u0007\u0003'\u0011)Da\u0012\u0002;Q|G+\u001f9fI\u0006\u001bGo\u001c:J]Z|7-\u0019;j_:D\u0015M\u001c3mKJ$B!\"%\u0006T\"A\u0011\u0011PA\u000b\u0001\u0004\tY\b\u0006\u0004\u0006H\u0016]W\u0011\u001c\u0005\u000b\u0003\u0003\n9\u0002%AA\u0002\u0015}\u0002BCCA\u0003/\u0001\n\u00111\u0001\u0006:V\u0011QQ\u001c\u0016\u0005\u000b\u007f\u00119&\u0006\u0002\u0006b*\"Q\u0011\u0018B,)\u0011\u0011I)\":\t\u0015\tE\u0015\u0011EA\u0001\u0002\u0004\u0011y\b\u0006\u0003\u0003\u001e\u0015%\bB\u0003BI\u0003K\t\t\u00111\u0001\u0003\nR!!1OCw\u0011)\u0011\t*a\n\u0002\u0002\u0003\u0007!q\u0010\u000b\u0005\u0005;)\t\u0010\u0003\u0006\u0003\u0012\u00065\u0012\u0011!a\u0001\u0005\u0013\u000bQeU3sS\u0006d\u0017N_3e)f\u0004X\rZ!di>\u0014\u0018J\u001c<pG\u0006$\u0018n\u001c8IC:$G.\u001a:\u0011\t\tM\u0011\u0011G\n\u0007\u0003c)IPa4\u0011\u0015\t\u0015'1ZC \u000bs+9\r\u0006\u0002\u0006vR1QqYC\u0000\r\u0003A\u0001\"!\u0011\u00028\u0001\u0007Qq\b\u0005\t\u000b\u0003\u000b9\u00041\u0001\u0006:R!aQ\u0001D\u0005!\u0019\t\u0019F!9\u0007\bAA\u00111\u000bBt\u000b\u007f)I\f\u0003\u0006\u0003n\u0006e\u0012\u0011!a\u0001\u000b\u000fD3\"AAY\u0003o\u000bI,!0\u0002@\"Z\u0001!!-\u00028\u0006e\u0016QXA`\u0001")
public final class TypedActor {
    public static ExecutionContextExecutor dispatcher() {
        return TypedActor$.MODULE$.dispatcher();
    }

    public static ActorContext context() {
        return TypedActor$.MODULE$.context();
    }

    public static <T> T self() {
        return TypedActor$.MODULE$.self();
    }

    public static TypedActorFactory get(ActorContext context) {
        return TypedActor$.MODULE$.get(context);
    }

    public static TypedActorFactory apply(ActorContext context) {
        return TypedActor$.MODULE$.apply(context);
    }

    public static TypedActorExtension createExtension(ExtendedActorSystem system) {
        return TypedActor$.MODULE$.createExtension(system);
    }

    public static TypedActor$ lookup() {
        return TypedActor$.MODULE$.lookup();
    }

    public static TypedActorExtension get(ClassicActorSystemProvider system) {
        return TypedActor$.MODULE$.get(system);
    }

    public static TypedActorExtension get(ActorSystem system) {
        return TypedActor$.MODULE$.get(system);
    }

    public static boolean equals(Object other) {
        return TypedActor$.MODULE$.equals(other);
    }

    public static int hashCode() {
        return TypedActor$.MODULE$.hashCode();
    }

    public static Extension apply(ClassicActorSystemProvider system) {
        return TypedActor$.MODULE$.apply(system);
    }

    public static Extension apply(ActorSystem system) {
        return TypedActor$.MODULE$.apply(system);
    }

    public static final class MethodCall
    implements Product,
    Serializable {
        private final Method method;
        private final Object[] parameters;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Method method() {
            return this.method;
        }

        public Object[] parameters() {
            return this.parameters;
        }

        public boolean isOneWay() {
            Class<?> clazz = this.method().getReturnType();
            Class<Void> clazz2 = Void.TYPE;
            return !(clazz != null ? !clazz.equals(clazz2) : clazz2 != null);
        }

        public boolean returnsFuture() {
            return Future.class.isAssignableFrom(this.method().getReturnType());
        }

        public boolean returnsJOption() {
            return akka.japi.Option.class.isAssignableFrom(this.method().getReturnType());
        }

        public boolean returnsOption() {
            return Option.class.isAssignableFrom(this.method().getReturnType());
        }

        public Object apply(Object instance) {
            Object object;
            try {
                Object[] objectArray = this.parameters();
                object = objectArray == null ? this.method().invoke(instance, new Object[0]) : (objectArray.length == 0 ? this.method().invoke(instance, new Object[0]) : this.method().invoke(instance, objectArray));
            }
            catch (InvocationTargetException i) {
                throw i.getTargetException();
            }
            return object;
        }

        private Object writeReplace() throws ObjectStreamException {
            Object[] objectArray = this.parameters();
            if (objectArray == null) {
                return new SerializedMethodCall(this.method().getDeclaringClass(), this.method().getName(), this.method().getParameterTypes(), null);
            }
            if (objectArray.length == 0) {
                return new SerializedMethodCall(this.method().getDeclaringClass(), this.method().getName(), this.method().getParameterTypes(), (Tuple3[])Array$.MODULE$.apply(Nil$.MODULE$, ClassTag$.MODULE$.apply(Tuple3.class)));
            }
            Serialization serialization = (Serialization)SerializationExtension$.MODULE$.apply((ActorSystem)JavaSerializer$.MODULE$.currentSystem().value());
            Tuple3[] serializedParameters = new Tuple3[objectArray.length];
            RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), objectArray.length).foreach$mVc$sp(i -> {
                Object p = objectArray[i];
                Serializer s2 = serialization.findSerializerFor(p);
                String m4 = Serializers$.MODULE$.manifestFor(s2, p);
                serializedParameters$1[i] = new Tuple3<Integer, String, byte[]>(BoxesRunTime.boxToInteger(s2.identifier()), m4, s2.toBinary(this.parameters()[i]));
            });
            return new SerializedMethodCall(this.method().getDeclaringClass(), this.method().getName(), this.method().getParameterTypes(), serializedParameters);
        }

        public MethodCall copy(Method method2, Object[] parameters) {
            return new MethodCall(method2, parameters);
        }

        public Method copy$default$1() {
            return this.method();
        }

        public Object[] copy$default$2() {
            return this.parameters();
        }

        @Override
        public String productPrefix() {
            return "MethodCall";
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
                    return this.method();
                }
                case 1: {
                    return this.parameters();
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
            return x$1 instanceof MethodCall;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "method";
                }
                case 1: {
                    return "parameters";
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
            if (!(object instanceof MethodCall)) return false;
            boolean bl = true;
            if (!bl) return false;
            MethodCall methodCall = (MethodCall)x$1;
            Method method2 = this.method();
            Method method3 = methodCall.method();
            if (method2 == null) {
                if (method3 != null) {
                    return false;
                }
            } else if (!((Object)method2).equals(method3)) return false;
            if (this.parameters() != methodCall.parameters()) return false;
            return true;
        }

        public MethodCall(Method method2, Object[] parameters) {
            this.method = method2;
            this.parameters = parameters;
            Product.$init$(this);
        }
    }

    public static interface PostRestart {
        public void postRestart(Throwable var1);
    }

    public static interface PostStop {
        public void postStop();
    }

    public static interface PreRestart {
        public void preRestart(Throwable var1, Option<Object> var2);
    }

    public static interface PreStart {
        public void preStart();
    }

    public static interface Receiver {
        public void onReceive(Object var1, ActorRef var2);
    }

    public static final class SerializedMethodCall
    implements Product,
    Serializable {
        private final Class<?> ownerType;
        private final String methodName;
        private final Class<?>[] parameterTypes;
        private final Tuple3<Object, String, byte[]>[] serializedParameters;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Class<?> ownerType() {
            return this.ownerType;
        }

        public String methodName() {
            return this.methodName;
        }

        public Class<?>[] parameterTypes() {
            return this.parameterTypes;
        }

        public Tuple3<Object, String, byte[]>[] serializedParameters() {
            return this.serializedParameters;
        }

        private Object readResolve() throws ObjectStreamException {
            Object[] objectArray;
            ExtendedActorSystem system = (ExtendedActorSystem)JavaSerializer$.MODULE$.currentSystem().value();
            if (system == null) {
                throw new IllegalStateException("Trying to deserialize a SerializedMethodCall without an ActorSystem in scope. Use akka.serialization.JavaSerializer.currentSystem.withValue(system) { ... }");
            }
            Serialization serialization = (Serialization)SerializationExtension$.MODULE$.apply(system);
            Method method2 = this.ownerType().getDeclaredMethod(this.methodName(), this.parameterTypes());
            Tuple3[] tuple3Array = this.serializedParameters();
            if (tuple3Array == null) {
                objectArray = null;
            } else if (tuple3Array.length == 0) {
                objectArray = (Object[])Array$.MODULE$.apply(Nil$.MODULE$, ClassTag$.MODULE$.AnyRef());
            } else {
                Object[] deserializedParameters = new Object[tuple3Array.length];
                RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), tuple3Array.length).foreach$mVc$sp(i -> {
                    Tuple3 tuple3 = tuple3Array[i];
                    if (tuple3 == null) {
                        throw new MatchError(tuple3);
                    }
                    int sId = BoxesRunTime.unboxToInt(tuple3._1());
                    String manifest = (String)tuple3._2();
                    byte[] bytes = (byte[])tuple3._3();
                    Tuple3<Integer, String, byte[]> tuple32 = new Tuple3<Integer, String, byte[]>(BoxesRunTime.boxToInteger(sId), manifest, bytes);
                    int sId2 = BoxesRunTime.unboxToInt(tuple32._1());
                    String manifest2 = tuple32._2();
                    byte[] bytes2 = tuple32._3();
                    deserializedParameters$1[i] = serialization.deserialize(bytes2, sId2, manifest2).get();
                });
                objectArray = deserializedParameters;
            }
            return new MethodCall(method2, objectArray);
        }

        public SerializedMethodCall copy(Class<?> ownerType, String methodName, Class<?>[] parameterTypes, Tuple3<Object, String, byte[]>[] serializedParameters) {
            return new SerializedMethodCall(ownerType, methodName, parameterTypes, serializedParameters);
        }

        public Class<?> copy$default$1() {
            return this.ownerType();
        }

        public String copy$default$2() {
            return this.methodName();
        }

        public Class<?>[] copy$default$3() {
            return this.parameterTypes();
        }

        public Tuple3<Object, String, byte[]>[] copy$default$4() {
            return this.serializedParameters();
        }

        @Override
        public String productPrefix() {
            return "SerializedMethodCall";
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
                    return this.ownerType();
                }
                case 1: {
                    return this.methodName();
                }
                case 2: {
                    return this.parameterTypes();
                }
                case 3: {
                    return this.serializedParameters();
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
            return x$1 instanceof SerializedMethodCall;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "ownerType";
                }
                case 1: {
                    return "methodName";
                }
                case 2: {
                    return "parameterTypes";
                }
                case 3: {
                    return "serializedParameters";
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
            if (!(object instanceof SerializedMethodCall)) return false;
            boolean bl = true;
            if (!bl) return false;
            SerializedMethodCall serializedMethodCall = (SerializedMethodCall)x$1;
            Class<?> clazz = this.ownerType();
            Class<?> clazz2 = serializedMethodCall.ownerType();
            if (clazz == null) {
                if (clazz2 != null) {
                    return false;
                }
            } else if (!clazz.equals(clazz2)) return false;
            String string = this.methodName();
            String string2 = serializedMethodCall.methodName();
            if (string == null) {
                if (string2 != null) {
                    return false;
                }
            } else if (!string.equals(string2)) return false;
            if (this.parameterTypes() != serializedMethodCall.parameterTypes()) return false;
            if (this.serializedParameters() != serializedMethodCall.serializedParameters()) return false;
            return true;
        }

        public SerializedMethodCall(Class<?> ownerType, String methodName, Class<?>[] parameterTypes, Tuple3<Object, String, byte[]>[] serializedParameters) {
            this.ownerType = ownerType;
            this.methodName = methodName;
            this.parameterTypes = parameterTypes;
            this.serializedParameters = serializedParameters;
            Product.$init$(this);
        }
    }

    public static final class SerializedTypedActorInvocationHandler
    implements Product,
    Serializable {
        private final ActorRef actor;
        private final FiniteDuration timeout;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public ActorRef actor() {
            return this.actor;
        }

        public FiniteDuration timeout() {
            return this.timeout;
        }

        private Object readResolve() throws ObjectStreamException {
            ExtendedActorSystem extendedActorSystem = (ExtendedActorSystem)JavaSerializer$.MODULE$.currentSystem().value();
            if (extendedActorSystem == null) {
                throw new IllegalStateException("SerializedTypedActorInvocationHandler.readResolve requires that JavaSerializer.currentSystem.value is set to a non-null value");
            }
            return this.toTypedActorInvocationHandler(extendedActorSystem);
        }

        public TypedActorInvocationHandler toTypedActorInvocationHandler(ActorSystem system) {
            return new TypedActorInvocationHandler((TypedActorExtension)TypedActor$.MODULE$.apply(system), new AtomicReference<ActorRef>(this.actor()), new Timeout(this.timeout()));
        }

        public SerializedTypedActorInvocationHandler copy(ActorRef actor, FiniteDuration timeout2) {
            return new SerializedTypedActorInvocationHandler(actor, timeout2);
        }

        public ActorRef copy$default$1() {
            return this.actor();
        }

        public FiniteDuration copy$default$2() {
            return this.timeout();
        }

        @Override
        public String productPrefix() {
            return "SerializedTypedActorInvocationHandler";
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
                    return this.actor();
                }
                case 1: {
                    return this.timeout();
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
            return x$1 instanceof SerializedTypedActorInvocationHandler;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "actor";
                }
                case 1: {
                    return "timeout";
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
            if (!(object instanceof SerializedTypedActorInvocationHandler)) return false;
            boolean bl = true;
            if (!bl) return false;
            SerializedTypedActorInvocationHandler serializedTypedActorInvocationHandler = (SerializedTypedActorInvocationHandler)x$1;
            ActorRef actorRef2 = this.actor();
            ActorRef actorRef3 = serializedTypedActorInvocationHandler.actor();
            if (actorRef2 == null) {
                if (actorRef3 != null) {
                    return false;
                }
            } else if (!((Object)actorRef2).equals(actorRef3)) return false;
            FiniteDuration finiteDuration = this.timeout();
            FiniteDuration finiteDuration2 = serializedTypedActorInvocationHandler.timeout();
            if (finiteDuration == null) {
                if (finiteDuration2 == null) return true;
                return false;
            } else {
                if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
                return true;
            }
        }

        public SerializedTypedActorInvocationHandler(ActorRef actor, FiniteDuration timeout2) {
            this.actor = actor;
            this.timeout = timeout2;
            Product.$init$(this);
        }
    }

    public static interface Supervisor {
        public SupervisorStrategy supervisorStrategy();
    }

    public static class TypedActor<R, T extends R>
    implements Actor {
        private final AtomicReference<R> proxyVar;
        private final T akka$actor$TypedActor$TypedActor$$me;
        private ActorContext context;
        private ActorRef self;

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
        public void unhandled(Object message) {
            Actor.unhandled$(this, message);
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

        private /* synthetic */ void super$preStart() {
            Actor.preStart$(this);
        }

        private /* synthetic */ void super$postStop() {
            Actor.postStop$(this);
        }

        private /* synthetic */ void super$postRestart(Throwable reason) {
            Actor.postRestart$(this, reason);
        }

        public AtomicReference<R> proxyVar() {
            return this.proxyVar;
        }

        public T akka$actor$TypedActor$TypedActor$$me() {
            return this.akka$actor$TypedActor$TypedActor$$me;
        }

        @Override
        public SupervisorStrategy supervisorStrategy() {
            T t2 = this.akka$actor$TypedActor$TypedActor$$me();
            if (t2 instanceof Supervisor) {
                T t3 = t2;
                return ((Supervisor)t3).supervisorStrategy();
            }
            return Actor.supervisorStrategy$(this);
        }

        @Override
        public void preStart() {
            this.withContext(() -> {
                Object t2 = this.akka$actor$TypedActor$TypedActor$$me();
                if (t2 instanceof PreStart) {
                    Object t3 = t2;
                    ((PreStart)t3).preStart();
                    return;
                }
                this.super$preStart();
            });
        }

        @Override
        public void postStop() {
            try {
                this.withContext(() -> {
                    Object t2 = this.akka$actor$TypedActor$TypedActor$$me();
                    if (t2 instanceof PostStop) {
                        Object t3 = t2;
                        ((PostStop)t3).postStop();
                        return;
                    }
                    this.super$postStop();
                });
            }
            finally {
                TypedActorInvocationHandler typedActorInvocationHandler = ((TypedActorExtension)TypedActor$.MODULE$.apply(this.context().system())).invocationHandlerFor(this.proxyVar().get());
                if (typedActorInvocationHandler == null) {
                } else {
                    typedActorInvocationHandler.actorVar().set(this.context().system().deadLetters());
                    this.proxyVar().set(null);
                }
            }
        }

        @Override
        public void preRestart(Throwable reason, Option<Object> message) {
            this.withContext(() -> {
                Object t2 = this.akka$actor$TypedActor$TypedActor$$me();
                if (t2 instanceof PreRestart) {
                    Object t3 = t2;
                    ((PreRestart)t3).preRestart(reason, message);
                    return;
                }
                this.context().children().foreach((Function1<ActorRef, Object> & Serializable)actor -> {
                    TypedActor.$anonfun$preRestart$2(this, actor);
                    return BoxedUnit.UNIT;
                });
            });
        }

        @Override
        public void postRestart(Throwable reason) {
            this.withContext(() -> {
                Object t2 = this.akka$actor$TypedActor$TypedActor$$me();
                if (t2 instanceof PostRestart) {
                    Object t3 = t2;
                    ((PostRestart)t3).postRestart(reason);
                    return;
                }
                this.super$postRestart(reason);
            });
        }

        public <U> U withContext(Function0<U> unitOfWork) {
            U u;
            TypedActor$.MODULE$.akka$actor$TypedActor$$selfReference().set(this.proxyVar().get());
            TypedActor$.MODULE$.akka$actor$TypedActor$$currentContext().set(this.context());
            try {
                u = unitOfWork.apply();
            }
            finally {
                TypedActor$.MODULE$.akka$actor$TypedActor$$selfReference().set(null);
                TypedActor$.MODULE$.akka$actor$TypedActor$$currentContext().set(null);
            }
            return u;
        }

        @Override
        public PartialFunction<Object, BoxedUnit> receive() {
            return new Serializable(this){
                private static final long serialVersionUID = 0L;
                private final /* synthetic */ TypedActor $outer;

                public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                    A1 A1 = x1;
                    if (A1 instanceof MethodCall) {
                        MethodCall methodCall = (MethodCall)A1;
                        this.$outer.withContext(() -> TypedActor$$anonfun$receive$1.$anonfun$applyOrElse$1(this, methodCall));
                        return (B1)BoxedUnit.UNIT;
                    }
                    if (this.$outer.akka$actor$TypedActor$TypedActor$$me() instanceof Receiver) {
                        return (B1)this.$outer.withContext(() -> ((Receiver)$this.$outer.akka$actor$TypedActor$TypedActor$$me()).onReceive(A1, $this.$outer.sender()));
                    }
                    return function1.apply(x1);
                }

                public final boolean isDefinedAt(Object x1) {
                    Object object = x1;
                    if (object instanceof MethodCall) {
                        return true;
                    }
                    return this.$outer.akka$actor$TypedActor$TypedActor$$me() instanceof Receiver;
                }

                public static final /* synthetic */ void $anonfun$applyOrElse$2(TypedActor$$anonfun$receive$1 $this, ActorRef s$1, Try x0$1) {
                    Try try_ = x0$1;
                    if (try_ instanceof Success) {
                        Success success = (Success)try_;
                        T result = success.value();
                        if (result == null) {
                            s$1.$bang(TypedActor$NullResponse$.MODULE$, $this.$outer.self());
                            return;
                        }
                        s$1.$bang(result, $this.$outer.self());
                        return;
                    }
                    if (try_ instanceof Failure) {
                        Failure failure = (Failure)try_;
                        Throwable f = failure.exception();
                        s$1.$bang(new Status.Failure(f), $this.$outer.self());
                        return;
                    }
                    throw new MatchError(try_);
                }

                /*
                 * Unable to fully structure code
                 */
                public static final /* synthetic */ Object $anonfun$applyOrElse$1(TypedActor$$anonfun$receive$1 $this, MethodCall x2$1) {
                    if (x2$1.isOneWay()) {
                        return x2$1.apply($this.$outer.akka$actor$TypedActor$TypedActor$$me());
                    }
                    try {
                        s = $this.$outer.sender();
                        var5_3 = x2$1.apply($this.$outer.akka$actor$TypedActor$TypedActor$$me());
                        if (!(var5_3 instanceof Future)) ** GOTO lbl-1000
                        var6_4 = (Future)var5_3;
                        if (x2$1.returnsFuture()) {
                            dispatcher = $this.$outer.context().dispatcher();
                            var6_4.onComplete((Function1<Try, Object> & Serializable)LambdaMetafactory.altMetafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, $anonfun$applyOrElse$2$adapted(akka.actor.TypedActor$TypedActor$$anonfun$receive$1 akka.actor.ActorRef scala.util.Try ), (Lscala/util/Try;)Ljava/lang/Object;)($this, (ActorRef)s), dispatcher);
                        } else if (var5_3 == null) {
                            s.$bang(TypedActor$NullResponse$.MODULE$, $this.$outer.self());
                        } else {
                            s.$bang(var5_3, $this.$outer.self());
                        }
                        v0 = BoxedUnit.UNIT;
                    }
                    catch (Throwable var8_6) {
                        var9_7 = var8_6;
                        if (var9_7 != null && !(var10_8 = NonFatal$.MODULE$.unapply(var9_7)).isEmpty()) {
                            e = var10_8.get();
                            $this.$outer.sender().$bang(new Status.Failure(e), $this.$outer.self());
                            throw e;
                        }
                        throw var8_6;
                    }
                    return v0;
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                }

                public static final /* synthetic */ Object $anonfun$applyOrElse$2$adapted(TypedActor$$anonfun$receive$1 $this, ActorRef s$1, Try x0$1) {
                    TypedActor$$anonfun$receive$1.$anonfun$applyOrElse$2($this, s$1, x0$1);
                    return BoxedUnit.UNIT;
                }

                private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                    return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1(akka.actor.TypedActor$TypedActor$$anonfun$receive$1 akka.actor.TypedActor$MethodCall ), $anonfun$applyOrElse$2$adapted(akka.actor.TypedActor$TypedActor$$anonfun$receive$1 akka.actor.ActorRef scala.util.Try ), $anonfun$applyOrElse$3(akka.actor.TypedActor$TypedActor$$anonfun$receive$1 java.lang.Object )}, serializedLambda);
                }
            };
        }

        public static final /* synthetic */ void $anonfun$preRestart$2(TypedActor $this, ActorRef actor) {
            $this.context().stop(actor);
        }

        public TypedActor(AtomicReference<R> proxyVar, Function0<T> createInstance, Seq<Class<?>> interfaces) {
            this.proxyVar = proxyVar;
            Actor.$init$(this);
            BoxedUnit boxedUnit = !((InternalActorRef)this.context().parent()).isLocal() ? TypedActor$.MODULE$.get(this.context().system()).createActorRefProxy(TypedProps$.MODULE$.apply(interfaces, createInstance), proxyVar, (Function0<ActorRef> & Serializable)() -> this.context().self()) : BoxedUnit.UNIT;
            this.akka$actor$TypedActor$TypedActor$$me = this.withContext(createInstance);
            Statics.releaseFence();
        }
    }

    public static class TypedActorInvocationHandler
    implements InvocationHandler,
    Serializable {
        private final transient TypedActorExtension extension;
        private final transient AtomicReference<ActorRef> actorVar;
        private final transient Timeout timeout;

        public TypedActorExtension extension() {
            return this.extension;
        }

        public AtomicReference<ActorRef> actorVar() {
            return this.actorVar;
        }

        public Timeout timeout() {
            return this.timeout;
        }

        public ActorRef actor() {
            return this.actorVar().get();
        }

        /*
         * Unable to fully structure code
         * Could not resolve type clashes
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            var9_4 = method.getName();
            switch (var9_4 == null ? 0 : var9_4.hashCode()) {
                case -1776922004: {
                    if (!"toString".equals(var9_4)) break;
                    return this.actor().toString();
                }
                case -1295482945: {
                    if (!"equals".equals(var9_4)) break;
                    if (args.length == 1 && proxy == args[0]) ** GOTO lbl-1000
                    v0 = this.actor();
                    var10_5 = this.extension().getActorRefFor(args[0]);
                    if (!(v0 != null ? v0.equals(var10_5) == false : var10_5 != null)) lbl-1000:
                    // 2 sources

                    {
                        v1 = true;
                    } else {
                        v1 = false;
                    }
                    return BoxesRunTime.boxToBoolean(v1);
                }
                case 147696667: {
                    if (!"hashCode".equals(var9_4)) break;
                    return BoxesRunTime.boxToInteger(this.actor().hashCode());
                }
            }
            dispatcher = this.extension().system().dispatcher();
            var12_7 = new MethodCall(method, args);
            if (var12_7.isOneWay()) {
                qual$1 = this.actor();
                x$1 = var12_7;
                x$2 = qual$1.$bang$default$2(x$1);
                qual$1.$bang(x$1, x$2);
                return null;
            }
            if (var12_7.returnsFuture()) {
                return package$.MODULE$.ask(this.actor(), (Object)var12_7, this.timeout()).map((Function1<Object, Object> & Serializable)LambdaMetafactory.altMetafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, $anonfun$invoke$1(java.lang.Object ), (Ljava/lang/Object;)Ljava/lang/Object;)(), dispatcher);
            }
            if (var12_7.returnsJOption() || var12_7.returnsOption()) {
                f = package$.MODULE$.ask(this.actor(), (Object)var12_7, this.timeout());
                try {
                    v2 /* !! */  = ((Future)Await$.MODULE$.ready(f, this.timeout().duration())).value();
                }
                catch (TimeoutException v3) {
                    v2 /* !! */  = var17_12 = None$.MODULE$;
                }
                if (None$.MODULE$.equals(var17_12) != false ? true : (var17_12 instanceof Some != false && (var19_14 = (Try)(var18_13 = (Some)var17_12).value()) instanceof Success != false && TypedActor$NullResponse$.MODULE$.equals(var21_16 = (var20_15 = (Success)var19_14).value()) != false ? true : var17_12 instanceof Some != false && (var23_18 = (Try)(var22_17 = (Some)var17_12).value()) instanceof Failure != false && (var24_19 = (Failure)var23_18).exception() instanceof AskTimeoutException != false)) {
                    if (var12_7.returnsJOption()) {
                        return Option$.MODULE$.none();
                    }
                    return None$.MODULE$;
                }
                if (var17_12 instanceof Some && (t = (Try)(var25_20 = (Some)var17_12).value()) instanceof Try) {
                    var27_22 = t;
                    return var27_22.get();
                }
                throw new MatchError(var17_12);
            }
            var28_23 = Await$.MODULE$.result(package$.MODULE$.ask(this.actor(), (Object)var12_7, this.timeout()), this.timeout().duration());
            if (TypedActor$NullResponse$.MODULE$.equals(var28_23)) {
                return null;
            }
            return var28_23;
        }

        private Object writeReplace() throws ObjectStreamException {
            return new SerializedTypedActorInvocationHandler(this.actor(), this.timeout().duration());
        }

        public static final /* synthetic */ Object $anonfun$invoke$1(Object x0$1) {
            Object object = x0$1;
            if (TypedActor$NullResponse$.MODULE$.equals(object)) {
                return null;
            }
            return object;
        }

        public TypedActorInvocationHandler(TypedActorExtension extension, AtomicReference<ActorRef> actorVar, Timeout timeout2) {
            this.extension = extension;
            this.actorVar = actorVar;
            this.timeout = timeout2;
        }
    }
}

