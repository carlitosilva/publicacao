package br.jus.tjba.api.push.publicador.domain.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Table(name = "mensagem_pendente")
@Entity(name = "MesagemPendente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class MensagemPendente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroProcesso;
    private String mensagem;
/*    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_sistema", referencedColumnName = "id")
    private UsuarioSistema usuarioSistema;*/
/*    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario_sistema")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private UsuarioSistema usuarioSistema;*/

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_usuario_sistema", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UsuarioSistema usuarioSistema;

}

