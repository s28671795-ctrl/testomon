/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.plugins.extensions;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.plugin.EventManagerExtension;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.plugins.models.PluginType;
import com.incoresoft.vezha.dto.plugins.extensions.ComponentDTO;
import com.incoresoft.vezha.dto.plugins.extensions.ExtensionAnalyticsParametersDTO;
import com.incoresoft.vezha.dto.plugins.extensions.VezhaExtensionInterface;
import com.incoresoft.vezha.protocol.controllers.plugin.MenuGroup;
import com.incoresoft.vezha.protocol.controllers.plugin.PluginMenuGenerator;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Optional;

@Schema(name="VezhaExtensionDTO", description="Vezha extension class structure for frontend")
public final class VezhaExtensionDTO
implements VezhaExtensionInterface {
    @JSONField(name="module_name")
    @Schema(name="module_name", description="Vezha extension name", implementation=String.class, example="api")
    private final String moduleName;
    @JSONField(name="plugin_id")
    @Schema(name="plugin_id", description="Vezha plugin id", implementation=String.class, example="api")
    private final String pluginId;
    @JSONField(name="type")
    @Schema(name="type", description="Vezha extension type", implementation=String.class, example="api")
    private final PluginType type;
    @JSONField(name="analytics")
    @Schema(name="analytics", description="Vezha extension analytics parameters", implementation=ExtensionAnalyticsParametersDTO.class)
    private final ExtensionAnalyticsParametersDTO extensionAnalyticsParametersDTO;
    @JSONField(name="path")
    @Schema(name="path", description="Vezha extension path", implementation=String.class, example="event_manager/api/app.js")
    private final String path;
    @JSONField(name="locale")
    @Schema(name="path", description="Path to locale", implementation=String.class, example="event_manager/api/locales")
    private final String localePath;
    @JSONField(name="icon")
    @Schema(name="icon", description="Path to icon", implementation=String.class, example="event_manager/api/icon.png")
    private final String iconPath;
    @JSONField(name="title")
    @Schema(name="title", description="Extension title", implementation=String.class, example="API")
    private final String title;
    @JSONField(name="components")
    @ArraySchema(schema=@Schema(name="components", description="Vezha extension frontend components", implementation=ComponentDTO.class))
    private final List<ComponentDTO> components;
    @JSONField(name="live_view_disabled")
    @Schema(name="live_view_disabled", description="Live view disabled", implementation=Boolean.class, example="true")
    private final boolean liveViewDisabled;

    public VezhaExtensionDTO(EventManagerExtension entry, String pluginId) {
        this.moduleName = entry.getExtensionId();
        this.pluginId = pluginId;
        this.type = PluginType.EVENT_MANAGER_PLUGIN;
        this.extensionAnalyticsParametersDTO = null;
        this.path = "event_manager/" + this.moduleName + "/app.js";
        this.localePath = "resources/" + this.moduleName + "/locales";
        this.iconPath = "event_manager/" + this.moduleName + "/icon";
        this.title = entry.getNameLocaleKey();
        this.components = entry.getFrontComponents().stream().map(it -> JSON.parseObject(it, ComponentDTO.class)).toList();
        Optional<JSONObject> settings = Optional.ofNullable(entry.getSettings());
        this.liveViewDisabled = settings.map(jsonObject -> jsonObject.getBoolean("live_view_disabled")).orElse(false);
    }

    public VezhaExtensionDTO(VezhaExtension entry, String pluginId) {
        this.moduleName = entry.getPluginName();
        this.pluginId = pluginId;
        if (entry.getModuleClass() == null) {
            this.type = PluginType.SERVICE_PLUGIN;
            this.extensionAnalyticsParametersDTO = null;
        } else {
            this.type = PluginType.ANALYTICS_PLUGIN;
            this.extensionAnalyticsParametersDTO = new ExtensionAnalyticsParametersDTO(entry.getManageAnalyticsPermission());
        }
        this.path = "resources/" + this.moduleName + "/app.js";
        this.localePath = "resources/" + this.moduleName + "/locales";
        this.iconPath = "resources/" + this.moduleName + "/icon";
        this.title = Optional.ofNullable(PluginMenuGenerator.getMenuGroup(entry)).map(MenuGroup::getPluginSidebar).orElse(null);
        this.components = entry.getFrontComponents().stream().map(it -> JSON.parseObject(it, ComponentDTO.class)).toList();
        Optional<JSONObject> settings = Optional.ofNullable(entry.getSettings());
        this.liveViewDisabled = settings.map(jsonObject -> jsonObject.getBoolean("live_view_disabled")).orElse(false);
    }

    public VezhaExtensionDTO(String moduleName, List<String> components) {
        this.moduleName = moduleName;
        this.pluginId = null;
        this.type = PluginType.SERVICE_PLUGIN;
        this.extensionAnalyticsParametersDTO = null;
        this.path = "resources/" + moduleName + "/app.js";
        this.localePath = "resources/" + moduleName + "/locales";
        this.iconPath = "resources/" + moduleName + "/icon";
        this.title = moduleName.toUpperCase();
        this.components = components.stream().map(it -> JSON.parseObject(it, ComponentDTO.class)).toList();
        this.liveViewDisabled = false;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public String getPluginId() {
        return this.pluginId;
    }

    public PluginType getType() {
        return this.type;
    }

    public ExtensionAnalyticsParametersDTO getExtensionAnalyticsParametersDTO() {
        return this.extensionAnalyticsParametersDTO;
    }

    public String getPath() {
        return this.path;
    }

    public String getLocalePath() {
        return this.localePath;
    }

    public String getIconPath() {
        return this.iconPath;
    }

    public String getTitle() {
        return this.title;
    }

    public List<ComponentDTO> getComponents() {
        return this.components;
    }

    public boolean isLiveViewDisabled() {
        return this.liveViewDisabled;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof VezhaExtensionDTO)) {
            return false;
        }
        VezhaExtensionDTO other = (VezhaExtensionDTO)o;
        if (this.isLiveViewDisabled() != other.isLiveViewDisabled()) {
            return false;
        }
        String this$moduleName = this.getModuleName();
        String other$moduleName = other.getModuleName();
        if (this$moduleName == null ? other$moduleName != null : !this$moduleName.equals(other$moduleName)) {
            return false;
        }
        String this$pluginId = this.getPluginId();
        String other$pluginId = other.getPluginId();
        if (this$pluginId == null ? other$pluginId != null : !this$pluginId.equals(other$pluginId)) {
            return false;
        }
        PluginType this$type = this.getType();
        PluginType other$type = other.getType();
        if (this$type == null ? other$type != null : !((Object)((Object)this$type)).equals((Object)other$type)) {
            return false;
        }
        ExtensionAnalyticsParametersDTO this$extensionAnalyticsParametersDTO = this.getExtensionAnalyticsParametersDTO();
        ExtensionAnalyticsParametersDTO other$extensionAnalyticsParametersDTO = other.getExtensionAnalyticsParametersDTO();
        if (this$extensionAnalyticsParametersDTO == null ? other$extensionAnalyticsParametersDTO != null : !((Object)this$extensionAnalyticsParametersDTO).equals(other$extensionAnalyticsParametersDTO)) {
            return false;
        }
        String this$path = this.getPath();
        String other$path = other.getPath();
        if (this$path == null ? other$path != null : !this$path.equals(other$path)) {
            return false;
        }
        String this$localePath = this.getLocalePath();
        String other$localePath = other.getLocalePath();
        if (this$localePath == null ? other$localePath != null : !this$localePath.equals(other$localePath)) {
            return false;
        }
        String this$iconPath = this.getIconPath();
        String other$iconPath = other.getIconPath();
        if (this$iconPath == null ? other$iconPath != null : !this$iconPath.equals(other$iconPath)) {
            return false;
        }
        String this$title = this.getTitle();
        String other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) {
            return false;
        }
        List<ComponentDTO> this$components = this.getComponents();
        List<ComponentDTO> other$components = other.getComponents();
        return !(this$components == null ? other$components != null : !((Object)this$components).equals(other$components));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isLiveViewDisabled() ? 79 : 97);
        String $moduleName = this.getModuleName();
        result = result * 59 + ($moduleName == null ? 43 : $moduleName.hashCode());
        String $pluginId = this.getPluginId();
        result = result * 59 + ($pluginId == null ? 43 : $pluginId.hashCode());
        PluginType $type = this.getType();
        result = result * 59 + ($type == null ? 43 : ((Object)((Object)$type)).hashCode());
        ExtensionAnalyticsParametersDTO $extensionAnalyticsParametersDTO = this.getExtensionAnalyticsParametersDTO();
        result = result * 59 + ($extensionAnalyticsParametersDTO == null ? 43 : ((Object)$extensionAnalyticsParametersDTO).hashCode());
        String $path = this.getPath();
        result = result * 59 + ($path == null ? 43 : $path.hashCode());
        String $localePath = this.getLocalePath();
        result = result * 59 + ($localePath == null ? 43 : $localePath.hashCode());
        String $iconPath = this.getIconPath();
        result = result * 59 + ($iconPath == null ? 43 : $iconPath.hashCode());
        String $title = this.getTitle();
        result = result * 59 + ($title == null ? 43 : $title.hashCode());
        List<ComponentDTO> $components = this.getComponents();
        result = result * 59 + ($components == null ? 43 : ((Object)$components).hashCode());
        return result;
    }

    public String toString() {
        return "VezhaExtensionDTO(moduleName=" + this.getModuleName() + ", pluginId=" + this.getPluginId() + ", type=" + String.valueOf((Object)this.getType()) + ", extensionAnalyticsParametersDTO=" + String.valueOf(this.getExtensionAnalyticsParametersDTO()) + ", path=" + this.getPath() + ", localePath=" + this.getLocalePath() + ", iconPath=" + this.getIconPath() + ", title=" + this.getTitle() + ", components=" + String.valueOf(this.getComponents()) + ", liveViewDisabled=" + this.isLiveViewDisabled() + ")";
    }

    public VezhaExtensionDTO(String moduleName, String pluginId, PluginType type, ExtensionAnalyticsParametersDTO extensionAnalyticsParametersDTO, String path, String localePath, String iconPath, String title, List<ComponentDTO> components, boolean liveViewDisabled) {
        this.moduleName = moduleName;
        this.pluginId = pluginId;
        this.type = type;
        this.extensionAnalyticsParametersDTO = extensionAnalyticsParametersDTO;
        this.path = path;
        this.localePath = localePath;
        this.iconPath = iconPath;
        this.title = title;
        this.components = components;
        this.liveViewDisabled = liveViewDisabled;
    }
}

