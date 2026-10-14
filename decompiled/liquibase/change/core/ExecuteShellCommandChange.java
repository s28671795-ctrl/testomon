/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.change.AbstractChange;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.sql.Sql;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.CommentStatement;
import liquibase.statement.core.RuntimeStatement;
import liquibase.util.StringUtil;

@DatabaseChange(name="executeCommand", description="Executes a system command. Because this refactoring doesn't generate SQL, using Liquibase commands such as update-sql may not work as expected. Therefore, prefer refactorings that generate SQL.", priority=1)
public class ExecuteShellCommandChange
extends AbstractChange {
    protected List<String> finalCommandArray;
    private String executable;
    private List<String> os;
    private final List<String> args = new ArrayList<String>();
    private String timeout;
    private static final String TIMEOUT_REGEX = "^\\s*(\\d+)\\s*([sSmMhH]?)\\s*$";
    private static final Pattern TIMEOUT_PATTERN = Pattern.compile("^\\s*(\\d+)\\s*([sSmMhH]?)\\s*$");
    private static final Long SECS_IN_MILLIS = 1000L;
    private static final Long MIN_IN_MILLIS = SECS_IN_MILLIS * 60L;
    private static final Long HOUR_IN_MILLIS = MIN_IN_MILLIS * 60L;
    protected Integer maxStreamGobblerOutput = null;

    @Override
    public boolean generateStatementsVolatile(Database database) {
        return true;
    }

    @Override
    public boolean generateRollbackStatementsVolatile(Database database) {
        return true;
    }

    @DatabaseChangeProperty(description="Name of the executable to run", exampleValue="mysqldump", requiredForDatabase={"all"})
    public String getExecutable() {
        return this.executable;
    }

    public void setExecutable(String executable) {
        this.executable = executable;
    }

    public List<String> getArgs() {
        return Collections.unmodifiableList(this.args);
    }

    public void addArg(String arg) {
        this.args.add(arg);
    }

    @DatabaseChangeProperty(description="Timeout value for the executable to run", exampleValue="10s")
    public String getTimeout() {
        return this.timeout;
    }

    public void setTimeout(String timeout2) {
        this.timeout = timeout2;
    }

    @DatabaseChangeProperty(exampleValue="Windows 7", description="List of operating systems on which to execute the command (taken from the os.name Java system property)")
    public List<String> getOs() {
        return this.os;
    }

    public void setOs(String os) {
        this.os = StringUtil.splitAndTrim(os, ",");
    }

    @Override
    public ValidationErrors validate(Database database) {
        Matcher matcher;
        ValidationErrors validationErrors = new ValidationErrors();
        if (!StringUtil.isEmpty(this.timeout) && !(matcher = TIMEOUT_PATTERN.matcher(this.timeout)).matches()) {
            validationErrors.addError("Invalid value specified for timeout: " + this.timeout);
        }
        return validationErrors;
    }

    @Override
    public Warnings warn(Database database) {
        return new Warnings();
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        String currentOS;
        boolean shouldRun = true;
        if (this.os != null && !this.os.isEmpty() && !this.os.contains(currentOS = System.getProperty("os.name"))) {
            shouldRun = false;
            Scope.getCurrentScope().getLog(this.getClass()).info("Not executing on os " + currentOS + " when " + this.os + " was specified");
        }
        boolean shouldExecuteChange = this.shouldExecuteChange(database);
        this.finalCommandArray = this.createFinalCommandArray(database);
        if (shouldRun && shouldExecuteChange) {
            return new SqlStatement[]{new RuntimeStatement(){

                @Override
                public Sql[] generate(Database database) {
                    try {
                        ExecuteShellCommandChange.this.executeCommand(database);
                    }
                    catch (Exception e) {
                        throw new UnexpectedLiquibaseException("Error executing command: " + e.getLocalizedMessage(), e);
                    }
                    return null;
                }
            }};
        }
        if (!shouldExecuteChange) {
            return new SqlStatement[]{new CommentStatement(this.getCommandString())};
        }
        return SqlStatement.EMPTY_SQL_STATEMENT;
    }

    protected List<String> createFinalCommandArray(Database database) {
        ArrayList<String> commandArray = new ArrayList<String>();
        commandArray.add(this.getExecutable());
        commandArray.addAll(this.getArgs());
        return commandArray;
    }

    protected void executeCommand(Database database) throws Exception {
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        ByteArrayOutputStream inputStream2 = new ByteArrayOutputStream();
        ProcessBuilder pb = this.createProcessBuilder(database);
        Process p = pb.start();
        int returnCode = 0;
        try {
            StreamGobbler errorGobbler = this.createErrorGobbler(p.getErrorStream(), errorStream);
            StreamGobbler outputGobbler = this.createErrorGobbler(p.getInputStream(), inputStream2);
            errorGobbler.start();
            outputGobbler.start();
            long timeoutInMillis = this.getTimeoutInMillis();
            returnCode = timeoutInMillis > 0L ? this.waitForOrKill(p, timeoutInMillis) : p.waitFor();
            errorGobbler.finish();
            outputGobbler.finish();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String errorStreamOut = errorStream.toString(GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue());
        String infoStreamOut = inputStream2.toString(GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue());
        if (errorStreamOut != null && !errorStreamOut.isEmpty()) {
            Scope.getCurrentScope().getLog(this.getClass()).severe(errorStreamOut);
        }
        Scope.getCurrentScope().getLog(this.getClass()).info(infoStreamOut);
        this.processResult(returnCode, errorStreamOut, infoStreamOut, database);
    }

    protected StreamGobbler createErrorGobbler(InputStream processStream, OutputStream outputStream2) {
        return new StreamGobbler(processStream, outputStream2, Thread.currentThread());
    }

    protected Integer getMaxStreamGobblerOutput() {
        return this.maxStreamGobblerOutput;
    }

    private int waitForOrKill(final Process process, long timeoutInMillis) throws TimeoutException {
        int ret = -1;
        final AtomicBoolean timedOut = new AtomicBoolean(false);
        Timer timer = new Timer();
        if (timeoutInMillis > 0L) {
            timer.schedule(new TimerTask(){

                @Override
                public void run() {
                    timedOut.set(true);
                    process.destroy();
                }
            }, timeoutInMillis);
        }
        boolean stop = false;
        while (!stop) {
            try {
                ret = process.waitFor();
                stop = true;
                timer.cancel();
                if (!timedOut.get()) continue;
                String timeoutStr = this.timeout != null ? this.timeout : timeoutInMillis + " ms";
                throw new TimeoutException("Process timed out (" + timeoutStr + ")");
            }
            catch (InterruptedException ignore) {
                if (!timedOut.get()) continue;
                timer.cancel();
                String timeoutStr = this.timeout != null ? this.timeout : timeoutInMillis + " ms";
                throw new TimeoutException("Process timed out (" + timeoutStr + ")");
            }
        }
        return ret;
    }

    protected long getTimeoutInMillis() {
        Matcher matcher;
        if (this.timeout != null && (matcher = TIMEOUT_PATTERN.matcher(this.timeout)).find()) {
            String val = matcher.group(1);
            try {
                long valLong = Long.parseLong(val);
                String unit = matcher.group(2);
                if (StringUtil.isEmpty(unit)) {
                    return valLong * SECS_IN_MILLIS;
                }
                char u = unit.toLowerCase().charAt(0);
                switch (u) {
                    case 'h': {
                        valLong *= HOUR_IN_MILLIS.longValue();
                        break;
                    }
                    case 'm': {
                        valLong *= MIN_IN_MILLIS.longValue();
                        break;
                    }
                    default: {
                        valLong *= SECS_IN_MILLIS.longValue();
                    }
                }
                return valLong;
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return 0L;
    }

    protected void processResult(int returnCode, String errorStreamOut, String infoStreamOut, Database database) {
        if (returnCode != 0) {
            throw new RuntimeException(this.getCommandString() + " returned a code of " + returnCode);
        }
    }

    protected ProcessBuilder createProcessBuilder(Database database) {
        ProcessBuilder pb = new ProcessBuilder(this.finalCommandArray);
        pb.redirectErrorStream(true);
        return pb;
    }

    @Override
    public String getConfirmationMessage() {
        return "Shell command '" + this.getCommandString() + "' executed";
    }

    protected String getCommandString() {
        return this.getExecutable() + " " + StringUtil.join(this.args, " ");
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    protected void customLoadLogic(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        ParsedNode argsNode = parsedNode.getChild(null, "args");
        if (argsNode == null) {
            argsNode = parsedNode;
        }
        for (ParsedNode arg : argsNode.getChildren(null, "arg")) {
            this.addArg(arg.getChildValue((String)null, "value", String.class));
        }
        String passedValue = StringUtil.trimToNull(parsedNode.getChildValue((String)null, "os", String.class));
        if (passedValue == null) {
            this.os = new ArrayList<String>();
        } else {
            List<String> os = StringUtil.splitAndTrim(StringUtil.trimToEmpty(parsedNode.getChildValue((String)null, "os", String.class)), ",");
            if (os.size() == 1 && "".equals(os.get(0))) {
                this.os = null;
            } else if (!os.isEmpty()) {
                this.os = os;
            }
        }
    }

    @Override
    public String toString() {
        return "external process '" + this.getExecutable() + "' " + this.getArgs();
    }

    public class StreamGobbler
    extends Thread {
        private static final int THREAD_SLEEP_MILLIS = 100;
        private final OutputStream outputStream;
        private InputStream processStream;
        boolean loggedTruncated = false;
        long copiedSize = 0L;
        private final Thread parentThread;

        public StreamGobbler(InputStream processStream, OutputStream outputStream2, Thread parentThread) {
            this.processStream = processStream;
            this.outputStream = outputStream2;
            this.parentThread = parentThread;
        }

        @Override
        public void run() {
            block11: {
                try (BufferedInputStream bufferedInputStream = new BufferedInputStream(this.processStream);){
                    while (this.processStream != null) {
                        if (bufferedInputStream.available() > 0) {
                            this.copy(bufferedInputStream, this.outputStream);
                        }
                        try {
                            Thread.sleep(100L);
                        }
                        catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
                catch (IOException ioe) {
                    Scope.getCurrentScope().getLog(ExecuteShellCommandChange.class).warning(ioe.getMessage());
                    if (this.parentThread == null) break block11;
                    this.parentThread.interrupt();
                }
            }
        }

        public void finish() {
            InputStream procStream = this.processStream;
            this.processStream = null;
            try {
                this.copy(procStream, this.outputStream);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void copy(InputStream inputStream2, OutputStream outputStream2) throws IOException {
            Integer maxToCopy = ExecuteShellCommandChange.this.getMaxStreamGobblerOutput();
            byte[] bytes = new byte[1024];
            int r = inputStream2.read(bytes);
            while (r > 0) {
                if (maxToCopy != null && this.copiedSize > (long)maxToCopy.intValue()) {
                    if (!this.loggedTruncated) {
                        outputStream2.write("...[TRUNCATED]...".getBytes());
                        this.loggedTruncated = true;
                    }
                } else {
                    outputStream2.write(bytes, 0, r);
                }
                r = inputStream2.read(bytes);
                this.copiedSize += (long)r;
            }
        }
    }
}

