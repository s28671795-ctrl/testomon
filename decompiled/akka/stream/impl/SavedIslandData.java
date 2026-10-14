/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.impl.PhaseIsland;
import akka.stream.impl.SavedIslandData$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple4;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Ud\u0001B\u0010!\r\u001eB\u0001\"\u0010\u0001\u0003\u0016\u0004%\tA\u0010\u0005\t\u0005\u0002\u0011\t\u0012)A\u0005\u007f!A1\t\u0001BK\u0002\u0013\u0005a\b\u0003\u0005E\u0001\tE\t\u0015!\u0003@\u0011!)\u0005A!f\u0001\n\u0003q\u0004\u0002\u0003$\u0001\u0005#\u0005\u000b\u0011B \t\u0011\u001d\u0003!Q3A\u0005\u0002!C\u0001\u0002\u0015\u0001\u0003\u0012\u0003\u0006I!\u0013\u0005\u0006#\u0002!\tA\u0015\u0005\b1\u0002\t\t\u0011\"\u0001Z\u0011\u001dq\u0006!%A\u0005\u0002}CqA\u001b\u0001\u0012\u0002\u0013\u0005q\fC\u0004l\u0001E\u0005I\u0011A0\t\u000f1\u0004\u0011\u0013!C\u0001[\"9q\u000eAA\u0001\n\u0003\u0002\bbB=\u0001\u0003\u0003%\tA\u0010\u0005\bu\u0002\t\t\u0011\"\u0001|\u0011\u001dq\b!!A\u0005B}D\u0011\"!\u0004\u0001\u0003\u0003%\t!a\u0004\t\u0013\u0005e\u0001!!A\u0005B\u0005m\u0001\"CA\u0010\u0001\u0005\u0005I\u0011IA\u0011\u0011%\t\u0019\u0003AA\u0001\n\u0003\n)\u0003C\u0005\u0002(\u0001\t\t\u0011\"\u0011\u0002*\u001dI\u0011Q\u0006\u0011\u0002\u0002#%\u0011q\u0006\u0004\t?\u0001\n\t\u0011#\u0003\u00022!1\u0011+\u0007C\u0001\u0003\u0013B\u0011\"a\t\u001a\u0003\u0003%)%!\n\t\u0013\u0005-\u0013$!A\u0005\u0002\u00065\u0003\"CA,3\u0005\u0005I\u0011QA-\u0011%\tY'GA\u0001\n\u0013\tiGA\bTCZ,G-S:mC:$G)\u0019;b\u0015\t\t#%\u0001\u0003j[Bd'BA\u0012%\u0003\u0019\u0019HO]3b[*\tQ%\u0001\u0003bW.\f7\u0001A\n\u0005\u0001!r\u0013\u0007\u0005\u0002*Y5\t!FC\u0001,\u0003\u0015\u00198-\u00197b\u0013\ti#F\u0001\u0004B]f\u0014VM\u001a\t\u0003S=J!\u0001\r\u0016\u0003\u000fA\u0013x\u000eZ;diB\u0011!G\u000f\b\u0003gar!\u0001N\u001c\u000e\u0003UR!A\u000e\u0014\u0002\rq\u0012xn\u001c;?\u0013\u0005Y\u0013BA\u001d+\u0003\u001d\u0001\u0018mY6bO\u0016L!a\u000f\u001f\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005eR\u0013AE5tY\u0006tGm\u00127pE\u0006dwJ\u001a4tKR,\u0012a\u0010\t\u0003S\u0001K!!\u0011\u0016\u0003\u0007%sG/A\njg2\fg\u000eZ$m_\n\fGn\u00144gg\u0016$\b%A\tmCN$h+[:ji\u0016$wJ\u001a4tKR\f!\u0003\\1tiZK7/\u001b;fI>3gm]3uA\u0005a1o[5qa\u0016$7\u000b\\8ug\u0006i1o[5qa\u0016$7\u000b\\8ug\u0002\nQ\u0001\u001d5bg\u0016,\u0012!\u0013\t\u0004\u0015.kU\"\u0001\u0011\n\u00051\u0003#a\u0003)iCN,\u0017j\u001d7b]\u0012\u0004\"!\u000b(\n\u0005=S#aA!os\u00061\u0001\u000f[1tK\u0002\na\u0001P5oSRtD#B*U+Z;\u0006C\u0001&\u0001\u0011\u0015i\u0014\u00021\u0001@\u0011\u0015\u0019\u0015\u00021\u0001@\u0011\u0015)\u0015\u00021\u0001@\u0011\u00159\u0015\u00021\u0001J\u0003\u0011\u0019w\u000e]=\u0015\u000bMS6\fX/\t\u000fuR\u0001\u0013!a\u0001\u007f!91I\u0003I\u0001\u0002\u0004y\u0004bB#\u000b!\u0003\u0005\ra\u0010\u0005\b\u000f*\u0001\n\u00111\u0001J\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012\u0001\u0019\u0016\u0003\u007f\u0005\\\u0013A\u0019\t\u0003G\"l\u0011\u0001\u001a\u0006\u0003K\u001a\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005\u001dT\u0013AC1o]>$\u0018\r^5p]&\u0011\u0011\u000e\u001a\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00134\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIQ*\u0012A\u001c\u0016\u0003\u0013\u0006\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#A9\u0011\u0005I<X\"A:\u000b\u0005Q,\u0018\u0001\u00027b]\u001eT\u0011A^\u0001\u0005U\u00064\u0018-\u0003\u0002yg\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002Ny\"9Q0EA\u0001\u0002\u0004y\u0014a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002\u0002A)\u00111AA\u0005\u001b6\u0011\u0011Q\u0001\u0006\u0004\u0003\u000fQ\u0013AC2pY2,7\r^5p]&!\u00111BA\u0003\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005E\u0011q\u0003\t\u0004S\u0005M\u0011bAA\u000bU\t9!i\\8mK\u0006t\u0007bB?\u0014\u0003\u0003\u0005\r!T\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\rF\u0002r\u0003;Aq! \u000b\u0002\u0002\u0003\u0007q(\u0001\u0005iCND7i\u001c3f)\u0005y\u0014\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003E\fa!Z9vC2\u001cH\u0003BA\t\u0003WAq!`\f\u0002\u0002\u0003\u0007Q*A\bTCZ,G-S:mC:$G)\u0019;b!\tQ\u0015dE\u0003\u001a\u0003g\ty\u0004E\u0005\u00026\u0005mrhP J'6\u0011\u0011q\u0007\u0006\u0004\u0003sQ\u0013a\u0002:v]RLW.Z\u0005\u0005\u0003{\t9DA\tBEN$(/Y2u\rVt7\r^5p]R\u0002B!!\u0011\u0002H5\u0011\u00111\t\u0006\u0004\u0003\u000b*\u0018AA5p\u0013\rY\u00141\t\u000b\u0003\u0003_\tQ!\u00199qYf$\u0012bUA(\u0003#\n\u0019&!\u0016\t\u000bub\u0002\u0019A \t\u000b\rc\u0002\u0019A \t\u000b\u0015c\u0002\u0019A \t\u000b\u001dc\u0002\u0019A%\u0002\u000fUt\u0017\r\u001d9msR!\u00111LA4!\u0015I\u0013QLA1\u0013\r\tyF\u000b\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u000f%\n\u0019gP @\u0013&\u0019\u0011Q\r\u0016\u0003\rQ+\b\u000f\\35\u0011!\tI'HA\u0001\u0002\u0004\u0019\u0016a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011q\u000e\t\u0004e\u0006E\u0014bAA:g\n1qJ\u00196fGR\u0004")
public final class SavedIslandData
implements Product,
Serializable {
    private final int islandGlobalOffset;
    private final int lastVisitedOffset;
    private final int skippedSlots;
    private final PhaseIsland<Object> phase;

    public static Option<Tuple4<Object, Object, Object, PhaseIsland<Object>>> unapply(SavedIslandData x$0) {
        return SavedIslandData$.MODULE$.unapply(x$0);
    }

    public static SavedIslandData apply(int islandGlobalOffset, int lastVisitedOffset, int skippedSlots, PhaseIsland<Object> phase) {
        return SavedIslandData$.MODULE$.apply(islandGlobalOffset, lastVisitedOffset, skippedSlots, phase);
    }

    public static Function1<Tuple4<Object, Object, Object, PhaseIsland<Object>>, SavedIslandData> tupled() {
        return SavedIslandData$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Object, Function1<Object, Function1<PhaseIsland<Object>, SavedIslandData>>>> curried() {
        return SavedIslandData$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int islandGlobalOffset() {
        return this.islandGlobalOffset;
    }

    public int lastVisitedOffset() {
        return this.lastVisitedOffset;
    }

    public int skippedSlots() {
        return this.skippedSlots;
    }

    public PhaseIsland<Object> phase() {
        return this.phase;
    }

    public SavedIslandData copy(int islandGlobalOffset, int lastVisitedOffset, int skippedSlots, PhaseIsland<Object> phase) {
        return new SavedIslandData(islandGlobalOffset, lastVisitedOffset, skippedSlots, phase);
    }

    public int copy$default$1() {
        return this.islandGlobalOffset();
    }

    public int copy$default$2() {
        return this.lastVisitedOffset();
    }

    public int copy$default$3() {
        return this.skippedSlots();
    }

    public PhaseIsland<Object> copy$default$4() {
        return this.phase();
    }

    @Override
    public String productPrefix() {
        return "SavedIslandData";
    }

    @Override
    public int productArity() {
        return 4;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return BoxesRunTime.boxToInteger(this.islandGlobalOffset());
            }
            case 1: {
                return BoxesRunTime.boxToInteger(this.lastVisitedOffset());
            }
            case 2: {
                return BoxesRunTime.boxToInteger(this.skippedSlots());
            }
            case 3: {
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
        return x$1 instanceof SavedIslandData;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "islandGlobalOffset";
            }
            case 1: {
                return "lastVisitedOffset";
            }
            case 2: {
                return "skippedSlots";
            }
            case 3: {
                return "phase";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.islandGlobalOffset());
        n = Statics.mix(n, this.lastVisitedOffset());
        n = Statics.mix(n, this.skippedSlots());
        n = Statics.mix(n, Statics.anyHash(this.phase()));
        return Statics.finalizeHash(n, 4);
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
        if (!(object instanceof SavedIslandData)) return false;
        boolean bl = true;
        if (!bl) return false;
        SavedIslandData savedIslandData = (SavedIslandData)x$1;
        if (this.islandGlobalOffset() != savedIslandData.islandGlobalOffset()) return false;
        if (this.lastVisitedOffset() != savedIslandData.lastVisitedOffset()) return false;
        if (this.skippedSlots() != savedIslandData.skippedSlots()) return false;
        PhaseIsland<Object> phaseIsland = this.phase();
        PhaseIsland<Object> phaseIsland2 = savedIslandData.phase();
        if (phaseIsland != null) {
            if (!phaseIsland.equals(phaseIsland2)) return false;
            return true;
        }
        if (phaseIsland2 == null) return true;
        return false;
    }

    public SavedIslandData(int islandGlobalOffset, int lastVisitedOffset, int skippedSlots, PhaseIsland<Object> phase) {
        this.islandGlobalOffset = islandGlobalOffset;
        this.lastVisitedOffset = lastVisitedOffset;
        this.skippedSlots = skippedSlots;
        this.phase = phase;
        Product.$init$(this);
    }
}

