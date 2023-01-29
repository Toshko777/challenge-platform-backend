package challenge.platform.backend.service;

import challenge.platform.backend.payload.Ch_createdDto;
import challenge.platform.backend.payload.Ch_createdResponse;

public interface Ch_createdService {

    Ch_createdDto createCh_created(Ch_createdDto bookDto);

    Ch_createdResponse getAllCh_createds(int pageNo, int pageSize, String sortBy, String sortDir);

    Ch_createdDto getCh_createdById(long id);
    

    Ch_createdDto updateCh_created(long id, Ch_createdDto accsDto);

    void deleteCh_createdById(long id);

}