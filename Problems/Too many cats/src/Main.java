class Cat {

    private static int counter = 0;

    public Cat(String name, int age) {
        counter++;
        if (counter > 5) {
            System.out.println("You have too many cats");
        }
    }

    public static int getNumberOfCats() {
        return counter;
    }
}
