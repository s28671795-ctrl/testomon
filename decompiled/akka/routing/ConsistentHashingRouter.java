/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.InvalidMessageException$;
import akka.routing.RouterEnvelope;
import java.io.Serializable;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005=w!\u0002\u0015*\u0011\u0003qc!\u0002\u0019*\u0011\u0003\t\u0004\"\u0002\u001d\u0002\t\u0003Ida\u0002\u001e\u0002!\u0003\r\na\u000f\u0005\u0006y\r1\t!\u0010\u0004\u0005\u0003\u0006\u0011%\t\u0003\u0005^\u000b\tU\r\u0011\"\u0001>\u0011!qVA!E!\u0002\u0013q\u0004\u0002C0\u0006\u0005+\u0007I\u0011A\u001f\t\u0011\u0001,!\u0011#Q\u0001\nyBQ\u0001O\u0003\u0005\u0002\u0005DQ\u0001P\u0003\u0005BuBq!Z\u0003\u0002\u0002\u0013\u0005a\rC\u0004j\u000bE\u0005I\u0011\u00016\t\u000fU,\u0011\u0013!C\u0001U\"9a/BA\u0001\n\u0003:\b\"CA\u0001\u000b\u0005\u0005I\u0011AA\u0002\u0011%\tY!BA\u0001\n\u0003\ti\u0001C\u0005\u0002\u0014\u0015\t\t\u0011\"\u0011\u0002\u0016!I\u00111E\u0003\u0002\u0002\u0013\u0005\u0011Q\u0005\u0005\n\u0003_)\u0011\u0011!C!\u0003cA\u0011\"!\u000e\u0006\u0003\u0003%\t%a\u000e\t\u0013\u0005eR!!A\u0005B\u0005m\u0002\"CA\u001f\u000b\u0005\u0005I\u0011IA \u000f%\ty%AA\u0001\u0012\u0003\t\tF\u0002\u0005B\u0003\u0005\u0005\t\u0012AA*\u0011\u0019A\u0014\u0004\"\u0001\u0002l!I\u0011\u0011H\r\u0002\u0002\u0013\u0015\u00131\b\u0005\n\u0003[J\u0012\u0011!CA\u0003_B\u0011\"!\u001e\u001a\u0003\u0003%\t)a\u001e\t\u0013\u0005%\u0015$!A\u0005\n\u0005-UABAJ\u0003\u0001\t)jB\u0004\u0002\u001c\u0006A\t!!(\u0007\u000f\u0005}\u0015\u0001#\u0001\u0002\"\"1\u0001(\tC\u0001\u0003KCq!a*\"\t\u0003\tI\u000bC\u0004\u0002n\u0005\"\t!a,\u0007\u0013\u0005u\u0016\u0001%A\u0012\u0002\u0005}\u0006BB0&\r\u0003\t\t\r\u0003\u0005\u0002F\u0006!\taKAd\u0003]\u0019uN\\:jgR,g\u000e\u001e%bg\"Lgn\u001a*pkR,'O\u0003\u0002+W\u00059!o\\;uS:<'\"\u0001\u0017\u0002\t\u0005\\7.Y\u0002\u0001!\ty\u0013!D\u0001*\u0005]\u0019uN\\:jgR,g\u000e\u001e%bg\"Lgn\u001a*pkR,'o\u0005\u0002\u0002eA\u00111GN\u0007\u0002i)\tQ'A\u0003tG\u0006d\u0017-\u0003\u00028i\t1\u0011I\\=SK\u001a\fa\u0001P5oSRtD#\u0001\u0018\u0003%\r{gn]5ti\u0016tG\u000fS1tQ\u0006\u0014G.Z\n\u0003\u0007I\n\u0011cY8og&\u001cH/\u001a8u\u0011\u0006\u001c\bnS3z+\u0005q\u0004CA\u001a@\u0013\t\u0001EGA\u0002B]f\u0014!dQ8og&\u001cH/\u001a8u\u0011\u0006\u001c\b.\u00192mK\u0016sg/\u001a7pa\u0016\u001cr!\u0002\u001aD\u000b\"s\u0015\u000b\u0005\u0002E\u00075\t\u0011\u0001\u0005\u00020\r&\u0011q)\u000b\u0002\u000f%>,H/\u001a:F]Z,Gn\u001c9f!\tIE*D\u0001K\u0015\tY5&A\u0003bGR|'/\u0003\u0002N\u0015\nqqK]1qa\u0016$W*Z:tC\u001e,\u0007CA\u001aP\u0013\t\u0001FGA\u0004Qe>$Wo\u0019;\u0011\u0005ISfBA*Y\u001d\t!v+D\u0001V\u0015\t1V&\u0001\u0004=e>|GOP\u0005\u0002k%\u0011\u0011\fN\u0001\ba\u0006\u001c7.Y4f\u0013\tYFL\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002Zi\u00059Q.Z:tC\u001e,\u0017\u0001C7fgN\fw-\u001a\u0011\u0002\u000f!\f7\u000f[&fs\u0006A\u0001.Y:i\u0017\u0016L\b\u0005F\u0002cG\u0012\u0004\"\u0001R\u0003\t\u000buS\u0001\u0019\u0001 \t\u000b}S\u0001\u0019\u0001 \u0002\t\r|\u0007/\u001f\u000b\u0004E\u001eD\u0007bB/\r!\u0003\u0005\rA\u0010\u0005\b?2\u0001\n\u00111\u0001?\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012a\u001b\u0016\u0003}1\\\u0013!\u001c\t\u0003]Nl\u0011a\u001c\u0006\u0003aF\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005I$\u0014AC1o]>$\u0018\r^5p]&\u0011Ao\u001c\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0003a\u0004\"!\u001f@\u000e\u0003iT!a\u001f?\u0002\t1\fgn\u001a\u0006\u0002{\u0006!!.\u0019<b\u0013\ty(P\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003\u000b\u00012aMA\u0004\u0013\r\tI\u0001\u000e\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004}\u0005=\u0001\"CA\t#\u0005\u0005\t\u0019AA\u0003\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011q\u0003\t\u0006\u00033\tyBP\u0007\u0003\u00037Q1!!\b5\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003C\tYB\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA\u0014\u0003[\u00012aMA\u0015\u0013\r\tY\u0003\u000e\u0002\b\u0005>|G.Z1o\u0011!\t\tbEA\u0001\u0002\u0004q\u0014A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$2\u0001_A\u001a\u0011%\t\t\u0002FA\u0001\u0002\u0004\t)!\u0001\u0005iCND7i\u001c3f)\t\t)!\u0001\u0005u_N#(/\u001b8h)\u0005A\u0018AB3rk\u0006d7\u000f\u0006\u0003\u0002(\u0005\u0005\u0003\u0002CA\t/\u0005\u0005\t\u0019\u0001 )\u000f\u0015\t)%a\u0013\u0002NA\u00191'a\u0012\n\u0007\u0005%CG\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t\u0011!\u0001\u000eD_:\u001c\u0018n\u001d;f]RD\u0015m\u001d5bE2,WI\u001c<fY>\u0004X\r\u0005\u0002E3M)\u0011$!\u0016\u0002bA9\u0011qKA/}y\u0012WBAA-\u0015\r\tY\u0006N\u0001\beVtG/[7f\u0013\u0011\ty&!\u0017\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t'\u0007\u0005\u0003\u0002d\u0005%TBAA3\u0015\r\t9\u0007`\u0001\u0003S>L1aWA3)\t\t\t&A\u0003baBd\u0017\u0010F\u0003c\u0003c\n\u0019\bC\u0003^9\u0001\u0007a\bC\u0003`9\u0001\u0007a(A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0005e\u0014Q\u0011\t\u0006g\u0005m\u0014qP\u0005\u0004\u0003{\"$AB(qi&|g\u000eE\u00034\u0003\u0003sd(C\u0002\u0002\u0004R\u0012a\u0001V;qY\u0016\u0014\u0004\u0002CAD;\u0005\u0005\t\u0019\u00012\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002\u000eB\u0019\u00110a$\n\u0007\u0005E%P\u0001\u0004PE*,7\r\u001e\u0002\u0016\u0007>t7/[:uK:$\b*Y:i\u001b\u0006\u0004\b/\u001b8h!\u0015\u0019\u0014q\u0013 ?\u0013\r\tI\n\u000e\u0002\u0010!\u0006\u0014H/[1m\rVt7\r^5p]\u0006QR-\u001c9us\u000e{gn]5ti\u0016tG\u000fS1tQ6\u000b\u0007\u000f]5oOB\u0011A)\t\u0002\u001bK6\u0004H/_\"p]NL7\u000f^3oi\"\u000b7\u000f['baBLgnZ\n\u0005CI\n\u0019\u000b\u0005\u0002E?Q\u0011\u0011QT\u0001\fSN$UMZ5oK\u0012\fE\u000f\u0006\u0003\u0002(\u0005-\u0006BBAWG\u0001\u0007a(A\u0001y)\u0011\t\t,a.\u0011\u0007M\n\u0019,C\u0002\u00026R\u0012qAT8uQ&tw\r\u0003\u0004\u0002.\u0012\u0002\rA\u0010\u0015\bC\u0005\u0015\u00131JA'Q\u001d\u0001\u0013QIA&\u0003\u001b\u0012AcQ8og&\u001cH/\u001a8u\u0011\u0006\u001c\b.T1qa\u0016\u00148CA\u00133)\rq\u00141\u0019\u0005\u0006;\u001a\u0002\rAP\u0001\u0013Q\u0006\u001c\b.T1qa&tw-\u00113baR,'\u000f\u0006\u0003\u0002$\u0006%\u0007bBAfO\u0001\u0007\u0011QZ\u0001\u0007[\u0006\u0004\b/\u001a:\u0011\u0005\u0011+\u0003")
public final class ConsistentHashingRouter {

