/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables.records;

import com.incoresoft.vezha.generated.tables.WindowsShareFolderConfigs;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;

public class WindowsShareFolderConfigsRecord
extends UpdatableRecordImpl<WindowsShareFolderConfigsRecord>
implements Record6<Integer, String, String, String, String, String> {
    private static final long serialVersionUID = 1L;

    public void setId(Integer value) {
        this.set(0, value);
    }

    public Integer getId() {
        return (Integer)this.get(0);
    }

    public void setHost(String value) {
        this.set(1, value);
    }

    public String getHost() {
        return (String)this.get(1);
    }

    public void setShareName(String value) {
        this.set(2, value);
    }

    public String getShareName() {
        return (String)this.get(2);
    }

    public void setUsername(String value) {
        this.set(3, value);
    }

    public String getUsername() {
        return (String)this.get(3);
    }

    public void setPassword(String value) {
        this.set(4, value);
    }

    public String getPassword() {
        return (String)this.get(4);
    }

    public void setDomain(String value) {
        this.set(5, value);
    }

    public String getDomain() {
        return (String)this.get(5);
    }

    @Override
    public Record1<Integer> key() {
        return (Record1)super.key();
    }

    @Override
    public Row6<Integer, String, String, String, String, String> fieldsRow() {
        return (Row6)super.fieldsRow();
    }

    @Override
    public Row6<Integer, String, String, String, String, String> valuesRow() {
        return (Row6)super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return WindowsShareFolderConfigs.WINDOWS_SHARE_FOLDER_CONFIGS.ID;
    }

    @Override
    public Field<String> field2() {
        return WindowsShareFolderConfigs.WINDOWS_SHARE_FOLDER_CONFIGS.HOST;
    }

    @Override
    public Field<String> field3() {
        return WindowsShareFolderConfigs.WINDOWS_SHARE_FOLDER_CONFIGS.SHARE_NAME;
    }

    @Override
    public Field<String> field4() {
        return WindowsShareFolderConfigs.WINDOWS_SHARE_FOLDER_CONFIGS.USERNAME;
    }

    @Override
    public Field<String> field5() {
        return WindowsShareFolderConfigs.WINDOWS_SHARE_FOLDER_CONFIGS.PASSWORD;
    }

    @Override
    public Field<String> field6() {
        return WindowsShareFolderConfigs.WINDOWS_SHARE_FOLDER_CONFIGS.DOMAIN;
    }

    @Override
    public Integer component1() {
        return this.getId();
    }

    @Override
    public String component2() {
        return this.getHost();
    }

    @Override
    public String component3() {
        return this.getShareName();
    }

    @Override
    public String component4() {
        return this.getUsername();
    }

    @Override
    public String component5() {
        return this.getPassword();
    }

    @Override
    public String component6() {
        return this.getDomain();
    }

    @Override
    public Integer value1() {
        return this.getId();
    }

    @Override
    public String value2() {
        return this.getHost();
    }

    @Override
    public String value3() {
        return this.getShareName();
    }

    @Override
    public String value4() {
        return this.getUsername();
    }

    @Override
    public String value5() {
        return this.getPassword();
    }

    @Override
    public String value6() {
        return this.getDomain();
    }

    public WindowsShareFolderConfigsRecord value1(Integer value) {
        this.setId(value);
        return this;
    }

    public WindowsShareFolderConfigsRecord value2(String value) {
        this.setHost(value);
        return this;
    }

    public WindowsShareFolderConfigsRecord value3(String value) {
        this.setShareName(value);
        return this;
    }

    public WindowsShareFolderConfigsRecord value4(String value) {
        this.setUsername(value);
        return this;
    }

    public WindowsShareFolderConfigsRecord value5(String value) {
        this.setPassword(value);
        return this;
    }

    public WindowsShareFolderConfigsRecord value6(String value) {
        this.setDomain(value);
        return this;
    }

    public WindowsShareFolderConfigsRecord values(Integer value1, String value2, String value3, String value4, String value5, String value6) {
        this.value1(value1);
        this.value2(value2);
        this.value3(value3);
        this.value4(value4);
        this.value5(value5);
        this.value6(value6);
        return this;
    }

    public WindowsShareFolderConfigsRecord() {
        super(WindowsShareFolderConfigs.WINDOWS_SHARE_FOLDER_CONFIGS);
    }

    public WindowsShareFolderConfigsRecord(Integer id, String host2, String shareName, String username, String password, String domain) {
        super(WindowsShareFolderConfigs.WINDOWS_SHARE_FOLDER_CONFIGS);
        this.setId(id);
        this.setHost(host2);
        this.setShareName(shareName);
        this.setUsername(username);
        this.setPassword(password);
        this.setDomain(domain);
        this.resetChangedOnNotNull();
    }
}

