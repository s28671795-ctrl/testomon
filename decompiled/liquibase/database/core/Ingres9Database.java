/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;
import liquibase.CatalogAndSchema;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.database.OfflineConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Sequence;
import liquibase.structure.core.Table;
import liquibase.util.JdbcUtil;

public class Ingres9Database
extends AbstractJdbcDatabase {
    public static final String PRODUCT_NAME = "INGRES";
    private static final String CREATE_VIEW_REGEX = "^CREATE\\s+.*?VIEW\\s+.*?AS\\s+";
    private static final Pattern CREATE_VIEW_AS_PATTERN = Pattern.compile("^CREATE\\s+.*?VIEW\\s+.*?AS\\s+", 34);

    public Ingres9Database() {
        this.setCurrentDateTimeFunction("date('now')");
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection databaseConnection) throws DatabaseException {
        return PRODUCT_NAME.equalsIgnoreCase(databaseConnection.getDatabaseProductName());
    }

    @Override
    protected String getConnectionSchemaName() {
        return this.getConnection().getConnectionUserName();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String getViewDefinition(CatalogAndSchema schema, String viewName) throws DatabaseException {
        String sql = "select text_segment from iiviews where table_name = ?";
        PreparedStatement stmt = null;
        StringBuilder definition = new StringBuilder();
        try {
            if (this.getConnection() instanceof OfflineConnection) {
                throw new DatabaseException("Cannot execute commands against an offline database");
            }
            stmt = ((JdbcConnection)this.getConnection()).getUnderlyingConnection().prepareStatement("select text_segment from iiviews where table_name = ?");
            stmt.setString(1, viewName);
            try (ResultSet rs = stmt.executeQuery();){
                while (rs.next()) {
                    definition.append(rs.getString("text_segment"));
                }
            }
        }
        catch (Exception ex) {
            String string;
            try {
                JdbcUtil.closeStatement(stmt);
                stmt = null;
                string = null;
            }
            catch (Throwable throwable) {
                JdbcUtil.closeStatement(stmt);
                throw throwable;
            }
            JdbcUtil.closeStatement(stmt);
            return string;
        }
        JdbcUtil.closeStatement(stmt);
        return CREATE_VIEW_AS_PATTERN.matcher(definition.toString()).replaceFirst("");
    }

    @Override
    public String getDefaultDriver(String url2) {
        if (url2.startsWith("jdbc:ingres:")) {
            return "com.ingres.jdbc.IngresDriver";
        }
        return null;
    }

    @Override
    public String getShortName() {
        return "ingres";
    }

    @Override
    public Integer getDefaultPort() {
        return 21071;
    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return false;
    }

    @Override
    public boolean supportsTablespaces() {
        return false;
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return PRODUCT_NAME;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean isSystemObject(DatabaseObject example) {
        if (example instanceof Table && example.getSchema() != null && "$ingres".equals(example.getSchema().getName())) {
            return true;
        }
        return super.isSystemObject(example);
    }

    @Override
    public boolean supports(Class<? extends DatabaseObject> object) {
        if (Sequence.class.isAssignableFrom(object)) {
            return false;
        }
        return super.supports(object);
    }

    @Override
    public boolean supportsSequences() {
        return false;
    }

    @Override
    public boolean supportsCreateIfNotExists(Class<? extends DatabaseObject> type) {
        return type.isAssignableFrom(Table.class);
    }
}

