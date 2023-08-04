package com.ts.postmaster.service;

/**
 * @author toyewole
 */

import com.ts.postmaster.dao.model.BlogPost;
import com.ts.postmaster.dao.model.PostComment;
import com.ts.postmaster.dao.repository.IBlogPostRepository;
import com.ts.postmaster.dao.repository.ICommentRepository;
import com.ts.postmaster.dto.ApiResp;
import com.ts.postmaster.dto.CommentDto;
import com.ts.postmaster.dto.enums.ResponseEnum;
import com.ts.postmaster.exception.CustomException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private IBlogPostRepository blogPostRepository;

    @Mock
    private ICommentRepository commentRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private CommentService commentService;

    @Test
     void testCreateComment_Success() {
        // Mocking the input data
        Long postId = 1L;
        CommentDto commentDto = new CommentDto();
        commentDto.setContent("This is a test comment.");

        BlogPost mockPost = new BlogPost();
        mockPost.setId(postId);

        when(blogPostRepository.findById(postId)).thenReturn(Optional.of(mockPost));
        when(userService.getCurrentUser()).thenReturn(getMockUserDetails());

        // Execute the method
        ApiResp<Long> result = commentService.createComment(commentDto, postId);

        // Assert the result
        assertNotNull(result);
        assertTrue(result.isStatus());
        assertEquals(ResponseEnum.SUCCESS.getMessage(), result.getMessage());
    }

    @Test
     void testCreateComment_PostNotFound() {
        // Mocking the input data
        Long postId = 1L;
        CommentDto commentDto = new CommentDto();
        commentDto.setContent("This is a test comment.");

        when(blogPostRepository.findById(postId)).thenReturn(Optional.empty());

        // Execute the method (This should throw a CustomException)
        assertThrows(CustomException.class, () -> commentService.createComment(commentDto, postId));

    }

    @Test
     void testGetCommentById_Success() {
        // Mocking the input data
        Long commentId = 1L;

        PostComment mockComment = new PostComment();
        mockComment.setId(commentId);
        mockComment.setText("This is a test comment.");

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(mockComment));

        // Execute the method
        ApiResp<PostComment> result = commentService.getCommentById(commentId);

        // Assert the result
        assertNotNull(result);
        assertEquals(ResponseEnum.SUCCESS.getMessage(), result.getMessage());
        assertNotNull(result.getData());
        assertEquals(commentId, result.getData().getId());
    }

    @Test
     void testGetCommentById_CommentNotFound() {
        // Mocking the input data
        Long commentId = 1L;

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // Execute the method (This should throw a CustomException)
        assertThrows(CustomException.class, () -> commentService.getCommentById(commentId));
    }

    // Helper method to create a mock UserDetails object for testing
    private UserDetails getMockUserDetails() {
        return new User("testuser", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    // Helper method to create a list of mock comments for testing
    private List<PostComment> createMockComments() {
        List<PostComment> comments = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            PostComment comment = new PostComment();
            comment.setId((long) i);
            comment.setText("Test comment " + i);
            comments.add(comment);
        }
        return comments;
    }
}
