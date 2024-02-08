package com.example.dailynews.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.dailynews.adapter.ImageAdapter
import com.example.dailynews.ListItem
import com.example.dailynews.R
import com.example.dailynews.SimpleListAdapter
import com.example.dailynews.adapter.HomeRecyclerAdapter
import com.example.dailynews.data.HomeRecyclerData
import com.example.dailynews.data.TrendingData
import java.util.UUID

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeBottomFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeBottomFragment : Fragment() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback

    private var currentPage = 0
    private val delayMillis: Long = 3000 // Set the delay for auto-scrolling in milliseconds
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    private lateinit var itemList : ArrayList<HomeRecyclerData>


    private lateinit var imageList: ArrayList<TrendingData>

    private var isUserScrolling = false

    private lateinit var context : Context

    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8, 0, 8, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_bottom, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        context = requireContext()

        getRecyclerData()
        val adapter  = HomeRecyclerAdapter(context,itemList)
        val currSize = adapter.itemCount


        // Set up RecyclerView with a simple layout manager and adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter = adapter
        adapter.notifyItemRangeInserted(currSize,itemList.size)

        getViewPagerData()

        viewPager2 = view.findViewById<ViewPager2>(R.id.mainViewPager)



        val trendImageAdapter = context.let { ImageAdapter(it) }
        viewPager2.adapter = trendImageAdapter
        trendImageAdapter.submitList(imageList)

        val slideDotLL = view.findViewById<LinearLayout>(R.id.mainDotLL)
        val dotsImage = Array(imageList.size) { ImageView(context) }

        dotsImage.forEach {
            it.setImageResource(
                R.drawable.non_active_dot
            )
            slideDotLL.addView(it, params)
        }


        // default first dot selected
        dotsImage[0].setImageResource(R.drawable.active_dot)
        startAutoScroll()

        pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                dotsImage.mapIndexed { index, imageView ->
                    if (position == index) {
                        imageView.setImageResource(
                            R.drawable.active_dot
                        )
                    } else {
                        imageView.setImageResource(R.drawable.non_active_dot)
                    }
                }
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                isUserScrolling = state == ViewPager2.SCROLL_STATE_DRAGGING
            }
        }
        viewPager2.registerOnPageChangeCallback(pageChangeListener)
        // Inflate the layout for this fragment
        return view
    }

    private fun getRecyclerData(){
        itemList = arrayListOf<HomeRecyclerData>(
            HomeRecyclerData(
                "What is the current situation of the crypto market?",
                "4 sec ago",
                "1k read",
                "android.resource://" + context.packageName + "/" + R.drawable.crypto_market_image
            ),
            HomeRecyclerData(
                "More than 5.70 lakh children in Gujarat malnourished: Govt data",
                "10 sec ago",
                "3k read",
                "android.resource://" + context.packageName + "/" + R.drawable.indian_market_image
            ),
            HomeRecyclerData(
                "Agri Picks Report February 07, 2024: Geojit",
                "1 min ago",
                "1k read",
                "android.resource://" + context.packageName + "/" + R.drawable.commodity_image
            ),
            HomeRecyclerData(
                " Back Back IPOs in 2024: From Ola Electric to Firstcry here are 11 expected issues in the new year; check out the full list",
                "10 min ago",
                "1k read",
                "android.resource://" + context.packageName + "/" + R.drawable.ipo_image
            ),
            HomeRecyclerData(
                "India will be world's biggest oil demand growth driver through 2030, says IEA ",
                "8 min ago",
                "1k read",
                "android.resource://" + context.packageName + "/" + R.drawable.currency_image
            ),
            HomeRecyclerData(
                "95% mid-cap schemes fail to beat benchmarks in seven years",
                "18 sec ago",
                "1k read",
                "android.resource://" + context.packageName + "/" + R.drawable.mutual_fund_image
            ),
            HomeRecyclerData(
                "RBI MPC February 2024 Preview: Will the Monetary Policy Committee change its stance? ",
                "11 min ago",
                "1k read",
                "android.resource://" + context.packageName + "/" + R.drawable.stock
            ),
            HomeRecyclerData(
                "US trade deficit widens slightly in December",
                "4sec ago",
                "1k read",
                "android.resource://" + context.packageName + "/" + R.drawable.global_market
            )
        )
    }

    private fun getViewPagerData(){
        imageList = arrayListOf(
            TrendingData(
                UUID.randomUUID().toString(),
                "https://cdn.pixabay.com/photo/2023/03/28/11/52/ai-generated-7882967_1280.jpg"
            ),
            TrendingData(
                UUID.randomUUID().toString(),
                "https://cdn.pixabay.com/photo/2023/04/27/13/19/ai-generated-7954616_1280.png"
            ),
            TrendingData(
                UUID.randomUUID().toString(),
                "https://cdn.pixabay.com/photo/2023/11/10/16/05/anime-8379662_1280.jpg"

            )
            //"android.resource://" + packageName + "/" + R.drawable.fast_3
        )
    }


    private fun startAutoScroll() {
        runnable = Runnable {
            if (!isUserScrolling) {
                if (currentPage == imageList.size) {
                    currentPage = 0
                }
                viewPager2.setCurrentItem(currentPage++, true)
            }
            handler.postDelayed(runnable, delayMillis)
        }
        handler.postDelayed(runnable, delayMillis)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager2.unregisterOnPageChangeCallback(pageChangeListener)
        handler.removeCallbacks(runnable)
    }
}