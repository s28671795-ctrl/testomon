/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin.messages.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GCStats {
    @JsonProperty(value="last_gc")
    private String lastGC;
    @JsonProperty(value="num_gc")
    private Integer numGC;
    @JsonProperty(value="pause_total")
    private Long pauseTotal;
    @JsonProperty(value="pause")
    private List<Integer> pause;
    @JsonProperty(value="pause_end")
    private List<String> pauseEnd;

    public String lastGC() {
        return this.lastGC;
    }

    public Integer numGC() {
        return this.numGC;
    }

    public Long pauseTotal() {
        return this.pauseTotal;
    }

    public List<Integer> pause() {
        return Collections.unmodifiableList(this.pause == null ? new LinkedList() : this.pause);
    }

    public List<String> pauseEnd() {
        return Collections.unmodifiableList(this.pauseEnd == null ? new LinkedList() : this.pauseEnd);
    }
}

