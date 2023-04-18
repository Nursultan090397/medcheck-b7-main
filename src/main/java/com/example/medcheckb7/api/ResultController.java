package com.example.medcheckb7.api;

import com.example.medcheckb7.db.entities.service.ResultService;
import com.example.medcheckb7.dto.request.ResultRequest;
import com.example.medcheckb7.dto.response.ResultResponseForUser;
import com.example.medcheckb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Result Api")
public class ResultController {

    private final ResultService resultService;

    @Operation(summary = "Retrieve results by order number", description = "Retrieves a list of results for a given order number")
    @GetMapping("/{orderNumber}")
    @PreAuthorize("hasAuthority('USER')")
    public List<ResultResponseForUser> getResultsByOrderNumber(@PathVariable String orderNumber) {
        return resultService.findResultsByOrderNumber(orderNumber);
    }


    @Operation(summary = "save Results", description = "you can add a result here, enter the userId for we to sent result's generated code to user's email")
    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse addResult(@RequestBody @Valid ResultRequest resultRequest) {
        return resultService.addResult(resultRequest);
    }
}
