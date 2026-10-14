/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables.records;

import com.incoresoft.vezha.generated.tables.Users;
import java.time.Instant;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record15;
import org.jooq.Row15;
import org.jooq.impl.UpdatableRecordImpl;

public class UsersRecord
extends UpdatableRecordImpl<UsersRecord>
implements Record15<Integer, String, String, String, String, Instant, String, Integer, Instant, String, Integer, String, String, String, String> {
    private static final long serialVersionUID = 1L;

    public void setId(Integer value) {
        this.set(0, value);
    }

    public Integer getId() {
        return (Integer)this.get(0);
    }

    public void setEmail(String value) {
        this.set(1, value);
    }

    public String getEmail() {
        return (String)this.get(1);
    }

    public void setFullname(String value) {
        this.set(2, value);
    }

    public String getFullname() {
        return (String)this.get(2);
    }

    public void setPassword(String value) {
        this.set(3, value);
    }

    public String getPassword() {
        return (String)this.get(3);
    }

    public void setLastIp(String value) {
        this.set(4, value);
    }

    public String getLastIp() {
        return (String)this.get(4);
    }

    public void setLastLogin(Instant value) {
        this.set(5, value);
    }

    public Instant getLastLogin() {
        return (Instant)this.get(5);
    }

    public void setIpParams(String value) {
        this.set(6, value);
    }

    public String getIpParams() {
        return (String)this.get(6);
    }

    public void setStatus(Integer value) {
        this.set(7, value);
    }

    public Integer getStatus() {
        return (Integer)this.get(7);
    }

    public void setCreatedAt(Instant value) {
        this.set(8, value);
    }

    public Instant getCreatedAt() {
        return (Instant)this.get(8);
    }

    public void setSettings(String value) {
        this.set(9, value);
    }

    public String getSettings() {
        return (String)this.get(9);
    }

    public void setClientId(Integer value) {
        this.set(10, value);
    }

    public Integer getClientId() {
        return (Integer)this.get(10);
    }

    public void setClientInfo(String value) {
        this.set(11, value);
    }

    public String getClientInfo() {
        return (String)this.get(11);
    }

    public void setType(String value) {
        this.set(12, value);
    }

    public String getType() {
        return (String)this.get(12);
    }

    public void setTimezone(String value) {
        this.set(13, value);
    }

    public String getTimezone() {
        return (String)this.get(13);
    }

    public void setRoleIds(String value) {
        this.set(14, value);
    }

    public String getRoleIds() {
        return (String)this.get(14);
    }

    @Override
    public Record1<Integer> key() {
        return (Record1)super.key();
    }

    @Override
    public Row15<Integer, String, String, String, String, Instant, String, Integer, Instant, String, Integer, String, String, String, String> fieldsRow() {
        return (Row15)super.fieldsRow();
    }

    @Override
    public Row15<Integer, String, String, String, String, Instant, String, Integer, Instant, String, Integer, String, String, String, String> valuesRow() {
        return (Row15)super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Users.USERS.ID;
    }

    @Override
    public Field<String> field2() {
        return Users.USERS.EMAIL;
    }

    @Override
    public Field<String> field3() {
        return Users.USERS.FULLNAME;
    }

    @Override
    public Field<String> field4() {
        return Users.USERS.PASSWORD;
    }

    @Override
    public Field<String> field5() {
        return Users.USERS.LAST_IP;
    }

    @Override
    public Field<Instant> field6() {
        return Users.USERS.LAST_LOGIN;
    }

    @Override
    public Field<String> field7() {
        return Users.USERS.IP_PARAMS;
    }

    @Override
    public Field<Integer> field8() {
        return Users.USERS.STATUS;
    }

    @Override
    public Field<Instant> field9() {
        return Users.USERS.CREATED_AT;
    }

    @Override
    public Field<String> field10() {
        return Users.USERS.SETTINGS;
    }

    @Override
    public Field<Integer> field11() {
        return Users.USERS.CLIENT_ID;
    }

    @Override
    public Field<String> field12() {
        return Users.USERS.CLIENT_INFO;
    }

    @Override
    public Field<String> field13() {
        return Users.USERS.TYPE;
    }

    @Override
    public Field<String> field14() {
        return Users.USERS.TIMEZONE;
    }

    @Override
    public Field<String> field15() {
        return Users.USERS.ROLE_IDS;
    }

    @Override
    public Integer component1() {
        return this.getId();
    }

    @Override
    public String component2() {
        return this.getEmail();
    }

    @Override
    public String component3() {
        return this.getFullname();
    }

    @Override
    public String component4() {
        return this.getPassword();
    }

    @Override
    public String component5() {
        return this.getLastIp();
    }

    @Override
    public Instant component6() {
        return this.getLastLogin();
    }

    @Override
    public String component7() {
        return this.getIpParams();
    }

    @Override
    public Integer component8() {
        return this.getStatus();
    }

    @Override
    public Instant component9() {
        return this.getCreatedAt();
    }

    @Override
    public String component10() {
        return this.getSettings();
    }

    @Override
    public Integer component11() {
        return this.getClientId();
    }

    @Override
    public String component12() {
        return this.getClientInfo();
    }

    @Override
    public String component13() {
        return this.getType();
    }

    @Override
    public String component14() {
        return this.getTimezone();
    }

    @Override
    public String component15() {
        return this.getRoleIds();
    }

    @Override
    public Integer value1() {
        return this.getId();
    }

    @Override
    public String value2() {
        return this.getEmail();
    }

    @Override
    public String value3() {
        return this.getFullname();
    }

    @Override
    public String value4() {
        return this.getPassword();
    }

    @Override
    public String value5() {
        return this.getLastIp();
    }

    @Override
    public Instant value6() {
        return this.getLastLogin();
    }

    @Override
    public String value7() {
        return this.getIpParams();
    }

    @Override
    public Integer value8() {
        return this.getStatus();
    }

    @Override
    public Instant value9() {
        return this.getCreatedAt();
    }

    @Override
    public String value10() {
        return this.getSettings();
    }

    @Override
    public Integer value11() {
        return this.getClientId();
    }

    @Override
    public String value12() {
        return this.getClientInfo();
    }

    @Override
    public String value13() {
        return this.getType();
    }

    @Override
    public String value14() {
        return this.getTimezone();
    }

    @Override
    public String value15() {
        return this.getRoleIds();
    }

    public UsersRecord value1(Integer value) {
        this.setId(value);
        return this;
    }

    public UsersRecord value2(String value) {
        this.setEmail(value);
        return this;
    }

    public UsersRecord value3(String value) {
        this.setFullname(value);
        return this;
    }

    public UsersRecord value4(String value) {
        this.setPassword(value);
        return this;
    }

    public UsersRecord value5(String value) {
        this.setLastIp(value);
        return this;
    }

    public UsersRecord value6(Instant value) {
        this.setLastLogin(value);
        return this;
    }

    public UsersRecord value7(String value) {
        this.setIpParams(value);
        return this;
    }

    public UsersRecord value8(Integer value) {
        this.setStatus(value);
        return this;
    }

    public UsersRecord value9(Instant value) {
        this.setCreatedAt(value);
        return this;
    }

    public UsersRecord value10(String value) {
        this.setSettings(value);
        return this;
    }

    public UsersRecord value11(Integer value) {
        this.setClientId(value);
        return this;
    }

    public UsersRecord value12(String value) {
        this.setClientInfo(value);
        return this;
    }

    public UsersRecord value13(String value) {
        this.setType(value);
        return this;
    }

    public UsersRecord value14(String value) {
        this.setTimezone(value);
        return this;
    }

    public UsersRecord value15(String value) {
        this.setRoleIds(value);
        return this;
    }

    public UsersRecord values(Integer value1, String value2, String value3, String value4, String value5, Instant value6, String value7, Integer value8, Instant value9, String value10, Integer value11, String value12, String value13, String value14, String value15) {
        this.value1(value1);
        this.value2(value2);
        this.value3(value3);
        this.value4(value4);
        this.value5(value5);
        this.value6(value6);
        this.value7(value7);
        this.value8(value8);
        this.value9(value9);
        this.value10(value10);
        this.value11(value11);
        this.value12(value12);
        this.value13(value13);
        this.value14(value14);
        this.value15(value15);
        return this;
    }

    public UsersRecord() {
        super(Users.USERS);
    }

    public UsersRecord(Integer id, String email, String fullname, String password, String lastIp, Instant lastLogin, String ipParams, Integer status, Instant createdAt, String settings, Integer clientId, String clientInfo, String type, String timezone, String roleIds) {
        super(Users.USERS);
        this.setId(id);
        this.setEmail(email);
        this.setFullname(fullname);
        this.setPassword(password);
        this.setLastIp(lastIp);
        this.setLastLogin(lastLogin);
        this.setIpParams(ipParams);
        this.setStatus(status);
        this.setCreatedAt(createdAt);
        this.setSettings(settings);
        this.setClientId(clientId);
        this.setClientInfo(clientInfo);
        this.setType(type);
        this.setTimezone(timezone);
        this.setRoleIds(roleIds);
        this.resetChangedOnNotNull();
    }
}

