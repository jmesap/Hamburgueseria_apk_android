package com.example.tarea2_jmp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import java.util.Locale;

public class LocaleConfig {
    private static final String LANGUAGE_KEY = "language_key";

    public static void setLocale(Context context, String language) {

        // Crear un objeto Locale con el idioma especificado
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        // Obtener los recursos y la configuración actuales
        Resources resources = context.getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());

        // Establecer el idioma en la configuración
        configuration.setLocale(locale);

        // Guardar la configuración en las preferencias compartidas para que persista
        SharedPreferences.Editor editor = context.getSharedPreferences("locale_prefs", Context.MODE_PRIVATE).edit();
        editor.putString(LANGUAGE_KEY, language);
        editor.apply();
    }

    public static void setLocaleFromPrefs(Context context) {

        // Obtener las preferencias compartidas
        SharedPreferences prefs = context.getSharedPreferences("locale_prefs", Context.MODE_PRIVATE);

        // Obtener el idioma guardado en las preferencias compartidas
        String language = prefs.getString(LANGUAGE_KEY, "");

        // Si se ha guardado un idioma, establecerlo
        if (!language.isEmpty()) {
            setLocale(context, language);
        }
    }
}
