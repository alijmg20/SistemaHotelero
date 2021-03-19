
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

public class MPersonal {

    public MPersonal() {
    }
    
    
    
    
    public void insertarPersonal(int dni, String nombrev, String direccionv, int telefonov, String observacionesv, float sueldov) {

        try {

            String SQL = "INSERT INTO vendedores (dniv,nombrev,direccionv,telefonov,observacionesv,sueldov) VALUES (?,?,?,?,?,?)";
            PreparedStatement consulta = MenuInicial.conectar.prepareStatement(SQL);

            consulta.setInt(1, dni);
            consulta.setString(2, nombrev);
            consulta.setString(3, direccionv);
            consulta.setInt(4, telefonov);
            consulta.setString(5, observacionesv);
            consulta.setFloat(6, sueldov);
            consulta.execute();
            JOptionPane.showMessageDialog(null, "Personal Registrado exitosamente", " Registrado ", JOptionPane.INFORMATION_MESSAGE);

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo del Registro" + e.getMessage(), " Error ", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Mostrar Datos 
    public DefaultTableModel mostrarPersonal() {
        String[] titulos = {"DNI", "nombre del Personal", "Direccion", " telefono ", " observaciones ", " sueldo "};

        String[] registros = new String[6];
        DefaultTableModel tabla = new DefaultTableModel(null, titulos);
        String SQL = "SELECT * FROM vendedores order by dniv";

        try {
            Statement consulta = MenuInicial.conectar.createStatement();
            ResultSet resultado = consulta.executeQuery(SQL);

            while (resultado.next()) {
                registros[0] = resultado.getString("dniv");
                registros[1] = resultado.getString("nombrev");
                
                registros[2] = resultado.getString("direccionv");;
                registros[3] = resultado.getString("telefonov");
                registros[4] = resultado.getString("observacionesv");
                registros[5] = resultado.getString("sueldov");
                tabla.addRow(registros);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Fallo para mostrar :" + e.getMessage(), " Error ", JOptionPane.ERROR_MESSAGE);
        }
        return tabla;
    }

    public void actualizarPersonal(int dni, String nombrev, String direccionv, int telefonov, String observacionesv, float sueldov) {

        try {
            String SQL = "UPDATE vendedores SET nombrev=?,direccionv=?,telefonov=?,observacionesv=?,sueldov=? WHERE dniv=?";
            PreparedStatement consulta = MenuInicial.conectar.prepareStatement(SQL);

            consulta.setString(1, nombrev);
            consulta.setString(2, direccionv);
            consulta.setInt(3, telefonov);
            consulta.setString(4, observacionesv);
            consulta.setFloat(5, sueldov);
            consulta.setInt(6, dni);
            consulta.execute();

            JOptionPane.showMessageDialog(null, "Personal editado exitosamente", " Editado ", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Fallo de la actualizacion: " + e.getMessage(), " Error ", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    public void eliminarPersonal(int dni) {
        String SQL = "DELETE FROM vendedores WHERE dniv=?";
        try {
            PreparedStatement consulta = MenuInicial.conectar.prepareStatement(SQL);
            consulta.setInt(1, dni);
            consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, " Personal eliminado exitosamente", " Eliminada ", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar registro " + e.getMessage());
        }
    }
}
