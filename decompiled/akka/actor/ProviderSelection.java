/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ProviderSelection$;
import java.io.Serializable;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\tma!\u0002&L\u0003\u0003\u0001\u0006\"C,\u0001\u0005\u000b\u0007I\u0011A'Y\u0011!!\u0007A!A!\u0002\u0013I\u0006\"C3\u0001\u0005\u000b\u0007I\u0011A'Y\u0011!1\u0007A!A!\u0002\u0013I\u0006\"C4\u0001\u0005\u000b\u0007I\u0011A'i\u0011!a\u0007A!A!\u0002\u0013I\u0007\"B7\u0001\t\u0013qw!\u0002;L\u0011\u0003)h!\u0002&L\u0011\u00031\b\"B7\n\t\u00039\b\u0002\u0003=\n\u0005\u0004%\t!T=\t\u000f\u0005\r\u0011\u0002)A\u0005u\"I\u0011QA\u0005C\u0002\u0013\u0005Q*\u001f\u0005\b\u0003\u000fI\u0001\u0015!\u0003{\u000f\u001d\tI!\u0003EA\u0003\u00171q!a\u0004\n\u0011\u0003\u000b\t\u0002\u0003\u0004n!\u0011\u0005\u00111\u0006\u0005\t\u0003[\u0001\u0012\u0011!C!s\"I\u0011q\u0006\t\u0002\u0002\u0013\u0005\u0011\u0011\u0007\u0005\n\u0003s\u0001\u0012\u0011!C\u0001\u0003wA\u0011\"a\u0012\u0011\u0003\u0003%\t%!\u0013\t\u0013\u0005]\u0003#!A\u0005\u0002\u0005e\u0003\"CA/!\u0005\u0005I\u0011IA0\u0011%\t\t\u0007EA\u0001\n\u0003\n\u0019\u0007C\u0005\u0002fA\t\t\u0011\"\u0003\u0002h\u001d9\u0011qN\u0005\t\u0002\u0006EdaBA:\u0013!\u0005\u0015Q\u000f\u0005\u0007[n!\t!a\u001e\t\u0011\u000552$!A\u0005BeD\u0011\"a\f\u001c\u0003\u0003%\t!!\r\t\u0013\u0005e2$!A\u0005\u0002\u0005e\u0004\"CA$7\u0005\u0005I\u0011IA%\u0011%\t9fGA\u0001\n\u0003\ti\bC\u0005\u0002^m\t\t\u0011\"\u0011\u0002`!I\u0011\u0011M\u000e\u0002\u0002\u0013\u0005\u00131\r\u0005\n\u0003KZ\u0012\u0011!C\u0005\u0003O:q!!!\n\u0011\u0003\u000b\u0019IB\u0004\u0002\u0006&A\t)a\"\t\r54C\u0011AAE\u0011!\tiCJA\u0001\n\u0003J\b\"CA\u0018M\u0005\u0005I\u0011AA\u0019\u0011%\tIDJA\u0001\n\u0003\tY\tC\u0005\u0002H\u0019\n\t\u0011\"\u0011\u0002J!I\u0011q\u000b\u0014\u0002\u0002\u0013\u0005\u0011q\u0012\u0005\n\u0003;2\u0013\u0011!C!\u0003?B\u0011\"!\u0019'\u0003\u0003%\t%a\u0019\t\u0013\u0005\u0015d%!A\u0005\n\u0005\u001ddABAJ\u0013\t\u000b)\n\u0003\u0005fa\tU\r\u0011\"\u0011Y\u0011%1\u0007G!E!\u0002\u0013I6\u0001\u0003\u0004na\u0011\u0005\u0011q\u0013\u0005\n\u0003;\u0003\u0014\u0011!C\u0001\u0003?C\u0011\"a)1#\u0003%\t!!*\t\u0011\u00055\u0002'!A\u0005BeD\u0011\"a\f1\u0003\u0003%\t!!\r\t\u0013\u0005e\u0002'!A\u0005\u0002\u0005m\u0006\"CA$a\u0005\u0005I\u0011IA%\u0011%\t9\u0006MA\u0001\n\u0003\ty\fC\u0005\u0002DB\n\t\u0011\"\u0011\u0002F\"I\u0011Q\f\u0019\u0002\u0002\u0013\u0005\u0013q\f\u0005\n\u0003C\u0002\u0014\u0011!C!\u0003GB\u0011\"!31\u0003\u0003%\t%a3\b\u0013\u0005=\u0017\"!A\t\u0002\u0005Eg!CAJ\u0013\u0005\u0005\t\u0012AAj\u0011\u0019i\u0007\t\"\u0001\u0002l\"I\u0011\u0011\r!\u0002\u0002\u0013\u0015\u00131\r\u0005\n\u0003[\u0004\u0015\u0011!CA\u0003_D\u0011\"a=A\u0003\u0003%\t)!>\t\u0013\u0005\u0015\u0004)!A\u0005\n\u0005\u001d\u0004b\u0002B\u0001\u0013\u0011\u0005!1\u0001\u0005\b\u0005\u000bIA\u0011\u0001B\u0002\u0011\u001d\u00119!\u0003C\u0001\u0005\u0007A\u0001\"!<\n\t\u0003i%\u0011\u0002\u0002\u0012!J|g/\u001b3feN+G.Z2uS>t'B\u0001'N\u0003\u0015\t7\r^8s\u0015\u0005q\u0015\u0001B1lW\u0006\u001c\u0001a\u0005\u0002\u0001#B\u0011!+V\u0007\u0002'*\tA+A\u0003tG\u0006d\u0017-\u0003\u0002W'\n1\u0011I\\=SK\u001a\f!\"\u001b3f]RLg-[3s+\u0005I\u0006C\u0001.b\u001d\tYv\f\u0005\u0002]'6\tQL\u0003\u0002_\u001f\u00061AH]8pizJ!\u0001Y*\u0002\rA\u0013X\rZ3g\u0013\t\u00117M\u0001\u0004TiJLgn\u001a\u0006\u0003AN\u000b1\"\u001b3f]RLg-[3sA\u0005!a-]2o\u0003\u00151\u0017o\u00198!\u0003)A\u0017m]\"mkN$XM]\u000b\u0002SB\u0011!K[\u0005\u0003WN\u0013qAQ8pY\u0016\fg.A\u0006iCN\u001cE.^:uKJ\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0003pcJ\u001c\bC\u00019\u0001\u001b\u0005Y\u0005\"B,\b\u0001\u0004I\u0006\"B3\b\u0001\u0004I\u0006\"B4\b\u0001\u0004I\u0017!\u0005)s_ZLG-\u001a:TK2,7\r^5p]B\u0011\u0001/C\n\u0003\u0013E#\u0012!^\u0001\u0017%\u0016lw\u000e^3BGR|'OU3g!J|g/\u001b3feV\t!\u0010E\u0002|\u0003\u0003i\u0011\u0001 \u0006\u0003{z\fA\u0001\\1oO*\tq0\u0001\u0003kCZ\f\u0017B\u00012}\u0003]\u0011V-\\8uK\u0006\u001bGo\u001c:SK\u001a\u0004&o\u001c<jI\u0016\u0014\b%A\fDYV\u001cH/\u001a:BGR|'OU3g!J|g/\u001b3fe\u0006A2\t\\;ti\u0016\u0014\u0018i\u0019;peJ+g\r\u0015:pm&$WM\u001d\u0011\u0002\u000b1{7-\u00197\u0011\u0007\u00055\u0001#D\u0001\n\u0005\u0015aunY1m'\u0019\u0001r.a\u0005\u0002\u001aA\u0019!+!\u0006\n\u0007\u0005]1KA\u0004Qe>$Wo\u0019;\u0011\t\u0005m\u0011Q\u0005\b\u0005\u0003;\t\tCD\u0002]\u0003?I\u0011\u0001V\u0005\u0004\u0003G\u0019\u0016a\u00029bG.\fw-Z\u0005\u0005\u0003O\tIC\u0001\u0007TKJL\u0017\r\\5{C\ndWMC\u0002\u0002$M#\"!a\u0003\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\t\t\u0019\u0004E\u0002S\u0003kI1!a\u000eT\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\u0011\ti$a\u0011\u0011\u0007I\u000by$C\u0002\u0002BM\u00131!\u00118z\u0011%\t)\u0005FA\u0001\u0002\u0004\t\u0019$A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003\u0017\u0002b!!\u0014\u0002T\u0005uRBAA(\u0015\r\t\tfU\u0001\u000bG>dG.Z2uS>t\u0017\u0002BA+\u0003\u001f\u0012\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR\u0019\u0011.a\u0017\t\u0013\u0005\u0015c#!AA\u0002\u0005u\u0012\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005M\u0012\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003i\fAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!\u001b\u0011\u0007m\fY'C\u0002\u0002nq\u0014aa\u00142kK\u000e$\u0018A\u0002*f[>$X\rE\u0002\u0002\u000em\u0011aAU3n_R,7CB\u000ep\u0003'\tI\u0002\u0006\u0002\u0002rQ!\u0011QHA>\u0011%\t)eHA\u0001\u0002\u0004\t\u0019\u0004F\u0002j\u0003\u007fB\u0011\"!\u0012\"\u0003\u0003\u0005\r!!\u0010\u0002\u000f\rcWo\u001d;feB\u0019\u0011Q\u0002\u0014\u0003\u000f\rcWo\u001d;feN1ae\\A\n\u00033!\"!a!\u0015\t\u0005u\u0012Q\u0012\u0005\n\u0003\u000bR\u0013\u0011!a\u0001\u0003g!2![AI\u0011%\t)\u0005LA\u0001\u0002\u0004\tiD\u0001\u0004DkN$x.\\\n\u0007a=\f\u0019\"!\u0007\u0015\t\u0005e\u00151\u0014\t\u0004\u0003\u001b\u0001\u0004\"B34\u0001\u0004I\u0016\u0001B2paf$B!!'\u0002\"\"9Q\r\u000eI\u0001\u0002\u0004I\u0016AD2paf$C-\u001a4bk2$H%M\u000b\u0003\u0003OS3!WAUW\t\tY\u000b\u0005\u0003\u0002.\u0006]VBAAX\u0015\u0011\t\t,a-\u0002\u0013Ut7\r[3dW\u0016$'bAA['\u0006Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005e\u0016q\u0016\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,G\u0003BA\u001f\u0003{C\u0011\"!\u00129\u0003\u0003\u0005\r!a\r\u0015\u0007%\f\t\rC\u0005\u0002Fi\n\t\u00111\u0001\u0002>\u0005\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\rQ\u0018q\u0019\u0005\n\u0003\u000bZ\u0014\u0011!a\u0001\u0003g\ta!Z9vC2\u001cHcA5\u0002N\"I\u0011Q\t \u0002\u0002\u0003\u0007\u0011QH\u0001\u0007\u0007V\u001cHo\\7\u0011\u0007\u00055\u0001iE\u0003A\u0003+\f\t\u000fE\u0004\u0002X\u0006u\u0017,!'\u000e\u0005\u0005e'bAAn'\u00069!/\u001e8uS6,\u0017\u0002BAp\u00033\u0014\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82!\u0011\t\u0019/!;\u000e\u0005\u0005\u0015(bAAt}\u0006\u0011\u0011n\\\u0005\u0005\u0003O\t)\u000f\u0006\u0002\u0002R\u0006)\u0011\r\u001d9msR!\u0011\u0011TAy\u0011\u0015)7\t1\u0001Z\u0003\u001d)h.\u00199qYf$B!a>\u0002~B!!+!?Z\u0013\r\tYp\u0015\u0002\u0007\u001fB$\u0018n\u001c8\t\u0013\u0005}H)!AA\u0002\u0005e\u0015a\u0001=%a\u0005)An\\2bYR\tq.\u0001\u0004sK6|G/Z\u0001\bG2,8\u000f^3s)\ry'1\u0002\u0005\u0007\u0005\u001bI\u0005\u0019A-\u0002\u001bA\u0014xN^5eKJ\u001cE.Y:tQ\rI%\u0011\u0003\t\u0005\u0005'\u00119\"\u0004\u0002\u0003\u0016)\u0019\u0011QW'\n\t\te!Q\u0003\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e")
public abstract class ProviderSelection {
    private final String identifier;
    private final String fqcn;
    private final boolean hasCluster;

