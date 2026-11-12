package br.com.skycode.sga;

import java.time.LocalDate;
import java.time.LocalTime;

public class Voo implements Imprimivel {
    private int id;
    private String numero;
    private String observacao;
    private String piloto;
    private String copiloto;
    private String origem;
    private String destino;
    private LocalDate data;
    private LocalTime hora;
    private Pista pista;
    private Aeromodelo aeromodelo;

    public Voo(int id, String numero, String observacao, String piloto, String copiloto, 
               String origem, String destino, LocalDate data, LocalTime hora, Pista pista, Aeromodelo aeromodelo) {
        this.id = id;
        this.setNumero(numero);
        this.observacao = observacao; 
        this.piloto = piloto;
        this.copiloto = copiloto;
        this.setOrigem(origem);
        this.setDestino(destino);
        this.data = data;
        this.hora = hora;
        this.pista = pista;
        this.aeromodelo = aeromodelo;
    }

    public int getId() { return id; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) {
        Validador.validarRegex(numero, "^[A-Z]{3}\\d{6}$", "3 letras e 6 números (ex: AZU986535)");
        this.numero = numero;
    }
    public String getOrigem() { return origem; }
    public void setOrigem(String origem) {
        Validador.validarRegex(origem, "^[A-Z]{3}$", "3 letras (ex: GRU)");
        this.origem = origem;
    }
    public String getDestino() { return destino; }
    public void setDestino(String destino) {
        Validador.validarRegex(destino, "^[A-Z]{3}$", "3 letras (ex: LIS)");
        this.destino = destino;
    }
    public String getObservacao() { return observacao; }
    public String getPiloto() { return piloto; }
    public String getCopiloto() { return copiloto; }
    public LocalDate getData() { return data; }
    public LocalTime getHora() { return hora; }
    public Pista getPista() { return pista; }
    public Aeromodelo getAeromodelo() { return aeromodelo; }

    @Override
    public void imprimir() {
        System.out.printf("[Voo ID: %d] %s | Origem: %s -> Destino: %s | Data: %s %s | Pista: %s | Aeronave: %s\n",
                id, numero, origem, destino, data.toString(), hora.toString(), pista.getNumero(), aeromodelo.getModelo());
    }
}