/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.params;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.model.clients.ClientId;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.utils.StatisticUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Schema(name="StreamsParams", description="Streams params")
public class StreamsParams {
    @JSONField(name="stream_ids")
    private Set<Integer> streamIds;
    @JSONField(name="radius")
    @Schema(name="radius", description="Radius")
    private double radius;
    @JSONField(name="lat")
    @Schema(name="lat", description="Latitude")
    private double latitude;
    @JSONField(name="lng")
    @Schema(name="lng", description="Longitude")
    private double longitude;

    public Set<Integer> getStreamIds(User user, StreamsManager streamsManager) {
        return this.getStreamIds(this.streamIds, user, streamsManager);
    }

    public Set<Integer> getVaIdsForUser(List<Integer> vaIds, User user, AnalyticsManager analyticsManager, StreamsManager streamsManager) {
        return this.getVaIdsForUser(new HashSet<Integer>(vaIds), user, analyticsManager, streamsManager);
    }

    public Set<Integer> getVaIdsForUser(Set<Integer> vaIds, User user, AnalyticsManager analyticsManager, StreamsManager streamsManager) {
        Predicate<Analytics> filter2 = it -> (vaIds == null || vaIds.contains(it.getId())) && user.hasAccessTo((ClientId)it);
        Collection<Analytics> selectedAnalytics = analyticsManager.getAll(filter2);
        Set<Integer> analyticsStreams = selectedAnalytics.stream().map(Analytics::getStreamId).collect(Collectors.toSet());
        Set<Integer> allowedStreamIds = this.getStreamIds(analyticsStreams, user, streamsManager);
        return selectedAnalytics.stream().filter(it -> allowedStreamIds.contains(it.getStreamId())).map(Analytics::getId).collect(Collectors.toSet());
    }

    public Set<Integer> getVaIdsForUser(int[] vaIds, User user, AnalyticsManager analyticsManager, StreamsManager streamsManager) {
        HashSet<Integer> ids = new HashSet<Integer>(Arrays.stream(vaIds).mapToObj(analyticsManager::get).map(Analytics::getStreamId).toList());
        return this.getVaIdsForUser(ids, user, analyticsManager, streamsManager);
    }

    private Set<Integer> getStreamIds(Set<Integer> ids, User user, StreamsManager streamsManager) {
        Collection allStreams = streamsManager.getAll();
        Set allowedStreams = allStreams.stream().filter(it -> ids == null || ids.contains(it.getId())).filter(user::hasAccessTo).filter(it -> it.getRestrictions().isAllowed(user, CorePermissions.ViewStreams)).collect(Collectors.toSet());
        if (this.radius > 0.0) {
            return StatisticUtils.getCamerasInRadius(allowedStreams, this.latitude, this.latitude, this.radius);
        }
        return allowedStreams.stream().map(Stream::getId).collect(Collectors.toSet());
    }

    public double getRadius() {
        return this.radius;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setStreamIds(Set<Integer> streamIds) {
        this.streamIds = streamIds;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String toString() {
        return "StreamsParams(streamIds=" + String.valueOf(this.streamIds) + ", radius=" + this.getRadius() + ", latitude=" + this.getLatitude() + ", longitude=" + this.getLongitude() + ")";
    }
}

