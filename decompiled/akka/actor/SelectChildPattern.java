/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.SelectChildPattern$;
import akka.actor.SelectionPathElement;
import akka.util.Helpers$;
import java.io.Serializable;
import java.util.regex.Pattern;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dd!\u0002\r\u001a\u0005ni\u0002\u0002\u0003\u001d\u0001\u0005+\u0007I\u0011A\u001d\t\u0011\t\u0003!\u0011#Q\u0001\niBQa\u0011\u0001\u0005\u0002\u0011Cqa\u0012\u0001C\u0002\u0013\u0005\u0001\n\u0003\u0004T\u0001\u0001\u0006I!\u0013\u0005\u0006)\u0002!\t%\u0016\u0005\b-\u0002\t\t\u0011\"\u0001X\u0011\u001dI\u0006!%A\u0005\u0002iCq!\u001a\u0001\u0002\u0002\u0013\u0005c\rC\u0004m\u0001\u0005\u0005I\u0011A7\t\u000fE\u0004\u0011\u0011!C\u0001e\"9\u0001\u0010AA\u0001\n\u0003J\b\"CA\u0001\u0001\u0005\u0005I\u0011AA\u0002\u0011%\ti\u0001AA\u0001\n\u0003\ny\u0001C\u0005\u0002\u0014\u0001\t\t\u0011\"\u0011\u0002\u0016!I\u0011q\u0003\u0001\u0002\u0002\u0013\u0005\u0013\u0011D\u0004\u000b\u0003SI\u0012\u0011!E\u00017\u0005-b!\u0003\r\u001a\u0003\u0003E\taGA\u0017\u0011\u0019\u0019%\u0003\"\u0001\u0002F!AAKEA\u0001\n\u000b\n9\u0005C\u0005\u0002JI\t\t\u0011\"!\u0002L!I\u0011q\n\n\u0002\u0002\u0013\u0005\u0015\u0011\u000b\u0005\n\u0003;\u0012\u0012\u0011!C\u0005\u0003?\u0012!cU3mK\u000e$8\t[5mIB\u000bG\u000f^3s]*\u0011!dG\u0001\u0006C\u000e$xN\u001d\u0006\u00029\u0005!\u0011m[6b'\u0015\u0001a\u0004\n\u0015,!\ty\"%D\u0001!\u0015\u0005\t\u0013!B:dC2\f\u0017BA\u0012!\u0005\u0019\te.\u001f*fMB\u0011QEJ\u0007\u00023%\u0011q%\u0007\u0002\u0015'\u0016dWm\u0019;j_:\u0004\u0016\r\u001e5FY\u0016lWM\u001c;\u0011\u0005}I\u0013B\u0001\u0016!\u0005\u001d\u0001&o\u001c3vGR\u0004\"\u0001L\u001b\u000f\u00055\u001adB\u0001\u00183\u001b\u0005y#B\u0001\u00192\u0003\u0019a$o\\8u}\r\u0001\u0011\"A\u0011\n\u0005Q\u0002\u0013a\u00029bG.\fw-Z\u0005\u0003m]\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!\u0001\u000e\u0011\u0002\u0015A\fG\u000f^3s]N#(/F\u0001;!\tYtH\u0004\u0002={A\u0011a\u0006I\u0005\u0003}\u0001\na\u0001\u0015:fI\u00164\u0017B\u0001!B\u0005\u0019\u0019FO]5oO*\u0011a\bI\u0001\fa\u0006$H/\u001a:o'R\u0014\b%\u0001\u0004=S:LGO\u0010\u000b\u0003\u000b\u001a\u0003\"!\n\u0001\t\u000ba\u001a\u0001\u0019\u0001\u001e\u0002\u000fA\fG\u000f^3s]V\t\u0011\n\u0005\u0002K#6\t1J\u0003\u0002M\u001b\u0006)!/Z4fq*\u0011ajT\u0001\u0005kRLGNC\u0001Q\u0003\u0011Q\u0017M^1\n\u0005I[%a\u0002)biR,'O\\\u0001\ta\u0006$H/\u001a:oA\u0005AAo\\*ue&tw\rF\u0001;\u0003\u0011\u0019w\u000e]=\u0015\u0005\u0015C\u0006b\u0002\u001d\b!\u0003\u0005\rAO\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005Y&F\u0001\u001e]W\u0005i\u0006C\u00010d\u001b\u0005y&B\u00011b\u0003%)hn\u00195fG.,GM\u0003\u0002cA\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005\u0011|&!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006i\u0001O]8ek\u000e$\bK]3gSb,\u0012a\u001a\t\u0003Q.l\u0011!\u001b\u0006\u0003U>\u000bA\u0001\\1oO&\u0011\u0001)[\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0002]B\u0011qd\\\u0005\u0003a\u0002\u00121!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$\"a\u001d<\u0011\u0005}!\u0018BA;!\u0005\r\te.\u001f\u0005\bo.\t\t\u00111\u0001o\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\t!\u0010E\u0002|}Nl\u0011\u0001 \u0006\u0003{\u0002\n!bY8mY\u0016\u001cG/[8o\u0013\tyHP\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA\u0003\u0003\u0017\u00012aHA\u0004\u0013\r\tI\u0001\t\u0002\b\u0005>|G.Z1o\u0011\u001d9X\"!AA\u0002M\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u0019q-!\u0005\t\u000f]t\u0011\u0011!a\u0001]\u0006A\u0001.Y:i\u0007>$W\rF\u0001o\u0003\u0019)\u0017/^1mgR!\u0011QAA\u000e\u0011\u001d9\b#!AA\u0002MDs\u0001AA\u0010\u0003K\t9\u0003E\u0002 \u0003CI1!a\t!\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0003\u0003I\u0019V\r\\3di\u000eC\u0017\u000e\u001c3QCR$XM\u001d8\u0011\u0005\u0015\u00122#\u0002\n\u00020\u0005m\u0002CBA\u0019\u0003oQT)\u0004\u0002\u00024)\u0019\u0011Q\u0007\u0011\u0002\u000fI,h\u000e^5nK&!\u0011\u0011HA\u001a\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\t\u0005\u0003{\t\u0019%\u0004\u0002\u0002@)\u0019\u0011\u0011I(\u0002\u0005%|\u0017b\u0001\u001c\u0002@Q\u0011\u00111\u0006\u000b\u0002O\u0006)\u0011\r\u001d9msR\u0019Q)!\u0014\t\u000ba*\u0002\u0019\u0001\u001e\u0002\u000fUt\u0017\r\u001d9msR!\u00111KA-!\u0011y\u0012Q\u000b\u001e\n\u0007\u0005]\u0003E\u0001\u0004PaRLwN\u001c\u0005\t\u000372\u0012\u0011!a\u0001\u000b\u0006\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005\u0005\u0004c\u00015\u0002d%\u0019\u0011QM5\u0003\r=\u0013'.Z2u\u0001")
public final class SelectChildPattern
implements SelectionPathElement,
Product,
Serializable {
    private static final long serialVersionUID = 2L;
    private final String patternStr;
    private final Pattern pattern;

    public static Option<String> unapply(SelectChildPattern x$0) {
        return SelectChildPattern$.MODULE$.unapply(x$0);
    }

    public static SelectChildPattern apply(String patternStr) {
        return SelectChildPattern$.MODULE$.apply(patternStr);
    }

    public static <A> Function1<String, A> andThen(Function1<SelectChildPattern, A> g2) {
        return SelectChildPattern$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, SelectChildPattern> compose(Function1<A, String> g2) {
        return SelectChildPattern$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String patternStr() {
        return this.patternStr;
    }

    public Pattern pattern() {
        return this.pattern;
    }

    public String toString() {
        return this.patternStr();
    }

    public SelectChildPattern copy(String patternStr) {
        return new SelectChildPattern(patternStr);
    }

    public String copy$default$1() {
        return this.patternStr();
    }

    @Override
    public String productPrefix() {
        return "SelectChildPattern";
    }

    @Override
    public int productArity() {
        return 1;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.patternStr();
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
        return x$1 instanceof SelectChildPattern;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "patternStr";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof SelectChildPattern)) return false;
        boolean bl = true;
        if (!bl) return false;
        SelectChildPattern selectChildPattern = (SelectChildPattern)x$1;
        String string = this.patternStr();
        String string2 = selectChildPattern.patternStr();
        if (string != null) {
            if (!string.equals(string2)) return false;
            return true;
        }
        if (string2 == null) return true;
        return false;
    }

    public SelectChildPattern(String patternStr) {
        this.patternStr = patternStr;
        Product.$init$(this);
        this.pattern = Helpers$.MODULE$.makePattern(patternStr);
    }
}

