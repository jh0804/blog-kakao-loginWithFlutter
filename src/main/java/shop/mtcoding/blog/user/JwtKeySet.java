package shop.mtcoding.blog.user;

import lombok.Data;

import java.util.List;

@Data
public class JwtKeySet {
    private List<JwtKey> keys;

    @Data
    public static class JwtKey {
        private String kid;
        private String kty;
        private String alg;
        private String use;
        private String n;
        private String e;
    }

}
