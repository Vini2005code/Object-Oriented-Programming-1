package br.com.skycode.sga;

public class Hangar implements Imprimivel {
    private int id;
    private String local;
    private Aviao aviao;

    public Hangar(int id, String local) {
        this.id = id;
        this.setLocal(local);
    }

    public int getId() { return id; }
    public String getLocal() { return local; }
    public void setLocal(String local) {
        Validador.validarTextoObrigatorio(local, "Local");
        this.local = local;
    }
    public Aviao getAviao() { return aviao; }
    public boolean isOcupado() { return this.aviao != null; }

    public void adicionarAviao(Aviao aviao) {
        if (isOcupado()) throw new ValidacaoException("Hangar já está ocupado!");
        this.aviao = aviao;
    }

    public void removerAviao() { this.aviao = null; }

    @Override
    public void imprimir() {
        String status = isOcupado() ? "Ocupado pelo Avião ID " + aviao.getId() : "Livre";
        System.out.printf("[Hangar ID: %d] Local: %s | Status: %s\n", id, local, status);
    }
}