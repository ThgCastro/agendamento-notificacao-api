package org.thgcastro.agendamentonotificacaoapi.controller;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.thgcastro.agendamentonotificacaoapi.business.AgendamentoService;
import org.thgcastro.agendamentonotificacaoapi.controller.dto.in.AgendamentoRecord;
import org.thgcastro.agendamentonotificacaoapi.controller.dto.out.AgendamentoRecordOut;
import org.thgcastro.agendamentonotificacaoapi.infrastructure.enums.StatusNotificacaoEnum;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AgendamentoControllerTest {

    @InjectMocks
    AgendamentoController agendamentoController;

    @Mock
    AgendamentoService agendamentoService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private AgendamentoRecord agendamentoRecord;
    private AgendamentoRecordOut agendamentoRecordOut;

    @BeforeEach
    void setUp(){

        mockMvc = MockMvcBuilders.standaloneSetup(agendamentoController).build();

        objectMapper.registerModule(new JavaTimeModule());

        agendamentoRecord = new AgendamentoRecord("email@email.com", "123456789", "Olá, mundo", LocalDateTime.of(2000, 01, 01, 00,00,00));

        agendamentoRecordOut = new AgendamentoRecordOut(1L, "email@email.com", "123456789", "Olá, mundo", LocalDateTime.of(2000, 01, 01, 00,00,00),
                StatusNotificacaoEnum.AGENDADO);
    }

    @Test
    void deveGravarAgendamentoComSucesso() throws Exception {
        when(agendamentoService.gravarAgendamento(agendamentoRecord)).thenReturn(agendamentoRecordOut);

        mockMvc.perform(post("/agendamento").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(agendamentoRecord)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.emailDestinatario").value("email@email.com"))
                .andExpect(jsonPath("$.telefoneDestinatario").value("123456789"))
                .andExpect(jsonPath("$.mensagem").value("Olá, mundo"))
                .andExpect(jsonPath("$.dataHoraEnvio").value("01-01-2000 00:00:00"))
                .andExpect(jsonPath("$.statusNotificacao").value("AGENDADO"));
        verify(agendamentoService, times(1)).gravarAgendamento(agendamentoRecord);
    }

    @Test
    void deveBuscarAgendamentoPorIdComSucesso() throws Exception {
        when(agendamentoService.buscarAgendamentosPorId(agendamentoRecordOut.id())).thenReturn(agendamentoRecordOut);

        mockMvc.perform(get("/agendamento/1", agendamentoRecordOut.id()))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.id").value(agendamentoRecordOut.id())));

        verify(agendamentoService, times(1)).buscarAgendamentosPorId(agendamentoRecordOut.id());
    }

    @Test
    void deveCancelarAgendamentoComSucesso() throws Exception {
        doNothing().when(agendamentoService).cancelarAgendamento(agendamentoRecordOut.id());

        mockMvc.perform(delete("/agendamento/1", agendamentoRecordOut.id()))
                .andExpect(status().isAccepted());

        verify(agendamentoService,times(1)).cancelarAgendamento(agendamentoRecordOut.id());
    }

}
