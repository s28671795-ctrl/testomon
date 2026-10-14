/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.audit;

import com.incoresoft.audit.CoreAuditEventCategory;
import com.incoresoft.middleware.audit.AuditEventAction;
import com.incoresoft.middleware.audit.AuditEventCategory;
import com.incoresoft.vezha.audit.VezhaAuditEventCategory;
import java.util.Objects;
import java.util.UUID;

public enum VezhaAuditEventAction implements AuditEventAction
{
    VIDEOSTREAM_ADDED((AuditEventCategory)VezhaAuditEventCategory.STREAMS_MANAGEMENT, "fd395181-5be8-4158-9733-d09254a42489"),
    VIDEOSTREAM_UPDATED((AuditEventCategory)VezhaAuditEventCategory.STREAMS_MANAGEMENT, "ec5b9ea6-8a7f-4c93-86c0-6c51d55e5bd7"),
    VIDEOSTREAM_FRAME_UPDATED((AuditEventCategory)VezhaAuditEventCategory.STREAMS_MANAGEMENT, "04628551-4c63-4481-b3a1-71b24000ca88"),
    VIDEOSTREAM_DELETED((AuditEventCategory)VezhaAuditEventCategory.STREAMS_MANAGEMENT, "6fbf4af6-1328-474f-b071-a8aa80b85a11"),
    VIDEOSTREAM_GROUP_ADDED((AuditEventCategory)VezhaAuditEventCategory.STREAMS_MANAGEMENT, "e3e51e0c-41d3-4fb8-9382-30d4fb1b6cb2"),
    VIDEOSTREAM_GROUP_UPDATED((AuditEventCategory)VezhaAuditEventCategory.STREAMS_MANAGEMENT, "537f383d-c401-4431-9b5f-da2f31d9e4ab"),
    VIDEOSTREAM_GROUP_DELETED((AuditEventCategory)VezhaAuditEventCategory.STREAMS_MANAGEMENT, "3654839b-3046-43de-b8d4-602f8963255c"),
    ONVIF_VIDEOSTREAM_IMPORTED((AuditEventCategory)VezhaAuditEventCategory.STREAMS_MANAGEMENT, "d712789d-b6f2-408c-8176-ee07bf51d7fa"),
    EVENT_MANAGER_EVENT_ADDED((AuditEventCategory)VezhaAuditEventCategory.EVENT_MANAGEMENT, "0bc544fb-b2eb-41e4-85ce-e5d1da776029"),
    EVENT_MANAGER_EVENT_UPDATED((AuditEventCategory)VezhaAuditEventCategory.EVENT_MANAGEMENT, "e59edff4-d4d8-4001-b416-a7416a5391af"),
    EVENT_MANAGER_EVENT_DELETED((AuditEventCategory)VezhaAuditEventCategory.EVENT_MANAGEMENT, "0a18fe00-2ff0-4400-8298-3e477d2d568b"),
    ANALYTICS_GROUP_ADDED((AuditEventCategory)VezhaAuditEventCategory.ANALYTICS_GROUPS_MANAGEMENT, "376d0c14-4b45-4a68-8eeb-6efebde0668a"),
    ANALYTICS_GROUP_UPDATED((AuditEventCategory)VezhaAuditEventCategory.ANALYTICS_GROUPS_MANAGEMENT, "2b790cb9-949a-43aa-ae45-b422eb4f57f2"),
    ANALYTICS_GROUP_DELETED((AuditEventCategory)VezhaAuditEventCategory.ANALYTICS_GROUPS_MANAGEMENT, "f8a37de9-ddfa-4231-8995-bcb95db25828"),
    ANALYTICS_SERVER_SETTINGS_UPDATED((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "692f03d2-7b14-4ba3-8779-4fb2bf490f5a"),
    ANALYTICS_SERVICE_RESTARTED((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "987cccd2-e1e2-4ace-b3f2-0dd003dd07d6"),
    ANALYTICS_SERVER_REBOOTED((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "22f18232-c4c9-4766-bacb-a1d39320b971"),
    MIDDLEWARE_SERVICE_RESTARTED((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "21e23486-e022-451f-8e4e-b343a39833a4"),
    MIDDLEWARE_SERVER_REBOOTED((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "02d601d2-a45a-4c28-b32e-b5f2afa798db"),
    CLEANER_TRIGGERED((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "41b70847-fc97-45b3-99a0-87a9e919dab3"),
    STORAGE_SETTINGS_UPDATED((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "e215c25f-963d-4b13-b285-4c2da5c8e7cc"),
    GOOGLE_MAPS_API_KEY_SET((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "317cf52d-4e3c-44cb-ab66-99c050c79739"),
    GOOGLE_MAPS_API_KEY_UPDATED((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "19017251-df12-43c9-bb4c-f413880426cc"),
    SOUNDS_ENABLED((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "3833a7d8-37d6-4c78-bbb0-8735190b14e5"),
    SOUNDS_DISABLED((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "71efa77c-a378-4c7e-bfca-fa4a03b32198"),
    SOUNDS_UPDATED((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "154e6bc5-774e-4ac3-a663-f9576898c4f7"),
    SOUNDS_UPLOADED((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "afcca25e-e571-4fc3-9b3d-7b656cc9caf1"),
    LOGO_UPDATED((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "e84135e9-c14e-4095-a1e5-d70723fc0aab"),
    LOGO_DELETED((AuditEventCategory)CoreAuditEventCategory.SYSTEM_SETTINGS_MANAGEMENT, "6818dc6b-158a-4bca-a8fb-b522bc3ae65d");

    private static final String LOCALIZATION_SUFFIX = "_AUDIT_ACTION";
    private final UUID id;
    private final AuditEventCategory category;

    private VezhaAuditEventAction(AuditEventCategory category, UUID id) {
        this.id = id;
        this.category = Objects.requireNonNull(category);
    }

    private VezhaAuditEventAction(AuditEventCategory category, String id) {
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

    @Override
    public String getLocalizationKey() {
        return this.name() + LOCALIZATION_SUFFIX;
    }

    @Override
    public AuditEventCategory getCategory() {
        return this.category;
    }

    public String toString() {
        return this.name().toLowerCase();
    }
}

