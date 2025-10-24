package com.eco.musicplayer.audioplayer.music.layout.summary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eco.musicplayer.audioplayer.music.layout.summary.adapter.CategoryAdapter
import com.eco.musicplayer.audioplayer.music.layout.summary.adapter.OfferAdapter
import com.eco.musicplayer.audioplayer.music.layout.summary.adapter.PopularAdapter
import com.eco.musicplayer.audioplayer.music.layout.summary.model.Category
import com.eco.musicplayer.audioplayer.music.layout.summary.model.Item
import com.eco.musicplayer.databinding.ActivitySummaryBinding

/**
 * Demo việc kết hợp nhiều layout
 */
class SummaryActivity : AppCompatActivity() {
    lateinit var binding: ActivitySummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCategory()
        initPopular()
        initOffers()
    }

    private fun initCategory() {
        val categoryList = listOf(
            Category("Cà phê", 1),
            Category("Trà sữa", 2),
            Category("Sinh tố", 3),
            Category("Đá xay", 4),
            Category("Bánh ngọt", 5)
        )

        binding.rcvCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rcvCategory.adapter = CategoryAdapter(categoryList)
    }

    private fun initPopular() {
        val popularList = listOf(
            Item(
                title = "Cà phê sữa đá",
                description = "Cà phê đậm đà, thơm ngon",
                picUrl = "https://thuytinhgiare.com/wp-content/uploads/2023/07/hinh-anh-ly-cafe-sua-da_3.jpg",
                price = 25000.0,
                rating = 4.5
            ),
            Item(
                title = "Trà đào cam sả",
                description = "Vị chua ngọt thanh mát",
                picUrl = "https://cdn.tgdd.vn/2020/07/CookRecipe/GalleryStep/thanh-pham-273.jpg",
                price = 30000.0,
                rating = 4.8
            ),
            Item(
                title = "Matcha Latte",
                description = "Thức uống Nhật Bản béo ngậy",
                picUrl = "https://ucarecdn.com/6aaa3bb8-8a69-41a1-9f8a-7c9a7380c4d6/-/crop/4016x5425/0,296/-/preview/",
                price = 35000.0,
                rating = 4.7
            )
        )

        binding.rcvPopular.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rcvPopular.adapter = PopularAdapter(popularList)
    }

    private fun initOffers() {
        val offerList = listOf(
            Item(
                title = "Combo Cà phê + Bánh ngọt",
                description = "Giảm 20% combo buổi sáng",
                picUrl = "https://aeroco.vn/wp-content/uploads/2025/03/Caphevabanhngot1.jpg",
                price = 45000.0,
                rating = 4.9
            ),
            Item(
                title = "Trà sữa trân châu",
                description = "Mua 1 tặng 1 cho học sinh sinh viên",
                picUrl = "https://product.hstatic.net/1000384103/product/chewy_choux_tra_sua_1ac6c64ec22f4d968761183fb138d67b.png",
                price = 50000.0,
                rating = 4.6
            ),
            Item(
                title = "Cà phê đen",
                description = "Mua 1 tặng 1 cho học sinh sinh viên",
                picUrl = "https://cdn.nhathuoclongchau.com.vn/unsafe/800x0/https://cms-prod.s3-sgn09.fptcloud.com/bai_vietca_phe_den_bao_nhieu_calo_uong_nhieu_co_tot_khong_html_1_ebb28c9c42.png",
                price = 30000.0,
                rating = 5.0
            )
        )

        binding.rcvOffer.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rcvOffer.adapter = OfferAdapter(offerList)
    }
}
