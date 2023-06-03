package com.project.unigram.promotion.controller;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.global.dto.ResponseSuccess;
import com.project.unigram.promotion.dto.PromotionCreateDto;
import com.project.unigram.promotion.dto.PromotionDetailDto;
import com.project.unigram.promotion.repository.LikesRepository;
import com.project.unigram.promotion.service.PromotionServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController //REST API를 처리하는 controller로 등록 어노테이션
@RequiredArgsConstructor
@RequestMapping("/api/promotion") //URI 지정 어노테이션
public class PromotionController {
    private final PromotionServiceImpl promotionService;
    private final MemberService memberService;
    private final LikesRepository likesRepository;


    //전체 게시글 조회
    @ApiOperation(value="전체 게시글 보기", notes="전체 게시글을 조회한다.")//API 명세서 Swagger에서 타이틀, 설명을 걸어주는 어노테이션
    @ResponseStatus(HttpStatus.OK)//정상 실행시 상태코드를 200으로 주는 코드
    @GetMapping("/resources")
    public ResponseSuccess findAll(
            @RequestParam(value = "page", defaultValue="1") int page,
            @RequestParam(value = "limit", defaultValue = "16") int limit,
            @RequestParam(value = "sorting") int sorting
                            ){

        return new ResponseSuccess(200,"전체 게시물 리턴",promotionService.getPromotions(page, limit, sorting));
    }

    //게시글 4개만 조회
    @ApiOperation(value = "현재 날짜로부터 2주 전까지 추천수가 높은 순으로 게시물 리턴", notes = "현재 날짜로부터 2주 전까지 추천수 높은 순으로 게시물을 조회한다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/resources/firstPage")
    public ResponseSuccess findFour(){

        return new ResponseSuccess(200, "메인 홍보글 불러오기에 성공하였습니다. ",promotionService.getFourPromotions());
    }



    //개별 게시글 조회
    @ApiOperation(value="개별 게시글 보기", notes="개별 게시글을 조회한다")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/detail/{promotionId}")
    public ResponseSuccess getPromotion(
            @PathVariable("promotionId") Long promotionId,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        viewCountMethod(promotionId, request, response);
        return new ResponseSuccess(200, "홍보글 상세 게시물 조회에 성공하였습니다.", promotionService.getPromotion(promotionId));
    }

    @ApiOperation(value = "사용자가 작성한 홍보글 보기", notes = "사용자가 작성한 홍보글을 조회한다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/my")
    public ResponseSuccess getUserPromotions(){
        return new ResponseSuccess(200, "사용자가 작성한 홍보글 조회에 성공하였습니다.", promotionService.getUserPromotions());
    }


    //게시글 작성
    @ApiOperation(value="게시글 작성", notes="게시글을 작성한다.")
    @PostMapping("/register")
    public ResponseSuccess write(@RequestBody @Valid PromotionCreateDto promotionCreateDto){ //@RequestBody로 JSON파싱
        Member member = memberService.getMember();
        return new ResponseSuccess(200, "홍보글 등록에 성공하였습니다.",promotionService.write(promotionCreateDto, member));
    }

    //게시글 수정
    @ApiOperation(value="게시글 수정", notes="게시글을 수정한다.")
    @PutMapping("/update/{postId}")
    public ResponseSuccess update(@PathVariable("postId") Long postId, @RequestBody PromotionCreateDto promotionCreateDto){

        return new ResponseSuccess(200,"홍보글 수정에 성공하였습니다.", promotionService.update(postId, promotionCreateDto));
    }

    //게시글 삭제
    @ApiOperation(value="게시글 삭제", notes="게시글을 삭제한다.")
    @DeleteMapping("/delete/{postId}")
    public ResponseSuccess delete(@PathVariable("postId") Long postId){
        promotionService.delete(postId);
        return new ResponseSuccess(200, "홍보글 삭제에 성공하였습니다.", "");
    }

    private void viewCountMethod(Long promotionId, HttpServletRequest request, HttpServletResponse response){
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("promotionView")){
                    oldCookie = cookie;
                }
            }
        }

        if(oldCookie != null){
            if(!oldCookie.getValue().contains("["+promotionId.toString()+"]")){
                promotionService.updateView(promotionId);
                oldCookie.setValue(oldCookie.getValue()+"["+promotionId+"]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60*60*24);
                response.addCookie(oldCookie);
            }
        }else{
            promotionService.updateView(promotionId);
            Cookie newCookie = new Cookie("promotionView", "["+promotionId+"]");
            newCookie.setPath("/");
            newCookie.setMaxAge(2*60);
            response.addCookie(newCookie);
        }
    }

}