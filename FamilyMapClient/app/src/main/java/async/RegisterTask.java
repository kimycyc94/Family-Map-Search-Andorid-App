package async;

import android.os.AsyncTask;

import com.example.familymapclient.ServerProxy;

import Request.RegisterRequest;
import Result.RegisterResult;

public class RegisterTask extends AsyncTask<RegisterRequest, Void, RegisterResult> {
    private String serverHost;
    private int serverPort;
    private Context context;

    public RegisterTask(String serverHost, int serverPort, Context context) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.context = context;
    }

    @Override
    protected RegisterResult doInBackground(RegisterRequest... registerRequests) {
        ServerProxy serverProxy;
        RegisterRequest request;
        RegisterResult result;
        if (registerRequests.length == 1) {
            serverProxy = new ServerProxy(serverHost, serverPort);
            request = registerRequests[0];
            result = serverProxy.registerResult(request);
            return result;
        }
        return null;
    }

    @Override
    protected void onPostExecute(RegisterResult result) {
        super.onPostExecute(result);
        context.registerProceed(result);
    }

    public interface Context {
        void registerProceed(RegisterResult result);
    }
}
