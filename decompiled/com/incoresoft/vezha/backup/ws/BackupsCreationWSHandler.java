/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.ws;

import com.google.inject.Inject;
import com.incoresoft.backup.database.availability.BackupDatabaseAvailabilityManager;
import com.incoresoft.http.server.JavalinAvailabilityInterceptor;
import com.incoresoft.middleware.http.exceptions.ValidationException;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.vezha.backup.BackupCreationProcess;
import com.incoresoft.vezha.backup.BackupsManager;
import com.incoresoft.vezha.backup.exceptions.BackupCreationNotStartedException;
import com.incoresoft.vezha.backup.listeners.creation.BackupCreationListener;
import com.incoresoft.vezha.backup.listeners.creation.LoggingBackupCreationListener;
import com.incoresoft.vezha.backup.listeners.creation.WSBackupCreationListener;
import com.incoresoft.vezha.backup.ws.message.BackupWSMessage;
import com.incoresoft.vezha.backup.ws.message.create.client.CancelBackupCreationMessage;
import com.incoresoft.vezha.backup.ws.message.create.client.ClientBackupCreationWSMessage;
import com.incoresoft.vezha.backup.ws.message.create.client.CreateBackupMessage;
import com.incoresoft.vezha.backup.ws.message.create.server.BackupCreationCancellationFailedMessage;
import com.incoresoft.vezha.models.users.UserImpl;
import com.incoresoft.vezha.models.users.UsersManagerImpl;
import com.incoresoft.vezha.protocol.ws.WSHandler;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsErrorContext;
import io.javalin.websocket.WsMessageContext;
import java.lang.runtime.SwitchBootstraps;
import java.nio.channels.ClosedChannelException;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackupsCreationWSHandler
extends WSHandler {
    private static final Logger log = LoggerFactory.getLogger(BackupsCreationWSHandler.class);
    private final JavalinAvailabilityInterceptor javalinAvailabilityInterceptor;
    private final BackupDatabaseAvailabilityManager databaseAvailabilityManager;
    private final BackupsManager backupsManager;
    private final UsersManagerImpl usersManager;
    private final BeanValidator beanValidator;
    private volatile UserImpl connectedUser;
    private volatile BackupCreationProcess currentBackupCreationProcess;

    @Inject
    public BackupsCreationWSHandler(JavalinAvailabilityInterceptor javalinAvailabilityInterceptor, BackupDatabaseAvailabilityManager databaseAvailabilityManager, BackupsManager backupsManager, UsersManagerImpl usersManager, BeanValidator beanValidator) {
        super(true);
        this.javalinAvailabilityInterceptor = javalinAvailabilityInterceptor;
        this.databaseAvailabilityManager = databaseAvailabilityManager;
        this.backupsManager = backupsManager;
        this.usersManager = usersManager;
        this.beanValidator = beanValidator;
    }

    @Override
    protected synchronized void onSessionConnect(WsConnectContext ctx) {
        this.validateNoUserConnected(ctx);
        this.connectedUser = Objects.requireNonNull(this.usersManager.getUser(ctx));
    }

    @Override
    protected synchronized void onMessage(WsMessageContext ctx) {
        try {
            BackupWSMessage message = ctx.messageAsClass(BackupWSMessage.class);
            if (message instanceof ClientBackupCreationWSMessage) {
                ClientBackupCreationWSMessage clientMessage;
                ClientBackupCreationWSMessage clientBackupCreationWSMessage = clientMessage = (ClientBackupCreationWSMessage)message;
                Objects.requireNonNull(clientBackupCreationWSMessage);
                ClientBackupCreationWSMessage clientBackupCreationWSMessage2 = clientBackupCreationWSMessage;
                int n = 0;
                switch (SwitchBootstraps.typeSwitch("typeSwitch", new Object[]{CreateBackupMessage.class, CancelBackupCreationMessage.class}, (Object)clientBackupCreationWSMessage2, n)) {
                    case 0: {
                        CreateBackupMessage m4 = (CreateBackupMessage)clientBackupCreationWSMessage2;
                        this.handleCreateBackupMessage(ctx, m4);
                        break;
                    }
                    case 1: {
                        CancelBackupCreationMessage m5 = (CancelBackupCreationMessage)clientBackupCreationWSMessage2;
                        this.handleCancelBackupCreationMessage(ctx);
                        break;
                    }
                    default: {
                        throw new IllegalStateException("Unexpected value: " + String.valueOf(clientMessage));
                    }
                }
            }
        }
        catch (Exception e) {
            log.error("Error while processing message", e);
            ctx.closeSession();
        }
    }

    private void handleCreateBackupMessage(WsMessageContext ctx, CreateBackupMessage message) {
        if (this.currentBackupCreationProcess != null && this.currentBackupCreationProcess.isRunning()) {
            log.warn("Backup creation is already started");
            return;
        }
        try {
            this.beanValidator.validateObjectOrThrow(message, new Class[0]);
        }
        catch (ValidationException e) {
            ctx.send(new ErrorAnswer(e));
            throw e;
        }
        List<BackupCreationListener> listeners = List.of(new WSBackupCreationListener(ctx), new LoggingBackupCreationListener());
        this.currentBackupCreationProcess = new BackupCreationProcess(this.connectedUser, this.backupsManager, message, listeners);
        this.currentBackupCreationProcess.start();
    }

    private void handleCancelBackupCreationMessage(WsMessageContext ctx) {
        try {
            if (this.currentBackupCreationProcess == null || !this.currentBackupCreationProcess.isRunning()) {
                throw new BackupCreationNotStartedException();
            }
            this.currentBackupCreationProcess.cancel();
            this.currentBackupCreationProcess = null;
            this.javalinAvailabilityInterceptor.setAvailable();
            this.databaseAvailabilityManager.setAvailable();
        }
        catch (BackupCreationNotStartedException e) {
            log.warn("Error while cancelling backup creation: it is not started");
        }
        catch (Exception e) {
            log.error("Error while cancelling backup creation", e);
            ctx.send(new BackupCreationCancellationFailedMessage());
        }
    }

    @Override
    protected void onClose(WsCloseContext wsCloseContext) {
        this.connectedUser = null;
    }

    @Override
    protected void onError(WsErrorContext wsErrorContext) {
        if (wsErrorContext.error() instanceof ClosedChannelException) {
            return;
        }
        log.error("Error while processing message", wsErrorContext.error());
        wsErrorContext.closeSession();
    }

    private void validateNoUserConnected(WsConnectContext ctx) {
        if (this.connectedUser != null) {
            ctx.closeSession();
        }
    }
}

