/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.plugins;

import com.incoresoft.dto.plugins.InstalledPluginInfoDTO;
import com.incoresoft.plugin.commons.http.answers.PaginationAnswer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name="InstalledPluginsPaginationAnswerDTO", description="Pagination answer for installed plugins")
public class InstalledPluginsPaginationAnswerDTO
extends PaginationAnswer<InstalledPluginInfoDTO> {
    public InstalledPluginsPaginationAnswerDTO(List<InstalledPluginInfoDTO> data, long total, int pageSize) {
        super(data, total, pageSize);
    }
}

