package pmb.recipes.adapter.rest.exception;

import org.springframework.core.NestedRuntimeException;

/** Thrown when a resource is not found. */
public class NotFoundException extends NestedRuntimeException {

  public NotFoundException(String msg) {
    super(msg);
  }
}
