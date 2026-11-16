package modelos;

import excecoes.DadosInvalidosException;
import interfaces.Bloqueavel;

public class PlanoFibra extends ServicoTelecom implements Bloqueavel {
    private int velocidadeMbps;

    public PlanoFibra(String nomePlano, double valorBase, int velocidadeMbps)
            throws DadosInvalidosException {
        super(nomePlano, valorBase);
        this.velocidadeMbps = velocidadeMbps;
    }

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
