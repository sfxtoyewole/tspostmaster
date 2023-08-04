package com.ts.postmaster.service;

/**
 * @author toyewole
 */

import com.ts.postmaster.dao.model.BlogPost;
import com.ts.postmaster.dao.model.PMUser;
import com.ts.postmaster.dao.repository.IBlogPostRepository;
import com.ts.postmaster.dao.repository.IPMUserRepository;
import com.ts.postmaster.dao.view.IPostView;
import com.ts.postmaster.dto.ApiResp;
import com.ts.postmaster.dto.DataTableResp;
import com.ts.postmaster.dto.PostDto;
import com.ts.postmaster.dto.enums.ResponseEnum;
import com.ts.postmaster.exception.CustomException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private IBlogPostRepository iBlogPostRepository;

    @Mock
    private IPMUserRepository ipmUserRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private PostService postService;

    @Test
    void testCreatePost_Success() {
        // Given
        PostDto postDto = new PostDto();
        postDto.setTitle("Test Post");
        postDto.setContent("This is a test post.");

        when(userService.getCurrentUser()).thenReturn(createMockUserDetails());
        when(ipmUserRepository.getPMUserByUsername(any())).thenReturn(createMockUser());
        when(iBlogPostRepository.save(any())).thenAnswer(invocation -> {
            BlogPost post = invocation.getArgument(0);
            post.setId(1L);
            return post;
        });

        // When
        ApiResp<Long> response = postService.createPost(postDto);

        // Then
        assertNotNull(response);
        assertTrue(response.isStatus());
        assertEquals(ResponseEnum.SUCCESS.getMessage(), response.getMessage());
        assertEquals(1L, response.getData());
    }

    @Test
    void testCreatePost_Unauthorized() {
        // Given
        PostDto postDto = new PostDto();
        postDto.setTitle("Test Post");
        postDto.setContent("This is a test post.");

        when(userService.getCurrentUser()).thenReturn(null);

        // When and Then
        assertThrows(CustomException.class, () -> postService.createPost(postDto));
    }

    @Test
    void testGetPosts_Success() {
        // Given
        String title = "Test Post";
        int index = 0;
        int size = 10;
        List<IPostView> blogPosts = createMockBlogPosts(size);

        when(iBlogPostRepository.findAllBlogPost(title, Pageable.ofSize(size).withPage(index))).thenReturn(blogPosts);

        // When
        ApiResp<DataTableResp<IPostView>> response = postService.getPosts(Optional.of(title), index, size);

        // Then
        assertNotNull(response);
        assertTrue(response.isStatus());
        assertEquals(ResponseEnum.SUCCESS.getMessage(), response.getMessage());
        assertNotNull(response.getData());
        assertEquals(size, response.getData().getData().size());
        assertTrue(response.getData().isHasNext());
    }

    @Test
    void testGetPostById_Success() {
        // Given
        Long postId = 1L;
        BlogPost blogPost = new BlogPost();
        blogPost.setId(postId);

        when(iBlogPostRepository.findById(postId)).thenReturn(Optional.of(blogPost));

        // When
        ApiResp<PostDto> response = postService.getPostById(postId);

        // Then
        assertNotNull(response);
        assertTrue(response.isStatus());
        assertEquals(ResponseEnum.SUCCESS.getMessage(), response.getMessage());
        assertNotNull(response.getData());
        assertEquals(postId, response.getData().getId());
    }

    @Test
    void testGetPostById_PostNotFound() {
        // Given
        Long postId = 1L;

        when(iBlogPostRepository.findById(postId)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(CustomException.class, () -> postService.getPostById(postId));
    }

    // Helper methods to create mock data

    private UserDetails createMockUserDetails() {
        return Mockito.mock(UserDetails.class);
    }

    private PMUser createMockUser() {
        PMUser user = new PMUser();
        user.setUsername("testUser");
        return user;
    }

    private List<IPostView> createMockBlogPosts(int count) {
        List<IPostView> blogPosts = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            blogPosts.add(createMockBlogPost((long) i));
        }
        return blogPosts;
    }

    private IPostView createMockBlogPost(Long id) {
        return new IPostView() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getContent() {
                return "This is a test post with id";
            }

            @Override
            public String getTitle() {
                return "Story of my life";
            }

            @Override
            public String getAuthor() {
                return "test";
            }

            @Override
            public Integer getLikes() {
                return 0;
            }
        };


    }
}
