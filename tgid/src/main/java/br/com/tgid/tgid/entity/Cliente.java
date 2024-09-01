package br.com.tgid.tgid.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    @NotBlank(message = "O campo sobrenome é obrigatório")
    private String sobrenome;

    @NotBlank(message = "O campo cpf é obrigatório")
    @CPF
    private String cpf;

    @NotBlank
    @Email(message = "O campo email é obrigatório")
    private String email;

    private String telefone;


}
