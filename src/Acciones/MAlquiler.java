
package Acciones;

import java.awt.HeadlessException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vistas.MenuInicial;

public class MAlquiler {

    public MAlquiler() {
    }
    
    public void obtenerPersonal(JComboBox cb) {
        try {
            String SQL = "SELECT nombrev FROM vendedores";
            PreparedStatement consulta = MenuInicial.conectar.prepareStatement(SQL);
            ResultSet resultado = consulta.executeQuery();
            cb.addItem("Seleccione una opcion");
            while (resultado.next()) {
                cb.addItem(resultado.getString("nombrev"));
            }

        } catch (Exception ex) {

        }
    }

    public int busquedaPersonal(String nombrepersonal) {

        String SQL = "SELECT dniv FROM vendedores WHERE nombrev = '" + nombrepersonal + "'";
        try {
            int codigo = 0;
            Statement consultaCodigo = MenuInicial.conectar.createStatement();
            ResultSet resultado = consultaCodigo.executeQuery(SQL);
            resultado.next();
            return codigo = resultado.getInt("dniv");
        } catch (Exception e) {

        }
        return -1;
    }
    
    
    
    public void obtenerClientes(JComboBox cb) {
        try {
            String SQL = "SELECT nombrec FROM clientes";
            PreparedStatement consulta = MenuInicial.conectar.prepareStatement(SQL);
            ResultSet resultado = consulta.executeQuery();
            cb.addItem("Seleccione una opcion");
            while (resultado.next()) {
                cb.addItem(resultado.getString("nombrec"));
            }

        } catch (Exception ex) {

        }
    }

    public int busquedaClientes(String nombretipo) {

        String SQL = "SELECT dnic FROM clientes WHERE nombrec = '" + nombretipo + "'";
        try {
            int codigo = 0;
            Statement consultaCodigo = MenuInicial.conectar.createStatement();
            ResultSet resultado = consultaCodigo.executeQuery(SQL);
            resultado.next();
            return codigo = resultado.getInt("dnic");
        } catch (Exception e) {

        }
        return -1;
    }
    
    
    
    public void obtenerhab(JComboBox cb) {
        try {
            String SQL = "SELECT codigohab FROM habitaciones";
            PreparedStatement consulta = MenuInicial.conectar.prepareStatement(SQL);
            ResultSet resultado = consulta.executeQuery();
            cb.addItem("Seleccione una opcion");
            while (resultado.next()) {
                cb.addItem(resultado.getString("codigohab"));
            }

        } catch (Exception ex) {

        }
    }
    
    public void insertarAlquiler(String codigoalq,float precioalq,Date fechaent,Date fechaSal, String observaciones, String nombrev,String nombrec, String codigohab) {

        try {
            int personal = this.busquedaPersonal(nombrev);
            int cliente = this.busquedaClientes(nombrec);
            
            
            String SQL = "INSERT INTO alquileres (codigoalq,precioalq,fechaent,fechaSal,observaciones,vendedor,cliente,habitacion) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement consulta = MenuInicial.conectar.prepareStatement(SQL);

            consulta.setString(1, codigoalq);
            consulta.setFloat(2, precioalq);
            consulta.setDate(3, fechaent);
            consulta.setDate(4, fechaSal);
            consulta.setString(5, observaciones);
            consulta.setInt(6, personal);
            consulta.setInt(7, cliente);
            consulta.setString(8, codigohab);
            consulta.execute();
            JOptionPane.showMessageDialog(null, "Alquiler Registrado exitosamente", " Registrado ", JOptionPane.INFORMATION_MESSAGE);

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo del Registro" + e.getMessage(), " Error ", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Mostrar Datos 
    public DefaultTableModel mostrarAlquileres() {
        String[] titulos = {"codigo", "Precio", "fecha entrada ", "fecha salida ", " observaciones ", " Personal ","Cliente","Codigo habitacion"};
        

        String[] registros = new String[8];
        DefaultTableModel tabla = new DefaultTableModel(null, titulos);
        String SQL = "SELECT * FROM alquileres "
                + "   INNER JOIN vendedores "
                + "   ON alquileres.vendedor = vendedores.dniv   "
                + "   INNER JOIN clientes "
                + "   ON alquileres.cliente=clientes.dnic";

        try {
            Statement consulta = MenuInicial.conectar.createStatement();
            ResultSet resultado = consulta.executeQuery(SQL);

            while (resultado.next()) {
                registros[0] = resultado.getString("codigoalq");
                registros[1] = resultado.getString("precioalq");
                String FormatoFecha = resultado.getString("fechaent");
                String fecha = FormatoFecha.replace("-", "/");
                registros[2] = fecha;
                String FormatoFecha2 = resultado.getString("fechaSal");
                String fecha2 = FormatoFecha2.replace("-", "/");
                registros[3] = fecha2;
                registros[4] = resultado.getString("observaciones");
                registros[5] = resultado.getString("nombrev");
                registros[6] = resultado.getString("nombrec");
                registros[7] = resultado.getString("habitacion");
                tabla.addRow(registros);
                
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Fallo para mostrar :" + e.getMessage(), " Error ", JOptionPane.ERROR_MESSAGE);
        }
        return tabla;
    }

    public void actualizarAlquileres(String codigoalq,float precioalq,Date fechaent,Date fechaSal, String observaciones, String nombrev,String nombrec, String codigohab) {

        try {

            int personal = this.busquedaPersonal(nombrev);
            int cliente = this.busquedaClientes(nombrec);

            String SQL = "UPDATE alquileres SET precioalq=?,fechaent=?,fechaSal=?,observaciones=?,vendedor=?,cliente=?,habitacion=? WHERE codigoalq=?";
            PreparedStatement consulta = MenuInicial.conectar.prepareStatement(SQL);

            
            consulta.setFloat(1, precioalq);
            consulta.setDate(2, fechaent);
            consulta.setDate(3, fechaSal);
            consulta.setString(4, observaciones);
            consulta.setInt(5, personal);
            consulta.setInt(6, cliente);
            consulta.setString(7, codigohab);
            consulta.setString(8, codigoalq);
            consulta.execute();

            JOptionPane.showMessageDialog(null, " Alquiler editado exitosamente", " Editado ", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Fallo de la actualizacion: " + e.getMessage(), " Error ", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminarAlquiler(String codigoalq) {
        String SQL = "DELETE FROM alquileres WHERE codigoalq=?";
        try {
            PreparedStatement consulta = MenuInicial.conectar.prepareStatement(SQL);
            consulta.setString(1, codigoalq);
            consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, " alquiler Eliminado exitosamente", " Eliminada ", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar alquiler " + e.getMessage());
        }
    }
    
    
    
    
}
