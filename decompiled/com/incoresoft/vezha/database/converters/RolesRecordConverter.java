/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database.converters;

import com.alibaba.fastjson.JSON;
import com.google.inject.Inject;
import com.incoresoft.vezha.generated.tables.records.RolesRecord;
import com.incoresoft.vezha.models.role.RoleImpl;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RolesRecordConverter
implements Converter<RolesRecord, RoleImpl> {
    private static final Logger log = LoggerFactory.getLogger(RolesRecordConverter.class);

    @Override
    public RoleImpl from(RolesRecord record) {
        RoleImpl role = new RoleImpl();
        role.setId(record.getId());
        role.setDescription(record.getDescription());
        role.setRoleName(record.getRoleName());
        role.setPermissions(JSON.parseArray(record.getPermissions()).stream().map(Object::toString).collect(Collectors.toSet()));
        role.setClientId(record.getClientId());
        return role;
    }

    @Override
    public RolesRecord to(RoleImpl item) {
        RolesRecord record = new RolesRecord();
        record.setId(item.getId());
        record.setRoleName(item.getName());
        record.setDescription(item.getDescription());
        record.setPermissions(JSON.toJSONString(item.getPermissions()));
        record.setClientId(item.getClientId());
        return record;
    }

    @Override
    @NotNull
    public Class<RolesRecord> fromType() {
        return RolesRecord.class;
    }

    @Override
    @NotNull
    public Class<RoleImpl> toType() {
        return RoleImpl.class;
    }

    @Inject
    public RolesRecordConverter() {
    }
}

