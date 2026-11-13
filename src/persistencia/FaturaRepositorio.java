package persistencia;

import modelos.Fatura;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FaturaRepositorio {
    private Path caminhoArquivo;

    public FaturaRepositorio(String caminho) {
        this.caminhoArquivo = Paths.get(caminho);
    }

    public synchronized void salvar(Fatura fatura) throws IOException {
        if (caminhoArquivo.getParent() != null) {
            Files.createDirectories(caminhoArquivo.getParent());
        }

        String linha = formatarLinha(fatura);

        Files.write(
                caminhoArquivo,
                Collections.singletonList(linha),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }

    public synchronized List<Fatura> listarTodas() throws IOException {
        List<Fatura> faturas = new ArrayList<>();

        if (!Files.exists(caminhoArquivo)) {
            return faturas;
        }

        List<String> linhas = Files.readAllLines(caminhoArquivo, StandardCharsets.UTF_8);

        for (int i = 0; i < linhas.size(); i++) {
            String linha = linhas.get(i).trim();
            if (linha.isEmpty()) {
                continue;
            }

            try {
                String[] partes = linha.split(";");
                String cpf = partes[0];
                double valor = Double.parseDouble(partes[1]);
                LocalDate vencimento = LocalDate.parse(partes[2]);
                boolean paga = Boolean.parseBoolean(partes[3]);

                faturas.add(new Fatura(cpf, valor, vencimento, paga));
            } catch (NumberFormatException | DateTimeParseException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Aviso: Linha corrompida ignorada em faturas.csv (linha " + (i + 1) + "): " + e.getMessage());
            }
        }

        return faturas;
    }

    public synchronized void salvarTodas(List<Fatura> faturas) throws IOException {
        if (caminhoArquivo.getParent() != null) {
            Files.createDirectories(caminhoArquivo.getParent());
        }

        List<String> linhas = new ArrayList<>();
        for (Fatura fatura : faturas) {
            linhas.add(formatarLinha(fatura));
        }

        Files.write(
                caminhoArquivo,
                linhas,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );
    }

    private String formatarLinha(Fatura fatura) {
        return fatura.getCpfCliente() + ";"
                + fatura.getValor() + ";"
                + fatura.getVencimento() + ";"
                + fatura.isPaga();
    }
}
