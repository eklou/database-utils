/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.seb.database.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cette classe retourne une connexion à une base de données MySQL Elle
 * implémente le pattern Singleton
 *
 * @author formation
 */
public class DatabaseConnection {

    /**
     * Variable destinée à stocker l'instance de la connexion
     */
    private static Connection instance;

    /**
     * Constructeur privé pour éviter de pouvoir instancier la classe depuis
     * l'extérieur
     */
    private DatabaseConnection() {
    }///Fin du constructeur

    /**
     * Retourne un objet de type Connection
     *
     * @return Connetion
     */
    public static Connection getInstance() throws SQLException {

        FileInputStream fis = null;
        try {
            //Instanciation d'un objet properties qui contiendra la configuration
            Properties config = new Properties();
            
            //ouverture du fichier qui contient les infos
            fis = new FileInputStream("./config/app.properties");
            
            //Chargement des données du fichier ds l'objet properties
            config.load(fis);
            fis.close();
            
            //Recuperation des informations de configuration ds des variables
            String dbHost = config.getProperty("db.host", "localhost");
            String dbName = config.getProperty("db.name", "bibliotheque");
            String dbUser = config.getProperty("db.user", "root");
            String dbPass = config.getProperty("db.pass", "");
            
            //Si l'instance est nulle on instancie une nouvelle connexion
            if (instance == null) {
                instance = DriverManager.getConnection(
                        "jdbc:mysql://"+dbHost+"/"+ dbName,
                        dbUser,
                        dbPass
                );
            }

        } catch (IOException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

}///Fin de la classe
