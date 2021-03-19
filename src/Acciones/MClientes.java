package Acciones;

import java.awt.HeadlessException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vistas.MenuInicial;

public class MClientes {

    public MClientes() {
    }

    public void insertarCliente(int dni, String nombrec, Date fechanac, String lugarn, char sexo, String observaciones) {

        try {

            String SQL = "INSERT INTO clientes (dnic,nombrec,fechanac,lugarnac,sexoc,observacionc) VALUES (?,?,?,?,?,?)";
            PreparedStatement consulta = MenuInicial.conectar.prepareStatement(SQL);

            consulta.setInt(1, dni);
            consulta.setString(2, nombrec);
            consulta.setDate(3, fechanac);
            consulta.setString(4, lugarn);
            consulta.setString(5, String.valueOf(sexo));
            consulta.setString(6, observaciones);
            consulta.execute();
            JOptionPane.showMessageDialog(null, "Cliente Registrado exitosamente", " Registrado ", JOptionPane.INFORMATION_MESSAGE);

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo del Registro" + e.getMessage(), " Error ", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Mostrar Datos 
    public DefaultTableModel mostrarClientes() {
        String[] titulos = {"DNI", "nombre del Cliente", "Fecha de nacimiento", " lugar de nacimiento ", " Genero ", " Observaciones "};

        String[] registros = new String[6];
        DefaultTableModel tabla = new DefaultTableModel(null, titulos);
        String SQL = "SELECT * FROM clientes order by dnic";

        try {
            Statement consulta = MenuInicial.conectar.createStatement();
            ResultSet resultado = consulta.executeQuery(SQL);

            while (resultado.next()) {
                registros[0] = resultado.getString("dnic");
                registros[1] = resultado.getString("nombrec");
                String FormatoFecha = resultado.getString("fechanac");
                String fecha = FormatoFecha.replace("-", "/");
                registros[2] = fecha;
                registros[3] = resultado.getString("lugarnac");
                registros[4] = resultado.getString("sexoc");
                registros[5] = resultado.getString("observacionc");
                tabla.addRow(registros);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Fallo para mostrar :" + e.getMessage(), " Error ", JOptionPane.ERROR_MESSAGE);
        }
        return tabla;
    }

    public void actualizarClientes(int dni, String nombrec, Date fechanac, String lugarn, char sexo, String observaciones) {

        try {
            String SQL = "UPDATE clientes SET nombrec=?,fechanac=?,lugarnac=?,sexoc=?,observacionc=? WHERE dnic=?";
            PreparedStatement consulta = MenuInicial.conectar.prepareStatement(SQL);

            consulta.setString(1, nombrec);
            consulta.setDate(2, fechanac);
            consulta.setString(3, lugarn);
            consulta.setString(4, String.valueOf(sexo));
            consulta.setString(5, observaciones);
            consulta.setInt(6, dni);
            consulta.execute();

            JOptionPane.showMessageDialog(null, "Cliente editado exitosamente", " Editado ", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Fallo de la actualizacion: " + e.getMessage(), " Error ", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    public void eliminarCliente(int dni) {
        String SQL = "DELETE FROM clientes WHERE dnic=?";
        try {
            PreparedStatement consulta = MenuInicial.conectar.prepareStatement(SQL);
            consulta.setInt(1, dni);
            consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, " Cliente eliminado exitosamente", " Eliminada ", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar registro " + e.getMessage());
        }
    }

}
