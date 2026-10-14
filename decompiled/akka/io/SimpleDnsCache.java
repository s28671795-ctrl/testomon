/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.NoSerializationVerificationNeeded;
import akka.annotation.InternalApi;
import akka.io.Dns;
import akka.io.Dns$Resolved$;
import akka.io.PeriodicCacheCleanup;
import akka.io.SimpleDnsCache$;
import akka.io.dns.AAAARecord;
import akka.io.dns.ARecord;
import akka.io.dns.CachePolicy;
import akka.io.dns.CachePolicy$Forever$;
import akka.io.dns.CachePolicy$Never$;
import akka.io.dns.DnsProtocol;
import akka.io.dns.DnsProtocol$Ip$;
import akka.io.dns.ResourceRecord;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicReference;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.collection.AbstractIterable;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Map$;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.SortedSet;
import scala.collection.immutable.SortedSet$;
import scala.math.Ordered;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\r\rc\u0001\u0002\u001e<\u0001\u0001CQA\u0014\u0001\u0005\u0002=Cq!\u0015\u0001C\u0002\u0013%!\u000bC\u0004\u0003l\u0002\u0001\u000b\u0011B*\t\u0013\t5\bA1A\u0005\n\u0005E\u0002\u0002\u0003Bx\u0001\u0001\u0006I!a\r\t\u000f\tE\b\u0001\"\u0011\u0003t\"9!\u0011\u001f\u0001\u0005B\r\u0015\u0001bBA'\u0001\u0011E1\u0011\u0004\u0005\t\u0005_\u0001AQA\u001f\u0004\u001c!A!1\b\u0001\u0005\u0006m\u001a)\u0003C\u0004\u0003f\u0001!)ea\u0010\b\u000b\u0005\\\u0004\u0012\u00012\u0007\u000biZ\u0004\u0012A2\t\u000b9kA\u0011\u00016\u0007\u000b-l\u0001a\u000f7\t\u00119|!\u0011!Q\u0001\n=D!\"a\u001c\u0010\u0005\u0003\u0005\u000b\u0011BA9\u0011)\tie\u0004B\u0001B\u0003%!q\u0004\u0005\u0007\u001d>!\tA!\n\t\u000f\t=r\u0002\"\u0001\u00032!9!1H\b\u0005\u0002\tu\u0002b\u0002B3\u001f\u0011\u0005!q\r\u0004\b\u0003sj\u0001iOA>\u0011)\tYi\u0006BK\u0002\u0013\u0005\u0011Q\u0012\u0005\u000b\u0003+;\"\u0011#Q\u0001\n\u0005=\u0005BCA\u0018/\tU\r\u0011\"\u0001\u00022!Q\u0011\u0011H\f\u0003\u0012\u0003\u0006I!a\r\t\r9;B\u0011AAL\u0011\u001d\t\te\u0006C\u0001\u0003?C\u0011\"a)\u0018\u0003\u0003%\t!!*\t\u0013\u0005Mv#%A\u0005\u0002\u0005U\u0006\"CAg/E\u0005I\u0011AAh\u0011%\t9nFA\u0001\n\u0003\nI\u000eC\u0005\u0002h^\t\t\u0011\"\u0001\u0002j\"I\u00111^\f\u0002\u0002\u0013\u0005\u0011Q\u001e\u0005\n\u0003g<\u0012\u0011!C!\u0003kD\u0011\"a@\u0018\u0003\u0003%\tA!\u0001\t\u0013\t\u0015q#!A\u0005B\t\u001d\u0001\"\u0003B\u0006/\u0005\u0005I\u0011\tB\u0007\u0011%\u0011yaFA\u0001\n\u0003\u0012\t\u0002C\u0005\u0003\u0014]\t\t\u0011\"\u0011\u0003\u0016\u001dQ!1N\u0007\u0002\u0002#\u00051H!\u001c\u0007\u0015\u0005eT\"!A\t\u0002m\u0012y\u0007\u0003\u0004OW\u0011\u0005!\u0011\u0010\u0005\n\u0005\u001fY\u0013\u0011!C#\u0005#A\u0011Ba\u001f,\u0003\u0003%\tI! \t\u0013\t-5&!A\u0005\u0002\n5\u0005\"\u0003BRW\u0005\u0005I\u0011\u0002BS\r\u0015IX\u0002A\u001e{\u0011)\tI#\rBC\u0002\u0013\u0005\u00111\u0006\u0005\u000b\u0003[\t$\u0011!Q\u0001\n\u0005M\u0001BCA\u0018c\t\u0015\r\u0011\"\u0001\u00022!Q\u0011\u0011H\u0019\u0003\u0002\u0003\u0006I!a\r\t\r9\u000bD\u0011AA\u001e\u0011\u001d\t\t%\rC\u0001\u0003\u0007Bq!a\u00142\t\u0003\n\t\u0006\u0003\u0005\u0003.6!\ta\u000fBX\u00059\u0019\u0016.\u001c9mK\u0012s7oQ1dQ\u0016T!\u0001P\u001f\u0002\u0005%|'\"\u0001 \u0002\t\u0005\\7.Y\u0002\u0001'\u0011\u0001\u0011)\u0012%\u0011\u0005\t\u001bU\"A\u001e\n\u0005\u0011[$a\u0001#ogB\u0011!IR\u0005\u0003\u000fn\u0012A\u0003U3sS>$\u0017nY\"bG\",7\t\\3b]V\u0004\bCA%M\u001b\u0005Q%BA&>\u0003\u0015\t7\r^8s\u0013\ti%JA\u0011O_N+'/[1mSj\fG/[8o-\u0016\u0014\u0018NZ5dCRLwN\u001c(fK\u0012,G-\u0001\u0004=S:LGO\u0010\u000b\u0002!B\u0011!\tA\u0001\tG\u0006\u001c\u0007.\u001a*fMV\t1\u000bE\u0002U;~k\u0011!\u0016\u0006\u0003-^\u000ba!\u0019;p[&\u001c'B\u0001-Z\u0003)\u0019wN\\2veJ,g\u000e\u001e\u0006\u00035n\u000bA!\u001e;jY*\tA,\u0001\u0003kCZ\f\u0017B\u00010V\u0005=\tEo\\7jGJ+g-\u001a:f]\u000e,\u0007C\u00021\u0010\u0005\u000f\u0014)O\u0004\u0002C\u0019\u0005q1+[7qY\u0016$en]\"bG\",\u0007C\u0001\"\u000e'\tiA\r\u0005\u0002fQ6\taMC\u0001h\u0003\u0015\u00198-\u00197b\u0013\tIgM\u0001\u0004B]f\u0014VM\u001a\u000b\u0002E\n)1)Y2iKV)Q.!\u001c\u0003\u001cM\u0011q\u0002Z\u0001\u0006cV,W/\u001a\t\u0004aV<X\"A9\u000b\u0005I\u001c\u0018!C5n[V$\u0018M\u00197f\u0015\t!h-\u0001\u0006d_2dWm\u0019;j_:L!A^9\u0003\u0013M{'\u000f^3e'\u0016$\b\u0003\u0002=2\u0003Wj\u0011!\u0004\u0002\f\u000bb\u0004\u0018N]=F]R\u0014\u00180F\u0002|\u0003/\u00192!\r3}!\u0015i\u00181BA\t\u001d\rq\u0018q\u0001\b\u0004\u007f\u0006\u0015QBAA\u0001\u0015\r\t\u0019aP\u0001\u0007yI|w\u000e\u001e \n\u0003\u001dL1!!\u0003g\u0003\u001d\u0001\u0018mY6bO\u0016LA!!\u0004\u0002\u0010\t9qJ\u001d3fe\u0016$'bAA\u0005MB!\u00010MA\n!\u0011\t)\"a\u0006\r\u0001\u00119\u0011\u0011D\u0019C\u0002\u0005m!!A&\u0012\t\u0005u\u00111\u0005\t\u0004K\u0006}\u0011bAA\u0011M\n9aj\u001c;iS:<\u0007cA3\u0002&%\u0019\u0011q\u00054\u0003\u0007\u0005s\u00170\u0001\u0003oC6,WCAA\n\u0003\u0015q\u0017-\\3!\u0003\u0015)h\u000e^5m+\t\t\u0019\u0004E\u0002f\u0003kI1!a\u000eg\u0005\u0011auN\\4\u0002\rUtG/\u001b7!)\u0019\t\t\"!\u0010\u0002@!9\u0011\u0011\u0006\u001cA\u0002\u0005M\u0001bBA\u0018m\u0001\u0007\u00111G\u0001\bSN4\u0016\r\\5e)\u0011\t)%a\u0013\u0011\u0007\u0015\f9%C\u0002\u0002J\u0019\u0014qAQ8pY\u0016\fg\u000eC\u0004\u0002N]\u0002\r!a\r\u0002\u000b\rdwnY6\u0002\u000f\r|W\u000e]1sKR!\u00111KA-!\r)\u0017QK\u0005\u0004\u0003/2'aA%oi\"9\u00111\f\u001dA\u0002\u0005E\u0011\u0001\u0002;iCRD3!MA0!\u0011\t\t'a\u001a\u000e\u0005\u0005\r$bAA3{\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005%\u00141\r\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e\u0005\u0003\u0002\u0016\u00055DaBA\r\u001f\t\u0007\u00111D\u0001\u0006G\u0006\u001c\u0007.\u001a\t\ba\u0006M\u00141NA<\u0013\r\t)(\u001d\u0002\u0004\u001b\u0006\u0004\b\u0003\u0002=\u0018\u00053\u0011!bQ1dQ\u0016,e\u000e\u001e:z+\u0011\ti(!%\u0014\r]!\u0017qPAC!\r)\u0017\u0011Q\u0005\u0004\u0003\u00073'a\u0002)s_\u0012,8\r\u001e\t\u0004{\u0006\u001d\u0015\u0002BAE\u0003\u001f\u0011AbU3sS\u0006d\u0017N_1cY\u0016\fa!\u00198to\u0016\u0014XCAAH!\u0011\t)\"!%\u0005\u000f\u0005MuC1\u0001\u0002\u001c\t\tA+A\u0004b]N<XM\u001d\u0011\u0015\r\u0005e\u00151TAO!\u0011Ax#a$\t\u000f\u0005-E\u00041\u0001\u0002\u0010\"9\u0011q\u0006\u000fA\u0002\u0005MB\u0003BA#\u0003CCq!!\u0014\u001e\u0001\u0004\t\u0019$\u0001\u0003d_BLX\u0003BAT\u0003[#b!!+\u00020\u0006E\u0006\u0003\u0002=\u0018\u0003W\u0003B!!\u0006\u0002.\u00129\u00111\u0013\u0010C\u0002\u0005m\u0001\"CAF=A\u0005\t\u0019AAV\u0011%\tyC\bI\u0001\u0002\u0004\t\u0019$\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\t\u0005]\u00161Z\u000b\u0003\u0003sSC!a$\u0002<.\u0012\u0011Q\u0018\t\u0005\u0003\u007f\u000b9-\u0004\u0002\u0002B*!\u00111YAc\u0003%)hn\u00195fG.,GMC\u0002\u0002f\u0019LA!!3\u0002B\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0005\u000f\u0005MuD1\u0001\u0002\u001c\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012T\u0003BAi\u0003+,\"!a5+\t\u0005M\u00121\u0018\u0003\b\u0003'\u0003#\u0019AA\u000e\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011\u00111\u001c\t\u0005\u0003;\f\u0019/\u0004\u0002\u0002`*\u0019\u0011\u0011].\u0002\t1\fgnZ\u0005\u0005\u0003K\fyN\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003'\na\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0003\u0002$\u0005=\b\"CAyG\u0005\u0005\t\u0019AA*\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011q\u001f\t\u0007\u0003s\fY0a\t\u000e\u0003ML1!!@t\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005\u0015#1\u0001\u0005\n\u0003c,\u0013\u0011!a\u0001\u0003G\t!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u00111\u001cB\u0005\u0011%\t\tPJA\u0001\u0002\u0004\t\u0019&\u0001\u0005iCND7i\u001c3f)\t\t\u0019&\u0001\u0005u_N#(/\u001b8h)\t\tY.\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003\u000b\u00129\u0002C\u0005\u0002r&\n\t\u00111\u0001\u0002$A!\u0011Q\u0003B\u000e\t\u001d\u0011ib\u0004b\u0001\u00037\u0011\u0011A\u0016\t\u0006K\n\u0005\u00121G\u0005\u0004\u0005G1'!\u0003$v]\u000e$\u0018n\u001c81)!\u00119C!\u000b\u0003,\t5\u0002C\u0002=\u0010\u0003W\u0012I\u0002C\u0003o'\u0001\u0007q\u000eC\u0004\u0002pM\u0001\r!!\u001d\t\u000f\u000553\u00031\u0001\u0003 \u0005\u0019q-\u001a;\u0015\t\tM\"\u0011\b\t\u0006K\nU\"\u0011D\u0005\u0004\u0005o1'AB(qi&|g\u000eC\u0004\u0002*Q\u0001\r!a\u001b\u0002\u0007A,H\u000f\u0006\u0005\u0003(\t}\"\u0011\tB\"\u0011\u001d\tI#\u0006a\u0001\u0003WBq!a#\u0016\u0001\u0004\u0011I\u0002C\u0004\u0003FU\u0001\rAa\u0012\u0002\u0007Q$H\u000e\u0005\u0003\u0003J\t}c\u0002\u0002B&\u00053rAA!\u0014\u0003V9!!q\nB*\u001d\ry(\u0011K\u0005\u0002}%\u0011A(P\u0005\u0004\u0005/Z\u0014a\u00013og&!!1\fB/\u0003-\u0019\u0015m\u00195f!>d\u0017nY=\u000b\u0007\t]3(\u0003\u0003\u0003b\t\r$aC\"bG\",\u0007k\u001c7jGfTAAa\u0017\u0003^\u000591\r\\3b]V\u0004HC\u0001B\u0014Q\ry\u0011qL\u0001\u000b\u0007\u0006\u001c\u0007.Z#oiJL\bC\u0001=,'\u0011YCM!\u001d\u0011\t\tM$qO\u0007\u0003\u0005kR!\u0001P.\n\t\u0005%%Q\u000f\u000b\u0003\u0005[\nQ!\u00199qYf,BAa \u0003\u0006R1!\u0011\u0011BD\u0005\u0013\u0003B\u0001_\f\u0003\u0004B!\u0011Q\u0003BC\t\u001d\t\u0019J\fb\u0001\u00037Aq!a#/\u0001\u0004\u0011\u0019\tC\u0004\u000209\u0002\r!a\r\u0002\u000fUt\u0017\r\u001d9msV!!q\u0012BN)\u0011\u0011\tJ!(\u0011\u000b\u0015\u0014)Da%\u0011\u000f\u0015\u0014)J!'\u00024%\u0019!q\u00134\u0003\rQ+\b\u000f\\33!\u0011\t)Ba'\u0005\u000f\u0005MuF1\u0001\u0002\u001c!I!qT\u0018\u0002\u0002\u0003\u0007!\u0011U\u0001\u0004q\u0012\u0002\u0004\u0003\u0002=\u0018\u00053\u000bAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"Aa*\u0011\t\u0005u'\u0011V\u0005\u0005\u0005W\u000byN\u0001\u0004PE*,7\r^\u0001\u0014Kb\u0004\u0018N]=F]R\u0014\u0018p\u0014:eKJLgnZ\u000b\u0005\u0005c\u0013\u0019\r\u0006\u0002\u00034J1!Q\u0017BT\u0005s3aAa.:\u0001\tM&\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004#B?\u0003<\n}\u0016\u0002\u0002B_\u0003\u001f\u0011\u0001b\u0014:eKJLgn\u001a\t\u0005qF\u0012\t\r\u0005\u0003\u0002\u0016\t\rGaBA\rs\t\u0007\u00111\u0004\u0015\u0004s\u0005}\u0003cB3\u0003\u0016\n%'q\u001b\t\u0005\u0005\u0017\u0014\u0019N\u0004\u0003\u0003N\n=\u0007CA@g\u0013\r\u0011\tNZ\u0001\u0007!J,G-\u001a4\n\t\u0005\u0015(Q\u001b\u0006\u0004\u0005#4\u0007\u0003\u0002Bm\u0005?tAAa\u0013\u0003\\&!!Q\u001cB/\u0003-!en\u001d)s_R|7m\u001c7\n\t\t\u0005(1\u001d\u0002\f%\u0016\fX/Z:u)f\u0004XM\u0003\u0003\u0003^\nu\u0003\u0003\u0002Bm\u0005OLAA!;\u0003d\nA!+Z:pYZ,G-A\u0005dC\u000eDWMU3gA\u0005Aa.\u00198p\u0005\u0006\u001cX-A\u0005oC:|')Y:fA\u000511-Y2iK\u0012$BA!>\u0004\u0004A)QM!\u000e\u0003xB!!\u0011 B\u0000\u001d\r\u0011%1`\u0005\u0004\u0005{\\\u0014a\u0001#og&!!\u0011^B\u0001\u0015\r\u0011ip\u000f\u0005\b\u0003S1\u0001\u0019\u0001Be)\u0011\u00199aa\u0004\u0011\u000b\u0015\u0014)d!\u0003\u0011\t\r-!q\u001d\b\u0005\u0007\u001b\u0011Y.\u0004\u0002\u0003^!91\u0011C\u0004A\u0002\rM\u0011a\u0002:fcV,7\u000f\u001e\t\u0005\u0007\u0017\u0019)\"\u0003\u0003\u0004\u0018\t\r(a\u0002*fg>dg/\u001a\u000b\u0003\u0003g!Ba!\b\u0004 A)QM!\u000e\u0003f\"91\u0011E\u0005A\u0002\t\u001d\u0017aA6fs\"\u001a\u0011\"a\u0018\u0015\u0011\r\u001d2QFB\u0018\u0007g\u00012!ZB\u0015\u0013\r\u0019YC\u001a\u0002\u0005+:LG\u000fC\u0004\u0004\")\u0001\rAa2\t\u000f\rE\"\u00021\u0001\u0003f\u00069!/Z2pe\u0012\u001c\bb\u0002B#\u0015\u0001\u0007!q\t\u0015\u0004\u0015\r]\u0002\u0003BB\u001d\u0007wi!!!2\n\t\ru\u0012Q\u0019\u0002\bi\u0006LGN]3d)\t\u00199\u0003K\u0002\f\u0007o\u0001")
public class SimpleDnsCache
extends Dns
implements PeriodicCacheCleanup,
NoSerializationVerificationNeeded {
    private final AtomicReference<Cache<Tuple2<String, DnsProtocol.RequestType>, DnsProtocol.Resolved>> cacheRef = new AtomicReference(new Cache((SortedSet)SortedSet$.MODULE$.apply(Nil$.MODULE$, SimpleDnsCache$.MODULE$.expiryEntryOrdering()), (Map)Map$.MODULE$.apply((Seq)Nil$.MODULE$), () -> this.clock()));
    private final long nanoBase = System.nanoTime();

    private AtomicReference<Cache<Tuple2<String, DnsProtocol.RequestType>, DnsProtocol.Resolved>> cacheRef() {
        return this.cacheRef;
    }

    private long nanoBase() {
        return this.nanoBase;
    }

    @Override
    public Option<Dns.Resolved> cached(String name) {
        boolean x$12 = false;
        boolean x$22 = DnsProtocol$Ip$.MODULE$.apply$default$1();
        Object ipv4 = this.cacheRef().get().get(new Tuple2<String, DnsProtocol.Ip>(name, new DnsProtocol.Ip(x$22, false))).toList().flatMap((Function1<DnsProtocol.Resolved, Seq> & Serializable)x$1 -> x$1.records());
        Object ipv6 = this.cacheRef().get().get(new Tuple2<String, DnsProtocol.Ip>(name, new DnsProtocol.Ip(false, DnsProtocol$Ip$.MODULE$.apply$default$2()))).toList().flatMap((Function1<DnsProtocol.Resolved, Seq> & Serializable)x$2 -> x$2.records());
        Object both = this.cacheRef().get().get(new Tuple2<String, DnsProtocol.Ip>(name, new DnsProtocol.Ip(DnsProtocol$Ip$.MODULE$.apply$default$1(), DnsProtocol$Ip$.MODULE$.apply$default$2()))).toList().flatMap((Function1<DnsProtocol.Resolved, Seq> & Serializable)x$3 -> x$3.records());
        Object all = ((List)((IterableOps)((AbstractIterable)ipv4).$plus$plus((IterableOnce)ipv6)).$plus$plus(both)).collect((PartialFunction)((Object)new Serializable(null){
            private static final long serialVersionUID = 0L;

            public final <A1 extends ResourceRecord, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof ARecord) {
                    ARecord aRecord = (ARecord)A1;
                    return (B1)aRecord.ip();
                }
                if (A1 instanceof AAAARecord) {
                    AAAARecord aAAARecord = (AAAARecord)A1;
                    return (B1)aAAARecord.ip();
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(ResourceRecord x1) {
                ResourceRecord resourceRecord = x1;
                if (resourceRecord instanceof ARecord) {
                    return true;
                }
                return resourceRecord instanceof AAAARecord;
            }
        }));
        if (((List)all).isEmpty()) {
            return None$.MODULE$;
        }
        return new Some<Dns.Resolved>(Dns$Resolved$.MODULE$.apply(name, (Iterable<InetAddress>)all));
    }

    @Override
    public Option<DnsProtocol.Resolved> cached(DnsProtocol.Resolve request) {
        return this.cacheRef().get().get(new Tuple2<String, DnsProtocol.RequestType>(request.name(), request.requestType()));
    }

    public long clock() {
        long now = System.nanoTime();
        if (now - this.nanoBase() < 0L) {
            return 0L;
        }
        return (now - this.nanoBase()) / 1000000L;
    }

    @InternalApi
    public final Option<DnsProtocol.Resolved> get(Tuple2<String, DnsProtocol.RequestType> key) {
        return this.cacheRef().get().get(key);
    }

    public final void put(Tuple2<String, DnsProtocol.RequestType> key, DnsProtocol.Resolved records, CachePolicy.CachePolicy ttl) {
        Cache<Tuple2<String, DnsProtocol.RequestType>, DnsProtocol.Resolved> c;
        do {
            c = this.cacheRef().get();
        } while (!this.cacheRef().compareAndSet(c, c.put(key, records, ttl)));
    }

    @Override
    public final void cleanup() {
        Cache<Tuple2<String, DnsProtocol.RequestType>, DnsProtocol.Resolved> c;
        do {
            c = this.cacheRef().get();
        } while (!this.cacheRef().compareAndSet(c, c.cleanup()));
    }

    @InternalApi
    public static class Cache<K, V> {
        private final SortedSet<ExpiryEntry<K>> queue;
        private final Map<K, CacheEntry<V>> cache;
        private final Function0<Object> clock;

        public Option<V> get(K name) {
            return this.cache.get(name).withFilter((Function1<CacheEntry, Object> & Serializable)e -> BoxesRunTime.boxToBoolean(e.isValid(this.clock.apply$mcJ$sp()))).map((Function1<CacheEntry, Object> & Serializable)e -> e.answer());
        }

        public Cache<K, V> put(K name, V answer, CachePolicy.CachePolicy ttl) {
            long l;
            CachePolicy.CachePolicy cachePolicy = ttl;
            if (CachePolicy$Forever$.MODULE$.equals(cachePolicy)) {
                l = Long.MAX_VALUE;
            } else if (CachePolicy$Never$.MODULE$.equals(cachePolicy)) {
                l = this.clock.apply$mcJ$sp() - 1L;
            } else if (cachePolicy instanceof CachePolicy.Ttl) {
                CachePolicy.Ttl ttl2 = (CachePolicy.Ttl)cachePolicy;
                l = this.clock.apply$mcJ$sp() + ttl2.value().toMillis();
            } else {
                throw new MatchError(cachePolicy);
            }
            long until = l;
            return new Cache<K, V>((SortedSet)this.queue.$plus(new ExpiryEntry<K>(name, until)), (Map)this.cache.$plus(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(name), new CacheEntry<V>(answer, until))), this.clock);
        }

        public Cache<K, V> cleanup() {
            long now = this.clock.apply$mcJ$sp();
            SortedSet q = this.queue;
            Map c = this.cache;
            while (q.nonEmpty() && !((ExpiryEntry)q.head()).isValid(now)) {
                ExpiryEntry minEntry = (ExpiryEntry)q.head();
                Object name = minEntry.name();
                q = (SortedSet)q.$minus(minEntry);
                if (!c.get(name).filterNot((Function1<CacheEntry, Object> & Serializable)x$4 -> BoxesRunTime.boxToBoolean(x$4.isValid(now))).isDefined()) continue;
                c = (Map)c.$minus(name);
            }
            return new Cache<K, V>(q, c, this.clock);
        }

        public Cache(SortedSet<ExpiryEntry<K>> queue, Map<K, CacheEntry<V>> cache, Function0<Object> clock) {
            this.queue = queue;
            this.cache = cache;
            this.clock = clock;
        }
    }

    public static class CacheEntry<T>
    implements Product,
    Serializable {
        private final T answer;
        private final long until;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public T answer() {
            return this.answer;
        }

        public long until() {
            return this.until;
        }

        public boolean isValid(long clock) {
            return clock < this.until();
        }

        public <T> CacheEntry<T> copy(T answer, long until) {
            return new CacheEntry<T>(answer, until);
        }

        public <T> T copy$default$1() {
            return this.answer();
        }

        public <T> long copy$default$2() {
            return this.until();
        }

        @Override
        public String productPrefix() {
            return "CacheEntry";
        }

        @Override
        public int productArity() {
            return 2;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.answer();
                }
                case 1: {
                    return BoxesRunTime.boxToLong(this.until());
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
            return x$1 instanceof CacheEntry;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "answer";
                }
                case 1: {
                    return "until";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.anyHash(this.answer()));
            n = Statics.mix(n, Statics.longHash(this.until()));
            return Statics.finalizeHash(n, 2);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof CacheEntry)) return false;
            boolean bl = true;
            if (!bl) return false;
            CacheEntry cacheEntry = (CacheEntry)x$1;
            if (this.until() != cacheEntry.until()) return false;
            if (!BoxesRunTime.equals(this.answer(), cacheEntry.answer())) return false;
            if (!cacheEntry.canEqual(this)) return false;
            return true;
        }

        public CacheEntry(T answer, long until) {
            this.answer = answer;
            this.until = until;
            Product.$init$(this);
        }
    }

    @InternalApi
    public static class ExpiryEntry<K>
    implements Ordered<ExpiryEntry<K>> {
        private final K name;
        private final long until;

        @Override
        public boolean $less(Object that) {
            return Ordered.$less$(this, that);
        }

        @Override
        public boolean $greater(Object that) {
            return Ordered.$greater$(this, that);
        }

        @Override
        public boolean $less$eq(Object that) {
            return Ordered.$less$eq$(this, that);
        }

        @Override
        public boolean $greater$eq(Object that) {
            return Ordered.$greater$eq$(this, that);
        }

        @Override
        public int compareTo(Object that) {
            return Ordered.compareTo$(this, that);
        }

        public K name() {
            return this.name;
        }

        public long until() {
            return this.until;
        }

        public boolean isValid(long clock) {
            return clock < this.until();
        }

        @Override
        public int compare(ExpiryEntry<K> that) {
            return -Predef$.MODULE$.long2Long(this.until()).compareTo(Predef$.MODULE$.long2Long(that.until()));
        }

        public ExpiryEntry(K name, long until) {
            this.name = name;
            this.until = until;
            Ordered.$init$(this);
        }
    }
}

