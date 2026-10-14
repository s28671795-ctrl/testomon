/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.database;

import com.google.inject.Inject;
import com.incoresoft.generated.Tables;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;

public class LicenseDatabaseRepository {
    private final DSLContext dslContext;

    @Inject
    public LicenseDatabaseRepository(@NotNull DSLContext dslContext) {
        this.dslContext = Objects.requireNonNull(dslContext);
    }

    public void store(String licenseKey, String licenseText) {
        this.dslContext.insertInto(Tables.LICENSES).set(Tables.LICENSES.LICENSE_KEY, licenseKey).set(Tables.LICENSES.LICENSE_TEXT, licenseText).onDuplicateKeyUpdate().set(Tables.LICENSES.LICENSE_TEXT, licenseText).execute();
    }

    public void delete(@NotNull String key) {
        this.dslContext.delete(Tables.LICENSES).where(Tables.LICENSES.LICENSE_KEY.eq(key)).execute();
    }

    public Map<String, String> getAll() {
        return this.dslContext.selectFrom(Tables.LICENSES).fetchMap(Tables.LICENSES.LICENSE_KEY, Tables.LICENSES.LICENSE_TEXT);
    }
}

