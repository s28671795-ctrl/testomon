/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FlowMonitorState$;
import java.io.Serializable;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\tMt!\u0002&L\u0011\u0003\u0001f!\u0002*L\u0011\u0003\u0019\u0006\"\u0002.\u0002\t\u0003Yfa\u0002/\u0002!\u0003\r\n#X\u0004\b\u0003O\f\u0001\u0012QAP\r\u001d\tI*\u0001EA\u00037CaAW\u0003\u0005\u0002\u0005u\u0005\"CA\u0015\u000b\u0005\u0005I\u0011IA\u0016\u0011%\ti$BA\u0001\n\u0003\ty\u0004C\u0005\u0002H\u0015\t\t\u0011\"\u0001\u0002\"\"I\u0011qJ\u0003\u0002\u0002\u0013\u0005\u0013\u0011\u000b\u0005\n\u0003?*\u0011\u0011!C\u0001\u0003KC\u0011\"!\u001d\u0006\u0003\u0003%\t%a\u001d\t\u0013\u0005UT!!A\u0005B\u0005]\u0004\"CAH\u000b\u0005\u0005I\u0011BAI\u0011\u001d\tI/\u0001C\u0001\u0003W4a!!+\u0002\u0005\u0006-\u0006BCA\\!\tU\r\u0011\"\u0001\u0002:\"Q\u00111\u0018\t\u0003\u0012\u0003\u0006I!!-\t\ri\u0003B\u0011AA_\u0011%\tY\u0001EA\u0001\n\u0003\t\u0019\rC\u0005\u0002\u0012A\t\n\u0011\"\u0001\u0002P\"I\u0011\u0011\u0006\t\u0002\u0002\u0013\u0005\u00131\u0006\u0005\n\u0003{\u0001\u0012\u0011!C\u0001\u0003\u007fA\u0011\"a\u0012\u0011\u0003\u0003%\t!a6\t\u0013\u0005=\u0003#!A\u0005B\u0005E\u0003\"CA0!\u0005\u0005I\u0011AAn\u0011%\tY\u0007EA\u0001\n\u0003\ny\u000eC\u0005\u0002rA\t\t\u0011\"\u0011\u0002t!I\u0011Q\u000f\t\u0002\u0002\u0013\u0005\u0013q\u000f\u0005\n\u0003s\u0002\u0012\u0011!C!\u0003G<\u0011\"!>\u0002\u0003\u0003E\t!a>\u0007\u0013\u0005%\u0016!!A\t\u0002\u0005e\bB\u0002.!\t\u0003\u0011)\u0001C\u0005\u0002v\u0001\n\t\u0011\"\u0012\u0002x!I!q\u0001\u0011\u0002\u0002\u0013\u0005%\u0011\u0002\u0005\n\u0005+\u0001\u0013\u0011!CA\u0005/A\u0011\"a$!\u0003\u0003%I!!%\t\u000f\t-\u0012\u0001\"\u0001\u0003.\u0019!\u0011.\u0001\"k\u0011!axE!f\u0001\n\u0003i\b\"CA\u0002O\tE\t\u0015!\u0003\u007f\u0011\u0019Qv\u0005\"\u0001\u0002\u0006!I\u00111B\u0014\u0002\u0002\u0013\u0005\u0011Q\u0002\u0005\n\u0003#9\u0013\u0013!C\u0001\u0003'A\u0011\"!\u000b(\u0003\u0003%\t%a\u000b\t\u0013\u0005ur%!A\u0005\u0002\u0005}\u0002\"CA$O\u0005\u0005I\u0011AA%\u0011%\tyeJA\u0001\n\u0003\n\t\u0006C\u0005\u0002`\u001d\n\t\u0011\"\u0001\u0002b!I\u00111N\u0014\u0002\u0002\u0013\u0005\u0013Q\u000e\u0005\n\u0003c:\u0013\u0011!C!\u0003gB\u0011\"!\u001e(\u0003\u0003%\t%a\u001e\t\u0013\u0005et%!A\u0005B\u0005mt!\u0003B\u001d\u0003\u0005\u0005\t\u0012\u0001B\u001e\r!I\u0017!!A\t\u0002\tu\u0002B\u0002.8\t\u0003\u0011Y\u0005C\u0005\u0002v]\n\t\u0011\"\u0012\u0002x!I!qA\u001c\u0002\u0002\u0013\u0005%Q\n\u0005\n\u0005+9\u0014\u0011!CA\u0005#B\u0011\"a$8\u0003\u0003%I!!%\t\u000f\t]\u0013\u0001\"\u0001\u0003Z\u001d9!QM\u0001\t\u0002\u0006\u0015eaBA@\u0003!\u0005\u0015\u0011\u0011\u0005\u00075~\"\t!a!\t\u0013\u0005%r(!A\u0005B\u0005-\u0002\"CA\u001f\u007f\u0005\u0005I\u0011AA \u0011%\t9ePA\u0001\n\u0003\t9\tC\u0005\u0002P}\n\t\u0011\"\u0011\u0002R!I\u0011qL \u0002\u0002\u0013\u0005\u00111\u0012\u0005\n\u0003cz\u0014\u0011!C!\u0003gB\u0011\"!\u001e@\u0003\u0003%\t%a\u001e\t\u0013\u0005=u(!A\u0005\n\u0005E\u0005b\u0002B4\u0003\u0011\u0005!\u0011N\u0001\u0011\r2|w/T8oSR|'o\u0015;bi\u0016T!\u0001T'\u0002\rM$(/Z1n\u0015\u0005q\u0015\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002R\u00035\t1J\u0001\tGY><Xj\u001c8ji>\u00148\u000b^1uKN\u0011\u0011\u0001\u0016\t\u0003+bk\u0011A\u0016\u0006\u0002/\u0006)1oY1mC&\u0011\u0011L\u0016\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005\u0001&aC*ue\u0016\fWn\u0015;bi\u0016,\"AX0\u0014\u0005\r!FA\u00021\u0004\t\u000b\u0007\u0011MA\u0001V#\t\u0011W\r\u0005\u0002VG&\u0011AM\u0016\u0002\b\u001d>$\b.\u001b8h!\t)f-\u0003\u0002h-\n\u0019\u0011I\\=*\u000b\r9s(\u0002\t\u0003\r\u0019\u000b\u0017\u000e\\3e'\u00159Ck[7q!\ra7AY\u0007\u0002\u0003A\u0011QK\\\u0005\u0003_Z\u0013q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002rs:\u0011!o\u001e\b\u0003gZl\u0011\u0001\u001e\u0006\u0003k>\u000ba\u0001\u0010:p_Rt\u0014\"A,\n\u0005a4\u0016a\u00029bG.\fw-Z\u0005\u0003un\u0014AbU3sS\u0006d\u0017N_1cY\u0016T!\u0001\u001f,\u0002\u000b\r\fWo]3\u0016\u0003y\u0004\"!]@\n\u0007\u0005\u00051PA\u0005UQJ|w/\u00192mK\u000611-Y;tK\u0002\"B!a\u0002\u0002\nA\u0011An\n\u0005\u0006y*\u0002\rA`\u0001\u0005G>\u0004\u0018\u0010\u0006\u0003\u0002\b\u0005=\u0001b\u0002?,!\u0003\u0005\rA`\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\t\t)BK\u0002\u007f\u0003/Y#!!\u0007\u0011\t\u0005m\u0011QE\u0007\u0003\u0003;QA!a\b\u0002\"\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003G1\u0016AC1o]>$\u0018\r^5p]&!\u0011qEA\u000f\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u00055\u0002\u0003BA\u0018\u0003si!!!\r\u000b\t\u0005M\u0012QG\u0001\u0005Y\u0006twM\u0003\u0002\u00028\u0005!!.\u0019<b\u0013\u0011\tY$!\r\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\t\t\t\u0005E\u0002V\u0003\u0007J1!!\u0012W\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\r)\u00171\n\u0005\n\u0003\u001bz\u0013\u0011!a\u0001\u0003\u0003\n1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA*!\u0015\t)&a\u0017f\u001b\t\t9FC\u0002\u0002ZY\u000b!bY8mY\u0016\u001cG/[8o\u0013\u0011\ti&a\u0016\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003G\nI\u0007E\u0002V\u0003KJ1!a\u001aW\u0005\u001d\u0011un\u001c7fC:D\u0001\"!\u00142\u0003\u0003\u0005\r!Z\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0002.\u0005=\u0004\"CA'e\u0005\u0005\t\u0019AA!\u0003!A\u0017m\u001d5D_\u0012,GCAA!\u0003!!xn\u0015;sS:<GCAA\u0017\u0003\u0019)\u0017/^1mgR!\u00111MA?\u0011!\ti%NA\u0001\u0002\u0004)'\u0001\u0003$j]&\u001c\b.\u001a3\u0014\u000b}\"6.\u001c9\u0015\u0005\u0005\u0015\u0005C\u00017@)\r)\u0017\u0011\u0012\u0005\n\u0003\u001b\u001a\u0015\u0011!a\u0001\u0003\u0003\"B!a\u0019\u0002\u000e\"A\u0011QJ#\u0002\u0002\u0003\u0007Q-\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002\u0014B!\u0011qFAK\u0013\u0011\t9*!\r\u0003\r=\u0013'.Z2u\u0005-Ie.\u001b;jC2L'0\u001a3\u0014\u000b\u0015!6.\u001c9\u0015\u0005\u0005}\u0005C\u00017\u0006)\r)\u00171\u0015\u0005\n\u0003\u001bJ\u0011\u0011!a\u0001\u0003\u0003\"B!a\u0019\u0002(\"A\u0011QJ\u0006\u0002\u0002\u0003\u0007QM\u0001\u0005SK\u000e,\u0017N^3e+\u0011\ti+!.\u0014\rA!\u0016qV7q!\u0011a7!!-\u0011\t\u0005M\u0016Q\u0017\u0007\u0001\t\u0019\u0001\u0007\u0003\"b\u0001C\u0006\u0019Qn]4\u0016\u0005\u0005E\u0016\u0001B7tO\u0002\"B!a0\u0002BB!A\u000eEAY\u0011\u001d\t9l\u0005a\u0001\u0003c+B!!2\u0002LR!\u0011qYAg!\u0011a\u0007#!3\u0011\t\u0005M\u00161\u001a\u0003\u0006AR\u0011\r!\u0019\u0005\n\u0003o#\u0002\u0013!a\u0001\u0003\u0013,B!!5\u0002VV\u0011\u00111\u001b\u0016\u0005\u0003c\u000b9\u0002B\u0003a+\t\u0007\u0011\rF\u0002f\u00033D\u0011\"!\u0014\u0019\u0003\u0003\u0005\r!!\u0011\u0015\t\u0005\r\u0014Q\u001c\u0005\t\u0003\u001bR\u0012\u0011!a\u0001KR!\u0011QFAq\u0011%\tieGA\u0001\u0002\u0004\t\t\u0005\u0006\u0003\u0002d\u0005\u0015\b\u0002CA'=\u0005\u0005\t\u0019A3\u0002\u0017%s\u0017\u000e^5bY&TX\rZ\u0001\fS:LG/[1mSj,G-\u0006\u0003\u0002n\u0006MHCAAx!\u0011a7!!=\u0011\t\u0005M\u00161\u001f\u0003\u0006A>\u0011\r!Y\u0001\t%\u0016\u001cW-\u001b<fIB\u0011A\u000eI\n\u0005AQ\u000bY\u0010\u0005\u0003\u0002~\n\rQBAA\u0000\u0015\u0011\u0011\t!!\u000e\u0002\u0005%|\u0017b\u0001>\u0002\u0000R\u0011\u0011q_\u0001\u0006CB\u0004H._\u000b\u0005\u0005\u0017\u0011\t\u0002\u0006\u0003\u0003\u000e\tM\u0001\u0003\u00027\u0011\u0005\u001f\u0001B!a-\u0003\u0012\u0011)\u0001m\tb\u0001C\"9\u0011qW\u0012A\u0002\t=\u0011aB;oCB\u0004H._\u000b\u0005\u00053\u0011\u0019\u0003\u0006\u0003\u0003\u001c\t\u0015\u0002#B+\u0003\u001e\t\u0005\u0012b\u0001B\u0010-\n1q\n\u001d;j_:\u0004B!a-\u0003$\u0011)\u0001\r\nb\u0001C\"I!q\u0005\u0013\u0002\u0002\u0003\u0007!\u0011F\u0001\u0004q\u0012\u0002\u0004\u0003\u00027\u0011\u0005C\t\u0001B]3dK&4X\rZ\u000b\u0005\u0005_\u0011)\u0004\u0006\u0003\u00032\t]\u0002\u0003\u00027\u0004\u0005g\u0001B!a-\u00036\u0011)\u0001M\nb\u0001C\"9\u0011q\u0017\u0014A\u0002\tM\u0012A\u0002$bS2,G\r\u0005\u0002moM)qGa\u0010\u0002|B9!\u0011\tB$}\u0006\u001dQB\u0001B\"\u0015\r\u0011)EV\u0001\beVtG/[7f\u0013\u0011\u0011IEa\u0011\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t\u0017\u0007\u0006\u0002\u0003<Q!\u0011q\u0001B(\u0011\u0015a(\b1\u0001\u007f)\u0011\u0011\u0019F!\u0016\u0011\tU\u0013iB \u0005\n\u0005OY\u0014\u0011!a\u0001\u0003\u000f\taAZ1jY\u0016$W\u0003\u0002B.\u0005C\"BA!\u0018\u0003dA!An\u0001B0!\u0011\t\u0019L!\u0019\u0005\u000b\u0001l$\u0019A1\t\u000bql\u0004\u0019\u0001@\u0002\u0011\u0019Kg.[:iK\u0012\f\u0001BZ5oSNDW\rZ\u000b\u0005\u0005W\u0012\t\b\u0006\u0002\u0003nA!An\u0001B8!\u0011\t\u0019L!\u001d\u0005\u000b\u0001L%\u0019A1")
public final class FlowMonitorState {
    public static <U> StreamState<U> finished() {
        return FlowMonitorState$.MODULE$.finished();
    }

