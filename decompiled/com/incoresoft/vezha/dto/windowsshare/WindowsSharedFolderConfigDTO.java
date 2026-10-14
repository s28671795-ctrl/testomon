/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.windowsshare;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.windowsshare.WindowsShareConfig;
import com.incoresoft.vezha.windowsshare.WindowsShareConfigImpl;
import com.incoresoft.vezha.windowsshare.validation.AlreadyExists;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

@AlreadyExists
@Schema(name="WindowsSharedFolderConfigDTO", description="Windows shared folder configuration object structure")
public class WindowsSharedFolderConfigDTO {
    @JSONField(name="id")
    @Schema(name="id", description="Configuration Id", implementation=Integer.class, example="1")
    private Integer id;
    @NotEmpty
    @JSONField(name="host")
    @Schema(name="host", description="Shared folder hostname", implementation=String.class, example="192.168.1.100")
    private String host;
    @JSONField(name="username")
    @Schema(name="username", description="Shared folder username", implementation=String.class, example="admin")
    private String username;
    @JSONField(name="password")
    @Schema(name="password", description="Shared folder password", implementation=String.class, example="admin")
    private String password;
    @JSONField(name="domain")
    @Schema(name="domain", description="Shared folder domain", implementation=String.class, example="")
    private String domain;
    @NotEmpty
    @JSONField(name="share_name")
    @Schema(name="share_name", description="Shared folder name", implementation=String.class, example="Shared")
    private String shareName;

    public WindowsSharedFolderConfigDTO() {
    }

    public WindowsSharedFolderConfigDTO(@NotNull WindowsShareConfig config) {
        this.id = config.getId();
        this.host = config.getHostname();
        this.username = config.getUsername();
        this.password = config.getPassword();
        this.domain = config.getDomain();
        this.shareName = config.getShareName();
    }

    public void hidePassword() {
        this.setPassword(StringUtils.repeat('*', this.getPassword().length()));
    }

    public WindowsShareConfig toModel() {
        return new WindowsShareConfigImpl(this.getId(), this.getHost(), this.getUsername(), this.getPassword(), this.getDomain(), this.getShareName());
    }

    public Integer getId() {
        return this.id;
    }

    public String getHost() {
        return this.host;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getDomain() {
        return this.domain;
    }

    public String getShareName() {
        return this.shareName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setHost(String host2) {
        this.host = host2;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }
}

