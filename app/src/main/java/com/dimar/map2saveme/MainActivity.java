package com.dimar.map2saveme;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.dimar.map2saveme.firebaseAuth.FirebaseCallback;
import com.dimar.map2saveme.models.User;
import com.dimar.map2saveme.optionMenu.OptionsMenuActivity;
import com.dimar.map2saveme.repository.Repository;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.util.ExtraConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;


// mozno da se odi na dr activitu bez vnesuvanje na dopolnitlno info za nov korisnik
public class MainActivity extends OptionsMenuActivity {

    EditText phone;
    TextView infoAH;
    TextView labelPhone;
    CheckBox isAH;
    Button submitBt;
    Button dataBt;
    Button save;
    Toolbar myToolbar;
    List<View> listView;
    TextView usrCount;
    TextView photoCount;
    private Repository repository;

    private static final String TAG = "SignedInActivity";
    FirebaseAuth auth;
    FirebaseUser user;
    IdpResponse response;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //generateFBKey();

        initialize();

        if (auth.getCurrentUser() != null) {
            // already signed in
            //saveUser();
            setUI(response!=null);
        } else {
            // not signed in
            startActivity(new Intent(this,Login.class));
            finish();
        }
    }

    private void initialize() {
        myToolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        response = getIntent().getParcelableExtra(ExtraConstants.IDP_RESPONSE);

        repository=new Repository();
    }

    private void setUI(boolean isNew){
        initView();

        if(isNew)
            onResumeSetUI();

        clickListen();
    }

    private void onResumeSetUI(){
        FirebaseUserMetadata metadata =auth.getCurrentUser().getMetadata();
        if (metadata != null && metadata.getCreationTimestamp() == metadata.getLastSignInTimestamp()) {
            setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu,menu);
        menu.findItem(R.id.menu_item1).setVisible(true);
        menu.findItem(R.id.menu_item2).setVisible(false);
        menu.findItem(R.id.menu_item3).setVisible(false);
        menu.findItem(R.id.menu_item4).setVisible(true);
        return true;
    }

    private void initView() {
        phone=findViewById(R.id.phone);
        submitBt=findViewById(R.id.button);
        dataBt=findViewById(R.id.showDataActivity);
        isAH=findViewById(R.id.isAHChk);
        infoAH=findViewById(R.id.textViewInfo);
        save=findViewById(R.id.saveBt);
        labelPhone=findViewById(R.id.labelForPhone);
        usrCount=findViewById(R.id.userCount_txt);
        photoCount=findViewById(R.id.photoCount_txt);

        listView= Arrays.asList(phone,isAH,infoAH,save,labelPhone);
        initStat();
    }

    private void initStat() {
        repository.statisticCount(new FirebaseCallback() {
            @Override
            public void onCallback(User flag) { }

            @Override
            public void onStatCallback(String num) {
                usrCount.setText(usrCount.getText().toString().concat("\n").concat(num));
            }
        },"users");
        repository.statisticCount(new FirebaseCallback() {
            @Override
            public void onCallback(User flag) { }

            @Override
            public void onStatCallback(String num) {
                photoCount.setText(photoCount.getText().toString().concat("\n").concat(num));
            }
        },"photos");
    }

    private void clickListen(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVisibility(View.INVISIBLE);
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
        String phoneEdited=phone.getText().toString().trim();
            User pom = new User(user.getUid()
                    , user.getDisplayName()
                    , user.getDisplayName()
                    , (phoneEdited.equals("") ? "//" : phoneEdited)
                    , user.getEmail(), isAH.isChecked());
            repository.save(pom);

    }

    private void setVisibility(int visibility){
        listView.forEach(item-> item.setVisibility(visibility));
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
