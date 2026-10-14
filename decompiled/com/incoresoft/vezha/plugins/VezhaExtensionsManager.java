/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.plugins;

import com.google.inject.Inject;
import com.incoresoft.audit.AuditEventActionsManager;
import com.incoresoft.extensions.ExtensionsManager;
import com.incoresoft.middleware.audit.AuditEventAction;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.permission.PermissionGroup;
import com.incoresoft.middleware.model.permission.PluginPermissions;
import com.incoresoft.middleware.plugin.EventManagerExtension;
import com.incoresoft.middleware.plugin.ExtensionContext;
import com.incoresoft.middleware.plugin.InitializableExtension;
import com.incoresoft.middleware.plugin.SystemExtension;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.vezha.eventmanager.extensions.StartBlock;
import com.incoresoft.vezha.eventmanager.extensions.StopBlock;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VezhaExtensionsManager
extends ExtensionsManager {
    private static final Logger log = LoggerFactory.getLogger(VezhaExtensionsManager.class);
    private final AtomicReference<ExtensionContext> extensionContext = new AtomicReference();
    private final AuditEventActionsManager auditEventActionsManager;

    @Inject
    public void init(ExtensionContext extensionContext) {
        this.extensionContext.set(extensionContext);
        this.initExtensions("", this.getDefaultEventManagerExtensions());
    }

    private List<EventManagerExtension> getDefaultEventManagerExtensions() {
        return List.of(new StartBlock(), new StopBlock());
    }

    @Override
    public void initExtensions(String pluginId, Collection<? extends InitializableExtension<?>> extensions) {
        super.initExtensions(pluginId, extensions);
        Set<AuditEventAction> auditActions = extensions.stream().filter(it -> it instanceof SystemExtension).flatMap(it -> ((SystemExtension)it).customAuditActions().stream()).collect(Collectors.toSet());
        this.auditEventActionsManager.registerAuditActions(pluginId, auditActions);
    }

    @Override
    protected void initExtension(InitializableExtension<?> extension) {
        try {
            InitializableExtension<?> casted = extension;
            casted.init(this.extensionContext.get());
        }
        catch (ClassCastException e) {
            log.error("Extension {} could not be initialized: it is not compatible with ExtensionContext", (Object)extension.getClass().getName());
        }
    }

    @Override
    public void terminateExtensions(@NotNull String pluginId) {
        super.terminateExtensions(pluginId);
        this.auditEventActionsManager.unregisterAuditActions(pluginId);
    }

    public Map<VezhaExtension, PluginPermissions> getPluginPermissionsMap() {
        return this.getAll(VezhaExtension.class).stream().filter(it -> it.customPermissions() != null).collect(Collectors.toMap(Function.identity(), VezhaExtension::customPermissions));
    }

    public Class<? extends AnalyticsModule> getExtensionsModuleClass(String className) {
        return this.getModuleClassesMap().entrySet().stream().filter(it -> ((String)it.getKey()).contains(className)).findFirst().map(Map.Entry::getValue).orElse(null);
    }

    private Map<String, Class<? extends AnalyticsModule>> getModuleClassesMap() {
        return this.getAll(VezhaExtension.class).stream().map(vezhaExtension -> {
            try {
                return vezhaExtension.getModuleClass();
            }
            catch (Throwable e) {
                log.error("Couldn't retrieve module class from extension {}.", (Object)vezhaExtension.getClass().getName(), (Object)e);
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toMap(Class::getName, Function.identity(), (a, b) -> a));
    }

    public Collection<Permission> getPermissions() {
        List allPermissions = Arrays.stream(CorePermissions.values()).collect(Collectors.toCollection(LinkedList::new));
        Collection<PluginPermissions> pluginPermissions = this.getAllVezhaExtensionPermissions();
        for (PluginPermissions pluginPermission : pluginPermissions) {
            allPermissions.addAll(pluginPermission.getPermissions());
            for (PermissionGroup group : pluginPermission.getGroups()) {
                allPermissions.addAll(group.getPermissions());
            }
        }
        return Collections.unmodifiableList(allPermissions);
    }

    private Collection<PluginPermissions> getAllVezhaExtensionPermissions() {
        return this.getAll(VezhaExtension.class).stream().map(VezhaExtension::customPermissions).filter(Objects::nonNull).toList();
    }

    @Inject
    public VezhaExtensionsManager(AuditEventActionsManager auditEventActionsManager) {
        this.auditEventActionsManager = auditEventActionsManager;
    }
}

