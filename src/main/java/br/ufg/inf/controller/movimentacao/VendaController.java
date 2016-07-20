package br.ufg.inf.controller.movimentacao;

import br.ufg.inf.model.Produto;
import br.ufg.inf.repository.ItemRepository;
import br.ufg.inf.repository.ProdutoRepository;
import br.ufg.inf.repository.VendaRepository;
import br.ufg.inf.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class VendaController {

    @Autowired
    VendaRepository vendaRepository;
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MovimentacaoService movimentacaoService;

    @RequestMapping("/novaVenda")
    public String novaVenda(Model model) {

        Iterable<Produto> listaProdutos = produtoRepository.findAll();
        model.addAttribute("listaProdutos", listaProdutos);

        return "movimentacao/novaVenda";
    }

    @RequestMapping(value = "/registrarVenda")
    @ResponseStatus(value = HttpStatus.OK)
    public void registrarVenda(String cliente, String documento, Double valorTotal, String vendedor, Double desconto,
                               String itensJSON) {
        Double descontoPercentual = calculaPorcentagem(desconto);
        movimentacaoService.registarVenda(documento, descontoPercentual, cliente, vendedor, itensJSON, valorTotal);
    }

    private Double calculaPorcentagem(Double desconto) {
        if (desconto != null) {
            return desconto / 100;
        }
        return Double.MIN_VALUE;
    }

}
