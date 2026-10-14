/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams;

import com.incoresoft.middleware.analytics.dto.streams.StreamDTO;
import com.incoresoft.plugin.commons.http.answers.PaginationAnswer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name="ShortStreamsPaginationAnswerDTO", description="Short streams pagination answer class description")
public class ShortStreamsPaginationAnswerDTO
extends PaginationAnswer<StreamDTO> {
    public ShortStreamsPaginationAnswerDTO(List<StreamDTO> data, long total, int pageSize) {
        super(data, total, pageSize);
    }
}

