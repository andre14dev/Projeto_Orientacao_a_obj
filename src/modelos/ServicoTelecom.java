package modelos;

import excecoes.DadosInvalidosException;

public abstract class ServicoTelecom {
    protected String nomePlano;
    protected double valorBase;

    public ServicoTelecom(String nomePlano, double valorBase) throws DadosInvalidosException {
        if (valorBase <= 0) {
            throw new DadosInvalidosException("O valor base do plano deve ser maior que zero.");
        }
        this.nomePlano = nomePlano;
        this.valorBase = valorBase;
    }

    public abstract double calcularMensalidade();

    public String getNomePlano() {
        return nomePlano;
    }

    public double getValorBase() {
        return valorBase;
    }
}
