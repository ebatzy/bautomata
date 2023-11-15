package config;

import java.util.ArrayList;
import java.util.HashMap;

public class Mensajes {

    public static ArrayList<String> listaMensaje = new ArrayList<String>();

    public HashMap<Integer, String> errores = new HashMap<>();

    private static Mensajes MENSAJES;

    public Mensajes() {
        errores.put(1, "Requisitos previos: {{mensaje}}");
        errores.put(2, "El objeto {objeto} no existe en el archivo {ruta}");
        errores.put(3, "El archivo {ruta} no existe");
    }

    public static Mensajes MENSAJES() {
		if (MENSAJES == null) {
			MENSAJES = new Mensajes();
		}

		return MENSAJES;
	}

    public void agregarMensaje(String mensaje) {
        listaMensaje.add(mensaje);
    }

    public String getMensaje() {
        return listaMensaje.toString();
    }
}