    public static ProviderSelection cluster() {
        return ProviderSelection$.MODULE$.cluster();
    }

    public static ProviderSelection remote() {
        return ProviderSelection$.MODULE$.remote();
    }

    public static ProviderSelection local() {
        return ProviderSelection$.MODULE$.local();
    }

    public String identifier() {
        return this.identifier;
    }

    public String fqcn() {
        return this.fqcn;
    }

    public boolean hasCluster() {
        return this.hasCluster;
    }

    public ProviderSelection(String identifier, String fqcn, boolean hasCluster) {
        this.identifier = identifier;
        this.fqcn = fqcn;
        this.hasCluster = hasCluster;
    }

    public static final class Custom
    extends ProviderSelection
    implements Product,
    Serializable {
        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public String fqcn() {
            return super.fqcn();
        }

        public Custom copy(String fqcn) {
            return new Custom(fqcn);
        }

        public String copy$default$1() {
            return this.fqcn();
        }

        @Override
        public String productPrefix() {
            return "Custom";
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
                    return this.fqcn();
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
            return x$1 instanceof Custom;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "fqcn";
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
            if (!(object instanceof Custom)) return false;
            boolean bl = true;
            if (!bl) return false;
            Custom custom = (Custom)x$1;
            String string = this.fqcn();
            String string2 = custom.fqcn();
            if (string != null) {
                if (!string.equals(string2)) return false;
                return true;
            }
            if (string2 == null) return true;
            return false;
        }

        public Custom(String fqcn) {
            super("custom", fqcn, false);
            Product.$init$(this);
        }
    }
}

