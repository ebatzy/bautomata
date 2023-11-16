package config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mensajes {

    private static ArrayList<String> listaMensaje = new ArrayList<String>();
    private static List<MensajesObserver> observers = new ArrayList<>();


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
        notifyObservers();
    }

    public String getMensaje() {
        return listaMensaje.toString();
    }

    public static void addObserver(MensajesObserver observer) {
        observers.add(observer);
    }

    public static void removeObserver(MensajesObserver observer) {
        observers.remove(observer);
    }

    private static void notifyObservers() {
        for (MensajesObserver observer : observers) {
            observer.onListaMensajeChanged(listaMensaje);
        }
    }

}
