package com.example.android.cinemusp.persistencia;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author marcelosuckowdebarrosrodrigues
 */
public class Conexao {

    
    public static String status = "Não conectou...";

    public Conexao() {
    }

    //Método de Conexão// 
    public static Connection getConnection() {
        Connection connection;
        //atributo do tipo Connection 
        try {
            // Carregando o JDBC Driver padrão 
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName);
            // Configurando a nossa conexão com um banco de dados// 
            
            //caminho do servidor do BD 
            String serverName = "db4free.net:3307";
            
            //nome do seu banco de dados
            String myDatabase = "dbcinema";
             
            String url = "jdbc:mysql://" + serverName + "/" + myDatabase;
            
            //nome de um usuário de seu BD
            String username = "projetopoo";
            
            //sua senha de acesso
            String password = "trabalhopooo";

            connection = DriverManager.getConnection(url, username, password);
           
            //Testa sua conexão// 
            if (connection != null) {
                status = ("STATUS--->Conectado com sucesso!");
            } else {
                status = ("STATUS--->Não foi possivel realizar conexão");
            }
            return connection;
        } catch (ClassNotFoundException e) {
            
            //Driver não encontrado 
            System.out.println("O driver expecificado nao foi encontrado.");
            return null;
            
        } catch (SQLException e) {
            
            //Não conseguindo se conectar ao banco
            e.printStackTrace();
            return null;
            
        }
    }
//Método que retorna o status da sua conexão// 
    public static String statusConection() {
        return status;
    }

//Método que fecha sua conexão// 
    public static boolean FecharConexao() {
        try {
            getConnection().close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

//Método que reinicia sua conexão// 
    static Connection ReiniciarConexao() {
        FecharConexao();
        return getConnection();
    }

}
