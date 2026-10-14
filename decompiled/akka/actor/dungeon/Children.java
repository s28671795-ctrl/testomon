/*
 * Decompiled with CFR 0.152.
 */
package akka.actor.dungeon;

import akka.actor.ActorCell;
import akka.actor.ActorCell$;
import akka.actor.ActorPath$;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ChildActorPath;
import akka.actor.ChildNameReserved$;
import akka.actor.ChildRestartStats;
import akka.actor.ChildRestartStats$;
import akka.actor.ChildStats;
import akka.actor.FunctionRef;
import akka.actor.InternalActorRef;
import akka.actor.InvalidActorNameException;
import akka.actor.LocalScope$;
import akka.actor.MinimalActorRef;
import akka.actor.NoSerializationVerificationNeeded;
import akka.actor.Nobody$;
import akka.actor.Props;
import akka.actor.RepointableRef;
import akka.actor.dungeon.AbstractActorCell;
import akka.actor.dungeon.Children$;
import akka.actor.dungeon.ChildrenContainer;
import akka.actor.dungeon.ChildrenContainer$EmptyChildrenContainer$;
import akka.actor.dungeon.ChildrenContainer$TerminatedChildrenContainer$;
import akka.actor.dungeon.Dispatch;
import akka.annotation.InternalStableApi;
import akka.serialization.Serialization;
import akka.serialization.Serialization$;
import akka.serialization.SerializationExtension$;
import akka.serialization.Serializer;
import akka.serialization.Serializers$;
import akka.util.Helpers$;
import akka.util.Unsafe;
import java.io.Serializable;
import java.lang.invoke.LambdaMetafactory;
import java.util.Optional;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.Some;
import scala.Tuple2;
import scala.collection.JavaConverters$;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.Map;
import scala.collection.immutable.Set;
import scala.collection.immutable.TreeMap;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.RichInt$;
import scala.runtime.java8.JFunction1$mcVI$sp;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\tExAB\u001b7\u0011\u0003QDH\u0002\u0004?m!\u0005!h\u0010\u0005\u0006\r\u0006!\t\u0001\u0013\u0005\b\u0013\u0006\u0011\r\u0011\"\u0001K\u0011\u0019\u0011\u0016\u0001)A\u0005\u0017\u001aQaH\u000eI\u0001\u0004\u0003Q4K!9\t\u000bQ+A\u0011A+\t\u000fe+\u0001\u0019!C\u00055\"9!-\u0002a\u0001\n\u0013\u0019\u0007\"\u00024\u0006\t\u0003Q\u0006\"B4\u0006\t\u000bA\u0007\"\u0002;\u0006\t\u000b)\b\"B?\u0006\t\u000bq\bbBA\u0010\u000b\u0011\u0015\u0011\u0011\u0005\u0005\b\u0003K)A\u0011AA\u0014\u0011\u001d\t9$\u0002C\u0001\u0003sAq!a\u000e\u0006\t\u0003\t)\u0005\u0003\u0005\u0002L\u0015!\tAOA'\u0011!\tY%\u0002C\u0001u\u0005m\u0003\"CA2\u000b\u0001\u0007I\u0011BA3\u0011%\t)(\u0002a\u0001\n\u0013\t9\bC\u0004\u0002|\u0015!I!! \t\u0011\u0005\rU\u0001\"\u0001;\u0003\u000bC!\"!'\u0006#\u0003%\tAOAN\u0011!\t\t,\u0002C\u0001u\u0005M\u0006BCAd\u000bE\u0005I\u0011\u0001\u001e\u0002J\"A\u0011QZ\u0003\u0005\u0002i\ny\r\u0003\u0004\u0002V\u0016!\t\"\u0016\u0005\n\u0003/,\u0001\u0019!C\u0005\u00033D\u0011\"a9\u0006\u0001\u0004%I!!:\t\u000f\u0005%X\u0001\"\u0006\u0002l\"9\u0011\u0011^\u0003\u0005\u0016\u0005]\bbBA}\u000b\u0011\u0015\u00111 \u0005\b\u0003\u007f,A\u0011BAm\u0011\u001d\u0011\t!\u0002C\u0007\u0005\u0007AqA!\u0006\u0006\t\u000b\u00119\u0002C\u0004\u0003&\u0015!)Ba\n\t\u000f\t5R\u0001\"\u0002\u00030!9!QH\u0003\u0005\u0016\t}\u0002B\u0002B+\u000b\u0011UQ\u000bC\u0004\u0003X\u0015!\tB!\u0017\t\u000f\tmS\u0001\"\u0005\u0003Z!9!QL\u0003\u0005\u0012\t}\u0003b\u0002B7\u000b\u0011E!q\u000e\u0005\n\u0005\u000f+\u0011\u0013!C\t\u0005\u0013CqA!$\u0006\t#\u0011y\tC\u0004\u0003,\u0016!\tA!,\t\u000f\teV\u0001\"\u0005\u0003<\"9!qX\u0003\u0005\u0012\t\u0005\u0007b\u0002Bc\u000b\u0011\u0005#q\u0019\u0005\b\u0005\u0017,A\u0011\u0003Bg\u0011\u001d\u0011\u0019.\u0002C\u0005\u0005+DqA!7\u0006\t\u0013\u0011Y.\u0001\u0005DQ&dGM]3o\u0015\t9\u0004(A\u0004ek:<Wm\u001c8\u000b\u0005eR\u0014!B1di>\u0014(\"A\u001e\u0002\t\u0005\\7.\u0019\t\u0003{\u0005i\u0011A\u000e\u0002\t\u0007\"LG\u000e\u001a:f]N\u0011\u0011\u0001\u0011\t\u0003\u0003\u0012k\u0011A\u0011\u0006\u0002\u0007\u0006)1oY1mC&\u0011QI\u0011\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?\u0007\u0001!\u0012\u0001P\u0001\n\u000f\u0016$hj\u001c2pIf,\u0012a\u0013\t\u0004\u00032s\u0015BA'C\u0005%1UO\\2uS>t\u0007G\u0004\u0002P!6\t\u0001(\u0003\u0002Rq\u00051aj\u001c2pIf\f!bR3u\u001d>\u0014w\u000eZ=!'\t)\u0001)\u0001\u0004%S:LG\u000f\n\u000b\u0002-B\u0011\u0011iV\u0005\u00031\n\u0013A!\u00168ji\u0006\u0001sl\u00195jY\u0012\u0014XM\u001c*fMN$uNT8u\u0007\u0006dG.T3ESJ,7\r\u001e7z+\u0005Y\u0006CA\u001f]\u0013\tifGA\tDQ&dGM]3o\u0007>tG/Y5oKJD#aB0\u0011\u0005\u0005\u0003\u0017BA1C\u0005!1x\u000e\\1uS2,\u0017\u0001J0dQ&dGM]3o%\u001647\u000fR8O_R\u001c\u0015\r\u001c7NK\u0012K'/Z2uYf|F%Z9\u0015\u0005Y#\u0007bB3\t\u0003\u0003\u0005\raW\u0001\u0004q\u0012\n\u0014\u0001D2iS2$'/\u001a8SK\u001a\u001c\u0018\u0001C2iS2$'/\u001a8\u0016\u0003%\u00042A[8r\u001b\u0005Y'B\u00017n\u0003%IW.\\;uC\ndWM\u0003\u0002o\u0005\u0006Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005A\\'\u0001C%uKJ\f'\r\\3\u0011\u0005=\u0013\u0018BA:9\u0005!\t5\r^8s%\u00164\u0017aC4fi\u000eC\u0017\u000e\u001c3sK:$\u0012A\u001e\t\u0004or\fX\"\u0001=\u000b\u0005eT\u0018\u0001\u00027b]\u001eT\u0011a_\u0001\u0005U\u00064\u0018-\u0003\u0002qq\u0006)1\r[5mIR\u0019q0!\u0002\u0011\t\u0005\u000b\t!]\u0005\u0004\u0003\u0007\u0011%AB(qi&|g\u000eC\u0004\u0002\b1\u0001\r!!\u0003\u0002\t9\fW.\u001a\t\u0005\u0003\u0017\tIB\u0004\u0003\u0002\u000e\u0005U\u0001cAA\b\u00056\u0011\u0011\u0011\u0003\u0006\u0004\u0003'9\u0015A\u0002\u001fs_>$h(C\u0002\u0002\u0018\t\u000ba\u0001\u0015:fI\u00164\u0017\u0002BA\u000e\u0003;\u0011aa\u0015;sS:<'bAA\f\u0005\u0006Aq-\u001a;DQ&dG\rF\u0002r\u0003GAq!a\u0002\u000e\u0001\u0004\tI!A\u0005gS:$7\t[5mIR!\u0011\u0011FA\u001b!\u0015\tY#!\rr\u001b\t\tiCC\u0002\u00020i\fA!\u001e;jY&!\u00111GA\u0017\u0005!y\u0005\u000f^5p]\u0006d\u0007bBA\u0004\u001d\u0001\u0007\u0011\u0011B\u0001\bC\u000e$xN](g)\r\t\u00181\b\u0005\b\u0003{y\u0001\u0019AA \u0003\u0015\u0001(o\u001c9t!\ry\u0015\u0011I\u0005\u0004\u0003\u0007B$!\u0002)s_B\u001cH#B9\u0002H\u0005%\u0003bBA\u001f!\u0001\u0007\u0011q\b\u0005\b\u0003\u000f\u0001\u0002\u0019AA\u0005\u0003-\tG\u000f^1dQ\u000eC\u0017\u000e\u001c3\u0015\u000bE\fy%!\u0015\t\u000f\u0005u\u0012\u00031\u0001\u0002@!9\u00111K\tA\u0002\u0005U\u0013!D:zgR,WnU3sm&\u001cW\rE\u0002B\u0003/J1!!\u0017C\u0005\u001d\u0011un\u001c7fC:$r!]A/\u0003?\n\t\u0007C\u0004\u0002>I\u0001\r!a\u0010\t\u000f\u0005\u001d!\u00031\u0001\u0002\n!9\u00111\u000b\nA\u0002\u0005U\u0013\u0001I0gk:\u001cG/[8o%\u001647\u000fR8O_R\u001c\u0015\r\u001c7NK\u0012K'/Z2uYf,\"!a\u001a\u0011\u000f)\fI'!\u0003\u0002n%\u0019\u00111N6\u0003\u00075\u000b\u0007\u000fE\u0002P\u0003_J1!!\u001d9\u0005-1UO\\2uS>t'+\u001a4)\u0005My\u0016\u0001J0gk:\u001cG/[8o%\u001647\u000fR8O_R\u001c\u0015\r\u001c7NK\u0012K'/Z2uYf|F%Z9\u0015\u0007Y\u000bI\b\u0003\u0005f)\u0005\u0005\t\u0019AA4\u000311WO\\2uS>t'+\u001a4t+\t\ty\b\u0005\u0005\u0002\f\u0005\u0005\u0015\u0011BA7\u0013\u0011\tY'!\b\u0002-\u001d,GOR;oGRLwN\u001c*fM>\u0013hj\u001c2pIf$b!a\"\u0002\u000e\u0006=\u0005cA(\u0002\n&\u0019\u00111\u0012\u001d\u0003!%sG/\u001a:oC2\f5\r^8s%\u00164\u0007bBA\u0004-\u0001\u0007\u0011\u0011\u0002\u0005\n\u0003#3\u0002\u0013!a\u0001\u0003'\u000b1!^5e!\r\t\u0015QS\u0005\u0004\u0003/\u0013%aA%oi\u0006\u0001s-\u001a;Gk:\u001cG/[8o%\u00164wJ\u001d(pE>$\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00133+\t\tiJ\u000b\u0003\u0002\u0014\u0006}5FAAQ!\u0011\t\u0019+!,\u000e\u0005\u0005\u0015&\u0002BAT\u0003S\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005-&)\u0001\u0006b]:|G/\u0019;j_:LA!a,\u0002&\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\u0005$GMR;oGRLwN\u001c*fMR1\u0011QNA[\u0003\u000bDq!a.\u0019\u0001\u0004\tI,A\u0001g!\u001d\t\u00151X9\u0002@ZK1!!0C\u0005%1UO\\2uS>t'\u0007E\u0002B\u0003\u0003L1!a1C\u0005\r\te.\u001f\u0005\n\u0003\u000fA\u0002\u0013!a\u0001\u0003\u0013\t\u0001$\u00193e\rVt7\r^5p]J+g\r\n3fM\u0006,H\u000e\u001e\u00133+\t\tYM\u000b\u0003\u0002\n\u0005}\u0015!\u0005:f[>4XMR;oGRLwN\u001c*fMR!\u0011QKAi\u0011\u001d\t\u0019N\u0007a\u0001\u0003[\n1A]3g\u0003A\u0019Ho\u001c9Gk:\u001cG/[8o%\u001647/\u0001\u000f`]\u0016DHOT1nK\u0012{gj\u001c;DC2dW*\u001a#je\u0016\u001cG\u000f\\=\u0016\u0005\u0005m\u0007cA!\u0002^&\u0019\u0011q\u001c\"\u0003\t1{gn\u001a\u0015\u00039}\u000b\u0001e\u00188fqRt\u0015-\\3E_:{GoQ1mY6+G)\u001b:fGRd\u0017p\u0018\u0013fcR\u0019a+a:\t\u0011\u0015l\u0012\u0011!a\u0001\u00037\f!B]1oI>lg*Y7f)\u0011\tI!!<\t\u000f\u0005=h\u00041\u0001\u0002r\u0006\u00111O\u0019\t\u0004o\u0006M\u0018bAA{q\ni1\u000b\u001e:j]\u001e\u0014U/\u001b7eKJ$\"!!\u0003\u0002\tM$x\u000e\u001d\u000b\u0004-\u0006u\b\"B\u001d!\u0001\u0004\t\u0018\u0001H0qe\u00164XM\u001c;Qe&4\u0018\r^3V]V\u001cX\rZ#sCN,(/Z\u0001\u0011g^\f\u0007o\u00115jY\u0012\u0014XM\u001c*fMN$b!!\u0016\u0003\u0006\t%\u0001B\u0002B\u0004E\u0001\u00071,A\u0006pY\u0012\u001c\u0005.\u001b7ee\u0016t\u0007B\u0002B\u0006E\u0001\u00071,A\u0006oK^\u001c\u0005.\u001b7ee\u0016t\u0007f\u0001\u0012\u0003\u0010A\u0019\u0011I!\u0005\n\u0007\tM!I\u0001\u0004j]2Lg.Z\u0001\re\u0016\u001cXM\u001d<f\u0007\"LG\u000e\u001a\u000b\u0005\u0003+\u0012I\u0002C\u0004\u0002\b\r\u0002\r!!\u0003)\u0007\r\u0012i\u0002\u0005\u0003\u0003 \t\u0005RBAAU\u0013\u0011\u0011\u0019#!+\u0003\u000fQ\f\u0017\u000e\u001c:fG\u0006qQO\u001c:fg\u0016\u0014h/Z\"iS2$G\u0003BA+\u0005SAq!a\u0002%\u0001\u0004\tI\u0001K\u0002%\u0005;\t\u0011\"\u001b8ji\u000eC\u0017\u000e\u001c3\u0015\t\tE\"\u0011\b\t\u0006\u0003\u0006\u0005!1\u0007\t\u0004\u001f\nU\u0012b\u0001B\u001cq\t\t2\t[5mIJ+7\u000f^1siN#\u0018\r^:\t\r\u0005MW\u00051\u0001rQ\r)#QD\u0001\u001dg\u0016$8\t[5mIJ,g\u000eV3s[&t\u0017\r^5p]J+\u0017m]8o)\u0011\t)F!\u0011\t\u000f\t\rc\u00051\u0001\u0003F\u00051!/Z1t_:\u0004BAa\u0012\u0003N9\u0019QH!\u0013\n\u0007\t-c'A\tDQ&dGM]3o\u0007>tG/Y5oKJLAAa\u0014\u0003R\ti1+^:qK:$'+Z1t_:T1Aa\u00137Q\r1#QD\u0001\u000eg\u0016$H+\u001a:nS:\fG/\u001a3\u0002\u0011%\u001chj\u001c:nC2,\"!!\u0016\u0002\u001b%\u001cH+\u001a:nS:\fG/\u001b8h\u0003a9\u0018-\u001b;j]\u001e4uN]\"iS2$'/\u001a8Pe:+H\u000e\\\u000b\u0003\u0005C\u0012bAa\u0019\u0003F\t\u001ddA\u0002B3\u0001\u0001\u0011\tG\u0001\u0007=e\u00164\u0017N\\3nK:$h\b\u0005\u0003\u0003H\t%\u0014\u0002\u0002B6\u0005#\u0012!cV1ji&twMR8s\u0007\"LG\u000e\u001a:f]\u0006y1/^:qK:$7\t[5mIJ,g\u000eF\u0002W\u0005cB\u0011Ba\u001d,!\u0003\u0005\rA!\u001e\u0002\u0013\u0015D8-\u001a9u\r>\u0014\b#BA\u0006\u0005o\n\u0018\u0002\u0002B=\u0003;\u00111aU3uQ\rY#Q\u0010\t\u0005\u0005\u007f\u0012\u0019)\u0004\u0002\u0003\u0002*\u0019\u00111\u0016\u001e\n\t\t\u0015%\u0011\u0011\u0002\u0012\u0013:$XM\u001d8bYN#\u0018M\u00197f\u0003BL\u0017!G:vgB,g\u000eZ\"iS2$'/\u001a8%I\u00164\u0017-\u001e7uIE*\"Aa#+\t\tU\u0014qT\u0001\u000fe\u0016\u001cX/\\3DQ&dGM]3o)\u00151&\u0011\u0013BT\u0011\u001d\u0011\u0019*\fa\u0001\u0005+\u000bqbY1vg\u0016$')\u001f$bS2,(/\u001a\t\u0005\u0005/\u0013\tK\u0004\u0003\u0003\u001a\nue\u0002BA\b\u00057K\u0011aQ\u0005\u0004\u0005?\u0013\u0015a\u00029bG.\fw-Z\u0005\u0005\u0005G\u0013)KA\u0005UQJ|w/\u00192mK*\u0019!q\u0014\"\t\r\t%V\u00061\u0001r\u0003\u0011\u0001XM\u001d9\u0002\u001d\u001d,Go\u00115jY\u0012\u0014\u0015PT1nKR!!q\u0016B\\!\u0015\t\u0015\u0011\u0001BY!\ry%1W\u0005\u0004\u0005kC$AC\"iS2$7\u000b^1ug\"9\u0011q\u0001\u0018A\u0002\u0005%\u0011!D4fi\u000eC\u0017\u000e\u001c3CsJ+g\r\u0006\u0003\u00032\tu\u0006BBAj_\u0001\u0007\u0011/\u0001\thKR\fE\u000e\\\"iS2$7\u000b^1ugV\u0011!1\u0019\t\u0005U>\u0014\u0019$\u0001\bhKR\u001c\u0016N\\4mK\u000eC\u0017\u000e\u001c3\u0015\t\u0005\u001d%\u0011\u001a\u0005\b\u0003\u000f\t\u0004\u0019AA\u0005\u0003q\u0011X-\\8wK\u000eC\u0017\u000e\u001c3B]\u0012<U\r^*uCR,7\t[1oO\u0016$BAa4\u0003RB)\u0011)!\u0001\u0003F!)QP\ra\u0001c\u0006I1\r[3dW:\u000bW.\u001a\u000b\u0005\u0003\u0013\u00119\u000eC\u0004\u0002\bM\u0002\r!!\u0003\u0002\u00135\f7.Z\"iS2$GcC9\u0003^\n\u001d(\u0011\u001eBv\u0005_DqAa85\u0001\u0004\u0011\t/\u0001\u0003dK2d\u0007cA(\u0003d&\u0019!Q\u001d\u001d\u0003\u0013\u0005\u001bGo\u001c:DK2d\u0007bBA\u001fi\u0001\u0007\u0011q\b\u0005\b\u0003\u000f!\u0004\u0019AA\u0005\u0011\u001d\u0011i\u000f\u000ea\u0001\u0003+\nQ!Y:z]\u000eDq!a\u00155\u0001\u0004\t)\u0006")
public interface Children {
    public static Function0<Nobody$> GetNobody() {
        return Children$.MODULE$.GetNobody();
    }

