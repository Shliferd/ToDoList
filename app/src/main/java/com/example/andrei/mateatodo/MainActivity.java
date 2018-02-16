package com.example.andrei.mateatodo;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andrei.mateatodo.model.AppDatabaseRoom;
import com.example.andrei.mateatodo.model.User;
import com.example.andrei.mateatodo.model.UserResponse;
import com.example.andrei.mateatodo.remote.APIUtils;
import com.example.andrei.mateatodo.remote.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private int id;
    AppDatabaseRoom databaseRoom;
    UserService userService;
    EditText usernameEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBtn;

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginBtn = findViewById(R.id.loginBtn);
        userService = APIUtils.getUserService();

        databaseRoom = Room.databaseBuilder(getApplicationContext(), AppDatabaseRoom.class, "luciferfall1n1").fallbackToDestructiveMigration().allowMainThreadQueries().build();

        /*
        byte[] randomBytes = new byte[20];
        new Random().nextBytes(randomBytes);
        User shliferd = new User("Shliferd", "123a", "andrei.matea96@gmail.com", randomBytes);
        databaseRoom.userDao().insertUser(shliferd);
        */

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String userNameOnButton = usernameEditText.getText().toString();
                final String passwordOnButton = passwordEditText.getText().toString();

                boolean connected = false;
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                } else
                    connected = false;

                if (connected) {
                    try {

//                        final String username = usernameEditText.getText().toString();
//                        final String password = passwordEditText.getText().toString();
                        if (userNameOnButton != null && passwordOnButton != null && userNameOnButton.length() > 0 && passwordOnButton.length() > 0) {
                            id = 0;
                            //String passwordEncrypted = null;
                            //passwordEncrypted = User.AESCrypt.encrypt(password);

                            //id = databaseRoom.userDao().login(username, password);
                            //GetIdTask getIdTask = new GetIdTask(username, passwordEncrypted);
                            //getIdTask.execute();

                            //List<User> users = databaseRoom.userDao().getAllUsers();

                            Call<UserResponse> listCall = userService.getUserByUsernameAndPassword(userNameOnButton, passwordOnButton);
                            listCall.enqueue(new Callback<UserResponse>() {
                                @Override
                                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                                    if (response.isSuccessful()) {
                                        setId(response.body().users, userNameOnButton, passwordOnButton);
                                    }
                                }

                                @Override
                                public void onFailure(Call<UserResponse> call, Throwable t) {
                                    Log.w("MyTag", "requestFailed", t);
                                }
                            });

                        } else {
                            Toast.makeText(MainActivity.this, "Introduce an appropriate username and password", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Connect to internet to login!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setId(List<User> users, String userNameOnButton, String passwordOnButton) {
        for (User userFor : users) {
            if ((userFor.getUsername().equals(userNameOnButton)) && (userFor.getPassword().equals(passwordOnButton))) {
                id = userFor.getId();
                break;
            }
        }
        if (id != 0) {
            Intent homeIntent = new Intent(MainActivity.this, HomeTODO.class);
            homeIntent.putExtra("userId", id);
            startActivity(homeIntent);
        } else {
            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
        }
    }

}
