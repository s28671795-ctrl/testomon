/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.PrettyDuration$;
import akka.util.PrettyDuration$PrettyPrintableDuration$;
import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.Duration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005tAB\u000e\u001d\u0011\u0003q\u0002E\u0002\u0004#9!\u0005ad\t\u0005\u0006U\u0005!\t\u0001\f\u0005\u0006[\u0005!\tA\f\u0005\u0006[\u0005!\ta\u0011\u0004\u0005\u001f\u0006\u0019\u0001\u000b\u0003\u0005<\u000b\t\u0015\r\u0011\"\u0001U\u0011!)VA!A!\u0002\u0013a\u0004\"\u0002\u0016\u0006\t\u00031\u0006\"\u0002.\u0006\t\u0003Y\u0006\"\u0002.\u0006\t\u0003a\u0006bB0\u0006#\u0003%\t\u0001\u0019\u0005\u0006W\u0016!\t\u0001\u001c\u0005\u0007\u007f\u0016!\t!!\u0001\t\u0013\u0005\u001dQ!!A\u0005B\u0005%\u0001\"CA\u0006\u000b\u0005\u0005I\u0011IA\u0007\u000f%\tI\"AA\u0001\u0012\u0003\tYB\u0002\u0005P\u0003\u0005\u0005\t\u0012AA\u000f\u0011\u0019Q\u0013\u0003\"\u0001\u0002 !9\u0011\u0011E\t\u0005\u0006\u0005\r\u0002bBA\u0011#\u0011\u0015\u0011\u0011\u0006\u0005\n\u0003g\t\u0012\u0013!C\u0003\u0003kAq!!\u000f\u0012\t\u000b\tY\u0004C\u0004\u0002DE!)!!\u0012\t\u0013\u00055\u0013#!A\u0005\u0006\u0005=\u0003\"CA*#\u0005\u0005IQAA+\u0011%\tI\"AA\u0001\n\u0007\ti&\u0001\bQe\u0016$H/\u001f#ve\u0006$\u0018n\u001c8\u000b\u0005uq\u0012\u0001B;uS2T\u0011aH\u0001\u0005C.\\\u0017\r\u0005\u0002\"\u00035\tAD\u0001\bQe\u0016$H/\u001f#ve\u0006$\u0018n\u001c8\u0014\u0005\u0005!\u0003CA\u0013)\u001b\u00051#\"A\u0014\u0002\u000bM\u001c\u0017\r\\1\n\u0005%2#AB!osJ+g-\u0001\u0004=S:LGOP\u0002\u0001)\u0005\u0001\u0013A\u00024pe6\fG\u000f\u0006\u00020uA\u0011\u0001g\u000e\b\u0003cU\u0002\"A\r\u0014\u000e\u0003MR!\u0001N\u0016\u0002\rq\u0012xn\u001c;?\u0013\t1d%\u0001\u0004Qe\u0016$WMZ\u0005\u0003qe\u0012aa\u0015;sS:<'B\u0001\u001c'\u0011\u0015Y4\u00011\u0001=\u0003!!WO]1uS>t\u0007CA\u001fB\u001b\u0005q$BA\u001e@\u0015\t\u0001e%\u0001\u0006d_:\u001cWO\u001d:f]RL!A\u0011 \u0003\u0011\u0011+(/\u0019;j_:$Ba\f#F\u0015\")1\b\u0002a\u0001y!)a\t\u0002a\u0001\u000f\u0006a\u0011N\\2mk\u0012,g*\u00198pgB\u0011Q\u0005S\u0005\u0003\u0013\u001a\u0012qAQ8pY\u0016\fg\u000eC\u0003L\t\u0001\u0007A*A\u0005qe\u0016\u001c\u0017n]5p]B\u0011Q%T\u0005\u0003\u001d\u001a\u00121!\u00138u\u0005]\u0001&/\u001a;usB\u0013\u0018N\u001c;bE2,G)\u001e:bi&|gn\u0005\u0002\u0006#B\u0011QEU\u0005\u0003'\u001a\u0012a!\u00118z-\u0006dW#\u0001\u001f\u0002\u0013\u0011,(/\u0019;j_:\u0004CCA,Z!\tAV!D\u0001\u0002\u0011\u0015Y\u0004\u00021\u0001=\u0003\u0019\u0001(/\u001a;usV\tq\u0006F\u00020;zCQA\u0012\u0006A\u0002\u001dCqa\u0013\u0006\u0011\u0002\u0003\u0007A*\u0001\tqe\u0016$H/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\t\u0011M\u000b\u0002ME.\n1\r\u0005\u0002eS6\tQM\u0003\u0002gO\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003Q\u001a\n!\"\u00198o_R\fG/[8o\u0013\tQWMA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\f!b\u00195p_N,WK\\5u)\ti'\u0010\u0005\u0002oo:\u0011q.\u001e\b\u0003aRt!!]:\u000f\u0005I\u0012\u0018\"A\u0014\n\u0005\u00013\u0013BA\u001e@\u0013\t1h(A\u0004qC\u000e\\\u0017mZ3\n\u0005aL(\u0001\u0003+j[\u0016,f.\u001b;\u000b\u0005Yt\u0004\"B>\r\u0001\u0004a\u0018!\u00028b]>\u001c\bCA\u0013~\u0013\tqhE\u0001\u0003M_:<\u0017AC1cEJ,g/[1uKR\u0019q&a\u0001\t\r\u0005\u0015Q\u00021\u0001n\u0003\u0011)h.\u001b;\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012\u0001T\u0001\u0007KF,\u0018\r\\:\u0015\u0007\u001d\u000by\u0001C\u0005\u0002\u0012=\t\t\u00111\u0001\u0002\u0014\u0005\u0019\u0001\u0010J\u0019\u0011\u0007\u0015\n)\"C\u0002\u0002\u0018\u0019\u00121!\u00118z\u0003]\u0001&/\u001a;usB\u0013\u0018N\u001c;bE2,G)\u001e:bi&|g\u000e\u0005\u0002Y#M\u0011\u0011\u0003\n\u000b\u0003\u00037\t\u0001\u0003\u001d:fiRLH%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007=\n)\u0003\u0003\u0004\u0002(M\u0001\raV\u0001\u0006IQD\u0017n\u001d\u000b\u0005\u0003W\t\t\u0004F\u00030\u0003[\ty\u0003C\u0003G)\u0001\u0007q\tC\u0004L)A\u0005\t\u0019\u0001'\t\r\u0005\u001dB\u00031\u0001X\u0003i\u0001(/\u001a;us\u0012\"WMZ1vYR$#\u0007J3yi\u0016t7/[8o)\r\t\u0017q\u0007\u0005\u0007\u0003O)\u0002\u0019A,\u0002)\rDwn\\:f+:LG\u000fJ3yi\u0016t7/[8o)\u0011\ti$!\u0011\u0015\u00075\fy\u0004C\u0003|-\u0001\u0007A\u0010\u0003\u0004\u0002(Y\u0001\raV\u0001\u0015C\n\u0014'/\u001a<jCR,G%\u001a=uK:\u001c\u0018n\u001c8\u0015\t\u0005\u001d\u00131\n\u000b\u0004_\u0005%\u0003BBA\u0003/\u0001\u0007Q\u000e\u0003\u0004\u0002(]\u0001\raV\u0001\u0013Q\u0006\u001c\bnQ8eK\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002\n\u0005E\u0003BBA\u00141\u0001\u0007q+\u0001\tfcV\fGn\u001d\u0013fqR,gn]5p]R!\u0011qKA.)\r9\u0015\u0011\f\u0005\n\u0003#I\u0012\u0011!a\u0001\u0003'Aa!a\n\u001a\u0001\u00049FcA,\u0002`!)1H\u0007a\u0001y\u0001")
public final class PrettyDuration {
    public static Duration PrettyPrintableDuration(Duration duration) {
        return PrettyDuration$.MODULE$.PrettyPrintableDuration(duration);
    }

