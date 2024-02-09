package com.uniSystemAPI.domain.estabelecimento;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uniSystemAPI.domain.Endereco;
import com.uniSystemAPI.domain.PrimeiroAcesso.DadosCadastroPrimeiroAcesso;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estabelecimento")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
// #region
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Estabelecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nomeFantasia;
    private String cpfCnpj;
    private String logoMarca;
    // @ManyToOne(cascade = CascadeType.PERSIST)
    // @JsonIgnore
    // Usuario usuarioAdmin;
    private String idUsuarioAdmin;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore
    Endereco endereco;

    public Estabelecimento(DadosCadastroEstabelecimento cadastroEstabelecimento) {
        this.nomeFantasia = cadastroEstabelecimento.nomeFantasia();
        this.cpfCnpj = cadastroEstabelecimento.cpfCnpj();
        this.endereco = new Endereco(cadastroEstabelecimento.endereco());
        this.idUsuarioAdmin = cadastroEstabelecimento.idUsuarioAdmin();
        this.logoMarca = cadastroEstabelecimento.nomeLogoMarca();
    }

    public Estabelecimento(DadosCadastroPrimeiroAcesso cadastroPrimeiroAcesso) {
        this.nomeFantasia = cadastroPrimeiroAcesso.estabNomeFantasia();
        this.cpfCnpj = cadastroPrimeiroAcesso.estabCpfCnpj();
        this.endereco = new Endereco(cadastroPrimeiroAcesso.estabEndereco());
        this.idUsuarioAdmin = cadastroPrimeiroAcesso.estabIdUsuarioAdmin();
        this.logoMarca = cadastroPrimeiroAcesso.estabNomeLogoMarca();
    }

    public Estabelecimento(String idEstabalecimento) {
        this.id = idEstabalecimento;
    }
}
