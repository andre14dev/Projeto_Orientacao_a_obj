
package principal;

import modelos.Cliente;
import modelos.Fatura;
import modelos.PlanoFibra;
import modelos.PlanoMovel;
import interfaces.Bloqueavel;
import excecoes.DadosInvalidosException;
import excecoes.ClienteInadimplenteException;
import persistencia.ClienteRepositorio;
import persistencia.FaturaRepositorio;
import threads.ProcessadorFaturas;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        ClienteRepositorio clienteRepositorio =
                new ClienteRepositorio("dados/clientes.csv");

        FaturaRepositorio faturaRepositorio =
                new FaturaRepositorio("dados/faturas.csv");

        int opcao = -1;

        while (opcao != 0) {

            System.out.println("\n===== SISTEMA DE GESTÃO ISP =====");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Listar clientes");
            System.out.println("3 - Gerar faturas");
            System.out.println("4 - Listar faturas");
            System.out.println("5 - Pagar fatura");
            System.out.println("6 - Verificar inadimplência");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = lerInt();

            try {

                switch (opcao) {

                    case 1:
                        cadastrarCliente(clienteRepositorio);
                        break;

                    case 2:
                        listarClientes(clienteRepositorio);
                        break;

                    case 3:
                        gerarFaturas(clienteRepositorio, faturaRepositorio);
                        break;

                    case 4:
                        listarFaturas(faturaRepositorio);
                        break;

                    case 5:
                        pagarFatura(faturaRepositorio);
                        break;

                    case 6:
                        verificarInadimplencia(clienteRepositorio, faturaRepositorio);
                        break;

                    case 0:
                        System.out.println("Encerrando o sistema...");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }

            } catch (DadosInvalidosException e) {
                System.out.println("Erro: " + e.getMessage());

            } catch (IOException e) {
                System.out.println("Erro ao acessar os arquivos: " + e.getMessage());
            }
        }

        scanner.close();
    }

    // Cadastro de clientes
    private static void cadastrarCliente(ClienteRepositorio repositorio)
            throws DadosInvalidosException, IOException {

        System.out.println("\n===== CADASTRAR CLIENTE =====");

        System.out.print("Nome: ");
        String nome = lerTexto();

        System.out.print("CPF: ");
        String cpf = Cliente.normalizarCpf(lerTexto());

        System.out.print("Endereço: ");
        String endereco = lerTexto();

        List<Cliente> clientes = repositorio.listarTodos();

        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                System.out.println("Já existe um cliente cadastrado com esse CPF!");
                return;
            }
        }

        System.out.println("\nTipo de plano:");
        System.out.println("1 - Fibra");
        System.out.println("2 - Móvel");
        System.out.print("Escolha: ");

        int tipo = lerInt();

        if (tipo == 1) {

            System.out.print("Nome do plano: ");
            String nomePlano = lerTexto();

            System.out.print("Valor mensal: ");
            double valorBase = lerDouble();

            System.out.print("Velocidade (Mbps): ");
            int velocidade = lerInt();

            PlanoFibra plano = new PlanoFibra(
                    nomePlano, valorBase, velocidade);

            Cliente cliente = new Cliente(nome, cpf, endereco, plano);

            repositorio.salvar(cliente);

            System.out.println("Cliente cadastrado com sucesso!");

        } else if (tipo == 2) {

            System.out.print("Nome do plano: ");
            String nomePlano = lerTexto();

            System.out.print("Valor base: ");
            double valorBase = lerDouble();

            System.out.print("Franquia (GB): ");
            double franquia = lerDouble();

            System.out.print("Consumo (GB): ");
            double consumo = lerDouble();

            System.out.print("Tarifa por GB excedente: ");
            double tarifa = lerDouble();

            PlanoMovel plano = new PlanoMovel(
                    nomePlano, valorBase, franquia, consumo, tarifa);

            Cliente cliente = new Cliente(nome, cpf, endereco, plano);

            repositorio.salvar(cliente);

            System.out.println("Cliente cadastrado com sucesso!");

        } else {
            System.out.println("Tipo de plano inválido!");
        }
    }

    // Listagem de clientes
    private static void listarClientes(ClienteRepositorio repositorio)
            throws IOException {

        System.out.println("\n===== CLIENTES CADASTRADOS =====");

        List<Cliente> clientes = repositorio.listarTodos();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        for (Cliente cliente : clientes) {

            System.out.println(cliente);

            System.out.println("Mensalidade: R$ "
                    + cliente.getPlano().calcularMensalidade());

            System.out.println("-------------------------");
        }
    }

    // Geração de faturas utilizando threads
    private static void gerarFaturas(
            ClienteRepositorio clienteRepositorio,
            FaturaRepositorio faturaRepositorio)
            throws IOException {

        System.out.println("\n===== GERAR FATURAS =====");

        System.out.print("Quantos dias faltam para o vencimento? ");
        int dias = lerInt();

        List<Cliente> clientes = clienteRepositorio.listarTodos();

        if (clientes.isEmpty()) {
            System.out.println("Não existem clientes cadastrados.");
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (Cliente cliente : clientes) {

            ProcessadorFaturas processador =
                    new ProcessadorFaturas(cliente, faturaRepositorio, dias);

            executor.execute(processador);
        }

        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);

            System.out.println("Geração de faturas concluída!");

        } catch (InterruptedException e) {
            System.out.println("Processamento interrompido.");
            Thread.currentThread().interrupt();
        }
    }

    // Listagem de faturas
    private static void listarFaturas(FaturaRepositorio repositorio)
            throws IOException {

        System.out.println("\n===== FATURAS =====");

        List<Fatura> faturas = repositorio.listarTodas();

        if (faturas.isEmpty()) {
            System.out.println("Nenhuma fatura encontrada.");
            return;
        }

        for (Fatura fatura : faturas) {
            System.out.println(fatura);
            System.out.println("-------------------------");
        }
    }

    // Pagamento de fatura
    private static void pagarFatura(FaturaRepositorio repositorio)
            throws IOException {

        System.out.println("\n===== PAGAR FATURA =====");

        System.out.print("Digite o CPF do cliente: ");
        String cpf = Cliente.normalizarCpf(lerTexto());

        List<Fatura> faturas = repositorio.listarTodas();

        for (Fatura fatura : faturas) {

            if (fatura.getCpfCliente().equals(cpf) && !fatura.isPaga()) {

                fatura.pagar();

                repositorio.salvarTodas(faturas);

                System.out.println("Fatura paga com sucesso!");
                return;
            }
        }

        System.out.println("Nenhuma fatura em aberto encontrada para esse CPF.");
    }

    // Verificação de inadimplência
    private static void verificarInadimplencia(
            ClienteRepositorio clienteRepositorio,
            FaturaRepositorio faturaRepositorio)
            throws IOException {

        System.out.println("\n===== VERIFICAR INADIMPLÊNCIA =====");

        List<Cliente> clientes = clienteRepositorio.listarTodos();
        List<Fatura> faturas = faturaRepositorio.listarTodas();

        for (Cliente cliente : clientes) {

            try {

                verificarAdimplencia(cliente, faturas);

                System.out.println(cliente.getNome() + " está em dia.");

            } catch (ClienteInadimplenteException e) {

                System.out.println("Atenção: " + e.getMessage());

                if (cliente.getPlano() instanceof Bloqueavel) {

                    Bloqueavel bloqueavel =
                            (Bloqueavel) cliente.getPlano();

                    bloqueavel.suspenderConexao();
                }
            }
        }
    }

    // Verifica se o cliente possui fatura atrasada
    private static void verificarAdimplencia(
            Cliente cliente, List<Fatura> faturas)
            throws ClienteInadimplenteException {

        for (Fatura fatura : faturas) {

            if (fatura.getCpfCliente().equals(cliente.getCpf())
                    && fatura.estaAtrasada()) {

                throw new ClienteInadimplenteException(
                        "Cliente " + cliente.getNome()
                                + " possui fatura atrasada!");
            }
        }
    }

    // Leitura de texto
    private static String lerTexto() {
        return scanner.nextLine();
    }

    // Leitura de números inteiros
    private static int lerInt() {

        while (true) {

            try {
                return Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {
                System.out.print("Digite um número inteiro válido: ");
            }
        }
    }

    // Leitura de números decimais
    private static double lerDouble() {

        while (true) {

            try {
                return Double.parseDouble(
                        scanner.nextLine().replace(",", "."));

            } catch (NumberFormatException e) {
                System.out.print("Digite um número válido: ");
            }
        }
    }
}
