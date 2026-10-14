/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.notifications;

import com.google.inject.Inject;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.notifications.Notification;
import com.incoresoft.middleware.notifications.NotificationListener;
import com.incoresoft.middleware.notifications.NotificationsManager;
import com.incoresoft.middleware.notifications.NotificationsPanel;
import com.incoresoft.middleware.notifications.NotificationsResult;
import com.incoresoft.vezha.dto.notificationspanel.NotificationItemDTO;
import com.incoresoft.vezha.protocol.ws.NotificationsWSBroadcaster;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationsPanelImpl
implements NotificationsPanel,
NotificationListener {
    private final Map<String, NotificationsManager> managerMap = new ConcurrentHashMap<String, NotificationsManager>();
    private final NotificationsWSBroadcaster notificationsWSBroadcaster;

    @Override
    public Collection<NotificationsManager> getNotificationsManagers() {
        return List.copyOf(this.managerMap.values());
    }

    @Override
    public Collection<NotificationsResult> getNotificationsResult(User user) {
        return this.getNotificationsManagers().stream().map(manager -> manager.getNotifications(user)).toList();
    }

    @Override
    public void registerManager(NotificationsManager notificationsManager) {
        Objects.requireNonNull(notificationsManager);
        String managerId = notificationsManager.getId();
        if (this.managerMap.containsKey(managerId)) {
            throw new IllegalArgumentException(String.format("Manager with id \"%s\" already registered", managerId));
        }
        notificationsManager.addListener(this);
        this.managerMap.put(managerId, notificationsManager);
    }

    @Override
    public void unregisterManager(NotificationsManager notificationsManager) {
        if (notificationsManager == null) {
            return;
        }
        notificationsManager.removeListener(this);
        this.managerMap.remove(notificationsManager.getId());
    }

    public void onNotificationAdded(NotificationsManager notificationsManager, Notification notification) {
        this.notificationsWSBroadcaster.broadcast(user -> user.hasAccessTo(notification) && notificationsManager.userAllowedToSeeNotification((User)user, notification), user -> new NotificationItemDTO(notificationsManager.getId(), notification));
    }

    public void onNotificationAccepted(NotificationsManager notificationsManager, User user, Notification notificationItem) {
    }

    public void onAllNotificationsAccepted(NotificationsManager notificationsManager, User user) {
    }

    @Inject
    public NotificationsPanelImpl(NotificationsWSBroadcaster notificationsWSBroadcaster) {
        this.notificationsWSBroadcaster = notificationsWSBroadcaster;
    }
}

