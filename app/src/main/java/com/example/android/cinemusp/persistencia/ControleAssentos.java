package com.example.android.cinemusp.persistencia;

import com.example.android.cinemusp.modelo.Assento;
import com.example.android.cinemusp.modelo.AssentoCadeirante;
import com.example.android.cinemusp.modelo.AssentoCasal;
import com.example.android.cinemusp.modelo.AssentoMovel;
import com.example.android.cinemusp.modelo.AssentoObeso;
import com.example.android.cinemusp.modelo.AssentoPadrao;
import com.example.android.cinemusp.modelo.AssentoReclinavel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author marcelosuckowdebarrosrodrigues
 */
public class ControleAssentos {

    
     public boolean inserirAssento(String sql) throws SQLException {
      
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = Conexao.getConnection();

            pst = conn.prepareStatement(sql);
            pst.clearParameters();
            pst.executeUpdate();
            pst.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Erro na conexão ao inserir: " + e.getMessage());
            throw new SQLException(e.getMessage());
        }
        return true;
    }

    public ArrayList<Assento> pesquisarAssentos(int id, int op) throws CinemaException {
        String SQL = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Assento> lista = new ArrayList<Assento>();

        switch (op) {
            case 0:
                SQL = "select * from tbAssento";
                break;
            case 1:
                SQL = "select * from tbAssento where idSala = " + id;
                break;
            case 2:
                SQL = "select * from tbAssento where idAssento = " + id;
        }

        try {
            conn = Conexao.getConnection();
            pst = conn.prepareStatement(SQL);
            rs = pst.executeQuery();

            while (rs.next()) {
                Assento obj = getAssento(rs.getInt(3));
                obj.setIdAssento(rs.getInt(1));
                obj.setNumero(rs.getInt(2));
                lista.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
         //   JOptionPane.showMessageDialog(null, "Erro na conexão ao pesquisar Assentos: " + e.getMessage(), "ERRO", 0);
        } finally {
            if (pst != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    //JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage(), "ERRO", 0);
                }
            }
        }

        return lista;
    }

    private Assento getAssento(int tipoAssento) {
        switch (tipoAssento) {
            case 0:
                return null;
            case 1:
                return new AssentoPadrao();
            case 2:
                return new AssentoReclinavel();
            case 3:
                return new AssentoCadeirante();
            case 4:
                return new AssentoCasal();
            case 5:
                return new AssentoObeso();
            default:
                return new AssentoMovel();
        }
    }

    public void alterarAssento(Assento assento, int idSala, int numeroAntigo) throws SQLException {
        String SQL = "UPDATE tbAssento SET numeroAssento=?,tipoAssento=? where idSala=" + idSala + " and numeroAssento=" + numeroAntigo;
        Connection conn = null;
        PreparedStatement pst;
        try {
            conn = Conexao.getConnection();
            //conn = ConexaoDerby.getConnection();

            pst = conn.prepareStatement(SQL);
            pst.setInt(1, assento.getNumero());
            pst.setInt(2, assento.getTipo());
            pst.executeUpdate();
            conn.close();
        } catch (Exception e) {
            conn.close();
            System.out.println("Erro na conexão ao alterar: " + e.getMessage());
        }

    }

    public void deletarAssento(int id, int op) throws SQLException {
        String SQL;
        if(op == 0){
             SQL = "delete from tbAssento where idAssento=?";
        }else{
             SQL = "delete from tbAssento where idSala=?";
        }
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Conexao.getConnection();
            //conn = ConexaoDerby.getConnection();

            pst = conn.prepareStatement(SQL);
            pst.setInt(1, id);

            pst.executeUpdate();
            conn.close();
        } catch (Exception e) {
            conn.close();
            System.out.println("Erro na conexão ao deletar: " + e.getMessage());
        }
    }



    public boolean inserirAssentoSessao(String SQL) throws SQLException {  

        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = Conexao.getConnection();

            pst = conn.prepareStatement(SQL);
            pst.clearParameters();
            pst.executeUpdate();
            pst.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Erro na conexão ao inserir: " + e.getMessage());
            throw new SQLException(e.getMessage());
        }
        return true;
    }
    
    
    
    public ArrayList<Assento> pesquisarAssentosSessao(int id) throws CinemaException {
        String SQL = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Assento> lista = new ArrayList<Assento>();

        SQL = "select * from tbAssentoSessao inner join tbAssento on tbAssentoSessao.idAssento = tbAssento.idAssento where idSessao = " + id;


        try {
            conn = Conexao.getConnection();
            pst = conn.prepareStatement(SQL);
            rs = pst.executeQuery();

            while (rs.next()) {
                Assento obj = getAssento(rs.getInt(7));
                obj.setIdAssento(rs.getInt(1));
                obj.setStatus(rs.getBoolean(2));
                obj.setNumero(rs.getInt(6));
                lista.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
           // JOptionPane.showMessageDialog(null, "Erro na conexão ao pesquisar Assentos: " + e.getMessage(), "ERRO", 0);
        } finally {
            if (pst != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                  //  JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage(), "ERRO", 0);
                }
            }
        }

        return lista;
    }

}
