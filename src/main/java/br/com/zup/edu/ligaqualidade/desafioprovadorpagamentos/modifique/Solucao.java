package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique;

import java.text.DecimalFormat;
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

		/*
		items transacao:
		100,CREDITO,764387534,Nome do cartao,06/03/2023,457,1
		itemsTransacao[0] -> valor
		itemsTransacao[1] -> metodoPagamento
		itemsTransacao[2] -> numeroCartao
		itemsTransacao[3] -> nomeCartao
		itemsTransacao[4] -> validade
		itemsTransacao[5] -> cvv
		itemsTransacao[6] -> idTransacao

		 */

		LocalDateTime hoje = LocalDateTime.now();
		//Date dateHoje = Date.from(hoje.atZone(ZoneId.systemDefault()).toInstant());

		String status = null;
		String valorOriginal = null;
		String valorASerRecebido = null;
		String dataRecebimento = null;
		
		String[] infoTransacao = new String[] {"A","B","C","D"};

		double valor;

		Date dMais30 = Date.from(hoje.atZone(ZoneId.systemDefault()).toInstant());

		List<String[]> listaInfoTransacoes = new ArrayList<String[]>();

		for (String transacao : infoTransacoes) {
			//System.out.println(transacao);
			String[] itemsTransacao = transacao.split(",");
			if(itemsTransacao[1].equalsIgnoreCase("CREDITO")) {
				status = "aguardando_liberacao_fundos";
				dMais30.setDate(dMais30.getDate() + 30);
				String formato = "dd/MM/yyyy";
				SimpleDateFormat dataFormatada = new SimpleDateFormat(formato);
				dataRecebimento = dataFormatada.format(dMais30);
				valorOriginal = itemsTransacao[0];
				valor = Double.parseDouble(valorOriginal) - 0.05*Double.parseDouble(valorOriginal);
				valorASerRecebido = String.valueOf(valor);
				infoTransacao[0] = status;
				infoTransacao[1] = valorOriginal;
				infoTransacao[2] = valorASerRecebido;
				infoTransacao[3] = dataRecebimento;
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
			}

			listaInfoTransacoes.add(infoTransacao);

		}

		/*
		System.out.println("ADIANTAMENTOS");
		for (String adiantamento : infoAdiantamentos) {
			System.out.println(adiantamento);
		}
		*/

		// 100,CREDITO,764387534,Nome do cartao,06/03/2023,457,1
		// 1,0.01

		
		// status, valorOriginal, valorASerRecebido, dataRecebimento
		//return List.of(new String[][] {
		//			 {"pago","200","194","04/03/2021"}
		//			});

		return listaInfoTransacoes;
	}

}
