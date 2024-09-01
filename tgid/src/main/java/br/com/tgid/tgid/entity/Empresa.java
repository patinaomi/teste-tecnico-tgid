package br.com.tgid.tgid.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message="O campo nome é obrigatório")
    private String nome;

    @CNPJ
    private String cnpj;

    @Email(message="E-mail inválido")
    @NotBlank(message="O campo email é obrigatório")
    private String email;

    private String telefone;
}
