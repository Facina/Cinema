package com.example.android.cinemusp.persistencia;

import com.example.android.cinemusp.modelo.Filme;
import com.example.android.cinemusp.modelo.Preco;
import com.example.android.cinemusp.modelo.Sala;
import com.example.android.cinemusp.modelo.Sessao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author marcelosuckowdebarrosrodrigues
 */
public class ControleSessoes {

    public boolean inserirSessao(Sessao sessao) throws SQLException {
        String SQL = "insert into tbSessao(data,horario,tresD,legendado,imax,quatroK,lotada,idFilme,idSala,idPreco) values (?,?,?,?,?,?,?,?,?,?)";

        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = Conexao.getConnection();

            pst = conn.prepareStatement(SQL);
            pst.clearParameters();
            pst.setDate(1, sessao.getData());
            pst.setTime(2, sessao.getHorario());
            pst.setBoolean(3, sessao.isTresD());
            pst.setBoolean(4, sessao.isLegendado());
            pst.setBoolean(5, sessao.isImax());
            pst.setBoolean(6, sessao.isQuatroK());
            pst.setBoolean(7, false);
            pst.setInt(8, sessao.getFilme().getIdFilme());
            pst.setInt(9, sessao.getSala().getIdSala());
            pst.setInt(10, sessao.getPrecos().getIdPreco());
            pst.executeUpdate();
            pst.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Erro na conexão ao inserir: " + e.getMessage());
            throw new SQLException(e.getMessage());
        }
        return true;
    }

    public ArrayList<Sessao> pesquisarSessoes(int id, int op, Date data, String nome) throws CinemaException {
        String SQL = "SELECT * FROM ((tbSessao INNER JOIN tbFilme ON tbSessao.idFilme = tbFilme.idFilme) inner join tbPreco on tbPreco.idPreco = tbSessao.idPreco) inner join tbSala on tbSala.idSala = tbSessao.idSala";
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Sessao> lista = new ArrayList<Sessao>();


        switch (op) {
            case 1:
                SQL = SQL + " where tbSessao.idSala = " + id;
                break;
            case 2:
                SQL = SQL + " where data =\"" + data + "\"";
                break;
            case 3:
                SQL = SQL + " where tbSessao.idSala = " + id;
                break;
            case 4:
                SQL = SQL + " where tbSessao.idSala = " + id + " and data =\"" + data + "\"";
                break;
            case 5:
                SQL = SQL + " where nomeFilme like '%" + nome + "%'";
                break;
            case 6:
                SQL = SQL + " where nomeFilme like '%" + nome + "%' and data =\"" + data + "\"";
                break;
            case 7:
                SQL = SQL + " where nomeFilme like '%" + nome + "%' and tbSessao.idSala = " + id;
                break;
            case 8:
                SQL = SQL + " where nomeFilme like '%" + nome + "%' and data =\"" + data + "\" and tbSessao.idSala = " + id;
                break;
            case 9:
                SQL = SQL + " where data >= curdate() and tbSessao.idSala = "+ id;
        }


        try {
            conn = Conexao.getConnection();
            pst = conn.prepareStatement(SQL);
            rs = pst.executeQuery();

            while (rs.next()) {
                Sessao obj = new Sessao();
                obj.setIdSessao(rs.getInt(1));
                obj.setData(rs.getDate(2));
                obj.setHorario(rs.getTime(3));
                obj.setTresD(rs.getBoolean(4));
                obj.setLegendado(rs.getBoolean(5));
                obj.setImax(rs.getBoolean(6));
                obj.setQuatroK(rs.getBoolean(7));
                obj.setLotada(rs.getBoolean(8));

                Filme filme = new Filme();
                Preco preco = new Preco();
                Sala sala = new Sala();

                filme.setIdFilme(rs.getInt(9));
                sala.setIdSala(rs.getInt(10));
                preco.setIdPreco(rs.getInt(11));

                filme.setNome(rs.getString(13));
                filme.setDataEstreia(rs.getDate(14));
                filme.setDataSaida(rs.getDate(15));
                filme.setDuracao(rs.getInt(16));
                filme.setClassificacao(rs.getString(17));
                filme.setSinopse(rs.getString(18));
                filme.setImgLink(rs.getString(19));

                preco.setPrecoPadrao(rs.getFloat(21));
                preco.setPrecoReclinavel(rs.getFloat(22));
                preco.setPrecoMovel(rs.getFloat(23));
                preco.setPrecoObeso(rs.getFloat(24));
                preco.setPrecoCasal(rs.getFloat(25));
                preco.setPrecoCadeirante(rs.getFloat(26));

                sala.setNumeroSala(rs.getInt(28));
                sala.setNFileiras(rs.getInt(29));
                sala.setMaxAssentos(rs.getInt(30));

                obj.setFilme(filme);
                obj.setPrecos(preco);
                obj.setSala(sala);
                
                lista.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        //    JOptionPane.showMessageDialog(null, "Erro na conexão ao pesquisar Filmes: " + e.getMessage(), "ERRO", 0);
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
