package challenge.platform.backend.service;

import challenge.platform.backend.payload.BadgeDto;
import challenge.platform.backend.payload.BadgeResponse;

public interface BadgeService {
    
    BadgeDto createBadge(BadgeDto bookDto);

    BadgeResponse getAllBadges(int pageNo, int pageSize, String sortBy, String sortDir);

    BadgeDto getBadgeById(long id);

    BadgeDto updateBadge(long id, BadgeDto badgeDto);

    void deleteBadgeById(long id);
}