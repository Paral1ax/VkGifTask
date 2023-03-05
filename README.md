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


<img src="https://drive.google.com/uc?export=view&id=1FR6dh0ErqnEGuakQSY9KwRsaPVHn6jap" alt="trend" width="200"/>

---
Далее, вводя в поисковую строку запрос, и, нажимая кнопку поиска, подгружаются гифки по искомому запросу


<img src="https://drive.google.com/uc?export=view&id=1tl1ZeMOkXVEiaBEDqdTe-1Pk1BfFP74W" alt="searched" width="200"/>

Скроллится всё, и viewpager и recyclerview и, также, отдельно сам экран, для удобства просмотра
## Экран отображения выбранной гифки состоит из:
1) ImageView, отображающую выбранную гифку
2) 4 Панелей, выводящих информацию о гифке:
    * Username гифки
    * Рейтинг гифки
    * Размер гифки
    * Id гифки


<img src="https://drive.google.com/uc?export=view&id=1upweqtZKfE9hllxJ4rB2PwIN9gwlNlm6" alt="inside" width="200"/>
