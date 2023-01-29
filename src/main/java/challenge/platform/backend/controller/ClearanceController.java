package challenge.platform.backend.controller;

import challenge.platform.backend.payload.ClearanceDto;
import challenge.platform.backend.payload.ClearanceResponse;
import challenge.platform.backend.service.ClearanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ClearanceController {

    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";

    // using interface -> because of the loose coupling
    private final ClearanceService clearanceService;

    @Autowired
    public ClearanceController(ClearanceService clearanceService) {
        this.clearanceService = clearanceService;
    }

    @GetMapping(value = "/clearance")
    public ClearanceResponse getAllClearances(
            @RequestParam(value = "re", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return clearanceService.getAllClearances(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping(value = "/clearance/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClearanceDto> getClearanceById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(clearanceService.getClearanceById(id));
    }


   


    

    
}