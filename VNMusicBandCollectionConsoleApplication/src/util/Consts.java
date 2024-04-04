package util;

public class Consts {
    public static String[] MusicBandQueries = {
            "Введите название группы: ",
            "coordinates",
            "Введите число участников: ",
            "Введите число альбомов: ",
            "Введите описание: ",
            "genre",
            "album"
    };

    public static boolean[] MusicBandNecessaries = {
            true,
            true,
            true,
            false,
            false,
            true
    };

    public static String[] MusicBandTypes = {
            "string",
            "coordinates",
            "long",
            "long",
            "string",
            "genre",
            "album"
    };

    public static String[] CoordinatesQueries = {
            "Введите X:",
            "Введите Y:"
    };

    public static boolean[] CoordinatesNecessaries = {
            false,
            true
    };

    public static String[] CoordinatesTypes = {
            "long",
            "long"
    };

    public static String[] AlbumQueries = {
            "Введите название: ",
            "Введите длину: "
    };

    public static boolean[] AlbumNecessaries = {
            true,
            true
    };

    public static String[] AlbumTypes = {
            "string",
            "long"
    };

    public static String CommandList = "help : вывести справку по доступным командам\n" +
            "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
            "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
            "add {element} : добавить новый элемент в коллекцию\n" +
            "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
            "remove_by_id id : удалить элемент из коллекции по его id\n" +
            "clear : очистить коллекцию\n" +
            "save : сохранить коллекцию в файл\n" +
            "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
            "exit : завершить программу (без сохранения в файл)\n" +
            "insert_at index {element} : добавить новый элемент в заданную позицию\n" +
            "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
            "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
            "count_less_than_genre genre : вывести количество элементов, значение поля genre которых меньше заданного\n" +
            "filter_starts_with_description description : вывести элементы, значение поля description которых начинается с заданной подстроки\n" +
            "print_descending : вывести элементы коллекции в порядке убывания";
}
