package dao;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import dao.impl.jdbc.RoleJdbcDao;
import dao.impl.jdbc.UserJdbcDao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {

    private static final String PROPERTIES_FILE = "dao.properties";
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_DRIVER = "driver";
    private static final String PROPERTY_USER_NAME = "userName";
    private static final String PROPERTY_PASSWORD = "password";

    private BoneCP connectionPool;

    private DAOFactory( BoneCP connectionPool ) {
        this.connectionPool = connectionPool;
    }

    /*
     * Méthode chargée de récupérer les informations de connexion à la base de
     * données, charger le driver JDBC et retourner une instance de la Factory
     */
    public static DAOFactory getInstance() throws DAOConfigurationException {
        Properties properties = new Properties();
        String url;
        String driver;
        String userName;
        String password;
        BoneCP connectionPool;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

        if ( propertiesFile == null ) {
            throw new DAOConfigurationException( "Properties file " + PROPERTIES_FILE + " not found." );
        }

        try {
            properties.load( propertiesFile );
            url = properties.getProperty( PROPERTY_URL );
            driver = properties.getProperty( PROPERTY_DRIVER );
            userName = properties.getProperty( PROPERTY_USER_NAME );
            password = properties.getProperty( PROPERTY_PASSWORD );
            propertiesFile.close();
        } catch ( IOException e ) {
            throw new DAOConfigurationException( "Impossible de charger le fichier properties " + PROPERTIES_FILE, e );
        }

        try {
            Class.forName( driver );
        } catch ( ClassNotFoundException e ) {
            throw new DAOConfigurationException( "Driver " + driver + " not found in the classpath.", e );
        }

        try {
            /*
             * Création d'une configuration de pool de connexions via l'objet
             * BoneCPConfig et les différents setters associés.
             */
            BoneCPConfig config = new BoneCPConfig();
            config.setJdbcUrl( url );
            config.setUsername( userName );
            config.setPassword( password );
            config.setMinConnectionsPerPartition( 5 );
            config.setMaxConnectionsPerPartition( 10 );
            config.setPartitionCount( 2 );
            connectionPool = new BoneCP( config );
        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DAOConfigurationException( "Error while configuring connection pool", e );
        }
        /*
         * Enregistrement du pool créé dans une variable d'instance via un appel
         * au constructeur de DAOFactory
         */
        return new DAOFactory( connectionPool );
    }

    /* Méthode chargée de fournir une connexion à la base de données */
    public Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    /*
     * Méthodes de récupération de l'implémentation des différents JdbcDAO (un seul
     * pour le moment)
     */
    public UserDao getUserDao() {
        return new UserJdbcDao( this );
    }

    public RoleDao getRoleDao() {
        return new RoleJdbcDao( this );
    }
}