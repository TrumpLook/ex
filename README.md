1 вопрос.
ArrayDeque - это реализация интерфейса Deque в Java, использующая массив для хранения элементов.
Реализует интерфейсы Deque, Queue, ... 
Динамически расширяемый массив, не потокобезопасен, не поддерживает null-элементы.
Добавление: addFirst(), addLast(), offerFirst(), offerLast()
Удаление: removeFirst(), removeLast(), pollFirst(), pollLast()
Просмотр: getFirst(), getLast(), peekFirst(), peekLast()


2 вопрос.
Logback - современный фреймворк для логирования.
Основные компоненты:
Logger - основной объект для записи логов
Appender - определяет куда писать логи, например, консоль или файл.
Layout - формат вывода логов.

Logback поддерживает стандартные уровни логирования:

ERROR - критические ошибки
WARN - предупреждения
INFO - информационные сообщения
DEBUG - отладочная информация
TRACE - максимально детальная информация


Jackson - библиотека для работы с JSON.
Основные функции:
Сериализация объектов в JSON
Десериализация JSON в объекты
Работа с деревьями узлов
Поддержка аннотаций

Typesafe Config - библиотека для конфигурации Java-приложений.
Особенности:
Поддержка форматов .conf, .json, .properties
Поддержка наследования конфигов
Поддержка подстановок
Типобезопасность
