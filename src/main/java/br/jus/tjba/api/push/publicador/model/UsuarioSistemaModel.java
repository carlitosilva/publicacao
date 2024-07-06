package br.jus.tjba.api.push.publicador.model;

import br.jus.tjba.api.push.publicador.domain.entities.MensagemPendente;
import br.jus.tjba.api.push.publicador.domain.entities.UsuarioSistema;

public record UsuarioSistemaModel(Long id, String login, String chaveAcesso) {


    public UsuarioSistemaModel(UsuarioSistema usuarioSistema) {
        this(usuarioSistema.getId(),
                usuarioSistema.getLogin(),
                usuarioSistema.getChaveAcesso());
    }
}
