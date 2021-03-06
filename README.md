# O que é isso?

Repositório com o desafio da liga da qualidade de código

## Link do board do trello

https://trello.com/b/e9f2nPy8/desafios-liga-qualidade-de-codigo

### Descrição do que precisa ser feito

Vamos implementar um provedor de serviços de pagamento. A ideia é que a gente receba transações referentes a compras de produtos de lojistas e que, depois, esses lojistas possam realizar seus saques em cima do saldo disponível(recebíveis). 

## Sobre a implementação

Você vai receber um projeto com uma parte de código pronta responsável por passar os dados necessários para que você consiga implementar um fluxo completo de geração de recebíveis. Ainda neste mesmo projeto você vai encontrar arquivos de testes que testam o código que você vai desenvolver contra cenários diferentes. 

Você vai perceber que o retorno esperado pelo método também já pronto. 

O seu objetivo é implementar o fluxo complexo para que os retornos esperados aconteçam.

Cada cartão implementado vai habilitar que mais cenários de testes passem. 

O ponto de entrada da sua solução é classe ```Solucao``` e seu método ```executa```.  Todos os testes rodam em cima deste método. 

Você não pode mexer em nada que exista no pacote **pronto**.

## Sobre a entrada dos dados

O ponto de entrada da sua aplicação vai receber duas listas de Strings com valores separados por virgula. 

Para o parâmetro que representa as transações, a String está formatada da seguinte maneira:

```
"valor,metodoPagamento,numeroCartao,nomeCartao,validade,cvv,idTransacao"
```

* O método de pagamento é 'DEBITO' ou 'CREDITO'
* Validade é uma data no formato dd/MM/yyyy.

Para o parâmetro que representa informação de adiantamento, a String está formatada da seguinte maneira:

```
"idTransacao,valorAdiantamento"
```


### Criação de recebíveis do cliente

## Necessidades

* Caso a transação tenha sido feito com cartão de débito
   * O recebível deve ser criado com o status 'pago' e com o dia de recebimento apontando para o dia da transação (D + 0)

* Caso a transação tenha sido feito com cartão de crédito
   * O recebível deve ser criado com o status 'aguardando_liberacao_fundos' e com o dia de recebimento apontando para o dia da transação mais 30 dias (D + 30)

O valor que a loja tem a receber(recebível) em função de uma transação tem um desconto por conta da taxa de processamento. 

* Caso a transação tenha sido feita no débito, o recebível é criado com um desconto de 3% em cima do valor da transação. 
* Caso a transação tenha sido feita no crédito, o recebível é criado com um desconto de 5% em cima do valor da transação. 

### Adiantamente de liberação de fundos

## Necessidades

Quando o pagamento é feito via cartão de crédito, a loja precisa aguardar o período de 30 dias para conseguir ter acesso ao valor. Só que nem sempre o(a) lojista pode ficar esperando por isso. Neste caso entra o pedido de adiantamento. 

O detalhe importante é que para pedir esse adiantamento, o lojista precisa pagar uma taxa extra para o serviço, além da que já é descontada pela facilitação do pagamento em si. 

## Como funciona a regra do adiantamento?

Aqui vamos usar uma versão simplificada. Para cada transação um adiantamento pode ser solicitado a um custo de uma taxa específica. 

Então para cada transação que o(a) lojista quiser receber um adiantamento, o valor original vai sofrer primeiro o desconto da taxa da transação. E depois, em cima do valor que sobrou pós taxa de desconto de operação, vai acontecer o desconto com a taxa do adiantamento.

## Implementação

* O método ```executa``` já recebe a lista de String com as informações relativas ao id da transação que precisa ser adiantada e também a taxa a ser aplicada.

## Resultado esperado

O recebível que antes teria o status "aguardando_pagamento" agora vai ter o status "pago", assim como o dia de recebimento vai ser D+0 em vez de D+30
