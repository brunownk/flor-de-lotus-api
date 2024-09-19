package vet.flordelotus.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vet.flordelotus.api.domain.dto.appointment.AppointmentCancelDTO;
import vet.flordelotus.api.domain.dto.appointment.AppointmentScheduleDTO;
import vet.flordelotus.api.domain.dto.appointment.AppointmentDetailDTO;
import vet.flordelotus.api.domain.entity.Appointment;
import vet.flordelotus.api.domain.repository.AppointmentRepository;
import vet.flordelotus.api.service.AppointmentSchedule;

@RestController
@RequestMapping("appointment")
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {

    @Autowired
    private AppointmentSchedule appointmentSchedule;

    @Autowired
    private AppointmentRepository appointmentRepository;

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

    @GetMapping
    public ResponseEntity<Page<AppointmentDetailDTO>> listAppointments(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "false") boolean withCanceled) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<Appointment> appointments;

        if (withCanceled) {
            appointments = appointmentRepository.findAll(pageable); // Retrieves all appointments, including canceled ones
        } else {
            appointments = appointmentRepository.findByCancelAppointmentReasonIsNull(pageable); // Retrieves only active appointments
        }

        Page<AppointmentDetailDTO> appointmentDTOs = appointments.map(AppointmentDetailDTO::new);
        return ResponseEntity.ok(appointmentDTOs);
    }
}
