package com.dimar.map2saveme.optionMenu;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import com.dimar.map2saveme.Login;
import com.dimar.map2saveme.MainActivity;
import com.dimar.map2saveme.R;
import com.dimar.map2saveme.dialog.DeletePasswordDialog;
import com.dimar.map2saveme.repository.Repository;
import com.facebook.AccessToken;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class OptionsMenuActivity extends AppCompatActivity implements DeletePasswordDialog.NoticeDialogListener {

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
                showNoticeDialog();
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

    private void deleteAcc(String userPassword){
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null && !userPassword.isEmpty()) {
            // already signed in
            logger.info("User provider1:"+ firebaseUser.getProviderId());

            AuthUI.getInstance()
                    .delete(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Deletion succeeded
                                Repository repository=new Repository();
                                repository.removeUser(firebaseUser.getUid());
                                startActivity(new Intent(getApplicationContext(), Login.class));
                                finish();
                            } else {
                                // Deletion failed
                                Log.w(TAG, "signOut:failure", task.getException());
                                logger.info("User provider2:"+ firebaseUser.getProviderId());
                                if(((FirebaseAuthException)task.getException()).getErrorCode().equals("ERROR_REQUIRES_RECENT_LOGIN")){
                                    reauthenticateAndDelete(firebaseUser,userPassword);
                                }
                            }
                        }
                    });
        }
    }

    public void reauthenticateAndDelete(FirebaseUser firebaseUser,String usrPassword){

        for(UserInfo userInfo : firebaseUser.getProviderData()){
            logger.info("User provider loop:"+ userInfo.getProviderId());
        }

        firebaseUser.reauthenticate(getCredential(firebaseUser,usrPassword))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.w(TAG, "reauthenticated");
                deleteAcc(usrPassword);
            }
        });
    }

    //se zema samo prviot od lista provajderi
    public AuthCredential getCredential(FirebaseUser firebaseUser,String usrPassword){
        AuthCredential authCredential;

        String providerid=firebaseUser.getProviderData().stream()
                .filter(userInfo -> !userInfo.getProviderId().equals("firebase"))
                .collect(Collectors.toList())
                .get(0)
                .getProviderId();

        switch (providerid){

            case FacebookAuthProvider.PROVIDER_ID:
                authCredential=FacebookAuthProvider.getCredential(AccessToken.getCurrentAccessToken().getToken());
                break;
            case EmailAuthProvider.PROVIDER_ID:
                authCredential=EmailAuthProvider.getCredential(firebaseUser.getEmail(),usrPassword);
                break;
            case GoogleAuthProvider.PROVIDER_ID:
                authCredential=GoogleAuthProvider.getCredential(String.valueOf(firebaseUser.getIdToken(true)),AccessToken.getCurrentAccessToken().getToken());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + firebaseUser.getProviderId());
        }

        return authCredential;
    }

    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new DeletePasswordDialog();
        dialog.show(getSupportFragmentManager(), "DeletePasswordDialog");
    }

    @Override
    public void onDialogPositiveClick(String  dialog) {
       String id= dialog.trim();
       deleteAcc(id);

    }

    @Override
    public void onDialogNegativeClick(String dialog) {
        Log.w(TAG, "deleteCanceled");
    }
}
