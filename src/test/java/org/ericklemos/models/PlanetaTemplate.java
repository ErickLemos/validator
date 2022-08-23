package org.ericklemos.models;


public interface PlanetaTemplate {

    static Planeta load() {
        var planeta = new Planeta();
        planeta.setId("id");
        planeta.setNome("Terra");
        return planeta;
    }

}
