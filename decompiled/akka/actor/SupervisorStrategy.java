/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorContext;
import akka.actor.ActorInitializationException;
import akka.actor.ActorRef;
import akka.actor.ChildRestartStats;
import akka.actor.InternalActorRef;
import akka.actor.SupervisorStrategy$;
import akka.actor.SupervisorStrategy$Escalate$;
import akka.actor.SupervisorStrategy$Restart$;
import akka.actor.SupervisorStrategy$Resume$;
import akka.actor.SupervisorStrategy$Stop$;
import akka.annotation.InternalApi;
import akka.event.Logging;
import akka.event.Logging$;
import akka.event.Logging$LogEvent$;
import akka.japi.Function;
import java.lang.reflect.InvocationTargetException;
import scala.MatchError;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.collection.Iterable;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\r\u001dv!B2e\u0011\u0003Ig!B6e\u0011\u0003a\u0007\"\u0002<\u0002\t\u00039ha\u0002=\u0002!\u0003\r\n#\u001f\u0005\u0007u\u000e1\tAZ>\b\u000f\t\u0015\u0011\u0001#!\u0002^\u001a9\u0011QZ\u0001\t\u0002\u0006e\u0007B\u0002<\u0007\t\u0003\tY\u000eC\u0005\u0002V\u0019\t\t\u0011\"\u0011\u0002X!I\u0011\u0011\u000e\u0004\u0002\u0002\u0013\u0005\u00111\u000e\u0005\n\u0003g2\u0011\u0011!C\u0001\u0003?D\u0011\"!!\u0007\u0003\u0003%\t%a!\t\u0013\u0005Ee!!A\u0005\u0002\u0005\r\b\"CAO\r\u0005\u0005I\u0011IAP\u0011%\t\tKBA\u0001\n\u0003\n\u0019\u000bC\u0005\u0002&\u001a\t\t\u0011\"\u0003\u0002(\u001a9\u0011QZ\u0001\u0011M\u0006=\u0007\"\u0003>\u0011\u0005\u000b\u0007I\u0011\u00014|\u0011%\t\u0019\f\u0005B\u0001B\u0003%A\u0010\u0003\u0004w!\u0011\u0005\u0011\u0011[\u0004\b\u0005\u000f\t\u0001\u0012QAa\r\u001d\ty+\u0001EA\u0003{CaA^\u000b\u0005\u0002\u0005}\u0006\"CA++\u0005\u0005I\u0011IA,\u0011%\tI'FA\u0001\n\u0003\tY\u0007C\u0005\u0002tU\t\t\u0011\"\u0001\u0002D\"I\u0011\u0011Q\u000b\u0002\u0002\u0013\u0005\u00131\u0011\u0005\n\u0003#+\u0012\u0011!C\u0001\u0003\u000fD\u0011\"!(\u0016\u0003\u0003%\t%a(\t\u0013\u0005\u0005V#!A\u0005B\u0005\r\u0006\"CAS+\u0005\u0005I\u0011BAT\r\u001d\ty+\u0001\tg\u0003cC\u0011B_\u0010\u0003\u0006\u0004%\tAZ>\t\u0013\u0005MvD!A!\u0002\u0013a\bB\u0002< \t\u0003\t)lB\u0004\u0003\n\u0005A\t)!?\u0007\u000f\u0005%\u0018\u0001#!\u0002v\"1a\u000f\nC\u0001\u0003oD\u0011\"!\u0016%\u0003\u0003%\t%a\u0016\t\u0013\u0005%D%!A\u0005\u0002\u0005-\u0004\"CA:I\u0005\u0005I\u0011AA~\u0011%\t\t\tJA\u0001\n\u0003\n\u0019\tC\u0005\u0002\u0012\u0012\n\t\u0011\"\u0001\u0002\u0000\"I\u0011Q\u0014\u0013\u0002\u0002\u0013\u0005\u0013q\u0014\u0005\n\u0003C#\u0013\u0011!C!\u0003GC\u0011\"!*%\u0003\u0003%I!a*\u0007\u000f\u0005%\u0018\u0001\u00054\u0002l\"I!P\fBC\u0002\u0013\u0005am\u001f\u0005\n\u0003gs#\u0011!Q\u0001\nqDaA\u001e\u0018\u0005\u0002\u00055xa\u0002B\u0006\u0003!\u0005\u00151\n\u0004\b\u0003S\t\u0001\u0012QA\u0016\u0011\u001918\u0007\"\u0001\u0002J!9!p\rC!M\u00065\u0003\"CA+g\u0005\u0005I\u0011IA,\u0011%\tIgMA\u0001\n\u0003\tY\u0007C\u0005\u0002tM\n\t\u0011\"\u0001\u0002v!I\u0011\u0011Q\u001a\u0002\u0002\u0013\u0005\u00131\u0011\u0005\n\u0003#\u001b\u0014\u0011!C\u0001\u0003'C\u0011\"!(4\u0003\u0003%\t%a(\t\u0013\u0005\u00056'!A\u0005B\u0005\r\u0006\"CASg\u0005\u0005I\u0011BAT\u0011\u001d\u0011i!\u0001C\u0001\u0005\u001fAqA!\u0004\u0002\t\u0003\u0011\t\u0002C\u0004\u0003\u0016\u0005!\tAa\u0006\t\u000f\tU\u0011\u0001\"\u0001\u0003\u001a!9!QD\u0001\u0005\u0002\t}\u0001b\u0002B\u000f\u0003\u0011\u0005!\u0011\u0005\u0005\b\u0005K\tA\u0011\u0001B\u0014\u0011%\u0011I#\u0001b\u0001\n\u000b\u0011Y\u0003\u0003\u0005\u0003>\u0005\u0001\u000bQ\u0002B\u0017\u0011%\u0011y$\u0001b\u0001\n\u000b\u0011\t\u0005\u0003\u0005\u0003\\\u0006\u0001\u000bQ\u0002B\"\u0011%\u0011i.\u0001b\u0001\n\u000b\u0011\t\u0005\u0003\u0005\u0003`\u0006\u0001\u000bQ\u0002B\"\u0011\u001d\u0011\t/\u0001C\u0002\u0005G,aAa\f\u0002\u0001\tERABB\t\u0003\u0001\u0019\u0019\"\u0002\u0004\u0004 \u0005\u00011\u0011\u0005\u0005\b\u0007c\tA\u0011AB\u001a\u0011\u001d\u0019\t$\u0001C\u0001\u0007\u0007Bqa!\r\u0002\t\u0003\u0019)\u0006C\u0004\u00042\u0005!\taa\u0018\t\u0011\r\u001d\u0014\u0001\"\u0001g\u0007SB\u0001b!\u001d\u0002\t\u0003171\u000f\u0005\t\u0007\u001f\u000bA\u0011\u00014\u0004\u0012\"Q1\u0011T\u0001C\u0002\u0013\u0005ama'\t\u0011\r\u0015\u0016\u0001)A\u0005\u0007;3aa\u001b3\u0002\u0002\t\u0015\u0003B\u0002<Y\t\u0003\u00119\u0005C\u0004\u0003Ja3\tAa\u0013\t\u000f\tE\u0003L\"\u0001\u0003T!9!\u0011\u0010-\u0007\u0002\tm\u0004b\u0002BK1\u0012\u0005!q\u0013\u0005\b\u0005GCF\u0011\u0003BS\u0011\u001d\u00119\u000b\u0017C\u0001\u0005SCqAa.Y\t\u0013\u0011I\fC\u0004\u0003Hb#)A!3\t\u000f\t=\u0007\f\"\u0002\u0003R\u0006\u00112+\u001e9feZL7o\u001c:TiJ\fG/Z4z\u0015\t)g-A\u0003bGR|'OC\u0001h\u0003\u0011\t7n[1\u0004\u0001A\u0011!.A\u0007\u0002I\n\u00112+\u001e9feZL7o\u001c:TiJ\fG/Z4z'\r\tQn\u001d\t\u0003]Fl\u0011a\u001c\u0006\u0002a\u0006)1oY1mC&\u0011!o\u001c\u0002\u0007\u0003:L(+\u001a4\u0011\u0005)$\u0018BA;e\u0005\u0019\u001aV\u000f]3sm&\u001cxN]*ue\u0006$XmZ=M_^\u0004&/[8sSRL\u0018*\u001c9mS\u000eLGo]\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003%\u0014\u0011\u0002R5sK\u000e$\u0018N^3\u0014\u0005\ri\u0017\u0001\u00037pO2+g/\u001a7\u0016\u0003q\u00042!`A\n\u001d\rq\u0018Q\u0002\b\u0004\u007f\u0006%a\u0002BA\u0001\u0003\u000fi!!a\u0001\u000b\u0007\u0005\u0015\u0001.\u0001\u0004=e>|GOP\u0005\u0002O&\u0019\u00111\u00024\u0002\u000b\u00154XM\u001c;\n\t\u0005=\u0011\u0011C\u0001\b\u0019><w-\u001b8h\u0015\r\tYAZ\u0005\u0005\u0003+\t9B\u0001\u0005M_\u001edUM^3m\u0015\u0011\ty!!\u0005)\u0007\u0011\tY\u0002\u0005\u0003\u0002\u001e\u0005\rRBAA\u0010\u0015\r\t\tCZ\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA\u0013\u0003?\u00111\"\u00138uKJt\u0017\r\\!qS&*1aM\u0010\u0011]\tAQi]2bY\u0006$Xm\u0005\u00054[\u00065\u0012\u0011GA\u001c!\r\tycA\u0007\u0002\u0003A\u0019a.a\r\n\u0007\u0005UrNA\u0004Qe>$Wo\u0019;\u0011\t\u0005e\u00121\t\b\u0005\u0003w\tyD\u0004\u0003\u0002\u0002\u0005u\u0012\"\u00019\n\u0007\u0005\u0005s.A\u0004qC\u000e\\\u0017mZ3\n\t\u0005\u0015\u0013q\t\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0004\u0003\u0003zGCAA&!\r\tycM\u000b\u0003\u0003\u001f\u00022A\\A)\u0013\r\t\u0019f\u001c\u0002\b\u001d>$\b.\u001b8h\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011\u0011\u0011\f\t\u0005\u00037\n)'\u0004\u0002\u0002^)!\u0011qLA1\u0003\u0011a\u0017M\\4\u000b\u0005\u0005\r\u0014\u0001\u00026bm\u0006LA!a\u001a\u0002^\t11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\"!!\u001c\u0011\u00079\fy'C\u0002\u0002r=\u00141!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$B!a\u001e\u0002~A\u0019a.!\u001f\n\u0007\u0005mtNA\u0002B]fD\u0011\"a 9\u0003\u0003\u0005\r!!\u001c\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\t)\t\u0005\u0004\u0002\b\u00065\u0015qO\u0007\u0003\u0003\u0013S1!a#p\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003\u001f\u000bII\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BAK\u00037\u00032A\\AL\u0013\r\tIj\u001c\u0002\b\u0005>|G.Z1o\u0011%\tyHOA\u0001\u0002\u0004\t9(\u0001\u0005iCND7i\u001c3f)\t\ti'\u0001\u0005u_N#(/\u001b8h)\t\tI&\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002*B!\u00111LAV\u0013\u0011\ti+!\u0018\u0003\r=\u0013'.Z2u\u0005\u001d\u0011Vm\u001d;beR\u001cBaH7\u0002.\u0005IAn\\4MKZ,G\u000e\t\u000b\u0005\u0003o\u000bI\fE\u0002\u00020}AQA\u001f\u0012A\u0002qL#aH\u000b\u0014\u000fU\t9,!\r\u00028Q\u0011\u0011\u0011\u0019\t\u0004\u0003_)B\u0003BA<\u0003\u000bD\u0011\"a \u001a\u0003\u0003\u0005\r!!\u001c\u0015\t\u0005U\u0015\u0011\u001a\u0005\n\u0003\u007fZ\u0012\u0011!a\u0001\u0003oB3aHA\u000e\u0005\u0019\u0011Vm];nKN!\u0001#\\A\u0017)\u0011\t\u0019.!6\u0011\u0007\u0005=\u0002\u0003C\u0003{'\u0001\u0007A0\u000b\u0002\u0011\rM9a!a5\u00022\u0005]BCAAo!\r\tyC\u0002\u000b\u0005\u0003o\n\t\u000fC\u0005\u0002\u0000)\t\t\u00111\u0001\u0002nQ!\u0011QSAs\u0011%\ty\bDA\u0001\u0002\u0004\t9\bK\u0002\u0011\u00037\u0011Aa\u0015;paN!a&\\A\u0017)\u0011\ty/!=\u0011\u0007\u0005=b\u0006C\u0003{c\u0001\u0007A0\u000b\u0002/IM9A%a<\u00022\u0005]BCAA}!\r\ty\u0003\n\u000b\u0005\u0003o\ni\u0010C\u0005\u0002\u0000!\n\t\u00111\u0001\u0002nQ!\u0011Q\u0013B\u0001\u0011%\tyHKA\u0001\u0002\u0004\t9\bK\u0002/\u00037\taAU3tk6,\u0017a\u0002*fgR\f'\u000f^\u0001\u0005'R|\u0007/\u0001\u0005Fg\u000e\fG.\u0019;f\u0003\u0019\u0011Xm];nKV\u0011\u0011Q\u001c\u000b\u0005\u0003[\u0011\u0019\u0002C\u0003{\u007f\u0001\u0007A0A\u0004sKN$\u0018M\u001d;\u0016\u0005\u0005\u0005G\u0003BA\u0017\u00057AQA_!A\u0002q\fAa\u001d;paV\u0011\u0011\u0011 \u000b\u0005\u0003[\u0011\u0019\u0003C\u0003{\u0007\u0002\u0007A0\u0001\u0005fg\u000e\fG.\u0019;f+\t\tY%\u0001\beK\u001a\fW\u000f\u001c;EK\u000eLG-\u001a:\u0016\u0005\t5\u0002cAA\u0018\u0019\n9A)Z2jI\u0016\u0014\bc\u00028\u00034\t]\u0012QF\u0005\u0004\u0005ky'a\u0004)beRL\u0017\r\u001c$v]\u000e$\u0018n\u001c8\u0011\t\u0005e\"\u0011H\u0005\u0005\u0005w\t9EA\u0005UQJ|w/\u00192mK\u0006yA-\u001a4bk2$H)Z2jI\u0016\u0014\b%A\beK\u001a\fW\u000f\u001c;TiJ\fG/Z4z+\t\u0011\u0019\u0005\u0005\u0002k1N\u0011\u0001,\u001c\u000b\u0003\u0005\u0007\nq\u0001Z3dS\u0012,'/\u0006\u0002\u0003NA\u0019!q\n'\u000f\u0005)\u0004\u0011!\u00065b]\u0012dWm\u00115jY\u0012$VM]7j]\u0006$X\r\u001a\u000b\t\u0005+\u0012YF!\u001a\u0003pA\u0019aNa\u0016\n\u0007\tesN\u0001\u0003V]&$\bb\u0002B/7\u0002\u0007!qL\u0001\bG>tG/\u001a=u!\rQ'\u0011M\u0005\u0004\u0005G\"'\u0001D!di>\u00148i\u001c8uKb$\bb\u0002B47\u0002\u0007!\u0011N\u0001\u0006G\"LG\u000e\u001a\t\u0004U\n-\u0014b\u0001B7I\nA\u0011i\u0019;peJ+g\rC\u0004\u0003rm\u0003\rAa\u001d\u0002\u0011\rD\u0017\u000e\u001c3sK:\u0004b!!\u000f\u0003v\t%\u0014\u0002\u0002B<\u0003\u000f\u0012\u0001\"\u0013;fe\u0006\u0014G.Z\u0001\u000faJ|7-Z:t\r\u0006LG.\u001e:f)9\u0011)F! \u0003\u0000\t\u0005%1\u0011BD\u0005#CqA!\u0018]\u0001\u0004\u0011y\u0006C\u0004\u0003\u0016q\u0003\r!!&\t\u000f\t\u001dD\f1\u0001\u0003j!9!Q\u0011/A\u0002\t]\u0012!B2bkN,\u0007b\u0002BE9\u0002\u0007!1R\u0001\u0006gR\fGo\u001d\t\u0004U\n5\u0015b\u0001BHI\n\t2\t[5mIJ+7\u000f^1siN#\u0018\r^:\t\u000f\tED\f1\u0001\u0003\u0014B1\u0011\u0011\bB;\u0005\u0017\u000bQ\u0002[1oI2,g)Y5mkJ,G\u0003DAK\u00053\u0013YJ!(\u0003 \n\u0005\u0006b\u0002B/;\u0002\u0007!q\f\u0005\b\u0005Oj\u0006\u0019\u0001B5\u0011\u001d\u0011))\u0018a\u0001\u0005oAqA!#^\u0001\u0004\u0011Y\tC\u0004\u0003ru\u0003\rAa%\u0002\u001d1|wmZ5oO\u0016s\u0017M\u00197fIV\u0011\u0011QS\u0001\u000bY><g)Y5mkJ,GC\u0003B+\u0005W\u0013iKa,\u00032\"9!QL0A\u0002\t}\u0003b\u0002B4?\u0002\u0007!\u0011\u000e\u0005\b\u0005\u000b{\u0006\u0019\u0001B\u001c\u0011\u001d\u0011\u0019l\u0018a\u0001\u0005k\u000b\u0001\u0002Z3dSNLwN\u001c\t\u0004\u0005\u001f\u001a\u0011a\u00029vE2L7\u000f\u001b\u000b\u0007\u0005+\u0012YL!0\t\u000f\tu\u0003\r1\u0001\u0003`!9!q\u00181A\u0002\t\u0005\u0017\u0001\u00037pO\u00163XM\u001c;\u0011\u0007u\u0014\u0019-\u0003\u0003\u0003F\u0006]!\u0001\u0003'pO\u00163XM\u001c;\u0002\u0017I,7/^7f\u0007\"LG\u000e\u001a\u000b\u0007\u0005+\u0012YM!4\t\u000f\t\u001d\u0014\r1\u0001\u0003j!9!QQ1A\u0002\t]\u0012\u0001\u0004:fgR\f'\u000f^\"iS2$G\u0003\u0003B+\u0005'\u0014)Na6\t\u000f\t\u001d$\r1\u0001\u0003j!9!Q\u00112A\u0002\t]\u0002b\u0002BmE\u0002\u0007\u0011QS\u0001\rgV\u001c\b/\u001a8e\r&\u00148\u000f^\u0001\u0011I\u00164\u0017-\u001e7u'R\u0014\u0018\r^3hs\u0002\n\u0001c\u001d;paBLgnZ*ue\u0006$XmZ=\u0002#M$x\u000e\u001d9j]\u001e\u001cFO]1uK\u001eL\b%\u0001\u000btKF$\u0006N]8xC\ndWM\r#fG&$WM\u001d\u000b\u0005\u0005[\u0011)\u000fC\u0004\u0003h.\u0003\rA!;\u0002\u0011Q\u0014\u0018\r]#ySR\u0004bAa;\u0003r\nUXB\u0001Bw\u0015\u0011\u0011y/!#\u0002\u0013%lW.\u001e;bE2,\u0017\u0002\u0002Bz\u0005[\u00141aU3ra\u0011\u00119pa\u0003\u0011\r\te8\u0011AB\u0004\u001d\u0011\u0011YP!@\u0011\u0007\u0005\u0005q.C\u0002\u0003\u0000>\fa\u0001\u0015:fI\u00164\u0017\u0002BB\u0002\u0007\u000b\u0011Qa\u00117bgNT1Aa@p!\u0011\u0019Iaa\u0003\r\u0001\u0011a1Q\u0002Bs\u0003\u0003\u0005\tQ!\u0001\u0004\u0010\t\u0019q\fJ\u0019\u0012\t\u0005=#q\u0007\u0002\t\u0015\u0012+7-\u001b3feBA1QCB\u000e\u0005o\ti#\u0004\u0002\u0004\u0018)\u00191\u0011\u00044\u0002\t)\f\u0007/[\u0005\u0005\u0007;\u00199B\u0001\u0005Gk:\u001cG/[8o\u00059\u0019\u0015-^:f\t&\u0014Xm\u0019;jm\u0016\u0004rA\\B\u0012\u0007O\ti#C\u0002\u0004&=\u0014a\u0001V;qY\u0016\u0014\u0004\u0007BB\u0015\u0007[\u0001bA!?\u0004\u0002\r-\u0002\u0003BB\u0005\u0007[!1ba\fO\u0003\u0003\u0005\tQ!\u0001\u0004\u0010\t\u0019q\f\n\u001a\u0002\u00175\f7.\u001a#fG&$WM\u001d\u000b\u0005\u0005[\u0019)\u0004C\u0004\u0003h>\u0003\raa\u000e\u0011\r\t-(\u0011_B\u001da\u0011\u0019Yda\u0010\u0011\r\te8\u0011AB\u001f!\u0011\u0019Iaa\u0010\u0005\u0019\r\u00053QGA\u0001\u0002\u0003\u0015\taa\u0004\u0003\u0007}#3\u0007\u0006\u0003\u0003.\r\u0015\u0003b\u0002Bt!\u0002\u00071q\t\t\u0007\u00037\u001aIea\u0013\n\t\t]\u0014Q\f\u0019\u0005\u0007\u001b\u001a\t\u0006\u0005\u0004\u0003z\u000e\u00051q\n\t\u0005\u0007\u0013\u0019\t\u0006\u0002\u0007\u0004T\r\u0015\u0013\u0011!A\u0001\u0006\u0003\u0019yAA\u0002`IQ\"BA!\f\u0004X!91\u0011L)A\u0002\rm\u0013\u0001\u00024mCR\u0004b!!\u000f\u0003v\ru\u0003cAA\u0018\u001dR!!QFB1\u0011\u001d\u0019\u0019G\u0015a\u0001\u0007K\nAAZ;oGB\u0019\u0011qF'\u0002\tM|'\u000f\u001e\u000b\u0005\u0007W\u001ai\u0007\u0005\u0004\u0003l\nE8Q\f\u0005\b\u0007_\u001a\u0006\u0019AB.\u0003\tIg.A\u000bxSRD\u0017N\u001c+j[\u0016\u0014\u0016M\\4f\u001fB$\u0018n\u001c8\u0015\t\rU41\u0012\t\u0006]\u000e]41P\u0005\u0004\u0007sz'AB(qi&|g\u000e\u0005\u0003\u0004~\r\u001dUBAB@\u0015\u0011\u0019\tia!\u0002\u0011\u0011,(/\u0019;j_:T1a!\"p\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0005\u0007\u0013\u001byH\u0001\u0005EkJ\fG/[8o\u0011\u001d\u0019i\t\u0016a\u0001\u0007w\nqb^5uQ&tG+[7f%\u0006tw-Z\u0001\u0015[\u0006DhJ](g%\u0016$(/[3t\u001fB$\u0018n\u001c8\u0015\t\rM5Q\u0013\t\u0006]\u000e]\u0014Q\u000e\u0005\b\u0007/+\u0006\u0019AA7\u00039i\u0017\r\u001f(s\u001f\u001a\u0014V\r\u001e:jKN\fq\"Z:dC2\fG/\u001a#fM\u0006,H\u000e^\u000b\u0003\u0007;\u0003rA\\BP\u0003o\u001a\u0019+C\u0002\u0004\">\u0014\u0011BR;oGRLwN\\\u0019\u000f\u0007\u0005=\"'\u0001\tfg\u000e\fG.\u0019;f\t\u00164\u0017-\u001e7uA\u0001")
public abstract class SupervisorStrategy {
    public static PartialFunction<Throwable, Directive> makeDecider(Function<Throwable, Directive> func) {
        return SupervisorStrategy$.MODULE$.makeDecider(func);
    }

