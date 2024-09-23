public class Dog extends Animal {
    public Dog(String name, int age, String color) {
        super(name, age, color);
    }
    public void intro() {
        System.out.println("I am a dog");
        super.intro();
    }
    @Override
    public void sound() {
        System.out.println("woof woof...");
    }
    public void sound(boolean angry) {
        System.out.println("I am angry!");
    }
}


