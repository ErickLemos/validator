package org.ericklemos;

import org.ericklemos.models.Planeta;
import org.ericklemos.models.PlanetaTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    @DisplayName("sucesso")
    void sucesso() {

        var planeta = PlanetaTemplate.load();

        var planetaValidado = Validator.ofType(Planeta.class)
                .addRegra(item -> Objects.nonNull(item.getId()), "id não pode ser nulo")
                .addRegra(item -> Objects.nonNull(item.getNome()), "nome não pode ser nulo")
                .supplier(planeta)
                .validar();

        assertEquals(planeta.getId(), planetaValidado.getId());
        assertEquals(planeta.getNome(), planetaValidado.getNome());

    }

    @Test
    @DisplayName("disparar exception caso o valor repassado no supplier seja nulo")
    void validarSupplierNulo() {

        var supplier = Validator.ofType(Planeta.class)
                .supplier(null);

        assertThrows(ValidationException.class,
                supplier::validar,
                "objeto não pode ser nulo");

    }

    @Test
    @DisplayName("disparar exception caso regras nao sejam atendidas")
    void validarRegras() {

        var planeta = PlanetaTemplate.load();
        planeta.setId(null);
        planeta.setNome("Marte");

        var supplier = Validator.ofType(Planeta.class)
                .addRegra(item -> Objects.nonNull(item.getId()), "id não pode ser nulo")
                .addRegra(item -> item.getNome().equals("Terra"), "nome precisa ser Terra")
                .supplier(planeta);

        var exception = assertThrows(ValidationException.class, supplier::validar);

        var mapSupressException = Arrays.stream(exception.getSuppressed())
                .collect(Collectors.toMap(Throwable::getMessage, e -> e));

        assertNotNull(mapSupressException.get("id não pode ser nulo"));
        assertNotNull(mapSupressException.get("nome precisa ser Terra"));

    }

}