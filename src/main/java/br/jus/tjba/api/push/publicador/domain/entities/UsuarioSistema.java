package br.jus.tjba.api.push.publicador.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "usuario_sistema")
@Entity(name = "UsuarioSistema")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UsuarioSistema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String chaveAcesso;
/*    @OneToOne(mappedBy = "usuarioSistema", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private MensagemPendente mensagemPendente;*/
}

