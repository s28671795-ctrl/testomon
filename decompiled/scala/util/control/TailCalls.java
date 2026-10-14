/*
 * Decompiled with CFR 0.152.
 */
package scala.util.control;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.Product;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Either;
import scala.util.Left;
import scala.util.Right;
import scala.util.control.TailCalls$;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\res!\u0002)R\u0011\u0003Af!\u0002.R\u0011\u0003Y\u0006\"\u00021\u0002\t\u0003\tg!\u00022\u0002\u0003C\u0019\u0007\"\u00021\u0004\t\u0003)\u0007\"B:\u0004\t\u000b!\bBB@\u0004\t\u000b\t\t\u0001C\u0004\u0002\u0010\r!)!!\u0005\t\u000f\u0005U2\u0001\"\u0002\u00028\u00191\u0011QH\u0001I\u0003\u007fA!\"!\u0019\n\u0005+\u0007I\u0011AA2\u0011)\t9'\u0003B\tB\u0003%\u0011Q\r\u0005\u0007A&!\t!!\u001b\t\u0013\u0005=\u0014\"!A\u0005\u0002\u0005E\u0004\"CAA\u0013E\u0005I\u0011AAB\u0011%\tI*CA\u0001\n\u0003\nY\nC\u0005\u0002.&\t\t\u0011\"\u0001\u00020\"I\u0011qW\u0005\u0002\u0002\u0013\u0005\u0011\u0011\u0018\u0005\n\u0003\u007fK\u0011\u0011!C!\u0003\u0003D\u0011\"a4\n\u0003\u0003%\t!!5\t\u0013\u0005m\u0017\"!A\u0005B\u0005u\u0007\"CAq\u0013\u0005\u0005I\u0011IAr\u0011%\t)/CA\u0001\n\u0003\n9\u000fC\u0005\u0002j&\t\t\u0011\"\u0011\u0002l\u001eI!qR\u0001\u0002\u0002#E!\u0011\u0013\u0004\n\u0003{\t\u0011\u0011!E\t\u0005'Ca\u0001Y\r\u0005\u0002\t}\u0005\"CAs3\u0005\u0005IQIAt\u0011%\u0011\t+GA\u0001\n\u0003\u0013\u0019\u000bC\u0005\u00034f\t\t\u0011\"!\u00036\"I!QZ\r\u0002\u0002\u0013%!q\u001a\u0004\u0007\u0005'\n\u0001J!\u0016\t\u0015\t}sD!f\u0001\n\u0003\u0011\t\u0007\u0003\u0006\u0003d}\u0011\t\u0012)A\u0005\u00057Ba\u0001Y\u0010\u0005\u0002\t\u0015\u0004\"CA8?\u0005\u0005I\u0011\u0001B6\u0011%\t\tiHI\u0001\n\u0003\u00119\bC\u0005\u0002\u001a~\t\t\u0011\"\u0011\u0002\u001c\"I\u0011QV\u0010\u0002\u0002\u0013\u0005\u0011q\u0016\u0005\n\u0003o{\u0012\u0011!C\u0001\u0005\u007fB\u0011\"a0 \u0003\u0003%\t%!1\t\u0013\u0005=w$!A\u0005\u0002\t\r\u0005\"CAn?\u0005\u0005I\u0011\tBD\u0011%\t\toHA\u0001\n\u0003\n\u0019\u000fC\u0005\u0002f~\t\t\u0011\"\u0011\u0002h\"I\u0011\u0011^\u0010\u0002\u0002\u0013\u0005#1R\u0004\n\u0005/\f\u0011\u0011!E\t\u000534\u0011Ba\u0015\u0002\u0003\u0003E\tBa7\t\r\u0001|C\u0011\u0001Bo\u0011%\t)oLA\u0001\n\u000b\n9\u000fC\u0005\u0003\">\n\t\u0011\"!\u0003`\"I!1W\u0018\u0002\u0002\u0013\u0005%1\u001e\u0005\n\u0005\u001b|\u0013\u0011!C\u0005\u0005\u001f4a!a<\u0002\u0011\u0006E\bBCA~k\tU\r\u0011\"\u0001\u0002~\"Q!QA\u001b\u0003\u0012\u0003\u0006I!a@\t\u0013m,$Q3A\u0005\u0002\t\u001d\u0001B\u0003B\u0006k\tE\t\u0015!\u0003\u0003\n!1\u0001-\u000eC\u0001\u0005\u001bA\u0011\"a\u001c6\u0003\u0003%\tA!\u0006\t\u0013\u0005\u0005U'%A\u0005\u0002\t5\u0002\"\u0003B\u001ckE\u0005I\u0011\u0001B\u001d\u0011%\tI*NA\u0001\n\u0003\nY\nC\u0005\u0002.V\n\t\u0011\"\u0001\u00020\"I\u0011qW\u001b\u0002\u0002\u0013\u0005!1\t\u0005\n\u0003\u007f+\u0014\u0011!C!\u0003\u0003D\u0011\"a46\u0003\u0003%\tAa\u0012\t\u0013\u0005mW'!A\u0005B\t-\u0003\"CAqk\u0005\u0005I\u0011IAr\u0011%\t)/NA\u0001\n\u0003\n9\u000fC\u0005\u0002jV\n\t\u0011\"\u0011\u0003P\u001dI!\u0011`\u0001\u0002\u0002#E!1 \u0004\n\u0003_\f\u0011\u0011!E\t\u0005{Da\u0001\u0019%\u0005\u0002\t}\b\"CAs\u0011\u0006\u0005IQIAt\u0011%\u0011\t\u000bSA\u0001\n\u0003\u001b\t\u0001C\u0005\u00034\"\u000b\t\u0011\"!\u0004\u001a!I!Q\u001a%\u0002\u0002\u0013%!q\u001a\u0005\b\u0007o\tA\u0011AB\u001d\u0011\u001d\u0019Y%\u0001C\u0001\u0007\u001b\n\u0011\u0002V1jY\u000e\u000bG\u000e\\:\u000b\u0005I\u001b\u0016aB2p]R\u0014x\u000e\u001c\u0006\u0003)V\u000bA!\u001e;jY*\ta+A\u0003tG\u0006d\u0017m\u0001\u0001\u0011\u0005e\u000bQ\"A)\u0003\u0013Q\u000b\u0017\u000e\\\"bY2\u001c8CA\u0001]!\tif,D\u0001V\u0013\tyVK\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003a\u0013q\u0001V1jYJ+7-\u0006\u0002eUN\u00111\u0001\u0018\u000b\u0002MB\u0019qm\u00015\u000e\u0003\u0005\u0001\"!\u001b6\r\u0001\u001111n\u0001CC\u00021\u0014\u0011!Q\t\u0003[B\u0004\"!\u00188\n\u0005=,&a\u0002(pi\"Lgn\u001a\t\u0003;FL!A]+\u0003\u0007\u0005s\u00170A\u0002nCB,\"!\u001e=\u0015\u0005YT\bcA4\u0004oB\u0011\u0011\u000e\u001f\u0003\u0006s\u0016\u0011\r\u0001\u001c\u0002\u0002\u0005\")10\u0002a\u0001y\u0006\ta\r\u0005\u0003^{\"<\u0018B\u0001@V\u0005%1UO\\2uS>t\u0017'A\u0004gY\u0006$X*\u00199\u0016\t\u0005\r\u0011\u0011\u0002\u000b\u0005\u0003\u000b\tY\u0001\u0005\u0003h\u0007\u0005\u001d\u0001cA5\u0002\n\u0011)\u0011P\u0002b\u0001Y\"11P\u0002a\u0001\u0003\u001b\u0001R!X?i\u0003\u000b\taA]3tk6,WCAA\n!\u001d\t)\"a\u0007\u0002\"!t1!XA\f\u0013\r\tI\"V\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\ti\"a\b\u0003\r\u0015KG\u000f[3s\u0015\r\tI\"\u0016\t\u0005;\u0006\rb-C\u0002\u0002&U\u0013\u0011BR;oGRLwN\u001c\u0019)\u0007\u001d\tI\u0003\u0005\u0003\u0002,\u0005ERBAA\u0017\u0015\r\ty#V\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA\u001a\u0003[\u0011q\u0001^1jYJ,7-\u0001\u0004sKN,H\u000e^\u000b\u0002Q\"\u001a\u0001\"!\u000b*\t\rIQg\b\u0002\u0005\u0007\u0006dG.\u0006\u0003\u0002B\u0005\u001d3cB\u0005\u0002D\u0005%\u0013q\n\t\u0005O\u000e\t)\u0005E\u0002j\u0003\u000f\"Qa[\u0005C\u00021\u00042!XA&\u0013\r\ti%\u0016\u0002\b!J|G-^2u!\u0011\t\t&!\u0018\u000f\t\u0005M\u0013q\u0003\b\u0005\u0003+\nY&\u0004\u0002\u0002X)\u0019\u0011\u0011L,\u0002\rq\u0012xn\u001c;?\u0013\u00051\u0016\u0002BA0\u0003?\u0011AbU3sS\u0006d\u0017N_1cY\u0016\fAA]3tiV\u0011\u0011Q\r\t\u0006;\u0006\r\u00121I\u0001\u0006e\u0016\u001cH\u000f\t\u000b\u0005\u0003W\ni\u0007\u0005\u0003h\u0013\u0005\u0015\u0003bBA1\u0019\u0001\u0007\u0011QM\u0001\u0005G>\u0004\u00180\u0006\u0003\u0002t\u0005eD\u0003BA;\u0003w\u0002BaZ\u0005\u0002xA\u0019\u0011.!\u001f\u0005\u000b-l!\u0019\u00017\t\u0013\u0005\u0005T\u0002%AA\u0002\u0005u\u0004#B/\u0002$\u0005}\u0004\u0003B4\u0004\u0003o\nabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0003\u0002\u0006\u0006]UCAADU\u0011\t)'!#,\u0005\u0005-\u0005\u0003BAG\u0003'k!!a$\u000b\t\u0005E\u0015QF\u0001\nk:\u001c\u0007.Z2lK\u0012LA!!&\u0002\u0010\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0005\u000b-t!\u0019\u00017\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\ti\n\u0005\u0003\u0002 \u0006%VBAAQ\u0015\u0011\t\u0019+!*\u0002\t1\fgn\u001a\u0006\u0003\u0003O\u000bAA[1wC&!\u00111VAQ\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\u0011\u0011\u0011\u0017\t\u0004;\u0006M\u0016bAA[+\n\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0019\u0001/a/\t\u0013\u0005u\u0016#!AA\u0002\u0005E\u0016a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002DB)\u0011QYAfa6\u0011\u0011q\u0019\u0006\u0004\u0003\u0013,\u0016AC2pY2,7\r^5p]&!\u0011QZAd\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005M\u0017\u0011\u001c\t\u0004;\u0006U\u0017bAAl+\n9!i\\8mK\u0006t\u0007\u0002CA_'\u0005\u0005\t\u0019\u00019\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0003;\u000by\u000eC\u0005\u0002>R\t\t\u00111\u0001\u00022\u0006A\u0001.Y:i\u0007>$W\r\u0006\u0002\u00022\u0006AAo\\*ue&tw\r\u0006\u0002\u0002\u001e\u00061Q-];bYN$B!a5\u0002n\"A\u0011QX\f\u0002\u0002\u0003\u0007\u0001O\u0001\u0003D_:$XCBAz\u0005\u0007\tIpE\u00046\u0003k\fI%a\u0014\u0011\t\u001d\u001c\u0011q\u001f\t\u0004S\u0006eH!B=6\u0005\u0004a\u0017!A1\u0016\u0005\u0005}\b\u0003B4\u0004\u0005\u0003\u00012!\u001bB\u0002\t\u0015YWG1\u0001m\u0003\t\t\u0007%\u0006\u0002\u0003\nA1Q, B\u0001\u0003k\f!A\u001a\u0011\u0015\r\t=!\u0011\u0003B\n!\u00199WG!\u0001\u0002x\"9\u00111 \u001eA\u0002\u0005}\bBB>;\u0001\u0004\u0011I!\u0006\u0004\u0003\u0018\tu!\u0011\u0005\u000b\u0007\u00053\u0011\u0019Ca\n\u0011\r\u001d,$1\u0004B\u0010!\rI'Q\u0004\u0003\u0006Wn\u0012\r\u0001\u001c\t\u0004S\n\u0005B!B=<\u0005\u0004a\u0007\"CA~wA\u0005\t\u0019\u0001B\u0013!\u001197Aa\u0007\t\u0011m\\\u0004\u0013!a\u0001\u0005S\u0001b!X?\u0003\u001c\t-\u0002\u0003B4\u0004\u0005?)bAa\f\u00034\tURC\u0001B\u0019U\u0011\ty0!#\u0005\u000b-d$\u0019\u00017\u0005\u000bed$\u0019\u00017\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU1!1\bB \u0005\u0003*\"A!\u0010+\t\t%\u0011\u0011\u0012\u0003\u0006Wv\u0012\r\u0001\u001c\u0003\u0006sv\u0012\r\u0001\u001c\u000b\u0004a\n\u0015\u0003\"CA_\u0001\u0006\u0005\t\u0019AAY)\u0011\t\u0019N!\u0013\t\u0011\u0005u&)!AA\u0002A$B!!(\u0003N!I\u0011QX\"\u0002\u0002\u0003\u0007\u0011\u0011\u0017\u000b\u0005\u0003'\u0014\t\u0006\u0003\u0005\u0002>\u001a\u000b\t\u00111\u0001q\u0005\u0011!uN\\3\u0016\t\t]#QL\n\b?\te\u0013\u0011JA(!\u001197Aa\u0017\u0011\u0007%\u0014i\u0006B\u0003l?\t\u0007A.A\u0003wC2,X-\u0006\u0002\u0003\\\u00051a/\u00197vK\u0002\"BAa\u001a\u0003jA!qm\bB.\u0011\u001d\u0011yF\ta\u0001\u00057*BA!\u001c\u0003tQ!!q\u000eB;!\u00119wD!\u001d\u0011\u0007%\u0014\u0019\bB\u0003lG\t\u0007A\u000eC\u0005\u0003`\r\u0002\n\u00111\u0001\u0003rU!!\u0011\u0010B?+\t\u0011YH\u000b\u0003\u0003\\\u0005%E!B6%\u0005\u0004aGc\u00019\u0003\u0002\"I\u0011QX\u0014\u0002\u0002\u0003\u0007\u0011\u0011\u0017\u000b\u0005\u0003'\u0014)\t\u0003\u0005\u0002>&\n\t\u00111\u0001q)\u0011\tiJ!#\t\u0013\u0005u&&!AA\u0002\u0005EF\u0003BAj\u0005\u001bC\u0001\"!0.\u0003\u0003\u0005\r\u0001]\u0001\u0005\u0007\u0006dG\u000e\u0005\u0002h3M!\u0011\u0004\u0018BK!\u0011\u00119J!(\u000e\u0005\te%\u0002\u0002BN\u0003K\u000b!![8\n\t\u0005}#\u0011\u0014\u000b\u0003\u0005#\u000bQ!\u00199qYf,BA!*\u0003,R!!q\u0015BW!\u00119\u0017B!+\u0011\u0007%\u0014Y\u000bB\u0003l9\t\u0007A\u000eC\u0004\u0002bq\u0001\rAa,\u0011\u000bu\u000b\u0019C!-\u0011\t\u001d\u001c!\u0011V\u0001\bk:\f\u0007\u000f\u001d7z+\u0011\u00119L!2\u0015\t\te&q\u0019\t\u0006;\nm&qX\u0005\u0004\u0005{+&AB(qi&|g\u000eE\u0003^\u0003G\u0011\t\r\u0005\u0003h\u0007\t\r\u0007cA5\u0003F\u0012)1.\bb\u0001Y\"I!\u0011Z\u000f\u0002\u0002\u0003\u0007!1Z\u0001\u0004q\u0012\u0002\u0004\u0003B4\n\u0005\u0007\fAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"A!5\u0011\t\u0005}%1[\u0005\u0005\u0005+\f\tK\u0001\u0004PE*,7\r^\u0001\u0005\t>tW\r\u0005\u0002h_M!q\u0006\u0018BK)\t\u0011I.\u0006\u0003\u0003b\n\u001dH\u0003\u0002Br\u0005S\u0004BaZ\u0010\u0003fB\u0019\u0011Na:\u0005\u000b-\u0014$\u0019\u00017\t\u000f\t}#\u00071\u0001\u0003fV!!Q\u001eBz)\u0011\u0011yO!>\u0011\u000bu\u0013YL!=\u0011\u0007%\u0014\u0019\u0010B\u0003lg\t\u0007A\u000eC\u0005\u0003JN\n\t\u00111\u0001\u0003xB!qm\bBy\u0003\u0011\u0019uN\u001c;\u0011\u0005\u001dD5\u0003\u0002%]\u0005+#\"Aa?\u0016\r\r\r1\u0011BB\u0007)\u0019\u0019)aa\u0004\u0004\u0014A1q-NB\u0004\u0007\u0017\u00012![B\u0005\t\u0015Y7J1\u0001m!\rI7Q\u0002\u0003\u0006s.\u0013\r\u0001\u001c\u0005\b\u0003w\\\u0005\u0019AB\t!\u001197aa\u0002\t\rm\\\u0005\u0019AB\u000b!\u0019iVpa\u0002\u0004\u0018A!qmAB\u0006+\u0019\u0019Yb!\u000b\u00042Q!1QDB\u001a!\u0015i&1XB\u0010!\u001di6\u0011EB\u0013\u0007WI1aa\tV\u0005\u0019!V\u000f\u001d7feA!qmAB\u0014!\rI7\u0011\u0006\u0003\u0006W2\u0013\r\u0001\u001c\t\u0007;v\u001c9c!\f\u0011\t\u001d\u001c1q\u0006\t\u0004S\u000eEB!B=M\u0005\u0004a\u0007\"\u0003Be\u0019\u0006\u0005\t\u0019AB\u001b!\u00199Wga\n\u00040\u0005AA/Y5mG\u0006dG.\u0006\u0003\u0004<\r\u0005C\u0003BB\u001f\u0007\u0007\u0002BaZ\u0002\u0004@A\u0019\u0011n!\u0011\u0005\u000b-t%\u0019\u00017\t\u0011\u0005\u0005d\n\"a\u0001\u0007\u000b\u0002R!XB$\u0007{I1a!\u0013V\u0005!a$-\u001f8b[\u0016t\u0014\u0001\u00023p]\u0016,Baa\u0014\u0004VQ!1\u0011KB,!\u001197aa\u0015\u0011\u0007%\u001c)\u0006B\u0003l\u001f\n\u0007A\u000eC\u0004\u00026=\u0003\raa\u0015")
public final class TailCalls {
    public static <A> TailRec<A> done(A result) {
        return TailCalls$.MODULE$.done(result);
    }

