package cl.duoc.pataverde.repository;

import cl.duoc.pataverde.dao.MascotaDAO;
import cl.duoc.pataverde.model.Gato;
import cl.duoc.pataverde.model.Mascota;
import cl.duoc.pataverde.model.Perro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MascotaRepository {

    private final MascotaDAO dao;
    private final ObservableList<Mascota> mascotas = FXCollections.observableArrayList();

    public MascotaRepository(MascotaDAO dao) {
        this.dao = dao;
    }

    public ObservableList<Mascota> getMascotas() {
        return mascotas;
    }

    public void cargar() throws IOException {        // ← nuevo
        mascotas.clear();
        mascotas.addAll(dao.cargarPerros());
        mascotas.addAll(dao.cargarGatos());
    }
    private void guardarArchivos() throws IOException {
        List<Perro> perros = new ArrayList<>();
        List<Gato> gatos = new ArrayList<>();

        for (Mascota m : mascotas) {
            if (m instanceof Perro p) {
                perros.add(p);
            } else if (m instanceof Gato g) {
                gatos.add(g);
            }
        }

        dao.guardarPerros(perros);
        dao.guardarGatos(gatos);
    }
    public void agregar(Mascota mascota) throws IOException {
        mascotas.add(mascota);
        guardarArchivos();
    }
    public void eliminar(Mascota mascota) throws IOException {
        mascotas.remove(mascota);
        guardarArchivos();
    }
}


