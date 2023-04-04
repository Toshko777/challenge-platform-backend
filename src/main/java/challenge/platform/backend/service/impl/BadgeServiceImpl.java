package challenge.platform.backend.service.impl;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import challenge.platform.backend.entity.Badge;
import challenge.platform.backend.exception.ResourceNotFoundException;
import challenge.platform.backend.payload.BadgeDto;
import challenge.platform.backend.payload.BadgeResponse;
import challenge.platform.backend.repository.BadgeRepository;
import challenge.platform.backend.service.BadgeService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@Service
public class BadgeServiceImpl implements BadgeService {
    private ModelMapper modelMapper;
    private BadgeRepository badgeRepository;
   
   

public BadgeServiceImpl(
            BadgeRepository badgeRepository,
            ModelMapper modelMapper
           
){
    this.badgeRepository = badgeRepository;
    this.modelMapper = modelMapper;
    
}

    private BadgeDto mapToDto(Badge badge) {
        return modelMapper.map(badge, BadgeDto.class);
    }

    
    private Badge mapToEntity(BadgeDto badgeDto) {
        return modelMapper.map(badgeDto, Badge.class);
    }



    @Override
    public BadgeDto createBadge(BadgeDto badgeDto) {
       
        Badge badgeToSave = createBadgeToSave(badgeDto);
        Badge createdBadge = badgeRepository.save(badgeToSave);

        log.info("Badge with name {} and id {} was created!", createdBadge.getName(), createdBadge.getId());
        return mapToDto(createdBadge);
    }
    private Badge createBadgeToSave(BadgeDto badgeDto) {
        Badge mappedBadge = mapToEntity(badgeDto);      
        return mappedBadge;
    }
   

    @Override
    public BadgeResponse getAllBadges(int pageNo, int pageSize, String sortBy, String sortDir) {
        
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Badge> badges = badgeRepository.findAll(pageable);

       
        BadgeResponse badgeResponse = new BadgeResponse();

        badgeResponse.setContent(badges.getContent().stream().map(badge -> mapToDto(badge)).toList());
        badgeResponse.setPageNo(badges.getNumber());
        badgeResponse.setPageSize(badges.getSize());
        badgeResponse.setTotalElements(badges.getTotalElements());
        badgeResponse.setTotalPages(badges.getTotalPages());
        badgeResponse.setLast(badges.isLast());

        return badgeResponse;
    }

    @Override
    public BadgeDto getBadgeById(long id) {
        
        Badge badge = badgeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Badge", "id", id));
        return mapToDto(badge);
    }

    @Override
    public BadgeDto updateBadge(long id, BadgeDto badgeDto) {
        Badge found = badgeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Badge", "id", id));
        found.setName(badgeDto.getName());
        found.setDescription(badgeDto.getDescription());
        found.setCondition(badgeDto.getCondition());

        Badge savedBadge = badgeRepository.save(found);
        log.info("Badge with id: {} updated!", id);
        return mapToDto(savedBadge);
    }

    @Override
    public void deleteBadgeById(long id) {
        Badge found = badgeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Badge", "id", id));
        badgeRepository.delete(found);
        log.info("Badge with id{} was deleted!", id);
    }
    
    
}
