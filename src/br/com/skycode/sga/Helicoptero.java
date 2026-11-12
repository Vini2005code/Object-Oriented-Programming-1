package br.com.skycode.sga;

public class Helicoptero extends Aeromodelo {
    private int capacidade;
    private String cor;

    public Helicoptero(int id, String marca, String modelo, int capacidade, String cor) {
        super(id, marca, modelo);
        this.setCapacidade(capacidade);
        this.setCor(cor);
    }

    public int getCapacidade() { return capacidade; }
    public void setCapacidade(int capacidade) {
        Validador.validarPositivo(capacidade, "Capacidade");
        this.capacidade = capacidade;
    }

    public String getCor() { return cor; }
    public void setCor(String cor) {
        Validador.validarTextoObrigatorio(cor, "Cor");
        this.cor = cor;
    }

    @Override
    public String getTipo() { return "HELICOPTERO"; }

    @Override
    public void imprimir() {
        System.out.printf("[Helicóptero ID: %d] %s %s - Cor: %s | Cap: %d\n", id, marca, modelo, cor, capacidade);
    }
}