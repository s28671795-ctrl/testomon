/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.AbstractIndexedSeqView;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.collection.StringView$;
import scala.reflect.ScalaSignature;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\u0005}d\u0001\u0002\r\u001a\u0005zA\u0001B\u000e\u0001\u0003\u0016\u0004%\ta\u000e\u0005\t\u0001\u0002\u0011\t\u0012)A\u0005q!)\u0011\t\u0001C\u0001\u0005\")Q\t\u0001C\u0001\r\")!\n\u0001C\u0001\u0017\")A\u000e\u0001C![\"9a\u000eAA\u0001\n\u0003y\u0007bB9\u0001#\u0003%\tA\u001d\u0005\b{\u0002\t\t\u0011\"\u0011\u007f\u0011!\ti\u0001AA\u0001\n\u00031\u0005\"CA\b\u0001\u0005\u0005I\u0011AA\t\u0011%\ti\u0002AA\u0001\n\u0003\ny\u0002C\u0005\u0002(\u0001\t\t\u0011\"\u0001\u0002*!I\u00111\u0007\u0001\u0002\u0002\u0013\u0005\u0013Q\u0007\u0005\n\u0003s\u0001\u0011\u0011!C!\u0003wA\u0011\"!\u0010\u0001\u0003\u0003%\t%a\u0010\b\u0013\u0005\r\u0013$!A\t\u0002\u0005\u0015c\u0001\u0003\r\u001a\u0003\u0003E\t!a\u0012\t\r\u0005\u0013B\u0011AA0\u0011!a'#!A\u0005F\u0005\u0005\u0004\u0002\u0003&\u0013\u0003\u0003%\t)a\u0019\t\u0013\u0005\u001d$#!A\u0005\u0002\u0006%\u0004\"CA;%\u0005\u0005I\u0011BA<\u0005)\u0019FO]5oOZKWm\u001e\u0006\u00035m\t!bY8mY\u0016\u001cG/[8o\u0015\u0005a\u0012!B:dC2\f7\u0001A\n\u0005\u0001}9#\u0006E\u0002!C\rj\u0011!G\u0005\u0003Ee\u0011a#\u00112tiJ\f7\r^%oI\u0016DX\rZ*fcZKWm\u001e\t\u0003I\u0015j\u0011aG\u0005\u0003Mm\u0011Aa\u00115beB\u0011A\u0005K\u0005\u0003Sm\u0011q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002,g9\u0011A&\r\b\u0003[Aj\u0011A\f\u0006\u0003_u\ta\u0001\u0010:p_Rt\u0014\"\u0001\u000f\n\u0005IZ\u0012a\u00029bG.\fw-Z\u0005\u0003iU\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!AM\u000e\u0002\u0003M,\u0012\u0001\u000f\t\u0003sur!AO\u001e\u0011\u00055Z\u0012B\u0001\u001f\u001c\u0003\u0019\u0001&/\u001a3fM&\u0011ah\u0010\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005qZ\u0012AA:!\u0003\u0019a\u0014N\\5u}Q\u00111\t\u0012\t\u0003A\u0001AQAN\u0002A\u0002a\na\u0001\\3oORDW#A$\u0011\u0005\u0011B\u0015BA%\u001c\u0005\rIe\u000e^\u0001\u0006CB\u0004H.\u001f\u000b\u0003G1CQ!T\u0003A\u0002\u001d\u000b\u0011A\u001c\u0015\u0004\u000b=3\u0006c\u0001\u0013Q%&\u0011\u0011k\u0007\u0002\u0007i\"\u0014xn^:\u0011\u0005M#fB\u0001\u00132\u0013\t)VGA\u0010TiJLgnZ%oI\u0016Dx*\u001e;PM\n{WO\u001c3t\u000bb\u001cW\r\u001d;j_:\fDA\b\u001dXWF*1\u0005W.g9V\u0011q'\u0017\u0003\u00065v\u0011\ra\u0018\u0002\u0002)&\u0011A,X\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u0019\u000b\u0005y[\u0012A\u0002;ie><8/\u0005\u0002aGB\u0011A%Y\u0005\u0003En\u0011qAT8uQ&tw\r\u0005\u0002TI&\u0011Q-\u000e\u0002\n)\"\u0014xn^1cY\u0016\fTaI4iSzs!\u0001\n5\n\u0005y[\u0012\u0007\u0002\u0012%7)\u0014Qa]2bY\u0006\f$A\n*\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012\u0001O\u0001\u0005G>\u0004\u0018\u0010\u0006\u0002Da\"9ag\u0002I\u0001\u0002\u0004A\u0014AD2paf$C-\u001a4bk2$H%M\u000b\u0002g*\u0012\u0001\b^\u0016\u0002kB\u0011ao_\u0007\u0002o*\u0011\u00010_\u0001\nk:\u001c\u0007.Z2lK\u0012T!A_\u000e\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002}o\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005y\b\u0003BA\u0001\u0003\u0017i!!a\u0001\u000b\t\u0005\u0015\u0011qA\u0001\u0005Y\u0006twM\u0003\u0002\u0002\n\u0005!!.\u0019<b\u0013\rq\u00141A\u0001\raJ|G-^2u\u0003JLG/_\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\u0011\t\u0019\"!\u0007\u0011\u0007\u0011\n)\"C\u0002\u0002\u0018m\u00111!\u00118z\u0011!\tYbCA\u0001\u0002\u00049\u0015a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002\"A)\u0001%a\t\u0002\u0014%\u0019\u0011QE\r\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003W\t\t\u0004E\u0002%\u0003[I1!a\f\u001c\u0005\u001d\u0011un\u001c7fC:D\u0011\"a\u0007\u000e\u0003\u0003\u0005\r!a\u0005\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0004\u007f\u0006]\u0002\u0002CA\u000e\u001d\u0005\u0005\t\u0019A$\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012aR\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005-\u0012\u0011\t\u0005\n\u00037\u0001\u0012\u0011!a\u0001\u0003'\t!b\u0015;sS:<g+[3x!\t\u0001#cE\u0003\u0013\u0003\u0013\n)\u0006\u0005\u0004\u0002L\u0005E\u0003hQ\u0007\u0003\u0003\u001bR1!a\u0014\u001c\u0003\u001d\u0011XO\u001c;j[\u0016LA!a\u0015\u0002N\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u0019\u0011\t\u0005]\u0013QL\u0007\u0003\u00033RA!a\u0017\u0002\b\u0005\u0011\u0011n\\\u0005\u0004i\u0005eCCAA#)\u0005yHcA\"\u0002f!)a'\u0006a\u0001q\u00059QO\\1qa2LH\u0003BA6\u0003c\u0002B\u0001JA7q%\u0019\u0011qN\u000e\u0003\r=\u0003H/[8o\u0011!\t\u0019HFA\u0001\u0002\u0004\u0019\u0015a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011\u0011\u0010\t\u0005\u0003\u0003\tY(\u0003\u0003\u0002~\u0005\r!AB(cU\u0016\u001cG\u000f")
public final class StringView
extends AbstractIndexedSeqView<Object>
implements Product {
    private final String s;

    public static Option<String> unapply(StringView x$0) {
        return StringView$.MODULE$.unapply(x$0);
    }

    public static <A$> Function1<String, A$> andThen(Function1<StringView, A$> g2) {
        return arg_0 -> Function1.$anonfun$andThen$1(StringView$.MODULE$, g2, arg_0);
    }

    public static <A$> Function1<A$, StringView> compose(Function1<A$, String> g2) {
        return arg_0 -> Function1.$anonfun$compose$1(StringView$.MODULE$, g2, arg_0);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String s() {
        return this.s;
    }

    @Override
    public int length() {
        return this.s().length();
    }

    @Override
    public char apply(int n) throws StringIndexOutOfBoundsException {
        return this.s().charAt(n);
    }

    @Override
    public String toString() {
        return new StringBuilder(12).append("StringView(").append(this.s()).append(")").toString();
    }

    public StringView copy(String s2) {
        return new StringView(s2);
    }

    public String copy$default$1() {
        return this.s();
    }

    @Override
    public String productPrefix() {
        return "StringView";
    }

    @Override
    public int productArity() {
        return 1;
    }

    @Override
    public Object productElement(int x$1) {
        switch (x$1) {
            case 0: {
                return this.s();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return new AbstractIterator<T>(this){
            private int c;
            private final int cmax;
            private final Product x$2;

            public boolean hasNext() {
                return this.c < this.cmax;
            }

            public T next() {
                Object result = this.x$2.productElement(this.c);
                ++this.c;
                return (T)result;
            }
            {
                this.x$2 = x$2;
                this.c = 0;
                this.cmax = x$2.productArity();
            }
        };
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof StringView;
    }

    @Override
    public String productElementName(int x$1) {
        switch (x$1) {
            case 0: {
                return "s";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return MurmurHash3$.MODULE$.productHash(this);
    }

    @Override
    public boolean equals(Object x$1) {
        block3: {
            block2: {
                if (this == x$1) break block2;
                if (!(x$1 instanceof StringView)) break block3;
                StringView stringView = (StringView)x$1;
                String string = this.s();
                String string2 = stringView.s();
                if (string != null ? !string.equals(string2) : string2 != null) break block3;
            }
            return true;
        }
        return false;
    }

    public StringView(String s2) {
        this.s = s2;
    }
}

