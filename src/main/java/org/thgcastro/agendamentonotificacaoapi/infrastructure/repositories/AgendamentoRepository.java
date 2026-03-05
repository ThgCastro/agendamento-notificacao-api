package org.thgcastro.agendamentonotificacaoapi.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thgcastro.agendamentonotificacaoapi.infrastructure.entities.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
}
