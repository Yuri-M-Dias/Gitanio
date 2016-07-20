package br.ufg.inf.service;

import br.ufg.inf.model.Categoria;
import br.ufg.inf.model.Produto;
import br.ufg.inf.repository.CategoriaRepository;
import br.ufg.inf.repository.CompraRepository;
import br.ufg.inf.repository.ProdutoRepository;
import br.ufg.inf.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private VendaRepository vendaRepository;

    public Iterable<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Produto salvaProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Iterable<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria procuraUmaCategoria(Long id) {
        return categoriaRepository.findOne(id);
    }

    public Produto procurarUmProdutoPorId(Long id) {
        return produtoRepository.findOne(id);
    }

    public Produto procurarUmProdutoPorCodigo(int codigo) {
        List<Produto> produtos = produtoRepository.findByCodigo(codigo);
        if (!produtos.isEmpty()) {
            return produtos.get(0);
        } else {
            return null;
        }
    }

    public void excluirProduto(Long idProduto) {
        produtoRepository.delete(idProduto);
    }

    private Produto adicionaQntProdutosEstoque(Produto produto, int quantidadeParaAdicionar) {
        int quantidadeAtual = produto.getQuantidadeEstoque();
        produto.setQuantidadeEstoque(quantidadeAtual + quantidadeParaAdicionar);
        return produtoRepository.save(produto);
    }

    private Produto removeQntProdutosEstoque(Produto produto, int quantidadeParaRemover) {
        int quantidadeAtual = produto.getQuantidadeEstoque();
        produto.setQuantidadeEstoque(quantidadeAtual - quantidadeParaRemover);
        return produtoRepository.save(produto);
    }

    public Produto adicionaQntProdutosEstoque(Long idProduto, int quantidadeParaAdicionar) {
        Produto produto = produtoRepository.findOne(idProduto);
        return adicionaQntProdutosEstoque(produto, quantidadeParaAdicionar);
    }

    public Produto removeQntProdutosEstoque(Long idProduto, int quantidadeParaRemover) {
        Produto produto = produtoRepository.findOne(idProduto);
        return removeQntProdutosEstoque(produto, quantidadeParaRemover);
    }

}
