package br.jus.tjba.api.push.publicador.controller;

import br.jus.tjba.api.push.publicador.domain.services.PublicadorService;
import br.jus.tjba.api.push.publicador.model.ProcessoParametroModel;
import br.jus.tjba.api.push.publicador.model.SistemaListagemModel;
import br.jus.tjba.api.push.publicador.model.UsuarioListagemModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/sinalizar")
//@SecurityRequirement(name = "bearer-key")
public class PublicadorController {

    @Autowired
    private PublicadorService service;

    @PostMapping
    @Transactional
    @CircuitBreaker(name = "enviarMensagem", fallbackMethod = "cadastrar")
    public ResponseEntity publicar(@RequestBody @Valid ProcessoParametroModel processoParametroModel, UriComponentsBuilder uriBuilder) {
        /*String numeroProcesso = processoParametroModel.numeroProcesso();
        String siglaSistema = processoParametroModel.siglaSistema();
        var publicaveis = service.obterPublicavies(numeroProcesso, siglaSistema);

/**       for (var i = 0; i < publicaveis.get().size(); i++) {
            var processo = publicaveis.get(i);
            rabbitTemplate.convertAndSend("processo.movimentado", processo);
        }** /
        publicaveis.forEach(p -> rabbitTemplate.convertAndSend("processo.movimentado", p));*/
        var publicaveis = service.enviarMensagem(processoParametroModel);
        return ResponseEntity.ok(publicaveis);
    }

    public ResponseEntity cadastrar(ProcessoParametroModel processoParametroModel, UriComponentsBuilder uriBuilder, Exception e){
        var  mensagemPendenteModel = service.cadastrar(processoParametroModel);

        var uri = uriBuilder.path("/publicador/{id}").buildAndExpand(mensagemPendenteModel.id()).toUri();

        return ResponseEntity.created(uri).body(mensagemPendenteModel);

    }


}
