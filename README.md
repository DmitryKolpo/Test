### Тестовое задание для Android разработчика

*   Требуется написать на Kotlin или Java мобильное приложение для Android, которое запрашивает у сервера определённое количество координат точек (x, y), а затем отображает полученный ответ в виде таблицы и графика.
*   На главном экране имеется блок информационного текста, поле для ввода числа точек и одна кнопка «Поехали».  
    По нажатию на кнопку осуществляется запрос к API сервера (`GET /api/test/points`), с параметром количества запрашиваемых точек (count).  
    Сервер выдаёт в ответ массив точек в JSON формате, пример: {"points":\[{"x":1.23, "y":2.44},{"x":2.17, "y":3.66}\]}  
    Спецификации API (если нужно):
    *   [Swagger 2](https://hr-challenge.interactivestandard.com/v2/api-docs?group=mobile) - [UI тут](https://hr-challenge.interactivestandard.com/swagger-ui.html?urls.primaryName=mobile),
    *   [OpenAPI (Swagger 3)](https://hr-challenge.interactivestandard.com/v3/api-docs/mobile) - [UI тут](https://hr-challenge.interactivestandard.com/v3/swagger-ui/index.html?configUrl=%2Fv3%2Fapi-docs%2Fswagger-config&urls.primaryName=mobile)
*   При неверном количестве запрошенных точек сервер возвращает ошибку. Кроме того он может просто ломаться сам-по-себе.
*   Если ответ от сервера получен, то на новом экране должна отобразиться таблица с полученными координатами точек. Ниже должен быть отображен график с точками, соединёнными прямыми линиями. Точки на графике должны следовать по возрастанию координаты x.  
    Дополнительно можно осуществить следующие возможности работы с графиком:
    *   изменения масштаба пользователем
    *   соединение точек не ломаной линией, а сглаженной
    *   работа в портретной и ландшафтной ориентации экрана
    *   сохранение изображения графика в файл
 
### Result

* Implemented: 2 screens, navigation, api request,  in memory cache, errors handling, chart, en/ru localisation, landscape/portrait orientation, light/dark theme

* Things to clarify:
   - "На главном экране имеется блок информационного текста" please clarify what text should be on top
   - "Точки на графике должны следовать по возрастанию координаты x" should points in table also be sorted or not?
 
* Known issues:
  - UX: all errors shows same Oops toast. Texts for different issues can be more infomative for user.
  - Axis texts in chart in dark theme should be white.

https://github.com/DmitryKolpo/Test/assets/56305336/4411bdf7-990d-4fc7-8b23-27bc640a41ea
