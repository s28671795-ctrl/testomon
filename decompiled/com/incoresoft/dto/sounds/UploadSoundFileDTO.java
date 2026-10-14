/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.sounds;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

@Schema(name="UploadSoundFileDTO")
public class UploadSoundFileDTO {
    @JsonProperty(value="id")
    @JSONField(name="id")
    @Schema(name="id")
    @NotBlank
    @NotNull
    private String id;
    @JsonProperty(value="file")
    @JSONField(name="file")
    @Schema(name="file", description="Uploaded file", type="string", format="binary")
    private String soundFile;

    @NotNull
    public String getId() {
        return this.id;
    }

    public String getSoundFile() {
        return this.soundFile;
    }

    @JsonProperty(value="id")
    public void setId(@NotNull String id) {
        if (id == null) {
            throw new NullPointerException("id is marked non-null but is null");
        }
        this.id = id;
    }

    @JsonProperty(value="file")
    public void setSoundFile(String soundFile) {
        this.soundFile = soundFile;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UploadSoundFileDTO)) {
            return false;
        }
        UploadSoundFileDTO other = (UploadSoundFileDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        String this$soundFile = this.getSoundFile();
        String other$soundFile = other.getSoundFile();
        return !(this$soundFile == null ? other$soundFile != null : !this$soundFile.equals(other$soundFile));
    }

    protected boolean canEqual(Object other) {
        return other instanceof UploadSoundFileDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $soundFile = this.getSoundFile();
        result = result * 59 + ($soundFile == null ? 43 : $soundFile.hashCode());
        return result;
    }

    public String toString() {
        return "UploadSoundFileDTO(id=" + this.getId() + ", soundFile=" + this.getSoundFile() + ")";
    }

    public UploadSoundFileDTO() {
    }

    public UploadSoundFileDTO(@NotNull String id, String soundFile) {
        if (id == null) {
            throw new NullPointerException("id is marked non-null but is null");
        }
        this.id = id;
        this.soundFile = soundFile;
    }
}

