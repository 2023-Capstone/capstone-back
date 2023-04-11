package capstone.be.domain.user.controller;


import capstone.be.domain.user.dto.RetKakaoOAuth;
import capstone.be.domain.user.service.KakaoService;
import capstone.be.domain.user.service.SignService;
import capstone.be.global.dto.response.ResponseService;
import capstone.be.global.jwt.JwtProvider;
import capstone.be.global.jwt.RefreshTokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final SignService signService;

    private final KakaoService kakaoService;

    private final ResponseService responseService;
    private final RefreshTokenJpaRepository tokenJpaRepo;

    private final JwtProvider jwtProvider;
// Todo: code는 프론트에서 nickname,email과 함께 보내주는 것으로 수정  email동의 했으면 nickname을 같이 보내준다.

    @GetMapping("/login/oauth2/code/kakao")
    public HttpEntity<String> getAuthCode(@RequestParam String code) throws Exception {
        System.out.println("author code = " + code);
        // code로 token 요청
        RetKakaoOAuth tokenInfo = kakaoService.getKakaoTokenInfo(code);
        String access_Token = tokenInfo.getAccess_token();
        return new HttpEntity<>(access_Token);
    }
}