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

@ScalaSignature(bytes="\u0006\u0005\r\u0005t!\u0002%J\u0011\u0003qe!\u0002)J\u0011\u0003\t\u0006\"\u0002-\u0002\t\u0003Ifa\u0002.\u0002!\u0003\r\nc\u0017\u0005\u0006;\u000e1\tA\u0018\u0005\u0006[\u000e1\tA\u001c\u0005\u0006}\u000e1\ta \u0004\u0007\u00033\t!)a\u0007\t\u0011y<!Q3A\u0005B}D!\"a\u0010\b\u0005#\u0005\u000b\u0011BA\u0001\u0011\u0019Av\u0001\"\u0001\u0002B!1Ql\u0002C!\u0003\u000fBa!\\\u0004\u0005B\u0005-\u0003\"CA-\u000f\u0005\u0005I\u0011AA.\u0011%\t9gBI\u0001\n\u0003\tI\u0007C\u0005\u0002\u0004\u001e\t\t\u0011\"\u0011\u0002\u0006\"I\u0011QS\u0004\u0002\u0002\u0013\u0005\u0011q\u0013\u0005\n\u0003?;\u0011\u0011!C\u0001\u0003CC\u0011\"a*\b\u0003\u0003%\t%!+\t\u0013\u0005Mv!!A\u0005\u0002\u0005U\u0006\"CA`\u000f\u0005\u0005I\u0011IAa\u0011%\t)mBA\u0001\n\u0003\n9\rC\u0005\u0002J\u001e\t\t\u0011\"\u0011\u0002L\"I\u0011QZ\u0004\u0002\u0002\u0013\u0005\u0013qZ\u0004\n\u0005s\t\u0011\u0011!E\u0001\u0005w1\u0011\"!\u0007\u0002\u0003\u0003E\tA!\u0010\t\raKB\u0011\u0001B%\u0011%\tI-GA\u0001\n\u000b\nY\rC\u0005\u0003Le\t\t\u0011\"!\u0003N!I!\u0011L\r\u0002\u0002\u0013\u0005%1\f\u0005\n\u0005_J\u0012\u0011!C\u0005\u0005c2a!a5\u0002\u0005\u0006U\u0007\"C/ \u0005+\u0007I\u0011IAp\u0011)\t\u0019o\bB\tB\u0003%\u0011\u0011\u001d\u0005\n[~\u0011)\u001a!C!\u0003KD!\"a= \u0005#\u0005\u000b\u0011BAt\u0011\u0019Av\u0004\"\u0001\u0002v\")ap\bC!\u007f\"I\u0011\u0011L\u0010\u0002\u0002\u0013\u0005!q\u0001\u0005\n\u0003Oz\u0012\u0013!C\u0001\u0005/A\u0011Ba\b #\u0003%\tA!\t\t\u0013\u0005\ru$!A\u0005B\u0005\u0015\u0005\"CAK?\u0005\u0005I\u0011AAL\u0011%\tyjHA\u0001\n\u0003\u0011I\u0003C\u0005\u0002(~\t\t\u0011\"\u0011\u0002*\"I\u00111W\u0010\u0002\u0002\u0013\u0005!Q\u0006\u0005\n\u0003\u007f{\u0012\u0011!C!\u0005cA\u0011\"!2 \u0003\u0003%\t%a2\t\u0013\u0005%w$!A\u0005B\u0005-\u0007\"CAg?\u0005\u0005I\u0011\tB\u001b\u000f%\u0011I(AA\u0001\u0012\u0003\u0011YHB\u0005\u0002T\u0006\t\t\u0011#\u0001\u0003~!1\u0001l\rC\u0001\u0005\u007fB\u0011\"!34\u0003\u0003%)%a3\t\u0013\t-3'!A\u0005\u0002\n\u0005\u0005\"\u0003B-g\u0005\u0005I\u0011\u0011BO\u0011%\u0011ygMA\u0001\n\u0013\u0011\tH\u0002\u0004Q\u0013\u0006\u0005!Q\u0018\u0005\u000b\u0005\u000fL$\u0011!Q\u0001\n\t%\u0007B\u0003Bis\t\u0005\t\u0015!\u0003\u0003T\"Q!\u0011]\u001d\u0003\u0002\u0003\u0006I!!\u0001\t\raKD\u0011\u0002Br\u0011\u0019A\u0016\b\"\u0001\u0003x\"91\u0011A\u001d\u0005\u0006\r\r\u0001bBB\u0003s\u0011\u00153q\u0001\u0005\u0007[f\"\tea\u0003\t\u0013\re\u0011\b1A\u0005\n\rm\u0001\"CB\u0018s\u0001\u0007I\u0011BB\u0019\u0011!\u0019i#\u000fQ!\n\ru\u0001bBB#s\u0011E1q\t\u0005\b\u0007+Jd\u0011CB,\u0011\u001d\u0019i&\u000fC\u0001\u0007?\n!BR1o\u0013:\u001c\u0006.\u00199f\u0015\tQ5*\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u0019\u0006!\u0011m[6b\u0007\u0001\u0001\"aT\u0001\u000e\u0003%\u0013!BR1o\u0013:\u001c\u0006.\u00199f'\t\t!\u000b\u0005\u0002T-6\tAKC\u0001V\u0003\u0015\u00198-\u00197b\u0013\t9FK\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u00039\u0013A!\u00138jiV\u0011A\fZ\n\u0003\u0007I\u000baa\\;uY\u0016$X#A0\u0011\u0007=\u0003'-\u0003\u0002b\u0013\n1q*\u001e;mKR\u0004\"a\u00193\r\u0001\u0011)Qm\u0001b\u0001M\n\tq*\u0005\u0002hUB\u00111\u000b[\u0005\u0003SR\u0013qAT8uQ&tw\r\u0005\u0002TW&\u0011A\u000e\u0016\u0002\u0004\u0003:L\u0018AB5oY\u0016$8/F\u0001p!\r\u0001Xo^\u0007\u0002c*\u0011!o]\u0001\nS6lW\u000f^1cY\u0016T!\u0001\u001e+\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002wc\n\u00191+Z91\u0005ad\bcA(zw&\u0011!0\u0013\u0002\u0006\u0013:dW\r\u001e\t\u0003Gr$\u0011\"`\u0003\u0002\u0002\u0003\u0005)\u0011\u00014\u0003\u0007}#\u0013'\u0001\u0003oC6,WCAA\u0001!\u0011\t\u0019!!\u0005\u000f\t\u0005\u0015\u0011Q\u0002\t\u0004\u0003\u000f!VBAA\u0005\u0015\r\tY!T\u0001\u0007yI|w\u000e\u001e \n\u0007\u0005=A+\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003'\t)B\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003\u001f!\u0016fA\u0002\b?\t!a*Y7f+\u0011\ti\"!\n\u0014\u0011\u001d\u0011\u0016qDA\u0014\u0003[\u0001R!!\t\u0004\u0003Gi\u0011!\u0001\t\u0004G\u0006\u0015B!B3\b\u0005\u00041\u0007cA*\u0002*%\u0019\u00111\u0006+\u0003\u000fA\u0013x\u000eZ;diB!\u0011qFA\u001d\u001d\u0011\t\t$!\u000e\u000f\t\u0005\u001d\u00111G\u0005\u0002+&\u0019\u0011q\u0007+\u0002\u000fA\f7m[1hK&!\u00111HA\u001f\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\r\t9\u0004V\u0001\u0006]\u0006lW\r\t\u000b\u0005\u0003\u0007\n)\u0005E\u0003\u0002\"\u001d\t\u0019\u0003\u0003\u0004\u007f\u0015\u0001\u0007\u0011\u0011A\u000b\u0003\u0003\u0013\u0002Ba\u00141\u0002$U\u0011\u0011Q\n\t\u0005aV\fy\u0005\r\u0003\u0002R\u0005U\u0003\u0003B(z\u0003'\u00022aYA+\t)\t9\u0006DA\u0001\u0002\u0003\u0015\tA\u001a\u0002\u0004?\u0012\u0012\u0014\u0001B2paf,B!!\u0018\u0002dQ!\u0011qLA3!\u0015\t\tcBA1!\r\u0019\u00171\r\u0003\u0006K6\u0011\rA\u001a\u0005\t}6\u0001\n\u00111\u0001\u0002\u0002\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT\u0003BA6\u0003\u0003+\"!!\u001c+\t\u0005\u0005\u0011qN\u0016\u0003\u0003c\u0002B!a\u001d\u0002~5\u0011\u0011Q\u000f\u0006\u0005\u0003o\nI(A\u0005v]\u000eDWmY6fI*\u0019\u00111\u0010+\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002\u0000\u0005U$!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0012)QM\u0004b\u0001M\u0006i\u0001O]8ek\u000e$\bK]3gSb,\"!a\"\u0011\t\u0005%\u00151S\u0007\u0003\u0003\u0017SA!!$\u0002\u0010\u0006!A.\u00198h\u0015\t\t\t*\u0001\u0003kCZ\f\u0017\u0002BA\n\u0003\u0017\u000bA\u0002\u001d:pIV\u001cG/\u0011:jif,\"!!'\u0011\u0007M\u000bY*C\u0002\u0002\u001eR\u00131!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$2A[AR\u0011%\t)+EA\u0001\u0002\u0004\tI*A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003W\u0003R!!,\u00020*l\u0011a]\u0005\u0004\u0003c\u001b(\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!a.\u0002>B\u00191+!/\n\u0007\u0005mFKA\u0004C_>dW-\u00198\t\u0011\u0005\u00156#!AA\u0002)\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011qQAb\u0011%\t)\u000bFA\u0001\u0002\u0004\tI*\u0001\u0005iCND7i\u001c3f)\t\tI*\u0001\u0005u_N#(/\u001b8h)\t\t9)\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003o\u000b\t\u000e\u0003\u0005\u0002&^\t\t\u00111\u0001k\u0005\u0015\u0001vN\u001d;t+\u0011\t9.!8\u0014\u0011}\u0011\u0016\u0011\\A\u0014\u0003[\u0001R!!\t\u0004\u00037\u00042aYAo\t\u0015)wD1\u0001g+\t\t\t\u000f\u0005\u0003PA\u0006m\u0017aB8vi2,G\u000fI\u000b\u0003\u0003O\u0004B\u0001];\u0002jB\"\u00111^Ax!\u0011y\u00150!<\u0011\u0007\r\fy\u000f\u0002\u0006\u0002r\u000e\n\t\u0011!A\u0003\u0002\u0019\u00141a\u0018\u00134\u0003\u001dIg\u000e\\3ug\u0002\"b!a>\u0002z\u0006m\b#BA\u0011?\u0005m\u0007BB/%\u0001\u0004\t\t\u000f\u0003\u0004nI\u0001\u0007\u0011Q \t\u0005aV\fy\u0010\r\u0003\u0003\u0002\t\u0015\u0001\u0003B(z\u0005\u0007\u00012a\u0019B\u0003\t-\t\t0a?\u0002\u0002\u0003\u0005)\u0011\u00014\u0016\t\t%!q\u0002\u000b\u0007\u0005\u0017\u0011\tB!\u0006\u0011\u000b\u0005\u0005rD!\u0004\u0011\u0007\r\u0014y\u0001B\u0003fM\t\u0007a\r\u0003\u0005^MA\u0005\t\u0019\u0001B\n!\u0011y\u0005M!\u0004\t\u001154\u0003\u0013!a\u0001\u0003{,BA!\u0007\u0003\u001eU\u0011!1\u0004\u0016\u0005\u0003C\fy\u0007B\u0003fO\t\u0007a-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\t\t\r\"qE\u000b\u0003\u0005KQC!a:\u0002p\u0011)Q\r\u000bb\u0001MR\u0019!Na\u000b\t\u0013\u0005\u00156&!AA\u0002\u0005eE\u0003BA\\\u0005_A\u0001\"!*.\u0003\u0003\u0005\rA\u001b\u000b\u0005\u0003\u000f\u0013\u0019\u0004C\u0005\u0002&:\n\t\u00111\u0001\u0002\u001aR!\u0011q\u0017B\u001c\u0011!\t)+MA\u0001\u0002\u0004Q\u0017\u0001\u0002(b[\u0016\u00042!!\t\u001a'\u0011I\"Ka\u0010\u0011\t\t\u0005#qI\u0007\u0003\u0005\u0007RAA!\u0012\u0002\u0010\u0006\u0011\u0011n\\\u0005\u0005\u0003w\u0011\u0019\u0005\u0006\u0002\u0003<\u0005)\u0011\r\u001d9msV!!q\nB+)\u0011\u0011\tFa\u0016\u0011\u000b\u0005\u0005rAa\u0015\u0011\u0007\r\u0014)\u0006B\u0003f9\t\u0007a\r\u0003\u0004\u007f9\u0001\u0007\u0011\u0011A\u0001\bk:\f\u0007\u000f\u001d7z+\u0011\u0011iF!\u001c\u0015\t\t}#Q\r\t\u0006'\n\u0005\u0014\u0011A\u0005\u0004\u0005G\"&AB(qi&|g\u000eC\u0005\u0003hu\t\t\u00111\u0001\u0003j\u0005\u0019\u0001\u0010\n\u0019\u0011\u000b\u0005\u0005rAa\u001b\u0011\u0007\r\u0014i\u0007B\u0003f;\t\u0007a-\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0003tA!\u0011\u0011\u0012B;\u0013\u0011\u00119(a#\u0003\r=\u0013'.Z2u\u0003\u0015\u0001vN\u001d;t!\r\t\tcM\n\u0005gI\u0013y\u0004\u0006\u0002\u0003|U!!1\u0011BE)\u0019\u0011)Ia#\u0003\u0010B)\u0011\u0011E\u0010\u0003\bB\u00191M!#\u0005\u000b\u00154$\u0019\u00014\t\ru3\u0004\u0019\u0001BG!\u0011y\u0005Ma\"\t\r54\u0004\u0019\u0001BI!\u0011\u0001XOa%1\t\tU%\u0011\u0014\t\u0005\u001ff\u00149\nE\u0002d\u00053#1\"!=\u0003\u001c\u0006\u0005\t\u0011!B\u0001M\"1QN\u000ea\u0001\u0005#+BAa(\u0003.R!!\u0011\u0015B]!\u0015\u0019&\u0011\rBR!\u001d\u0019&Q\u0015BU\u0005_K1Aa*U\u0005\u0019!V\u000f\u001d7feA!q\n\u0019BV!\r\u0019'Q\u0016\u0003\u0006K^\u0012\rA\u001a\t\u0005aV\u0014\t\f\r\u0003\u00034\n]\u0006\u0003B(z\u0005k\u00032a\u0019B\\\t)\t\tpNA\u0001\u0002\u0003\u0015\tA\u001a\u0005\n\u0005O:\u0014\u0011!a\u0001\u0005w\u0003R!!\t \u0005W+BAa0\u0003PN\u0019\u0011H!1\u0011\u0007=\u0013\u0019-C\u0002\u0003F&\u0013Qa\u00155ba\u0016\fAaX8viB!q\n\u0019BfU\u0011\u0011i-a\u001c\u0011\u0007\r\u0014y\r\u0002\u0004fs\u0011\u0015\rAZ\u0001\f?J,w-[:uKJ,G\r\u0005\u0004\u00020\tU'q[\u0005\u0005\u0003c\u000bi\u0004\r\u0003\u0003Z\nu\u0007\u0003B(z\u00057\u00042a\u0019Bo\t)\u0011ynOA\u0001\u0002\u0003\u0015\tA\u001a\u0002\u0004?\u0012\"\u0014!B0oC6,G\u0003\u0003Bs\u0005O\u0014IO!>\u0011\t=K$Q\u001a\u0005\b\u0005\u000fl\u0004\u0019\u0001Be\u0011\u001d\u0011\t.\u0010a\u0001\u0005W\u0004b!a\f\u0003V\n5\b\u0007\u0002Bx\u0005g\u0004BaT=\u0003rB\u00191Ma=\u0005\u0017\t}'\u0011^A\u0001\u0002\u0003\u0015\tA\u001a\u0005\b\u0005Cl\u0004\u0019AA\u0001)\u0011\u0011)O!?\t\u000f\tmh\b1\u0001\u0003~\u0006!\u0011N\\5u!\u0015\u0011yp\u0001Bg\u001d\ty\u0005!A\u0002pkR,\"A!3\u0002\u000f=,H\u000f\\3ugV\u00111\u0011\u0002\t\u0005aV\u0014I-\u0006\u0002\u0004\u000eA!\u0001/^B\ba\u0011\u0019\tb!\u0006\u0011\t=K81\u0003\t\u0004G\u000eUAACB\f\u0003\u0006\u0005\t\u0011!B\u0001M\n\u0019q\fJ\u001b\u0002\u000f}Kg\u000e\\3ugV\u00111Q\u0004\t\u0007\u0003_\u0019yba\t\n\t\r\u0005\u0012Q\b\u0002\u0007-\u0016\u001cGo\u001c:1\t\r\u00152\u0011\u0006\t\u0005\u001ff\u001c9\u0003E\u0002d\u0007S!!ba\u000bE\u0003\u0003\u0005\tQ!\u0001g\u0005\ryFEN\u0001\t?&tG.\u001a;tA\u0005Yq,\u001b8mKR\u001cx\fJ3r)\u0011\u0019\u0019d!\u000f\u0011\u0007M\u001b)$C\u0002\u00048Q\u0013A!\u00168ji\"I\u0011QU\"\u0002\u0002\u0003\u000711\b\t\u0007\u0003_\u0019yb!\u00101\t\r}21\t\t\u0005\u001ff\u001c\t\u0005E\u0002d\u0007\u0007\"1ba\u000b\u0004:\u0005\u0005\t\u0011!B\u0001M\u0006Aa.Z<J]2,G/\u0006\u0003\u0004J\r=C\u0003BB&\u0007'\u0002BaT=\u0004NA\u00191ma\u0014\u0005\r\rESI1\u0001g\u0005\u0005!\u0006B\u0002@F\u0001\u0004\t\t!A\u0005d_:\u001cHO];diR!!Q]B-\u0011\u001d\u0011YP\u0012a\u0001\u00077\u0002RAa@\u0004\u0005\u0017\f\u0001\u0002Z3fa\u000e{\u0007/\u001f\u000b\u0003\u0005K\u0004")
public abstract class FanInShape<O>
extends Shape {
    private final Outlet<O> _out;
    private final Iterator<Inlet<?>> _registered;
    private final String _name;
    private Vector<Inlet<?>> _inlets;

    public final Outlet<O> out() {
        return this._out;
    }

    public final Seq<Outlet<O>> outlets() {
        Outlet<O> outlet = this._out;
        return Nil$.MODULE$.$colon$colon(outlet);
    }

    @Override
    public Seq<Inlet<?>> inlets() {
        return this._inlets();
    }

    private Vector<Inlet<?>> _inlets() {
        return this._inlets;
    }

    private void _inlets_$eq(Vector<Inlet<?>> x$1) {
        this._inlets = x$1;
    }

    public <T> Inlet<T> newInlet(String name) {
        Inlet<?> p = this._registered.hasNext() ? this._registered.next() : Inlet$.MODULE$.apply(new StringBuilder(1).append(this._name).append(".").append(name).toString());
        this._inlets_$eq((Vector)this._inlets().$colon$plus(p));
        return p;
    }

    public abstract FanInShape<O> construct(Init<O> var1);

    @Override
    public FanInShape<O> deepCopy() {
        return this.construct(new Ports<O>(this._out.carbonCopy(), (Seq)this.inlets().map((Function1<Inlet, Inlet> & Serializable)x$1 -> x$1.carbonCopy())));
    }

    private FanInShape(Outlet<O> _out, Iterator<Inlet<?>> _registered, String _name) {
        this._out = _out;
        this._registered = _registered;
        this._name = _name;
        this._inlets = package$.MODULE$.Vector().empty();
    }

    public FanInShape(Init<O> init2) {
        this(init2.outlet(), init2.inlets().iterator(), init2.name());
    }

    public static interface Init<O> {
        public Outlet<O> outlet();

        public Seq<Inlet<?>> inlets();

        public String name();
    }

    public static final class Name<O>
    implements Init<O>,
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
        public Outlet<O> outlet() {
            return Outlet$.MODULE$.apply(new StringBuilder(4).append(this.name()).append(".out").toString());
        }

        @Override
        public Seq<Inlet<?>> inlets() {
            return Nil$.MODULE$;
        }

        public <O> Name<O> copy(String name) {
            return new Name<O>(name);
        }

        public <O> String copy$default$1() {
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

    public static final class Ports<O>
    implements Init<O>,
    Product,
    Serializable {
        private final Outlet<O> outlet;
        private final Seq<Inlet<?>> inlets;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public Outlet<O> outlet() {
            return this.outlet;
        }

        @Override
        public Seq<Inlet<?>> inlets() {
            return this.inlets;
        }

        @Override
        public String name() {
            return "FanIn";
        }

        public <O> Ports<O> copy(Outlet<O> outlet, Seq<Inlet<?>> inlets) {
            return new Ports<O>(outlet, inlets);
        }

        public <O> Outlet<O> copy$default$1() {
            return this.outlet();
        }

        public <O> Seq<Inlet<?>> copy$default$2() {
            return this.inlets();
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
                    return this.outlet();
                }
                case 1: {
                    return this.inlets();
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
                    return "outlet";
                }
                case 1: {
                    return "inlets";
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
            Outlet<O> outlet = this.outlet();
            Outlet<O> outlet2 = ports.outlet();
            if (outlet == null) {
                if (outlet2 != null) {
                    return false;
                }
            } else if (!((Object)outlet).equals(outlet2)) return false;
            Seq<Inlet<?>> seq = this.inlets();
            Seq<Inlet<?>> seq2 = ports.inlets();
            if (seq == null) {
                if (seq2 == null) return true;
                return false;
            } else {
                if (!seq.equals(seq2)) return false;
                return true;
            }
        }

        public Ports(Outlet<O> outlet, Seq<Inlet<?>> inlets) {
            this.outlet = outlet;
            this.inlets = inlets;
            Product.$init$(this);
        }
    }
}

