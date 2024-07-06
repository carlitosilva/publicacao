package br.jus.tjba.api.push.publicador.http;

import br.jus.tjba.api.push.publicador.model.ProcessoListagemModel;
import br.jus.tjba.api.push.publicador.model.ProcessoParametroModel;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("usuario")
public interface UsuarioClient {

    @RequestMapping(method = RequestMethod.GET, value = "/usuarios/obter-lista-publicacao")
    ResponseEntity<List<ProcessoListagemModel>> obterPublicavies(@RequestParam("numeroProcesso") String numeroProcesso, @RequestParam("siglaSistema") String siglaSistema);
}
