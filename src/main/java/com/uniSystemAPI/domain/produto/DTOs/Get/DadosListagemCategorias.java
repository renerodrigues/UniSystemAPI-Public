package com.uniSystemAPI.domain.produto.DTOs.Get;

import java.util.List;

import com.uniSystemAPI.domain.produto.Categoria;
import com.uniSystemAPI.domain.produto.pizza.Bordas;
import com.uniSystemAPI.domain.produto.pizza.Massas;
import com.uniSystemAPI.domain.produto.pizza.Tamanhos;

public record DadosListagemCategorias(
                String id,
                String nomeCategoria,
                int index,
                boolean isPizza,
                String estabelecimentoId,
                List<Tamanhos> tamanhos,
                List<Massas> massas,
                List<Bordas> bordas) {

        public DadosListagemCategorias(Categoria categoria) {

                this(categoria.getId(), categoria.getNomeCategoria(), categoria.getIndex(), categoria.isPizza(),
                                categoria.getEstabelecimentoId(), categoria.getTamanhos(),
                                categoria.getMassas(),
                                categoria.getBordas());
        }

        // private static List<Tamanhos> carregaTamanhos(List<Tamanhos> t) {
        // List<Tamanhos> tamanhos = new ArrayList<>();
        // tamanhos.addAll(t);
        // return tamanhos;
        // }
}
