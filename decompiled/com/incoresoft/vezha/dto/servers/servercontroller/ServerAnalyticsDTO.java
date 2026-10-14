/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.servers.servercontroller;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.analytics.BaseAnalytics;
import com.incoresoft.vezha.analytics.servers.Server;
import com.incoresoft.vezha.analytics.status.AnalyticsStatusImpl;
import com.incoresoft.vezha.dto.servers.servercontroller.AnalyticsHeartBeatDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Optional;
import javax.validation.constraints.NotBlank;

@Schema(name="ServerAnalyticsDTO", description="Server analytics class structure")
public final class ServerAnalyticsDTO {
    @JSONField(name="id")
    @Schema(name="id", description="Server id.", implementation=String.class, example="1", nullable=true)
    private final String id;
    @JSONField(name="name")
    @Schema(name="name", description="Custom server name.", example="LPR Server")
    @NotBlank
    private final String name;
    @JSONField(name="heartBeat")
    @Schema(name="heartbeat", description="Heartbeat information", implementation=AnalyticsHeartBeatDTO.class)
    private final AnalyticsHeartBeatDTO heartBeat;

    public ServerAnalyticsDTO(BaseAnalytics it) {
        this.id = Optional.ofNullable(it.getAnalyticsStatus()).map(AnalyticsStatusImpl::getServerValue).map(Server::getId).orElse(null);
        this.name = it.getName();
        this.heartBeat = Optional.ofNullable(it.getHeartBeatValue()).map(AnalyticsHeartBeatDTO::new).orElse(null);
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public AnalyticsHeartBeatDTO getHeartBeat() {
        return this.heartBeat;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ServerAnalyticsDTO)) {
            return false;
        }
        ServerAnalyticsDTO other = (ServerAnalyticsDTO)o;
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        AnalyticsHeartBeatDTO this$heartBeat = this.getHeartBeat();
        AnalyticsHeartBeatDTO other$heartBeat = other.getHeartBeat();
        return !(this$heartBeat == null ? other$heartBeat != null : !((Object)this$heartBeat).equals(other$heartBeat));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        AnalyticsHeartBeatDTO $heartBeat = this.getHeartBeat();
        result = result * 59 + ($heartBeat == null ? 43 : ((Object)$heartBeat).hashCode());
        return result;
    }

    public String toString() {
        return "ServerAnalyticsDTO(id=" + this.getId() + ", name=" + this.getName() + ", heartBeat=" + String.valueOf(this.getHeartBeat()) + ")";
    }

    public ServerAnalyticsDTO(String id, String name, AnalyticsHeartBeatDTO heartBeat) {
        this.id = id;
        this.name = name;
        this.heartBeat = heartBeat;
    }
}

