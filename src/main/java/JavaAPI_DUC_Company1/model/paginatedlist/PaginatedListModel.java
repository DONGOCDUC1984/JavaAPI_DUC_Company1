package JavaAPI_DUC_Company1.model.paginatedlist;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginatedListModel<T> {
    private List<T> items;
    private long count;
    private int currentPage;
    private int pageSize;
    private int totalPages;

    public PaginatedListModel() {
    }

    public PaginatedListModel(List<T> items, long count, int totalPages)
    {
        this.items = items;
        this.count = count;
        this.totalPages = totalPages;
    }
}
