/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.mdc;

import java.io.Closeable;
import liquibase.Scope;

public class MdcObject
implements Closeable {
    private final String key;
    private final Object value;

    public MdcObject(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public void close() {
        Scope.getCurrentScope().getMdcManager().remove(this.key);
    }
}

