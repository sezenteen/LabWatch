package com.example.labwatch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.List;

@Configuration
public class DatabaseSchemaConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSchemaConfig.class);
    private static final List<IdentityColumn> IDENTITY_COLUMNS = List.of(
            new IdentityColumn("users", "idusers"),
            new IdentityColumn("computers", "idcomputers"),
            new IdentityColumn("activities", "idactivities"),
            new IdentityColumn("alerts", "idalerts"),
            new IdentityColumn("blocked_apps", "idblocked_apps")
    );

    @Bean
    ApplicationRunner ensureAutoIncrementIds(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        return args -> {
            if (!isMySql(dataSource)) {
                logger.debug("Skipping MySQL auto-increment schema check for non-MySQL database");
                return;
            }

            for (IdentityColumn identityColumn : IDENTITY_COLUMNS) {
                ensureAutoIncrement(jdbcTemplate, identityColumn);
            }
        };
    }

    private boolean isMySql(DataSource dataSource) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            return metaData.getDatabaseProductName().toLowerCase().contains("mysql");
        }
    }

    private void ensureAutoIncrement(JdbcTemplate jdbcTemplate, IdentityColumn identityColumn) {
        List<ColumnMetadata> columns = jdbcTemplate.query("""
                        SELECT COLUMN_TYPE, EXTRA, COLUMN_KEY
                        FROM INFORMATION_SCHEMA.COLUMNS
                        WHERE TABLE_SCHEMA = DATABASE()
                          AND TABLE_NAME = ?
                          AND COLUMN_NAME = ?
                        """,
                (rs, rowNum) -> new ColumnMetadata(
                        rs.getString("COLUMN_TYPE"),
                        rs.getString("EXTRA"),
                        rs.getString("COLUMN_KEY")),
                identityColumn.tableName(),
                identityColumn.columnName());

        if (columns.isEmpty()) {
            logger.warn("Skipping auto-increment check because {}.{} does not exist",
                    identityColumn.tableName(), identityColumn.columnName());
            return;
        }

        ColumnMetadata column = columns.get(0);
        if (column.extra() != null && column.extra().toLowerCase().contains("auto_increment")) {
            return;
        }

        ensureIndexed(jdbcTemplate, identityColumn, column);

        String statement = "ALTER TABLE " + identityColumn.tableName()
                + " MODIFY COLUMN " + identityColumn.columnName()
                + " " + column.columnType()
                + " NOT NULL AUTO_INCREMENT";

        jdbcTemplate.execute(statement);
        logger.info("Updated {}.{} to AUTO_INCREMENT",
                identityColumn.tableName(), identityColumn.columnName());
    }

    private void ensureIndexed(JdbcTemplate jdbcTemplate, IdentityColumn identityColumn, ColumnMetadata column) {
        if (column.columnKey() != null && !column.columnKey().isBlank()) {
            return;
        }

        Integer primaryKeyCount = jdbcTemplate.queryForObject("""
                        SELECT COUNT(*)
                        FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
                        WHERE TABLE_SCHEMA = DATABASE()
                          AND TABLE_NAME = ?
                          AND CONSTRAINT_TYPE = 'PRIMARY KEY'
                        """,
                Integer.class,
                identityColumn.tableName());

        if (primaryKeyCount != null && primaryKeyCount == 0) {
            jdbcTemplate.execute("ALTER TABLE " + identityColumn.tableName()
                    + " ADD PRIMARY KEY (" + identityColumn.columnName() + ")");
        } else {
            jdbcTemplate.execute("CREATE INDEX idx_" + identityColumn.tableName()
                    + "_" + identityColumn.columnName()
                    + " ON " + identityColumn.tableName()
                    + " (" + identityColumn.columnName() + ")");
        }
    }

    private record IdentityColumn(String tableName, String columnName) {
    }

    private record ColumnMetadata(String columnType, String extra, String columnKey) {
    }
}
