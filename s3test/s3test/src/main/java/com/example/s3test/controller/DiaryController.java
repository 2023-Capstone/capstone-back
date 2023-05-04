package com.example.s3test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.s3test.aws.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor //final or @Not null 필드의 생성자를 자동으로 만들어줌
@RestController
@RequestMapping("/api")
public class DiaryController {

    private final S3Uploader s3Uploader;
    private final Map<String, Object> response = new HashMap<>();

    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestParam("image") MultipartFile multipartFile)throws IOException {
        String imgUrl = s3Uploader.upload(multipartFile, "static");
        int idx = imgUrl.lastIndexOf('.');
        String imgformat=imgUrl.substring(idx+1);
        // 이미지 형식 검사해서 에러 출력하는 코드 작성
        if (imgformat.equals("png") ||imgformat.equals("jpg")||imgformat.equals("jpeg")) {
            //성공시 응답코드 출력






            //api문서 잘 모르겠다 물어보자
            response.put("result","SUCCESS");
            response.put("status",201);
            response.put("Location",imgUrl);
        }
        else{
            //실패시 응답코드 출력
            response.put("result","FAIL");
            response.put("status",400);
            response.put("error","IMAGE_002");
        }
        return response;
    }

    //templates/diary/creatediaryForm 일기 작성하는 템플릿 반환
    @GetMapping("/diary")
    public String createDiary() {return "diary/createdDiaryForm";}

    //일기 작성 후 상세페이지 템플릿 반환
    @PostMapping("/diary")
    public String create(DiaryForm form){
        return "diary/diaryDetail";
    }
}
