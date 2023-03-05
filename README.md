# VkGifTask
Репозиторий с заданием для теста в vk
## Приложение состоит из 2 экранов:
1. Экран отображения всех загружаемых гифок.
2. Экран отображения выбранной гифки и информации о ней.

## Экран отображения всех загружаемых гифок состоит из:
1. Поисковой строки
2. Кнопки поиска
3. ViewPager2
4. RecyclerView
---
При загрузке приложения, сначала загружается список из 25 трендовых гифок
![Trend](https://www.dropbox.com/s/4v0eo694gfc6xg8/trend.png?dl=0)

---
Далее, вводя в поисковую строку запрос, и, нажимая кнопку поиска, подгружаются гифки по искомому запросу
![Searched](https://www.dropbox.com/s/4xl1mfvmb35mhyv/Screenshot_20230305_164804.png?dl=0)

Скроллится всё, и viewpager и recyclerview и, также, отдельно сам экран, для удобства просмотра
## Экран отображения выбранной гифки состоит из:
1) ImageView, отображающую выбранную гифку
2) 4 Панелей, выводящих информацию о гифке:
    * Username гифки
    * Рейтинг гифки
    * Размер гифки
    * Id гифки
![InsideGif](https://www.dropbox.com/s/rbn7iptlmr8ubyi/Screenshot_20230305_165852.png?dl=0)
