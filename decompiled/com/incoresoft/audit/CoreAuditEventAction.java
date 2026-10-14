/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.audit;

import com.incoresoft.audit.CoreAuditEventCategory;
import com.incoresoft.middleware.audit.AuditEventAction;
import com.incoresoft.middleware.audit.AuditEventCategory;
import java.util.Objects;
import java.util.UUID;

public enum CoreAuditEventAction implements AuditEventAction
{
    PLUGIN_INSTALLED((AuditEventCategory)CoreAuditEventCategory.MARKETPLACE_MANAGEMENT, "4a8228cc-1074-42e3-a8f3-500b664baca9"),
    PLUGIN_UPDATED((AuditEventCategory)CoreAuditEventCategory.MARKETPLACE_MANAGEMENT, "18a3a1fc-8bb1-4747-84d8-b6623d344434"),
    PLUGIN_ENABLED((AuditEventCategory)CoreAuditEventCategory.MARKETPLACE_MANAGEMENT, "3cc50c94-98c7-4d8b-91c2-b181efec5f50"),
    PLUGIN_DISABLED((AuditEventCategory)CoreAuditEventCategory.MARKETPLACE_MANAGEMENT, "5a523900-301d-4fb9-a949-65141338a1b6"),
    PLUGIN_DELETED((AuditEventCategory)CoreAuditEventCategory.MARKETPLACE_MANAGEMENT, "6a3d45a2-57b8-475d-9676-fdf92a5dc683"),
    PLUGIN_REPAIRED((AuditEventCategory)CoreAuditEventCategory.MARKETPLACE_MANAGEMENT, "527f89de-f155-42ff-aeaa-d3da85876f61"),
    CLIENT_ADDED((AuditEventCategory)CoreAuditEventCategory.USER_MANAGEMENT, "74b9488d-8d69-41ee-8d72-6e05e7f5b49e"),
    CLIENT_UPDATED((AuditEventCategory)CoreAuditEventCategory.USER_MANAGEMENT, "d3369a25-795b-49c7-8132-695645461d2a"),
    CLIENT_DELETED((AuditEventCategory)CoreAuditEventCategory.USER_MANAGEMENT, "d789e994-a7a9-49ee-8295-0d5e25b3d456"),
    ROLE_ADDED((AuditEventCategory)CoreAuditEventCategory.USER_MANAGEMENT, "0ed4bdbc-21f3-4252-91ba-cc598f716c32"),
    ROLE_UPDATED((AuditEventCategory)CoreAuditEventCategory.USER_MANAGEMENT, "79d0db5c-1202-4a5e-a820-0b302fc9a124"),
    ROLE_DELETED((AuditEventCategory)CoreAuditEventCategory.USER_MANAGEMENT, "1d09103e-1628-4f3d-8f49-5c0a08b70609"),
    USER_ADDED((AuditEventCategory)CoreAuditEventCategory.USER_MANAGEMENT, "2464767d-edfc-4cf7-8c75-13f90d5b175d"),
    USER_UPDATED((AuditEventCategory)CoreAuditEventCategory.USER_MANAGEMENT, "db2c5a01-543d-452e-9d73-c85d1df43d16"),
    USER_DELETED((AuditEventCategory)CoreAuditEventCategory.USER_MANAGEMENT, "42f7cbd5-5998-4fe2-a13a-03684581f46c"),
    USERS_FETCHED_FROM_LDAP((AuditEventCategory)CoreAuditEventCategory.USER_MANAGEMENT, "8b03b0d3-7f47-454d-ae9c-78a604c82a63"),
    LOGIN((AuditEventCategory)CoreAuditEventCategory.USER_MANAGEMENT, "2cb7445c-3fee-4ef5-80b0-ca92d11a03f9"),
    LOGOUT((AuditEventCategory)CoreAuditEventCategory.USER_MANAGEMENT, "e245ec90-1c5e-4a9c-9f4a-7b2a2d10c0f6"),
    LICENSE_ACTIVATED((AuditEventCategory)CoreAuditEventCategory.LICENSE_MANAGEMENT, "1e0f2fe1-fb95-48b4-895a-8f194c95f46a"),
    LICENSE_DEACTIVATED((AuditEventCategory)CoreAuditEventCategory.LICENSE_MANAGEMENT, "306f786d-7226-4d13-8548-7569e5beb498"),
    SYSTEM_UPDATED((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "6c8bb0c5-cc7f-405b-aec3-6edf0021adbb"),
    CLEANING_SETTINGS_CHANGED((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "3d79055e-f997-45b6-909c-afd4c9f56990"),
    BACKUP_CREATED((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "c794d85c-e148-4c3c-aeed-c18129f4a72d"),
    BACKUP_DELETED((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "f861c137-84ab-497e-9f08-ff42903236ed"),
    BACKUP_RESTORED((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "544ff5fd-8037-42f4-883a-f133dae6c9b5"),
    BACKUP_DOWNLOADED((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "97ea72fb-2a0b-4879-be76-9a742a595e14");

    private final UUID id;
    private final AuditEventCategory category;

    private CoreAuditEventAction(AuditEventCategory category, UUID id) {
        this.id = id;
        this.category = Objects.requireNonNull(category);
    }

    private CoreAuditEventAction(AuditEventCategory category, String id) {
        this.id = UUID.fromString(id);
        this.category = Objects.requireNonNull(category);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name();
    }

    public String toString() {
        return this.getName().toLowerCase();
    }

    @Override
    public AuditEventCategory getCategory() {
        return this.category;
    }
}

