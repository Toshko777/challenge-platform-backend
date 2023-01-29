package challenge.platform.backend.service;

import challenge.platform.backend.payload.ClearanceDto;
import challenge.platform.backend.payload.ClearanceResponse;

public interface ClearanceService {

    

    ClearanceResponse getAllClearances(int pageNo, int pageSize, String sortBy, String sortDir);

    ClearanceDto getClearanceById(long id);
    

  

}