package challenge.platform.backend.payload;

import java.util.List;

import lombok.Data;

@Data
public class ChallengeResponse {
    private List<ChallengeDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
