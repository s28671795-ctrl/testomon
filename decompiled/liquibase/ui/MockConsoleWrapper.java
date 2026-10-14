/*
 * Decompiled with CFR 0.152.
 */
package liquibase.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import liquibase.ui.ConsoleUIService;

public class MockConsoleWrapper
extends ConsoleUIService.ConsoleWrapper {
    private final List<String> responses;

    public MockConsoleWrapper(String ... responses) {
        super(null, false);
        this.responses = new ArrayList<String>(Arrays.asList(responses));
    }

    @Override
    public boolean supportsInput() {
        return true;
    }

    @Override
    public String readLine() {
        return this.responses.remove(0);
    }
}

