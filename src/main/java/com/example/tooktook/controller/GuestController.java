package com.example.tooktook.controller;

import com.example.tooktook.common.response.ApiResponse;
import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.common.response.ValidMember;
import com.example.tooktook.exception.GlobalException;
import com.example.tooktook.model.dto.answerDto.AnswerDAO;
import com.example.tooktook.model.dto.answerDto.RandomAnswerDto;
import com.example.tooktook.model.dto.categoryDto.CategoryCountDto;
import com.example.tooktook.model.dto.categoryDto.CategoryListDto;
import com.example.tooktook.model.dto.categoryDto.mainPageDto;
import com.example.tooktook.model.dto.decoDto.GiftImgDto;
import com.example.tooktook.model.dto.memberDto.MemberDetailsDto;
import com.example.tooktook.model.dto.questionDto.QuestionOtherDto;
import com.example.tooktook.model.entity.Answer;
import com.example.tooktook.model.entity.Member;
import com.example.tooktook.model.repository.MemberNeo4jRepository;
import com.example.tooktook.service.AnswerService;
import com.example.tooktook.service.Neo4jService;
import com.example.tooktook.service.S3Service;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/guest")
public class GuestController {
    private final Neo4jService neo4jService;
    private final MemberNeo4jRepository memberNeo4jRepository;
    private final AnswerService answerService;
    private final S3Service s3Service;
    @GetMapping("/guest_rnd/{memberId}")
    public ApiResponse<RandomAnswerDto> guestRandomCategoryAndQuestion(@PathVariable Long memberId){

        log.info("-----------------GuestController 시작 ---------------");
        log.info("-------------path : /api/ques/guest_rnd/{}",memberId);

        Long memberIds = memberNeo4jRepository.findByMemberId(memberId)
                .orElseThrow(() -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER))
                .getMemberId();

        RandomAnswerDto randomAnswerDto = neo4jService.randomReadCategoryAndQuestion(memberIds);
        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,randomAnswerDto);
    }
    @GetMapping("/other/guest_question/{memberId}")
    public ApiResponse<?> guestOtherCategoryAndQuestion(@PathVariable Long memberId, @RequestParam Long rndCid){

        log.info("-----------------GuestController 시작 ---------------");
        log.info("-------------path : /api/other/login_question/{}?rndCid={}",memberId,rndCid);

        Long memberIds = memberNeo4jRepository.findByMemberId(memberId)
                .orElseThrow(() -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER))
                .getMemberId();

        QuestionOtherDto questionLst = neo4jService.otherCategoryAndQuestion(memberIds, rndCid);

        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,questionLst);
    }
    // api/ques/find/category #memberId
    @GetMapping("/find/category/{memberId}")
    public ApiResponse<CategoryCountDto> getMemberIdToCategoryAllCount(@PathVariable Long memberId){
        log.info("------------GuestController 시작 ----------------");
        log.info("--------------path : /api/guest/find/category/{} ---------------",memberId);

        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,
                neo4jService.getAllCategoryCount(memberId));
    }

    // api/ques/find/question #memberId
    @GetMapping("/find/question/{memberId}")
    public ApiResponse<?> getCategoryToQuestion(@PathVariable Long memberId, @RequestParam Long cid){
        log.info("------------GuestController 시작 ----------------");
        log.info("--------------path : /api/guest/find/question/{}?cid={} ---------------",memberId,cid);
        log.info("-------------------requestParm : {} ", cid);

        if(cid == 99999){
            return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,
                    neo4jService.getAllCategoryToQuestions(memberId));
        }else{
            return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,
                    neo4jService.getCategoryToQuestion(memberId,cid));
        }
    }
    // api/default #memberId
    @PostMapping("/default/{memberId}")
    public ApiResponse<Member> getMemberId(
            @PathVariable Long memberId) {

        log.info("------------GuestController 시작 ----------------");
        log.info("--------------path : /api/guest/default/{} ---------------",memberId);

        String memberEmail = memberNeo4jRepository.findByMemberId(memberId)
                .orElseThrow(() -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER))
                .getLoginEmail();

        return ApiResponse.ok(ResponseCode.Normal.CREATE,
                neo4jService.createMemberWithDefault(memberEmail));
    }
    // api/answers/details #memberId
    @GetMapping("/answers/details/{memberId}")
    public ApiResponse<AnswerDAO> getAnswerDetails(@PathVariable Long memberId,
                                                   @RequestParam Long answerId){
        log.info("------------GuestController 시작 ----------------");
        log.info("--------------path : /api/answers/details/{}?answerId={} ---------------",memberId,answerId);
        log.info("-------------------requestParm : {} ", answerId);

        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,answerService.getAnswerDetails(memberId,answerId));
    }
    @PostMapping("/save-gift-img/{memberId}")
    public ApiResponse<?> saveGiftImg(@PathVariable Long memberId,
                                      @RequestBody GiftImgDto giftImgDto){

        log.info("------------S3Controller 시작 ----------------");
        log.info("--------------path : /api/s3/save-gift-img ---------------");
        s3Service.saveGiftS3Url(memberId,giftImgDto);
        return ApiResponse.ok(ResponseCode.Normal.CREATE,String.format("memberId %d",memberId));
    }

    @GetMapping("/all/find/{memberId}")
    public ApiResponse<?> findAll(@PathVariable Long memberId){
        log.info("----------- All find Start--------- ");

        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,neo4jService.findAllGet(memberId));
    }
    @GetMapping("/myspace/{memberId}")
    public ApiResponse<List<mainPageDto>> mySpaceGetAll(@PathVariable Long memberId){
        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,neo4jService.findAllListMain(memberId));
    }
}
