package br.com.tgid.entity;

import br.com.tgid.enums.TipoTransacao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "transacao")
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TipoTransacao tipo;
    private LocalDateTime data;
    private Double valor;

}
