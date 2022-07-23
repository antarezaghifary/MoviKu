package com.gibox.moviku.ui.activity.detail

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.gibox.moviku.R
import com.gibox.moviku.databinding.ActivityDetailBinding
import com.gibox.moviku.util.dateFormat
import com.gibox.moviku.util.loadImage

class DetailActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private var id: Int? = null
    private var title: String? = null
    private var original_title: String? = null
    private var image: String? = null
    private var poster: String? = null
    private var overview: String? = null
    private var rilis: String? = null
    private var vote: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        title = intent.getStringExtra("title")
        Log.e("TAG", "title: $title")
        image = intent.getStringExtra("image")
        poster = intent.getStringExtra("poster")
        overview = intent.getStringExtra("overview")
        rilis = intent.getStringExtra("rilis")

        with(binding) {
            setupToolbar()
        }
    }

    private fun setupToolbar() {
        with(binding) {
            ivClose.setOnClickListener { onBackPressed() }
            ivBack.setOnClickListener { onBackPressed() }
            tvToolbar.text = title
            tvEmpty.text = String.format(
                getString(R.string.placeholder_empty_review),
                title
            )
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        window.decorView.systemUiVisibility =
                            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                    }
                    Configuration.UI_MODE_NIGHT_NO -> {
                        window.decorView.systemUiVisibility =
                            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                    }
                }
            } else {
                window.setDecorFitsSystemWindows(true)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                nsContent.setOnScrollChangeListener { _, _, scrollY, _, _ ->
                    if (scrollY > 250) {
                        toolbar.isVisible = true
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                                Configuration.UI_MODE_NIGHT_YES -> {
                                    window.decorView.systemUiVisibility =
                                        View.SYSTEM_UI_FLAG_VISIBLE
                                }
                                Configuration.UI_MODE_NIGHT_NO -> {
                                    window.decorView.systemUiVisibility =
                                        (View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                                }
                            }
                        } else {
                            window.setDecorFitsSystemWindows(true)
                        }
                    } else {
                        toolbar.isVisible = false
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                                Configuration.UI_MODE_NIGHT_YES -> {
                                    window.decorView.systemUiVisibility =
                                        (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                                }
                                Configuration.UI_MODE_NIGHT_NO -> {
                                    window.decorView.systemUiVisibility =
                                        (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                                }
                            }
                        } else {
                            window.setDecorFitsSystemWindows(false)
                        }
                    }
                }
            }
        }
        setupUI()
    }

    private fun setupUI() {
        with(binding) {
            loadImage(ivBackground, image!!)
            loadImage(ivImage, poster!!)

            tvTitle.text = title
            if (rilis!!.isNotEmpty()) {
                tvDate.dateFormat(rilis!!, "yyyy-MM-dd", "dd MMMM yyyy")
            } else {
                tvDate.text = getString(R.string.title_unknown)
            }

            tvOverview.text = if (overview!!.isNotEmpty()) {
                overview
            } else {
                getString(R.string.title_unknown)
            }
        }
    }

}