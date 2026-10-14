/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.collection.BuildFrom;
import scala.collection.EvidenceIterableFactory;
import scala.collection.Factory;
import scala.runtime.ModuleSerializationProxy;

public final class EvidenceIterableFactory$
implements Serializable {
    public static final EvidenceIterableFactory$ MODULE$ = new EvidenceIterableFactory$();

    public <Ev, A, CC> Factory<A, CC> toFactory(EvidenceIterableFactory<CC, Ev> factory2, Ev evidence$14) {
        return new EvidenceIterableFactory.ToFactory(factory2, evidence$14);
    }

    public <Ev, A, CC> BuildFrom<Object, A, CC> toBuildFrom(EvidenceIterableFactory<CC, Ev> factory2, Ev evidence$16) {
        return new EvidenceIterableFactory.EvidenceIterableFactoryToBuildFrom(factory2, evidence$16);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(EvidenceIterableFactory$.class);
    }

    private EvidenceIterableFactory$() {
    }
}

