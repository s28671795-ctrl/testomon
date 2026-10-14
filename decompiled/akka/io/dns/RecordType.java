/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.io.dns.RecordType$;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\teb\u0001B%K\u0005FC\u0001b\u001a\u0001\u0003\u0016\u0004%\t\u0001\u001b\u0005\tY\u0002\u0011\t\u0012)A\u0005S\"AQ\u000e\u0001BK\u0002\u0013\u0005a\u000e\u0003\u0005x\u0001\tE\t\u0015!\u0003p\u0011\u0015A\b\u0001\"\u0001z\u0011\u001dq\b!!A\u0005\u0002}D\u0011\"!\u0002\u0001#\u0003%\t!a\u0002\t\u0013\u0005u\u0001!%A\u0005\u0002\u0005}\u0001\"CA\u0012\u0001\u0005\u0005I\u0011IA\u0013\u0011%\t)\u0004AA\u0001\n\u0003\t9\u0004C\u0005\u0002@\u0001\t\t\u0011\"\u0001\u0002B!I\u0011Q\n\u0001\u0002\u0002\u0013\u0005\u0013q\n\u0005\n\u0003;\u0002\u0011\u0011!C\u0001\u0003?B\u0011\"!\u001b\u0001\u0003\u0003%\t%a\u001b\t\u0013\u0005=\u0004!!A\u0005B\u0005E\u0004\"CA:\u0001\u0005\u0005I\u0011IA;\u0011%\t9\bAA\u0001\n\u0003\nIhB\u0004\u0002~)C\t!a \u0007\r%S\u0005\u0012AAA\u0011\u0019A8\u0003\"\u0001\u0002\f\"I\u0011QR\nC\u0002\u00135\u0011q\u0012\u0005\t\u0003/\u001b\u0002\u0015!\u0004\u0002\u0012\"A\u0011\u0011T\n\u0005\u00029\u000bY\nC\u0004\u0002 N!\t!!)\t\u000f\u0005M6\u0003\"\u0003\u00026\"I\u00111X\nC\u0002\u0013\u0015\u0011Q\u0018\u0005\b\u0003\u007f\u001b\u0002\u0015!\u0004{\u0011%\t\tm\u0005b\u0001\n\u000b\ti\fC\u0004\u0002DN\u0001\u000bQ\u0002>\t\u0013\u0005\u00157C1A\u0005\u0006\u0005u\u0006bBAd'\u0001\u0006iA\u001f\u0005\n\u0003\u0013\u001c\"\u0019!C\u0003\u0003{Cq!a3\u0014A\u00035!\u0010C\u0005\u0002NN\u0011\r\u0011\"\u0002\u0002>\"9\u0011qZ\n!\u0002\u001bQ\b\"CAi'\t\u0007IQAA_\u0011\u001d\t\u0019n\u0005Q\u0001\u000eiD\u0011\"!6\u0014\u0005\u0004%)!!0\t\u000f\u0005]7\u0003)A\u0007u\"I\u0011\u0011\\\nC\u0002\u0013\u0015\u0011Q\u0018\u0005\b\u00037\u001c\u0002\u0015!\u0004{\u0011%\tin\u0005b\u0001\n\u000b\ti\fC\u0004\u0002`N\u0001\u000bQ\u0002>\t\u0013\u0005\u00058C1A\u0005\u0006\u0005u\u0006bBAr'\u0001\u0006iA\u001f\u0005\n\u0003K\u001c\"\u0019!C\u0003\u0003{Cq!a:\u0014A\u00035!\u0010C\u0005\u0002jN\u0011\r\u0011\"\u0002\u0002>\"9\u00111^\n!\u0002\u001bQ\b\"CAw'\t\u0007IQAA_\u0011\u001d\tyo\u0005Q\u0001\u000eiD\u0011\"!=\u0014\u0005\u0004%)!!0\t\u000f\u0005M8\u0003)A\u0007u\"I\u0011Q_\nC\u0002\u0013\u0015\u0011Q\u0018\u0005\b\u0003o\u001c\u0002\u0015!\u0004{\u0011%\tIp\u0005b\u0001\n\u000b\ti\fC\u0004\u0002|N\u0001\u000bQ\u0002>\t\u0013\u0005u8C1A\u0005\u0006\u0005u\u0006bBA\u0000'\u0001\u0006iA\u001f\u0005\n\u0005\u0003\u0019\"\u0019!C\u0003\u0003{CqAa\u0001\u0014A\u00035!\u0010C\u0005\u0003\u0006M\u0011\r\u0011\"\u0002\u0002>\"9!qA\n!\u0002\u001bQ\b\"\u0003B\u0005'\t\u0007IQAA_\u0011\u001d\u0011Ya\u0005Q\u0001\u000eiD\u0011B!\u0004\u0014\u0005\u0004%)!!0\t\u000f\t=1\u0003)A\u0007u\"I!\u0011C\nC\u0002\u0013\u0015\u0011Q\u0018\u0005\b\u0005'\u0019\u0002\u0015!\u0004{\u0011%\tyjEA\u0001\n\u0003\u0013)\u0002C\u0005\u0003\u001cM\t\t\u0011\"!\u0003\u001e!I!qF\n\u0002\u0002\u0013%!\u0011\u0007\u0002\u000b%\u0016\u001cwN\u001d3UsB,'BA&M\u0003\r!gn\u001d\u0006\u0003\u001b:\u000b!![8\u000b\u0003=\u000bA!Y6lC\u000e\u00011\u0003\u0002\u0001S1n\u0003\"a\u0015,\u000e\u0003QS\u0011!V\u0001\u0006g\u000e\fG.Y\u0005\u0003/R\u0013a!\u00118z%\u00164\u0007CA*Z\u0013\tQFKA\u0004Qe>$Wo\u0019;\u0011\u0005q#gBA/c\u001d\tq\u0016-D\u0001`\u0015\t\u0001\u0007+\u0001\u0004=e>|GOP\u0005\u0002+&\u00111\rV\u0001\ba\u0006\u001c7.Y4f\u0013\t)gM\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002d)\u0006!1m\u001c3f+\u0005I\u0007CA*k\u0013\tYGKA\u0003TQ>\u0014H/A\u0003d_\u0012,\u0007%\u0001\u0003oC6,W#A8\u0011\u0005A$hBA9s!\tqF+\u0003\u0002t)\u00061\u0001K]3eK\u001aL!!\u001e<\u0003\rM#(/\u001b8h\u0015\t\u0019H+A\u0003oC6,\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0004url\bCA>\u0001\u001b\u0005Q\u0005\"B4\u0006\u0001\u0004I\u0007\"B7\u0006\u0001\u0004y\u0017\u0001B2paf$RA_A\u0001\u0003\u0007Aqa\u001a\u0004\u0011\u0002\u0003\u0007\u0011\u000eC\u0004n\rA\u0005\t\u0019A8\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011\u0011\u0011\u0002\u0016\u0004S\u0006-1FAA\u0007!\u0011\ty!!\u0007\u000e\u0005\u0005E!\u0002BA\n\u0003+\t\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005]A+\u0001\u0006b]:|G/\u0019;j_:LA!a\u0007\u0002\u0012\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\u0011\u0011\u0011\u0005\u0016\u0004_\u0006-\u0011!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0002(A!\u0011\u0011FA\u001a\u001b\t\tYC\u0003\u0003\u0002.\u0005=\u0012\u0001\u00027b]\u001eT!!!\r\u0002\t)\fg/Y\u0005\u0004k\u0006-\u0012\u0001\u00049s_\u0012,8\r^!sSRLXCAA\u001d!\r\u0019\u00161H\u0005\u0004\u0003{!&aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$H\u0003BA\"\u0003\u0013\u00022aUA#\u0013\r\t9\u0005\u0016\u0002\u0004\u0003:L\b\"CA&\u0017\u0005\u0005\t\u0019AA\u001d\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011\u0011\u000b\t\u0007\u0003'\nI&a\u0011\u000e\u0005\u0005U#bAA,)\u0006Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u0005m\u0013Q\u000b\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0002b\u0005\u001d\u0004cA*\u0002d%\u0019\u0011Q\r+\u0003\u000f\t{w\u000e\\3b]\"I\u00111J\u0007\u0002\u0002\u0003\u0007\u00111I\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0002(\u00055\u0004\"CA&\u001d\u0005\u0005\t\u0019AA\u001d\u0003!A\u0017m\u001d5D_\u0012,GCAA\u001d\u0003!!xn\u0015;sS:<GCAA\u0014\u0003\u0019)\u0017/^1mgR!\u0011\u0011MA>\u0011%\tY%EA\u0001\u0002\u0004\t\u0019%\u0001\u0006SK\u000e|'\u000f\u001a+za\u0016\u0004\"a_\n\u0014\tM\u0011\u00161\u0011\t\u0005\u0003\u000b\u000bI)\u0004\u0002\u0002\b*\u0019Q*a\f\n\u0007\u0015\f9\t\u0006\u0002\u0002\u0000\u0005YAn\\8lkB$\u0016M\u00197f+\t\t\t\n\u0005\u0003T\u0003'S\u0018bAAK)\n)\u0011I\u001d:bs\u0006aAn\\8lkB$\u0016M\u00197fA\u00051An\\8lkB$2A_AO\u0011\u00199w\u00031\u0001\u0002:\u0005)\u0011\r\u001d9msR!\u00111UAX!\u0015\t)+a+{\u001b\t\t9KC\u0002\u0002*:\u000bA!\u001e;jY&!\u0011QVAT\u0005%y\u0005\u000f^5p]Z\u000bG\u000e\u0003\u0004\u00022b\u0001\r![\u0001\u0003S\u0012\f\u0001B]3hSN$XM\u001d\u000b\u0004u\u0006]\u0006BBA]3\u0001\u0007!0A\u0001u\u0003\u0005\tU#\u0001>\u0002\u0005\u0005\u0003\u0013A\u0001(T\u0003\rq5\u000bI\u0001\u0003\u001b\u0012\u000b1!\u0014#!\u0003\tie)A\u0002N\r\u0002\nQa\u0011(B\u001b\u0016\u000baa\u0011(B\u001b\u0016\u0003\u0013aA*P\u0003\u0006!1kT!!\u0003\ti%)A\u0002N\u0005\u0002\n!!T$\u0002\u00075;\u0005%\u0001\u0002N%\u0006\u0019QJ\u0015\u0011\u0002\t9+F\nT\u0001\u0006\u001dVcE\nI\u0001\u0004/.\u001b\u0016\u0001B,L'\u0002\n1\u0001\u0015+S\u0003\u0011\u0001FK\u0015\u0011\u0002\u000b!KeJR(\u0002\r!KeJR(!\u0003\u0015i\u0015J\u0014$P\u0003\u0019i\u0015J\u0014$PA\u0005\u0011Q\nW\u0001\u0004\u001bb\u0003\u0013a\u0001+Y)\u0006!A\u000b\u0017+!\u0003\u0011\t\u0015)Q!\u0002\u000b\u0005\u000b\u0015)\u0011\u0011\u0002\u0007M\u0013f+\u0001\u0003T%Z\u0003\u0013\u0001B!Y\rJ\u000bQ!\u0011-G%\u0002\nQ!T!J\u0019\n\u000ba!T!J\u0019\n\u0003\u0013!B'B\u00132\u000b\u0015AB'B\u00132\u000b\u0005%\u0001\u0005X\u00132#5)\u0011*E\u0003%9\u0016\n\u0014#D\u0003J#\u0005\u0005F\u0003{\u0005/\u0011I\u0002C\u0003h\r\u0002\u0007\u0011\u000eC\u0003n\r\u0002\u0007q.A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\t}!1\u0006\t\u0006'\n\u0005\"QE\u0005\u0004\u0005G!&AB(qi&|g\u000eE\u0003T\u0005OIw.C\u0002\u0003*Q\u0013a\u0001V;qY\u0016\u0014\u0004\u0002\u0003B\u0017\u000f\u0006\u0005\t\u0019\u0001>\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u00034A!\u0011\u0011\u0006B\u001b\u0013\u0011\u00119$a\u000b\u0003\r=\u0013'.Z2u\u0001")
public final class RecordType
implements Product,
Serializable {
    private final short code;
    private final String name;

    public static Option<Tuple2<Object, String>> unapply(RecordType x$0) {
        return RecordType$.MODULE$.unapply(x$0);
    }

    public static RecordType apply(short code, String name) {
        return RecordType$.MODULE$.apply(code, name);
    }

    public static RecordType WILDCARD() {
        return RecordType$.MODULE$.WILDCARD();
    }

    public static RecordType MAILA() {
        return RecordType$.MODULE$.MAILA();
    }

    public static RecordType MAILB() {
        return RecordType$.MODULE$.MAILB();
    }

    public static RecordType AXFR() {
        return RecordType$.MODULE$.AXFR();
    }

    public static RecordType SRV() {
        return RecordType$.MODULE$.SRV();
    }

    public static RecordType AAAA() {
        return RecordType$.MODULE$.AAAA();
    }

    public static RecordType TXT() {
        return RecordType$.MODULE$.TXT();
    }

    public static RecordType MX() {
        return RecordType$.MODULE$.MX();
    }

    public static RecordType MINFO() {
        return RecordType$.MODULE$.MINFO();
    }

    public static RecordType HINFO() {
        return RecordType$.MODULE$.HINFO();
    }

    public static RecordType PTR() {
        return RecordType$.MODULE$.PTR();
    }

    public static RecordType WKS() {
        return RecordType$.MODULE$.WKS();
    }

    public static RecordType NULL() {
        return RecordType$.MODULE$.NULL();
    }

    public static RecordType MR() {
        return RecordType$.MODULE$.MR();
    }

    public static RecordType MG() {
        return RecordType$.MODULE$.MG();
    }

    public static RecordType MB() {
        return RecordType$.MODULE$.MB();
    }

    public static RecordType SOA() {
        return RecordType$.MODULE$.SOA();
    }

    public static RecordType CNAME() {
        return RecordType$.MODULE$.CNAME();
    }

    public static RecordType MF() {
        return RecordType$.MODULE$.MF();
    }

    public static RecordType MD() {
        return RecordType$.MODULE$.MD();
    }

    public static RecordType NS() {
        return RecordType$.MODULE$.NS();
    }

    public static RecordType A() {
        return RecordType$.MODULE$.A();
    }

    public static RecordType apply(short id) {
        return RecordType$.MODULE$.apply(id);
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

    public RecordType copy(short code, String name) {
        return new RecordType(code, name);
    }

    public short copy$default$1() {
        return this.code();
    }

    public String copy$default$2() {
        return this.name();
    }

    @Override
    public String productPrefix() {
        return "RecordType";
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
        return x$1 instanceof RecordType;
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
        if (!(object instanceof RecordType)) return false;
        boolean bl = true;
        if (!bl) return false;
        RecordType recordType = (RecordType)x$1;
        if (this.code() != recordType.code()) return false;
        String string = this.name();
        String string2 = recordType.name();
        if (string != null) {
            if (!string.equals(string2)) return false;
            return true;
        }
        if (string2 == null) return true;
        return false;
    }

    public RecordType(short code, String name) {
        this.code = code;
        this.name = name;
        Product.$init$(this);
    }
}

