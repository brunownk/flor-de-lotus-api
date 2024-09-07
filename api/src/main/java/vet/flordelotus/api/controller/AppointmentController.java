package vet.flordelotus.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vet.flordelotus.api.domain.dto.appointment.AppointmentCancelDTO;
import vet.flordelotus.api.service.AppointmentSchedule;
import vet.flordelotus.api.domain.dto.appointment.AppointmentScheduleDTO;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {

    @Autowired
    private AppointmentSchedule appointmentSchedule;

    @PostMapping
    @Transactional
    public ResponseEntity schedule(@RequestBody @Valid AppointmentScheduleDTO data) {
        var dto = appointmentSchedule.schedule(data);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelAppointment(@RequestBody @Valid AppointmentCancelDTO data) {
        appointmentSchedule.cancel(data);
        return ResponseEntity.noContent().build();
    }

}

