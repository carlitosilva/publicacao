package br.jus.tjba.api.push.publicador.domain.services;

import br.jus.tjba.api.push.publicador.http.UsuarioClient;
import br.jus.tjba.api.push.publicador.model.ProcessoListagemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicadorService {

    @Autowired
    private UsuarioClient client;

    public List<ProcessoListagemModel> obterPublicavies() {

        return client.obterPublicavies().getBody();
    }

}
