package modelos;

import excecoes.DadosInvalidosException;

// Classe abstrata que representa qualquer serviço de telecom do provedor.
// Não existe "serviço genérico" no sistema, por isso ela não pode ser instanciada:
// só PlanoFibra e PlanoMovel (as filhas) viram objetos de verdade.
public abstract class ServicoTelecom {

    protected String nomePlano;
    protected double valorBase;

    // Valida o valor base na origem: um plano com valor <= 0 nem chega a ser criado,
    // o que evita fatura inválida mais adiante.
    public ServicoTelecom(String nomePlano, double valorBase) throws DadosInvalidosException {
        if (valorBase <= 0) {
            throw new DadosInvalidosException("O valor base do plano deve ser maior que zero.");
        }
        this.nomePlano = nomePlano;
        this.valorBase = valorBase;
    }

    // Método abstrato: cada plano calcula a mensalidade do seu jeito.
    // É isso que permite o polimorfismo no Main e no ProcessadorFaturas, que chamam
    // calcularMensalidade() sem precisar saber se o plano é fibra ou móvel.
    public abstract double calcularMensalidade();

    public String getNomePlano() {
        return nomePlano;
    }

    public double getValorBase() {
        return valorBase;
    }
}
