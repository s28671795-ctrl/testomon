/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database;

import com.google.inject.Inject;
import com.incoresoft.vezha.database.converters.UsersRecordConverter;
import com.incoresoft.vezha.generated.tables.Users;
import com.incoresoft.vezha.generated.tables.records.UsersRecord;
import com.incoresoft.vezha.models.users.UserImpl;
import java.time.Instant;
import java.util.List;
import org.jooq.DSLContext;

public class UsersRepository {
    public static final int ARCHIVED = -1;
    private final DSLContext create;
    private final UsersRecordConverter converter;

    public List<UserImpl> getUsers() {
        return this.create.selectFrom(Users.USERS).where(Users.USERS.STATUS.notEqual(-1)).stream().map(this.converter::from).toList();
    }

    public List<UserImpl> getArchivedUsers() {
        return this.create.selectFrom(Users.USERS).where(Users.USERS.STATUS.equal(-1)).stream().map(this.converter::from).toList();
    }

    public void insert(UserImpl item) {
        UsersRecord record = this.create.newRecord(Users.USERS);
        record.from((Object)this.converter.to(item));
        record.reset(Users.USERS.ID);
        record.insert();
        item.setId(record.getId());
    }

    public void update(UserImpl item) {
        UsersRecord record = this.create.newRecord(Users.USERS);
        record.from((Object)this.converter.to(item));
        if (item.getPassword().isBlank()) {
            record.reset(Users.USERS.PASSWORD);
        }
        record.changed(Users.USERS.ID, false);
        record.update();
    }

    public void archiveUser(int id) {
        UsersRecord record = this.create.newRecord(Users.USERS);
        record.setStatus(-1);
        record.setId(id);
        record.update(Users.USERS.STATUS);
    }

    public void updateLastLogin(int id, String clientIp) {
        UsersRecord record = this.create.newRecord(Users.USERS);
        record.setLastIp(clientIp);
        record.setLastLogin(Instant.now());
        record.setId(id);
        record.update(Users.USERS.LAST_IP, Users.USERS.LAST_LOGIN);
    }

    @Inject
    public UsersRepository(DSLContext create, UsersRecordConverter converter) {
        this.create = create;
        this.converter = converter;
    }
}

