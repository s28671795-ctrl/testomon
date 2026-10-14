/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database;

import com.google.inject.Inject;
import com.incoresoft.middleware.model.role.Role;
import com.incoresoft.vezha.database.converters.RolesRecordConverter;
import com.incoresoft.vezha.generated.tables.Roles;
import com.incoresoft.vezha.generated.tables.records.RolesRecord;
import com.incoresoft.vezha.models.role.RoleImpl;
import java.util.List;
import org.jooq.DSLContext;

public class RolesRepository {
    private final DSLContext create;
    private final RolesRecordConverter converter;

    public List<RoleImpl> getRoles() {
        return this.create.selectFrom(Roles.ROLES).stream().map(this.converter::from).toList();
    }

    public RoleImpl upsertRole(Role item) {
        RolesRecord record = this.create.newRecord(Roles.ROLES);
        record.from((Object)this.converter.to((RoleImpl)item));
        if (item.getId() == 0) {
            record.reset(Roles.ROLES.ID);
            record.insert();
        } else {
            record.changed(Roles.ROLES.ID, false);
            record.update();
        }
        return this.converter.from(record);
    }

    public void deleteRole(int id) {
        RolesRecord record = this.create.newRecord(Roles.ROLES);
        record.setId(id);
        record.delete();
    }

    @Inject
    public RolesRepository(DSLContext create, RolesRecordConverter converter) {
        this.create = create;
        this.converter = converter;
    }
}

