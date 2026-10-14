/*
 * Decompiled with CFR 0.152.
 */
package liquibase.ui;

import java.util.logging.Level;
import liquibase.AbstractExtensibleObject;
import liquibase.Scope;
import liquibase.ui.InputHandler;
import liquibase.ui.UIService;

public class LoggerUIService
extends AbstractExtensibleObject
implements UIService {
    private Level standardLogLevel = Level.INFO;
    private Level errorLogLevel = Level.SEVERE;

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public void sendMessage(String message) {
        Scope.getCurrentScope().getLog(this.getClass()).log(this.standardLogLevel, message, null);
    }

    @Override
    public void sendErrorMessage(String message) {
        this.sendErrorMessage(message, null);
    }

    @Override
    public void sendErrorMessage(String message, Throwable exception) {
        Scope.getCurrentScope().getLog(this.getClass()).log(this.errorLogLevel, message, exception);
    }

    @Override
    public void setAllowPrompt(boolean allowPrompt) throws IllegalArgumentException {
        if (allowPrompt) {
            throw new IllegalArgumentException("allowPrompt=true not allowed in LoggerUIService");
        }
    }

    @Override
    public boolean getAllowPrompt() {
        return false;
    }

    @Override
    public <T> T prompt(String prompt, T valueIfNoEntry, InputHandler<T> inputHandler, Class<T> type) {
        return valueIfNoEntry;
    }

    public Level getStandardLogLevel() {
        return this.standardLogLevel;
    }

    public void setStandardLogLevel(Level standardLogLevel) {
        this.standardLogLevel = standardLogLevel;
    }

    public Level getErrorLogLevel() {
        return this.errorLogLevel;
    }

    public void setErrorLogLevel(Level errorLogLevel) {
        this.errorLogLevel = errorLogLevel;
    }
}

