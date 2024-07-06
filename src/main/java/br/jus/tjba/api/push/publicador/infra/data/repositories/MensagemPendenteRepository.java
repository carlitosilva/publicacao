package br.jus.tjba.api.push.publicador.infra.data.repositories;

import br.jus.tjba.api.push.publicador.domain.entities.MensagemPendente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MensagemPendenteRepository  extends JpaRepository<MensagemPendente, Long> {
}