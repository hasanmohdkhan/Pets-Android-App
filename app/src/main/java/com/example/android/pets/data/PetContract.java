package com.example.android.pets.data;

import android.provider.BaseColumns;

/**
 * Created by hasanZian on 25-01-2018.
 */

public class PetContract {

    //private constructor
    private PetContract() {

    }

         /* Subclass for pets entry */
         public static final class PetsEntry implements BaseColumns {

               //table Name
                 public static final String TABLE_NAME = "pets";
              //Column name
                 public static final String _ID = BaseColumns._ID;
                 public static final String COLUMN_PET_NAME = "name";
                 public static final String COLUMN_PET_BREED = "breed";
                 public static final String COLUMN_PET_GENDER = "gender";
                 public static final String COLUMN_PET_WEIGHT = "weight";

                  // values for default in gender
                  public static final int GENDER_UNKNOWN = 0;
                  public static final int GENDER_MALE = 1;
                  public static final int GENDER_FEMALE = 2;



         }



}
