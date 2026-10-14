/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.BidiShape;
import akka.stream.Graph;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.Shape;
import akka.stream.TLSClosing;
import akka.stream.TLSProtocol;
import akka.stream.impl.StreamLayout;
import akka.stream.impl.TlsModuleIslandTag$;
import akka.stream.impl.TraversalBuilder;
import akka.stream.impl.TraversalBuilder$;
import akka.stream.impl.io.TlsModule$;
import akka.util.ByteString;
import java.io.Serializable;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.Product;
import scala.Tuple9;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\tMg!\u0002\u00192\u0005VJ\u0004\u0002C;\u0001\u0005+\u0007I\u0011\u0001<\t\u0011i\u0004!\u0011#Q\u0001\n]D\u0001b\u001f\u0001\u0003\u0016\u0004%\t\u0001 \u0005\n\u0003\u0003\u0001!\u0011#Q\u0001\nuD!\"a\u0001\u0001\u0005+\u0007I\u0011AA\u0003\u0011)\tI\u0001\u0001B\tB\u0003%\u0011q\u0001\u0005\u000b\u0003\u0017\u0001!Q3A\u0005\u0002\u00055\u0001BCA\t\u0001\tE\t\u0015!\u0003\u0002\u0010!Q\u00111\u0003\u0001\u0003\u0016\u0004%\t!!\u0006\t\u0013\u0005]\u0001A!E!\u0002\u0013\t\u0006BCA\r\u0001\tU\r\u0011\"\u0001\u0002\u001c!Q\u00111\u0005\u0001\u0003\u0012\u0003\u0006I!!\b\t\u0015\u0005\u0015\u0002A!f\u0001\n\u0003\t9\u0003\u0003\u0006\u0002P\u0001\u0011\t\u0012)A\u0005\u0003SA!\"!\u0015\u0001\u0005+\u0007I\u0011AA*\u0011)\t\t\b\u0001B\tB\u0003%\u0011Q\u000b\u0005\u000b\u0003g\u0002!Q3A\u0005\u0002\u0005U\u0004BCA?\u0001\tE\t\u0015!\u0003\u0002x!9\u0011q\u0010\u0001\u0005\u0002\u0005\u0005\u0005bBAM\u0001\u0011\u0005\u00131\u0014\u0005\b\u0003C\u0003A\u0011IAR\u0011!\t)\f\u0001C!k\u0005]\u0006\"CAa\u0001\u0005\u0005I\u0011AAb\u0011%\t9\u000eAI\u0001\n\u0003\tI\u000eC\u0005\u0002p\u0002\t\n\u0011\"\u0001\u0002r\"I\u0011Q\u001f\u0001\u0012\u0002\u0013\u0005\u0011q\u001f\u0005\n\u0003w\u0004\u0011\u0013!C\u0001\u0003{D\u0011B!\u0001\u0001#\u0003%\tAa\u0001\t\u0013\t\u001d\u0001!%A\u0005\u0002\t%\u0001\"\u0003B\u0007\u0001E\u0005I\u0011\u0001B\b\u0011%\u0011\u0019\u0002AI\u0001\n\u0003\u0011)\u0002C\u0005\u0003\u001a\u0001\t\n\u0011\"\u0001\u0003\u001c!I!q\u0004\u0001\u0002\u0002\u0013\u0005#\u0011\u0005\u0005\n\u0005c\u0001\u0011\u0011!C\u0001\u0005gA\u0011Ba\u000f\u0001\u0003\u0003%\tA!\u0010\t\u0013\t%\u0003!!A\u0005B\t-\u0003\"\u0003B-\u0001\u0005\u0005I\u0011\u0001B.\u0011%\u0011)\u0007AA\u0001\n\u0003\u00129\u0007C\u0005\u0003l\u0001\t\t\u0011\"\u0011\u0003n!I!q\u000e\u0001\u0002\u0002\u0013\u0005#\u0011O\u0004\t\u0005\u0003\u000b\u0004\u0012A\u001b\u0003\u0004\u001a9\u0001'\rE\u0001k\t\u0015\u0005bBA@U\u0011\u0005!q\u0012\u0005\b\u0005#SC\u0011\u0001BJ\u0011%\u0011\tJKA\u0001\n\u0003\u0013i\nC\u0005\u00032*\n\t\u0011\"!\u00034\"I!Q\u0019\u0016\u0002\u0002\u0013%!q\u0019\u0002\n)2\u001cXj\u001c3vY\u0016T!AM\u001a\u0002\u0005%|'B\u0001\u001b6\u0003\u0011IW\u000e\u001d7\u000b\u0005Y:\u0014AB:ue\u0016\fWNC\u00019\u0003\u0011\t7n[1\u0014\u000b\u0001Q\u0004)\u001b7\u0011\u0005mrT\"\u0001\u001f\u000b\u0003u\nQa]2bY\u0006L!a\u0010\u001f\u0003\r\u0005s\u0017PU3g!\u0011\te*U3\u000f\u0005\tceBA\"L\u001d\t!%J\u0004\u0002F\u00136\taI\u0003\u0002H\u0011\u00061AH]8piz\u001a\u0001!C\u00019\u0013\t1t'\u0003\u00025k%\u0011QjM\u0001\r'R\u0014X-Y7MCf|W\u000f^\u0005\u0003\u001fB\u0013A\"\u0011;p[&\u001cWj\u001c3vY\u0016T!!T\u001a\u0011\rI\u001bV\u000b\u0018/c\u001b\u0005)\u0014B\u0001+6\u0005%\u0011\u0015\u000eZ5TQ\u0006\u0004X\r\u0005\u0002W3:\u00111iV\u0005\u00031V\n1\u0002\u0016'T!J|Go\\2pY&\u0011!l\u0017\u0002\u000f'NdG\u000b\\:PkR\u0014w.\u001e8e\u0015\tAV\u0007\u0005\u0002^A6\taL\u0003\u0002`o\u0005!Q\u000f^5m\u0013\t\tgL\u0001\u0006CsR,7\u000b\u001e:j]\u001e\u0004\"AV2\n\u0005\u0011\\&!D*tYRc7/\u00138c_VtG\r\u0005\u0002gO6\tq'\u0003\u0002io\t9aj\u001c;Vg\u0016$\u0007CA\u001ek\u0013\tYGHA\u0004Qe>$Wo\u0019;\u0011\u00055\u0014hB\u00018q\u001d\t)u.C\u0001>\u0013\t\tH(A\u0004qC\u000e\\\u0017mZ3\n\u0005M$(\u0001D*fe&\fG.\u001b>bE2,'BA9=\u0003\u001d\u0001H.Y5o\u0013:,\u0012a\u001e\t\u0004%b,\u0016BA=6\u0005\u0015Ie\u000e\\3u\u0003!\u0001H.Y5o\u0013:\u0004\u0013\u0001\u00039mC&tw*\u001e;\u0016\u0003u\u00042A\u0015@c\u0013\tyXG\u0001\u0004PkRdW\r^\u0001\na2\f\u0017N\\(vi\u0002\n\u0001bY5qQ\u0016\u0014\u0018J\\\u000b\u0003\u0003\u000f\u00012A\u0015=]\u0003%\u0019\u0017\u000e\u001d5fe&s\u0007%A\u0005dSBDWM](viV\u0011\u0011q\u0002\t\u0004%zd\u0016AC2ja\",'oT;uA\u0005)1\u000f[1qKV\t\u0011+\u0001\u0004tQ\u0006\u0004X\rI\u0001\u000bCR$(/\u001b2vi\u0016\u001cXCAA\u000f!\r\u0011\u0016qD\u0005\u0004\u0003C)$AC!uiJL'-\u001e;fg\u0006Y\u0011\r\u001e;sS\n,H/Z:!\u0003=\u0019'/Z1uKN\u001bF*\u00128hS:,WCAA\u0015!\u001dY\u00141FA\u0018\u0003wI1!!\f=\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0003\u00022\u0005]RBAA\u001a\u0015\r\t)dN\u0001\u0006C\u000e$xN]\u0005\u0005\u0003s\t\u0019DA\u0006BGR|'oU=ti\u0016l\u0007\u0003BA\u001f\u0003\u0017j!!a\u0010\u000b\t\u0005\u0005\u00131I\u0001\u0004gNd'\u0002BA#\u0003\u000f\n1A\\3u\u0015\t\tI%A\u0003kCZ\f\u00070\u0003\u0003\u0002N\u0005}\"!C*T\u0019\u0016sw-\u001b8f\u0003A\u0019'/Z1uKN\u001bF*\u00128hS:,\u0007%A\u0007wKJLg-_*fgNLwN\\\u000b\u0003\u0003+\u0002\u0012bOA,\u0003_\tY&!\u0019\n\u0007\u0005eCHA\u0005Gk:\u001cG/[8oeA!\u0011QHA/\u0013\u0011\ty&a\u0010\u0003\u0015M\u001bFjU3tg&|g\u000e\u0005\u0004\u0002d\u0005\u001d\u00141N\u0007\u0003\u0003KR!a\u0018\u001f\n\t\u0005%\u0014Q\r\u0002\u0004)JL\bcA\u001e\u0002n%\u0019\u0011q\u000e\u001f\u0003\tUs\u0017\u000e^\u0001\u000fm\u0016\u0014\u0018NZ=TKN\u001c\u0018n\u001c8!\u0003\u001d\u0019Gn\\:j]\u001e,\"!a\u001e\u0011\u0007I\u000bI(C\u0002\u0002|U\u0012!\u0002\u0016'T\u00072|7/\u001b8h\u0003!\u0019Gn\\:j]\u001e\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u000b\u0002\u0004\u0006\u001d\u0015\u0011RAF\u0003\u001b\u000by)!%\u0002\u0014\u0006U\u0015q\u0013\t\u0004\u0003\u000b\u0003Q\"A\u0019\t\u000bU\u001c\u0002\u0019A<\t\u000bm\u001c\u0002\u0019A?\t\u000f\u0005\r1\u00031\u0001\u0002\b!9\u00111B\nA\u0002\u0005=\u0001BBA\n'\u0001\u0007\u0011\u000bC\u0004\u0002\u001aM\u0001\r!!\b\t\u000f\u0005\u00152\u00031\u0001\u0002*!9\u0011\u0011K\nA\u0002\u0005U\u0003bBA:'\u0001\u0007\u0011qO\u0001\u000fo&$\b.\u0011;ue&\u0014W\u000f^3t)\u0011\t\u0019)!(\t\u000f\u0005}E\u00031\u0001\u0002\u001e\u0005\u0019\u0011\r\u001e;\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!!*\u0011\t\u0005\u001d\u0016q\u0016\b\u0005\u0003S\u000bY\u000b\u0005\u0002Fy%\u0019\u0011Q\u0016\u001f\u0002\rA\u0013X\rZ3g\u0013\u0011\t\t,a-\u0003\rM#(/\u001b8h\u0015\r\ti\u000bP\u0001\u0011iJ\fg/\u001a:tC2\u0014U/\u001b7eKJ,\"!!/\u0011\t\u0005m\u0016QX\u0007\u0002g%\u0019\u0011qX\u001a\u0003!Q\u0013\u0018M^3sg\u0006d')^5mI\u0016\u0014\u0018\u0001B2paf$B#a!\u0002F\u0006\u001d\u0017\u0011ZAf\u0003\u001b\fy-!5\u0002T\u0006U\u0007bB;\u0018!\u0003\u0005\ra\u001e\u0005\bw^\u0001\n\u00111\u0001~\u0011%\t\u0019a\u0006I\u0001\u0002\u0004\t9\u0001C\u0005\u0002\f]\u0001\n\u00111\u0001\u0002\u0010!A\u00111C\f\u0011\u0002\u0003\u0007\u0011\u000bC\u0005\u0002\u001a]\u0001\n\u00111\u0001\u0002\u001e!I\u0011QE\f\u0011\u0002\u0003\u0007\u0011\u0011\u0006\u0005\n\u0003#:\u0002\u0013!a\u0001\u0003+B\u0011\"a\u001d\u0018!\u0003\u0005\r!a\u001e\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011\u00111\u001c\u0016\u0004o\u0006u7FAAp!\u0011\t\t/a;\u000e\u0005\u0005\r(\u0002BAs\u0003O\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005%H(\u0001\u0006b]:|G/\u0019;j_:LA!!<\u0002d\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\u0011\u00111\u001f\u0016\u0004{\u0006u\u0017AD2paf$C-\u001a4bk2$HeM\u000b\u0003\u0003sTC!a\u0002\u0002^\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\"TCAA\u0000U\u0011\ty!!8\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%kU\u0011!Q\u0001\u0016\u0004#\u0006u\u0017AD2paf$C-\u001a4bk2$HEN\u000b\u0003\u0005\u0017QC!!\b\u0002^\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012:TC\u0001B\tU\u0011\tI#!8\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%qU\u0011!q\u0003\u0016\u0005\u0003+\ni.\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001d\u0016\u0005\tu!\u0006BA<\u0003;\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXC\u0001B\u0012!\u0011\u0011)Ca\f\u000e\u0005\t\u001d\"\u0002\u0002B\u0015\u0005W\tA\u0001\\1oO*\u0011!QF\u0001\u0005U\u00064\u0018-\u0003\u0003\u00022\n\u001d\u0012\u0001\u00049s_\u0012,8\r^!sSRLXC\u0001B\u001b!\rY$qG\u0005\u0004\u0005sa$aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$H\u0003\u0002B \u0005\u000b\u00022a\u000fB!\u0013\r\u0011\u0019\u0005\u0010\u0002\u0004\u0003:L\b\"\u0003B$G\u0005\u0005\t\u0019\u0001B\u001b\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011!Q\n\t\u0007\u0005\u001f\u0012)Fa\u0010\u000e\u0005\tE#b\u0001B*y\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\t]#\u0011\u000b\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0003^\t\r\u0004cA\u001e\u0003`%\u0019!\u0011\r\u001f\u0003\u000f\t{w\u000e\\3b]\"I!qI\u0013\u0002\u0002\u0003\u0007!qH\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0003$\t%\u0004\"\u0003B$M\u0005\u0005\t\u0019\u0001B\u001b\u0003!A\u0017m\u001d5D_\u0012,GC\u0001B\u001b\u0003\u0019)\u0017/^1mgR!!Q\fB:\u0011%\u00119\u0005KA\u0001\u0002\u0004\u0011y\u0004K\u0002\u0001\u0005o\u0002BA!\u001f\u0003~5\u0011!1\u0010\u0006\u0004\u0003S<\u0014\u0002\u0002B@\u0005w\u00121\"\u00138uKJt\u0017\r\\!qS\u0006IA\u000b\\:N_\u0012,H.\u001a\t\u0004\u0003\u000bS3\u0003\u0002\u0016;\u0005\u000f\u0003BA!#\u0003\u000e6\u0011!1\u0012\u0006\u0004e\t-\u0012bA:\u0003\fR\u0011!1Q\u0001\u0006CB\u0004H.\u001f\u000b\u000b\u0003\u0007\u0013)Ja&\u0003\u001a\nm\u0005bBA\rY\u0001\u0007\u0011Q\u0004\u0005\b\u0003Ka\u0003\u0019AA\u0015\u0011\u001d\t\t\u0006\fa\u0001\u0003+Bq!a\u001d-\u0001\u0004\t9\b\u0006\u000b\u0002\u0004\n}%\u0011\u0015BR\u0005K\u00139K!+\u0003,\n5&q\u0016\u0005\u0006k6\u0002\ra\u001e\u0005\u0006w6\u0002\r! \u0005\b\u0003\u0007i\u0003\u0019AA\u0004\u0011\u001d\tY!\fa\u0001\u0003\u001fAa!a\u0005.\u0001\u0004\t\u0006bBA\r[\u0001\u0007\u0011Q\u0004\u0005\b\u0003Ki\u0003\u0019AA\u0015\u0011\u001d\t\t&\fa\u0001\u0003+Bq!a\u001d.\u0001\u0004\t9(A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\tU&\u0011\u0019\t\u0006w\t]&1X\u0005\u0004\u0005sc$AB(qi&|g\u000e\u0005\n<\u0005{;X0a\u0002\u0002\u0010E\u000bi\"!\u000b\u0002V\u0005]\u0014b\u0001B`y\t1A+\u001e9mKfB\u0011Ba1/\u0003\u0003\u0005\r!a!\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0003JB!!Q\u0005Bf\u0013\u0011\u0011iMa\n\u0003\r=\u0013'.Z2uQ\rQ#q\u000f\u0015\u0004S\t]\u0004")
@InternalApi
public final class TlsModule
implements StreamLayout.AtomicModule<BidiShape<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound>, NotUsed>,
Product,
Serializable {
    private final Inlet<TLSProtocol.SslTlsOutbound> plainIn;
    private final Outlet<TLSProtocol.SslTlsInbound> plainOut;
    private final Inlet<ByteString> cipherIn;
    private final Outlet<ByteString> cipherOut;
    private final BidiShape<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound> shape;
    private final Attributes attributes;
    private final Function1<ActorSystem, SSLEngine> createSSLEngine;
    private final Function2<ActorSystem, SSLSession, Try<BoxedUnit>> verifySession;
    private final TLSClosing closing;

    public static Option<Tuple9<Inlet<TLSProtocol.SslTlsOutbound>, Outlet<TLSProtocol.SslTlsInbound>, Inlet<ByteString>, Outlet<ByteString>, BidiShape<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound>, Attributes, Function1<ActorSystem, SSLEngine>, Function2<ActorSystem, SSLSession, Try<BoxedUnit>>, TLSClosing>> unapply(TlsModule x$0) {
        return TlsModule$.MODULE$.unapply(x$0);
    }

    public static TlsModule apply(Inlet<TLSProtocol.SslTlsOutbound> plainIn, Outlet<TLSProtocol.SslTlsInbound> plainOut, Inlet<ByteString> cipherIn, Outlet<ByteString> cipherOut, BidiShape<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound> shape, Attributes attributes, Function1<ActorSystem, SSLEngine> createSSLEngine, Function2<ActorSystem, SSLSession, Try<BoxedUnit>> verifySession, TLSClosing closing2) {
        return TlsModule$.MODULE$.apply(plainIn, plainOut, cipherIn, cipherOut, shape, attributes, createSSLEngine, verifySession, closing2);
    }

    public static TlsModule apply(Attributes attributes, Function1<ActorSystem, SSLEngine> createSSLEngine, Function2<ActorSystem, SSLSession, Try<BoxedUnit>> verifySession, TLSClosing closing2) {
        return TlsModule$.MODULE$.apply(attributes, createSSLEngine, verifySession, closing2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public Graph<BidiShape<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound>, NotUsed> named(String name) {
        return Graph.named$(this, name);
    }

    @Override
    public Graph<BidiShape<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound>, NotUsed> async() {
        return Graph.async$(this);
    }

    @Override
    public Graph<BidiShape<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound>, NotUsed> async(String dispatcher) {
        return Graph.async$(this, dispatcher);
    }

    @Override
    public Graph<BidiShape<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound>, NotUsed> async(String dispatcher, int inputBufferSize) {
        return Graph.async$(this, dispatcher, inputBufferSize);
    }

    @Override
    public Graph<BidiShape<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound>, NotUsed> addAttributes(Attributes attr) {
        return Graph.addAttributes$(this, attr);
    }

    @Override
    public Attributes getAttributes() {
        return Graph.getAttributes$(this);
    }

    public Inlet<TLSProtocol.SslTlsOutbound> plainIn() {
        return this.plainIn;
    }

    public Outlet<TLSProtocol.SslTlsInbound> plainOut() {
        return this.plainOut;
    }

    public Inlet<ByteString> cipherIn() {
        return this.cipherIn;
    }

    public Outlet<ByteString> cipherOut() {
        return this.cipherOut;
    }

    @Override
    public BidiShape<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound> shape() {
        return this.shape;
    }

    public Attributes attributes() {
        return this.attributes;
    }

    public Function1<ActorSystem, SSLEngine> createSSLEngine() {
        return this.createSSLEngine;
    }

    public Function2<ActorSystem, SSLSession, Try<BoxedUnit>> verifySession() {
        return this.verifySession;
    }

    public TLSClosing closing() {
        return this.closing;
    }

    public TlsModule withAttributes(Attributes att) {
        Attributes x$1 = att;
        Inlet<TLSProtocol.SslTlsOutbound> x$2 = this.copy$default$1();
        Outlet<TLSProtocol.SslTlsInbound> x$3 = this.copy$default$2();
        Inlet<ByteString> x$4 = this.copy$default$3();
        Outlet<ByteString> x$5 = this.copy$default$4();
        BidiShape<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound> x$6 = this.copy$default$5();
        Function1<ActorSystem, SSLEngine> x$7 = this.copy$default$7();
        Function2<ActorSystem, SSLSession, Try<BoxedUnit>> x$8 = this.copy$default$8();
        TLSClosing x$9 = this.copy$default$9();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$1, x$7, x$8, x$9);
    }

    public String toString() {
        return StringOps$.MODULE$.format$extension("TlsModule(%s) [%08x]", ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.closing(), BoxesRunTime.boxToInteger(System.identityHashCode(this))}));
    }

    @Override
    public TraversalBuilder traversalBuilder() {
        return TraversalBuilder$.MODULE$.atomic(this, this.attributes()).makeIsland(TlsModuleIslandTag$.MODULE$);
    }

    public TlsModule copy(Inlet<TLSProtocol.SslTlsOutbound> plainIn, Outlet<TLSProtocol.SslTlsInbound> plainOut, Inlet<ByteString> cipherIn, Outlet<ByteString> cipherOut, BidiShape<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound> shape, Attributes attributes, Function1<ActorSystem, SSLEngine> createSSLEngine, Function2<ActorSystem, SSLSession, Try<BoxedUnit>> verifySession, TLSClosing closing2) {
        return new TlsModule(plainIn, plainOut, cipherIn, cipherOut, shape, attributes, createSSLEngine, verifySession, closing2);
    }

    public Inlet<TLSProtocol.SslTlsOutbound> copy$default$1() {
        return this.plainIn();
    }

    public Outlet<TLSProtocol.SslTlsInbound> copy$default$2() {
        return this.plainOut();
    }

    public Inlet<ByteString> copy$default$3() {
        return this.cipherIn();
    }

    public Outlet<ByteString> copy$default$4() {
        return this.cipherOut();
    }

    public BidiShape<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound> copy$default$5() {
        return this.shape();
    }

    public Attributes copy$default$6() {
        return this.attributes();
    }

    public Function1<ActorSystem, SSLEngine> copy$default$7() {
        return this.createSSLEngine();
    }

    public Function2<ActorSystem, SSLSession, Try<BoxedUnit>> copy$default$8() {
        return this.verifySession();
    }

    public TLSClosing copy$default$9() {
        return this.closing();
    }

    @Override
    public String productPrefix() {
        return "TlsModule";
    }

    @Override
    public int productArity() {
        return 9;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.plainIn();
            }
            case 1: {
                return this.plainOut();
            }
            case 2: {
                return this.cipherIn();
            }
            case 3: {
                return this.cipherOut();
            }
            case 4: {
                return this.shape();
            }
            case 5: {
                return this.attributes();
            }
            case 6: {
                return this.createSSLEngine();
            }
            case 7: {
                return this.verifySession();
            }
            case 8: {
                return this.closing();
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
        return x$1 instanceof TlsModule;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "plainIn";
            }
            case 1: {
                return "plainOut";
            }
            case 2: {
                return "cipherIn";
            }
            case 3: {
                return "cipherOut";
            }
            case 4: {
                return "shape";
            }
            case 5: {
                return "attributes";
            }
            case 6: {
                return "createSSLEngine";
            }
            case 7: {
                return "verifySession";
            }
            case 8: {
                return "closing";
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
        if (!(object instanceof TlsModule)) return false;
        boolean bl = true;
        if (!bl) return false;
        TlsModule tlsModule = (TlsModule)x$1;
        Inlet<TLSProtocol.SslTlsOutbound> inlet = this.plainIn();
        Inlet<TLSProtocol.SslTlsOutbound> inlet2 = tlsModule.plainIn();
        if (inlet == null) {
            if (inlet2 != null) {
                return false;
            }
        } else if (!((Object)inlet).equals(inlet2)) return false;
        Outlet<TLSProtocol.SslTlsInbound> outlet = this.plainOut();
        Outlet<TLSProtocol.SslTlsInbound> outlet2 = tlsModule.plainOut();
        if (outlet == null) {
            if (outlet2 != null) {
                return false;
            }
        } else if (!((Object)outlet).equals(outlet2)) return false;
        Inlet<ByteString> inlet3 = this.cipherIn();
        Inlet<ByteString> inlet4 = tlsModule.cipherIn();
        if (inlet3 == null) {
            if (inlet4 != null) {
                return false;
            }
        } else if (!((Object)inlet3).equals(inlet4)) return false;
        Outlet<ByteString> outlet3 = this.cipherOut();
        Outlet<ByteString> outlet4 = tlsModule.cipherOut();
        if (outlet3 == null) {
            if (outlet4 != null) {
                return false;
            }
        } else if (!((Object)outlet3).equals(outlet4)) return false;
        Shape shape = this.shape();
        Shape shape2 = tlsModule.shape();
        if (shape == null) {
            if (shape2 != null) {
                return false;
            }
        } else if (!shape.equals(shape2)) return false;
        Attributes attributes = this.attributes();
        Attributes attributes2 = tlsModule.attributes();
        if (attributes == null) {
            if (attributes2 != null) {
                return false;
            }
        } else if (!((Object)attributes).equals(attributes2)) return false;
        Function1<ActorSystem, SSLEngine> function1 = this.createSSLEngine();
        Function1<ActorSystem, SSLEngine> function12 = tlsModule.createSSLEngine();
        if (function1 == null) {
            if (function12 != null) {
                return false;
            }
        } else if (!function1.equals(function12)) return false;
        Function2<ActorSystem, SSLSession, Try<BoxedUnit>> function2 = this.verifySession();
        Function2<ActorSystem, SSLSession, Try<BoxedUnit>> function22 = tlsModule.verifySession();
        if (function2 == null) {
            if (function22 != null) {
                return false;
            }
        } else if (!function2.equals(function22)) return false;
        TLSClosing tLSClosing = this.closing();
        TLSClosing tLSClosing2 = tlsModule.closing();
        if (tLSClosing == null) {
            if (tLSClosing2 == null) return true;
            return false;
        } else {
            if (!tLSClosing.equals(tLSClosing2)) return false;
            return true;
        }
    }

    public TlsModule(Inlet<TLSProtocol.SslTlsOutbound> plainIn, Outlet<TLSProtocol.SslTlsInbound> plainOut, Inlet<ByteString> cipherIn, Outlet<ByteString> cipherOut, BidiShape<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound> shape, Attributes attributes, Function1<ActorSystem, SSLEngine> createSSLEngine, Function2<ActorSystem, SSLSession, Try<BoxedUnit>> verifySession, TLSClosing closing2) {
        this.plainIn = plainIn;
        this.plainOut = plainOut;
        this.cipherIn = cipherIn;
        this.cipherOut = cipherOut;
        this.shape = shape;
        this.attributes = attributes;
        this.createSSLEngine = createSSLEngine;
        this.verifySession = verifySession;
        this.closing = closing2;
        Graph.$init$(this);
        Product.$init$(this);
    }
}

