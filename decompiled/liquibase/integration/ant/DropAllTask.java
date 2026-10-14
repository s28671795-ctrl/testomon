/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.BuildException
 */
package liquibase.integration.ant;

import java.util.ArrayList;
import java.util.List;
import liquibase.CatalogAndSchema;
import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import liquibase.integration.ant.BaseLiquibaseTask;
import liquibase.util.StringUtil;
import org.apache.tools.ant.BuildException;

public class DropAllTask
extends BaseLiquibaseTask {
    private String schemas;
    private String catalog;

    @Override
    public void executeWithLiquibaseClassloader() throws BuildException {
        Liquibase liquibase = this.getLiquibase();
        try {
            if (StringUtil.trimToNull(this.schemas) != null) {
                List<String> schemaNames = StringUtil.splitAndTrim(this.schemas, ",");
                ArrayList<CatalogAndSchema> schemas = new ArrayList<CatalogAndSchema>();
                for (String name : schemaNames) {
                    schemas.add(new CatalogAndSchema(this.catalog, name));
                }
                liquibase.dropAll(schemas.toArray(new CatalogAndSchema[0]));
            } else {
                liquibase.dropAll();
            }
        }
        catch (LiquibaseException e) {
            throw new BuildException("Unable to drop all objects from database: " + e.getMessage(), (Throwable)e);
        }
    }

    public String getCatalog() {
        return this.catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchemas() {
        return this.schemas;
    }

    public void setSchemas(String schemas) {
        this.schemas = schemas;
    }
}

