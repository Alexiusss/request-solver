package com.example.request_solver.util;

import com.example.request_solver.exception.IllegalRequestData;
import com.example.request_solver.model.Request;
import com.example.request_solver.to.RequestCreationTo;
import com.example.request_solver.to.RequestTo;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for working with {@link Request} entities.
 */
@UtilityClass
public class RequestUtil {

    /**
     * Converts a {@link Request} entity to a {@link RequestTo} DTO.
     *
     * @param request The {@link Request} entity to convert.
     * @return The corresponding {@link RequestTo} DTO.
     */
    public static RequestTo asTo(Request request) {
        return new RequestTo(request.getId(), request.getCreatedAt().toLocalDate(), request.getTitle(), request.getDescription(), request.getStatus());
    }

    /**
     * Converts a {@link RequestCreationTo} DTO to a {@link RequestTo} DTO.
     *
     * @param request The {@link RequestCreationTo} DTO to convert.
     * @return The corresponding {@link RequestTo} DTO.
     */
    public static RequestTo asTo(RequestCreationTo request) {
        return new RequestTo(request.getTitle(), request.getDescription());
    }

    /**
     * Converts a list of {@link Request} entities to a list of {@link RequestTo} DTOs.
     *
     * @param requests The list of {@link Request} entities to convert.
     * @return The corresponding list of {@link RequestTo} DTOs.
     */
    public static List<RequestTo> asTos(List<Request> requests) {
        return requests.stream()
                .map(RequestUtil::asTo)
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of {@link Request} entities to a list of {@link RequestTo} DTOs,
     * specifically formatting the text for operators.
     *
     * @param requests The list of {@link Request} entities to convert.
     * @return The corresponding list of {@link RequestTo} DTOs with formatted text.
     */
    public static List<RequestTo> asTosForOperator(List<Request> requests) {
        return requests.stream()
                .map(RequestUtil::asTo)
                .map(RequestUtil::formatTextForOperator)
                .collect(Collectors.toList());
    }

    /**
     * Formats the title and description of a {@link RequestTo} DTO for operators.
     *
     * @param requestTo The {@link RequestTo} DTO to format.
     * @return The formatted {@link RequestTo} DTO.
     */
    private static RequestTo formatTextForOperator(RequestTo requestTo) {
        String formattedTitle = formatString(requestTo.getTitle());
        String formattedDescription = formatString(requestTo.getDescription());
        requestTo.setTitle(formattedTitle);
        requestTo.setDescription(formattedDescription);
        return requestTo;
    }

    /**
     * Formats a string by replacing spaces with hyphens and joining characters with hyphens.
     *
     * @param value The string to format.
     * @return The formatted string.
     */
    static String formatString(String value) {
        return String.join("-", value.split("")).replaceAll(" ", "");
    }

    /**
     * Creates a {@link Pageable} object based on the provided parameters.
     *
     * @param page      The page number.
     * @param size      The page size.
     * @param direction The sort direction (ASC or DESC).
     * @return The created {@link Pageable} object.
     * @throws IllegalRequestData If the sort direction is invalid.
     */
    public static Pageable createPageable(int page, int size, String direction) {
        try {
            return PageRequest.of(page, size, Sort.Direction.valueOf(direction.toUpperCase()), "createdAt");
        } catch (IllegalArgumentException e) {
            throw new IllegalRequestData("Invalid sort direction: " + direction);
        }
    }
}