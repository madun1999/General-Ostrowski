class TestOstrowski {
    public static void main(String[] args) {
        Alg1State a = new Alg1State(4366);
        a.addTransition(2,2);
        System.out.println(a);
        System.out.print(a.a+ " " + a.b + " " + a.c + "\n" + a.d + " " + a.e + " " + a.f + "\n" + a.g + "\n");
        a = new Alg1State(3976);
        System.out.print(a.a+ " " + a.b + " " + a.c + "\n" + a.d + " " + a.e + " " + a.f + "\n" + a.g + "\n");
    }
}
