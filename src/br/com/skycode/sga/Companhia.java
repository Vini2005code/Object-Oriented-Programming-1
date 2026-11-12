package br.com.skycode.sga;

public class Companhia implements Imprimivel {
    private int id;
    private String nome;
    private String cnpj;

    public Companhia(int id, String nome, String cnpj) {
        this.id = id;
        this.setNome(nome);
        this.setCnpj(cnpj);
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) {
        Validador.validarTextoObrigatorio(nome, "Nome da Companhia");
        this.nome = nome;
    }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) {
        Validador.validarRegex(cnpj, "^\\d{14}$", "14 números sem pontuação");
        this.cnpj = cnpj;
    }

    @Override
    public void imprimir() {
        System.out.printf("[Companhia ID: %d] %s - CNPJ: %s\n", id, nome, cnpj);
    }
}