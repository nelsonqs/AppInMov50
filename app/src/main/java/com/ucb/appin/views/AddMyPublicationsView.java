package com.ucb.appin.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.ucb.appin.R;
import com.ucb.appin.data.model.AvisosBean;
import com.ucb.appin.data.model.TipoAvisoBean;
import com.ucb.appin.data.model.TransaccionAviso;
import com.ucb.appin.presenters.AddMyPublicationsPresenter;
import com.ucb.appin.presenters.IAddMyPublicationsPresenter;
import com.ucb.appin.util.ConstantAppInVirtual;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMyPublicationsView extends AppCompatActivity implements IAddMyPublicationsView {

    private String path_foto = null;

    private IAddMyPublicationsPresenter iAddMyPublicationsPresenter;
    private List<TipoAvisoBean> listTipoAvisoBeans = new ArrayList<TipoAvisoBean>();
    private List<TransaccionAviso> listTransaccionAvisos = new ArrayList<TransaccionAviso>();

    private String[] slistaTipoAvisos;
    private String[] slistaTransaccionAvisos;
    private AvisosBean avisosBean = null;

    private double latitud = ConstantAppInVirtual.DEFAULT_LATITUD;
    private double longitud = ConstantAppInVirtual.DEFAULT_LONGITUD;

    // UI references.
    @BindView(R.id.add_progress)
    View mProgressView;
    @BindView(R.id.add_form)
    View mAddFormView;
    @BindView(R.id.img_add_foto)
    ImageView imgAddFoto;

    EditText txtDescripcion;
    EditText txtAddPrecio;
    EditText txtAddTelefono;
    EditText txtAddDireccion;
    Spinner spiAddIipo;
    Spinner spiAddTransaccion;

    ImageButton btnAddFoto;
    ImageButton btnDeleteFoto;

    @BindView(R.id.map_add_ubicacion)
    MapView mapView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_publications);

        ButterKnife.bind(this);

        this.iAddMyPublicationsPresenter = new AddMyPublicationsPresenter(this, this);
        this.iAddMyPublicationsPresenter.listTipoAvisos();
        this.iAddMyPublicationsPresenter.listTransaccionAvisos();

        // recuperando datos desde la vista
        this.mProgressView = findViewById(R.id.add_progress);
        this.mAddFormView = findViewById(R.id.add_form);
        this.imgAddFoto = (ImageView) findViewById(R.id.img_add_foto);

        txtDescripcion = (EditText) findViewById(R.id.txt_descripcion);
        txtAddPrecio  = (EditText) findViewById(R.id.txt_add_precio);
        txtAddTelefono  = (EditText) findViewById(R.id.txt_add_telefono);
        txtAddDireccion  = (EditText) findViewById(R.id.txt_add_direccion);
        spiAddIipo  = (Spinner) findViewById(R.id.spi_add_tipo);
        spiAddTransaccion = (Spinner) findViewById(R.id.spi_add_transaccion);

        this.btnAddFoto = (ImageButton) findViewById(R.id.btn_add_foto);
        this.btnDeleteFoto = (ImageButton) findViewById(R.id.btn_delete_foto);

        this.btnAddFoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImage();
            }
        });
        this.btnDeleteFoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteImage();
            }
        });

        mapView.onCreate(savedInstanceState);
        loadGoogleMaps();

        //seting data in spinner
        ArrayAdapter<String> adapterAvisos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slistaTipoAvisos);
        adapterAvisos.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spiAddIipo.setAdapter(adapterAvisos);

        ArrayAdapter<String> adapterTransaccion = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slistaTransaccionAvisos);
        adapterTransaccion.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spiAddTransaccion.setAdapter(adapterTransaccion);

        try {
            Gson gson = new Gson();

            Bundle extras = getIntent().getExtras();
            String tmp = extras.getString("avisosBean");
            this.avisosBean = gson.fromJson(tmp, AvisosBean.class);
        }catch (Exception err){
            this.avisosBean = null;
        }

        if(this.avisosBean != null){
            this.insertData();
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @Override
    public void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mAddFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mAddFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAddFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mAddFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_my_public, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_add_save){

            String descripcion = this.txtDescripcion.getText().toString();
            String precio = this.txtAddPrecio.getText().toString();
            String telefono = this.txtAddTelefono.getText().toString();
            String direccion = this.txtDescripcion.getText().toString();

            TipoAvisoBean tipoAvisoBean = listTipoAvisoBeans.get(this.spiAddIipo.getSelectedItemPosition());
            TransaccionAviso transaccionAviso = listTransaccionAvisos.get(this.spiAddTransaccion.getSelectedItemPosition());

            boolean dvalid = this.validateData(descripcion,precio,telefono,direccion,null,null,null);
            if(dvalid){
                Log.v("--->",">>LLEGA PARA GUARDAR>>");

                String image64 = "";
                try{

                    Bitmap bitmap = ((BitmapDrawable)imgAddFoto.getDrawable()).getBitmap();
                    if(bitmap != null){

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageBytes = baos.toByteArray();

                        Bitmap original = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        Bitmap news = Bitmap.createScaledBitmap(original, 120, 120, false);

                        ByteArrayOutputStream rBaos = new ByteArrayOutputStream();
                        news.compress(Bitmap.CompressFormat.JPEG, 100, rBaos);
                        byte[] rImageBytes = rBaos.toByteArray();

                        image64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    }

                }catch (Exception error){}

                AvisosBean avisosBean = new AvisosBean();
                if(this.avisosBean != null){
                    avisosBean.setId(this.avisosBean.getId());
                }
                else{
                    int idAviso = this.iAddMyPublicationsPresenter.getMaxId() + 1;
                    avisosBean.setId(idAviso);
                }

                String titulo = descripcion;
                if(descripcion.length() > 35){
                    titulo = descripcion.substring(0, 35);
                }
                avisosBean.setTitulo(titulo.toUpperCase());
                avisosBean.setDescripcion(descripcion);
                avisosBean.setPrecio(Double.parseDouble(precio));
                avisosBean.setTelefono(telefono);
                avisosBean.setDireccion(direccion);
                avisosBean.setLatitud(ConstantAppInVirtual.DEFAULT_LATITUD);
                avisosBean.setLongitud(ConstantAppInVirtual.DEFAULT_LONGITUD);
                avisosBean.setTipoAviso(tipoAvisoBean.getId());
                avisosBean.setTransaccionAviso(transaccionAviso.getId());
                avisosBean.setImagen(image64);

                this.iAddMyPublicationsPresenter.addAviso(avisosBean);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){

            switch (requestCode){
                case ConstantAppInVirtual.COD_SELECCIONA:
                    Uri miPath=data.getData();
                    imgAddFoto.setImageURI(miPath);
                    break;

                case ConstantAppInVirtual.COD_FOTO:
                    MediaScannerConnection.scanFile(this, new String[]{path_foto}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Ruta de almacenamiento","Path: "+path);
                                }
                            });

                    Bitmap bitmap = BitmapFactory.decodeFile(path_foto);
                    imgAddFoto.setImageBitmap(bitmap);

                    break;
            }
        }
    }

    @Override
    public void loadInitDataTipoAviso(List<TipoAvisoBean> listTipoAvisoBeans){
        this.listTipoAvisoBeans = listTipoAvisoBeans;
        if(this.listTipoAvisoBeans.size() > 0){
            this.slistaTipoAvisos = new String[this.listTipoAvisoBeans.size()];
            for(int i = 0;i < listTipoAvisoBeans.size();i++){
                TipoAvisoBean tipoAvisoBean = listTipoAvisoBeans.get(i);
                this.slistaTipoAvisos[i] =  tipoAvisoBean.getNombre();
            }
        }

        /*SubCategoriaBean subCategoriaBean = new SubCategoriaBean();
        subCategoriaBean.setNombre("Categoria 1");
        SubCategoriaBean subCategoriaBean0 = new SubCategoriaBean();
        subCategoriaBean0.setNombre("Categoria 2");
        SubCategoriaBean subCategoriaBean1 = new SubCategoriaBean();
        subCategoriaBean1.setNombre("Categoria 3");
        listSubCategoriaBeans.add(subCategoriaBean);*/
    }

    @Override
    public void loadInitDataTransaccionAviso(List<TransaccionAviso>listTransaccionAvisos){
        this.listTransaccionAvisos = listTransaccionAvisos;

        if(this.listTransaccionAvisos.size() > 0){
            this.slistaTransaccionAvisos = new String[this.listTransaccionAvisos.size()];
            for(int i = 0;i < listTransaccionAvisos.size();i++){
                TransaccionAviso transaccionAviso = listTransaccionAvisos.get(i);
                this.slistaTransaccionAvisos[i] =  transaccionAviso.getNombre();
            }
        }
    }


    //?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????//
    private void loadGoogleMaps() {
        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(final GoogleMap googleMap) {
                LatLng coordinates = new LatLng(latitud, longitud);
                googleMap.addMarker(new MarkerOptions().position(coordinates).title("Ubicacion"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, ConstantAppInVirtual.DEFAULT_ZOOM));
                mapView.onResume();

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng point) {
                        // TODO Auto-generated method stub
                        //lstLatLngs.add(point);
                        latitud = point.latitude;
                        longitud = point.longitude;

                        googleMap.clear();
                        googleMap.addMarker(new MarkerOptions().position(point));
                    }
                });
            }
        });
    }

    //insert data if is mod
    private void insertData(){
        this.txtDescripcion.setText(this.avisosBean.getDescripcion());
        this.txtAddPrecio.setText(this.avisosBean.getPrecio().toString());
        this.txtAddTelefono.setText(this.avisosBean.getTelefono());
        this.txtAddDireccion.setText(this.avisosBean.getDireccion());

        int index = 0;
        for(int i = 0;i < listTipoAvisoBeans.size();i++){
            TipoAvisoBean tipoAvisoBean = listTipoAvisoBeans.get(i);
            if(tipoAvisoBean.getId() == this.avisosBean.getTipoAviso()){
                index = i;
                break;
            }
        }
        this.spiAddIipo.setSelection(index);

        for(int i = 0;i < listTransaccionAvisos.size();i++){
            TransaccionAviso transaccionAviso = listTransaccionAvisos.get(i);
            if(transaccionAviso.getId() == this.avisosBean.getTransaccionAviso()){
                index = i;
                break;
            }
        }
        this.spiAddTransaccion.setSelection(index);

        if(this.avisosBean.getImagen() != null){
            if(!this.avisosBean.getImagen().isEmpty()){
                byte[] imaget64 = Base64.decode(this.avisosBean.getImagen(), Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imaget64, 0, imaget64.length);
                imgAddFoto.setImageBitmap(decodedImage);
            }
        }

        try {
            this.latitud = this.avisosBean.getLatitud();
            this.longitud = this.avisosBean.getLongitud();
            loadGoogleMaps();
        }catch (Exception err){

        }
    }

    private void loadImage() {

        final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(AddMyPublicationsView.this);
        alertOpciones.setTitle("Seleccione una Opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    takePhothograph();
                }else{
                    if (opciones[i].equals("Cargar Imagen")){
                        Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"), ConstantAppInVirtual.COD_SELECCIONA);
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();

    }

    private void takePhothograph() {
        File fileImagen=new File(Environment.getExternalStorageDirectory(),ConstantAppInVirtual.RUTA_IMAGEN);
        boolean isCreada=fileImagen.exists();
        String nombreImagen = "";
        if(!isCreada){
            isCreada=fileImagen.mkdirs();
        }

        if(isCreada){
            nombreImagen=(System.currentTimeMillis()/1000)+".jpg";
        }


        path_foto = Environment.getExternalStorageDirectory()+
                File.separator + ConstantAppInVirtual.RUTA_IMAGEN + File.separator+nombreImagen;

        File imagen=new File(path_foto);

        Intent intent=null;
        intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ////
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
        {
            String authorities=getApplicationContext().getPackageName()+".provider";
            Uri imageUri= FileProvider.getUriForFile(this,authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else
        {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent, ConstantAppInVirtual.COD_FOTO);

    }

    private void deleteImage() {
        this.imgAddFoto.setImageURI(null);
    }

    //////////////////////////////////////////////VALIDATE///////////////////////////////////////////////////////////
    public boolean validateData(String descripcion, String precio, String telefono, String direccion, String subCategoria, String tipo, String transaccion){
        boolean result = true;
        if(descripcion == null){
            this.txtDescripcion.setError(ConstantAppInVirtual.DEFAULT_DATA_ERROR);
            this.txtDescripcion.requestFocus();
            result = false;
        }
        else if(descripcion.length() == 0 || descripcion.length() > 250){
            this.txtDescripcion.setError(ConstantAppInVirtual.DEFAULT_DATA_ERROR);
            this.txtDescripcion.requestFocus();
            result = false;
        }
        if(telefono == null){
            this.txtAddTelefono.setError(ConstantAppInVirtual.DEFAULT_DATA_ERROR);
            this.txtAddTelefono.requestFocus();
            result = false;
        }
        else if(telefono.length() == 0 || telefono.length() > 20){
            this.txtAddTelefono.setError(ConstantAppInVirtual.DEFAULT_DATA_ERROR);
            this.txtAddTelefono.requestFocus();
            result = false;
        }
        if(direccion != null){
            if(direccion.length() > 250){
                this.txtAddDireccion.setError(ConstantAppInVirtual.DEFAULT_DATA_ERROR);
                this.txtAddDireccion.requestFocus();
                result = false;
            }
        }

        try {
            double aux = Double.parseDouble(precio);
            result = true;
        }catch (Exception error){
            result = false;
            this.txtAddPrecio.setError(ConstantAppInVirtual.DEFAULT_DATA_ERROR);
            this.txtAddPrecio.requestFocus();
        }

        return result;
    }

}

