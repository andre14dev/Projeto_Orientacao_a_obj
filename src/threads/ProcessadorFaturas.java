package threads;

import modelos.Cliente;
import modelos.Fatura;
import persistencia.FaturaRepositorio;

import java.io.IOException;
import java.time.LocalDate;

public class ProcessadorFaturas implements Runnable {
    private Cliente cliente;
    private FaturaRepositorio faturaRepositorio;
    private int diasParaVencimento;

    public ProcessadorFaturas(Cliente cliente, FaturaRepositorio faturaRepositorio, int diasParaVencimento) {
        this.cliente = cliente;
        this.faturaRepositorio = faturaRepositorio;
        this.diasParaVencimento = diasParaVencimento;
    }

    @Override
    public void run() {
        double valor = cliente.getPlano().calcularMensalidade();
        LocalDate vencimento = LocalDate.now().plusDays(diasParaVencimento);
        Fatura fatura = new Fatura(cliente.getCpf(), valor, vencimento, false);

        System.out.printf("[%s] Gerando fatura para %s (CPF: %s) - Valor: R$ %.2f%n",
                Thread.currentThread().getName(),
                cliente.getNome(),
                cliente.getCpf(),
                valor);

        try {
            faturaRepositorio.salvar(fatura);
        } catch (IOException e) {
            System.out.println("[" + Thread.currentThread().getName() + "] Erro ao salvar fatura de "
                    + cliente.getNome() + ": " + e.getMessage());
        }
    }
}
