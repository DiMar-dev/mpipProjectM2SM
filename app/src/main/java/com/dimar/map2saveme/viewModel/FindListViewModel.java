package com.dimar.map2saveme.viewModel;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.dimar.map2saveme.models.Photo;
import com.dimar.map2saveme.repository.Repository;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindListViewModel extends ViewModel {

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(Repository.getDatabaseReference());

    private final LiveData<List<Photo>> photoLiveData =
            Transformations.map(liveData, new Deserializer());

    private class Deserializer implements Function<List<DataSnapshot>, List<Photo>> {
        List<Photo> photoAsync=new ArrayList<>();

        @Override
        public List<Photo> apply(List<DataSnapshot> dataSnapshot) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    dataSnapshot.stream().forEach(m->photoAsync.add(m.getValue(Photo.class)));
                   // photoAsync.add(dataSnapshot.getValue(Photo.class));

                    return null;
                }
            }.execute();
            return photoAsync;
        }
    }

    @NonNull
    public LiveData<List<Photo>> getDataSnapshotLiveData() {
        return photoLiveData;
    }
}
