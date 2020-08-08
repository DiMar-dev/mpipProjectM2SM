package com.dimar.map2saveme.optionMenu;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.dimar.map2saveme.Login;
import com.dimar.map2saveme.MainActivity;
import com.dimar.map2saveme.R;
import com.dimar.map2saveme.repository.Repository;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.provider.FirebaseInitProvider;

import java.util.Objects;
import java.util.logging.Logger;

public class OptionsMenuActivity extends AppCompatActivity {

    Logger logger = Logger.getLogger("OptionsMenuActivity");
    private static final String TAG = "OptionsMenuActivity";
//    FirebaseAuth auth;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu,menu);

        menu.findItem(R.id.menu_item1).setVisible(false);
        menu.findItem(R.id.menu_item3).setVisible(false);
        menu.findItem(R.id.menu_item4).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_item1:
                signOut();
                break;
            case R.id.menu_item2:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.menu_item3:
                logger.info("Clicked menu item: 3");
                break;
            case R.id.menu_item4:
                deleteAcc();
                logger.info("Clicked menu item: 4");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            finish();
                        } else {
                            Log.w(TAG, "signOut:failure", task.getException());
//                            showSnackbar(R.string.sign_out_failed);
                        }
                    }
                });
    }

    private void deleteAcc(){
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            // already signed in
            String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential authCredential;

            switch (firebaseUser.getProviderId()){

                case FacebookAuthProvider.PROVIDER_ID:
                    authCredential=FacebookAuthProvider.getCredential(AccessToken.getCurrentAccessToken().getToken());
                case EmailAuthProvider.PROVIDER_ID:
                    authCredential=EmailAuthProvider.getCredential(firebaseUser.getEmail(),"vnesi od std");
                case GoogleAuthProvider.PROVIDER_ID:
                    authCredential=GoogleAuthProvider.getCredential(String.valueOf(firebaseUser.getIdToken(true)),AccessToken.getCurrentAccessToken().getToken());
            }


            AuthUI.getInstance()
                    .delete(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Deletion succeeded
                                Repository repository=new Repository();
                                repository.removeUser(id);
                                startActivity(new Intent(getApplicationContext(), Login.class));
                                finish();
                            } else {
                                // Deletion failed
                                Log.w(TAG, "signOut:failure", task.getException());
                                if(((FirebaseAuthException)task.getException()).getErrorCode().equals("ERROR_REQUIRES_RECENT_LOGIN")){

                                }
                            }
                        }
                    });
        }
    }
}
