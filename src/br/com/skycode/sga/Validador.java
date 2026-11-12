package br.com.skycode.sga;

public class Validador {
    public static void validarRegex(String valor, String regex, String formatoExigido) {
        if (valor == null || !valor.matches(regex)) {
            throw new ValidacaoException("Formato inválido. Exigido: " + formatoExigido);
        }
    }

    public static void validarPositivo(int valor, String campo) {
        if (valor < 0) {
            throw new ValidacaoException(campo + " não pode ser negativo.");
        }
    }
    
    public static void validarTextoObrigatorio(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacaoException("O campo " + campo + " é obrigatório.");
        }
    }
}