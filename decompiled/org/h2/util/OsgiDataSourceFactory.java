/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.BundleContext
 *  org.osgi.service.jdbc.DataSourceFactory
 */
package org.h2.util;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;
import org.h2.Driver;
import org.h2.engine.Constants;
import org.h2.jdbcx.JdbcDataSource;
import org.osgi.framework.BundleContext;
import org.osgi.service.jdbc.DataSourceFactory;

public class OsgiDataSourceFactory
implements DataSourceFactory {
    private final Driver driver;

    public OsgiDataSourceFactory(Driver driver) {
        this.driver = driver;
    }

    public DataSource createDataSource(Properties properties) throws SQLException {
        Properties properties2 = new Properties();
        if (properties != null) {
            properties2.putAll((Map<?, ?>)properties);
        }
        OsgiDataSourceFactory.rejectUnsupportedOptions(properties2);
        OsgiDataSourceFactory.rejectPoolingOptions(properties2);
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        OsgiDataSourceFactory.setupH2DataSource(jdbcDataSource, properties2);
        return jdbcDataSource;
    }

    public ConnectionPoolDataSource createConnectionPoolDataSource(Properties properties) throws SQLException {
        Properties properties2 = new Properties();
        if (properties != null) {
            properties2.putAll((Map<?, ?>)properties);
        }
        OsgiDataSourceFactory.rejectUnsupportedOptions(properties2);
        OsgiDataSourceFactory.rejectPoolingOptions(properties2);
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        OsgiDataSourceFactory.setupH2DataSource(jdbcDataSource, properties2);
        return jdbcDataSource;
    }

    public XADataSource createXADataSource(Properties properties) throws SQLException {
        Properties properties2 = new Properties();
        if (properties != null) {
            properties2.putAll((Map<?, ?>)properties);
        }
        OsgiDataSourceFactory.rejectUnsupportedOptions(properties2);
        OsgiDataSourceFactory.rejectPoolingOptions(properties2);
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        OsgiDataSourceFactory.setupH2DataSource(jdbcDataSource, properties2);
        return jdbcDataSource;
    }

    public java.sql.Driver createDriver(Properties properties) throws SQLException {
        if (properties != null && !properties.isEmpty()) {
            throw new SQLException();
        }
        return this.driver;
    }

    private static void rejectUnsupportedOptions(Properties properties) throws SQLFeatureNotSupportedException {
        if (properties.containsKey("roleName")) {
            throw new SQLFeatureNotSupportedException("The roleName property is not supported by H2");
        }
        if (properties.containsKey("dataSourceName")) {
            throw new SQLFeatureNotSupportedException("The dataSourceName property is not supported by H2");
        }
    }

    private static void setupH2DataSource(JdbcDataSource jdbcDataSource, Properties properties) {
        if (properties.containsKey("user")) {
            jdbcDataSource.setUser((String)properties.remove("user"));
        }
        if (properties.containsKey("password")) {
            jdbcDataSource.setPassword((String)properties.remove("password"));
        }
        if (properties.containsKey("description")) {
            jdbcDataSource.setDescription((String)properties.remove("description"));
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (properties.containsKey("url")) {
            stringBuilder.append(properties.remove("url"));
            properties.remove("networkProtocol");
            properties.remove("serverName");
            properties.remove("portNumber");
            properties.remove("databaseName");
        } else {
            stringBuilder.append("jdbc:h2:");
            Object object = "";
            if (properties.containsKey("networkProtocol")) {
                object = (String)properties.remove("networkProtocol");
                stringBuilder.append((String)object).append(":");
            }
            if (properties.containsKey("serverName")) {
                stringBuilder.append("//").append(properties.remove("serverName"));
                if (properties.containsKey("portNumber")) {
                    stringBuilder.append(":").append(properties.remove("portNumber"));
                }
                stringBuilder.append("/");
            } else if (properties.containsKey("portNumber")) {
                stringBuilder.append("//localhost:").append(properties.remove("portNumber")).append("/");
            } else if (((String)object).equals("tcp") || ((String)object).equals("ssl")) {
                stringBuilder.append("//localhost/");
            }
            if (properties.containsKey("databaseName")) {
                stringBuilder.append(properties.remove("databaseName"));
            }
        }
        for (Object e : properties.keySet()) {
            stringBuilder.append(";").append(e).append("=").append(properties.get(e));
        }
        if (stringBuilder.length() > "jdbc:h2:".length()) {
            jdbcDataSource.setURL(stringBuilder.toString());
        }
    }

    private static void rejectPoolingOptions(Properties properties) throws SQLFeatureNotSupportedException {
        if (properties.containsKey("initialPoolSize") || properties.containsKey("maxIdleTime") || properties.containsKey("maxPoolSize") || properties.containsKey("maxStatements") || properties.containsKey("minPoolSize") || properties.containsKey("propertyCycle")) {
            throw new SQLFeatureNotSupportedException("Pooling properties are not supported by H2");
        }
    }

    static void registerService(BundleContext bundleContext, Driver driver) {
        Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
        hashtable.put("osgi.jdbc.driver.class", Driver.class.getName());
        hashtable.put("osgi.jdbc.driver.name", "H2 JDBC Driver");
        hashtable.put("osgi.jdbc.driver.version", Constants.FULL_VERSION);
        hashtable.put("osgi.jdbc.datasourcefactory.capability", new String[]{"driver", "datasource", "connectionpooldatasource", "xadatasource"});
        bundleContext.registerService(DataSourceFactory.class.getName(), (Object)new OsgiDataSourceFactory(driver), hashtable);
    }
}

