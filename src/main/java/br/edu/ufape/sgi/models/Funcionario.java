package br.edu.ufape.sgi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor

public abstract class Funcionario extends Perfil {
    private String siape;
}
