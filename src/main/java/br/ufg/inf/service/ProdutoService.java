package br.ufg.inf.service;

import br.ufg.inf.model.Categoria;
import br.ufg.inf.model.Produto;
import br.ufg.inf.repository.CategoriaRepository;
import br.ufg.inf.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Iterable<Produto> listarProdutos(){
        return produtoRepository.findAll();
    }

    public Produto salvaProduto(Produto produto){
        return produtoRepository.save(produto);
    }

    public Iterable<Categoria> listarCategorias(){
        return categoriaRepository.findAll();
    }

    public Categoria procuraUmaCategoria(Long id){
        return categoriaRepository.findOne(id);
    }

    public void excluirProduto(Long idProduto){
        produtoRepository.delete(idProduto);
    }

}
