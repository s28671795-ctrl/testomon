/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.notifications;

import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.notifications.Notification;
import com.incoresoft.middleware.notifications.NotificationItem;
import com.incoresoft.middleware.notifications.NotificationListener;
import com.incoresoft.middleware.notifications.NotificationsResult;
import com.incoresoft.middleware.plugin.AccessibleView;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class NotificationsManager<T extends Notification>
implements AccessibleView {
    protected final Set<NotificationListener> listeners = Collections.newSetFromMap(new ConcurrentHashMap());
    private final String pluginName;

    protected NotificationsManager(String pluginName) {
        this.pluginName = Objects.requireNonNull(pluginName);
    }

    public void addListener(NotificationListener listener) {
        this.listeners.add(Objects.requireNonNull(listener));
    }

    public void removeListener(NotificationListener listener) {
        if (listener == null) {
            return;
        }
        this.listeners.remove(listener);
    }

    public void send(NotificationItem<T> notification) {
        Objects.requireNonNull(notification);
        this.onSend((Notification)notification.getNotification());
    }

    public void accept(User user, Notification notification) {
        Objects.requireNonNull(user);
        this.onAccept(user, notification);
    }

    public String getId() {
        return this.pluginName;
    }

    public String getHref() {
        return "/" + this.pluginName + "/events";
    }

    public abstract NotificationsResult getNotifications(User var1);

    public abstract String getTitleLocaleKey();

    public abstract boolean userAllowedToSeeNotification(User var1, Notification var2);

    public abstract Notification get(long var1);

    protected void onSend(Notification notification) {
        for (NotificationListener listener : this.listeners) {
            listener.onNotificationAdded(this, notification);
        }
    }

    protected void onAccept(User user, Notification notification) {
        for (NotificationListener listener : this.listeners) {
            listener.onNotificationAccepted(this, user, notification);
        }
    }

    public void acceptAll(User user) {
        Objects.requireNonNull(user);
        this.onAcceptAll(user);
    }

    protected void onAcceptAll(User user) {
        for (NotificationListener listener : this.listeners) {
            listener.onAllNotificationsAccepted(this, user);
        }
    }

    public String getPluginName() {
        return this.pluginName;
    }
}

