package model;

public class AnimalesModel {
    private String id;
    private String color;
    private String especie;
    private String animal;
    private String alimento;
    private double peso;
    private String habitad;
    private String altura;

    public String getColor() {
        return color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getAlimento() {
        return alimento;
    }

    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getHabitad() {
        return habitad;
    }

    public void setHabitad(String habitad) {
        this.habitad = habitad;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    @Override
    public String toString() {
        return "AnimalesModel{" + "id=" + id + ", color=" + color + ", especie=" + especie + ", animal=" + animal + ", alimento=" + alimento + ", peso=" + peso + ", habitad=" + habitad + ", altura=" + altura + '}';
    }

   
    
    
}
