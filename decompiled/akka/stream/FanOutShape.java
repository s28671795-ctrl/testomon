/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.Shape;
import java.io.Serializable;
import scala.Function1;
import scala.Product;
import scala.collection.Iterator;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Vector;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\r\u0005t!\u0002%J\u0011\u0003qe!\u0002)J\u0011\u0003\t\u0006\"\u0002-\u0002\t\u0003Ifa\u0002.\u0002!\u0003\r\nc\u0017\u0005\u0006;\u000e1\tA\u0018\u0005\u0006[\u000e1\tA\u001c\u0005\u0006}\u000e1\ta \u0004\u0007\u00033\t!)a\u0007\t\u0011y<!Q3A\u0005B}D!\"a\u0010\b\u0005#\u0005\u000b\u0011BA\u0001\u0011\u0019Av\u0001\"\u0001\u0002B!1Ql\u0002C!\u0003\u000fBa!\\\u0004\u0005B\u0005-\u0003\"CA-\u000f\u0005\u0005I\u0011AA.\u0011%\t9gBI\u0001\n\u0003\tI\u0007C\u0005\u0002\u0004\u001e\t\t\u0011\"\u0011\u0002\u0006\"I\u0011QS\u0004\u0002\u0002\u0013\u0005\u0011q\u0013\u0005\n\u0003?;\u0011\u0011!C\u0001\u0003CC\u0011\"a*\b\u0003\u0003%\t%!+\t\u0013\u0005Mv!!A\u0005\u0002\u0005U\u0006\"CA`\u000f\u0005\u0005I\u0011IAa\u0011%\t)mBA\u0001\n\u0003\n9\rC\u0005\u0002J\u001e\t\t\u0011\"\u0011\u0002L\"I\u0011QZ\u0004\u0002\u0002\u0013\u0005\u0013qZ\u0004\n\u0005s\t\u0011\u0011!E\u0001\u0005w1\u0011\"!\u0007\u0002\u0003\u0003E\tA!\u0010\t\raKB\u0011\u0001B%\u0011%\tI-GA\u0001\n\u000b\nY\rC\u0005\u0003Le\t\t\u0011\"!\u0003N!I!\u0011L\r\u0002\u0002\u0013\u0005%1\f\u0005\n\u0005_J\u0012\u0011!C\u0005\u0005c2a!a5\u0002\u0005\u0006U\u0007\"C/ \u0005+\u0007I\u0011IAp\u0011)\t\u0019o\bB\tB\u0003%\u0011\u0011\u001d\u0005\n[~\u0011)\u001a!C!\u0003KD!\"a= \u0005#\u0005\u000b\u0011BAt\u0011\u0019Av\u0004\"\u0001\u0002v\")ap\bC!\u007f\"I\u0011\u0011L\u0010\u0002\u0002\u0013\u0005!q\u0001\u0005\n\u0003Oz\u0012\u0013!C\u0001\u0005/A\u0011Ba\b #\u0003%\tA!\t\t\u0013\u0005\ru$!A\u0005B\u0005\u0015\u0005\"CAK?\u0005\u0005I\u0011AAL\u0011%\tyjHA\u0001\n\u0003\u0011I\u0003C\u0005\u0002(~\t\t\u0011\"\u0011\u0002*\"I\u00111W\u0010\u0002\u0002\u0013\u0005!Q\u0006\u0005\n\u0003\u007f{\u0012\u0011!C!\u0005cA\u0011\"!2 \u0003\u0003%\t%a2\t\u0013\u0005%w$!A\u0005B\u0005-\u0007\"CAg?\u0005\u0005I\u0011\tB\u001b\u000f%\u0011I(AA\u0001\u0012\u0003\u0011YHB\u0005\u0002T\u0006\t\t\u0011#\u0001\u0003~!1\u0001l\rC\u0001\u0005\u007fB\u0011\"!34\u0003\u0003%)%a3\t\u0013\t-3'!A\u0005\u0002\n\u0005\u0005\"\u0003B-g\u0005\u0005I\u0011\u0011BO\u0011%\u0011ygMA\u0001\n\u0013\u0011\tH\u0002\u0004Q\u0013\u0006\u0005!Q\u0018\u0005\u000b\u0005\u000fL$\u0011!Q\u0001\n\t%\u0007B\u0003Bis\t\u0005\t\u0015!\u0003\u0003T\"Q!\u0011]\u001d\u0003\u0002\u0003\u0006I!!\u0001\t\raKD\u0011\u0002Br\u0011\u0019A\u0016\b\"\u0001\u0003x\"91\u0011A\u001d\u0005\u0006\r\r\u0001BB7:\t\u0003\u001a)\u0001C\u0004\u0004\u0014e\")e!\u0006\t\u0013\re\u0011\b1A\u0005\n\rm\u0001\"CB\u0018s\u0001\u0007I\u0011BB\u0019\u0011!\u0019i#\u000fQ!\n\ru\u0001bBB#s\u0011E1q\t\u0005\b\u0007+Jd\u0011CB,\u0011\u001d\u0019i&\u000fC\u0001\u0007?\n1BR1o\u001fV$8\u000b[1qK*\u0011!jS\u0001\u0007gR\u0014X-Y7\u000b\u00031\u000bA!Y6lC\u000e\u0001\u0001CA(\u0002\u001b\u0005I%a\u0003$b]>+Ho\u00155ba\u0016\u001c\"!\u0001*\u0011\u0005M3V\"\u0001+\u000b\u0003U\u000bQa]2bY\u0006L!a\u0016+\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\taJ\u0001\u0003J]&$XC\u0001/e'\t\u0019!+A\u0003j]2,G/F\u0001`!\ry\u0005MY\u0005\u0003C&\u0013Q!\u00138mKR\u0004\"a\u00193\r\u0001\u0011)Qm\u0001b\u0001M\n\t\u0011*\u0005\u0002hUB\u00111\u000b[\u0005\u0003SR\u0013qAT8uQ&tw\r\u0005\u0002TW&\u0011A\u000e\u0016\u0002\u0004\u0003:L\u0018aB8vi2,Go]\u000b\u0002_B\u0019\u0001/^<\u000e\u0003ET!A]:\u0002\u0013%lW.\u001e;bE2,'B\u0001;U\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003mF\u00141aU3ra\tAH\u0010E\u0002PsnL!A_%\u0003\r=+H\u000f\\3u!\t\u0019G\u0010B\u0005~\u000b\u0005\u0005\t\u0011!B\u0001M\n\u0019q\fJ\u0019\u0002\t9\fW.Z\u000b\u0003\u0003\u0003\u0001B!a\u0001\u0002\u00129!\u0011QAA\u0007!\r\t9\u0001V\u0007\u0003\u0003\u0013Q1!a\u0003N\u0003\u0019a$o\\8u}%\u0019\u0011q\u0002+\u0002\rA\u0013X\rZ3g\u0013\u0011\t\u0019\"!\u0006\u0003\rM#(/\u001b8h\u0015\r\ty\u0001V\u0015\u0004\u0007\u001dy\"\u0001\u0002(b[\u0016,B!!\b\u0002&MAqAUA\u0010\u0003O\ti\u0003E\u0003\u0002\"\r\t\u0019#D\u0001\u0002!\r\u0019\u0017Q\u0005\u0003\u0006K\u001e\u0011\rA\u001a\t\u0004'\u0006%\u0012bAA\u0016)\n9\u0001K]8ek\u000e$\b\u0003BA\u0018\u0003sqA!!\r\u000269!\u0011qAA\u001a\u0013\u0005)\u0016bAA\u001c)\u00069\u0001/Y2lC\u001e,\u0017\u0002BA\u001e\u0003{\u0011AbU3sS\u0006d\u0017N_1cY\u0016T1!a\u000eU\u0003\u0015q\u0017-\\3!)\u0011\t\u0019%!\u0012\u0011\u000b\u0005\u0005r!a\t\t\ryT\u0001\u0019AA\u0001+\t\tI\u0005\u0005\u0003PA\u0006\rRCAA'!\u0011\u0001X/a\u00141\t\u0005E\u0013Q\u000b\t\u0005\u001ff\f\u0019\u0006E\u0002d\u0003+\"!\"a\u0016\r\u0003\u0003\u0005\tQ!\u0001g\u0005\ryFEM\u0001\u0005G>\u0004\u00180\u0006\u0003\u0002^\u0005\rD\u0003BA0\u0003K\u0002R!!\t\b\u0003C\u00022aYA2\t\u0015)WB1\u0001g\u0011!qX\u0002%AA\u0002\u0005\u0005\u0011AD2paf$C-\u001a4bk2$H%M\u000b\u0005\u0003W\n\t)\u0006\u0002\u0002n)\"\u0011\u0011AA8W\t\t\t\b\u0005\u0003\u0002t\u0005uTBAA;\u0015\u0011\t9(!\u001f\u0002\u0013Ut7\r[3dW\u0016$'bAA>)\u0006Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005}\u0014Q\u000f\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,G!B3\u000f\u0005\u00041\u0017!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0002\bB!\u0011\u0011RAJ\u001b\t\tYI\u0003\u0003\u0002\u000e\u0006=\u0015\u0001\u00027b]\u001eT!!!%\u0002\t)\fg/Y\u0005\u0005\u0003'\tY)\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0006\u0002\u0002\u001aB\u00191+a'\n\u0007\u0005uEKA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000fF\u0002k\u0003GC\u0011\"!*\u0012\u0003\u0003\u0005\r!!'\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\tY\u000bE\u0003\u0002.\u0006=&.D\u0001t\u0013\r\t\tl\u001d\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u00028\u0006u\u0006cA*\u0002:&\u0019\u00111\u0018+\u0003\u000f\t{w\u000e\\3b]\"A\u0011QU\n\u0002\u0002\u0003\u0007!.\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003BAD\u0003\u0007D\u0011\"!*\u0015\u0003\u0003\u0005\r!!'\u0002\u0011!\f7\u000f[\"pI\u0016$\"!!'\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!a\"\u0002\r\u0015\fX/\u00197t)\u0011\t9,!5\t\u0011\u0005\u0015v#!AA\u0002)\u0014Q\u0001U8siN,B!a6\u0002^NAqDUAm\u0003O\ti\u0003E\u0003\u0002\"\r\tY\u000eE\u0002d\u0003;$Q!Z\u0010C\u0002\u0019,\"!!9\u0011\t=\u0003\u00171\\\u0001\u0007S:dW\r\u001e\u0011\u0016\u0005\u0005\u001d\b\u0003\u00029v\u0003S\u0004D!a;\u0002pB!q*_Aw!\r\u0019\u0017q\u001e\u0003\u000b\u0003c\u001c\u0013\u0011!A\u0001\u0006\u00031'aA0%g\u0005Aq.\u001e;mKR\u001c\b\u0005\u0006\u0004\u0002x\u0006e\u00181 \t\u0006\u0003Cy\u00121\u001c\u0005\u0007;\u0012\u0002\r!!9\t\r5$\u0003\u0019AA\u007f!\u0011\u0001X/a@1\t\t\u0005!Q\u0001\t\u0005\u001ff\u0014\u0019\u0001E\u0002d\u0005\u000b!1\"!=\u0002|\u0006\u0005\t\u0011!B\u0001MV!!\u0011\u0002B\b)\u0019\u0011YA!\u0005\u0003\u0016A)\u0011\u0011E\u0010\u0003\u000eA\u00191Ma\u0004\u0005\u000b\u00154#\u0019\u00014\t\u0011u3\u0003\u0013!a\u0001\u0005'\u0001Ba\u00141\u0003\u000e!AQN\nI\u0001\u0002\u0004\ti0\u0006\u0003\u0003\u001a\tuQC\u0001B\u000eU\u0011\t\t/a\u001c\u0005\u000b\u0015<#\u0019\u00014\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU!!1\u0005B\u0014+\t\u0011)C\u000b\u0003\u0002h\u0006=D!B3)\u0005\u00041Gc\u00016\u0003,!I\u0011QU\u0016\u0002\u0002\u0003\u0007\u0011\u0011\u0014\u000b\u0005\u0003o\u0013y\u0003\u0003\u0005\u0002&6\n\t\u00111\u0001k)\u0011\t9Ia\r\t\u0013\u0005\u0015f&!AA\u0002\u0005eE\u0003BA\\\u0005oA\u0001\"!*2\u0003\u0003\u0005\rA[\u0001\u0005\u001d\u0006lW\rE\u0002\u0002\"e\u0019B!\u0007*\u0003@A!!\u0011\tB$\u001b\t\u0011\u0019E\u0003\u0003\u0003F\u0005=\u0015AA5p\u0013\u0011\tYDa\u0011\u0015\u0005\tm\u0012!B1qa2LX\u0003\u0002B(\u0005+\"BA!\u0015\u0003XA)\u0011\u0011E\u0004\u0003TA\u00191M!\u0016\u0005\u000b\u0015d\"\u0019\u00014\t\ryd\u0002\u0019AA\u0001\u0003\u001d)h.\u00199qYf,BA!\u0018\u0003nQ!!q\fB3!\u0015\u0019&\u0011MA\u0001\u0013\r\u0011\u0019\u0007\u0016\u0002\u0007\u001fB$\u0018n\u001c8\t\u0013\t\u001dT$!AA\u0002\t%\u0014a\u0001=%aA)\u0011\u0011E\u0004\u0003lA\u00191M!\u001c\u0005\u000b\u0015l\"\u0019\u00014\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\tM\u0004\u0003BAE\u0005kJAAa\u001e\u0002\f\n1qJ\u00196fGR\fQ\u0001U8siN\u00042!!\t4'\u0011\u0019$Ka\u0010\u0015\u0005\tmT\u0003\u0002BB\u0005\u0013#bA!\"\u0003\f\n=\u0005#BA\u0011?\t\u001d\u0005cA2\u0003\n\u0012)QM\u000eb\u0001M\"1QL\u000ea\u0001\u0005\u001b\u0003Ba\u00141\u0003\b\"1QN\u000ea\u0001\u0005#\u0003B\u0001];\u0003\u0014B\"!Q\u0013BM!\u0011y\u0015Pa&\u0011\u0007\r\u0014I\nB\u0006\u0002r\nm\u0015\u0011!A\u0001\u0006\u00031\u0007BB77\u0001\u0004\u0011\t*\u0006\u0003\u0003 \n5F\u0003\u0002BQ\u0005s\u0003Ra\u0015B1\u0005G\u0003ra\u0015BS\u0005S\u0013y+C\u0002\u0003(R\u0013a\u0001V;qY\u0016\u0014\u0004\u0003B(a\u0005W\u00032a\u0019BW\t\u0015)wG1\u0001g!\u0011\u0001XO!-1\t\tM&q\u0017\t\u0005\u001ff\u0014)\fE\u0002d\u0005o#!\"!=8\u0003\u0003\u0005\tQ!\u0001g\u0011%\u00119gNA\u0001\u0002\u0004\u0011Y\fE\u0003\u0002\"}\u0011Y+\u0006\u0003\u0003@\n=7cA\u001d\u0003BB\u0019qJa1\n\u0007\t\u0015\u0017JA\u0003TQ\u0006\u0004X-A\u0002`S:\u0004Ba\u00141\u0003L*\"!QZA8!\r\u0019'q\u001a\u0003\u0007KfB)\u0019\u00014\u0002\u0017}\u0013XmZ5ti\u0016\u0014X\r\u001a\t\u0007\u0003_\u0011)Na6\n\t\u0005E\u0016Q\b\u0019\u0005\u00053\u0014i\u000e\u0005\u0003Ps\nm\u0007cA2\u0003^\u0012Q!q\\\u001e\u0002\u0002\u0003\u0005)\u0011\u00014\u0003\u0007}#C'A\u0003`]\u0006lW\r\u0006\u0005\u0003f\n\u001d(\u0011\u001eB{!\u0011y\u0015H!4\t\u000f\t\u001dW\b1\u0001\u0003J\"9!\u0011[\u001fA\u0002\t-\bCBA\u0018\u0005+\u0014i\u000f\r\u0003\u0003p\nM\b\u0003B(z\u0005c\u00042a\u0019Bz\t-\u0011yN!;\u0002\u0002\u0003\u0005)\u0011\u00014\t\u000f\t\u0005X\b1\u0001\u0002\u0002Q!!Q\u001dB}\u0011\u001d\u0011YP\u0010a\u0001\u0005{\fA!\u001b8jiB)!q`\u0002\u0003N:\u0011q\nA\u0001\u0003S:,\"A!3\u0016\u0005\r\u001d\u0001\u0003\u00029v\u0007\u0013\u0001Daa\u0003\u0004\u0010A!q*_B\u0007!\r\u00197q\u0002\u0003\u000b\u0007#\u0001\u0015\u0011!A\u0001\u0006\u00031'aA0%k\u00051\u0011N\u001c7fiN,\"aa\u0006\u0011\tA,(\u0011Z\u0001\t?>,H\u000f\\3ugV\u00111Q\u0004\t\u0007\u0003_\u0019yba\t\n\t\r\u0005\u0012Q\b\u0002\u0007-\u0016\u001cGo\u001c:1\t\r\u00152\u0011\u0006\t\u0005\u001ff\u001c9\u0003E\u0002d\u0007S!!ba\u000bE\u0003\u0003\u0005\tQ!\u0001g\u0005\ryFEN\u0001\n?>,H\u000f\\3ug\u0002\nAbX8vi2,Go]0%KF$Baa\r\u0004:A\u00191k!\u000e\n\u0007\r]BK\u0001\u0003V]&$\b\"CAS\u0007\u0006\u0005\t\u0019AB\u001e!\u0019\tyca\b\u0004>A\"1qHB\"!\u0011y\u0015p!\u0011\u0011\u0007\r\u001c\u0019\u0005B\u0006\u0004,\re\u0012\u0011!A\u0001\u0006\u00031\u0017!\u00038fo>+H\u000f\\3u+\u0011\u0019Iea\u0014\u0015\t\r-31\u000b\t\u0005\u001ff\u001ci\u0005E\u0002d\u0007\u001f\"aa!\u0015F\u0005\u00041'!\u0001+\t\ry,\u0005\u0019AA\u0001\u0003%\u0019wN\\:ueV\u001cG\u000f\u0006\u0003\u0003f\u000ee\u0003b\u0002B~\r\u0002\u000711\f\t\u0006\u0005\u007f\u001c!1Z\u0001\tI\u0016,\u0007oQ8qsR\u0011!Q\u001d")
public abstract class FanOutShape<I>
extends Shape {
    private final Inlet<I> _in;
    private final Iterator<Outlet<?>> _registered;
    private final String _name;
    private Vector<Outlet<?>> _outlets;

    public final Inlet<I> in() {
        return this._in;
    }

    @Override
    public Seq<Outlet<?>> outlets() {
        return this._outlets();
    }

    public final Seq<Inlet<I>> inlets() {
        Inlet<I> inlet = this.in();
        return Nil$.MODULE$.$colon$colon(inlet);
    }

    private Vector<Outlet<?>> _outlets() {
        return this._outlets;
    }

    private void _outlets_$eq(Vector<Outlet<?>> x$1) {
        this._outlets = x$1;
    }

    public <T> Outlet<T> newOutlet(String name) {
        Outlet<?> p = this._registered.hasNext() ? this._registered.next() : Outlet$.MODULE$.apply(new StringBuilder(1).append(this._name).append(".").append(name).toString());
        this._outlets_$eq((Vector)this._outlets().$colon$plus(p));
        return p;
    }

    public abstract FanOutShape<I> construct(Init<I> var1);

    @Override
    public FanOutShape<I> deepCopy() {
        return this.construct(new Ports<I>(this._in.carbonCopy(), (Seq)this.outlets().map((Function1<Outlet, Outlet> & Serializable)x$1 -> x$1.carbonCopy())));
    }

    private FanOutShape(Inlet<I> _in, Iterator<Outlet<?>> _registered, String _name) {
        this._in = _in;
        this._registered = _registered;
        this._name = _name;
        this._outlets = package$.MODULE$.Vector().empty();
    }

    public FanOutShape(Init<I> init2) {
        this(init2.inlet(), init2.outlets().iterator(), init2.name());
    }

    public static interface Init<I> {
        public Inlet<I> inlet();

        public Seq<Outlet<?>> outlets();

        public String name();
    }

    public static final class Name<I>
    implements Init<I>,
    Product,
    Serializable {
        private final String name;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public String name() {
            return this.name;
        }

        @Override
        public Inlet<I> inlet() {
            return Inlet$.MODULE$.apply(new StringBuilder(3).append(this.name()).append(".in").toString());
        }

        @Override
        public Seq<Outlet<?>> outlets() {
            return Nil$.MODULE$;
        }

        public <I> Name<I> copy(String name) {
            return new Name<I>(name);
        }

        public <I> String copy$default$1() {
            return this.name();
        }

        @Override
        public String productPrefix() {
            return "Name";
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
                    return this.name();
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
            return x$1 instanceof Name;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "name";
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
            if (!(object instanceof Name)) return false;
            boolean bl = true;
            if (!bl) return false;
            Name name = (Name)x$1;
            String string = this.name();
            String string2 = name.name();
            if (string != null) {
                if (!string.equals(string2)) return false;
                return true;
            }
            if (string2 == null) return true;
            return false;
        }

        public Name(String name) {
            this.name = name;
            Product.$init$(this);
        }
    }

    public static final class Ports<I>
    implements Init<I>,
    Product,
    Serializable {
        private final Inlet<I> inlet;
        private final Seq<Outlet<?>> outlets;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public Inlet<I> inlet() {
            return this.inlet;
        }

        @Override
        public Seq<Outlet<?>> outlets() {
            return this.outlets;
        }

        @Override
        public String name() {
            return "FanOut";
        }

        public <I> Ports<I> copy(Inlet<I> inlet, Seq<Outlet<?>> outlets) {
            return new Ports<I>(inlet, outlets);
        }

        public <I> Inlet<I> copy$default$1() {
            return this.inlet();
        }

        public <I> Seq<Outlet<?>> copy$default$2() {
            return this.outlets();
        }

        @Override
        public String productPrefix() {
            return "Ports";
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
                    return this.inlet();
                }
                case 1: {
                    return this.outlets();
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
            return x$1 instanceof Ports;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "inlet";
                }
                case 1: {
                    return "outlets";
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
            if (!(object instanceof Ports)) return false;
            boolean bl = true;
            if (!bl) return false;
            Ports ports = (Ports)x$1;
            Inlet<I> inlet = this.inlet();
            Inlet<I> inlet2 = ports.inlet();
            if (inlet == null) {
                if (inlet2 != null) {
                    return false;
                }
            } else if (!((Object)inlet).equals(inlet2)) return false;
            Seq<Outlet<?>> seq = this.outlets();
            Seq<Outlet<?>> seq2 = ports.outlets();
            if (seq == null) {
                if (seq2 == null) return true;
                return false;
            } else {
                if (!seq.equals(seq2)) return false;
                return true;
            }
        }

        public Ports(Inlet<I> inlet, Seq<Outlet<?>> outlets) {
            this.inlet = inlet;
            this.outlets = outlets;
            Product.$init$(this);
        }
    }
}

