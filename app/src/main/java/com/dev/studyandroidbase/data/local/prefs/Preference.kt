package com.dev.studyandroidbase.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import kotlin.reflect.KProperty

abstract class Preference(
	context: Context,
	private val name: String? = null
) {
	private val prefs: SharedPreferences by lazy {
		context.getSharedPreferences(name ?: javaClass.simpleName, Context.MODE_PRIVATE)
	}
	
	abstract class PrefDelegate<T>(val prefKey: String?) {
		abstract operator fun getValue(thisRef: Any?, property: KProperty<*>): T
		abstract operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
	}
	
	enum class StorableType {
		String, Int, Float, Boolean, Long, StringSet
	}
	
	inner class GenericPrefDelegate<T>(
		prefKey: String? = null,
		private val defaultValue: T?,
		val type: StorableType
	): PrefDelegate<T>(prefKey) {
		
		override fun getValue(thisRef: Any?, property: KProperty<*>): T {
			return try {
				when (type) {
					StorableType.String ->
						prefs.getString(prefKey ?: property.name, defaultValue as String?) as T
					StorableType.Int ->
						prefs.getInt(prefKey ?: property.name, defaultValue as Int) as T
					StorableType.Float ->
						prefs.getFloat(prefKey ?: property.name, defaultValue as Float) as T
					StorableType.Boolean ->
						prefs.getBoolean(prefKey ?: property.name, defaultValue as Boolean) as T
					StorableType.Long ->
						prefs.getLong(prefKey ?: property.name, defaultValue as Long) as T
					StorableType.StringSet ->
						prefs.getStringSet(
							prefKey ?: property.name,
							defaultValue as Set<String>
						) as T
				}
			} catch (e: ClassCastException) {
				prefs.edit().remove(prefKey).apply()
				defaultValue as T
			}
		}
		
		override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
			when (type) {
				StorableType.String -> {
					prefs.edit().putString(prefKey ?: property.name, value as String?).apply()
				}
				StorableType.Int -> {
					prefs.edit().putInt(prefKey ?: property.name, value as Int).apply()
				}
				StorableType.Float -> {
					prefs.edit().putFloat(prefKey ?: property.name, value as Float).apply()
				}
				StorableType.Boolean -> {
					prefs.edit().putBoolean(prefKey ?: property.name, value as Boolean).apply()
				}
				StorableType.Long -> {
					prefs.edit().putLong(prefKey ?: property.name, value as Long).apply()
				}
				StorableType.StringSet -> {
					prefs.edit().putStringSet(prefKey ?: property.name, value as Set<String>)
						.apply()
				}
			}
		}
		
	}
	
	fun stringPref(prefKey: String? = null, defaultValue: String? = null) =
		GenericPrefDelegate(prefKey, defaultValue, StorableType.String)
	
	fun intPref(prefKey: String? = null, defaultValue: Int = 0) =
		GenericPrefDelegate(prefKey, defaultValue, StorableType.Int)
	
	fun floatPref(prefKey: String? = null, defaultValue: Float = 0f) =
		GenericPrefDelegate(prefKey, defaultValue, StorableType.Float)
	
	fun booleanPref(prefKey: String? = null, defaultValue: Boolean = false) =
		GenericPrefDelegate(prefKey, defaultValue, StorableType.Boolean)
	
	fun longPref(prefKey: String? = null, defaultValue: Long = 0L) =
		GenericPrefDelegate(prefKey, defaultValue, StorableType.Long)
	
	fun stringSetPref(prefKey: String? = null, defaultValue: Set<String> = HashSet()) =
		GenericPrefDelegate(prefKey, defaultValue, StorableType.StringSet)
}