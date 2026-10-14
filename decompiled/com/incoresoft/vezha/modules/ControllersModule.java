/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.modules;

import com.google.inject.AbstractModule;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.plugin.commons.validation.BeanValidatorImpl;
import com.incoresoft.vezha.backup.ws.BackupsCreationWSHandler;
import com.incoresoft.vezha.backup.ws.BackupsRestoreWSHandler;
import com.incoresoft.vezha.backup.ws.BackupsWSController;
import com.incoresoft.vezha.protocol.controllers.api.v2.ApiAuditControllerV2;
import com.incoresoft.vezha.protocol.controllers.api.v2.ApiBrandingControllerV2;
import com.incoresoft.vezha.protocol.controllers.api.v2.ApiCameraAnalyticsTestController;
import com.incoresoft.vezha.protocol.controllers.api.v2.ApiClientsControllerV2;
import com.incoresoft.vezha.protocol.controllers.api.v2.ApiCountriesControllerV2;
import com.incoresoft.vezha.protocol.controllers.api.v2.ApiDocsControllerV2;
import com.incoresoft.vezha.protocol.controllers.api.v2.ApiEventManagerControllerV2;
import com.incoresoft.vezha.protocol.controllers.api.v2.ApiGeneralSettingsControllerV2;
import com.incoresoft.vezha.protocol.controllers.api.v2.ApiLdapControllerV2;
import com.incoresoft.vezha.protocol.controllers.api.v2.ApiLicenseControllerV2;
import com.incoresoft.vezha.protocol.controllers.api.v2.ApiNotificationsControllerV2;
import com.incoresoft.vezha.protocol.controllers.api.v2.ApiRolesControllerV2;
import com.incoresoft.vezha.protocol.controllers.api.v2.ApiServiceControllerV2;
import com.incoresoft.vezha.protocol.controllers.api.v2.ApiSoundsControllerV2;
import com.incoresoft.vezha.protocol.controllers.api.v2.ApiStorageController;
import com.incoresoft.vezha.protocol.controllers.api.v2.ApiStreamsControllerV2;
import com.incoresoft.vezha.protocol.controllers.api.v2.ApiSystemSpaceControllerV2;
import com.incoresoft.vezha.protocol.controllers.api.v2.ApiTimeZoneControllerV2;
import com.incoresoft.vezha.protocol.controllers.api.v2.ApiUsersControllerV2;
import com.incoresoft.vezha.protocol.controllers.api.v2.ApiWindowsShareControllerV2;
import com.incoresoft.vezha.protocol.controllers.api.v2.AxisStationProSettingsController;
import com.incoresoft.vezha.protocol.controllers.api.v2.BackupsController;
import com.incoresoft.vezha.protocol.controllers.api.v2.MarketplaceController;
import com.incoresoft.vezha.protocol.controllers.api.v2.PluginExtensionsController;
import com.incoresoft.vezha.protocol.controllers.api.v2.PluginsController;
import com.incoresoft.vezha.protocol.controllers.api.v2.VezhaCleaningSettingsController;
import com.incoresoft.vezha.protocol.controllers.api.v2.analytics.AnalyticsGroupsController;
import com.incoresoft.vezha.protocol.controllers.api.v2.analytics.ApiAnalyticsControllerV2;
import com.incoresoft.vezha.protocol.controllers.api.v2.analytics.ApiAnalyticsServersControllerV2;
import com.incoresoft.vezha.protocol.ws.AnalyticsWSBroadcaster;
import com.incoresoft.vezha.protocol.ws.NotificationsWSBroadcaster;
import com.incoresoft.vezha.protocol.ws.ServersWSBroadcaster;
import com.incoresoft.vezha.protocol.ws.WebsocketController;

public class ControllersModule
extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(BeanValidatorImpl.class).asEagerSingleton();
        this.bind(BeanValidator.class).to(BeanValidatorImpl.class);
        this.bind(ApiAuditControllerV2.class).asEagerSingleton();
        this.bind(ApiBrandingControllerV2.class).asEagerSingleton();
        this.bind(ApiClientsControllerV2.class).asEagerSingleton();
        this.bind(ApiCountriesControllerV2.class).asEagerSingleton();
        this.bind(ApiDocsControllerV2.class).asEagerSingleton();
        this.bind(ApiEventManagerControllerV2.class).asEagerSingleton();
        this.bind(ApiGeneralSettingsControllerV2.class).asEagerSingleton();
        this.bind(ApiLdapControllerV2.class).asEagerSingleton();
        this.bind(ApiLicenseControllerV2.class).asEagerSingleton();
        this.bind(ApiNotificationsControllerV2.class).asEagerSingleton();
        this.bind(PluginExtensionsController.class).asEagerSingleton();
        this.bind(ApiRolesControllerV2.class).asEagerSingleton();
        this.bind(ApiServiceControllerV2.class).asEagerSingleton();
        this.bind(ApiAnalyticsServersControllerV2.class).asEagerSingleton();
        this.bind(ApiSoundsControllerV2.class).asEagerSingleton();
        this.bind(ApiStreamsControllerV2.class).asEagerSingleton();
        this.bind(ApiTimeZoneControllerV2.class).asEagerSingleton();
        this.bind(ApiUsersControllerV2.class).asEagerSingleton();
        this.bind(ApiWindowsShareControllerV2.class).asEagerSingleton();
        this.bind(ApiStorageController.class).asEagerSingleton();
        this.bind(ApiSystemSpaceControllerV2.class).asEagerSingleton();
        this.bind(ApiCameraAnalyticsTestController.class).asEagerSingleton();
        this.bind(AnalyticsGroupsController.class).asEagerSingleton();
        this.bind(ApiAnalyticsControllerV2.class).asEagerSingleton();
        this.bind(PluginsController.class).asEagerSingleton();
        this.bind(MarketplaceController.class).asEagerSingleton();
        this.bind(AxisStationProSettingsController.class).asEagerSingleton();
        this.bind(BackupsController.class).asEagerSingleton();
        this.bind(BackupsCreationWSHandler.class).asEagerSingleton();
        this.bind(BackupsRestoreWSHandler.class).asEagerSingleton();
        this.bind(BackupsWSController.class).asEagerSingleton();
        this.bind(WebsocketController.class).asEagerSingleton();
        this.bind(AnalyticsWSBroadcaster.class).asEagerSingleton();
        this.bind(NotificationsWSBroadcaster.class).asEagerSingleton();
        this.bind(ServersWSBroadcaster.class).asEagerSingleton();
        this.bind(VezhaCleaningSettingsController.class).asEagerSingleton();
    }
}