    public static PartialFunction<Throwable, Directive> makeDecider(Iterable<Tuple2<Class<? extends Throwable>, Directive>> flat) {
        return SupervisorStrategy$.MODULE$.makeDecider(flat);
    }

    public static PartialFunction<Throwable, Directive> makeDecider(java.lang.Iterable<Class<? extends Throwable>> trapExit) {
        return SupervisorStrategy$.MODULE$.makeDecider(trapExit);
    }

    public static PartialFunction<Throwable, Directive> makeDecider(Seq<Class<? extends Throwable>> trapExit) {
        return SupervisorStrategy$.MODULE$.makeDecider(trapExit);
    }

    public static PartialFunction<Throwable, Directive> seqThrowable2Decider(Seq<Class<? extends Throwable>> trapExit) {
        return SupervisorStrategy$.MODULE$.seqThrowable2Decider(trapExit);
    }

    public static SupervisorStrategy stoppingStrategy() {
        return SupervisorStrategy$.MODULE$.stoppingStrategy();
    }

    public static SupervisorStrategy defaultStrategy() {
        return SupervisorStrategy$.MODULE$.defaultStrategy();
    }

    public static PartialFunction<Throwable, Directive> defaultDecider() {
        return SupervisorStrategy$.MODULE$.defaultDecider();
    }

