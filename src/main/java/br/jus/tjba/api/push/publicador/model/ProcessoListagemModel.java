package br.jus.tjba.api.push.publicador.model;

public record ProcessoListagemModel(Long id, String numeroProcesso, UsuarioListagemModel usuario,
                                    SistemaListagemModel sistema) {
}
