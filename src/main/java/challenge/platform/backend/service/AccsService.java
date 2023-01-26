package challenge.platform.backend.service;

import challenge.platform.backend.payload.AccsDto;
import challenge.platform.backend.payload.AccsResponse;

public interface AccsService {

    AccsDto createAccs(AccsDto bookDto);

    AccsResponse getAllAccss(int pageNo, int pageSize, String sortBy, String sortDir);

    AccsDto getAccsById(long id);
    

    AccsDto updateAccs(long id, AccsDto accsDto);

    void deleteAccsById(long id);

}
