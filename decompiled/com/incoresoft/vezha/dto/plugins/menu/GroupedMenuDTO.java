/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.plugins.menu;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.dto.plugins.menu.MenuItemConfigurationDTO;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.vezha.dto.plugins.menu.FrontMenuItemDTO;
import com.incoresoft.vezha.protocol.controllers.plugin.MenuGroup;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

@Schema(name="GroupedMenuDTO", description="Grouped menu class structure")
public final class GroupedMenuDTO {
    @JSONField(name="title")
    @Schema(name="title", description="Sidebar locale key", implementation=String.class, example="OBJECT_DETECTION_SIDEBAR")
    private final String title;
    @JSONField(name="icon")
    @Schema(name="icon", description="Sidebar icon", implementation=String.class, example="/object_detection.ico")
    private final String icon;
    @JSONField(name="menu_items")
    @ArraySchema(schema=@Schema(name="menu_items", description="Menu items", implementation=FrontMenuItemDTO.class))
    private final List<FrontMenuItemDTO> menuItems;
    @JSONField(name="name")
    @Schema(name="name", description="Menu item name", implementation=String.class, example="object_in_zone")
    private final String name;

    public GroupedMenuDTO(User user, MenuGroup menuGroup, Map<String, Permission> permissions) {
        this("resources/" + menuGroup.getIconHref(), user, menuGroup, permissions);
    }

    public GroupedMenuDTO(String icon, User user, MenuGroup menuGroup, Map<String, Permission> allPermissions) {
        this.title = menuGroup.getPluginSidebar();
        this.icon = icon;
        this.name = menuGroup.getPluginName();
        this.menuItems = menuGroup.getMenuItems().stream().filter(it -> user.hasAnyPermission(GroupedMenuDTO.menuItemPermissions(allPermissions, it))).map(FrontMenuItemDTO::new).toList();
    }

    @NotNull
    private static List<Permission> menuItemPermissions(Map<String, Permission> allPermissions, MenuItemConfigurationDTO menuItemConfigurationDTO) {
        if (menuItemConfigurationDTO.getPermissions() == null) {
            return List.of();
        }
        return Arrays.stream(menuItemConfigurationDTO.getPermissions()).filter(allPermissions::containsKey).map(allPermissions::get).toList();
    }

    public String getTitle() {
        return this.title;
    }

    public String getIcon() {
        return this.icon;
    }

    public List<FrontMenuItemDTO> getMenuItems() {
        return this.menuItems;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GroupedMenuDTO)) {
            return false;
        }
        GroupedMenuDTO other = (GroupedMenuDTO)o;
        String this$title = this.getTitle();
        String other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) {
            return false;
        }
        String this$icon = this.getIcon();
        String other$icon = other.getIcon();
        if (this$icon == null ? other$icon != null : !this$icon.equals(other$icon)) {
            return false;
        }
        List<FrontMenuItemDTO> this$menuItems = this.getMenuItems();
        List<FrontMenuItemDTO> other$menuItems = other.getMenuItems();
        if (this$menuItems == null ? other$menuItems != null : !((Object)this$menuItems).equals(other$menuItems)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        return !(this$name == null ? other$name != null : !this$name.equals(other$name));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $title = this.getTitle();
        result = result * 59 + ($title == null ? 43 : $title.hashCode());
        String $icon = this.getIcon();
        result = result * 59 + ($icon == null ? 43 : $icon.hashCode());
        List<FrontMenuItemDTO> $menuItems = this.getMenuItems();
        result = result * 59 + ($menuItems == null ? 43 : ((Object)$menuItems).hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public String toString() {
        return "GroupedMenuDTO(title=" + this.getTitle() + ", icon=" + this.getIcon() + ", menuItems=" + String.valueOf(this.getMenuItems()) + ", name=" + this.getName() + ")";
    }

    public GroupedMenuDTO(String title, String icon, List<FrontMenuItemDTO> menuItems, String name) {
        this.title = title;
        this.icon = icon;
        this.menuItems = menuItems;
        this.name = name;
    }
}

