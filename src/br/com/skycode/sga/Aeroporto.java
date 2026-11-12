package br.com.skycode.sga;

import java.util.ArrayList;
import java.util.List;

public class Aeroporto {
    // Motivo: Utilização de Listas (Collections) para manter os dados em memória RAM durante a execução, 
    // minimizando a necessidade de acessos constantes ao disco (I/O) e otimizando a performance.
    private List<Aeromodelo> aeromodelos = new ArrayList<>();
    private List<Companhia> companhias = new ArrayList<>();
    private List<Hangar> hangares = new ArrayList<>();
    private List<Pista> pistas = new ArrayList<>();
    private List<Voo> voos = new ArrayList<>();

    public void carregarDadosIniciais() { ArmazenamentoTxt.carregar(this); }
    public void salvarState() { ArmazenamentoTxt.salvar(this); }

    // ==========================================
    // CRUD - COMPANHIAS
    // ==========================================
    public void cadastrarCompanhia(String nome, String cnpj) {
        // Motivo: Geração dinâmica de ID baseada no maior ID existente para evitar conflitos após remoções.
        int novoId = companhias.stream().mapToInt(Companhia::getId).max().orElse(0) + 1;
        companhias.add(new Companhia(novoId, nome, cnpj));
        salvarState();
    }

    public void listarCompanhias() {
        if (companhias.isEmpty()) System.out.println("Nenhuma companhia cadastrada.");
        for (Companhia c : companhias) c.imprimir();
    }

    public Companhia buscarCompanhia(int id) {
        return companhias.stream().filter(c -> c.getId() == id).findFirst()
                .orElseThrow(() -> new NaoEncontradoException("Companhia ID " + id + " não encontrada."));
    }

    public void editarCompanhia(int id, String novoNome, String novoCnpj) {
        Companhia c = buscarCompanhia(id);
        c.setNome(novoNome); 
        c.setCnpj(novoCnpj); // A própria entidade valida via RegEx
        salvarState();
    }

    public void removerCompanhia(int id) {
        Companhia c = buscarCompanhia(id);
        // Motivo: RN11 - Garantir a integridade relacional. Não podemos ter um avião "órfão" no sistema.
        boolean emUso = aeromodelos.stream()
                .anyMatch(a -> a instanceof Aviao && ((Aviao) a).getCompanhia().getId() == id);
        if (emUso) throw new ValidacaoException("Erro (RN11): Companhia possui aviões e não pode ser excluída.");
        
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
        if (pistas.isEmpty()) System.out.println("Nenhuma pista cadastrada.");
        for (Pista p : pistas) p.imprimir();
    }

    public Pista buscarPista(int id) {
        return pistas.stream().filter(p -> p.getId() == id).findFirst()
                .orElseThrow(() -> new NaoEncontradoException("Pista ID " + id + " não encontrada."));
    }

    public void editarPista(int id, String novoNumero) {
        Pista p = buscarPista(id);
        p.setNumero(novoNumero);
        salvarState();
    }

    public void removerPista(int id) {
        Pista p = buscarPista(id);
        // Motivo: RN11 - Pistas ligadas a voos são registos históricos e não podem desaparecer.
        boolean emUso = voos.stream().anyMatch(v -> v.getPista().getId() == id);
        if (emUso) throw new ValidacaoException("Erro (RN11): Pista registrada em um voo não pode ser excluída.");
        
        pistas.remove(p);
        salvarState();
    }

    // ==========================================
    // OPERAÇÕES DE HANGARES E AEROMODELOS
    // ==========================================
    public void listarHangares() {
        if (hangares.isEmpty()) System.out.println("Nenhum hangar cadastrado.");
        for (Hangar h : hangares) h.imprimir();
    }

    public Hangar buscarHangar(int id) {
        return hangares.stream().filter(h -> h.getId() == id).findFirst()
                .orElseThrow(() -> new NaoEncontradoException("Hangar ID " + id + " não encontrado."));
    }

    public void listarAeromodelos() {
        if (aeromodelos.isEmpty()) System.out.println("Nenhum aeromodelo cadastrado.");
        // [POLIMORFISMO] O método imprimir adapta-se automaticamente à subclasse instanciada em tempo de execução
        for (Aeromodelo a : aeromodelos) a.imprimir();
    }

    public Aeromodelo buscarAeromodelo(int id) {
        return aeromodelos.stream().filter(a -> a.getId() == id).findFirst()
                .orElseThrow(() -> new NaoEncontradoException("Aeromodelo ID " + id + " não encontrado."));
    }

    public void alocarHangar(int idHangar, int idAviao) {
        Hangar h = buscarHangar(idHangar);
        Aeromodelo a = buscarAeromodelo(idAviao);
        
        if (!(a instanceof Aviao)) throw new ValidacaoException("Apenas aviões podem ser guardados em hangares. (RN02)");
        if (h.isOcupado()) throw new ValidacaoException("Hangar já ocupado.");
        
        // Motivo: Impedir que o mesmo avião ocupe fisicamente dois lugares ao mesmo tempo (RN02).
        for (Hangar hangarAux : hangares) {
            if (hangarAux.isOcupado() && hangarAux.getAviao().getId() == idAviao) {
                throw new ValidacaoException("Avião já alocado no Hangar ID " + hangarAux.getId());
            }
        }
        h.adicionarAviao((Aviao) a);
        salvarState();
    }

    public void desocuparHangar(int idHangar) {
        Hangar h = buscarHangar(idHangar);
        if (!h.isOcupado()) throw new ValidacaoException("O hangar já está livre.");
        h.removerAviao();
        salvarState();
    }

    // Getters para ArmazenamentoTxt
    public List<Aeromodelo> getAeromodelos() { return aeromodelos; }
    public List<Companhia> getCompanhias() { return companhias; }
    public List<Hangar> getHangares() { return hangares; }
    public List<Pista> getPistas() { return pistas; }
    public List<Voo> getVoos() { return voos; }
}