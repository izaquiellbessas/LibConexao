/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao.bd.mysql;

import conexao.bd.UsuarioDB;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author izaquiellopesdebessas
 */
public class ConectarDB {

    private Connection conn;
    private static final String DRIVER_PADRAO_MYSQL = "org.gjt.mm.mysql.Driver";

    /**
     * Construtor padrão da classe
     * @param u Classe usuário do banco de dados
     */
    public void conecte(UsuarioDB u) {
        try {
            UsuarioDB us = u;

            Class.forName(DRIVER_PADRAO_MYSQL);
            conn = DriverManager.getConnection(us.getUrl(), us.getUsuario(), us.getSenha());
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ConectarDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Retorna conexão com o banco de dados do tipo Connection
     * @return Connection
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * Fecha um ResultSet
     * @param rs ResultSet
     */
    public void fechar(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
            }
        }
    }

    /**
     * Fecha um Statement
     * @param stmt Statement
     */
    public void fechar(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
            }
        }
    }

    /**
     * Fechar a conexão pré-estabelecida
     */
    public void fecharConexao() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }
}
