package com.filipkesteli.templatesqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Filip on 22.6.2016..
 */
public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB.db";
    private static final String TABLE_PRODUCTS = "products";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCT_NAME = "productname";
    public static final String COLUMN_QUANTITY = "quantity";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Prvi put kad se kreira baza podataka -> Defaultna vrijednost baze podataka -> Pocetno stanje
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_PRODUCTS_TABLE =
                "CREATE TABLE " + TABLE_PRODUCTS
                        + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY, "
                        + COLUMN_PRODUCT_NAME + " TEXT, "
                        + COLUMN_QUANTITY + " INTEGER"
                        + ");";
    }

    /**
     * Svaki sljedeci put, poveca se vrijednost verzije baze podataka
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(sqLiteDatabase);
    }

    /**
     * Dodaj produkt metoda -> Dodaje se vrijednost unutar baze podataka
     */
    public void addProduct(Product product) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUCT_NAME, product.get_productName());

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(TABLE_PRODUCTS, null, contentValues);
        sqLiteDatabase.close();
    }

    /**
     * Vrsta QUERY-ja -> Pronađi produkt -> Jedan od SELECT statementa
     */
    public Product findProduct(String productName) {
        String query = "SELECT * FROM " + TABLE_PRODUCTS
                + " WHERE " + COLUMN_PRODUCT_NAME + " = " + productName;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        Product product = new Product();
        if (cursor.moveToFirst() == true) {
            cursor.moveToFirst();
            product.set_id(Integer.parseInt(cursor.getString(0)));
            product.set_productName(cursor.getString(1));
            product.set_quantity(Integer.parseInt(cursor.getString(2)));
            cursor.close();
        } else {
            product = null;
        }
        sqLiteDatabase.close();
        return product;
    }

    /**
     * Vrsta QUERY-ja -> Izbrisi product
     */
    public boolean deleteProduct(String productName) {
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_PRODUCTS
                + " WHERE " + COLUMN_PRODUCT_NAME + " = " + productName;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        Product product = new Product();
        if (cursor.moveToFirst()) {
            product.set_id(Integer.parseInt(cursor.getString(0)));
            sqLiteDatabase.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?", new String[]{String.valueOf(product.get_id())});
            cursor.close();
            result = true;
        }
        sqLiteDatabase.close();
        return result;
    }
}