    public static SupervisorStrategy$Escalate$ escalate() {
        return SupervisorStrategy$.MODULE$.escalate();
    }

    public static Directive stop(int logLevel) {
        return SupervisorStrategy$.MODULE$.stop(logLevel);
    }

    public static SupervisorStrategy$Stop$ stop() {
        return SupervisorStrategy$.MODULE$.stop();
    }

    public static Directive restart(int logLevel) {
        return SupervisorStrategy$.MODULE$.restart(logLevel);
    }

    public static SupervisorStrategy$Restart$ restart() {
        return SupervisorStrategy$.MODULE$.restart();
    }

    public static Directive resume(int logLevel) {
        return SupervisorStrategy$.MODULE$.resume(logLevel);
    }

    public static SupervisorStrategy$Resume$ resume() {
        return SupervisorStrategy$.MODULE$.resume();
    }

    public static PartialFunction<Throwable, Directive> seqCauseDirective2Decider(Iterable<Tuple2<Class<? extends Throwable>, Directive>> trapExit) {
        return SupervisorStrategy$.MODULE$.seqCauseDirective2Decider(trapExit);
    }

    public abstract PartialFunction<Throwable, Directive> decider();

    public abstract void handleChildTerminated(ActorContext var1, ActorRef var2, Iterable<ActorRef> var3);

