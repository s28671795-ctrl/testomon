/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.plugins.extensions;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

@Schema(name="ComponentDTO", description="Component class structure for frontend")
public record ComponentDTO(@JSONField(name="component") @Schema(name="component", description="Component name", implementation=String.class, example="api") String component, @JSONField(name="path") @Schema(name="path", description="Component path", implementation=String.class, example="event_manager/api/app.js") String path, @JSONField(name="active_route_sidebar") @Schema(name="active_route_sidebar", description="Active sidebar page", implementation=String.class, example="/analytics") String activeRouteSidebar, @JSONField(name="type") @Schema(name="type", description="Component type", implementation=String.class, example="notification_block") String type, @JSONField(name="permissions") @Schema(name="permissions", description="Id of required permission for this component", implementation=String.class, example="view_streams") Set<String> permissions) {
}

