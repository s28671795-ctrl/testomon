/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import liquibase.CatalogAndSchema;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.RuntimeEnvironment;
import liquibase.Scope;
import liquibase.changelog.ChangeLogIterator;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.filter.DbmsChangeSetFilter;
import liquibase.changelog.visitor.DBDocVisitor;
import liquibase.command.AbstractCommandStep;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.database.Database;
import liquibase.exception.LiquibaseException;
import liquibase.lockservice.LockService;
import liquibase.resource.PathHandlerFactory;
import liquibase.resource.Resource;
import liquibase.resource.ResourceAccessor;

public class DbDocCommandStep
extends AbstractCommandStep {
    public static final String[] COMMAND_NAME = new String[]{"dbDoc"};
    public static final CommandArgumentDefinition<String> OUTPUT_DIRECTORY_ARG;
    public static final CommandArgumentDefinition<String> SCHEMAS_ARG;
    public static final CommandArgumentDefinition<CatalogAndSchema[]> CATALOG_AND_SCHEMAS_ARG;

    @Override
    public List<Class<?>> requiredDependencies() {
        return Arrays.asList(Database.class, LockService.class, DatabaseChangeLog.class, ChangeLogParameters.class);
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Generates JavaDoc documentation for the existing database and changelogs");
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        CommandScope commandScope = resultsBuilder.getCommandScope();
        Database database = (Database)commandScope.getDependency(Database.class);
        ChangeLogParameters changeLogParameters = (ChangeLogParameters)commandScope.getDependency(ChangeLogParameters.class);
        String outputDirectory = commandScope.getArgumentValue(OUTPUT_DIRECTORY_ARG);
        String schemaList = commandScope.getArgumentValue(SCHEMAS_ARG);
        ResourceAccessor resourceAccessor = Scope.getCurrentScope().getResourceAccessor();
        CatalogAndSchema[] catalogAndSchemaArg = commandScope.getArgumentValue(CATALOG_AND_SCHEMAS_ARG);
        CatalogAndSchema[] catalogAndSchemas = this.getCatalogAndSchema(schemaList, catalogAndSchemaArg, database);
        Contexts contexts = changeLogParameters.getContexts();
        LabelExpression labelExpression = changeLogParameters.getLabels();
        Scope.getCurrentScope().getLog(this.getClass()).info("Generating Database Documentation");
        try {
            DatabaseChangeLog databaseChangeLog = (DatabaseChangeLog)commandScope.getDependency(DatabaseChangeLog.class);
            databaseChangeLog.validate(database, contexts, labelExpression);
            ChangeLogIterator logIterator = new ChangeLogIterator(databaseChangeLog, new DbmsChangeSetFilter(database));
            DBDocVisitor visitor = new DBDocVisitor(database);
            logIterator.run(visitor, new RuntimeEnvironment(database, contexts, labelExpression));
            PathHandlerFactory pathHandlerFactory = Scope.getCurrentScope().getSingleton(PathHandlerFactory.class);
            Resource resource = pathHandlerFactory.getResource(outputDirectory);
            visitor.writeHTML(resource, resourceAccessor, catalogAndSchemas);
            resultsBuilder.addResult("statusCode", (Object)0);
        }
        catch (IOException e) {
            resultsBuilder.addResult("statusCode", (Object)1);
            throw new LiquibaseException(e);
        }
    }

    private CatalogAndSchema[] getCatalogAndSchema(String schemas, CatalogAndSchema[] catalogAndSchemaArg, Database database) {
        if (catalogAndSchemaArg != null) {
            return catalogAndSchemaArg;
        }
        if (schemas != null) {
            ArrayList<CatalogAndSchema> schemaList = new ArrayList<CatalogAndSchema>();
            for (String schema : schemas.split(",")) {
                schemaList.add(new CatalogAndSchema(null, schema).customize(database));
            }
            return schemaList.toArray(new CatalogAndSchema[0]);
        }
        return new CatalogAndSchema[]{new CatalogAndSchema(null, null)};
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        OUTPUT_DIRECTORY_ARG = builder.argument("outputDirectory", String.class).required().description("The directory where the documentation is generated").build();
        SCHEMAS_ARG = builder.argument("schemas", String.class).description("Database schemas to include objects from in reporting").build();
        CATALOG_AND_SCHEMAS_ARG = builder.argument("catalogAndSchemas", CatalogAndSchema[].class).hidden().optional().build();
    }
}

