package br.ufg.inf.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("COMPRA")
public class Compra extends Movimentacao {

    String numeroCompra;
    String nomeFornecedor;
    Date dataCompra;

    public Compra() {

    }

    public Compra(String numeroCompra, String nomeFornecedor, Date dataCompra, Double valorTotal, List<Item> itensComprados) {
        this.dataCompra = dataCompra;
        this.numeroCompra = numeroCompra;
        this.nomeFornecedor = nomeFornecedor;
        this.valorTotal = valorTotal;
        this.itens = itensComprados;
    }

    public String getNumeroCompra() {
        return numeroCompra;
    }

    public void setNumeroCompra(String numeroCompra) {
        this.numeroCompra = numeroCompra;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

}