    public ChildrenContainer akka$actor$dungeon$Children$$_childrenRefsDoNotCallMeDirectly();

    public void akka$actor$dungeon$Children$$_childrenRefsDoNotCallMeDirectly_$eq(ChildrenContainer var1);

    public static /* synthetic */ ChildrenContainer childrenRefs$(Children $this) {
        return $this.childrenRefs();
    }

    default public ChildrenContainer childrenRefs() {
        return (ChildrenContainer)Unsafe.instance.getObjectVolatile(this, AbstractActorCell.childrenOffset);
    }

    public static /* synthetic */ Iterable children$(Children $this) {
        return $this.children();
    }

    default public Iterable<ActorRef> children() {
        return this.childrenRefs().children();
    }

    public static /* synthetic */ java.lang.Iterable getChildren$(Children $this) {
        return $this.getChildren();
    }

    default public java.lang.Iterable<ActorRef> getChildren() {
        return JavaConverters$.MODULE$.asJavaIterableConverter(this.children()).asJava();
    }

    public static /* synthetic */ Option child$(Children $this, String name) {
        return $this.child(name);
    }

    default public Option<ActorRef> child(String name) {
        return Option$.MODULE$.apply(this.getChild(name));
    }

    public static /* synthetic */ ActorRef getChild$(Children $this, String name) {
        return $this.getChild(name);
    }

