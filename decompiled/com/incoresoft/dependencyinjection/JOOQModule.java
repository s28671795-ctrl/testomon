/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dependencyinjection;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.incoresoft.dependencyinjection.jooq.ExceptionThrowingDataSourceConnectionProvider;
import com.incoresoft.dependencyinjection.jooq.LockingDSLContext;
import com.incoresoft.dependencyinjection.jooq.LockingDataSourceConnectionProvider;
import com.incoresoft.middleware.database.DBType;
import com.incoresoft.middleware.database.DatabaseConnector;
import javax.sql.DataSource;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.ExecuteWithoutWhere;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.ThreadLocalTransactionProvider;

public class JOOQModule
extends AbstractModule {
    private final DatabaseConnector databaseConnector;

    public JOOQModule(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    @Override
    protected void configure() {
        this.bind(DataSource.class).toInstance(this.databaseConnector.getDataSource());
    }

    @Provides
    public SQLDialect dialect() {
        return switch (this.databaseConnector.getDBType()) {
            case DBType.h2 -> SQLDialect.H2;
            case DBType.mysql -> SQLDialect.MYSQL;
            case DBType.postgresql -> SQLDialect.POSTGRES;
            case DBType.sqlserver -> SQLDialect.SQLSERVER;
            default -> SQLDialect.DEFAULT;
        };
    }

    @Provides
    public Settings settings() {
        return new Settings().withRenderCatalog(false).withRenderSchema(false).withExecuteUpdateWithoutWhere(ExecuteWithoutWhere.LOG_INFO).withExecuteDeleteWithoutWhere(ExecuteWithoutWhere.LOG_INFO);
    }

    @Provides
    @Singleton
    public DSLContext dslContext(ExceptionThrowingDataSourceConnectionProvider exceptionThrowingConnectionProvider, Settings settings, SQLDialect dialect) {
        Configuration configuration = new DefaultConfiguration().set(settings).set(exceptionThrowingConnectionProvider).set(dialect).set(new ThreadLocalTransactionProvider(exceptionThrowingConnectionProvider, true));
        return DSL.using(configuration);
    }

    @Provides
    @Singleton
    @LockingDSLContext
    public DSLContext lockingDslContext(LockingDataSourceConnectionProvider waitingConnectionProvider, Settings settings, SQLDialect dialect) {
        Configuration configuration = new DefaultConfiguration().set(settings).set(waitingConnectionProvider).set(dialect).set(new ThreadLocalTransactionProvider(waitingConnectionProvider, true));
        return DSL.using(configuration);
    }

    @Provides
    @Singleton
    public LockingDataSourceConnectionProvider lockingConnectionProvider(DataSource dataSource) {
        return new LockingDataSourceConnectionProvider(dataSource);
    }

    @Provides
    @Singleton
    public ExceptionThrowingDataSourceConnectionProvider exceptionThrowingConnectionProvider(DataSource dataSource) {
        return new ExceptionThrowingDataSourceConnectionProvider(dataSource);
    }
}

