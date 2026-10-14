/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import java.util.NoSuchElementException;
import scala.$less$colon$less;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterator;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.LazyZip2;
import scala.collection.Map;
import scala.collection.MapFactory;
import scala.collection.MapFactoryDefaults;
import scala.collection.MapOps;
import scala.collection.MapView;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.View;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.ArraySeq;
import scala.collection.immutable.HashMap$;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.SeqMap$;
import scala.collection.immutable.SeqMap$EmptySeqMap$;
import scala.collection.immutable.Set;
import scala.collection.immutable.Stream;
import scala.collection.immutable.Vector;
import scala.collection.immutable.Vector$;
import scala.collection.immutable.VectorMap;
import scala.collection.immutable.VectorMapBuilder;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.ReusableBuilder;
import scala.collection.mutable.StringBuilder;
import scala.math.Numeric;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005\u0011=daB5k!\u0003\r\t!\u001d\u0005\b\u0003_\u0001A\u0011AA\u0019\u0011\u001d\tI\u0004\u0001C!\u0003w9q!a\u0011k\u0011\u0003\t)E\u0002\u0004jU\"\u0005\u0011q\t\u0005\b\u0003\u0013\"A\u0011AA&\u0011\u001d\ti\u0005\u0002C\u0001\u0003\u001fBq!!\u0018\u0005\t\u0003\ty\u0006C\u0004\u0002~\u0011!\t!a \b\u000f\u0005mE\u0001#\u0003\u0002\u001e\u001a9\u0011\u0011\u0015\u0003\t\n\u0005\r\u0006bBA%\u0015\u0011\u0005\u0011Q\u0017\u0005\b\u0003oSA\u0011IA]\u0011\u001d\t\tM\u0003C!\u0003sCq!a1\u000b\t\u0003\n)\rC\u0004\u0002L*!\t%!4\t\u000f\u0005]'\u0002\"\u0001\u0002Z\"9\u00111\u001d\u0006\u0005B\u0005\u0015\bbBA~\u0015\u0011\u0005\u0011Q \u0005\b\u0005\u000fQA\u0011\u0001B\u0005\u0011\u001d\u0011IB\u0003C\u0001\u00057A\u0011Ba\b\u000b\u0003\u0003%IA!\t\u0007\r\t}BA\u0002B!\u0011)\u0011yE\u0006B\u0001B\u0003%!q\t\u0005\u000b\u0005#2\"\u0011!Q\u0001\n\t-\u0003bBA%-\u0011\u0005!1\u000b\u0005\b\u0003o3B\u0011IA]\u0011\u001d\t\tM\u0006C!\u0003sCq!a1\u0017\t\u0003\u0012Y\u0006C\u0004\u0002LZ!\tEa\u0018\t\u000f\u0005]g\u0003\"\u0001\u0003d!9\u00111\u001d\f\u0005B\t%\u0004bBA~-\u0011\u0005!\u0011\u0010\u0005\b\u0005\u000f1B\u0011\u0001B@\u0011\u001d\u0011IB\u0006C\u0001\u0005\u001bCqA!%\u0017\t\u0003\u0012\u0019\nC\u0004\u0003(Z!\tE!+\u0007\r\tmFA\u0002B_\u0011)\u0011y%\nB\u0001B\u0003%!1\u0019\u0005\u000b\u0005#*#\u0011!Q\u0001\n\t\u001d\u0007B\u0003BfK\t\u0005\t\u0015!\u0003\u0003D\"Q!QZ\u0013\u0003\u0002\u0003\u0006IAa2\t\u000f\u0005%S\u0005\"\u0001\u0003P\"9\u0011qW\u0013\u0005B\u0005e\u0006bBAaK\u0011\u0005\u0013\u0011\u0018\u0005\b\u0003\u0007,C\u0011\tBn\u0011\u001d\tY-\nC!\u0005?Dq!a6&\t\u0003\u0011\u0019\u000fC\u0004\u0002d\u0016\"\tE!;\t\u000f\u0005mX\u0005\"\u0001\u0003z\"9!qA\u0013\u0005\u0002\t}\bb\u0002B\rK\u0011\u00051Q\u0002\u0005\b\u0005#+C\u0011IB\t\u0011\u001d\u00119+\nC!\u0007;1aaa\u000b\u0005\t\r5\u0002B\u0003B(m\t\u0005\t\u0015!\u0003\u00044!Q!\u0011\u000b\u001c\u0003\u0002\u0003\u0006Iaa\u000e\t\u0015\t-gG!A!\u0002\u0013\u0019\u0019\u0004\u0003\u0006\u0003NZ\u0012\t\u0011)A\u0005\u0007oA!ba\u000f7\u0005\u0003\u0005\u000b\u0011BB\u001a\u0011)\u0019iD\u000eB\u0001B\u0003%1q\u0007\u0005\b\u0003\u00132D\u0011AB \u0011\u001d\t9L\u000eC!\u0003sCq!!17\t\u0003\nI\fC\u0004\u0002DZ\"\tea\u0014\t\u000f\u0005-g\u0007\"\u0011\u0004T!9\u0011q\u001b\u001c\u0005\u0002\r]\u0003bBArm\u0011\u00053Q\f\u0005\b\u0003w4D\u0011AB7\u0011\u001d\u00119A\u000eC\u0001\u0007gBqA!\u00077\t\u0003\u0019\t\tC\u0004\u0003\u0012Z\"\te!\"\t\u000f\t\u001df\u0007\"\u0011\u0004\u0012\u001a11q\u0014\u0003\u0007\u0007CC!Ba\u0014J\u0005\u0003\u0005\u000b\u0011BBT\u0011)\u0011\t&\u0013B\u0001B\u0003%11\u0016\u0005\u000b\u0005\u0017L%\u0011!Q\u0001\n\r\u001d\u0006B\u0003Bg\u0013\n\u0005\t\u0015!\u0003\u0004,\"Q11H%\u0003\u0002\u0003\u0006Iaa*\t\u0015\ru\u0012J!A!\u0002\u0013\u0019Y\u000b\u0003\u0006\u00040&\u0013\t\u0011)A\u0005\u0007OC!b!-J\u0005\u0003\u0005\u000b\u0011BBV\u0011\u001d\tI%\u0013C\u0001\u0007gCq!a.J\t\u0003\nI\fC\u0004\u0002B&#\t%!/\t\u000f\u0005\r\u0017\n\"\u0011\u0004H\"9\u00111Z%\u0005B\r-\u0007bBAl\u0013\u0012\u00051q\u001a\u0005\b\u0003GLE\u0011IBk\u0011\u001d\tY0\u0013C\u0001\u0007KDqAa\u0002J\t\u0003\u0019Y\u000fC\u0004\u0003\u001a%#\ta!?\t\u000f\tE\u0015\n\"\u0011\u0004~\"9!qU%\u0005B\u0011%\u0001\u0002\u0003C\u000b\u0013\u0012\u0005A\u0001b\u0006\u0007\r\u00115BA\u0002C\u0018\u0011\u001d\tIe\u0018C\u0001\t\u000bB\u0001\u0002\"\u0013`A\u0003&A1\t\u0005\t\t\u0017z\u0006\u0015)\u0003\u0002P\"YAQJ0A\u0002\u0003\u0005\u000b\u0015\u0002C(\u0011\u001d!)f\u0018C!\u0003cAq\u0001b\u0016`\t\u0003\"I\u0006C\u0004\u0005\\}#\t\u0001\"\u0018\t\u000f\u0011\u0015t\f\"\u0011\u0005h!I!q\u0004\u0003\u0002\u0002\u0013%!\u0011\u0005\u0002\u0007'\u0016\fX*\u00199\u000b\u0005-d\u0017!C5n[V$\u0018M\u00197f\u0015\tig.\u0001\u0006d_2dWm\u0019;j_:T\u0011a\\\u0001\u0006g\u000e\fG.Y\u0002\u0001+\u0011\u0011X0a\u0004\u0014\u0013\u0001\u0019x/a\u0005\u0002\u001a\u0005\r\u0002C\u0001;v\u001b\u0005q\u0017B\u0001<o\u0005\u0019\te.\u001f*fMB)\u00010_>\u0002\u000e5\t!.\u0003\u0002{U\n\u0019Q*\u00199\u0011\u0005qlH\u0002\u0001\u0003\u0006}\u0002\u0011\ra \u0002\u0002\u0017F!\u0011\u0011AA\u0004!\r!\u00181A\u0005\u0004\u0003\u000bq'a\u0002(pi\"Lgn\u001a\t\u0004i\u0006%\u0011bAA\u0006]\n\u0019\u0011I\\=\u0011\u0007q\fy\u0001B\u0004\u0002\u0012\u0001!)\u0019A@\u0003\u0003Y\u0003r!!\u0006\u0002\u0018m\fi!D\u0001m\u0013\tIG\u000e\u0005\u0006y\u00037Y\u0018QBA\u0010\u0003CI1!!\bk\u0005\u0019i\u0015\r](qgB\u0011\u0001\u0010\u0001\t\u0006q\u0002Y\u0018Q\u0002\t\f\u0003+\t)c_A\u0007\u0003?\tI#C\u0002\u0002(1\u0014!#T1q\r\u0006\u001cGo\u001c:z\t\u00164\u0017-\u001e7ugB\u0019\u00010a\u000b\n\u0007\u00055\"N\u0001\u0005Ji\u0016\u0014\u0018M\u00197f\u0003\u0019!\u0013N\\5uIQ\u0011\u00111\u0007\t\u0004i\u0006U\u0012bAA\u001c]\n!QK\\5u\u0003)i\u0017\r\u001d$bGR|'/_\u000b\u0003\u0003{\u0001b!!\u0006\u0002@\u0005}\u0011bAA!Y\nQQ*\u00199GC\u000e$xN]=\u0002\rM+\u0017/T1q!\tAHa\u0005\u0003\u0005g\u0006u\u0012A\u0002\u001fj]&$h\b\u0006\u0002\u0002F\u0005)Q-\u001c9usV1\u0011\u0011KA,\u00037*\"!a\u0015\u0011\ra\u0004\u0011QKA-!\ra\u0018q\u000b\u0003\u0006}\u001a\u0011\ra \t\u0004y\u0006mCABA\t\r\t\u0007q0\u0001\u0003ge>lWCBA1\u0003O\nY\u0007\u0006\u0003\u0002d\u00055\u0004C\u0002=\u0001\u0003K\nI\u0007E\u0002}\u0003O\"QA`\u0004C\u0002}\u00042\u0001`A6\t\u0019\t\tb\u0002b\u0001\u007f\"9\u0011qN\u0004A\u0002\u0005E\u0014AA5u!\u0019\t)\"a\u001d\u0002x%\u0019\u0011Q\u000f7\u0003\u0019%#XM]1cY\u0016|enY3\u0011\u000fQ\fI(!\u001a\u0002j%\u0019\u00111\u00108\u0003\rQ+\b\u000f\\33\u0003)qWm\u001e\"vS2$WM]\u000b\u0007\u0003\u0003\u000b\u0019*a&\u0016\u0005\u0005\r\u0005\u0003CAC\u0003\u0017\u000by)!'\u000e\u0005\u0005\u001d%bAAEY\u00069Q.\u001e;bE2,\u0017\u0002BAG\u0003\u000f\u0013qAQ;jY\u0012,'\u000fE\u0004u\u0003s\n\t*!&\u0011\u0007q\f\u0019\nB\u0003\u007f\u0011\t\u0007q\u0010E\u0002}\u0003/#a!!\u0005\t\u0005\u0004y\bC\u0002=\u0001\u0003#\u000b)*A\u0006F[B$\u0018pU3r\u001b\u0006\u0004\bcAAP\u00155\tAAA\u0006F[B$\u0018pU3r\u001b\u0006\u00048C\u0002\u0006t\u0003K\u000b9\u000b\u0005\u0004y\u0001\u0005\u001d\u0011\u0011\u0001\t\u0005\u0003S\u000byKD\u0002u\u0003WK1!!,o\u0003\u001d\u0001\u0018mY6bO\u0016LA!!-\u00024\na1+\u001a:jC2L'0\u00192mK*\u0019\u0011Q\u00168\u0015\u0005\u0005u\u0015\u0001B:ju\u0016,\"!a/\u0011\u0007Q\fi,C\u0002\u0002@:\u00141!\u00138u\u0003%Ygn\\<o'&TX-A\u0003baBd\u0017\u0010\u0006\u0003\u0002\u0002\u0005\u001d\u0007bBAe\u001d\u0001\u0007\u0011qA\u0001\u0004W\u0016L\u0018\u0001C2p]R\f\u0017N\\:\u0015\t\u0005=\u0017Q\u001b\t\u0004i\u0006E\u0017bAAj]\n9!i\\8mK\u0006t\u0007bBAe\u001f\u0001\u0007\u0011qA\u0001\u0004O\u0016$H\u0003BAn\u0003C\u0004R\u0001^Ao\u0003\u0003I1!a8o\u0005\u0019y\u0005\u000f^5p]\"9\u0011\u0011\u001a\tA\u0002\u0005\u001d\u0011!C4fi>\u0013X\t\\:f+\u0011\t9/a;\u0015\r\u0005%\u0018q^Ay!\ra\u00181\u001e\u0003\u0007\u0003[\f\"\u0019A@\u0003\u0005Y\u000b\u0004bBAe#\u0001\u0007\u0011q\u0001\u0005\t\u0003g\fB\u00111\u0001\u0002v\u00069A-\u001a4bk2$\b#\u0002;\u0002x\u0006%\u0018bAA}]\nAAHY=oC6,g(\u0001\u0005ji\u0016\u0014\u0018\r^8s+\t\ty\u0010\u0005\u0004\u0002\u0016\t\u0005!QA\u0005\u0004\u0005\u0007a'\u0001C%uKJ\fGo\u001c:\u0011\u000fQ\fI(a\u0002\u0002\u0002\u00059Q\u000f\u001d3bi\u0016$W\u0003\u0002B\u0006\u0005#!bA!\u0004\u0003\u0014\tU\u0001C\u0002=\u0001\u0003\u000f\u0011y\u0001E\u0002}\u0005#!a!!<\u0014\u0005\u0004y\bbBAe'\u0001\u0007\u0011q\u0001\u0005\b\u0005/\u0019\u0002\u0019\u0001B\b\u0003\u00151\u0018\r\\;f\u0003\u001d\u0011X-\\8wK\u0012$B!!*\u0003\u001e!9\u0011\u0011\u001a\u000bA\u0002\u0005\u001d\u0011\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001B\u0012!\u0011\u0011)Ca\f\u000e\u0005\t\u001d\"\u0002\u0002B\u0015\u0005W\tA\u0001\\1oO*\u0011!QF\u0001\u0005U\u00064\u0018-\u0003\u0003\u00032\t\u001d\"AB(cU\u0016\u001cG\u000fK\u0004\u000b\u0005k\u00119Ba\u000f\u0011\u0007Q\u00149$C\u0002\u0003:9\u0014\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u001f\u0003\rAs!\u0003B\u001b\u0005/\u0011YDA\u0004TKFl\u0015\r]\u0019\u0016\r\t\r#\u0011\nB''\u001912O!\u0012\u0002(B1\u0001\u0010\u0001B$\u0005\u0017\u00022\u0001 B%\t\u0015qhC1\u0001\u0000!\ra(Q\n\u0003\b\u0003#1BQ1\u0001\u0000\u0003\u0011YW-_\u0019\u0002\rY\fG.^32)\u0019\u0011)Fa\u0016\u0003ZA9\u0011q\u0014\f\u0003H\t-\u0003b\u0002B(3\u0001\u0007!q\t\u0005\b\u0005#J\u0002\u0019\u0001B&)\u0011\u0011YE!\u0018\t\u000f\u0005%G\u00041\u0001\u0003HQ!\u0011q\u001aB1\u0011\u001d\tI-\ba\u0001\u0005\u000f\"BA!\u001a\u0003hA)A/!8\u0003L!9\u0011\u0011\u001a\u0010A\u0002\t\u001dS\u0003\u0002B6\u0005_\"bA!\u001c\u0003t\tU\u0004c\u0001?\u0003p\u00119\u0011Q^\u0010C\u0002\tE\u0014\u0003\u0002B&\u0003\u000fAq!!3 \u0001\u0004\u00119\u0005\u0003\u0005\u0002t~!\t\u0019\u0001B<!\u0015!\u0018q\u001fB7+\t\u0011Y\b\u0005\u0004\u0002\u0016\t\u0005!Q\u0010\t\bi\u0006e$q\tB&+\u0011\u0011\tIa\"\u0015\r\t\r%\u0011\u0012BF!\u0019A\bAa\u0012\u0003\u0006B\u0019APa\"\u0005\u000f\u00055\u0018E1\u0001\u0003r!9\u0011\u0011Z\u0011A\u0002\t\u001d\u0003b\u0002B\fC\u0001\u0007!Q\u0011\u000b\u0005\u0005\u000b\u0012y\tC\u0004\u0002J\n\u0002\rAa\u0012\u0002\u000f\u0019|'/Z1dQV!!Q\u0013BR)\u0011\t\u0019Da&\t\u000f\te5\u00051\u0001\u0003\u001c\u0006\ta\rE\u0004u\u0005;\u0013iH!)\n\u0007\t}eNA\u0005Gk:\u001cG/[8ocA\u0019APa)\u0005\r\t\u00156E1\u0001\u0000\u0005\u0005)\u0016\u0001\u00044pe\u0016\f7\r[#oiJLX\u0003\u0002BV\u0005o#B!a\r\u0003.\"9!\u0011\u0014\u0013A\u0002\t=\u0006#\u0003;\u00032\n\u001d#1\nB[\u0013\r\u0011\u0019L\u001c\u0002\n\rVt7\r^5p]J\u00022\u0001 B\\\t\u0019\u0011)\u000b\nb\u0001\u007f\":aC!\u000e\u0003\u0018\tm\"aB*fc6\u000b\u0007OM\u000b\u0007\u0005\u007f\u0013)M!3\u0014\r\u0015\u001a(\u0011YAT!\u0019A\bAa1\u0003HB\u0019AP!2\u0005\u000by,#\u0019A@\u0011\u0007q\u0014I\rB\u0004\u0002\u0012\u0015\")\u0019A@\u0002\t-,\u0017PM\u0001\u0007m\u0006dW/\u001a\u001a\u0015\u0015\tE'1\u001bBk\u0005/\u0014I\u000eE\u0004\u0002 \u0016\u0012\u0019Ma2\t\u000f\t=#\u00061\u0001\u0003D\"9!\u0011\u000b\u0016A\u0002\t\u001d\u0007b\u0002BfU\u0001\u0007!1\u0019\u0005\b\u0005\u001bT\u0003\u0019\u0001Bd)\u0011\u00119M!8\t\u000f\u0005%W\u00061\u0001\u0003DR!\u0011q\u001aBq\u0011\u001d\tIM\fa\u0001\u0005\u0007$BA!:\u0003hB)A/!8\u0003H\"9\u0011\u0011Z\u0018A\u0002\t\rW\u0003\u0002Bv\u0005_$bA!<\u0003t\nU\bc\u0001?\u0003p\u00129\u0011Q\u001e\u0019C\u0002\tE\u0018\u0003\u0002Bd\u0003\u000fAq!!31\u0001\u0004\u0011\u0019\r\u0003\u0005\u0002tB\"\t\u0019\u0001B|!\u0015!\u0018q\u001fBw+\t\u0011Y\u0010\u0005\u0004\u0002\u0016\t\u0005!Q \t\bi\u0006e$1\u0019Bd+\u0011\u0019\taa\u0002\u0015\r\r\r1\u0011BB\u0006!\u0019A\bAa1\u0004\u0006A\u0019Apa\u0002\u0005\u000f\u00055(G1\u0001\u0003r\"9\u0011\u0011\u001a\u001aA\u0002\t\r\u0007b\u0002B\fe\u0001\u00071Q\u0001\u000b\u0005\u0005\u0003\u001cy\u0001C\u0004\u0002JN\u0002\rAa1\u0016\t\rM11\u0004\u000b\u0005\u0003g\u0019)\u0002C\u0004\u0003\u001aR\u0002\raa\u0006\u0011\u000fQ\u0014iJ!@\u0004\u001aA\u0019Apa\u0007\u0005\r\t\u0015FG1\u0001\u0000+\u0011\u0019yba\n\u0015\t\u0005M2\u0011\u0005\u0005\b\u00053+\u0004\u0019AB\u0012!%!(\u0011\u0017Bb\u0005\u000f\u001c)\u0003E\u0002}\u0007O!aA!*6\u0005\u0004y\bfB\u0013\u00036\t]!1\b\u0002\b'\u0016\fX*\u001994+\u0019\u0019yc!\u000e\u0004:M1ag]B\u0019\u0003O\u0003b\u0001\u001f\u0001\u00044\r]\u0002c\u0001?\u00046\u0011)aP\u000eb\u0001\u007fB\u0019Ap!\u000f\u0005\u000f\u0005Ea\u0007\"b\u0001\u007f\u0006!1.Z=4\u0003\u00191\u0018\r\\;fgQq1\u0011IB\"\u0007\u000b\u001a9e!\u0013\u0004L\r5\u0003cBAPm\rM2q\u0007\u0005\b\u0005\u001fj\u0004\u0019AB\u001a\u0011\u001d\u0011\t&\u0010a\u0001\u0007oAqAa3>\u0001\u0004\u0019\u0019\u0004C\u0004\u0003Nv\u0002\raa\u000e\t\u000f\rmR\b1\u0001\u00044!91QH\u001fA\u0002\r]B\u0003BB\u001c\u0007#Bq!!3A\u0001\u0004\u0019\u0019\u0004\u0006\u0003\u0002P\u000eU\u0003bBAe\u0003\u0002\u000711\u0007\u000b\u0005\u00073\u001aY\u0006E\u0003u\u0003;\u001c9\u0004C\u0004\u0002J\n\u0003\raa\r\u0016\t\r}31\r\u000b\u0007\u0007C\u001a9g!\u001b\u0011\u0007q\u001c\u0019\u0007B\u0004\u0002n\u000e\u0013\ra!\u001a\u0012\t\r]\u0012q\u0001\u0005\b\u0003\u0013\u001c\u0005\u0019AB\u001a\u0011!\t\u0019p\u0011CA\u0002\r-\u0004#\u0002;\u0002x\u000e\u0005TCAB8!\u0019\t)B!\u0001\u0004rA9A/!\u001f\u00044\r]R\u0003BB;\u0007w\"baa\u001e\u0004~\r}\u0004C\u0002=\u0001\u0007g\u0019I\bE\u0002}\u0007w\"q!!<F\u0005\u0004\u0019)\u0007C\u0004\u0002J\u0016\u0003\raa\r\t\u000f\t]Q\t1\u0001\u0004zQ!1\u0011GBB\u0011\u001d\tIM\u0012a\u0001\u0007g)Baa\"\u0004\u0010R!\u00111GBE\u0011\u001d\u0011Ij\u0012a\u0001\u0007\u0017\u0003r\u0001\u001eBO\u0007c\u001ai\tE\u0002}\u0007\u001f#aA!*H\u0005\u0004yX\u0003BBJ\u00077#B!a\r\u0004\u0016\"9!\u0011\u0014%A\u0002\r]\u0005#\u0003;\u00032\u000eM2qGBM!\ra81\u0014\u0003\u0007\u0005KC%\u0019A@)\u000fY\u0012)Da\u0006\u0003<\t91+Z9NCB$TCBBR\u0007S\u001bik\u0005\u0004Jg\u000e\u0015\u0016q\u0015\t\u0007q\u0002\u00199ka+\u0011\u0007q\u001cI\u000bB\u0003\u007f\u0013\n\u0007q\u0010E\u0002}\u0007[#q!!\u0005J\t\u000b\u0007q0\u0001\u0003lKf$\u0014A\u0002<bYV,G\u0007\u0006\n\u00046\u000e]6\u0011XB^\u0007{\u001byl!1\u0004D\u000e\u0015\u0007cBAP\u0013\u000e\u001d61\u0016\u0005\b\u0005\u001f\u0012\u0006\u0019ABT\u0011\u001d\u0011\tF\u0015a\u0001\u0007WCqAa3S\u0001\u0004\u00199\u000bC\u0004\u0003NJ\u0003\raa+\t\u000f\rm\"\u000b1\u0001\u0004(\"91Q\b*A\u0002\r-\u0006bBBX%\u0002\u00071q\u0015\u0005\b\u0007c\u0013\u0006\u0019ABV)\u0011\u0019Yk!3\t\u000f\u0005%W\u000b1\u0001\u0004(R!\u0011qZBg\u0011\u001d\tIM\u0016a\u0001\u0007O#Ba!5\u0004TB)A/!8\u0004,\"9\u0011\u0011Z,A\u0002\r\u001dV\u0003BBl\u00077$ba!7\u0004`\u000e\u0005\bc\u0001?\u0004\\\u00129\u0011Q\u001e-C\u0002\ru\u0017\u0003BBV\u0003\u000fAq!!3Y\u0001\u0004\u00199\u000b\u0003\u0005\u0002tb#\t\u0019ABr!\u0015!\u0018q_Bm+\t\u00199\u000f\u0005\u0004\u0002\u0016\t\u00051\u0011\u001e\t\bi\u0006e4qUBV+\u0011\u0019ioa=\u0015\r\r=8Q_B|!\u0019A\baa*\u0004rB\u0019Apa=\u0005\u000f\u00055(L1\u0001\u0004^\"9\u0011\u0011\u001a.A\u0002\r\u001d\u0006b\u0002B\f5\u0002\u00071\u0011\u001f\u000b\u0005\u0007K\u001bY\u0010C\u0004\u0002Jn\u0003\raa*\u0016\t\r}Hq\u0001\u000b\u0005\u0003g!\t\u0001C\u0004\u0003\u001ar\u0003\r\u0001b\u0001\u0011\u000fQ\u0014ij!;\u0005\u0006A\u0019A\u0010b\u0002\u0005\r\t\u0015FL1\u0001\u0000+\u0011!Y\u0001b\u0005\u0015\t\u0005MBQ\u0002\u0005\b\u00053k\u0006\u0019\u0001C\b!%!(\u0011WBT\u0007W#\t\u0002E\u0002}\t'!aA!*^\u0005\u0004y\u0018a\u00022vS2$Gk\\\u000b\u0005\t3!9\u0003\u0006\u0003\u0005\u001c\u0011uab\u0001?\u0005\u001e!9Aq\u00040A\u0002\u0011\u0005\u0012a\u00022vS2$WM\u001d\t\t\u0003\u000b\u000bY\tb\t\u0005*A9A/!\u001f\u0004(\u0012\u0015\u0002c\u0001?\u0005(\u00119\u0011Q\u001e0C\u0002\ru\u0007C\u0002=\u0001\u0007O#)\u0003K\u0004J\u0005k\u00119Ba\u000f\u0003#M+\u0017/T1q\u0005VLG\u000eZ3s\u00136\u0004H.\u0006\u0004\u00052\u0011uB\u0011I\n\u0005?N$\u0019\u0004\u0005\u0005\u0002\u0006\u0012UB\u0011\bC\"\u0013\u0011!9$a\"\u0003\u001fI+Wo]1cY\u0016\u0014U/\u001b7eKJ\u0004r\u0001^A=\tw!y\u0004E\u0002}\t{!QA`0C\u0002}\u00042\u0001 C!\t\u0019\t\tb\u0018b\u0001\u007fB1\u0001\u0010\u0001C\u001e\t\u007f!\"\u0001b\u0012\u0011\u000f\u0005}u\fb\u000f\u0005@\u0005)Q\r\\3ng\u0006Q2o^5uG\",G\rV8WK\u000e$xN]'ba\n+\u0018\u000e\u001c3fe\u0006\u0001b/Z2u_Jl\u0015\r\u001d\"vS2$WM\u001d\t\bq\u0012EC1\bC \u0013\r!\u0019F\u001b\u0002\u0011-\u0016\u001cGo\u001c:NCB\u0014U/\u001b7eKJ\fQa\u00197fCJ\faA]3tk2$HC\u0001C\"\u0003\u0019\tG\rZ(oKR!Aq\fC1\u001b\u0005y\u0006b\u0002C2M\u0002\u0007A\u0011H\u0001\u0005K2,W.\u0001\u0004bI\u0012\fE\u000e\u001c\u000b\u0005\t?\"I\u0007C\u0004\u0005l\u001d\u0004\r\u0001\"\u001c\u0002\u0005a\u001c\bCBA\u000b\u0003g\"I\u0004")
public interface SeqMap<K, V>
extends scala.collection.immutable.Map<K, V>,
scala.collection.SeqMap<K, V> {
    public static <K, V> Builder<Tuple2<K, V>, SeqMap<K, V>> newBuilder() {
        return new SeqMapBuilderImpl();
    }

    public static <K, V> SeqMap<K, V> from(IterableOnce<Tuple2<K, V>> it) {
        return SeqMap$.MODULE$.from((IterableOnce)it);
    }

    @Override
    default public MapFactory<SeqMap> mapFactory() {
        return SeqMap$.MODULE$;
    }

    public static void $init$(SeqMap $this) {
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class SeqMap1<K, V>
    implements SeqMap<K, V>,
    Serializable {
        private static final long serialVersionUID = 3L;
        private final K key1;
        private final V value1;

        @Override
        public MapFactory<SeqMap> mapFactory() {
            return ((SeqMap)this).mapFactory();
        }

        @Override
        public String stringPrefix() {
            return scala.collection.SeqMap.stringPrefix$(this);
        }

        @Override
        public final <K2, V2> scala.collection.immutable.Map<K2, V2> toMap($less$colon$less<Tuple2<K, V>, Tuple2<K2, V2>> ev) {
            return scala.collection.immutable.Map.toMap$(this, ev);
        }

        @Override
        public <V1> scala.collection.immutable.Map<K, V1> withDefault(Function1<K, V1> d) {
            return scala.collection.immutable.Map.withDefault$(this, d);
        }

        @Override
        public <V1> scala.collection.immutable.Map<K, V1> withDefaultValue(V1 d) {
            return scala.collection.immutable.Map.withDefaultValue$(this, d);
        }

        @Override
        public final scala.collection.immutable.MapOps $minus(Object key) {
            return scala.collection.immutable.MapOps.$minus$(this, key);
        }

        @Override
        public scala.collection.immutable.MapOps $minus(Object key1, Object key2, Seq keys2) {
            return scala.collection.immutable.MapOps.$minus$(this, key1, key2, keys2);
        }

        @Override
        public scala.collection.immutable.MapOps removedAll(IterableOnce keys2) {
            return scala.collection.immutable.MapOps.removedAll$(this, keys2);
        }

        @Override
        public final scala.collection.immutable.MapOps $minus$minus(IterableOnce keys2) {
            return scala.collection.immutable.MapOps.$minus$minus$(this, keys2);
        }

        @Override
        public scala.collection.immutable.MapOps updatedWith(Object key, Function1 remappingFunction) {
            return scala.collection.immutable.MapOps.updatedWith$(this, key, remappingFunction);
        }

        @Override
        public scala.collection.immutable.MapOps $plus(Tuple2 kv) {
            return scala.collection.immutable.MapOps.$plus$(this, kv);
        }

        @Override
        public scala.collection.immutable.MapOps transform(Function2 f) {
            return scala.collection.immutable.MapOps.transform$(this, f);
        }

        @Override
        public Set<K> keySet() {
            return scala.collection.immutable.MapOps.keySet$(this);
        }

        @Override
        public boolean canEqual(Object that) {
            return Map.canEqual$(this, that);
        }

        @Override
        public boolean equals(Object o) {
            return Map.equals$(this, o);
        }

        @Override
        public int hashCode() {
            return Map.hashCode$(this);
        }

        @Override
        public String toString() {
            return Map.toString$(this);
        }

        @Override
        public IterableOps fromSpecific(IterableOnce coll) {
            return MapFactoryDefaults.fromSpecific$(this, coll);
        }

        @Override
        public Builder<Tuple2<K, V>, SeqMap<K, V>> newSpecificBuilder() {
            return MapFactoryDefaults.newSpecificBuilder$(this);
        }

        @Override
        public IterableOps empty() {
            return MapFactoryDefaults.empty$(this);
        }

        @Override
        public MapOps.WithFilter<K, V, ?, ?> withFilter(Function1<Tuple2<K, V>, Object> p) {
            return MapFactoryDefaults.withFilter$(this, p);
        }

        @Override
        public MapView<K, V> view() {
            return MapOps.view$(this);
        }

        @Override
        public <S extends Stepper<?>> S keyStepper(StepperShape<K, S> shape) {
            return (S)MapOps.keyStepper$(this, shape);
        }

        @Override
        public <S extends Stepper<?>> S valueStepper(StepperShape<V, S> shape) {
            return (S)MapOps.valueStepper$(this, shape);
        }

        @Override
        public final IterableOps mapFromIterable(Iterable it) {
            return MapOps.mapFromIterable$(this, it);
        }

        @Override
        public <K1 extends K, V1> V1 applyOrElse(K1 x, Function1<K1, V1> function1) {
            return (V1)MapOps.applyOrElse$(this, x, function1);
        }

        @Override
        public Iterable<K> keys() {
            return MapOps.keys$(this);
        }

        @Override
        public Iterable<V> values() {
            return MapOps.values$(this);
        }

        @Override
        public Iterator<K> keysIterator() {
            return MapOps.keysIterator$(this);
        }

        @Override
        public Iterator<V> valuesIterator() {
            return MapOps.valuesIterator$(this);
        }

        @Override
        public MapView<K, V> filterKeys(Function1<K, Object> p) {
            return MapOps.filterKeys$(this, p);
        }

        @Override
        public <W> MapView<K, W> mapValues(Function1<V, W> f) {
            return MapOps.mapValues$(this, f);
        }

        @Override
        public V default(K key) throws NoSuchElementException {
            return (V)MapOps.default$(this, key);
        }

        @Override
        public boolean isDefinedAt(K key) {
            return MapOps.isDefinedAt$(this, key);
        }

        @Override
        public IterableOps map(Function1 f) {
            return MapOps.map$(this, f);
        }

        @Override
        public IterableOps collect(PartialFunction pf) {
            return MapOps.collect$(this, pf);
        }

        @Override
        public IterableOps flatMap(Function1 f) {
            return MapOps.flatMap$(this, f);
        }

        @Override
        public IterableOps concat(IterableOnce suffix) {
            return MapOps.concat$(this, suffix);
        }

        @Override
        public IterableOps $plus$plus(IterableOnce xs) {
            return MapOps.$plus$plus$(this, xs);
        }

        @Override
        public StringBuilder addString(StringBuilder sb, String start2, String sep, String end) {
            return MapOps.addString$(this, sb, start2, sep, end);
        }

        @Override
        public IterableOps $plus(Tuple2 elem1, Tuple2 elem2, Seq elems) {
            return MapOps.$plus$(this, elem1, elem2, elems);
        }

        @Override
        public IterableOps $plus$plus$colon(IterableOnce that) {
            return MapOps.$plus$plus$colon$(this, that);
        }

        @Override
        public Option<V> unapply(K a) {
            return PartialFunction.unapply$(this, a);
        }

        @Override
        public PartialFunction<K, V> elementWise() {
            return PartialFunction.elementWise$(this);
        }

        @Override
        public <A1 extends K, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
            return PartialFunction.orElse$(this, that);
        }

        @Override
        public <C$> PartialFunction<K, C$> andThen(Function1<V, C$> k) {
            return PartialFunction.andThen$((PartialFunction)this, k);
        }

        @Override
        public <C$> PartialFunction<K, C$> andThen(PartialFunction<V, C$> k) {
            return PartialFunction.andThen$((PartialFunction)this, k);
        }

        @Override
        public <R$> PartialFunction<R$, V> compose(PartialFunction<R$, K> k) {
            return PartialFunction.compose$(this, k);
        }

        @Override
        public Function1<K, Option<V>> lift() {
            return PartialFunction.lift$(this);
        }

        @Override
        public <U> Function1<K, Object> runWith(Function1<V, U> action) {
            return PartialFunction.runWith$(this, action);
        }

        @Override
        public boolean apply$mcZD$sp(double v1) {
            return Function1.apply$mcZD$sp$(this, v1);
        }

        @Override
        public double apply$mcDD$sp(double v1) {
            return Function1.apply$mcDD$sp$(this, v1);
        }

        @Override
        public float apply$mcFD$sp(double v1) {
            return Function1.apply$mcFD$sp$(this, v1);
        }

        @Override
        public int apply$mcID$sp(double v1) {
            return Function1.apply$mcID$sp$(this, v1);
        }

        @Override
        public long apply$mcJD$sp(double v1) {
            return Function1.apply$mcJD$sp$(this, v1);
        }

        @Override
        public void apply$mcVD$sp(double v1) {
            Function1.apply$mcVD$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZF$sp(float v1) {
            return Function1.apply$mcZF$sp$(this, v1);
        }

        @Override
        public double apply$mcDF$sp(float v1) {
            return Function1.apply$mcDF$sp$(this, v1);
        }

        @Override
        public float apply$mcFF$sp(float v1) {
            return Function1.apply$mcFF$sp$(this, v1);
        }

        @Override
        public int apply$mcIF$sp(float v1) {
            return Function1.apply$mcIF$sp$(this, v1);
        }

        @Override
        public long apply$mcJF$sp(float v1) {
            return Function1.apply$mcJF$sp$(this, v1);
        }

        @Override
        public void apply$mcVF$sp(float v1) {
            Function1.apply$mcVF$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZI$sp(int v1) {
            return Function1.apply$mcZI$sp$(this, v1);
        }

        @Override
        public double apply$mcDI$sp(int v1) {
            return Function1.apply$mcDI$sp$(this, v1);
        }

        @Override
        public float apply$mcFI$sp(int v1) {
            return Function1.apply$mcFI$sp$(this, v1);
        }

        @Override
        public int apply$mcII$sp(int v1) {
            return Function1.apply$mcII$sp$(this, v1);
        }

        @Override
        public long apply$mcJI$sp(int v1) {
            return Function1.apply$mcJI$sp$(this, v1);
        }

        @Override
        public void apply$mcVI$sp(int v1) {
            Function1.apply$mcVI$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZJ$sp(long v1) {
            return Function1.apply$mcZJ$sp$(this, v1);
        }

        @Override
        public double apply$mcDJ$sp(long v1) {
            return Function1.apply$mcDJ$sp$(this, v1);
        }

        @Override
        public float apply$mcFJ$sp(long v1) {
            return Function1.apply$mcFJ$sp$(this, v1);
        }

        @Override
        public int apply$mcIJ$sp(long v1) {
            return Function1.apply$mcIJ$sp$(this, v1);
        }

        @Override
        public long apply$mcJJ$sp(long v1) {
            return Function1.apply$mcJJ$sp$(this, v1);
        }

        @Override
        public void apply$mcVJ$sp(long v1) {
            Function1.apply$mcVJ$sp$(this, v1);
        }

        @Override
        public <A$> Function1<A$, V> compose(Function1<A$, K> g2) {
            return Function1.compose$(this, g2);
        }

        @Override
        public IterableFactory<scala.collection.immutable.Iterable> iterableFactory() {
            return scala.collection.immutable.Iterable.iterableFactory$(this);
        }

        @Override
        public final Iterable<Tuple2<K, V>> toIterable() {
            return Iterable.toIterable$(this);
        }

        @Override
        public final Iterable<Tuple2<K, V>> coll() {
            return Iterable.coll$(this);
        }

        @Override
        public Iterable<Tuple2<K, V>> seq() {
            return Iterable.seq$(this);
        }

        @Override
        public String className() {
            return Iterable.className$(this);
        }

        @Override
        public final String collectionClassName() {
            return Iterable.collectionClassName$(this);
        }

        @Override
        public <B> LazyZip2<Tuple2<K, V>, B, Iterable> lazyZip(Iterable<B> that) {
            return Iterable.lazyZip$(this, that);
        }

        @Override
        public final Iterable<Tuple2<K, V>> toTraversable() {
            return IterableOps.toTraversable$(this);
        }

        @Override
        public boolean isTraversableAgain() {
            return IterableOps.isTraversableAgain$(this);
        }

        @Override
        public final Object repr() {
            return IterableOps.repr$(this);
        }

        @Override
        public IterableFactory<?> companion() {
            return IterableOps.companion$(this);
        }

        @Override
        public Object head() {
            return IterableOps.head$(this);
        }

        @Override
        public Option<Tuple2<K, V>> headOption() {
            return IterableOps.headOption$(this);
        }

        @Override
        public Object last() {
            return IterableOps.last$(this);
        }

        @Override
        public Option<Tuple2<K, V>> lastOption() {
            return IterableOps.lastOption$(this);
        }

        @Override
        public int sizeCompare(int otherSize) {
            return IterableOps.sizeCompare$((IterableOps)this, otherSize);
        }

        @Override
        public final IterableOps sizeIs() {
            return IterableOps.sizeIs$(this);
        }

        @Override
        public int sizeCompare(Iterable<?> that) {
            return IterableOps.sizeCompare$((IterableOps)this, that);
        }

        @Override
        public View<Tuple2<K, V>> view(int from, int until) {
            return IterableOps.view$(this, from, until);
        }

        @Override
        public Object transpose(Function1 asIterable) {
            return IterableOps.transpose$(this, asIterable);
        }

        @Override
        public Object filter(Function1 pred) {
            return IterableOps.filter$(this, pred);
        }

        @Override
        public Object filterNot(Function1 pred) {
            return IterableOps.filterNot$(this, pred);
        }

        @Override
        public Tuple2<SeqMap<K, V>, SeqMap<K, V>> partition(Function1<Tuple2<K, V>, Object> p) {
            return IterableOps.partition$(this, p);
        }

        @Override
        public Tuple2<SeqMap<K, V>, SeqMap<K, V>> splitAt(int n) {
            return IterableOps.splitAt$(this, n);
        }

        @Override
        public Object take(int n) {
            return IterableOps.take$(this, n);
        }

        @Override
        public Object takeRight(int n) {
            return IterableOps.takeRight$(this, n);
        }

        @Override
        public Object takeWhile(Function1 p) {
            return IterableOps.takeWhile$(this, p);
        }

        @Override
        public Tuple2<SeqMap<K, V>, SeqMap<K, V>> span(Function1<Tuple2<K, V>, Object> p) {
            return IterableOps.span$(this, p);
        }

        @Override
        public Object drop(int n) {
            return IterableOps.drop$(this, n);
        }

        @Override
        public Object dropRight(int n) {
            return IterableOps.dropRight$(this, n);
        }

        @Override
        public Object dropWhile(Function1 p) {
            return IterableOps.dropWhile$(this, p);
        }

        @Override
        public Iterator<SeqMap<K, V>> grouped(int size) {
            return IterableOps.grouped$(this, size);
        }

        @Override
        public Iterator<SeqMap<K, V>> sliding(int size) {
            return IterableOps.sliding$(this, size);
        }

        @Override
        public Iterator<SeqMap<K, V>> sliding(int size, int step) {
            return IterableOps.sliding$(this, size, step);
        }

        @Override
        public Object tail() {
            return IterableOps.tail$(this);
        }

        @Override
        public Object init() {
            return IterableOps.init$(this);
        }

        @Override
        public Object slice(int from, int until) {
            return IterableOps.slice$(this, from, until);
        }

        @Override
        public <K$> scala.collection.immutable.Map<K$, SeqMap<K, V>> groupBy(Function1<Tuple2<K, V>, K$> f) {
            return IterableOps.groupBy$(this, f);
        }

        @Override
        public <K$, B> scala.collection.immutable.Map<K$, scala.collection.immutable.Iterable<B>> groupMap(Function1<Tuple2<K, V>, K$> key, Function1<Tuple2<K, V>, B> f) {
            return IterableOps.groupMap$(this, key, f);
        }

        @Override
        public <K$, B> scala.collection.immutable.Map<K$, B> groupMapReduce(Function1<Tuple2<K, V>, K$> key, Function1<Tuple2<K, V>, B> f, Function2<B, B, B> reduce) {
            return IterableOps.groupMapReduce$(this, key, f, reduce);
        }

        @Override
        public Object scan(Object z, Function2 op) {
            return IterableOps.scan$(this, z, op);
        }

        @Override
        public Object scanLeft(Object z, Function2 op) {
            return IterableOps.scanLeft$(this, z, op);
        }

        @Override
        public Object scanRight(Object z, Function2 op) {
            return IterableOps.scanRight$(this, z, op);
        }

        @Override
        public Object map(Function1 f) {
            return IterableOps.map$(this, f);
        }

        @Override
        public Object flatMap(Function1 f) {
            return IterableOps.flatMap$(this, f);
        }

        @Override
        public Object flatten(Function1 asIterable) {
            return IterableOps.flatten$(this, asIterable);
        }

        @Override
        public Object collect(PartialFunction pf) {
            return IterableOps.collect$(this, pf);
        }

        @Override
        public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> partitionMap(Function1<Tuple2<K, V>, Either<A1, A2>> f) {
            return IterableOps.partitionMap$(this, f);
        }

        @Override
        public Object concat(IterableOnce suffix) {
            return IterableOps.concat$(this, suffix);
        }

        @Override
        public final Object $plus$plus(IterableOnce suffix) {
            return IterableOps.$plus$plus$(this, suffix);
        }

        @Override
        public Object zip(IterableOnce that) {
            return IterableOps.zip$(this, that);
        }

        @Override
        public Object zipWithIndex() {
            return IterableOps.zipWithIndex$(this);
        }

        @Override
        public Object zipAll(Iterable that, Object thisElem, Object thatElem) {
            return IterableOps.zipAll$(this, that, thisElem, thatElem);
        }

        @Override
        public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> unzip(Function1<Tuple2<K, V>, Tuple2<A1, A2>> asPair) {
            return IterableOps.unzip$(this, asPair);
        }

        @Override
        public <A1, A2, A3> Tuple3<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>, scala.collection.immutable.Iterable<A3>> unzip3(Function1<Tuple2<K, V>, Tuple3<A1, A2, A3>> asTriple) {
            return IterableOps.unzip3$(this, asTriple);
        }

        @Override
        public Iterator<SeqMap<K, V>> tails() {
            return IterableOps.tails$(this);
        }

        @Override
        public Iterator<SeqMap<K, V>> inits() {
            return IterableOps.inits$(this);
        }

        @Override
        public Object tapEach(Function1 f) {
            return IterableOps.tapEach$(this, f);
        }

        @Override
        public Object $plus$plus$colon(IterableOnce that) {
            return IterableOps.$plus$plus$colon$(this, that);
        }

        @Override
        public boolean hasDefiniteSize() {
            return IterableOnceOps.hasDefiniteSize$(this);
        }

        @Override
        public boolean forall(Function1<Tuple2<K, V>, Object> p) {
            return IterableOnceOps.forall$(this, p);
        }

        @Override
        public boolean exists(Function1<Tuple2<K, V>, Object> p) {
            return IterableOnceOps.exists$(this, p);
        }

        @Override
        public int count(Function1<Tuple2<K, V>, Object> p) {
            return IterableOnceOps.count$(this, p);
        }

        @Override
        public Option<Tuple2<K, V>> find(Function1<Tuple2<K, V>, Object> p) {
            return IterableOnceOps.find$(this, p);
        }

        @Override
        public <B> B foldLeft(B z, Function2<B, Tuple2<K, V>, B> op) {
            return (B)IterableOnceOps.foldLeft$(this, z, op);
        }

        @Override
        public <B> B foldRight(B z, Function2<Tuple2<K, V>, B, B> op) {
            return (B)IterableOnceOps.foldRight$(this, z, op);
        }

        @Override
        public final <B> B $div$colon(B z, Function2<B, Tuple2<K, V>, B> op) {
            return (B)IterableOnceOps.$div$colon$(this, z, op);
        }

        @Override
        public final <B> B $colon$bslash(B z, Function2<Tuple2<K, V>, B, B> op) {
            return (B)IterableOnceOps.$colon$bslash$(this, z, op);
        }

        @Override
        public <A1> A1 fold(A1 z, Function2<A1, A1, A1> op) {
            return (A1)IterableOnceOps.fold$(this, z, op);
        }

        @Override
        public <B> B reduce(Function2<B, B, B> op) {
            return (B)IterableOnceOps.reduce$(this, op);
        }

        @Override
        public <B> Option<B> reduceOption(Function2<B, B, B> op) {
            return IterableOnceOps.reduceOption$(this, op);
        }

        @Override
        public <B> B reduceLeft(Function2<B, Tuple2<K, V>, B> op) {
            return (B)IterableOnceOps.reduceLeft$(this, op);
        }

        @Override
        public <B> B reduceRight(Function2<Tuple2<K, V>, B, B> op) {
            return (B)IterableOnceOps.reduceRight$(this, op);
        }

        @Override
        public <B> Option<B> reduceLeftOption(Function2<B, Tuple2<K, V>, B> op) {
            return IterableOnceOps.reduceLeftOption$(this, op);
        }

        @Override
        public <B> Option<B> reduceRightOption(Function2<Tuple2<K, V>, B, B> op) {
            return IterableOnceOps.reduceRightOption$(this, op);
        }

        @Override
        public boolean isEmpty() {
            return IterableOnceOps.isEmpty$(this);
        }

        @Override
        public boolean nonEmpty() {
            return IterableOnceOps.nonEmpty$(this);
        }

        @Override
        public final <B> void copyToBuffer(Buffer<B> dest) {
            IterableOnceOps.copyToBuffer$(this, dest);
        }

        @Override
        public <B> int copyToArray(Object xs) {
            return IterableOnceOps.copyToArray$(this, xs);
        }

        @Override
        public <B> int copyToArray(Object xs, int start2) {
            return IterableOnceOps.copyToArray$(this, xs, start2);
        }

        @Override
        public <B> int copyToArray(Object xs, int start2, int len) {
            return IterableOnceOps.copyToArray$(this, xs, start2, len);
        }

        @Override
        public <B> B sum(Numeric<B> num) {
            return (B)IterableOnceOps.sum$(this, num);
        }

        @Override
        public <B> B product(Numeric<B> num) {
            return (B)IterableOnceOps.product$(this, num);
        }

        @Override
        public Object min(Ordering ord) {
            return IterableOnceOps.min$(this, ord);
        }

        @Override
        public <B> Option<Tuple2<K, V>> minOption(Ordering<B> ord) {
            return IterableOnceOps.minOption$(this, ord);
        }

        @Override
        public Object max(Ordering ord) {
            return IterableOnceOps.max$(this, ord);
        }

        @Override
        public <B> Option<Tuple2<K, V>> maxOption(Ordering<B> ord) {
            return IterableOnceOps.maxOption$(this, ord);
        }

        @Override
        public Object maxBy(Function1 f, Ordering cmp) {
            return IterableOnceOps.maxBy$(this, f, cmp);
        }

        @Override
        public <B> Option<Tuple2<K, V>> maxByOption(Function1<Tuple2<K, V>, B> f, Ordering<B> cmp) {
            return IterableOnceOps.maxByOption$(this, f, cmp);
        }

        @Override
        public Object minBy(Function1 f, Ordering cmp) {
            return IterableOnceOps.minBy$(this, f, cmp);
        }

        @Override
        public <B> Option<Tuple2<K, V>> minByOption(Function1<Tuple2<K, V>, B> f, Ordering<B> cmp) {
            return IterableOnceOps.minByOption$(this, f, cmp);
        }

        @Override
        public <B> Option<B> collectFirst(PartialFunction<Tuple2<K, V>, B> pf) {
            return IterableOnceOps.collectFirst$(this, pf);
        }

        @Override
        public <B> B aggregate(Function0<B> z, Function2<B, Tuple2<K, V>, B> seqop, Function2<B, B, B> combop) {
            return (B)IterableOnceOps.aggregate$(this, z, seqop, combop);
        }

        @Override
        public <B> boolean corresponds(IterableOnce<B> that, Function2<Tuple2<K, V>, B, Object> p) {
            return IterableOnceOps.corresponds$(this, that, p);
        }

        @Override
        public final String mkString(String start2, String sep, String end) {
            return IterableOnceOps.mkString$(this, start2, sep, end);
        }

        @Override
        public final String mkString(String sep) {
            return IterableOnceOps.mkString$(this, sep);
        }

        @Override
        public final String mkString() {
            return IterableOnceOps.mkString$(this);
        }

        @Override
        public final StringBuilder addString(StringBuilder b, String sep) {
            return IterableOnceOps.addString$(this, b, sep);
        }

        @Override
        public final StringBuilder addString(StringBuilder b) {
            return IterableOnceOps.addString$(this, b);
        }

        @Override
        public <C1> C1 to(Factory<Tuple2<K, V>, C1> factory2) {
            return (C1)IterableOnceOps.to$(this, factory2);
        }

        @Override
        public final Iterator<Tuple2<K, V>> toIterator() {
            return IterableOnceOps.toIterator$(this);
        }

        @Override
        public List<Tuple2<K, V>> toList() {
            return IterableOnceOps.toList$(this);
        }

        @Override
        public Vector<Tuple2<K, V>> toVector() {
            return IterableOnceOps.toVector$(this);
        }

        @Override
        public <B> Set<B> toSet() {
            return IterableOnceOps.toSet$(this);
        }

        @Override
        public Seq<Tuple2<K, V>> toSeq() {
            return IterableOnceOps.toSeq$(this);
        }

        @Override
        public IndexedSeq<Tuple2<K, V>> toIndexedSeq() {
            return IterableOnceOps.toIndexedSeq$(this);
        }

        @Override
        public final Stream<Tuple2<K, V>> toStream() {
            return IterableOnceOps.toStream$(this);
        }

        @Override
        public final <B> Buffer<B> toBuffer() {
            return IterableOnceOps.toBuffer$(this);
        }

        @Override
        public <B> Object toArray(ClassTag<B> evidence$2) {
            return IterableOnceOps.toArray$(this, evidence$2);
        }

        @Override
        public Iterable<Tuple2<K, V>> reversed() {
            return IterableOnceOps.reversed$(this);
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<Tuple2<K, V>, S> shape) {
            return (S)IterableOnce.stepper$(this, shape);
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public int knownSize() {
            return 1;
        }

        @Override
        public V apply(K key) {
            if (BoxesRunTime.equals(key, this.key1)) {
                return this.value1;
            }
            throw new NoSuchElementException(new java.lang.StringBuilder(15).append("key not found: ").append(key).toString());
        }

        @Override
        public boolean contains(K key) {
            return BoxesRunTime.equals(key, this.key1);
        }

        @Override
        public Option<V> get(K key) {
            if (BoxesRunTime.equals(key, this.key1)) {
                return new Some<V>(this.value1);
            }
            return None$.MODULE$;
        }

        @Override
        public <V1> V1 getOrElse(K key, Function0<V1> function0) {
            if (BoxesRunTime.equals(key, this.key1)) {
                return (V1)this.value1;
            }
            return function0.apply();
        }

        @Override
        public Iterator<Tuple2<K, V>> iterator() {
            Tuple2<K, V> single_a = new Tuple2<K, V>(this.key1, this.value1);
            return new AbstractIterator<A>(single_a){
                private boolean consumed;
                private final Object a$1;

                public boolean hasNext() {
                    return !this.consumed;
                }

                public A next() {
                    if (this.consumed) {
                        return (A)Iterator$.scala$collection$Iterator$$_empty.next();
                    }
                    this.consumed = true;
                    return (A)this.a$1;
                }

                public Iterator<A> sliceIterator(int from, int until) {
                    if (this.consumed || from > 0 || until == 0) {
                        return Iterator$.scala$collection$Iterator$$_empty;
                    }
                    return this;
                }
                {
                    this.a$1 = a$1;
                    this.consumed = false;
                }
            };
        }

        @Override
        public <V1> SeqMap<K, V1> updated(K key, V1 value) {
            if (BoxesRunTime.equals(key, this.key1)) {
                return new SeqMap1<K, V1>(this.key1, value);
            }
            return new SeqMap2<K, V>(this.key1, this.value1, key, value);
        }

        @Override
        public SeqMap<K, V> removed(K key) {
            if (BoxesRunTime.equals(key, this.key1)) {
                return SeqMap$EmptySeqMap$.MODULE$;
            }
            return this;
        }

        @Override
        public <U> void foreach(Function1<Tuple2<K, V>, U> f) {
            f.apply(new Tuple2<K, V>(this.key1, this.value1));
        }

        @Override
        public <U> void foreachEntry(Function2<K, V, U> f) {
            f.apply(this.key1, this.value1);
        }

        public SeqMap1(K key1, V value1) {
            this.key1 = key1;
            this.value1 = value1;
        }
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class SeqMap2<K, V>
    implements SeqMap<K, V>,
    Serializable {
        private static final long serialVersionUID = 3L;
        private final K key1;
        private final V value1;
        private final K key2;
        private final V value2;

        @Override
        public MapFactory<SeqMap> mapFactory() {
            return ((SeqMap)this).mapFactory();
        }

        @Override
        public String stringPrefix() {
            return scala.collection.SeqMap.stringPrefix$(this);
        }

        @Override
        public final <K2, V2> scala.collection.immutable.Map<K2, V2> toMap($less$colon$less<Tuple2<K, V>, Tuple2<K2, V2>> ev) {
            return scala.collection.immutable.Map.toMap$(this, ev);
        }

        @Override
        public <V1> scala.collection.immutable.Map<K, V1> withDefault(Function1<K, V1> d) {
            return scala.collection.immutable.Map.withDefault$(this, d);
        }

        @Override
        public <V1> scala.collection.immutable.Map<K, V1> withDefaultValue(V1 d) {
            return scala.collection.immutable.Map.withDefaultValue$(this, d);
        }

        @Override
        public final scala.collection.immutable.MapOps $minus(Object key) {
            return scala.collection.immutable.MapOps.$minus$(this, key);
        }

        @Override
        public scala.collection.immutable.MapOps $minus(Object key1, Object key2, Seq keys2) {
            return scala.collection.immutable.MapOps.$minus$(this, key1, key2, keys2);
        }

        @Override
        public scala.collection.immutable.MapOps removedAll(IterableOnce keys2) {
            return scala.collection.immutable.MapOps.removedAll$(this, keys2);
        }

        @Override
        public final scala.collection.immutable.MapOps $minus$minus(IterableOnce keys2) {
            return scala.collection.immutable.MapOps.$minus$minus$(this, keys2);
        }

        @Override
        public scala.collection.immutable.MapOps updatedWith(Object key, Function1 remappingFunction) {
            return scala.collection.immutable.MapOps.updatedWith$(this, key, remappingFunction);
        }

        @Override
        public scala.collection.immutable.MapOps $plus(Tuple2 kv) {
            return scala.collection.immutable.MapOps.$plus$(this, kv);
        }

        @Override
        public scala.collection.immutable.MapOps transform(Function2 f) {
            return scala.collection.immutable.MapOps.transform$(this, f);
        }

        @Override
        public Set<K> keySet() {
            return scala.collection.immutable.MapOps.keySet$(this);
        }

        @Override
        public boolean canEqual(Object that) {
            return Map.canEqual$(this, that);
        }

        @Override
        public boolean equals(Object o) {
            return Map.equals$(this, o);
        }

        @Override
        public int hashCode() {
            return Map.hashCode$(this);
        }

        @Override
        public String toString() {
            return Map.toString$(this);
        }

        @Override
        public IterableOps fromSpecific(IterableOnce coll) {
            return MapFactoryDefaults.fromSpecific$(this, coll);
        }

        @Override
        public Builder<Tuple2<K, V>, SeqMap<K, V>> newSpecificBuilder() {
            return MapFactoryDefaults.newSpecificBuilder$(this);
        }

        @Override
        public IterableOps empty() {
            return MapFactoryDefaults.empty$(this);
        }

        @Override
        public MapOps.WithFilter<K, V, ?, ?> withFilter(Function1<Tuple2<K, V>, Object> p) {
            return MapFactoryDefaults.withFilter$(this, p);
        }

        @Override
        public MapView<K, V> view() {
            return MapOps.view$(this);
        }

        @Override
        public <S extends Stepper<?>> S keyStepper(StepperShape<K, S> shape) {
            return (S)MapOps.keyStepper$(this, shape);
        }

        @Override
        public <S extends Stepper<?>> S valueStepper(StepperShape<V, S> shape) {
            return (S)MapOps.valueStepper$(this, shape);
        }

        @Override
        public final IterableOps mapFromIterable(Iterable it) {
            return MapOps.mapFromIterable$(this, it);
        }

        @Override
        public <K1 extends K, V1> V1 applyOrElse(K1 x, Function1<K1, V1> function1) {
            return (V1)MapOps.applyOrElse$(this, x, function1);
        }

        @Override
        public Iterable<K> keys() {
            return MapOps.keys$(this);
        }

        @Override
        public Iterable<V> values() {
            return MapOps.values$(this);
        }

        @Override
        public Iterator<K> keysIterator() {
            return MapOps.keysIterator$(this);
        }

        @Override
        public Iterator<V> valuesIterator() {
            return MapOps.valuesIterator$(this);
        }

        @Override
        public MapView<K, V> filterKeys(Function1<K, Object> p) {
            return MapOps.filterKeys$(this, p);
        }

        @Override
        public <W> MapView<K, W> mapValues(Function1<V, W> f) {
            return MapOps.mapValues$(this, f);
        }

        @Override
        public V default(K key) throws NoSuchElementException {
            return (V)MapOps.default$(this, key);
        }

        @Override
        public boolean isDefinedAt(K key) {
            return MapOps.isDefinedAt$(this, key);
        }

        @Override
        public IterableOps map(Function1 f) {
            return MapOps.map$(this, f);
        }

        @Override
        public IterableOps collect(PartialFunction pf) {
            return MapOps.collect$(this, pf);
        }

        @Override
        public IterableOps flatMap(Function1 f) {
            return MapOps.flatMap$(this, f);
        }

        @Override
        public IterableOps concat(IterableOnce suffix) {
            return MapOps.concat$(this, suffix);
        }

        @Override
        public IterableOps $plus$plus(IterableOnce xs) {
            return MapOps.$plus$plus$(this, xs);
        }

        @Override
        public StringBuilder addString(StringBuilder sb, String start2, String sep, String end) {
            return MapOps.addString$(this, sb, start2, sep, end);
        }

        @Override
        public IterableOps $plus(Tuple2 elem1, Tuple2 elem2, Seq elems) {
            return MapOps.$plus$(this, elem1, elem2, elems);
        }

        @Override
        public IterableOps $plus$plus$colon(IterableOnce that) {
            return MapOps.$plus$plus$colon$(this, that);
        }

        @Override
        public Option<V> unapply(K a) {
            return PartialFunction.unapply$(this, a);
        }

        @Override
        public PartialFunction<K, V> elementWise() {
            return PartialFunction.elementWise$(this);
        }

        @Override
        public <A1 extends K, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
            return PartialFunction.orElse$(this, that);
        }

        @Override
        public <C$> PartialFunction<K, C$> andThen(Function1<V, C$> k) {
            return PartialFunction.andThen$((PartialFunction)this, k);
        }

        @Override
        public <C$> PartialFunction<K, C$> andThen(PartialFunction<V, C$> k) {
            return PartialFunction.andThen$((PartialFunction)this, k);
        }

        @Override
        public <R$> PartialFunction<R$, V> compose(PartialFunction<R$, K> k) {
            return PartialFunction.compose$(this, k);
        }

        @Override
        public Function1<K, Option<V>> lift() {
            return PartialFunction.lift$(this);
        }

        @Override
        public <U> Function1<K, Object> runWith(Function1<V, U> action) {
            return PartialFunction.runWith$(this, action);
        }

        @Override
        public boolean apply$mcZD$sp(double v1) {
            return Function1.apply$mcZD$sp$(this, v1);
        }

        @Override
        public double apply$mcDD$sp(double v1) {
            return Function1.apply$mcDD$sp$(this, v1);
        }

        @Override
        public float apply$mcFD$sp(double v1) {
            return Function1.apply$mcFD$sp$(this, v1);
        }

        @Override
        public int apply$mcID$sp(double v1) {
            return Function1.apply$mcID$sp$(this, v1);
        }

        @Override
        public long apply$mcJD$sp(double v1) {
            return Function1.apply$mcJD$sp$(this, v1);
        }

        @Override
        public void apply$mcVD$sp(double v1) {
            Function1.apply$mcVD$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZF$sp(float v1) {
            return Function1.apply$mcZF$sp$(this, v1);
        }

        @Override
        public double apply$mcDF$sp(float v1) {
            return Function1.apply$mcDF$sp$(this, v1);
        }

        @Override
        public float apply$mcFF$sp(float v1) {
            return Function1.apply$mcFF$sp$(this, v1);
        }

        @Override
        public int apply$mcIF$sp(float v1) {
            return Function1.apply$mcIF$sp$(this, v1);
        }

        @Override
        public long apply$mcJF$sp(float v1) {
            return Function1.apply$mcJF$sp$(this, v1);
        }

        @Override
        public void apply$mcVF$sp(float v1) {
            Function1.apply$mcVF$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZI$sp(int v1) {
            return Function1.apply$mcZI$sp$(this, v1);
        }

        @Override
        public double apply$mcDI$sp(int v1) {
            return Function1.apply$mcDI$sp$(this, v1);
        }

        @Override
        public float apply$mcFI$sp(int v1) {
            return Function1.apply$mcFI$sp$(this, v1);
        }

        @Override
        public int apply$mcII$sp(int v1) {
            return Function1.apply$mcII$sp$(this, v1);
        }

        @Override
        public long apply$mcJI$sp(int v1) {
            return Function1.apply$mcJI$sp$(this, v1);
        }

        @Override
        public void apply$mcVI$sp(int v1) {
            Function1.apply$mcVI$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZJ$sp(long v1) {
            return Function1.apply$mcZJ$sp$(this, v1);
        }

        @Override
        public double apply$mcDJ$sp(long v1) {
            return Function1.apply$mcDJ$sp$(this, v1);
        }

        @Override
        public float apply$mcFJ$sp(long v1) {
            return Function1.apply$mcFJ$sp$(this, v1);
        }

        @Override
        public int apply$mcIJ$sp(long v1) {
            return Function1.apply$mcIJ$sp$(this, v1);
        }

        @Override
        public long apply$mcJJ$sp(long v1) {
            return Function1.apply$mcJJ$sp$(this, v1);
        }

        @Override
        public void apply$mcVJ$sp(long v1) {
            Function1.apply$mcVJ$sp$(this, v1);
        }

        @Override
        public <A$> Function1<A$, V> compose(Function1<A$, K> g2) {
            return Function1.compose$(this, g2);
        }

        @Override
        public IterableFactory<scala.collection.immutable.Iterable> iterableFactory() {
            return scala.collection.immutable.Iterable.iterableFactory$(this);
        }

        @Override
        public final Iterable<Tuple2<K, V>> toIterable() {
            return Iterable.toIterable$(this);
        }

        @Override
        public final Iterable<Tuple2<K, V>> coll() {
            return Iterable.coll$(this);
        }

        @Override
        public Iterable<Tuple2<K, V>> seq() {
            return Iterable.seq$(this);
        }

        @Override
        public String className() {
            return Iterable.className$(this);
        }

        @Override
        public final String collectionClassName() {
            return Iterable.collectionClassName$(this);
        }

        @Override
        public <B> LazyZip2<Tuple2<K, V>, B, Iterable> lazyZip(Iterable<B> that) {
            return Iterable.lazyZip$(this, that);
        }

        @Override
        public final Iterable<Tuple2<K, V>> toTraversable() {
            return IterableOps.toTraversable$(this);
        }

        @Override
        public boolean isTraversableAgain() {
            return IterableOps.isTraversableAgain$(this);
        }

        @Override
        public final Object repr() {
            return IterableOps.repr$(this);
        }

        @Override
        public IterableFactory<?> companion() {
            return IterableOps.companion$(this);
        }

        @Override
        public Object head() {
            return IterableOps.head$(this);
        }

        @Override
        public Option<Tuple2<K, V>> headOption() {
            return IterableOps.headOption$(this);
        }

        @Override
        public Object last() {
            return IterableOps.last$(this);
        }

        @Override
        public Option<Tuple2<K, V>> lastOption() {
            return IterableOps.lastOption$(this);
        }

        @Override
        public int sizeCompare(int otherSize) {
            return IterableOps.sizeCompare$((IterableOps)this, otherSize);
        }

        @Override
        public final IterableOps sizeIs() {
            return IterableOps.sizeIs$(this);
        }

        @Override
        public int sizeCompare(Iterable<?> that) {
            return IterableOps.sizeCompare$((IterableOps)this, that);
        }

        @Override
        public View<Tuple2<K, V>> view(int from, int until) {
            return IterableOps.view$(this, from, until);
        }

        @Override
        public Object transpose(Function1 asIterable) {
            return IterableOps.transpose$(this, asIterable);
        }

        @Override
        public Object filter(Function1 pred) {
            return IterableOps.filter$(this, pred);
        }

        @Override
        public Object filterNot(Function1 pred) {
            return IterableOps.filterNot$(this, pred);
        }

        @Override
        public Tuple2<SeqMap<K, V>, SeqMap<K, V>> partition(Function1<Tuple2<K, V>, Object> p) {
            return IterableOps.partition$(this, p);
        }

        @Override
        public Tuple2<SeqMap<K, V>, SeqMap<K, V>> splitAt(int n) {
            return IterableOps.splitAt$(this, n);
        }

        @Override
        public Object take(int n) {
            return IterableOps.take$(this, n);
        }

        @Override
        public Object takeRight(int n) {
            return IterableOps.takeRight$(this, n);
        }

        @Override
        public Object takeWhile(Function1 p) {
            return IterableOps.takeWhile$(this, p);
        }

        @Override
        public Tuple2<SeqMap<K, V>, SeqMap<K, V>> span(Function1<Tuple2<K, V>, Object> p) {
            return IterableOps.span$(this, p);
        }

        @Override
        public Object drop(int n) {
            return IterableOps.drop$(this, n);
        }

        @Override
        public Object dropRight(int n) {
            return IterableOps.dropRight$(this, n);
        }

        @Override
        public Object dropWhile(Function1 p) {
            return IterableOps.dropWhile$(this, p);
        }

        @Override
        public Iterator<SeqMap<K, V>> grouped(int size) {
            return IterableOps.grouped$(this, size);
        }

        @Override
        public Iterator<SeqMap<K, V>> sliding(int size) {
            return IterableOps.sliding$(this, size);
        }

        @Override
        public Iterator<SeqMap<K, V>> sliding(int size, int step) {
            return IterableOps.sliding$(this, size, step);
        }

        @Override
        public Object tail() {
            return IterableOps.tail$(this);
        }

        @Override
        public Object init() {
            return IterableOps.init$(this);
        }

        @Override
        public Object slice(int from, int until) {
            return IterableOps.slice$(this, from, until);
        }

        @Override
        public <K$> scala.collection.immutable.Map<K$, SeqMap<K, V>> groupBy(Function1<Tuple2<K, V>, K$> f) {
            return IterableOps.groupBy$(this, f);
        }

        @Override
        public <K$, B> scala.collection.immutable.Map<K$, scala.collection.immutable.Iterable<B>> groupMap(Function1<Tuple2<K, V>, K$> key, Function1<Tuple2<K, V>, B> f) {
            return IterableOps.groupMap$(this, key, f);
        }

        @Override
        public <K$, B> scala.collection.immutable.Map<K$, B> groupMapReduce(Function1<Tuple2<K, V>, K$> key, Function1<Tuple2<K, V>, B> f, Function2<B, B, B> reduce) {
            return IterableOps.groupMapReduce$(this, key, f, reduce);
        }

        @Override
        public Object scan(Object z, Function2 op) {
            return IterableOps.scan$(this, z, op);
        }

        @Override
        public Object scanLeft(Object z, Function2 op) {
            return IterableOps.scanLeft$(this, z, op);
        }

        @Override
        public Object scanRight(Object z, Function2 op) {
            return IterableOps.scanRight$(this, z, op);
        }

        @Override
        public Object map(Function1 f) {
            return IterableOps.map$(this, f);
        }

        @Override
        public Object flatMap(Function1 f) {
            return IterableOps.flatMap$(this, f);
        }

        @Override
        public Object flatten(Function1 asIterable) {
            return IterableOps.flatten$(this, asIterable);
        }

        @Override
        public Object collect(PartialFunction pf) {
            return IterableOps.collect$(this, pf);
        }

        @Override
        public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> partitionMap(Function1<Tuple2<K, V>, Either<A1, A2>> f) {
            return IterableOps.partitionMap$(this, f);
        }

        @Override
        public Object concat(IterableOnce suffix) {
            return IterableOps.concat$(this, suffix);
        }

        @Override
        public final Object $plus$plus(IterableOnce suffix) {
            return IterableOps.$plus$plus$(this, suffix);
        }

        @Override
        public Object zip(IterableOnce that) {
            return IterableOps.zip$(this, that);
        }

        @Override
        public Object zipWithIndex() {
            return IterableOps.zipWithIndex$(this);
        }

        @Override
        public Object zipAll(Iterable that, Object thisElem, Object thatElem) {
            return IterableOps.zipAll$(this, that, thisElem, thatElem);
        }

        @Override
        public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> unzip(Function1<Tuple2<K, V>, Tuple2<A1, A2>> asPair) {
            return IterableOps.unzip$(this, asPair);
        }

        @Override
        public <A1, A2, A3> Tuple3<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>, scala.collection.immutable.Iterable<A3>> unzip3(Function1<Tuple2<K, V>, Tuple3<A1, A2, A3>> asTriple) {
            return IterableOps.unzip3$(this, asTriple);
        }

        @Override
        public Iterator<SeqMap<K, V>> tails() {
            return IterableOps.tails$(this);
        }

        @Override
        public Iterator<SeqMap<K, V>> inits() {
            return IterableOps.inits$(this);
        }

        @Override
        public Object tapEach(Function1 f) {
            return IterableOps.tapEach$(this, f);
        }

        @Override
        public Object $plus$plus$colon(IterableOnce that) {
            return IterableOps.$plus$plus$colon$(this, that);
        }

        @Override
        public boolean hasDefiniteSize() {
            return IterableOnceOps.hasDefiniteSize$(this);
        }

        @Override
        public boolean forall(Function1<Tuple2<K, V>, Object> p) {
            return IterableOnceOps.forall$(this, p);
        }

        @Override
        public boolean exists(Function1<Tuple2<K, V>, Object> p) {
            return IterableOnceOps.exists$(this, p);
        }

        @Override
        public int count(Function1<Tuple2<K, V>, Object> p) {
            return IterableOnceOps.count$(this, p);
        }

        @Override
        public Option<Tuple2<K, V>> find(Function1<Tuple2<K, V>, Object> p) {
            return IterableOnceOps.find$(this, p);
        }

        @Override
        public <B> B foldLeft(B z, Function2<B, Tuple2<K, V>, B> op) {
            return (B)IterableOnceOps.foldLeft$(this, z, op);
        }

        @Override
        public <B> B foldRight(B z, Function2<Tuple2<K, V>, B, B> op) {
            return (B)IterableOnceOps.foldRight$(this, z, op);
        }

        @Override
        public final <B> B $div$colon(B z, Function2<B, Tuple2<K, V>, B> op) {
            return (B)IterableOnceOps.$div$colon$(this, z, op);
        }

        @Override
        public final <B> B $colon$bslash(B z, Function2<Tuple2<K, V>, B, B> op) {
            return (B)IterableOnceOps.$colon$bslash$(this, z, op);
        }

        @Override
        public <A1> A1 fold(A1 z, Function2<A1, A1, A1> op) {
            return (A1)IterableOnceOps.fold$(this, z, op);
        }

        @Override
        public <B> B reduce(Function2<B, B, B> op) {
            return (B)IterableOnceOps.reduce$(this, op);
        }

        @Override
        public <B> Option<B> reduceOption(Function2<B, B, B> op) {
            return IterableOnceOps.reduceOption$(this, op);
        }

        @Override
        public <B> B reduceLeft(Function2<B, Tuple2<K, V>, B> op) {
            return (B)IterableOnceOps.reduceLeft$(this, op);
        }

        @Override
        public <B> B reduceRight(Function2<Tuple2<K, V>, B, B> op) {
            return (B)IterableOnceOps.reduceRight$(this, op);
        }

        @Override
        public <B> Option<B> reduceLeftOption(Function2<B, Tuple2<K, V>, B> op) {
            return IterableOnceOps.reduceLeftOption$(this, op);
        }

        @Override
        public <B> Option<B> reduceRightOption(Function2<Tuple2<K, V>, B, B> op) {
            return IterableOnceOps.reduceRightOption$(this, op);
        }

        @Override
        public boolean isEmpty() {
            return IterableOnceOps.isEmpty$(this);
        }

        @Override
        public boolean nonEmpty() {
            return IterableOnceOps.nonEmpty$(this);
        }

        @Override
        public final <B> void copyToBuffer(Buffer<B> dest) {
            IterableOnceOps.copyToBuffer$(this, dest);
        }

        @Override
        public <B> int copyToArray(Object xs) {
            return IterableOnceOps.copyToArray$(this, xs);
        }

        @Override
        public <B> int copyToArray(Object xs, int start2) {
            return IterableOnceOps.copyToArray$(this, xs, start2);
        }

        @Override
        public <B> int copyToArray(Object xs, int start2, int len) {
            return IterableOnceOps.copyToArray$(this, xs, start2, len);
        }

        @Override
        public <B> B sum(Numeric<B> num) {
            return (B)IterableOnceOps.sum$(this, num);
        }

        @Override
        public <B> B product(Numeric<B> num) {
            return (B)IterableOnceOps.product$(this, num);
        }

        @Override
        public Object min(Ordering ord) {
            return IterableOnceOps.min$(this, ord);
        }

        @Override
        public <B> Option<Tuple2<K, V>> minOption(Ordering<B> ord) {
            return IterableOnceOps.minOption$(this, ord);
        }

        @Override
        public Object max(Ordering ord) {
            return IterableOnceOps.max$(this, ord);
        }

        @Override
        public <B> Option<Tuple2<K, V>> maxOption(Ordering<B> ord) {
            return IterableOnceOps.maxOption$(this, ord);
        }

        @Override
        public Object maxBy(Function1 f, Ordering cmp) {
            return IterableOnceOps.maxBy$(this, f, cmp);
        }

        @Override
        public <B> Option<Tuple2<K, V>> maxByOption(Function1<Tuple2<K, V>, B> f, Ordering<B> cmp) {
            return IterableOnceOps.maxByOption$(this, f, cmp);
        }

        @Override
        public Object minBy(Function1 f, Ordering cmp) {
            return IterableOnceOps.minBy$(this, f, cmp);
        }

        @Override
        public <B> Option<Tuple2<K, V>> minByOption(Function1<Tuple2<K, V>, B> f, Ordering<B> cmp) {
            return IterableOnceOps.minByOption$(this, f, cmp);
        }

        @Override
        public <B> Option<B> collectFirst(PartialFunction<Tuple2<K, V>, B> pf) {
            return IterableOnceOps.collectFirst$(this, pf);
        }

        @Override
        public <B> B aggregate(Function0<B> z, Function2<B, Tuple2<K, V>, B> seqop, Function2<B, B, B> combop) {
            return (B)IterableOnceOps.aggregate$(this, z, seqop, combop);
        }

        @Override
        public <B> boolean corresponds(IterableOnce<B> that, Function2<Tuple2<K, V>, B, Object> p) {
            return IterableOnceOps.corresponds$(this, that, p);
        }

        @Override
        public final String mkString(String start2, String sep, String end) {
            return IterableOnceOps.mkString$(this, start2, sep, end);
        }

        @Override
        public final String mkString(String sep) {
            return IterableOnceOps.mkString$(this, sep);
        }

        @Override
        public final String mkString() {
            return IterableOnceOps.mkString$(this);
        }

        @Override
        public final StringBuilder addString(StringBuilder b, String sep) {
            return IterableOnceOps.addString$(this, b, sep);
        }

        @Override
        public final StringBuilder addString(StringBuilder b) {
            return IterableOnceOps.addString$(this, b);
        }

        @Override
        public <C1> C1 to(Factory<Tuple2<K, V>, C1> factory2) {
            return (C1)IterableOnceOps.to$(this, factory2);
        }

        @Override
        public final Iterator<Tuple2<K, V>> toIterator() {
            return IterableOnceOps.toIterator$(this);
        }

        @Override
        public List<Tuple2<K, V>> toList() {
            return IterableOnceOps.toList$(this);
        }

        @Override
        public Vector<Tuple2<K, V>> toVector() {
            return IterableOnceOps.toVector$(this);
        }

        @Override
        public <B> Set<B> toSet() {
            return IterableOnceOps.toSet$(this);
        }

        @Override
        public Seq<Tuple2<K, V>> toSeq() {
            return IterableOnceOps.toSeq$(this);
        }

        @Override
        public IndexedSeq<Tuple2<K, V>> toIndexedSeq() {
            return IterableOnceOps.toIndexedSeq$(this);
        }

        @Override
        public final Stream<Tuple2<K, V>> toStream() {
            return IterableOnceOps.toStream$(this);
        }

        @Override
        public final <B> Buffer<B> toBuffer() {
            return IterableOnceOps.toBuffer$(this);
        }

        @Override
        public <B> Object toArray(ClassTag<B> evidence$2) {
            return IterableOnceOps.toArray$(this, evidence$2);
        }

        @Override
        public Iterable<Tuple2<K, V>> reversed() {
            return IterableOnceOps.reversed$(this);
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<Tuple2<K, V>, S> shape) {
            return (S)IterableOnce.stepper$(this, shape);
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public int knownSize() {
            return 2;
        }

        @Override
        public V apply(K key) {
            if (BoxesRunTime.equals(key, this.key1)) {
                return this.value1;
            }
            if (BoxesRunTime.equals(key, this.key2)) {
                return this.value2;
            }
            throw new NoSuchElementException(new java.lang.StringBuilder(15).append("key not found: ").append(key).toString());
        }

        @Override
        public boolean contains(K key) {
            return BoxesRunTime.equals(key, this.key1) || BoxesRunTime.equals(key, this.key2);
        }

        @Override
        public Option<V> get(K key) {
            if (BoxesRunTime.equals(key, this.key1)) {
                return new Some<V>(this.value1);
            }
            if (BoxesRunTime.equals(key, this.key2)) {
                return new Some<V>(this.value2);
            }
            return None$.MODULE$;
        }

        @Override
        public <V1> V1 getOrElse(K key, Function0<V1> function0) {
            if (BoxesRunTime.equals(key, this.key1)) {
                return (V1)this.value1;
            }
            if (BoxesRunTime.equals(key, this.key2)) {
                return (V1)this.value2;
            }
            return function0.apply();
        }

        @Override
        public Iterator<Tuple2<K, V>> iterator() {
            Tuple2<K, V> tuple2 = new Tuple2<K, V>(this.key1, this.value1);
            Tuple2<K, V> tuple22 = new Tuple2<K, V>(this.key2, this.value2);
            Nil$ $colon$colon_this = Nil$.MODULE$;
            Object var3_3 = null;
            $colon$colon<Nothing$> $colon$colon_this2 = new $colon$colon<Nothing$>((Nothing$)((Object)tuple22), $colon$colon_this);
            Object var4_4 = null;
            return new $colon$colon<Nothing$>((Nothing$)((Object)tuple2), (List<Nothing$>)$colon$colon_this2).iterator();
        }

        @Override
        public <V1> SeqMap<K, V1> updated(K key, V1 value) {
            if (BoxesRunTime.equals(key, this.key1)) {
                return new SeqMap2<K, V1>(this.key1, value, this.key2, this.value2);
            }
            if (BoxesRunTime.equals(key, this.key2)) {
                return new SeqMap2<K, V>(this.key1, this.value1, this.key2, value);
            }
            return new SeqMap3<K, V>(this.key1, this.value1, this.key2, this.value2, key, value);
        }

        @Override
        public SeqMap<K, V> removed(K key) {
            if (BoxesRunTime.equals(key, this.key1)) {
                return new SeqMap1<K, V>(this.key2, this.value2);
            }
            if (BoxesRunTime.equals(key, this.key2)) {
                return new SeqMap1<K, V>(this.key1, this.value1);
            }
            return this;
        }

        @Override
        public <U> void foreach(Function1<Tuple2<K, V>, U> f) {
            f.apply(new Tuple2<K, V>(this.key1, this.value1));
            f.apply(new Tuple2<K, V>(this.key2, this.value2));
        }

        @Override
        public <U> void foreachEntry(Function2<K, V, U> f) {
            f.apply(this.key1, this.value1);
            f.apply(this.key2, this.value2);
        }

        public SeqMap2(K key1, V value1, K key2, V value2) {
            this.key1 = key1;
            this.value1 = value1;
            this.key2 = key2;
            this.value2 = value2;
        }
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class SeqMap3<K, V>
    implements SeqMap<K, V>,
    Serializable {
        private static final long serialVersionUID = 3L;
        private final K key1;
        private final V value1;
        private final K key2;
        private final V value2;
        private final K key3;
        private final V value3;

        @Override
        public MapFactory<SeqMap> mapFactory() {
            return ((SeqMap)this).mapFactory();
        }

        @Override
        public String stringPrefix() {
            return scala.collection.SeqMap.stringPrefix$(this);
        }

        @Override
        public final <K2, V2> scala.collection.immutable.Map<K2, V2> toMap($less$colon$less<Tuple2<K, V>, Tuple2<K2, V2>> ev) {
            return scala.collection.immutable.Map.toMap$(this, ev);
        }

        @Override
        public <V1> scala.collection.immutable.Map<K, V1> withDefault(Function1<K, V1> d) {
            return scala.collection.immutable.Map.withDefault$(this, d);
        }

        @Override
        public <V1> scala.collection.immutable.Map<K, V1> withDefaultValue(V1 d) {
            return scala.collection.immutable.Map.withDefaultValue$(this, d);
        }

        @Override
        public final scala.collection.immutable.MapOps $minus(Object key) {
            return scala.collection.immutable.MapOps.$minus$(this, key);
        }

        @Override
        public scala.collection.immutable.MapOps $minus(Object key1, Object key2, Seq keys2) {
            return scala.collection.immutable.MapOps.$minus$(this, key1, key2, keys2);
        }

        @Override
        public scala.collection.immutable.MapOps removedAll(IterableOnce keys2) {
            return scala.collection.immutable.MapOps.removedAll$(this, keys2);
        }

        @Override
        public final scala.collection.immutable.MapOps $minus$minus(IterableOnce keys2) {
            return scala.collection.immutable.MapOps.$minus$minus$(this, keys2);
        }

        @Override
        public scala.collection.immutable.MapOps updatedWith(Object key, Function1 remappingFunction) {
            return scala.collection.immutable.MapOps.updatedWith$(this, key, remappingFunction);
        }

        @Override
        public scala.collection.immutable.MapOps $plus(Tuple2 kv) {
            return scala.collection.immutable.MapOps.$plus$(this, kv);
        }

        @Override
        public scala.collection.immutable.MapOps transform(Function2 f) {
            return scala.collection.immutable.MapOps.transform$(this, f);
        }

        @Override
        public Set<K> keySet() {
            return scala.collection.immutable.MapOps.keySet$(this);
        }

        @Override
        public boolean canEqual(Object that) {
            return Map.canEqual$(this, that);
        }

        @Override
        public boolean equals(Object o) {
            return Map.equals$(this, o);
        }

        @Override
        public int hashCode() {
            return Map.hashCode$(this);
        }

        @Override
        public String toString() {
            return Map.toString$(this);
        }

        @Override
        public IterableOps fromSpecific(IterableOnce coll) {
            return MapFactoryDefaults.fromSpecific$(this, coll);
        }

        @Override
        public Builder<Tuple2<K, V>, SeqMap<K, V>> newSpecificBuilder() {
            return MapFactoryDefaults.newSpecificBuilder$(this);
        }

        @Override
        public IterableOps empty() {
            return MapFactoryDefaults.empty$(this);
        }

        @Override
        public MapOps.WithFilter<K, V, ?, ?> withFilter(Function1<Tuple2<K, V>, Object> p) {
            return MapFactoryDefaults.withFilter$(this, p);
        }

        @Override
        public MapView<K, V> view() {
            return MapOps.view$(this);
        }

        @Override
        public <S extends Stepper<?>> S keyStepper(StepperShape<K, S> shape) {
            return (S)MapOps.keyStepper$(this, shape);
        }

        @Override
        public <S extends Stepper<?>> S valueStepper(StepperShape<V, S> shape) {
            return (S)MapOps.valueStepper$(this, shape);
        }

        @Override
        public final IterableOps mapFromIterable(Iterable it) {
            return MapOps.mapFromIterable$(this, it);
        }

        @Override
        public <K1 extends K, V1> V1 applyOrElse(K1 x, Function1<K1, V1> function1) {
            return (V1)MapOps.applyOrElse$(this, x, function1);
        }

        @Override
        public Iterable<K> keys() {
            return MapOps.keys$(this);
        }

        @Override
        public Iterable<V> values() {
            return MapOps.values$(this);
        }

        @Override
        public Iterator<K> keysIterator() {
            return MapOps.keysIterator$(this);
        }

        @Override
        public Iterator<V> valuesIterator() {
            return MapOps.valuesIterator$(this);
        }

        @Override
        public MapView<K, V> filterKeys(Function1<K, Object> p) {
            return MapOps.filterKeys$(this, p);
        }

        @Override
        public <W> MapView<K, W> mapValues(Function1<V, W> f) {
            return MapOps.mapValues$(this, f);
        }

        @Override
        public V default(K key) throws NoSuchElementException {
            return (V)MapOps.default$(this, key);
        }

        @Override
        public boolean isDefinedAt(K key) {
            return MapOps.isDefinedAt$(this, key);
        }

        @Override
        public IterableOps map(Function1 f) {
            return MapOps.map$(this, f);
        }

        @Override
        public IterableOps collect(PartialFunction pf) {
            return MapOps.collect$(this, pf);
        }

        @Override
        public IterableOps flatMap(Function1 f) {
            return MapOps.flatMap$(this, f);
        }

        @Override
        public IterableOps concat(IterableOnce suffix) {
            return MapOps.concat$(this, suffix);
        }

        @Override
        public IterableOps $plus$plus(IterableOnce xs) {
            return MapOps.$plus$plus$(this, xs);
        }

        @Override
        public StringBuilder addString(StringBuilder sb, String start2, String sep, String end) {
            return MapOps.addString$(this, sb, start2, sep, end);
        }

        @Override
        public IterableOps $plus(Tuple2 elem1, Tuple2 elem2, Seq elems) {
            return MapOps.$plus$(this, elem1, elem2, elems);
        }

        @Override
        public IterableOps $plus$plus$colon(IterableOnce that) {
            return MapOps.$plus$plus$colon$(this, that);
        }

        @Override
        public Option<V> unapply(K a) {
            return PartialFunction.unapply$(this, a);
        }

        @Override
        public PartialFunction<K, V> elementWise() {
            return PartialFunction.elementWise$(this);
        }

        @Override
        public <A1 extends K, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
            return PartialFunction.orElse$(this, that);
        }

        @Override
        public <C$> PartialFunction<K, C$> andThen(Function1<V, C$> k) {
            return PartialFunction.andThen$((PartialFunction)this, k);
        }

        @Override
        public <C$> PartialFunction<K, C$> andThen(PartialFunction<V, C$> k) {
            return PartialFunction.andThen$((PartialFunction)this, k);
        }

        @Override
        public <R$> PartialFunction<R$, V> compose(PartialFunction<R$, K> k) {
            return PartialFunction.compose$(this, k);
        }

        @Override
        public Function1<K, Option<V>> lift() {
            return PartialFunction.lift$(this);
        }

        @Override
        public <U> Function1<K, Object> runWith(Function1<V, U> action) {
            return PartialFunction.runWith$(this, action);
        }

        @Override
        public boolean apply$mcZD$sp(double v1) {
            return Function1.apply$mcZD$sp$(this, v1);
        }

        @Override
        public double apply$mcDD$sp(double v1) {
            return Function1.apply$mcDD$sp$(this, v1);
        }

        @Override
        public float apply$mcFD$sp(double v1) {
            return Function1.apply$mcFD$sp$(this, v1);
        }

        @Override
        public int apply$mcID$sp(double v1) {
            return Function1.apply$mcID$sp$(this, v1);
        }

        @Override
        public long apply$mcJD$sp(double v1) {
            return Function1.apply$mcJD$sp$(this, v1);
        }

        @Override
        public void apply$mcVD$sp(double v1) {
            Function1.apply$mcVD$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZF$sp(float v1) {
            return Function1.apply$mcZF$sp$(this, v1);
        }

        @Override
        public double apply$mcDF$sp(float v1) {
            return Function1.apply$mcDF$sp$(this, v1);
        }

        @Override
        public float apply$mcFF$sp(float v1) {
            return Function1.apply$mcFF$sp$(this, v1);
        }

        @Override
        public int apply$mcIF$sp(float v1) {
            return Function1.apply$mcIF$sp$(this, v1);
        }

        @Override
        public long apply$mcJF$sp(float v1) {
            return Function1.apply$mcJF$sp$(this, v1);
        }

        @Override
        public void apply$mcVF$sp(float v1) {
            Function1.apply$mcVF$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZI$sp(int v1) {
            return Function1.apply$mcZI$sp$(this, v1);
        }

        @Override
        public double apply$mcDI$sp(int v1) {
            return Function1.apply$mcDI$sp$(this, v1);
        }

        @Override
        public float apply$mcFI$sp(int v1) {
            return Function1.apply$mcFI$sp$(this, v1);
        }

        @Override
        public int apply$mcII$sp(int v1) {
            return Function1.apply$mcII$sp$(this, v1);
        }

        @Override
        public long apply$mcJI$sp(int v1) {
            return Function1.apply$mcJI$sp$(this, v1);
        }

        @Override
        public void apply$mcVI$sp(int v1) {
            Function1.apply$mcVI$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZJ$sp(long v1) {
            return Function1.apply$mcZJ$sp$(this, v1);
        }

        @Override
        public double apply$mcDJ$sp(long v1) {
            return Function1.apply$mcDJ$sp$(this, v1);
        }

        @Override
        public float apply$mcFJ$sp(long v1) {
            return Function1.apply$mcFJ$sp$(this, v1);
        }

        @Override
        public int apply$mcIJ$sp(long v1) {
            return Function1.apply$mcIJ$sp$(this, v1);
        }

        @Override
        public long apply$mcJJ$sp(long v1) {
            return Function1.apply$mcJJ$sp$(this, v1);
        }

        @Override
        public void apply$mcVJ$sp(long v1) {
            Function1.apply$mcVJ$sp$(this, v1);
        }

        @Override
        public <A$> Function1<A$, V> compose(Function1<A$, K> g2) {
            return Function1.compose$(this, g2);
        }

        @Override
        public IterableFactory<scala.collection.immutable.Iterable> iterableFactory() {
            return scala.collection.immutable.Iterable.iterableFactory$(this);
        }

        @Override
        public final Iterable<Tuple2<K, V>> toIterable() {
            return Iterable.toIterable$(this);
        }

        @Override
        public final Iterable<Tuple2<K, V>> coll() {
            return Iterable.coll$(this);
        }

        @Override
        public Iterable<Tuple2<K, V>> seq() {
            return Iterable.seq$(this);
        }

        @Override
        public String className() {
            return Iterable.className$(this);
        }

        @Override
        public final String collectionClassName() {
            return Iterable.collectionClassName$(this);
        }

        @Override
        public <B> LazyZip2<Tuple2<K, V>, B, Iterable> lazyZip(Iterable<B> that) {
            return Iterable.lazyZip$(this, that);
        }

        @Override
        public final Iterable<Tuple2<K, V>> toTraversable() {
            return IterableOps.toTraversable$(this);
        }

        @Override
        public boolean isTraversableAgain() {
            return IterableOps.isTraversableAgain$(this);
        }

        @Override
        public final Object repr() {
            return IterableOps.repr$(this);
        }

        @Override
        public IterableFactory<?> companion() {
            return IterableOps.companion$(this);
        }

        @Override
        public Object head() {
            return IterableOps.head$(this);
        }

        @Override
        public Option<Tuple2<K, V>> headOption() {
            return IterableOps.headOption$(this);
        }

        @Override
        public Object last() {
            return IterableOps.last$(this);
        }

        @Override
        public Option<Tuple2<K, V>> lastOption() {
            return IterableOps.lastOption$(this);
        }

        @Override
        public int sizeCompare(int otherSize) {
            return IterableOps.sizeCompare$((IterableOps)this, otherSize);
        }

        @Override
        public final IterableOps sizeIs() {
            return IterableOps.sizeIs$(this);
        }

        @Override
        public int sizeCompare(Iterable<?> that) {
            return IterableOps.sizeCompare$((IterableOps)this, that);
        }

        @Override
        public View<Tuple2<K, V>> view(int from, int until) {
            return IterableOps.view$(this, from, until);
        }

        @Override
        public Object transpose(Function1 asIterable) {
            return IterableOps.transpose$(this, asIterable);
        }

        @Override
        public Object filter(Function1 pred) {
            return IterableOps.filter$(this, pred);
        }

        @Override
        public Object filterNot(Function1 pred) {
            return IterableOps.filterNot$(this, pred);
        }

        @Override
        public Tuple2<SeqMap<K, V>, SeqMap<K, V>> partition(Function1<Tuple2<K, V>, Object> p) {
            return IterableOps.partition$(this, p);
        }

        @Override
        public Tuple2<SeqMap<K, V>, SeqMap<K, V>> splitAt(int n) {
            return IterableOps.splitAt$(this, n);
        }

        @Override
        public Object take(int n) {
            return IterableOps.take$(this, n);
        }

        @Override
        public Object takeRight(int n) {
            return IterableOps.takeRight$(this, n);
        }

        @Override
        public Object takeWhile(Function1 p) {
            return IterableOps.takeWhile$(this, p);
        }

        @Override
        public Tuple2<SeqMap<K, V>, SeqMap<K, V>> span(Function1<Tuple2<K, V>, Object> p) {
            return IterableOps.span$(this, p);
        }

        @Override
        public Object drop(int n) {
            return IterableOps.drop$(this, n);
        }

        @Override
        public Object dropRight(int n) {
            return IterableOps.dropRight$(this, n);
        }

        @Override
        public Object dropWhile(Function1 p) {
            return IterableOps.dropWhile$(this, p);
        }

        @Override
        public Iterator<SeqMap<K, V>> grouped(int size) {
            return IterableOps.grouped$(this, size);
        }

        @Override
        public Iterator<SeqMap<K, V>> sliding(int size) {
            return IterableOps.sliding$(this, size);
        }

        @Override
        public Iterator<SeqMap<K, V>> sliding(int size, int step) {
            return IterableOps.sliding$(this, size, step);
        }

        @Override
        public Object tail() {
            return IterableOps.tail$(this);
        }

        @Override
        public Object init() {
            return IterableOps.init$(this);
        }

        @Override
        public Object slice(int from, int until) {
            return IterableOps.slice$(this, from, until);
        }

        @Override
        public <K$> scala.collection.immutable.Map<K$, SeqMap<K, V>> groupBy(Function1<Tuple2<K, V>, K$> f) {
            return IterableOps.groupBy$(this, f);
        }

        @Override
        public <K$, B> scala.collection.immutable.Map<K$, scala.collection.immutable.Iterable<B>> groupMap(Function1<Tuple2<K, V>, K$> key, Function1<Tuple2<K, V>, B> f) {
            return IterableOps.groupMap$(this, key, f);
        }

        @Override
        public <K$, B> scala.collection.immutable.Map<K$, B> groupMapReduce(Function1<Tuple2<K, V>, K$> key, Function1<Tuple2<K, V>, B> f, Function2<B, B, B> reduce) {
            return IterableOps.groupMapReduce$(this, key, f, reduce);
        }

        @Override
        public Object scan(Object z, Function2 op) {
            return IterableOps.scan$(this, z, op);
        }

        @Override
        public Object scanLeft(Object z, Function2 op) {
            return IterableOps.scanLeft$(this, z, op);
        }

        @Override
        public Object scanRight(Object z, Function2 op) {
            return IterableOps.scanRight$(this, z, op);
        }

        @Override
        public Object map(Function1 f) {
            return IterableOps.map$(this, f);
        }

        @Override
        public Object flatMap(Function1 f) {
            return IterableOps.flatMap$(this, f);
        }

        @Override
        public Object flatten(Function1 asIterable) {
            return IterableOps.flatten$(this, asIterable);
        }

        @Override
        public Object collect(PartialFunction pf) {
            return IterableOps.collect$(this, pf);
        }

        @Override
        public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> partitionMap(Function1<Tuple2<K, V>, Either<A1, A2>> f) {
            return IterableOps.partitionMap$(this, f);
        }

        @Override
        public Object concat(IterableOnce suffix) {
            return IterableOps.concat$(this, suffix);
        }

        @Override
        public final Object $plus$plus(IterableOnce suffix) {
            return IterableOps.$plus$plus$(this, suffix);
        }

        @Override
        public Object zip(IterableOnce that) {
            return IterableOps.zip$(this, that);
        }

        @Override
        public Object zipWithIndex() {
            return IterableOps.zipWithIndex$(this);
        }

        @Override
        public Object zipAll(Iterable that, Object thisElem, Object thatElem) {
            return IterableOps.zipAll$(this, that, thisElem, thatElem);
        }

        @Override
        public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> unzip(Function1<Tuple2<K, V>, Tuple2<A1, A2>> asPair) {
            return IterableOps.unzip$(this, asPair);
        }

        @Override
        public <A1, A2, A3> Tuple3<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>, scala.collection.immutable.Iterable<A3>> unzip3(Function1<Tuple2<K, V>, Tuple3<A1, A2, A3>> asTriple) {
            return IterableOps.unzip3$(this, asTriple);
        }

        @Override
        public Iterator<SeqMap<K, V>> tails() {
            return IterableOps.tails$(this);
        }

        @Override
        public Iterator<SeqMap<K, V>> inits() {
            return IterableOps.inits$(this);
        }

        @Override
        public Object tapEach(Function1 f) {
            return IterableOps.tapEach$(this, f);
        }

        @Override
        public Object $plus$plus$colon(IterableOnce that) {
            return IterableOps.$plus$plus$colon$(this, that);
        }

        @Override
        public boolean hasDefiniteSize() {
            return IterableOnceOps.hasDefiniteSize$(this);
        }

        @Override
        public boolean forall(Function1<Tuple2<K, V>, Object> p) {
            return IterableOnceOps.forall$(this, p);
        }

        @Override
        public boolean exists(Function1<Tuple2<K, V>, Object> p) {
            return IterableOnceOps.exists$(this, p);
        }

        @Override
        public int count(Function1<Tuple2<K, V>, Object> p) {
            return IterableOnceOps.count$(this, p);
        }

        @Override
        public Option<Tuple2<K, V>> find(Function1<Tuple2<K, V>, Object> p) {
            return IterableOnceOps.find$(this, p);
        }

        @Override
        public <B> B foldLeft(B z, Function2<B, Tuple2<K, V>, B> op) {
            return (B)IterableOnceOps.foldLeft$(this, z, op);
        }

        @Override
        public <B> B foldRight(B z, Function2<Tuple2<K, V>, B, B> op) {
            return (B)IterableOnceOps.foldRight$(this, z, op);
        }

        @Override
        public final <B> B $div$colon(B z, Function2<B, Tuple2<K, V>, B> op) {
            return (B)IterableOnceOps.$div$colon$(this, z, op);
        }

        @Override
        public final <B> B $colon$bslash(B z, Function2<Tuple2<K, V>, B, B> op) {
            return (B)IterableOnceOps.$colon$bslash$(this, z, op);
        }

        @Override
        public <A1> A1 fold(A1 z, Function2<A1, A1, A1> op) {
            return (A1)IterableOnceOps.fold$(this, z, op);
        }

        @Override
        public <B> B reduce(Function2<B, B, B> op) {
            return (B)IterableOnceOps.reduce$(this, op);
        }

        @Override
        public <B> Option<B> reduceOption(Function2<B, B, B> op) {
            return IterableOnceOps.reduceOption$(this, op);
        }

        @Override
        public <B> B reduceLeft(Function2<B, Tuple2<K, V>, B> op) {
            return (B)IterableOnceOps.reduceLeft$(this, op);
        }

        @Override
        public <B> B reduceRight(Function2<Tuple2<K, V>, B, B> op) {
            return (B)IterableOnceOps.reduceRight$(this, op);
        }

        @Override
        public <B> Option<B> reduceLeftOption(Function2<B, Tuple2<K, V>, B> op) {
            return IterableOnceOps.reduceLeftOption$(this, op);
        }

        @Override
        public <B> Option<B> reduceRightOption(Function2<Tuple2<K, V>, B, B> op) {
            return IterableOnceOps.reduceRightOption$(this, op);
        }

        @Override
        public boolean isEmpty() {
            return IterableOnceOps.isEmpty$(this);
        }

        @Override
        public boolean nonEmpty() {
            return IterableOnceOps.nonEmpty$(this);
        }

        @Override
        public final <B> void copyToBuffer(Buffer<B> dest) {
            IterableOnceOps.copyToBuffer$(this, dest);
        }

        @Override
        public <B> int copyToArray(Object xs) {
            return IterableOnceOps.copyToArray$(this, xs);
        }

        @Override
        public <B> int copyToArray(Object xs, int start2) {
            return IterableOnceOps.copyToArray$(this, xs, start2);
        }

        @Override
        public <B> int copyToArray(Object xs, int start2, int len) {
            return IterableOnceOps.copyToArray$(this, xs, start2, len);
        }

        @Override
        public <B> B sum(Numeric<B> num) {
            return (B)IterableOnceOps.sum$(this, num);
        }

        @Override
        public <B> B product(Numeric<B> num) {
            return (B)IterableOnceOps.product$(this, num);
        }

        @Override
        public Object min(Ordering ord) {
            return IterableOnceOps.min$(this, ord);
        }

        @Override
        public <B> Option<Tuple2<K, V>> minOption(Ordering<B> ord) {
            return IterableOnceOps.minOption$(this, ord);
        }

        @Override
        public Object max(Ordering ord) {
            return IterableOnceOps.max$(this, ord);
        }

        @Override
        public <B> Option<Tuple2<K, V>> maxOption(Ordering<B> ord) {
            return IterableOnceOps.maxOption$(this, ord);
        }

        @Override
        public Object maxBy(Function1 f, Ordering cmp) {
            return IterableOnceOps.maxBy$(this, f, cmp);
        }

        @Override
        public <B> Option<Tuple2<K, V>> maxByOption(Function1<Tuple2<K, V>, B> f, Ordering<B> cmp) {
            return IterableOnceOps.maxByOption$(this, f, cmp);
        }

        @Override
        public Object minBy(Function1 f, Ordering cmp) {
            return IterableOnceOps.minBy$(this, f, cmp);
        }

        @Override
        public <B> Option<Tuple2<K, V>> minByOption(Function1<Tuple2<K, V>, B> f, Ordering<B> cmp) {
            return IterableOnceOps.minByOption$(this, f, cmp);
        }

        @Override
        public <B> Option<B> collectFirst(PartialFunction<Tuple2<K, V>, B> pf) {
            return IterableOnceOps.collectFirst$(this, pf);
        }

        @Override
        public <B> B aggregate(Function0<B> z, Function2<B, Tuple2<K, V>, B> seqop, Function2<B, B, B> combop) {
            return (B)IterableOnceOps.aggregate$(this, z, seqop, combop);
        }

        @Override
        public <B> boolean corresponds(IterableOnce<B> that, Function2<Tuple2<K, V>, B, Object> p) {
            return IterableOnceOps.corresponds$(this, that, p);
        }

        @Override
        public final String mkString(String start2, String sep, String end) {
            return IterableOnceOps.mkString$(this, start2, sep, end);
        }

        @Override
        public final String mkString(String sep) {
            return IterableOnceOps.mkString$(this, sep);
        }

        @Override
        public final String mkString() {
            return IterableOnceOps.mkString$(this);
        }

        @Override
        public final StringBuilder addString(StringBuilder b, String sep) {
            return IterableOnceOps.addString$(this, b, sep);
        }

        @Override
        public final StringBuilder addString(StringBuilder b) {
            return IterableOnceOps.addString$(this, b);
        }

        @Override
        public <C1> C1 to(Factory<Tuple2<K, V>, C1> factory2) {
            return (C1)IterableOnceOps.to$(this, factory2);
        }

        @Override
        public final Iterator<Tuple2<K, V>> toIterator() {
            return IterableOnceOps.toIterator$(this);
        }

        @Override
        public List<Tuple2<K, V>> toList() {
            return IterableOnceOps.toList$(this);
        }

        @Override
        public Vector<Tuple2<K, V>> toVector() {
            return IterableOnceOps.toVector$(this);
        }

        @Override
        public <B> Set<B> toSet() {
            return IterableOnceOps.toSet$(this);
        }

        @Override
        public Seq<Tuple2<K, V>> toSeq() {
            return IterableOnceOps.toSeq$(this);
        }

        @Override
        public IndexedSeq<Tuple2<K, V>> toIndexedSeq() {
            return IterableOnceOps.toIndexedSeq$(this);
        }

        @Override
        public final Stream<Tuple2<K, V>> toStream() {
            return IterableOnceOps.toStream$(this);
        }

        @Override
        public final <B> Buffer<B> toBuffer() {
            return IterableOnceOps.toBuffer$(this);
        }

        @Override
        public <B> Object toArray(ClassTag<B> evidence$2) {
            return IterableOnceOps.toArray$(this, evidence$2);
        }

        @Override
        public Iterable<Tuple2<K, V>> reversed() {
            return IterableOnceOps.reversed$(this);
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<Tuple2<K, V>, S> shape) {
            return (S)IterableOnce.stepper$(this, shape);
        }

        @Override
        public int size() {
            return 3;
        }

        @Override
        public int knownSize() {
            return 3;
        }

        @Override
        public V apply(K key) {
            if (BoxesRunTime.equals(key, this.key1)) {
                return this.value1;
            }
            if (BoxesRunTime.equals(key, this.key2)) {
                return this.value2;
            }
            if (BoxesRunTime.equals(key, this.key3)) {
                return this.value3;
            }
            throw new NoSuchElementException(new java.lang.StringBuilder(15).append("key not found: ").append(key).toString());
        }

        @Override
        public boolean contains(K key) {
            return BoxesRunTime.equals(key, this.key1) || BoxesRunTime.equals(key, this.key2) || BoxesRunTime.equals(key, this.key3);
        }

        @Override
        public Option<V> get(K key) {
            if (BoxesRunTime.equals(key, this.key1)) {
                return new Some<V>(this.value1);
            }
            if (BoxesRunTime.equals(key, this.key2)) {
                return new Some<V>(this.value2);
            }
            if (BoxesRunTime.equals(key, this.key3)) {
                return new Some<V>(this.value3);
            }
            return None$.MODULE$;
        }

        @Override
        public <V1> V1 getOrElse(K key, Function0<V1> function0) {
            if (BoxesRunTime.equals(key, this.key1)) {
                return (V1)this.value1;
            }
            if (BoxesRunTime.equals(key, this.key2)) {
                return (V1)this.value2;
            }
            if (BoxesRunTime.equals(key, this.key3)) {
                return (V1)this.value3;
            }
            return function0.apply();
        }

        @Override
        public Iterator<Tuple2<K, V>> iterator() {
            Tuple2<K, V> tuple2 = new Tuple2<K, V>(this.key1, this.value1);
            Tuple2<K, V> tuple22 = new Tuple2<K, V>(this.key2, this.value2);
            Tuple2<K, V> tuple23 = new Tuple2<K, V>(this.key3, this.value3);
            Nil$ $colon$colon_this = Nil$.MODULE$;
            Object var4_4 = null;
            $colon$colon<Nothing$> $colon$colon_this2 = new $colon$colon<Nothing$>((Nothing$)((Object)tuple23), $colon$colon_this);
            Object var5_5 = null;
            $colon$colon<Nothing$> $colon$colon_this3 = new $colon$colon<Nothing$>((Nothing$)((Object)tuple22), (List<Nothing$>)$colon$colon_this2);
            Object var6_6 = null;
            return new $colon$colon<Nothing$>((Nothing$)((Object)tuple2), (List<Nothing$>)$colon$colon_this3).iterator();
        }

        @Override
        public <V1> SeqMap<K, V1> updated(K key, V1 value) {
            if (BoxesRunTime.equals(key, this.key1)) {
                return new SeqMap3<K, V1>(this.key1, value, this.key2, this.value2, this.key3, this.value3);
            }
            if (BoxesRunTime.equals(key, this.key2)) {
                return new SeqMap3<K, V>(this.key1, this.value1, this.key2, value, this.key3, this.value3);
            }
            if (BoxesRunTime.equals(key, this.key3)) {
                return new SeqMap3<K, V>(this.key1, this.value1, this.key2, this.value2, this.key3, value);
            }
            return new SeqMap4<K, V>(this.key1, this.value1, this.key2, this.value2, this.key3, this.value3, key, value);
        }

        @Override
        public SeqMap<K, V> removed(K key) {
            if (BoxesRunTime.equals(key, this.key1)) {
                return new SeqMap2<K, V>(this.key2, this.value2, this.key3, this.value3);
            }
            if (BoxesRunTime.equals(key, this.key2)) {
                return new SeqMap2<K, V>(this.key1, this.value1, this.key3, this.value3);
            }
            if (BoxesRunTime.equals(key, this.key3)) {
                return new SeqMap2<K, V>(this.key1, this.value1, this.key2, this.value2);
            }
            return this;
        }

        @Override
        public <U> void foreach(Function1<Tuple2<K, V>, U> f) {
            f.apply(new Tuple2<K, V>(this.key1, this.value1));
            f.apply(new Tuple2<K, V>(this.key2, this.value2));
            f.apply(new Tuple2<K, V>(this.key3, this.value3));
        }

        @Override
        public <U> void foreachEntry(Function2<K, V, U> f) {
            f.apply(this.key1, this.value1);
            f.apply(this.key2, this.value2);
            f.apply(this.key3, this.value3);
        }

        public SeqMap3(K key1, V value1, K key2, V value2, K key3, V value3) {
            this.key1 = key1;
            this.value1 = value1;
            this.key2 = key2;
            this.value2 = value2;
            this.key3 = key3;
            this.value3 = value3;
        }
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class SeqMap4<K, V>
    implements SeqMap<K, V>,
    Serializable {
        private static final long serialVersionUID = 3L;
        private final K key1;
        private final V value1;
        private final K key2;
        private final V value2;
        private final K key3;
        private final V value3;
        private final K key4;
        private final V value4;

        @Override
        public MapFactory<SeqMap> mapFactory() {
            return ((SeqMap)this).mapFactory();
        }

        @Override
        public String stringPrefix() {
            return scala.collection.SeqMap.stringPrefix$(this);
        }

        @Override
        public final <K2, V2> scala.collection.immutable.Map<K2, V2> toMap($less$colon$less<Tuple2<K, V>, Tuple2<K2, V2>> ev) {
            return scala.collection.immutable.Map.toMap$(this, ev);
        }

        @Override
        public <V1> scala.collection.immutable.Map<K, V1> withDefault(Function1<K, V1> d) {
            return scala.collection.immutable.Map.withDefault$(this, d);
        }

        @Override
        public <V1> scala.collection.immutable.Map<K, V1> withDefaultValue(V1 d) {
            return scala.collection.immutable.Map.withDefaultValue$(this, d);
        }

        @Override
        public final scala.collection.immutable.MapOps $minus(Object key) {
            return scala.collection.immutable.MapOps.$minus$(this, key);
        }

        @Override
        public scala.collection.immutable.MapOps $minus(Object key1, Object key2, Seq keys2) {
            return scala.collection.immutable.MapOps.$minus$(this, key1, key2, keys2);
        }

        @Override
        public scala.collection.immutable.MapOps removedAll(IterableOnce keys2) {
            return scala.collection.immutable.MapOps.removedAll$(this, keys2);
        }

        @Override
        public final scala.collection.immutable.MapOps $minus$minus(IterableOnce keys2) {
            return scala.collection.immutable.MapOps.$minus$minus$(this, keys2);
        }

        @Override
        public scala.collection.immutable.MapOps updatedWith(Object key, Function1 remappingFunction) {
            return scala.collection.immutable.MapOps.updatedWith$(this, key, remappingFunction);
        }

        @Override
        public scala.collection.immutable.MapOps $plus(Tuple2 kv) {
            return scala.collection.immutable.MapOps.$plus$(this, kv);
        }

        @Override
        public scala.collection.immutable.MapOps transform(Function2 f) {
            return scala.collection.immutable.MapOps.transform$(this, f);
        }

        @Override
        public Set<K> keySet() {
            return scala.collection.immutable.MapOps.keySet$(this);
        }

        @Override
        public boolean canEqual(Object that) {
            return Map.canEqual$(this, that);
        }

        @Override
        public boolean equals(Object o) {
            return Map.equals$(this, o);
        }

        @Override
        public int hashCode() {
            return Map.hashCode$(this);
        }

        @Override
        public String toString() {
            return Map.toString$(this);
        }

        @Override
        public IterableOps fromSpecific(IterableOnce coll) {
            return MapFactoryDefaults.fromSpecific$(this, coll);
        }

        @Override
        public Builder<Tuple2<K, V>, SeqMap<K, V>> newSpecificBuilder() {
            return MapFactoryDefaults.newSpecificBuilder$(this);
        }

        @Override
        public IterableOps empty() {
            return MapFactoryDefaults.empty$(this);
        }

        @Override
        public MapOps.WithFilter<K, V, ?, ?> withFilter(Function1<Tuple2<K, V>, Object> p) {
            return MapFactoryDefaults.withFilter$(this, p);
        }

        @Override
        public MapView<K, V> view() {
            return MapOps.view$(this);
        }

        @Override
        public <S extends Stepper<?>> S keyStepper(StepperShape<K, S> shape) {
            return (S)MapOps.keyStepper$(this, shape);
        }

        @Override
        public <S extends Stepper<?>> S valueStepper(StepperShape<V, S> shape) {
            return (S)MapOps.valueStepper$(this, shape);
        }

        @Override
        public final IterableOps mapFromIterable(Iterable it) {
            return MapOps.mapFromIterable$(this, it);
        }

        @Override
        public <K1 extends K, V1> V1 applyOrElse(K1 x, Function1<K1, V1> function1) {
            return (V1)MapOps.applyOrElse$(this, x, function1);
        }

        @Override
        public Iterable<K> keys() {
            return MapOps.keys$(this);
        }

        @Override
        public Iterable<V> values() {
            return MapOps.values$(this);
        }

        @Override
        public Iterator<K> keysIterator() {
            return MapOps.keysIterator$(this);
        }

        @Override
        public Iterator<V> valuesIterator() {
            return MapOps.valuesIterator$(this);
        }

        @Override
        public MapView<K, V> filterKeys(Function1<K, Object> p) {
            return MapOps.filterKeys$(this, p);
        }

        @Override
        public <W> MapView<K, W> mapValues(Function1<V, W> f) {
            return MapOps.mapValues$(this, f);
        }

        @Override
        public V default(K key) throws NoSuchElementException {
            return (V)MapOps.default$(this, key);
        }

        @Override
        public boolean isDefinedAt(K key) {
            return MapOps.isDefinedAt$(this, key);
        }

        @Override
        public IterableOps map(Function1 f) {
            return MapOps.map$(this, f);
        }

        @Override
        public IterableOps collect(PartialFunction pf) {
            return MapOps.collect$(this, pf);
        }

        @Override
        public IterableOps flatMap(Function1 f) {
            return MapOps.flatMap$(this, f);
        }

        @Override
        public IterableOps concat(IterableOnce suffix) {
            return MapOps.concat$(this, suffix);
        }

        @Override
        public IterableOps $plus$plus(IterableOnce xs) {
            return MapOps.$plus$plus$(this, xs);
        }

        @Override
        public StringBuilder addString(StringBuilder sb, String start2, String sep, String end) {
            return MapOps.addString$(this, sb, start2, sep, end);
        }

        @Override
        public IterableOps $plus(Tuple2 elem1, Tuple2 elem2, Seq elems) {
            return MapOps.$plus$(this, elem1, elem2, elems);
        }

        @Override
        public IterableOps $plus$plus$colon(IterableOnce that) {
            return MapOps.$plus$plus$colon$(this, that);
        }

        @Override
        public Option<V> unapply(K a) {
            return PartialFunction.unapply$(this, a);
        }

        @Override
        public PartialFunction<K, V> elementWise() {
            return PartialFunction.elementWise$(this);
        }

        @Override
        public <A1 extends K, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
            return PartialFunction.orElse$(this, that);
        }

        @Override
        public <C$> PartialFunction<K, C$> andThen(Function1<V, C$> k) {
            return PartialFunction.andThen$((PartialFunction)this, k);
        }

        @Override
        public <C$> PartialFunction<K, C$> andThen(PartialFunction<V, C$> k) {
            return PartialFunction.andThen$((PartialFunction)this, k);
        }

        @Override
        public <R$> PartialFunction<R$, V> compose(PartialFunction<R$, K> k) {
            return PartialFunction.compose$(this, k);
        }

        @Override
        public Function1<K, Option<V>> lift() {
            return PartialFunction.lift$(this);
        }

        @Override
        public <U> Function1<K, Object> runWith(Function1<V, U> action) {
            return PartialFunction.runWith$(this, action);
        }

        @Override
        public boolean apply$mcZD$sp(double v1) {
            return Function1.apply$mcZD$sp$(this, v1);
        }

        @Override
        public double apply$mcDD$sp(double v1) {
            return Function1.apply$mcDD$sp$(this, v1);
        }

        @Override
        public float apply$mcFD$sp(double v1) {
            return Function1.apply$mcFD$sp$(this, v1);
        }

        @Override
        public int apply$mcID$sp(double v1) {
            return Function1.apply$mcID$sp$(this, v1);
        }

        @Override
        public long apply$mcJD$sp(double v1) {
            return Function1.apply$mcJD$sp$(this, v1);
        }

        @Override
        public void apply$mcVD$sp(double v1) {
            Function1.apply$mcVD$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZF$sp(float v1) {
            return Function1.apply$mcZF$sp$(this, v1);
        }

        @Override
        public double apply$mcDF$sp(float v1) {
            return Function1.apply$mcDF$sp$(this, v1);
        }

        @Override
        public float apply$mcFF$sp(float v1) {
            return Function1.apply$mcFF$sp$(this, v1);
        }

        @Override
        public int apply$mcIF$sp(float v1) {
            return Function1.apply$mcIF$sp$(this, v1);
        }

        @Override
        public long apply$mcJF$sp(float v1) {
            return Function1.apply$mcJF$sp$(this, v1);
        }

        @Override
        public void apply$mcVF$sp(float v1) {
            Function1.apply$mcVF$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZI$sp(int v1) {
            return Function1.apply$mcZI$sp$(this, v1);
        }

        @Override
        public double apply$mcDI$sp(int v1) {
            return Function1.apply$mcDI$sp$(this, v1);
        }

        @Override
        public float apply$mcFI$sp(int v1) {
            return Function1.apply$mcFI$sp$(this, v1);
        }

        @Override
        public int apply$mcII$sp(int v1) {
            return Function1.apply$mcII$sp$(this, v1);
        }

        @Override
        public long apply$mcJI$sp(int v1) {
            return Function1.apply$mcJI$sp$(this, v1);
        }

        @Override
        public void apply$mcVI$sp(int v1) {
            Function1.apply$mcVI$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZJ$sp(long v1) {
            return Function1.apply$mcZJ$sp$(this, v1);
        }

        @Override
        public double apply$mcDJ$sp(long v1) {
            return Function1.apply$mcDJ$sp$(this, v1);
        }

        @Override
        public float apply$mcFJ$sp(long v1) {
            return Function1.apply$mcFJ$sp$(this, v1);
        }

        @Override
        public int apply$mcIJ$sp(long v1) {
            return Function1.apply$mcIJ$sp$(this, v1);
        }

        @Override
        public long apply$mcJJ$sp(long v1) {
            return Function1.apply$mcJJ$sp$(this, v1);
        }

        @Override
        public void apply$mcVJ$sp(long v1) {
            Function1.apply$mcVJ$sp$(this, v1);
        }

        @Override
        public <A$> Function1<A$, V> compose(Function1<A$, K> g2) {
            return Function1.compose$(this, g2);
        }

        @Override
        public IterableFactory<scala.collection.immutable.Iterable> iterableFactory() {
            return scala.collection.immutable.Iterable.iterableFactory$(this);
        }

        @Override
        public final Iterable<Tuple2<K, V>> toIterable() {
            return Iterable.toIterable$(this);
        }

        @Override
        public final Iterable<Tuple2<K, V>> coll() {
            return Iterable.coll$(this);
        }

        @Override
        public Iterable<Tuple2<K, V>> seq() {
            return Iterable.seq$(this);
        }

        @Override
        public String className() {
            return Iterable.className$(this);
        }

        @Override
        public final String collectionClassName() {
            return Iterable.collectionClassName$(this);
        }

        @Override
        public <B> LazyZip2<Tuple2<K, V>, B, Iterable> lazyZip(Iterable<B> that) {
            return Iterable.lazyZip$(this, that);
        }

        @Override
        public final Iterable<Tuple2<K, V>> toTraversable() {
            return IterableOps.toTraversable$(this);
        }

        @Override
        public boolean isTraversableAgain() {
            return IterableOps.isTraversableAgain$(this);
        }

        @Override
        public final Object repr() {
            return IterableOps.repr$(this);
        }

        @Override
        public IterableFactory<?> companion() {
            return IterableOps.companion$(this);
        }

        @Override
        public Object head() {
            return IterableOps.head$(this);
        }

        @Override
        public Option<Tuple2<K, V>> headOption() {
            return IterableOps.headOption$(this);
        }

        @Override
        public Object last() {
            return IterableOps.last$(this);
        }

        @Override
        public Option<Tuple2<K, V>> lastOption() {
            return IterableOps.lastOption$(this);
        }

        @Override
        public int sizeCompare(int otherSize) {
            return IterableOps.sizeCompare$((IterableOps)this, otherSize);
        }

        @Override
        public final IterableOps sizeIs() {
            return IterableOps.sizeIs$(this);
        }

        @Override
        public int sizeCompare(Iterable<?> that) {
            return IterableOps.sizeCompare$((IterableOps)this, that);
        }

        @Override
        public View<Tuple2<K, V>> view(int from, int until) {
            return IterableOps.view$(this, from, until);
        }

        @Override
        public Object transpose(Function1 asIterable) {
            return IterableOps.transpose$(this, asIterable);
        }

        @Override
        public Object filter(Function1 pred) {
            return IterableOps.filter$(this, pred);
        }

        @Override
        public Object filterNot(Function1 pred) {
            return IterableOps.filterNot$(this, pred);
        }

        @Override
        public Tuple2<SeqMap<K, V>, SeqMap<K, V>> partition(Function1<Tuple2<K, V>, Object> p) {
            return IterableOps.partition$(this, p);
        }

        @Override
        public Tuple2<SeqMap<K, V>, SeqMap<K, V>> splitAt(int n) {
            return IterableOps.splitAt$(this, n);
        }

        @Override
        public Object take(int n) {
            return IterableOps.take$(this, n);
        }

        @Override
        public Object takeRight(int n) {
            return IterableOps.takeRight$(this, n);
        }

        @Override
        public Object takeWhile(Function1 p) {
            return IterableOps.takeWhile$(this, p);
        }

        @Override
        public Tuple2<SeqMap<K, V>, SeqMap<K, V>> span(Function1<Tuple2<K, V>, Object> p) {
            return IterableOps.span$(this, p);
        }

        @Override
        public Object drop(int n) {
            return IterableOps.drop$(this, n);
        }

        @Override
        public Object dropRight(int n) {
            return IterableOps.dropRight$(this, n);
        }

        @Override
        public Object dropWhile(Function1 p) {
            return IterableOps.dropWhile$(this, p);
        }

        @Override
        public Iterator<SeqMap<K, V>> grouped(int size) {
            return IterableOps.grouped$(this, size);
        }

        @Override
        public Iterator<SeqMap<K, V>> sliding(int size) {
            return IterableOps.sliding$(this, size);
        }

        @Override
        public Iterator<SeqMap<K, V>> sliding(int size, int step) {
            return IterableOps.sliding$(this, size, step);
        }

        @Override
        public Object tail() {
            return IterableOps.tail$(this);
        }

        @Override
        public Object init() {
            return IterableOps.init$(this);
        }

        @Override
        public Object slice(int from, int until) {
            return IterableOps.slice$(this, from, until);
        }

        @Override
        public <K$> scala.collection.immutable.Map<K$, SeqMap<K, V>> groupBy(Function1<Tuple2<K, V>, K$> f) {
            return IterableOps.groupBy$(this, f);
        }

        @Override
        public <K$, B> scala.collection.immutable.Map<K$, scala.collection.immutable.Iterable<B>> groupMap(Function1<Tuple2<K, V>, K$> key, Function1<Tuple2<K, V>, B> f) {
            return IterableOps.groupMap$(this, key, f);
        }

        @Override
        public <K$, B> scala.collection.immutable.Map<K$, B> groupMapReduce(Function1<Tuple2<K, V>, K$> key, Function1<Tuple2<K, V>, B> f, Function2<B, B, B> reduce) {
            return IterableOps.groupMapReduce$(this, key, f, reduce);
        }

        @Override
        public Object scan(Object z, Function2 op) {
            return IterableOps.scan$(this, z, op);
        }

        @Override
        public Object scanLeft(Object z, Function2 op) {
            return IterableOps.scanLeft$(this, z, op);
        }

        @Override
        public Object scanRight(Object z, Function2 op) {
            return IterableOps.scanRight$(this, z, op);
        }

        @Override
        public Object map(Function1 f) {
            return IterableOps.map$(this, f);
        }

        @Override
        public Object flatMap(Function1 f) {
            return IterableOps.flatMap$(this, f);
        }

        @Override
        public Object flatten(Function1 asIterable) {
            return IterableOps.flatten$(this, asIterable);
        }

        @Override
        public Object collect(PartialFunction pf) {
            return IterableOps.collect$(this, pf);
        }

        @Override
        public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> partitionMap(Function1<Tuple2<K, V>, Either<A1, A2>> f) {
            return IterableOps.partitionMap$(this, f);
        }

        @Override
        public Object concat(IterableOnce suffix) {
            return IterableOps.concat$(this, suffix);
        }

        @Override
        public final Object $plus$plus(IterableOnce suffix) {
            return IterableOps.$plus$plus$(this, suffix);
        }

        @Override
        public Object zip(IterableOnce that) {
            return IterableOps.zip$(this, that);
        }

        @Override
        public Object zipWithIndex() {
            return IterableOps.zipWithIndex$(this);
        }

        @Override
        public Object zipAll(Iterable that, Object thisElem, Object thatElem) {
            return IterableOps.zipAll$(this, that, thisElem, thatElem);
        }

        @Override
        public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> unzip(Function1<Tuple2<K, V>, Tuple2<A1, A2>> asPair) {
            return IterableOps.unzip$(this, asPair);
        }

        @Override
        public <A1, A2, A3> Tuple3<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>, scala.collection.immutable.Iterable<A3>> unzip3(Function1<Tuple2<K, V>, Tuple3<A1, A2, A3>> asTriple) {
            return IterableOps.unzip3$(this, asTriple);
        }

        @Override
        public Iterator<SeqMap<K, V>> tails() {
            return IterableOps.tails$(this);
        }

        @Override
        public Iterator<SeqMap<K, V>> inits() {
            return IterableOps.inits$(this);
        }

        @Override
        public Object tapEach(Function1 f) {
            return IterableOps.tapEach$(this, f);
        }

        @Override
        public Object $plus$plus$colon(IterableOnce that) {
            return IterableOps.$plus$plus$colon$(this, that);
        }

        @Override
        public boolean hasDefiniteSize() {
            return IterableOnceOps.hasDefiniteSize$(this);
        }

        @Override
        public boolean forall(Function1<Tuple2<K, V>, Object> p) {
            return IterableOnceOps.forall$(this, p);
        }

        @Override
        public boolean exists(Function1<Tuple2<K, V>, Object> p) {
            return IterableOnceOps.exists$(this, p);
        }

        @Override
        public int count(Function1<Tuple2<K, V>, Object> p) {
            return IterableOnceOps.count$(this, p);
        }

        @Override
        public Option<Tuple2<K, V>> find(Function1<Tuple2<K, V>, Object> p) {
            return IterableOnceOps.find$(this, p);
        }

        @Override
        public <B> B foldLeft(B z, Function2<B, Tuple2<K, V>, B> op) {
            return (B)IterableOnceOps.foldLeft$(this, z, op);
        }

        @Override
        public <B> B foldRight(B z, Function2<Tuple2<K, V>, B, B> op) {
            return (B)IterableOnceOps.foldRight$(this, z, op);
        }

        @Override
        public final <B> B $div$colon(B z, Function2<B, Tuple2<K, V>, B> op) {
            return (B)IterableOnceOps.$div$colon$(this, z, op);
        }

        @Override
        public final <B> B $colon$bslash(B z, Function2<Tuple2<K, V>, B, B> op) {
            return (B)IterableOnceOps.$colon$bslash$(this, z, op);
        }

        @Override
        public <A1> A1 fold(A1 z, Function2<A1, A1, A1> op) {
            return (A1)IterableOnceOps.fold$(this, z, op);
        }

        @Override
        public <B> B reduce(Function2<B, B, B> op) {
            return (B)IterableOnceOps.reduce$(this, op);
        }

        @Override
        public <B> Option<B> reduceOption(Function2<B, B, B> op) {
            return IterableOnceOps.reduceOption$(this, op);
        }

        @Override
        public <B> B reduceLeft(Function2<B, Tuple2<K, V>, B> op) {
            return (B)IterableOnceOps.reduceLeft$(this, op);
        }

        @Override
        public <B> B reduceRight(Function2<Tuple2<K, V>, B, B> op) {
            return (B)IterableOnceOps.reduceRight$(this, op);
        }

        @Override
        public <B> Option<B> reduceLeftOption(Function2<B, Tuple2<K, V>, B> op) {
            return IterableOnceOps.reduceLeftOption$(this, op);
        }

        @Override
        public <B> Option<B> reduceRightOption(Function2<Tuple2<K, V>, B, B> op) {
            return IterableOnceOps.reduceRightOption$(this, op);
        }

        @Override
        public boolean isEmpty() {
            return IterableOnceOps.isEmpty$(this);
        }

        @Override
        public boolean nonEmpty() {
            return IterableOnceOps.nonEmpty$(this);
        }

        @Override
        public final <B> void copyToBuffer(Buffer<B> dest) {
            IterableOnceOps.copyToBuffer$(this, dest);
        }

        @Override
        public <B> int copyToArray(Object xs) {
            return IterableOnceOps.copyToArray$(this, xs);
        }

        @Override
        public <B> int copyToArray(Object xs, int start2) {
            return IterableOnceOps.copyToArray$(this, xs, start2);
        }

        @Override
        public <B> int copyToArray(Object xs, int start2, int len) {
            return IterableOnceOps.copyToArray$(this, xs, start2, len);
        }

        @Override
        public <B> B sum(Numeric<B> num) {
            return (B)IterableOnceOps.sum$(this, num);
        }

        @Override
        public <B> B product(Numeric<B> num) {
            return (B)IterableOnceOps.product$(this, num);
        }

        @Override
        public Object min(Ordering ord) {
            return IterableOnceOps.min$(this, ord);
        }

        @Override
        public <B> Option<Tuple2<K, V>> minOption(Ordering<B> ord) {
            return IterableOnceOps.minOption$(this, ord);
        }

        @Override
        public Object max(Ordering ord) {
            return IterableOnceOps.max$(this, ord);
        }

        @Override
        public <B> Option<Tuple2<K, V>> maxOption(Ordering<B> ord) {
            return IterableOnceOps.maxOption$(this, ord);
        }

        @Override
        public Object maxBy(Function1 f, Ordering cmp) {
            return IterableOnceOps.maxBy$(this, f, cmp);
        }

        @Override
        public <B> Option<Tuple2<K, V>> maxByOption(Function1<Tuple2<K, V>, B> f, Ordering<B> cmp) {
            return IterableOnceOps.maxByOption$(this, f, cmp);
        }

        @Override
        public Object minBy(Function1 f, Ordering cmp) {
            return IterableOnceOps.minBy$(this, f, cmp);
        }

        @Override
        public <B> Option<Tuple2<K, V>> minByOption(Function1<Tuple2<K, V>, B> f, Ordering<B> cmp) {
            return IterableOnceOps.minByOption$(this, f, cmp);
        }

        @Override
        public <B> Option<B> collectFirst(PartialFunction<Tuple2<K, V>, B> pf) {
            return IterableOnceOps.collectFirst$(this, pf);
        }

        @Override
        public <B> B aggregate(Function0<B> z, Function2<B, Tuple2<K, V>, B> seqop, Function2<B, B, B> combop) {
            return (B)IterableOnceOps.aggregate$(this, z, seqop, combop);
        }

        @Override
        public <B> boolean corresponds(IterableOnce<B> that, Function2<Tuple2<K, V>, B, Object> p) {
            return IterableOnceOps.corresponds$(this, that, p);
        }

        @Override
        public final String mkString(String start2, String sep, String end) {
            return IterableOnceOps.mkString$(this, start2, sep, end);
        }

        @Override
        public final String mkString(String sep) {
            return IterableOnceOps.mkString$(this, sep);
        }

        @Override
        public final String mkString() {
            return IterableOnceOps.mkString$(this);
        }

        @Override
        public final StringBuilder addString(StringBuilder b, String sep) {
            return IterableOnceOps.addString$(this, b, sep);
        }

        @Override
        public final StringBuilder addString(StringBuilder b) {
            return IterableOnceOps.addString$(this, b);
        }

        @Override
        public <C1> C1 to(Factory<Tuple2<K, V>, C1> factory2) {
            return (C1)IterableOnceOps.to$(this, factory2);
        }

        @Override
        public final Iterator<Tuple2<K, V>> toIterator() {
            return IterableOnceOps.toIterator$(this);
        }

        @Override
        public List<Tuple2<K, V>> toList() {
            return IterableOnceOps.toList$(this);
        }

        @Override
        public Vector<Tuple2<K, V>> toVector() {
            return IterableOnceOps.toVector$(this);
        }

        @Override
        public <B> Set<B> toSet() {
            return IterableOnceOps.toSet$(this);
        }

        @Override
        public Seq<Tuple2<K, V>> toSeq() {
            return IterableOnceOps.toSeq$(this);
        }

        @Override
        public IndexedSeq<Tuple2<K, V>> toIndexedSeq() {
            return IterableOnceOps.toIndexedSeq$(this);
        }

        @Override
        public final Stream<Tuple2<K, V>> toStream() {
            return IterableOnceOps.toStream$(this);
        }

        @Override
        public final <B> Buffer<B> toBuffer() {
            return IterableOnceOps.toBuffer$(this);
        }

        @Override
        public <B> Object toArray(ClassTag<B> evidence$2) {
            return IterableOnceOps.toArray$(this, evidence$2);
        }

        @Override
        public Iterable<Tuple2<K, V>> reversed() {
            return IterableOnceOps.reversed$(this);
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<Tuple2<K, V>, S> shape) {
            return (S)IterableOnce.stepper$(this, shape);
        }

        @Override
        public int size() {
            return 4;
        }

        @Override
        public int knownSize() {
            return 4;
        }

        @Override
        public V apply(K key) {
            if (BoxesRunTime.equals(key, this.key1)) {
                return this.value1;
            }
            if (BoxesRunTime.equals(key, this.key2)) {
                return this.value2;
            }
            if (BoxesRunTime.equals(key, this.key3)) {
                return this.value3;
            }
            if (BoxesRunTime.equals(key, this.key4)) {
                return this.value4;
            }
            throw new NoSuchElementException(new java.lang.StringBuilder(15).append("key not found: ").append(key).toString());
        }

        @Override
        public boolean contains(K key) {
            return BoxesRunTime.equals(key, this.key1) || BoxesRunTime.equals(key, this.key2) || BoxesRunTime.equals(key, this.key3) || BoxesRunTime.equals(key, this.key4);
        }

        @Override
        public Option<V> get(K key) {
            if (BoxesRunTime.equals(key, this.key1)) {
                return new Some<V>(this.value1);
            }
            if (BoxesRunTime.equals(key, this.key2)) {
                return new Some<V>(this.value2);
            }
            if (BoxesRunTime.equals(key, this.key3)) {
                return new Some<V>(this.value3);
            }
            if (BoxesRunTime.equals(key, this.key4)) {
                return new Some<V>(this.value4);
            }
            return None$.MODULE$;
        }

        @Override
        public <V1> V1 getOrElse(K key, Function0<V1> function0) {
            if (BoxesRunTime.equals(key, this.key1)) {
                return (V1)this.value1;
            }
            if (BoxesRunTime.equals(key, this.key2)) {
                return (V1)this.value2;
            }
            if (BoxesRunTime.equals(key, this.key3)) {
                return (V1)this.value3;
            }
            if (BoxesRunTime.equals(key, this.key4)) {
                return (V1)this.value4;
            }
            return function0.apply();
        }

        @Override
        public Iterator<Tuple2<K, V>> iterator() {
            Tuple2<K, V> tuple2 = new Tuple2<K, V>(this.key1, this.value1);
            Tuple2<K, V> tuple22 = new Tuple2<K, V>(this.key2, this.value2);
            Tuple2<K, V> tuple23 = new Tuple2<K, V>(this.key3, this.value3);
            Tuple2<K, V> tuple24 = new Tuple2<K, V>(this.key4, this.value4);
            Nil$ $colon$colon_this = Nil$.MODULE$;
            Object var5_5 = null;
            $colon$colon<Nothing$> $colon$colon_this2 = new $colon$colon<Nothing$>((Nothing$)((Object)tuple24), $colon$colon_this);
            Object var6_6 = null;
            $colon$colon<Nothing$> $colon$colon_this3 = new $colon$colon<Nothing$>((Nothing$)((Object)tuple23), (List<Nothing$>)$colon$colon_this2);
            Object var7_7 = null;
            $colon$colon<Nothing$> $colon$colon_this4 = new $colon$colon<Nothing$>((Nothing$)((Object)tuple22), (List<Nothing$>)$colon$colon_this3);
            Object var8_8 = null;
            return new $colon$colon<Nothing$>((Nothing$)((Object)tuple2), (List<Nothing$>)$colon$colon_this4).iterator();
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public <V1> SeqMap<K, V1> updated(K key, V1 value) {
            void apply_elems;
            void apply_elems2;
            if (BoxesRunTime.equals(key, this.key1)) {
                return new SeqMap4<K, V1>(this.key1, value, this.key2, this.value2, this.key3, this.value3, this.key4, this.value4);
            }
            if (BoxesRunTime.equals(key, this.key2)) {
                return new SeqMap4<K, V>(this.key1, this.value1, this.key2, value, this.key3, this.value3, this.key4, this.value4);
            }
            if (BoxesRunTime.equals(key, this.key3)) {
                return new SeqMap4<K, V>(this.key1, this.value1, this.key2, this.value2, this.key3, value, this.key4, this.value4);
            }
            if (BoxesRunTime.equals(key, this.key4)) {
                return new SeqMap4<K, V>(this.key1, this.value1, this.key2, this.value2, this.key3, this.value3, this.key4, value);
            }
            ArraySeq arraySeq = ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.key1, this.key2, this.key3, this.key4, key});
            Vector$ apply_this = Vector$.MODULE$;
            Object var5_4 = null;
            arraySeq = null;
            Object fields = apply_this.from((IterableOnce)apply_elems2);
            ArraySeq<Tuple2> arraySeq2 = ScalaRunTime$.MODULE$.wrapRefArray(new Tuple2[]{new Tuple2<K, Tuple2<Integer, V>>(this.key1, new Tuple2<Integer, V>(0, this.value1)), new Tuple2<K, Tuple2<Integer, V>>(this.key2, new Tuple2<Integer, V>(1, this.value2)), new Tuple2<K, Tuple2<Integer, V>>(this.key3, new Tuple2<Integer, V>(2, this.value3)), new Tuple2<K, Tuple2<Integer, V>>(this.key4, new Tuple2<Integer, V>(3, this.value4)), new Tuple2<K, Tuple2<Integer, V1>>(key, new Tuple2<Integer, V1>(4, value))});
            HashMap$ apply_this2 = HashMap$.MODULE$;
            Object var7_7 = null;
            arraySeq2 = null;
            Object underlying = apply_this2.from((IterableOnce)apply_elems);
            return new VectorMap(fields, underlying);
        }

        @Override
        public SeqMap<K, V> removed(K key) {
            if (BoxesRunTime.equals(key, this.key1)) {
                return new SeqMap3<K, V>(this.key2, this.value2, this.key3, this.value3, this.key4, this.value4);
            }
            if (BoxesRunTime.equals(key, this.key2)) {
                return new SeqMap3<K, V>(this.key1, this.value1, this.key3, this.value3, this.key4, this.value4);
            }
            if (BoxesRunTime.equals(key, this.key3)) {
                return new SeqMap3<K, V>(this.key1, this.value1, this.key2, this.value2, this.key4, this.value4);
            }
            if (BoxesRunTime.equals(key, this.key4)) {
                return new SeqMap3<K, V>(this.key1, this.value1, this.key2, this.value2, this.key3, this.value3);
            }
            return this;
        }

        @Override
        public <U> void foreach(Function1<Tuple2<K, V>, U> f) {
            f.apply(new Tuple2<K, V>(this.key1, this.value1));
            f.apply(new Tuple2<K, V>(this.key2, this.value2));
            f.apply(new Tuple2<K, V>(this.key3, this.value3));
            f.apply(new Tuple2<K, V>(this.key4, this.value4));
        }

        @Override
        public <U> void foreachEntry(Function2<K, V, U> f) {
            f.apply(this.key1, this.value1);
            f.apply(this.key2, this.value2);
            f.apply(this.key3, this.value3);
            f.apply(this.key4, this.value4);
        }

        public <V1> Builder<Tuple2<K, V1>, SeqMap<K, V1>> buildTo(Builder<Tuple2<K, V1>, SeqMap<K, V1>> builder) {
            return (Builder)builder.addOne(new Tuple2<K, V>(this.key1, this.value1)).addOne(new Tuple2<K, V>(this.key2, this.value2)).addOne(new Tuple2<K, V>(this.key3, this.value3)).addOne(new Tuple2<K, V>(this.key4, this.value4));
        }

        public SeqMap4(K key1, V value1, K key2, V value2, K key3, V value3, K key4, V value4) {
            this.key1 = key1;
            this.value1 = value1;
            this.key2 = key2;
            this.value2 = value2;
            this.key3 = key3;
            this.value3 = value3;
            this.key4 = key4;
            this.value4 = value4;
        }
    }

    private static final class SeqMapBuilderImpl<K, V>
    implements ReusableBuilder<Tuple2<K, V>, SeqMap<K, V>> {
        private SeqMap<K, V> elems = SeqMap$EmptySeqMap$.MODULE$;
        private boolean switchedToVectorMapBuilder = false;
        private VectorMapBuilder<K, V> vectorMapBuilder;

        @Override
        public void sizeHint(int size) {
            Builder.sizeHint$(this, size);
        }

        @Override
        public final void sizeHint(IterableOnce<?> coll, int delta) {
            Builder.sizeHint$(this, coll, delta);
        }

        @Override
        public final int sizeHint$default$2() {
            return Builder.sizeHint$default$2$(this);
        }

        @Override
        public final void sizeHintBounded(int size, Iterable<?> boundingColl) {
            Builder.sizeHintBounded$(this, size, boundingColl);
        }

        @Override
        public <NewTo> Builder<Tuple2<K, V>, NewTo> mapResult(Function1<SeqMap<K, V>, NewTo> f) {
            return Builder.mapResult$(this, f);
        }

        @Override
        public final Growable $plus$eq(Object elem) {
            return Growable.$plus$eq$(this, elem);
        }

        @Override
        public final Growable $plus$eq(Object elem1, Object elem2, Seq elems) {
            return Growable.$plus$eq$(this, elem1, elem2, elems);
        }

        @Override
        public final Growable<Tuple2<K, V>> $plus$plus$eq(IterableOnce<Tuple2<K, V>> xs) {
            return Growable.$plus$plus$eq$(this, xs);
        }

        @Override
        public int knownSize() {
            return Growable.knownSize$(this);
        }

        @Override
        public void clear() {
            this.elems = SeqMap$EmptySeqMap$.MODULE$;
            if (this.vectorMapBuilder != null) {
                this.vectorMapBuilder.clear();
            }
            this.switchedToVectorMapBuilder = false;
        }

        @Override
        public SeqMap<K, V> result() {
            if (this.switchedToVectorMapBuilder) {
                return this.vectorMapBuilder.result();
            }
            return this.elems;
        }

        public SeqMapBuilderImpl<K, V> addOne(Tuple2<K, V> elem) {
            if (this.switchedToVectorMapBuilder) {
                this.vectorMapBuilder.addOne(elem);
            } else if (this.elems.size() < 4) {
                this.elems = (SeqMap)this.elems.$plus(elem);
            } else if (this.elems.contains(elem._1())) {
                this.elems = (SeqMap)this.elems.$plus(elem);
            } else {
                this.switchedToVectorMapBuilder = true;
                if (this.vectorMapBuilder == null) {
                    this.vectorMapBuilder = new VectorMapBuilder();
                }
                ((SeqMap4)this.elems).buildTo(this.vectorMapBuilder);
                this.vectorMapBuilder.addOne(elem);
            }
            return this;
        }

        public SeqMapBuilderImpl<K, V> addAll(IterableOnce<Tuple2<K, V>> xs) {
            if (this.switchedToVectorMapBuilder) {
                VectorMapBuilder<K, V> vectorMapBuilder = this.vectorMapBuilder;
                if (vectorMapBuilder == null) {
                    throw null;
                }
                Growable.addAll$(vectorMapBuilder, xs);
                return this;
            }
            return (SeqMapBuilderImpl)Growable.addAll$(this, xs);
        }
    }
}

