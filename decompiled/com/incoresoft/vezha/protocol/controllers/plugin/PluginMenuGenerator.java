/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.plugin;

import com.alibaba.fastjson.JSON;
import com.google.inject.Inject;
import com.incoresoft.dto.plugins.menu.MenuGroupConfigurationDTO;
import com.incoresoft.dto.plugins.menu.MenuItemConfigurationDTO;
import com.incoresoft.extensions.ExtensionsManager;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.role.RolesManager;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.plugin.SystemExtension;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.vezha.dto.plugins.menu.GroupedMenuDTO;
import com.incoresoft.vezha.protocol.controllers.plugin.MenuGroup;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginMenuGenerator {
    private static final Logger log = LoggerFactory.getLogger(PluginMenuGenerator.class);
    private final ExtensionsManager extensionsManager;
    private final RolesManager rolesManager;

    public List<GroupedMenuDTO> getMenuGroups(User user) {
        Map<String, Permission> allPermissions = this.rolesManager.getPermissions().stream().collect(Collectors.toUnmodifiableMap(Permission::getId, Function.identity()));
        LinkedList<GroupedMenuDTO> menuGroups = new LinkedList<GroupedMenuDTO>();
        menuGroups.addAll(this.getExtensionMenuGroupDTOs(user, allPermissions));
        menuGroups.add(this.getSettingsMenuGroupDTO(user, allPermissions));
        return menuGroups;
    }

    @NotNull
    private List<GroupedMenuDTO> getExtensionMenuGroupDTOs(User user, Map<String, Permission> allPermissions) {
        return this.extensionsManager.getAll(VezhaExtension.class).stream().map(PluginMenuGenerator::getMenuGroup).filter(Objects::nonNull).filter(it -> user.hasAnyPermission(it.getMenuItems().stream().flatMap(item -> Arrays.stream(item.getPermissions())).filter(allPermissions::containsKey).map(allPermissions::get).toList())).sorted(Comparator.comparingInt(MenuGroup::getPriority)).map(it -> new GroupedMenuDTO(user, (MenuGroup)it, allPermissions)).toList();
    }

    @Nullable
    public static MenuGroup getMenuGroup(VezhaExtension plugin) {
        return plugin.getSidebarMenuGroups().stream().map(it -> JSON.parseObject(it, MenuGroupConfigurationDTO.class)).map(it -> new MenuGroup(plugin.getPluginName(), it.getTitle(), List.of(it.getMenuItems()))).findAny().orElse(null);
    }

    private GroupedMenuDTO getSettingsMenuGroupDTO(User user, Map<String, Permission> allPermissions) {
        ArrayList<MenuItemConfigurationDTO> allItems = new ArrayList<MenuItemConfigurationDTO>();
        this.readCoreSettingsConfigurationsValue().stream().map(it -> JSON.parseObject(it, MenuGroupConfigurationDTO.class).getMenuItems()).flatMap(Stream::of).forEach(allItems::add);
        this.extensionsManager.getAll(VezhaExtension.class).stream().map(SystemExtension::getSettingsMenuItems).flatMap(Collection::stream).map(it -> JSON.parseObject(it, MenuItemConfigurationDTO.class)).forEach(allItems::add);
        MenuGroup menuGroup = new MenuGroup("settings", "SETTINGS", allItems);
        return new GroupedMenuDTO("images/settings_icon.svg", user, menuGroup, allPermissions);
    }

    private List<String> readCoreSettingsConfigurationsValue() {
        return Optional.ofNullable(this.getClass().getClassLoader().getResourceAsStream("front/core/sidebar_menu.json")).map(is -> {
            try {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).map(it -> JSON.parseArray(it, String.class)).orElseGet(Collections::emptyList);
    }

    @Inject
    public PluginMenuGenerator(ExtensionsManager extensionsManager, RolesManager rolesManager) {
        this.extensionsManager = extensionsManager;
        this.rolesManager = rolesManager;
    }
}

