package com.winas_lesson.android.day4.day4homework.data.local

import com.winas_lesson.android.day4.day4homework.App
import net.grandcentrix.tray.AppPreferences
import java.util.*

class Me {
    companion object {
        val shared = Me()
    }
    private val preference = AppPreferences(App.context)

    enum class Key(val keyName: String) {
        USER_ID("userID"), PASSWORD("password");
        //USER_ID("userID");
        val keyType: KeyType?
            get() {
                // TODO
                return when (this) {
                    USER_ID ->
                        KeyType.STRING
                    PASSWORD ->
                        KeyType.STRING
                }
            }
        val defaultValue: Any?
            get() {
                // TODO
                return null
            }
    }
    enum class KeyType {
        BOOL, INT, FLOAT, LONG, STRING, DATE,
    }

    fun get(key: Key): Any? {
        val keyType = key.keyType
        when (keyType) {
            KeyType.BOOL -> return preference.getBoolean(
                key.keyName,
                (key.defaultValue as? Boolean) ?: false
            )
            KeyType.INT -> return preference.getInt(key.keyName, (key.defaultValue as? Int) ?: 0)
            KeyType.FLOAT -> return preference.getFloat(
                key.keyName,
                (key.defaultValue as? Float) ?: 0.0F
            )
            KeyType.LONG -> return preference.getLong(
                key.keyName,
                (key.defaultValue as? Long) ?: 0L
            )
            KeyType.STRING -> return preference.getString(
                key.keyName,
                (key.defaultValue as? String) ?: null
            )
            KeyType.DATE -> {
                val timestamp = preference.getLong(key.keyName, 0L)
                return if (timestamp != 0L) {
                    Date(timestamp * 1000)
                } else {
                    null
                }
            }
        }
        return null
    }

    fun put(key: Key, value: Boolean) {
        preference.put(key.keyName, value)
    }
    fun put(key: Key, value: Int) {
        preference.put(key.keyName, value)
    }
    fun put(key: Key, value: Float) {
        preference.put(key.keyName, value)
    }
    fun put(key: Key, value: Long) {
        preference.put(key.keyName, value)
    }
    fun put(key: Key, value: String?) {
        preference.put(key.keyName, value)
    }
    fun put(key: Key, date: Date) {
        preference.put(key.keyName, date.time / 1000L)
    }
}
