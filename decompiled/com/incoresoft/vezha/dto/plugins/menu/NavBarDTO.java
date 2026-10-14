/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.plugins.menu;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.dto.plugins.menu.GroupedMenuDTO;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name="NavBarDTO", description="Navigation bar class structure")
public final class NavBarDTO {
    @JSONField(name="navbar")
    @ArraySchema(schema=@Schema(name="navbar", description="Navigation bar array", implementation=GroupedMenuDTO.class))
    private final List<GroupedMenuDTO> navbar;

    public List<GroupedMenuDTO> getNavbar() {
        return this.navbar;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof NavBarDTO)) {
            return false;
        }
        NavBarDTO other = (NavBarDTO)o;
        List<GroupedMenuDTO> this$navbar = this.getNavbar();
        List<GroupedMenuDTO> other$navbar = other.getNavbar();
        return !(this$navbar == null ? other$navbar != null : !((Object)this$navbar).equals(other$navbar));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        List<GroupedMenuDTO> $navbar = this.getNavbar();
        result = result * 59 + ($navbar == null ? 43 : ((Object)$navbar).hashCode());
        return result;
    }

    public String toString() {
        return "NavBarDTO(navbar=" + String.valueOf(this.getNavbar()) + ")";
    }

    public NavBarDTO(List<GroupedMenuDTO> navbar) {
        this.navbar = navbar;
    }
}

