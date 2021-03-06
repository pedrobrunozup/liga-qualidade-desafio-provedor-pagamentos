package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Solucao {

	/**
	 * 
	 * @param infoTransacoes dados das transações. A String está formatada da seguinte maneira:		
		<b>"valor,metodoPagamento,numeroCartao,nomeCartao,validade,cvv,idTransacao"</b>
		<ol>
		 <li> Valor é um decimal</li>
	 	 <li> O método de pagamento é 'DEBITO' ou 'CREDITO' </li>
	 	 <li> Validade é uma data no formato dd/MM/yyyy. </li>
	 	</ol>
	 	
	 * @param infoAdiantamentos informacao da transacao que pode ser recebida adiantada. A String está formatada da seguinte maneira:		
		<b>"idTransacao,taxa"</b>
		<ol>
	 	 <li> Taxa é um decimal </li>	 	 
	 	</ol> 
	 * 
	 * @return Uma lista de array de string com as informações na seguinte ordem:
	 * [status,valorOriginal,valorASerRecebidoDeFato,dataEsperadoRecebimento].
	 * <ol>
	 *  <li>O status pode ser 'pago' ou 'aguardando_pagamento'</li>
	 *  <li>O valor original e o a ser recebido de fato devem vir no formato decimal. Ex: 50.45</li>
	 *  <li>dataEsperadoRecebimento deve ser formatada como dd/MM/yyyy. Confira a classe {@link DateTimeFormatter}</li> 
	 * </ol> 
	 * 
	 * É esperado que o retorno respeite a ordem de recebimento
	 */
	public static List<String[]> executa(List<String> infoTransacoes, List<String> infoAdiantamentos) {

		LocalDateTime hoje = LocalDateTime.now();

		String status = null;
		String valorOriginal = null;
		String valorASerRecebido = null;
		String dataRecebimento = null;
		
		String[] infoTransacao;

		double valor;

		Date dMais30;

		List<String[]> listaInfoTransacoes = new ArrayList<String[]>();

		for (String transacao : infoTransacoes) {
			String[] itemsTransacao = transacao.split(",");
			infoTransacao = new String[] {"A","B","C","D","E"};
			dMais30 = Date.from(hoje.atZone(ZoneId.systemDefault()).toInstant());
			if(itemsTransacao[1].equalsIgnoreCase("CREDITO")) {
				status = "aguardando_liberacao_fundos";
				dMais30.setDate(dMais30.getDate() + 30);
				String formato = "dd/MM/yyyy";
				SimpleDateFormat dataFormatada = new SimpleDateFormat(formato);
				dataRecebimento = dataFormatada.format(dMais30);
				valorOriginal = itemsTransacao[0];
				valor = Double.parseDouble(valorOriginal) - 0.05 * Double.parseDouble(valorOriginal);
				valorASerRecebido = String.valueOf(valor);
				infoTransacao[0] = status;
				infoTransacao[1] = valorOriginal;
				infoTransacao[2] = valorASerRecebido;
				infoTransacao[3] = dataRecebimento;
				infoTransacao[4] = itemsTransacao[6];
			} else if(itemsTransacao[1].equalsIgnoreCase("DEBITO")) {
				status = "pago";
				dMais30.setDate(dMais30.getDate() + 0);
				String formato = "dd/MM/yyyy";
				SimpleDateFormat dataFormatada = new SimpleDateFormat(formato);
				dataRecebimento = dataFormatada.format(dMais30);
				valorOriginal = itemsTransacao[0];
				valor = Double.parseDouble(valorOriginal) - 0.03*Double.parseDouble(valorOriginal);
				valorASerRecebido = String.valueOf(valor);
				infoTransacao[0] = status;
				infoTransacao[1] = valorOriginal;
				infoTransacao[2] = valorASerRecebido;
				infoTransacao[3] = dataRecebimento;
				infoTransacao[4] = itemsTransacao[6];
			}

			listaInfoTransacoes.add(infoTransacao);
		}

		for (String adiantamento: infoAdiantamentos) {
			String[] itemsAdiantamento = adiantamento.split(",");
			dMais30 = Date.from(hoje.atZone(ZoneId.systemDefault()).toInstant());
			dMais30.setDate(dMais30.getDate() + 0);
			String formato = "dd/MM/yyyy";
			SimpleDateFormat dataFormatada = new SimpleDateFormat(formato);
			String dataFinal = dataFormatada.format(dMais30);
			listaInfoTransacoes.forEach(strings -> {
				if (strings[4].equals(itemsAdiantamento[0])) {
					strings[0] = "pago";
					strings[2] = String.valueOf(Double.parseDouble(strings[2]) - (Double.parseDouble(strings[2])*Double.parseDouble(itemsAdiantamento[1])));
					strings[3] = dataFinal;
				}
			});
		}
		return listaInfoTransacoes;
	}

}
