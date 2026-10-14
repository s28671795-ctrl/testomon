/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database;

import com.google.inject.Inject;
import com.incoresoft.plugin.commons.database.CRUDRepository;
import com.incoresoft.plugin.commons.params.PaginationParams;
import com.incoresoft.plugin.commons.params.StartEndDateParams;
import com.incoresoft.vezha.backup.BackupInfo;
import com.incoresoft.vezha.database.converters.BackupInfosConverter;
import com.incoresoft.vezha.generated.Tables;
import com.incoresoft.vezha.generated.tables.records.BackupsRecord;
import io.javalin.http.NotFoundResponse;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.OrderField;
import org.jooq.TableLike;

public class BackupInfosRepository
extends CRUDRepository<BackupsRecord, BackupInfo> {
    @Inject
    public BackupInfosRepository(@NotNull DSLContext dslContext, @NotNull BackupInfosConverter converter) {
        super(dslContext, converter, Tables.BACKUPS, Tables.BACKUPS.ID, Tables.BACKUPS.FILE_NAME, Tables.BACKUPS.CLIENT_ID);
    }

    public BackupInfo getBackupInfoOrThrow(int id) {
        return (BackupInfo)this.get(id).orElseThrow(NotFoundResponse::new);
    }

    public boolean exists(String fileName) {
        return this.context.selectFrom(this.table).where(this.nameField.equalIgnoreCase(fileName)).fetchOptional().isPresent();
    }

    public Pair<Integer, List<BackupInfo>> getAll(StartEndDateParams dateParams, PaginationParams paginationParams) {
        ArrayList<Condition> conditions = new ArrayList<Condition>();
        if (dateParams.getStartDate() != null) {
            conditions.add(Tables.BACKUPS.CREATED_AT.ge(dateParams.getStartDate()));
        }
        if (dateParams.getEndDate() != null) {
            conditions.add(Tables.BACKUPS.CREATED_AT.le(dateParams.getEndDate()));
        }
        Integer count = this.context.selectCount().from((TableLike<?>)Tables.BACKUPS).where(conditions).fetchOne(0, Integer.class);
        return Pair.of(count, this.getAll(conditions, paginationParams, new OrderField[0]));
    }
}

