package challenge.platform.backend.payload;

import lombok.Data;

import java.util.List;

@Data
public class AccountChallengeResponse {
    private List<AccountChallengeDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