    default public ActorRef getChild(String name) {
        Some some;
        ChildStats s2;
        Option<ChildStats> option = this.childrenRefs().getByName(name);
        if (option instanceof Some && (s2 = (ChildStats)(some = (Some)option).value()) instanceof ChildRestartStats) {
            ChildRestartStats childRestartStats = (ChildRestartStats)s2;
            return childRestartStats.child();
        }
        return null;
    }

    public static /* synthetic */ Optional findChild$(Children $this, String name) {
        return $this.findChild(name);
    }

    default public Optional<ActorRef> findChild(String name) {
        return Optional.ofNullable(this.getChild(name));
    }

    public static /* synthetic */ ActorRef actorOf$(Children $this, Props props) {
        return $this.actorOf(props);
    }

    default public ActorRef actorOf(Props props) {
        return this.makeChild((ActorCell)this, props, this.randomName(), false, false);
    }

    public static /* synthetic */ ActorRef actorOf$(Children $this, Props props, String name) {
        return $this.actorOf(props, name);
    }

    default public ActorRef actorOf(Props props, String name) {
        return this.makeChild((ActorCell)this, props, this.checkName(name), false, false);
    }

    public static /* synthetic */ ActorRef attachChild$(Children $this, Props props, boolean systemService) {
        return $this.attachChild(props, systemService);
    }

