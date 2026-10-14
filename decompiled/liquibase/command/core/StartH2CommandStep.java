/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.sql.Connection;
import java.sql.DriverManager;
import liquibase.Scope;
import liquibase.command.AbstractCommandStep;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.configuration.ConfigurationValueObfuscator;

public class StartH2CommandStep
extends AbstractCommandStep {
    public static final String[] COMMAND_NAME = new String[]{"init", "startH2"};
    public static final CommandArgumentDefinition<String> BIND_ARG;
    public static final CommandArgumentDefinition<Integer> DB_PORT_ARG;
    public static final CommandArgumentDefinition<Integer> WEB_PORT_ARG;
    public static final CommandArgumentDefinition<String> USERNAME_ARG;
    public static final CommandArgumentDefinition<String> PASSWORD_ARG;
    public static final CommandArgumentDefinition<Boolean> LAUNCH_BROWSER_ARG;

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        CommandScope commandScope = resultsBuilder.getCommandScope();
        System.setProperty("h2.bindAddress", commandScope.getConfiguredValue(BIND_ARG).getValue());
        System.out.println("Starting Example H2 Database...");
        System.out.println("NOTE: The database does not persist data, so stopping and restarting this process will reset it back to a blank database");
        System.out.println();
        try {
            Class.forName("org.h2.Driver");
        }
        catch (ClassNotFoundException e) {
            String msg = "ERROR: H2 was not configured properly. To use Liquibase and H2, you need to have the H2 JDBC driver jar file in liquibase/lib. Learn more at https://docs.liquibase.com/";
            System.out.println(msg);
            throw e;
        }
        String username = commandScope.getConfiguredValue(USERNAME_ARG).getValue();
        String password = commandScope.getConfiguredValue(PASSWORD_ARG).getValue();
        Integer dbPort = commandScope.getConfiguredValue(DB_PORT_ARG).getValue();
        Integer webPort = commandScope.getConfiguredValue(WEB_PORT_ARG).getValue();
        try (Connection devConnection = DriverManager.getConnection("jdbc:h2:mem:dev", username, password);
             Connection intConnection = DriverManager.getConnection("jdbc:h2:mem:integration", username, password);){
            StartH2CommandStep.startTcpServer(dbPort);
            Object webServer = StartH2CommandStep.startWebServer(webPort);
            String devUrl = StartH2CommandStep.createWebSession(devConnection, webServer, commandScope.getConfiguredValue(LAUNCH_BROWSER_ARG).getValue());
            String intUrl = StartH2CommandStep.createWebSession(intConnection, webServer, false);
            System.out.println("Connection Information:" + System.lineSeparator() + "  Dev database: " + System.lineSeparator() + "    JDBC URL: jdbc:h2:tcp://localhost:" + dbPort + "/mem:dev" + System.lineSeparator() + "    Username: " + username + System.lineSeparator() + "    Password: " + password + System.lineSeparator() + "  Integration database: " + System.lineSeparator() + "    JDBC URL: jdbc:h2:tcp://localhost:" + dbPort + "/mem:integration" + System.lineSeparator() + "    Username: " + username + System.lineSeparator() + "    Password: " + password + System.lineSeparator() + "" + System.lineSeparator() + "Opening Database Console in Browser..." + System.lineSeparator() + "  Dev Web URL: " + devUrl + System.lineSeparator() + "  Integration Web URL: " + intUrl + System.lineSeparator());
            Runtime.getRuntime().addShutdownHook(new Thread(() -> Scope.getCurrentScope().getUI().sendMessage("Shutting down H2 database...")));
            Thread.sleep(Long.MAX_VALUE);
        }
        catch (InterruptedException e) {
            throw e;
        }
        catch (Throwable e) {
            e.printStackTrace();
            System.exit(-1);
        }
        resultsBuilder.addResult("statusCode", (Object)0);
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        super.adjustCommandDefinition(commandDefinition);
        commandDefinition.setShortDescription("Launches H2, an included open source in-memory database. This Java application is shipped with Liquibase, and is useful in the Getting Started experience and for testing out Liquibase commands.");
        commandDefinition.setGroupShortDescription(new String[]{"init"}, "Init commands");
    }

    protected static void startTcpServer(Integer dbPort) throws Exception {
        Class<?> serverClass = Class.forName("org.h2.tools.Server");
        Object tcpServer = serverClass.getMethod("createTcpServer", String[].class).invoke(null, new Object[]{new String[]{"-tcpAllowOthers", "-tcpPort", dbPort.toString()}});
        tcpServer.getClass().getMethod("start", new Class[0]).invoke(tcpServer, new Object[0]);
    }

    protected static Object startWebServer(Integer webPort) throws Exception {
        Class<?> serverClass = Class.forName("org.h2.tools.Server");
        Object webServer = Class.forName("org.h2.server.web.WebServer").newInstance();
        Object web = serverClass.getConstructor(Class.forName("org.h2.server.Service"), String[].class).newInstance(webServer, new String[]{"-webPort", webPort.toString()});
        web.getClass().getMethod("start", new Class[0]).invoke(web, new Object[0]);
        return webServer;
    }

    private static String createWebSession(Connection connection, Object webServer, boolean openBrowser) throws Exception {
        Class<?> serverClass = Class.forName("org.h2.tools.Server");
        String url2 = (String)webServer.getClass().getMethod("addSession", Connection.class).invoke(webServer, connection);
        if (openBrowser) {
            try {
                serverClass.getMethod("openBrowser", String.class).invoke(null, url2);
            }
            catch (Exception e) {
                String message = e.getMessage();
                if (message == null && e.getCause() != null) {
                    message = e.getCause().getMessage();
                }
                System.out.println("Cannot open browser: " + message);
                System.out.println("");
            }
        }
        return url2;
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        DB_PORT_ARG = builder.argument("dbPort", Integer.class).description("Port to run h2 database on").defaultValue(9090).build();
        WEB_PORT_ARG = builder.argument("webPort", Integer.class).description("Port to run h2's web interface on").defaultValue(8080).build();
        USERNAME_ARG = builder.argument("username", String.class).description("Username to create in h2").defaultValue("dbuser").build();
        PASSWORD_ARG = builder.argument("password", String.class).description("Password to use for created h2 user").defaultValue("letmein").setValueObfuscator(ConfigurationValueObfuscator.STANDARD).build();
        BIND_ARG = builder.argument("bindAddress", String.class).description("Network address to bind to").defaultValue("127.0.0.1").build();
        LAUNCH_BROWSER_ARG = builder.argument("launchBrowser", Boolean.class).description("Whether to open a browser to the database's web interface").defaultValue(true).build();
    }
}

