/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.Nullable;
import org.jooq.Catalog;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.RenamedCatalog;
import org.jooq.RenamedSchema;
import org.jooq.RenamedTable;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.conf.MappedCatalog;
import org.jooq.conf.MappedSchema;
import org.jooq.conf.MappedTable;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.SettingsTools;
import org.jooq.impl.DSL;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

@Deprecated(forRemoval=true, since="2.0")
public class SchemaMapping
implements Serializable {
    private static final JooqLogger log = JooqLogger.getLogger(SchemaMapping.class);
    private static volatile boolean loggedDeprecation = false;
    private final Configuration configuration;
    private volatile transient Map<String, Catalog> catalogs;
    private volatile transient Map<String, Schema> schemata;
    private volatile transient Map<String, Table<?>> tables;

    public SchemaMapping(Configuration configuration) {
        this.configuration = configuration;
    }

    private final RenderMapping mapping() {
        return SettingsTools.getRenderMapping(this.configuration.settings());
    }

    private final boolean renderCatalog() {
        return Boolean.TRUE.equals(this.configuration.settings().isRenderCatalog());
    }

    private final boolean renderSchema() {
        return Boolean.TRUE.equals(this.configuration.settings().isRenderSchema());
    }

    private static void logDeprecation() {
        if (!loggedDeprecation) {
            loggedDeprecation = true;
            log.warn((Object)"DEPRECATION", "org.jooq.SchemaMapping is deprecated as of jOOQ 2.0.5. Consider using jOOQ's runtime configuration org.jooq.conf.Settings instead");
        }
    }

    public void use(Schema schema) {
        this.use(schema.getName());
    }

    public void use(String schemaName) {
        SchemaMapping.logDeprecation();
        this.mapping().setDefaultSchema(schemaName);
    }

    public void add(String inputSchema, String outputSchema) {
        SchemaMapping.logDeprecation();
        MappedSchema schema = null;
        for (MappedSchema s2 : this.mapping().getSchemata()) {
            if (!inputSchema.equals(s2.getInput())) continue;
            schema = s2;
            break;
        }
        if (schema == null) {
            schema = new MappedSchema().withInput(inputSchema);
            this.mapping().getSchemata().add(schema);
        }
        schema.setOutput(outputSchema);
    }

    public void add(String inputSchema, Schema outputSchema) {
        this.add(inputSchema, outputSchema.getName());
    }

    public void add(Schema inputSchema, Schema outputSchema) {
        this.add(inputSchema.getName(), outputSchema.getName());
    }

    public void add(Schema inputSchema, String outputSchema) {
        this.add(inputSchema.getName(), outputSchema);
    }

    public void add(Table<?> inputTable, Table<?> outputTable) {
        this.add(inputTable, outputTable.getName());
    }

    public void add(Table<?> inputTable, String outputTable) {
        SchemaMapping.logDeprecation();
        MappedSchema schema = null;
        MappedTable table = null;
        for (MappedSchema s2 : this.mapping().getSchemata()) {
            if (!inputTable.getSchema().getName().equals(s2.getInput())) continue;
            for (MappedTable t2 : s2.getTables()) {
                if (!inputTable.getName().equals(t2.getInput())) continue;
                table = t2;
                break;
            }
            schema = s2;
            break;
        }
        if (schema == null) {
            schema = new MappedSchema().withInput(inputTable.getSchema().getName());
            this.mapping().getSchemata().add(schema);
        }
        if (table == null) {
            table = new MappedTable().withInput(inputTable.getName());
            schema.getTables().add(table);
        }
        table.setOutput(outputTable);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public Catalog map(Catalog catalog) {
        if (!this.renderCatalog()) {
            return null;
        }
        if (catalog instanceof RenamedCatalog) {
            return catalog;
        }
        Catalog result = catalog;
        if (result == null) {
            result = DSL.catalog(DSL.name(""));
        }
        String catalogName = result.getName();
        RenderMapping m4 = this.mapping();
        if (!m4.getCatalogs().isEmpty() || !StringUtils.isEmpty(m4.getDefaultCatalog())) {
            if (!this.getCatalogs().containsKey(catalogName)) {
                SchemaMapping schemaMapping = this;
                synchronized (schemaMapping) {
                    if (!this.getCatalogs().containsKey(catalogName)) {
                        for (MappedCatalog c : m4.getCatalogs()) {
                            if (!this.matches(c, catalogName)) continue;
                            if (StringUtils.isBlank(c.getOutput())) break;
                            if (c.getInput() != null && !c.getOutput().equals(catalogName)) {
                                result = new RenamedCatalog(result, c.getOutput());
                                break;
                            }
                            if (c.getInputExpression() == null) break;
                            result = new RenamedCatalog(result, c.getInputExpression().matcher(catalogName).replaceAll(c.getOutput()));
                            break;
                        }
                        if ("".equals(result.getName()) || result.getName().equals(m4.getDefaultCatalog())) {
                            result = null;
                        }
                        this.getCatalogs().put(catalogName, result);
                    }
                }
            }
            result = this.getCatalogs().get(catalogName);
        } else if ("".equals(result.getName())) {
            result = null;
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public Schema map(Schema schema) {
        RenderMapping m4;
        if (!this.renderSchema()) {
            return null;
        }
        if (schema instanceof RenamedSchema) {
            return schema;
        }
        Schema result = schema;
        if (result == null) {
            result = DSL.schema(DSL.name(""));
        }
        if (!((m4 = this.mapping()).getSchemata().isEmpty() && m4.getCatalogs().isEmpty() && StringUtils.isEmpty(m4.getDefaultSchema()) && StringUtils.isEmpty(m4.getDefaultCatalog()))) {
            String key;
            Catalog catalog = result.getCatalog();
            if (catalog == null) {
                catalog = DSL.catalog(DSL.name(""));
            }
            String catalogName = catalog.getName();
            String schemaName = result.getName();
            String string = key = StringUtils.isEmpty(catalogName) ? schemaName : catalogName + "." + schemaName;
            if (!this.getSchemata().containsKey(key)) {
                SchemaMapping schemaMapping = this;
                synchronized (schemaMapping) {
                    if (!this.getSchemata().containsKey(key)) {
                        block3: for (MappedCatalog c : m4.getCatalogs()) {
                            if (!this.matches(c, catalogName)) continue;
                            for (MappedSchema s2 : c.getSchemata()) {
                                if (!this.matches(s2, schemaName)) continue;
                                if (StringUtils.isBlank(s2.getOutput())) break block3;
                                if (s2.getInput() != null && !s2.getOutput().equals(schemaName)) {
                                    result = new RenamedSchema(this.map(catalog), result, s2.getOutput());
                                    break block3;
                                }
                                if (s2.getInputExpression() == null) break block3;
                                result = new RenamedSchema(this.map(catalog), result, s2.getInputExpression().matcher(schemaName).replaceAll(s2.getOutput()));
                                break block3;
                            }
                            result = new RenamedSchema(this.map(catalog), result, schemaName);
                            break;
                        }
                        if (!(result instanceof RenamedSchema)) {
                            for (MappedSchema s3 : m4.getSchemata()) {
                                if (!this.matches(s3, schemaName)) continue;
                                if (StringUtils.isBlank(s3.getOutput())) break;
                                if (s3.getInput() != null && !s3.getOutput().equals(schemaName)) {
                                    result = new RenamedSchema(this.map(catalog), result, s3.getOutput());
                                    break;
                                }
                                if (s3.getInputExpression() == null) break;
                                result = new RenamedSchema(this.map(catalog), result, s3.getInputExpression().matcher(schemaName).replaceAll(s3.getOutput()));
                                break;
                            }
                        }
                        if (result.getCatalog() != null && this.map(result.getCatalog()) == null) {
                            result = new RenamedSchema(null, result, result.getName());
                        }
                        if ("".equals(result.getName())) {
                            result = null;
                        } else if (result.getName().equals(m4.getDefaultSchema()) && (result.getCatalog() == null || "".equals(result.getCatalog().getName()) || result.getCatalog().getName().equals(m4.getDefaultCatalog()))) {
                            result = null;
                        }
                        this.getSchemata().put(key, result);
                    }
                }
            }
            result = this.getSchemata().get(key);
        } else if ("".equals(result.getName())) {
            result = null;
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public <R extends Record> Table<R> map(Table<R> table) {
        Table<Object> result = table;
        if (!(result == null || this.mapping().getSchemata().isEmpty() && this.mapping().getCatalogs().isEmpty())) {
            Object key;
            Catalog catalog = result.getCatalog();
            Schema schema = result.getSchema();
            if (catalog == null) {
                catalog = DSL.catalog(DSL.name(""));
            }
            if (schema == null) {
                schema = DSL.schema(DSL.name(""));
            }
            String catalogName = catalog.getName();
            String schemaName = schema.getName();
            String tableName = result.getName();
            Object object = StringUtils.isEmpty(catalogName) ? (StringUtils.isEmpty(schemaName) ? tableName : schemaName + "." + tableName) : (key = catalogName + "." + schemaName + "." + tableName);
            if (!this.getTables().containsKey(key)) {
                SchemaMapping schemaMapping = this;
                synchronized (schemaMapping) {
                    if (!this.getTables().containsKey(key)) {
                        block3: for (MappedCatalog c : this.mapping().getCatalogs()) {
                            if (!this.matches(c, catalogName)) continue;
                            for (MappedSchema s2 : c.getSchemata()) {
                                if (!this.matches(s2, schemaName)) continue;
                                for (MappedTable t2 : s2.getTables()) {
                                    if (!this.matches(t2, tableName)) continue;
                                    if (StringUtils.isBlank(t2.getOutput())) break block3;
                                    if (t2.getInput() != null && !t2.getOutput().equals(tableName)) {
                                        result = new RenamedTable<R>(this.map(schema), result, t2.getOutput());
                                        break block3;
                                    }
                                    if (t2.getInputExpression() == null) break block3;
                                    result = new RenamedTable<R>(this.map(schema), result, t2.getInputExpression().matcher(tableName).replaceAll(t2.getOutput()));
                                    break block3;
                                }
                            }
                            result = new RenamedTable<R>(this.map(schema), result, tableName);
                            break;
                        }
                        if (!(result instanceof RenamedTable)) {
                            block6: for (MappedSchema s3 : this.mapping().getSchemata()) {
                                if (!this.matches(s3, schemaName)) continue;
                                for (MappedTable t3 : s3.getTables()) {
                                    if (!this.matches(t3, tableName)) continue;
                                    if (StringUtils.isBlank(t3.getOutput())) break block6;
                                    if (t3.getInput() != null && !t3.getOutput().equals(tableName)) {
                                        result = new RenamedTable<R>(this.map(schema), result, t3.getOutput());
                                        break block6;
                                    }
                                    if (t3.getInputExpression() == null) break block6;
                                    result = new RenamedTable<R>(this.map(schema), result, t3.getInputExpression().matcher(tableName).replaceAll(t3.getOutput()));
                                    break block6;
                                }
                                result = new RenamedTable<R>(this.map(schema), result, tableName);
                                break;
                            }
                        }
                        this.getTables().put((String)key, result);
                    }
                }
            }
            result = this.getTables().get(key);
        }
        return result;
    }

    private final boolean matches(MappedCatalog c, String catalogName) {
        return c.getInput() != null && catalogName.equals(c.getInput()) || c.getInputExpression() != null && c.getInputExpression().matcher(catalogName).matches();
    }

    private final boolean matches(MappedSchema s2, String schemaName) {
        return s2.getInput() != null && schemaName.equals(s2.getInput()) || s2.getInputExpression() != null && s2.getInputExpression().matcher(schemaName).matches();
    }

    private final boolean matches(MappedTable t2, String tableName) {
        return t2.getInput() != null && tableName.equals(t2.getInput()) || t2.getInputExpression() != null && t2.getInputExpression().matcher(tableName).matches();
    }

    public void setDefaultSchema(String schema) {
        this.use(schema);
    }

    public void setSchemaMapping(Map<String, String> schemaMap) {
        schemaMap.forEach(this::add);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final Map<String, Catalog> getCatalogs() {
        if (this.catalogs == null) {
            SchemaMapping schemaMapping = this;
            synchronized (schemaMapping) {
                if (this.catalogs == null) {
                    this.catalogs = new HashMap<String, Catalog>();
                }
            }
        }
        return this.catalogs;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final Map<String, Schema> getSchemata() {
        if (this.schemata == null) {
            SchemaMapping schemaMapping = this;
            synchronized (schemaMapping) {
                if (this.schemata == null) {
                    this.schemata = new HashMap<String, Schema>();
                }
            }
        }
        return this.schemata;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final Map<String, Table<?>> getTables() {
        if (this.tables == null) {
            SchemaMapping schemaMapping = this;
            synchronized (schemaMapping) {
                if (this.tables == null) {
                    this.tables = new HashMap();
                }
            }
        }
        return this.tables;
    }

    public String toString() {
        return String.valueOf(this.mapping());
    }
}

