package com.example.newscatcherapp.repository

import android.content.Context
import android.content.SharedPreferences

class LanguagePreferences(context: Context) {

    val login = "language"
    val langPreference: SharedPreferences = context.getSharedPreferences("LanguagePreference", Context.MODE_PRIVATE)

    fun getLangSetting() : String? {
        return langPreference.getString(login, "en")
    }

    fun setLangSetting(lang: String) {
        val editor = langPreference.edit()
        editor.putString(login, lang)
        editor.apply()
    }

}