/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.modules;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.zafarkhaja.semver.Version;
import com.incoresoft.license.model.LPRLicenseType;
import com.incoresoft.middleware.analytics.dto.restrictions.ItemRestrictionsDTO;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.plugin.commons.params.ZonedDateTimeSerializer;
import com.incoresoft.plugin.commons.serialization.InstantEpochSerializer;
import com.incoresoft.plugins.broadcast.events.PluginEvent;
import com.incoresoft.plugins.models.PluginType;
import com.incoresoft.user.UserDateFormat;
import com.incoresoft.user.UserDistanceUnit;
import com.incoresoft.user.UserFirstDayOfWeek;
import com.incoresoft.user.UserTemperatureUnit;
import com.incoresoft.user.UserTimeFormat;
import com.incoresoft.version.models.MinorVersion;
import com.incoresoft.vezha.backup.ws.message.BackupWSMessage;
import com.incoresoft.vezha.backup.ws.message.BackupWsMessageDeserializer;
import com.incoresoft.vezha.models.users.serializers.UserDateFormatSerializerDeserializer;
import com.incoresoft.vezha.models.users.serializers.UserDistanceUnitSerializerDeserializer;
import com.incoresoft.vezha.models.users.serializers.UserFirstDayOfWeekSerializerDeserializer;
import com.incoresoft.vezha.models.users.serializers.UserTemperatureUnitSerializerDeserializer;
import com.incoresoft.vezha.models.users.serializers.UserTimeFormatSerializerDeserializer;
import com.incoresoft.vezha.plugins.FastjsonPluginInstallationEventDeserializer;
import com.incoresoft.vezha.plugins.FastjsonPluginTypeSerializer;
import com.incoresoft.vezha.version.MinorVersionDeserializer;
import com.incoresoft.vezha.version.MinorVersionSerializer;
import com.incoresoft.vezha.version.VersionDeserializer;
import com.incoresoft.vezha.version.VersionSerializer;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZonedDateTime;

public class JSONBaseSerializerDeserializerInit {
    public static void init() {
        JSONBaseSerializerDeserializerInit.initSerializer();
        JSONBaseSerializerDeserializerInit.initDeserializer();
    }

    private static void initDeserializer() {
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)Instant.class), new InstantEpochSerializer());
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)ItemRestrictionsDTO.class), new ItemRestrictionsDTO.Serializer());
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)BackupWSMessage.class), new BackupWsMessageDeserializer());
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)PluginEvent.class), new FastjsonPluginInstallationEventDeserializer());
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)Version.class), new VersionDeserializer());
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)MinorVersion.class), new MinorVersionDeserializer());
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)LPRLicenseType.class), new LPRLicenseType.Deserializer());
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)UserDateFormat.class), new UserDateFormatSerializerDeserializer());
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)UserTimeFormat.class), new UserTimeFormatSerializerDeserializer());
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)UserDistanceUnit.class), new UserDistanceUnitSerializerDeserializer());
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)UserTemperatureUnit.class), new UserTemperatureUnitSerializerDeserializer());
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)UserFirstDayOfWeek.class), new UserFirstDayOfWeekSerializerDeserializer());
    }

    private static void initSerializer() {
        SerializeConfig.getGlobalInstance().put((Type)((Object)Instant.class), new InstantEpochSerializer());
        SerializeConfig.getGlobalInstance().put((Type)((Object)ZonedDateTime.class), new ZonedDateTimeSerializer());
        SerializeConfig.getGlobalInstance().put((Type)((Object)Version.class), new VersionSerializer());
        SerializeConfig.getGlobalInstance().put((Type)((Object)MinorVersion.class), new MinorVersionSerializer());
        SerializeConfig.getGlobalInstance().put((Type)((Object)ErrorAnswer.class), new ErrorAnswer.Serializer());
        SerializeConfig.getGlobalInstance().put((Type)((Object)PluginType.class), new FastjsonPluginTypeSerializer());
        SerializeConfig.getGlobalInstance().put((Type)((Object)LPRLicenseType.class), new LPRLicenseType.Serializer());
        SerializeConfig.getGlobalInstance().put((Type)((Object)UserDateFormat.class), new UserDateFormatSerializerDeserializer());
        SerializeConfig.getGlobalInstance().put((Type)((Object)UserTimeFormat.class), new UserTimeFormatSerializerDeserializer());
        SerializeConfig.getGlobalInstance().put((Type)((Object)UserDistanceUnit.class), new UserDistanceUnitSerializerDeserializer());
        SerializeConfig.getGlobalInstance().put((Type)((Object)UserTemperatureUnit.class), new UserTemperatureUnitSerializerDeserializer());
        SerializeConfig.getGlobalInstance().put((Type)((Object)UserFirstDayOfWeek.class), new UserFirstDayOfWeekSerializerDeserializer());
    }

    static {
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
    }
}

