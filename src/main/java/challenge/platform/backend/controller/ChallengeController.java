package challenge.platform.backend.controller;


import challenge.platform.backend.payload.ChallengeDto;
import challenge.platform.backend.payload.ChallengeResponse;
import challenge.platform.backend.service.ChallengeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@SecurityRequirement(
        name = "Bearer Authentication"
)
public class ChallengeController {
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";


    private final ChallengeService challengeService;

    @Autowired
    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MODERATOR')")
    @GetMapping(value = "/all-challenges")
    public ChallengeResponse getAllChallenges(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return challengeService.getAllChallenges(pageNo, pageSize, sortBy, sortDir);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(value = "/challenge/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChallengeDto> getChallengeById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(challengeService.getChallengeById(id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping(value = "/challenge", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChallengeDto> createChallenge(@Valid @RequestBody ChallengeDto dto) {
        return new ResponseEntity<>(challengeService.createChallenge(dto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PutMapping(value = "/challenge/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChallengeDto> updateChallenge(@PathVariable(name = "id") long id,
                                                        @Valid @RequestBody ChallengeDto challengeDto) {
        return new ResponseEntity<>(challengeService.updateChallenge(id, challengeDto), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/challenge/{id}")
    public ResponseEntity<String> deleteChallenge(@PathVariable(name = "id") Long id) {
        challengeService.deleteChallengeById(id);
        return ResponseEntity.ok("Challenge entity deleted successfully");
    }
}
