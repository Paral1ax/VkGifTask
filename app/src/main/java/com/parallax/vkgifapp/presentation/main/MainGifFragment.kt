package com.parallax.vkgifapp.presentation.main

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import com.parallax.vkgifapp.MainActivity
import com.parallax.vkgifapp.R
import com.parallax.vkgifapp.api.ApiState
import com.parallax.vkgifapp.adapter.ShowGifsRecyclerAdapter
import com.parallax.vkgifapp.adapter.ShowGifsViewpagerAdapter
import com.parallax.vkgifapp.model.GifParams
import com.parallax.vkgifapp.model.MainData
import com.parallax.vkgifapp.presentation.gif.SingleGifFragment
import com.parallax.vkgifapp.repository.Repository
import kotlinx.coroutines.launch
import java.lang.NullPointerException

/**
 * Фрагмент основного активити
 */
class MainGifFragment: Fragment() {

    private lateinit var mainViewModel: MainFragmentViewModel  //вьюмодель фрагмента
    private val gifs = ArrayList<GifParams>()  //список с базовыми полями гифок
    private var trendGifsCopy: MainData? = null  //список для сохранения трендовых гивок
    private lateinit var gifsRecyclerAdapter: ShowGifsRecyclerAdapter  //ресайлер для отображения гифок
    private lateinit var viewPagerAdapter: ShowGifsViewpagerAdapter  //вьюпейджер для отображения гифок
    private lateinit var recyclerAdapter: RecyclerView  //вью ресайклера
    private lateinit var viewPager: ViewPager2  //вью вьюпейджера
    private lateinit var searchButton: Button  //вью кнопки поиска
    private lateinit var searchArea: EditText  //вью строки поиска
    private lateinit var toast: Toast  //инстанц тоста для вызова в разных частях класса

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_gif_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(
            this,
            MainFragmentViewModelFactory(Repository())
        )[MainFragmentViewModel::class.java]   //биндим вьюмодель

        mainViewModel.getTrendData()

        initUi(view)  //инициализируем ui

        getTrendData()  //получаем трендовые гифки из апи

    }

    /**
     * Инициализация UI
     */
    private fun initUi(view: View) {
        toast = Toast.makeText(this.requireContext(), "Не удалось загрузить гифки. Проверьте подключение к интернету и снова нажмите на иконку поиска!", Toast.LENGTH_LONG)

        //работа с ресайлером
        recyclerAdapter = view.findViewById(R.id.show_recyclerview_gifs)
        gifsRecyclerAdapter = ShowGifsRecyclerAdapter(this.requireContext(), gifs)
        recyclerAdapter.layoutManager = GridLayoutManager(this.requireContext(), 3)  //посчитал гридом будет интереснее
        recyclerAdapter.adapter = gifsRecyclerAdapter
        gifsRecyclerAdapter.setItemClick {

            proceedToSingleGifFragment(it)  //переход во фрагмент с полями гифки

        }

        //работа с вьюпейджером
        viewPager = view.findViewById(R.id.show_viewpager_gifs)
        viewPagerAdapter = ShowGifsViewpagerAdapter(this.requireContext(), gifs)  //добавил вьюпейджер для разнообразия
        viewPager.adapter = viewPagerAdapter
        viewPagerAdapter.setItemClick {

            proceedToSingleGifFragment(it)  //переход во фрагмент с полями гифки

        }
        //работа с поисковой строкой
        searchArea = view.findViewById(R.id.editTextSearchArea)
        searchButton = view.findViewById(R.id.search_gifs_button)
        searchButton.setOnClickListener {
            if (!TextUtils.isEmpty(searchArea.text)) {  //проверка на пустоту
                getSearchedData(searchArea.text.toString())  //метод обращения к апи с поиском
            } else {
                if (trendGifsCopy != null)
                    successApiState(trendGifsCopy!!)  //Нажимая на пустую строку поиска, подгружаются трендовые гифки
                else getTrendData()
            }
        }

    }

    /**
     * Метод для получения трендовых гифок
     */
    private fun getTrendData() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {  //используем этот метод для того, чтобы отправлять запрос на трендовые гифки при создании фрагмента
                mainViewModel.gifTrendList.collect {
                    when (it) {
                        is ApiState.Loading -> {
                            Log.d("API", "происходит загрузка трендовых гифок")
                        }
                        is ApiState.Failure -> {
                            Log.d("API", "Ошибка при загрузке трендовых гифок")
                            it.e.printStackTrace()
                            toast.show()
                        }
                        is ApiState.Success -> {
                            Log.d("API", "трендовые гифки загружены УСПЕШНО")
                            val data = it.data as MainData  //получаем данные из ApiState
                            successApiState(data)
                            trendGifsCopy = data.copy()

                        }
                        is ApiState.Empty -> {
                            Log.d("API", "SMTH went wrong")
                        }
                    }
                }
            }

        }
    }// собираем гифки

    /**
     * Метод для получения искомых гифок
     */
    private fun getSearchedData(searched: String) {
        mainViewModel.getSearchedData(searched)
        this.lifecycleScope.launch {  //запускаем корутину для получения искомых пользователем гифок
            mainViewModel.gifSearchedList.collect {
                when(it) {

                    is ApiState.Loading -> {
                        Log.d("API", "происходит загрузка искомых гифок")
                    }
                    is ApiState.Failure -> {
                        Log.d("API", "Ошибка при загрузке искомых гифок")
                        it.e.printStackTrace()
                        toast.show()
                    }

                    is ApiState.Success -> {
                        Log.d("API", "искомые гифки загружены УСПЕШНО")
                        val data =  it.data as MainData
                        successApiState(data)

                    }
                    is ApiState.Empty -> {
                        Log.d("API", "SMTH went wrong")
                    }
                }
            }
        }
    }

    /**
     * Вспомогательная функция для заполнения главного списка гифок и уведомления об изменении данных ресайклера и вьюпеджера
     */
    private fun successApiState(data: MainData) {
        gifs.clear()

        gifs.addAll(data.data)

        gifsRecyclerAdapter.notifyDataSetChanged()
        viewPagerAdapter.notifyDataSetChanged()
    }

    /**
     * Функция для перехода в фрагмент с полями гифки
     */
    private fun proceedToSingleGifFragment(gifParams: GifParams) {
        try {

            val activity = context as MainActivity
            val fragment = SingleGifFragment()
            val bundle = Bundle()  //бандл для передачи параметров гифки внутрь нового фрагмента
            val gson = Gson()
            val serialized = gson.toJson(gifParams)  //передадим класс в виде строки json
            bundle.putString("gif", serialized)
            fragment.arguments = bundle
            activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).addToBackStack(null).commit()
            // переход в новый фрагмент
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

}