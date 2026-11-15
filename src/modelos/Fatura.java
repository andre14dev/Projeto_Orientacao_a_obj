package modelos;

import java.time.LocalDate;

public class Fatura {
    private String cpfCliente;
    private double valor;
    private LocalDate vencimento;
    private boolean paga;

    public Fatura(String cpfCliente, double valor, LocalDate vencimento, boolean paga) {

        if (cpfCliente == null || cpfCliente.isBlank()) {
            throw new IllegalArgumentException("O CPF do cliente não pode ser vazio.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor da fatura deve ser maior que zero.");
        }
        if (vencimento == null) {
            throw new IllegalArgumentException("A data de vencimento não pode ser nula.");
        }
        
        this.cpfCliente = cpfCliente;
        this.valor = valor;
        this.vencimento = vencimento;
        this.paga = paga;
    }

    public boolean estaAtrasada() {
        return !paga && vencimento.isBefore(LocalDate.now());
    }

    public void pagar() {
        this.paga = true;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public boolean isPaga() {
        return paga;
    }

    @Override
    public String toString() {
        String status;

        if (paga) {
            status = "PAGA";
        } else if (estaAtrasada()) {
            status = "ATRASADA";
        } else {
            status = "ABERTA";
        }
        
        return String.format("Fatura [CPF: %s | Valor: R$ %.2f | Venc.: %s | Status: %s]", 
                             cpfCliente, valor, vencimento, status);
    }
}