    public static <U> StreamState<U> failed(Throwable cause) {
        return FlowMonitorState$.MODULE$.failed(cause);
    }

    public static <U> StreamState<U> received(U msg) {
        return FlowMonitorState$.MODULE$.received(msg);
    }

    public static <U> StreamState<U> initialized() {
        return FlowMonitorState$.MODULE$.initialized();
    }

    public static final class Failed
    implements StreamState<Nothing$>,
    Product,
    Serializable {
        private final Throwable cause;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Throwable cause() {
            return this.cause;
        }

        public Failed copy(Throwable cause) {
            return new Failed(cause);
        }

        public Throwable copy$default$1() {
            return this.cause();
        }

        @Override
        public String productPrefix() {
            return "Failed";
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
                    return this.cause();
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
            return x$1 instanceof Failed;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "cause";
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
            if (!(object instanceof Failed)) return false;
            boolean bl = true;
            if (!bl) return false;
            Failed failed = (Failed)x$1;
            Throwable throwable = this.cause();
            Throwable throwable2 = failed.cause();
            if (throwable != null) {
                if (!throwable.equals(throwable2)) return false;
                return true;
            }
            if (throwable2 == null) return true;
            return false;
        }

        public Failed(Throwable cause) {
            this.cause = cause;
            Product.$init$(this);
        }
    }

    public static final class Received<U>
    implements StreamState<U>,
    Product,
    Serializable {
        private final U msg;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public U msg() {
            return this.msg;
        }

        public <U> Received<U> copy(U msg) {
            return new Received<U>(msg);
        }

        public <U> U copy$default$1() {
            return this.msg();
        }

        @Override
        public String productPrefix() {
            return "Received";
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
                    return this.msg();
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
            return x$1 instanceof Received;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "msg";
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
            if (!(object instanceof Received)) return false;
            boolean bl = true;
            if (!bl) return false;
            Received received = (Received)x$1;
            if (!BoxesRunTime.equals(this.msg(), received.msg())) return false;
            return true;
        }

        public Received(U msg) {
            this.msg = msg;
            Product.$init$(this);
        }
    }

    public static interface StreamState<U> {
    }
}

