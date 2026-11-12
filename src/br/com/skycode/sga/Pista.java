package br.com.skycode.sga;

public class Pista implements Imprimivel {
    private int id;
    private String numero;

    public Pista(int id, String numero) {
        this.id = id;
        this.setNumero(numero);
    }

    public int getId() { return id; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) {
        Validador.validarRegex(numero, "^[A-Z]\\d{2}$", "1 letra e 2 números (ex: A10)");
        this.numero = numero;
    }

    @Override
    public void imprimir() { System.out.printf("[Pista ID: %d] Número: %s\n", id, numero); }
}