/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorLogging;
import akka.actor.ActorRef;
import akka.actor.ActorRefFactory;
import akka.actor.SupervisorStrategy;
import akka.annotation.InternalApi;
import akka.event.LoggingAdapter;
import akka.io.SimpleDnsCache;
import akka.io.dns.AAAARecord;
import akka.io.dns.ARecord;
import akka.io.dns.CachePolicy;
import akka.io.dns.CachePolicy$Never$;
import akka.io.dns.CachePolicy$Ttl$;
import akka.io.dns.DnsProtocol;
import akka.io.dns.DnsProtocol$Ip$;
import akka.io.dns.DnsProtocol$Resolved$;
import akka.io.dns.DnsProtocol$Srv$;
import akka.io.dns.DnsSettings;
import akka.io.dns.ResourceRecord;
import akka.io.dns.internal.AsyncDnsResolver$;
import akka.io.dns.internal.DnsClient;
import akka.io.dns.internal.package$CachePolicyIsOrdered$;
import akka.pattern.AskTimeoutException;
import akka.pattern.AskableActorRef$;
import akka.pattern.package$;
import akka.util.Helpers$;
import akka.util.PrettyDuration$;
import akka.util.PrettyDuration$PrettyPrintableDuration$;
import akka.util.Timeout;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.collection.AbstractSeq;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.SeqOps;
import scala.collection.StringOps$;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.Future;
import scala.concurrent.Future$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Try$;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\r\u0015b!\u0002\u001f>\u0005\u0005+\u0005\u0002C+\u0001\u0005\u0003\u0005\u000b\u0011B,\t\u0011m\u0003!\u0011!Q\u0001\nqC\u0001\u0002\u0019\u0001\u0003\u0002\u0003\u0006I!\u0019\u0005\u0007\u007f\u0002!\t!!\u0001\t\u0013\u00055\u0001A1A\u0005\u0004\u0005=\u0001\u0002CA\u000f\u0001\u0001\u0006I!!\u0005\t\u0013\u0005}\u0001A1A\u0005\u0004\u0005\u0005\u0002\u0002CA\u0018\u0001\u0001\u0006I!a\t\t\u0013\u0005E\u0002A1A\u0005\u0002\u0005M\u0002bBA\u001b\u0001\u0001\u0006Ia\u001a\u0005\n\u0003o\u0001!\u0019!C\u0001\u0003sA\u0001\"!\u0016\u0001A\u0003%\u00111\b\u0005\n\u0003/\u0002!\u0019!C\u0001\u0003sA\u0001\"!\u0017\u0001A\u0003%\u00111\b\u0005\n\u00037\u0002\u0001\u0019!C\u0005\u0003;B\u0011\"!\u001a\u0001\u0001\u0004%I!a\u001a\t\u0011\u0005M\u0004\u0001)Q\u0005\u0003?Bq!!\u001e\u0001\t\u0013\t9\bC\u0005\u0002z\u0001\u0011\r\u0011\"\u0003\u0002|!9\u0011Q\u0010\u0001!\u0002\u0013Y\bbBA@\u0001\u0011\u0005\u0013\u0011\u0011\u0005\b\u0003\u0017\u0003A\u0011BAG\u0011\u001d\t)\r\u0001C\u0005\u0003\u000fDq!a;\u0001\t\u0013\ti\u000fC\u0004\u0002v\u0002!I!a>\t\u000f\t\r\u0001\u0001\"\u0003\u0003\u0006\u001dA!1D\u001f\t\u0002\r\u0013iBB\u0004={!\u00051Ia\b\t\r}dB\u0011\u0001B\u0011\u0011%\u0011\u0019\u0003\bb\u0001\n\u0013\u0011)\u0003\u0003\u0005\u00036q\u0001\u000b\u0011\u0002B\u0014\u0011%\u00119\u0004\bb\u0001\n\u0013\u0011)\u0003\u0003\u0005\u0003:q\u0001\u000b\u0011\u0002B\u0014\u0011!\u0011Y\u0004\bC\u0001\u0007\nu\u0002\u0002\u0003B$9\u0011\u00051I!\u0013\t\u000f\t5C\u0004\"\u0003\u0003P!I!1\u000b\u000fC\u0002\u0013%!Q\u000b\u0005\t\u0005/b\u0002\u0015!\u0003\u0002J\u001a1!\u0011\f\u000fA\u00057B!Ba\u001c(\u0005+\u0007I\u0011\u0001B9\u0011)\u0011\u0019h\nB\tB\u0003%\u0011q\u0015\u0005\u0007\u007f\u001e\"\tA!\u001e\t\u0013\tut%!A\u0005\u0002\t}\u0004\"\u0003BBOE\u0005I\u0011\u0001BC\u0011%\u0011IjJA\u0001\n\u0003\u0012Y\nC\u0005\u0003(\u001e\n\t\u0011\"\u0001\u0003*\"I!\u0011W\u0014\u0002\u0002\u0013\u0005!1\u0017\u0005\n\u0005{;\u0013\u0011!C!\u0005\u007fC\u0011B!4(\u0003\u0003%\tAa4\t\u0013\tMw%!A\u0005B\tU\u0007\"\u0003BmO\u0005\u0005I\u0011\tBn\u0011%\u0011inJA\u0001\n\u0003\u0012ynB\u0005\u0003dr\t\t\u0011#\u0001\u0003f\u001aI!\u0011\f\u000f\u0002\u0002#\u0005!q\u001d\u0005\u0007\u007fZ\"\tA!@\t\u0013\t}h'!A\u0005F\r\u0005\u0001\"CB\u0002m\u0005\u0005I\u0011QB\u0003\u0011%\u0019IANA\u0001\n\u0003\u001bY\u0001C\u0005\u0004\u0018Y\n\t\u0011\"\u0003\u0004\u001a\t\u0001\u0012i]=oG\u0012s7OU3t_24XM\u001d\u0006\u0003}}\n\u0001\"\u001b8uKJt\u0017\r\u001c\u0006\u0003\u0001\u0006\u000b1\u0001\u001a8t\u0015\t\u00115)\u0001\u0002j_*\tA)\u0001\u0003bW.\f7\u0003\u0002\u0001G\u0019J\u0003\"a\u0012&\u000e\u0003!S\u0011!S\u0001\u0006g\u000e\fG.Y\u0005\u0003\u0017\"\u0013a!\u00118z%\u00164\u0007CA'Q\u001b\u0005q%BA(D\u0003\u0015\t7\r^8s\u0013\t\tfJA\u0003BGR|'\u000f\u0005\u0002N'&\u0011AK\u0014\u0002\r\u0003\u000e$xN\u001d'pO\u001eLgnZ\u0001\tg\u0016$H/\u001b8hg\u000e\u0001\u0001C\u0001-Z\u001b\u0005y\u0014B\u0001.@\u0005-!en]*fiRLgnZ:\u0002\u000b\r\f7\r[3\u0011\u0005usV\"A!\n\u0005}\u000b%AD*j[BdW\r\u00128t\u0007\u0006\u001c\u0007.Z\u0001\u000eG2LWM\u001c;GC\u000e$xN]=\u0011\u000b\u001d\u0013GmZ>\n\u0005\rD%!\u0003$v]\u000e$\u0018n\u001c83!\tiU-\u0003\u0002g\u001d\ny\u0011i\u0019;peJ+gMR1di>\u0014\u0018\u0010E\u0002iaNt!!\u001b8\u000f\u0005)lW\"A6\u000b\u000514\u0016A\u0002\u001fs_>$h(C\u0001J\u0013\ty\u0007*A\u0004qC\u000e\\\u0017mZ3\n\u0005E\u0014(\u0001\u0002'jgRT!a\u001c%\u0011\u0005QLX\"A;\u000b\u0005Y<\u0018a\u00018fi*\t\u00010\u0001\u0003kCZ\f\u0017B\u0001>v\u0005EIe.\u001a;T_\u000e\\W\r^!eIJ,7o\u001d\t\u0004QBd\bCA'~\u0013\tqhJ\u0001\u0005BGR|'OU3g\u0003\u0019a\u0014N\\5u}QA\u00111AA\u0004\u0003\u0013\tY\u0001E\u0002\u0002\u0006\u0001i\u0011!\u0010\u0005\u0006+\u0012\u0001\ra\u0016\u0005\u00067\u0012\u0001\r\u0001\u0018\u0005\u0006A\u0012\u0001\r!Y\u0001\u0003K\u000e,\"!!\u0005\u0011\t\u0005M\u0011\u0011D\u0007\u0003\u0003+Q1!a\u0006I\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0005\u00037\t)B\u0001\rFq\u0016\u001cW\u000f^5p]\u000e{g\u000e^3yi\u0016CXmY;u_J\f1!Z2!\u0003\u001d!\u0018.\\3pkR,\"!a\t\u0011\t\u0005\u0015\u00121F\u0007\u0003\u0003OQ1!!\u000bD\u0003\u0011)H/\u001b7\n\t\u00055\u0012q\u0005\u0002\b)&lWm\\;u\u0003!!\u0018.\\3pkR\u0004\u0013a\u00038b[\u0016\u001cVM\u001d<feN,\u0012aZ\u0001\r]\u0006lWmU3sm\u0016\u00148\u000fI\u0001\u0014a>\u001c\u0018\u000e^5wK\u000e\u000b7\r[3Q_2L7-_\u000b\u0003\u0003w\u0001B!!\u0010\u0002P9!\u0011qHA&\u001d\u0011\t\t%!\u0013\u000f\t\u0005\r\u0013q\t\b\u0004U\u0006\u0015\u0013\"\u0001#\n\u0005\t\u001b\u0015B\u0001!B\u0013\r\tieP\u0001\f\u0007\u0006\u001c\u0007.\u001a)pY&\u001c\u00170\u0003\u0003\u0002R\u0005M#aC\"bG\",\u0007k\u001c7jGfT1!!\u0014@\u0003Q\u0001xn]5uSZ,7)Y2iKB{G.[2zA\u0005\u0019b.Z4bi&4XmQ1dQ\u0016\u0004v\u000e\\5ds\u0006!b.Z4bi&4XmQ1dQ\u0016\u0004v\u000e\\5ds\u0002\n\u0011B]3rk\u0016\u001cH/\u00133\u0016\u0005\u0005}\u0003cA$\u0002b%\u0019\u00111\r%\u0003\u000bMCwN\u001d;\u0002\u001bI,\u0017/^3ti&#w\fJ3r)\u0011\tI'a\u001c\u0011\u0007\u001d\u000bY'C\u0002\u0002n!\u0013A!\u00168ji\"I\u0011\u0011\u000f\t\u0002\u0002\u0003\u0007\u0011qL\u0001\u0004q\u0012\n\u0014A\u0003:fcV,7\u000f^%eA\u00051a.\u001a=u\u0013\u0012$\"!a\u0018\u0002\u0013I,7o\u001c7wKJ\u001cX#A>\u0002\u0015I,7o\u001c7wKJ\u001c\b%A\u0004sK\u000e,\u0017N^3\u0016\u0005\u0005\r\u0005\u0003BAC\u0003\u000fk\u0011\u0001A\u0005\u0004\u0003\u0013\u0003&a\u0002*fG\u0016Lg/Z\u0001\u0015e\u0016\u001cx\u000e\u001c<f/&$\bNU3t_24XM]:\u0015\u0011\u0005=\u00151UA\\\u0003\u0007\u0004b!a\u0005\u0002\u0012\u0006U\u0015\u0002BAJ\u0003+\u0011aAR;ukJ,\u0007\u0003BAL\u0003;s1\u0001WAM\u0013\r\tYjP\u0001\f\t:\u001c\bK]8u_\u000e|G.\u0003\u0003\u0002 \u0006\u0005&\u0001\u0003*fg>dg/\u001a3\u000b\u0007\u0005mu\bC\u0004\u0002&Z\u0001\r!a*\u0002\t9\fW.\u001a\t\u0005\u0003S\u000b\tL\u0004\u0003\u0002,\u00065\u0006C\u00016I\u0013\r\ty\u000bS\u0001\u0007!J,G-\u001a4\n\t\u0005M\u0016Q\u0017\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005=\u0006\nC\u0004\u0002:Z\u0001\r!a/\u0002\u0017I,\u0017/^3tiRK\b/\u001a\t\u0005\u0003{\u000byL\u0004\u0003\u0002@\u0005e\u0015\u0002BAa\u0003C\u00131BU3rk\u0016\u001cH\u000fV=qK\"1\u0011\u0011\u0010\fA\u0002m\fAb]3oIF+Xm\u001d;j_:$b!!3\u0002^\u0006\u0005\bCBA\n\u0003#\u000bY\r\u0005\u0003\u0002N\u0006]g\u0002BAh\u0003'tA!a\u0010\u0002R&\u0011ahP\u0005\u0004\u0003+l\u0014!\u0003#og\u000ec\u0017.\u001a8u\u0013\u0011\tI.a7\u0003\r\u0005s7o^3s\u0015\r\t).\u0010\u0005\u0007\u0003?<\u0002\u0019\u0001?\u0002\u0011I,7o\u001c7wKJDq!a9\u0018\u0001\u0004\t)/A\u0004nKN\u001c\u0018mZ3\u0011\t\u00055\u0017q]\u0005\u0005\u0003S\fYNA\u0006E]N\fV/Z:uS>t\u0017!\u0005:fg>dg/Z,ji\"\u001cV-\u0019:dQRA\u0011qRAx\u0003c\f\u0019\u0010C\u0004\u0002&b\u0001\r!a*\t\u000f\u0005e\u0006\u00041\u0001\u0002<\"1\u0011q\u001c\rA\u0002q\fAB]3t_24XMR5sgR$\u0002\"a$\u0002z\u0006}(\u0011\u0001\u0005\b\u0003wL\u0002\u0019AA\u007f\u0003-\u0019X-\u0019:dQ:\u000bW.Z:\u0011\t!\u0004\u0018q\u0015\u0005\b\u0003sK\u0002\u0019AA^\u0011\u0019\ty.\u0007a\u0001y\u00069!/Z:pYZ,G\u0003CAH\u0005\u000f\u0011IAa\u0003\t\u000f\u0005\u0015&\u00041\u0001\u0002(\"9\u0011\u0011\u0018\u000eA\u0002\u0005m\u0006BBAp5\u0001\u0007A\u0010K\u0002\u0001\u0005\u001f\u0001BA!\u0005\u0003\u00185\u0011!1\u0003\u0006\u0004\u0005+\u0019\u0015AC1o]>$\u0018\r^5p]&!!\u0011\u0004B\n\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0002!\u0005\u001b\u0018P\\2E]N\u0014Vm]8mm\u0016\u0014\bcAA\u00039M\u0011AD\u0012\u000b\u0003\u0005;\t1\"\u001b9wi\u0005#GM]3tgV\u0011!q\u0005\t\u0005\u0005S\u0011\t$\u0004\u0002\u0003,)!!Q\u0006B\u0018\u0003!i\u0017\r^2iS:<'bAA\u0015\u0011&!!1\u0007B\u0016\u0005\u0015\u0011VmZ3y\u00031I\u0007O\u001e\u001bBI\u0012\u0014Xm]:!\u0003-I\u0007O\u001e\u001cBI\u0012\u0014Xm]:\u0002\u0019%\u0004hON!eIJ,7o\u001d\u0011\u0002\u001b%\u001c\u0018\n\u001d<5\u0003\u0012$'/Z:t)\u0011\u0011yD!\u0012\u0011\u0007\u001d\u0013\t%C\u0002\u0003D!\u0013qAQ8pY\u0016\fg\u000eC\u0004\u0002&\n\u0002\r!a*\u0002\u001b%\u001c\u0018\n\u001d<7\u0003\u0012$'/Z:t)\u0011\u0011yDa\u0013\t\u000f\u0005\u00156\u00051\u0001\u0002(\u0006i\u0011n]%oKR\fE\r\u001a:fgN$BAa\u0010\u0003R!9\u0011Q\u0015\u0013A\u0002\u0005\u001d\u0016!B#naRLXCAAe\u0003\u0019)U\u000e\u001d;zA\t1\"+Z:pYZ,g)Y5mK\u0012,\u0005pY3qi&|gnE\u0004(\u0005;\u0012\u0019G!\u001b\u0011\u0007!\u0014y&C\u0002\u0003bI\u0014\u0011\"\u0012=dKB$\u0018n\u001c8\u0011\u0007\u001d\u0013)'C\u0002\u0003h!\u0013q\u0001\u0015:pIV\u001cG\u000fE\u0002i\u0005WJ1A!\u001cs\u00051\u0019VM]5bY&T\u0018M\u00197f\u0003\ri7oZ\u000b\u0003\u0003O\u000bA!\\:hAQ!!q\u000fB>!\r\u0011IhJ\u0007\u00029!9!q\u000e\u0016A\u0002\u0005\u001d\u0016\u0001B2paf$BAa\u001e\u0003\u0002\"I!qN\u0016\u0011\u0002\u0003\u0007\u0011qU\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\t\u00119I\u000b\u0003\u0002(\n%5F\u0001BF!\u0011\u0011iI!&\u000e\u0005\t=%\u0002\u0002BI\u0005'\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\tU\u0001*\u0003\u0003\u0003\u0018\n=%!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006i\u0001O]8ek\u000e$\bK]3gSb,\"A!(\u0011\t\t}%QU\u0007\u0003\u0005CS1Aa)x\u0003\u0011a\u0017M\\4\n\t\u0005M&\u0011U\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0005W\u00032a\u0012BW\u0013\r\u0011y\u000b\u0013\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0005k\u0013Y\fE\u0002H\u0005oK1A!/I\u0005\r\te.\u001f\u0005\n\u0003cz\u0013\u0011!a\u0001\u0005W\u000bq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0005\u0003\u0004bAa1\u0003J\nUVB\u0001Bc\u0015\r\u00119\rS\u0001\u000bG>dG.Z2uS>t\u0017\u0002\u0002Bf\u0005\u000b\u0014\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!!q\bBi\u0011%\t\t(MA\u0001\u0002\u0004\u0011),\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003\u0002BO\u0005/D\u0011\"!\u001d3\u0003\u0003\u0005\rAa+\u0002\u0011!\f7\u000f[\"pI\u0016$\"Aa+\u0002\r\u0015\fX/\u00197t)\u0011\u0011yD!9\t\u0013\u0005ED'!AA\u0002\tU\u0016A\u0006*fg>dg/\u001a$bS2,G-\u0012=dKB$\u0018n\u001c8\u0011\u0007\tedgE\u00037\u0005S\u0014)\u0010\u0005\u0005\u0003l\nE\u0018q\u0015B<\u001b\t\u0011iOC\u0002\u0003p\"\u000bqA];oi&lW-\u0003\u0003\u0003t\n5(!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8ocA!!q\u001fB~\u001b\t\u0011IP\u0003\u0002Co&!!Q\u000eB})\t\u0011)/\u0001\u0005u_N#(/\u001b8h)\t\u0011i*A\u0003baBd\u0017\u0010\u0006\u0003\u0003x\r\u001d\u0001b\u0002B8s\u0001\u0007\u0011qU\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\u0019iaa\u0005\u0011\u000b\u001d\u001by!a*\n\u0007\rE\u0001J\u0001\u0004PaRLwN\u001c\u0005\n\u0007+Q\u0014\u0011!a\u0001\u0005o\n1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\u0019Y\u0002\u0005\u0003\u0003 \u000eu\u0011\u0002BB\u0010\u0005C\u0013aa\u00142kK\u000e$\bf\u0001\u000f\u0003\u0010!\u001a1Da\u0004")
@InternalApi
public final class AsyncDnsResolver
implements Actor,
ActorLogging {
    private final DnsSettings settings;
    public final SimpleDnsCache akka$io$dns$internal$AsyncDnsResolver$$cache;
    private final ExecutionContextExecutor ec;
    private final Timeout timeout;
    private final List<InetSocketAddress> nameServers;
    private final CachePolicy.CachePolicy positiveCachePolicy;
    private final CachePolicy.CachePolicy negativeCachePolicy;
    private short requestId;
    private final List<ActorRef> akka$io$dns$internal$AsyncDnsResolver$$resolvers;
    private LoggingAdapter akka$actor$ActorLogging$$_log;
    private ActorContext context;
    private ActorRef self;

    @Override
    public LoggingAdapter log() {
        return ActorLogging.log$(this);
    }

    @Override
    public final ActorRef sender() {
        return Actor.sender$(this);
    }

    @Override
    @InternalApi
    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
        Actor.aroundReceive$(this, receive2, msg);
    }

    @Override
    @InternalApi
    public void aroundPreStart() {
        Actor.aroundPreStart$(this);
    }

    @Override
    @InternalApi
    public void aroundPostStop() {
        Actor.aroundPostStop$(this);
    }

    @Override
    @InternalApi
    public void aroundPreRestart(Throwable reason, Option<Object> message) {
        Actor.aroundPreRestart$(this, reason, message);
    }

    @Override
    @InternalApi
    public void aroundPostRestart(Throwable reason) {
        Actor.aroundPostRestart$(this, reason);
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return Actor.supervisorStrategy$(this);
    }

    @Override
    public void preStart() throws Exception {
        Actor.preStart$(this);
    }

    @Override
    public void postStop() throws Exception {
        Actor.postStop$(this);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        Actor.preRestart$(this, reason, message);
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        Actor.postRestart$(this, reason);
    }

    @Override
    public void unhandled(Object message) {
        Actor.unhandled$(this, message);
    }

    @Override
    public LoggingAdapter akka$actor$ActorLogging$$_log() {
        return this.akka$actor$ActorLogging$$_log;
    }

    @Override
    public void akka$actor$ActorLogging$$_log_$eq(LoggingAdapter x$1) {
        this.akka$actor$ActorLogging$$_log = x$1;
    }

    @Override
    public ActorContext context() {
        return this.context;
    }

    @Override
    public final ActorRef self() {
        return this.self;
    }

    @Override
    public void akka$actor$Actor$_setter_$context_$eq(ActorContext x$1) {
        this.context = x$1;
    }

    @Override
    public final void akka$actor$Actor$_setter_$self_$eq(ActorRef x$1) {
        this.self = x$1;
    }

    public ExecutionContextExecutor ec() {
        return this.ec;
    }

    public Timeout timeout() {
        return this.timeout;
    }

    public List<InetSocketAddress> nameServers() {
        return this.nameServers;
    }

    public CachePolicy.CachePolicy positiveCachePolicy() {
        return this.positiveCachePolicy;
    }

    public CachePolicy.CachePolicy negativeCachePolicy() {
        return this.negativeCachePolicy;
    }

    private short requestId() {
        return this.requestId;
    }

    private void requestId_$eq(short x$1) {
        this.requestId = x$1;
    }

    private short nextId() {
        this.requestId_$eq((short)(this.requestId() + 1));
        return this.requestId();
    }

    public List<ActorRef> akka$io$dns$internal$AsyncDnsResolver$$resolvers() {
        return this.akka$io$dns$internal$AsyncDnsResolver$$resolvers;
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ AsyncDnsResolver $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof DnsProtocol.Resolve) {
                    DnsProtocol.RequestType mode;
                    DnsProtocol.Resolve resolve = (DnsProtocol.Resolve)A1;
                    String name = resolve.name();
                    Option<DnsProtocol.Resolved> option = this.$outer.akka$io$dns$internal$AsyncDnsResolver$$cache.get(new Tuple2<String, DnsProtocol.RequestType>(name, mode = resolve.requestType()));
                    if (option instanceof Some) {
                        Some some = (Some)option;
                        DnsProtocol.Resolved resolved2 = (DnsProtocol.Resolved)some.value();
                        this.$outer.log().debug("{} cached {}", mode, resolved2);
                        this.$outer.sender().$bang(resolved2, this.$outer.self());
                    } else if (None$.MODULE$.equals(option)) {
                        package$.MODULE$.pipe(this.$outer.akka$io$dns$internal$AsyncDnsResolver$$resolveWithResolvers(name, mode, this.$outer.akka$io$dns$internal$AsyncDnsResolver$$resolvers()).map((Function1<DnsProtocol.Resolved, DnsProtocol.Resolved> & Serializable)resolved -> {
                            if (resolved.records().nonEmpty()) {
                                CachePolicy.CachePolicy cachePolicy = $this.$outer.positiveCachePolicy();
                                CachePolicy.CachePolicy minTtl = (CachePolicy.CachePolicy)((IterableOnceOps)((SeqOps)resolved.records().map((Function1<ResourceRecord, CachePolicy.Ttl> & Serializable)x$2 -> x$2.ttl())).$plus$colon(cachePolicy)).min(package$CachePolicyIsOrdered$.MODULE$);
                                $this.$outer.akka$io$dns$internal$AsyncDnsResolver$$cache.put(new Tuple2<String, DnsProtocol.RequestType>(name, mode), (DnsProtocol.Resolved)resolved, minTtl);
                            } else {
                                CachePolicy.CachePolicy cachePolicy = $this.$outer.negativeCachePolicy();
                                CachePolicy$Never$ cachePolicy$Never$ = CachePolicy$Never$.MODULE$;
                                if (cachePolicy == null ? cachePolicy$Never$ != null : !cachePolicy.equals(cachePolicy$Never$)) {
                                    $this.$outer.akka$io$dns$internal$AsyncDnsResolver$$cache.put(new Tuple2<String, DnsProtocol.RequestType>(name, mode), (DnsProtocol.Resolved)resolved, $this.$outer.negativeCachePolicy());
                                }
                            }
                            $this.$outer.log().debug("{} resolved {}", mode, resolved);
                            return resolved;
                        }, this.$outer.ec()), this.$outer.ec()).pipeTo(this.$outer.sender(), this.$outer.self());
                    } else {
                        throw new MatchError(option);
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return object instanceof DnsProtocol.Resolve;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1(akka.io.dns.internal.AsyncDnsResolver$$anonfun$receive$1 java.lang.String akka.io.dns.DnsProtocol$RequestType akka.io.dns.DnsProtocol$Resolved ), $anonfun$applyOrElse$2(akka.io.dns.ResourceRecord )}, serializedLambda);
            }
        };
    }

    public Future<DnsProtocol.Resolved> akka$io$dns$internal$AsyncDnsResolver$$resolveWithResolvers(String name, DnsProtocol.RequestType requestType, List<ActorRef> resolvers) {
        if (AsyncDnsResolver$.MODULE$.akka$io$dns$internal$AsyncDnsResolver$$isInetAddress(name)) {
            return Future$.MODULE$.fromTry(Try$.MODULE$.apply((Function0<DnsProtocol.Resolved> & Serializable)() -> {
                ARecord record;
                ResourceRecord resourceRecord;
                InetAddress address = InetAddress.getByName(name);
                InetAddress inetAddress = address;
                if (inetAddress instanceof Inet4Address) {
                    resourceRecord = new ARecord(name, CachePolicy$Ttl$.MODULE$.effectivelyForever(), address);
                } else if (inetAddress instanceof Inet6Address) {
                    Inet6Address inet6Address = (Inet6Address)inetAddress;
                    resourceRecord = new AAAARecord(name, CachePolicy$Ttl$.MODULE$.effectivelyForever(), inet6Address);
                } else {
                    throw new IllegalArgumentException(new StringBuilder(20).append("Unexpected address: ").append(inetAddress).toString());
                }
                ARecord aRecord = record = resourceRecord;
                return DnsProtocol$Resolved$.MODULE$.apply(name, Nil$.MODULE$.$colon$colon(aRecord));
            }));
        }
        List<ActorRef> list = resolvers;
        if (((Object)Nil$.MODULE$).equals(list)) {
            return Future$.MODULE$.failed(new ResolveFailedException(new StringBuilder(37).append("Failed to resolve ").append(name).append(" with nameservers: ").append(this.nameServers()).toString()));
        }
        if (list instanceof $colon$colon) {
            $colon$colon $colon$colon = ($colon$colon)list;
            ActorRef head = (ActorRef)$colon$colon.head();
            List tail = $colon$colon.next$access$1();
            return this.resolveWithSearch(name, requestType, head).recoverWith(new Serializable(this, name, requestType, tail){
                private static final long serialVersionUID = 0L;
                private final /* synthetic */ AsyncDnsResolver $outer;
                private final String name$2;
                private final DnsProtocol.RequestType requestType$1;
                private final List tail$1;

                public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                    Option<Throwable> option;
                    A1 A1 = x1;
                    if (A1 != null && !(option = NonFatal$.MODULE$.unapply(A1)).isEmpty()) {
                        Throwable t2 = option.get();
                        Throwable throwable = t2;
                        if (throwable instanceof AskTimeoutException) {
                            this.$outer.log().info("Resolve of {} timed out after {}. Trying next name server", this.name$2, PrettyDuration$PrettyPrintableDuration$.MODULE$.pretty$extension(PrettyDuration$.MODULE$.PrettyPrintableDuration(this.$outer.timeout().duration())));
                        } else {
                            this.$outer.log().info("Resolve of {} failed. Trying next name server {}", this.name$2, t2.getMessage());
                        }
                        return (B1)this.$outer.akka$io$dns$internal$AsyncDnsResolver$$resolveWithResolvers(this.name$2, this.requestType$1, this.tail$1);
                    }
                    return function1.apply(x1);
                }

                public final boolean isDefinedAt(Throwable x1) {
                    Option<Throwable> option;
                    Throwable throwable = x1;
                    return throwable != null && !(option = NonFatal$.MODULE$.unapply(throwable)).isEmpty();
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    this.name$2 = name$2;
                    this.requestType$1 = requestType$1;
                    this.tail$1 = tail$1;
                }
            }, this.ec());
        }
        throw new MatchError(list);
    }

    private Future<DnsClient.Answer> sendQuestion(ActorRef resolver2, DnsClient.DnsQuestion message) {
        Future<DnsClient.Answer> result = AskableActorRef$.MODULE$.$qmark$extension(package$.MODULE$.ask(resolver2), message, this.timeout(), this.self()).mapTo(ClassTag$.MODULE$.apply(DnsClient.Answer.class));
        result.failed().foreach((Function1<Throwable, Object> & Serializable)x$3 -> {
            resolver2.$bang(new DnsClient.DropRequest(message.id()), this.self());
            return BoxedUnit.UNIT;
        }, this.ec());
        return result;
    }

    private Future<DnsProtocol.Resolved> resolveWithSearch(String name, DnsProtocol.RequestType requestType, ActorRef resolver2) {
        if (this.settings.SearchDomains().nonEmpty()) {
            List<String> list;
            Object nameWithSearch = this.settings.SearchDomains().map((Function1<String, String> & Serializable)sd -> new StringBuilder(1).append(name).append(".").append((String)sd).toString());
            if (StringOps$.MODULE$.count$extension(Predef$.MODULE$.augmentString(name), (Function1<Object, Object> & Serializable)x$4 -> BoxesRunTime.boxToBoolean(AsyncDnsResolver.$anonfun$resolveWithSearch$2(BoxesRunTime.unboxToChar(x$4)))) >= this.settings.NDots()) {
                String string = name;
                list = ((List)nameWithSearch).$colon$colon(string);
            } else {
                list = (List<String>)((AbstractSeq)nameWithSearch).$colon$plus(name);
            }
            List<String> toResolve = list;
            return this.resolveFirst(toResolve, requestType, resolver2);
        }
        return this.resolve(name, requestType, resolver2);
    }

    private Future<DnsProtocol.Resolved> resolveFirst(List<String> searchNames, DnsProtocol.RequestType requestType, ActorRef resolver2) {
        boolean bl = false;
        $colon$colon $colon$colon = null;
        List<String> list = searchNames;
        if (list instanceof $colon$colon) {
            bl = true;
            $colon$colon = ($colon$colon)list;
            String searchName = (String)$colon$colon.head();
            List list2 = $colon$colon.next$access$1();
            if (((Object)Nil$.MODULE$).equals(list2)) {
                return this.resolve(searchName, requestType, resolver2);
            }
        }
        if (bl) {
            String searchName = (String)$colon$colon.head();
            List remaining = $colon$colon.next$access$1();
            return this.resolve(searchName, requestType, resolver2).flatMap((Function1<DnsProtocol.Resolved, Future> & Serializable)resolved -> {
                if (resolved.records().isEmpty()) {
                    return this.resolveFirst(remaining, requestType, resolver2);
                }
                return Future$.MODULE$.successful(resolved);
            }, this.ec());
        }
        if (((Object)Nil$.MODULE$).equals(list)) {
            return Future$.MODULE$.failed(new IllegalStateException("Failed to 'resolveFirst': 'searchNames' must not be empty"));
        }
        throw new MatchError(list);
    }

    private Future<DnsProtocol.Resolved> resolve(String name, DnsProtocol.RequestType requestType, ActorRef resolver2) {
        this.log().debug("Attempting to resolve {} with {}", name, resolver2);
        String caseFoldedName = Helpers$.MODULE$.toRootLowerCase(name);
        DnsProtocol.RequestType requestType2 = requestType;
        if (requestType2 instanceof DnsProtocol.Ip) {
            DnsProtocol.Ip ip2 = (DnsProtocol.Ip)requestType2;
            boolean ipv42 = ip2.ipv4();
            boolean ipv6 = ip2.ipv6();
            Future<DnsClient.Answer> ipv4Recs = ipv42 ? this.sendQuestion(resolver2, new DnsClient.Question4(this.nextId(), caseFoldedName)) : AsyncDnsResolver$.MODULE$.akka$io$dns$internal$AsyncDnsResolver$$Empty();
            Future<DnsClient.Answer> ipv6Recs = ipv6 ? this.sendQuestion(resolver2, new DnsClient.Question6(this.nextId(), caseFoldedName)) : AsyncDnsResolver$.MODULE$.akka$io$dns$internal$AsyncDnsResolver$$Empty();
            return ipv4Recs.flatMap((Function1<DnsClient.Answer, Future> & Serializable)ipv4 -> ipv6Recs.map((Function1<DnsClient.Answer, DnsProtocol.Resolved> & Serializable)ipv6 -> new DnsProtocol.Resolved(name, (Seq)ipv4.rrs().$plus$plus(ipv6.rrs()), (Seq)ipv4.additionalRecs().$plus$plus(ipv6.additionalRecs())), this.ec()), this.ec());
        }
        if (DnsProtocol$Srv$.MODULE$.equals(requestType2)) {
            return this.sendQuestion(resolver2, new DnsClient.SrvQuestion(this.nextId(), caseFoldedName)).map((Function1<DnsClient.Answer, DnsProtocol.Resolved> & Serializable)answer -> new DnsProtocol.Resolved(name, answer.rrs(), answer.additionalRecs()), this.ec());
        }
        throw new MatchError(requestType2);
    }

    public static final /* synthetic */ boolean $anonfun$resolveWithSearch$2(char x$4) {
        return x$4 == '.';
    }

    public AsyncDnsResolver(DnsSettings settings, SimpleDnsCache cache, Function2<ActorRefFactory, List<InetSocketAddress>, List<ActorRef>> clientFactory) {
        Tuple2<Inet4Address, InetAddress> tuple2;
        InetAddress loopback;
        this.settings = settings;
        this.akka$io$dns$internal$AsyncDnsResolver$$cache = cache;
        Actor.$init$(this);
        ActorLogging.$init$(this);
        this.ec = this.context().dispatcher();
        InetAddress inetAddress = loopback = InetAddress.getLoopbackAddress();
        if (inetAddress instanceof Inet6Address) {
            Inet6Address inet6Address = (Inet6Address)inetAddress;
            tuple2 = new Tuple2<Inet4Address, InetAddress>((Inet4Address)InetAddress.getByName("127.0.0.1"), inet6Address);
        } else if (inetAddress instanceof Inet4Address) {
            Inet4Address inet4Address = (Inet4Address)inetAddress;
            tuple2 = new Tuple2<Inet4Address, InetAddress>(inet4Address, InetAddress.getByName("::1"));
        } else {
            throw new IllegalArgumentException(new StringBuilder(23).append("Loopback address was [").append(inetAddress).append("]").toString());
        }
        Tuple2<Inet4Address, InetAddress> tuple22 = tuple2;
        if (tuple22 == null) {
            throw new MatchError(tuple22);
        }
        InetAddress ipv4Address = tuple22._1();
        InetAddress ipv6Address = tuple22._2();
        Tuple2<InetAddress, InetAddress> tuple23 = new Tuple2<InetAddress, InetAddress>(ipv4Address, ipv6Address);
        InetAddress ipv4Address2 = tuple23._1();
        InetAddress ipv6Address2 = tuple23._2();
        ARecord aRecord = new ARecord("localhost", CachePolicy$Ttl$.MODULE$.effectivelyForever(), loopback);
        cache.put(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("localhost"), new DnsProtocol.Ip(DnsProtocol$Ip$.MODULE$.apply$default$1(), DnsProtocol$Ip$.MODULE$.apply$default$2())), DnsProtocol$Resolved$.MODULE$.apply("localhost", Nil$.MODULE$.$colon$colon(aRecord)), CachePolicy$Ttl$.MODULE$.effectivelyForever());
        boolean x$1 = false;
        boolean x$2 = true;
        ARecord aRecord2 = new ARecord("localhost", CachePolicy$Ttl$.MODULE$.effectivelyForever(), ipv4Address2);
        cache.put(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("localhost"), new DnsProtocol.Ip(true, false)), DnsProtocol$Resolved$.MODULE$.apply("localhost", Nil$.MODULE$.$colon$colon(aRecord2)), CachePolicy$Ttl$.MODULE$.effectivelyForever());
        boolean x$3 = true;
        boolean x$4 = false;
        ARecord aRecord3 = new ARecord("localhost", CachePolicy$Ttl$.MODULE$.effectivelyForever(), ipv6Address2);
        cache.put(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("localhost"), new DnsProtocol.Ip(false, true)), DnsProtocol$Resolved$.MODULE$.apply("localhost", Nil$.MODULE$.$colon$colon(aRecord3)), CachePolicy$Ttl$.MODULE$.effectivelyForever());
        this.timeout = new Timeout(settings.ResolveTimeout());
        this.nameServers = settings.NameServers();
        this.positiveCachePolicy = settings.PositiveCachePolicy();
        this.negativeCachePolicy = settings.NegativeCachePolicy();
        this.log().debug("Using name servers [{}] and search domains [{}] with ndots={}", this.nameServers(), settings.SearchDomains(), BoxesRunTime.boxToInteger(settings.NDots()));
        this.requestId = 0;
        this.akka$io$dns$internal$AsyncDnsResolver$$resolvers = clientFactory.apply(this.context(), this.nameServers());
        Statics.releaseFence();
    }

    public static class ResolveFailedException
    extends Exception
    implements Product {
        private final String msg;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public String msg() {
            return this.msg;
        }

        public ResolveFailedException copy(String msg) {
            return new ResolveFailedException(msg);
        }

        public String copy$default$1() {
            return this.msg();
        }

        @Override
        public String productPrefix() {
            return "ResolveFailedException";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.msg();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof ResolveFailedException;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "msg";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof ResolveFailedException)) return false;
            boolean bl = true;
            if (!bl) return false;
            ResolveFailedException resolveFailedException = (ResolveFailedException)x$1;
            String string = this.msg();
            String string2 = resolveFailedException.msg();
            if (string == null) {
                if (string2 != null) {
                    return false;
                }
            } else if (!string.equals(string2)) return false;
            if (!resolveFailedException.canEqual(this)) return false;
            return true;
        }

        public ResolveFailedException(String msg) {
            this.msg = msg;
            super(msg);
            Product.$init$(this);
        }
    }
}

