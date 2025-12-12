package br.com.alura.reserva.model.Sala;

public record DadosSala(
    Long id,
    String nome,
    String descricao,
    int capacidade,
    boolean ativa
) {
    public DadosSala(Sala sala) {
        this(sala.getId(), sala.getNome(), sala.getDescricao(), sala.getCapacidade(),sala.isAtiva());
    }
}
