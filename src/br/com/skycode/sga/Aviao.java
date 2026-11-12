package br.com.skycode.sga;

public class Aviao extends Aeromodelo {
    private String prefixo;
    private int capacidade;
    private Companhia companhia;

    public Aviao(int id, String marca, String modelo, String prefixo, int capacidade, Companhia companhia) {
        super(id, marca, modelo);
        this.setPrefixo(prefixo);
        this.setCapacidade(capacidade);
        this.setCompanhia(companhia);
    }

    public String getPrefixo() { return prefixo; }
    public void setPrefixo(String prefixo) {
        Validador.validarRegex(prefixo, "^[A-Z]{3}\\d{4}$", "3 letras e 4 números (ex: PRA1234)");
        this.prefixo = prefixo;
    }

    public int getCapacidade() { return capacidade; }
    public void setCapacidade(int capacidade) {
        Validador.validarPositivo(capacidade, "Capacidade");
        this.capacidade = capacidade;
    }

    public Companhia getCompanhia() { return companhia; }
    public void setCompanhia(Companhia companhia) {
        if (companhia == null) throw new ValidacaoException("Companhia é obrigatória para o avião.");
        this.companhia = companhia;
    }

    @Override
    public String getTipo() { return "AVIAO"; }

    @Override
    public void imprimir() {
        System.out.printf("[Avião ID: %d] %s %s - Prefixo: %s | Cap: %d | Companhia: %s\n", 
                          id, marca, modelo, prefixo, capacidade, companhia.getNome());
    }
}