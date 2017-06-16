package com.example.android.cinemusp.persistencia;

import com.example.android.cinemusp.modelo.Filme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author marcelosuckowdebarrosrodrigues
 */
public class ControleFilmes {

    public boolean inserirFilme(Filme filme) throws SQLException {
        String SQL = "insert into tbFilme(nomeFilme,dataEstreia,dataSaida,duracao,classificacao,sinopse,imgLink) values (?,?,?,?,?,?,?)";

        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = Conexao.getConnection();

            pst = conn.prepareStatement(SQL);
            pst.clearParameters();
            pst.setString(1, filme.getNome());
            pst.setDate(2, filme.getDataEstreia());
            pst.setDate(3, filme.getDataSaida());
            pst.setInt(4, filme.getDuracao());
            pst.setString(5, filme.getClassificacao());
            pst.setString(6, filme.getSinopse());
            pst.setString(7, filme.getImgLink());
            pst.executeUpdate();
            pst.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Erro na conexão ao inserir: " + e.getMessage());
            throw new SQLException(e.getMessage());
        }
        return true;
    }

    public ArrayList<Filme> pesquisarFilmes(String id, int op) throws CinemaException {
        String SQL = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Filme> lista = new ArrayList<Filme>();

        switch (op) {
            case 0:
                SQL = "select * from tbFilme";
                break;
            case 1:
                SQL = "select * from tbFilme where nomeFilme like '%" + id + "%'";
                break;
            case 2:
                SQL = "select * from tbFilme where (dataSaida >= curdate() or dataSaida is null) and dataEstreia <= curdate()";
                break;
            case 3:
                SQL = "select * from tbFilme where (dataSaida >= curdate() or dataSaida is null) and dataEstreia <= curdate() and nomeFilme like '%" + id + "%'";
                break;
            case 4:
                SQL = "select * from tbFilme where idFilme = " + Integer.parseInt(id);
                break;

        }

        try {
            conn = Conexao.getConnection();
            pst = conn.prepareStatement(SQL);
            rs = pst.executeQuery();

            while (rs.next()) {
                Filme obj = new Filme();
                obj.setIdFilme(rs.getInt(1));
                obj.setNome(rs.getString(2));
                obj.setDataEstreia(rs.getDate(3));
                obj.setDataSaida(rs.getDate(4));
                obj.setDuracao(rs.getInt(5));
                obj.setClassificacao(rs.getString(6));
                obj.setSinopse(rs.getString(7));
                obj.setImgLink(rs.getString(8));

                lista.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pst != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                e.printStackTrace();
                }
            }
        }

        return lista;
    }

    public void alterarFilme(Filme filme, int idFilme) throws SQLException {
        String SQL = "UPDATE tbFilme SET nomeFilme=?,dataEstreia=?,dataSaida=?,duracao=?,classificacao=?,sinopse=?,imgLink=? where idFilme=" + idFilme;
        Connection conn = null;
        PreparedStatement pst;
        try {
            conn = Conexao.getConnection();
            //conn = ConexaoDerby.getConnection();

            pst = conn.prepareStatement(SQL);
            pst.setString(1, filme.getNome());
            pst.setDate(2, filme.getDataEstreia());
            pst.setDate(3, filme.getDataSaida());
            pst.setInt(4, filme.getDuracao());
            pst.setString(5, filme.getClassificacao());
            pst.setString(6, filme.getSinopse());
            pst.setString(7, filme.getImgLink());
            pst.executeUpdate();
            conn.close();
        } catch (Exception e) {
            conn.close();
            System.out.println("Erro na conexão ao alterar: " + e.getMessage());
        }

    }

    public void deletarFilme(Filme filme) throws SQLException {
        String SQL = "delete from tbFilme where idFilme=?";
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Conexao.getConnection();
            //conn = ConexaoDerby.getConnection();

            pst = conn.prepareStatement(SQL);
            pst.setInt(1, filme.getIdFilme());

            pst.executeUpdate();
            conn.close();
        } catch (Exception e) {
            conn.close();
            System.out.println("Erro na conexão ao deletar: " + e.getMessage());
        }
    }
}
