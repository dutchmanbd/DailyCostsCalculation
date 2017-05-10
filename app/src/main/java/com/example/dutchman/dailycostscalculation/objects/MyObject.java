package com.example.dutchman.dailycostscalculation.objects;

import com.example.dutchman.dailycostscalculation.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dutchman on 2/15/17.
 */

public class MyObject {


    public class DB{

        public static final String NAME = "products.db";
        public static final int VERSION = 1;

        public class TB{

            public class PRODUCT_INFO{

                public static final String NAME = "productInfo";

                public static final String KEY_ID = "id";
                public static final String KEY_DATE = "date";
                public static final String KEY_TIME = "time";
                public static final String KEY_MONTH = "month";
                public static final String KEY_YEAR = "year";
                public static final String KEY_CATEGORY = "category";
                public static final String KEY_PRICE = "price";


                //Table create
                public static final String CREATE_TB = "CREATE TABLE " + NAME + "(" +
                        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        KEY_MONTH + " TEXT NOT NULL, " +
                        KEY_YEAR + " TEXT NOT NULL, " +
                        KEY_DATE + " DATETIME NOT NULL," +
                        KEY_TIME + " DATETIME NOT NULL," +
                        KEY_CATEGORY + " TEXT NOT NULL," +
                        KEY_PRICE + " DOUBLE NOT NULL" +
                        ");";


                //Table Drop

                public static final String DROP_TB = "DROP TABLE IF EXISTS "+NAME;


                // Select Query
                public static final String SELECT_SQL = "SELECT * FROM "+NAME+" WHERE 1";

                public static final String SELECT_SQL_MONTH_YEAR = "SELECT * FROM "+NAME+" WHERE "+KEY_MONTH+" = ? AND "+KEY_YEAR+" = ?";

                public static final String SELECT_SQL_MONTHS = "SELECT DISTINCT "+KEY_MONTH+" FROM "+NAME+" WHERE "+KEY_YEAR+" = ?";


                public static final String SELECT_SQL_YEARS = "SELECT DISTINCT "+KEY_YEAR+" FROM "+NAME+" WHERE 1";

                //Select Specefic date

                public static final String SELECT_SPECEFIC_DATE = "SELECT SUM("+KEY_PRICE+") FROM "+NAME+" WHERE "+KEY_DATE+" = ?";


            }

            public class PRICE_INFO{

                public static final String NAME = "priceinfo";

                public static final String KEY_ID = "id";
                public static final String KEY_DATE = "date";
                public static final String KEY_MONTH = "month";
                public static final String KEY_YEAR = "year";
                public static final String KEY_PRICE = "price";


                //Table create
                public static final String CREATE_TB = "CREATE TABLE " + NAME + "(" +
                        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        KEY_MONTH + " TEXT NOT NULL, " +
                        KEY_YEAR + " TEXT NOT NULL, " +
                        KEY_DATE + " DATETIME NOT NULL," +
                        KEY_PRICE + " DOUBLE NOT NULL" +
                        ");";

                //Table Drop

                public static final String DROP_TB = "DROP TABLE IF EXISTS "+NAME;


                // Select Query
                public static final String SELECT_SQL = "SELECT * FROM "+NAME+" WHERE 1";

                // Select Query
                public static final String SELECT_SQL_MONTH_YEAR = "SELECT SUM("+KEY_PRICE+") FROM "+NAME+" WHERE "+KEY_MONTH+" = ? AND "+KEY_YEAR+" = ?" ;

                //Select Specefic date

                public static final String SELECT_SPECEFIC_DATE = "SELECT SUM("+KEY_PRICE+") FROM "+NAME+" WHERE "+KEY_DATE+" = ?";


                //IS DATE EXISTS OR NOT
                public static final String SELECT_IS_DATE_EXIST = "SELECT "+KEY_DATE+" FROM "+NAME+" WHERE "+KEY_DATE+" = ?";

                //Update cmmand
                public static final String UPDATE_SQL = "";

            }


            public class CATAGORY_INFO{

                public static final String NAME = "catagory_info";

                public static final String KEY_ID = "id";
                public static final String KEY_NAME = "catagory_name";


                //Table create
                public static final String CREATE_TB = "CREATE TABLE " + NAME + "(" +
                        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        KEY_NAME + " TEXT NOT NULL UNIQUE" +
                        ");";


                //Table Drop

                public static final String DROP_TB = "DROP TABLE IF EXISTS "+NAME;


                // Select Query
                public static final String SELECT_SQL = "SELECT * FROM "+NAME+" WHERE 1";

            }


        }

    }


    public static final String[] months = new String[]{"Select Month","January","February","March","April","May","June",
                                                        "July","August","September","October","November","December"};

    public static final List<String> monthList = new ArrayList<>(Arrays.asList(months));

    public static final List<String> yearList = new ArrayList<>(Arrays.asList(getYearList()));

    private static String[] getYearList(){

        String[] years = new String[100];
        years[0] = "Select Year";

        for(int i=1,year=2015; year<=2100; i++,year++){

            years[i] = year+"";
        }

        return years;
    }



}