    default public ActorRef attachChild(Props props, boolean systemService) {
        return this.makeChild((ActorCell)this, props, this.randomName(), true, systemService);
    }

    public static /* synthetic */ ActorRef attachChild$(Children $this, Props props, String name, boolean systemService) {
        return $this.attachChild(props, name, systemService);
    }

    default public ActorRef attachChild(Props props, String name, boolean systemService) {
        return this.makeChild((ActorCell)this, props, this.checkName(name), true, systemService);
    }

    public Map<String, FunctionRef> akka$actor$dungeon$Children$$_functionRefsDoNotCallMeDirectly();

    public void akka$actor$dungeon$Children$$_functionRefsDoNotCallMeDirectly_$eq(Map<String, FunctionRef> var1);

    private Map<String, FunctionRef> functionRefs() {
        return (Map)Unsafe.instance.getObjectVolatile(this, AbstractActorCell.functionRefsOffset);
    }

    public static /* synthetic */ InternalActorRef getFunctionRefOrNobody$(Children $this, String name, int uid) {
        return $this.getFunctionRefOrNobody(name, uid);
    }

    default public InternalActorRef getFunctionRefOrNobody(String name, int uid) {
        MinimalActorRef minimalActorRef = this.functionRefs().getOrElse(name, Children$.MODULE$.GetNobody());
        if (minimalActorRef instanceof FunctionRef) {
            FunctionRef functionRef = (FunctionRef)minimalActorRef;
            if (uid == 0 || functionRef.path().uid() == uid) {
                return functionRef;
            }
            return Nobody$.MODULE$;
        }
        return (InternalActorRef)((Object)minimalActorRef);
    }

