/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.analytics.servers.models.GpuCard;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;

@Schema(name="GpuCardDTO", description="Graphic adapter class structure")
public final class GpuCardDTO {
    @JSONField(name="id")
    @Schema(name="id", description="Graphic adapter id", implementation=Integer.class, example="0")
    private final int id;
    @JSONField(name="name")
    @Schema(name="name", description="Graphic adapter name", implementation=String.class, example="NVIDIA GeForce RTX 3070 Ti")
    private final String name;
    @JSONField(name="gpu")
    @Schema(name="gpu", description="Graphic adapter utilization in percents.", implementation=Double.class, example="32.41")
    private final double gpu;
    @JSONField(name="nv_dec")
    @Schema(name="nv_dec", description="NVDEC utilization in percents.", implementation=Double.class, example="32.41")
    private final double nvDec;
    @JSONField(name="nv_enc")
    @Schema(name="nv_enc", description="NVENC utilization in percents.", implementation=Double.class, example="32.41")
    private final double nvEnc;
    @JSONField(name="total_mem")
    @Schema(name="total_mem", description="Total amount of graphics adapter memory in GiB.", implementation=Double.class, example="7.79")
    private final double totalMem;
    @JSONField(name="free_mem")
    @Schema(name="free_mem", description="Amount of available graphics adapter memory in GiB.", implementation=Double.class, example="5.09")
    private final double freeMem;

    public GpuCardDTO(@NotNull GpuCard gpuCard) {
        this.id = gpuCard.getId();
        this.name = gpuCard.getName();
        this.gpu = gpuCard.getGpuUsage();
        this.nvDec = gpuCard.getNvDec();
        this.nvEnc = gpuCard.getNvEnc();
        this.totalMem = gpuCard.getTotalMem();
        this.freeMem = gpuCard.getFreeMemory().get();
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getGpu() {
        return this.gpu;
    }

    public double getNvDec() {
        return this.nvDec;
    }

    public double getNvEnc() {
        return this.nvEnc;
    }

    public double getTotalMem() {
        return this.totalMem;
    }

    public double getFreeMem() {
        return this.freeMem;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GpuCardDTO)) {
            return false;
        }
        GpuCardDTO other = (GpuCardDTO)o;
        if (this.getId() != other.getId()) {
            return false;
        }
        if (Double.compare(this.getGpu(), other.getGpu()) != 0) {
            return false;
        }
        if (Double.compare(this.getNvDec(), other.getNvDec()) != 0) {
            return false;
        }
        if (Double.compare(this.getNvEnc(), other.getNvEnc()) != 0) {
            return false;
        }
        if (Double.compare(this.getTotalMem(), other.getTotalMem()) != 0) {
            return false;
        }
        if (Double.compare(this.getFreeMem(), other.getFreeMem()) != 0) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        return !(this$name == null ? other$name != null : !this$name.equals(other$name));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        long $gpu = Double.doubleToLongBits(this.getGpu());
        result = result * 59 + (int)($gpu >>> 32 ^ $gpu);
        long $nvDec = Double.doubleToLongBits(this.getNvDec());
        result = result * 59 + (int)($nvDec >>> 32 ^ $nvDec);
        long $nvEnc = Double.doubleToLongBits(this.getNvEnc());
        result = result * 59 + (int)($nvEnc >>> 32 ^ $nvEnc);
        long $totalMem = Double.doubleToLongBits(this.getTotalMem());
        result = result * 59 + (int)($totalMem >>> 32 ^ $totalMem);
        long $freeMem = Double.doubleToLongBits(this.getFreeMem());
        result = result * 59 + (int)($freeMem >>> 32 ^ $freeMem);
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public String toString() {
        return "GpuCardDTO(id=" + this.getId() + ", name=" + this.getName() + ", gpu=" + this.getGpu() + ", nvDec=" + this.getNvDec() + ", nvEnc=" + this.getNvEnc() + ", totalMem=" + this.getTotalMem() + ", freeMem=" + this.getFreeMem() + ")";
    }

    public GpuCardDTO(int id, String name, double gpu, double nvDec, double nvEnc, double totalMem, double freeMem) {
        this.id = id;
        this.name = name;
        this.gpu = gpu;
        this.nvDec = nvDec;
        this.nvEnc = nvEnc;
        this.totalMem = totalMem;
        this.freeMem = freeMem;
    }
}

