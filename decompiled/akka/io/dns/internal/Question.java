/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.annotation.InternalApi;
import akka.io.dns.RecordClass;
import akka.io.dns.RecordType;
import akka.io.dns.internal.DomainName$;
import akka.io.dns.internal.Question$;
import akka.io.dns.internal.RecordClassSerializer$;
import akka.io.dns.internal.RecordTypeSerializer$;
import akka.util.ByteIterator;
import akka.util.ByteString;
import akka.util.ByteStringBuilder;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005mf!B\u000f\u001f\u0005\u00122\u0003\u0002C\u001f\u0001\u0005+\u0007I\u0011\u0001 \t\u0011\u001d\u0003!\u0011#Q\u0001\n}B\u0001\u0002\u0013\u0001\u0003\u0016\u0004%\t!\u0013\u0005\t\u001d\u0002\u0011\t\u0012)A\u0005\u0015\"Aq\n\u0001BK\u0002\u0013\u0005\u0001\u000b\u0003\u0005U\u0001\tE\t\u0015!\u0003R\u0011\u0015)\u0006\u0001\"\u0001W\u0011\u0015a\u0006\u0001\"\u0001^\u0011\u001dI\u0007!!A\u0005\u0002)DqA\u001c\u0001\u0012\u0002\u0013\u0005q\u000eC\u0004{\u0001E\u0005I\u0011A>\t\u000fu\u0004\u0011\u0013!C\u0001}\"I\u0011\u0011\u0001\u0001\u0002\u0002\u0013\u0005\u00131\u0001\u0005\n\u0003'\u0001\u0011\u0011!C\u0001\u0003+A\u0011\"!\b\u0001\u0003\u0003%\t!a\b\t\u0013\u0005-\u0002!!A\u0005B\u00055\u0002\"CA\u001e\u0001\u0005\u0005I\u0011AA\u001f\u0011%\t9\u0005AA\u0001\n\u0003\nI\u0005C\u0005\u0002N\u0001\t\t\u0011\"\u0011\u0002P!I\u0011\u0011\u000b\u0001\u0002\u0002\u0013\u0005\u00131\u000b\u0005\n\u0003+\u0002\u0011\u0011!C!\u0003/:\u0001\"a\u001a\u001f\u0011\u0003!\u0013\u0011\u000e\u0004\b;yA\t\u0001JA6\u0011\u0019)v\u0003\"\u0001\u0002v!9\u0011qO\f\u0005\u0002\u0005e\u0004\"CAH/\u0005\u0005I\u0011QAI\u0011%\tIjFA\u0001\n\u0003\u000bY\nC\u0005\u0002.^\t\t\u0011\"\u0003\u00020\nA\u0011+^3ti&|gN\u0003\u0002 A\u0005A\u0011N\u001c;fe:\fGN\u0003\u0002\"E\u0005\u0019AM\\:\u000b\u0005\r\"\u0013AA5p\u0015\u0005)\u0013\u0001B1lW\u0006\u001cB\u0001A\u0014.aA\u0011\u0001fK\u0007\u0002S)\t!&A\u0003tG\u0006d\u0017-\u0003\u0002-S\t1\u0011I\\=SK\u001a\u0004\"\u0001\u000b\u0018\n\u0005=J#a\u0002)s_\u0012,8\r\u001e\t\u0003cir!A\r\u001d\u000f\u0005M:T\"\u0001\u001b\u000b\u0005U2\u0014A\u0002\u001fs_>$hh\u0001\u0001\n\u0003)J!!O\u0015\u0002\u000fA\f7m[1hK&\u00111\b\u0010\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003s%\nAA\\1nKV\tq\b\u0005\u0002A\t:\u0011\u0011I\u0011\t\u0003g%J!aQ\u0015\u0002\rA\u0013X\rZ3g\u0013\t)eI\u0001\u0004TiJLgn\u001a\u0006\u0003\u0007&\nQA\\1nK\u0002\nQ!\u001d+za\u0016,\u0012A\u0013\t\u0003\u00172k\u0011\u0001I\u0005\u0003\u001b\u0002\u0012!BU3d_J$G+\u001f9f\u0003\u0019\tH+\u001f9fA\u00051\u0011o\u00117bgN,\u0012!\u0015\t\u0003\u0017JK!a\u0015\u0011\u0003\u0017I+7m\u001c:e\u00072\f7o]\u0001\bc\u000ec\u0017m]:!\u0003\u0019a\u0014N\\5u}Q!q+\u0017.\\!\tA\u0006!D\u0001\u001f\u0011\u0015it\u00011\u0001@\u0011\u0015Au\u00011\u0001K\u0011\u0015yu\u00011\u0001R\u0003\u00159(/\u001b;f)\tq\u0016\r\u0005\u0002)?&\u0011\u0001-\u000b\u0002\u0005+:LG\u000fC\u0003c\u0011\u0001\u00071-A\u0002pkR\u0004\"\u0001Z4\u000e\u0003\u0015T!A\u001a\u0013\u0002\tU$\u0018\u000e\\\u0005\u0003Q\u0016\u0014\u0011CQ=uKN#(/\u001b8h\u0005VLG\u000eZ3s\u0003\u0011\u0019w\u000e]=\u0015\t][G.\u001c\u0005\b{%\u0001\n\u00111\u0001@\u0011\u001dA\u0015\u0002%AA\u0002)CqaT\u0005\u0011\u0002\u0003\u0007\u0011+\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003AT#aP9,\u0003I\u0004\"a\u001d=\u000e\u0003QT!!\u001e<\u0002\u0013Ut7\r[3dW\u0016$'BA<*\u0003)\tgN\\8uCRLwN\\\u0005\u0003sR\u0014\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\u0012\u0001 \u0016\u0003\u0015F\fabY8qs\u0012\"WMZ1vYR$3'F\u0001\u0000U\t\t\u0016/A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003\u000b\u0001B!a\u0002\u0002\u00125\u0011\u0011\u0011\u0002\u0006\u0005\u0003\u0017\ti!\u0001\u0003mC:<'BAA\b\u0003\u0011Q\u0017M^1\n\u0007\u0015\u000bI!\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0006\u0002\u0002\u0018A\u0019\u0001&!\u0007\n\u0007\u0005m\u0011FA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0003\u0002\"\u0005\u001d\u0002c\u0001\u0015\u0002$%\u0019\u0011QE\u0015\u0003\u0007\u0005s\u0017\u0010C\u0005\u0002*=\t\t\u00111\u0001\u0002\u0018\u0005\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!a\f\u0011\r\u0005E\u0012qGA\u0011\u001b\t\t\u0019DC\u0002\u00026%\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\tI$a\r\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003\u007f\t)\u0005E\u0002)\u0003\u0003J1!a\u0011*\u0005\u001d\u0011un\u001c7fC:D\u0011\"!\u000b\u0012\u0003\u0003\u0005\r!!\t\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0003\u000b\tY\u0005C\u0005\u0002*I\t\t\u00111\u0001\u0002\u0018\u0005A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0002\u0018\u0005AAo\\*ue&tw\r\u0006\u0002\u0002\u0006\u00051Q-];bYN$B!a\u0010\u0002Z!I\u0011\u0011F\u000b\u0002\u0002\u0003\u0007\u0011\u0011\u0005\u0015\u0004\u0001\u0005u\u0003\u0003BA0\u0003Gj!!!\u0019\u000b\u0005]$\u0013\u0002BA3\u0003C\u00121\"\u00138uKJt\u0017\r\\!qS\u0006A\u0011+^3ti&|g\u000e\u0005\u0002Y/M!qcJA7!\u0011\ty'a\u001d\u000e\u0005\u0005E$bA\u0012\u0002\u000e%\u00191(!\u001d\u0015\u0005\u0005%\u0014!\u00029beN,G#B,\u0002|\u0005\u0015\u0005bBA?3\u0001\u0007\u0011qP\u0001\u0003SR\u00042\u0001ZAA\u0013\r\t\u0019)\u001a\u0002\r\u0005f$X-\u0013;fe\u0006$xN\u001d\u0005\b\u0003\u000fK\u0002\u0019AAE\u0003\ri7o\u001a\t\u0004I\u0006-\u0015bAAGK\nQ!)\u001f;f'R\u0014\u0018N\\4\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u000f]\u000b\u0019*!&\u0002\u0018\")QH\u0007a\u0001\u007f!)\u0001J\u0007a\u0001\u0015\")qJ\u0007a\u0001#\u00069QO\\1qa2LH\u0003BAO\u0003S\u0003R\u0001KAP\u0003GK1!!)*\u0005\u0019y\u0005\u000f^5p]B1\u0001&!*@\u0015FK1!a**\u0005\u0019!V\u000f\u001d7fg!A\u00111V\u000e\u0002\u0002\u0003\u0007q+A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!-\u0011\t\u0005\u001d\u00111W\u0005\u0005\u0003k\u000bIA\u0001\u0004PE*,7\r\u001e\u0015\u0004/\u0005u\u0003f\u0001\f\u0002^\u0001")
@InternalApi
public final class Question
implements Product,
Serializable {
    private final String name;
    private final RecordType qType;
    private final RecordClass qClass;

    public static Option<Tuple3<String, RecordType, RecordClass>> unapply(Question x$0) {
        return Question$.MODULE$.unapply(x$0);
    }

    public static Question apply(String name, RecordType qType, RecordClass qClass) {
        return Question$.MODULE$.apply(name, qType, qClass);
    }

    public static Question parse(ByteIterator it, ByteString msg) {
        return Question$.MODULE$.parse(it, msg);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String name() {
        return this.name;
    }

    public RecordType qType() {
        return this.qType;
    }

    public RecordClass qClass() {
        return this.qClass;
    }

    public void write(ByteStringBuilder out) {
        DomainName$.MODULE$.write(out, this.name());
        RecordTypeSerializer$.MODULE$.write(out, this.qType());
        RecordClassSerializer$.MODULE$.write(out, this.qClass());
    }

    public Question copy(String name, RecordType qType, RecordClass qClass) {
        return new Question(name, qType, qClass);
    }

    public String copy$default$1() {
        return this.name();
    }

    public RecordType copy$default$2() {
        return this.qType();
    }

    public RecordClass copy$default$3() {
        return this.qClass();
    }

    @Override
    public String productPrefix() {
        return "Question";
    }

    @Override
    public int productArity() {
        return 3;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.name();
            }
            case 1: {
                return this.qType();
            }
            case 2: {
                return this.qClass();
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
        return x$1 instanceof Question;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "name";
            }
            case 1: {
                return "qType";
            }
            case 2: {
                return "qClass";
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
        if (!(object instanceof Question)) return false;
        boolean bl = true;
        if (!bl) return false;
        Question question = (Question)x$1;
        String string = this.name();
        String string2 = question.name();
        if (string == null) {
            if (string2 != null) {
                return false;
            }
        } else if (!string.equals(string2)) return false;
        RecordType recordType = this.qType();
        RecordType recordType2 = question.qType();
        if (recordType == null) {
            if (recordType2 != null) {
                return false;
            }
        } else if (!((Object)recordType).equals(recordType2)) return false;
        RecordClass recordClass = this.qClass();
        RecordClass recordClass2 = question.qClass();
        if (recordClass == null) {
            if (recordClass2 == null) return true;
            return false;
        } else {
            if (!((Object)recordClass).equals(recordClass2)) return false;
            return true;
        }
    }

    public Question(String name, RecordType qType, RecordClass qClass) {
        this.name = name;
        this.qType = qType;
        this.qClass = qClass;
        Product.$init$(this);
    }
}

