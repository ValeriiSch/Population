package com.company;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.company.Education.HIGHER;
import static com.company.Sex.MAN;
import static com.company.Sex.WOMAN;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
//        System.out.println(persons.toString());

        long count = persons.parallelStream()
                .filter(x -> x.getAge() < 18)
                .count();
//        System.out.println(count);

        List<String> listFamilies = new ArrayList<>();
        listFamilies = persons.parallelStream()
                .filter(x -> x.getSex() == MAN)
                .filter(x -> x.getAge() >= 18 && x.getAge() < 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
//        System.out.println(listFamilies);

        persons = persons.parallelStream()
                .filter(x -> x.getEducation() == HIGHER && x.getAge() >= 18)
                .filter(x -> (x.getSex() == MAN && x.getAge() < 65) || (x.getSex() == WOMAN && x.getAge() < 60))
                .sorted(Comparator.comparing(x -> x.getFamily()))
                .collect(Collectors.toList());
//        System.out.println(persons.toString());

    }
}
