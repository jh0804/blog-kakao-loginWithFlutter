package shop.mtcoding.blog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import shop.mtcoding.blog.user.JwtKeySet;
import shop.mtcoding.blog.user.MyRSAUtil;
import shop.mtcoding.blog.user.OAuthProfile;

public class JwtKeySetTest {

    // 1번
    @Test
    public void publicKeyDownload() {
        String jwtUrl = "https://kauth.kakao.com/.well-known/jwks.json"; // ← 실제 주소로 교체

        RestTemplate restTemplate = new RestTemplate();
        JwtKeySet keySet = restTemplate.getForObject(jwtUrl, JwtKeySet.class);

        assert keySet != null;
        for (JwtKeySet.JwtKey key : keySet.getKeys()) {
            System.out.println(key.toString());
        }
    }

    // 2. 다운 받은 공개키로 idToken 검증
    @Test
    public void rsaVerify_test() {
        // idToken 값을 직접 변경해야함
        String idToken = """
                eyJraWQiOiI5ZjI1MmRhZGQ1ZjIzM2Y5M2QyZmE1MjhkMTJmZWEiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzMjZjZGFiN2JmYzhkM2M1NzVhZGI2NWUyOGJhZWExYyIsInN1YiI6IjQzMjA0MTMzMDkiLCJhdXRoX3RpbWUiOjE3NTA5MDIyODQsImlzcyI6Imh0dHBzOi8va2F1dGgua2FrYW8uY29tIiwibmlja25hbWUiOiLstZzso7ztmLgiLCJleHAiOjE3NTA5MjM4ODQsImlhdCI6MTc1MDkwMjI4NCwicGljdHVyZSI6Imh0dHA6Ly9rLmtha2FvY2RuLm5ldC9kbi94SDlWai9idHNOY0llcHNtNy80a0Q4RkY4cDcxM3FLOUVVdjY4Z1UxL2ltZ18xMTB4MTEwLmpwZyJ9.TvEePs2IrHi4kDWndA_O-R7uX4hsmDAsn7EdFdLxr2JjUO4YyS0rGcWHf51ZwX0pl_rjvKsgEhFWfv6NKO0JDC4ye543_IALjEHTGwpipj5BbLZ_IOxF_ez3E28mJUIx7vg0X88C7jhhLuumR9RPLkzzmoJHHPNFwOcL9UoA4LJ3EWdiV2FxsccaCnyGDaTp_UT-L1cln375IDLoIgL-9YT77R_2LHJQBgTvjE4R9SQxlML5vOlVbuhESlgK2X28bKJ5JgxYwqym0-pUGVZNlNFO6EhW7GSDmaIL-jghD40htBahbEHQMK1k4IlVqQ98kiNUst2aupzQ4ahOD17G4A
                """;
        MyRSAUtil.verify(idToken);
    }

    // 3. 검증된 idToken의 페이로드를 자바에 OAuthProfile 객체에 파싱
    @Test
    public void payloadParse() throws JsonProcessingException {
        String payload = """
                {"aud":"326cdab7bfc8d3c575adb65e28baea1c","sub":"4320413309","auth_time":1750902284,"iss":"https://kauth.kakao.com","nickname":"최주호","exp":1750923884,"iat":1750902284,"picture":"http://k.kakaocdn.net/dn/xH9Vj/btsNcIepsm7/4kD8FF8p713qK9EUv68gU1/img_110x110.jpg"}
                """;
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthProfile profile = objectMapper.readValue(payload, OAuthProfile.class);
        System.out.println(profile);
    }
}