    public static /* synthetic */ int getFunctionRefOrNobody$default$2$(Children $this) {
        return $this.getFunctionRefOrNobody$default$2();
    }

    default public int getFunctionRefOrNobody$default$2() {
        return 0;
    }

    public static /* synthetic */ FunctionRef addFunctionRef$(Children $this, Function2 f, String name) {
        return $this.addFunctionRef(f, name);
    }

    default public FunctionRef addFunctionRef(Function2<ActorRef, Object, BoxedUnit> f, String name) {
        String r = this.randomName(new StringBuilder("$$"));
        String string = name;
        String string2 = "";
        String n = (string == null ? string2 != null : !string.equals(string2)) ? new StringBuilder(1).append(r).append("-").append(name).toString() : r;
        ChildActorPath childPath = new ChildActorPath(((ActorCell)this).self().path(), n, ActorCell$.MODULE$.newUid());
        FunctionRef ref = new FunctionRef(childPath, ((ActorCell)this).provider(), ((ActorCell)this).system(), f);
        this.rec$1(childPath, ref);
        return ref;
    }

    public static /* synthetic */ String addFunctionRef$default$2$(Children $this) {
        return $this.addFunctionRef$default$2();
    }

    default public String addFunctionRef$default$2() {
        return "";
    }

    public static /* synthetic */ boolean removeFunctionRef$(Children $this, FunctionRef ref) {
        return $this.removeFunctionRef(ref);
    }

    default public boolean removeFunctionRef(FunctionRef ref) {
        Predef$.MODULE$.require(ref.path().parent() == ((ActorCell)this).self().path(), (Function0<Object>)(Function0<String> & Serializable)() -> "trying to remove FunctionRef from wrong ActorCell");
        String name = ref.path().name();
        return this.rec$2(name, ref);
    }

    public static /* synthetic */ void stopFunctionRefs$(Children $this) {
        $this.stopFunctionRefs();
    }

    default public void stopFunctionRefs() {
        Map refs = (Map)Unsafe.instance.getAndSetObject(this, AbstractActorCell.functionRefsOffset, Predef$.MODULE$.Map().empty());
        refs.valuesIterator().foreach((Function1<FunctionRef, Object> & Serializable)x$1 -> {
            x$1.stop();
            return BoxedUnit.UNIT;
        });
    }

    public long akka$actor$dungeon$Children$$_nextNameDoNotCallMeDirectly();

    public void akka$actor$dungeon$Children$$_nextNameDoNotCallMeDirectly_$eq(long var1);

    public static /* synthetic */ String randomName$(Children $this, StringBuilder sb) {
        return $this.randomName(sb);
    }

    default public String randomName(StringBuilder sb) {
        long num = Unsafe.instance.getAndAddLong(this, AbstractActorCell.nextNameOffset, 1L);
        return Helpers$.MODULE$.base64(num, sb);
    }

    public static /* synthetic */ String randomName$(Children $this) {
        return $this.randomName();
    }

    default public String randomName() {
        long num = Unsafe.instance.getAndAddLong(this, AbstractActorCell.nextNameOffset, 1L);
        return Helpers$.MODULE$.base64(num, Helpers$.MODULE$.base64$default$2());
    }

    public static /* synthetic */ void stop$(Children $this, ActorRef actor) {
        $this.stop(actor);
    }

    default public void stop(ActorRef actor) {
        Serializable serializable;
        if (this.childrenRefs().getByRef(actor).isDefined()) {
            boolean bl;
            ActorRef actorRef2 = actor;
            if (actorRef2 instanceof RepointableRef) {
                ActorRef actorRef3 = actorRef2;
                bl = ((RepointableRef)((Object)actorRef3)).isStarted();
            } else {
                bl = true;
            }
            serializable = bl ? BoxesRunTime.boxToBoolean(this.shallDie$1(actor)) : BoxedUnit.UNIT;
        } else {
            serializable = BoxedUnit.UNIT;
        }
        ((InternalActorRef)actor).stop();
    }

    private long _preventPrivateUnusedErasure() {
        this.akka$actor$dungeon$Children$$_childrenRefsDoNotCallMeDirectly();
        this.akka$actor$dungeon$Children$$_functionRefsDoNotCallMeDirectly();
        return this.akka$actor$dungeon$Children$$_nextNameDoNotCallMeDirectly();
    }

