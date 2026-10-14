/*
 * Decompiled with CFR 0.152.
 */
package liquibase.parser.core.yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import liquibase.ContextExpression;
import liquibase.Labels;
import liquibase.Scope;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.exception.ChangeLogParseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.parser.ChangeLogParser;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.yaml.CustomConstructYamlTimestamp;
import liquibase.parser.core.yaml.YamlParser;
import liquibase.resource.Resource;
import liquibase.resource.ResourceAccessor;
import liquibase.util.FileUtil;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.nodes.Tag;

public class YamlChangeLogParser
extends YamlParser
implements ChangeLogParser {
    private static final String DATABASE_CHANGE_LOG = "databaseChangeLog";

    @Override
    public DatabaseChangeLog parse(String physicalChangeLogLocation, ChangeLogParameters changeLogParameters, ResourceAccessor resourceAccessor) throws ChangeLogParseException {
        Yaml yaml = new Yaml(new CustomSafeConstructor(YamlChangeLogParser.createLoaderOptions()));
        try {
            Map parsedYaml;
            Resource changelog = resourceAccessor.get(physicalChangeLogLocation);
            if (!changelog.exists()) {
                throw new ChangeLogParseException(physicalChangeLogLocation + " does not exist");
            }
            try (InputStream changeLogStream = changelog.openInputStream();){
                parsedYaml = this.parseYamlStream(physicalChangeLogLocation, yaml, changeLogStream);
            }
            if (parsedYaml == null || parsedYaml.isEmpty()) {
                throw new ChangeLogParseException("Empty file " + physicalChangeLogLocation);
            }
            DatabaseChangeLog changeLog = new DatabaseChangeLog(DatabaseChangeLog.normalizePath(physicalChangeLogLocation));
            if (!parsedYaml.containsKey(DATABASE_CHANGE_LOG)) {
                throw new ChangeLogParseException("Could not find databaseChangeLog node");
            }
            Object rootList = parsedYaml.get(DATABASE_CHANGE_LOG);
            if (rootList == null) {
                changeLog.setChangeLogParameters(changeLogParameters);
                return changeLog;
            }
            if (!(rootList instanceof List)) {
                throw new ChangeLogParseException("databaseChangeLog does not contain a list of entries. Each changeSet must begin ' - changeSet:'");
            }
            for (Object obj : (List)rootList) {
                if (!(obj instanceof Map) || !((Map)obj).containsKey("property")) continue;
                Map property = (Map)((Map)obj).get("property");
                ContextExpression context = new ContextExpression((String)property.get("context"));
                Labels labels = new Labels((String)property.get("labels"));
                Boolean global = this.getGlobalParam(property);
                if (property.containsKey("name")) {
                    Object value = property.get("value");
                    changeLogParameters.set((String)property.get("name"), value, context, labels, (String)property.get("dbms"), (boolean)global, changeLog);
                    continue;
                }
                if (!property.containsKey("file")) continue;
                this.loadChangeLogParametersFromFile(changeLogParameters, resourceAccessor, changeLog, property, context, labels, global);
            }
            this.replaceParameters(parsedYaml, changeLogParameters, changeLog);
            changeLog.setChangeLogParameters(changeLogParameters);
            ParsedNode databaseChangeLogNode = new ParsedNode(null, DATABASE_CHANGE_LOG);
            databaseChangeLogNode.setValue(rootList);
            changeLog.load(databaseChangeLogNode, resourceAccessor);
            return changeLog;
        }
        catch (ChangeLogParseException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ChangeLogParseException("Error parsing " + physicalChangeLogLocation + " : " + e.getMessage(), e);
        }
    }

    private Map parseYamlStream(String physicalChangeLogLocation, Yaml yaml, InputStream changeLogStream) throws ChangeLogParseException {
        Map parsedYaml;
        try {
            parsedYaml = (Map)yaml.load(changeLogStream);
        }
        catch (Exception e) {
            throw new ChangeLogParseException("Syntax error in file " + physicalChangeLogLocation + ": " + e.getMessage(), e);
        }
        return parsedYaml;
    }

    private void loadChangeLogParametersFromFile(ChangeLogParameters changeLogParameters, ResourceAccessor resourceAccessor, DatabaseChangeLog changeLog, Map property, ContextExpression context, Labels labels, Boolean global) throws IOException, LiquibaseException {
        Resource resource;
        Properties props = new Properties();
        Boolean relativeToChangelogFile = (Boolean)property.get("relativeToChangelogFile");
        Boolean errorIfMissing = (Boolean)property.get("errorIfMissing");
        String file = (String)property.get("file");
        if (relativeToChangelogFile == null) {
            relativeToChangelogFile = false;
        }
        if (errorIfMissing == null) {
            errorIfMissing = true;
        }
        if (!(resource = relativeToChangelogFile != false ? resourceAccessor.get(changeLog.getPhysicalFilePath()).resolveSibling(file) : resourceAccessor.get(file)).exists()) {
            if (errorIfMissing.booleanValue()) {
                throw new UnexpectedLiquibaseException(FileUtil.getFileNotFoundMessage(file));
            }
            Scope.getCurrentScope().getLog(this.getClass()).warning(FileUtil.getFileNotFoundMessage(file));
        } else {
            try (InputStream stream = resource.openInputStream();){
                props.load(stream);
            }
            for (Map.Entry<Object, Object> entry : props.entrySet()) {
                changeLogParameters.set(entry.getKey().toString(), (Object)entry.getValue().toString(), context, labels, (String)property.get("dbms"), (boolean)global, changeLog);
            }
        }
    }

    private Boolean getGlobalParam(Map property) {
        Boolean global = null;
        Object globalObj = property.get("global");
        global = globalObj == null ? Boolean.valueOf(true) : (Boolean)globalObj;
        return global;
    }

    protected void replaceParameters(Object obj, ChangeLogParameters changeLogParameters, DatabaseChangeLog changeLog) throws ChangeLogParseException {
        block5: {
            block4: {
                if (!(obj instanceof Map)) break block4;
                for (Map.Entry entry : ((Map)obj).entrySet()) {
                    if (entry.getValue() instanceof Map || entry.getValue() instanceof Collection) {
                        this.replaceParameters(entry.getValue(), changeLogParameters, changeLog);
                        continue;
                    }
                    if (!(entry.getValue() instanceof String)) continue;
                    entry.setValue(changeLogParameters.expandExpressions((String)entry.getValue(), changeLog));
                }
                break block5;
            }
            if (!(obj instanceof Collection)) break block5;
            ListIterator<String> iterator2 = ((List)obj).listIterator();
            while (iterator2.hasNext()) {
                Object e = iterator2.next();
                if (e instanceof Map || e instanceof Collection) {
                    this.replaceParameters(e, changeLogParameters, changeLog);
                    continue;
                }
                if (!(e instanceof String)) continue;
                iterator2.set(changeLogParameters.expandExpressions((String)e, changeLog));
            }
        }
    }

    static class CustomSafeConstructor
    extends SafeConstructor {
        public CustomSafeConstructor(LoaderOptions loaderOptions) {
            super(loaderOptions);
            this.yamlConstructors.put(Tag.TIMESTAMP, new CustomConstructYamlTimestamp());
        }
    }
}

