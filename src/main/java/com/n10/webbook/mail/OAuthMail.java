package  com.n10.webbook.mail;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;


//Authentication and Auhorization mail
/*Việc Authentication and Auhorization mail nhằm mục đích auto đăng nhập thông qua
    accsessToken mà không cần pass mail và vượt qua xác thực 2 yếu tố*/
@Component("oAuthMail")
public class OAuthMail {



    private static String TOKEN_URL = "https://www.googleapis.com/oauth2/v4/token";
    private JavaMailSender sender;

    @Value("${oauthClientId}")
    private String oauthClientId ;

    @Value("${oauthSecret}")
    private String oauthSecret ;

    @Value("${refreshToken}")
    private  String refreshToken;

    @Value("${accessToken}")
    private  String accessToken;


    private long tokenExpires = 1458168133864L;



    public  String getAccessToken() {
        this.renewToken();
        return this.accessToken;
    }

    public void  setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public void renewToken(){

        if(System.currentTimeMillis() > tokenExpires) {

            try
            {
                String request = "client_id="+ URLEncoder.encode(oauthClientId, "UTF-8")
                        +"&client_secret="+URLEncoder.encode(oauthSecret, "UTF-8")
                        +"&refresh_token="+URLEncoder.encode(refreshToken, "UTF-8")
                        +"&grant_type=refresh_token";
                HttpURLConnection conn = (HttpURLConnection) new URL(TOKEN_URL).openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                PrintWriter out = new PrintWriter(conn.getOutputStream());
                out.print(request);
                out.flush();
                out.close();
                conn.connect();
                try
                {
                    HashMap<String,Object> result;
                    result = new ObjectMapper().readValue(conn.getInputStream(), new TypeReference<HashMap<String,Object>>() {});
                    this.accessToken = (String) result.get("access_token");
                    this.tokenExpires = System.currentTimeMillis()+(((Number)result.get("expires_in")).intValue()*1000);
                }
                catch (IOException e)
                {
                    String line;
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    while((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                    System.out.flush();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    public static String getTokenUrl() {
        return TOKEN_URL;
    }

    public static void setTokenUrl(String tokenUrl) {
        TOKEN_URL = tokenUrl;
    }

    public JavaMailSender getSender() {
        return sender;
    }

    public void setSender(JavaMailSender sender) {
        this.sender = sender;
    }

    public String getOauthClientId() {
        return oauthClientId;
    }

    public void setOauthClientId(String oauthClientId) {
        this.oauthClientId = oauthClientId;
    }

    public String getOauthSecret() {
        return oauthSecret;
    }

    public void setOauthSecret(String oauthSecret) {
        this.oauthSecret = oauthSecret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getTokenExpires() {
        return tokenExpires;
    }

    public void setTokenExpires(long tokenExpires) {
        this.tokenExpires = tokenExpires;
    }


}
