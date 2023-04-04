package challenge.platform.backend.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;
import challenge.platform.backend.payload.BadgeDto;
import challenge.platform.backend.payload.BadgeResponse;
import challenge.platform.backend.service.BadgeService;



@RestController
@RequestMapping("/api")
public class BadgeController {
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";

    
    private final BadgeService badgeService;

    @Autowired
    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @GetMapping(value = "/badges")
    public BadgeResponse getAllBadges(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return badgeService.getAllBadges(pageNo, pageSize, sortBy, sortDir);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(value = "/badge/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BadgeDto> getBadgeById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(badgeService.getBadgeById(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/badge", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BadgeDto> createBadge(@Valid @RequestBody BadgeDto dto) {
        return new ResponseEntity<>(badgeService.createBadge(dto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/badge/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BadgeDto> updateBadge(@PathVariable(name = "id") long id,
                                                 @Valid @RequestBody BadgeDto badgeDto) {
        return new ResponseEntity<>(badgeService.updateBadge(id, badgeDto), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/badge/{id}")
    public ResponseEntity<String> deleteBadge(@PathVariable(name = "id") Long id) {
        badgeService.deleteBadgeById(id);
        return ResponseEntity.ok("Badge entity deleted successfully");
    }
}
