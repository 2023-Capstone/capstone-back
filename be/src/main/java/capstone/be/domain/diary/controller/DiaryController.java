

package capstone.be.domain.diary.controller;

import capstone.be.domain.diary.domain.Diary;
import capstone.be.domain.diary.dto.DiaryCreatedDto;
import capstone.be.domain.diary.dto.DiaryDto;
import capstone.be.domain.diary.dto.response.DiaryCreateResponse;
import capstone.be.domain.diary.dto.response.DiaryMoodSearchResponse;
import capstone.be.domain.diary.dto.response.DiaryMoodTotalResponse;
import capstone.be.domain.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<DiaryCreateResponse> createDiary(@RequestBody DiaryDto diaryDto){   // id 만 반환하는 응답
        System.out.println(diaryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(diaryService.save(diaryDto));
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<DiaryCreatedDto> diary(@PathVariable Long diaryId){   // DiaryDto에 date(createdAt)이 추가된 응답
        return ResponseEntity.ok(diaryService.getDiary(diaryId));
    }


    @PatchMapping("/{diaryId}")
    public ResponseEntity<?> updateDiary(@PathVariable Long diaryId, DiaryDto diaryDto){
        diaryService.update(diaryId, diaryDto);
        return ResponseEntity.ok("");
    }


    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long diaryId){  // 응답 값 없음
        diaryService.delete(diaryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    //페이지형태
    @GetMapping("/emotion")
    public List<DiaryMoodSearchResponse> getSortedDiariesByMood(@RequestParam("mood") String mood,
                                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                                @RequestParam(value = "size", defaultValue = "10") int size){

        Page<Diary> sortedDiaries = diaryService.getSortedDiariesByMood(mood, page, size);

        //DiaryEntity를 dto로 변환
        List<Diary> diaryList = sortedDiaries.getContent();
        List<DiaryMoodSearchResponse> responses = diaryList.stream().map(DiaryMoodSearchResponse::from).collect(Collectors.toList());

        return responses;
    }

    //마이페이지 들어갈 때 전체 기분별 개수 보내주기
    @GetMapping("/emotion/num")
    public DiaryMoodTotalResponse getTotalMood(){
        DiaryMoodTotalResponse response = diaryService.getMoodTotal();
        return response;
    }

}
