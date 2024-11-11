# Pr_2
Варіант 3:
Напишіть програму, яка асинхронно обробляє великий масив
чисел для обчислення середнього значення масиву. Розбивайте масив
на частини і обробляйте кожну частину в окремому потоці.
Діапазон [0; 1000] – цілі числа. Використати CopyOnWriteArraySet.


В програмі є два класи: ArrayCalculator і ArrayProcessor.

ArrayCalculator - головний клас програми, який виконує:
  Генерацію масиву випадкових чисел у вказаному діапазоні.
  Запуск меню для введення параметрів (мінімальне і максимальне значення діапазону, розмір масиву).
  Розбиття масиву на частини та запуск потоків для обробки кожної частини.
  Обчислення середнього значення та вивід часу виконання.

ArrayProcessor - це клас, що реалізує Callable<Double>, який виконує обчислення суми елементів підмасиву. Повернуте значення кожного потоку — це сума відповідної частини масиву.

Програма приймає дані користувача про діапозон і кількість елементів масиву, після цього йде розбиття на частини і запуск їх обробки. 
Після отримання результатів виконується завершальне обчислення і вивід результатів.
