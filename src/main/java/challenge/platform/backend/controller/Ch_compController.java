package challenge.platform.backend.controller;

import challenge.platform.backend.payload.Ch_compDto;
import challenge.platform.backend.payload.Ch_compResponse;
import challenge.platform.backend.service.Ch_compService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Ch_compController {

    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";

    // using interface -> because of the loose coupling
    private final Ch_compService ch_compService;

    @Autowired
    public Ch_compController(Ch_compService ch_compService) {
        this.ch_compService = ch_compService;
    }

    @GetMapping(value = "/ch_comp")
    public Ch_compResponse getAllCh_comps(
            @RequestParam(value = "re", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return ch_compService.getAllCh_comps(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping(value = "/ch_comp/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ch_compDto> getCh_compById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(ch_compService.getCh_compById(id));
    }


   


    @PostMapping(value = "/ch_comp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ch_compDto> createCh_comp(@Valid @RequestBody Ch_compDto dto) {
        return new ResponseEntity<>(ch_compService.createCh_comp(dto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/ch_comp/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ch_compDto> updateCh_comp(@PathVariable(name = "id") long id,
                                              @Valid @RequestBody Ch_compDto bookDto) {
        return new ResponseEntity<>(ch_compService.updateCh_comp(id, bookDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/ch_comp/{id}")
    public ResponseEntity<String> deleteCh_comp(@PathVariable(name = "id") Long id) {
        ch_compService.deleteCh_compById(id);
        return ResponseEntity.ok("Book entity deleted successfully");
    }
}