    private boolean swapChildrenRefs(ChildrenContainer oldChildren, ChildrenContainer newChildren) {
        return Unsafe.instance.compareAndSwapObject(this, AbstractActorCell.childrenOffset, oldChildren, newChildren);
    }

    public static /* synthetic */ boolean reserveChild$(Children $this, String name) {
        return $this.reserveChild(name);
    }

    default public boolean reserveChild(String name) {
        ChildrenContainer c;
        while (!this_.swapChildrenRefs(c = this_.childrenRefs(), c.reserve(name))) {
            Children this_ = (ActorCell)this_;
        }
        return true;
    }

    public static /* synthetic */ boolean unreserveChild$(Children $this, String name) {
        return $this.unreserveChild(name);
    }

    default public boolean unreserveChild(String name) {
        ChildrenContainer c;
        while (!this_.swapChildrenRefs(c = this_.childrenRefs(), c.unreserve(name))) {
            Children this_ = (ActorCell)this_;
        }
        return true;
    }

    public static /* synthetic */ Option initChild$(Children $this, ActorRef ref) {
        return $this.initChild(ref);
    }

    default public Option<ChildRestartStats> initChild(ActorRef ref) {
        Option<ChildStats> option;
        while (true) {
            ChildStats childStats;
            ChildrenContainer cc = this_.childrenRefs();
            boolean bl = false;
            Some some = null;
            option = cc.getByName(ref.path().name());
            if (option instanceof Some) {
                bl = true;
                some = (Some)option;
                if (some.value() instanceof ChildRestartStats) {
                    return some;
                }
            }
            if (!bl || !ChildNameReserved$.MODULE$.equals(childStats = (ChildStats)some.value())) break;
            ChildRestartStats crs = new ChildRestartStats(ref, ChildRestartStats$.MODULE$.apply$default$2(), ChildRestartStats$.MODULE$.apply$default$3());
            String name = ref.path().name();
            if (this_.swapChildrenRefs(cc, cc.add(name, crs))) {
                return new Some<ChildRestartStats>(crs);
            }
            Children this_ = (ActorCell)this_;
        }
        if (None$.MODULE$.equals(option)) {
            return None$.MODULE$;
        }
        throw new MatchError(option);
    }

    public static /* synthetic */ boolean setChildrenTerminationReason$(Children $this, ChildrenContainer.SuspendReason reason) {
        return $this.setChildrenTerminationReason(reason);
    }

    default public boolean setChildrenTerminationReason(ChildrenContainer.SuspendReason reason) {
        ChildrenContainer childrenContainer;
        while ((childrenContainer = this_.childrenRefs()) instanceof ChildrenContainer.TerminatingChildrenContainer) {
            Set<ActorRef> x$3;
            ChildrenContainer.TerminatingChildrenContainer terminatingChildrenContainer = (ChildrenContainer.TerminatingChildrenContainer)childrenContainer;
            ChildrenContainer.SuspendReason x$1 = reason;
            TreeMap<String, ChildStats> x$2 = terminatingChildrenContainer.copy$default$1();
            if (!this_.swapChildrenRefs(terminatingChildrenContainer, terminatingChildrenContainer.copy(x$2, x$3 = terminatingChildrenContainer.copy$default$2(), x$1))) {
                Children this_ = (ActorCell)this_;
                continue;
            }
            return true;
        }
        return false;
    }

    public static /* synthetic */ void setTerminated$(Children $this) {
        $this.setTerminated();
    }

    default public void setTerminated() {
        Unsafe.instance.putObjectVolatile(this, AbstractActorCell.childrenOffset, ChildrenContainer$TerminatedChildrenContainer$.MODULE$);
    }

    public static /* synthetic */ boolean isNormal$(Children $this) {
        return $this.isNormal();
    }

    default public boolean isNormal() {
        return this.childrenRefs().isNormal();
    }

    public static /* synthetic */ boolean isTerminating$(Children $this) {
        return $this.isTerminating();
    }

    default public boolean isTerminating() {
        return this.childrenRefs().isTerminating();
    }

    public static /* synthetic */ ChildrenContainer.SuspendReason waitingForChildrenOrNull$(Children $this) {
        return $this.waitingForChildrenOrNull();
    }

    default public ChildrenContainer.SuspendReason waitingForChildrenOrNull() {
        ChildrenContainer.TerminatingChildrenContainer terminatingChildrenContainer;
        ChildrenContainer.SuspendReason w;
        ChildrenContainer childrenContainer = this.childrenRefs();
        if (childrenContainer instanceof ChildrenContainer.TerminatingChildrenContainer && (w = (terminatingChildrenContainer = (ChildrenContainer.TerminatingChildrenContainer)childrenContainer).reason()) instanceof ChildrenContainer.WaitingForChildren) {
            ChildrenContainer.SuspendReason suspendReason = w;
            return suspendReason;
        }
        return null;
    }

    public static /* synthetic */ void suspendChildren$(Children $this, Set exceptFor) {
        $this.suspendChildren(exceptFor);
    }

    @InternalStableApi
    default public void suspendChildren(Set<ActorRef> exceptFor) {
        this.childrenRefs().stats().foreach((Function1<ChildRestartStats, Object> & Serializable)x0$1 -> {
            Children.$anonfun$suspendChildren$1(exceptFor, x0$1);
            return BoxedUnit.UNIT;
        });
    }

    public static /* synthetic */ Set suspendChildren$default$1$(Children $this) {
        return $this.suspendChildren$default$1();
    }

    default public Set<ActorRef> suspendChildren$default$1() {
        return Predef$.MODULE$.Set().empty();
    }

    public static /* synthetic */ void resumeChildren$(Children $this, Throwable causedByFailure, ActorRef perp) {
        $this.resumeChildren(causedByFailure, perp);
    }

    default public void resumeChildren(Throwable causedByFailure, ActorRef perp) {
        this.childrenRefs().stats().foreach((Function1<ChildRestartStats, Object> & Serializable)x0$1 -> {
            Children.$anonfun$resumeChildren$1(perp, causedByFailure, x0$1);
            return BoxedUnit.UNIT;
        });
    }

