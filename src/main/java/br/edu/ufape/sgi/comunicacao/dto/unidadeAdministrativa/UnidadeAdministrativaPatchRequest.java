package br.edu.ufape.sgi.comunicacao.dto.unidadeAdministrativa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public abstract class UnidadeAdministrativaPatchRequest {
    private String nome;

    private String codigo;
}