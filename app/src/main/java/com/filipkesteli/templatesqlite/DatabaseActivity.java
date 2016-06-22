package com.filipkesteli.templatesqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DatabaseActivity extends AppCompatActivity {

    private EditText etProductID;
    private EditText etProductName;
    private EditText etProductQuantity;
    private Button btnAddProduct;
    private Button btnLookupProduct;
    private Button btnDeleteProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        initWidgets();
        setupListeners();
    }

    private void initWidgets() {
        etProductID = (EditText) findViewById(R.id.etProductID);
        etProductName = (EditText) findViewById(R.id.etProductName);
        etProductQuantity = (EditText) findViewById(R.id.etProductQuality);
        btnAddProduct = (Button) findViewById(R.id.btnAddProduct);
        btnLookupProduct = (Button) findViewById(R.id.btnLookupProduct);
        btnDeleteProduct = (Button) findViewById(R.id.btnDeleteProduct);
    }

    private void setupListeners() {
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDBHandler myDBHandler = new MyDBHandler(getApplicationContext(), null, null, 1);
                String productName = etProductName.getText().toString();
                int productQuantity = Integer.parseInt(etProductQuantity.getText().toString());
                Product product = new Product(productName, productQuantity);
                myDBHandler.addProduct(product);
                // TODO: 22.6.2016. Dodati metodu za dodavanje na listu RecyclerView-a
                etProductQuantity.setText("");
                etProductName.setText("");
            }
        });
        btnLookupProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDBHandler myDBHandler = new MyDBHandler(getApplicationContext(), null, null, 1);
                Product product = myDBHandler.findProduct(etProductName.getText().toString());
                if (product != null) {
                    etProductID.setText(String.valueOf(product.get_id()));
                    etProductQuantity.setText(String.valueOf(product.get_quantity()));
                } else {
                    etProductID.setText("No match found");
                }
            }
        });
        btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDBHandler myDBHandler = new MyDBHandler(getApplicationContext(), null, null, 1);
            }
        });
    }
}

