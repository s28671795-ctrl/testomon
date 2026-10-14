/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.impl.PhaseIsland;
import akka.stream.impl.SegmentInfo$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Tuple5;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005]e\u0001\u0002\u0012$\r*B\u0001\u0002\u0011\u0001\u0003\u0016\u0004%\t!\u0011\u0005\t\u000b\u0002\u0011\t\u0012)A\u0005\u0005\"Aa\t\u0001BK\u0002\u0013\u0005\u0011\t\u0003\u0005H\u0001\tE\t\u0015!\u0003C\u0011!A\u0005A!f\u0001\n\u0003\t\u0005\u0002C%\u0001\u0005#\u0005\u000b\u0011\u0002\"\t\u0011)\u0003!Q3A\u0005\u0002\u0005C\u0001b\u0013\u0001\u0003\u0012\u0003\u0006IA\u0011\u0005\t\u0019\u0002\u0011)\u001a!C\u0001\u001b\"AQ\u000b\u0001B\tB\u0003%a\nC\u0003W\u0001\u0011\u0005q\u000bC\u0003_\u0001\u0011\u0005s\fC\u0004i\u0001\u0005\u0005I\u0011A5\t\u000f=\u0004\u0011\u0013!C\u0001a\"91\u0010AI\u0001\n\u0003\u0001\bb\u0002?\u0001#\u0003%\t\u0001\u001d\u0005\b{\u0002\t\n\u0011\"\u0001q\u0011\u001dq\b!%A\u0005\u0002}D\u0011\"a\u0001\u0001\u0003\u0003%\t%!\u0002\t\u0011\u0005U\u0001!!A\u0005\u0002\u0005C\u0011\"a\u0006\u0001\u0003\u0003%\t!!\u0007\t\u0013\u0005}\u0001!!A\u0005B\u0005\u0005\u0002\"CA\u0018\u0001\u0005\u0005I\u0011AA\u0019\u0011%\tY\u0004AA\u0001\n\u0003\ni\u0004C\u0005\u0002B\u0001\t\t\u0011\"\u0011\u0002D!I\u0011Q\t\u0001\u0002\u0002\u0013\u0005\u0013qI\u0004\n\u0003\u0017\u001a\u0013\u0011!E\u0005\u0003\u001b2\u0001BI\u0012\u0002\u0002#%\u0011q\n\u0005\u0007-r!\t!a\u001a\t\u0011yc\u0012\u0011!C#\u0003SB\u0011\"a\u001b\u001d\u0003\u0003%\t)!\u001c\t\u0013\u0005eD$!A\u0005\u0002\u0006m\u0004\"CAG9\u0005\u0005I\u0011BAH\u0005-\u0019VmZ7f]RLeNZ8\u000b\u0005\u0011*\u0013\u0001B5na2T!AJ\u0014\u0002\rM$(/Z1n\u0015\u0005A\u0013\u0001B1lW\u0006\u001c\u0001a\u0005\u0003\u0001WE\"\u0004C\u0001\u00170\u001b\u0005i#\"\u0001\u0018\u0002\u000bM\u001c\u0017\r\\1\n\u0005Aj#AB!osJ+g\r\u0005\u0002-e%\u00111'\f\u0002\b!J|G-^2u!\t)TH\u0004\u00027w9\u0011qGO\u0007\u0002q)\u0011\u0011(K\u0001\u0007yI|w\u000e\u001e \n\u00039J!\u0001P\u0017\u0002\u000fA\f7m[1hK&\u0011ah\u0010\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003y5\n!c\u001a7pE\u0006d\u0017n\u001d7b]\u0012|eMZ:fiV\t!\t\u0005\u0002-\u0007&\u0011A)\f\u0002\u0004\u0013:$\u0018aE4m_\n\fG.[:mC:$wJ\u001a4tKR\u0004\u0013A\u00027f]\u001e$\b.A\u0004mK:<G\u000f\u001b\u0011\u0002!\u001ddwNY1m\u0005\u0006\u001cXm\u00144gg\u0016$\u0018!E4m_\n\fGNQ1tK>3gm]3uA\u0005\u0011\"/\u001a7bi&4XMQ1tK>3gm]3u\u0003M\u0011X\r\\1uSZ,')Y:f\u001f\u001a47/\u001a;!\u0003\u0015\u0001\b.Y:f+\u0005q\u0005cA(Q%6\t1%\u0003\u0002RG\tY\u0001\u000b[1tK&\u001bH.\u00198e!\ta3+\u0003\u0002U[\t\u0019\u0011I\\=\u0002\rAD\u0017m]3!\u0003\u0019a\u0014N\\5u}Q1\u0001,\u0017.\\9v\u0003\"a\u0014\u0001\t\u000b\u0001[\u0001\u0019\u0001\"\t\u000b\u0019[\u0001\u0019\u0001\"\t\u000b![\u0001\u0019\u0001\"\t\u000b)[\u0001\u0019\u0001\"\t\u000b1[\u0001\u0019\u0001(\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012\u0001\u0019\t\u0003C\u0016t!AY2\u0011\u0005]j\u0013B\u00013.\u0003\u0019\u0001&/\u001a3fM&\u0011am\u001a\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005\u0011l\u0013\u0001B2paf$b\u0001\u00176lY6t\u0007b\u0002!\u000e!\u0003\u0005\rA\u0011\u0005\b\r6\u0001\n\u00111\u0001C\u0011\u001dAU\u0002%AA\u0002\tCqAS\u0007\u0011\u0002\u0003\u0007!\tC\u0004M\u001bA\u0005\t\u0019\u0001(\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\t\u0011O\u000b\u0002Ce.\n1\u000f\u0005\u0002us6\tQO\u0003\u0002wo\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003q6\n!\"\u00198o_R\fG/[8o\u0013\tQXOA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%i\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012*TCAA\u0001U\tq%/A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003\u000f\u0001B!!\u0003\u0002\u00145\u0011\u00111\u0002\u0006\u0005\u0003\u001b\ty!\u0001\u0003mC:<'BAA\t\u0003\u0011Q\u0017M^1\n\u0007\u0019\fY!\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0007I\u000bY\u0002\u0003\u0005\u0002\u001eU\t\t\u00111\u0001C\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u00111\u0005\t\u0006\u0003K\tYCU\u0007\u0003\u0003OQ1!!\u000b.\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003[\t9C\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA\u001a\u0003s\u00012\u0001LA\u001b\u0013\r\t9$\f\u0002\b\u0005>|G.Z1o\u0011!\tibFA\u0001\u0002\u0004\u0011\u0016A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!a\u0002\u0002@!A\u0011Q\u0004\r\u0002\u0002\u0003\u0007!)\u0001\u0005iCND7i\u001c3f)\u0005\u0011\u0015AB3rk\u0006d7\u000f\u0006\u0003\u00024\u0005%\u0003\u0002CA\u000f5\u0005\u0005\t\u0019\u0001*\u0002\u0017M+w-\\3oi&sgm\u001c\t\u0003\u001fr\u0019R\u0001HA)\u0003;\u0002\"\"a\u0015\u0002Z\t\u0013%I\u0011(Y\u001b\t\t)FC\u0002\u0002X5\nqA];oi&lW-\u0003\u0003\u0002\\\u0005U#!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8okA!\u0011qLA3\u001b\t\t\tG\u0003\u0003\u0002d\u0005=\u0011AA5p\u0013\rq\u0014\u0011\r\u000b\u0003\u0003\u001b\"\"!a\u0002\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0017a\u000by'!\u001d\u0002t\u0005U\u0014q\u000f\u0005\u0006\u0001~\u0001\rA\u0011\u0005\u0006\r~\u0001\rA\u0011\u0005\u0006\u0011~\u0001\rA\u0011\u0005\u0006\u0015~\u0001\rA\u0011\u0005\u0006\u0019~\u0001\rAT\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\ti(!#\u0011\u000b1\ny(a!\n\u0007\u0005\u0005UF\u0001\u0004PaRLwN\u001c\t\tY\u0005\u0015%I\u0011\"C\u001d&\u0019\u0011qQ\u0017\u0003\rQ+\b\u000f\\36\u0011!\tY\tIA\u0001\u0002\u0004A\u0016a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011\u0011\u0013\t\u0005\u0003\u0013\t\u0019*\u0003\u0003\u0002\u0016\u0006-!AB(cU\u0016\u001cG\u000f")
public final class SegmentInfo
implements Product,
Serializable {
    private final int globalislandOffset;
    private final int length;
    private final int globalBaseOffset;
    private final int relativeBaseOffset;
    private final PhaseIsland<Object> phase;

    public static Option<Tuple5<Object, Object, Object, Object, PhaseIsland<Object>>> unapply(SegmentInfo x$0) {
        return SegmentInfo$.MODULE$.unapply(x$0);
    }

    public static SegmentInfo apply(int globalislandOffset, int length, int globalBaseOffset, int relativeBaseOffset, PhaseIsland<Object> phase) {
        return SegmentInfo$.MODULE$.apply(globalislandOffset, length, globalBaseOffset, relativeBaseOffset, phase);
    }

    public static Function1<Tuple5<Object, Object, Object, Object, PhaseIsland<Object>>, SegmentInfo> tupled() {
        return SegmentInfo$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Object, Function1<Object, Function1<Object, Function1<PhaseIsland<Object>, SegmentInfo>>>>> curried() {
        return SegmentInfo$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int globalislandOffset() {
        return this.globalislandOffset;
    }

    public int length() {
        return this.length;
    }

    public int globalBaseOffset() {
        return this.globalBaseOffset;
    }

    public int relativeBaseOffset() {
        return this.relativeBaseOffset;
    }

    public PhaseIsland<Object> phase() {
        return this.phase;
    }

    public String toString() {
        return StringOps$.MODULE$.stripMargin$extension(Predef$.MODULE$.augmentString(new StringBuilder(185).append("\n       | Segment\n       |  globalislandOffset = ").append(this.globalislandOffset()).append("\n       |  length             = ").append(this.length()).append("\n       |  globalBaseOffset   = ").append(this.globalBaseOffset()).append("\n       |  relativeBaseOffset = ").append(this.relativeBaseOffset()).append("\n       |  phase              = ").append(this.phase()).append("\n       ").toString()));
    }

    public SegmentInfo copy(int globalislandOffset, int length, int globalBaseOffset, int relativeBaseOffset, PhaseIsland<Object> phase) {
        return new SegmentInfo(globalislandOffset, length, globalBaseOffset, relativeBaseOffset, phase);
    }

    public int copy$default$1() {
        return this.globalislandOffset();
    }

    public int copy$default$2() {
        return this.length();
    }

    public int copy$default$3() {
        return this.globalBaseOffset();
    }

    public int copy$default$4() {
        return this.relativeBaseOffset();
    }

    public PhaseIsland<Object> copy$default$5() {
        return this.phase();
    }

    @Override
    public String productPrefix() {
        return "SegmentInfo";
    }

    @Override
    public int productArity() {
        return 5;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return BoxesRunTime.boxToInteger(this.globalislandOffset());
            }
            case 1: {
                return BoxesRunTime.boxToInteger(this.length());
            }
            case 2: {
                return BoxesRunTime.boxToInteger(this.globalBaseOffset());
            }
            case 3: {
                return BoxesRunTime.boxToInteger(this.relativeBaseOffset());
            }
            case 4: {
                return this.phase();
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
        return x$1 instanceof SegmentInfo;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "globalislandOffset";
            }
            case 1: {
                return "length";
            }
            case 2: {
                return "globalBaseOffset";
            }
            case 3: {
                return "relativeBaseOffset";
            }
            case 4: {
                return "phase";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.globalislandOffset());
        n = Statics.mix(n, this.length());
        n = Statics.mix(n, this.globalBaseOffset());
        n = Statics.mix(n, this.relativeBaseOffset());
        n = Statics.mix(n, Statics.anyHash(this.phase()));
        return Statics.finalizeHash(n, 5);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof SegmentInfo)) return false;
        boolean bl = true;
        if (!bl) return false;
        SegmentInfo segmentInfo = (SegmentInfo)x$1;
        if (this.globalislandOffset() != segmentInfo.globalislandOffset()) return false;
        if (this.length() != segmentInfo.length()) return false;
        if (this.globalBaseOffset() != segmentInfo.globalBaseOffset()) return false;
        if (this.relativeBaseOffset() != segmentInfo.relativeBaseOffset()) return false;
        PhaseIsland<Object> phaseIsland = this.phase();
        PhaseIsland<Object> phaseIsland2 = segmentInfo.phase();
        if (phaseIsland != null) {
            if (!phaseIsland.equals(phaseIsland2)) return false;
            return true;
        }
        if (phaseIsland2 == null) return true;
        return false;
    }

    public SegmentInfo(int globalislandOffset, int length, int globalBaseOffset, int relativeBaseOffset, PhaseIsland<Object> phase) {
        this.globalislandOffset = globalislandOffset;
        this.length = length;
        this.globalBaseOffset = globalBaseOffset;
        this.relativeBaseOffset = relativeBaseOffset;
        this.phase = phase;
        Product.$init$(this);
    }
}

