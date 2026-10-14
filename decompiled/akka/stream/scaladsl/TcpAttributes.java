/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.scaladsl.TcpAttributes$;
import java.io.Serializable;
import scala.Predef$;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015t!\u0002\u000e\u001c\u0011\u0003\u0011c!\u0002\u0013\u001c\u0011\u0003)\u0003\"\u0002\u0017\u0002\t\u0003ic\u0001\u0002\u0018\u0002\u0005>B\u0001BS\u0002\u0003\u0016\u0004%\ta\u0013\u0005\t\u001f\u000e\u0011\t\u0012)A\u0005\u0019\")Af\u0001C\u0001!\"9AkAA\u0001\n\u0003)\u0006bB,\u0004#\u0003%\t\u0001\u0017\u0005\bG\u000e\t\t\u0011\"\u0011e\u0011\u001di7!!A\u0005\u0002-CqA\\\u0002\u0002\u0002\u0013\u0005q\u000eC\u0004v\u0007\u0005\u0005I\u0011\t<\t\u000fu\u001c\u0011\u0011!C\u0001}\"I\u0011qA\u0002\u0002\u0002\u0013\u0005\u0013\u0011\u0002\u0005\n\u0003\u001b\u0019\u0011\u0011!C!\u0003\u001fA\u0011\"!\u0005\u0004\u0003\u0003%\t%a\u0005\t\u0013\u0005U1!!A\u0005B\u0005]q!CA\u000e\u0003\u0005\u0005\t\u0012AA\u000f\r!q\u0013!!A\t\u0002\u0005}\u0001B\u0002\u0017\u0014\t\u0003\t9\u0004C\u0005\u0002\u0012M\t\t\u0011\"\u0012\u0002\u0014!I\u0011\u0011H\n\u0002\u0002\u0013\u0005\u00151\b\u0005\n\u0003\u007f\u0019\u0012\u0011!CA\u0003\u0003B\u0011\"!\u0014\u0014\u0003\u0003%I!a\u0014\t\u000f\u0005]\u0013\u0001\"\u0001\u0002Z\u0005iAk\u00199BiR\u0014\u0018NY;uKNT!\u0001H\u000f\u0002\u0011M\u001c\u0017\r\\1eg2T!AH\u0010\u0002\rM$(/Z1n\u0015\u0005\u0001\u0013\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002$\u00035\t1DA\u0007UGB\fE\u000f\u001e:jEV$Xm]\n\u0003\u0003\u0019\u0002\"a\n\u0016\u000e\u0003!R\u0011!K\u0001\u0006g\u000e\fG.Y\u0005\u0003W!\u0012a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001#\u0005I!6\r],sSR,')\u001e4gKJ\u001c\u0016N_3\u0014\u000b\r1\u0003GP!\u0011\u0005EZdB\u0001\u001a:\u001d\t\u0019\u0004H\u0004\u00025o5\tQG\u0003\u00027C\u00051AH]8pizJ\u0011\u0001I\u0005\u0003=}I!AO\u000f\u0002\u0015\u0005#HO]5ckR,7/\u0003\u0002={\tI\u0011\t\u001e;sS\n,H/\u001a\u0006\u0003uu\u0001\"aJ \n\u0005\u0001C#a\u0002)s_\u0012,8\r\u001e\t\u0003\u0005\u001es!aQ#\u000f\u0005Q\"\u0015\"A\u0015\n\u0005\u0019C\u0013a\u00029bG.\fw-Z\u0005\u0003\u0011&\u0013AbU3sS\u0006d\u0017N_1cY\u0016T!A\u0012\u0015\u0002\tML'0Z\u000b\u0002\u0019B\u0011q%T\u0005\u0003\u001d\"\u00121!\u00138u\u0003\u0015\u0019\u0018N_3!)\t\t6\u000b\u0005\u0002S\u00075\t\u0011\u0001C\u0003K\r\u0001\u0007A*\u0001\u0003d_BLHCA)W\u0011\u001dQu\u0001%AA\u00021\u000babY8qs\u0012\"WMZ1vYR$\u0013'F\u0001ZU\ta%lK\u0001\\!\ta\u0016-D\u0001^\u0015\tqv,A\u0005v]\u000eDWmY6fI*\u0011\u0001\rK\u0001\u000bC:tw\u000e^1uS>t\u0017B\u00012^\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0003\u0015\u0004\"AZ6\u000e\u0003\u001dT!\u0001[5\u0002\t1\fgn\u001a\u0006\u0002U\u0006!!.\u0019<b\u0013\tawM\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\t\u00018\u000f\u0005\u0002(c&\u0011!\u000f\u000b\u0002\u0004\u0003:L\bb\u0002;\f\u0003\u0003\u0005\r\u0001T\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0003]\u00042\u0001_>q\u001b\u0005I(B\u0001>)\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003yf\u0014\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR\u0019q0!\u0002\u0011\u0007\u001d\n\t!C\u0002\u0002\u0004!\u0012qAQ8pY\u0016\fg\u000eC\u0004u\u001b\u0005\u0005\t\u0019\u00019\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0004K\u0006-\u0001b\u0002;\u000f\u0003\u0003\u0005\r\u0001T\u0001\tQ\u0006\u001c\bnQ8eKR\tA*\u0001\u0005u_N#(/\u001b8h)\u0005)\u0017AB3rk\u0006d7\u000fF\u0002\u0000\u00033Aq\u0001^\t\u0002\u0002\u0003\u0007\u0001/\u0001\nUGB<&/\u001b;f\u0005V4g-\u001a:TSj,\u0007C\u0001*\u0014'\u0015\u0019\u0012\u0011EA\u0017!\u0019\t\u0019#!\u000bM#6\u0011\u0011Q\u0005\u0006\u0004\u0003OA\u0013a\u0002:v]RLW.Z\u0005\u0005\u0003W\t)CA\tBEN$(/Y2u\rVt7\r^5p]F\u0002B!a\f\u000265\u0011\u0011\u0011\u0007\u0006\u0004\u0003gI\u0017AA5p\u0013\rA\u0015\u0011\u0007\u000b\u0003\u0003;\tQ!\u00199qYf$2!UA\u001f\u0011\u0015Qe\u00031\u0001M\u0003\u001d)h.\u00199qYf$B!a\u0011\u0002JA!q%!\u0012M\u0013\r\t9\u0005\u000b\u0002\u0007\u001fB$\u0018n\u001c8\t\u0011\u0005-s#!AA\u0002E\u000b1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\t\t\u0006E\u0002g\u0003'J1!!\u0016h\u0005\u0019y%M[3di\u0006\u0011Bo\u00199Xe&$XMQ;gM\u0016\u00148+\u001b>f)\u0011\tY&a\u0019\u0011\t\u0005u\u0013qL\u0007\u0002;%\u0019\u0011\u0011M\u000f\u0003\u0015\u0005#HO]5ckR,7\u000fC\u0003K3\u0001\u0007A\n")
public final class TcpAttributes {
    public static Attributes tcpWriteBufferSize(int size) {
        return TcpAttributes$.MODULE$.tcpWriteBufferSize(size);
    }

    public static final class TcpWriteBufferSize
    implements Attributes.Attribute,
    Product,
    Serializable {
        private final int size;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public int size() {
            return this.size;
        }

        public TcpWriteBufferSize copy(int size) {
            return new TcpWriteBufferSize(size);
        }

        public int copy$default$1() {
            return this.size();
        }

        @Override
        public String productPrefix() {
            return "TcpWriteBufferSize";
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
                    return BoxesRunTime.boxToInteger(this.size());
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
            return x$1 instanceof TcpWriteBufferSize;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "size";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.size());
            return Statics.finalizeHash(n, 1);
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
            if (!(object instanceof TcpWriteBufferSize)) return false;
            boolean bl = true;
            if (!bl) return false;
            TcpWriteBufferSize tcpWriteBufferSize = (TcpWriteBufferSize)x$1;
            if (this.size() != tcpWriteBufferSize.size()) return false;
            return true;
        }

        public TcpWriteBufferSize(int size) {
            this.size = size;
            Product.$init$(this);
            Predef$.MODULE$.require(size > 0);
        }
    }
}