    public static <A> TailRec<A> tailcall(Function0<TailRec<A>> rest) {
        return TailCalls$.MODULE$.tailcall(rest);
    }

    public static class Call<A>
    extends TailRec<A>
    implements Product,
    Serializable {
        private final Function0<TailRec<A>> rest;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Function0<TailRec<A>> rest() {
            return this.rest;
        }

        public <A> Call<A> copy(Function0<TailRec<A>> rest) {
            return new Call<A>(rest);
        }

        public <A> Function0<TailRec<A>> copy$default$1() {
            return this.rest();
        }

        @Override
        public String productPrefix() {
            return "Call";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            switch (x$1) {
                case 0: {
                    return this.rest();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return new AbstractIterator<T>(this){
                private int c;
                private final int cmax;
                private final Product x$2;

                public boolean hasNext() {
                    return this.c < this.cmax;
                }

                public T next() {
                    Object result = this.x$2.productElement(this.c);
                    ++this.c;
                    return (T)result;
                }
                {
                    this.x$2 = x$2;
                    this.c = 0;
                    this.cmax = x$2.productArity();
                }
            };
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof Call;
        }

        @Override
        public String productElementName(int x$1) {
            switch (x$1) {
                case 0: {
                    return "rest";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return MurmurHash3$.MODULE$.productHash(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        @Override
        public boolean equals(Object x$1) {
            block3: {
                block2: {
                    if (this == x$1) break block2;
                    if (!(x$1 instanceof Call)) break block3;
                    Call call = (Call)x$1;
                    Function0<TailRec<A>> function0 = this.rest();
                    Function0<TailRec<A>> function02 = call.rest();
                    if ((function0 != null ? !function0.equals(function02) : function02 != null) || !call.canEqual(this)) break block3;
                }
                return true;
            }
            return false;
        }

        public Call(Function0<TailRec<A>> rest) {
            this.rest = rest;
        }
    }

    public static class Cont<A, B>
    extends TailRec<B>
    implements Product,
    Serializable {
        private final TailRec<A> a;
        private final Function1<A, TailRec<B>> f;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public TailRec<A> a() {
            return this.a;
        }

        public Function1<A, TailRec<B>> f() {
            return this.f;
        }

        public <A, B> Cont<A, B> copy(TailRec<A> a, Function1<A, TailRec<B>> f) {
            return new Cont<A, B>(a, f);
        }

        public <A, B> TailRec<A> copy$default$1() {
            return this.a();
        }

        public <A, B> Function1<A, TailRec<B>> copy$default$2() {
            return this.f();
        }

        @Override
        public String productPrefix() {
            return "Cont";
        }

        @Override
        public int productArity() {
            return 2;
        }

        @Override
        public Object productElement(int x$1) {
            switch (x$1) {
                case 0: {
                    return this.a();
                }
                case 1: {
                    return this.f();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return new /* invalid duplicate definition of identical inner class */;
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof Cont;
        }

        @Override
        public String productElementName(int x$1) {
            switch (x$1) {
                case 0: {
                    return "a";
                }
                case 1: {
                    return "f";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return MurmurHash3$.MODULE$.productHash(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        @Override
        public boolean equals(Object x$1) {
            block3: {
                block2: {
                    if (this == x$1) break block2;
                    if (!(x$1 instanceof Cont)) break block3;
                    Cont cont = (Cont)x$1;
                    TailRec<A> tailRec = this.a();
                    TailRec<A> tailRec2 = cont.a();
                    if (tailRec != null ? !tailRec.equals(tailRec2) : tailRec2 != null) break block3;
                    Function1<A, TailRec<B>> function1 = this.f();
                    Function1<A, TailRec<B>> function12 = cont.f();
                    if ((function1 != null ? !function1.equals(function12) : function12 != null) || !cont.canEqual(this)) break block3;
                }
                return true;
            }
            return false;
        }

        public Cont(TailRec<A> a, Function1<A, TailRec<B>> f) {
            this.a = a;
            this.f = f;
        }
    }

    public static class Done<A>
    extends TailRec<A>
    implements Product,
    Serializable {
        private final A value;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public A value() {
            return this.value;
        }

        public <A> Done<A> copy(A value) {
            return new Done<A>(value);
        }

        public <A> A copy$default$1() {
            return this.value();
        }

        @Override
        public String productPrefix() {
            return "Done";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            switch (x$1) {
                case 0: {
                    return this.value();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return new /* invalid duplicate definition of identical inner class */;
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof Done;
        }

        @Override
        public String productElementName(int x$1) {
            switch (x$1) {
                case 0: {
                    return "value";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return MurmurHash3$.MODULE$.productHash(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        @Override
        public boolean equals(Object x$1) {
            block3: {
                block2: {
                    if (this == x$1) break block2;
                    if (!(x$1 instanceof Done)) break block3;
                    Done done = (Done)x$1;
                    if (!BoxesRunTime.equals(this.value(), done.value()) || !done.canEqual(this)) break block3;
                }
                return true;
            }
            return false;
        }

        public Done(A value) {
            this.value = value;
        }
    }

    public static abstract class TailRec<A> {
        public final <B> TailRec<B> map(Function1<A, B> f) {
            Function1<Object, Call> & Serializable flatMap_f = (Function1<Object, Call> & Serializable)a -> new Call((Function0<Done> & Serializable)() -> new Done(f.apply(a)));
            if (this instanceof Done) {
                Object flatMap_a = ((Done)this).value();
                return new Call((Function0<TailRec> & Serializable)() -> (TailRec)f.apply(flatMap_a));
            }
            if (this instanceof Call) {
                Call call = (Call)this;
                return new Cont(call, flatMap_f);
            }
            if (this instanceof Cont) {
                Cont cont = (Cont)this;
                return new Cont(cont.a(), (Function1<Object, TailRec> & Serializable)x -> {
                    TailRec tailRec = cont.f().apply(x);
                    if (tailRec == null) {
                        throw null;
                    }
                    TailRec flatMap_this = tailRec;
                    if (flatMap_this instanceof Done) {
                        Object flatMap_a = ((Done)flatMap_this).value();
                        return new Call((Function0<TailRec> & Serializable)() -> (TailRec)f.apply(flatMap_a));
                    }
                    if (flatMap_this instanceof Call) {
                        Call call = (Call)flatMap_this;
                        return new Cont(call, f);
                    }
                    if (flatMap_this instanceof Cont) {
                        Cont cont = (Cont)flatMap_this;
                        return new Cont(cont.a(), arg_0 -> TailRec.$anonfun$flatMap$2(cont, f, arg_0));
                    }
                    throw new MatchError(flatMap_this);
                });
            }
            throw new MatchError(this);
        }

        public final <B> TailRec<B> flatMap(Function1<A, TailRec<B>> f) {
            if (this instanceof Done) {
                Object a = ((Done)this).value();
                return new Call((Function0<TailRec> & Serializable)() -> (TailRec)f.apply(flatMap_a));
            }
            if (this instanceof Call) {
                Call call = (Call)this;
                return new Cont<A, B>(call, f);
            }
            if (this instanceof Cont) {
                Cont cont = (Cont)this;
                return new Cont(cont.a(), (Function1<Object, TailRec> & Serializable)x -> {
                    TailRec tailRec = cont.f().apply(x);
                    if (tailRec == null) {
                        throw null;
                    }
                    TailRec flatMap_this = tailRec;
                    if (flatMap_this instanceof Done) {
                        Object flatMap_a = ((Done)flatMap_this).value();
                        return new Call((Function0<TailRec> & Serializable)() -> (TailRec)f.apply(flatMap_a));
                    }
                    if (flatMap_this instanceof Call) {
                        Call call = (Call)flatMap_this;
                        return new Cont(call, f);
                    }
                    if (flatMap_this instanceof Cont) {
                        Cont cont = (Cont)flatMap_this;
                        return new Cont(cont.a(), arg_0 -> TailRec.$anonfun$flatMap$2(cont, f, arg_0));
                    }
                    throw new MatchError(flatMap_this);
                });
            }
            throw new MatchError(this);
        }

        /*
         * WARNING - void declaration
         */
        public final Either<Function0<TailRec<A>>, A> resume() {
            block15: {
                TailRec a;
                while (true) {
                    Cont cont;
                    Call call;
                    TailRec tailRec;
                    void flatMap_f;
                    Done done;
                    TailRec this_;
                    if (this_ instanceof Done) {
                        Object a2 = ((Done)this_).value();
                        if (package$.MODULE$.Right() == null) {
                            throw null;
                        }
                        return new Right(a2);
                    }
                    if (this_ instanceof Call) {
                        Function0 k = ((Call)this_).rest();
                        if (package$.MODULE$.Left() == null) {
                            throw null;
                        }
                        return new Left(k);
                    }
                    if (!(this_ instanceof Cont)) break block15;
                    Cont cont2 = (Cont)this_;
                    a = cont2.a();
                    Function1 f = cont2.f();
                    if (a instanceof Done) {
                        Object v = ((Done)a).value();
                        this_ = f.apply(v);
                        continue;
                    }
                    if (a instanceof Call) {
                        void apply_value;
                        Function0 k = ((Call)a).rest();
                        Function0<TailRec> & Serializable intersect = (Function0<TailRec> & Serializable)() -> {
                            TailRec tailRec = (TailRec)k.apply();
                            if (tailRec == null) {
                                throw null;
                            }
                            TailRec flatMap_this = tailRec;
                            if (flatMap_this instanceof Done) {
                                Object flatMap_a = ((Done)flatMap_this).value();
                                return new Call((Function0<TailRec> & Serializable)() -> (TailRec)f.apply(flatMap_a));
                            }
                            if (flatMap_this instanceof Call) {
                                Call call = (Call)flatMap_this;
                                return new Cont(call, f);
                            }
                            if (flatMap_this instanceof Cont) {
                                Cont cont = (Cont)flatMap_this;
                                return new Cont(cont.a(), (Function1<Object, TailRec> & Serializable)x -> {
                                    TailRec tailRec = cont.f().apply(x);
                                    if (tailRec == null) {
                                        throw null;
                                    }
                                    TailRec flatMap_this = tailRec;
                                    if (flatMap_this instanceof Done) {
                                        Object flatMap_a = ((Done)flatMap_this).value();
                                        return new Call((Function0<TailRec> & Serializable)() -> (TailRec)f.apply(flatMap_a));
                                    }
                                    if (flatMap_this instanceof Call) {
                                        Call call = (Call)flatMap_this;
                                        return new Cont(call, f);
                                    }
                                    if (flatMap_this instanceof Cont) {
                                        Cont cont = (Cont)flatMap_this;
                                        return new Cont(cont.a(), arg_0 -> TailRec.$anonfun$flatMap$2(cont, f, arg_0));
                                    }
                                    throw new MatchError(flatMap_this);
                                });
                            }
                            throw new MatchError(flatMap_this);
                        };
                        if (package$.MODULE$.Left() == null) {
                            throw null;
                        }
                        return new Left(apply_value);
                    }
                    if (!(a instanceof Cont)) break;
                    Cont cont3 = (Cont)a;
                    TailRec b = cont3.a();
                    Function1 g2 = cont3.f();
                    Function1<Object, TailRec> & Serializable intersect = (Function1<Object, TailRec> & Serializable)x2 -> {
                        TailRec tailRec = (TailRec)g2.apply(x2);
                        if (tailRec == null) {
                            throw null;
                        }
                        TailRec flatMap_this = tailRec;
                        if (flatMap_this instanceof Done) {
                            Object flatMap_a = ((Done)flatMap_this).value();
                            return new Call((Function0<TailRec> & Serializable)() -> (TailRec)f.apply(flatMap_a));
                        }
                        if (flatMap_this instanceof Call) {
                            Call call = (Call)flatMap_this;
                            return new Cont(call, f);
                        }
                        if (flatMap_this instanceof Cont) {
                            Cont cont = (Cont)flatMap_this;
                            return new Cont(cont.a(), (Function1<Object, TailRec> & Serializable)x -> {
                                TailRec tailRec = cont.f().apply(x);
                                if (tailRec == null) {
                                    throw null;
                                }
                                TailRec flatMap_this = tailRec;
                                if (flatMap_this instanceof Done) {
                                    Object flatMap_a = ((Done)flatMap_this).value();
                                    return new Call((Function0<TailRec> & Serializable)() -> (TailRec)f.apply(flatMap_a));
                                }
                                if (flatMap_this instanceof Call) {
                                    Call call = (Call)flatMap_this;
                                    return new Cont(call, f);
                                }
                                if (flatMap_this instanceof Cont) {
                                    Cont cont = (Cont)flatMap_this;
                                    return new Cont(cont.a(), arg_0 -> TailRec.$anonfun$flatMap$2(cont, f, arg_0));
                                }
                                throw new MatchError(flatMap_this);
                            });
                        }
                        throw new MatchError(flatMap_this);
                    };
                    if (b == null) {
                        throw null;
                    }
                    if (b instanceof Done) {
                        done = (Done)b;
                        Object flatMap_a = done.value();
                        tailRec = new Call(() -> TailRec.$anonfun$flatMap$1((Function1)flatMap_f, flatMap_a));
                    } else if (b instanceof Call) {
                        call = (Call)b;
                        tailRec = new Cont(call, flatMap_f);
                    } else if (b instanceof Cont) {
                        cont = (Cont)b;
                        tailRec = new Cont(cont.a(), arg_0 -> TailRec.$anonfun$flatMap$2(cont, (Function1)flatMap_f, arg_0));
                    } else {
                        throw new MatchError(b);
                    }
                    intersect = null;
                    done = null;
                    Object var13_10 = null;
                    call = null;
                    cont = null;
                    this_ = tailRec;
                }
                throw new MatchError(a);
            }
            throw new MatchError(this_);
        }

        /*
         * WARNING - void declaration
         */
        public final A result() {
            block12: {
                TailRec a;
                while (true) {
                    Cont cont;
                    Call call;
                    TailRec tailRec;
                    void flatMap_f;
                    Done done;
                    TailRec<Object> this_;
                    if (this_ instanceof Done) {
                        return ((Done)this_).value();
                    }
                    if (this_ instanceof Call) {
                        this_ = ((Call)this_).rest().apply();
                        continue;
                    }
                    if (!(this_ instanceof Cont)) break block12;
                    Cont cont2 = (Cont)this_;
                    a = cont2.a();
                    Function1 f = cont2.f();
                    if (a instanceof Done) {
                        Object v = ((Done)a).value();
                        this_ = f.apply(v);
                        continue;
                    }
                    if (a instanceof Call) {
                        this_ = ((Call)a).rest().apply().flatMap(f);
                        continue;
                    }
                    if (!(a instanceof Cont)) break;
                    Cont cont3 = (Cont)a;
                    TailRec b = cont3.a();
                    Function1 g2 = cont3.f();
                    Function1<Object, TailRec> & Serializable intersect = (Function1<Object, TailRec> & Serializable)x2 -> {
                        TailRec tailRec = (TailRec)g2.apply(x2);
                        if (tailRec == null) {
                            throw null;
                        }
                        TailRec flatMap_this = tailRec;
                        if (flatMap_this instanceof Done) {
                            Object flatMap_a = ((Done)flatMap_this).value();
                            return new Call((Function0<TailRec> & Serializable)() -> (TailRec)f.apply(flatMap_a));
                        }
                        if (flatMap_this instanceof Call) {
                            Call call = (Call)flatMap_this;
                            return new Cont(call, f);
                        }
                        if (flatMap_this instanceof Cont) {
                            Cont cont = (Cont)flatMap_this;
                            return new Cont(cont.a(), (Function1<Object, TailRec> & Serializable)x -> {
                                TailRec tailRec = cont.f().apply(x);
                                if (tailRec == null) {
                                    throw null;
                                }
                                TailRec flatMap_this = tailRec;
                                if (flatMap_this instanceof Done) {
                                    Object flatMap_a = ((Done)flatMap_this).value();
                                    return new Call((Function0<TailRec> & Serializable)() -> (TailRec)f.apply(flatMap_a));
                                }
                                if (flatMap_this instanceof Call) {
                                    Call call = (Call)flatMap_this;
                                    return new Cont(call, f);
                                }
                                if (flatMap_this instanceof Cont) {
                                    Cont cont = (Cont)flatMap_this;
                                    return new Cont(cont.a(), arg_0 -> TailRec.$anonfun$flatMap$2(cont, f, arg_0));
                                }
                                throw new MatchError(flatMap_this);
                            });
                        }
                        throw new MatchError(flatMap_this);
                    };
                    if (b == null) {
                        throw null;
                    }
                    if (b instanceof Done) {
                        done = (Done)b;
                        Object flatMap_a = done.value();
                        tailRec = new Call(() -> TailRec.$anonfun$flatMap$1((Function1)flatMap_f, flatMap_a));
                    } else if (b instanceof Call) {
                        call = (Call)b;
                        tailRec = new Cont(call, flatMap_f);
                    } else if (b instanceof Cont) {
                        cont = (Cont)b;
                        tailRec = new Cont(cont.a(), arg_0 -> TailRec.$anonfun$flatMap$2(cont, (Function1)flatMap_f, arg_0));
                    } else {
                        throw new MatchError(b);
                    }
                    intersect = null;
                    done = null;
                    Object var10_10 = null;
                    call = null;
                    cont = null;
                    this_ = tailRec;
                }
                throw new MatchError(a);
            }
            throw new MatchError(this_);
        }
    }
}