    public static String format(Duration duration, boolean includeNanos, int precision) {
        return PrettyDuration$.MODULE$.format(duration, includeNanos, precision);
    }

    public static String format(Duration duration) {
        return PrettyDuration$.MODULE$.format(duration);
    }

    public static final class PrettyPrintableDuration {
        private final Duration duration;

        public Duration duration() {
            return this.duration;
        }

        public String pretty() {
            return PrettyDuration$PrettyPrintableDuration$.MODULE$.pretty$extension(this.duration());
        }

        public String pretty(boolean includeNanos, int precision) {
            return PrettyDuration$PrettyPrintableDuration$.MODULE$.pretty$extension(this.duration(), includeNanos, precision);
        }

        public int pretty$default$2() {
            return PrettyDuration$PrettyPrintableDuration$.MODULE$.pretty$default$2$extension(this.duration());
        }

        public TimeUnit chooseUnit(long nanos) {
            return PrettyDuration$PrettyPrintableDuration$.MODULE$.chooseUnit$extension(this.duration(), nanos);
        }

        public String abbreviate(TimeUnit unit) {
            return PrettyDuration$PrettyPrintableDuration$.MODULE$.abbreviate$extension(this.duration(), unit);
        }

        public int hashCode() {
            return PrettyDuration$PrettyPrintableDuration$.MODULE$.hashCode$extension(this.duration());
        }

        public boolean equals(Object x$1) {
            return PrettyDuration$PrettyPrintableDuration$.MODULE$.equals$extension(this.duration(), x$1);
        }

        public PrettyPrintableDuration(Duration duration) {
            this.duration = duration;
        }
    }
}

