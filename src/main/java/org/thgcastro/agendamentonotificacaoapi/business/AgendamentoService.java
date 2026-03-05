package org.thgcastro.agendamentonotificacaoapi.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thgcastro.agendamentonotificacaoapi.business.mapper.IAgendamentoMapper;
import org.thgcastro.agendamentonotificacaoapi.controller.dto.in.AgendamentoRecord;
import org.thgcastro.agendamentonotificacaoapi.controller.dto.out.AgendamentoRecordOut;
import org.thgcastro.agendamentonotificacaoapi.infrastructure.repositories.AgendamentoRepository;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository repository;

    private final IAgendamentoMapper agendamentoMapper;

    public AgendamentoRecordOut gravarAgendamento(AgendamentoRecord agendamento){
        return agendamentoMapper.paraOut(repository.save(agendamentoMapper.paraEntity(agendamento)));
    }


}