    public abstract void processFailure(ActorContext var1, boolean var2, ActorRef var3, Throwable var4, ChildRestartStats var5, Iterable<ChildRestartStats> var6);

    public boolean handleFailure(ActorContext context, ActorRef child, Throwable cause, ChildRestartStats stats, Iterable<ChildRestartStats> children) {
        Directive directive = this.decider().applyOrElse(cause, SupervisorStrategy$.MODULE$.escalateDefault());
        Directive directive2 = directive;
        if (directive2 instanceof Resume) {
            this.logFailure(context, child, cause, directive);
            this.resumeChild(child, cause);
            return true;
        }
        if (directive2 instanceof Restart) {
            this.logFailure(context, child, cause, directive);
            this.processFailure(context, true, child, cause, stats, children);
            return true;
        }
        if (directive2 instanceof Stop) {
            this.logFailure(context, child, cause, directive);
            this.processFailure(context, false, child, cause, stats, children);
            return true;
        }
        if (SupervisorStrategy$Escalate$.MODULE$.equals(directive2)) {
            this.logFailure(context, child, cause, directive);
            return false;
        }
        throw new MatchError(directive2);
    }

    public boolean loggingEnabled() {
        return true;
    }

    public void logFailure(ActorContext context, ActorRef child, Throwable cause, Directive decision) {
        if (this.loggingEnabled()) {
            InvocationTargetException invocationTargetException;
            Throwable throwable;
            ActorInitializationException actorInitializationException;
            Throwable throwable2 = cause;
            String logMessage = throwable2 instanceof ActorInitializationException && (actorInitializationException = (ActorInitializationException)throwable2).getCause() != null ? ((throwable = actorInitializationException.getCause()) instanceof InvocationTargetException && (invocationTargetException = (InvocationTargetException)throwable).getCause() != null ? invocationTargetException.getCause().getMessage() : throwable.getMessage()) : throwable2.getMessage();
            Directive directive = decision;
            if (SupervisorStrategy$Escalate$.MODULE$.equals(directive)) {
                return;
            }
            if (directive.logLevel() == Logging$.MODULE$.ErrorLevel()) {
                this.publish(context, new Logging.Error(cause, child.path().toString(), this.getClass(), logMessage));
                return;
            }
            this.publish(context, Logging$LogEvent$.MODULE$.apply(directive.logLevel(), child.path().toString(), this.getClass(), logMessage));
            return;
        }
    }

