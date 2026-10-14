/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.io.Inet;
import akka.japi.Util$;
import akka.stream.Materializer;
import akka.stream.SystemMaterializer;
import akka.stream.SystemMaterializer$;
import akka.stream.TLSClosing;
import akka.stream.TLSProtocol;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Flow$;
import akka.stream.javadsl.Source;
import akka.stream.javadsl.Source$;
import akka.stream.javadsl.Tcp$;
import akka.stream.scaladsl.Tcp;
import akka.util.ByteString;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import akka.util.JavaDurationConverters$ScalaDurationOps$;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.compat.java8.FutureConverters$;
import scala.compat.java8.FutureConverters$FutureOps$;
import scala.compat.java8.OptionConverters$;
import scala.compat.java8.OptionConverters$RichOptionalGeneric$;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.Duration$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\rMu!\u0002\u00180\u0011\u00031d!\u0002\u001d0\u0011\u0003I\u0004BB(\u0002\t\u0003\u00199H\u0002\u0004\u0002d\u0005\u0011\u0011Q\r\u0005\n%\u000e\u0011\t\u0011)A\u0005\u0003OBqaT\u0002\u0005\u0002M\nY\u0007C\u0003o\u0007\u0011\u0005q\u000eC\u0004\u0002\u0000\r!\t!!!\t\u000f\u0005-5\u0001\"\u0001\u0002\u000e\u001a!\u0001-\u0001\u0001b\u0011!\u0011\u0016B!A!\u0002\u0013\u0011\u0007BB(\n\t\u0003\u0019$\u000eC\u0003o\u0013\u0011\u0005q\u000eC\u0003y\u0013\u0011\u0005q\u000eC\u0003z\u0013\u0011\u0005!\u0010\u0003\u0004z\u0013\u0011\u0005\u0011q\u0006\u0005\b\u0003\u000bJA\u0011AA$\r\u0019\u0011I%\u0001\u0001\u0003L!I!+\u0005B\u0001B\u0003%!Q\n\u0005\b\u001fF!\ta\rB)\u0011\u0015A\u0018\u0003\"\u0001p\u0011\u0015q\u0017\u0003\"\u0001p\u0011\u001d\u0019I(\u0001C!\u0007wBqa!\u001f\u0002\t\u0003\u001a)\tC\u0004\u0004\n\u0006!\taa#\t\u000f\r5\u0015\u0001\"\u0001\u0004\u0010\u001a!\u0001h\f\u0001H\u0011!Y%D!A!\u0002\u0013a\u0005\"B(\u001b\t\u0003\u0001\u0006\u0002\u0003*\u001b\u0011\u000b\u0007I\u0011B*\t\u000beSB\u0011\u0001.\t\reSB\u0011\u0001B\u0006\u0011\u0019I&\u0004\"\u0001\u0003:!9!q\b\u000e\u0005\u0002\t\u0005\u0003b\u0002B 5\u0011\u0005!q\r\u0005\b\u0005\u007fQB\u0011\u0001B<\u0011\u001d\u0011yH\u0007C\u0001\u0005\u0003CqAa \u001b\t\u0003\u0011I\fC\u0004\u0003Lj!\tA!4\t\u000f\t-'\u0004\"\u0001\u0003h\"911\u0005\u000e\u0005\u0002\r\u0015\u0002bBB\u00125\u0011\u00051Q\b\u0005\b\u0007\u0013RB\u0011AB&\u0011\u001d\u0019IE\u0007C\u0001\u0007'Bqa!\u001a\u001b\t\u0013\u00199\u0007C\u0004\u0004li!Ia!\u001c\u0002\u0007Q\u001b\u0007O\u0003\u00021c\u00059!.\u0019<bINd'B\u0001\u001a4\u0003\u0019\u0019HO]3b[*\tA'\u0001\u0003bW.\f7\u0001\u0001\t\u0003o\u0005i\u0011a\f\u0002\u0004)\u000e\u00048#B\u0001;\u0001\u000eE\u0004CA\u001e?\u001b\u0005a$\"A\u001f\u0002\u000bM\u001c\u0017\r\\1\n\u0005}b$AB!osJ+g\rE\u0002B\t\u001ak\u0011A\u0011\u0006\u0003\u0007N\nQ!Y2u_JL!!\u0012\"\u0003\u0017\u0015CH/\u001a8tS>t\u0017\n\u001a\t\u0003oi\u00192A\u0007\u001eI!\t\t\u0015*\u0003\u0002K\u0005\nIQ\t\u001f;f]NLwN\\\u0001\u0007gf\u001cH/Z7\u0011\u0005\u0005k\u0015B\u0001(C\u0005M)\u0005\u0010^3oI\u0016$\u0017i\u0019;peNK8\u000f^3n\u0003\u0019a\u0014N\\5u}Q\u0011a)\u0015\u0005\u0006\u0017r\u0001\r\u0001T\u0001\tI\u0016dWmZ1uKV\tA\u000b\u0005\u0002V16\taK\u0003\u0002Xc\u0005A1oY1mC\u0012\u001cH.\u0003\u00029-\u0006!!-\u001b8e)5Y\u0016qSAY\u0003w\u000by,!;\u0002tB)q\u0007\u00180\u0002T%\u0011Ql\f\u0002\u0007'>,(oY3\u0011\u0005}KaBA\u001c\u0001\u0005IIenY8nS:<7i\u001c8oK\u000e$\u0018n\u001c8\u0014\u0005%Q\u0004CA2i\u001d\t!wM\u0004\u0002fM6\t\u0011'\u0003\u0002Xc%\u0011aFV\u0005\u0003A&T!A\f,\u0015\u0005-l\u0007C\u00017\n\u001b\u0005\t\u0001\"\u0002*\f\u0001\u0004\u0011\u0017\u0001\u00047pG\u0006d\u0017\t\u001a3sKN\u001cX#\u00019\u0011\u0005E4X\"\u0001:\u000b\u0005M$\u0018a\u00018fi*\tQ/\u0001\u0003kCZ\f\u0017BA<s\u0005EIe.\u001a;T_\u000e\\W\r^!eIJ,7o]\u0001\u000ee\u0016lw\u000e^3BI\u0012\u0014Xm]:\u0002\u0015!\fg\u000e\u001a7f/&$\b.\u0006\u0002|}R)A0a\u0004\u0002&A\u0011QP \u0007\u0001\t\u0019yhB1\u0001\u0002\u0002\t\u0019Q*\u0019;\u0012\t\u0005\r\u0011\u0011\u0002\t\u0004w\u0005\u0015\u0011bAA\u0004y\t9aj\u001c;iS:<\u0007cA\u001e\u0002\f%\u0019\u0011Q\u0002\u001f\u0003\u0007\u0005s\u0017\u0010C\u0004\u0002\u00129\u0001\r!a\u0005\u0002\u000f!\fg\u000e\u001a7feBAq'!\u0006\u0002\u001a\u0005eA0C\u0002\u0002\u0018=\u0012AA\u00127poB!\u00111DA\u0011\u001b\t\tiBC\u0002\u0002 M\nA!\u001e;jY&!\u00111EA\u000f\u0005)\u0011\u0015\u0010^3TiJLgn\u001a\u0005\b\u0003Oq\u0001\u0019AA\u0015\u00039\u0019\u0018p\u001d;f[B\u0013xN^5eKJ\u00042!QA\u0016\u0013\r\tiC\u0011\u0002\u001b\u00072\f7o]5d\u0003\u000e$xN]*zgR,W\u000e\u0015:pm&$WM]\u000b\u0005\u0003c\t)\u0004\u0006\u0004\u00024\u0005]\u00121\b\t\u0004{\u0006UBAB@\u0010\u0005\u0004\t\t\u0001C\u0004\u0002\u0012=\u0001\r!!\u000f\u0011\u0013]\n)\"!\u0007\u0002\u001a\u0005M\u0002bBA\u001f\u001f\u0001\u0007\u0011qH\u0001\r[\u0006$XM]5bY&TXM\u001d\t\u0004K\u0006\u0005\u0013bAA\"c\taQ*\u0019;fe&\fG.\u001b>fe\u0006!a\r\\8x+\t\tI\u0005E\u00058\u0003+\tI\"!\u0007\u0002LA!\u0011QJA(\u001b\u0005\u0019\u0014bAA)g\t9aj\u001c;Vg\u0016$\u0007CBA+\u0003;\n\t'\u0004\u0002\u0002X)!\u0011\u0011LA.\u0003)\u0019wN\\2veJ,g\u000e\u001e\u0006\u0004\u0003?!\u0018\u0002BA0\u0003/\u0012qbQ8na2,G/[8o'R\fw-\u001a\t\u0003?\u000e\u0011QbU3sm\u0016\u0014()\u001b8eS:<7CA\u0002;!\r\u0019\u0017\u0011N\u0005\u0004\u0003GJG\u0003BA7\u0003_\u0002\"\u0001\\\u0002\t\rI+\u0001\u0019AA4Q\r)\u00111\u000f\t\u0005\u0003k\nY(\u0004\u0002\u0002x)\u0019\u0011\u0011P\u001a\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002~\u0005]$aC%oi\u0016\u0014h.\u00197Ba&\fa!\u001e8cS:$GCAAB!\u0019\t)&!\u0018\u0002\u0006B\u00191(a\"\n\u0007\u0005%EH\u0001\u0003V]&$\u0018aC<iK:,fNY8v]\u0012$\"!a$\u0011\r\u0005U\u0013QLAI!\u0011\ti%a%\n\u0007\u0005U5G\u0001\u0003E_:,\u0007bBAM=\u0001\u0007\u00111T\u0001\nS:$XM\u001d4bG\u0016\u0004B!!(\u0002,:!\u0011qTAT!\r\t\t\u000bP\u0007\u0003\u0003GS1!!*6\u0003\u0019a$o\\8u}%\u0019\u0011\u0011\u0016\u001f\u0002\rA\u0013X\rZ3g\u0013\u0011\ti+a,\u0003\rM#(/\u001b8h\u0015\r\tI\u000b\u0010\u0005\b\u0003gs\u0002\u0019AA[\u0003\u0011\u0001xN\u001d;\u0011\u0007m\n9,C\u0002\u0002:r\u00121!\u00138u\u0011\u001d\tiL\ba\u0001\u0003k\u000bqAY1dW2|w\rC\u0004\u0002Bz\u0001\r!a1\u0002\u000f=\u0004H/[8ogB1\u0011QYAf\u0003\u001fl!!a2\u000b\u0007\u0005%G/\u0001\u0003mC:<\u0017\u0002BAg\u0003\u000f\u0014\u0001\"\u0013;fe\u0006\u0014G.\u001a\t\u0005\u0003#\f\u0019O\u0004\u0003\u0002T\u0006ug\u0002BAk\u00033tA!!)\u0002X&\tA'C\u0002\u0002\\N\n!![8\n\t\u0005}\u0017\u0011]\u0001\u0005\u0013:,GOC\u0002\u0002\\NJA!!:\u0002h\na1k\\2lKR|\u0005\u000f^5p]*!\u0011q\\Aq\u0011\u001d\tYO\ba\u0001\u0003[\f\u0011\u0002[1mM\u000ecwn]3\u0011\u0007m\ny/C\u0002\u0002rr\u0012qAQ8pY\u0016\fg\u000eC\u0004\u0002vz\u0001\r!a>\u0002\u0017%$G.\u001a+j[\u0016|W\u000f\u001e\t\u0007\u0003s\fY0a@\u000e\u0005\u0005m\u0013\u0002BA\u007f\u00037\u0012\u0001b\u00149uS>t\u0017\r\u001c\t\u0005\u0005\u0003\u00119!\u0004\u0002\u0003\u0004)\u0019!Q\u0001;\u0002\tQLW.Z\u0005\u0005\u0005\u0013\u0011\u0019A\u0001\u0005EkJ\fG/[8o)5Y&Q\u0002B\b\u0005#\u0011\u0019B!\u0006\u0003\u0018!9\u0011\u0011T\u0010A\u0002\u0005m\u0005bBAZ?\u0001\u0007\u0011Q\u0017\u0005\b\u0003{{\u0002\u0019AA[\u0011\u001d\t\tm\ba\u0001\u0003\u0007Dq!a; \u0001\u0004\ti\u000fC\u0004\u0002v~\u0001\rA!\u0007\u0011\t\tm!1E\u0007\u0003\u0005;QAAa\b\u0003\"\u0005AA-\u001e:bi&|gNC\u0002\u0002ZqJAA!\u0003\u0003\u001e!ZqDa\n\u0003.\t=\"1\u0007B\u001b!\rY$\u0011F\u0005\u0004\u0005Wa$A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017E\u0001B\u0019\u0003m*6/\u001a\u0011cS:$\u0007\u0005\u001e5bi\u0002\"\u0018m[3tA\u0005\u0004#.\u0019<b]QLW.\u001a\u0018EkJ\fG/[8oAA\f'/Y7fi\u0016\u0014\b%\u001b8ti\u0016\fGML\u0001\u0006g&t7-Z\u0011\u0003\u0005o\tQA\r\u00187]A\"Ra\u0017B\u001e\u0005{Aq!!'!\u0001\u0004\tY\nC\u0004\u00024\u0002\u0002\r!!.\u0002%=,HoZ8j]\u001e\u001cuN\u001c8fGRLwN\u001c\u000b\u000f\u0005\u0007\u00129F!\u0017\u0003^\t}#\u0011\rB3!%9\u0014QCA\r\u00033\u0011)\u0005\u0005\u0004\u0002V\u0005u#q\t\t\u0003?F\u0011!cT;uO>LgnZ\"p]:,7\r^5p]N\u0011\u0011C\u000f\t\u0004G\n=\u0013b\u0001B%SR!!1\u000bB+!\ta\u0017\u0003\u0003\u0004S'\u0001\u0007!Q\n\u0005\u0006q\u0006\u0002\r\u0001\u001d\u0005\u0007]\u0006\u0002\rAa\u0017\u0011\u000b\u0005e\u00181 9\t\u000f\u0005\u0005\u0017\u00051\u0001\u0002D\"9\u00111^\u0011A\u0002\u00055\bb\u0002B2C\u0001\u0007\u0011q_\u0001\u000fG>tg.Z2u)&lWm\\;u\u0011\u001d\t)0\ta\u0001\u0003o$bBa\u0011\u0003j\t-$Q\u000eB8\u0005c\u0012\u0019\bC\u0003yE\u0001\u0007\u0001\u000f\u0003\u0004oE\u0001\u0007!1\f\u0005\b\u0003\u0003\u0014\u0003\u0019AAb\u0011\u001d\tYO\ta\u0001\u0003[DqAa\u0019#\u0001\u0004\u0011I\u0002C\u0004\u0002v\n\u0002\rA!\u0007)\u0017\t\u00129C!\f\u00030\tM\"Q\u0007\u000b\u0007\u0005\u0007\u0012IH! \t\u000f\tm4\u00051\u0001\u0002\u001c\u0006!\u0001n\\:u\u0011\u001d\t\u0019l\ta\u0001\u0003k\u000bQc\\;uO>Lgn\u001a+mg\u000e{gN\\3di&|g\u000e\u0006\u0006\u0003D\t\r%Q\u0011BD\u0005;CqAa\u001f%\u0001\u0004\tY\nC\u0004\u00024\u0012\u0002\r!!.\t\u000f\t%E\u00051\u0001\u0003\f\u0006Q1o\u001d7D_:$X\r\u001f;\u0011\t\t5%\u0011T\u0007\u0003\u0005\u001fSAA!%\u0003\u0014\u0006\u00191o\u001d7\u000b\u0007M\u0014)J\u0003\u0002\u0003\u0018\u0006)!.\u0019<bq&!!1\u0014BH\u0005)\u00196\u000bT\"p]R,\u0007\u0010\u001e\u0005\b\u0005?#\u0003\u0019\u0001BQ\u0003MqWmZ8uS\u0006$XMT3x'\u0016\u001c8/[8o!\u0011\u0011\u0019K!,\u000f\t\t\u0015&\u0011\u0016\b\u0005\u0003+\u00149+\u0003\u00023g%\u0019!1V\u0019\u0002\u0017Qc5\u000b\u0015:pi>\u001cw\u000e\\\u0005\u0005\u0005_\u0013\tLA\nOK\u001e|G/[1uK:+woU3tg&|gNC\u0002\u0003,FB3\u0002\nB\u0014\u0005[\u0011)La\r\u00036\u0005\u0012!qW\u0001r+N,\u0007e\\;uO>LgnZ\"p]:,7\r^5p]^KG\u000f\u001b+mg\u0002\"\b.\u0019;!i\u0006\\Wm\u001d\u0011bAM\u001bF*\u00128hS:,\u0007EZ1di>\u0014\u0018\u0010I5ogR,\u0017\r\u001a\u0018!'\u0016$X\u000f\u001d\u0011uQ\u0016\u00043k\u0015'F]\u001eLg.\u001a\u0011xSRD\u0007E\\3fI\u0016$\u0007\u0005]1sC6,G/\u001a:t]Q\u0001\"1\tB^\u0005{\u0013yL!1\u0003D\n\u0015'q\u0019\u0005\u0006q\u0016\u0002\r\u0001\u001d\u0005\b\u0005\u0013+\u0003\u0019\u0001BF\u0011\u001d\u0011y*\na\u0001\u0005CCaA\\\u0013A\u0002\tm\u0003bBAaK\u0001\u0007\u00111\u0019\u0005\b\u0005G*\u0003\u0019\u0001B\r\u0011\u001d\t)0\na\u0001\u00053A3\"\nB\u0014\u0005[\u0011)La\r\u00036\u0005Ir.\u001e;h_&twmQ8o]\u0016\u001cG/[8o/&$\b\u000e\u00167t)\u0019\u0011\u0019Ea4\u0003R\")\u0001P\na\u0001a\"9!1\u001b\u0014A\u0002\tU\u0017aD2sK\u0006$XmU*M\u000b:<\u0017N\\3\u0011\r\t]'Q\u001cBq\u001b\t\u0011IN\u0003\u0003\u0003\\\u0006m\u0013\u0001\u00034v]\u000e$\u0018n\u001c8\n\t\t}'\u0011\u001c\u0002\t'V\u0004\b\u000f\\5feB!!Q\u0012Br\u0013\u0011\u0011)Oa$\u0003\u0013M\u001bF*\u00128hS:,GC\u0005B\"\u0005S\u0014YO!<\u0003p\nE(1\u001fB{\u00073AQ\u0001_\u0014A\u0002ADqAa5(\u0001\u0004\u0011)\u000e\u0003\u0004oO\u0001\u0007!1\f\u0005\b\u0003\u0003<\u0003\u0019AAb\u0011\u001d\u0011\u0019g\na\u0001\u0003oDq!!>(\u0001\u0004\t9\u0010C\u0004\u0003x\u001e\u0002\rA!?\u0002\u001bY,'/\u001b4z'\u0016\u001c8/[8o!!\u00119Na?\u0003\u0000\u000e\u0015\u0011\u0002\u0002B\u007f\u00053\u0014\u0001BR;oGRLwN\u001c\t\u0005\u0005\u001b\u001b\t!\u0003\u0003\u0004\u0004\t=%AC*T\u0019N+7o]5p]B1\u0011\u0011`A~\u0007\u000f\u0001Ba!\u0003\u0004\u00149!11BB\b\u001d\u0011\t\tk!\u0004\n\u0003uJ1a!\u0005=\u0003\u001d\u0001\u0018mY6bO\u0016LAa!\u0006\u0004\u0018\tIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0004\u0007#a\u0004bBB\u000eO\u0001\u00071QD\u0001\bG2|7/\u001b8h!\r)7qD\u0005\u0004\u0007C\t$A\u0003+M'\u000ecwn]5oO\u00069!-\u001b8e)2\u001cH#E.\u0004(\r%21FB\u0017\u0007_\u0019\tda\r\u00046!9\u0011\u0011\u0014\u0015A\u0002\u0005m\u0005bBAZQ\u0001\u0007\u0011Q\u0017\u0005\b\u0005\u0013C\u0003\u0019\u0001BF\u0011\u001d\u0011y\n\u000ba\u0001\u0005CCq!!0)\u0001\u0004\t)\fC\u0004\u0002B\"\u0002\r!a1\t\u000f\u0005-\b\u00061\u0001\u0002n\"9\u0011Q\u001f\u0015A\u0002\te\u0001f\u0003\u0015\u0003(\t52\u0011\bB\u001a\u0005k\t#aa\u000f\u0002GV\u001bX\r\t2j]\u0012<\u0016\u000e\u001e5UYN\u0004C\u000f[1uAQ\f7.Z:!C\u0002\u001a6\u000bT#oO&tW\r\t4bGR|'/\u001f\u0011j]N$X-\u00193/AM+G/\u001e9!i\",\u0007eU*M\u000b:<\u0017N\\3!o&$\b\u000e\t8fK\u0012,G\r\t9be\u0006lW\r^3sg:\"\u0012bWB \u0007\u0003\u001a\u0019e!\u0012\t\u000f\u0005e\u0015\u00061\u0001\u0002\u001c\"9\u00111W\u0015A\u0002\u0005U\u0006b\u0002BES\u0001\u0007!1\u0012\u0005\b\u0005?K\u0003\u0019\u0001BQQ-I#q\u0005B\u0017\u0007s\u0011\u0019D!\u000e\u0002\u0017\tLg\u000eZ,ji\"$Fn\u001d\u000b\b7\u000e53qJB)\u0011\u001d\tIJ\u000ba\u0001\u00037Cq!a-+\u0001\u0004\t)\fC\u0004\u0003T*\u0002\rA!6\u0015#m\u001b)fa\u0016\u0004Z\rm3QLB0\u0007C\u001a\u0019\u0007C\u0004\u0002\u001a.\u0002\r!a'\t\u000f\u0005M6\u00061\u0001\u00026\"9!1[\u0016A\u0002\tU\u0007bBA_W\u0001\u0007\u0011Q\u0017\u0005\b\u0003\u0003\\\u0003\u0019AAb\u0011\u001d\t)p\u000ba\u0001\u0003oDqAa>,\u0001\u0004\u0011I\u0010C\u0004\u0004\u001c-\u0002\ra!\b\u0002/=\u0004H/[8oC2$UO]1uS>tGk\\*dC2\fG\u0003\u0002B\r\u0007SBqAa\b-\u0001\u0004\t90\u0001\fekJ\fG/[8o)>T\u0015M^1PaRLwN\\1m)\u0011\t9pa\u001c\t\u000f\t}Q\u00061\u0001\u0003\u001aA\u0019\u0011ia\u001d\n\u0007\rU$IA\nFqR,gn]5p]&#\u0007K]8wS\u0012,'\u000fF\u00017\u0003\r9W\r\u001e\u000b\u0004\r\u000eu\u0004BB&\u0017\u0001\u0004\u0019y\bE\u0002B\u0007\u0003K1aa!C\u0005-\t5\r^8s'f\u001cH/Z7\u0015\u0007\u0019\u001b9\t\u0003\u0004L/\u0001\u0007\u0011\u0011F\u0001\u0007Y>|7.\u001e9\u0016\u0003Y\nqb\u0019:fCR,W\t\u001f;f]NLwN\u001c\u000b\u0004\r\u000eE\u0005\"B&\u001a\u0001\u0004a\u0005")
public class Tcp
implements Extension {
    private akka.stream.scaladsl.Tcp delegate;
    private ExtendedActorSystem system;
    private volatile boolean bitmap$0;

    public static Tcp createExtension(ExtendedActorSystem system) {
        return Tcp$.MODULE$.createExtension(system);
    }

    public static Tcp$ lookup() {
        return Tcp$.MODULE$.lookup();
    }

    public static Tcp get(ClassicActorSystemProvider system) {
        return Tcp$.MODULE$.get(system);
    }

    public static Tcp get(ActorSystem system) {
        return Tcp$.MODULE$.get(system);
    }

    public static Extension apply(ClassicActorSystemProvider system) {
        return Tcp$.MODULE$.apply(system);
    }

    public static Extension apply(ActorSystem system) {
        return Tcp$.MODULE$.apply(system);
    }

    private akka.stream.scaladsl.Tcp delegate$lzycompute() {
        Tcp tcp = this;
        synchronized (tcp) {
            if (!this.bitmap$0) {
                this.delegate = (akka.stream.scaladsl.Tcp)akka.stream.scaladsl.Tcp$.MODULE$.apply(this.system);
                this.bitmap$0 = true;
            }
        }
        this.system = null;
        return this.delegate;
    }

    private akka.stream.scaladsl.Tcp delegate() {
        if (!this.bitmap$0) {
            return this.delegate$lzycompute();
        }
        return this.delegate;
    }

    public Source<IncomingConnection, CompletionStage<ServerBinding>> bind(String string, int port, int backlog, Iterable<Inet.SocketOption> options, boolean halfClose, Optional<java.time.Duration> idleTimeout) {
        return Source$.MODULE$.fromGraph(((akka.stream.scaladsl.Source)this.delegate().bind(string, port, backlog, Util$.MODULE$.immutableSeq(options), halfClose, this.optionalDurationToScala(idleTimeout)).map((Function1<Tcp.IncomingConnection, IncomingConnection> & Serializable)x$1 -> new IncomingConnection((Tcp.IncomingConnection)x$1))).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$2 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$2.map((Function1<Tcp.ServerBinding, ServerBinding> & Serializable)x$3 -> new ServerBinding((Tcp.ServerBinding)x$3), ExecutionContexts$.MODULE$.parasitic())))));
    }

    public Source<IncomingConnection, CompletionStage<ServerBinding>> bind(String string, int port, int backlog, Iterable<Inet.SocketOption> options, boolean halfClose, Duration idleTimeout) {
        return this.bind(string, port, backlog, options, halfClose, this.durationToJavaOptional(idleTimeout));
    }

    public Source<IncomingConnection, CompletionStage<ServerBinding>> bind(String string, int port) {
        return Source$.MODULE$.fromGraph(((akka.stream.scaladsl.Source)this.delegate().bind(string, port, this.delegate().bind$default$3(), this.delegate().bind$default$4(), this.delegate().bind$default$5(), this.delegate().bind$default$6()).map((Function1<Tcp.IncomingConnection, IncomingConnection> & Serializable)x$4 -> new IncomingConnection((Tcp.IncomingConnection)x$4))).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$5 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$5.map((Function1<Tcp.ServerBinding, ServerBinding> & Serializable)x$6 -> new ServerBinding((Tcp.ServerBinding)x$6), ExecutionContexts$.MODULE$.parasitic())))));
    }

    public Flow<ByteString, ByteString, CompletionStage<OutgoingConnection>> outgoingConnection(InetSocketAddress remoteAddress, Optional<InetSocketAddress> localAddress, Iterable<Inet.SocketOption> options, boolean halfClose, Optional<java.time.Duration> connectTimeout, Optional<java.time.Duration> idleTimeout) {
        return Flow$.MODULE$.fromGraph(this.delegate().outgoingConnection(remoteAddress, OptionConverters$RichOptionalGeneric$.MODULE$.asScala$extension(OptionConverters$.MODULE$.RichOptionalGeneric(localAddress)), Util$.MODULE$.immutableSeq(options), halfClose, this.optionalDurationToScala(connectTimeout), this.optionalDurationToScala(idleTimeout)).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$7 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$7.map((Function1<Tcp.OutgoingConnection, OutgoingConnection> & Serializable)x$8 -> new OutgoingConnection((Tcp.OutgoingConnection)x$8), ExecutionContexts$.MODULE$.parasitic())))));
    }

    public Flow<ByteString, ByteString, CompletionStage<OutgoingConnection>> outgoingConnection(InetSocketAddress remoteAddress, Optional<InetSocketAddress> localAddress, Iterable<Inet.SocketOption> options, boolean halfClose, Duration connectTimeout, Duration idleTimeout) {
        return this.outgoingConnection(remoteAddress, localAddress, options, halfClose, this.durationToJavaOptional(connectTimeout), this.durationToJavaOptional(idleTimeout));
    }

    public Flow<ByteString, ByteString, CompletionStage<OutgoingConnection>> outgoingConnection(String host2, int port) {
        return Flow$.MODULE$.fromGraph(this.delegate().outgoingConnection(new InetSocketAddress(host2, port), this.delegate().outgoingConnection$default$2(), this.delegate().outgoingConnection$default$3(), this.delegate().outgoingConnection$default$4(), this.delegate().outgoingConnection$default$5(), this.delegate().outgoingConnection$default$6()).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$9 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$9.map((Function1<Tcp.OutgoingConnection, OutgoingConnection> & Serializable)x$10 -> new OutgoingConnection((Tcp.OutgoingConnection)x$10), ExecutionContexts$.MODULE$.parasitic())))));
    }

    public Flow<ByteString, ByteString, CompletionStage<OutgoingConnection>> outgoingTlsConnection(String host2, int port, SSLContext sslContext, TLSProtocol.NegotiateNewSession negotiateNewSession) {
        return Flow$.MODULE$.fromGraph(this.delegate().outgoingTlsConnection(host2, port, sslContext, negotiateNewSession).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$11 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$11.map((Function1<Tcp.OutgoingConnection, OutgoingConnection> & Serializable)x$12 -> new OutgoingConnection((Tcp.OutgoingConnection)x$12), ExecutionContexts$.MODULE$.parasitic())))));
    }

    public Flow<ByteString, ByteString, CompletionStage<OutgoingConnection>> outgoingTlsConnection(InetSocketAddress remoteAddress, SSLContext sslContext, TLSProtocol.NegotiateNewSession negotiateNewSession, Optional<InetSocketAddress> localAddress, Iterable<Inet.SocketOption> options, Duration connectTimeout, Duration idleTimeout) {
        return Flow$.MODULE$.fromGraph(this.delegate().outgoingTlsConnection(remoteAddress, sslContext, negotiateNewSession, OptionConverters$RichOptionalGeneric$.MODULE$.asScala$extension(OptionConverters$.MODULE$.RichOptionalGeneric(localAddress)), Util$.MODULE$.immutableSeq(options), connectTimeout, idleTimeout).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$13 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$13.map((Function1<Tcp.OutgoingConnection, OutgoingConnection> & Serializable)x$14 -> new OutgoingConnection((Tcp.OutgoingConnection)x$14), ExecutionContexts$.MODULE$.parasitic())))));
    }

    public Flow<ByteString, ByteString, CompletionStage<OutgoingConnection>> outgoingConnectionWithTls(InetSocketAddress remoteAddress, Supplier<SSLEngine> createSSLEngine) {
        return Flow$.MODULE$.fromGraph(this.delegate().outgoingConnectionWithTls(remoteAddress, (Function0<SSLEngine> & Serializable)() -> (SSLEngine)createSSLEngine.get()).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$15 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$15.map((Function1<Tcp.OutgoingConnection, OutgoingConnection> & Serializable)x$16 -> new OutgoingConnection((Tcp.OutgoingConnection)x$16), ExecutionContexts$.MODULE$.parasitic())))));
    }

    public Flow<ByteString, ByteString, CompletionStage<OutgoingConnection>> outgoingConnectionWithTls(InetSocketAddress remoteAddress, Supplier<SSLEngine> createSSLEngine, Optional<InetSocketAddress> localAddress, Iterable<Inet.SocketOption> options, Optional<java.time.Duration> connectTimeout, Optional<java.time.Duration> idleTimeout, Function<SSLSession, Optional<Throwable>> verifySession, TLSClosing closing2) {
        return Flow$.MODULE$.fromGraph(this.delegate().outgoingConnectionWithTls(remoteAddress, (Function0<SSLEngine> & Serializable)() -> (SSLEngine)createSSLEngine.get(), OptionConverters$RichOptionalGeneric$.MODULE$.asScala$extension(OptionConverters$.MODULE$.RichOptionalGeneric(localAddress)), Util$.MODULE$.immutableSeq(options), this.optionalDurationToScala(connectTimeout), this.optionalDurationToScala(idleTimeout), (Function1<SSLSession, Try<BoxedUnit>>)(Function1<SSLSession, Try> & Serializable)session -> {
            Option option = OptionConverters$RichOptionalGeneric$.MODULE$.asScala$extension(OptionConverters$.MODULE$.RichOptionalGeneric((Optional)verifySession.apply((SSLSession)session)));
            if (None$.MODULE$.equals(option)) {
                return new Success<BoxedUnit>(BoxedUnit.UNIT);
            }
            if (option instanceof Some) {
                Some some = (Some)option;
                Throwable t2 = (Throwable)some.value();
                return new Failure(t2);
            }
            throw new MatchError(option);
        }, closing2).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$17 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$17.map((Function1<Tcp.OutgoingConnection, OutgoingConnection> & Serializable)x$18 -> new OutgoingConnection((Tcp.OutgoingConnection)x$18), ExecutionContexts$.MODULE$.parasitic())))));
    }

    public Source<IncomingConnection, CompletionStage<ServerBinding>> bindTls(String string, int port, SSLContext sslContext, TLSProtocol.NegotiateNewSession negotiateNewSession, int backlog, Iterable<Inet.SocketOption> options, boolean halfClose, Duration idleTimeout) {
        return Source$.MODULE$.fromGraph(((akka.stream.scaladsl.Source)this.delegate().bindTls(string, port, sslContext, negotiateNewSession, backlog, Util$.MODULE$.immutableSeq(options), idleTimeout).map((Function1<Tcp.IncomingConnection, IncomingConnection> & Serializable)x$19 -> new IncomingConnection((Tcp.IncomingConnection)x$19))).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$20 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$20.map((Function1<Tcp.ServerBinding, ServerBinding> & Serializable)x$21 -> new ServerBinding((Tcp.ServerBinding)x$21), ExecutionContexts$.MODULE$.parasitic())))));
    }

    public Source<IncomingConnection, CompletionStage<ServerBinding>> bindTls(String string, int port, SSLContext sslContext, TLSProtocol.NegotiateNewSession negotiateNewSession) {
        return Source$.MODULE$.fromGraph(((akka.stream.scaladsl.Source)this.delegate().bindTls(string, port, sslContext, negotiateNewSession, this.delegate().bindTls$default$5(), this.delegate().bindTls$default$6(), this.delegate().bindTls$default$7()).map((Function1<Tcp.IncomingConnection, IncomingConnection> & Serializable)x$22 -> new IncomingConnection((Tcp.IncomingConnection)x$22))).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$23 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$23.map((Function1<Tcp.ServerBinding, ServerBinding> & Serializable)x$24 -> new ServerBinding((Tcp.ServerBinding)x$24), ExecutionContexts$.MODULE$.parasitic())))));
    }

    public Source<IncomingConnection, CompletionStage<ServerBinding>> bindWithTls(String string, int port, Supplier<SSLEngine> createSSLEngine) {
        return Source$.MODULE$.fromGraph(((akka.stream.scaladsl.Source)this.delegate().bindWithTls(string, port, (Function0<SSLEngine> & Serializable)() -> (SSLEngine)createSSLEngine.get()).map((Function1<Tcp.IncomingConnection, IncomingConnection> & Serializable)x$25 -> new IncomingConnection((Tcp.IncomingConnection)x$25))).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$26 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$26.map((Function1<Tcp.ServerBinding, ServerBinding> & Serializable)x$27 -> new ServerBinding((Tcp.ServerBinding)x$27), ExecutionContexts$.MODULE$.parasitic())))));
    }

    public Source<IncomingConnection, CompletionStage<ServerBinding>> bindWithTls(String string, int port, Supplier<SSLEngine> createSSLEngine, int backlog, Iterable<Inet.SocketOption> options, Optional<java.time.Duration> idleTimeout, Function<SSLSession, Optional<Throwable>> verifySession, TLSClosing closing2) {
        return Source$.MODULE$.fromGraph(((akka.stream.scaladsl.Source)this.delegate().bindWithTls(string, port, (Function0<SSLEngine> & Serializable)() -> (SSLEngine)createSSLEngine.get(), backlog, Util$.MODULE$.immutableSeq(options), this.optionalDurationToScala(idleTimeout), (Function1<SSLSession, Try<BoxedUnit>>)(Function1<SSLSession, Try> & Serializable)session -> {
            Option option = OptionConverters$RichOptionalGeneric$.MODULE$.asScala$extension(OptionConverters$.MODULE$.RichOptionalGeneric((Optional)verifySession.apply((SSLSession)session)));
            if (None$.MODULE$.equals(option)) {
                return new Success<BoxedUnit>(BoxedUnit.UNIT);
            }
            if (option instanceof Some) {
                Some some = (Some)option;
                Throwable t2 = (Throwable)some.value();
                return new Failure(t2);
            }
            throw new MatchError(option);
        }, closing2).map((Function1<Tcp.IncomingConnection, IncomingConnection> & Serializable)x$28 -> new IncomingConnection((Tcp.IncomingConnection)x$28))).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$29 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$29.map((Function1<Tcp.ServerBinding, ServerBinding> & Serializable)x$30 -> new ServerBinding((Tcp.ServerBinding)x$30), ExecutionContexts$.MODULE$.parasitic())))));
    }

    private Duration optionalDurationToScala(Optional<java.time.Duration> duration) {
        if (duration.isPresent()) {
            return JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(duration.get()));
        }
        return Duration$.MODULE$.Inf();
    }

    private Optional<java.time.Duration> durationToJavaOptional(Duration duration) {
        if (duration.isFinite()) {
            return Optional.ofNullable(JavaDurationConverters$ScalaDurationOps$.MODULE$.asJava$extension(JavaDurationConverters$.MODULE$.ScalaDurationOps(duration)));
        }
        return Optional.empty();
    }

    public Tcp(ExtendedActorSystem system) {
        this.system = system;
    }

    public static class IncomingConnection {
        private final Tcp.IncomingConnection delegate;

        public InetSocketAddress localAddress() {
            return this.delegate.localAddress();
        }

        public InetSocketAddress remoteAddress() {
            return this.delegate.remoteAddress();
        }

        public <Mat> Mat handleWith(Flow<ByteString, ByteString, Mat> handler, ClassicActorSystemProvider systemProvider) {
            return this.delegate.handleWith(handler.asScala(), ((SystemMaterializer)SystemMaterializer$.MODULE$.apply(systemProvider.classicSystem())).materializer());
        }

        public <Mat> Mat handleWith(Flow<ByteString, ByteString, Mat> handler, Materializer materializer) {
            return this.delegate.handleWith(handler.asScala(), materializer);
        }

        public Flow<ByteString, ByteString, NotUsed> flow() {
            return new Flow<ByteString, ByteString, NotUsed>(this.delegate.flow());
        }

        public IncomingConnection(Tcp.IncomingConnection delegate) {
            this.delegate = delegate;
        }
    }

    public static class OutgoingConnection {
        private final Tcp.OutgoingConnection delegate;

        public InetSocketAddress remoteAddress() {
            return this.delegate.remoteAddress();
        }

        public InetSocketAddress localAddress() {
            return this.delegate.localAddress();
        }

        public OutgoingConnection(Tcp.OutgoingConnection delegate) {
            this.delegate = delegate;
        }
    }

    public static final class ServerBinding {
        private final Tcp.ServerBinding delegate;

        public InetSocketAddress localAddress() {
            return this.delegate.localAddress();
        }

        public CompletionStage<BoxedUnit> unbind() {
            return FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(this.delegate.unbind()));
        }

        public CompletionStage<Done> whenUnbound() {
            return FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(this.delegate.whenUnbound()));
        }

        @InternalApi
        public ServerBinding(Tcp.ServerBinding delegate) {
            this.delegate = delegate;
        }
    }
}

