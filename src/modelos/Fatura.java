package modelos;

import java.time.LocalDate;

public class Fatura {
    private String cpfCliente;
    private double valor;
    private LocalDate vencimento;
    private boolean paga;

    public Fatura(String cpfCliente, double valor, LocalDate vencimento, boolean paga) {
        this.cpfCliente = cpfCliente;
        this.valor = valor;
        this.vencimento = vencimento;
        this.paga = paga;
    }

    public boolean estaAtrasada() {
        if (paga == false && vencimento.isBefore(LocalDate.now())) {
            return true;
        } else {
            return false;
        }
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

    public boolean getPaga() {
        return paga;
    }

    @Override
    public String toString() {
        String status = "";

        if (paga == true) {
            status = "PAGA";
        } else if (estaAtrasada() == true) {
            status = "ATRASADA";
        } else {
            status = "ABERTA";
        }
        return "Fatura [CPF: " + cpfCliente + " | Valor: R$ " + valor + " | Venc.: " + vencimento + " | Status: " + status + "]";
    }
}
