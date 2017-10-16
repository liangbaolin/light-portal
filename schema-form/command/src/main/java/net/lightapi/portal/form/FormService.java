package net.lightapi.portal.form;

import com.networknt.eventuate.common.EntityWithIdAndVersion;
import net.lightapi.portal.form.domain.FormAggregate;

import java.util.concurrent.CompletableFuture;

/**
 * Created by stevehu on 2016-12-10.
 */
public interface FormService {

    CompletableFuture<EntityWithIdAndVersion<FormAggregate>> create(String data);

    CompletableFuture<EntityWithIdAndVersion<FormAggregate>> remove(String host);

    CompletableFuture<EntityWithIdAndVersion<FormAggregate>> update(String host, String data);
}
