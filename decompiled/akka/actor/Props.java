/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor;
import akka.actor.Actor$emptyBehavior$;
import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Deploy;
import akka.actor.IndirectActorProducer;
import akka.actor.IndirectActorProducer$;
import akka.actor.Props$;
import akka.actor.Scope;
import akka.actor.SupervisorStrategy;
import akka.annotation.InternalApi;
import akka.japi.Creator;
import akka.routing.RouterConfig;
import com.typesafe.config.Config;
import java.io.Serializable;
import scala.Function0;
import scala.Option;
import scala.PartialFunction;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@ScalaSignature(bytes="\u0006\u0005\r]r!B\u001e=\u0011\u0003\te!B\"=\u0011\u0003!\u0005\"\u0002,\u0002\t\u00039\u0006b\u0002-\u0002\u0005\u0004%)!\u0017\u0005\u0007A\u0006\u0001\u000bQ\u0002.\t\u000f\u0005\f!\u0019!C\u0003E\"1\u0011.\u0001Q\u0001\u000e\rDqA[\u0001C\u0002\u0013\u00151\u000e\u0003\u0004p\u0003\u0001\u0006i\u0001\u001c\u0005\ba\u0006\u0011\r\u0011\"\u0002r\u0011\u001d\u0011y(\u0001Q\u0001\u000eID\u0001B!!\u0002\u0005\u0004%)!\u001d\u0005\b\u0005\u0007\u000b\u0001\u0015!\u0004s\r\u001d\u0011))\u0001\u0001?\u0005\u000fCaAV\u0007\u0005\u0002\t%\u0005b\u0002BH\u001b\u0011\u0005!\u0011\u0013\u0005\b\u0005C\u000bA\u0011\u0001BR\u0011\u001d\u0011\t+\u0001C\u0001\u0005\u007fCqA!7\u0002\t\u0013\u0011Y\u000eC\u0004\u0003\"\u0006!\tAa<\t\u0013\t\u0005\u0016!!A\u0005\u0002\u000e\u0005\u0001\"CB\t\u0003\u0005\u0005I\u0011QB\n\u0011%\u0019i#AA\u0001\n\u0013\u0019yC\u0002\u0003Dy\t\u001b\b\"CA\u0003/\tU\r\u0011\"\u0001l\u0011%\t9a\u0006B\tB\u0003%A\u000e\u0003\u0006\u0002\n]\u0011)\u001a!C\u0001\u0003\u0017A!\"a\n\u0018\u0005#\u0005\u000b\u0011BA\u0007\u0011)\t9d\u0006BK\u0002\u0013\u0005\u0011\u0011\b\u0005\u000b\u0003\u0017:\"\u0011#Q\u0001\n\u0005m\u0002B\u0002,\u0018\t\u0003\ti\u0005C\u0006\u0002^]\u0001\r\u0011!Q!\n\u0005}\u0003bCA7/\u0001\u0007\t\u0011)Q\u0005\u0003_B\u0001\"! \u0018\t\u0003q\u0014q\u0010\u0005\t\u0003\u0003;\u0002\u0015\"\u0003\u0002\u0004\"9\u0011qR\f\u0005\u0002\u0005E\u0005bBAM/\u0011\u0005\u0011\u0011\u0013\u0005\u0007\u00037;B\u0011\u00012\t\u000f\u0005uu\u0003\"\u0001\u0002 \"9\u0011QU\f\u0005\u0002\u0005\u001d\u0006bBAW/\u0011\u0005\u0011q\u0016\u0005\b\u0003k;B\u0011AA\\\u0011\u001d\tYl\u0006C\u0001\u0003{Cq!a/\u0018\t\u0003\t9\u000eC\u0004\u0002b^!\t!a9\t\u0011\u0005=x\u0003\"\u0001?\u0003cD\u0011\"a=\u0018\u0003\u0003%\t!!>\t\u0013\u0005ux#%A\u0005\u0002\u0005}\b\"\u0003B\t/E\u0005I\u0011\u0001B\n\u0011%\u00119cFI\u0001\n\u0003\u0011I\u0003C\u0005\u0003.]\t\t\u0011\"\u0011\u00030!I!QG\f\u0002\u0002\u0013\u0005!q\u0007\u0005\n\u0005\u007f9\u0012\u0011!C\u0001\u0005\u0003B\u0011Ba\u0012\u0018\u0003\u0003%\tE!\u0013\t\u0013\tMs#!A\u0005\u0002\tU\u0003\"\u0003B0/\u0005\u0005I\u0011\tB1\u0011%\u0011)gFA\u0001\n\u0003\u00129\u0007C\u0005\u0003j]\t\t\u0011\"\u0011\u0003l!I!QN\f\u0002\u0002\u0013\u0005#qN\u0001\u0006!J|\u0007o\u001d\u0006\u0003{y\nQ!Y2u_JT\u0011aP\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0005\t\u000bQ\"\u0001\u001f\u0003\u000bA\u0013x\u000e]:\u0014\t\u0005)5J\u0014\t\u0003\r&k\u0011a\u0012\u0006\u0002\u0011\u0006)1oY1mC&\u0011!j\u0012\u0002\u0007\u0003:L(+\u001a4\u0011\u0005\tc\u0015BA'=\u00055\t%m\u001d;sC\u000e$\bK]8qgB\u0011q\nV\u0007\u0002!*\u0011\u0011KU\u0001\u0003S>T\u0011aU\u0001\u0005U\u00064\u0018-\u0003\u0002V!\na1+\u001a:jC2L'0\u00192mK\u00061A(\u001b8jiz\"\u0012!Q\u0001\u000fI\u00164\u0017-\u001e7u\u0007J,\u0017\r^8s+\u0005Q\u0006c\u0001$\\;&\u0011Al\u0012\u0002\n\rVt7\r^5p]B\u0002\"A\u00110\n\u0005}c$!B!di>\u0014\u0018a\u00043fM\u0006,H\u000e^\"sK\u0006$xN\u001d\u0011\u0002%\u0011,g-Y;miJ{W\u000f^3e!J|\u0007o]\u000b\u0002GB\u0011AmZ\u0007\u0002K*\u0011aMP\u0001\be>,H/\u001b8h\u0013\tAWM\u0001\u0007S_V$XM]\"p]\u001aLw-A\neK\u001a\fW\u000f\u001c;S_V$X\r\u001a)s_B\u001c\b%A\u0007eK\u001a\fW\u000f\u001c;EKBdw._\u000b\u0002YB\u0011!)\\\u0005\u0003]r\u0012a\u0001R3qY>L\u0018A\u00043fM\u0006,H\u000e\u001e#fa2|\u0017\u0010I\u0001\u0006K6\u0004H/_\u000b\u0002eB\u0011!iF\n\u0005/\u0015#x\u000f\u0005\u0002Gk&\u0011ao\u0012\u0002\b!J|G-^2u!\rA\u0018\u0011\u0001\b\u0003szt!A_?\u000e\u0003mT!\u0001 !\u0002\rq\u0012xn\u001c;?\u0013\u0005A\u0015BA@H\u0003\u001d\u0001\u0018mY6bO\u0016L1!VA\u0002\u0015\tyx)\u0001\u0004eKBdw._\u0001\bI\u0016\u0004Hn\\=!\u0003\u0015\u0019G.\u0019>{+\t\ti\u0001\r\u0003\u0002\u0010\u0005\r\u0002CBA\t\u00033\tyB\u0004\u0003\u0002\u0014\u0005U\u0001C\u0001>H\u0013\r\t9bR\u0001\u0007!J,G-\u001a4\n\t\u0005m\u0011Q\u0004\u0002\u0006\u00072\f7o\u001d\u0006\u0004\u0003/9\u0005\u0003BA\u0011\u0003Ga\u0001\u0001B\u0006\u0002&m\t\t\u0011!A\u0003\u0002\u0005%\"aA0%g\u000511\r\\1{u\u0002\nB!a\u000b\u00022A\u0019a)!\f\n\u0007\u0005=rIA\u0004O_RD\u0017N\\4\u0011\u0007\u0019\u000b\u0019$C\u0002\u00026\u001d\u00131!\u00118z\u0003\u0011\t'oZ:\u0016\u0005\u0005m\u0002CBA\u001f\u0003\u000f\n\t$\u0004\u0002\u0002@)!\u0011\u0011IA\"\u0003%IW.\\;uC\ndWMC\u0002\u0002F\u001d\u000b!bY8mY\u0016\u001cG/[8o\u0013\u0011\tI%a\u0010\u0003\u0007M+\u0017/A\u0003be\u001e\u001c\b\u0005F\u0004s\u0003\u001f\n\t&a\u0017\t\r\u0005\u0015a\u00041\u0001m\u0011\u001d\tIA\ba\u0001\u0003'\u0002D!!\u0016\u0002ZA1\u0011\u0011CA\r\u0003/\u0002B!!\t\u0002Z\u0011a\u0011QEA)\u0003\u0003\u0005\tQ!\u0001\u0002*!9\u0011q\u0007\u0010A\u0002\u0005m\u0012!C0qe>$WoY3s!\r\u0011\u0015\u0011M\u0005\u0004\u0003Gb$!F%oI&\u0014Xm\u0019;BGR|'\u000f\u0015:pIV\u001cWM\u001d\u0015\u0004?\u0005\u001d\u0004c\u0001$\u0002j%\u0019\u00111N$\u0003\u0013Q\u0014\u0018M\\:jK:$\u0018!E0dC\u000eDW\rZ!di>\u00148\t\\1tgB\"\u0011\u0011OA;!\u0019\t\t\"!\u0007\u0002tA!\u0011\u0011EA;\t-\t9\bIA\u0001\u0002\u0003\u0015\t!!\u001f\u0003\u0007}#C'E\u0002\u0002,uC3\u0001IA4\u0003!\u0001(o\u001c3vG\u0016\u0014XCAA0\u0003A\u0019\u0017m\u00195fI\u0006\u001bGo\u001c:DY\u0006\u001c8/\u0006\u0002\u0002\u0006B\"\u0011qQAF!\u0019\t\t\"!\u0007\u0002\nB!\u0011\u0011EAF\t-\tiIIA\u0001\u0002\u0003\u0015\t!!\u001f\u0003\u0007}#S'\u0001\u0006eSN\u0004\u0018\r^2iKJ,\"!a%\u0011\t\u0005E\u0011QS\u0005\u0005\u0003/\u000biB\u0001\u0004TiJLgnZ\u0001\b[\u0006LGNY8y\u00031\u0011x.\u001e;fe\u000e{gNZ5h\u000399\u0018\u000e\u001e5ESN\u0004\u0018\r^2iKJ$2A]AQ\u0011\u001d\t\u0019K\na\u0001\u0003'\u000b\u0011\u0001Z\u0001\fo&$\b.T1jY\n|\u0007\u0010F\u0002s\u0003SCq!a+(\u0001\u0004\t\u0019*A\u0001n\u0003)9\u0018\u000e\u001e5S_V$XM\u001d\u000b\u0004e\u0006E\u0006BBAZQ\u0001\u00071-A\u0001s\u0003)9\u0018\u000e\u001e5EKBdw.\u001f\u000b\u0004e\u0006e\u0006BBARS\u0001\u0007A.A\u0007xSRD\u0017i\u0019;peR\u000bwm\u001d\u000b\u0004e\u0006}\u0006bBAaU\u0001\u0007\u00111Y\u0001\u0005i\u0006<7\u000fE\u0003G\u0003\u000b\f\u0019*C\u0002\u0002H\u001e\u0013!\u0002\u0010:fa\u0016\fG/\u001a3?Q\rQ\u00131\u001a\t\u0005\u0003\u001b\f\u0019.\u0004\u0002\u0002P*\u0019\u0011\u0011[$\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002V\u0006='a\u0002<be\u0006\u0014xm\u001d\u000b\u0004e\u0006e\u0007bBAaW\u0001\u0007\u00111\u001c\t\u0007\u0003#\ti.a%\n\t\u0005}\u0017Q\u0004\u0002\u0004'\u0016$\u0018AC1di>\u00148\t\\1tgR\u0011\u0011Q\u001d\u0019\u0005\u0003O\fY\u000f\u0005\u0004\u0002\u0012\u0005e\u0011\u0011\u001e\t\u0005\u0003C\tY\u000fB\u0006\u0002n2\n\t\u0011!A\u0003\u0002\u0005e$aA0%m\u0005Aa.Z<BGR|'\u000fF\u0001^\u0003\u0011\u0019w\u000e]=\u0015\u000fI\f90!?\u0002|\"A\u0011Q\u0001\u0018\u0011\u0002\u0003\u0007A\u000eC\u0005\u0002\n9\u0002\n\u00111\u0001\u0002T!I\u0011q\u0007\u0018\u0011\u0002\u0003\u0007\u00111H\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\t\u0011\tAK\u0002m\u0005\u0007Y#A!\u0002\u0011\t\t\u001d!QB\u0007\u0003\u0005\u0013QAAa\u0003\u0002P\u0006IQO\\2iK\u000e\\W\rZ\u0005\u0005\u0005\u001f\u0011IAA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'\u0006\u0002\u0003\u0016A\"!q\u0003B\u0013U\u0011\u0011IBa\u0001\u0011\r\tm!\u0011\u0005B\u0012\u001b\t\u0011iBC\u0002\u0003 I\u000bA\u0001\\1oO&!\u00111\u0004B\u000f!\u0011\t\tC!\n\u0005\u0017\u0005\u0015\u0002'!A\u0001\u0002\u000b\u0005\u0011\u0011F\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00134+\t\u0011YC\u000b\u0003\u0002<\t\r\u0011!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u00032A!!1\u0004B\u001a\u0013\u0011\t9J!\b\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0005\te\u0002c\u0001$\u0003<%\u0019!QH$\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\u0005E\"1\t\u0005\n\u0005\u000b\"\u0014\u0011!a\u0001\u0005s\t1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XC\u0001B&!\u0019\u0011iEa\u0014\u000225\u0011\u00111I\u0005\u0005\u0005#\n\u0019E\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003\u0002B,\u0005;\u00022A\u0012B-\u0013\r\u0011Yf\u0012\u0002\b\u0005>|G.Z1o\u0011%\u0011)ENA\u0001\u0002\u0004\t\t$\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003\u0002B\u0019\u0005GB\u0011B!\u00128\u0003\u0003\u0005\rA!\u000f\u0002\u0011!\f7\u000f[\"pI\u0016$\"A!\u000f\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"A!\r\u0002\r\u0015\fX/\u00197t)\u0011\u00119F!\u001d\t\u0013\t\u0015#(!AA\u0002\u0005E\u0002fB\f\u0003v\tm$Q\u0010\t\u0004\r\n]\u0014b\u0001B=\u000f\n\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0005\u00051Q-\u001c9us\u0002\nq\u0001Z3gCVdG/\u0001\u0005eK\u001a\fW\u000f\u001c;!\u0005))U\u000e\u001d;z\u0003\u000e$xN]\n\u0004\u001b\u0015kFC\u0001BF!\r\u0011i)D\u0007\u0002\u0003\u00059!/Z2fSZ,WC\u0001BJ!\u0011\u0011)Ja'\u000f\u0007\t\u00139*C\u0002\u0003\u001ar\nQ!Q2u_JTAA!(\u0003 \u0006iQ-\u001c9us\n+\u0007.\u0019<j_JT1A!'=\u0003\u0015\t\u0007\u000f\u001d7z+\u0011\u0011)Ka/\u0015\u0005\t\u001dFc\u0001:\u0003*\"I!1\u0016\t\u0002\u0002\u0003\u000f!QV\u0001\u000bKZLG-\u001a8dK\u0012\n\u0004C\u0002BX\u0005k\u0013I,\u0004\u0002\u00032*\u0019!1W$\u0002\u000fI,g\r\\3di&!!q\u0017BY\u0005!\u0019E.Y:t)\u0006<\u0007\u0003BA\u0011\u0005w#qA!0\u0011\u0005\u0004\tIHA\u0001U+\u0011\u0011\tM!4\u0015\t\t\r'q\u001a\u000b\u0004e\n\u0015\u0007\"\u0003Bd#\u0005\u0005\t9\u0001Be\u0003))g/\u001b3f]\u000e,GE\r\t\u0007\u0005_\u0013)La3\u0011\t\u0005\u0005\"Q\u001a\u0003\b\u0005{\u000b\"\u0019AA=\u0011!\u0011\t.\u0005CA\u0002\tM\u0017aB2sK\u0006$xN\u001d\t\u0006\r\nU'1Z\u0005\u0004\u0005/<%\u0001\u0003\u001fcs:\fW.\u001a \u0002\u000f5\\\u0007K]8qgR)!O!8\u0003l\"9!q\u001c\nA\u0002\t\u0005\u0018\u0001D2mCN\u001cxJZ!di>\u0014\b\u0007\u0002Br\u0005O\u0004b!!\u0005\u0002\u001a\t\u0015\b\u0003BA\u0011\u0005O$AB!;\u0003^\u0006\u0005\t\u0011!B\u0001\u0003S\u00111a\u0018\u00132\u0011\u0019\u0011iO\u0005a\u00015\u0006!1\r^8s)\u0015\u0011(\u0011\u001fB\u007f\u0011\u001d\tIa\u0005a\u0001\u0005g\u0004DA!>\u0003zB1\u0011\u0011CA\r\u0005o\u0004B!!\t\u0003z\u0012a!1 By\u0003\u0003\u0005\tQ!\u0001\u0002*\t\u0019q\f\n\u001a\t\u000f\u0005]2\u00031\u0001\u0003\u0000B)a)!2\u00022Q9!oa\u0001\u0004\u0006\r=\u0001BBA\u0003)\u0001\u0007A\u000eC\u0004\u0002\nQ\u0001\raa\u00021\t\r%1Q\u0002\t\u0007\u0003#\tIba\u0003\u0011\t\u0005\u00052Q\u0002\u0003\r\u0003K\u0019)!!A\u0001\u0002\u000b\u0005\u0011\u0011\u0006\u0005\b\u0003o!\u0002\u0019AA\u001e\u0003\u001d)h.\u00199qYf$Ba!\u0006\u0004*A)aia\u0006\u0004\u001c%\u00191\u0011D$\u0003\r=\u0003H/[8o!!15Q\u00047\u0004\"\u0005m\u0012bAB\u0010\u000f\n1A+\u001e9mKN\u0002Daa\t\u0004(A1\u0011\u0011CA\r\u0007K\u0001B!!\t\u0004(\u0011Y\u0011QE\u000b\u0002\u0002\u0003\u0005)\u0011AA\u0015\u0011!\u0019Y#FA\u0001\u0002\u0004\u0011\u0018a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u00111\u0011\u0007\t\u0005\u00057\u0019\u0019$\u0003\u0003\u00046\tu!AB(cU\u0016\u001cG\u000f")
public final class Props
implements Product,
Serializable {
    private static final long serialVersionUID = 2L;
    private final Deploy deploy;
    private final Class<?> clazz;
    private final Seq<Object> args;
    private transient IndirectActorProducer _producer;
    private transient Class<? extends Actor> _cachedActorClass;

    public static Option<Tuple3<Deploy, Class<?>, Seq<Object>>> unapply(Props x$0) {
        return Props$.MODULE$.unapply(x$0);
    }

    public static Props apply(Deploy deploy, Class<?> clazz, Seq<Object> args2) {
        return Props$.MODULE$.apply(deploy, clazz, args2);
    }

    public static Props apply(Class<?> clazz, Seq<Object> args2) {
        return Props$.MODULE$.apply(clazz, args2);
    }

    public static <T extends Actor> Props apply(Function0<T> creator, ClassTag<T> evidence$2) {
        return Props$.MODULE$.apply(creator, evidence$2);
    }

    public static <T extends Actor> Props apply(ClassTag<T> evidence$1) {
        return Props$.MODULE$.apply(evidence$1);
    }

    public static Props default() {
        return Props$.MODULE$.default();
    }

    public static Props empty() {
        return Props$.MODULE$.empty();
    }

    public static Deploy defaultDeploy() {
        return Props$.MODULE$.defaultDeploy();
    }

    public static RouterConfig defaultRoutedProps() {
        return Props$.MODULE$.defaultRoutedProps();
    }

    public static Function0<Actor> defaultCreator() {
        return Props$.MODULE$.defaultCreator();
    }

    public static Props create(Class<?> clazz, Object ... args2) {
        return Props$.MODULE$.create(clazz, args2);
    }

    public static <T extends Actor> Props create(Class<T> actorClass, Creator<T> creator) {
        return Props$.MODULE$.create(actorClass, creator);
    }

    public static <T extends Actor> Props create(Creator<T> creator) {
        return Props$.MODULE$.create(creator);
    }

    public static Props create(Class<?> clazz, Seq<Object> args2) {
        return Props$.MODULE$.create(clazz, args2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Props withActorTags(String ... tags) {
        return this.withActorTags(ScalaRunTime$.MODULE$.wrapRefArray((Object[])tags));
    }

    public Deploy deploy() {
        return this.deploy;
    }

    public Class<?> clazz() {
        return this.clazz;
    }

    public Seq<Object> args() {
        return this.args;
    }

    public IndirectActorProducer producer() {
        block0: {
            if (this._producer != null) break block0;
            this._producer = IndirectActorProducer$.MODULE$.apply(this.clazz(), this.args());
        }
        return this._producer;
    }

    private Class<? extends Actor> cachedActorClass() {
        block0: {
            if (this._cachedActorClass != null) break block0;
            this._cachedActorClass = this.producer().actorClass();
        }
        return this._cachedActorClass;
    }

    public String dispatcher() {
        String string = this.deploy().dispatcher();
        switch (string == null ? 0 : string.hashCode()) {
            case 0: {
                if (!"".equals(string)) break;
                return "akka.actor.default-dispatcher";
            }
        }
        return string;
    }

    public String mailbox() {
        String string = this.deploy().mailbox();
        switch (string == null ? 0 : string.hashCode()) {
            case 0: {
                if (!"".equals(string)) break;
                return "akka.actor.default-mailbox";
            }
        }
        return string;
    }

    public RouterConfig routerConfig() {
        return this.deploy().routerConfig();
    }

    public Props withDispatcher(String d) {
        String string = this.deploy().dispatcher();
        switch (string == null ? 0 : string.hashCode()) {
            case 0: {
                if (!"".equals(string)) break;
                String x$1 = d;
                String x$2 = this.deploy().copy$default$1();
                Config x$3 = this.deploy().copy$default$2();
                RouterConfig x$4 = this.deploy().copy$default$3();
                Scope x$5 = this.deploy().copy$default$4();
                String x$6 = this.deploy().copy$default$6();
                return this.copy(this.deploy().copy(x$2, x$3, x$4, x$5, x$1, x$6), this.copy$default$2(), this.copy$default$3());
            }
        }
        String string2 = string;
        String string3 = d;
        if (!(string2 != null ? !string2.equals(string3) : string3 != null)) {
            return this;
        }
        String x$7 = d;
        String x$8 = this.deploy().copy$default$1();
        Config x$9 = this.deploy().copy$default$2();
        RouterConfig x$10 = this.deploy().copy$default$3();
        Scope x$11 = this.deploy().copy$default$4();
        String x$12 = this.deploy().copy$default$6();
        return this.copy(this.deploy().copy(x$8, x$9, x$10, x$11, x$7, x$12), this.copy$default$2(), this.copy$default$3());
    }

    public Props withMailbox(String m4) {
        String string = this.deploy().mailbox();
        switch (string == null ? 0 : string.hashCode()) {
            case 0: {
                if (!"".equals(string)) break;
                String x$1 = m4;
                String x$2 = this.deploy().copy$default$1();
                Config x$3 = this.deploy().copy$default$2();
                RouterConfig x$4 = this.deploy().copy$default$3();
                Scope x$5 = this.deploy().copy$default$4();
                String x$6 = this.deploy().copy$default$5();
                return this.copy(this.deploy().copy(x$2, x$3, x$4, x$5, x$6, x$1), this.copy$default$2(), this.copy$default$3());
            }
        }
        String string2 = string;
        String string3 = m4;
        if (!(string2 != null ? !string2.equals(string3) : string3 != null)) {
            return this;
        }
        String x$7 = m4;
        String x$8 = this.deploy().copy$default$1();
        Config x$9 = this.deploy().copy$default$2();
        RouterConfig x$10 = this.deploy().copy$default$3();
        Scope x$11 = this.deploy().copy$default$4();
        String x$12 = this.deploy().copy$default$5();
        return this.copy(this.deploy().copy(x$8, x$9, x$10, x$11, x$12, x$7), this.copy$default$2(), this.copy$default$3());
    }

    public Props withRouter(RouterConfig r) {
        RouterConfig x$1 = r;
        String x$2 = this.deploy().copy$default$1();
        Config x$3 = this.deploy().copy$default$2();
        Scope x$4 = this.deploy().copy$default$4();
        String x$5 = this.deploy().copy$default$5();
        String x$6 = this.deploy().copy$default$6();
        return this.copy(this.deploy().copy(x$2, x$3, x$1, x$4, x$5, x$6), this.copy$default$2(), this.copy$default$3());
    }

    public Props withDeploy(Deploy d) {
        return this.copy(d.withFallback(this.deploy()), this.copy$default$2(), this.copy$default$3());
    }

    public Props withActorTags(Seq<String> tags) {
        return this.withActorTags(tags.toSet());
    }

    public Props withActorTags(Set<String> tags) {
        return this.copy(this.deploy().withTags(tags), this.copy$default$2(), this.copy$default$3());
    }

    public Class<? extends Actor> actorClass() {
        return this.cachedActorClass();
    }

    public Actor newActor() {
        return this.producer().produce();
    }

    public Props copy(Deploy deploy, Class<?> clazz, Seq<Object> args2) {
        return new Props(deploy, clazz, args2);
    }

    public Deploy copy$default$1() {
        return this.deploy();
    }

    public Class<?> copy$default$2() {
        return this.clazz();
    }

    public Seq<Object> copy$default$3() {
        return this.args();
    }

    @Override
    public String productPrefix() {
        return "Props";
    }

    @Override
    public int productArity() {
        return 3;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.deploy();
            }
            case 1: {
                return this.clazz();
            }
            case 2: {
                return this.args();
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
        return x$1 instanceof Props;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "deploy";
            }
            case 1: {
                return "clazz";
            }
            case 2: {
                return "args";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
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
        if (!(object instanceof Props)) return false;
        boolean bl = true;
        if (!bl) return false;
        Props props = (Props)x$1;
        Deploy deploy = this.deploy();
        Deploy deploy2 = props.deploy();
        if (deploy == null) {
            if (deploy2 != null) {
                return false;
            }
        } else if (!((Object)deploy).equals(deploy2)) return false;
        Class<?> clazz = this.clazz();
        Class<?> clazz2 = props.clazz();
        if (clazz == null) {
            if (clazz2 != null) {
                return false;
            }
        } else if (!clazz.equals(clazz2)) return false;
        Seq<Object> seq = this.args();
        Seq<Object> seq2 = props.args();
        if (seq == null) {
            if (seq2 == null) return true;
            return false;
        } else {
            if (!seq.equals(seq2)) return false;
            return true;
        }
    }

    public Props(Deploy deploy, Class<?> clazz, Seq<Object> args2) {
        this.deploy = deploy;
        this.clazz = clazz;
        this.args = args2;
        Product.$init$(this);
        Props$.MODULE$.validate(clazz);
        this.producer();
    }

    public static class EmptyActor
    implements Actor {
        private ActorContext context;
        private ActorRef self;

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

        public Actor$emptyBehavior$ receive() {
            return Actor$emptyBehavior$.MODULE$;
        }

        public EmptyActor() {
            Actor.$init$(this);
            Statics.releaseFence();
        }
    }
}

