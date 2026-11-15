package modelos;

import excecoes.DadosInvalidosException;

public class Cliente {
    private String nome;
    private String cpf;
    private String endereco;
    private ServicoTelecom plano; 

    public Cliente(String nome, String cpf, String endereco, ServicoTelecom plano) throws DadosInvalidosException {
        
        if (nome == "" || endereco == "") {
            throw new DadosInvalidosException("Nome e endereço não podem ser vazios.");
        }

        if (plano == null) {
            throw new DadosInvalidosException("O plano não pode ser nulo.");
        }

        String cpfLimpo = normalizarCpf(cpf);
        
        if (cpfLimpo.length() != 11) {
            throw new DadosInvalidosException("O CPF precisa ter 11 dígitos.");
        }

        this.nome = nome;
        this.cpf = cpfLimpo;
        this.endereco = endereco;
        this.plano = plano;
    }

    public static String normalizarCpf(String cpf) {
        String tiraPonto = cpf.replace(".", "");
        String tiraTraco = tiraPonto.replace("-", "");
        return tiraTraco;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public ServicoTelecom getPlano() {
        return plano;
    }
    
    @Override
    public String toString() {
        return "Cliente: " + nome + ", CPF: " + cpf + ", Endereço: " + endereco + ", Plano: " + plano.getNomePlano();
    }
}
