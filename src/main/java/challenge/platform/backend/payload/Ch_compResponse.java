package challenge.platform.backend.payload;

import lombok.Data;

import java.util.List;

@Data
public class Ch_compResponse {
    private List<Ch_compDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}