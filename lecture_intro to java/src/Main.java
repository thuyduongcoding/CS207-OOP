public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");

        Animal cat = new Cat ("Pearl", 3, "white");
        cat.intro();

        if (cat instanceof Cat) {
            Cat cat2 = (Cat) cat;
        }
        cat2.intro()
    }
}