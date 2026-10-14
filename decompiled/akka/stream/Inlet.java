/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.InPort;
import akka.stream.Inlet$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e;Q\u0001D\u0007\t\u0002I1Q\u0001F\u0007\t\u0002UAQ\u0001H\u0001\u0005\u0002uAQAH\u0001\u0005\u0002}AQAU\u0001\u0005\u0002M3A\u0001F\u0007\u0003E!Aq%\u0002BC\u0002\u0013\u0005\u0001\u0006\u0003\u00055\u000b\t\u0005\t\u0015!\u0003*\u0011\u0015aR\u0001\"\u00036\u0011\u0015\u0019U\u0001\"\u0001E\u0011\u0015)U\u0001\"\u0001G\u0011\u0015aU\u0001\"\u0011N\u0003\u0015Ie\u000e\\3u\u0015\tqq\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002!\u0005!\u0011m[6b\u0007\u0001\u0001\"aE\u0001\u000e\u00035\u0011Q!\u00138mKR\u001c\"!\u0001\f\u0011\u0005]QR\"\u0001\r\u000b\u0003e\tQa]2bY\u0006L!a\u0007\r\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t!#A\u0003baBd\u00170\u0006\u0002!\u001fR\u0011\u0011\u0005\u0015\t\u0004'\u0015qUCA\u0012:'\t)A\u0005\u0005\u0002\u0014K%\u0011a%\u0004\u0002\u0007\u0013:\u0004vN\u001d;\u0002\u0003M,\u0012!\u000b\t\u0003UEr!aK\u0018\u0011\u00051BR\"A\u0017\u000b\u00059\n\u0012A\u0002\u001fs_>$h(\u0003\u000211\u00051\u0001K]3eK\u001aL!AM\u001a\u0003\rM#(/\u001b8h\u0015\t\u0001\u0004$\u0001\u0002tAQ\u0011aG\u0011\t\u0004'\u00159\u0004C\u0001\u001d:\u0019\u0001!QAO\u0003C\u0002m\u0012\u0011\u0001V\t\u0003y}\u0002\"aF\u001f\n\u0005yB\"a\u0002(pi\"Lgn\u001a\t\u0003/\u0001K!!\u0011\r\u0003\u0007\u0005s\u0017\u0010C\u0003(\u0011\u0001\u0007\u0011&\u0001\u0006dCJ\u0014wN\\\"paf$\u0012AN\u0001\u0003CN,\"a\u0012&\u0016\u0003!\u00032aE\u0003J!\tA$\nB\u0003L\u0015\t\u00071HA\u0001V\u0003!!xn\u0015;sS:<G#A\u0015\u0011\u0005azE!\u0002\u001e\u0004\u0005\u0004Y\u0004\"B)\u0004\u0001\u0004I\u0013\u0001\u00028b[\u0016\faa\u0019:fCR,WC\u0001+X)\t)\u0006\fE\u0002\u0014\u000bY\u0003\"\u0001O,\u0005\u000bi\"!\u0019A\u001e\t\u000bE#\u0001\u0019A\u0015")
public final class Inlet<T>
extends InPort {
    private final String s;

    public static <T> Inlet<T> create(String name) {
        return Inlet$.MODULE$.create(name);
    }

    public static <T> Inlet<T> apply(String name) {
        return Inlet$.MODULE$.apply(name);
    }

    public String s() {
        return this.s;
    }

    public Inlet<T> carbonCopy() {
        Inlet in = Inlet$.MODULE$.apply(this.s());
        in.mappedTo_$eq(this);
        return in;
    }

    public <U> Inlet<U> as() {
        return this;
    }

    public String toString() {
        return new StringBuilder(2).append(this.s()).append("(").append(this.hashCode()).append(")").append((Object)(this.mappedTo() == this ? "" : new StringBuilder(11).append(" mapped to ").append(this.mappedTo()).toString())).toString();
    }

    public Inlet(String s2) {
        this.s = s2;
    }
}

