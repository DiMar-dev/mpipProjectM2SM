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
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.logging.Logger;

public class OptionsMenuActivity extends AppCompatActivity {

    Logger logger = Logger.getLogger("OptionsMenuActivity");
    private static final String TAG = "OptionsMenuActivity";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu,menu);

        menu.findItem(R.id.menu_item1).setVisible(false);
        menu.findItem(R.id.menu_item3).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_item1:
                ///
                signOut();
                break;
            case R.id.menu_item2:
                ///
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.menu_item3:
                //
                logger.info("Clicked menu item: 3");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void signOut() {
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
}
