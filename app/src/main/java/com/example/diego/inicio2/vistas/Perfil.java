package com.example.diego.inicio2.vistas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diego.inicio2.Conexion.Conexion;
import com.example.diego.inicio2.Conexion.ImageLoad;
import com.example.diego.inicio2.MainActivity;
import com.example.diego.inicio2.Manejadores.ManejadorUsuario;
import com.example.diego.inicio2.R;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

/**
 * Created by diego on 8/14/2015.
 */
public class Perfil extends Fragment {
    TextView mail;
    TextView nombreCompleto;
    TextView sexo;
    TextView fecha;
    ImageView imageView;
    Button cambiarImg;

    private String APP_DIRECTORY = "myPictureApp/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";



    private final int PHOTO_CODE = 100;
    private final int SELECT_PICTURE = 200;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.f_perfil, container, false);


        ImageView img = (ImageView)rootView.findViewById(R.id.imgUsuario_MiPerfil);
        new ImageLoad(Conexion.MI_IP+"FotosPerfil/"+ManejadorUsuario.usuario.getIdUsuario()+".jpg", img).execute();

        TextView nombre = (TextView) rootView.findViewById(R.id.nombreCompleto_MiPerfil);
        nombre.setText(ManejadorUsuario.usuario.getNombre() + " " + ManejadorUsuario.usuario.getApellido());

        TextView mail = (TextView) rootView.findViewById(R.id.txtEmail_Perfil);
        mail.setText(ManejadorUsuario.usuario.getMail());

        TextView fecha = (TextView) rootView.findViewById(R.id.txtFechaNacimiento_MiPerfil);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        fecha.setText(format.format(ManejadorUsuario.usuario.getFecha()));

        TextView sexo = (TextView) rootView.findViewById(R.id.txtSexo_MiPerfil);
        if(ManejadorUsuario.usuario.getSexo())
        {
            sexo.setText("Masculino");
        }else{
            sexo.setText("Femenino");
        }
        cambiarImg = (Button)rootView.findViewById(R.id.btnCambiarImagen_Perfil);

        cambiarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"Cambiar foto", "Editar Perfil", "Cancelar"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
                builder.setTitle("Elige una opcion ");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int seleccion) {
                        if(options[seleccion] == "Cambiar foto"){
                            //openCamera();
                            Intent i = new Intent(rootView.getContext(), FotoPerfil.class );
                            startActivity(i);

                        }else if (options[seleccion] == "Editar Perfil") {
                            dialog.dismiss();
                        }else if(options[seleccion] == "Cancelar"){
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });








        // PANTALLA EN VERTICAL
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//


        return rootView;

    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        file.mkdirs();

        String path = Environment.getExternalStorageDirectory() + File.separator
                + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;

        File newFile = new File(path);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
        startActivityForResult(intent, PHOTO_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case PHOTO_CODE:
                if(resultCode == Activity.RESULT_OK){
                    String dir =  Environment.getExternalStorageDirectory() + File.separator
                            + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;
                    decodeBitmap(dir);
                }
                break;

            case SELECT_PICTURE:
                if(resultCode == Activity.RESULT_OK){
                    Uri path = data.getData();
                    ContentResolver cr= getActivity().getContentResolver();
                    try
                    {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(cr,path);
                        bitmap = Bitmap.createBitmap(bitmap, 0, 0, 1000, 1000);

                        //creamos el drawable redondeado
                        RoundedBitmapDrawable roundedDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), bitmap);

                        //asignamos el CornerRadius
                        roundedDrawable.setCornerRadius(bitmap.getWidth());


                        /*
                        * AQUI SE DEBE SUBIR A LA IMAGEN BASE
                        *
                        * */
                        imageView.setImageDrawable(roundedDrawable);

                    }catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                break;
        }

    }

    private void decodeBitmap(String dir) {
        Bitmap bitmap,bitmap1;
        bitmap = BitmapFactory.decodeFile(dir);

        /*
                      * AQUI SE DEBE SUBIR A LA IMAGEN BASE
                        *
                        * */
        imageView.setImageBitmap(bitmap);
    }









}