/*
 * Decompiled with CFR 0.152.
 */
package liquibase.ui;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import liquibase.AbstractExtensibleObject;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.configuration.ConfiguredValue;
import liquibase.logging.Logger;
import liquibase.ui.DefaultInputHandler;
import liquibase.ui.InputHandler;
import liquibase.ui.UIService;
import liquibase.util.StringUtil;

public class ConsoleUIService
extends AbstractExtensibleObject
implements UIService {
    private PrintStream outputStream = System.out;
    private PrintStream errorStream = System.out;
    private boolean outputStackTraces = false;
    private boolean allowPrompt = false;
    private ConsoleWrapper console;
    public static final String TERM_PROGRAM = "TERM_PROGRAM";
    public static final String MINTTY = "mintty";
    public static final String MSYSTEM = "MSYSTEM";
    public static final String MINGW64 = "mingw64";

    public ConsoleUIService() {
    }

    protected ConsoleUIService(ConsoleWrapper console) {
        this.console = console;
    }

    @Override
    public int getPriority() {
        return -1;
    }

    @Override
    public void sendMessage(String message) {
        this.getOutputStream().println(message);
    }

    @Override
    public void sendErrorMessage(String message) {
        this.getErrorStream().println(message);
    }

    @Override
    public void sendErrorMessage(String message, Throwable exception) {
        this.sendErrorMessage(message);
        if (this.getOutputStackTraces()) {
            exception.printStackTrace(this.getErrorStream());
        }
    }

    @Override
    public void setAllowPrompt(boolean allowPrompt) throws IllegalArgumentException {
        this.allowPrompt = allowPrompt;
    }

    @Override
    public boolean getAllowPrompt() {
        return this.getConsole().supportsInput() && this.allowPrompt;
    }

    @Override
    public <T> T prompt(String prompt, T valueIfNoEntry, InputHandler<T> inputHandler, Class<T> type) {
        Logger log = Scope.getCurrentScope().getLog(this.getClass());
        if (!this.allowPrompt) {
            log.fine("No prompt for input is allowed at this time");
            return valueIfNoEntry;
        }
        ConsoleWrapper console = this.getConsole();
        if (!console.supportsInput()) {
            Scope.getCurrentScope().getUI().sendMessage("No interactive environment detected. Skipping interactive prompt: '" + prompt + "'. Using default value '" + valueIfNoEntry + "'");
            return valueIfNoEntry;
        }
        if (inputHandler == null) {
            inputHandler = new DefaultInputHandler<T>();
        }
        String initialMessage = prompt;
        if (valueIfNoEntry != null) {
            initialMessage = initialMessage + " [" + valueIfNoEntry + "]";
        }
        this.sendMessage(initialMessage + ": ");
        while (true) {
            String input = StringUtil.trimToNull(console.readLine());
            try {
                if (input == null) {
                    if (inputHandler.shouldAllowEmptyInput()) {
                        return valueIfNoEntry;
                    }
                    throw new IllegalArgumentException("Empty values are not permitted.");
                }
                return inputHandler.parseInput(input, type);
            }
            catch (IllegalArgumentException e) {
                String message = e.getMessage() != null ? e.getMessage() : "Invalid value: \"" + input + "\"";
                this.sendMessage(message);
                this.sendMessage(initialMessage + ": ");
                continue;
            }
            break;
        }
    }

    protected ConsoleWrapper getConsole() {
        if (this.console == null) {
            ConfiguredValue<Boolean> headlessValue = GlobalConfiguration.HEADLESS.getCurrentConfiguredValue();
            boolean headlessConfigValue = headlessValue.getValue();
            boolean wasHeadlessOverridden = !headlessValue.wasDefaultValueUsed();
            Logger log = Scope.getCurrentScope().getLog(this.getClass());
            if (headlessConfigValue) {
                log.fine("Not prompting for user input because liquibase.headless=true. Set to 'false' in liquibase.properties to change this behavior.");
                this.console = new ConsoleWrapper(null, false);
            } else {
                Console systemConsole = System.console();
                boolean useStdIn = wasHeadlessOverridden;
                String minTtyValue = System.getenv(TERM_PROGRAM);
                if (systemConsole == null && !useStdIn) {
                    if (StringUtil.isNotEmpty(minTtyValue)) {
                        useStdIn = minTtyValue.equalsIgnoreCase(MINTTY);
                    }
                    if (!useStdIn) {
                        String msystem = System.getenv(MSYSTEM);
                        useStdIn = msystem != null && msystem.equalsIgnoreCase(MINGW64);
                    }
                }
                this.console = new ConsoleWrapper(systemConsole, useStdIn);
                if (systemConsole == null) {
                    log.fine("No system console detected for user input");
                    if (useStdIn) {
                        log.fine("Input will be from stdin");
                    }
                } else {
                    log.fine("A system console was detected for user input");
                }
            }
            if (!wasHeadlessOverridden) {
                log.fine("To override or validate the auto-detected environment for user input, set the liquibase.headless property in liquibase.properties file.");
            }
        }
        return this.console;
    }

    public PrintStream getOutputStream() {
        return this.outputStream;
    }

    public void setOutputStream(PrintStream outputStream2) {
        this.outputStream = outputStream2;
    }

    public PrintStream getErrorStream() {
        return this.errorStream;
    }

    public void setErrorStream(PrintStream errorStream) {
        this.errorStream = errorStream;
    }

    public boolean getOutputStackTraces() {
        return this.outputStackTraces;
    }

    public void setOutputStackTraces(boolean outputStackTraces) {
        this.outputStackTraces = outputStackTraces;
    }

    public static class ConsoleWrapper {
        private final Console console;
        private final boolean useStdin;

        public ConsoleWrapper(Console console) {
            this(console, false);
        }

        public ConsoleWrapper(Console console, boolean useStdInParam) {
            this.console = console;
            this.useStdin = useStdInParam;
        }

        public String readLine() {
            if (this.console == null) {
                if (!this.useStdin) {
                    return "";
                }
                try {
                    return new BufferedReader(new InputStreamReader(System.in)).readLine();
                }
                catch (IOException ioe) {
                    throw new RuntimeException("Unable to read from the system input stream", ioe);
                }
            }
            return this.console.readLine();
        }

        public boolean supportsInput() {
            return this.console != null || this.useStdin;
        }
    }
}

