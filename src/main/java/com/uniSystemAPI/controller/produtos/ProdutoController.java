package com.uniSystemAPI.controller.produtos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uniSystemAPI.domain.estabelecimento.EstabelecimentoRepository;
import com.uniSystemAPI.domain.produto.DTOs.Get.DadosListagemProdutos;
import com.uniSystemAPI.domain.produto.DTOs.Get.IndexHeader;
import com.uniSystemAPI.domain.produto.DTOs.Post.DadosCadastroPizza;
import com.uniSystemAPI.domain.produto.DTOs.Post.DadosCadastroSabores;
import com.uniSystemAPI.domain.produto.pizza.Pizza;
import com.uniSystemAPI.domain.produto.pizza.Sabores;
import com.uniSystemAPI.domain.produto.repositories.CategoriaRepository;
import com.uniSystemAPI.domain.produto.repositories.PizzaRepository;
import com.uniSystemAPI.domain.produto.repositories.ProdutoRepository;
import com.uniSystemAPI.domain.produto.repositories.SaboresRepository;
import com.uniSystemAPI.domain.util.CacheNames;
import com.uniSystemAPI.domain.util.SalvaMidiaNoDisco;

import jakarta.validation.Valid;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    private final String subDiretorioImagensProduto = "produtosImages";

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private SaboresRepository saboresRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    // @PostMapping("novaMassa")
    // public ResponseEntity<String> cadastrarMassa(@RequestBody @Valid
    // DadosCadastroMassas cadastroMassas) {
    // try {
    // if
    // (this.estabelecimentoRepository.findById(cadastroMassas.estabelecimentoId())
    // == null
    // || cadastroMassas.estabelecimentoId().equals(""))
    // return ResponseEntity.badRequest().body("Erro! O estabelecimento não foi
    // encontrado");

    // Massas categoria = new Massas(cadastroMassas);
    // massasRepository.save(categoria);

    // } catch (Exception e) {
    // return ResponseEntity.badRequest().body("Erro ao salvar o tipo de massa: " +
    // e.getMessage());
    // }
    // return ResponseEntity.ok().build();
    // }

    // @PostMapping("novoTamanho")
    // public ResponseEntity<String> cadastrarTamanho(@RequestBody @Valid
    // DadosCadastroTamanhos cadastroTamanhos) {
    // try {
    // if
    // (this.estabelecimentoRepository.findById(cadastroTamanhos.estabelecimentoId())
    // == null
    // || cadastroTamanhos.estabelecimentoId().equals(""))
    // return ResponseEntity.badRequest().body("Erro! O estabelecimento não foi
    // encontrado");

    // Tamanhos tamanhos = new Tamanhos(cadastroTamanhos);
    // tamanhosRepository.save(tamanhos);

    // } catch (Exception e) {
    // return ResponseEntity.badRequest().body("Erro ao salvar o novo tamanho: " +
    // e.getMessage());
    // }
    // return ResponseEntity.ok().build();
    // }

    // @PostMapping("novaBorda")
    // public ResponseEntity<String> cadastrarBorda(@RequestBody @Valid
    // DadosCadastroBordas cadastroBordas) {
    // try {
    // if
    // (this.estabelecimentoRepository.findById(cadastroBordas.estabelecimentoId())
    // == null
    // || cadastroBordas.estabelecimentoId().equals(""))
    // return ResponseEntity.badRequest().body("Erro! O estabelecimento não foi
    // encontrado");

    // Bordas borda = new Bordas(cadastroBordas);
    // bordasRepository.save(borda);

    // } catch (Exception e) {
    // return ResponseEntity.badRequest().body("Erro ao salvar a nova borda: " +
    // e.getMessage());
    // }
    // return ResponseEntity.ok().build();
    // }

    @PostMapping("novoSabor")
    public ResponseEntity<String> cadastrarSabor(@RequestBody @Valid DadosCadastroSabores cadastroSabores) {
        try {
            if (this.estabelecimentoRepository.findById(cadastroSabores.estabelecimentoId()) == null
                    || cadastroSabores.estabelecimentoId().equals(""))
                return ResponseEntity.badRequest().body("Erro! O estabelecimento não foi encontrado");

            Sabores sabor = new Sabores(cadastroSabores);
            saboresRepository.save(sabor);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao salvar a nova borda: " + e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("novaPizza")
    @CacheEvict(value = CacheNames.NOME_CACHE_PRODUTOS, allEntries = true)
    public ResponseEntity<String> cadastrarPizza(@RequestBody @Valid DadosCadastroPizza cadastroPizza) {
        try {
            if (this.estabelecimentoRepository.findById(cadastroPizza.estabelecimentoId()) == null
                    || cadastroPizza.estabelecimentoId().equals(""))
                return ResponseEntity.badRequest().body("Erro! O estabelecimento não foi encontrado");

            if (this.categoriaRepository.findById(cadastroPizza.produto().categoriaId()) == null
                    || cadastroPizza.estabelecimentoId().equals(""))
                return ResponseEntity.badRequest().body("Erro! nenhuma categoria foi informada");

            List<Sabores> sabor = new ArrayList<>();
            cadastroPizza.saboresId().forEach((s) -> {
                var sb = saboresRepository.getReferenceById(s);
                sabor.add(sb);
            });

            var categoria = categoriaRepository.getReferenceById(cadastroPizza.produto().categoriaId());

            Pizza pizza = new Pizza(cadastroPizza);
            pizza.setSabores(sabor);
            pizza.getProduto().setCategoria(categoria);

            pizza.getProduto().setNomeImagemProduto(
                    SalvaMidiaNoDisco.salvaImagemBase64NoDisco(cadastroPizza.produto().imagemBase64(),
                            subDiretorioImagensProduto, cadastroPizza.produto().nomeImagemProduto()));

            pizzaRepository.save(pizza);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao Salvar Pizza: " + e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("{idestab}")
    @Cacheable(CacheNames.NOME_CACHE_PRODUTOS)
    public List<Map<String, Object>> listar(@PathVariable String idestab,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "false") Boolean section_list) {
        PageRequest pageRequest = PageRequest.of(page, size);
        System.out.println("requisição listarProdutos");
        List<DadosListagemProdutos> produtos = produtoRepository
                .findAllByEstabelecimentoIdOrderByCategoriaIndex(idestab,
                        pageRequest)
                .stream()
                .map(produto -> new DadosListagemProdutos(produto, new IndexHeader()))
                // .map(DadosListagemProdutos::new)
                .sorted(Comparator.comparing(p -> p.categoria().getIndex()))
                .collect(Collectors.toList());
        // final List<Integer> qtdInd = new ArrayList<>();
        final int[] ind = { 0 };
        final String[] nomeCategoria = { "" };

        produtos.forEach((e) -> {
            e.indexHeader().setTituloCategoria("");
            if (!e.categoria().getNomeCategoria().equals(nomeCategoria[0])) {
                ind[0]++;
                e.indexHeader().setTituloCategoria(e.categoria().getNomeCategoria());
                nomeCategoria[0] = e.categoria().getNomeCategoria();
            }
            e.indexHeader().setIndexHeader(ind[0] - 1);
        });
        if (section_list) {
            return produtos.stream()
                    .collect(Collectors.groupingBy(p -> p.categoria().getNomeCategoria()))
                    .entrySet()
                    .stream()
                    .map(entry -> {
                        // Aqui pode-se criar uma nova classe ou usar um Map, dependendo do que for
                        // retornar
                        // nesse caso, está sendo utilizado um Map para representar a categoria e a
                        // lista de
                        // pizzas
                        Map<String, Object> categoriaAgrupada = Map.of(
                                "title", entry.getKey(),
                                "data", entry.getValue());
                        return categoriaAgrupada;
                    })
                    .collect(Collectors.toList());
        }
        return produtos.stream().map(entry -> {
            Map<String, Object> dadosDesagrupados = Map.of("id", entry.id(), "indexHeader2", entry.indexHeader2(),
                    "nome", entry.nome(), "descricao",
                    entry.descricao(),
                    "preco", entry.preco(), "permitirVenda", entry.permitirVenda(), // "sempreDisponivel",
                                                                                    // entry.sempreDisponivel(),
                    "urlImgProd", entry.urlImgProd(), "categoria", entry.categoria(),
                    "indexHeader", entry.indexHeader().getIndexHeader(), "tituloCategoria",
                    entry.indexHeader().getTituloCategoria());
            return dadosDesagrupados;
        }).toList();
    }

    // @GetMapping("{idestab}")
    // public List<DadosListagemProdutos> listar(@PathVariable String idestab,
    // @RequestParam(defaultValue = "0") int page,
    // @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue =
    // "false") Boolean section_list) {
    // PageRequest pageRequest = PageRequest.of(page, size);

    // // final List<Integer> qtdInd = new ArrayList<>();
    // return
    // produtoRepository.findAllByEstabelecimentoIdOrderByCategoriaIndex(idestab,pageRequest).stream()
    // .map(DadosListagemProdutos::new)
    // .toList();

    // }

    @GetMapping("/imagem/{nomeImagem}")
    public ResponseEntity<Resource> obterImagem(@PathVariable String nomeImagem) {
        // System.out.println("aqui");
        Resource resource = new FileSystemResource(
                SalvaMidiaNoDisco.obtemCaminhoImagem(subDiretorioImagensProduto,
                        nomeImagem));

        if (!resource.exists() | resource == null) {

            var productImage = produtoRepository.findByNomeImagemProduto(nomeImagem);

            if (productImage != null & productImage.getImagemBase64() != null) {
                try {
                    SalvaMidiaNoDisco.salvaImagemBase64NoDisco(
                            new String(productImage.getImagemBase64(), StandardCharsets.UTF_8),
                            subDiretorioImagensProduto, productImage.getNomeImagemProduto());

                    resource = new FileSystemResource(
                            SalvaMidiaNoDisco.obtemCaminhoImagem(subDiretorioImagensProduto, nomeImagem));
                } catch (IOException e) {

                }
            } else {
                resource = new FileSystemResource(
                        SalvaMidiaNoDisco.caminhoImagemNaoEncontrada());
            }
            // return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentType(MediaType.IMAGE_PNG)
                .contentType(MediaType.IMAGE_GIF)
                .body(resource);
    }
}
