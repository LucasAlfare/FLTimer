# Especificações gerais CLIENT

As seguintes especificações levam em consideração que o cliente estará se conectando a um servidor que não implementa gerenciamento de salas, ou seja, estará se conectado diretamente no _core_ do servidor. Dessa forma, define-se que:
- o projeto de cliente deverá ter um objeto central `User` com formato similar a esse mesmo formato que há no servidor;
- quando um cliente conectar no servidor, ele deverá receber:
    - lista de todos os usuários que já estão conectados lá no servidor;
    - todos as solves que esses usuários já fizeram;
- esses dados deverão ser montados usando o objeto de tipo `User` listado acima;
- um cliente só poderá INICIAR uma solve caso tenha recebido notificação do servidor que TODOS OS OUTROS clientes já tenham finalizado suas respectivas solves da rodada;
- quando o cliente iniciar sua fase de contagem (timer_start), ele deverá notificar o servidor;
- quando for notificado que o cliente iniciou, esse mesmo cliente deverá enviar juntamente dessa notificação a marcação de tempo exata que o evento ocorreu;
- quando o cliente finalizar sua fase de contagem (timer_stop), ele deverá notificar o servidor;
- quando for notificado que o cliente finalizou, esse mesmo cliente deverá enviar juntamente dessa notificação a marcação de tempo exata que o evento ocorreu;
- sempre que um cliente receber notificação do servidor que algum cliente (outro ou si próprio) finalizou uma solve, o cliente deverá receber todos os dados contidos no servidor:
    - todos os usuários conectados;
    - tempos de cada usuários;
- Cada cliente deverá listar os dados dos objetos `User` em formato de "tabela" da seguinte forma:
    - cada usuário de cliente recebido (incluindo o próprio) deverá ser uma coluna vertical;
    - as solves desse usuário devem ser organizadas verticalmente.
