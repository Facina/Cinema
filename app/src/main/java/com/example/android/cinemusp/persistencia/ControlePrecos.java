/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.android.cinemusp.persistencia;

import com.example.android.cinemusp.modelo.Preco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author marcelosuckowdebarrosrodrigues
 */
public class ControlePrecos {
     public boolean inserirPreco(Preco precos) throws SQLException {
        String SQL = "insert into tbPreco(precoPadrao,precoReclinavel,precoMovel,precoObeso,precoCasal,precoCadeirante) values (?,?,?,?,?,?)";

        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = Conexao.getConnection();

            pst = conn.prepareStatement(SQL);
            pst.clearParameters();
            pst.setFloat(1, precos.getPrecoPadrao());
            pst.setFloat(2, precos.getPrecoReclinavel());
            pst.setFloat(3, precos.getPrecoMovel());
            pst.setFloat(4, precos.getPrecoObeso());
            pst.setFloat(5, precos.getPrecoCasal());
            pst.setFloat(6, precos.getPrecoCadeirante());
            pst.executeUpdate();
            pst.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Erro na conexão ao inserir: " + e.getMessage());
            throw new SQLException(e.getMessage());
        }
        return true;
    }
     
     
     public ArrayList<Preco> pesquisarPrecos(int id,int op) throws CinemaException {
        String SQL = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Preco> lista = new ArrayList<Preco>();

        switch (op) {
            case 0:
                SQL = "select * from tbPreco";
                break;
            case 1:
                SQL = "select * from tbPreco where idPreco = " + id;
                break;
        }

        try {
            conn = Conexao.getConnection();
            pst = conn.prepareStatement(SQL);
            rs = pst.executeQuery();

            while (rs.next()) {
                Preco obj = new Preco();
                obj.setIdPreco(rs.getInt(1));
                obj.setPrecoPadrao(rs.getFloat(2));
                obj.setPrecoReclinavel(rs.getFloat(3));
                obj.setPrecoMovel(rs.getFloat(4));
                obj.setPrecoObeso(rs.getFloat(5));
                obj.setPrecoCasal(rs.getFloat(6));
                obj.setPrecoCadeirante(rs.getFloat(7));
                lista.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
           // JOptionPane.showMessageDialog(null, "Erro na conexão ao pesquisar Filmes: " + e.getMessage(), "ERRO", 0);
        } finally {
            if (pst != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                   // JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage(), "ERRO", 0);
                }
            }
        }

        return lista;
    }
}
