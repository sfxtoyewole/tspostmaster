package com.ts.postmaster.service;

import com.ts.postmaster.aop.TrackTime;
import com.ts.postmaster.dao.filter.BlogPostFilter;
import com.ts.postmaster.dao.repository.IPMUserRepository;
import com.ts.postmaster.dao.view.IPostView;
import com.ts.postmaster.dto.ApiResp;
import com.ts.postmaster.dto.DataTableResp;
import com.ts.postmaster.dto.PostDto;
import com.ts.postmaster.dto.enums.ResponseEnum;
import com.ts.postmaster.dao.model.BlogPost;
import com.ts.postmaster.dao.repository.IBlogPostRepository;
import com.ts.postmaster.exception.CustomException;
import com.ts.postmaster.utility.CommonLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author toyewole
 */
@RequiredArgsConstructor
@Service
public class PostService {

    private final IBlogPostRepository iBlogPostRepository;
    private final IPMUserRepository ipmUserRepository;
    private final UserService userService;

    @TrackTime
    public ApiResp<Long> createPost(PostDto postDto) {
        BlogPost post = postDto.transformToBlogPost();
        var currentUser = Optional.ofNullable(userService.getCurrentUser())
                .orElseThrow(() -> new CustomException("Kindly login to post ", HttpStatus.FORBIDDEN));

        post.setAuthor(ipmUserRepository.getPMUserByUsername(currentUser.getUsername()).getUsername());

        iBlogPostRepository.save(post);
        return ApiResp.getApiResponse(ResponseEnum.SUCCESS, post.getId());
    }

    @TrackTime
    public ApiResp<DataTableResp<IPostView> > getPosts(Optional<String> titleOpt, int index, int size) {


        List<IPostView> blogPost = iBlogPostRepository.findAllBlogPost(titleOpt.orElse(null), Pageable.ofSize(size).withPage(index));

        DataTableResp<IPostView> dataTableResp = new DataTableResp<>();
        dataTableResp.setData(blogPost);
        dataTableResp.setHasNext(blogPost.size() == size);

        return ApiResp.getApiResponse(ResponseEnum.SUCCESS, dataTableResp);
    }

    @TrackTime
    public ApiResp<PostDto> getPostById(Long id) {

        var post = iBlogPostRepository.findById(id)
                .orElseThrow(() -> new CustomException("Post with this id is not found", HttpStatus.NOT_FOUND));

        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setImageBase64(CommonLogic.toBase64(post.getImg()));
        postDto.setTitle(post.getTitle());

        return ApiResp.getApiResponse(ResponseEnum.SUCCESS, postDto);

    }
}
