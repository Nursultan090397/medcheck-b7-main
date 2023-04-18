package com.example.medcheckb7.api;

import com.example.medcheckb7.db.entities.User;
import com.example.medcheckb7.db.entities.service.AdminAndUserGetAllExpert;
import com.example.medcheckb7.db.entities.service.ExpertService;
import com.example.medcheckb7.dto.request.ExpertRequest;
import com.example.medcheckb7.dto.request.ExpertRequests;
import com.example.medcheckb7.dto.response.*;
import com.example.medcheckb7.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/expert")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Expert Api")
public class ExpertController {

    private final ExpertService expertService;


    @Operation(summary = "Save experts", description = "You can add an expert here")
    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ExpertResponseForAddExpert saveExpert( @RequestBody @Valid ExpertRequest expertRequest) throws IOException {
        return expertService.addExperts(expertRequest);
    }

    @Operation(summary = "Get expert by id for Admin", description = "you can find an expert here by id")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ExpertResponseForAddExpert getExpertById(@PathVariable Long id) throws NotFoundException {
        return expertService.getExpertById(id);
    }

    @Operation(summary = "update an expert", description = "you can update an expert here")
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public ExpertResponseForUser updateExpert(@PathVariable Long id, @RequestBody @Valid ExpertRequests expertRequest) throws NotFoundException {
        return expertService.updateExpert(id, expertRequest);
    }

    @Operation(summary = "delete expert", description = "you can delete an expert here by id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteExpert(@PathVariable Long id) throws NotFoundException {
        return expertService.deleteExpert(id);
    }

    @Operation(summary = "Get all Experts for Admin and for User ", description = "you can get all experts for User and Admin here!" +
            "You must input size(how services you want to see) when you User, if you're the Admin you can skip they params" +
            "param 'text' for Search Experts!")
    @GetMapping("/getAllAndSearch")
    public List<? extends AdminAndUserGetAllExpert> markerGetAll(Authentication authentication,
                                                                 @RequestParam(name = "text", required = false) String text,
                                                                 @RequestParam(name = "size", defaultValue = "3") Integer size) {
        return expertService.getAllExpert((User) authentication.getPrincipal(), text, size);
    }

    @Operation(summary = "edit the Expert status", description = "You must enter expert's id which you want change status," +
            "when you used this method: if before status was false,now it be true, else be false!")
    @PutMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ExpertResponseForAdmin checkExpert(@RequestParam Long id) {
        return expertService.checkStatus(id);
    }
}