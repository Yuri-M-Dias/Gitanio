package br.ufg.inf.controller.movimentacao;

import br.ufg.inf.model.Compra;
import br.ufg.inf.model.Item;
import br.ufg.inf.model.Produto;
import br.ufg.inf.repository.CompraRepository;
import br.ufg.inf.repository.ItemRepository;
import br.ufg.inf.repository.ProdutoRepository;
import br.ufg.inf.service.MovimentacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class CompraController {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private MovimentacaoService movimentacaoService;

    @RequestMapping("/novaCompra")
    public String novaCompra(Model model) {
        Iterable<Produto> listaProdutos = produtoRepository.findAll();
        model.addAttribute("listaProdutos", listaProdutos);
        return "movimentacao/novaCompra";
    }

    @RequestMapping("/registrarCompra")
    @ResponseStatus(HttpStatus.OK)
    public void registrarCompra(String documento, String fornecedor, String itensJSON, String dataCompra, Double valorCompra) {
        Date dataCompraFormatada = createDate(dataCompra, "");
        movimentacaoService.registrarCompra(documento, fornecedor, dataCompraFormatada, valorCompra, itensJSON);
    }

    private Date createDate(String dataText, String format) {
        if (format == null || format.equals("")) {
            format = "dd/MM/yyyy";
        }

        Date data = null;

        try {
            data = new SimpleDateFormat(format).parse(dataText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }
}
