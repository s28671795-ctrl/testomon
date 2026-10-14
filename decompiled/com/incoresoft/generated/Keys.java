/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated;

import com.incoresoft.generated.tables.ApiTokens;
import com.incoresoft.generated.tables.AuditTrail;
import com.incoresoft.generated.tables.CleaningSettings;
import com.incoresoft.generated.tables.Clients;
import com.incoresoft.generated.tables.EnabledPlugins;
import com.incoresoft.generated.tables.Licenses;
import com.incoresoft.generated.tables.NetworkSettings;
import com.incoresoft.generated.tables.PluginConfigurations;
import com.incoresoft.generated.tables.ServiceCertificateInfo;
import com.incoresoft.generated.tables.SoundsSettings;
import com.incoresoft.generated.tables.SystemSettings;
import com.incoresoft.generated.tables.records.ApiTokensRecord;
import com.incoresoft.generated.tables.records.AuditTrailRecord;
import com.incoresoft.generated.tables.records.CleaningSettingsRecord;
import com.incoresoft.generated.tables.records.ClientsRecord;
import com.incoresoft.generated.tables.records.EnabledPluginsRecord;
import com.incoresoft.generated.tables.records.LicensesRecord;
import com.incoresoft.generated.tables.records.NetworkSettingsRecord;
import com.incoresoft.generated.tables.records.PluginConfigurationsRecord;
import com.incoresoft.generated.tables.records.ServiceCertificateInfoRecord;
import com.incoresoft.generated.tables.records.SoundsSettingsRecord;
import com.incoresoft.generated.tables.records.SystemSettingsRecord;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

public class Keys {
    public static final UniqueKey<ApiTokensRecord> PK_API_TOKENS = Internal.createUniqueKey(ApiTokens.API_TOKENS, DSL.name("PK_API_TOKENS"), new TableField[]{ApiTokens.API_TOKENS.ID}, true);
    public static final UniqueKey<AuditTrailRecord> PK_AUDIT_TRAIL = Internal.createUniqueKey(AuditTrail.AUDIT_TRAIL, DSL.name("PK_AUDIT_TRAIL"), new TableField[]{AuditTrail.AUDIT_TRAIL.ID}, true);
    public static final UniqueKey<CleaningSettingsRecord> CONSTRAINT_A = Internal.createUniqueKey(CleaningSettings.CLEANING_SETTINGS, DSL.name("CONSTRAINT_A"), new TableField[]{CleaningSettings.CLEANING_SETTINGS.CATEGORY_ID}, true);
    public static final UniqueKey<ClientsRecord> PK_CLIENTS = Internal.createUniqueKey(Clients.CLIENTS, DSL.name("PK_CLIENTS"), new TableField[]{Clients.CLIENTS.ID}, true);
    public static final UniqueKey<EnabledPluginsRecord> PK_ENABLED_PLUGINS = Internal.createUniqueKey(EnabledPlugins.ENABLED_PLUGINS, DSL.name("PK_ENABLED_PLUGINS"), new TableField[]{EnabledPlugins.ENABLED_PLUGINS.PLUGIN_ID}, true);
    public static final UniqueKey<LicensesRecord> PK_LICENSES = Internal.createUniqueKey(Licenses.LICENSES, DSL.name("PK_LICENSES"), new TableField[]{Licenses.LICENSES.LICENSE_KEY}, true);
    public static final UniqueKey<NetworkSettingsRecord> PK_NETWORK_SETTINGS = Internal.createUniqueKey(NetworkSettings.NETWORK_SETTINGS, DSL.name("PK_NETWORK_SETTINGS"), new TableField[]{NetworkSettings.NETWORK_SETTINGS.ID}, true);
    public static final UniqueKey<PluginConfigurationsRecord> PK_PLUGIN_CONFIGURATIONS = Internal.createUniqueKey(PluginConfigurations.PLUGIN_CONFIGURATIONS, DSL.name("PK_PLUGIN_CONFIGURATIONS"), new TableField[]{PluginConfigurations.PLUGIN_CONFIGURATIONS.ID}, true);
    public static final UniqueKey<ServiceCertificateInfoRecord> PK_SERVICE_CERTIFICATE_INFO = Internal.createUniqueKey(ServiceCertificateInfo.SERVICE_CERTIFICATE_INFO, DSL.name("PK_SERVICE_CERTIFICATE_INFO"), new TableField[]{ServiceCertificateInfo.SERVICE_CERTIFICATE_INFO.ID}, true);
    public static final UniqueKey<SoundsSettingsRecord> PK_SOUNDS_SETTINGS = Internal.createUniqueKey(SoundsSettings.SOUNDS_SETTINGS, DSL.name("PK_SOUNDS_SETTINGS"), new TableField[]{SoundsSettings.SOUNDS_SETTINGS.ID}, true);
    public static final UniqueKey<SystemSettingsRecord> PK_SYSTEM_SETTINGS = Internal.createUniqueKey(SystemSettings.SYSTEM_SETTINGS, DSL.name("PK_SYSTEM_SETTINGS"), new TableField[]{SystemSettings.SYSTEM_SETTINGS.ID}, true);
}

