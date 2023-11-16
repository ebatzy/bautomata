package config;

import java.util.ArrayList;

public interface MensajesObserver {
    void onListaMensajeChanged(ArrayList<String> nuevaListaMensaje);
}
