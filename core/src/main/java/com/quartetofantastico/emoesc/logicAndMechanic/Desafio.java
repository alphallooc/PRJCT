package com.quartetofantastico.emoesc.logicAndMechanic;

public class Desafio {
    private String pergunta;
    private String resposta;

    // Construtor completo
    public Desafio(String pergunta, String resposta){
        this.pergunta = pergunta;
        this.resposta = resposta;
    }

    // Construtor simples: pergunta + resposta vazia
    public Desafio(String pergunta) {
        this.pergunta = pergunta;
        this.resposta = ""; // evita null
    }

    public String getPergunta(){
        return pergunta;
    }

    public boolean acertou(String respostaJogador){
        if(resposta == null) return false;
        return respostaJogador.trim().equalsIgnoreCase(resposta);
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }
}
