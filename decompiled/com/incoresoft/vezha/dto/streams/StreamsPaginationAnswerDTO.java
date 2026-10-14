/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams;

import com.incoresoft.plugin.commons.http.answers.PaginationAnswer;
import com.incoresoft.vezha.dto.streams.FullStreamDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name="StreamsPaginationAnswerDTO", description="Streams pagination answer class description")
public class StreamsPaginationAnswerDTO
extends PaginationAnswer<FullStreamDTO> {
    public StreamsPaginationAnswerDTO(List<FullStreamDTO> data, long total, int pageSize) {
        super(data, total, pageSize);
    }
}

