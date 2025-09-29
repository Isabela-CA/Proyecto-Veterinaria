package com.happyfeet.model.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Inventario {
    private int id;
    private String nombre_producto;
    private int producto_tipo_id;
    private String descripcion;
    private String fabricante;
    private String lote;
    private int cantidad_stock;
    private int stock_minimo;
    private LocalDate fecha_vencimiento;
    private BigDecimal precio_venta;

    public Inventario() {
    }

    public Inventario(int id) {
        this.id = id;
    }

    public Inventario(String nombre_producto, int producto_tipo_id, String descripcion, String fabricante, String lote, int cantidad_stock, int stock_minimo, LocalDate fecha_vencimiento, BigDecimal precio_venta) {
        this.nombre_producto = nombre_producto;
        this.producto_tipo_id = producto_tipo_id;
        this.descripcion = descripcion;
        this.fabricante = fabricante;
        this.lote = lote;
        this.cantidad_stock = cantidad_stock;
        this.stock_minimo = stock_minimo;
        this.fecha_vencimiento = fecha_vencimiento;
        this.precio_venta = precio_venta;
    }

    public Inventario(int id, String nombre_producto, int producto_tipo_id, String descripcion, String fabricante, String lote, int cantidad_stock, int stock_minimo, LocalDate fecha_vencimiento, BigDecimal precio_venta) {
        this(nombre_producto, producto_tipo_id,descripcion,fabricante,lote,cantidad_stock,stock_minimo,fecha_vencimiento,precio_venta);
        this.id = id;
    }

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

    public LocalDate getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(LocalDate fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public BigDecimal getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(BigDecimal precio_venta) {
        this.precio_venta = precio_venta;
    }

    // metodos de negocio
    public boolean necesitaReposicion() {
        return cantidad_stock <= stock_minimo;
    }

    public boolean estaVencido() {
        return fecha_vencimiento != null && fecha_vencimiento.isBefore(LocalDate.now());
    }

    public boolean estaPorVencerse(int dias) {
        return fecha_vencimiento != null &&
                fecha_vencimiento.isBefore(LocalDate.now().plusDays(dias)) &&
                !fecha_vencimiento.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        return "Invetario{" +
                "id=" + id +
                ", nombre_producto='" + nombre_producto + '\'' +
                ", producto_tipo_id=" + producto_tipo_id +
                ", descripcion='" + descripcion + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", lote='" + lote + '\'' +
                ", cantidad_stock=" + cantidad_stock +
                ", stock_minimo=" + stock_minimo +
                ", fecha_vencimiento=" + fecha_vencimiento +
                ", precio_venta=" + precio_venta +
                '}';
    }
}
