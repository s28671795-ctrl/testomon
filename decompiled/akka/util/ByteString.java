/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.ByteIterator;
import akka.util.ByteIterator$ByteArrayIterator$;
import akka.util.ByteIterator$MultiByteArrayIterator$;
import akka.util.ByteString$;
import akka.util.ByteString$ByteString1$;
import akka.util.ByteString$ByteString1C$;
import akka.util.ByteString$ByteStrings$;
import akka.util.ByteString$Companion$;
import akka.util.ByteStringBuilder;
import akka.util.CompactByteString;
import akka.util.CompactByteString$;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import scala.$less$colon$less;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Tuple2;
import scala.Tuple2$mcII$sp;
import scala.Tuple3;
import scala.collection.AbstractSeq;
import scala.collection.ArrayOps$;
import scala.collection.Factory;
import scala.collection.IndexedSeq;
import scala.collection.IndexedSeqOps;
import scala.collection.IndexedSeqView;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableFactory$;
import scala.collection.IterableFactoryDefaults;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.JavaConverters$;
import scala.collection.LazyZip2;
import scala.collection.LinearSeq;
import scala.collection.Searching;
import scala.collection.Seq;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StrictOptimizedSeqOps;
import scala.collection.WithFilter;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Range;
import scala.collection.immutable.Set;
import scala.collection.immutable.Stream;
import scala.collection.immutable.Vector;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Map;
import scala.collection.mutable.StringBuilder;
import scala.math.Numeric;
import scala.math.Ordering;
import scala.math.package$;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;
import scala.runtime.Nothing$;
import scala.runtime.RichInt$;
import scala.runtime.ScalaRunTime$;
import scala.util.Either;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@ScalaSignature(bytes="\u0006\u0005\u001d\u001ds\u0001CA`\u0003\u0003D\t!a3\u0007\u0011\u0005=\u0017\u0011\u0019E\u0001\u0003#Dq!a8\u0002\t\u0003\t\t\u000fC\u0004\u0002d\u0006!\t!!:\t\u000f\u0005\r\u0018\u0001\"\u0001\u0007R!9\u00111]\u0001\u0005\u0002\u0019m\u0003bBAr\u0003\u0011\u0005aq\f\u0005\b\u0003G\fA\u0011\u0001D=\u0011\u001d\t\u0019/\u0001C\u0001\r{Bq!a9\u0002\t\u00031\u0019\tC\u0004\u0002d\u0006!\tA\"#\t\u000f\u0019=\u0015\u0001\"\u0001\u0007\u0012\"9aqS\u0001\u0005\u0002\u0019e\u0005b\u0002DH\u0003\u0011\u0005aQ\u0014\u0005\b\r/\u000bA\u0011\u0001DS\u0011\u001d1i+\u0001C\u0001\r_Cqaa2\u0002\t\u00031\u0019\rC\u0004\u0004H\u0006!\tAb2\t\u000f\r\u001d\u0017\u0001\"\u0001\u0007N\"Ia1[\u0001C\u0002\u0013\u0005Q1\u0007\u0005\t\r+\f\u0001\u0015!\u0003\u0004\\!9aq[\u0001\u0005\u0002\u0019e\u0007\"\u0003B!\u0003\t\u0007I\u0011\u0001B\"\u0011!\u0019)-\u0001Q\u0001\n\u0005\u001d\bb\u0002Do\u0003\u0011\u0005!1\t\u0005\b\r?\fA\u0011\u0001Dq\u0011\u001d1\u0019/\u0001C\u0001\rC<\u0011B\":\u0002\u0011\u0003\t)m!>\u0007\u0013\r=\u0018\u0001#\u0001\u0002F\u000eE\bbBAp9\u0011\u000511\u001f\u0005\n\u0005\u0003b\"\u0019!C\u0001\u0007oD\u0001b!2\u001dA\u0003%1\u0011 \u0005\b\u0007\u000fdB\u0011\u0001C6\u0011\u001d\t\u0019\u000f\bC\u0001\t_B\u0011B!\u0018\u001d\u0005\u0004%\tAa\u0018\t\u0011\rmG\u0004)A\u0005\u0003wDqA!\u0019\u001d\t\u0003!\u0019\bC\u0005\u0004>r\t\t\u0011\"\u0003\u0004b\u001a11q^\u0001\u0003\u0007wD!Ba&'\u0005\u000b\u0007I\u0011\u0002BM\u0011)\u0011\tK\nB\u0001B\u0003%!1\u0014\u0005\b\u0003?4C\u0011BB\u007f\u0011\u001d\t\u0019O\nC\u0001\t\u0003AqA!+'\t\u0003\u0012)\u000bC\u0004\u0003:\u001a\"\tEa/\t\u0013\u0011\u0015a\u0005\"\u0001\u0002F\n5\u0005\"\u0003B)M\u0011\u0005\u0011Q\u0019C\u0004\u0011\u001d\u0019IE\nC!\u0007\u0017Bqa!\u0014'\t\u0003\u001ay\u0005C\u0004\u0004X\u0019\"\t\u0005\"\u0003\t\u000f\r]c\u0005\"\u0011\u0005\u000e!91Q\u0010\u0014\u0005B\t\r\u0003bBB@M\u0011\u0005#1\t\u0005\b\u0007\u00033C\u0011\tC\t\u0011\u001d\u0019iA\nC!\t+AqAa='\t\u0003\"I\u0002C\u0004\u0004\u0002\u0019\"\t\u0005\"\b\t\u000f\r%e\u0005\"\u0011\u0005\"!91\u0011\u0004\u0014\u0005B\u00115\u0002\"\u0003BjM\u0011\u0005\u0013Q\u0019C\u001a\u0011\u001d\u0019)C\nC!\toA\u0011b!\u000f'\t\u0003\t)\rb\u000f\t\u0013\u0011\rc\u0005\"\u0001\u0002F\u0012\u0015\u0003bBBSM\u0011\u0005Cq\n\u0005\b\u0007\u00034C\u0011IBb\u000f%19/\u0001E\u0001\u0003\u000b\u0014YIB\u0005\u0003|\u0005A\t!!2\u0003~!9\u0011q\u001c\"\u0005\u0002\t%\u0005\"\u0003B!\u0005\n\u0007I\u0011\u0001BG\u0011!\u0019)M\u0011Q\u0001\n\t=\u0005bBBd\u0005\u0012\u00051\u0011\u001a\u0005\b\u0003G\u0014E\u0011ABh\u0011\u001d\t\u0019O\u0011C\u0001\u0007'D\u0011B!\u0018C\u0005\u0004%\tAa\u0018\t\u0011\rm'\t)A\u0005\u0003wDqA!\u0019C\t\u0003\u0019i\u000eC\u0005\u0004>\n\u000b\t\u0011\"\u0003\u0004b\u001a1!1P\u0001\u0003\u0005#C!Ba&N\u0005\u000b\u0007I\u0011\u0002BM\u0011)\u0011\t+\u0014B\u0001B\u0003%!1\u0014\u0005\u000b\u0005Gk%Q1A\u0005\n\t\u0015\u0006B\u0003BT\u001b\n\u0005\t\u0015!\u0003\u0003L!Q!\u0011V'\u0003\u0006\u0004%\tA!*\t\u0015\t-VJ!A!\u0002\u0013\u0011Y\u0005C\u0004\u0002`6#IA!,\t\u000f\u0005\rX\n\"\u0001\u00036\"9!\u0011X'\u0005B\tm\u0006b\u0002Bf\u001b\u0012%!Q\u001a\u0005\n\u0005'lE\u0011AAc\u0005+DqAa:N\t\u0003\u0011I\u000fC\u0005\u0003R5#\t!!2\u0003r\"9!1_'\u0005B\tU\b\"\u0003B~\u001b\u0012\u0005\u0011Q\u0019B\u007f\u0011\u001d\u0019\t!\u0014C!\u0007\u0007A\u0011ba\u0002N\t\u0003\t)m!\u0003\t\u000f\r5Q\n\"\u0011\u0004\u0010!I11C'\u0005\u0002\u0005\u00157Q\u0003\u0005\b\u00073iE\u0011IB\u000e\u0011\u001d\u0019)#\u0014C!\u0007OA\u0011b!\u000fN\t\u0003\t)ma\u000f\t\u000f\r}R\n\"\u0001\u0004B!91\u0011J'\u0005\u0002\r-\u0003bBB'\u001b\u0012\u00051q\n\u0005\b\u0007/jE\u0011IB-\u0011\u001d\u00199&\u0014C!\u0007_Bqa! N\t\u0003\u0012\u0019\u0005C\u0004\u0004\u00005#\tEa\u0011\t\u000f\r\u0005U\n\"\u0001\u0004\u0004\"91\u0011R'\u0005B\r-\u0005bBBS\u001b\u0012\u00053q\u0015\u0005\b\u0007{kE\u0011CB`\u0011\u001d\u0019\t-\u0014C!\u0007\u0007<\u0011B\";\u0002\u0011\u0003\t)\r\" \u0007\u0013\u0011]\u0014\u0001#\u0001\u0002F\u0012e\u0004bBApc\u0012\u0005A1\u0010\u0005\b\u0003G\fH\u0011\u0001C@\u0011\u001d\t\u0019/\u001dC\u0001\t\u0017Cq!a9r\t\u0003!\t\nC\u0004\u0002dF$\t\u0001b'\t\u000f\u0005\r\u0018\u000f\"\u0001\u0006\u0016!9\u00111]9\u0005\u0002\u0015m\u0001bBC\u0013c\u0012\u0005Qq\u0005\u0005\n\u0005;\n(\u0019!C\u0001\u0005?B\u0001ba7rA\u0003%\u00111 \u0005\b\u0005C\nH\u0011AC\u0017\u0011%\u0019i,]A\u0001\n\u0013\u0019\tO\u0002\u0004\u0005x\u0005\u0011Aq\u0015\u0005\r\t\u0007s(Q1A\u0005\u0002\u0005\u0015G\u0011\u0016\u0005\u000b\tWs(\u0011!Q\u0001\n\u0011\u0015\u0005B\u0003BU}\n\u0015\r\u0011\"\u0001\u0003&\"Q!1\u0016@\u0003\u0002\u0003\u0006IAa\u0013\t\u000f\u0005}g\u0010\"\u0003\u0005.\"9\u00111\u001d@\u0005\u0002\u0011M\u0006b\u0002B]}\u0012\u0005Cq\u0017\u0005\b\u0007\u0003sH\u0011\u0001C`\u0011%\u0011\tF C\u0001\u0003\u000b$\u0019\rC\u0004\u0003hz$\tA!;\t\u000f\r\u0015b\u0010\"\u0011\u0005F\"91q\b@\u0005\u0002\r\u0005\u0003bBB%}\u0012\u000511\n\u0005\b\u0007\u001brH\u0011AB(\u0011\u001d\u00199F C\u0001\t\u0013Dqaa\u0016\u007f\t\u0003!i\rC\u0004\u0004~y$\tEa\u0011\t\u000f\r}d\u0010\"\u0011\u0003D!I!1\u001b@\u0005\u0002\u0005\u0015G\u0011\u001b\u0005\b\u0007\u001bqH\u0011\tCk\u0011%!IN C\u0001\u0003\u000b$Y\u000eC\u0004\u0003tz$\t\u0005b8\t\u000f\u0011\rh\u0010\"\u0003\u0005f\"91\u0011\u0004@\u0005B\u0011%\bbBB\u0001}\u0012\u0005Cq\u001e\u0005\b\tgtH\u0011\u0002C{\u0011\u001d\u0019II C!\tsDqa!*\u007f\t\u0003*)\u0001C\u0004\u0004>z$\tba0\u0007\r\u0019-\u0018\u0001\u0002Dw\u0011-1y/!\u000f\u0003\u0002\u0004%IAa\u0011\t\u0017\u0019E\u0018\u0011\bBA\u0002\u0013%a1\u001f\u0005\f\rs\fID!A!B\u0013\t9\u000f\u0003\u0005\u0002`\u0006eB\u0011AD\u0002\u0011!9I!!\u000f\u0005\n\u001d-\u0001\u0002CD\t\u0003s!Iab\u0005\t\u0011\u001de\u0011\u0011\bC\u0005\u0007\u007f;\u0011bb\b\u0002\u0011\u0003\t)m\"\t\u0007\u0013\te\u0013\u0001#\u0001\u0002F\u001e\r\u0002\u0002CAp\u0003\u0017\"\ta\"\n\t\u0015\u001d\u001d\u00121\nb\u0001\n\u00139I\u0003C\u0005\bB\u0005-\u0003\u0015!\u0003\b,!A\u00111]A&\t\u00039\u0019EB\u0006\u0003Z\u0005\u0001\n1%\t\u0002F\nm\u0003\u0002\u0003B/\u0003+2\tAa\u0018\t\u0011\t\u0005\u0014Q\u000bD\u0001\u0005G2\u0001\"a4\u0002B\u0006\u0005\u0012\u0011\u001e\u0005\t\u0003?\fY\u0006\"\u0001\u0003\u0010!A!\u0011CA.\t#\u0012\u0019\u0002\u0003\u0005\u00032\u0005mC\u0011\u000bB\u001a\u0011!\u0011\t%a\u0017\u0005B\t\r\u0003\u0002CAr\u000372\tA!\u0012\t\u0015\tE\u00131\fD\u0001\u0003\u000b\u0014\u0019\u0006\u0003\u0005\u00062\u0005mCQIC\u001a\u0011!))$a\u0017\u0005B\t%\b\u0002\u0003B]\u00037\"\t%b\u000e\t\u0011\u0015}\u00121\fC!\u0005?B\u0001\"\"\u0011\u0002\\\u0011\u0005#1\t\u0005\t\u000b\u0007\nY\u0006\"\u0011\u0003`!AQQIA.\t\u0003\u0012\u0019\u0005\u0003\u0005\u0004\u000e\u0005mC\u0011IC$\u0011!)Y%a\u0017\u0005B\u00155\u0003\u0002CB\r\u00037\"\t%\"\u0015\t\u0011\r\u0005\u00111\fC!\u000b/B\u0001Ba=\u0002\\\u0011\u0005S1\f\u0005\t\u000b?\nY\u0006\"\u0011\u0006b!AQQNA.\t\u0003*y\u0007\u0003\u0005\u0006t\u0005mC\u0011IC;\u0011!)y(a\u0017\u0005B\u0015\u0005\u0005\u0002CCC\u00037\"\t%b\"\t\u0011\r%\u00151\fC!\u000b\u001bC\u0001\"\"'\u0002\\\u0011\u0005S1\u0014\u0005\t\u000bO\u000bY\u0006\"\u0011\u0006*\"QQ1VA.\t#\tYF!'\t\u0011\u0015-\u00161\fC#\u000b[C\u0001b!*\u0002\\\u0011\u0015Sq\u0019\u0005\t\u0007K\u000bY\u0006\"\u0011\u0006X\"A1\u0011YA.\t\u0003\u0019\u0019\r\u0003\u0005\u0006h\u0006mC\u0011ICu\u0011)\u0011\u0019.a\u0017\u0007\u0002\u0005\u0015g\u0011\u0002\u0005\t\u0007\u0003\u000bYF\"\u0001\u0007\u000e!Aa\u0011CA.\t\u00031\u0019\u0002\u0003\u0005\u0004&\u0005mc\u0011\u0001D\f\u0011!\u0019y$a\u0017\u0007\u0002\r\u0005\u0003\u0002\u0003Bt\u000372\tA!;\t\u0011\r%\u00131\fD\u0001\u0007\u0017B\u0001b!\u0014\u0002\\\u0019\u00051q\n\u0005\t\rG\tY\u0006\"\u0001\u0007&!Aa1FA.\t\u0003\u0019Y\u0005\u0003\u0005\u0007.\u0005mCQAC\u001a\u0011!\u00199&a\u0017\u0007\u0002\u0019=\u0002\u0002CB,\u000372\tAb\r\t\u0011\ru\u00141\fD\u0001\u0005\u0007B\u0001ba \u0002\\\u0019\u0005!1\t\u0005\t\ro\tY\u0006\"\u0002\u0007:!AaqHA.\t\u00031\t%\u0001\u0006CsR,7\u000b\u001e:j]\u001eTA!a1\u0002F\u0006!Q\u000f^5m\u0015\t\t9-\u0001\u0003bW.\f7\u0001\u0001\t\u0004\u0003\u001b\fQBAAa\u0005)\u0011\u0015\u0010^3TiJLgnZ\n\u0004\u0003\u0005M\u0007\u0003BAk\u00037l!!a6\u000b\u0005\u0005e\u0017!B:dC2\f\u0017\u0002BAo\u0003/\u0014a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\b\u0006\u0002\u0002L\u0006)\u0011\r\u001d9msR!\u0011q\u001dD(!\u0011\ti-a\u0017\u0014\u0015\u0005m\u00131[Av\u0005\u0003\u0011I\u0001\u0005\u0004\u0002n\u0006]\u00181`\u0007\u0003\u0003_TA!!=\u0002t\u0006I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0005\u0003k\f9.\u0001\u0006d_2dWm\u0019;j_:LA!!?\u0002p\nQ\u0011J\u001c3fq\u0016$7+Z9\u0011\t\u0005U\u0017Q`\u0005\u0005\u0003\u007f\f9N\u0001\u0003CsR,\u0007CCAw\u0005\u0007\tYPa\u0002\u0002h&!!QAAx\u00055Ie\u000eZ3yK\u0012\u001cV-](qgB!\u0011Q^A|!)\tiOa\u0003\u0002|\n\u001d\u0011q]\u0005\u0005\u0005\u001b\tyOA\u000bTiJL7\r^(qi&l\u0017N_3e'\u0016\fx\n]:\u0015\u0005\u0005\u001d\u0018\u0001\u00044s_6\u001c\u0006/Z2jM&\u001cG\u0003BAt\u0005+A\u0001Ba\u0006\u0002`\u0001\u0007!\u0011D\u0001\u0005G>dG\u000e\u0005\u0004\u0003\u001c\t-\u00121 \b\u0005\u0005;\u00119C\u0004\u0003\u0003 \t\u0015RB\u0001B\u0011\u0015\u0011\u0011\u0019#!3\u0002\rq\u0012xn\u001c;?\u0013\t\tI.\u0003\u0003\u0003*\u0005]\u0017a\u00029bG.\fw-Z\u0005\u0005\u0005[\u0011yC\u0001\u0007Ji\u0016\u0014\u0018M\u00197f\u001f:\u001cWM\u0003\u0003\u0003*\u0005]\u0017A\u00058foN\u0003XmY5gS\u000e\u0014U/\u001b7eKJ,\"A!\u000e\u0011\u0011\t]\"QHA~\u0003Ol!A!\u000f\u000b\t\tm\u00121_\u0001\b[V$\u0018M\u00197f\u0013\u0011\u0011yD!\u000f\u0003\u000f\t+\u0018\u000e\u001c3fe\u0006)Q-\u001c9usV\u0011\u0011q\u001d\u000b\u0005\u0003w\u00149\u0005\u0003\u0005\u0003J\u0005\u0015\u0004\u0019\u0001B&\u0003\rIG\r\u001f\t\u0005\u0003+\u0014i%\u0003\u0003\u0003P\u0005]'aA%oi\u0006\u0019\"-\u001f;f'R\u0014\u0018N\\4D_6\u0004\u0018M\\5p]V\u0011!Q\u000b\t\u0005\u0005/\n)FD\u0002\u0002N\u0002\u0011\u0011bQ8na\u0006t\u0017n\u001c8\u0014\t\u0005U\u00131[\u0001\u0016'\u0016\u0014\u0018.\u00197ju\u0006$\u0018n\u001c8JI\u0016tG/\u001b;z+\t\tY0A\nsK\u0006$gI]8n\u0013:\u0004X\u000f^*ue\u0016\fW\u000e\u0006\u0003\u0002h\n\u0015\u0004\u0002\u0003B4\u00033\u0002\rA!\u001b\u0002\u0005%\u001c\b\u0003\u0002B6\u0005kj!A!\u001c\u000b\t\t=$\u0011O\u0001\u0003S>T!Aa\u001d\u0002\t)\fg/Y\u0005\u0005\u0005o\u0012iGA\tPE*,7\r^%oaV$8\u000b\u001e:fC6LS!!\u0016C9E\u00141BQ=uKN#(/\u001b8hcM9!)a5\u0003\u0000\t\r\u0005\u0003\u0002BA\u0003+j\u0011!\u0001\t\u0005\u0005W\u0012))\u0003\u0003\u0003\b\n5$\u0001D*fe&\fG.\u001b>bE2,GC\u0001BF!\r\u0011\tIQ\u000b\u0003\u0005\u001f\u00032A!!N'\u0015i\u0015q\u001dBJ!\u0011\u0011YB!&\n\t\t\u001d%qF\u0001\u0006Ef$Xm]\u000b\u0003\u00057\u0003b!!6\u0003\u001e\u0006m\u0018\u0002\u0002BP\u0003/\u0014Q!\u0011:sCf\faAY=uKN\u0004\u0013AC:uCJ$\u0018J\u001c3fqV\u0011!1J\u0001\fgR\f'\u000f^%oI\u0016D\b%\u0001\u0004mK:<G\u000f[\u0001\bY\u0016tw\r\u001e5!)!\u0011yIa,\u00032\nM\u0006b\u0002BL)\u0002\u0007!1\u0014\u0005\b\u0005G#\u0006\u0019\u0001B&\u0011\u001d\u0011I\u000b\u0016a\u0001\u0005\u0017\"B!a?\u00038\"9!\u0011J+A\u0002\t-\u0013\u0001C5uKJ\fGo\u001c:\u0016\u0005\tu\u0006\u0003\u0002B`\u0005\u000btA!!4\u0003B&!!1YAa\u00031\u0011\u0015\u0010^3Ji\u0016\u0014\u0018\r^8s\u0013\u0011\u00119M!3\u0003#\tKH/Z!se\u0006L\u0018\n^3sCR|'O\u0003\u0003\u0003D\u0006\u0005\u0017!E2iK\u000e\\'+\u00198hK\u000e{gN^3siR!!1\nBh\u0011\u001d\u0011\tn\u0016a\u0001\u0005\u0017\nQ!\u001b8eKb\f1c\u001e:ji\u0016$vnT;uaV$8\u000b\u001e:fC6$BAa6\u0003^B!\u0011Q\u001bBm\u0013\u0011\u0011Y.a6\u0003\tUs\u0017\u000e\u001e\u0005\b\u0005?D\u0006\u0019\u0001Bq\u0003\ty7\u000f\u0005\u0003\u0003l\t\r\u0018\u0002\u0002Bs\u0005[\u0012!c\u00142kK\u000e$x*\u001e;qkR\u001cFO]3b[\u0006I\u0011n]\"p[B\f7\r^\u000b\u0003\u0005W\u0004B!!6\u0003n&!!q^Al\u0005\u001d\u0011un\u001c7fC:,\"Aa#\u0002\u0013\u0011\u0014x\u000e\u001d*jO\"$H\u0003BAt\u0005oDqA!?\\\u0001\u0004\u0011Y%A\u0001o\u0003)!'o\u001c9SS\u001eDG/\r\u000b\u0005\u0005\u001f\u0013y\u0010C\u0004\u0003zr\u0003\rAa\u0013\u0002\t\u0011\u0014x\u000e\u001d\u000b\u0005\u0003O\u001c)\u0001C\u0004\u0003zv\u0003\rAa\u0013\u0002\u000b\u0011\u0014x\u000e]\u0019\u0015\t\t=51\u0002\u0005\b\u0005st\u0006\u0019\u0001B&\u0003\u0011!\u0018m[3\u0015\t\u0005\u001d8\u0011\u0003\u0005\b\u0005s|\u0006\u0019\u0001B&\u0003\u0015!\u0018m[32)\u0011\u0011yia\u0006\t\u000f\te\b\r1\u0001\u0003L\u0005)1\u000f\\5dKR1\u0011q]B\u000f\u0007CAqaa\bb\u0001\u0004\u0011Y%\u0001\u0003ge>l\u0007bBB\u0012C\u0002\u0007!1J\u0001\u0006k:$\u0018\u000e\\\u0001\rG>\u0004\u0018\u0010V8Ck\u001a4WM\u001d\u000b\u0005\u0005\u0017\u001aI\u0003C\u0004\u0004,\t\u0004\ra!\f\u0002\r\t,hMZ3s!\u0011\u0019yc!\u000e\u000e\u0005\rE\"\u0002BB\u001a\u0005c\n1A\\5p\u0013\u0011\u00199d!\r\u0003\u0015\tKH/\u001a\"vM\u001a,'/A\u0007xe&$X\rV8Ck\u001a4WM\u001d\u000b\u0005\u0005\u0017\u001ai\u0004C\u0004\u0004,\r\u0004\ra!\f\u0002\u000f\r|W\u000e]1diV\u001111\t\t\u0005\u0003\u001b\u001c)%\u0003\u0003\u0004H\u0005\u0005'!E\"p[B\f7\r\u001e\"zi\u0016\u001cFO]5oO\u0006a\u0011m\u001d\"zi\u0016\u0014UO\u001a4feV\u00111QF\u0001\u000eCN\u0014\u0015\u0010^3Ck\u001a4WM]:\u0016\u0005\rE\u0003CBAw\u0007'\u001ai#\u0003\u0003\u0004V\u0005=(\u0001C%uKJ\f'\r\\3\u0002\u0019\u0011,7m\u001c3f'R\u0014\u0018N\\4\u0015\t\rm31\u000e\t\u0005\u0007;\u001a)G\u0004\u0003\u0004`\r\u0005\u0004\u0003\u0002B\u0010\u0003/LAaa\u0019\u0002X\u00061\u0001K]3eK\u001aLAaa\u001a\u0004j\t11\u000b\u001e:j]\u001eTAaa\u0019\u0002X\"91QN4A\u0002\rm\u0013aB2iCJ\u001cX\r\u001e\u000b\u0005\u00077\u001a\t\bC\u0004\u0004n!\u0004\raa\u001d\u0011\t\rU4\u0011P\u0007\u0003\u0007oRAa!\u001c\u00042%!11PB<\u0005\u001d\u0019\u0005.\u0019:tKR\fA\u0002Z3d_\u0012,')Y:fmQ\nA\"\u001a8d_\u0012,')Y:fmQ\n!\u0002\n9mkN$\u0003\u000f\\;t)\u0011\t9o!\"\t\u000f\r\u001d5\u000e1\u0001\u0002h\u0006!A\u000f[1u\u0003\u001dIg\u000eZ3y\u001f\u001a,Ba!$\u0004\u0018R1!1JBH\u0007GCqa!%m\u0001\u0004\u0019\u0019*\u0001\u0003fY\u0016l\u0007\u0003BBK\u0007/c\u0001\u0001B\u0004\u0004\u001a2\u0014\raa'\u0003\u0003\t\u000bB!a?\u0004\u001eB!\u0011Q[BP\u0013\u0011\u0019\t+a6\u0003\u0007\u0005s\u0017\u0010C\u0004\u0004 1\u0004\rAa\u0013\u0002\u0017\r|\u0007/\u001f+p\u0003J\u0014\u0018-_\u000b\u0005\u0007S\u001b\u0019\f\u0006\u0005\u0003L\r-6QWB]\u0011\u001d\u0019i+\u001ca\u0001\u0007_\u000bA\u0001Z3tiB1\u0011Q\u001bBO\u0007c\u0003Ba!&\u00044\u001291\u0011T7C\u0002\rm\u0005bBB\\[\u0002\u0007!1J\u0001\u0006gR\f'\u000f\u001e\u0005\b\u0007wk\u0007\u0019\u0001B&\u0003\raWM\\\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003'\fQ\u0002^8BeJ\f\u00170\u00168tC\u001a,GC\u0001BN\u0003\u0019)W\u000e\u001d;zA\u0005QaM]8n'R\u0014\u0018N\\4\u0015\t\t=51\u001a\u0005\b\u0007\u001b4\u0005\u0019AB.\u0003\u0005\u0019H\u0003\u0002BH\u0007#DqAa&H\u0001\u0004\u0011Y\n\u0006\u0005\u0003\u0010\u000eU7q[Bm\u0011\u001d\u00119\n\u0013a\u0001\u00057CqAa)I\u0001\u0004\u0011Y\u0005C\u0004\u0003*\"\u0003\rAa\u0013\u0002-M+'/[1mSj\fG/[8o\u0013\u0012,g\u000e^5us\u0002\"BAa$\u0004`\"9!qM&A\u0002\t%DCABr!\u0011\u0019)oa;\u000e\u0005\r\u001d(\u0002BBu\u0005c\nA\u0001\\1oO&!1Q^Bt\u0005\u0019y%M[3di\na!)\u001f;f'R\u0014\u0018N\\42\u0007N9A$a5\u0003\u0000\t\rECAB{!\r\u0011\t\tH\u000b\u0003\u0007s\u00042A!!''\r131\t\u000b\u0005\u0007s\u001cy\u0010C\u0004\u0003\u0018&\u0002\rAa'\u0015\t\u0005mH1\u0001\u0005\b\u0005\u0013R\u0003\u0019\u0001B&\u00035!xNQ=uKN#(/\u001b8hcU\u00111Q\u001f\u000b\u0005\u00077\"Y\u0001C\u0004\u0004nE\u0002\raa\u0017\u0015\t\rmCq\u0002\u0005\b\u0007[\u0012\u0004\u0019AB:)\u0011\t9\u000fb\u0005\t\u000f\r\u001dU\u00071\u0001\u0002hR!\u0011q\u001dC\f\u0011\u001d\u0011IP\u000ea\u0001\u0005\u0017\"B!a:\u0005\u001c!9!\u0011`\u001cA\u0002\t-C\u0003BAt\t?AqA!?9\u0001\u0004\u0011Y%\u0006\u0003\u0005$\u0011%BC\u0002B&\tK!Y\u0003C\u0004\u0004\u0012f\u0002\r\u0001b\n\u0011\t\rUE\u0011\u0006\u0003\b\u00073K$\u0019ABN\u0011\u001d\u0019y\"\u000fa\u0001\u0005\u0017\"b!a:\u00050\u0011E\u0002bBB\u0010u\u0001\u0007!1\n\u0005\b\u0007GQ\u0004\u0019\u0001B&)\u0011\u00119\u000e\"\u000e\t\u000f\t}7\b1\u0001\u0003bR!!1\nC\u001d\u0011\u001d\u0019Y\u0003\u0010a\u0001\u0007[!bAa\u0013\u0005>\u0011}\u0002bBB\u0016{\u0001\u00071Q\u0006\u0005\b\t\u0003j\u0004\u0019\u0001B&\u0003\u0019ygMZ:fi\u0006y\u0011\r\u001d9f]\u0012$vNQ;jY\u0012,'\u000f\u0006\u0003\u0005H\u00115\u0003\u0003BAg\t\u0013JA\u0001b\u0013\u0002B\n\t\")\u001f;f'R\u0014\u0018N\\4Ck&dG-\u001a:\t\u000f\r-b\b1\u0001\u0005HU!A\u0011\u000bC-)!\u0011Y\u0005b\u0015\u0005\\\u0011u\u0003bBBW\u007f\u0001\u0007AQ\u000b\t\u0007\u0003+\u0014i\nb\u0016\u0011\t\rUE\u0011\f\u0003\b\u00073{$\u0019ABN\u0011\u001d\u00199l\u0010a\u0001\u0005\u0017Bqaa/@\u0001\u0004\u0011Y\u0005K\u0004'\tC\"9\u0007\"\u001b\u0011\t\u0005UG1M\u0005\u0005\tK\n9N\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKzAa';xQ`?'O\u0015\u0006\u0003\u0004z\u00125\u0004bBBgA\u0001\u000711\f\u000b\u0005\u0007s$\t\bC\u0004\u0003\u0018\u0006\u0002\rAa'\u0015\t\reHQ\u000f\u0005\b\u0005O\"\u0003\u0019\u0001B5\u0005-\u0011\u0015\u0010^3TiJLgnZ:\u0014\u000fE\f\u0019Na \u0003\u0004R\u0011AQ\u0010\t\u0004\u0005\u0003\u000bH\u0003BAt\t\u0003Cq\u0001b!t\u0001\u0004!))A\u0006csR,7\u000f\u001e:j]\u001e\u001c\bC\u0002B\u000e\t\u000f\u0013y)\u0003\u0003\u0005\n\n=\"A\u0002,fGR|'\u000f\u0006\u0004\u0002h\u00125Eq\u0012\u0005\b\t\u0007#\b\u0019\u0001CC\u0011\u001d\u0011I\u000b\u001ea\u0001\u0005\u0017\"b!a:\u0005\u0014\u0012]\u0005b\u0002CKk\u0002\u0007!qR\u0001\u0003EFBq\u0001\"'v\u0001\u0004\u0011y)\u0001\u0002ceQ1\u0011q\u001dCO\tCCq\u0001b(w\u0001\u0004\u0011y)A\u0001c\u0011\u001d!\u0019K\u001ea\u0001\tK\u000b!AY:\u0011\u0007\t\u0005epE\u0003\u007f\u0003O\u0014\u0019*\u0006\u0002\u0005\u0006\u0006a!-\u001f;fgR\u0014\u0018N\\4tAQ1AQ\u0015CX\tcC\u0001\u0002b!\u0002\b\u0001\u0007AQ\u0011\u0005\t\u0005S\u000b9\u00011\u0001\u0003LQ!\u00111 C[\u0011!\u0011I%!\u0003A\u0002\t-SC\u0001C]!\u0011\u0011y\fb/\n\t\u0011u&\u0011\u001a\u0002\u0017\u001bVdG/\u001b\"zi\u0016\f%O]1z\u0013R,'/\u0019;peR!\u0011q\u001dCa\u0011!\u00199)!\u0004A\u0002\u0005\u001dXC\u0001C?)\u0011\u0011Y\u0005b2\t\u0011\r-\u00121\u0003a\u0001\u0007[!Baa\u0017\u0005L\"A1QNA\u000e\u0001\u0004\u0019Y\u0006\u0006\u0003\u0004\\\u0011=\u0007\u0002CB7\u0003;\u0001\raa\u001d\u0015\t\t]G1\u001b\u0005\t\u0005?\f\u0019\u00031\u0001\u0003bR!\u0011q\u001dCl\u0011!\u0011I0!\nA\u0002\t-\u0013!\u0002;bW\u0016\u0004D\u0003BAt\t;D\u0001B!?\u0002(\u0001\u0007!1\n\u000b\u0005\u0003O$\t\u000f\u0003\u0005\u0003z\u0006%\u0002\u0019\u0001B&\u0003)!'o\u001c9SS\u001eDG\u000f\r\u000b\u0005\u0003O$9\u000f\u0003\u0005\u0003z\u0006-\u0002\u0019\u0001B&)\u0019\t9\u000fb;\u0005n\"A1qDA\u0017\u0001\u0004\u0011Y\u0005\u0003\u0005\u0004$\u00055\u0002\u0019\u0001B&)\u0011\t9\u000f\"=\t\u0011\te\u0018q\u0006a\u0001\u0005\u0017\nQ\u0001\u001a:paB\"B!a:\u0005x\"A!\u0011`A\u0019\u0001\u0004\u0011Y%\u0006\u0003\u0005|\u0016\u0005AC\u0002B&\t{,\u0019\u0001\u0003\u0005\u0004\u0012\u0006M\u0002\u0019\u0001C\u0000!\u0011\u0019)*\"\u0001\u0005\u0011\re\u00151\u0007b\u0001\u00077C\u0001ba\b\u00024\u0001\u0007!1J\u000b\u0005\u000b\u000f)y\u0001\u0006\u0005\u0003L\u0015%Q\u0011CC\n\u0011!\u0019i+!\u000eA\u0002\u0015-\u0001CBAk\u0005;+i\u0001\u0005\u0003\u0004\u0016\u0016=A\u0001CBM\u0003k\u0011\raa'\t\u0011\r]\u0016Q\u0007a\u0001\u0005\u0017B\u0001ba/\u00026\u0001\u0007!1\n\u000b\u0007\u0003O,9\"\"\u0007\t\u000f\u0011\rv\u000f1\u0001\u0005&\"9AqT<A\u0002\t=ECBAt\u000b;)\t\u0003C\u0004\u0006 a\u0004\r\u0001\"*\u0002\u0007\t\u001c\u0018\u0007C\u0004\u0006$a\u0004\r\u0001\"*\u0002\u0007\t\u001c('A\u0004d_6\u0004\u0018M]3\u0015\r\t-S\u0011FC\u0016\u0011\u001d!)*\u001fa\u0001\u0003ODq\u0001\"'z\u0001\u0004\t9\u000f\u0006\u0003\u0005&\u0016=\u0002b\u0002B4y\u0002\u0007!\u0011N\u0001\nG2\f7o\u001d(b[\u0016,\"aa\u0017\u0002\u000f%\u001cX)\u001c9usV\u0011Q\u0011\b\t\u0005\u0003\u001b,Y$\u0003\u0003\u0006>\u0005\u0005'\u0001\u0004\"zi\u0016LE/\u001a:bi>\u0014\u0018\u0001\u00025fC\u0012\fA\u0001^1jY\u0006!A.Y:u\u0003\u0011Ig.\u001b;\u0015\t\u0005\u001dX\u0011\n\u0005\t\u0005s\f9\b1\u0001\u0003L\u0005IA/Y6f%&<\u0007\u000e\u001e\u000b\u0005\u0003O,y\u0005\u0003\u0005\u0003z\u0006e\u0004\u0019\u0001B&)\u0019\t9/b\u0015\u0006V!A1qDA>\u0001\u0004\u0011Y\u0005\u0003\u0005\u0004$\u0005m\u0004\u0019\u0001B&)\u0011\t9/\"\u0017\t\u0011\te\u0018Q\u0010a\u0001\u0005\u0017\"B!a:\u0006^!A!\u0011`A@\u0001\u0004\u0011Y%A\u0005uC.,w\u000b[5mKR!\u0011q]C2\u0011!))'!!A\u0002\u0015\u001d\u0014!\u00019\u0011\u0011\u0005UW\u0011NA~\u0005WLA!b\u001b\u0002X\nIa)\u001e8di&|g.M\u0001\nIJ|\u0007o\u00165jY\u0016$B!a:\u0006r!AQQMAB\u0001\u0004)9'\u0001\u0003ta\u0006tG\u0003BC<\u000b{\u0002\u0002\"!6\u0006z\u0005\u001d\u0018q]\u0005\u0005\u000bw\n9N\u0001\u0004UkBdWM\r\u0005\t\u000bK\n)\t1\u0001\u0006h\u000591\u000f\u001d7ji\u0006#H\u0003BC<\u000b\u0007C\u0001B!?\u0002\b\u0002\u0007!1J\u0001\u000bS:$W\r_,iKJ,GC\u0002B&\u000b\u0013+Y\t\u0003\u0005\u0006f\u0005%\u0005\u0019AC4\u0011!\u0019y\"!#A\u0002\t-S\u0003BCH\u000b+#bAa\u0013\u0006\u0012\u0016]\u0005\u0002CBI\u0003\u0017\u0003\r!b%\u0011\t\rUUQ\u0013\u0003\t\u00073\u000bYI1\u0001\u0004\u001c\"A1qDAF\u0001\u0004\u0011Y%A\u0004he>,\b/\u001a3\u0015\t\u0015uU1\u0015\t\u0007\u00057)y*a:\n\t\u0015\u0005&q\u0006\u0002\t\u0013R,'/\u0019;pe\"AQQUAG\u0001\u0004\u0011Y%\u0001\u0003tSj,\u0017\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\rm\u0013a\u0002;p\u0003J\u0014\u0018-_\u000b\u0005\u000b_+)\f\u0006\u0003\u00062\u0016]\u0006CBAk\u0005;+\u0019\f\u0005\u0003\u0004\u0016\u0016UF\u0001CBM\u0003'\u0013\raa'\t\u0011\u0015e\u00161\u0013a\u0002\u000bw\u000bA!\u0019:haA1QQXCb\u000bgk!!b0\u000b\t\u0015\u0005\u0017q[\u0001\be\u00164G.Z2u\u0013\u0011))-b0\u0003\u0011\rc\u0017m]:UC\u001e,B!\"3\u0006TR1!1JCf\u000b+D\u0001\"\"4\u0002\u0016\u0002\u0007QqZ\u0001\u0003qN\u0004b!!6\u0003\u001e\u0016E\u0007\u0003BBK\u000b'$\u0001b!'\u0002\u0016\n\u000711\u0014\u0005\t\u0007o\u000b)\n1\u0001\u0003LU!Q\u0011\\Cq)!\u0011Y%b7\u0006d\u0016\u0015\b\u0002CCg\u0003/\u0003\r!\"8\u0011\r\u0005U'QTCp!\u0011\u0019)*\"9\u0005\u0011\re\u0015q\u0013b\u0001\u00077C\u0001ba.\u0002\u0018\u0002\u0007!1\n\u0005\t\u0007w\u000b9\n1\u0001\u0003L\u00059am\u001c:fC\u000eDW\u0003BCv\u000bk$BAa6\u0006n\"AQq^AN\u0001\u0004)\t0A\u0001g!!\t).\"\u001b\u0002|\u0016M\b\u0003BBK\u000bk$A\"b>\u0002\u001c\u0002\u0006\t\u0011!b\u0001\u000bs\u0014\u0011!V\t\u0005\u000bw\u001ci\n\u0005\u0003\u0002V\u0016u\u0018\u0002BC\u0000\u0003/\u0014qAT8uQ&tw\r\u000b\u0003\u0006v\u001a\r\u0001\u0003BAk\r\u000bIAAb\u0002\u0002X\nY1\u000f]3dS\u0006d\u0017N_3e)\u0011\u00119Nb\u0003\t\u0011\t}\u0017Q\u0014a\u0001\u0005C$B!a:\u0007\u0010!A1qQAP\u0001\u0004\t9/\u0001\u0004d_:\u001c\u0017\r\u001e\u000b\u0005\u0003O4)\u0002\u0003\u0005\u0004\b\u0006\u0005\u0006\u0019AAt)\u0011\u0011YE\"\u0007\t\u0011\r-\u00121\u0015a\u0001\u0007[ACA\"\u0007\u0007\u001eA!\u0011Q\u001aD\u0010\u0013\u00111\t#!1\u0003\rUtWo]3e\u000399W\r\u001e\"zi\u0016\u0014UO\u001a4feN$\"Ab\n\u0011\r\r\u0015h\u0011FB\u0017\u0013\u0011\u0019)fa:\u0002\u0019Q|')\u001f;f\u0005V4g-\u001a:\u0002\u0015U$h\rO*ue&tw\r\u0006\u0003\u0004\\\u0019E\u0002\u0002CB7\u0003g\u0003\raa\u0017\u0015\t\rmcQ\u0007\u0005\t\u0007[\n)\f1\u0001\u0004t\u0005!Q.\u00199J)\u0011\t9Ob\u000f\t\u0011\u0015=\u00181\u0018a\u0001\r{\u0001\u0002\"!6\u0006j\u0005m(1J\u0001\u0004[\u0006\u0004X\u0003\u0002D\"\r\u0013\"B!a:\u0007F!AQq^A_\u0001\u000419\u0005\u0005\u0005\u0002V\u0016%\u00141`A~\t!1Y%!0C\u0002\u0015e(!A!*\r\u0005mSJ`B#\u0011\u001d\u00119j\u0001a\u0001\u00057#B!a:\u0007T!9!q\u0013\u0003A\u0002\u0019U\u0003CBAk\r/\nY0\u0003\u0003\u0007Z\u0005]'A\u0003\u001fsKB,\u0017\r^3e}Q!\u0011q\u001dD/\u0011\u001d\u00119*\u0002a\u0001\u00053)BA\"\u0019\u0007rQ!a1\rD;)\u0011\t9O\"\u001a\t\u000f\u0019\u001dd\u0001q\u0001\u0007j\u0005\u0019a.^7\u0011\r\tma1\u000eD8\u0013\u00111iGa\f\u0003\u0011%sG/Z4sC2\u0004Ba!&\u0007r\u00119a1\u000f\u0004C\u0002\u0015e(!\u0001+\t\u000f\t]e\u00011\u0001\u0007xA1\u0011Q\u001bD,\r_\"B!a:\u0007|!9!qS\u0004A\u0002\r5B\u0003BAt\r\u007fBqA\"!\t\u0001\u0004\u0019Y&\u0001\u0004tiJLgn\u001a\u000b\u0007\u0003O4)Ib\"\t\u000f\u0019\u0005\u0015\u00021\u0001\u0004\\!91QN\u0005A\u0002\rmCCBAt\r\u00173i\tC\u0004\u0007\u0002*\u0001\raa\u0017\t\u000f\r5$\u00021\u0001\u0004t\u0005IaM]8n\u0003J\u0014\u0018-\u001f\u000b\u0005\u0003O4\u0019\nC\u0004\u0007\u0016.\u0001\rAa'\u0002\u000b\u0005\u0014(/Y=\u0002\u001f\u0019\u0014x.\\!se\u0006LXK\\:bM\u0016$B!a:\u0007\u001c\"9aQ\u0013\u0007A\u0002\tmE\u0003CAt\r?3\tKb)\t\u000f\u0019UU\u00021\u0001\u0003\u001c\"9A\u0011I\u0007A\u0002\t-\u0003b\u0002BU\u001b\u0001\u0007!1\n\u000b\t\u0003O49K\"+\u0007,\"9aQ\u0013\bA\u0002\tm\u0005b\u0002C!\u001d\u0001\u0007!1\n\u0005\b\u0005Ss\u0001\u0019\u0001B&\u0003!1'o\\7J]R\u001cH\u0003BAt\rcCqA\"&\u0010\u0001\u00041\u0019\f\u0005\u0004\u0002V\u001a]#1\n\u0015\u0004\u001f\u0019]\u0006\u0003\u0002D]\r\u007fk!Ab/\u000b\t\u0019u\u0016q[\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002\u0002Da\rw\u0013qA^1sCJ<7\u000f\u0006\u0003\u0002h\u001a\u0015\u0007b\u0002DA!\u0001\u000711\f\u000b\u0007\u0003O4IMb3\t\u000f\u0019\u0005\u0015\u00031\u0001\u0004\\!91QN\tA\u0002\rmCCBAt\r\u001f4\t\u000eC\u0004\u0007\u0002J\u0001\raa\u0017\t\u000f\r5$\u00031\u0001\u0004t\u0005)Q\u000b\u0016$`q\u00051Q\u000b\u0016$`q\u0001\naB\u001a:p[\nKH/\u001a\"vM\u001a,'\u000f\u0006\u0003\u0002h\u001am\u0007bBB\u0016+\u0001\u00071QF\u0001\u0010K6\u0004H/\u001f\"zi\u0016\u001cFO]5oO\u0006Qa.Z<Ck&dG-\u001a:\u0016\u0005\u0011\u001d\u0013!D2sK\u0006$XMQ;jY\u0012,'/\u0001\u0007CsR,7\u000b\u001e:j]\u001e\f4)A\u0006CsR,7\u000b\u001e:j]\u001e\f\u0014a\u0003\"zi\u0016\u001cFO]5oON\u0014!cU3sS\u0006d\u0017N_1uS>t\u0007K]8ysN1\u0011\u0011HAj\u0005'\u000bAa\u001c:jO\u0006AqN]5h?\u0012*\u0017\u000f\u0006\u0003\u0003X\u001aU\bB\u0003D|\u0003{\t\t\u00111\u0001\u0002h\u0006\u0019\u0001\u0010J\u0019\u0002\u000b=\u0014\u0018n\u001a\u0011)\t\u0005}bQ \t\u0005\u0003+4y0\u0003\u0003\b\u0002\u0005]'!\u0003;sC:\u001c\u0018.\u001a8u)\u00119)ab\u0002\u0011\t\t\u0005\u0015\u0011\b\u0005\t\r_\f\t\u00051\u0001\u0002h\u0006YqO]5uK>\u0013'.Z2u)\u0011\u00119n\"\u0004\t\u0011\u001d=\u00111\ta\u0001\u0005C\f1a\\;u\u0003)\u0011X-\u00193PE*,7\r\u001e\u000b\u0005\u0005/<)\u0002\u0003\u0005\b\u0018\u0005\u0015\u0003\u0019\u0001B5\u0003\tIg.A\u0006sK\u0006$'+Z:pYZ,\u0007\u0006CA\u001d\tC\"9g\"\b\u001f\u0003\u0005\t\u0011bQ8na\u0006t\u0017n\u001c8\u0011\t\t\u0005\u00151J\n\u0005\u0003\u0017\n\u0019\u000e\u0006\u0002\b\"\u0005a1m\\7qC:LwN\\'baV\u0011q1\u0006\t\t\u0003[<i#a?\b2%!qqFAx\u0005\ri\u0015\r\u001d\n\u0007\u000fg\u0011yHa!\u0007\r\u001dU\u0012\u0001AD\u0019\u00051a$/\u001a4j]\u0016lWM\u001c;?\u0011!\u0011\tgb\r\u0007\u0002\u001deB\u0003BD\u001e\u000f\u007f\u0011ba\"\u0010\u0002h\n\reABD\u001b\u0003\u00019Y\u0004\u0003\u0005\u0003h\u001d]\u0002\u0019\u0001B5\u00035\u0019w.\u001c9b]&|g.T1qAQ!!qPD#\u0011!\u0019y\"a\u0015A\u0002\u0005m\b")
public abstract class ByteString
implements scala.collection.immutable.IndexedSeq<Object>,
scala.collection.immutable.StrictOptimizedSeqOps<Object, scala.collection.immutable.IndexedSeq, ByteString> {
    public static ByteString fromInts(int ... array) {
        return ByteString$.MODULE$.fromInts(array);
    }

    public static ByteStringBuilder createBuilder() {
        return ByteString$.MODULE$.createBuilder();
    }

    public static ByteStringBuilder newBuilder() {
        return ByteString$.MODULE$.newBuilder();
    }

    public static ByteString emptyByteString() {
        return ByteString$.MODULE$.emptyByteString();
    }

    public static ByteString fromByteBuffer(ByteBuffer buffer) {
        return ByteString$.MODULE$.fromByteBuffer(buffer);
    }

    public static String UTF_8() {
        return ByteString$.MODULE$.UTF_8();
    }

    public static ByteString fromString(String string, Charset charset) {
        return ByteString$.MODULE$.fromString(string, charset);
    }

    public static ByteString fromString(String string, String charset) {
        return ByteString$.MODULE$.fromString(string, charset);
    }

    public static ByteString fromString(String string) {
        return ByteString$.MODULE$.fromString(string);
    }

    public static ByteString fromInts(scala.collection.immutable.Seq<Object> array) {
        return ByteString$.MODULE$.fromInts(array);
    }

    public static ByteString fromArrayUnsafe(byte[] array, int offset, int length) {
        return ByteString$.MODULE$.fromArrayUnsafe(array, offset, length);
    }

    public static ByteString fromArray(byte[] array, int offset, int length) {
        return ByteString$.MODULE$.fromArray(array, offset, length);
    }

    public static ByteString fromArrayUnsafe(byte[] array) {
        return ByteString$.MODULE$.fromArrayUnsafe(array);
    }

    public static ByteString fromArray(byte[] array) {
        return ByteString$.MODULE$.fromArray(array);
    }

    @Override
    public /* synthetic */ Object scala$collection$immutable$StrictOptimizedSeqOps$$super$sorted(Ordering ord) {
        return SeqOps.sorted$(this, ord);
    }

    @Override
    public Object distinctBy(Function1 f) {
        return scala.collection.immutable.StrictOptimizedSeqOps.distinctBy$(this, f);
    }

    @Override
    public Object updated(int index, Object elem) {
        return scala.collection.immutable.StrictOptimizedSeqOps.updated$(this, index, elem);
    }

    @Override
    public Object patch(int from, IterableOnce other, int replaced) {
        return scala.collection.immutable.StrictOptimizedSeqOps.patch$(this, from, other, replaced);
    }

    @Override
    public Object sorted(Ordering ord) {
        return scala.collection.immutable.StrictOptimizedSeqOps.sorted$(this, ord);
    }

    @Override
    public Object prepended(Object elem) {
        return StrictOptimizedSeqOps.prepended$(this, elem);
    }

    @Override
    public Object appended(Object elem) {
        return StrictOptimizedSeqOps.appended$(this, elem);
    }

    @Override
    public Object appendedAll(IterableOnce suffix) {
        return StrictOptimizedSeqOps.appendedAll$(this, suffix);
    }

    @Override
    public Object prependedAll(IterableOnce prefix) {
        return StrictOptimizedSeqOps.prependedAll$(this, prefix);
    }

    @Override
    public Object padTo(int len, Object elem) {
        return StrictOptimizedSeqOps.padTo$(this, len, elem);
    }

    @Override
    public Object diff(Seq that) {
        return StrictOptimizedSeqOps.diff$(this, that);
    }

    @Override
    public Object intersect(Seq that) {
        return StrictOptimizedSeqOps.intersect$(this, that);
    }

    @Override
    public Tuple2<ByteString, ByteString> partition(Function1<Object, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<scala.collection.immutable.IndexedSeq<A1>, scala.collection.immutable.IndexedSeq<A2>> unzip(Function1<Object, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<scala.collection.immutable.IndexedSeq<A1>, scala.collection.immutable.IndexedSeq<A2>, scala.collection.immutable.IndexedSeq<A3>> unzip3(Function1<Object, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<Object, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<Object, IterableOnce<B>> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatMap$(this, b, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedConcat(IterableOnce<B> that, Builder<B, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedConcat$(this, that, b);
    }

    @Override
    public Object collect(PartialFunction pf) {
        return StrictOptimizedIterableOps.collect$(this, pf);
    }

    @Override
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<Object, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<Object, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<Object, B>, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedZip$(this, that, b);
    }

    @Override
    public Object zipWithIndex() {
        return StrictOptimizedIterableOps.zipWithIndex$(this);
    }

    @Override
    public Object scanLeft(Object z, Function2 op) {
        return StrictOptimizedIterableOps.scanLeft$(this, z, op);
    }

    @Override
    public Object filter(Function1 pred) {
        return StrictOptimizedIterableOps.filter$(this, pred);
    }

    @Override
    public Object filterNot(Function1 pred) {
        return StrictOptimizedIterableOps.filterNot$(this, pred);
    }

    @Override
    public Object filterImpl(Function1 pred, boolean isFlipped) {
        return StrictOptimizedIterableOps.filterImpl$(this, pred, isFlipped);
    }

    @Override
    public <A1, A2> Tuple2<scala.collection.immutable.IndexedSeq<A1>, scala.collection.immutable.IndexedSeq<A2>> partitionMap(Function1<Object, Either<A1, A2>> f) {
        return StrictOptimizedIterableOps.partitionMap$(this, f);
    }

    @Override
    public Object tapEach(Function1 f) {
        return StrictOptimizedIterableOps.tapEach$(this, f);
    }

    @Override
    public /* synthetic */ boolean scala$collection$immutable$IndexedSeq$$super$canEqual(Object that) {
        return Seq.canEqual$(this, that);
    }

    @Override
    public /* synthetic */ boolean scala$collection$immutable$IndexedSeq$$super$sameElements(IterableOnce that) {
        return SeqOps.sameElements$(this, that);
    }

    @Override
    public final scala.collection.immutable.IndexedSeq<Object> toIndexedSeq() {
        return scala.collection.immutable.IndexedSeq.toIndexedSeq$(this);
    }

    @Override
    public boolean canEqual(Object that) {
        return scala.collection.immutable.IndexedSeq.canEqual$(this, that);
    }

    @Override
    public <B> boolean sameElements(IterableOnce<B> o) {
        return scala.collection.immutable.IndexedSeq.sameElements$(this, o);
    }

    @Override
    public int applyPreferredMaxLength() {
        return scala.collection.immutable.IndexedSeq.applyPreferredMaxLength$(this);
    }

    @Override
    public SeqFactory<scala.collection.immutable.IndexedSeq> iterableFactory() {
        return scala.collection.immutable.IndexedSeq.iterableFactory$(this);
    }

    @Override
    public /* synthetic */ Object scala$collection$immutable$IndexedSeqOps$$super$slice(int from, int until) {
        return IndexedSeqOps.slice$(this, from, until);
    }

    @Override
    public String stringPrefix() {
        return IndexedSeq.stringPrefix$(this);
    }

    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
        return (S)IndexedSeqOps.stepper$(this, shape);
    }

    @Override
    public Iterator<Object> reverseIterator() {
        return IndexedSeqOps.reverseIterator$(this);
    }

    @Override
    public <B> B foldRight(B z, Function2<Object, B, B> op) {
        return (B)IndexedSeqOps.foldRight$(this, z, op);
    }

    @Override
    public IndexedSeqView<Object> view() {
        return IndexedSeqOps.view$(this);
    }

    @Override
    public IndexedSeqView<Object> view(int from, int until) {
        return IndexedSeqOps.view$(this, from, until);
    }

    @Override
    public Iterable<Object> reversed() {
        return IndexedSeqOps.reversed$(this);
    }

    @Override
    public Object reverse() {
        return IndexedSeqOps.reverse$(this);
    }

    @Override
    public Option<Object> headOption() {
        return IndexedSeqOps.headOption$(this);
    }

    @Override
    public final int lengthCompare(int len) {
        return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, len);
    }

    @Override
    public int knownSize() {
        return IndexedSeqOps.knownSize$(this);
    }

    @Override
    public final int lengthCompare(Iterable<?> that) {
        return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, that);
    }

    @Override
    public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
        return IndexedSeqOps.search$(this, elem, ord);
    }

    @Override
    public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
        return IndexedSeqOps.search$(this, elem, from, to, ord);
    }

    @Override
    public final scala.collection.immutable.Seq<Object> toSeq() {
        return scala.collection.immutable.Seq.toSeq$(this);
    }

    @Override
    public boolean equals(Object o) {
        return Seq.equals$(this, o);
    }

    @Override
    public int hashCode() {
        return Seq.hashCode$(this);
    }

    @Override
    public /* synthetic */ Object scala$collection$SeqOps$$super$concat(IterableOnce suffix) {
        return IterableOps.concat$(this, suffix);
    }

    @Override
    public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(int otherSize) {
        return IterableOps.sizeCompare$((IterableOps)this, otherSize);
    }

    @Override
    public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(Iterable that) {
        return IterableOps.sizeCompare$((IterableOps)this, that);
    }

    @Override
    public final Object $plus$colon(Object elem) {
        return SeqOps.$plus$colon$(this, elem);
    }

    @Override
    public final Object $colon$plus(Object elem) {
        return SeqOps.$colon$plus$(this, elem);
    }

    @Override
    public final Object $plus$plus$colon(IterableOnce prefix) {
        return SeqOps.$plus$plus$colon$(this, prefix);
    }

    @Override
    public final Object $colon$plus$plus(IterableOnce suffix) {
        return SeqOps.$colon$plus$plus$(this, suffix);
    }

    @Override
    public final Object concat(IterableOnce suffix) {
        return SeqOps.concat$(this, suffix);
    }

    @Override
    public final Object union(Seq that) {
        return SeqOps.union$(this, that);
    }

    @Override
    public final int size() {
        return SeqOps.size$(this);
    }

    @Override
    public Object distinct() {
        return SeqOps.distinct$(this);
    }

    @Override
    public <B> boolean startsWith(IterableOnce<B> that, int offset) {
        return SeqOps.startsWith$(this, that, offset);
    }

    @Override
    public <B> int startsWith$default$2() {
        return SeqOps.startsWith$default$2$(this);
    }

    @Override
    public <B> boolean endsWith(Iterable<B> that) {
        return SeqOps.endsWith$(this, that);
    }

    @Override
    public boolean isDefinedAt(int idx) {
        return SeqOps.isDefinedAt$(this, idx);
    }

    @Override
    public final int segmentLength(Function1<Object, Object> p) {
        return SeqOps.segmentLength$(this, p);
    }

    @Override
    public int segmentLength(Function1<Object, Object> p, int from) {
        return SeqOps.segmentLength$(this, p, from);
    }

    @Override
    public final int prefixLength(Function1<Object, Object> p) {
        return SeqOps.prefixLength$(this, p);
    }

    @Override
    public int indexWhere(Function1<Object, Object> p) {
        return SeqOps.indexWhere$(this, p);
    }

    @Override
    public <B> int indexOf(B elem) {
        return SeqOps.indexOf$(this, elem);
    }

    @Override
    public <B> int lastIndexOf(B elem, int end) {
        return SeqOps.lastIndexOf$(this, elem, end);
    }

    @Override
    public <B> int lastIndexOf$default$2() {
        return SeqOps.lastIndexOf$default$2$(this);
    }

    @Override
    public int lastIndexWhere(Function1<Object, Object> p, int end) {
        return SeqOps.lastIndexWhere$(this, p, end);
    }

    @Override
    public int lastIndexWhere(Function1<Object, Object> p) {
        return SeqOps.lastIndexWhere$(this, p);
    }

    @Override
    public <B> int indexOfSlice(Seq<B> that, int from) {
        return SeqOps.indexOfSlice$(this, that, from);
    }

    @Override
    public <B> int indexOfSlice(Seq<B> that) {
        return SeqOps.indexOfSlice$(this, that);
    }

    @Override
    public <B> int lastIndexOfSlice(Seq<B> that, int end) {
        return SeqOps.lastIndexOfSlice$(this, that, end);
    }

    @Override
    public <B> int lastIndexOfSlice(Seq<B> that) {
        return SeqOps.lastIndexOfSlice$(this, that);
    }

    @Override
    public Option<Object> findLast(Function1<Object, Object> p) {
        return SeqOps.findLast$(this, p);
    }

    @Override
    public <B> boolean containsSlice(Seq<B> that) {
        return SeqOps.containsSlice$(this, that);
    }

    @Override
    public <A1> boolean contains(A1 elem) {
        return SeqOps.contains$(this, elem);
    }

    @Override
    public Object reverseMap(Function1 f) {
        return SeqOps.reverseMap$(this, f);
    }

    @Override
    public Iterator<ByteString> permutations() {
        return SeqOps.permutations$(this);
    }

    @Override
    public Iterator<ByteString> combinations(int n) {
        return SeqOps.combinations$(this, n);
    }

    @Override
    public Object sortWith(Function2 lt) {
        return SeqOps.sortWith$(this, lt);
    }

    @Override
    public Object sortBy(Function1 f, Ordering ord) {
        return SeqOps.sortBy$(this, f, ord);
    }

    @Override
    public Range indices() {
        return SeqOps.indices$(this);
    }

    @Override
    public final int sizeCompare(int otherSize) {
        return SeqOps.sizeCompare$((SeqOps)this, otherSize);
    }

    @Override
    public final int sizeCompare(Iterable<?> that) {
        return SeqOps.sizeCompare$((SeqOps)this, that);
    }

    @Override
    public final IterableOps lengthIs() {
        return SeqOps.lengthIs$(this);
    }

    @Override
    public <B> boolean corresponds(Seq<B> that, Function2<Object, B, Object> p) {
        return SeqOps.corresponds$(this, that, p);
    }

    @Override
    public <B> Map<B, Object> occCounts(Seq<B> sq) {
        return SeqOps.occCounts$(this, sq);
    }

    @Override
    public Option unapply(Object a) {
        return PartialFunction.unapply$(this, a);
    }

    @Override
    public PartialFunction<Object, Object> elementWise() {
        return PartialFunction.elementWise$(this);
    }

    @Override
    public <A1, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
        return PartialFunction.orElse$(this, that);
    }

    @Override
    public <C> PartialFunction<Object, C> andThen(Function1<Object, C> k) {
        return PartialFunction.andThen$((PartialFunction)this, k);
    }

    @Override
    public <C> PartialFunction<Object, C> andThen(PartialFunction<Object, C> k) {
        return PartialFunction.andThen$((PartialFunction)this, k);
    }

    @Override
    public <R$> PartialFunction<R$, Object> compose(PartialFunction<R$, Object> k) {
        return PartialFunction.compose$(this, k);
    }

    @Override
    public Function1<Object, Option<Object>> lift() {
        return PartialFunction.lift$(this);
    }

    @Override
    public Object applyOrElse(Object x, Function1 function1) {
        return PartialFunction.applyOrElse$(this, x, function1);
    }

    @Override
    public <U> Function1<Object, Object> runWith(Function1<Object, U> action) {
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
    public <A> Function1<A, Object> compose(Function1<A, Object> g2) {
        return Function1.compose$(this, g2);
    }

    @Override
    public final Iterable<Object> toIterable() {
        return Iterable.toIterable$(this);
    }

    @Override
    public final Iterable<Object> coll() {
        return Iterable.coll$(this);
    }

    @Override
    public Iterable<Object> seq() {
        return Iterable.seq$(this);
    }

    @Override
    public final String collectionClassName() {
        return Iterable.collectionClassName$(this);
    }

    @Override
    public <B> LazyZip2<Object, B, Iterable> lazyZip(Iterable<B> that) {
        return Iterable.lazyZip$(this, that);
    }

    @Override
    public final Iterable<Object> toTraversable() {
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
    public Option<Object> lastOption() {
        return IterableOps.lastOption$(this);
    }

    @Override
    public final IterableOps sizeIs() {
        return IterableOps.sizeIs$(this);
    }

    @Override
    public Object transpose(Function1 asIterable) {
        return IterableOps.transpose$(this, asIterable);
    }

    @Override
    public WithFilter<Object, ?> withFilter(Function1<Object, Object> p) {
        return IterableOps.withFilter$(this, p);
    }

    @Override
    public Iterator<ByteString> sliding(int size) {
        return IterableOps.sliding$(this, size);
    }

    @Override
    public Iterator<ByteString> sliding(int size, int step) {
        return IterableOps.sliding$(this, size, step);
    }

    @Override
    public <K> scala.collection.immutable.Map<K, ByteString> groupBy(Function1<Object, K> f) {
        return IterableOps.groupBy$(this, f);
    }

    @Override
    public <K, B> scala.collection.immutable.Map<K, scala.collection.immutable.IndexedSeq<B>> groupMap(Function1<Object, K> key, Function1<Object, B> f) {
        return IterableOps.groupMap$(this, key, f);
    }

    @Override
    public <K, B> scala.collection.immutable.Map<K, B> groupMapReduce(Function1<Object, K> key, Function1<Object, B> f, Function2<B, B, B> reduce) {
        return IterableOps.groupMapReduce$(this, key, f, reduce);
    }

    @Override
    public Object scan(Object z, Function2 op) {
        return IterableOps.scan$(this, z, op);
    }

    @Override
    public Object scanRight(Object z, Function2 op) {
        return IterableOps.scanRight$(this, z, op);
    }

    @Override
    public final Object $plus$plus(IterableOnce suffix) {
        return IterableOps.$plus$plus$(this, suffix);
    }

    @Override
    public Object zipAll(Iterable that, Object thisElem, Object thatElem) {
        return IterableOps.zipAll$(this, that, thisElem, thatElem);
    }

    @Override
    public Iterator<ByteString> tails() {
        return IterableOps.tails$(this);
    }

    @Override
    public Iterator<ByteString> inits() {
        return IterableOps.inits$(this);
    }

    @Override
    public boolean hasDefiniteSize() {
        return IterableOnceOps.hasDefiniteSize$(this);
    }

    @Override
    public boolean forall(Function1<Object, Object> p) {
        return IterableOnceOps.forall$(this, p);
    }

    @Override
    public boolean exists(Function1<Object, Object> p) {
        return IterableOnceOps.exists$(this, p);
    }

    @Override
    public int count(Function1<Object, Object> p) {
        return IterableOnceOps.count$(this, p);
    }

    @Override
    public Option<Object> find(Function1<Object, Object> p) {
        return IterableOnceOps.find$(this, p);
    }

    @Override
    public <B> B foldLeft(B z, Function2<B, Object, B> op) {
        return (B)IterableOnceOps.foldLeft$(this, z, op);
    }

    @Override
    public final <B> B $div$colon(B z, Function2<B, Object, B> op) {
        return (B)IterableOnceOps.$div$colon$(this, z, op);
    }

    @Override
    public final <B> B $colon$bslash(B z, Function2<Object, B, B> op) {
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
    public <B> B reduceLeft(Function2<B, Object, B> op) {
        return (B)IterableOnceOps.reduceLeft$(this, op);
    }

    @Override
    public <B> B reduceRight(Function2<Object, B, B> op) {
        return (B)IterableOnceOps.reduceRight$(this, op);
    }

    @Override
    public <B> Option<B> reduceLeftOption(Function2<B, Object, B> op) {
        return IterableOnceOps.reduceLeftOption$(this, op);
    }

    @Override
    public <B> Option<B> reduceRightOption(Function2<Object, B, B> op) {
        return IterableOnceOps.reduceRightOption$(this, op);
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
    public <B> Option<Object> minOption(Ordering<B> ord) {
        return IterableOnceOps.minOption$(this, ord);
    }

    @Override
    public Object max(Ordering ord) {
        return IterableOnceOps.max$(this, ord);
    }

    @Override
    public <B> Option<Object> maxOption(Ordering<B> ord) {
        return IterableOnceOps.maxOption$(this, ord);
    }

    @Override
    public Object maxBy(Function1 f, Ordering cmp) {
        return IterableOnceOps.maxBy$(this, f, cmp);
    }

    @Override
    public <B> Option<Object> maxByOption(Function1<Object, B> f, Ordering<B> cmp) {
        return IterableOnceOps.maxByOption$(this, f, cmp);
    }

    @Override
    public Object minBy(Function1 f, Ordering cmp) {
        return IterableOnceOps.minBy$(this, f, cmp);
    }

    @Override
    public <B> Option<Object> minByOption(Function1<Object, B> f, Ordering<B> cmp) {
        return IterableOnceOps.minByOption$(this, f, cmp);
    }

    @Override
    public <B> Option<B> collectFirst(PartialFunction<Object, B> pf) {
        return IterableOnceOps.collectFirst$(this, pf);
    }

    @Override
    public <B> B aggregate(Function0<B> z, Function2<B, Object, B> seqop, Function2<B, B, B> combop) {
        return (B)IterableOnceOps.aggregate$(this, z, seqop, combop);
    }

    @Override
    public <B> boolean corresponds(IterableOnce<B> that, Function2<Object, B, Object> p) {
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
    public StringBuilder addString(StringBuilder b, String start2, String sep, String end) {
        return IterableOnceOps.addString$(this, b, start2, sep, end);
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
    public <C1> C1 to(Factory<Object, C1> factory2) {
        return (C1)IterableOnceOps.to$(this, factory2);
    }

    @Override
    public final Iterator<Object> toIterator() {
        return IterableOnceOps.toIterator$(this);
    }

    @Override
    public List<Object> toList() {
        return IterableOnceOps.toList$(this);
    }

    @Override
    public Vector<Object> toVector() {
        return IterableOnceOps.toVector$(this);
    }

    @Override
    public <K, V> scala.collection.immutable.Map<K, V> toMap($less$colon$less<Object, Tuple2<K, V>> ev) {
        return IterableOnceOps.toMap$(this, ev);
    }

    @Override
    public <B> Set<B> toSet() {
        return IterableOnceOps.toSet$(this);
    }

    @Override
    public final Stream<Object> toStream() {
        return IterableOnceOps.toStream$(this);
    }

    @Override
    public final <B> Buffer<B> toBuffer() {
        return IterableOnceOps.toBuffer$(this);
    }

    @Override
    public ByteString fromSpecific(IterableOnce<Object> coll) {
        return ByteString$.MODULE$.apply(coll);
    }

    @Override
    public Builder<Object, ByteString> newSpecificBuilder() {
        return ByteString$.MODULE$.newBuilder();
    }

    @Override
    public ByteString empty() {
        return ByteString$.MODULE$.empty();
    }

    @Override
    public abstract byte apply(int var1);

    public abstract Companion byteStringCompanion();

    @Override
    public final String className() {
        return "ByteString";
    }

    @Override
    public boolean isEmpty() {
        return this.length() == 0;
    }

    public ByteIterator iterator() {
        throw new UnsupportedOperationException("Method iterator is not implemented in ByteString");
    }

    @Override
    public byte head() {
        return this.apply(0);
    }

    @Override
    public ByteString tail() {
        return this.drop(1);
    }

    @Override
    public byte last() {
        return this.apply(this.length() - 1);
    }

    @Override
    public ByteString init() {
        return this.dropRight(1);
    }

    @Override
    public ByteString take(int n) {
        throw new UnsupportedOperationException("Method take is not implemented in ByteString");
    }

    @Override
    public ByteString takeRight(int n) {
        return this.slice(this.length() - n, this.length());
    }

    @Override
    public ByteString slice(int from, int until) {
        throw new UnsupportedOperationException("Method slice is not implemented in ByteString");
    }

    @Override
    public ByteString drop(int n) {
        throw new UnsupportedOperationException("Method drop is not implemented in ByteString");
    }

    @Override
    public ByteString dropRight(int n) {
        throw new UnsupportedOperationException("Method dropRight is not implemented in ByteString");
    }

    @Override
    public ByteString takeWhile(Function1<Object, Object> p) {
        return ((ByteIterator)this.iterator().takeWhile((Function1)p)).toByteString();
    }

    @Override
    public ByteString dropWhile(Function1<Object, Object> p) {
        return ((ByteIterator)this.iterator().dropWhile((Function1)p)).toByteString();
    }

    @Override
    public Tuple2<ByteString, ByteString> span(Function1<Object, Object> p) {
        Tuple2<ByteIterator, ByteIterator> tuple2 = this.iterator().span(p);
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        ByteIterator a = tuple2._1();
        ByteIterator b = tuple2._2();
        Tuple2<ByteIterator, ByteIterator> tuple22 = new Tuple2<ByteIterator, ByteIterator>(a, b);
        ByteIterator a2 = tuple22._1();
        ByteIterator b2 = tuple22._2();
        return new Tuple2<ByteString, ByteString>(a2.toByteString(), b2.toByteString());
    }

    @Override
    public Tuple2<ByteString, ByteString> splitAt(int n) {
        return new Tuple2<ByteString, ByteString>(this.take(n), this.drop(n));
    }

    @Override
    public int indexWhere(Function1<Object, Object> p, int from) {
        return this.iterator().indexWhere(p, from);
    }

    @Override
    public <B> int indexOf(B elem, int from) {
        return this.indexOf(elem, from);
    }

    @Override
    public Iterator<ByteString> grouped(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException(new java.lang.StringBuilder(22).append("size=").append(size).append(" must be positive").toString());
        }
        return scala.package$.MODULE$.Iterator().iterate(this, (Function1<ByteString, ByteString> & Serializable)x$10 -> x$10.drop(size)).takeWhile((Function1<ByteString, Object> & Serializable)(A x$11) -> BoxesRunTime.boxToBoolean(((ByteString)x$11).nonEmpty())).map((Function1<ByteString, ByteString> & Serializable)(A x$12) -> x$12.take(size));
    }

    @Override
    public String toString() {
        int maxSize = 100;
        if (this.size() > maxSize) {
            return new java.lang.StringBuilder(15).append(this.take(maxSize).toString()).append("... and [").append(this.size() - maxSize).append("] more").toString();
        }
        return Seq.toString$(this);
    }

    public byte[] toArray() {
        return (byte[])this.toArray(ClassTag$.MODULE$.Byte());
    }

    @Override
    public final <B> Object toArray(ClassTag<B> arg0) {
        Object array = arg0.newArray(this.size());
        this.copyToArray(array, 0, this.size());
        return array;
    }

    @Override
    public final <B> int copyToArray(Object xs, int start2) {
        return this.copyToArray(xs, start2, RichInt$.MODULE$.min$extension(Predef$.MODULE$.intWrapper(this.size()), ArrayOps$.MODULE$.size$extension(Predef$.MODULE$.genericArrayOps(xs))));
    }

    @Override
    public <B> int copyToArray(Object xs, int start2, int len) {
        throw new UnsupportedOperationException("Method copyToArray is not implemented in ByteString");
    }

    public byte[] toArrayUnsafe() {
        return this.toArray();
    }

    @Override
    public <U> void foreach(Function1<Object, U> f) {
        this.iterator().foreach(f);
    }

    public abstract void writeToOutputStream(ObjectOutputStream var1);

    public abstract ByteString $plus$plus(ByteString var1);

    public ByteString concat(ByteString that) {
        return this.$plus$plus(that);
    }

    public abstract int copyToBuffer(ByteBuffer var1);

    public abstract CompactByteString compact();

    public abstract boolean isCompact();

    public abstract ByteBuffer asByteBuffer();

    public abstract scala.collection.immutable.Iterable<ByteBuffer> asByteBuffers();

    public java.lang.Iterable<ByteBuffer> getByteBuffers() {
        return JavaConverters$.MODULE$.asJavaIterableConverter(this.asByteBuffers()).asJava();
    }

    public ByteBuffer toByteBuffer() {
        return ByteBuffer.wrap(this.toArray());
    }

    public final String utf8String() {
        return this.decodeString(StandardCharsets.UTF_8);
    }

    public abstract String decodeString(String var1);

    public abstract String decodeString(Charset var1);

    public abstract ByteString decodeBase64();

    public abstract ByteString encodeBase64();

    public final ByteString mapI(Function1<Object, Object> f) {
        return this.map(f.andThen((Function1<Object, Object> & Serializable)(R x$13) -> BoxesRunTime.boxToByte(ByteString.$anonfun$mapI$1(BoxesRunTime.unboxToInt(x$13)))));
    }

    @Override
    public <A> ByteString map(Function1<Object, Object> f) {
        return this.fromSpecific((IterableOnce)StrictOptimizedIterableOps.map$(this, f));
    }

    public void foreach$mZc$sp(Function1<Object, Object> f) {
        this.iterator().foreach$mZc$sp(f);
    }

    public void foreach$mBc$sp(Function1<Object, Object> f) {
        this.iterator().foreach$mBc$sp(f);
    }

    public void foreach$mCc$sp(Function1<Object, Object> f) {
        this.iterator().foreach$mCc$sp(f);
    }

    public void foreach$mDc$sp(Function1<Object, Object> f) {
        this.iterator().foreach$mDc$sp(f);
    }

    public void foreach$mFc$sp(Function1<Object, Object> f) {
        this.iterator().foreach$mFc$sp(f);
    }

    public void foreach$mIc$sp(Function1<Object, Object> f) {
        this.iterator().foreach$mIc$sp(f);
    }

    public void foreach$mJc$sp(Function1<Object, Object> f) {
        this.iterator().foreach$mJc$sp(f);
    }

    public void foreach$mSc$sp(Function1<Object, Object> f) {
        this.iterator().foreach$mSc$sp(f);
    }

    public void foreach$mVc$sp(Function1<Object, BoxedUnit> f) {
        this.iterator().foreach$mVc$sp(f);
    }

    public static final /* synthetic */ byte $anonfun$mapI$1(int x$13) {
        return (byte)x$13;
    }

    public ByteString() {
        IterableOnce.$init$(this);
        IterableOnceOps.$init$(this);
        IterableOps.$init$(this);
        IterableFactoryDefaults.$init$(this);
        Iterable.$init$(this);
        scala.collection.immutable.Iterable.$init$(this);
        Function1.$init$(this);
        PartialFunction.$init$(this);
        SeqOps.$init$(this);
        Seq.$init$(this);
        scala.collection.immutable.Seq.$init$(this);
        IndexedSeqOps.$init$(this);
        IndexedSeq.$init$(this);
        scala.collection.immutable.IndexedSeqOps.$init$(this);
        scala.collection.immutable.IndexedSeq.$init$(this);
        StrictOptimizedIterableOps.$init$(this);
        StrictOptimizedSeqOps.$init$(this);
        scala.collection.immutable.StrictOptimizedSeqOps.$init$(this);
    }

    public static final class ByteString1
    extends ByteString
    implements Serializable {
        private final byte[] bytes;
        private final int startIndex;
        private final int length;

        private byte[] bytes() {
            return this.bytes;
        }

        private int startIndex() {
            return this.startIndex;
        }

        @Override
        public int length() {
            return this.length;
        }

        @Override
        public byte apply(int idx) {
            return this.bytes()[this.checkRangeConvert(idx)];
        }

        @Override
        public ByteIterator.ByteArrayIterator iterator() {
            return ByteIterator$ByteArrayIterator$.MODULE$.apply(this.bytes(), this.startIndex(), this.startIndex() + this.length());
        }

        private int checkRangeConvert(int index) {
            if (0 <= index && this.length() > index) {
                return index + this.startIndex();
            }
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }

        @Override
        public void writeToOutputStream(ObjectOutputStream os) {
            os.writeInt(this.length());
            os.write(this.bytes(), this.startIndex(), this.length());
        }

        @Override
        public boolean isCompact() {
            return this.length() == this.bytes().length;
        }

        @Override
        public ByteString$ByteString1$ byteStringCompanion() {
            return ByteString$ByteString1$.MODULE$;
        }

        @Override
        public ByteString dropRight(int n) {
            return this.dropRight1(n);
        }

        public ByteString1 dropRight1(int n) {
            if (n <= 0) {
                return this;
            }
            if (this.length() - n <= 0) {
                return ByteString$ByteString1$.MODULE$.empty();
            }
            return new ByteString1(this.bytes(), this.startIndex(), this.length() - n);
        }

        @Override
        public ByteString drop(int n) {
            if (n <= 0) {
                return this;
            }
            return this.drop1(n);
        }

        public ByteString1 drop1(int n) {
            int nextStartIndex = this.startIndex() + n;
            if (nextStartIndex >= this.bytes().length) {
                return ByteString$ByteString1$.MODULE$.empty();
            }
            return ByteString$ByteString1$.MODULE$.apply(this.bytes(), nextStartIndex, this.length() - n);
        }

        @Override
        public ByteString take(int n) {
            if (n <= 0) {
                return ByteString$.MODULE$.empty();
            }
            return this.take1(n);
        }

        public ByteString1 take1(int n) {
            if (n >= this.length()) {
                return this;
            }
            return ByteString$ByteString1$.MODULE$.apply(this.bytes(), this.startIndex(), n);
        }

        @Override
        public ByteString slice(int from, int until) {
            return this.drop(from).take(until - Math.max(0, from));
        }

        @Override
        public int copyToBuffer(ByteBuffer buffer) {
            return this.writeToBuffer(buffer);
        }

        public int writeToBuffer(ByteBuffer buffer) {
            int copyLength = Math.min(buffer.remaining(), this.length());
            Object object = copyLength > 0 ? buffer.put(this.bytes(), this.startIndex(), copyLength) : BoxedUnit.UNIT;
            return copyLength;
        }

        @Override
        public CompactByteString compact() {
            if (this.isCompact()) {
                return ByteString$ByteString1C$.MODULE$.apply(this.bytes());
            }
            return ByteString$ByteString1C$.MODULE$.apply(this.toArray());
        }

        @Override
        public ByteBuffer asByteBuffer() {
            ByteBuffer buffer = ByteBuffer.wrap(this.bytes(), this.startIndex(), this.length()).asReadOnlyBuffer();
            if (buffer.remaining() < this.bytes().length) {
                return buffer.slice();
            }
            return buffer;
        }

        @Override
        public scala.collection.immutable.Iterable<ByteBuffer> asByteBuffers() {
            return new $colon$colon<Nothing$>((Nothing$)((Object)this.asByteBuffer()), Nil$.MODULE$);
        }

        @Override
        public String decodeString(String charset) {
            if (this.isEmpty()) {
                return "";
            }
            return new String(this.bytes(), this.startIndex(), this.length(), charset);
        }

        @Override
        public String decodeString(Charset charset) {
            if (this.isEmpty()) {
                return "";
            }
            return new String(this.bytes(), this.startIndex(), this.length(), charset);
        }

        @Override
        public ByteString decodeBase64() {
            if (this.isEmpty()) {
                return this;
            }
            if (this.isCompact()) {
                return ByteString$ByteString1C$.MODULE$.apply(Base64.getDecoder().decode(this.bytes()));
            }
            ByteBuffer dst = Base64.getDecoder().decode(ByteBuffer.wrap(this.bytes(), this.startIndex(), this.length()));
            if (dst.hasArray()) {
                if (dst.array().length == dst.remaining()) {
                    return ByteString$ByteString1C$.MODULE$.apply(dst.array());
                }
                return ByteString$ByteString1$.MODULE$.apply(dst.array(), dst.arrayOffset() + dst.position(), dst.remaining());
            }
            return CompactByteString$.MODULE$.apply(dst);
        }

        @Override
        public ByteString encodeBase64() {
            if (this.isEmpty()) {
                return this;
            }
            if (this.isCompact()) {
                return ByteString$ByteString1C$.MODULE$.apply(Base64.getEncoder().encode(this.bytes()));
            }
            ByteBuffer dst = Base64.getEncoder().encode(ByteBuffer.wrap(this.bytes(), this.startIndex(), this.length()));
            if (dst.hasArray()) {
                if (dst.array().length == dst.remaining()) {
                    return ByteString$ByteString1C$.MODULE$.apply(dst.array());
                }
                return ByteString$ByteString1$.MODULE$.apply(dst.array(), dst.arrayOffset() + dst.position(), dst.remaining());
            }
            return CompactByteString$.MODULE$.apply(dst);
        }

        @Override
        public ByteString $plus$plus(ByteString that) {
            if (that.isEmpty()) {
                return this;
            }
            if (this.isEmpty()) {
                return that;
            }
            ByteString byteString = that;
            if (byteString instanceof ByteString1C) {
                ByteString1C byteString1C = (ByteString1C)byteString;
                return ByteString$ByteStrings$.MODULE$.apply(this, byteString1C.toByteString1());
            }
            if (byteString instanceof ByteString1) {
                ByteString1 byteString1 = (ByteString1)byteString;
                if (this.bytes() == byteString1.bytes() && this.startIndex() + this.length() == byteString1.startIndex()) {
                    return new ByteString1(this.bytes(), this.startIndex(), this.length() + byteString1.length());
                }
                return ByteString$ByteStrings$.MODULE$.apply(this, byteString1);
            }
            if (byteString instanceof ByteStrings) {
                ByteStrings byteStrings = (ByteStrings)byteString;
                return ByteString$ByteStrings$.MODULE$.apply(this, byteStrings);
            }
            throw new MatchError(byteString);
        }

        @Override
        public <B> int indexOf(B elem, int from) {
            if (from >= this.length()) {
                return -1;
            }
            int found = -1;
            for (int i = package$.MODULE$.max(from, 0); i < this.length() && found == -1; ++i) {
                if (!BoxesRunTime.equals(BoxesRunTime.boxToByte(this.bytes()[this.startIndex() + i]), elem)) continue;
                found = i;
            }
            return found;
        }

        @Override
        public <B> int copyToArray(Object dest, int start2, int len) {
            int toCopy;
            block0: {
                toCopy = package$.MODULE$.min(package$.MODULE$.min(len, this.length()), ScalaRunTime$.MODULE$.array_length(dest) - start2);
                if (toCopy <= 0) break block0;
                Array$.MODULE$.copy(this.bytes(), this.startIndex(), dest, start2, toCopy);
            }
            return toCopy;
        }

        public Object writeReplace() {
            return new SerializationProxy(this);
        }

        @Override
        public byte[] toArrayUnsafe() {
            if (this.startIndex() == 0 && this.length() == this.bytes().length) {
                return this.bytes();
            }
            return this.toArray();
        }

        public ByteString1(byte[] bytes, int startIndex, int length) {
            this.bytes = bytes;
            this.startIndex = startIndex;
            this.length = length;
        }
    }

    public static final class ByteString1C
    extends CompactByteString {
        private static final long serialVersionUID = 3956956327691936932L;
        private final byte[] bytes;

        private byte[] bytes() {
            return this.bytes;
        }

        @Override
        public byte apply(int idx) {
            return this.bytes()[idx];
        }

        @Override
        public int length() {
            return this.bytes().length;
        }

        @Override
        public ByteIterator.ByteArrayIterator iterator() {
            return ByteIterator$ByteArrayIterator$.MODULE$.apply(this.bytes(), 0, this.bytes().length);
        }

        public ByteString1 toByteString1() {
            return ByteString$ByteString1$.MODULE$.apply(this.bytes(), 0, this.bytes().length);
        }

        @Override
        public ByteString$ByteString1C$ byteStringCompanion() {
            return ByteString$ByteString1C$.MODULE$;
        }

        @Override
        public ByteBuffer asByteBuffer() {
            return this.toByteString1().asByteBuffer();
        }

        @Override
        public scala.collection.immutable.Iterable<ByteBuffer> asByteBuffers() {
            return new $colon$colon<Nothing$>((Nothing$)((Object)this.asByteBuffer()), Nil$.MODULE$);
        }

        @Override
        public String decodeString(String charset) {
            if (this.isEmpty()) {
                return "";
            }
            return new String(this.bytes(), charset);
        }

        @Override
        public String decodeString(Charset charset) {
            if (this.isEmpty()) {
                return "";
            }
            return new String(this.bytes(), charset);
        }

        @Override
        public ByteString decodeBase64() {
            if (this.isEmpty()) {
                return this;
            }
            return ByteString$ByteString1C$.MODULE$.apply(Base64.getDecoder().decode(this.bytes()));
        }

        @Override
        public ByteString encodeBase64() {
            if (this.isEmpty()) {
                return this;
            }
            return ByteString$ByteString1C$.MODULE$.apply(Base64.getEncoder().encode(this.bytes()));
        }

        @Override
        public ByteString $plus$plus(ByteString that) {
            if (that.isEmpty()) {
                return this;
            }
            if (this.isEmpty()) {
                return that;
            }
            return this.toByteString1().$plus$plus(that);
        }

        @Override
        public ByteString take(int n) {
            if (n <= 0) {
                return ByteString$.MODULE$.empty();
            }
            if (n >= this.length()) {
                return this;
            }
            return this.toByteString1().take(n);
        }

        @Override
        public ByteString dropRight(int n) {
            if (n <= 0) {
                return this;
            }
            return this.toByteString1().dropRight(n);
        }

        @Override
        public ByteString drop(int n) {
            if (n <= 0) {
                return this;
            }
            return this.toByteString1().drop(n);
        }

        @Override
        public <B> int indexOf(B elem, int from) {
            if (from >= this.length()) {
                return -1;
            }
            int found = -1;
            for (int i = package$.MODULE$.max(from, 0); i < this.length() && found == -1; ++i) {
                if (!BoxesRunTime.equals(BoxesRunTime.boxToByte(this.bytes()[i]), elem)) continue;
                found = i;
            }
            return found;
        }

        @Override
        public ByteString slice(int from, int until) {
            if (from <= 0 && until >= this.length()) {
                return this;
            }
            if (from >= this.length() || until <= 0 || from >= until) {
                return ByteString$.MODULE$.empty();
            }
            return this.toByteString1().slice(from, until);
        }

        @Override
        public void writeToOutputStream(ObjectOutputStream os) {
            this.toByteString1().writeToOutputStream(os);
        }

        @Override
        public int copyToBuffer(ByteBuffer buffer) {
            return this.writeToBuffer(buffer, 0);
        }

        public int writeToBuffer(ByteBuffer buffer, int offset) {
            int copyLength = Math.min(buffer.remaining(), offset + this.length());
            Object object = copyLength > 0 ? buffer.put(this.bytes(), offset, copyLength) : BoxedUnit.UNIT;
            return copyLength;
        }

        public ByteStringBuilder appendToBuilder(ByteStringBuilder buffer) {
            return buffer.putByteArrayUnsafe(this.bytes());
        }

        @Override
        public <B> int copyToArray(Object dest, int start2, int len) {
            int toCopy;
            block0: {
                toCopy = package$.MODULE$.min(package$.MODULE$.min(len, this.bytes().length), ScalaRunTime$.MODULE$.array_length(dest) - start2);
                if (toCopy <= 0) break block0;
                Array$.MODULE$.copy(this.bytes(), 0, dest, start2, toCopy);
            }
            return toCopy;
        }

        @Override
        public byte[] toArrayUnsafe() {
            return this.bytes();
        }

        public ByteString1C(byte[] bytes) {
            this.bytes = bytes;
        }
    }

    public static final class ByteStrings
    extends ByteString
    implements Serializable {
        private final Vector<ByteString1> bytestrings;
        private final int length;

        public Vector<ByteString1> bytestrings() {
            return this.bytestrings;
        }

        @Override
        public int length() {
            return this.length;
        }

        @Override
        public byte apply(int idx) {
            if (0 <= idx && idx < this.length()) {
                int pos = 0;
                int seen = 0;
                ByteString1 frag = (ByteString1)this.bytestrings().apply(pos);
                while (idx >= seen + frag.length()) {
                    seen += frag.length();
                    frag = (ByteString1)this.bytestrings().apply(++pos);
                }
                return frag.apply(idx - seen);
            }
            throw new IndexOutOfBoundsException(Integer.toString(idx));
        }

        @Override
        public ByteIterator.MultiByteArrayIterator iterator() {
            return ByteIterator$MultiByteArrayIterator$.MODULE$.apply((LinearSeq<ByteIterator.ByteArrayIterator>)this.bytestrings().to(IterableFactory$.MODULE$.toFactory(scala.package$.MODULE$.LazyList())).map((Function1<ByteString1, ByteIterator.ByteArrayIterator> & Serializable)(T1 x$4) -> x$4.iterator()));
        }

        @Override
        public ByteString $plus$plus(ByteString that) {
            if (that.isEmpty()) {
                return this;
            }
            if (this.isEmpty()) {
                return that;
            }
            ByteString byteString = that;
            if (byteString instanceof ByteString1C) {
                ByteString1C byteString1C = (ByteString1C)byteString;
                return ByteString$ByteStrings$.MODULE$.apply(this, byteString1C.toByteString1());
            }
            if (byteString instanceof ByteString1) {
                ByteString1 byteString1 = (ByteString1)byteString;
                return ByteString$ByteStrings$.MODULE$.apply(this, byteString1);
            }
            if (byteString instanceof ByteStrings) {
                ByteStrings byteStrings = (ByteStrings)byteString;
                return ByteString$ByteStrings$.MODULE$.apply(this, byteStrings);
            }
            throw new MatchError(byteString);
        }

        @Override
        public ByteString$ByteStrings$ byteStringCompanion() {
            return ByteString$ByteStrings$.MODULE$;
        }

        @Override
        public boolean isCompact() {
            if (this.bytestrings().length() == 1) {
                return this.bytestrings().head().isCompact();
            }
            return false;
        }

        @Override
        public int copyToBuffer(ByteBuffer buffer) {
            return this.copyItToTheBuffer$1(buffer, 0, 0);
        }

        @Override
        public CompactByteString compact() {
            if (this.isCompact()) {
                return this.bytestrings().head().compact();
            }
            byte[] ar = new byte[this.length()];
            IntRef pos = IntRef.create(0);
            this.bytestrings().foreach((Function1<ByteString1, Object> & Serializable)(A b) -> {
                ByteStrings.$anonfun$compact$1(ar, pos, b);
                return BoxedUnit.UNIT;
            });
            return ByteString$ByteString1C$.MODULE$.apply(ar);
        }

        @Override
        public ByteBuffer asByteBuffer() {
            return this.compact().asByteBuffer();
        }

        @Override
        public scala.collection.immutable.Iterable<ByteBuffer> asByteBuffers() {
            return (scala.collection.immutable.Iterable)this.bytestrings().map((Function1<ByteString1, ByteBuffer> & Serializable)(T1 x$5) -> x$5.asByteBuffer());
        }

        @Override
        public String decodeString(String charset) {
            return this.compact().decodeString(charset);
        }

        @Override
        public String decodeString(Charset charset) {
            return this.compact().decodeString(charset);
        }

        @Override
        public ByteString decodeBase64() {
            return this.compact().decodeBase64();
        }

        @Override
        public ByteString encodeBase64() {
            return this.compact().encodeBase64();
        }

        @Override
        public void writeToOutputStream(ObjectOutputStream os) {
            os.writeInt(this.bytestrings().length());
            this.bytestrings().foreach((Function1<ByteString1, Object> & Serializable)(A x$6) -> {
                x$6.writeToOutputStream(os);
                return BoxedUnit.UNIT;
            });
        }

        @Override
        public ByteString take(int n) {
            if (n <= 0) {
                return ByteString$.MODULE$.empty();
            }
            if (n >= this.length()) {
                return this;
            }
            return this.take0(n);
        }

        public ByteString take0(int n) {
            Tuple2 tuple2 = this.go$1(0, n);
            if (tuple2 == null) {
                throw new MatchError(tuple2);
            }
            int last = tuple2._1$mcI$sp();
            int restToTake = tuple2._2$mcI$sp();
            Tuple2$mcII$sp tuple2$mcII$sp = new Tuple2$mcII$sp(last, restToTake);
            int last2 = ((Tuple2)tuple2$mcII$sp)._1$mcI$sp();
            int restToTake2 = ((Tuple2)tuple2$mcII$sp)._2$mcI$sp();
            if (last2 == 0) {
                return ((ByteString1)this.bytestrings().apply(last2)).take(restToTake2);
            }
            if (restToTake2 == 0) {
                return new ByteStrings((Vector<ByteString1>)this.bytestrings().take(last2), n);
            }
            return new ByteStrings((Vector)((AbstractSeq)this.bytestrings().take(last2)).$colon$plus(((ByteString1)this.bytestrings().apply(last2)).take1(restToTake2)), n);
        }

        @Override
        public ByteString dropRight(int n) {
            if (0 < n && n < this.length()) {
                return this.dropRight0(n);
            }
            if (n >= this.length()) {
                return ByteString$.MODULE$.empty();
            }
            return this;
        }

        private ByteString dropRight0(int n) {
            int byteStringsSize = this.bytestrings().length();
            return this.dropRightWithFullDropsAndRemainig$1(0, n, byteStringsSize, n);
        }

        @Override
        public ByteString slice(int from, int until) {
            if (from <= 0 && until >= this.length()) {
                return this;
            }
            if (from > this.length() || until <= from) {
                return ByteString$.MODULE$.empty();
            }
            return this.drop(from).dropRight(this.length() - until);
        }

        @Override
        public ByteString drop(int n) {
            if (n <= 0) {
                return this;
            }
            if (n >= this.length()) {
                return ByteString$.MODULE$.empty();
            }
            return this.drop0(n);
        }

        private ByteString drop0(int n) {
            Tuple2 tuple2 = this.findSplit$1(0, n);
            if (tuple2 == null) {
                throw new MatchError(tuple2);
            }
            int fullDrops = tuple2._1$mcI$sp();
            int remainingToDrop = tuple2._2$mcI$sp();
            Tuple2$mcII$sp tuple2$mcII$sp = new Tuple2$mcII$sp(fullDrops, remainingToDrop);
            int fullDrops2 = ((Tuple2)tuple2$mcII$sp)._1$mcI$sp();
            int remainingToDrop2 = ((Tuple2)tuple2$mcII$sp)._2$mcI$sp();
            if (remainingToDrop2 == 0) {
                return new ByteStrings((Vector<ByteString1>)this.bytestrings().drop(fullDrops2), this.length() - n);
            }
            if (fullDrops2 == this.bytestrings().length() - 1) {
                return ((ByteString1)this.bytestrings().apply(fullDrops2)).drop(remainingToDrop2);
            }
            ByteString1 byteString1 = ((ByteString1)this.bytestrings().apply(fullDrops2)).drop1(remainingToDrop2);
            return new ByteStrings((Vector)((AbstractSeq)this.bytestrings().drop(fullDrops2 + 1)).$plus$colon(byteString1), this.length() - n);
        }

        @Override
        public <B> int indexOf(B elem, int from) {
            if (from >= this.length()) {
                return -1;
            }
            int byteStringsSize = this.bytestrings().size();
            return this.find$1(0, package$.MODULE$.max(from, 0), 0, byteStringsSize, elem);
        }

        @Override
        public <B> int copyToArray(Object dest, int start2, int len) {
            if (this.bytestrings().size() == 1) {
                return this.bytestrings().head().copyToArray(dest, start2, len);
            }
            int totalToCopy = package$.MODULE$.min(package$.MODULE$.min(len, this.length()), ScalaRunTime$.MODULE$.array_length(dest) - start2);
            if (totalToCopy > 0) {
                ByteString1 current;
                Iterator<ByteString1> bsIterator = this.bytestrings().iterator();
                for (int copied = 0; copied < totalToCopy; copied += current.copyToArray(dest, start2 + copied, totalToCopy - copied)) {
                    current = bsIterator.next();
                }
            }
            return totalToCopy;
        }

        public Object writeReplace() {
            return new SerializationProxy(this);
        }

        private final int copyItToTheBuffer$1(ByteBuffer buffer, int i, int written) {
            while (i < this.bytestrings().length()) {
                written += ((ByteString1)this.bytestrings().apply(i)).writeToBuffer(buffer);
                ++i;
            }
            return written;
        }

        public static final /* synthetic */ void $anonfun$compact$1(byte[] ar$1, IntRef pos$1, ByteString1 b) {
            b.copyToArray(ar$1, pos$1.elem, b.length());
            pos$1.elem += b.length();
        }

        private final Tuple2 go$1(int last, int restToTake) {
            ByteString1 bs;
            while ((bs = (ByteString1)this.bytestrings().apply(last)).length() <= restToTake) {
                restToTake -= bs.length();
                ++last;
            }
            return new Tuple2$mcII$sp(last, restToTake);
        }

        private final ByteString dropRightWithFullDropsAndRemainig$1(int fullDrops, int remainingToDrop, int byteStringsSize$1, int n$1) {
            while (true) {
                ByteString1 bs;
                if ((bs = (ByteString1)this.bytestrings().apply(byteStringsSize$1 - fullDrops - 1)).length() > remainingToDrop) {
                    if (fullDrops == byteStringsSize$1 - 1) {
                        return ((ByteString1)this.bytestrings().apply(false)).dropRight(remainingToDrop);
                    }
                    if (remainingToDrop == 0) {
                        return new ByteStrings((Vector<ByteString1>)this.bytestrings().dropRight(fullDrops), this.length() - n$1);
                    }
                    return new ByteStrings((Vector)((AbstractSeq)this.bytestrings().dropRight(fullDrops + 1)).$colon$plus(((ByteString1)this.bytestrings().apply(byteStringsSize$1 - fullDrops - 1)).dropRight1(remainingToDrop)), this.length() - n$1);
                }
                remainingToDrop -= bs.length();
                ++fullDrops;
            }
        }

        private final Tuple2 findSplit$1(int fullDrops, int remainingToDrop) {
            ByteString1 bs;
            while ((bs = (ByteString1)this.bytestrings().apply(fullDrops)).length() <= remainingToDrop) {
                remainingToDrop -= bs.length();
                ++fullDrops;
            }
            return new Tuple2$mcII$sp(fullDrops, remainingToDrop);
        }

        private final int find$1(int bsIdx, int relativeIndex, int bytesPassed, int byteStringsSize$2, Object elem$1) {
            int subIndexOf;
            while (true) {
                if (bsIdx >= byteStringsSize$2) {
                    return -1;
                }
                ByteString1 bs = (ByteString1)this.bytestrings().apply(bsIdx);
                if (bs.length() <= relativeIndex) {
                    bytesPassed += bs.length();
                    relativeIndex -= bs.length();
                    ++bsIdx;
                    continue;
                }
                subIndexOf = bs.indexOf(elem$1, relativeIndex);
                if (subIndexOf >= 0) break;
                int nextString = bsIdx + 1;
                bytesPassed += bs.length();
                relativeIndex -= bs.length();
                bsIdx = nextString;
            }
            return subIndexOf + bytesPassed;
        }

        public ByteStrings(Vector<ByteString1> bytestrings, int length) {
            this.bytestrings = bytestrings;
            this.length = length;
            if (bytestrings.isEmpty()) {
                throw new IllegalArgumentException("bytestrings must not be empty");
            }
            if (((ByteString)bytestrings.head()).isEmpty()) {
                throw new IllegalArgumentException("bytestrings.head must not be empty");
            }
        }
    }

    public static interface Companion {
        public byte SerializationIdentity();

        public ByteString readFromInputStream(ObjectInputStream var1);
    }

    private static class SerializationProxy
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private transient ByteString orig;

        private ByteString orig() {
            return this.orig;
        }

        private void orig_$eq(ByteString x$1) {
            this.orig = x$1;
        }

        private void writeObject(ObjectOutputStream out) {
            out.writeByte(this.orig().byteStringCompanion().SerializationIdentity());
            this.orig().writeToOutputStream(out);
        }

        private void readObject(ObjectInputStream in) {
            byte serializationId = in.readByte();
            this.orig_$eq(ByteString$Companion$.MODULE$.apply(serializationId).readFromInputStream(in));
        }

        private Object readResolve() {
            return this.orig();
        }

        public SerializationProxy(ByteString orig) {
            this.orig = orig;
        }
    }
}

