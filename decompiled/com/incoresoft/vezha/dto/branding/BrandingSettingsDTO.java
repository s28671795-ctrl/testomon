/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.branding;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.models.settings.CustomSettingsManager;
import io.swagger.v3.oas.annotations.media.Schema;

public final class BrandingSettingsDTO {
    @JSONField(name="show_help_tooltip")
    @Schema(name="show_help_tooltip", description="Determines whether show help tooltip will be shown", implementation=Boolean.class, example="true")
    private final boolean showHelpTooltip;
    @JSONField(name="title")
    @Schema(name="title", description="Title of the application", example="Incoresoft")
    private final String title;
    @JSONField(name="marketplace_server")
    @Schema(name="marketplace_server", description="URL of marketplace API", implementation=String.class)
    private final String marketplaceServer;

    public BrandingSettingsDTO(CustomSettingsManager settingsManager) {
        this.showHelpTooltip = Boolean.parseBoolean(settingsManager.getValue("show_help_tooltip"));
        this.title = settingsManager.getValue("title");
        this.marketplaceServer = settingsManager.getValue("marketplace_server");
    }

    public boolean isShowHelpTooltip() {
        return this.showHelpTooltip;
    }

    public String getTitle() {
        return this.title;
    }

    public String getMarketplaceServer() {
        return this.marketplaceServer;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BrandingSettingsDTO)) {
            return false;
        }
        BrandingSettingsDTO other = (BrandingSettingsDTO)o;
        if (this.isShowHelpTooltip() != other.isShowHelpTooltip()) {
            return false;
        }
        String this$title = this.getTitle();
        String other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) {
            return false;
        }
        String this$marketplaceServer = this.getMarketplaceServer();
        String other$marketplaceServer = other.getMarketplaceServer();
        return !(this$marketplaceServer == null ? other$marketplaceServer != null : !this$marketplaceServer.equals(other$marketplaceServer));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isShowHelpTooltip() ? 79 : 97);
        String $title = this.getTitle();
        result = result * 59 + ($title == null ? 43 : $title.hashCode());
        String $marketplaceServer = this.getMarketplaceServer();
        result = result * 59 + ($marketplaceServer == null ? 43 : $marketplaceServer.hashCode());
        return result;
    }

    public String toString() {
        return "BrandingSettingsDTO(showHelpTooltip=" + this.isShowHelpTooltip() + ", title=" + this.getTitle() + ", marketplaceServer=" + this.getMarketplaceServer() + ")";
    }
}

