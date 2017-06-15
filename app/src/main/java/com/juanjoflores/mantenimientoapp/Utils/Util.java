package com.juanjoflores.mantenimientoapp.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class Util {

    public static final String EMAIL = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"+
            "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"+
            "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."+
            "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"+
            "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"+
            "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";



    public static boolean isValidEmail(String email)
    {
        String expression = EMAIL;
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches())
        {
            return true;
        }
        else{
            return false;
        }
    }

    public static String getFechaActual(){
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(c.getTime());
    }

    public static void ShowConfirmCloseDialogAcept(final Activity activity,
                                                   Context ctx, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

        builder.setMessage(message)
                .setTitle(title)
                .setPositiveButton("ACEPTAR",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                activity.finish();
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    /**
     * CreateProgressDialog : Crea un cuadro de espera default con el texto
     * "CARGANDO"
     *
     * @ProgressDialog
     */
    public static ProgressDialog CreateProgressDialog(Context ctx) {
        return CreateProgressDialog(ctx, "Cargando...");
    }

    /**
     * CreateProgressDialog : Crea un cuadro de espera con el mensaje definido
     *
     * @ProgressDialog
     */
    public static ProgressDialog CreateProgressDialog(Context ctx,
                                                      String message) {

        ProgressDialog progressDialog = null;
        try{
            progressDialog = new ProgressDialog(ctx);

            progressDialog.setCancelable(false);
            progressDialog.setMessage(message);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.setMax(100);
            progressDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
        return progressDialog;
    }

}
