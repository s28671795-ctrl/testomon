/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import akka.japi.Pair;
import akka.japi.function.Function;
import akka.japi.function.Function2;
import akka.util.ConstantFun$;
import scala.Function0;
import scala.Function1;
import scala.Function3;
import scala.Option;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\t%tAB\u001a5\u0011\u00031\u0004H\u0002\u0004;i!\u0005ag\u000f\u0005\u0006\u0005\u0006!\t\u0001\u0012\u0005\u0007\u000b\u0006\u0001\u000b\u0011\u0002$\t\u000fQ\u000b!\u0019!C\u0001+\"1\u0001,\u0001Q\u0001\nYCQ\u0001Y\u0001\u0005\u0002\u0005DQ\u0001]\u0001\u0005\u0002EDQa^\u0001\u0005\u0002aDaa`\u0001\u0005\u0002\u0005\u0005\u0001bBA\u000b\u0003\u0011\u0005\u0011q\u0003\u0005\b\u0003O\tA\u0011AA\u0015\u0011\u001d\t\t%\u0001C\u0001\u0003\u0007Bq!!\u0015\u0002\t\u0003\t\u0019\u0006C\u0004\u0002j\u0005!\t!a\u001b\t\u000f\u0005}\u0014\u0001\"\u0001\u0002\u0002\"9\u00111S\u0001\u0005\u0002\u0005U\u0005bBAT\u0003\u0011\u0005\u0011\u0011\u0016\u0005\b\u0003s\u000bA\u0011AA^\u0011%\t)-\u0001b\u0001\n\u0003\t9\r\u0003\u0005\u0002R\u0006\u0001\u000b\u0011BAe\u0011%\t\u0019.\u0001b\u0001\n\u0003\t9\r\u0003\u0005\u0002V\u0006\u0001\u000b\u0011BAe\u0011%\t9.\u0001b\u0001\n\u0003\tI\u000e\u0003\u0005\u0002d\u0006\u0001\u000b\u0011BAn\u0011%\t)/\u0001b\u0001\n\u0003\t9\u000f\u0003\u0005\u0002p\u0006\u0001\u000b\u0011BAu\u0011%\t\t0\u0001b\u0001\n\u0003\t\u0019\u0010\u0003\u0005\u0002x\u0006\u0001\u000b\u0011BA{\u0011%\tI0\u0001b\u0001\n\u0003\t\u0019\u0010\u0003\u0005\u0002|\u0006\u0001\u000b\u0011BA{\u0011%\ti0\u0001b\u0001\n\u0013\ty\u0010\u0003\u0005\u0003\n\u0005\u0001\u000b\u0011\u0002B\u0001\u0011%\u0011Y!\u0001b\u0001\n\u0013\u0011i\u0001\u0003\u0005\u0003\u0012\u0005\u0001\u000b\u0011\u0002B\b\u0011%\u0011\u0019\"\u0001b\u0001\n\u0013\u0011)\u0002\u0003\u0005\u0003\u001a\u0005\u0001\u000b\u0011\u0002B\f\u0011%\u0011Y\"\u0001b\u0001\n\u0013\u0011i\u0002\u0003\u0005\u0003(\u0005\u0001\u000b\u0011\u0002B\u0010\u0011%\u0011I#\u0001b\u0001\n\u0013\u0011Y\u0003\u0003\u0005\u00030\u0005\u0001\u000b\u0011\u0002B\u0017\u0011%\u0011\t$\u0001b\u0001\n\u0013\u0011\u0019\u0004\u0003\u0005\u00038\u0005\u0001\u000b\u0011\u0002B\u001b\u0011%\u0011I$\u0001b\u0001\n\u0013\u0011Y\u0004\u0003\u0005\u0003@\u0005\u0001\u000b\u0011\u0002B\u001f\u0011%\u0011\t%\u0001b\u0001\n\u0013\u0011\u0019\u0005\u0003\u0005\u0003H\u0005\u0001\u000b\u0011\u0002B#\u0011%\u0011I%\u0001b\u0001\n\u0013\u0011Y\u0005\u0003\u0005\u0003P\u0005\u0001\u000b\u0011\u0002B'\u0011%\u0011\t&\u0001b\u0001\n\u0013\u0011\u0019\u0006\u0003\u0005\u0003X\u0005\u0001\u000b\u0011\u0002B+\u0003-\u0019uN\\:uC:$h)\u001e8\u000b\u0005U2\u0014\u0001B;uS2T\u0011aN\u0001\u0005C.\\\u0017\r\u0005\u0002:\u00035\tAGA\u0006D_:\u001cH/\u00198u\rVt7CA\u0001=!\ti\u0004)D\u0001?\u0015\u0005y\u0014!B:dC2\f\u0017BA!?\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\u001a\u0001\u0001F\u00019\u0003QQ\u0015M^1JI\u0016tG/\u001b;z\rVt7\r^5p]J\u0019q\tP%\u0007\t!\u001b\u0001A\u0012\u0002\ryI,g-\u001b8f[\u0016tGO\u0010\t\u0005\u0015>\u000b\u0016+D\u0001L\u0015\taU*\u0001\u0005gk:\u001cG/[8o\u0015\tqe'\u0001\u0003kCBL\u0017B\u0001)L\u0005!1UO\\2uS>t\u0007CA\u001fS\u0013\t\u0019fHA\u0002B]f\f\u0001CS1wCB\u000b\u0017N\u001d$v]\u000e$\u0018n\u001c8\u0016\u0003Y\u00132a\u0016\u001fZ\r\u0011AU\u0001\u0001,\u0002#)\u000bg/\u0019)bSJ4UO\\2uS>t\u0007\u0005E\u0003K5rbD,\u0003\u0002\\\u0017\nIa)\u001e8di&|gN\r\t\u0005;zcD(D\u0001N\u0013\tyVJ\u0001\u0003QC&\u0014\u0018A\u00066bm\u0006\u001c%/Z1uKB\u000b\u0017N\u001d$v]\u000e$\u0018n\u001c8\u0016\u0007\t4W.F\u0001d!\u0015Q%\f\u001a7p!\t)g\r\u0004\u0001\u0005\u000b\u001d4!\u0019\u00015\u0003\u0003\u0005\u000b\"![)\u0011\u0005uR\u0017BA6?\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!Z7\u0005\u000b94!\u0019\u00015\u0003\u0003\t\u0003B!\u00180eY\u0006!\".\u0019<b\u0013\u0012,g\u000e^5us\u001a+hn\u0019;j_:,\"A];\u0016\u0003M\u0004BAS(uiB\u0011Q-\u001e\u0003\u0006m\u001e\u0011\r\u0001\u001b\u0002\u0002)\u0006)2oY1mC&#WM\u001c;jif4UO\\2uS>tWCA=\u007f+\u0005Q\b\u0003B\u001f|{vL!\u0001  \u0003\u0013\u0019+hn\u0019;j_:\f\u0004CA3\u007f\t\u00151\bB1\u0001i\u00039\u00198-\u00197b\u0003:LHk\u001c(p]\u0016,b!a\u0001\u0002\n\u0005MQCAA\u0003!\u0019i40a\u0002\u0002\fA\u0019Q-!\u0003\u0005\u000b\u001dL!\u0019\u00015\u0011\u000bu\ni!!\u0005\n\u0007\u0005=aH\u0001\u0004PaRLwN\u001c\t\u0004K\u0006MA!\u00028\n\u0005\u0004A\u0017AD:dC2\f\u0017I\\=U_Vs\u0017\u000e^\u000b\u0005\u00033\ty\"\u0006\u0002\u0002\u001cA1Qh_A\u000f\u0003C\u00012!ZA\u0010\t\u00159'B1\u0001i!\ri\u00141E\u0005\u0004\u0003Kq$\u0001B+oSR\f\u0011c]2bY\u0006\fe.\u001f+x_R{gj\u001c8f+!\tY#a\r\u00028\u0005uRCAA\u0017!%i\u0014qFA\u0019\u0003k\tI$\u0003\u0002\\}A\u0019Q-a\r\u0005\u000b\u001d\\!\u0019\u00015\u0011\u0007\u0015\f9\u0004B\u0003o\u0017\t\u0007\u0001\u000eE\u0003>\u0003\u001b\tY\u0004E\u0002f\u0003{!a!a\u0010\f\u0005\u0004A'!A\"\u0002#M\u001c\u0017\r\\1B]f$vo\u001c+p+:LG/\u0006\u0004\u0002F\u0005-\u0013qJ\u000b\u0003\u0003\u000f\u0002\u0012\"PA\u0018\u0003\u0013\ni%!\t\u0011\u0007\u0015\fY\u0005B\u0003h\u0019\t\u0007\u0001\u000eE\u0002f\u0003\u001f\"QA\u001c\u0007C\u0002!\f1c]2bY\u0006\fe.\u001f+ie\u0016,Gk\\+oSR,\u0002\"!\u0016\u0002`\u0005\r\u0014qM\u000b\u0003\u0003/\u00022\"PA-\u0003;\n\t'!\u001a\u0002\"%\u0019\u00111\f \u0003\u0013\u0019+hn\u0019;j_:\u001c\u0004cA3\u0002`\u0011)q-\u0004b\u0001QB\u0019Q-a\u0019\u0005\u000b9l!\u0019\u00015\u0011\u0007\u0015\f9\u0007\u0002\u0004\u0002@5\u0011\r\u0001[\u0001\u0012g\u000e\fG.Y!osR;x\u000eV8UeV,WCBA7\u0003g\n9(\u0006\u0002\u0002pAIQ(a\f\u0002r\u0005U\u0014\u0011\u0010\t\u0004K\u0006MD!B4\u000f\u0005\u0004A\u0007cA3\u0002x\u0011)aN\u0004b\u0001QB\u0019Q(a\u001f\n\u0007\u0005udHA\u0004C_>dW-\u00198\u0002)M\u001c\u0017\r\\1B]f$\u0006N]3f)>4\u0015\r\\:f+!\t\u0019)!#\u0002\u000e\u0006EUCAAC!-i\u0014\u0011LAD\u0003\u0017\u000by)!\u001f\u0011\u0007\u0015\fI\tB\u0003h\u001f\t\u0007\u0001\u000eE\u0002f\u0003\u001b#QA\\\bC\u0002!\u00042!ZAI\t\u0019\tyd\u0004b\u0001Q\u0006!2oY1mC\u0006s\u0017\u0010\u00165sK\u0016$v\u000e\u00165je\u0012,\u0002\"a&\u0002\u001e\u0006\u0005\u0016QU\u000b\u0003\u00033\u00032\"PA-\u00037\u000by*a)\u0002$B\u0019Q-!(\u0005\u000b\u001d\u0004\"\u0019\u00015\u0011\u0007\u0015\f\t\u000bB\u0003o!\t\u0007\u0001\u000eE\u0002f\u0003K#a!a\u0010\u0011\u0005\u0004A\u0017!\u00046bm\u0006\fe.\u001f+p\u001d>tW-\u0006\u0004\u0002,\u0006E\u0016qW\u000b\u0003\u0003[\u0003b!P>\u00020\u0006M\u0006cA3\u00022\u0012)q-\u0005b\u0001QB)Q(!\u0004\u00026B\u0019Q-a.\u0005\u000b9\f\"\u0019\u00015\u0002\u000f9,H\u000e\u001c$v]V!\u0011QXAb+\t\ty\fE\u0003>wF\u000b\t\rE\u0002f\u0003\u0007$QA\u001e\nC\u0002!\f\u0001B_3s_2{gnZ\u000b\u0003\u0003\u0013\u0004R!P>R\u0003\u0017\u00042!PAg\u0013\r\tyM\u0010\u0002\u0005\u0019>tw-A\u0005{KJ|Gj\u001c8hA\u00059qN\\3M_:<\u0017\u0001C8oK2{gn\u001a\u0011\u0002\r=tW-\u00138u+\t\tY\u000eE\u0003>wF\u000bi\u000eE\u0002>\u0003?L1!!9?\u0005\rIe\u000e^\u0001\b_:,\u0017J\u001c;!\u0003))h.\u001b;U_Vs\u0017\u000e^\u000b\u0003\u0003S\u0004R!PAv\u0003CI1!!<?\u0005%1UO\\2uS>t\u0007'A\u0006v]&$Hk\\+oSR\u0004\u0013!C1osR{GK];f+\t\t)\u0010E\u0003>wF\u000bI(\u0001\u0006b]f$v\u000e\u0016:vK\u0002\n!\"\u00198z)>4\u0015\r\\:f\u0003-\tg.\u001f+p\r\u0006d7/\u001a\u0011\u0002\u0011}sW\u000f\u001c7Gk:,\"A!\u0001\u0011\u000buZ\u0018Ka\u0001\u0011\u0007u\u0012)!C\u0002\u0003\by\u0012AAT;mY\u0006IqL\\;mY\u001a+h\u000eI\u0001\tG>tgm\u001c:ngV\u0011!q\u0002\t\u0005{m\f\u0016+A\u0005d_:4wN]7tA\u0005!QO\\5u+\t\u00119\u0002E\u0003>wF\u000b\t#A\u0003v]&$\b%\u0001\u0003o_:,WC\u0001B\u0010!\u0015i40\u0015B\u0011\u001d\ri$1E\u0005\u0004\u0005Kq\u0014\u0001\u0002(p]\u0016\fQA\\8oK\u0002\n\u0001\u0002^<pe9|g.Z\u000b\u0003\u0005[\u0001r!PA\u0018#F\u0013\t#A\u0005uo>\u0014dn\u001c8fA\u0005AAo^83iJ,X-\u0006\u0002\u00036A9Q(a\fR#\u0006e\u0014!\u0003;x_J\"(/^3!\u0003!!xo\u001c\u001av]&$XC\u0001B\u001f!\u001di\u0014qF)R\u0003C\t\u0011\u0002^<peUt\u0017\u000e\u001e\u0011\u0002\u0015QD'/Z33k:LG/\u0006\u0002\u0003FAAQ(!\u0017R#F\u000b\t#A\u0006uQJ,WMM;oSR\u0004\u0013a\u0003;ie\u0016,'GZ1mg\u0016,\"A!\u0014\u0011\u0011u\nI&U)R\u0003s\nA\u0002\u001e5sK\u0016\u0014d-\u00197tK\u0002\n1\u0002\u001e5sK\u0016\u0014D\u000f[5sIV\u0011!Q\u000b\t\b{\u0005e\u0013+U)R\u00031!\bN]3feQD\u0017N\u001d3!Q\r\t!1\f\t\u0005\u0005;\u0012\u0019'\u0004\u0002\u0003`)\u0019!\u0011\r\u001c\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0003f\t}#aC%oi\u0016\u0014h.\u00197Ba&D3\u0001\u0001B.\u0001")
@InternalApi
public final class ConstantFun {
    public static Function1<Object, Object> anyToFalse() {
        return ConstantFun$.MODULE$.anyToFalse();
    }

