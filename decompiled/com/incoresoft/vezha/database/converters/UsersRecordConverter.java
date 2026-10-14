/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database.converters;

import com.alibaba.fastjson.JSON;
import com.google.inject.Inject;
import com.incoresoft.dto.user.DbUserSettingsDTO;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.middleware.model.role.RolesManager;
import com.incoresoft.user.UserSettings;
import com.incoresoft.vezha.generated.tables.records.UsersRecord;
import com.incoresoft.vezha.models.users.UserImpl;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsersRecordConverter
implements Converter<UsersRecord, UserImpl> {
    private static final Logger log = LoggerFactory.getLogger(UsersRecordConverter.class);
    private final RolesManager rolesManager;

    @Inject
    public UsersRecordConverter(@NotNull RolesManager rolesManager) {
        this.rolesManager = Objects.requireNonNull(rolesManager);
    }

    @Override
    public UserImpl from(UsersRecord record) {
        UserImpl user = new UserImpl();
        user.setId(record.getId());
        user.setEmail(record.getEmail());
        user.setFullname(record.getFullname());
        user.setPassword(record.getPassword());
        user.setLastIP(Optional.ofNullable(record.getLastIp()).orElse("0.0.0.0"));
        user.setLastLogin(Optional.ofNullable(record.getLastLogin()).orElse(Instant.EPOCH));
        user.setIpParams(record.getIpParams());
        user.setRoleIds(Set.copyOf(Serialization.readAsList(record.getRoleIds(), Integer.class)));
        user.setStatus(record.getStatus());
        user.setCreatedAt(record.getCreatedAt());
        user.setSettings(new UserSettings(Serialization.readValue(record.getSettings(), DbUserSettingsDTO.class)));
        user.setClientId(record.getClientId());
        user.setType(record.getType());
        user.setRoleManager(this.rolesManager);
        try {
            user.setTimezone(ZoneId.of(record.getTimezone()));
        }
        catch (Exception e) {
            user.setTimezone(ZoneId.systemDefault());
        }
        return user;
    }

    @Override
    public UsersRecord to(UserImpl item) {
        UsersRecord record = new UsersRecord();
        record.setId(item.getId());
        record.setEmail(item.getEmail());
        record.setPassword(item.getPassword());
        record.setRoleIds(Serialization.writeValueAsString(item.getRoleIds()));
        record.setFullname(item.getFullname());
        record.setClientId(item.getClientId());
        record.setStatus(item.getStatus());
        Optional.ofNullable(item.getSettings()).map(DbUserSettingsDTO::new).map(JSON::toJSONString).ifPresent(record::setSettings);
        Optional.ofNullable(item.getLastIP()).ifPresent(record::setLastIp);
        record.setLastLogin(Objects.requireNonNullElse(item.getLastLogin(), Instant.EPOCH));
        record.setIpParams(Objects.requireNonNullElse(item.getIpParams(), ""));
        record.setType(item.getType());
        record.setTimezone(Optional.ofNullable(item.getTimezone()).orElse(ZoneId.systemDefault()).getId());
        return record;
    }

    @Override
    @NotNull
    public Class<UsersRecord> fromType() {
        return UsersRecord.class;
    }

    @Override
    @NotNull
    public Class<UserImpl> toType() {
        return UserImpl.class;
    }
}

