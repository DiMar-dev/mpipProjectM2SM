package com.dimar.map2saveme;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.dimar.map2saveme.firebaseAuth.FirebaseCallback;
import com.dimar.map2saveme.models.Photo;
import com.dimar.map2saveme.models.User;
import com.dimar.map2saveme.optionMenu.OptionsMenuActivity;
import com.dimar.map2saveme.repository.Repository;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.util.ExtraConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


// mozno da se odi na dr activitu bez vnesuvanje na dopolnitlno info za nov korisnik
public class MainActivity extends OptionsMenuActivity {

    EditText phone;
    CheckBox isAH;
    Button submitBt;
    Button dataBt;
    Button save;
    Toolbar myToolbar;
    private Repository repository;

    private static final String TAG = "SignedInActivity";
    FirebaseAuth auth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //generateFBKey();

        myToolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);

        auth=FirebaseAuth.getInstance();
        IdpResponse response = getIntent().getParcelableExtra(ExtraConstants.IDP_RESPONSE);

        if (auth.getCurrentUser() != null) {
            // already signed in
            //saveUser();
            setUI();
        } else {
            // not signed in
            startActivity(new Intent(this,Login.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu,menu);
        menu.findItem(R.id.menu_item1).setVisible(true);
        menu.findItem(R.id.menu_item2).setVisible(false);
        menu.findItem(R.id.menu_item3).setVisible(false);
        return true;
    }

    private void setUI(){
        repository=new Repository();

        user=auth.getCurrentUser();

        phone=findViewById(R.id.phone);
        submitBt=findViewById(R.id.button);
        dataBt=findViewById(R.id.showDataActivity);
        isAH=findViewById(R.id.isAHChk);
        save=findViewById(R.id.saveBt);

        //String ui=user.getIdToken(false).getResult().getToken();

        repository.findUser(new FirebaseCallback() {
            @Override
            public void onCallback(User flag) {
                if (flag == null){
                    phone.setVisibility(View.VISIBLE);
                    isAH.setVisibility(View.VISIBLE);
                    save.setVisibility(View.VISIBLE);
                }
            }
        }, user.getUid());

        clickListen();
    }

    private void clickListen(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToBase();
            }
        });

        submitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Map_Pic.class);
                startActivity(intent);
            }
        });
        dataBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(getApplicationContext(),FindList.class);
                startActivity(intent2);
            }
        });
    }

    private void addToBase() {
        if(!phone.getText().toString().trim().equals("")){
            User pom = new User(user.getUid(), user.getDisplayName(), user.getDisplayName(),
                    phone.getText().toString().trim(), user.getEmail(), isAH.isChecked());
            repository.save(pom);
        }
    }


    public final void generateFBKey() {
        try {
            PackageInfo info = this.getPackageManager().getPackageInfo("com.dimar.map2saveme",
                    this.getPackageManager().GET_SIGNATURES);

            for(Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), 0));
            }

        } catch (PackageManager.NameNotFoundException var7) {
        } catch (NoSuchAlgorithmException var8) {
        }

    }

}
