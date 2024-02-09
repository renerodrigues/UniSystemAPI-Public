package com.uniSystemAPI.domain.produto.DTOs.Get;

import com.uniSystemAPI.domain.produto.Categoria;
import com.uniSystemAPI.domain.produto.Produto;
import com.uniSystemAPI.domain.util.MontarURLdeImagem;

public record DadosListagemProdutos(
                String id,
                Categoria categoria,
                String nome,
                String descricao,
                double preco,
                Boolean permitirVenda,
                Boolean sempreDisponivel,
                String urlImgProd,
                Integer indexHeader2,
                IndexHeader indexHeader
// List<Tamanhos> tamanhos,
// List<Massas> massas,
// List<Bordas> bordas,
// List<Sabores> sabores
) {
        public DadosListagemProdutos(Produto produto, IndexHeader indexHeader) {
                // this(pizza.getId(),pizza.getProduto().getCategoria(),
                // this(
                // pizza.getProduto(),pizza.getTamanhos(),pizza.getMassas(),pizza.getBordas(),pizza.getSabores());
                this(produto.getId(), produto.getCategoria(), produto.getNome(), produto.getDescricao(),
                                produto.getPreco(), produto.getPermitirVenda(), produto.getSempreDisponivel(),
                                MontarURLdeImagem.montaURLImagemProduto(produto.getNomeImagemProduto()),
                                produto.getIndexHeader(),
                                indexHeader);
                // produtos(pizza.getProduto()));
                // this.data = data;
        }
        // public DadosListagemProdutos(Produto produto, IndexHeader indexHeader) {
        // // this(pizza.getId(),pizza.getProduto().getCategoria(),
        // // this(
        // //
        // pizza.getProduto(),pizza.getTamanhos(),pizza.getMassas(),pizza.getBordas(),pizza.getSabores());
        // this(produto.getId(), produto.getCategoria(), produto.getNome(),
        // produto.getDescricao(),
        // produto.getPreco(), produto.getPermitirVenda(),
        // produto.getSempreDisponivel(),
        // MontarURLdeImagem.montaURLImagemProduto(produto.getNomeImagemProduto()),
        // indexHeader);
        // // produtos(pizza.getProduto()));
        // // this.data = data;
        // }

        // private static List<Object> dados(Pizza pizza) {

        // List<Object> lista = new ArrayList<>();

        // String title = pizza.getProduto().getCategoria().getNomeCategoria();

        // lista.add(title);

        // return lista;
        // }

        // private static List<Produto> produtos(Produto produto) {
        // List<Produto> protudos = new ArrayList<>();
        // protudos.add(produto);
        // return protudos;
        // }
}
