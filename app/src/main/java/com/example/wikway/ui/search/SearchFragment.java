package com.example.wikway.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikway.App;
import com.example.wikway.JobAd;
import com.example.wikway1.R;
import com.example.wikway.ui.VacancyAdapter;
import com.example.wikway.ui.home.GalleryActivity;
import com.example.wikway.utils.OnItemClickListener;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    private EditText searchEditText;
    private Spinner spinner;
    private Button button;
    private RecyclerView rv;
    private VacancyAdapter vacancyAdapter;
    private App.OnDataReadyListener onDataReadyListener;

    private ArrayAdapter<String> bundeslandAdapter;

    private ArrayList<String> listOfBundesland;
    private ArrayList<JobAd> allJobs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

       View v = inflater.inflate(R.layout.fragment_search, container, false);
        initViews(v);
        getJobs();
       return v;
    }

    private void initViews(View v) {
        searchEditText = v.findViewById(R.id.search_input);
        spinner = v.findViewById(R.id.category_spinner);
        button = v.findViewById(R.id.search_button);
        rv = v.findViewById(R.id.rv);
        listOfBundesland = new ArrayList<>();
        bundeslandAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, listOfBundesland);
        spinner.setAdapter(bundeslandAdapter);
        initRV();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchEditText.getText().toString();
                if (spinner.getSelectedItem() != null)
                search(searchText, listOfBundesland.get(spinner.getSelectedItemPosition()));
                else {
                    search(searchText, "");
                }
            }
        });
    }

    private void initRV() {
        vacancyAdapter = new VacancyAdapter(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(vacancyAdapter);
        vacancyAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(getContext(), GalleryActivity.class);
                intent.putExtra("image_url", vacancyAdapter.jobs.get(pos).getImageLink());
                intent.putExtra("image_name", vacancyAdapter.jobs.get(pos).getTitle());
                intent.putExtra("image_artDerStelle", vacancyAdapter.jobs.get(pos).getArtDerStelle());
                intent.putExtra("image_anschreiben", vacancyAdapter.jobs.get(pos).getAnschreiben());
                intent.putExtra("image_abteilung", vacancyAdapter.jobs.get(pos).getAbteilung());
                intent.putExtra("image_qualifizirung", vacancyAdapter.jobs.get(pos).getQualifizirung());
                intent.putExtra("image_einsatsort", vacancyAdapter.jobs.get(pos).getAnforderung());
                intent.putExtra("image_strasse", vacancyAdapter.jobs.get(pos).getSrasse());
                intent.putExtra("image_email", vacancyAdapter.jobs.get(pos).getEmail());
                intent.putExtra("image_ort", vacancyAdapter.jobs.get(pos).getOrt());
                intent.putExtra("image_firma", vacancyAdapter.jobs.get(pos).getFirma());
                startActivity(intent);
            }

            @Override
            public void onItemLongCLick(int pos) {
                Toast.makeText(getContext(), "on long click: " + vacancyAdapter.jobs.get(pos).getTitle(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getJobs() {
        onDataReadyListener = new App.OnDataReadyListener() {
            @Override
            public void setData(ArrayList<JobAd> jobs) {
                allJobs = jobs;
                listOfBundesland.addAll(App.listOfBundesland);
                bundeslandAdapter.notifyDataSetChanged();
            }
        };
        App.setOnDataReadyListener(onDataReadyListener);
        App.parseJobs();
    }

    private void search(@NonNull String searchTerm, @NonNull String bundesland) {
        ArrayList<JobAd> filteredJobs = new ArrayList<>();
        if (!searchTerm.isEmpty() && !bundesland.isEmpty()){
            for (JobAd job : allJobs) {
                if (job.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) && job.getBundesland().equals(bundesland)) {
                    filteredJobs.add(job);
                }
            }
        }else if (searchTerm.isEmpty()) {
            for (JobAd job : allJobs) {
                if (job.getBundesland().equals(bundesland)) {
                    filteredJobs.add(job);
                }
            }
        } else {
            for (JobAd job : allJobs) {
                if (job.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                    filteredJobs.add(job);
                }
            }
        }

        vacancyAdapter.setJobs(filteredJobs);
        }
}