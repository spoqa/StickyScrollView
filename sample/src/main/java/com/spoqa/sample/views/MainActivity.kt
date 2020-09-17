package com.spoqa.sample.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spoqa.sample.R
import com.spoqa.sample.adapters.SampleRecyclerViewAdapter
import com.spoqa.sample.models.SampleData
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sampleDataList = arrayListOf<SampleData>(
            SampleData("Lemon", "17kg", 2, "BOX", BigDecimal(80000), BigDecimal(160000)),
            SampleData("Grapefruit", "5kg", 1, "BOX", BigDecimal(30000), BigDecimal(30000)),
            SampleData("Seoul Milk", "1000ml", 5, "EA", BigDecimal(3000), BigDecimal(15000)),
            SampleData("Ice", "3kg", 10, "EA", BigDecimal(3500), BigDecimal(35000)),
            SampleData("Brown sugar", "1kg", 1, "EA", BigDecimal(2500), BigDecimal(2500))
        )

        main_recyclerView1.adapter =
            SampleRecyclerViewAdapter(sampleDataList)
        main_scrollView1.run {
            recyclerView = main_recyclerView1
        }

        main_recyclerView2.adapter =
            SampleRecyclerViewAdapter(sampleDataList)
        main_scrollView2.run {
            recyclerView = main_recyclerView2
            minWidthOfStickyColumn = (100 * context.resources.displayMetrics.density).toInt()
        }

        main_recyclerView3.adapter =
            SampleRecyclerViewAdapter(sampleDataList)
        main_scrollView3.run {
            stickyHeaderColumn = main_HeaderLayout
            recyclerView = main_recyclerView3
            minWidthOfStickyColumn = (100 * context.resources.displayMetrics.density).toInt()
        }
    }
}
