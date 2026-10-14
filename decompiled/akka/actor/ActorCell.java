/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.AbstractActor;
import akka.actor.Actor;
import akka.actor.ActorCell$;
import akka.actor.ActorContext;
import akka.actor.ActorIdentity;
import akka.actor.ActorInitializationException;
import akka.actor.ActorInitializationException$;
import akka.actor.ActorKilledException$;
import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorRefFactory;
import akka.actor.ActorRefProvider;
import akka.actor.ActorSelection;
import akka.actor.ActorSelection$;
import akka.actor.ActorSelectionMessage;
import akka.actor.ActorSystemImpl;
import akka.actor.Address;
import akka.actor.AddressTerminated;
import akka.actor.AutoReceivedMessage;
import akka.actor.Cancellable;
import akka.actor.Cell;
import akka.actor.ChildRestartStats;
import akka.actor.ChildStats;
import akka.actor.FunctionRef;
import akka.actor.Identify;
import akka.actor.InternalActorRef;
import akka.actor.Kill$;
import akka.actor.PoisonPill$;
import akka.actor.Props;
import akka.actor.RepointableActorRef;
import akka.actor.Terminated;
import akka.actor.dungeon.Children;
import akka.actor.dungeon.ChildrenContainer;
import akka.actor.dungeon.DeathWatch;
import akka.actor.dungeon.Dispatch;
import akka.actor.dungeon.FaultHandling;
import akka.actor.dungeon.ReceiveTimeout;
import akka.annotation.InternalStableApi;
import akka.dispatch.Envelope;
import akka.dispatch.Envelope$;
import akka.dispatch.Mailbox;
import akka.dispatch.MailboxType;
import akka.dispatch.MessageDispatcher;
import akka.dispatch.sysmsg.Create;
import akka.dispatch.sysmsg.DeathWatchNotification;
import akka.dispatch.sysmsg.EarliestFirstSystemMessageList$;
import akka.dispatch.sysmsg.Failed;
import akka.dispatch.sysmsg.LatestFirstSystemMessageList$;
import akka.dispatch.sysmsg.NoMessage$;
import akka.dispatch.sysmsg.Recreate;
import akka.dispatch.sysmsg.Resume;
import akka.dispatch.sysmsg.StashWhenFailed;
import akka.dispatch.sysmsg.StashWhenWaitingForChildren;
import akka.dispatch.sysmsg.Supervise;
import akka.dispatch.sysmsg.Suspend;
import akka.dispatch.sysmsg.SystemMessage;
import akka.dispatch.sysmsg.SystemMessageList$;
import akka.dispatch.sysmsg.Terminate;
import akka.dispatch.sysmsg.Unwatch;
import akka.dispatch.sysmsg.Watch;
import akka.event.Logging;
import akka.event.Logging$Error$;
import akka.japi.Procedure;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Optional;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.PartialFunction;
import scala.Predef$;
import scala.Some;
import scala.Tuple2;
import scala.collection.IterableOps;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Set;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.duration.Duration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\ruuAB(Q\u0011\u0003\u0011FK\u0002\u0004W!\"\u0005!k\u0016\u0005\u0006=\u0006!\t\u0001\u0019\u0005\bC\u0006\u0011\r\u0011\"\u0001c\u0011\u0019Q\u0018\u0001)A\u0005G\"910\u0001b\u0001\n\u000ba\bbBA\u0001\u0003\u0001\u0006i! \u0005\n\u0003\u0007\t!\u0019!C\u0003\u0003\u000bA\u0001\"a\u0006\u0002A\u00035\u0011q\u0001\u0005\n\u00033\t!\u0019!C\u0003\u00037A\u0001\"a\r\u0002A\u00035\u0011Q\u0004\u0005\n\u0003k\t!\u0019!C\u0003\u0003oA\u0001\"a\u0010\u0002A\u00035\u0011\u0011\b\u0005\n\u0003\u0003\n!\u0019!C\u0003\u0003\u0007B\u0001\"!\u0013\u0002A\u00035\u0011Q\t\u0005\b\u0003\u0017\nAQAA'\u0011\u001d\t\u0019'\u0001C\u0003\u0003KB\u0011\"a\u001e\u0002\u0005\u0004%)!a\u0011\t\u0011\u0005e\u0014\u0001)A\u0007\u0003\u000bB\u0011\"a\u001f\u0002\u0005\u0004%)!! \t\u0011\u0005\r\u0015\u0001)A\u0007\u0003\u007fB\u0011\"!\"\u0002\u0005\u0004%)!a\"\t\u0011\u00055\u0015\u0001)A\u0007\u0003\u00133aA\u0016)\u0001%\u0006=\u0005BCAd/\t\u0015\r\u0011\"\u0001\u0002J\"Q\u0011\u0011[\f\u0003\u0002\u0003\u0006I!a3\t\u0015\u0005MwC!b\u0001\n\u0003\t)\u000e\u0003\u0006\u0002^^\u0011\t\u0011)A\u0005\u0003/D!\"a8\u0018\u0005\u0003\u0005\u000b\u0011BA\u001d\u0011)\t\to\u0006BC\u0002\u0013\u0005\u00111\u001d\u0005\u000b\u0003c<\"\u0011!Q\u0001\n\u0005\u0015\bBCAz/\t\u0015\r\u0011\"\u0001\u0002V\"Q\u0011Q_\f\u0003\u0002\u0003\u0006I!a6\t\ry;B\u0011AA|\u0011!\u0011)a\u0006Q!\n\u0005e\u0002b\u0002B\u0004/\u0011\u0005\u0011q\u0007\u0005\b\u0005\u00139BQ\u0001B\u0006\u0011\u001d\u0011\u0019b\u0006C\u0003\u0003\u0013DqA!\u0006\u0018\t+\t)\u000eC\u0004\u0003\u0018]!)\"!6\t\u000f\teq\u0003\"\u0002\u0003\u001c!9!1E\f\u0005F\t\u0015\u0002b\u0002B\u0014/\u0011E!\u0011\u0006\u0005\f\u0005W9\u0002\u0019!A!B\u0013\u0011i\u0003\u0003\u0004R/\u0011\u0005!1\u0007\u0005\f\u0005k9\u0002\u0019!a\u0001\n\u0003\u00119\u0004C\u0006\u0003@]\u0001\r\u00111A\u0005\u0002\t\u0005\u0003b\u0003B'/\u0001\u0007\t\u0011)Q\u0005\u0005sA\u0011Ba\u0014\u0018\u0001\u0004%I!!\u0002\t\u0013\tEs\u00031A\u0005\n\tM\u0003\u0002\u0003B,/\u0001\u0006K!a\u0002\t\u0011\tes\u0003)Q\u0005\u00057BqAa\u001a\u0018\t\u000b\u0011I\u0007C\u0004\u0003l]!)A!\u001c\t\u000f\t=t\u0003\"\u0012\u0003r!9!qP\f\u0005F\t\u0005\u0005b\u0002BB/\u0011\u0015#Q\u0011\u0005\b\u0005\u000f;B\u0011\u0003BE\u0011\u001d\u0011)j\u0006C\u0005\u0005/CqA!'\u0018\t\u000b\u0011Y\nC\u0004\u0003\"^!)Aa)\t\u000f\t%v\u0003\"\u0001\u0003,\"9!qV\f\u0005\n\tE\u0006b\u0002B_/\u0011\u0015!q\u0018\u0005\b\u0005\u0013<BQ\u0001BA\u0011\u001d\u0011Ym\u0006C\u0001\u0005\u001bD\u0011Ba6\u0018#\u0003%\tA!7\t\u000f\t-w\u0003\"\u0001\u0003l\"9!1Z\f\u0005\u0002\tm\bbBB\u0001/\u0011\u000511\u0001\u0005\b\u0007\u000b9B\u0011CB\u0004\u0011\u001d\u0019Ia\u0006C\t\u0007\u0017Aqa!\b\u0018\t\u0013\u0019y\u0002C\u0004\u0004.]!Iaa\f\t\u000f\rer\u0003\"\u0005\u0004<!91\u0011I\f\u0005\u0016\r\r\u0003bBB-/\u0011U11\u0001\u0005\b\u00077:BQCB/\u0011\u001d\u0019ih\u0006C\u000b\u0007\u007f\n\u0011\"Q2u_J\u001cU\r\u001c7\u000b\u0005E\u0013\u0016!B1di>\u0014(\"A*\u0002\t\u0005\\7.\u0019\t\u0003+\u0006i\u0011\u0001\u0015\u0002\n\u0003\u000e$xN]\"fY2\u001c\"!\u0001-\u0011\u0005ecV\"\u0001.\u000b\u0003m\u000bQa]2bY\u0006L!!\u0018.\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}\r\u0001A#\u0001+\u0002\u0019\r|g\u000e^3yiN#\u0018mY6\u0016\u0003\r\u00042\u0001Z5l\u001b\u0005)'B\u00014h\u0003\u0011a\u0017M\\4\u000b\u0003!\fAA[1wC&\u0011!.\u001a\u0002\f)\"\u0014X-\u00193M_\u000e\fG\u000eE\u0002mi^t!!\u001c:\u000f\u00059\fX\"A8\u000b\u0005A|\u0016A\u0002\u001fs_>$h(C\u0001\\\u0013\t\u0019(,A\u0004qC\u000e\\\u0017mZ3\n\u0005U4(\u0001\u0002'jgRT!a\u001d.\u0011\u0005UC\u0018BA=Q\u00051\t5\r^8s\u0007>tG/\u001a=u\u00035\u0019wN\u001c;fqR\u001cF/Y2lA\u0005\u0001R-\u001c9us\u000e\u000bgnY3mY\u0006\u0014G.Z\u000b\u0002{B\u0011QK`\u0005\u0003\u007fB\u00131bQ1oG\u0016dG.\u00192mK\u0006\tR-\u001c9us\u000e\u000bgnY3mY\u0006\u0014G.\u001a\u0011\u0002%\u0015l\u0007\u000f^=CK\"\fg/[8s'R\f7m[\u000b\u0003\u0003\u000f\u0001B\u0001\u001c;\u0002\nA!\u00111BA\t\u001d\r)\u0016QB\u0005\u0004\u0003\u001f\u0001\u0016!B!di>\u0014\u0018\u0002BA\n\u0003+\u0011qAU3dK&4XMC\u0002\u0002\u0010A\u000b1#Z7qif\u0014U\r[1wS>\u00148\u000b^1dW\u0002\n\u0001#Z7qif\f5\r^8s%\u001647+\u001a;\u0016\u0005\u0005u\u0001CBA\u0010\u0003O\tiC\u0004\u0003\u0002\"\u0005\r\u0002C\u00018[\u0013\r\t)CW\u0001\u0007!J,G-\u001a4\n\t\u0005%\u00121\u0006\u0002\u0004'\u0016$(bAA\u00135B\u0019Q+a\f\n\u0007\u0005E\u0002K\u0001\u0005BGR|'OU3g\u0003E)W\u000e\u001d;z\u0003\u000e$xN\u001d*fMN+G\u000fI\u0001\u0010i\u0016\u0014X.\u001b8bi\u0016$\u0007K]8qgV\u0011\u0011\u0011\b\t\u0004+\u0006m\u0012bAA\u001f!\n)\u0001K]8qg\u0006\u0001B/\u001a:nS:\fG/\u001a3Qe>\u00048\u000fI\u0001\rk:$WMZ5oK\u0012,\u0016\u000eZ\u000b\u0003\u0003\u000bz!!a\u0012\u001e\u0003\u0001\tQ\"\u001e8eK\u001aLg.\u001a3VS\u0012\u0004\u0013A\u00028foVKG\r\u0006\u0002\u0002PA\u0019\u0011,!\u0015\n\u0007\u0005M#LA\u0002J]RD3aDA,!\u0011\tI&a\u0018\u000e\u0005\u0005m#bAA/5\u0006Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005\u0005\u00141\f\u0002\bi\u0006LGN]3d\u0003=\u0019\b\u000f\\5u\u001d\u0006lW-\u00118e+&$G\u0003BA4\u0003g\u0002r!WA5\u0003[\ny%C\u0002\u0002li\u0013a\u0001V;qY\u0016\u0014\u0004\u0003BA\u0010\u0003_JA!!\u001d\u0002,\t11\u000b\u001e:j]\u001eDq!!\u001e\u0011\u0001\u0004\ti'\u0001\u0003oC6,\u0017\u0001\u0004#fM\u0006,H\u000e^*uCR,\u0017!\u0004#fM\u0006,H\u000e^*uCR,\u0007%\u0001\bTkN\u0004XM\u001c3fIN#\u0018\r^3\u0016\u0005\u0005}tBAAA;\u0005\t\u0011aD*vgB,g\u000eZ3e'R\fG/\u001a\u0011\u0002;M+8\u000f]3oI\u0016$w+Y5u\r>\u00148\t[5mIJ,gn\u0015;bi\u0016,\"!!#\u0010\u0005\u0005-U$\u0001\u0002\u0002=M+8\u000f]3oI\u0016$w+Y5u\r>\u00148\t[5mIJ,gn\u0015;bi\u0016\u00043\u0003E\fY\u0003#\u000bi*a)\u00020\u0006U\u00161XAa!\u0011\t\u0019*!'\u000f\u0007U\u000b)*C\u0002\u0002\u0018B\u000bQ\"\u00112tiJ\f7\r^!di>\u0014\u0018bA=\u0002\u001c*\u0019\u0011q\u0013)\u0011\u0007U\u000by*C\u0002\u0002\"B\u0013AaQ3mYB!\u0011QUAV\u001b\t\t9KC\u0002\u0002*B\u000bq\u0001Z;oO\u0016|g.\u0003\u0003\u0002.\u0006\u001d&A\u0004*fG\u0016Lg/\u001a+j[\u0016|W\u000f\u001e\t\u0005\u0003K\u000b\t,\u0003\u0003\u00024\u0006\u001d&\u0001C\"iS2$'/\u001a8\u0011\t\u0005\u0015\u0016qW\u0005\u0005\u0003s\u000b9K\u0001\u0005ESN\u0004\u0018\r^2i!\u0011\t)+!0\n\t\u0005}\u0016q\u0015\u0002\u000b\t\u0016\fG\u000f[,bi\u000eD\u0007\u0003BAS\u0003\u0007LA!!2\u0002(\nia)Y;mi\"\u000bg\u000e\u001a7j]\u001e\faa]=ti\u0016lWCAAf!\r)\u0016QZ\u0005\u0004\u0003\u001f\u0004&aD!di>\u00148+_:uK6LU\u000e\u001d7\u0002\u000fML8\u000f^3nA\u0005!1/\u001a7g+\t\t9\u000eE\u0002V\u00033L1!a7Q\u0005AIe\u000e^3s]\u0006d\u0017i\u0019;peJ+g-A\u0003tK24\u0007%A\u0007`S:LG/[1m!J|\u0007o]\u0001\u000bI&\u001c\b/\u0019;dQ\u0016\u0014XCAAs!\u0011\t9/!<\u000e\u0005\u0005%(bAAv%\u0006AA-[:qCR\u001c\u0007.\u0003\u0003\u0002p\u0006%(!E'fgN\fw-\u001a#jgB\fGo\u00195fe\u0006YA-[:qCR\u001c\u0007.\u001a:!\u0003\u0019\u0001\u0018M]3oi\u00069\u0001/\u0019:f]R\u0004C\u0003DA}\u0003w\fi0a@\u0003\u0002\t\r\u0001CA+\u0018\u0011\u001d\t9-\ta\u0001\u0003\u0017Dq!a5\"\u0001\u0004\t9\u000eC\u0004\u0002`\u0006\u0002\r!!\u000f\t\u000f\u0005\u0005\u0018\u00051\u0001\u0002f\"9\u00111_\u0011A\u0002\u0005]\u0017AB0qe>\u00048/A\u0003qe>\u00048/A\u0004jg2{7-\u00197\u0016\u0005\t5\u0001cA-\u0003\u0010%\u0019!\u0011\u0003.\u0003\u000f\t{w\u000e\\3b]\u0006Q1/_:uK6LU\u000e\u001d7\u0002\u0011\u001d,\u0018M\u001d3jC:\f!\u0002\\8pWV\u0004(k\\8u\u0003!\u0001(o\u001c<jI\u0016\u0014XC\u0001B\u000f!\r)&qD\u0005\u0004\u0005C\u0001&\u0001E!di>\u0014(+\u001a4Qe>4\u0018\u000eZ3s\u0003M\u0019G.Y:tS\u000e\f5\r^8s\u0007>tG/\u001a=u+\u00059\u0018aA;jIV\u0011\u0011qJ\u0001\u0007?\u0006\u001cGo\u001c:\u0011\u0007U\u0013y#C\u0002\u00032A\u0013Q!Q2u_J,\"A!\f\u0002\u001d\r,(O]3oi6+7o]1hKV\u0011!\u0011\b\t\u0005\u0003O\u0014Y$\u0003\u0003\u0003>\u0005%(\u0001C#om\u0016dw\u000e]3\u0002%\r,(O]3oi6+7o]1hK~#S-\u001d\u000b\u0005\u0005\u0007\u0012I\u0005E\u0002Z\u0005\u000bJ1Aa\u0012[\u0005\u0011)f.\u001b;\t\u0013\t-c&!AA\u0002\te\u0012a\u0001=%c\u0005y1-\u001e:sK:$X*Z:tC\u001e,\u0007%A\u0007cK\"\fg/[8s'R\f7m[\u0001\u0012E\u0016D\u0017M^5peN#\u0018mY6`I\u0015\fH\u0003\u0002B\"\u0005+B\u0011Ba\u00132\u0003\u0003\u0005\r!a\u0002\u0002\u001d\t,\u0007.\u0019<j_J\u001cF/Y2lA\u0005Y1/_:ng\u001e\u001cF/Y:i!\u0011\u0011iFa\u0019\u000e\u0005\t}#\u0002\u0002B1\u0003S\faa]=t[N<\u0017\u0002\u0002B3\u0005?\u0012A\u0004T1uKN$h)\u001b:tiNK8\u000f^3n\u001b\u0016\u001c8/Y4f\u0019&\u001cH/A\u0005hKR\u0004\u0016M]3oiR\u0011\u0011q[\u0001\nO\u0016$8+_:uK6$\"!a3\u0002\u001b\u001d,G\u000fR5ta\u0006$8\r[3s)\t\u0011\u0019\b\u0005\u0003\u0003v\tmTB\u0001B<\u0015\r\u0011IHW\u0001\u000bG>t7-\u001e:sK:$\u0018\u0002\u0002B?\u0005o\u0012\u0001$\u0012=fGV$\u0018n\u001c8D_:$X\r\u001f;Fq\u0016\u001cW\u000f^8s\u0003\u001d9W\r^*fY\u001a$\"!!\f\u0002\u0011\u001d,G\u000f\u0015:paN$\"!!\u000f\u0002\u000bM$\u0018m\u001d5\u0015\t\t\r#1\u0012\u0005\b\u0005\u001bK\u0004\u0019\u0001BH\u0003\ri7o\u001a\t\u0005\u0005;\u0012\t*\u0003\u0003\u0003\u0014\n}#!D*zgR,W.T3tg\u0006<W-\u0001\u0006v]N$\u0018m\u001d5BY2$\"Aa\u0017\u0002\u0019ML8\u000f^3n\u0013:4xn[3\u0015\t\t\r#Q\u0014\u0005\b\u0005?[\u0004\u0019\u0001BH\u0003\u001diWm]:bO\u0016\fa!\u001b8w_.,G\u0003\u0002B\"\u0005KCqAa*=\u0001\u0004\u0011I$A\u0007nKN\u001c\u0018mZ3IC:$G.Z\u0001\u0013CV$xNU3dK&4X-T3tg\u0006<W\r\u0006\u0003\u0003D\t5\u0006b\u0002BG{\u0001\u0007!\u0011H\u0001\u0011e\u0016\u001cW-\u001b<f'\u0016dWm\u0019;j_:$BAa\u0011\u00034\"9!Q\u0017 A\u0002\t]\u0016aA:fYB\u0019QK!/\n\u0007\tm\u0006KA\u000bBGR|'oU3mK\u000e$\u0018n\u001c8NKN\u001c\u0018mZ3\u0002\u001dI,7-Z5wK6+7o]1hKR!!1\tBa\u0011\u001d\u0011ii\u0010a\u0001\u0005\u0007\u00042!\u0017Bc\u0013\r\u00119M\u0017\u0002\u0004\u0003:L\u0018AB:f]\u0012,'/\u0001\u0004cK\u000e|W.\u001a\u000b\u0007\u0005\u0007\u0012yMa5\t\u000f\tE\u0017\t1\u0001\u0002\n\u0005A!-\u001a5bm&|'\u000fC\u0005\u0003V\u0006\u0003\n\u00111\u0001\u0003\u000e\u0005QA-[:dCJ$w\n\u001c3\u0002!\t,7m\\7fI\u0011,g-Y;mi\u0012\u0012TC\u0001BnU\u0011\u0011iA!8,\u0005\t}\u0007\u0003\u0002Bq\u0005Ol!Aa9\u000b\t\t\u0015\u00181L\u0001\nk:\u001c\u0007.Z2lK\u0012LAA!;\u0003d\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0015\t\t\r#Q\u001e\u0005\b\u0005#\u001c\u0005\u0019\u0001Bx!\u0019\u0011\tPa>\u0003D6\u0011!1\u001f\u0006\u0004\u0005k\u0014\u0016\u0001\u00026ba&LAA!?\u0003t\nI\u0001K]8dK\u0012,(/\u001a\u000b\u0007\u0005\u0007\u0012iPa@\t\u000f\tEG\t1\u0001\u0003p\"9!Q\u001b#A\u0002\t5\u0011\u0001C;oE\u0016\u001cw.\\3\u0015\u0005\t\r\u0013\u0001\u00038fo\u0006\u001bGo\u001c:\u0015\u0005\t5\u0012AB2sK\u0006$X\r\u0006\u0003\u0003D\r5\u0001bBB\b\u000f\u0002\u00071\u0011C\u0001\bM\u0006LG.\u001e:f!\u0015I61CB\f\u0013\r\u0019)B\u0017\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u0007U\u001bI\"C\u0002\u0004\u001cA\u0013A$Q2u_JLe.\u001b;jC2L'0\u0019;j_:,\u0005pY3qi&|g.A\u0006s_>$8)Y;tK>3G\u0003BB\u0011\u0007O\u00012\u0001\\B\u0012\u0013\r\u0019)C\u001e\u0002\n)\"\u0014xn^1cY\u0016Dqa!\u000bI\u0001\u0004\u0019\t#A\u0005uQJ|w/\u00192mK\"\u001a\u0001*a\u0016\u0002\u0013M,\b/\u001a:wSN,GC\u0002B\"\u0007c\u0019)\u0004C\u0004\u00044%\u0003\r!!\f\u0002\u000b\rD\u0017\u000e\u001c3\t\u000f\r]\u0012\n1\u0001\u0003\u000e\u0005)\u0011m]=oG\u0006y\u0001.\u00198eY\u0016\u001cV\u000f]3sm&\u001cX\r\u0006\u0004\u0003D\ru2q\b\u0005\b\u0007gQ\u0005\u0019AA\u0017\u0011\u001d\u00199D\u0013a\u0001\u0005\u001b\t\u0001c\u00197fCJ\f5\r^8s\r&,G\u000eZ:\u0015\r\t\r3QIB%\u0011\u001d\u00199e\u0013a\u0001\u0005[\tQ\"Y2u_JLen\u001d;b]\u000e,\u0007bBB&\u0017\u0002\u0007!QB\u0001\te\u0016\u001c'/Z1uK\"\u001a1ja\u0014\u0011\t\rE3QK\u0007\u0003\u0007'R1!!\u0018S\u0013\u0011\u00199fa\u0015\u0003#%sG/\u001a:oC2\u001cF/\u00192mK\u0006\u0003\u0018.A\rdY\u0016\f'OR5fY\u0012\u001chi\u001c:UKJl\u0017N\\1uS>t\u0017a\u00029vE2L7\u000f\u001b\u000b\u0005\u0005\u0007\u001ay\u0006C\u0004\u0004b5\u0003\raa\u0019\u0002\u0003\u0015\u0004Ba!\u001a\u0004x9!1qMB9\u001d\u0011\u0019Ig!\u001c\u000f\u00079\u001cY'C\u0001T\u0013\r\u0019yGU\u0001\u0006KZ,g\u000e^\u0005\u0005\u0007g\u001a)(A\u0004M_\u001e<\u0017N\\4\u000b\u0007\r=$+\u0003\u0003\u0004z\rm$\u0001\u0003'pO\u00163XM\u001c;\u000b\t\rM4QO\u0001\u0006G2\f'P\u001f\u000b\u0005\u0007\u0003\u001bI\n\r\u0003\u0004\u0004\u000e5\u0005CBA\u0010\u0007\u000b\u001bI)\u0003\u0003\u0004\b\u0006-\"!B\"mCN\u001c\b\u0003BBF\u0007\u001bc\u0001\u0001B\u0006\u0004\u0010:\u000b\t\u0011!A\u0003\u0002\rE%aA0%cE!11\u0013Bb!\rI6QS\u0005\u0004\u0007/S&a\u0002(pi\"Lgn\u001a\u0005\u0007\u00077s\u0005\u0019\u0001-\u0002\u0003=\u0004")
public class ActorCell
implements AbstractActor.ActorContext,
Cell,
ReceiveTimeout,
Children,
Dispatch,
DeathWatch,
FaultHandling {
    private final ActorSystemImpl system;
    private final InternalActorRef self;
    private final MessageDispatcher dispatcher;
    private final InternalActorRef parent;
    private Props _props;
    private Actor _actor;
    private Envelope currentMessage;
    private List<PartialFunction<Object, BoxedUnit>> behaviorStack;
    private SystemMessage sysmsgStash;
    private FaultHandling.FailedInfo akka$actor$dungeon$FaultHandling$$_failed;
    private Map<ActorRef, Option<Object>> akka$actor$dungeon$DeathWatch$$watching;
    private Set<ActorRef> akka$actor$dungeon$DeathWatch$$watchedBy;
    private Map<ActorRef, Option<Object>> akka$actor$dungeon$DeathWatch$$terminatedQueued;
    private volatile Mailbox akka$actor$dungeon$Dispatch$$_mailboxDoNotCallMeDirectly;
    private volatile ChildrenContainer akka$actor$dungeon$Children$$_childrenRefsDoNotCallMeDirectly;
    private volatile Map<String, FunctionRef> akka$actor$dungeon$Children$$_functionRefsDoNotCallMeDirectly;
    private volatile long akka$actor$dungeon$Children$$_nextNameDoNotCallMeDirectly;
    private Tuple2<Duration, Cancellable> akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData;

    public static int SuspendedWaitForChildrenState() {
        return ActorCell$.MODULE$.SuspendedWaitForChildrenState();
    }

    public static int SuspendedState() {
        return ActorCell$.MODULE$.SuspendedState();
    }

    public static int DefaultState() {
        return ActorCell$.MODULE$.DefaultState();
    }

    public static Tuple2<String, Object> splitNameAndUid(String name) {
        return ActorCell$.MODULE$.splitNameAndUid(name);
    }

    public static int newUid() {
        return ActorCell$.MODULE$.newUid();
    }

    public static int undefinedUid() {
        return ActorCell$.MODULE$.undefinedUid();
    }

    public static Props terminatedProps() {
        return ActorCell$.MODULE$.terminatedProps();
    }

    public static Set<ActorRef> emptyActorRefSet() {
        return ActorCell$.MODULE$.emptyActorRefSet();
    }

    public static List<PartialFunction<Object, BoxedUnit>> emptyBehaviorStack() {
        return ActorCell$.MODULE$.emptyBehaviorStack();
    }

    public static Cancellable emptyCancellable() {
        return ActorCell$.MODULE$.emptyCancellable();
    }

    public static ThreadLocal<List<ActorContext>> contextStack() {
        return ActorCell$.MODULE$.contextStack();
    }

    @Override
    public void setFailedFatally() {
        FaultHandling.setFailedFatally$(this);
    }

    @Override
    public void faultRecreate(Throwable cause) {
        FaultHandling.faultRecreate$(this, cause);
    }

    @Override
    public void faultSuspend() {
        FaultHandling.faultSuspend$(this);
    }

    @Override
    public void faultResume(Throwable causedByFailure) {
        FaultHandling.faultResume$(this, causedByFailure);
    }

    @Override
    public void faultCreate() {
        FaultHandling.faultCreate$(this);
    }

    @Override
    public void terminate() {
        FaultHandling.terminate$(this);
    }

    @Override
    @InternalStableApi
    public final void handleInvokeFailure(Iterable<ActorRef> childrenNotToSuspend, Throwable t2) {
        FaultHandling.handleInvokeFailure$(this, childrenNotToSuspend, t2);
    }

    @Override
    public final void handleFailure(Failed f) {
        FaultHandling.handleFailure$(this, f);
    }

    @Override
    public final void handleChildTerminated(ActorRef child) {
        FaultHandling.handleChildTerminated$(this, child);
    }

    @Override
    public final PartialFunction<Throwable, BoxedUnit> handleNonFatalOrInterruptedException(Function1<Throwable, BoxedUnit> thunk) {
        return FaultHandling.handleNonFatalOrInterruptedException$(this, thunk);
    }

    @Override
    public boolean isWatching(ActorRef ref) {
        return DeathWatch.isWatching$(this, ref);
    }

    @Override
    public final ActorRef watch(ActorRef subject) {
        return DeathWatch.watch$(this, subject);
    }

    @Override
    public final ActorRef watchWith(ActorRef subject, Object msg) {
        return DeathWatch.watchWith$(this, subject, msg);
    }

    @Override
    public final ActorRef unwatch(ActorRef subject) {
        return DeathWatch.unwatch$(this, subject);
    }

    @Override
    public void receivedTerminated(Terminated t2) {
        DeathWatch.receivedTerminated$(this, t2);
    }

    @Override
    public void watchedActorTerminated(ActorRef actor, boolean existenceConfirmed, boolean addressTerminated) {
        DeathWatch.watchedActorTerminated$(this, actor, existenceConfirmed, addressTerminated);
    }

    @Override
    public void terminatedQueuedFor(ActorRef subject, Option<Object> customMessage) {
        DeathWatch.terminatedQueuedFor$(this, subject, customMessage);
    }

    @Override
    public void tellWatchersWeDied() {
        DeathWatch.tellWatchersWeDied$(this);
    }

    @Override
    public void unwatchWatchedActors(Actor actor) {
        DeathWatch.unwatchWatchedActors$(this, actor);
    }

    @Override
    public void addWatcher(ActorRef watchee, ActorRef watcher) {
        DeathWatch.addWatcher$(this, watchee, watcher);
    }

    @Override
    public void remWatcher(ActorRef watchee, ActorRef watcher) {
        DeathWatch.remWatcher$(this, watchee, watcher);
    }

    @Override
    public void addressTerminated(Address address) {
        DeathWatch.addressTerminated$(this, address);
    }

    @Override
    public final Mailbox mailbox() {
        return Dispatch.mailbox$(this);
    }

    @Override
    public final Mailbox swapMailbox(Mailbox newMailbox) {
        return Dispatch.swapMailbox$(this, newMailbox);
    }

    @Override
    public final boolean hasMessages() {
        return Dispatch.hasMessages$(this);
    }

    @Override
    public final int numberOfMessages() {
        return Dispatch.numberOfMessages$(this);
    }

    @Override
    public final boolean isTerminated() {
        return Dispatch.isTerminated$(this);
    }

    @Override
    public final ActorCell init(boolean sendSupervise, MailboxType mailboxType) {
        return Dispatch.init$(this, sendSupervise, mailboxType);
    }

    @Override
    public final ActorCell initWithFailure(Throwable failure) {
        return Dispatch.initWithFailure$(this, failure);
    }

    @Override
    public ActorCell start() {
        return Dispatch.start$(this);
    }

    @Override
    public final void suspend() {
        Dispatch.suspend$(this);
    }

    @Override
    public final void resume(Throwable causedByFailure) {
        Dispatch.resume$(this, causedByFailure);
    }

    @Override
    public final void restart(Throwable cause) {
        Dispatch.restart$(this, cause);
    }

    @Override
    public final void stop() {
        Dispatch.stop$(this);
    }

    @Override
    public void sendMessage(Envelope msg) {
        Dispatch.sendMessage$(this, msg);
    }

    @Override
    public void sendSystemMessage(SystemMessage message) {
        Dispatch.sendSystemMessage$(this, message);
    }

    @Override
    public ChildrenContainer childrenRefs() {
        return Children.childrenRefs$(this);
    }

    @Override
    public final Iterable<ActorRef> children() {
        return Children.children$(this);
    }

    @Override
    public final java.lang.Iterable<ActorRef> getChildren() {
        return Children.getChildren$(this);
    }

    @Override
    public final Option<ActorRef> child(String name) {
        return Children.child$(this, name);
    }

    @Override
    public final ActorRef getChild(String name) {
        return Children.getChild$(this, name);
    }

    @Override
    public Optional<ActorRef> findChild(String name) {
        return Children.findChild$(this, name);
    }

    @Override
    public ActorRef actorOf(Props props) {
        return Children.actorOf$(this, props);
    }

    @Override
    public ActorRef actorOf(Props props, String name) {
        return Children.actorOf$(this, props, name);
    }

    @Override
    public ActorRef attachChild(Props props, boolean systemService) {
        return Children.attachChild$(this, props, systemService);
    }

    @Override
    public ActorRef attachChild(Props props, String name, boolean systemService) {
        return Children.attachChild$(this, props, name, systemService);
    }

    @Override
    public InternalActorRef getFunctionRefOrNobody(String name, int uid) {
        return Children.getFunctionRefOrNobody$(this, name, uid);
    }

    @Override
    public int getFunctionRefOrNobody$default$2() {
        return Children.getFunctionRefOrNobody$default$2$(this);
    }

    @Override
    public FunctionRef addFunctionRef(Function2<ActorRef, Object, BoxedUnit> f, String name) {
        return Children.addFunctionRef$(this, f, name);
    }

    @Override
    public String addFunctionRef$default$2() {
        return Children.addFunctionRef$default$2$(this);
    }

    @Override
    public boolean removeFunctionRef(FunctionRef ref) {
        return Children.removeFunctionRef$(this, ref);
    }

    @Override
    public void stopFunctionRefs() {
        Children.stopFunctionRefs$(this);
    }

    @Override
    public final String randomName(StringBuilder sb) {
        return Children.randomName$(this, sb);
    }

    @Override
    public final String randomName() {
        return Children.randomName$(this);
    }

    @Override
    public final void stop(ActorRef actor) {
        Children.stop$(this, actor);
    }

    @Override
    public final boolean reserveChild(String name) {
        return Children.reserveChild$(this, name);
    }

    @Override
    public final boolean unreserveChild(String name) {
        return Children.unreserveChild$(this, name);
    }

    @Override
    public final Option<ChildRestartStats> initChild(ActorRef ref) {
        return Children.initChild$(this, ref);
    }

    @Override
    public final boolean setChildrenTerminationReason(ChildrenContainer.SuspendReason reason) {
        return Children.setChildrenTerminationReason$(this, reason);
    }

    @Override
    public final void setTerminated() {
        Children.setTerminated$(this);
    }

    @Override
    public boolean isNormal() {
        return Children.isNormal$(this);
    }

    @Override
    public boolean isTerminating() {
        return Children.isTerminating$(this);
    }

    @Override
    public ChildrenContainer.SuspendReason waitingForChildrenOrNull() {
        return Children.waitingForChildrenOrNull$(this);
    }

    @Override
    @InternalStableApi
    public void suspendChildren(Set<ActorRef> exceptFor) {
        Children.suspendChildren$(this, exceptFor);
    }

    @Override
    public Set<ActorRef> suspendChildren$default$1() {
        return Children.suspendChildren$default$1$(this);
    }

    @Override
    public void resumeChildren(Throwable causedByFailure, ActorRef perp) {
        Children.resumeChildren$(this, causedByFailure, perp);
    }

    @Override
    public Option<ChildStats> getChildByName(String name) {
        return Children.getChildByName$(this, name);
    }

    @Override
    public Option<ChildRestartStats> getChildByRef(ActorRef ref) {
        return Children.getChildByRef$(this, ref);
    }

    @Override
    public Iterable<ChildRestartStats> getAllChildStats() {
        return Children.getAllChildStats$(this);
    }

    @Override
    public InternalActorRef getSingleChild(String name) {
        return Children.getSingleChild$(this, name);
    }

    @Override
    public Option<ChildrenContainer.SuspendReason> removeChildAndGetStateChange(ActorRef child) {
        return Children.removeChildAndGetStateChange$(this, child);
    }

    @Override
    public final Duration receiveTimeout() {
        return ReceiveTimeout.receiveTimeout$(this);
    }

    @Override
    public final void setReceiveTimeout(Duration timeout2) {
        ReceiveTimeout.setReceiveTimeout$(this, timeout2);
    }

    @Override
    public void checkReceiveTimeoutIfNeeded(Object message, Tuple2<Duration, Cancellable> beforeReceive) {
        ReceiveTimeout.checkReceiveTimeoutIfNeeded$(this, message, beforeReceive);
    }

    @Override
    public final void checkReceiveTimeout(boolean reschedule) {
        ReceiveTimeout.checkReceiveTimeout$(this, reschedule);
    }

    @Override
    public Tuple2<Duration, Cancellable> cancelReceiveTimeoutIfNeeded(Object message) {
        return ReceiveTimeout.cancelReceiveTimeoutIfNeeded$(this, message);
    }

    @Override
    public void cancelReceiveTimeoutTask() {
        ReceiveTimeout.cancelReceiveTimeoutTask$(this);
    }

    @Override
    @InternalStableApi
    public final void sendMessage(Object message, ActorRef sender) {
        Cell.sendMessage$(this, message, sender);
    }

    @Override
    public ActorRef getSender() {
        return AbstractActor.ActorContext.getSender$(this);
    }

    @Override
    public void become(AbstractActor.Receive behavior) {
        AbstractActor.ActorContext.become$(this, behavior);
    }

    @Override
    public void become(AbstractActor.Receive behavior, boolean discardOld) {
        AbstractActor.ActorContext.become$(this, behavior, discardOld);
    }

    @Override
    public java.time.Duration getReceiveTimeout() {
        return AbstractActor.ActorContext.getReceiveTimeout$(this);
    }

    @Override
    public void setReceiveTimeout(java.time.Duration timeout2) {
        AbstractActor.ActorContext.setReceiveTimeout$(this, timeout2);
    }

    @Override
    public void cancelReceiveTimeout() {
        AbstractActor.ActorContext.cancelReceiveTimeout$(this);
    }

    @Override
    public void become(PartialFunction<Object, BoxedUnit> behavior) {
        ActorContext.become$(this, behavior);
    }

    @Override
    public final void writeObject(ObjectOutputStream o) {
        ActorContext.writeObject$(this, o);
    }

    @Override
    public ActorSelection actorSelection(String path) {
        return ActorRefFactory.actorSelection$((ActorRefFactory)this, path);
    }

    @Override
    public ActorSelection actorSelection(ActorPath path) {
        return ActorRefFactory.actorSelection$((ActorRefFactory)this, path);
    }

    @Override
    public FaultHandling.FailedInfo akka$actor$dungeon$FaultHandling$$_failed() {
        return this.akka$actor$dungeon$FaultHandling$$_failed;
    }

    @Override
    public void akka$actor$dungeon$FaultHandling$$_failed_$eq(FaultHandling.FailedInfo x$1) {
        this.akka$actor$dungeon$FaultHandling$$_failed = x$1;
    }

    @Override
    public Map<ActorRef, Option<Object>> akka$actor$dungeon$DeathWatch$$watching() {
        return this.akka$actor$dungeon$DeathWatch$$watching;
    }

    @Override
    public void akka$actor$dungeon$DeathWatch$$watching_$eq(Map<ActorRef, Option<Object>> x$1) {
        this.akka$actor$dungeon$DeathWatch$$watching = x$1;
    }

    @Override
    public Set<ActorRef> akka$actor$dungeon$DeathWatch$$watchedBy() {
        return this.akka$actor$dungeon$DeathWatch$$watchedBy;
    }

    @Override
    public void akka$actor$dungeon$DeathWatch$$watchedBy_$eq(Set<ActorRef> x$1) {
        this.akka$actor$dungeon$DeathWatch$$watchedBy = x$1;
    }

    @Override
    public Map<ActorRef, Option<Object>> akka$actor$dungeon$DeathWatch$$terminatedQueued() {
        return this.akka$actor$dungeon$DeathWatch$$terminatedQueued;
    }

    @Override
    public void akka$actor$dungeon$DeathWatch$$terminatedQueued_$eq(Map<ActorRef, Option<Object>> x$1) {
        this.akka$actor$dungeon$DeathWatch$$terminatedQueued = x$1;
    }

    @Override
    public Mailbox akka$actor$dungeon$Dispatch$$_mailboxDoNotCallMeDirectly() {
        return this.akka$actor$dungeon$Dispatch$$_mailboxDoNotCallMeDirectly;
    }

    @Override
    public void akka$actor$dungeon$Dispatch$$_mailboxDoNotCallMeDirectly_$eq(Mailbox x$1) {
        this.akka$actor$dungeon$Dispatch$$_mailboxDoNotCallMeDirectly = x$1;
    }

    @Override
    public ChildrenContainer akka$actor$dungeon$Children$$_childrenRefsDoNotCallMeDirectly() {
        return this.akka$actor$dungeon$Children$$_childrenRefsDoNotCallMeDirectly;
    }

    @Override
    public void akka$actor$dungeon$Children$$_childrenRefsDoNotCallMeDirectly_$eq(ChildrenContainer x$1) {
        this.akka$actor$dungeon$Children$$_childrenRefsDoNotCallMeDirectly = x$1;
    }

    @Override
    public Map<String, FunctionRef> akka$actor$dungeon$Children$$_functionRefsDoNotCallMeDirectly() {
        return this.akka$actor$dungeon$Children$$_functionRefsDoNotCallMeDirectly;
    }

    @Override
    public void akka$actor$dungeon$Children$$_functionRefsDoNotCallMeDirectly_$eq(Map<String, FunctionRef> x$1) {
        this.akka$actor$dungeon$Children$$_functionRefsDoNotCallMeDirectly = x$1;
    }

    @Override
    public long akka$actor$dungeon$Children$$_nextNameDoNotCallMeDirectly() {
        return this.akka$actor$dungeon$Children$$_nextNameDoNotCallMeDirectly;
    }

    @Override
    public void akka$actor$dungeon$Children$$_nextNameDoNotCallMeDirectly_$eq(long x$1) {
        this.akka$actor$dungeon$Children$$_nextNameDoNotCallMeDirectly = x$1;
    }

    @Override
    public Tuple2<Duration, Cancellable> akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData() {
        return this.akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData;
    }

    @Override
    public void akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData_$eq(Tuple2<Duration, Cancellable> x$1) {
        this.akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData = x$1;
    }

    @Override
    public ActorSystemImpl system() {
        return this.system;
    }

    @Override
    public InternalActorRef self() {
        return this.self;
    }

    @Override
    public MessageDispatcher dispatcher() {
        return this.dispatcher;
    }

    @Override
    public InternalActorRef parent() {
        return this.parent;
    }

    @Override
    public Props props() {
        return this._props;
    }

    @Override
    public final boolean isLocal() {
        return true;
    }

    @Override
    public final ActorSystemImpl systemImpl() {
        return this.system();
    }

    @Override
    public final InternalActorRef guardian() {
        return this.self();
    }

    @Override
    public final InternalActorRef lookupRoot() {
        return this.self();
    }

    @Override
    public final ActorRefProvider provider() {
        return this.system().provider();
    }

    @Override
    public final ActorContext classicActorContext() {
        return this;
    }

    public int uid() {
        return this.self().path().uid();
    }

    public Actor actor() {
        return this._actor;
    }

    public Envelope currentMessage() {
        return this.currentMessage;
    }

    public void currentMessage_$eq(Envelope x$1) {
        this.currentMessage = x$1;
    }

    private List<PartialFunction<Object, BoxedUnit>> behaviorStack() {
        return this.behaviorStack;
    }

    private void behaviorStack_$eq(List<PartialFunction<Object, BoxedUnit>> x$1) {
        this.behaviorStack = x$1;
    }

    @Override
    public final InternalActorRef getParent() {
        return this.parent();
    }

    @Override
    public final ActorSystemImpl getSystem() {
        return this.system();
    }

    @Override
    public final ExecutionContextExecutor getDispatcher() {
        return this.dispatcher();
    }

    @Override
    public final ActorRef getSelf() {
        return this.self();
    }

    @Override
    public final Props getProps() {
        return this.props();
    }

    public void stash(SystemMessage msg) {
        Predef$.MODULE$.assert(msg.unlinked());
        this.sysmsgStash = LatestFirstSystemMessageList$.MODULE$.$colon$colon$extension(this.sysmsgStash, msg);
    }

    private SystemMessage unstashAll() {
        SystemMessage unstashed = this.sysmsgStash;
        this.sysmsgStash = SystemMessageList$.MODULE$.LNil();
        return unstashed;
    }

    public final void systemInvoke(SystemMessage message) {
        this.invokeAll$1(message, this.calculateState$1());
    }

    public final void invoke(Envelope messageHandle) {
        block8: {
            Object msg = messageHandle.message();
            Tuple2<Duration, Cancellable> timeoutBeforeReceive = this.cancelReceiveTimeoutIfNeeded(msg);
            try {
                try {
                    this.currentMessage_$eq(messageHandle);
                    if (msg instanceof AutoReceivedMessage) {
                        this.autoReceiveMessage(messageHandle);
                    } else {
                        this.receiveMessage(msg);
                    }
                    this.currentMessage_$eq(null);
                }
                catch (Throwable x$5) {
                    PartialFunction<Throwable, BoxedUnit> catchExpr$2 = this.handleNonFatalOrInterruptedException((Function1<Throwable, BoxedUnit>)(Function1<Throwable, Object> & Serializable)e -> {
                        this.handleInvokeFailure(Nil$.MODULE$, e);
                        return BoxedUnit.UNIT;
                    });
                    if (catchExpr$2.isDefinedAt(x$5)) {
                        catchExpr$2.apply(x$5);
                        break block8;
                    }
                    throw x$5;
                }
            }
            finally {
                this.checkReceiveTimeoutIfNeeded(msg, timeoutBeforeReceive);
            }
        }
    }

    public void autoReceiveMessage(Envelope msg) {
        Object object;
        if (this.system().settings().DebugAutoReceive()) {
            this.publish(new Logging.Debug(this.self().path().toString(), this.clazz(this.actor()), new StringBuilder(28).append("received AutoReceiveMessage ").append(msg).toString()));
        }
        if ((object = msg.message()) instanceof Terminated) {
            Terminated terminated = (Terminated)object;
            this.receivedTerminated(terminated);
            return;
        }
        if (object instanceof AddressTerminated) {
            AddressTerminated addressTerminated = (AddressTerminated)object;
            Address address = addressTerminated.address();
            this.addressTerminated(address);
            return;
        }
        if (Kill$.MODULE$.equals(object)) {
            throw ActorKilledException$.MODULE$.apply("Kill");
        }
        if (PoisonPill$.MODULE$.equals(object)) {
            this.self().stop();
            return;
        }
        if (object instanceof ActorSelectionMessage) {
            ActorSelectionMessage actorSelectionMessage = (ActorSelectionMessage)object;
            this.receiveSelection(actorSelectionMessage);
            return;
        }
        if (object instanceof Identify) {
            Identify identify = (Identify)object;
            Object messageId = identify.messageId();
            ActorRef qual$1 = this.sender();
            ActorIdentity x$1 = new ActorIdentity(messageId, new Some<ActorRef>(this.self()));
            ActorRef x$2 = qual$1.$bang$default$2(x$1);
            qual$1.$bang(x$1, x$2);
            return;
        }
        throw new RuntimeException(new StringBuilder(36).append("Unexpected message for autoreceive: ").append(object).toString());
    }

    private void receiveSelection(ActorSelectionMessage sel) {
        if (sel.elements().isEmpty()) {
            this.invoke(Envelope$.MODULE$.apply(sel.msg(), this.sender(), this.system()));
            return;
        }
        ActorSelection$.MODULE$.deliverSelection(this.self(), this.sender(), sel);
    }

    public final void receiveMessage(Object msg) {
        this.actor().aroundReceive((PartialFunction)this.behaviorStack().head(), msg);
    }

    @Override
    public final ActorRef sender() {
        Envelope envelope = this.currentMessage();
        if (envelope == null) {
            return this.system().deadLetters();
        }
        if (envelope.sender() != null) {
            return envelope.sender();
        }
        return this.system().deadLetters();
    }

    @Override
    public void become(PartialFunction<Object, BoxedUnit> behavior, boolean discardOld) {
        PartialFunction<Object, BoxedUnit> partialFunction = behavior;
        this.behaviorStack_$eq(((List)(discardOld && this.behaviorStack().nonEmpty() ? this.behaviorStack().tail() : this.behaviorStack())).$colon$colon(partialFunction));
    }

    public void become(Procedure<Object> behavior) {
        this.become(behavior, true);
    }

    public void become(Procedure<Object> behavior, boolean discardOld) {
        this.become((PartialFunction<Object, BoxedUnit>)((Object)new Serializable(null, behavior){
            private static final long serialVersionUID = 0L;
            private final Procedure behavior$1;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                this.behavior$1.apply(A1);
                return (B1)BoxedUnit.UNIT;
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return true;
            }
            {
                this.behavior$1 = behavior$1;
            }
        }), discardOld);
    }

    public boolean become$default$2() {
        return true;
    }

    @Override
    public void unbecome() {
        List<PartialFunction<Object, BoxedUnit>> list;
        List<PartialFunction<Object, BoxedUnit>> original = this.behaviorStack();
        if (original.isEmpty() || ((List)original.tail()).isEmpty()) {
            PartialFunction<Object, BoxedUnit> partialFunction = this.actor().receive();
            list = ActorCell$.MODULE$.emptyBehaviorStack().$colon$colon(partialFunction);
        } else {
            list = (List<PartialFunction<Object, BoxedUnit>>)original.tail();
        }
        this.behaviorStack_$eq(list);
    }

    /*
     * WARNING - void declaration
     */
    public Actor newActor() {
        void v1;
        block6: {
            ActorCell actorCell = this;
            ActorCell$.MODULE$.contextStack().set(ActorCell$.MODULE$.contextStack().get().$colon$colon(actorCell));
            try {
                void var2_2;
                List<PartialFunction<Object, BoxedUnit>> list;
                this.behaviorStack_$eq(ActorCell$.MODULE$.emptyBehaviorStack());
                Actor instance = this.props().newActor();
                if (instance == null) {
                    throw ActorInitializationException$.MODULE$.apply(this.self(), "Actor instance passed to actorOf can't be 'null'", ActorInitializationException$.MODULE$.apply$default$3());
                }
                if (this.behaviorStack().isEmpty()) {
                    PartialFunction<Object, BoxedUnit> partialFunction = instance.receive();
                    list = this.behaviorStack().$colon$colon(partialFunction);
                } else {
                    list = this.behaviorStack();
                }
                this.behaviorStack_$eq(list);
                this._actor = instance;
                v1 = var2_2;
            }
            catch (Throwable throwable) {
                List<ActorContext> stackAfter = ActorCell$.MODULE$.contextStack().get();
                if (stackAfter.nonEmpty()) {
                    ActorCell$.MODULE$.contextStack().set((List<ActorContext>)(stackAfter.head() == null ? ((IterableOps)stackAfter.tail()).tail() : stackAfter.tail()));
                }
                throw throwable;
            }
            List<ActorContext> stackAfter = ActorCell$.MODULE$.contextStack().get();
            if (!stackAfter.nonEmpty()) break block6;
            ActorCell$.MODULE$.contextStack().set((List<ActorContext>)(stackAfter.head() == null ? ((IterableOps)stackAfter.tail()).tail() : stackAfter.tail()));
        }
        return v1;
    }

    public void create(Option<ActorInitializationException> failure) {
        failure.foreach((Function1<ActorInitializationException, Nothing$> & Serializable)x$3 -> {
            throw x$3;
        });
        try {
            Actor created = this.newActor();
            created.aroundPreStart();
            this.checkReceiveTimeout(true);
            if (this.system().settings().DebugLifecycle()) {
                this.publish(new Logging.Debug(this.self().path().toString(), this.clazz(created), new StringBuilder(10).append("started (").append(created).append(")").toString()));
            }
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 instanceof InterruptedException) {
                InterruptedException interruptedException = (InterruptedException)throwable2;
                this.failActor$1();
                Thread.currentThread().interrupt();
                throw ActorInitializationException$.MODULE$.apply(this.self(), "interruption during creation", interruptedException);
            }
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable e = option.get();
                this.failActor$1();
                Throwable throwable3 = e;
                if (throwable3 instanceof InstantiationException) {
                    InstantiationException instantiationException = (InstantiationException)throwable3;
                    throw ActorInitializationException$.MODULE$.apply(this.self(), "exception during creation, this problem is likely to occur because the class of the Actor you tried to create is either,\n               a non-static inner class (in which case make it a static inner class or use Props(new ...) or Props( new Creator ... )\n               or is missing an appropriate, reachable no-args constructor.\n              ", instantiationException.getCause());
                }
                String rootCauseMessage = (String)Option$.MODULE$.apply(this.rootCauseOf(throwable3).getMessage()).getOrElse((Function0<String> & Serializable)() -> "No message available");
                throw ActorInitializationException$.MODULE$.apply(this.self(), new StringBuilder(49).append("exception during creation, root cause message: [").append(rootCauseMessage).append("]").toString(), throwable3);
            }
            throw throwable;
        }
    }

    private Throwable rootCauseOf(Throwable throwable) {
        while (throwable.getCause() != null) {
            Throwable throwable2 = throwable.getCause();
            Throwable throwable3 = throwable;
            if (!(throwable2 == null ? throwable3 != null : !throwable2.equals(throwable3))) break;
            throwable = throwable.getCause();
        }
        return throwable;
    }

    private void supervise(ActorRef child, boolean async) {
        if (!this.isTerminating()) {
            Option<ChildRestartStats> option = this.initChild(child);
            if (option instanceof Some) {
                this.handleSupervise(child, async);
                if (this.system().settings().DebugLifecycle()) {
                    this.publish(new Logging.Debug(this.self().path().toString(), this.clazz(this.actor()), new StringBuilder(16).append("now supervising ").append(child).toString()));
                    return;
                }
                return;
            }
            if (None$.MODULE$.equals(option)) {
                this.publish(Logging$Error$.MODULE$.apply(this.self().path().toString(), this.clazz(this.actor()), new StringBuilder(67).append("received Supervise from unregistered child ").append(child).append(", this will not end well").toString()));
                return;
            }
            throw new MatchError(option);
        }
    }

    public void handleSupervise(ActorRef child, boolean async) {
        ActorRef actorRef2 = child;
        if (actorRef2 instanceof RepointableActorRef) {
            RepointableActorRef repointableActorRef = (RepointableActorRef)actorRef2;
            if (async) {
                repointableActorRef.point(true);
                return;
            }
        }
    }

    @InternalStableApi
    public final void clearActorFields(Actor actorInstance, boolean recreate) {
        this.currentMessage_$eq(null);
        this.behaviorStack_$eq(ActorCell$.MODULE$.emptyBehaviorStack());
    }

    public final void clearFieldsForTermination() {
        this.unstashAll();
        this._props = ActorCell$.MODULE$.terminatedProps();
        this._actor = null;
    }

    public final void publish(Logging.LogEvent e) {
        try {
            this.system().eventStream().publish(e);
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
            }
            throw throwable;
        }
    }

    public final Class<?> clazz(Object o) {
        if (o == null) {
            return this.getClass();
        }
        return o.getClass();
    }

    private final int calculateState$1() {
        if (this.waitingForChildrenOrNull() != null) {
            return 2;
        }
        if (this.mailbox().isSuspended()) {
            return 1;
        }
        return 0;
    }

    private final void sendAllToDeadLetters$1(SystemMessage messages) {
        while (EarliestFirstSystemMessageList$.MODULE$.nonEmpty$extension(messages)) {
            SystemMessage tail = EarliestFirstSystemMessageList$.MODULE$.tail$extension(messages);
            SystemMessage msg = messages;
            msg.unlink();
            ActorRef qual$1 = this.provider().deadLetters();
            SystemMessage x$1 = msg;
            ActorRef x$2 = qual$1.$bang$default$2(x$1);
            qual$1.$bang(x$1, x$2);
            messages = tail;
        }
    }

    private static final boolean shouldStash$1(SystemMessage m4, int state) {
        int n = state;
        switch (n) {
            case 0: {
                return false;
            }
            case 1: {
                return m4 instanceof StashWhenFailed;
            }
            case 2: {
                return m4 instanceof StashWhenWaitingForChildren;
            }
        }
        throw new MatchError(BoxesRunTime.boxToInteger(n));
    }

    private final void invokeAll$1(SystemMessage messages, int currentState) {
        block29: {
            BoxedUnit boxedUnit;
            while (true) {
                SystemMessage todo;
                Object object;
                SystemMessage rest = EarliestFirstSystemMessageList$.MODULE$.tail$extension(messages);
                SystemMessage message = messages;
                message.unlink();
                try {
                    SystemMessage systemMessage;
                    SystemMessage systemMessage2 = message;
                    if (systemMessage2 != null && ActorCell.shouldStash$1(systemMessage = systemMessage2, currentState)) {
                        this.stash(systemMessage);
                    } else if (systemMessage2 instanceof Failed) {
                        Failed failed = (Failed)systemMessage2;
                        this.handleFailure(failed);
                    } else if (systemMessage2 instanceof DeathWatchNotification) {
                        DeathWatchNotification deathWatchNotification = (DeathWatchNotification)systemMessage2;
                        ActorRef a = deathWatchNotification.actor();
                        boolean ec = deathWatchNotification.existenceConfirmed();
                        boolean at = deathWatchNotification.addressTerminated();
                        this.watchedActorTerminated(a, ec, at);
                    } else if (systemMessage2 instanceof Create) {
                        Create create = (Create)systemMessage2;
                        Option<ActorInitializationException> failure = create.failure();
                        this.create(failure);
                    } else if (systemMessage2 instanceof Watch) {
                        Watch watch = (Watch)systemMessage2;
                        InternalActorRef watchee = watch.watchee();
                        InternalActorRef watcher = watch.watcher();
                        this.addWatcher(watchee, watcher);
                    } else if (systemMessage2 instanceof Unwatch) {
                        Unwatch unwatch = (Unwatch)systemMessage2;
                        ActorRef watchee = unwatch.watchee();
                        ActorRef watcher = unwatch.watcher();
                        this.remWatcher(watchee, watcher);
                    } else if (systemMessage2 instanceof Recreate) {
                        Recreate recreate = (Recreate)systemMessage2;
                        Throwable cause = recreate.cause();
                        this.faultRecreate(cause);
                    } else if (systemMessage2 instanceof Suspend) {
                        this.faultSuspend();
                    } else if (systemMessage2 instanceof Resume) {
                        Resume resume = (Resume)systemMessage2;
                        Throwable inRespToFailure = resume.causedByFailure();
                        this.faultResume(inRespToFailure);
                    } else if (systemMessage2 instanceof Terminate) {
                        this.terminate();
                    } else if (systemMessage2 instanceof Supervise) {
                        Supervise supervise = (Supervise)systemMessage2;
                        ActorRef child = supervise.child();
                        boolean async = supervise.async();
                        this.supervise(child, async);
                    } else if (NoMessage$.MODULE$.equals(systemMessage2)) {
                    } else {
                        throw new MatchError(systemMessage2);
                    }
                    object = BoxedUnit.UNIT;
                }
                catch (Throwable x$4) {
                    PartialFunction<Throwable, BoxedUnit> catchExpr$1 = this.handleNonFatalOrInterruptedException((Function1<Throwable, BoxedUnit>)(Function1<Throwable, Object> & Serializable)e -> {
                        this.handleInvokeFailure(Nil$.MODULE$, e);
                        return BoxedUnit.UNIT;
                    });
                    if (catchExpr$1.isDefinedAt(x$4)) {
                        object = catchExpr$1.apply(x$4);
                    }
                    throw x$4;
                }
                int newState = this.calculateState$1();
                SystemMessage systemMessage = todo = newState < currentState ? EarliestFirstSystemMessageList$.MODULE$.reversePrepend$extension(rest, this.unstashAll()) : rest;
                if (this.isTerminated()) {
                    this.sendAllToDeadLetters$1(todo);
                    boxedUnit = BoxedUnit.UNIT;
                    break block29;
                }
                if (!EarliestFirstSystemMessageList$.MODULE$.nonEmpty$extension(todo)) break;
                currentState = newState;
                messages = todo;
            }
            boxedUnit = BoxedUnit.UNIT;
        }
    }

    private final void failActor$1() {
        if (this._actor != null) {
            this.clearActorFields(this.actor(), false);
            this.setFailedFatally();
            this._actor = null;
            return;
        }
    }

    public ActorCell(ActorSystemImpl system, InternalActorRef self, Props _initialProps, MessageDispatcher dispatcher, InternalActorRef parent) {
        this.system = system;
        this.self = self;
        this.dispatcher = dispatcher;
        this.parent = parent;
        ActorRefFactory.$init$(this);
        ActorContext.$init$(this);
        AbstractActor.ActorContext.$init$(this);
        Cell.$init$(this);
        ReceiveTimeout.$init$(this);
        Children.$init$(this);
        Dispatch.$init$(this);
        DeathWatch.$init$(this);
        FaultHandling.$init$(this);
        this._props = _initialProps;
        this.behaviorStack = ActorCell$.MODULE$.emptyBehaviorStack();
        this.sysmsgStash = SystemMessageList$.MODULE$.LNil();
    }
}

