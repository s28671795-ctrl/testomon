/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.plugins.menu;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.dto.plugins.menu.MenuItemConfigurationDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

@Schema(name="MenuGroupConfigurationDTO", description="Menu group structure, retrieved from plugin \"sidebar_menu.json\" file")
public final class MenuGroupConfigurationDTO {
    @JsonProperty(value="title")
    @JSONField(name="title")
    @Schema(name="title", description="Menu item locale key", implementation=String.class, example="OBJECT_DETECTION_ANALYTICS")
    private final String title;
    @JsonProperty(value="menu_items")
    @JSONField(name="menu_items")
    @Schema(name="menu_items", description="Menu items of this group")
    private final MenuItemConfigurationDTO[] menuItems;

    public String getTitle() {
        return this.title;
    }

    public MenuItemConfigurationDTO[] getMenuItems() {
        return this.menuItems;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MenuGroupConfigurationDTO)) {
            return false;
        }
        MenuGroupConfigurationDTO other = (MenuGroupConfigurationDTO)o;
        String this$title = this.getTitle();
        String other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) {
            return false;
        }
        return Arrays.deepEquals(this.getMenuItems(), other.getMenuItems());
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $title = this.getTitle();
        result = result * 59 + ($title == null ? 43 : $title.hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getMenuItems());
        return result;
    }

    public String toString() {
        return "MenuGroupConfigurationDTO(title=" + this.getTitle() + ", menuItems=" + Arrays.deepToString(this.getMenuItems()) + ")";
    }

    public MenuGroupConfigurationDTO(String title, MenuItemConfigurationDTO[] menuItems) {
        this.title = title;
        this.menuItems = menuItems;
    }
}

