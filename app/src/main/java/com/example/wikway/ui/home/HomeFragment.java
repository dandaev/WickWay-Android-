package com.example.wikway.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikway.App;
import com.example.wikway.JobAd;
import com.example.wikway1.R;
import com.example.wikway.ui.VacancyAdapter;
import com.example.wikway.utils.OnItemClickListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    public static final String TAG = "JobListView";
    private static HomeFragment instance;

    private ProgressBar progressBar;

    private RecyclerView recyclerView;
    private VacancyAdapter adapter;
    private App.OnDataReadyListener onDataReadyListener;
    public static HomeFragment getInstance(){
        if(instance==null)
            instance = new HomeFragment();
        return instance;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        progressBar = view.findViewById(R.id.progress_bar);
        initRecyclerView(view);
        getJobs();
        return view;

    }

    private void initRecyclerView(View view){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        recyclerView = view.findViewById(R.id.recyclerv_view);
        adapter = new VacancyAdapter(getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(getContext(), GalleryActivity.class);
                intent.putExtra("image_url", adapter.jobs.get(pos).getImageLink());
                intent.putExtra("image_name", adapter.jobs.get(pos).getTitle());
                intent.putExtra("image_artDerStelle", adapter.jobs.get(pos).getArtDerStelle());
                intent.putExtra("image_anschreiben", adapter.jobs.get(pos).getAnschreiben());
                intent.putExtra("image_abteilung", adapter.jobs.get(pos).getAbteilung());
                intent.putExtra("image_qualifizirung", adapter.jobs.get(pos).getQualifizirung());
                intent.putExtra("image_einsatsort", adapter.jobs.get(pos).getAnforderung());
                intent.putExtra("image_strasse", adapter.jobs.get(pos).getSrasse());
                intent.putExtra("image_email", adapter.jobs.get(pos).getEmail());
                intent.putExtra("image_ort", adapter.jobs.get(pos).getOrt());
                intent.putExtra("image_einsatsort", adapter.jobs.get(pos).getEinsatsort());
                intent.putExtra("image_firma", adapter.jobs.get(pos).getFirma());
                startActivity(intent);
            }

            @Override
            public void onItemLongCLick(int pos) {
                Toast.makeText(getContext(), "on long click: " + adapter.jobs.get(pos).getTitle(), Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getJobs() {
        onDataReadyListener = new App.OnDataReadyListener() {
            @Override
            public void setData(ArrayList<JobAd> jobs) {
                adapter.setJobs(App.jobAds);
                progressBar.setVisibility(View.GONE);
            }
        };
        App.setOnDataReadyListener(onDataReadyListener);
        App.parseJobs();
    }
}