    public static /* synthetic */ Option getChildByName$(Children $this, String name) {
        return $this.getChildByName(name);
    }

    default public Option<ChildStats> getChildByName(String name) {
        return this.childrenRefs().getByName(name);
    }

    public static /* synthetic */ Option getChildByRef$(Children $this, ActorRef ref) {
        return $this.getChildByRef(ref);
    }

    default public Option<ChildRestartStats> getChildByRef(ActorRef ref) {
        return this.childrenRefs().getByRef(ref);
    }

    public static /* synthetic */ Iterable getAllChildStats$(Children $this) {
        return $this.getAllChildStats();
    }

    default public Iterable<ChildRestartStats> getAllChildStats() {
        return this.childrenRefs().stats();
    }

    public static /* synthetic */ InternalActorRef getSingleChild$(Children $this, String name) {
        return $this.getSingleChild(name);
    }

    default public InternalActorRef getSingleChild(String name) {
        Some some;
        ChildStats crs;
        if (name.indexOf(35) == -1) {
            Some some2;
            ChildStats crs2;
            Option<ChildStats> option = this.getChildByName(name);
            if (option instanceof Some && (crs2 = (ChildStats)(some2 = (Some)option).value()) instanceof ChildRestartStats) {
                ChildRestartStats childRestartStats = (ChildRestartStats)crs2;
                return (InternalActorRef)childRestartStats.child();
            }
            return this.getFunctionRefOrNobody(name, this.getFunctionRefOrNobody$default$2());
        }
        Tuple2<String, Object> tuple2 = ActorCell$.MODULE$.splitNameAndUid(name);
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        String childName = tuple2._1();
        int uid = tuple2._2$mcI$sp();
        Tuple2<String, Integer> tuple22 = new Tuple2<String, Integer>(childName, BoxesRunTime.boxToInteger(uid));
        String childName2 = tuple22._1();
        int uid2 = tuple22._2$mcI$sp();
        Option<ChildStats> option = this.getChildByName(childName2);
        if (option instanceof Some && (crs = (ChildStats)(some = (Some)option).value()) instanceof ChildRestartStats) {
            ChildRestartStats childRestartStats = (ChildRestartStats)crs;
            if (uid2 == 0 || uid2 == childRestartStats.uid()) {
                return (InternalActorRef)childRestartStats.child();
            }
        }
        return this.getFunctionRefOrNobody(childName2, uid2);
    }

    public static /* synthetic */ Option removeChildAndGetStateChange$(Children $this, ActorRef child) {
        return $this.removeChildAndGetStateChange(child);
    }

    default public Option<ChildrenContainer.SuspendReason> removeChildAndGetStateChange(ActorRef child) {
        ChildrenContainer childrenContainer = this.childrenRefs();
        if (childrenContainer instanceof ChildrenContainer.TerminatingChildrenContainer) {
            ChildrenContainer.TerminatingChildrenContainer terminatingChildrenContainer = (ChildrenContainer.TerminatingChildrenContainer)childrenContainer;
            ChildrenContainer.SuspendReason reason = terminatingChildrenContainer.reason();
            ChildrenContainer childrenContainer2 = this.removeChild$1(child);
            if (childrenContainer2 instanceof ChildrenContainer.TerminatingChildrenContainer) {
                return None$.MODULE$;
            }
            return new Some<ChildrenContainer.SuspendReason>(reason);
        }
        this.removeChild$1(child);
        return None$.MODULE$;
    }

    private String checkName(String name) {
        String string = name;
        switch (string == null ? 0 : string.hashCode()) {
            case 0: {
                if (string == null) {
                    throw new InvalidActorNameException("actor name must not be null");
                }
                if (!"".equals(string)) break;
                throw new InvalidActorNameException("actor name must not be empty");
            }
        }
        ActorPath$.MODULE$.validatePathElement(name);
        return name;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private ActorRef makeChild(ActorCell cell, Props props, String name, boolean async, boolean systemService) {
        settings = cell.system().settings();
        if (!settings.SerializeAllCreators() || systemService) ** GOTO lbl-1000
        v0 = props.deploy().scope();
        var9_7 = LocalScope$.MODULE$;
        if (v0 == null ? var9_7 != null : v0.equals(var9_7) == false) {
            oldInfo = Serialization$.MODULE$.currentTransportInformation().value();
            try {
                try {
                    ser = (Serialization)SerializationExtension$.MODULE$.apply(cell.system());
                    if (oldInfo == null) {
                        Serialization$.MODULE$.currentTransportInformation().value_$eq(((ActorCell)this).system().provider().serializationInformation());
                    }
                    v1 = props.args().forall((Function1<Object, Object> & Serializable)LambdaMetafactory.altMetafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, $anonfun$makeChild$1$adapted(akka.actor.ActorSystem$Settings akka.serialization.Serialization java.lang.Object ), (Ljava/lang/Object;)Ljava/lang/Object;)((ActorSystem.Settings)settings, (Serialization)ser));
                }
                catch (Throwable var12_10) {
                    var13_11 = var12_10;
                    if (var13_11 != null && !(var14_12 = NonFatal$.MODULE$.unapply(var13_11)).isEmpty()) {
                        e = var14_12.get();
                        throw new IllegalArgumentException(new StringBuilder(46).append("pre-creation serialization check failed at [").append(cell.self().path()).append("/").append(name).append("]").toString(), e);
                    }
                    throw var12_10;
                }
            }
            finally {
                Serialization$.MODULE$.currentTransportInformation().value_$eq(oldInfo);
            }
            v2 /* !! */  = BoxesRunTime.boxToBoolean(v1);
        } else lbl-1000:
        // 2 sources

        {
            v2 /* !! */  = BoxedUnit.UNIT;
        }
        if (cell.childrenRefs().isTerminating()) {
            throw new IllegalStateException("cannot create children while terminating or terminated");
        }
        this.reserveChild(name);
        try {
            childPath = new ChildActorPath(cell.self().path(), name, ActorCell$.MODULE$.newUid());
            v3 = cell.provider().actorOf(cell.systemImpl(), props, cell.self(), childPath, systemService, None$.MODULE$, true, async);
        }
        catch (Throwable var19_16) {
            var20_17 = var19_16;
            if (var20_17 instanceof InterruptedException) {
                var21_18 = (InterruptedException)var20_17;
                this.unreserveChild(name);
                Thread.interrupted();
                throw var21_18;
            }
            if (var20_17 != null && !(var22_19 = NonFatal$.MODULE$.unapply(var20_17)).isEmpty()) {
                e = var22_19.get();
                this.unreserveChild(name);
                throw e;
            }
            throw var19_16;
        }
        actor = v3;
        if (((Dispatch)this).mailbox() != null) {
            RichInt$.MODULE$.to$extension(Predef$.MODULE$.intWrapper(1), ((Dispatch)this).mailbox().suspendCount()).foreach$mVc$sp((JFunction1$mcVI$sp)LambdaMetafactory.altMetafactory(null, null, null, (I)V, $anonfun$makeChild$3(akka.actor.InternalActorRef int ), (I)V)((InternalActorRef)actor));
        }
        this.initChild(actor);
        actor.start();
        return actor;
    }

