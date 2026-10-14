/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.windowsshare;

import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msfscc.FileAttributes;
import com.hierynomus.mssmb2.SMB2CreateDisposition;
import com.hierynomus.mssmb2.SMB2CreateOptions;
import com.hierynomus.mssmb2.SMB2ShareAccess;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import com.incoresoft.middleware.windowsshare.WindowsShareConfig;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class SmbFileInputStream
extends InputStream {
    private final WindowsShareConfig config;
    private final String path;
    private SMBClient client;
    private Connection connection;
    private Session session;
    private DiskShare diskShare;
    private InputStream inner;

    public SmbFileInputStream(@NotNull WindowsShareConfig config, @NotNull String path) {
        this.config = Objects.requireNonNull(config);
        this.path = Objects.requireNonNull(path);
        this.open();
    }

    private void open() {
        try {
            this.client = new SMBClient();
            this.connection = this.client.connect(this.config.getHostname());
            AuthenticationContext ac = new AuthenticationContext(this.config.getUsername(), this.config.getPassword().toCharArray(), this.config.getDomain());
            this.session = this.connection.authenticate(ac);
            this.diskShare = (DiskShare)this.session.connectShare(this.config.getShareName());
            this.inner = this.diskShare.openFile(this.path, Set.of(AccessMask.GENERIC_READ), Set.of(FileAttributes.FILE_ATTRIBUTE_NORMAL), SMB2ShareAccess.ALL, SMB2CreateDisposition.FILE_OPEN, Set.of(SMB2CreateOptions.FILE_DIRECTORY_FILE)).getInputStream();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int read() throws IOException {
        return this.inner.read();
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.inner.close();
        this.diskShare.close();
        this.session.close();
        this.connection.close();
        this.client.close();
    }
}