    public static Function1<Object, Object> anyToTrue() {
        return ConstantFun$.MODULE$.anyToTrue();
    }

    public static Function0<BoxedUnit> unitToUnit() {
        return ConstantFun$.MODULE$.unitToUnit();
    }

    public static Function1<Object, Object> oneInt() {
        return ConstantFun$.MODULE$.oneInt();
    }

    public static Function1<Object, Object> oneLong() {
        return ConstantFun$.MODULE$.oneLong();
    }

    public static Function1<Object, Object> zeroLong() {
        return ConstantFun$.MODULE$.zeroLong();
    }

    public static <T> Function1<Object, T> nullFun() {
        return ConstantFun$.MODULE$.nullFun();
    }

    public static <A, B> Function1<A, Option<B>> javaAnyToNone() {
        return ConstantFun$.MODULE$.javaAnyToNone();
    }

    public static <A, B, C> Function3<A, B, C, C> scalaAnyThreeToThird() {
        return ConstantFun$.MODULE$.scalaAnyThreeToThird();
    }

    public static <A, B, C> Function3<A, B, C, Object> scalaAnyThreeToFalse() {
        return ConstantFun$.MODULE$.scalaAnyThreeToFalse();
    }

    public static <A, B> scala.Function2<A, B, Object> scalaAnyTwoToTrue() {
        return ConstantFun$.MODULE$.scalaAnyTwoToTrue();
    }

