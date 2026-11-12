package br.com.skycode.sga;

public abstract class ExcecaoSGA extends RuntimeException {
    public ExcecaoSGA(String mensagem) {
        super(mensagem);
    }
}