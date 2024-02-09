package com.uniSystemAPI.domain.usuario;

import java.util.List;
// import java.util.Optional;

//import java.sql.Blob;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    // @Query(value = """
    // SELECT
    // u.id, u.pessoa_id, p.foto, p.id
    // FROM
    // usuarios AS u
    // INNER JOIN
    // pessoas AS p ON u.pessoa_id = p.id
    // WHERE
    // u.id = :id
    // """,nativeQuery = true)
    // Blob findByIdNative(Long id);

    // UserDetails findByEmail(String email);

    // Optional<Usuario> getReferenceByEmail(String email);
     Usuario findByLoginIgnoreCase(String email);

    Usuario findByEstabelecimentoId(String estabelecimentoId);

    List<Usuario> findAllByEstabelecimentoIdOrderByPessoaNomeAsc(String estabelecimentoId);
    // UserDetails findByPessoaCpf(Pessoa pessoa);

    Usuario findByPessoaNomeImagem(String foto);
}
