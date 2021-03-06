package br.ufg.inf.controller.produto;

import br.ufg.inf.model.Categoria;
import br.ufg.inf.model.Produto;
import br.ufg.inf.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @RequestMapping("/listarProdutos")
    public String listarProdutos(Model model) {
        //TODO: implementar paginação
        Iterable<Produto> listaProdutos = produtoService.listarProdutos();
        model.addAttribute("listaProdutos", listaProdutos);
        return "produto/listagemProdutos";
    }

    @RequestMapping("/criaProduto")
    public String produto(Model model) {
        Iterable<Categoria> listaCategorias = produtoService.listarCategorias();
        model.addAttribute("listaCategorias", listaCategorias);
        return "produto/produto";
    }

    @RequestMapping("/criaProduto/{id}")
    public String produto(@PathVariable("id") Long idProduto, Model model) {

        Iterable<Categoria> listaCategorias = produtoService.listarCategorias();
        Produto produto = produtoService.procurarUmProdutoPorId(idProduto);

        model.addAttribute("listaCategorias", listaCategorias);
        model.addAttribute("produto",produto);

        return "produto/produto";
    }

    @RequestMapping("/salvaProduto")
    public String salvaProduto(Integer codigo, String descricao, Long idCategoria,
                               Double valorUnitario, Integer quantidadeMinima, Model model) {
        Categoria categoria = produtoService.procuraUmaCategoria(idCategoria);
        Produto produto = produtoService.procurarUmProdutoPorCodigo(codigo);

        if (produto == null) {
            Produto newProduto = new Produto(
                codigo,
                descricao,
                valorUnitario,
                quantidadeMinima,
                categoria
            );
            produtoService.salvaProduto(newProduto);
        } else {
            produto.setCodigo(codigo);
            produto.setDescricao(descricao);
            produto.setCategoria(categoria);
            produto.setQuantidadeMinima(quantidadeMinima);
            produtoService.salvaProduto(produto);
        }

        Iterable<Produto> listaProdutos = produtoService.listarProdutos();
        model.addAttribute("listaProdutos", listaProdutos);
        return "produto/listagemProdutos";
    }

    @RequestMapping("/excluirProduto")
    public String excluirProduto(Long idProduto) {
        produtoService.excluirProduto(idProduto);
        return "produto/listagemProdutos";
    }

}
