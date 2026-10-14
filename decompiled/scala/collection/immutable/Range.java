/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterator;
import scala.collection.IndexedSeq;
import scala.collection.IndexedSeqOps;
import scala.collection.IndexedSeqView;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.Searching;
import scala.collection.Seq;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StepperShape$;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StrictOptimizedSeqOps;
import scala.collection.StringOps$;
import scala.collection.convert.impl.RangeStepper;
import scala.collection.immutable.AbstractSeq;
import scala.collection.immutable.IndexedSeq$;
import scala.collection.immutable.Range$;
import scala.collection.immutable.Range$Partial$;
import scala.collection.immutable.RangeIterator;
import scala.collection.mutable.Builder;
import scala.math.Numeric;
import scala.math.Numeric$IntIsIntegral$;
import scala.math.Ordering;
import scala.math.Ordering$Int$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.util.Either;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\u0015\u001dg\u0001CA\u0004\u0003\u0013\t\t#a\u0006\t\u0015\u0005M\u0003A!b\u0001\n\u0003\t)\u0006\u0003\u0006\u0002X\u0001\u0011\t\u0011)A\u0005\u0003CA!\"!\u0017\u0001\u0005\u000b\u0007I\u0011AA+\u0011)\tY\u0006\u0001B\u0001B\u0003%\u0011\u0011\u0005\u0005\u000b\u0003;\u0002!Q1A\u0005\u0002\u0005U\u0003BCA0\u0001\t\u0005\t\u0015!\u0003\u0002\"!9\u0011\u0011\r\u0001\u0005\u0002\u0005\r\u0004bBA7\u0001\u0011\u0015\u0013q\u000e\u0005\b\u0003o\u0002AQIA=\u0011!\ty\r\u0001Q\u0005\n\u0005E\u0007\u0002CAm\u0001\u0001&I!a7\t\u0011\u0005\r\b\u0001)C\u0005\u00037D\u0001\"!:\u0001A\u0013%\u0011\u0011\u001b\u0005\b\u0003O\u0004a\u0011AAn\u0011%\tI\u000f\u0001b\u0001\n\u000b\nY\u000e\u0003\u0005\u0002l\u0002\u0001\u000bQBAo\u0011!\ti\u000f\u0001Q\u0001\n\u0005\u0005\u0002bBAx\u0001\u0011\u0015\u0011Q\u000b\u0005\t\u0003c\u0004\u0001\u0015!\u0003\u0002\"!9\u00111\u001f\u0001\u0005F\u0005U\u0003bBA{\u0001\u0011\u0015\u0013Q\u000b\u0005\b\u0003o\u0004AQIA}\u0011\u001d\tY\u0010\u0001C#\u0003sDq!!@\u0001\t\u0003\ny\u0010C\u0004\u0003\u0016\u0001!)Ba\u0006\t\u0013\t\u0005\u0002!%A\u0005\u0016\t\r\u0002\"\u0003B\u001d\u0001E\u0005IQ\u0003B\u0012\u0011%\u0011Y\u0004AI\u0001\n+\u0011\u0019\u0003C\u0005\u0003>\u0001\t\n\u0011\"\u0006\u0003@!9!1\t\u0001\u0005\u0006\t\u0015\u0003\u0002\u0003B%\u0001\u0001&IAa\u0013\t\u0011\tM\u0003\u0001)C\u0005\u0005+B\u0001Ba\u001a\u0001A\u0013%!\u0011\u000e\u0005\b\u0005W\u0002AQ\u0001B7\u0011\u001d\u00119\u000b\u0001C#\u0005SCqAa2\u0001\t\u000b\u0012I\rC\u0005\u0003f\u0002\t\n\u0011\"\u0002\u0003h\"9!q\u001e\u0001\u0005F\tE\b\"CB\u0001\u0001E\u0005IQAB\u0002\u0011!\u0019Y\u0001\u0001Q\u0005\n\r5\u0001bBB\n\u0001\u0011\u00053Q\u0003\u0005\b\u0007O\u0001AQIB\u0015\u0011\u001d\u0019y\u0003\u0001C#\u0007cAqa!\u000e\u0001\t\u000b\u001a9\u0004C\u0004\u0004<\u0001!)e!\u0010\t\u0011\r\u0005\u0003\u0001)C\u0005\u0007\u0007Bqaa\u0013\u0001\t\u000b\u001ai\u0005C\u0004\u0004R\u0001!)ea\u0015\t\u000f\r]\u0003\u0001\"\u0012\u0004Z!911\r\u0001\u0005F\r\u0015\u0004bBB7\u0001\u0011\u00153q\u000e\u0005\t\u0007g\u0002\u0001\u0015\"\u0003\u0004v!A1\u0011\u0010\u0001!\n\u0013\u0019Y\bC\u0004\u0006L\u0001!)%!?\t\u000f\r}\u0006\u0001\"\u0002\u0002z\"9QQ\n\u0001\u0005\u0006\u0015=\u0003bBC'\u0001\u0011\u0015S1\u000b\u0005\b\u000b;\u0002AQIC0\u0011\u001d)\t\b\u0001C#\u000bgBq!b\"\u0001\t\u000b*I\tC\u0004\u0006\u0016\u0002!\t%b&\t\u000f\u0015m\u0005\u0001\"\u0011\u0006\u0018\"9QQ\u0014\u0001\u0005V\u0005U\u0003b\u0002C^\u0001\u0011\u0015Sq\u0014\u0005\b\to\u0003AQ\tC]\u0011\u001d!I\u000b\u0001C#\u000bKC\u0001\"b*\u0001A\u0013ES\u0011\u0016\u0005\b\u000bW\u0003A\u0011IA}\u0011\u001d)i\u000b\u0001C!\u000b_Cq!\".\u0001\t\u0003*9l\u0002\u0005\u0004\u0002\u0006%\u0001\u0012ABB\r!\t9!!\u0003\t\u0002\r\u0015\u0005bBA1\u0011\u0012\u000511\u0014\u0005\b\u0007;CE\u0011ABP\u0011\u001d\u0019i\n\u0013C\u0001\u0007SCqAa\u001bI\t\u0003\u0019\t\fC\u0004\u0003l!#\ta!/\t\u000f\r}\u0006\n\"\u0001\u0004B\"91q\u0018%\u0005\u0002\r\u001dhABBc\u0011\n\u00199\rC\u0006\u0002TA\u0013\t\u0011)A\u0005\u0003C\t\u0001bCA-!\n\u0005\t\u0015!\u0003\u0002\"\rA1\"!\u0018Q\u0005\u0003\u0005\u000b\u0011BA\u0011\u000b!9\u0011\u0011\r)\u0005\u0002\r%\u0007bBAt!\u0012\u0005\u00111\u001c\u0004\u0007\u0007[D%aa<\t\u0017\u0005McK!A!\u0002\u0013\t\t#\u0001\u0005\f\u000332&\u0011!Q\u0001\n\u0005\u00052\u0001C\u0006\u0002^Y\u0013\t\u0011)A\u0005\u0003C)\u0001bBA1-\u0012\u00051\u0011\u001f\u0005\b\u0003O4F\u0011AAn\u000f\u001d\u0019i\u0010\u0013E\u0001\u0007\u007f4q\u0001\"\u0001I\u0011\u0003!\u0019\u0001C\u0004\u0002bu#\t\u0001\"\u0002\t\u000f\t-T\f\"\u0001\u0005\b!91qX/\u0005\u0002\u0011}qa\u0002C\u0016\u0011\"\u0005AQ\u0006\u0004\b\u0003/D\u0005\u0012\u0001C\u0018\u0011\u001d\t\tG\u0019C\u0001\tcAqAa\u001bc\t\u0003!\u0019\u0004C\u0004\u0004@\n$\t\u0001\"\u0010\b\u000f\u0011\u001d\u0003\n#\u0001\u0005J\u00199A1\n%\t\u0002\u00115\u0003bBA1O\u0012\u0005Aq\n\u0005\n\t#:'\u0019!C\u0002\t'B\u0001\u0002b\u001ahA\u0003%AQ\u000b\u0005\b\u0005W:G\u0011\u0001C5\u0011\u001d\u0019yl\u001aC\u0001\to2a\u0001\"!I\u0005\u0011\r\u0005B\u0004CG[\u0012\u0005\tQ!BC\u0002\u0013%Aq\u0012\u0005\f\t7k'Q!A!\u0002\u0013!\t\nC\u0004\u0002b5$\t\u0001\"(\t\u000f\t\rS\u000e\"\u0001\u0005$\"9A\u0011V7\u0005B\u0011-\u0006\"\u0003C\\[\u0006\u0005I\u0011\tC]\u0011%!Y,\\A\u0001\n\u0003\"ilB\u0005\u0005D\"\u000b\t\u0011#\u0001\u0005F\u001aIA\u0011\u0011%\u0002\u0002#\u0005Aq\u0019\u0005\b\u0003C2H\u0011\u0001Ce\u0011\u001d!YM\u001eC\u0003\t\u001bDq\u0001b9w\t\u000b!)\u000fC\u0005\u0005vZ\f\t\u0011\"\u0002\u0005x\"IQq\u0001<\u0002\u0002\u0013\u0015Q\u0011B\u0004\b\u0005?D\u0005\u0012AC\u000f\r\u001d\t9\u0003\u0013E\u0001\u000b?Aq!!\u0019~\t\u0003)\t\u0003C\u0004\u0003lu$\t!b\t\t\u000f\r}V\u0010\"\u0001\u0006.!9Qq\u0007%\u0005\n\u0015e\u0002\"CC \u0011\u0006\u0005I\u0011BC!\u0005\u0015\u0011\u0016M\\4f\u0015\u0011\tY!!\u0004\u0002\u0013%lW.\u001e;bE2,'\u0002BA\b\u0003#\t!bY8mY\u0016\u001cG/[8o\u0015\t\t\u0019\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u001b\u0001\tI\"!\u000b\u00020\u0005]\u0012QHA#!\u0019\tY\"!\b\u0002\"5\u0011\u0011\u0011B\u0005\u0005\u0003?\tIAA\u0006BEN$(/Y2u'\u0016\f\b\u0003BA\u0012\u0003Ki!!!\u0005\n\t\u0005\u001d\u0012\u0011\u0003\u0002\u0004\u0013:$\bCBA\u000e\u0003W\t\t#\u0003\u0003\u0002.\u0005%!AC%oI\u0016DX\rZ*fcBQ\u00111DA\u0019\u0003C\t)$!\u000b\n\t\u0005M\u0012\u0011\u0002\u0002\u000e\u0013:$W\r_3e'\u0016\fx\n]:\u0011\t\u0005m\u00111\u0006\t\u000b\u00037\tI$!\t\u00026\u0005%\u0012\u0002BA\u001e\u0003\u0013\u0011Qc\u0015;sS\u000e$x\n\u001d;j[&TX\rZ*fc>\u00038\u000f\u0005\u0005\u0002@\u0005\u0005\u0013\u0011EA\u001b\u001b\t\ti!\u0003\u0003\u0002D\u00055!aF%uKJ\f'\r\\3GC\u000e$xN]=EK\u001a\fW\u000f\u001c;t!\u0011\t9%!\u0014\u000f\t\u0005\r\u0012\u0011J\u0005\u0005\u0003\u0017\n\t\"A\u0004qC\u000e\\\u0017mZ3\n\t\u0005=\u0013\u0011\u000b\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0005\u0003\u0017\n\t\"A\u0003ti\u0006\u0014H/\u0006\u0002\u0002\"\u000511\u000f^1si\u0002\n1!\u001a8e\u0003\u0011)g\u000e\u001a\u0011\u0002\tM$X\r]\u0001\u0006gR,\u0007\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0011\u0005\u0015\u0014qMA5\u0003W\u00022!a\u0007\u0001\u0011\u001d\t\u0019f\u0002a\u0001\u0003CAq!!\u0017\b\u0001\u0004\t\t\u0003C\u0004\u0002^\u001d\u0001\r!!\t\u0002\u0011%$XM]1u_J,\"!!\u001d\u0011\r\u0005}\u00121OA\u0011\u0013\u0011\t)(!\u0004\u0003\u0011%#XM]1u_J\fqa\u001d;faB,'/\u0006\u0003\u0002|\u0005\u001dE\u0003BA?\u0003\u000b\u0014b!a \u0002\u0004\u0006%fABAA\u0001\u0001\tiH\u0001\u0007=e\u00164\u0017N\\3nK:$h\b\u0005\u0003\u0002\u0006\u0006\u001dE\u0002\u0001\u0003\b\u0003\u0013K!\u0019AAF\u0005\u0005\u0019\u0016\u0003BAG\u0003'\u0003B!a\t\u0002\u0010&!\u0011\u0011SA\t\u0005\u001dqu\u000e\u001e5j]\u001e\u0004D!!&\u0002\u001eB1\u0011qHAL\u00037KA!!'\u0002\u000e\t91\u000b^3qa\u0016\u0014\b\u0003BAC\u0003;#A\"a(\u0002\b\u0006\u0005\t\u0011!B\u0001\u0003C\u00131a\u0018\u00132#\u0011\ti)a)\u0011\t\u0005\r\u0012QU\u0005\u0005\u0003O\u000b\tBA\u0002B]f\u0004B!a+\u0002@:!\u0011QVA^\u001d\u0011\ty+!/\u000f\t\u0005E\u0016qW\u0007\u0003\u0003gSA!!.\u0002\u0016\u00051AH]8pizJ!!a\u0005\n\t\u0005=\u0011\u0011C\u0005\u0005\u0003{\u000bi!A\u0004Ti\u0016\u0004\b/\u001a:\n\t\u0005\u0005\u00171\u0019\u0002\u000f\u000b\u001a4\u0017nY5f]R\u001c\u0006\u000f\\5u\u0015\u0011\ti,!\u0004\t\u000f\u0005\u001d\u0017\u0002q\u0001\u0002J\u0006)1\u000f[1qKBA\u0011qHAf\u0003C\t\u0019)\u0003\u0003\u0002N\u00065!\u0001D*uKB\u0004XM]*iCB,\u0017aA4baV\u0011\u00111\u001b\t\u0005\u0003G\t).\u0003\u0003\u0002X\u0006E!\u0001\u0002'p]\u001e\fq![:Fq\u0006\u001cG/\u0006\u0002\u0002^B!\u00111EAp\u0013\u0011\t\t/!\u0005\u0003\u000f\t{w\u000e\\3b]\u00069\u0001.Y:TiV\u0014\u0017A\u00037p]\u001edUM\\4uQ\u0006Y\u0011n]%oG2,8/\u001b<f\u0003\u001dI7/R7qif\f\u0001\"[:F[B$\u0018\u0010I\u0001\u0011]Vl'+\u00198hK\u0016cW-\\3oiN\fa\u0001\\3oORD\u0017a\u00037bgR,E.Z7f]R\fA\u0001\\1ti\u0006!\u0001.Z1e\u0003\u0011Ig.\u001b;\u0016\u0005\u0005\u0015\u0014\u0001\u0002;bS2\f1!\\1q+\u0011\u0011\tAa\u0002\u0015\t\t\r!1\u0002\t\u0007\u00037\tYC!\u0002\u0011\t\u0005\u0015%q\u0001\u0003\b\u0005\u0013A\"\u0019AAQ\u0005\u0005\u0011\u0005b\u0002B\u00071\u0001\u0007!qB\u0001\u0002MBA\u00111\u0005B\t\u0003C\u0011)!\u0003\u0003\u0003\u0014\u0005E!!\u0003$v]\u000e$\u0018n\u001c82\u0003\u0011\u0019w\u000e]=\u0015\u0015\u0005\u0015$\u0011\u0004B\u000e\u0005;\u0011y\u0002C\u0005\u0002Te\u0001\n\u00111\u0001\u0002\"!I\u0011\u0011L\r\u0011\u0002\u0003\u0007\u0011\u0011\u0005\u0005\n\u0003;J\u0002\u0013!a\u0001\u0003CA\u0011\"a:\u001a!\u0003\u0005\r!!8\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011!Q\u0005\u0016\u0005\u0003C\u00119c\u000b\u0002\u0003*A!!1\u0006B\u001b\u001b\t\u0011iC\u0003\u0003\u00030\tE\u0012!C;oG\",7m[3e\u0015\u0011\u0011\u0019$!\u0005\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u00038\t5\"!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012\u0014AD2paf$C-\u001a4bk2$HeM\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00135+\t\u0011\tE\u000b\u0003\u0002^\n\u001d\u0012A\u00012z)\u0011\t)Ga\u0012\t\u000f\u0005uc\u00041\u0001\u0002\"\u0005\tb/\u00197jI\u0006$X-T1y\u0019\u0016tw\r\u001e5\u0015\u0005\t5\u0003\u0003BA\u0012\u0005\u001fJAA!\u0015\u0002\u0012\t!QK\\5u\u0003-!Wm]2sSB$\u0018n\u001c8\u0016\u0005\t]\u0003\u0003\u0002B-\u0005CrAAa\u0017\u0003^A!\u0011\u0011WA\t\u0013\u0011\u0011y&!\u0005\u0002\rA\u0013X\rZ3g\u0013\u0011\u0011\u0019G!\u001a\u0003\rM#(/\u001b8h\u0015\u0011\u0011y&!\u0005\u0002\t\u0019\f\u0017\u000e\u001c\u000b\u0003\u0003\u001b\u000bQ!\u00199qYf$B!!\t\u0003p!9!\u0011\u000f\u0012A\u0002\u0005\u0005\u0012aA5eq\"*!E!\u001e\u0003\u0002B1\u00111\u0005B<\u0005wJAA!\u001f\u0002\u0012\t1A\u000f\u001b:poN\u0004B!a\u0012\u0003~%!!qPA)\u0005eIe\u000eZ3y\u001fV$xJ\u001a\"pk:$7/\u0012=dKB$\u0018n\u001c82\u000fy\u00119Fa!\u0003&FJ1E!\"\u0003\f\nm%QR\u000b\u0005\u0005+\u00129\t\u0002\u0005\u0003\n\u0006U!\u0019\u0001BJ\u0005\u0005!\u0016\u0002\u0002BG\u0005\u001f\u000b1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\n$\u0002\u0002BI\u0003#\ta\u0001\u001e5s_^\u001c\u0018\u0003BAG\u0005+\u0003B!a\u0012\u0003\u0018&!!\u0011TA)\u0005%!\u0006N]8xC\ndW-M\u0005$\u0005;\u0013yJ!)\u0003\u0012:!\u00111\u0005BP\u0013\u0011\u0011\t*!\u00052\u000f\t\n\u0019#!\u0005\u0003$\n)1oY1mCF\u001aaEa\u001f\u0002\u000f\u0019|'/Z1dQV!!1\u0016BZ)\u0011\u0011iE!,\t\u000f\t51\u00051\u0001\u00030BA\u00111\u0005B\t\u0003C\u0011\t\f\u0005\u0003\u0002\u0006\nMFa\u0003B[G\u0001\u0006\t\u0011!b\u0001\u0003C\u0013\u0011!\u0016\u0015\u0007\u0005g\u0013ILa0\u0011\t\u0005\r\"1X\u0005\u0005\u0005{\u000b\tBA\u0006ta\u0016\u001c\u0017.\u00197ju\u0016$\u0017g\u0002\u0013\u0003B\n\r'Q\u0019\b\u0005\u0003G\u0011\u0019-\u0003\u0003\u0003F\u0006E\u0011\u0001B+oSR\fq!\u001b8eKb|e-\u0006\u0003\u0003L\nMGCBA\u0011\u0005\u001b\u0014\t\u000fC\u0004\u0003P\u0012\u0002\rA!5\u0002\t\u0015dW-\u001c\t\u0005\u0003\u000b\u0013\u0019\u000eB\u0006\u0003\n\u0011\u0002\u000b\u0011!AC\u0002\tU\u0017\u0003BA\u0011\u0003GCcAa5\u0003:\ne\u0017g\u0002\u0013\u0003\\\nu'q\u001c\b\u0005\u0003G\u0011i.\u0003\u0003\u0003`\u0006E\u0011aA%oi\"I!1\u001d\u0013\u0011\u0002\u0003\u0007\u0011\u0011E\u0001\u0005MJ|W.A\tj]\u0012,\u0007p\u00144%I\u00164\u0017-\u001e7uII*BAa\t\u0003j\u0012Y!\u0011B\u0013!\u0002\u0003\u0005)\u0019\u0001BkQ\u0019\u0011IO!/\u0003nF:AEa7\u0003^\n}\u0017a\u00037bgRLe\u000eZ3y\u001f\u001a,BAa=\u0003zR1\u0011\u0011\u0005B{\u0005\u007fDqAa4'\u0001\u0004\u00119\u0010\u0005\u0003\u0002\u0006\neHa\u0003B\u0005M\u0001\u0006\t\u0011!b\u0001\u0005+DcA!?\u0003:\nu\u0018g\u0002\u0013\u0003\\\nu'q\u001c\u0005\n\u000332\u0003\u0013!a\u0001\u0003C\tQ\u0003\\1ti&sG-\u001a=PM\u0012\"WMZ1vYR$#'\u0006\u0003\u0003$\r\u0015Aa\u0003B\u0005O\u0001\u0006\t\u0011!b\u0001\u0005+Dca!\u0002\u0003:\u000e%\u0011g\u0002\u0013\u0003\\\nu'q\\\u0001\u0006a>\u001cxJ\u001a\u000b\u0005\u0003C\u0019y\u0001C\u0004\u0004\u0012!\u0002\r!!\t\u0002\u0003%\fAb]1nK\u0016cW-\\3oiN,Baa\u0006\u0004&Q!\u0011Q\\B\r\u0011\u001d\u0019Y\"\u000ba\u0001\u0007;\tA\u0001\u001e5biB1\u0011qIB\u0010\u0007GIAa!\t\u0002R\ta\u0011\n^3sC\ndWm\u00148dKB!\u0011QQB\u0013\t\u001d\u0011I!\u000bb\u0001\u0005+\fA\u0001^1lKR!\u0011QMB\u0016\u0011\u001d\u0019iC\u000ba\u0001\u0003C\t\u0011A\\\u0001\u0005IJ|\u0007\u000f\u0006\u0003\u0002f\rM\u0002bBB\u0017W\u0001\u0007\u0011\u0011E\u0001\ni\u0006\\WMU5hQR$B!!\u001a\u0004:!91Q\u0006\u0017A\u0002\u0005\u0005\u0012!\u00033s_B\u0014\u0016n\u001a5u)\u0011\t)ga\u0010\t\u000f\r5R\u00061\u0001\u0002\"\u0005a\u0011M]4UC.,w\u000b[5mKR!\u00111[B#\u0011\u001d\u00199E\fa\u0001\u0007\u0013\n\u0011\u0001\u001d\t\t\u0003G\u0011\t\"!\t\u0002^\u0006IA/Y6f/\"LG.\u001a\u000b\u0005\u0003K\u001ay\u0005C\u0004\u0004H=\u0002\ra!\u0013\u0002\u0013\u0011\u0014x\u000e],iS2,G\u0003BA3\u0007+Bqaa\u00121\u0001\u0004\u0019I%\u0001\u0003ta\u0006tG\u0003BB.\u0007C\u0002\u0002\"a\t\u0004^\u0005\u0015\u0014QM\u0005\u0005\u0007?\n\tB\u0001\u0004UkBdWM\r\u0005\b\u0007\u000f\n\u0004\u0019AB%\u0003\u0015\u0019H.[2f)\u0019\t)ga\u001a\u0004j!9!1\u001d\u001aA\u0002\u0005\u0005\u0002bBB6e\u0001\u0007\u0011\u0011E\u0001\u0006k:$\u0018\u000e\\\u0001\bgBd\u0017\u000e^!u)\u0011\u0019Yf!\u001d\t\u000f\r52\u00071\u0001\u0002\"\u0005qAn\\2bi&|g.\u00114uKJtE\u0003BA\u0011\u0007oBqa!\f5\u0001\u0004\t\t#A\u0007oK^,U\u000e\u001d;z%\u0006tw-\u001a\u000b\u0005\u0007{*I\u0005E\u0002\u0004\u0000Ys1!a\u0007H\u0003\u0015\u0011\u0016M\\4f!\r\tY\u0002S\n\u0006\u0011\u000e\u001d5Q\u0012\t\u0005\u0003G\u0019I)\u0003\u0003\u0004\f\u0006E!AB!osJ+g\r\u0005\u0003\u0004\u0010\u000eeUBABI\u0015\u0011\u0019\u0019j!&\u0002\u0005%|'BABL\u0003\u0011Q\u0017M^1\n\t\u0005=3\u0011\u0013\u000b\u0003\u0007\u0007\u000bQaY8v]R$\"\"!\t\u0004\"\u000e\r6QUBT\u0011\u001d\t\u0019F\u0013a\u0001\u0003CAq!!\u0017K\u0001\u0004\t\t\u0003C\u0004\u0002^)\u0003\r!!\t\t\u000f\u0005\u001d(\n1\u0001\u0002^RA\u0011\u0011EBV\u0007[\u001by\u000bC\u0004\u0002T-\u0003\r!!\t\t\u000f\u0005e3\n1\u0001\u0002\"!9\u0011QL&A\u0002\u0005\u0005B\u0003CB?\u0007g\u001b)la.\t\u000f\u0005MC\n1\u0001\u0002\"!9\u0011\u0011\f'A\u0002\u0005\u0005\u0002bBA/\u0019\u0002\u0007\u0011\u0011\u0005\u000b\u0007\u0007{\u001aYl!0\t\u000f\u0005MS\n1\u0001\u0002\"!9\u0011\u0011L'A\u0002\u0005\u0005\u0012!C5oG2,8/\u001b<f)!\u0019\u0019m!9\u0004d\u000e\u0015\bcAB@!\nI\u0011J\\2mkNLg/Z\n\u0004!\u0006\u0015D\u0003CBf\u0007\u001f\u001c\tna5\u0011\u0007\r5\u0007+D\u0001I\u0011\u001d\t\u0019\u0006\u0016a\u0001\u0003CAq!!\u0017U\u0001\u0004\t\t\u0003C\u0004\u0002^Q\u0003\r!!\t)\u000fA\u001b9n!8\u0004`B!\u00111EBm\u0013\u0011\u0019Y.!\u0005\u0003!M+'/[1m-\u0016\u00148/[8o+&#\u0015!\u0002<bYV,g$A\u0002\t\u000f\u0005Mc\n1\u0001\u0002\"!9\u0011\u0011\f(A\u0002\u0005\u0005\u0002bBA/\u001d\u0002\u0007\u0011\u0011\u0005\u000b\u0007\u0007\u0007\u001cIoa;\t\u000f\u0005Ms\n1\u0001\u0002\"!9\u0011\u0011L(A\u0002\u0005\u0005\"!C#yG2,8/\u001b<f'\r1\u0016Q\r\u000b\t\u0007g\u001c)pa>\u0004zB\u00191Q\u001a,\t\u000f\u0005M#\f1\u0001\u0002\"!9\u0011\u0011\f.A\u0002\u0005\u0005\u0002bBA/5\u0002\u0007\u0011\u0011\u0005\u0015\b-\u000e]7Q\\Bp\u0003\u0019\u0011\u0015nZ%oiB\u00191QZ/\u0003\r\tKw-\u00138u'\ri6q\u0011\u000b\u0003\u0007\u007f$\u0002\u0002\"\u0003\u0005\u001a\u0011mAQ\u0004\t\u0007\t\u0017!\t\u0002\"\u0006\u000f\t\u0005mAQB\u0005\u0005\t\u001f\tI!\u0001\u0007Ok6,'/[2SC:<W-\u0003\u0003\u0004n\u0012M!\u0002\u0002C\b\u0003\u0013\u0001B!a\u0012\u0005\u0018%!A\u0011AA)\u0011\u001d\t\u0019f\u0018a\u0001\t+Aq!!\u0017`\u0001\u0004!)\u0002C\u0004\u0002^}\u0003\r\u0001\"\u0006\u0015\u0011\u0011\u0005BQ\u0005C\u0014\tS\u0001b\u0001b\u0003\u0005$\u0011U\u0011\u0002BBc\t'Aq!a\u0015a\u0001\u0004!)\u0002C\u0004\u0002Z\u0001\u0004\r\u0001\"\u0006\t\u000f\u0005u\u0003\r1\u0001\u0005\u0016\u0005!Aj\u001c8h!\r\u0019iMY\n\u0004E\u000e\u001dEC\u0001C\u0017)!!)\u0004b\u000e\u0005:\u0011m\u0002C\u0002C\u0006\t#\t\u0019\u000eC\u0004\u0002T\u0011\u0004\r!a5\t\u000f\u0005eC\r1\u0001\u0002T\"9\u0011Q\f3A\u0002\u0005MG\u0003\u0003C \t\u0003\"\u0019\u0005\"\u0012\u0011\r\u0011-A1EAj\u0011\u001d\t\u0019&\u001aa\u0001\u0003'Dq!!\u0017f\u0001\u0004\t\u0019\u000eC\u0004\u0002^\u0015\u0004\r!a5\u0002\u0015\tKw\rR3dS6\fG\u000eE\u0002\u0004N\u001e\u0014!BQ5h\t\u0016\u001c\u0017.\\1m'\r97q\u0011\u000b\u0003\t\u0013\n\u0001CY5h\t\u0016\u001c\u0017i]%oi\u0016<'/\u00197\u0016\u0005\u0011U\u0003\u0003\u0002C,\t;rA!a\u0012\u0005Z%!A1LA)\u0003\u001dqU/\\3sS\u000eLA\u0001b\u0018\u0005b\t1\")[4EK\u000eLW.\u00197Bg&3\u0017J\u001c;fOJ\fGN\u0003\u0003\u0005\\\u0011\r$\u0002\u0002C3\u0003#\tA!\\1uQ\u0006\t\"-[4EK\u000e\f5/\u00138uK\u001e\u0014\u0018\r\u001c\u0011\u0015\u0011\u0011-D\u0011\u000fC:\tk\u0002b\u0001b\u0003\u0005\u0012\u00115\u0004\u0003BA$\t_JA\u0001b\u0013\u0002R!9\u00111K6A\u0002\u00115\u0004bBA-W\u0002\u0007AQ\u000e\u0005\b\u0003;Z\u0007\u0019\u0001C7)!!I\bb\u001f\u0005~\u0011}\u0004C\u0002C\u0006\tG!i\u0007C\u0004\u0002T1\u0004\r\u0001\"\u001c\t\u000f\u0005eC\u000e1\u0001\u0005n!9\u0011Q\f7A\u0002\u00115$a\u0002)beRL\u0017\r\\\u000b\u0007\t\u000b#)\n\"'\u0014\u00075$9\t\u0005\u0003\u0002$\u0011%\u0015\u0002\u0002CF\u0003#\u0011a!\u00118z-\u0006d\u0017aK:dC2\fGeY8mY\u0016\u001cG/[8oI%lW.\u001e;bE2,GEU1oO\u0016$\u0003+\u0019:uS\u0006dG\u0005\n4\u0016\u0005\u0011E\u0005\u0003CA\u0012\u0005#!\u0019\nb&\u0011\t\u0005\u0015EQ\u0013\u0003\b\u0005\u0013k'\u0019AAQ!\u0011\t)\t\"'\u0005\u000f\tUVN1\u0001\u0002\"\u0006a3oY1mC\u0012\u001aw\u000e\u001c7fGRLwN\u001c\u0013j[6,H/\u00192mK\u0012\u0012\u0016M\\4fIA\u000b'\u000f^5bY\u0012\"c\r\t\u000b\u0005\t?#\t\u000bE\u0004\u0004N6$\u0019\nb&\t\u000f\t5\u0001\u000f1\u0001\u0005\u0012R!Aq\u0013CS\u0011\u001d!9+\u001da\u0001\t'\u000b\u0011\u0001_\u0001\ti>\u001cFO]5oOR\u0011AQ\u0016\t\u0005\t_#),\u0004\u0002\u00052*!A1WBK\u0003\u0011a\u0017M\\4\n\t\t\rD\u0011W\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u0011\u0011E\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005uGq\u0018\u0005\n\t\u0003$\u0018\u0011!a\u0001\u0003G\u000b1\u0001\u001f\u00132\u0003\u001d\u0001\u0016M\u001d;jC2\u00042a!4w'\r18q\u0011\u000b\u0003\t\u000b\fABY=%Kb$XM\\:j_:,b\u0001b4\u0005\\\u0012UG\u0003\u0002Ci\t;$B\u0001b5\u0005XB!\u0011Q\u0011Ck\t\u001d\u0011)\f\u001fb\u0001\u0003CCq\u0001b*y\u0001\u0004!I\u000e\u0005\u0003\u0002\u0006\u0012mGa\u0002BEq\n\u0007\u0011\u0011\u0015\u0005\b\t?D\b\u0019\u0001Cq\u0003\u0015!C\u000f[5t!\u001d\u0019i-\u001cCm\t'\f!\u0003^8TiJLgn\u001a\u0013fqR,gn]5p]V1Aq\u001dCx\tg$B\u0001b+\u0005j\"9Aq\\=A\u0002\u0011-\bcBBg[\u00125H\u0011\u001f\t\u0005\u0003\u000b#y\u000fB\u0004\u0003\nf\u0014\r!!)\u0011\t\u0005\u0015E1\u001f\u0003\b\u0005kK(\u0019AAQ\u0003IA\u0017m\u001d5D_\u0012,G%\u001a=uK:\u001c\u0018n\u001c8\u0016\r\u0011eX\u0011AC\u0003)\u0011!I\fb?\t\u000f\u0011}'\u00101\u0001\u0005~B91QZ7\u0005\u0000\u0016\r\u0001\u0003BAC\u000b\u0003!qA!#{\u0005\u0004\t\t\u000b\u0005\u0003\u0002\u0006\u0016\u0015Aa\u0002B[u\n\u0007\u0011\u0011U\u0001\u0011KF,\u0018\r\\:%Kb$XM\\:j_:,b!b\u0003\u0006\u0018\u0015mA\u0003BC\u0007\u000b#!B!!8\u0006\u0010!IA\u0011Y>\u0002\u0002\u0003\u0007\u00111\u0015\u0005\b\t?\\\b\u0019AC\n!\u001d\u0019i-\\C\u000b\u000b3\u0001B!!\"\u0006\u0018\u00119!\u0011R>C\u0002\u0005\u0005\u0006\u0003BAC\u000b7!qA!.|\u0005\u0004\t\t\u000bE\u0002\u0004Nv\u001c2!`BD)\t)i\u0002\u0006\u0005\u0006&\u0015\u001dR\u0011FC\u0016!\u0019!Y\u0001\"\u0005\u0002\"!9\u00111K@A\u0002\u0005\u0005\u0002bBA-\u007f\u0002\u0007\u0011\u0011\u0005\u0005\b\u0003;z\b\u0019AA\u0011)!)y#\"\r\u00064\u0015U\u0002C\u0002C\u0006\tG\t\t\u0003\u0003\u0005\u0002T\u0005\u0005\u0001\u0019AA\u0011\u0011!\tI&!\u0001A\u0002\u0005\u0005\u0002\u0002CA/\u0003\u0003\u0001\r!!\t\u0002\u001f\u0015l\u0007\u000f^=SC:<W-\u0012:s_J$BA!&\u0006<!AQQHA\u0002\u0001\u0004\u00119&\u0001\u0003xQ\u0006$\u0018\u0001D<sSR,'+\u001a9mC\u000e,GCAC\"!\u0011!y+\"\u0012\n\t\u0015\u001dC\u0011\u0017\u0002\u0007\u001f\nTWm\u0019;\t\u000f\ruW\u00071\u0001\u0002\"\u00059!/\u001a<feN,\u0017\u0001C2p]R\f\u0017N\\:\u0015\t\u0005uW\u0011\u000b\u0005\b\tOC\u0004\u0019AA\u0011+\u0011))&b\u0017\u0015\t\u0005uWq\u000b\u0005\b\u0005\u001fL\u0004\u0019AC-!\u0011\t))b\u0017\u0005\u000f\t%\u0011H1\u0001\u0003V\u0006\u00191/^7\u0016\t\u0015\u0005Tq\u000e\u000b\u0005\u0003C)\u0019\u0007C\u0004\u0006fi\u0002\u001d!b\u001a\u0002\u00079,X\u000e\u0005\u0004\u0002H\u0015%TQN\u0005\u0005\u000bW\n\tFA\u0004Ok6,'/[2\u0011\t\u0005\u0015Uq\u000e\u0003\b\u0005\u0013Q$\u0019\u0001Bk\u0003\ri\u0017N\\\u000b\u0005\u000bk*\u0019\t\u0006\u0003\u0002\"\u0015]\u0004bBC=w\u0001\u000fQ1P\u0001\u0004_J$\u0007CBA$\u000b{*\t)\u0003\u0003\u0006\u0000\u0005E#\u0001C(sI\u0016\u0014\u0018N\\4\u0011\t\u0005\u0015U1\u0011\u0003\b\u000b\u000b[$\u0019\u0001Bk\u0005\t\t\u0015'A\u0002nCb,B!b#\u0006\u0014R!\u0011\u0011ECG\u0011\u001d)I\b\u0010a\u0002\u000b\u001f\u0003b!a\u0012\u0006~\u0015E\u0005\u0003BAC\u000b'#q!\"\"=\u0005\u0004\u0011).A\u0003uC&d7/\u0006\u0002\u0006\u001aB1\u0011qHA:\u0003K\nQ!\u001b8jiN\fq#\u00199qYf\u0004&/\u001a4feJ,G-T1y\u0019\u0016tw\r\u001e5\u0015\t\u0005uW\u0011\u0015\u0005\b\u000bG\u0003\u0005\u0019AAR\u0003\u0015yG\u000f[3s)\t\u00119&A\u0005dY\u0006\u001c8OT1nKV\u0011AQV\u0001\tI&\u001cH/\u001b8di\u00069qM]8va\u0016$G\u0003BCM\u000bcCq!b-F\u0001\u0004\t\t#\u0001\u0003tSj,\u0017AB:peR,G-\u0006\u0003\u0006:\u0016\u0005G\u0003BA\u0015\u000bwCq!\"\u001fG\u0001\b)i\f\u0005\u0004\u0002H\u0015uTq\u0018\t\u0005\u0003\u000b+\t\rB\u0004\u0003\n\u0019\u0013\rA!6*\u0007\u00011\u0006\u000bK\u0004\u0001\u0007/\u001cina8")
public abstract class Range
extends AbstractSeq<Object>
implements scala.collection.immutable.IndexedSeq<Object>,
scala.collection.immutable.StrictOptimizedSeqOps<Object, scala.collection.immutable.IndexedSeq, scala.collection.immutable.IndexedSeq<Object>>,
Serializable {
    private static final long serialVersionUID = 3L;
    private final int start;
    private final int end;
    private final int step;
    private final boolean isEmpty;
    public final int scala$collection$immutable$Range$$numRangeElements;
    public final int scala$collection$immutable$Range$$lastElement;

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
    public Tuple2<scala.collection.immutable.IndexedSeq<Object>, scala.collection.immutable.IndexedSeq<Object>> partition(Function1<Object, Object> p) {
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
    public Iterator<Object> reverseIterator() {
        return IndexedSeqOps.reverseIterator$(this);
    }

    @Override
    public <B$> B$ foldRight(B$ z, Function2<Object, B$, B$> op) {
        return (B$)IndexedSeqOps.foldRight$(this, z, op);
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

    public int start() {
        return this.start;
    }

    public int end() {
        return this.end;
    }

    public int step() {
        return this.step;
    }

    @Override
    public final Iterator<Object> iterator() {
        return new RangeIterator(this.start(), this.step(), this.scala$collection$immutable$Range$$lastElement, this.isEmpty());
    }

    @Override
    public final <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
        RangeStepper st = new RangeStepper(this.start(), this.step(), 0, this.length());
        if (shape.shape() == StepperShape$.MODULE$.IntShape()) {
            return (S)st;
        }
        if (!(shape.shape() == StepperShape$.MODULE$.ReferenceShape())) {
            throw new AssertionError((Object)new StringBuilder(18).append("assertion failed: ").append((Object)Range.$anonfun$stepper$1(shape)).toString());
        }
        return (S)new Stepper.EfficientSplit(st){};
    }

    private long gap() {
        return (long)this.end() - (long)this.start();
    }

    private boolean isExact() {
        return this.gap() % (long)this.step() == 0L;
    }

    private boolean hasStub() {
        return this.isInclusive() || !this.isExact();
    }

    private long longLength() {
        return this.gap() / (long)this.step() + (long)(this.hasStub() ? 1 : 0);
    }

    public abstract boolean isInclusive();

    @Override
    public final boolean isEmpty() {
        return this.isEmpty;
    }

    @Override
    public final int length() {
        if (this.scala$collection$immutable$Range$$numRangeElements < 0) {
            throw this.fail();
        }
        return this.scala$collection$immutable$Range$$numRangeElements;
    }

    @Override
    public final int last() {
        if (this.isEmpty()) {
            throw Range$.MODULE$.scala$collection$immutable$Range$$emptyRangeError("last");
        }
        return this.scala$collection$immutable$Range$$lastElement;
    }

    @Override
    public final int head() {
        if (this.isEmpty()) {
            throw Range$.MODULE$.scala$collection$immutable$Range$$emptyRangeError("head");
        }
        return this.start();
    }

    @Override
    public final Range init() {
        if (this.isEmpty()) {
            throw Range$.MODULE$.scala$collection$immutable$Range$$emptyRangeError("init");
        }
        return this.dropRight(1);
    }

    @Override
    public final Range tail() {
        if (this.isEmpty()) {
            throw Range$.MODULE$.scala$collection$immutable$Range$$emptyRangeError("tail");
        }
        if (this.scala$collection$immutable$Range$$numRangeElements == 1) {
            return this.newEmptyRange(this.end());
        }
        if (this.isInclusive()) {
            return new Inclusive(this.start() + this.step(), this.end(), this.step());
        }
        return new Exclusive(this.start() + this.step(), this.end(), this.step());
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> scala.collection.immutable.IndexedSeq<B> map(Function1<Object, B> f) {
        this.scala$collection$immutable$Range$$validateMaxLength();
        Builder strictOptimizedMap_b = IndexedSeq$.MODULE$.newBuilder();
        Iterator strictOptimizedMap_it = this.iterator();
        while (strictOptimizedMap_it.hasNext()) {
            void strictOptimizedMap_$plus$eq_elem;
            B b = f.apply(strictOptimizedMap_it.next());
            if (strictOptimizedMap_b == null) {
                throw null;
            }
            strictOptimizedMap_b.addOne(strictOptimizedMap_$plus$eq_elem);
            b = null;
        }
        return (scala.collection.immutable.IndexedSeq)strictOptimizedMap_b.result();
    }

    public final Range copy(int start2, int end, int step, boolean isInclusive) {
        if (isInclusive) {
            return new Inclusive(start2, end, step);
        }
        return new Exclusive(start2, end, step);
    }

    public final int copy$default$1() {
        return this.start();
    }

    public final int copy$default$2() {
        return this.end();
    }

    public final int copy$default$3() {
        return this.step();
    }

    public final boolean copy$default$4() {
        return this.isInclusive();
    }

    public final Range by(int step) {
        return this.copy(this.start(), this.end(), step, this.isInclusive());
    }

    public void scala$collection$immutable$Range$$validateMaxLength() {
        if (this.scala$collection$immutable$Range$$numRangeElements < 0) {
            throw this.fail();
        }
    }

    private String description() {
        return StringOps$.MODULE$.format$extension("%d %s %d by %s", ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.start(), this.isInclusive() ? "to" : "until", this.end(), this.step()}));
    }

    private Nothing$ fail() {
        throw new IllegalArgumentException(new StringBuilder(54).append(this.description()).append(": seqs cannot contain more than Int.MaxValue elements.").toString());
    }

    @Override
    public final int apply(int idx) throws IndexOutOfBoundsException {
        return this.apply$mcII$sp(idx);
    }

    @Override
    public final <U> void foreach(Function1<Object, U> f) {
        if (!this.isEmpty()) {
            int i = this.start();
            while (true) {
                f.apply(i);
                if (i == this.scala$collection$immutable$Range$$lastElement) {
                    return;
                }
                i += this.step();
            }
        }
    }

    @Override
    public final <B> int indexOf(B elem, int from) {
        if (elem instanceof Integer) {
            int n = BoxesRunTime.unboxToInt(elem);
            int pos = this.scala$collection$immutable$Range$$posOf(n);
            if (pos >= from) {
                return pos;
            }
            return -1;
        }
        return SeqOps.indexOf$(this, elem, from);
    }

    public final <B> int indexOf$default$2() {
        return 0;
    }

    @Override
    public final <B> int lastIndexOf(B elem, int end) {
        if (elem instanceof Integer) {
            int n = BoxesRunTime.unboxToInt(elem);
            int pos = this.scala$collection$immutable$Range$$posOf(n);
            if (pos <= end) {
                return pos;
            }
            return -1;
        }
        return SeqOps.lastIndexOf$(this, elem, end);
    }

    @Override
    public final <B> int lastIndexOf$default$2() {
        return this.length() - 1;
    }

    public int scala$collection$immutable$Range$$posOf(int i) {
        if (this.contains(i)) {
            return (i - this.start()) / this.step();
        }
        return -1;
    }

    @Override
    public <B> boolean sameElements(IterableOnce<B> that) {
        if (that instanceof Range) {
            Range range = (Range)that;
            int n = this.length();
            switch (n) {
                case 0: {
                    return range.isEmpty();
                }
                case 1: {
                    return range.length() == 1 && this.start() == range.start();
                }
            }
            return range.length() == n && this.start() == range.start() && this.step() == range.step();
        }
        return scala.collection.immutable.IndexedSeq.sameElements$(this, that);
    }

    @Override
    public final Range take(int n) {
        if (n <= 0 || this.isEmpty()) {
            return this.newEmptyRange(this.start());
        }
        if (n >= this.scala$collection$immutable$Range$$numRangeElements && this.scala$collection$immutable$Range$$numRangeElements >= 0) {
            return this;
        }
        return new Inclusive(this.start(), this.locationAfterN(n - 1), this.step());
    }

    @Override
    public final Range drop(int n) {
        if (n <= 0 || this.isEmpty()) {
            return this;
        }
        if (n >= this.scala$collection$immutable$Range$$numRangeElements && this.scala$collection$immutable$Range$$numRangeElements >= 0) {
            return this.newEmptyRange(this.end());
        }
        return this.copy(this.locationAfterN(n), this.end(), this.step(), this.isInclusive());
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public final Range takeRight(int n) {
        void inclusive_step;
        if (n <= 0) {
            return this.newEmptyRange(this.start());
        }
        if (this.scala$collection$immutable$Range$$numRangeElements >= 0) {
            return this.drop(this.scala$collection$immutable$Range$$numRangeElements - n);
        }
        int y = this.last();
        long x = (long)y - (long)this.step() * (long)(n - 1);
        if (this.step() > 0 && x < (long)this.start() || this.step() < 0 && x > (long)this.start()) {
            return this;
        }
        int n2 = this.step();
        int inclusive_start = (int)x;
        return new Inclusive(inclusive_start, y, (int)inclusive_step);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public final Range dropRight(int n) {
        void inclusive_step;
        if (n <= 0) {
            return this;
        }
        if (this.scala$collection$immutable$Range$$numRangeElements >= 0) {
            return this.take(this.scala$collection$immutable$Range$$numRangeElements - n);
        }
        int y = this.last() - this.step() * n;
        if (this.step() > 0 && y < this.start() || this.step() < 0 && y > this.start()) {
            return this.newEmptyRange(this.start());
        }
        int n2 = this.step();
        int inclusive_start = this.start();
        return new Inclusive(inclusive_start, y, (int)inclusive_step);
    }

    private long argTakeWhile(Function1<Object, Object> p) {
        int current;
        if (this.isEmpty()) {
            return this.start();
        }
        int stop = this.last();
        for (current = this.start(); current != stop && p.apply$mcZI$sp(current); current += this.step()) {
        }
        if (current != stop || !p.apply$mcZI$sp(current)) {
            return current;
        }
        return (long)current + (long)this.step();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public final Range takeWhile(Function1<Object, Object> p) {
        void inclusive_step;
        long stop;
        long l;
        if (this.isEmpty()) {
            l = this.start();
        } else {
            int argTakeWhile_current;
            int argTakeWhile_stop = this.last();
            for (argTakeWhile_current = this.start(); argTakeWhile_current != argTakeWhile_stop && p.apply$mcZI$sp(argTakeWhile_current); argTakeWhile_current += this.step()) {
            }
            l = stop = argTakeWhile_current != argTakeWhile_stop || !p.apply$mcZI$sp(argTakeWhile_current) ? (long)argTakeWhile_current : (long)argTakeWhile_current + (long)this.step();
        }
        if (stop == (long)this.start()) {
            return this.newEmptyRange(this.start());
        }
        int x = (int)(stop - (long)this.step());
        if (x == this.last()) {
            return this;
        }
        int n = this.step();
        int inclusive_start = this.start();
        return new Inclusive(inclusive_start, x, (int)inclusive_step);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public final Range dropWhile(Function1<Object, Object> p) {
        void inclusive_step;
        void inclusive_end;
        long stop;
        long l;
        if (this.isEmpty()) {
            l = this.start();
        } else {
            int argTakeWhile_current;
            int argTakeWhile_stop = this.last();
            for (argTakeWhile_current = this.start(); argTakeWhile_current != argTakeWhile_stop && p.apply$mcZI$sp(argTakeWhile_current); argTakeWhile_current += this.step()) {
            }
            l = stop = argTakeWhile_current != argTakeWhile_stop || !p.apply$mcZI$sp(argTakeWhile_current) ? (long)argTakeWhile_current : (long)argTakeWhile_current + (long)this.step();
        }
        if (stop == (long)this.start()) {
            return this;
        }
        int x = (int)(stop - (long)this.step());
        if (x == this.last()) {
            return this.newEmptyRange(this.last());
        }
        int n = this.step();
        int n2 = this.last();
        int inclusive_start = x + this.step();
        return new Inclusive(inclusive_start, (int)inclusive_end, (int)inclusive_step);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public final Tuple2<Range, Range> span(Function1<Object, Object> p) {
        void inclusive_step;
        void inclusive_end;
        void inclusive_step2;
        long border;
        long l;
        if (this.isEmpty()) {
            l = this.start();
        } else {
            int argTakeWhile_current;
            int argTakeWhile_stop = this.last();
            for (argTakeWhile_current = this.start(); argTakeWhile_current != argTakeWhile_stop && p.apply$mcZI$sp(argTakeWhile_current); argTakeWhile_current += this.step()) {
            }
            l = border = argTakeWhile_current != argTakeWhile_stop || !p.apply$mcZI$sp(argTakeWhile_current) ? (long)argTakeWhile_current : (long)argTakeWhile_current + (long)this.step();
        }
        if (border == (long)this.start()) {
            return new Tuple2<Range, Range>(this.newEmptyRange(this.start()), this);
        }
        int x = (int)(border - (long)this.step());
        if (x == this.last()) {
            return new Tuple2<Range, Range>(this, this.newEmptyRange(this.last()));
        }
        int n = this.step();
        int inclusive_start = this.start();
        int n2 = this.step();
        int n3 = this.last();
        int inclusive_start2 = x + this.step();
        return new Tuple2<Range, Range>(new Inclusive(inclusive_start, x, (int)inclusive_step2), new Inclusive(inclusive_start2, (int)inclusive_end, (int)inclusive_step));
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public final Range slice(int from, int until) {
        void inclusive_step;
        if (from <= 0) {
            return this.take(until);
        }
        if (until >= this.scala$collection$immutable$Range$$numRangeElements && this.scala$collection$immutable$Range$$numRangeElements >= 0) {
            return this.drop(from);
        }
        int fromValue = this.locationAfterN(from);
        if (from >= until) {
            return this.newEmptyRange(fromValue);
        }
        int n = this.step();
        int inclusive_end = this.locationAfterN(until - 1);
        return new Inclusive(fromValue, inclusive_end, (int)inclusive_step);
    }

    @Override
    public final Tuple2<Range, Range> splitAt(int n) {
        return new Tuple2<Range, Range>(this.take(n), this.drop(n));
    }

    private int locationAfterN(int n) {
        return this.start() + this.step() * n;
    }

    private Exclusive newEmptyRange(int value) {
        return new Exclusive(value, value, this.step());
    }

    @Override
    public final Range reverse() {
        if (this.isEmpty()) {
            return this;
        }
        return new Inclusive(this.last(), this.start(), -this.step());
    }

    public final Range inclusive() {
        if (this.isInclusive()) {
            return this;
        }
        return new Inclusive(this.start(), this.end(), this.step());
    }

    @Override
    public final boolean contains(int x) {
        if (x == this.end() && !this.isInclusive()) {
            return false;
        }
        if (this.step() > 0) {
            if (x < this.start() || x > this.end()) {
                return false;
            }
            return this.step() == 1 || Integer.remainderUnsigned(x - this.start(), this.step()) == 0;
        }
        if (x < this.end() || x > this.start()) {
            return false;
        }
        return this.step() == -1 || Integer.remainderUnsigned(this.start() - x, -this.step()) == 0;
    }

    @Override
    public final <B> boolean contains(B elem) {
        if (elem instanceof Integer) {
            int n = BoxesRunTime.unboxToInt(elem);
            return this.contains(n);
        }
        return SeqOps.contains$(this, elem);
    }

    @Override
    public final <B> int sum(Numeric<B> num) {
        if (num == Numeric$IntIsIntegral$.MODULE$) {
            if (this.isEmpty()) {
                return 0;
            }
            if (this.length() == 1) {
                return this.head();
            }
            return (int)((long)this.length() * ((long)this.head() + (long)this.last()) / 2L);
        }
        if (this.isEmpty()) {
            return num.toInt(num.zero());
        }
        Object acc = num.zero();
        int i = this.head();
        while (true) {
            acc = num.plus(acc, i);
            if (i == this.scala$collection$immutable$Range$$lastElement) {
                return num.toInt(acc);
            }
            i += this.step();
        }
    }

    @Override
    public final <A1> int min(Ordering<A1> ord) {
        if (ord == Ordering$Int$.MODULE$) {
            if (this.step() > 0) {
                return this.head();
            }
            return this.last();
        }
        if (Ordering.CachedReverse.isReverseOf$(Ordering$Int$.MODULE$, ord)) {
            if (this.step() > 0) {
                return this.last();
            }
            return this.head();
        }
        return BoxesRunTime.unboxToInt(IterableOnceOps.min$(this, ord));
    }

    @Override
    public final <A1> int max(Ordering<A1> ord) {
        if (ord == Ordering$Int$.MODULE$) {
            if (this.step() > 0) {
                return this.last();
            }
            return this.head();
        }
        if (Ordering.CachedReverse.isReverseOf$(Ordering$Int$.MODULE$, ord)) {
            if (this.step() > 0) {
                return this.head();
            }
            return this.last();
        }
        return BoxesRunTime.unboxToInt(IterableOnceOps.max$(this, ord));
    }

    @Override
    public Iterator<Range> tails() {
        return new AbstractIterator<Range>(this){
            private int i;
            private final /* synthetic */ Range $outer;

            public boolean hasNext() {
                return this.i <= this.$outer.length();
            }

            public Range next() {
                if (this.hasNext()) {
                    Range res = this.$outer.drop(this.i);
                    ++this.i;
                    return res;
                }
                return (Range)((Object)Iterator$.scala$collection$Iterator$$_empty.next());
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.i = 0;
            }
        };
    }

    @Override
    public Iterator<Range> inits() {
        return new AbstractIterator<Range>(this){
            private int i;
            private final /* synthetic */ Range $outer;

            public boolean hasNext() {
                return this.i <= this.$outer.length();
            }

            public Range next() {
                if (this.hasNext()) {
                    Range res = this.$outer.dropRight(this.i);
                    ++this.i;
                    return res;
                }
                return (Range)((Object)Iterator$.scala$collection$Iterator$$_empty.next());
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.i = 0;
            }
        };
    }

    @Override
    public final int applyPreferredMaxLength() {
        return Integer.MAX_VALUE;
    }

    @Override
    public final boolean equals(Object other) {
        if (other instanceof Range) {
            int l0;
            Range range = (Range)other;
            if (this.isEmpty()) {
                return range.isEmpty();
            }
            return range.nonEmpty() && this.start() == range.start() && (l0 = this.last()) == range.last() && (this.start() == l0 || this.step() == range.step());
        }
        return Seq.equals$(this, other);
    }

    @Override
    public final int hashCode() {
        if (this.length() >= 2) {
            return MurmurHash3$.MODULE$.rangeHash(this.start(), this.step(), this.scala$collection$immutable$Range$$lastElement);
        }
        return MurmurHash3$.MODULE$.seqHash(this);
    }

    @Override
    public final String toString() {
        String stepped;
        String preposition = this.isInclusive() ? "to" : "until";
        String string = stepped = this.step() == 1 ? "" : new StringBuilder(4).append(" by ").append(this.step()).toString();
        String prefix = this.isEmpty() ? "empty " : (!this.isExact() ? "inexact " : "");
        return new StringBuilder(8).append(prefix).append("Range ").append(this.start()).append(" ").append(preposition).append(" ").append(this.end()).append(stepped).toString();
    }

    @Override
    public String className() {
        return "Range";
    }

    @Override
    public Range distinct() {
        return this;
    }

    @Override
    public Iterator<Range> grouped(int size) {
        if (!(size >= 1)) {
            throw new IllegalArgumentException(new StringBuilder(20).append("requirement failed: ").append((Object)StringOps$.MODULE$.format$extension("size=%d, but size must be positive", ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{size}))).toString());
        }
        if (this.isEmpty()) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        return new AbstractIterator<Range>(this, size){
            private int i;
            private final /* synthetic */ Range $outer;
            private final int s$1;

            public boolean hasNext() {
                return this.$outer.length() > this.i;
            }

            public Range next() {
                if (this.hasNext()) {
                    Range x = this.$outer.slice(this.i, this.i + this.s$1);
                    this.i += this.s$1;
                    return x;
                }
                return (Range)((Object)Iterator$.scala$collection$Iterator$$_empty.next());
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.s$1 = s$1;
                this.i = 0;
            }
        };
    }

    @Override
    public <B> scala.collection.immutable.IndexedSeq<Object> sorted(Ordering<B> ord) {
        if (ord == Ordering$Int$.MODULE$) {
            if (this.step() > 0) {
                return this;
            }
            return this.reverse();
        }
        return (scala.collection.immutable.IndexedSeq)SeqOps.sorted$(this, ord);
    }

    public final void foreach$mVc$sp(Function1<Object, BoxedUnit> f) {
        if (!this.isEmpty()) {
            int i = this.start();
            while (true) {
                f.apply$mcVI$sp(i);
                if (i == this.scala$collection$immutable$Range$$lastElement) {
                    return;
                }
                i += this.step();
            }
        }
    }

    public final int indexOf$mIc$sp(int elem, int from) {
        int pos = this.scala$collection$immutable$Range$$posOf(elem);
        if (pos >= from) {
            return pos;
        }
        return -1;
    }

    public final int lastIndexOf$mIc$sp(int elem, int end) {
        int pos = this.scala$collection$immutable$Range$$posOf(elem);
        if (pos <= end) {
            return pos;
        }
        return -1;
    }

    @Override
    public int apply$mcII$sp(int idx) {
        this.scala$collection$immutable$Range$$validateMaxLength();
        if (idx < 0 || idx >= this.scala$collection$immutable$Range$$numRangeElements) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(idx).append(" is out of bounds (min 0, max ").append(this.scala$collection$immutable$Range$$numRangeElements - 1).append(")").toString());
        }
        return this.start() + this.step() * idx;
    }

    public static final /* synthetic */ String $anonfun$stepper$1(StepperShape shape$1) {
        return new StringBuilder(25).append("unexpected StepperShape: ").append(shape$1).toString();
    }

    public Range(int start2, int end, int step) {
        int n;
        long len;
        this.start = start2;
        this.end = end;
        this.step = step;
        boolean bl = this.isEmpty = start2 > end && step > 0 || start2 < end && step < 0 || start2 == end && !this.isInclusive();
        if (step == 0) {
            throw new IllegalArgumentException("step cannot be 0.");
        }
        this.scala$collection$immutable$Range$$numRangeElements = this.isEmpty() ? 0 : ((len = this.longLength()) > Integer.MAX_VALUE ? -1 : (int)len);
        switch (step) {
            case 1: {
                if (this.isInclusive()) {
                    n = end;
                    break;
                }
                n = end - 1;
                break;
            }
            case -1: {
                if (this.isInclusive()) {
                    n = end;
                    break;
                }
                n = end + 1;
                break;
            }
            default: {
                int remainder = (int)(this.gap() % (long)step);
                n = remainder != 0 ? end - remainder : (this.isInclusive() ? end : end - step);
            }
        }
        this.scala$collection$immutable$Range$$lastElement = n;
    }

    public static final class Exclusive
    extends Range {
        private static final long serialVersionUID = 3L;

        @Override
        public boolean isInclusive() {
            return false;
        }

        public Exclusive(int start2, int end, int step) {
            super(start2, end, step);
        }
    }

    public static final class Inclusive
    extends Range {
        private static final long serialVersionUID = 3L;

        @Override
        public boolean isInclusive() {
            return true;
        }

        public Inclusive(int start2, int end, int step) {
            super(start2, end, step);
        }
    }

    public static final class Partial<T, U> {
        private final Function1<T, U> scala$collection$immutable$Range$Partial$$f;

        public Function1<T, U> scala$collection$immutable$Range$Partial$$f() {
            return this.scala$collection$immutable$Range$Partial$$f;
        }

        public U by(T x) {
            return this.scala$collection$immutable$Range$Partial$$f().apply(x);
        }

        public String toString() {
            this.scala$collection$immutable$Range$Partial$$f();
            return "Range requires step";
        }

        public int hashCode() {
            return this.scala$collection$immutable$Range$Partial$$f().hashCode();
        }

        public boolean equals(Object x$1) {
            return Range$Partial$.MODULE$.equals$extension(this.scala$collection$immutable$Range$Partial$$f(), x$1);
        }

        public Partial(Function1<T, U> f) {
            this.scala$collection$immutable$Range$Partial$$f = f;
        }
    }
}

