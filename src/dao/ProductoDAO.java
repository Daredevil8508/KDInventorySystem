package dao;

import model.Producto;
import util.Conexion;

import java.sql.*;
import java.util.*;

public class ProductoDAO {
    public void crearProducto(Producto producto) throws SQLException {
        String sql = "INSERT INTO productos VALUES (?, ?, ?, ?, ?, ?, ?, TRUE)";
        try (Connection con = Conexion.conectar(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, producto.getCodigoProducto());
            stmt.setString(2, producto.getNombre());
            stmt.setString(3, producto.getDescripcion());
            stmt.setDouble(4, producto.getPrecioBase());
            stmt.setDouble(5, producto.getPrecioVenta());
            stmt.setString(6, producto.getCategoria());
            stmt.setInt(7, producto.getCantidadDisponible());
            stmt.executeUpdate();
        }
    }

    public Producto buscarPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT * FROM productos WHERE codigo_producto = ? AND activo = TRUE";
        try (Connection con = Conexion.conectar(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Producto p = new Producto();
                // Setters con rs.getXXX
                return p;
            }
        }
        return null;
    }

    public void actualizarProducto(Producto producto) throws SQLException {
        String sql = "UPDATE productos SET nombre=?, descripcion=?, precio_base=?, precio_venta=?, categoria=?, cantidad_disponible=? WHERE codigo_producto=?";
        try (Connection con = Conexion.conectar(); PreparedStatement stmt = con.prepareStatement(sql)) {
            // Setters
            stmt.executeUpdate();
        }
    }

    public void eliminarLogico(String codigo) throws SQLException {
        String sql = "UPDATE productos SET activo = FALSE WHERE codigo_producto = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            stmt.executeUpdate();
        }
    }
}
