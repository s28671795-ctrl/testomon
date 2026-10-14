/*
 * Decompiled with CFR 0.152.
 */
package akka.japi;

import akka.japi.JavaPartialFunction$;
import akka.japi.JavaPartialFunction$NoMatch$;
import scala.Function1;
import scala.MatchError;
import scala.reflect.ScalaSignature;
import scala.runtime.AbstractPartialFunction;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dt!\u0002\f\u0018\u0011\u0003ab!\u0002\u0010\u0018\u0011\u0003y\u0002\"\u0002\u0014\u0002\t\u00039c!\u0002\u0015\u0002\u0003CI\u0003\"\u0002\u0014\u0004\t\u0003qt!B<\u0002\u0011\u0003[e!\u0002\"\u0002\u0011\u0003\u001b\u0005\"\u0002\u0014\u0007\t\u0003Q\u0005b\u0002'\u0007\u0003\u0003%\t%\u0014\u0005\b-\u001a\t\t\u0011\"\u0001X\u0011\u001dYf!!A\u0005\u0002qCqA\u0019\u0004\u0002\u0002\u0013\u00053\rC\u0004k\r\u0005\u0005I\u0011A6\t\u000fA4\u0011\u0011!C!c\"9!OBA\u0001\n\u0013\u0019\b\"\u0002=\u0002\t\u000bIh!\u0002\u0010\u0018\u0003\u0003Q\bB\u0002\u0014\u0011\t\u0003\tY\u0002C\u0004\u0002 A1\t!!\t\t\u000f\u0005m\u0002\u0003\"\u0002\u0002>!9\u0011q\u0004\t\u0005F\u0005\u0005\u0003bBA#!\u0011\u0015\u0013qI\u0001\u0014\u0015\u00064\u0018\rU1si&\fGNR;oGRLwN\u001c\u0006\u00031e\tAA[1qS*\t!$\u0001\u0003bW.\f7\u0001\u0001\t\u0003;\u0005i\u0011a\u0006\u0002\u0014\u0015\u00064\u0018\rU1si&\fGNR;oGRLwN\\\n\u0003\u0003\u0001\u0002\"!\t\u0013\u000e\u0003\tR\u0011aI\u0001\u0006g\u000e\fG.Y\u0005\u0003K\t\u0012a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001\u001d\u0005Aqu.T1uG\",\u0005pY3qi&|gnE\u0002\u0004UY\u0002\"aK\u001a\u000f\u00051\ndBA\u00171\u001b\u0005q#BA\u0018\u001c\u0003\u0019a$o\\8u}%\t1%\u0003\u00023E\u00059\u0001/Y2lC\u001e,\u0017B\u0001\u001b6\u0005A\u0011VO\u001c;j[\u0016,\u0005pY3qi&|gN\u0003\u00023EA\u0011q\u0007P\u0007\u0002q)\u0011\u0011HO\u0001\bG>tGO]8m\u0015\tY$%\u0001\u0003vi&d\u0017BA\u001f9\u00051qun\u0015;bG.$&/Y2f)\u0005y\u0004C\u0001!\u0004\u001b\u0005\t\u0011FA\u0002\u0007\u0005\u001dqu.T1uG\"\u001cBAB E\u000fB\u0011\u0011%R\u0005\u0003\r\n\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002,\u0011&\u0011\u0011*\u000e\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u000b\u0002\u0017B\u0011\u0001IB\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u00039\u0003\"a\u0014+\u000e\u0003AS!!\u0015*\u0002\t1\fgn\u001a\u0006\u0002'\u0006!!.\u0019<b\u0013\t)\u0006K\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u00021B\u0011\u0011%W\u0005\u00035\n\u00121!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$\"!\u00181\u0011\u0005\u0005r\u0016BA0#\u0005\r\te.\u001f\u0005\bC*\t\t\u00111\u0001Y\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\tA\rE\u0002fQvk\u0011A\u001a\u0006\u0003O\n\n!bY8mY\u0016\u001cG/[8o\u0013\tIgM\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dGC\u00017p!\t\tS.\u0003\u0002oE\t9!i\\8mK\u0006t\u0007bB1\r\u0003\u0003\u0005\r!X\u0001\tQ\u0006\u001c\bnQ8eKR\t\u0001,\u0001\u0007xe&$XMU3qY\u0006\u001cW\rF\u0001u!\tyU/\u0003\u0002w!\n1qJ\u00196fGR\fqAT8NCR\u001c\u0007.A\u0004o_6\u000bGo\u00195\u0015\u0003)*Ra_A\u0005\u0003/\u0019\"\u0001\u0005?\u0011\u000fu\f\t!!\u0002\u0002\u00165\taP\u0003\u0002\u0000E\u00059!/\u001e8uS6,\u0017bAA\u0002}\n9\u0012IY:ue\u0006\u001cG\u000fU1si&\fGNR;oGRLwN\u001c\t\u0005\u0003\u000f\tI\u0001\u0004\u0001\u0005\u000f\u0005-\u0001C1\u0001\u0002\u000e\t\t\u0011)E\u0002\u0002\u0010u\u00032!IA\t\u0013\r\t\u0019B\t\u0002\b\u001d>$\b.\u001b8h!\u0011\t9!a\u0006\u0005\u000f\u0005e\u0001C1\u0001\u0002\u000e\t\t!\t\u0006\u0002\u0002\u001eA1Q\u0004EA\u0003\u0003+\tQ!\u00199qYf$b!!\u0006\u0002$\u0005\u001d\u0002bBA\u0013%\u0001\u0007\u0011QA\u0001\u0002q\"1\u0011\u0011\u0006\nA\u00021\fq![:DQ\u0016\u001c7\u000eK\u0003\u0013\u0003[\tI\u0004E\u0003\"\u0003_\t\u0019$C\u0002\u00022\t\u0012a\u0001\u001e5s_^\u001c\bcA\u0016\u00026%\u0019\u0011qG\u001b\u0003\u0013\u0015C8-\u001a9uS>t7EAA\u001a\u0003-I7\u000fR3gS:,G-\u0011;\u0015\u00071\fy\u0004C\u0004\u0002&M\u0001\r!!\u0002\u0015\t\u0005U\u00111\t\u0005\b\u0003K!\u0002\u0019AA\u0003\u0003-\t\u0007\u000f\u001d7z\u001fJ,En]3\u0016\r\u0005%\u0013qKA')\u0019\tY%a\u0015\u0002^A!\u0011qAA'\t\u001d\ty%\u0006b\u0001\u0003#\u0012!AQ\u0019\u0012\u0007\u0005UQ\fC\u0004\u0002&U\u0001\r!!\u0016\u0011\t\u0005\u001d\u0011q\u000b\u0003\b\u00033*\"\u0019AA.\u0005\t\t\u0015'\u0005\u0003\u0002\u0010\u0005\u0015\u0001bBA0+\u0001\u0007\u0011\u0011M\u0001\bI\u00164\u0017-\u001e7u!\u001d\t\u00131MA+\u0003\u0017J1!!\u001a#\u0005%1UO\\2uS>t\u0017\u0007")
public abstract class JavaPartialFunction<A, B>
extends AbstractPartialFunction<A, B> {
    public static RuntimeException noMatch() {
        return JavaPartialFunction$.MODULE$.noMatch();
    }

    public abstract B apply(A var1, boolean var2) throws Exception;

    @Override
    public final boolean isDefinedAt(A x) {
        boolean bl;
        try {
            this.apply(x, true);
            bl = true;
        }
        catch (Throwable throwable) {
            Throwable throwable2 = throwable;
            if (JavaPartialFunction$NoMatch$.MODULE$.equals(throwable2)) {
                bl = false;
            }
            throw throwable;
        }
        return bl;
    }

    @Override
    public final B apply(A x) {
        B b;
        try {
            b = this.apply(x, false);
        }
        catch (Throwable throwable) {
            Throwable throwable2 = throwable;
            if (JavaPartialFunction$NoMatch$.MODULE$.equals(throwable2)) {
                throw new MatchError(x);
            }
            throw throwable;
        }
        return b;
    }

    @Override
    public final <A1 extends A, B1> B1 applyOrElse(A1 x, Function1<A1, B1> function1) {
        Object object;
        try {
            object = this.apply(x, false);
        }
        catch (Throwable throwable) {
            Throwable throwable2 = throwable;
            if (JavaPartialFunction$NoMatch$.MODULE$.equals(throwable2)) {
                object = function1.apply(x);
            }
            throw throwable;
        }
        return (B1)object;
    }

    public static abstract class NoMatchException
    extends RuntimeException
    implements NoStackTrace {
        @Override
        public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
            return super.fillInStackTrace();
        }

        @Override
        public Throwable fillInStackTrace() {
            return NoStackTrace.fillInStackTrace$(this);
        }

        public NoMatchException() {
            NoStackTrace.$init$(this);
        }
    }
}

