package remoteservices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sekhar on 4/27/18.
 */

public class RemoteServiceFacade {
    private static final RemoteServiceFacade ourInstance = new RemoteServiceFacade();
    private static NotifyDataService service;

   public static RemoteServiceFacade getInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cline.co.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(NotifyDataService.class);
        return ourInstance;
    }

    private RemoteServiceFacade() {
    }

    public void notifydatachange(String type, String code, final ServiceCallBack<AuthResponse, String> callBack) {
        service.authenticateUser(type, code).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }

    public void validate(String type, String number, final ServiceCallBack<AuthResponse,String> callBack) {
        service.validateCode(type, number).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }

}
