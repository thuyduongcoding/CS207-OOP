public class Cat extends Animal {
    public Cat(String name, int age, String color) {
        super(name, age, color);
    }
    @Override
    public void sound() {
        System.out.println("meow meow...");
    }

    public void sound(boolean angry) {
        System.out.println("I am an angry cat!");
    }
}
