package br.ufg.inf.service;

import br.ufg.inf.model.Compra;
import br.ufg.inf.model.Item;
import br.ufg.inf.model.Venda;
import br.ufg.inf.repository.CompraRepository;
import br.ufg.inf.repository.ItemRepository;
import br.ufg.inf.repository.VendaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MovimentacaoService {

    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ProdutoService produtoService;

    public Venda registarVenda(String numeroVenda, Double descontoPercentual, String nomeCliente, String nomeVendedor,
                               String itensJSON, Double valorTotal) {

        List<Item> itensVendidos = this.salvarItensPeloJson(itensJSON);
        Venda venda = new Venda(numeroVenda, descontoPercentual, nomeVendedor, nomeCliente, itensVendidos, valorTotal);

        Venda vendaSalva = vendaRepository.save(venda);
        atualizaEstoqueVendido(itensVendidos);
        return vendaSalva;
    }

    public Compra registrarCompra(String numeroCompra, String nomeFornecedor, Date dataCompraFormatada,
                                  Double valorCompra, String itensJSON) {

        List<Item> itensComprados = salvarItensPeloJson(itensJSON);
        Compra compra = new Compra(numeroCompra, nomeFornecedor, dataCompraFormatada, valorCompra, itensComprados);
        Compra compraSalva = compraRepository.save(compra);
        atualizaEstoqueComprado(itensComprados);
        return compraSalva;
    }

    ;

    private List<Item> salvarItensPeloJson(String itensJSON) {
        List<Item> itens = construirListaItensFromJSON(itensJSON);

        return Lists.newArrayList(itemRepository.save(itens));
    }

    //TODO: Esse método deveria ser de responsabilidade do controlador
    private List<Item> construirListaItensFromJSON(String itensJSON) {
        ObjectMapper mapper = new ObjectMapper();
        List<Map> listaMapas = null;
        try {
            listaMapas = mapper.readValue(itensJSON, ArrayList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Item> itens = new ArrayList<>();

        for (Map itemMap : listaMapas) {
            Long idProduto = Long.parseLong(itemMap.get("idProduto").toString());
            Integer quantidade = Integer.parseInt(itemMap.get("quantidade").toString());
            itens.add(new Item(idProduto, quantidade));
        }
        return itens;
    }

    private void atualizaEstoqueVendido(List<Item> itens) {
        for (Item item : itens) {
            produtoService.removeQntProdutosEstoque(item.getIdProduto(), item.getQuantidade());
        }
    }

    private void atualizaEstoqueComprado(List<Item> itens) {
        for (Item item : itens) {
            produtoService.adicionaQntProdutosEstoque(item.getIdProduto(), item.getQuantidade());
        }
    }
}
