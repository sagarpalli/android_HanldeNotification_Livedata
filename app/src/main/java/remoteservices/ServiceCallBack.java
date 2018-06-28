package remoteservices;

/**
 * Created by Sekhar on 4/27/18.
 */

public interface ServiceCallBack <T,E>{

    void onSuccess(T response);
    void onError(E error);
}
