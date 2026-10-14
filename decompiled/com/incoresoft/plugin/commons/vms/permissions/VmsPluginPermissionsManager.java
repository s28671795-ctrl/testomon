/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.vms.permissions;

import com.google.inject.Inject;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.middleware.model.permission.PluginPermissions;
import com.incoresoft.middleware.vms.roles.VmsPluginRolesManager;
import com.incoresoft.middleware.vms.vezhaintegration.VezhaIntegrationManager;
import com.incoresoft.plugin.commons.vms.permissions.PluginPermission;
import com.incoresoft.plugin.commons.vms.permissions.dto.PluginPermissionDTO;
import com.incoresoft.plugin.commons.vms.permissions.dto.PluginPermissionListDTO;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class VmsPluginPermissionsManager {
    private static final Logger log = LoggerFactory.getLogger(VmsPluginPermissionsManager.class);
    private final VmsPluginRolesManager rolesManager;
    private final VezhaIntegrationManager vezhaIntegrationManager;
    private final HttpClient httpClient;
    private final AtomicReference<Map<String, PluginPermission>> orderedPermissionsReference = new AtomicReference(Map.of());

    public Collection<PluginPermission> getPluginPermissions() {
        if (this.orderedPermissionsReference.get().isEmpty()) {
            log.warn("Plugin permissions are empty, trying to update permissions for plugin {}", (Object)this.getPluginName());
            this.updatePermissions();
        }
        return this.orderedPermissionsReference.get().values();
    }

    public Optional<PluginPermission> getPluginPermission(String id) {
        if (!this.orderedPermissionsReference.get().containsKey(id)) {
            log.warn("Plugin permissions are empty, trying to update permissions for plugin {}", (Object)this.getPluginName());
            this.updatePermissions();
        }
        return Optional.ofNullable(this.orderedPermissionsReference.get().get(id));
    }

    public void updatePermissions() {
        try {
            List<PluginPermissionDTO> dto = this.getVezhaPermissions();
            log.info("Received {} plugin permissions from Vezha", (Object)dto.size());
            LinkedHashMap permissionsMap = dto.stream().filter(it -> !this.ignorablePermissions().contains(it.id())).map(PluginPermission::new).collect(Collectors.toMap(PluginPermission::getId, it -> it, (a, b) -> a, LinkedHashMap::new));
            if (!this.orderedPermissionsReference.get().isEmpty()) {
                this.rolesManager.unregister(this.getPluginName(), this.buildPluginPermissions(this.orderedPermissionsReference.get()));
            }
            this.orderedPermissionsReference.set(permissionsMap);
            this.rolesManager.register(this.getPluginName(), this.buildPluginPermissions(permissionsMap));
            log.info("Plugin permissions updated for plugin {}", (Object)this.getPluginName());
        }
        catch (Exception e) {
            log.error("Failed to update plugin permissions", e);
        }
    }

    public void clearPermissions() {
        this.rolesManager.unregister(this.getPluginName(), this.buildPluginPermissions(this.orderedPermissionsReference.get()));
        this.orderedPermissionsReference.set(Map.of());
        log.info("Plugin permissions cleared for plugin {}", (Object)this.getPluginName());
    }

    private PluginPermissions buildPluginPermissions(Map<String, PluginPermission> permissionsMap) {
        return new PluginPermissions(Collections.emptyList(), permissionsMap.values().stream().map(it -> it).toList());
    }

    private List<PluginPermissionDTO> getVezhaPermissions() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(this.vezhaIntegrationManager.getVezhaApiUri().resolve(this.getPermissionsPath())).header("Authorization", "Bearer " + this.vezhaIntegrationManager.getAdminToken()).GET().build();
        HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to get plugin permissions, status code: %d, response: %s".formatted(response.statusCode(), response.body()));
        }
        return Serialization.readValue(response.body(), PluginPermissionListDTO.class).data();
    }

    protected abstract String getPermissionsPath();

    protected abstract Set<String> ignorablePermissions();

    protected abstract String getPluginName();

    @Inject
    public VmsPluginPermissionsManager(VmsPluginRolesManager rolesManager, VezhaIntegrationManager vezhaIntegrationManager, HttpClient httpClient) {
        this.rolesManager = rolesManager;
        this.vezhaIntegrationManager = vezhaIntegrationManager;
        this.httpClient = httpClient;
    }
}

