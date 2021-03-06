package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos;

import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.Solucao;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //List<String> infoTransacoes, List<String> infoAdiantamentos
        // Transacoes
        //"valor,metodoPagamento,numeroCartao,nomeCartao,validade,cvv,idTransacao"

        List<String> infoTransacoes = new ArrayList<String>();
        infoTransacoes.add("100,DEBITO,5498763884495343,Gilson Dourado,06/05/2022,849,1");
        infoTransacoes.add("1500,CREDITO,4929310815242493,Carla Baresi,06/02/2022,988,2");

        List<String> infoAdiantamentos = new ArrayList<String>();
        infoAdiantamentos.add("1,1000");
        infoAdiantamentos.add("2,500");

        List<String[]> lista = Solucao.executa(infoTransacoes, infoAdiantamentos);

        for (String[] elemento : lista) {
            for(String item : elemento)
                System.out.println(item);
        }

        System.out.println("TESTE");
    }
}
