package br.com.skycode.sga;

public abstract class Aeromodelo implements Imprimivel {
    protected int id;
    protected String marca;
    protected String modelo;

    public Aeromodelo(int id, String marca, String modelo) {
        this.id = id;
        this.setMarca(marca);
        this.setModelo(modelo);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getMarca() { return marca; }
    public void setMarca(String marca) {
        Validador.validarTextoObrigatorio(marca, "Marca");
        this.marca = marca;
    }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) {
        Validador.validarTextoObrigatorio(modelo, "Modelo");
        this.modelo = modelo;
    }

    public abstract String getTipo();
}