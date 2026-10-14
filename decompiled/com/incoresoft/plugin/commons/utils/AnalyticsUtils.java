/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.utils;

import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.params.AnalyticsParams;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class AnalyticsUtils {
    public static Optional<Analytics> getAnalytics(int id, AnalyticsManager analyticsManager) {
        return Optional.ofNullable((Analytics)analyticsManager.get(id)).or(() -> Optional.ofNullable((Analytics)analyticsManager.getAnalyticsArchive().get(id)));
    }

    public static Predicate<Analytics> getPredicate(AnalyticsParams params, User user, String pluginName, AnalyticsManager analyticsManager) {
        String matchingText = params.getMatchingText();
        AnalyticsStatus status = params.getStatus();
        ArrayList<Predicate<Analytics>> predicates = new ArrayList<Predicate<Analytics>>();
        Set allowedAnalyticsIds = analyticsManager.getAll(user).stream().map(Analytics::getId).collect(Collectors.toSet());
        if (params.isShowDeleted()) {
            allowedAnalyticsIds.addAll(analyticsManager.getAnalyticsArchive().getAll(user).stream().map(Analytics::getId).collect(Collectors.toSet()));
        }
        predicates.add(analytics -> allowedAnalyticsIds.contains(analytics.getId()));
        if (!StringUtils.isBlank(matchingText)) {
            Predicate<Analytics> matchingTextPredicate = analytics -> StringUtils.containsIgnoreCase(analytics.getName(), matchingText);
            if (StringUtils.isNumeric(matchingText)) {
                matchingTextPredicate = matchingTextPredicate.or(analytics -> Integer.toString(analytics.getId()).contains(matchingText));
            }
            predicates.add(matchingTextPredicate);
        }
        if (status != null) {
            predicates.add(analytics -> analytics.getStatus() == status);
        }
        predicates.add(analytics -> analytics.getPluginName().equals(pluginName));
        return analytics -> predicates.stream().allMatch(predicate -> predicate.test(analytics));
    }
}

