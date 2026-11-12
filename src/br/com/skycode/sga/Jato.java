package br.com.skycode.sga;

public class Jato extends Aeromodelo {
    private String cor;
    private int velocidade;

    public Jato(int id, String marca, String modelo, String cor, int velocidade) {
        super(id, marca, modelo);
        this.setCor(cor);
        this.setVelocidade(velocidade);
    }

    public String getCor() { return cor; }
    public void setCor(String cor) {
        Validador.validarTextoObrigatorio(cor, "Cor");
        this.cor = cor;
    }

    public int getVelocidade() { return velocidade; }
    public void setVelocidade(int velocidade) {
        Validador.validarPositivo(velocidade, "Velocidade");
        this.velocidade = velocidade;
    }

    @Override
    public String getTipo() { return "JATO"; }

    @Override
    public void imprimir() {
        System.out.printf("[Jato ID: %d] %s %s - Cor: %s | Vel: %d km/h\n", id, marca, modelo, cor, velocidade);
    }
}