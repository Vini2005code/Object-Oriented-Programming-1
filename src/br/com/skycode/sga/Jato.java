package br.com.skycode.sga;

public class Jato extends Aeromodelo {

    //  BLOCO DE ATRIBUTOS (separados por tipo) 
    private String cor;        // Atributo do tipo String
    private int velocidade;    // Atributo do tipo int

    //  CONSTRUTOR 
    public Jato(int id, String marca, String modelo, String cor, int velocidade) {
        super(id, marca, modelo);
        this.setCor(cor);
        this.setVelocidade(velocidade);
    }

    // BLOCO DE MÉTODOS PARA O TIPO STRING (cor)
    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        Validador.validarTextoObrigatorio(cor, "Cor");
        this.cor = cor;
    }

    // BLOCO DE MÉTODOS PARA O TIPO INT (velocidade)
    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        Validador.validarPositivo(velocidade, "Velocidade");
        this.velocidade = velocidade;
    }

    // MÉTODOS OBRIGATÓRIOS / POLIMORFISMO
    @Override
    public String getTipo() {
        return "JATO";
    }

    @Override
    public void imprimir() {
        System.out.printf("[Jato ID: %d] %s %s - Cor: %s | Vel: %d km/h\n", 
                          id, marca, modelo, cor, velocidade);
    }
}