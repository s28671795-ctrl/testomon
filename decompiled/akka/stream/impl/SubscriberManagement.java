/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.impl.ReactiveStreamsCompliance;
import akka.stream.impl.ReactiveStreamsCompliance$;
import akka.stream.impl.ResizableMultiReaderRingBuffer;
import akka.stream.impl.SubscriberManagement$;
import akka.stream.impl.SubscriberManagement$Completed$;
import akka.stream.impl.SubscriberManagement$NotReached$;
import akka.stream.impl.SubscriptionWithCursor;
import java.io.Serializable;
import org.reactivestreams.Subscriber;
import scala.Function1;
import scala.MatchError;
import scala.Product;
import scala.collection.Iterator;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.math.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\tUxA\u0002\"D\u0011\u00039\u0015J\u0002\u0004L\u0007\"\u0005q\t\u0014\u0005\u0006'\u0006!\t!\u0016\u0004\b-\u0006\u0001\n1%\tX\u0011\u0015A6A\"\u0001Z\u000f\u001d\tI-\u0001E\u0001\u0003w3q!!.\u0002\u0011\u0003\t9\f\u0003\u0004T\r\u0011\u0005\u0011\u0011\u0018\u0005\u00071\u001a!\t!!0\b\r\u0005-\u0017\u0001#\u0001z\r\u0015!\u0018\u0001#\u0001v\u0011\u0015\u0019&\u0002\"\u0001y\u0011\u0015A&\u0002\"\u0001{\r\u0019\t\t!\u0001\"\u0002\u0004!Q\u00111E\u0007\u0003\u0016\u0004%\t!!\n\t\u0015\u00055RB!E!\u0002\u0013\t9\u0003\u0003\u0004T\u001b\u0011\u0005\u0011q\u0006\u0005\u000716!\t!!\u000e\t\u0013\u0005\u0005S\"!A\u0005\u0002\u0005\r\u0003\"CA$\u001bE\u0005I\u0011AA%\u0011%\ty&DA\u0001\n\u0003\n\t\u0007C\u0005\u0002t5\t\t\u0011\"\u0001\u0002v!I\u0011QP\u0007\u0002\u0002\u0013\u0005\u0011q\u0010\u0005\n\u0003\u000bk\u0011\u0011!C!\u0003\u000fC\u0011\"!&\u000e\u0003\u0003%\t!a&\t\u0013\u0005\u0005V\"!A\u0005B\u0005\r\u0006\"CAT\u001b\u0005\u0005I\u0011IAU\u0011%\tY+DA\u0001\n\u0003\ni\u000bC\u0005\u000206\t\t\u0011\"\u0011\u00022\u001eI\u0011QZ\u0001\u0002\u0002#\u0005\u0011q\u001a\u0004\n\u0003\u0003\t\u0011\u0011!E\u0001\u0003#Daa\u0015\u0010\u0005\u0002\u0005%\b\"CAV=\u0005\u0005IQIAW\u0011!Af$!A\u0005\u0002\u0006-\b\"CAx=\u0005\u0005I\u0011QAy\u0011%\tiPHA\u0001\n\u0013\ty\u0010C\u0005\u0003\b\u0005\u0011\r\u0011\"\u0001\u0003\n!A!1B\u0001!\u0002\u0013\t\tDB\u0005L\u0007B\u0005\u0019\u0011A$\u0003\u000e!9!q\u0004\u0014\u0005\u0002\t\u0005Ba\u0002B\u0012M\t\u0005!QE\u0003\u0007\u0005c1\u0003Aa\r\t\u000f\tubE\"\u0001\u0002v!9!q\b\u0014\u0007\u0002\u0005U\u0004b\u0002B!M\u0019E!1\t\u0005\b\u0005\u001f2c\u0011\u0003B\u0011\u0011\u001d\u0011\tF\nD\t\u0005'BqA!\u0017'\r#\u0011Y\u0006C\u0005\u0003l\u0019\u0012\r\u0015\"\u0003\u0003n!9!Q\u000f\u0014\u0005\u0012\t]\u0004\"\u0003BDM\u0001\u0007K\u0011\u0002BE\u0011%\u0011iI\na!\n\u0013\u0011y\tC\u0005\u0003\u0014\u001a\u0002\r\u0015\"\u0003\u0003\u0016\"I!q\u0013\u0014AB\u0013%!\u0011\u0014\u0005\n\u0005;3\u0003\u0019)C\u0005\u0005?C\u0011B!*'\u0001\u0004&IAa*\t\u000f\t-f\u0005\"\u0001\u0003\n\"9!Q\u0016\u0014\u0005\u0012\t=\u0006\u0002\u0003B\\M\u0001&iA!\t\t\u000f\tef\u0005\"\u0005\u0003<\"9!\u0011\u0019\u0014\u0005\u0012\t\u0005\u0002b\u0002BbM\u0011E!Q\u0019\u0005\b\u0005\u00134C\u0011\u0003Bf\u0011\u001d\u0011IN\nC\u0005\u00057DqA!;'\t#\u0011Y\u000fC\u0004\u0003p\u001a\"IA!=\u0002)M+(m]2sS\n,'/T1oC\u001e,W.\u001a8u\u0015\t!U)\u0001\u0003j[Bd'B\u0001$H\u0003\u0019\u0019HO]3b[*\t\u0001*\u0001\u0003bW.\f\u0007C\u0001&\u0002\u001b\u0005\u0019%\u0001F*vEN\u001c'/\u001b2fe6\u000bg.Y4f[\u0016tGo\u0005\u0002\u0002\u001bB\u0011a*U\u0007\u0002\u001f*\t\u0001+A\u0003tG\u0006d\u0017-\u0003\u0002S\u001f\n1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u0002\u0013\nYQI\u001c3PMN#(/Z1n'\t\u0019Q*A\u0003baBd\u00170\u0006\u0002[UR\u00111L\u0018\t\u0003\u001drK!!X(\u0003\tUs\u0017\u000e\u001e\u0005\u0006?\u0012\u0001\r\u0001Y\u0001\u000bgV\u00147o\u0019:jE\u0016\u0014\bcA1gQ6\t!M\u0003\u0002dI\u0006y!/Z1di&4Xm\u001d;sK\u0006l7OC\u0001f\u0003\ry'oZ\u0005\u0003O\n\u0014!bU;cg\u000e\u0014\u0018NY3s!\tI'\u000e\u0004\u0001\u0005\u000b-$!\u0019\u00017\u0003\u0003Q\u000b\"!\u001c9\u0011\u00059s\u0017BA8P\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"AT9\n\u0005I|%aA!os&\"1AC\u0007\u0007\u0005%\u0019u.\u001c9mKR,GmE\u0002\u000b\u001bZ\u0004\"a^\u0002\u000e\u0003\u0005!\u0012!\u001f\t\u0003o*)\"a_@\u0015\u0005mc\b\"B0\r\u0001\u0004i\bcA1g}B\u0011\u0011n \u0003\u0006W2\u0011\r\u0001\u001c\u0002\u000f\u000bJ\u0014xN]\"p[BdW\r^3e'\u001diQJ^A\u0003\u0003\u0017\u00012ATA\u0004\u0013\r\tIa\u0014\u0002\b!J|G-^2u!\u0011\ti!!\b\u000f\t\u0005=\u0011\u0011\u0004\b\u0005\u0003#\t9\"\u0004\u0002\u0002\u0014)\u0019\u0011Q\u0003+\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0001\u0016bAA\u000e\u001f\u00069\u0001/Y2lC\u001e,\u0017\u0002BA\u0010\u0003C\u0011AbU3sS\u0006d\u0017N_1cY\u0016T1!a\u0007P\u0003\u0015\u0019\u0017-^:f+\t\t9\u0003\u0005\u0003\u0002\u000e\u0005%\u0012\u0002BA\u0016\u0003C\u0011\u0011\u0002\u00165s_^\f'\r\\3\u0002\r\r\fWo]3!)\u0011\t\t$a\r\u0011\u0005]l\u0001bBA\u0012!\u0001\u0007\u0011qE\u000b\u0005\u0003o\ty\u0004F\u0002\\\u0003sAaaX\tA\u0002\u0005m\u0002\u0003B1g\u0003{\u00012![A \t\u0015Y\u0017C1\u0001m\u0003\u0011\u0019w\u000e]=\u0015\t\u0005E\u0012Q\t\u0005\n\u0003G\u0011\u0002\u0013!a\u0001\u0003O\tabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0002\u0002L)\"\u0011qEA'W\t\ty\u0005\u0005\u0003\u0002R\u0005mSBAA*\u0015\u0011\t)&a\u0016\u0002\u0013Ut7\r[3dW\u0016$'bAA-\u001f\u0006Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005u\u00131\u000b\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0002dA!\u0011QMA8\u001b\t\t9G\u0003\u0003\u0002j\u0005-\u0014\u0001\u00027b]\u001eT!!!\u001c\u0002\t)\fg/Y\u0005\u0005\u0003c\n9G\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003o\u00022ATA=\u0013\r\tYh\u0014\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004a\u0006\u0005\u0005\"CAB-\u0005\u0005\t\u0019AA<\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011\u0011\u0012\t\u0006\u0003\u0017\u000b\t\n]\u0007\u0003\u0003\u001bS1!a$P\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003'\u000biI\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BAM\u0003?\u00032ATAN\u0013\r\tij\u0014\u0002\b\u0005>|G.Z1o\u0011!\t\u0019\tGA\u0001\u0002\u0004\u0001\u0018A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!a\u0019\u0002&\"I\u00111Q\r\u0002\u0002\u0003\u0007\u0011qO\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u0011qO\u0001\ti>\u001cFO]5oOR\u0011\u00111M\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005e\u00151\u0017\u0005\t\u0003\u0007c\u0012\u0011!a\u0001a\nQaj\u001c;SK\u0006\u001c\u0007.\u001a3\u0014\u0007\u0019ie\u000f\u0006\u0002\u0002<B\u0011qOB\u000b\u0005\u0003\u007f\u000b9\rF\u0002\\\u0003\u0003Daa\u0018\u0005A\u0002\u0005\r\u0007\u0003B1g\u0003\u000b\u00042![Ad\t\u0015Y\u0007B1\u0001m\u0003)qu\u000e\u001e*fC\u000eDW\rZ\u0001\n\u0007>l\u0007\u000f\\3uK\u0012\fa\"\u0012:s_J\u001cu.\u001c9mKR,G\r\u0005\u0002x=M)a$a5\u0002`BA\u0011Q[An\u0003O\t\t$\u0004\u0002\u0002X*\u0019\u0011\u0011\\(\u0002\u000fI,h\u000e^5nK&!\u0011Q\\Al\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\t\u0005\u0003C\f9/\u0004\u0002\u0002d*!\u0011Q]A6\u0003\tIw.\u0003\u0003\u0002 \u0005\rHCAAh)\u0011\t\t$!<\t\u000f\u0005\r\u0012\u00051\u0001\u0002(\u00059QO\\1qa2LH\u0003BAz\u0003s\u0004RATA{\u0003OI1!a>P\u0005\u0019y\u0005\u000f^5p]\"I\u00111 \u0012\u0002\u0002\u0003\u0007\u0011\u0011G\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001B\u0001!\u0011\t)Ga\u0001\n\t\t\u0015\u0011q\r\u0002\u0007\u001f\nTWm\u0019;\u0002\u0011MCW\u000f\u001e#po:,\"!!\r\u0002\u0013MCW\u000f\u001e#po:\u0004S\u0003\u0002B\b\u0005_\u0019BAJ'\u0003\u0012A!!1\u0003B\r\u001d\rQ%QC\u0005\u0004\u0005/\u0019\u0015A\b*fg&T\u0018M\u00197f\u001bVdG/\u001b*fC\u0012,'OU5oO\n+hMZ3s\u0013\u0011\u0011YB!\b\u0003\u000f\r+(o]8sg*\u0019!qC\"\u0002\r\u0011Jg.\u001b;%)\u0005Y&!A*\u0012\u00075\u00149\u0003E\u0003K\u0005S\u0011i#C\u0002\u0003,\r\u0013acU;cg\u000e\u0014\u0018\u000e\u001d;j_:<\u0016\u000e\u001e5DkJ\u001cxN\u001d\t\u0004S\n=B!B6'\u0005\u0004a'!D*vEN\u001c'/\u001b9uS>t7\u000f\u0005\u0004\u0002\u000e\tU\"\u0011H\u0005\u0005\u0005o\t\tC\u0001\u0003MSN$\bc\u0001B\u001eQ5\ta%A\tj]&$\u0018.\u00197Ck\u001a4WM]*ju\u0016\fQ\"\\1y\u0005V4g-\u001a:TSj,\u0017a\u0005:fcV,7\u000f\u001e$s_6,\u0006o\u001d;sK\u0006lGcA.\u0003F!9!q\t\u0017A\u0002\t%\u0013\u0001C3mK6,g\u000e^:\u0011\u00079\u0013Y%C\u0002\u0003N=\u0013A\u0001T8oO\u0006q1-\u00198dK2,\u0006o\u001d;sK\u0006l\u0017\u0001C:ikR$wn\u001e8\u0015\u0007m\u0013)\u0006C\u0004\u0003X9\u0002\r!!'\u0002\u0013\r|W\u000e\u001d7fi\u0016$\u0017AE2sK\u0006$XmU;cg\u000e\u0014\u0018\u000e\u001d;j_:$BA!\u000f\u0003^!1ql\fa\u0001\u0005?\u0002DA!\u0019\u0003fA!\u0011M\u001aB2!\rI'Q\r\u0003\r\u0005O\u0012i&!A\u0001\u0002\u000b\u0005!\u0011\u000e\u0002\u0004?\u0012\u0012\u0014c\u0001B\u0017a\u00061!-\u001e4gKJ,\"Aa\u001c\u0011\u000b)\u0013\tH!\f\n\u0007\tM4I\u0001\u0010SKNL'0\u00192mK6+H\u000e^5SK\u0006$WM\u001d*j]\u001e\u0014UO\u001a4fe\u0006Y!-\u001e4gKJ$UMY;h+\t\u0011I\b\u0005\u0003\u0003|\t\re\u0002\u0002B?\u0005\u007f\u00022!!\u0005P\u0013\r\u0011\tiT\u0001\u0007!J,G-\u001a4\n\t\u0005E$Q\u0011\u0006\u0004\u0005\u0003{\u0015!D:vEN\u001c'/\u001b9uS>t7/\u0006\u0002\u0003\fB\u0019!1H\u0015\u0002#M,(m]2sSB$\u0018n\u001c8t?\u0012*\u0017\u000fF\u0002\\\u0005#C\u0011\"a!4\u0003\u0003\u0005\rAa#\u0002'A,g\u000eZ5oO\u001a\u0013x.\\+qgR\u0014X-Y7\u0016\u0005\t%\u0013a\u00069f]\u0012Lgn\u001a$s_6,\u0006o\u001d;sK\u0006lw\fJ3r)\rY&1\u0014\u0005\n\u0003\u0007+\u0014\u0011!a\u0001\u0005\u0013\n1\"\u001a8e\u001f\u001a\u001cFO]3b[V\u0011!\u0011\u0015\t\u0004\u0005G\u001baB\u0001&\u0001\u0003=)g\u000eZ(g'R\u0014X-Y7`I\u0015\fHcA.\u0003*\"I\u00111Q\u001c\u0002\u0002\u0003\u0007!\u0011U\u0001\bGV\u00148o\u001c:t\u00035iwN]3SKF,Xm\u001d;fIR)1L!-\u00036\"9!1W\u001dA\u0002\te\u0012\u0001D:vEN\u001c'/\u001b9uS>t\u0007b\u0002B$s\u0001\u0007!\u0011J\u0001\u001ee\u0016\fX/Z:u\rJ|W.\u00169tiJ,\u0017-\\%g%\u0016\fX/\u001b:fI\u0006\u0001\u0002/^:i)>$un\u001e8tiJ,\u0017-\u001c\u000b\u00047\nu\u0006b\u0002B`w\u0001\u0007!QF\u0001\u0006m\u0006dW/Z\u0001\u0013G>l\u0007\u000f\\3uK\u0012{wO\\:ue\u0016\fW.A\bbE>\u0014H\u000fR8x]N$(/Z1n)\rY&q\u0019\u0005\b\u0003Gi\u0004\u0019AA\u0014\u0003I\u0011XmZ5ti\u0016\u00148+\u001e2tGJL'-\u001a:\u0015\u0007m\u0013i\r\u0003\u0004`}\u0001\u0007!q\u001a\u0019\u0005\u0005#\u0014)\u000e\u0005\u0003bM\nM\u0007cA5\u0003V\u0012a!q\u001bBg\u0003\u0003\u0005\tQ!\u0001\u0003j\t\u0019q\fJ\u001a\u0002\u001f\u0005$GmU;cg\u000e\u0014\u0018\u000e\u001d;j_:$2a\u0017Bo\u0011\u0019yv\b1\u0001\u0003`B\"!\u0011\u001dBs!\u0011\tgMa9\u0011\u0007%\u0014)\u000f\u0002\u0007\u0003h\nu\u0017\u0011!A\u0001\u0006\u0003\u0011IGA\u0002`IQ\na#\u001e8sK\u001eL7\u000f^3s'V\u00147o\u0019:jaRLwN\u001c\u000b\u00047\n5\bb\u0002BZ\u0001\u0002\u0007!\u0011H\u0001\u001fk:\u0014XmZ5ti\u0016\u00148+\u001e2tGJL\u0007\u000f^5p]&sG/\u001a:oC2$2a\u0017Bz\u0011\u001d\u0011\u0019,\u0011a\u0001\u0005s\u0001")
public interface SubscriberManagement<T>
extends ResizableMultiReaderRingBuffer.Cursors {
    public static ErrorCompleted ShutDown() {
        return SubscriberManagement$.MODULE$.ShutDown();
    }

    public void akka$stream$impl$SubscriberManagement$_setter_$akka$stream$impl$SubscriberManagement$$buffer_$eq(ResizableMultiReaderRingBuffer<T> var1);

    public int initialBufferSize();

    public int maxBufferSize();

    public void requestFromUpstream(long var1);

    public void cancelUpstream();

    public void shutdown(boolean var1);

    public SubscriptionWithCursor createSubscription(Subscriber<? super T> var1);

    public ResizableMultiReaderRingBuffer<T> akka$stream$impl$SubscriberManagement$$buffer();

    public static /* synthetic */ String bufferDebug$(SubscriberManagement $this) {
        return $this.bufferDebug();
    }

    default public String bufferDebug() {
        return this.akka$stream$impl$SubscriberManagement$$buffer().toString();
    }

    public List<SubscriptionWithCursor> akka$stream$impl$SubscriberManagement$$subscriptions();

    public void akka$stream$impl$SubscriberManagement$$subscriptions_$eq(List<SubscriptionWithCursor> var1);

    public long akka$stream$impl$SubscriberManagement$$pendingFromUpstream();

    public void akka$stream$impl$SubscriberManagement$$pendingFromUpstream_$eq(long var1);

    public EndOfStream akka$stream$impl$SubscriberManagement$$endOfStream();

    public void akka$stream$impl$SubscriberManagement$$endOfStream_$eq(EndOfStream var1);

    public static /* synthetic */ List cursors$(SubscriberManagement $this) {
        return $this.cursors();
    }

    default public List<SubscriptionWithCursor> cursors() {
        return this.akka$stream$impl$SubscriberManagement$$subscriptions();
    }

    public static /* synthetic */ void moreRequested$(SubscriberManagement $this, SubscriptionWithCursor subscription, long elements) {
        $this.moreRequested(subscription, elements);
    }

    default public void moreRequested(SubscriptionWithCursor subscription, long elements) {
        if (subscription.active()) {
            if (elements < 1L) {
                try {
                    ReactiveStreamsCompliance$.MODULE$.tryOnError(subscription.subscriber(), ReactiveStreamsCompliance$.MODULE$.numberOfElementsInRequestMustBePositiveException());
                }
                finally {
                    this.unregisterSubscriptionInternal(subscription);
                }
                return;
            }
            EndOfStream endOfStream = this.akka$stream$impl$SubscriberManagement$$endOfStream();
            if (SubscriberManagement$NotReached$.MODULE$.equals(endOfStream) ? true : SubscriberManagement$Completed$.MODULE$.equals(endOfStream)) {
                long d = subscription.totalDemand() + elements;
                long demand = d < 1L ? Long.MAX_VALUE : d;
                subscription.totalDemand_$eq(demand);
                long l = this.dispatchFromBufferAndReturnRemainingRequested$1(demand, endOfStream, subscription);
                if (Long.MIN_VALUE == l) {
                    endOfStream.apply(subscription.subscriber());
                    this.unregisterSubscriptionInternal(subscription);
                } else {
                    subscription.totalDemand_$eq(l);
                    this.requestFromUpstreamIfRequired();
                }
                return;
            }
            if (endOfStream instanceof ErrorCompleted) {
                return;
            }
            throw new MatchError(endOfStream);
        }
    }

    private void requestFromUpstreamIfRequired() {
        int desired = (int)Math.min(Integer.MAX_VALUE, Math.min(this.maxRequested$1(this.akka$stream$impl$SubscriberManagement$$subscriptions(), SubscriberManagement.maxRequested$default$2$1()), (long)this.akka$stream$impl$SubscriberManagement$$buffer().maxAvailable()) - this.akka$stream$impl$SubscriberManagement$$pendingFromUpstream());
        if (desired > 0) {
            this.akka$stream$impl$SubscriberManagement$$pendingFromUpstream_$eq(this.akka$stream$impl$SubscriberManagement$$pendingFromUpstream() + (long)desired);
            this.requestFromUpstream(desired);
            return;
        }
    }

    public static /* synthetic */ void pushToDownstream$(SubscriberManagement $this, Object value) {
        $this.pushToDownstream(value);
    }

    default public void pushToDownstream(T value) {
        EndOfStream endOfStream = this.akka$stream$impl$SubscriberManagement$$endOfStream();
        if (SubscriberManagement$NotReached$.MODULE$.equals(endOfStream)) {
            this.akka$stream$impl$SubscriberManagement$$pendingFromUpstream_$eq(this.akka$stream$impl$SubscriberManagement$$pendingFromUpstream() - 1L);
            if (!this.akka$stream$impl$SubscriberManagement$$buffer().write(value)) {
                throw new IllegalStateException("Output buffer overflow");
            }
            if (this.dispatch$1(this.akka$stream$impl$SubscriberManagement$$subscriptions(), SubscriberManagement.dispatch$default$2$1())) {
                this.requestFromUpstreamIfRequired();
                return;
            }
            return;
        }
        throw new IllegalStateException("pushToDownStream(...) after completeDownstream() or abortDownstream(...)");
    }

    public static /* synthetic */ void completeDownstream$(SubscriberManagement $this) {
        $this.completeDownstream();
    }

    default public void completeDownstream() {
        if (this.akka$stream$impl$SubscriberManagement$$endOfStream() == SubscriberManagement$NotReached$.MODULE$) {
            this.akka$stream$impl$SubscriberManagement$$endOfStream_$eq(SubscriberManagement$Completed$.MODULE$);
            this.akka$stream$impl$SubscriberManagement$$subscriptions_$eq(this.completeDoneSubscriptions$1(this.akka$stream$impl$SubscriberManagement$$subscriptions(), SubscriberManagement.completeDoneSubscriptions$default$2$1()));
            if (this.akka$stream$impl$SubscriberManagement$$subscriptions().isEmpty()) {
                this.shutdown(true);
                return;
            }
            return;
        }
    }

    public static /* synthetic */ void abortDownstream$(SubscriberManagement $this, Throwable cause) {
        $this.abortDownstream(cause);
    }

    default public void abortDownstream(Throwable cause) {
        this.akka$stream$impl$SubscriberManagement$$endOfStream_$eq(new ErrorCompleted(cause));
        this.akka$stream$impl$SubscriberManagement$$subscriptions().foreach((Function1<SubscriptionWithCursor, Object> & Serializable)s2 -> {
            SubscriberManagement.$anonfun$abortDownstream$1(this, s2);
            return BoxedUnit.UNIT;
        });
        this.akka$stream$impl$SubscriberManagement$$subscriptions_$eq(Nil$.MODULE$);
    }

    public static /* synthetic */ void registerSubscriber$(SubscriberManagement $this, Subscriber subscriber) {
        $this.registerSubscriber(subscriber);
    }

    default public void registerSubscriber(Subscriber<? super T> subscriber) {
        boolean bl = false;
        EndOfStream endOfStream = null;
        EndOfStream endOfStream2 = this.akka$stream$impl$SubscriberManagement$$endOfStream();
        if (SubscriberManagement$NotReached$.MODULE$.equals(endOfStream2)) {
            bl = true;
            endOfStream = endOfStream2;
            if (this.akka$stream$impl$SubscriberManagement$$subscriptions().exists((Function1<SubscriptionWithCursor, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(SubscriberManagement.$anonfun$registerSubscriber$1(subscriber, x$1)))) {
                ReactiveStreamsCompliance$.MODULE$.rejectDuplicateSubscriber(subscriber);
                return;
            }
        }
        if (bl) {
            this.addSubscription(subscriber);
            return;
        }
        if (SubscriberManagement$Completed$.MODULE$.equals(endOfStream2) && this.akka$stream$impl$SubscriberManagement$$buffer().nonEmpty()) {
            this.addSubscription(subscriber);
            return;
        }
        endOfStream2.apply(subscriber);
    }

    private void addSubscription(Subscriber<? super T> subscriber) {
        SubscriptionWithCursor newSubscription = this.createSubscription(subscriber);
        this.akka$stream$impl$SubscriberManagement$$subscriptions_$eq(this.akka$stream$impl$SubscriberManagement$$subscriptions().$colon$colon(newSubscription));
        this.akka$stream$impl$SubscriberManagement$$buffer().initCursor(newSubscription);
        try {
            ReactiveStreamsCompliance$.MODULE$.tryOnSubscribe(subscriber, newSubscription);
        }
        catch (Throwable throwable) {
            Throwable throwable2 = throwable;
            if (throwable2 instanceof ReactiveStreamsCompliance.SpecViolation) {
                this.unregisterSubscriptionInternal(newSubscription);
            }
            throw throwable;
        }
    }

    public static /* synthetic */ void unregisterSubscription$(SubscriberManagement $this, SubscriptionWithCursor subscription) {
        $this.unregisterSubscription(subscription);
    }

    default public void unregisterSubscription(SubscriptionWithCursor subscription) {
        this.unregisterSubscriptionInternal(subscription);
    }

    private void unregisterSubscriptionInternal(SubscriptionWithCursor subscription) {
        if (subscription.active()) {
            this.akka$stream$impl$SubscriberManagement$$subscriptions_$eq(this.removeFrom$1(this.akka$stream$impl$SubscriberManagement$$subscriptions(), SubscriberManagement.removeFrom$default$2$1(), subscription));
            this.akka$stream$impl$SubscriberManagement$$buffer().onCursorRemoved(subscription);
            subscription.active_$eq(false);
            if (this.akka$stream$impl$SubscriberManagement$$subscriptions().isEmpty()) {
                if (this.akka$stream$impl$SubscriberManagement$$endOfStream() == SubscriberManagement$NotReached$.MODULE$) {
                    this.akka$stream$impl$SubscriberManagement$$endOfStream_$eq(SubscriberManagement$.MODULE$.ShutDown());
                    this.cancelUpstream();
                }
                this.shutdown(false);
                return;
            }
            this.requestFromUpstreamIfRequired();
            return;
        }
    }

    private long dispatchFromBufferAndReturnRemainingRequested$1(long requested, EndOfStream eos, SubscriptionWithCursor subscription$1) {
        block7: {
            while (true) {
                boolean bl;
                if (requested == 0L) {
                    if (eos != SubscriberManagement$NotReached$.MODULE$ && this.akka$stream$impl$SubscriberManagement$$buffer().count(subscription$1) == 0) {
                        return Long.MIN_VALUE;
                    }
                    return 0L;
                }
                if (this.akka$stream$impl$SubscriberManagement$$buffer().count(subscription$1) <= 0) break block7;
                try {
                    subscription$1.dispatch(this.akka$stream$impl$SubscriberManagement$$buffer().read(subscription$1));
                    bl = true;
                }
                catch (Throwable throwable) {
                    Throwable throwable2 = throwable;
                    if (throwable2 instanceof ReactiveStreamsCompliance.SpecViolation) {
                        this.unregisterSubscriptionInternal(subscription$1);
                        bl = false;
                    }
                    throw throwable;
                }
                boolean goOn = bl;
                if (!goOn) break;
                --requested;
            }
            return Long.MIN_VALUE;
        }
        if (eos != SubscriberManagement$NotReached$.MODULE$) {
            return Long.MIN_VALUE;
        }
        return requested;
    }

    private long maxRequested$1(List remaining, long result) {
        List list;
        while ((list = remaining) instanceof $colon$colon) {
            $colon$colon $colon$colon = ($colon$colon)list;
            SubscriptionWithCursor head = (SubscriptionWithCursor)$colon$colon.head();
            List tail = $colon$colon.next$access$1();
            result = package$.MODULE$.max(head.totalDemand(), result);
            remaining = tail;
        }
        return result;
    }

    private static long maxRequested$default$2$1() {
        return 0L;
    }

    private boolean dispatch$1(List remaining, boolean sent) {
        List list;
        while ((list = remaining) instanceof $colon$colon) {
            $colon$colon $colon$colon = ($colon$colon)list;
            SubscriptionWithCursor head = (SubscriptionWithCursor)$colon$colon.head();
            List tail = $colon$colon.next$access$1();
            if (head.totalDemand() > 0L) {
                T element = this.akka$stream$impl$SubscriberManagement$$buffer().read(head);
                head.dispatch(element);
                head.totalDemand_$eq(head.totalDemand() - 1L);
                sent = true;
                remaining = tail;
                continue;
            }
            remaining = tail;
        }
        return sent;
    }

    private static boolean dispatch$default$2$1() {
        return false;
    }

    private List completeDoneSubscriptions$1(List remaining, List result) {
        List list;
        while ((list = remaining) instanceof $colon$colon) {
            $colon$colon $colon$colon = ($colon$colon)list;
            SubscriptionWithCursor head = (SubscriptionWithCursor)$colon$colon.head();
            List tail = $colon$colon.next$access$1();
            if (this.akka$stream$impl$SubscriberManagement$$buffer().count(head) == 0) {
                head.active_$eq(false);
                SubscriberManagement$Completed$.MODULE$.apply(head.subscriber());
                remaining = tail;
                continue;
            }
            SubscriptionWithCursor subscriptionWithCursor = head;
            result = result.$colon$colon(subscriptionWithCursor);
            remaining = tail;
        }
        return result;
    }

    private static List completeDoneSubscriptions$default$2$1() {
        return Nil$.MODULE$;
    }

    public static /* synthetic */ void $anonfun$abortDownstream$1(SubscriberManagement $this, SubscriptionWithCursor s2) {
        $this.akka$stream$impl$SubscriberManagement$$endOfStream().apply(s2.subscriber());
    }

    public static /* synthetic */ boolean $anonfun$registerSubscriber$1(Subscriber subscriber$1, SubscriptionWithCursor x$1) {
        Subscriber subscriber = x$1.subscriber();
        Subscriber subscriber2 = subscriber$1;
        return !(subscriber != null ? !subscriber.equals(subscriber2) : subscriber2 != null);
    }

    private List removeFrom$1(List remaining, List result, SubscriptionWithCursor subscription$2) {
        List list;
        while ((list = remaining) instanceof $colon$colon) {
            $colon$colon $colon$colon = ($colon$colon)list;
            SubscriptionWithCursor head = (SubscriptionWithCursor)$colon$colon.head();
            List tail = $colon$colon.next$access$1();
            if (head == subscription$2) {
                return result.reverse_$colon$colon$colon(tail);
            }
            SubscriptionWithCursor subscriptionWithCursor = head;
            result = result.$colon$colon(subscriptionWithCursor);
            remaining = tail;
        }
        throw new IllegalStateException("Subscription to unregister not found");
    }

    private static List removeFrom$default$2$1() {
        return Nil$.MODULE$;
    }

    public static void $init$(SubscriberManagement $this) {
        $this.akka$stream$impl$SubscriberManagement$_setter_$akka$stream$impl$SubscriberManagement$$buffer_$eq(new ResizableMultiReaderRingBuffer($this.initialBufferSize(), $this.maxBufferSize(), $this));
        $this.akka$stream$impl$SubscriberManagement$$subscriptions_$eq(Nil$.MODULE$);
        $this.akka$stream$impl$SubscriberManagement$$pendingFromUpstream_$eq(0L);
        $this.akka$stream$impl$SubscriberManagement$$endOfStream_$eq(SubscriberManagement$NotReached$.MODULE$);
    }

    public static interface EndOfStream {
        public <T> void apply(Subscriber<T> var1);
    }

    public static final class ErrorCompleted
    implements EndOfStream,
    Product,
    Serializable {
        private final Throwable cause;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Throwable cause() {
            return this.cause;
        }

        @Override
        public <T> void apply(Subscriber<T> subscriber) {
            ReactiveStreamsCompliance$.MODULE$.tryOnError(subscriber, this.cause());
        }

        public ErrorCompleted copy(Throwable cause) {
            return new ErrorCompleted(cause);
        }

        public Throwable copy$default$1() {
            return this.cause();
        }

        @Override
        public String productPrefix() {
            return "ErrorCompleted";
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
                    return this.cause();
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
            return x$1 instanceof ErrorCompleted;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "cause";
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
            if (!(object instanceof ErrorCompleted)) return false;
            boolean bl = true;
            if (!bl) return false;
            ErrorCompleted errorCompleted = (ErrorCompleted)x$1;
            Throwable throwable = this.cause();
            Throwable throwable2 = errorCompleted.cause();
            if (throwable != null) {
                if (!throwable.equals(throwable2)) return false;
                return true;
            }
            if (throwable2 == null) return true;
            return false;
        }

        public ErrorCompleted(Throwable cause) {
            this.cause = cause;
            Product.$init$(this);
        }
    }
}

