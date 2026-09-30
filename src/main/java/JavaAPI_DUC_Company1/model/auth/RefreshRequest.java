package JavaAPI_DUC_Company1.model.auth;

public class RefreshRequest {
    private String refreshToken;
    public RefreshRequest() {
    }
    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