    public static <A, B, C> Function3<A, B, C, BoxedUnit> scalaAnyThreeToUnit() {
        return ConstantFun$.MODULE$.scalaAnyThreeToUnit();
    }

    public static <A, B> scala.Function2<A, B, BoxedUnit> scalaAnyTwoToUnit() {
        return ConstantFun$.MODULE$.scalaAnyTwoToUnit();
    }

    public static <A, B, C> scala.Function2<A, B, Option<C>> scalaAnyTwoToNone() {
        return ConstantFun$.MODULE$.scalaAnyTwoToNone();
    }

    public static <A> Function1<A, BoxedUnit> scalaAnyToUnit() {
        return ConstantFun$.MODULE$.scalaAnyToUnit();
    }

    public static <A, B> Function1<A, Option<B>> scalaAnyToNone() {
        return ConstantFun$.MODULE$.scalaAnyToNone();
    }

    public static <T> Function1<T, T> scalaIdentityFunction() {
        return ConstantFun$.MODULE$.scalaIdentityFunction();
    }

    public static <T> Function<T, T> javaIdentityFunction() {
        return ConstantFun$.MODULE$.javaIdentityFunction();
    }

    public static <A, B> Function2<A, B, Pair<A, B>> javaCreatePairFunction() {
        return ConstantFun$.MODULE$.javaCreatePairFunction();
    }

    public static Function2<Object, Object, Pair<Object, Object>> JavaPairFunction() {
        return ConstantFun$.MODULE$.JavaPairFunction();
    }
}

