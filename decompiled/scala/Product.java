/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Equals;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y2q\u0001C\u0005\u0011\u0002\u0007\u0005A\u0002C\u0003\u0015\u0001\u0011\u0005Q\u0003C\u0003\u001a\u0001\u0019\u0005!\u0004C\u0003\u001f\u0001\u0019\u0005q\u0004C\u0003#\u0001\u0011\u00051\u0005C\u0003,\u0001\u0011\u0005A\u0006C\u00039\u0001\u0011\u0005\u0011\bC\u0003<\u0001\u0011\u0005AHA\u0004Qe>$Wo\u0019;\u000b\u0003)\tQa]2bY\u0006\u001c\u0001aE\u0002\u0001\u001bE\u0001\"AD\b\u000e\u0003%I!\u0001E\u0005\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\u000f%%\u00111#\u0003\u0002\u0007\u000bF,\u0018\r\\:\u0002\r\u0011Jg.\u001b;%)\u00051\u0002C\u0001\b\u0018\u0013\tA\u0012B\u0001\u0003V]&$\u0018\u0001\u00049s_\u0012,8\r^!sSRLX#A\u000e\u0011\u00059a\u0012BA\u000f\n\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\ti\u0001\u0005C\u0003\"\u0007\u0001\u00071$A\u0001o\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#\u0001\u0013\u0011\u0007\u0015BSB\u0004\u0002\u000fM%\u0011q%C\u0001\ba\u0006\u001c7.Y4f\u0013\tI#F\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0015\t9\u0013\"A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002[A\u0011a&\u000e\b\u0003_M\u0002\"\u0001M\u0005\u000e\u0003ER!AM\u0006\u0002\rq\u0012xn\u001c;?\u0013\t!\u0014\"\u0001\u0004Qe\u0016$WMZ\u0005\u0003m]\u0012aa\u0015;sS:<'B\u0001\u001b\n\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u00055R\u0004\"B\u0011\u0007\u0001\u0004Y\u0012a\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016\u001cX#A\u001f\u0011\u0007\u0015BS\u0006")
public interface Product
extends Equals {
    public int productArity();

    public Object productElement(int var1);

    public static /* synthetic */ Iterator productIterator$(Product $this) {
        return $this.productIterator();
    }

    default public Iterator<Object> productIterator() {
        return new AbstractIterator<Object>(this){
            private int c;
            private final int cmax;
            private final /* synthetic */ Product $outer;

            public boolean hasNext() {
                return this.c < this.cmax;
            }

            public Object next() {
                Object result = this.$outer.productElement(this.c);
                ++this.c;
                return result;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.c = 0;
                this.cmax = $outer.productArity();
            }
        };
    }

    public static /* synthetic */ String productPrefix$(Product $this) {
        return $this.productPrefix();
    }

    default public String productPrefix() {
        return "";
    }

    public static /* synthetic */ String productElementName$(Product $this, int n) {
        return $this.productElementName(n);
    }

    default public String productElementName(int n) {
        if (n >= 0 && n < this.productArity()) {
            return "";
        }
        throw new IndexOutOfBoundsException(new StringBuilder(31).append(n).append(" is out of bounds (min 0, max ").append(this.productArity() - 1).append(")").toString());
    }

    public static /* synthetic */ Iterator productElementNames$(Product $this) {
        return $this.productElementNames();
    }

    default public Iterator<String> productElementNames() {
        return new AbstractIterator<String>(this){
            private int c;
            private final int cmax;
            private final /* synthetic */ Product $outer;

            public boolean hasNext() {
                return this.c < this.cmax;
            }

            public String next() {
                String result = this.$outer.productElementName(this.c);
                ++this.c;
                return result;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.c = 0;
                this.cmax = $outer.productArity();
            }
        };
    }

    public static void $init$(Product $this) {
    }
}