    public static interface ConsistentHashMapper {
        public Object hashKey(Object var1);
    }

    public static interface ConsistentHashable {
        public Object consistentHashKey();
    }

    public static final class ConsistentHashableEnvelope
    implements ConsistentHashable,
    RouterEnvelope,
    Product,
    Serializable {
        private static final long serialVersionUID = 1L;
        private final Object message;
        private final Object hashKey;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public Object message() {
            return this.message;
        }

        public Object hashKey() {
            return this.hashKey;
        }

        @Override
        public Object consistentHashKey() {
            return this.hashKey();
        }

        public ConsistentHashableEnvelope copy(Object message, Object hashKey) {
            return new ConsistentHashableEnvelope(message, hashKey);
        }

        public Object copy$default$1() {
            return this.message();
        }

        public Object copy$default$2() {
            return this.hashKey();
        }

        @Override
        public String productPrefix() {
            return "ConsistentHashableEnvelope";
        }

        @Override
        public int productArity() {
            return 2;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.message();
                }
                case 1: {
                    return this.hashKey();
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
            return x$1 instanceof ConsistentHashableEnvelope;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "message";
                }
                case 1: {
                    return "hashKey";
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
            if (!(object instanceof ConsistentHashableEnvelope)) return false;
            boolean bl = true;
            if (!bl) return false;
            ConsistentHashableEnvelope consistentHashableEnvelope = (ConsistentHashableEnvelope)x$1;
            if (!BoxesRunTime.equals(this.message(), consistentHashableEnvelope.message())) return false;
            if (!BoxesRunTime.equals(this.hashKey(), consistentHashableEnvelope.hashKey())) return false;
            return true;
        }

        public ConsistentHashableEnvelope(Object message, Object hashKey) {
            this.message = message;
            this.hashKey = hashKey;
            Product.$init$(this);
            if (message == null) {
                throw InvalidMessageException$.MODULE$.apply("[null] is not an allowed message");
            }
        }
    }
}

