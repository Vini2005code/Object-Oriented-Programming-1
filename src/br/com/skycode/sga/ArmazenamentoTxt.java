package br.com.skycode.sga;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ArmazenamentoTxt {
    private static final String PASTA = "dados/";

    public static void carregar(Aeroporto aeroporto) {
        try {
            Files.createDirectories(Paths.get(PASTA));
            
            // 1. Companhias
            File fileComp = new File(PASTA + "companhias.txt");
            if (fileComp.exists()) {
                for (String linha : Files.readAllLines(fileComp.toPath())) {
                    String[] d = linha.split(";");
                    aeroporto.getCompanhias().add(new Companhia(Integer.parseInt(d[0]), d[1], d[2]));
                }
            }
            
            // 2. Pistas
            File filePista = new File(PASTA + "pistas.txt");
            if (filePista.exists()) {
                for (String linha : Files.readAllLines(filePista.toPath())) {
                    String[] d = linha.split(";");
                    aeroporto.getPistas().add(new Pista(Integer.parseInt(d[0]), d[1]));
                }
            }
            
            // 3. Aeromodelos
            File fileAero = new File(PASTA + "aeromodelos.txt");
            if (fileAero.exists()) {
                for (String linha : Files.readAllLines(fileAero.toPath())) {
                    String[] d = linha.split(";");
                    String tipo = d[0];
                    int id = Integer.parseInt(d[1]);
                    if (tipo.equals("AVIAO")) {
                        Companhia c = aeroporto.buscarCompanhia(Integer.parseInt(d[6]));
                        aeroporto.getAeromodelos().add(new Aviao(id, d[2], d[3], d[4], Integer.parseInt(d[5]), c));
                    } else if (tipo.equals("HELICOPTERO")) {
                        aeroporto.getAeromodelos().add(new Helicoptero(id, d[2], d[3], Integer.parseInt(d[4]), d[5]));
                    } else if (tipo.equals("JATO")) {
                        aeroporto.getAeromodelos().add(new Jato(id, d[2], d[3], d[4], Integer.parseInt(d[5])));
                    }
                }
            }
            
            // 4. Hangares
            File fileHangar = new File(PASTA + "hangares.txt");
            if (fileHangar.exists()) {
                for (String linha : Files.readAllLines(fileHangar.toPath())) {
                    String[] d = linha.split(";");
                    Hangar h = new Hangar(Integer.parseInt(d[0]), d[1]);
                    int idAviao = Integer.parseInt(d[2]);
                    if (idAviao != 0) {
                        Aeromodelo a = aeroporto.buscarAeromodelo(idAviao);
                        if (a instanceof Aviao) h.adicionarAviao((Aviao) a);
                    }
                    aeroporto.getHangares().add(h);
                }
            }
            
            // 5. Voos
            File fileVoos = new File(PASTA + "voos.txt");
            if (fileVoos.exists()) {
                for (String linha : Files.readAllLines(fileVoos.toPath())) {
                    String[] d = linha.split(";");
                    Pista p = aeroporto.buscarPista(Integer.parseInt(d[9]));
                    Aeromodelo a = aeroporto.buscarAeromodelo(Integer.parseInt(d[10]));
                    aeroporto.getVoos().add(new Voo(Integer.parseInt(d[0]), d[1], d[2], d[3], d[4], d[5], d[6], 
                        LocalDate.parse(d[7]), LocalTime.parse(d[8]), p, a));
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados. Verifique a formatação dos arquivos .txt: " + e.getMessage());
        }
    }

    public static void salvar(Aeroporto aeroporto) {
        try {
            try (PrintWriter w = new PrintWriter(new FileWriter(PASTA + "companhias.txt"))) {
                for (Companhia c : aeroporto.getCompanhias()) w.println(c.getId() + ";" + c.getNome() + ";" + c.getCnpj());
            }
            try (PrintWriter w = new PrintWriter(new FileWriter(PASTA + "pistas.txt"))) {
                for (Pista p : aeroporto.getPistas()) w.println(p.getId() + ";" + p.getNumero());
            }
            try (PrintWriter w = new PrintWriter(new FileWriter(PASTA + "aeromodelos.txt"))) {
                for (Aeromodelo a : aeroporto.getAeromodelos()) {
                    if (a instanceof Aviao) {
                        Aviao av = (Aviao) a;
                        w.println("AVIAO;" + av.getId() + ";" + av.getMarca() + ";" + av.getModelo() + ";" + av.getPrefixo() + ";" + av.getCapacidade() + ";" + av.getCompanhia().getId());
                    } else if (a instanceof Helicoptero) {
                        Helicoptero h = (Helicoptero) a;
                        w.println("HELICOPTERO;" + h.getId() + ";" + h.getMarca() + ";" + h.getModelo() + ";" + h.getCapacidade() + ";" + h.getCor());
                    } else if (a instanceof Jato) {
                        Jato j = (Jato) a;
                        w.println("JATO;" + j.getId() + ";" + j.getMarca() + ";" + j.getModelo() + ";" + j.getCor() + ";" + j.getVelocidade());
                    }
                }
            }
            try (PrintWriter w = new PrintWriter(new FileWriter(PASTA + "hangares.txt"))) {
                for (Hangar h : aeroporto.getHangares()) {
                    w.println(h.getId() + ";" + h.getLocal() + ";" + (h.isOcupado() ? h.getAviao().getId() : 0));
                }
            }
            try (PrintWriter w = new PrintWriter(new FileWriter(PASTA + "voos.txt"))) {
                for (Voo v : aeroporto.getVoos()) {
                    w.println(v.getId() + ";" + v.getNumero() + ";" + v.getObservacao() + ";" + v.getPiloto() + ";" + v.getCopiloto() + ";" + v.getOrigem() + ";" + v.getDestino() + ";" + v.getData() + ";" + v.getHora() + ";" + v.getPista().getId() + ";" + v.getAeromodelo().getId());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }
    
    public static void exportarRelatorioVoos(List<Voo> voos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PASTA + "RegistroVoos.txt"))) {
            writer.println("--- REGISTRO OFICIAL DE VOOS PARA AUDITORIA ---");
            for (Voo v : voos) {
                writer.printf("Voo %s | Origem: %s | Destino: %s | Data: %s %s | Aeromodelo ID: %d | Pista: %s\n", 
                              v.getNumero(), v.getOrigem(), v.getDestino(), v.getData(), v.getHora(), v.getAeromodelo().getId(), v.getPista().getNumero());
            }
            System.out.println("Relatório exportado com sucesso para dados/RegistroVoos.txt");
        } catch (IOException e) {
            System.err.println("Erro ao exportar relatório: " + e.getMessage());
        }
    }
}