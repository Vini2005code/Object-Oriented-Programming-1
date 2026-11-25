package br.com.skycode.sga;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Aeroporto {
    // Motivo: Manter os dados em memória durante a execução evita leituras repetidas de disco a cada consulta.
    private List<Aeromodelo> aeromodelos = new ArrayList<>();
    private List<Companhia> companhias = new ArrayList<>();
    private List<Hangar> hangares = new ArrayList<>();
    private List<Pista> pistas = new ArrayList<>();
    private List<Voo> voos = new ArrayList<>();

    public void carregarDadosIniciais() {
        ArmazenamentoTxt.carregar(this);
    }

    public void salvarState() {
        ArmazenamentoTxt.salvar(this);
    }

    // ==========================================
    // CRUD - COMPANHIAS
    // ==========================================
    public void cadastrarCompanhia(String nome, String cnpj) {
        int novoId = companhias.stream().mapToInt(Companhia::getId).max().orElse(0) + 1;
        companhias.add(new Companhia(novoId, nome, cnpj));
        salvarState();
    }

    public void listarCompanhias() {
        if (companhias.isEmpty()) {
            System.out.println("Nenhuma companhia cadastrada.");
            return;
        }
        for (Companhia c : companhias) {
            c.imprimir();
        }
    }

    public Companhia buscarCompanhia(int id) {
        return companhias.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NaoEncontradoException("Companhia ID " + id + " não encontrada."));
    }

    public void editarCompanhia(int id, String novoNome, String novoCnpj) {
        Companhia c = buscarCompanhia(id);
        c.setNome(novoNome);
        c.setCnpj(novoCnpj);
        salvarState();
    }

    public void removerCompanhia(int id) {
        Companhia c = buscarCompanhia(id);
        // Motivo: Impede exclusão de companhia que ainda possui avião vinculado, evitando referência inconsistente.
        boolean emUso = aeromodelos.stream()
                .anyMatch(a -> a instanceof Aviao && ((Aviao) a).getCompanhia().getId() == id);
        if (emUso) {
            throw new ValidacaoException("Erro: Companhia possui aviões vinculados e não pode ser excluída.");
        }
        companhias.remove(c);
        salvarState();
    }

    // ==========================================
    // CRUD - PISTAS
    // ==========================================
    public void cadastrarPista(String numero) {
        int novoId = pistas.stream().mapToInt(Pista::getId).max().orElse(0) + 1;
        pistas.add(new Pista(novoId, numero));
        salvarState();
    }

    public void listarPistas() {
        if (pistas.isEmpty()) {
            System.out.println("Nenhuma pista cadastrada.");
            return;
        }
        for (Pista p : pistas) {
            p.imprimir();
        }
    }

    public Pista buscarPista(int id) {
        return pistas.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NaoEncontradoException("Pista ID " + id + " não encontrada."));
    }

    public void editarPista(int id, String novoNumero) {
        Pista p = buscarPista(id);
        p.setNumero(novoNumero);
        salvarState();
    }

    public void removerPista(int id) {
        Pista p = buscarPista(id);
        // Motivo: Pistas associadas a voos fazem parte do histórico operacional e não devem ser removidas.
        boolean emUso = voos.stream().anyMatch(v -> v.getPista().getId() == id);
        if (emUso) {
            throw new ValidacaoException("Erro: Pista vinculada a um voo não pode ser excluída.");
        }
        pistas.remove(p);
        salvarState();
    }

    // ==========================================
    // AEROMODELOS, HANGARES E VOOS
    // ==========================================
    public void listarAeromodelos() {
        if (aeromodelos.isEmpty()) {
            System.out.println("Nenhum aeromodelo cadastrado.");
            return;
        }
        // [POLIMORFISMO] Cada subclasse executa sua própria implementação de imprimir().
        for (Aeromodelo a : aeromodelos) {
            a.imprimir();
        }
    }

    public Aeromodelo buscarAeromodelo(int id) {
        return aeromodelos.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NaoEncontradoException("Aeromodelo ID " + id + " não encontrado."));
    }

    public void listarHangares() {
        if (hangares.isEmpty()) {
            System.out.println("Nenhum hangar cadastrado.");
            return;
        }
        for (Hangar h : hangares) {
            h.imprimir();
        }
    }

    public Hangar buscarHangar(int id) {
        return hangares.stream()
                .filter(h -> h.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NaoEncontradoException("Hangar ID " + id + " não encontrado."));
    }

    public void alocarHangar(int idHangar, int idAviao) {
        Hangar h = buscarHangar(idHangar);
        Aeromodelo a = buscarAeromodelo(idAviao);

        if (!(a instanceof Aviao)) {
            throw new ValidacaoException("Apenas aviões podem ser alocados em hangares.");
        }
        if (h.isOcupado()) {
            throw new ValidacaoException("O hangar selecionado já está ocupado.");
        }

        // Motivo: Evita que o mesmo avião fique alocado em dois hangares ao mesmo tempo.
        for (Hangar hangarAux : hangares) {
            if (hangarAux.isOcupado() && hangarAux.getAviao().getId() == idAviao) {
                throw new ValidacaoException("Este avião já está alocado no Hangar ID " + hangarAux.getId() + ".");
            }
        }

        h.adicionarAviao((Aviao) a);
        salvarState();
    }

    public void desocuparHangar(int idHangar) {
        Hangar h = buscarHangar(idHangar);
        if (!h.isOcupado()) {
            throw new ValidacaoException("O hangar já está livre.");
        }
        h.removerAviao();
        salvarState();
    }

    public void registrarVoo(
            String numero,
            String observacao,
            String piloto,
            String copiloto,
            String origem,
            String destino,
            LocalDate data,
            LocalTime hora,
            int idPista,
            int idAeromodelo
    ) {
        Pista pista = buscarPista(idPista);
        Aeromodelo aeromodelo = buscarAeromodelo(idAeromodelo);
        int novoId = voos.stream().mapToInt(Voo::getId).max().orElse(0) + 1;

        Voo novoVoo = new Voo(
                novoId,
                numero,
                observacao,
                piloto,
                copiloto,
                origem,
                destino,
                data,
                hora,
                pista,
                aeromodelo
        );
        voos.add(novoVoo);
        salvarState();
    }

    public void listarVoos() {
        if (voos.isEmpty()) {
            System.out.println("Nenhum voo registrado.");
            return;
        }
        for (Voo v : voos) {
            v.imprimir();
        }
    }

    public List<Aeromodelo> getAeromodelos() {
        return aeromodelos;
    }

    public List<Companhia> getCompanhias() {
        return companhias;
    }

    public List<Hangar> getHangares() {
        return hangares;
    }

    public List<Pista> getPistas() {
        return pistas;
    }

    public List<Voo> getVoos() {
        return voos;
    }
}