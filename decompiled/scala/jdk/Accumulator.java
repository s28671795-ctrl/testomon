/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.io.Serializable;
import scala.$less$colon$less;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableFactoryDefaults;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.LazyZip2;
import scala.collection.Searching;
import scala.collection.Seq;
import scala.collection.SeqFactory;
import scala.collection.SeqView;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.View;
import scala.collection.WithFilter;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.List;
import scala.collection.immutable.Range;
import scala.collection.immutable.Set;
import scala.collection.immutable.Stream;
import scala.collection.immutable.Vector;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.Map;
import scala.collection.mutable.SeqOps;
import scala.collection.mutable.StringBuilder;
import scala.jdk.Accumulator$;
import scala.jdk.Accumulator$AccumulatorFactoryShape$;
import scala.jdk.AnyAccumulator;
import scala.jdk.AnyAccumulator$;
import scala.math.Integral;
import scala.math.Numeric;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005\u0015%c!B!C\u0003\u00039\u0005BB@\u0001\t\u0003\t\t\u0001\u0003\u0006\u0002\u001a\u0001\u0001\r\u0011\"\u0001C\u00037A!\"a\t\u0001\u0001\u0004%\tAQA\u0013\u0011!\t\t\u0004\u0001Q!\n\u0005u\u0001BCA\u001a\u0001\u0001\u0007I\u0011\u0001\"\u0002\u001c!Q\u0011Q\u0007\u0001A\u0002\u0013\u0005!)a\u000e\t\u0011\u0005m\u0002\u0001)Q\u0005\u0003;A!\"!\u0010\u0001\u0001\u0004%\tAQA \u0011)\t9\u0005\u0001a\u0001\n\u0003\u0011\u0015\u0011\n\u0005\t\u0003\u001b\u0002\u0001\u0015)\u0003\u0002B!A\u0011q\n\u0001\u0007\u0002\t\u000b\t\u0006\u0003\u0005\u0002X\u0001!\tAQA\u000e\u0011\u001d\tI\u0006\u0001D\t\u00037Bq!!'\u0001\t\u000b\nY\nC\u0004\u00028\u0002!)%a\u0007\t\u000f\u0005e\u0006\u0001\"\u0012\u0002\u001c!9\u00111\u0018\u0001\u0005\u0006\u0005}\u0002bBA_\u0001\u0011\u0005\u0011q\u0018\u0005\t\u0003\u0003\u0004A\u0011\u0001\"\u0002D\u001e9\u0011\u0011\u001a\"\t\u0002\u0005-gAB!C\u0011\u0003\ti\r\u0003\u0004\u0000+\u0011\u0005\u0011q\u001a\u0005\b\u0003#,B1AAj\u0011\u001d\u00119#\u0006C\u0001\u0005SAqAa\u0001\u0016\t\u0003\u0011i\u0005C\u0004\u0003^U!\tAa\u0018\t\u000f\tmT\u0003\"\u0001\u0003~!9!1U\u000b\u0005\u0002\t\u0015\u0006b\u0002Bi+\u0011\u0005!1\u001b\u0005\b\u0005#,B\u0011\u0001B{\u0011\u001d\u0019)\"\u0006C\u0001\u0007/Aqa!\u000b\u0016\t\u0003\u0019Y\u0003C\u0004\u0004*U!\ta!\u0014\t\u000f\r%R\u0003\"\u0001\u0004t!91\u0011F\u000b\u0005\u0002\r]\u0005bBB\u0015+\u0011\u00051q\u0018\u0005\b\u0007W,B\u0011ABw\u0011\u001d\u0019Y/\u0006C\u0001\t\u000fAqaa;\u0016\t\u0003!I\u0003C\u0004\u0004lV!\t\u0001b\u0014\t\u000f\r-X\u0003\"\u0001\u0005z!9AqU\u000b\u0005\u0002\u0011%f!CAx+A\u0005\u0019\u0013EAy\u0011\u001d\t)p\u000bD\u0001\u0003oDqAa\u0001,\r\u0003\u0011)aB\u0004\u0005HVA\t\u0001\"3\u0007\u000f\u0005=X\u0003#\u0001\u0005L\"1qp\fC\u0001\tcD\u0011\u0002b=0\u0005\u0004%\u0019\u0001\">\t\u0011\u0015\u0015q\u0006)A\u0005\toD\u0011\"b\u00020\u0005\u0004%\u0019!\"\u0003\t\u0011\u0015Mq\u0006)A\u0005\u000b\u0017A\u0011\"\"\u00060\u0005\u0004%\u0019!b\u0006\t\u0011\u0015\u0005r\u0006)A\u0005\u000b3A\u0011\"b\t0\u0005\u0004%\u0019!\"\n\t\u0011\u00155r\u0006)A\u0005\u000bOA\u0011\"b\f0\u0005\u0004%\u0019!\"\r\t\u0011\u0015mr\u0006)A\u0005\u000bgA\u0011\"\"\u00100\u0005\u0004%\u0019!b\u0010\t\u0011\u0015\u001ds\u0006)A\u0005\u000b\u00032\u0011\u0002b4\u0016!\u0003\r\t\u0003\"5\t\u000f\u0011MW\b\"\u0001\u0002@\"9AQ[\u001f\u0005\u0004\u0011]\u0007\"\u0003Cr{\t\u0007I\u0011\u0002Cs\u0005-\t5mY;nk2\fGo\u001c:\u000b\u0005\r#\u0015a\u00016eW*\tQ)A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u000b!;\u0016\u0011\u0002?\u0014\t\u0001IU\n\u001f\t\u0003\u0015.k\u0011\u0001R\u0005\u0003\u0019\u0012\u0013a!\u00118z%\u00164\u0007c\u0001(T+6\tqJ\u0003\u0002Q#\u00069Q.\u001e;bE2,'B\u0001*E\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003)>\u00131aU3r!\t1v\u000b\u0004\u0001\u0005\u0013a\u0003\u0001\u0015!A\u0001\u0006\u0004I&!A!\u0012\u0005ik\u0006C\u0001&\\\u0013\taFIA\u0004O_RD\u0017N\\4\u0011\u0005)s\u0016BA0E\u0005\r\te.\u001f\u0015\u0006/\u0006$gn\u001d\t\u0003\u0015\nL!a\u0019#\u0003\u0017M\u0004XmY5bY&TX\rZ\u0019\u0006G\u00154\u0007n\u001a\b\u0003\u0015\u001aL!a\u001a#\u0002\r\u0011{WO\u00197fc\u0011!\u0013.\\#\u000f\u0005)lW\"A6\u000b\u000514\u0015A\u0002\u001fs_>$h(C\u0001Fc\u0015\u0019s\u000e\u001d:r\u001d\tQ\u0005/\u0003\u0002r\t\u0006\u0019\u0011J\u001c;2\t\u0011JW.R\u0019\u0006GQ,xO\u001e\b\u0003\u0015VL!A\u001e#\u0002\t1{gnZ\u0019\u0005I%lW\t\u0005\u0003OsV[\u0018B\u0001>P\u0005\u001d\u0011U/\u001b7eKJ\u0004\"A\u0016?\u0005\ru\u0004AQ1\u0001\u007f\u0005\u0005\u0019\u0015C\u0001.N\u0003\u0019a\u0014N\\5u}Q\u0011\u00111\u0001\t\b\u0003\u000b\u0001Q+a\u0002|\u001b\u0005\u0011\u0005c\u0001,\u0002\n\u0011A\u00111\u0002\u0001\u0005\u0006\u0004\tiA\u0001\u0002D\u0007V!\u0011qBA\u000b#\rQ\u0016\u0011\u0003\t\u0005\u001dN\u000b\u0019\u0002E\u0002W\u0003+!q!a\u0006\u0002\n\t\u0007\u0011LA\u0001Y\u0003\u0015Ig\u000eZ3y+\t\ti\u0002E\u0002K\u0003?I1!!\tE\u0005\rIe\u000e^\u0001\nS:$W\r_0%KF$B!a\n\u0002.A\u0019!*!\u000b\n\u0007\u0005-BI\u0001\u0003V]&$\b\"CA\u0018\u0007\u0005\u0005\t\u0019AA\u000f\u0003\rAH%M\u0001\u0007S:$W\r\u001f\u0011\u0002\r!Le\u000eZ3y\u0003)A\u0017J\u001c3fq~#S-\u001d\u000b\u0005\u0003O\tI\u0004C\u0005\u00020\u0019\t\t\u00111\u0001\u0002\u001e\u00059\u0001.\u00138eKb\u0004\u0013!\u0003;pi\u0006d7+\u001b>f+\t\t\t\u0005E\u0002K\u0003\u0007J1!!\u0012E\u0005\u0011auN\\4\u0002\u001bQ|G/\u00197TSj,w\fJ3r)\u0011\t9#a\u0013\t\u0013\u0005=\u0012\"!AA\u0002\u0005\u0005\u0013A\u0003;pi\u0006d7+\u001b>fA\u0005Q1-^7vY\u0006$\u0018N^3\u0015\t\u0005\u0005\u00131\u000b\u0005\b\u0003+Z\u0001\u0019AA\u000f\u0003\u0005I\u0017!\u00048fqR\u0014En\\2l'&TX-\u0001\tfM\u001aL7-[3oiN#X\r\u001d9feV!\u0011QLA4)\u0011\ty&a$\u0013\r\u0005\u0005\u0014QMA?\r\u0019\t\u0019\u0007\u0001\u0001\u0002`\taAH]3gS:,W.\u001a8u}A\u0019a+a\u001a\u0005\u000f\u0005%TB1\u0001\u0002l\t\t1+E\u0002[\u0003[\u0002D!a\u001c\u0002zA1\u0011\u0011OA:\u0003oj\u0011!U\u0005\u0004\u0003k\n&aB*uKB\u0004XM\u001d\t\u0004-\u0006eDaCA>\u0003O\n\t\u0011!A\u0003\u0002e\u00131a\u0018\u00132!\u0011\ty(!#\u000f\t\u0005\u0005\u0015Q\u0011\b\u0004S\u0006\r\u0015B\u0001*E\u0013\r\t9)U\u0001\b'R,\u0007\u000f]3s\u0013\u0011\tY)!$\u0003\u001d\u00153g-[2jK:$8\u000b\u001d7ji*\u0019\u0011qQ)\t\u000f\u0005EU\u0002q\u0001\u0002\u0014\u0006)1\u000f[1qKB9\u0011\u0011OAK+\u0006\u0015\u0014bAAL#\na1\u000b^3qa\u0016\u00148\u000b[1qK\u000691\u000f^3qa\u0016\u0014X\u0003BAO\u0003K#B!a(\u00024J1\u0011\u0011UAR\u0003{2a!a\u0019\u0001\u0001\u0005}\u0005c\u0001,\u0002&\u00129\u0011\u0011\u000e\bC\u0002\u0005\u001d\u0016c\u0001.\u0002*B\"\u00111VAX!\u0019\t\t(a\u001d\u0002.B\u0019a+a,\u0005\u0017\u0005E\u0016QUA\u0001\u0002\u0003\u0015\t!\u0017\u0002\u0004?\u0012\u0012\u0004bBAI\u001d\u0001\u000f\u0011Q\u0017\t\b\u0003c\n)*VAR\u0003\u0019aWM\\4uQ\u0006I1N\\8x]NK'0Z\u0001\tg&TX\rT8oO\u0006)1\r\\3beR\u0011\u0011qE\u0001\tg\u0016,7n\u00157piR!\u0011\u0011IAc\u0011\u001d\t9m\u0005a\u0001\u0003\u0003\n!!\u001b=\u0002\u0017\u0005\u001b7-^7vY\u0006$xN\u001d\t\u0004\u0003\u000b)2CA\u000bJ)\t\tY-A\u0005u_\u001a\u000b7\r^8ssV1\u0011Q[Aq\u0003K$B!a6\u0003\"Q!\u0011\u0011\\At!!\t\t(a7\u0002`\u0006\r\u0018bAAo#\n9a)Y2u_JL\bc\u0001,\u0002b\u0012)\u0001l\u0006b\u00013B\u0019a+!:\u0005\u000bu<\"\u0019A-\t\u000f\u0005%x\u0003q\u0001\u0002l\u0006i1-\u00198BG\u000e,X.\u001e7bi\u0016\u0004r!!<,\u0003?\f\u0019/D\u0001\u0016\u0005]\t5mY;nk2\fGo\u001c:GC\u000e$xN]=TQ\u0006\u0004X-\u0006\u0004\u0002t\u0006u(\u0011A\n\u0003W%\u000bqAZ1di>\u0014\u00180\u0006\u0002\u0002zBA\u0011\u0011OAn\u0003w\fy\u0010E\u0002W\u0003{$Q\u0001W\u0016C\u0002e\u00032A\u0016B\u0001\t\u0015i8F1\u0001Z\u0003\u0015)W\u000e\u001d;z+\t\ty0K\u0002,\u0005\u00131aAa\u0003,\u0001\t5!!\u0004\u001fm_\u000e\fG\u000eI2iS2$gh\u0005\u0004\u0003\n\t=!q\u0004\t\u0005\u0005#\u0011Y\"\u0004\u0002\u0003\u0014)!!Q\u0003B\f\u0003\u0011a\u0017M\\4\u000b\u0005\te\u0011\u0001\u00026bm\u0006LAA!\b\u0003\u0014\t1qJ\u00196fGR\u0004r!!<,\u0003w\fy\u0010C\u0004\u0003$]\u0001\rA!\n\u0002\u0005M\fgbAA\u0003)\u0005!aM]8n+\u0019\u0011YC!\u000f\u00032Q!!Q\u0006B\u001e)\u0011\u0011yCa\r\u0011\u0007Y\u0013\t\u0004B\u0003~1\t\u0007\u0011\fC\u0004\u0002jb\u0001\u001dA!\u000e\u0011\u000f\u000558Fa\u000e\u00030A\u0019aK!\u000f\u0005\u000baC\"\u0019A-\t\u000f\tu\u0002\u00041\u0001\u0003@\u000511o\\;sG\u0016\u0004bA!\u0011\u0003H\t]bbA5\u0003D%\u0019!Q\t#\u0002\u000fA\f7m[1hK&!!\u0011\nB&\u00051IE/\u001a:bE2,wJ\\2f\u0015\r\u0011)\u0005R\u000b\u0007\u0005\u001f\u0012YFa\u0015\u0015\t\tE#Q\u000b\t\u0004-\nMC!B?\u001a\u0005\u0004I\u0006bBAu3\u0001\u000f!q\u000b\t\b\u0003[\\#\u0011\fB)!\r1&1\f\u0003\u00061f\u0011\r!W\u0001\u0006CB\u0004H._\u000b\u0007\u0005C\u0012yGa\u001a\u0015\t\t\r$\u0011\u000f\u000b\u0005\u0005K\u0012I\u0007E\u0002W\u0005O\"Q! \u000eC\u0002eCq!!;\u001b\u0001\b\u0011Y\u0007E\u0004\u0002n.\u0012iG!\u001a\u0011\u0007Y\u0013y\u0007B\u0003Y5\t\u0007\u0011\fC\u0004\u0003ti\u0001\rA!\u001e\u0002\u000b\u0015dW-\\:\u0011\u000b)\u00139H!\u001c\n\u0007\teDI\u0001\u0006=e\u0016\u0004X-\u0019;fIz\nq!\u001b;fe\u0006$X-\u0006\u0004\u0003\u0000\t=%q\u0011\u000b\u0007\u0005\u0003\u0013YJa(\u0015\t\t\r%\u0011\u0013\u000b\u0005\u0005\u000b\u0013I\tE\u0002W\u0005\u000f#Q!`\u000eC\u0002eCq!!;\u001c\u0001\b\u0011Y\tE\u0004\u0002n.\u0012iI!\"\u0011\u0007Y\u0013y\tB\u0003Y7\t\u0007\u0011\fC\u0004\u0003\u0014n\u0001\rA!&\u0002\u0003\u0019\u0004rA\u0013BL\u0005\u001b\u0013i)C\u0002\u0003\u001a\u0012\u0013\u0011BR;oGRLwN\\\u0019\t\u000f\tu5\u00041\u0001\u0003\u000e\u0006)1\u000f^1si\"9!\u0011U\u000eA\u0002\u0005u\u0011a\u00017f]\u00061QO\u001c4pY\u0012,\u0002Ba*\u00038\n}&q\u0016\u000b\u0005\u0005S\u0013i\r\u0006\u0003\u0003,\neF\u0003\u0002BW\u0005c\u00032A\u0016BX\t\u0015iHD1\u0001Z\u0011\u001d\tI\u000f\ba\u0002\u0005g\u0003r!!<,\u0005k\u0013i\u000bE\u0002W\u0005o#Q\u0001\u0017\u000fC\u0002eCqAa%\u001d\u0001\u0004\u0011Y\fE\u0004K\u0005/\u0013iL!1\u0011\u0007Y\u0013y\f\u0002\u0004\u0002jq\u0011\r!\u0017\t\u0006\u0015\n\r'qY\u0005\u0004\u0005\u000b$%AB(qi&|g\u000eE\u0004K\u0005\u0013\u0014)L!0\n\u0007\t-GI\u0001\u0004UkBdWM\r\u0005\b\u0005\u001fd\u0002\u0019\u0001B_\u0003\u0011Ig.\u001b;\u0002\u000bI\fgnZ3\u0016\r\tU'\u0011\u001eBn)\u0019\u00119Na<\u0003rR1!\u0011\u001cBo\u0005W\u00042A\u0016Bn\t\u0015iXD1\u0001Z\u0011%\u0011y.HA\u0001\u0002\b\u0011\t/\u0001\u0006fm&$WM\\2fIE\u0002bA!\u0011\u0003d\n\u001d\u0018\u0002\u0002Bs\u0005\u0017\u0012\u0001\"\u00138uK\u001e\u0014\u0018\r\u001c\t\u0004-\n%H!\u0002-\u001e\u0005\u0004I\u0006bBAu;\u0001\u000f!Q\u001e\t\b\u0003[\\#q\u001dBm\u0011\u001d\u0011i*\ba\u0001\u0005ODqAa=\u001e\u0001\u0004\u00119/A\u0002f]\u0012,bAa>\u0004\b\tuH\u0003\u0003B}\u0007\u001b\u0019ya!\u0005\u0015\r\tm(q`B\u0005!\r1&Q \u0003\u0006{z\u0011\r!\u0017\u0005\n\u0007\u0003q\u0012\u0011!a\u0002\u0007\u0007\t!\"\u001a<jI\u0016t7-\u001a\u00133!\u0019\u0011\tEa9\u0004\u0006A\u0019aka\u0002\u0005\u000bas\"\u0019A-\t\u000f\u0005%h\u0004q\u0001\u0004\fA9\u0011Q^\u0016\u0004\u0006\tm\bb\u0002BO=\u0001\u00071Q\u0001\u0005\b\u0005gt\u0002\u0019AB\u0003\u0011\u001d\u0019\u0019B\ba\u0001\u0007\u000b\tAa\u001d;fa\u0006Qa.Z<Ck&dG-\u001a:\u0016\r\re1qDB\u0012)\u0011\u0019Yb!\n\u0011\r9K8QDB\u0011!\r16q\u0004\u0003\u00061~\u0011\r!\u0017\t\u0004-\u000e\rB!B? \u0005\u0004I\u0006bBAu?\u0001\u000f1q\u0005\t\b\u0003[\\3QDB\u0011\u0003\u00111\u0017\u000e\u001c7\u0016\r\r52QHB\u001b)\u0011\u0019yc!\u0013\u0015\t\rE2q\b\u000b\u0005\u0007g\u00199\u0004E\u0002W\u0007k!Q! \u0011C\u0002eCq!!;!\u0001\b\u0019I\u0004E\u0004\u0002n.\u001aYda\r\u0011\u0007Y\u001bi\u0004B\u0003YA\t\u0007\u0011\f\u0003\u0005\u0004B\u0001\"\t\u0019AB\"\u0003\u0011)G.Z7\u0011\u000b)\u001b)ea\u000f\n\u0007\r\u001dCI\u0001\u0005=Eft\u0017-\\3?\u0011\u001d\u0019Y\u0005\ta\u0001\u0003;\t\u0011A\\\u000b\u0007\u0007\u001f\u001a)g!\u0018\u0015\r\rE31NB8)\u0011\u0019\u0019fa\u001a\u0015\t\rU3q\f\t\u0007\u0003\u000b\u00199fa\u0017\n\u0007\re#I\u0001\bB]f\f5mY;nk2\fGo\u001c:\u0011\u0007Y\u001bi\u0006B\u0003~C\t\u0007\u0011\fC\u0004\u0002j\u0006\u0002\u001da!\u0019\u0011\u000f\u000558fa\u0019\u0004\\A\u0019ak!\u001a\u0005\u000ba\u000b#\u0019A-\t\u0011\r\u0005\u0013\u0005\"a\u0001\u0007S\u0002RASB#\u0007GBqa!\u001c\"\u0001\u0004\ti\"\u0001\u0002oc!91\u0011O\u0011A\u0002\u0005u\u0011A\u000183+\u0019\u0019)h!#\u0004\u0002RA1qOBH\u0007#\u001b\u0019\n\u0006\u0003\u0004z\r-E\u0003BB>\u0007\u0007\u0003b!!\u0002\u0004X\ru\u0004CBA\u0003\u0007/\u001ay\bE\u0002W\u0007\u0003#Q! \u0012C\u0002eCq!!;#\u0001\b\u0019)\tE\u0004\u0002n.\u001a9ia \u0011\u0007Y\u001bI\tB\u0003YE\t\u0007\u0011\f\u0003\u0005\u0004B\t\"\t\u0019ABG!\u0015Q5QIBD\u0011\u001d\u0019iG\ta\u0001\u0003;Aqa!\u001d#\u0001\u0004\ti\u0002C\u0004\u0004\u0016\n\u0002\r!!\b\u0002\u00059\u001cTCBBM\u0007_\u001b9\u000b\u0006\u0006\u0004\u001c\u000eU6qWB]\u0007w#Ba!(\u00042R!1qTBU!\u0019\t)aa\u0016\u0004\"B1\u0011QAB,\u0007G\u0003b!!\u0002\u0004X\r\u0015\u0006c\u0001,\u0004(\u0012)Qp\tb\u00013\"9\u0011\u0011^\u0012A\u0004\r-\u0006cBAwW\r56Q\u0015\t\u0004-\u000e=F!\u0002-$\u0005\u0004I\u0006\u0002CB!G\u0011\u0005\raa-\u0011\u000b)\u001b)e!,\t\u000f\r54\u00051\u0001\u0002\u001e!91\u0011O\u0012A\u0002\u0005u\u0001bBBKG\u0001\u0007\u0011Q\u0004\u0005\b\u0007{\u001b\u0003\u0019AA\u000f\u0003\tqG'\u0006\u0004\u0004B\u000ee7\u0011\u001b\u000b\r\u0007\u0007\u001cyn!9\u0004d\u000e\u00158q\u001d\u000b\u0005\u0007\u000b\u001cY\u000e\u0006\u0003\u0004H\u000eM\u0007CBA\u0003\u0007/\u001aI\r\u0005\u0004\u0002\u0006\r]31\u001a\t\u0007\u0003\u000b\u00199f!4\u0011\r\u0005\u00151qKBh!\r16\u0011\u001b\u0003\u0006{\u0012\u0012\r!\u0017\u0005\b\u0003S$\u00039ABk!\u001d\tioKBl\u0007\u001f\u00042AVBm\t\u0015AFE1\u0001Z\u0011!\u0019\t\u0005\nCA\u0002\ru\u0007#\u0002&\u0004F\r]\u0007bBB7I\u0001\u0007\u0011Q\u0004\u0005\b\u0007c\"\u0003\u0019AA\u000f\u0011\u001d\u0019)\n\na\u0001\u0003;Aqa!0%\u0001\u0004\ti\u0002C\u0004\u0004j\u0012\u0002\r!!\b\u0002\u00059,\u0014\u0001\u0003;bEVd\u0017\r^3\u0016\r\r=8q`B|)\u0011\u0019\t\u0010\"\u0002\u0015\t\rMH\u0011\u0001\u000b\u0005\u0007k\u001cI\u0010E\u0002W\u0007o$Q!`\u0013C\u0002eCq!!;&\u0001\b\u0019Y\u0010E\u0004\u0002n.\u001aip!>\u0011\u0007Y\u001by\u0010B\u0003YK\t\u0007\u0011\fC\u0004\u0003\u0014\u0016\u0002\r\u0001b\u0001\u0011\u000f)\u00139*!\b\u0004~\"911J\u0013A\u0002\u0005uQC\u0002C\u0005\t7!\u0019\u0002\u0006\u0004\u0005\f\u0011\u0015Bq\u0005\u000b\u0005\t\u001b!i\u0002\u0006\u0003\u0005\u0010\u0011U\u0001CBA\u0003\u0007/\"\t\u0002E\u0002W\t'!Q! \u0014C\u0002eCq!!;'\u0001\b!9\u0002E\u0004\u0002n.\"I\u0002\"\u0005\u0011\u0007Y#Y\u0002B\u0003YM\t\u0007\u0011\fC\u0004\u0003\u0014\u001a\u0002\r\u0001b\b\u0011\u0013)#\t#!\b\u0002\u001e\u0011e\u0011b\u0001C\u0012\t\nIa)\u001e8di&|gN\r\u0005\b\u0007[2\u0003\u0019AA\u000f\u0011\u001d\u0019\tH\na\u0001\u0003;)b\u0001b\u000b\u0005@\u0011]B\u0003\u0003C\u0017\t\u0013\"Y\u0005\"\u0014\u0015\t\u0011=B\u0011\t\u000b\u0005\tc!I\u0004\u0005\u0004\u0002\u0006\r]C1\u0007\t\u0007\u0003\u000b\u00199\u0006\"\u000e\u0011\u0007Y#9\u0004B\u0003~O\t\u0007\u0011\fC\u0004\u0002j\u001e\u0002\u001d\u0001b\u000f\u0011\u000f\u000558\u0006\"\u0010\u00056A\u0019a\u000bb\u0010\u0005\u000ba;#\u0019A-\t\u000f\tMu\u00051\u0001\u0005DAY!\n\"\u0012\u0002\u001e\u0005u\u0011Q\u0004C\u001f\u0013\r!9\u0005\u0012\u0002\n\rVt7\r^5p]NBqa!\u001c(\u0001\u0004\ti\u0002C\u0004\u0004r\u001d\u0002\r!!\b\t\u000f\rUu\u00051\u0001\u0002\u001eU1A\u0011\u000bC4\t?\"\"\u0002b\u0015\u0005r\u0011MDQ\u000fC<)\u0011!)\u0006\"\u001b\u0015\t\u0011]C\u0011\r\t\u0007\u0003\u000b\u00199\u0006\"\u0017\u0011\r\u0005\u00151q\u000bC.!\u0019\t)aa\u0016\u0005^A\u0019a\u000bb\u0018\u0005\u000buD#\u0019A-\t\u000f\u0005%\b\u0006q\u0001\u0005dA9\u0011Q^\u0016\u0005f\u0011u\u0003c\u0001,\u0005h\u0011)\u0001\f\u000bb\u00013\"9!1\u0013\u0015A\u0002\u0011-\u0004#\u0004&\u0005n\u0005u\u0011QDA\u000f\u0003;!)'C\u0002\u0005p\u0011\u0013\u0011BR;oGRLwN\u001c\u001b\t\u000f\r5\u0004\u00061\u0001\u0002\u001e!91\u0011\u000f\u0015A\u0002\u0005u\u0001bBBKQ\u0001\u0007\u0011Q\u0004\u0005\b\u0007{C\u0003\u0019AA\u000f+\u0019!Y\bb%\u0005\fRaAQ\u0010CO\t?#\t\u000bb)\u0005&R!Aq\u0010CK)\u0011!\t\t\"$\u0011\r\u0005\u00151q\u000bCB!\u0019\t)aa\u0016\u0005\u0006B1\u0011QAB,\t\u000f\u0003b!!\u0002\u0004X\u0011%\u0005c\u0001,\u0005\f\u0012)Q0\u000bb\u00013\"9\u0011\u0011^\u0015A\u0004\u0011=\u0005cBAwW\u0011EE\u0011\u0012\t\u0004-\u0012ME!\u0002-*\u0005\u0004I\u0006b\u0002BJS\u0001\u0007Aq\u0013\t\u0010\u0015\u0012e\u0015QDA\u000f\u0003;\ti\"!\b\u0005\u0012&\u0019A1\u0014#\u0003\u0013\u0019+hn\u0019;j_:,\u0004bBB7S\u0001\u0007\u0011Q\u0004\u0005\b\u0007cJ\u0003\u0019AA\u000f\u0011\u001d\u0019)*\u000ba\u0001\u0003;Aqa!0*\u0001\u0004\ti\u0002C\u0004\u0004j&\u0002\r!!\b\u0002\r\r|gnY1u+\u0019!Y\u000b\"/\u00052R!AQ\u0016C^)\u0011!y\u000bb-\u0011\u0007Y#\t\fB\u0003~U\t\u0007\u0011\fC\u0004\u0002j*\u0002\u001d\u0001\".\u0011\u000f\u000558\u0006b.\u00050B\u0019a\u000b\"/\u0005\u000baS#\u0019A-\t\u000f\u0011u&\u00061\u0001\u0005@\u0006\u0019\u0001p]:\u0011\u000b)\u00139\b\"1\u0011\r\t\u0005C1\u0019C\\\u0013\u0011!)Ma\u0013\u0003\u0011%#XM]1cY\u0016\fq#Q2dk6,H.\u0019;pe\u001a\u000b7\r^8ssNC\u0017\r]3\u0011\u0007\u00055xf\u0005\u00030\u0013\u00125\u0007cAAw{\t\u0011Cj\\<Qe&|'/\u001b;z\u0003\u000e\u001cW/\\;mCR|'OR1di>\u0014\u0018p\u00155ba\u0016\u001c\"!P%\u0002\r\u0011Jg.\u001b;%\u0003i\tg._!dGVlW\u000f\\1u_J4\u0015m\u0019;pef\u001c\u0006.\u00199f+\u0011!I\u000eb8\u0016\u0005\u0011m\u0007cBAwW\u0011uG\u0011\u001d\t\u0004-\u0012}G!\u0002-@\u0005\u0004I\u0006CBA\u0003\u0007/\"i.A\u0012b]f\f5mY;nk2\fGo\u001c:GC\u000e$xN]=TQ\u0006\u0004X\r\u0015:pi>$\u0018\u0010]3\u0016\u0005\u0011\u001d(#\u0002Cu\u0013\u0012-hABA2\u0001\u0002!9\u000f\u0005\u0004\u0002n.JEQ\u001e\t\u0006\u0003\u000b\u00199&S\u0015\u0003{=\"\"\u0001\"3\u0002;\u0011|WO\u00197f\u0003\u000e\u001cW/\\;mCR|'OR1di>\u0014\u0018p\u00155ba\u0016,\"\u0001b>\u0011\u000f\u000558\u0006\"?\u0005\u0000B\u0019!\nb?\n\u0007\u0011uHI\u0001\u0004E_V\u0014G.\u001a\t\u0005\u0003\u000b)\t!C\u0002\u0006\u0004\t\u0013\u0011\u0003R8vE2,\u0017iY2v[Vd\u0017\r^8s\u0003y!w.\u001e2mK\u0006\u001b7-^7vY\u0006$xN\u001d$bGR|'/_*iCB,\u0007%\u0001\u000ej]R\f5mY;nk2\fGo\u001c:GC\u000e$xN]=TQ\u0006\u0004X-\u0006\u0002\u0006\fA9\u0011Q^\u0016\u0002\u001e\u00155\u0001\u0003BA\u0003\u000b\u001fI1!\"\u0005C\u00059Ie\u000e^!dGVlW\u000f\\1u_J\f1$\u001b8u\u0003\u000e\u001cW/\\;mCR|'OR1di>\u0014\u0018p\u00155ba\u0016\u0004\u0013a\u00077p]\u001e\f5mY;nk2\fGo\u001c:GC\u000e$xN]=TQ\u0006\u0004X-\u0006\u0002\u0006\u001aA9\u0011Q^\u0016\u0002B\u0015m\u0001\u0003BA\u0003\u000b;I1!b\bC\u0005=auN\\4BG\u000e,X.\u001e7bi>\u0014\u0018\u0001\b7p]\u001e\f5mY;nk2\fGo\u001c:GC\u000e$xN]=TQ\u0006\u0004X\rI\u0001\u001fU\u0012{WO\u00197f\u0003\u000e\u001cW/\\;mCR|'OR1di>\u0014\u0018p\u00155ba\u0016,\"!b\n\u0011\u000f\u000558&\"\u000b\u0005\u0000B!!\u0011CC\u0016\u0013\u0011!iPa\u0005\u0002?)$u.\u001e2mK\u0006\u001b7-^7vY\u0006$xN\u001d$bGR|'/_*iCB,\u0007%A\u0010k\u0013:$XmZ3s\u0003\u000e\u001cW/\\;mCR|'OR1di>\u0014\u0018p\u00155ba\u0016,\"!b\r\u0011\u000f\u000558&\"\u000e\u0006\u000eA!!\u0011CC\u001c\u0013\u0011)IDa\u0005\u0003\u000f%sG/Z4fe\u0006\u0001#.\u00138uK\u001e,'/Q2dk6,H.\u0019;pe\u001a\u000b7\r^8ssNC\u0017\r]3!\u0003qQGj\u001c8h\u0003\u000e\u001cW/\\;mCR|'OR1di>\u0014\u0018p\u00155ba\u0016,\"!\"\u0011\u0011\u000f\u000558&b\u0011\u0006\u001cA!!\u0011CC#\u0013\u0011\t)Ea\u0005\u0002;)duN\\4BG\u000e,X.\u001e7bi>\u0014h)Y2u_JL8\u000b[1qK\u0002\u0002")
public abstract class Accumulator<A, CC extends scala.collection.mutable.Seq<Object>, C extends scala.collection.mutable.Seq<A>>
implements scala.collection.mutable.Seq<A>,
Builder<A, C> {
    private int index = 0;
    private int hIndex = 0;
    private long totalSize = 0L;

    /*
     * WARNING - void declaration
     */
    public static <A, C> AnyAccumulator<AnyAccumulator<AnyAccumulator<AnyAccumulator<C>>>> tabulate(int n1, int n2, int n3, int n4, int n5, Function5<Object, Object, Object, Object, Object, A> f, AccumulatorFactoryShape<A, C> canAccumulate) {
        void tabulate_tabulate_canAccumulate;
        Accumulator$ tabulate_this = Accumulator$.MODULE$;
        Accumulator$AccumulatorFactoryShape$ tabulate_anyAccumulatorFactoryShape_this = Accumulator$AccumulatorFactoryShape$.MODULE$;
        Object var8_8 = null;
        AccumulatorFactoryShape<Object, AnyAccumulator<Object>> accumulatorFactoryShape = tabulate_anyAccumulatorFactoryShape_this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();
        Function1<Object, AnyAccumulator> & Serializable tabulate_tabulate_f = arg_0 -> Accumulator$.$anonfun$tabulate$7$adapted(n2, n3, n4, n5, f, canAccumulate, arg_0);
        return (AnyAccumulator)tabulate_this.from(new View.Tabulate(n1, tabulate_tabulate_f), tabulate_tabulate_canAccumulate);
    }

    /*
     * WARNING - void declaration
     */
    public static <A, C> AnyAccumulator<AnyAccumulator<AnyAccumulator<C>>> tabulate(int n1, int n2, int n3, int n4, Function4<Object, Object, Object, Object, A> f, AccumulatorFactoryShape<A, C> canAccumulate) {
        void tabulate_tabulate_canAccumulate;
        Accumulator$ tabulate_this = Accumulator$.MODULE$;
        Accumulator$AccumulatorFactoryShape$ tabulate_anyAccumulatorFactoryShape_this = Accumulator$AccumulatorFactoryShape$.MODULE$;
        Object var7_7 = null;
        AccumulatorFactoryShape<Object, AnyAccumulator<Object>> accumulatorFactoryShape = tabulate_anyAccumulatorFactoryShape_this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();
        Function1<Object, AnyAccumulator> & Serializable tabulate_tabulate_f = arg_0 -> Accumulator$.$anonfun$tabulate$5$adapted(n2, n3, n4, f, canAccumulate, arg_0);
        return (AnyAccumulator)tabulate_this.from(new View.Tabulate(n1, tabulate_tabulate_f), tabulate_tabulate_canAccumulate);
    }

    /*
     * WARNING - void declaration
     */
    public static <A, C> AnyAccumulator<AnyAccumulator<C>> tabulate(int n1, int n2, int n3, Function3<Object, Object, Object, A> f, AccumulatorFactoryShape<A, C> canAccumulate) {
        void tabulate_tabulate_canAccumulate;
        Accumulator$ tabulate_this = Accumulator$.MODULE$;
        Accumulator$AccumulatorFactoryShape$ tabulate_anyAccumulatorFactoryShape_this = Accumulator$AccumulatorFactoryShape$.MODULE$;
        Object var6_6 = null;
        AccumulatorFactoryShape<Object, AnyAccumulator<Object>> accumulatorFactoryShape = tabulate_anyAccumulatorFactoryShape_this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();
        Function1<Object, AnyAccumulator> & Serializable tabulate_tabulate_f = arg_0 -> Accumulator$.$anonfun$tabulate$3$adapted(n2, n3, f, canAccumulate, arg_0);
        return (AnyAccumulator)tabulate_this.from(new View.Tabulate(n1, tabulate_tabulate_f), tabulate_tabulate_canAccumulate);
    }

    /*
     * WARNING - void declaration
     */
    public static <A, C> AnyAccumulator<C> tabulate(int n1, int n2, Function2<Object, Object, A> f, AccumulatorFactoryShape<A, C> canAccumulate) {
        void tabulate_tabulate_canAccumulate;
        Accumulator$ tabulate_this = Accumulator$.MODULE$;
        Accumulator$AccumulatorFactoryShape$ tabulate_anyAccumulatorFactoryShape_this = Accumulator$AccumulatorFactoryShape$.MODULE$;
        Object var5_5 = null;
        AccumulatorFactoryShape<Object, AnyAccumulator<Object>> accumulatorFactoryShape = tabulate_anyAccumulatorFactoryShape_this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();
        Function1<Object, Object> & Serializable tabulate_tabulate_f = arg_0 -> Accumulator$.$anonfun$tabulate$1$adapted(n2, f, canAccumulate, arg_0);
        return (AnyAccumulator)tabulate_this.from(new View.Tabulate(n1, tabulate_tabulate_f), tabulate_tabulate_canAccumulate);
    }

    public static <A, C> C tabulate(int n, Function1<Object, A> f, AccumulatorFactoryShape<A, C> canAccumulate) {
        return Accumulator$.MODULE$.from(new View.Tabulate<A>(n, f), canAccumulate);
    }

    /*
     * WARNING - void declaration
     */
    public static <A, C> AnyAccumulator<AnyAccumulator<AnyAccumulator<AnyAccumulator<C>>>> fill(int n1, int n2, int n3, int n4, int n5, Function0<A> elem, AccumulatorFactoryShape<A, C> canAccumulate) {
        void fill_fill_canAccumulate;
        Accumulator$ fill_this = Accumulator$.MODULE$;
        Accumulator$AccumulatorFactoryShape$ fill_anyAccumulatorFactoryShape_this = Accumulator$AccumulatorFactoryShape$.MODULE$;
        Object var8_8 = null;
        AccumulatorFactoryShape<Object, AnyAccumulator<Object>> accumulatorFactoryShape = fill_anyAccumulatorFactoryShape_this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();
        Function0<AnyAccumulator> & Serializable fill_fill_elem = () -> Accumulator$.$anonfun$fill$4(n2, n3, n4, n5, elem, canAccumulate);
        return (AnyAccumulator)fill_this.from(new View.Fill(n1, fill_fill_elem), fill_fill_canAccumulate);
    }

    /*
     * WARNING - void declaration
     */
    public static <A, C> AnyAccumulator<AnyAccumulator<AnyAccumulator<C>>> fill(int n1, int n2, int n3, int n4, Function0<A> elem, AccumulatorFactoryShape<A, C> canAccumulate) {
        void fill_fill_canAccumulate;
        Accumulator$ fill_this = Accumulator$.MODULE$;
        Accumulator$AccumulatorFactoryShape$ fill_anyAccumulatorFactoryShape_this = Accumulator$AccumulatorFactoryShape$.MODULE$;
        Object var7_7 = null;
        AccumulatorFactoryShape<Object, AnyAccumulator<Object>> accumulatorFactoryShape = fill_anyAccumulatorFactoryShape_this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();
        Function0<AnyAccumulator> & Serializable fill_fill_elem = () -> Accumulator$.$anonfun$fill$3(n2, n3, n4, elem, canAccumulate);
        return (AnyAccumulator)fill_this.from(new View.Fill(n1, fill_fill_elem), fill_fill_canAccumulate);
    }

    /*
     * WARNING - void declaration
     */
    public static <A, C> AnyAccumulator<AnyAccumulator<C>> fill(int n1, int n2, int n3, Function0<A> elem, AccumulatorFactoryShape<A, C> canAccumulate) {
        void fill_fill_canAccumulate;
        Accumulator$ fill_this = Accumulator$.MODULE$;
        Accumulator$AccumulatorFactoryShape$ fill_anyAccumulatorFactoryShape_this = Accumulator$AccumulatorFactoryShape$.MODULE$;
        Object var6_6 = null;
        AccumulatorFactoryShape<Object, AnyAccumulator<Object>> accumulatorFactoryShape = fill_anyAccumulatorFactoryShape_this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();
        Function0<AnyAccumulator> & Serializable fill_fill_elem = () -> Accumulator$.$anonfun$fill$2(n2, n3, elem, canAccumulate);
        return (AnyAccumulator)fill_this.from(new View.Fill(n1, fill_fill_elem), fill_fill_canAccumulate);
    }

    /*
     * WARNING - void declaration
     */
    public static <A, C> AnyAccumulator<C> fill(int n1, int n2, Function0<A> elem, AccumulatorFactoryShape<A, C> canAccumulate) {
        void fill_fill_canAccumulate;
        Accumulator$ fill_this = Accumulator$.MODULE$;
        Accumulator$AccumulatorFactoryShape$ fill_anyAccumulatorFactoryShape_this = Accumulator$AccumulatorFactoryShape$.MODULE$;
        Object var5_5 = null;
        AccumulatorFactoryShape<Object, AnyAccumulator<Object>> accumulatorFactoryShape = fill_anyAccumulatorFactoryShape_this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();
        Function0<Object> & Serializable fill_fill_elem = () -> Accumulator$.$anonfun$fill$1(n2, elem, canAccumulate);
        return (AnyAccumulator)fill_this.from(new View.Fill(n1, fill_fill_elem), fill_fill_canAccumulate);
    }

    public static <A, C> C fill(int n, Function0<A> elem, AccumulatorFactoryShape<A, C> canAccumulate) {
        return Accumulator$.MODULE$.from(new View.Fill<A>(n, elem), canAccumulate);
    }

    public static <A, C> Builder<A, C> newBuilder(AccumulatorFactoryShape<A, C> canAccumulate) {
        return Accumulator$.MODULE$.newBuilder(canAccumulate);
    }

    public static <A, C> C range(A start2, A end, A step, Integral<A> evidence$2, AccumulatorFactoryShape<A, C> canAccumulate) {
        return Accumulator$.MODULE$.range(start2, end, step, evidence$2, canAccumulate);
    }

    public static <A, C> C range(A start2, A end, Integral<A> evidence$1, AccumulatorFactoryShape<A, C> canAccumulate) {
        return Accumulator$.MODULE$.range(start2, end, evidence$1, canAccumulate);
    }

    public static <A, S, C> C unfold(S init2, Function1<S, Option<Tuple2<A, S>>> f, AccumulatorFactoryShape<A, C> canAccumulate) {
        return Accumulator$.MODULE$.from(new View.Unfold<A, S>(init2, f), canAccumulate);
    }

    public static <A, C> C iterate(A start2, int len, Function1<A, A> f, AccumulatorFactoryShape<A, C> canAccumulate) {
        return Accumulator$.MODULE$.from(new View.Iterate<A>(start2, len, f), canAccumulate);
    }

    public static <A, C> C from(IterableOnce<A> source2, AccumulatorFactoryShape<A, C> canAccumulate) {
        return Accumulator$.MODULE$.from(source2, canAccumulate);
    }

    public static <A, C> Factory<A, C> toFactory(Accumulator$ sa, AccumulatorFactoryShape<A, C> canAccumulate) {
        return canAccumulate.factory();
    }

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
    public <NewTo> Builder<A, NewTo> mapResult(Function1<C, NewTo> f) {
        return Builder.mapResult$(this, f);
    }

    @Override
    public final Growable<A> $plus$eq(A elem) {
        return Growable.$plus$eq$(this, elem);
    }

    @Override
    public final Growable<A> $plus$eq(A elem1, A elem2, scala.collection.immutable.Seq<A> elems) {
        return Growable.$plus$eq$(this, elem1, elem2, elems);
    }

    @Override
    public Growable<A> addAll(IterableOnce<A> xs) {
        return Growable.addAll$(this, xs);
    }

    @Override
    public final Growable<A> $plus$plus$eq(IterableOnce<A> xs) {
        return Growable.$plus$plus$eq$(this, xs);
    }

    @Override
    public SeqFactory<scala.collection.mutable.Seq> iterableFactory() {
        return scala.collection.mutable.Seq.iterableFactory$(this);
    }

    @Override
    public Object clone() {
        return SeqOps.clone$(this);
    }

    @Override
    public final SeqOps<A, scala.collection.mutable.Seq, scala.collection.mutable.Seq<A>> transform(Function1<A, A> f) {
        return SeqOps.transform$(this, f);
    }

    @Override
    public /* synthetic */ Object scala$collection$mutable$Cloneable$$super$clone() {
        return super.clone();
    }

    @Override
    public boolean canEqual(Object that) {
        return Seq.canEqual$(this, that);
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
    public String toString() {
        return Seq.toString$(this);
    }

    @Override
    public String stringPrefix() {
        return Seq.stringPrefix$(this);
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
    public SeqView<A> view() {
        return scala.collection.SeqOps.view$(this);
    }

    @Override
    public Object prepended(Object elem) {
        return scala.collection.SeqOps.prepended$(this, elem);
    }

    @Override
    public final Object $plus$colon(Object elem) {
        return scala.collection.SeqOps.$plus$colon$(this, elem);
    }

    @Override
    public Object appended(Object elem) {
        return scala.collection.SeqOps.appended$(this, elem);
    }

    @Override
    public final Object $colon$plus(Object elem) {
        return scala.collection.SeqOps.$colon$plus$(this, elem);
    }

    @Override
    public Object prependedAll(IterableOnce prefix) {
        return scala.collection.SeqOps.prependedAll$(this, prefix);
    }

    @Override
    public final Object $plus$plus$colon(IterableOnce prefix) {
        return scala.collection.SeqOps.$plus$plus$colon$(this, prefix);
    }

    @Override
    public Object appendedAll(IterableOnce suffix) {
        return scala.collection.SeqOps.appendedAll$(this, suffix);
    }

    @Override
    public final Object $colon$plus$plus(IterableOnce suffix) {
        return scala.collection.SeqOps.$colon$plus$plus$(this, suffix);
    }

    @Override
    public final Object concat(IterableOnce suffix) {
        return scala.collection.SeqOps.concat$(this, suffix);
    }

    @Override
    public final Object union(Seq that) {
        return scala.collection.SeqOps.union$(this, that);
    }

    @Override
    public final int size() {
        return scala.collection.SeqOps.size$(this);
    }

    @Override
    public Object distinct() {
        return scala.collection.SeqOps.distinct$(this);
    }

    @Override
    public Object distinctBy(Function1 f) {
        return scala.collection.SeqOps.distinctBy$(this, f);
    }

    @Override
    public Object reverse() {
        return scala.collection.SeqOps.reverse$(this);
    }

    @Override
    public Iterator<A> reverseIterator() {
        return scala.collection.SeqOps.reverseIterator$(this);
    }

    @Override
    public <B> boolean startsWith(IterableOnce<B> that, int offset) {
        return scala.collection.SeqOps.startsWith$(this, that, offset);
    }

    @Override
    public <B> int startsWith$default$2() {
        return scala.collection.SeqOps.startsWith$default$2$(this);
    }

    @Override
    public <B> boolean endsWith(Iterable<B> that) {
        return scala.collection.SeqOps.endsWith$(this, that);
    }

    @Override
    public boolean isDefinedAt(int idx) {
        return scala.collection.SeqOps.isDefinedAt$(this, idx);
    }

    @Override
    public Object padTo(int len, Object elem) {
        return scala.collection.SeqOps.padTo$(this, len, elem);
    }

    @Override
    public final int segmentLength(Function1<A, Object> p) {
        return scala.collection.SeqOps.segmentLength$(this, p);
    }

    @Override
    public int segmentLength(Function1<A, Object> p, int from) {
        return scala.collection.SeqOps.segmentLength$(this, p, from);
    }

    @Override
    public final int prefixLength(Function1<A, Object> p) {
        return scala.collection.SeqOps.prefixLength$(this, p);
    }

    @Override
    public int indexWhere(Function1<A, Object> p, int from) {
        return scala.collection.SeqOps.indexWhere$(this, p, from);
    }

    @Override
    public int indexWhere(Function1<A, Object> p) {
        return scala.collection.SeqOps.indexWhere$(this, p);
    }

    @Override
    public <B> int indexOf(B elem, int from) {
        return scala.collection.SeqOps.indexOf$(this, elem, from);
    }

    @Override
    public <B> int indexOf(B elem) {
        return scala.collection.SeqOps.indexOf$(this, elem);
    }

    @Override
    public <B> int lastIndexOf(B elem, int end) {
        return scala.collection.SeqOps.lastIndexOf$(this, elem, end);
    }

    @Override
    public <B> int lastIndexOf$default$2() {
        return scala.collection.SeqOps.lastIndexOf$default$2$(this);
    }

    @Override
    public int lastIndexWhere(Function1<A, Object> p, int end) {
        return scala.collection.SeqOps.lastIndexWhere$(this, p, end);
    }

    @Override
    public int lastIndexWhere(Function1<A, Object> p) {
        return scala.collection.SeqOps.lastIndexWhere$(this, p);
    }

    @Override
    public <B> int indexOfSlice(Seq<B> that, int from) {
        return scala.collection.SeqOps.indexOfSlice$(this, that, from);
    }

    @Override
    public <B> int indexOfSlice(Seq<B> that) {
        return scala.collection.SeqOps.indexOfSlice$(this, that);
    }

    @Override
    public <B> int lastIndexOfSlice(Seq<B> that, int end) {
        return scala.collection.SeqOps.lastIndexOfSlice$(this, that, end);
    }

    @Override
    public <B> int lastIndexOfSlice(Seq<B> that) {
        return scala.collection.SeqOps.lastIndexOfSlice$(this, that);
    }

    @Override
    public Option<A> findLast(Function1<A, Object> p) {
        return scala.collection.SeqOps.findLast$(this, p);
    }

    @Override
    public <B> boolean containsSlice(Seq<B> that) {
        return scala.collection.SeqOps.containsSlice$(this, that);
    }

    @Override
    public <A1> boolean contains(A1 elem) {
        return scala.collection.SeqOps.contains$(this, elem);
    }

    @Override
    public Object reverseMap(Function1 f) {
        return scala.collection.SeqOps.reverseMap$(this, f);
    }

    @Override
    public Iterator<scala.collection.mutable.Seq<A>> permutations() {
        return scala.collection.SeqOps.permutations$(this);
    }

    @Override
    public Iterator<scala.collection.mutable.Seq<A>> combinations(int n) {
        return scala.collection.SeqOps.combinations$(this, n);
    }

    @Override
    public Object sorted(Ordering ord) {
        return scala.collection.SeqOps.sorted$(this, ord);
    }

    @Override
    public Object sortWith(Function2 lt) {
        return scala.collection.SeqOps.sortWith$(this, lt);
    }

    @Override
    public Object sortBy(Function1 f, Ordering ord) {
        return scala.collection.SeqOps.sortBy$(this, f, ord);
    }

    @Override
    public Range indices() {
        return scala.collection.SeqOps.indices$(this);
    }

    @Override
    public final int sizeCompare(int otherSize) {
        return scala.collection.SeqOps.sizeCompare$((scala.collection.SeqOps)this, otherSize);
    }

    @Override
    public int lengthCompare(int len) {
        return scala.collection.SeqOps.lengthCompare$((scala.collection.SeqOps)this, len);
    }

    @Override
    public final int sizeCompare(Iterable<?> that) {
        return scala.collection.SeqOps.sizeCompare$((scala.collection.SeqOps)this, that);
    }

    @Override
    public int lengthCompare(Iterable<?> that) {
        return scala.collection.SeqOps.lengthCompare$((scala.collection.SeqOps)this, that);
    }

    @Override
    public final IterableOps lengthIs() {
        return scala.collection.SeqOps.lengthIs$(this);
    }

    @Override
    public boolean isEmpty() {
        return scala.collection.SeqOps.isEmpty$(this);
    }

    @Override
    public <B> boolean sameElements(IterableOnce<B> that) {
        return scala.collection.SeqOps.sameElements$(this, that);
    }

    @Override
    public <B$> boolean corresponds(Seq<B$> that, Function2<A, B$, Object> p) {
        return scala.collection.SeqOps.corresponds$(this, that, p);
    }

    @Override
    public Object diff(Seq that) {
        return scala.collection.SeqOps.diff$(this, that);
    }

    @Override
    public Object intersect(Seq that) {
        return scala.collection.SeqOps.intersect$(this, that);
    }

    @Override
    public Object patch(int from, IterableOnce other, int replaced) {
        return scala.collection.SeqOps.patch$(this, from, other, replaced);
    }

    @Override
    public Object updated(int index, Object elem) {
        return scala.collection.SeqOps.updated$(this, index, elem);
    }

    @Override
    public <B> Map<B, Object> occCounts(Seq<B> sq) {
        return scala.collection.SeqOps.occCounts$(this, sq);
    }

    @Override
    public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
        return scala.collection.SeqOps.search$(this, elem, ord);
    }

    @Override
    public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
        return scala.collection.SeqOps.search$(this, elem, from, to, ord);
    }

    @Override
    public Option unapply(Object a) {
        return PartialFunction.unapply$(this, a);
    }

    @Override
    public PartialFunction<Object, A> elementWise() {
        return PartialFunction.elementWise$(this);
    }

    @Override
    public <A1, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
        return PartialFunction.orElse$(this, that);
    }

    @Override
    public <C$> PartialFunction<Object, C$> andThen(Function1<A, C$> k) {
        return PartialFunction.andThen$((PartialFunction)this, k);
    }

    @Override
    public <C$> PartialFunction<Object, C$> andThen(PartialFunction<A, C$> k) {
        return PartialFunction.andThen$((PartialFunction)this, k);
    }

    @Override
    public <R$> PartialFunction<R$, A> compose(PartialFunction<R$, Object> k) {
        return PartialFunction.compose$(this, k);
    }

    @Override
    public Function1<Object, Option<A>> lift() {
        return PartialFunction.lift$(this);
    }

    @Override
    public Object applyOrElse(Object x, Function1 function1) {
        return PartialFunction.applyOrElse$(this, x, function1);
    }

    @Override
    public <U> Function1<Object, Object> runWith(Function1<A, U> action) {
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
    public <A$> Function1<A$, A> compose(Function1<A$, Object> g2) {
        return Function1.compose$(this, g2);
    }

    @Override
    public final Iterable<A> toIterable() {
        return Iterable.toIterable$(this);
    }

    @Override
    public final Iterable<A> coll() {
        return Iterable.coll$(this);
    }

    @Override
    public Iterable<A> seq() {
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
    public <B> LazyZip2<A, B, Iterable> lazyZip(Iterable<B> that) {
        return Iterable.lazyZip$(this, that);
    }

    @Override
    public IterableOps fromSpecific(IterableOnce coll) {
        return IterableFactoryDefaults.fromSpecific$(this, coll);
    }

    @Override
    public Builder<A, scala.collection.mutable.Seq<A>> newSpecificBuilder() {
        return IterableFactoryDefaults.newSpecificBuilder$(this);
    }

    @Override
    public IterableOps empty() {
        return IterableFactoryDefaults.empty$(this);
    }

    @Override
    public final Iterable<A> toTraversable() {
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
    public A head() {
        return (A)IterableOps.head$(this);
    }

    @Override
    public Option<A> headOption() {
        return IterableOps.headOption$(this);
    }

    @Override
    public A last() {
        return (A)IterableOps.last$(this);
    }

    @Override
    public Option<A> lastOption() {
        return IterableOps.lastOption$(this);
    }

    @Override
    public final IterableOps sizeIs() {
        return IterableOps.sizeIs$(this);
    }

    @Override
    public View<A> view(int from, int until) {
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
    public WithFilter<A, ?> withFilter(Function1<A, Object> p) {
        return IterableOps.withFilter$(this, p);
    }

    @Override
    public Tuple2<scala.collection.mutable.Seq<A>, scala.collection.mutable.Seq<A>> partition(Function1<A, Object> p) {
        return IterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<scala.collection.mutable.Seq<A>, scala.collection.mutable.Seq<A>> splitAt(int n) {
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
    public Tuple2<scala.collection.mutable.Seq<A>, scala.collection.mutable.Seq<A>> span(Function1<A, Object> p) {
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
    public Iterator<scala.collection.mutable.Seq<A>> grouped(int size) {
        return IterableOps.grouped$(this, size);
    }

    @Override
    public Iterator<scala.collection.mutable.Seq<A>> sliding(int size) {
        return IterableOps.sliding$(this, size);
    }

    @Override
    public Iterator<scala.collection.mutable.Seq<A>> sliding(int size, int step) {
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
    public <K$> scala.collection.immutable.Map<K$, scala.collection.mutable.Seq<A>> groupBy(Function1<A, K$> f) {
        return IterableOps.groupBy$(this, f);
    }

    @Override
    public <K$, B> scala.collection.immutable.Map<K$, scala.collection.mutable.Seq<B>> groupMap(Function1<A, K$> key, Function1<A, B> f) {
        return IterableOps.groupMap$(this, key, f);
    }

    @Override
    public <K$, B> scala.collection.immutable.Map<K$, B> groupMapReduce(Function1<A, K$> key, Function1<A, B> f, Function2<B, B, B> reduce) {
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
    public <A1, A2> Tuple2<scala.collection.mutable.Seq<A1>, scala.collection.mutable.Seq<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
        return IterableOps.partitionMap$(this, f);
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
    public <A1, A2> Tuple2<scala.collection.mutable.Seq<A1>, scala.collection.mutable.Seq<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
        return IterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<scala.collection.mutable.Seq<A1>, scala.collection.mutable.Seq<A2>, scala.collection.mutable.Seq<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
        return IterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Iterator<scala.collection.mutable.Seq<A>> tails() {
        return IterableOps.tails$(this);
    }

    @Override
    public Iterator<scala.collection.mutable.Seq<A>> inits() {
        return IterableOps.inits$(this);
    }

    @Override
    public Object tapEach(Function1 f) {
        return IterableOps.tapEach$(this, f);
    }

    @Override
    public boolean hasDefiniteSize() {
        return IterableOnceOps.hasDefiniteSize$(this);
    }

    @Override
    public <U> void foreach(Function1<A, U> f) {
        IterableOnceOps.foreach$(this, f);
    }

    @Override
    public boolean forall(Function1<A, Object> p) {
        return IterableOnceOps.forall$(this, p);
    }

    @Override
    public boolean exists(Function1<A, Object> p) {
        return IterableOnceOps.exists$(this, p);
    }

    @Override
    public int count(Function1<A, Object> p) {
        return IterableOnceOps.count$(this, p);
    }

    @Override
    public Option<A> find(Function1<A, Object> p) {
        return IterableOnceOps.find$(this, p);
    }

    @Override
    public <B> B foldLeft(B z, Function2<B, A, B> op) {
        return (B)IterableOnceOps.foldLeft$(this, z, op);
    }

    @Override
    public <B> B foldRight(B z, Function2<A, B, B> op) {
        return (B)IterableOnceOps.foldRight$(this, z, op);
    }

    @Override
    public final <B> B $div$colon(B z, Function2<B, A, B> op) {
        return (B)IterableOnceOps.$div$colon$(this, z, op);
    }

    @Override
    public final <B> B $colon$bslash(B z, Function2<A, B, B> op) {
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
    public <B> B reduceLeft(Function2<B, A, B> op) {
        return (B)IterableOnceOps.reduceLeft$(this, op);
    }

    @Override
    public <B> B reduceRight(Function2<A, B, B> op) {
        return (B)IterableOnceOps.reduceRight$(this, op);
    }

    @Override
    public <B> Option<B> reduceLeftOption(Function2<B, A, B> op) {
        return IterableOnceOps.reduceLeftOption$(this, op);
    }

    @Override
    public <B> Option<B> reduceRightOption(Function2<A, B, B> op) {
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
    public <B> A min(Ordering<B> ord) {
        return (A)IterableOnceOps.min$(this, ord);
    }

    @Override
    public <B> Option<A> minOption(Ordering<B> ord) {
        return IterableOnceOps.minOption$(this, ord);
    }

    @Override
    public <B> A max(Ordering<B> ord) {
        return (A)IterableOnceOps.max$(this, ord);
    }

    @Override
    public <B> Option<A> maxOption(Ordering<B> ord) {
        return IterableOnceOps.maxOption$(this, ord);
    }

    @Override
    public <B> A maxBy(Function1<A, B> f, Ordering<B> cmp) {
        return (A)IterableOnceOps.maxBy$(this, f, cmp);
    }

    @Override
    public <B> Option<A> maxByOption(Function1<A, B> f, Ordering<B> cmp) {
        return IterableOnceOps.maxByOption$(this, f, cmp);
    }

    @Override
    public <B> A minBy(Function1<A, B> f, Ordering<B> cmp) {
        return (A)IterableOnceOps.minBy$(this, f, cmp);
    }

    @Override
    public <B> Option<A> minByOption(Function1<A, B> f, Ordering<B> cmp) {
        return IterableOnceOps.minByOption$(this, f, cmp);
    }

    @Override
    public <B> Option<B> collectFirst(PartialFunction<A, B> pf) {
        return IterableOnceOps.collectFirst$(this, pf);
    }

    @Override
    public <B> B aggregate(Function0<B> z, Function2<B, A, B> seqop, Function2<B, B, B> combop) {
        return (B)IterableOnceOps.aggregate$(this, z, seqop, combop);
    }

    @Override
    public <B> boolean corresponds(IterableOnce<B> that, Function2<A, B, Object> p) {
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
    public <C1> C1 to(Factory<A, C1> factory2) {
        return (C1)IterableOnceOps.to$(this, factory2);
    }

    @Override
    public final Iterator<A> toIterator() {
        return IterableOnceOps.toIterator$(this);
    }

    @Override
    public List<A> toList() {
        return IterableOnceOps.toList$(this);
    }

    @Override
    public Vector<A> toVector() {
        return IterableOnceOps.toVector$(this);
    }

    @Override
    public <K$, V$> scala.collection.immutable.Map<K$, V$> toMap($less$colon$less<A, Tuple2<K$, V$>> ev) {
        return IterableOnceOps.toMap$(this, ev);
    }

    @Override
    public <B> Set<B> toSet() {
        return IterableOnceOps.toSet$(this);
    }

    @Override
    public scala.collection.immutable.Seq<A> toSeq() {
        return IterableOnceOps.toSeq$(this);
    }

    @Override
    public IndexedSeq<A> toIndexedSeq() {
        return IterableOnceOps.toIndexedSeq$(this);
    }

    @Override
    public final Stream<A> toStream() {
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
    public Iterable<A> reversed() {
        return IterableOnceOps.reversed$(this);
    }

    public int index() {
        return this.index;
    }

    public void index_$eq(int x$1) {
        this.index = x$1;
    }

    public int hIndex() {
        return this.hIndex;
    }

    public void hIndex_$eq(int x$1) {
        this.hIndex = x$1;
    }

    public long totalSize() {
        return this.totalSize;
    }

    public void totalSize_$eq(long x$1) {
        this.totalSize = x$1;
    }

    public abstract long cumulative(int var1);

    public int nextBlockSize() {
        if (this.totalSize() < 32L) {
            return 16;
        }
        if (this.totalSize() <= Integer.MAX_VALUE) {
            int bit = 64 - Long.numberOfLeadingZeros(this.totalSize());
            return 1 << bit - (bit >> 2);
        }
        return 0x1000000;
    }

    public abstract <S extends Stepper<?>> S efficientStepper(StepperShape<A, S> var1);

    @Override
    public final <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
        return this.efficientStepper(shape);
    }

    @Override
    public final int length() {
        if (this.totalSize() < Integer.MAX_VALUE) {
            return (int)this.totalSize();
        }
        throw new IllegalArgumentException(new java.lang.StringBuilder(27).append("Size too large for an Int: ").append(this.totalSize()).toString());
    }

    @Override
    public final int knownSize() {
        if (this.totalSize() < Integer.MAX_VALUE) {
            return this.length();
        }
        return -1;
    }

    public final long sizeLong() {
        return this.totalSize();
    }

    @Override
    public void clear() {
        this.index_$eq(0);
        this.hIndex_$eq(0);
        this.totalSize_$eq(0L);
    }

    public long seekSlot(long ix) {
        int lo = -1;
        int hi = this.hIndex();
        while (lo + 1 < hi) {
            int m4 = lo + hi >>> 1;
            if (this.cumulative(m4) > ix) {
                hi = m4;
                continue;
            }
            lo = m4;
        }
        return (long)hi << 32 | (long)((int)(hi == 0 ? ix : ix - this.cumulative(hi - 1)));
    }

    public static interface AccumulatorFactoryShape<A, C> {
        public Factory<A, C> factory();

        public C empty();
    }

    public static interface LowPriorityAccumulatorFactoryShape {
        public void scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$_setter_$scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype_$eq(AccumulatorFactoryShape<Object, AnyAccumulator<Object>> var1);

        public static /* synthetic */ AccumulatorFactoryShape anyAccumulatorFactoryShape$(LowPriorityAccumulatorFactoryShape $this) {
            return $this.anyAccumulatorFactoryShape();
        }

        default public <A> AccumulatorFactoryShape<A, AnyAccumulator<A>> anyAccumulatorFactoryShape() {
            return this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();
        }

        public AccumulatorFactoryShape<Object, AnyAccumulator<Object>> scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();

        public static void $init$(LowPriorityAccumulatorFactoryShape $this) {
            $this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$_setter_$scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype_$eq(new AccumulatorFactoryShape<Object, AnyAccumulator<Object>>(null){

                public Factory<Object, AnyAccumulator<Object>> factory() {
                    AnyAccumulator$ toFactory_factory = AnyAccumulator$.MODULE$;
                    return new IterableFactory.ToFactory<Object, AnyAccumulator<Object>>(toFactory_factory);
                }

                public AnyAccumulator<Object> empty() {
                    return new AnyAccumulator<Object>();
                }
            });
        }
    }
}

