/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.dto;

import com.incoresoft.middleware.analytics.dto.analytics.VideoAnalyticsDTO;
import com.incoresoft.plugin.commons.analytics.dto.validations.AnalyticsAndParentMustNotBeFromDifferentPlugins;
import com.incoresoft.plugin.commons.analytics.dto.validations.AnalyticsParentMustExist;
import com.incoresoft.plugin.commons.analytics.dto.validations.ParentIdValidatable;
import com.incoresoft.plugin.commons.analytics.dto.validations.StreamIdValidatable;
import com.incoresoft.plugin.commons.analytics.dto.validations.StreamShouldExist;

@AnalyticsParentMustExist
@AnalyticsAndParentMustNotBeFromDifferentPlugins
@StreamShouldExist
public class PluginAnalyticsWithGroupDTO
extends VideoAnalyticsDTO
implements ParentIdValidatable,
StreamIdValidatable {
}

