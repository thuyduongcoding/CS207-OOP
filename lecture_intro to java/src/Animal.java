public class Animal {
    String name;
    int age;
    String color;

    public Animal(String name, int age, String color){
        this.name = name;
        this.age = age;
        this.color = color;
    }

    public void sound() {
        System.out.println("I am an abstract animal. Having no specific sound.");
    }

    public void intro() {
        System.out.println("My name is: " + name);
        System.out.println("I am " + age + " years old");
    }
}
