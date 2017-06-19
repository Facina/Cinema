package com.example.android.cinemusp.modelo;

public abstract class Assento {

    @Override
    public String toString() {
        return "" + idAssento;
    }

    private int idAssento;
    private int numero;

    public int getIdAssentoSessao() {
        return idAssentoSessao;
    }

    public void setIdAssentoSessao(int idAssentoSessao) {
        this.idAssentoSessao = idAssentoSessao;
    }

    private int idAssentoSessao;
    private boolean status;
    private String imgL, imgR;

    public Assento() {
        status = false;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public abstract int getEspacoOcupado();

    public abstract int getTipo();

    public abstract float getPreco(Preco preco, boolean meia);

    /**
     * @return the status
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * @return the imgL
     */
    public String getImgL() {
        return imgL;
    }

    /**
     * @param imgL the imgL to set
     */
    public void setImgL(String imgL) {
        this.imgL = imgL;
    }

    /**
     * @return the imgR
     */
    public String getImgR() {
        return imgR;
    }

    /**
     * @param imgR the imgR to set
     */
    public void setImgR(String imgR) {
        this.imgR = imgR;
    }

    /**
     * @return the idAssento
     */
    public int getIdAssento() {
        return idAssento;
    }

    /**
     * @param idAssento the idAssento to set
     */
    public void setIdAssento(int idAssento) {
        this.idAssento = idAssento;
    }

    public static String gerarComandoSQLInserirAssento(Assento[][] mapaAssentos, int fileiras, int colunas,int idSala) {
       String sql = "insert into tbAssento (numeroAssento,tipoAssento,idSala) values ";
       int flag = 0;
       
        for (int i=0;i<fileiras;i++){
            for(int j=0;j<colunas;j++){
                if(mapaAssentos[i][j] != null){
                    if(flag == 0){
                        flag = 1;
                    }else{
                        sql = sql + " , ";
                    }
                    sql = sql + "( " + mapaAssentos[i][j].getNumero() + ", "+ mapaAssentos[i][j].getTipo() + ", " + idSala + ")";
                }
            }
        }
        return sql;   
    }
    
    public static String gerarComandoSQLInserirAssentoSessao(Sala sala,int idSessao) {
       String sql = "insert into tbAssentoSessao (statusAssento,idSessao,idAssento) values ";
       int flag = 0;
       
        for (int i=0;i<sala.getNFileiras();i++){
            for(int j=0;j<sala.getMaxAssentos();j++){
                if(sala.getAssento(i, j) != null){
                    if(flag == 0){
                        flag = 1;
                    }else{
                        sql = sql + " , ";
                    }
                    sql = sql + "( false ," + idSessao + ", "+ sala.getAssento(i, j).getIdAssento()+ ")";
                }
            }
        }
        return sql;   
    }
}
