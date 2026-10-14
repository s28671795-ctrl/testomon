/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.InPort;
import akka.stream.MaterializationContext;
import akka.stream.OutPort;
import akka.stream.Shape;
import akka.stream.impl.PhaseIsland;
import akka.stream.impl.PhasedFusingActorMaterializer;
import akka.stream.impl.SourceModule;
import akka.stream.impl.StreamLayout;
import org.reactivestreams.Publisher;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Ma!B\u0006\r\u0005A\u0011\u0002\u0002\u0003\u0015\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0016\t\u00115\u0002!\u0011!Q\u0001\n9BQ!\u000f\u0001\u0005\u0002iBQA\u0010\u0001\u0005B}BQ\u0001\u0011\u0001\u0005B\u0005CQ!\u0018\u0001\u0005ByCQ!\u0018\u0001\u0005B9DQA\u001e\u0001\u0005B]DQA\u001f\u0001\u0005BmDq!!\u0001\u0001\t\u0003\n\u0019AA\tT_V\u00148-Z'pIVdW\r\u00155bg\u0016T!!\u0004\b\u0002\t%l\u0007\u000f\u001c\u0006\u0003\u001fA\taa\u001d;sK\u0006l'\"A\t\u0002\t\u0005\\7.Y\n\u0004\u0001MI\u0002C\u0001\u000b\u0018\u001b\u0005)\"\"\u0001\f\u0002\u000bM\u001c\u0017\r\\1\n\u0005a)\"AB!osJ+g\rE\u0002\u001b7ui\u0011\u0001D\u0005\u000391\u00111\u0002\u00155bg\u0016L5\u000f\\1oIB\u0019adI\u0013\u000e\u0003}Q!\u0001I\u0011\u0002\u001fI,\u0017m\u0019;jm\u0016\u001cHO]3b[NT\u0011AI\u0001\u0004_J<\u0017B\u0001\u0013 \u0005%\u0001VO\u00197jg\",'\u000f\u0005\u0002\u0015M%\u0011q%\u0006\u0002\u0004\u0003:L\u0018\u0001D7bi\u0016\u0014\u0018.\u00197ju\u0016\u00148\u0001\u0001\t\u00035-J!\u0001\f\u0007\u0003;AC\u0017m]3e\rV\u001c\u0018N\\4BGR|'/T1uKJL\u0017\r\\5{KJ\f!\"[:mC:$g*Y7f!\tycG\u0004\u00021iA\u0011\u0011'F\u0007\u0002e)\u00111'K\u0001\u0007yI|w\u000e\u001e \n\u0005U*\u0012A\u0002)sK\u0012,g-\u0003\u00028q\t11\u000b\u001e:j]\u001eT!!N\u000b\u0002\rqJg.\u001b;?)\rYD(\u0010\t\u00035\u0001AQ\u0001K\u0002A\u0002)BQ!L\u0002A\u00029\nAA\\1nKV\ta&A\tnCR,'/[1mSj,\u0017\t^8nS\u000e$2AQ#Y!\u0011!2)H\u0013\n\u0005\u0011+\"A\u0002+va2,'\u0007C\u0003G\u000b\u0001\u0007q)A\u0002n_\u0012\u0004B\u0001S)UK9\u0011\u0011j\u0014\b\u0003\u0015:s!aS'\u000f\u0005Eb\u0015\"A\t\n\u0005=\u0001\u0012BA\u0007\u000f\u0013\t\u0001F\"\u0001\u0007TiJ,\u0017-\u001c'bs>,H/\u0003\u0002S'\na\u0011\t^8nS\u000elu\u000eZ;mK*\u0011\u0001\u000b\u0004\t\u0003+Zk\u0011AD\u0005\u0003/:\u0011Qa\u00155ba\u0016DQ!W\u0003A\u0002i\u000b!\"\u0019;ue&\u0014W\u000f^3t!\t)6,\u0003\u0002]\u001d\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u0015\u0005\u001c8/[4o!>\u0014H\u000f\u0006\u0003`E\u001ed\u0007C\u0001\u000ba\u0013\t\tWC\u0001\u0003V]&$\b\"B2\u0007\u0001\u0004!\u0017AA5o!\t)V-\u0003\u0002g\u001d\t1\u0011J\u001c)peRDQ\u0001\u001b\u0004A\u0002%\fAa\u001d7piB\u0011AC[\u0005\u0003WV\u00111!\u00138u\u0011\u0015ig\u00011\u0001\u001e\u0003\u0015awnZ5d)\u0011yv\u000e^;\t\u000bA<\u0001\u0019A9\u0002\u0007=,H\u000f\u0005\u0002Ve&\u00111O\u0004\u0002\b\u001fV$\bk\u001c:u\u0011\u0015Aw\u00011\u0001j\u0011\u0015iw\u00011\u0001\u001e\u0003=\u0019'/Z1uKB+(\r\\5tQ\u0016\u0014HcA\u000fys\")\u0001\u000f\u0003a\u0001c\")Q\u000e\u0003a\u0001;\u0005iA/Y6f!V\u0014G.[:iKJ$Ba\u0018?~\u007f\")\u0001.\u0003a\u0001S\")a0\u0003a\u0001;\u0005I\u0001/\u001e2mSNDWM\u001d\u0005\u00063&\u0001\rAW\u0001\u000e_:L5\u000f\\1oIJ+\u0017\rZ=\u0015\u0003}C3\u0001AA\u0004!\u0011\tI!a\u0004\u000e\u0005\u0005-!bAA\u0007!\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005E\u00111\u0002\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e")
@InternalApi
public final class SourceModulePhase
implements PhaseIsland<Publisher<Object>> {
    private final PhasedFusingActorMaterializer materializer;
    private final String islandName;

    @Override
    public String name() {
        return "SourceModule phase";
    }

    @Override
    public Tuple2<Publisher<Object>, Object> materializeAtomic(StreamLayout.AtomicModule<Shape, Object> mod, Attributes attributes) {
        return ((SourceModule)mod).create(new MaterializationContext(this.materializer, attributes, new StringBuilder(1).append(this.islandName).append("-").append(attributes.nameOrDefault(attributes.nameOrDefault$default$1())).toString()));
    }

    @Override
    public void assignPort(InPort in, int slot, Publisher<Object> logic) {
    }

    @Override
    public void assignPort(OutPort out, int slot, Publisher<Object> logic) {
    }

    @Override
    public Publisher<Object> createPublisher(OutPort out, Publisher<Object> logic) {
        return logic;
    }

    @Override
    public void takePublisher(int slot, Publisher<Object> publisher, Attributes attributes) {
        throw new UnsupportedOperationException("A Source cannot take a Publisher");
    }

    @Override
    public void onIslandReady() {
    }

    public SourceModulePhase(PhasedFusingActorMaterializer materializer, String islandName) {
        this.materializer = materializer;
        this.islandName = islandName;
    }
}

