package cl.duoc.pataverde.dao;

import cl.duoc.pataverde.model.Gato;
import cl.duoc.pataverde.model.Perro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MascotaJsonDAO implements MascotaDAO {
    private static final String ARCHIVO_PERROS = "perros.json";
    private static final String ARCHIVO_GATOS = "gatos.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public List<Perro> cargarPerros() throws IOException {
        File archivo = new File(ARCHIVO_PERROS);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        try (FileReader reader = new FileReader(archivo, StandardCharsets.UTF_8)) {
            Type tipo = new TypeToken<List<Perro>>() {}.getType();
            List<Perro> perros = gson.fromJson(reader, tipo);
            if (perros == null) {
                return new ArrayList<>();
            }
            return perros;
        }
    }

    @Override
    public List<Gato> cargarGatos() throws IOException {
        File archivo = new File(ARCHIVO_GATOS);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        try (FileReader reader = new FileReader(archivo, StandardCharsets.UTF_8)) {
            Type tipo = new TypeToken<List<Gato>>() {}.getType();
            List<Gato> gatos = gson.fromJson(reader, tipo);
            if (gatos == null) {
                return new ArrayList<>();
            }
            return gatos;
        }
    }

    @Override
    public void guardarPerros(List<Perro> perros) throws IOException {
        try (FileWriter writer = new FileWriter(ARCHIVO_PERROS, StandardCharsets.UTF_8)) {
            gson.toJson(perros, writer);
        }
    }

    @Override
    public void guardarGatos(List<Gato> gatos) throws IOException {
        try (FileWriter writer = new FileWriter(ARCHIVO_GATOS, StandardCharsets.UTF_8)) {
            gson.toJson(gatos, writer);
        }
    }
}


