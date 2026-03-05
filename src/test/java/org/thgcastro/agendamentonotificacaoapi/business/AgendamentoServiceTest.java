package org.thgcastro.agendamentonotificacaoapi.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thgcastro.agendamentonotificacaoapi.business.mapper.IAgendamentoMapper;
import org.thgcastro.agendamentonotificacaoapi.controller.dto.in.AgendamentoRecord;
import org.thgcastro.agendamentonotificacaoapi.controller.dto.out.AgendamentoRecordOut;
import org.thgcastro.agendamentonotificacaoapi.infrastructure.entities.Agendamento;
import org.thgcastro.agendamentonotificacaoapi.infrastructure.enums.StatusNotificacaoEnum;
import org.thgcastro.agendamentonotificacaoapi.infrastructure.repositories.AgendamentoRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceTest {

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private IAgendamentoMapper iAgendamentoMapper;
    private Agendamento agendamentoEntity;

    private AgendamentoRecord agendamentoRecord;
    private AgendamentoRecordOut agendamentoRecordOut;

    @BeforeEach
    void setUp(){

        agendamentoEntity = new Agendamento(1L, "email@email.com", "123456789", LocalDateTime.of(2000, 01, 01, 00,00,00),
                LocalDateTime.now(),null, "Olá, mundo", StatusNotificacaoEnum.AGENDADO);

        agendamentoRecord = new AgendamentoRecord("email@email.com", "123456789", "Olá, mundo", LocalDateTime.of(2000, 01, 01, 00,00,00));

        agendamentoRecordOut = new AgendamentoRecordOut(1L, "email@email.com", "123456789", "Olá, mundo", LocalDateTime.of(2000, 01, 01, 00,00,00),
                StatusNotificacaoEnum.AGENDADO);
    }

    @Test
    void deveGravarAgendamentoComSucesso(){
        when(iAgendamentoMapper.paraEntity(agendamentoRecord)).thenReturn(agendamentoEntity);
        when(agendamentoRepository.save(agendamentoEntity)).thenReturn(agendamentoEntity);
        when(iAgendamentoMapper.paraOut(agendamentoEntity)).thenReturn(agendamentoRecordOut);

        AgendamentoRecordOut out = agendamentoService.gravarAgendamento(agendamentoRecord);

        verify(iAgendamentoMapper, times(1)).paraEntity(agendamentoRecord);
        verify(agendamentoRepository, times(1)).save(agendamentoEntity);
        verify(iAgendamentoMapper, times(1)).paraOut(agendamentoEntity);
        assertThat(out).usingRecursiveComparison().isEqualTo(agendamentoRecordOut);
    }

    @Test
    void deveBuscarAgendamentosPorIdComSucesso(){
        when(agendamentoRepository.findById(agendamentoRecordOut.id())).thenReturn(Optional.of(agendamentoEntity));
        when(iAgendamentoMapper.paraOut(agendamentoEntity)).thenReturn(agendamentoRecordOut);

        AgendamentoRecordOut out = agendamentoService.buscarAgendamentosPorId(agendamentoRecordOut.id());

        verify(agendamentoRepository, times(1)).findById(agendamentoRecordOut.id());
        verify(iAgendamentoMapper, times(1)).paraOut(agendamentoEntity);

        assertThat(out).usingRecursiveComparison().isEqualTo(agendamentoRecordOut);
    }

    @Test
    void deveCancelarAgendamentosComSucesso(){
        when(agendamentoRepository.findById(agendamentoRecordOut.id())).thenReturn(Optional.of(agendamentoEntity));
        when(iAgendamentoMapper.paraEntityCancelamento(agendamentoEntity)).thenReturn(agendamentoEntity);
        when(agendamentoRepository.save(agendamentoEntity)).thenReturn(agendamentoEntity);

        agendamentoService.cancelarAgendamento(agendamentoRecordOut.id());

        verify(agendamentoRepository, times(1)).findById(agendamentoRecordOut.id());
        verify(iAgendamentoMapper, times(1)).paraEntityCancelamento(agendamentoEntity);
        verify(agendamentoRepository, times(1)).save(agendamentoEntity);
    }
}
