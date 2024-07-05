package br.jus.tjba.api.push.publicador.infra.data.repositories;

import br.jus.tjba.api.push.publicador.domain.entities.UsuarioSistema;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioSistemaRepository  extends JpaRepository<UsuarioSistema, Long> {

    UsuarioSistema findByLogin(String login);
}
