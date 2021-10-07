package async;

import android.os.AsyncTask;

import com.example.familymapclient.ServerProxy;

import Result.OnePersonResult;

public class PersonTask extends AsyncTask<String, Void, OnePersonResult> {
    private String serverHost;
    private int serverPort;
    private Context context;

    public PersonTask(String serverHost, int serverPort, Context context) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.context = context;
    }

    @Override
    protected OnePersonResult doInBackground(String... strings) {
        ServerProxy serverProxy;
        String personID;
        String authToken;
        OnePersonResult result;
        if (strings.length == 2) {
            serverProxy = new ServerProxy(serverHost, serverPort);
            personID = strings[0];
            authToken = strings[1];
            result = serverProxy.getPerson(personID, authToken);
            return result;
        }
        return null;
    }

    @Override
    protected void onPostExecute(OnePersonResult result) {
        super.onPostExecute(result);
        context.personTaskProceed(result);
    }

    public interface Context {
        void personTaskProceed(OnePersonResult result);
    }
}
