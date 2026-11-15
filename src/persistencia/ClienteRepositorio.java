package persistencia;

import excecoes.DadosInvalidosException;
import modelos.Cliente;
import modelos.PlanoFibra;
import modelos.PlanoMovel;
import modelos.ServicoTelecom;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClienteRepositorio {
    private Path caminhoArquivo;

    public ClienteRepositorio(String caminho) {
        this.caminhoArquivo = Paths.get(caminho);
    }

    public synchronized void salvar(Cliente cliente) throws IOException {
        if (caminhoArquivo.getParent() != null) {
            Files.createDirectories(caminhoArquivo.getParent());
        }

        ServicoTelecom plano = cliente.getPlano();
        String linha;

        if (plano instanceof PlanoFibra) {
            PlanoFibra fibra = (PlanoFibra) plano;
            linha = "FIBRA;" + cliente.getNome() + ";" + cliente.getCpf() + ";" + cliente.getEndereco() + ";"
                    + fibra.getNomePlano() + ";" + fibra.getValorBase() + ";" + fibra.getVelocidadeMbps();
        } else if (plano instanceof PlanoMovel) {
            PlanoMovel movel = (PlanoMovel) plano;
            linha = "MOVEL;" + cliente.getNome() + ";" + cliente.getCpf() + ";" + cliente.getEndereco() + ";"
                    + movel.getNomePlano() + ";" + movel.getValorBase() + ";" + movel.getFranquiaGb() + ";"
                    + movel.getConsumoGb() + ";" + movel.getTarifaPorGbExcedente();
        } else {
            throw new IllegalArgumentException("Tipo de plano desconhecido para persistência.");
        }

        Files.write(
                caminhoArquivo,
                Collections.singletonList(linha),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }

    public synchronized List<Cliente> listarTodos() throws IOException {
        List<Cliente> clientes = new ArrayList<>();

        if (!Files.exists(caminhoArquivo)) {
            return clientes;
        }

        List<String> linhas = Files.readAllLines(caminhoArquivo, StandardCharsets.UTF_8);

        for (int i = 0; i < linhas.size(); i++) {
            String linha = linhas.get(i).trim();
            if (linha.isEmpty()) {
                continue;
            }

            try {
                String[] partes = linha.split(";");
                String tipo = partes[0];
                String nome = partes[1];
                String cpf = partes[2];
                String endereco = partes[3];
                String nomePlano = partes[4];
                double valorBase = Double.parseDouble(partes[5]);

                ServicoTelecom plano;
                if ("FIBRA".equalsIgnoreCase(tipo)) {
                    int velocidade = Integer.parseInt(partes[6]);
                    plano = new PlanoFibra(nomePlano, valorBase, velocidade);
                } else if ("MOVEL".equalsIgnoreCase(tipo)) {
                    double franquia = Double.parseDouble(partes[6]);
                    double consumo = Double.parseDouble(partes[7]);
                    double tarifa = Double.parseDouble(partes[8]);
                    plano = new PlanoMovel(nomePlano, valorBase, franquia, consumo, tarifa);
                } else {
                    System.out.println("Aviso: Tipo de plano inválido na linha " + (i + 1) + " de clientes.csv. Ignorando.");
                    continue;
                }

                clientes.add(new Cliente(nome, cpf, endereco, plano));
            } catch (DadosInvalidosException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Aviso: Linha corrompida ignorada em clientes.csv (linha " + (i + 1) + "): " + e.getMessage());
            }
        }

        return clientes;
    }
}
