package Acciones;

import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vistas.MenuInicial;

public class Mtipohabitaciones {

    public Mtipohabitaciones() {
    }

    
    public void insertartipo(String codigot, String nombret,String descripciont) {

        try {

            String SQL = "INSERT INTO tipohabitaciones (codigotipo,nombretipo,descripciontipo) VALUES (?,?,?)";
            PreparedStatement consulta = MenuInicial.conectar.prepareStatement(SQL);

            consulta.setString(1, codigot);
            consulta.setString(2, nombret);
            consulta.setString(3, descripciont);
            consulta.execute();
            JOptionPane.showMessageDialog(null, "Tipo de habitacion Registrada exitosamente"," Registrado ",JOptionPane.INFORMATION_MESSAGE);

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo del Registro" + e.getMessage()," Error ",JOptionPane.ERROR_MESSAGE);
        }
    }

    //Mostrar Datos 
    public DefaultTableModel mostrartipo() {
        String[] titulos = {"codigo de Tipo de habitacion", "nombre del tipo de habitacion","Descripcion del tipo de habitacion"};

        String[] registros = new String[3];
        DefaultTableModel tabla = new DefaultTableModel(null, titulos);
        String SQL = "SELECT * FROM tipohabitaciones order by codigotipo";

        try {
            Statement consulta = MenuInicial.conectar.createStatement();
            ResultSet resultado = consulta.executeQuery(SQL);
            
            while (resultado.next()) {
                registros[0] = resultado.getString("codigotipo");
                registros[1] = resultado.getString("nombretipo");
                registros[2] = resultado.getString("descripciontipo");
                tabla.addRow(registros);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Fallo para mostrar :" + e.getMessage()," Error ",JOptionPane.ERROR_MESSAGE);
        }
        return tabla;
    }

    public void actualizatipo(String codigot, String nombret,String descripciont) {

        try {
            String SQL = "UPDATE tipohabitaciones SET nombretipo=?,descripciontipo=? WHERE codigotipo=?";
            PreparedStatement consulta = MenuInicial.conectar.prepareStatement(SQL);

            consulta.setString(1, nombret);
            consulta.setString(2, descripciont);
            consulta.setString(3, codigot);

            consulta.execute();

            JOptionPane.showMessageDialog(null, "Tipo de habitacion editado exitosamente"," Editado ",JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Fallo de la actualizacion: " + e.getMessage()," Error ",JOptionPane.ERROR_MESSAGE);
        }
    }

    
    public void eliminartipo(String codigot) {
        String SQL = "DELETE FROM tipohabitaciones WHERE codigotipo=?";
        try {
            PreparedStatement consulta = MenuInicial.conectar.prepareStatement(SQL);
            consulta.setString(1, codigot);
            consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tipo de habitacion Eliminada exitosamente"," Eliminada ",JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar registro " + e.getMessage());
        }
    }
    
    
    
    
}
