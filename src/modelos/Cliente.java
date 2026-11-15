package modelos;

import excecoes.DadosInvalidosException;

public class Cliente {
    private String nome;
    private String cpf;
    private String endereco;
    private ServicoTelecom plano; 

    public Cliente(String nome, String cpf, String endereco, ServicoTelecom plano) throws DadosInvalidosException {
        
        if (nome == null || nome.isBlank() || endereco == null || endereco.isBlank()) {
            throw new DadosInvalidosException("Nome e endereço não podem ser nulos ou vazios.");
        }

        if (plano == null) {
            throw new DadosInvalidosException("O plano não pode ser nulo.");
        }

        if (cpf == null) {
            throw new DadosInvalidosException("O CPF não pode ser nulo.");
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
        return cpf.replaceAll("\\D", "");
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
