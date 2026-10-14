/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database.converters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONObject;
import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.dto.restrictions.ItemRestrictionsDTO;
import com.incoresoft.middleware.model.itemaccess.RoleUserRestrictions;
import com.incoresoft.vezha.models.streams.model.StreamRestrictions;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;

public class RestrictionsConverter
implements Converter<String, RoleUserRestrictions> {
    private final StreamRestrictions.Converter streamRestrictionsConverter;

    @Override
    public RoleUserRestrictions from(String s2) {
        ItemRestrictionsDTO itemRestrictionsDTO = Optional.ofNullable(s2).map(it -> JSON.parseObject(it, ItemRestrictionsDTO.class)).orElse(new ItemRestrictionsDTO(1, null, null, null));
        return this.streamRestrictionsConverter.convert(itemRestrictionsDTO);
    }

    @Override
    public String to(RoleUserRestrictions roleUserRestrictions) {
        return Optional.ofNullable(roleUserRestrictions).map(ItemRestrictionsDTO::new).map(JSON::toJSON).map(it -> (JSONObject)it).map(JSONAware::toJSONString).orElse(((JSONObject)JSONObject.toJSON(new ItemRestrictionsDTO(1, null, null, null))).toJSONString());
    }

    @Override
    @NotNull
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    @NotNull
    public Class<RoleUserRestrictions> toType() {
        return RoleUserRestrictions.class;
    }

    @Inject
    public RestrictionsConverter(StreamRestrictions.Converter streamRestrictionsConverter) {
        this.streamRestrictionsConverter = streamRestrictionsConverter;
    }
}

