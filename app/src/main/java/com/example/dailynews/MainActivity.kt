package com.example.dailynews

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils.replace
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.dailynews.fragments.BookmarksBottomFragment
import com.example.dailynews.fragments.CategoryBottomFragment
import com.example.dailynews.fragments.HomeBottomFragment
import com.example.dailynews.fragments.ProfileBottomFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.android.material.navigation.NavigationBarView
import java.util.UUID


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_view)

        val homeBottomFragment = HomeBottomFragment()
        val categoryBottomFragment = CategoryBottomFragment()
        val bookmarksBottomFragment = BookmarksBottomFragment()
        val profileBottomFragment = ProfileBottomFragment()

        setCurrentFragment(homeBottomFragment)


        val customItemBackground =
            ResourcesCompat.getDrawable(resources, R.drawable.bottom_nav_item_background, theme)

        bottomNavView.itemBackground = customItemBackground
        bottomNavView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_UNLABELED


        bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_page -> {
                    setCurrentFragment(homeBottomFragment)
                    return@setOnItemSelectedListener true
                }

                R.id.category -> {
                    setCurrentFragment(categoryBottomFragment)
                    return@setOnItemSelectedListener true
                }

                R.id.bookmarks -> {
                    setCurrentFragment(bookmarksBottomFragment)
                    return@setOnItemSelectedListener true
                }

                R.id.profile -> {
                    setCurrentFragment(profileBottomFragment)
                    return@setOnItemSelectedListener true
                }

                else -> false
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_notify, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.notify -> {
                Toast.makeText(this, "Notification Clicked", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
            replace(R.id.mainFrameLayout, fragment)
            commit()
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val fm = supportFragmentManager.backStackEntryCount
        if (fm == 1) {
            finish()
        } else if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }

    }

}