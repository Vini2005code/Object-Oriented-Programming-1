package br.com.skycode.sga;

import java.util.Scanner;

//Função: É o ponto de entrada do sistema (main). Exibe os menus em loop interativo (CRUDs), 
// lê os comandos digitados pelo utilizador no terminal e direciona as chamadas para a classe controladora.

public class Principal {
    private static Aeroporto aeroporto = new Aeroporto();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        aeroporto.carregarDadosIniciais();
        
        int opcao = 0;
        // Motivo: O loop principal funciona como o "Event Listener" da nossa interface de terminal.
        while (opcao != 6) {
            System.out.println("\n=== SGA JATINHO FELIZ ===");
            System.out.println("1. Gerenciar Companhias");
            System.out.println("2. Gerenciar Pistas");
            System.out.println("3. Listar Aeromodelos");
            System.out.println("4. Operações de Hangar (Alocar/Desocupar)");
            System.out.println("5. Relatório de Voos (Auditoria)");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                processarOpcaoMenuPrincipal(opcao);
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite apenas números.");
            } catch (ExcecaoSGA e) {
                // [HERANÇA] Tratamento polimórfico de exceções da nossa hierarquia customizada
                System.out.println("\n[ERRO DE NEGÓCIO] " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n[ERRO INESPERADO] " + e.getMessage());
            }
        }
        System.out.println("Sistema encerrado com segurança.");
    }

    private static void processarOpcaoMenuPrincipal(int opcao) {
        switch (opcao) {
            case 1: menuCompanhias(); break;
            case 2: menuPistas(); break;
            case 3: 
                System.out.println("\n--- AEROMODELOS ---");
                aeroporto.listarAeromodelos(); 
                break;
            case 4: menuHangares(); break;
            case 5:
                System.out.println("\n--- VOOS REGISTRADOS ---");
                ArmazenamentoTxt.exportarRelatorioVoos(aeroporto.getVoos());
                break;
            case 6: break;
            default: System.out.println("Opção inválida.");
        }
    }

    // SUBMENUS (Requisito RF10)
    
    private static void menuCompanhias() {
        int op = 0;
        while (op != 6) {
            System.out.println("\n--- MENU COMPANHIAS ---");
            System.out.println("1. Cadastrar | 2. Listar | 3. Buscar | 4. Alterar | 5. Excluir | 6. Voltar");
            System.out.print("Escolha: ");
            try {
                op = Integer.parseInt(scanner.nextLine());
                if (op == 1) {
                    System.out.print("Nome: "); String nome = scanner.nextLine();
                    System.out.print("CNPJ (14 dígitos): "); String cnpj = scanner.nextLine();
                    aeroporto.cadastrarCompanhia(nome, cnpj);
                    System.out.println("=> Companhia cadastrada!");
                } else if (op == 2) {
                    aeroporto.listarCompanhias();
                } else if (op == 3) {
                    System.out.print("ID para busca: "); int id = Integer.parseInt(scanner.nextLine());
                    aeroporto.buscarCompanhia(id).imprimir();
                } else if (op == 4) {
                    System.out.print("ID para alterar: "); int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Novo Nome: "); String nome = scanner.nextLine();
                    System.out.print("Novo CNPJ: "); String cnpj = scanner.nextLine();
                    aeroporto.editarCompanhia(id, nome, cnpj);
                    System.out.println("=> Companhia atualizada!");
                } else if (op == 5) {
                    System.out.print("ID para exclusão: "); int id = Integer.parseInt(scanner.nextLine());
                    aeroporto.removerCompanhia(id);
                    System.out.println("=> Companhia excluída com sucesso.");
                }
            } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); }
        }
    }

    private static void menuPistas() {
        int op = 0;
        while (op != 6) {
            System.out.println("\n--- MENU PISTAS ---");
            System.out.println("1. Cadastrar | 2. Listar | 3. Buscar | 4. Alterar | 5. Excluir | 6. Voltar");
            System.out.print("Escolha: ");
            try {
                op = Integer.parseInt(scanner.nextLine());
                if (op == 1) {
                    System.out.print("Número da Pista (ex: A10): "); String num = scanner.nextLine();
                    aeroporto.cadastrarPista(num);
                    System.out.println("=> Pista cadastrada!");
                } else if (op == 2) {
                    aeroporto.listarPistas();
                } else if (op == 3) {
                    System.out.print("ID para busca: "); int id = Integer.parseInt(scanner.nextLine());
                    aeroporto.buscarPista(id).imprimir();
                } else if (op == 4) {
                    System.out.print("ID para alterar: "); int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Novo Número: "); String num = scanner.nextLine();
                    aeroporto.editarPista(id, num);
                    System.out.println("=> Pista atualizada!");
                } else if (op == 5) {
                    System.out.print("ID para exclusão: "); int id = Integer.parseInt(scanner.nextLine());
                    aeroporto.removerPista(id);
                    System.out.println("=> Pista excluída com sucesso.");
                }
            } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); }
        }
    }

    private static void menuHangares() {
        System.out.println("\n--- ESTADO DOS HANGARES ---");
        aeroporto.listarHangares();
        System.out.println("\n[1] Alocar Avião | [2] Desocupar Hangar | [3] Voltar");
        System.out.print("Opção: ");
        try {
            int op = Integer.parseInt(scanner.nextLine());
            if (op == 1) {
                System.out.print("ID do Hangar Livre: "); int idHangar = Integer.parseInt(scanner.nextLine());
                System.out.print("ID do Avião: "); int idAviao = Integer.parseInt(scanner.nextLine());
                aeroporto.alocarHangar(idHangar, idAviao);
                System.out.println("=> Avião alocado com sucesso!");
            } else if (op == 2) {
                System.out.print("ID do Hangar a desocupar: "); int idHangar = Integer.parseInt(scanner.nextLine());
                aeroporto.desocuparHangar(idHangar);
                System.out.println("=> Hangar liberado com sucesso!");
            }
        } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); }
    }
}