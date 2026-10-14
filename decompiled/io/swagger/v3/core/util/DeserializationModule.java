/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.util;

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.swagger.v3.core.util.ApiResponsesDeserializer;
import io.swagger.v3.core.util.CallbackDeserializer;
import io.swagger.v3.core.util.EncodingPropertyStyleEnumDeserializer;
import io.swagger.v3.core.util.EncodingStyleEnumDeserializer;
import io.swagger.v3.core.util.HeaderStyleEnumDeserializer;
import io.swagger.v3.core.util.ModelDeserializer;
import io.swagger.v3.core.util.ParameterDeserializer;
import io.swagger.v3.core.util.PathsDeserializer;
import io.swagger.v3.core.util.SecuritySchemeDeserializer;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.callbacks.Callback;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.media.Encoding;
import io.swagger.v3.oas.models.media.EncodingProperty;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityScheme;

public class DeserializationModule
extends SimpleModule {
    public DeserializationModule() {
        this.addDeserializer(Schema.class, new ModelDeserializer());
        this.addDeserializer(Parameter.class, new ParameterDeserializer());
        this.addDeserializer(Header.StyleEnum.class, new HeaderStyleEnumDeserializer());
        this.addDeserializer(Encoding.StyleEnum.class, new EncodingStyleEnumDeserializer());
        this.addDeserializer(EncodingProperty.StyleEnum.class, new EncodingPropertyStyleEnumDeserializer());
        this.addDeserializer(SecurityScheme.class, new SecuritySchemeDeserializer());
        this.addDeserializer(ApiResponses.class, new ApiResponsesDeserializer());
        this.addDeserializer(Paths.class, new PathsDeserializer());
        this.addDeserializer(Callback.class, new CallbackDeserializer());
    }
}

