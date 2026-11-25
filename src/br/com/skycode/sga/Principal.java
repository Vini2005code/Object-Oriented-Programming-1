package br.com.skycode.sga;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Principal {
    private static Aeroporto aeroporto = new Aeroporto();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        aeroporto.carregarDadosIniciais();

        int opcao = 0;
        while (opcao != 7) {
            System.out.println("\n=== SGA - SISTEMA DE GERENCIAMENTO AEROPORTUÁRIO ===");
            System.out.println("1. Gerenciar Companhias");
            System.out.println("2. Gerenciar Pistas");
            System.out.println("3. Listar Aeromodelos");
            System.out.println("4. Operações de Hangar");
            System.out.println("5. Voos e Relatório de Auditoria");
            System.out.println("6. Exportar Relatório de Voos (.txt)");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                String entrada = scanner.nextLine();
                opcao = Integer.parseInt(entrada);
                processarOpcaoMenuPrincipal(opcao);
            } catch (NumberFormatException e) {
                System.out.println("Erro de entrada: digite um número válido.");
            } catch (ExcecaoSGA e) {
                // [HERANÇA] Captura exceções específicas do domínio do SGA sem encerrar o sistema.
                System.out.println("Erro de regra/operação: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }

        System.out.println("Sistema encerrado com segurança.");
    }

    private static void processarOpcaoMenuPrincipal(int opcao) {
        switch (opcao) {
            case 1:
                menuCompanhias();
                break;
            case 2:
                menuPistas();
                break;
            case 3:
                System.out.println("\n--- AEROMODELOS ---");
                aeroporto.listarAeromodelos();
                break;
            case 4:
                menuHangares();
                break;
            case 5:
                menuVoos();
                break;
            case 6:
                ArmazenamentoTxt.exportarRelatorioVoos(aeroporto.getVoos());
                System.out.println("Relatório de voos exportado com sucesso.");
                break;
            case 7:
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private static void menuCompanhias() {
        int op = 0;
        while (op != 6) {
            System.out.println("\n--- MENU COMPANHIAS ---");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Alterar");
            System.out.println("5. Excluir");
            System.out.println("6. Voltar");
            System.out.print("Escolha: ");

            try {
                String entradaOp = scanner.nextLine();
                op = Integer.parseInt(entradaOp);

                if (op == 1) {
                    System.out.print("Nome da companhia: ");
                    String nome = scanner.nextLine();

                    System.out.print("CNPJ (14 dígitos): ");
                    String cnpj = scanner.nextLine();

                    aeroporto.cadastrarCompanhia(nome, cnpj);
                    System.out.println("Companhia cadastrada com sucesso.");
                } else if (op == 2) {
                    aeroporto.listarCompanhias();
                } else if (op == 3) {
                    System.out.print("ID da companhia: ");
                    String entradaId = scanner.nextLine();
                    int id = Integer.parseInt(entradaId);

                    Companhia encontrada = aeroporto.buscarCompanhia(id);
                    encontrada.imprimir();
                } else if (op == 4) {
                    System.out.print("ID da companhia para alterar: ");
                    String entradaId = scanner.nextLine();
                    int id = Integer.parseInt(entradaId);

                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine();

                    System.out.print("Novo CNPJ (14 dígitos): ");
                    String novoCnpj = scanner.nextLine();

                    aeroporto.editarCompanhia(id, novoNome, novoCnpj);
                    System.out.println("Companhia atualizada com sucesso.");
                } else if (op == 5) {
                    System.out.print("ID da companhia para excluir: ");
                    String entradaId = scanner.nextLine();
                    int id = Integer.parseInt(entradaId);

                    aeroporto.removerCompanhia(id);
                    System.out.println("Companhia excluída com sucesso.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: informe um número inteiro válido.");
            } catch (ExcecaoSGA e) {
                System.out.println("Falha na operação: " + e.getMessage());
            }
        }
    }

    private static void menuPistas() {
        int op = 0;
        while (op != 6) {
            System.out.println("\n--- MENU PISTAS ---");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Alterar");
            System.out.println("5. Excluir");
            System.out.println("6. Voltar");
            System.out.print("Escolha: ");

            try {
                String entradaOp = scanner.nextLine();
                op = Integer.parseInt(entradaOp);

                if (op == 1) {
                    System.out.print("Número/código da pista: ");
                    String numero = scanner.nextLine();

                    aeroporto.cadastrarPista(numero);
                    System.out.println("Pista cadastrada com sucesso.");
                } else if (op == 2) {
                    aeroporto.listarPistas();
                } else if (op == 3) {
                    System.out.print("ID da pista: ");
                    String entradaId = scanner.nextLine();
                    int id = Integer.parseInt(entradaId);

                    Pista encontrada = aeroporto.buscarPista(id);
                    encontrada.imprimir();
                } else if (op == 4) {
                    System.out.print("ID da pista para alterar: ");
                    String entradaId = scanner.nextLine();
                    int id = Integer.parseInt(entradaId);

                    System.out.print("Novo número/código da pista: ");
                    String novoNumero = scanner.nextLine();

                    aeroporto.editarPista(id, novoNumero);
                    System.out.println("Pista atualizada com sucesso.");
                } else if (op == 5) {
                    System.out.print("ID da pista para excluir: ");
                    String entradaId = scanner.nextLine();
                    int id = Integer.parseInt(entradaId);

                    aeroporto.removerPista(id);
                    System.out.println("Pista excluída com sucesso.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: informe um número inteiro válido.");
            } catch (ExcecaoSGA e) {
                System.out.println("Falha na operação: " + e.getMessage());
            }
        }
    }

    private static void menuHangares() {
        int op = 0;
        while (op != 4) {
            System.out.println("\n--- MENU HANGARES ---");
            System.out.println("1. Listar hangares");
            System.out.println("2. Alocar avião em hangar");
            System.out.println("3. Desocupar hangar");
            System.out.println("4. Voltar");
            System.out.print("Escolha: ");

            try {
                String entradaOp = scanner.nextLine();
                op = Integer.parseInt(entradaOp);

                if (op == 1) {
                    aeroporto.listarHangares();
                } else if (op == 2) {
                    System.out.print("ID do hangar: ");
                    String entradaHangar = scanner.nextLine();
                    int idHangar = Integer.parseInt(entradaHangar);

                    System.out.print("ID do avião: ");
                    String entradaAviao = scanner.nextLine();
                    int idAviao = Integer.parseInt(entradaAviao);

                    aeroporto.alocarHangar(idHangar, idAviao);
                    System.out.println("Avião alocado no hangar com sucesso.");
                } else if (op == 3) {
                    System.out.print("ID do hangar para desocupar: ");
                    String entradaHangar = scanner.nextLine();
                    int idHangar = Integer.parseInt(entradaHangar);

                    aeroporto.desocuparHangar(idHangar);
                    System.out.println("Hangar desocupado com sucesso.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: informe um número inteiro válido.");
            } catch (ExcecaoSGA e) {
                System.out.println("Falha na operação: " + e.getMessage());
            }
        }
    }

    private static void menuVoos() {
        int op = 0;
        while (op != 4) {
            System.out.println("\n--- MENU VOOS ---");
            System.out.println("1. Listar voos");
            System.out.println("2. Registrar voo");
            System.out.println("3. Exportar relatório de auditoria (.txt)");
            System.out.println("4. Voltar");
            System.out.print("Escolha: ");

            try {
                String entradaOp = scanner.nextLine();
                op = Integer.parseInt(entradaOp);

                if (op == 1) {
                    aeroporto.listarVoos();
                } else if (op == 2) {
                    System.out.print("Número do voo: ");
                    String numero = scanner.nextLine();

                    System.out.print("Observação: ");
                    String observacao = scanner.nextLine();

                    System.out.print("Piloto: ");
                    String piloto = scanner.nextLine();

                    System.out.print("Copiloto: ");
                    String copiloto = scanner.nextLine();

                    System.out.print("Origem: ");
                    String origem = scanner.nextLine();

                    System.out.print("Destino: ");
                    String destino = scanner.nextLine();

                    System.out.print("Data (AAAA-MM-DD): ");
                    String entradaData = scanner.nextLine();
                    LocalDate data = LocalDate.parse(entradaData);

                    System.out.print("Hora (HH:MM): ");
                    String entradaHora = scanner.nextLine();
                    LocalTime hora = LocalTime.parse(entradaHora);

                    System.out.print("ID da pista: ");
                    String entradaPista = scanner.nextLine();
                    int idPista = Integer.parseInt(entradaPista);

                    System.out.print("ID do aeromodelo: ");
                    String entradaAero = scanner.nextLine();
                    int idAeromodelo = Integer.parseInt(entradaAero);

                    aeroporto.registrarVoo(
                            numero,
                            observacao,
                            piloto,
                            copiloto,
                            origem,
                            destino,
                            data,
                            hora,
                            idPista,
                            idAeromodelo
                    );
                    System.out.println("Voo registrado com sucesso.");
                } else if (op == 3) {
                    ArmazenamentoTxt.exportarRelatorioVoos(aeroporto.getVoos());
                    System.out.println("Relatório exportado com sucesso.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: informe um número inteiro válido.");
            } catch (ExcecaoSGA e) {
                System.out.println("Falha na operação: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro de formato ou operação: " + e.getMessage());
            }
        }
    }
}