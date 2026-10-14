/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.database;

import com.google.inject.Inject;
import com.incoresoft.generated.Tables;
import com.incoresoft.generated.tables.records.ServiceCertificateInfoRecord;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.TableLike;

public class CertificateInfoRepository {
    private static final int SINGLETON_RECORD_ID = 1;
    private final DSLContext context;

    public Optional<String> getCertificatePassword() {
        return this.context.select(Tables.SERVICE_CERTIFICATE_INFO.CERTIFICATE_PASSWORD).from((TableLike<?>)Tables.SERVICE_CERTIFICATE_INFO).where(Tables.SERVICE_CERTIFICATE_INFO.ID.eq(1)).fetchOptional(Tables.SERVICE_CERTIFICATE_INFO.CERTIFICATE_PASSWORD);
    }

    public void setCertificatePassword(String password) {
        ServiceCertificateInfoRecord record = this.context.newRecord(Tables.SERVICE_CERTIFICATE_INFO);
        record.setId(1);
        record.setCertificatePassword(password);
        this.context.insertInto(Tables.SERVICE_CERTIFICATE_INFO).set(record).onDuplicateKeyUpdate().set(record).execute();
    }

    public void resetCertificatePassword() {
        this.setCertificatePassword(null);
    }

    @Inject
    public CertificateInfoRepository(DSLContext context) {
        this.context = context;
    }
}

