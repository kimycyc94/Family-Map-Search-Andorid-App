package async;

import android.os.AsyncTask;

import com.example.familymapclient.ServerProxy;

import Request.LoginRequest;
import Result.LoginResult;

public class LoginTask extends AsyncTask<LoginRequest, Void, LoginResult> {
    private String serverHost;
    private int serverPort;
    private Context context;

    public LoginTask(String serverHost, int serverPort, Context context) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.context = context;
    }

    @Override
    protected LoginResult doInBackground(LoginRequest... loginRequests) {
        ServerProxy serverProxy;
        LoginRequest request;
        LoginResult result;
        if (loginRequests.length == 1) {
            serverProxy = new ServerProxy(serverHost, serverPort);
            request = loginRequests[0];
            result = serverProxy.loginResult(request);
            return result;
        }
        return null;
    }

    @Override
    protected void onPostExecute(LoginResult result) {
        super.onPostExecute(result);
        context.loginProceed(result);
    }

    public interface Context {
        void loginProceed(LoginResult result);
    }
}
