/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated;

import com.incoresoft.generated.DefaultCatalog;
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
import java.util.Arrays;
import java.util.List;
import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

public class DefaultSchema
extends SchemaImpl {
    private static final long serialVersionUID = 1L;
    public static final DefaultSchema DEFAULT_SCHEMA = new DefaultSchema();
    public final ApiTokens API_TOKENS = ApiTokens.API_TOKENS;
    public final AuditTrail AUDIT_TRAIL = AuditTrail.AUDIT_TRAIL;
    public final CleaningSettings CLEANING_SETTINGS = CleaningSettings.CLEANING_SETTINGS;
    public final Clients CLIENTS = Clients.CLIENTS;
    public final EnabledPlugins ENABLED_PLUGINS = EnabledPlugins.ENABLED_PLUGINS;
    public final Licenses LICENSES = Licenses.LICENSES;
    public final NetworkSettings NETWORK_SETTINGS = NetworkSettings.NETWORK_SETTINGS;
    public final PluginConfigurations PLUGIN_CONFIGURATIONS = PluginConfigurations.PLUGIN_CONFIGURATIONS;
    public final ServiceCertificateInfo SERVICE_CERTIFICATE_INFO = ServiceCertificateInfo.SERVICE_CERTIFICATE_INFO;
    public final SoundsSettings SOUNDS_SETTINGS = SoundsSettings.SOUNDS_SETTINGS;
    public final SystemSettings SYSTEM_SETTINGS = SystemSettings.SYSTEM_SETTINGS;

    private DefaultSchema() {
        super("", null);
    }

    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(ApiTokens.API_TOKENS, AuditTrail.AUDIT_TRAIL, CleaningSettings.CLEANING_SETTINGS, Clients.CLIENTS, EnabledPlugins.ENABLED_PLUGINS, Licenses.LICENSES, NetworkSettings.NETWORK_SETTINGS, PluginConfigurations.PLUGIN_CONFIGURATIONS, ServiceCertificateInfo.SERVICE_CERTIFICATE_INFO, SoundsSettings.SOUNDS_SETTINGS, SystemSettings.SYSTEM_SETTINGS);
    }
}

