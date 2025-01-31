package Board.Post.Dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<PostDto> {
    private List<PostDto> postList;
    private int currentPage;
    private int totalPage;

    public PageResponse(List<PostDto> postList,int currentPage,int totalPage){
        this.postList=postList;
        this.currentPage=currentPage;
        this.totalPage=totalPage;
    }


}
