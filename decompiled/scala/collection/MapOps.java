/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import java.util.NoSuchElementException;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.collection.AbstractIterator;
import scala.collection.AbstractSet;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.MapFactory;
import scala.collection.MapView;
import scala.collection.Set;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StepperShape$;
import scala.collection.View;
import scala.collection.View$;
import scala.collection.convert.impl.AnyIteratorStepper;
import scala.collection.convert.impl.DoubleIteratorStepper;
import scala.collection.convert.impl.IntIteratorStepper;
import scala.collection.convert.impl.LongIteratorStepper;
import scala.collection.generic.DefaultSerializable;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set$;
import scala.collection.mutable.StringBuilder;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.LazyRef;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@ScalaSignature(bytes="\u0006\u0005\u0011=fa\u0002\u001b6!\u0003\r\tA\u000f\u0005\u0006=\u0002!\ta\u0018\u0005\u0006G\u0002!\t\u0005\u001a\u0005\u0006Q\u0002!\t!\u001b\u0005\u0006w\u0002!\t\u0001 \u0005\b\u0003#\u0001AQCA\n\u0011\u001d\t\u0019\u0007\u0001D\u0001\u0003KBq!a\u001c\u0001\r\u0003\t\t\bC\u0004\u0002~\u0001!\t!a \t\u000f\u0005]\u0005\u0001\"\u0001\u0002\u001a\"9\u0011q\u001e\u0001\u0005B\u0005E\bb\u0002B\u0007\u0001\u0011\u0005!q\u0002\u0004\u0007\u0005/\u0001\u0001B!\u0007\t\u000f\t\u0005D\u0002\"\u0001\u0003d!9!q\r\u0007\u0005\u0002\t%da\u0003B\u0013\u0001A\u0005\u0019\u0011\u0003B\u0014\u0005\u001fBQAX\b\u0005\u0002}CqA!\u000b\u0010\t\u0003\u0011Y\u0003C\u0004\u00034=!\tA!\u000e\t\u000f\t}r\u0002\"\u0011\u0003B!9!\u0011J\b\u0005B\t\u0005\u0003b\u0002B&\u001f\u0011\u0005#Q\n\u0005\b\u0005_\u0002A\u0011\u0001B9\u0011\u001d\u0011)\b\u0001C\u0001\u0005oBqAa\u001f\u0001\t\u0003\u0011Y\u0003C\u0004\u0003~\u0001!\tAa \t\u000f\t\r\u0005\u0001\"\u0001\u0003\u0006\"9!\u0011\u0014\u0001\u0005\u0002\tm\u0005b\u0002B\\\u0001\u0011\u0005!\u0011\u0018\u0005\b\u0003\u001f\u0003A\u0011\u0001Bh\u0011\u001d\u0011\u0019\u0004\u0001C\u0001\u0005?DqAa9\u0001\t\u0003\u0011)\u000fC\u0004\u0003j\u0002!\tAa;\t\u000f\t}\b\u0001\"\u0001\u0004\u0002!91q\u0003\u0001\u0005\u0002\re\u0001bBB\u001a\u0001\u0011\u00051Q\u0007\u0005\b\u0007\u000f\u0002A\u0011AB%\u0011\u001d\u0019Y\u0006\u0001C!\u0007;Bqa! \u0001\t\u0003\u0019y\bC\u0004\u0004~\u0001!\ta!&\t\u000f\re\u0006\u0001\"\u0001\u0004<\"91\u0011\u001a\u0001\u0005\u0002\r-waBBqk!\u000511\u001d\u0004\u0007iUB\ta!:\t\u000f\t\u00054\u0006\"\u0001\u0004h\u001a11\u0011^\u0016\u0001\u0007WD!\u0002b\u0006.\u0005\u0003\u0005\u000b\u0011\u0002C\r\u0011)\u0011y*\fB\u0001B\u0003%A\u0011\u000b\u0005\b\u0005CjC\u0011\u0001C*\u0011\u001d\u0011I/\fC\u0001\tcBqaa\u0006.\t\u0003!)\tC\u0004\u0005\u001c6\"\t\u0005\"(\u0003\r5\u000b\u0007o\u00149t\u0015\t1t'\u0001\u0006d_2dWm\u0019;j_:T\u0011\u0001O\u0001\u0006g\u000e\fG.Y\u0002\u0001+\u0019Y\u0014jUA\r3N!\u0001\u0001\u0010!\\!\tid(D\u00018\u0013\tytG\u0001\u0004B]f\u0014VM\u001a\t\u0006\u0003\n#U\u000bW\u0007\u0002k%\u00111)\u000e\u0002\f\u0013R,'/\u00192mK>\u00038\u000f\u0005\u0003>\u000b\u001e\u0013\u0016B\u0001$8\u0005\u0019!V\u000f\u001d7feA\u0011\u0001*\u0013\u0007\u0001\t\u0015Q\u0005A1\u0001L\u0005\u0005Y\u0015C\u0001'P!\tiT*\u0003\u0002Oo\t9aj\u001c;iS:<\u0007CA\u001fQ\u0013\t\tvGA\u0002B]f\u0004\"\u0001S*\u0005\rQ\u0003AQ1\u0001L\u0005\u00051\u0006CA!W\u0013\t9VG\u0001\u0005Ji\u0016\u0014\u0018M\u00197f!\tA\u0015\f\u0002\u0004[\u0001\u0011\u0015\ra\u0013\u0002\u0002\u0007B!Q\bX$S\u0013\tivGA\bQCJ$\u0018.\u00197Gk:\u001cG/[8o\u0003\u0019!\u0013N\\5uIQ\t\u0001\r\u0005\u0002>C&\u0011!m\u000e\u0002\u0005+:LG/\u0001\u0003wS\u0016<X#A3\u0011\t\u00053wIU\u0005\u0003OV\u0012q!T1q-&,w/\u0001\u0006lKf\u001cF/\u001a9qKJ,\"A\u001b7\u0015\u0005-4\bC\u0001%m\t\u0015i7A1\u0001o\u0005\u0005\u0019\u0016C\u0001'pa\t\u0001H\u000fE\u0002BcNL!A]\u001b\u0003\u000fM#X\r\u001d9feB\u0011\u0001\n\u001e\u0003\nk2\f\t\u0011!A\u0003\u0002-\u00131a\u0018\u00134\u0011\u001598\u0001q\u0001y\u0003\u0015\u0019\b.\u00199f!\u0011\t\u0015pR6\n\u0005i,$\u0001D*uKB\u0004XM]*iCB,\u0017\u0001\u0004<bYV,7\u000b^3qa\u0016\u0014XCA?\u0000)\rq\u0018Q\u0002\t\u0003\u0011~$a!\u001c\u0003C\u0002\u0005\u0005\u0011c\u0001'\u0002\u0004A\"\u0011QAA\u0005!\u0011\t\u0015/a\u0002\u0011\u0007!\u000bI\u0001\u0002\u0006\u0002\f}\f\t\u0011!A\u0003\u0002-\u00131a\u0018\u00135\u0011\u00199H\u0001q\u0001\u0002\u0010A!\u0011)\u001f*\u007f\u0003=i\u0017\r\u001d$s_6LE/\u001a:bE2,WCBA\u000b\u0003\u0013\ny\u0005\u0006\u0003\u0002\u0018\u0005M\u0003c\u0002%\u0002\u001a\u0005\u001d\u0013Q\n\u0003\t\u00037\u0001AQ1\u0001\u0002\u001e\t\u00111iQ\u000b\u0007\u0003?\ty$a\u0011\u0012\u00071\u000b\t\u0003\r\u0004\u0002$\u0005\u001d\u00121\b\t\t\u0003\n\u000b)#a\u000b\u0002:A\u0019\u0001*a\n\u0005\u0017\u0005%\u0012\u0011DA\u0001\u0002\u0003\u0015\ta\u0013\u0002\u0004?\u0012\n\u0004\u0003BA\u0017\u0003gq1!QA\u0018\u0013\r\t\t$N\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\t)$a\u000e\u0003\u0013\u0005s\u0017pQ8ogR\u0014(bAA\u0019kA\u0019\u0001*a\u000f\u0005\u0017\u0005u\u0012\u0011DA\u0001\u0002\u0003\u0015\ta\u0013\u0002\u0004?\u0012\u0012DaBA!\u00033\u0011\ra\u0013\u0002\u0005?\u0012\"\u0013\u0007B\u0004\u0002F\u0005e!\u0019A&\u0003\t}#CE\r\t\u0004\u0011\u0006%CABA&\u000b\t\u00071J\u0001\u0002LeA\u0019\u0001*a\u0014\u0005\r\u0005ESA1\u0001L\u0005\t1&\u0007C\u0004\u0002V\u0015\u0001\r!a\u0016\u0002\u0005%$\b\u0003B!W\u00033\u0002b!P#\u0002H\u00055\u0003fA\u0003\u0002^A\u0019Q(a\u0018\n\u0007\u0005\u0005tG\u0001\u0004j]2Lg.Z\u0001\u000b[\u0006\u0004h)Y2u_JLXCAA4!\u0015\t\u0015\u0011NA7\u0013\r\tY'\u000e\u0002\u000b\u001b\u0006\u0004h)Y2u_JL\bc\u0001%\u0002\u001a\u0005\u0019q-\u001a;\u0015\t\u0005M\u0014\u0011\u0010\t\u0005{\u0005U$+C\u0002\u0002x]\u0012aa\u00149uS>t\u0007BBA>\u000f\u0001\u0007q)A\u0002lKf\f\u0011bZ3u\u001fJ,En]3\u0016\t\u0005\u0005\u0015Q\u0011\u000b\u0007\u0003\u0007\u000bY)!$\u0011\u0007!\u000b)\tB\u0004\u0002\b\"\u0011\r!!#\u0003\u0005Y\u000b\u0014C\u0001*P\u0011\u0019\tY\b\u0003a\u0001\u000f\"A\u0011q\u0012\u0005\u0005\u0002\u0004\t\t*A\u0004eK\u001a\fW\u000f\u001c;\u0011\u000bu\n\u0019*a!\n\u0007\u0005UuG\u0001\u0005=Eft\u0017-\\3?\u0003\u0015\t\u0007\u000f\u001d7z)\r\u0011\u00161\u0014\u0005\u0007\u0003wJ\u0001\u0019A$)\u000b%\ty*!-\u0011\u000bu\n\t+!*\n\u0007\u0005\rvG\u0001\u0004uQJ|wo\u001d\t\u0005\u0003O\u000bYKD\u0002>\u0003SK1!!\r8\u0013\u0011\ti+a,\u0003-9{7+^2i\u000b2,W.\u001a8u\u000bb\u001cW\r\u001d;j_:T1!!\r8c\u001dq\u00121WAe\u0003[\u0004B!!.\u0002D:!\u0011qWA`!\r\tIlN\u0007\u0003\u0003wS1!!0:\u0003\u0019a$o\\8u}%\u0019\u0011\u0011Y\u001c\u0002\rA\u0013X\rZ3g\u0013\u0011\t)-a2\u0003\rM#(/\u001b8h\u0015\r\t\tmN\u0019\nG\u0005-\u00171[Ar\u0003+,B!!4\u0002PV\u0011\u00111\u0017\u0003\b\u0003#L$\u0019AAn\u0005\u0005!\u0016\u0002BAk\u0003/\f1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\n$bAAmo\u00051A\u000f\u001b:poN\f2\u0001TAo!\u0011\t9+a8\n\t\u0005\u0005\u0018q\u0016\u0002\n)\"\u0014xn^1cY\u0016\f\u0014bIAs\u0003O\fI/!7\u000f\u0007u\n9/C\u0002\u0002Z^\nTAI\u001f8\u0003W\u0014Qa]2bY\u0006\f4AJAS\u0003-\t\u0007\u000f\u001d7z\u001fJ,En]3\u0016\r\u0005M\u0018q`A|)\u0019\t)0!?\u0003\u0006A\u0019\u0001*a>\u0005\u000f\u0005\u001d%B1\u0001\u0002\n\"9\u00111 \u0006A\u0002\u0005u\u0018!\u0001=\u0011\u0007!\u000by\u0010B\u0004\u0003\u0002)\u0011\rAa\u0001\u0003\u0005-\u000b\u0014C\u0001'H\u0011\u001d\tyI\u0003a\u0001\u0005\u000f\u0001r!\u0010B\u0005\u0003{\f)0C\u0002\u0003\f]\u0012\u0011BR;oGRLwN\\\u0019\u0002\r-,\u0017pU3u+\t\u0011\t\u0002\u0005\u0003B\u0005'9\u0015b\u0001B\u000bk\t\u00191+\u001a;\u0003\r-+\u0017pU3u'\u001da!1\u0004B\u0011\u0005+\u0002B!\u0011B\u000f\u000f&\u0019!qD\u001b\u0003\u0017\u0005\u00137\u000f\u001e:bGR\u001cV\r\u001e\t\u0004\u0005GyQ\"\u0001\u0001\u0003\u0013\u001d+gnS3z'\u0016$8CA\b=\u0003!IG/\u001a:bi>\u0014XC\u0001B\u0017!\u0011\t%qF$\n\u0007\tERG\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019wN\u001c;bS:\u001cH\u0003\u0002B\u001c\u0005{\u00012!\u0010B\u001d\u0013\r\u0011Yd\u000e\u0002\b\u0005>|G.Z1o\u0011\u0019\tYH\u0005a\u0001\u000f\u0006!1/\u001b>f+\t\u0011\u0019\u0005E\u0002>\u0005\u000bJ1Aa\u00128\u0005\rIe\u000e^\u0001\nW:|wO\\*ju\u0016\fq![:F[B$\u00180\u0006\u0002\u00038I1!\u0011\u000bB\u0011\u0005#1aAa\u0015\u0001\u0001\t=#\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004\u0003\u0002B,\u0005;j!A!\u0017\u000b\u0007\tmS'A\u0004hK:,'/[2\n\t\t}#\u0011\f\u0002\u0014\t\u00164\u0017-\u001e7u'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005\t\u0015\u0004c\u0001B\u0012\u0019\u0005!A-\u001b4g)\u0011\u0011\tBa\u001b\t\u000f\t5d\u00021\u0001\u0003\u0012\u0005!A\u000f[1u\u0003\u0011YW-_:\u0016\u0005\tM\u0004cA!W\u000f\u00061a/\u00197vKN,\"A!\u001f\u0011\u0007\u00053&+\u0001\u0007lKf\u001c\u0018\n^3sCR|'/\u0001\bwC2,Xm]%uKJ\fGo\u001c:\u0016\u0005\t\u0005\u0005\u0003B!\u00030I\u000bABZ8sK\u0006\u001c\u0007.\u00128uef,BAa\"\u0003\u0016R\u0019\u0001M!#\t\u000f\t-%\u00041\u0001\u0003\u000e\u0006\ta\rE\u0004>\u0005\u001f;%Ka%\n\u0007\tEuGA\u0005Gk:\u001cG/[8oeA\u0019\u0001J!&\u0005\r\t]%D1\u0001L\u0005\u0005)\u0016A\u00034jYR,'oS3zgR\u0019QM!(\t\u000f\t}5\u00041\u0001\u0003\"\u0006\t\u0001\u000f\u0005\u0004>\u0005\u00139%q\u0007\u0015\f7\t\u0015&1\u0016BW\u0005c\u0013\u0019\fE\u0002>\u0005OK1A!+8\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\t\u0011y+\u0001?Vg\u0016\u0004cF^5fo:2\u0017\u000e\u001c;fe.+\u0017p\u001d\u0015gS9\u0002\u0013\t\t4viV\u0014X\r\t<feNLwN\u001c\u0011xS2d\u0007%\u001b8dYV$W\rI1!gR\u0014\u0018n\u0019;!m\u0016\u00148/[8oA=4\u0007\u0005\u001e5jg\u0002jW\r\u001e5pI\u0002Bcm\u001c:!]><H\u0006\t\u0018wS\u0016<hFZ5mi\u0016\u00148*Z=tQALc\u0006^8NCBLc&A\u0003tS:\u001cW-\t\u0002\u00036\u00061!GL\u00194]A\n\u0011\"\\1q-\u0006dW/Z:\u0016\t\tm&\u0011\u0019\u000b\u0005\u0005{\u0013)\rE\u0003BM\u001e\u0013y\fE\u0002I\u0005\u0003$aAa1\u001d\u0005\u0004Y%!A,\t\u000f\t-E\u00041\u0001\u0003HB1QH!\u0003S\u0005\u007fC3\u0002\bBS\u0005W\u0013YM!-\u00034\u0006\u0012!QZ\u0001{+N,\u0007E\f<jK^tS.\u00199WC2,Xm\u001d\u0015gS9\u0002\u0013\t\t4viV\u0014X\r\t<feNLwN\u001c\u0011xS2d\u0007%\u001b8dYV$W\rI1!gR\u0014\u0018n\u0019;!m\u0016\u00148/[8oA=4\u0007\u0005\u001e5jg\u0002jW\r\u001e5pI\u0002Bcm\u001c:!]><H\u0006\t\u0018wS\u0016<h&\\1q-\u0006dW/Z:)M&rCo\\'ba&rCc\u0001*\u0003R\"1\u00111P\u000fA\u0002\u001dCS!HAP\u0005+\ftAHAZ\u0005/\u0014i.M\u0005$\u0003\u0017\f\u0019N!7\u0002VFJ1%!:\u0002h\nm\u0017\u0011\\\u0019\u0006Eu:\u00141^\u0019\u0004M\u0005\u0015F\u0003\u0002B\u001c\u0005CDa!a\u001f\u001f\u0001\u00049\u0015aC5t\t\u00164\u0017N\\3e\u0003R$BAa\u000e\u0003h\"1\u00111P\u0010A\u0002\u001d\u000b1!\\1q+\u0019\u0011iOa=\u0003xR!!q\u001eB}!\u001dA\u0015\u0011\u0004By\u0005k\u00042\u0001\u0013Bz\t\u0019\tY\u0005\tb\u0001\u0017B\u0019\u0001Ja>\u0005\r\u0005E\u0003E1\u0001L\u0011\u001d\u0011Y\t\ta\u0001\u0005w\u0004b!\u0010B\u0005\t\nu\bCB\u001fF\u0005c\u0014)0A\u0004d_2dWm\u0019;\u0016\r\r\r1\u0011BB\u0007)\u0011\u0019)aa\u0004\u0011\u000f!\u000bIba\u0002\u0004\fA\u0019\u0001j!\u0003\u0005\r\u0005-\u0013E1\u0001L!\rA5Q\u0002\u0003\u0007\u0003#\n#\u0019A&\t\u000f\rE\u0011\u00051\u0001\u0004\u0014\u0005\u0011\u0001O\u001a\t\u0006{q#5Q\u0003\t\u0007{\u0015\u001b9aa\u0003\u0002\u000f\u0019d\u0017\r^'baV111DB\u0011\u0007K!Ba!\b\u0004(A9\u0001*!\u0007\u0004 \r\r\u0002c\u0001%\u0004\"\u00111\u00111\n\u0012C\u0002-\u00032\u0001SB\u0013\t\u0019\t\tF\tb\u0001\u0017\"9!1\u0012\u0012A\u0002\r%\u0002CB\u001f\u0003\n\u0011\u001bY\u0003E\u0003B\u0007[\u0019\t$C\u0002\u00040U\u0012A\"\u0013;fe\u0006\u0014G.Z(oG\u0016\u0004b!P#\u0004 \r\r\u0012AB2p]\u000e\fG/\u0006\u0003\u00048\ruB\u0003BB\u001d\u0007\u007f\u0001b\u0001SA\r\u000f\u000em\u0002c\u0001%\u0004>\u00119\u0011\u0011K\u0012C\u0002\u0005%\u0005bBB!G\u0001\u000711I\u0001\u0007gV4g-\u001b=\u0011\u000b\u0005\u001bic!\u0012\u0011\u000bu*uia\u000f\u0002\u0015\u0011\u0002H.^:%a2,8/\u0006\u0003\u0004L\rEC\u0003BB'\u0007'\u0002b\u0001SA\r\u000f\u000e=\u0003c\u0001%\u0004R\u00119\u0011\u0011\u000b\u0013C\u0002\u0005%\u0005bBB+I\u0001\u00071qK\u0001\u0003qN\u0004R!QB\u0017\u00073\u0002R!P#H\u0007\u001f\n\u0011\"\u00193e'R\u0014\u0018N\\4\u0015\u0015\r}3\u0011MB9\u0007k\u001aIHD\u0002I\u0007CBqaa\u0019&\u0001\u0004\u0019)'\u0001\u0002tEB!1qMB7\u001b\t\u0019IGC\u0002\u0004lU\nq!\\;uC\ndW-\u0003\u0003\u0004p\r%$!D*ue&twMQ;jY\u0012,'\u000fC\u0004\u0004t\u0015\u0002\r!a-\u0002\u000bM$\u0018M\u001d;\t\u000f\r]T\u00051\u0001\u00024\u0006\u00191/\u001a9\t\u000f\rmT\u00051\u0001\u00024\u0006\u0019QM\u001c3\u0002\u000b\u0011\u0002H.^:\u0016\t\r\u00055q\u0011\u000b\u0005\u0007\u0007\u001bI\t\u0005\u0004I\u0003395Q\u0011\t\u0004\u0011\u000e\u001dEaBADM\t\u0007\u0011\u0011\u0012\u0005\b\u0007\u00173\u0003\u0019ABG\u0003\tYg\u000fE\u0003>\u000b\u001e\u001b)\tK\u0006'\u0005K\u0013Yk!%\u00032\nM\u0016EABJ\u0003}\u001auN\\:jI\u0016\u0014\bE]3rk&\u0014\u0018N\\4!C:\u0004\u0013.\\7vi\u0006\u0014G.\u001a\u0011NCB\u0004sN\u001d\u0011gC2d\u0007EY1dW\u0002\"x\u000eI'ba:\u001awN\\2bi:*Baa&\u0004\u001eRA1\u0011TBP\u0007K\u001bI\u000b\u0005\u0004I\u00033951\u0014\t\u0004\u0011\u000euEaBADO\t\u0007\u0011\u0011\u0012\u0005\b\u0007C;\u0003\u0019ABR\u0003\u0015)G.Z72!\u0015iTiRBN\u0011\u001d\u00199k\na\u0001\u0007G\u000bQ!\u001a7f[JBqaa+(\u0001\u0004\u0019i+A\u0003fY\u0016l7\u000fE\u0003>\u0007_\u001b\u0019+C\u0002\u00042^\u0012!\u0002\u0010:fa\u0016\fG/\u001a3?Q-9#Q\u0015BV\u0007k\u0013\tLa-\"\u0005\r]\u0016!R+tK\u0002Z3\u0006I<ji\"\u0004\u0013M\u001c\u0011fqBd\u0017nY5uA\r|G\u000e\\3di&|g\u000eI1sOVlWM\u001c;!S:\u001cH/Z1eA=4\u0007e\u000b\u0011xSRD\u0007E^1sCJ<7/\u0001\u0007%[&tWo\u001d\u0013nS:,8\u000fF\u0002Y\u0007{CqAa\u001c)\u0001\u0004\u0019y\f\u0005\u0003B\u0007[9\u0005f\u0001\u0015\u0002^!Z\u0001F!*\u0003,\u000e\u0015'\u0011\u0017BZC\t\u00199-\u0001\u0013D_:\u001c\u0018\u000eZ3sAI,\u0017/^5sS:<\u0007%\u00198!S6lW\u000f^1cY\u0016\u0004S*\u00199/\u0003A!\u0003\u000f\\;tIAdWo\u001d\u0013d_2|g.\u0006\u0003\u0004N\u000eMG\u0003BBh\u0007+\u0004b\u0001SA\r\u000f\u000eE\u0007c\u0001%\u0004T\u00129\u0011qQ\u0015C\u0002\u0005%\u0005b\u0002B7S\u0001\u00071q\u001b\t\u0006\u0003\u000e52\u0011\u001c\t\u0006{\u0015;5\u0011\u001b\u0015\fS\t\u0015&1VBo\u0005c\u0013\u0019,\t\u0002\u0004`\u00061Tk]3!W-\u0002\u0013N\\:uK\u0006$\u0007e\u001c4!W-R\u0004EZ8sA\r|G\u000e\\3di&|gn\u001d\u0011pM\u0002\"\u0018\u0010]3!\u0013R,'/\u00192mK\u00061Q*\u00199PaN\u0004\"!Q\u0016\u0014\u0005-bDCABr\u0005)9\u0016\u000e\u001e5GS2$XM]\u000b\u000b\u0007[\u001cy\u0010b\u0001\u0005\b\u0011\r2#B\u0017\u0004p\u0012E\u0001\u0003CBy\u0007o\u001cY\u0010\"\u0002\u000f\u0007\u0005\u001b\u00190C\u0002\u0004vV\n1\"\u0013;fe\u0006\u0014G.Z(qg&!1\u0011^B}\u0015\r\u0019)0\u000e\t\u0007{\u0015\u001bi\u0010\"\u0001\u0011\u0007!\u001by\u0010B\u0003K[\t\u00071\nE\u0002I\t\u0007!a\u0001V\u0017\u0005\u0006\u0004Y\u0005c\u0001%\u0005\b\u0011AA\u0011B\u0017\u0005\u0006\u0004!YA\u0001\u0006Ji\u0016\u0014\u0018M\u00197f\u0007\u000e+2a\u0013C\u0007\t\u001d!y\u0001b\u0002C\u0002-\u0013Aa\u0018\u0013%gA!\u0011q\u0015C\n\u0013\u0011!)\"a,\u0003\u0019M+'/[1mSj\f'\r\\3\u0002\tM,GN\u001a\n\u0007\t7!i\u0002b\u0012\u0007\r\tM3\u0006\u0001C\ra\u0011!y\u0002b\u0011\u0011\u0015\u0005\u00031Q C\u0001\tC!\t\u0005E\u0002I\tG!\u0001\"a\u0007.\t\u000b\u0007AQE\u000b\u0007\tO!I\u0004\"\u0010\u0012\u00071#I\u0003\r\u0004\u0005,\u0011=BQ\u0007\t\t\u0003\n#i#a\u000b\u00054A\u0019\u0001\nb\f\u0005\u0017\u0011EB1EA\u0001\u0002\u0003\u0015\ta\u0013\u0002\u0004?\u0012*\u0004c\u0001%\u00056\u0011YAq\u0007C\u0012\u0003\u0003\u0005\tQ!\u0001L\u0005\ryFE\u000e\u0003\b\tw!\u0019C1\u0001L\u0005\u0011yF\u0005\n\u001b\u0005\u000f\u0011}B1\u0005b\u0001\u0017\n!q\f\n\u00136!\rAE1\t\u0003\u000b\t\u000br\u0013\u0011!A\u0001\u0006\u0003Y%aA0%oA\"A\u0011\nC'!!\t%ia?\u0005\u0006\u0011-\u0003c\u0001%\u0005N\u0011QAq\n\u0018\u0002\u0002\u0003\u0005)\u0011A&\u0003\u0007}#\u0003\bE\u0004>\u0005\u0013\u0019YPa\u000e\u0015\r\u0011UC\u0011\fC8!-!9&LB\u007f\t\u0003!)\u0001\"\t\u000e\u0003-Bq\u0001b\u00061\u0001\u0004!YF\u0005\u0004\u0005^\u0011}Cq\r\u0004\u0007\u0005'Z\u0003\u0001b\u00171\t\u0011\u0005DQ\r\t\u000b\u0003\u0002\u0019i\u0010\"\u0001\u0005\"\u0011\r\u0004c\u0001%\u0005f\u0011YAQ\tC-\u0003\u0003\u0005\tQ!\u0001La\u0011!I\u0007\"\u001c\u0011\u0011\u0005\u001351 C\u0003\tW\u00022\u0001\u0013C7\t-!y\u0005\"\u0017\u0002\u0002\u0003\u0005)\u0011A&\t\u000f\t}\u0005\u00071\u0001\u0005RU1A1\u000fC=\t{\"B\u0001\"\u001e\u0005\u0000A9\u0001\nb\t\u0005x\u0011m\u0004c\u0001%\u0005z\u00111\u00111J\u0019C\u0002-\u00032\u0001\u0013C?\t\u0019\t\t&\rb\u0001\u0017\"9!1R\u0019A\u0002\u0011\u0005\u0005cB\u001f\u0003\n\rmH1\u0011\t\u0007{\u0015#9\bb\u001f\u0016\r\u0011\u001dEQ\u0012CI)\u0011!I\tb%\u0011\u000f!#\u0019\u0003b#\u0005\u0010B\u0019\u0001\n\"$\u0005\r\u0005-#G1\u0001L!\rAE\u0011\u0013\u0003\u0007\u0003#\u0012$\u0019A&\t\u000f\t-%\u00071\u0001\u0005\u0016B9QH!\u0003\u0004|\u0012]\u0005#B!\u0004.\u0011e\u0005CB\u001fF\t\u0017#y)\u0001\u0006xSRDg)\u001b7uKJ$B\u0001\"\u0016\u0005 \"9A\u0011U\u001aA\u0002\u0011E\u0013!A9)\u000f5\")\u000bb+\u0005.B\u0019Q\bb*\n\u0007\u0011%vG\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t1\u0001")
public interface MapOps<K, V, CC extends IterableOps<?, Object, ?>, C>
extends IterableOps<Tuple2<K, V>, Iterable, C>,
PartialFunction<K, V> {
    public static /* synthetic */ MapView view$(MapOps $this) {
        return $this.view();
    }

    default public MapView<K, V> view() {
        return new MapView.Id(this);
    }

    public static /* synthetic */ Stepper keyStepper$(MapOps $this, StepperShape shape) {
        return $this.keyStepper(shape);
    }

    default public <S extends Stepper<?>> S keyStepper(StepperShape<K, S> shape) {
        int n = shape.shape();
        if (StepperShape$.MODULE$.IntShape() == n) {
            return (S)new IntIteratorStepper(this.keysIterator());
        }
        if (StepperShape$.MODULE$.LongShape() == n) {
            return (S)new LongIteratorStepper(this.keysIterator());
        }
        if (StepperShape$.MODULE$.DoubleShape() == n) {
            return (S)new DoubleIteratorStepper(this.keysIterator());
        }
        return shape.seqUnbox(new AnyIteratorStepper<K>(this.keysIterator()));
    }

    public static /* synthetic */ Stepper valueStepper$(MapOps $this, StepperShape shape) {
        return $this.valueStepper(shape);
    }

    default public <S extends Stepper<?>> S valueStepper(StepperShape<V, S> shape) {
        int n = shape.shape();
        if (StepperShape$.MODULE$.IntShape() == n) {
            return (S)new IntIteratorStepper(this.valuesIterator());
        }
        if (StepperShape$.MODULE$.LongShape() == n) {
            return (S)new LongIteratorStepper(this.valuesIterator());
        }
        if (StepperShape$.MODULE$.DoubleShape() == n) {
            return (S)new DoubleIteratorStepper(this.valuesIterator());
        }
        return shape.seqUnbox(new AnyIteratorStepper<V>(this.valuesIterator()));
    }

    public static /* synthetic */ IterableOps mapFromIterable$(MapOps $this, Iterable it) {
        return $this.mapFromIterable(it);
    }

    default public <K2, V2> CC mapFromIterable(Iterable<Tuple2<K2, V2>> it) {
        return (CC)((IterableOps)this.mapFactory().from(it));
    }

    public MapFactory<CC> mapFactory();

    public Option<V> get(K var1);

    public static /* synthetic */ Object getOrElse$(MapOps $this, Object key, Function0 function0) {
        return $this.getOrElse(key, function0);
    }

    default public <V1> V1 getOrElse(K key, Function0<V1> function0) {
        Option<V> option = this.get(key);
        if (option instanceof Some) {
            return (V1)((Some)option).value();
        }
        if (None$.MODULE$.equals(option)) {
            return function0.apply();
        }
        throw new MatchError(option);
    }

    public static /* synthetic */ Object apply$(MapOps $this, Object key) {
        return $this.apply((K)key);
    }

    @Override
    default public V apply(K key) throws NoSuchElementException {
        Option<V> option = this.get(key);
        if (None$.MODULE$.equals(option)) {
            return this.default(key);
        }
        if (option instanceof Some) {
            return (V)((Some)option).value();
        }
        throw new MatchError(option);
    }

    public static /* synthetic */ Object applyOrElse$(MapOps $this, Object x, Function1 function1) {
        return $this.applyOrElse((K1)x, function1);
    }

    @Override
    default public <K1 extends K, V1> V1 applyOrElse(K1 x, Function1<K1, V1> function1) {
        return this.getOrElse(x, (Function0<Object> & Serializable)() -> function1.apply(x));
    }

    public static /* synthetic */ Set keySet$(MapOps $this) {
        return $this.keySet();
    }

    default public Set<K> keySet() {
        return new KeySet();
    }

    public static /* synthetic */ Iterable keys$(MapOps $this) {
        return $this.keys();
    }

    default public Iterable<K> keys() {
        return this.keySet();
    }

    public static /* synthetic */ Iterable values$(MapOps $this) {
        return $this.values();
    }

    default public Iterable<V> values() {
        return new DefaultSerializable(this){
            private final /* synthetic */ MapOps $outer;

            public Object writeReplace() {
                return DefaultSerializable.writeReplace$(this);
            }

            public int knownSize() {
                return this.$outer.knownSize();
            }

            public Iterator<V> iterator() {
                return this.$outer.valuesIterator();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public static /* synthetic */ Iterator keysIterator$(MapOps $this) {
        return $this.keysIterator();
    }

    default public Iterator<K> keysIterator() {
        return new AbstractIterator<K>(this){
            private final Iterator<Tuple2<K, V>> iter;

            private Iterator<Tuple2<K, V>> iter() {
                return this.iter;
            }

            public boolean hasNext() {
                return this.iter().hasNext();
            }

            public K next() {
                return this.iter().next()._1();
            }
            {
                this.iter = $outer.iterator();
            }
        };
    }

    public static /* synthetic */ Iterator valuesIterator$(MapOps $this) {
        return $this.valuesIterator();
    }

    default public Iterator<V> valuesIterator() {
        return new AbstractIterator<V>(this){
            private final Iterator<Tuple2<K, V>> iter;

            private Iterator<Tuple2<K, V>> iter() {
                return this.iter;
            }

            public boolean hasNext() {
                return this.iter().hasNext();
            }

            public V next() {
                return this.iter().next()._2();
            }
            {
                this.iter = $outer.iterator();
            }
        };
    }

    public static /* synthetic */ void foreachEntry$(MapOps $this, Function2 f) {
        $this.foreachEntry(f);
    }

    default public <U> void foreachEntry(Function2<K, V, U> f) {
        Iterator it = this.iterator();
        while (it.hasNext()) {
            Tuple2 next = (Tuple2)it.next();
            f.apply(next._1(), next._2());
        }
    }

    public static /* synthetic */ MapView filterKeys$(MapOps $this, Function1 p) {
        return $this.filterKeys(p);
    }

    default public MapView<K, V> filterKeys(Function1<K, Object> p) {
        return new MapView.FilterKeys(this, p);
    }

    public static /* synthetic */ MapView mapValues$(MapOps $this, Function1 f) {
        return $this.mapValues(f);
    }

    default public <W> MapView<K, W> mapValues(Function1<V, W> f) {
        return new MapView.MapValues(this, f);
    }

    public static /* synthetic */ Object default$(MapOps $this, Object key) {
        return $this.default(key);
    }

    default public V default(K key) throws NoSuchElementException {
        throw new NoSuchElementException(new java.lang.StringBuilder(15).append("key not found: ").append(key).toString());
    }

    public static /* synthetic */ boolean contains$(MapOps $this, Object key) {
        return $this.contains(key);
    }

    default public boolean contains(K key) {
        return this.get(key).isDefined();
    }

    public static /* synthetic */ boolean isDefinedAt$(MapOps $this, Object key) {
        return $this.isDefinedAt((K)key);
    }

    @Override
    default public boolean isDefinedAt(K key) {
        return this.contains(key);
    }

    public static /* synthetic */ IterableOps map$(MapOps $this, Function1 f) {
        return $this.map(f);
    }

    @Override
    default public <K2, V2> CC map(Function1<Tuple2<K, V>, Tuple2<K2, V2>> f) {
        return (CC)((IterableOps)this.mapFactory().from(new View.Map<Tuple2<K, V>, Tuple2<K2, V2>>(this, f)));
    }

    public static /* synthetic */ IterableOps collect$(MapOps $this, PartialFunction pf) {
        return $this.collect(pf);
    }

    @Override
    default public <K2, V2> CC collect(PartialFunction<Tuple2<K, V>, Tuple2<K2, V2>> pf) {
        return (CC)((IterableOps)this.mapFactory().from(new View.Collect<Tuple2<K, V>, Tuple2<K2, V2>>(this, pf)));
    }

    public static /* synthetic */ IterableOps flatMap$(MapOps $this, Function1 f) {
        return $this.flatMap(f);
    }

    @Override
    default public <K2, V2> CC flatMap(Function1<Tuple2<K, V>, IterableOnce<Tuple2<K2, V2>>> f) {
        return (CC)((IterableOps)this.mapFactory().from(new View.FlatMap<Tuple2<K, V>, Tuple2<K2, V2>>(this, f)));
    }

    public static /* synthetic */ IterableOps concat$(MapOps $this, IterableOnce suffix) {
        return $this.concat(suffix);
    }

    @Override
    default public <V2> CC concat(IterableOnce<Tuple2<K, V2>> suffix) {
        IterableOnce<Object> iterableOnce;
        MapFactory<CC> mapFactory = this.mapFactory();
        if (suffix instanceof Iterable) {
            Iterable iterable = (Iterable)suffix;
            iterableOnce = new View.Concat(this, iterable);
        } else {
            iterableOnce = this.iterator().concat((Function0<Iterator> & Serializable)() -> suffix.iterator());
        }
        return (CC)((IterableOps)mapFactory.from(iterableOnce));
    }

    public static /* synthetic */ IterableOps $plus$plus$(MapOps $this, IterableOnce xs) {
        return $this.$plus$plus(xs);
    }

    @Override
    default public <V2> CC $plus$plus(IterableOnce<Tuple2<K, V2>> xs) {
        return this.concat(xs);
    }

    public static /* synthetic */ StringBuilder addString$(MapOps $this, StringBuilder sb, String start2, String sep, String end) {
        return $this.addString(sb, start2, sep, end);
    }

    @Override
    default public StringBuilder addString(StringBuilder sb, String start2, String sep, String end) {
        return this.iterator().map((Function1<Tuple2, String> & Serializable)(A x0$1) -> {
            if (x0$1 != null) {
                Object k = x0$1._1();
                Object v = x0$1._2();
                return new java.lang.StringBuilder(4).append(k).append(" -> ").append(v).toString();
            }
            throw new MatchError((Object)null);
        }).addString(sb, start2, sep, end);
    }

    public static /* synthetic */ IterableOps $plus$(MapOps $this, Tuple2 kv) {
        return $this.$plus(kv);
    }

    default public <V1> CC $plus(Tuple2<K, V1> kv) {
        return (CC)((IterableOps)this.mapFactory().from(new View.Appended(this, kv)));
    }

    public static /* synthetic */ IterableOps $plus$(MapOps $this, Tuple2 elem1, Tuple2 elem2, Seq elems) {
        return $this.$plus(elem1, elem2, elems);
    }

    default public <V1> CC $plus(Tuple2<K, V1> elem1, Tuple2<K, V1> elem2, Seq<Tuple2<K, V1>> elems) {
        return (CC)((IterableOps)this.mapFactory().from(new View.Concat(new View.Appended<Tuple2<K, V1>>(new View.Appended<Tuple2<K, V1>>(this, elem1), elem2), elems)));
    }

    public static /* synthetic */ Object $minus$minus$(MapOps $this, IterableOnce keys2) {
        return $this.$minus$minus(keys2);
    }

    default public C $minus$minus(IterableOnce<K> keys2) {
        LazyRef keysSet$lzy = new LazyRef();
        return this.fromSpecific(this.view().filterKeys((Function1<Object, Object> & Serializable)k -> BoxesRunTime.boxToBoolean(MapOps.$anonfun$$minus$minus$1(keysSet$lzy, keys2, k))));
    }

    public static /* synthetic */ IterableOps $plus$plus$colon$(MapOps $this, IterableOnce that) {
        return $this.$plus$plus$colon(that);
    }

    @Override
    default public <V1> CC $plus$plus$colon(IterableOnce<Tuple2<K, V1>> that) {
        Object thatIterable = that instanceof Iterable ? (Iterable)that : View$.MODULE$.from((IterableOnce)that);
        return (CC)((IterableOps)this.mapFactory().from(new View.Concat(thatIterable, this)));
    }

    private static /* synthetic */ scala.collection.immutable.Set keysSet$lzycompute$1(LazyRef keysSet$lzy$1, IterableOnce keys$1) {
        synchronized (keysSet$lzy$1) {
            scala.collection.immutable.Set set;
            if (keysSet$lzy$1.initialized()) {
                set = (scala.collection.immutable.Set)keysSet$lzy$1.value();
            } else {
                Set$ toFactory_factory = Set$.MODULE$;
                Object var3_2 = null;
                set = keysSet$lzy$1.initialize(keys$1.iterator().to(new IterableFactory.ToFactory(toFactory_factory)));
            }
            scala.collection.immutable.Set set2 = set;
            return set2;
        }
    }

    private static scala.collection.immutable.Set keysSet$1(LazyRef keysSet$lzy$1, IterableOnce keys$1) {
        if (keysSet$lzy$1.initialized()) {
            return (scala.collection.immutable.Set)keysSet$lzy$1.value();
        }
        return MapOps.keysSet$lzycompute$1(keysSet$lzy$1, keys$1);
    }

    public static /* synthetic */ boolean $anonfun$$minus$minus$1(LazyRef keysSet$lzy$1, IterableOnce keys$1, Object k) {
        return !MapOps.keysSet$1(keysSet$lzy$1, keys$1).contains(k);
    }

    public static void $init$(MapOps $this) {
    }

    public interface GenKeySet {
        default public Iterator<K> iterator() {
            return this.scala$collection$MapOps$GenKeySet$$$outer().keysIterator();
        }

        default public boolean contains(K key) {
            return this.scala$collection$MapOps$GenKeySet$$$outer().contains(key);
        }

        default public int size() {
            return this.scala$collection$MapOps$GenKeySet$$$outer().size();
        }

        default public int knownSize() {
            return this.scala$collection$MapOps$GenKeySet$$$outer().knownSize();
        }

        default public boolean isEmpty() {
            return this.scala$collection$MapOps$GenKeySet$$$outer().isEmpty();
        }

        public /* synthetic */ MapOps scala$collection$MapOps$GenKeySet$$$outer();

        public static void $init$(GenKeySet $this) {
        }
    }

    public class KeySet
    extends AbstractSet<K>
    implements GenKeySet,
    DefaultSerializable {
        @Override
        public Object writeReplace() {
            return DefaultSerializable.writeReplace$(this);
        }

        @Override
        public Iterator<K> iterator() {
            return ((GenKeySet)this).iterator();
        }

        @Override
        public boolean contains(K key) {
            return ((GenKeySet)this).contains(key);
        }

        @Override
        public int size() {
            return ((GenKeySet)this).size();
        }

        @Override
        public int knownSize() {
            return ((GenKeySet)this).knownSize();
        }

        @Override
        public boolean isEmpty() {
            return ((GenKeySet)this).isEmpty();
        }

        @Override
        public Set<K> diff(Set<K> that) {
            return (Set)this.fromSpecific((IterableOnce)this.view().filterNot(that));
        }

        public /* synthetic */ MapOps scala$collection$MapOps$KeySet$$$outer() {
            return MapOps.this;
        }

        @Override
        public /* synthetic */ MapOps scala$collection$MapOps$GenKeySet$$$outer() {
            return this.scala$collection$MapOps$KeySet$$$outer();
        }

        public KeySet() {
            if (MapOps.this == null) {
                throw null;
            }
        }
    }

    public static class WithFilter<K, V, IterableCC, CC extends IterableOps<?, Object, ?>>
    extends IterableOps.WithFilter<Tuple2<K, V>, IterableCC> {
        private static final long serialVersionUID = 3L;
        private final MapOps<K, V, CC, ?> self;
        private final Function1<Tuple2<K, V>, Object> p;

        @Override
        public <K2, V2> CC map(Function1<Tuple2<K, V>, Tuple2<K2, V2>> f) {
            return (CC)((IterableOps)this.self.mapFactory().from(new View.Map<Tuple2<K, V>, Tuple2<K2, V2>>(this.filtered(), f)));
        }

        @Override
        public <K2, V2> CC flatMap(Function1<Tuple2<K, V>, IterableOnce<Tuple2<K2, V2>>> f) {
            return (CC)((IterableOps)this.self.mapFactory().from(new View.FlatMap<Tuple2<K, V>, Tuple2<K2, V2>>(this.filtered(), f)));
        }

        public WithFilter<K, V, IterableCC, CC> withFilter(Function1<Tuple2<K, V>, Object> q) {
            return new WithFilter<K, V, IterableCC, CC>(this.self, (Function1<Tuple2, Object> & Serializable)kv -> BoxesRunTime.boxToBoolean(WithFilter.$anonfun$withFilter$1(this, q, kv)));
        }

        public static final /* synthetic */ boolean $anonfun$withFilter$1(WithFilter $this, Function1 q$1, Tuple2 kv) {
            return BoxesRunTime.unboxToBoolean($this.p.apply(kv)) && BoxesRunTime.unboxToBoolean(q$1.apply(kv));
        }

        public WithFilter(MapOps<K, V, CC, ?> self, Function1<Tuple2<K, V>, Object> p) {
            this.self = self;
            this.p = p;
            super(self, p);
        }
    }
}

