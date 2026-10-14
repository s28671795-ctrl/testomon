/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.ConfigurationException;
import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorRefFactory;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem$;
import akka.actor.BootstrapSetup;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.Extension;
import akka.actor.ExtensionId;
import akka.actor.ProviderSelection;
import akka.actor.ProviderSelection$;
import akka.actor.Scheduler;
import akka.actor.Terminated;
import akka.actor.setup.ActorSystemSetup;
import akka.actor.setup.ActorSystemSetup$;
import akka.dispatch.Dispatchers;
import akka.dispatch.Mailboxes;
import akka.event.EventStream;
import akka.event.LoggingAdapter;
import akka.japi.Util$;
import akka.util.Helpers$;
import akka.util.Helpers$ConfigOps$;
import akka.util.Timeout;
import akka.util.ccompat.package$JavaConverters$;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.Serializable;
import java.util.concurrent.CompletionStage;
import scala.Function0;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.Iterable;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.Duration$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0011mw\u0001CA\u0014\u0003SA\t!a\r\u0007\u0011\u0005]\u0012\u0011\u0006E\u0001\u0003sAq!a\u0012\u0002\t\u0003\tI\u0005C\u0005\u0002L\u0005\u0011\r\u0011\"\u0001\u0002N!A\u0011QM\u0001!\u0002\u0013\ty\u0005C\u0004\u0002h\u0005!\t!!\u001b\t\u000f\u0005\u001d\u0014\u0001\"\u0001\u0005\"!9\u0011qM\u0001\u0005\u0002\u0011\u0015\u0002bBA4\u0003\u0011\u0005AQ\u0006\u0005\b\u0003O\nA\u0011\u0001C\u001e\u0011\u001d\t9'\u0001C\u0001\t\u0003Bq!a\u001a\u0002\t\u0003!I\u0005C\u0004\u0005\\\u0005!\t!!\u001b\t\u000f\u0011m\u0013\u0001\"\u0001\u0005^!9A1L\u0001\u0005\u0002\u0011\u0005\u0004b\u0002C.\u0003\u0011\u0005Aq\r\u0005\b\t7\nA\u0011\u0001C7\u0011\u001d!Y&\u0001C\u0001\tgBq\u0001b\u0017\u0002\t\u0003!Y\bC\u0005\u0005\f\u0006\t\n\u0011\"\u0001\u0005\u000e\"IA1U\u0001\u0012\u0002\u0013\u0005AQ\u0015\u0005\n\tS\u000b\u0011\u0013!C\u0001\tW;\u0011\u0002b,\u0002\u0011\u0003\ti\u0003\"-\u0007\u0013\u0005\u0015\u0015\u0001#\u0001\u0002.\u0011M\u0006bBA$/\u0011\u0005AQ\u0017\u0005\n\to;B\u0011AA\u0017\ts3a!!\"\u0002\u0001\u0005\u001d\u0005BCAE5\t\u0005\t\u0015!\u0003\u0002\f\"Q\u00111\u0014\u000e\u0003\u0002\u0003\u0006I!!(\t\u0015\u0005m$D!b\u0001\n\u000b\ti\u0005\u0003\u0006\u00022j\u0011\t\u0011)A\u0007\u0003\u001fB!\"a-\u001b\u0005\u000b\u0007I\u0011AA[\u0011)\t\tM\u0007B\u0001B\u0003%\u0011q\u0017\u0005\b\u0003\u000fRB\u0011AAb\u0011\u001d\t9E\u0007C\u0001\u0003#D\u0011\"a)\u001b\u0005\u0004%)!!7\t\u0011\u0005m'\u0004)A\u0007\u0003;C\u0011\"!8\u001b\u0005\u0004%)!!\u0014\t\u0011\u0005}'\u0004)A\u0007\u0003\u001fB\u0011\"!9\u001b\u0005\u0004%i!!\u0014\t\u0011\u0005\r(\u0004)A\u0007\u0003\u001fB\u0011\"!:\u001b\u0005\u0004%)!a:\t\u0011\u0005=(\u0004)A\u0007\u0003SD\u0011\"!=\u001b\u0005\u0004%)!!\u0014\t\u0011\u0005M(\u0004)A\u0007\u0003\u001fB\u0011\"!>\u001b\u0005\u0004%)!a>\t\u0011\u0005}(\u0004)A\u0007\u0003sD\u0011B!\u0001\u001b\u0005\u0004%)!!\u0014\t\u0011\t\r!\u0004)A\u0007\u0003\u001fB\u0011B!\u0002\u001b\u0005\u0004%)Aa\u0002\t\u0011\tU!\u0004)A\u0007\u0005\u0013A\u0011Ba\u0006\u001b\u0005\u0004%)Aa\u0002\t\u0011\te!\u0004)A\u0007\u0005\u0013A\u0011Ba\u0007\u001b\u0005\u0004%)!a>\t\u0011\tu!\u0004)A\u0007\u0003sD\u0011Ba\b\u001b\u0005\u0004%)!a>\t\u0011\tU\"\u0004)A\u0007\u0003sD\u0011B!\u000f\u001b\u0005\u0004%)!a>\t\u0011\tm\"\u0004)A\u0007\u0003sD\u0011B!\u0010\u001b\u0005\u0004%)!a>\t\u0011\t}\"\u0004)A\u0007\u0003sD\u0011B!\u0011\u001b\u0005\u0004%)Aa\u0011\t\u0011\t-#\u0004)A\u0007\u0005\u000bB\u0011B!\u0014\u001b\u0005\u0004%)!!\u0014\t\u0011\t=#\u0004)A\u0007\u0003\u001fB\u0011B!\u0015\u001b\u0005\u0004%)!!\u0014\t\u0011\tM#\u0004)A\u0007\u0003\u001fB\u0011B!\u0016\u001b\u0005\u0004%)Aa\u0016\t\u0011\t%$\u0004)A\u0007\u00053B\u0011Ba\u001b\u001b\u0005\u0004%)!!\u0014\t\u0011\t5$\u0004)A\u0007\u0003\u001fB\u0011Ba\u001c\u001b\u0005\u0004%)!!\u0014\t\u0011\tE$\u0004)A\u0007\u0003\u001fB\u0011Ba\u001d\u001b\u0005\u0004%)Aa\u0002\t\u0011\tU$\u0004)A\u0007\u0005\u0013A\u0011Ba\u001e\u001b\u0005\u0004%)!a>\t\u0011\te$\u0004)A\u0007\u0003sD\u0011Ba\u001f\u001b\u0005\u0004%)A! \t\u0011\t\u0015%\u0004)A\u0007\u0005\u007fB\u0011Ba\"\u001b\u0005\u0004%)!a>\t\u0011\t%%\u0004)A\u0007\u0003sD\u0011Ba#\u001b\u0005\u0004%)A!$\t\u0011\t}%\u0004)A\u0007\u0005\u001fC\u0011B!)\u001b\u0005\u0004%)!a>\t\u0011\t\r&\u0004)A\u0007\u0003sD\u0011B!*\u001b\u0005\u0004%)!a>\t\u0011\t\u001d&\u0004)A\u0007\u0003sD\u0011B!+\u001b\u0005\u0004%)!a>\t\u0011\t-&\u0004)A\u0007\u0003sD\u0011B!,\u001b\u0005\u0004%)!a>\t\u0011\t=&\u0004)A\u0007\u0003sD\u0011B!-\u001b\u0005\u0004%)!a>\t\u0011\tM&\u0004)A\u0007\u0003sD\u0011B!.\u001b\u0005\u0004%)!a>\t\u0011\t]&\u0004)A\u0007\u0003sD\u0011B!/\u001b\u0005\u0004%)!a>\t\u0011\tm&\u0004)A\u0007\u0003sD\u0011B!0\u001b\u0005\u0004%)Aa0\t\u0011\t\u001d'\u0004)A\u0007\u0005\u0003D\u0011B!3\u001b\u0005\u0004%)!!\u0014\t\u0011\t-'\u0004)A\u0007\u0003\u001fB\u0011B!4\u001b\u0005\u0004%)!a>\t\u0011\t='\u0004)A\u0007\u0003sD\u0011B!5\u001b\u0005\u0004%)!a>\t\u0011\tM'\u0004)A\u0007\u0003sD\u0011B!6\u001b\u0005\u0004%)!a>\t\u0011\t]'\u0004)A\u0007\u0003sD\u0011B!7\u001b\u0005\u0004%)!a>\t\u0011\tm'\u0004)A\u0007\u0003sD\u0011B!8\u001b\u0005\u0004%)!a>\t\u0011\t}'\u0004)A\u0007\u0003sD\u0011B!9\u001b\u0005\u0004%)!a>\t\u0011\t\r(\u0004)A\u0007\u0003sD\u0011B!:\u001b\u0005\u0004%)A! \t\u0011\t\u001d(\u0004)A\u0007\u0005\u007fBqA!;\u001b\t\u0003\u0012Y\u000fC\u0005\u0005X\u0006!\t!!\f\u0005Z\u001aA\u0011qGA\u0015\u0003\u0003\ti\u0007C\u0004\u0002HU$\t!!\u001b\t\u000f\u0005mTO\"\u0001\u0002N!9\u0011QP;\u0007\u0002\u0005}\u0004b\u0002Bwk\u001a\u0005!q\u001e\u0005\b\u0005o,h\u0011\u0001B}\u0011\u001d\u0019\u0019!\u001eC\u0001\u0007\u000bAqAa>v\r\u0003\u0019I\u0001C\u0004\u0004 U$\ta!\t\t\u0013\r-RO1A\u0005\u0002\r5\u0002\u0002CB\u001bk\u0002\u0006Iaa\f\t\u000f\r]R\u000f\"\u0001\u0004.!91\u0011H;\u0007\u0002\rm\u0002bBB%k\u0012\u000511\b\u0005\b\u0007\u0017*h\u0011AB'\u0011\u001d\u0019)&\u001eD\u0001\u0007/Bqaa\u0018v\r\u0003\u0019\t\u0007C\u0004\u0004jU$\ta!\u0019\t\u000f\r-TO\"\u0001\u0004n!911P;\u0007\u0004\ru\u0004bBBDk\u0012\u00051Q\u0010\u0005\b\u0007\u0013+h\u0011ABF\u0011\u001d\u0019\u0019*\u001eD\u0001\u0007+Cqaa%v\r\u0003\u0019I\fC\u0004\u0004DV4\ta!2\t\u000f\rMWO\"\u0001\u0004V\"91q[;\u0007\u0002\re\u0007bBBtk\u001a\u00051\u0011\u001e\u0005\b\t\u0007)h\u0011\u0001C\u0003\u0011\u001d!\t\"\u001eD\u0001\t'\t1\"Q2u_J\u001c\u0016p\u001d;f[*!\u00111FA\u0017\u0003\u0015\t7\r^8s\u0015\t\ty#\u0001\u0003bW.\f7\u0001\u0001\t\u0004\u0003k\tQBAA\u0015\u0005-\t5\r^8s'f\u001cH/Z7\u0014\u0007\u0005\tY\u0004\u0005\u0003\u0002>\u0005\rSBAA \u0015\t\t\t%A\u0003tG\u0006d\u0017-\u0003\u0003\u0002F\u0005}\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0003\u0003g\tqAV3sg&|g.\u0006\u0002\u0002PA!\u0011\u0011KA0\u001d\u0011\t\u0019&a\u0017\u0011\t\u0005U\u0013qH\u0007\u0003\u0003/RA!!\u0017\u00022\u00051AH]8pizJA!!\u0018\u0002@\u00051\u0001K]3eK\u001aLA!!\u0019\u0002d\t11\u000b\u001e:j]\u001eTA!!\u0018\u0002@\u0005Aa+\u001a:tS>t\u0007%\u0001\u0004de\u0016\fG/\u001a\u000b\u0003\u0003W\u00022!!\u000ev'\u001d)\u00181HA8\u0003k\u0002B!!\u000e\u0002r%!\u00111OA\u0015\u0005=\t5\r^8s%\u00164g)Y2u_JL\b\u0003BA\u001b\u0003oJA!!\u001f\u0002*\tQ2\t\\1tg&\u001c\u0017i\u0019;peNK8\u000f^3n!J|g/\u001b3fe\u0006!a.Y7f\u0003!\u0019X\r\u001e;j]\u001e\u001cXCAAA!\r\t\u0019I\u0007\b\u0004\u0003k\u0001!\u0001C*fiRLgnZ:\u0014\u0007i\tY$A\u0006dY\u0006\u001c8\u000fT8bI\u0016\u0014\b\u0003BAG\u0003/k!!a$\u000b\t\u0005E\u00151S\u0001\u0005Y\u0006twM\u0003\u0002\u0002\u0016\u0006!!.\u0019<b\u0013\u0011\tI*a$\u0003\u0017\rc\u0017m]:M_\u0006$WM]\u0001\u0004G\u001a<\u0007\u0003BAP\u0003[k!!!)\u000b\t\u0005\r\u0016QU\u0001\u0007G>tg-[4\u000b\t\u0005\u001d\u0016\u0011V\u0001\tif\u0004Xm]1gK*\u0011\u00111V\u0001\u0004G>l\u0017\u0002BAX\u0003C\u0013aaQ8oM&<\u0017!\u00028b[\u0016\u0004\u0013!B:fiV\u0004XCAA\\!\u0011\tI,!0\u000e\u0005\u0005m&\u0002BAZ\u0003SIA!a0\u0002<\n\u0001\u0012i\u0019;peNK8\u000f^3n'\u0016$X\u000f]\u0001\u0007g\u0016$X\u000f\u001d\u0011\u0015\u0015\u0005\u0015\u0017\u0011ZAf\u0003\u001b\fy\rE\u0002\u0002Hji\u0011!\u0001\u0005\b\u0003\u0013\u000b\u0003\u0019AAF\u0011\u001d\tY*\ta\u0001\u0003;Cq!a\u001f\"\u0001\u0004\ty\u0005C\u0004\u00024\u0006\u0002\r!a.\u0015\u0011\u0005\u0015\u00171[Ak\u0003/Dq!!##\u0001\u0004\tY\tC\u0004\u0002\u001c\n\u0002\r!!(\t\u000f\u0005m$\u00051\u0001\u0002PU\u0011\u0011QT\u0001\bG>tg-[4!\u00035\u0019uN\u001c4jOZ+'o]5p]\u0006q1i\u001c8gS\u001e4VM]:j_:\u0004\u0013A\u00069s_ZLG-\u001a:TK2,7\r^5p]N+G/\u001e9\u0002/A\u0014xN^5eKJ\u001cV\r\\3di&|gnU3ukB\u0004\u0013!\u0006)s_ZLG-\u001a:TK2,7\r^5p]RK\b/Z\u000b\u0003\u0003S\u0004B!!\u000e\u0002l&!\u0011Q^A\u0015\u0005E\u0001&o\u001c<jI\u0016\u00148+\u001a7fGRLwN\\\u0001\u0017!J|g/\u001b3feN+G.Z2uS>tG+\u001f9fA\u0005i\u0001K]8wS\u0012,'o\u00117bgN\fa\u0002\u0015:pm&$WM]\"mCN\u001c\b%\u0001\u0006ICN\u001cE.^:uKJ,\"!!?\u0011\t\u0005u\u00121`\u0005\u0005\u0003{\fyDA\u0004C_>dW-\u00198\u0002\u0017!\u000b7o\u00117vgR,'\u000fI\u0001\u0018'V\u0004XM\u001d<jg>\u00148\u000b\u001e:bi\u0016<\u0017p\u00117bgN\f\u0001dU;qKJ4\u0018n]8s'R\u0014\u0018\r^3hs\u000ec\u0017m]:!\u0003=\u0019%/Z1uS>tG+[7f_V$XC\u0001B\u0005!\u0011\u0011YA!\u0005\u000e\u0005\t5!\u0002\u0002B\b\u0003[\tA!\u001e;jY&!!1\u0003B\u0007\u0005\u001d!\u0016.\\3pkR\f\u0001c\u0011:fCRLwN\u001c+j[\u0016|W\u000f\u001e\u0011\u0002)Us7\u000f^1si\u0016$\u0007+^:i)&lWm\\;u\u0003U)fn\u001d;beR,G\rU;tQRKW.Z8vi\u0002\na#\u00117m_^T\u0015M^1TKJL\u0017\r\\5{CRLwN\\\u0001\u0018\u00032dwn\u001e&bm\u0006\u001cVM]5bY&T\u0018\r^5p]\u0002\nQ%\u00128bE2,\u0017\t\u001a3ji&|g.\u00197TKJL\u0017\r\\5{CRLwN\u001c\"j]\u0012LgnZ:)\u0017]\u0012\u0019C!\u000b\u0003,\t=\"\u0011\u0007\t\u0005\u0003{\u0011)#\u0003\u0003\u0003(\u0005}\"A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017E\u0001B\u0017\u0003y\tEn^1zg\u0002*g.\u00192mK\u0012\u0004cM]8nA\u0005[7.\u0019\u00113]Yr\u0003'A\u0003tS:\u001cW-\t\u0002\u00034\u0005)!G\f\u001c/a\u00051SI\\1cY\u0016\fE\rZ5uS>t\u0017\r\\*fe&\fG.\u001b>bi&|gNQ5oI&twm\u001d\u0011)\u0017a\u0012\u0019C!\u000b\u0003,\t=\"\u0011G\u0001\u0015'\u0016\u0014\u0018.\u00197ju\u0016\fE\u000e\\'fgN\fw-Z:\u0002+M+'/[1mSj,\u0017\t\u001c7NKN\u001c\u0018mZ3tA\u0005!2+\u001a:jC2L'0Z!mY\u000e\u0013X-\u0019;peN\fQcU3sS\u0006d\u0017N_3BY2\u001c%/Z1u_J\u001c\b%\u0001\u0017O_N+'/[1mSj\fG/[8o-\u0016\u0014\u0018NZ5dCRLwN\u001c(fK\u0012,Gm\u00117bgN\u0004&/\u001a4jqV\u0011!Q\t\t\u0007\u0003#\u00129%a\u0014\n\t\t%\u00131\r\u0002\u0004'\u0016$\u0018!\f(p'\u0016\u0014\u0018.\u00197ju\u0006$\u0018n\u001c8WKJLg-[2bi&|gNT3fI\u0016$7\t\\1tgB\u0013XMZ5yA\u0005AAj\\4MKZ,G.A\u0005M_\u001edUM^3mA\u0005q1\u000b\u001e3pkRdun\u001a'fm\u0016d\u0017aD*uI>,H\u000fT8h\u0019\u00164X\r\u001c\u0011\u0002\u000f1{wmZ3sgV\u0011!\u0011\f\t\u0007\u00057\u0012)'a\u0014\u000e\u0005\tu#\u0002\u0002B0\u0005C\n\u0011\"[7nkR\f'\r\\3\u000b\t\t\r\u0014qH\u0001\u000bG>dG.Z2uS>t\u0017\u0002\u0002B4\u0005;\u00121aU3r\u0003!aunZ4feN\u0004\u0013!\u0005'pO\u001e,'o\u001d#jgB\fGo\u00195fe\u0006\u0011Bj\\4hKJ\u001cH)[:qCR\u001c\u0007.\u001a:!\u00035aunZ4j]\u001e4\u0015\u000e\u001c;fe\u0006qAj\\4hS:<g)\u001b7uKJ\u0004\u0013A\u0005'pO\u001e,'o\u0015;beR$\u0016.\\3pkR\f1\u0003T8hO\u0016\u00148\u000b^1siRKW.Z8vi\u0002\n\u0001\u0003T8h\u0007>tg-[4P]N#\u0018M\u001d;\u0002#1{wmQ8oM&<wJ\\*uCJ$\b%\u0001\bM_\u001e$U-\u00193MKR$XM]:\u0016\u0005\t}\u0004\u0003BA\u001f\u0005\u0003KAAa!\u0002@\t\u0019\u0011J\u001c;\u0002\u001f1{w\rR3bI2+G\u000f^3sg\u0002\nA\u0004T8h\t\u0016\fG\rT3ui\u0016\u00148\u000fR;sS:<7\u000b[;uI><h.A\u000fM_\u001e$U-\u00193MKR$XM]:EkJLgnZ*ikR$wn\u001e8!\u0003uaun\u001a#fC\u0012dU\r\u001e;feN\u001cVo\u001d9f]\u0012$UO]1uS>tWC\u0001BH!\u0011\u0011\tJa'\u000e\u0005\tM%\u0002\u0002BK\u0005/\u000b\u0001\u0002Z;sCRLwN\u001c\u0006\u0005\u00053\u000by$\u0001\u0006d_:\u001cWO\u001d:f]RLAA!(\u0003\u0014\nAA)\u001e:bi&|g.\u0001\u0010M_\u001e$U-\u00193MKR$XM]:TkN\u0004XM\u001c3EkJ\fG/[8oA\u0005\t\u0012\t\u001a3M_\u001e<\u0017N\\4SK\u000e,\u0017N^3\u0002%\u0005#G\rT8hO&twMU3dK&4X\rI\u0001\u0011\t\u0016\u0014WoZ!vi>\u0014VmY3jm\u0016\f\u0011\u0003R3ck\u001e\fU\u000f^8SK\u000e,\u0017N^3!\u00039!UMY;h\u0019&4WmY=dY\u0016\fq\u0002R3ck\u001ed\u0015NZ3ds\u000edW\rI\u0001\u000e\rNlG)\u001a2vO\u00163XM\u001c;\u0002\u001d\u0019\u001bX\u000eR3ck\u001e,e/\u001a8uA\u0005\u0001B)\u001a2vO\u00163XM\u001c;TiJ,\u0017-\\\u0001\u0012\t\u0016\u0014WoZ#wK:$8\u000b\u001e:fC6\u0004\u0013!\u0006#fEV<WK\u001c5b]\u0012dW\rZ'fgN\fw-Z\u0001\u0017\t\u0016\u0014WoZ+oQ\u0006tG\r\\3e\u001b\u0016\u001c8/Y4fA\u0005YB)\u001a2vOJ{W\u000f^3s\u001b&\u001c8m\u001c8gS\u001e,(/\u0019;j_:\fA\u0004R3ck\u001e\u0014v.\u001e;fe6K7oY8oM&<WO]1uS>t\u0007%\u0001\u0003I_6,WC\u0001Ba!\u0019\tiDa1\u0002P%!!QYA \u0005\u0019y\u0005\u000f^5p]\u0006)\u0001j\\7fA\u0005q1k\u00195fIVdWM]\"mCN\u001c\u0018aD*dQ\u0016$W\u000f\\3s\u00072\f7o\u001d\u0011\u0002\u0017\u0011\u000bW-\\8oS\u000eLG/_\u0001\r\t\u0006,Wn\u001c8jG&$\u0018\u0010I\u0001\u0014\u0015ZlW\t_5u\u001f:4\u0015\r^1m\u000bJ\u0014xN]\u0001\u0015\u0015ZlW\t_5u\u001f:4\u0015\r^1m\u000bJ\u0014xN\u001d\u0011\u0002!)3Xn\u00155vi\u0012|wO\u001c%p_.\u001c\u0018!\u0005&w[NCW\u000f\u001e3po:Dun\\6tA\u0005\tb)Y5m\u001b&DX\r\u001a,feNLwN\\:\u0002%\u0019\u000b\u0017\u000e\\'jq\u0016$g+\u001a:tS>t7\u000fI\u0001(\u0007>|'\u000fZ5oCR,Gm\u00155vi\u0012|wO\u001c+fe6Lg.\u0019;f\u0003\u000e$xN]*zgR,W.\u0001\u0015D_>\u0014H-\u001b8bi\u0016$7\u000b[;uI><h\u000eV3s[&t\u0017\r^3BGR|'oU=ti\u0016l\u0007%\u0001\u0017D_>\u0014H-\u001b8bi\u0016$7\u000b[;uI><hNU;o\u0005f\f5\r^8s'f\u001cH/Z7UKJl\u0017N\\1uK\u0006i3i\\8sI&t\u0017\r^3e'\",H\u000fZ8x]J+hNQ=BGR|'oU=ti\u0016lG+\u001a:nS:\fG/\u001a\u0011\u00023\u0011+g-Y;miZK'\u000f^;bY:{G-Z:GC\u000e$xN]\u0001\u001b\t\u00164\u0017-\u001e7u-&\u0014H/^1m\u001d>$Wm\u001d$bGR|'\u000fI\u0001\ti>\u001cFO]5oOR\u0011\u0011qJ\u0001\u0011Y><7i\u001c8gS\u001e,(/\u0019;j_:$\"A!=\u0011\t\u0005u\"1_\u0005\u0005\u0005k\fyD\u0001\u0003V]&$\u0018\u0001\u0002\u0013eSZ$BAa?\u0004\u0002A!\u0011Q\u0007B\u007f\u0013\u0011\u0011y0!\u000b\u0003\u0013\u0005\u001bGo\u001c:QCRD\u0007bBA>u\u0002\u0007\u0011qJ\u0001\u0006G\"LG\u000e\u001a\u000b\u0005\u0005w\u001c9\u0001C\u0004\u0004\u0004m\u0004\r!a\u0014\u0015\t\tm81\u0002\u0005\b\u0003wb\b\u0019AB\u0007!\u0019\u0019ya!\u0007\u0002P9!1\u0011CB\u000b\u001d\u0011\t)fa\u0005\n\u0005\u0005\u0005\u0013\u0002BB\f\u0003\u007f\tq\u0001]1dW\u0006<W-\u0003\u0003\u0004\u001c\ru!\u0001C%uKJ\f'\r\\3\u000b\t\r]\u0011qH\u0001\u000bI\u0016\u001c8-\u001a8eC:$H\u0003\u0002B~\u0007GAqa!\n~\u0001\u0004\u00199#A\u0003oC6,7\u000f\u0005\u0004\u0002\u000e\u000e%\u0012qJ\u0005\u0005\u00077\ty)A\u0005ti\u0006\u0014H\u000fV5nKV\u00111q\u0006\t\u0005\u0003{\u0019\t$\u0003\u0003\u00044\u0005}\"\u0001\u0002'p]\u001e\f!b\u001d;beR$\u0016.\\3!\u0003\u0019)\b\u000f^5nK\u0006YQM^3oiN#(/Z1n+\t\u0019i\u0004\u0005\u0003\u0004@\r\u0015SBAB!\u0015\u0011\u0019\u0019%!\f\u0002\u000b\u00154XM\u001c;\n\t\r\u001d3\u0011\t\u0002\f\u000bZ,g\u000e^*ue\u0016\fW.\u0001\bhKR,e/\u001a8u'R\u0014X-Y7\u0002\u00071|w-\u0006\u0002\u0004PA!1qHB)\u0013\u0011\u0019\u0019f!\u0011\u0003\u001d1{wmZ5oO\u0006#\u0017\r\u001d;fe\u0006YA-Z1e\u0019\u0016$H/\u001a:t+\t\u0019I\u0006\u0005\u0003\u00026\rm\u0013\u0002BB/\u0003S\u0011\u0001\"Q2u_J\u0014VMZ\u0001\ng\u000eDW\rZ;mKJ,\"aa\u0019\u0011\t\u0005U2QM\u0005\u0005\u0007O\nICA\u0005TG\",G-\u001e7fe\u0006aq-\u001a;TG\",G-\u001e7fe\u0006YA-[:qCR\u001c\u0007.\u001a:t+\t\u0019y\u0007\u0005\u0003\u0004r\r]TBAB:\u0015\u0011\u0019)(!\f\u0002\u0011\u0011L7\u000f]1uG\"LAa!\u001f\u0004t\tYA)[:qCR\u001c\u0007.\u001a:t\u0003)!\u0017n\u001d9bi\u000eDWM]\u000b\u0003\u0007\u007f\u0002Ba!!\u0004\u00046\u0011!qS\u0005\u0005\u0007\u000b\u00139J\u0001\rFq\u0016\u001cW\u000f^5p]\u000e{g\u000e^3yi\u0016CXmY;u_J\fQbZ3u\t&\u001c\b/\u0019;dQ\u0016\u0014\u0018!C7bS2\u0014w\u000e_3t+\t\u0019i\t\u0005\u0003\u0004r\r=\u0015\u0002BBI\u0007g\u0012\u0011\"T1jY\n|\u00070Z:\u0002+I,w-[:uKJ|e\u000eV3s[&t\u0017\r^5p]V!1qSBT)\u0011\u0011\tp!'\t\u0013\rm\u0015q\u0003CA\u0002\ru\u0015\u0001B2pI\u0016\u0004b!!\u0010\u0004 \u000e\r\u0016\u0002BBQ\u0003\u007f\u0011\u0001\u0002\u00102z]\u0006lWM\u0010\t\u0005\u0007K\u001b9\u000b\u0004\u0001\u0005\u0011\r%\u0016q\u0003b\u0001\u0007W\u0013\u0011\u0001V\t\u0005\u0007[\u001b\u0019\f\u0005\u0003\u0002>\r=\u0016\u0002BBY\u0003\u007f\u0011qAT8uQ&tw\r\u0005\u0003\u0002>\rU\u0016\u0002BB\\\u0003\u007f\u00111!\u00118z)\u0011\u0011\tpa/\t\u0011\rm\u0015\u0011\u0004a\u0001\u0007{\u0003B!!$\u0004@&!1\u0011YAH\u0005!\u0011VO\u001c8bE2,\u0017!\u0003;fe6Lg.\u0019;f)\t\u00199\r\u0005\u0004\u0004\u0002\u000e%7QZ\u0005\u0005\u0007\u0017\u00149J\u0001\u0004GkR,(/\u001a\t\u0005\u0003k\u0019y-\u0003\u0003\u0004R\u0006%\"A\u0003+fe6Lg.\u0019;fI\u0006qq\u000f[3o)\u0016\u0014X.\u001b8bi\u0016$WCABd\u0003E9W\r^,iK:$VM]7j]\u0006$X\rZ\u000b\u0003\u00077\u0004ba!8\u0004d\u000e5WBABp\u0015\u0011\u0011Ij!9\u000b\t\t=\u00111S\u0005\u0005\u0007K\u001cyNA\bD_6\u0004H.\u001a;j_:\u001cF/Y4f\u0003E\u0011XmZ5ti\u0016\u0014X\t\u001f;f]NLwN\\\u000b\u0005\u0007W\u001cy\u000f\u0006\u0003\u0004n\u000ee\b\u0003BBS\u0007_$\u0001b!+\u0002\"\t\u00071\u0011_\t\u0005\u0007[\u001b\u0019\u0010\u0005\u0003\u00026\rU\u0018\u0002BB|\u0003S\u0011\u0011\"\u0012=uK:\u001c\u0018n\u001c8\t\u0011\rm\u0018\u0011\u0005a\u0001\u0007{\f1!\u001a=u!\u0019\t)da@\u0004n&!A\u0011AA\u0015\u0005-)\u0005\u0010^3og&|g.\u00133\u0002\u0013\u0015DH/\u001a8tS>tW\u0003\u0002C\u0004\t\u0017!B\u0001\"\u0003\u0005\u000eA!1Q\u0015C\u0006\t!\u0019I+a\tC\u0002\rE\b\u0002CB~\u0003G\u0001\r\u0001b\u0004\u0011\r\u0005U2q C\u0005\u00031A\u0017m]#yi\u0016t7/[8o)\u0011\tI\u0010\"\u0006\t\u0011\rm\u0018Q\u0005a\u0001\t/\u0001D\u0001\"\u0007\u0005\u001eA1\u0011QGB\u0000\t7\u0001Ba!*\u0005\u001e\u0011aAq\u0004C\u000b\u0003\u0003\u0005\tQ!\u0001\u0004r\n\u0019q\fJ\u0019\u0015\t\u0005-D1\u0005\u0005\b\u0003w2\u0001\u0019AA()\u0019\tY\u0007b\n\u0005*!9\u00111P\u0004A\u0002\u0005=\u0003b\u0002C\u0016\u000f\u0001\u0007\u0011qW\u0001\u0007g\u0016$X\u000f]:\u0015\r\u0005-Dq\u0006C\u0019\u0011\u001d\tY\b\u0003a\u0001\u0003\u001fBq\u0001b\r\t\u0001\u0004!)$\u0001\bc_>$8\u000f\u001e:baN+G/\u001e9\u0011\t\u0005UBqG\u0005\u0005\ts\tIC\u0001\bC_>$8\u000f\u001e:baN+G/\u001e9\u0015\r\u0005-DQ\bC \u0011\u001d\tY(\u0003a\u0001\u0003\u001fBq!a)\n\u0001\u0004\ti\n\u0006\u0005\u0002l\u0011\rCQ\tC$\u0011\u001d\tYH\u0003a\u0001\u0003\u001fBq!a)\u000b\u0001\u0004\ti\nC\u0004\u0002\n*\u0001\r!a#\u0015\u0015\u0005-D1\nC'\t\u001f\"\t\u0006C\u0004\u0002|-\u0001\r!a\u0014\t\u000f\u0005\r6\u00021\u0001\u0002\u001e\"9\u0011\u0011R\u0006A\u0002\u0005-\u0005b\u0002C*\u0017\u0001\u0007AQK\u0001\u0018I\u00164\u0017-\u001e7u\u000bb,7-\u001e;j_:\u001cuN\u001c;fqR\u0004Ba!!\u0005X%!A\u0011\fBL\u0005A)\u00050Z2vi&|gnQ8oi\u0016DH/A\u0003baBd\u0017\u0010\u0006\u0003\u0002l\u0011}\u0003bBA>\u001b\u0001\u0007\u0011q\n\u000b\u0007\u0003W\"\u0019\u0007\"\u001a\t\u000f\u0005md\u00021\u0001\u0002P!9\u00111\u0017\bA\u0002\u0005]FCBA6\tS\"Y\u0007C\u0004\u0002|=\u0001\r!a\u0014\t\u000f\u0011Mr\u00021\u0001\u00056Q1\u00111\u000eC8\tcBq!a\u001f\u0011\u0001\u0004\ty\u0005C\u0004\u0002$B\u0001\r!!(\u0015\u0011\u0005-DQ\u000fC<\tsBq!a\u001f\u0012\u0001\u0004\ty\u0005C\u0004\u0002$F\u0001\r!!(\t\u000f\u0005%\u0015\u00031\u0001\u0002\fRQ\u00111\u000eC?\t\u007f\"\u0019\tb\"\t\u000f\u0005m$\u00031\u0001\u0002P!I\u00111\u0015\n\u0011\u0002\u0003\u0007A\u0011\u0011\t\u0007\u0003{\u0011\u0019-!(\t\u0013\u0005%%\u0003%AA\u0002\u0011\u0015\u0005CBA\u001f\u0005\u0007\fY\tC\u0005\u0005TI\u0001\n\u00111\u0001\u0005\nB1\u0011Q\bBb\t+\nq\"\u00199qYf$C-\u001a4bk2$HEM\u000b\u0003\t\u001fSC\u0001\"!\u0005\u0012.\u0012A1\u0013\t\u0005\t+#y*\u0004\u0002\u0005\u0018*!A\u0011\u0014CN\u0003%)hn\u00195fG.,GM\u0003\u0003\u0005\u001e\u0006}\u0012AC1o]>$\u0018\r^5p]&!A\u0011\u0015CL\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%gU\u0011Aq\u0015\u0016\u0005\t\u000b#\t*A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00135+\t!iK\u000b\u0003\u0005\n\u0012E\u0015\u0001C*fiRLgnZ:\u0011\u0007\u0005\u001dwcE\u0002\u0018\u0003w!\"\u0001\"-\u0002!\u0005lWM\u001c3TY\u001a$$nQ8oM&<GCBAO\tw#i\fC\u0004\u0002$f\u0001\r!!(\t\u000f\u0011}\u0016\u00041\u0001\u0005B\u0006iA-\u001f8b[&\u001c\u0017iY2fgN\u0004B!!\u000e\u0005D&!AQYA\u0015\u00055!\u0015P\\1nS\u000e\f5mY3tg\"\u001a\u0011\u0004\"3\u0011\t\u0011-GqZ\u0007\u0003\t\u001bTA\u0001\"(\u0002.%!A\u0011\u001bCg\u0005-Ie\u000e^3s]\u0006d\u0017\t]5)\u0007]!I\rK\u0002\u0017\t\u0013\fqBZ5oI\u000ec\u0017m]:M_\u0006$WM\u001d\u000b\u0003\u0003\u0017\u0003")
public abstract class ActorSystem
implements ActorRefFactory,
ClassicActorSystemProvider {
    private final long startTime;

    public static Option<ExecutionContext> apply$default$4() {
        return ActorSystem$.MODULE$.apply$default$4();
    }

    public static Option<ClassLoader> apply$default$3() {
        return ActorSystem$.MODULE$.apply$default$3();
    }

    public static Option<Config> apply$default$2() {
        return ActorSystem$.MODULE$.apply$default$2();
    }

    public static ActorSystem apply(String name, Option<Config> config, Option<ClassLoader> classLoader, Option<ExecutionContext> defaultExecutionContext) {
        return ActorSystem$.MODULE$.apply(name, config, classLoader, defaultExecutionContext);
    }

    public static ActorSystem apply(String name, Config config, ClassLoader classLoader) {
        return ActorSystem$.MODULE$.apply(name, config, classLoader);
    }

    public static ActorSystem apply(String name, Config config) {
        return ActorSystem$.MODULE$.apply(name, config);
    }

    public static ActorSystem apply(String name, BootstrapSetup bootstrapSetup) {
        return ActorSystem$.MODULE$.apply(name, bootstrapSetup);
    }

    public static ActorSystem apply(String name, ActorSystemSetup setup) {
        return ActorSystem$.MODULE$.apply(name, setup);
    }

    public static ActorSystem apply(String name) {
        return ActorSystem$.MODULE$.apply(name);
    }

    public static ActorSystem apply() {
        return ActorSystem$.MODULE$.apply();
    }

    public static ActorSystem create(String name, Config config, ClassLoader classLoader, ExecutionContext defaultExecutionContext) {
        return ActorSystem$.MODULE$.create(name, config, classLoader, defaultExecutionContext);
    }

    public static ActorSystem create(String name, Config config, ClassLoader classLoader) {
        return ActorSystem$.MODULE$.create(name, config, classLoader);
    }

    public static ActorSystem create(String name, Config config) {
        return ActorSystem$.MODULE$.create(name, config);
    }

    public static ActorSystem create(String name, BootstrapSetup bootstrapSetup) {
        return ActorSystem$.MODULE$.create(name, bootstrapSetup);
    }

    public static ActorSystem create(String name, ActorSystemSetup setups) {
        return ActorSystem$.MODULE$.create(name, setups);
    }

    public static ActorSystem create(String name) {
        return ActorSystem$.MODULE$.create(name);
    }

    public static ActorSystem create() {
        return ActorSystem$.MODULE$.create();
    }

    public static String Version() {
        return ActorSystem$.MODULE$.Version();
    }

    @Override
    public ActorSelection actorSelection(String path) {
        return ActorRefFactory.actorSelection$((ActorRefFactory)this, path);
    }

    @Override
    public ActorSelection actorSelection(ActorPath path) {
        return ActorRefFactory.actorSelection$((ActorRefFactory)this, path);
    }

    public abstract String name();

    public abstract Settings settings();

    public abstract void logConfiguration();

    public abstract ActorPath $div(String var1);

    public ActorPath child(String child) {
        return this.$div(child);
    }

    public abstract ActorPath $div(Iterable<String> var1);

    public ActorPath descendant(java.lang.Iterable<String> names) {
        return this.$div(Util$.MODULE$.immutableSeq(names));
    }

    public long startTime() {
        return this.startTime;
    }

    public long uptime() {
        return (System.currentTimeMillis() - this.startTime()) / 1000L;
    }

    public abstract EventStream eventStream();

    public EventStream getEventStream() {
        return this.eventStream();
    }

    public abstract LoggingAdapter log();

    public abstract ActorRef deadLetters();

    public abstract Scheduler scheduler();

    public Scheduler getScheduler() {
        return this.scheduler();
    }

    public abstract Dispatchers dispatchers();

    @Override
    public abstract ExecutionContextExecutor dispatcher();

    public ExecutionContextExecutor getDispatcher() {
        return this.dispatcher();
    }

    public abstract Mailboxes mailboxes();

    public abstract <T> void registerOnTermination(Function0<T> var1);

    public abstract void registerOnTermination(Runnable var1);

    public abstract Future<Terminated> terminate();

    public abstract Future<Terminated> whenTerminated();

    public abstract CompletionStage<Terminated> getWhenTerminated();

    public abstract <T extends Extension> T registerExtension(ExtensionId<T> var1);

    public abstract <T extends Extension> T extension(ExtensionId<T> var1);

    public abstract boolean hasExtension(ExtensionId<? extends Extension> var1);

    public ActorSystem() {
        ActorRefFactory.$init$(this);
        this.startTime = System.currentTimeMillis();
    }

    public static class Settings {
        private final String name;
        private final ActorSystemSetup setup;
        private final Config config;
        private final String ConfigVersion;
        private final String providerSelectionSetup;
        private final ProviderSelection ProviderSelectionType;
        private final String ProviderClass;
        private final boolean HasCluster;
        private final String SupervisorStrategyClass;
        private final Timeout CreationTimeout;
        private final Timeout UnstartedPushTimeout;
        private final boolean AllowJavaSerialization;
        private final boolean EnableAdditionalSerializationBindings;
        private final boolean SerializeAllMessages;
        private final boolean SerializeAllCreators;
        private final Set<String> NoSerializationVerificationNeededClassPrefix;
        private final String LogLevel;
        private final String StdoutLogLevel;
        private final Seq<String> Loggers;
        private final String LoggersDispatcher;
        private final String LoggingFilter;
        private final Timeout LoggerStartTimeout;
        private final boolean LogConfigOnStart;
        private final int LogDeadLetters;
        private final boolean LogDeadLettersDuringShutdown;
        private final Duration LogDeadLettersSuspendDuration;
        private final boolean AddLoggingReceive;
        private final boolean DebugAutoReceive;
        private final boolean DebugLifecycle;
        private final boolean FsmDebugEvent;
        private final boolean DebugEventStream;
        private final boolean DebugUnhandledMessage;
        private final boolean DebugRouterMisconfiguration;
        private final Option<String> Home;
        private final String SchedulerClass;
        private final boolean Daemonicity;
        private final boolean JvmExitOnFatalError;
        private final boolean JvmShutdownHooks;
        private final boolean FailMixedVersions;
        private final boolean CoordinatedShutdownTerminateActorSystem;
        private final boolean CoordinatedShutdownRunByActorSystemTerminate;
        private final int DefaultVirtualNodesFactor;

        public final String name() {
            return this.name;
        }

        public ActorSystemSetup setup() {
            return this.setup;
        }

        public final Config config() {
            return this.config;
        }

        public final String ConfigVersion() {
            return this.ConfigVersion;
        }

        private final String providerSelectionSetup() {
            return this.providerSelectionSetup;
        }

        public final ProviderSelection ProviderSelectionType() {
            return this.ProviderSelectionType;
        }

        public final String ProviderClass() {
            return this.ProviderClass;
        }

        public final boolean HasCluster() {
            return this.HasCluster;
        }

        public final String SupervisorStrategyClass() {
            return this.SupervisorStrategyClass;
        }

        public final Timeout CreationTimeout() {
            return this.CreationTimeout;
        }

        public final Timeout UnstartedPushTimeout() {
            return this.UnstartedPushTimeout;
        }

        public final boolean AllowJavaSerialization() {
            return this.AllowJavaSerialization;
        }

        public final boolean EnableAdditionalSerializationBindings() {
            return this.EnableAdditionalSerializationBindings;
        }

        public final boolean SerializeAllMessages() {
            return this.SerializeAllMessages;
        }

        public final boolean SerializeAllCreators() {
            return this.SerializeAllCreators;
        }

        public final Set<String> NoSerializationVerificationNeededClassPrefix() {
            return this.NoSerializationVerificationNeededClassPrefix;
        }

        public final String LogLevel() {
            return this.LogLevel;
        }

        public final String StdoutLogLevel() {
            return this.StdoutLogLevel;
        }

        public final Seq<String> Loggers() {
            return this.Loggers;
        }

        public final String LoggersDispatcher() {
            return this.LoggersDispatcher;
        }

        public final String LoggingFilter() {
            return this.LoggingFilter;
        }

        public final Timeout LoggerStartTimeout() {
            return this.LoggerStartTimeout;
        }

        public final boolean LogConfigOnStart() {
            return this.LogConfigOnStart;
        }

        public final int LogDeadLetters() {
            return this.LogDeadLetters;
        }

        public final boolean LogDeadLettersDuringShutdown() {
            return this.LogDeadLettersDuringShutdown;
        }

        public final Duration LogDeadLettersSuspendDuration() {
            return this.LogDeadLettersSuspendDuration;
        }

        public final boolean AddLoggingReceive() {
            return this.AddLoggingReceive;
        }

        public final boolean DebugAutoReceive() {
            return this.DebugAutoReceive;
        }

        public final boolean DebugLifecycle() {
            return this.DebugLifecycle;
        }

        public final boolean FsmDebugEvent() {
            return this.FsmDebugEvent;
        }

        public final boolean DebugEventStream() {
            return this.DebugEventStream;
        }

        public final boolean DebugUnhandledMessage() {
            return this.DebugUnhandledMessage;
        }

        public final boolean DebugRouterMisconfiguration() {
            return this.DebugRouterMisconfiguration;
        }

        public final Option<String> Home() {
            return this.Home;
        }

        public final String SchedulerClass() {
            return this.SchedulerClass;
        }

        public final boolean Daemonicity() {
            return this.Daemonicity;
        }

        public final boolean JvmExitOnFatalError() {
            return this.JvmExitOnFatalError;
        }

        public final boolean JvmShutdownHooks() {
            return this.JvmShutdownHooks;
        }

        public final boolean FailMixedVersions() {
            return this.FailMixedVersions;
        }

        public final boolean CoordinatedShutdownTerminateActorSystem() {
            return this.CoordinatedShutdownTerminateActorSystem;
        }

        public final boolean CoordinatedShutdownRunByActorSystemTerminate() {
            return this.CoordinatedShutdownRunByActorSystemTerminate;
        }

        public final int DefaultVirtualNodesFactor() {
            return this.DefaultVirtualNodesFactor;
        }

        public String toString() {
            return this.config().root().render();
        }

        public Settings(ClassLoader classLoader, Config cfg, String name, ActorSystemSetup setup) {
            Option option;
            block20: {
                Duration duration;
                block19: {
                    int n;
                    block18: {
                        block16: {
                            block17: {
                                this.name = name;
                                this.setup = setup;
                                cfg.checkValid(ConfigFactory.defaultReference(classLoader).withoutPath("akka.actor.internal-dispatcher"), "akka");
                                this.config = cfg;
                                this.ConfigVersion = this.config().getString("akka.version");
                                this.providerSelectionSetup = (String)setup.get(ClassTag$.MODULE$.apply(BootstrapSetup.class)).flatMap((Function1<BootstrapSetup, Option> & Serializable)x$5 -> x$5.actorRefProvider()).map((Function1<ProviderSelection, String> & Serializable)x$6 -> x$6.identifier()).getOrElse((Function0<String> & Serializable)() -> this.config().getString("akka.actor.provider"));
                                this.ProviderSelectionType = ProviderSelection$.MODULE$.apply(this.providerSelectionSetup());
                                this.ProviderClass = this.ProviderSelectionType().fqcn();
                                this.HasCluster = this.ProviderSelectionType().hasCluster();
                                this.SupervisorStrategyClass = this.config().getString("akka.actor.guardian-supervisor-strategy");
                                this.CreationTimeout = new Timeout(Helpers$ConfigOps$.MODULE$.getMillisDuration$extension(Helpers$.MODULE$.ConfigOps(this.config()), "akka.actor.creation-timeout"));
                                this.UnstartedPushTimeout = new Timeout(Helpers$ConfigOps$.MODULE$.getMillisDuration$extension(Helpers$.MODULE$.ConfigOps(this.config()), "akka.actor.unstarted-push-timeout"));
                                this.AllowJavaSerialization = this.config().getBoolean("akka.actor.allow-java-serialization");
                                this.EnableAdditionalSerializationBindings = true;
                                this.SerializeAllMessages = this.config().getBoolean("akka.actor.serialize-messages");
                                this.SerializeAllCreators = this.config().getBoolean("akka.actor.serialize-creators");
                                this.NoSerializationVerificationNeededClassPrefix = package$JavaConverters$.MODULE$.ListHasAsScala(this.config().getStringList("akka.actor.no-serialization-verification-needed-class-prefix")).asScala().toSet();
                                this.LogLevel = this.config().getString("akka.loglevel");
                                this.StdoutLogLevel = this.config().getString("akka.stdout-loglevel");
                                this.Loggers = Util$.MODULE$.immutableSeq(this.config().getStringList("akka.loggers"));
                                this.LoggersDispatcher = this.config().getString("akka.loggers-dispatcher");
                                this.LoggingFilter = this.config().getString("akka.logging-filter");
                                this.LoggerStartTimeout = new Timeout(Helpers$ConfigOps$.MODULE$.getMillisDuration$extension(Helpers$.MODULE$.ConfigOps(this.config()), "akka.logger-startup-timeout"));
                                this.LogConfigOnStart = this.config().getBoolean("akka.log-config-on-start");
                                String string = Helpers$.MODULE$.toRootLowerCase(this.config().getString("akka.log-dead-letters"));
                                switch (string == null ? 0 : string.hashCode()) {
                                    case 3551: {
                                        if (!"on".equals(string)) break block16;
                                        break block17;
                                    }
                                    case 109935: {
                                        if ("off".equals(string)) {
                                            break;
                                        }
                                        break block16;
                                    }
                                    case 3569038: {
                                        if (!"true".equals(string)) break block16;
                                        break block17;
                                    }
                                    case 97196323: {
                                        if ("false".equals(string)) {
                                            break;
                                        }
                                        break block16;
                                    }
                                    default: {
                                        break block16;
                                    }
                                }
                                n = 0;
                                break block18;
                            }
                            n = Integer.MAX_VALUE;
                            break block18;
                        }
                        n = this.config().getInt("akka.log-dead-letters");
                    }
                    this.LogDeadLetters = n;
                    this.LogDeadLettersDuringShutdown = this.config().getBoolean("akka.log-dead-letters-during-shutdown");
                    String key = "akka.log-dead-letters-suspend-duration";
                    String string = Helpers$.MODULE$.toRootLowerCase(this.config().getString(key));
                    switch (string == null ? 0 : string.hashCode()) {
                        case 173173268: {
                            if (!"infinite".equals(string)) break;
                            duration = Duration$.MODULE$.Inf();
                            break block19;
                        }
                    }
                    duration = Helpers$ConfigOps$.MODULE$.getMillisDuration$extension(Helpers$.MODULE$.ConfigOps(this.config()), key);
                }
                this.LogDeadLettersSuspendDuration = duration;
                this.AddLoggingReceive = this.config().getBoolean("akka.actor.debug.receive");
                this.DebugAutoReceive = this.config().getBoolean("akka.actor.debug.autoreceive");
                this.DebugLifecycle = this.config().getBoolean("akka.actor.debug.lifecycle");
                this.FsmDebugEvent = this.config().getBoolean("akka.actor.debug.fsm");
                this.DebugEventStream = this.config().getBoolean("akka.actor.debug.event-stream");
                this.DebugUnhandledMessage = this.config().getBoolean("akka.actor.debug.unhandled");
                this.DebugRouterMisconfiguration = this.config().getBoolean("akka.actor.debug.router-misconfiguration");
                String string = this.config().getString("akka.home");
                switch (string == null ? 0 : string.hashCode()) {
                    case 0: {
                        if (!"".equals(string)) break;
                        option = None$.MODULE$;
                        break block20;
                    }
                }
                option = new Some<String>(string);
            }
            this.Home = option;
            this.SchedulerClass = this.config().getString("akka.scheduler.implementation");
            this.Daemonicity = this.config().getBoolean("akka.daemonic");
            this.JvmExitOnFatalError = this.config().getBoolean("akka.jvm-exit-on-fatal-error");
            this.JvmShutdownHooks = this.config().getBoolean("akka.jvm-shutdown-hooks");
            this.FailMixedVersions = this.config().getBoolean("akka.fail-mixed-versions");
            this.CoordinatedShutdownTerminateActorSystem = this.config().getBoolean("akka.coordinated-shutdown.terminate-actor-system");
            this.CoordinatedShutdownRunByActorSystemTerminate = this.config().getBoolean("akka.coordinated-shutdown.run-by-actor-system-terminate");
            if (this.CoordinatedShutdownRunByActorSystemTerminate() && !this.CoordinatedShutdownTerminateActorSystem()) {
                throw new ConfigurationException("akka.coordinated-shutdown.run-by-actor-system-terminate=on and akka.coordinated-shutdown.terminate-actor-system=off is not a supported configuration combination.");
            }
            this.DefaultVirtualNodesFactor = this.config().getInt("akka.actor.deployment.default.virtual-nodes-factor");
            String string = this.ConfigVersion();
            String string2 = ActorSystem$.MODULE$.Version();
            if (string == null ? string2 != null : !string.equals(string2)) {
                throw new ConfigurationException(new StringBuilder(65).append("Akka JAR version [").append(ActorSystem$.MODULE$.Version()).append("] does not match the provided config version [").append(this.ConfigVersion()).append("]").toString());
            }
        }

        public Settings(ClassLoader classLoader, Config cfg, String name) {
            this(classLoader, cfg, name, ActorSystemSetup$.MODULE$.apply(Nil$.MODULE$));
        }
    }
}

