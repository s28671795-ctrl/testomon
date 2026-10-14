/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.ValueOf$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U3Aa\u0003\u0007\u0003\u001f!AQ\u0003\u0001BC\u0002\u0013\u0005a\u0003\u0003\u0005#\u0001\t\u0005\t\u0015!\u0003\u0018\u0011\u0015\u0019\u0003\u0001\"\u0001%\u0011\u001d9\u0003!!A\u0005B!Bq\u0001\f\u0001\u0002\u0002\u0013\u0005SfB\u0004>\u0019\u0005\u0005\t\u0012\u0001 \u0007\u000f-a\u0011\u0011!E\u0001\u007f!)1e\u0002C\u0001\u0007\"9AiBA\u0001\n\u000b)\u0005b\u0002'\b\u0003\u0003%)!\u0014\u0002\b-\u0006dW/Z(g\u0015\u0005i\u0011!B:dC2\f7\u0001A\u000b\u0003!e\u0019\"\u0001A\t\u0011\u0005I\u0019R\"\u0001\u0007\n\u0005Qa!AB!osZ\u000bG.A\u0003wC2,X-F\u0001\u0018!\tA\u0012\u0004\u0004\u0001\u0005\u000bi\u0001!\u0019A\u000e\u0003\u0003Q\u000b\"\u0001H\u0010\u0011\u0005Ii\u0012B\u0001\u0010\r\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u0005\u0011\n\u0005\u0005b!aA!os\u00061a/\u00197vK\u0002\na\u0001P5oSRtDCA\u0013'!\r\u0011\u0002a\u0006\u0005\u0006+\r\u0001\raF\u0001\tQ\u0006\u001c\bnQ8eKR\t\u0011\u0006\u0005\u0002\u0013U%\u00111\u0006\u0004\u0002\u0004\u0013:$\u0018AB3rk\u0006d7\u000f\u0006\u0002/cA\u0011!cL\u0005\u0003a1\u0011qAQ8pY\u0016\fg\u000eC\u00043\u000b\u0005\u0005\t\u0019A\u0010\u0002\u0007a$\u0013\u0007\u000b\u0003\u0001iiZ\u0004CA\u001b9\u001b\u00051$BA\u001c\r\u0003)\tgN\\8uCRLwN\\\u0005\u0003sY\u0012\u0001#[7qY&\u001c\u0017\u000e\u001e(pi\u001a{WO\u001c3\u0002\u00075\u001cx-I\u0001=\u0003itu\u000eI:j]\u001edW\r^8oAY\fG.^3!CZ\f\u0017\u000e\\1cY\u0016\u0004cm\u001c:!Im$Vp\u000f\u0011fY&<\u0017N\u00197fAMLgn\u001a7fi>t\u0007\u0005^=qKN\u0004cm\u001c:!AZ\u000bG.^3PM\u0002\u00043/\u001f8uQ\u0016\u001c\u0018n\u001d\u0011j]\u000edW\u000fZ3!Y&$XM]1mg\u0002\ng\u000e\u001a\u0011ti\u0006\u0014G.\u001a\u0011qCRD7OL\u0001\b-\u0006dW/Z(g!\t\u0011ra\u0005\u0002\b\u0001B\u0011!#Q\u0005\u0003\u00052\u0011a!\u00118z%\u00164G#\u0001 \u0002%!\f7\u000f[\"pI\u0016$S\r\u001f;f]NLwN\\\u000b\u0003\r.#\"\u0001K$\t\u000b!K\u0001\u0019A%\u0002\u000b\u0011\"\b.[:\u0011\u0007I\u0001!\n\u0005\u0002\u0019\u0017\u0012)!$\u0003b\u00017\u0005\u0001R-];bYN$S\r\u001f;f]NLwN\\\u000b\u0003\u001dR#\"aT)\u0015\u00059\u0002\u0006b\u0002\u001a\u000b\u0003\u0003\u0005\ra\b\u0005\u0006\u0011*\u0001\rA\u0015\t\u0004%\u0001\u0019\u0006C\u0001\rU\t\u0015Q\"B1\u0001\u001c\u0001")
public final class ValueOf<T> {
    private final T value;

    public static <T> boolean equals$extension(T $this, Object x$1) {
        return ValueOf$.MODULE$.equals$extension($this, x$1);
    }

    public static <T> int hashCode$extension(T $this) {
        return $this.hashCode();
    }

    public T value() {
        return this.value;
    }

    public int hashCode() {
        return this.value().hashCode();
    }

    public boolean equals(Object x$1) {
        return ValueOf$.MODULE$.equals$extension(this.value(), x$1);
    }

    public ValueOf(T value) {
        this.value = value;
    }
}