    private void publish(ActorContext context, Logging.LogEvent logEvent) {
        try {
            context.system().eventStream().publish(logEvent);
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
            }
            throw throwable;
        }
    }

    public final void resumeChild(ActorRef child, Throwable cause) {
        ((InternalActorRef)child).resume(cause);
    }

    public final void restartChild(ActorRef child, Throwable cause, boolean suspendFirst) {
        InternalActorRef c = (InternalActorRef)child;
        if (suspendFirst) {
            c.suspend();
        }
        c.restart(cause);
    }

    public static interface Directive {
        @InternalApi
        public int logLevel();
    }

    @InternalApi
    public static class Restart
    implements Directive {
        private final int logLevel;

        @Override
        public int logLevel() {
            return this.logLevel;
        }

        public Restart(int logLevel) {
            this.logLevel = logLevel;
        }
    }

    @InternalApi
    public static class Resume
    implements Directive {
        private final int logLevel;

        @Override
        public int logLevel() {
            return this.logLevel;
        }

        public Resume(int logLevel) {
            this.logLevel = logLevel;
        }
    }

    @InternalApi
    public static class Stop
    implements Directive {
        private final int logLevel;

        @Override
        public int logLevel() {
            return this.logLevel;
        }

        public Stop(int logLevel) {
            this.logLevel = logLevel;
        }
    }
}

