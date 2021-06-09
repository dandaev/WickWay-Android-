package com.example.wikway;

import android.app.Application;
import android.os.AsyncTask;

import com.example.wikway.ui.saved.FavoriteDbHelper;
import com.example.wikway.utils.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class App extends Application {
    //Favorites DB
    public static FavoriteDbHelper favoriteDbHelper;
    public static ArrayList<JobAd> jobFavs;


    private static final String API_WIKWAY = "https://raw.githubusercontent.com/dandaev/json/master/.mockend.json";

    public static ArrayList<JobAd> jobAds;
    public static InitImageBitmaps initialization;

    private static boolean loading = false;
    public static OnDataReadyListener onDataReadyListener;

    public static ArrayList<String> listOfBundesland = new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static void setOnDataReadyListener(OnDataReadyListener listener) {
        onDataReadyListener = listener;
    }


    public static void parseJobs(){
        if (jobAds == null || jobAds.size() == 0) {
            jobAds = new ArrayList<>();
            if (initialization == null) {
                initialization = new InitImageBitmaps();
            }
            if (!loading) {
                loading = true;
                initialization.execute();
            }

        } else {
            onDataReadyListener.setData(jobAds);
        }
    }
    public static class InitImageBitmaps extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response

            String jsonStr = sh.makeServiceCall(API_WIKWAY);
            if (jsonStr != null) {

                try {
                    //TODO(2) MAKE THEN THIS
                    JSONArray companies = new JSONArray(jsonStr);
                    String deadline;
                    for (int i = 0; i < companies.length(); i++) {
                        JSONObject c = companies.getJSONObject(i);
                        String jobName = c.getString("Bezeichnung der Stelle");
                        String logo = c.getString("Logo");
                        String bundesland = c.getString("Bundesland");
                        String ort = c.getString("Ort");
                        String anschreiben = c.getString("Anschreiben zur Stelle");
                        String abteilung = c.getString("Abteilung");
                        String qualifizirung = c.getString("Fachrichtung");
                        String srasse = c.getString("Straße");
                        String einsatsort = c.getString("Einsatzort");
                        String artDerStelle = c.getString("Art der Stelle");
                        String email = c.getString("E-Mail");
                        String firma = c.getString("Bewerberkontakt Firma");
//                        String aufgabenGebiet = c.getString("AufgabenGebiet");
                        if(c.getString("Stelle aktiv bis (Publikationsende)").isEmpty()){ deadline = "--.--.----";}
                        else{
                            deadline = c.getString("Stelle aktiv bis (Publikationsende)");}

                        if (bundesland != null && !listOfBundesland.contains(bundesland)) {
                            listOfBundesland.add(bundesland);
                        }
                        jobAds.add(new JobAd(jobName,logo,
                                bundesland,ort,anschreiben,
                                abteilung,qualifizirung,srasse,
                                einsatsort,deadline,artDerStelle,email,firma));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("here");
                }
            }


            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (onDataReadyListener != null) onDataReadyListener.setData(jobAds);
            loading = false;
        }
    }

    public interface OnDataReadyListener {
        void setData (ArrayList<JobAd> jobs);
    }

    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }


}
