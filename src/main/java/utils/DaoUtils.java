package utils;

import java.sql.*;

/**
 * @author Nathan Salez
 */
public class DaoUtils {

    /**
     * build a new prepared statement with given arguments.
     * param connexion must be initialized. Otherwise, this method returns a null value.
     * Set returnGeneratedKeys to true if you want to create an update prepared statement.
     * @param connexion must be initialized
     * @param sql the sql statement.
     * @param returnGeneratedKeys true if you want to create an update prepared statement.
     * @param objets the params table of statement's params.
     * @return the built prepared statement.
     * @throws SQLException
     */
    public static PreparedStatement buildPreparedStatement(Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets ) throws SQLException {
        if(connexion == null)
            return null;

        PreparedStatement pstmt = connexion.prepareStatement(sql, returnGeneratedKeys ? PreparedStatement.RETURN_GENERATED_KEYS : PreparedStatement.NO_GENERATED_KEYS);
        for(int i = 1; i <= objets.length; i++)
        {
            pstmt.setObject(i,objets[i-1]);
        }

        return pstmt;
    }

    /**
     * Execute a simple select sql query, without query parameters.
     * @param connexion must be initialized
     * @param sql the sql statement
     * @return data returned by sql server.
     * @throws SQLException
     */
    public static ResultSet executeSelectStatement(Connection connexion, String sql) throws SQLException
    {
        if( connexion == null)
            return null;

        Statement stmt = connexion.createStatement();

        return stmt.executeQuery(sql);
    }

    private static void closeResultSet( ResultSet resultSet ) {
        if ( resultSet != null ) {
            try {
                resultSet.close();
            } catch ( SQLException e ) {
                System.out.println( "Échec de la fermeture du ResultSet : " + e.getMessage() );
            }
        }
    }

    private static void closeStatement( Statement statement ) {
        if ( statement != null ) {
            try {
                statement.close();
            } catch ( SQLException e ) {
                System.out.println( "Échec de la fermeture du Statement : " + e.getMessage() );
            }
        }
    }

    private static void closeConnexion( Connection connexion ) {
        if ( connexion != null ) {
            try {
                connexion.close();
            } catch ( SQLException e ) {
                System.out.println( "Échec de la fermeture de la connexion : " + e.getMessage() );
            }
        }
    }

    public static void closeDatabaseConnexion( ResultSet resultSet, Statement statement, Connection connexion ) {
        closeResultSet( resultSet );
        closeStatement( statement );
        closeConnexion( connexion );
    }

    public static void closeDatabaseConnexion( Statement statement, Connection connexion ) {
        closeStatement( statement );
        closeConnexion( connexion );
    }

    public static void closeDatabaseConnexion( ResultSet results, Connection connexion ) {
        closeResultSet( results );
        closeConnexion( connexion );
    }
}
