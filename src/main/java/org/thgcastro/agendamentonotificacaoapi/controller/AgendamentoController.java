package org.thgcastro.agendamentonotificacaoapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thgcastro.agendamentonotificacaoapi.business.AgendamentoService;
import org.thgcastro.agendamentonotificacaoapi.controller.dto.in.AgendamentoRecord;
import org.thgcastro.agendamentonotificacaoapi.controller.dto.out.AgendamentoRecordOut;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agendamento")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoRecordOut> gravarAgendamentos(@RequestBody AgendamentoRecord agendamento){
        return ResponseEntity.ok(agendamentoService.gravarAgendamento(agendamento));
    }


}
