package modelos;

import excecoes.DadosInvalidosException;
import interfaces.Bloqueavel;

// Plano móvel: herda nomePlano e valorBase de ServicoTelecom
// e implementa Bloqueavel para poder ter a linha suspensa.
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

    // Sobrescrita (override): no móvel a mensalidade é dinâmica.
    // Valor base + tarifa de cada GB consumido acima da franquia.
    // Math.max(0, ...) evita excedente negativo quando o consumo fica dentro da franquia.
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
