package remoteservices;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Sekhar on 4/27/18.
 */

public interface NotifyDataService {

    @FormUrlEncoded
    @POST("AppAuth.php")
    Call<AuthResponse> authenticateUser(@Field("type") String type,  @Field("code") String authcode);

    @FormUrlEncoded
    @POST("AppAuth.php")
    Call<AuthResponse> validateCode(@Field("type") String type, @Field("number") String number);

}
