/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.plugins.menu;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.dto.plugins.menu.MenuItemConfigurationDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

@Schema(name="MenuItemDTO", description="Menu item class structure")
public final class FrontMenuItemDTO {
    @JSONField(name="title")
    @Schema(name="title", description="Menu item locale key", implementation=String.class, example="OBJECT_DETECTION_ANALYTICS")
    private final String title;
    @JSONField(name="path")
    @Schema(name="path", description="Menu item path", implementation=String.class, example="/object_detection/analytics")
    private final String path;
    @JSONField(name="active_routes_sidebar")
    @Schema(name="active_routes_sidebar", description="Available routes from this page", example="[\"object_in_zone/events\"]")
    private final String[] activeRoutes;
    @JSONField(name="icon")
    @Schema(name="icon", description="Menu item icon", implementation=String.class, example="icon")
    private final String icon;

    public FrontMenuItemDTO(MenuItemConfigurationDTO menuItem) {
        this.title = menuItem.getTitle();
        this.path = menuItem.getPath();
        this.activeRoutes = menuItem.getActiveRoutes();
        this.icon = menuItem.getIcon();
    }

    public String getTitle() {
        return this.title;
    }

    public String getPath() {
        return this.path;
    }

    public String[] getActiveRoutes() {
        return this.activeRoutes;
    }

    public String getIcon() {
        return this.icon;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FrontMenuItemDTO)) {
            return false;
        }
        FrontMenuItemDTO other = (FrontMenuItemDTO)o;
        String this$title = this.getTitle();
        String other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) {
            return false;
        }
        String this$path = this.getPath();
        String other$path = other.getPath();
        if (this$path == null ? other$path != null : !this$path.equals(other$path)) {
            return false;
        }
        if (!Arrays.deepEquals(this.getActiveRoutes(), other.getActiveRoutes())) {
            return false;
        }
        String this$icon = this.getIcon();
        String other$icon = other.getIcon();
        return !(this$icon == null ? other$icon != null : !this$icon.equals(other$icon));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $title = this.getTitle();
        result = result * 59 + ($title == null ? 43 : $title.hashCode());
        String $path = this.getPath();
        result = result * 59 + ($path == null ? 43 : $path.hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getActiveRoutes());
        String $icon = this.getIcon();
        result = result * 59 + ($icon == null ? 43 : $icon.hashCode());
        return result;
    }

    public String toString() {
        return "FrontMenuItemDTO(title=" + this.getTitle() + ", path=" + this.getPath() + ", activeRoutes=" + Arrays.deepToString(this.getActiveRoutes()) + ", icon=" + this.getIcon() + ")";
    }

    public FrontMenuItemDTO(String title, String path, String[] activeRoutes, String icon) {
        this.title = title;
        this.path = path;
        this.activeRoutes = activeRoutes;
        this.icon = icon;
    }
}

