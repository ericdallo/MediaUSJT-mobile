package com.mediausjt;
/**
 * Created by eric on 09/03/15.
 */
public class Nota {
    private int id;
    private String materia;
    private String nota;


    public Nota(int id,String materia,String nota){
        setId(id);
        setMateria(materia);
        setNota(nota);
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getMateria() {

        return materia;
    }


    public String getNota() {
        return nota;
    }

    public int getId() {
        return id;
    }


}