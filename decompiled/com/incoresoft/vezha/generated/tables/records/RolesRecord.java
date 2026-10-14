/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables.records;

import com.incoresoft.vezha.generated.tables.Roles;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;

public class RolesRecord
extends UpdatableRecordImpl<RolesRecord>
implements Record5<Integer, String, String, Integer, String> {
    private static final long serialVersionUID = 1L;

    public void setId(Integer value) {
        this.set(0, value);
    }

    public Integer getId() {
        return (Integer)this.get(0);
    }

    public void setRoleName(String value) {
        this.set(1, value);
    }

    public String getRoleName() {
        return (String)this.get(1);
    }

    public void setPermissions(String value) {
        this.set(2, value);
    }

    public String getPermissions() {
        return (String)this.get(2);
    }

    public void setClientId(Integer value) {
        this.set(3, value);
    }

    public Integer getClientId() {
        return (Integer)this.get(3);
    }

    public void setDescription(String value) {
        this.set(4, value);
    }

    public String getDescription() {
        return (String)this.get(4);
    }

    @Override
    public Record1<Integer> key() {
        return (Record1)super.key();
    }

    @Override
    public Row5<Integer, String, String, Integer, String> fieldsRow() {
        return (Row5)super.fieldsRow();
    }

    @Override
    public Row5<Integer, String, String, Integer, String> valuesRow() {
        return (Row5)super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Roles.ROLES.ID;
    }

    @Override
    public Field<String> field2() {
        return Roles.ROLES.ROLE_NAME;
    }

    @Override
    public Field<String> field3() {
        return Roles.ROLES.PERMISSIONS;
    }

    @Override
    public Field<Integer> field4() {
        return Roles.ROLES.CLIENT_ID;
    }

    @Override
    public Field<String> field5() {
        return Roles.ROLES.DESCRIPTION;
    }

    @Override
    public Integer component1() {
        return this.getId();
    }

    @Override
    public String component2() {
        return this.getRoleName();
    }

    @Override
    public String component3() {
        return this.getPermissions();
    }

    @Override
    public Integer component4() {
        return this.getClientId();
    }

    @Override
    public String component5() {
        return this.getDescription();
    }

    @Override
    public Integer value1() {
        return this.getId();
    }

    @Override
    public String value2() {
        return this.getRoleName();
    }

    @Override
    public String value3() {
        return this.getPermissions();
    }

    @Override
    public Integer value4() {
        return this.getClientId();
    }

    @Override
    public String value5() {
        return this.getDescription();
    }

    public RolesRecord value1(Integer value) {
        this.setId(value);
        return this;
    }

    public RolesRecord value2(String value) {
        this.setRoleName(value);
        return this;
    }

    public RolesRecord value3(String value) {
        this.setPermissions(value);
        return this;
    }

    public RolesRecord value4(Integer value) {
        this.setClientId(value);
        return this;
    }

    public RolesRecord value5(String value) {
        this.setDescription(value);
        return this;
    }

    public RolesRecord values(Integer value1, String value2, String value3, Integer value4, String value5) {
        this.value1(value1);
        this.value2(value2);
        this.value3(value3);
        this.value4(value4);
        this.value5(value5);
        return this;
    }

    public RolesRecord() {
        super(Roles.ROLES);
    }

    public RolesRecord(Integer id, String roleName, String permissions, Integer clientId, String description) {
        super(Roles.ROLES);
        this.setId(id);
        this.setRoleName(roleName);
        this.setPermissions(permissions);
        this.setClientId(clientId);
        this.setDescription(description);
        this.resetChangedOnNotNull();
    }
}

