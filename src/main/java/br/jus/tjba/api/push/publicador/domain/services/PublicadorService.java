package br.jus.tjba.api.push.publicador.domain.services;

import br.jus.tjba.api.push.publicador.domain.entities.MensagemPendente;
import br.jus.tjba.api.push.publicador.domain.entities.UsuarioSistema;
import br.jus.tjba.api.push.publicador.http.UsuarioClient;
import br.jus.tjba.api.push.publicador.infra.data.repositories.MensagemPendenteRepository;
import br.jus.tjba.api.push.publicador.infra.data.repositories.UsuarioSistemaRepository;
import br.jus.tjba.api.push.publicador.model.MensagemPendenteModel;
import br.jus.tjba.api.push.publicador.model.ProcessoListagemModel;
import br.jus.tjba.api.push.publicador.model.ProcessoParametroModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicadorService {

    @Autowired
    private UsuarioClient client;

    @Autowired
    private MensagemPendenteRepository mensagemPendenteRepository;

    @Autowired
    private UsuarioSistemaRepository usuarioSistemaRepository;

    public List<ProcessoListagemModel> obterPublicavies(String numeroProcesso, String siglaSistema) {

        return client.obterPublicavies(numeroProcesso, siglaSistema).getBody();
    }

    public MensagemPendenteModel cadastrar(ProcessoParametroModel processoParametroModel) {

        var usuarioSistema = usuarioSistemaRepository.findByLogin(processoParametroModel.siglaSistema());

        var mensagem = new MensagemPendente(null,
                processoParametroModel.numeroProcesso(),
                processoParametroModel.siglaSistema(),
                usuarioSistema);

        mensagemPendenteRepository.save(mensagem);

        return new MensagemPendenteModel(mensagem);

    }

}
