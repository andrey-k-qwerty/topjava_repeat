package ru.javawebinar.topjava;

import java.util.function.Predicate;

/**
 * @see <a href="http://topjava.herokuapp.com">Demo application</a>
 * @see <a href="https://github.com/JavaOPs/topjava">Initial project</a>
 */
public class Main {
    public static void main(String[] args) {
        System.out.format("Hello TopJava Enterprise!\n");

        /* Проверка работы как работает предикант*/

        Predicate<Boolean> predicate = aBoolean ->{
            System.out.println("first");
            return true;};
        for (int i = 0; i < 5; i++) {

            int fI = i;
            predicate = predicate.and (b -> {
                System.out.println(b);
                System.out.println(fI);
                return fI % 2 == 0;
              //   return true;
            });
        }
        predicate.test(true);
    }
}
