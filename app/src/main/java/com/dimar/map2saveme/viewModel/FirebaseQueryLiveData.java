package com.dimar.map2saveme.viewModel;

import android.os.Handler;
import android.util.Log;
import androidx.lifecycle.LiveData;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class FirebaseQueryLiveData extends LiveData<List<DataSnapshot>> {
    private static final String LOG_TAG = "FirebaseQueryLiveData";

    private final Query query;
    private final MyValueEventListener listener = new MyValueEventListener();
//
//    private boolean listenerRemovePending = false;
//    private final Handler handler = new Handler();
//    private final Runnable removeListener = new Runnable() {
//        @Override
//        public void run() {
//            query.removeEventListener(listener);
//            listenerRemovePending = false;
//        }
//    };

    public FirebaseQueryLiveData(Query query) {
        this.query = query;
    }

    public FirebaseQueryLiveData(DatabaseReference ref) {
        this.query = ref.child("photos");
    }

    @Override
    protected void onActive() {
        Log.d(LOG_TAG, "onActive");
        query.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(LOG_TAG, "onInactive");
        query.removeEventListener(listener);
    }

    private class MyValueEventListener implements ValueEventListener {
        List<DataSnapshot> dataSnapshots=new ArrayList<>();
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                dataSnapshots.add(postSnapshot);
            }
            setValue(dataSnapshots);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e(LOG_TAG, "Can't listen to query " + query, databaseError.toException());
        }
    }
}
