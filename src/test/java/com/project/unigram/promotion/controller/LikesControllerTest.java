package com.project.unigram.promotion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.domain.Role;
import com.project.unigram.auth.domain.Token;
import com.project.unigram.auth.repository.MemberRepository;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.promotion.domain.Likes;
import com.project.unigram.promotion.domain.Promotion;
import com.project.unigram.promotion.dto.PromotionCreateDto;
import com.project.unigram.promotion.service.LikesService;
import com.project.unigram.promotion.service.PromotionService;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Date;

@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
public class LikesControllerTest {
    @InjectMocks
    private LikesController likesController;

    @Mock
    private LikesService likesService;
    @Mock
    private PromotionService promotionService;
    @Mock
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    private @Value("${jwt.secret}") String envKey;
    private @Value("${jwt.access-token-validaity-in-seconds}") String envAccessExp;
    private @Value("${jwt.refresh-token-validaity-in-seconds}") String envRefreshExp;
    private Key key;
    private Long accessExp;
    private Long refreshExp;
    private String accessToken;

    @BeforeEach
    public void beforeEach(){
        //HTTP 호출
        mockMvc = MockMvcBuilders.standaloneSetup(likesController).build();
        byte[] keyBytes = envKey.getBytes();
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessExp = Long.parseLong(envAccessExp);
        this.refreshExp = Long.parseLong(envRefreshExp);
        Date accessExp = new Date(System.currentTimeMillis() + this.accessExp);
        Date refreshExp = new Date(System.currentTimeMillis() + this.refreshExp);

        Member member = getMember();
        Token token = Token.initToken(member.getId(), Role.NAVER, accessExp, refreshExp, key);
        accessToken = token.getAccessToken();
        token.createRefreshToken(member.getId(), Role.NAVER, accessExp, key);
        System.out.println(token.getRefreshToken());

    }

    @DisplayName("액세스토큰 발급 테스트")
    @Test
    void check() throws  Exception{
        System.out.println(accessToken);
    }

//    @DisplayName("추천 true 성공")
//    @Test
//    void 추천_true_성공_메서드() throws Exception{
//        //given
//        Member member = new Member(1L, "member1", "naver@naver.com", Role.NAVER);
//        memberRepository.save(member);
//        Promotion promotion = new Promotion(1L,member, "title1", "content1", "abstract1");
//        promotionService.write(new PromotionCreateDto().toDto(promotion), member);
//        Likes likes = new Likes(member, promotion);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization", "Bearer"+accessToken);
//        String requestJson = "{\"memberId\":\"1L\",\"promotionId\":\"1L\"}";
//
//        //when
//        mockMvc.perform(
//                MockMvcRequestBuilders
//                        .post("/api/promotion/like")
//                        .headers(httpHeaders)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//
//    }
    private Member getMember() {
        return Member.builder()
                .id(1L)
                .role(Role.NAVER)
                .build();
    }
}
