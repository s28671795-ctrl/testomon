/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.audit;

import com.incoresoft.audit.AuditSourceActionContainer;
import com.incoresoft.audit.exceptions.AuditActionAlreadyExistException;
import com.incoresoft.middleware.audit.AuditEventAction;
import com.incoresoft.middleware.audit.AuditEventCategory;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class AuditEventActionsManager {
    private final Map<UUID, AuditSourceActionContainer> registeredAuditActions = new ConcurrentHashMap<UUID, AuditSourceActionContainer>();

    public void registerAuditActions(String pluginId, Set<AuditEventAction> actions) {
        Optional<AuditEventAction> failed = actions.stream().filter(it -> this.registeredAuditActions.containsKey(it.getId())).findAny();
        if (failed.isPresent()) {
            AuditSourceActionContainer existing = this.registeredAuditActions.get(failed.get().getId());
            throw new AuditActionAlreadyExistException(existing, new AuditSourceActionContainer(pluginId, failed.get()));
        }
        this.registeredAuditActions.putAll(actions.stream().collect(Collectors.toMap(AuditEventAction::getId, it -> new AuditSourceActionContainer(pluginId, (AuditEventAction)it))));
    }

    public void unregisterAuditActions(String pluginId) {
        this.registeredAuditActions.values().stream().filter(it -> it.source().equalsIgnoreCase(pluginId)).map(it -> it.action().getId()).forEach(this.registeredAuditActions::remove);
    }

    public Optional<AuditEventAction> getAction(String name) {
        return this.registeredAuditActions.values().stream().map(AuditSourceActionContainer::action).filter(it -> it.getName().equals(name)).findFirst();
    }

    public Optional<AuditEventAction> getAction(UUID id) {
        return Optional.ofNullable(this.registeredAuditActions.get(id)).map(AuditSourceActionContainer::action);
    }

    public Optional<AuditEventCategory> getCategory(String name) {
        return this.registeredAuditActions.values().stream().map(it -> it.action().getCategory()).filter(it -> it.getName().equals(name)).findFirst();
    }

    public Map<AuditEventCategory, List<AuditEventAction>> getActionsMap() {
        return this.registeredAuditActions.values().stream().collect(Collectors.groupingBy(it -> it.action().getCategory(), Collectors.mapping(AuditSourceActionContainer::action, Collectors.toList())));
    }
}

