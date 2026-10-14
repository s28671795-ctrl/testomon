/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.$less$colon$less;
import scala.Function0;
import scala.Function1;
import scala.None$;
import scala.Option$;
import scala.PartialFunction;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterator;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.Null$;
import scala.util.Either;
import scala.util.Left;
import scala.util.Right;

@ScalaSignature(bytes="\u0006\u0005\r%s!B\u0017/\u0011\u0003\td!B\u001a/\u0011\u0003!\u0004\"\u0002!\u0002\t\u0003\t\u0005\"\u0002\"\u0002\t\u0007\u0019\u0005b\u0002Bx\u0003\u0011\u0005!\u0011\u001f\u0005\b\u0005\u007f\fA\u0011AB\u0001\u0011\u001d\u0019Y!\u0001C\u0001\u0007\u001bAqaa\t\u0002\t\u0003\u0019)\u0003C\u0005\u0004:\u0005\t\t\u0011\"\u0003\u0004<\u0019)1GLA\u00115\")\u0001)\u0003C\u0001M\")\u0001.\u0003C\u0003S\")Q.\u0003C\u0003S\")a.\u0003C#_\")1/\u0003D\u0001i\")Q/\u0003C\u0003m\"9\u00111B\u0005\u0005\u0006\u00055\u0001bBA\u0015\u0013\u0011\u0015\u00111\u0006\u0005\b\u0003\u0003JAQAA\"\u0011\u001d\tI&\u0003C\u0003\u00037Bq!a\u001b\n\t\u0003\ti\u0007C\u0004\u0002|%!)!! \t\u000f\u0005\u001d\u0015\u0002\"\u0002\u0002\n\"1\u0011qR\u0005\u0005\u0006%Dq!!%\n\t\u000b\t\u0019J\u0002\u0004\u0002\u001a&\u0001\u00111\u0014\u0005\u000b\u0003\u0003K\"\u0011!Q\u0001\n\u0005\r\u0005B\u0002!\u001a\t\u0003\ti\nC\u0004\u0002*e!\t!!)\t\u000f\u0005e\u0013\u0004\"\u0001\u00020\"9\u0011QX\r\u0005\u0002\u0005}\u0006bBAI3\u0011\u0005\u00111\u001b\u0005\b\u0003;LAQAAp\u0011\u001d\tY/\u0003C\u0003\u0003[Dq!a=\n\t\u000b\t)\u0010C\u0004\u0002>&!)!a?\t\u000f\t%\u0011\u0002\"\u0002\u0003\f!9!\u0011E\u0005\u0005\u0006\t\r\u0002b\u0002B\u001b\u0013\u0011\u0015!q\u0007\u0005\b\u0005#JAQ\u0001B*\u0011\u001d\u0011y'\u0003C\u0003\u0005cBqAa&\n\t\u0003\u0011I\nC\u0004\u0003\"&!\tAa)\t\u000f\t-\u0016\u0002\"\u0002\u0003.\"9!QY\u0005\u0005\u0006\t\u001d\u0017AB(qi&|gNC\u00010\u0003\u0015\u00198-\u00197b\u0007\u0001\u0001\"AM\u0001\u000e\u00039\u0012aa\u00149uS>t7cA\u00016qA\u0011!GN\u0005\u0003o9\u0012a!\u00118z%\u00164\u0007CA\u001d?\u001b\u0005Q$BA\u001e=\u0003\tIwNC\u0001>\u0003\u0011Q\u0017M^1\n\u0005}R$\u0001D*fe&\fG.\u001b>bE2,\u0017A\u0002\u001fj]&$h\bF\u00012\u0003=y\u0007\u000f^5p]JJE/\u001a:bE2,WC\u0001#O)\t)u\u000bE\u0002G\u00132s!AM$\n\u0005!s\u0013a\u00029bG.\fw-Z\u0005\u0003\u0015.\u0013\u0001\"\u0013;fe\u0006\u0014G.\u001a\u0006\u0003\u0011:\u0002\"!\u0014(\r\u0001\u0011)qj\u0001b\u0001!\n\t\u0011)\u0005\u0002R)B\u0011!GU\u0005\u0003':\u0012qAT8uQ&tw\r\u0005\u00023+&\u0011aK\f\u0002\u0004\u0003:L\b\"\u0002-\u0004\u0001\u0004I\u0016A\u0001=p!\r\u0011\u0014\u0002T\u000b\u00037\u0002\u001cR!C\u001b]C\u0012\u00042AR/`\u0013\tq6J\u0001\u0007Ji\u0016\u0014\u0018M\u00197f\u001f:\u001cW\r\u0005\u0002NA\u00121q*\u0003CC\u0002A\u0003\"A\r2\n\u0005\rt#a\u0002)s_\u0012,8\r\u001e\t\u0003\r\u0016L!aP&\u0015\u0003\u001d\u00042AM\u0005`\u0003\u001dI7/R7qif,\u0012A\u001b\t\u0003e-L!\u0001\u001c\u0018\u0003\u000f\t{w\u000e\\3b]\u0006I\u0011n\u001d#fM&tW\rZ\u0001\nW:|wO\\*ju\u0016,\u0012\u0001\u001d\t\u0003eEL!A\u001d\u0018\u0003\u0007%sG/A\u0002hKR,\u0012aX\u0001\nO\u0016$xJ]#mg\u0016,\"a^=\u0015\u0005ad\bCA'z\t\u0015QxB1\u0001|\u0005\u0005\u0011\u0015CA0U\u0011\u0019ix\u0002\"a\u0001}\u00069A-\u001a4bk2$\bc\u0001\u001a\u0000q&\u0019\u0011\u0011\u0001\u0018\u0003\u0011q\u0012\u0017P\\1nKzB3aDA\u0003!\r\u0011\u0014qA\u0005\u0004\u0003\u0013q#AB5oY&tW-\u0001\u0004pe:+H\u000e\\\u000b\u0005\u0003\u001f\t\u0019\u0002\u0006\u0003\u0002\u0012\u0005]\u0001cA'\u0002\u0014\u00111\u0011Q\u0003\tC\u0002m\u0014!!Q\u0019\t\u000f\u0005e\u0001\u0003q\u0001\u0002\u001c\u0005\u0011QM\u001e\t\be\u0005u\u0011\u0011EA\t\u0013\r\tyB\f\u0002\u0011I1,7o\u001d\u0013d_2|g\u000e\n7fgN\u00042AMA\u0012\u0013\r\t)C\f\u0002\u0005\u001dVdG\u000eK\u0002\u0011\u0003\u000b\t1!\\1q+\u0011\ti#a\r\u0015\t\u0005=\u0012Q\u0007\t\u0005e%\t\t\u0004E\u0002N\u0003g!QA_\tC\u0002ACq!a\u000e\u0012\u0001\u0004\tI$A\u0001g!\u0019\u0011\u00141H0\u00022%\u0019\u0011Q\b\u0018\u0003\u0013\u0019+hn\u0019;j_:\f\u0004fA\t\u0002\u0006\u0005!am\u001c7e+\u0011\t)%a\u0013\u0015\t\u0005\u001d\u0013\u0011\u000b\u000b\u0005\u0003\u0013\ni\u0005E\u0002N\u0003\u0017\"QA\u001f\nC\u0002ACq!a\u000e\u0013\u0001\u0004\ty\u0005\u0005\u00043\u0003wy\u0016\u0011\n\u0005\t\u0003'\u0012B\u00111\u0001\u0002V\u00059\u0011NZ#naRL\b\u0003\u0002\u001a\u0000\u0003\u0013B3AEA\u0003\u0003\u001d1G.\u0019;NCB,B!!\u0018\u0002dQ!\u0011qLA3!\u0011\u0011\u0014\"!\u0019\u0011\u00075\u000b\u0019\u0007B\u0003{'\t\u0007\u0001\u000bC\u0004\u00028M\u0001\r!a\u001a\u0011\rI\nYdXA0Q\r\u0019\u0012QA\u0001\bM2\fG\u000f^3o+\u0011\ty'!\u001e\u0015\t\u0005E\u0014q\u000f\t\u0005e%\t\u0019\bE\u0002N\u0003k\"QA\u001f\u000bC\u0002ACq!!\u0007\u0015\u0001\b\tI\b\u0005\u00043\u0003;y\u0016\u0011O\u0001\u0007M&dG/\u001a:\u0015\u0007\u001d\fy\bC\u0004\u0002\u0002V\u0001\r!a!\u0002\u0003A\u0004RAMA\u001e?*D3!FA\u0003\u0003%1\u0017\u000e\u001c;fe:{G\u000fF\u0002h\u0003\u0017Cq!!!\u0017\u0001\u0004\t\u0019\tK\u0002\u0017\u0003\u000b\t\u0001B\\8o\u000b6\u0004H/_\u0001\u000bo&$\bNR5mi\u0016\u0014H\u0003BAK\u00033\u00042!a&\u001a\u001b\u0005I!AC,ji\"4\u0015\u000e\u001c;feN\u0011\u0011$\u000e\u000b\u0005\u0003+\u000by\nC\u0004\u0002\u0002n\u0001\r!a!\u0016\t\u0005\r\u0016\u0011\u0016\u000b\u0005\u0003K\u000bY\u000b\u0005\u00033\u0013\u0005\u001d\u0006cA'\u0002*\u0012)!\u0010\bb\u0001!\"9\u0011q\u0007\u000fA\u0002\u00055\u0006C\u0002\u001a\u0002<}\u000b9+\u0006\u0003\u00022\u0006]F\u0003BAZ\u0003s\u0003BAM\u0005\u00026B\u0019Q*a.\u0005\u000bil\"\u0019\u0001)\t\u000f\u0005]R\u00041\u0001\u0002<B1!'a\u000f`\u0003g\u000bqAZ8sK\u0006\u001c\u0007.\u0006\u0003\u0002B\u0006=G\u0003BAb\u0003\u0013\u00042AMAc\u0013\r\t9M\f\u0002\u0005+:LG\u000fC\u0004\u00028y\u0001\r!a3\u0011\rI\nYdXAg!\ri\u0015q\u001a\u0003\u0007\u0003#t\"\u0019\u0001)\u0003\u0003U#B!!&\u0002V\"9\u0011q[\u0010A\u0002\u0005\r\u0015!A9\t\u000f\u0005\u0005\u0005\u00041\u0001\u0002\u0004\"\u001a\u0001$!\u0002\u0002\u0011\r|g\u000e^1j]N,B!!9\u0002jR\u0019!.a9\t\u000f\u0005\u0015\b\u00051\u0001\u0002h\u0006!Q\r\\3n!\ri\u0015\u0011\u001e\u0003\u0007\u0003+\u0001#\u0019A>\u0002\r\u0015D\u0018n\u001d;t)\rQ\u0017q\u001e\u0005\b\u0003\u0003\u000b\u0003\u0019AABQ\r\t\u0013QA\u0001\u0007M>\u0014\u0018\r\u001c7\u0015\u0007)\f9\u0010C\u0004\u0002\u0002\n\u0002\r!a!)\u0007\t\n)!\u0006\u0003\u0002~\n\u0015A\u0003BAb\u0003\u007fDq!a\u000e$\u0001\u0004\u0011\t\u0001\u0005\u00043\u0003wy&1\u0001\t\u0004\u001b\n\u0015AABAiG\t\u0007\u0001\u000bK\u0002$\u0003\u000b\tqaY8mY\u0016\u001cG/\u0006\u0003\u0003\u000e\tMA\u0003\u0002B\b\u0005+\u0001BAM\u0005\u0003\u0012A\u0019QJa\u0005\u0005\u000bi$#\u0019\u0001)\t\u000f\t]A\u00051\u0001\u0003\u001a\u0005\u0011\u0001O\u001a\t\u0007e\tmqL!\u0005\n\u0007\tuaFA\bQCJ$\u0018.\u00197Gk:\u001cG/[8oQ\r!\u0013QA\u0001\u0007_J,En]3\u0016\t\t\u0015\"1\u0006\u000b\u0005\u0005O\u0011i\u0003\u0005\u00033\u0013\t%\u0002cA'\u0003,\u0011)!0\nb\u0001w\"A!qF\u0013\u0005\u0002\u0004\u0011\t$A\u0006bYR,'O\\1uSZ,\u0007\u0003\u0002\u001a\u0000\u0005OA3!JA\u0003\u0003\rQ\u0018\u000e]\u000b\u0007\u0005s\u0011)E!\u0013\u0015\t\tm\"1\n\t\u0005e%\u0011i\u0004E\u00043\u0005\u007f\u0011\u0019Ea\u0012\n\u0007\t\u0005cF\u0001\u0004UkBdWM\r\t\u0004\u001b\n\u0015CABA\u000bM\t\u00071\u0010E\u0002N\u0005\u0013\"QA\u001f\u0014C\u0002ACqA!\u0014'\u0001\u0004\u0011y%\u0001\u0003uQ\u0006$\b\u0003\u0002\u001a\n\u0005\u000f\nQ!\u001e8{SB,bA!\u0016\u0003^\t\rD\u0003\u0002B,\u0005O\u0002rA\rB \u00053\u0012y\u0006\u0005\u00033\u0013\tm\u0003cA'\u0003^\u00111\u0011QC\u0014C\u0002A\u0003BAM\u0005\u0003bA\u0019QJa\u0019\u0005\r\t\u0015tE1\u0001Q\u0005\t\t%\u0007C\u0004\u0003j\u001d\u0002\u001dAa\u001b\u0002\r\u0005\u001c\b+Y5s!\u0019\u0011\u0014QD0\u0003nA9!Ga\u0010\u0003\\\t\u0005\u0014AB;ou&\u00048'\u0006\u0005\u0003t\t}$Q\u0011BF)\u0011\u0011)Ha$\u0011\u0013I\u00129Ha\u001f\u0003\u0002\n\u001d\u0015b\u0001B=]\t1A+\u001e9mKN\u0002BAM\u0005\u0003~A\u0019QJa \u0005\r\u0005U\u0001F1\u0001Q!\u0011\u0011\u0014Ba!\u0011\u00075\u0013)\t\u0002\u0004\u0003f!\u0012\r\u0001\u0015\t\u0005e%\u0011I\tE\u0002N\u0005\u0017#aA!$)\u0005\u0004\u0001&AA!4\u0011\u001d\u0011\t\n\u000ba\u0002\u0005'\u000b\u0001\"Y:Ue&\u0004H.\u001a\t\u0007e\u0005uqL!&\u0011\u0013I\u00129H! \u0003\u0004\n%\u0015\u0001C5uKJ\fGo\u001c:\u0016\u0005\tm\u0005\u0003\u0002$\u0003\u001e~K1Aa(L\u0005!IE/\u001a:bi>\u0014\u0018A\u0002;p\u0019&\u001cH/\u0006\u0002\u0003&B!aIa*`\u0013\r\u0011Ik\u0013\u0002\u0005\u0019&\u001cH/A\u0004u_JKw\r\u001b;\u0016\t\t=&\u0011\u0018\u000b\u0005\u0005c\u0013i\f\u0005\u0004G\u0005g\u00139lX\u0005\u0004\u0005k[%AB#ji\",'\u000fE\u0002N\u0005s#aAa/,\u0005\u0004\u0001&!\u0001-\t\u0011\t}6\u0006\"a\u0001\u0005\u0003\fA\u0001\\3giB!!g B\\Q\rY\u0013QA\u0001\u0007i>dUM\u001a;\u0016\t\t%'q\u001a\u000b\u0005\u0005\u0017\u0014\t\u000e\u0005\u0004G\u0005g{&Q\u001a\t\u0004\u001b\n=GA\u0002B^Y\t\u0007\u0001\u000b\u0003\u0005\u0003T2\"\t\u0019\u0001Bk\u0003\u0015\u0011\u0018n\u001a5u!\u0011\u0011tP!4)\u00071\n)!K\u0003\n\u00057\u0014yNC\u0002\u0003^:\nAAT8oK&\u0019!\u0011\u001d\u0018\u0003\tM{W.\u001a\u0015\b\u0013\t\u0015(1\u001eBw!\r\u0011$q]\u0005\u0004\u0005St#\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=!q nN\u007f\\\u001e\u0019$\u0018!B1qa2LX\u0003\u0002Bz\u0005s$BA!>\u0003|B!!'\u0003B|!\ri%\u0011 \u0003\u0006\u001f\u0012\u0011\r\u0001\u0015\u0005\b\u0005{$\u0001\u0019\u0001B|\u0003\u0005A\u0018!B3naRLX\u0003BB\u0002\u0007\u0013)\"a!\u0002\u0011\tIJ1q\u0001\t\u0004\u001b\u000e%A!B(\u0006\u0005\u0004\u0001\u0016\u0001B<iK:,Baa\u0004\u0004\u0018Q!1\u0011CB\u0010)\u0011\u0019\u0019b!\u0007\u0011\tIJ1Q\u0003\t\u0004\u001b\u000e]A!B(\u0007\u0005\u0004\u0001\u0006\u0002CB\u000e\r\u0011\u0005\ra!\b\u0002\u0003\u0005\u0004BAM@\u0004\u0016!11\u0011\u0005\u0004A\u0002)\fAaY8oI\u00061QO\u001c7fgN,Baa\n\u00040Q!1\u0011FB\u001b)\u0011\u0019Yc!\r\u0011\tIJ1Q\u0006\t\u0004\u001b\u000e=B!B(\b\u0005\u0004\u0001\u0006\u0002CB\u000e\u000f\u0011\u0005\raa\r\u0011\tIz8Q\u0006\u0005\u0007\u0007C9\u0001\u0019\u00016)\u0007\u001d\t)!\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0004>A!1qHB#\u001b\t\u0019\tEC\u0002\u0004Dq\nA\u0001\\1oO&!1qIB!\u0005\u0019y%M[3di\u0002")
public abstract class Option<A>
implements IterableOnce<A>,
Product,
Serializable {
    private static final long serialVersionUID = -114498752079829388L;

    public static <A> Option<A> unless(boolean cond, Function0<A> a) {
        if (!cond) {
            return new Some<A>(a.apply());
        }
        return None$.MODULE$;
    }

    public static <A> Option<A> when(boolean cond, Function0<A> a) {
        if (cond) {
            return new Some<A>(a.apply());
        }
        return None$.MODULE$;
    }

    public static <A> Option<A> empty() {
        return None$.MODULE$;
    }

    public static <A> Option<A> apply(A x) {
        return Option$.MODULE$.apply(x);
    }

    public static <A> Iterable<A> option2Iterable(Option<A> xo) {
        return Option$.MODULE$.option2Iterable(xo);
    }

    @Override
    public Iterator<Object> productIterator() {
        return Product.productIterator$(this);
    }

    @Override
    public String productPrefix() {
        return Product.productPrefix$(this);
    }

    @Override
    public String productElementName(int n) {
        return Product.productElementName$(this, n);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
        return (S)IterableOnce.stepper$(this, shape);
    }

    public final boolean isEmpty() {
        return this == None$.MODULE$;
    }

    public final boolean isDefined() {
        return !this.isEmpty();
    }

    @Override
    public final int knownSize() {
        if (this.isEmpty()) {
            return 0;
        }
        return 1;
    }

    public abstract A get();

    public final <B> B getOrElse(Function0<B> function0) {
        if (this.isEmpty()) {
            return function0.apply();
        }
        return (B)this.get();
    }

    public final <A1> A1 orNull($less$colon$less<Null$, A1> ev) {
        if (this.isEmpty()) {
            return ev.apply(null);
        }
        return (A1)this.get();
    }

    public final <B> Option<B> map(Function1<A, B> f) {
        if (this.isEmpty()) {
            return None$.MODULE$;
        }
        return new Some<B>(f.apply(this.get()));
    }

    public final <B> B fold(Function0<B> ifEmpty2, Function1<A, B> f) {
        if (this.isEmpty()) {
            return ifEmpty2.apply();
        }
        return f.apply(this.get());
    }

    public final <B> Option<B> flatMap(Function1<A, Option<B>> f) {
        if (this.isEmpty()) {
            return None$.MODULE$;
        }
        return f.apply(this.get());
    }

    public <B> Option<B> flatten($less$colon$less<A, Option<B>> ev) {
        if (this.isEmpty()) {
            return None$.MODULE$;
        }
        return ev.apply(this.get());
    }

    public final Option<A> filter(Function1<A, Object> p) {
        if (this.isEmpty() || BoxesRunTime.unboxToBoolean(p.apply(this.get()))) {
            return this;
        }
        return None$.MODULE$;
    }

    public final Option<A> filterNot(Function1<A, Object> p) {
        if (this.isEmpty() || !BoxesRunTime.unboxToBoolean(p.apply(this.get()))) {
            return this;
        }
        return None$.MODULE$;
    }

    public final boolean nonEmpty() {
        return this.isDefined();
    }

    public final WithFilter withFilter(Function1<A, Object> p) {
        return new WithFilter(this, p);
    }

    public final <A1> boolean contains(A1 elem) {
        return !this.isEmpty() && BoxesRunTime.equals(this.get(), elem);
    }

    public final boolean exists(Function1<A, Object> p) {
        return !this.isEmpty() && BoxesRunTime.unboxToBoolean(p.apply(this.get()));
    }

    public final boolean forall(Function1<A, Object> p) {
        return this.isEmpty() || BoxesRunTime.unboxToBoolean(p.apply(this.get()));
    }

    public final <U> void foreach(Function1<A, U> f) {
        if (!this.isEmpty()) {
            f.apply(this.get());
            return;
        }
    }

    public final <B> Option<B> collect(PartialFunction<A, B> pf) {
        if (!this.isEmpty()) {
            return pf.lift().apply(this.get());
        }
        return None$.MODULE$;
    }

    public final <B> Option<B> orElse(Function0<Option<B>> alternative) {
        if (this.isEmpty()) {
            return alternative.apply();
        }
        return this;
    }

    public final <A1, B> Option<Tuple2<A1, B>> zip(Option<B> that) {
        if (this.isEmpty() || that.isEmpty()) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<A1, B>>(new Tuple2<A, B>(this.get(), that.get()));
    }

    public final <A1, A2> Tuple2<Option<A1>, Option<A2>> unzip($less$colon$less<A, Tuple2<A1, A2>> asPair) {
        if (this.isEmpty()) {
            return new Tuple2<Option<A1>, Option<A2>>(None$.MODULE$, None$.MODULE$);
        }
        Tuple2<A1, A2> e = asPair.apply(this.get());
        return new Tuple2<Option<A1>, Option<A2>>(new Some<A1>(e._1()), new Some<A2>(e._2()));
    }

    public final <A1, A2, A3> Tuple3<Option<A1>, Option<A2>, Option<A3>> unzip3($less$colon$less<A, Tuple3<A1, A2, A3>> asTriple) {
        if (this.isEmpty()) {
            return new Tuple3<Option<A1>, Option<A2>, Option<A3>>(None$.MODULE$, None$.MODULE$, None$.MODULE$);
        }
        Tuple3<A1, A2, A3> e = asTriple.apply(this.get());
        return new Tuple3<Option<A1>, Option<A2>, Option<A3>>(new Some<A1>(e._1()), new Some<A2>(e._2()), new Some<A3>(e._3()));
    }

    @Override
    public Iterator<A> iterator() {
        if (this.isEmpty()) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        A single_a = this.get();
        return new AbstractIterator<A>(single_a){
            private boolean consumed;
            private final Object a$1;

            public boolean hasNext() {
                return !this.consumed;
            }

            public A next() {
                if (this.consumed) {
                    return (A)Iterator$.scala$collection$Iterator$$_empty.next();
                }
                this.consumed = true;
                return (A)this.a$1;
            }

            public Iterator<A> sliceIterator(int from, int until) {
                if (this.consumed || from > 0 || until == 0) {
                    return Iterator$.scala$collection$Iterator$$_empty;
                }
                return this;
            }
            {
                this.a$1 = a$1;
                this.consumed = false;
            }
        };
    }

    public List<A> toList() {
        if (this.isEmpty()) {
            return Nil$.MODULE$;
        }
        return new $colon$colon<Nothing$>((Nothing$)this.get(), Nil$.MODULE$);
    }

    /*
     * WARNING - void declaration
     */
    public final <X> Either<X, A> toRight(Function0<X> left) {
        void apply_value;
        if (this.isEmpty()) {
            void apply_value2;
            X x = left.apply();
            if (package$.MODULE$.Left() == null) {
                throw null;
            }
            return new Left(apply_value2);
        }
        A a = this.get();
        if (package$.MODULE$.Right() == null) {
            throw null;
        }
        return new Right(apply_value);
    }

    /*
     * WARNING - void declaration
     */
    public final <X> Either<A, X> toLeft(Function0<X> right) {
        void apply_value;
        if (this.isEmpty()) {
            void apply_value2;
            X x = right.apply();
            if (package$.MODULE$.Right() == null) {
                throw null;
            }
            return new Right(apply_value2);
        }
        A a = this.get();
        if (package$.MODULE$.Left() == null) {
            throw null;
        }
        return new Left(apply_value);
    }

    public static final /* synthetic */ Object $anonfun$orNull$1($less$colon$less ev$1) {
        return ev$1.apply(null);
    }

    public class WithFilter {
        private final Function1<A, Object> p;
        public final /* synthetic */ Option $outer;

        /*
         * WARNING - void declaration
         */
        public <B> Option<B> map(Function1<A, B> f) {
            void filter_p;
            Option option = this.scala$Option$WithFilter$$$outer();
            Function1 function1 = this.p;
            if (option == null) {
                throw null;
            }
            Object var2_3 = null;
            function1 = null;
            Option filter_this = option;
            Option map_this = filter_this.isEmpty() || BoxesRunTime.unboxToBoolean(filter_p.apply(filter_this.get())) ? filter_this : None$.MODULE$;
            if (map_this.isEmpty()) {
                return None$.MODULE$;
            }
            return new Some<B>(f.apply(map_this.get()));
        }

        /*
         * WARNING - void declaration
         */
        public <B> Option<B> flatMap(Function1<A, Option<B>> f) {
            void filter_p;
            Option option = this.scala$Option$WithFilter$$$outer();
            Function1 function1 = this.p;
            if (option == null) {
                throw null;
            }
            Object var2_3 = null;
            function1 = null;
            Option filter_this = option;
            Option flatMap_this = filter_this.isEmpty() || BoxesRunTime.unboxToBoolean(filter_p.apply(filter_this.get())) ? filter_this : None$.MODULE$;
            if (flatMap_this.isEmpty()) {
                return None$.MODULE$;
            }
            return f.apply(flatMap_this.get());
        }

        /*
         * WARNING - void declaration
         */
        public <U> void foreach(Function1<A, U> f) {
            void filter_p;
            Option option = this.scala$Option$WithFilter$$$outer();
            Function1 function1 = this.p;
            if (option == null) {
                throw null;
            }
            Object var2_3 = null;
            function1 = null;
            Option filter_this = option;
            Option foreach_this = filter_this.isEmpty() || BoxesRunTime.unboxToBoolean(filter_p.apply(filter_this.get())) ? filter_this : None$.MODULE$;
            if (!foreach_this.isEmpty()) {
                f.apply(foreach_this.get());
                return;
            }
        }

        public WithFilter withFilter(Function1<A, Object> q) {
            return new WithFilter(this.scala$Option$WithFilter$$$outer(), (Function1<Object, Object> & Serializable)x -> BoxesRunTime.boxToBoolean(WithFilter.$anonfun$withFilter$1(this, q, x)));
        }

        public /* synthetic */ Option scala$Option$WithFilter$$$outer() {
            return this.$outer;
        }

        public static final /* synthetic */ boolean $anonfun$withFilter$1(WithFilter $this, Function1 q$1, Object x) {
            return BoxesRunTime.unboxToBoolean($this.p.apply(x)) && BoxesRunTime.unboxToBoolean(q$1.apply(x));
        }

        public WithFilter(Option $outer, Function1<A, Object> p) {
            this.p = p;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }
}

