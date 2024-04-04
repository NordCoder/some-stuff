package entity;

public class Album {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Long length; //Поле не может быть null, Значение поля должно быть больше 0

    public Album(String name, Long length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public Long getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                ", length=" + length +
                '}';
    }
}
