package com.ddrd.goldeskapp.data.model.traspasos;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.IOException;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class PdfResponse {

    public static Uri guardarPdfEnDescargas(Context context, ResponseBody body, String nombreArchivo) throws IOException {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Downloads.DISPLAY_NAME, nombreArchivo + ".pdf");
        values.put(MediaStore.Downloads.MIME_TYPE, "application/pdf");

        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/Goldesk");
            values.put(MediaStore.Downloads.IS_PENDING, 1);
            uri = context.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
        } else {
            java.io.File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            java.io.File file = new java.io.File(directory, nombreArchivo + ".pdf");
            uri = Uri.fromFile(file);
        }

        // MEJORA: Uso de try-with-resources para garantizar el cierre de flujos
        try (java.io.InputStream is = body.byteStream();
             OutputStream fos = context.getContentResolver().openOutputStream(uri)) {

            if (fos != null) {
                byte[] buffer = new byte[8192]; // Buffer de 8KB es más eficiente
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                fos.flush();
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && uri != null) {
            values.clear();
            values.put(MediaStore.Downloads.IS_PENDING, 0);
            context.getContentResolver().update(uri, values, null, null);
        }

        return uri;
    }
}
