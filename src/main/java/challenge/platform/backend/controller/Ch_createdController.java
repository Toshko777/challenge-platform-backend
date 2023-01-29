package challenge.platform.backend.controller;

import challenge.platform.backend.payload.Ch_createdDto;
import challenge.platform.backend.payload.Ch_createdResponse;
import challenge.platform.backend.service.Ch_createdService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Ch_createdController {

    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";

    // using interface -> because of the loose coupling
    private final Ch_createdService ch_createdService;

    @Autowired
    public Ch_createdController(Ch_createdService ch_createdService) {
        this.ch_createdService = ch_createdService;
    }

    @GetMapping(value = "/ch_created")
    public Ch_createdResponse getAllCh_createds(
            @RequestParam(value = "re", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return ch_createdService.getAllCh_createds(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping(value = "/ch_created/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ch_createdDto> getCh_createdById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(ch_createdService.getCh_createdById(id));
    }


   


    @PostMapping(value = "/ch_created", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ch_createdDto> createCh_created(@Valid @RequestBody Ch_createdDto dto) {
        return new ResponseEntity<>(ch_createdService.createCh_created(dto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/ch_created/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ch_createdDto> updateCh_created(@PathVariable(name = "id") long id,
                                              @Valid @RequestBody Ch_createdDto bookDto) {
        return new ResponseEntity<>(ch_createdService.updateCh_created(id, bookDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/ch_created/{id}")
    public ResponseEntity<String> deleteCh_created(@PathVariable(name = "id") Long id) {
        ch_createdService.deleteCh_createdById(id);
        return ResponseEntity.ok("Book entity deleted successfully");
    }
}