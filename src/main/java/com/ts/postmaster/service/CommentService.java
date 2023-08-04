package com.ts.postmaster.service;

import com.ts.postmaster.dao.filter.CommentFilter;
import com.ts.postmaster.dao.model.BlogPost;
import com.ts.postmaster.dao.model.PostComment;
import com.ts.postmaster.dao.repository.IBlogPostRepository;
import com.ts.postmaster.dao.repository.ICommentRepository;
import com.ts.postmaster.dto.ApiResp;
import com.ts.postmaster.dto.CommentDto;
import com.ts.postmaster.dto.DataTableResp;
import com.ts.postmaster.dto.enums.ResponseEnum;
import com.ts.postmaster.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author toyewole
 */
@RequiredArgsConstructor
@Service
public class CommentService {

    private final IBlogPostRepository iBlogPostRepository;
    private final ICommentRepository iCommentRepository;
    private final UserService userService;

    public ApiResp<Long> createComment(CommentDto commentDto, Long postId) {

        BlogPost post = iBlogPostRepository.findById(postId)
                .orElseThrow(() -> new CustomException("Post ID cannot be found", HttpStatus.BAD_REQUEST));

        var comment = commentDto.transformToCommentEntity();
        comment.setPostId(post.getId());

        var currentUser = Optional.ofNullable(userService.getCurrentUser())
                .orElseThrow(() -> new CustomException("Kindly login to comment ", HttpStatus.FORBIDDEN));

        comment.setAuthor(currentUser.getUsername());

        iCommentRepository.save(comment);

        return ApiResp.getApiResponse(ResponseEnum.SUCCESS, comment.getId());
    }

    public ApiResp< DataTableResp<PostComment>> getComments(Long postId, int index, int size) {

        var spec = Specification.where(CommentFilter.equalPostId(postId));
        Slice<PostComment> commentSlice = iCommentRepository.findAll(spec, Pageable.ofSize(size).withPage(index));

        DataTableResp<PostComment> dataTableResp = new DataTableResp<>();
        dataTableResp.setData(commentSlice.getContent());
        dataTableResp.setHasNext(commentSlice.hasNext());


        return ApiResp.getApiResponse(ResponseEnum.SUCCESS, dataTableResp);
    }

    public ApiResp<PostComment> getCommentById(Long id) {
        var comment = iCommentRepository.findById(id)
                .orElseThrow(() -> new CustomException("Comment with this id is not found", HttpStatus.NOT_FOUND));

        return ApiResp.getApiResponse(ResponseEnum.SUCCESS, comment);
    }
}