    private void rec$1(ChildActorPath childPath$1, FunctionRef ref$1) {
        Map added;
        Map<String, FunctionRef> old;
        while (!Unsafe.instance.compareAndSwapObject(this_, AbstractActorCell.functionRefsOffset, old = this_.functionRefs(), added = (Map)old.updated(childPath$1.name(), ref$1))) {
            Children this_ = (ActorCell)this_;
        }
    }

    private boolean rec$2(String name$1, FunctionRef ref$2) {
        while (true) {
            Map<String, FunctionRef> old;
            if (!(old = this_.functionRefs()).contains(name$1)) {
                return false;
            }
            Map removed = (Map)old.$minus(name$1);
            if (Unsafe.instance.compareAndSwapObject(this_, AbstractActorCell.functionRefsOffset, old, removed)) break;
            Children this_ = (ActorCell)this_;
        }
        ref$2.stop();
        return true;
    }

    private boolean shallDie$1(ActorRef ref) {
        ChildrenContainer c;
        while (!this_.swapChildrenRefs(c = this_.childrenRefs(), c.shallDie(ref))) {
            Children this_ = (ActorCell)this_;
        }
        return true;
    }

    public static /* synthetic */ void $anonfun$suspendChildren$1(Set exceptFor$1, ChildRestartStats x0$1) {
        ActorRef child;
        ChildRestartStats childRestartStats = x0$1;
        if (childRestartStats != null && !exceptFor$1.contains(child = childRestartStats.child())) {
            ((InternalActorRef)child).suspend();
            return;
        }
    }

    public static /* synthetic */ void $anonfun$resumeChildren$1(ActorRef perp$1, Throwable causedByFailure$1, ChildRestartStats x0$1) {
        ActorRef child;
        ChildRestartStats childRestartStats = x0$1;
        if (childRestartStats != null && (child = childRestartStats.child()) instanceof InternalActorRef) {
            InternalActorRef internalActorRef = (InternalActorRef)child;
            ActorRef actorRef2 = perp$1;
            InternalActorRef internalActorRef2 = internalActorRef;
            internalActorRef.resume(!(actorRef2 != null ? !((Object)actorRef2).equals(internalActorRef2) : internalActorRef2 != null) ? causedByFailure$1 : null);
            return;
        }
        throw new IllegalStateException(new StringBuilder(27).append("Unexpected child ActorRef: ").append(childRestartStats.child()).toString());
    }

    private ChildrenContainer removeChild$1(ActorRef ref) {
        ChildrenContainer n;
        ChildrenContainer c;
        while (!this_.swapChildrenRefs(c = this_.childrenRefs(), n = c.remove(ref))) {
            Children this_ = (ActorCell)this_;
        }
        return n;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static /* synthetic */ boolean $anonfun$makeChild$1(ActorSystem.Settings settings$1, Serialization ser$1, Object arg) {
        if (arg == null) return true;
        if (arg instanceof NoSerializationVerificationNeeded) return true;
        String string = arg.getClass().getName();
        if (settings$1.NoSerializationVerificationNeededClassPrefix().exists((Function1<String, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(string.startsWith(x$1)))) return true;
        Object o = arg;
        Serializer serializer = ser$1.findSerializerFor(o);
        byte[] bytes = serializer.toBinary(o);
        String ms = Serializers$.MODULE$.manifestFor(serializer, o);
        if (ser$1.deserialize(bytes, serializer.identifier(), ms).get() == null) return false;
        return true;
    }

    public static /* synthetic */ void $anonfun$makeChild$3(InternalActorRef actor$1, int x$3) {
        int n = x$3;
        actor$1.suspend();
    }

    public static void $init$(Children $this) {
        $this.akka$actor$dungeon$Children$$_childrenRefsDoNotCallMeDirectly_$eq(ChildrenContainer$EmptyChildrenContainer$.MODULE$);
        $this.akka$actor$dungeon$Children$$_functionRefsDoNotCallMeDirectly_$eq((Map<String, FunctionRef>)Predef$.MODULE$.Map().empty());
        $this.akka$actor$dungeon$Children$$_nextNameDoNotCallMeDirectly_$eq(0L);
    }

    public static /* synthetic */ Object $anonfun$makeChild$1$adapted(ActorSystem.Settings settings$1, Serialization ser$1, Object arg) {
        return BoxesRunTime.boxToBoolean(Children.$anonfun$makeChild$1(settings$1, ser$1, arg));
    }
}

