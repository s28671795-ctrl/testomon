/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.io.dns.RecordClass$;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005md\u0001\u0002\u0012$\u0005*B\u0001\u0002\u0011\u0001\u0003\u0016\u0004%\t!\u0011\u0005\t\u000b\u0002\u0011\t\u0012)A\u0005\u0005\"Aa\t\u0001BK\u0002\u0013\u0005q\t\u0003\u0005Q\u0001\tE\t\u0015!\u0003I\u0011\u0015\t\u0006\u0001\"\u0001S\u0011\u001d9\u0006!!A\u0005\u0002aCqa\u0017\u0001\u0012\u0002\u0013\u0005A\fC\u0004h\u0001E\u0005I\u0011\u00015\t\u000f)\u0004\u0011\u0011!C!W\"91\u000fAA\u0001\n\u0003!\bb\u0002=\u0001\u0003\u0003%\t!\u001f\u0005\t\u007f\u0002\t\t\u0011\"\u0011\u0002\u0002!I\u0011q\u0002\u0001\u0002\u0002\u0013\u0005\u0011\u0011\u0003\u0005\n\u00037\u0001\u0011\u0011!C!\u0003;A\u0011\"!\t\u0001\u0003\u0003%\t%a\t\t\u0013\u0005\u0015\u0002!!A\u0005B\u0005\u001d\u0002\"CA\u0015\u0001\u0005\u0005I\u0011IA\u0016\u000f\u001d\tyc\tE\u0001\u0003c1aAI\u0012\t\u0002\u0005M\u0002BB)\u0014\t\u0003\ti\u0004C\u0005\u0002@M\u0011\r\u0011\"\u0001\u0002B!9\u00111I\n!\u0002\u0013\u0019\u0006\"CA#'\t\u0007I\u0011AA!\u0011\u001d\t9e\u0005Q\u0001\nMC\u0011\"!\u0013\u0014\u0005\u0004%\t!!\u0011\t\u000f\u0005-3\u0003)A\u0005'\"I\u0011QJ\nC\u0002\u0013\u0005\u0011\u0011\t\u0005\b\u0003\u001f\u001a\u0002\u0015!\u0003T\u0011%\t\tf\u0005b\u0001\n\u0003\t\t\u0005C\u0004\u0002TM\u0001\u000b\u0011B*\t\u0013\u0005U3#!A\u0005\u0002\u0006]\u0003\"CA/'\u0005\u0005I\u0011QA0\u0011%\t\thEA\u0001\n\u0013\t\u0019HA\u0006SK\u000e|'\u000fZ\"mCN\u001c(B\u0001\u0013&\u0003\r!gn\u001d\u0006\u0003M\u001d\n!![8\u000b\u0003!\nA!Y6lC\u000e\u00011\u0003\u0002\u0001,cQ\u0002\"\u0001L\u0018\u000e\u00035R\u0011AL\u0001\u0006g\u000e\fG.Y\u0005\u0003a5\u0012a!\u00118z%\u00164\u0007C\u0001\u00173\u0013\t\u0019TFA\u0004Qe>$Wo\u0019;\u0011\u0005UjdB\u0001\u001c<\u001d\t9$(D\u00019\u0015\tI\u0014&\u0001\u0004=e>|GOP\u0005\u0002]%\u0011A(L\u0001\ba\u0006\u001c7.Y4f\u0013\tqtH\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002=[\u0005!1m\u001c3f+\u0005\u0011\u0005C\u0001\u0017D\u0013\t!UFA\u0003TQ>\u0014H/A\u0003d_\u0012,\u0007%\u0001\u0003oC6,W#\u0001%\u0011\u0005%keB\u0001&L!\t9T&\u0003\u0002M[\u00051\u0001K]3eK\u001aL!AT(\u0003\rM#(/\u001b8h\u0015\taU&A\u0003oC6,\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0004'V3\u0006C\u0001+\u0001\u001b\u0005\u0019\u0003\"\u0002!\u0006\u0001\u0004\u0011\u0005\"\u0002$\u0006\u0001\u0004A\u0015\u0001B2paf$2aU-[\u0011\u001d\u0001e\u0001%AA\u0002\tCqA\u0012\u0004\u0011\u0002\u0003\u0007\u0001*\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003uS#A\u00110,\u0003}\u0003\"\u0001Y3\u000e\u0003\u0005T!AY2\u0002\u0013Ut7\r[3dW\u0016$'B\u00013.\u0003)\tgN\\8uCRLwN\\\u0005\u0003M\u0006\u0014\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\u0012!\u001b\u0016\u0003\u0011z\u000bQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#\u00017\u0011\u00055\u0014X\"\u00018\u000b\u0005=\u0004\u0018\u0001\u00027b]\u001eT\u0011!]\u0001\u0005U\u00064\u0018-\u0003\u0002O]\u0006a\u0001O]8ek\u000e$\u0018I]5usV\tQ\u000f\u0005\u0002-m&\u0011q/\f\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003uv\u0004\"\u0001L>\n\u0005ql#aA!os\"9apCA\u0001\u0002\u0004)\u0018a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002\u0004A)\u0011QAA\u0006u6\u0011\u0011q\u0001\u0006\u0004\u0003\u0013i\u0013AC2pY2,7\r^5p]&!\u0011QBA\u0004\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005M\u0011\u0011\u0004\t\u0004Y\u0005U\u0011bAA\f[\t9!i\\8mK\u0006t\u0007b\u0002@\u000e\u0003\u0003\u0005\rA_\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\rF\u0002m\u0003?AqA \b\u0002\u0002\u0003\u0007Q/\u0001\u0005iCND7i\u001c3f)\u0005)\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u00031\fa!Z9vC2\u001cH\u0003BA\n\u0003[AqA`\t\u0002\u0002\u0003\u0007!0A\u0006SK\u000e|'\u000fZ\"mCN\u001c\bC\u0001+\u0014'\u0011\u00192&!\u000e\u0011\t\u0005]\u00121H\u0007\u0003\u0003sQ!A\n9\n\u0007y\nI\u0004\u0006\u0002\u00022\u0005\u0011\u0011JT\u000b\u0002'\u0006\u0019\u0011J\u0014\u0011\u0002\u0005\r\u001b\u0016aA\"TA\u0005\u00111\tS\u0001\u0004\u0007\"\u0003\u0013A\u0001%T\u0003\rA5\u000bI\u0001\t/&cEiQ!S\t\u0006Iq+\u0013'E\u0007\u0006\u0013F\tI\u0001\u0006CB\u0004H.\u001f\u000b\u0006'\u0006e\u00131\f\u0005\u0006\u0001~\u0001\rA\u0011\u0005\u0006\r~\u0001\r\u0001S\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\t\t'!\u001c\u0011\u000b1\n\u0019'a\u001a\n\u0007\u0005\u0015TF\u0001\u0004PaRLwN\u001c\t\u0006Y\u0005%$\tS\u0005\u0004\u0003Wj#A\u0002+va2,'\u0007\u0003\u0005\u0002p\u0001\n\t\u00111\u0001T\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003k\u00022!\\A<\u0013\r\tIH\u001c\u0002\u0007\u001f\nTWm\u0019;")
public final class RecordClass
implements Product,
Serializable {
    private final short code;
    private final String name;

    public static Option<Tuple2<Object, String>> unapply(RecordClass x$0) {
        return RecordClass$.MODULE$.unapply(x$0);
    }

    public static RecordClass apply(short code, String name) {
        return RecordClass$.MODULE$.apply(code, name);
    }

    public static RecordClass WILDCARD() {
        return RecordClass$.MODULE$.WILDCARD();
    }

    public static RecordClass HS() {
        return RecordClass$.MODULE$.HS();
    }

    public static RecordClass CH() {
        return RecordClass$.MODULE$.CH();
    }

    public static RecordClass CS() {
        return RecordClass$.MODULE$.CS();
    }

    public static RecordClass IN() {
        return RecordClass$.MODULE$.IN();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public short code() {
        return this.code;
    }

    public String name() {
        return this.name;
    }

    public RecordClass copy(short code, String name) {
        return new RecordClass(code, name);
    }

    public short copy$default$1() {
        return this.code();
    }

    public String copy$default$2() {
        return this.name();
    }

    @Override
    public String productPrefix() {
        return "RecordClass";
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
                return BoxesRunTime.boxToShort(this.code());
            }
            case 1: {
                return this.name();
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
        return x$1 instanceof RecordClass;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "code";
            }
            case 1: {
                return "name";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.code());
        n = Statics.mix(n, Statics.anyHash(this.name()));
        return Statics.finalizeHash(n, 2);
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
        if (!(object instanceof RecordClass)) return false;
        boolean bl = true;
        if (!bl) return false;
        RecordClass recordClass = (RecordClass)x$1;
        if (this.code() != recordClass.code()) return false;
        String string = this.name();
        String string2 = recordClass.name();
        if (string != null) {
            if (!string.equals(string2)) return false;
            return true;
        }
        if (string2 == null) return true;
        return false;
    }

    public RecordClass(short code, String name) {
        this.code = code;
        this.name = name;
        Product.$init$(this);
    }
}

