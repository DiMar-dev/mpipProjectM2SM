package com.dimar.map2saveme.viewModel;

import android.os.Handler;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import com.dimar.map2saveme.models.Photo;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class FirebaseQueryLiveData extends LiveData<List<Photo>> {
    private static final String LOG_TAG = "FirebaseQueryLiveData";

    private final Query query;
    private final MyValueEventListener listener = new MyValueEventListener();
    private final MyChildEventListener childListener = new MyChildEventListener();


    public FirebaseQueryLiveData(Query query) {
        this.query = query;
    }

    public FirebaseQueryLiveData(DatabaseReference ref) {
        this.query = ref.child("photos");
    }

    @Override
    protected void onActive() {
        Log.d(LOG_TAG, "onActive");
        query.addChildEventListener(childListener);

    }

    @Override
    protected void onInactive() {
        Log.d(LOG_TAG, "onInactive");
        query.removeEventListener(childListener);
    }



//  na sekoja promena loadira cela lista site predh + novo
    private class MyValueEventListener implements ValueEventListener {
        List<DataSnapshot> dataSnapshots=new ArrayList<>();
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
              //  dataSnapshots.add(postSnapshot.getValue(Photo.class));
                dataSnapshots.add(postSnapshot);

            }
//            setValue(dataSnapshots);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e(LOG_TAG, "Can't listen to query " + query, databaseError.toException());
        }
    }

    private class MyChildEventListener implements ChildEventListener{


        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            List<Photo> pomSnap=new ArrayList<>();
            pomSnap.add(dataSnapshot.getValue(Photo.class));
            setValue(pomSnap);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            List<Photo> pomSnap=new ArrayList<>();
            pomSnap.add(dataSnapshot.getValue(Photo.class));
            setValue(pomSnap);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            List<Photo> pomSnap=new ArrayList<>();
            pomSnap.add(dataSnapshot.getValue(Photo.class));
            pomSnap.get(0).setImageBase64("DELETED");
            setValue(pomSnap);
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
}
