/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.WildcardTree$;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.collection.immutable.HashMap;
import scala.collection.immutable.Map;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t%tAB\u0015+\u0011\u0003acF\u0002\u00041U!\u0005A&\r\u0005\u0006\u0001\u0006!\tA\u0011\u0005\b\u0007\u0006\u0011\r\u0011\"\u0003E\u0011\u001d\t\t-\u0001Q\u0001\n\u0015Cq!a1\u0002\t\u0003\t)\rC\u0005\u0002D\u0006\t\t\u0011\"!\u0002P\"I\u00111]\u0001\u0012\u0002\u0013\u0005\u0011Q\u001d\u0005\n\u0003g\f\u0011\u0013!C\u0001\u0003kD\u0011B!\u0004\u0002#\u0003%\tAa\u0004\t\u0013\tu\u0011!!A\u0005\u0002\n}\u0001\"\u0003B\u001d\u0003E\u0005I\u0011\u0001B\u001e\u0011%\u0011y$AI\u0001\n\u0003\u0011\t\u0005C\u0005\u0003P\u0005\t\n\u0011\"\u0001\u0003R!I!qL\u0001\u0002\u0002\u0013%!\u0011\r\u0004\u0006a)\u0012EF\u0012\u0005\t->\u0011)\u001a!C\u0001/\"Aam\u0004B\tB\u0003%\u0001\f\u0003\u0005h\u001f\tU\r\u0011\"\u0001i\u0011!)xB!E!\u0002\u0013I\u0007\u0002\u0003<\u0010\u0005+\u0007I\u0011\u00015\t\u0011]|!\u0011#Q\u0001\n%DQ\u0001Q\b\u0005\u0002aDQ\u0001`\b\u0005\u0002uDq!a\u0001\u0010\t\u0003\t)\u0001C\u0004\u0002\u0016=!\t!a\u0006\t\u000f\u0005%r\u0002\"\u0001\u0002,!I\u0011QG\b\u0012\u0002\u0013\u0005\u0011q\u0007\u0005\n\u0003\u0013z\u0011\u0011!C\u0001\u0003\u0017B\u0011\"a\u0018\u0010#\u0003%\t!!\u0019\t\u0013\u0005%t\"%A\u0005\u0002\u0005-\u0004\"CA:\u001fE\u0005I\u0011AA;\u0011%\tIhDA\u0001\n\u0003\nY\bC\u0005\u0002\b>\t\t\u0011\"\u0001\u0002\n\"I\u0011\u0011S\b\u0002\u0002\u0013\u0005\u00111\u0013\u0005\n\u00033{\u0011\u0011!C!\u00037C\u0011\"a*\u0010\u0003\u0003%\t!!+\t\u0013\u00055v\"!A\u0005B\u0005=\u0006\"CAZ\u001f\u0005\u0005I\u0011IA[\u0011%\t9lDA\u0001\n\u0003\nI\fC\u0005\u0002<>\t\t\u0011\"\u0011\u0002>\u0006aq+\u001b7eG\u0006\u0014H\r\u0016:fK*\u00111\u0006L\u0001\u0005kRLGNC\u0001.\u0003\u0011\t7n[1\u0011\u0005=\nQ\"\u0001\u0016\u0003\u0019]KG\u000eZ2be\u0012$&/Z3\u0014\u0007\u0005\u0011\u0004\b\u0005\u00024m5\tAGC\u00016\u0003\u0015\u00198-\u00197b\u0013\t9DG\u0001\u0004B]f\u0014VM\u001a\t\u0003syj\u0011A\u000f\u0006\u0003wq\n!![8\u000b\u0003u\nAA[1wC&\u0011qH\u000f\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\ta&A\u0003f[B$\u00180F\u0001F!\rys\u0002Y\u000b\u0003\u000fv\u001bBa\u0004\u001aI\u0017B\u00111'S\u0005\u0003\u0015R\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002M):\u0011QJ\u0015\b\u0003\u001dFk\u0011a\u0014\u0006\u0003!\u0006\u000ba\u0001\u0010:p_Rt\u0014\"A\u001b\n\u0005M#\u0014a\u00029bG.\fw-Z\u0005\u0003\u007fUS!a\u0015\u001b\u0002\t\u0011\fG/Y\u000b\u00021B\u00191'W.\n\u0005i#$AB(qi&|g\u000e\u0005\u0002];2\u0001A!\u00020\u0010\u0005\u0004y&!\u0001+\u0012\u0005\u0001\u001c\u0007CA\u001ab\u0013\t\u0011GGA\u0004O_RD\u0017N\\4\u0011\u0005M\"\u0017BA35\u0005\r\te._\u0001\u0006I\u0006$\u0018\rI\u0001\tG\"LG\u000e\u001a:f]V\t\u0011\u000e\u0005\u0003k]F$hBA6m!\tqE'\u0003\u0002ni\u00051\u0001K]3eK\u001aL!a\u001c9\u0003\u00075\u000b\u0007O\u0003\u0002niA\u0011!N]\u0005\u0003gB\u0014aa\u0015;sS:<\u0007cA\u0018\u00107\u0006I1\r[5mIJ,g\u000eI\u0001\u0017o&dGmY1sIN+hMZ5y\u0007\"LG\u000e\u001a:f]\u00069r/\u001b7eG\u0006\u0014HmU;gM&D8\t[5mIJ,g\u000e\t\u000b\u0005ifT8\u0010C\u0004W-A\u0005\t\u0019\u0001-\t\u000f\u001d4\u0002\u0013!a\u0001S\"9aO\u0006I\u0001\u0002\u0004I\u0017aB5t\u000b6\u0004H/_\u000b\u0002}B\u00111g`\u0005\u0004\u0003\u0003!$a\u0002\"p_2,\u0017M\\\u0001\u0007S:\u001cXM\u001d;\u0015\u000bQ\f9!!\u0005\t\u000f\u0005%\u0001\u00041\u0001\u0002\f\u0005)Q\r\\3ngB!A*!\u0004r\u0013\r\ty!\u0016\u0002\t\u0013R,'/\u0019;pe\"1\u00111\u0003\rA\u0002m\u000b\u0011\u0001Z\u0001\u0017M&tGmV5uQNKgn\u001a7f/&dGmY1sIR\u0019A/!\u0007\t\u000f\u0005%\u0011\u00041\u0001\u0002\f!\u001a\u0011$!\b\u0011\t\u0005}\u0011QE\u0007\u0003\u0003CQ1!a\t5\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003O\t\tCA\u0004uC&d'/Z2\u0002=\u0019Lg\u000eZ,ji\"$VM]7j]\u0006dGi\\;cY\u0016<\u0016\u000e\u001c3dCJ$G#\u0002;\u0002.\u0005=\u0002bBA\u00055\u0001\u0007\u00111\u0002\u0005\t\u0003cQ\u0002\u0013!a\u0001i\u0006\u0019\u0011\r\u001c;)\u0007i\ti\"\u0001\u0015gS:$w+\u001b;i)\u0016\u0014X.\u001b8bY\u0012{WO\u00197f/&dGmY1sI\u0012\"WMZ1vYR$#'\u0006\u0002\u0002:)\u001aA/a\u000f,\u0005\u0005u\u0002\u0003BA \u0003\u000bj!!!\u0011\u000b\t\u0005\r\u0013\u0011E\u0001\nk:\u001c\u0007.Z2lK\u0012LA!a\u0012\u0002B\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\t\r|\u0007/_\u000b\u0005\u0003\u001b\n\u0019\u0006\u0006\u0005\u0002P\u0005U\u0013\u0011LA/!\u0011ys\"!\u0015\u0011\u0007q\u000b\u0019\u0006B\u0003_9\t\u0007q\f\u0003\u0005W9A\u0005\t\u0019AA,!\u0011\u0019\u0014,!\u0015\t\u0011\u001dd\u0002\u0013!a\u0001\u00037\u0002RA\u001b8r\u0003\u001fB\u0001B\u001e\u000f\u0011\u0002\u0003\u0007\u00111L\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0011\t\u0019'a\u001a\u0016\u0005\u0005\u0015$f\u0001-\u0002<\u0011)a,\bb\u0001?\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012T\u0003BA7\u0003c*\"!a\u001c+\u0007%\fY\u0004B\u0003_=\t\u0007q,\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0016\t\u00055\u0014q\u000f\u0003\u0006=~\u0011\raX\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005u\u0004\u0003BA@\u0003\u000bk!!!!\u000b\u0007\u0005\rE(\u0001\u0003mC:<\u0017bA:\u0002\u0002\u0006a\u0001O]8ek\u000e$\u0018I]5usV\u0011\u00111\u0012\t\u0004g\u00055\u0015bAAHi\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u00191-!&\t\u0013\u0005]%%!AA\u0002\u0005-\u0015a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002\u001eB)\u0011qTASG6\u0011\u0011\u0011\u0015\u0006\u0004\u0003G#\u0014AC2pY2,7\r^5p]&!\u0011qBAQ\u0003!\u0019\u0017M\\#rk\u0006dGc\u0001@\u0002,\"A\u0011q\u0013\u0013\u0002\u0002\u0003\u00071-\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003BA?\u0003cC\u0011\"a&&\u0003\u0003\u0005\r!a#\u0002\u0011!\f7\u000f[\"pI\u0016$\"!a#\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!! \u0002\r\u0015\fX/\u00197t)\rq\u0018q\u0018\u0005\t\u0003/C\u0013\u0011!a\u0001G\u00061Q-\u001c9us\u0002\nQ!\u00199qYf,B!a2\u0002NR\u0011\u0011\u0011\u001a\t\u0005_=\tY\rE\u0002]\u0003\u001b$QAX\u0003C\u0002}+B!!5\u0002XRA\u00111[Am\u0003;\f\t\u000f\u0005\u00030\u001f\u0005U\u0007c\u0001/\u0002X\u0012)aL\u0002b\u0001?\"AaK\u0002I\u0001\u0002\u0004\tY\u000e\u0005\u000343\u0006U\u0007\u0002C4\u0007!\u0003\u0005\r!a8\u0011\u000b)t\u0017/a5\t\u0011Y4\u0001\u0013!a\u0001\u0003?\fq\"\u00199qYf$C-\u001a4bk2$H%M\u000b\u0005\u0003O\f\t0\u0006\u0002\u0002j*\"\u00111^A\u001e\u001d\r\u0019\u0014Q^\u0005\u0004\u0003_$\u0014\u0001\u0002(p]\u0016$QAX\u0004C\u0002}\u000bq\"\u00199qYf$C-\u001a4bk2$HEM\u000b\u0005\u0003o\u0014Y!\u0006\u0002\u0002z*\"\u00111`A\u001e!\u001d\tiPa\u0001r\u0005\u000fi!!a@\u000b\t\t\u0005\u0011\u0011U\u0001\nS6lW\u000f^1cY\u0016LAA!\u0002\u0002\u0000\n9\u0001*Y:i\u001b\u0006\u0004\b\u0003B\u0018\u0010\u0005\u0013\u00012\u0001\u0018B\u0006\t\u0015q\u0006B1\u0001`\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\u001aT\u0003\u0002B\t\u00057)\"Aa\u0005+\t\tU\u00111\b\t\b\u0003{\u0014\u0019!\u001dB\f!\u0011ysB!\u0007\u0011\u0007q\u0013Y\u0002B\u0003_\u0013\t\u0007q,A\u0004v]\u0006\u0004\b\u000f\\=\u0016\t\t\u0005\"q\u0006\u000b\u0005\u0005G\u0011)\u0004\u0005\u000343\n\u0015\u0002#C\u001a\u0003(\t-\"\u0011\u0007B\u0019\u0013\r\u0011I\u0003\u000e\u0002\u0007)V\u0004H.Z\u001a\u0011\tMJ&Q\u0006\t\u00049\n=B!\u00020\u000b\u0005\u0004y\u0006#\u00026oc\nM\u0002\u0003B\u0018\u0010\u0005[A\u0011Ba\u000e\u000b\u0003\u0003\u0005\rAa\r\u0002\u0007a$\u0003'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H%M\u000b\u0005\u0003O\u0014i\u0004B\u0003_\u0017\t\u0007q,A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HEM\u000b\u0005\u0005\u0007\u0012i%\u0006\u0002\u0003F)\"!qIA\u001e!\u001d\tiPa\u0001r\u0005\u0013\u0002BaL\b\u0003LA\u0019AL!\u0014\u0005\u000byc!\u0019A0\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00134+\u0011\u0011\u0019F!\u0018\u0016\u0005\tU#\u0006\u0002B,\u0003w\u0001r!!@\u0003\u0004E\u0014I\u0006\u0005\u00030\u001f\tm\u0003c\u0001/\u0003^\u0011)a,\u0004b\u0001?\u0006aqO]5uKJ+\u0007\u000f\\1dKR\u0011!1\r\t\u0005\u0003\u007f\u0012)'\u0003\u0003\u0003h\u0005\u0005%AB(cU\u0016\u001cG\u000f")
public final class WildcardTree<T>
implements Product,
Serializable {
    private final Option<T> data;
    private final Map<String, WildcardTree<T>> children;
    private final Map<String, WildcardTree<T>> wildcardSuffixChildren;

    public static <T> HashMap<String, WildcardTree<T>> $lessinit$greater$default$3() {
        return WildcardTree$.MODULE$.$lessinit$greater$default$3();
    }

    public static <T> HashMap<String, WildcardTree<T>> $lessinit$greater$default$2() {
        return WildcardTree$.MODULE$.$lessinit$greater$default$2();
    }

    public static <T> None$ $lessinit$greater$default$1() {
        return WildcardTree$.MODULE$.$lessinit$greater$default$1();
    }

    public static <T> Option<Tuple3<Option<T>, Map<String, WildcardTree<T>>, Map<String, WildcardTree<T>>>> unapply(WildcardTree<T> x$0) {
        return WildcardTree$.MODULE$.unapply(x$0);
    }

    public static <T> HashMap<String, WildcardTree<T>> apply$default$3() {
        return WildcardTree$.MODULE$.apply$default$3();
    }

    public static <T> HashMap<String, WildcardTree<T>> apply$default$2() {
        return WildcardTree$.MODULE$.apply$default$2();
    }

    public static <T> None$ apply$default$1() {
        return WildcardTree$.MODULE$.apply$default$1();
    }

    public static <T> WildcardTree<T> apply(Option<T> data, Map<String, WildcardTree<T>> children, Map<String, WildcardTree<T>> wildcardSuffixChildren) {
        return WildcardTree$.MODULE$.apply(data, children, wildcardSuffixChildren);
    }

    public static <T> WildcardTree<T> apply() {
        return WildcardTree$.MODULE$.apply();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Option<T> data() {
        return this.data;
    }

    public Map<String, WildcardTree<T>> children() {
        return this.children;
    }

    public Map<String, WildcardTree<T>> wildcardSuffixChildren() {
        return this.wildcardSuffixChildren;
    }

    public boolean isEmpty() {
        return this.data().isEmpty() && this.children().isEmpty();
    }

    public WildcardTree<T> insert(Iterator<String> elems, T d) {
        String e;
        if (!elems.hasNext()) {
            return this.copy(new Some<T>(d), this.copy$default$2(), this.copy$default$3());
        }
        String string = e = elems.next();
        String string2 = "**";
        if ((string == null ? string2 != null : !string.equals(string2)) && e.endsWith("**")) {
            throw new IllegalArgumentException("double wildcard can't be used as a suffix (e.g. /user/actor**), only as a full subPath element (e.g. /user/actor/**)");
        }
        String string3 = e;
        String string4 = "*";
        if (string3 == null ? string4 != null : !string3.equals(string4)) {
            String string5 = e;
            String string6 = "**";
            if ((string5 == null ? string6 != null : !string5.equals(string6)) && e.endsWith("*")) {
                Map x$1 = (Map)this.wildcardSuffixChildren().updated(StringOps$.MODULE$.stripSuffix$extension(Predef$.MODULE$.augmentString(e), "*"), ((WildcardTree)this.wildcardSuffixChildren().getOrElse(e, (Function0<WildcardTree> & Serializable)() -> WildcardTree$.MODULE$.apply())).insert(elems, d));
                Option<T> x$2 = this.copy$default$1();
                Map<String, WildcardTree<T>> x$3 = this.copy$default$2();
                return this.copy(x$2, x$3, x$1);
            }
        }
        Map x$4 = (Map)this.children().updated(e, ((WildcardTree)this.children().getOrElse(e, (Function0<WildcardTree> & Serializable)() -> WildcardTree$.MODULE$.apply())).insert(elems, d));
        Option<T> x$5 = this.copy$default$1();
        Map<String, WildcardTree<T>> x$6 = this.copy$default$3();
        return this.copy(x$5, x$4, x$6);
    }

    public WildcardTree<T> findWithSingleWildcard(Iterator<String> elems) {
        Option option;
        block5: {
            Option option2;
            block6: {
                Option option3;
                while (true) {
                    WildcardTree branch;
                    WildcardTree this_;
                    if (!elems.hasNext()) {
                        return this_;
                    }
                    String nextElement = elems.next();
                    option = this_.children().get(nextElement);
                    if (option instanceof Some) {
                        WildcardTree branch2;
                        Some some = (Some)option;
                        this_ = branch2 = (WildcardTree)some.value();
                        continue;
                    }
                    if (!None$.MODULE$.equals(option)) break block5;
                    option2 = this_.children().get("*");
                    if (option2 instanceof Some) {
                        WildcardTree branch3;
                        Some some = (Some)option2;
                        this_ = branch3 = (WildcardTree)some.value();
                        continue;
                    }
                    if (!None$.MODULE$.equals(option2)) break block6;
                    Option maybeWildcardSuffixMatchingBranch = this_.wildcardSuffixChildren().collectFirst(new Serializable(null, nextElement){
                        private static final long serialVersionUID = 0L;
                        private final String nextElement$1;

                        public final <A1 extends Tuple2<String, WildcardTree<T>>, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                            A1 A1 = x1;
                            if (A1 != null) {
                                String key = A1._1();
                                WildcardTree<T> branch = A1._2();
                                if (this.nextElement$1.startsWith(key)) {
                                    return (B1)branch;
                                }
                            }
                            return function1.apply(x1);
                        }

                        public final boolean isDefinedAt(Tuple2<String, WildcardTree<T>> x1) {
                            String key;
                            Tuple2<String, WildcardTree<T>> tuple2 = x1;
                            return tuple2 != null && this.nextElement$1.startsWith(key = tuple2._1());
                        }
                        {
                            this.nextElement$1 = nextElement$1;
                        }
                    });
                    option3 = maybeWildcardSuffixMatchingBranch;
                    if (!(option3 instanceof Some)) break;
                    Some some = (Some)option3;
                    this_ = branch = (WildcardTree)some.value();
                }
                if (None$.MODULE$.equals(option3)) {
                    return WildcardTree$.MODULE$.apply();
                }
                throw new MatchError(option3);
            }
            throw new MatchError(option2);
        }
        throw new MatchError(option);
    }

    public WildcardTree<T> findWithTerminalDoubleWildcard(Iterator<String> elems, WildcardTree<T> alt) {
        Option option;
        block4: {
            Option option2;
            WildcardTree newAlt;
            while (true) {
                WildcardTree this_;
                if (!elems.hasNext()) {
                    return this_;
                }
                newAlt = (WildcardTree)this_.children().getOrElse("**", (Function0<WildcardTree> & Serializable)() -> alt);
                option = this_.children().get(elems.next());
                if (option instanceof Some) {
                    Some some = (Some)option;
                    WildcardTree branch = (WildcardTree)some.value();
                    alt = newAlt;
                    this_ = branch;
                    continue;
                }
                if (!None$.MODULE$.equals(option)) break block4;
                option2 = this_.children().get("*");
                if (!(option2 instanceof Some)) break;
                Some some = (Some)option2;
                WildcardTree branch = (WildcardTree)some.value();
                alt = newAlt;
                this_ = branch;
            }
            if (None$.MODULE$.equals(option2)) {
                return newAlt;
            }
            throw new MatchError(option2);
        }
        throw new MatchError(option);
    }

    public WildcardTree<T> findWithTerminalDoubleWildcard$default$2() {
        return WildcardTree$.MODULE$.apply();
    }

    public <T> WildcardTree<T> copy(Option<T> data, Map<String, WildcardTree<T>> children, Map<String, WildcardTree<T>> wildcardSuffixChildren) {
        return new WildcardTree<T>(data, children, wildcardSuffixChildren);
    }

    public <T> Option<T> copy$default$1() {
        return this.data();
    }

    public <T> Map<String, WildcardTree<T>> copy$default$2() {
        return this.children();
    }

    public <T> Map<String, WildcardTree<T>> copy$default$3() {
        return this.wildcardSuffixChildren();
    }

    @Override
    public String productPrefix() {
        return "WildcardTree";
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
                return this.data();
            }
            case 1: {
                return this.children();
            }
            case 2: {
                return this.wildcardSuffixChildren();
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
        return x$1 instanceof WildcardTree;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "data";
            }
            case 1: {
                return "children";
            }
            case 2: {
                return "wildcardSuffixChildren";
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
        if (!(object instanceof WildcardTree)) return false;
        boolean bl = true;
        if (!bl) return false;
        WildcardTree wildcardTree = (WildcardTree)x$1;
        Option<T> option = this.data();
        Option<T> option2 = wildcardTree.data();
        if (option == null) {
            if (option2 != null) {
                return false;
            }
        } else if (!option.equals(option2)) return false;
        Map<String, WildcardTree<T>> map = this.children();
        Map<String, WildcardTree<T>> map2 = wildcardTree.children();
        if (map == null) {
            if (map2 != null) {
                return false;
            }
        } else if (!map.equals(map2)) return false;
        Map<String, WildcardTree<T>> map3 = this.wildcardSuffixChildren();
        Map<String, WildcardTree<T>> map4 = wildcardTree.wildcardSuffixChildren();
        if (map3 == null) {
            if (map4 == null) return true;
            return false;
        } else {
            if (!map3.equals(map4)) return false;
            return true;
        }
    }

    public WildcardTree(Option<T> data, Map<String, WildcardTree<T>> children, Map<String, WildcardTree<T>> wildcardSuffixChildren) {
        this.data = data;
        this.children = children;
        this.wildcardSuffixChildren = wildcardSuffixChildren;
        Product.$init$(this);
    }
}

