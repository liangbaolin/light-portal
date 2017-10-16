package net.lightapi.portal.form;

import com.networknt.eventuate.common.AggregateRepository;
import com.networknt.eventuate.common.EntityWithIdAndVersion;
import net.lightapi.portal.form.command.CreateFormCommand;
import net.lightapi.portal.form.command.DeleteFormCommand;
import net.lightapi.portal.form.command.FormCommand;
import net.lightapi.portal.form.command.UpdateFormCommand;
import net.lightapi.portal.form.domain.FormAggregate;

import java.util.concurrent.CompletableFuture;

/**
 * Created by stevehu on 2016-12-10.
 */
public class FormServiceImpl implements FormService {

    private AggregateRepository<FormAggregate, FormCommand> aggregateRepository;

    public FormServiceImpl(AggregateRepository<FormAggregate, FormCommand> aggregateRepository) {
        this.aggregateRepository = aggregateRepository;
    }


    @Override
    public CompletableFuture<EntityWithIdAndVersion<FormAggregate>> create(String data) {
        return aggregateRepository.save(new CreateFormCommand(data));
    }

    @Override
    public CompletableFuture<EntityWithIdAndVersion<FormAggregate>> remove(String id) {
        return aggregateRepository.update(id, new DeleteFormCommand());
    }

    @Override
    public CompletableFuture<EntityWithIdAndVersion<FormAggregate>> update(String id, String data) {
        return aggregateRepository.update(id, new UpdateFormCommand(id, data));
    }

}
