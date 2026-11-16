package modelos;

import excecoes.DadosInvalidosException;
import interfaces.Bloqueavel;

public class PlanoMovel extends ServicoTelecom implements Bloqueavel {
    private double franquiaGb;
    private double consumoGb;
    private double tarifaPorGbExcedente;

    public PlanoMovel(String nomePlano, double valorBase, double franquiaGb,
                      double consumoGb, double tarifaPorGbExcedente) throws DadosInvalidosException {
        super(nomePlano, valorBase);
        this.franquiaGb = franquiaGb;
        this.consumoGb = consumoGb;
        this.tarifaPorGbExcedente = tarifaPorGbExcedente;
    }

    @Override
    public double calcularMensalidade() {
        double excedente = Math.max(0, consumoGb - franquiaGb);
        return valorBase + excedente * tarifaPorGbExcedente;
    }

    @Override
    public void suspenderConexao() {
        System.out.println("Linha móvel \"" + nomePlano + "\" suspensa por inadimplência.");
    }

    public double getFranquiaGb() {
        return franquiaGb;
    }

    public double getConsumoGb() {
        return consumoGb;
    }

    public double getTarifaPorGbExcedente() {
        return tarifaPorGbExcedente;
    }

    @Override
    public String toString() {
        return "Móvel " + nomePlano + " (franquia " + franquiaGb + " GB, consumo "
                + consumoGb + " GB)";
    }
}
