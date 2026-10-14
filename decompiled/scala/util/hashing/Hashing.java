/*
 * Decompiled with CFR 0.152.
 */
package scala.util.hashing;

import java.io.Serializable;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@ScalaSignature(bytes="\u0006\u0005A4qa\u0003\u0007\u0011\u0002G\u00051\u0003C\u0003!\u0001\u0019\u0005\u0011eB\u0003=\u0019!\u0005QHB\u0003\f\u0019!\u0005q\bC\u0003H\u0007\u0011\u0005\u0001J\u0002\u0003J\u0007\tQ\u0005\"B$\u0006\t\u0003y\u0005\"\u0002\u0011\u0006\t\u0003\u0011\u0006\"\u0002+\u0004\t\u0007)\u0006\"\u0002.\u0004\t\u0003Y\u0006b\u00025\u0004\u0003\u0003%I!\u001b\u0002\b\u0011\u0006\u001c\b.\u001b8h\u0015\tia\"A\u0004iCND\u0017N\\4\u000b\u0005=\u0001\u0012\u0001B;uS2T\u0011!E\u0001\u0006g\u000e\fG.Y\u0002\u0001+\t!\u0012fE\u0002\u0001+e\u0001\"AF\f\u000e\u0003AI!\u0001\u0007\t\u0003\r\u0005s\u0017PU3g!\tQRD\u0004\u0002\u00177%\u0011A\u0004E\u0001\ba\u0006\u001c7.Y4f\u0013\tqrD\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002\u001d!\u0005!\u0001.Y:i)\t\u0011S\u0005\u0005\u0002\u0017G%\u0011A\u0005\u0005\u0002\u0004\u0013:$\b\"\u0002\u0014\u0002\u0001\u00049\u0013!\u0001=\u0011\u0005!JC\u0002\u0001\u0003\u0006U\u0001\u0011\ra\u000b\u0002\u0002)F\u0011Af\f\t\u0003-5J!A\f\t\u0003\u000f9{G\u000f[5oOB\u0011a\u0003M\u0005\u0003cA\u00111!\u00118zQ\u0011\u00011'\u000f\u001e\u0011\u0005Q:T\"A\u001b\u000b\u0005Y\u0002\u0012AC1o]>$\u0018\r^5p]&\u0011\u0001(\u000e\u0002\u0011S6\u0004H.[2ji:{GOR8v]\u0012\f1!\\:hC\u0005Y\u0014!\n(pA%l\u0007\u000f\\5dSR\u0004\u0003*Y:iS:<\u0007\u0005Z3gS:,G\r\t4pe\u0002\"3\u0010V?/\u0003\u001dA\u0015m\u001d5j]\u001e\u0004\"AP\u0002\u000e\u00031\u00192aA\u000bA!\t\te)D\u0001C\u0015\t\u0019E)\u0001\u0002j_*\tQ)\u0001\u0003kCZ\f\u0017B\u0001\u0010C\u0003\u0019a\u0014N\\5u}Q\tQHA\u0004EK\u001a\fW\u000f\u001c;\u0016\u0005-s5cA\u0003\u0016\u0019B\u0019a\bA'\u0011\u0005!rE!\u0002\u0016\u0006\u0005\u0004YC#\u0001)\u0011\u0007E+Q*D\u0001\u0004)\t\u00113\u000bC\u0003'\u000f\u0001\u0007Q*A\u0004eK\u001a\fW\u000f\u001c;\u0016\u0005YKV#A,\u0011\u0007E+\u0001\f\u0005\u0002)3\u0012)!\u0006\u0003b\u0001W\u0005aaM]8n\rVt7\r^5p]V\u0011AL\u0019\u000b\u0003;\u000e\u00142AX\u000ba\r\u0011y\u0016\u0002A/\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \u0011\u0007y\u0002\u0011\r\u0005\u0002)E\u0012)!&\u0003b\u0001W!)A-\u0003a\u0001K\u0006\ta\r\u0005\u0003\u0017M\u0006\u0014\u0013BA4\u0011\u0005%1UO\\2uS>t\u0017'\u0001\u0007xe&$XMU3qY\u0006\u001cW\rF\u0001k!\tYg.D\u0001m\u0015\tiG)\u0001\u0003mC:<\u0017BA8m\u0005\u0019y%M[3di\u0002")
public interface Hashing<T>
extends Serializable {
    public static <T> Hashing<T> fromFunction(Function1<T, Object> f) {
        return new Hashing<T>(f){
            private final Function1 f$1;

            public int hash(T x) {
                return BoxesRunTime.unboxToInt(this.f$1.apply(x));
            }
            {
                this.f$1 = f$1;
            }
        };
    }

    public static <T> Default<T> default() {
        return new Default();
    }

    public int hash(T var1);

    public static final class Default<T>
    implements Hashing<T> {
        @Override
        public int hash(T x) {
            return Statics.anyHash(x);
        }
    }
}

