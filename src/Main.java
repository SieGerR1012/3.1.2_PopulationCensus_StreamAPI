void main() {
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

    // Поиск количества несовершеннолетних людей младше 18 лет
    long minorsCount = persons.stream()
            .filter(p -> p.getAge() < 18) // Фильтр менее 18
            .count();
    System.out.println("Несовершеннолетних: " + minorsCount);

    // Поиск фамилий призывников - мужчин от 18 и до 27 лет
    List<String> recruits = persons.stream()
            .filter(p -> p.getSex() == Sex.MAN) // Фильтр по мужчинам
            .filter(p -> p.getAge() >= 18 && p.getAge() <= 27) // Фильтр по возрасту от 18 до 27
            .map(Person::getFamily)
            .collect(Collectors.toList());
    System.out.println("Призывников: " + recruits.size());

    // Сортировка по фамилии список потенциально работоспособных людей с высшим образованием от 18 до 60 лет женщин и до 65 лет мужчин
    List<Person> workers = persons.stream()
            .filter(p -> p.getEducation() == Education.HIGHER)
            .filter(p -> {
                if (p.getSex() == Sex.WOMAN) {
                    return p.getAge() >= 18 && p.getAge() <= 60;
                } else {
                    return p.getAge() >= 18 && p.getAge() <= 65;
                }
            })
            .sorted(Comparator.comparing(Person::getFamily))
            .collect(Collectors.toList());
    System.out.println("Работоспособных: " + workers.size());
}
