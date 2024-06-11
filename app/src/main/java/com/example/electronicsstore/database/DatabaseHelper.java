package com.example.electronicsstore.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.electronicsstore.model.CartModel;
import com.example.electronicsstore.model.CategoryModel;
import com.example.electronicsstore.model.OrderModel;
import com.example.electronicsstore.model.ProductsModel;
import com.example.electronicsstore.model.UsersModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "store_app.db";
    // Columns for the Users table
    private static final String TABLE_USERS = "users_table";
    private static final String KEY_ID_USER = "user_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";
    // Columns for the Categories table
    private static final String TABLE_CATEGORIES = "users_categories";
    private static final String KEY_CATEGORIES_ID = "categories_id";
    private static final String KEY_CATEGORIES_NAME = "categories_name";
    private static final String KEY_CATEGORIES_IMAGE = "categories_image";
    // Columns for the Products table
    private static final String TABLE_PRODUCTS = "table_products";
    private static final String KEY_PRODUCTS_ID = "products_id";
    private static final String KEY_PRODUCTS_NAME = "products_name";
    private static final String KEY_PRODUCTS_PRICE = "products_price";
    private static final String KEY_PRODUCTS_OFFER_PRICE = "products_offer_price";
    private static final String KEY_PRODUCTS_QUANTITY = "products_quantity";
    private static final String KEY_PRODUCTS_DESCRIPTION = "products_description";
    private static final String KEY_PRODUCTS_IMAGE = "products_image";

    private static final String TABLE_CART = "table_cart";
    private static final String KEY_CART_ID = "cart_id";
    private static final String KEY_CART_PRODUCTS_NAME = "cart_products_name";
    private static final String KEY_CART_PRODUCTS_PRICE = "cart_products_price";
    private static final String KEY_CART_PRODUCTS_QUANTITY = "cart_products_quantity";
    private static final String KEY_CART_PRODUCTS_DESCRIPTION = "cart_products_description";
    private static final String KEY_CART_PRODUCTS_IMAGE = "cart_products_image";

    // Columns for the Orders table
    private static final String TABLE_ORDERS = "table_orders";
    private static final String KEY_ORDER_ID = "order_id";
    private static final String KEY_ORDER_USER_ID = "order_user_id";
    private static final String KEY_ORDER_USER_NAME = "order_user_name";
    private static final String KEY_ORDER_STATUS = "order_status";
    private static final String KEY_ORDER_DATE_TIME = "order_date_time";

    // Columns for the Order Products table
    private static final String TABLE_ORDER_PRODUCTS = "table_order_products";
    private static final String KEY_ORDER_PRODUCTS_ID = "order_products_id";
    private static final String KEY_ORDER_ID_FOREIGN = "order_id";
    private static final String KEY_ORDER_PRODUCTS_NAME = "order_products_name";
    private static final String KEY_ORDER_PRODUCTS_PRICE = "order_products_price";
    private static final String KEY_ORDER_PRODUCTS_QUANTITY = "order_products_quantity";
    private static final String KEY_ORDER_PRODUCTS_DESCRIPTION = "order_products_description";
    private static final String KEY_ORDER_PRODUCTS_IMAGE = "order_products_image";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create the Users table
        db.execSQL(" CREATE TABLE " + TABLE_USERS + " (" +
                KEY_ID_USER + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " TEXT NOT NULL, " +
                KEY_EMAIL + " TEXT NOT NULL, " +
                KEY_PHONE + " TEXT NOT NULL, " +
                KEY_PASSWORD + " TEXT NOT NULL);"
        );

        // Create the Categories table
        db.execSQL(" CREATE TABLE " + TABLE_CATEGORIES + " (" +
                KEY_CATEGORIES_ID + " INTEGER PRIMARY KEY, " +
                KEY_CATEGORIES_NAME + " TEXT NOT NULL, " +
                KEY_CATEGORIES_IMAGE + " TEXT NOT NULL);"
        );

        // Create the Products table
        db.execSQL(" CREATE TABLE " + TABLE_PRODUCTS + " (" +
                KEY_PRODUCTS_ID + " INTEGER PRIMARY KEY, " +
                KEY_CATEGORIES_ID + " TEXT NOT NULL, " +
                KEY_PRODUCTS_NAME + " TEXT NOT NULL, " +
                KEY_PRODUCTS_PRICE + " TEXT NOT NULL, " +
                KEY_PRODUCTS_OFFER_PRICE + " TEXT NOT NULL, " +
                KEY_PRODUCTS_QUANTITY + " TEXT NOT NULL, " +
                KEY_PRODUCTS_DESCRIPTION + " TEXT NOT NULL, " +
                KEY_PRODUCTS_IMAGE + " TEXT NOT NULL);"
        );

        // Create the Cart table
        db.execSQL(" CREATE TABLE " + TABLE_CART + " (" +
                KEY_CART_ID + " INTEGER PRIMARY KEY, " +
                KEY_ID_USER + " TEXT NOT NULL, " +
                KEY_CATEGORIES_ID + " TEXT NOT NULL, " +
                KEY_PRODUCTS_ID + " TEXT NOT NULL, " +
                KEY_CART_PRODUCTS_NAME + " TEXT NOT NULL, " +
                KEY_CART_PRODUCTS_PRICE + " TEXT NOT NULL, " +
                KEY_CART_PRODUCTS_QUANTITY + " TEXT NOT NULL, " +
                KEY_CART_PRODUCTS_DESCRIPTION + " TEXT NOT NULL, " +
                KEY_CART_PRODUCTS_IMAGE + " TEXT NOT NULL);"
        );

        // Create the Orders table
        db.execSQL(" CREATE TABLE " + TABLE_ORDERS + " (" +
                KEY_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_ORDER_USER_ID + " INTEGER NOT NULL, " +
                KEY_ORDER_USER_NAME + " TEXT NOT NULL, " +
                KEY_ORDER_STATUS + " TEXT NOT NULL, " +
                KEY_ORDER_DATE_TIME + " TEXT NOT NULL);"
        );

        // Create the Order Products table
        db.execSQL(" CREATE TABLE " + TABLE_ORDER_PRODUCTS + " (" +
                KEY_ORDER_PRODUCTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_ORDER_ID_FOREIGN + " INTEGER NOT NULL, " +
                KEY_ORDER_PRODUCTS_NAME + " TEXT NOT NULL, " +
                KEY_ORDER_PRODUCTS_PRICE + " TEXT NOT NULL, " +
                KEY_ORDER_PRODUCTS_QUANTITY + " TEXT NOT NULL, " +
                KEY_ORDER_PRODUCTS_DESCRIPTION + " TEXT NOT NULL, " +
                KEY_ORDER_PRODUCTS_IMAGE + " TEXT NOT NULL, " +
                "FOREIGN KEY (" + KEY_ORDER_ID_FOREIGN + ") REFERENCES " + TABLE_ORDERS + "(" + KEY_ORDER_ID + "));"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_PRODUCTS);
        // Recreate the tables
        onCreate(db);
    }

    public void register(UsersModel users) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, users.getUserName());
        values.put(KEY_EMAIL, users.getEmail());
        values.put(KEY_PHONE, users.getPhone());
        values.put(KEY_PASSWORD, users.getPassword());

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public List<UsersModel> getAllUsers() {
        List<UsersModel> usersList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                UsersModel users = new UsersModel();
                users.setId(Integer.parseInt(cursor.getString(0)));
                users.setUserName(cursor.getString(1));
                users.setEmail(cursor.getString(2));
                users.setPhone(cursor.getString(3));
                users.setPassword(cursor.getString(4));

                usersList.add(users);
            } while (cursor.moveToNext());
        }

        return usersList;
    }

    public void updateUser(UsersModel user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getUserName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PHONE, user.getPhone());
        values.put(KEY_PASSWORD, user.getPassword());

        db.update(TABLE_USERS, values, KEY_ID_USER + " = ?", new String[]{String.valueOf(user.getId())});

        db.close();
    }

    public void insertCategory(CategoryModel model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORIES_NAME, model.getName());
        values.put(KEY_CATEGORIES_IMAGE, model.getImage());

        db.insert(TABLE_CATEGORIES, null, values);
        db.close();
    }

    public List<CategoryModel> getAllCategories() {
        List<CategoryModel> categoryList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CATEGORIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CategoryModel model = new CategoryModel();
                model.setId(Integer.parseInt(cursor.getString(0)));
                model.setName(cursor.getString(1));
                model.setImage(cursor.getString(2));

                categoryList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return categoryList;
    }

    public void insertProduct(ProductsModel model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORIES_ID, model.getCategoryId());
        values.put(KEY_PRODUCTS_NAME, model.getName());
        values.put(KEY_PRODUCTS_PRICE, model.getOriginalPrice());
        values.put(KEY_PRODUCTS_OFFER_PRICE, model.getOfferPrice());
        values.put(KEY_PRODUCTS_QUANTITY, model.getQuantity());
        values.put(KEY_PRODUCTS_DESCRIPTION, model.getDescription());
        values.put(KEY_PRODUCTS_IMAGE, model.getImageUri());

        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public List<ProductsModel> getProductsByCategory(int categoryId) {
        List<ProductsModel> productList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS +
                " WHERE " + KEY_CATEGORIES_ID + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(categoryId)});

        if (cursor.moveToFirst()) {
            do {
                ProductsModel product = new ProductsModel();
                product.setId(Integer.parseInt(cursor.getString(0)));
                product.setCategoryId(Integer.parseInt(cursor.getString(1)));
                product.setName(cursor.getString(2));
                product.setOriginalPrice(cursor.getString(3));
                product.setOfferPrice(cursor.getString(4));
                product.setQuantity(cursor.getString(5));
                product.setDescription(cursor.getString(6));
                product.setImageUri(cursor.getString(7));

                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return productList;
    }

    public List<ProductsModel> getAllProductsWithOffer() {
        List<ProductsModel> productList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS +
                " WHERE " + KEY_PRODUCTS_OFFER_PRICE + " <> ''"; // Add this condition

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProductsModel product = new ProductsModel();
                product.setId(Integer.parseInt(cursor.getString(0)));
                product.setCategoryId(Integer.parseInt(cursor.getString(1)));
                product.setName(cursor.getString(2));
                product.setOriginalPrice(cursor.getString(3));
                product.setOfferPrice(cursor.getString(4));
                product.setQuantity(cursor.getString(5));
                product.setDescription(cursor.getString(6));
                product.setImageUri(cursor.getString(7));

                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return productList;
    }

    public ProductsModel getProductByIdAndCategory(int productId, int categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS +
                " WHERE " + KEY_PRODUCTS_ID + " = ?" +
                " AND " + KEY_CATEGORIES_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(productId), String.valueOf(categoryId)});

        ProductsModel product = null;

        if (cursor.moveToFirst()) {
            product = new ProductsModel();
            product.setId(Integer.parseInt(cursor.getString(0)));
            product.setCategoryId(Integer.parseInt(cursor.getString(1)));
            product.setName(cursor.getString(2));
            product.setOriginalPrice(cursor.getString(3));
            product.setOfferPrice(cursor.getString(4));
            product.setQuantity(cursor.getString(5));
            product.setDescription(cursor.getString(6));
            product.setImageUri(cursor.getString(7));
        }

        cursor.close();
        db.close();

        return product;
    }


    public void deleteCategoryAndProducts(int categoryId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete products associated with the category
        db.delete(TABLE_PRODUCTS, KEY_CATEGORIES_ID + " = ?", new String[]{String.valueOf(categoryId)});

        // Delete the category
        db.delete(TABLE_CATEGORIES, KEY_CATEGORIES_ID + " = ?", new String[]{String.valueOf(categoryId)});

        db.close();
    }

    public void updateProduct(ProductsModel model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORIES_ID, model.getCategoryId());
        values.put(KEY_PRODUCTS_NAME, model.getName());
        values.put(KEY_PRODUCTS_PRICE, model.getOriginalPrice());
        values.put(KEY_PRODUCTS_OFFER_PRICE, model.getOfferPrice());
        values.put(KEY_PRODUCTS_QUANTITY, model.getQuantity());
        values.put(KEY_PRODUCTS_DESCRIPTION, model.getDescription());
        values.put(KEY_PRODUCTS_IMAGE, model.getImageUri());

        // Update the row where KEY_PRODUCTS_ID matches the ID of the ProductsModel
        db.update(TABLE_PRODUCTS, values, KEY_PRODUCTS_ID + " = ?", new String[]{String.valueOf(model.getId())});

        db.close();
    }

    public void deleteProduct(int productId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete the row where KEY_PRODUCTS_ID matches productId
        db.delete(TABLE_PRODUCTS, KEY_PRODUCTS_ID + " = ?", new String[]{String.valueOf(productId)});

        db.close();
    }

    public void insertCart(CartModel model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_USER, model.getUserId());
        values.put(KEY_CATEGORIES_ID, model.getCategoryId());
        values.put(KEY_PRODUCTS_ID, model.getProductId());
        values.put(KEY_CART_PRODUCTS_NAME, model.getName());
        values.put(KEY_CART_PRODUCTS_PRICE, model.getPrice());
        values.put(KEY_CART_PRODUCTS_QUANTITY, model.getQuantity());
        values.put(KEY_CART_PRODUCTS_DESCRIPTION, model.getDescription());
        values.put(KEY_CART_PRODUCTS_IMAGE, model.getImageUri());

        db.insert(TABLE_CART, null, values);
        db.close();
    }

    public List<CartModel> getCartData(int userId) {
        List<CartModel> journalList = new ArrayList<>();

        // Use placeholders for the parameters to avoid SQL injection
        String selectQuery = "SELECT * FROM " + TABLE_CART +
                " WHERE " + KEY_ID_USER + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                CartModel model = new CartModel();
                model.setId(Integer.parseInt(cursor.getString(0)));
                model.setUserId(Integer.parseInt(cursor.getString(1)));
                model.setCategoryId(Integer.parseInt(cursor.getString(2)));
                model.setProductId(Integer.parseInt(cursor.getString(3)));
                model.setName(cursor.getString(4));
                model.setPrice(cursor.getString(5));
                model.setQuantity(cursor.getString(6));
                model.setDescription(cursor.getString(7));
                model.setImageUri(cursor.getString(8));

                journalList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close(); // Close the cursor to avoid potential memory leaks
        return journalList;
    }

    public void updateCart(CartModel model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_USER, model.getUserId());
        values.put(KEY_CATEGORIES_ID, model.getCategoryId());
        values.put(KEY_PRODUCTS_ID, model.getProductId());
        values.put(KEY_CART_PRODUCTS_NAME, model.getName());
        values.put(KEY_CART_PRODUCTS_PRICE, model.getPrice());
        values.put(KEY_CART_PRODUCTS_QUANTITY, model.getQuantity());
        values.put(KEY_CART_PRODUCTS_DESCRIPTION, model.getDescription());
        values.put(KEY_CART_PRODUCTS_IMAGE, model.getImageUri());

        // Update the row where KEY_PRODUCTS_ID matches the ID of the ProductsModel
        db.update(TABLE_CART, values, KEY_CART_ID + " = ?", new String[]{String.valueOf(model.getId())});

        db.close();
    }

    public void deleteCartData(int cartId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete the row where KEY_PRODUCTS_ID matches productId
        db.delete(TABLE_CART, KEY_CART_ID + " = ?", new String[]{String.valueOf(cartId)});

        db.close();
    }

    public void insertOrder(OrderModel order) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues orderValues = new ContentValues();
        orderValues.put(KEY_ORDER_USER_ID, order.getUserId());
        orderValues.put(KEY_ORDER_USER_NAME, order.getUserName());
        orderValues.put(KEY_ORDER_STATUS, order.getStatus());
        orderValues.put(KEY_ORDER_DATE_TIME, order.getDateTime());

        long orderId = db.insert(TABLE_ORDERS, null, orderValues);

        for (ProductsModel product : order.getProductList()) {
            ContentValues productValues = new ContentValues();
            productValues.put(KEY_ORDER_ID_FOREIGN, orderId);
            productValues.put(KEY_ORDER_PRODUCTS_NAME, product.getName());
            productValues.put(KEY_ORDER_PRODUCTS_PRICE, product.getOriginalPrice());
            productValues.put(KEY_ORDER_PRODUCTS_QUANTITY, product.getQuantity());
            productValues.put(KEY_ORDER_PRODUCTS_DESCRIPTION, product.getDescription());
            productValues.put(KEY_ORDER_PRODUCTS_IMAGE, product.getImageUri());

            db.insert(TABLE_ORDER_PRODUCTS, null, productValues);
        }

        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<OrderModel> getOrdersByUserId(int userId) {
        ArrayList<OrderModel> orderList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_ORDERS + " WHERE " + KEY_ORDER_USER_ID + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                OrderModel order = new OrderModel();
                order.setOrderId(cursor.getString(cursor.getColumnIndex(KEY_ORDER_ID)));
                order.setUserId(cursor.getString(cursor.getColumnIndex(KEY_ORDER_USER_ID)));
                order.setUserName(cursor.getString(cursor.getColumnIndex(KEY_ORDER_USER_NAME)));
                order.setStatus(cursor.getString(cursor.getColumnIndex(KEY_ORDER_STATUS)));
                order.setDateTime(cursor.getString(cursor.getColumnIndex(KEY_ORDER_DATE_TIME)));

                String productSelectQuery = "SELECT * FROM " + TABLE_ORDER_PRODUCTS + " WHERE " + KEY_ORDER_ID_FOREIGN + " = ?";
                Cursor productCursor = db.rawQuery(productSelectQuery, new String[]{cursor.getString(cursor.getColumnIndex(KEY_ORDER_ID))});
                ArrayList<ProductsModel> productList = new ArrayList<>();

                if (productCursor.moveToFirst()) {
                    do {
                        ProductsModel product = new ProductsModel();
                        product.setId(productCursor.getInt(productCursor.getColumnIndex(KEY_ORDER_PRODUCTS_ID)));
                        product.setName(productCursor.getString(productCursor.getColumnIndex(KEY_ORDER_PRODUCTS_NAME)));
                        product.setOriginalPrice(productCursor.getString(productCursor.getColumnIndex(KEY_ORDER_PRODUCTS_PRICE)));
                        product.setQuantity(productCursor.getString(productCursor.getColumnIndex(KEY_ORDER_PRODUCTS_QUANTITY)));
                        product.setDescription(productCursor.getString(productCursor.getColumnIndex(KEY_ORDER_PRODUCTS_DESCRIPTION)));
                        product.setImageUri(productCursor.getString(productCursor.getColumnIndex(KEY_ORDER_PRODUCTS_IMAGE)));

                        productList.add(product);
                    } while (productCursor.moveToNext());
                }
                productCursor.close();

                order.setProductList(productList);
                orderList.add(order);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return orderList;
    }

    @SuppressLint("Range")
    public ArrayList<OrderModel> getAllOrders() {
        ArrayList<OrderModel> orderList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_ORDERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                OrderModel order = new OrderModel();
                order.setOrderId(cursor.getString(cursor.getColumnIndex(KEY_ORDER_ID)));
                order.setUserId(cursor.getString(cursor.getColumnIndex(KEY_ORDER_USER_ID)));
                order.setUserName(cursor.getString(cursor.getColumnIndex(KEY_ORDER_USER_NAME)));
                order.setStatus(cursor.getString(cursor.getColumnIndex(KEY_ORDER_STATUS)));
                order.setDateTime(cursor.getString(cursor.getColumnIndex(KEY_ORDER_DATE_TIME)));

                String productSelectQuery = "SELECT * FROM " + TABLE_ORDER_PRODUCTS + " WHERE " + KEY_ORDER_ID_FOREIGN + " = ?";
                Cursor productCursor = db.rawQuery(productSelectQuery, new String[]{cursor.getString(cursor.getColumnIndex(KEY_ORDER_ID))});
                ArrayList<ProductsModel> productList = new ArrayList<>();

                if (productCursor.moveToFirst()) {
                    do {
                        ProductsModel product = new ProductsModel();
                        product.setId(productCursor.getInt(productCursor.getColumnIndex(KEY_ORDER_PRODUCTS_ID)));
                        product.setName(productCursor.getString(productCursor.getColumnIndex(KEY_ORDER_PRODUCTS_NAME)));
                        product.setOriginalPrice(productCursor.getString(productCursor.getColumnIndex(KEY_ORDER_PRODUCTS_PRICE)));
                        product.setQuantity(productCursor.getString(productCursor.getColumnIndex(KEY_ORDER_PRODUCTS_QUANTITY)));
                        product.setDescription(productCursor.getString(productCursor.getColumnIndex(KEY_ORDER_PRODUCTS_DESCRIPTION)));
                        product.setImageUri(productCursor.getString(productCursor.getColumnIndex(KEY_ORDER_PRODUCTS_IMAGE)));

                        productList.add(product);
                    } while (productCursor.moveToNext());
                }
                productCursor.close();

                order.setProductList(productList);
                orderList.add(order);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return orderList;
    }

    public void updateOrder(OrderModel order) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues orderValues = new ContentValues();
        orderValues.put(KEY_ORDER_USER_ID, order.getUserId());
        orderValues.put(KEY_ORDER_USER_NAME, order.getUserName());
        orderValues.put(KEY_ORDER_STATUS, order.getStatus());
        orderValues.put(KEY_ORDER_DATE_TIME, order.getDateTime());

        db.update(TABLE_ORDERS, orderValues, KEY_ORDER_ID + " = ?", new String[]{String.valueOf(order.getOrderId())});

        // Update associated products
        for (ProductsModel product : order.getProductList()) {
            ContentValues productValues = new ContentValues();
            productValues.put(KEY_ORDER_PRODUCTS_NAME, product.getName());
            productValues.put(KEY_ORDER_PRODUCTS_PRICE, product.getOriginalPrice());
            productValues.put(KEY_ORDER_PRODUCTS_QUANTITY, product.getQuantity());
            productValues.put(KEY_ORDER_PRODUCTS_DESCRIPTION, product.getDescription());
            productValues.put(KEY_ORDER_PRODUCTS_IMAGE, product.getImageUri());

            db.update(TABLE_ORDER_PRODUCTS, productValues, KEY_ORDER_PRODUCTS_ID + " = ?", new String[]{String.valueOf(product.getId())});
        }

        db.close();
    }


}