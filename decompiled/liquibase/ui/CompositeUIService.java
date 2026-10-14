/*
 * Decompiled with CFR 0.152.
 */
package liquibase.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import liquibase.AbstractExtensibleObject;
import liquibase.Beta;
import liquibase.ui.InputHandler;
import liquibase.ui.UIService;

@Beta
public class CompositeUIService
extends AbstractExtensibleObject
implements UIService {
    public final List<UIService> outputServices = new ArrayList<UIService>();
    public final UIService inputService;

    public CompositeUIService(UIService inputService, Collection<UIService> outputServices) {
        this.inputService = inputService;
        this.outputServices.addAll(outputServices);
    }

    public List<UIService> getOutputServices() {
        return this.outputServices;
    }

    @Override
    public int getPriority() {
        return -1;
    }

    @Override
    public void sendMessage(String message) {
        this.outputServices.forEach(service2 -> service2.sendMessage(message));
    }

    @Override
    public void sendErrorMessage(String message) {
        this.outputServices.forEach(service2 -> service2.sendErrorMessage(message));
    }

    @Override
    public void sendErrorMessage(String message, Throwable exception) {
        this.outputServices.forEach(service2 -> service2.sendErrorMessage(message, exception));
    }

    @Override
    public <T> T prompt(String prompt, T valueIfNoEntry, InputHandler<T> inputHandler, Class<T> type) {
        return this.inputService.prompt(prompt, valueIfNoEntry, inputHandler, type);
    }

    @Override
    public void setAllowPrompt(boolean allowPrompt) throws IllegalArgumentException {
        this.inputService.setAllowPrompt(allowPrompt);
    }

    @Override
    public boolean getAllowPrompt() {
        return this.inputService.getAllowPrompt();
    }
}

