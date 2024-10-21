package com.spring.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.blog.post.mapper.PostMapper;
import com.spring.blog.post.vo.PostVO;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PostControllerTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeAll
    static void setUp() {
        Dotenv dotenv = Dotenv.configure().load();

        System.setProperty("spring.datasource.username", dotenv.get("DB_USERNAME"));
        System.setProperty("spring.datasource.password", dotenv.get("DB_PASSWORD"));
        System.setProperty("spring.datasource.url", dotenv.get("DB_URL"));
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void clearData() {
        postMapper.deleteAll();
    }

    /*
        게시글 생성
     */
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

        //정상
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("SAVE POST COMPLETE"));

        //제목 누락
        post.setTitle(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.detailMessage").value("Post title cannot be null or empty"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        //작성자 누락
        post.setTitle("Test Title");
        post.setWriter(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.detailMessage").value("Post writer cannot be null or empty"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        //내용 누락
        post.setWriter("Test Writer");
        post.setContent(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.detailMessage").value("Post content cannot be null or empty"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        //카테고리 누락
        post.setContent("Test Content");
        post.setCategory(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.detailMessage").value("Post category cannot be null or empty"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
    }

    /*
        게시글 수정
     */
    @Test
    void testUpdatePost() throws Exception {
        // Given
        PostVO post = new PostVO();
        post.setTitle("Original Title");
        post.setContent("Original Content");
        post.setWriter("Original Writer");
        post.setCategory("General");
        post.setSelectCount(0);
        post.setRecomCount(0);
        post.setWriteDate(LocalDateTime.now());
        post.setUpdateDate(LocalDateTime.now());
        post.setDeleteYn("N");
        postMapper.createPost(post);

        // 수정할 데이터 준비
        PostVO updatedPost = new PostVO();
        updatedPost.setId(post.getId());
        updatedPost.setTitle("Updated Title");
        updatedPost.setContent("Updated Content");
        updatedPost.setWriter("Original Writer");
        updatedPost.setCategory("Updated Category");
        updatedPost.setSelectCount(0);
        updatedPost.setRecomCount(0);
        updatedPost.setWriteDate(post.getWriteDate());
        updatedPost.setUpdateDate(LocalDateTime.now());
        updatedPost.setDeleteYn("N");

        // When & Then: 정상적인 수정 요청
        mockMvc.perform(MockMvcRequestBuilders.put("/api/posts/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPost)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("UPDATE POST COMPLETE"));

        // 예외 상황: 존재하지 않는 게시글 수정
        updatedPost.setId(999999L);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/posts/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPost)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.detailMessage").value("Not Found Post, postId = 999999"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
    }

    /*
        게시글 삭제
     */
    @Test
    void testDeletePost() throws Exception {
        // Given
        PostVO post = new PostVO();
        post.setTitle("Title to be deleted");
        post.setContent("Content to be deleted");
        post.setWriter("Writer");
        post.setCategory("General");
        post.setSelectCount(0);
        post.setRecomCount(0);
        post.setWriteDate(LocalDateTime.now());
        post.setUpdateDate(LocalDateTime.now());
        post.setDeleteYn("N");
        postMapper.createPost(post);

        // When & Then: 정상적인 삭제 요청
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/posts/delete")
                        .param("postId", String.valueOf(post.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("DELETE POST COMPLETE"));

        // 존재하지 않는 게시글 삭제
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/posts/delete")
                        .param("postId", String.valueOf(999999L))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.detailMessage").value("Not Found Post, postId = 999999"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
    }

    /*
        게시글 ID 조회
     */
    @Test
    void testSelectPostById() throws Exception {
        // Given
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
        postMapper.createPost(post);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/" + post.getId())
                        .param("incrementViewCount", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Test Content"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.writer").value("Test Writer"));

        // 존재하지 않는 ID로 조회
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/9999999")
                        .param("incrementViewCount", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.detailMessage").value("Not Found Post, postId = 9999999"));
    }

    /*
        전체 게시글 조회
     */
    @Test
    void testSelectAllPosts() throws Exception {
        // Given
        PostVO post1 = new PostVO();
        post1.setTitle("Test Title 1");
        post1.setContent("Test Content 1");
        post1.setWriter("Test Writer 1");
        post1.setCategory("General");
        post1.setSelectCount(0);
        post1.setRecomCount(0);
        post1.setWriteDate(LocalDateTime.now());
        post1.setUpdateDate(LocalDateTime.now());
        post1.setDeleteYn("N");
        postMapper.createPost(post1);

        PostVO post2 = new PostVO();
        post2.setTitle("Test Title 2");
        post2.setContent("Test Content 2");
        post2.setWriter("Test Writer 2");
        post2.setCategory("General");
        post2.setSelectCount(0);
        post2.setRecomCount(0);
        post2.setWriteDate(LocalDateTime.now());
        post2.setUpdateDate(LocalDateTime.now());
        post2.setDeleteYn("N");
        postMapper.createPost(post2);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.list.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].title").value("Test Title 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.list[1].title").value("Test Title 1"));
    }


}
