package com.calc.test.framework.database;

import com.calc.test.framework.context.ConfigurationDataContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Roshan
 */
public class DBConnector {

    public static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        String strConnection = "jdbc:oracle:thin:@"+ ConfigurationDataContext.dbName;
        return DriverManager.getConnection(strConnection,
                ConfigurationDataContext.dbUser,
                ConfigurationDataContext.dbPassword);
    }






}
