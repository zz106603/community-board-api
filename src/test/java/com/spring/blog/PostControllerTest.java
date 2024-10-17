package com.spring.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.blog.post.mapper.PostMapper;
import com.spring.blog.post.vo.PostVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        postMapper.deleteAll();
    }

    @Test
    void testCreatePost() throws Exception {
        PostVO post = new PostVO();
        post.setTitle("Test Title");
        post.setContent("Test Content");
        post.setWriter("Test Writer");
        post.setCategory("General");
        post.setSelectCount(0);
        post.setRecomCount(0);
        post.setWriteDate(LocalDateTime.now());
        post.setUpdateDate(LocalDateTime.now());
        post.setDeleteYn("N");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("SAVE POST COMPLETE"));
    }

}
