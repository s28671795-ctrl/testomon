/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.plugin;

import com.incoresoft.dto.plugins.menu.MenuItemConfigurationDTO;
import java.util.Collection;

public final class MenuGroup {
    private final String pluginName;
    private final String pluginSidebar;
    private final Collection<MenuItemConfigurationDTO> menuItems;
    private final int priority;
    private final String iconHref;

    public MenuGroup(String pluginName, String pluginSidebar, Collection<MenuItemConfigurationDTO> menuItems) {
        this(pluginName, pluginSidebar, menuItems, 0, pluginName + "/icon");
    }

    public String getPluginName() {
        return this.pluginName;
    }

    public String getPluginSidebar() {
        return this.pluginSidebar;
    }

    public Collection<MenuItemConfigurationDTO> getMenuItems() {
        return this.menuItems;
    }

    public int getPriority() {
        return this.priority;
    }

    public String getIconHref() {
        return this.iconHref;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MenuGroup)) {
            return false;
        }
        MenuGroup other = (MenuGroup)o;
        if (this.getPriority() != other.getPriority()) {
            return false;
        }
        String this$pluginName = this.getPluginName();
        String other$pluginName = other.getPluginName();
        if (this$pluginName == null ? other$pluginName != null : !this$pluginName.equals(other$pluginName)) {
            return false;
        }
        String this$pluginSidebar = this.getPluginSidebar();
        String other$pluginSidebar = other.getPluginSidebar();
        if (this$pluginSidebar == null ? other$pluginSidebar != null : !this$pluginSidebar.equals(other$pluginSidebar)) {
            return false;
        }
        Collection<MenuItemConfigurationDTO> this$menuItems = this.getMenuItems();
        Collection<MenuItemConfigurationDTO> other$menuItems = other.getMenuItems();
        if (this$menuItems == null ? other$menuItems != null : !((Object)this$menuItems).equals(other$menuItems)) {
            return false;
        }
        String this$iconHref = this.getIconHref();
        String other$iconHref = other.getIconHref();
        return !(this$iconHref == null ? other$iconHref != null : !this$iconHref.equals(other$iconHref));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getPriority();
        String $pluginName = this.getPluginName();
        result = result * 59 + ($pluginName == null ? 43 : $pluginName.hashCode());
        String $pluginSidebar = this.getPluginSidebar();
        result = result * 59 + ($pluginSidebar == null ? 43 : $pluginSidebar.hashCode());
        Collection<MenuItemConfigurationDTO> $menuItems = this.getMenuItems();
        result = result * 59 + ($menuItems == null ? 43 : ((Object)$menuItems).hashCode());
        String $iconHref = this.getIconHref();
        result = result * 59 + ($iconHref == null ? 43 : $iconHref.hashCode());
        return result;
    }

    public String toString() {
        return "MenuGroup(pluginName=" + this.getPluginName() + ", pluginSidebar=" + this.getPluginSidebar() + ", menuItems=" + String.valueOf(this.getMenuItems()) + ", priority=" + this.getPriority() + ", iconHref=" + this.getIconHref() + ")";
    }

    public MenuGroup(String pluginName, String pluginSidebar, Collection<MenuItemConfigurationDTO> menuItems, int priority, String iconHref) {
        this.pluginName = pluginName;
        this.pluginSidebar = pluginSidebar;
        this.menuItems = menuItems;
        this.priority = priority;
        this.iconHref = iconHref;
    }
}

