package cl.duoc.pataverde.controller;

import cl.duoc.pataverde.dao.MascotaJsonDAO;
import cl.duoc.pataverde.model.Gato;
import cl.duoc.pataverde.model.Mascota;
import cl.duoc.pataverde.model.Perro;
import cl.duoc.pataverde.repository.MascotaRepository;
import cl.duoc.pataverde.util.ValidadorFormulario;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.Locale;

public class MainController {

    // ---- Formulario ----
    @FXML private ComboBox<String> cmbTipo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEdad;
    @FXML private TextField txtPeso;
    @FXML private TextField txtRaza;
    @FXML private CheckBox chkVacunado;
    @FXML private CheckBox chkAdopcion;
    @FXML private CheckBox chkExterior;

    // ---- Botones ----
    @FXML private Button btnRegistrar;
    @FXML private Button btnEliminar;

    // ---- Tabla ----
    @FXML private TableView<Mascota> tblMascotas;
    @FXML private TableColumn<Mascota, String> colTipo;
    @FXML private TableColumn<Mascota, String> colNombre;
    @FXML private TableColumn<Mascota, Integer> colEdad;
    @FXML private TableColumn<Mascota, Double> colPeso;
    @FXML private TableColumn<Mascota, String> colCosto;

    // ---- Mensajes ----
    @FXML private Label lblMensaje;

    // ---- Acceso a datos ----
    private final MascotaRepository repository = new MascotaRepository(new MascotaJsonDAO());

    @FXML
    private void initialize() {
        // 1. Opciones del selector
        cmbTipo.getItems().addAll("Perro", "Gato");

        // 2. Qué muestra cada columna
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colCosto.setCellValueFactory(fila ->
                new SimpleStringProperty(formatearCosto(fila.getValue().calcularCostoConsulta())));

        // 3. La tabla muestra la lista observable del repository
        tblMascotas.setItems(repository.getMascotas());

        // 4. Cargar lo guardado en los archivos
        try {
            repository.cargar();
        } catch (IOException e) {
            mostrarError("Error al leer los archivos: " + e.getMessage());
        }
        // 5. Estado inicial: sin tipo elegido, todo lo específico deshabilitado
        onCambiarTipo();
    }

    private String formatearCosto(double costo) {
        return String.format(Locale.of("es", "CL"), "$%,.0f", costo);
    }

    // ---- Eventos (los llenamos en los próximos pasos) ----
    @FXML
    private void onCambiarTipo() {
        String tipo = cmbTipo.getValue();
        boolean esPerro = "Perro".equals(tipo);
        boolean esGato = "Gato".equals(tipo);

        txtRaza.setDisable(!esPerro);
        chkVacunado.setDisable(!esPerro);
        chkAdopcion.setDisable(!esPerro);
        chkExterior.setDisable(!esGato);
    }
    @FXML
    private void onRegistrar() {
        // 1 y 2. Validar
        String tipo = cmbTipo.getValue();
        String error = ValidadorFormulario.validar(tipo, txtNombre.getText(),
                txtEdad.getText(), txtPeso.getText(), txtRaza.getText());
        if (error != null) {
            mostrarError(error);
            return;
        }

        // 3. Convertir (ya sabemos que son válidos)
        String nombre = txtNombre.getText().trim();
        int edad = Integer.parseInt(txtEdad.getText().trim());
        double peso = Double.parseDouble(txtPeso.getText().trim().replace(',', '.'));

        try {
            // 4. Crear el objeto según el tipo
            Mascota mascota;
            if (tipo.equals("Perro")) {
                Perro perro = new Perro(nombre, edad, peso,
                        txtRaza.getText().trim(), chkVacunado.isSelected());
                if (chkAdopcion.isSelected()) {
                    perro.marcarDisponibleParaAdopcion();
                }
                mascota = perro;
            } else {
                mascota = new Gato(nombre, edad, peso, chkExterior.isSelected());
            }

            // 5. Registrar (lista + archivos)
            repository.agregar(mascota);

            // 6. Confirmar y limpiar
            mostrarExito(mascota.getNombre() + " (" + mascota.getTipo() + ") registrado correctamente.");
            limpiarFormulario();

        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        } catch (IOException e) {
            mostrarError("Error al guardar los archivos: " + e.getMessage());
        }
    }

    private void limpiarFormulario() {
        cmbTipo.setValue(null);
        txtNombre.clear();
        txtEdad.clear();
        txtPeso.clear();
        txtRaza.clear();
        chkVacunado.setSelected(false);
        chkAdopcion.setSelected(false);
        chkExterior.setSelected(false);
        onCambiarTipo();
    }

    @FXML
    private void onEliminar() {
        Mascota seleccionada = tblMascotas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarError("Debe seleccionar una mascota de la tabla para eliminarla.");
            return;
        }

        try {
            repository.eliminar(seleccionada);
            mostrarExito(seleccionada.getNombre() + " (" + seleccionada.getTipo() + ") eliminado correctamente.");
        } catch (IOException e) {
            mostrarError("Error al guardar los archivos: " + e.getMessage());
        }
    }
    private void mostrarExito(String mensaje) {
        lblMensaje.setStyle("-fx-text-fill: green;");
        lblMensaje.setText(mensaje);
    }

    private void mostrarError(String mensaje) {
        lblMensaje.setStyle("-fx-text-fill: red;");
        lblMensaje.setText(mensaje);
    }
}