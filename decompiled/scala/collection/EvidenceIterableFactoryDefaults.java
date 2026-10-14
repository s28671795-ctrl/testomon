/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.EvidenceIterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.mutable.Builder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m3qa\u0002\u0005\u0011\u0002\u0007\u0005Q\u0002C\u00038\u0001\u0011\u0005\u0001\bC\u0003=\u0001\u0019EQ\bC\u0003H\u0001\u0019M\u0001\nC\u0003K\u0001\u0011E3\nC\u0003R\u0001\u0011E#\u000bC\u0003Z\u0001\u0011\u0005#LA\u0010Fm&$WM\\2f\u0013R,'/\u00192mK\u001a\u000b7\r^8ss\u0012+g-Y;miNT!!\u0003\u0006\u0002\u0015\r|G\u000e\\3di&|gNC\u0001\f\u0003\u0015\u00198-\u00197b\u0007\u0001)BAD\r$\u0005N\u0019\u0001aD\n\u0011\u0005A\tR\"\u0001\u0006\n\u0005IQ!AB!osJ+g\rE\u0003\u0015+]\u0011C&D\u0001\t\u0013\t1\u0002BA\u0006Ji\u0016\u0014\u0018M\u00197f\u001fB\u001c\bC\u0001\r\u001a\u0019\u0001!aA\u0007\u0001\u0005\u0006\u0004Y\"!A!\u0012\u0005qy\u0002C\u0001\t\u001e\u0013\tq\"BA\u0004O_RD\u0017N\\4\u0011\u0005A\u0001\u0013BA\u0011\u000b\u0005\r\te.\u001f\t\u00031\r\"a\u0001\n\u0001\u0005\u0006\u0004)#AA\"D+\t1\u0013&\u0005\u0002\u001dOA)A#\u0006\u0015#WA\u0011\u0001$\u000b\u0003\u0006U\r\u0012\ra\u0007\u0002\u0002qB\u0019\u0001d\t\u0015\u0011\u0007a\u0019SF\u000b\u0002\u0018]-\nq\u0006\u0005\u00021k5\t\u0011G\u0003\u00023g\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0003i)\t!\"\u00198o_R\fG/[8o\u0013\t1\u0014GA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fa\u0001J5oSR$C#A\u001d\u0011\u0005AQ\u0014BA\u001e\u000b\u0005\u0011)f.\u001b;\u0002/\u00154\u0018\u000eZ3oG\u0016LE/\u001a:bE2,g)Y2u_JLX#\u0001 \u0011\tQy$%Q\u0005\u0003\u0001\"\u0011q#\u0012<jI\u0016t7-Z%uKJ\f'\r\\3GC\u000e$xN]=\u0011\u0005a\u0011E!B\"\u0001\u0005\u0004!%AA#w+\tYR\tB\u0003G\u0005\n\u00071D\u0001\u0003`I\u0011\u001a\u0014\u0001E5uKJ\f'\r\\3Fm&$WM\\2f+\u0005I\u0005c\u0001\rC[\u0005aaM]8n'B,7-\u001b4jGR\u0011A\u0006\u0014\u0005\u0006\u001b\u0012\u0001\rAT\u0001\u0005G>dG\u000eE\u0002\u0015\u001f6J!\u0001\u0015\u0005\u0003\u0019%#XM]1cY\u0016|enY3\u0002%9,wo\u00159fG&4\u0017n\u0019\"vS2$WM]\u000b\u0002'B!AkV\u0017-\u001b\u0005)&B\u0001,\t\u0003\u001diW\u000f^1cY\u0016L!\u0001W+\u0003\u000f\t+\u0018\u000e\u001c3fe\u0006)Q-\u001c9usV\tA\u0006")
public interface EvidenceIterableFactoryDefaults<A, CC extends IterableOps<Object, CC, CC>, Ev>
extends IterableOps<A, CC, CC> {
    public EvidenceIterableFactory<CC, Ev> evidenceIterableFactory();

    public Ev iterableEvidence();

    public static /* synthetic */ IterableOps fromSpecific$(EvidenceIterableFactoryDefaults $this, IterableOnce coll) {
        return $this.fromSpecific(coll);
    }

    @Override
    default public CC fromSpecific(IterableOnce<A> coll) {
        return (CC)((IterableOps)this.evidenceIterableFactory().from(coll, this.iterableEvidence()));
    }

    public static /* synthetic */ Builder newSpecificBuilder$(EvidenceIterableFactoryDefaults $this) {
        return $this.newSpecificBuilder();
    }

    @Override
    default public Builder<A, CC> newSpecificBuilder() {
        return this.evidenceIterableFactory().newBuilder(this.iterableEvidence());
    }

    public static /* synthetic */ IterableOps empty$(EvidenceIterableFactoryDefaults $this) {
        return $this.empty();
    }

    @Override
    default public CC empty() {
        return (CC)((IterableOps)this.evidenceIterableFactory().empty(this.iterableEvidence()));
    }

    public static void $init$(EvidenceIterableFactoryDefaults $this) {
    }
}

