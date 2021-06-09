package com.example.wikway.ui.saved;

import android.provider.BaseColumns;

/**
 * Created by uns
 */
public class FavoriteContract {

    public static final class FavoriteEntry implements BaseColumns {

        public static final String TABLE_NAME = "favoriteSQL";
        public static final String COLUMN_JOBNAME = "jobNameSQL";
        public static final String COLUMN_IMAGEURL = "imageUrlSQL";
        public static final String COLUMN_BUNDESLAND = "bundeslandSQL";
        public static final String COLUMN_ORT = "ortSQL";
        public static final String COLUMN_ANSCHREIBEN = "anschreibenSQL";
        public static final String COLUMN_ABTEILUNG = "abteilungSQL";
        public static final String COLUMN_QUALIFIZIRUNG = "qualifizirungSQL";
        public static final String COLUMN_STRASSE = "strasseSQL";
        public static final String COLUMN_ANFORDERUNG= "anforderungSQL";
        public static final String COLUMN_DEADLINE= "deadlineSQL";
        public static final String COLUMN_ARTDERSTELLE= "artderstelleSQL";
        public static final String COLUMN_EMAIL= "emailSQL";





    }
}
