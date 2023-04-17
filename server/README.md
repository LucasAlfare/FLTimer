# Especificações gerais SERVER

As seguintes especificações levam em consideração que um servidor que será capaz de receber múltiplas conexões _websocket_. O mecanismo `core` deste servidor deverá ser sincronizar os clientes conectados em "fases" definidas no formato de "rounds". Ou seja, todos os usuários só poderão iniciar suas respectivas rodadas caso todos já tenham finalizado. Dessa forma, define-se que:

- criar-se-á um tipo geral de dado chamado de `User` para ser o formato principal de dados do servidor;
- o tipo `User` deverá conter dados como:
    - metadados;
    - lista de solves;
    - variáveis de controle (ativo, inativo, etc);
- a toda conexão de um novo cliente recebida deverá ser criado um objeto de tipo `User` respectivo;
- a toda desconexão de um novo cliente recebida deverá ser removido o objeto de tipo `User` respectivo;
- sempre que um cliente for conectado o servidor deverá propagar para TODOS OS CLIENTES (incluindo o recém conectado) a lista principal de `User`s;
- quando um cliente solicitar para iniciar uma solve deverá ser verificado se todos os clientes conectados já finalizaram;
- para verificar se todos os clientes já finalizaram deverá ser checado se o cliente X esteve ativo e se esse mesmo cliente está inativo no momento;
- para definir um cliente como inativo deverá ser obrigatório fazer isso somente se uma variável de controle (e.x.: "`isActive`") estiver como "`true`";
- o servidor deverá manter um "_loop de update_" infinito sempre ativo com o objetivo de fazer as verificações de cada cliente a cada "_tick_";
- dentro do loop de update principal, as ações principais a serem executadas serão:
    - sempre que for detectado que todos os clientes conectados já terminaram suas solves deverá ser propagada a informação que o início de uma nova rodada já está disponível;
    - detectar se um cliente enviou a informação que inicou corretamente uma solve. Caso ocorra, o servidor irá propagar isso para todos os clientes conectados, exceto para o respectivo;
    - a cada início de rodada, sincronizar um embaralhamento fixo para todos os clientes;
