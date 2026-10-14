/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.groups;

import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsGroup;
import com.incoresoft.middleware.analytics.AnalyticsGroupsManager;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.vezha.analytics.exceptions.AnalyticsIsNotStoppedException;
import com.incoresoft.vezha.analytics.groups.AnalyticsRecursiveGroupDeleteStrategy;
import com.incoresoft.vezha.database.AnalyticsGroupsRepository;
import com.incoresoft.vezha.database.AnalyticsRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyticsGroupsManagerImpl
implements AnalyticsGroupsManager {
    private static final Logger log = LoggerFactory.getLogger(AnalyticsGroupsManagerImpl.class);
    private final AnalyticsGroupsRepository groupsRepository;
    private final AnalyticsManager analyticsManager;
    private final AnalyticsRepository analyticsRepository;
    private static final AnalyticsGroup ROOT_GROUP = new AnalyticsGroup(0, "root", "", -1, 0);

    @Override
    public AnalyticsGroup getRootGroup() {
        return ROOT_GROUP;
    }

    @Override
    public Optional<AnalyticsGroup> get(int id) {
        return this.groupsRepository.get(id);
    }

    @Override
    public List<AnalyticsGroup> getAll() {
        return this.groupsRepository.getAll();
    }

    @Override
    public List<AnalyticsGroup> getAll(String pluginId, User user) {
        return this.groupsRepository.getAll(pluginId, user);
    }

    @Override
    public AnalyticsGroup add(AnalyticsGroup group) {
        return this.groupsRepository.add(group);
    }

    @Override
    public AnalyticsGroup update(AnalyticsGroup group) {
        return this.groupsRepository.update(group);
    }

    @Override
    public void delete(int id) {
        Optional<Analytics> runningAnalytics = this.analyticsRepository.getByGroupId(id).stream().filter(it -> it.getStatus() != AnalyticsStatus.stopped).findFirst();
        if (runningAnalytics.isPresent()) {
            throw new AnalyticsIsNotStoppedException(runningAnalytics.get().getId());
        }
        new AnalyticsRecursiveGroupDeleteStrategy(this.groupsRepository, this.analyticsManager).delete(id);
    }

    @Override
    public boolean isGroupParentToItself(AnalyticsGroup group) {
        HashSet<Integer> restrictedParentIds = new HashSet<Integer>();
        AnalyticsGroup currentGroup = group;
        while (!restrictedParentIds.contains(ROOT_GROUP.getId())) {
            if (restrictedParentIds.contains(currentGroup.getId())) {
                return true;
            }
            restrictedParentIds.add(currentGroup.getId());
            Optional<AnalyticsGroup> groupOptional = this.get(currentGroup.getParentId());
            if (groupOptional.isEmpty()) {
                return false;
            }
            currentGroup = groupOptional.get();
        }
        return false;
    }

    @Override
    public void stopByGroup(int groupId) {
        for (Analytics analytics : this.analyticsRepository.getByGroupId(groupId)) {
            try {
                this.analyticsManager.stop(analytics.getId());
            }
            catch (Exception e) {
                log.error("Failed to stop analytics {}", (Object)analytics.getId(), (Object)e);
            }
        }
    }

    @Override
    public void startByGroup(int groupId) {
        for (Analytics analytics : this.analyticsRepository.getByGroupId(groupId)) {
            try {
                this.analyticsManager.start(analytics.getId());
            }
            catch (Exception e) {
                log.error("Failed to start analytics {}", (Object)analytics.getId(), (Object)e);
            }
        }
    }

    @Override
    public boolean exists(int groupId) {
        if (groupId == ROOT_GROUP.getId()) {
            return true;
        }
        return this.groupsRepository.get(groupId).isPresent();
    }

    @Inject
    public AnalyticsGroupsManagerImpl(AnalyticsGroupsRepository groupsRepository, AnalyticsManager analyticsManager, AnalyticsRepository analyticsRepository) {
        this.groupsRepository = groupsRepository;
        this.analyticsManager = analyticsManager;
        this.analyticsRepository = analyticsRepository;
    }
}

