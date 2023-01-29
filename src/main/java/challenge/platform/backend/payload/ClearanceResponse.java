package challenge.platform.backend.payload;

import lombok.Data;

import java.util.List;

@Data
public class ClearanceResponse {
    private List<ClearanceDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
    
}