/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.android.cinemusp.persistencia;

import com.example.android.cinemusp.modelo.Sala;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author marcelosuckowdebarrosrodrigues
 */
public class ControleSalas {

    public boolean inserirSala(Sala sala) throws SQLException {
        String SQL = "insert into tbSala(numeroSala,fileiras,maxAssentos) values (?,?,?)";

        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = Conexao.getConnection();

            pst = conn.prepareStatement(SQL);
            pst.clearParameters();
            pst.setInt(1, sala.getNumeroSala());
            pst.setInt(2, sala.getNFileiras());
            pst.setInt(3, sala.getMaxAssentos());
            pst.executeUpdate();
            pst.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Erro na conexão ao inserir: " + e.getMessage());
            throw new SQLException(e.getMessage());
        }
        return true;
    }

    public ArrayList<Sala> pesquisarSalas(int id, int op) throws CinemaException {
        String SQL = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Sala> lista = new ArrayList<Sala>();

        switch (op) {
            case 0:
                SQL = "select * from tbSala";
                break;
            case 1:
                SQL = "select * from tbSala where numeroSala = " + id;
                break;
            case 2:
                SQL = "select * from tbSala where idSala = " + id;
                break;
        }

        try {
            conn = Conexao.getConnection();
            pst = conn.prepareStatement(SQL);
            rs = pst.executeQuery();

            while (rs.next()) {
                Sala obj = new Sala();
                obj.setIdSala(rs.getInt(1));
                obj.setNumeroSala(rs.getInt(2));
                obj.setNFileiras(rs.getInt(3));
                obj.setMaxAssentos(rs.getInt(4));
                lista.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
       //     JOptionPane.showMessageDialog(null, "Erro na conexão ao pesquisar Filmes: " + e.getMessage(), "ERRO", 0);
        } finally {
            if (pst != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                //    JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage(), "ERRO", 0);
                }
            }
        }

        return lista;
    }

    public void deletarSala(Sala sala) throws SQLException {
        String SQL = "delete from tbSala where idSala=?";
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Conexao.getConnection();
            //conn = ConexaoDerby.getConnection();

            pst = conn.prepareStatement(SQL);
            pst.setInt(1, sala.getIdSala());

            pst.executeUpdate();
            conn.close();
        } catch (Exception e) {
            conn.close();
            System.out.println("Erro na conexão ao deletar: " + e.getMessage());
        }
    }

    public void alterarSala(Sala sala) throws SQLException {
        String SQL = "UPDATE tbSala SET numeroSala=?,fileiras=?,maxAssentos=? where idSala=" + sala.getIdSala();
        Connection conn = null;
        PreparedStatement pst;
        try {
            conn = Conexao.getConnection();
            //conn = ConexaoDerby.getConnection();

            pst = conn.prepareStatement(SQL);
            pst.setInt(1, sala.getNumeroSala());
            pst.setInt(2, sala.getNFileiras());
            pst.setInt(3, sala.getMaxAssentos());
            pst.executeUpdate();
            conn.close();
        } catch (Exception e) {
            conn.close();
            System.out.println("Erro na conexão ao alterar: " + e.getMessage());
        }

    }
}
