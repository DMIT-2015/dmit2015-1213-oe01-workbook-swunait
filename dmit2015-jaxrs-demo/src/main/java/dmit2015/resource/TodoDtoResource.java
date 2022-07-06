package dmit2015.resource;

import common.validator.BeanValidator;
import dmit2015.dto.TodoMapper;
import dmit2015.entity.Todo;
import dmit2015.dto.TodoDto;
import dmit2015.repository.TodoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.stream.Collectors;

@ApplicationScoped
@Path("TodoDtos")                    // All methods of this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)    // All methods this class accept only JSON format data
@Produces(MediaType.APPLICATION_JSON)    // All methods returns data that has been converted to JSON format
public class TodoDtoResource {

    @Inject
    private TodoRepository _todoRepository;

    @GET    // This method only accepts HTTP GET requests.
    public Response listTodos() {
        return Response.ok(
                _todoRepository
                        .findAll()
                        .stream()
                        .map(TodoMapper.INSTANCE::toDto)
                        .collect(Collectors.toList())
        ).build();
    }

    @Path("{id}")
    @GET    // This method only accepts HTTP GET requests.
    public Response findTodoById(@PathParam("id") Long todoId) {
        Todo existingTodo = _todoRepository.findOptionalById(todoId).orElseThrow(NotFoundException::new);

        TodoDto dto = TodoMapper.INSTANCE.toDto(existingTodo);

        return Response.ok(dto).build();
    }

    @POST    // This method only accepts HTTP POST requests.
    public Response addTodo(TodoDto dto, @Context UriInfo uriInfo) {
        Todo newTodo = TodoMapper.INSTANCE.toEntity(dto);

        String errorMessage = BeanValidator.validateBean(Todo.class, newTodo);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        try {
            // Persist the new Todo into the database
            _todoRepository.add(newTodo);
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // userInfo is injected via @Context parameter to this method
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(newTodo.getId() + "")
                .build();

        // Set the location path of the new entity with its identifier
        // Returns an HTTP status of "201 Created" if the Todo was successfully persisted
        return Response
                .created(location)
                .build();
    }

    @PUT            // This method only accepts HTTP PUT requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response updateTodo(@PathParam("id") Long id, TodoDto dto) {
        if (!id.equals(dto.getId())) {
            throw new BadRequestException();
        }

        Todo existingTodo = _todoRepository
                .findOptionalById(id)
                .orElseThrow(NotFoundException::new);

        Todo updatedTodo = TodoMapper.INSTANCE.toEntity(dto);

        String errorMessage = BeanValidator.validateBean(Todo.class, updatedTodo);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        // TODO: copy properties from the updated entity to the existing entity such as copy the version property shown below
        existingTodo.setVersion(updatedTodo.getVersion());
        existingTodo.setName(updatedTodo.getName());
        existingTodo.setComplete(updatedTodo.isComplete());

        try {
            _todoRepository.update(existingTodo);
        } catch (OptimisticLockException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("The data you are trying to update has changed since your last read request.")
                    .build();
        } catch (Exception ex) {
            // Return an HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "200 OK" and include in the body of the response the object that was updated
        return Response.ok(existingTodo).build();
    }

    @DELETE            // This method only accepts HTTP DELETE requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response delete(@PathParam("id") Long todoId) {

        Todo existingTodo = _todoRepository
                .findOptionalById(todoId)
                .orElseThrow(NotFoundException::new);

        try {
            _todoRepository.delete(existingTodo);    // Removes the Todo from being persisted
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response
                    .serverError()
                    .encoding(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "204 No Content" if the Todo was successfully deleted
        return Response.noContent().build();

    }

}