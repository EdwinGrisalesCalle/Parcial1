package com.egc.parcial1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.ib.custom.toast.CustomToastView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spOption;
    private Spinner spCategories;
    private Spinner spCategoryCost;

    private List<String> categoryList = new ArrayList<>();
    private TextView tbCode;
    private TextView tbName;
    private TextView tbValue;
    private TextView tbIva;
    private TextView tvMessage;

    private TableRow tableHeader;
    private TableRow tableBody;


    private TextView tbDescription;
    private TextView tbCategory;
    private List<Product> operation = new ArrayList<>();
    private String operationStr;
    private List<Product> listProduct = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listProduct =(List<Product>) getIntent().getSerializableExtra("listProduct");
        categoryList.add("Bebidas");categoryList.add("Granos");
        categoryList.add("Frutas");categoryList.add("Verduras");
        categoryList.add("Abarrotes");categoryList.add("Aseo Personal");
        tvMessage=findViewById(R.id.tvMessage);
        tableHeader=findViewById(R.id.tableHeader);
        tableBody=findViewById(R.id.tableBody);

        spCategories= findViewById(R.id.spCategories);
        ArrayAdapter<CharSequence> adapterCategory = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,categoryList);
        adapterCategory.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spCategories.setAdapter(adapterCategory);

        spCategoryCost= findViewById(R.id.spCategoryCost);
        ArrayAdapter<CharSequence> adapterCategoryCost = ArrayAdapter.createFromResource(this,R.array.categoriaCost,R.layout.support_simple_spinner_dropdown_item);
        adapterCategoryCost.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spCategoryCost.setAdapter(adapterCategoryCost);


        spOption= findViewById(R.id.spSelection);
        dropDownAsignation(listProduct);

        //btnSend.setOnClickListener(this);
        spOption.setOnItemSelectedListener(this);
        spCategoryCost.setOnItemSelectedListener(this);
        spCategories.setOnItemSelectedListener(this);

        tbCode=findViewById(R.id.tbCode);
        tbName=findViewById(R.id.tbName);
        tbValue=findViewById(R.id.tbValue);
        tbIva=findViewById(R.id.tbIva);
        tbDescription=findViewById(R.id.tbDescription);
        tbCategory=findViewById(R.id.tbCategory);
        tvMessage.setVisibility(View.GONE);
        spCategories.setVisibility(View.GONE);
        spCategoryCost.setVisibility(View.GONE);



        if (listProduct != null){
            setList(listProduct);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Integer seleccion = spOption.getSelectedItemPosition();

        if (parent.getId() == R.id.spSelection){

            switch (seleccion){
            case 0:
                CustomToastView.makeErrorToast(this,"Seleccione alguna acción",R.layout.custom_toast).show();
                break;
            case 1:
                Intent intent = new Intent(this, Register_Product.class);
                intent.putExtra("categoryList", (Serializable) categoryList);
                startActivity(intent);
                break;
            case 2://con iva

                operation = iva(listProduct,true);
                setList(operation);
                break;
            case 3://sin iva
                operation = iva(listProduct,false);
                setList(operation);

                break;//promedio values
            case 4:
                tableBody.setVisibility(View.GONE);
                tableHeader.setVisibility(View.GONE);
                operationStr = averageValue(listProduct);
                tvMessage.setVisibility(View.VISIBLE);
                tvMessage.setText(operationStr);
                break;
            case 5://productos por categoria
                spCategories.setVisibility(View.VISIBLE);
                spCategoryCost.setVisibility(View.VISIBLE);


                break;
            case 6://10 mas costosos
                operation = moreExpensiveProducts();
                setList(operation);

                break;
                case 7://10 mas baratos
                    operation = moreCheaperProducts();
                    setList(operation);
                    break;
            case 8://todos los registros
                tableBody.setVisibility(View.VISIBLE);
                tableHeader.setVisibility(View.VISIBLE);

                break;
            default:
                break;
        }
        }
       if (parent.getId()== R.id.spCategories) {
           operation = productsByCategory();
           setList(operation);
       }
        if (parent.getId()== R.id.spCategoryCost) {
            Integer select = spCategoryCost.getSelectedItemPosition();
            switch (select){
                case 0:
                    setList(moreExpensiveProductsCategory(operation));
                    break;
                case 1:
                    setList(moreCheaperProductsCategory(operation));
                    break;
            }

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    public void dropDownAsignation(List<Product> List){
        ArrayAdapter<CharSequence> adapter;
        if (List == null){
            adapter = ArrayAdapter.createFromResource(this,R.array.opcion_unique,R.layout.support_simple_spinner_dropdown_item);
        }else {
            adapter = ArrayAdapter.createFromResource(this, R.array.options_array, R.layout.support_simple_spinner_dropdown_item);
        }
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spOption.setAdapter(adapter);
    }

    public void setList(List<Product> employeeList) {

        String code = "";
        String name = "";
        String value = "";
        String iva = "";
        String description = "";
        String category = "";
        for (int i = 0; i <= employeeList.size() - 1; i++) {

            code = code + "\n" + employeeList.get(i).code;
            name = name + "\n" + employeeList.get(i).name;
            value = value + "\n" + employeeList.get(i).value;
            if(employeeList.get(i).hasIva==true){
                iva = iva + "\n" + "NO";
            }else{
                iva = iva + "\n" + "SI";
            }

            description = description + "\n" + employeeList.get(i).description;
            category = category + "\n" + employeeList.get(i).category;
        }

        tbCode.setText(code);
        tbName.setText(name);
        tbValue.setText(value);
        tbIva.setText(iva);
        tbDescription.setText(description);
        tbCategory.setText(category);
    }


    ////////////////////Consultas
    private List<Product> iva(List<Product> list,boolean ivaCondicion){
        List<Product> Iva = new ArrayList<>();
        for (Product  p: list){
            if (p.hasIva==ivaCondicion){
                Iva.add(p);
            }
        }
        return Iva;
    }


        private String averageValue(List<Product> list){
        Double sum =0.0;
        Double result=0.0;
        for (Product  p: list) {
                sum = sum + p.value;

        }
        result = sum/list.size();

        return "El promedio de precios de los productos es " + result;
    }

    private List<Product> productsByCategory(){
        List<Product> list = new ArrayList<>();
        String CategorySelected = spCategories.getSelectedItem().toString();
        for (Product product : listProduct){
            if (CategorySelected.equals(product.category)){
                list.add(product);
            }
        }
        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Product> moreExpensiveProducts(){
        Collections.sort(listProduct, new costComparator().reversed());
        if (listProduct.size()>10){
            return listProduct.subList(0,10);
        }else{
            return listProduct.subList(0,listProduct.size()-1);
        }
    }

    private List<Product> moreCheaperProducts(){
        Collections.sort(listProduct, new costComparator());
        if (listProduct.size()>10){
            return listProduct.subList(0,10);
        }else{
            return listProduct.subList(0,listProduct.size()-1);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Product> moreExpensiveProductsCategory(List<Product> list){
        Collections.sort(list, new costComparator().reversed());
        if (list.size()>3){
            return list.subList(0,3);
        }else{
            return list.subList(0,list.size()-1);
        }
    }

    private List<Product> moreCheaperProductsCategory(List<Product> list){
        Collections.sort(list, new costComparator());
        if (list.size()>3){
            return list.subList(0,3);
        }else{
            return list.subList(0,list.size()-1);
        }

    }




    class costComparator implements Comparator<Product> {
        @Override
        public int compare(Product o1, Product o2) {
            return o1.getValue().compareTo(o2.getValue());
        }
    }

    }