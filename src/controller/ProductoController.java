package controller;

import dao.ProductoDAO;
import model.Producto;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ProductoController {
    private final ProductoDAO dao = new ProductoDAO();
    private final Scanner scanner = new Scanner(System.in);

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- Menú de Productos ---");
            System.out.println("1. Registrar producto");
            System.out.println("2. Consultar producto por código");
            System.out.println("3. Actualizar producto");
            System.out.println("4. Eliminar producto (lógico)");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> registrarProducto();
                case 2 -> consultarProducto();
                case 3 -> actualizarProducto();
                case 4 -> eliminarProducto();
                case 5 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    private void registrarProducto() {
        try {
            Producto p = leerDatosProducto(true);
            dao.crearProducto(p);
            System.out.println("Producto registrado con éxito.");
        } catch (Exception e) {
            System.err.println("Error al registrar producto: " + e.getMessage());
        }
    }

    private void consultarProducto() {
        try {
            String codigo = pedirCodigoProducto();
            Producto p = dao.buscarPorCodigo(codigo);
            if (p != null) {
                mostrarProducto(p);
            } else {
                System.out.println("Producto no encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Error al consultar producto: " + e.getMessage());
        }
    }

    private void actualizarProducto() {
        try {
            String codigo = pedirCodigoProducto();
            Producto existente = dao.buscarPorCodigo(codigo);
            if (existente != null) {
                Producto actualizado = leerDatosProducto(false);
                actualizado.setCodigoProducto(codigo); // mantiene el mismo código
                dao.actualizarProducto(actualizado);
                System.out.println("Producto actualizado correctamente.");
            } else {
                System.out.println("Producto no encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
        }
    }

    private void eliminarProducto() {
        try {
            String codigo = pedirCodigoProducto();
            dao.eliminarLogico(codigo);
            System.out.println("Producto eliminado lógicamente.");
        } catch (Exception e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
        }
    }

    private Producto leerDatosProducto(boolean incluirCodigo) {
        Producto p = new Producto();

        if (incluirCodigo) {
            String codigo;
            do {
                System.out.print("Código del producto (máx 10 caracteres alfanuméricos): ");
                codigo = scanner.nextLine();
            } while (!Pattern.matches("^[a-zA-Z0-9]{1,10}$", codigo));
            p.setCodigoProducto(codigo);
        }

        System.out.print("Nombre: ");
        p.setNombre(scanner.nextLine());

        System.out.print("Descripción: ");
        p.setDescripcion(scanner.nextLine());

        p.setPrecioBase(pedirDouble("Precio base (> 0): ", 0));
        p.setPrecioVenta(pedirDouble("Precio de venta (> 0): ", 0));

        System.out.print("Categoría: ");
        p.setCategoria(scanner.nextLine());

        p.setCantidadDisponible(pedirEntero("Cantidad disponible (>= 0): ", 0));

        return p;
    }

    private String pedirCodigoProducto() {
        String codigo;
        do {
            System.out.print("Ingrese el código del producto: ");
            codigo = scanner.nextLine();
        } while (!Pattern.matches("^[a-zA-Z0-9]{1,10}$", codigo));
        return codigo;
    }

    private double pedirDouble(String mensaje, double minimo) {
        double valor;
        do {
            System.out.print(mensaje);
            valor = Double.parseDouble(scanner.nextLine());
        } while (valor <= minimo);
        return valor;
    }

    private int pedirEntero(String mensaje, int minimo) {
        int valor;
        do {
            System.out.print(mensaje);
            valor = Integer.parseInt(scanner.nextLine());
        } while (valor < minimo);
        return valor;
    }

    private void mostrarProducto(Producto p) {
        System.out.println("\n--- Detalles del producto ---");
        System.out.println("Código: " + p.getCodigoProducto());
        System.out.println("Nombre: " + p.getNombre());
        System.out.println("Descripción: " + p.getDescripcion());
        System.out.println("Precio base: " + p.getPrecioBase());
        System.out.println("Precio venta: " + p.getPrecioVenta());
        System.out.println("Categoría: " + p.getCategoria());
        System.out.println("Cantidad disponible: " + p.getCantidadDisponible());
    }
}
