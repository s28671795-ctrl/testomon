/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.data;

import com.google.inject.Inject;
import com.incoresoft.backup.database.data.BackupIgnoredTables;
import com.incoresoft.backup.database.data.DatabaseDataDeletionManager;
import com.incoresoft.backup.database.data.JooqSchemaManager;
import com.incoresoft.backup.database.data.JooqTableOrderManager;
import com.incoresoft.backup.database.exceptions.DatabaseDataDeletionException;
import com.incoresoft.middleware.database.DatabaseConnector;
import java.sql.Connection;
import java.util.List;
import javax.sql.DataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JooqDatabaseDataDeletionManager
implements DatabaseDataDeletionManager {
    private static final Logger log = LoggerFactory.getLogger(JooqDatabaseDataDeletionManager.class);
    private final DatabaseConnector databaseConnector;
    private final SQLDialect dialect;
    private final JooqTableOrderManager tableOrderManager;
    private final JooqSchemaManager jooqSchemaManager;

    @Override
    public void deleteData(BackupIgnoredTables ignoredTables) {
        DataSource dataSource = this.databaseConnector.getDataSource();
        try (Connection connection = dataSource.getConnection();){
            DSLContext context = DSL.using(connection, this.dialect);
            Schema schema = this.jooqSchemaManager.getMainSchema(context);
            List<Table<?>> tables = this.tableOrderManager.getTablesOrderedForDeleting(schema.getTables(), schema.getForeignKeys(), ignoredTables);
            for (Table<?> table : tables) {
                context.deleteFrom(table).execute();
                log.info("Deleted data from table: {}", (Object)table.getName());
            }
        }
        catch (Exception e) {
            throw new DatabaseDataDeletionException(e);
        }
    }

    @Inject
    public JooqDatabaseDataDeletionManager(DatabaseConnector databaseConnector, SQLDialect dialect, JooqTableOrderManager tableOrderManager, JooqSchemaManager jooqSchemaManager) {
        this.databaseConnector = databaseConnector;
        this.dialect = dialect;
        this.tableOrderManager = tableOrderManager;
        this.jooqSchemaManager = jooqSchemaManager;
    }
}

