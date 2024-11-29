package apicela.estoquepreco.connections;

import apicela.estoquepreco.constants.RabbitmqConsts;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;


@Component
public class RabbitMQConnection {
    private static final String EXCHANGE_NAME = "amq.direct";
    private AmqpAdmin amqpAdmin;


    public RabbitMQConnection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue queue(String queueName){
        return new Queue(queueName, true, false, false);
    }

    private DirectExchange directExchange(){
        return new DirectExchange(EXCHANGE_NAME);
    }

    private Binding binding(Queue queue, DirectExchange directExchange){
       return new Binding(queue.getName(), Binding.DestinationType.QUEUE, directExchange.getName(), queue.getName(), null);
    }
    @PostConstruct
    private void init(){
        Queue stockQueue = this.queue(RabbitmqConsts.STOCK_QUEUE);
        Queue priceQueue = this.queue(RabbitmqConsts.PRICE_QUEUE);

        DirectExchange exchange  = this.directExchange();
        Binding bindingEstoque = this.binding(stockQueue, exchange);
        Binding bindingPrice = this.binding(priceQueue, exchange);

        // cria fila rabbitmq
        this.amqpAdmin.declareQueue(stockQueue);
        this.amqpAdmin.declareQueue(priceQueue);

        this.amqpAdmin.declareExchange(exchange);

        this.amqpAdmin.declareBinding(bindingEstoque);
        this.amqpAdmin.declareBinding(bindingPrice);
    }
}
