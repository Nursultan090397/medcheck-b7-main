package com.example.medcheckb7.api;

import com.example.medcheckb7.db.entities.service.impl.ApplicationServiceImpl;
import com.example.medcheckb7.dto.request.ApplicationRequest;
import com.example.medcheckb7.dto.response.ApplicationResponse;
import com.example.medcheckb7.dto.response.ApplicationResponseListForAdmin;
import com.example.medcheckb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/application")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Application Api")
public class ApplicationController {

    private final ApplicationServiceImpl applicationService;

    @Operation(summary = "save", description = "You can add an application here")
    @PostMapping("/")
    @PreAuthorize("hasAuthority('USER')")
    public ApplicationResponse saveApplication(@RequestBody @Valid ApplicationRequest applicationRequest) {
        return applicationService.saveApplication(applicationRequest);
    }

    @Operation(summary = "get all", description = "You can get all application")
    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ApplicationResponse> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @Operation(summary = "search Application", description = "You can search application")
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApplicationResponseListForAdmin searchApplication(@RequestParam("text") String text) {
        return applicationService.searchApplications(text);
    }

    @Operation(summary = "delete selected Applications", description = "You can delete selected applications")
    @DeleteMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteSelectedApplications(@RequestBody List<Long> ids) {
        return applicationService.deleteSelectedApplications(ids);
    }

    @Operation(summary = "edit the Application status", description = "You must enter application's id which you want change status," +
            "when you used this method: if before status was false,now it be true, else be false!")
    @PutMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApplicationResponse checkApplication(@RequestParam Long id) {
        return applicationService.checkApplications(id);
    }
}
