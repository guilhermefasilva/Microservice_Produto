# language: pt
Funcionalidade: Propondo um id valido deve retornar um produto cadastrado no banco de dados

Esquema do Cenario: Propondo o cadastro de um produto
    #Dado que faça uma solicitação para cadastrar um produto
    Quando enviar id <id>, nome <nome>, marca <marca>, descricao <descricao> e preco <preco>
    Entao é retornado um novo produto cadastrado

		
 
  
  
  Exemplos:
		  | id  	 |nome			|marca			|descricao			|preco		 |
 			|  123	 |Doril			|agora		  |Cartela 			  |	10.99    |