package java.com.github.example.state.statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineModelConfigurer;
import org.springframework.statemachine.config.model.StateMachineModelFactory;
import org.springframework.statemachine.data.*;
import org.springframework.statemachine.data.support.StateMachineJackson2RepositoryPopulatorFactoryBean;
import java.com.github.example.state.client.OrderClient;
import java.com.github.example.state.messages.CatalogEvent;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends StateMachineConfigurerAdapter<String, String> {

    @Autowired
    private OrderClient orderClient;
    @Autowired
    private StateRepository<? extends RepositoryState>  stateRepository;
    @Autowired
    private TransitionRepository<? extends RepositoryTransition> transitionRepository;
    @Autowired
    Source mysource;

    @Bean
    public StateMachineJackson2RepositoryPopulatorFactoryBean jackson2RepositoryPopulatorFactoryBean() {
        StateMachineJackson2RepositoryPopulatorFactoryBean factoryBean = new StateMachineJackson2RepositoryPopulatorFactoryBean();
        factoryBean.setResources(new Resource[]{new ClassPathResource("data.json")});
        return factoryBean;
    }

    @Bean
    public StateMachineModelFactory<String, String> modelFactory() {
        return new RepositoryStateMachineModelFactory(stateRepository, transitionRepository);
    }

    @Override
    public void configure(StateMachineModelConfigurer<String, String> model) throws Exception {
        model
                .withModel()
                .factory(modelFactory());
    }


    @Bean(name = "initAction")
    public Action<String, String> initAction() {
        return ctx -> {
            StateMachine<String, String> stateMachine =  ctx.getStateMachine();
            mysource.output().send(MessageBuilder.withPayload(CatalogEvent.builder().carId((Integer)stateMachine.getExtendedState().getVariables().get("carID"))
                    .status("start").orderId((Integer)stateMachine.getExtendedState().getVariables().get("orderID"))
            .startRentdate((String)stateMachine.getExtendedState().getVariables().get("rentDate")).build()).build());
        };
    }


    @Bean(name = "execAction")
    public Action<String, String>  executionAction() {
        return ctx -> {
            StateMachine<String, String>  stateMachine =  ctx.getStateMachine();
            mysource.output().send(MessageBuilder.withPayload(CatalogEvent.builder().carId((Integer)stateMachine.getExtendedState().getVariables().get("carID"))
                    .status("rent").orderId((Integer)stateMachine.getExtendedState().getVariables().get("orderID"))
                    .startRentdate((String)stateMachine.getExtendedState().getVariables().get("rentDate"))
                    .endRentdate((String)stateMachine.getExtendedState().getVariables().get("endDate"))
                    .build()).build());
            orderClient.changeOrderStatus((Integer)stateMachine.getExtendedState().getVariables().get("orderID"),ctx.getTarget().getId());
        };
    }

    @Bean(name = "cancelAction")
    public Action<String, String>  cancelAction() {
        return ctx -> {
            StateMachine<String, String>  stateMachine =  ctx.getStateMachine();
            mysource.output().send(MessageBuilder.withPayload(CatalogEvent.builder().carId((Integer)stateMachine.getExtendedState().getVariables().get("carID"))
                    .status("enable").orderId((Integer)stateMachine.getExtendedState().getVariables().get("orderID"))
                    .startRentdate((String)stateMachine.getExtendedState().getVariables().get("rentDate"))
                    .endRentdate((String)stateMachine.getExtendedState().getVariables().get("endDate"))
                    .build()).build());
            orderClient.changeOrderStatus((Integer)stateMachine.getExtendedState().getVariables().get("orderID"),ctx.getTarget().getId());
                   };
    }

    @Bean(name = "finishAction")
    public Action<String, String>  finishAction() {
        return ctx -> {
            StateMachine<String, String>  stateMachine =  ctx.getStateMachine();
            mysource.output().send(MessageBuilder.withPayload(CatalogEvent.builder().carId((Integer)stateMachine.getExtendedState().getVariables().get("carID"))
                    .status("enable").orderId((Integer)stateMachine.getExtendedState().getVariables().get("orderID"))
                    .startRentdate((String)stateMachine.getExtendedState().getVariables().get("rentDate"))
                    .endRentdate((String)stateMachine.getExtendedState().getVariables().get("endDate"))
                    .build()).build());
            orderClient.changeOrderStatus((Integer)stateMachine.getExtendedState().getVariables().get("orderID"),ctx.getTarget().getId());
        };
    }


}
