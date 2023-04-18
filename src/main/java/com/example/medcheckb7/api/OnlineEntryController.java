package com.example.medcheckb7.api;

import com.example.medcheckb7.db.entities.User;
import com.example.medcheckb7.db.entities.service.OnlineEntryService;
import com.example.medcheckb7.dto.request.OnlineEntryRequest;
import com.example.medcheckb7.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/onlineEntry")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Online Entry Api")
public class OnlineEntryController {

    private final OnlineEntryService onlineEntryService;

    @Operation(summary = "Get all clinic services", description = "Get a list of all clinic services")
    @GetMapping("/services")
    public List<ClinicServiceResponse> getAllClinicServices() {
        return onlineEntryService.getAllClinicService();
    }

    @Operation(summary = "Get all experts clinic serviceId", description = "Get a list of all experts for a specific clinic service")
    @GetMapping("/experts")
    public List<ExpertResponseOnlineEntry> getAllExpertsByClinicServiceId(@RequestParam Long clinicServiceId) {
        return onlineEntryService.getAllExpertByClinicId(clinicServiceId);
    }

    @Operation(summary = "Get all timeByDate", description = "view all times in one date")
    @GetMapping("/times")
    public List<ScheduleAndTimeResponse> getAllTime(@RequestParam Long expertId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        return onlineEntryService.getTimesToEntry(expertId, localDate);
    }

    @Operation(summary = "Add online entry", description = "select service first then expert")
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/save")
    public OnlineEntryResponse saveOnlineEntry(@RequestBody OnlineEntryRequest onlineEntryRequest) {
        return onlineEntryService.saveOnlineEntry(onlineEntryRequest);
    }

    @Operation(summary = "cancel online entry", description = "cancel online entry")
    @PostMapping("/cancel")
    public SimpleResponse cancelOnlineEntry(@RequestParam Long onlineEntryId) {
        return onlineEntryService.cancelEntry(onlineEntryId);
    }

    @Operation(summary = "Get all", description = "all online entries come out here and those that are deleted do not come out")
    @GetMapping("/Appointments")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<AppointmentsAdminResponse> getAll(@RequestParam(name = "text", required = false) String text) {
        return onlineEntryService.getAll(text);
    }


    @Operation(summary = "delete appointments ", description = "you can delete one or several or all of them")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteAppointments(@RequestBody List<Long> ids) {
        return onlineEntryService.deleteAppointments(ids);
    }

    @Operation(summary = "get all Appointments by User", description = "You can see all appointments here!")
    @GetMapping("/myAppointments")
    @PreAuthorize("hasAuthority('USER')")
    public List<OnlineEntryResponseForUser> getAllOnlineEntriesByUserId(Authentication authentication) {
        return onlineEntryService.getOnlineEntriesByUserId((User) authentication.getPrincipal());
    }

    @Operation(summary = "get Appointment with User", description = "Enter the appointment's id which you want to see")
    @GetMapping("/")
    @PreAuthorize("hasAuthority('USER')")
    public OnlineEntryResponses getOnlineEntryWithUser(Authentication authentication, @RequestParam("id") Long id) {
        return onlineEntryService.getOnlineEntryWithUser((User) authentication.getPrincipal(), id);
    }

    @Operation(summary = "Clear Appointments", description = "You can clear Appointments here, Warning! You can't restore data again!")
    @DeleteMapping("/clear")
    @PreAuthorize("hasAuthority('USER')")
    public SimpleResponse clear(Authentication authentication) {
        return onlineEntryService.deleteAll((User) authentication.getPrincipal());
    }
}