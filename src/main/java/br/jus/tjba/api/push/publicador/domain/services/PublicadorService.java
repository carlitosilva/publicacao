package br.jus.tjba.api.push.publicador.domain.services;

import br.jus.tjba.api.push.publicador.domain.entities.MensagemPendente;
import br.jus.tjba.api.push.publicador.domain.entities.UsuarioSistema;
import br.jus.tjba.api.push.publicador.http.UsuarioClient;
import br.jus.tjba.api.push.publicador.infra.data.repositories.MensagemPendenteRepository;
import br.jus.tjba.api.push.publicador.infra.data.repositories.UsuarioSistemaRepository;
import br.jus.tjba.api.push.publicador.model.MensagemPendenteModel;
import br.jus.tjba.api.push.publicador.model.ProcessoListagemModel;
import br.jus.tjba.api.push.publicador.model.ProcessoParametroModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicadorService {

    @Autowired
    private UsuarioClient client;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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

    public List<ProcessoListagemModel> enviarMensagem(ProcessoParametroModel processoParametroModel) {
        String numeroProcesso = processoParametroModel.numeroProcesso();
        String siglaSistema = processoParametroModel.siglaSistema();
        var publicaveis = this.obterPublicavies(numeroProcesso, siglaSistema);

        this.publicarNaFila(publicaveis);
        return publicaveis;
    }

    private void publicarNaFila(List<ProcessoListagemModel> publicaveis) {
        publicaveis.forEach(p -> rabbitTemplate.convertAndSend("processo.movimentado", p));
    }

    public List<MensagemPendenteModel> obterMensagensPendentes() {
        var mensagensPendentesList = mensagemPendenteRepository.findAll();
        return mensagensPendentesList.stream().
                map(m ->
                        new MensagemPendenteModel(m)).collect(Collectors.toList());
    }

    public void enviarMensagensPendentes() {
        var mensagensPendendetesList = this.obterMensagensPendentes();
        for (int i = 0; i < mensagensPendendetesList.size(); i++) {
            var mensagemPendente = mensagensPendendetesList.get(i);
            enviarMensagem(new ProcessoParametroModel(mensagemPendente.numeroProcesso(),
                    mensagemPendente.usuarioSistemaModel().login()));
            mensagemPendenteRepository.delete(new MensagemPendente(mensagemPendente.id(), null, null, null));
        }
/*        mensagensPendendetesList.stream().
                forEach(m -> enviarMensagem(new ProcessoParametroModel(m.numeroProcesso(), m.usuarioSistemaModel().login())));*/
        //var publicaveis = service.enviarMensagem(processoParametroModel);
    }

}
