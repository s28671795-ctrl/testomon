/*
 * Decompiled with CFR 0.152.
 */
package scala.reflect;

import scala.reflect.OptManifest;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;

public final class NoManifest$
implements OptManifest<Nothing$> {
    public static final NoManifest$ MODULE$ = new NoManifest$();

    public String toString() {
        return "<?>";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(NoManifest$.class);
    }

    private NoManifest$() {
    }
}

