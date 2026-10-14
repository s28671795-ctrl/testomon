/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.plugins;

import com.incoresoft.dto.plugins.MarketplacePluginInfoDTO;
import com.incoresoft.plugin.commons.http.answers.PaginationAnswer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name="MarketplacePluginInfoPaginationAnswerDTO", description="Marketplace plugin info pagination answer class structure")
public class MarketplacePluginInfoPaginationAnswerDTO
extends PaginationAnswer<MarketplacePluginInfoDTO> {
    public MarketplacePluginInfoPaginationAnswerDTO(List<MarketplacePluginInfoDTO> data, long total, int pageSize) {
        super(data, total, pageSize);
    }
}

