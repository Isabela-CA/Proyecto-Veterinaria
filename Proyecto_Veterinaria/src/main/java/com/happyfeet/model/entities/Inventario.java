package com.happyfeet.model.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Inventario {
    private int id;
    private String nombre_producto;
    private int producto_tipo_id;
    private String descripcion;
    private String fabricante;
    private Integer proveedor_id;
    private String lote;
    private int cantidad_stock;
    private int stock_minimo;
    private String unidad_medida;
    private LocalDate fecha_vencimiento;
    private BigDecimal precio_compra;
    private BigDecimal precio_venta;
    private boolean requiere_receta;
    private boolean activo;
    private LocalDateTime fecha_registro;

    // Constructores
    public Inventario() {
    }

    public Inventario(int id) {
        this.id = id;
    }

    // Constructor sin ID ni fecha_registro (para insertar)
    public Inventario(String nombre_producto, int producto_tipo_id, String descripcion,
                      String fabricante, Integer proveedor_id, String lote, int cantidad_stock,
                      int stock_minimo, String unidad_medida, LocalDate fecha_vencimiento,
                      BigDecimal precio_compra, BigDecimal precio_venta, boolean requiere_receta,
                      boolean activo) {
        this.nombre_producto = nombre_producto;
        this.producto_tipo_id = producto_tipo_id;
        this.descripcion = descripcion;
        this.fabricante = fabricante;
        this.proveedor_id = proveedor_id;
        this.lote = lote;
        this.cantidad_stock = cantidad_stock;
        this.stock_minimo = stock_minimo;
        this.unidad_medida = unidad_medida;
        this.fecha_vencimiento = fecha_vencimiento;
        this.precio_compra = precio_compra;
        this.precio_venta = precio_venta;
        this.requiere_receta = requiere_receta;
        this.activo = activo;
    }

    // Constructor completo
    public Inventario(int id, String nombre_producto, int producto_tipo_id, String descripcion,
                      String fabricante, Integer proveedor_id, String lote, int cantidad_stock,
                      int stock_minimo, String unidad_medida, LocalDate fecha_vencimiento,
                      BigDecimal precio_compra, BigDecimal precio_venta, boolean requiere_receta,
                      boolean activo, LocalDateTime fecha_registro) {
        this(nombre_producto, producto_tipo_id, descripcion, fabricante, proveedor_id, lote,
                cantidad_stock, stock_minimo, unidad_medida, fecha_vencimiento, precio_compra,
                precio_venta, requiere_receta, activo);
        this.id = id;
        this.fecha_registro = fecha_registro;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public int getProducto_tipo_id() {
        return producto_tipo_id;
    }

    public void setProducto_tipo_id(int producto_tipo_id) {
        this.producto_tipo_id = producto_tipo_id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Integer getProveedor_id() {
        return proveedor_id;
    }

    public void setProveedor_id(Integer proveedor_id) {
        this.proveedor_id = proveedor_id;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public int getCantidad_stock() {
        return cantidad_stock;
    }

    public void setCantidad_stock(int cantidad_stock) {
        this.cantidad_stock = cantidad_stock;
    }

    public int getStock_minimo() {
        return stock_minimo;
    }

    public void setStock_minimo(int stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public LocalDate getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(LocalDate fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public BigDecimal getPrecio_compra() {
        return precio_compra;
    }

    public void setPrecio_compra(BigDecimal precio_compra) {
        this.precio_compra = precio_compra;
    }

    public BigDecimal getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(BigDecimal precio_venta) {
        this.precio_venta = precio_venta;
    }

    public boolean isRequiere_receta() {
        return requiere_receta;
    }

    public void setRequiere_receta(boolean requiere_receta) {
        this.requiere_receta = requiere_receta;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(LocalDateTime fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    // Métodos de utilidad
    public boolean tieneStockBajo() {
        return cantidad_stock <= stock_minimo;
    }

    public boolean estaVencido() {
        return fecha_vencimiento != null && fecha_vencimiento.isBefore(LocalDate.now());
    }

    public boolean proximoAVencer(int diasAnticipacion) {
        return fecha_vencimiento != null &&
                fecha_vencimiento.isBefore(LocalDate.now().plusDays(diasAnticipacion)) &&
                !estaVencido();
    }

    @Override
    public String toString() {
        return "Inventario{" +
                "id=" + id +
                ", nombre_producto='" + nombre_producto + '\'' +
                ", producto_tipo_id=" + producto_tipo_id +
                ", descripcion='" + descripcion + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", proveedor_id=" + proveedor_id +
                ", lote='" + lote + '\'' +
                ", cantidad_stock=" + cantidad_stock +
                ", stock_minimo=" + stock_minimo +
                ", unidad_medida='" + unidad_medida + '\'' +
                ", fecha_vencimiento=" + fecha_vencimiento +
                ", precio_compra=" + precio_compra +
                ", precio_venta=" + precio_venta +
                ", requiere_receta=" + requiere_receta +
                ", activo=" + activo +
                ", fecha_registro=" + fecha_registro +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventario that = (Inventario) o;
        return id == that.id &&
                producto_tipo_id == that.producto_tipo_id &&
                cantidad_stock == that.cantidad_stock &&
                stock_minimo == that.stock_minimo &&
                requiere_receta == that.requiere_receta &&
                activo == that.activo &&
                Objects.equals(nombre_producto, that.nombre_producto) &&
                Objects.equals(descripcion, that.descripcion) &&
                Objects.equals(fabricante, that.fabricante) &&
                Objects.equals(proveedor_id, that.proveedor_id) &&
                Objects.equals(lote, that.lote) &&
                Objects.equals(unidad_medida, that.unidad_medida) &&
                Objects.equals(fecha_vencimiento, that.fecha_vencimiento) &&
                Objects.equals(precio_compra, that.precio_compra) &&
                Objects.equals(precio_venta, that.precio_venta) &&
                Objects.equals(fecha_registro, that.fecha_registro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre_producto, producto_tipo_id, descripcion, fabricante,
                proveedor_id, lote, cantidad_stock, stock_minimo, unidad_medida, fecha_vencimiento,
                precio_compra, precio_venta, requiere_receta, activo, fecha_registro);
    }
}