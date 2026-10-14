/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.PartialFunction;
import scala.Predef$;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterator;
import scala.collection.ArrayOps$;
import scala.collection.ClassTagIterableFactory;
import scala.collection.EvidenceIterableFactoryDefaults;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.Seq;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StrictOptimizedSeqOps;
import scala.collection.StringOps$;
import scala.collection.View;
import scala.collection.generic.DefaultSerializable;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.Nil$;
import scala.collection.mutable.AbstractBuffer;
import scala.collection.mutable.ArraySeq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.UnrolledBuffer$;
import scala.math.Integral;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;
import scala.runtime.Nothing$;
import scala.runtime.ObjectRef;
import scala.runtime.ScalaRunTime$;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005\u0011ec\u0001B2e!-D!\"!\u0010\u0001\u0005\u000b\u0007I1AA \u0011)\t\u0019\u0005\u0001B\u0001B\u0003%\u0011\u0011\t\u0005\b\u0003\u000b\u0002A\u0011AA$\u0011%\ti\u0005\u0001a\u0001\n\u0013\ty\u0005C\u0005\u0004Z\u0001\u0001\r\u0011\"\u0003\u0004\\!A1q\f\u0001!B\u0013\t\t\u0006C\u0005\u0004j\u0001\u0001\r\u0011\"\u0003\u0002P!I11\u000e\u0001A\u0002\u0013%1Q\u000e\u0005\t\u0007c\u0002\u0001\u0015)\u0003\u0002R!I1Q\u000f\u0001A\u0002\u0013%\u00111\u0018\u0005\n\u0007o\u0002\u0001\u0019!C\u0005\u0007sB\u0001b! \u0001A\u0003&\u0011Q\u0018\u0005\t\u0007\u0003\u0003A\u0011\u00014\u0002P!A11\u0011\u0001\u0005\u0002\u0019\u001c)\t\u0003\u0005\u0004\f\u0002!\tAZA(\u0011!\u0019i\t\u0001C\u0001M\u000e=\u0005\u0002CAw\u0001\u0011\u0005am!&\t\u000f\rm\u0005\u0001\"\u0005\u0004\u001e\"91q\u0014\u0001\u0005\u0012\u0005}\u0002bBBQ\u0001\u0011\u0005\u00131\u000e\u0005\b\u0007G\u0003A\u0011CA(\u0011!\u0019)\u000b\u0001C\u0001M\u000e\u001d\u0006bBBV\u0001\u0011\u00051Q\u0016\u0005\b\u0007_\u0003A\u0011ABY\u0011\u001d\u00199\f\u0001C\u0001\u0007sCqaa0\u0001\t\u0003\u0011i\u000bC\u0004\u0004B\u0002!\taa1\t\u000f\t%\u0004\u0001\"\u0011\u0004L\"91q\u001b\u0001\u0005\u0002\re\u0007bBBn\u0001\u0011\u0005\u00111\u0018\u0005\b\u0007;\u0004A\u0011IA^\u0011\u001d\u00119\t\u0001C\u0001\u0007?DqA!%\u0001\t\u0003\u0019\u0019\u000fC\u0004\u0003\u0000\u0001!\ta!;\t\u000f\t=\u0006\u0001\"\u0001\u0004p\"9!q\u0016\u0001\u0005\u0006\rM\bb\u0002BS\u0001\u0011\u00051Q \u0005\b\t\u0003\u0001A\u0011\u0001C\u0002\u0011\u001d\u00119\u000e\u0001C\u0001\t\u0013AqAa/\u0001\t\u0003\"\u0019\u0002C\u0004\u0005\u0018\u0001!\t\u0001\"\u0007\t\u000f\u0011\u0015\u0002\u0001\"\u0003\u0005(!9A\u0011\b\u0001\u0005\n\u0011m\u0002b\u0002C$\u0001\u0011\u00053\u0011\u001c\u0005\t\t\u0013\u0002\u0001\u0015\"\u0015\u0005L\u001d9\u0011Q\u000b3\t\u0002\u0005]cAB2e\u0011\u0003\tI\u0006C\u0004\u0002F=\"\t!a\u001a\t\u0013\u0005%tF1A\u0005\u0002\u0005-\u0004\u0002CA:_\u0001\u0006I!!\u001c\t\u000f\u0005Ut\u0006\"\u0001\u0002x!9\u0011\u0011R\u0018\u0005\u0002\u0005-\u0005bBAT_\u0011\u0005\u0011\u0011\u0016\u0005\n\u0003s{#\u0019!C\u0003\u0003wC\u0001\"a10A\u00035\u0011Q\u0018\u0005\b\u0003\u000b|CQAA^\u0011%\t9m\fb\u0001\n\u000b\tY\f\u0003\u0005\u0002^>\u0002\u000bQBA_\u0011)\t\to\fb\u0001\n\u00031\u00171\u0018\u0005\t\u0003G|\u0003\u0015!\u0003\u0002>\u001a1\u0011Q]\u0018\u0001\u0003OD!\"a;>\u0005\u0003\u0007I\u0011AA^\u0011)\ti/\u0010BA\u0002\u0013\u0005\u0011q\u001e\u0005\u000b\u0003wl$\u0011!Q!\n\u0005u\u0006BCA\u007f{\t\u0005\r\u0011\"\u0001\u0002\u0000\"Q!1B\u001f\u0003\u0002\u0004%\tA!\u0004\t\u0015\tEQH!A!B\u0013\u0011\t\u0001\u0003\u0006\u0003\u0014u\u0012\t\u0019!C\u0001\u0005+A!Ba\u0007>\u0005\u0003\u0007I\u0011\u0001B\u000f\u0011)\u0011\t#\u0010B\u0001B\u0003&!q\u0003\u0005\u000b\u0005Gi$Q1A\u0005\u0002\t\u0015\u0002B\u0003B\u0015{\t\u0005\t\u0015!\u0003\u0003(!Q!1F\u001f\u0003\u0004\u0003\u0006YA!\f\t\u0011\u0005\u0015S\b\"\u0001g\u0005_A\u0001\"!\u0012>\t\u00031'Q\b\u0005\t\u0003\u000bjD\u0011\u00014\u0003F!9!\u0011K\u001f\u0005\n\u0005m\u0006b\u0002B*{\u0011\u0015!Q\u000b\u0005\b\u0005SjD\u0011\u0001B6\u0011\u001d\u0011y(\u0010C\u0001\u0005\u0003CqAa\">\t\u000b\u0011I\tC\u0004\u0003\u0012v\")Aa%\t\u000f\tuU\b\"\u0002\u0003 \"9!QU\u001f\u0005\u0002\t\u001d\u0006b\u0002BV{\u0011%!Q\u0016\u0005\b\u0005_kDQ\u0001BY\u0011\u001d\u0011Y,\u0010C\u0003\u0005{CqAa3>\t\u0013\u0011i\rC\u0004\u0003Tv\"\tB!6\t\u000f\t]W\b\"\u0002\u0003Z\"9!q]\u001f\u0005\n\t%\bb\u0002By{\u0011\u0005!1\u001f\u0005\b\u0005slD\u0011\tB~\u000f%\u0019\u0019bLA\u0001\u0012\u0003\u0019)BB\u0005\u0002f>\n\t\u0011#\u0001\u0004\u0018!9\u0011QI0\u0005\u0002\re\u0001BCB\u000e?F\u0005I\u0011\u00014\u0004\u001e!I1qG\u0018\u0002\u0002\u0013%1\u0011\b\u0002\u000f+:\u0014x\u000e\u001c7fI\n+hMZ3s\u0015\t)g-A\u0004nkR\f'\r\\3\u000b\u0005\u001dD\u0017AC2pY2,7\r^5p]*\t\u0011.A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u00051\u001c8c\u0004\u0001n{\u0006\u0005\u0011qAA\t\u00033\tY#!\r\u0011\u00079|\u0017/D\u0001e\u0013\t\u0001HM\u0001\bBEN$(/Y2u\u0005V4g-\u001a:\u0011\u0005I\u001cH\u0002\u0001\u0003\u0006i\u0002\u0011\r!\u001e\u0002\u0002)F\u0011aO\u001f\t\u0003obl\u0011\u0001[\u0005\u0003s\"\u0014qAT8uQ&tw\r\u0005\u0002xw&\u0011A\u0010\u001b\u0002\u0004\u0003:L\bc\u00018\u007fc&\u0011q\u0010\u001a\u0002\u0007\u0005V4g-\u001a:\u0011\t9\f\u0019!]\u0005\u0004\u0003\u000b!'aA*fcBAa.!\u0003r\u0003\u001b\ty!C\u0002\u0002\f\u0011\u0014aaU3r\u001fB\u001c\bC\u00018\u0001!\rq\u0007!\u001d\t\n\u0003'\t)\"]A\u0007\u0003\u001fi\u0011AZ\u0005\u0004\u0003/1'!F*ue&\u001cGo\u00149uS6L'0\u001a3TKF|\u0005o\u001d\t\n\u0003'\tY\"]A\u0007\u0003?I1!!\bg\u0005})e/\u001b3f]\u000e,\u0017\n^3sC\ndWMR1di>\u0014\u0018\u0010R3gCVdGo\u001d\t\u0005\u0003C\t9#\u0004\u0002\u0002$)\u0019\u0011Q\u00055\u0002\u000fI,g\r\\3di&!\u0011\u0011FA\u0012\u0005!\u0019E.Y:t)\u0006<\u0007C\u00028\u0002.E\fy!C\u0002\u00020\u0011\u0014qAQ;jY\u0012,'\u000f\u0005\u0003\u00024\u0005eRBAA\u001b\u0015\r\t9DZ\u0001\bO\u0016tWM]5d\u0013\u0011\tY$!\u000e\u0003'\u0011+g-Y;miN+'/[1mSj\f'\r\\3\u0002\u0007Q\fw-\u0006\u0002\u0002BA)\u0011\u0011EA\u0014c\u0006!A/Y4!\u0003\u0019a\u0014N\\5u}Q\u0011\u0011\u0011\n\u000b\u0005\u0003\u001f\tY\u0005C\u0004\u0002>\r\u0001\u001d!!\u0011\u0002\u000f!,\u0017\r\u001a9ueV\u0011\u0011\u0011\u000b\t\u0005\u0003'j\u0014O\u0004\u0002o]\u0005qQK\u001c:pY2,GMQ;gM\u0016\u0014\bC\u000180'\u0015y\u00131LA1!\r9\u0018QL\u0005\u0004\u0003?B'AB!osJ+g\r\u0005\u0004\u0002\u0014\u0005\r\u0014QB\u0005\u0004\u0003K2'!I*ue&\u001cGo\u00149uS6L'0\u001a3DY\u0006\u001c8\u000fV1h'\u0016\fh)Y2u_JLHCAA,\u0003!)h\u000e^1hO\u0016$WCAA7!\u0019\t\u0019\"a\u001c\u0002\u000e%\u0019\u0011\u0011\u000f4\u0003\u0015M+\u0017OR1di>\u0014\u00180A\u0005v]R\fwmZ3eA\u0005)Q-\u001c9usV!\u0011\u0011PA@)\u0011\tY(a!\u0011\t9\u0004\u0011Q\u0010\t\u0004e\u0006}DABAAg\t\u0007QOA\u0001B\u0011%\t)iMA\u0001\u0002\b\t9)\u0001\u0006fm&$WM\\2fIE\u0002b!!\t\u0002(\u0005u\u0014\u0001\u00024s_6,B!!$\u0002\u0016R!\u0011qRAO)\u0011\t\t*a&\u0011\t9\u0004\u00111\u0013\t\u0004e\u0006UEABAAi\t\u0007Q\u000fC\u0005\u0002\u001aR\n\t\u0011q\u0001\u0002\u001c\u0006QQM^5eK:\u001cW\r\n\u001a\u0011\r\u0005\u0005\u0012qEAJ\u0011\u001d\ty\n\u000ea\u0001\u0003C\u000baa]8ve\u000e,\u0007CBA\n\u0003G\u000b\u0019*C\u0002\u0002&\u001a\u0014A\"\u0013;fe\u0006\u0014G.Z(oG\u0016\f!B\\3x\u0005VLG\u000eZ3s+\u0011\tY+!-\u0015\t\u00055\u00161\u0017\t\u0005]\u0002\ty\u000bE\u0002s\u0003c#a!!!6\u0005\u0004)\b\"CA[k\u0005\u0005\t9AA\\\u0003))g/\u001b3f]\u000e,Ge\r\t\u0007\u0003C\t9#a,\u0002\u0013]\fG/\u001a:mS:,WCAA_!\r9\u0018qX\u0005\u0004\u0003\u0003D'aA%oi\u0006Qq/\u0019;fe2Lg.\u001a\u0011\u0002\u001d]\fG/\u001a:mS:,G)\u001a8p[\u0006qq/\u0019;fe2Lg.\u001a#fY&l\u0007fC\u001d\u0002L\u0006E\u00171[Al\u00033\u00042a^Ag\u0013\r\ty\r\u001b\u0002\u000bI\u0016\u0004(/Z2bi\u0016$\u0017aB7fgN\fw-Z\u0011\u0003\u0003+\f1$V:fA]\fG/\u001a:mS:,G)\u001a8p[\u0002Jgn\u001d;fC\u0012t\u0013!B:j]\u000e,\u0017EAAn\u0003\u0019\u0011d&M\u001a/a\u0005yq/\u0019;fe2Lg.\u001a#fY&l\u0007\u0005K\u0006;\u0003\u0017\f\t.a5\u0002X\u0006e\u0017AD;oe>dG.\u001a3mK:<G\u000f[\u0001\u0010k:\u0014x\u000e\u001c7fI2,gn\u001a;iA\tAQK\u001c:pY2,G-\u0006\u0003\u0002j\n%1cA\u001f\u0002\\\u0005!1/\u001b>f\u0003!\u0019\u0018N_3`I\u0015\fH\u0003BAy\u0003o\u00042a^Az\u0013\r\t)\u0010\u001b\u0002\u0005+:LG\u000fC\u0005\u0002z~\n\t\u00111\u0001\u0002>\u0006\u0019\u0001\u0010J\u0019\u0002\u000bML'0\u001a\u0011\u0002\u000b\u0005\u0014(/Y=\u0016\u0005\t\u0005\u0001#B<\u0003\u0004\t\u001d\u0011b\u0001B\u0003Q\n)\u0011I\u001d:bsB\u0019!O!\u0003\u0005\u000bQl$\u0019A;\u0002\u0013\u0005\u0014(/Y=`I\u0015\fH\u0003BAy\u0005\u001fA\u0011\"!?C\u0003\u0003\u0005\rA!\u0001\u0002\r\u0005\u0014(/Y=!\u0003\u0011qW\r\u001f;\u0016\u0005\t]\u0001#\u0002B\r{\t\u001dQ\"A\u0018\u0002\u00119,\u0007\u0010^0%KF$B!!=\u0003 !I\u0011\u0011`#\u0002\u0002\u0003\u0007!qC\u0001\u0006]\u0016DH\u000fI\u0001\u0005EV4g-\u0006\u0002\u0003(A!a\u000e\u0001B\u0004\u0003\u0015\u0011WO\u001a4!\u0003))g/\u001b3f]\u000e,G\u0005\u000e\t\u0007\u0003C\t9Ca\u0002\u0015\u0015\tE\"Q\u0007B\u001c\u0005s\u0011Y\u0004\u0006\u0003\u0003\u0018\tM\u0002b\u0002B\u0016\u0015\u0002\u000f!Q\u0006\u0005\b\u0003WT\u0005\u0019AA_\u0011\u001d\tiP\u0013a\u0001\u0005\u0003AqAa\u0005K\u0001\u0004\u00119\u0002C\u0005\u0003$)\u0003\n\u00111\u0001\u0003(Q\u0011!q\b\u000b\u0005\u0005/\u0011\t\u0005C\u0005\u0003D-\u000b\t\u0011q\u0001\u0003.\u0005QQM^5eK:\u001cW\rJ\u001b\u0015\t\t\u001d#Q\n\u000b\u0005\u0005/\u0011I\u0005C\u0005\u0003L1\u000b\t\u0011q\u0001\u0003.\u0005QQM^5eK:\u001cW\r\n\u001c\t\u000f\t=C\n1\u0001\u0003(\u0005\t!-\u0001\u0006oKb$H.\u001a8hi\"\fa!\u00199qK:$G\u0003\u0002B\f\u0005/BqA!\u0017O\u0001\u0004\u00119!\u0001\u0003fY\u0016l\u0007f\u0001(\u0003^A!!q\fB3\u001b\t\u0011\tGC\u0002\u0003d!\f!\"\u00198o_R\fG/[8o\u0013\u0011\u00119G!\u0019\u0003\u000fQ\f\u0017\u000e\u001c:fG\u00069am\u001c:fC\u000eDW\u0003\u0002B7\u0005w\"B!!=\u0003p!9!\u0011O(A\u0002\tM\u0014!\u00014\u0011\u000f]\u0014)Ha\u0002\u0003z%\u0019!q\u000f5\u0003\u0013\u0019+hn\u0019;j_:\f\u0004c\u0001:\u0003|\u00111!QP(C\u0002U\u0014\u0011!V\u0001\u000b[\u0006\u0004\u0018J\u001c)mC\u000e,G\u0003BAy\u0005\u0007CqA!\u001dQ\u0001\u0004\u0011)\tE\u0004x\u0005k\u00129Aa\u0002\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\t\t\u001d!1\u0012\u0005\b\u0005\u001b\u000b\u0006\u0019AA_\u0003\rIG\r\u001f\u0015\u0004#\nu\u0013AB;qI\u0006$X\r\u0006\u0004\u0002r\nU%q\u0013\u0005\b\u0005\u001b\u0013\u0006\u0019AA_\u0011\u001d\u0011IJ\u0015a\u0001\u0005\u000f\tqA\\3xK2,W\u000eK\u0002S\u0005;\na\u0001\\8dCR,G\u0003\u0002B\f\u0005CCqA!$T\u0001\u0004\ti\fK\u0002T\u0005;\nq\u0001\u001d:fa\u0016tG\r\u0006\u0003\u0003\u0018\t%\u0006b\u0002B-)\u0002\u0007!qA\u0001\u000bg\"Lg\r\u001e:jO\"$HCAAy\u0003\u0019\u0011X-\\8wKR1!q\u0001BZ\u0005kCqA!$W\u0001\u0004\ti\fC\u0004\u00038Z\u0003\rAa\n\u0002\r\t,hMZ3sQ\r1&QL\u0001\fgV\u0014GO]1di>sW\r\u0006\u0004\u0003@\n\u0015'q\u0019\t\u0004o\n\u0005\u0017b\u0001BbQ\n9!i\\8mK\u0006t\u0007b\u0002B-/\u0002\u0007!q\u0001\u0005\b\u0005o;\u0006\u0019\u0001B\u0014Q\r9&QL\u0001\ng\"Lg\r\u001e7fMR$B!!=\u0003P\"9!\u0011\u001b-A\u0002\u0005u\u0016!\u00027fMR\u0014\u0017\u0001\u0005;ss6+'oZ3XSRDg*\u001a=u)\t\u0011y,A\u0005j]N,'\u000f^!mYRA\u0011Q\u0018Bn\u0005;\u0014\u0019\u000fC\u0004\u0003\u000ej\u0003\r!!0\t\u000f\t}'\f1\u0001\u0003b\u0006\tA\u000f\u0005\u0004\u0002\u0014\u0005\r&q\u0001\u0005\b\u0005oS\u0006\u0019\u0001B\u0014Q\rQ&QL\u0001\b]VdGn\\;u)\u0019\t\tPa;\u0003n\"9\u0011\u0011R.A\u0002\u0005u\u0006b\u0002Bx7\u0002\u0007\u0011QX\u0001\u0006k:$\u0018\u000e\\\u0001\u0005E&tG\r\u0006\u0003\u0003@\nU\bb\u0002B|9\u0002\u0007!qC\u0001\ti\"\fG\u000f[3bI\u0006AAo\\*ue&tw\r\u0006\u0002\u0003~B!!q`B\u0007\u001d\u0011\u0019\ta!\u0003\u0011\u0007\r\r\u0001.\u0004\u0002\u0004\u0006)\u00191q\u00016\u0002\rq\u0012xn\u001c;?\u0013\r\u0019Y\u0001[\u0001\u0007!J,G-\u001a4\n\t\r=1\u0011\u0003\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\r-\u0001.\u0001\u0005V]J|G\u000e\\3e!\r\u0011IbX\n\u0004?\u0006mCCAB\u000b\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%iU!1qDB\u001b+\t\u0019\tC\u000b\u0003\u0004$\r\u001drBAB\u0013E\u0001Y#a!\u000b\u0011\t\r-2\u0011G\u0007\u0003\u0007[QAaa\f\u0003b\u0005IQO\\2iK\u000e\\W\rZ\u0005\u0005\u0007g\u0019iCA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016$Q\u0001^1C\u0002U\fAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"aa\u000f\u0011\t\ru2qI\u0007\u0003\u0007\u007fQAa!\u0011\u0004D\u0005!A.\u00198h\u0015\t\u0019)%\u0001\u0003kCZ\f\u0017\u0002BB%\u0007\u007f\u0011aa\u00142kK\u000e$\bfB\u0018\u0004N\rM3Q\u000b\t\u0004o\u000e=\u0013bAB)Q\n\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0007!:af!\u0014\u0004T\rU\u0013a\u00035fC\u0012\u0004HO]0%KF$B!!=\u0004^!I\u0011\u0011`\u0003\u0002\u0002\u0003\u0007\u0011\u0011K\u0001\tQ\u0016\fG\r\u001d;sA!\u001aaaa\u0019\u0011\u0007]\u001c)'C\u0002\u0004h!\u0014\u0011\u0002\u001e:b]NLWM\u001c;\u0002\u000f1\f7\u000f\u001e9ue\u0006YA.Y:uaR\u0014x\fJ3r)\u0011\t\tpa\u001c\t\u0013\u0005e\b\"!AA\u0002\u0005E\u0013\u0001\u00037bgR\u0004HO\u001d\u0011)\u0007%\u0019\u0019'\u0001\u0002tu\u000611O_0%KF$B!!=\u0004|!I\u0011\u0011`\u0006\u0002\u0002\u0003\u0007\u0011QX\u0001\u0004gj\u0004\u0003f\u0001\u0007\u0004d\u00059\u0001.Z1e!R\u0014\u0018a\u00035fC\u0012\u0004FO]0%KF$B!!=\u0004\b\"91\u0011\u0012\bA\u0002\u0005E\u0013\u0001\u00025fC\u0012\fq\u0001\\1tiB#(/A\u0006mCN$\b\u000b\u001e:`I\u0015\fH\u0003BAy\u0007#Cqaa%\u0011\u0001\u0004\t\t&\u0001\u0003mCN$H\u0003BAy\u0007/Cqa!'\u0012\u0001\u0004\ti,A\u0001t\u0003])g/\u001b3f]\u000e,\u0017\n^3sC\ndWMR1di>\u0014\u00180\u0006\u0002\u0002T\u0005\u0001\u0012\u000e^3sC\ndW-\u0012<jI\u0016t7-Z\u0001\u0010SR,'/\u00192mK\u001a\u000b7\r^8ss\u0006Ya.Z<V]J|G\u000e\\3e\u00039\u0019\u0017\r\\2OKb$H*\u001a8hi\"$B!!0\u0004*\"91Q\u000f\fA\u0002\u0005u\u0016!E2mCN\u001cH+Y4D_6\u0004\u0018M\\5p]V\u0011\u0011qK\u0001\u0007G>t7-\u0019;\u0015\t\u0005=11\u0017\u0005\b\u0007kC\u0002\u0019AA\b\u0003\u0011!\b.\u0019;\u0002\r\u0005$Gm\u00148f)\u0011\u0019Yl!0\u000e\u0003\u0001AaA!\u0017\u001a\u0001\u0004\t\u0018!B2mK\u0006\u0014\u0018\u0001C5uKJ\fGo\u001c:\u0016\u0005\r\u0015\u0007#BA\n\u0007\u000f\f\u0018bABeM\nA\u0011\n^3sCR|'/\u0006\u0003\u0004N\u000eUG\u0003BAy\u0007\u001fDqA!\u001d\u001d\u0001\u0004\u0019\t\u000e\u0005\u0004x\u0005k\n81\u001b\t\u0004e\u000eUGA\u0002B?9\t\u0007Q/\u0001\u0004sKN,H\u000e\u001e\u000b\u0003\u0003\u001f\ta\u0001\\3oORD\u0017!C6o_^t7+\u001b>f)\r\t8\u0011\u001d\u0005\b\u0005\u001b\u0003\u0003\u0019AA_)\u0019\t\tp!:\u0004h\"9!QR\u0011A\u0002\u0005u\u0006B\u0002BMC\u0001\u0007\u0011\u000f\u0006\u0003\u0004<\u000e-\bb\u0002B9E\u0001\u00071Q\u001e\t\u0006o\nU\u0014/\u001d\u000b\u0004c\u000eE\bb\u0002BGG\u0001\u0007\u0011Q\u0018\u000b\u0007\u0003c\u001c)pa>\t\u000f\t5E\u00051\u0001\u0002>\"91\u0011 \u0013A\u0002\u0005u\u0016!B2pk:$\bf\u0001\u0013\u0003^Q!11XB\u0000\u0011\u0019\u0011I&\na\u0001c\u00061\u0011N\\:feR$b!!=\u0005\u0006\u0011\u001d\u0001b\u0002BGM\u0001\u0007\u0011Q\u0018\u0005\u0007\u000532\u0003\u0019A9\u0015\r\u0005EH1\u0002C\u0007\u0011\u001d\u0011ii\na\u0001\u0003{Cq\u0001b\u0004(\u0001\u0004!\t\"A\u0003fY\u0016l7\u000fE\u0003\u0002\u0014\u0005\r\u0016\u000f\u0006\u0003\u0004<\u0012U\u0001B\u0002B-Q\u0001\u0007\u0011/\u0001\u0007qCR\u001c\u0007.\u00138QY\u0006\u001cW\r\u0006\u0005\u0004<\u0012mAQ\u0004C\u0011\u0011\u001d\tI)\u000ba\u0001\u0003{Cq\u0001b\b*\u0001\u0004!\t\"A\u0003qCR\u001c\u0007\u000eC\u0004\u0005$%\u0002\r!!0\u0002\u0011I,\u0007\u000f\\1dK\u0012\f1b\u001e:ji\u0016|%M[3diR!\u0011\u0011\u001fC\u0015\u0011\u001d!YC\u000ba\u0001\t[\t1a\\;u!\u0011!y\u0003\"\u000e\u000e\u0005\u0011E\"\u0002\u0002C\u001a\u0007\u0007\n!![8\n\t\u0011]B\u0011\u0007\u0002\u0013\u001f\nTWm\u0019;PkR\u0004X\u000f^*ue\u0016\fW.\u0001\u0006sK\u0006$wJ\u00196fGR$B!!=\u0005>!9AqH\u0016A\u0002\u0011\u0005\u0013AA5o!\u0011!y\u0003b\u0011\n\t\u0011\u0015C\u0011\u0007\u0002\u0012\u001f\nTWm\u0019;J]B,Ho\u0015;sK\u0006l\u0017!B2m_:,\u0017!C2mCN\u001ch*Y7f+\t!i\u0005\u0005\u0003\u0004>\u0011=\u0013\u0002BB\b\u0007\u007fI3\u0001\u0001C*\u0013\r!)\u0006\u001a\u0002\u0017\t>,(\r\\5oOVs'o\u001c7mK\u0012\u0014UO\u001a4fe\":\u0001a!\u0014\u0004T\rU\u0003")
public class UnrolledBuffer<T>
extends AbstractBuffer<T>
implements StrictOptimizedSeqOps<T, UnrolledBuffer, UnrolledBuffer<T>>,
EvidenceIterableFactoryDefaults<T, UnrolledBuffer, ClassTag>,
Builder<T, UnrolledBuffer<T>>,
DefaultSerializable {
    private static final long serialVersionUID = 3L;
    private final ClassTag<T> tag;
    private transient Unrolled<T> scala$collection$mutable$UnrolledBuffer$$headptr;
    private transient Unrolled<T> lastptr;
    private transient int sz;

    public static int waterlineDelim() {
        return UnrolledBuffer$.MODULE$.waterlineDelim();
    }

    public static int waterlineDenom() {
        return 100;
    }

    public static int waterline() {
        return UnrolledBuffer$.MODULE$.waterline();
    }

    public static <A> UnrolledBuffer<A> newBuilder(ClassTag<A> evidence$3) {
        return new UnrolledBuffer<A>(evidence$3);
    }

    public static <A> UnrolledBuffer<A> from(IterableOnce<A> source2, ClassTag<A> evidence$2) {
        return UnrolledBuffer$.MODULE$.from(source2, evidence$2);
    }

    public static SeqFactory<UnrolledBuffer> untagged() {
        return UnrolledBuffer$.MODULE$.untagged();
    }

    public static SeqOps tabulate(int n, Function1 f, ClassTag evidence$35) {
        UnrolledBuffer tabulate_b = new UnrolledBuffer(evidence$35);
        tabulate_b.sizeHint(n);
        for (int tabulate_i = 0; tabulate_i < n; ++tabulate_i) {
            Object tabulate_$plus$eq_elem = f.apply(tabulate_i);
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var5_5 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static SeqOps fill(int n, Function0 elem, ClassTag evidence$34) {
        UnrolledBuffer fill_b = new UnrolledBuffer(evidence$34);
        fill_b.sizeHint(n);
        for (int fill_i = 0; fill_i < n; ++fill_i) {
            Object fill_$plus$eq_elem = elem.apply();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var5_5 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static SeqOps unapplySeq(SeqOps x) {
        return x;
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f, ClassTag evidence$33) {
        ClassTag<Object> tabulate_evidence$9 = ClassTag$.MODULE$.AnyRef();
        UnrolledBuffer<Object> tabulate_b = new UnrolledBuffer<Object>(tabulate_evidence$9);
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var14_14;
            ClassTag<Object> tabulate_evidence$92 = ClassTag$.MODULE$.AnyRef();
            UnrolledBuffer<Object> tabulate_b2 = new UnrolledBuffer<Object>(tabulate_evidence$92);
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var18_18;
                ClassTag<Object> tabulate_evidence$93 = ClassTag$.MODULE$.AnyRef();
                UnrolledBuffer<Object> tabulate_b3 = new UnrolledBuffer<Object>(tabulate_evidence$93);
                tabulate_b3.sizeHint(n3);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object var22_22;
                    ClassTag<Object> tabulate_evidence$94 = ClassTag$.MODULE$.AnyRef();
                    UnrolledBuffer<Object> tabulate_b4 = new UnrolledBuffer<Object>(tabulate_evidence$94);
                    tabulate_b4.sizeHint(n4);
                    for (int tabulate_i4 = 0; tabulate_i4 < n4; ++tabulate_i4) {
                        Object var25_25;
                        UnrolledBuffer tabulate_b5 = new UnrolledBuffer(evidence$33);
                        tabulate_b5.sizeHint(n5);
                        for (int tabulate_i5 = 0; tabulate_i5 < n5; ++tabulate_i5) {
                            Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3, tabulate_i4, tabulate_i5);
                            tabulate_b5.addOne(tabulate_$plus$eq_elem);
                            var25_25 = null;
                        }
                        Object var23_23 = null;
                        var25_25 = null;
                        SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b5.result();
                        tabulate_b4.addOne(tabulate_$plus$eq_elem);
                        var22_22 = null;
                    }
                    Object var20_20 = null;
                    var22_22 = null;
                    Object var19_19 = null;
                    SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b4.result();
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var18_18 = null;
                }
                Object var16_16 = null;
                var18_18 = null;
                Object var15_15 = null;
                SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var14_14 = null;
            }
            Object var12_12 = null;
            var14_14 = null;
            Object var11_11 = null;
            SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var10_10 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, Function4 f, ClassTag evidence$32) {
        ClassTag<Object> tabulate_evidence$9 = ClassTag$.MODULE$.AnyRef();
        UnrolledBuffer<Object> tabulate_b = new UnrolledBuffer<Object>(tabulate_evidence$9);
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var13_13;
            ClassTag<Object> tabulate_evidence$92 = ClassTag$.MODULE$.AnyRef();
            UnrolledBuffer<Object> tabulate_b2 = new UnrolledBuffer<Object>(tabulate_evidence$92);
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var17_17;
                ClassTag<Object> tabulate_evidence$93 = ClassTag$.MODULE$.AnyRef();
                UnrolledBuffer<Object> tabulate_b3 = new UnrolledBuffer<Object>(tabulate_evidence$93);
                tabulate_b3.sizeHint(n3);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object var20_20;
                    UnrolledBuffer tabulate_b4 = new UnrolledBuffer(evidence$32);
                    tabulate_b4.sizeHint(n4);
                    for (int tabulate_i4 = 0; tabulate_i4 < n4; ++tabulate_i4) {
                        Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3, tabulate_i4);
                        tabulate_b4.addOne(tabulate_$plus$eq_elem);
                        var20_20 = null;
                    }
                    Object var18_18 = null;
                    var20_20 = null;
                    SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b4.result();
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var17_17 = null;
                }
                Object var15_15 = null;
                var17_17 = null;
                Object var14_14 = null;
                SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var13_13 = null;
            }
            Object var11_11 = null;
            var13_13 = null;
            Object var10_10 = null;
            SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var9_9 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, int n3, Function3 f, ClassTag evidence$31) {
        ClassTag<Object> tabulate_evidence$9 = ClassTag$.MODULE$.AnyRef();
        UnrolledBuffer<Object> tabulate_b = new UnrolledBuffer<Object>(tabulate_evidence$9);
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var12_12;
            ClassTag<Object> tabulate_evidence$92 = ClassTag$.MODULE$.AnyRef();
            UnrolledBuffer<Object> tabulate_b2 = new UnrolledBuffer<Object>(tabulate_evidence$92);
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var15_15;
                UnrolledBuffer tabulate_b3 = new UnrolledBuffer(evidence$31);
                tabulate_b3.sizeHint(n3);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3);
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var15_15 = null;
                }
                Object var13_13 = null;
                var15_15 = null;
                SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var12_12 = null;
            }
            Object var10_10 = null;
            var12_12 = null;
            Object var9_9 = null;
            SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var8_8 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, Function2 f, ClassTag evidence$30) {
        ClassTag<Object> tabulate_evidence$9 = ClassTag$.MODULE$.AnyRef();
        UnrolledBuffer<Object> tabulate_b = new UnrolledBuffer<Object>(tabulate_evidence$9);
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var10_10;
            UnrolledBuffer tabulate_b2 = new UnrolledBuffer(evidence$30);
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2);
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var10_10 = null;
            }
            Object var8_8 = null;
            var10_10 = null;
            SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var7_7 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem, ClassTag evidence$29) {
        ClassTag<Object> fill_evidence$8 = ClassTag$.MODULE$.AnyRef();
        UnrolledBuffer<Object> fill_b = new UnrolledBuffer<Object>(fill_evidence$8);
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var14_14;
            ClassTag<Object> fill_evidence$82 = ClassTag$.MODULE$.AnyRef();
            UnrolledBuffer<Object> fill_b2 = new UnrolledBuffer<Object>(fill_evidence$82);
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var18_18;
                ClassTag<Object> fill_evidence$83 = ClassTag$.MODULE$.AnyRef();
                UnrolledBuffer<Object> fill_b3 = new UnrolledBuffer<Object>(fill_evidence$83);
                fill_b3.sizeHint(n3);
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object var22_22;
                    ClassTag<Object> fill_evidence$84 = ClassTag$.MODULE$.AnyRef();
                    UnrolledBuffer<Object> fill_b4 = new UnrolledBuffer<Object>(fill_evidence$84);
                    fill_b4.sizeHint(n4);
                    for (int fill_i4 = 0; fill_i4 < n4; ++fill_i4) {
                        Object var25_25;
                        UnrolledBuffer fill_b5 = new UnrolledBuffer(evidence$29);
                        fill_b5.sizeHint(n5);
                        for (int fill_i5 = 0; fill_i5 < n5; ++fill_i5) {
                            Object fill_$plus$eq_elem = elem.apply();
                            fill_b5.addOne(fill_$plus$eq_elem);
                            var25_25 = null;
                        }
                        Object var23_23 = null;
                        var25_25 = null;
                        SeqOps fill_$plus$eq_elem = (SeqOps)fill_b5.result();
                        fill_b4.addOne(fill_$plus$eq_elem);
                        var22_22 = null;
                    }
                    Object var20_20 = null;
                    var22_22 = null;
                    Object var19_19 = null;
                    SeqOps fill_$plus$eq_elem = (SeqOps)fill_b4.result();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var18_18 = null;
                }
                Object var16_16 = null;
                var18_18 = null;
                Object var15_15 = null;
                SeqOps fill_$plus$eq_elem = (SeqOps)fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var14_14 = null;
            }
            Object var12_12 = null;
            var14_14 = null;
            Object var11_11 = null;
            SeqOps fill_$plus$eq_elem = (SeqOps)fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var10_10 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static Object fill(int n1, int n2, int n3, int n4, Function0 elem, ClassTag evidence$28) {
        ClassTag<Object> fill_evidence$8 = ClassTag$.MODULE$.AnyRef();
        UnrolledBuffer<Object> fill_b = new UnrolledBuffer<Object>(fill_evidence$8);
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var13_13;
            ClassTag<Object> fill_evidence$82 = ClassTag$.MODULE$.AnyRef();
            UnrolledBuffer<Object> fill_b2 = new UnrolledBuffer<Object>(fill_evidence$82);
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var17_17;
                ClassTag<Object> fill_evidence$83 = ClassTag$.MODULE$.AnyRef();
                UnrolledBuffer<Object> fill_b3 = new UnrolledBuffer<Object>(fill_evidence$83);
                fill_b3.sizeHint(n3);
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object var20_20;
                    UnrolledBuffer fill_b4 = new UnrolledBuffer(evidence$28);
                    fill_b4.sizeHint(n4);
                    for (int fill_i4 = 0; fill_i4 < n4; ++fill_i4) {
                        Object fill_$plus$eq_elem = elem.apply();
                        fill_b4.addOne(fill_$plus$eq_elem);
                        var20_20 = null;
                    }
                    Object var18_18 = null;
                    var20_20 = null;
                    SeqOps fill_$plus$eq_elem = (SeqOps)fill_b4.result();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var17_17 = null;
                }
                Object var15_15 = null;
                var17_17 = null;
                Object var14_14 = null;
                SeqOps fill_$plus$eq_elem = (SeqOps)fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var13_13 = null;
            }
            Object var11_11 = null;
            var13_13 = null;
            Object var10_10 = null;
            SeqOps fill_$plus$eq_elem = (SeqOps)fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var9_9 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static Object fill(int n1, int n2, int n3, Function0 elem, ClassTag evidence$27) {
        ClassTag<Object> fill_evidence$8 = ClassTag$.MODULE$.AnyRef();
        UnrolledBuffer<Object> fill_b = new UnrolledBuffer<Object>(fill_evidence$8);
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var12_12;
            ClassTag<Object> fill_evidence$82 = ClassTag$.MODULE$.AnyRef();
            UnrolledBuffer<Object> fill_b2 = new UnrolledBuffer<Object>(fill_evidence$82);
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var15_15;
                UnrolledBuffer fill_b3 = new UnrolledBuffer(evidence$27);
                fill_b3.sizeHint(n3);
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object fill_$plus$eq_elem = elem.apply();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var15_15 = null;
                }
                Object var13_13 = null;
                var15_15 = null;
                SeqOps fill_$plus$eq_elem = (SeqOps)fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var12_12 = null;
            }
            Object var10_10 = null;
            var12_12 = null;
            Object var9_9 = null;
            SeqOps fill_$plus$eq_elem = (SeqOps)fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var8_8 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static Object fill(int n1, int n2, Function0 elem, ClassTag evidence$26) {
        ClassTag<Object> fill_evidence$8 = ClassTag$.MODULE$.AnyRef();
        UnrolledBuffer<Object> fill_b = new UnrolledBuffer<Object>(fill_evidence$8);
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var10_10;
            UnrolledBuffer fill_b2 = new UnrolledBuffer(evidence$26);
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object fill_$plus$eq_elem = elem.apply();
                fill_b2.addOne(fill_$plus$eq_elem);
                var10_10 = null;
            }
            Object var8_8 = null;
            var10_10 = null;
            SeqOps fill_$plus$eq_elem = (SeqOps)fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var7_7 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$24, ClassTag evidence$25) {
        return ClassTagIterableFactory.range$(UnrolledBuffer$.MODULE$, start2, end, step, evidence$24, evidence$25);
    }

    public static Object range(Object start2, Object end, Integral evidence$22, ClassTag evidence$23) {
        return ClassTagIterableFactory.range$(UnrolledBuffer$.MODULE$, start2, end, evidence$22, evidence$23);
    }

    public static Object unfold(Object init2, Function1 f, Object evidence$11) {
        UnrolledBuffer$ unfold_this = UnrolledBuffer$.MODULE$;
        View.Unfold from_it = new View.Unfold(init2, f);
        return unfold_this.from(from_it, (ClassTag)evidence$11);
    }

    public static Object iterate(Object start2, int len, Function1 f, Object evidence$10) {
        UnrolledBuffer$ iterate_this = UnrolledBuffer$.MODULE$;
        View.Iterate<Object> from_it = new View.Iterate<Object>(start2, len, f);
        return iterate_this.from(from_it, (ClassTag)evidence$10);
    }

    @Override
    public Object writeReplace() {
        return DefaultSerializable.writeReplace$(this);
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
    public <NewTo> Builder<T, NewTo> mapResult(Function1<UnrolledBuffer<T>, NewTo> f) {
        return Builder.mapResult$(this, f);
    }

    @Override
    public IterableOps fromSpecific(IterableOnce coll) {
        return EvidenceIterableFactoryDefaults.fromSpecific$(this, coll);
    }

    @Override
    public Builder<T, UnrolledBuffer<T>> newSpecificBuilder() {
        return EvidenceIterableFactoryDefaults.newSpecificBuilder$(this);
    }

    @Override
    public IterableOps empty() {
        return EvidenceIterableFactoryDefaults.empty$(this);
    }

    @Override
    public Object distinctBy(Function1 f) {
        return StrictOptimizedSeqOps.distinctBy$(this, f);
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
    public Tuple2<UnrolledBuffer<T>, UnrolledBuffer<T>> partition(Function1<T, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<UnrolledBuffer<T>, UnrolledBuffer<T>> span(Function1<T, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<UnrolledBuffer<A1>, UnrolledBuffer<A2>> unzip(Function1<T, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<UnrolledBuffer<A1>, UnrolledBuffer<A2>, UnrolledBuffer<A3>> unzip3(Function1<T, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<T, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<T, IterableOnce<B>> f) {
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
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<T, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<T, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<T, B>, C2> b) {
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
    public <A1, A2> Tuple2<UnrolledBuffer<A1>, UnrolledBuffer<A2>> partitionMap(Function1<T, Either<A1, A2>> f) {
        return StrictOptimizedIterableOps.partitionMap$(this, f);
    }

    @Override
    public Object tapEach(Function1 f) {
        return StrictOptimizedIterableOps.tapEach$(this, f);
    }

    @Override
    public Object takeRight(int n) {
        return StrictOptimizedIterableOps.takeRight$(this, n);
    }

    @Override
    public Object dropRight(int n) {
        return StrictOptimizedIterableOps.dropRight$(this, n);
    }

    public ClassTag<T> tag() {
        return this.tag;
    }

    public Unrolled<T> scala$collection$mutable$UnrolledBuffer$$headptr() {
        return this.scala$collection$mutable$UnrolledBuffer$$headptr;
    }

    private void headptr_$eq(Unrolled<T> x$1) {
        this.scala$collection$mutable$UnrolledBuffer$$headptr = x$1;
    }

    private Unrolled<T> lastptr() {
        return this.lastptr;
    }

    private void lastptr_$eq(Unrolled<T> x$1) {
        this.lastptr = x$1;
    }

    private int sz() {
        return this.sz;
    }

    private void sz_$eq(int x$1) {
        this.sz = x$1;
    }

    public Unrolled<T> headPtr() {
        return this.scala$collection$mutable$UnrolledBuffer$$headptr();
    }

    public void headPtr_$eq(Unrolled<T> head) {
        this.headptr_$eq(head);
    }

    public Unrolled<T> lastPtr() {
        return this.lastptr();
    }

    public void lastPtr_$eq(Unrolled<T> last) {
        this.lastptr_$eq(last);
    }

    public void size_$eq(int s2) {
        this.sz_$eq(s2);
    }

    public UnrolledBuffer$ evidenceIterableFactory() {
        return UnrolledBuffer$.MODULE$;
    }

    @Override
    public ClassTag<T> iterableEvidence() {
        return this.tag();
    }

    @Override
    public SeqFactory<UnrolledBuffer> iterableFactory() {
        return UnrolledBuffer$.MODULE$.untagged();
    }

    public Unrolled<T> newUnrolled() {
        return new Unrolled<T>(this, this.tag());
    }

    public int calcNextLength(int sz) {
        return sz;
    }

    public UnrolledBuffer$ classTagCompanion() {
        return UnrolledBuffer$.MODULE$;
    }

    @Override
    public UnrolledBuffer<T> concat(UnrolledBuffer<T> that) {
        if (!this.lastptr().bind(that.scala$collection$mutable$UnrolledBuffer$$headptr())) {
            this.lastptr_$eq(that.lastPtr());
        }
        this.sz_$eq(this.sz() + super.sz());
        that.clear();
        return this;
    }

    @Override
    public UnrolledBuffer<T> addOne(T elem) {
        this.lastptr_$eq(this.lastptr().append(elem));
        this.sz_$eq(this.sz() + 1);
        return this;
    }

    @Override
    public void clear() {
        this.headptr_$eq(this.newUnrolled());
        this.lastptr_$eq(this.scala$collection$mutable$UnrolledBuffer$$headptr());
        this.sz_$eq(0);
    }

    @Override
    public Iterator<T> iterator() {
        return new AbstractIterator<T>(this){
            private int pos;
            private Unrolled<T> node;

            private int pos() {
                return this.pos;
            }

            private void pos_$eq(int x$1) {
                this.pos = x$1;
            }

            private Unrolled<T> node() {
                return this.node;
            }

            private void node_$eq(Unrolled<T> x$1) {
                this.node = x$1;
            }

            private void scan() {
                this.pos_$eq(this.pos() + 1);
                while (this.pos() >= this.node().size()) {
                    this.pos_$eq(0);
                    this.node_$eq(this.node().next());
                    if (this.node() != null) continue;
                    return;
                }
            }

            public boolean hasNext() {
                return this.node() != null;
            }

            public T next() {
                if (this.hasNext()) {
                    Object r = ScalaRunTime$.MODULE$.array_apply(this.node().array(), this.pos());
                    this.scan();
                    return (T)r;
                }
                return (T)Iterator$.scala$collection$Iterator$$_empty.next();
            }
            {
                this.pos = -1;
                this.node = $outer.scala$collection$mutable$UnrolledBuffer$$headptr();
                this.scan();
            }
        };
    }

    @Override
    public <U> void foreach(Function1<T, U> f) {
        this.scala$collection$mutable$UnrolledBuffer$$headptr().foreach(f);
    }

    @Override
    public UnrolledBuffer<T> result() {
        return this;
    }

    @Override
    public int length() {
        return this.sz();
    }

    @Override
    public int knownSize() {
        return this.sz();
    }

    @Override
    public T apply(int idx) {
        if (idx >= 0 && idx < this.sz()) {
            return this.scala$collection$mutable$UnrolledBuffer$$headptr().apply(idx);
        }
        throw new IndexOutOfBoundsException(new StringBuilder(31).append(idx).append(" is out of bounds (min 0, max ").append(this.sz() - 1).append(")").toString());
    }

    @Override
    public void update(int idx, T newelem) {
        if (idx >= 0 && idx < this.sz()) {
            this.scala$collection$mutable$UnrolledBuffer$$headptr().update(idx, newelem);
            return;
        }
        throw new IndexOutOfBoundsException(new StringBuilder(31).append(idx).append(" is out of bounds (min 0, max ").append(this.sz() - 1).append(")").toString());
    }

    public UnrolledBuffer<T> mapInPlace(Function1<T, T> f) {
        this.scala$collection$mutable$UnrolledBuffer$$headptr().mapInPlace(f);
        return this;
    }

    @Override
    public T remove(int idx) {
        if (idx >= 0 && idx < this.sz()) {
            this.sz_$eq(this.sz() - 1);
            return this.scala$collection$mutable$UnrolledBuffer$$headptr().remove(idx, this);
        }
        throw new IndexOutOfBoundsException(new StringBuilder(31).append(idx).append(" is out of bounds (min 0, max ").append(this.sz() - 1).append(")").toString());
    }

    @Override
    public final void remove(int idx, int count) {
        while (count > 0) {
            this.remove(idx);
            --count;
        }
    }

    @Override
    public UnrolledBuffer<T> prepend(T elem) {
        this.headptr_$eq(this.scala$collection$mutable$UnrolledBuffer$$headptr().prepend(elem));
        this.sz_$eq(this.sz() + 1);
        return this;
    }

    @Override
    public void insert(int idx, T elem) {
        Nil$ $colon$colon_this = Nil$.MODULE$;
        Object var3_3 = null;
        this.insertAll(idx, (IterableOnce<T>)new $colon$colon<Nothing$>((Nothing$)elem, $colon$colon_this));
    }

    @Override
    public void insertAll(int idx, IterableOnce<T> elems) {
        if (idx >= 0 && idx <= this.sz()) {
            this.sz_$eq(this.sz() + this.scala$collection$mutable$UnrolledBuffer$$headptr().insertAll(idx, elems, this));
            return;
        }
        throw new IndexOutOfBoundsException(new StringBuilder(31).append(idx).append(" is out of bounds (min 0, max ").append(this.sz() - 1).append(")").toString());
    }

    @Override
    public UnrolledBuffer<T> subtractOne(T elem) {
        if (this.scala$collection$mutable$UnrolledBuffer$$headptr().subtractOne(elem, this)) {
            this.sz_$eq(this.sz() - 1);
        }
        return this;
    }

    @Override
    public UnrolledBuffer<T> patchInPlace(int from, IterableOnce<T> patch, int replaced) {
        this.remove(from, replaced);
        this.insertAll(from, patch);
        return this;
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        out.writeInt(this.sz());
        this.foreach((Function1)(Function1<Object, Object> & Serializable)elem -> {
            out.writeObject(elem);
            return BoxedUnit.UNIT;
        });
    }

    private void readObject(ObjectInputStream in) {
        in.defaultReadObject();
        int num = in.readInt();
        this.headPtr_$eq(this.newUnrolled());
        this.lastPtr_$eq(this.headPtr());
        this.sz_$eq(0);
        for (int i = 0; i < num; ++i) {
            Object $plus$eq_elem = in.readObject();
            this.addOne($plus$eq_elem);
            Object var4_4 = null;
        }
    }

    @Override
    public UnrolledBuffer<T> clone() {
        return (UnrolledBuffer)new UnrolledBuffer<T>(this.tag()).addAll(this);
    }

    @Override
    public String className() {
        return "UnrolledBuffer";
    }

    public UnrolledBuffer(ClassTag<T> tag) {
        this.tag = tag;
        this.scala$collection$mutable$UnrolledBuffer$$headptr = this.newUnrolled();
        this.lastptr = this.scala$collection$mutable$UnrolledBuffer$$headptr();
        this.sz = 0;
    }

    public static class Unrolled<T> {
        private int size;
        private Object array;
        private Unrolled<T> next;
        private final UnrolledBuffer<T> buff;
        private final ClassTag<T> evidence$4;

        public int size() {
            return this.size;
        }

        public void size_$eq(int x$1) {
            this.size = x$1;
        }

        public Object array() {
            return this.array;
        }

        public void array_$eq(Object x$1) {
            this.array = x$1;
        }

        public Unrolled<T> next() {
            return this.next;
        }

        public void next_$eq(Unrolled<T> x$1) {
            this.next = x$1;
        }

        public UnrolledBuffer<T> buff() {
            return this.buff;
        }

        private int nextlength() {
            if (this.buff() == null) {
                return UnrolledBuffer$.MODULE$.unrolledlength();
            }
            return this.buff().calcNextLength(Array.getLength(this.array()));
        }

        public final Unrolled<T> append(T elem) {
            while (true) {
                if (this_.size() < Array.getLength(this_.array())) {
                    ScalaRunTime$.MODULE$.array_update(this_.array(), this_.size(), elem);
                    this_.size_$eq(this_.size() + 1);
                    return this_;
                }
                this_.next_$eq(new Unrolled<T>(0, this_.evidence$4.newArray(this_.nextlength()), null, this_.buff(), this_.evidence$4));
                Unrolled<T> this_ = this_.next();
            }
        }

        public <U> void foreach(Function1<T, U> f) {
            int i = 0;
            for (Unrolled<T> unrolled = this; unrolled != null; unrolled = unrolled.next()) {
                Object chunkarr = unrolled.array();
                int chunksz = unrolled.size();
                while (i < chunksz) {
                    Object elem = ScalaRunTime$.MODULE$.array_apply(chunkarr, i);
                    f.apply(elem);
                    ++i;
                }
                i = 0;
            }
        }

        public void mapInPlace(Function1<T, T> f) {
            int i = 0;
            for (Unrolled<T> unrolled = this; unrolled != null; unrolled = unrolled.next()) {
                Object chunkarr = unrolled.array();
                int chunksz = unrolled.size();
                while (i < chunksz) {
                    Object elem = ScalaRunTime$.MODULE$.array_apply(chunkarr, i);
                    ScalaRunTime$.MODULE$.array_update(chunkarr, i, f.apply(elem));
                    ++i;
                }
                i = 0;
            }
        }

        public final T apply(int idx) {
            while (idx >= this_.size()) {
                idx -= this_.size();
                Unrolled<T> this_ = this_.next();
            }
            return (T)ScalaRunTime$.MODULE$.array_apply(this_.array(), idx);
        }

        public final void update(int idx, T newelem) {
            while (true) {
                if (idx < this_.size()) {
                    ScalaRunTime$.MODULE$.array_update(this_.array(), idx, newelem);
                    return;
                }
                idx -= this_.size();
                Unrolled<T> this_ = this_.next();
            }
        }

        public final Unrolled<T> locate(int idx) {
            while (idx >= this_.size()) {
                idx -= this_.size();
                Unrolled<T> this_ = this_.next();
            }
            return this_;
        }

        public Unrolled<T> prepend(T elem) {
            if (this.size() < Array.getLength(this.array())) {
                this.shiftright();
                ScalaRunTime$.MODULE$.array_update(this.array(), 0, elem);
                this.size_$eq(this.size() + 1);
                return this;
            }
            Unrolled<T> newhead = new Unrolled<T>(this.buff(), this.evidence$4);
            newhead.append(elem);
            newhead.next_$eq(this);
            return newhead;
        }

        private void shiftright() {
            for (int i = this.size() - 1; i >= 0; --i) {
                ScalaRunTime$.MODULE$.array_update(this.array(), i + 1, ScalaRunTime$.MODULE$.array_apply(this.array(), i));
            }
        }

        public final T remove(int idx, UnrolledBuffer<T> buffer) {
            while (true) {
                if (idx < this_.size()) {
                    Object r = ScalaRunTime$.MODULE$.array_apply(this_.array(), idx);
                    this_.shiftleft(idx);
                    this_.size_$eq(this_.size() - 1);
                    if (this_.tryMergeWithNext()) {
                        buffer.lastPtr_$eq(this_);
                    }
                    return (T)r;
                }
                idx -= this_.size();
                Unrolled<T> this_ = this_.next();
            }
        }

        public final boolean subtractOne(T elem, UnrolledBuffer<T> buffer) {
            while (true) {
                for (int i = 0; i < this_.size(); ++i) {
                    if (!BoxesRunTime.equals(ScalaRunTime$.MODULE$.array_apply(this_.array(), i), elem)) continue;
                    this_.remove(i, buffer);
                    return true;
                }
                if (this_.next() == null) break;
                Unrolled<T> this_ = this_.next();
            }
            return false;
        }

        private void shiftleft(int leftb) {
            int i;
            for (i = leftb; i < this.size() - 1; ++i) {
                ScalaRunTime$.MODULE$.array_update(this.array(), i, ScalaRunTime$.MODULE$.array_apply(this.array(), i + 1));
            }
            this.nullout(i, i + 1);
        }

        public boolean tryMergeWithNext() {
            if (this.next() != null) {
                if (this.size() + this.next().size() < Array.getLength(this.array()) * UnrolledBuffer$.MODULE$.waterline() / 100) {
                    Array$.MODULE$.copy(this.next().array(), 0, this.array(), this.size(), this.next().size());
                    this.size_$eq(this.size() + this.next().size());
                    this.next_$eq(this.next().next());
                    return this.next() == null;
                }
            }
            return false;
        }

        public final int insertAll(int idx, IterableOnce<T> t2, UnrolledBuffer<T> buffer) {
            while (true) {
                if (idx < this_.size()) {
                    Unrolled<T> newnextnode = new Unrolled<T>(0, this_.evidence$4.newArray(Array.getLength(this_.array())), null, this_.buff(), this_.evidence$4);
                    Array$.MODULE$.copy(this_.array(), idx, newnextnode.array(), 0, this_.size() - idx);
                    newnextnode.size_$eq(this_.size() - idx);
                    newnextnode.next_$eq(this_.next());
                    super.nullout(idx, this_.size());
                    this_.size_$eq(idx);
                    this_.next_$eq(null);
                    ObjectRef curr = new ObjectRef(this_);
                    int create_e = 0;
                    IntRef appended = new IntRef(create_e);
                    t2.iterator().foreach((Function1<Object, Object> & Serializable)(A elem) -> {
                        Unrolled.$anonfun$insertAll$1(curr, appended, elem);
                        return BoxedUnit.UNIT;
                    });
                    ((Unrolled)curr.elem).next_$eq(newnextnode);
                    if (((Unrolled)curr.elem).tryMergeWithNext()) {
                        buffer.lastPtr_$eq((Unrolled)curr.elem);
                    } else if (newnextnode.next() == null) {
                        buffer.lastPtr_$eq(newnextnode);
                    }
                    return appended.elem;
                }
                if (idx == this_.size() || this_.next() == null) {
                    ObjectRef<Unrolled> curr = new ObjectRef<Unrolled>(this_);
                    int create_e = 0;
                    IntRef appended = new IntRef(create_e);
                    t2.iterator().foreach((Function1<Object, Object> & Serializable)(A elem) -> {
                        Unrolled.$anonfun$insertAll$2(curr, appended, elem);
                        return BoxedUnit.UNIT;
                    });
                    return appended.elem;
                }
                idx -= this_.size();
                Unrolled<T> this_ = this_.next();
            }
        }

        private void nullout(int from, int until) {
            for (int idx = from; idx < until; ++idx) {
                ScalaRunTime$.MODULE$.array_update(this.array(), idx, null);
            }
        }

        public boolean bind(Unrolled<T> thathead) {
            Predef$.MODULE$.assert(this.next() == null);
            this.next_$eq(thathead);
            return this.tryMergeWithNext();
        }

        /*
         * WARNING - void declaration
         */
        public String toString() {
            void mkString_end;
            void mkString_sep;
            void mkString_start;
            int take$extension_n = this.size();
            ArraySeq arraySeq = Predef$.MODULE$.genericWrapArray(ArrayOps$.MODULE$.slice$extension(this.array(), 0, take$extension_n));
            String string = ")";
            String string2 = ", ";
            String string3 = new StringBuilder(4).append(StringOps$.MODULE$.format$extension("Unrolled@%08x", ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{System.identityHashCode(this)}))).append("[").append(this.size()).append("/").append(Array.getLength(this.array())).append("](").toString();
            if (arraySeq == null) {
                throw null;
            }
            string3 = null;
            string2 = null;
            string = null;
            return new StringBuilder(4).append(IterableOnceOps.mkString$(arraySeq, (String)mkString_start, (String)mkString_sep, (String)mkString_end)).append(" -> ").append((Object)(this.next() != null ? this.next().toString() : "")).toString();
        }

        public static final /* synthetic */ void $anonfun$insertAll$1(ObjectRef curr$1, IntRef appended$1, Object elem) {
            curr$1.elem = ((Unrolled)curr$1.elem).append(elem);
            ++appended$1.elem;
        }

        public static final /* synthetic */ void $anonfun$insertAll$2(ObjectRef curr$2, IntRef appended$2, Object elem) {
            curr$2.elem = ((Unrolled)curr$2.elem).append(elem);
            ++appended$2.elem;
        }

        public Unrolled(int size, Object array, Unrolled<T> next, UnrolledBuffer<T> buff, ClassTag<T> evidence$4) {
            this.size = size;
            this.array = array;
            this.next = next;
            this.buff = buff;
            this.evidence$4 = evidence$4;
        }

        public Unrolled(ClassTag<T> evidence$5) {
            this(0, evidence$5.newArray(UnrolledBuffer$.MODULE$.unrolledlength()), null, null, evidence$5);
        }

        public Unrolled(UnrolledBuffer<T> b, ClassTag<T> evidence$6) {
            this(0, evidence$6.newArray(UnrolledBuffer$.MODULE$.unrolledlength()), null, b, evidence$6);
        }
    }
}

