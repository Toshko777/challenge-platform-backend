package challenge.platform.backend.service;

import challenge.platform.backend.payload.Ch_compDto;
import challenge.platform.backend.payload.Ch_compResponse;

public interface Ch_compService {

    Ch_compDto createCh_comp(Ch_compDto bookDto);

    Ch_compResponse getAllCh_comps(int pageNo, int pageSize, String sortBy, String sortDir);

    Ch_compDto getCh_compById(long id);
    

    Ch_compDto updateCh_comp(long id, Ch_compDto accsDto);

    void deleteCh_compById(long id);

}