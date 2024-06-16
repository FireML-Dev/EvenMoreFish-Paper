package com.oheers.fish.database.migrate.migrations;


import com.oheers.fish.database.DatabaseUtil;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;

public class V5__AddUserSalesInfoToUsersTable extends BaseJavaMigration {
    /*
    We should add a total sold column, and total money earned from selling.
     */
    @Override
    public void migrate(@NotNull Context context) throws Exception {
        String sql = "ALTER TABLE ${table.prefix}users " +
            "ADD fish_sold INTEGER DEFAULT 0;";
        String sql2 = "ALTER TABLE ${table.prefix}users " +
            "ADD money_earned DOUBLE DEFAULT 0;";
        try (PreparedStatement statement = context.getConnection().prepareStatement(DatabaseUtil.parseSqlString(sql, context.getConnection()))){
            statement.execute();
        }
        try (PreparedStatement statement = context.getConnection().prepareStatement(DatabaseUtil.parseSqlString(sql2, context.getConnection()))){
            statement.execute();
        }
    }
}
