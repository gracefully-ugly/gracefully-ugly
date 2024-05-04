package com.gracefullyugly.domain.review.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.gracefullyugly.common.security.jwt.JWTUtil;
import com.gracefullyugly.common.wrapper.ApiResponse;
import com.gracefullyugly.domain.review.dto.ReviewDto;
import com.gracefullyugly.domain.review.dto.ReviewResponse;
import com.gracefullyugly.domain.review.service.ReviewSearchService;
import com.gracefullyugly.domain.review.service.ReviewService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class ReviewControllerTest {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewSearchService reviewSearchService;

    @MockBean
    private ReviewService reviewService;

    @Test
    @DisplayName("리뷰 생성 테스트")
    void createReviewTest() throws Exception {
        given(reviewService.createReview(any(), any(), any())).willReturn(
                ReviewResponse.builder()
                        .reviewId(1L)
                        .userId(1L)
                        .itemId(1L)
                        .comments("좋아요")
                        .starPoint(5)
                        .build()
        );

        Gson gson = new Gson();
        String json = gson.toJson(ReviewDto.builder()
                .comments("좋아요")
                .starPoint(5)
                .build());

        String access = getToken();

        mockMvc.perform(post("/api/reviews/1")
                        .header("access", access)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewId").value(1L))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.itemId").value(1L))
                .andExpect(jsonPath("$.comments").value("좋아요"))
                .andExpect(jsonPath("$.starPoint").value(5))
                .andDo(print());

        verify(reviewService, times(1)).createReview(any(), any(), any());
    }

    @Test
    @DisplayName("리뷰 조회 테스트")
    void findReviewTest() throws Exception {
        given(reviewSearchService.getReviewById(any())).willReturn(
                ReviewResponse.builder()
                        .reviewId(1L)
                        .userId(1L)
                        .itemId(1L)
                        .comments("좋아요")
                        .starPoint(5)
                        .build()
        );

        mockMvc.perform(get("/api/reviews/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewId").value(1L))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.itemId").value(1L))
                .andExpect(jsonPath("$.comments").value("좋아요"))
                .andExpect(jsonPath("$.starPoint").value(5))
                .andDo(print());

        verify(reviewSearchService, times(1)).getReviewById(any());
    }

    @Test
    @DisplayName("상품별 리뷰 조회 테스트")
    void findReviewsByItemIdTest() throws Exception {
        List<ReviewResponse> responses = List.of(
                ReviewResponse.builder()
                        .reviewId(1L)
                        .userId(1L)
                        .itemId(1L)
                        .comments("좋아요")
                        .starPoint(5)
                        .build(),
                ReviewResponse.builder()
                        .reviewId(2L)
                        .userId(3L)
                        .itemId(1L)
                        .comments("좋아요")
                        .starPoint(3)
                        .build()
        );

        given(reviewSearchService.getReviewsByItemId(any())).willReturn(
                new ApiResponse<>(responses.size(), responses)
        );

        mockMvc.perform(get("/api/reviews/items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].reviewId").value(1L))
                .andExpect(jsonPath("$.data[0].userId").value(1L))
                .andExpect(jsonPath("$.data[0].itemId").value(1L))
                .andExpect(jsonPath("$.data[0].comments").value("좋아요"))
                .andExpect(jsonPath("$.data[0].starPoint").value(5))
                .andExpect(jsonPath("$.data[1].reviewId").value(2L))
                .andExpect(jsonPath("$.data[1].userId").value(3L))
                .andExpect(jsonPath("$.data[1].itemId").value(1L))
                .andExpect(jsonPath("$.data[1].comments").value("좋아요"))
                .andExpect(jsonPath("$.data[1].starPoint").value(3))
                .andDo(print());

        verify(reviewSearchService, times(1)).getReviewsByItemId(any());
    }

    @Test
    @DisplayName("리뷰 수정 테스트")
    void updateReviewTest() throws Exception {
        given(reviewService.updateReview(any(), any(), any())).willReturn(
                ReviewDto.builder()
                        .comments("싫어요")
                        .starPoint(3)
                        .build()
        );

        Gson gson = new Gson();
        String json = gson.toJson(ReviewDto.builder()
                .comments("싫어요")
                .starPoint(3)
                .build());

        String access = getToken();

        mockMvc.perform(patch("/api/reviews/1")
                        .header("access", access)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments").value("싫어요"))
                .andExpect(jsonPath("$.starPoint").value(3))
                .andDo(print());

        verify(reviewService, times(1)).updateReview(any(), any(), any());
    }

    @Test
    @DisplayName("리뷰 삭제 테스트")
    void deleteReviewTest() throws Exception {
        String access = getToken();

        mockMvc.perform(delete("/api/reviews/1")
                        .header("access", access))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(reviewService, times(1)).deleteReview(any(), any());
    }

    private String getToken() {
        return jwtUtil.createJwt("access", 100L, "loginId", "ROLE_BUYER", 60 * 10 * 1000L);
    }
}