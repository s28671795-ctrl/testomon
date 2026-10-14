/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.models.alpr.countrypatterns.CountriesManager;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.vezha.dto.countries.CountryDTO;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API", version="v2"))
public class ApiCountriesControllerV2
implements Controller {
    private static final String API_PATH = "/api/v2/countries";
    private final CountriesManager countriesList;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> ApiBuilder.get(this::getCountries, AppRole.ANYONE)));
    }

    @GET
    @Path(value="/api/v2/countries")
    @Operation(tags={"Countries"}, summary="Get list of countries", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", array=@ArraySchema(arraySchema=@Schema(description="List of clients"), schema=@Schema(implementation=CountryDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getCountries(@Parameter(hidden=true) Context ctx) {
        List<CountryDTO> result = this.countriesList.getAll().stream().map(CountryDTO::new).toList();
        ctx.json(result);
    }

    @Inject
    public ApiCountriesControllerV2(CountriesManager countriesList) {
        this.countriesList = countriesList;
    }
}

