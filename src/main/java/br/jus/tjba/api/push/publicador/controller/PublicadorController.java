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

@RestController
@RequestMapping("/sinalizar")
//@SecurityRequirement(name = "bearer-key")
public class PublicadorController {

    @Autowired
    private PublicadorService service;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping
    @Transactional
    public ResponseEntity publicar() {

        var publicaveis = service.obterPublicavies();


        for (var i = 0; i < publicaveis.size(); i++){
            var processo = publicaveis.get(i);
/*            var message = new Message(
                    ("Processo Movimentado " +
                    processo.id() + " " +
                    processo.numeroProcesso() + " " +
                    processo.usuario().login() + " " +
                    processo.sistema().sigla()
                    ).getBytes());*/

            rabbitTemplate.convertAndSend("processo.movimentado", processo);
        }
/*        var usuarioDetalheModel = usuarioService.cadastrar(usuarioModel);

        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuarioDetalheModel.id()).toUri();

        return ResponseEntity.created(uri).body(usuarioDetalheModel);*/
        return ResponseEntity.noContent().build();
    }
}
