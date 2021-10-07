package fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.familymapclient.*;
import Model.*;
import Request.*;
import Result.*;
import async.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements RegisterTask.Context, LoginTask.Context, UserTask.Context, PersonTask.Context {
    public Singleton singleton = Singleton.getSingleton();
    private EditText serverPort;
    private EditText serverHost;
    private EditText userName;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText emailAddress;

    private EditTextWatcher serverPortWatcher = new EditTextWatcher();
    private EditTextWatcher serverHostWatcher = new EditTextWatcher();
    private EditTextWatcher userNameWatcher = new EditTextWatcher();
    private EditTextWatcher passwordWatcher = new EditTextWatcher();
    private EditTextWatcher firstNameWatcher = new EditTextWatcher();
    private EditTextWatcher lastNameWatcher = new EditTextWatcher();
    private EditTextWatcher emailAddressWatcher = new EditTextWatcher();

    private String gender = "";
    private RadioGroup genderButtons;
    private Button signInButton;
    private Button registerButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewResult = inflater.inflate(R.layout.fragment_login, container, false);

        serverHost = viewResult.findViewById(R.id.serverHostField);
        serverHost.addTextChangedListener(serverHostWatcher);
        serverPort = viewResult.findViewById(R.id.serverPortField);
        serverPort.addTextChangedListener(serverPortWatcher);
        userName = viewResult.findViewById(R.id.userNameField);
        userName.addTextChangedListener(userNameWatcher);
        password = viewResult.findViewById(R.id.passwordField);
        password.addTextChangedListener(passwordWatcher);
        firstName = viewResult.findViewById(R.id.firstNameField);
        firstName.addTextChangedListener(firstNameWatcher);
        lastName = viewResult.findViewById(R.id.lastNameField);
        lastName.addTextChangedListener(lastNameWatcher);
        emailAddress = viewResult.findViewById(R.id.emailField);
        emailAddress.addTextChangedListener(emailAddressWatcher);

        genderButtons = viewResult.findViewById(R.id.radioButtons);
        genderButtons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (group.getCheckedRadioButtonId() == R.id.maleRadioButton) {
                    gender = "m";
                } else {
                    gender = "f";
                }
                registerButtonController();
            }
        });

        registerButton = viewResult.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerHelper();
            }
        });

        signInButton = viewResult.findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginHelper();
            }
        });

        registerButtonController();
        loginButtonController();

        return viewResult;
    }

    //Async task classes helper functions//
    private void registerHelper() {
        try {
            if (serverHostWatcher.returnVal.equals("10.0.2.2") && serverPortWatcher.returnVal.equals("8080")) {
                RegisterTask register = new RegisterTask(serverHostWatcher.returnVal, Integer.parseInt(serverPortWatcher.returnVal), this);
                RegisterRequest request = new RegisterRequest(userNameWatcher.returnVal, passwordWatcher.returnVal,
                        emailAddressWatcher.returnVal, firstNameWatcher.returnVal, lastNameWatcher.returnVal, gender);
                register.execute(request);
            }
            else {
                if (!serverHostWatcher.returnVal.equals("10.0.2.2")) {
                    Toast.makeText(LoginFragment.this.getContext(), "Incorrect host number", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(LoginFragment.this.getContext(), "Incorrect port number", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(LoginFragment.this.getContext(), "Register has been failed. Try again.", Toast.LENGTH_SHORT).show();
        }
    }
    private void loginHelper() {
        try {
            if (serverHostWatcher.returnVal.equals("10.0.2.2") && serverPortWatcher.returnVal.equals("8080")) {
                LoginTask login = new LoginTask(serverHostWatcher.returnVal, Integer.parseInt(serverPortWatcher.returnVal), this);
                LoginRequest request = new LoginRequest(userNameWatcher.returnVal, passwordWatcher.returnVal);
                login.execute(request);
            }
            else {
                if (!serverHostWatcher.returnVal.equals("10.0.2.2")) {
                    Toast.makeText(LoginFragment.this.getContext(), "Incorrect host number", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(LoginFragment.this.getContext(), "Incorrect port number", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(LoginFragment.this.getContext(), "Login has been failed. Try again.", Toast.LENGTH_SHORT).show();
        }
    }
    private void userHelper(String personID, String authToken) {
        try {
            UserTask user = new UserTask(serverHostWatcher.returnVal, Integer.parseInt(serverPortWatcher.returnVal), this);
            user.execute(personID, authToken);
        } catch (NumberFormatException e) {
            Toast.makeText(LoginFragment.this.getContext(), "UserTask has been failed. Try again.", Toast.LENGTH_SHORT).show();
        }
    }
    //Helper functions done//

    //Button on and off controllers//
    private void registerButtonController() {
        if (serverHost.getText().toString().length() > 0 && serverPort.getText().toString().length() > 0 && userName.getText().toString().length() > 0
                && password.getText().toString().length() > 0 && firstName.getText().toString().length() > 0 && lastName.getText().toString().length() > 0
                && emailAddress.getText().toString().length() > 0) {
            if (gender.equals("m") || gender.equals("f")) {
                registerButton.setEnabled(true);
            }
        }
        else {
            registerButton.setEnabled(false);
        }
    }
    private void loginButtonController() {
        if (serverHost.getText().toString().length() > 0 && serverPort.getText().toString().length() > 0
                && userName.getText().toString().length() > 0 && password.getText().toString().length() > 0) {
            signInButton.setEnabled(true);
        }
        else {
            signInButton.setEnabled(false);
        }
    }
    //Controllers Done//

                                //****************Proceed tasks starts****************//
    @Override
    public void registerProceed(RegisterResult result) {
        String authToken;
        String personID;

        Log.i("LoginFragment", "registerProceed");
        if (result == null) {
            Toast.makeText(LoginFragment.this.getContext(), "Register has been failed due to empty result", Toast.LENGTH_LONG).show();
        }
        else{
            if (result.isSuccess()) {
                authToken = result.getAuthtoken();
                personID = result.getPersonID();
                userHelper(personID, authToken);
            }
            else {
                Toast.makeText(LoginFragment.this.getContext(), "Register has been failed. Try different username", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void loginProceed(LoginResult result) {
        String authToken;
        String personID;

        Log.i("LoginFragment", "loginProceed");
        if (result == null) {
            Toast.makeText(LoginFragment.this.getContext(), "Login has been failed.", Toast.LENGTH_LONG).show();
        }
        else{
            if (result.getSuccess()) {
                authToken = result.getAuthtoken();
                personID = result.getPersonID();
                userHelper(personID, authToken);
                Singleton.getSingleton().setLoginResult(result);
            }
            else {
                Toast.makeText(LoginFragment.this.getContext(), "Login has been failed.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void userTaskProceed() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Singleton singleton = Singleton.getSingleton();
                String personID = singleton.getPersonID();
                Persons person = singleton.getPerson(personID);

                String message = "Welcome to Family Map, " + person.getFirstName() + " " + person.getLastName() + "!";
                Toast.makeText(LoginFragment.this.getContext(), message , Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(MainActivity.changeFragment, true);
                startActivity(intent);
            }
        });
    }
    @Override
    public void userTaskFailed() {
        Toast.makeText(LoginFragment.this.getContext(), "Finding user has been failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void personTaskProceed(OnePersonResult result) {
        if (result == null) {
            Toast.makeText(LoginFragment.this.getContext(), "PersonTask has been failed due to empty result", Toast.LENGTH_LONG).show();
        }
        else {
            if (result.isSuccess()) {
                String message = "Welcome to Family Map, " + result.getFirstName() + " " + result.getLastName() + "!";
                Toast.makeText(LoginFragment.this.getContext(), message , Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(LoginFragment.this.getContext(), "PersonTask has been failed", Toast.LENGTH_LONG).show();
            }
        }
    }
                                //****************Proceed tasks done****************//

    //EditTextWatcher Class//
    public class EditTextWatcher implements TextWatcher {
        private String returnVal = "";

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            returnVal = s.toString();
            registerButtonController();
            loginButtonController();
        }

        @Override
        public void afterTextChanged(Editable s) { }
    }
    //Class done//
}