package Acciones;

import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vistas.MenuInicial;

public class MHabitaciones {

    public void obtenerTpo(JComboBox cb) {
        try {
            String SQL = "SELECT nombretipo FROM tipohabitaciones";
            PreparedStatement consulta = MenuInicial.conectar.prepareStatement(SQL);
            ResultSet resultado = consulta.executeQuery();
            cb.addItem("Seleccione una opcion");
            while (resultado.next()) {
                cb.addItem(resultado.getString("nombretipo"));
            }

        } catch (Exception ex) {

        }
    }

    public String busquedaTipo(String nombretipo) {

        String SQL = "SELECT codigotipo FROM tipohabitaciones WHERE nombretipo = '" + nombretipo + "'";
        try {
            String codigo = "";
            Statement consultaCodigo = MenuInicial.conectar.createStatement();
            ResultSet resultado = consultaCodigo.executeQuery(SQL);
            resultado.next();
            return codigo = resultado.getString("codigotipo");
        } catch (Exception e) {

        }
        return null;
    }

    public void insertarhabitacion(String codigoh, int ncamas, String descripcionh, float precioa, String nombretipo, String observaciones) {

        try {
            String codigotipo = this.busquedaTipo(nombretipo);

            String SQL = "INSERT INTO habitaciones (codigohab,numerocamas,descripcionh,precioh,tipoh,observacionesh) VALUES (?,?,?,?,?,?)";
            PreparedStatement consulta = MenuInicial.conectar.prepareStatement(SQL);

            consulta.setString(1, codigoh);
            consulta.setInt(2, ncamas);
            consulta.setString(3, descripcionh);
            consulta.setFloat(4, precioa);
            consulta.setString(5, codigotipo);
            consulta.setString(6, observaciones);
            consulta.execute();
            JOptionPane.showMessageDialog(null, "Habitacion Registrada exitosamente", " Registrado ", JOptionPane.INFORMATION_MESSAGE);

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo del Registro" + e.getMessage(), " Error ", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Mostrar Datos 
    public DefaultTableModel mostrarhabitacion() {
        String[] titulos = {"codigo de habitacion", "numero de camas", "Descripcion ", "Precio ", " tipo habitacion ", " Observaciones "};

        String[] registros = new String[6];
        DefaultTableModel tabla = new DefaultTableModel(null, titulos);
        String SQL = "SELECT * FROM habitaciones "
                + "   INNER JOIN tipohabitaciones "
                + "   ON habitaciones.tipoh=tipohabitaciones.codigotipo ORDER BY codigotipo  ";

        try {
            Statement consulta = MenuInicial.conectar.createStatement();
            ResultSet resultado = consulta.executeQuery(SQL);

            while (resultado.next()) {
                registros[0] = resultado.getString("codigohab");
                registros[1] = resultado.getString("numerocamas");
                registros[2] = resultado.getString("descripcionh");
                registros[3] = resultado.getString("precioh");
                registros[4] = resultado.getString("nombretipo");
                registros[5] = resultado.getString("observacionesh");
                tabla.addRow(registros);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Fallo para mostrar :" + e.getMessage(), " Error ", JOptionPane.ERROR_MESSAGE);
        }
        return tabla;
    }

    public void actualizahabitacion(String codigoh, int ncamas, String descripcionh, float precioa, String nombretipo, String observaciones) {

        try {

            String codigotipo = this.busquedaTipo(nombretipo);

            String SQL = "UPDATE habitaciones SET numerocamas=?,descripcionh=?,precioh=?,tipoh=?,observacionesh=? WHERE codigohab=?";
            PreparedStatement consulta = MenuInicial.conectar.prepareStatement(SQL);

            consulta.setInt(1, ncamas);
            consulta.setString(2, descripcionh);
            consulta.setFloat(3, precioa);
            consulta.setString(4, codigotipo);
            consulta.setString(5, observaciones);
            consulta.setString(6, codigoh);
            consulta.execute();

            JOptionPane.showMessageDialog(null, " habitacion editado exitosamente", " Editado ", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Fallo de la actualizacion: " + e.getMessage(), " Error ", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminarhabitacion(String codigohab) {
        String SQL = "DELETE FROM habitaciones WHERE codigohab=?";
        try {
            PreparedStatement consulta = MenuInicial.conectar.prepareStatement(SQL);
            consulta.setString(1, codigohab);
            consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, " habitacion Eliminada exitosamente", " Eliminada ", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar registro " + e.getMessage());
        }
    }

}
