package modelos;

import excecoes.DadosInvalidosException;
import interfaces.Bloqueavel;

// Plano de fibra: herda nomePlano e valorBase de ServicoTelecom
// e implementa Bloqueavel para poder ter a conexão suspensa.
public class PlanoFibra extends ServicoTelecom implements Bloqueavel {

    private int velocidadeMbps;

    public PlanoFibra(String nomePlano, double valorBase, int velocidadeMbps)
            throws DadosInvalidosException {
        // super(...) precisa ser a primeira instrução: a classe pai
        // é construída antes da filha.
        super(nomePlano, valorBase);
        this.velocidadeMbps = velocidadeMbps;
    }

    // Sobrescrita (override): na fibra a mensalidade é fixa, igual ao valor base.
    @Override
    public double calcularMensalidade() {
        return valorBase;
    }

    @Override
    public void suspenderConexao() {
        System.out.println("Fibra \"" + nomePlano + "\" (" + velocidadeMbps
                + " Mbps) suspensa por inadimplência.");
    }

    public int getVelocidadeMbps() {
        return velocidadeMbps;
    }

    @Override
    public String toString() {
        return "Fibra " + nomePlano + " (" + velocidadeMbps + " Mbps)";
    }
}
