/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.NoSerializationVerificationNeeded;
import akka.annotation.InternalApi;
import akka.event.LogMarker$;
import akka.event.LogSource$;
import akka.event.Logging$;
import akka.event.LoggingAdapter;
import akka.event.MarkerLoggingAdapter;
import akka.serialization.ByteBufferSerializer;
import akka.serialization.DisabledJavaSerializer;
import akka.serialization.JavaSerializer;
import akka.serialization.NullSerializer$;
import akka.serialization.Serialization$;
import akka.serialization.SerializationSetup;
import akka.serialization.Serializer;
import akka.serialization.SerializerDetails;
import akka.serialization.SerializerDetails$;
import akka.serialization.SerializerWithStringManifest;
import akka.util.ccompat.package$JavaConverters$;
import com.typesafe.config.Config;
import java.io.NotSerializableException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import scala.$less$colon$less$;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.collection.Iterable;
import scala.collection.IterableFactory$;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.MapOps;
import scala.collection.SeqFactory;
import scala.collection.SeqFactory$UnapplySeqWrapper$;
import scala.collection.SeqOps;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Seq$;
import scala.collection.mutable.ArrayBuffer;
import scala.package$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.Try$;

@ScalaSignature(bytes="\u0006\u0005\u0011%w!B/_\u0011\u0003\u0019g!B3_\u0011\u00031\u0007\"B7\u0002\t\u0003qW\u0001B8\u0002\u0001AD!\"a\u0007\u0002\u0005\u0004%\t\u0001YA\u000f\u0011!\tY/\u0001Q\u0001\n\u0005}aABA}\u0003\u0001\tY\u0010\u0003\u0006\u0002~\u001a\u0011)\u0019!C\u0001\u0003\u007fD!Ba\u0005\u0007\u0005\u0003\u0005\u000b\u0011\u0002B\u0001\u0011\u0019ig\u0001\"\u0001\u0003\u0016!I!1\u0004\u0004C\u0002\u0013\u0005!Q\u0004\u0005\t\u0005S1\u0001\u0015!\u0003\u0003 !I!1\u0006\u0004C\u0002\u0013\u0005!Q\u0004\u0005\t\u0005[1\u0001\u0015!\u0003\u0003 !9!q\u0006\u0004\u0005\u000e\tE\u0002b\u0002B\u001c\u0003\u0011\u0005!\u0011\b\u0004\u0007\u0003_\t!)!\r\t\u0015\u0005-\u0003C!f\u0001\n\u0003\ti\u0005\u0003\u0006\u0002\\A\u0011\t\u0012)A\u0005\u0003\u001fB!\"!\u0018\u0011\u0005+\u0007I\u0011AA0\u0011)\t9\u0007\u0005B\tB\u0003%\u0011\u0011\r\u0005\u0007[B!\t!!\u001b\t\u0013\u0005=\u0004#!A\u0005\u0002\u0005E\u0004\"CA<!E\u0005I\u0011AA=\u0011%\ty\tEI\u0001\n\u0003\t\t\nC\u0005\u0002\u0016B\t\t\u0011\"\u0011\u0002\u0018\"I\u0011\u0011\u0016\t\u0002\u0002\u0013\u0005\u00111\u0016\u0005\n\u0003g\u0003\u0012\u0011!C\u0001\u0003kC\u0011\"a/\u0011\u0003\u0003%\t%!0\t\u0013\u0005-\u0007#!A\u0005\u0002\u00055\u0007\"CAl!\u0005\u0005I\u0011IAm\u0011%\ti\u000eEA\u0001\n\u0003\ny\u000eC\u0005\u0002bB\t\t\u0011\"\u0011\u0002d\"I\u0011Q\u001d\t\u0002\u0002\u0013\u0005\u0013q]\u0004\n\u0005\u000b\n\u0011\u0011!E\u0001\u0005\u000f2\u0011\"a\f\u0002\u0003\u0003E\tA!\u0013\t\r5\u001cC\u0011\u0001B1\u0011%\t\toIA\u0001\n\u000b\n\u0019\u000fC\u0005\u0003d\r\n\t\u0011\"!\u0003f!I!1N\u0012\u0002\u0002\u0013\u0005%Q\u000e\u0005\n\u0005w\u001a\u0013\u0011!C\u0005\u0005{BqA!\"\u0002\t\u0003\u00119\tC\u0004\u0003&\u0006!\tAa*\u0007\u000b\u0015t\u0006A!+\t\u0015\u0005u3F!b\u0001\n\u0003\u0011\t\f\u0003\u0006\u0002h-\u0012\t\u0011)A\u0005\u0005?Ca!\\\u0016\u0005\u0002\tM\u0006\"\u0003B]W\t\u0007I\u0011\u0001B^\u0011!\u0011\tm\u000bQ\u0001\n\tu\u0006\"\u0003BbW\t\u0007I\u0011\u0001Bc\u0011!\u00119m\u000bQ\u0001\n\u0005=\u0007\u0002\u0003BeW\u0001\u0006IAa3\t\u0013\t]7F1A\u0005\u0002\te\u0007\u0002\u0003BqW\u0001\u0006IAa7\t\u0013\t\r8F1A\u0005\n\t\u0015\b\u0002CB\tW\u0001\u0006IAa:\t\u0011\rM1\u0006\"\u0001a\u0007+AqA!\",\t\u0013\u0019Y\u0002C\u0004\u0004(-\"\ta!\u000b\t\u000f\r\u00053\u0006\"\u0001\u0004D!91\u0011I\u0016\u0005\u0002\r-\u0005bBBLW\u0011%1\u0011\u0014\u0005\b\u0007G[C\u0011ABS\u0011\u001d\u0019\te\u000bC\u0001\u0007\u0017Dqaa7,\t\u0003\u0019i\u000eC\u0004\u0004b.\"\taa9\t\u000f\rM8\u0006\"\u0001\u0004v\"911_\u0016\u0005\n\ru\b\"\u0003C\u0003W\t\u0007I\u0011\u0002C\u0004\u0011!!)b\u000bQ\u0001\n\u0011%\u0001\"\u0003C\fW\t\u0007I\u0011\u0002C\r\u0011!!ib\u000bQ\u0001\n\u0011m\u0001B\u0003C\u0010W\t\u0007I\u0011\u00011\u0005\"!AAqE\u0016!\u0002\u0013!\u0019\u0003C\u0004\u0005*-\"I\u0001b\u000b\t\u000f\u0011m2\u0006\"\u0003\u0005>!9AqJ\u0016\u0005\n\u0011E\u0003b\u0002C,W\u0011%A\u0011\f\u0005\b\t;ZC\u0011\u0002C0\u0011\u001d!9g\u000bC\u0005\tSB\u0011\u0002\"\u001e,\u0005\u0004%I\u0001b\u001e\t\u0011\u0011-5\u0006)A\u0005\tsB\u0011\u0002\"$,\u0005\u0004%\t\u0001b$\t\u0011\u0011M5\u0006)A\u0005\t#C\u0011\u0002\"&,\u0005\u0004%I\u0001b&\t\u0011\u0011m5\u0006)A\u0005\t3Cq\u0001\"(,\t\u0013!y\nC\u0005\u0005&.\u0012\r\u0011\"\u0003\u0003F\"AAqU\u0016!\u0002\u0013\ty\rC\u0005\u0005*.\u0012\r\u0011\"\u0003\u0003F\"AA1V\u0016!\u0002\u0013\ty\rC\u0004\u0005..\"I\u0001b,\t\u0011\u0011M6\u0006\"\u0001a\tk\u000bQbU3sS\u0006d\u0017N_1uS>t'BA0a\u00035\u0019XM]5bY&T\u0018\r^5p]*\t\u0011-\u0001\u0003bW.\f7\u0001\u0001\t\u0003I\u0006i\u0011A\u0018\u0002\u000e'\u0016\u0014\u0018.\u00197ju\u0006$\u0018n\u001c8\u0014\u0005\u00059\u0007C\u00015l\u001b\u0005I'\"\u00016\u0002\u000bM\u001c\u0017\r\\1\n\u00051L'AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002G\ny1\t\\1tgN+'/[1mSj,'\u000fE\u0003icN\f)\"\u0003\u0002sS\n1A+\u001e9mKJ\u00024\u0001^A\u0002!\r)Hp \b\u0003mj\u0004\"a^5\u000e\u0003aT!!\u001f2\u0002\rq\u0012xn\u001c;?\u0013\tY\u0018.\u0001\u0004Qe\u0016$WMZ\u0005\u0003{z\u0014Qa\u00117bgNT!a_5\u0011\t\u0005\u0005\u00111\u0001\u0007\u0001\t-\t)aAA\u0001\u0002\u0003\u0015\t!a\u0002\u0003\u0007}#\u0013'\u0005\u0003\u0002\n\u0005=\u0001c\u00015\u0002\f%\u0019\u0011QB5\u0003\u000f9{G\u000f[5oOB\u0019\u0001.!\u0005\n\u0007\u0005M\u0011NA\u0002B]f\u00042\u0001ZA\f\u0013\r\tIB\u0018\u0002\u000b'\u0016\u0014\u0018.\u00197ju\u0016\u0014\u0018aG2veJ,g\u000e\u001e+sC:\u001c\bo\u001c:u\u0013:4wN]7bi&|g.\u0006\u0002\u0002 A1\u0011\u0011EA\u0014\u0003Wi!!a\t\u000b\u0007\u0005\u0015\u0012.\u0001\u0003vi&d\u0017\u0002BA\u0015\u0003G\u0011q\u0002R=oC6L7MV1sS\u0006\u0014G.\u001a\t\u0004\u0003[\u0001R\"A\u0001\u0003\u0017%sgm\u001c:nCRLwN\\\n\u0007!\u001d\f\u0019$!\u000f\u0011\u0007!\f)$C\u0002\u00028%\u0014q\u0001\u0015:pIV\u001cG\u000f\u0005\u0003\u0002<\u0005\u0015c\u0002BA\u001f\u0003\u0003r1a^A \u0013\u0005Q\u0017bAA\"S\u00069\u0001/Y2lC\u001e,\u0017\u0002BA$\u0003\u0013\u0012AbU3sS\u0006d\u0017N_1cY\u0016T1!a\u0011j\u0003\u001d\tG\r\u001a:fgN,\"!a\u0014\u0011\t\u0005E\u0013qK\u0007\u0003\u0003'R1!!\u0016a\u0003\u0015\t7\r^8s\u0013\u0011\tI&a\u0015\u0003\u000f\u0005#GM]3tg\u0006A\u0011\r\u001a3sKN\u001c\b%\u0001\u0004tsN$X-\\\u000b\u0003\u0003C\u0002B!!\u0015\u0002d%!\u0011QMA*\u0005-\t5\r^8s'f\u001cH/Z7\u0002\u000fML8\u000f^3nAQ1\u00111FA6\u0003[Bq!a\u0013\u0016\u0001\u0004\ty\u0005C\u0004\u0002^U\u0001\r!!\u0019\u0002\t\r|\u0007/\u001f\u000b\u0007\u0003W\t\u0019(!\u001e\t\u0013\u0005-c\u0003%AA\u0002\u0005=\u0003\"CA/-A\u0005\t\u0019AA1\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\"!a\u001f+\t\u0005=\u0013QP\u0016\u0003\u0003\u007f\u0002B!!!\u0002\f6\u0011\u00111\u0011\u0006\u0005\u0003\u000b\u000b9)A\u0005v]\u000eDWmY6fI*\u0019\u0011\u0011R5\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002\u000e\u0006\r%!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012TCAAJU\u0011\t\t'! \u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\tI\n\u0005\u0003\u0002\u001c\u0006\u0015VBAAO\u0015\u0011\ty*!)\u0002\t1\fgn\u001a\u0006\u0003\u0003G\u000bAA[1wC&!\u0011qUAO\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\u0011\u0011Q\u0016\t\u0004Q\u0006=\u0016bAAYS\n\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!\u0011qBA\\\u0011%\tIlGA\u0001\u0002\u0004\ti+A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003\u007f\u0003b!!1\u0002H\u0006=QBAAb\u0015\r\t)-[\u0001\u000bG>dG.Z2uS>t\u0017\u0002BAe\u0003\u0007\u0014\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!\u0011qZAk!\rA\u0017\u0011[\u0005\u0004\u0003'L'a\u0002\"p_2,\u0017M\u001c\u0005\n\u0003sk\u0012\u0011!a\u0001\u0003\u001f\t!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011\u0011TAn\u0011%\tILHA\u0001\u0002\u0004\ti+\u0001\u0005iCND7i\u001c3f)\t\ti+\u0001\u0005u_N#(/\u001b8h)\t\tI*\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003\u001f\fI\u000fC\u0005\u0002:\u0006\n\t\u00111\u0001\u0002\u0010\u0005a2-\u001e:sK:$HK]1ogB|'\u000f^%oM>\u0014X.\u0019;j_:\u0004\u0003fA\u0003\u0002pB!\u0011\u0011_A{\u001b\t\t\u0019PC\u0002\u0002\n\u0002LA!a>\u0002t\nY\u0011J\u001c;fe:\fG.\u00119j\u0005!\u0019V\r\u001e;j]\u001e\u001c8C\u0001\u0004h\u0003\u0019\u0019wN\u001c4jOV\u0011!\u0011\u0001\t\u0005\u0005\u0007\u0011y!\u0004\u0002\u0003\u0006)!\u0011Q B\u0004\u0015\u0011\u0011IAa\u0003\u0002\u0011QL\b/Z:bM\u0016T!A!\u0004\u0002\u0007\r|W.\u0003\u0003\u0003\u0012\t\u0015!AB\"p]\u001aLw-A\u0004d_:4\u0017n\u001a\u0011\u0015\t\t]!\u0011\u0004\t\u0004\u0003[1\u0001bBA\u007f\u0013\u0001\u0007!\u0011A\u0001\f'\u0016\u0014\u0018.\u00197ju\u0016\u00148/\u0006\u0002\u0003 A9QO!\t\u0003&\t\u0015\u0012b\u0001B\u0012}\n\u0019Q*\u00199\u0011\u0007U\u00149#C\u0002\u0002(z\fAbU3sS\u0006d\u0017N_3sg\u0002\nQcU3sS\u0006d\u0017N_1uS>t')\u001b8eS:<7/\u0001\fTKJL\u0017\r\\5{CRLwN\u001c\"j]\u0012LgnZ:!\u0003-\u0019wN\u001c4jOR{W*\u00199\u0015\t\t}!1\u0007\u0005\b\u0005kq\u0001\u0019\u0001B\u0001\u0003\r\u0019gmZ\u0001\u0014g\u0016\u0014\u0018.\u00197ju\u0016$\u0017i\u0019;peB\u000bG\u000f\u001b\u000b\u0005\u0005K\u0011Y\u0004C\u0004\u0003>=\u0001\rAa\u0010\u0002\u0011\u0005\u001cGo\u001c:SK\u001a\u0004B!!\u0015\u0003B%!!1IA*\u0005!\t5\r^8s%\u00164\u0017aC%oM>\u0014X.\u0019;j_:\u00042!!\f$'\u0015\u0019#1\nB,!)\u0011iEa\u0015\u0002P\u0005\u0005\u00141F\u0007\u0003\u0005\u001fR1A!\u0015j\u0003\u001d\u0011XO\u001c;j[\u0016LAA!\u0016\u0003P\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\u001c\u001a\u0011\t\te#qL\u0007\u0003\u00057RAA!\u0018\u0002\"\u0006\u0011\u0011n\\\u0005\u0005\u0003\u000f\u0012Y\u0006\u0006\u0002\u0003H\u0005)\u0011\r\u001d9msR1\u00111\u0006B4\u0005SBq!a\u0013'\u0001\u0004\ty\u0005C\u0004\u0002^\u0019\u0002\r!!\u0019\u0002\u000fUt\u0017\r\u001d9msR!!q\u000eB<!\u0015A'\u0011\u000fB;\u0013\r\u0011\u0019(\u001b\u0002\u0007\u001fB$\u0018n\u001c8\u0011\r!\f\u0018qJA1\u0011%\u0011IhJA\u0001\u0002\u0004\tY#A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"Aa \u0011\t\u0005m%\u0011Q\u0005\u0005\u0005\u0007\u000biJ\u0001\u0004PE*,7\r^\u0001\u0019o&$\b\u000e\u0016:b]N\u0004xN\u001d;J]\u001a|'/\\1uS>tW\u0003\u0002BE\u0005\u001f#BAa#\u0003\u001eR!!Q\u0012BJ!\u0011\t\tAa$\u0005\u000f\tE\u0015F1\u0001\u0002\b\t\tA\u000bC\u0004\u0003\u0016&\u0002\rAa&\u0002\u0003\u0019\u0004R\u0001\u001bBM\u0005\u001bK1Aa'j\u0005%1UO\\2uS>t\u0007\u0007C\u0004\u0002^%\u0002\rAa(\u0011\t\u0005E#\u0011U\u0005\u0005\u0005G\u000b\u0019FA\nFqR,g\u000eZ3e\u0003\u000e$xN]*zgR,W.\u0001\u0010hKR\u001cUO\u001d:f]R$&/\u00198ta>\u0014H/\u00138g_Jl\u0017\r^5p]R\u0011\u00111F\n\u0005W\u001d\u0014Y\u000b\u0005\u0003\u0002R\t5\u0016\u0002\u0002BX\u0003'\u0012\u0011\"\u0012=uK:\u001c\u0018n\u001c8\u0016\u0005\t}E\u0003\u0002B[\u0005o\u0003\"\u0001Z\u0016\t\u000f\u0005uc\u00061\u0001\u0003 \u0006A1/\u001a;uS:<7/\u0006\u0002\u0003>B\u0019!q\u0018\u0004\u000f\u0005\u0011\u0004\u0011!C:fiRLgnZ:!\u0003Y\tE\u000e\\8x\u0015\u00064\u0018mU3sS\u0006d\u0017N_1uS>tWCAAh\u0003]\tE\u000e\\8x\u0015\u00064\u0018mU3sS\u0006d\u0017N_1uS>t\u0007%\u0001\u0003`Y><\u0007\u0003\u0002Bg\u0005'l!Aa4\u000b\u0007\tE\u0007-A\u0003fm\u0016tG/\u0003\u0003\u0003V\n='\u0001F'be.,'\u000fT8hO&tw-\u00113baR,'/A\u0002m_\u001e,\"Aa7\u0011\t\t5'Q\\\u0005\u0005\u0005?\u0014yM\u0001\bM_\u001e<\u0017N\\4BI\u0006\u0004H/\u001a:\u0002\t1|w\rI\u0001\u000e[\u0006t\u0017NZ3ti\u000e\u000b7\r[3\u0016\u0005\t\u001d\bC\u0002Bu\u0005k\u0014I0\u0004\u0002\u0003l*!!Q\u001eBx\u0003\u0019\tGo\\7jG*!!\u0011\u001fBz\u0003)\u0019wN\\2veJ,g\u000e\u001e\u0006\u0005\u0003K\t\t+\u0003\u0003\u0003x\n-(aD!u_6L7MU3gKJ,gnY3\u0011\u0011\tm8\u0011\u0001B\u0013\u0007\u0007i!A!@\u000b\t\t}\u00181Y\u0001\nS6lW\u000f^1cY\u0016LAAa\t\u0003~B)\u0001N!\u001d\u0004\u0006A\"1qAB\u0007!\u0019\tYj!\u0003\u0004\f%\u0019Q0!(\u0011\t\u0005\u00051Q\u0002\u0003\f\u0007\u001f9\u0014\u0011!A\u0001\u0006\u0003\t9AA\u0002`II\na\"\\1oS\u001a,7\u000f^\"bG\",\u0007%\u0001\rtKJL\u0017\r\\5{CRLwN\\%oM>\u0014X.\u0019;j_:,\"aa\u0006\u0011\u0007\t}\u0006\u0003K\u00029\u0003_,Ba!\b\u0004\"Q!1qDB\u0012!\u0011\t\ta!\t\u0005\u000f\tE\u0015H1\u0001\u0002\b!9!QS\u001dA\u0002\r\u0015\u0002#\u00025\u0003\u001a\u000e}\u0011!C:fe&\fG.\u001b>f)\u0011\u0019Yc!\u0010\u0011\r\u0005\u00052QFB\u0019\u0013\u0011\u0019y#a\t\u0003\u0007Q\u0013\u0018\u0010E\u0003i\u0007g\u00199$C\u0002\u00046%\u0014Q!\u0011:sCf\u00042\u0001[B\u001d\u0013\r\u0019Y$\u001b\u0002\u0005\u0005f$X\r\u0003\u0004\u0004@i\u0002\raZ\u0001\u0002_\u0006YA-Z:fe&\fG.\u001b>f+\u0011\u0019)ea\u0013\u0015\u0011\r\u001d3QJB)\u0007+\u0002b!!\t\u0004.\r%\u0003\u0003BA\u0001\u0007\u0017\"qA!%<\u0005\u0004\t9\u0001C\u0004\u0004Pm\u0002\ra!\r\u0002\u000b\tLH/Z:\t\u000f\rM3\b1\u0001\u0002.\u0006a1/\u001a:jC2L'0\u001a:JI\"91qK\u001eA\u0002\re\u0013!B2mCjT\b#\u00025\u0003r\rm\u0003\u0007BB/\u0007C\u0002B!\u001e?\u0004`A!\u0011\u0011AB1\t1\u0019\u0019g!\u001a\u0002\u0002\u0003\u0005)\u0011AB;\u0005\ryF\u0005\u000e\u0005\b\u0007/Z\u0004\u0019AB4!\u0015A'\u0011OB5a\u0011\u0019Yga\u001c\u0011\tUd8Q\u000e\t\u0005\u0003\u0003\u0019y\u0007\u0002\u0007\u0004d\r\u0015\u0014\u0011!A\u0001\u0006\u0003\u0019\t(\u0005\u0003\u0002\n\rM\u0004\u0003BA\u0001\u0007\u0017\nB!!\u0003\u0004J!Z1h!\u001f\u0004\u0000\r\u00055QQBD!\rA71P\u0005\u0004\u0007{J'A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017EABB\u0003q*6/\u001a\u0011eKN,'/[1mSj,\u0007\u0005\u001e5bi\u0002\n7mY3qiN\u0004C\u000f[3!A6\fg.\u001b4fgR\u0004\u0007%Y:!C\u0002\u001aG.Y:tA9\fW.\u001a\u0018\u0002\u000bMLgnY3\"\u0005\r%\u0015!\u0002\u001a/m9\u0002D\u0003CBG\u0007\u001f\u001b\tja%\u0011\u000b\u0005\u00052QF4\t\u000f\r=C\b1\u0001\u00042!911\u000b\u001fA\u0002\u00055\u0006bBBKy\u0001\u0007!QE\u0001\t[\u0006t\u0017NZ3ti\u0006!B-Z:fe&\fG.\u001b>f\u0005f$X-\u0011:sCf$raZBN\u0007;\u001b\t\u000bC\u0004\u0004Pu\u0002\ra!\r\t\u000f\r}U\b1\u0001\u0002\u0016\u0005Q1/\u001a:jC2L'0\u001a:\t\u000f\rUU\b1\u0001\u0003&\u0005)B-Z:fe&\fG.\u001b>f\u0005f$XMQ;gM\u0016\u0014HcB4\u0004(\u000e]6\u0011\u0018\u0005\b\u0007Ss\u0004\u0019ABV\u0003\r\u0011WO\u001a\t\u0005\u0007[\u001b\u0019,\u0004\u0002\u00040*!1\u0011WAQ\u0003\rq\u0017n\\\u0005\u0005\u0007k\u001byK\u0001\u0006CsR,')\u001e4gKJDqaa\u0015?\u0001\u0004\ti\u000bC\u0004\u0004\u0016z\u0002\rA!\n)\u000by\u001ail!3\u0011\u000b!\u001cyla1\n\u0007\r\u0005\u0017N\u0001\u0004uQJ|wo\u001d\t\u0005\u00053\u001a)-\u0003\u0003\u0004H\nm#\u0001\u0007(piN+'/[1mSj\f'\r\\3Fq\u000e,\u0007\u000f^5p]\u000e\u001211Y\u000b\u0005\u0007\u001b\u001c\u0019\u000e\u0006\u0004\u0004P\u000eU7q\u001b\t\u0007\u0003C\u0019ic!5\u0011\t\u0005\u000511\u001b\u0003\b\u0005#{$\u0019AA\u0004\u0011\u001d\u0019ye\u0010a\u0001\u0007cAqaa\u0016@\u0001\u0004\u0019I\u000e\u0005\u0003vy\u000eE\u0017!\u00054j]\u0012\u001cVM]5bY&TXM\u001d$peR!\u0011QCBp\u0011\u0019\u0019y\u0004\u0011a\u0001O\u0006i1/\u001a:jC2L'0\u001a:G_J$B!!\u0006\u0004f\"91qK!A\u0002\r\u001d\b\u0007BBu\u0007[\u0004B!\u001e?\u0004lB!\u0011\u0011ABw\t1\u0019yo!:\u0002\u0002\u0003\u0005)\u0011AA\u0004\u0005\ryF\u0005\u000f\u0015\u0006\u0003\u000eu6\u0011Z\u0001\rg\u0016\u0014\u0018.\u00197ju\u0016\u0014xJ\u001a\u000b\u0005\u0007o\u001cI\u0010\u0005\u0004\u0002\"\r5\u0012Q\u0003\u0005\b\u0007w\u0014\u0005\u0019\u0001B\u0013\u00035\u0019XM]5bY&TXM\u001d$R\u001dR11q_B\u0000\t\u0007Aq\u0001\"\u0001D\u0001\u0004\u0011)#A\u0006cS:$\u0017N\\4OC6,\u0007bBB~\u0007\u0002\u0007!QE\u0001\u0012g\u0016\u0014\u0018.\u00197ju\u0016\u0014H)\u001a;bS2\u001cXC\u0001C\u0005!\u0019\u0011Y\u0010b\u0003\u0005\u0010%!AQ\u0002B\u007f\u0005\r\u0019V-\u001d\t\u0004I\u0012E\u0011b\u0001C\n=\n\t2+\u001a:jC2L'0\u001a:EKR\f\u0017\u000e\\:\u0002%M,'/[1mSj,'\u000fR3uC&d7\u000fI\u0001\fg\u0016\u0014\u0018.\u00197ju\u0016\u00148/\u0006\u0002\u0005\u001cA9QO!\t\u0003&\u0005U\u0011\u0001D:fe&\fG.\u001b>feN\u0004\u0013\u0001\u00032j]\u0012LgnZ:\u0016\u0005\u0011\r\u0002C\u0002B~\t\u0017!)\u0003E\u0002\u0003@\u000e\t\u0011BY5oI&twm\u001d\u0011\u00029\u0015t7/\u001e:f\u001f:d\u00170\u00117m_^,GmU3sS\u0006d\u0017N_3sgR!AQ\u0006C\u001a!\rAGqF\u0005\u0004\tcI'\u0001B+oSRDq\u0001\"\u000eK\u0001\u0004!9$\u0001\u0003ji\u0016\u0014\bCBA\u001e\ts\t)\"\u0003\u0003\u0002J\u0006%\u0013aH<be:,f.\u001a=qK\u000e$X\r\u001a(p]\u0006[7.Y*fe&\fG.\u001b>feR1\u0011q\u001aC \t\u0017Bqaa\u0016L\u0001\u0004!\t\u0005\r\u0003\u0005D\u0011\u001d\u0003\u0003B;}\t\u000b\u0002B!!\u0001\u0005H\u0011aA\u0011\nC \u0003\u0003\u0005\tQ!\u0001\u0002\b\t!q\fJ\u00191\u0011\u001d!ie\u0013a\u0001\u0003+\t1a]3s\u0003M\u0019\u0007.Z2l\u000f>|w\r\\3Qe>$xNY;g)\u0011\ty\rb\u0015\t\u000f\u0011UC\n1\u0001\u0003&\u0005I1\r\\1tg:\u000bW.Z\u0001\u0012G\",7m[!lW\u0006\u0004&o\u001c;pEV4G\u0003BAh\t7Bq\u0001\"\u0016N\u0001\u0004\u0011)#\u0001\u0006dQ\u0016\u001c7n\u00117bgN$b!a4\u0005b\u0011\u0015\u0004b\u0002C2\u001d\u0002\u0007!QE\u0001\u0007aJ,g-\u001b=\t\u000f\u0011Uc\n1\u0001\u0003&\u0005!1o\u001c:u)\u0011!\u0019\u0003b\u001b\t\u000f\u00115t\n1\u0001\u0005p\u0005\u0011\u0011N\u001c\t\u0007\u0003w!\t\b\"\n\n\t\u0011M\u0014\u0011\n\u0002\t\u0013R,'/\u00192mK\u0006i1/\u001a:jC2L'0\u001a:NCB,\"\u0001\"\u001f\u0011\u0011\u0011mDQ\u0010CA\u0003+i!Aa<\n\t\u0011}$q\u001e\u0002\u0012\u0007>t7-\u001e:sK:$\b*Y:i\u001b\u0006\u0004\b\u0007\u0002CB\t\u000f\u0003B!\u001e?\u0005\u0006B!\u0011\u0011\u0001CD\t-!I)UA\u0001\u0002\u0003\u0015\t!a\u0002\u0003\t}#\u0013'M\u0001\u000fg\u0016\u0014\u0018.\u00197ju\u0016\u0014X*\u00199!\u0003Q\u0019XM]5bY&TXM\u001d\"z\u0013\u0012,g\u000e^5usV\u0011A\u0011\u0013\t\bk\n\u0005\u0012QVA\u000b\u0003U\u0019XM]5bY&TXM\u001d\"z\u0013\u0012,g\u000e^5us\u0002\n\u0011$];jG.\u001cVM]5bY&TXM\u001d\"z\u0013\u0012,g\u000e^5usV\u0011A\u0011\u0014\t\u0006Q\u000eM\u0012QC\u0001\u001bcVL7m[*fe&\fG.\u001b>fe\nK\u0018\nZ3oi&$\u0018\u0010I\u0001\u0012O\u0016$8+\u001a:jC2L'0\u001a:Cs&#G\u0003BA\u000b\tCCq\u0001b)W\u0001\u0004\ti+\u0001\u0002jI\u0006\t\u0013n\u001d&bm\u0006\u001cVM]5bY&T\u0018\r^5p]^\u000b'O\\5oO\u0016s\u0017M\u00197fI\u0006\u0011\u0013n\u001d&bm\u0006\u001cVM]5bY&T\u0018\r^5p]^\u000b'O\\5oO\u0016s\u0017M\u00197fI\u0002\n\u0001%[:XCJt\u0017N\\4P]:{g+\u001a:jM&\u001c\u0017\r^5p]\u0016s\u0017M\u00197fI\u0006\t\u0013n],be:LgnZ(o\u001d>4VM]5gS\u000e\fG/[8o\u000b:\f'\r\\3eA\u0005Q\u0012n\u001d#jg\u0006dGn\\<fI*\u000bg/Y*fe&\fG.\u001b>feR!\u0011q\u001aCY\u0011\u001d\u0019yj\u0017a\u0001\u0003+\tQd\u001d5pk2$w+\u0019:o\u0003\n|W\u000f\u001e&bm\u0006\u001cVM]5bY&TXM\u001d\u000b\u0007\u0003\u001f$9\f\"2\t\u000f\u0011eF\f1\u0001\u0005<\u0006y1/\u001a:jC2L'0\u001a3DY\u0006\u001c8\u000f\r\u0003\u0005>\u0012\u0005\u0007\u0003B;}\t\u007f\u0003B!!\u0001\u0005B\u0012aA1\u0019C\\\u0003\u0003\u0005\tQ!\u0001\u0002\b\t!q\fJ\u00194\u0011\u001d\u0019y\n\u0018a\u0001\u0003+A3\u0001XAx\u0001")
public class Serialization
implements Extension {
    private final ExtendedActorSystem system;
    private final Settings settings;
    private final boolean AllowJavaSerialization;
    private final MarkerLoggingAdapter _log;
    private final LoggingAdapter log;
    private final AtomicReference<Map<String, Option<Class<?>>>> manifestCache;
    private final Seq<SerializerDetails> serializerDetails;
    private final Map<String, Serializer> serializers;
    private final Seq<Tuple2<Class<?>, Serializer>> bindings;
    private final ConcurrentHashMap<Class<?>, Serializer> serializerMap;
    private final Map<Object, Serializer> serializerByIdentity;
    private final Serializer[] quickSerializerByIdentity;
    private final boolean isJavaSerializationWarningEnabled;
    private final boolean isWarningOnNoVerificationEnabled;

    public static Information getCurrentTransportInformation() {
        return Serialization$.MODULE$.getCurrentTransportInformation();
    }

    public static String serializedActorPath(ActorRef actorRef2) {
        return Serialization$.MODULE$.serializedActorPath(actorRef2);
    }

    public ExtendedActorSystem system() {
        return this.system;
    }

    public Settings settings() {
        return this.settings;
    }

    public boolean AllowJavaSerialization() {
        return this.AllowJavaSerialization;
    }

    public LoggingAdapter log() {
        return this.log;
    }

    private AtomicReference<Map<String, Option<Class<?>>>> manifestCache() {
        return this.manifestCache;
    }

    @InternalApi
    public Information serializationInformation() {
        return this.system().provider().serializationInformation();
    }

    private <T> T withTransportInformation(Function0<T> f) {
        T t2;
        Information oldInfo = Serialization$.MODULE$.currentTransportInformation().value();
        try {
            if (oldInfo == null) {
                Serialization$.MODULE$.currentTransportInformation().value_$eq(this.serializationInformation());
            }
            t2 = f.apply();
        }
        finally {
            Serialization$.MODULE$.currentTransportInformation().value_$eq(oldInfo);
        }
        return t2;
    }

    public Try<byte[]> serialize(Object o) {
        return (Try)this.withTransportInformation((Function0<Try> & Serializable)() -> Try$.MODULE$.apply((Function0<byte[]> & Serializable)() -> this.findSerializerFor(o).toBinary(o)));
    }

    public <T> Try<T> deserialize(byte[] bytes, int serializerId, Option<Class<? extends T>> clazz) {
        return Try$.MODULE$.apply((Function0<Object> & Serializable)() -> {
            Serializer serializer;
            try {
                serializer = this.getSerializerById(serializerId);
            }
            catch (NoSuchElementException noSuchElementException) {
                throw new NotSerializableException(new StringBuilder(154).append("Cannot find serializer with id [").append(serializerId).append("]").append(clazz.map((Function1<Class, String> & Serializable)c -> new StringBuilder(11).append(" (class [").append(c.getName()).append("])").toString()).getOrElse((Function0<String> & Serializable)() -> "")).append(". ").append("The most probable reason is that the configuration entry ").append("akka.actor.serializers is not in sync between the two systems.").toString());
            }
            Serializer serializer2 = serializer;
            return this.withTransportInformation((Function0<Object> & Serializable)() -> serializer2.fromBinary(bytes, clazz));
        });
    }

    public Try<Object> deserialize(byte[] bytes, int serializerId, String manifest) {
        return Try$.MODULE$.apply((Function0<Object> & Serializable)() -> {
            Serializer serializer;
            try {
                serializer = this.getSerializerById(serializerId);
            }
            catch (NoSuchElementException noSuchElementException) {
                throw new NotSerializableException(new StringBuilder(168).append("Cannot find serializer with id [").append(serializerId).append("] (manifest [").append(manifest).append("]). The most probable reason is that the configuration entry ").append("akka.actor.serializers is not in sync between the two systems.").toString());
            }
            Serializer serializer2 = serializer;
            return this.deserializeByteArray(bytes, serializer2, manifest);
        });
    }

    private Object deserializeByteArray(byte[] bytes, Serializer serializer, String manifest) {
        return this.withTransportInformation((Function0<Object> & Serializable)() -> {
            Serializer serializer = serializer;
            if (serializer instanceof SerializerWithStringManifest) {
                SerializerWithStringManifest serializerWithStringManifest = (SerializerWithStringManifest)serializer;
                return serializerWithStringManifest.fromBinary(bytes, manifest);
            }
            String string = manifest;
            String string2 = "";
            if (!(string != null ? !string.equals(string2) : string2 != null)) {
                return serializer.fromBinary(bytes, None$.MODULE$);
            }
            Map<String, Option<Class<?>>> cache = this.manifestCache().get();
            Option option = cache.get(manifest);
            if (option instanceof Some) {
                Some some = (Some)option;
                Option cachedClassManifest = (Option)some.value();
                return serializer.fromBinary(bytes, cachedClassManifest);
            }
            if (None$.MODULE$.equals(option)) {
                Try<Class<Object>> try_ = this.system().dynamicAccess().getClassFor(manifest, ClassTag$.MODULE$.AnyRef());
                if (try_ instanceof Success) {
                    Success success = (Success)try_;
                    Class classManifest = (Class)success.value();
                    Some classManifestOption = new Some(classManifest);
                    this.updateCache$1(cache, manifest, classManifestOption);
                    return serializer.fromBinary(bytes, classManifestOption);
                }
                if (try_ instanceof Failure) {
                    throw new NotSerializableException(new StringBuilder(56).append("Cannot find manifest class [").append(manifest).append("] for serializer with id [").append(serializer.identifier()).append("].").toString());
                }
                throw new MatchError(try_);
            }
            throw new MatchError(option);
        });
    }

    public Object deserializeByteBuffer(ByteBuffer buf, int serializerId, String manifest) throws NotSerializableException {
        Object object;
        Serializer serializer;
        try {
            serializer = this.getSerializerById(serializerId);
        }
        catch (NoSuchElementException noSuchElementException) {
            throw new NotSerializableException(new StringBuilder(169).append("Cannot find serializer with id [").append(serializerId).append("] (manifest [").append(manifest).append("]). The most probable reason is that the configuration entry ").append("akka.actor.serializers is not in synch between the two systems.").toString());
        }
        Serializer serializer2 = serializer;
        Information oldInfo = Serialization$.MODULE$.currentTransportInformation().value();
        try {
            Serializer serializer3;
            if (oldInfo == null) {
                Serialization$.MODULE$.currentTransportInformation().value_$eq(this.serializationInformation());
            }
            if ((serializer3 = serializer2) instanceof ByteBufferSerializer) {
                Serializer serializer4 = serializer3;
                object = ((ByteBufferSerializer)((Object)serializer4)).fromBinary(buf, manifest);
            } else {
                byte[] bytes = new byte[buf.remaining()];
                buf.get(bytes);
                object = this.deserializeByteArray(bytes, serializer2, manifest);
            }
        }
        finally {
            Serialization$.MODULE$.currentTransportInformation().value_$eq(oldInfo);
        }
        return object;
    }

    public <T> Try<T> deserialize(byte[] bytes, Class<T> clazz) {
        return (Try)this.withTransportInformation((Function0<Try> & Serializable)() -> Try$.MODULE$.apply((Function0<Object> & Serializable)() -> this.serializerFor(clazz).fromBinary(bytes, new Some(clazz))));
    }

    public Serializer findSerializerFor(Object o) {
        if (o == null) {
            return NullSerializer$.MODULE$;
        }
        return this.serializerFor(o.getClass());
    }

    public Serializer serializerFor(Class<?> clazz) throws NotSerializableException {
        Serializer serializer = this.serializerMap().get(clazz);
        if (serializer == null) {
            Serializer serializer2;
            SeqOps seqOps;
            Seq seq = (Seq)this.bindings().filter((Function1<Tuple2, Object> & Serializable)x0$1 -> BoxesRunTime.boxToBoolean(Serialization.$anonfun$serializerFor$3(clazz, x0$1)));
            if (seq != null && !SeqFactory$UnapplySeqWrapper$.MODULE$.isEmpty$extension(seqOps = Seq$.MODULE$.unapplySeq(seq)) && new SeqFactory.UnapplySeqWrapper(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps)) != null && SeqFactory$UnapplySeqWrapper$.MODULE$.lengthCompare$extension(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps), 0) == 0) {
                throw new NotSerializableException(new StringBuilder(49).append("No configured serialization-bindings for class [").append(clazz.getName()).append("]").toString());
            }
            if (Serialization.unique$1(seq)) {
                serializer2 = (Serializer)((Tuple2)seq.head())._2();
            } else {
                Seq possibilitiesWithoutJavaSerializer = (Seq)seq.filter((Function1<Tuple2, Object> & Serializable)x0$2 -> BoxesRunTime.boxToBoolean(Serialization.$anonfun$serializerFor$4(x0$2)));
                if (possibilitiesWithoutJavaSerializer.isEmpty()) {
                    throw new NotSerializableException(new StringBuilder(52).append("More than one JavaSerializer configured for class [").append(clazz.getName()).append("]").toString());
                }
                if (!Serialization.unique$1(possibilitiesWithoutJavaSerializer)) {
                    this._log.warning(LogMarker$.MODULE$.Security(), "Multiple serializers found for [{}], choosing first of: [{}]", (Object)clazz.getName(), (Object)((IterableOnceOps)possibilitiesWithoutJavaSerializer.map((Function1<Tuple2, String> & Serializable)x0$3 -> {
                        Tuple2 tuple2 = x0$3;
                        if (tuple2 != null) {
                            Serializer s2 = (Serializer)tuple2._2();
                            return s2.getClass().getName();
                        }
                        throw new MatchError(tuple2);
                    })).mkString(", "));
                }
                serializer2 = (Serializer)((Tuple2)possibilitiesWithoutJavaSerializer.head())._2();
            }
            Serializer ser = serializer2;
            Serializer serializer3 = this.serializerMap().putIfAbsent(clazz, ser);
            if (serializer3 == null) {
                if (this.shouldWarnAboutJavaSerializer(clazz, ser)) {
                    this._log.warning(LogMarker$.MODULE$.Security(), "Using the Java serializer for class [{}] which is not recommended because of performance implications. Use another serializer or disable this warning using the setting 'akka.actor.warn-about-java-serializer-usage'", (Object)clazz.getName());
                }
                if (!this.warnUnexpectedNonAkkaSerializer(clazz, ser)) {
                    this.log().debug("Using serializer [{}] for message [{}]", ser.getClass().getName(), clazz.getName());
                }
                return ser;
            }
            return serializer3;
        }
        return serializer;
    }

    public Try<Serializer> serializerOf(String serializerFQN) {
        return this.serializerOf("", serializerFQN);
    }

    /*
     * Unable to fully structure code
     */
    private Try<Serializer> serializerOf(String bindingName, String serializerFQN) {
        if (this.system().settings().AllowJavaSerialization()) ** GOTO lbl-1000
        v0 = serializerFQN;
        var4_3 = JavaSerializer.class.getName();
        if (!(v0 != null ? v0.equals(var4_3) == false : var4_3 != null)) {
            this.log().debug("Replacing JavaSerializer with DisabledJavaSerializer, due to `akka.actor.allow-java-serialization = off`.");
            v1 = DisabledJavaSerializer.class.getName();
        } else lbl-1000:
        // 2 sources

        {
            v1 = serializerFQN;
        }
        fqn = v1;
        return this.system().dynamicAccess().createInstanceFor(fqn, new $colon$colon<Nothing$>((Nothing$)Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(ExtendedActorSystem.class), this.system()), Nil$.MODULE$), ClassTag$.MODULE$.apply(Serializer.class)).recoverWith(new Serializable(this, fqn, bindingName){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ Serialization $outer;
            public final String fqn$1;
            public final String bindingName$1;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof NoSuchMethodException) {
                    return (B1)this.$outer.system().dynamicAccess().createInstanceFor(this.fqn$1, Nil$.MODULE$, ClassTag$.MODULE$.apply(Serializer.class)).recoverWith(new Serializable(this){
                        private static final long serialVersionUID = 0L;
                        private final /* synthetic */ $anonfun$serializerOf$1 $outer;

                        public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                            A1 A1 = x1;
                            if (A1 instanceof NoSuchMethodException) {
                                NoSuchMethodException noSuchMethodException = (NoSuchMethodException)A1;
                                String string = this.$outer.bindingName$1;
                                String string2 = "";
                                if (!(string != null ? !string.equals(string2) : string2 != null)) {
                                    throw noSuchMethodException;
                                }
                                return (B1)this.$outer.akka$serialization$Serialization$$anonfun$$$outer().system().dynamicAccess().createInstanceFor(this.$outer.fqn$1, new $colon$colon<Nothing$>((Nothing$)((Object)Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(ExtendedActorSystem.class), this.$outer.akka$serialization$Serialization$$anonfun$$$outer().system())), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(String.class), this.$outer.bindingName$1)), Nil$.MODULE$)), ClassTag$.MODULE$.apply(Serializer.class));
                            }
                            return function1.apply(x1);
                        }

                        public final boolean isDefinedAt(Throwable x1) {
                            Throwable throwable = x1;
                            return throwable instanceof NoSuchMethodException;
                        }
                        {
                            if ($outer == null) {
                                throw null;
                            }
                            this.$outer = $outer;
                        }
                    });
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Throwable x1) {
                Throwable throwable = x1;
                return throwable instanceof NoSuchMethodException;
            }

            public /* synthetic */ Serialization akka$serialization$Serialization$$anonfun$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.fqn$1 = fqn$1;
                this.bindingName$1 = bindingName$1;
            }
        });
    }

    private Seq<SerializerDetails> serializerDetails() {
        return this.serializerDetails;
    }

    private Map<String, Serializer> serializers() {
        return this.serializers;
    }

    public Seq<Tuple2<Class<?>, Serializer>> bindings() {
        return this.bindings;
    }

    private void ensureOnlyAllowedSerializers(Iterator<Serializer> iter) {
        if (!this.system().settings().AllowJavaSerialization()) {
            Predef$.MODULE$.require(iter.forall((Function1<Serializer, Object> & Serializable)x$6 -> BoxesRunTime.boxToBoolean(Serialization.$anonfun$ensureOnlyAllowedSerializers$1(this, x$6))), (Function0<Object>)(Function0<String> & Serializable)() -> "Disallowed JavaSerializer binding.");
            return;
        }
    }

    private boolean warnUnexpectedNonAkkaSerializer(Class<?> clazz, Serializer ser) {
        String className = clazz.getName();
        if (className.startsWith("akka.") && !ser.getClass().getName().startsWith("akka.") && !className.startsWith("akka.grpc") && !className.startsWith("akka.http")) {
            this.log().warning("Using serializer [{}] for message [{}]. Note that this serializer is not implemented by Akka. It's not recommended to replace serializers for messages provided by Akka.", (Object)ser.getClass().getName(), (Object)clazz.getName());
            return true;
        }
        return false;
    }

    private boolean checkGoogleProtobuf(String className) {
        return this.checkClass("com.google.protobuf", className);
    }

    private boolean checkAkkaProtobuf(String className) {
        return this.checkClass("akka.protobuf", className);
    }

    private boolean checkClass(String prefix, String className) {
        return !className.startsWith(prefix) || this.system().dynamicAccess().getClassFor(className, ClassTag$.MODULE$.Any()).isSuccess();
    }

    private Seq<Tuple2<Class<?>, Serializer>> sort(Iterable<Tuple2<Class<?>, Serializer>> in) {
        return ((IterableOnceOps)in.foldLeft(new ArrayBuffer(in.size()), (Function2<ArrayBuffer, Tuple2, ArrayBuffer> & Serializable)(buf, ca) -> {
            int n = buf.indexWhere((Function1<Tuple2, Object> & Serializable)x$7 -> BoxesRunTime.boxToBoolean(Serialization.$anonfun$sort$2(ca, x$7)));
            switch (n) {
                case -1: {
                    Object object = buf.append(ca);
                    break;
                }
                default: {
                    buf.insert(n, ca);
                    Object object = BoxedUnit.UNIT;
                    break;
                }
            }
            return buf;
        })).to(IterableFactory$.MODULE$.toFactory(Seq$.MODULE$));
    }

    private ConcurrentHashMap<Class<?>, Serializer> serializerMap() {
        return this.serializerMap;
    }

    public Map<Object, Serializer> serializerByIdentity() {
        return this.serializerByIdentity;
    }

    private Serializer[] quickSerializerByIdentity() {
        return this.quickSerializerByIdentity;
    }

    private Serializer getSerializerById(int id) {
        if (0 <= id && id < this.quickSerializerByIdentity().length) {
            Serializer serializer = this.quickSerializerByIdentity()[id];
            if (serializer == null) {
                throw new NoSuchElementException(new StringBuilder(15).append("key not found: ").append(id).toString());
            }
            return serializer;
        }
        return (Serializer)this.serializerByIdentity().apply(BoxesRunTime.boxToInteger(id));
    }

    private boolean isJavaSerializationWarningEnabled() {
        return this.isJavaSerializationWarningEnabled;
    }

    private boolean isWarningOnNoVerificationEnabled() {
        return this.isWarningOnNoVerificationEnabled;
    }

    public boolean akka$serialization$Serialization$$isDisallowedJavaSerializer(Serializer serializer) {
        return serializer instanceof JavaSerializer && !this.system().settings().AllowJavaSerialization();
    }

    @InternalApi
    public boolean shouldWarnAboutJavaSerializer(Class<?> serializedClass, Serializer serializer) {
        return this.isJavaSerializationWarningEnabled() && (serializer instanceof JavaSerializer || serializer instanceof DisabledJavaSerializer) && !serializedClass.getName().startsWith("akka.") && !serializedClass.getName().startsWith("java.lang.") && !this.suppressWarningOnNonSerializationVerification$1(serializedClass);
    }

    private final boolean updateCache$1(Map cache, String key, Option value) {
        while (!this.manifestCache().compareAndSet(cache, (Map<String, Option<Class<?>>>)cache.updated(key, value))) {
            cache = this.manifestCache().get();
        }
        return true;
    }

    public static final /* synthetic */ boolean $anonfun$serializerFor$1(Seq possibilities$1, Tuple2 x$1) {
        return ((Class)x$1._1()).isAssignableFrom((Class)((Tuple2)possibilities$1.apply(false))._1());
    }

    public static final /* synthetic */ boolean $anonfun$serializerFor$2(Seq possibilities$1, Tuple2 x$2) {
        return BoxesRunTime.equals(x$2._2(), ((Tuple2)possibilities$1.apply(false))._2());
    }

    private static final boolean unique$1(Seq possibilities) {
        return possibilities.size() == 1 || possibilities.forall((Function1<Tuple2, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(Serialization.$anonfun$serializerFor$1(possibilities, x$1))) || possibilities.forall((Function1<Tuple2, Object> & Serializable)x$2 -> BoxesRunTime.boxToBoolean(Serialization.$anonfun$serializerFor$2(possibilities, x$2)));
    }

    public static final /* synthetic */ boolean $anonfun$serializerFor$3(Class clazz$3, Tuple2 x0$1) {
        Tuple2 tuple2 = x0$1;
        if (tuple2 != null) {
            Class c = (Class)tuple2._1();
            return c.isAssignableFrom(clazz$3);
        }
        throw new MatchError(tuple2);
    }

    public static final /* synthetic */ boolean $anonfun$serializerFor$4(Tuple2 x0$2) {
        Tuple2 tuple2 = x0$2;
        if (tuple2 != null && tuple2._2() instanceof JavaSerializer) {
            return false;
        }
        return tuple2 == null || !(tuple2._2() instanceof DisabledJavaSerializer);
    }

    public static final /* synthetic */ boolean $anonfun$serializers$1(Tuple2 check$ifrefutable$1) {
        Tuple2 tuple2 = check$ifrefutable$1;
        if (tuple2 != null) {
            String k = (String)tuple2._1();
            String v = (String)tuple2._2();
            if (k != null && v != null) {
                return true;
            }
        }
        return false;
    }

    public static final /* synthetic */ boolean $anonfun$bindings$1(Tuple2 check$ifrefutable$2) {
        Tuple2 tuple2 = check$ifrefutable$2;
        if (tuple2 != null) {
            String className = (String)tuple2._1();
            String alias = (String)tuple2._2();
            if (className != null && alias != null) {
                return true;
            }
        }
        return false;
    }

    public static final /* synthetic */ boolean $anonfun$bindings$2(Serialization $this, Tuple2 x$4) {
        Tuple2 tuple2 = x$4;
        if (tuple2 != null) {
            String className = (String)tuple2._1();
            String alias = (String)tuple2._2();
            if (className != null) {
                String string = className;
                if (alias != null) {
                    String string2;
                    String string3 = string2 = alias;
                    String string4 = "none";
                    return (string3 == null ? string4 != null : !string3.equals(string4)) && $this.checkGoogleProtobuf(string) && $this.checkAkkaProtobuf(string);
                }
            }
        }
        throw new MatchError(tuple2);
    }

    public static final /* synthetic */ boolean $anonfun$bindings$7(Serialization $this, Tuple2 x0$3) {
        Tuple2 tuple2 = x0$3;
        if (tuple2 != null) {
            Class clazz = (Class)tuple2._1();
            Serializer ser = (Serializer)tuple2._2();
            return $this.warnUnexpectedNonAkkaSerializer(clazz, ser);
        }
        throw new MatchError(tuple2);
    }

    public static final /* synthetic */ boolean $anonfun$ensureOnlyAllowedSerializers$1(Serialization $this, Serializer x$6) {
        return !$this.akka$serialization$Serialization$$isDisallowedJavaSerializer(x$6);
    }

    public static final /* synthetic */ boolean $anonfun$sort$2(Tuple2 ca$1, Tuple2 x$7) {
        return ((Class)x$7._1()).isAssignableFrom((Class)ca$1._1());
    }

    public static final /* synthetic */ void $anonfun$quickSerializerByIdentity$1(int size$1, Serializer[] table$1, Tuple2 x0$6) {
        Tuple2 tuple2 = x0$6;
        if (tuple2 != null) {
            int id = tuple2._1$mcI$sp();
            Serializer ser = (Serializer)tuple2._2();
            if (0 <= id && id < size$1) {
                table$1[id] = ser;
                return;
            }
            return;
        }
        throw new MatchError(tuple2);
    }

    private final boolean suppressWarningOnNonSerializationVerification$1(Class serializedClass) {
        return !this.isWarningOnNoVerificationEnabled() && NoSerializationVerificationNeeded.class.isAssignableFrom(serializedClass);
    }

    public Serialization(ExtendedActorSystem system) {
        Seq<SerializerDetails> seq;
        this.system = system;
        this.settings = new Settings(system.settings().config());
        this.AllowJavaSerialization = system.settings().AllowJavaSerialization();
        this._log = Logging$.MODULE$.withMarker(system, this.getClass().getName(), LogSource$.MODULE$.fromString());
        this.log = this._log;
        this.manifestCache = new AtomicReference<Object>(Predef$.MODULE$.Map().empty());
        Option option = system.settings().setup().get(ClassTag$.MODULE$.apply(SerializationSetup.class));
        if (None$.MODULE$.equals(option)) {
            seq = package$.MODULE$.Vector().empty();
        } else if (option instanceof Some) {
            Some some = (Some)option;
            SerializationSetup setting = (SerializationSetup)some.value();
            seq = setting.createSerializers().apply(system);
        } else {
            throw new MatchError(option);
        }
        this.serializerDetails = (Seq)seq.collect(new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ Serialization $outer;

            public final <A1 extends SerializerDetails, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1;
                A1 A12 = x1;
                if (A12 != null && this.$outer.akka$serialization$Serialization$$isDisallowedJavaSerializer((A1 = A12).serializer())) {
                    this.$outer.log().debug("Replacing JavaSerializer with DisabledJavaSerializer, due to `akka.actor.allow-java-serialization = off`.");
                    return (B1)SerializerDetails$.MODULE$.apply(A1.alias(), new DisabledJavaSerializer(this.$outer.system()), A1.useFor());
                }
                return (B1)A12;
            }

            public final boolean isDefinedAt(SerializerDetails x1) {
                SerializerDetails serializerDetails;
                SerializerDetails serializerDetails2 = x1;
                if (serializerDetails2 != null && this.$outer.akka$serialization$Serialization$$isDisallowedJavaSerializer((serializerDetails = serializerDetails2).serializer())) {
                    return true;
                }
                return true;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        });
        Map fromConfig = (Map)this.settings().Serializers().withFilter((Function1<Tuple2<String, String>, Object>)(Function1<Tuple2, Object> & Serializable)check$ifrefutable$1 -> BoxesRunTime.boxToBoolean(Serialization.$anonfun$serializers$1(check$ifrefutable$1))).map((Function1<Tuple2, Tuple2> & Serializable)x$3 -> {
            Tuple2 tuple2 = x$3;
            if (tuple2 != null) {
                String k = (String)tuple2._1();
                String v = (String)tuple2._2();
                if (k != null) {
                    String string = k;
                    if (v != null) {
                        String string2 = v;
                        return Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(string), this.serializerOf(string, string2).get());
                    }
                }
            }
            throw new MatchError(tuple2);
        });
        Map result = (Map)fromConfig.$plus$plus((IterableOnce)this.serializerDetails().map((Function1<SerializerDetails, Tuple2> & Serializable)d -> Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(d.alias()), d.serializer())));
        this.ensureOnlyAllowedSerializers(result.iterator().map((Function1<Tuple2, Serializer> & Serializable)x0$1 -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null) {
                Serializer ser = (Serializer)tuple2._2();
                return ser;
            }
            throw new MatchError(tuple2);
        }));
        this.serializers = result;
        Map fromConfig2 = (Map)((MapOps.WithFilter)this.settings().SerializationBindings().withFilter((Function1<Tuple2<String, String>, Object>)(Function1<Tuple2, Object> & Serializable)check$ifrefutable$2 -> BoxesRunTime.boxToBoolean(Serialization.$anonfun$bindings$1(check$ifrefutable$2))).withFilter((Function1<Tuple2, Object> & Serializable)x$4 -> BoxesRunTime.boxToBoolean(Serialization.$anonfun$bindings$2(this, x$4)))).map((Function1<Tuple2, Tuple2> & Serializable)x$5 -> {
            Tuple2 tuple2 = x$5;
            if (tuple2 != null) {
                String className = (String)tuple2._1();
                String alias = (String)tuple2._2();
                if (className != null) {
                    String string = className;
                    if (alias != null) {
                        String string2 = alias;
                        return new Tuple2(this.system().dynamicAccess().getClassFor(string, ClassTag$.MODULE$.Any()).get(), this.serializers().apply(string2));
                    }
                }
            }
            throw new MatchError(tuple2);
        });
        Seq fromSettings = (Seq)this.serializerDetails().flatMap((Function1<SerializerDetails, Seq> & Serializable)detail -> (Seq)detail.useFor().map((Function1<Class, Tuple2> & Serializable)clazz -> Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(clazz), detail.serializer())));
        Seq<Tuple2<Class<?>, Serializer>> result2 = this.sort((Iterable)fromConfig2.$plus$plus(fromSettings));
        this.ensureOnlyAllowedSerializers(result2.iterator().map((Function1<Tuple2, Serializer> & Serializable)x0$2 -> {
            Tuple2 tuple2 = x0$2;
            if (tuple2 != null) {
                Serializer ser = (Serializer)tuple2._2();
                return ser;
            }
            throw new MatchError(tuple2);
        }));
        result2.foreach((Function1<Tuple2, Object> & Serializable)x0$3 -> BoxesRunTime.boxToBoolean(Serialization.$anonfun$bindings$7(this, x0$3)));
        this.bindings = result2;
        this.serializerMap = this.bindings().foldLeft(new ConcurrentHashMap(), (Function2<ConcurrentHashMap, Tuple2, ConcurrentHashMap> & Serializable)(x0$4, x1$1) -> {
            Tuple2<ConcurrentHashMap, Tuple2> tuple2 = new Tuple2<ConcurrentHashMap, Tuple2>((ConcurrentHashMap)x0$4, (Tuple2)x1$1);
            if (tuple2 != null) {
                ConcurrentHashMap map = tuple2._1();
                Tuple2 tuple22 = tuple2._2();
                if (tuple22 != null) {
                    Class c = (Class)tuple22._1();
                    Serializer s2 = (Serializer)tuple22._2();
                    map.put(c, s2);
                    return map;
                }
            }
            throw new MatchError(tuple2);
        });
        Map zero = (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(BoxesRunTime.boxToInteger(NullSerializer$.MODULE$.identifier())), NullSerializer$.MODULE$)}));
        this.serializerByIdentity = this.serializers().foldLeft(zero, (Function2<Map, Tuple2, Map> & Serializable)(x0$5, x1$2) -> {
            Tuple2<Map, Tuple2> tuple2 = new Tuple2<Map, Tuple2>((Map)x0$5, (Tuple2)x1$2);
            if (tuple2 != null) {
                Map acc = tuple2._1();
                Tuple2 tuple22 = tuple2._2();
                if (tuple22 != null) {
                    Serializer ser = (Serializer)tuple22._2();
                    int id = ser.identifier();
                    Option option = acc.get(BoxesRunTime.boxToInteger(id));
                    if (option instanceof Some) {
                        Serializer existing;
                        Some some = (Some)option;
                        Serializer serializer = existing = (Serializer)some.value();
                        Serializer serializer2 = ser;
                        if (serializer == null ? serializer2 != null : !serializer.equals(serializer2)) {
                            throw new IllegalArgumentException(new StringBuilder(68).append("Serializer identifier [").append(id).append("] of [").append(ser.getClass().getName()).append("] ").append("is not unique. It is also used by [").append(acc.apply(BoxesRunTime.boxToInteger(id)).getClass().getName()).append("].").toString());
                        }
                    }
                    return (Map)acc.updated(BoxesRunTime.boxToInteger(id), ser);
                }
            }
            throw new MatchError(tuple2);
        });
        int size = 1024;
        Serializer[] table = new Serializer[size];
        this.serializerByIdentity().foreach((Function1<Tuple2, Object> & Serializable)x0$6 -> {
            Serialization.$anonfun$quickSerializerByIdentity$1(size, table, x0$6);
            return BoxedUnit.UNIT;
        });
        this.quickSerializerByIdentity = table;
        this.isJavaSerializationWarningEnabled = this.settings().config().getBoolean("akka.actor.warn-about-java-serializer-usage");
        this.isWarningOnNoVerificationEnabled = this.settings().config().getBoolean("akka.actor.warn-on-no-serialization-verification");
    }

    public static final class Information
    implements Product,
    Serializable {
        private final Address address;
        private final ActorSystem system;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Address address() {
            return this.address;
        }

        public ActorSystem system() {
            return this.system;
        }

        public Information copy(Address address, ActorSystem system) {
            return new Information(address, system);
        }

        public Address copy$default$1() {
            return this.address();
        }

        public ActorSystem copy$default$2() {
            return this.system();
        }

        @Override
        public String productPrefix() {
            return "Information";
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
                    return this.address();
                }
                case 1: {
                    return this.system();
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
            return x$1 instanceof Information;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "address";
                }
                case 1: {
                    return "system";
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
            if (!(object instanceof Information)) return false;
            boolean bl = true;
            if (!bl) return false;
            Information information = (Information)x$1;
            Address address = this.address();
            Address address2 = information.address();
            if (address == null) {
                if (address2 != null) {
                    return false;
                }
            } else if (!((Object)address).equals(address2)) return false;
            ActorSystem actorSystem = this.system();
            ActorSystem actorSystem2 = information.system();
            if (actorSystem == null) {
                if (actorSystem2 == null) return true;
                return false;
            } else {
                if (!actorSystem.equals(actorSystem2)) return false;
                return true;
            }
        }

        public Information(Address address, ActorSystem system) {
            this.address = address;
            this.system = system;
            Product.$init$(this);
        }
    }

    public static class Settings {
        private final Config config;
        private final Map<String, String> Serializers;
        private final Map<String, String> SerializationBindings;

        public Config config() {
            return this.config;
        }

        public Map<String, String> Serializers() {
            return this.Serializers;
        }

        public Map<String, String> SerializationBindings() {
            return this.SerializationBindings;
        }

        private final Map<String, String> configToMap(Config cfg) {
            return (Map)package$JavaConverters$.MODULE$.MapHasAsScala(cfg.root().unwrapped()).asScala().toMap($less$colon$less$.MODULE$.refl()).map((Function1<Tuple2, Tuple2> & Serializable)x0$1 -> {
                Tuple2 tuple2 = x0$1;
                if (tuple2 != null) {
                    String k = (String)tuple2._1();
                    Object v = tuple2._2();
                    return Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(k), v.toString());
                }
                throw new MatchError(tuple2);
            });
        }

        public Settings(Config config) {
            this.config = config;
            this.Serializers = this.configToMap(config.getConfig("akka.actor.serializers"));
            Config bindings = config.getConfig("akka.actor.serialization-bindings");
            this.SerializationBindings = this.configToMap(bindings);
        }
    }
}

