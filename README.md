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
![Trend](https://drive.google.com/file/d/1FR6dh0ErqnEGuakQSY9KwRsaPVHn6jap/view?usp=share_link)
---
Далее, вводя в поисковую строку запрос, и, нажимая кнопку поиска, подгружаются гифки по искомому запросу
![Searched](https://drive.google.com/file/d/1tl1ZeMOkXVEiaBEDqdTe-1Pk1BfFP74W/view?usp=share_link)

Скроллится всё, и viewpager и recyclerview и, также, отдельно сам экран, для удобства просмотра
## Экран отображения выбранной гифки состоит из:
1) ImageView, отображающую выбранную гифку
2) 4 Панелей, выводящих информацию о гифке:
    * Username гифки
    * Рейтинг гифки
    * Размер гифки
    * Id гифки
![InsideGif](https://drive.google.com/file/d/1upweqtZKfE9hllxJ4rB2PwIN9gwlNlm6/view?usp=share_link)
