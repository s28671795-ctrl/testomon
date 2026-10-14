/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.paths;

import com.hierynomus.mssmb2.SMB2Packet;
import com.hierynomus.smbj.common.SmbPath;
import com.hierynomus.smbj.paths.PathResolveException;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.StatusHandler;

public interface PathResolver {
    public static final PathResolver LOCAL = new PathResolver(){

        @Override
        public <T> T resolve(Session session, SMB2Packet responsePacket, SmbPath smbPath, ResolveAction<T> action) throws PathResolveException {
            return action.apply(smbPath);
        }

        @Override
        public <T> T resolve(Session session, SmbPath smbPath, ResolveAction<T> action) {
            return action.apply(smbPath);
        }

        @Override
        public StatusHandler statusHandler() {
            return StatusHandler.SUCCESS;
        }
    };

    public <T> T resolve(Session var1, SMB2Packet var2, SmbPath var3, ResolveAction<T> var4) throws PathResolveException;

    public <T> T resolve(Session var1, SmbPath var2, ResolveAction<T> var3) throws PathResolveException;

    public StatusHandler statusHandler();

    public static interface ResolveAction<T> {
        public T apply(SmbPath var1);
    }
}

