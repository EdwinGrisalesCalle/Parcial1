package com.egc.parcial1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ib.custom.toast.CustomToastView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Register_Product extends AppCompatActivity implements View.OnClickListener {

    private EditText txtCode;
    private EditText txtName;
    private EditText txtValue;
    private Spinner spIva;
    private EditText txtDescription;
    private Spinner spCategory;
    private Button btnSave;
    private Button btnReturn;
    static List<Product> productList = new ArrayList<>();
    private Product product1 = new Product("1","1", (float) 1,true,"1","Bebidas");
    private Product product2 = new Product("2","2",(float)2,false,"2","Granos");
    private Product product3 = new Product("3","3",(float)3,true,"3","Verduras");
    private Product product4 = new Product("4","4",(float)4,false,"4","Aseo Personal");
    private Product product5 = new Product("5","5",(float)5,true,"5","Abarrotes");
    private Product product6 = new Product("6","6",(float)6,false,"6","Abarrotes");
    private Product product7 = new Product("7","7",(float)7,true,"7","Abarrotes");
    private Product product8 = new Product("8","8",(float)8,false,"8","Abarrotes");
    private Product product9 = new Product("9","9",(float)9,true,"9","Verduras");
    private Product product10 = new Product("10","10",(float)10,true,"10","Bebidas");
    private Product product11 = new Product("11","11",(float)11,false,"11","Verduras");
    private Product product12 = new Product("12","12",(float)12,true,"12","Bebidas");
    private Product product13 = new Product("13","13",(float)13,false,"13","Aseo Personal");
    private Product product14 = new Product("14","14",(float)14,true,"14","Bebidas");
    private Product product15 = new Product("15","15",(float)15,true,"15","Verduras");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__product);
        List<String> categoryList =(List<String>) getIntent().getSerializableExtra("categoryList");
        //List<Product> listEmp =(List<Product>) getIntent().getSerializableExtra("list");
        productList.add(product1);productList.add(product8);
        productList.add(product2);productList.add(product9);
        productList.add(product3);productList.add(product10);
        productList.add(product4);productList.add(product11);
        productList.add(product5);productList.add(product12);
        productList.add(product6);productList.add(product13);
        productList.add(product7);productList.add(product14);
        productList.add(product15);

        txtCode = findViewById(R.id.txtCode);
        txtName = findViewById(R.id.txtName);
        txtValue = findViewById(R.id.txtCode);
        txtDescription = findViewById(R.id.txtDescription);

        spIva = findViewById(R.id.spIva);
        ArrayAdapter<CharSequence> adapterIva = ArrayAdapter.createFromResource(this,R.array.spIva,R.layout.support_simple_spinner_dropdown_item);
        adapterIva.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spIva.setAdapter(adapterIva);

        spCategory = findViewById(R.id.spCategory);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,categoryList);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        btnSave = findViewById(R.id.btnSave);
        btnReturn = findViewById(R.id.btnReturn);
        btnSave.setOnClickListener(this);
        btnReturn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
            if (v.getId() == R.id.btnSave){
                if (txtCode.getText().toString().isEmpty()|| txtName.getText().toString().isEmpty()||
                txtValue.getText().toString().isEmpty()||txtDescription.getText().toString().isEmpty()){
                    CustomToastView.makeErrorToast(this, "Llene todos los campos", R.layout.custom_toast).show();

                }else{
                    addEmploye();
                    CustomToastView.makeSuccessToast(this, "Empleado Guardado", R.layout.custom_toast).show();

                }
            }else if(v.getId() == R.id.btnReturn){
                Intent intentList = new Intent(this, MainActivity.class);
                intentList.putExtra("listProduct", (Serializable) productList);
                startActivity(intentList);
            }

    }

    private void addEmploye() {
        Product newProduct = new Product(txtCode.getText().toString(),txtName.getText().toString(), Float.parseFloat(txtValue.getText().toString()),
                spIva.getSelectedItemId()==0?true:false,txtDescription.getText().toString(),spCategory.getSelectedItem().toString());
        productList.add(newProduct);
        clearFields();
    }

    public void clearFields(){
        txtCode.setText("");
        txtName.setText("");
        txtValue.setText("");
        txtDescription.setText("");
    }
}