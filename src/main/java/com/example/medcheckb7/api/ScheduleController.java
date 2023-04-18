package com.example.medcheckb7.api;

import com.example.medcheckb7.db.entities.service.ScheduleService;
import com.example.medcheckb7.dto.request.ScheduleRequest;
import com.example.medcheckb7.dto.request.ScheduleUpdateRequest;
import com.example.medcheckb7.dto.response.ScheduleResponse;
import com.example.medcheckb7.dto.response.ScheduleResponseForSearch;
import com.example.medcheckb7.dto.response.SimpleResponse;
import com.example.medcheckb7.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Schedule API")
@PreAuthorize("hasAuthority('ADMIN')")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Operation(summary = "Add a new schedule", description = "you can add schedule for expert in clinicService here")
    @PostMapping("/")
    public ScheduleResponse addSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        return scheduleService.addSchedule(scheduleRequest);
    }

    @Operation(summary = "Schedule search and get all schedule", description = " using this method you can find schedules by export names or see all experts with their schedules")
    @GetMapping("/")
    public List<ScheduleResponseForSearch> getSchedule(@RequestParam(name = "expertFName", required = false) String expertFName) throws NotFoundException {
        return scheduleService.searchAndFetchAllSchedules(expertFName);
    }

    @Operation(summary = "Update schedule", description = "you can update schedule and date and time")
    @PutMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse updateSchedule(@RequestBody ScheduleUpdateRequest scheduleRequest) {
        return scheduleService.updateSchedule(scheduleRequest);
    }

    @Operation(summary = "to export to excel file")
    @PostMapping(
            value = "/export",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public HttpEntity<byte[]> exportExcel(@RequestParam(required = false) String expertFName) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/xlsx"));
        headers.set("content-disposition", "attachment; filename=\"schedules.xlsx\"");
        return new HttpEntity<>(scheduleService.patientExportExcel(expertFName), headers);
    }

    @Operation(summary = "delete selected dates", description = "You can delete selected dates")
    @DeleteMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteSelected(@RequestBody List<Long> ids) {
        return scheduleService.delete(ids);
    }
}




