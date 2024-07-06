package br.jus.tjba.api.push.publicador.model;

import br.jus.tjba.api.push.publicador.domain.entities.MensagemPendente;

public record MensagemPendenteModel(Long id, String numeroProcesso, String mensagem,
                                    UsuarioSistemaModel usuarioSistemaModel) {
    public MensagemPendenteModel(MensagemPendente mensagemPendente) {
        this(mensagemPendente.getId(),
                mensagemPendente.getNumeroProcesso(),
                mensagemPendente.getMensagem(),
                new UsuarioSistemaModel(mensagemPendente.getUsuarioSistema()));
    }
}
