package com.juanjoflores.mantenimientoapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

	public static String TOKEN_NO_DISPONIBLE = "No disponible";
	private static SessionManager 	s_instance 				= null;
	private static Object s_lock 					= new Object();
	
	private Context m_context 				= null;
	private final String PREFERENCES_NAME 		= "_mantenimiento_preferences";
	
	public final static String SESSION_TYPE_KEY		= "_mantenimiento_session_ty";
	public final static String ACCESS_TOKEN_KEY 		= "_mantenimiento_session_access_token";
	public final static String USER_PERFIL_KEY 		= "_mantenimiento_session_perfil";

	
	private SessionManager() {
	}

	public static SessionManager getInstace(Context context) {
		synchronized (s_lock) {
			if (s_instance == null) {
				s_instance = new SessionManager();
				s_instance.setContext(context);
			}
		}
		return s_instance;
	}
	private void setContext(Context context) {
		m_context = context;
	}

	public void setAccessToken(String token) {
		synchronized (s_lock) {
			SharedPreferences prefs = m_context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString(ACCESS_TOKEN_KEY, token);
			editor.commit();
		}
	}
	
	public String getAccessToken() {
		String result = null;
		synchronized (s_lock) {
			SharedPreferences prefs = m_context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
			String accessToken = prefs.getString(ACCESS_TOKEN_KEY, null);
			if(accessToken == null) {
				accessToken = prefs.getString(ACCESS_TOKEN_KEY, TOKEN_NO_DISPONIBLE);
			}
			result = accessToken;
		}
		return result;
	}

	public void setPerfilUser(String Perfil) {
		synchronized (s_lock) {
			SharedPreferences prefs = m_context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString(USER_PERFIL_KEY, Perfil);
			editor.commit();
		}
	}

	public String getPerfilUser() {
		String result = null;
		synchronized (s_lock) {
			SharedPreferences prefs = m_context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
			String accessToken = prefs.getString(USER_PERFIL_KEY, null);
			if(accessToken == null) {
				accessToken = prefs.getString(USER_PERFIL_KEY, TOKEN_NO_DISPONIBLE);
			}
			result = accessToken;
		}
		return result;
	}
	
	public void setAccessType(String type) {
		synchronized (s_lock) {
			SharedPreferences prefs = m_context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString(SESSION_TYPE_KEY, type);
			editor.commit();
		}	
	}
	
	public String getAccessType() {
		String result = null;
		synchronized (s_lock) {
			SharedPreferences prefs = m_context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
			String accessType = prefs.getString(SESSION_TYPE_KEY, null);
			if(accessType == null) {
				accessType = prefs.getString(SESSION_TYPE_KEY, null);
			}
			result = accessType;
		}
		return result;
	}
	
	public void close() {
		
		//Elimina lo contenido en el SharedPreferences
		SharedPreferences prefs = m_context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.clear();
		editor.commit();
		return;
	}
	
	
}
